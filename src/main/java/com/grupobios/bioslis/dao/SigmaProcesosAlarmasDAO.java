/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.bioslis.entity.SigmaProcesosalarmas;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco Caracciolo
 */
public class SigmaProcesosAlarmasDAO {
    
    private static Logger logger = LogManager.getLogger(SigmaProcesosAlarmasDAO.class);
    
    LogCfgTablasDAO logCfgTablasDAO;
    
    
    
    
    public LogCfgTablasDAO getLogCfgTablasDAO() {
		return logCfgTablasDAO;
	}

	public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
		this.logCfgTablasDAO = logCfgTablasDAO;
	}

	public List<SigmaProcesosalarmas> getAlarmasByProceso(String codigoProceso) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaProcesosalarmas spa"
                    + " where spa.sigmaProcesos.spCodigoproceso = :codigoProceso";
            Query query = sesion.createQuery(sql);
            query.setParameter("codigoProceso", codigoProceso);
            List<SigmaProcesosalarmas> list = query.list();
            sesion.getTransaction().commit();
            return list;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public SigmaProcesosalarmas getAlarmasById(short idAlarma) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaProcesosalarmas spa"
                    + " where spa.spaIdprocesoalarma = :idAlarma";
            Query query = sesion.createQuery(sql);
            query.setParameter("idAlarma", idAlarma);
            SigmaProcesosalarmas alarma = (SigmaProcesosalarmas) query.uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return alarma;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void insertAlarma(SigmaProcesosalarmas spa, Long idUsuario) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(spa);
            
            //Se agrega alarma  a log tablas **********************
            LogCfgtablas logTabla = new LogCfgtablas();
            LogCfgTablasDAO logCfgTablasDAO = new LogCfgTablasDAO();
            logTabla.setCfgAccionesdatos(1);
            logTabla.setLctIdusuario(idUsuario.intValue());
            logTabla.setLctNombretabla("SIGMA_PROCESOSALARMAS");   
            logTabla.setLctNombrecampo("SPA_IDPROCESOALARMA");
            logTabla.setLctValornuevo(spa.getSpaAlarmadescripcion());
            logTabla.setLctIdtabla(spa.getSigmaProcesos().getSpIdproceso());
            logTabla.setLctDescripcion(spa.getSigmaProcesos().getSpDescripcion());
            
            logCfgTablasDAO.insertLogTablas(logTabla, sesion);
	        //*************************************************************
            
            
            
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }

    }
    
    public void updateAlarma(SigmaProcesosalarmas spa , Long idUsuario) throws BiosLISDAOException{    	
    	SigmaProcesosalarmas sigmaAntiguo = new SigmaProcesosalarmas();
    	    	
    	sigmaAntiguo = this.getAlarmasById(spa.getSpaIdprocesoalarma());    	;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.update(spa);
            sesion.getTransaction().commit();
            sesion.close();          
            
           
            logCfgTablasDAO.compararTablasSigmaProcesoAlarmas(spa, sigmaAntiguo,  idUsuario,spa.getSigmaProcesos().getSpDescripcion()); 
            
            
        } catch (HibernateException he) {
        	he.printStackTrace();
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }

    }
    
}
