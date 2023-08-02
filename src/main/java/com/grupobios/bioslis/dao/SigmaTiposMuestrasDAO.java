/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.SigmaTiposmuestras;
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
public class SigmaTiposMuestrasDAO {
    
    private static Logger logger = LogManager.getLogger(SigmaTiposMuestrasDAO.class);
    
    public List<SigmaTiposmuestras> listSigmaTiposMuestras() throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaTiposmuestras"
                    + " order by case when stmIdsigmatipomuestra = 1 then 0 else 1 end, stmSort asc, stmDescripcion asc";
            Query query = sesion.createQuery(sql);
            List<SigmaTiposmuestras> list = query.list();
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
    
    public SigmaTiposmuestras getSigmaTiposMuestrasById(byte idTipoMuestra) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from SigmaTiposmuestras"
                    + " where stmIdsigmatipomuestra = :idTipoMuestra";
            Query query = sesion.createQuery(sql);
            query.setParameter("idTipoMuestra", idTipoMuestra);
            SigmaTiposmuestras stm = (SigmaTiposmuestras) query.uniqueResult();
            sesion.getTransaction().commit();
            return stm;
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
