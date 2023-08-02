package com.grupobios.bioslis.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgEstadosexamenes;

public class CfgEstadosexamenesDAO {

    private static Logger logger = LogManager.getLogger(CfgEstadosexamenesDAO.class);
    
	public List<CfgEstadosexamenes> getEstados() {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<CfgEstadosexamenes> listEstados = new  ArrayList<CfgEstadosexamenes>();
		try {
    		Query query = sesion.createQuery("select c " + " from CfgEstadosexamenes c");
    		 listEstados = query.list();
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
		
		listEstados.forEach(e -> {
		   // System.err.println(e.getCeeDescripcionestadoexamen());
		 logger.debug(e.getCeeDescripcionestadoexamen());
		});

		return listEstados;
	}

}
