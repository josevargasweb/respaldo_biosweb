/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgGruposmuestras;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author marco.caracciolo
 */
public class CfgGruposMuestrasDAO {
    
    private static Logger logger = LogManager.getLogger(CfgGruposMuestrasDAO.class);
    
    public List<Object[]> getGruposMuestras() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select cgm.cgmIdgrupomuestra, cgm.cgmDescripcion from CfgGruposmuestras cgm "
                    + "order by cgm.cgmIdgrupomuestra");
            List<Object[]> lista = query.list();
            sesion.close();
            return lista;
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
