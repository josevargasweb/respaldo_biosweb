/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.bioslis.entity.SigmaProcesos;
import java.math.BigDecimal;
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
public class SigmaProcesosDAO {
    
    private static Logger logger = LogManager.getLogger(SigmaProcesosDAO.class);
    private static ValidacionMantenedor validador = new ValidacionMantenedor();
    
   LogCfgTablasDAO logCfgTablasDAO; 	
	
	
    
    public LogCfgTablasDAO getLogCfgTablasDAO() {
	return logCfgTablasDAO;
}

public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
	this.logCfgTablasDAO = logCfgTablasDAO;
}

	public List<SigmaProcesos> getProcesosFiltro(String codigo, String nombre, short idSeccion, short idEstacion, String activo)
            throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaProcesos sp "            	       	
                    + " where sp.spCodigoproceso like :codigo"
                    + " and sp.spDescripcion like :nombre";
            if (idSeccion > 0){
                sql += " and sp.spIdseccion = :idSeccion";
            }
            if (idEstacion > 0){
                sql += " and sp.spEstacionsigma = :idEstacion";
            }
            if (activo.equals("S")){
                sql += " and sp.spActivo = 'S'";
            }
            sql += " order by sp.spSort,sp.spDescripcion";
            Query query = sesion.createQuery(sql);
            query.setParameter("codigo", "%" + codigo + "%");
            query.setParameter("nombre", "%" + nombre + "%");
            if (idSeccion > 0){
                query.setParameter("idSeccion", idSeccion);
            }
            if (idEstacion > 0){
                query.setParameter("idEstacion", idEstacion);
            }
            List<SigmaProcesos> list = query.list();
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
    
    public SigmaProcesos getProcesoById(String codigoProceso) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaProcesos sp"
                    + " where sp.spCodigoproceso = :codigoProceso";
            Query query = sesion.createQuery(sql);
            query.setParameter("codigoProceso", codigoProceso);
            SigmaProcesos sp = (SigmaProcesos) query.uniqueResult();
            return sp;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    
    public SigmaProcesos getSigmaProcesoById(short id) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaProcesos sp"
                    + " where sp.spIdproceso = :id";
            Query query = sesion.createQuery(sql);
            query.setParameter("id", id);
            SigmaProcesos sp = (SigmaProcesos) query.uniqueResult();
            sesion.close();
            return sp;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    
    public String insertProceso(SigmaProcesos sp, Long idUsuario) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
        	if (validador.validarCodigo("SP_CODIGOPROCESO", "SIGMA_PROCESOS", sp.getSpCodigoproceso())) {
	            sesion.beginTransaction();
	            sesion.save(sp);
	            
	            
	            
    	        //Se agregan sigma proceso a log tablas **********************
                LogCfgtablas logTabla = new LogCfgtablas();
              
                logTabla.setCfgAccionesdatos(2);
                logTabla.setLctIdusuario(idUsuario.intValue());
                logTabla.setLctNombretabla("SIGMA_PROCESOS");                  
                logTabla.setLctIdtabla(sp.getSpIdproceso());
                logTabla.setLctDescripcion(sp.getSpDescripcion());
                
                logCfgTablasDAO.insertLogTablas(logTabla, sesion);
    	        //*************************************************************
	            
	            
	            sesion.getTransaction().commit();
	            sesion.close();
	            mensaje = "C";
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
    
    public void updateProceso(SigmaProcesos sp, Long idUsuario) throws BiosLISDAOException{
    	SigmaProcesos spAntiguo = this.getSigmaProcesoById(sp.getSpIdproceso());
    
    	
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
        	if (sp.getSpSort() == 0) {
        		sp.setSpSort((short)1);
			}
            sesion.beginTransaction();
            sesion.update(sp);
            sesion.getTransaction().commit();
            sesion.close();
            System.out.println("entre a comparar tablas ");
            logCfgTablasDAO.compararTablasSigmaProcesos(sp, spAntiguo, idUsuario, sp.getSpDescripcion());
            
            
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }

    }
    
    public short obtenerIdSecuencia() throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            return ((BigDecimal) sesion.createSQLQuery("SELECT SEQ_SIGMA_PROCESOS.NEXTVAL FROM DUAL")
                    .uniqueResult()).shortValue();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public List<SigmaProcesos> getProcesoByCodigo(String codigoProceso) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createQuery("from SigmaProcesos"
                    + " where spCodigoproceso = :codigoProceso");
            query.setParameter("codigoProceso", codigoProceso);
            List<SigmaProcesos> list = query.list();
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
