/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.MetodosTestDTO;
import com.grupobios.bioslis.entity.CfgMetodostest;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Marco Caracciolo
 */
public class CfgMetodosTestDAO {

    private static Logger logger = LogManager.getLogger(CfgMetodosTestDAO.class);
    
    public void insertMetodosTest(CfgMetodostest metodostest) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.save(metodostest);
            sesion.getTransaction().commit();
            sesion.close();
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
    
    public String buscarMetodoTestById(long idTest, short idMetodo) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgMetodostest cmt "
                    + "where cmt.id.cmtIdtest = :idTest "
                    + "and cmt.id.cmtIdmetodo = :idMetodo";
            Query query = sesion.createQuery(sql);
            query.setParameter("idTest", idTest);
            query.setParameter("idMetodo", idMetodo);
            String existe = "N";
            List<CfgMetodostest> list = query.list();
            sesion.close();
            if (list.size()>0){
                existe = "S";
            }
            return existe;
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
    
    public List<CfgMetodostest> getMetodos(long idTest) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from CfgMetodostest cmt "
                    + "where cmt.id.cmtIdtest = :idTest";
            Query query = sesion.createQuery(sql);
            query.setParameter("idTest", idTest);
            List<CfgMetodostest> list = query.list();
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

    public void updateMetodosTest(CfgMetodostest metodostest) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.update(metodostest);
            sesion.getTransaction().commit();
            sesion.close();
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

    public void deleteMetodoTest(CfgMetodostest metodostest) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.delete(metodostest);
            sesion.getTransaction().commit();
            sesion.close();
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
    
    public CfgMetodostest getMetodoByIdyTest(long idTest, short idMetodo) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            String sql = "from CfgMetodostest cmt "
                    + "where cmt.id.cmtIdtest = :idTest "
                    + "and cmt.id.cmtIdmetodo = :idMetodo";
            Query query = sesion.createQuery(sql);
            query.setParameter("idTest", idTest);
            query.setParameter("idMetodo", idMetodo);
            CfgMetodostest metodo = (CfgMetodostest) query.uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return metodo;
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
    
    public List<MetodosTestDTO> getMetodosByTest(Integer idTest) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            String sql = "select cm.cmet_idmetodo cmetIdmetodo, cm.cmet_descripcion cmetDescripcion, cmt.cmt_activo cmtActivo, "
                    + "cmt.cmt_esvalorpordefecto cmtEsvalorpordefecto, cmt.cmt_idtest ctIdtest "
                    + "from cfg_metodos cm "
                    + "join cfg_metodostest cmt on cmt.cmt_idmetodo = cm.cmet_idmetodo "
                    + "where cmt.cmt_idtest = :idTest";
            Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(MetodosTestDTO.class));
            query.setParameter("idTest", idTest);
            List<MetodosTestDTO> lstDTO = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return lstDTO;
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
