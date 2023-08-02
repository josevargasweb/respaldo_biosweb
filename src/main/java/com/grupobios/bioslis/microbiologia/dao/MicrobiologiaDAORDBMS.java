package com.grupobios.bioslis.microbiologia.dao;

import java.util.List;

import com.grupobios.bioslis.entity.Microbiologia;
import com.grupobios.bioslis.config.HibernateUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class MicrobiologiaDAORDBMS implements MicrobiologiaDAO{

    private static Logger logger = LogManager.getLogger(MicrobiologiaDAORDBMS.class);
    
    public MicrobiologiaDAORDBMS() {}

    @SuppressWarnings("unchecked")
    @Override
    public List<Microbiologia> getAll(){

        List<Microbiologia> result = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try{
            Criteria cr = sesion.createCriteria(Microbiologia.class);
            result = cr.list();
        } catch (HibernateException he) {
            logger.error("error en getAll MicrobiologiaDAORDBMS "+he.getMessage());   
             } finally {
               if (sesion != null && sesion.isOpen()) {
                 sesion.close();
               }
             }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Microbiologia getByName(String name){

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Microbiologia result = null;
        try{
            Criteria cr = sesion.createCriteria(Microbiologia.class);
            cr.add(Restrictions.eq("name", name));
            List<Microbiologia> resultList = cr.list();
            if(resultList.size() == 1){
                result = (Microbiologia) cr.list().get(0);
            }
        } catch (HibernateException he) {
            logger.error("error en getByName MicrobiologiaDAORDBMS "+he.getMessage());   
             } finally {
               if (sesion != null && sesion.isOpen()) {
                 sesion.close();
               }
        }

        return result;
    }

    @Override
    public void save(Microbiologia microbiologia){

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try{
            sesion.beginTransaction();
            sesion.save(microbiologia);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error("error en save MicrobiologiaDAORDBMS  "+he.getMessage());   
             } finally {
               if (sesion != null && sesion.isOpen()) {
                 sesion.close();
               }
             }

    }

    @Override
    public void delete(Microbiologia microbiologia){

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try{
            Microbiologia mToDelete = this.getByName(microbiologia.getName());
            sesion.beginTransaction();
            sesion.delete(mToDelete);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error("error en delete MicrobiologiaDAORDBMS  "+he.getMessage());   
             } finally {
               if (sesion != null && sesion.isOpen()) {
                 sesion.close();
               }
             }

    }
    
}
