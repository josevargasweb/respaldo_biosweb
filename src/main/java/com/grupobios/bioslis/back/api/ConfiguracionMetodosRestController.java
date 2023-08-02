/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.DerivadoresService;
import com.grupobios.bioslis.bs.MetodosService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgMetodos;
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
public class ConfiguracionMetodosRestController {
    
    MetodosService metodosService;

	public MetodosService getMetodosService() {
		return metodosService;
	}

	public void setMetodosService(MetodosService metodosService) {
		this.metodosService = metodosService;
	}
	@PostMapping("/api/metodos/insert")
    public ResponseTemplateGen<String> insertMetodos(@RequestBody CfgMetodos cm, @Context HttpServletRequest context) {
		 HttpSession sesion = (HttpSession) context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	String msj = metodosService.insertMetodo(cm, usuario.getDuIdusuario());//ccdsdao.agregarCentroDeSalud(ccds);
            if(msj.equals("C")) {
            	return new ResponseTemplateGen<String>(msj, 200, "Ingresado corrrectamente");
            }else {
            	return new ResponseTemplateGen<String>(msj, 300, "Ya existente");
            }
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }
	@PostMapping("/api/metodos/update")
    public ResponseTemplateGen<String> updateMetodos(@RequestBody CfgMetodos cm, @Context HttpServletRequest context) {
		 HttpSession sesion = (HttpSession) context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	String msj = metodosService.updateMetodo(cm, usuario.getDuIdusuario());//ccdsdao.agregarCentroDeSalud(ccds);
            if(msj.equals("C")) {
            	return new ResponseTemplateGen<String>(msj, 200, "Actualizado corrrectamente");
            }else {
            	return new ResponseTemplateGen<String>(msj, 300, "Ya existente");
            }
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }
    /*
    @PostMapping("/api/derivadores/filtro")
    public ResponseTemplateGen<List<CfgDerivadores>> getDerivadoresFiltro(@RequestBody HashMap<String, String> filtros){
        try {
            List<CfgDerivadores> list = derivadoresService.getDerivadoresFiltro(filtros);
            return new ResponseTemplateGen<>(list, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMetodosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
	@PostMapping("/api/derivadores/insert")
    public ResponseTemplateGen<String> insertDerivadores(@RequestBody CfgDerivadores cd) {
        try {
        	String msj = derivadoresService.insertDerivadores(cd);//ccdsdao.agregarCentroDeSalud(ccds);
            if(msj.equals("C")) {
            	return new ResponseTemplateGen<String>(msj, 200, "Derivador ingresado corrrectamente");
            }else {
            	return new ResponseTemplateGen<String>(msj, 300, "Derivador ya existente");
            }
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }
	@PostMapping("/api/derivadores/update")
    public ResponseTemplateGen<String> updateDerivadores(@RequestBody CfgDerivadores cd) {
        try {
        	String msj = derivadoresService.updateDerivadores(cd);//ccdsdao.agregarCentroDeSalud(ccds);
            if(msj.equals("C")) {
            	return new ResponseTemplateGen<String>(msj, 200, "Derivador ingresado corrrectamente");
            }else {
            	return new ResponseTemplateGen<String>(msj, 300, "Derivador ya existente");
            }
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }*/
}
