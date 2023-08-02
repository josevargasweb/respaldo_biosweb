/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.InstitucionesService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgConvenios;
import com.grupobios.bioslis.entity.CfgInstitucionesdesalud;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.CfgPrioridadatencion;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.SistemaConfiguraciones;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class InstitucionesRestController {
    
    private static Logger logger = LogManager.getLogger(InstitucionesRestController.class);
    
    @Autowired
    private InstitucionesService institucionesService;
    
    @GetMapping("/api/instituciones/centrosdesalud/list")
    public ResponseEntity<List<CfgCentrosdesalud>> getCentrosdesalud() {
        try {
            List<CfgCentrosdesalud> listCentrosdesalud = institucionesService.getCentrosdesalud();
            return ResponseEntity.ok(listCentrosdesalud);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/instituciones/centrosdesalud/list/codigo/{codigo}")
    public ResponseEntity<List<CfgCentrosdesalud>> getCentrosdesaludLikeCodigo(@PathVariable("codigo") String codigo) {
        try {
            codigo = codigo.toUpperCase();
            List<CfgCentrosdesalud> listCentrosdesalud = institucionesService.getCentrosdesaludLikeCodigo(codigo);
            return ResponseEntity.ok(listCentrosdesalud);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/instituciones/centrosdesalud/list/nombre/{nombre}")
    public ResponseEntity<List<CfgCentrosdesalud>> getCentrosdesaludLikeNombre(@PathVariable("nombre") String nombre) {
        try {
            nombre = nombre.toUpperCase();
            List<CfgCentrosdesalud> listCentrosdesalud = institucionesService.getCentrosdesaludLikeNombre(nombre);
            return ResponseEntity.ok(listCentrosdesalud);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PostMapping("/api/instituciones/centrosdesalud/filtro")
    public ResponseEntity<List<CfgCentrosdesalud>> getCentrosdesaludFiltro(@RequestBody HashMap<String, String> filtros) {
        try {
            List<CfgCentrosdesalud> listCentrosdesalud = institucionesService.getCentrosdesaludFiltro(filtros);
            return ResponseEntity.ok(listCentrosdesalud);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/instituciones/centrosdesalud/list/{id}")
    public ResponseEntity<CfgCentrosdesalud> getCentrosdesaludById(@PathVariable("id") short id) {
        try {
            CfgCentrosdesalud centro = institucionesService.getCentrosdesaludById(id);
            return ResponseEntity.ok(centro);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/instituciones/laboratorios/list")
    public ResponseEntity<List<CfgLaboratorios>> getLaboratorios() {
        try {
            List<CfgLaboratorios> listLaboratorios = institucionesService.getLaboratorios(); 
            return ResponseEntity.ok(listLaboratorios);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/instituciones/laboratorios/list/{idCentro}")
    public ResponseEntity<List<CfgLaboratorios>> getLaboratoriosByCentro(@PathVariable("idCentro") int idCentro) {
        try {
            List<CfgLaboratorios> listLaboratorios = institucionesService.getLaboratoriosByCentro(idCentro); 
            return ResponseEntity.ok(listLaboratorios);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/instituciones/secciones/list/{idLab}")
    public ResponseEntity<List<CfgSecciones>> getSecciones(@PathVariable("idLab") int idLab) {
        try {
            List<CfgSecciones> listSecciones = institucionesService.getSecciones(idLab);
            return ResponseEntity.ok(listSecciones);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/instituciones/convenios/list")
    public ResponseEntity<List<CfgConvenios>> getConvenios() {
        try {
            List<CfgConvenios> listConvenios = institucionesService.getConvenios();
            return ResponseEntity.ok(listConvenios);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PostMapping(path ="/api/instituciones/centros/save",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CfgCentrosdesalud> agregarCentrodesalud(@RequestBody CfgCentrosdesalud ccds){
      
        logger.debug("agregar centro de salud");
        //institucionesService.agregarCentrodesalud(ccds);
        return new ResponseEntity<>(ccds, HttpStatus.CREATED); 
    }
    
    @GetMapping("/api/instituciones/sistemaConfiguraciones/{idCentro}")
    public ResponseEntity<SistemaConfiguraciones> getSistemaConfiguracionesByCentro(@PathVariable("idCentro") byte idCentro) {
        try {
            SistemaConfiguraciones sistemaConfiguraciones = institucionesService.getSistemaConfiguracionesByCentro(idCentro);
            return ResponseEntity.ok(sistemaConfiguraciones);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }

    public InstitucionesService getInstitucionesService() {
        return institucionesService;
    }

    public void setInstitucionesService(InstitucionesService institucionesService) {
        this.institucionesService = institucionesService;
    }
    
    @GetMapping("/api/instituciones/institucionesdesalud")
    public ResponseEntity<List<CfgInstitucionesdesalud>> getInstitucionesdesalud() {
        try {
            List<CfgInstitucionesdesalud> list = institucionesService.getInstitucionesSalud();
            return ResponseEntity.ok(list);
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(InstitucionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/api/instituciones/existeCentrodesalud/{codigo}")
    public ResponseTemplateGen<CfgCentrosdesalud> compruebaExisteCentrodesalud(@PathVariable("codigo") String codigo, @Context HttpServletRequest context){
    	 HttpSession sesion = (HttpSession) context.getSession();
         DatosUsuarios usuario = null;
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
         if (usuario == null)
           return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<CfgCentrosdesalud> list = institucionesService.existeCentrodesalud(codigo);
            if (!list.isEmpty()){
                return new ResponseTemplateGen<>(list.get(0), 200, "Centro de salud ya existe");
            }
            return new ResponseTemplateGen<>(null, 404, "Centro de salud disponible");
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
}
