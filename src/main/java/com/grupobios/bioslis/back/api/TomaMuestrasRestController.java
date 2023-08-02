package com.grupobios.bioslis.back.api;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupobios.bioslis.bs.TomaMuestrasService;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ExamenOrdenMuestraDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.dto.OrdenesTMDTO;
import com.grupobios.bioslis.dto.TMClickDTO;
import com.grupobios.bioslis.dto.TestCurvaDTO;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvFichasestadostm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marco caracciolo
 */
@RestController
public class TomaMuestrasRestController {

  private static Logger logger = LogManager.getLogger(TomaMuestrasRestController.class);

  @Autowired
  TomaMuestrasService tomaMuestrasService;

  @PostMapping("/api/tomaMuestras/ordenes/{dia}/{mes}/{year}")
  public ResponseTemplateGen<List<OrdenesTMDTO>> rellenarTablaOrdenesTM(@PathVariable("dia") String dia,
      @PathVariable("mes") String mes, @PathVariable("year") String year, @Context HttpServletRequest req)
      throws ParseException {

    try {
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") == null) {
        return new ResponseTemplateGen<>(null, 408, "Error: Sesión ha expirado");
      }

      String fecha = dia + "/" + mes + "/" + year;
      String fechaInicio = null;
      String fechaFin = null;

      // Fecha vacía
      if (Integer.parseInt(dia) == 0 && Integer.parseInt(mes) == 0 && Integer.parseInt(year) == 0) {
        Date hoy = BiosLisCalendarService.getInstance().getTS();
        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
        fechaInicio = sdformat.format(hoy);
        fechaFin = fechaInicio;
      } else {
        fechaInicio = fecha + " 00:00:00";
        fechaFin = fecha;
      }
      logger.debug("Fecha Inicio: {}", fechaInicio);
      logger.debug("Fecha Fin: {}", fechaFin);

      List<OrdenesTMDTO> listaOrdenes = tomaMuestrasService.getListaOrdenesTM(fechaInicio, fechaFin);

      return new ResponseTemplateGen<>(listaOrdenes, 200, "Tabla de órdenes Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/tomaMuestras/muestras/{nOrden}")
  public ResponseTemplateGen<List<MuestrasDTO>> rellenarTablaMuestrasTM(@PathVariable("nOrden") Long nOrden)
      throws UnsupportedEncodingException, SQLException {
    try {
      List<MuestrasDTO> listaTest = tomaMuestrasService.getListaMuestrasTM(nOrden);
      return new ResponseTemplateGen<>(listaTest, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  /*
   * @GetMapping(value="/api/tomaMuestras/orden/ultimoRegistro/{nOrden}") public
   * ResponseEntity<OrdenesTMDTO> obtenerUltimoRegistro(@PathVariable("nOrden")
   * Long nOrden){ OrdenesTMDTO orden =
   * tomaMuestrasService.getUltimoRegistro(nOrden); return
   * ResponseEntity.ok(orden); }
   */
  @GetMapping(value = "/api/tomaMuestras/curvaTolerancia/{nOrden}/{idExamen}")
  public ResponseEntity<List<TestCurvaDTO>> obtenerMuestrasCurvaTolerancia(@PathVariable("nOrden") Long nOrden,
      @PathVariable("idExamen") Long idExamen) {
    try {
      List<TestCurvaDTO> lista = tomaMuestrasService.getListaTestCurva(nOrden, idExamen);
      return ResponseEntity.ok(lista);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping(value = "/api/examenes/orden/{nOrden}")
  public ResponseEntity<List<ExamenOrdenMuestraDTO>> obtenerExamenesOrden(@PathVariable("nOrden") Long nOrden) {
    try {
      List<ExamenOrdenMuestraDTO> lista = tomaMuestrasService.getExamenesOrdenMuestra(nOrden);
      return ResponseEntity.ok(lista);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  /*
   * @GetMapping("/api/tomaMuestras/tipoMuestra/{idMuestra}") public
   * ResponseEntity<List<ExamenOrdenMuestraDTO>> obtenerExamenesTipoMuestra(
   * 
   * @PathVariable("idMuestra") Long idMuestra) { List<ExamenOrdenMuestraDTO>
   * lista = tomaMuestrasService.getDetalleMuestra(idMuestra); return
   * ResponseEntity.ok(lista); }
   * 
   * @GetMapping(
   * "/api/tomaMuestras/tipoMuestra/{nOrden}/{idTipoMuestra}/{idEnvase}/{idDerivador}/{comparteMuestra}")
   * public ResponseEntity<List<ExamenOrdenMuestraDTO>>
   * obtenerExamenesTipoMuestraSM(@PathVariable("nOrden") Long nOrden,
   * 
   * @PathVariable("idTipoMuestra") Short idTipoMuestra, @PathVariable("idEnvase")
   * Short idEnvase,
   * 
   * @PathVariable("idDerivador") Short
   * idDerivador, @PathVariable("comparteMuestra") String comparteMuestra) { try {
   * List<ExamenOrdenMuestraDTO> lista =
   * tomaMuestrasService.getDetalleMuestraSM(nOrden, idTipoMuestra, idEnvase,
   * idDerivador, comparteMuestra); return ResponseEntity.ok(lista); } catch
   * (BiosLISDAOException ex) {
   * java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()
   * ).log(Level.SEVERE, null, ex); return null; } }
   */
  @PostMapping("/api/tomaMuestras/tipoMuestra")
  public ResponseTemplateGen<List<ExamenOrdenMuestraDTO>> obtenerExamenesTipoMuestraNuevo(
      @RequestBody HashMap<String, String> datosMuestra) {
    try {
      Long nOrden = Long.parseLong(datosMuestra.get("nOrden").toUpperCase());
      Short idTipoMuestra = Short.parseShort(datosMuestra.get("idTipoMuestra"));
      Short idEnvase = Short.parseShort(datosMuestra.get("idEnvase"));
      Short idDerivador = Short.parseShort(datosMuestra.get("idDerivador"));
      String comparteMuestra = datosMuestra.get("comparteMuestra");

      List<ExamenOrdenMuestraDTO> lista = tomaMuestrasService.getDetalleMuestraSM(nOrden, idTipoMuestra, idEnvase,
          idDerivador, comparteMuestra);
      return new ResponseTemplateGen<>(lista, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/tomaMuestras/tmClick/{nOrden}/{idUsuario}")
  public ResponseEntity<TMClickDTO> validaTmClick(@PathVariable("nOrden") Integer nOrden,
      @PathVariable("idUsuario") Long idUsuario) {
    logger.debug("Nro Orden: " + nOrden);
    logger.debug("ID Usuario: " + idUsuario);
    TMClickDTO tmClick;
    try {
      tmClick = tomaMuestrasService.validarTmClick(nOrden, idUsuario);
      return ResponseEntity.ok(tmClick);

    } catch (BiosLISDAOException e) {
      logger.debug("{}", e.getLocalizedMessage());

      return ResponseEntity.status(500).build();
    }
  }

  @PostMapping("/api/tomaMuestras/tmClick/volverNormalidadOrdenAnt/{nOrden}/{idUsuario}")
  public ResponseEntity<List<TMClickDTO>> volverNormalidadOrdenAnt(@PathVariable("nOrden") Integer nOrden,
      @PathVariable("idUsuario") Long idUsuario) {

    try {
      List<TMClickDTO> lisTmClick = tomaMuestrasService.volverNormalidadOrdenAnterior(nOrden, idUsuario);
      return ResponseEntity.ok(lisTmClick);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }

  }

  @PostMapping("/api/tomaMuestras/tmClick/volverNormalidadOrden/{nOrden}")
  public ResponseTemplateGen<String> volverNormalidadOrden(@PathVariable("nOrden") Integer nOrden) {
    try {
      tomaMuestrasService.volverNormalidadOrden(nOrden);
      return new ResponseTemplateGen<>("volverNormalidadOrden", 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/tomaMuestras/tmClick/volverNormalidadOrdenesUsuario/{idUsuario}")
  public ResponseTemplateGen<String> volverNormalidadOrdenesUsuario(@PathVariable("idUsuario") Long idUsuario) {
    try {
      tomaMuestrasService.volverNormalidadUsuario(idUsuario);
      return new ResponseTemplateGen<>("volverNormalidadOrdenesUsuario", 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/tomaMuestras/examenes/muestra/{idMuestra}")
  public ResponseEntity<List<ExamenOrdenDTO>> obtenerExamenesMuestra(@PathVariable("idMuestra") Long idMuestra) {
    try {
      List<ExamenOrdenDTO> lista = tomaMuestrasService.getExamenesMuestra(idMuestra);
      return ResponseEntity.ok(lista);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/api/tomaMuestras/examenes/{nOrden}/{idTipoMuestra}/{idEnvase}/{idDerivador}/{comparteMuestra}/{idExamen}")
  public ResponseEntity<List<ExamenOrdenDTO>> obtenerExamenesSinMuestra(@PathVariable("nOrden") Long nOrden,
      @PathVariable("idTipoMuestra") Short idTipoMuestra, @PathVariable("idEnvase") Short idEnvase,
      @PathVariable("idDerivador") Short idDerivador, @PathVariable("comparteMuestra") String comparteMuestra,
      @PathVariable("idExamen") Long idExamen) {
    try {
      List<ExamenOrdenDTO> lista = tomaMuestrasService.getExamenesMuestraSinIdMuestra(nOrden, idTipoMuestra, idEnvase,
          idDerivador, comparteMuestra, idExamen);
      return ResponseEntity.ok(lista);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/api/tomaMuestras/examenes/muestra/antiguo/{idMuestra}")
  public ResponseEntity<List<CfgExamenes>> obtenerExamenesMuestraAntiguo(@PathVariable("idMuestra") Long idMuestra) {
    try {
      List<CfgExamenes> lista = tomaMuestrasService.getExamenesMuestraAntiguo(idMuestra);
      for (CfgExamenes ce : lista) {
        logger.debug("examen: " + ce.getCfgMuestras().getCmueDescripcion());
      }
      return ResponseEntity.ok(lista);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/api/tomaMuestras/examenes/antiguo/{nOrden}/{idTipoMuestra}/{idEnvase}/{idDerivador}/{comparteMuestra}")
  public ResponseEntity<List<CfgExamenes>> obtenerExamenesSinMuestraAntiguo(@PathVariable("nOrden") Long nOrden,
      @PathVariable("idTipoMuestra") Short idTipoMuestra, @PathVariable("idEnvase") Short idEnvase,
      @PathVariable("idDerivador") Short idDerivador, @PathVariable("comparteMuestra") String comparteMuestra) {
    try {
      List<CfgExamenes> lista = tomaMuestrasService.getExamenesMuestraSinIdMuestraAntiguo(nOrden, idTipoMuestra,
          idEnvase, idDerivador, comparteMuestra);
      return ResponseEntity.ok(lista);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  @PostMapping("/api/tomaMuestras/tests/curvaTolerancia/save/{idMuestra}/{idUsuario}")
  public ResponseTemplateGen<OrdenesTMDTO> tomarMuestraCurva(@PathVariable("idMuestra") Long idMuestra,
      @PathVariable("idUsuario") Long idUsuario) {
    try {
      OrdenesTMDTO orden = tomaMuestrasService.tomarMuestraCurva(idMuestra, idUsuario);
      if (orden == null) {
        return new ResponseTemplateGen<>(null, 202, "Muestra tomada");
      }
      return new ResponseTemplateGen<>(orden, 200, "Muestra tomada");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/tomaMuestras/tests/curvaTolerancia/tomaManual/{idMuestra}/{fecha}/{idUsuario}")
  public ResponseTemplateGen<String> tomarMuestraManualCurva(@PathVariable("idMuestra") Long idMuestra,
      @PathVariable("fecha") Long fecha, @PathVariable("idUsuario") Long idUsuario) {
    try {
      tomaMuestrasService.tomarMuestraManualCurva(idMuestra, fecha, idUsuario);
      return new ResponseTemplateGen<>("tomarMuestraCurva", 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>("", 500, ex.getMessage());
    }
  }

  @PostMapping("/api/tomaMuestras/tipoMuestra/update/{idMuestra}/{idTipoMuestra}")
  public ResponseTemplateGen<String> cambiarTipoMuestraCM(@PathVariable("idMuestra") Long idMuestra,
      @PathVariable("idTipoMuestra") Short idTipoMuestra, @Context HttpServletRequest req) {
    Long idUsuario = 0L;
    DatosUsuarios usuario = null;
    HttpSession sesion = req.getSession();

    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

    } else {

      return new ResponseTemplateGen<>(null, 408, "Error: Sesión ha expirado");
    }
    try {
      tomaMuestrasService.cambiarTipoMuestraCM(idMuestra, idTipoMuestra, idUsuario);
      return new ResponseTemplateGen<>("cambiarTipoMuestra", 200, "Ok");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<>("cambiarTipoMuestra", 200, "Ok");
    }
  }

  @PostMapping("/api/tomaMuestras/tipoMuestra/update/{nOrden}/{idTipoMuestra}/{idEnvase}/{idDerivador}/{comparteMuestra}/{newTipoMuestra}")
  public ResponseTemplateGen<String> cambiarTipoMuestraSM(@PathVariable("nOrden") Long nOrden,
      @PathVariable("idTipoMuestra") Short idTipoMuestra, @PathVariable("idEnvase") Short idEnvase,
      @PathVariable("idDerivador") Short idDerivador, @PathVariable("comparteMuestra") String comparteMuestra,
      @PathVariable("newTipoMuestra") Short newTipoMuestra, @Context HttpServletRequest req) {
    Long idUsuario = 0L;
    DatosUsuarios usuario = null;
    HttpSession sesion = req.getSession();

    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

    } else {

      return new ResponseTemplateGen<>(null, 408, "Error: Sesión ha expirado");
    }
    try {
      tomaMuestrasService.cambiarTipoMuestraSM(nOrden, idTipoMuestra, idEnvase, idDerivador, comparteMuestra,
          newTipoMuestra, idUsuario);
      return new ResponseTemplateGen<>("cambiarTipoMuestra", 200, "Ok");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>("No se pudo ejecutar operación", 500, "Error");
    }
  }

  @PatchMapping("/api/tomaMuestras/tipoMuestra/update")
  public ResponseTemplateGen<String> cambiarTipoMuestra(@RequestBody HashMap<String, String> datosMuestra) {
    try {
      tomaMuestrasService.cambiarTipoMuestra(datosMuestra);
      return new ResponseTemplateGen<>("cambiarTipoMuestra", 200, "Ok");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>("No se pudo ejecutar operación", 500, e.getMessage());
    }
  }

  @GetMapping("/api/tomaMuestras/flebotomistas/list")
  public ResponseEntity<List<DatosUsuarios>> obtenerListaFlebotomistas() {
    try {
      List<DatosUsuarios> lista = tomaMuestrasService.getListaFlebotomistas();
      return ResponseEntity.ok(lista);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  @PatchMapping("/api/tomaMuestras/cambiarEstado/{idMuestra}/{idUsuario}/{estado}")
  public ResponseTemplateGen<OrdenesTMDTO> cambioEstadoMuestra(@PathVariable("idMuestra") long idMuestra,
      @PathVariable("idUsuario") long idUsuario, @PathVariable("estado") String estado) {

    try {
      OrdenesTMDTO orden = tomaMuestrasService.cambioEstadoMuestra(idMuestra, idUsuario, estado);
      if (orden != null) {
        return new ResponseTemplateGen<>(orden, 200, "Se ha cambiado estado de muestra");
      } else {
        return new ResponseTemplateGen<>(null, 404, "Se ha cambiado estado de muestra");
      }
    } catch (Exception e) {
      return new ResponseTemplateGen<>(null, 500, "Error");
    }
  }

  @PatchMapping("/api/tomaMuestras/tomarMuestra/codigoBarras/{idUsuario}/{codigoBarras}")
  public ResponseTemplateGen<OrdenesTMDTO> confirmarMuestraCodigoBarras(@PathVariable("idUsuario") long idUsuario,
      @PathVariable("codigoBarras") String codigoBarras) {
    try {
      DatosFichasmuestras dfm = tomaMuestrasService.getMuestraByCodigoBarras(codigoBarras);
      if (dfm != null) {
        if (dfm.getDfmEstadotm().equals(EstadosSistema.DFM_ESTADOTM_PENDIENTE)) {
          OrdenesTMDTO orden = tomaMuestrasService.confirmarTomaMuestra(dfm, idUsuario);
          if (orden != null) {
            return new ResponseTemplateGen<>(orden, 200, "Muestra tomada");
          } else {
            // se confirma la toma de muestra pero no se devuelve ningún objeto, ya que aún
            // no están todas las muestras de la orden confirmadas.
            return new ResponseTemplateGen<>(null, 202, "Muestra tomada");
          }
        } else {
          return new ResponseTemplateGen<>(null, 409, "Muestra ya ha sido tomada");
        }
      } else {
        return new ResponseTemplateGen<>(null, 404, "Muestra no identificada");
      }
    } catch (Exception e) {
      return new ResponseTemplateGen<>(null, 500, "Error: " + e);
    }
  }

  @PatchMapping("/api/tomaMuestras/tomarMuestra/{idMuestra}")
  public ResponseTemplateGen<OrdenesTMDTO> confirmarTomaMuestra(@PathVariable("idMuestra") long idMuestra,
      /* @PathVariable("idUsuario") long idUsuario, */ @Context HttpServletRequest req) {
    try {
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();

      } else {

        return new ResponseTemplateGen<>(null, 408, "Error: Sesión ha expirado");
      }

      DatosFichasmuestras dfm = tomaMuestrasService.getMuestraById(idMuestra);
      if (dfm != null) {

        OrdenesTMDTO orden = tomaMuestrasService.confirmarTomaMuestra(dfm, idUsuario);
        if (orden != null) {
          return new ResponseTemplateGen<>(orden, 200, "Muestra tomada");
        } else {
          // se confirma la toma de muestra pero no se devuelve ningún objeto, ya que aún
          // no están todas las muestras de la orden confirmadas.
          return new ResponseTemplateGen<>(null, 202, "Muestra tomada");
        }

      } else {
        return new ResponseTemplateGen<>(null, 404, "Error. Muestra no encontrada");
      }
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PatchMapping("/api/tomaMuestras/muestra/update")
  public ResponseTemplateGen<String> updateMuestra(@RequestBody DatosFichasmuestras dfm) {
    try {
      tomaMuestrasService.updateMuestra(dfm);
      return new ResponseTemplateGen<>("OK", 200, "Muestra actualizada correctamente");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>("", 500, ex.getMessage());
    }
  }

  @GetMapping("/api/tomaMuestras/muestra/zonacuerpo/update/idmuestra/{idMuestra}/idCuerpo/{idCuerpo}")
  public ResponseTemplateGen<String> updateZonaCuerpo(@PathVariable("idMuestra") long idMuestra,
      @PathVariable("idCuerpo") long idCuerpo, @Context HttpServletRequest req) {
    Long idUsuario = 0L;
    DatosUsuarios usuario = null;
    HttpSession sesion = req.getSession();

    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();
    } else {
      return new ResponseTemplateGen<>(null, 408, "Error: Sesión ha expirado");
    }
    try {
      // dfmDao.updateDatosFichasmuestras(dfm);long idMuestra,long idCuerpo

      tomaMuestrasService.updateMuestraZonaCuerpo(idMuestra, idCuerpo, idUsuario);
      return new ResponseTemplateGen<>("OK", 200, "Muestra actualizada correctamente");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>("", 500, ex.getMessage());
    }
  }

  @PatchMapping("/api/tomaMuestras/guardarObservacion/{idMuestra}/{observacion}")
  public ResponseTemplateGen<String> guardarObservacion(@PathVariable("idMuestra") long idMuestra,
      @PathVariable("observacion") String observacion, @Context HttpServletRequest req) {
    Long idUsuario = 0L;
    DatosUsuarios usuario = null;
    HttpSession sesion = req.getSession();

    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

    } else {

      return new ResponseTemplateGen<>(null, 408, "Error: Sesión ha expirado");
    }
    try {
      DatosFichasmuestras dfm = tomaMuestrasService.getMuestraById(idMuestra);
      tomaMuestrasService.guardarObservacion(dfm, observacion, idUsuario);
      return new ResponseTemplateGen<>("OK", 200, "Observación guardada");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>("", 500, ex.getMessage());
    }
  }

  @PatchMapping("/api/tomaMuestras/tipoAtencionUrgente/{nOrden}")
  public ResponseTemplateGen<String> cambiarEstadoUrgente(@PathVariable("nOrden") int nOrden,
      @Context HttpServletRequest req) {
    try {
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();

      } else {

        return new ResponseTemplateGen<>(null, 408, "Error: Sesión ha expirado");
      }
      tomaMuestrasService.cambiarEstadoUrgente(nOrden, idUsuario);
      return new ResponseTemplateGen<>("", 200, "Tipo de atención de Orden " + nOrden + " cambió a Urgente");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/tomaMuestras/ordenes/{nOrden}")
  public ResponseTemplateGen<List<OrdenesTMDTO>> rellenarTablaOrdenesByOrden(@PathVariable("nOrden") int nOrden)
      throws ParseException {
    try {
      List<OrdenesTMDTO> listaOrdenes = tomaMuestrasService.getListaOrdenesByOrden(nOrden);
      return new ResponseTemplateGen<>(listaOrdenes, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/tomaMuestras/estadosOrden")
  public ResponseTemplateGen<List<LdvFichasestadostm>> getEstadosOrden() {
    try {
      List<LdvFichasestadostm> lista = tomaMuestrasService.getListaEstadosOrden();
      return new ResponseTemplateGen<>(lista, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/tomaMuestras/generarMuestras")
  public ResponseTemplateGen<List<MuestrasDTO>> generarMuestrasTM(@RequestBody List<MuestrasDTO> data,
      @Context HttpServletRequest req) {
    logger.debug(data);
    try {
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();

      } else {

        return new ResponseTemplateGen<List<MuestrasDTO>>(null, 408, "Error: Sesión ha expirado");
      }

      ObjectMapper om = new ObjectMapper();
      // List<MuestrasDTO> lista = om.readValue(data,
      // om.getTypeFactory().constructCollectionType(List.class, MuestrasDTO.class));
      logger.debug(data);
      List<MuestrasDTO> listaMuestras = tomaMuestrasService.generarMuestra(data, idUsuario);
      return new ResponseTemplateGen<>(listaMuestras, 200, "Muestras generadas satisfactoriamente");
    } catch (BiosLISDAOException | ParseException | BiosLISDAONotFoundException ex) {
      java.util.logging.Logger.getLogger(TomaMuestrasRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  public TomaMuestrasService getTomaMuestrasService() {
    return tomaMuestrasService;
  }

  public void setTomaMuestrasService(TomaMuestrasService tomaMuestrasService) {
    this.tomaMuestrasService = tomaMuestrasService;
  }

}
