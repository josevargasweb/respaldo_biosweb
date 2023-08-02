/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Marco Caracciolo
 */
@Controller
public class ConfiguracionProcedenciasController {
    
    private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(ConfiguracionProcedenciasController.class);
    ModelAndView mav = new ModelAndView();
    CfgProcedenciasDAO cpDAO = new CfgProcedenciasDAO();
    
    @RequestMapping("/ConfiguracionProcedencias")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_CONFIGURACION_PROCEDENCIAS);
        
      //aqui se inserta la accion que realiza usuario en modulos ***********           
        
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Configuración de procedencias");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        return mav;
    }
    /*
    @RequestMapping(value="/ConfiguracionProcedencias", method = RequestMethod.POST)
    public ModelAndView agregarProcedencia(CfgProcedencias cp, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            String codigo = request.getParameter("cpCodigo").toUpperCase();
            String nombre = request.getParameter("cpDescripcion").toUpperCase();
            //String cpConvenio = request.getParameter("cpConvenio");
            byte cpCentro = Byte.parseByte(request.getParameter("cpCentro"));
            short idConvenio = Short.parseShort(request.getParameter("cpConvenio"));
            cp.setCpCodigo(codigo);
            cp.setCpDescripcion(nombre);
            if (cp.getCpActivo() == null){
                cp.setCpActivo("N");
            }
            if (cp.getCpProcedenciarestringida()== null){
                cp.setCpProcedenciarestringida("N");
            }
            if (cp.getCpTomamuestraautomatica()== null){
                cp.setCpTomamuestraautomatica("N");
            }
            if (cp.getCpMembrete()== null){
                cp.setCpMembrete("N");
            }
            if(cpCentro > 0){
                CfgCentrosdesalud centro = new CfgCentrosdesalud();
                centro.setCcdsIdcentrodesalud(cpCentro);
                cp.setCfgCentroDeSalud(centro);
            } else {
                cp.setCfgCentroDeSalud(null);
            }
            if(idConvenio > 0){
                CfgConvenios conv = new CfgConvenios();
                conv.setCcIdconvenio(idConvenio);
                cp.setCfgConvenios(conv);
            }
            String msj = cpDAO.insertProcedencia(cp);
            if (msj.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Procedencia ingresada correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Procedencia ya existe");
			}
            
        } catch (BiosLISDAOException ex) {
            LOGGER.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_PROCEDENCIAS);
    }
    
    @RequestMapping(value="/ConfiguracionProcedencias", method = RequestMethod.POST, params = "update")
    public ModelAndView actualizarProcedencia(CfgProcedencias cp, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            Integer idProcedencia = Integer.parseInt(request.getParameter("idProcedencia"));
            String codigo = request.getParameter("cpCodigo").toUpperCase();
            String nombre = request.getParameter("cpDescripcion").toUpperCase();
            String cpConvenio = request.getParameter("cpConvenio");
            String cpCentro = request.getParameter("cpCentro");
            cp.setCpIdprocedencia(idProcedencia);
            cp.setCpCodigo(codigo);
            cp.setCpDescripcion(nombre);
            if (cp.getCpActivo() == null){
                cp.setCpActivo("N");
            }
            if (cp.getCpProcedenciarestringida()== null){
                cp.setCpProcedenciarestringida("N");
            }
            if (cp.getCpTomamuestraautomatica()== null){
                cp.setCpTomamuestraautomatica("N");
            }
            if (cp.getCpMembrete()== null){
                cp.setCpMembrete("N");
            }
            if(!cpCentro.equals("0")){
                CfgCentrosdesalud centro = new CfgCentrosdesalud();
                centro.setCcdsIdcentrodesalud(Byte.parseByte(cpCentro));
                cp.setCfgCentroDeSalud(centro);
            }
            if(!cpConvenio.equals("0")){
                CfgConvenios conv = new CfgConvenios();
                conv.setCcIdconvenio(Short.parseShort(cpConvenio));
                cp.setCfgConvenios(conv);
            }
            String msj =cpDAO.updateProcedencia(cp);
            if (msj.equals("C")) {
            	attributes.addFlashAttribute("mensaje", "Procedencia actualizada correctamente");
			}else {
				attributes.addFlashAttribute("mensajeError", "Procedencia ya existe");
			}
        } catch (BiosLISDAOException ex) {
            LOGGER.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/"+ViewNames.CONFIGURACION_PROCEDENCIAS);
    }*/
    /*
    @RequestMapping(value = "/ConfiguracionProcedencias", method = RequestMethod.POST, params = "filtro")
    public void getProcedencias(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigo");
        codigo = codigo.toUpperCase();
        String nombre = request.getParameter("nombre");
        nombre = nombre.toUpperCase();
        List<CfgProcedencias> listaProcedencias = new ArrayList<>();
        CfgProcedenciasDAO cpDao = new CfgProcedenciasDAO();
        if (codigo.length() <= 0 && nombre.length() <= 0) {
            listaProcedencias = cpDao.getProcedencias();
        } else {
            if (codigo.length() > 0) {
                listaProcedencias = cpDao.getProcedenciasLikeCodigo(codigo);
            } else {
                listaProcedencias = cpDao.getProcedenciasLikeNombre(nombre);
            }
        }
        PrintWriter writer = response.getWriter();
        JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (CfgProcedencias procedencia : listaProcedencias) {
                JsonObjectBuilder seccionBuilder = Json.createObjectBuilder();
                JsonObject procedenciaJson;
                seccionBuilder.add("Id", procedencia.getCpIdprocedencia())
                            .add("Codigo", procedencia.getCpCodigo()==null?"":procedencia.getCpCodigo())
                            .add("Descripcion", procedencia.getCpDescripcion());
                procedenciaJson = seccionBuilder.build();
                arrayBuilder.add(procedenciaJson);
        }
        JsonObject root = rootBuilder.add("procedencias", arrayBuilder).build();
        writer.print(root);

    }
    */
    @RequestMapping(value = "/ConfiguracionProcedencias", method = RequestMethod.POST, params = "filtroId")
    public void buscarProcedenciaById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String idProcedencia = request.getParameter("idProcedencia");
            
            CfgProcedencias cp = cpDAO.getProcedenciaById(Integer.parseInt(idProcedencia));
            
            PrintWriter writer = response.getWriter();
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            
            JsonObjectBuilder procedenciaBuilder = Json.createObjectBuilder();
            JsonObject procedenciaJson;
            procedenciaBuilder.add("IdProcedencia", cp.getCpIdprocedencia())
                    .add("Codigo", (cp.getCpCodigo() != null) ? cp.getCpCodigo() : "")
                    .add("Descripcion", cp.getCpDescripcion())
                    .add("Orden", (cp.getCpSort() >= 0) ? cp.getCpSort() : 1)
                    .add("CodCentro", (cp.getCfgCentroDeSalud() != null) ? cp.getCfgCentroDeSalud().getCcdsIdcentrodesalud() : 0)
                    .add("Email", (cp.getCpEmail() != null) ? cp.getCpEmail() : "")
                    .add("Convenio", (cp.getCfgConvenios() != null) ? cp.getCfgConvenios().getCcIdconvenio() : 0)
                    .add("Host", (cp.getCpHost() != null) ? cp.getCpHost() : "")
                    .add("Host2", (cp.getCpHost2() != null) ? cp.getCpHost2() : "")
                    .add("Host3", (cp.getCpHost3() != null) ? cp.getCpHost3() : "")
                    .add("Activo", (cp.getCpActivo() != null) ? cp.getCpActivo() : "N")
                    .add("Grupo", (cp.getCpCodigoGrupo() != null) ? cp.getCpCodigoGrupo() : "")
                    .add("TextoInforme", (cp.getCpTextoInforme() != null) ? cp.getCpTextoInforme() : "")
                    .add("Restringida", (cp.getCpProcedenciarestringida() != null) ? cp.getCpProcedenciarestringida() : "N")
                    .add("TomaMuestraAutomatica", (cp.getCpTomamuestraautomatica() != null) ? cp.getCpTomamuestraautomatica() : "N")
                    .add("Membrete", (cp.getCpMembrete()!= null) ? cp.getCpMembrete() : "N");
            
            procedenciaJson = procedenciaBuilder.build();
            arrayBuilder.add(procedenciaJson);
            
            JsonObject root = rootBuilder.add("procedencia", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException ex) {
            LOGGER.error(ex.getMessage());
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        }
    }

    public CfgProcedenciasDAO getCpDAO() {
        return cpDAO;
    }

    public void setCpDAO(CfgProcedenciasDAO cpDAO) {
        this.cpDAO = cpDAO;
    }
    
    
}
