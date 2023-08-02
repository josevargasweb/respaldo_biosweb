/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgMetodos;
import com.grupobios.bioslis.entity.LogCfgtablas;

/**
 *
 * @author Nacho
 */
public class CfgMetodosDAO {

  private static Logger logger = LogManager.getLogger(CfgMetodosDAO.class);
  private static ValidacionMantenedor validador = new ValidacionMantenedor();
  LogCfgTablasDAO logCfgTablasDAO;

  public LogCfgTablasDAO getLogCfgTablasDAO() {
    return logCfgTablasDAO;
  }

  public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
    this.logCfgTablasDAO = logCfgTablasDAO;
  }

  public String insertMetodos(CfgMetodos cmet, Long idUsuario) throws BiosLISDAOException {
    Session sesion = null;
    String mensaje = "E";
    try {
      if (validador.validarCodigo("CMET_DESCRIPCION", "CFG_METODOS", cmet.getCmetDescripcion())) {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.save(cmet);

        // Se agregan metodos log tablas **********************
        LogCfgtablas logTabla = new LogCfgtablas();
        logTabla.setCfgAccionesdatos(1);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_METODOS");
        logTabla.setLctIdtabla(cmet.getCmetIdmetodo());
        logTabla.setLctDescripcion(cmet.getCmetDescripcion());

        logCfgTablasDAO.insertLogTablas(logTabla, sesion);
        // ********************************************************

        sesion.getTransaction().commit();
        sesion.close();
        mensaje = "C";

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

  public String updateMetodos(CfgMetodos cmet, Long idUsuario) throws BiosLISDAOException {
    CfgMetodos cmetAntiguo = this.getMetodoById(cmet.getCmetIdmetodo());
    Session sesion = null;
    String mensaje = "E";
    try {
    	if (validador.validarxIdSinAbreviado("CMET_DESCRIPCION","CMET_IDMETODO" ,"CFG_METODOS", cmet.getCmetDescripcion(),BigDecimal.valueOf(cmet.getCmetIdmetodo()))) {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.update(cmet);
        sesion.getTransaction().commit();
        sesion.close();

        logCfgTablasDAO.comparadorObjetos(cmetAntiguo, cmet, 2, idUsuario.intValue(), cmet.getCmetIdmetodo(),
            cmet.getCmetDescripcion(), "");

        mensaje = "C";
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

  public List<CfgMetodos> getMetodos() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(
          "select cmet " + "from com.grupobios.bioslis.entity.CfgMetodos cmet " + "order by cmet.cmetDescripcion");
      List<CfgMetodos> listaMetodos = query.list();
      sesion.close();
      return listaMetodos;
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

  public List<CfgMetodos> getMetodosbyLike(String nombre, String activo) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "select cmet " + "from com.grupobios.bioslis.entity.CfgMetodos cmet "
          + "where upper(cmet.cmetDescripcion) like :nombre ";
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sql += " and cmet.cmetActivo = 'S'";
      }
      sql += " order by cmet.cmetDescripcion asc";
      Query query = sesion.createQuery(sql);
      query.setParameter("nombre", "%" + nombre + "%");
      List<CfgMetodos> listaMetodos = query.list();
      sesion.close();
      return listaMetodos;
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

  public CfgMetodos getMetodoById(int idMetodo) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("from CfgMetodos cmet " + "where cmet.cmetIdmetodo = :idMetodo");
      query.setParameter("idMetodo", idMetodo);
      CfgMetodos cmet = (CfgMetodos) query.uniqueResult();
      sesion.close();
      return cmet;
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

  public List<CfgMetodos> getMetodosByTest(int idTest) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createSQLQuery(
          "select cm.* " + "from cfg_metodos cm " + "join cfg_metodostest cmt on cmt.cmt_idmetodo = cm.cmet_idmetodo "
              + "where cmt.cmt_idtest = :idTest and cmt.cmt_activo = 'S'")
          .addEntity(CfgMetodos.class);
      query.setParameter("idTest", idTest);
      List<CfgMetodos> listaMetodos = query.list();
      sesion.close();
      return listaMetodos;
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

  public CfgMetodos getMetodoPorDefecto(int idTest) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createSQLQuery(
          "select cm.* " + "from cfg_metodos cm " + "join cfg_metodostest cmt on cmt.cmt_idmetodo = cm.cmet_idmetodo "
              + "where cmt.cmt_idtest = :idTest and cmt.cmt_esvalorpordefecto = 'S'")
          .addEntity(CfgMetodos.class);
      query.setParameter("idTest", idTest);
      CfgMetodos cmet = (CfgMetodos) query.uniqueResult();
      sesion.close();
      return cmet;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

  }

}
