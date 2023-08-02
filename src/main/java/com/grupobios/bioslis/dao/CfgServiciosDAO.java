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
import org.springframework.beans.BeanUtils;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.CfgServiciosDTO;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgLocalizaciones;
import com.grupobios.bioslis.entity.CfgServicios;

import com.grupobios.bioslis.entity.LogCfgtablas;



public class CfgServiciosDAO {

  private static Logger logger = LogManager.getLogger(CfgServiciosDAO.class);
  private static ValidacionMantenedor validador = new ValidacionMantenedor();

  public List<CfgServicios> getServicios() {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery("select list from CfgServicios list  join  fetch list.cfgCentrosdesalud "
            + "where list.csIdservicio >= 0 and list.csActivo = 'S'" 
            + "order by case when list.csIdservicio = 0 then 0 else 1 end, list.csSort asc, list.csDescripcion asc");
        		//+ "order by list.csIdservicio asc");
        List<CfgServicios> lista = query.list();
        sesion.close();
        logger.debug("Cantidad de servicios recuperados {}", lista.size());
        return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      // throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return null;
  }

  public CfgServicios getServiciosById(int idS) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(
          "select list from com.grupobios.bioslis.entity.CfgServicios list " + "where list.csIdservicio = :Servicios "); // and
                                                                                                                         // list.csIdservicio
                                                                                                                         // >
                                                                                                                         // 0");
      query.setParameter("Servicios", idS);
      CfgServicios list = (CfgServicios) query.uniqueResult();
      sesion.close();
      return list;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public List<CfgServicios> getServiciosByIdCentro(short idCentro) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgServicios> lista = null;
    try {
      Query query = sesion.createQuery("from CfgServicios cs "
          + "where cs.cfgCentrosdesalud.ccdsIdcentrodesalud = :idCentro and cs.csIdservicio > 0 "
          + "order by cs.csIdservicio asc");
      query.setParameter("idCentro", idCentro);
      lista = query.list();
    } catch (HibernateException he) {
      logger.error("error en getServiciosByIdCentro " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public List<CfgServicios> getServiciosActivosByIdCentro(short idCentro) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgServicios> lista = null;
    try {
      Query query = sesion.createQuery("from CfgServicios cs "
          + "where cs.cfgCentrosdesalud.ccdsIdcentrodesalud = :idCentro and cs.csIdservicio > 0 and cs.csActivo = 'S'"
          + "order by cs.csIdservicio asc");
      query.setParameter("idCentro", idCentro);
      lista = query.list();
    } catch (HibernateException he) {
      logger.error("error en getServiciosActivosByIdCentro " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public List<CfgServicios> getServiciosLocalizacion(HashMap<String, String> filtros) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    short idCentro = Short.parseShort(filtros.get("idCentro"));
    String activo = filtros.get("activo");
    List<CfgServicios> lista = null;
    try {
      String sql = "from CfgServicios cs"
          + " where cs.cfgCentrosdesalud.ccdsIdcentrodesalud = :idCentro and cs.csIdservicio > 0";
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sql += " and cs.csActivo = 'S'";
      }
      sql += " order by cs.csDescripcion asc";
      Query query = sesion.createQuery(sql);
      query.setParameter("idCentro", idCentro);
      lista = query.list();
    } catch (HibernateException he) {
      logger.error("error en getServiciosActivosByIdCentro " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public String agregarServicio(CfgServiciosDTO cs, Long idUsuario) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    // Se agrego validacion x jan

    CfgServicios cfgservice = new CfgServicios();

    BeanUtils.copyProperties(cs, cfgservice);

    CfgCentrosDeSaludDAO ccdsdao = new CfgCentrosDeSaludDAO();
    CfgCentrosdesalud cfgCentrosdesalud = new CfgCentrosdesalud();
    if (cs.getCfgCentrosdesalud() > 0) {
      cfgCentrosdesalud = ccdsdao.getCentrosDeSaludById((short) cs.getCfgCentrosdesalud());
    } else {
      cfgCentrosdesalud = ccdsdao.getCentrosDeSaludById((short) 1);
    }

    cfgservice.setCfgCentrosdesalud(cfgCentrosdesalud);

    String mensaje = "E";
    try {

    	if (validador.validarCodigoyAbreviado("CS_CODIGO","CS_DESCRIPCION","CFG_SERVICIOS",cs.getCsCodigo(),cs.getCsDescripcion())) {
            sesion.beginTransaction();
            sesion.save(cfgservice);
            
            //Se agregan secciones a log tablas **********************
            LogCfgtablas logTabla = new LogCfgtablas();
            LogCfgTablasDAO log = new LogCfgTablasDAO();
            logTabla.setCfgAccionesdatos(1);
            logTabla.setLctIdusuario(idUsuario.intValue());
            logTabla.setLctNombretabla("CFG_SERVICIOS");                  
            logTabla.setLctIdtabla(cfgservice.getCsIdservicio());
            logTabla.setLctDescripcion(cfgservice.getCsDescripcion());
            
            log.insertLogTablas(logTabla, sesion);
            //*************************************************************
            
            
            
            sesion.getTransaction().commit();
            mensaje = "C";
            
            //Agregado por Marco Caracciolo 21/03/2023
            CfgLocalizaciones cl = new CfgLocalizaciones();
            cl.setClCodigolocalizacion(cfgservice.getCsCodigo());
            cl.setClIdservicio(cfgservice.getCsIdservicio());
            cl.setClIdcentrodesalud(cs.getCfgCentrosdesalud());
            CfgLocalizacionesDAO clDAO = new CfgLocalizacionesDAO();
            clDAO.agregarLocalizacion(cl);
            //End añadido por Marco Caracciolo
          
            
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

  public String actualizarServicio(CfgServiciosDTO cs, Long idUsuario) throws BiosLISDAOException {
	  
	  CfgServicios cfgserviceAntiguo = new CfgServicios();
	  cfgserviceAntiguo = this.getServiciosById(cs.getCsIdservicio());
	  
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    // Se agrego validacion x jan
    String mensaje = "E";
    CfgServicios cfgservice = new CfgServicios();

    BeanUtils.copyProperties(cs, cfgservice);

    CfgCentrosDeSaludDAO ccdsdao = new CfgCentrosDeSaludDAO();
    CfgCentrosdesalud cfgCentrosdesalud = new CfgCentrosdesalud();
    if (cs.getCfgCentrosdesalud() > 0) {
      cfgCentrosdesalud = ccdsdao.getCentrosDeSaludById((short) cs.getCfgCentrosdesalud());
    } else {
      cfgCentrosdesalud = ccdsdao.getCentrosDeSaludById((short) 1);
    }

    cfgservice.setCfgCentrosdesalud(cfgCentrosdesalud);
    try {

		//Revisa en la bd si son la misma id para actualizar
    	if (validador.validarxId("CS_CODIGO","CS_IDSERVICIO","CS_DESCRIPCION","CFG_SERVICIOS",cs.getCsCodigo(),cs.getCsDescripcion(),BigDecimal.valueOf(cs.getCsIdservicio()))) {
		        sesion.beginTransaction();
		        sesion.update(cfgservice);
		        sesion.getTransaction().commit();
		        mensaje = "C";
                sesion.close();
                
                
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                log.compararTablasServicios(cfgservice, cfgserviceAntiguo, idUsuario, cfgservice.getCsDescripcion());
                
                
                
            //Agregado por Marco Caracciolo 21/03/2023
            CfgLocalizacionesDAO clDAO = new CfgLocalizacionesDAO();
            CfgLocalizaciones cl = clDAO.getLocalizacion(cfgservice.getCsIdservicio(), 0, 0);
            if (cl!=null && cl.getClIdlocalizacion()>0){
                cl.setClCodigolocalizacion(cs.getCsCodigo());
                cl.setClIdcentrodesalud(cs.getCfgCentrosdesalud());
                clDAO.actualizarLocalizacion(cl);
            } else {
                cl = new CfgLocalizaciones();
                cl.setClIdservicio(cfgservice.getCsIdservicio());
                cl.setClCodigolocalizacion(cs.getCsCodigo());
                cl.setClIdcentrodesalud(cs.getCfgCentrosdesalud());
                clDAO.agregarLocalizacion(cl);
            }
            
            //End añadido por Marco Caracciolo
        	
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

  public List<CfgServicios> getServiciosActivosWithMail() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgServicios> lista = null;
    try {
      Query query = sesion.createQuery("select cs from CfgServicios cs "
          + "where (cs.csEmail is not null or cs.csEmail <> '') and cs.csActivo='S' ");

      lista = query.list();
      sesion.close();
    } catch (HibernateException he) {
      logger.error("error en getServiciosActivosWithMail " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public CfgServicios getServiciosLocalizacionByNroOrden(long nOrden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    CfgServicios servicios = null;
    try {
      Query query = sesion
          .createSQLQuery("select cs.* from cfg_servicios cs "
              + "join cfg_localizaciones cl ON cs.cs_idservicio = cl.cl_idservicio "
              + "join datos_fichas df on cl.cl_idlocalizacion = df.df_idlocalizacion " + "where df.df_norden = :nOrden")
          .addEntity(CfgServicios.class);
      query.setParameter("nOrden", nOrden);
      servicios = (CfgServicios) query.setMaxResults(1).uniqueResult();

    } catch (HibernateException he) {
      logger.error("error en getServiciosLocalizacionByNroOrden " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return servicios;
  }

  public List<CfgServicios> getServiciosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String codigo = filtros.get("codigo").toUpperCase();
    String descripcion = filtros.get("descripcion").toUpperCase();
    String activo = filtros.get("activo");
    Short idCentro = Short.parseShort(filtros.get("idCentro"));
    List<CfgServicios> list = null;
    try {
      String sqlServicios = "from CfgServicios cs" + " where cs.csCodigo like :codigo"
          + " and cs.csDescripcion like :descripcion" + " and cs.csIdservicio > 0 ";
      if (idCentro > 0) {
        sqlServicios += " and cs.cfgCentrosdesalud.ccdsIdcentrodesalud = :idCentro";
      }
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sqlServicios += " and cs.csActivo = 'S'";
      }
      sqlServicios += " order by cs.csSort asc, cs.csDescripcion asc";
      Query query = sesion.createQuery(sqlServicios);
      query.setParameter("codigo", "%" + codigo + "%");
      query.setParameter("descripcion", "%" + descripcion + "%");
      if (idCentro > 0) {
        query.setParameter("idCentro", idCentro);
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
