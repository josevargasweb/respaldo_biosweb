/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvLoinc;
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
public class LdvLoincDAO {
    
    private static Logger logger = LogManager.getLogger(LdvLoincDAO.class);
    
    public List<LdvLoinc> getListLoinc() throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<LdvLoinc> lista;
        try {
            sesion.beginTransaction();
            String sql = "from LdvLoinc ll"
                    + " order by ll.ldvlDescripcion asc";
            Query query = sesion.createQuery(sql);
            lista = query.list();
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
        return lista;
    }
    
    public LdvLoinc getCodigoLoincByExamen(long idExamen) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "select * from ldv_loinc loinc"
                    + " join cfg_examenes ce on ce.ce_codigoloinc = loinc.ldvl_codigoloinc"
                    + " where ce.ce_idexamen = :idExamen";
            Query query = sesion.createSQLQuery(sql).addEntity(LdvLoinc.class);
            query.setParameter("idExamen", idExamen);
            LdvLoinc ldvLoinc = (LdvLoinc) query.uniqueResult();
            sesion.getTransaction().commit();
            return ldvLoinc;
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
