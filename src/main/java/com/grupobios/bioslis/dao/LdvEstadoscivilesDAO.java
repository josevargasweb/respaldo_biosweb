/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvEstadosciviles;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Tatiana Branada
 */
public class LdvEstadoscivilesDAO {

    public List<LdvEstadosciviles> getEstadosCiviles() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<LdvEstadosciviles> listaEstadosCiviles = null;
        try {
            Query query = sesion.createQuery("select ldvec from com.grupobios.bioslis.entity.LdvEstadosciviles ldvec");
            listaEstadosCiviles = query.list();
        }catch(Exception e) {
            e.printStackTrace();
        }
        sesion.close();
        return listaEstadosCiviles;
    }

    public LdvEstadosciviles getEstadoCivilById(int idEstadoCivil) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        LdvEstadosciviles estadoCivil =  null;
        try {
            Query query = sesion.createQuery("select ldvec "
                    + "from com.grupobios.bioslis.entity.LdvEstadosciviles ldvec "
                    + "where ldvec.ldvecIdestadocivil = :idEstadoCivil");
            query.setParameter("idEstadoCivil", idEstadoCivil);
            List<LdvEstadosciviles> listaEstadosCiviles = query.list();
            estadoCivil = listaEstadosCiviles.get(0);
        }catch(Exception e) {
            e.printStackTrace();
        }
        sesion.close();
        return estadoCivil;
    }
}
