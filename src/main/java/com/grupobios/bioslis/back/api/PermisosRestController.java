/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.CargosModulosService;
import com.grupobios.bioslis.bs.UsuariosModulosService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.CargosModulosDTO;
import com.grupobios.bioslis.dto.ItemsMenuDTO;
import com.grupobios.bioslis.entity.CfgCargosmodulosbl;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvCargosusuarios;
import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class PermisosRestController {
    
    private static Logger logger = LogManager.getLogger(PermisosRestController.class);
    
    @Autowired
    CargosModulosService cargosModulosService;
    
    @Autowired
    UsuariosModulosService usuariosModulosService;

    public CargosModulosService getCargosModulosService() {
        return cargosModulosService;
    }

    public void setCargosModulosService(CargosModulosService cargosModulosService) {
        this.cargosModulosService = cargosModulosService;
    }

    public UsuariosModulosService getUsuariosModulosService() {
        return usuariosModulosService;
    }

    public void setUsuariosModulosService(UsuariosModulosService usuariosModulosService) {
        this.usuariosModulosService = usuariosModulosService;
    }
    
    @GetMapping("/api/permisos/cargosmodulos")
    public ResponseTemplateGen<List<CargosModulosDTO>> getCargosModulos( @Context HttpServletRequest context){
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<CargosModulosDTO> lista = cargosModulosService.listaCargosModulos();
            if (lista.isEmpty()){
                return new ResponseTemplateGen<>(lista, 404, "No encontrado");
            }
            return new ResponseTemplateGen<>(lista, 200, "OK");
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(PermisosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/permisos/cargos/list")
    public ResponseTemplateGen<List<LdvCargosusuarios>> getCargosUsuarios( @Context HttpServletRequest context){
    	 HttpSession sesion = context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<LdvCargosusuarios> lista = cargosModulosService.listaCargos();
            if (lista.isEmpty()){
                return new ResponseTemplateGen<>(lista, 404, "No encontrado");
            }
            return new ResponseTemplateGen<>(lista, 200, "OK");
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(PermisosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/permisos/cargo/{idCargo}")
    public ResponseTemplateGen<CargosModulosDTO> getModulosCargo(@PathVariable("idCargo") short idCargo, @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            CargosModulosDTO cargosModulosDTO = cargosModulosService.obtenerModulosCargo(idCargo);
            if (cargosModulosDTO==null){
                return new ResponseTemplateGen<>(cargosModulosDTO, 404, "No encontrado");
            }
            return new ResponseTemplateGen<>(cargosModulosDTO, 200, "OK");
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(PermisosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PostMapping("/api/permisos/list/cargos/modulos/save/{idCargo}")
    public ResponseTemplateGen<List<CfgCargosmodulosbl>> guardarCargosModulo(@PathVariable("idCargo") short idCargo,
    		@RequestBody List<CfgCargosmodulosbl> listCmbl, @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        logger.debug("Size: ", listCmbl.size());
        try {
            cargosModulosService.guardarCargosModulos(idCargo, listCmbl);
        } catch (BiosLISDAOException e) {
            return new ResponseTemplateGen<>(listCmbl, 500, "Error: " + e.getMessage());
        }
        return new ResponseTemplateGen<>(listCmbl, 200, "Ok");
    }
    
    @PostMapping("/api/cargo/save/{cargo}")
    public ResponseTemplateGen<String> agregarCargo(@PathVariable("cargo") String cargo,  @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            cargosModulosService.agregarCargo(cargo);
            return new ResponseTemplateGen<>("Agregado correctamente", 200, "Ok");
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(PermisosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PutMapping("/api/cargo/update/{idCargo}/{cargo}")
    public ResponseTemplateGen<String> actualizarCargo(@PathVariable("idCargo") short idCargo, @PathVariable("cargo") String cargo,  @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    	try {
            cargosModulosService.actualizarCargo(idCargo, cargo);
            return new ResponseTemplateGen<>("Agregado correctamente", 200, "Ok");
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(PermisosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @DeleteMapping("/api/cargo/delete/{idCargo}")
    public ResponseEntity<Short> eliminarCargo(@PathVariable("idCargo") short idCargo){
       
    	try {
            LdvCargosusuarios cargoDelete = cargosModulosService.obtenerCargoById(idCargo);
            cargosModulosService.eliminarCargo(cargoDelete);  
            return ResponseEntity.ok(idCargo);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(PermisosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    //Menú
    @GetMapping("/api/permisos/usuario/{idUsuario}")
    public ResponseTemplateGen<List<ItemsMenuDTO>> getModulosUsuario(@PathVariable("idUsuario") long idUsuario,  @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<ItemsMenuDTO> menu = usuariosModulosService.getItemsMenuUsuario(idUsuario);
            if (menu.isEmpty()){
                return new ResponseTemplateGen<>(menu, 404, "Menú vacío");
            }
            return new ResponseTemplateGen<>(menu, 200, "OK");
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(PermisosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    //Registro de Usuarios - Permisos/Accesos
    @GetMapping("/api/accesos/usuario/{idUsuario}")
    public ResponseTemplateGen<List<ItemsMenuDTO>> getAccesosUsuario(@PathVariable("idUsuario") long idUsuario,  @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    	try {
            List<ItemsMenuDTO> menu = usuariosModulosService.getAccesosUsuario(idUsuario);
            if (menu.isEmpty()){
                return new ResponseTemplateGen<>(menu, 404, "No encontrado");
            }
            return new ResponseTemplateGen<>(menu, 200, "OK");
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(PermisosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/accesos/cargo/{idCargo}")
    public ResponseTemplateGen<CargosModulosDTO> listarModulosCargo(@PathVariable("idCargo") short idCargo,  @Context HttpServletRequest context){
    	HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    	try {
            CargosModulosDTO cargosModulosDTO = cargosModulosService.listarModulosCargo(idCargo);
            if (cargosModulosDTO==null){
                return new ResponseTemplateGen<>(cargosModulosDTO, 404, "No encontrado");
            }
            return new ResponseTemplateGen<>(cargosModulosDTO, 200, "OK");
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(PermisosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
}
