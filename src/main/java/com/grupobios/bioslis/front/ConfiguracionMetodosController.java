/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgMetodosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgMetodos;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author Nacho
 */
@Controller
public class ConfiguracionMetodosController {

  private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(RegistroMedicoController.class);
  ModelAndView mav = new ModelAndView();


  @RequestMapping("/ConfiguracionMetodos")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes,
        ViewNames.ID_CONFIGURACION_METODOS);

    // aqui se inserta la accion que realiza usuario en modulos ***********
    if (usuario != null) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(11);
      leu.setLeuIdacciondato(0);
      leu.setLeuValornuevo("accede a Configuración de métodos");
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-************************************************************
    return mav;
  }


    @RequestMapping(value = "/ConfiguracionMetodos", method = RequestMethod.POST)
    public ModelAndView agregarMetodo(CfgMetodos cmet, HttpServletRequest request, RedirectAttributes attributes) {
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String estado = request.getParameter("cmetActivo");
            CfgMetodosDAO cmetDAO = new CfgMetodosDAO();
            cmet.setCmetActivo(estado != null ? estado : "N");
            String msj = cmetDAO.insertMetodos(cmet, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Metodo ingresada correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Metodo Repetida");
			}
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_METODOS);
    }

    @RequestMapping(value = "/ConfiguracionMetodos", method = RequestMethod.POST, params = "update")
    public ModelAndView updateMetodo(CfgMetodos cmet, HttpServletRequest request, RedirectAttributes attributes) {
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String estado = request.getParameter("cmetActivo");
            int idMetodo = Integer.parseInt(request.getParameter("idMetodo"));
            CfgMetodosDAO cmetDAO = new CfgMetodosDAO();
            cmet.setCmetIdmetodo(idMetodo);
            cmet.setCmetActivo(estado != null ? estado : "N");
            String msj = cmetDAO.updateMetodos(cmet, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Metodo ingresada correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Metodo Repetida");
			}
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_METODOS);
    }


  @RequestMapping(value = "/ConfiguracionMetodos", method = RequestMethod.POST, params = "filtro")
  public void filtroMetodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String nombre = request.getParameter("nombre");
      String activo = request.getParameter("activo");
      nombre = StringUtils.stripAccents(nombre).toUpperCase().trim();
      PrintWriter writer = response.getWriter();
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      CfgMetodosDAO cmetDAO = new CfgMetodosDAO();
      List<CfgMetodos> listaMetodos = cmetDAO.getMetodosbyLike(nombre, activo);
      for (CfgMetodos metodo : listaMetodos) {
        JsonObjectBuilder metodoBuilder = Json.createObjectBuilder();
        JsonObject metodoJson;
        metodoBuilder.add("NombreMetodo", metodo.getCmetDescripcion()).add("IdMetodo", metodo.getCmetIdmetodo())
            .add("EstadoMetodo", metodo.getCmetActivo())
    		.add("ColorMetodo", metodo.getCmetColor());
        metodoJson = metodoBuilder.build();
        arrayBuilder.add(metodoJson);
      }
      JsonObject root = rootBuilder.add("metodo", arrayBuilder).build();
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
