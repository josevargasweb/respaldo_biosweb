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
import com.grupobios.bioslis.entity.CfgBacgrupostest;

/**
 *
 * @author Marco Caracciolo
 */
public class CfgBacgrupostestDAO {

  private static Logger logger = LogManager.getLogger(CfgBacgrupostestDAO.class);

  public List<CfgBacgrupostest> getListBacGruposTests() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "from CfgBacgrupostest order by cbgtIdbacgrupotest";
      Query query = sesion.createQuery(sql);
      List<CfgBacgrupostest> list = query.list();
      sesion.close();
      return list;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public CfgBacgrupostest getBacGruposTestById(byte idBgt) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "from CfgBacgrupostest where cbgtIdbacgrupotest = :idBgt";
      Query query = sesion.createQuery(sql);
      query.setParameter("idBgt", idBgt);
      CfgBacgrupostest cbgt = (CfgBacgrupostest) query.uniqueResult();
      sesion.close();
      return cbgt;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

}
