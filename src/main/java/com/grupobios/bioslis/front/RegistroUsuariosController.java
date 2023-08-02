/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCentrosDeSaludDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.LdvCargosUsuariosDAO;
import com.grupobios.bioslis.dao.LdvProfesionesUsuariosDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Marco Caracciolo
 */
@Controller
public class RegistroUsuariosController {
    
    ModelAndView mav = new ModelAndView();
    //private static final Logger LOGGER = LogManager.getLogger(RegistroUsuariosController.class);
    
    @RequestMapping("/RegistroUsuarios")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) throws BiosLISDAOException {
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_REGISTRO_USUARIOS);
        if (sesion.getAttribute("usuario")!=null){
            LdvCargosUsuariosDAO lcuDao = new LdvCargosUsuariosDAO();
            CfgCentrosDeSaludDAO cmcDao = new CfgCentrosDeSaludDAO();
            LdvSexoDAO lsDao = new LdvSexoDAO();
            CfgProcedenciasDAO cpDao = new CfgProcedenciasDAO();
            LdvProfesionesUsuariosDAO lpuDao = new LdvProfesionesUsuariosDAO();
            mav.addObject("listaCargosUsuario", lcuDao.getCargosUsuarios());
            mav.addObject("listaCentrosdeSalud", cmcDao.getCentrosDeSalud());
            mav.addObject("listaSexo", lsDao.getSexo());
            //mav.addObject("listaProcedencias", cpDao.getProcedencias());
            mav.addObject("listaProfesiones", lpuDao.getProfesionesUsuarios());
        }
        
   //aqui se inserta la accion que realiza usuario en modulos ***********            
        
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Configuración de registro usuarios");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
	return mav;
    }

}
