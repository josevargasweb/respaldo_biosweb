/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;


import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgExamenesPaneles;
import com.grupobios.bioslis.entity.LogCfgtablas;

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
public class CfgExamenesPanelesDAO {
    
    private static Logger logger = LogManager.getLogger(CfgExamenesPanelesDAO.class);
    
    
    public void insertExamenesPanel(CfgExamenesPaneles cep, Long idUsuario, String  nombrePanel) throws BiosLISDAOException{
    	CfgExamenesDAO examenesDAO = new CfgExamenesDAO();
    	CfgExamenes examen = new CfgExamenes();
    	try {
		examen	= examenesDAO.getExamenById(cep.getCfgExamenesPanelesId().getCepIdexamen());
		} catch (BiosLISDAONotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiosLISDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Session sesion = HibernateUtil.getSessionFactory().openSession();

        try {
                sesion.beginTransaction();
                sesion.save(cep);
                
                
                //Se agregan examenes a panel a log tablas **********************
                LogCfgtablas logTabla = new LogCfgtablas();
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                logTabla.setCfgAccionesdatos(1);
                logTabla.setLctIdusuario(idUsuario.intValue());
                logTabla.setLctNombretabla("CFG_PANELESEXAMENES");  
                logTabla.setLctNombrecampo("CEP_IDEXAMEN");
                logTabla.setLctIdtabla((int) cep.getCfgExamenesPanelesId().getCepIdpanelexamenes());
                logTabla.setLctValornuevo(examen.getCeAbreviado());
                logTabla.setLctDescripcion(nombrePanel);
                
                
                log.insertLogTablas(logTabla, sesion);
    	        //*************************************************************
                
                sesion.getTransaction().commit();
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
    }
    
    public void deleteExamenPanel(CfgExamenesPaneles cep, Long idUsuario, String nombrePanel) throws BiosLISDAOException{
    	CfgExamenesDAO examenesDAO = new CfgExamenesDAO();
    	CfgExamenes examen = new CfgExamenes();  	
    	
    	int idPanel = cep.getCfgExamenesPanelesId().getCepIdpanelexamenes();
    	try {
    		examen	= examenesDAO.getExamenById(cep.getCfgExamenesPanelesId().getCepIdexamen());
    		} catch (BiosLISDAONotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (BiosLISDAOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.delete(cep);
            
            //Se agregan examene eliminados a panel  a log tablas **********************
            LogCfgtablas logTabla = new LogCfgtablas();
            LogCfgTablasDAO log = new LogCfgTablasDAO();
            logTabla.setCfgAccionesdatos(3);
            logTabla.setLctIdusuario(idUsuario.intValue());
            logTabla.setLctNombretabla("CFG_PANELESEXAMENES");    
            logTabla.setLctNombrecampo("CEP_IDEXAMEN");
            logTabla.setLctIdtabla(idPanel);
            logTabla.setLctValoranterior(examen.getCeAbreviado());
            logTabla.setLctDescripcion(nombrePanel);
            
            
            log.insertLogTablas(logTabla, sesion);
	        //*************************************************************
            sesion.getTransaction().commit();
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
    }
    
    public List<CfgExamenesPaneles> listaExamenesPanel(Short idPanel) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String sql = "from CfgExamenesPaneles cep "
                    + "join fetch cep.cfgExamenes ce "
                    + "where cep.cfgExamenesPanelesId.cepIdpanelexamenes = :idPanel "
                    + "order by cep.cepSort, ce.ceDescripcion";
            Query query = sesion.createQuery(sql);
            query.setParameter("idPanel", idPanel);
            List<CfgExamenesPaneles> list = query.list();
            sesion.close();
            return list;
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
    
    public CfgExamenesPaneles buscarExamenPanel(Short idPanel, Long idExamen) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String sql = "from CfgExamenesPaneles cep "
                    + "where cep.cfgExamenesPanelesId.cepIdpanelexamenes = :idPanel "
                    + "and cep.cfgExamenesPanelesId.cepIdexamen = :idExamen";
            Query query = sesion.createQuery(sql);
            query.setParameter("idPanel", idPanel);
            query.setParameter("idExamen", idExamen);
            CfgExamenesPaneles examen = (CfgExamenesPaneles) query.uniqueResult();
            sesion.close();
            return examen;
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
    
}
