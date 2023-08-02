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
import com.grupobios.bioslis.entity.DatosFichasantecedentes;

/**
 *
 * @author Nacho
 */
public class DatosFichasAntecedentesDAO {

  private static Logger logger = LogManager.getLogger(DatosFichasAntecedentesDAO.class);

  public void insertAntecedentesTest(DatosFichasantecedentes cat) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
		
    sesion.beginTransaction();
    sesion.saveOrUpdate(cat);
    sesion.getTransaction().commit();
    sesion.close();
    } catch (HibernateException e) {
        logger.error(e.getLocalizedMessage());
        throw new BiosLISDAOException("No se pudo realizar insert  datos antacedentes: " + e.getLocalizedMessage());
	}finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
	}
  }

  public List<DatosFichasantecedentes> getAntecedentesPorNorden(Long nOrden) throws BiosLISDAOException {
    Session sesion = null;

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "from DatosFichasantecedentes dfa inner join fetch dfa.cfgAntecedentes "
          + " where dfa.id.dfaNorden = :nOrden" + " ";
      Query query = sesion.createQuery(sql);
      query.setParameter("nOrden", nOrden);
      List<DatosFichasantecedentes> dfaLst = (List<DatosFichasantecedentes>) query.list();
      sesion.close();
      return dfaLst;
    } catch (HibernateException he) {
      logger.error("No se pudo obtener antecedentes de orden. Error:{}", he.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo obtener antecedentes de orden.");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  public DatosFichasantecedentes getAntecedentesPorIdyNorden(short idAntecedente, long nOrden)
      throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "from DatosFichasantecedentes dfa" + " where dfa.id.dfaNorden = :nOrden"
          + " and dfa.id.dfaIdantecedente = :idAntecedente";
      Query query = sesion.createQuery(sql);
      query.setParameter("nOrden", nOrden);
      query.setParameter("idAntecedente", idAntecedente);
      DatosFichasantecedentes dfa = (DatosFichasantecedentes) query.uniqueResult();
      sesion.close();
      return dfa;
    } catch (HibernateException he) {
      logger.error("");
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }
}
