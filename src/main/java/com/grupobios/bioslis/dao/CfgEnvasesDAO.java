/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.bs.ConfiguracionEnvasesService;
import com.grupobios.bioslis.bs.ConfiguracionEnvasesServiceImpl;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.EnvaseDTO;
import com.grupobios.bioslis.entity.CfgEnvases;
import com.grupobios.bioslis.entity.LogCfgtablas;

/**
 *
 * @author Nacho
 */
public class CfgEnvasesDAO {

  private static Logger logger = LogManager.getLogger(CfgMuestrasDAO.class);
  private static ValidacionMantenedor validador = new ValidacionMantenedor();
  LogCfgTablasDAO logCfgTablasDAO;

  public LogCfgTablasDAO getLogCfgTablasDAO() {
    return logCfgTablasDAO;
  }

  public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
    this.logCfgTablasDAO = logCfgTablasDAO;
  }

  public String insertEnvase(CfgEnvases cenv, Long idUsuario) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    try {
      if (validador.validarCodigoyAbreviado("CENV_CODIGO", "CENV_DESCRIPCION", "CFG_ENVASES", cenv.getCenvCodigo(),
          cenv.getCenvDescripcion())) {
        sesion.beginTransaction();
        sesion.save(cenv);
        // Se agregan antecedentes a log tablas **********************
        LogCfgtablas logTabla = new LogCfgtablas();
        logTabla.setCfgAccionesdatos(1);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_ENVASES");
        logTabla.setLctIdtabla(cenv.getCenvIdenvase());
        logTabla.setLctDescripcion(cenv.getCenvDescripcion());
        logCfgTablasDAO.insertLogTablas(logTabla, sesion);
        // *************************************************************
        sesion.getTransaction().commit();
        mensaje = "C";
      }

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
    return mensaje;
  }

  public String updateEnvase(CfgEnvases cenv, Long idUsuario) throws BiosLISDAOException {

    CfgEnvases envaseAntiguo = this.getEnvaseById(cenv.getCenvIdenvase());

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    try {

      if (validador.validarxId("CENV_CODIGO", "CENV_IDENVASE", "CENV_DESCRIPCION", "CFG_ENVASES", cenv.getCenvCodigo(),
          cenv.getCenvDescripcion(), BigDecimal.valueOf(cenv.getCenvIdenvase()))) {

        sesion.beginTransaction();
        sesion.update(cenv);
        sesion.getTransaction().commit();
        mensaje = "C";
        sesion.close();

        // Se agregan envases a log tablas **********************
        ConfiguracionEnvasesService configuracionEnvasesService = new ConfiguracionEnvasesServiceImpl();
        EnvaseDTO envaseDTO = new EnvaseDTO();
        // CfgEnvases envase = configuracionEnvasesService.getEnvaseById(idEnvase);
        envaseDTO.setCfgEnvases(cenv);
        if (cenv.getCenvImagenenvase() != null) {
          byte[] bdata = null;
          try {
            bdata = cenv.getCenvImagenenvase().getBytes(1, (int) cenv.getCenvImagenenvase().length());
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
          String base64Encoded;
          try {
            base64Encoded = new String(encodeBase64, "UTF-8");
            envaseDTO.setBase64Img(base64Encoded);
          } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

        }

        EnvaseDTO envaseDTO1 = new EnvaseDTO();
        // CfgEnvases envase = configuracionEnvasesService.getEnvaseById(idEnvase);
        envaseDTO1.setCfgEnvases(envaseAntiguo);
        if (envaseAntiguo.getCenvImagenenvase() != null) {
          byte[] bdata = null;
          try {
            bdata = envaseAntiguo.getCenvImagenenvase().getBytes(1, (int) envaseAntiguo.getCenvImagenenvase().length());
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
          String base64Encoded;
          try {
            base64Encoded = new String(encodeBase64, "UTF-8");
            envaseDTO1.setBase64Img(base64Encoded);
          } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

        }

        // aqui se dejan null para que comparador de objetos no grabe imagen
        envaseAntiguo.setCfgTests(null);
        envaseAntiguo.setCenvImagenenvase(null);
        cenv.setCenvImagenenvase(null);

        logCfgTablasDAO.comparadorObjetos(envaseAntiguo, cenv, 2, idUsuario.intValue(), cenv.getCenvIdenvase(),
            cenv.getCenvDescripcion(), "");
        // aqui se manda la imagen la cual no se puede agregar con el comparador de
        // objetos
        String base64Img = "";
        String base64ImgAntiguo = "";
        if (envaseDTO.getBase64Img() != null) {
          base64Img = envaseDTO.getBase64Img().length() > 90
              ? envaseDTO.getBase64Img().substring(envaseDTO.getBase64Img().length() - 90)
              : envaseDTO.getBase64Img();
        }
        if (envaseDTO1.getBase64Img() != null) {
          base64ImgAntiguo = envaseDTO1.getBase64Img().length() > 90
              ? envaseDTO1.getBase64Img().substring(envaseDTO1.getBase64Img().length() - 90)
              : envaseDTO1.getBase64Img();
        }
        logCfgTablasDAO.validarDatos(base64Img, base64ImgAntiguo, idUsuario, "CENV_IMAGENENVASE",
            cenv.getCenvIdenvase(), "CFG_ENVASES", cenv.getCenvDescripcion());
        // ***********************************************************
      }

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
    return mensaje;
  }

  public List<CfgEnvases> getEnvases() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select cenv.cenvIdenvase, cenv.cenvDescripcion "
          + "from com.grupobios.bioslis.entity.CfgEnvases cenv where cenv.cenvIdenvase > 0");
      List<CfgEnvases> listEnvases = query.list();
      sesion.close();
      return listEnvases;
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

  public List<CfgEnvases> getSelectListEnvases() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(
          "select cenv from CfgEnvases cenv where cenv.cenvIdenvase > 0 order by cenv.cenvSort asc, cenv.cenvDescripcion asc");
      List<CfgEnvases> listEnvases = query.list();
      sesion.close();
      return listEnvases;
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

  public CfgEnvases getEnvaseById(int idEnvase) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select cenv " + "from com.grupobios.bioslis.entity.CfgEnvases cenv "
          + "where cenv.cenvIdenvase = :idEnvase and cenv.cenvIdenvase > 0");
      query.setParameter("idEnvase", idEnvase);
      List<CfgEnvases> listEnvases = query.list();
      CfgEnvases cenv = listEnvases.get(0);
      sesion.close();
      return cenv;
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

  public List<CfgEnvases> getEnvasesFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    String codigo = filtros.get("codigo").toUpperCase();
    String descripcion = filtros.get("descripcion").toUpperCase();
    String activo = filtros.get("activo");
    int idMuestra = Integer.parseInt(filtros.get("idMuestra"));
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgEnvases> listEnvases = null;
    try {
      String sql = "select cenv from CfgEnvases cenv" + " where cenv.cenvCodigo like :codigo"
          + " and cenv.cenvIdenvase > 0" + " and cenv.cenvDescripcion like :descripcion";
      if (activo.equals("S")) {
        sql += " and cenv.cenvActivo = 'S'";
      }
      if (idMuestra > 0) {
        sql += " and cenv.cenvIdtipomuestra = :idMuestra";
      }
      sql += " order by cenv.cenvSort asc, cenv.cenvDescripcion asc";
      Query query = sesion.createQuery(sql);
      query.setParameter("codigo", "%" + codigo + "%");
      query.setParameter("descripcion", "%" + descripcion + "%");
      if (idMuestra > 0) {
        query.setParameter("idMuestra", idMuestra);
      }
      listEnvases = query.list();

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
    return listEnvases;
  }

}
