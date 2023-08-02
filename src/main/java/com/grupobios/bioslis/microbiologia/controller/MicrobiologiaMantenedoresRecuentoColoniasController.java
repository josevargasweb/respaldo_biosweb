package com.grupobios.bioslis.microbiologia.controller;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.front.ViewNames;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MicrobiologiaMantenedoresRecuentoColoniasController {
    
    ModelAndView mav = new ModelAndView();
    
    @RequestMapping("/MicrobiologiaMantenedoresRecuentoColonias")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_MICROBIOLOGIA_RECUENTOCOLONIAS);
        
        //aqui se inserta la accion que realiza  usuario al ingresar a un modulo***********    
        if(usuario != null) {
	        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	        
	        leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	        leu.setLeuIdevento(11);              
	        leu.setLeuIdacciondato(0);               
	        leu.setLeuValornuevo("accede a Configuración de recuento de colonias");
	        logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-**************************************************************** 
        return mav;
    }
}
