/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvFormatos;

/**
 *
 * @author Marco Caracciolo
 */
public class LdvFormatosDAO {

  private static Logger logger = LogManager.getLogger(LdvFormatosDAO.class);

  public List<LdvFormatos> getListFormatos() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createQuery("from LdvFormatos lf " + " order by lf.ldvfDescripcion asc");
      List<LdvFormatos> list = query.list();
      return list;
    } catch (Exception he) {
      logger.error("No se pudo obtener lista de formatos.", he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public LdvFormatos getFormatoByExamen(long idExamen) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    LdvFormatos ldvFormatos;
    try {
      Query query = sesion
          .createQuery("SELECT fo FROM CfgExamenes ce "
          		+ "LEFT JOIN ce.ldvFormatos fo "
          		+ "WHERE ce.ceIdexamen =:idExamen");
      query.setParameter("idExamen", idExamen);
      ldvFormatos = (LdvFormatos) query.uniqueResult();
      
    } catch (Exception he) {
      logger.error("No se pudo obtener formato de examen con id " + idExamen, he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return ldvFormatos;

  }


    
    public LdvFormatos getFormatoById(long id) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createSQLQuery("select * from Ldv_Formatos lf "                   
                    + " where lf.ldvf_idformato = :id")
                    .addEntity(LdvFormatos.class);
            query.setParameter("id", id);
            LdvFormatos ldvFormatos = (LdvFormatos) query.uniqueResult();
            sesion.close();
            return ldvFormatos;
        } catch (Exception he) {
            logger.error("No se pudo obtener formato  con id "+id, he.getMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }

}
