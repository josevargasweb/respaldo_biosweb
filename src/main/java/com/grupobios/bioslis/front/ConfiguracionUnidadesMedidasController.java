/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgUnidadesdemedidaDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgUnidadesdemedida;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Nacho
 */
@Controller
public class ConfiguracionUnidadesMedidasController {
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(RegistroMedicoController.class);
    ModelAndView mav = new ModelAndView();

    @RequestMapping("/ConfiguracionUnidadesMedidas")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_CONFIGURACION_UNIDADES_MEDIDAS);
        
      //aqui se inserta la accion que realiza usuario en modulos ***********    
        if(usuario != null) {       
	        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	        
	        leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	        leu.setLeuIdevento(11);              
	        leu.setLeuIdacciondato(0);               
	        leu.setLeuValornuevo("accede a Configuración de unidades de medida");
	        logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        return mav;
    }

    @RequestMapping(value = "/ConfiguracionUnidadesMedidas", method = RequestMethod.POST)
    public ModelAndView agregarUnidadMedida(CfgUnidadesdemedida cum, HttpServletRequest request, RedirectAttributes attributes) {
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String estado = request.getParameter("estado");
            cum.setCumActivo(estado);
            CfgUnidadesdemedidaDAO cumDAO = new CfgUnidadesdemedidaDAO();
            cumDAO.insertUnidadMedida(cum, usuario.getDuIdusuario());
            attributes.addFlashAttribute("mensaje", "Unidad de medida ingresado correctamente");
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/ConfiguracionUnidadesMedidas");
    }

    @RequestMapping(value = "/ConfiguracionUnidadesMedidas", method = RequestMethod.POST, params = "update")
    public ModelAndView actualizarUnidadMedida(CfgUnidadesdemedida cum, HttpServletRequest request, RedirectAttributes attributes) {
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String estado = request.getParameter("estado");
            cum.setCumActivo(estado);
            int idUnidad = Integer.parseInt(request.getParameter("idUnidad"));
            cum.setCumIdunidadmedida(idUnidad);
            CfgUnidadesdemedidaDAO cumDAO = new CfgUnidadesdemedidaDAO();
            cumDAO.updateUnidadMedida(cum, usuario.getDuIdusuario());
            attributes.addFlashAttribute("mensaje", "Unidad de medida actualizado correctamente");
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/ConfiguracionUnidadesMedidas");
    }

    @RequestMapping(value = "/ConfiguracionUnidadesMedidas", method = RequestMethod.POST, params = "filtro")
    public void buscarUnidadesMedida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigo");
        String descripcion = request.getParameter("descripcion");
        codigo = StringUtils.stripAccents(codigo).toUpperCase().trim();
        descripcion = StringUtils.stripAccents(descripcion).toUpperCase().trim();
        try {
            CfgUnidadesdemedidaDAO cumDAO = new CfgUnidadesdemedidaDAO();
            List<CfgUnidadesdemedida> listaUnidadesdemedida = new ArrayList<CfgUnidadesdemedida>();
            if (codigo.length() > 0) {
                listaUnidadesdemedida = cumDAO.getUnidadesMedidaLikeCodigo(codigo);
            
            } else {
                listaUnidadesdemedida = cumDAO.getUnidadesMedidaLikeDescripcion(descripcion);
            }
            PrintWriter writer = response.getWriter();
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (CfgUnidadesdemedida unidadesdemedida : listaUnidadesdemedida) {
                JsonObjectBuilder unidadBuilder = Json.createObjectBuilder();
                JsonObject unidadJson;
                unidadBuilder.add("IdUnidad", unidadesdemedida.getCumIdunidadmedida())
                        .add("DescripcionUnidad", unidadesdemedida.getCumDescripcion())
                        .add("CodigoUnidad", unidadesdemedida.getCumCodigo());
                unidadJson = unidadBuilder.build();
                arrayBuilder.add(unidadJson);
            }
            JsonObject root = rootBuilder.add("unidadmedida", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        }
    }

    @RequestMapping(value = "/ConfiguracionUnidadesMedidas", method = RequestMethod.POST, params = "getUnidad")
    public void getUnidadesMedida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int idUnidad = Integer.parseInt(request.getParameter("idUnidad"));
            CfgUnidadesdemedidaDAO cumDAO = new CfgUnidadesdemedidaDAO();
            CfgUnidadesdemedida unidadesdemedida = cumDAO.getUnidadesdemedidaById(idUnidad);
            PrintWriter writer = response.getWriter();
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            JsonObjectBuilder unidadBuilder = Json.createObjectBuilder();
            JsonObject unidadJson;
            unidadBuilder.add("IdUnidad", unidadesdemedida.getCumIdunidadmedida())
                    .add("DescripcionUnidad", unidadesdemedida.getCumDescripcion())
                    .add("CodigoUnidad", unidadesdemedida.getCumCodigo())
                    .add("EstadoUnidad", unidadesdemedida.getCumActivo());
            if (unidadesdemedida.getCumSort() != null) {
                unidadBuilder.add("OrdenUnidad", unidadesdemedida.getCumSort());
            }
            unidadJson = unidadBuilder.build();
            arrayBuilder.add(unidadJson);
            JsonObject root = rootBuilder.add("unidadmedida", arrayBuilder).build();
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
