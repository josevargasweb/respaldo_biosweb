/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisLogger;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.CfgBaczonacuerpoDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgMuestrasDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesTestDAO;
import com.grupobios.bioslis.dao.DatosFichasmuestrasDAO;
import com.grupobios.bioslis.dao.DatosPacientesDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LdvFichasEstadosTMDAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ExamenOrdenMuestraDTO;
import com.grupobios.bioslis.dto.ExamenPacienteDTO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.dto.OrdenesTMDTO;
import com.grupobios.bioslis.dto.TMClickDTO;
import com.grupobios.bioslis.dto.TestCurvaDTO;
import com.grupobios.bioslis.entity.CfgBaczonacuerpo;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvFichasestadostm;
import com.grupobios.bioslis.entity.LogEventosfichas;

import java.text.ParseException;

/**
 *
 * @author marco.caracciolo
 */
@Service
public class TomaMuestrasServiceImpl implements TomaMuestrasService {

  private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(TomaMuestrasServiceImpl.class);
  @Autowired
  private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;

  @Autowired
  private DatosFichasDAO datosFichasDAO;

  @Autowired
  private CfgExamenesDAO cfgExamenesDAO;

  @Autowired
  private DatosFichasmuestrasDAO datosFichasmuestrasDAO;

  @Autowired
  private CfgMuestrasDAO cfgMuestrasDAO;

  @Autowired
  private DatosUsuariosDAO datosUsuariosDAO;
  
  private LdvFichasEstadosTMDAO fichasEstadosTMDAO;
  
  private DatosPacientesDAO datosPacientesDAO;

  BiosLisLogger bl = new BiosLisLogger();

  // GETTERS Y SETTERS
  // Colocar hasta que el sistema funcione solo con anotaciones
  public DatosFichasExamenesTestDAO getDatosFichasExamenesTestDAO() {
    return datosFichasExamenesTestDAO;
  }

  public void setDatosFichasExamenesTestDAO(DatosFichasExamenesTestDAO datosFichasExamenesTestDAO) {
    this.datosFichasExamenesTestDAO = datosFichasExamenesTestDAO;
  }

  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  public CfgExamenesDAO getCfgExamenesDAO() {
    return cfgExamenesDAO;
  }

  public void setCfgExamenesDAO(CfgExamenesDAO cfgExamenesDAO) {
    this.cfgExamenesDAO = cfgExamenesDAO;
  }

  public DatosFichasmuestrasDAO getDatosFichasmuestrasDAO() {
    return datosFichasmuestrasDAO;
  }

  public void setDatosFichasmuestrasDAO(DatosFichasmuestrasDAO datosFichasmuestrasDAO) {
    this.datosFichasmuestrasDAO = datosFichasmuestrasDAO;
  }

  public CfgMuestrasDAO getCfgMuestrasDAO() {
    return cfgMuestrasDAO;
  }

  public void setCfgMuestrasDAO(CfgMuestrasDAO cfgMuestrasDAO) {
    this.cfgMuestrasDAO = cfgMuestrasDAO;
  }

  public DatosUsuariosDAO getDatosUsuariosDAO() {
    return datosUsuariosDAO;
  }

  public void setDatosUsuariosDAO(DatosUsuariosDAO datosUsuariosDAO) {
    this.datosUsuariosDAO = datosUsuariosDAO;
  }

    public LdvFichasEstadosTMDAO getFichasEstadosTMDAO() {
        return fichasEstadosTMDAO;
    }

    public void setFichasEstadosTMDAO(LdvFichasEstadosTMDAO fichasEstadosTMDAO) {
        this.fichasEstadosTMDAO = fichasEstadosTMDAO;
    }

    public DatosPacientesDAO getDatosPacientesDAO() {
        return datosPacientesDAO;
    }

    public void setDatosPacientesDAO(DatosPacientesDAO datosPacientesDAO) {
        this.datosPacientesDAO = datosPacientesDAO;
    }

  // END GETTERS Y SETTERS
  @Override
  public List<OrdenesTMDTO> getListaOrdenesTM(String fechaInicio, String fechaFin) throws BiosLISDAOException {
    return datosFichasDAO.getOrdenesTomaMuestras(fechaInicio, fechaFin);
  }

  @Override
  public void updateTipoMuestra(MuestrasDTO muestraDTO) throws BiosLISDAOException {
    datosFichasExamenesTestDAO.updateTipoMuestra(muestraDTO);
  }

  @Override
  public List<MuestrasDTO> getListaMuestrasTM(long nOrden) throws BiosLISDAOException, UnsupportedEncodingException, SQLException {
    List<MuestrasDTO> listMuestras = datosFichasExamenesTestDAO.getDatosTablaMuestrasTM(nOrden);
    for (MuestrasDTO m : listMuestras) {
      String estado = "";
      if (m.getESTADOTM() != null) {
        switch (m.getESTADOTM()) {
        case "T":
          estado = "TOMADA";
          break;
        case "P":
          estado = "PENDIENTE";
          break;
        case "B":
          estado = "BLOQUEADA";
          break;
        case "R":
          estado = "RECHAZADA";
          break;
        default:
          estado = "NULL";
          break;
        }
      }
      m.setESTADOTM(estado);
      m.setCODIGOBARRAS((m.getCODIGOBARRAS() != null) ? m.getCODIGOBARRAS() : "");
      if (m.getOBSERVACIONTM() == null) {
        m.setOBSERVACIONTM("");
      }
      if (m.getPREFIJO() == null) {
        m.setPREFIJO("");
      }
    }
    return listMuestras;
  }

  @Override
  public OrdenesTMDTO getUltimoRegistro(long nOrden) throws BiosLISDAOException {
    return datosFichasDAO.getUltimoRegistro(nOrden);
  }

  @Override
  public List<TestCurvaDTO> getListaTestCurva(long nOrden, long idExamen) throws BiosLISDAOException {
    return datosFichasExamenesTestDAO.obtenerListaTestsCurva(nOrden, idExamen);
  }

  @Override
  public List<ExamenOrdenMuestraDTO> getExamenesOrdenMuestra(long nOrden) throws BiosLISDAOException {
    Set<String> setMuestras = new HashSet<>();
    Set<String> setEnvases = new HashSet<>();

    BigDecimal curIdExamen = new BigDecimal(-1L);
    String curEnvase = "";
    List<ExamenOrdenMuestraDTO> listaExamenesInit = datosFichasExamenesTestDAO.getExamenesOrdenMuestra(nOrden);
    List<ExamenOrdenMuestraDTO> listaExamenesGroup = new ArrayList<>();
    ExamenOrdenMuestraDTO fila = null;

    for (ExamenOrdenMuestraDTO eom : listaExamenesInit) {
      if (!eom.getCE_IDEXAMEN().equals(curIdExamen)) {
        if (fila != null) {
          StringBuilder sbMuestra = new StringBuilder("<ul>");
          setMuestras.forEach(m -> {
            sbMuestra.append("<li>").append(m).append("</li>");
          });
          sbMuestra.append("</ul>");
          fila.setCMUE_DESCRIPCION(sbMuestra.toString());
          StringBuilder sbEnvase = new StringBuilder("<ul>");
          setEnvases.forEach(e -> {
            if (!curEnvase.equals(e)) {
              sbEnvase.append("<li>").append(e).append("</li>");
            }
          });
          sbEnvase.append("</ul>");
          fila.setCENV_DESCRIPCION(sbEnvase.toString());
        }
        fila = new ExamenOrdenMuestraDTO();
        setMuestras.clear();
        setEnvases.clear();
        listaExamenesGroup.add(fila);
        fila.setCE_IDEXAMEN(eom.getCE_IDEXAMEN());
        fila.setCE_CODIGOEXAMEN(eom.getCE_CODIGOEXAMEN());
        fila.setCE_DESCRIPCION(eom.getCE_DESCRIPCION());
        fila.setCE_COMPARTEMUESTRA(eom.getCE_COMPARTEMUESTRA());
        fila.setCSEC_IDSECCION(eom.getCSEC_IDSECCION());
        fila.setCSEC_DESCRIPCION(eom.getCSEC_DESCRIPCION());
        fila.setCLAB_IDLABORATORIO(eom.getCLAB_IDLABORATORIO());
        fila.setCLAB_DESCRIPCION(eom.getCLAB_DESCRIPCION());
        fila.setCCDS_IDCENTRODESALUD(eom.getCCDS_IDCENTRODESALUD());
        fila.setCCDS_DESCRIPCION(eom.getCCDS_DESCRIPCION());
        fila.setCDERIV_IDDERIVADOR(eom.getCDERIV_IDDERIVADOR());
        fila.setCDERIV_DESCRIPCION(eom.getCDERIV_DESCRIPCION());
        fila.setDF_OBSERVACION(eom.getDF_OBSERVACION());
        fila.setCD_DESCRIPCION(eom.getCD_DESCRIPCION());
        fila.setDFET_IDMUESTRA(eom.getDFET_IDMUESTRA());
        fila.setDFE_EXAMENANULADO(eom.getDFE_EXAMENANULADO());
        curIdExamen = eom.getCE_IDEXAMEN();
      }
      setMuestras.add(eom.getCMUE_DESCRIPCION());
      setEnvases.add(eom.getCENV_DESCRIPCION());
    }

    if (fila != null) {
      StringBuilder sbMuestra = new StringBuilder("<ul>");
      setMuestras.forEach(m -> {
        sbMuestra.append("<li>").append(m).append("</li>");
      });
      sbMuestra.append("</ul>");
      fila.setCMUE_DESCRIPCION(sbMuestra.toString());
      StringBuilder sbEnvase = new StringBuilder("<ul>");
      setEnvases.forEach(e -> {
        if (!curEnvase.equals(e)) {
          sbEnvase.append("<li>").append(e).append("</li>");
        }
      });
      sbEnvase.append("</ul>");
      fila.setCENV_DESCRIPCION(sbEnvase.toString());
    }

    return listaExamenesGroup;
  }

  @Override
  public TMClickDTO validarTmClick(Integer nOrden, Long idUsuario) throws BiosLISDAOException {
    TMClickDTO tmClick = datosFichasDAO.getTmClick(nOrden);
    logger.debug("TM Click: " + tmClick.getOCUPADO());
    if (!tmClick.getOCUPADO().equals("N")) {
      String[] parts = tmClick.getOCUPADO().split("/");
      tmClick.setIDUSUARIO(new BigDecimal(parts[0]));
      tmClick.setNOMUSUARIO(parts[1]);
      tmClick.setOCUPADO("S");
    } else {
      tmClick.setIDUSUARIO(new BigDecimal(idUsuario));
      datosFichasDAO.actualizarTmClick(nOrden, idUsuario);
    }
    return tmClick;
  }

  @Override
  public ExamenPacienteDTO getCurvaTolerancia(long nOrden, long idMuestra) throws BiosLISDAOException {
    ExamenPacienteDTO examenPacienteCurvaDTO = datosFichasDAO.getExamenPacienteCurva(nOrden, idMuestra);
    // List<TestCurvaDTO> lstCurvas =
    // datosFichasExamenesTestDAO.obtenerListaTestsCurva(nOrden,
    // examenPacienteCurvaDTO.getCE_IDEXAMEN().longValue());
    // examenPacienteCurvaDTO.setLstCurvas(lstCurvas);
    return examenPacienteCurvaDTO;
  }

  @Override
  public List<TMClickDTO> volverNormalidadOrdenAnterior(Integer nOrden, Long idUsuario) throws BiosLISDAOException {
    List<TMClickDTO> lisTmClick = datosFichasDAO.getTmClickNotOrden(nOrden);
    List<TMClickDTO> newList = new ArrayList<>();
    for (TMClickDTO tc : lisTmClick) {
      String[] parts = tc.getOCUPADO().split("/");
      Long idUsrTc = Long.parseLong(parts[0]);
      if (Objects.equals(idUsrTc, idUsuario)) {
        datosFichasDAO.volverANormlidadOrden(tc.getNORDEN().intValue());
        newList.add(tc);
      }
    }
    return newList;
  }

  @Override
  public void volverNormalidadOrden(Integer nOrden) throws BiosLISDAOException {
    datosFichasDAO.volverANormlidadOrden(nOrden);
  }

  @Override
  public void volverNormalidadUsuario(Long idUsuario) throws BiosLISDAOException {
    datosFichasDAO.volverANormlidadOrdenesUsuario(idUsuario);
  }

  @Override
  public List<ExamenOrdenDTO> getExamenesMuestra(long idMuestra) throws BiosLISDAOException {
    return cfgExamenesDAO.getExamenesMuestraByIdMuestra(idMuestra);
  }

  @Override
  public List<ExamenOrdenDTO> getExamenesMuestraSinIdMuestra(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra,Long idExamen) throws BiosLISDAOException {
    return cfgExamenesDAO.getExamenesMuestraSinIdMuestra(nOrden, idTipoMuestra, idEnvase, idDerivador, comparteMuestra,idExamen);
  }

  @Override
  public List<CfgExamenes> getExamenesMuestraAntiguo(long idMuestra) throws BiosLISDAOException {
    return cfgExamenesDAO.getExamenesMuestraByIdMuestraAntiguo(idMuestra);
  }

  @Override
  public List<CfgExamenes> getExamenesMuestraSinIdMuestraAntiguo(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra) throws BiosLISDAOException {
    return cfgExamenesDAO.getExamenesMuestraSinIdMuestraAntiguo(nOrden, idTipoMuestra, idEnvase, idDerivador,
        comparteMuestra);
  }

  @Override
  public OrdenesTMDTO tomarMuestraCurva(Long idMuestra, Long idUsuario) throws BiosLISDAOException {
    DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(idMuestra);
    String estadoMuestra = dfm.getDfmEstadotm();
    dfm.setDfmFechatm(BiosLisCalendarService.getInstance().getTS());
    dfm.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_TOMADA);
    dfm.setDfmEstadorm(EstadosSistema.DFM_ESTADORM_PENDIENTE);
    dfm.setDfmIdusuariotm(idUsuario);
    datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);  
    
// realizar aqui ***********************************************************************************
    // TRAZABILIDAD
    //AQUI SE AGREGA DATOS A LOGeventosfichas **********************
	  DatosFichas df = datosFichasDAO.getOrdenxID((int) dfm.getDfmNorden());
	  List<DatosFichasexamenestest> listaTest =   datosFichasExamenesTestDAO.getTestByMuestra(dfm.getDfmIdmuestra());
	  LogEventosfichas lef = new LogEventosfichas();
	  lef.setLefIdpaciente(df.getDatosPacientes());
	  lef.setDatosFichas((int) dfm.getDfmNorden() );
	  lef.setLefFechaorden(df.getDfFechaorden());
	  lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
	  lef.setCfgEventos(2);
	  
  if(listaTest != null) {
	  for(DatosFichasexamenestest lis : listaTest) {
		 if(estadoMuestra.equals("P")) {
		     lef.setLefValoranterior("PENDIENTE");
		 }else if(estadoMuestra.equals("T")) {
		     lef.setLefValoranterior("TOMADA");
		 }else if(estadoMuestra.equals("B")) {
		     lef.setLefValoranterior("BLOQUEADA");
		 }  else{
		     lef.setLefValoranterior("RECHAZADA");
		 }
		 
		 lef.setLefValornuevo("TOMADA"); 
		 lef.setLefNombrecampo("DFM_ESTADOTM");
		 lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
		 lef.setLefIdusuario(idUsuario.intValue() );
		 lef.setLefIdmuestra((int) dfm.getDfmIdmuestra());
		 lef.setLefCodigobarra(dfm.getDfmCodigobarra());
		 lef.setLefIdexamen((int) lis.getId().getDfetIdexamen());
		 lef.setLefIdtest((int) lis.getId().getDfetIdtest());
		 LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
		 lefDAO.insertLogEventosFichas(lef);
	  }
  }
//*****************************************************************
   /*
    try {
      bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, dfm, new BigDecimal(idUsuario),
          BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfm.getDfmNorden()), null, null,
          new BigDecimal(idMuestra), null, null, "", Constante.MODIFICA_DATOS_FICHAS);
    } catch (IllegalArgumentException | IllegalAccessException ex) {
      Logger.getLogger(TomaMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
*/
    if (validaTodasMuestrasAtendidas(dfm.getDfmNorden(), idUsuario)) {
      return datosFichasDAO.getUltimoRegistro(dfm.getDfmNorden());
    }
    return null;
  }

  @Override
  public OrdenesTMDTO tomarMuestraManualCurva(Long idMuestra, Long time, Long idUsuario) throws BiosLISDAOException {
    DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(idMuestra);
    // dfm.setDfmFechatm(BiosLiosCalendarService.getInstance().getTS());
    dfm.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_TOMADA);
    dfm.setDfmEstadorm(EstadosSistema.DFM_ESTADORM_PENDIENTE);
    dfm.setDfmIdusuariotm(idUsuario);
    logger.debug("hora: " + new Date(time * 1000));
    dfm.setDfmFechatm(new Date(time * 1000));

    datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);

    // TRAZABILIDAD
    /*
    try {
      bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, dfm, new BigDecimal(idUsuario),
          BiosLisCalendarService.getInstance().getTS(), new BigDecimal(dfm.getDfmNorden()), null, null,
          new BigDecimal(idMuestra), null, null, "", Constante.MODIFICA_DATOS_FICHAS);
    } catch (IllegalArgumentException | IllegalAccessException ex) {
      Logger.getLogger(TomaMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
*/
    if (validaTodasMuestrasAtendidas(dfm.getDfmNorden(), idUsuario)) {
      return datosFichasDAO.getUltimoRegistro(dfm.getDfmNorden());
    }
    return null;
  }

  @Override
  public List<ExamenOrdenMuestraDTO> getDetalleMuestra(long idMuestra) throws BiosLISDAOException {
    return datosFichasExamenesTestDAO.getDetalleMuestra(idMuestra);
  }

  @Override
  public List<ExamenOrdenMuestraDTO> getDetalleMuestraSM(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra) throws BiosLISDAOException {
    return datosFichasExamenesTestDAO.getDetalleMuestraSM(nOrden, idTipoMuestra, idEnvase, idDerivador,
        comparteMuestra);
  }

  @Override
  public void cambiarTipoMuestraCM(Long idMuestra, Short idTipoMuestra, Long idUsuario) throws BiosLISDAOException {
    List<DatosFichasexamenestest> listDfet = datosFichasExamenesTestDAO.getDFExamenesTestByMuestra(idMuestra);
    Short tipoMuestraAntiguo = listDfet.get(0).getDfetIdtipomuestra();
    for (DatosFichasexamenestest dfet : listDfet) {
      dfet.setDfetIdtipomuestra(idTipoMuestra);
      // dfet.setDfetIdmuestra(null);
      datosFichasExamenesTestDAO.updateDFExamenesTest(dfet);
    
    }

    CfgMuestras cm = cfgMuestrasDAO.getMuestraById(idTipoMuestra);
    DatosFichasmuestras dfm = this.getMuestraById(idMuestra);
    String estadoMuestraAntiguo = dfm.getDfmEstadotm();
    String codigoBarraAntiguo = dfm.getDfmCodigobarra();
    String nuevoPrefijo = cm.getCmuePrefijotipomuestra() + dfm.getDfmIdmuestra();
    dfm.setDfmPrefijotipomuestra(cm.getCmuePrefijotipomuestra());
    
    dfm.setDfmCodigobarra(nuevoPrefijo);   
    //ya no se cambia estado cuando se cambie el tipodemuestra *****************************
   // dfm.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_PENDIENTE);
    datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
    
    DatosFichas df = datosFichasDAO.getOrdenxID((int)dfm.getDfmNorden());
    LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();
   
    lef.setDatosFichas( df.getDfNorden() );
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefIdpaciente((int) df.getDatosPacientes()); 
    lef.setLefNombretabla("DATOS_FICHASEXAMENESTEST");
    lef.setCfgEventos(2);   
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefIdusuario(idUsuario.intValue());
    lef.setLefIdmuestra(idMuestra.intValue());
    lef.setLefCodigobarra(codigoBarraAntiguo);    

    lef.setLefNombrecampo("DFET_IDTIPOMUESTRA");
    CfgMuestrasDAO cfgMuestrasDAO = new CfgMuestrasDAO();
	  CfgMuestras muestraNueva = cfgMuestrasDAO.getMuestraById(tipoMuestraAntiguo);
	  lef.setLefValoranterior(muestraNueva.getCmueDescripcionabreviada());
    muestraNueva = cfgMuestrasDAO.getMuestraById(idTipoMuestra);
    lef.setLefValornuevo(muestraNueva.getCmueDescripcionabreviada());
    logEventosfichasDAO.insertLogEventosFichas(lef);
    
    lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
    lef.setLefCodigobarra(nuevoPrefijo);
    lef.setLefNombrecampo("DFM_CODIGOBARRA");
    lef.setLefValoranterior(codigoBarraAntiguo);
    lef.setLefValornuevo(nuevoPrefijo);    
    logEventosfichasDAO.insertLogEventosFichas(lef);
      
  }

  @Override
  public void cambiarTipoMuestraSM(Long nOrden, Short idTipoMuestra, Short idEnvase, Short idDerivador,
      String comparteMuestra, Short newTipoMuestra , Long idUsuario) throws BiosLISDAOException {
    List<DatosFichasexamenestest> listDfet = datosFichasExamenesTestDAO.buscarDFETforMuestras(nOrden, idTipoMuestra,
        idEnvase, idDerivador, comparteMuestra, comparteMuestra);
   
    LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();
   
    lef.setDatosFichas( nOrden.intValue() );
    lef.setLefFechaorden(listDfet.get(0).getDfetFechaorden());
    lef.setLefIdpaciente((int) listDfet.get(0).getDfetIdpaciente()); 
    lef.setLefNombretabla("DATOS_FICHASEXAMENESTEST");
    lef.setCfgEventos(2);
    lef.setLefNombrecampo("DFET_IDTIPOMUESTRA");
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefIdusuario(idUsuario.intValue());
    
    for (DatosFichasexamenestest dfet : listDfet) {
	  if(dfet.getDfetIdmuestra() != null) {
		  DatosFichasmuestras muestra = this.getMuestraById(dfet.getDfetIdmuestra());
		  lef.setLefCodigobarra(muestra.getDfmCodigobarra());
		  CfgMuestrasDAO cfgMuestrasDAO = new CfgMuestrasDAO();
		  CfgMuestras muestraNueva = cfgMuestrasDAO.getMuestraById(newTipoMuestra);
		  lef.setLefValornuevo(muestraNueva.getCmueDescripcionabreviada());
		  
		  muestraNueva = cfgMuestrasDAO.getMuestraById(dfet.getDfetIdtipomuestra());
		  lef.setLefValoranterior(muestraNueva.getCmueDescripcionabreviada());	   
		    logEventosfichasDAO.insertLogEventosFichas(lef);
	  }
	   
	    
      dfet.setDfetIdtipomuestra(newTipoMuestra);
      dfet.setDfetIdmuestra(null);
      datosFichasExamenesTestDAO.updateDFExamenesTest(dfet);          
      
  
      
   
    }

  }

  @Override
  public void cambiarTipoMuestra(HashMap<String, String> datosMuestra) throws BiosLISDAOException {
    Long nOrden = Long.parseLong(datosMuestra.get("nOrden").toUpperCase());
    Short idTipoMuestra = Short.parseShort(datosMuestra.get("idTipoMuestra"));
    Short idEnvase = Short.parseShort(datosMuestra.get("idEnvase"));
    Short idDerivador = Short.parseShort(datosMuestra.get("idDerivador"));
    String comparteMuestra = datosMuestra.get("comparteMuestra");
  }

  @Override
  public void updateMuestra(DatosFichasmuestras dfm) throws BiosLISDAOException {
    datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
  }

  @Override
  public List<DatosUsuarios> getListaFlebotomistas() throws BiosLISDAOException {
    return datosUsuariosDAO.listaFlebotomistas();
  }

  @Override
  public OrdenesTMDTO cambioEstadoMuestra(long idMuestra, long idUsuario, String estado) throws BiosLISDAOException {
	
    DatosFichasmuestras muestra = this.getMuestraById(idMuestra);
    String estadoAnterior = muestra.getDfmEstadotm();
    muestra.setDfmEstadotm(estado);
    switch (estado) {
    case EstadosSistema.DFM_ESTADOTM_PENDIENTE:
      muestra.setDfmFechatm(BiosLisCalendarService.getInstance().getTS());
      muestra.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_PENDIENTE);
      muestra.setDfmIdusuariotm(idUsuario);
      break;
    case EstadosSistema.DFM_ESTADOTM_TOMADA:
      muestra.setDfmFechatm(BiosLisCalendarService.getInstance().getTS());
      muestra.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_TOMADA);
      muestra.setDfmEstadorm(EstadosSistema.DFM_ESTADORM_PENDIENTE);
      muestra.setDfmIdusuariotm(idUsuario);
      break;
    case EstadosSistema.DFM_ESTADOTM_BLOQUEADA:
      muestra.setDfmFechatm(BiosLisCalendarService.getInstance().getTS());
      muestra.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_BLOQUEADA);
      muestra.setDfmIdusuariotm(idUsuario);
      break;
    default:
      break;
    }

    
    datosFichasmuestrasDAO.updateDatosFichasmuestras(muestra);
    
    //***** aqui se toman los datos para agregarlos a log eventos fichas
    DatosFichas df = datosFichasDAO.getOrdenxID((int) muestra.getDfmNorden());
    LogEventosfichas lef = new LogEventosfichas();
    LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
    
    lef.setDatosFichas( (int) muestra.getDfmNorden());
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefIdpaciente(muestra.getDatosPacientes().getDpIdpaciente());
    lef.setLefIdmuestra((int) muestra.getDfmIdmuestra());
    lef.setCfgEventos(2);
    lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
    lef.setLefNombrecampo("DFM_ESTADOTM");
    
    if(estadoAnterior.equals( "T") ) {
        lef.setLefValoranterior("TOMADA");
    }else if(estadoAnterior.equals("P")) {
        lef.setLefValoranterior("PENDIENTE");
    }else if(estadoAnterior.equals("B")) {
        lef.setLefValoranterior("BLOQUEADO");
    }else {
        lef.setLefValoranterior("RECHAZADA");
    }
    
    if(estado.equals("T") ) {
        lef.setLefValornuevo("TOMADA");
    }else if(estado.equals("P")) {
        lef.setLefValornuevo("PENDIENTE");
    }else if(estado.equals("B")) {
        lef.setLefValornuevo("BLOQUEADO");
    }else {
        lef.setLefValornuevo("RECHAZADA");
    }
    lef.setLefIdusuario((int) idUsuario);
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefCodigobarra(muestra.getDfmCodigobarra());
    
    lefDAO.insertLogEventosFichas(lef);
  //******************************************************************************************  
    
    /*
    try {
        
      bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, muestra, new BigDecimal(idUsuario),
          BiosLisCalendarService.getInstance().getTS(), new BigDecimal(muestra.getDfmNorden()), null, null,
          new BigDecimal(idMuestra), null, null, "", Constante.MODIFICA_DATOS_FICHAS);
    } catch (IllegalArgumentException | IllegalAccessException ex) {
      Logger.getLogger(TomaMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
*/
    boolean muestrasAtendidas = validaTodasMuestrasAtendidas(muestra.getDfmNorden(), idUsuario);

    if (muestrasAtendidas == true) {
      return datosFichasDAO.getUltimoRegistro(muestra.getDfmNorden());
    }
    return null;
  }

  private Boolean validaTodasMuestrasAtendidas(Long norden , Long idUsuario) throws BiosLISDAOException {
    Boolean todasMuestrasAtendidas = datosFichasmuestrasDAO.validar_OrdenTM(norden);

    /*
     * List<DatosFichasmuestras> listaMuestras = datosFichasmuestrasDAO.obtenerListaMuestrasPorOrden(norden);
    for (DatosFichasmuestras muestra : listaMuestras) {
    	logger.debug(muestra.getDfmEstadotm());
      if (muestra.getDfmEstadotm().equals(EstadosSistema.DFM_ESTADOTM_PENDIENTE)) {
    	  
    	  
        todasMuestrasAtendidas = false;
        logger.debug(todasMuestrasAtendidas);
        break;
      }
    }*/

    
      try {
          if (todasMuestrasAtendidas == true) {
                 datosFichasDAO.updateEstadoPaciente(norden, EstadosSistema.DF_FICHASESTADOSTM_ATENDIDO, idUsuario);
                 datosFichasDAO.volverANormlidadOrden(norden.intValue());       
          }else {
              datosFichasDAO.updateEstadoPaciente(norden, EstadosSistema.DF_FICHASESTADOSTM_ESPERA , idUsuario);
          }
         
      } catch (IllegalArgumentException | IllegalAccessException ex) {
        Logger.getLogger(TomaMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
      }
   
    return todasMuestrasAtendidas;
  }
  /*
   * @Override public OrdenesTMDTO confirmarMuestraCodigoBarras(String
   * codigoBarras, long idUsuario) { DatosFichasmuestras dfm =
   * datosFichasmuestrasDAO.getMuestraByCodigoBarras(codigoBarras); if
   * (dfm!=null){ dfm.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_TOMADA);
   * dfm.setDfmEstadorm(EstadosSistema.DFM_ESTADORM_PENDIENTE);
   * dfm.setDfmFechatm(BiosLisCalendarService.getInstance().getTS());
   * dfm.setDfmIdusuariotm(idUsuario);
   * datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm); try {
   * bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, dfm, new
   * BigDecimal(idUsuario), BiosLisCalendarService.getInstance().getTS(), new
   * BigDecimal(dfm.getDfmNorden()), null, null, new
   * BigDecimal(dfm.getDfmIdmuestra()), null, null, "",
   * Constante.MODIFICA_DATOS_FICHAS); } catch (IllegalArgumentException |
   * IllegalAccessException ex) {
   * Logger.getLogger(TomaMuestrasServiceImpl.class.getName()).log(Level.SEVERE,
   * null, ex); } if (validaTodasMuestrasAtendidas(dfm.getDfmNorden())){ return
   * datosFichasDAO.getUltimoRegistro(dfm.getDfmNorden()); } } return null;
   * 
   * }
   */

  @Override
  public OrdenesTMDTO confirmarTomaMuestra(DatosFichasmuestras muestra, long idUsuario) throws BiosLISDAOException {        
    String estadoMuestra = muestra.getDfmEstadotm();
    muestra.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_TOMADA);
    muestra.setDfmEstadorm(EstadosSistema.DFM_ESTADORM_PENDIENTE);
    muestra.setDfmFechatm(BiosLisCalendarService.getInstance().getTS());
    muestra.setDfmIdusuariotm(idUsuario);
    logger.debug("actualiza");
    datosFichasmuestrasDAO.updateDatosFichasmuestras(muestra);
   
    //AQUI SE AGREGA DATOS A LOGeventosficgas **********************
	  DatosFichas df = datosFichasDAO.getOrdenxID((int) muestra.getDfmNorden());
    LogEventosfichas lef = new LogEventosfichas();
    lef.setLefIdpaciente(muestra.getDatosPacientes().getDpIdpaciente());
    lef.setDatosFichas((int) muestra.getDfmNorden() );
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setCfgEventos(2);
    
    
    
   if(estadoMuestra.equals("P")) {
       lef.setLefValoranterior("PENDIENTE");
   }else if(estadoMuestra.equals("T")) {
       lef.setLefValoranterior("TOMADA");
   }else if(estadoMuestra.equals("B")) {
       lef.setLefValoranterior("BLOQUEADA");
   }  else{
       lef.setLefValoranterior("RECHAZADA");
   }
   
   lef.setLefValornuevo("TOMADA"); 
   lef.setLefNombrecampo("DFM_ESTADOTM");
   lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
   lef.setLefIdusuario((int) idUsuario );
   lef.setLefIdmuestra((int) muestra.getDfmIdmuestra());
   lef.setLefCodigobarra(muestra.getDfmCodigobarra());
   LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
   lefDAO.insertLogEventosFichas(lef);
  //*****************************************************************
 
/*
    try {
      bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, muestra, new BigDecimal(idUsuario),
          BiosLisCalendarService.getInstance().getTS(), new BigDecimal(muestra.getDfmNorden()), null, null,
          new BigDecimal(muestra.getDfmIdmuestra()), null, null, "", Constante.MODIFICA_DATOS_FICHAS);
    } catch (IllegalArgumentException | IllegalAccessException ex) {
      Logger.getLogger(TomaMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
*/
    if (validaTodasMuestrasAtendidas(muestra.getDfmNorden(), idUsuario)) {
    	logger.debug("entro primer IF");
      return datosFichasDAO.getUltimoRegistro(muestra.getDfmNorden());
    }
    return null;
  }

  @Override
  public List<MuestrasDTO> generarMuestra(List<MuestrasDTO> listaMuestras, Long idUsuario) throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException {
    //  List<DatosFichasmuestras> listDFM = new ArrayList<>();
      for (MuestrasDTO muedto: listaMuestras){
        DatosFichasmuestras dfm = new DatosFichasmuestras();
        dfm.setDfmNorden(muedto.getNORDEN().longValue());
        dfm.setDfmPrefijotipomuestra(muedto.getPREFIJO());
        dfm.setDfmEstadotm(EstadosSistema.DFM_ESTADOTM_PENDIENTE);
        DatosPacientes datosPacientes = datosPacientesDAO.getPacienteById(muedto.getIDPACIENTE().intValue());
        dfm.setDatosPacientes(datosPacientes);
        Integer nroVeces = 1;
        dfm.setDfmNrovecestomada(nroVeces.byteValue());
        dfm.setDfmFechacreacion(BiosLisCalendarService.getInstance().getTS());
        datosFichasmuestrasDAO.insertDatosFichasMuestra(dfm);
        muedto.setIDMUESTRA(new BigDecimal(dfm.getDfmIdmuestra()));
        
        String codigoBarra = muedto.getPREFIJO() + dfm.getDfmIdmuestra();
        dfm.setDfmCodigobarra(codigoBarra);
        muedto.setCODIGOBARRAS(codigoBarra);
        
        datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
        logger.debug(muedto.getIDEXAMEN() + "ID EXAMEN PPPPPP");
        List<DatosFichasexamenestest> listDfet = datosFichasExamenesTestDAO.buscarDFETparGenerarMuestras(muedto.getNORDEN().longValue(), muedto.getIDTIPOMUESTRA().shortValue(),
            muedto.getIDENVASE().shortValue(), muedto.getIDDERIVADOR().shortValue(), muedto.getCOMPARTEMUESTRA(),muedto.getESCURVATOLERANCIA(),muedto.getIDEXAMEN());
        
        try {
         // TRAZABILIDAD
            //agregado para insertar eventos 13-02-2023 por cristian 
            LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
            LogEventosfichas lef = new LogEventosfichas();
           
            lef.setDatosFichas( (int) dfm.getDfmNorden() );
            lef.setLefFechaorden(listDfet.get(0).getDfetFechaorden());
            lef.setLefIdpaciente((int) listDfet.get(0).getDfetIdpaciente());           
            lef.setLefIdmuestra( (int) dfm.getDfmIdmuestra());
            lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
            lef.setLefCodigobarra(codigoBarra);
            lef.setLefFecharegistro(dfm.getDfmFechacreacion());
            lef.setLefIdusuario(idUsuario.intValue());
            lef.setCfgEventos(1);
        
         logEventosfichasDAO.insertLogEventosFichas(lef);
          //**********************************************************************  
         //aqui se inserta la accion que realiza usuario en logusuarios
         
         
         LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
         LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
         
         leu.setLeuIdusuario( idUsuario.intValue());
         leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
         leu.setLeuIdevento(9);              
         leu.setLeuIdacciondato(4);
         leu.setLeuNombretabla("DATOS_FICHASMUESTRAS");
         leu.setLeuNombrecampo("DFM_CODIGOBARRA");
         leu.setLeuValornuevo("etiqueta "+ codigoBarra);
         logUsuarioDao.insertLogEventosUsuario(leu);
          //*-************************************************************ 
         
         
         
            /*
             * 
            
            bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, dfm, muedto.getIDUSRFLEBOTOMISTA(),
                    BiosLisCalendarService.getInstance().getTS(), muedto.getNORDEN(), null, null,
                    new BigDecimal(dfm.getDfmIdmuestra()), null, null, "", Constante.CREACION_DATOS_FICHAS);
            */
        } catch (IllegalArgumentException  ex) {
            Logger.getLogger(TomaMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (DatosFichasexamenestest ddfet : listDfet) {
          ddfet.setDfetIdmuestra(dfm.getDfmIdmuestra());
          datosFichasExamenesTestDAO.updateDFExamenesTest(ddfet);
   }
        
    }
    return listaMuestras;
  }

  @Override
  public DatosFichasmuestras getMuestraByCodigoBarras(String codigoBarras) throws BiosLISDAOException {
    return datosFichasmuestrasDAO.getMuestraByCodigoBarras(codigoBarras);
  }

  @Override
  public DatosFichasmuestras getMuestraById(long idMuestra) throws BiosLISDAOException {
    return datosFichasmuestrasDAO.getMuestraById(idMuestra);
  }

  @Override
  public void guardarObservacion(DatosFichasmuestras dfm, String observacion, Long idUsuario) throws BiosLISDAOException {
	  DatosFichas df = datosFichasDAO.getOrdenxID((int) dfm.getDfmNorden());
	  String observacionAntigua = dfm.getDfmTmobservacion();
    dfm.setDfmTmobservacion(observacion);
    datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
    
    LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();
   
    lef.setDatosFichas( df.getDfNorden() );
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefIdpaciente(df.getDatosPacientes());           
    lef.setLefIdmuestra( (int) dfm.getDfmIdmuestra());
    lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
    lef.setLefCodigobarra(dfm.getDfmCodigobarra());
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefIdusuario(idUsuario.intValue());
    lef.setCfgEventos(2);
    lef.setLefNombrecampo("DFM_TMOBSERVACION");
    lef.setLefValoranterior(observacionAntigua);
    lef.setLefValornuevo(observacion);
    
    logEventosfichasDAO.insertLogEventosFichas(lef);
  }

  @Override
  public void cambiarEstadoUrgente(int nOrden, Long idUsuario) throws BiosLISDAOException {
    datosFichasDAO.updateEstadoUrgente(nOrden, idUsuario);
  }

  @Override
  public List<OrdenesTMDTO> getListaOrdenesByOrden(int nOrden) throws BiosLISDAOException {
    return datosFichasDAO.getOrdenesTomaMuestrasByOrden(nOrden);
  }

    @Override
    public List<LdvFichasestadostm> getListaEstadosOrden() throws BiosLISDAOException {
        return fichasEstadosTMDAO.llenarSelectEstadosPaciente();
    }

	@Override
	public String updateMuestraZonaCuerpo(long idMuestra,long idCuerpo, Long idUsuario) throws BiosLISDAOException {
		//  se buscan datos anteriores para agregar a log eventos **************************
		DatosFichasmuestras dfm = this.getMuestraById(idMuestra);
		 DatosFichas df = datosFichasDAO.getOrdenxID((int) dfm.getDfmNorden());
		 CfgBaczonacuerpoDAO cfgBaczonacuerpoDAO = new CfgBaczonacuerpoDAO();
		 CfgBaczonacuerpo zonaCuerpo = cfgBaczonacuerpoDAO.BuscarBacZonaCuerpoPorId((short) idCuerpo );
	
		 
		 
		 LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
		    LogEventosfichas lef = new LogEventosfichas();
		   
		    lef.setDatosFichas( df.getDfNorden() );
		    lef.setLefFechaorden(df.getDfFechaorden());
		    lef.setLefIdpaciente(df.getDatosPacientes());           
		    lef.setLefIdmuestra( (int) dfm.getDfmIdmuestra());
		    lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
		    lef.setLefCodigobarra(dfm.getDfmCodigobarra());
		    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
		    lef.setLefIdusuario(idUsuario.intValue());
		    lef.setCfgEventos(2);
		    lef.setLefNombrecampo("DFM_IDZONACUERPO");
		    lef.setLefValornuevo(zonaCuerpo.getCbzcDescripcion());
		    if( dfm.getCfgBaczonacuerpo() != null) {
		    	lef.setLefValoranterior(dfm.getCfgBaczonacuerpo().getCbzcDescripcion());
		    }else {
		    	lef.setLefValoranterior("");
		    }	    
		   
		    if(!lef.getLefValoranterior().equals(lef.getLefValornuevo())) {
		    	logEventosfichasDAO.insertLogEventosFichas(lef);
		    }
		//***************************************************************************    
		
		return datosFichasmuestrasDAO.updateDatosFichasmuestrasZonaCuerpo(idMuestra,idCuerpo, idUsuario);
	}

    @Override
    public OrdenesTMDTO actualizarEstadoOrden(long nOrden, int estado) throws BiosLISDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String cambiarInstitucionOrden() throws BiosLISDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
