/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgNotasDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgNotas;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author Nacho
 */
@Controller
public class ConfiguracionNotasController {

    ModelAndView mav = new ModelAndView();

    @RequestMapping("/ConfiguracionNotas")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_CONFIGURACION_NOTAS);
        
        //aqui se inserta la accion que realiza usuario en modulos ***********            
        
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Configuración de notas");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        return mav;
    }

    @RequestMapping(value = "/ConfiguracionNotas", method = RequestMethod.POST)
    public void agregarNota(HttpServletRequest request, HttpServletResponse response) {

    }

    @RequestMapping(value = "/ConfiguracionNotas", method = RequestMethod.POST, params = "getNotas")
    public void getNotas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            CfgNotasDAO cnDAO = new CfgNotasDAO();
            List<CfgNotas> listaNotas = cnDAO.getNotas();
            
            PrintWriter writer = response.getWriter();
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            
            for (CfgNotas nota : listaNotas) {
                JsonObjectBuilder notaBuilder = Json.createObjectBuilder();
                JsonObject notaJson;
                notaBuilder.add("DescripcionNota", nota.getCnDescripcionnota())
                        .add("IdNota", nota.getCnIdnota());
                notaJson = notaBuilder.build();
                arrayBuilder.add(notaJson);
            }
            JsonObject root = rootBuilder.add("nota", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
