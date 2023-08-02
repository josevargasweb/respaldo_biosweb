/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

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

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisLogger;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCentrosDeSaludDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgServicios;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author marco caracciolo
 */
@Controller
public class ConfiguracionServiciosController {

  ModelAndView mav = new ModelAndView();
  DatosUsuarios usuario = new DatosUsuarios();
  private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ConfiguracionServiciosController.class);

  @RequestMapping("/ConfiguracionServicios")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_CONFIGURACION_SERVICIOS);
    
    //aqui se inserta la accion que realiza usuario en modulos ***********            
    
    if(usuario != null) {
    	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
    	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
    	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	    leu.setLeuIdevento(11);              
	    leu.setLeuIdacciondato(0);               
	    leu.setLeuValornuevo("accede a Configuración de servicios");
	    logUsuarioDao.insertLogEventosUsuario(leu);
    }
   //*-************************************************************ 
    return mav;
  }
/*
  @RequestMapping(value = "/ConfiguracionServicios", method = RequestMethod.POST)
  public ModelAndView agregarServicio(CfgServicios cs, HttpServletRequest request, RedirectAttributes attributes)
      throws IllegalArgumentException, IllegalAccessException {
      try {
          String codigo = request.getParameter("csCodigo");
          String descripcion = request.getParameter("csDescripcion");
          String sort = request.getParameter("csSort");
          String centroMedico = request.getParameter("centroMedico");
          String ip = request.getParameter("ipEquipo");
          cs.setCsCodigo(codigo.toUpperCase());
          cs.setCsDescripcion(descripcion.toUpperCase());
          // setea los valores en caso de que no se active
          if (cs.getCsActivo() == null)
              cs.setCsActivo("N");
          if (cs.getCsUrgente() == null)
              cs.setCsUrgente("N");
          if (cs.getCsIndicador() == null)
              cs.setCsIndicador("N");
          
          if (sort == null || Integer.parseInt(sort)<1){
              sort = "1";
          }
          cs.setCsSort(Integer.parseInt(sort));
          
          CfgCentrosDeSaludDAO ccdsdao = new CfgCentrosDeSaludDAO();
          CfgCentrosdesalud cfgCentrosdesalud = ccdsdao.getCentrosDeSaludById(Byte.parseByte(centroMedico));
          cs.setCfgCentrosdesalud(cfgCentrosdesalud);
          
          CfgServiciosDAO csDao = new CfgServiciosDAO();
          String mensaje = csDao.agregarServicio(cs);
          // Log
          BiosLisLogger bl = new BiosLisLogger();
          bl.logCfgInsertTableRecord(CfgServicios.class, cs, new BigDecimal(1L), new BigDecimal(usuario.getDuIdusuario()),
                  BiosLisCalendarService.getInstance().getTS(), new BigDecimal(cs.getCsIdservicio()));
          attributes.addFlashAttribute("mensaje", mensaje);
      } catch (BiosLISDAOException ex) {
          logger.error(ex.getMessage());
          attributes.addFlashAttribute("mensajeError", ex.getMessage());
      }
      return new ModelAndView("redirect:/" + ViewNames.CONFIGURACION_SERVICIOS);
  }*/

  @RequestMapping(value = "/ConfiguracionServicios", method = RequestMethod.POST, params = "filtroId")
  public void buscarServicioById(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) throws IOException {
      try {
          String idServicio = request.getParameter("idServicio");
          
          CfgServiciosDAO csDAO = new CfgServiciosDAO();
          CfgServicios cs = csDAO.getServiciosById(Integer.parseInt(idServicio));
          
          PrintWriter writer = response.getWriter();
          JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
          JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
          
          JsonObjectBuilder servicioBuilder = Json.createObjectBuilder();
          JsonObject serviceJson;
          servicioBuilder.add("IdServicio", cs.getCsIdservicio()).add("Codigo", cs.getCsCodigo())
                  .add("Descripcion", cs.getCsDescripcion()).add("CodCentro", cs.getCfgCentrosdesalud().getCcdsIdcentrodesalud())
                  .add("Orden", (cs.getCsSort() >= 0) ? cs.getCsSort() : 1)
                  .add("Email", (cs.getCsEmail() != null) ? cs.getCsEmail() : "")
                  //.add("Email", (cs.getCsEmail()))
                  .add("Host1", (cs.getCsHost() != null) ? cs.getCsHost() : "")
                  .add("Host2", (cs.getCsHost2() != null) ? cs.getCsHost2() : "")
                  .add("Host3", (cs.getCsHost3() != null) ? cs.getCsHost3() : "")
                  .add("HostMicro", (cs.getCsHostmicro() != null) ? cs.getCsHostmicro() : "")
                  .add("Activo", (cs.getCsActivo() != null) ? cs.getCsActivo() : "N")
                  .add("Urgente", (cs.getCsUrgente() != null) ? cs.getCsUrgente() : "N")
                  .add("Indicador", (cs.getCsIndicador() != null) ? cs.getCsIndicador() : "N");
          
          serviceJson = servicioBuilder.build();
          arrayBuilder.add(serviceJson);
          
          JsonObject root = rootBuilder.add("servicio", arrayBuilder).build();
          writer.print(root);
      } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
      }
  }
/*
  @RequestMapping(value = "/ConfiguracionServicios", method = RequestMethod.POST, params = "update")
  public ModelAndView actualizarServicio(CfgServicios cs, HttpServletRequest request, RedirectAttributes attributes) {
      try {
          Integer idServicio = Integer.parseInt(request.getParameter("idServicio"));
          String codigo = request.getParameter("csCodigo");
          String descripcion = request.getParameter("csDescripcion");
          String centroMedico = request.getParameter("centroMedico");
          String sort = request.getParameter("csSort");
          String ip = request.getParameter("ipEquipo");
          cs.setCsIdservicio(idServicio);
          cs.setCsCodigo(codigo.toUpperCase());
          cs.setCsDescripcion(descripcion.toUpperCase());
          if (sort == null || Integer.parseInt(sort)<1){
              sort = "1";
          }
          cs.setCsSort(Integer.parseInt(sort));
          CfgServiciosDAO csDao = new CfgServiciosDAO();
          
          // setea los valores en caso de que no se active
          if (cs.getCsActivo() == null)
              cs.setCsActivo("N");
          if (cs.getCsUrgente() == null)
              cs.setCsUrgente("N");
          if (cs.getCsIndicador() == null)
              cs.setCsIndicador("N");
          
          // CfgCentrosdesalud newCds = new CfgCentrosdesalud();
          // cs.setCfgCentrosdesalud(newCds);
          
          CfgCentrosDeSaludDAO ccdsdao = new CfgCentrosDeSaludDAO();
          CfgCentrosdesalud cfgCentrosdesalud = ccdsdao.getCentrosDeSaludById(Byte.parseByte(centroMedico));
          cs.setCfgCentrosdesalud(cfgCentrosdesalud);
          
          String mensaje = csDao.actualizarServicio(cs);
         
          
          // Log
          BiosLisLogger bl = new BiosLisLogger();
          bl.logCfgInsertTableRecord(CfgServicios.class, cs, new BigDecimal(2L), new BigDecimal(usuario.getDuIdusuario()),
                  BiosLisCalendarService.getInstance().getTS(), new BigDecimal(idServicio));
          
          attributes.addFlashAttribute("mensaje", mensaje);
          
      } catch (BiosLISDAOException | IllegalArgumentException | IllegalAccessException ex) {
          logger.error(ex.getMessage());
          attributes.addFlashAttribute("mensajeError", ex.getMessage());
      }
      return new ModelAndView("redirect:/" + ViewNames.CONFIGURACION_SERVICIOS);
  }*/

}
