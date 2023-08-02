/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author eric.nicholls
 */
@Controller 
public class DefController {
    
    ModelAndView mav = new ModelAndView();

    @RequestMapping(value="/",method = RequestMethod.GET)
    public ModelAndView pageLoad(){
        mav.setViewName("Login");
        return mav;
    }   
    @RequestMapping(value="/Estilos",method = RequestMethod.GET)
    public ModelAndView pageEstilosLoad(){
        mav.setViewName("Estilos");
        return mav;
    }   
   
    
    //creado por cristian para prueba excel 17-11
    @RequestMapping(value="/PortalEstadistica",method = RequestMethod.GET)
    public ModelAndView prueba(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
    	HttpSession sesion = (HttpSession) request.getSession();
    	DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_PORTAL_ESTADISTICA);
    	
        
  //aqui se inserta la accion que realiza usuario en modulos ***********            
        
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Estadística visualizacion de datos");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        
        //mav.setViewName("PortalEstadistica");
        return mav;
    }   

}
