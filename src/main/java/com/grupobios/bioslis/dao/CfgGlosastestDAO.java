/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgGlosastest;
import com.grupobios.bioslis.entity.CfgTest;
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
public class CfgGlosastestDAO {

    private static Logger logger = LogManager.getLogger(CfgGlosastestDAO.class);
    
    public List<CfgGlosastest> getGlosasByTest(CfgTest ct) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("select cgt "
                    + "from com.grupobios.bioslis.entity.CfgGlosastest cgt "
                    + "left join fetch cgt.cfgGlosas cg "
                    + "left join fetch cgt.cfgTest cte "
                    + "where cgt.cfgTest = :ct");
            query.setParameter("ct", ct);
            List<CfgGlosastest> listaGlosas = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return listaGlosas;
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
    
    public CfgGlosastest getGlosaByIdAndTest(int idGlosa, int idTest) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("select cgt "
                    + "from com.grupobios.bioslis.entity.CfgGlosastest cgt "
                    + "left join fetch cgt.cfgGlosas cg "
                    + "left join fetch cgt.cfgTest cte "
                    + "where cg.cgIdglosa = :idGlosa "
                    + "and cte.ctIdtest = :idTest");
            query.setParameter("idGlosa", idGlosa);
            query.setParameter("idTest", idTest);
            CfgGlosastest glosasTest = (CfgGlosastest) query.uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return glosasTest;
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
    
    public boolean existeGlosa(int idGlosa, int idTest) throws BiosLISDAOException {
       return this.getGlosaByIdAndTest(idGlosa, idTest) != null;
    }
    
    public void insertGlosasTest(CfgGlosastest cgt) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(cgt);
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
    
    public void updateGlosasTest(CfgGlosastest cgt) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.update(cgt);
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
    
    public void deleteGlosasTest(CfgGlosastest cgt) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.delete(cgt);
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

    
}
