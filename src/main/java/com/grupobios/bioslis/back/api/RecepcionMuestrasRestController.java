/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.dto.DatosOrdenPacienteExamenesDTO;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.grupobios.bioslis.bs.RecepcionMuestrasService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author marco.c
 */
@RestController
public class RecepcionMuestrasRestController {
    
    private static Logger logger = LogManager.getLogger(TomaMuestrasRestController.class);
    @Autowired
    RecepcionMuestrasService recepcionMuestrasService;
    
    @GetMapping("/api/recepcionMuestras/muestras/{dia}/{mes}/{year}")
    public ResponseTemplateGen<List<MuestrasDTO>> rellenarTablaMuestrasRM(@PathVariable("dia") String dia, @PathVariable("mes") String mes,
    		@PathVariable("year") String year,  @Context HttpServletRequest context) throws ParseException{
    	
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
        	 return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            String fecha = dia + "/" + mes + "/" + year;
            if (Integer.parseInt(dia) == 0 && Integer.parseInt(mes) == 0 && Integer.parseInt(year) == 0){
                fecha = "HOY";
            }
            logger.debug("Fecha: "+fecha);
            
            List<MuestrasDTO> listaMuestrasRM = recepcionMuestrasService.getListaMuestrasRM(fecha);
            
            //return ResponseEntity.ok(listaMuestrasRM);
            return new ResponseTemplateGen<List<MuestrasDTO>>(listaMuestrasRM, 200, "ok");
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RecepcionMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
           // return ResponseEntity.status(500).body(null);
            return new ResponseTemplateGen<List<MuestrasDTO>>(null, 300, "error");
        }
    }

    @GetMapping("/api/recepcionMuestras/datosOrden/{idMuestra}")
    public ResponseEntity<DatosOrdenPacienteExamenesDTO> getDatosOrdenRM(@PathVariable("idMuestra") Long idMuestra,  @Context HttpServletRequest context){
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;        
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return ResponseEntity.status(401).body(null);
    	try {
            DatosOrdenPacienteExamenesDTO dopex = recepcionMuestrasService.getDatosOrden(idMuestra);
            return ResponseEntity.ok(dopex);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RecepcionMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PatchMapping("/api/recepcionMuestras/recepcionaMuestra/{idMuestra}/{idUsuario}")
    public ResponseTemplateGen<Long> recepcionarMuestra(@PathVariable("idMuestra") Long idMuestra, @PathVariable("idUsuario") Long idUsuario
    		,  @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    	try {
            Long dfm = recepcionMuestrasService.recepcionarMuestra(idMuestra, idUsuario);
            return new ResponseTemplateGen<>(dfm, 200, "Muestra recepcionada");
        } catch (BiosLISDAOException e) {
            return new ResponseTemplateGen<>(null, 500, "Error: " + e.getMessage());
        }
    }
    
    @PatchMapping("/api/recepcionMuestras/recepcionaMuestra/codigoBarras/{codigoBarras}/{idUsuario}")
    public ResponseTemplateGen<Long> recepcionarMuestraCodigoBarra(@PathVariable("codigoBarras") String codigoBarras, @PathVariable("idUsuario") Long idUsuario
    		,  @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    	try {
            Long dfm = recepcionMuestrasService.recepcionarMuestraCodigoBarras(codigoBarras, idUsuario);
            if (dfm==null){
                return new ResponseTemplateGen<>(null, 404, "Código de barras no encontrado");
            } else if (dfm==-1) {
                return new ResponseTemplateGen<>(null, 401, "Muestra no disponible para recepción");
            } else if (dfm==-2) {
                return new ResponseTemplateGen<>(null, 409, "Muestra ya recepcionada");
            } else {    
                return new ResponseTemplateGen<>(dfm, 200, "Muestra recepcionada");
            }
        } catch (BiosLISDAOException e) {
            return new ResponseTemplateGen<>(null, 500, "Error: " + e);
        }
    }

    @GetMapping("/api/recepcionMuestras/recepcionistas/list")
    public ResponseEntity<List<DatosUsuarios>> getListRecepcionistas( @Context HttpServletRequest context){
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return ResponseEntity.status(401).body(null); 
        try {
            List<DatosUsuarios> recepcionistas = recepcionMuestrasService.listaRecepcionistas();
            return ResponseEntity.ok(recepcionistas);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RecepcionMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/recepcionMuestras/muestrasPendientes")
    public ResponseEntity<List<MuestrasDTO>> getMuestrasPendientes(@Context HttpServletRequest context){
    	// HttpSession sesion = context.getSession();
        //  DatosUsuarios usuario = null;
        //  usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        //  if (usuario == null)
         //   return ResponseEntity.status(401).body(null);
    	try {
            List<MuestrasDTO> listMuestrasPendientes = recepcionMuestrasService.getListaMuestrasPendientes();
            return ResponseEntity.ok(listMuestrasPendientes);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RecepcionMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/recepcionMuestras/muestrasRecepcionadas")
    public ResponseEntity<List<MuestrasDTO>> getMuestrasRecepcionadas(@Context HttpServletRequest context){
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return ResponseEntity.status(401).body(null);
    	try {
            List<MuestrasDTO> listMuestrasRecepcionadas = recepcionMuestrasService.getListaMuestrasRecepcionadas();
            return ResponseEntity.ok(listMuestrasRecepcionadas);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RecepcionMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/recepcionMuestras/muestrasConDerivador")
    public ResponseEntity<List<MuestrasDTO>> getMuestrasConDerivador(@Context HttpServletRequest context){
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return ResponseEntity.status(401).body(null);
    	try {
            List<MuestrasDTO> listMuestrasConDerivador = recepcionMuestrasService.getListaMuestrasConDerivador();
            return ResponseEntity.ok(listMuestrasConDerivador);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RecepcionMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PatchMapping("/api/recepcionMuestras/observacion/save/{idMuestra}/{observacion}")
    public ResponseTemplateGen<String> guardarObservacion(@PathVariable("idMuestra") Long idMuestra, @PathVariable("observacion") String observacion, @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        Long idUsuario = usuario.getDuIdusuario();
    	try {
            recepcionMuestrasService.guardarObservacion(idMuestra, observacion, idUsuario);
            return new ResponseTemplateGen<>(observacion, 200, "Muestra recepcionada");
        } catch (BiosLISDAOException e) {
            return new ResponseTemplateGen<>(null, 500, e.getMessage());
        }
    }
    
    @PatchMapping("/api/recepcionMuestras/derivador/update/{idMuestra}/{idDerivador}")
    public ResponseTemplateGen<String> cambiarDerivador(@PathVariable("idMuestra") Long idMuestra, @PathVariable("idDerivador") Short idDerivador, @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        Long idUsuario = 0L;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        idUsuario = usuario.getDuIdusuario();
    	try {
            recepcionMuestrasService.cambiarDerivador(idMuestra, idDerivador, idUsuario);
            return new ResponseTemplateGen<>("Ok", 200, "Derivador actualizado");
        } catch (BiosLISDAOException e) {
            return new ResponseTemplateGen<>(null, 500, "Error: " + e.getMessage());
        }
    }
    
    @PatchMapping("/api/recepcionMuestras/receptor/update/{idMuestra}/{idUsuario}")
    public ResponseTemplateGen<String> cambiarUsuarioReceptor(@PathVariable("idMuestra") Long idMuestra, @PathVariable("idUsuario") Long idUsuario, @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    	try {
    		Long usuarioId = usuario.getDuIdusuario();    		
            recepcionMuestrasService.cambiarReceptor(idMuestra, idUsuario, usuarioId);
            return new ResponseTemplateGen<>("Ok", 200, "Usuario receptor modificado");
        } catch (BiosLISDAOException e) {
            return new ResponseTemplateGen<>(null, 500, "Error: " + e.getMessage());
        }
    }
    
    @PatchMapping("/api/recepcionMuestras/volverEstadoPendiente/{idMuestra}/{idUsuario}")
    public ResponseTemplateGen<Long> volverEstadoPendiente(@PathVariable("idMuestra") Long idMuestra, @PathVariable("idUsuario") Long idUsuario, @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    	try {
            recepcionMuestrasService.cambiarEstadoPendiente(idMuestra, idUsuario);
            return new ResponseTemplateGen<>(idMuestra, 200, "Muestra configurada en estado pendiente");
        } catch (BiosLISDAOException e) {
            return new ResponseTemplateGen<>(null, 500, "Error: " + e.getMessage());
        }
    }
    
    public RecepcionMuestrasService getRecepcionMuestrasService() {
        return recepcionMuestrasService;
    }

    public void setRecepcionMuestrasService(RecepcionMuestrasService recepcionMuestrasService) {
        this.recepcionMuestrasService = recepcionMuestrasService;
    }
    
}
