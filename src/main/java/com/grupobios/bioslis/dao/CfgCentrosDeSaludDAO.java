/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.LogCfgtablas;

import java.math.BigDecimal;
import java.util.HashMap;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author marco.caracciolo
 */
public class CfgCentrosDeSaludDAO {

    private static Logger logger = LogManager.getLogger(CfgCentrosDeSaludDAO.class);
    private static ValidacionMantenedor validador = new ValidacionMantenedor();
    
    public List<CfgCentrosdesalud> getCentrosDeSalud() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgCentrosdesalud cds order by cds.ccdsDescripcion asc");
            List<CfgCentrosdesalud> lista = query.list();
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
    
    public List<CfgCentrosdesalud> getCentrosDeSaludLikeCodigo(String codigo) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgCentrosdesalud cds "
                    + "where cds.ccdsCodigo like :codigo");
            query.setParameter("codigo", "%"+codigo+"%");
            List<CfgCentrosdesalud> lista = query.list();
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
    
    public List<CfgCentrosdesalud> getCentrosDeSaludLikeNombre(String nombre) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgCentrosdesalud cds "
                    + "where cds.ccdsDescripcion like :nombre");
            query.setParameter("nombre", "%"+nombre+"%");
            List<CfgCentrosdesalud> lista = query.list();
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
    
    public List<CfgCentrosdesalud> getCentrosDeSaludFiltro(HashMap<String, String> filtros) throws BiosLISDAOException{
        Session sesion = null;
        String codigo = filtros.get("codigo").toUpperCase();
        String nombre = filtros.get("nombre").toUpperCase();
        String activo = filtros.get("activo");
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgCentrosdesalud cds"
                    + " where cds.ccdsCodigo like :codigo"
                    + " and cds.ccdsDescripcion like :nombre";
            if (activo.equals(Constante.ESTADO_ACTIVO)){
                sql += " and cds.ccdsActivo = 'S'";
            }
            sql += " order by cds.ccdsDescripcion";
            Query query = sesion.createQuery(sql);
            query.setParameter("codigo", "%"+codigo+"%");
            query.setParameter("nombre", "%"+nombre+"%");
            List<CfgCentrosdesalud> lista = query.list();
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
    
    public CfgCentrosdesalud getCentrosDeSaludById(short id) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgCentrosdesalud cds "
                    + "where ccdsIdcentrodesalud = :id");
            query.setParameter("id", id);
            CfgCentrosdesalud centro = (CfgCentrosdesalud) query.uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return centro;
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
    
    public String agregarCentroDeSalud(CfgCentrosdesalud ccds, Long idUsuario) throws BiosLISDAOException{
        Session sesion = null;
        String mensaje = "E";
        try {
        	if (validador.validarCodigoyAbreviado("CCDS_CODIGO", "CCDS_DESCRIPCION", "CFG_CENTROSDESALUD", ccds.getCcdsCodigo(), ccds.getCcdsDescripcion())) {
                sesion = HibernateUtil.getSessionFactory().openSession();
                short idInsti = 1;
                ccds.setCcdsIdinstituciondesalud(idInsti);
                sesion.beginTransaction();
                sesion.save(ccds);
                
                //Se agregan centro de salud muestra a log tablas **********************
                LogCfgtablas logTabla = new LogCfgtablas();
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                logTabla.setCfgAccionesdatos(1);
                logTabla.setLctIdusuario(idUsuario.intValue());
                logTabla.setLctNombretabla("CFG_CENTROSDESALUD");                  
                logTabla.setLctIdtabla(ccds.getCcdsIdcentrodesalud());
                logTabla.setLctDescripcion(ccds.getCcdsDescripcion());
                
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
    
    public String actualizarCentroDeSalud(CfgCentrosdesalud ccds, Long idUsuario) throws BiosLISDAOException{
    	CfgCentrosdesalud ccdsAntiguo = this.getCentrosDeSaludById(ccds.getCcdsIdcentrodesalud());
        Session sesion = null;
        String mensaje = "E";
        try {
        	BigDecimal id = new BigDecimal(ccds.getCcdsIdcentrodesalud());
        	if (validador.validarxId("CCDS_CODIGO", "CCDS_IDCENTRODESALUD","CCDS_DESCRIPCION", "CFG_CENTROSDESALUD", ccds.getCcdsCodigo(), ccds.getCcdsDescripcion(), id)) {
                sesion = HibernateUtil.getSessionFactory().openSession();
                sesion.beginTransaction();
                short idInsti = 1;
                ccds.setCcdsIdinstituciondesalud(idInsti);
                sesion.update(ccds);
                sesion.getTransaction().commit();
                mensaje = "C";
                sesion.close();
                
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                log.comparadorObjetos(ccdsAntiguo, ccds, 2, idUsuario.intValue(), ccds.getCcdsIdcentrodesalud(), ccds.getCcdsDescripcion(), null);
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
    
    public List<CfgCentrosdesalud> existeCentrosdesalud(String codigo) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from com.grupobios.bioslis.entity.CfgCentrosdesalud ccds"
                    + " where ccds.ccdsCodigo = :codigo";
            Query query = sesion.createQuery(sql);
            query.setParameter("codigo", codigo);
            List<CfgCentrosdesalud> list = query.list();
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
}
