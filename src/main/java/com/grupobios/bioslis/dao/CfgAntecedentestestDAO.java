/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgAntecedentestest;
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
public class CfgAntecedentestestDAO {

    private static Logger logger = LogManager.getLogger(CfgAntecedentestestDAO.class);

    public void insertAntecedentesTest(CfgAntecedentestest cat) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.saveOrUpdate(cat);
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

    public List<CfgAntecedentestest> getAntecedentes(int idtest) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select ca"
                    + " from com.grupobios.bioslis.entity.CfgAntecedentestest ca"
                    + " join fetch ca.cfgAntecedentes cfgA"
                    + " where ca.id.catIdtest = :idtest");
            query.setParameter("idtest", idtest);
            List<CfgAntecedentestest> listaAntecedentes = query.list();
            sesion.close();
            return listaAntecedentes;
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
    
    public String buscarAntecedenteTestById(int idtest, int idantecedente) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select ca"
                    + " from com.grupobios.bioslis.entity.CfgAntecedentestest ca"
                    + " join fetch ca.cfgAntecedentes cfgA"
                    + " where ca.id.catIdtest = :idtest"
                    + " and ca.id.catIdantecedente = :idantecedente"
                    + " order by ca.id.catIdantecedente");
            query.setParameter("idtest", idtest);
            query.setParameter("idantecedente", idantecedente);
            String existe = "N";
            List<CfgAntecedentestest> list = query.list();
            sesion.close();
            if (list.size()>0){
                existe = "S";
            }
            return existe;
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

    public void deleteAntecedentesTest(CfgAntecedentestest cat) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.delete(cat);
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
    
    public CfgAntecedentes  getAntecedenteById(int idAntecedente) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select ca"
                    + " from com.grupobios.bioslis.entity.CfgAntecedentes ca"
                     + " where  ca.caIdantecedente= :idAntecedente");
            query.setParameter("idAntecedente", idAntecedente);
           CfgAntecedentes antecedente = (CfgAntecedentes) query.uniqueResult();
           sesion.close();
            return antecedente;
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
