package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.bs.SessionService;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.LdvIndicacionesExamenesDAO;
import com.grupobios.bioslis.dao.LdvIndicacionesTMDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;
import com.grupobios.bioslis.entity.LdvIndicacionestm;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndicacionesController {

    ModelAndView mav = new ModelAndView();

    @RequestMapping("/Indicaciones")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_INDICACIONES);
        
      //aqui se inserta la accion que realiza usuario en modulos ***********            
        
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Configuración de indicaciones para el paciente");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        return mav;

    }
    @RequestMapping("/IndicacionesTM")
    public ModelAndView pageLoad2(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_INDICACIONESTM);
      
        //aqui se inserta la accion que realiza usuario en modulos ***********          
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Configuración de indicaciones para la toma de muestras");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        return mav;

    }

    @RequestMapping(value = "/Indicaciones", method = RequestMethod.POST, params = "getIndicaciones")
    public void MostrarPacientee(HttpServletRequest request, HttpServletResponse response) {
        try {
            String descripcion = request.getParameter("getIndicaciones");

            LdvIndicacionesExamenesDAO ldvie = new LdvIndicacionesExamenesDAO();
            List<LdvIndicacionesexamenes> IndicacionesList = ldvie.getIndicacionesExamen(descripcion);
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (LdvIndicacionesexamenes Indicaciones : IndicacionesList) {
                JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
                JsonObject comunaJson;

                comunaBuilder.add("idIndicaciones", Indicaciones.getLdvieIdindicacionesexamen())
                        .add("detalleIndicaciones", Indicaciones.getLdvieDescripcion());
                comunaJson = comunaBuilder.build();
                arrayBuilder.add(comunaJson);
            }
            JsonObject root = rootBuilder.add("Indicaciones", arrayBuilder).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        } catch (BiosLISDAOException | IOException ex) {
            Logger.getLogger(IndicacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/Indicaciones", method = RequestMethod.POST, params = "insertIndicacionesExa")
    public ModelAndView insertIndicacionExamen(HttpServletRequest request, HttpServletResponse response) {
        try {
        	HttpSession sesion = (HttpSession) request.getSession();
            DatosUsuarios usuario = new DatosUsuarios();
            usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            LdvIndicacionesExamenesDAO ldvie = new LdvIndicacionesExamenesDAO();
            String descripcion = request.getParameter("insertIndicacionesExa");
            LdvIndicacionesexamenes insertLdvIndicacionExamen = new LdvIndicacionesexamenes();
            insertLdvIndicacionExamen.setLdvieDescripcion(descripcion.toUpperCase());
            //insertLdvIndicacionExamen.setLdvieIdindicacionesexamen(ldvie.getLastId());
            ldvie.insertIndicacionesExamen(insertLdvIndicacionExamen, usuario.getDuIdusuario());
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(IndicacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:/Indicaciones?message=ingresadoCorrectamente");
    }

    @RequestMapping(value = "/Indicaciones", method = RequestMethod.POST, params = "updateIndicacionesExa")
    public ModelAndView updateIndicacionExamen(HttpServletRequest request, HttpServletResponse response) {

        try {
        	HttpSession sesion = (HttpSession) request.getSession();
            DatosUsuarios usuario = new DatosUsuarios();
            usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            LdvIndicacionesExamenesDAO ldvie = new LdvIndicacionesExamenesDAO();
            String descripcion = request.getParameter("updateIndicacionesExa");
            String id = request.getParameter("id");
            
            LdvIndicacionesexamenes insertLdvIndicacionExamen = new LdvIndicacionesexamenes();
            insertLdvIndicacionExamen.setLdvieDescripcion(descripcion.toUpperCase());
            insertLdvIndicacionExamen.setLdvieIdindicacionesexamen((byte) Integer.parseInt(id));
            ldvie.updateIndicacionesExamen(insertLdvIndicacionExamen, usuario.getDuIdusuario());
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(IndicacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:/Indicaciones?message=ingresadoCorrectamente");
    }

    ////////////////////////////
    @RequestMapping(value = "/Indicaciones", method = RequestMethod.POST, params = "getIndicacionesTM")
    public void tablaTomaMuestra(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, Exception {
/*
        try {

            String descripcion = request.getParameter("getIndicacionesTM");

            LdvIndicacionesTMDAO ldvie = new LdvIndicacionesTMDAO();
            List<LdvIndicacionestm> IndicacionesList = ldvie.getIndicacionesTM(descripcion);
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (LdvIndicacionestm Indicaciones : IndicacionesList) {
                JsonObjectBuilder comunaBuilder = Json.createObjectBuilder();
                JsonObject comunaJson;

                comunaBuilder.add("detalleIndicaciones", Indicaciones.getLdvitmDescripcionindicacion())
                        .add("idIndicaciones", Indicaciones.getLdvitmIdindicacionestm());
                comunaJson = comunaBuilder.build();
                arrayBuilder.add(comunaJson);
            }
            JsonObject root = rootBuilder.add("Indicaciones", arrayBuilder).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);

        } catch (BiosLISDAOException | IOException e) {
            throw e;
        }*/

    }
/*
    @RequestMapping(value = "/Indicaciones", method = RequestMethod.POST, params = "insertIndicacionesTM")
    public ModelAndView insertIndicacionTM(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            LdvIndicacionesTMDAO ldvie = new LdvIndicacionesTMDAO();
            String descripcion = request.getParameter("insertIndicacionesTM");
            LdvIndicacionestm insertLdvIndicacionTM = new LdvIndicacionestm();
            insertLdvIndicacionTM.setLdvitmDescripcionindicacion(descripcion.toUpperCase());
            insertLdvIndicacionTM.setLdvitmIdindicacionestm(ldvie.getLastId());
            ldvie.insertIndicacionesTM(insertLdvIndicacionTM);
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(IndicacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:/Indicaciones?message=ingresadoCorrectamente");
    }*/

    @RequestMapping(value = "/Indicaciones", method = RequestMethod.POST, params = "updateIndicacionesTM")
    public ModelAndView updateIndicacionTM(HttpServletRequest request, HttpServletResponse response) {
    	 HttpSession sesion = (HttpSession) request.getSession();
         DatosUsuarios usuario = new DatosUsuarios();
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        try {
            LdvIndicacionesTMDAO ldvie = new LdvIndicacionesTMDAO();
            String descripcion = request.getParameter("updateIndicacionesTM");
            String id = request.getParameter("id");
            
            LdvIndicacionestm insertLdvIndicacionExamen = new LdvIndicacionestm();
            insertLdvIndicacionExamen.setLdvitmDescripcionindicacion(descripcion.toUpperCase());
            insertLdvIndicacionExamen.setLdvitmIdindicacionestm((short) Integer.parseInt(id));
            ldvie.insertIndicacionesTM(insertLdvIndicacionExamen, usuario.getDuIdusuario());
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(IndicacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:/Indicaciones?message=ingresadoCorrectamente");
    }

}
