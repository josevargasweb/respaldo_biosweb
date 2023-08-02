package com.grupobios.bioslis.back.api;

import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.EstadisticaService;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.AnalizadorDTO;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.DatosLineaTiempoDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.dto.MuestrasRMDTO;
import com.grupobios.bioslis.dto.TestEdicionOrdenDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;


//creado por cristian 22-11-2022
@RestController
public class EstadisticaRestController {

    private static Logger logger = LogManager.getLogger(EstadisticaRestController.class);
    private EstadisticaService estadisticaService;

    public EstadisticaService getEstadisticaService() {
        return estadisticaService;
    }

    public void setEstadisticaService(EstadisticaService estadisticaService) {
        this.estadisticaService = estadisticaService;
    }
    
    
    @PostMapping("/api/estadistica/lineatiempoorden")
    public ResponseTemplateGen< List<DatosLineaTiempoDTO>> getEstadisticaLineaTiempo(@RequestBody BCFichaFiltroDTO bcFichaFiltroDTO, @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
     List<DatosLineaTiempoDTO> result = null;
      try {
        result = estadisticaService.getEstadisticaDatosLineaTiempo(bcFichaFiltroDTO);  
        
  // aqui se inserta la accion que realiza usuario en modulos ***********

        
        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        leu.setLeuIdusuario((int) usuario.getDuIdusuario());
        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
        leu.setLeuIdevento(11);
        leu.setLeuIdacciondato(0);       		       
        leu.setLeuValornuevo("Se visualiza estadística: Tiempo entrega examenes");
        logUsuarioDao.insertLogEventosUsuario(leu);
    
      // *-************************************************************
        
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
        return new ResponseTemplateGen<List<DatosLineaTiempoDTO>>(result, 505, "No se encontro registros");
      }
      return new ResponseTemplateGen<List<DatosLineaTiempoDTO>>(result, 200, "Ok");
    }
    
    
    @PostMapping("/api/descarga/estadistica/lineatiempoorden")
    public ResponseTemplateGen< List<DatosLineaTiempoDTO>> getDescargaEstadisticaLineaTiempo(@RequestBody BCFichaFiltroDTO bcFichaFiltroDTO, @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
     List<DatosLineaTiempoDTO> result = null;
      try {
        result = estadisticaService.getEstadisticaDatosLineaTiempo(bcFichaFiltroDTO);  
        
  // aqui se inserta la accion que realiza usuario en modulos ***********

        
        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        leu.setLeuIdusuario((int) usuario.getDuIdusuario());
        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
        leu.setLeuIdevento(11);
        leu.setLeuIdacciondato(0);       		       
        leu.setLeuValornuevo("Se descarga la información de estadística: Tiempo entrega examenes");
        logUsuarioDao.insertLogEventosUsuario(leu);
    
      // *-************************************************************
        
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
        return new ResponseTemplateGen<List<DatosLineaTiempoDTO>>(result, 505, "No se encontro registros");
      }
      return new ResponseTemplateGen<List<DatosLineaTiempoDTO>>(result, 200, "Ok");
    }
    
    
    @PostMapping("/api/estadistica/totalporexamen")
    public ResponseTemplateGen< List<ExamenOrdenDTO>> getEstadisticaTotalPorExamen(@RequestBody BCFichaFiltroDTO bcFichaFiltroDTO, @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        List<ExamenOrdenDTO> result = null;
      try {
        result = estadisticaService.getEstadisticaTotalPorExamen(bcFichaFiltroDTO);    
        
        // aqui se inserta la accion que realiza usuario en modulos ***********

       
          LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
          LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
          leu.setLeuIdusuario((int) usuario.getDuIdusuario());
          leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
          leu.setLeuIdevento(11);
          leu.setLeuIdacciondato(0);       		       
          leu.setLeuValornuevo("Se visualiza estadística: Total por Examen");
          logUsuarioDao.insertLogEventosUsuario(leu);
      
        // *-************************************************************
        
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
        return new ResponseTemplateGen<List<ExamenOrdenDTO>>(result, 505, "No se encontro registros");
      }
      return new ResponseTemplateGen<List<ExamenOrdenDTO>>(result, 200, "Ok");
    }
    
    
    @PostMapping("/api/descarga/estadistica/totalporexamen")
    public ResponseTemplateGen< List<ExamenOrdenDTO>> getDescargaEstadisticaTotalPorExamen(@RequestBody BCFichaFiltroDTO bcFichaFiltroDTO, @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        List<ExamenOrdenDTO> result = null;
      try {
        result = estadisticaService.getEstadisticaTotalPorExamen(bcFichaFiltroDTO);    
        
        // aqui se inserta la accion que realiza usuario en modulos ***********

       
          LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
          LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
          leu.setLeuIdusuario((int) usuario.getDuIdusuario());
          leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
          leu.setLeuIdevento(11);
          leu.setLeuIdacciondato(0);       		       
          leu.setLeuValornuevo("Se descarga la información de estadística: Total por Examen");
          logUsuarioDao.insertLogEventosUsuario(leu);
      
        // *-************************************************************
        
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
        return new ResponseTemplateGen<List<ExamenOrdenDTO>>(result, 505, "No se encontro registros");
      }
      return new ResponseTemplateGen<List<ExamenOrdenDTO>>(result, 200, "Ok");
    }
    
    
    @PostMapping("/api/estadistica/muestrasrechazadas")
    public ResponseTemplateGen< List<MuestrasRMDTO>> getEstadisticaMuestrasRechazadas(@RequestBody BCFichaFiltroDTO bcFichaFiltroDTO,  @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
     List<MuestrasRMDTO> result = null;
      try {
        result = estadisticaService.getEstadisticaMuestrasRechazadas(bcFichaFiltroDTO);    
        
        // aqui se inserta la accion que realiza usuario en modulos ***********

        
        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        leu.setLeuIdusuario((int) usuario.getDuIdusuario());
        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
        leu.setLeuIdevento(11);
        leu.setLeuIdacciondato(0);       		       
        leu.setLeuValornuevo("Se visualiza estadística: Muestras rechazadas");
        logUsuarioDao.insertLogEventosUsuario(leu);
    
      // *-************************************************************
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
        return new ResponseTemplateGen<List<MuestrasRMDTO>>(result, 505, "No se encontro registros");
      }
      return new ResponseTemplateGen<List<MuestrasRMDTO>>(result, 200, "Ok");
    }
    
    
    @PostMapping("/api/descarga/estadistica/muestrasrechazadas")
    public ResponseTemplateGen< List<MuestrasRMDTO>> getDescargaEstadisticaMuestrasRechazadas(@RequestBody BCFichaFiltroDTO bcFichaFiltroDTO,  @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
     List<MuestrasRMDTO> result = null;
      try {
        result = estadisticaService.getEstadisticaMuestrasRechazadas(bcFichaFiltroDTO);    
        
        // aqui se inserta la accion que realiza usuario en modulos ***********

        
        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        leu.setLeuIdusuario((int) usuario.getDuIdusuario());
        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
        leu.setLeuIdevento(11);
        leu.setLeuIdacciondato(0);       		       
        leu.setLeuValornuevo("Se descarga la información de estadística: Muestras rechazadas");
        logUsuarioDao.insertLogEventosUsuario(leu);
    
      // *-************************************************************
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
        return new ResponseTemplateGen<List<MuestrasRMDTO>>(result, 505, "No se encontro registros");
      }
      return new ResponseTemplateGen<List<MuestrasRMDTO>>(result, 200, "Ok");
    }
    
    @PostMapping("/api/estadistica/ExamenesDerivados")
    public ResponseTemplateGen< List<ExamenOrdenDTO>> getEstadisticaExamenesDerivados(@RequestBody BCFichaFiltroDTO bcFichaFiltroDTO,  @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        List<ExamenOrdenDTO> result = null;
      try {
        result = estadisticaService.getEstadisticaExamenesDerivados(bcFichaFiltroDTO);     
        
  // aqui se inserta la accion que realiza usuario en modulos ***********

        
        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        leu.setLeuIdusuario((int) usuario.getDuIdusuario());
        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
        leu.setLeuIdevento(11);
        leu.setLeuIdacciondato(0);       		       
        leu.setLeuValornuevo("Se visualiza estadística: Examenes derivados");
        logUsuarioDao.insertLogEventosUsuario(leu);
    
      // *-************************************************************
        
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
        return new ResponseTemplateGen<List<ExamenOrdenDTO>>(result, 505, "No se encontro registros");
      }
      return new ResponseTemplateGen<List<ExamenOrdenDTO>>(result, 200, "Ok");
    }
    
    @PostMapping("/api/descarga/estadistica/ExamenesDerivados")
    public ResponseTemplateGen< List<ExamenOrdenDTO>> getDescargaEstadisticaExamenesDerivados(@RequestBody BCFichaFiltroDTO bcFichaFiltroDTO,  @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
        List<ExamenOrdenDTO> result = null;
      try {
        result = estadisticaService.getEstadisticaExamenesDerivados(bcFichaFiltroDTO);     
        
  // aqui se inserta la accion que realiza usuario en modulos ***********

        
        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        leu.setLeuIdusuario((int) usuario.getDuIdusuario());
        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
        leu.setLeuIdevento(11);
        leu.setLeuIdacciondato(0);       		       
        leu.setLeuValornuevo("Se descarga la información de estadística: Examenes derivados");
        logUsuarioDao.insertLogEventosUsuario(leu);
    
      // *-************************************************************
        
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
        return new ResponseTemplateGen<List<ExamenOrdenDTO>>(result, 505, "No se encontro registros");
      }
      return new ResponseTemplateGen<List<ExamenOrdenDTO>>(result, 200, "Ok");
    }
    
    @PostMapping("/api/estadistica/resultadoscriticos")
    public ResponseTemplateGen< List<TestEdicionOrdenDTO>> getEstadisticaResultadosCriticos(@RequestBody BCFichaFiltroDTO bcFichaFiltroDTO,  @Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        if (usuario == null)
            return new ResponseTemplateGen<>(null, 401, "No hay sesion");
     List<TestEdicionOrdenDTO> result = null;
      try {
        result = estadisticaService.getEstadisticaResultadosCriticos(bcFichaFiltroDTO);   
        
  // aqui se inserta la accion que realiza usuario en modulos ***********

        
        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
        leu.setLeuIdusuario((int) usuario.getDuIdusuario());
        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
        leu.setLeuIdevento(11);
        leu.setLeuIdacciondato(0);       		       
        leu.setLeuValornuevo("Se visualiza estadística: Resultados Críticos");
        logUsuarioDao.insertLogEventosUsuario(leu);
    
      // *-************************************************************
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
        return new ResponseTemplateGen<List<TestEdicionOrdenDTO>>(result, 505, "No se encontro registros");
      }
      return new ResponseTemplateGen<List<TestEdicionOrdenDTO>>(result, 200, "Ok");
    }
    
    @GetMapping("/api/query/{query}")
    public ResponseTemplateGen<String> getQueryEstadistica(@PathVariable("query") String query,@Context HttpServletRequest context) {
        HttpSession sesion = context.getSession();
        DatosUsuarios usuario = null;
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        try {
        	// aqui se inserta la accion que realiza usuario en modulos ***********

            
            LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
            LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
            leu.setLeuIdusuario((int) usuario.getDuIdusuario());
            leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
            leu.setLeuIdevento(11);
            leu.setLeuIdacciondato(0);       		       
            leu.setLeuValornuevo("Se visualiza la consulta de la estadística: " + query);
            logUsuarioDao.insertLogEventosUsuario(leu);
        
          // *-************************************************************
            return new ResponseTemplateGen<>(null, 200, "Ok");
        } catch (Exception e) {           
            return new ResponseTemplateGen<>(null, 500, e.getMessage());
        }
    }
    
    
}
