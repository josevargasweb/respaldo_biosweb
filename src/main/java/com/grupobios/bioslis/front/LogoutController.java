/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupobios.bioslis.front;

import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author Marco
 */
@Controller
public class LogoutController {
  ModelAndView mav = new ModelAndView();
  DatosUsuariosDAO duDao = new DatosUsuariosDAO();
  DatosFichasDAO dfDao = new DatosFichasDAO();
  private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

  @RequestMapping(value = "/Logout", method = RequestMethod.GET)
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    // mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion,
    // attributes, ViewNames.LOGOUT);
    if (sesion.getAttribute("usuario") != null) {
      LOGGER.debug("Cerrando sesión");
      // esto es para no repetir el mismo código jaja
      invalidarSesion(sesion);
    }

    // ***** SE AGREGA EVENTO A LOG USUARIO *********************

    LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
    LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();

    leu.setLeuIdusuario((int) usuario.getDuIdusuario());
    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    leu.setLeuIdevento(8);
    leu.setLeuIdacciondato(0);
    leu.setLeuValornuevo("BiosLIS web");
    logUsuarioDao.insertLogEventosUsuario(leu);
    // *-************************************************************
    return new ModelAndView("redirect:/" + ViewNames.LOGIN);
  }

  // Agregar manejo de excepción.
  // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    
    @RequestMapping(value="/Logout",method = RequestMethod.POST, params = "sesionExpired")
    public void expiraSesion(HttpServletRequest request, HttpServletResponse response){
        HttpSession sesion=request.getSession();
        DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (sesion.getAttribute("usuario")!=null){
            LOGGER.debug("Sesión expirada");
            //***** SE AGREGA EVENTO A LOG USUARIO *********************
            
            LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
            LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
            
            leu.setLeuIdusuario((int) usuario.getDuIdusuario());
            leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
            leu.setLeuIdevento(8);
            leu.setLeuIdacciondato(0);     
            leu.setLeuValornuevo("BiosLIS web");
            logUsuarioDao.insertLogEventosUsuario(leu);
             //*-************************************************************   
            invalidarSesion(sesion);
        }
        
        
        //return new ModelAndView("redirect:/Login");

    }


  private void invalidarSesion(HttpSession session) {
    try {
      DatosUsuarios usuario = (DatosUsuarios) session.getAttribute("usuario");
      LOGGER.debug("Usuario: " + usuario.getDuNombres() + " " + usuario.getDuPrimerapellido());
      // Si el usuario tomó ordenes en toma de muestras desactivarlas
      // Marco del futuro debe crear una condición que valide que usuario sea
      // flebotomista
      dfDao.volverANormlidadOrdenesUsuario(usuario.getDuIdusuario());
      session.invalidate();
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(LogoutController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
