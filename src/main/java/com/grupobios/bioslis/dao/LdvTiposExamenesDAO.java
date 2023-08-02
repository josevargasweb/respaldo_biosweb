/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.LdvTiposexamenes;
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
public class LdvTiposExamenesDAO {
    
    private static Logger logger = LogManager.getLogger(CfgExamenesDAO.class);
    
    public LdvTiposexamenes getTipoExamen(long idExamen) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createSQLQuery("select * from ldv_tiposexamenes lte" +
                    " join cfg_examenes ce on ce.ce_idtipoexamen = lte.ldvte_idtipoexamen" +
                    " where ce.ce_idexamen = :idExamen").addEntity(LdvTiposexamenes.class);
            query.setLong("idExamen", idExamen);
            LdvTiposexamenes list = (LdvTiposexamenes) query.uniqueResult();
            sesion.close();
            return list;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    
    public LdvTiposexamenes getTipoExamenById(long id) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createSQLQuery("select * from ldv_tiposexamenes lte" +                  
                    " where lte.ldvte_idtipoexamen = :id").addEntity(LdvTiposexamenes.class);
            query.setLong("id", id);
            LdvTiposexamenes list = (LdvTiposexamenes) query.uniqueResult();
            sesion.close();
            return list;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
}
