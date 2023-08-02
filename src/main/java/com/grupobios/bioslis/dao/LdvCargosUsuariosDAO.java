/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvCargosusuarios;
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
public class LdvCargosUsuariosDAO {
    
    private static Logger logger = LogManager.getLogger(LdvCargosUsuariosDAO.class);
        
    public LdvCargosusuarios getCargosById(short idCargo) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from LdvCargosusuarios ldvcu "
                    + "where ldvcu.ldvcuIdcargo = :idCargo";
            Query query = sesion.createQuery(sql);
            query.setParameter("idCargo", idCargo);
            LdvCargosusuarios cargo = (LdvCargosusuarios) query.uniqueResult();
            return cargo;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public List<LdvCargosusuarios> getCargosUsuarios() throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from LdvCargosusuarios ldvcu order by ldvcu.ldvcuIdcargo";
            Query query = sesion.createQuery(sql);
            List<LdvCargosusuarios> list = query.list();
            return list;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void agregarCargo(LdvCargosusuarios lcu) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.save(lcu);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void actualizarCargo(LdvCargosusuarios lcu) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.update(lcu);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void eliminarCargo(LdvCargosusuarios lcu) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.delete(lcu);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
}
