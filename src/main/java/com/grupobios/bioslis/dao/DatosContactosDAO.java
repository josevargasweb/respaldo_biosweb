/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.DatosContactos;
import com.grupobios.bioslis.entity.DatosPacientes;

public class DatosContactosDAO {
  private static Logger logger = LogManager.getLogger(DatosContactosDAO.class);

  public void insertDatosContactos(DatosContactos dc) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      logger.debug("Datos de contacto {}", dc);     
      sesion.beginTransaction();
      sesion.save(dc);
      sesion.getTransaction().commit();
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en insercion en base de datos seccion Contactos.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateDatosContactos(DatosContactos dc) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      logger.debug("Datos de contacto modificacion {}", dc);
      sesion.beginTransaction();
      sesion.update(dc);
      sesion.getTransaction().commit();
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la actualización updateDatosContactos");
    } finally {
      if (sesion.isOpen())
        sesion.close();

    }
  }

  public int getNewIdDatosContactos() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Query query = sesion.createQuery(
        "select dc.dcIdcontacto from com.grupobios.bioslis.entity.DatosContactos dc " + "order by dc.dcIdcontacto desc")
        .setMaxResults(1);
    int indice;
    try {
      indice = ((int) query.list().get(0)) + 1;
    } catch (HibernateException he) {
        logger.error("error en getNewIdDatosContactos "+he.getMessage()); 
        indice = 1;
    } finally {
      if (sesion.isOpen())
        sesion.close();
    }
    return indice;
  }

  public DatosContactos getContactoxPaciente(DatosPacientes dp) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    DatosContactos contacto = null;
    try {

      Query query = sesion.createQuery("select dc " + "from DatosContactos dc " + "left join fetch dc.datosPacientes "
          + "left join fetch dc.ldvContactospacientesrelacion "
          + "where dc.datosPacientes.dpIdpaciente = :dpIdpaciente ");
      query.setInteger("dpIdpaciente", dp.getDpIdpaciente());
      contacto = (DatosContactos) query.uniqueResult();
      if (contacto != null) { //no se que hace este if, ademas de retornar algo ??
        contacto.getLdvContactospacientesrelacion();
      }
     
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda getContactoxPaciente");
    } finally {
      if (sesion.isOpen())
        sesion.close();
    }
    return contacto;
  }

  /*
   * public DatosContactos getcontactoxPaciente(DatosPacientes dp) throws
   * BiosLISDAOException { Session sesion =
   * HibernateUtil.getSessionFactory().openSession(); try { Query query =
   * sesion.createQuery("select dc " + "from DatosContactos dc " +
   * "left join fetch dc.datosPacientes dp" +
   * "outer left join fetch dc.ldvContactospacientesrelacion " +
   * "where dc.datosPacientes.dpIdpaciente = :dp"); query.setParameter("dp",
   * dp.getDpIdpaciente()); DatosContactos contacto = (DatosContactos)
   * query.uniqueResult(); return contacto; } catch (HibernateException e) {
   * logger.error(e.getMessage()); throw new
   * BiosLISDAOException("Error en la busqueda getListContactoxPaciente"); }
   * finally { if (sesion.isOpen()) sesion.close(); }
   * 
   * }
   */

}
