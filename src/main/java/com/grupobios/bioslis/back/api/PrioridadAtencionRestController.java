/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupobios.bioslis.bs.PrioridadAtencionService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgPrioridadatencion;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.common.Resizer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class PrioridadAtencionRestController {

  private static org.apache.logging.log4j.Logger logger = LogManager
      .getLogger(ConfiguracionFormulasRestController.class);

  PrioridadAtencionService prioridadAtencionService;

  public PrioridadAtencionService getPrioridadAtencionService() {
    return prioridadAtencionService;
  }

  public void setPrioridadAtencionService(PrioridadAtencionService prioridadAtencionService) {
    this.prioridadAtencionService = prioridadAtencionService;
  }

  @PostMapping("/api/prioridadatencion/filtro")
  public ResponseTemplateGen<List<CfgPrioridadatencion>> getListPrioridadAtencionFiltro(
      @RequestBody HashMap<String, String> filtros, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgPrioridadatencion> listPrioridadatencion = prioridadAtencionService.getPrioridadesAtencionFiltro(filtros);
      return new ResponseTemplateGen<>(listPrioridadatencion, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/prioridadatencion/insert")
  public ResponseTemplateGen<CfgPrioridadatencion> insertPrioridadAtencion(@RequestParam("data") String data,
      @RequestParam("imagen") MultipartFile imagen, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      ObjectMapper om = new ObjectMapper();
      CfgPrioridadatencion prio = om.readValue(data, CfgPrioridadatencion.class);
      if (!imagen.isEmpty()) {
        byte[] imageBytes = imagen.getBytes();
        Resizer resizer = new Resizer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage buffImg = resizer.genResizedImage(imageBytes, 640, 640);
        ImageIO.write(buffImg, "jpg", baos);
        Blob imageBlob = new SerialBlob(baos.toByteArray());
        prio.setCpaIconoprioridad(imageBlob);
      }
      String msj = prioridadAtencionService.agregarPrioridadAtencion(prio, usuario.getDuIdusuario());
      if (msj.equals("C")) {
        return new ResponseTemplateGen<>(prio, 200, "Prioridad atención ingresada");
      } else {
        return new ResponseTemplateGen<>(prio, 300, "Prioridad ya existe");
      }

    } catch (IOException | SQLException | BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/prioridadatencion/update")
  public ResponseTemplateGen<CfgPrioridadatencion> updatePrioridadAtencion(@RequestParam("data") String data,
      @RequestParam("imagen") MultipartFile imagen, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      ObjectMapper om = new ObjectMapper();
      CfgPrioridadatencion prio = om.readValue(data, CfgPrioridadatencion.class);

      CfgPrioridadatencion prioAux = prioridadAtencionService
          .getPrioridadAtencionById(prio.getCpaIdprioridadatencion());

      if ("sinFoto.txt".equals(imagen.getOriginalFilename())) {
        prio.setCpaIconoprioridad(null);
      }
      if ("conservarFoto.txt".equals(imagen.getOriginalFilename())) {
        prio.setCpaIconoprioridad(prioAux.getCpaIconoprioridad());
      }

      if (!imagen.isEmpty()) {
        byte[] imageBytes = imagen.getBytes();
        Resizer resizer = new Resizer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage buffImg = resizer.genResizedImage(imageBytes, 640, 640);
        ImageIO.write(buffImg, "jpg", baos);
        Blob imageBlob = new SerialBlob(baos.toByteArray());
        prio.setCpaIconoprioridad(imageBlob);
      } else {
        if ("sinFoto.txt".equals(imagen.getOriginalFilename())) {
          // prio.setCpaIconoprioridad(null);
        }
      }
      String msj = prioridadAtencionService.actualizarPrioridadAtencion(prio, usuario.getDuIdusuario());
      if (msj.equals("C")) {
        return new ResponseTemplateGen<>(prio, 200, "Prioridad atención actualizada");
      } else {
        return new ResponseTemplateGen<>(prio, 300, "Prioridad ya existe");
      }
    } catch (IOException | SQLException | BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/existePrioridad/{codigo}")
  public ResponseTemplateGen<CfgPrioridadatencion> compruebaExistePrioridadAtencion(
      @PathVariable("codigo") String codigo, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgPrioridadatencion> list = prioridadAtencionService.existePrioridadAtencion(codigo);
      if (!list.isEmpty()) {
        return new ResponseTemplateGen<>(list.get(0), 200, "Código de proceso ya existe");
      }
      return new ResponseTemplateGen<>(null, 404, "Código disponible");
    } catch (BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

}
