/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.util.logging.Level;

import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.config.SpringContext;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgLaboratoriosDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.dao.CfgUsuariosModulosDAO;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.CfgPerfilesusuarios;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.front.ViewNames;

/**
 *
 * @author Marco Caracciolo
 */
public class SessionServiceImpl implements SessionService {

  private static final Logger LOGGER = LogManager.getLogger(SessionServiceImpl.class);

  public SessionServiceImpl() {

    this.clabDAO = SpringContext.getBean(CfgLaboratoriosDAO.class);
    this.cproceDao = SpringContext.getBean(CfgProcedenciasDAO.class);
    this.cseccDao = SpringContext.getBean(CfgSeccionesDAO.class);
    this.cumodDAO = SpringContext.getBean(CfgUsuariosModulosDAO.class);
  }

  private CfgLaboratoriosDAO clabDAO;
  private CfgProcedenciasDAO cproceDao;
  private CfgSeccionesDAO cseccDao;
  private CfgUsuariosModulosDAO cumodDAO;

  public CfgLaboratoriosDAO getClabDAO() {
    return clabDAO;
  }

  public void setClabDAO(CfgLaboratoriosDAO clabDAO) {
    this.clabDAO = clabDAO;
  }

  public CfgProcedenciasDAO getCproceDao() {
    return cproceDao;
  }

  public void setCproceDao(CfgProcedenciasDAO cproceDao) {
    this.cproceDao = cproceDao;
  }

  public CfgSeccionesDAO getCseccDao() {
    return cseccDao;
  }

  public void setCseccDao(CfgSeccionesDAO cseccDao) {
    this.cseccDao = cseccDao;
  }

  public CfgUsuariosModulosDAO getCumodDAO() {
    return cumodDAO;
  }

  public void setCumodDAO(CfgUsuariosModulosDAO cumodDAO) {
    this.cumodDAO = cumodDAO;
  }

  @Override
  public ModelAndView validarSesionUsuario(ModelAndView mav, HttpSession sesion, RedirectAttributes attributes,
      int idModulo) {

//    Session session = HibernateUtil.getSessionFactory().openSession();
//    session.close();
    if (sesion.getAttribute("usuario") != null) {
      try {
        DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        CfgPerfilesusuarios perfilUsuario = (CfgPerfilesusuarios) sesion.getAttribute("perfilUsuario");
        LOGGER.debug("Sesión validada usuario " + usuario.getDuNombres() + " " + usuario.getDuPrimerapellido());
        String base64Foto = (String) sesion.getAttribute("base64Foto");

        // ** modificado por cristian 24-10 mandar datos a front de usuario
        // ******************

        CfgLaboratorios clab = clabDAO.getLaboratorioById(perfilUsuario.getCpuIdlaboratorio());
        CfgProcedencias cpro = cproceDao.getProcedenciaById(perfilUsuario.getCpuIdprocedencia());
        CfgSecciones secciones = cseccDao.getSeccionById(perfilUsuario.getCpuIdseccion());
        // ******* fin modificacion ************

        if (idModulo > 0) {
          String permitirAcceso = cumodDAO.existeAccesoUsuario(usuario.getDuIdusuario(), (short) idModulo);
          if (permitirAcceso.equals("N")) {
            attributes.addFlashAttribute("mensaje", "Usuario no tiene permisos para acceder a esta página");
            mav = new ModelAndView("redirect:/" + ViewNames.HOME);
          } else {
            // mav.setViewName(ViewNames.getURL(idModulo))idModulo;
            // mav.setViewName(ViewNames.modulos.get(idModulo));
            mav.setViewName(ViewNames.viewModulos.get(idModulo));

            mav.addObject("usuario", usuario);
            mav.addObject("perfilUsuario", perfilUsuario);
            mav.addObject("base64Foto", base64Foto);
            mav.addObject("procedencia", cpro.getCpDescripcion());
            mav.addObject("seccion", secciones.getCsecDescripcion());
            mav.addObject("centro", cpro.getCfgCentroDeSalud().getCcdsDescripcion());
            mav.addObject("laboratorio", clab.getClabDescripcion());
            mav.addObject("idCentro", cpro.getCfgCentroDeSalud().getCcdsIdcentrodesalud());
          }
        } else {
          mav.setViewName(ViewNames.HOME);
          mav.addObject("usuario", usuario);
          mav.addObject("perfilUsuario", perfilUsuario);
          mav.addObject("base64Foto", base64Foto);
          mav.addObject("procedencia", cpro.getCpDescripcion());
          mav.addObject("seccion", secciones.getCsecDescripcion());
          mav.addObject("centro", cpro.getCfgCentroDeSalud().getCcdsDescripcion());
          mav.addObject("laboratorio", clab.getClabDescripcion());
          mav.addObject("idCentro", cpro.getCfgCentroDeSalud().getCcdsIdcentrodesalud());
        }
      } catch (BiosLISDAOException ex) {
        java.util.logging.Logger.getLogger(SessionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        attributes.addFlashAttribute("mensaje", "Error: " + ex.getMessage());
        mav = new ModelAndView("redirect:/" + ViewNames.LOGIN);
      }
    } else {
      LOGGER.debug("No hay sesión activa");
      attributes.addFlashAttribute("mensaje", "Debe iniciar sesión para ingresar a esta página");
      mav = new ModelAndView("redirect:/" + ViewNames.LOGIN);
    }

    return mav;
  }

}
