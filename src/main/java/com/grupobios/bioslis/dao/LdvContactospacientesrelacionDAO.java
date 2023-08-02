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
import com.grupobios.bioslis.entity.LdvContactospacientesrelacion;

public class LdvContactospacientesrelacionDAO {
    private static Logger logger = LogManager.getLogger(LdvContactospacientesrelacionDAO.class);
    
  public List<LdvContactospacientesrelacion> getContactosRelacion() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<LdvContactospacientesrelacion> listaContactosRelacion = null;
    try {
        Query query = sesion
            .createQuery("select ldvcpr from com.grupobios.bioslis.entity.LdvContactospacientesrelacion ldvcpr");
       listaContactosRelacion = query.list();
    }catch(HibernateException he) {
        logger.error("", he.getLocalizedMessage());     
    } finally {
        if (sesion != null && sesion.isOpen()) {
          sesion.close();
        }
    }
    return listaContactosRelacion;
  }

  public LdvContactospacientesrelacion getContactosRelacionById(int ldvcprIdcontactopacienterel) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    LdvContactospacientesrelacion contactosRelacion = null;
    try {
    Query query = sesion.createQuery(
        "select ldvcpr from LdvContactospacientesrelacion ldvcpr where ldvcpr.ldvcprIdcontactopacienterel = :ldvcprIdcontactopacienterel");
    query.setInteger("ldvcprIdcontactopacienterel", ldvcprIdcontactopacienterel);
    contactosRelacion = (LdvContactospacientesrelacion) query.uniqueResult();
    }catch(HibernateException he) {
        logger.error("", he.getLocalizedMessage());     
    } finally {
        if (sesion != null && sesion.isOpen()) {
          sesion.close();
        }
    }
    return contactosRelacion;
  }

}
