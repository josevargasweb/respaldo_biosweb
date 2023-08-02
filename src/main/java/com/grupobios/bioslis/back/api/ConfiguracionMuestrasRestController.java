/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.ConfiguracionMuestrasService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
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
public class ConfiguracionMuestrasRestController {
    
    ConfiguracionMuestrasService configuracionMuestrasService;

    public ConfiguracionMuestrasService getConfiguracionMuestrasService() {
        return configuracionMuestrasService;
    }

    public void setConfiguracionMuestrasService(ConfiguracionMuestrasService configuracionMuestrasService) {
        this.configuracionMuestrasService = configuracionMuestrasService;
    }
    
    @PostMapping("/api/muestras/filtro")
    public ResponseTemplateGen<List<CfgMuestras>> getMuestrasFiltro(@RequestBody HashMap<String, String> filtros , @Context HttpServletRequest context){
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<CfgMuestras> list = configuracionMuestrasService.getMuestrasFiltro(filtros);
            return new ResponseTemplateGen<>(list, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PostMapping("/api/muestras/insert")
    public ResponseTemplateGen<String> insertMuestra(@RequestBody CfgMuestras cmue, @Context HttpServletRequest context){
    	 HttpSession sesion = (HttpSession) context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            String msj = configuracionMuestrasService.insertMuestra(cmue, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	return new ResponseTemplateGen<>(null, 200, "Ingresado Correctamente");
			}else {
				return new ResponseTemplateGen<>(null, 300, "Ya existe");
			}
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    @PostMapping("/api/muestras/update")
    public ResponseTemplateGen<String> updateMuestra(@RequestBody CfgMuestras cmue, @Context HttpServletRequest context){
    	 HttpSession sesion = (HttpSession) context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            String msj = configuracionMuestrasService.updateMuestra(cmue, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	return new ResponseTemplateGen<>(null, 200, "Actualizado Correctamente");
			}else {
				return new ResponseTemplateGen<>(null, 300, "Ya existe");
			}
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
}
