/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.CapturaResultadosService;
import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.bs.DominiosService;
import com.grupobios.bioslis.bs.UsuariosModulosService;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.CfgEnvasesDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgGlosastestDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgEnvases;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgGlosastest;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author Nacho
 */
@Controller
public class CapturaResultadosController {

  public static final Integer RANGOULTIMOSDIAS = 15;
  private static final Logger logger = LogManager.getLogger(CapturaResultadosController.class);

  private CapturaResultadosService capturaResultadosService;
  private UsuariosModulosService usuariosModulosService;
  private DominiosService dominiosService;
  ModelAndView mav = new ModelAndView();

  @RequestMapping("/CapturaResultados")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes)
      throws BiosLISDAOException {
    HttpSession sesion = (HttpSession) request.getSession();

    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes,
        ViewNames.ID_CAPTURA_RESULTADOS);
    Long idUsuario = 0L;
    Long idSeccion = 0L;
    DatosUsuarios usuario = null;

    if (sesion.getAttribute("usuario") != null) {
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      idUsuario = usuario.getDuIdusuario();
      idSeccion = (long) usuariosModulosService.getPerfilUsuario(idUsuario).getCpuIdseccion();
      usuariosModulosService.getPerfilUsuario(idUsuario);
      try {
        dominiosService.getSeccionById(idSeccion);
      } catch (BiosLISDAOException ex) {
        java.util.logging.Logger.getLogger(CapturaResultadosController.class.getName()).log(Level.SEVERE, null, ex);
      }
      mav.addObject("idUsuarioConectado", idUsuario);
      mav.addObject("idSeccion", idSeccion);

    }
    // aqui se inserta la accion que realiza usuario en modulos ***********

    if (usuario != null) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(11);
      leu.setLeuIdacciondato(0);
      leu.setLeuValornuevo("accede a Captura resultados");
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-************************************************************

    return mav;
  }

  @RequestMapping(value = "/CapturaResultados", method = RequestMethod.POST)
  public ModelAndView agregar() {
    return mav;
  }

  @RequestMapping(value = "/CapturaResultados", method = RequestMethod.POST, params = "getExamenes")
  public void getExamenesByOrden(HttpServletRequest request, HttpServletResponse response)
      throws IOException, BiosLISDAONotFoundException {
    int norden = Integer.parseInt(request.getParameter("norden"));
    DatosFichasExamenesDAO dfeDAO = new DatosFichasExamenesDAO();
    CfgExamenesDAO ceDAO = new CfgExamenesDAO();
    List<Object[]> listaExamenes = dfeDAO.getDatosFichasexamenesByNroOrdenMalo2(norden);
    PrintWriter writer = response.getWriter();
    JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for (Object[] idexamen : listaExamenes) {
      try {
        BigDecimal id = (BigDecimal) idexamen[0];
        BigDecimal idEnvase = (BigDecimal) idexamen[1];
        CfgExamenes examen = ceDAO.getExamenById(id.intValue());
        CfgEnvasesDAO cenvDAO = new CfgEnvasesDAO();
        CfgEnvases cenv = new CfgEnvases();
        cenv = cenvDAO.getEnvaseById(idEnvase.intValue());
        JsonObjectBuilder examenBuilder = Json.createObjectBuilder();
        JsonObject examenJson;
        examenBuilder.add("examen", examen.getCeDescripcion())
            .add("Muestra", examen.getCfgMuestras().getCmueDescripcion())
            .add("Seccion", examen.getCfgSecciones().getCsecDescripcion()).add("Urgente", examen.getCeEsurgente())
            .add("Envase", cenv.getCenvDescripcion());
        examenJson = examenBuilder.build();
        arrayBuilder.add(examenJson);
      } catch (BiosLISDAOException ex) {
        java.util.logging.Logger.getLogger(CapturaResultadosController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    // HASTA ACA
    JsonObject root = rootBuilder.add("examen", arrayBuilder).build();
    // root = rootBuilder.add("patologias", arrayBuilderPatologias).build();

    writer.print(root);
  }

  @RequestMapping(value = "/CapturaResultados", method = RequestMethod.POST, params = "getTest")
  public void getExamenesTestByOrden(HttpServletRequest request, HttpServletResponse response)
      throws IOException, BiosLISDAONotFoundException, BiosLISDAOException {
    int norden = Integer.parseInt(request.getParameter("idExamen"));
    DatosFichasExamenesDAO dfeDAO = new DatosFichasExamenesDAO();
    CfgExamenesDAO ceDAO = new CfgExamenesDAO();
    List<BigDecimal> listaExamenes = dfeDAO.getDatosFichasexamenesByNroOrdenMalo(norden);
    PrintWriter writer = response.getWriter();
    JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for (BigDecimal idexamen : listaExamenes) {
      CfgExamenes examen = ceDAO.getExamenById(idexamen.intValue());
      JsonObjectBuilder examenBuilder = Json.createObjectBuilder();
      JsonObject examenJson;
      examenBuilder.add("examen", examen.getCeDescripcion());
      examenJson = examenBuilder.build();
      arrayBuilder.add(examenJson);
    }
    // HASTA ACA
    JsonObject root = rootBuilder.add("examen", arrayBuilder).build();
    // root = rootBuilder.add("patologias", arrayBuilderPatologias).build();

    writer.print(root);
  }

  @RequestMapping(value = "/CapturaResultados", method = RequestMethod.POST, params = "getGlosas")
  public void getGlosaByTest(HttpServletRequest request, HttpServletResponse response)
      throws IOException, BiosLISDAOException {
    int idTest = Integer.parseInt(request.getParameter("idTest"));
    CfgTest ct = new CfgTest();
    ct.setCtIdtest(idTest);
    CfgGlosastestDAO cgtDAO = new CfgGlosastestDAO();
    List<CfgGlosastest> listaGlosas = cgtDAO.getGlosasByTest(ct);
    PrintWriter writer = response.getWriter();
    JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for (CfgGlosastest glosa : listaGlosas) {
      JsonObjectBuilder glosaBuilder = Json.createObjectBuilder();
      JsonObject glosaJson;
      glosaBuilder.add("Descripcion", glosa.getCfgGlosas().getCgDescripcion());
      glosaJson = glosaBuilder.build();
      arrayBuilder.add(glosaJson);
    }
    // HASTA ACA
    JsonObject root = rootBuilder.add("Glosa", arrayBuilder).build();
    writer.print(root);
  }

  public CapturaResultadosService getCapturaResultadosService() {
    return capturaResultadosService;
  }

  public void setCapturaResultadosService(CapturaResultadosService capturaResultadosService) {
    this.capturaResultadosService = capturaResultadosService;
  }

  public UsuariosModulosService getUsuariosModulosService() {
    return usuariosModulosService;
  }

  public void setUsuariosModulosService(UsuariosModulosService usuariosModulosService) {
    this.usuariosModulosService = usuariosModulosService;
  }

  public DominiosService getDominiosService() {
    return dominiosService;
  }

  public void setDominiosService(DominiosService dominiosService) {
    this.dominiosService = dominiosService;
  }

}
