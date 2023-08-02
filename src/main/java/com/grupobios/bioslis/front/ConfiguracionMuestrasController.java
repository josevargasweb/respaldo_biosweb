/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigFactory;
import com.grupobios.bioslis.bs.ConfigPrefijosService;
import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgGruposMuestrasDAO;
import com.grupobios.bioslis.dao.CfgMuestrasDAO;
import com.grupobios.bioslis.dao.CfgMuestrasPrefijosDAO;
import com.grupobios.bioslis.dao.LogCfgTablasDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgGruposmuestras;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgMuestrasprefijos;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author marco.c
 */
@Controller
public class ConfiguracionMuestrasController {

	private static final org.apache.logging.log4j.Logger logger = LogManager
		      .getLogger(ConfiguracionMuestrasController.class);
		  private static final ConfigPrefijosService CONFIG_PREFIJOS = ConfigFactory.create();
		  ModelAndView mav = new ModelAndView();

		  @RequestMapping("/ConfiguracionMuestras")
		  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
		      RedirectAttributes attributes) {
		    HttpSession sesion = (HttpSession) request.getSession();
		    DatosUsuarios usuario = new DatosUsuarios();
		    usuario = (DatosUsuarios) sesion.getAttribute("usuario");

		    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes,
		        ViewNames.ID_CONFIGURACION_MUESTRAS);
		    if (sesion.getAttribute("usuario") != null) {
		      try {
		        String prefijoAsignado = CONFIG_PREFIJOS.asignarPrefijo();
		        List<Object[]> listaPrefijosMuestras = CONFIG_PREFIJOS.listarPrefijosMuestras();
		        CfgGruposMuestrasDAO cgmDao = new CfgGruposMuestrasDAO();
		        // List<Object[]> listaGruposMuestras = cgmDao.getGruposMuestras();
		        CfgMuestrasDAO cmDao = new CfgMuestrasDAO();
		        List<Object[]> listaMuestras = cmDao.getListaMuestras();

		        mav.addObject("listaPrefijosMuestras", listaPrefijosMuestras);
		        // mav.addObject("listaGruposMuestras", listaGruposMuestras);
		        mav.addObject("listaMuestras", listaMuestras);
		        mav.addObject("prefijo", prefijoAsignado);
		      } catch (BiosLISDAOException ex) {
		        logger.error(ex.getMessage());
		        attributes.addFlashAttribute("mensajeError", "Error: " + ex.getMessage());
		      }
		    }

		    // aqui se inserta la accion que realiza usuario en modulos ***********
		    if (usuario != null) {
		      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
		      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();

		      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
		      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
		      leu.setLeuIdevento(11);
		      leu.setLeuIdacciondato(0);
		      leu.setLeuValornuevo("accede a Configuración de muestras");
		      logUsuarioDao.insertLogEventosUsuario(leu);
		    }
		    // *-************************************************************

		    return mav;
		  }

		  @RequestMapping(value = "/ConfiguracionMuestras", method = RequestMethod.POST, params = "filtroId")
		  public void getTipoMuestrasFiltro(HttpServletRequest request, HttpServletResponse response) throws IOException {
		    try {
		      String idTipoMuestra = request.getParameter("idTipoMuestra");

		      CfgMuestrasDAO cmueDao = new CfgMuestrasDAO();
		      CfgMuestras cm = cmueDao.getMuestraById(Short.parseShort(idTipoMuestra));

		      PrintWriter writer = response.getWriter();
		      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
		      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		      JsonObjectBuilder muestraBuilder = Json.createObjectBuilder();
		      JsonObject muestraJson;

		      muestraBuilder.add("id", cm.getCmueIdtipomuestra())
		          .add("descripcion", (cm.getCmueDescripcion() == null) ? "" : cm.getCmueDescripcion())
		          .add("descripcionabr", (cm.getCmueDescripcionabreviada() == null) ? "" : cm.getCmueDescripcionabreviada())
		          .add("orden", cm.getCmueSort() == null ? 1 : cm.getCmueSort())
		          .add("prefijo", (cm.getCmuePrefijotipomuestra() == null) ? "" : cm.getCmuePrefijotipomuestra())
		          .add("host", (cm.getCmueHost() == null) ? "" : cm.getCmueHost())
		          .add("hostmicro", (cm.getCmueHostmicro() == null) ? "" : cm.getCmueHostmicro())
		          .add("microbiologia", (cm.getCmueEsmicrobiologia() == null) ? "N" : cm.getCmueEsmicrobiologia())
		          .add("multimuestra", (cm.getCmueEsmultimuestra() == null) ? "N" : cm.getCmueEsmultimuestra())
		          .add("pesquisa", (cm.getCmueEspesquisa() == null) ? "N" : cm.getCmueEspesquisa())
		          .add("tipocurva", (cm.getCmueEstipocurva() == null) ? "N" : cm.getCmueEstipocurva())
		          .add("curvasmin", (cm.getCmueCurvasminutos() == null) ? 0 : cm.getCmueCurvasminutos())
		          .add("color", (cm.getCmueColor() == null) ? "#FFFFFF" : cm.getCmueColor())
		          // .add("grupo", cm.getCfgGruposmuestras().getCgmIdgrupomuestra())
		          .add("activo", cm.getCmueActivo());

		      muestraJson = muestraBuilder.build();
		      arrayBuilder.add(muestraJson);

		      JsonObject root = rootBuilder.add("muestra", arrayBuilder).build();
		      writer.print(root);
		    } catch (BiosLISDAOException ex) {
		      logger.error(ex.getMessage());
		      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
		      JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
		      PrintWriter writer = response.getWriter();
		      writer.print(root);
		    }
		  }

		  @RequestMapping(value = "/ConfiguracionMuestras", method = RequestMethod.POST)
		  public ModelAndView agregarTipoMuestra(CfgMuestras cmue, HttpServletRequest request, RedirectAttributes attributes) {
		    try {
		    	 HttpSession sesion = (HttpSession) request.getSession();
				    DatosUsuarios usuario = new DatosUsuarios();
				    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		      String descripcion = request.getParameter("cmueDescripcion").toUpperCase();
		      String descripcionAbreviada = request.getParameter("cmueDescripcionabreviada").toUpperCase();
		      String prefijo = request.getParameter("cmuePrefijotipomuestra").toUpperCase();
		      String idGrupo = request.getParameter("grupoMuestras");
		      String orden = request.getParameter("cmueSort");
		      String ip = request.getParameter("ipEquipo");

		      CfgMuestrasDAO cmueDao = new CfgMuestrasDAO();
		      // BigDecimal newId = cmueDao.obtenerNuevoId();

		      CfgGruposmuestras cgm = new CfgGruposmuestras();
		      cgm.setCgmIdgrupomuestra(Short.parseShort(idGrupo));

		      // cmue.setCmueIdtipomuestra(newId.shortValue());
		      cmue.setCmueDescripcion(descripcion);
		      cmue.setCmueDescripcionabreviada(descripcionAbreviada);
		      cmue.setCmuePrefijotipomuestra(prefijo);
		      // cmue.setCfgGruposmuestras(cgm);
		      cmue.setCmueSort((orden == null || Short.parseShort(orden) < 1) ? 1 : Short.parseShort(orden));
		      if (cmue.getCmueActivo() == null)
		        cmue.setCmueActivo("N");
		      if (cmue.getCmueEsmicrobiologia() == null)
		        cmue.setCmueEsmicrobiologia("N");
		      if (cmue.getCmueEstipocurva() == null)
		        cmue.setCmueEstipocurva("N");
		      if ("N".equals(cmue.getCmueEstipocurva()))
		        cmue.setCmueCurvasminutos(Short.valueOf("0"));

		      CfgMuestrasprefijos cmp = new CfgMuestrasprefijos();

		      String msj = cmueDao.agregarMuestra(cmue, usuario.getDuIdusuario());
		      cmp.setCmuepIdtipomuestra(cmue.getCmueIdtipomuestra());
		      cmp.setCmuepPrefijotipomuestra(prefijo);
		      CfgMuestrasPrefijosDAO cmpdao = new CfgMuestrasPrefijosDAO();
		      cmpdao.insertPrefijo(cmp);

		      // Log comentado por cristian el 10-05  ya no se utiliza este metodo
		      /*
		      LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
		      try {
		        logTablas.insertLog("Nuevo Tipo de Muestra", "", "", "CFG_MUESTRAS", 1, 1, "Equipo",
		            (int) cmue.getCmueIdtipomuestra(), ip);
		      } catch (ParseException ex) {
		        Logger.getLogger(ConfiguracionMuestrasController.class.getName()).log(Level.SEVERE, null, ex);
		      }
		      */
		      if (msj.equals("C")) {
		        attributes.addFlashAttribute("mensaje", "Muestra ingresada correctamente");
		      } else {
		        attributes.addFlashAttribute("mensajeError", "Muestra Repetida");
		      }

		    } catch (BiosLISDAOException ex) {
		      logger.error(ex.getMessage());
		      attributes.addFlashAttribute("mensajeError", ex.getMessage());
		    }

		    return new ModelAndView("redirect:/ConfiguracionMuestras");

		  }

		  @RequestMapping(value = "/ConfiguracionMuestras", method = RequestMethod.POST, params = "update")
		  public ModelAndView actualizarTipoMuestra(CfgMuestras cmue, HttpServletRequest request,
		      RedirectAttributes attributes) {
		    try {
		    	 HttpSession sesion = (HttpSession) request.getSession();
				    DatosUsuarios usuario = new DatosUsuarios();
				    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		      String idTipoMuestra = request.getParameter("idTipoMuestra");
		      String descripcion = request.getParameter("cmueDescripcion").toUpperCase();
		      String descripcionAbreviada = request.getParameter("cmueDescripcionabreviada").toUpperCase();
		      String prefijo = request.getParameter("cmuePrefijotipomuestra").toUpperCase();
		      String idGrupo = request.getParameter("grupoMuestras");
		      String orden = request.getParameter("cmueSort");
		      String ip = request.getParameter("ipEquipo");

		      CfgGruposmuestras cgm = new CfgGruposmuestras();
		      cgm.setCgmIdgrupomuestra(Short.parseShort(idGrupo));

		      cmue.setCmueIdtipomuestra(Short.parseShort(idTipoMuestra));
		      cmue.setCmueDescripcion(descripcion);
		      cmue.setCmueDescripcionabreviada(descripcionAbreviada);
		      cmue.setCmuePrefijotipomuestra(prefijo);
		      // cmue.setCfgGruposmuestras(cgm);
		      cmue.setCmueSort((orden == null) ? 1 : Short.parseShort(orden));
		      if (cmue.getCmueActivo() == null)
		        cmue.setCmueActivo("N");
		      if (cmue.getCmueEsmicrobiologia() == null)
		        cmue.setCmueEsmicrobiologia("N");
		      if (cmue.getCmueEstipocurva() == null)
		        cmue.setCmueEstipocurva("N");
		      if ("N".equals(cmue.getCmueEstipocurva()))
		        cmue.setCmueCurvasminutos(Short.valueOf("0"));

		      CfgMuestrasDAO cmueDao = new CfgMuestrasDAO();
		      String msj = cmueDao.actualizarTipoMuestra(cmue, usuario.getDuIdusuario());

		      // CfgMuestras cmueOld =
		      // cmueDao.getMuestraById(Short.parseShort(idTipoMuestra));

		      // LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
		      // logTablas.comparadorObjetos(cmueOld, cmue, "CFG_MUESTRAS", 2, 1, "Equipo",
		      // Short.parseShort(idTipoMuestra), ip);
		      if (msj.equals("C")) {
		        attributes.addFlashAttribute("mensaje", "Muestra actualizada correctamente");
		      } else {
		        attributes.addFlashAttribute("mensajeError", "Muestra Repetida");
		      }

		    } catch (BiosLISDAOException ex) {
		      logger.error(ex.getMessage());
		      attributes.addFlashAttribute("mensajeError", ex.getMessage());
		    }
		    return new ModelAndView("redirect:/ConfiguracionMuestras");
		  }

		  @RequestMapping(value = "/ConfiguracionMuestras", method = RequestMethod.POST, params = "filtroEx")
		  public void getExamenesMuestra(HttpServletRequest request, HttpServletResponse response) throws IOException {
		    try {
		      String idMuestra = request.getParameter("idMuestra");
		      String prefijo = request.getParameter("prefijo").toUpperCase();
		      PrintWriter writer = response.getWriter();
		      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
		      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		      CfgExamenesDAO ceDao = new CfgExamenesDAO();

		      if (idMuestra.length() > 0) {
		        arrayBuilder = ceDao.getExamenesMuestraById(Short.parseShort(idMuestra));
		      } else {
		        arrayBuilder = ceDao.getExamenesMuestraLikePrefijo(prefijo);

		      }

		      JsonObject root = rootBuilder.add("examen", arrayBuilder).build();
		      writer.print(root);
		    } catch (BiosLISDAOException ex) {
		      logger.error(ex.getMessage());
		      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
		      JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
		      PrintWriter writer = response.getWriter();
		      writer.print(root);
		    }
		  }
		}
