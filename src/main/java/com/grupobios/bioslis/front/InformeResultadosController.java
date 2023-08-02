package com.grupobios.bioslis.front;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.bs.InformeResultadosService;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

@Controller
public class InformeResultadosController {

  ModelAndView mav = new ModelAndView();
  private static final Logger logger = LogManager.getLogger(InformeResultadosController.class);

  @Autowired
  private InformeResultadosService infResultados;


  @RequestMapping("/InformeResultados")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    logger.debug("Test {}", infResultados.getClass().getCanonicalName());
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes,
        ViewNames.ID_INFORME_RESULTADOS);
    if (sesion.getAttribute("usuario") != null) {
      mav = infResultados.fill(mav);
    }
//        mav.setViewName("InformeResultados");
    
    //aqui se inserta la accion que realiza usuario en logusuarios ***********        
    if(usuario != null ) {
	    LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	    LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	    
	    leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	    leu.setLeuIdevento(11);              
	    leu.setLeuIdacciondato(0);               
	    leu.setLeuValornuevo("accede a Impresió de informes");
	    logUsuarioDao.insertLogEventosUsuario(leu);
    }
   //*-**************************************************************** 
    
    
    
    return mav;
  }

  public InformeResultadosService getInfResultados() {
    return infResultados;
  }

  public void setInfResultados(InformeResultadosService infResultados) {
    this.infResultados = infResultados;
  }

}
