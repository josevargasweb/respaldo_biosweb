/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgUnidadesdemedida;
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
 * @author Nacho
 */
public class CfgUnidadesdemedidaDAO {

    private static Logger logger = LogManager.getLogger(CfgUnidadesdemedidaDAO.class);
    private static ValidacionMantenedor validador = new ValidacionMantenedor();
    
    LogCfgTablasDAO logCfgTablasDAO;
    
    
    public LogCfgTablasDAO getLogCfgTablasDAO() {
		return logCfgTablasDAO;
	}

	public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
		this.logCfgTablasDAO = logCfgTablasDAO;
	}

	public String insertUnidadMedida(CfgUnidadesdemedida cum, Long idUsuario) throws BiosLISDAOException {
        Session sesion = null;
        String mensaje = "Existe";
        try {
        	if (validador.validarCodigoyAbreviado("CUM_CODIGO","CUM_DESCRIPCION","CFG_UNIDADESDEMEDIDA",cum.getCumCodigo(),cum.getCumDescripcion())) {
            sesion = HibernateUtil.getSessionFactory().openSession();
	            sesion.beginTransaction();
	            cum.setCumActivo("S");
	            sesion.save(cum);
	            
	            //Se agregan unidades de medida  log tablas **********************
	            LogCfgtablas logTabla = new LogCfgtablas();
                logTabla.setCfgAccionesdatos(1);
                logTabla.setLctIdusuario(idUsuario.intValue());
                logTabla.setLctNombretabla("CFG_UNIDADESDEMEDIDA");                  
                logTabla.setLctIdtabla(cum.getCumIdunidadmedida());
                logTabla.setLctDescripcion(cum.getCumDescripcion());
                
                logCfgTablasDAO.insertLogTablas(logTabla, sesion);
	          //******
	            sesion.getTransaction().commit();
	            mensaje = "Creado";            
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

    public String updateUnidadMedida(CfgUnidadesdemedida cum, Long idUsuario) throws BiosLISDAOException {
    	CfgUnidadesdemedida cumAntiguo = this.getUnidadesdemedidaById(cum.getCumIdunidadmedida());
        Session sesion = null;
        String mensaje = "Existe";
        System.out.println(cum.getCumCodigo());
        try {
        	if (validador.validarxId("CUM_CODIGO","CUM_IDUNIDADMEDIDA","CUM_DESCRIPCION","CFG_UNIDADESDEMEDIDA",cum.getCumCodigo(),cum.getCumDescripcion(),BigDecimal.valueOf(cum.getCumIdunidadmedida()))) {
	            sesion = HibernateUtil.getSessionFactory().openSession();
	            sesion.beginTransaction();
	            sesion.update(cum);
	            sesion.getTransaction().commit();
	            sesion.close();
	            mensaje = "Actualizado";
	            
	            logCfgTablasDAO.comparadorObjetos(cumAntiguo, cum, 2, idUsuario.intValue(), cum.getCumIdunidadmedida(), cum.getCumDescripcion(), "");
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

    public int getNewIdUnidadMedida() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion.createQuery("select cum.cumIdunidadmedida from com.grupobios.bioslis.entity.CfgUnidadesdemedida cum "
                + " where cum.cumIdunidadmedida >= 2 order by cum.cumIdunidadmedida desc").setMaxResults(1);
        int indice;
        try {
            indice = ((int) query.list().get(0)) + 1;
        } catch (Exception e) {
            indice = 1;
        }
        sesion.getTransaction().commit();
        sesion.close();
        return indice;
    }

    public List<CfgUnidadesdemedida> getUnidadesMedida() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select cum "
                    + "from com.grupobios.bioslis.entity.CfgUnidadesdemedida cum "
                    + "where cum.cumIdunidadmedida >= 1"
                    + " order by cum.cumSort asc, cum.cumDescripcion asc");
            List<CfgUnidadesdemedida> listaUnidadesMedida = query.list();
            sesion.close();
            return listaUnidadesMedida;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
    }

    public CfgUnidadesdemedida getUnidadesdemedidaById(int idMedida) throws BiosLISDAOException  {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select cum "
                    + "from com.grupobios.bioslis.entity.CfgUnidadesdemedida cum "
                    + "where cum.cumIdunidadmedida = :idMedida");
            query.setParameter("idMedida", idMedida);
            List<CfgUnidadesdemedida> listaUnidadesMedida = query.list();
            CfgUnidadesdemedida unidadMedida = listaUnidadesMedida.get(0);
            sesion.close();
            return unidadMedida;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
    }

    public List<CfgUnidadesdemedida> getUnidadesMedidaLikeCodigo(String codigo) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select cum "
                    + "from com.grupobios.bioslis.entity.CfgUnidadesdemedida cum "
                    + "where upper(cum.cumCodigo) like :codigo and cum.cumIdunidadmedida >= 1");
            query.setParameter("codigo", "%" + codigo + "%");
            List<CfgUnidadesdemedida> listaUnidadesMedida = query.list();
            sesion.close();
            return listaUnidadesMedida;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
    }

    public List<CfgUnidadesdemedida> getUnidadesMedidaLikeDescripcion(String descripcion) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select cum "
                    + "from com.grupobios.bioslis.entity.CfgUnidadesdemedida cum "
                    + "where upper(cum.cumDescripcion) like :codigo and cum.cumIdunidadmedida >= 1");
            query.setParameter("codigo", "%" + descripcion + "%");
            List<CfgUnidadesdemedida> listaUnidadesMedida = query.list();
            sesion.close();
            return listaUnidadesMedida;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
    }

    public List<CfgUnidadesdemedida> getUnidadesMedidaFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        String codigo = filtros.get("codigo").toUpperCase();
        String unidad = filtros.get("unidad").toUpperCase();
        String activo = filtros.get("activo");
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sqlGetUnidadesMedida = "select cum"
                    + " from com.grupobios.bioslis.entity.CfgUnidadesdemedida cum"
                    + " where upper(cum.cumCodigo) like :codigo "
                    + " and cum.cumIdunidadmedida >= 1"
                    + " and upper(cum.cumDescripcion) like :unidad";
            if ("S".equals(activo)){
                sqlGetUnidadesMedida += " and cumActivo ='S'";
            }
            sqlGetUnidadesMedida += " order by cumSort asc, cumDescripcion asc";
            Query query = sesion.createQuery(sqlGetUnidadesMedida);
            query.setParameter("codigo", "%" + codigo + "%");
            query.setParameter("unidad", "%" + unidad + "%");
            List<CfgUnidadesdemedida> listaUnidadesMedida = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return listaUnidadesMedida;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
/*
    public CfgUnidadesdemedida unidadMedidaById(Long idMedida) throws BiosLISDAOException {
    	System.out.println("entre a selkect");
    	 Session sesion = HibernateUtil.getSessionFactory().openSession();
         sesion.beginTransaction();
         Query query = sesion.createQuery("select cum "
                 + "from com.grupobios.bioslis.entity.CfgUnidadesdemedida cum "
                 + "where cum.cumIdunidadmedida = :idMedida");
         query.setParameter("idMedida", idMedida);
         CfgUnidadesdemedida unidadMedida = (CfgUnidadesdemedida) query.uniqueResult();        
         sesion.getTransaction().commit();
         sesion.close();
         return unidadMedida;
    	
    }
    */
}
