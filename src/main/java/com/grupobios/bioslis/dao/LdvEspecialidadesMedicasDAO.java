/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LdvSexo;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.grupobios.bioslis.entity.LdvEspecialidadesmedicas;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

public class LdvEspecialidadesMedicasDAO {

  private static Logger logger = LogManager.getLogger(LdvEspecialidadesMedicasDAO.class);

    public List<LdvEspecialidadesmedicas> getEspecialidadesMedicas() throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select lem.ldvemIdespecialidadmedica,lem.ldvemDescripcion from com.grupobios.bioslis.entity.LdvEspecialidadesmedicas lem");
            List<LdvEspecialidadesmedicas> listaEspecialidadesMedicas = query.list();
            return listaEspecialidadesMedicas;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
    }

    public LdvEspecialidadesmedicas getEspecialidadesMedicasById(int id) throws BiosLISDAOException {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select ids from com.grupobios.bioslis.entity.LdvEspecialidadesmedicas ids "
                    + "where ids.ldvemIdespecialidadmedica = :id");
            query.setParameter("id", id);
            List<LdvEspecialidadesmedicas> lista = query.list();

            LdvEspecialidadesmedicas list = lista.get(0);
            return list;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
    }

}
