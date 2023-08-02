/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.ConfiguracionProcedenciasService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.CfgProcedenciasDTO;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.DatosUsuarios;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class ConfiguracionProcedenciasRestController {

  @Autowired
  private ConfiguracionProcedenciasService configuracionProcedenciasService;

  /*
   * @GetMapping("/api/procedencia/{idProcedencia}") public
   * ResponseTemplateGen<CfgProcedencias>
   * getProcedenciaById(@PathVariable("idProcedencia") int idProcedencia) { try {
   * CfgProcedencias proce =
   * configuracionProcedenciasService.getProcedenciaById(idProcedencia); if (proce
   * == null) { //logger.debug("No se encontr{o paciente {}", rutPaciente); return
   * new ResponseTemplateGen<>(proce, 404, "Procedencia no encontrada"); } return
   * new ResponseTemplateGen<>(proce, 200, "Ok"); } catch (BiosLISDAOException ex)
   * {
   * Logger.getLogger(ConfiguracionProcedenciasRestController.class.getName()).log
   * (Level.SEVERE, null, ex); return new ResponseTemplateGen<>(null, 500,
   * ex.getMessage()); } }
   * 
   * @PostMapping("/api/procedencia/save") public ResponseEntity<Void>
   * saveProcedencia(@RequestBody CfgProcedencias cp){ //EN CONSTRUCCIÓN return
   * new ResponseEntity<>(HttpStatus.CREATED); }
   */
  @PostMapping("/api/procedencias/filtro")
  public ResponseTemplateGen<List<CfgProcedencias>> getProcedenciasFiltro(@RequestBody HashMap<String, String> filtros,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgProcedencias> lista = configuracionProcedenciasService.getProcedenciasFiltro(filtros);
      return new ResponseTemplateGen<>(lista, 200, "OK");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ConfiguracionProcedenciasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  public ConfiguracionProcedenciasService getConfiguracionProcedenciasService() {
    return configuracionProcedenciasService;
  }

  public void setConfiguracionProcedenciasService(ConfiguracionProcedenciasService configuracionProcedenciasService) {
    this.configuracionProcedenciasService = configuracionProcedenciasService;
  }

  // agregado x jan
  @GetMapping("/api/procedencia/{idcentros}")
  public ResponseTemplateGen<List<CfgProcedenciasDTO>> getProcedenciaByCentros(@PathVariable("idcentros") int idCentro,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgProcedenciasDTO> proce = configuracionProcedenciasService.getProcedenciasxCentro(idCentro);

      if (proce == null) {
        // logger.debug("No se encontr{o paciente {}", rutPaciente);
        return new ResponseTemplateGen<>(proce, 404, "Procedencia no encontrada");
      }
      return new ResponseTemplateGen<>(proce, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ConfiguracionProcedenciasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/procedencia/insert")
  public ResponseTemplateGen<String> insertProcedencia(@RequestBody CfgProcedenciasDTO cfg,
      @Context HttpServletRequest context) {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      String msj = configuracionProcedenciasService.insertProcedencia(cfg, usuario.getDuIdusuario());// ccdsdao.agregarCentroDeSalud(ccds);
      if (msj.equals("C")) {
        return new ResponseTemplateGen<String>(msj, 200, "Centro ingresado corrrectamente");
      } else {
        return new ResponseTemplateGen<String>(msj, 300, "Centro ya existente");
      }
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/procedencia/update")
  public ResponseTemplateGen<String> updateProcedencia(@RequestBody CfgProcedenciasDTO cfg,
      @Context HttpServletRequest context) {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      String msj = configuracionProcedenciasService.updateProcedencia(cfg, usuario.getDuIdusuario());// ccdsdao.agregarCentroDeSalud(ccds);
      if (msj.equals("C")) {
        return new ResponseTemplateGen<String>(msj, 200, "Centro ingresado corrrectamente");
      } else {
        return new ResponseTemplateGen<String>(msj, 300, "Centro ya existente");
      }
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ConfiguracionMedicosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<String>(null, 500, ex.getMessage());
    }
  }

}
