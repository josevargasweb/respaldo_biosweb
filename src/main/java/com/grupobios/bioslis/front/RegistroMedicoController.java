/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.bs.SessionService;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgInstitucionesDeSaludDAO;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.dao.LdvEspecialidadesMedicasDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgMedicos;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
 *
 */
@Controller

//RESTCONTROLLER CONFIGURACIONMEDICORESTCONTROLLER
public class RegistroMedicoController {
    
    private static final Logger logger = LogManager.getLogger(RegistroMedicoController.class);
    ModelAndView mav = new ModelAndView();

    @RequestMapping("/RegistroMedico")
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_REGISTRO_MEDICO);
        if (sesion.getAttribute("usuario")!=null){
            try {
                LdvSexoDAO sexoDAO = new LdvSexoDAO();
                LdvEspecialidadesMedicasDAO especMedicas = new LdvEspecialidadesMedicasDAO();
                CfgInstitucionesDeSaludDAO institSalud = new CfgInstitucionesDeSaludDAO();
                mav.addObject("listaSexo", sexoDAO.getSexo());
                mav.addObject("listaInsitucionesSalud", institSalud.getInstitucionesDeSalud());
                mav.addObject("listEspecialidadesMedicas", especMedicas.getEspecialidadesMedicas());
            } catch (BiosLISDAOException ex) {
                java.util.logging.Logger.getLogger(RegistroMedicoController.class.getName()).log(Level.SEVERE, null, ex);
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
    	    leu.setLeuValornuevo("accede a Configuración de médico");
    	    logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        
        
        //mav.setViewName("RegistroMedico");
        return mav;
    }
/*
    @RequestMapping(value = "/RegistroMedico", method = RequestMethod.POST, params = "insert")
    public ModelAndView registroMedico(CfgMedicos med, HttpServletRequest request, RedirectAttributes attributes) throws ParseException { 
        String mensaje = null;
        String validadorOrden = request.getParameter("validadorOrden");
        String ipEquipo = request.getParameter("ipEquipo");
        CfgMedicosDAO medDAO = new CfgMedicosDAO();
        String rut = med.getCmRun().replace(".", "").toUpperCase();
        try {
            if (medDAO.ifMedicExist(rut) == true) {
                return new ModelAndView("redirect:/RegistroMedico?message=rutExiste");
            }

            String activo = request.getParameter("mcActivo");
            if (activo == null) {
                activo = "N";
            }
            med.setCmActivo(activo);
            med.setCmIdmedico(medDAO.getLastIdMedico());
            med.setCmRun(rut);
            med.setCmNombres(med.getCmNombres().toUpperCase());
            med.setCmPrimerapellido(med.getCmPrimerapellido().toUpperCase());
            med.setCmSegundoapellido(med.getCmSegundoapellido().toUpperCase());
            med.setCmRegistrodesalud(med.getCmRegistrodesalud().toUpperCase());

            medDAO.insertDatosMedicos(med, ipEquipo);

            if ("1".equals(validadorOrden)) {
                return new ModelAndView("redirect:/Orden?message=editadoCorrectamente&rm=" + med.getCmIdmedico());
            }
            attributes.addFlashAttribute("mensaje", "Médico ingresado correctamente");
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return new ModelAndView("redirect:/RegistroMedico");
    }*/
/*
    @RequestMapping(value = "/RegistroMedico", method = RequestMethod.POST, params = "update")
    public ModelAndView editMedico(CfgMedicos med, HttpServletRequest request, RedirectAttributes attributes) throws ParseException {
        String ipEquipo = request.getParameter("ipEquipo");       
        try {
            int id = Integer.parseInt(request.getParameter("idMedico"));
            String activo = request.getParameter("mcActivo");
            if (activo == null) {
                activo = "N";
            }
            CfgMedicosDAO medDAO = new CfgMedicosDAO();
            med.setCmActivo(activo);
            med.setCmIdmedico(id);
            med.setCmRun(med.getCmRun().replace(".", ""));
            med.setCmNombres(med.getCmNombres().toUpperCase());
            med.setCmPrimerapellido(med.getCmPrimerapellido().toUpperCase());
            med.setCmSegundoapellido(med.getCmSegundoapellido().toUpperCase());
            med.setCmRegistrodesalud(med.getCmRegistrodesalud().toUpperCase());
            medDAO.updateDatosMedicos(med, ipEquipo);
            attributes.addFlashAttribute("mensaje", "Médico actualizado correctamente");
        } catch (BiosLISDAOException ex) {
          logger.error(ex.getMessage());
          attributes.addFlashAttribute("mensajeError", ex.getMessage());
        } 
        return new ModelAndView("redirect:/RegistroMedico");
    }*/

    @RequestMapping(value = "/RegistroMedico", method = RequestMethod.POST, params = "rutFiltroMedico")
    public void filtroMedicoRut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            PrintWriter writer = response.getWriter();
            String rutMedico = request.getParameter("rutFiltroMedico");
            rutMedico = rutMedico.replace(".", "");
            CfgMedicosDAO medicos = new CfgMedicosDAO();
            CfgMedicos medico = medicos.getMedicoByRut(rutMedico);
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            
            // EN CASO DE +1 ROW COLOCAR ESTO EN FOR EACH
            JsonObjectBuilder pacienteBuilder = Json.createObjectBuilder();
            JsonObject pacienteJson;
            if (medico.getCmRun() == "0") {
                pacienteBuilder.add("idMedico", medico.getCmIdmedico())
                        .add("Rut", medico.getCmRun());
            } else {
                pacienteBuilder.add("idMedico", medico.getCmIdmedico())
                        .add("Rut", medico.getCmRun())
                        .add("Nombres", medico.getCmNombres())
                        .add("PrimerApellido", medico.getCmPrimerapellido())
                        .add("Sexo", medico.getLdvSexo())
                        .add("SegundoApellido", medico.getCmSegundoapellido())
                        .add("EspecialidadMedica", medico.getLdvEspecialidadesmedicas())
                        .add("Institucion", medico.getCfgInstitucionesdesalud())
                        .add("Email", medico.getCmEmail())
                        .add("Telefono", medico.getCmTelefono())
                        .add("Estado", medico.getCmActivo())
                        .add("RegistroSaludMedico", medico.getCmRegistrodesalud());
            }
            pacienteJson = pacienteBuilder.build();
            arrayBuilder.add(pacienteJson);
            //HASTA ACA
            
            JsonObject root = rootBuilder.add("pacientes", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        }

    }

    @RequestMapping(value = "/RegistroMedico", method = RequestMethod.POST, params = "idFiltroMedico")
    public void filtroMedicoId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            PrintWriter writer = response.getWriter();
            int id= 0;
        
            if(request.getParameter("idFiltroMedico") != null && !request.getParameter("idFiltroMedico").equals("null")) {
            	id = Integer.parseInt(request.getParameter("idFiltroMedico"));
            }
            
            CfgMedicosDAO medicos = new CfgMedicosDAO();
            CfgMedicos medico = medicos.getMedicoById(id);
            medico = medicos.nullToSpaceAll(medico);
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            
            // EN CASO DE +1 ROW COLOCAR ESTO EN FOR EACH
            JsonObjectBuilder pacienteBuilder = Json.createObjectBuilder();
            JsonObject pacienteJson;
            if ("0".equals(medico.getCmRun())) {
                pacienteBuilder.add("idMedico", medico.getCmIdmedico())
                        .add("Rut", medico.getCmRun());
            } else {
                pacienteBuilder.add("idMedico", medico.getCmIdmedico())
                        .add("Rut", medico.getCmRun())
                        .add("Nombres", medico.getCmNombres())
                        .add("PrimerApellido", medico.getCmPrimerapellido())
                        .add("Sexo", medico.getLdvSexo())
                        .add("SegundoApellido", medico.getCmSegundoapellido())
                        .add("EspecialidadMedica", medico.getLdvEspecialidadesmedicas())
                        .add("Institucion", medico.getCfgInstitucionesdesalud())
                        .add("Email", medico.getCmEmail())
                        .add("Telefono", medico.getCmTelefono())
                        .add("Estado", medico.getCmActivo())
                        .add("RegistroSaludMedico", medico.getCmRegistrodesalud());
            }
            
            pacienteJson = pacienteBuilder.build();
            arrayBuilder.add(pacienteJson);
            //HASTA ACA
            
            JsonObject root = rootBuilder.add("pacientes", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException ex) {
            logger.error(ex.getMessage());
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
            PrintWriter writer = response.getWriter();
            writer.print(root);
        }

    }

    @RequestMapping(value = "/RegistroMedico", method = RequestMethod.POST, params = "nombreFiltroMedico")
    public void filtroMedicoNombre(HttpServletRequest request, HttpServletResponse response) throws IOException  {
       	
        try {
            String nombreMedico = request.getParameter("nombreFiltroMedico");
            String apellidoMedico = request.getParameter("apellidoFiltroMedico");
            String segundoApellidoMedico = request.getParameter("segundoApellidoFiltroMedico");
            String rut = request.getParameter("rut");
            String activo = request.getParameter("activo");
            CfgMedicosDAO medDAO = new CfgMedicosDAO();
            List<CfgMedicos> medicos = medDAO.getMedicoByNombreApellido(nombreMedico, apellidoMedico, segundoApellidoMedico, rut, activo);
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (CfgMedicos medico : medicos) {
                JsonObjectBuilder MedicoBuilder = Json.createObjectBuilder();
                JsonObject MedicoJson;
                MedicoBuilder.add("Rut", medico.getCmRun());
                MedicoBuilder.add("Nombre", medico.getCmNombres());
                MedicoBuilder.add("Apellido", medico.getCmPrimerapellido());
                if (medico.getCmSegundoapellido() != null) {
                    MedicoBuilder.add("SegundoApellido", medico.getCmSegundoapellido());
                } else {
                    MedicoBuilder.add("SegundoApellido", "");
                }
                MedicoJson = MedicoBuilder.build();
                arrayBuilder.add(MedicoJson);
            }
            JsonObject root = rootBuilder.add("medicos", arrayBuilder).build();
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
    
    

    @RequestMapping(value = "/RegistroMedico", method = RequestMethod.POST, params = "filtro")
	public void getSecciones(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		String run = request.getParameter("run");
		
		String nombre = request.getParameter("nombre");
		nombre = nombre.toUpperCase();
	
	
	/*
		List<CfgSecciones> listaSecciones = new ArrayList<CfgSecciones>();
		CfgSeccionesDAO csecDAO = new CfgSeccionesDAO();
		if (codigo.length() <= 0 && descripcion.length() <= 0) {
			listaSecciones = csecDAO.getSecciones();
		} else {
			if (codigo.length() > 0) {
				listaSecciones = csecDAO.getSeccionesLikeCodigo(codigo);
			} else {
				listaSecciones = csecDAO.getSeccionesLikeDesc(descripcion);
			}
		}
		*/
		PrintWriter writer = response.getWriter();
		JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		/*
		for (CfgSecciones seccion : listaSecciones) {
			JsonObjectBuilder seccionBuilder = Json.createObjectBuilder();
			JsonObject seccionJson;
			seccionBuilder.add("IdSeccion", seccion.getCsecIdseccion()).add("CodigoSeccion", seccion.getCsecCodigo())
					.add("DescripcionSeccion", seccion.getCsecDescripcion());
			seccionJson = seccionBuilder.build();
			arrayBuilder.add(seccionJson);
		}
		*/
		JsonObject root = rootBuilder.add("seccion", arrayBuilder).build();
		writer.print(root);

	}
    
    
}
