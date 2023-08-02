/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgExamenesPanelesDAO;
import com.grupobios.bioslis.dao.CfgPanelesExamenesDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgExamenesPaneles;
import com.grupobios.bioslis.entity.CfgExamenesPanelesId;
import com.grupobios.bioslis.entity.CfgPanelesExamenes;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ConfiguracionPanelesExamenesController {
    
    private static final Logger logger = LogManager.getLogger(ConfiguracionPanelesExamenesController.class);
    ModelAndView mav = new ModelAndView();
    DatosUsuarios usuario = new DatosUsuarios();
    
    @RequestMapping("/ConfiguracionPanelesExamenes")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_CONFIGURACION_PANELES_EXAMENES);
        
      //aqui se inserta la accion que realiza usuario en modulos ***********            
        
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Configuración de paneles exámenes");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        return mav;
    }
    
    @RequestMapping(value ="/ConfiguracionPanelesExamenes", method = RequestMethod.POST)
    public ModelAndView agregarPanel(CfgPanelesExamenes cpe, HttpServletRequest request, RedirectAttributes attributes) {
    	 HttpSession sesion = (HttpSession) request.getSession();
         DatosUsuarios usuario = new DatosUsuarios();
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    	
        String nombre = request.getParameter("cpeNombrepanelexamen").toUpperCase();
        String[] examenes = request.getParameterValues("ExamenesResumenes");
        try {
            cpe.setCpeNombrepanelexamen(nombre);
            if (cpe.getCpeActivo() == null)
                cpe.setCpeActivo("N");
            CfgPanelesExamenesDAO cpeDao = new CfgPanelesExamenesDAO();
            CfgExamenesPanelesDAO cepDao = new CfgExamenesPanelesDAO();
           String mensaje = cpeDao.insertPanelExamen(cpe, usuario.getDuIdusuario());
           
            //Integer cont = 1;
            for (String examen: examenes){
                    CfgExamenesPanelesId id = new CfgExamenesPanelesId(cpe.getCpeIdpanelexamenes(), Long.parseLong(examen));
                    //CfgExamenesPaneles cep = new CfgExamenesPaneles(id, cont.shortValue());
                    CfgExamenesPaneles cep = new CfgExamenesPaneles(id, Short.valueOf("1"));
                     cepDao.insertExamenesPanel(cep, usuario.getDuIdusuario(), nombre);
                    //cont++;
            }
            if (mensaje.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Panel ingresado correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Panel ya existe");
			}
            
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_PANELES_EXAMENES);
    }
    
    @RequestMapping(value ="/ConfiguracionPanelesExamenes", method = RequestMethod.POST, params = "update")
    public ModelAndView actualizarPanel(CfgPanelesExamenes cpe, HttpServletRequest request, RedirectAttributes attributes) {
    	 HttpSession sesion = (HttpSession) request.getSession();
         DatosUsuarios usuario = new DatosUsuarios();
         usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        Short idPanel = Short.parseShort(request.getParameter("idPanel"));
        String nombre = request.getParameter("cpeNombrepanelexamen").toUpperCase();
        String[] examenes = request.getParameterValues("ExamenesResumenes");
        try {
            cpe.setCpeIdpanelexamenes(idPanel);
            cpe.setCpeNombrepanelexamen(nombre);
            if (cpe.getCpeActivo() == null)
                cpe.setCpeActivo("N");
            CfgPanelesExamenesDAO cpeDao = new CfgPanelesExamenesDAO();
            CfgExamenesPanelesDAO cepDao = new CfgExamenesPanelesDAO();
           String msj = cpeDao.updatePanelExamen(cpe, usuario.getDuIdusuario());

            List<CfgExamenesPaneles> ListExamenesPanel = cepDao.listaExamenesPanel(idPanel);

            for (String e: examenes){
                Long examen = Long.parseLong(e);
                CfgExamenesPaneles buscaExamen = cepDao.buscarExamenPanel(idPanel, examen);
                if (buscaExamen == null){
                    //agregar examen
                    CfgExamenesPanelesId id = new CfgExamenesPanelesId(idPanel, examen);
                    CfgExamenesPaneles cep = new CfgExamenesPaneles(id, Short.valueOf("1"));
                    cepDao.insertExamenesPanel(cep, usuario.getDuIdusuario(), nombre);
                   
                }
            }

            for (CfgExamenesPaneles exp : ListExamenesPanel){
                boolean notFound = true;
                for (String e: examenes){
                    if (exp.getCfgExamenesPanelesId().getCepIdexamen() == Long.parseLong(e)){
                        notFound = false;
                    }
                }
                if (notFound == true){ //examen eliminado                   
                    //eliminar examen                
                    cepDao.deleteExamenPanel(exp, usuario.getDuIdusuario(), nombre);
                }
            }

            if (msj.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Panel Actualizado correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Panel ya existe");
			}
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_PANELES_EXAMENES);
    }
    
    @RequestMapping(value = "/ConfiguracionPanelesExamenes", method = RequestMethod.POST, params = "filtro")
    public void getPanelesExamenesFiltro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String nombre = request.getParameter("nombre").toUpperCase();
            Integer activo = Integer.parseInt(request.getParameter("activo").toUpperCase());
            CfgPanelesExamenesDAO cpeDao = new CfgPanelesExamenesDAO();
            List<CfgPanelesExamenes> lista = cpeDao.getListPanelesFiltro(nombre, activo);
            PrintWriter writer = response.getWriter();
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (CfgPanelesExamenes panel : lista) {
                JsonObjectBuilder panelBuilder = Json.createObjectBuilder();
                JsonObject panelJson;
                panelBuilder.add("Id", panel.getCpeIdpanelexamenes())
                        .add("Activo", panel.getCpeActivo())
                        .add("Descripcion", panel.getCpeNombrepanelexamen());
                panelJson = panelBuilder.build();
                arrayBuilder.add(panelJson);
            }
            JsonObject root = rootBuilder.add("paneles", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException | IOException ex) {
            logger.error(ex.getMessage());
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        }
    }
    
    @RequestMapping(value = "/ConfiguracionPanelesExamenes", method = RequestMethod.POST, params = "buscarPorId")
    public void getPanelExamenesById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Short idPanel = Short.parseShort(request.getParameter("idPanel"));
            CfgPanelesExamenesDAO cpeDao = new CfgPanelesExamenesDAO();
            CfgExamenesPanelesDAO cepDao = new CfgExamenesPanelesDAO();
            CfgPanelesExamenes panel = cpeDao.getPanelById(idPanel);
            
            List<CfgExamenesPaneles> ListExamenesPanel = cepDao.listaExamenesPanel(idPanel);
            
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            
            JsonObjectBuilder panelBuilder = Json.createObjectBuilder();
            JsonObject panelJson;
            
            JsonArrayBuilder panelExamenes = Json.createArrayBuilder();
            for (CfgExamenesPaneles examen : ListExamenesPanel) {
                JsonObjectBuilder examenBuilder = Json.createObjectBuilder();
                JsonObject examenJson;
                
                examenBuilder.add("examenId", examen.getCfgExamenesPanelesId().getCepIdexamen())
                        .add("examenCodigo", examen.getCfgExamenes().getCeCodigoexamen())
                        .add("examenNombre", examen.getCfgExamenes().getCeAbreviado())
                        .add("examenDescripcion", examen.getCfgExamenes().getCeDescripcion())
                        .add("examenOrden", examen.getCepSort());
                examenJson = examenBuilder.build();
                panelExamenes.add(examenJson);
            }
            
            panelBuilder.add("panelId", panel.getCpeIdpanelexamenes())
                    .add("panelNombre", panel.getCpeNombrepanelexamen())
                    .add("panelActivo", panel.getCpeActivo())
                    .add("examenesPanel", panelExamenes);
            
            panelJson = panelBuilder.build();
            arrayBuilder.add(panelJson);
            
            JsonObject root = rootBuilder.add("paneles", arrayBuilder).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        } catch (BiosLISDAOException | IOException ex) {
            logger.error(ex.getMessage());
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        }
    }
        
}
