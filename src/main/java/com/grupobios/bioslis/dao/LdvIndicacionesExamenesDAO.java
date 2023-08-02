/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgAntecedentestest;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;
import com.grupobios.bioslis.entity.LogCfgtablas;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nacho
 */
public class LdvIndicacionesExamenesDAO {

    private static Logger logger = LogManager.getLogger(LdvIndicacionesExamenesDAO.class);
    private  static final String getIndicacionPaciente = "SELECT LDVIE_DESCRIPCION FROM LDV_INDICACIONESEXAMENES WHERE LDVIE_DESCRIPCION = :desc";
   
    LogCfgTablasDAO logCfgTablasDAO;
    
    
    
    
    
    public LogCfgTablasDAO getLogCfgTablasDAO() {
		return logCfgTablasDAO;
	}
	public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
		this.logCfgTablasDAO = logCfgTablasDAO;
	}
	//Validar si ya existe la indicacion para el paciente
    public boolean validarInsertIP(String indiNueva) {
    	
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        boolean validador = false;
        try {
        	//sesion = HibernateUtil.getSessionFactory().openSession();
        	Query query = sesion.createSQLQuery(getIndicacionPaciente);
            query.setParameter("desc", indiNueva);
            
            validador = query.list().isEmpty();
                      
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
            if (sesion.isOpen()){
                sesion.close();
            }
        }
    	return validador;
    }
    public String insertIndicacionesExamen(LdvIndicacionesexamenes cat, Long idUsuario) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "Indicación ya existe";
        try {
        	if (validarInsertIP(cat.getLdvieDescripcion())) {
        		
	            sesion.beginTransaction();
	            sesion.save(cat);
	            
	            

	            
		        //Se agregan indicaciones  log tablas **********************
	            LogCfgtablas logTabla = new LogCfgtablas();
	            LogCfgTablasDAO log = new LogCfgTablasDAO();
	            logTabla.setCfgAccionesdatos(1);
	            logTabla.setLctIdusuario(idUsuario.intValue());
	            logTabla.setLctNombretabla("LDV_INDICACIONESEXAMENES");                  
	            logTabla.setLctIdtabla((int) cat.getLdvieIdindicacionesexamen());
	            logTabla.setLctDescripcion(cat.getLdvieDescripcion());
	            
	            log.insertLogTablas(logTabla, sesion);
		        //*************************************************************
	            
	            
	            sesion.getTransaction().commit();
	            mensaje = "Indicación Creada exitosamente";	            
	            sesion.close();
        	}
        } catch (Exception he) {
        	he.printStackTrace();
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()){
                sesion.close();
            }
        }
        return mensaje;
    }
    
    public String updateIndicacionesExamen(LdvIndicacionesexamenes cat, Long idUsuario) throws BiosLISDAOException {
    	LdvIndicacionesexamenes catAntiguo =  this.getIndicacionesExamen(cat.getLdvieIdindicacionesexamen());
    	LogCfgTablasDAO log = new LogCfgTablasDAO();
        Session sesion = null;
        String mensaje = "";
        try {
        	if (validarInsertIP(cat.getLdvieDescripcion())) {
	            sesion = HibernateUtil.getSessionFactory().openSession();
	            sesion.beginTransaction();
	            cat.setLdvieDescripcion(cat.getLdvieDescripcion().toUpperCase());
	            sesion.update(cat);
	            sesion.getTransaction().commit();
	            sesion.close();
	          
	            log.validarDatos(cat.getLdvieDescripcion() , catAntiguo.getLdvieDescripcion() , idUsuario , "LDVIE_DESCRIPCION" , (int) cat.getLdvieIdindicacionesexamen(), "LDV_INDICACIONESEXAMENES",cat.getLdvieDescripcion() );
	        	
	            mensaje = "Actualizado correctamente";
        	}
        	mensaje = "Indicacion existente";
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
        return mensaje;
    }

    public List<LdvIndicacionesexamenes> getIndicacionesExamen(String descripcion) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("select ca"
                    + " from com.grupobios.bioslis.entity.LdvIndicacionesexamenes ca"
                    + " where ca.ldvieDescripcion like '%"+descripcion.toUpperCase()+"%' order by ca.ldvieDescripcion");
            List<LdvIndicacionesexamenes> listaAntecedentes = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return listaAntecedentes;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
    }
    /*
        public byte getLastId() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {

            sesion.beginTransaction();
            Query query = sesion.createQuery("select med.ldvieIdindicacionesexamen from com.grupobios.bioslis.entity.LdvIndicacionesexamenes med "
                    + "order by med.ldvieIdindicacionesexamen desc").setMaxResults(1);
            byte indice;
            try {
                indice = ((byte) query.list().get(0));
            } catch (Exception e) {
                indice = 1;
            }
            sesion.getTransaction().commit();
            sesion.close();
            return  (byte)(indice + 1);
        } catch (Exception e) {
            return 1;
        }
    }*/

    public List<LdvIndicacionesexamenes> getListIndicacionesExamen() throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("select ca"
                    + " from com.grupobios.bioslis.entity.LdvIndicacionesexamenes ca"
                    + " order by ldvieDescripcion");
            List<LdvIndicacionesexamenes> listaAntecedentes = query.list();
            sesion.getTransaction().commit();
            return listaAntecedentes;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()){
                sesion.close();
            }
        }
    }
    
    public LdvIndicacionesexamenes getIndicacionesExamen(Long idIndicaciones) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("select ca"
                    + " from com.grupobios.bioslis.entity.LdvIndicacionesexamenes ca"
                    + " where ca.ldvieIdindicacionesexamen = :idIndicaciones"
                    + " order by ldvieDescripcion");
            query.setParameter("idIndicaciones", idIndicaciones);
            LdvIndicacionesexamenes indicacionesexamenes = (LdvIndicacionesexamenes) query.uniqueResult();
            sesion.getTransaction().commit();
            return indicacionesexamenes;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()){
                sesion.close();
            }
        }
    }
    
    public LdvIndicacionesexamenes getIndicacionesExamenByIdExamen(long idExamen) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createSQLQuery("select * from BIOSLIS.ldv_indicacionesexamenes lie"
                    + " join cfg_examenes ce on ce.ce_idindicacionexamen = lie.ldvie_idindicacionesexamen "
                    + " where ce.ce_idexamen = :idExamen").addEntity(LdvIndicacionesexamenes.class);
            query.setParameter("idExamen", idExamen);
            LdvIndicacionesexamenes indicacionesexamenes = (LdvIndicacionesexamenes) query.uniqueResult();
            sesion.getTransaction().commit();
            return indicacionesexamenes;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()){
                sesion.close();
            }
        }
    }
    
}
