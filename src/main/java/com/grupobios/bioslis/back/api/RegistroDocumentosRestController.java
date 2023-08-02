/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grupobios.bioslis.bs.RegistroDocumentosService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.DocumentosOrdenDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class RegistroDocumentosRestController {

  RegistroDocumentosService registroDocumentosService;
  private static Logger logger = LogManager.getLogger(RegistroDocumentosRestController.class);

//  @PostMapping("/api/documento/save/{nOrden}/{tipoDocumentoAnexo}")
//  public ResponseTemplateGen<String> guardarDocumento(@PathVariable("nOrden") int nOrden,
//      @PathVariable("tipoDocumentoAnexo") byte tipoDocumentoAnexo, @RequestParam("documento") MultipartFile documento,
//      @RequestParam(defaultValue = "-1") String modulo, @Context HttpServletRequest context) {
//
//    HttpSession sesion = (HttpSession) context.getSession();
//    DatosUsuarios usuario = new DatosUsuarios();
//    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
//
//    logger.debug("nro orden: " + nOrden);
//    logger.debug("tipo documento anexo: " + tipoDocumentoAnexo);
//    try {
//      byte[] documentBytes = documento.getBytes();
//      Blob docuBlob = new SerialBlob(documentBytes);
//      registroDocumentosService.guardarDocumento(nOrden, tipoDocumentoAnexo, docuBlob);
//
//      // aqui se inserta la accion que realiza usuario en modulos ***********
//      if (usuario != null && modulo.equals("1")) {
//        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
//        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
//        leu.setLeuIdusuario((int) usuario.getDuIdusuario());
//        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
//        leu.setLeuIdevento(12);
//        leu.setLeuIdacciondato(1);
//        leu.setLeuNombretabla("DATOS_FICHASDOCUMENTOS");
//        leu.setLeuNombrecampo("DF_NORDEN");
//        leu.setLeuValornuevo("Se adjunta documento a la Orden: " + nOrden);
//        logUsuarioDao.insertLogEventosUsuario(leu);
//      }
//      if (usuario != null && modulo.equals("2")) {
//
//        LdvTiposdocumentosanexosDAO tiposdocumentosanexosDAO = new LdvTiposdocumentosanexosDAO();
//        LdvTiposdocumentosanexos tda = tiposdocumentosanexosDAO.getTiposDocumentosAnexos(tipoDocumentoAnexo);
//
//        LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
//        LogEventosfichas lef = new LogEventosfichas();
//        DatosFichasDAO dfDao = new DatosFichasDAO();
//        DatosFichas df = dfDao.getOrdenxID(nOrden);
//        lef.setDatosFichas(nOrden);
//        lef.setLefFechaorden(df.getDfFechaorden());
//        lef.setLefIdpaciente(df.getDatosPacientes());
//        lef.setLefNombretabla("DATOS_FICHASDOCUMENTOS");
//        lef.setLefNombrecampo("DFD_IDTIPODOCUMENTOANEXO");
//        lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
//        lef.setLefIdusuario((int) usuario.getDuIdusuario());
//        lef.setLefValornuevo(tda.getLdvtdaDescripcion());
//        lef.setCfgEventos(2);
//        logEventosfichasDAO.insertLogEventosFichas(lef);
//      }
//      // *-************************************************************
//
//      return new ResponseTemplateGen<>("", 200, "OK");
//    } catch (SQLException | IOException | BiosLISDAOException ex) {
//      logger.error(ex.getMessage());
//      return new ResponseTemplateGen<>("", 500, "Error: " + ex.getMessage());
//    }
//  }

  @GetMapping("/api/documentos/orden/{nOrden}")
  public ResponseTemplateGen<List<DocumentosOrdenDTO>> getDocumentosOrden(@PathVariable("nOrden") int nOrden) {
    try {
      List<DocumentosOrdenDTO> lstDocumentosOrden = registroDocumentosService.getDocumentosOrden(nOrden);
      if (lstDocumentosOrden.isEmpty()) {
        return new ResponseTemplateGen<>(null, 404, "No se encontraron documentos para esta orden");
      }
      return new ResponseTemplateGen<>(lstDocumentosOrden, 200, "OK");
    } catch (BiosLISDAOException | SQLException | UnsupportedEncodingException ex) {
      return new ResponseTemplateGen<>(null, 500, "Error: " + ex.getMessage());
    }
  }

  @PostMapping("/api/documento/borrar/{nOrden}/{tipoDocumentoAnexo}")
  public ResponseTemplateGen<String> borrarDocumentoOrden(@PathVariable("nOrden") int nOrden,
      @PathVariable("tipoDocumentoAnexo") byte tipoDocumentoAnexo) {
    try {
      registroDocumentosService.guardarDocumento(nOrden, tipoDocumentoAnexo, null);
      return new ResponseTemplateGen<>("Documento eliminado", 200, "OK");
    } catch (BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<>(null, 500, "Error: " + ex.getMessage());
    }
  }

  // AGREGADO X JAN
  @GetMapping("/api/documentos/orden/{nOrden}/examen/{idExamen}")
  public ResponseTemplateGen<List<DocumentosOrdenDTO>> getDocumentosExamenOrden(@PathVariable("nOrden") Integer nOrden,
      @PathVariable("idExamen") Integer idExamen) {
    try {
      List<DocumentosOrdenDTO> lstDocumentosOrden = registroDocumentosService.getDocumentosExamenOrden(nOrden,
          idExamen);
      return new ResponseTemplateGen<>(lstDocumentosOrden, 200, "OK");
    } catch (SQLException | UnsupportedEncodingException | BiosLISDAOException ex) {
      return new ResponseTemplateGen<>(null, 500, "Error: " + ex.getMessage());
    }
  }

  @PostMapping("/api/documento/borrar/nOrden/{nOrden}/idExamen/{idExamen}/tipoDocumento/{tipoDocumentoAnexo}")
  public ResponseTemplateGen<String> eliminarArchivoXexamen(@PathVariable("nOrden") int nOrden,
      @PathVariable("idExamen") int idExamen, @PathVariable("tipoDocumentoAnexo") byte tipoDocumentoAnexo) {
    try {
      registroDocumentosService.eliminarArchivoxExamen(nOrden, idExamen, tipoDocumentoAnexo);
      return new ResponseTemplateGen<String>("Documento eliminado", 200, "OK");
    } catch (BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<String>(null, 500, "Error: " + ex.getMessage());
    }
  }
  //

  public RegistroDocumentosService getRegistroDocumentosService() {
    return registroDocumentosService;
  }

  public void setRegistroDocumentosService(RegistroDocumentosService registroDocumentosService) {
    this.registroDocumentosService = registroDocumentosService;
  }

  @PostMapping("/api/documento/orden/{nOrden}/examen/{idExamen}/save")
  public ResponseTemplateGen<String> guardarDocumento(@PathVariable("nOrden") Long nOrden,
      @PathVariable("idExamen") Long idExamen, @RequestParam("documento") MultipartFile documento) {

    try {
      byte[] documentBytes = documento.getBytes();
      Blob docuBlob = new SerialBlob(documentBytes);
      if (docuBlob != null) {
        logger.debug("Tamaño archivo upload {}", docuBlob.length());
      } else {
        logger.debug("************************************");
      }

      registroDocumentosService.guardarDocumentoNew(nOrden, idExamen, (byte) 1, docuBlob);
      return new ResponseTemplateGen<>("", 200, "OK");
    } catch (SQLException | IOException | BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<>("", 500, "Error: " + ex.getMessage());
    }
  }

  @PostMapping("/api/documento/save/{nOrden}/{tipoDocumentoAnexo}")
  public ResponseTemplateGen<String> guardarDocumento2(@PathVariable("nOrden") int nOrden,
      @PathVariable("tipoDocumentoAnexo") byte tipoDocumentoAnexo, @RequestParam("documento") MultipartFile documento,
      @Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");

    logger.debug("nro orden: " + nOrden);
    logger.debug("tipo documento anexo: " + tipoDocumentoAnexo);
    try {
      byte[] documentBytes = documento.getBytes();
      Blob docuBlob = new SerialBlob(documentBytes);
      registroDocumentosService.guardarDocumento(nOrden, tipoDocumentoAnexo, docuBlob);

      // aqui se inserta la accion que realiza usuario en modulos ***********
      // *-************************************************************

      return new ResponseTemplateGen<>("", 200, "OK");
    } catch (SQLException | IOException | BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<>("", 500, "Error: " + ex.getMessage());
    }
  }

}
