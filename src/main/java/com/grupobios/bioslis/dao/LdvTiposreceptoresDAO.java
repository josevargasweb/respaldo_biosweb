package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvTiposreceptores;

public class LdvTiposreceptoresDAO {
    
  private static Logger logger = LogManager.getLogger(LdvTiposreceptoresDAO.class);

  public List<LdvTiposreceptores> getTiposReceptores() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "select tr from LdvTiposreceptores tr";
      Query qry = sesion.createQuery(sql);
      return qry.list();
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
