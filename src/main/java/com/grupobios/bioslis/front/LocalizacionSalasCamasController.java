/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCamasSalasDAO;
import com.grupobios.bioslis.dao.CfgCentrosDeSaludDAO;
import com.grupobios.bioslis.dao.CfgSalasServiciosDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.LogCfgTablasDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgCamassalas;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgSalasservicios;
import com.grupobios.bioslis.entity.CfgServicios;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
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
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author marco.cortez
 */
@Controller
public class LocalizacionSalasCamasController {
    
    ModelAndView mav = new ModelAndView();
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(LocalizacionSalasCamasController.class);
    
    @RequestMapping("/LocalizacionSalasCamas")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_LOCALIZACION_SALAS_CAMAS);
        if (sesion.getAttribute("usuario")!=null){
            try {
                CfgCentrosDeSaludDAO cmcDao = new CfgCentrosDeSaludDAO();
                List<CfgCentrosdesalud> listaCentrosdesalud = cmcDao.getCentrosDeSalud();
                mav.addObject("listaCentrosdesalud", listaCentrosdesalud);
            } catch (BiosLISDAOException ex) {
                Logger.getLogger(LocalizacionSalasCamasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //aqui se inserta la accion que realiza usuario en modulos ***********            
        
        if(usuario != null) {
        	LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        	LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        	leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
    	    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
    	    leu.setLeuIdevento(11);              
    	    leu.setLeuIdacciondato(0);               
    	    leu.setLeuValornuevo("accede a Configuración de localizaciones salas cama");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        
        return mav;
    }
    
    @RequestMapping(value ="/LocalizacionSalasCamas", method = RequestMethod.POST, params = "filtro")
    public void getListaServicios(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String idCentro = request.getParameter("idCentro");
            String switchInactivos = request.getParameter("swInactivos");
            CfgServiciosDAO csrvDao = new CfgServiciosDAO();
            List<CfgServicios> listaServicios = new ArrayList<>();

            if (Integer.parseInt(switchInactivos) == 1){
                //listaServicios = csrvDao.getServiciosByIdCentro(Byte.parseByte(idCentro));
                listaServicios = csrvDao.getServiciosByIdCentro(Short.parseShort(idCentro));
            } else{
                //listaServicios = csrvDao.getServiciosActivosByIdCentro(Byte.parseByte(idCentro));
                listaServicios = csrvDao.getServiciosActivosByIdCentro(Short.parseShort(idCentro));
            }

            PrintWriter writer = response.getWriter();
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            //lista los servicios del centro de salud seleccionado
            for (CfgServicios servicio : listaServicios) {
                JsonObjectBuilder objectServicios = Json.createObjectBuilder();
                JsonObject seccionJson;
                JsonArrayBuilder arraySalas = Json.createArrayBuilder();

                CfgSalasServiciosDAO cfgSS = new CfgSalasServiciosDAO();
                List<Object[]> listaSalasServicios = cfgSS.getSalasServicios(servicio);

                //lista las salas de los servicios
                for (Object[] salasS: listaSalasServicios){
                    JsonObjectBuilder objectSalas = Json.createObjectBuilder();
                    JsonObject salasJson;
                    JsonArrayBuilder arrayCamas = Json.createArrayBuilder();

                    String idS = salasS[0].toString();
                    String descripcionS = salasS[2].toString();

                    CfgCamasSalasDAO cfgCsDao = new CfgCamasSalasDAO();
                    List<Object[]> listaCamasSalas = cfgCsDao.getCamasSalas(Integer.parseInt(idS));

                    //lista las camas de las salas
                    for (Object[] camas : listaCamasSalas){
                        JsonObjectBuilder objectCamas = Json.createObjectBuilder();
                        JsonObject camasJson;

                        String idC = camas[0].toString();
                        String descripcionC = camas[2].toString();
                        //System.out.println("idCama: "+ idC + ", descripcionCama: "+ descripcionC);
                        objectCamas.add("idCama", idC)
                                .add("descripcionCama", descripcionC);
                        camasJson = objectCamas.build();
                        arrayCamas.add(camasJson);
                    }

                  
                    objectSalas.add("idSala", idS)
                            .add("descripcionSala", descripcionS)
                            .add("camas", arrayCamas);
                    salasJson = objectSalas.build();
                    arraySalas.add(salasJson);
                }

                objectServicios.add("id", servicio.getCsIdservicio())
                        .add("codigo", servicio.getCsCodigo()!=null?servicio.getCsCodigo(): "")
                        .add("descripcion", servicio.getCsDescripcion()!=null?servicio.getCsDescripcion():"")
                        .add("salas", arraySalas);
                seccionJson = objectServicios.build();
                arrayBuilder.add(seccionJson);
            }
            JsonObject root = rootBuilder.add("servicio", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException | IOException ex) {
            logger.error(ex.getMessage());
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        }
    }
    
    @RequestMapping(value ="/LocalizacionSalasCamas", method = RequestMethod.POST, params = "nuevaSala")
    public ModelAndView agregarNuevaSala(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) throws ParseException{
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String idServicio = request.getParameter("idServicio");
            String codigoNuevaSala = request.getParameter("codigoNuevaSala").toUpperCase();
            String descripcionNuevaSala = request.getParameter("descripcionNuevaSala").toUpperCase();
            String ipEquipo = request.getParameter("ipEquipo");
           
            
            CfgSalasservicios css = new CfgSalasservicios();
            CfgSalasServiciosDAO cssDao = new CfgSalasServiciosDAO();
            CfgServicios cs = new CfgServicios(Integer.parseInt(idServicio));
            BigDecimal newId = cssDao.obtenerNuevoID();
            
            css.setCssIdsalaservicio(newId.intValue());
            css.setCssCodigosala(codigoNuevaSala);
            css.setCssDescripcion(descripcionNuevaSala);
            css.setCfgServicios(cs);
            cssDao.agregarSalaServicio(css, usuario.getDuIdusuario());
            
            mav.addObject("id", newId.toString());
            
            //Log comentado por cristian el 10-05  ya no se utiliza este metodo
            /*
            LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
            logTablas.insertLog("Nueva Sala", "", "", "CFG_SALASSERVICIOS", 1, 1, "Equipo", newId.intValue(), ipEquipo);
            */
            attributes.addFlashAttribute("mensaje", "Sala ingresada correctamente");
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LocalizacionSalasCamasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:/LocalizacionSalasCamas");
    }
    
    @RequestMapping(value ="/LocalizacionSalasCamas", method = RequestMethod.POST, params = "editSala")
    public void habilitarEdicionSala(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            String idSala = request.getParameter("idSala");
            CfgSalasServiciosDAO cssDao = new CfgSalasServiciosDAO();
            CfgSalasservicios css = cssDao.getSalasById(Integer.parseInt(idSala));
            
            PrintWriter writer = response.getWriter();
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            
            JsonObjectBuilder salaBuilder = Json.createObjectBuilder();
            JsonObject salaJson;
            salaBuilder.add("idSala", css.getCssIdsalaservicio())
                    .add("codigoSala", css.getCssCodigosala())
                    .add("descripcionSala", css.getCssDescripcion());
            
            salaJson = salaBuilder.build();
            arrayBuilder.add(salaJson);
            
            JsonObject root = rootBuilder.add("sala", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LocalizacionSalasCamasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @RequestMapping(value ="/LocalizacionSalasCamas", method = RequestMethod.POST, params = "updateSala")
    public ModelAndView actualizarSala(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) throws ParseException{
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String idSala = request.getParameter("idSala");
            String descripcionSala = request.getParameter("descripcionSala").toUpperCase();
            //String ipEquipo = request.getParameter("ipEquipo");          
            //System.out.println("IP: " + ipEquipo);
            
            CfgSalasservicios css = new CfgSalasservicios();
            CfgSalasServiciosDAO cssDao = new CfgSalasServiciosDAO();
            //Para realizar el comparador y para recuperar el id de servicio
            CfgSalasservicios cssOld = cssDao.getSalasById(Integer.parseInt(idSala));
            
            css.setCssIdsalaservicio(Integer.parseInt(idSala));
            css.setCssDescripcion(descripcionSala);
            css.setCfgServicios(cssOld.getCfgServicios());
            
            cssDao.actualizarSalaServicio(css, usuario.getDuIdusuario());
            
            //Objeto para realizar el comparador
            Object[] objOld = new Object[2];
            objOld[0] = cssOld.getCssIdsalaservicio();
            objOld[1] = cssOld.getCssDescripcion();
            
            Object[] objNew = new Object[2];
            objNew[0] = css.getCssIdsalaservicio();
            objNew[1] = css.getCssDescripcion();
            
            //Log
            LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
         //   logTablas.comparadorObjetos(objOld, objNew, "CFG_SALASSERVICIOS", 2, 1, "Equipo", Integer.parseInt(idSala), "");
            //logTablas.comparadorObjetos(cssOld, css, "CFG_SALASSERVICIOS", 2, 1, "Equipo", Integer.parseInt(idSala), "");
            attributes.addFlashAttribute("mensaje", "Sala actualizada correctamente");
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LocalizacionSalasCamasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:/LocalizacionSalasCamas");
    }
    
    @RequestMapping(value ="/LocalizacionSalasCamas", method = RequestMethod.POST, params = "nuevaCama")
    public ModelAndView agregarNuevaCama(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String idSala = request.getParameter("idSala");
            String idServicio = request.getParameter("idServicio");
            String codigoNuevaCama = request.getParameter("codigoNuevaCama").toUpperCase();
            String descripcionNuevaCama = request.getParameter("descripcionNuevaCama").toUpperCase();
            String ipEquipo = request.getParameter("ipEquipo");
          //  System.out.println("Agregar Nueva Cama " + codigoNuevaCama + " - " + descripcionNuevaCama + " a Sala: " + idSala + ", Servicio: " + idServicio);
            
            CfgCamassalas ccs = new CfgCamassalas();
            CfgCamasSalasDAO ccsDao = new CfgCamasSalasDAO();
            CfgSalasservicios css = new CfgSalasservicios();
            css.setCssIdsalaservicio(Integer.parseInt(idSala));
            CfgServicios cs = new CfgServicios();
            cs.setCsIdservicio(Integer.parseInt(idServicio));
            css.setCfgServicios(cs);
            //BigDecimal newId = ccsDao.obtenerNuevoId();
            
            //ccs.setCcsIdcamasala(newId.intValue());
            ccs.setCcsCodigocama(codigoNuevaCama);
            ccs.setCcsDescripcion(descripcionNuevaCama);
            ccs.setCfgSalasservicios(css);        
            ccsDao.agregarCama(ccs, usuario.getDuIdusuario());
            
            //mav.addObject("id", newId.toString());
            
            //Log   comentado el 10-05 por cristian porque ya no se utiliza 
            /*
            LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
            logTablas.insertLog("Nueva Cama", "", "", "CFG_CAMASSALAS", 1, 1, "Equipo", ccs.getCcsIdcamasala(), ipEquipo);
            */
            attributes.addFlashAttribute("mensaje", "Cama ingresada correctamente");
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(LocalizacionSalasCamasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:/LocalizacionSalasCamas");
    }
    
    @RequestMapping(value ="/LocalizacionSalasCamas", method = RequestMethod.POST, params = "updateCama")
    public ModelAndView actualizarCama(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {
        try {
        	 HttpSession sesion = (HttpSession) request.getSession();
             DatosUsuarios usuario = new DatosUsuarios();
             usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            String idCama = request.getParameter("idCama");
            String descripcionCama = request.getParameter("descripcionCama").toUpperCase();          
            
            CfgCamasSalasDAO ccsDao = new CfgCamasSalasDAO();
            CfgCamassalas ccsOld = ccsDao.getCamasById(Integer.parseInt(idCama));
            CfgCamassalas ccs = new CfgCamassalas();
            
            ccs.setCcsIdcamasala(Integer.parseInt(idCama));
            ccs.setCcsDescripcion(descripcionCama);
            ccs.setCfgSalasservicios(ccsOld.getCfgSalasservicios());
            ccsDao.actualizarCama(ccs, usuario.getDuIdusuario());
            
            //Objeto para realizar el comparador
            Object[] objOld = new Object[2];
            objOld[0] = ccsOld.getCcsIdcamasala();
            objOld[1] = ccsOld.getCcsDescripcion();
            
            Object[] objNew = new Object[2];
            objNew[0] = ccs.getCcsIdcamasala();
            objNew[1] = ccs.getCcsDescripcion();
            
            //Log
            LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
            //logTablas.comparadorObjetos(objOld, objNew, "CFG_CAMASSALAS", 2, 1, "Equipo", Integer.parseInt(idCama), "");
           // logTablas.comparadorObjetos(ccsOld, ccs, "CFG_CAMASSALAS", 2, 1, "Equipo", Integer.parseInt(idCama), "");
            
            attributes.addFlashAttribute("mensaje", "Cama actualizada correctamente");
            
        } catch (BiosLISDAOException  ex) {
            Logger.getLogger(LocalizacionSalasCamasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:/LocalizacionSalasCamas");
    }
    
}
