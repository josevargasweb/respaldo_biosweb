/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvFichasestadostm;

/**
 *
 * @author marco.caracciolo
 */
public class LdvFichasEstadosTMDAO {

  public List<LdvFichasestadostm> llenarSelectEstadosPaciente() throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createQuery("from LdvFichasestadostm");
      List<LdvFichasestadostm> lista = query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

}
