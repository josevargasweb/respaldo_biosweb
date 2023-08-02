/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.UnidadesMedidaService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.CfgUnidadesdemedida;
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
public class UnidadesMedidaRestController {
    
    UnidadesMedidaService unidadesMedidaService;
    

    public UnidadesMedidaService getUnidadesMedidaService() {
        return unidadesMedidaService;
    }

    public void setUnidadesMedidaService(UnidadesMedidaService unidadesMedidaService) {
        this.unidadesMedidaService = unidadesMedidaService;
    }
    
    @PostMapping("/api/unidadesMedida/filtro")
    public ResponseTemplateGen<List<CfgUnidadesdemedida>> getUnidadesMedidaFiltro(@RequestBody HashMap<String, String> filtros,  @Context HttpServletRequest context){
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<CfgUnidadesdemedida> list = unidadesMedidaService.getUnidadesMedidaFiltro(filtros);
            return new ResponseTemplateGen<>(list, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(UnidadesMedidaRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    @PostMapping("/api/unidadesMedida/save")
    public ResponseTemplateGen<String> guardarMedida(@RequestBody CfgUnidadesdemedida cfgum,  @Context HttpServletRequest context){
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	String mensaje =unidadesMedidaService.insertUnidadesM(cfgum, usuario.getDuIdusuario());
        	if (mensaje.equals("Existe")) {
        		return new ResponseTemplateGen<String>("Unidad de medida ya existente", 300, mensaje);
			}else {
				return new ResponseTemplateGen<String>("Unidad de medida creada correctamente", 200, mensaje);
			}
            
        } catch (BiosLISDAOException ex) {
        	Logger.getLogger(ex.getMessage());
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }
    @PostMapping("/api/unidadesMedida/update")
    public ResponseTemplateGen<String> updaterMedida(@RequestBody CfgUnidadesdemedida cfgum,  @Context HttpServletRequest context){
        try {
        	 HttpSession sesion = context.getSession();
             DatosUsuarios usuario = null;
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        	String mensaje =unidadesMedidaService.updateUnidadesM(cfgum, usuario.getDuIdusuario());
        	if (mensaje.equals("Existe")) {
        		return new ResponseTemplateGen<String>("Unidad de medida ya existente", 300, mensaje);
			}else {
				return new ResponseTemplateGen<String>("Unidad de medida actualizada correctamente", 200, mensaje);
			}
            
        } catch (BiosLISDAOException ex) {
        	Logger.getLogger(ex.getMessage());
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }
    
}
