/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.DerivadoresService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgDerivadores;
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
public class DerivadoresRestController {
    
    DerivadoresService derivadoresService;

    public DerivadoresService getDerivadoresService() {
        return derivadoresService;
    }

    public void setDerivadoresService(DerivadoresService derivadoresService) {
        this.derivadoresService = derivadoresService;
    }
    
    @PostMapping("/api/derivadores/filtro")
    public ResponseTemplateGen<List<CfgDerivadores>> getDerivadoresFiltro(@RequestBody HashMap<String, String> filtros, @Context HttpServletRequest context){
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<CfgDerivadores> list = derivadoresService.getDerivadoresFiltro(filtros);
            return new ResponseTemplateGen<>(list, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(DerivadoresRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
	@PostMapping("/api/derivadores/insert")
    public ResponseTemplateGen<String> insertDerivadores(@RequestBody CfgDerivadores cd, @Context HttpServletRequest context) {
		HttpSession sesion = (HttpSession) context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	String msj = derivadoresService.insertDerivadores(cd, usuario.getDuIdusuario());//ccdsdao.agregarCentroDeSalud(ccds);
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
    public ResponseTemplateGen<String> updateDerivadores(@RequestBody CfgDerivadores cd, @Context HttpServletRequest context) {
		HttpSession sesion = (HttpSession) context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	String msj = derivadoresService.updateDerivadores(cd, usuario.getDuIdusuario());//ccdsdao.agregarCentroDeSalud(ccds);
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
}
