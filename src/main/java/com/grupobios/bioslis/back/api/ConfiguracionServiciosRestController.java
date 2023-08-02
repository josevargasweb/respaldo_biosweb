/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.ConfiguracionServiciosService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.CfgServiciosDTO;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgServicios;
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
public class ConfiguracionServiciosRestController {

    ConfiguracionServiciosService cfgServiciosService;

    public ConfiguracionServiciosService getCfgServiciosService() {
        return cfgServiciosService;
    }

    public void setCfgServiciosService(ConfiguracionServiciosService cfgServiciosService) {
        this.cfgServiciosService = cfgServiciosService;
    }

    @PostMapping("/api/servicios/filtro")
    public ResponseTemplateGen<List<CfgServicios>> getServiciosFiltro(@RequestBody HashMap<String, String> filtros,
            @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<CfgServicios> lista = cfgServiciosService.getServiciosFiltro(filtros);
            return new ResponseTemplateGen<>(lista, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionServiciosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }

    @PostMapping("/api/servicios/insert")
    public ResponseTemplateGen<String> insertServicio(@RequestBody CfgServiciosDTO cs,
            @Context HttpServletRequest context) {
        HttpSession sesion = (HttpSession) context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            String msj = cfgServiciosService.agregarServicio(cs, usuario.getDuIdusuario());// ccdsdao.agregarCentroDeSalud(ccds);
            if (msj.equals("C")) {
                return new ResponseTemplateGen<String>(msj, 200, "Servicios ingresado corrrectamente");
            } else {
                return new ResponseTemplateGen<String>(msj, 300, "Servicios ya existente");
            }
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }

    @PostMapping("/api/servicios/update")
    public ResponseTemplateGen<String> updateServicio(@RequestBody CfgServiciosDTO cs,
            @Context HttpServletRequest context) {
        HttpSession sesion = (HttpSession) context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            String msj = cfgServiciosService.updateServicio(cs, usuario.getDuIdusuario());// ccdsdao.agregarCentroDeSalud(ccds);
            if (msj.equals("C")) {
                return new ResponseTemplateGen<String>(msj, 200, "Servicios actualizado corrrectamente");
            } else {
                return new ResponseTemplateGen<String>(msj, 300, "Servicios ya existente");
            }
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
        }
    }

}
