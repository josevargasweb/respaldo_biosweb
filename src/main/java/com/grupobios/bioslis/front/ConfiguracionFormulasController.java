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
import com.grupobios.bioslis.bs.SessionService;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgAntecedentesDAO;
import com.grupobios.bioslis.dao.CfgEstadosresultadostestDAO;
import com.grupobios.bioslis.dao.CfgLaboratoriosDAO;
import com.grupobios.bioslis.dao.CfgTestDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.util.logging.Level;

/**
 *
 * @author Nacho
 */
@Controller
public class ConfiguracionFormulasController {

  private static Logger logger = LogManager.getLogger(ConfiguracionFormulasController.class);

  private CfgAntecedentesDAO cfgaDAO;
  private CfgEstadosresultadostestDAO cfgEstadosresultadostestDAO;

  public CfgEstadosresultadostestDAO getCfgEstadosresultadostestDAO() {
    return cfgEstadosresultadostestDAO;
  }

  public void setCfgEstadosresultadostestDAO(CfgEstadosresultadostestDAO cfgEstadosresultadostestDAO) {
    this.cfgEstadosresultadostestDAO = cfgEstadosresultadostestDAO;
  }

  ModelAndView mav = new ModelAndView();
  private static final SessionService SERVICE = ConfigSingletonSession.getInstance();

  @RequestMapping("/ConfiguracionFormulas")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = SERVICE.validarSesionUsuario(mav, sesion, attributes, 39);
    if (sesion.getAttribute("usuario") != null) {
      try {
          CfgTestDAO ctDAO = new CfgTestDAO();
          CfgLaboratoriosDAO clDAO = new CfgLaboratoriosDAO();
          //mav.addObject("listaTestNumerico", ctDAO.getTestResultadoNumerico());
          //mav.addObject("listaTestFormula", ctDAO.getTestResultadoFormula());
          try {
              mav.addObject("listaLaboratorios", clDAO.getLaboratorios());
              mav.addObject("listaEstadosTest", cfgEstadosresultadostestDAO.getAll());
          } catch (BiosLISDAOException e) {
              logger.error(e.getLocalizedMessage());
          }
          mav.addObject("listaTest", ctDAO.getTests());
          mav.addObject("listaAntecedentes", cfgaDAO.getAntecedentes());
      } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(ConfiguracionFormulasController.class.getName()).log(Level.SEVERE, null, ex);
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
	    leu.setLeuValornuevo("accede a Configuración de fórmulas");
	    logUsuarioDao.insertLogEventosUsuario(leu);
    }
   //*-************************************************************ 
    return mav;
  }

  public CfgAntecedentesDAO getCfgaDAO() {
    return cfgaDAO;
  }

  public void setCfgaDAO(CfgAntecedentesDAO cfgaDAO) {
    this.cfgaDAO = cfgaDAO;
  }

}
