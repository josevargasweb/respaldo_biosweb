/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupobios.bioslis.bs.ConfiguracionEnvasesService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.EnvaseDTO;
import com.grupobios.bioslis.entity.CfgEnvases;
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
public class ConfiguracionEnvasesRestController {

  ConfiguracionEnvasesService configuracionEnvasesService;

  public ConfiguracionEnvasesService getConfiguracionEnvasesService() {
    return configuracionEnvasesService;
  }

  public void setConfiguracionEnvasesService(ConfiguracionEnvasesService configuracionEnvasesService) {
    this.configuracionEnvasesService = configuracionEnvasesService;
  }

  @PostMapping("/api/envases/filtro")
  public ResponseTemplateGen<List<CfgEnvases>> getEnvasesFiltro(@RequestBody HashMap<String, String> filtros,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");

    try {
      List<CfgEnvases> list = configuracionEnvasesService.getEnvasesFiltro(filtros);
      return new ResponseTemplateGen<>(list, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      Logger.getLogger(ConfiguracionEnvasesRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/envase/insert")
  public ResponseTemplateGen<CfgEnvases> insertEnvase(@RequestParam("data") String data,
      @RequestParam("imagen") MultipartFile imagen, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      ObjectMapper om = new ObjectMapper();
      CfgEnvases envase = om.readValue(data, CfgEnvases.class);
      if (!imagen.isEmpty()) {
        byte[] imageBytes = imagen.getBytes();
        Resizer resizer = new Resizer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage buffImg = resizer.genResizedImage(imageBytes, 640, 640);

        ImageIO.write(buffImg, "jpg", baos);

        Blob imageBlob = new SerialBlob(baos.toByteArray());
        envase.setCenvImagenenvase(imageBlob);
      }
      String msj = configuracionEnvasesService.agregarEnvase(envase, usuario.getDuIdusuario());
      if (msj.equals("C")) {
        return new ResponseTemplateGen<>(envase, 200, "Envase ingresado");
      } else {
        return new ResponseTemplateGen<>(envase, 300, "Envase existente");
      }

    } catch (BiosLISDAOException | IOException | SQLException ex) {
      Logger.getLogger(ConfiguracionEnvasesRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/envase/update")
  public ResponseTemplateGen<CfgEnvases> updateEnvase(@RequestParam("data") String data,
      @RequestParam("imagen") MultipartFile imagen, @Context HttpServletRequest context) {

    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      ObjectMapper om = new ObjectMapper();
      CfgEnvases envase = om.readValue(data, CfgEnvases.class);

      CfgEnvases envaseAux = configuracionEnvasesService.getEnvaseById(envase.getCenvIdenvase());

      if ("sinFoto.txt".equals(imagen.getOriginalFilename())) {
        envase.setCenvImagenenvase(null);
      }
      if ("conservarFoto.txt".equals(imagen.getOriginalFilename())) {
        envase.setCenvImagenenvase(envaseAux.getCenvImagenenvase());
      }

      if (!imagen.isEmpty()) {
        byte[] imageBytes = imagen.getBytes();
        Resizer resizer = new Resizer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage buffImg = resizer.genResizedImage(imageBytes, 640, 640);
        ImageIO.write(buffImg, "jpg", baos);
        Blob imageBlob = new SerialBlob(baos.toByteArray());
        envase.setCenvImagenenvase(imageBlob);
      } else {
        if ("sinFoto.txt".equals(imagen.getOriginalFilename())) {
          // envase.setCenvImagenenvase(null);
        }
      }
      String msj = configuracionEnvasesService.actualizarEnvase(envase, usuario.getDuIdusuario());
      if (msj.equals("C")) {
        return new ResponseTemplateGen<>(envase, 200, "Envase ingresado");
      } else {
        return new ResponseTemplateGen<>(envase, 300, "Envase existente");
      }
    } catch (BiosLISDAOException | IOException | SQLException ex) {
      Logger.getLogger(ConfiguracionEnvasesRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/envase/{idEnvase}")
  public ResponseTemplateGen<EnvaseDTO> getEnvaseById(@PathVariable("idEnvase") int idEnvase,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      EnvaseDTO envaseDTO = new EnvaseDTO();
      CfgEnvases envase = configuracionEnvasesService.getEnvaseById(idEnvase);
      envaseDTO.setCfgEnvases(envase);
      if (envase.getCenvImagenenvase() != null) {
        byte[] bdata = envase.getCenvImagenenvase().getBytes(1, (int) envase.getCenvImagenenvase().length());
        byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
        String base64Encoded = new String(encodeBase64, "UTF-8");
        envaseDTO.setBase64Img(base64Encoded);
      }
      return new ResponseTemplateGen<>(envaseDTO, 200, "Ok");
    } catch (BiosLISDAOException | SQLException | UnsupportedEncodingException ex) {
      Logger.getLogger(ConfiguracionEnvasesRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

}
