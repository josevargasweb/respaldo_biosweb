/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgExamenestablasanexas;
import com.grupobios.bioslis.entity.CfgTablasreferenciasexamenes;
import com.grupobios.bioslis.entity.CfgTablasreferenciasexamenesId;

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
public class CfgTablasreferenciasexamenesDAO {

    private static Logger logger = LogManager.getLogger(CfgExamenesTestDAO.class);
    
    public CfgTablasreferenciasexamenes getCeldasTablaReferenciasByIdandCampo(CfgTablasreferenciasexamenesId id  ) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
		        
		        CfgTablasreferenciasexamenes ctre = (CfgTablasreferenciasexamenes) sesion.get(CfgTablasreferenciasexamenes.class, id);
		        sesion.close();
		        return ctre;
            } catch (HibernateException he) {
                logger.error(he.getMessage());
                throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
            } finally {
                if (sesion.isOpen()) {
                    sesion.close();
                }
            }
    }
    
    
    public void insertTablaReferencia(CfgTablasreferenciasexamenes ctre) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(ctre);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void updateTablaReferencia(CfgTablasreferenciasexamenes ctre) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.update(ctre);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    public void insertTablaAnexa(CfgExamenestablasanexas ce) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(ce);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void updateTablaAnexa(CfgExamenestablasanexas ceta) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.update(ceta);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
/*
    public int getNewId() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion.createQuery("select ctre.ctreIdtablareferenciaexamen from com.grupobios.bioslis.entity.CfgTablasreferenciasexamenes ctre "
                + "order by ctre.ctreIdtablareferenciaexamen desc").setMaxResults(1);
        int indice;
        try {
            indice = ((int) query.list().get(0)) + 1;
        } catch (Exception e) {
            indice = 1;
        }
        sesion.getTransaction().commit();
        sesion.close();
        return indice;
    }
    */
    public List<CfgTablasreferenciasexamenes> getCeldasTablaReferenciasById(short idTablaRef) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
            try {
                String sql = "select ctre from CfgTablasreferenciasexamenes ctre"
                        + " join ctre.cfgExamenestablasanexas ceta"
                        + " where ctre.id.ctreIdtablareferenciaexamen = :idTablaRef";
                Query query = sesion.createQuery(sql);
                query.setParameter("idTablaRef", idTablaRef);
                List<CfgTablasreferenciasexamenes> lista = query.list();
                sesion.close();
                return lista;
            } catch (HibernateException he) {
                logger.error(he.getMessage());
                throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
            } finally {
                if (sesion.isOpen()) {
                    sesion.close();
                }
            }
    }
    
    public CfgExamenestablasanexas getTablaAnexaExamen(long idExamen) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
            try {
            sesion.beginTransaction();
            String sql = "select ceta from CfgExamenestablasanexas ceta"
                    + " where ceta.cetaIdexamen = :idExamen";
            Query query = sesion.createQuery(sql);
            query.setParameter("idExamen", idExamen);
            CfgExamenestablasanexas ceta = (CfgExamenestablasanexas) query.uniqueResult();
            sesion.close();
            return ceta;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public CfgExamenestablasanexas getTablaAnexaById(short idTablaAnexa) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
            try {
            sesion.beginTransaction();
            String sql = "select ceta from CfgExamenestablasanexas ceta"
                    + " where ceta.cetaIdexamentabla = :idTablaAnexa";
            Query query = sesion.createQuery(sql);
            query.setParameter("idTablaAnexa", idTablaAnexa);
            CfgExamenestablasanexas ceta = (CfgExamenestablasanexas) query.uniqueResult();
            sesion.close();
            return ceta;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public boolean existeCeldaTablaReferencia(short idTablaRef, String campo) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "select ctre from CfgTablasreferenciasexamenes ctre"
                    + " where ctre.id.ctreIdtablareferenciaexamen = :idTablaRef"
                    + " and ctre.id.ctreCampo = :campo";
            Query query = sesion.createQuery(sql);
            query.setParameter("idTablaRef", idTablaRef);
            query.setParameter("campo", campo);
            List<CfgTablasreferenciasexamenes> list = query.list();
            sesion.close();
            return !list.isEmpty();
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
