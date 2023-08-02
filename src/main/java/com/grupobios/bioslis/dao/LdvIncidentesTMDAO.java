/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgAntecedentestest;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;
import com.grupobios.bioslis.entity.LdvIndicacionestm;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nacho
 */
public class LdvIncidentesTMDAO {

    public void insertIndicacionesTM(LdvIndicacionestm cat) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(cat);
        sesion.getTransaction().commit();
        sesion.close();
    }

    public List<LdvIndicacionestm> getIndicacionesTM(String descripcion) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion.createQuery("select ca"
                + " from com.grupobios.bioslis.entity.LdvIndicacionestm ca"
                + " where ca.ldvitmDescripcionindicacion like '%"+descripcion.toUpperCase()+"%'");
        List<LdvIndicacionestm> listaAntecedentes = query.list();
        sesion.getTransaction().commit();
        sesion.close();
        return listaAntecedentes;
    }
        public short getLastId() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {

            sesion.beginTransaction();
            Query query = sesion.createQuery("select med.ldvitmIdindicacionestm from com.grupobios.bioslis.entity.LdvIndicacionestm med "
                    + "order by med.ldvitmIdindicacionestm desc").setMaxResults(1);
            short indice;
            try {
                indice = ((short) query.list().get(0));
            } catch (Exception e) {
                indice = 1;
            }
            sesion.getTransaction().commit();
            sesion.close();
            return (short)(indice + 1);
        } catch (Exception e) {
            return 1;
        }
    }
}
