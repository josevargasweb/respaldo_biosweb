/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.bioslis.entity.SigmaAnalizadores;

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
public class SigmaAnalizadoresDAO {
    
    private static Logger logger = LogManager.getLogger(SigmaAnalizadoresDAO.class);
    private static ValidacionMantenedor validador = new ValidacionMantenedor();
    
    public List<SigmaAnalizadores> getAnalizadoresFiltro(String codigo, String nombre) 
            throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "select list from SigmaAnalizadores list"
                + " where list.saCodigoanalizador like :codigo"
                + " and list.saDescripcion like :nombre"
                + " order by list.saDescripcion";
            Query query = sesion.createQuery(sql);
            query.setParameter("codigo", "%" + codigo + "%");
            query.setParameter("nombre", "%" + nombre + "%");
            List<SigmaAnalizadores> list = query.list();
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
    
    public SigmaAnalizadores getAnalizadorById(short idAnalizador) 
            throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaAnalizadores sa"
                + " where sa.saIdanalizador = :idAnalizador";
            Query query = sesion.createQuery(sql);
            query.setParameter("idAnalizador", idAnalizador);
            SigmaAnalizadores sa = (SigmaAnalizadores) query.uniqueResult();
            sesion.getTransaction().commit();
            return sa;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public String insertAnalizadores(SigmaAnalizadores sa, Long idUsuario) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
        	if (validador.validarCodigo("SA_CODIGOANALIZADOR","SIGMA_ANALIZADORES",sa.getSaCodigoanalizador())) {
	            sesion.beginTransaction();
	            sesion.save(sa);
	            
	            
    	        //Se agregan analizador a log tablas **********************
                LogCfgtablas logTabla = new LogCfgtablas();
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                logTabla.setCfgAccionesdatos(1);
                logTabla.setLctIdusuario(idUsuario.intValue());
                logTabla.setLctNombretabla("SIGMA_ANALIZADORES");                  
                logTabla.setLctIdtabla(sa.getSaIdanalizador());
                logTabla.setLctDescripcion(sa.getSaDescripcion());
                
                log.insertLogTablas(logTabla, sesion);
    	        //*************************************************************
	            
	            sesion.getTransaction().commit();
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
    
    public String updateAnalizadores(SigmaAnalizadores sa , Long idUsuario) throws BiosLISDAOException{
    	SigmaAnalizadores saAntiguo = this.getAnalizadorById(sa.getSaIdanalizador());    	
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
        	if (validador.validarxIdSinAbreviado("SA_CODIGOANALIZADOR", "SA_IDANALIZADOR", "SIGMA_ANALIZADORES", sa.getSaCodigoanalizador(), BigDecimal.valueOf(sa.getSaIdanalizador()))) {
                sesion.beginTransaction();
                sesion.update(sa);
                sesion.getTransaction().commit();
                mensaje = "C";
                sesion.close();
                
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                log.comparadorObjetos(saAntiguo, sa, 2, idUsuario.intValue(), sa.getSaIdanalizador(), sa.getSaDescripcion(), null);
                
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
    
}
