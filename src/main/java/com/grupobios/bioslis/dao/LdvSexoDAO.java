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
import com.grupobios.bioslis.entity.LdvSexo;

public class LdvSexoDAO {

  public List<LdvSexo> getSexo() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();   
    Query query = sesion.createQuery("select ldvs from com.grupobios.bioslis.entity.LdvSexo ldvs");
    List<LdvSexo> listaSexo = null;
    try {
        listaSexo = query.list();
   }catch(Exception e){
       e.printStackTrace();
   }
    sesion.close();
    return listaSexo;
  }

  public LdvSexo getSexoById(int id) throws BiosLISDAOException {
      LdvSexo sexo = null;   
      Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {      
      Query query = sesion
          .createQuery("select ids from com.grupobios.bioslis.entity.LdvSexo ids " + "where ids.ldvsIdsexo = :id");
      query.setParameter("id", id);
       sexo = (LdvSexo) query.uniqueResult();     

    } catch (HibernateException e) {
      throw new BiosLISDAOException(e.getMessage());
    }
    sesion.close();
    return sexo;
  }

}
