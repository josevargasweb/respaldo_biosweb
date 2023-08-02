/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvPatologias;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;



public class LdvPatologiasDAO {
    
    public List<LdvPatologias> getPatologias(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<LdvPatologias> listaPatologias = null;
        try {
            Query query = sesion.createQuery("select ldvpat from com.grupobios.bioslis.entity.LdvPatologias ldvpat order by ldvpat.ldvpatDescripcion");
             listaPatologias = query.list();
        }catch(Exception e) {
            e.printStackTrace();
        }
        sesion.close();
        return listaPatologias;
    }
}
