package com.grupobios.bioslis.front;

import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author Nacho
 */
@Controller
public class ConfiguracionGlosasController {

  private static final org.apache.logging.log4j.Logger logger = LogManager
      .getLogger(ConfiguracionGlosasController.class);
  ModelAndView mav = new ModelAndView();

  @RequestMapping("/ConfiguracionGlosas")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes,
        ViewNames.ID_CONFIGURACION_GLOSAS);
    if (sesion.getAttribute("usuario") != null) {
      CfgSeccionesDAO csecDAO = new CfgSeccionesDAO();
      try {
        mav.addObject("listaSecciones", csecDAO.getSecciones());
      } catch (BiosLISDAOException ex) {
        Logger.getLogger(ConfiguracionGlosasController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    // aqui se inserta la accion que realiza usuario en modulos ***********

    if (usuario != null) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(11);
      leu.setLeuIdacciondato(0);
      leu.setLeuValornuevo("accede a Configuración de glosas");
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-************************************************************
    return mav;
  }

  public Integer getNull() {
    Integer re = null;
    return re;
  }
}
