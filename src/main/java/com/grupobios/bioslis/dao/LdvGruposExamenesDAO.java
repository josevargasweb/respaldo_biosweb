/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvFormatos;
import com.grupobios.bioslis.entity.LdvGruposexamenes;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco Caracciolo
 */
public class LdvGruposExamenesDAO {
    
    private static Logger logger = LogManager.getLogger(LdvFormatosDAO.class);
    
    public List<LdvGruposexamenes> getGruposExamenes() throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createQuery("from LdvGruposexamenes lge "
                    + " order by lge.ldvgeDescripcion asc");
            List<LdvGruposexamenes> list = query.list();
            return list;
        } catch (Exception he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public LdvGruposexamenes getGrupoExamenesByIDExamen(long idExamen) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createSQLQuery("select lge.* from ldv_gruposexamenes lge" +
                " join cfg_examenes ce on ce.ce_idgrupoexamen = lge.ldvge_idgrupoexamen" +
                " where ce.ce_idexamen = :idExamen").addEntity(LdvGruposexamenes.class);
            query.setParameter("idExamen", idExamen);
            LdvGruposexamenes gruposexamenes = (LdvGruposexamenes) query.uniqueResult();
            return gruposexamenes;
        } catch (Exception he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
     
    
    public LdvGruposexamenes getGrupoExamenesByID(long id) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createSQLQuery("select lge.* from ldv_gruposexamenes lge" +               
                " where lge.ldvge_idgrupoexamen =:id").addEntity(LdvGruposexamenes.class);
            query.setParameter("id", id);
            LdvGruposexamenes gruposexamenes = (LdvGruposexamenes) query.uniqueResult();
            return gruposexamenes;
        } catch (Exception he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
}
