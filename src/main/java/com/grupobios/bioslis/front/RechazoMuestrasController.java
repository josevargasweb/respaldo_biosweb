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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCausasrechazosmuestrasDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marco.caracciolo
 */
@Controller
public class RechazoMuestrasController {

  ModelAndView mav = new ModelAndView();
  DatosUsuarios usuario = new DatosUsuarios();
  private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(RecepcionMuestrasController.class);

  @RequestMapping("/RechazoMuestras")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_RECHAZO_MUESTRAS);
    if (sesion.getAttribute("usuario") != null && !mav.getModel().isEmpty()) {
        try {
        	usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            LdvTiposdocumentosDAO ldvTiposdocumentos_dao = new LdvTiposdocumentosDAO();
            CfgCausasrechazosmuestrasDAO ccdao = new CfgCausasrechazosmuestrasDAO();
            
            mav.addObject("listaTiposDocumentos", ldvTiposdocumentos_dao.getTiposDocumentoRutPasaporte());
            mav.addObject("listCausaRechazoMuestras", ccdao.getCausasRechazoMuestras());
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            mav.addObject("errorPageLoad", ex.getMessage());
        }
    }
    //aqui se inserta la accion que realiza usuario en logusuarios ***********  
    if(usuario != null) {
	    LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	    LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	    
	    leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	    leu.setLeuIdevento(11);              
	    leu.setLeuIdacciondato(0);               
	    leu.setLeuValornuevo("accede a Rechazo de muestras");
    logUsuarioDao.insertLogEventosUsuario(leu);
    }
   //*-**************************************************************** 
    return mav;
  }

}
