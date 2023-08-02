/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgBaczonacuerpo;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jan Perkov
 */
public class CfgBaczonacuerpoDAO {

    private static Logger logger = LogManager.getLogger(CfgBaczonacuerpoDAO.class);
    
    public CfgBaczonacuerpo BuscarBacZonaCuerpoPorId(short id) throws BiosLISDAOException{
    
    //com.grupobios.bioslis.entity.CfgBaczonacuerpo
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select ca "
                    + "from com.grupobios.bioslis.entity.CfgBaczonacuerpo ca "
                    + "where ca.cbzcIdzonacuerpo = :id");
            query.setParameter("id", id);
            List<CfgBaczonacuerpo> listaas = query.list();
            CfgBaczonacuerpo lista = listaas.get(0);
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
