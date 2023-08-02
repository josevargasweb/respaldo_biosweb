package com.grupobios.bioslis.front;



import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCentrosDeSaludDAO;
import com.grupobios.bioslis.dao.CfgConvenioDAO;
import com.grupobios.bioslis.dao.CfgDiagnosticosDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgInstitucionesDeSaludDAO;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.dao.CfgPrioridadAtencionDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.LdvCargosUsuariosDAO;
import com.grupobios.bioslis.dao.LdvProfesionesUsuariosDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;




@Controller
public class TrazabilidadController {

	
	  ModelAndView mav = new ModelAndView();

	    @RequestMapping(value = "/AccionesUsuarios", method = RequestMethod.GET)
	    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
		        RedirectAttributes attributes) { 
	    	 HttpSession sesion = (HttpSession) request.getSession();
	    	 DatosUsuarios usuario = null; 
	    	 if (sesion.getAttribute("usuario") != null) {
		      	 usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	      }
	      
	    	 
	    	 
	    	LdvCargosUsuariosDAO lcuDao = new LdvCargosUsuariosDAO();
            CfgCentrosDeSaludDAO cmcDao = new CfgCentrosDeSaludDAO();
            LdvSexoDAO lsDao = new LdvSexoDAO();
            CfgProcedenciasDAO cpDao = new CfgProcedenciasDAO();
            LdvProfesionesUsuariosDAO lpuDao = new LdvProfesionesUsuariosDAO();
            try {
				mav.addObject("listaCargosUsuario", lcuDao.getCargosUsuarios());
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				mav.addObject("listaCentrosdeSalud", cmcDao.getCentrosDeSalud());
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            mav.addObject("listaSexo", lsDao.getSexo());
            //mav.addObject("listaProcedencias", cpDao.getProcedencias());
            mav.addObject("listaProfesiones", lpuDao.getProfesionesUsuarios());
	      mav.setViewName("/AccionesUsuarios");
	      
	      
	      //aqui se inserta la accion que realiza usuario en modulos  ***********  
	      if(usuario != null) {
	        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	  	      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	  	      
	  	      leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	  	      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	  	      leu.setLeuIdevento(11);              
	  	      leu.setLeuIdacciondato(0);	      
	  	      leu.setLeuValornuevo("accede a acciones usuarios");
	  	      logUsuarioDao.insertLogEventosUsuario(leu);
	      }
	       //*-************************************************************ 
	      
	      return mav;
	    }
	    
	    
	    @RequestMapping("/AccionesOrdenes")
	    public ModelAndView pageLoadOrdenes(HttpServletRequest request, HttpServletResponse response,
	        RedirectAttributes attributes) throws BiosLISDAOException {
	      HttpSession sesion = (HttpSession) request.getSession();
	      
	      DatosUsuarios usuario = null;
	      
	      
	     // mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, 5);
	      if (sesion.getAttribute("usuario") != null) {
	      	 usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	        LdvTiposdocumentosDAO tiposDocumentosDAO = new LdvTiposdocumentosDAO();//
	        CfgMedicosDAO med = new CfgMedicosDAO();
	        LdvSexoDAO sexoDAO = new LdvSexoDAO();
	        CfgPrioridadAtencionDAO prior = new CfgPrioridadAtencionDAO();
	        CfgTipoAtencionDAO tipoAt = new CfgTipoAtencionDAO();
	        CfgProcedenciasDAO getProce = new CfgProcedenciasDAO();
	        CfgConvenioDAO getConvenio = new CfgConvenioDAO();
	        CfgInstitucionesDeSaludDAO institSalud = new CfgInstitucionesDeSaludDAO();
	        CfgExamenesDAO getExamn = new CfgExamenesDAO();
	        CfgDiagnosticosDAO getDiag = new CfgDiagnosticosDAO();
	        CfgServiciosDAO getServicios = new CfgServiciosDAO();

	        //
	        mav.addObject("listaServicios", getServicios.getServicios());
	        mav.addObject("listaInsitucionesSalud", institSalud.getInstitucionesDeSalud());
	        mav.addObject("listaConvenio", getConvenio.getConvenios());
	        mav.addObject("listaSexo", sexoDAO.getSexo());
	        mav.addObject("listaExamen", getExamn.getExamenes());
	        //mav.addObject("listaProce", getProce.getProcedencias());
	        mav.addObject("listaTiposDocumentos", tiposDocumentosDAO.getTiposDocumentos());
	        mav.addObject("listaMedicos", med.getAllMedics());
	          try {
	              mav.addObject("listaPrior", prior.getPrior());
	          } catch (BiosLISDAOException ex) {
	              java.util.logging.Logger.getLogger(EdicionOrdenesController.class.getName()).log(Level.SEVERE, null, ex);
	          }
	        mav.addObject("listaTipoAt", tipoAt.getTipoAtencion());
	        mav.addObject("listaDiag", getDiag.getDiagnosticos());
	      }
	      
	      
	      //aqui se inserta la accion que realiza usuario en modulos  ***********  
	      if(usuario != null) {
	        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	  	      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	  	      
	  	      leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	  	      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	  	      leu.setLeuIdevento(11);              
	  	      leu.setLeuIdacciondato(0);	      
	  	      leu.setLeuValornuevo("accede a acciones órdenes");
	  	      logUsuarioDao.insertLogEventosUsuario(leu);
	      }
	       //*-************************************************************ 
	      
	      mav.setViewName("/AccionesOrdenes");
	      
	      return mav;
	    }
	    
	    
}
