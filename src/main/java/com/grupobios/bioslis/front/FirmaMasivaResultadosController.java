package com.grupobios.bioslis.front;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

@Controller
public class FirmaMasivaResultadosController {



  ModelAndView mav = new ModelAndView();

  @RequestMapping("/FirmaMasivaResultados")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response) throws Exception {
	  HttpSession sesion = (HttpSession) request.getSession();
	    DatosUsuarios usuario = new DatosUsuarios();
	    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav.setViewName("FirmaMasivaResultados");
    
    //aqui se inserta la accion que realiza usuario en logusuarios ***********      
    if(usuario != null) {
	    LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	    LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	    
	    leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	    leu.setLeuIdevento(11);              
	    leu.setLeuIdacciondato(0);               
	    leu.setLeuValornuevo("accede a Firma masiva de resultados");
	    logUsuarioDao.insertLogEventosUsuario(leu);
    }
   //*-**************************************************************** 
    return mav;
  }

  public FirmaMasivaResultadosController() {
    // TODO Auto-generated constructor stub
  }

}
