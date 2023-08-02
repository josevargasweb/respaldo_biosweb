/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import java.math.BigDecimal;
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
public class CfgAntecedentesDAO {

    private static Logger logger = LogManager.getLogger(CfgAntecedentesDAO.class);
    
    public List<CfgAntecedentes> getAntecedentes() throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select ca "
                    + "from com.grupobios.bioslis.entity.CfgAntecedentes ca "
                    //+ "order by ca.caIdantecedente");
                    + "order by ca.caDescripcion asc");
            List<CfgAntecedentes> listaAntecedentes = query.list();
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

    public CfgAntecedentes getAntecedenteById(int id) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select ca "
                    + "from com.grupobios.bioslis.entity.CfgAntecedentes ca "
                    + "where ca.caIdantecedente = :id");
            query.setParameter("id", id);
            List<CfgAntecedentes> listaAntecedentes = query.list();
            CfgAntecedentes antecedente = listaAntecedentes.get(0);
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
    
    public BigDecimal getIdbyCodigo(String codigo) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createSQLQuery("select ca_idantecedente id from cfg_antecedentes "
                    + "where ca_codigoantecedente = :codigo").addScalar("id");
            query.setParameter("codigo", codigo);
            BigDecimal id = (BigDecimal) query.uniqueResult();
            sesion.close();
            return id;
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
