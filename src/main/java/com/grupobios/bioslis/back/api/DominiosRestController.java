/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.DominiosService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.EntryPanel;
import com.grupobios.bioslis.dto.GlosaDTO;
import com.grupobios.bioslis.dto.GlosasExamenDTO;
import com.grupobios.bioslis.dto.ItemPanelExamenDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgConvenios;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgDiagnosticos;
import com.grupobios.bioslis.entity.CfgEnvases;
import com.grupobios.bioslis.entity.CfgEstadosexamenes;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgLocalizaciones;
import com.grupobios.bioslis.entity.CfgMedicos;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgPesquisas;
import com.grupobios.bioslis.entity.CfgPrioridadatencion;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.CfgServicios;
import com.grupobios.bioslis.entity.CfgTipoatencion;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvCie10;
import com.grupobios.bioslis.entity.LdvComunas;
import com.grupobios.bioslis.entity.LdvContactospacientesrelacion;
import com.grupobios.bioslis.entity.LdvEstadosciviles;
import com.grupobios.bioslis.entity.LdvFormatos;
import com.grupobios.bioslis.entity.LdvGruposexamenes;
import com.grupobios.bioslis.entity.LdvIndicacionestm;
import com.grupobios.bioslis.entity.LdvLoinc;
import com.grupobios.bioslis.entity.LdvPaises;
import com.grupobios.bioslis.entity.LdvPatologias;
import com.grupobios.bioslis.entity.LdvRegiones;
import com.grupobios.bioslis.entity.LdvSexo;
import com.grupobios.bioslis.entity.LdvTiposdocumentos;
import com.grupobios.bioslis.entity.LdvTiposreceptores;
import com.grupobios.bioslis.entity.LdvViasnotificaciones;

/**
 *
 * @author eric.nicholls
 */

@RestController
public class DominiosRestController {

  private static Logger logger = LogManager.getLogger(DominiosRestController.class);

  @Autowired
  private DominiosService dominiosService;

  @GetMapping("/api/dominio/servicios/list")
  public ResponseEntity<List<CfgServicios>> getServicios(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgServicios> lstServicios = dominiosService.getServicios();
      return ResponseEntity.ok(lstServicios);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @GetMapping("/api/dominio/muestras/list")
  public ResponseEntity<List<CfgMuestras>> getMuestras(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgMuestras> lstMuestras = dominiosService.getMuestras();
      return ResponseEntity.ok(lstMuestras);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @GetMapping("/api/dominio/envases/list")
  public ResponseEntity<List<CfgEnvases>> getEnvases(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgEnvases> lstEnvases = dominiosService.getEnvases();
      return ResponseEntity.ok(lstEnvases);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @GetMapping("/api/dominio/derivadores/list")
  public ResponseEntity<List<CfgDerivadores>> getDerivadores(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgDerivadores> lstDerivadores = dominiosService.getDerivadores();
      return ResponseEntity.ok(lstDerivadores);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @GetMapping("/api/dominio/derivadores/lstSelect")
  public ResponseEntity<List<CfgDerivadores>> getDerivadores2(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgDerivadores> lstDerivadores = dominiosService.getDerivadoresSelect();
      return ResponseEntity.ok(lstDerivadores);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @GetMapping("/api/dominio/cfgenvases/list")
  public ResponseEntity<List<CfgEnvases>> getCfgEnvases(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgEnvases> lstEnvases = dominiosService.getCfgEnvases();
      return ResponseEntity.ok(lstEnvases);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  public DominiosService getDominiosService() {
    return dominiosService;
  }

  public void setDominiosService(DominiosService dominiosService) {
    this.dominiosService = dominiosService;
  }

  @GetMapping("/api/dominio/glosa/resultados/list")
  public ResponseTemplateGen<List<GlosaDTO>> getGlosasResultados(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<GlosaDTO> lstGlosas = dominiosService.getGlosasResultados();
    return new ResponseTemplateGen<List<GlosaDTO>>(lstGlosas, 200, "");

  }

  @GetMapping("/api/dominio/secciones/list")
  public ResponseEntity<List<CfgSecciones>> getSecciones(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgSecciones> lstSecciones = dominiosService.getSecciones();
      return ResponseEntity.ok(lstSecciones);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @GetMapping("/api/dominio/secciones/list/sm")
  public ResponseEntity<List<CfgSecciones>> getSeccionesList(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgSecciones> lstSecciones = dominiosService.getSeccionesSm();
      return ResponseEntity.ok(lstSecciones);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @GetMapping("/api/dominio/tipoatencion/list")
  public ResponseEntity<List<CfgTipoatencion>> getTiposAtencion(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    /*
     * try { List<CfgTipoatencion> lstTipoAtencion =
     * dominiosService.getTiposAtencion(); return new
     * ResponseTemplateGen<List<CfgTipoatencion>>(lstTipoAtencion, 200, "OK"); }
     * catch (Exception e) { logger.error("error en DominiosRestController " +
     * e.getMessage()); return new ResponseTemplateGen<List<CfgTipoatencion>>(null,
     * 500, e.getMessage()); }
     */
    try {
      List<CfgTipoatencion> lstTipoAtencion = dominiosService.getTiposAtencion();
      return ResponseEntity.ok(lstTipoAtencion);
    } catch (Exception e) {
      logger.error("error en DominiosRestController " + e.getMessage());
    }
    return null;

  }

  @GetMapping("/api/dominio/procedencia/list")
  public ResponseEntity<List<CfgProcedencias>> getProcedencias(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgProcedencias> lstProcedencias = dominiosService.getProcedencias();
      return ResponseEntity.ok(lstProcedencias);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @GetMapping("/api/dominio/estadoexamen/list")
  public ResponseEntity<List<CfgEstadosexamenes>> getEstadosExamenes(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<CfgEstadosexamenes> lstCfgEstados = dominiosService.getEstadosExamenes();
    lstCfgEstados.forEach(e -> {
    });
    return ResponseEntity.ok(lstCfgEstados);

  }

  @GetMapping("/api/dominio/cfgexamen/list")
  public ResponseEntity<List<CfgExamenes>> getCfgExamenes(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgExamenes> lstCfgExamenes = dominiosService.getCfgExamenes();
      return ResponseEntity.ok(lstCfgExamenes);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @GetMapping("/api/dominio/cfgexamen/listincmuestras")
  public ResponseTemplateGen<List<CfgExamenesDTO>> getCfgExamenesIncMuestras(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
//      if (usuario == null)
//          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgExamenesDTO> lstCfgExamenesDTO = dominiosService.getCfgExamenesIncMuestras();
      return new ResponseTemplateGen<List<CfgExamenesDTO>>(lstCfgExamenesDTO, 200, "");
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }

  }

  @PostMapping("/api/orden/{nroOrden}/examenes/resultados/dominios")
  public ResponseTemplateGen<List<GlosasExamenDTO>> getResultadosExamenesOrden(@PathVariable("nroOrden") Long nroOrden,
      @RequestBody ParamResultadoExamenOrden prmResultadoExamenOrden, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    logger.debug("Resultados para orden {}, {} ", nroOrden, prmResultadoExamenOrden.getExamenes().size());
    List<GlosasExamenDTO> lstGlosasExamen;
    try {
      lstGlosasExamen = dominiosService.getDominiosResultadosExamenesOrden(nroOrden, prmResultadoExamenOrden);
      return new ResponseTemplateGen<List<GlosasExamenDTO>>(lstGlosasExamen, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<GlosasExamenDTO>>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/dominio/panelexamenes/list")
  public ResponseTemplateGen<List<EntryPanel>> getPanelesExamenes(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<EntryPanel> lstPanelesExamenes;
    try {
      lstPanelesExamenes = dominiosService.getPanelesExamenes();
      return new ResponseTemplateGen<List<EntryPanel>>(lstPanelesExamenes, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<EntryPanel>>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/dominio/prioridadAtencion/list")
  public ResponseEntity<List<CfgPrioridadatencion>> getPrioridadesAtencion(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgPrioridadatencion> lstPrioridades;
      lstPrioridades = dominiosService.getPrioridadesAtencion();
      return ResponseEntity.ok(lstPrioridades);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return null;
    }
  }

  @GetMapping("/api/dominio/tipodocumento/list")
  public ResponseEntity<List<LdvTiposdocumentos>> getTiposDeDocumentos(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<LdvTiposdocumentos> lstTiposDocumento;
    lstTiposDocumento = dominiosService.getTiposDeDocumentos();
    return ResponseEntity.ok(lstTiposDocumento);
  }

  @GetMapping("/api/dominio/localizacion/list")
  public ResponseEntity<List<CfgLocalizaciones>> getLocalizaciones(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgLocalizaciones> lstLocalizaciones;
      lstLocalizaciones = dominiosService.getLocalizaciones();
      return ResponseEntity.ok(lstLocalizaciones);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/api/dominio/muestras/grupo/list/{idExamen}")
  public ResponseEntity<List<CfgMuestras>> getMuestrasByGrupo(@PathVariable("idExamen") long idExamen,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<CfgMuestras> lstMuestrasGrupo = dominiosService.getMuestrasByGrupo(idExamen);
    return ResponseEntity.ok(lstMuestrasGrupo);
  }

  @GetMapping("/api/dominio/sexo/list")
  public ResponseEntity<List<LdvSexo>> getSexos(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<LdvSexo> lstSexos;
    lstSexos = dominiosService.getSexos();
    return ResponseEntity.ok(lstSexos);
  }

  @GetMapping("/api/dominio/region/list")
  public ResponseEntity<List<LdvRegiones>> getRegiones(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<LdvRegiones> lstRegiones;
    lstRegiones = dominiosService.getRegiones();
    return ResponseEntity.ok(lstRegiones);
  }

  @GetMapping("/api/dominio/comuna/list")
  public ResponseEntity<List<LdvComunas>> getComunas(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<LdvComunas> lstComunas;
    lstComunas = dominiosService.getComunas();
    return ResponseEntity.ok(lstComunas);
  }

  @GetMapping("/api/dominio/pacienterelacion/list")
  public ResponseEntity<List<LdvSexo>> getPacientesrelacion(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<LdvSexo> lstSexos;
    lstSexos = dominiosService.getSexos();
    return ResponseEntity.ok(lstSexos);
  }

  @GetMapping("/api/dominio/pais/list")
  public ResponseEntity<List<LdvPaises>> getPaises(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<LdvPaises> lstPaises;
    lstPaises = dominiosService.getPaises();
    return ResponseEntity.ok(lstPaises);
  }

  @GetMapping("/api/dominio/estadocivil/list")
  public ResponseEntity<List<LdvEstadosciviles>> getEstadosCiviles(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<LdvEstadosciviles> lstLdvEstadosciviles;
    lstLdvEstadosciviles = dominiosService.getEstadosCiviles();
    return ResponseEntity.ok(lstLdvEstadosciviles);
  }

  @GetMapping("/api/dominio/patologia/list")
  public ResponseEntity<List<LdvPatologias>> getPatologias(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<LdvPatologias> lstLdvPatologias;
    lstLdvPatologias = dominiosService.getPatologias();
    return ResponseEntity.ok(lstLdvPatologias);
  }

  @GetMapping("/api/dominio/contactospacientesrelacion/list")
  public ResponseEntity<List<LdvContactospacientesrelacion>> getLdvContactospacientesrelacion(
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<LdvContactospacientesrelacion> lstLdvPatologias;
    lstLdvPatologias = dominiosService.getLdvContactospacientesrelacion();
    return ResponseEntity.ok(lstLdvPatologias);
  }

  @GetMapping("/api/dominio/itempanelexamenes/list")
  public ResponseTemplateGen<List<ItemPanelExamenDTO>> getItemPanelesExamenes(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<ItemPanelExamenDTO> lstPanelesExamenes;
    try {
      lstPanelesExamenes = dominiosService.getItemPanelesExamenes();
      return new ResponseTemplateGen<List<ItemPanelExamenDTO>>(lstPanelesExamenes, 200, "OK");
    } catch (BiosLISDAOException | BiosLISException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<ItemPanelExamenDTO>>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/dominio/obtenerMedicos/list")
  public ResponseEntity<List<CfgMedicos>> getTodosLosMedicos(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<CfgMedicos> cfgMedicos;
    cfgMedicos = dominiosService.getTodosLosMedicos();
    return ResponseEntity.ok(cfgMedicos);
  }

  @GetMapping("/api/dominio/obtenerConvenios/list")
  public ResponseEntity<List<CfgConvenios>> getTodosLosConvenios(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    List<CfgConvenios> cfgConvenios;
    try {
      cfgConvenios = dominiosService.getTodosLosConvenios();
      return ResponseEntity.ok(cfgConvenios);
    } catch (Exception e) {
      logger.error("error en " + e.getMessage());
    }
    return null;

  }

  @GetMapping("/api/dominio/obtenerDiagnosticos/list")
  public ResponseEntity<List<CfgDiagnosticos>> getTodosLosDiagnosticos(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      List<CfgDiagnosticos> cfgDiagnosticos;
      cfgDiagnosticos = dominiosService.getTodosLosDiagnosticos();
      return ResponseEntity.ok(cfgDiagnosticos);
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/api/dominio/tiposreceptores/list")
  public ResponseTemplateGen<List<LdvTiposreceptores>> getTiposReceptores(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<LdvTiposreceptores> lsdTiposReceptores;
    try {
      lsdTiposReceptores = dominiosService.getTiposReceptores();
      return new ResponseTemplateGen<List<LdvTiposreceptores>>(lsdTiposReceptores, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
    }
    return new ResponseTemplateGen<List<LdvTiposreceptores>>(null, 503, "OK");
  }

  @GetMapping("/api/dominio/viasnotificaciones/list")
  public ResponseTemplateGen<List<LdvViasnotificaciones>> getViasNotificaciones(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<LdvViasnotificaciones> ldvViasnotificaciones;
    try {
      ldvViasnotificaciones = dominiosService.getViasNotificaciones();
      return new ResponseTemplateGen<List<LdvViasnotificaciones>>(ldvViasnotificaciones, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
    }
    return new ResponseTemplateGen<List<LdvViasnotificaciones>>(null, 503, "OK");
  }

  @GetMapping("/api/dominio/pesquisas/list")
  public ResponseTemplateGen<List<CfgPesquisas>> getPesquisas(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<CfgPesquisas> listPesquisas;
    try {
      listPesquisas = dominiosService.getListPesquisas();
      return new ResponseTemplateGen<>(listPesquisas, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/dominio/loinc/list")
  public ResponseTemplateGen<List<LdvLoinc>> getLoincList(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<LdvLoinc> listLoinc;
    try {
      listLoinc = dominiosService.getListLoinc();
      return new ResponseTemplateGen<>(listLoinc, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/dominio/formatos/list")
  public ResponseTemplateGen<List<LdvFormatos>> getListFormatos(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<LdvFormatos> listFormatos;
    try {
      listFormatos = dominiosService.getListFormatos();
      return new ResponseTemplateGen<>(listFormatos, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/dominio/gruposExamenes/list")
  public ResponseTemplateGen<List<LdvGruposexamenes>> getGruposExamenes(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<LdvGruposexamenes> listGruposExamenes;
    try {
      listGruposExamenes = dominiosService.getGruposExamenes();
      return new ResponseTemplateGen<>(listGruposExamenes, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/dominio/indicacionesTM/list")
  public ResponseTemplateGen<List<LdvIndicacionestm>> getListIndicacionesTM(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<LdvIndicacionestm> listIndicacionesTM;
    try {
      listIndicacionesTM = dominiosService.getListIndicacionesTM();
      return new ResponseTemplateGen<>(listIndicacionesTM, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/dominio/glosasxtest/list")
  public ResponseTemplateGen<List<GlosaDTO>> getDominiosGlosaxTest(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<GlosaDTO> listGlosas;
    try {
      listGlosas = dominiosService.getDominiosGlosaxTest();
      return new ResponseTemplateGen<>(listGlosas, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/dominio/cfgantecedentes/list")
  public ResponseTemplateGen<List<CfgAntecedentes>> getDominiosAntedentes(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgAntecedentes> listCfgAntecedentes;
      listCfgAntecedentes = dominiosService.getDominiosAntedentes();
      return new ResponseTemplateGen<>(listCfgAntecedentes, 200, "OK");
    } catch (BiosLISDAOException ex) {
      logger.error(ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/dominio/cie10/list")
  public ResponseTemplateGen<List<LdvCie10>> getListCie10(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<LdvCie10> list = dominiosService.getListCie10();
      return new ResponseTemplateGen<>(list, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    }
  }

}
