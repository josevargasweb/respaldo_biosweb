/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvTiposdocumentosanexos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco Caracciolo
 */
public class LdvTiposdocumentosanexosDAO {

    private static Logger logger = LogManager.getLogger(LdvTiposdocumentosanexosDAO.class);
    
    public LdvTiposdocumentosanexos getTiposDocumentosAnexos(byte idTipodocumento) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from LdvTiposdocumentosanexos where ldvtdaIdtipodocumentoanexo = :idTipodocumento";
            Query query = sesion.createQuery(sql);
            query.setParameter("idTipodocumento", idTipodocumento);
            LdvTiposdocumentosanexos tda = (LdvTiposdocumentosanexos) query.uniqueResult();
            return tda;
        } catch (HibernateException he) {
            logger.error("No se pudo guardar condición para test {}", he.getLocalizedMessage());
            throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
              sesion.close();
            }
        }
    }
    
}
