/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgDerivadoresDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

/**
 *
 * @author Marco Caracciolo
 */
@Controller
public class ConfiguracionDerivadoresController {
    
    ModelAndView mav = new ModelAndView();
    DatosUsuarios usuario = new DatosUsuarios();
    CfgDerivadoresDAO cdDAO = new CfgDerivadoresDAO();
    private static final Logger LOGGER = LogManager.getLogger(ConfiguracionDerivadoresController.class);
    
    @RequestMapping("/ConfiguracionDerivadores")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_CONFIGURACION_DERIVADORES);
        
        //aqui se inserta la accion que realiza usuario en modulos ***********            
        
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Configuración de derivadores");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        return mav;
    }
    
    @RequestMapping(value ="/ConfiguracionDerivadores", method = RequestMethod.POST)
    public ModelAndView agregarDerivador(CfgDerivadores cd, HttpServletRequest request, RedirectAttributes attributes) {
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String codigo = request.getParameter("cderivCodigo").toUpperCase();
            String nombre = request.getParameter("cderivDescripcion").toUpperCase();
            cd.setCderivCodigo(codigo);
            cd.setCderivDescripcion(nombre);
            if (cd.getCderivActivo() == null){
                cd.setCderivActivo("N");
            }
            String msj = cdDAO.agregarDerivador(cd, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Derivador ingresado correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Derivador existente");
			}
            
        } catch (BiosLISDAOException ex) {
            LOGGER.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_DERIVADORES);
    }
    
    @RequestMapping(value ="/ConfiguracionDerivadores", method = RequestMethod.POST, params = "update")
    public ModelAndView actualizarDerivador(CfgDerivadores cd, HttpServletRequest request, RedirectAttributes attributes) {
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            Short idDerivador = Short.parseShort(request.getParameter("idDerivador"));
            String codigo = request.getParameter("cderivCodigo").toUpperCase();
            String nombre = request.getParameter("cderivDescripcion").toUpperCase();
            cd.setCderivIdderivador(idDerivador);
            cd.setCderivCodigo(codigo);
            cd.setCderivDescripcion(nombre);
            if (cd.getCderivActivo() == null){
                cd.setCderivActivo("N");
            }
            String msj = cdDAO.actualizarDerivador(cd, usuario.getDuIdusuario());
            if (msj.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Derivador ingresado correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Derivador existente");
			}
        } catch (BiosLISDAOException ex) {
            LOGGER.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_DERIVADORES);
    }

    @RequestMapping(value = "/ConfiguracionDerivadores", method = RequestMethod.POST, params = "buscarPorId")
    public void getDerivadoresById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Short idDerivador = Short.parseShort(request.getParameter("idDerivador"));
            CfgDerivadores deriv = cdDAO.getDerivadorById(idDerivador);
            
            PrintWriter writer = response.getWriter();
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            
            JsonObjectBuilder derivadorBuilder = Json.createObjectBuilder();
            JsonObject derivadorJson;
            derivadorBuilder.add("Id", deriv.getCderivIdderivador())
                    .add("Codigo", deriv.getCderivCodigo())
                    .add("Nombre", deriv.getCderivDescripcion())
                    .add("Orden", (deriv.getCderivSort()!=null && deriv.getCderivSort()>0) ? deriv.getCderivSort() : 1)
                    .add("Host", (deriv.getCderivHost()!=null) ? deriv.getCderivHost() : "")
                    .add("HorarioAtencion", (deriv.getCderivHorarioatencion()!=null) ? deriv.getCderivHorarioatencion() : "")
                    .add("Telefono", (deriv.getCderivTelefono()!=null) ? deriv.getCderivTelefono() : "")
                    .add("Direccion", (deriv.getCderivDireccion()!=null) ? deriv.getCderivDireccion() : "")
                    .add("Email", (deriv.getCderivEmail()!=null) ? deriv.getCderivEmail() : "")
                    .add("Encargado", (deriv.getCderivNombreencargado()!=null ) ? deriv.getCderivNombreencargado() : "")
                    .add("Activo", (deriv.getCderivActivo()!=null) ? deriv.getCderivActivo() : "N");
            derivadorJson = derivadorBuilder.build();
            arrayBuilder.add(derivadorJson);
            
            JsonObject root = rootBuilder.add("derivadores", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException | IOException ex) {
            LOGGER.error(ex.getMessage());
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        }
    }

    public DatosUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(DatosUsuarios usuario) {
        this.usuario = usuario;
    }

    public CfgDerivadoresDAO getCdDAO() {
        return cdDAO;
    }

    public void setCdDAO(CfgDerivadoresDAO cdDAO) {
        this.cdDAO = cdDAO;
    }
    
    
}
