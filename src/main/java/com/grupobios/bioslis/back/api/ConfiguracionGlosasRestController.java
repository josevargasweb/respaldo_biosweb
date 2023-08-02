/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.ConfiguracionGlosasService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgGlosas;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class ConfiguracionGlosasRestController {
    
    @Autowired
    ConfiguracionGlosasService configuracionGlosasService;

    public ConfiguracionGlosasService getConfiguracionGlosasService() {
        return configuracionGlosasService;
    }

    public void setConfiguracionGlosasService(ConfiguracionGlosasService configuracionGlosasService) {
        this.configuracionGlosasService = configuracionGlosasService;
    }
    
    @PostMapping("/api/glosas/filtro")
    public ResponseTemplateGen<List<CfgGlosas>> getGlosasFiltro(@RequestBody HashMap<String, String> filtros, @Context HttpServletRequest context){
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        
        
        try {
            List<CfgGlosas> lista = configuracionGlosasService.getGlosasFiltro(filtros);
            return new ResponseTemplateGen<>(lista, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionGlosasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PostMapping("/api/glosas/insert")
    public ResponseTemplateGen<CfgGlosas> insertGlosa(@RequestBody CfgGlosas cfgGlosas , @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            String msj = configuracionGlosasService.agregarGlosa(cfgGlosas, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	return new ResponseTemplateGen<>(cfgGlosas, 200, "Glosa ingresada corrrectamente");
			}else {
				return new ResponseTemplateGen<>(cfgGlosas, 300, "Glosa ya existe");
			}
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionGlosasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PutMapping("/api/glosas/update")
    public ResponseTemplateGen<CfgGlosas> updateGlosa(@RequestBody CfgGlosas cfgGlosas , @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	String msj = configuracionGlosasService.actualizarGlosa(cfgGlosas, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	return new ResponseTemplateGen<>(cfgGlosas, 200, "Glosa actualizada corrrectamente");
			}else {
				return new ResponseTemplateGen<>(cfgGlosas, 300, "Glosa ya existe");
			}
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionGlosasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/glosa/{idGlosa}")
    public ResponseTemplateGen<CfgGlosas> getGlosaById(@PathVariable("idGlosa") int idGlosa , @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            CfgGlosas glosa = configuracionGlosasService.getGlosaById(idGlosa);
            return new ResponseTemplateGen<>(glosa, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionGlosasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    @GetMapping("/api/glosaTest/{idTest}/{idGlosa}")
    public ResponseTemplateGen<String> getGlosaTest(@PathVariable("idGlosa") int idGlosa ,@PathVariable("idTest") int idTest , @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            String glosa = configuracionGlosasService.getGlosaCritico(idTest,idGlosa);
            return new ResponseTemplateGen<>(glosa, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionGlosasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
}
