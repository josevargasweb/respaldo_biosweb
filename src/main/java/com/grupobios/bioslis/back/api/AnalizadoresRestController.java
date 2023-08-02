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
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupobios.bioslis.bs.AnalizadoresService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.AnalizadorDTO;
import com.grupobios.bioslis.entity.CfgSigmaimagenes;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.SigmaAnalizadores;
import com.grupobios.common.Resizer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class AnalizadoresRestController {

  private static Logger logger = LogManager.getLogger(AnalizadoresRestController.class);

  @Autowired
  AnalizadoresService analizadoresService;

  @PostMapping("/api/analizadores/filtro")
  public ResponseTemplateGen<List<SigmaAnalizadores>> getAnalizadoresFiltro(
      @RequestBody HashMap<String, String> filtros) {
    try {
      List<SigmaAnalizadores> lista = analizadoresService.getAnalizadoresFiltro(filtros);
      return new ResponseTemplateGen<>(lista, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(AnalizadoresRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/analizador/{idAnalizador}")
  public ResponseTemplateGen<AnalizadorDTO> getAnalizadorById(@PathVariable("idAnalizador") short idAnalizador) {
    try {
      AnalizadorDTO analizador = analizadoresService.getAnalizadorById(idAnalizador);
      return new ResponseTemplateGen<>(analizador, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(AnalizadoresRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/analizador/insert")
  public ResponseTemplateGen<SigmaAnalizadores> insertAnalizador(@RequestParam("dataAnalizador") String dataAnalizador,
      @RequestParam("imagen") MultipartFile imagen, @Context HttpServletRequest context) {
    try {
      HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      ObjectMapper om = new ObjectMapper();
      SigmaAnalizadores sa = om.readValue(dataAnalizador, SigmaAnalizadores.class);
      if (!imagen.isEmpty()) {
        byte[] imageBytes = imagen.getBytes();
        Resizer resizer = new Resizer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage buffImg = resizer.genResizedImage(imageBytes, 640, 640);

        ImageIO.write(buffImg, "jpg", baos);

        Blob imageBlob = new SerialBlob(baos.toByteArray());

        CfgSigmaimagenes sigmaImagen = new CfgSigmaimagenes();
        sigmaImagen.setCsiImagen(imageBlob);
        analizadoresService.insertSigmaImagen(sigmaImagen);

        sa.setSaIdimagen(sigmaImagen.getCsiIdimagen());
      }
      String msj = analizadoresService.insertAnalizador(sa, usuario.getDuIdusuario());
      if (msj.equals("C")) {
        return new ResponseTemplateGen<>(sa, 200, "Se registró el analizador satisfactoriamente");
      } else {
        return new ResponseTemplateGen<>(sa, 300, "Analizador ya existente");
      }

    } catch (BiosLISDAOException | IOException | SQLException ex) {
      java.util.logging.Logger.getLogger(AnalizadoresRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/analizador/update")
  public ResponseTemplateGen<SigmaAnalizadores> updateAnalizador(@RequestParam("dataAnalizador") String dataAnalizador,
      @RequestParam("imagen") MultipartFile imagen, @Context HttpServletRequest context) {

    try {
      HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");

      ObjectMapper om = new ObjectMapper();
      SigmaAnalizadores sa = om.readValue(dataAnalizador, SigmaAnalizadores.class);
      AnalizadorDTO aDTO = analizadoresService.getAnalizadorById(sa.getSaIdanalizador());
      if (!imagen.isEmpty()) {
        byte[] imageBytes = imagen.getBytes();
        Resizer resizer = new Resizer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage buffImg = resizer.genResizedImage(imageBytes, 640, 640);
        ImageIO.write(buffImg, "jpg", baos);
        Blob imageBlob = new SerialBlob(baos.toByteArray());
        if (aDTO.getSigmaAnalizadores().getSaIdimagen() != null) {
          CfgSigmaimagenes sigmaImagen = analizadoresService.getImagenById(aDTO.getSigmaAnalizadores().getSaIdimagen());
          sigmaImagen.setCsiImagen(imageBlob);
          analizadoresService.updateSigmaImagen(sigmaImagen, usuario.getDuIdusuario(),
              (long) aDTO.getSigmaAnalizadores().getSaIdanalizador(), sa.getSaDescripcion());
          sa.setSaIdimagen(sigmaImagen.getCsiIdimagen());
        } else {
          CfgSigmaimagenes newImagen = new CfgSigmaimagenes();
          newImagen.setCsiImagen(imageBlob);
          analizadoresService.insertSigmaImagen(newImagen);
          sa.setSaIdimagen(newImagen.getCsiIdimagen());
          // esto se realiza para agregar el update de la primera vez que se agrega imagen
          // a log
          analizadoresService.updateSigmaImagen(newImagen, usuario.getDuIdusuario(),
              (long) aDTO.getSigmaAnalizadores().getSaIdanalizador(), sa.getSaDescripcion());
        }
      } else {
        if ("sinFoto.txt".equals(imagen.getOriginalFilename())) {
          sa.setSaIdimagen(null);
        }
        if ("conservarFoto.txt".equals(imagen.getOriginalFilename())) {
          sa.setSaIdimagen(aDTO.getSigmaAnalizadores().getSaIdimagen());
        }
      }
      String msj = analizadoresService.updateAnalizador(sa, usuario.getDuIdusuario());
      if (msj.equals("C")) {
        return new ResponseTemplateGen<>(sa, 200, "Se Actualizo el analizador satisfactoriamente");
      } else {
        return new ResponseTemplateGen<>(sa, 300, "Analizador ya existente");
      }
    } catch (BiosLISDAOException | IOException | SQLException ex) {
      java.util.logging.Logger.getLogger(AnalizadoresRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  public AnalizadoresService getAnalizadoresService() {
    return analizadoresService;
  }

  public void setAnalizadoresService(AnalizadoresService analizadoresService) {
    this.analizadoresService = analizadoresService;
  }

}
