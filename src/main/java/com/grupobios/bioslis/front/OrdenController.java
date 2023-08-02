package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.bs.ExamenService;
import com.grupobios.bioslis.bs.OrdenService;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCamasSalasDAO;
import com.grupobios.bioslis.dao.CfgConvenioDAO;
import com.grupobios.bioslis.dao.CfgDiagnosticosDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgExamenesTestDAO;
import com.grupobios.bioslis.dao.CfgInstitucionesDeSaludDAO;
import com.grupobios.bioslis.dao.CfgLocalizacionesDAO;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.dao.CfgPrioridadAtencionDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgSalasServiciosDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.DatosContactosDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosPacientesDAO;
import com.grupobios.bioslis.dao.DatosPacientespatologiasDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.DatosFichasDTO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.dto.TestRepetidosDTO;
import com.grupobios.bioslis.entity.DatosContactos;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosPacientespatologias;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.common.DateFormatterHelper;

@Controller
public class OrdenController {

  private static Logger logger = LogManager.getLogger(OrdenController.class);

  @Autowired
  private DatosFichasDAO datosFichasDAO;
  private CfgLocalizacionesDAO cfgLocalizacionesDAO;

  @Autowired
  private OrdenService ordenService;

  @Autowired
  private ExamenService examenService;

  ModelAndView mav = new ModelAndView();

  @RequestMapping("/Orden")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes)
      throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) request.getSession();

    Long idUsuario = 0L;
    DatosUsuarios usuario = null;

    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_ORDEN);
    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

      LdvTiposdocumentosDAO tiposDocumentosDAO = new LdvTiposdocumentosDAO();//
      LdvSexoDAO sexoDAO = new LdvSexoDAO();
      CfgPrioridadAtencionDAO prior = new CfgPrioridadAtencionDAO();
      CfgTipoAtencionDAO tipoAt = new CfgTipoAtencionDAO();
      CfgProcedenciasDAO getProce = new CfgProcedenciasDAO();
      CfgConvenioDAO getConvenio = new CfgConvenioDAO();
      CfgInstitucionesDeSaludDAO institSalud = new CfgInstitucionesDeSaludDAO();
      CfgExamenesDAO getExamn = new CfgExamenesDAO();
      CfgDiagnosticosDAO getDiag = new CfgDiagnosticosDAO();
      CfgServiciosDAO getServicios = new CfgServiciosDAO();
      CfgMedicosDAO med = new CfgMedicosDAO();

      mav.addObject("listaServicios", getServicios.getServicios());
      mav.addObject("listaInsitucionesSalud", institSalud.getInstitucionesDeSalud());
      mav.addObject("listaConvenio", getConvenio.getConvenios());
      mav.addObject("listaSexo", sexoDAO.getSexo());
      mav.addObject("listaExamen", getExamn.getExamenes());
      // mav.addObject("listaProce", getProce.getProcedencias());
      mav.addObject("listaTiposDocumentos", tiposDocumentosDAO.getTiposDocumentoRutPasaporte());
      mav.addObject("listaMedicos", med.getAllMedics());
      try {
        mav.addObject("listaPrior", prior.getPrior());
      } catch (BiosLISDAOException ex) {
        java.util.logging.Logger.getLogger(OrdenController.class.getName()).log(Level.SEVERE, null, ex);
      }
      mav.addObject("listaTipoAt", tipoAt.getTipoAtencion());
      mav.addObject("listaDiag", getDiag.getDiagnosticos());
    }
    
    //aqui se inserta la accion que realiza usuario en modulos  ***********   
    if(usuario != null) {         
	    LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	    LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	    
	    leu.setLeuIdusuario( idUsuario.intValue());
	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	    leu.setLeuIdevento(11);              
	    leu.setLeuIdacciondato(0);
	    leu.setLeuNombretabla("");
	    leu.setLeuNombrecampo("");
	    leu.setLeuValornuevo("accede a Registro de Orden");
	    logUsuarioDao.insertLogEventosUsuario(leu);
    }
   //*-************************************************************ 
    
    return mav;
  }

  @RequestMapping(value = "/Orden", method = RequestMethod.POST)
  public ModelAndView registroOrden(DatosFichasDTO medReq, HttpServletRequest request) throws Exception {

    try {
      HttpSession sesion = (HttpSession) request.getSession();
      Long idUsuario = 0L;
      String[] examenes = request.getParameterValues("ExamenesResumenes");
      String ipEquipo = request.getParameter("ipEquipo");
      if (examenes == null) {
        return new ModelAndView("redirect:/Orden?message=Orden no tiene examenes.");
      }
      DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

      DatosFichas med = medReq.toDatosFicha();
      logger.debug("registroOrden : {}", med.toString());
      med = ordenService.insertOrden(medReq, medReq.getLstExamenes(), ipEquipo, idUsuario);

      List<TestRepetidosDTO> lstTest = examenService.getTestRepetidosExamenes();
      List<?> lstOrdenes = ordenService.getOrdenesConExamenesValidosDeUnPaciente(med.getDatosPacientes());

      String mensaje = Integer.toString(med.getDfNorden());
      if (lstTest.size() != 0 || lstOrdenes.size() != 0) {
        mensaje += " hay exámenes repetidos y/o validos";
      }
      return new ModelAndView("redirect:/Orden?message=" + mensaje);
    } catch (NumberFormatException | BiosLISDAOException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  @RequestMapping(value = "/Orden", method = RequestMethod.POST, params = "masDeUnaOrden")
  public void verificarOrden(HttpServletRequest request, HttpServletResponse response) throws IOException {

    try {
      int idPac = Integer.parseInt(request.getParameter("masDeUnaOrden"));
      DatosFichasDAO dfd = new DatosFichasDAO();
      boolean val = dfd.verificarMasDeUnaOrden(idPac);
      new Gson().toJson(!val, response.getWriter());
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/Orden", method = RequestMethod.POST, params = "masDeUnExamenEnTiempo")
  public void verificarExamenxPaciente(HttpServletRequest request, HttpServletResponse response) throws IOException {

    try {
      int idExamen = Integer.parseInt(request.getParameter("masDeUnExamenEnTiempo"));
      int idPa2 = Integer.parseInt(request.getParameter("idPac"));
      DatosFichasDAO dfd = new DatosFichasDAO();
      boolean val = dfd.verificarExamenxPaciente(idPa2, idExamen);
      new Gson().toJson(!val, response.getWriter());
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/Orden", method = RequestMethod.POST, params = "salasServicios")
  public void SalasServicios(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
    try {
      CfgServiciosDAO serv = new CfgServiciosDAO();
      CfgSalasServiciosDAO salasServicios = new CfgSalasServiciosDAO();

      int idsalasServicios = 0;
      try {
        idsalasServicios = Integer.parseInt(request.getParameter("salasServicios"));

      } catch (NumberFormatException nfe) {
        idsalasServicios = 0;
      }

      List<Object[]> salasServicioList = salasServicios.getSalasServicios(serv.getServiciosById(idsalasServicios));
      if (salasServicioList.isEmpty()) {

        PrintWriter writer = response.getWriter();
        writer.print("0");
        return;
      }
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (Object[] salasS : salasServicioList) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;
        String id = salasS[0].toString();
        String descripcion = salasS[2].toString();
        comunaBuilder.add("idSalasS", id).add("detalleSalasS", descripcion);
        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("salasS", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/Orden", method = RequestMethod.POST, params = "CamasSalas")
  public void CamasSalas(HttpServletRequest request, HttpServletResponse response)
      throws IOException, BiosLISDAOException {

    CfgCamasSalasDAO camasSalas = new CfgCamasSalasDAO();

    int idsalasServicios = 0;
    try {
      idsalasServicios = Integer.parseInt(request.getParameter("CamasSalas"));

    } catch (NumberFormatException nfe) {
      idsalasServicios = 0;
    }

    List<Object[]> camasSalasList = camasSalas.getCamasSalas(idsalasServicios);
    if (camasSalasList.isEmpty()) {

      PrintWriter writer = response.getWriter();
      writer.print("0");
      return;
    }

    JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for (Object[] salasS : camasSalasList) {
      JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
      JsonObject comunaJson;
      String id = salasS[0].toString();
      String descripcion = salasS[2].toString();
      comunaBuilder.add("idCamasS", id).add("detalleCamasS", descripcion);
      comunaJson = comunaBuilder.build();
      arrayBuilder.add(comunaJson);
    }
    JsonObject root = rootBuilder.add("camasSalas", arrayBuilder).build();
    PrintWriter writer = response.getWriter();
    writer.print(root);

  }

  @RequestMapping(value = "/Orden", method = RequestMethod.POST, params = "examenesTest")
  public void getExamenTest(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {

    CfgExamenesTestDAO cetd = new CfgExamenesTestDAO();
    try {
      int idExamenes = Integer.parseInt(request.getParameter("examenesTest"));
      cetd.getTest(idExamenes);

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      JsonObject root = rootBuilder.add("test", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/Orden", method = RequestMethod.POST, params = "rutFiltro")
  public void filtroPacienteRut(HttpServletRequest request, HttpServletResponse response)
      throws IOException, BiosLISDAOException {

    // Rehacer no se maneja bien la busqueda, ver tema pasaporte.

    try (PrintWriter writer = response.getWriter()) {
      String rutPaciente = request.getParameter("rutFiltro");
      logger.debug("{}", rutPaciente);
      // Cambiar esto ******

      int pasaporteIndex = rutPaciente.indexOf("PASAPORTE");

      if (pasaporteIndex > -1) {
        rutPaciente = rutPaciente.substring("PASAPORTE".length());
      }

      // *******************

      rutPaciente = rutPaciente.replace(".", "");
      DatosPacientesDAO dpDAO = new DatosPacientesDAO();
      DatosPacientes paciente = dpDAO.getByRut(rutPaciente);
      if (paciente == null) {
        paciente = new DatosPacientes();
        paciente.setDpNrodocumento("0");
      }
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      JsonObject pacienteJson;
      pacienteJson = pacienteJsonBuilder(paciente);
      arrayBuilder.add(pacienteJson);
      JsonObject root = rootBuilder.add("pacientes", arrayBuilder).build();
      writer.print(root);
    } catch (IOException | BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      throw ex;
    } finally {

    }

  }

  public JsonObject pacienteJsonBuilder(DatosPacientes paciente) throws BiosLISDAOException {

    JsonObjectBuilder pacienteBuilder = Json.createObjectBuilder();

    if (paciente.getDpNrodocumento().equals("0")) {
      pacienteBuilder.add("Rut", paciente.getDpNrodocumento());
    } else {
      pacienteBuilder.add("idPaciente", paciente.getDpIdpaciente())
          .add("TipoDocumento", paciente.getLdvTiposdocumentos()).add("Rut", paciente.getDpNrodocumento())
          .add("Nombres", paciente.getDpNombres()).add("PrimerApellido", paciente.getDpPrimerapellido())
          .add("Sexo", paciente.getLdvSexo())
          .add("dpFnacimiento", DateFormatterHelper.dtimeToText(paciente.getDpFnacimiento()));
      if (paciente.getDpEsvip() != null) {
        pacienteBuilder.add("esVip", paciente.getDpEsvip());
      }
      if (paciente.getDpEsafroamericano() != null) {
        pacienteBuilder.add("esAfro", paciente.getDpEsafroamericano());
      }
      if (paciente.getDpSegundoapellido() != null) {
        pacienteBuilder.add("SegundoApellido", paciente.getDpSegundoapellido());
      }
      if (paciente.getDpDireccion() != null) {
        paciente.setDpDireccion(paciente.getDpDireccion().toUpperCase());
        pacienteBuilder.add("Direccion", paciente.getDpDireccion());
      }
      if (paciente.getLdvEstadosciviles() != null) {
        pacienteBuilder.add("EstadoCivil", paciente.getLdvEstadosciviles());
      }
      if (paciente.getLdvPaises() != null) {
        pacienteBuilder.add("Pais", paciente.getLdvPaises());
      }
      if (paciente.getLdvRegiones() != null) {
        pacienteBuilder.add("Region", paciente.getLdvRegiones());
      }
      if (paciente.getLdvComunas() != null) {
        pacienteBuilder.add("Comuna", paciente.getLdvComunas());
      }
      if (paciente.getDpTelefono() != null) {
        pacienteBuilder.add("Telefono", paciente.getDpTelefono());
      }
      if (paciente.getDpEmail() != null) {
        paciente.setDpEmail(paciente.getDpEmail().toUpperCase());
        pacienteBuilder.add("Email", paciente.getDpEmail());
      }
      if (paciente.getDpNombresocial() != null) {
        pacienteBuilder.add("NombreSocial", paciente.getDpNombresocial());
      }
      if (paciente.getDpObservacion() != null) {
        paciente.setDpCampolibre1(paciente.getDpObservacion().toUpperCase());
        pacienteBuilder.add("Observacion", paciente.getDpObservacion());
      }
      if (paciente.getDpExitus() != null) {
        pacienteBuilder.add("Exitus", paciente.getDpExitus());
      }

      // PATOLOGIAS JSON
      DatosPacientespatologiasDAO dppDAO = new DatosPacientespatologiasDAO();
      List<DatosPacientespatologias> listaPatologias = dppDAO.getPatologiasxPacientesList(paciente);
      JsonArrayBuilder arrayBuilderPatologias = Json.createArrayBuilder();
      for (DatosPacientespatologias listaPatologia : listaPatologias) {
        JsonObjectBuilder patologiaBuilder = Json.createObjectBuilder();
        JsonObject pacientePatologiaJson;
        patologiaBuilder.add("Patologia", listaPatologia.getLdvPatologias().getLdvpatDescripcion()).add("Id",
            listaPatologia.getLdvPatologias().getLdvpatIdpatologia());
        if (listaPatologia.getDppObservacion() != null) {
          patologiaBuilder.add("Observacion", listaPatologia.getDppObservacion());
        } else {
          patologiaBuilder.add("Observacion", "");
        }
        pacientePatologiaJson = patologiaBuilder.build();
        arrayBuilderPatologias.add(pacientePatologiaJson);
      }
      if (listaPatologias.size() > 0) {
        pacienteBuilder.add("Patologias", arrayBuilderPatologias.build());
      }

      // DATOS CONTACTOS JSON
      DatosContactosDAO dcDAO = new DatosContactosDAO();
      DatosContactos dc = dcDAO.getContactoxPaciente(paciente);
      JsonArrayBuilder arrayBuilderContacto = Json.createArrayBuilder();
      JsonObjectBuilder contactoBuilder = Json.createObjectBuilder();
      JsonObject contactoJson;
      if (dc != null && dc.getDcIdcontacto() != 0) {
        contactoBuilder.add("Nombre", dc.getDcNombre()).add("Telefono", dc.getDcTelefono());
        if (dc.getLdvContactospacientesrelacion() != null) {
          contactoBuilder.add("Relacion", dc.getLdvContactospacientesrelacion().getLdvcprIdcontactopacienterel());
        }
        contactoJson = contactoBuilder.build();
        arrayBuilderContacto.add(contactoJson);
      }

      pacienteBuilder.add("Contacto", arrayBuilderContacto.build());

    }
    ;

    return pacienteBuilder.build();
  }

  /**
   * @return the datosFichasDAO
   */
  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  /**
   * @param datosFichasDAO the datosFichasDAO to set
   */
  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  /**
   * @return the cfgLocalizacionesDAO
   */
  public CfgLocalizacionesDAO getCfgLocalizacionesDAO() {
    return cfgLocalizacionesDAO;
  }

  /**
   * @param cfgLocalizacionesDAO the cfgLocalizacionesDAO to set
   */
  public void setCfgLocalizacionesDAO(CfgLocalizacionesDAO cfgLocalizacionesDAO) {
    this.cfgLocalizacionesDAO = cfgLocalizacionesDAO;
  }

  @RequestMapping(value = "/Orden", method = RequestMethod.POST, params = "nroDocumento")
  public void filtroPacienteTipoDoc(HttpServletRequest request, HttpServletResponse response)
      throws IOException, BiosLISDAOException {

    // Rehacer no se maneja bien la busqueda, ver tema pasaporte.

    try (PrintWriter writer = response.getWriter()) {
      String nroDocumento = request.getParameter("nroDocumento");
      logger.debug("{}", nroDocumento);
      // Cambiar esto ******

      DatosPacientesDAO dpDAO = new DatosPacientesDAO();
      DatosPacientes paciente = dpDAO.getByRut(nroDocumento);
      if (paciente == null) {
        paciente = new DatosPacientes();
        paciente.setDpNrodocumento("0");
      }
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      JsonObject pacienteJson;
      pacienteJson = pacienteJsonBuilder(paciente);
      arrayBuilder.add(pacienteJson);
      JsonObject root = rootBuilder.add("pacientes", arrayBuilder).build();
      writer.print(root);
    } catch (IOException | BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      throw ex;
    } finally {

    }

  }

  public OrdenService getOrdenService() {
    return ordenService;
  }

  public void setOrdenService(OrdenService ordenService) {
    this.ordenService = ordenService;
  }

  public ExamenService getExamenService() {
    return examenService;
  }

  public void setExamenService(ExamenService examenService) {
    this.examenService = examenService;
  }

}
