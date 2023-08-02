package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.bs.SessionService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosPacientesDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.common.DateFormatterHelper;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReasignacionOrdenesController {

	ModelAndView mav = new ModelAndView();
        
        private final static Logger LOGGER = LogManager.getLogger(ReasignacionOrdenesController.class);

	@RequestMapping("/ReasignacionOrdenes")
        public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
            HttpSession sesion = (HttpSession) request.getSession();
            DatosUsuarios usuario = new DatosUsuarios();
            usuario = (DatosUsuarios) sesion.getAttribute("usuario");
            
            mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, 6);
            if (sesion.getAttribute("usuario")!=null){
		LdvTiposdocumentosDAO tiposDocumentosDAO = new LdvTiposdocumentosDAO();//
		LdvSexoDAO sexoDAO = new LdvSexoDAO();
		mav.addObject("listaSexo", sexoDAO.getSexo());
		mav.addObject("listaTiposDocumentos", tiposDocumentosDAO.getTiposDocumentosMenosRut());
		//mav.setViewName("ReasignacionOrdenes");
            }
            
            
            //aqui se inserta la accion que realiza usuario en logusuarios ***********  
            if(usuario != null) {
	              LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	              LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	              
	              leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	              leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	              leu.setLeuIdevento(11);              
	              leu.setLeuIdacciondato(0);               
	              leu.setLeuValornuevo("accede a Reasignar ordenes");
	              logUsuarioDao.insertLogEventosUsuario(leu);
            }
             //*-************************************************************ 
            return mav;
	}

	@RequestMapping(value = "/ReasignacionOrdenes", method = RequestMethod.POST, params = "rutFiltro")
	public void filtroPacienteRut(HttpServletRequest request, HttpServletResponse response) {

		JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		try (PrintWriter writer = response.getWriter();) {
			String rutPaciente = request.getParameter("rutFiltro");
			DatosPacientesDAO dpDAO = new DatosPacientesDAO();

			// Busqueda revisar porblemas con la logica.
			Integer idPac = dpDAO.getIdByRut(rutPaciente);

			if (idPac == null) {
				JsonObject root = rootBuilder.add("pacientes", arrayBuilder).build();
				writer.print(root);
			}
			try {
				DatosPacientes paciente;
				paciente = dpDAO.getPacienteById(idPac);
				if (!paciente.getDpNrodocumento().equals("0")) {
					// EN CASO DE +1 ROW COLOCAR ESTO EN FOR EACH
					JsonObjectBuilder pacienteBuilder = Json.createObjectBuilder();
					JsonObject pacienteJson;
					pacienteBuilder.add("idPaciente", paciente.getDpIdpaciente())
							.add("TipoDocumento", paciente.getLdvTiposdocumentos())
							.add("Rut", paciente.getDpNrodocumento()).add("Nombres", paciente.getDpNombres())
							.add("PrimerApellido", paciente.getDpPrimerapellido()).add("Sexo", paciente.getLdvSexo())
							.add("FechaNacimiento", DateFormatterHelper.dtimeToText(paciente.getDpFnacimiento()));

					// PATOLOGIAS JSON
					DatosFichasDAO dfd = new DatosFichasDAO();

					List<DatosFichas> listaOrdenes = dfd.OrdenxPaciente(idPac);
					JsonArrayBuilder arrayBuilderOrden = Json.createArrayBuilder();
					for (DatosFichas listaOrden : listaOrdenes) {
						JsonObjectBuilder OrdenBuilder = Json.createObjectBuilder();
						JsonObject pacienteOrdenJson;
						OrdenBuilder.add("NOrden", listaOrden.getDfNorden()).add("Fecha",
								listaOrden.getDfFechaorden().toString());
						pacienteOrdenJson = OrdenBuilder.build();
						arrayBuilderOrden.add(pacienteOrdenJson);
					}
					if (listaOrdenes.size() > 0) {
						pacienteBuilder.add("Ordenes", arrayBuilderOrden.build());
					}

					pacienteJson = pacienteBuilder.build();
					arrayBuilder.add(pacienteJson);
					JsonObject root = rootBuilder.add("pacientes", arrayBuilder).build();
					writer.print(root);
				} else {
					JsonObjectBuilder pacienteBuilder = Json.createObjectBuilder();
					JsonObject pacienteJson;
					pacienteBuilder.add("Rut", paciente.getDpNrodocumento());
					pacienteJson = pacienteBuilder.build();
					arrayBuilder.add(pacienteJson);

				}
			} catch (BiosLISDAONotFoundException e) {
				LOGGER.debug(e.getMessage());
				JsonObject root = rootBuilder.add("pacientes", arrayBuilder).build();
				writer.print(root);
			}
		} catch (IOException | BiosLISDAOException | ParseException e) {
			LOGGER.error(e.getMessage());
		}

	}

	@RequestMapping(value = "/ReasignacionOrdenes", method = RequestMethod.POST, params = "UpdatePaciente")
	public ModelAndView updatePacienteReasignacion(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, IOException {

		try {
			int idPaciente1 = Integer.parseInt(request.getParameter("UpdatePaciente"));
			int idPaciente2 = Integer.parseInt(request.getParameter("UpdatePaciente2"));
			String[] ordenes = request.getParameterValues("Ordenes");
			String ordene = ordenes[0];

			DatosFichasDAO dfd = new DatosFichasDAO();
			dfd.updateOrdenxPaciente(idPaciente1, idPaciente2, ordene);
			return new ModelAndView("redirect:/ReasignacionOrdenes?message=Exito");
		} catch (Exception e) {
			throw e;
		}

	}

}
