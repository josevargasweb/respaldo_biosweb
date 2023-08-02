/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvProfesionesusuarios;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco Caracciolo
 */
public class LdvProfesionesUsuariosDAO {
    
    public List<LdvProfesionesusuarios> getProfesionesUsuarios(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion.createQuery("from LdvProfesionesusuarios");
        List<LdvProfesionesusuarios> list = query.list();
        sesion.getTransaction().commit();
        sesion.close();
        return list;
    }
    
}
