/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgCausasrechazosmuestras;
import com.grupobios.bioslis.entity.LogCfgtablas;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author marco.c
 */
public class CfgCausasrechazosmuestrasDAO {
    
    private static Logger logger = LogManager.getLogger(CfgCausasrechazosmuestrasDAO.class);
    private static ValidacionMantenedor validador = new ValidacionMantenedor();
    
    public List<CfgCausasrechazosmuestras> getCausasRechazoMuestras() throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgCausasrechazosmuestras ccrm "
                    + "where ccrm.ccrmIdcausarechazo > 1 "
                    //+ "order by ccrm.ccrmSort, ccrm.ccrmIdcausarechazo");
                    + "order by ccrm.ccrmSort asc, ccrm.ccrmDescripcion asc");
            List<CfgCausasrechazosmuestras> lista = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return lista;
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
    /*
    public List<CfgCausasrechazosmuestras> getCausasRechazoLikeCodigo(String codigo){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion.createQuery("from CfgCausasrechazosmuestras ccrm "
                + "where ccrm.ccrmCodigo like :codigo and ccrm.ccrmIdcausarechazo > 1 "
                + "order by ccrm.ccrmSort, ccrm.ccrmIdcausarechazo");
        query.setParameter("codigo", "%" + codigo + "%");
        List<CfgCausasrechazosmuestras> lista = query.list();
        sesion.getTransaction().commit();
        sesion.close();
        return lista;
    }
    
    public List<CfgCausasrechazosmuestras> getCausasRechazoLikeDesc(String descripcion){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion.createQuery("from CfgCausasrechazosmuestras ccrm "
                + "where ccrm.ccrmDescripcion like :descripcion and ccrm.ccrmIdcausarechazo > 1 "
                + "order by ccrm.ccrmSort, ccrm.ccrmIdcausarechazo");
        query.setParameter("descripcion", "%" + descripcion + "%");
        List<CfgCausasrechazosmuestras> lista = query.list();
        sesion.getTransaction().commit();
        sesion.close();
        return lista;
    }
    */
    public CfgCausasrechazosmuestras getCausaRechazoMuestraById(short id) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgCausasrechazosmuestras ccrm "
                    + "where ccrm.ccrmIdcausarechazo = :id");
            query.setParameter("id", id);
            CfgCausasrechazosmuestras ccrm = (CfgCausasrechazosmuestras) query.setMaxResults(1).uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return ccrm;
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
    /*
    public BigDecimal obtenerNuevoId(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion.createSQLQuery("select NVL(max(ccrm.ccrm_idcausarechazo),0)+1 newID from cfg_causasrechazosmuestras ccrm");
        BigDecimal newID = (BigDecimal) query.setMaxResults(1).uniqueResult();
        sesion.getTransaction().commit();
        sesion.close();
        return newID;
    }
    */
    public String agregarCausaRechazoMuestras(CfgCausasrechazosmuestras ccrm, Long idUsuario) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
    		ccrm.setCcrmCodigo(ccrm.getCcrmCodigo().toUpperCase());
    		ccrm.setCcrmDescripcion(ccrm.getCcrmDescripcion().toUpperCase());
        	if (validador.validarCodigoyAbreviado("CCRM_CODIGO", "CCRM_DESCRIPCION", "CFG_CAUSASRECHAZOSMUESTRAS", ccrm.getCcrmCodigo(), ccrm.getCcrmDescripcion())) {

                sesion.beginTransaction();
                sesion.save(ccrm);
                
                
    	        //Se agregan causa de rechazo muestra a log tablas **********************
                LogCfgtablas logTabla = new LogCfgtablas();
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                logTabla.setCfgAccionesdatos(1);
                logTabla.setLctIdusuario(idUsuario.intValue());
                logTabla.setLctNombretabla("CFG_CAUSASRECHAZOSMUESTRAS");                  
                logTabla.setLctIdtabla(ccrm.getCcrmIdcausarechazo());
                logTabla.setLctDescripcion(ccrm.getCcrmDescripcion());
                
                log.insertLogTablas(logTabla, sesion);
    	        //*************************************************************
                
                
                sesion.getTransaction().commit();
                mensaje = "C";
			}

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
        return mensaje;
    }
    
    public String actualizarCausaRechazoMuestras(CfgCausasrechazosmuestras ccrm, Long idUsuario) throws BiosLISDAOException{
    	CfgCausasrechazosmuestras ccrmAntiguo = this.getCausaRechazoMuestraById(ccrm.getCcrmIdcausarechazo());
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
        	BigDecimal id = new BigDecimal(ccrm.getCcrmIdcausarechazo());
    		ccrm.setCcrmCodigo(ccrm.getCcrmCodigo().toUpperCase());
    		ccrm.setCcrmDescripcion(ccrm.getCcrmDescripcion().toUpperCase());
        	if (validador.validarxId("CCRM_CODIGO", "CCRM_IDCAUSARECHAZO", "CCRM_DESCRIPCION", "CFG_CAUSASRECHAZOSMUESTRAS", ccrm.getCcrmCodigo(), ccrm.getCcrmDescripcion(), id)) {
        		
                sesion.beginTransaction();
                sesion.update(ccrm);
                sesion.getTransaction().commit();
                mensaje = "C";
                sesion.close();
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                log.comparadorObjetos(ccrmAntiguo, ccrm, 2, idUsuario.intValue(), ccrm.getCcrmIdcausarechazo(), ccrm.getCcrmDescripcion(), null);
                
			}
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
        return mensaje;
    }
    
    public List<CfgCausasrechazosmuestras> getCausasRechazoFiltro(HashMap<String, String> filtros) throws BiosLISDAOException{
        String codigo = filtros.get("codigo").toUpperCase();
        String descripcion = filtros.get("descripcion").toUpperCase();
        String activo = filtros.get("activo");
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String sqlCausasRechazoFilter = "from CfgCausasrechazosmuestras ccrm"
                    + " where ccrm.ccrmCodigo like :codigo"
                    + " and ccrm.ccrmDescripcion like :descripcion"
                    + " and ccrm.ccrmIdcausarechazo > 1";
            if (activo.equals(Constante.ESTADO_ACTIVO)){
                sqlCausasRechazoFilter += " and ccrm.ccrmActivo = 'S'";
            }
            sqlCausasRechazoFilter += " order by ccrm.ccrmSort asc, ccrm.ccrmDescripcion asc";
            Query query = sesion.createQuery(sqlCausasRechazoFilter);
            query.setParameter("codigo", "%" + codigo + "%");
            query.setParameter("descripcion", "%" + descripcion + "%");
            List<CfgCausasrechazosmuestras> lista = query.list();
            sesion.close();
            return lista;
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
