/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;


import com.grupobios.bioslis.bs.ConfiguracionCentrosSaludService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgGlosas;
import com.grupobios.bioslis.entity.DatosUsuarios;

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
public class ConfiguracionCentrosSaludRestController {
    
	ConfiguracionCentrosSaludService configuracionCentrosSaludService;


 
    public ConfiguracionCentrosSaludService getConfiguracionCentrosSaludService() {
		return configuracionCentrosSaludService;
	}



	public void setConfiguracionCentrosSaludService(ConfiguracionCentrosSaludService configuracionCentrosSaludService) {
		this.configuracionCentrosSaludService = configuracionCentrosSaludService;
	}



	@PostMapping("/api/centrosSalud/insert")
    public ResponseTemplateGen<String> insertCentroSalud(@RequestBody CfgCentrosdesalud ccds, @Context HttpServletRequest context) {
		 HttpSession sesion = (HttpSession) context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	String msj = configuracionCentrosSaludService.insertCentroSalud(ccds, usuario.getDuIdusuario());//ccdsdao.agregarCentroDeSalud(ccds);
            if(msj.equals("C")) {
            	return new ResponseTemplateGen<String>(msj, 200, "Centro ingresado corrrectamente");
            }else {
            	return new ResponseTemplateGen<String>(msj, 300, "Centro ya existente");
            }
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }
	@PostMapping("/api/centrosSalud/update")
    public ResponseTemplateGen<String> updateCentroSalud(@RequestBody CfgCentrosdesalud ccds, @Context HttpServletRequest context) {
		 HttpSession sesion = (HttpSession) context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
        	String msj = configuracionCentrosSaludService.updateCentroSalud(ccds, usuario.getDuIdusuario());//ccdsdao.agregarCentroDeSalud(ccds);
            
            if(msj.equals("C")) {
            	return new ResponseTemplateGen<String>(msj, 200, "Centro actualiado corrrectamente");
            }else {
            	return new ResponseTemplateGen<String>(msj, 300, "Centro ya existente");
            }
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }
    
}
