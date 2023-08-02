package com.grupobios.bioslis.back.api;

import java.util.List;

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

import com.grupobios.bioslis.bs.BiosLisFormulaResultIsNaNException;
import com.grupobios.bioslis.bs.TestService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.CfgCondicionesformulasDTO;
import com.grupobios.bioslis.dto.CfgTestDTO;
import com.grupobios.bioslis.dto.FiltroTestDTO;
import com.grupobios.bioslis.dto.FormulaTestDto;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosUsuarios;

@RestController
public class ConfiguracionFormulasRestController {

  private static Logger logger = LogManager.getLogger(ConfiguracionFormulasRestController.class);

  @Autowired
  TestService testService;

  public TestService getTestService() {
    return testService;
  }

  public ConfiguracionFormulasRestController() {
  }

  @PostMapping("/api/configuracionformulas/set")
  public ResponseTemplateGen<CfgTest> insertFormula(@RequestBody FormulaTestDto formulaTestDto,
      @Context HttpServletRequest context) {
      
      HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
      
    try {
      CfgTest ct = testService.setFormula(formulaTestDto.getTestId(), formulaTestDto.getFormula(), usuario.getDuIdusuario());
      return new ResponseTemplateGen<>(ct, 200, "Ok");
    } catch (BiosLISDAONotFoundException e) {
      logger.warn("No se pudo encontrar test a actualizar {}", e.getLocalizedMessage());
      return new ResponseTemplateGen<>(null, 404, "Test no encontrado");
    } catch (BiosLISDAOException | BiosLISException e) {
      logger.error("Error al actualizar formula {}", e.getLocalizedMessage());
      return new ResponseTemplateGen<>(null, 505, "No se pudo actualizar fórmula:".concat(e.getLocalizedMessage()));
    }
  }

  @GetMapping("/api/configuracionformulas/get/{testId}")
  public ResponseTemplateGen<CfgTest> getCfgTestById(@PathVariable("testId") Integer testId,
      @Context HttpServletRequest context) {

      HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
      
    try {
      CfgTest ct = testService.getCfgTest(testId);
      return new ResponseTemplateGen<>(ct, 200, "OK");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<>(null, 404, "Not found");
    }
  }

  public void setTestService(TestService testService) {
    this.testService = testService;
  }

  @PostMapping("/api/configuracionformulas/getxfiltro")
  public ResponseTemplateGen<List<CfgTestDTO>> getTestByFiltroTest(@RequestBody FiltroTestDTO filtroTest,
      @Context HttpServletRequest context) {
      
      HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgTestDTO> ctLst = testService.getTestByFiltroTest(filtroTest);
      return new ResponseTemplateGen<>(ctLst, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error("No se pudo recuperar datos. {}", e.getLocalizedMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    }
  }

  @PostMapping("/api/configuracionformulas/savecond")
  public ResponseTemplateGen<CfgTest> saveCond(@RequestBody CfgCondicionesformulasDTO cfgCond,
      @Context HttpServletRequest context) {
      HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      String condicion = "";
      CfgTest ct = testService.saveCond(cfgCond, condicion);
      return new ResponseTemplateGen<>(ct, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error("No se pudo recuperar datos. {}", e.getLocalizedMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    }
  }

  @PostMapping("/api/configuracionformulas/check")
  public ResponseTemplateGen<String> validateFormula(@RequestBody String formula, @Context HttpServletRequest context) {
      HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      String ct = testService.validateFormula(formula);
      return new ResponseTemplateGen<>(ct, 200, ct);
    } catch (BiosLisFormulaResultIsNaNException e) {
      logger.warn(" {}", e.getLocalizedMessage());
      return new ResponseTemplateGen<>(null, 405, e.getLocalizedMessage());
    }
  }

}
