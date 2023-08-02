/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvCie10;
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
public class LdvCie10DAO {

    private static Logger logger = LogManager.getLogger(LdvCie10DAO.class);
    
    public List<LdvCie10> getListCie10() throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createQuery("from LdvCie10 lc order by lc.ldvcieDescripcion");
            List<LdvCie10> list = query.list();
            sesion.close();
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
    
    public LdvCie10 getListCie10Bycod(String cod) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createQuery("from LdvCie10 lc  where lc.ldvcieCodigocie10 =:cod");
            query.setParameter("cod", cod);
           LdvCie10  lvdcie = (LdvCie10) query.uniqueResult();
           sesion.close();
            return lvdcie;
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
