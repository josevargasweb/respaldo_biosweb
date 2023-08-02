package com.grupobios.bioslis.front;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
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
public class ConfiguracionEtiquetasController {

    
    ModelAndView mav = new ModelAndView();

    @RequestMapping(value = "/ConfiguracionEtiquetas", method = RequestMethod.GET)
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {
    	
    	 HttpSession sesion = (HttpSession) request.getSession();
         DatosUsuarios usuario = new DatosUsuarios();
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_CONFIGURACION_ETIQUETAS);
         
         //aqui se inserta la accion que realiza usuario en modulos ***********            
         
         if(usuario != null) {
         	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
         	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
         	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
     	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
     	    leu.setLeuIdevento(11);              
     	    leu.setLeuIdacciondato(0);               
     	    leu.setLeuValornuevo("accede a Configuración de formato de etiquetas");
     	    logUsuarioDao.insertLogEventosUsuario(leu);
         }
        //*-************************************************************ 
         
         
         
      //mav.setViewName("/ConfiguracionEtiquetas");
      return mav;
    }
}
