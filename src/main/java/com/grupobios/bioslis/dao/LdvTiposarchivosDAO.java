package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvTiposarchivos;

public class LdvTiposarchivosDAO {

  private static Logger logger = LogManager.getLogger(LdvTiposarchivosDAO.class);

  List<LdvTiposarchivos> getAll() throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sql = "select tda from LdvTiposarchivos tda";
      Query qry = sesion.createQuery(sql);
      return qry.list();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public LdvTiposarchivos findById(byte tipoArchivo) throws BiosLISDAOException {

    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "select tda from LdvTiposarchivos tda where tda.ldvtaIdtipoarchivo = :tda";
      Query qry = sesion.createQuery(sql);
      qry.setByte("tda", tipoArchivo);
      return (LdvTiposarchivos) qry.uniqueResult();
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
