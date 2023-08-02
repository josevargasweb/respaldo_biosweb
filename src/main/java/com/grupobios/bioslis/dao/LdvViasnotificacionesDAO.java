package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvViasnotificaciones;

public class LdvViasnotificacionesDAO {
  private static Logger logger = LogManager.getLogger(LdvViasnotificacionesDAO.class);

  public List<LdvViasnotificaciones> getViasNotificaciones() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sql = "select ldvvn from LdvViasnotificaciones ldvvn";
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

}
