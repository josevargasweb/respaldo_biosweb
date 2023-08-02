/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.util.logging.Level;

/**
 *
 * @author Nacho
 */
@Controller
public class RecepcionMuestrasController {
  ModelAndView mav = new ModelAndView();
  DatosUsuarios usuario = new DatosUsuarios();
  private final static Logger LOGGER = LogManager.getLogger(RecepcionMuestrasController.class);

  @RequestMapping("/RecepcionMuestras")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_RECEPCION_MUESTRAS);
    if (sesion.getAttribute("usuario") != null && !mav.getModel().isEmpty()) {
        try {
        	 DatosUsuariosDAO duDao = new DatosUsuariosDAO();
            mav.addObject("listaUsuarios", duDao.getUsuarios());
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RecepcionMuestrasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //aqui se inserta la accion que realiza usuario en modulos ***********  
    if(usuario != null) {
	    LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	    LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	    
	    leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	    leu.setLeuIdevento(11);              
	    leu.setLeuIdacciondato(0);               
	    leu.setLeuValornuevo("accede a Recepción de muestras");
	    logUsuarioDao.insertLogEventosUsuario(leu);
    }
   //*-************************************************************ 
    return mav;
  }

}
