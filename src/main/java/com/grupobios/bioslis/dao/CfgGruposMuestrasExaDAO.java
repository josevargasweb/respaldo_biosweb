/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgExamenestest;
import com.grupobios.bioslis.entity.CfgGruposexamenesfonasa;
import com.grupobios.bioslis.entity.CfgGruposmuestrasexa;
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
public class CfgGruposMuestrasExaDAO {

    private static Logger logger = LogManager.getLogger(CfgGruposMuestrasExaDAO.class);
    
    public void insertGruposMuestrasExa(CfgGruposmuestrasexa gruposMuestras) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(gruposMuestras);
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
    
    public void deleteGruposMuestrasExa(CfgGruposmuestrasexa gruposMuestras) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.delete(gruposMuestras);
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
    
    public List<CfgGruposmuestrasexa> getGruposMuestrasExamen(long idExamen) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from CfgGruposmuestrasexa cgme"
                    + " where cgme.id.cgmeIdexamen = :idExamen";
            Query query = sesion.createQuery(sql);
            query.setParameter("idExamen", idExamen);
            List<CfgGruposmuestrasexa> list = query.list();
            sesion.getTransaction().commit();
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
    
    public CfgGruposmuestrasexa getGruposMuestrasExamen(long idExamen, short idTipoMuestra) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from CfgGruposmuestrasexa cgme"
                    + " where cgme.id.cgmeIdexamen = :idExamen"
                    + " and cgme.id.cgmeIdtipomuestra = :idTipoMuestra";
            Query query = sesion.createQuery(sql);
            query.setParameter("idExamen", idExamen);
            query.setParameter("idTipoMuestra", idTipoMuestra);
            CfgGruposmuestrasexa cgme = (CfgGruposmuestrasexa) query.uniqueResult();
            sesion.getTransaction().commit();
            return cgme;
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
    /*
    public boolean existeGrupoMuestraExamen(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        return false;
    }
    */
}
