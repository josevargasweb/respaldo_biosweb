/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.ProcesosTestsDTO;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.bioslis.entity.SigmaProcesostest;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco Caracciolo
 */
public class SigmaProcesosTestDAO {
    
    private static Logger logger = LogManager.getLogger(SigmaProcesosTestDAO.class);
    
    LogCfgTablasDAO logCfgTablasDAO; 
    CfgTestDAO cfgTestDAO;
    
    
    
    
    
    
    public CfgTestDAO getCfgTestDAO() {
		return cfgTestDAO;
	}

	public void setCfgTestDAO(CfgTestDAO cfgTestDAO) {
		this.cfgTestDAO = cfgTestDAO;
	}

	public LogCfgTablasDAO getLogCfgTablasDAO() {
		return logCfgTablasDAO;
	}

	public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
		this.logCfgTablasDAO = logCfgTablasDAO;
	}

	public void insertProcesotest(SigmaProcesostest spt, Long idUsuario) throws BiosLISDAOException{
		CfgTest test = new CfgTest();
		try {
			 test = cfgTestDAO.getTestById((int) spt.getId().getSptIdtest());
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
            sesion.save(spt);
            
            
	        //Se agrega proceso test  a log tablas **********************
            LogCfgtablas logTabla = new LogCfgtablas();
            
            logTabla.setCfgAccionesdatos(2);
            logTabla.setLctIdusuario(idUsuario.intValue());
            logTabla.setLctNombretabla("SIGMA_PROCESOSTEST");   
            logTabla.setLctNombrecampo("SPT_IDPROCESO");
            logTabla.setLctValornuevo(test.getCtAbreviado());
            logTabla.setLctIdtabla(spt.getId().getSptIdproceso());
            logTabla.setLctDescripcion(spt.getSigmaProcesos().getSpDescripcion());
            
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
    
    public void updateProcesotest(SigmaProcesostest spt, Long idUsuario) throws BiosLISDAOException{
    	SigmaProcesostest sptAntiguo = this.getProcesosTestByProcesoAndTest(spt.getId().getSptIdproceso(), spt.getId().getSptIdtest() );
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.update(spt);
            sesion.getTransaction().commit();
            sesion.close();
            
            logCfgTablasDAO.compararTablasSigmaProcesosTest(spt, sptAntiguo, idUsuario,spt.getSigmaProcesos().getSpDescripcion());
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public List<SigmaProcesostest> getProcesosTestById(short idProceso) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaProcesostest spt"
                    + " where spt.id.sptIdproceso = :idProceso";
            Query query = sesion.createQuery(sql);
            query.setParameter("idProceso", idProceso);
            List<SigmaProcesostest> list = query.list();
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
    
    public boolean existeProcesoTest(short idProceso, long idTest) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaProcesostest spt"
                    + " where spt.id.sptIdproceso = :idProceso"
                    + " and spt.id.sptIdtest = :idTest";
            Query query = sesion.createQuery(sql);
            query.setParameter("idProceso", idProceso);
            query.setParameter("idTest", idTest);
            List<SigmaProcesostest> list = query.list();
            sesion.getTransaction().commit();
            return !list.isEmpty();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    public SigmaProcesostest getProcesosTestByProcesoAndTest(short idProceso, long idTest) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaProcesostest spt"
                    + " where spt.id.sptIdproceso = :idProceso and spt.id.sptIdtest = :idTest";
            Query query = sesion.createQuery(sql);
            query.setParameter("idProceso", idProceso);
            query.setParameter("idTest", idTest);
            SigmaProcesostest list = (SigmaProcesostest) query.uniqueResult();
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
}
