/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgLocalizaciones;
import com.grupobios.bioslis.entity.LogCfgtablas;

/**
 *
 * @author Jan Perkov
 */
public class CfgLocalizacionesDAO {

  private static Logger logger = LogManager.getLogger(CfgLocalizacionesDAO.class);

  public Integer getIdLocalizacion(Integer idServicio, Integer idSalasServicios, Integer idCamasSalas) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query;
        if (idSalasServicios == 0 || idSalasServicios == null) {
          query = sesion.createQuery("select clIdlocalizacion from com.grupobios.bioslis.entity.CfgLocalizaciones "
              + "where clIdservicio = :idServicio " + "and clIdsala is null " + "and clIdcama is null");
          query.setParameter("idServicio", idServicio);
        } else {
          query = sesion.createQuery("select clIdlocalizacion from com.grupobios.bioslis.entity.CfgLocalizaciones "
              + "where clIdservicio = :idServicio " + "and clIdsala = :idSalasServicios " + "and clIdcama = :idCamasSalas");
          query.setParameter("idServicio", idServicio);
          query.setParameter("idSalasServicios", idSalasServicios);
          query.setParameter("idCamasSalas", idCamasSalas);
        }

        logger.debug("Servicio {} sala {} cama {}", idServicio, idSalasServicios, idCamasSalas);
        Integer rpta = (Integer) query.uniqueResult();
        sesion.close();
        return rpta;
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  public CfgLocalizaciones getLocalizacion(Integer idServicio, Integer idSalasServicios, Integer idCamasSalas)
      throws BiosLISDAOException {
    Session sesion = null; 
    CfgLocalizaciones rpta;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query;

     // if (idServicio.equals(0)) {
       // return null;
     // }
      if (idSalasServicios.equals(0) || idSalasServicios.equals(null) ) {          
        query = sesion.createQuery("select c from com.grupobios.bioslis.entity.CfgLocalizaciones c "
            + "where c.clIdservicio = :idServicio " + "and c.clIdsala is null " + "and c.clIdcama is null");
        query.setParameter("idServicio", idServicio);
      } else if (idCamasSalas.equals(0)) {
        query = sesion.createQuery("select c from com.grupobios.bioslis.entity.CfgLocalizaciones c "
            + "where c.clIdservicio = :idServicio " + "and c.clIdsala = :idSalasServicios " + "and c.clIdcama is null");
        query.setParameter("idServicio", idServicio);
        query.setParameter("idSalasServicios", idSalasServicios);
      } else {
        query = sesion.createQuery(
            "select c from com.grupobios.bioslis.entity.CfgLocalizaciones c " + "where c.clIdservicio = :idServicio "
                + "and c.clIdsala = :idSalasServicios " + "and c.clIdcama = :idCamasSalas");
        query.setParameter("idServicio", idServicio);
        query.setParameter("idSalasServicios", idSalasServicios);
        query.setParameter("idCamasSalas", idCamasSalas);
      }

      logger.debug("Servicio {} sala {} cama {}", idServicio, idSalasServicios, idCamasSalas);
      rpta = (CfgLocalizaciones) query.setMaxResults(1).uniqueResult();
      sesion.close();

    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
      logger.debug("Error obteniendo localizaciones {}", he.getMessage());
      throw new BiosLISDAOException("No se pudo obtener localizaciones. Ver error hibernate.");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();

      }      
    }
    return rpta;
  }

  public CfgLocalizaciones getLocalizacionById(int id) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery("select cl " + "from com.grupobios.bioslis.entity.CfgLocalizaciones cl "
            + "where cl.clIdlocalizacion = :idLocalizacion");
        query.setParameter("idLocalizacion", id);
        List<CfgLocalizaciones> listaLocalizaciones = query.list();
        CfgLocalizaciones cl = listaLocalizaciones.get(0);
        sesion.close();
        return cl;
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }

  public List<CfgLocalizaciones> getLocalizaciones() throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery("select cl " + "from CfgLocalizaciones cl ");
        List<CfgLocalizaciones> listaLocalizaciones = query.list();
        sesion.close();
        return listaLocalizaciones;
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }
  
  //Añadido por Marco Caracciolo
  
  public void agregarLocalizacion(CfgLocalizaciones cl) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.save(cl);      
        sesion.getTransaction().commit();
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }
  
  public void actualizarLocalizacion(CfgLocalizaciones cl) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.update(cl);
        sesion.getTransaction().commit();
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()){
            sesion.close();
        }
    }
  }
  
  //End añadido por Marco Caracciolo
}
