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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.ProcesosService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.ProcesosDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvOperacionesmath;
import com.grupobios.bioslis.entity.LdvTipocomunicacion;
import com.grupobios.bioslis.entity.LdvTiposcondicion;
import com.grupobios.bioslis.entity.SigmaProcesos;
import com.grupobios.bioslis.entity.SigmaProcesosalarmas;
import com.grupobios.bioslis.entity.SigmaTiposmuestras;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class ProcesosRestController {

  @Autowired
  ProcesosService procesosService;

  public ProcesosService getProcesosService() {
    return procesosService;
  }

  public void setProcesosService(ProcesosService procesosService) {
    this.procesosService = procesosService;
  }

  @PostMapping("/api/procesos/filtro")
  public ResponseTemplateGen<List<SigmaProcesos>> getProcesosFiltro(@RequestBody HashMap<String, String> filtros,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<SigmaProcesos> lista = procesosService.getProcesosFiltro(filtros);
      return new ResponseTemplateGen<>(lista, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/proceso/{codigoProceso}")
  public ResponseTemplateGen<ProcesosDTO> getProcesoById(@PathVariable("codigoProceso") String codigoProceso,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      ProcesosDTO procesosDTO = procesosService.getProcesoById(codigoProceso);
      return new ResponseTemplateGen<>(procesosDTO, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/proceso/save")
  public ResponseTemplateGen<ProcesosDTO> guardarProceso(@RequestBody ProcesosDTO procesoDTO,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      String msj = procesosService.insertProceso(procesoDTO, usuario.getDuIdusuario());
      if (msj.equals("C")) {
        return new ResponseTemplateGen<>(procesoDTO, 200, "Se registró el proceso satisfactoriamente");
      } else {
        return new ResponseTemplateGen<>(procesoDTO, 300, "Registro repetido");
      }

    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PutMapping("/api/proceso/update")
  public ResponseTemplateGen<ProcesosDTO> actualizarProceso(@RequestBody ProcesosDTO procesoDTO,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      procesosService.updateProceso(procesoDTO, usuario.getDuIdusuario());
      return new ResponseTemplateGen<>(procesoDTO, 200, "Se actualizó el proceso satisfactoriamente");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/tipoComunicacion/list")
  public ResponseTemplateGen<List<LdvTipocomunicacion>> getListTipoComunicaciones(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<LdvTipocomunicacion> listTipoComunicacion = procesosService.getListTiposComunicaciones();
      return new ResponseTemplateGen<>(listTipoComunicacion, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/sigmatiposmuestras/list")
  public ResponseTemplateGen<List<SigmaTiposmuestras>> getListTiposMuestrasSigma(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<SigmaTiposmuestras> listStm = procesosService.getListTiposMuestras();
      return new ResponseTemplateGen<>(listStm, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/tiposcondicion/list")
  public ResponseTemplateGen<List<LdvTiposcondicion>> getListTiposCondicion(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<LdvTiposcondicion> list = procesosService.getListTiposCondicion();
      return new ResponseTemplateGen<>(list, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/operacionesmath/list")
  public ResponseTemplateGen<List<LdvOperacionesmath>> getListOperacionesMath(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<LdvOperacionesmath> list = procesosService.getListOperacionesMath();
      return new ResponseTemplateGen<>(list, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/proceso/busquedacodigo/{codigoProceso}")
  public ResponseTemplateGen<List<SigmaProcesos>> getProcesoByCodigo(
      @PathVariable("codigoProceso") String codigoProceso, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<SigmaProcesos> procesos = procesosService.getProcesoByCodigo(codigoProceso);
      if (!procesos.isEmpty()) {
        return new ResponseTemplateGen<>(procesos, 200, "Código de proceso ya existe");
      }
      return new ResponseTemplateGen<>(procesos, 404, "");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/alarmas/proceso/{codigoProceso}")
  public ResponseTemplateGen<List<SigmaProcesosalarmas>> getAlarmasByProceso(
      @PathVariable("codigoProceso") String codigoProceso, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<SigmaProcesosalarmas> procesos = procesosService.getAlarmasByCodigoProceso(codigoProceso);
      return new ResponseTemplateGen<>(procesos, 200, "");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ProcesosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }
}
