/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;


import com.grupobios.bioslis.bs.UsuarioService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.DatosUsuariosDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class LoginRestController {
    
	UsuarioService usuarioService;

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
    @PostMapping("/api/login")
    public ResponseTemplateGen<String> Login(@RequestBody DatosUsuariosDTO user, @Context HttpServletRequest context){
       // HttpSession sesion = context.getSession(); 
        try {
        	Object[] response = usuarioService.getUsuarioByUsername(user,context);
        	
            return new ResponseTemplateGen<>(response[0].toString(), Integer.parseInt(response[1].toString()), "Ok");
        } catch (BiosLISDAOException ex) {
            
            return new ResponseTemplateGen<>("Error.", 500, ex.getMessage());
        }
    }
}
