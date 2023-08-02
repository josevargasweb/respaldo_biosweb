/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.LdvIndicacionesExamenesDAO;
import com.grupobios.bioslis.dao.LdvIndicacionesTMDAO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;
import com.grupobios.bioslis.entity.LdvIndicacionestm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

@RestController
public class LdvIndicacionesRestController {

  private static Logger logger = LogManager.getLogger(LdvIndicacionesRestController.class);

  @PostMapping("/api/ldvIndicaciones/insert/{getIndicaciones}")
  public ResponseTemplateGen<String> insertIndicacionExamen(@PathVariable("getIndicaciones") String getIndicaciones,
      @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    String mensaje;
    try {
      LdvIndicacionesExamenesDAO ldvie = new LdvIndicacionesExamenesDAO();
      // String descripcion = request.getParameter("insertIndicacionesExa");
      LdvIndicacionesexamenes indiExa = new LdvIndicacionesexamenes();

      indiExa.setLdvieDescripcion(getIndicaciones.toUpperCase());

      // insertLdvIndicacionExamen.setLdvieIdindicacionesexamen(ldvie.getLastId());
      mensaje = ldvie.insertIndicacionesExamen(indiExa, usuario.getDuIdusuario());

      // capturaresultDTO =
      // microbiologiaService.getTestxExamen(Long.parseLong(orderId),prmResultadoExamenOrden);
    } catch (BiosLISDAOException e) {
      // log.error(e.getMessage());
      return new ResponseTemplateGen<String>("Problemas al crear nueva indicación", 404, e.getMessage());
    }
    return new ResponseTemplateGen<String>(mensaje, 200, "OK");
  }

  @PostMapping("/api/ldvIndicaciones/update")
  public ResponseTemplateGen<String> updateIndicacionExamen(@RequestBody LdvIndicacionesexamenes indiquis,
      @Context HttpServletRequest context) throws BiosLISDAOException {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    String mensaje = "";
    try {
      LdvIndicacionesExamenesDAO ldvie = new LdvIndicacionesExamenesDAO();
      mensaje = ldvie.updateIndicacionesExamen(indiquis, usuario.getDuIdusuario());

    } catch (BiosLISDAOException ex) {
      // Logger.getLogger(IndicacionesController.class.getName()).log(Level.SEVERE,
      // null, ex);
      return new ResponseTemplateGen<String>("Problemas al actualizar indicación", 404, ex.getMessage());
    }
    return new ResponseTemplateGen<String>(mensaje, 200, "OK");
  }

  /////////////////////////
  @GetMapping("/api/ldvIndicacionestm/getList")
  public ResponseTemplateGen<List<LdvIndicacionestm>> getIndicacionTM(@Context HttpServletRequest context)
      throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<LdvIndicacionestm> inditm = null;
    try {
      LdvIndicacionesTMDAO ldvie = new LdvIndicacionesTMDAO();

      // LdvIndicacionesexamenes insertLdvIndicacionExamen = new
      // LdvIndicacionesexamenes();
      inditm = ldvie.getIndicacionesTM();

    } catch (BiosLISDAOException ex) {
      // Logger.getLogger(IndicacionesController.class.getName()).log(Level.SEVERE,
      // null, ex);
      return new ResponseTemplateGen<List<LdvIndicacionestm>>(inditm, 404, ex.getMessage());
    }
    return new ResponseTemplateGen<List<LdvIndicacionestm>>(inditm, 200, "OK");
  }

  @PostMapping("/api/ldvIndicacionestm/insert/{getIndicacionestm}")
  public ResponseTemplateGen<String> insertIndicacionTM(@PathVariable("getIndicacionestm") String getIndicaciones,
      @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    String mensaje;
    try {
      LdvIndicacionesTMDAO ldvie = new LdvIndicacionesTMDAO();
      LdvIndicacionestm indiTM = new LdvIndicacionestm();
      indiTM.setLdvitmDescripcionindicacion(getIndicaciones);
      mensaje = ldvie.insertIndicacionesTM(indiTM, usuario.getDuIdusuario());
    } catch (BiosLISDAOException e) {
      // log.error(e.getMessage());
      return new ResponseTemplateGen<String>("Problemas al crear nueva indicación", 404, e.getMessage());
    }
    return new ResponseTemplateGen<String>(mensaje, 200, "OK");
  }

  @PostMapping("/api/ldvIndicacionestm/update")
  public ResponseTemplateGen<String> updateIndicacionTM(@RequestBody LdvIndicacionestm indiquisTM,
      @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    String mensaje;
    try {
      LdvIndicacionesTMDAO ldvie = new LdvIndicacionesTMDAO();
      mensaje = ldvie.updateIndicacionesTM(indiquisTM, usuario.getDuIdusuario());
    } catch (BiosLISDAOException e) {
      // log.error(e.getMessage());
      return new ResponseTemplateGen<String>("Problemas al crear nueva indicación", 404, e.getMessage());
    }
    return new ResponseTemplateGen<String>(mensaje, 200, "OK");
  }

}
