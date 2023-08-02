/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisLogger;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgDerivadoresDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesTestDAO;
import com.grupobios.bioslis.dao.DatosFichasmuestrasDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dto.DatosOrdenPacienteExamenesDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LogEventosfichas;

/**
 *
 * @author marco.c
 */
@Service
public class RecepcionMuestrasServiceImpl implements RecepcionMuestrasService {

  private static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(RecepcionMuestrasServiceImpl.class);

  BiosLisLogger bl = new BiosLisLogger();

  @Autowired
  DatosFichasmuestrasDAO datosFichasmuestrasDAO;

  @Autowired
  DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;

  @Autowired
  DatosFichasExamenesDAO datosFichasExamenesDAO;

  @Autowired
  CfgExamenesDAO cfgExamenesDAO;

  @Autowired
  DatosUsuariosDAO datosUsuariosDAO;

  @Autowired
  DatosFichasDAO datosFichasDAO;

  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  /*** GETTERS AND SETTERS ***/
  public DatosFichasmuestrasDAO getDatosFichasmuestrasDAO() {
    return datosFichasmuestrasDAO;
  }

  public void setDatosFichasmuestrasDAO(DatosFichasmuestrasDAO datosFichasmuestrasDAO) {
    this.datosFichasmuestrasDAO = datosFichasmuestrasDAO;
  }

  public DatosFichasExamenesTestDAO getDatosFichasExamenesTestDAO() {
    return datosFichasExamenesTestDAO;
  }

  public void setDatosFichasExamenesTestDAO(DatosFichasExamenesTestDAO datosFichasExamenesTestDAO) {
    this.datosFichasExamenesTestDAO = datosFichasExamenesTestDAO;
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

  public DatosUsuariosDAO getDatosUsuariosDAO() {
    return datosUsuariosDAO;
  }

  public void setDatosUsuariosDAO(DatosUsuariosDAO datosUsuariosDAO) {
    this.datosUsuariosDAO = datosUsuariosDAO;
  }

  /**** END GETTERS AND SETTERS ****/

  @Override
  public List<MuestrasDTO> getListaMuestrasRM(String fecha) throws BiosLISDAOException {
    List<MuestrasDTO> listaMuestras = new ArrayList<>();
    LOGGER.debug("INI_ getListaMuestrasRM: {}", LocalDateTime.now());
    try {
      listaMuestras = datosFichasmuestrasDAO.getMuestrasRM(fecha);

      for (MuestrasDTO mr : listaMuestras) {
        List<ExamenOrdenDTO> listExamenes = cfgExamenesDAO.getExamenesByMuestra(mr.getIDMUESTRA().longValue());
        LOGGER.debug("INI_ ciclo getListaMuestrasRM: {}", LocalDateTime.now());

        int countNoAnulados = 0;
        for (ExamenOrdenDTO ex : listExamenes) {
          if (ex.getDFE_EXAMENANULADO() != null) {
            if (!ex.getDFE_EXAMENANULADO().equals("S")) {
              countNoAnulados++;
            }
          } else {
            countNoAnulados++;
          }
        }
        mr.setALLEXAMENESANULADOS(countNoAnulados > 0 ? "N" : "S");
        if (mr.getOBSERVACIONRM() == null) {
          mr.setOBSERVACIONRM("");
        }
        if (mr.getIDDERIVADOR() == null) {
          mr.setIDDERIVADOR(BigDecimal.ZERO);
        }
        if (mr.getDERIVADOR() == null) {
          mr.setDERIVADOR("NO SE DERIVA");
        }
        LOGGER.debug("FIN_ ciclo getListaMuestrasRM: {}", LocalDateTime.now());
      }

    } catch (ParseException ex) {
      Logger.getLogger(RecepcionMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    LOGGER.debug("FIN getListaMuestrasRM: {}", LocalDateTime.now());

    return listaMuestras;
  }

  @Override
  public DatosOrdenPacienteExamenesDTO getDatosOrden(Long idMuestra) throws BiosLISDAOException {
    DatosOrdenPacienteExamenesDTO dopex = datosFichasmuestrasDAO.getDatosOrdenPaciente(idMuestra);
    List<ExamenOrdenDTO> listExamenes = cfgExamenesDAO.getExamenesByMuestra(idMuestra);
    dopex.setLIST_EXAMENES(listExamenes);
    return dopex;
  }

  @Override
  public Long recepcionarMuestraCodigoBarras(String codigoBarras, Long idUsuario) throws BiosLISDAOException {
    DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraByCodigoBarras(codigoBarras);
    if (dfm != null) {
      // valida que muestra esté tomada
      if (dfm.getDfmEstadotm().equals(EstadosSistema.DFM_ESTADOTM_TOMADA)) {
        // valida que muestra no esté recepcionada
        if (!dfm.getDfmEstadorm().equals(EstadosSistema.DFM_ESTADORM_RECEPCIONADA)) {
          try {
            String estado = dfm.getDfmEstadorm();
            dfm.setDfmCodigobarra(codigoBarras);
            dfm.setDfmFecharm(BiosLisCalendarService.getInstance().getTS());
            dfm.setDfmEstadorm(EstadosSistema.DFM_ESTADORM_RECEPCIONADA);
            dfm.setDfmIdusuariorm(idUsuario);
            datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
            this.cambiarEstadoExamenesMuestra(dfm, idUsuario);

            // aqui se realiza agregar datos a log eventos fichas.. creado por cristian
            // 14_02-2023 ***************************
            DatosFichas df = datosFichasDAO.getOrdenxID((int) dfm.getDfmNorden());
            LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
            LogEventosfichas lef = new LogEventosfichas();

            lef.setDatosFichas((int) dfm.getDfmNorden());
            lef.setLefFechaorden(df.getDfFechaorden());
            lef.setLefIdpaciente(df.getDatosPacientes());
            lef.setLefIdmuestra((int) dfm.getDfmIdmuestra());
            lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
            lef.setLefCodigobarra(dfm.getDfmCodigobarra());
            lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
            lef.setLefIdusuario(idUsuario.intValue());
            lef.setCfgEventos(2);
            if (estado.equals("R")) {
              lef.setLefValoranterior("RECEPCIONADA");
            } else if (estado.equals("P")) {
              lef.setLefValoranterior("PENDIENTE");
            } else {
              lef.setLefValoranterior("");
            }
            lef.setLefValornuevo("RECEPCIONADA");
            lef.setLefNombrecampo("DFM_ESTADORM");
            logEventosfichasDAO.insertLogEventosFichas(lef);

            // ************************************************************************

            // se elimino por cristian 24-02 codigo de trazabilidad antigua
            return dfm.getDfmIdmuestra();
          } catch (IllegalAccessException ex) {
            Logger.getLogger(RecepcionMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
          return -2L;
        }
      } else {
        return -1L;
      }
    }
    return null;
  }

  @Override
  public Long recepcionarMuestra(Long idMuestra, Long idUsuario) throws BiosLISDAOException {
    try {

      DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(idMuestra);
      String estado = dfm.getDfmEstadorm();
      dfm.setDfmEstadorm(EstadosSistema.DFM_ESTADORM_RECEPCIONADA);
      dfm.setDfmFecharm(BiosLisCalendarService.getInstance().getTS());
      dfm.setDfmIdusuariorm(idUsuario);
      datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
      this.cambiarEstadoExamenesMuestra(dfm, idUsuario);

      // aqui se realiza agregar datos a log eventos fichas.. creado por cristian
      // 14_02-2023 ***************************
      DatosFichas df = datosFichasDAO.getOrdenxID((int) dfm.getDfmNorden());
      LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
      LogEventosfichas lef = new LogEventosfichas();

      lef.setDatosFichas((int) dfm.getDfmNorden());
      lef.setLefFechaorden(df.getDfFechaorden());
      lef.setLefIdpaciente(df.getDatosPacientes());
      lef.setLefIdmuestra((int) dfm.getDfmIdmuestra());
      lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
      lef.setLefCodigobarra(dfm.getDfmCodigobarra());
      lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
      lef.setLefIdusuario(idUsuario.intValue());
      lef.setCfgEventos(2);
      if (estado.equals("R")) {
        lef.setLefValoranterior("RECEPCIONADA");
      } else if (estado.equals("P")) {
        lef.setLefValoranterior("PENDIENTE");
      } else {
        lef.setLefValoranterior("");
      }
      lef.setLefValornuevo("RECEPCIONADA");
      lef.setLefNombrecampo("DFM_ESTADORM");
      logEventosfichasDAO.insertLogEventosFichas(lef);

      // ************************************************************************

      return dfm.getDfmIdmuestra();
    } catch (IllegalAccessException ex) {
      Logger.getLogger(RecepcionMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }

  private void cambiarEstadoExamenesMuestra(DatosFichasmuestras muestra, Long idUsuario)
      throws IllegalAccessException, BiosLISDAOException {
    List<DatosFichasexamenestest> listaExamenesTest = datosFichasExamenesTestDAO
        .getTestByMuestra(muestra.getDfmIdmuestra());
    for (DatosFichasexamenestest examenesTest : listaExamenesTest) {
      examenesTest.setDfetIdestadoresultadotest(EstadosSistema.DFET_IDRESULTADOTEST_PENDIENTE);
      datosFichasExamenesTestDAO.updateDFExamenesTest(examenesTest);
      // se elimino por cristian 24-02 codigo de trazabilidad antigua

    }

    List<DatosFichasexamenes> listaExamenes = datosFichasExamenesDAO
        .getDatosFichasExamenesByMuestra(muestra.getDfmIdmuestra());
    for (DatosFichasexamenes examen : listaExamenes) {
      examen.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE);
      datosFichasExamenesDAO.updateDatosFichasExamenes(examen);
      // se elimino por cristian 24-02 codigo de trazabilidad antigua
    }
  }

  @Override
  public List<DatosUsuarios> listaRecepcionistas() throws BiosLISDAOException {
    return datosUsuariosDAO.listaRecepcionistas();
  }

  @Override
  public List<MuestrasDTO> getListaMuestrasPendientes() throws BiosLISDAOException {
    List<MuestrasDTO> listaMuestrasPendientes = new ArrayList<>();
    try {
      listaMuestrasPendientes = datosFichasmuestrasDAO.getMuestrasByEstadoRM(EstadosSistema.DFM_ESTADORM_PENDIENTE);
    } catch (ParseException ex) {
      Logger.getLogger(RecepcionMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return listaMuestrasPendientes;
  }

  @Override
  public List<MuestrasDTO> getListaMuestrasRecepcionadas() throws BiosLISDAOException {
    List<MuestrasDTO> listaMuestrasRecepcionadas = new ArrayList<>();
    try {
      listaMuestrasRecepcionadas = datosFichasmuestrasDAO
          .getMuestrasByEstadoRM(EstadosSistema.DFM_ESTADORM_RECEPCIONADA);
    } catch (ParseException ex) {
      Logger.getLogger(RecepcionMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return listaMuestrasRecepcionadas;
  }

  @Override
  public List<MuestrasDTO> getListaMuestrasConDerivador() throws BiosLISDAOException {
    List<MuestrasDTO> listaMuestrasConDerivador = new ArrayList<>();
    try {
      listaMuestrasConDerivador = datosFichasmuestrasDAO.getMuestrasConDerivador();
    } catch (ParseException ex) {
      Logger.getLogger(RecepcionMuestrasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return listaMuestrasConDerivador;
  }

  @Override
  public void guardarObservacion(Long idMuestra, String observacion, Long idUsuario) throws BiosLISDAOException {
    DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(idMuestra);
    String observacionAntigua = dfm.getDfmRecepcionobs();
    dfm.setDfmRecepcionobs(observacion);
    datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
    // aqui se realiza log evento update observacion *************
    DatosFichas df = datosFichasDAO.getOrdenxID((int) dfm.getDfmNorden());
    LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();

    lef.setLefCodigobarra(dfm.getDfmCodigobarra());
    lef.setLefIdmuestra((int) dfm.getDfmIdmuestra());
    lef.setDatosFichas((int) dfm.getDfmNorden());
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefIdpaciente(df.getDatosPacientes());
    lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
    lef.setLefIdusuario(idUsuario.intValue());
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefNombrecampo("DFM_RECEPCIONOBS");
    lef.setCfgEventos(2);
    lef.setLefValoranterior(observacionAntigua);
    lef.setLefValornuevo(observacion);

    logEventosfichasDAO.insertLogEventosFichas(lef);
  }

  @Override
  public void cambiarDerivador(Long idMuestra, Short idDerivador, Long idUsuario) throws BiosLISDAOException {
    List<DatosFichasexamenes> listExamenes = datosFichasExamenesDAO.getDatosFichasExamenesByMuestra(idMuestra);

    DatosFichas df = datosFichasDAO.getOrdenxID((int) listExamenes.get(0).getIddatosFichasExamenes().getDfeNorden());
    CfgDerivadoresDAO cd = new CfgDerivadoresDAO();

    LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();

    lef.setDatosFichas((int) listExamenes.get(0).getIddatosFichasExamenes().getDfeNorden());
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefIdpaciente(df.getDatosPacientes());
    lef.setLefNombretabla("DATOS_FICHASEXAMENES");
    lef.setLefIdusuario(idUsuario.intValue());
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefNombrecampo("DFE_IDDERIVADOR");
    lef.setCfgEventos(2);

    for (DatosFichasexamenes dfe : listExamenes) {

      CfgDerivadores valorAnterior = cd.getDerivadorById(dfe.getDfeIdderivador());
      CfgDerivadores valorNuevo = cd.getDerivadorById(idDerivador);

      dfe.setDfeIdderivador(idDerivador);
      datosFichasExamenesDAO.updateDatosFichasExamenes(dfe);

      lef.setLefIdexamen((int) dfe.getIddatosFichasExamenes().getDfeIdexamen());
      lef.setLefValoranterior(valorAnterior.getCderivDescripcion());
      lef.setLefValornuevo(valorNuevo.getCderivDescripcion());

      logEventosfichasDAO.insertLogEventosFichas(lef);
    }

  }

  @Override
  public void cambiarReceptor(Long idMuestra, Long idUsuario, Long usuario) throws BiosLISDAOException {
    DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(idMuestra);
    Long usuarioAnterior = dfm.getDfmIdusuariorm();
    dfm.setDfmIdusuariorm(idUsuario);
    datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);

    // se agregan datos para registrar cambio receptor log
    // eventos*************************

    DatosFichas df = datosFichasDAO.getOrdenxID((int) dfm.getDfmNorden());

    LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();

    DatosUsuarios du = datosUsuariosDAO.getUsuarioById(idUsuario);
    lef.setLefValornuevo(du.getDuNombres() + " " + du.getDuPrimerapellido() + " "
        + (du.getDuSegundoapellido() == null ? "" : du.getDuSegundoapellido()));
    DatosUsuarios duanterior = datosUsuariosDAO.getUsuarioById(usuarioAnterior);
    lef.setLefValoranterior(duanterior.getDuNombres() + " " + duanterior.getDuPrimerapellido() + " "
        + (duanterior.getDuSegundoapellido() == null ? "" : duanterior.getDuSegundoapellido()));

    lef.setLefIdmuestra((int) dfm.getDfmIdmuestra());
    lef.setLefCodigobarra(dfm.getDfmCodigobarra());
    lef.setDatosFichas(df.getDfNorden());
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefIdpaciente(df.getDatosPacientes());
    lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
    lef.setLefIdusuario(usuario.intValue());
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefNombrecampo("DFM_IDUSUARIORM");
    lef.setCfgEventos(2);

    logEventosfichasDAO.insertLogEventosFichas(lef);
    // ****************************************************************************************
    /*
     * try { bl.logInsertDatosFichaTableRecord(DatosFichasmuestras.class, dfm, new
     * BigDecimal(idUsuario), BiosLisCalendarService.getInstance().getTS(), new
     * BigDecimal(dfm.getDfmNorden()), null, null, new BigDecimal(idMuestra), null,
     * null, "", Constante.MODIFICA_DATOS_FICHAS); } catch (IllegalArgumentException
     * e1) { LOGGER.error("No se pudo insertar registro de log de tabla.\n{}",
     * e1.getMessage()); } catch (IllegalAccessException ex) {
     * Logger.getLogger(RecepcionMuestrasServiceImpl.class.getName()).log(Level.
     * SEVERE, null, ex); }
     */
  }

  @Override
  public void cambiarEstadoPendiente(Long idMuestra, Long idUsuario) throws BiosLISDAOException {
    DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(idMuestra);
    String estadoAnterior = dfm.getDfmEstadorm();
    dfm.setDfmEstadorm(EstadosSistema.DFM_ESTADORM_PENDIENTE);
    dfm.setDfmIdusuariorm(0L);
    dfm.setDfmFecharm(null);
    datosFichasmuestrasDAO.updateDatosFichasmuestras(dfm);
    // aqui se agregan los datos para log eventos cambio a pendiente la recepcion de
    // muestra

    DatosFichas df = datosFichasDAO.getOrdenxID((int) dfm.getDfmNorden());
    LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();

    lef.setLefCodigobarra(dfm.getDfmCodigobarra());
    lef.setLefIdmuestra((int) dfm.getDfmIdmuestra());
    lef.setDatosFichas((int) dfm.getDfmNorden());
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefIdpaciente(df.getDatosPacientes());
    lef.setLefNombretabla("DATOS_FICHASMUESTRAS");
    lef.setLefIdusuario(idUsuario.intValue());
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefNombrecampo("DFM_ESTADOTM");
    lef.setCfgEventos(2);

    if (estadoAnterior != null) {
      if (estadoAnterior.equals("R")) {
        lef.setLefValoranterior("RECEPCIONADA");
      } else if (estadoAnterior.equals("P")) {
        lef.setLefValoranterior("PENDIENTE");
      } else {
        lef.setLefValoranterior("");
      }

    }
    lef.setLefValornuevo("PENDIENTE");

    logEventosfichasDAO.insertLogEventosFichas(lef);
    // ***********************************************************************************************

    List<DatosFichasexamenestest> listaExamenesTest = datosFichasExamenesTestDAO.getTestByMuestra(idMuestra);
    for (DatosFichasexamenestest examenesTest : listaExamenesTest) {
      examenesTest.setDfetIdestadoresultadotest(EstadosSistema.DFET_IDRESULTADOTEST_INGRESADO);
      datosFichasExamenesTestDAO.updateDFExamenesTest(examenesTest);
      /*
       * try { bl.logInsertDatosFichaTableRecord(DatosFichasexamenestest.class,
       * examenesTest, new BigDecimal(idUsuario),
       * BiosLisCalendarService.getInstance().getTS(), new
       * BigDecimal(dfm.getDfmNorden()), null, new
       * BigDecimal(examenesTest.getId().getDfetIdexamen()), new
       * BigDecimal(idMuestra), null, null, "", Constante.MODIFICA_DATOS_FICHAS); }
       * catch (IllegalArgumentException | IllegalAccessException ex) {
       * Logger.getLogger(RecepcionMuestrasServiceImpl.class.getName()).log(Level.
       * SEVERE, null, ex); }
       */
    }

    List<DatosFichasexamenes> listaExamenes = datosFichasExamenesDAO.getDatosFichasExamenesByMuestra(idMuestra);
    for (DatosFichasexamenes examen : listaExamenes) {
      examen.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_INGRESADO);
      datosFichasExamenesDAO.updateDatosFichasExamenes(examen);
      /*
       * try { bl.logInsertDatosFichaTableRecord(DatosFichasexamenes.class, examen,
       * new BigDecimal(idUsuario), BiosLisCalendarService.getInstance().getTS(), new
       * BigDecimal(dfm.getDfmNorden()), null, new
       * BigDecimal(examen.getIddatosFichasExamenes().getDfeIdexamen()), new
       * BigDecimal(idMuestra), null, null, "", Constante.MODIFICA_DATOS_FICHAS); }
       * catch (IllegalArgumentException | IllegalAccessException ex) {
       * Logger.getLogger(RecepcionMuestrasServiceImpl.class.getName()).log(Level.
       * SEVERE, null, ex); }
       */
    }

  }

}
