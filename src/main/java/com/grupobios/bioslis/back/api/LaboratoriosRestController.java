/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.LaboratoriosService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

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
public class LaboratoriosRestController {
    
    LaboratoriosService laboratoriosService;

    public LaboratoriosService getLaboratoriosService() {
        return laboratoriosService;
    }

    public void setLaboratoriosService(LaboratoriosService laboratoriosService) {
        this.laboratoriosService = laboratoriosService;
    }
    
    @PostMapping("/api/laboratorios/filtro")
    public ResponseTemplateGen<List<CfgLaboratorios>> getLaboratoriosFiltro(@RequestBody HashMap<String, String> filtros, @Context HttpServletRequest context){
    	HttpSession sesion = (HttpSession) context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<CfgLaboratorios> lista = laboratoriosService.getLaboratoriosFiltro(filtros);
            return new ResponseTemplateGen<>(lista, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LaboratoriosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/laboratorio/{idLab}")
    public ResponseTemplateGen<CfgLaboratorios> getLaboratorioById(@PathVariable("idLab") int idLab, @Context HttpServletRequest context){
    	HttpSession sesion = (HttpSession) context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            CfgLaboratorios lab = laboratoriosService.getLaboratorioById(idLab);
            return new ResponseTemplateGen<>(lab, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LaboratoriosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PostMapping("/api/laboratorio/insert")
    public ResponseTemplateGen<CfgLaboratorios> insertLaboratorio(@RequestBody CfgLaboratorios cfgLaboratorios, @Context HttpServletRequest context){
    	HttpSession sesion = (HttpSession) context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            String msj = laboratoriosService.insertLaboratorio(cfgLaboratorios, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	return new ResponseTemplateGen<>(cfgLaboratorios, 200, "Laboratorio ingresado");
			}else {
				return new ResponseTemplateGen<>(cfgLaboratorios, 300, "Laboratorio ya existe");
			}
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LaboratoriosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PutMapping("/api/laboratorio/update")
    public ResponseTemplateGen<CfgLaboratorios> updateLaboratorio(@RequestBody CfgLaboratorios cfgLaboratorios, @Context HttpServletRequest context){
    	HttpSession sesion = (HttpSession) context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            String msj = laboratoriosService.updateLaboratorio(cfgLaboratorios, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	return new ResponseTemplateGen<>(cfgLaboratorios, 200, "Laboratorio actualizado");
			}else {
				return new ResponseTemplateGen<>(cfgLaboratorios, 300, "Laboratorio ya existe");
			}
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LaboratoriosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/countExamenes/laboratorio/{idLab}")
    public ResponseTemplateGen<Integer> getCountExamenesByLab(@PathVariable("idLab") int idLab, @Context HttpServletRequest context){
    	HttpSession sesion = (HttpSession) context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            int countExamenes = laboratoriosService.getCountExamenesByLab(idLab);
            return new ResponseTemplateGen<>(countExamenes, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LaboratoriosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
}
