/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgAntecedentestest;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;
import com.grupobios.bioslis.entity.LdvIndicacionestm;
import com.grupobios.bioslis.entity.LogCfgtablas;

import java.sql.SQLException;
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
public class LdvIndicacionesTMDAO {
    
    private static Logger logger = LogManager.getLogger(LdvIndicacionesTMDAO.class);
    
   // Validar si ya existe la indicacion TM
    private  static final String getIndicacionTM = "SELECT LDVITM_DESCRIPCIONINDICACION FROM LDV_INDICACIONESTM WHERE LDVITM_DESCRIPCIONINDICACION = :desc";
    public boolean validarInsertTM(String indiNueva) {
    	//System.out.println("asdasdasdasdasd2");
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        boolean validador = false;
        try {
        	//sesion = HibernateUtil.getSessionFactory().openSession();
        	Query query = sesion.createSQLQuery(getIndicacionTM);
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

    public String insertIndicacionesTM(LdvIndicacionestm cat, Long idUsuario) throws BiosLISDAOException {
        Session sesion = null;
        String mensaje = "Indicación ya existe";
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query;
        try {
        	if (validarInsertTM(cat.getLdvitmDescripcionindicacion())) {
        		
	            sesion.beginTransaction();
	            sesion.save(cat);
	            
	            
		        //Se agregan antecedentes a log tablas **********************
	            LogCfgtablas logTabla = new LogCfgtablas();
	            LogCfgTablasDAO log = new LogCfgTablasDAO();
	            logTabla.setCfgAccionesdatos(1);
	            logTabla.setLctIdusuario(idUsuario.intValue());
	            logTabla.setLctNombretabla("LDV_INDICACIONESTM");                  
	            logTabla.setLctIdtabla(cat.getLdvitmIdindicacionestm());
	            logTabla.setLctDescripcion(cat.getLdvitmDescripcionindicacion());
	            
	            log.insertLogTablas(logTabla, sesion);
		        //*************************************************************
	            
	            
	            sesion.getTransaction().commit();
	            mensaje = "Indicación Creada exitosamente";
	            sesion.close();
        	}
        } catch (Exception he) {
        
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return mensaje;
    }

    public List<LdvIndicacionestm> getIndicacionesTM() throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("select ca"
                    + " from com.grupobios.bioslis.entity.LdvIndicacionestm ca order by ca.ldvitmDescripcionindicacion");
            List<LdvIndicacionestm> listaAntecedentes = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return listaAntecedentes;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    public String updateIndicacionesTM(LdvIndicacionestm cat, Long idUsuario) throws BiosLISDAOException {
    	String catAntiguo = this.getIndicacionesByid((long) cat.getLdvitmIdindicacionestm());
        Session sesion = null;
        String mensaje = "";
        try {
        	if (validarInsertTM(cat.getLdvitmDescripcionindicacion())) {
	            sesion = HibernateUtil.getSessionFactory().openSession();
	            sesion.beginTransaction();
	            sesion.update(cat);
	            sesion.getTransaction().commit();
	            mensaje = "Actualizado correctamente";
	            sesion.close();
	            
	            LogCfgTablasDAO log = new LogCfgTablasDAO();
	            log.validarDatos(cat.getLdvitmDescripcionindicacion(),catAntiguo, idUsuario , "LDVITM_DESCRIPCIONINDICACION",cat.getLdvitmIdindicacionestm(), "LDV_INDICACIONESTM", cat.getLdvitmDescripcionindicacion());	     
	                       
        	}else {
        		mensaje = "Indicacion existente";
        	}
        	
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
    /*
    public short getLastId() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {

            sesion.beginTransac)tion();
            Query query = sesion.createQuery("select med.ldvitmIdindicacionestm from com.grupobios.bioslis.entity.LdvIndicacionestm med "
                    + "order by med.ldvitmIdindicacionestm desc").setMaxResults(1);
            short indice;
            try {
                indice = ((short) query.list().get(0));
            } catch (Exception e) {
                indice = 1;
            }
            sesion.getTransaction().commit();
            sesion.close();
            return (short)(indice + 1);
        } catch (Exception e) {
            return 1;
        }
    }*/

    public List<LdvIndicacionestm> getListIndicacionesTM() throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("select ldvitm"
                    + " from LdvIndicacionestm ldvitm"
                    + " order by ldvitm.ldvitmDescripcionindicacion asc");
            List<LdvIndicacionestm> listaAntecedentes = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return listaAntecedentes;
        } catch (Exception he) {
            logger.error("No se pudo otner test repetidos en lista de examenes {}.", he.getMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public String getIndicacionesByid(Long id) {
    	
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String indicaciones ="";
        try {
        	
        	Query query = sesion.createSQLQuery("SELECT LDVITM.LDVITM_DESCRIPCIONINDICACION  FROM LDV_INDICACIONESTM LDVITM WHERE LDVITM.LDVITM_IDINDICACIONESTM = :id");
            query.setParameter("id", id);        
          indicaciones = (String) query.uniqueResult();
            sesion.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            if (sesion.isOpen()){
                sesion.close();
            }
        }
    	return indicaciones;
    }
}
