package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgExamenesindicacionestm;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class CfgExamenesIndicacionesTMDAO {

    private static Logger logger = LogManager.getLogger(CfgExamenesIndicacionesTMDAO.class);
    
    public List<CfgExamenesindicacionestm> getIndicacionesTM(long idexamen) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createQuery("from CfgExamenesindicacionestm list"
                    + " join fetch list.ldvIndicacionestm "
                    + " where list.id.ceitmIdexamen = :id");
            query.setParameter("id", idexamen);
            List<CfgExamenesindicacionestm> lista = query.list();
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
    
    public List<CfgExamenesindicacionestm> getIndicacionesTMLst(long idexamen) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<CfgExamenesindicacionestm> lista = new ArrayList<CfgExamenesindicacionestm>();
        try {
        sesion.beginTransaction();
        Query query = sesion.createSQLQuery("select {ceitm.*}, {litm.*} from BIOSLIS.cfg_examenesindicacionestm ceitm"
                + " join ldv_indicacionestm litm on litm.ldvitm_idindicacionestm = ceitm.ceitm_idindicaciontm"
                + " where ceitm_idexamen = :id")
                .addEntity("ceitm", CfgExamenesindicacionestm.class)
                .addJoin("litm", "ceitm.ldvIndicacionestm")
                .addEntity("ceitm", CfgExamenesindicacionestm.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        query.setParameter("id", idexamen);
        sesion.getTransaction().commit();
         lista = query.list();
       
        sesion.close();
        }catch(Exception e) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
        	e.printStackTrace();
        }
        return lista;
    }
    
    public void insertCfgExamenesIndicaciones(CfgExamenesindicacionestm ceitm) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(ceitm);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
              sesion.close();
            }
        }
    }
    
    public void insertCfgExamenesIndicacionesNativo(long idExamen, short idIndicacion) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String sqlInsert = "INSERT INTO CFG_EXAMENESINDICACIONESTM"
                    + " (CEITM_IDEXAMEN, CEITM_IDINDICACIONTM)"
                    + " VALUES"
                    + " (:CEITM_IDEXAMEN, :CEITM_IDINDICACIONTM)";
            Query query = sesion.createSQLQuery(sqlInsert);
            sesion.beginTransaction();
            query.setLong("CEITM_IDEXAMEN", idExamen);
            query.setShort("CEITM_IDINDICACIONTM", idIndicacion);
            sesion.getTransaction().commit();
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
    
    public void deleteCfgExamenesIndicaciones(CfgExamenesindicacionestm ceitm) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.delete(ceitm);
            sesion.getTransaction().commit();
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

    public boolean existeIndicacionesTM(long idexamen, short idIndicacionestm) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("from CfgExamenesindicacionestm list"
                    + " join fetch list.ldvIndicacionestm litm "
                    + " where list.id.ceitmIdexamen = :idexamen"
                    + " and list.id.ceitmIdindicaciontm = :idIndicacionestm");
            query.setParameter("idexamen", idexamen);
            query.setParameter("idIndicacionestm", idIndicacionestm);
            sesion.getTransaction().commit();
            List<CfgExamenesindicacionestm> lista = query.list();
            sesion.close();
            return !lista.isEmpty();
        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }


}
