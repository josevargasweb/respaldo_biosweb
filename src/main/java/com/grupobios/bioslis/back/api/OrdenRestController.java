package com.grupobios.bioslis.back.api;

import java.io.IOException;
import java.time.LocalDate;
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

import com.grupobios.bioslis.bs.ExamenService;
import com.grupobios.bioslis.bs.InformeResultadosService;
import com.grupobios.bioslis.bs.OrdenService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisExecutorException;
import com.grupobios.bioslis.common.ResponseTemplate;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.DatosPacientesDAO;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.BOOrdenesFiltroDTO;
import com.grupobios.bioslis.dto.BiosLisEtiquetaDTO;
import com.grupobios.bioslis.dto.DatosFichasDTO;
import com.grupobios.bioslis.dto.FichaFiltroDTO;
import com.grupobios.bioslis.dto.LogFichasExamenesTestDTO;
import com.grupobios.bioslis.dto.OrdenExamenCapturaResultadoDTO;
import com.grupobios.bioslis.dto.OrdenExamenValidoDTO;
import com.grupobios.bioslis.dto.OrdenFullDTO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;
import com.grupobios.bioslis.dto.ParamRgoFechasPacienteDTO;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.common.DateFormatterHelper;

@RestController()
public class OrdenRestController {

  private static Logger logger = LogManager.getLogger(OrdenRestController.class);

  @Autowired
  private InformeResultadosService informeResultadosService;

  @Autowired
  private OrdenService ordenService;

  @Autowired
  private ExamenService examenService;

  @PostMapping(value = "/api/ordennueva")
  public ResponseTemplate filtroPacienteRut(@RequestParam("rutFiltro") String rutPaciente,
      @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplate(null, 401, "No hay sesion");
    rutPaciente = rutPaciente.replace(".", "");
    DatosPacientesDAO dpDAO = new DatosPacientesDAO();
    DatosPacientes paciente = dpDAO.getByRut(rutPaciente);

    if (paciente == null) {
      return null;
    } else {
      ResponseTemplate rp = new ResponseTemplate(paciente, 200, rutPaciente);
      return rp;
    }
  }

  @PostMapping("/api/ordenes")
  public ResponseTemplateGen<List<OrdenInformeResultadoDTO>> getOrdenesInformeResultados(
      @RequestBody FichaFiltroDTO fichaFiltroDTO, @Context HttpServletRequest context) {

    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<OrdenInformeResultadoDTO> lstOrdenes;
    try {
      lstOrdenes = informeResultadosService.getOrdenesInformeResultados(fichaFiltroDTO);
      return new ResponseTemplateGen<>(lstOrdenes, 200, "Ok");
    } catch (BiosLISException | BiosLISDAOException e) {
      return new ResponseTemplateGen<>(null, 503, e.getMessage());
    }

  }

  public InformeResultadosService getInformeResultadosService() {
    return informeResultadosService;
  }

  public void setInformeResultadosService(InformeResultadosService informeResultadosService) {
    this.informeResultadosService = informeResultadosService;
  }

  @PostMapping("/api/ordenes/examenes")
  public ResponseEntity<List<OrdenInformeResultadoDTO>> getOrdenesConSeccionesCapturaResultados(
      @RequestBody FichaFiltroDTO fichaFiltroDTO) throws BiosLISDAOException {

    List<OrdenInformeResultadoDTO> lstOrdenes = null;
    try {
      lstOrdenes = ordenService.getOrdenesConSeccionesCapturaResultados(fichaFiltroDTO);
    } catch (BiosLISException e) {
      logger.error(e.getMessage());
    }

    return ResponseEntity.ok(lstOrdenes);
  }

  @PostMapping("/api/ordenes/examenesseccion")
  public ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>> getOrdenesConSeccionesCapturaResultados(
      @RequestBody List<Integer> listaOrdenes, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<OrdenExamenCapturaResultadoDTO> lstOrdenes = null;
    try {
      if (listaOrdenes != null && !listaOrdenes.isEmpty()) {
        lstOrdenes = ordenService.getOrdenesExamenesCapturaResultados(listaOrdenes);
      }
      return new ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>>(lstOrdenes, 200, "Ok");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>>(null, 503, e.getMessage());
    }
  }

  public OrdenService getOrdenService() {
    return ordenService;
  }

  public void setOrdenService(OrdenService ordenService) {
    this.ordenService = ordenService;
  }

  @PostMapping("/api/common/ordenes/examenes")
  public ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>> getOrdenesxFiltro(
      @RequestBody BCFichaFiltroDTO bcFichaFiltroDTO, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    logger.debug("Hola   {}", bcFichaFiltroDTO);

    List<OrdenExamenCapturaResultadoDTO> lstOrdenes;
    try {
      lstOrdenes = ordenService.getOrdenesxFiltro(bcFichaFiltroDTO);
      return new ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>>(lstOrdenes, 200, "Ok");
    } catch (BiosLISException | BiosLISDAOException e) {
      logger.error(e);
      return new ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>>(null, 503, "Ok");
    }
  }

  @PostMapping("/api/common/ordenes/examenes/secciones")
  public ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>> getOrdenesExamenesSeccionesxFiltro(
      @RequestBody BCFichaFiltroDTO bcFichaFiltroDTO, @Context HttpServletRequest req) {

    logger.debug("Hola   {}", bcFichaFiltroDTO);

    List<OrdenExamenCapturaResultadoDTO> lstOrdenes;
    try {
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();

      } else {

        return new ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>>(null, 408, "Error: Sesión ha expirado");
      }

      lstOrdenes = ordenService.getOrdenesExamenesSeccionesxFiltro(bcFichaFiltroDTO);
      return new ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>>(lstOrdenes, 200, "Ok");
    } catch (BiosLISException | BiosLISDAOException e) {
      logger.error(e);
      return new ResponseTemplateGen<List<OrdenExamenCapturaResultadoDTO>>(null, 503, "Ok");
    }
  }

  // se cambio de ordenfulldto a OrdenInformeResultadoDTO
  @PostMapping("/api/common/ordenes")
  public ResponseTemplateGen<List<OrdenInformeResultadoDTO>> getSoloOrdenesxFiltro(
      @RequestBody BCFichaFiltroDTO bcFichaFiltroDTO, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    if (bcFichaFiltroDTO.getBo_nroDocumento() != null) {
      bcFichaFiltroDTO.setBo_nroDocumento(bcFichaFiltroDTO.getBo_nroDocumento().toUpperCase());
    }

//    logger.debug("Hola   {}", bcFichaFiltroDTO);
    List<OrdenInformeResultadoDTO> lstOrdenes;
    try {
      lstOrdenes = ordenService.getSoloOrdenesxFiltro(bcFichaFiltroDTO);
      return new ResponseTemplateGen<List<OrdenInformeResultadoDTO>>(lstOrdenes, 200, "Ok");
    } catch (BiosLISException e) {
      logger.error(e);
      return new ResponseTemplateGen<List<OrdenInformeResultadoDTO>>(null, 503, "Ok");
    }
  }

  @PostMapping("/api/ordenes/action/{actionCode}")
  public ResponseTemplateGen<List<OrdenFullDTO>> doAction(@PathVariable("actionCode") String actionCode,
      @RequestBody List<Long> listaOrdenes, @Context HttpServletRequest req) {

    try {
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();

      } else {

        return new ResponseTemplateGen<List<OrdenFullDTO>>(null, 408, "Error: Sesión ha expirado");
      }

      ordenService.doAction(actionCode, listaOrdenes, idUsuario);
      return new ResponseTemplateGen<List<OrdenFullDTO>>(null, 200, "Ok");
    } catch (BiosLISDAOException | BiosLISException | BiosLISDAONotFoundException | BiosLisExecutorException
        | IOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<OrdenFullDTO>>(null, 500,
          "Error: ".concat(e.getMessage().concat(e.getLocalizedMessage())));
    }

  }

  @PostMapping("/api/ordenes/insert")
  public ResponseTemplateGen<DatosFichas> insertOrden(@RequestBody DatosFichasDTO medReq,
      @Context HttpServletRequest req) throws Exception {
    try {
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();

      } else {

        return new ResponseTemplateGen<DatosFichas>(null, 408, "Error: Sesión ha expirado");
      }
      logger.debug("usuario : {} - id : {} ", usuario.getDuNombres(), idUsuario);
      // agregado por cristian 07-12 *************
      if (medReq.getCfgProcedencias() == -1) {
        medReq.setCfgProcedencias(0);
      }
      if (medReq.getDfIdprevision() == null) {
        medReq.setDfIdprevision((short) 0);
      }
      // **************************************************
      DatosFichas med = medReq.toDatosFicha();

      String ipEquipo = "";// request.getParameter("ipEquipo");
      med = ordenService.insertOrden(medReq, ipEquipo, idUsuario);
//      String mensaje = ordenService.getOrdenesConExamenesValidosDeUnPaciente(med, medReq.getLstExamenesDto());
//      mensaje = mensaje.concat(examenService.getNombresTestRepetidosExamenes(medReq.getLstExamenesDto()));
      return new ResponseTemplateGen<DatosFichas>(med, 200, "Orden creada");
    } catch (NumberFormatException | BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<DatosFichas>(null, 500,
          "Error: ".concat(e.getMessage().concat(e.getLocalizedMessage())));
    }
  }

  @PostMapping("/api/ordenes/fecha")
  public ResponseTemplateGen<List<DatosFichas>> ordenesPorFechaPaciente(@RequestBody ParamRgoFechasPacienteDTO parm,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    Integer idPac = parm.getIdPaciente();
    LocalDate fechaInicio = DateFormatterHelper.textDateToLocalDate(parm.getfInicio());
    LocalDate fechaFin = DateFormatterHelper.textDateToLocalDate(parm.getfFin());
    fechaFin = fechaFin.plusDays(1);
    try {
      List<DatosFichas> lst = ordenService.selectOrdenxRangoFechas(fechaInicio, fechaFin, idPac);
      return new ResponseTemplateGen<List<DatosFichas>>(lst, 200, "Ok");
    } catch (BiosLISDAOException e) {
      logger.error("No se pudo obtener ordenes {}", e.getMessage());
    }
    return new ResponseTemplateGen<List<DatosFichas>>(null, 200, "Ok");
  }

  @GetMapping("/api/ordenes/paciente/validos/{idpac}")
  public ResponseTemplateGen<List<OrdenExamenValidoDTO>> getOrdenesConExamenesValidosDeUnPaciente(
      @PathVariable("idpac") Integer idPac, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<OrdenExamenValidoDTO> lst = ordenService.getOrdenesConExamenesValidosDeUnPaciente(idPac);
      return new ResponseTemplateGen<List<OrdenExamenValidoDTO>>(lst, 200, "Ok");
    } catch (BiosLISDAOException e) {
      logger.error("No se pudo obtener ordenes {}", e.getMessage());
    }
    return new ResponseTemplateGen<List<OrdenExamenValidoDTO>>(null, 200, "Ok");

  }

  @PostMapping("/api/bordenes/examenes")
  public ResponseEntity<List<OrdenInformeResultadoDTO>> getBOOrdenesConSeccionesCapturaResultados(
      @RequestBody BOOrdenesFiltroDTO fichaFiltroDTO) throws BiosLISDAOException {

    List<OrdenInformeResultadoDTO> lstOrdenes = null;
    try {
      lstOrdenes = ordenService.getBOOrdenesConSeccionesCapturaResultados(fichaFiltroDTO);
    } catch (BiosLISException e) {
      logger.error(e.getMessage());
    }

    return ResponseEntity.ok(lstOrdenes);
  }

  @PostMapping("/api/bordenes/examenes/secciones")
  public ResponseTemplateGen<List<OrdenInformeResultadoDTO>> getBOOrdenesConSecciones(
      @RequestBody BCFichaFiltroDTO fichaFiltroDTO, @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<OrdenInformeResultadoDTO> lstOrdenes = null;
    try {
      lstOrdenes = ordenService.getBOOrdenesConSecciones(fichaFiltroDTO);
      return new ResponseTemplateGen<List<OrdenInformeResultadoDTO>>(lstOrdenes, 200, "Ok");
    } catch (BiosLISException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, "Error: " + e.getMessage());
    }
  }

  @GetMapping("/api/ordenFull/{nOrden}")
  public ResponseTemplateGen<OrdenFullDTO> getDatosOrdenFull(@PathVariable("nOrden") Integer nOrden,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    OrdenFullDTO orden = new OrdenFullDTO();

    try {
      orden = ordenService.getDatosOrdenFull(nOrden);
      return new ResponseTemplateGen<>(orden, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(OrdenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, "Error: " + ex.getMessage());
    }

  }

  @GetMapping(value = "/api/orden/{nOrden}/examen/{idExamen}/test/opcionales")
  public ResponseTemplateGen<List<CfgTest>> getTestOpcionalesExamenesOrden(@PathVariable("nOrden") Long nOrden,
      @PathVariable("idExamen") Long idExamen, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<CfgTest> lst;
    try {
      lst = ordenService.getTestOpcionalesExamenesOrden(nOrden, idExamen);
      return new ResponseTemplateGen<List<CfgTest>>(lst, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getLocalizedMessage());
      return new ResponseTemplateGen<List<CfgTest>>(null, 503, e.getMessage());
    }
  }

  @PostMapping(value = "/api/orden/{nOrden}/examen/{idExamen}/test/opcionales")
  public ResponseTemplateGen<List<CfgTest>> addTestOpcionalList2Examen(@PathVariable("nOrden") Long nOrden,
      @PathVariable("idExamen") Long idExamen, @RequestBody Integer[] testIdLst, @Context HttpServletRequest req) {
    try {

      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();
      } else {
        return new ResponseTemplateGen<List<CfgTest>>(null, 408, "Error: Sesión ha expirado");
      }

      // lst =
      ordenService.addTestOpcionalList2Examen(nOrden.intValue(), idExamen.intValue(), testIdLst, idUsuario);
      return new ResponseTemplateGen<>(null, 200, "OK");
    } catch (BiosLISDAOException | BiosLISDAONotFoundException e) {
      logger.error(e.getLocalizedMessage());
      return new ResponseTemplateGen<>(null, 503, e.getMessage());
    }
  }

  public ExamenService getExamenService() {
    return examenService;
  }

  public void setExamenService(ExamenService examenService) {
    this.examenService = examenService;
  }

  // *** agregado para editar orden 18-10

  @PostMapping("/api/ordenes/update")
  public ResponseTemplateGen<Object> UpdateOrdenes(@RequestBody DatosFichasDTO ordenExamenUpdate,
      @Context HttpServletRequest req) {

    try {
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();

        this.ordenService.updatefichaExamenTest(ordenExamenUpdate, idUsuario);
      } else {
        return new ResponseTemplateGen<Object>(null, 500, "Error: Sesión ha expirado");
      }
      logger.debug("usuario : {} - id : {} ", usuario.getDuNombres(), idUsuario);

      return new ResponseTemplateGen<Object>(null, 200, "Ok");
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<Object>(null, 503, "no se pudo realizar update");
    }
  }

  @GetMapping(value = "/api/net/orden/impresionetiquetas/{nOrden}")
  public ResponseTemplateGen<List<BiosLisEtiquetaDTO>> getEtiquetasOrden(@PathVariable("nOrden") Long nOrden,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<BiosLisEtiquetaDTO> lst;
    try {
      lst = ordenService.getEtiquetasOrden(nOrden);
      return new ResponseTemplateGen<List<BiosLisEtiquetaDTO>>(lst, 200, "OK");
    } catch (BiosLISDAOException | BiosLISException e) {
      logger.error(e.getLocalizedMessage());
      return new ResponseTemplateGen<List<BiosLisEtiquetaDTO>>(null, 503, e.getMessage());
    }
  }

  @PostMapping(value = "/api/net/orden/impresionetiquetas/{nOrden}")
  public ResponseTemplateGen<List<BiosLisEtiquetaDTO>> getEtiquetasOrden(@PathVariable("nOrden") Long nOrden,
      @RequestBody String[] codigos, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<BiosLisEtiquetaDTO> lst;
    try {
      lst = ordenService.getEtiquetasOrdenYCodigosBarras(nOrden, codigos);
      return new ResponseTemplateGen<List<BiosLisEtiquetaDTO>>(lst, 200, "OK");
    } catch (BiosLISDAOException | BiosLISException e) {
      logger.error(e.getLocalizedMessage());
      return new ResponseTemplateGen<List<BiosLisEtiquetaDTO>>(null, 503, e.getMessage());
    }
  }

  @GetMapping(value = "/api/orden/eventos/fichas")
  public ResponseTemplateGen<List<LogFichasExamenesTestDTO>> getEventosFichasByOrder(
      @RequestParam(value = "orderId", defaultValue = "0") String orderId,
      @RequestParam(value = "filtro", defaultValue = "-1") String filtro,
      @RequestParam(value = "idTest", defaultValue = "-1") String idTest, @Context HttpServletRequest req) {

    List<LogFichasExamenesTestDTO> lst;
    Long order = Long.parseLong(orderId);

    try {
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();

      } else {
        return new ResponseTemplateGen<List<LogFichasExamenesTestDTO>>(null, 500, "Error: Sesión ha expirado");
      }
      lst = ordenService.getEventosFichasByOrder(order, filtro, idTest);
      return new ResponseTemplateGen<List<LogFichasExamenesTestDTO>>(lst, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getLocalizedMessage());
      return new ResponseTemplateGen<List<LogFichasExamenesTestDTO>>(null, 503, e.getMessage());
    }
  }

  @PostMapping("/api2/bordenes/examenes/secciones")
  public ResponseTemplateGen<List<OrdenInformeResultadoDTO>> getBOOrdenesConSecciones2(
      @RequestBody BCFichaFiltroDTO fichaFiltroDTO, @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
//    DatosUsuarios usuario = null;
//    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
//    if (usuario == null)
//      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<OrdenInformeResultadoDTO> lstOrdenes = null;
    try {
      lstOrdenes = ordenService.getBOOrdenesConSecciones_Urgentes(fichaFiltroDTO);
      return new ResponseTemplateGen<List<OrdenInformeResultadoDTO>>(lstOrdenes, 200, "Ok");
    } catch (BiosLISException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, "Error: " + e.getMessage());
    }
  }

}
