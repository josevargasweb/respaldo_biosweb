package com.grupobios.bioslis.dao;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;

public class RelojDAO {

  private static Logger logger = LogManager.getLogger(RelojDAO.class);

  public RelojDAO() {
    init();

  }

  private Timestamp ts;
//  private Calendar myCalendar;

  private void init() {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createSQLQuery("select SYSDATE from dual").setMaxResults(1);
      this.ts = (Timestamp) query.uniqueResult();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      // throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  /**
   * @return the ts
   */
  public Timestamp getTs() {
    init();
    return ts;
  }

  public Timestamp getTs(Session sesion) {
    init(sesion);
    return ts;
  }

  private void init(Session sesion) {
    try {
      Query query = sesion.createSQLQuery("select SYSDATE from dual").setMaxResults(1);
      this.ts = (Timestamp) query.uniqueResult();
//      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      // throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
//      if (sesion != null && sesion.isOpen()) {
//        sesion.close();
//      }
    }
  }

}
