/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * 
 * @author Jan Perkov
 */
public class DatosUsuariosDAO {

    private static Logger logger = LogManager.getLogger(DatosUsuariosDAO.class);
    
    public DatosUsuarios getUsuarioById(long idUsuario) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("from DatosUsuarios du "
                    + "left join fetch du.ldvCargosusuarios "
                    + "left join fetch du.ldvSexo "
                    + "left join fetch du.ldvProfesionesusuarios "
                    + "where du.duIdusuario = :idUsuario");
            query.setParameter("idUsuario", idUsuario);
            DatosUsuarios usuario = (DatosUsuarios) query.uniqueResult();
            return usuario;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    public DatosUsuarios getUsuarioByRun(String run) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("from DatosUsuarios du "
                    + "where du.duRun = :run");
            query.setParameter("run", run);
            DatosUsuarios usuario = (DatosUsuarios) query.uniqueResult();
            return usuario;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    public DatosUsuarios getUsuarioByUsername(String username) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("from DatosUsuarios du "
                    + "where du.duUsuario = :username");
            query.setParameter("username", username);
            DatosUsuarios usuario = (DatosUsuarios) query.uniqueResult();
            return usuario;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
        
    public List<DatosUsuarios> getUsuarios() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("from DatosUsuarios du order by du.duUsuario");
            List<DatosUsuarios> listaUsuarios = query.list();
         sesion.close();
            return listaUsuarios;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
        
    public List<DatosUsuarios> getUsuariosLikeNombre(String nombre) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("from DatosUsuarios du "
                    + "where du.duNombres like :nombre "
                    + "or du.duPrimerapellido like :nombre "
                    + "or du.duSegundoapellido like :nombre");
            query.setParameter("nombre", "%" + nombre + "%");
            List<DatosUsuarios> listaUsuarios = query.list();
            sesion.close();
            return listaUsuarios;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public List<DatosUsuarios> getUsuariosByCentro(Byte idCentro) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "select du.* from datos_usuarios du "
                    + "join cfg_perfilesusuarios cpu on cpu.cpu_idusuario = du.du_idusuario "
                    + "where cpu.cpu_idcentrodesalud = :idCentro";
            Query query = sesion.createSQLQuery(sql)
                    .addEntity("du", DatosUsuarios.class);
            query.setParameter("idCentro", idCentro);
            List<DatosUsuarios> listaUsuarios = query.list();
            sesion.close();
            return listaUsuarios;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    public List<DatosUsuarios> getUsuariosByActivo(String activo) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "select du.* from datos_usuarios du "
                    + "join cfg_perfilesusuarios cpu on cpu.cpu_idusuario = du.du_idusuario "
                    + "WHERE du.DU_ACTIVO = :activo";
            Query query = sesion.createSQLQuery(sql)
                    .addEntity("du", DatosUsuarios.class);
            query.setParameter("activo", activo);
            List<DatosUsuarios> listaUsuarios = query.list();
            sesion.close();
            return listaUsuarios;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    public List<DatosUsuarios> getUsuariosByCargo(Short idCargo) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from DatosUsuarios du "
                    + "left join fetch du.ldvCargosusuarios lcu "
                    + "where lcu.ldvcuIdcargo = :idCargo";
            Query query = sesion.createQuery(sql);
            query.setParameter("idCargo", idCargo);
            List<DatosUsuarios> listaUsuarios = query.list();
            sesion.close();
            return listaUsuarios;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void agregarUsuario(DatosUsuarios du) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.save(du);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void actualizarUsuario(DatosUsuarios du) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.update(du);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public String getPassword(long idUsuario) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "select du.duPassword from DatosUsuarios du "
                    + "where du.duIdusuario = :idUsuario";
            Query query = sesion.createQuery(sql);
            query.setParameter("idUsuario", idUsuario);
            String pwd = (String) query.uniqueResult();
            sesion.close();
            return pwd;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public List<DatosUsuarios> listaFlebotomistas() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "select * from datos_usuarios du\n" +
                        "join cfg_perfilesusuarios cpu on cpu.cpu_idusuario = du.du_idusuario\n" +
                        "where cpu.cpu_flebotomista = 'S'";
            Query query = sesion.createSQLQuery(sql).addEntity(DatosUsuarios.class);
            List<DatosUsuarios> list = query.list();
            sesion.close();
            return list;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public List<DatosUsuarios> listaRecepcionistas() throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "select * from datos_usuarios du " +
                        "join cfg_perfilesusuarios cpu on cpu.cpu_idusuario = du.du_idusuario " +
                        "where cpu.cpu_recepcionxexamen = 'S'";
            Query query = sesion.createSQLQuery(sql).addEntity(DatosUsuarios.class);
            List<DatosUsuarios> list = query.list();
            sesion.close();
            return list;
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
