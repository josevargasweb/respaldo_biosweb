/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.ItemsMenuDTO;
import com.grupobios.bioslis.entity.CfgUsuariosModulos;
import org.hibernate.HibernateException;

/**
 *
 * @author Marco Caracciolo
 */
public class CfgUsuariosModulosDAO {

  private static final Logger logger = LogManager.getLogger(CfgUsuariosModulosDAO.class);
  
  private static final String queryModulosxUsuario = "SELECT "
  		+ "CBM_IDBIOSLISMODULO cbmIdbioslismodulo,CBM_NOMBREMODULO cbmNombremodulo, CBM_PRIMERNIVEL cbmPrimernivel, "
  		+ "CBM_SEGUNDONIVEL cbmSegundonivel,CBM_TERCERNIVEL cbmTercernivel, CBM_ACTIVO cbmActivo "
  		+ "FROM CFG_USUARIOSMODULOS um "
  		+ "INNER JOIN CFG_BIOSLISMODULOS bm ON um.CUMOD_IDBIOSLISMODULO = bm.CBM_IDBIOSLISMODULO  WHERE um.CUMOD_IDUSUARIO = :idUsuario "
  		+ "ORDER BY 3,4,5";

  public List<CfgUsuariosModulos> listUsuariosModulos(long idUsuario) throws BiosLISDAOException {
    Session sesion = null;
    List<CfgUsuariosModulos> list = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        String sql = "from CfgUsuariosModulos cumod " + "join fetch cumod.datosUsuarios du "
            + "where du.duIdusuario = :idUsuario";
        Query query = sesion.createQuery(sql);
        query.setParameter("idUsuario", idUsuario);
        list = query.list();
        sesion.close();
        
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }
    return list;
  }
  public List<ItemsMenuDTO> listUsuariosxModulos(long idUsuario) throws BiosLISDAOException {
	    Session sesion = null;
	    List<ItemsMenuDTO> list = null;
	    try {
	        sesion = HibernateUtil.getSessionFactory().openSession();
	        Query query = sesion.createSQLQuery(queryModulosxUsuario).setResultTransformer(Transformers.aliasToBean(ItemsMenuDTO.class));
	        query.setParameter("idUsuario", idUsuario);
	        list = query.list();
	        sesion.close();
	        
	    } catch (HibernateException he) {
	        logger.error(he.getMessage());
	        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	    return list;
	  }
  public String existeAccesoUsuario(long idUsuario, short idModulo) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        String sql = "from CfgUsuariosModulos cumod " + "where cumod.datosUsuarios.duIdusuario = :idUsuario "
            + "and cumod.id.cumodIdbioslismodulo = :idModulo";
        Query query = sesion.createQuery(sql);
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("idModulo", idModulo);
        String existe = "N";
        List<CfgUsuariosModulos> list = query.list();
        if (list.size() > 0) {
          existe = "S";
        }
        sesion.close();

        logger.debug("Tiene acceso a modulo {} : {}", idModulo, existe);
        return existe;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }
  }

  public void agregarUsuarioModulo(CfgUsuariosModulos cumod) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.save(cumod);
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

  public void eliminarUsuarioModulo(CfgUsuariosModulos cumod) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.delete(cumod);
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

}
