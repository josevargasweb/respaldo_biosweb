/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgCargosmodulosbl;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco
 */
public class CfgCargosmodulosblDAO {
    
    private static Logger logger = LogManager.getLogger(CfgCargosmodulosblDAO.class);
    
    public List<CfgCargosmodulosbl> listModulosPorCargo(short idCargo) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgCargosmodulosbl ccmb "
                    + "where ccmb.id.ccmIdcargo = :idCargo";
            Query query = sesion.createQuery(sql);
            query.setParameter("idCargo", idCargo);
            List<CfgCargosmodulosbl> listCargosModulos = query.list();
            sesion.close();
            return listCargosModulos;
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
    
    public void agregarCargoModulo(CfgCargosmodulosbl ccmbl) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.save(ccmbl);
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
    
    public void eliminarCargoModulo(CfgCargosmodulosbl ccmbl) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.delete(ccmbl);
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
    
    public String existeAccesoCargo(short idCargo, short idModulo) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgCargosmodulosbl ccmb "
                    + "where ccmb.id.ccmIdcargo = :idCargo "
                    + "and ccmb.id.ccmIdbioslismodulo = :idModulo";
            Query query = sesion.createQuery(sql);
            query.setParameter("idCargo", idCargo);
            query.setParameter("idModulo", idModulo);
            String existe = "N";
            List<CfgCargosmodulosbl> list = query.list();
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

}
