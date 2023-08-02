package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgConvenioDAO;
import com.grupobios.bioslis.dao.CfgDiagnosticosDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgInstitucionesDeSaludDAO;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.dao.CfgPrioridadAtencionDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgPrioridadatencion;
import com.grupobios.bioslis.entity.DatosUsuarios;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ConfiguracionPrioridadAtencionController {

  ModelAndView mav = new ModelAndView();

  private static org.apache.logging.log4j.Logger logger = LogManager
      .getLogger(ConfiguracionPrioridadAtencionController.class);

  @RequestMapping("/ConfiguracionPrioridadAtencion")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes)
      throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes,
        ViewNames.ID_CONFIGURACION_PRIORIDAD_ATENCION);
    if (sesion.getAttribute("usuario") != null) {
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
      // mav.addObject("listaProce", getProce.getProcedencias());
      mav.addObject("listaTiposDocumentos", tiposDocumentosDAO.getTiposDocumentos());
      mav.addObject("listaMedicos", med.getAllMedics());
      // mav.addObject("listaPrior", prior.getPrior());
      mav.addObject("listaTipoAt", tipoAt.getTipoAtencion());
      mav.addObject("listaDiag", getDiag.getDiagnosticos());
    }
    // mav.setViewName("ConfiguracionPrioridadAtencion");

    // aqui se inserta la accion que realiza usuario en modulos ***********

    if (usuario != null) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(11);
      leu.setLeuIdacciondato(0);
      leu.setLeuValornuevo("accede a Configuración de prioridades de atención");
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-************************************************************

    return mav;
    // mav.addObject("listaServicios", getServicios.getServicios());
    // testeoJasper jas = new testeoJasper();
    // jas.pdf();
    // this.getRpt1(response);
    // this.getHelloWorldReport();
  }

  @RequestMapping(value = "/ConfiguracionPrioridadAtencion", method = RequestMethod.POST)
  public ModelAndView registroPrioridadAtencion(CfgPrioridadatencion prio, @RequestParam("imagen") MultipartFile imagen,
      MultipartHttpServletRequest request, RedirectAttributes attributes) throws SQLException, IOException {
    try {
      HttpSession sesion = (HttpSession) request.getSession();
      DatosUsuarios usuario = new DatosUsuarios();
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      String descripcion = request.getParameter("cpaDescripcion");
      prio.setCpaDescripcion(descripcion.toUpperCase());
      byte[] bytes = imagen.getBytes();
      Blob myblob = null;
      myblob = new SerialBlob(bytes);
      prio.setCpaIconoprioridad(myblob);
      String color = prio.getCpaColorprioridad();
      prio.setCpaColorprioridad(color);
      // String nOrden = request.getParameter("dfOrdenParaAsignarAdentro");
      CfgPrioridadAtencionDAO prioAte = new CfgPrioridadAtencionDAO();
      prioAte.insertPrio(prio, usuario.getDuIdusuario());
      attributes.addFlashAttribute("mensaje", "Prioridad de atención ingresada correctamente");
    } catch (BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      attributes.addFlashAttribute("mensajeError", ex.getMessage());
    }
    return new ModelAndView("redirect:/" + ViewNames.CONFIGURACION_PRIORIDAD_ATENCION);

  }

  @RequestMapping(value = "/ConfiguracionPrioridadAtencion", method = RequestMethod.POST, params = "BusquedaPrioAteidRellenarDatos")
  public void selectPoridRellenarDatos(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException, Exception {

    try {

      CfgPrioridadAtencionDAO prioAte = new CfgPrioridadAtencionDAO();
      List<CfgPrioridadatencion> listaPrio = prioAte
          .getPrioAtencionid(Short.parseShort(request.getParameter("BusquedaPrioAteidRellenarDatos")));

      if (listaPrio.isEmpty()) {

        PrintWriter writer = response.getWriter();
        writer.print("0");
        return;
      }

      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (CfgPrioridadatencion prioridad : listaPrio) {
        JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
        JsonObject comunaJson;

        comunaBuilder.add("id", prioridad.getCpaIdprioridadatencion()).add("prioAtencion", prioridad.getCpaPrioridad())
            .add("activo", prioridad.getCpaActivo()).add("sort", prioridad.getCpaSort())
            .add("color", prioridad.getCpaColorprioridad()).add("descripcion", prioridad.getCpaDescripcion());
        if (prioridad.getCpaIconoprioridad() != null) {
          byte[] bdata = prioridad.getCpaIconoprioridad().getBytes(1, (int) prioridad.getCpaIconoprioridad().length());
          byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
          String base64Encoded = new String(encodeBase64, "UTF-8");
          comunaBuilder.add("imagen", base64Encoded);
        }

        comunaJson = comunaBuilder.build();
        arrayBuilder.add(comunaJson);
      }
      JsonObject root = rootBuilder.add("Prioridad", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    } catch (BiosLISDAOException | IOException | NumberFormatException | SQLException ex) {
      logger.error(ex.getMessage());
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    }

  }

}
