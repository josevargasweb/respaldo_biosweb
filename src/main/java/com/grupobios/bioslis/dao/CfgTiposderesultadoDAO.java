/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgTiposderesultado;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nacho
 */
public class CfgTiposderesultadoDAO {

    private static Logger logger = LogManager.getLogger(CfgTiposderesultadoDAO.class);
    
    public List<CfgTiposderesultado> getTiposResultado() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("select ctr "
                    + "from com.grupobios.bioslis.entity.CfgTiposderesultado ctr "
                    + "where ctr.ctrIdtiporesultado > 1 "
                    + "order by ctr.ctrSort asc, ctr.ctrDescripcion asc");
            List<CfgTiposderesultado> listaTiposResultado = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return listaTiposResultado;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public CfgTiposderesultado getTipoResultadoById(int idTipoResultado) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgTiposderesultado ctr "
                    + "where ctr.ctrIdtiporesultado = :idTipoResultado");
            query.setParameter("idTipoResultado", idTipoResultado);
            CfgTiposderesultado ctr = (CfgTiposderesultado) query.uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return ctr;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
}
