/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;


import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgConvenios;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


public class CfgConvenioDAO {
    
    private static Logger logger = LogManager.getLogger(CfgConvenioDAO.class);
    
    public List<CfgConvenios> getConvenios(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<CfgConvenios> lista = null;
        
        try {
        Query query = sesion.createQuery("select list from com.grupobios.bioslis.entity.CfgConvenios list"
        		+ " order by list.ccSort asc, list.ccDescripcion asc");
         lista = query.list();
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error("error en getConvenios "+he.getMessage());   
             } finally {
               if (sesion != null && sesion.isOpen()) {
                 sesion.close();
               }
        }
        return lista;
    }
    
    public CfgConvenios getConveniosById(Short id){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        CfgConvenios convenio = null;
        
        try {        	
        	Query query = sesion
                    .createQuery("select list from com.grupobios.bioslis.entity.CfgConvenios list where list.ccIdconvenio =" + id);
         convenio  = (CfgConvenios) query.uniqueResult();
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error("error en getConvenios "+he.getMessage());   
             } finally {
               if (sesion != null && sesion.isOpen()) {
                 sesion.close();
               }
        }
        return convenio;
    }
}
