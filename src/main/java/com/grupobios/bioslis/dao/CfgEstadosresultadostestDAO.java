package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;

public class CfgEstadosresultadostestDAO {

  private static Logger logger = LogManager.getLogger(CfgEstadosresultadostestDAO.class);

  public CfgEstadosresultadostestDAO() {
  }

  public List<CfgEstadosresultadostestDAO> getAll() throws BiosLISDAOException {

    Session sesion = null;
    List<CfgEstadosresultadostestDAO> lista = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
     Query qry = sesion.createQuery("select cert from CfgEstadosresultadostest cert");     
      lista = qry.list();
      
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
      logger.error("No se pudo obtener lista de estados de resultados de un test {}", he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }
    return lista;
  }
}
