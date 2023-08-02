package com.grupobios.bioslis.front;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

@Controller
public class ImpresionEtiquetasController {

  private static final Logger logger = LogManager.getLogger(ImpresionEtiquetasController.class);

  ModelAndView mav = new ModelAndView();

  @CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*", methods = { RequestMethod.OPTIONS,
      RequestMethod.POST, RequestMethod.GET })
  @RequestMapping("/ImpresionEtiquetas")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes)
      throws Exception {

    HttpSession sesion = (HttpSession) request.getSession();

    Long idUsuario = 0L;
    DatosUsuarios usuario = null;

    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, 61);
    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

    }
    
  //aqui se inserta la accion que realiza usuario en logusuarios ***********   
    if(usuario != null) {
	    LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	    LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	    
	    leu.setLeuIdusuario(  idUsuario.intValue());
	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	    leu.setLeuIdevento(11);              
	    leu.setLeuIdacciondato(0);               
	    leu.setLeuValornuevo("accede a Impresión de etiquetas");
	    logUsuarioDao.insertLogEventosUsuario(leu);
    }
   //*-**************************************************************** 
    return mav;
  }

}
