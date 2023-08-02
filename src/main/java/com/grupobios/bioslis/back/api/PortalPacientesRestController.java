package com.grupobios.bioslis.back.api;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.DominiosService;
import com.grupobios.bioslis.bs.PacienteService;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgPerfilesusuariosDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.DatosEditarOrdenDTO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.dto.PortalDatosPacienteOrdenExamenDTO;
import com.grupobios.bioslis.entity.CfgPerfilesusuarios;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvTiposdocumentos;
import com.grupobios.common.CifradoMD5;

@RestController()
public class PortalPacientesRestController {

	private static Logger logger = LogManager.getLogger(PortalPacientesRestController.class);

	DatosUsuariosDAO duDao = new DatosUsuariosDAO();

	@Autowired
	private DominiosService dominiosService;

	@Autowired
	PacienteService pacienteService;

	public DominiosService getDominiosService() {
		return dominiosService;
	}

	public void setDominiosService(DominiosService dominiosService) {
		this.dominiosService = dominiosService;
	}

	public PacienteService getPacienteService() {
		return pacienteService;
	}

	public void setPacienteService(PacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	// entrega todos los tipos de documentos.
	@GetMapping("/api/portal/documentos")
	public ResponseTemplateGen<List<LdvTiposdocumentos>> getTiposDeDocumentos() {

		try {
			List<LdvTiposdocumentos> resultado = new ArrayList<LdvTiposdocumentos>();
			resultado = dominiosService.getTiposDeDocumentos();
			return new ResponseTemplateGen<List<LdvTiposdocumentos>>(resultado, 200, "OK");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseTemplateGen<List<LdvTiposdocumentos>>(null, 505,
					"No se obtuvo información ".concat(e.getMessage()));
		}
	}

	// entrega UN OBJETO CON DATOS SI EXISTE , SI NO EXISTE ENTREGA NULL
	@GetMapping("/api/portal/datosPacientesExiste/{rutPac}/{nOrden}/{idDocumento}")
	public ResponseTemplateGen<Object> getDatosPacientesExiste(@PathVariable("rutPac") String rutPac,
			@PathVariable("nOrden") Long nOrden, @PathVariable("idDocumento") Long idDocumento,  @Context HttpServletRequest context)
			throws BiosLISDAOException {
		HttpSession sesion = context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {

			Object resultado = pacienteService.getDatosPacientesExiste(rutPac, nOrden, idDocumento);

			return new ResponseTemplateGen<Object>(resultado, 200, "OK");
		} catch (BiosLISDAOException e) {
			logger.error(e.getMessage());
			return new ResponseTemplateGen<Object>(null, 505, "No se obtuvo información ".concat(e.getMessage()));
		}
	}

	// entrega datos de paciente y examenes de la orden
	@GetMapping("/api/portal/datosPacientesOrden/{run}/{nOrden}/{tDocumento}/{tBusqueda}")
	public ResponseTemplateGen<PortalDatosPacienteOrdenExamenDTO> getdatosPacientesOrden(
			@PathVariable("run") String run, @PathVariable("nOrden") Long nOrden,
			@PathVariable("tDocumento") int tDocumento, @PathVariable("tBusqueda") int tBusqueda,  @Context HttpServletRequest context) throws BiosLISDAOException {
		HttpSession sesion = context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			PortalDatosPacienteOrdenExamenDTO resultado = pacienteService.getdatosPacientesOrden(run, nOrden,
					tDocumento, tBusqueda);
			return new ResponseTemplateGen<PortalDatosPacienteOrdenExamenDTO>(resultado, 200, "OK");
		} catch (BiosLISDAOException e) {
			logger.error(e.getMessage());
			return new ResponseTemplateGen<PortalDatosPacienteOrdenExamenDTO>(null, 505,
					"No se obtuvo información ".concat(e.getMessage()));
		}
	}

	// ***************LOGIN *********************************
	@PostMapping("/api/portal/datospacientelogin")
	public ResponseTemplateGen<Map<String, String>> getValidaLogin(@RequestBody Map<String, String> username) {
		
		try {
			DatosUsuarios usuario = duDao.getUsuarioByUsername(username.get("user").toUpperCase());
			Map<String, String> datosUsuario = new HashMap<String, String>();
			String activo = usuario.getDuActivo();
			String pwd = CifradoMD5.encode(username.get("password"));

			if (pwd.equals(usuario.getDuPassword())) {
				// Si la contraseña ha caducado
				if (usuario.getDuPasswordexpira().equals("S") && (usuario.getDuFechacaducapassword()
						.equals(BiosLisCalendarService.getInstance().getDate())
						|| usuario.getDuFechacaducapassword().before(BiosLisCalendarService.getInstance().getDate()))) {
					return new ResponseTemplateGen<Map<String, String>>(null, 300, "Contraseña Caducada ");
				}

				if (activo.equals("N")) {
					return new ResponseTemplateGen<Map<String, String>>(null, 300, "Usuario no activo ");
				}

				String procedencia = pacienteService.getDatosLoginUsuario(usuario.getDuIdusuario());
				datosUsuario.put("usuario", username.get("user"));
				datosUsuario.put("clave", username.get("password"));
				datosUsuario.put("procedencia", procedencia);

				  
				
				 LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
				    LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
				    
				 
				    leu.setLeuIdusuario((int) usuario.getDuIdusuario());
				   
				    leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
				    
				    leu.setLeuIdevento(7);
				    leu.setLeuIdacciondato(0);
				    leu.setLeuValornuevo("Portal de Pacientes");    
				  
				    logUsuarioDao.insertLogEventosUsuario(leu);
				     //*-************************************************************  
				
				return new ResponseTemplateGen<Map<String, String>>(datosUsuario, 200, "Ok");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseTemplateGen<Map<String, String>>(null, 300, "no encontrado");
		}

		
		return new ResponseTemplateGen<Map<String, String>>(null, 300, "no encontrado");

	}

	
	//realizado por cristian 13-10  --  para editar orden
	@GetMapping("/api/portal/editarorden/paciente/{id}/nOrden/{nOrden}")
    public ResponseTemplateGen<DatosEditarOrdenDTO> getPacienteMedicoExamenesPatologias(
            @PathVariable("id") Long id, @PathVariable("nOrden") Long nOrden,  @Context HttpServletRequest context) throws BiosLISDAOException {
		HttpSession sesion = context.getSession();
	      DatosUsuarios usuario = null;
	      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      if (usuario == null)
	        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        try {
            DatosEditarOrdenDTO resultado = pacienteService.getPacienteMedicoExamenesPatologias(id, nOrden);
            return new ResponseTemplateGen<DatosEditarOrdenDTO>(resultado, 200, "OK");
        } catch (BiosLISDAOException e) {
            logger.error(e.getMessage());
            return new ResponseTemplateGen<DatosEditarOrdenDTO>(null, 505,"No se obtuvo información ".concat(e.getMessage()));
        }
    }
}
