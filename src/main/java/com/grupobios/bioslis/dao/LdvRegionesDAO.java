/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvRegiones;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class LdvRegionesDAO {

    public List<LdvRegiones> getRegiones() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();  
        List<LdvRegiones> listaRegiones = null;
        Query query = sesion.createQuery("select ldvr from com.grupobios.bioslis.entity.LdvRegiones ldvr");
        try {
          listaRegiones = query.list();
        }catch(Exception e) {
            e.printStackTrace();
        }
        sesion.close();
        return listaRegiones;
    }

    public LdvRegiones getRegionById(int idRegion) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        LdvRegiones region = null;
        try {
        Query query = sesion.createQuery("select ldvr from com.grupobios.bioslis.entity.LdvRegiones ldvr "
                + "where ldvr.ldvrIdregion = :idRegion").setMaxResults(1);
        query.setParameter("idRegion", idRegion);
        List<LdvRegiones> listaRegiones = query.list();
        region = listaRegiones.get(0);
        }catch(Exception e ) {
            e.printStackTrace();
        }     
        sesion.close();
        return region;
    }
}
