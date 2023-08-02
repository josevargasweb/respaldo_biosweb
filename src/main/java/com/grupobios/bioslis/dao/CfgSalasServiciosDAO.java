/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.SalasServiciosDTO;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgLocalizaciones;
import com.grupobios.bioslis.entity.CfgSalasservicios;
import com.grupobios.bioslis.entity.CfgServicios;
import com.grupobios.bioslis.entity.LogCfgtablas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Jan Perkov
 */
public class CfgSalasServiciosDAO {

  private static Logger logger = LogManager.getLogger(CfgSalasServiciosDAO.class);

  public List<Object[]> getSalasServicios(CfgServicios idServicio) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();

        int servicioId = 0;
        if (idServicio != null) {
          servicioId = idServicio.getCsIdservicio();
        }
        Query query = sesion.createSQLQuery("select *  from cfg_salasservicios where " + " CSS_IDSERVICIO =" + servicioId);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  public int getSalasServiciosById(int idServicio) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion
            .createSQLQuery("select *  from cfg_salasservicios where " + " CSS_IDSALASERVICIO =" + idServicio);
    //        Query query = sesion.createQuery("select list from com.grupobios.bioslis.entity.CfgSalasservicios list "
    //                + "where list.cssIdsalaservicio = :idServicio");
    //        query.setParameter("idServicio", idServicio);
        List<Object[]> lista = query.list();
        String list = lista.get(0)[3].toString();
        sesion.close();
        return Integer.parseInt(list);
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  /** Marco C. 07/06/20 */

  public BigDecimal obtenerNuevoID() throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createSQLQuery("SELECT NVL(MAX(CSS.CSS_IDSALASERVICIO),0)+1 FROM CFG_SALASSERVICIOS CSS");
        BigDecimal newId = (BigDecimal) query.setMaxResults(1).uniqueResult();
        sesion.close();
        return newId;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  public void agregarSalaServicio(CfgSalasservicios css, Long idUsuario) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.save(css);
        
        //Se agregan sala servicio  log tablas **********************
        LogCfgtablas logTabla = new LogCfgtablas();
        LogCfgTablasDAO log = new LogCfgTablasDAO();
        logTabla.setCfgAccionesdatos(1);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_SALASSERVICIOS");                  
        logTabla.setLctIdtabla(css.getCssIdsalaservicio());
        logTabla.setLctDescripcion(css.getCssDescripcion());
        
        log.insertLogTablas(logTabla, sesion);
        //*************************************************************
        
        
        
        sesion.getTransaction().commit();
        sesion.close();
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  public void actualizarSalaServicio(CfgSalasservicios css, Long idUsuario) throws BiosLISDAOException {
	  CfgSalasservicios cssAntiguo = this.getSalasById(css.getCssIdsalaservicio());
    Session sesion = null;
    
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.update(css);
        sesion.getTransaction().commit();
        sesion.close();
        
        LogCfgtablas logTabla = new LogCfgtablas();
        LogCfgTablasDAO log = new LogCfgTablasDAO();
        log.validarDatos(css.getCssCodigosala().toString(),  cssAntiguo.getCssCodigosala().toString() , idUsuario , "CSS_CODIGOSALA" , (int) css.getCssIdsalaservicio(), "CFG_SALASSERVICIOS", css.getCssDescripcion());
        log.validarDatos(css.getCssDescripcion(),  cssAntiguo.getCssDescripcion() , idUsuario , "CSS_DESCRIPCION" , (int) css.getCssIdsalaservicio(), "CFG_SALASSERVICIOS", css.getCssDescripcion());
        
        
        
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  public CfgSalasservicios getSalasById(int idSala) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery("from CfgSalasservicios css where css.cssIdsalaservicio = :idSala");
        query.setParameter("idSala", idSala);
        List<CfgSalasservicios> lista = query.list();
        CfgSalasservicios list = lista.get(0);
        sesion.close();
        return list;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  public CfgSalasservicios getSalasByIdFull(int idSala) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery("from CfgSalasservicios css"
                + " join fetch css.cfgServicios cs "
                + " join fetch cs.cfgCentrosdesalud ccds"
                + " where css.cssIdsalaservicio = :idSala");
        query.setParameter("idSala", idSala);
        List<CfgSalasservicios> lista = query.list();
        CfgSalasservicios list = lista.get(0);
        sesion.close();
        return list;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }
  
  public List<SalasServiciosDTO> getSalasServicios(int idServicio) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        String sql = "select * from cfg_salasservicios where CSS_IDSERVICIO = :idServicio";
        Query query = sesion.createSQLQuery(sql)
                .setResultTransformer(Transformers.aliasToBean(SalasServiciosDTO.class));
        query.setParameter("idServicio", idServicio);
        List<SalasServiciosDTO> lista = query.list();
        sesion.close();
        return lista;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }
  
  
  public CfgSalasservicios guardarSalaServicio(SalasServiciosDTO ssdto, Long idUsuario) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        CfgSalasservicios css = ssdto.transformToEntity();
        
        if (css.getCssIdsalaservicio() == 0){
            this.agregarSalaServicio(css, idUsuario);
        } else {
            this.actualizarSalaServicio(css, idUsuario);
        }
        
        CfgLocalizacionesDAO clDAO = new CfgLocalizacionesDAO();
        CfgLocalizaciones cl = clDAO.getLocalizacion(ssdto.getCSS_IDSERVICIO().intValue(), css.getCssIdsalaservicio(), 0);
        
        CfgServiciosDAO csDAO = new CfgServiciosDAO();
        CfgServicios cs = csDAO.getServiciosById(ssdto.getCSS_IDSERVICIO().intValue());
        
        if (cl!=null && cl.getClIdlocalizacion()>0){
            cl.setClIdsala(css.getCssIdsalaservicio());
            cl.setClCodigolocalizacion(css.getCssCodigosala());
            cl.setClIdservicio(cs.getCsIdservicio());
            cl.setClIdcentrodesalud((int)cs.getCfgCentrosdesalud().getCcdsIdcentrodesalud());
            clDAO.actualizarLocalizacion(cl);
        } else {
            cl = new CfgLocalizaciones();
            cl.setClIdservicio(ssdto.getCSS_IDSERVICIO().intValue());
            cl.setClIdsala(css.getCssIdsalaservicio());
            cl.setClCodigolocalizacion(css.getCssCodigosala());
            cl.setClIdcentrodesalud((int)cs.getCfgCentrosdesalud().getCcdsIdcentrodesalud());
            clDAO.agregarLocalizacion(cl);
        }
        
        return css;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

}
