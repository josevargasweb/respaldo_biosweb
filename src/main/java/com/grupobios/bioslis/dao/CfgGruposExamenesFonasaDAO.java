/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgGruposexamenesfonasa;
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
public class CfgGruposExamenesFonasaDAO {
    
    private static Logger logger = LogManager.getLogger(CfgGruposExamenesFonasaDAO.class);
    
    public List<CfgGruposexamenesfonasa> listaGruposExamenesFonasa() throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from CfgGruposexamenesfonasa order by cgefIdgrupoexamenfonasa";
            Query query = sesion.createQuery(sql);
            List<CfgGruposexamenesfonasa> list = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return list;
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
    
    public CfgGruposexamenesfonasa getGrupoExamenesFonasaByExamen(long idExamen) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "select * from cfg_gruposexamenesfonasa cgef" +
                        " join cfg_examenes ce on ce.ce_idgrupoexamenfonasa = cgef.cgef_idgrupoexamenfonasa" +
                        " where ce.ce_idexamen = :idExamen";
            Query query = sesion.createSQLQuery(sql).addEntity(CfgGruposexamenesfonasa.class);
            query.setParameter("idExamen", idExamen);
            CfgGruposexamenesfonasa cgef = (CfgGruposexamenesfonasa) query.uniqueResult();
            sesion.close();
            return cgef;
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
    
    

    public CfgGruposexamenesfonasa getGrupoExamenesFonasaById(long id) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "select * from cfg_gruposexamenesfonasa cgef" +                        
                        " where cgef.cgef_idgrupoexamenfonasa = :id";
            Query query = sesion.createSQLQuery(sql).addEntity(CfgGruposexamenesfonasa.class);
            query.setParameter("id", id);
            CfgGruposexamenesfonasa cgef = (CfgGruposexamenesfonasa) query.uniqueResult();
            sesion.close();
            return cgef;
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
