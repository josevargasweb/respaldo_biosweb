/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.LocalizacionesService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.CamasSalasDTO;
import com.grupobios.bioslis.dto.SalasServiciosDTO;
import com.grupobios.bioslis.entity.CfgCamassalas;
import com.grupobios.bioslis.entity.CfgSalasservicios;
import com.grupobios.bioslis.entity.CfgServicios;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class LocalizacionesRestController {
    
    LocalizacionesService localizacionesService;

    public LocalizacionesService getLocalizacionesService() {
        return localizacionesService;
    }

    public void setLocalizacionesService(LocalizacionesService localizacionesService) {
        this.localizacionesService = localizacionesService;
    }
    
    @PostMapping("/api/localizaciones/servicios")
    public ResponseTemplateGen<List<CfgServicios>> getListServicios(@RequestBody HashMap<String, String> filtros){
        try {
            List<CfgServicios> listServicios = localizacionesService.getServicios(filtros);
            return new ResponseTemplateGen<>(listServicios, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LocalizacionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/localizaciones/salas/servicio/{idServicio}")
    public ResponseTemplateGen<List<SalasServiciosDTO>> getListSalasServicios(@PathVariable("idServicio") int idServicio){
        try {
            List<SalasServiciosDTO> listSalas = localizacionesService.getSalasServicios(idServicio);
            return new ResponseTemplateGen<>(listSalas, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LocalizacionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @GetMapping("/api/localizaciones/camas/sala/{idSala}")
    public ResponseTemplateGen<List<CamasSalasDTO>> getListCamasSala(@PathVariable("idSala") int idSala){
        try {
            List<CamasSalasDTO> listCamas = localizacionesService.getCamasSalas(idSala);
            return new ResponseTemplateGen<>(listCamas, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LocalizacionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }
    
    @PostMapping("/api/localizaciones/sala/save")
    public ResponseTemplateGen<CfgSalasservicios> guardarSala(@RequestBody SalasServiciosDTO ss, @Context HttpServletRequest context){
        HttpSession sesion = (HttpSession) context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesión");
        try {
            String msje = "Sala guardada correctamente";
            if (!ss.getCSS_IDSERVICIO().equals(0)){
                msje = "Sala actualizada correctamente";
            }
            CfgSalasservicios css = localizacionesService.guardarSalaServicio(ss, usuario.getDuIdusuario());
            return new ResponseTemplateGen<>(css, 200, msje);
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LocalizacionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
        
    }
    
    @PostMapping("/api/localizaciones/cama/save")
    public ResponseTemplateGen<CfgCamassalas> guardarCama(@RequestBody CamasSalasDTO cs, @Context HttpServletRequest context){
        HttpSession sesion = (HttpSession) context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesión");
        try {
            String msje = "Cama guardada correctamente";
            if (!cs.getCCS_IDCAMASALA().equals(0)){
                msje = "Cama actualizada correctamente";
            }
            CfgCamassalas ccs = localizacionesService.guardarCamasSalas(cs, usuario.getDuIdusuario());
            return new ResponseTemplateGen<>(ccs, 200, msje);
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LocalizacionesRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
        
    }
}
