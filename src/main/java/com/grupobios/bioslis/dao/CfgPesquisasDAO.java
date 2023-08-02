/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgPesquisas;
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
public class CfgPesquisasDAO {
    
    private static Logger logger = LogManager.getLogger(CfgPesquisasDAO.class);
    
    public List<CfgPesquisas> getListPesquisas() throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<CfgPesquisas> lista;
        try {
            sesion.beginTransaction();
            String sql = "from CfgPesquisas cp"
                    + " order by cp.cpeDescripcion asc";
            Query query = sesion.createQuery(sql);
            lista = query.list();
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
        return lista;
    }
    
    public CfgPesquisas getPesquisaById(short idPesquisa) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from CfgPesquisas cp"
                    + " where cp.cpeIdpesquisa = :idPesquisa";
            Query query = sesion.createQuery(sql);
            query.setParameter("idPesquisa", idPesquisa);
            CfgPesquisas cp = (CfgPesquisas) query.uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return cp;
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
