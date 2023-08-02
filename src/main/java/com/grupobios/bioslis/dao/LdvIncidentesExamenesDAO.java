/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgAntecedentestest;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nacho
 */
public class LdvIncidentesExamenesDAO {

    public void insertIndicacionesExamen(LdvIndicacionesexamenes cat) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(cat);
        sesion.getTransaction().commit();
        sesion.close();
    }

    public List<LdvIndicacionesexamenes> getIndicacionesExamen(String descripcion) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion.createQuery("select ca"
                + " from com.grupobios.bioslis.entity.LdvIndicacionesexamenes ca"
                + " where ca.ldvieDescripcion like '%"+descripcion.toUpperCase()+"%'");
        List<LdvIndicacionesexamenes> listaAntecedentes = query.list();
        sesion.getTransaction().commit();
        sesion.close();
        return listaAntecedentes;
    }
        public byte getLastId() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {

            sesion.beginTransaction();
            Query query = sesion.createQuery("select med.ldvieIdindicacionesexamen from com.grupobios.bioslis.entity.LdvIndicacionesexamenes med "
                    + "order by med.ldvieIdindicacionesexamen desc").setMaxResults(1);
            byte indice;
            try {
                indice = ((byte) query.list().get(0));
            } catch (Exception e) {
                indice = 1;
            }
            sesion.getTransaction().commit();
            sesion.close();
            return  (byte)(indice + 1);
        } catch (Exception e) {
            return 1;
        }
    }
}
