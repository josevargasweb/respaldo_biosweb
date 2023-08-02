/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgMuestrasprefijos;
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
public class CfgMuestrasPrefijosDAO {
    
    private static Logger logger = LogManager.getLogger(CfgMuestrasPrefijosDAO.class);
    
    public CfgMuestrasprefijos getMuestraPrefijo(String prefijo) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgMuestrasprefijos where cmuepPrefijotipomuestra = :prefijo");
            query.setParameter("prefijo", prefijo);
            CfgMuestrasprefijos cm = (CfgMuestrasprefijos) query.uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return cm;
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
    
    public boolean existeMuestraPrefijo(String prefijo) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgMuestrasprefijos where cmuepPrefijotipomuestra = :prefijo");
            query.setParameter("prefijo", prefijo);
            List<CfgMuestrasprefijos> list = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return !list.isEmpty();
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
    
    public void insertPrefijo(CfgMuestrasprefijos cmp) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(cmp);
            sesion.getTransaction().commit();
            sesion.close();
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
    
    public void updatePrefijo(CfgMuestrasprefijos cmp) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.update(cmp);
            sesion.getTransaction().commit();
            sesion.close();
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
