/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.bs.PacienteService;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.DatosPacientesHelper;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.CfgComunasregionesDAO;
import com.grupobios.bioslis.dao.DatosContactosDAO;
import com.grupobios.bioslis.dao.DatosPacientesDAO;
import com.grupobios.bioslis.dao.LdvComunasDAO;
import com.grupobios.bioslis.dao.LdvContactospacientesrelacionDAO;
import com.grupobios.bioslis.dao.LdvEstadoscivilesDAO;
import com.grupobios.bioslis.dao.LdvPaisesDAO;
import com.grupobios.bioslis.dao.LdvPatologiasDAO;
import com.grupobios.bioslis.dao.LdvRegionesDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.DatosPacienteDTO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgComunasregiones;
import com.grupobios.bioslis.entity.DatosContactos;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvComunas;
import com.grupobios.bioslis.entity.LdvEstadosciviles;
import com.grupobios.bioslis.entity.LdvPaises;
import com.grupobios.bioslis.entity.LdvRegiones;
import com.grupobios.bioslis.entity.LdvTiposdocumentos;
import com.grupobios.common.DateFormatterHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

/**
 *
 * @author Nacho
 */
@Controller
public class RegistroPacienteController {

  private static final Logger logger = LogManager.getLogger(RegistroPacienteController.class);

  @Autowired
  private PacienteService pacienteService;

  public static final Integer LDVTD_RUN = 1;
  public static final Integer LDVTD_PASAPORTE = 2;
  public static final Integer LDVTD_RECIENNACIDO = 4;
  public static final Integer LDVTD_SINIDENTIFICACION = 5;

  ModelAndView mav = new ModelAndView();

  public PacienteService getPacienteService() {
    return pacienteService;
  }

  public void setPacienteService(PacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  @RequestMapping(value = "/RegistroPaciente")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, 2);
    if (sesion.getAttribute("usuario") != null) {

      Enumeration<String> namesList = request.getParameterNames();

      while (namesList.hasMoreElements()) {
        logger.debug("", namesList.nextElement());
      }

      request.getParameter("esPartoMultiple");
      LdvTiposdocumentosDAO tiposDocumentosDAO = new LdvTiposdocumentosDAO();
      LdvSexoDAO sexoDAO = new LdvSexoDAO();
      LdvRegionesDAO regionesDAO = new LdvRegionesDAO();
      LdvComunasDAO comunasDAO = new LdvComunasDAO();
      LdvPaisesDAO paisesDAO = new LdvPaisesDAO();
      LdvEstadoscivilesDAO estadosCivilesDAO = new LdvEstadoscivilesDAO();
      LdvPatologiasDAO patologiasDAO = new LdvPatologiasDAO();
      LdvContactospacientesrelacionDAO pacRelDAO = new LdvContactospacientesrelacionDAO();
      mav.addObject("listaTiposDocumentos", tiposDocumentosDAO.getTiposDocumentos());
      mav.addObject("listaSexo", sexoDAO.getSexo());
      mav.addObject("listaRegiones", regionesDAO.getRegiones());
      mav.addObject("listaComunas", comunasDAO.getComunas());
      mav.addObject("listaPaises", paisesDAO.getPaises());
      mav.addObject("listaEstadosCiviles", estadosCivilesDAO.getEstadosCiviles());
      mav.addObject("listaPatologias", patologiasDAO.getPatologias());
      mav.addObject("listaPacienteRelacion", pacRelDAO.getContactosRelacion());
    }

    if (usuario != null) {
      // aqui se inserta la accion que realiza usuario en modulos ***********
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();

      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(11);
      leu.setLeuIdacciondato(0);
      leu.setLeuNombretabla("");
      leu.setLeuNombrecampo("");
      leu.setLeuValornuevo("accede a Registro de pacientes");
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-**************************************************

    return mav;
  }

  @RequestMapping(value = "/RegistroPaciente", method = RequestMethod.POST)
  public ModelAndView registroPaciente(DatosPacientes dp, DatosContactos dc, LdvTiposdocumentos tipoDocumento,
      LdvEstadosciviles estadoCivil, LdvPaises pais, LdvRegiones region, LdvComunas comuna, HttpServletRequest request,
      @Context HttpServletRequest context) throws ParseException {
    String mensaje = null;
    HttpSession sesion = context.getSession();

    DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    Long idUsuario = usuario.getDuIdusuario();
    try {

      String esPartoMultiple = request.getParameter("esPartoMultiple");
      String fechaNacimiento = request.getParameter("dpFnacimiento");
      String[] patologias = request.getParameterValues("liPatologias");
      String[] observacionesPatologias = request.getParameterValues("observacionesPatologias");
      String rutMadre = request.getParameter("rutMadre");
      String exitus = request.getParameter("exitus");

      if (dp.getIpEquipo().equals("Postback")) {
        return new ModelAndView("redirect:/RegistroPaciente?message=" + "Postback");
      }
      if (dp.getDpDireccion() != null) {
        dp.setDpDireccion(dp.getDpDireccion().toUpperCase());
      }
      if (dp.getDpRun() != null && !dp.getDpRun().trim().isEmpty()) {
        dp.setDpRun(dp.getDpRun().replace(".", ""));
        dp.setDpNrodocumento(dp.getDpRun());
      }
      logger.debug("tipo de documento {}", tipoDocumento.getLdvtdIdtipodocumento());
      dp.setLdvTiposdocumentos(tipoDocumento.getLdvtdIdtipodocumento());

      if (DatosPacientesHelper.checkObligatoriosDatosPacientes(dp, rutMadre, esPartoMultiple) == false) {
        mensaje = "Datos de paciente incompletos";
      } else {
        DatosPacientesHelper.estandarizaPaciente(dp, fechaNacimiento, esPartoMultiple, tipoDocumento, estadoCivil, pais,
            region, comuna, exitus);
        rutMadre = rutMadre.replaceAll("\\.", "");
        pacienteService.insertPaciente(dp, dc, patologias, observacionesPatologias, rutMadre, idUsuario);
        mensaje = "Paciente ingresado correctamente&idPaciente=" + dp.getDpIdpaciente();
      }
    } catch (BiosLISDAOException | NumberFormatException | BiosLISException e) {
      logger.error(e.getMessage());
      mensaje = e.getMessage();
    }
    return new ModelAndView("redirect:/RegistroPaciente?message=" + mensaje);
  }

  @RequestMapping(value = "/RegistroPaciente", method = RequestMethod.POST, params = "rutFiltro")
  public void filtroPacienteRut(HttpServletRequest request, HttpServletResponse response)
      throws IOException, BiosLISDAOException {

    // Hay que validar los parametros

    PrintWriter writer = response.getWriter();
    String rutPaciente = request.getParameter("rutFiltro");
    DatosPacienteDTO dprn;
    try {

      // Cambiar esto ******
      int pasaporteIndex = rutPaciente.indexOf("PASAPORTE");

      if (pasaporteIndex > -1) {
        rutPaciente = rutPaciente.substring("PASAPORTE".length());
      }
      // *******************
      rutPaciente = rutPaciente.replace(".", "").toUpperCase();

      dprn = pacienteService.getPacienteByRut(rutPaciente);

      if (dprn == null) {

        dprn = new DatosPacienteDTO();
        DatosPacientes dp = new DatosPacientes();
        dp.setDpIdpaciente(0);
        dprn.setDp(dp);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonString = mapper.writeValueAsString(dprn);
        writer.print(jsonString);

      } else {

        // PATOLOGIAS JSON

        DatosContactosDAO dcDAO = new DatosContactosDAO();
        DatosContactos dc = dcDAO.getContactoxPaciente(dprn.getDp());
        dprn.setDc(dc);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonString = mapper.writeValueAsString(dprn);
        logger.debug(dprn.getDp().getDpFnacimiento());
        logger.debug("\nJSON RESPUESTA{}", jsonString);
        writer.print(jsonString);
      }
    } catch (BiosLISDAOException | BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      dprn = new DatosPacienteDTO();
      DatosPacientes dp = new DatosPacientes();

      dp.setDpIdpaciente(0);
      dprn.setDp(dp);
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      String jsonString = mapper.writeValueAsString(dprn);
      writer.print(jsonString);
    }
  }

  @RequestMapping(value = "/RegistroPaciente", method = RequestMethod.POST, params = "nombreFiltro")
  public void filtroPacienteNombre(HttpServletRequest request, HttpServletResponse response) throws IOException {

    PrintWriter writer = response.getWriter();
    try {
      List<DatosPacientes> pacientes = this.filtroPacienteNombre(request);

      if (pacientes.isEmpty()) {
        logger.debug("No se encontraron pacientes con ese nombre.");
        new Gson().toJson("empty", response.getWriter()); // retorna no existencia de pacientes
      } else {
        JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (DatosPacientes paciente : pacientes) {
          JsonObjectBuilder pacienteBuilder = Json.createObjectBuilder();
          JsonObject pacienteJson;
          pacienteBuilder.add("Id", paciente.getDpIdpaciente());
          pacienteBuilder.add("Rut", paciente.getDpNrodocumento());
          pacienteBuilder.add("Nombre", paciente.getDpNombres());
          pacienteBuilder.add("Apellido", paciente.getDpPrimerapellido());
          if (paciente.getDpRnnumero() != null) {
            pacienteBuilder.add("Secuencia", paciente.getDpRnnumero());
          }
          if (paciente.getDpSegundoapellido() != null) {
            pacienteBuilder.add("SegundoApellido", paciente.getDpSegundoapellido());
          }
          if (paciente.getDpFechacreacion() != null) {
            pacienteBuilder.add("FechaIngreso", DateFormatterHelper.tsToText(paciente.getDpFechacreacion()));
          }
          pacienteBuilder.add("TipoDocumento", paciente.getLdvTiposdocumentos());

          pacienteJson = pacienteBuilder.build();
          arrayBuilder.add(pacienteJson);
        }
        JsonObject root = rootBuilder.add("pacientes", arrayBuilder).build();
        writer.print(root);
      }
    } catch (JsonIOException | BiosLISDAOException | IOException | NumberFormatException e) {
      logger.error(e.getMessage());
      writer.print(e.getMessage());
    }

  }

  @RequestMapping(value = "/RegistroPaciente", method = RequestMethod.POST, params = "rutMadreFiltro")
  public void filtroPacienteMadre(HttpServletRequest request, HttpServletResponse response)
      throws IOException, BiosLISDAOException, BiosLISDAONotFoundException {
    PrintWriter writer = response.getWriter();
    try {

      String rutMadre = request.getParameter("rutMadreFiltro");
      rutMadre = rutMadre.replace(".", "");
      DatosPacientes madre = pacienteService.getPacienteByRut(rutMadre).getDp(); // getPacienteByRut(rutMadre);
      List<DatosPacientes> listaPacientes = pacienteService.getPacienteByIdMadre(madre.getDpIdpaciente());

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (DatosPacientes paciente : listaPacientes) {
        JsonObjectBuilder pacienteBuilder = Json.createObjectBuilder();
        JsonObject pacienteJson;
        pacienteBuilder.add("Id", paciente.getDpIdpaciente());
        pacienteBuilder.add("Rut", paciente.getDpNrodocumento());
        pacienteBuilder.add("Nombre", paciente.getDpNombres());
        pacienteBuilder.add("Apellido", paciente.getDpPrimerapellido());
        pacienteBuilder.add("Secuencia", paciente.getDpRnnumero() == null ? 0 : paciente.getDpRnnumero());
        if (paciente.getDpSegundoapellido() != null) {
          pacienteBuilder.add("SegundoApellido", paciente.getDpSegundoapellido());
        }
        pacienteJson = pacienteBuilder.build();
        arrayBuilder.add(pacienteJson);
      }
      JsonObject root = rootBuilder.add("pacientes", arrayBuilder).build();

      writer.print(root);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      writer.print(e.getMessage());
    }

  }

  @RequestMapping(value = "/RegistroPaciente", method = RequestMethod.POST, params = "validarRnnumero")
  public void validarRunnumero(HttpServletRequest request, HttpServletResponse response)
      throws IOException, BiosLISDAOException {
    try {
      String rnmuero = request.getParameter("rnnumero");
      int rnnmuero = Integer.parseInt(rnmuero);
      String rutMadre = request.getParameter("rutMadreRN");
      rutMadre = rutMadre.replace(".", "");
      DatosPacientesDAO dpDAO = new DatosPacientesDAO();
      DatosPacientes madre = dpDAO.getPacienteByRut(rutMadre);
      List<DatosPacientes> listaPaciente = dpDAO.getPacienteRnnumero(madre.getDpIdpaciente(), rnnmuero);
      if (listaPaciente.size() > 0) {
        new Gson().toJson("ya existe", response.getWriter());
      } else {
        new Gson().toJson("no existe", response.getWriter());
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      new Gson().toJson(e.getMessage(), response.getWriter());
    }

  }

  @RequestMapping(value = "/RegistroPaciente", method = RequestMethod.POST, params = "getComunas")
  public void comunasByRegion(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PrintWriter writer = response.getWriter();
    try {
      int idRegion = Integer.parseInt(request.getParameter("idRegion"));
      LdvRegionesDAO regionesDAO = new LdvRegionesDAO();
      CfgComunasregionesDAO comunasRegiones = new CfgComunasregionesDAO();
      LdvRegiones region = regionesDAO.getRegionById(idRegion);
      List<CfgComunasregiones> listaComuna = comunasRegiones.getComunasByRegion(region);
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (CfgComunasregiones comuna : listaComuna) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;
        comunaBuilder.add("idComuna", comuna.getLdvComunas().getLdvcIdcomuna()).add("nombreComuna",
            comuna.getLdvComunas().getLdvcDescripcion());
        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("comunas", arrayBuilder).build();

      writer.print(root);
    } catch (Exception e) {
      logger.error(e.getMessage());
      writer.print(e.getMessage());
    }

  }

  @RequestMapping(value = "/RegistroPaciente", method = RequestMethod.POST, params = "getNN")
  public void buscarNN(HttpServletRequest request, HttpServletResponse response) throws Exception {

    try (PrintWriter writer = response.getWriter()) {

      Integer tipoDoc = Integer.valueOf(request.getParameter("tipoDoc"));
      List<DatosPacientes> ldp = null;

      String fecha = request.getParameter("fecha");
      String nombre = request.getParameter("nombre");
      String primerApellido = request.getParameter("primerApellido");
      String segundoApellido = request.getParameter("segundoApellido");

      Timestamp fechaCreacion = null;
      if (fecha != null) {
        logger.debug("Busca NN por fecha {}", fecha);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(fecha));
        fechaCreacion = BiosLisCalendarService.getInstance().getTS(fecha);
        ldp = pacienteService.getByFechaCreacionTipoDoc(fechaCreacion, tipoDoc);
      } else if (nombre != null && primerApellido != null && segundoApellido != null) {
        logger.debug("Busca NN por nombre y apellidos {} - {} - {}", nombre, primerApellido, segundoApellido);
        ldp = pacienteService.getPacienteByNombreApellidoTipoDoc(nombre, primerApellido, segundoApellido, tipoDoc);
      }

      JsonObject datosPacienteJson;
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonObjectBuilder datosPacienteBuilder = Json.createObjectBuilder();
      JsonArrayBuilder listaPacientesBuilder = Json.createArrayBuilder();

      if (ldp == null) {
        datosPacienteBuilder.add("idPaciente", -1).add("nroDocumento", "").add("nombres", "").add("primerApellido", "");
      } else {
        for (DatosPacientes dp : ldp) {
          DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
          String strDate = dateFormat.format(dp.getDpFechacreacion());
          datosPacienteBuilder.add("idPaciente", dp.getDpIdpaciente()).add("nroDocumento", dp.getDpNrodocumento())
              .add("nombres", dp.getDpNombres()).add("primerApellido", dp.getDpPrimerapellido())
              .add("fechaIngreso", strDate);
          if (dp.getDpSegundoapellido() != null) {
            datosPacienteBuilder.add("SegundoApellido", dp.getDpSegundoapellido());
          }
          datosPacienteJson = datosPacienteBuilder.build();
          listaPacientesBuilder.add(datosPacienteJson);
        }
      }
      JsonObject root = rootBuilder.add("pacienteNN", listaPacientesBuilder).build();
      writer.print(root);
    } catch (IOException | ParseException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  private List<DatosPacientes> filtroPacienteNombre(HttpServletRequest request)
      throws NumberFormatException, BiosLISDAOException {

    String nombrePaciente = request.getParameter("nombreFiltro");
    String apellidoPaciente = request.getParameter("apellidoFiltro");
    String segundoApellidoPaciente = request.getParameter("segundoApellidoFiltro");
    String tipoDocumento = request.getParameter("tipoDocumento");

    if (nombrePaciente != null) {
      nombrePaciente = StringUtils.stripAccents(nombrePaciente).toUpperCase().trim().replace("Ñ", "N").replace('-',
          ' ');
      ;
    }

    if (apellidoPaciente != null) {
      apellidoPaciente = StringUtils.stripAccents(apellidoPaciente).toUpperCase().trim().replace("Ñ", "N").replace('-',
          ' ');
    }
    if (segundoApellidoPaciente != null) {
      segundoApellidoPaciente = StringUtils.stripAccents(segundoApellidoPaciente).toUpperCase().trim().replace("Ñ", "N")
          .replace('-', ' ');
      ;
    }
    List<DatosPacientes> pacientes = pacienteService.getPacienteByNombreApellidoTipoDoc(nombrePaciente,
        apellidoPaciente, segundoApellidoPaciente, Integer.valueOf(tipoDocumento));
    return pacientes;

  }

  List<DatosPacientes> filtroPacienteMadre(HttpServletRequest request)
      throws BiosLISDAOException, BiosLISDAONotFoundException {

    String rutMadre = request.getParameter("rutMadreFiltro");
    rutMadre = rutMadre.replace(".", "");
    DatosPacientesDAO dpDAO = new DatosPacientesDAO();
    DatosPacientes madre = dpDAO.getPacienteByRut(rutMadre);
    return dpDAO.getPacienteByIdMadre(madre.getDpIdpaciente());
  }

}
