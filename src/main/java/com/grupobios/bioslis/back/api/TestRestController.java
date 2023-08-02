/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.BiosLISBSException;
import com.grupobios.bioslis.bs.TestService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisExecutorException;
import com.grupobios.bioslis.common.BioslisMailSender;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.BiosLISNotFoundException;
import com.grupobios.bioslis.dto.CRDetalleTestDTO;
import com.grupobios.bioslis.dto.ExamenesResultadosDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.LogFichasExamenesTestDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.entity.CfgGlosas;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasexamenestestId;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author eric.nicholls
 */

@RestController
public class TestRestController {

  private static Logger logger = LogManager.getLogger(TestRestController.class);

  @Autowired
  private BioslisMailSender bioslisMailS;

  @Autowired
  private TestService testService;

  @PostMapping("/api/test/events")
  public ResponseTemplateGen<List<LogFichasExamenesTestDTO>> getTestEvents(
      @RequestBody DatosFichasexamenestestId dfetId, @Context HttpServletRequest req) throws BiosLISDAOException {

    Long idUsuario;
    DatosUsuarios usuario = null;
    HttpSession sesion = req.getSession();

    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

    } else {
      return new ResponseTemplateGen<List<LogFichasExamenesTestDTO>>(null, 408, "Error: Sesión ha expirado");
    }

    logger.debug("Parametros getTestEvents {}", dfetId.toString());
    List<LogFichasExamenesTestDTO> lst = null;
    try {
      lst = testService.getTestEvents(dfetId);
      return new ResponseTemplateGen<List<LogFichasExamenesTestDTO>>(lst, 200, "");
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<List<LogFichasExamenesTestDTO>>(lst, 404, "No se encontraron eventos");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<List<LogFichasExamenesTestDTO>>(lst, 500, "Error en DAO.");
    }

  }

  @PostMapping("/api/test/action/{actionCode}")
  public ResponseTemplateGen<DatosFichasexamenestest> doAction(@PathVariable("actionCode") String actionCode,
      @RequestBody ResultadoNumericoTestExamenOrdenDTO dfetId, @Context HttpServletRequest req)
      throws BiosLISDAOException {

    logger.debug("Parametros doAction: idTest {} - actionCode {}", dfetId, actionCode);

    try {
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      HttpSession sesion = req.getSession();

      if (sesion.getAttribute("usuario") != null) {
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        idUsuario = usuario.getDuIdusuario();

      } else {

        return new ResponseTemplateGen<DatosFichasexamenestest>(null, 408, "Error: Sesión ha expirado");
      }

      String message = "";
      switch (actionCode) {
      case "FIRMAR":
        message = "Tests firmados exitosamente";
        break;
      case "RETIRARFIRMA":
        message = "Firma retirada";
        break;
      default:
        message = "Ok";
      }
      DatosFichasexamenestest dfet;
      dfet = testService.doAction(actionCode, dfetId, idUsuario);
      return new ResponseTemplateGen<DatosFichasexamenestest>(dfet, 200, message);

    } catch (BiosLISDAONotFoundException | BiosLISDAOException | BiosLISException | IOException e) {
      logger.error("No se ejecutó correctamente la acción.");
      return new ResponseTemplateGen<DatosFichasexamenestest>(null, 503, e.getMessage());
    } catch (BiosLisExecutorException e) {
      logger.warn("No se ejecutó correctamente la acción asociada. La transición de estado sí.");
      return new ResponseTemplateGen<DatosFichasexamenestest>(null, 201,
          "No se ejecutó correctamente la acción asociada. La transición de estado sí.");
    }

  }

  @PostMapping("/api/test/action/list/{actionCode}")
  public ResponseTemplateGen<DatosFichasexamenestest> doListAction(@PathVariable("actionCode") String actionCode,
      @RequestBody List<ResultadoNumericoTestExamenOrdenDTO> examenes, @Context HttpServletRequest req) {

    Long idUsuario;
    DatosUsuarios usuario = null;
    HttpSession sesion = req.getSession();

    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

    } else {
      return new ResponseTemplateGen<DatosFichasexamenestest>(null, 408, "Error: Sesión ha expirado");
    }
    try {
      testService.doListAction(actionCode, examenes, idUsuario);
    } catch (BiosLISBSException e) {
      return new ResponseTemplateGen<DatosFichasexamenestest>(null, 503, e.getMessage());
    }
    return new ResponseTemplateGen<DatosFichasexamenestest>(null, 200, "OK");

  }

  @GetMapping("/api/test/{idTest}/glosamultiple")
  public ResponseTemplateGen<List<CfgGlosas>> getTestGM(@PathVariable("idTest") Integer idTest,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgGlosas> lstResult = testService.getTestGM(idTest);
      return new ResponseTemplateGen<List<CfgGlosas>>(lstResult, 200, "OK");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<List<CfgGlosas>>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<List<CfgGlosas>>(null, 404, "Not found");
    }

  }

  @GetMapping("/api/test/{nroOrden}/{idExamen}/{idTest}/resultado")
  public ResponseTemplateGen<ExamenesResultadosDeUnaOrdenDTO> getResultadoTest(
      @PathVariable("nroOrden") Integer nroOrden, @PathVariable("idExamen") Integer idExamen,
      @PathVariable("idTest") Integer idTest, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      ExamenesResultadosDeUnaOrdenDTO result = testService.getResultadoTest(nroOrden, idExamen, idTest);
      return new ResponseTemplateGen<ExamenesResultadosDeUnaOrdenDTO>(result, 200, "OK");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<ExamenesResultadosDeUnaOrdenDTO>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<ExamenesResultadosDeUnaOrdenDTO>(null, 404, "Not found");
    }

  }

  // Obtiene todos los test cuyos resultados se calculan en formulas.
  @GetMapping("/api/test/dominio/calculated/list")
  public ResponseTemplateGen<List<CfgTest>> getCalculatedTest(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgTest> result = testService.getCalculatedTest();
      return new ResponseTemplateGen<>(result, 200, "OK");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<>(null, 404, "Not found");
    }

  }

  // Obtiene todos los test que aparecen en una formula que se envía como
  // parámetro.
  @PostMapping("/api/test/fromformula/list")
  public ResponseTemplateGen<List<CfgTest>> getFromFormula(@RequestBody String formula,
      @Context HttpServletRequest req) {
    HttpSession sesion = req.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      logger.debug("formula :{}", formula);
      List<CfgTest> result = testService.getFromFormula(formula);
      return new ResponseTemplateGen<>(result, 200, "OK");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException | BiosLISNotFoundException e) {
      return new ResponseTemplateGen<>(null, 404, "Not found");
    }

  }

  // Obtinen los test que se afectan por el resultado del test pasado como
  // parámetro

  @PostMapping("/api/test/dependent/list")
  public ResponseTemplateGen<List<CfgTest>> getDependentTest(@RequestBody Integer testId,
      @Context HttpServletRequest req) {
    HttpSession sesion = req.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgTest> result = testService.getRelFormulaTest(testId);
      return new ResponseTemplateGen<>(result, 200, "OK");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException | BiosLISNotFoundException e) {
      return new ResponseTemplateGen<>(null, 404, "Not found");
    }

  }

  /**
   * @return the testService
   */
  public TestService getTestService() {
    return testService;
  }

  /**
   * @param testService the testService to set
   */
  public void setTestService(TestService testService) {
    this.testService = testService;
  }

  public BioslisMailSender getBioslisMailS() {
    return bioslisMailS;
  }

  public void setBioslisMailS(BioslisMailSender bioslisMailS) {
    this.bioslisMailS = bioslisMailS;
  }

  // Obtiene todos los test que tinene tipo de resultado numérico

  @GetMapping("/api/test/numericos")
  public ResponseTemplateGen<List<CfgTest>> getTestNumericos(@Context HttpServletRequest req) {
//No sé quien desarrolló esto pero lo dejé con manejo de exception (Marco Caracciolo 06/01/2023)
    HttpSession sesion = req.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgTest> result = testService.getTestNumericos();
      return new ResponseTemplateGen<>(result, 200, "OK");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(TestRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }

  }

  //
  @PostMapping("/api/antedente/fromformula/list")
  public ResponseTemplateGen<List<String>> getAntecedenteFromFormula(@RequestBody String formula,
      @Context HttpServletRequest req) {
    HttpSession sesion = req.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      logger.debug("formula :{}", formula);
      List<String> result = testService.getAntecedenteFromFormula(formula);
      return new ResponseTemplateGen<>(result, 200, "OK");
    } finally {
    }

    
  }
  @PostMapping("/api/test/testDetail")
  public ResponseTemplateGen<List<CRDetalleTestDTO>> getTestDetalle(
		  @RequestBody Map<String, Integer> params, @Context HttpServletRequest context) throws BiosLISDAONotFoundException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
	    int idOrden = params.get("idOrden");
	    int idExam = params.get("idExam");
	    int idTest = params.get("idTest");
	    List<CRDetalleTestDTO> result = testService.getDetallesTest(idOrden, idExam, idTest);
      return new ResponseTemplateGen<List<CRDetalleTestDTO>>(result, 200, "OK");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<List<CRDetalleTestDTO>>(null, 500, e.getMessage());
    }

  }
}
