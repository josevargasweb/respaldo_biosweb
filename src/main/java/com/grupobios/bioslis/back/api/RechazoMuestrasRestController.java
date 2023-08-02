/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.RechazoMuestrasService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCausasrechazosmuestrasDAO;
import com.grupobios.bioslis.dto.FichaFiltroDTO;
import com.grupobios.bioslis.dto.MuestraRechazadaDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.dto.OrdenesTMDTO;
import com.grupobios.bioslis.entity.CfgCausasrechazosmuestras;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author marco.caracciolo
 */
@RestController
public class RechazoMuestrasRestController {
    
    private static Logger logger = LogManager.getLogger(OrdenRestController.class);
    
    @Autowired
    private RechazoMuestrasService rechazoMuestrasService;

    @PostMapping("/api/rechazoMuestras/ordenes")
    public ResponseEntity<List<OrdenesTMDTO>> getOrdenesRechazoMuestras(
                    @RequestBody FichaFiltroDTO fichaFiltroDTO, @Context HttpServletRequest context) throws BiosLISDAOException {
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return ResponseEntity.status(401).body(null);
        try {
            logger.debug("Hola   {}", fichaFiltroDTO);
            List<OrdenesTMDTO> lstOrdenes = rechazoMuestrasService.getOrdenesRechazoMuestras(fichaFiltroDTO);

            return ResponseEntity.ok(lstOrdenes);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RechazoMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/rechazoMuestras/muestras/{nOrden}")
    public ResponseEntity<List<MuestrasDTO>> rellenarTablaMuestrasRechazo(@PathVariable("nOrden") long nOrden, @Context HttpServletRequest context) throws ParseException{
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return ResponseEntity.status(401).body(null);
        try {
            List<MuestrasDTO> listaMuestras = rechazoMuestrasService.getListaMuestrasRechazo(nOrden); 
            return ResponseEntity.ok(listaMuestras);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RechazoMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/api/rechazoMuestras/muestra/{idMuestra}")
    public ResponseEntity<MuestraRechazadaDTO> getMuestraRechazo(@PathVariable("idMuestra") long idMuestra, @Context HttpServletRequest context) throws ParseException{
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return ResponseEntity.status(401).body(null);
    	try {
            MuestraRechazadaDTO muestra = rechazoMuestrasService.getMuestraRechazo(idMuestra); 
            return ResponseEntity.ok(muestra);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(RechazoMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/api/rechazoMuestras/rechazoTotal/{idMuestra}")
    public ResponseTemplateGen<DatosFichasmuestras> rechazoTotalMuestra(
            @PathVariable("idMuestra") long idMuestra, 
            @RequestParam("idUsuario") long idUsuario, 
            @RequestParam("causaRechazo") byte causaRechazo, 
            @RequestParam("observacion") String observacion , @Context HttpServletRequest context) throws ParseException{
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            DatosFichasmuestras muestra = rechazoMuestrasService.rechazoTotalMuestra(idMuestra, idUsuario, causaRechazo, observacion);
            return new ResponseTemplateGen<>(muestra, 200, "Muestra rechazada");
        } catch (BiosLISDAOException e) {
            return new ResponseTemplateGen<>(null, 500, "Error: " + e.getMessage()); 
        }
    }

    @PostMapping("/api/rechazoMuestras/rechazoParcial/{idMuestra}")
    public ResponseTemplateGen<DatosFichasmuestras> rechazoParcialMuestra(
            @PathVariable("idMuestra") long idMuestra, 
            @RequestParam("idUsuario") long idUsuario, 
            @RequestParam("causaRechazo") byte causaRechazo, 
            @RequestParam("observacion") String observacion,
            @RequestParam("examenesRechazados") List<String> examenesRechazados,
            @RequestParam("examenesNoRechazados") List<String> examenesNoRechazados, @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            DatosFichasmuestras muestra = rechazoMuestrasService.rechazoParcialMuestra(idMuestra, idUsuario, causaRechazo, observacion, examenesRechazados, examenesNoRechazados);
            return new ResponseTemplateGen<>(muestra, 200, "Muestra rechazada parcialmente"); 
        } catch (BiosLISDAOException e) {
            return new ResponseTemplateGen<>(null, 500, "Error: " + e.getMessage()); 
        }
    }
    @PostMapping("/api/CausaRechazoMuestras/insert")
    public ResponseTemplateGen<String> insertRechazoMuestras(@RequestBody CfgCausasrechazosmuestras cfgCausasrechazosmuestras, @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	
            CfgCausasrechazosmuestrasDAO ccrmDao = new CfgCausasrechazosmuestrasDAO();
            
            String msj = ccrmDao.agregarCausaRechazoMuestras(cfgCausasrechazosmuestras, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	return new ResponseTemplateGen<String>(null, 200, "Causa rechazo ingresado");
			}else {
				return new ResponseTemplateGen<String>(null, 300, "Causa rechazo existe");
			}
            
        } catch (BiosLISDAOException ex) {
           // Logger.getLogger(LaboratoriosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    @PostMapping("/api/CausaRechazoMuestras/update")
    public ResponseTemplateGen<String> updateRechazoMuestras(@RequestBody CfgCausasrechazosmuestras cfgCausasrechazosmuestras, @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	
            CfgCausasrechazosmuestrasDAO ccrmDao = new CfgCausasrechazosmuestrasDAO();
            
            String msj = ccrmDao.actualizarCausaRechazoMuestras(cfgCausasrechazosmuestras, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	return new ResponseTemplateGen<String>(null, 200, "Causa de rechazo actualizada correctamente");
			}else {
				return new ResponseTemplateGen<String>(null, 300, "Causa rechazo existe");
			}
            
        } catch (BiosLISDAOException ex) {
           // Logger.getLogger(LaboratoriosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    public RechazoMuestrasService getRechazoMuestrasService() {
        return rechazoMuestrasService;
    }

    public void setRechazoMuestrasService(RechazoMuestrasService rechazoMuestrasService) {
        this.rechazoMuestrasService = rechazoMuestrasService;
    }
    
}
