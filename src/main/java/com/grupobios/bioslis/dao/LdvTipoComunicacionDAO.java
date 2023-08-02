/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvTipocomunicacion;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco Caracciolo
 */
public class LdvTipoComunicacionDAO {
    
    private static Logger logger = LogManager.getLogger(LdvTipoComunicacionDAO.class);
    
    public List<LdvTipocomunicacion> getTiposComunicaciones() throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createQuery("from LdvTipocomunicacion order by ldvtcDescripcion asc");
            List<LdvTipocomunicacion> lista = query.list();
            return lista;
        } catch (Exception he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public LdvTipocomunicacion getTipoComunicacionById(byte idtipocomunicacion) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createQuery("from LdvTipocomunicacion where ldvtcIdtipocomunicacion = :idtipocomunicacion");
            query.setParameter("idtipocomunicacion", idtipocomunicacion);
            LdvTipocomunicacion ldvTipocomunicacion = (LdvTipocomunicacion) query.uniqueResult();
            return ldvTipocomunicacion;
        } catch (Exception he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
}
