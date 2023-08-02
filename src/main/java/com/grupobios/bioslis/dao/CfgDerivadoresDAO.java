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

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.LogCfgtablas;

/**
 *
 * @author marco.caracciolo
 */
public class CfgDerivadoresDAO {

  private static Logger logger = LogManager.getLogger(CfgDerivadoresDAO.class);
  private static ValidacionMantenedor validador = new ValidacionMantenedor();

  public List<CfgDerivadores> getDerivadores() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select cderiv from CfgDerivadores cderiv where cderiv.cderivIdderivador > 0"
          + " order by cderiv.cderivSort, cderiv.cderivDescripcion");
      List<CfgDerivadores> lista = query.list();
      sesion.close();
      return lista;
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

  public List<CfgDerivadores> getDerivadoresSelect() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createQuery("from CfgDerivadores cderiv"
          + " order by case when cderiv.cderivIdderivador = 0 then 0 else 1 end, cderiv.cderivSort asc, cderiv.cderivDescripcion asc");
      List<CfgDerivadores> lista = query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public CfgDerivadores getDerivadorById(Short id) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    CfgDerivadores deriv = null;
    try {
      Query query = sesion.createQuery("from CfgDerivadores cd " + "where cd.cderivIdderivador = :id");
      query.setParameter("id", id);
      deriv = (CfgDerivadores) query.uniqueResult();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return deriv;
  }

  public String agregarDerivador(CfgDerivadores cd, Long idUsuario) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    try {
      if (validador.validarCodigoyAbreviado("CDERIV_CODIGO", "CDERIV_DESCRIPCION", "CFG_DERIVADORES",
          cd.getCderivCodigo(), cd.getCderivDescripcion())) {
        sesion.beginTransaction();
        sesion.save(cd);
        
        
        //Se agregan derivadores a log tablas **********************
        LogCfgtablas logTabla = new LogCfgtablas();
        LogCfgTablasDAO log = new LogCfgTablasDAO();
        logTabla.setCfgAccionesdatos(1);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_DERIVADORES");                  
        logTabla.setLctIdtabla(cd.getCderivIdderivador());
        logTabla.setLctDescripcion(cd.getCderivDescripcion());
        
        log.insertLogTablas(logTabla, sesion);
        //*************************************************************
        
        
        
        sesion.getTransaction().commit();
        mensaje = "C";
      }

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public String actualizarDerivador(CfgDerivadores cd, Long idUsuario) throws BiosLISDAOException {
	  CfgDerivadores cdAntiguo = this.getDerivadorById(cd.getCderivIdderivador());
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    try {
      BigDecimal id = new BigDecimal(cd.getCderivIdderivador());
      if (validador.validarxId("CDERIV_CODIGO", "CDERIV_IDDERIVADOR", "CDERIV_DESCRIPCION", "CFG_DERIVADORES",
          cd.getCderivCodigo(), cd.getCderivDescripcion(), id)) {
        sesion.beginTransaction();
        sesion.update(cd);
        sesion.getTransaction().commit();
        mensaje = "C";
        sesion.close();
        
        LogCfgTablasDAO log = new LogCfgTablasDAO();
        log.comparadorObjetos(cdAntiguo, cd, 2, idUsuario.intValue(), cd.getCderivIdderivador(), cd.getCderivDescripcion(), null);
        
        
      }

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public List<CfgDerivadores> getDerivadoresFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    String codigo = filtros.get("codigo").toUpperCase();
    String nombre = filtros.get("nombre").toUpperCase();
    String activo = filtros.get("activo");
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgDerivadores> lista = null;
    try {
      String sqlDeriv = "from CfgDerivadores cd" + " where cd.cderivCodigo like :codigo"
          + " and cd.cderivDescripcion like :nombre" + " and cd.cderivIdderivador > 0";
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sqlDeriv += " and cd.cderivActivo = 'S'";
      }
      sqlDeriv += " order by cd.cderivSort asc, cd.cderivDescripcion asc";
      Query query = sesion.createQuery(sqlDeriv);
      query.setParameter("codigo", "%" + codigo + "%");
      query.setParameter("nombre", "%" + nombre + "%");
      lista = query.list();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public CfgDerivadores getDerivadorById(Short id, Session sesion) throws BiosLISDAOException {
    CfgDerivadores deriv = null;
    try {
      Query query = sesion.createQuery("from CfgDerivadores cd " + "where cd.cderivIdderivador = :id");
      query.setParameter("id", id);
      deriv = (CfgDerivadores) query.uniqueResult();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
//      if (sesion.isOpen()) {
//        sesion.close();
//      }
    }
    return deriv;
  }
}
