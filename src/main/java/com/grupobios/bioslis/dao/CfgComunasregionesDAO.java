/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;


import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgComunasregiones;
import com.grupobios.bioslis.entity.LdvRegiones;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;



public class CfgComunasregionesDAO {
    private static Logger logger = LogManager.getLogger(CfgComunasregionesDAO.class);
    
    public List<CfgComunasregiones> getComunasByRegion(LdvRegiones region){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<CfgComunasregiones> listaComunas = null;
        try {
        Query query = sesion.createQuery("select ccr "
                + "from com.grupobios.bioslis.entity.CfgComunasregiones ccr "
                + "left join fetch ccr.ldvComunas "
                + "left join fetch ccr.ldvRegiones "
                + "where ccr.ldvRegiones = :region");
        query.setParameter("region", region);
         listaComunas = query.list();
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error("error en getExamenes "+he.getMessage());   
          } finally {
               if (sesion != null && sesion.isOpen()) {
                 sesion.close();
               }
          }
        return listaComunas;
    }
}
