package com.grupobios.bioslis.microbiologia.rest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.BiosLISBSException;
import com.grupobios.bioslis.bs.ExamenService;
import com.grupobios.bioslis.bs.MicrobiologiaService;
import com.grupobios.bioslis.bs.TestService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.AntibiogramaMicrobiologiaDTO;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaActionsDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaExamDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaTestDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

@RestController
public class MicrobiologiaRestController {

  static Logger log = LogManager.getLogger(MicrobiologiaRestController.class.getName());

  @Autowired
  private TestService testService;

  public TestService getTestService() {
    return testService;
  }

  public void setTestService(TestService testService) {
    this.testService = testService;
  }

  @Autowired
  private ExamenService examenService;

  public ExamenService getExamenService() {
    return examenService;
  }

  public void setExamenService(ExamenService examenService) {
    this.examenService = examenService;
  }

  private MicrobiologiaService microbiologiaService;

  public MicrobiologiaService getMicrobiologiaService() {
    return microbiologiaService;
  }

  public void setMicrobiologiaService(MicrobiologiaService microbiologiaService) {
    this.microbiologiaService = microbiologiaService;
  }

//OBTENER TEST
  @GetMapping("/api/microbiologia/{orderId}/examenes/getTest")
  public ResponseTemplateGen<List<MicrobiologiaTestDTO>> getTest(@PathVariable("orderId") String orderId,
      @Context HttpServletRequest context) throws BiosLISDAOException {

    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    List<MicrobiologiaTestDTO> capturaresultDTO = null;

    try {
      capturaresultDTO = microbiologiaService.getTest(Long.parseLong(orderId));
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<List<MicrobiologiaTestDTO>>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<List<MicrobiologiaTestDTO>>(capturaresultDTO, 200, "OK");
  }

//OBTENER TEST x Examen {orderId}
  /// api/microbiologia/-1/examenes/getTestxExamen
  @PostMapping("/api/microbiologia/{orderId}/examenes/getTestxExamen")
  public ResponseTemplateGen<List<MicrobiologiaTestDTO>> getTestxExamen(@PathVariable("orderId") String orderId,
      @RequestBody ParamResultadoExamenOrden prmResultadoExamenOrden, @Context HttpServletRequest context)
      throws BiosLISDAOException {

    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    List<MicrobiologiaTestDTO> capturaresultDTO = null;

    try {
      capturaresultDTO = microbiologiaService.getTestxExamen(Long.parseLong(orderId), prmResultadoExamenOrden);
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<List<MicrobiologiaTestDTO>>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<List<MicrobiologiaTestDTO>>(capturaresultDTO, 200, "OK");
  }

  // OBTENER ACTIONS
  @GetMapping("/api/microbiologia/orden/{IdOrder}/examen/{IdExamen}/test/{IdTest}/getActions")
  public ResponseTemplateGen<List<MicrobiologiaActionsDTO>> getActions(@PathVariable("IdOrder") String IdOrder,
      @PathVariable("IdExamen") String IdExamen, @PathVariable("IdTest") String IdTest,
      @Context HttpServletRequest context) throws BiosLISDAOException {

    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    List<MicrobiologiaActionsDTO> capturaresultDTO = null;
    try {
      capturaresultDTO = microbiologiaService.getAction(Long.parseLong(IdOrder), Long.parseLong(IdExamen),
          Long.parseLong(IdTest));
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<List<MicrobiologiaActionsDTO>>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<List<MicrobiologiaActionsDTO>>(capturaresultDTO, 200, "OK");
  }

  // GET STATUS
  @GetMapping("/api/microbiologia/getListStatus")
  public ResponseTemplateGen<HashMap<String, Object>> getListStatus(@Context HttpServletRequest context)
      throws BiosLISDAOException, ParseException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    HashMap<String, Object> getStatus = new HashMap<String, Object>();
    try {
      getStatus = microbiologiaService.getListStatus();
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<HashMap<String, Object>>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<HashMap<String, Object>>(getStatus, 200, "OK");
  }

  // GET MICROBIOLOGY
  @GetMapping("/api/microbiologia/getListMicrobiology")
  public ResponseTemplateGen<HashMap<String, Object>> getListMicrobiology(@Context HttpServletRequest context)
      throws BiosLISDAOException, ParseException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    HashMap<String, Object> getMiro = new HashMap<String, Object>();
    try {
      getMiro = microbiologiaService.getListMicro();
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<HashMap<String, Object>>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<HashMap<String, Object>>(getMiro, 200, "OK");
  }

  //
  @PostMapping("/api/microbiologia/updateTestList")
  public ResponseTemplateGen<MicrobiologiaTestDTO> updateEstado(@RequestBody MicrobiologiaTestDTO MTDTO,
      @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    MicrobiologiaTestDTO MicroTestDTO = new MicrobiologiaTestDTO();
    try {
      MicroTestDTO = microbiologiaService.updateTest(MTDTO);
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<MicrobiologiaTestDTO>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<MicrobiologiaTestDTO>(MicroTestDTO, 200, "OK");
  }

  @PostMapping("/api/microbiologia/updateActionList")
  public ResponseTemplateGen<MicrobiologiaActionsDTO> updateEstadoAction(@RequestBody MicrobiologiaActionsDTO MTADTO,
      @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    MicrobiologiaActionsDTO MicroActionDTO = new MicrobiologiaActionsDTO();
    try {
      MicroActionDTO = microbiologiaService.updateAction(MTADTO);
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<MicrobiologiaActionsDTO>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<MicrobiologiaActionsDTO>(MicroActionDTO, 200, "OK");
  }

  @PostMapping("/api/microbiologia/updateBodyPart")
  public ResponseTemplateGen<HashMap<String, Object>> updateBodyTiempos(@RequestBody MicrobiologiaActionsDTO MTADTO,
      @Context HttpServletRequest context) throws BiosLISDAOException, ParseException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    HashMap<String, Object> MicroActionDTO = new HashMap<String, Object>();
    try {
      MicroActionDTO = microbiologiaService.updateBodyTiempos(MTADTO);
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<HashMap<String, Object>>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<HashMap<String, Object>>(MicroActionDTO, 200, "OK");
  }

  @PostMapping("/api/microbiologia/updateExam")
  public ResponseTemplateGen<MicrobiologiaExamDTO> updateExamMicrobiologia(@RequestBody MicrobiologiaExamDTO MED,
      @Context HttpServletRequest context) throws BiosLISDAOException, ParseException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    MicrobiologiaExamDTO MicroExam = new MicrobiologiaExamDTO();
    try {
      MicroExam = microbiologiaService.updateExamMicrobiologia(MED);
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<MicrobiologiaExamDTO>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<MicrobiologiaExamDTO>(MicroExam, 200, "OK");
  }

  @GetMapping("/api/microbiologia/orden/{IdOrder}/examen/{IdExamen}/getExamenPorIds")
  public ResponseTemplateGen<MicrobiologiaExamDTO> getExamenOrderandId(@PathVariable("IdOrder") String IdOrder,
      @PathVariable("IdExamen") String IdExamen, @Context HttpServletRequest context)
      throws BiosLISDAOException, ParseException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    MicrobiologiaExamDTO MicroExam = new MicrobiologiaExamDTO();
    try {
      MicroExam = microbiologiaService.getExamenPorIds(Long.parseLong(IdOrder), Long.parseLong(IdExamen));
    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<MicrobiologiaExamDTO>(null, 404, e.getMessage());
    }
    return new ResponseTemplateGen<MicrobiologiaExamDTO>(MicroExam, 200, "OK");
  }

//creado por cristian 19-12
  @GetMapping("/api/microbiologia/getAntibiogramList")
  public ResponseTemplateGen<AntibiogramaMicrobiologiaDTO> getAntibiogramList(
      @RequestParam(value = "nOrder", defaultValue = "") Long nOrder,
      @RequestParam(value = "idExamen", defaultValue = "") Long idExamen,
      @RequestParam(value = "idTest", defaultValue = "") Long idTest, @Context HttpServletRequest context)
      throws BiosLISDAOException {

    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    AntibiogramaMicrobiologiaDTO result = new AntibiogramaMicrobiologiaDTO();
    try {
      result = microbiologiaService.getAntibiogramList(nOrder, idExamen, idTest);

    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<AntibiogramaMicrobiologiaDTO>(result, 505, "No se pudo traer registros");
    }
    return new ResponseTemplateGen<AntibiogramaMicrobiologiaDTO>(result, 200, "OK");

  }

  // creado por cristian 21-12

  @PutMapping("/api/microbiologia/test/action/list/{actionCode}")
  public ResponseTemplateGen<List<MicrobiologiaTestDTO>> doListAction2(@PathVariable("actionCode") String actionCode,
      @RequestBody List<MicrobiologiaTestDTO> microbiologia, @Context HttpServletRequest req) {

    HttpSession sesion = req.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<ResultadoNumericoTestExamenOrdenDTO> examenes = new ArrayList<ResultadoNumericoTestExamenOrdenDTO>();

    for (MicrobiologiaTestDTO mic : microbiologia) {
      ResultadoNumericoTestExamenOrdenDTO exa = new ResultadoNumericoTestExamenOrdenDTO();
      exa.setDF_NORDEN(mic.getORDERID());
      exa.setDFE_IDEXAMEN(mic.getEXAMID());
      exa.setDFET_IDTEST(mic.getTESTID());
      examenes.add(exa);
    }
    MicrobiologiaTestDTO result = new MicrobiologiaTestDTO();
    Long idUsuario = (long) 23;

    try {
      testService.doListAction(actionCode, examenes, idUsuario);

    } catch (BiosLISBSException e) {

      return new ResponseTemplateGen<List<MicrobiologiaTestDTO>>(microbiologia, 503, e.getMessage());
    }

    // esto es un salto-- para no volver a leer desde la base de datos ese dato.
    for (int i = 0; i < microbiologia.size(); i++) {
      if (microbiologia.get(i).getSTATUS().equals("FIRMADO")) {

        microbiologia.get(i).setSTATUS("DIGITADO");
      } else {
        microbiologia.get(i).setSTATUS("FIRMADO");
      }

    }

    return new ResponseTemplateGen<List<MicrobiologiaTestDTO>>(microbiologia, 200, "OK");

  }

  @PutMapping("/api/microbiologia/examen/action/list/{actionCode}")
  public ResponseTemplateGen<List<DatosFichasexamenes>> doListAction3(@PathVariable("actionCode") String actionCode,
      @RequestBody List<MicrobiologiaTestDTO> microbiologia, @Context HttpServletRequest req) {
    HttpSession sesion = req.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    List<ExamenOrdenDTO> listaExamenes = new ArrayList<ExamenOrdenDTO>();

    for (MicrobiologiaTestDTO mic : microbiologia) {
      ExamenOrdenDTO examen = new ExamenOrdenDTO();
      examen.setDFE_IDEXAMEN(mic.getEXAMID());
      examen.setDFE_NORDEN(mic.getORDERID());
      listaExamenes.add(examen);
    }
    Long idUsuario = 23L;

    List<DatosFichasexamenes> listaDFExamenes;
    try {
      listaDFExamenes = examenService.doAction(actionCode, listaExamenes, idUsuario);
      for (DatosFichasexamenes ex : listaDFExamenes) {
        if (ex.getDfeCodigoestadoexamen() == "A") {
          ex.setDfeCodigoestadoexamen("AUTORIZADO");
        } else if (ex.getDfeCodigoestadoexamen() == "I") {
          ex.setDfeCodigoestadoexamen("INGRESADO");
        } else if (ex.getDfeCodigoestadoexamen() == "P") {
          ex.setDfeCodigoestadoexamen("PENDIENTE");
        } else if (ex.getDfeCodigoestadoexamen() == "B") {
          ex.setDfeCodigoestadoexamen("BLOQUEADO");
        } else if (ex.getDfeCodigoestadoexamen() == "E") {
          ex.setDfeCodigoestadoexamen("EN PROCESO");
        } else if (ex.getDfeCodigoestadoexamen() == "F") {
          ex.setDfeCodigoestadoexamen("FIRMADO");
        }
      }
      return new ResponseTemplateGen<List<DatosFichasexamenes>>(listaDFExamenes, 200, "OK");
    } catch (BiosLISBSException | BiosLISDAONotFoundException | BiosLISDAOException e) {
      // logger.error(e.getMessage());
      return new ResponseTemplateGen<List<DatosFichasexamenes>>(null, 500, e.getMessage());
    }
  }

  @GetMapping("/api/microbiologia/getlistantibiogramaantibiotico")
  public ResponseTemplateGen<List<Object>> getListAntibiogramaAntibiotico(
      @RequestParam(value = "antibiogramName", defaultValue = "") String antibiogramName,
      @Context HttpServletRequest req) throws BiosLISDAOException {
    HttpSession sesion = req.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<Object> result = new ArrayList<Object>();
    try {
      result = microbiologiaService.getListAntibiogramaAntibiotico(antibiogramName);

    } catch (BiosLISDAOException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<List<Object>>(result, 505, "No se pudo traer registros");
    }
    return new ResponseTemplateGen<List<Object>>(result, 200, "OK");

  }

  // Nueva api creada por Marco Caracciolo 03/04/2023
  @PostMapping("/api/microbiologia/ordenes")
  public ResponseTemplateGen<List<OrdenInformeResultadoDTO>> getBOOrdenesMicrobiologia(
      @RequestBody BCFichaFiltroDTO fichaFiltroDTO, @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<OrdenInformeResultadoDTO> lstOrdenes = null;
    try {
      lstOrdenes = microbiologiaService.getBOOrdenesMicrobiologia(fichaFiltroDTO);
      return new ResponseTemplateGen<>(lstOrdenes, 200, "Ok");
    } catch (BiosLISException e) {
      log.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, "Error: " + e.getMessage());
    }

  }
}
