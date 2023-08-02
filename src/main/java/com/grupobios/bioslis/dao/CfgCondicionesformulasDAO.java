package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgCondicionesformulas;

public class CfgCondicionesformulasDAO {

  private static Logger logger = LogManager.getLogger(CfgCondicionesformulasDAO.class);

  public CfgCondicionesformulasDAO() {
  }

  public List<CfgCondicionesformulas> getCondicionesTest(Integer idTest) throws BiosLISDAOException {

    Session sesion = null;
    List<CfgCondicionesformulas> lista = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query qry = sesion.createQuery("select ccf from CfgCondicionesformulas ccf where ccf.id.ccfIdtest = :idTest");
      qry.setInteger("idTest", idTest);
      lista = qry.list();
     sesion.close();
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
          logger.error("error en getCondicionesTest "+ he.getMessage());
          throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }
    return lista;
  }
}
