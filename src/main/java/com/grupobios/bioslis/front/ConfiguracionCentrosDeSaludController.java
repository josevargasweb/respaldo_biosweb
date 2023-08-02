/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCentrosDeSaludDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.util.logging.Level;
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

/**
 *
 * @author Marco Caracciolo
 */
@Controller
public class ConfiguracionCentrosDeSaludController {
    private static final Logger LOGGER = LogManager.getLogger(ConfiguracionCentrosDeSaludController.class);
    ModelAndView mav = new ModelAndView();
    DatosUsuarios usuario = new DatosUsuarios();
    CfgCentrosDeSaludDAO ccdsdao = new CfgCentrosDeSaludDAO();
    
    @RequestMapping("/ConfiguracionCentrosDeSalud")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_CONFIGURACION_CENTROS_SALUD);
        
      //aqui se inserta la accion que realiza usuario en modulos ***********            
        
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Configuración de centros de salud");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        return mav;
    }
    
    @RequestMapping(value ="/ConfiguracionCentrosDeSalud", method = RequestMethod.POST)
    public ModelAndView agregarCentrodeSalud(CfgCentrosdesalud ccds, HttpServletRequest request, RedirectAttributes attributes){
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String codigo = request.getParameter("ccdsCodigo").toUpperCase();
            String nombre = request.getParameter("ccdsDescripcion").toUpperCase();
            ccds.setCcdsCodigo(codigo);
            ccds.setCcdsDescripcion(nombre);
          
            if (ccds.getCcdsActivo() == null){
                ccds.setCcdsActivo("N");
            }
            String msj = ccdsdao.agregarCentroDeSalud(ccds, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Centro de salud ingresado correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Centro de salud ya existe");
			}
        } catch (BiosLISDAOException ex) {
            LOGGER.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_CENTROS_SALUD);
    }
    
    @RequestMapping(value ="/ConfiguracionCentrosDeSalud", method = RequestMethod.POST, params = "update")
    public ModelAndView actualizarCentrodeSalud(CfgCentrosdesalud ccds, HttpServletRequest request, RedirectAttributes attributes){
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            Short idCentro = Short.parseShort(request.getParameter("idCentro"));
            String codigo = request.getParameter("ccdsCodigo").toUpperCase();
            String nombre = request.getParameter("ccdsDescripcion").toUpperCase();
            ccds.setCcdsIdcentrodesalud(idCentro);
            ccds.setCcdsCodigo(codigo);
            ccds.setCcdsDescripcion(nombre);
            if (ccds.getCcdsActivo() == null){
                ccds.setCcdsActivo("N");
            }
            String msj = ccdsdao.actualizarCentroDeSalud(ccds, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Centro de salud actualizado correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Centro de salud ya existe");
			}
            
        } catch (BiosLISDAOException ex) {
            LOGGER.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_CENTROS_SALUD);
    }

}
