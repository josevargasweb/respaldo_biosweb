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
import com.grupobios.bioslis.entity.DatosFichasdocumentos;
import com.grupobios.bioslis.entity.LdvTiposdocumentosanexos;

/**
 *
 * @author Marco Caracciolo
 */
public class DatosFichasDocumentosDAO {

  private static Logger logger = LogManager.getLogger(DatosFichasDocumentosDAO.class);

  public void insertDocumento(DatosFichasdocumentos dfd) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.save(dfd);
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

  public void updateDocumento(DatosFichasdocumentos dfd) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.update(dfd);
        sesion.getTransaction().commit();
        sesion.close();
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }
  }

  public void deleteDocumento(DatosFichasdocumentos dfd) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.update(dfd);
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

  public void saveOrUpdateDocumento(DatosFichas df, LdvTiposdocumentosanexos tda, Blob docuBlob)
      throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      String sql = "from DatosFichasdocumentos dfd " + "where dfd.datosFichas.dfNorden = :nOrden "
          + "and dfd.ldvTiposdocumentosanexos.ldvtdaIdtipodocumentoanexo = :idTipoDocumento";
      Query query = sesion.createQuery(sql);
      query.setParameter("nOrden", df.getDfNorden());
      query.setParameter("idTipoDocumento", tda.getLdvtdaIdtipodocumentoanexo());
      List<DatosFichasdocumentos> list = query.list();
      DatosFichasdocumentos doc = new DatosFichasdocumentos();
      if (list.isEmpty()) {
        doc.setDatosFichas(df);
        doc.setLdvTiposdocumentosanexos(tda);
        doc.setDfdDocumento(docuBlob);
        sesion.save(doc);
      } else {
        // doc = this.getDocumento(df.getDfNorden(),
        // tda.getLdvtdaIdtipodocumentoanexo());
        doc = list.get(0);
        doc.setDfdDocumento(docuBlob);
        sesion.update(doc);
      }
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

  public DatosFichasdocumentos getDocumento(int nOrden, byte idTipoDocumento) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        String sql = "from DatosFichasdocumentos dfd " + "where dfd.datosFichas.dfNorden = :nOrden "
            + "and dfd.ldvTiposdocumentosanexos.ldvtdaIdtipodocumentoanexo = :idTipoDocumento";
        Query query = sesion.createQuery(sql);
        query.setParameter("nOrden", nOrden);
        query.setParameter("idTipoDocumento", idTipoDocumento);
        DatosFichasdocumentos dfd = (DatosFichasdocumentos) query.uniqueResult();
        return dfd;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }
  }

  public List<DatosFichasdocumentos> getDocumentosOrden(int nOrden) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        String sql = "from DatosFichasdocumentos dfd " + "join fetch dfd.ldvTiposdocumentosanexos "
            + "where dfd.datosFichas.dfNorden = :nOrden";
        Query query = sesion.createQuery(sql);
        query.setParameter("nOrden", nOrden);
        List<DatosFichasdocumentos> lstDocumentos = query.list();
        return lstDocumentos;
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
