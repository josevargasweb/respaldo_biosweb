package com.grupobios.bioslis.back.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.BiosLISBSException;
import com.grupobios.bioslis.bs.OrdenService;
import com.grupobios.bioslis.bs.PacienteService;
import com.grupobios.bioslis.bs.TestService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BioslisMailSender;
import com.grupobios.bioslis.common.RangoValorResultado;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.BiosLISNotFoundException;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.DatosLineaTiempoDTO;
import com.grupobios.bioslis.dto.DeltaCheckAptoDTO;
import com.grupobios.bioslis.dto.ExamenNotasDTO;
import com.grupobios.bioslis.dto.ExamenesResultadosDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.FilaExamenesMuestrasDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.dto.NotificaResultadosDTO;
import com.grupobios.bioslis.dto.ObservacionCRDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;
import com.grupobios.bioslis.dto.ResultadoActualizacionTestDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoTestNotificacionDeUnaOrdenDTO;
import com.grupobios.bioslis.entity.CfgCellcounter;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.front.BiosLisNullHttpSesionException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

@RestController()
public class CapturaResultadosRestController {

  private PacienteService pacienteService;
  private OrdenService ordenService;
  private TestService testService;

  public TestService getTestService() {
    return testService;
  }

  public void setTestService(TestService testService) {
    this.testService = testService;
  }

  @Autowired
  private BioslisMailSender bioslisMailS;
  private static Logger logger = LogManager.getLogger(CapturaResultadosRestController.class);

  @GetMapping("/api/orden/{nroOrden}/examenes/muestras")
  public ResponseTemplateGen<List<FilaExamenesMuestrasDeUnaOrdenDTO>> getMuestrasExamenesOrden(
      @PathVariable("nroOrden") Long nroOrden, @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    logger.debug("Nro de orden buscada   {}", nroOrden);
    List<FilaExamenesMuestrasDeUnaOrdenDTO> lstExamenOrden = ordenService.getMuestrasExamenesOrden(nroOrden);
    if (lstExamenOrden != null) {
      for (FilaExamenesMuestrasDeUnaOrdenDTO ex : lstExamenOrden) {
        logger.debug(ex.getCE_ABREVIADO());
      }
    }

    // aqui se inserta la accion que realiza usuario en modulos ***********

    if (usuario != null && nroOrden != -1) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(11);
      leu.setLeuIdacciondato(0);
      leu.setLeuNombretabla("DATOS_FICHAS");
      leu.setLeuNombrecampo("DF_NORDEN");
      leu.setLeuValornuevo("Se visualizan resultados de Orden: " + nroOrden);
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-************************************************************
    return new ResponseTemplateGen<List<FilaExamenesMuestrasDeUnaOrdenDTO>>(lstExamenOrden, 200, "OK");
  }

  // Se usa en captura de resultados para llenar el datatable de resultados de
  // test.

  @PostMapping("/api/orden/{nroOrden}/examenes/resultados")
  public ResponseTemplateGen<List<ExamenesResultadosDeUnaOrdenDTO>> getResultadosExamenesOrden(
      @PathVariable("nroOrden") Long nroOrden, @RequestBody ParamResultadoExamenOrden prmResultadoExamenOrden,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    logger.debug("Resultados para orden {}, {} ", nroOrden, prmResultadoExamenOrden.getExamenes().size());
    List<ExamenesResultadosDeUnaOrdenDTO> lstExamenOrden;
    try {
      lstExamenOrden = ordenService.getResultadosExamenesOrden(nroOrden, prmResultadoExamenOrden);
      return new ResponseTemplateGen<>(lstExamenOrden, 200, "OK");
    } catch (BiosLISException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 404, e.getMessage());
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 503, e.getMessage());
    }
  }

  public OrdenService getOrdenService() {
    return ordenService;
  }

  public void setOrdenService(OrdenService ordenService) {
    this.ordenService = ordenService;
  }

  @PostMapping("/api/orden/update/examenes/resultados")
  public ResponseTemplateGen<Long> updateResultadosExamenesOrdenes(
      @RequestBody List<ExamenesResultadosDeUnaOrdenDTO> lista, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 303, "No hay sesion");
    logger.debug("Lista updateResultadosExamenesOrdenes {}", lista.size());
    try {
      ordenService.updateResultadosExamenesOrdenes(lista);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 505, "No se actualizó registro");
    }
    return new ResponseTemplateGen<>(Long.valueOf(lista.size()), 200, "OK");
  }

  // **actualizado por cristian 07-11 *** se agrega idusuario
  @PostMapping("/api/orden/update/examen/test/resultado")
  public ResponseTemplateGen<ResultadoActualizacionTestDTO> updateResultadoTestExamenOrden(
      @RequestBody ResultadoNumericoTestExamenOrdenDTO resultado, @Context HttpServletRequest context) {

    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    resultado.setIdUsuarioDigita(usuario.getDuIdusuario());
    // ***********************************
    ResultadoActualizacionTestDTO rpta = new ResultadoActualizacionTestDTO();
    try {
      rpta.setRangoValorResultado(ordenService.updateResultadoTestExamenOrden(resultado));
      rpta.setResultadoExamen(testService.getResultadoTest(resultado.getDF_NORDEN().intValueExact(),
          resultado.getDFE_IDEXAMEN().intValueExact(), resultado.getDFET_IDTEST().intValueExact()));

    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(rpta, 505, "No se actualizó registro");
    } catch (ParseException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(rpta, 506, "Resultado numérico inválido");
    } catch (BiosLISDAONotFoundException | BiosLISNotFoundException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(rpta, 404, "No se encontró registro de resultado");
    }
    return new ResponseTemplateGen<>(rpta, 200, "Resultado registrado");
  }

  /**
   * @return the bioslisMailS
   */
  public BioslisMailSender getBioslisMailS() {
    return bioslisMailS;
  }

  /**
   * @param bioslisMailS the bioslisMailS to set
   */
  public void setBioslisMailS(BioslisMailSender bioslisMailS) {
    this.bioslisMailS = bioslisMailS;
  }

  @PostMapping("/api/orden/{nroOrden}/examenes/resultados/notify")
  public ResponseTemplateGen<String> notificarResultadoCritico(@RequestBody NotificaResultadosDTO notificaResultadosDTO,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      // notificaResultadosDTO.
      bioslisMailS.notificarResultadoCritico(notificaResultadosDTO);

      return new ResponseTemplateGen<>("OK", 200, "OK");
    } catch (BiosLISException e) {
      return new ResponseTemplateGen<>("Error", 505, e.getMessage());
    }

  }

  @GetMapping("/api/orden/{nroOrden}/examen/{examenId}/test/{testId}")
  public ResponseTemplateGen<ResultadoTestNotificacionDeUnaOrdenDTO> getDatosTestNotificacion(
      @PathVariable("nroOrden") Long nroOrden, @PathVariable("examenId") Long examenId,
      @PathVariable("testId") Long testId, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    ResultadoTestNotificacionDeUnaOrdenDTO resultado = null;
    try {
      resultado = ordenService.getDatosTestNotificacion(nroOrden, examenId, testId);
      return new ResponseTemplateGen<ResultadoTestNotificacionDeUnaOrdenDTO>(resultado, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<ResultadoTestNotificacionDeUnaOrdenDTO>(null, 505,
          "No se obtuvo información: ".concat(e.getMessage()));
    }

  }

  @PostMapping("/api/orden/{nroOrden}/examen/{examenId}/test/{testId}/notificacion/add")
  public ResponseTemplateGen<String> insertDatosTestNotificacion(@PathVariable("nroOrden") Long nroOrden,
      @PathVariable("examenId") Long examenId, @PathVariable("testId") Long testId,
      @RequestBody ResultadoTestNotificacionDeUnaOrdenDTO notif, @Context HttpServletRequest context)
      throws ParseException {
    String resultado = null;
    try {
      HttpSession sesion = (HttpSession) context.getSession();
      Long idUsuario = null;
      logger.debug("********** {} :", notif.toString());

//      if (sesion.getAttribute("usuario") == null) {
//        return new ResponseTemplateGen<>(null, 401, "Sesión expirada.");
//      } else {
//        DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");
//        idUsuario = usuario.getDuIdusuario();
//      }
      resultado = ordenService.insertDatosTestNotificacion(nroOrden, examenId, testId, notif, idUsuario,
          notif.getTIPO_NOTIFICACION());// notif.getTIPO_NOTIFICACION());
      return new ResponseTemplateGen<String>(resultado, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<String>(null, 505, "No se pudo ingresar notificación".concat(e.getMessage()));
    } catch (BiosLISBSException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<String>(null, 505, "No se pudo ingresar notificación".concat(e.getMessage()));
    }

  }

  @PostMapping("/api/orden/{nroOrden}/examen/{examenId}/test/{testId}/notificacion/{notificacionId}/validador")
  public ResponseTemplateGen<Boolean> validarNumeroNotificacion(@PathVariable("nroOrden") Long nroOrden,
      @PathVariable("examenId") Long examenId, @PathVariable("testId") Long testId,
      @PathVariable("notificacionId") Long notificacionId, @Context HttpServletRequest context) throws ParseException {
    Boolean resultado = null;
    try {

      HttpSession sesion = (HttpSession) context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");

      resultado = (Boolean) ordenService.ValidarNumeroNotificaciones(nroOrden, examenId, testId, notificacionId);
      return new ResponseTemplateGen<Boolean>(resultado, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<Boolean>(null, 505, "No se pudo ingresar notificación".concat(e.getMessage()));
    }

  }

  // CRISTIAN 02-09 Contador diferencial Celulas - modifica fecha resultados
  @PostMapping("/api/orden/{nroOrden}/examen/{examenId}/test/{testId}/fechaResultado/{fechaResultado}")
  public ResponseTemplateGen<ResultadoTestNotificacionDeUnaOrdenDTO> modificarDatosContadorDiferencialCelulas(
      @PathVariable("nroOrden") Long nroOrden, @PathVariable("examenId") Long examenId,
      @PathVariable("testId") Long testId, @PathVariable("fechaResultado") String fechaResultado,
      @Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    try {

      ordenService.updateContadorFichaExamenTest(nroOrden, examenId, testId, fechaResultado);
      return new ResponseTemplateGen<ResultadoTestNotificacionDeUnaOrdenDTO>(null, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<ResultadoTestNotificacionDeUnaOrdenDTO>(null, 505,
          "No se obtuvo información".concat(e.getMessage()));
    }
  }

  // CRISTIAN 02-09 GRABAR DATOS Contador diferencial Celulas - Recibe objeto
  // Cellcounter e idExamen
  @PostMapping("/api/contadorcelulas")
  public ResponseTemplateGen<ResultadoTestNotificacionDeUnaOrdenDTO> insertarDatosContadorDiferencialCelulas(
      @RequestBody CfgCellcounter cellCounter, @Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    try {
      cellCounter.setCccIdcellcounter((byte) 1);
      ordenService.insertDatosContadorCelulas(cellCounter);
      return new ResponseTemplateGen<ResultadoTestNotificacionDeUnaOrdenDTO>(null, 200, "OK");
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<ResultadoTestNotificacionDeUnaOrdenDTO>(null, 505,
          "No se obtuvo información".concat(e.getMessage()));
    }

  }

  // Cristian Trae datos de cfgcellcounter
  @GetMapping("/api/contadorCelulas")
  public ResponseTemplateGen<CfgCellcounter> getContadorDiferencialCelulas(@Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    try {
      CfgCellcounter cellcounter = ordenService.getCellCounter();
      return new ResponseTemplateGen<CfgCellcounter>(cellcounter, 200, "OK");
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<CfgCellcounter>(null, 505, "No se obtuvo información".concat(e.getMessage()));
    }
  }

  // Cambios realizados por cristian 07-11 *** agrega usuario que agrega
  // resultados cellcoounter
  @PostMapping("/api/orden/update/examen/resultado/transmitido")
  public ResponseTemplateGen<ResultadoActualizacionTestDTO> updateResultadoExameneOrdeneTransmitido(
      @RequestBody List<ResultadoNumericoTestExamenOrdenDTO> resultado, @Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    Long idUsuario = null;
    if (sesion.getAttribute("usuario") == null) {
      return new ResponseTemplateGen<>(null, 401, "Sesión expirada.");
    } else {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();
    }

    ResultadoActualizacionTestDTO rpta = new ResultadoActualizacionTestDTO();
    List<BigDecimal> examenes = new ArrayList<BigDecimal>();
    try {
      for (ResultadoNumericoTestExamenOrdenDTO res : resultado) {
        res.setIdUsuarioDigita(idUsuario);
        // aqui buscar examen de la orden y test
        examenes = ordenService.getExamenByOrdenAndTest(res.getDF_NORDEN().longValue(),
            res.getDFET_IDTEST().longValue());

        for (int i = 0; i < examenes.size(); i++) {
          res.setDFE_IDEXAMEN(examenes.get(i));
          rpta.setRangoValorResultado(ordenService.updateResultadoTestExamenOrden(res));
        }
        // rpta.setResultadoExamen(testService.getResultadoTest(res.getDF_NORDEN().intValueExact(),
        // res.getDFE_IDEXAMEN().intValueExact(),
        // res.getDFET_IDTEST().intValueExact()));
        // ordenService.updateEstadoTransmitidoResultadoTestExamenOrden(resultado);
      }

    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 505, "No se actualizó registro");
    } catch (ParseException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 506, "Resultado numérico inválido");
    } catch (BiosLISDAONotFoundException | BiosLISNotFoundException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 404, "No se encontró registro de resultado");
    }
    return new ResponseTemplateGen<>(null, 200, "Resultado registrado");

  }

  @GetMapping("/api/test/all")
  public ResponseTemplateGen<List<CfgTest>> datosFichasExamenesTest(@Context HttpServletRequest context)
      throws BiosLISDAOException {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    try {
      List<CfgTest> resultado = new ArrayList<CfgTest>();
      resultado = ordenService.getTestAll();
      return new ResponseTemplateGen<List<CfgTest>>((List<CfgTest>) resultado, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<CfgTest>>(null, 505, "No se obtuvo información".concat(e.getMessage()));
    }

  }

  // TRAE LOS TEST POR NUMERO DE ORDEN
  @GetMapping("/api/orden/{nOrden}/testAll")
  public ResponseTemplateGen<List<DatosFichasexamenestest>> datosFichasExamenesTest(@PathVariable("nOrden") Long nOrden,
      @Context HttpServletRequest context) throws BiosLISDAOException {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    try {
      List<DatosFichasexamenestest> resultado = new ArrayList<DatosFichasexamenestest>();
      resultado = ordenService.getDatosFichasExamenesTestByOrden(nOrden);
      return new ResponseTemplateGen<List<DatosFichasexamenestest>>((List<DatosFichasexamenestest>) resultado, 200,
          "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<DatosFichasexamenestest>>(null, 505,
          "No se obtuvo información".concat(e.getMessage()));
    }

  }

  @GetMapping("/api/examenes/{examen}")
  public ResponseTemplateGen<Object> datosExamenById(@PathVariable("examen") Long examen,
      @Context HttpServletRequest context) throws BiosLISDAOException, BiosLISDAONotFoundException {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      Object resultado = ordenService.datosExamenById(examen);
      return new ResponseTemplateGen<Object>(resultado, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<Object>(null, 505, "No se obtuvo información".concat(e.getMessage()));
    }

  }

  @GetMapping("/api/unidades/medida/{idMedida}")
  public ResponseTemplateGen<Object> getUnidadesMedida(@PathVariable("idMedida") int idMedida,
      @Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      Object resultado = ordenService.unidadMedidaById(idMedida);
      return new ResponseTemplateGen<Object>(resultado, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<Object>(null, 505, "No se obtuvo información".concat(e.getMessage()));
    }
  }

  @GetMapping("/api/paciente/{idPaciente}/test/{idTest}")
  public ResponseTemplateGen<List<Object>> getPacienteTest(@PathVariable("idPaciente") Long idPaciente,
      @PathVariable("idTest") Long idTest, @Context HttpServletRequest context) throws BiosLISDAOException {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<Object> resultado = new ArrayList<Object>();
      resultado = ordenService.getDatosPacienteTest(idPaciente, idTest);
      return new ResponseTemplateGen<List<Object>>((List<Object>) resultado, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<Object>>(null, 505, "No se obtuvo información".concat(e.getMessage()));
    }

  }

  @GetMapping("/api/orden/{nOrden}/examentest")
  public ResponseTemplateGen<List<Object>> getDatosOrdenTestUnidad(@PathVariable("nOrden") Long nOrden,
      @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<Object> resultado = new ArrayList<Object>();
      resultado = ordenService.getDatosOrdenTestUnidad(nOrden);
      return new ResponseTemplateGen<List<Object>>(resultado, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<Object>>(null, 505, "No se obtuvo información ".concat(e.getMessage()));
    }

  }

  // ************************************************************************************
  public ResponseTemplateGen<Integer> updateEstadoResultadoExamenesOrdenes(@PathVariable("nroOrden") Long nroOrden,
      @PathVariable("examenId") Long examenId, @PathVariable("testId") Long testId,
      @PathVariable("estado") String estado, @Context HttpServletRequest context) {
    Integer resultado = null;
    try {

      HttpSession sesion = (HttpSession) context.getSession();
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");

      idUsuario = usuario.getDuIdusuario();
      ordenService.updateEstadoResultadoExamenesOrdenes(nroOrden, examenId, testId, estado, idUsuario);
      return new ResponseTemplateGen<>(resultado, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 505, "No se pudo ingresar notificación".concat(e.getMessage()));
    }
  }

  @PostMapping("/api/orden/reset/examen/test/resultado")
  public ResponseTemplateGen<RangoValorResultado> resetResultadoTestExamenOrden(
      @RequestBody ResultadoNumericoTestExamenOrdenDTO resultado, @Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    RangoValorResultado result = null;
    try {
      // DatosUsuarios usuario = checkSession(context.getSession());
      result = ordenService.resetResultadoTestExamenOrden(resultado, usuario);
      logger.debug(result);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(result, 505, "No se actualizó registro");
    } catch (ParseException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(result, 506, "Resultado numérico inválido");
    }
    return new ResponseTemplateGen<>(result, 200, "Resultado registrado");
  }

  @GetMapping("/api/rc1/paciente/{idPaciente}/deltacheck/{idTest}")
  public ResponseTemplateGen<DeltaCheckAptoDTO> getDeltaCheckTestPacienteV2(@PathVariable Long idPaciente,
      @PathVariable Long idTest, @Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    DeltaCheckAptoDTO result = null;
    try {
      result = pacienteService.getDeltaCheckTestPaciente(idPaciente, idTest, (long) -1);
      logger.debug(result);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(result, 505, "No se actualizó registro");
    }
    return new ResponseTemplateGen<>(result, 200, "Resultado registrado");
  }

  public PacienteService getPacienteService() {
    return pacienteService;
  }

  public void setPacienteService(PacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  // realizado por cristian 12_10 para linea de tiempo
  @GetMapping("/api/lineatiempo/orden/{nOrden}/examen/{idExamen}/test/{idTest}")
  public ResponseTemplateGen<DatosLineaTiempoDTO> getDatosLineaTiempo(@PathVariable Long nOrden,
      @PathVariable Long idExamen, @PathVariable Long idTest, @Context HttpServletRequest context) {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    DatosLineaTiempoDTO result = null;
    try {
      result = ordenService.getDatosLineaTiempo(nOrden, idExamen, idTest);
      logger.debug(result);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(result, 505, "No se actualizó registro");
    }
    return new ResponseTemplateGen<>(result, 200, "Resultado registrado");
  }

  private static DatosUsuarios checkSession(HttpSession httpSesion) throws BiosLisNullHttpSesionException {

    if (httpSesion == null) {
      throw new BiosLisNullHttpSesionException();
    }
    DatosUsuarios usuario = (DatosUsuarios) httpSesion.getAttribute("usuario");
    if (usuario == null) {
      throw new BiosLisNullHttpSesionException();
    }

    return usuario;
  }

//**actualizado por cristian 07-11 *** se agrega idusuario
  @PostMapping("/api/orden/examen/test/formula")
  public ResponseTemplateGen<ResultadoActualizacionTestDTO> getResultadoTestFormula(
      @RequestBody ResultadoNumericoTestExamenOrdenDTO resultado, @Context HttpServletRequest context) {

    /*
     * HttpSession sesion = context.getSession(); DatosUsuarios usuario = null;
     * usuario = (DatosUsuarios) sesion.getAttribute("usuario"); if (usuario ==
     * null) return new ResponseTemplateGen<>(null, 303, "No hay sesion");
     * resultado.setIdUsuarioDigita(usuario.getDuIdusuario());
     */
    // ***********************************
    ResultadoActualizacionTestDTO rpta = new ResultadoActualizacionTestDTO();
    try {

      rpta.setResultadoExamen(testService.getResultadoTest(resultado.getDF_NORDEN().intValueExact(),
          resultado.getDFE_IDEXAMEN().intValueExact(), resultado.getDFET_IDTEST().intValueExact()));

    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(rpta, 505, "No se actualizó registro");

    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<>(rpta, 505, "No se actualizó registro");
    }
    return new ResponseTemplateGen<>(rpta, 200, "Resultado registrado");

  }

  @GetMapping("/api/orden/impresion/informe/orden/{nOrden}")
  public ResponseTemplateGen getImpresionInforme(@PathVariable Long nOrden, @Context HttpServletRequest context)
      throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen(null, 401, "No hay sesion");

    // aqui se inserta la accion que realiza usuario en modulos ***********

    if (usuario != null) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(9);
      leu.setLeuIdacciondato(4);
      leu.setLeuNombretabla("DATOS_FICHAS");
      leu.setLeuNombrecampo("DF_NORDEN");
      leu.setLeuValornuevo("Se visualizan pre-informe de resultados de Orden: " + nOrden);
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-************************************************************
    return new ResponseTemplateGen(null, 200, "OK");
  }

  @GetMapping("/api/orden/impresion/comprobante/orden/{nOrden}")
  public ResponseTemplateGen getImpresionComprobante(@PathVariable Long nOrden, @Context HttpServletRequest context)
      throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen(null, 401, "No hay sesion");

    // aqui se inserta la accion que realiza usuario en modulos ***********

    if (usuario != null) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(9);
      leu.setLeuIdacciondato(4);
      leu.setLeuNombretabla("DATOS_FICHAS");
      leu.setLeuNombrecampo("DF_NORDEN");
      leu.setLeuValornuevo("comprobante de atencion Orden: " + nOrden);
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-************************************************************
    return new ResponseTemplateGen(null, 200, "OK");
  }

  // OBTENER SI LA ORDEN TIENE DOCUMENTOS
  @GetMapping("/api/{nOrden}/EstadoDocumento")
  public ResponseTemplateGen<String> getEstadoDocumento(@PathVariable Long nOrden,
      @Context HttpServletRequest context) {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    String EstadoDocumento;
    try {
      EstadoDocumento = ordenService.getEstadoDocumento(nOrden);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<String>("", 505, "Resultado no encontrado");
    }
    return new ResponseTemplateGen<String>(EstadoDocumento, 200, "Resultado encontrado");
  }

  // OBTENER OBSERVACIONES DEL EXAMEN
  @GetMapping("/api/{nOrden}/{idExamen}/getObservacion")
  public ResponseTemplateGen<ObservacionCRDTO> getObsExamen(@PathVariable Long nOrden, @PathVariable Long idExamen,
      @Context HttpServletRequest context) {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    ObservacionCRDTO EstadoDocumento;
    try {
      EstadoDocumento = ordenService.getObservacionesExamen(nOrden, idExamen);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<ObservacionCRDTO>(null, 505, "Error, No hay observaciones");
    }
    return new ResponseTemplateGen<ObservacionCRDTO>(EstadoDocumento, 200, "Resultado encontrado");
  }

  @GetMapping("/api/{nOrden}/{idExamen}/getNotasExamen")
  public ResponseTemplateGen<ExamenNotasDTO> getNotasExamen(@PathVariable Long nOrden, @PathVariable Long idExamen,
      @Context HttpServletRequest context) {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    ExamenNotasDTO Notas;
    try {
      Notas = ordenService.getNotasExamen(nOrden, idExamen);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<ExamenNotasDTO>(null, 505, "Error, No hay observaciones");
    }
    return new ResponseTemplateGen<ExamenNotasDTO>(Notas, 200, "Resultado encontrado");
  }

  @PutMapping("/api/PUTNotasExamen")
  public ResponseTemplateGen<Boolean> updateNotasExamen(@RequestBody HashMap<String, Object> notes,
      @Context HttpServletRequest context) {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    try {
      ordenService.updateNotasExamen(notes);
      // EstadoDocumento = ordenService.getObservacionesExamen(nOrden,idExamen);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<Boolean>(null, 505, "Error" + e);
    }
    return new ResponseTemplateGen<Boolean>(null, 200, "Resultado actualizado");
  }
}
