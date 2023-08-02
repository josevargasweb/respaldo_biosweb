/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.SeccionesService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
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
public class SeccionesRestController {
            
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SeccionesRestController.class);
    SeccionesService seccionesService;

    public SeccionesService getSeccionesService() {
        return seccionesService;
    }

    public void setSeccionesService(SeccionesService seccionesService) {
        this.seccionesService = seccionesService;
    }
    
    @PostMapping("/api/secciones/filtro")
    public ResponseTemplateGen<List<CfgSecciones>> getSeccionesFiltro(@RequestBody HashMap<String, String> filtros,  @Context HttpServletRequest context){
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<CfgSecciones> list = seccionesService.getSeccionesFiltro(filtros);
            return new ResponseTemplateGen<>(list, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/seccion/{idSeccion}")
    public ResponseTemplateGen<CfgSecciones> getSeccionById(@PathVariable("idSeccion") int idSeccion,  @Context HttpServletRequest context){
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            CfgSecciones seccion = seccionesService.getSeccionById(idSeccion);
            return new ResponseTemplateGen<>(seccion, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PostMapping("/api/seccion/save")
    public ResponseTemplateGen<CfgSecciones> guardarSeccion(@RequestBody CfgSecciones csec,  @Context HttpServletRequest context){
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	String mensaje =seccionesService.agregarSeccion(csec, usuario.getDuIdusuario());
            return new ResponseTemplateGen<>(csec, 200, mensaje);
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PutMapping("/api/seccion/update")
    public ResponseTemplateGen<CfgSecciones> actualizarSeccion(@RequestBody CfgSecciones csec,  @Context HttpServletRequest context){
    	System.out.println(csec);
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	String mensaje =seccionesService.actualizarSeccion(csec, usuario.getDuIdusuario());
            return new ResponseTemplateGen<>(csec, 200, mensaje);
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/countExamenes/seccion/{idSeccion}")
    public ResponseTemplateGen<Integer> getCountExamenesBySeccion(@PathVariable("idSeccion") int idSeccion,  @Context HttpServletRequest context){
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            int countExamenes = seccionesService.getCountExamenesBySeccion(idSeccion);
            return new ResponseTemplateGen<>(countExamenes, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
}
