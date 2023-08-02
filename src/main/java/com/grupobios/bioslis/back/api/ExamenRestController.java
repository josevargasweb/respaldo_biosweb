/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.BiosLISBSException;
import com.grupobios.bioslis.bs.ExamenService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BioslisMailSender;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dto.ExamenFullDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ExamenPacienteDTO;
import com.grupobios.bioslis.dto.NotificaResultadosDTO;
import com.grupobios.bioslis.dto.TestRepetidosDTO;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;

/**
 *
 * @author eric.nicholls
 */

@RestController
public class ExamenRestController {

  private static Logger logger = LogManager.getLogger(ExamenRestController.class);

  @Autowired
  private BioslisMailSender bioslisMailS;

  public BioslisMailSender getBioslisMailS() {
    return bioslisMailS;
  }

  public void setBioslisMailS(BioslisMailSender bioslisMailS) {
    this.bioslisMailS = bioslisMailS;
  }

  DatosFichasDAO datosFichasDAO;

  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  @Autowired
  private ExamenService examenService;

  @GetMapping("/api/examen/list")
  public ResponseTemplateGen<List<DatosFichasexamenes>> getExamenes() throws BiosLISDAOException {

    return new ResponseTemplateGen<List<DatosFichasexamenes>>(examenService.getAll(), 200, "");
  }

  @PostMapping("/api/examen/update")
  public ResponseTemplateGen<String> updateExamen(@RequestBody DatosFichasexamenes dfe,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      examenService.updateExamen(dfe);
      return new ResponseTemplateGen<String>("Actualización correcta", 200, "Actualización correcta");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<String>("Error DAO Exception", 503, e.getMessage());
    }

  }

  @PostMapping("/api/examen/readytosign")
  public ResponseTemplateGen<Boolean> readyToSign(@RequestBody DatosFichasexamenes dfe,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      Boolean rpta = examenService.readyToSign(dfe.getIddatosFichasExamenes().getDfeIdexamen(),
          dfe.getIddatosFichasExamenes().getDfeNorden());
      if (rpta.booleanValue()) {
        return new ResponseTemplateGen<Boolean>(rpta, 200, "Se puede cambiar a firmado.");
      } else {
        return new ResponseTemplateGen<Boolean>(rpta, 404, "No se puede cambiar a firmado.");
      }
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<Boolean>(Boolean.valueOf(false), 503, e.getMessage());
    }

  }

  @PostMapping("/api/examen/list/action/{actionCode}")
  public ResponseTemplateGen<List<DatosFichasexamenes>> doActionOverList(@PathVariable("actionCode") String actionCode,
      @RequestBody List<ExamenOrdenDTO> listaExamenes, @Context HttpServletRequest req) throws BiosLISException {
    logger.debug("Parametros doAction: #lista {} - actionCode {}", listaExamenes.size(), actionCode);

    Long idUsuario = 0L;
    DatosUsuarios usuario = null;
    HttpSession sesion = req.getSession();

    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

    } else {
      return new ResponseTemplateGen<List<DatosFichasexamenes>>(null, 500, "Error: Sesión ha expirado");
    }

    List<DatosFichasexamenes> listaDFExamenes;
    try {
      listaDFExamenes = examenService.doAction(actionCode, listaExamenes, idUsuario);
      //NO BORRAR COMENTARIO DE CORREOS / SE TIENE QUE VOLVER A REVISAR EL REQUERIMIENTO -Jan
      /*String correoPaciente = datosFichasDAO.getCorreoxOrden(listaExamenes.get(0).getDFE_NORDEN());

      // n.setNotificationsSendTo(listaExamenes.get(0).getCS_EMAIL());
      
      if (correoPaciente != null) {
        NotificaResultadosDTO n = new NotificaResultadosDTO();
        n.setNotificationsSendTo(correoPaciente);
        n.setMessage(
            "Algunos de sus resultados ya se encuentran disponibles, ingresar a [Página web portal de pacientes] para visualizarlos");
        bioslisMailS.notificarExamenAutorizado(n);
      }*/
      return new ResponseTemplateGen<List<DatosFichasexamenes>>(listaDFExamenes, 200, "OK");
    } catch (BiosLISBSException | BiosLISDAONotFoundException | BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<DatosFichasexamenes>>(null, 500, e.getMessage());
    }
  }

  @GetMapping(value = "/api/examenes/{nOrden}/{idmuestra}")
  public ResponseEntity<ExamenPacienteDTO> obtenerExamenesPacientes(@PathVariable("nOrden") Long nOrden,
      @PathVariable("idmuestra") Long idMuestra, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return ResponseEntity.status(401).body(null);
    try {
      ExamenPacienteDTO curvaDTO = examenService.getExamenesPaciente(nOrden, idMuestra);
      return ResponseEntity.ok(curvaDTO);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ExamenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  /**
   * @return the examenService
   */
  public ExamenService getExamenService() {
    return examenService;
  }

  /**
   * @param examenService the examenService to set
   */
  public void setExamenService(ExamenService examenService) {
    this.examenService = examenService;
  }

  @PostMapping(value = "/api/examen/test/repetidos")
  public ResponseTemplateGen<List<TestRepetidosDTO>> getTestRepetidosExamenes(@RequestBody Integer[] lstExamenes,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<TestRepetidosDTO> lst;
    try {
      lst = examenService.getTestRepetidosExamenes(lstExamenes);
      return new ResponseTemplateGen<List<TestRepetidosDTO>>(lst, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getLocalizedMessage());
      return new ResponseTemplateGen<List<TestRepetidosDTO>>(null, 503, e.getMessage());
    }
  }

  @GetMapping(value = "/api/examen/{idExamen}/test/opcionales")
  public ResponseTemplateGen<List<CfgTest>> getTestOpcionalesExamenes(@PathVariable("idExamen") Long idExamen,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<CfgTest> lst;
    try {
      lst = examenService.getTestOpcionalesExamenes(idExamen);
      return new ResponseTemplateGen<List<CfgTest>>(lst, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getLocalizedMessage());
      return new ResponseTemplateGen<List<CfgTest>>(null, 503, e.getMessage());
    }
  }

  // Desarrollo Marco Caracciolo 21/09/2022
  @GetMapping("/api/examen/{idExamen}")
  public ResponseTemplateGen<ExamenFullDTO> getExamenById(@PathVariable("idExamen") long idExamen,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<CfgTest> lst;
    ExamenFullDTO exmnDTO;
    try {
      exmnDTO = examenService.getExamenById(idExamen);
      return new ResponseTemplateGen<>(exmnDTO, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ExamenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    } catch (BiosLISDAONotFoundException ex) {
      java.util.logging.Logger.getLogger(ExamenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 404, ex.getMessage());
    }
  }

  @PostMapping("/api/examen/filtro")
  public ResponseTemplateGen<List<CfgExamenes>> getExamenFiltro(@RequestBody HashMap<String, String> filtros,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<CfgTest> lst;
    List<CfgExamenes> lista;
    try {
      lista = examenService.getListaExamanesFiltro(filtros);
      return new ResponseTemplateGen<>(lista, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ExamenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/indicaciones/examenes")
  public ResponseTemplateGen<List<LdvIndicacionesexamenes>> getIndicacionesExamenes(
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<LdvIndicacionesexamenes> lista;
    try {
      lista = examenService.getIndicacionesExamenes();
      return new ResponseTemplateGen<>(lista, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ExamenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/examen/insert")
  public ResponseTemplateGen<String> insertExamen(@RequestBody ExamenFullDTO examenDTO,
      @Context HttpServletRequest context) throws IOException {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      examenService.insertExamen(examenDTO , usuario.getDuIdusuario());
      return new ResponseTemplateGen<>("", 200, "Se registró el Examen satisfactoriamente");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ExamenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>("", 500, ex.getMessage());
    }
  }

  @PutMapping("/api/examen/full/update")
  public ResponseTemplateGen<String> updateExamenDTO(@RequestBody ExamenFullDTO examenDTO,
      @Context HttpServletRequest context) throws IOException {

    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      examenService.updateExamenFull(examenDTO, usuario.getDuIdusuario());
      return new ResponseTemplateGen<>("", 200, "Se actualizó el Examen satisfactoriamente");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ExamenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>("", 500, ex.getMessage());
    }
  }

  @GetMapping("/api/examen/busquedacodigo/{codigoExamen}")
  public ResponseTemplateGen<List<CfgExamenes>> getExamenByCodigo(@PathVariable("codigoExamen") String codigoExamen,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<CfgExamenes> examenes = examenService.getExamenByCodigo(codigoExamen);
      if (!examenes.isEmpty()) {
        return new ResponseTemplateGen<>(examenes, 200, "Código de examen ya existe");
      }
      return new ResponseTemplateGen<>(examenes, 404, "");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ExamenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/examen/getParaExcel")
  public ResponseTemplateGen<List<ExamenOrdenDTO>> getExamenesParaExcel(@Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<ExamenOrdenDTO> lst;
    List<CfgExamenes> lista;
    try {
      lst = examenService.getListExamenesExcel();
      return new ResponseTemplateGen<>(lst, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(ExamenRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }
  
  @GetMapping("/api/examenes/seccion/{idSeccion}")
  public ResponseTemplateGen<List<CfgExamenes>> getExamenesBySeccion(@PathVariable("idSeccion") int idSeccion, @Context HttpServletRequest context){
	  HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      /*if (usuario == null)
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");*/
      List<CfgExamenes> list;
	try {
		list = examenService.getExamenesBySeccion(idSeccion);
		return new ResponseTemplateGen<List<CfgExamenes>>(list, 200, "Ok");
	} catch (BiosLISDAOException ex) {
		// TODO Auto-generated catch block
		ex.printStackTrace();
		return new ResponseTemplateGen<>(null, 500, ex.getMessage());
	}
  }
}
