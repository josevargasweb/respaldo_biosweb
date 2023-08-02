package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
import com.grupobios.bioslis.dao.CfgConvenioDAO;
import com.grupobios.bioslis.dao.CfgDiagnosticosDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgInstitucionesDeSaludDAO;
import com.grupobios.bioslis.dao.CfgLocalizacionesDAO;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.dao.CfgPrioridadAtencionDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.util.logging.Level;

@Controller
public class EdicionOrdenesController {

  ModelAndView mav = new ModelAndView();
  private static Logger logger = LogManager.getLogger(EdicionOrdenesController.class);

  @RequestMapping("/EdicionOrdenes")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) request.getSession();
    
    DatosUsuarios usuario = null;

    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, 5);
    if (sesion.getAttribute("usuario") != null) {
    	 usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      LdvTiposdocumentosDAO tiposDocumentosDAO = new LdvTiposdocumentosDAO();//
      CfgMedicosDAO med = new CfgMedicosDAO();
      LdvSexoDAO sexoDAO = new LdvSexoDAO();
      CfgPrioridadAtencionDAO prior = new CfgPrioridadAtencionDAO();
      CfgTipoAtencionDAO tipoAt = new CfgTipoAtencionDAO();
      CfgProcedenciasDAO getProce = new CfgProcedenciasDAO();
      CfgConvenioDAO getConvenio = new CfgConvenioDAO();
      CfgInstitucionesDeSaludDAO institSalud = new CfgInstitucionesDeSaludDAO();
      CfgExamenesDAO getExamn = new CfgExamenesDAO();
      CfgDiagnosticosDAO getDiag = new CfgDiagnosticosDAO();
      CfgServiciosDAO getServicios = new CfgServiciosDAO();

      //
      mav.addObject("listaServicios", getServicios.getServicios());
      mav.addObject("listaInsitucionesSalud", institSalud.getInstitucionesDeSalud());
      mav.addObject("listaConvenio", getConvenio.getConvenios());
      mav.addObject("listaSexo", sexoDAO.getSexo());
      mav.addObject("listaExamen", getExamn.getExamenes());
      //mav.addObject("listaProce", getProce.getProcedencias());
      mav.addObject("listaTiposDocumentos", tiposDocumentosDAO.getTiposDocumentos());
      mav.addObject("listaMedicos", med.getAllMedics());
        try {
            mav.addObject("listaPrior", prior.getPrior());
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(EdicionOrdenesController.class.getName()).log(Level.SEVERE, null, ex);
        }
      mav.addObject("listaTipoAt", tipoAt.getTipoAtencion());
      mav.addObject("listaDiag", getDiag.getDiagnosticos());
    }
    
    
    //aqui se inserta la accion que realiza usuario en modulos  ***********  
    if(usuario != null) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	      
	      leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	      leu.setLeuIdevento(11);              
	      leu.setLeuIdacciondato(0);	      
	      leu.setLeuValornuevo("accede a Modificar órdenes");
	      logUsuarioDao.insertLogEventosUsuario(leu);
    }
     //*-************************************************************ 
    
    
    return mav;
  }

  @RequestMapping(value = "/EdicionOrdenes", method = RequestMethod.POST)
  public ModelAndView registroOrden(DatosFichas med, HttpServletRequest request)
      throws ParseException, BiosLISDAOException {

    try {
      HttpSession sesion = (HttpSession) request.getSession();
      Long idUsuario = 0L;
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();

      CfgLocalizacionesDAO locali = new CfgLocalizacionesDAO();
      String[] examenes = request.getParameterValues("ExamenesResumenes");
      String idServicios = request.getParameter("servicioss");
      String idSalas = request.getParameter("salas");
      String idCamas = request.getParameter("camas");
      String ipEquipo = request.getParameter("ipEquipo");
      DatosFichasDAO dfd = new DatosFichasDAO();
      if (idServicios != null) {
        med.setCfgInstitucionesdesalud(1);
        med.setCfgProcedencias(1);
        med.setCfgPrioridadatencion(1);
      }
      med.setCfgServicios(locali.getIdLocalizacion(Integer.parseInt(idServicios), Integer.parseInt(idSalas),
          Integer.parseInt(idCamas)));
      med.setDfCodigolocalizacion("");
      med.setLdvFichasestadostm(2);
      dfd.updateOrden(med, examenes, ipEquipo, idUsuario);

      return new ModelAndView("redirect:/EdicionOrdenes?message=orden Actualizada");
    } catch (NumberFormatException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  @RequestMapping(value = "/EdicionOrdenes", method = RequestMethod.POST, params = "busquedaPorNombres")
  public void selectPorNombres(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException {

    try {
      String nombre = request.getParameter("busquedaPorNombres");
      String apellido = request.getParameter("apellido");

      DatosFichasDAO dfd = new DatosFichasDAO();
      List<Object[]> listaOrdenes = dfd.selectOrdenNombreApellido(nombre, apellido);

      if (listaOrdenes.isEmpty()) {

        PrintWriter writer = response.getWriter();
        writer.print("0");
        return;
      }

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (Object[] ordenes : listaOrdenes) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;
        if (ordenes[12] == null) {
          ordenes[12] = "0";
        }
        if (ordenes[16] == null) {
          ordenes[16] = "";
        }
        String fecha = ordenes[0].toString();
        String nOrdenn = ordenes[1].toString();
        String Nombres = ordenes[2].toString();
        String documento = ordenes[3].toString();
        String nDocumento = ordenes[4].toString();
        String tipoPaciente = ordenes[5].toString();
        String procendecia = ordenes[6].toString();
        String servicio = ordenes[7].toString();
        String idMedico = ordenes[8].toString();
        String idServicio = ordenes[9].toString();
        String idsalasServicio = ordenes[10].toString();
        String idcamasSalas = ordenes[11].toString();
        String enviarxMail = ordenes[12].toString();
        String idDiagnostico = ordenes[13].toString();
        String idConvenio = ordenes[14].toString();
        String PrioAtencion = ordenes[15].toString();
        String observacion = ordenes[16].toString();

        comunaBuilder.add("fecha", fecha).add("nOrdenn", nOrdenn).add("Nombres", Nombres).add("documento", documento)
            .add("nDocumento", nDocumento).add("tipoPaciente", tipoPaciente).add("procendecia", procendecia)
            .add("servicio", servicio).add("idMedico", idMedico).add("idsalasServicio", idsalasServicio)
            .add("idcamasSalas", idcamasSalas).add("enviarxMail", enviarxMail).add("idDiagnostico", idDiagnostico)
            .add("idConvenio", idConvenio).add("PrioAtencion", PrioAtencion).add("observacion", observacion)
            .add("idServicio", idServicio);
        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("ordenesTotales", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/EdicionOrdenes", method = RequestMethod.POST, params = "busquedaPorNombresyFechas")
  public void selectPorNombresyFechas(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException {

    try {
      String nombre = request.getParameter("busquedaPorNombresyFechas");
      String apellido = request.getParameter("apellido");
      String fechaInicio = request.getParameter("FechaInicio");
      String fechaFin = request.getParameter("FechaFin");

      DatosFichasDAO dfd = new DatosFichasDAO();
      List<Object[]> listaOrdenes = dfd.selectOrdenNombreApellidoFechas(nombre, apellido, fechaInicio, fechaFin);

      if (listaOrdenes.isEmpty()) {

        PrintWriter writer = response.getWriter();
        writer.print("0");
        return;
      }

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (Object[] ordenes : listaOrdenes) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;
        if (ordenes[12] == null) {
          ordenes[12] = "0";
        }
        if (ordenes[16] == null) {
          ordenes[16] = "";
        }
        String fecha = ordenes[0].toString();
        String nOrdenn = ordenes[1].toString();
        String Nombres = ordenes[2].toString();
        String documento = ordenes[3].toString();
        String nDocumento = ordenes[4].toString();
        String tipoPaciente = ordenes[5].toString();
        String procendecia = ordenes[6].toString();
        String servicio = ordenes[7].toString();
        String idMedico = ordenes[8].toString();
        String idServicio = ordenes[9].toString();
        String idsalasServicio = ordenes[10].toString();
        String idcamasSalas = ordenes[11].toString();
        String enviarxMail = ordenes[12].toString();
        String idDiagnostico = ordenes[13].toString();
        String idConvenio = ordenes[14].toString();
        String PrioAtencion = ordenes[15].toString();
        String observacion = ordenes[16].toString();

        comunaBuilder.add("fecha", fecha).add("nOrdenn", nOrdenn).add("Nombres", Nombres).add("documento", documento)
            .add("nDocumento", nDocumento).add("tipoPaciente", tipoPaciente).add("procendecia", procendecia)
            .add("servicio", servicio).add("idMedico", idMedico).add("idsalasServicio", idsalasServicio)
            .add("idcamasSalas", idcamasSalas).add("enviarxMail", enviarxMail).add("idDiagnostico", idDiagnostico)
            .add("idConvenio", idConvenio).add("PrioAtencion", PrioAtencion).add("observacion", observacion)
            .add("idServicio", idServicio);
        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("ordenesTotales", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/EdicionOrdenes", method = RequestMethod.POST, params = "busquedaPorOrden")
  public void selectPorOrden(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException {

    try {
      int nOrden = Integer.parseInt(request.getParameter("busquedaPorOrden"));

      DatosFichasDAO dfd = new DatosFichasDAO();
      List<Object[]> listaOrdenes = dfd.selectOrdenPorOrden(nOrden);

      if (listaOrdenes.isEmpty()) {

        PrintWriter writer = response.getWriter();
        writer.print("0");
        return;
      }

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (Object[] ordenes : listaOrdenes) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;
        if (ordenes[12] == null) {
          ordenes[12] = "0";
        }
        if (ordenes[16] == null) {
          ordenes[16] = "";
        }
        String fecha = ordenes[0].toString();
        String nOrdenn = ordenes[1].toString();
        String Nombres = ordenes[2].toString();
        String documento = ordenes[3].toString();
        String nDocumento = ordenes[4].toString();
        String tipoPaciente = ordenes[5].toString();
        String procendecia = ordenes[6].toString();
        String servicio = ordenes[7].toString();
        String idMedico = ordenes[8].toString();
        String idServicio = ordenes[9].toString();
        String idsalasServicio = ordenes[10].toString();
        String idcamasSalas = ordenes[11].toString();
        String enviarxMail = ordenes[12].toString();
        String idDiagnostico = ordenes[13].toString();
        String idConvenio = ordenes[14].toString();
        String PrioAtencion = ordenes[15].toString();
        String observacion = ordenes[16].toString();

        comunaBuilder.add("fecha", fecha).add("nOrdenn", nOrdenn).add("Nombres", Nombres).add("documento", documento)
            .add("nDocumento", nDocumento).add("tipoPaciente", tipoPaciente).add("procendecia", procendecia)
            .add("servicio", servicio).add("idMedico", idMedico).add("idsalasServicio", idsalasServicio)
            .add("idcamasSalas", idcamasSalas).add("enviarxMail", enviarxMail).add("idDiagnostico", idDiagnostico)
            .add("idConvenio", idConvenio).add("PrioAtencion", PrioAtencion).add("observacion", observacion)
            .add("idServicio", idServicio);
        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("ordenesTotales", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (IOException | NumberFormatException e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/EdicionOrdenes", method = RequestMethod.POST, params = "busquedaPorDoc")
  public void selectPorDoc(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException {

    try {
      String rut = request.getParameter("busquedaPorDoc");

      DatosFichasDAO dfd = new DatosFichasDAO();
      List<Object[]> listaOrdenes = dfd.selectOrdenesxnDoc(rut);

      if (listaOrdenes.isEmpty()) {

        PrintWriter writer = response.getWriter();
        writer.print("0");
        return;
      }

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (Object[] ordenes : listaOrdenes) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;
        if (ordenes[12] == null) {
          ordenes[12] = "0";
        }
        if (ordenes[16] == null) {
          ordenes[16] = "";
        }
        String fecha = ordenes[0].toString();
        String nOrdenn = ordenes[1].toString();
        String Nombres = ordenes[2].toString();
        String documento = ordenes[3].toString();
        String nDocumento = ordenes[4].toString();
        String tipoPaciente = ordenes[5].toString();
        String procendecia = ordenes[6].toString();
        String servicio = ordenes[7].toString();
        String idMedico = ordenes[8].toString();
        String idServicio = ordenes[9].toString();
        String idsalasServicio = ordenes[10].toString();
        String idcamasSalas = ordenes[11].toString();
        String enviarxMail = ordenes[12].toString();
        String idDiagnostico = ordenes[13].toString();
        String idConvenio = ordenes[14].toString();
        String PrioAtencion = ordenes[15].toString();
        String observacion = ordenes[16].toString();

        comunaBuilder.add("fecha", fecha).add("nOrdenn", nOrdenn).add("Nombres", Nombres).add("documento", documento)
            .add("nDocumento", nDocumento).add("tipoPaciente", tipoPaciente).add("procendecia", procendecia)
            .add("servicio", servicio).add("idMedico", idMedico).add("idsalasServicio", idsalasServicio)
            .add("idcamasSalas", idcamasSalas).add("enviarxMail", enviarxMail).add("idDiagnostico", idDiagnostico)
            .add("idConvenio", idConvenio).add("PrioAtencion", PrioAtencion).add("observacion", observacion)
            .add("idServicio", idServicio);
        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("ordenesTotales", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/EdicionOrdenes", method = RequestMethod.POST, params = "busquedaPorDocyFechas")
  public void selectPorDocyFechas(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException {

    try {
      String rut = request.getParameter("busquedaPorDocyFechas");
      String fechaInicio = request.getParameter("FechaInicio");
      String fechaFin = request.getParameter("FechaFin");

      DatosFichasDAO dfd = new DatosFichasDAO();
      List<Object[]> listaOrdenes = dfd.selectOrdenRutYFechas(rut, fechaInicio, fechaFin);

      if (listaOrdenes.isEmpty()) {

        PrintWriter writer = response.getWriter();
        writer.print("0");
        return;
      }

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (Object[] ordenes : listaOrdenes) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;
        if (ordenes[12] == null) {
          ordenes[12] = "0";
        }
        if (ordenes[16] == null) {
          ordenes[16] = "";
        }
        String fecha = ordenes[0].toString();
        String nOrdenn = ordenes[1].toString();
        String Nombres = ordenes[2].toString();
        String documento = ordenes[3].toString();
        String nDocumento = ordenes[4].toString();
        String tipoPaciente = ordenes[5].toString();
        String procendecia = ordenes[6].toString();
        String servicio = ordenes[7].toString();
        String idMedico = ordenes[8].toString();
        String idServicio = ordenes[9].toString();
        String idsalasServicio = ordenes[10].toString();
        String idcamasSalas = ordenes[11].toString();
        String enviarxMail = ordenes[12].toString();
        String idDiagnostico = ordenes[13].toString();
        String idConvenio = ordenes[14].toString();
        String PrioAtencion = ordenes[15].toString();
        String observacion = ordenes[16].toString();

        comunaBuilder.add("fecha", fecha).add("nOrdenn", nOrdenn).add("Nombres", Nombres).add("documento", documento)
            .add("nDocumento", nDocumento).add("tipoPaciente", tipoPaciente).add("procendecia", procendecia)
            .add("servicio", servicio).add("idMedico", idMedico).add("idsalasServicio", idsalasServicio)
            .add("idcamasSalas", idcamasSalas).add("enviarxMail", enviarxMail).add("idDiagnostico", idDiagnostico)
            .add("idConvenio", idConvenio).add("PrioAtencion", PrioAtencion).add("observacion", observacion)
            .add("idServicio", idServicio);
        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("ordenesTotales", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/EdicionOrdenes", method = RequestMethod.POST, params = "busquedaPorProcedenciaFechas")
  public void selectPorProcedenciaFechas(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException {

    try {
      String procedencia = request.getParameter("busquedaPorProcedenciaFechas");
      String fechaInicio = request.getParameter("FechaInicio");
      String fechaFin = request.getParameter("FechaFin");

      DatosFichasDAO dfd = new DatosFichasDAO();
      List<Object[]> listaOrdenes = dfd.selectOrdenProcedenciaFechas(Integer.parseInt(procedencia), fechaInicio,
          fechaFin);

      if (listaOrdenes.isEmpty()) {

        PrintWriter writer = response.getWriter();
        writer.print("0");
        return;
      }

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (Object[] ordenes : listaOrdenes) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;
        if (ordenes[12] == null) {
          ordenes[12] = "0";
        }
        if (ordenes[16] == null) {
          ordenes[16] = "";
        }
        String fecha = ordenes[0].toString();
        String nOrdenn = ordenes[1].toString();
        String Nombres = ordenes[2].toString();
        String documento = ordenes[3].toString();
        String nDocumento = ordenes[4].toString();
        String tipoPaciente = ordenes[5].toString();
        String procendecia = ordenes[6].toString();
        String servicio = ordenes[7].toString();
        String idMedico = ordenes[8].toString();
        String idServicio = ordenes[9].toString();
        String idsalasServicio = ordenes[10].toString();
        String idcamasSalas = ordenes[11].toString();
        String enviarxMail = ordenes[12].toString();
        String idDiagnostico = ordenes[13].toString();
        String idConvenio = ordenes[14].toString();
        String PrioAtencion = ordenes[15].toString();
        String observacion = ordenes[16].toString();

        comunaBuilder.add("fecha", fecha).add("nOrdenn", nOrdenn).add("Nombres", Nombres).add("documento", documento)
            .add("nDocumento", nDocumento).add("tipoPaciente", tipoPaciente).add("procendecia", procendecia)
            .add("servicio", servicio).add("idMedico", idMedico).add("idsalasServicio", idsalasServicio)
            .add("idcamasSalas", idcamasSalas).add("enviarxMail", enviarxMail).add("idDiagnostico", idDiagnostico)
            .add("idConvenio", idConvenio).add("PrioAtencion", PrioAtencion).add("observacion", observacion)
            .add("idServicio", idServicio);
        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("ordenesTotales", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (IOException | NumberFormatException e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/EdicionOrdenes", method = RequestMethod.POST, params = "busquedaPorFechas")
  public void selectPorFechas(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException {

    try {
      String fechaInicio = request.getParameter("busquedaPorFechas");
      String fechaFin = request.getParameter("FechaFin");

      DatosFichasDAO dfd = new DatosFichasDAO();
      List<Object[]> listaOrdenes = dfd.selectOrdenFechas(fechaInicio, fechaFin);

      if (listaOrdenes.isEmpty()) {

        PrintWriter writer = response.getWriter();
        writer.print("0");
        return;
      }

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (Object[] ordenes : listaOrdenes) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;
        if (ordenes[12] == null) {
          ordenes[12] = "0";
        }
        if (ordenes[16] == null) {
          ordenes[16] = "";
        }
        String fecha = ordenes[0].toString();
        String nOrdenn = ordenes[1].toString();
        String Nombres = ordenes[2].toString();
        String documento = ordenes[3].toString();
        String nDocumento = ordenes[4].toString();
        String tipoPaciente = ordenes[5].toString();
        String procendecia = ordenes[6].toString();
        String servicio = ordenes[7].toString();
        String idMedico = ordenes[8].toString();
        String idServicio = ordenes[9].toString();
        String idsalasServicio = ordenes[10].toString();
        String idcamasSalas = ordenes[11].toString();
        String enviarxMail = ordenes[12].toString();
        String idDiagnostico = ordenes[13].toString();
        String idConvenio = ordenes[14].toString();
        String PrioAtencion = ordenes[15].toString();
        String observacion = ordenes[16].toString();

        comunaBuilder.add("fecha", fecha).add("nOrdenn", nOrdenn).add("Nombres", Nombres).add("documento", documento)
            .add("nDocumento", nDocumento).add("tipoPaciente", tipoPaciente).add("procendecia", procendecia)
            .add("servicio", servicio).add("idMedico", idMedico).add("idsalasServicio", idsalasServicio)
            .add("idcamasSalas", idcamasSalas).add("enviarxMail", enviarxMail).add("idDiagnostico", idDiagnostico)
            .add("idConvenio", idConvenio).add("PrioAtencion", PrioAtencion).add("observacion", observacion)
            .add("idServicio", idServicio);
        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("ordenesTotales", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

  @RequestMapping(value = "/EdicionOrdenes", method = RequestMethod.POST, params = "busquedaPorExamen")
  public void selectExamenes(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException {

    try {
      String nOrden = request.getParameter("busquedaPorExamen");

      DatosFichasExamenesDAO dfed = new DatosFichasExamenesDAO();
      List<Object[]> listaOrdenes = dfed.selectExamenesPorOrden(Integer.parseInt(nOrden));

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (Object[] ordenes : listaOrdenes) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;

        String idExamen = ordenes[0].toString();
        String Codigo = ordenes[1].toString();
        String descripcion = ordenes[2].toString();

        comunaBuilder.add("idExamen", idExamen).add("Codigo", Codigo).add("descripcion", descripcion);

        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("Examenes", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (IOException | NumberFormatException e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

}
