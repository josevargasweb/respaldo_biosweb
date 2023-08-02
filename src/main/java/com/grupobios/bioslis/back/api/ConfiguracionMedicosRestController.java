/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import com.grupobios.bioslis.bs.ConfiguracionGlosasService;
import com.grupobios.bioslis.bs.ConfiguracionMedicosService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgGlosas;
import com.grupobios.bioslis.entity.CfgMedicos;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.text.ParseException;
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
public class ConfiguracionMedicosRestController {

	ConfiguracionMedicosService configuracionMedicosService;

		public ConfiguracionMedicosService getConfiguracionMedicosService() {
				return configuracionMedicosService;
			}

			public void setConfiguracionMedicosService(ConfiguracionMedicosService configuracionMedicosService) {
					this.configuracionMedicosService = configuracionMedicosService;
				}
/*
    @PostMapping("/api/medico/filtro")
    public ResponseTemplateGen<List<CfgGlosas>> getMedicoFiltro(@RequestBody HashMap<String, String> filtros,
            @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            List<CfgGlosas> lista = configuracionGlosasService.getGlosasFiltro(filtros);
            return new ResponseTemplateGen<>(lista, 200, "Ok");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }*/

    @PostMapping("/api/medico/insert")
    public ResponseTemplateGen<CfgGlosas> insertMedico(@RequestBody CfgMedicos med,
            @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            configuracionMedicosService.insertMedico(med, usuario.getDuIdusuario());
            return new ResponseTemplateGen<>(null, 200, "Medico ingresado corrrectamente");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionGlosasRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }

    @PutMapping("/api/medico/update")
    public ResponseTemplateGen<CfgGlosas> updateMedico(@RequestBody CfgMedicos med,
            @Context HttpServletRequest context) throws ParseException {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
        	configuracionMedicosService.updateMedico(med, usuario.getDuIdusuario());
        	return new ResponseTemplateGen<>(null, 200, "Medico actualizado corrrectamente");
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseTemplateGen<>(null, 500, ex.getMessage());
        }
    }

}
