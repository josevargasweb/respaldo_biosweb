/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.sql.Blob;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenesarchivos;
import com.grupobios.bioslis.entity.LdvTiposarchivos;

/**
 *
 * @author Marco Caracciolo
 */
public class DatosFichasExamenesArchivosDAO {

  private static Logger logger = LogManager.getLogger(DatosFichasExamenesArchivosDAO.class);

  public void saveOrUpdateDocumento(Long nOrden, Long idExamen, byte tda, Blob documento) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      sesion.beginTransaction();
      String sql = "select dfea from DatosFichasexamenesarchivos dfea "
          + "where dfea.datosFichasexamenes.IddatosFichasExamenes.dfeNorden = :nOrden and dfea.datosFichasexamenes.IddatosFichasExamenes.dfeIdexamen = :idExamen "
          + " and dfea.ldvTiposarchivos.ldvtaIdtipoarchivo = :idTipoDocumento";
      Query query = sesion.createQuery(sql);
      query.setLong("nOrden", nOrden);
      query.setLong("idExamen", idExamen);
      query.setByte("idTipoDocumento", tda);
      DatosFichasexamenesarchivos dfea = (DatosFichasexamenesarchivos) query.uniqueResult();
      DatosFichasexamenesarchivos doc = new DatosFichasexamenesarchivos();
      if (dfea == null) {
        dfea = new DatosFichasexamenesarchivos();

//        dfea.setDatosFichasexamenes(dfe);
//        dfea.setLdvTiposarchivos(tda);
        dfea.setDfeaDocumento(documento);
        sesion.save(doc);
      } else {
        doc.setDfeaDocumento(documento);
        sesion.update(doc);
      }
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }
  
  public String deleteExamenesOrden(Long nOrden, Long idExamen, byte tda) throws BiosLISDAOException {
	    Session sesion = HibernateUtil.getSessionFactory().openSession();
	    try {
	    	
	      sesion.beginTransaction();
	      String sql = "UPDATE DATOS_FICHASEXAMENESARCHIVOS   "
	      		+ "SET DFEA_DOCUMENTO  =  NULL, "
	      		+ "DFEA_IDTIPOARCHIVO  = NULL "
	      		+ "WHERE DFEA_NORDEN  =  :nOrden AND DFEA_IDEXAMEN  =  :idExamen  AND DFEA_IDTIPOARCHIVO  =  :idTipoDocumento";
	      Query query = sesion.createSQLQuery(sql);
	      
	      query.setParameter("nOrden", nOrden);
	      query.setParameter("idExamen", idExamen);
	      query.setParameter("idTipoDocumento", tda);
	   
	      query.executeUpdate();
	      sesion.getTransaction().commit();
	    
	    } catch (HibernateException he) {
	      logger.error(he.getMessage());
	      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
	    } finally {
	      if (sesion.isOpen()) {
	        sesion.close();
	      }
	    }
	    return "Bien";
	  }

  public List<DatosFichasexamenesarchivos> getDocumentosExamenOrden(Integer nOrden, Integer idExamen)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<DatosFichasexamenesarchivos> list =  null;
    try {
      sesion.beginTransaction();
      String sql = "from DatosFichasexamenesarchivos dfea "
          + "where dfea.datosFichasexamenes.IddatosFichasExamenes.dfeNorden = :nOrden and dfea.datosFichasexamenes.IddatosFichasExamenes.dfeIdexamen = :idExamen ";
      Query query = sesion.createQuery(sql);
      query.setParameter("nOrden", Long.valueOf(nOrden));
      query.setParameter("idExamen", Long.valueOf(idExamen));
      
      list = query.list();
      if (list.isEmpty()) {
    	  list = null;
      }else if (list.get(0).getLdvTiposarchivos()  == null ) {
    	  list = null;
      }

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
      
    }
    return list;
  }

  public void saveOrUpdateDocumento(DatosFichasexamenes dfe, LdvTiposarchivos tda, Blob documento)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      sesion.beginTransaction();
      DatosFichas df = (DatosFichas) sesion.get(DatosFichas.class,(int) dfe.getIddatosFichasExamenes().getDfeNorden());
      String sql = "select dfea from DatosFichasexamenesarchivos dfea "
          + "where dfea.datosFichasexamenes.IddatosFichasExamenes.dfeNorden = :nOrden and dfea.datosFichasexamenes.IddatosFichasExamenes.dfeIdexamen = :idExamen ";
      Query query = sesion.createQuery(sql);
    
      query.setLong("nOrden", dfe.getIddatosFichasExamenes().getDfeNorden());
      query.setLong("idExamen", dfe.getIddatosFichasExamenes().getDfeIdexamen());
     // query.setByte("idTipoDocumento", tda.getLdvtaIdtipoarchivo());
      DatosFichasexamenesarchivos dfea = (DatosFichasexamenesarchivos) query.uniqueResult();
      if (dfea == null) {
    	
        dfea = new DatosFichasexamenesarchivos();
        dfea.setDatosFichasexamenes(dfe);
        dfea.setLdvTiposarchivos(tda);
        dfea.setDfeaDocumento(documento);
       dfea.setDfeaIdpaciente(Long.valueOf(df.getDatosPacientes()));
        sesion.saveOrUpdate(dfea);
      } else {
    	 
          dfea.setDatosFichasexamenes(dfe);
          dfea.setLdvTiposarchivos(tda);
          dfea.setDfeaDocumento(documento);
        sesion.update(dfea);
      }
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

}
