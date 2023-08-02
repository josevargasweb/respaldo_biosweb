/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgPerfilesusuarios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco Caracciolo
 */
public class CfgPerfilesusuariosDAO {
    
    private static Logger logger = LogManager.getLogger(CfgPerfilesusuariosDAO.class);
    
    public CfgPerfilesusuarios getPerfilesUsuariosById(long idUsuario) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "select cpu from CfgPerfilesusuarios cpu "
                    + "left join fetch cpu.cfgCentrosdesalud cs "
                    + "where cpu.cpuIdusuario = :idUsuario";
            Query query = sesion.createQuery(sql);
            query.setParameter("idUsuario", idUsuario);
            CfgPerfilesusuarios cpu = (CfgPerfilesusuarios) query.uniqueResult();
            sesion.close();
            return cpu;
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
    
    public void agregarPerfilesusuarios(CfgPerfilesusuarios cpu) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.save(cpu);
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
    
    public void actualizarPerfilesusuarios(CfgPerfilesusuarios cpu) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.update(cpu);
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
    
}
