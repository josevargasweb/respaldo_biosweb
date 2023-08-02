/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Base64;

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
import com.grupobios.bioslis.dao.CfgPerfilesusuariosDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgPerfilesusuarios;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.common.CifradoMD5;



/**
 *
 * @author Marco Caracciolo
 */
@Controller
public class LoginController {

  ModelAndView mav = new ModelAndView();
  DatosUsuariosDAO duDao = new DatosUsuariosDAO();
  private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

  @RequestMapping(value = "/Login", method = RequestMethod.GET)
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response) {
    
    mav.setViewName("Login");
    return mav;
  }
/*
  @RequestMapping(value = "/Login", method = RequestMethod.POST)
  public ModelAndView getValidaLogin(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) throws UnsupportedEncodingException, SQLException {
	  DatosUsuarios usuario = new DatosUsuarios();
      try {
          String usermame = request.getParameter("username").toUpperCase();
          String password = request.getParameter("password");
          usuario = duDao.getUsuarioByUsername(usermame);
          int validaLogin = 1;
          
          if (usuario == null) {
              attributes.addFlashAttribute("mensaje", "Usuario o contraseña incorrectos");
              LOGGER.debug("Usuario no existe");
              // validaLogin = false;
              validaLogin = 0;
              return new ModelAndView("redirect:/Login");
          } else {
              String activo = usuario.getDuActivo();
              String pwd = CifradoMD5.encode(password);
              
              if (!pwd.equals(usuario.getDuPassword())) {
                  attributes.addFlashAttribute("mensaje", "Usuario o contraseña incorrectos");
                  LOGGER.debug("Password incorrecta");
                  // validaLogin = false;
                  validaLogin = 0;
                  return new ModelAndView("redirect:/Login");
              } else {
                  // Si la contraseña ha caducado
                  if (usuario.getDuPasswordexpira().equals("S")
                          && (usuario.getDuFechacaducapassword().equals(BiosLisCalendarService.getInstance().getDate())
                          || usuario.getDuFechacaducapassword().before(BiosLisCalendarService.getInstance().getDate()))) {
                      attributes.addFlashAttribute("mensaje",
                              "Su contraseña ha caducado. Ingrese una nueva para poder acceder al sistema.");
                      LOGGER.debug("Password caducada");
                      // validaLogin = false;
                      validaLogin = 2;
                  }
                  if (activo.equals("N")) {
                      attributes.addFlashAttribute("mensaje", "Usuario inactivo");
                      LOGGER.debug("Usuario inactivo");
                      // validaLogin = false;
                      validaLogin = 0;
                      return new ModelAndView("redirect:/Login");
                  }
              }
          }
          
          switch (validaLogin) {
              case 1: // ingreso correcto
                  LOGGER.debug("Ingreso exitoso");
                  mav = new ModelAndView("redirect:/Home");
                  HttpSession sesion = (HttpSession) request.getSession();
                  sesion.setAttribute("usuario", usuario);
                  CfgPerfilesusuariosDAO cpuDAO = new CfgPerfilesusuariosDAO();
                  CfgPerfilesusuarios perfilUsuario = cpuDAO.getPerfilesUsuariosById(usuario.getDuIdusuario());
                  sesion.setAttribute("perfilUsuario", perfilUsuario);
                  // La sesión durará el tiempo indicado
                  // sesion.setMaxInactiveInterval(10*60);
                  String base64Foto = "";
                  // Foto de usuario en formato base64 se guarda en otra variable de sesión
                  if (usuario.getDuFoto() != null) {
                      byte[] bdata1 = usuario.getDuFoto().getBytes(1, (int) usuario.getDuFoto().length());
                      byte[] encodeBase64 = Base64.getEncoder().encode(bdata1);
                      base64Foto = new String(encodeBase64, "UTF-8");
                  }
                  sesion.setAttribute("base64Foto", base64Foto);
                  // setear fecha última conexión
                  usuario.setDuFechaultimaconexion(BiosLisCalendarService.getInstance().getTS());
                  duDao.actualizarUsuario(usuario);
                  break;
              case 2: // password caducada
                  HttpSession sesion2 = (HttpSession) request.getSession();
                  sesion2.setAttribute("usuario", usuario);
                  // setear fecha última conexión
                  usuario.setDuFechaultimaconexion(BiosLisCalendarService.getInstance().getTS());
                  duDao.actualizarUsuario(usuario);
                  mav = new ModelAndView("redirect:/CambioPassword");
                  break;
              default: //
            	  
                  mav = new ModelAndView("redirect:/Login");
                  break;
          }
          
          //return mav;
      } catch (BiosLISDAOException ex) {
          LOGGER.error(ex.getMessage());
          attributes.addFlashAttribute("mensaje", "Error: " +  ex.getMessage());
          mav = new ModelAndView("redirect:/Login");
      }
      //***** SE AGREGA EVENTO A LOG USUARIO *********************
     
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
      
      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(7);
      leu.setLeuIdacciondato(0);
      leu.setLeuValornuevo("BiosLIS web");
      
    
      logUsuarioDao.insertLogEventosUsuario(leu);
       //*-************************************************************    
      return mav;
  }*/

}
