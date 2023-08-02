/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvOperacionesmath;
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
public class LdvOperacionesMathDAO {
    
    private static Logger logger = LogManager.getLogger(LdvOperacionesMathDAO.class);
    
    public List<LdvOperacionesmath> listOperacionesMath() throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from LdvOperacionesmath"
                    + " order by ldvomIdoperadormath";
            Query query = sesion.createQuery(sql);
            List<LdvOperacionesmath> list = query.list();
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
    
    public LdvOperacionesmath getOperacionesMathById(byte idOperadorMath) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from LdvOperacionesmath"
                    + " where ldvomIdoperadormath = :idOperadorMath";
            Query query = sesion.createQuery(sql);
            query.setParameter("idOperadorMath", idOperadorMath);
            LdvOperacionesmath list = (LdvOperacionesmath) query.uniqueResult();
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
    
}
