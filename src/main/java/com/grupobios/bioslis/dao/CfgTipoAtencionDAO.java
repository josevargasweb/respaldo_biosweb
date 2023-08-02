/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgTipoatencion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

public class CfgTipoAtencionDAO {

  private static Logger logger = LogManager.getLogger(CfgTipoAtencionDAO.class);

	public List<CfgTipoatencion> getTipoAtencion() throws BiosLISDAOException {
            Session sesion = null;
            try {
                sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("select list from com.grupobios.bioslis.entity.CfgTipoatencion list "
				+ "where list.ctaIdtipoatencion != 4");
		List<CfgTipoatencion> lista = query.list();
		sesion.close();
		return lista;
            } catch (HibernateException he) {
            	if (sesion != null && sesion.isOpen()){
                    sesion.close();
                }
                logger.error(he.getMessage());
                throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
            } finally {
                if (sesion != null && sesion.isOpen()){
                    sesion.close();
                }
            }
	}

	public List<CfgTipoatencion> getTiposAtencion() throws BiosLISDAOException {
            Session sesion = null;
            try {
                sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("select list from CfgTipoatencion list where list.ctaActivo = 'S' ");
		List<CfgTipoatencion> lista = query.list();
		sesion.close();
		return lista;
            } catch (HibernateException he) {
            	if (sesion != null && sesion.isOpen()){
                    sesion.close();
                }
                logger.error(he.getMessage());
                throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
            } finally {
                if (sesion != null && sesion.isOpen()){
                    sesion.close();
                }
            }
	}

        public CfgTipoatencion getTipoAtencionByNroOrden(long nOrden) throws BiosLISDAOException{
            Session sesion = null;
            try {
                sesion = HibernateUtil.getSessionFactory().openSession();
                Query query = sesion.createSQLQuery("SELECT cta.* from cfg_tipoatencion cta "
                        + "join datos_fichas df ON cta.cta_idtipoatencion = df.df_idtipoatencion "
                        + "where df.df_norden = :nOrden").addEntity(CfgTipoatencion.class);
                query.setParameter("nOrden", nOrden);
                CfgTipoatencion tipoAtencion = (CfgTipoatencion) query.setMaxResults(1).uniqueResult();
                sesion.close();
                return tipoAtencion;
            } catch (HibernateException he) {
            	if (sesion != null && sesion.isOpen()){
                    sesion.close();
                }
                logger.error(he.getMessage());
                throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
            } finally {
                if (sesion != null && sesion.isOpen()){
                    sesion.close();
                }
            }
        }
        
    	public CfgTipoatencion getTipoAtencionById(int id) throws BiosLISDAOException {
            Session sesion = null;
            try {
                sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("select list from CfgTipoatencion list where list.ctaIdtipoatencion =:id");
		query.setParameter("id", id);
		CfgTipoatencion tipoAtencion = (CfgTipoatencion) query.uniqueResult();
		sesion.close();
		return tipoAtencion;
            } catch (HibernateException he) {
            	if (sesion != null && sesion.isOpen()){
                    sesion.close();
                }
                logger.error(he.getMessage());
                throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
            } finally {
                if (sesion != null && sesion.isOpen()){
                    sesion.close();
                }
            }
	}
        
}
