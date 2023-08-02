/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.math.BigDecimal;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.PacienteService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.AntecedentePacienteDTO;
import com.grupobios.bioslis.dto.DatosPacienteDTO;
import com.grupobios.bioslis.dto.ExamenValidoDTO;
import com.grupobios.bioslis.dto.LogPacientesDTO;
import com.grupobios.bioslis.dto.ParamBusquedaPorNombre;
import com.grupobios.bioslis.dto.PatologiaDTO;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author eric.nicholls
 */
@RestController()
public class PacienteRestController {

  private static Logger logger = LogManager.getLogger(InformeResultadosRestController.class);

  @Autowired
  PacienteService pacienteService;

  @GetMapping("/api/paciente/{idPaciente}/patologias")
  public ResponseEntity<List<PatologiaDTO>> getPatologias(@PathVariable("idPaciente") Long idPaciente)
      throws BiosLISDAOException {

    List<PatologiaDTO> lstPatologiasPaciente = pacienteService.getPatologias(idPaciente);
    lstPatologiasPaciente.forEach(p -> {
      logger.debug(p.getLDVPAT_DESCRIPCION());
    });
    return ResponseEntity.ok(lstPatologiasPaciente);
  }

  @GetMapping("/api/paciente/{rutPaciente}")
  public ResponseTemplateGen<DatosPacienteDTO> getPacienteByRut(@PathVariable("rutPaciente") String rutPaciente,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      DatosPacienteDTO dp = pacienteService.getPacienteByRut(rutPaciente.toUpperCase());
      return new ResponseTemplateGen<>(dp, 200, "Paciente encontrado");
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<>(null, 404, "Paciente no encontrado");
    }
  }

  @GetMapping("/api/paciente/ordenes/rut/{rutPaciente}")
  public ResponseTemplateGen<DatosPacienteDTO> getPacienteOrdenesByRut(@PathVariable("rutPaciente") String rutPaciente,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      DatosPacienteDTO dp = pacienteService.getPacienteOrdenesByRut(rutPaciente.toUpperCase());
      if (dp.getListaOrdenesDelDia() != null && dp.getListaOrdenesDelDia().size() > 0) {
        return new ResponseTemplateGen<>(dp, 201, "Paciente encontrado");
      } else {
        return new ResponseTemplateGen<>(dp, 200, "Paciente encontrado");
      }
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<>(null, 404, "Paciente no encontrado");
    }
  }

  public PacienteService getPacienteService() {
    return pacienteService;
  }

  public void setPacienteService(PacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  @PostMapping(value = "/api/paciente/antecedentes/{nOrden}")
  public ResponseTemplateGen<List<AntecedentePacienteDTO>> ModalAntecedentes(@PathVariable("nOrden") Long nOrden,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<AntecedentePacienteDTO> lstAntecedentes;
    try {
      lstAntecedentes = pacienteService.getAntecedentesPacienteOrden(BigDecimal.valueOf(nOrden));
      if (!lstAntecedentes.isEmpty()) {
        return new ResponseTemplateGen<List<AntecedentePacienteDTO>>(lstAntecedentes, 200, "Antecedentes encontrados");
      } else {
        return new ResponseTemplateGen<List<AntecedentePacienteDTO>>(lstAntecedentes, 404, "Sin antecedentes");
      }
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<List<AntecedentePacienteDTO>>(null, 500, e.getMessage());
    }

  }

  @PostMapping(value = "/api/antecedentes/{nOrden}")
  public ResponseTemplateGen<List<AntecedentePacienteDTO>> modalAntecedentesPac(@PathVariable("nOrden") Long nOrden,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<AntecedentePacienteDTO> listAntecedentes;
    try {
      listAntecedentes = pacienteService.getAntecedentesPaciente(nOrden);
      if (!listAntecedentes.isEmpty()) {
        return new ResponseTemplateGen<>(listAntecedentes, 200, "Antecedentes encontrados");
      } else {
        return new ResponseTemplateGen<>(listAntecedentes, 404, "Sin antecedentes");
      }
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<>(null, 500, "Error al recuperar antecednte de paciente");
    }
  }

  @PostMapping(value = "/api/antecedentes/save")

  public ResponseTemplateGen<String> guardarAntecedentes(@RequestBody List<AntecedentePacienteDTO> listAntecedentes,  @Context HttpServletRequest context) {
	  HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      Long idUsuario = 0L;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
      idUsuario = usuario.getDuIdusuario();
      try {
          logger.debug("Lista guardarAntecedentes {}", listAntecedentes.size());
          String paciente = null;
          pacienteService.guardarAntecedentes(listAntecedentes, idUsuario);
          return new ResponseTemplateGen<>(paciente, 200, "Ok");
      } catch (BiosLISDAOException ex) {
          java.util.logging.Logger.getLogger(PacienteRestController.class.getName()).log(Level.SEVERE, null, ex);
          return new ResponseTemplateGen<>(null, 500, ex.getMessage());
      }

  }

  @PostMapping(value = "/api/paciente/rn/{idPaciente}")
  public ResponseTemplateGen<DatosPacienteDTO> getDataRN(@PathVariable("idPaciente") Long idPaciente,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    DatosPacienteDTO paciente = null;
    try {
      paciente = pacienteService.getDataRN(BigDecimal.valueOf(idPaciente));
    } catch (ParseException e) {
      return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 505, e.getMessage());
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 505, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 404, e.getMessage());
    }
    return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 200, "Ok");
  }

  @PostMapping(value = "/api/paciente/actualiza")
  public ResponseTemplateGen<String> actualizaPaciente(@RequestBody DatosPacienteDTO dp,
      @Context HttpServletRequest context) {

    String paciente = null;
    try {
      HttpSession sesion = (HttpSession) context.getSession();
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");

      idUsuario = usuario.getDuIdusuario();
      if (dp.getDp().getDpDireccion() != null) {
        dp.getDp().setDpDireccion(dp.getDp().getDpDireccion().toUpperCase());
        // dp.setDp();
      }
      logger.debug(dp.getDp().getDpFnacimiento());
      pacienteService.udpatePaciente(dp, dp.getDc(), idUsuario);
    } catch (ParseException e) {
      return new ResponseTemplateGen<String>(paciente, 505, e.getMessage());
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<String>(paciente, 505, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<String>(paciente, 404, e.getMessage());
    } catch (BiosLISException e) {
      return new ResponseTemplateGen<String>(paciente, 506, e.getMessage());
    }
    return new ResponseTemplateGen<String>(paciente, 200, "Ok");
  }

  @PostMapping(value = "/api/paciente/{idPaciente}")
  public ResponseTemplateGen<DatosPacienteDTO> getDataById(@PathVariable("idPaciente") Long idPaciente,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    DatosPacienteDTO paciente = null;
    try {
      paciente = pacienteService.getDataById(BigDecimal.valueOf(idPaciente));
    } catch (ParseException e) {
      return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 505, e.getMessage());
    } catch (BiosLISDAOException e) {
      return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 505, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 404, e.getMessage());
    }
    return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 200, "Ok");
  }

  @PostMapping(value = "/api/paciente/pasaporte/{pasaportePaciente}")
  public ResponseTemplateGen<DatosPacienteDTO> getDataByPasaporte(
      @PathVariable("pasaportePaciente") String pasaportePaciente, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    DatosPacienteDTO paciente = null;
    try {
      logger.debug("Se busca pasaporte {}.", pasaportePaciente);
      paciente = pacienteService.getPacienteDTOByPasaporte(pasaportePaciente);
    } catch (BiosLISDAOException | ParseException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 505, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 4, e.getMessage());
    }
    return new ResponseTemplateGen<DatosPacienteDTO>(paciente, 200, "Ok");
  }

  // Rest creado por Marco Caracciolo el 13/05/2022
  @GetMapping(value = "/api/paciente/orden/{nOrden}")
  public ResponseTemplateGen<DatosPacienteDTO> getPacienteByOrden(@PathVariable("nOrden") Long nOrden,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    DatosPacienteDTO paciente = pacienteService.getPacienteDTOByOrden(nOrden);
    return new ResponseTemplateGen<>(paciente, 200, "Ok");
  }

  @PostMapping(value = "/api/paciente/tipoDoc/{tipoDoc}")
  public ResponseTemplateGen<List<DatosPacientes>> getPacienteByNombreApellidoTipoDoc(
      @PathVariable("tipoDoc") Integer tipodoc, @RequestBody ParamBusquedaPorNombre parNombres,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<DatosPacientes> pacientes = null;
    try {
      logger.debug(
          "Se busca paciente {} {} {} {} parNombres.getNombrePaciente(), parNombres.getApellidoPaciente(), parNombres.getSegundoApellidoPaciente(), tipodoc.",
          parNombres.getNombrePaciente(), parNombres.getApellidoPaciente(), parNombres.getSegundoApellidoPaciente(),
          tipodoc);
      pacientes = pacienteService.getPacienteByNombreApellidoTipoDoc(parNombres.getNombrePaciente(),
          parNombres.getApellidoPaciente(), parNombres.getSegundoApellidoPaciente(), tipodoc);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<DatosPacientes>>(pacientes, 505, e.getMessage());
    }
    return new ResponseTemplateGen<List<DatosPacientes>>(pacientes, 200, "Ok");
  }

  @GetMapping("/api/paciente/{idpac}/examenes/validos")
  public ResponseTemplateGen<List<ExamenValidoDTO>> getExamenesValidosDeUnPaciente(@PathVariable("idpac") Integer idPac,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<ExamenValidoDTO> lst = pacienteService.getExamenesValidosDeUnPaciente(idPac);
      return new ResponseTemplateGen<List<ExamenValidoDTO>>(lst, 200, "Ok");
    } catch (BiosLISDAOException e) {
      logger.error("No se pudo obtener ordenes {}", e.getMessage());
    }
    return new ResponseTemplateGen<List<ExamenValidoDTO>>(null, 200, "Ok");

  }

  @GetMapping("/api/paciente/ordenes/pasaporte/{pasaporte}")
  public ResponseTemplateGen<DatosPacienteDTO> getPacienteOrdenesByPasaporte(
      @PathVariable("pasaporte") String pasaporte, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      DatosPacienteDTO dp = pacienteService.getPacienteOrdenesByPasaporte(pasaporte.toUpperCase());
      if (dp.getListaOrdenesDelDia() != null && dp.getListaOrdenesDelDia().size() > 0) {
        return new ResponseTemplateGen<>(dp, 201, "Paciente encontrado");
      } else {
        return new ResponseTemplateGen<>(dp, 200, "Paciente encontrado");
      }
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 500, e.getMessage());
    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 400, e.getMessage());
    }
  }

  @GetMapping("/api/paciente/eventos/{idPaciente}")
  public ResponseTemplateGen<List<LogPacientesDTO>> getEventosPaciente(@PathVariable("idPaciente") Long idPaciente,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    if (usuario == null)
      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    try {
      List<LogPacientesDTO> listaEventosPaciente = pacienteService.getEventosPaciente(idPaciente);

      return new ResponseTemplateGen<List<LogPacientesDTO>>(listaEventosPaciente, 200, "eventos encontrado");

    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<LogPacientesDTO>>(null, 500, e.getMessage());

    }
  }

}
