/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LogCfgtablas;

/**
 *
 * @author Nacho
 */
public class CfgMuestrasDAO {

  private static Logger logger = LogManager.getLogger(CfgMuestrasDAO.class);
  private static ValidacionMantenedor validador = new ValidacionMantenedor();
  LogCfgTablasDAO logCfgTablasDAO;
  
  

  public LogCfgTablasDAO getLogCfgTablasDAO() {
	return logCfgTablasDAO;
}

public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
	this.logCfgTablasDAO = logCfgTablasDAO;
}

public List<CfgMuestras> getMuestras() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createQuery("select cmue.cmueIdtipomuestra, cmue.cmueDescripcion "
          + "from com.grupobios.bioslis.entity.CfgMuestras cmue where cmue.cmueIdtipomuestra <> 0 order by cmue.cmueSort , cmue.cmueDescripcionabreviada asc");
      List<CfgMuestras> listMuestras = query.list();
      sesion.close();
      return listMuestras;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgMuestras> getListMuestras() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createQuery(
          "select cmue " + "from CfgMuestras cmue " + "order by cmue.cmueSort , cmue.cmueDescripcionabreviada asc");
      List<CfgMuestras> listMuestras = query.list();
      sesion.close();
      return listMuestras;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<Object[]> getListaMuestras() throws BiosLISDAOException {

    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion
          .createQuery("select cmue.cmueIdtipomuestra, cmue.cmuePrefijotipomuestra, cmue.cmueDescripcionabreviada "
              + "from CfgMuestras cmue where cmue.cmuePrefijotipomuestra is not null and cmue.cmueIdtipomuestra <> 0 order by cmue.cmueSort , cmue.cmueDescripcionabreviada asc");//
      List<Object[]> listMuestras = query.list();
      sesion.close();
      return listMuestras;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<Object[]> getListaMuestrasByGrupo(short idGrupo) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion
          .createQuery("select cmue.cmueIdtipomuestra, cmue.cmuePrefijotipomuestra, cmue.cmueDescripcionabreviada "
              + "from CfgMuestras cmue where cmue.cmuePrefijotipomuestra is not null and cmue.cfgGruposmuestras.cgmIdgrupomuestra=:idGrupo and cmue.cmueIdtipomuestra <> 0");
      query.setParameter("idGrupo", idGrupo);
      List<Object[]> listMuestras = query.list();
      sesion.close();
      return listMuestras;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public CfgMuestras getMuestraById(short idMuestra) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createQuery("from CfgMuestras cmue where cmue.cmueIdtipomuestra=:idMuestra");
      query.setParameter("idMuestra", idMuestra);
      CfgMuestras cm = (CfgMuestras) query.uniqueResult();
      sesion.close();
      return cm;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public String agregarMuestra(CfgMuestras cmue, Long idUsuario) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    // CfgGruposmuestras cgm = new CfgGruposmuestras();
    // cgm.setCgmIdgrupomuestra((short)0);
    // cmue.setCfgGruposmuestras(cgm);
    try {
      if (validador.validarCodigo("CMUE_DESCRIPCIONABREVIADA", "CFG_MUESTRAS", cmue.getCmueDescripcionabreviada())) {
        sesion.beginTransaction();
        sesion.save(cmue);  
        
        //Se agregan muestras a log tablas **********************    
        
        LogCfgtablas logTabla = new LogCfgtablas();
        logTabla.setCfgAccionesdatos(1);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_MUESTRAS");         
        logTabla.setLctIdtabla(cmue.getCmueIdtipomuestra());
        logTabla.setLctDescripcion(cmue.getCmueDescripcionabreviada());
        logCfgTablasDAO.insertLogTablas(logTabla, sesion);
        //********************************************************************
        
        sesion.getTransaction().commit();
        sesion.close();
        
     
        
        mensaje = "C";
      }

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public String actualizarTipoMuestra(CfgMuestras cmue, Long idUsuario) throws BiosLISDAOException {
	  CfgMuestras cmueAntiguo = this.getMuestraById(cmue.getCmueIdtipomuestra());
	  
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    try {
      BigDecimal id = new BigDecimal(cmue.getCmueIdtipomuestra());
      if (validador.validarxIdSinAbreviado("CMUE_DESCRIPCIONABREVIADA", "CMUE_IDTIPOMUESTRA", "CFG_MUESTRAS",    		  
          cmue.getCmueDescripcionabreviada(), id)) {
    	  
        sesion.beginTransaction();
        
        sesion.update(cmue);
        sesion.getTransaction().commit();
        sesion.close();
        mensaje = "C";
        
        logCfgTablasDAO.compararTablasMuestras(cmue, cmueAntiguo, idUsuario,cmue.getCmueDescripcionabreviada());
      }
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public List<String> buscarPrefijos() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createQuery("select cmue.cmuePrefijotipomuestra from CfgMuestras cmue "
          + "where cmue.cmuePrefijotipomuestra is not null " + "order by cmue.cmuePrefijotipomuestra asc");
      List<String> prefijos = query.list();
      sesion.close();
      return prefijos;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgMuestras> getMuestrasByGrupo(long idExamen) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgMuestras> list = null;
    try {
      String sql = "select cmue.* from cfg_muestras cmue "
          + "join cfg_gruposmuestrasexa cgme on cgme.cgme_idtipomuestra = cmue.cmue_idtipomuestra "
          + "where cgme.cgme_idexamen = :idExamen "
          // + "and cgm.cgmIdgrupomuestra > 0"
          + " ORDER BY cmue.CMUE_SORT ASC, CMUE.CMUE_DESCRIPCION ASC ";
      Query query = sesion.createSQLQuery(sql).addEntity(CfgMuestras.class);
      query.setParameter("idExamen", idExamen);
      list = query.list();
      sesion.close();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("error en getMuestrasByGrupo " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return list;
  }

  public List<CfgMuestras> getMuestrasFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    String prefijo = filtros.get("prefijo").toUpperCase();
    String descripcion = filtros.get("descripcion").toUpperCase();
    String activo = filtros.get("activo");
    // short idGrupo = Short.parseShort(filtros.get("idGrupo"));
    List<CfgMuestras> listMuestras = null;

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sql = "from CfgMuestras cmue" + " where cmue.cmuePrefijotipomuestra is not null"
          + " and cmue.cmueIdtipomuestra >= 1" + " and cmue.cmuePrefijotipomuestra like :prefijo"
          + " and cmue.cmueDescripcionabreviada like :descripcion";
      if (activo.equals("S")) {
        sql += " and cmue.cmueActivo=:activo";
      }
      // if (idGrupo>0){
      // sql += " and cmue.cfgGruposmuestras.cgmIdgrupomuestra=:idGrupo";
      // }
      sql += " order by cmue.cmueSort , cmue.cmueDescripcionabreviada asc";
      Query query = sesion.createQuery(sql);
      query.setParameter("prefijo", "%" + prefijo + "%");
      query.setParameter("descripcion", "%" + descripcion + "%");
      if (activo.equals("S")) {
        query.setParameter("activo", activo);
      }
      // if (idGrupo>0){
      // query.setParameter("idGrupo", idGrupo);
      // }
      listMuestras = query.list();
      sesion.close();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return listMuestras;
  }

}
