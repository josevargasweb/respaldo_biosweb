/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisLogger;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesTestDAO;
import com.grupobios.bioslis.dao.DatosFichas_DAO;
import com.grupobios.bioslis.dao.DatosFichasmuestrasDAO;
import com.grupobios.bioslis.dao.DatosMuestrasRechazadasDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ExamenPacienteDTO;
import com.grupobios.bioslis.dto.FichaFiltroDTO;
import com.grupobios.bioslis.dto.MuestraRechazadaDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.dto.OrdenesTMDTO;
import com.grupobios.bioslis.entity.CfgCausasrechazosmuestras;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosMuestrasrechazadas;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LogEventosfichas;
import com.grupobios.bioslis.microbiologia.dao.BiosLISDaoException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MarcoC
 */
public class RechazoMuestrasServiceImpl implements RechazoMuestrasService {

    private DatosFichasmuestrasDAO datosFichasmuestrasDAO;
    private DatosFichas_DAO datosFichas_dao;
    private DatosMuestrasRechazadasDAO datosMuestrasRechazadasDAO;
    private CfgExamenesDAO cfgExamenesDAO;
    private DatosFichasExamenesDAO datosFichasExamenesDAO;
    private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;
    
    BiosLisLogger bl = new BiosLisLogger();
    
    /*** GETTERS Y SETTERS ***/
    public DatosFichasmuestrasDAO getDatosFichasmuestrasDAO() {
        return datosFichasmuestrasDAO;
    }

    public void setDatosFichasmuestrasDAO(DatosFichasmuestrasDAO datosFichasmuestrasDAO) {
        this.datosFichasmuestrasDAO = datosFichasmuestrasDAO;
    }

    public DatosFichas_DAO getDatosFichas_dao() {
        return datosFichas_dao;
    }

    public void setDatosFichas_dao(DatosFichas_DAO datosFichas_dao) {
        this.datosFichas_dao = datosFichas_dao;
    }

    public DatosMuestrasRechazadasDAO getDatosMuestrasRechazadasDAO() {
        return datosMuestrasRechazadasDAO;
    }

    public void setDatosMuestrasRechazadasDAO(DatosMuestrasRechazadasDAO datosMuestrasRechazadasDAO) {
        this.datosMuestrasRechazadasDAO = datosMuestrasRechazadasDAO;
    }

    public CfgExamenesDAO getCfgExamenesDAO() {
        return cfgExamenesDAO;
    }

    public void setCfgExamenesDAO(CfgExamenesDAO cfgExamenesDAO) {
        this.cfgExamenesDAO = cfgExamenesDAO;
    }

    public DatosFichasExamenesDAO getDatosFichasExamenesDAO() {
        return datosFichasExamenesDAO;
    }

    public void setDatosFichasExamenesDAO(DatosFichasExamenesDAO datosFichasExamenesDAO) {
        this.datosFichasExamenesDAO = datosFichasExamenesDAO;
    }

    public DatosFichasExamenesTestDAO getDatosFichasExamenesTestDAO() {
        return datosFichasExamenesTestDAO;
    }

    public void setDatosFichasExamenesTestDAO(DatosFichasExamenesTestDAO datosFichasExamenesTestDAO) {
        this.datosFichasExamenesTestDAO = datosFichasExamenesTestDAO;
    }
    /*** END GETTERS Y SETTERS 
     * @throws BiosLISDAOException ***/
    
    @Override
    public List<OrdenesTMDTO> getOrdenesRechazoMuestras(FichaFiltroDTO ffDto) throws BiosLISDAOException {
        return datosFichas_dao.getOrdenesRechazoMuestras(ffDto);
    }
    
    @Override
    public List<MuestrasDTO> getListaMuestrasRechazo(long nOrden) throws BiosLISDAOException {
        List<MuestrasDTO> listaMuestras = datosFichasmuestrasDAO.obtenerMuestrasRechazo(nOrden);
        
        for (MuestrasDTO muestra : listaMuestras){
            if (muestra.getESTADOTM().equals(EstadosSistema.DFM_ESTADOTM_RECHAZADA)){
                DatosMuestrasrechazadas muestraRechazada = datosMuestrasRechazadasDAO.getMuestraRechazadaById(muestra.getIDMUESTRA().longValue());
                if (muestraRechazada.getDmrRechazoparcialototal().equals(EstadosSistema.DMR_RECHAZOPARCIALOTOTAL_TOTAL)){
                    DatosFichasmuestras nuevaMuestra = datosFichasmuestrasDAO.getMuestraById(muestraRechazada.getDmrIdnuevamuestra());
                    if (!nuevaMuestra.getDfmEstadotm().equals(EstadosSistema.DFM_ESTADOTM_RECHAZADA)){
                        List<Object[]> listObject = datosFichasmuestrasDAO.obtenerMuestrayEnvaseMuestraRechazada(nuevaMuestra.getDfmIdmuestra());
                        for (Object[] obj : listObject){
                            muestra.setMUESTRADESC(obj[0].toString());
                            muestra.setENVASEDESC(obj[1].toString());
                        }
                    } else {
                        DatosMuestrasrechazadas muestraRechazada2 = datosMuestrasRechazadasDAO.getMuestraRechazadaById(nuevaMuestra.getDfmIdmuestra());
                        DatosFichasmuestras nuevaMuestra2 = datosFichasmuestrasDAO.getMuestraById(muestraRechazada2.getDmrIdnuevamuestra());
                        List<Object[]> listObject = datosFichasmuestrasDAO.obtenerMuestrayEnvaseMuestraRechazada(nuevaMuestra2.getDfmIdmuestra());
                        for (Object[] obj : listObject){
                            muestra.setMUESTRADESC(obj[0].toString());
                            muestra.setENVASEDESC(obj[1].toString());
                        }
                    }
                }
            }
            List<ExamenOrdenDTO> listExamenes = cfgExamenesDAO.getExamenesByMuestra(muestra.getIDMUESTRA().longValue());
            int countNoAnulados = 0;
            for (ExamenOrdenDTO ex: listExamenes){
                if (ex.getDFE_EXAMENANULADO().equals("N") || ex.getDFE_EXAMENANULADO() == null){
                    countNoAnulados++;
                }
            }
            if (listExamenes.size()>0 && countNoAnulados==0){
                muestra.setALLEXAMENESANULADOS("S");
            } else {
                muestra.setALLEXAMENESANULADOS("N");
            }
            
            //muestra.setALLEXAMENESANULADOS(countNoAnulados > 0 ? "N" : "S");
        }
        return listaMuestras;
    }

    @Override
    public MuestraRechazadaDTO getMuestraRechazo(long idMuestra) throws BiosLISDAOException {
        List<ExamenPacienteDTO> listExamenes = datosFichas_dao.getListaExamenesPaciente(idMuestra);
        MuestraRechazadaDTO muestra = datosMuestrasRechazadasDAO.obtenerMuestraRechazada(idMuestra);
        muestra.setListaExamenes(listExamenes);
        return muestra;
    }

    @Override
    public DatosFichasmuestras rechazoTotalMuestra(long idMuestra, long idUsuario, byte causaRechazo, String observacion) throws BiosLISDAOException {
        try {
            DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(idMuestra);
            String estadoMuestraAntiguo = dfm.getDfmEstadotm();
            dfm.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_RECHAZADA);
            dfm.setDfmEstadorm(null);
            datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
          
            /*
            bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, dfm, new BigDecimal(idUsuario),
                    BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfm.getDfmNorden()), null, null,
                    new BigDecimal(dfm.getDfmIdmuestra()), null, null, "", Constante.MODIFICA_DATOS_FICHAS);
            */
            DatosFichasmuestras nuevaMuestra = new DatosFichasmuestras();
            nuevaMuestra.setDatosPacientes(dfm.getDatosPacientes());
            nuevaMuestra.setCfgBaczonacuerpo(dfm.getCfgBaczonacuerpo());
            nuevaMuestra.setDfmNorden(dfm.getDfmNorden());
            nuevaMuestra.setDfmPrefijotipomuestra(dfm.getDfmPrefijotipomuestra());
            nuevaMuestra.setDfmFechacreacion(BiosLisCalendarService.getInstance().getTS());
            nuevaMuestra.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_PENDIENTE);
            Integer nroVeces = dfm.getDfmNrovecestomada() + 1;
            nuevaMuestra.setDfmNrovecestomada(nroVeces.byteValue());
            datosFichasmuestrasDAO.insertDatosFichasMuestra(nuevaMuestra);
            
            // se crea nuevo codigo de barra ****************
            String nuevoCodigoBarras = dfm.getDfmPrefijotipomuestra() + nuevaMuestra.getDfmIdmuestra();
            //**********************************************
            
            //datos generales para log eventos***********************
            DatosFichasDAO datosFichasDAO = new  DatosFichasDAO();
            DatosFichas df = datosFichasDAO.getOrdenxID((int) nuevaMuestra.getDfmNorden());
        	LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
            LogEventosfichas lef = new LogEventosfichas();
            
            lef.setLefIdpaciente(nuevaMuestra.getDatosPacientes().getDpIdpaciente());
            lef.setDatosFichas((int) nuevaMuestra.getDfmNorden() );
            lef.setLefFechaorden(df.getDfFechaorden());
            lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
            lef.setLefIdusuario((int) idUsuario );
            lef.setLefIdmuestra((int) nuevaMuestra.getDfmIdmuestra());
          
           
        
          // se cambia estado de toma de muestra antigua  rechazada
            
            lef.setCfgEventos(2);  
            lef.setLefIdmuestra((int) dfm.getDfmIdmuestra());
            lef.setLefCodigobarra(dfm.getDfmCodigobarra()); 
            lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
            lef.setLefNombrecampo("DFM_ESTADOTM");                        
            lef.setLefValornuevo("RECHAZADA");
            if(estadoMuestraAntiguo.equals("T")) {
            	lef.setLefValoranterior("TOMADA");
            }else if(dfm.getDfmEstadotm().equals("P")) {
            	lef.setLefValoranterior("PENDIENTE");
            }else if(dfm.getDfmEstadotm().equals("B")) {
            	lef.setLefValoranterior("BLOQUEADA");
            }
            lefDAO.insertLogEventosFichas(lef);            
            
            //AQUI SE AGREGA DATOS A LOGeventosficgasmodificar rechazoparcialototal  **********************         
            lef.setCfgEventos(2);            
            lef.setLefNombretabla("DATOS_MUESTRASRECHAZADAS");
            lef.setLefNombrecampo("DMR_RECHAZOPARCIALOTOTAL");                        
            lef.setLefValornuevo("TOTAL");
            lef.setLefValoranterior("");
            lefDAO.insertLogEventosFichas(lef);
          
            //***********************************************
            //AQUI SE AGREGA DATOS A LOGeventosficgas nuevo codigo barra **********************         
            lef.setCfgEventos(1); 
            lef.setLefCodigobarra(nuevoCodigoBarras); 
            lef.setLefNombretabla("DATOS_FICHASMUESTRAS");                          
            lef.setLefValornuevo("");          
            lefDAO.insertLogEventosFichas(lef);
            //************************************************************
            
            List<DatosFichasexamenes> lstExamen = datosFichasExamenesDAO.getDatosFichasExamenesByMuestra(idMuestra);
            for (DatosFichasexamenes examen : lstExamen) {
                examen.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_INGRESADO);
                datosFichasExamenesDAO.updateDatosFichasExamenes(examen);
            
                /*
                bl.logInsertDatosFichaTableRecord(DatosFichasexamenes.class, examen, new BigDecimal(1),
                        BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfm.getDfmNorden()), null,
                        new BigDecimal(examen.getIddatosFichasExamenes().getDfeIdexamen()), null, null, null, "",
                    Constante.MODIFICA_DATOS_FICHAS);
                    */
            }
           
            
            List<DatosFichasexamenestest> examenesTest = datosFichasExamenesTestDAO.getDFExamenesTestByMuestra(idMuestra);
            for (DatosFichasexamenestest dfet : examenesTest) {
                dfet.setDfetIdestadoresultadotest(EstadosSistema.DFET_IDRESULTADOTEST_INGRESADO);
                dfet.setDfetIdmuestra(nuevaMuestra.getDfmIdmuestra());
                datosFichasExamenesTestDAO.updateDFExamenesTest(dfet);
                /*
                bl.logInsertDatosFichaTableRecord(DatosFichasexamenestest.class, dfet, new BigDecimal(idUsuario),
                        BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfet.getId().getDfetNorden()), null,
                        new BigDecimal(dfet.getId().getDfetIdexamen()), new BigDecimal(dfm.getDfmIdmuestra()),
                        new BigDecimal(dfet.getDfetIdpaciente()), new BigDecimal(dfet.getId().getDfetIdtest()), "",
                        Constante.MODIFICA_DATOS_FICHAS);
                        */
            }
            
            DatosMuestrasrechazadas dmr = new DatosMuestrasrechazadas();
            CfgCausasrechazosmuestras ccmr = new CfgCausasrechazosmuestras();
            ccmr.setCcrmIdcausarechazo(causaRechazo);
            dmr.setCfgCausasrechazosmuestras(ccmr);
            dmr.setDatosFichasmuestras(dfm);
            dmr.setDmrCodigobarra(dfm.getDfmCodigobarra());
            dmr.setDmrFecharechazo(BiosLisCalendarService.getInstance().getTS());
            dmr.setDmrIdmuestra(idMuestra);
            dmr.setDmrNota(observacion);            
            dmr.setDmrRechazoparcialototal(EstadosSistema.DMR_RECHAZOPARCIALOTOTAL_TOTAL);
            dmr.setDmrIdnuevamuestra(nuevaMuestra.getDfmIdmuestra());
            dmr.setDmrIdusuariorechazo(idUsuario);
            datosMuestrasRechazadasDAO.guardarMuestraRechazada(dmr);
            
          
            
            /*
            bl.logInsertDatosFichaTableRecord(DatosMuestrasrechazadas.class, dmr, new BigDecimal(idUsuario),
                    BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfm.getDfmNorden()), null, null,
                    new BigDecimal(dfm.getDfmIdmuestra()), null, null, "", Constante.CREACION_DATOS_FICHAS);
            */
            // Actualizar codigo de barras
            
            nuevaMuestra.setDfmCodigobarra(nuevoCodigoBarras);
            datosFichasmuestrasDAO.updateDatosFichasmuestras(nuevaMuestra);
            
        
            
            
            
            /*
            bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, nuevaMuestra, new BigDecimal(idUsuario),
                    BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfm.getDfmNorden()), null, null,
                    new BigDecimal(nuevaMuestra.getDfmIdmuestra()), null, null, "", Constante.CREACION_DATOS_FICHAS);
                    */
            return nuevaMuestra;
        } catch (Exception ex) {
            Logger.getLogger(RechazoMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public DatosFichasmuestras rechazoParcialMuestra(long idMuestra, long idUsuario, byte causaRechazo, String observacion, List<String> examenesRechazados, List<String> examenesNoRechazados) throws BiosLISDAOException {
        try {
            DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(idMuestra);
            String estadoTmAntigua = dfm.getDfmEstadotm();
            String estadoRmAntiguo = dfm.getDfmEstadorm();
            dfm.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_RECHAZADA);
            dfm.setDfmEstadorm(EstadosSistema.DFM_ESTADORM_RECEPCIONADA);
            dfm.setDfmIdusuariorm(idUsuario);
            dfm.setDfmFecharm(BiosLisCalendarService.getInstance().getTS());
            datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
            
            //datos generales para log eventos***********************
            DatosFichasDAO datosFichasDAO = new  DatosFichasDAO();
            DatosFichas df = datosFichasDAO.getOrdenxID((int)dfm.getDfmNorden());
        	LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
            LogEventosfichas lef = new LogEventosfichas();
            lef.setLefIdpaciente(df.getDatosPacientes());
            lef.setDatosFichas((int) df.getDfNorden() );
            lef.setLefFechaorden(df.getDfFechaorden());
            lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
            lef.setLefIdusuario((int) idUsuario );      
            
          //**************************************************************************** 
            //AQUI SE AGREGA DATOS A LOGeventosficgasmodificar rechazoparcialototal  **********************  
            CfgExamenesDAO cfgExamenesDAO = new CfgExamenesDAO();
            String nombreExamenes ="";
            int contador = 0;            		
            for (String idExamen: examenesRechazados){
            	CfgExamenes examen = cfgExamenesDAO.getExamenById(Long.parseLong(idExamen));
            	if (contador > 0) {
            		nombreExamenes += " , ";
            		
            	}
            	contador++;
                nombreExamenes +=  examen.getCeAbreviado();
            }
           
          //************************************************************** 
            // se cambia estado de toma de muestra antigua  rechazada
            lef.setCfgEventos(2);  
            lef.setLefIdmuestra((int) dfm.getDfmIdmuestra());
            lef.setLefCodigobarra(dfm.getDfmCodigobarra()); 
            lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
            lef.setLefNombrecampo("DFM_ESTADOTM");                        
            lef.setLefValornuevo("RECHAZADA");
            if(estadoTmAntigua.equals("T")) {
            	lef.setLefValoranterior("TOMADA");
            }else if(estadoTmAntigua.equals("P")) {
            	lef.setLefValoranterior("PENDIENDE");
            }else if(estadoTmAntigua.equals("B")) {
            	lef.setLefValoranterior("BLOQUEADA");
            }
            lefDAO.insertLogEventosFichas(lef);   
         // ****   SE AGREGA TIPO DE RECHAZO PARCIAL **************** 
            lef.setLefIdmuestra((int) dfm.getDfmIdmuestra());
            lef.setLefCodigobarra(dfm.getDfmCodigobarra()); 
            lef.setCfgEventos(2);            
            lef.setLefNombretabla("DATOS_MUESTRASRECHAZADAS");
            lef.setLefNombrecampo("DMR_RECHAZOPARCIALOTOTAL");                        
            lef.setLefValornuevo("PARCIAL (Examenes :  "+nombreExamenes +" )");
            lef.setLefValoranterior("");
            lefDAO.insertLogEventosFichas(lef);
   //****************************************************************
            
            DatosFichasmuestras nuevaMuestra = new DatosFichasmuestras();
            nuevaMuestra.setDatosPacientes(dfm.getDatosPacientes());
            nuevaMuestra.setCfgBaczonacuerpo(dfm.getCfgBaczonacuerpo());
            nuevaMuestra.setDfmNorden(dfm.getDfmNorden());
            nuevaMuestra.setDfmPrefijotipomuestra(dfm.getDfmPrefijotipomuestra());
            nuevaMuestra.setDfmFechacreacion(BiosLisCalendarService.getInstance().getTS());
            nuevaMuestra.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_PENDIENTE);
            Integer nroVeces = dfm.getDfmNrovecestomada() + 1;
            nuevaMuestra.setDfmNrovecestomada(nroVeces.byteValue());
            datosFichasmuestrasDAO.insertDatosFichasMuestra(nuevaMuestra);
            
            //nuevo codigo de barra
            String nuevoCodigoBarras = dfm.getDfmPrefijotipomuestra() + nuevaMuestra.getDfmIdmuestra();
            
            
           //***************se crea nueva muestra ***************************************************** 
            lef.setCfgEventos(1);  
            lef.setLefIdmuestra((int) nuevaMuestra.getDfmIdmuestra());
            lef.setLefCodigobarra(nuevoCodigoBarras); 
            lef.setLefValoranterior(null);
            lef.setLefValornuevo(null);
            lef.setLefNombrecampo(null);   
            lefDAO.insertLogEventosFichas(lef);  
            //************************************************************************
            
            for (String idExamen: examenesRechazados){
                DatosFichasexamenes examen = datosFichasExamenesDAO.getDatosFichasExamenesByExamenyOrden(Long.parseLong(idExamen), dfm.getDfmNorden());
               String estadoExamenAntiguo = examen.getDfeCodigoestadoexamen();              
                examen.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_INGRESADO);
                datosFichasExamenesDAO.updateDatosFichasExamenes(examen);
                
                
                // se cambia estado EXAMEN LOG EVENTO****************************
              
                lef.setCfgEventos(2);  
                lef.setLefIdmuestra(null);
                lef.setLefCodigobarra(""); 
                lef.setLefIdexamen((int) examen.getIddatosFichasExamenes().getDfeIdexamen());
                lef.setLefNombretabla("DATOS_FICHASEXAMENES");
                lef.setLefNombrecampo("DFE_CODIGOESTADOEXAMEN");                        
                lef.setLefValornuevo("INGRESADO");
                if(estadoExamenAntiguo.equals("I")) {
                	lef.setLefValoranterior("INGRESADO");
                }else if(estadoExamenAntiguo.equals("P")) {
                	lef.setLefValoranterior("PENDIENTE");
                }else if(estadoExamenAntiguo.equals("B")) {
                	lef.setLefValoranterior("BLOQUEADA");
                }else if(estadoExamenAntiguo.equals("E")) {
                	lef.setLefValoranterior("EN PROCESO");
                }else if(estadoExamenAntiguo.equals("F")) {
                	lef.setLefValoranterior("FIRMADO");
                }else if(estadoExamenAntiguo.equals("A")) {
                	lef.setLefValoranterior("AUTORIZADO");
                }
                if(!lef.getLefValoranterior().equals(lef.getLefValornuevo())) {
                	lefDAO.insertLogEventosFichas(lef);  
                }
                //***********************************************
                
                
                /*                                  
                 
                bl.logInsertDatosFichaTableRecord(DatosFichasexamenes.class, examen, new BigDecimal(idUsuario),
                    BiosLisCalendarService.getInstance().getTS(),
                    new BigDecimal(examen.getIddatosFichasExamenes().getDfeNorden()), null,
                    new BigDecimal(examen.getIddatosFichasExamenes().getDfeIdexamen()), null, null, null, "",
                    Constante.MODIFICA_DATOS_FICHAS);
                */
                List<DatosFichasexamenestest> listaDfet = datosFichasExamenesTestDAO.getDatosFichasExamenesTestByMuestrayExamen(idMuestra, Long.parseLong(idExamen));
                for (DatosFichasexamenestest dfet : listaDfet) {                	
                    dfet.setDfetIdestadoresultadotest(EstadosSistema.DFET_IDRESULTADOTEST_INGRESADO);
                    dfet.setDfetIdmuestra(nuevaMuestra.getDfmIdmuestra());
                    datosFichasExamenesTestDAO.updateDFExamenesTest(dfet);
                   /* 
                    bl.logInsertDatosFichaTableRecord(DatosFichasexamenestest.class, dfet, new BigDecimal(idUsuario),
                        BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfet.getId().getDfetNorden()), null,
                        new BigDecimal(dfet.getId().getDfetIdexamen()), new BigDecimal(dfm.getDfmIdmuestra()),
                        new BigDecimal(dfet.getDfetIdpaciente()), new BigDecimal(dfet.getId().getDfetIdtest()), "",
                        Constante.MODIFICA_DATOS_FICHAS);
                        */
                }
            }
            
            if (!examenesNoRechazados.isEmpty()){
                for (String idExNR: examenesNoRechazados){
                    DatosFichasexamenes examen = datosFichasExamenesDAO.getDatosFichasExamenesByExamenyOrden(Long.parseLong(idExNR), dfm.getDfmNorden());
                    String estadoExamem = examen.getDfeCodigoestadoexamen();
                    examen.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE);
                    datosFichasExamenesDAO.updateDatosFichasExamenes(examen);
                    
                    
                 // se cambia estado EXAMEN LOG EVENTO****************************
                    lef.setCfgEventos(2);  
                    lef.setLefIdmuestra(null);
                    lef.setLefCodigobarra(null); 
                    lef.setLefIdexamen((int) examen.getIddatosFichasExamenes().getDfeIdexamen());
                    lef.setLefNombretabla("DATOS_FICHASEXAMENES");
                    lef.setLefNombrecampo("DFE_CODIGOESTADOEXAMEN");                        
                    lef.setLefValornuevo("PENDIENTE");
                    if(estadoExamem.equals("I")) {
                    	lef.setLefValoranterior("INGRESADO");
                    }else if(estadoExamem.equals("P")) {
                    	lef.setLefValoranterior("PENDIENTE");
                    }else if(estadoExamem.equals("B")) {
                    	lef.setLefValoranterior("BLOQUEADA");
                    }else if(estadoExamem.equals("E")) {
                    	lef.setLefValoranterior("EN PROCESO");
                    }else if(estadoExamem.equals("F")) {
                    	lef.setLefValoranterior("FIRMADO");
                    }else if(estadoExamem.equals("A")) {
                    	lef.setLefValoranterior("AUTORIZADO");
                    }
                    if(!lef.getLefValoranterior().equals(lef.getLefValornuevo())) {
                    	lefDAO.insertLogEventosFichas(lef);
                    }
                       
                    //***********************************************
                    
                    
                    /*
                    bl.logInsertDatosFichaTableRecord(DatosFichasexamenes.class, examen, new BigDecimal(idUsuario),
                        BiosLisCalendarService.getInstance().getTS(),
                        new BigDecimal(examen.getIddatosFichasExamenes().getDfeNorden()), null,
                        new BigDecimal(examen.getIddatosFichasExamenes().getDfeIdexamen()), null, null, null, "",
                        Constante.MODIFICA_DATOS_FICHAS);
                        */
                    
                    List<DatosFichasexamenestest> listaNoRechazados = datosFichasExamenesTestDAO.getDatosFichasExamenesTestByMuestrayExamen(idMuestra, Long.parseLong(idExNR));
                    for (DatosFichasexamenestest dfet : listaNoRechazados) {
                      dfet.setDfetIdestadoresultadotest(EstadosSistema.DFET_IDRESULTADOTEST_PENDIENTE);
                      datosFichasExamenesTestDAO.updateDFExamenesTest(dfet);
                      /*
                      bl.logInsertDatosFichaTableRecord(DatosFichasexamenestest.class, dfet,
                          new BigDecimal(idUsuario), BiosLisCalendarService.getInstance().getTS(),
                          new BigDecimal(dfet.getId().getDfetNorden()), null, new BigDecimal(dfet.getId().getDfetIdexamen()), null,
                          new BigDecimal(dfet.getDfetIdpaciente()), new BigDecimal(dfet.getId().getDfetIdtest()), "",
                          Constante.MODIFICA_DATOS_FICHAS);
                          */
                    }
                }
            }
            
            DatosMuestrasrechazadas dmr = new DatosMuestrasrechazadas();
            CfgCausasrechazosmuestras ccmr = new CfgCausasrechazosmuestras();
            ccmr.setCcrmIdcausarechazo(causaRechazo);
            dmr.setCfgCausasrechazosmuestras(ccmr);
            dmr.setDatosFichasmuestras(dfm);
            dmr.setDmrCodigobarra(dfm.getDfmCodigobarra());
            dmr.setDmrFecharechazo(BiosLisCalendarService.getInstance().getTS());
            dmr.setDmrIdmuestra(idMuestra);
            dmr.setDmrNota(observacion);
            dmr.setDmrRechazoparcialototal(EstadosSistema.DMR_RECHAZOPARCIALOTOTAL_PARCIAL);
            dmr.setDmrIdnuevamuestra(nuevaMuestra.getDfmIdmuestra());
            dmr.setDmrIdusuariorechazo(idUsuario);
            datosMuestrasRechazadasDAO.guardarMuestraRechazada(dmr);
            /*
            bl.logInsertDatosFichaTableRecord(DatosMuestrasrechazadas.class, dmr, new BigDecimal(idUsuario),
                BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfm.getDfmNorden()), null, null,
                new BigDecimal(dfm.getDfmIdmuestra()), null, null, "", Constante.CREACION_DATOS_FICHAS);
*/
            // Actualizar codigo de barras           
            nuevaMuestra.setDfmCodigobarra(nuevoCodigoBarras);
            datosFichasmuestrasDAO.updateDatosFichasmuestras(nuevaMuestra);
/*
            bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, nuevaMuestra, new BigDecimal(idUsuario),
                BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfm.getDfmNorden()), null, null,
                new BigDecimal(nuevaMuestra.getDfmIdmuestra()), null, null, "", Constante.CREACION_DATOS_FICHAS);
            */
            
            
            
            
            
            return nuevaMuestra;
        } catch (Exception ex) {
            Logger.getLogger(RechazoMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
}
