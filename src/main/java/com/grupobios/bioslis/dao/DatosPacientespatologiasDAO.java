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
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosPacientespatologias;

/**
 *
 * @author Tatiana Branada
 */
public class DatosPacientespatologiasDAO {
  private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

  public void insertDatosPacientesPatologias(DatosPacientespatologias dpp) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    if (dpp == null) {
        sesion.close();
      return;
    }
    try {
      sesion.beginTransaction();
      sesion.save(dpp);
      sesion.getTransaction().commit();
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en insercion en base de datos seccion patologias.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateDatosPacientesPatologias(DatosPacientespatologias dpp) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
  
    try {
      sesion.beginTransaction();
      sesion.update(dpp);
      sesion.getTransaction().commit();
    }catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en insercion en base de datos seccion patologias.");
    }finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<DatosPacientespatologias> getComunas() {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<DatosPacientespatologias> listaComunas = null;

    try {
      Query query = sesion.createQuery("select ldvc LdvComunas ldvc");
      listaComunas = query.list();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return listaComunas;
  }

  public List<DatosPacientespatologias> getPatologiasxPacientesList(DatosPacientes dp) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<DatosPacientespatologias> lista = null;
    try {
   
      Query query = sesion
          .createQuery("select dpp " + "from com.grupobios.bioslis.entity.DatosPacientespatologias as dpp "
              + "left join fetch dpp.ldvPatologias as ldvp " + "left join fetch dpp.datosPacientes as dp "
              + "where dpp.datosPacientes.dpIdpaciente = :id");
      query.setParameter("id", dp.getDpIdpaciente());
      lista = query.list(); 
      sesion.close();      
    } catch (Exception e) {
        logger.error("error en getPatologiasxPacientesList " + e.getMessage());
      throw e;
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public Integer delPatologiasxPaciente(DatosPacientes dp) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Integer rpta = 0;
    try {
      sesion.beginTransaction();
      Query query = sesion
          .createQuery("delete from DatosPacientespatologias dpp " + "where dpp.datosPacientes.dpIdpaciente = :id");
      query.setParameter("id", dp.getDpIdpaciente());
       rpta = query.executeUpdate();
      sesion.getTransaction().commit();
      sesion.close();
     
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("No se pudo eliminar las patologias del paciente.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return rpta;
  }
 
}

/*
 * public JsonArrayBuilder getPatologiasxPacientes(DatosPacientes id) { try {
 * 
 * Session sesion = HibernateUtil.getSessionFactory().openSession();
 * sesion.beginTransaction(); Query query = sesion .createQuery("select dpp " +
 * "from com.grupobios.bioslis.entity.DatosPacientespatologias as dpp " +
 * "join dpp.ldvPatologias as a " + "join dpp.datosPacientes as b " +
 * "where dpp.datosPacientes = :id"); query.setParameter("id", id);
 * List<DatosPacientespatologias> lista = query.list();
 * 
 * sesion.getTransaction().commit(); JsonArrayBuilder pacienteJson =
 * Json.createArrayBuilder(); for (DatosPacientespatologias pat : lista) {
 * String descripcionPat = pat.getLdvPatologias().getLdvpatDescripcion(); String
 * observacionPat = pat.getDppObservacion(); String idPat =
 * Integer.toString(pat.getLdvPatologias().getLdvpatIdpatologia());
 * 
 * JsonObjectBuilder pacBuilder = Json.createObjectBuilder(); JsonObject
 * pacJson; pacBuilder.add("descripcionPat", descripcionPat).add("idPat",
 * idPat).add("observacionPat", observacionPat); pacJson = pacBuilder.build();
 * pacienteJson.add(pacJson); } sesion.close(); //
 * arrayBuilder.add(pacienteJson); return pacienteJson; } catch (Exception e) {
 * throw e; }
 * 
 * }
 */

/*
 * public List<DatosPacientespatologias> getPatologiaxPaciente(LdvPatologias
 * ldvp, DatosPacientes dp) { try { Session sesion =
 * HibernateUtil.getSessionFactory().openSession(); sesion.beginTransaction();
 * Query query = sesion .createQuery("select dpp " +
 * "from com.grupobios.bioslis.entity.DatosPacientespatologias as dpp " +
 * "left join fetch dpp.ldvPatologias as ldvp " +
 * "left join fetch dpp.datosPacientes as dp " +
 * "where dpp.datosPacientes = :dp and " + "dpp.ldvPatologias = :ldvp");
 * query.setParameter("dp", dp); query.setParameter("ldvp", ldvp);
 * List<DatosPacientespatologias> lista = query.list();
 * sesion.getTransaction().commit(); sesion.close(); return lista; } catch
 * (Exception ex) { throw ex; }
 * 
 * }
 * 
 */