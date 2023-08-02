/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.bioslis.entity.SigmaProcesotestconversion;
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
public class SigmaProcesosTestConversionDAO {
    
    private static Logger logger = LogManager.getLogger(SigmaProcesosTestConversionDAO.class);
    
    LogCfgTablasDAO logCfgTablasDAO;
    
    
    
    public LogCfgTablasDAO getLogCfgTablasDAO() {
		return logCfgTablasDAO;
	}

	public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
		this.logCfgTablasDAO = logCfgTablasDAO;
	}

	public void insertProceso(SigmaProcesotestconversion sptc, Long idUsuario) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(sptc);
            
            
            
            
	        //Se agregan sigma proceso a log tablas **********************
            LogCfgtablas logTabla = new LogCfgtablas();
          
            logTabla.setCfgAccionesdatos(1);
            logTabla.setLctIdusuario(idUsuario.intValue());
            logTabla.setLctNombretabla("SIGMA_PROCESOTESTCONVERSION");
            logTabla.setLctNombrecampo("SPTC_NUMEROCONDICION");
            logTabla.setLctIdtabla(sptc.getId().getSptcIdproceso());
            logTabla.setLctDescripcion(sptc.getSigmaProcesostest().getSigmaProcesos().getSpDescripcion());
            logTabla.setLctValornuevo(Byte.toString(sptc.getId().getSptcNumerocondicion()));
            
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
    
    public void updateProceso(SigmaProcesotestconversion sptc) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.update(sptc);
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
    
    public List<SigmaProcesotestconversion> getConversionesFromProcesoTest(short idProceso, long idTest) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from SigmaProcesotestconversion sptc"
                    + " where sptc.id.sptcIdproceso = :idProceso"
                    + " and sptc.id.sptcIdtest = :idTest"
                    + " order by sptc.id.sptcNumerocondicion");
            query.setParameter("idProceso", idProceso);
            query.setParameter("idTest", idTest);
            sesion.getTransaction().commit();
            List<SigmaProcesotestconversion> list = query.list();
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
    
    public boolean existeConversionProcesoTest(short idProceso, long idTest, byte numeroCondicion) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from SigmaProcesotestconversion sptc"
                    + " where sptc.id.sptcIdproceso = :idProceso"
                    + " and sptc.id.sptcIdtest = :idTest"
                    + " and sptc.id.sptcNumerocondicion = :numeroCondicion");
            query.setParameter("idProceso", idProceso);
            query.setParameter("idTest", idTest);
            query.setParameter("numeroCondicion", numeroCondicion);
            sesion.getTransaction().commit();
            List<SigmaProcesotestconversion> list = query.list();
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
    
    
    public SigmaProcesotestconversion getConversionesFromProcesoTest(short idProceso , long idTest, byte numeroCondicion )throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from SigmaProcesotestconversion sptc"
                    + " where sptc.id.sptcIdproceso = :idProceso"
                    + " and sptc.id.sptcIdtest = :idTest "
                    + " and sptc.id.sptcNumerocondicion = :numeroCondicion " 
                    + " order by sptc.id.sptcNumerocondicion");
            query.setParameter("idProceso", idProceso);
            query.setParameter("idTest", idTest);
            query.setParameter("numeroCondicion", numeroCondicion);
            sesion.getTransaction().commit();
           SigmaProcesotestconversion condicion = (SigmaProcesotestconversion) query.uniqueResult();
           sesion.close();
            return condicion;
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
