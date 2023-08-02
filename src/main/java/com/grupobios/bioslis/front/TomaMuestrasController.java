package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisLogger;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgConvenioDAO;
import com.grupobios.bioslis.dao.CfgDerivadoresDAO;
import com.grupobios.bioslis.dao.CfgDiagnosticosDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgExamenesIndicacionesTMDAO;
import com.grupobios.bioslis.dao.CfgInstitucionesDeSaludDAO;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.dao.CfgPrioridadAtencionDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesTestDAO;
import com.grupobios.bioslis.dao.DatosFichasmuestrasDAO;
import com.grupobios.bioslis.dao.DatosPacientesDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LdvFichasEstadosTMDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgBaczonacuerpo;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgExamenesindicacionestm;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvFichasestadostm;
import com.grupobios.bioslis.entity.LogEventosfichas;

import java.util.logging.Level;

@Controller
public class TomaMuestrasController {

  private static final Logger LOGGER = LogManager.getLogger(TomaMuestrasController.class);
  ModelAndView mav = new ModelAndView();
  // DatosUsuarios usuario = new DatosUsuarios();

  @RequestMapping("/TomaMuestras")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes,
        ViewNames.ID_TOMA_MUESTRAS);
    if (sesion.getAttribute("usuario") != null) {
    	usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        try {
            LdvTiposdocumentosDAO tiposDocumentosDAO = new LdvTiposdocumentosDAO();//
            CfgMedicosDAO med = new CfgMedicosDAO();
            LdvSexoDAO sexoDAO = new LdvSexoDAO();
            CfgPrioridadAtencionDAO prior = new CfgPrioridadAtencionDAO();
            CfgTipoAtencionDAO tipoAt = new CfgTipoAtencionDAO();
            //CfgProcedenciasDAO getProce = new CfgProcedenciasDAO();
            CfgConvenioDAO getConvenio = new CfgConvenioDAO();
            CfgInstitucionesDeSaludDAO institSalud = new CfgInstitucionesDeSaludDAO();
            CfgExamenesDAO getExamn = new CfgExamenesDAO();
            CfgDiagnosticosDAO getDiag = new CfgDiagnosticosDAO();
            CfgServiciosDAO getServicios = new CfgServiciosDAO();
            DatosUsuariosDAO duDAO = new DatosUsuariosDAO();
            
            mav.addObject("listaServicios", getServicios.getServicios());
            mav.addObject("listaInsitucionesSalud", institSalud.getInstitucionesDeSalud());
            mav.addObject("listaConvenio", getConvenio.getConvenios());
            mav.addObject("listaSexo", sexoDAO.getSexo());
            mav.addObject("listaExamen", getExamn.getExamenes());
            //mav.addObject("listaProce", getProce.getProcedencias());
            mav.addObject("listaTiposDocumentos", tiposDocumentosDAO.getTiposDocumentos());
            mav.addObject("listaMedicos", med.getAllMedics());
            //      mav.addObject("listaPrior", prior.getPrior());
            mav.addObject("listaTipoAt", tipoAt.getTipoAtencion());
            mav.addObject("listaDiag", getDiag.getDiagnosticos());
            mav.addObject("listaUsuarios", duDAO.getUsuarios());
        } catch (BiosLISDAOException ex) {
            java.util.logging.Logger.getLogger(TomaMuestrasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //aqui se insea la accion que realiza usuario en logusuarios ***********    
	if(usuario != null) {
	      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	      
	      leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	      leu.setLeuIdevento(11);              
	      leu.setLeuIdacciondato(0);      
	      leu.setLeuValornuevo("accede a Toma de muestras");
	      logUsuarioDao.insertLogEventosUsuario(leu);
	}
     //*-************************************************************ 
    return mav;
  }

  // CAMBIAR A REST CONTROLLER
  @RequestMapping(value = "/TomaMuestras", method = RequestMethod.POST, params = "MostrarIndicaciones")
  public void MostrarIndicaciones(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException, Exception {
    try {
      Long nOrden = Long.parseLong(request.getParameter("nOrden"));
      Short idTipoMuestra = Short.parseShort(request.getParameter("idTipoMuestra"));
      Short idEnvase = Short.parseShort(request.getParameter("idEnvase"));
      Short idDerivador = Short.parseShort(request.getParameter("idDerivador"));
      String comparteMuestra = request.getParameter("comparteMuestra");
      String esCurva = request.getParameter("esCurva");

      CfgExamenesDAO examenesDAO = new CfgExamenesDAO();
      DatosFichasExamenesTestDAO dfetDAO = new DatosFichasExamenesTestDAO();
      CfgExamenesIndicacionesTMDAO cfgeitmDAO = new CfgExamenesIndicacionesTMDAO();

      List<DatosFichasexamenestest> listaTest = dfetDAO.buscarDFETforMuestras(nOrden, idTipoMuestra, idEnvase,
          idDerivador, comparteMuestra,esCurva);
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

      JsonObjectBuilder indicacionBuilder = Json.createObjectBuilder();
      JsonObject indicacionJson;

      String Indicaciones = null;
      String TAmbiente = null;
      String Refrigerado = null;
      String Congelado = null;
      List<Double> adulto = new ArrayList<>();
      List<Double> pediatra = new ArrayList<>();
      JsonArrayBuilder indicacionExamenes = Json.createArrayBuilder();
      // el switch nos sirve para validar que los exámenes no se repitan
      Long swIdExamen = -1L;
      for (DatosFichasexamenestest dfe : listaTest) {
        JsonObjectBuilder examenBuilder = Json.createObjectBuilder();
        JsonObject examenJson;

        if (!swIdExamen.equals(dfe.getId().getDfetIdexamen())) {
          swIdExamen = dfe.getId().getDfetIdexamen();
          List<CfgExamenes> examenes = examenesDAO.getIndicacionesXexamen(dfe.getId().getDfetIdexamen());

          for (CfgExamenes examene : examenes) {
            if (examene.getLdvIndicacionesexamenes() == null) {
              Indicaciones = "";
            } else {
              Indicaciones =   examene.getLdvIndicacionesexamenes().getLdvieDescripcion();
            }
            if (examene.getCeEstabilidadambiental() == null) {
              TAmbiente = "NO APLICA";
            } else {
              TAmbiente = examene.getCeEstabilidadambiental();
            }
            if (examene.getCeEstabilidadrefrigerado() == null) {
              Refrigerado = "NO APLICA";
            } else {
              Refrigerado = examene.getCeEstabilidadrefrigerado();
            }
            if (examene.getCeEstabilidadcongelado() == null) {
              Congelado = "NO APLICA";
            } else {
              Congelado = examene.getCeEstabilidadcongelado();
            }

            adulto.add((double) examene.getCeVolumenmuestraadulto());
            pediatra.add((double) examene.getCeVolumenmuestrapediatrica());
            examenBuilder.add("descpIndicacionExamen", Indicaciones).add("TAmbiente", TAmbiente)
                .add("Refrigerado", Refrigerado).add("Congelado", Congelado).add("Examen", examene.getCeAbreviado());
          }
          List<CfgExamenesindicacionestm> listaIndicacionesTM = cfgeitmDAO
              .getIndicacionesTM(dfe.getId().getDfetIdexamen());
          if (!listaIndicacionesTM.isEmpty()) {
            for (CfgExamenesindicacionestm listaiTM : listaIndicacionesTM) {
              examenBuilder.add("descpIndicacionExamenTM", listaiTM.getLdvIndicacionestm().getLdvitmDescripcionindicacion());
            }
          } else {
            examenBuilder.add("descpIndicacionExamenTM", "");
          }

          examenJson = examenBuilder.build();
          indicacionExamenes.add(examenJson);
        }

      }



      Double minAdulto = adulto.isEmpty() ? 0.0 : Collections.max(adulto);
      Double minPediatra = pediatra.isEmpty() ? 0.0 : Collections.max(pediatra);


      indicacionBuilder.add("minAdulto", minAdulto);
      indicacionBuilder.add("minPediatra", minPediatra);
      indicacionBuilder.add("indicacionesExamenes", indicacionExamenes);
      indicacionJson = indicacionBuilder.build();
      arrayBuilder.add(indicacionJson);

      JsonObject root = rootBuilder.add("indicaciones", arrayBuilder).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);

    } catch (Exception e) {
      throw e;
    }

  }

  // REVISAR SI CAMBIAR A REST CONTROLLER
  @RequestMapping(value = "/TomaMuestras", method = RequestMethod.POST, params = "cambiarEstadoPaciente")
  public void cambiarEstadoPaciente(HttpServletRequest request, HttpServletResponse response)
      throws ParseException, IOException, Exception {
      HttpSession sesion = (HttpSession) request.getSession();
      DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      Long idUsuario = usuario.getDuIdusuario();
    
    try {

      Long nOrden = Long.parseLong(request.getParameter("cambiarEstadoPaciente"));
      String estado = request.getParameter("estado");

    
      DatosFichasDAO dfd = new DatosFichasDAO();
      dfd.updateEstadoPaciente(nOrden, Byte.parseByte(estado), idUsuario);

    } catch (Exception e) {
      throw e;
    }

  }

  // CAMBIAR A REST CONTROLLER
  
  @RequestMapping(value = "/TomaMuestras", method = RequestMethod.POST, params = "cambiarFlebotomista")
  public void cambiarFlebotomista(HttpServletRequest request, HttpServletResponse response)
      throws IllegalAccessException, BiosLISDAOException {
	  HttpSession sesion = (HttpSession) request.getSession();
	    DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");
	    Long idUsuario = usuario.getDuIdusuario();
    try {
      Long nOrden = Long.parseLong(request.getParameter("cambiarFlebotomista"));
      Long idMuestra = Long.parseLong(request.getParameter("idMuestra"));
      Long flebotomista = Long.parseLong(request.getParameter("flebotomista"));
      DatosFichasmuestrasDAO dfmDao = new DatosFichasmuestrasDAO();
      DatosFichasmuestras muestra = dfmDao.getMuestraById(idMuestra);
     
    
      Long flebotomistaAntiguo  = muestra.getDfmIdusuariotm();
     
      muestra.setDfmIdusuariotm(flebotomista);
      dfmDao.updateDatosFichasmuestras(muestra);
     //aqui realizar log evento  ***********************************
      DatosFichasDAO  datosFichasDAO = new  DatosFichasDAO();
      DatosFichas df =  datosFichasDAO.getOrdenxID( nOrden.intValue());
      LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
      LogEventosfichas lef = new LogEventosfichas();
      
      lef.setDatosFichas(  (int) df.getDfNorden());
      lef.setLefFechaorden(df.getDfFechaorden());
      lef.setLefIdpaciente(df.getDatosPacientes());
      lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
      lef.setLefIdusuario( idUsuario.intValue());
      lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
      lef.setLefNombrecampo("DFM_IDUSUARIOTM");
      lef.setCfgEventos(2);
      lef.setLefCodigobarra(muestra.getDfmCodigobarra());
      lef.setLefIdmuestra((int) muestra.getDfmIdmuestra());
      DatosUsuariosDAO datosUsuariosDAO = new DatosUsuariosDAO();
      
      DatosUsuarios usuarioAnterior = (DatosUsuarios) datosUsuariosDAO.getUsuarioById(flebotomistaAntiguo);
      DatosUsuarios usuarioNuevo = (DatosUsuarios) datosUsuariosDAO.getUsuarioById(flebotomista);
      if(usuarioAnterior != null) {
    	  lef.setLefValoranterior(usuarioAnterior.getDuNombres()+ " "+ usuarioAnterior.getDuPrimerapellido());
      }
      if(usuarioAnterior != null) {
    	  lef.setLefValornuevo(usuarioNuevo.getDuNombres() + " "+ usuarioNuevo.getDuPrimerapellido());
      }
      logEventosfichasDAO.insertLogEventosFichas(lef);
    // *******************************************************  
    } catch ( IllegalArgumentException e) {
      throw e;
    }
  }

  // CAMBIAR A REST CONTROLLER
  @RequestMapping(value = "/TomaMuestras", method = RequestMethod.POST, params = "cambiarDerivador")
  public void cambiarDerivador(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, BiosLISDAOException {
    Long nOrden = Long.parseLong(request.getParameter("nOrden"));
    Long idExamen = Long.parseLong(request.getParameter("idExamen"));
    Short idDerivador = Short.parseShort(request.getParameter("idDerivador"));

    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    Long idUsuario = usuario.getDuIdusuario();
    
    DatosFichasExamenesDAO dfeDAO = new DatosFichasExamenesDAO();
  //  BiosLisLogger bl = new BiosLisLogger();
    DatosFichasDAO  datosFichasDAO = new  DatosFichasDAO();
    CfgDerivadoresDAO cd = new CfgDerivadoresDAO();
    
    DatosFichasexamenes dfe = dfeDAO.getDatosFichasExamenesByExamenyOrden(idExamen, nOrden);
    Short derivador = dfe.getDfeIdderivador();
    
    dfe.setDfeIdderivador(idDerivador);
    dfeDAO.updateDatosFichasExamenes(dfe);
    
    DatosFichas df =  datosFichasDAO.getOrdenxID((int) dfe.getIddatosFichasExamenes().getDfeNorden());
    
    CfgDerivadores valorAnterior = cd.getDerivadorById(derivador);     
    CfgDerivadores valorNuevo= cd.getDerivadorById(idDerivador);
    LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();
    
    lef.setDatosFichas(  (int) df.getDfNorden());
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefIdpaciente(df.getDatosPacientes());
    lef.setLefNombretabla("DATOS_FICHASEXAMENES");
    lef.setLefIdusuario( idUsuario.intValue());
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefNombrecampo("DFE_IDDERIVADOR");
    lef.setCfgEventos(2);
    
    lef.setLefIdexamen((int) dfe.getIddatosFichasExamenes().getDfeIdexamen());       
    lef.setLefValoranterior(valorAnterior.getCderivDescripcion());
    lef.setLefValornuevo(valorNuevo.getCderivDescripcion());
    
    logEventosfichasDAO.insertLogEventosFichas(lef);
    
/*
    try {
      bl.logInsertDatosFichaTableRecord(DatosFichasexamenes.class, dfe, new BigDecimal(2),
          BiosLisCalendarService.getInstance().getTS(), new BigDecimal(nOrden), null, new BigDecimal(idExamen), null,
          null, null, "", Constante.MODIFICA_DATOS_FICHAS);
    } catch (IllegalArgumentException e1) {
      LOGGER.error("No se pudo insertar registro de log de tabla.\n{}", e1.getMessage());
    }
    */
  }

  // CAMBIAR A REST CONTROLLER
  @RequestMapping(value = "/TomaMuestras", method = RequestMethod.POST, params = "cambiarInstitucion")
  public void cambiarInstitucion(HttpServletRequest request, HttpServletResponse response)
      throws IllegalAccessException, BiosLISDAOException {
    Short idSeccion = Short.parseShort(request.getParameter("idSeccion"));
    Long nOrden = Long.parseLong(request.getParameter("nOrden"));
    Long idExamen = Long.parseLong(request.getParameter("idExamen"));

  //  BiosLisLogger bl = new BiosLisLogger();
    DatosFichasExamenesTestDAO dfetDao = new DatosFichasExamenesTestDAO();
    List<DatosFichasexamenestest> ldfet = dfetDao.buscarPorOrdenyExamenList(nOrden, idExamen);
    for (DatosFichasexamenestest dfet : ldfet) {
      dfet.setDfetIdseccion(idSeccion);
      dfetDao.updateDFExamenesTest(dfet);
      /*
      try {
        bl.logInsertDatosFichaTableRecord(DatosFichasexamenestest.class, dfet, new BigDecimal(2),
            BiosLisCalendarService.getInstance().getTS(), new BigDecimal(nOrden), null, new BigDecimal(idExamen), null,
            null, null, "", Constante.MODIFICA_DATOS_FICHAS);
      } catch (IllegalArgumentException e1) {
        LOGGER.error("No se pudo insertar registro de log de tabla.\n{}", e1.getMessage());
      }
      */
    }

  }


}
