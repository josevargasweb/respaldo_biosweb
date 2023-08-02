/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgExamenesconjuntodetalle;
import com.grupobios.bioslis.entity.CfgExamenesconjuntos;
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
public class CfgExamenesConjuntosDAO {
    
    private static Logger logger = LogManager.getLogger(CfgExamenesConjuntosDAO.class);
    
    public void insertExamenesConjuntos(CfgExamenesconjuntos cec) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(cec);
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
    
    public CfgExamenesconjuntos getExamenesConjuntosByExamen(long idexamen) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createSQLQuery("select cec.* from cfg_examenesconjuntos cec"
                    + " join cfg_examenes ce on ce.ce_idexamenesconjuntos = cec.cec_idexamenesconjuntos"
                    + " where ce.ce_idexamen = :idexamen").addEntity(CfgExamenesconjuntos.class);
            query.setParameter("idexamen", idexamen);
            sesion.getTransaction().commit();
            CfgExamenesconjuntos cec = (CfgExamenesconjuntos) query.uniqueResult();
            sesion.close();
            return cec;
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
