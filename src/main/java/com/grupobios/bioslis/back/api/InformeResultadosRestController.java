/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.BiosLISBSException;
import com.grupobios.bioslis.bs.BiosLISJasperException;
import com.grupobios.bioslis.bs.InformeResultadosService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.OrdenExamenCapturaResultadoDTO;
import com.grupobios.bioslis.dto.SendMailDTO;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author eric.nicholls
 */

@RestController()
public class InformeResultadosRestController {

  @Autowired
  private InformeResultadosService informeResultadosService;

  private static Logger logger = LogManager.getLogger(InformeResultadosRestController.class);

  @PostMapping("/api/ordenesold")
  public ResponseEntity<List<DatosFichas>> getOrdenes() throws BiosLISDAOException {

    List<DatosFichas> lstOrdenes = informeResultadosService.getOrdenes();
    if (lstOrdenes.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(lstOrdenes);
  }

  @GetMapping("/api/orden/{nroOrden}/examenes")
  public ResponseEntity<List<ExamenOrdenDTO>> getExamenesOrden(@PathVariable("nroOrden") Long nroOrden) {

    logger.debug("Nro de orden buscada   {}", nroOrden);
    List<ExamenOrdenDTO> lstExamenOrden = informeResultadosService.getExamenesOrden(nroOrden);
    lstExamenOrden.removeIf(examen -> (examen.getDFE_EXAMENANULADO().equals("")));

    return ResponseEntity.ok(lstExamenOrden);
  }

  @PostMapping("/api/orden/{nroOrden}/mail")
  public ResponseEntity<List<ExamenOrdenDTO>> sendExamenes(@PathVariable("nroOrden") Long nroOrden,
      @RequestBody SendMailDTO sendMailDto, @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");

    try {
      logger.debug("Lista de examens {}", sendMailDto.getLstIdExamenes().length);
      List<ExamenOrdenDTO> lstExamenOrden = informeResultadosService.getExamenesOrden(nroOrden);
      if (lstExamenOrden.isEmpty()) {
        return ResponseEntity.notFound().build();
      }
      informeResultadosService.sendExamenes(nroOrden, sendMailDto);
      return ResponseEntity.ok(lstExamenOrden);
    } catch (BiosLISJasperException ex) {
      logger.error(ex.getMessage());
    } catch (BiosLISException ex) {
      logger.error(ex.getMessage());
    }
    return null;
  }

  @PostMapping("/api/orden/{nroOrden}/imprimirInformes")
  public ResponseEntity<List<ExamenOrdenDTO>> imprimirInformes(@PathVariable("nroOrden") Long nroOrden,
      @RequestBody SendMailDTO sendMailDto, @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");

    try {
      logger.debug("Lista de examens {}", sendMailDto.getLstIdExamenes().length);
      List<ExamenOrdenDTO> lstExamenOrden = informeResultadosService.getExamenesOrden(nroOrden);
      if (lstExamenOrden.isEmpty()) {
        return ResponseEntity.notFound().build();
      }
      informeResultadosService.imprimirExamenes(nroOrden, sendMailDto);
      return ResponseEntity.ok(lstExamenOrden);
    } catch (BiosLISJasperException ex) {
      logger.error(ex.getMessage());
    } catch (BiosLISException ex) {
      logger.error(ex.getMessage());
    }
    return null;
  }

  @PostMapping("/api/orden/{nroOrden}/portalResultado")
  public ResponseEntity<List<ExamenOrdenDTO>> imprimirInformesPR(@PathVariable("nroOrden") Long nroOrden,
      @RequestBody SendMailDTO sendMailDto) throws BiosLISDAOException {

    try {
      logger.debug("Lista de examens {}", sendMailDto.getLstIdExamenes().length);
      List<ExamenOrdenDTO> lstExamenOrden = informeResultadosService.getExamenesOrden(nroOrden);
      if (lstExamenOrden.isEmpty()) {
        return ResponseEntity.notFound().build();
      }
      informeResultadosService.imprimirExamenes(nroOrden, sendMailDto);
      return ResponseEntity.ok(lstExamenOrden);
    } catch (BiosLISJasperException ex) {
      logger.error(ex.getMessage());
    } catch (BiosLISException ex) {
      logger.error(ex.getMessage());
    }
    return null;
  }

  public InformeResultadosService getInformeResultadosService() {
    return informeResultadosService;
  }

  public void setInformeResultadosService(InformeResultadosService informeResultadosService) {
    this.informeResultadosService = informeResultadosService;
  }

  // agregado x jan
  @PostMapping("/api/informeResultado/update")
  public ResponseTemplateGen<HashMap<String, Object>> updateDatosFichasExamenes(
      @RequestBody OrdenExamenCapturaResultadoDTO MOD, @Context HttpServletRequest context)
      throws BiosLISDAOException, ParseException, BiosLISBSException, BiosLISJasperException, BiosLISException {
    HashMap<String, Object> MicroExam = null;
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    if (usuario != null)
      MOD.setDFE_IDUSUARIOIMPRIME(new BigDecimal(usuario.getDuIdusuario()));

    try {

      MicroExam = (HashMap<String, Object>) informeResultadosService.updateInformeResultado(MOD);
    } catch (BiosLISDAOException e) {
      // log.error(e.getMessage());
      return new ResponseTemplateGen<HashMap<String, Object>>(null, 404, e.getMessage());
    }

    return new ResponseTemplateGen<HashMap<String, Object>>(MicroExam, 200, "OK");
  }

  @GetMapping("/api/orden/{nroOrden}/examenes/{idExamenes}")
  public ResponseTemplateGen<ExamenOrdenDTO> getOrdenesxExamenes(@PathVariable("nroOrden") Long nroOrden,
      @PathVariable("idExamenes") Long idExamenes, @Context HttpServletRequest context) throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    logger.debug("Nro de orden buscada   {}", nroOrden);
    ExamenOrdenDTO lstExamenOrden = informeResultadosService.getOrdenesxExamen(nroOrden, idExamenes);
    // lstExamenOrden.removeIf(examen ->
    // (examen.getDFE_EXAMENANULADO().equals("")));

    return new ResponseTemplateGen<ExamenOrdenDTO>(lstExamenOrden, 200, "OK");
  }

}

/*
 * 
 * try { resList = docHelper.getDocClasified(filter); return
 * ResponseEntity.ok(resList);
 * 
 * } catch (DocListNotFoundException e) { logger.error(e.getMessage()); return
 * ResponseEntity.status(HttpStatus.NOT_FOUND).
 * body("No se encontaron documentos."); }
 */
