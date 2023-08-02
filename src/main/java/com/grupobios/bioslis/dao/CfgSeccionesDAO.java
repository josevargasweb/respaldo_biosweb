/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.SeccionLabDTO;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.LogCfgtablas;

/**
 *
 * @author Nacho
 */
public class CfgSeccionesDAO {

  private static Logger logger = LogManager.getLogger(CfgSeccionesDAO.class);
  private static ValidacionMantenedor validador = new ValidacionMantenedor();

  public String insertSeccion(CfgSecciones csec, Long idUsuario) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    // Se agrego validacion x jan
    String mensaje = "Ya existe";

    try {
      if (validador.validarCodigo("CSEC_CODIGO", "CFG_SECCIONES", csec.getCsecCodigo())) {
        sesion.beginTransaction();
        sesion.save(csec);

        // Se agregan secciones a log tablas **********************
        LogCfgtablas logTabla = new LogCfgtablas();
        LogCfgTablasDAO log = new LogCfgTablasDAO();
        logTabla.setCfgAccionesdatos(1);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_SECCIONES");
        logTabla.setLctIdtabla(csec.getCsecIdseccion());
        logTabla.setLctDescripcion(csec.getCsecDescripcion());

        log.insertLogTablas(logTabla, sesion);
        // *************************************************************

        sesion.getTransaction().commit();
        mensaje = "Creado exitosamente";
      }
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public String updateSeccion(CfgSecciones csec, Long idUsuario) throws BiosLISDAOException {
    CfgSecciones csecAntiguo = this.getSeccionById(csec.getCsecIdseccion());
    CfgLaboratoriosDAO labDao = new CfgLaboratoriosDAO();
    CfgLaboratorios lab = labDao.getLaboratorioById(csec.getCfgLaboratorios().getClabIdlaboratorio());
    csec.setCfgLaboratorios(lab);
    System.out.println(csec);
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "Ya existe";
    System.out.println(csec.getCfgLaboratorios());
    try {
      if (validador.validarxIdSinAbreviado("CSEC_CODIGO", "CSEC_IDSECCION", "CFG_SECCIONES", csec.getCsecCodigo(),
          BigDecimal.valueOf(csec.getCsecIdseccion()))) {
        sesion.beginTransaction();
        sesion.update(csec);
        sesion.getTransaction().commit();
        mensaje = "Actualizado exitosamente";
        sesion.close();

        LogCfgTablasDAO log = new LogCfgTablasDAO();
        log.compararTablasSecciones(csec, csecAntiguo, idUsuario, csec.getCsecDescripcion());

      }

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public List<CfgSecciones> getSecciones() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgSecciones> listaSecciones = null;
    try {

      Query query = sesion.createQuery(
          "select csec " + "from com.grupobios.bioslis.entity.CfgSecciones csec left join fetch csec.cfgLaboratorios "
              + "where csec.csecIdseccion > 0 and csec.csecActiva = 'S' " // + "order by csec.csecIdseccion asc ");
              + "order by csec.csecSort asc, csec.csecDescripcion asc");
      listaSecciones = query.list();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaSecciones;
  }

  // get sin microbiologia
  public List<CfgSecciones> getSeccionesSM() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgSecciones> listaSecciones = null;
    try {

      Query query = sesion.createQuery("select csec " + "from com.grupobios.bioslis.entity.CfgSecciones csec "
      		+ " left join fetch csec.cfgLaboratorios "
          + "where csec.csecIdseccion > 0 and csec.csecActiva = 'S' and csec.csecEsMicrobiologia = 'N' "
          + "order by csec.csecIdseccion asc ");
      // + "order by csec.csecDescripcion asc");

      listaSecciones = query.list();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaSecciones;
  }

  public CfgSecciones getSeccionById(int idSeccion) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    CfgSecciones seccion = null;
    try {

      Query query = sesion.createQuery(
          "select csec " + "from com.grupobios.bioslis.entity.CfgSecciones csec inner join fetch csec.cfgLaboratorios "
              + "where csec.csecIdseccion = :idSeccion");

      query.setParameter("idSeccion", idSeccion);
      seccion = (CfgSecciones) query.uniqueResult();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return seccion;
  }

  public List<CfgSecciones> getSeccionesByLab(int idLab) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgSecciones> listaSecciones = null;
    try {
      String condition = (idLab == -1) ? ""
          : "join fetch csec.cfgLaboratorios lab where lab.clabIdlaboratorio = :idLab";
      Query query = sesion.createQuery("select csec from CfgSecciones csec " + condition
          + " order by csec.csecSort asc, csec.csecDescripcion asc");
      if (idLab != -1)
        query.setParameter("idLab", idLab);
      listaSecciones = query.list();
    } catch (HibernateException he) {
      logger.error("error en getSeccionesByLab " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaSecciones;
  }

  public List<SeccionLabDTO> getSeccionesLaboratorios(int idLab) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<SeccionLabDTO> list = null;
    try {
      String sql = "select csec.csec_idseccion, csec.csec_idlaboratorio, csec.csec_descripcion, clab.clab_descripcion "
          + "from BIOSLIS.cfg_secciones csec "
          + "join cfg_laboratorios clab on clab.clab_idlaboratorio = csec.csec_idlaboratorio "
          + "where csec.csec_idlaboratorio = :idLab " // + "order by csec.csec_idseccion";
          + "order by csec.csec_sort asc, csec.csec_descripcion asc";
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(SeccionLabDTO.class));
      query.setParameter("idLab", idLab);
      list = query.list();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return list;
  }

  public List<CfgSecciones> getSeccionesFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    String codigo = filtros.get("codigo").toUpperCase();
    String descripcion = filtros.get("descripcion").toUpperCase();
    String activo = filtros.get("activo");
    int idLab = Integer.parseInt(filtros.get("idLab"));
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgSecciones> list = null;
    try {

      String sqlGetSecciones = "from CfgSecciones csec left join fetch csec.cfgLaboratorios "
          + " where csec.csecCodigo like :codigo"
          + " and csec.csecDescripcion like :descripcion" + " and csec.csecIdseccion >= 1";
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sqlGetSecciones += " and csec.csecActiva = 'S'";
      }
      if (idLab > 0) {
        sqlGetSecciones += " and csec.cfgLaboratorios.clabIdlaboratorio = :idLab";
      }
      sqlGetSecciones += " order by csec.csecSort asc, csec.csecDescripcion asc";
      Query query = sesion.createQuery(sqlGetSecciones);
      query.setParameter("codigo", "%" + codigo + "%");
      query.setParameter("descripcion", "%" + descripcion + "%");
      if (idLab > 0) {
        query.setParameter("idLab", idLab);
      }
      list = query.list();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return list;
  }
}
