/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvTiposcondicion;
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
public class LdvTiposCondicionDAO {
    
    private static Logger logger = LogManager.getLogger(LdvTiposCondicionDAO.class);
    
    public List<LdvTiposcondicion> listTiposCondicion() throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from LdvTiposcondicion" + "";
            Query query = sesion.createQuery(sql);
            List<LdvTiposcondicion> list = query.list();
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
    
    public LdvTiposcondicion getTipoCondicionById(byte idtipocondicion) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from LdvTiposcondicion" 
                    + " where ldvtcondIdtipocondicion = :idtipocondicion";
            Query query = sesion.createQuery(sql);
            query.setParameter("idtipocondicion", idtipocondicion);
            LdvTiposcondicion list = (LdvTiposcondicion) query.uniqueResult();
            sesion.getTransaction().commit();
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
}
