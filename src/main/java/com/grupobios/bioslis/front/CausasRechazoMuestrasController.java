/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCausasrechazosmuestrasDAO;
import com.grupobios.bioslis.dao.LogCfgTablasDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgCausasrechazosmuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author marco.c
 */
@Controller
public class CausasRechazoMuestrasController {

  ModelAndView mav = new ModelAndView();
  private static final Logger LOGGER = LogManager.getLogger(CambioPasswordController.class);

  @RequestMapping("/CausasRechazoMuestras")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes,
        ViewNames.ID_CAUSAS_RECHAZO_MUESTRAS);

    // aqui se inserta la accion que realiza usuario en modulos ***********

    if (usuario != null) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(11);
      leu.setLeuIdacciondato(0);
      leu.setLeuValornuevo("accede a Configuración de causas de rechazo");
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-************************************************************
    return mav;
  }

  @RequestMapping(value = "/CausasRechazoMuestras", method = RequestMethod.POST, params = "filtro")
  public void getCausasRechazo(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String codigo = request.getParameter("codigo").toUpperCase();
      String descripcion = request.getParameter("descripcion").toUpperCase();
      String activo = request.getParameter("activo");

      // List<CfgCausasrechazosmuestras> listaCausasRechazo = new ArrayList<>();
      CfgCausasrechazosmuestrasDAO ccrmDao = new CfgCausasrechazosmuestrasDAO();
      HashMap<String, String> filtros = new HashMap<>();
      filtros.put("codigo", codigo);
      filtros.put("descripcion", descripcion);
      filtros.put("activo", activo);
      List<CfgCausasrechazosmuestras> listaCausasRechazo = ccrmDao.getCausasRechazoFiltro(filtros);
      /*
       * if (codigo.length() <= 0 && descripcion.length() <= 0) { listaCausasRechazo =
       * ccrmDao.getCausasRechazoMuestras(); } else { if (codigo.length() > 0) {
       * listaCausasRechazo = ccrmDao.getCausasRechazoLikeCodigo(codigo); } else {
       * listaCausasRechazo = ccrmDao.getCausasRechazoLikeDesc(descripcion); } }
       */

      PrintWriter writer = response.getWriter();
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (CfgCausasrechazosmuestras ccrm : listaCausasRechazo) {
        JsonObjectBuilder causaBuilder = Json.createObjectBuilder();
        JsonObject causaJson;
        causaBuilder.add("id", ccrm.getCcrmIdcausarechazo()).add("codigo", ccrm.getCcrmCodigo()).add("descripcion",
            ccrm.getCcrmDescripcion());
        causaJson = causaBuilder.build();
        arrayBuilder.add(causaJson);
      }
      JsonObject root = rootBuilder.add("causa", arrayBuilder).build();
      writer.print(root);
    } catch (BiosLISDAOException ex) {
      LOGGER.error(ex.getMessage());
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    }
  }

  @RequestMapping(value = "/CausasRechazoMuestras", method = RequestMethod.POST, params = "filtroId")
  public void getCausasRechazoById(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String idCausa = request.getParameter("idCausa");

      CfgCausasrechazosmuestrasDAO ccrmDao = new CfgCausasrechazosmuestrasDAO();
      CfgCausasrechazosmuestras ccrm = ccrmDao.getCausaRechazoMuestraById(Short.parseShort(idCausa));

      PrintWriter writer = response.getWriter();
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

      JsonObjectBuilder causaBuilder = Json.createObjectBuilder();
      JsonObject causaJson;

      causaBuilder.add("idCausa", ccrm.getCcrmIdcausarechazo()).add("codigo", ccrm.getCcrmCodigo())
          .add("descripcion", ccrm.getCcrmDescripcion()).add("activo", ccrm.getCcrmActivo())
          .add("orden", ccrm.getCcrmSort());

      causaJson = causaBuilder.build();
      arrayBuilder.add(causaJson);

      JsonObject root = rootBuilder.add("causa", arrayBuilder).build();
      writer.print(root);
    } catch (BiosLISDAOException | IOException ex) {
      LOGGER.error(ex.getMessage());
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    }
  }

  @RequestMapping(value = "/CausasRechazoMuestras", method = RequestMethod.POST)
  public ModelAndView agregarCausaRechazo(CfgCausasrechazosmuestras ccrm, HttpServletRequest request,
      RedirectAttributes attributes) {
    try {
      HttpSession sesion = (HttpSession) request.getSession();
      DatosUsuarios usuario = new DatosUsuarios();
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      String codigo = request.getParameter("ccrmCodigo").toUpperCase();
      String descripcion = request.getParameter("ccrmDescripcion").toUpperCase();
      String ip = request.getParameter("ipEquipo");

      CfgCausasrechazosmuestrasDAO ccrmDao = new CfgCausasrechazosmuestrasDAO();

      // BigDecimal newId = ccrmDao.obtenerNuevoId();
      // ccrm.setCcrmIdcausarechazo(newId.byteValue());
      ccrm.setCcrmCodigo(codigo);
      ccrm.setCcrmDescripcion(descripcion);
      if (ccrm.getCcrmActivo() == null)
        ccrm.setCcrmActivo("N");

      String msj = ccrmDao.agregarCausaRechazoMuestras(ccrm, usuario.getDuIdusuario());

      // Log comentado por cristian el 10-05 ya no se ocupa este metodo
      /*
       * LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
       * logTablas.insertLog("Nueva Causa de Rechazo", "", "",
       * "CFG_CAUSASRECHAZOSMUESTRAS", 1, 1, "Equipo", ccrm.getCcrmIdcausarechazo(),
       * ip);
       */
      if (msj.equals("C")) {
        attributes.addFlashAttribute("mensaje", "Causa de rechazo ingresada correctamente");
      } else {
        attributes.addFlashAttribute("mensajeError", "Causa de rechazo ya existe");
      }

    } catch (BiosLISDAOException ex) {
      LOGGER.error(ex.getMessage());
      attributes.addFlashAttribute("mensajeError", ex.getMessage());
    }
    return new ModelAndView("redirect:/" + ViewNames.CAUSAS_RECHAZO_MUESTRAS);
  }

  @RequestMapping(value = "/CausasRechazoMuestras", method = RequestMethod.POST, params = "update")
  public ModelAndView actualizarCausaRechazo(CfgCausasrechazosmuestras ccrm, HttpServletRequest request,
      RedirectAttributes attributes) {
    try {
      HttpSession sesion = (HttpSession) request.getSession();
      DatosUsuarios usuario = new DatosUsuarios();
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      String idCausa = request.getParameter("idCausaRechazo");
      String ip = request.getParameter("ipEquipo");

      ccrm.setCcrmIdcausarechazo(Byte.parseByte(idCausa));
      if (ccrm.getCcrmActivo() == null)
        ccrm.setCcrmActivo("N");

      CfgCausasrechazosmuestrasDAO ccrmDao = new CfgCausasrechazosmuestrasDAO();
      CfgCausasrechazosmuestras ccrmOld = ccrmDao.getCausaRechazoMuestraById(Short.parseShort(idCausa));
      String msj = ccrmDao.actualizarCausaRechazoMuestras(ccrm, usuario.getDuIdusuario());

      // Log
      LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
      // logTablas.comparadorObjetos(ccrmOld, ccrm, "CFG_CAUSASRECHAZOSMUESTRAS", 2,
      // 1, "Equipo", Byte.parseByte(idCausa), ip);

      if (msj.equals("C")) {
        attributes.addFlashAttribute("mensaje", "Causa de rechazo actualizada correctamente");
      } else {
        attributes.addFlashAttribute("mensajeError", "Causa de rechazo ya existe");
      }

    } catch (BiosLISDAOException ex) {
      LOGGER.error(ex.getMessage());
      attributes.addFlashAttribute("mensajeError", ex.getMessage());
    }
    return new ModelAndView("redirect:/" + ViewNames.CAUSAS_RECHAZO_MUESTRAS);
  }

}
