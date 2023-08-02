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
import com.grupobios.bioslis.entity.LdvTiposdocumentos;

/**
 *
 * @author Tatiana Branada
 */
public class LdvTiposdocumentosDAO {

  private static Logger logger = LogManager.getLogger(LdvTiposdocumentosDAO.class);

  public List<LdvTiposdocumentos> getTiposDocumentos()   {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<LdvTiposdocumentos> listaDocumentos = null;
    try {
      Query query = sesion.createQuery("select ldvtd from com.grupobios.bioslis.entity.LdvTiposdocumentos ldvtd");
      listaDocumentos = query.list();
      sesion.close();     
    } catch (Exception e) {
      throw e;
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaDocumentos;
  }

  public List<LdvTiposdocumentos> getTiposDocumentosMenosRut() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<LdvTiposdocumentos> listaDocumentos = null;
    try {
      Query query = sesion.createQuery("select ldvtd from com.grupobios.bioslis.entity.LdvTiposdocumentos ldvtd "
          + "where ldvtd.ldvtdIdtipodocumento > 1");
      listaDocumentos = query.list();
      sesion.close();
     
    } catch (Exception e) {
      throw e;
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaDocumentos;
  }

  public LdvTiposdocumentos getTipoDocumentoById(int idTipoDocumento) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    LdvTiposdocumentos tipoDocumento = null;
    try {
      Query query = sesion.createQuery("select ldvtd from com.grupobios.bioslis.entity.LdvTiposdocumentos ldvtd "
          + "where ldvtd.ldvtdIdtipodocumento = :idTipoDocumento");
      query.setParameter("idTipoDocumento", idTipoDocumento);
       tipoDocumento = (LdvTiposdocumentos) query.uniqueResult();
      sesion.close();
     
    } catch (Exception e) {
      throw e;
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return tipoDocumento;
  }

  public List<LdvTiposdocumentos> getTiposDocumentoRutPasaporte() {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<LdvTiposdocumentos> listaDocumentos = null;
    try {
      Query query = sesion.createQuery("select ldvtd from com.grupobios.bioslis.entity.LdvTiposdocumentos ldvtd "
          + "where ldvtd.ldvtdIdtipodocumento in (1,2,6)"
          );
      listaDocumentos = query.list();
     
    } catch (HibernateException he) {
      logger.error(he.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaDocumentos;
  }
}