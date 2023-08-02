package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.SistemaConfiguraciones;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marco Caracciolo
 */
public class SistemaConfiguracionesDAO {

  private static Logger logger = LogManager.getLogger(SistemaConfiguracionesDAO.class);
    
    public BigDecimal getDiasCaducaPassword(Long idUsuario) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "select sc.sc_pwdiascaducapassword dcp from sistema_configuraciones sc " +
                "inner join cfg_centrosdesalud ccs on ccs.ccds_idcentrodesalud = sc.sc_idcentrodesalud " +
                "inner join cfg_perfilesusuarios cpu on cpu.cpu_idcentrodesalud = ccs.ccds_idcentrodesalud " +
                "inner join datos_usuarios du on du.du_idusuario = cpu.cpu_idusuario " +
                //"where du.du_idusuario = " + idUsuario;
                "where du.du_idusuario = :idUsuario";
            Query query = sesion.createSQLQuery(sql);
            query.setLong("idUsuario", idUsuario);
            BigDecimal dcp = query.uniqueResult() != null ? (BigDecimal) query.uniqueResult() : new BigDecimal(BigInteger.ZERO);
            return dcp;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public SistemaConfiguraciones getSistemaConfiguracionesByCentro(byte idCentroSalud) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from SistemaConfiguraciones where scIdcentrodesalud = :idCentroSalud";
            Query query = sesion.createQuery(sql);
            query.setParameter("idCentroSalud", idCentroSalud);
            SistemaConfiguraciones sc = (SistemaConfiguraciones) query.uniqueResult();
            return sc;
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
