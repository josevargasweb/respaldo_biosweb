/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupobios.bioslis.bs.ValoresReferenciaService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgValoresreferencia;
import com.grupobios.bioslis.entity.DatosUsuarios;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class ValoresReferenciaRestController {

  private static Logger logger = LogManager.getLogger(ValoresReferenciaRestController.class);

  @Autowired
  ValoresReferenciaService valoresReferenciaService;

  public ValoresReferenciaService getValoresReferenciaService() {
    return valoresReferenciaService;
  }

  public void setValoresReferenciaService(ValoresReferenciaService valoresReferenciaService) {
    this.valoresReferenciaService = valoresReferenciaService;
  }

  @GetMapping("/api/valoresReferencia/list/{idTest}")
  public ResponseTemplateGen<List<CfgValoresreferencia>> getListValoresReferenciaByTest(
      @PathVariable("idTest") int idTest, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgValoresreferencia> listaValoresReferencia = valoresReferenciaService.getValoresReferenciaByTest(idTest);
      return new ResponseTemplateGen<>(listaValoresReferencia, 200, "OK");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ValoresReferenciaRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/valoresReferencia/save")
  public ResponseTemplateGen<String> guardarValoresReferencia(@RequestParam("data") String data,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      ObjectMapper om = new ObjectMapper();
      List<CfgValoresreferencia> lista = om.readValue(data,
          om.getTypeFactory().constructCollectionType(List.class, CfgValoresreferencia.class));
      valoresReferenciaService.guardarValoresReferencia(lista, usuario.getDuIdusuario());
      return new ResponseTemplateGen<>("guardarValoresReferencia", 500, "Ok");
    } catch (BiosLISDAOException | IOException ex) {
      java.util.logging.Logger.getLogger(ValoresReferenciaRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @DeleteMapping("/api/valorReferencia/delete/{idVr}")
  public ResponseTemplateGen<String> deleteValorReferencia(@PathVariable("idVr") int idVr,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      valoresReferenciaService.deleteValorReferencia(idVr, usuario.getDuIdusuario());
      return new ResponseTemplateGen<>("deleteValorReferencia", 200, "Valor de referencia eliminado");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ValoresReferenciaRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>("deleteValorReferencia", 500, ex.getMessage());
    }
  }

}
