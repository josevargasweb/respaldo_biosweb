/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.CamasSalasDTO;
import com.grupobios.bioslis.entity.CfgCamassalas;
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
public class CfgCamasSalasDAO {

  private static Logger logger = LogManager.getLogger(CfgCamasSalasDAO.class);

  public List<Object[]> getCamasSalas(int idServicioSalas) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion
            .createSQLQuery("select *  from cfg_camassalas where " + " CCS_IDSALASERVICIO =" + idServicioSalas);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  // Nuevos Metodos Marco C

  public CfgCamassalas getCamasById(int idCama) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery("from CfgCamassalas ccs where ccs.ccsIdcamasala = :idCama");
        query.setParameter("idCama", idCama);
        List<CfgCamassalas> lista = query.list();
        CfgCamassalas list = lista.get(0);
        sesion.close();
        return list;
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  public void agregarCama(CfgCamassalas ccs, Long idUsuario) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.save(ccs);

        //Se agregan secciones a log tablas **********************
        LogCfgtablas logTabla = new LogCfgtablas();
        LogCfgTablasDAO log = new LogCfgTablasDAO();
        logTabla.setCfgAccionesdatos(1);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_CAMASSALAS");                  
        logTabla.setLctIdtabla(ccs.getCcsIdcamasala());
        
        log.insertLogTablas(logTabla, sesion);
        //*************************************************************
        
        sesion.getTransaction().commit();
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
        
  }

  public void actualizarCama(CfgCamassalas ccs, Long idUsuario) throws BiosLISDAOException {
	  CfgCamassalas ccsAntiguo = this.getCamasById(ccs.getCcsIdcamasala());
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.update(ccs);
        sesion.getTransaction().commit();
        sesion.close();
        
        
        LogCfgtablas logTabla = new LogCfgtablas();
        LogCfgTablasDAO log = new LogCfgTablasDAO();
        log.validarDatos(ccs.getCcsCodigocama(),  ccsAntiguo.getCcsCodigocama() , idUsuario , "CCS_CODIGOCAMA" , (int) ccs.getCcsIdcamasala(), "CFG_CAMASSALAS", ccs.getCcsDescripcion());
        log.validarDatos(ccs.getCcsDescripcion(),  ccsAntiguo.getCcsDescripcion() , idUsuario , "CCS_DESCRIPCION" , (int) ccs.getCcsIdcamasala(), "CFG_CAMASSALAS", ccs.getCcsDescripcion());
        
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
        
  }

  public List<CamasSalasDTO> getCamasSala(int idSala) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion
            .createSQLQuery("select *  from cfg_camassalas where CCS_IDSALASERVICIO = :idSala")
                .setResultTransformer(Transformers.aliasToBean(CamasSalasDTO.class));
        query.setParameter("idSala", idSala);
        List<CamasSalasDTO> lista = query.list();
        sesion.close();
        return lista;
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }
  
  public CfgCamassalas guardarCamaSala(CamasSalasDTO csdto, Long idUsuario) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        CfgCamassalas ccs = csdto.transformToEntity();
        if (ccs.getCcsIdcamasala() == 0){
            this.agregarCama(ccs, idUsuario);
        } else {
            this.actualizarCama(ccs, idUsuario);
        }
        
        CfgSalasServiciosDAO cssDAO = new CfgSalasServiciosDAO();
        CfgSalasservicios salas = cssDAO.getSalasByIdFull(csdto.getCCS_IDSALASERVICIO().intValue());
        
        CfgLocalizacionesDAO clDAO = new CfgLocalizacionesDAO();
        CfgLocalizaciones cl = clDAO.getLocalizacion(salas.getCfgServicios().getCsIdservicio(), salas.getCssIdsalaservicio(), ccs.getCcsIdcamasala());
        
        if (cl!=null && cl.getClIdlocalizacion()>0){
            cl.setClIdsala(ccs.getCfgSalasservicios().getCssIdsalaservicio());
            cl.setClIdcama(ccs.getCcsIdcamasala());
            cl.setClCodigolocalizacion(ccs.getCcsCodigocama());
            cl.setClIdcentrodesalud((int) salas.getCfgServicios().getCfgCentrosdesalud().getCcdsIdcentrodesalud());
            clDAO.actualizarLocalizacion(cl);
        } else {
            cl = new CfgLocalizaciones();
            cl.setClIdservicio(salas.getCfgServicios().getCsIdservicio());
            cl.setClIdsala(ccs.getCfgSalasservicios().getCssIdsalaservicio());
            cl.setClIdcama(ccs.getCcsIdcamasala());
            cl.setClCodigolocalizacion(ccs.getCcsCodigocama());
            cl.setClIdcentrodesalud((int) salas.getCfgServicios().getCfgCentrosdesalud().getCcdsIdcentrodesalud());
            clDAO.agregarLocalizacion(cl);
        }
        
        
        return ccs;
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
