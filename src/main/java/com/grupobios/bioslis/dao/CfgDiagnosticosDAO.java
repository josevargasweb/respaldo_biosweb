/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgDiagnosticos;
import com.grupobios.bioslis.entity.LdvCie10;
import com.grupobios.bioslis.entity.LogCfgtablas;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


public class CfgDiagnosticosDAO {

    private static Logger logger = LogManager.getLogger(CfgDiagnosticosDAO.class);
    private static ValidacionMantenedor validador = new ValidacionMantenedor();
    
    public List<CfgDiagnosticos> getDiagnosticos() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("select list.cdIddiagnostico,list.cdDescripcion from com.grupobios.bioslis.entity.CfgDiagnosticos list where list.cdIddiagnostico >= 1"
            		+ " order by case when list.cdIddiagnostico = 1 then 0 else 1 end, list.cdSort asc, list.cdDescripcion asc");
            List<CfgDiagnosticos> lista = query.list();
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
    
    public List<CfgDiagnosticos> getDiagnosticosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        //String codigo = filtros.get("codigo").toUpperCase();
        String descripcion = filtros.get("descripcion").toUpperCase();
        String activo = filtros.get("activo");
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String sql = "from CfgDiagnosticos cd"
                    + " left join fetch cd.ldvCie10 lc"
                    + " where cd.cdIddiagnostico >= 1"
                    + " and cd.cdDescripcion like :descripcion";
            if (activo.equals(Constante.ESTADO_ACTIVO)){
                sql += " and cd.cdActivo = 'S'";
            }
            sql += " order by cd.cdSort asc, cd.cdDescripcion asc";
            Query query = sesion.createQuery(sql);
            //query.setParameter("codigo", "%" + codigo + "%");
            query.setParameter("descripcion", "%" + descripcion + "%");
            List<CfgDiagnosticos> list = query.list();
            sesion.close();
            return list;
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public CfgDiagnosticos getDiagnosticoById(short idDiagnostico) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String sql = "from CfgDiagnosticos cd"
                    + " left join fetch cd.ldvCie10 lc"
                    + " where cd.cdIddiagnostico = :idDiagnostico and cd.cdIddiagnostico >= 1";
            Query query = sesion.createQuery(sql);
            query.setParameter("idDiagnostico", idDiagnostico);
            CfgDiagnosticos cd = (CfgDiagnosticos) query.uniqueResult();
            sesion.close();
            return cd;
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public String insertDiagnostico(CfgDiagnosticos cd, Long idUsuario) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
        	if (validador.validarCodigo("CD_DESCRIPCION", "CFG_DIAGNOSTICOS", cd.getCdDescripcion())) {
	            sesion.beginTransaction();
	            sesion.save(cd);
	            
	            
	            //Se agregan medicos a log tablas **********************
	            LogCfgtablas logTabla = new LogCfgtablas();
	            LogCfgTablasDAO log = new LogCfgTablasDAO();
	            logTabla.setCfgAccionesdatos(1);
	            logTabla.setLctIdusuario(idUsuario.intValue());
	            logTabla.setLctNombretabla("CFG_DIAGNOSTICOS");                  
	            logTabla.setLctIdtabla(cd.getCdIddiagnostico());
	            logTabla.setLctDescripcion(cd.getCdDescripcion());
	            
	            log.insertLogTablas(logTabla, sesion);
	            //*************************************************************
	            
	            sesion.getTransaction().commit();
	            mensaje = "C";
	            sesion.close();
        	}
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
        return mensaje;
    }
    
    public String updateDiagnostico(CfgDiagnosticos cd, Long idUsuario) throws BiosLISDAOException {
    	CfgDiagnosticos cdAntiguo = this.getDiagnosticoById(cd.getCdIddiagnostico());
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try{
        	
        	BigDecimal id = new BigDecimal(cd.getCdIddiagnostico());
        	if (validador.validarxIdSinAbreviado("CD_DESCRIPCION", "CD_IDDIAGNOSTICO","CFG_DIAGNOSTICOS", cd.getCdDescripcion(),id)) {
                sesion.beginTransaction();
                sesion.update(cd);
                sesion.getTransaction().commit();
                mensaje = "C";
                sesion.close();
                
                if(cd.getLdvCie10() != null) {
              	   LdvCie10 codigo = new LdvCie10();
              	   LdvCie10DAO lvd = new LdvCie10DAO();
              	   codigo = lvd.getListCie10Bycod(cd.getLdvCie10().getLdvcieCodigocie10());            	   
              	   cd.getLdvCie10().setLdvcieDescripcion(codigo.getLdvcieDescripcion());
                 }
                LogCfgTablasDAO log = new LogCfgTablasDAO();
               log.comparadorObjetos(cdAntiguo, cd,  2, idUsuario.intValue() ,cd.getCdIddiagnostico(), cd.getCdDescripcion(), null);
               
               String valorNuevo="";
               String valorAntiguo = "";
               if(cdAntiguo.getLdvCie10() != null) {            	  
            	   valorAntiguo = cdAntiguo.getLdvCie10().getLdvcieDescripcion();
               }
               if(cd.getLdvCie10() != null) {
            	   LdvCie10 codigo = new LdvCie10();
            	   LdvCie10DAO lvd = new LdvCie10DAO();
            	   codigo = lvd.getListCie10Bycod(cd.getLdvCie10().getLdvcieCodigocie10());            	   
            	   valorNuevo = codigo.getLdvcieDescripcion();
               }
               // agregando CD_IDCIE10
           	log.validarDatos(valorNuevo ,valorAntiguo, idUsuario ,  "CD_IDCIE10" , cd.getCdIddiagnostico(), "CFG_DIAGNOSTICOS" , cd.getCdDescripcion());
          
			}

        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
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
