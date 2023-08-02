/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgBioslismodulos;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco
 */
public class CfgBioslismodulosDAO {
    
    private static Logger logger = LogManager.getLogger(CfgBioslismodulosDAO.class);
    
    public CfgBioslismodulos getModuloById(short idbioslismodulo) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgBioslismodulos "
                    + "where cbmIdbioslismodulo = :idbioslismodulo "
                    + "and cbmActivo = 'S'"
                    + "";
            Query query = sesion.createQuery(sql);
            query.setParameter("idbioslismodulo", idbioslismodulo);
            CfgBioslismodulos cblm = (CfgBioslismodulos) query.uniqueResult();
            sesion.close();
            return cblm;
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
    
    public List<CfgBioslismodulos> listaModulos() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgBioslismodulos where cbmActivo = 'S'";
            Query query = sesion.createQuery(sql);
            List<CfgBioslismodulos> list = query.list();
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
    
    public List<CfgBioslismodulos> listaModulosPrimerNivel() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgBioslismodulos "
                    + "where cbmSegundonivel = 0 and cbmTercernivel = 0 and cbmActivo = 'S' "
                    + "order by cbmIdbioslismodulo";
            Query query = sesion.createQuery(sql);
            List<CfgBioslismodulos> list = query.list();
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
    
    public List<CfgBioslismodulos> listaModulosSegundoNivel(byte primerNivel) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgBioslismodulos "
                    + "where cbmPrimernivel = :primerNivel and cbmSegundonivel > 0 "
                    + "and cbmTercernivel = 0 and cbmActivo = 'S' "
                    + "order by cbmIdbioslismodulo";
            Query query = sesion.createQuery(sql);
            query.setParameter("primerNivel", primerNivel);
            List<CfgBioslismodulos> list = query.list();
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
    
    public List<CfgBioslismodulos> listaModulosTercerNivel(byte primerNivel, byte segundoNivel) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgBioslismodulos "
                    + "where cbmPrimernivel = :primerNivel and cbmSegundonivel = :segundoNivel "
                    + "and cbmTercernivel > 0 and cbmActivo = 'S' "
                    + "order by cbmIdbioslismodulo";
            Query query = sesion.createQuery(sql);
            query.setParameter("primerNivel", primerNivel);
            query.setParameter("segundoNivel", segundoNivel);
            List<CfgBioslismodulos> list = query.list();
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
