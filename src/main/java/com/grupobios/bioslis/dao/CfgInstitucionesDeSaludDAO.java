/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgInstitucionesdesalud;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;


public class CfgInstitucionesDeSaludDAO {
        
    private static Logger logger = LogManager.getLogger(CfgInstitucionesDeSaludDAO.class);
    
    public List<CfgInstitucionesdesalud> getInstitucionesDeSalud() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select ids from com.grupobios.bioslis.entity.CfgInstitucionesdesalud ids"
                    + " order by ids.cidsDescripcion asc");
            List<CfgInstitucionesdesalud> listaInstitucionesDeSalud = query.list();
            sesion.close();
            return listaInstitucionesDeSalud;
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public CfgInstitucionesdesalud getInstitutById(int id) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select ids from com.grupobios.bioslis.entity.CfgInstitucionesdesalud ids "
                    + "where ids.cidsIdinstituciondesalud = :id");
            query.setParameter("id", id);
            List<CfgInstitucionesdesalud> lista = query.list();

            CfgInstitucionesdesalud list = lista.get(0);
            sesion.close();
            return list;
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
}
