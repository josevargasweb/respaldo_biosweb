/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgExamenesconjuntodetalle;
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
public class CfgExamenesConjuntosDetalleDAO {
    
    private static Logger logger = LogManager.getLogger(CfgExamenesConjuntosDAO.class);
    
    public void insertExamenesConjuntosDetalle(CfgExamenesconjuntodetalle cecd) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.save(cecd);
            sesion.getTransaction().commit();
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
    
    public void updateExamenesConjuntosDetalle(CfgExamenesconjuntodetalle cecd) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.update(cecd);
            sesion.getTransaction().commit();
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
    
    public void deleteExamenesConjuntosDetalle(CfgExamenesconjuntodetalle cecd) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.delete(cecd);
            sesion.getTransaction().commit();
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
    
    public List<CfgExamenesconjuntodetalle> getExamenesConjuntosById(long idExamenesConjuntos) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgExamenesconjuntodetalle cecd"
                    + " where cecd.id.cecdIdexamenesconjuntos = :idExamenesConjuntos");
            query.setParameter("idExamenesConjuntos", idExamenesConjuntos);
            sesion.getTransaction().commit();
            List<CfgExamenesconjuntodetalle> lista = query.list();
            sesion.close();
            return lista;
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

    public boolean existeExamenConjunto(long idExamen, long idExamenConjunto) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgExamenesconjuntodetalle cecd"
                    + " where cecd.id.cecdIdexamen = :idExamen"
                    + " and cecd.id.cecdIdexamenesconjuntos = :idExamenConjunto");
            query.setParameter("idExamen", idExamen);
            query.setParameter("idExamenConjunto", idExamenConjunto);
            sesion.getTransaction().commit();
            List<CfgExamenesconjuntodetalle> lista = query.list();
            sesion.close();
            return !lista.isEmpty();
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
