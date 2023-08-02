/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.grupobios.bioslis.common.ActionExamenExecutor;
import com.grupobios.bioslis.common.ActionExamenExecutorFactory;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisLogEventos;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.common.ExamenBiosAction;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.CfgDerivadoresDAO;
import com.grupobios.bioslis.dao.CfgExamenesConjuntosDAO;
import com.grupobios.bioslis.dao.CfgExamenesConjuntosDetalleDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgExamenesIndicacionesTMDAO;
import com.grupobios.bioslis.dao.CfgExamenesTestDAO;
import com.grupobios.bioslis.dao.CfgGruposExamenesFonasaDAO;
import com.grupobios.bioslis.dao.CfgGruposMuestrasExaDAO;
import com.grupobios.bioslis.dao.CfgMetodosDAO;
import com.grupobios.bioslis.dao.CfgMuestrasDAO;
import com.grupobios.bioslis.dao.CfgPesquisasDAO;
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.dao.CfgTablasreferenciasexamenesDAO;
import com.grupobios.bioslis.dao.CfgTestDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesTestDAO;
import com.grupobios.bioslis.dao.LdvFormatosDAO;
import com.grupobios.bioslis.dao.LdvGruposExamenesDAO;
import com.grupobios.bioslis.dao.LdvIndicacionesExamenesDAO;
import com.grupobios.bioslis.dao.LdvLoincDAO;
import com.grupobios.bioslis.dao.LdvTiposExamenesDAO;
import com.grupobios.bioslis.dao.LogCfgTablasDAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.ExamenFullDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ExamenPacienteDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.dto.TestRepetidosDTO;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgEnvases;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgExamenesconjuntodetalle;
import com.grupobios.bioslis.entity.CfgExamenesconjuntodetalleId;
import com.grupobios.bioslis.entity.CfgExamenesconjuntos;
import com.grupobios.bioslis.entity.CfgExamenesindicacionestm;
import com.grupobios.bioslis.entity.CfgExamenestablasanexas;
import com.grupobios.bioslis.entity.CfgExamenestest;
import com.grupobios.bioslis.entity.CfgExamenestestId;
import com.grupobios.bioslis.entity.CfgGruposexamenesfonasa;
import com.grupobios.bioslis.entity.CfgGruposmuestrasexa;
import com.grupobios.bioslis.entity.CfgGruposmuestrasexaId;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgPesquisas;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.CfgTablasreferenciasexamenes;
import com.grupobios.bioslis.entity.CfgTablasreferenciasexamenesId;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenesId;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.LdvFormatos;
import com.grupobios.bioslis.entity.LdvGruposexamenes;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;
import com.grupobios.bioslis.entity.LdvTiposexamenes;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.bioslis.entity.LogEventosfichas;

/**
 *
 * @author eric.nicholls
 */

public class ExamenServiceImpl implements ExamenService {

  private static Logger logger = LogManager.getLogger(ExamenServiceImpl.class);
  @Autowired
  private DatosFichasExamenesDAO datosFichasExamenesDAO;
  @Autowired
  private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;
  private Map<String, Short> mapCodEstado = new HashMap<>();

  @Autowired
  private CfgExamenesDAO cfgExamenesDAO;

  @Autowired
  private CfgTestDAO cfgTestDAO;

  private Map<Short, String> mapEstadoCod = new HashMap<>();
  private Map<Pair<Short, String>, Short> examenEstadomatrizTransicion = new HashMap<>();
  @Autowired
  private ActionExamenExecutorFactory actionExamenExecutorFactory;

  @Autowired
  private DatosFichasDAO datosFichasDAO;

  @Autowired
  private CfgDerivadoresDAO cfgDerivadoresDAO;

  @Autowired
  private LdvIndicacionesExamenesDAO indicacionesExamenesDAO;

  @Autowired
  private CfgMuestrasDAO cfgMuestrasDAO;

  @Autowired
  private CfgSeccionesDAO cfgSeccionesDAO;

  @Autowired
  private CfgPesquisasDAO cfgPesquisasDAO;

  @Autowired
  private LdvLoincDAO ldvLoincDAO;

  @Autowired
  private LdvFormatosDAO ldvFormatosDAO;

  @Autowired
  private LdvGruposExamenesDAO ldvGruposExamenesDAO;

  @Autowired
  private CfgExamenesTestDAO cfgExamenesTestDAO;

  @Autowired
  private CfgGruposMuestrasExaDAO cfgGruposMuestrasExaDAO;

  @Autowired
  private CfgExamenesIndicacionesTMDAO cfgExamenesIndicacionesTMDAO;

  @Autowired
  private CfgGruposExamenesFonasaDAO gruposExamenesFonasaDAO;

  @Autowired
  private CfgMetodosDAO cfgMetodosDAO;

  @Autowired
  private LdvTiposExamenesDAO tiposExamenesDAO;

  @Autowired
  private CfgExamenesConjuntosDAO examenesConjuntosDAO;

  @Autowired
  private CfgExamenesConjuntosDetalleDAO examenesConjuntosDetalleDAO;

  @Autowired
  private CfgTablasreferenciasexamenesDAO tablasreferenciasexamenesDAO;

  

  LogCfgTablasDAO logCfgTablasDAO; 


  public LogCfgTablasDAO getLogCfgTablasDAO() {
	return logCfgTablasDAO;
}

public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
	this.logCfgTablasDAO = logCfgTablasDAO;
}

public ExamenServiceImpl() {
    this.examenEstadoAccionMapInit();
  }

  public DatosFichasExamenesTestDAO getDatosFichasExamenesTestDAO() {
    return datosFichasExamenesTestDAO;
  }

  public void setDatosFichasExamenesTestDAO(DatosFichasExamenesTestDAO datosFichasExamenesTestDAO) {
    this.datosFichasExamenesTestDAO = datosFichasExamenesTestDAO;
  }

  @Override
  public void updateExamen(DatosFichasexamenes dfe) throws BiosLISDAOException {

    datosFichasExamenesDAO.updateExamen(dfe);
  }

  /**
   * @return the datosFichasExamenesDAO
   */
  public DatosFichasExamenesDAO getDatosFichasExamenesDAO() {
    return datosFichasExamenesDAO;
  }

  /**
   * @param datosFichasExamenesDAO the datosFichasExamenesDAO to set
   */
  public void setDatosFichasExamenesDAO(DatosFichasExamenesDAO datosFichasExamenesDAO) {
    this.datosFichasExamenesDAO = datosFichasExamenesDAO;
  }

  @Override
  public List<DatosFichasexamenes> getAll() throws BiosLISDAOException {
    return datosFichasExamenesDAO.getAll();

  }

  @Override
  public Boolean readyToSign(Long nOrden) throws BiosLISDAOException {
    return datosFichasExamenesDAO.readyToSign(nOrden);
  }

  @Override
  public Boolean readyToSign(Long idExamen, Long nOrden) throws BiosLISDAOException {
    return datosFichasExamenesTestDAO.readyToSign(idExamen, nOrden);
  }

  @Override
  public void sign(ResultadoNumericoTestExamenOrdenDTO dfetId) throws BiosLISDAONotFoundException, BiosLISDAOException {

//    datosFichasExamenesDAO.signExamen(dfetId.getDF_NORDEN().longValue(), dfetId.getDFE_IDEXAMEN().longValue(),
//        idusuario);
    return;
  }

  public LdvIndicacionesExamenesDAO getIndicacionesExamenesDAO() {
    return indicacionesExamenesDAO;
  }

  public void setIndicacionesExamenesDAO(LdvIndicacionesExamenesDAO indicacionesExamenesDAO) {
    this.indicacionesExamenesDAO = indicacionesExamenesDAO;
  }

  public CfgMuestrasDAO getCfgMuestrasDAO() {
    return cfgMuestrasDAO;
  }

  public void setCfgMuestrasDAO(CfgMuestrasDAO cfgMuestrasDAO) {
    this.cfgMuestrasDAO = cfgMuestrasDAO;
  }

  private void examenEstadoAccionMapInit() {

    mapCodEstado.put(EstadosSistema.DFE_CODIGOESTADOEXAMEN_AUTORIZADO, EstadosSistema.DFE_IDESTADOEXAMEN_AUTORIZADO);
    mapCodEstado.put(EstadosSistema.DFE_CODIGOESTADOEXAMEN_BLOQUEADO, EstadosSistema.DFE_IDESTADOEXAMEN_BLOQUEADO);
    mapCodEstado.put(EstadosSistema.DFE_CODIGOESTADOEXAMEN_ENPROCESO, EstadosSistema.DFE_IDESTADOEXAMEN_ENPROCESO);
    mapCodEstado.put(EstadosSistema.DFE_CODIGOESTADOEXAMEN_FIRMADO, EstadosSistema.DFE_IDESTADOEXAMEN_FIRMADO);
    mapCodEstado.put(EstadosSistema.DFE_CODIGOESTADOEXAMEN_INGRESADO, EstadosSistema.DFE_IDESTADOEXAMEN_INGRESADO);
    mapCodEstado.put(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE, EstadosSistema.DFE_IDESTADOEXAMEN_PENDIENTE);

    mapEstadoCod.put(EstadosSistema.DFE_IDESTADOEXAMEN_AUTORIZADO, EstadosSistema.DFE_CODIGOESTADOEXAMEN_AUTORIZADO);
    mapEstadoCod.put(EstadosSistema.DFE_IDESTADOEXAMEN_BLOQUEADO, EstadosSistema.DFE_CODIGOESTADOEXAMEN_BLOQUEADO);
    mapEstadoCod.put(EstadosSistema.DFE_IDESTADOEXAMEN_ENPROCESO, EstadosSistema.DFE_CODIGOESTADOEXAMEN_ENPROCESO);
    mapEstadoCod.put(EstadosSistema.DFE_IDESTADOEXAMEN_FIRMADO, EstadosSistema.DFE_CODIGOESTADOEXAMEN_FIRMADO);
    mapEstadoCod.put(EstadosSistema.DFE_IDESTADOEXAMEN_INGRESADO, EstadosSistema.DFE_CODIGOESTADOEXAMEN_INGRESADO);
    mapEstadoCod.put(EstadosSistema.DFE_IDESTADOEXAMEN_PENDIENTE, EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE);

    Pair<Short, String> examenEstadoAccion = Pair.of(EstadosSistema.DFE_IDESTADOEXAMEN_FIRMADO,
        ExamenBiosAction.AUTORIZAR.toString());
    this.examenEstadomatrizTransicion.put(examenEstadoAccion, EstadosSistema.DFE_IDESTADOEXAMEN_AUTORIZADO);
    examenEstadoAccion = Pair.of(EstadosSistema.DFE_IDESTADOEXAMEN_AUTORIZADO,
        ExamenBiosAction.DESAUTORIZAR.toString());
    this.examenEstadomatrizTransicion.put(examenEstadoAccion, EstadosSistema.DFE_IDESTADOEXAMEN_FIRMADO);
    examenEstadoAccion = Pair.of(EstadosSistema.DFE_IDESTADOEXAMEN_AUTORIZADO, ExamenBiosAction.AUTORIZAR.toString());
    this.examenEstadomatrizTransicion.put(examenEstadoAccion, EstadosSistema.DFE_IDESTADOEXAMEN_AUTORIZADO);

  }

  @Override
  public List<DatosFichasexamenes> doAction(String actionCode, List<ExamenOrdenDTO> listaExamenes, Long idUsuario)
      throws BiosLISBSException, BiosLISDAONotFoundException, BiosLISDAOException {
    List<DatosFichasexamenes> lstDatosFichasexamenes = this.executeTransition(actionCode, listaExamenes, idUsuario); // Se
                                                                                                                     // marca
    this.executeAction(actionCode, listaExamenes, idUsuario);
    return lstDatosFichasexamenes;
  }

  private List<DatosFichasexamenes> executeTransition(String actionCode, List<ExamenOrdenDTO> listaExamenes,
      Long idUsuario) throws BiosLISDAONotFoundException, BiosLISDAOException, BiosLISBSException {
    List<DatosFichasexamenes> lstDatosFichasexamenes = datosFichasExamenesDAO
        .getDatosFichasExamenesByIdOrdenExamen(listaExamenes);
    if (lstDatosFichasexamenes.isEmpty()) {
      throw new BiosLISDAONotFoundException("No se encontró datosfichasexamenes");
    }
    for (DatosFichasexamenes datosFichasexamenes : lstDatosFichasexamenes) {

      logger.debug(datosFichasexamenes.getDfeCodigoestadoexamen());
      Short examenEstado = this.getNextState(datosFichasexamenes.getDfeCodigoestadoexamen(), actionCode);
      if (examenEstado == null) {
        logger.error("No se puede {} cuando estado actual es {}", actionCode,
            datosFichasexamenes.getDfeCodigoestadoexamen());
        throw new BiosLISBSException("No se pudo ejecutar acción " + actionCode);
      }
      datosFichasexamenes.setDfeCodigoestadoexamen(mapEstadoCod.get(examenEstado));

      if (actionCode.equals("DESAUTORIZAR")) {
        Session sesion = null;

        try {
          DatosFichas ordenPaciente = new DatosFichas();
          sesion = HibernateUtil.getSessionFactory().openSession();

          ordenPaciente = (DatosFichas) sesion.get(DatosFichas.class,
              (int) datosFichasexamenes.getIddatosFichasExamenes().getDfeNorden());
          sesion.close();
          LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
          LogEventosfichas lef = new LogEventosfichas();

          lef.setDatosFichas(ordenPaciente.getDfNorden());
          lef.setLefFechaorden(ordenPaciente.getDfFechaorden());
          lef.setLefIdpaciente(ordenPaciente.getDatosPacientes());
          lef.setLefNombretabla("DATOS_FICHASEXAMENES");
          lef.setLefIdexamen((int) datosFichasexamenes.getIddatosFichasExamenes().getDfeIdexamen());
          lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
          lef.setLefIdusuario(idUsuario.intValue());
          lef.setCfgEventos(2);
          lef.setLefNombrecampo("DFE_CODIGOESTADOEXAMEN");
          lef.setLefValoranterior("AUTORIZADO");
          lef.setLefValornuevo("FIRMADO");
          logEventosfichasDAO.insertLogEventosFichas(lef);

        } catch (HibernateException he) {
          if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
          logger.error(he.getLocalizedMessage());
          throw new BiosLISDAOException(he.getMessage());
        } finally {
          if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        }
        // saca la autorizacion del examen
        datosFichasexamenes.setDfeAutorizado("N");
        datosFichasexamenes.setDfeFechaautoriza(null);
        datosFichasexamenes.setDfeIdusuarioautoriza(null);
        // al desautorizar cambio los valores de disponibilidad web
        datosFichasexamenes.setDfeWebdisponible("N");
        datosFichasexamenes.setDfeWebdisponiblefecha(null);

      }
      logger.debug("Estado final: {} final: {}", datosFichasexamenes.getDfeCodigoestadoexamen(), examenEstado);
    }

    datosFichasExamenesDAO.updateEstados(lstDatosFichasexamenes);
    return lstDatosFichasexamenes;

  }

  private Short getNextState(String dfeCodigoestadoexamen, String actionCode) {

    Pair<Short, String> examenEstadoAccion = Pair.of(mapCodEstado.get(dfeCodigoestadoexamen), actionCode);

    logger.debug("Codigo: {} id:{}", dfeCodigoestadoexamen, mapCodEstado.get(dfeCodigoestadoexamen));
    return this.examenEstadomatrizTransicion.get(examenEstadoAccion);
  }

  private void executeAction(String actionCode, List<ExamenOrdenDTO> listaExamenes, Long idUsuario)
      throws BiosLISDAOException {

    ActionExamenExecutor actionExamenExecutor = (ActionExamenExecutor) actionExamenExecutorFactory
        .getInstance(actionCode);
    actionExamenExecutor.exec(listaExamenes, idUsuario);
  }

  @Override
  public boolean readyToSign(DatosFichasexamenesId iddatosFichasExamenes) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void sign(Long nroOrden) {
    // TODO Auto-generated method stub

  }

  public ActionExamenExecutorFactory getActionExamenExecutorFactory() {
    return actionExamenExecutorFactory;
  }

  public void setActionExamenExecutorFactory(ActionExamenExecutorFactory actionExamenExecutorFactory) {
    this.actionExamenExecutorFactory = actionExamenExecutorFactory;
  }

  @Override
  public ExamenPacienteDTO getExamenesPaciente(long nOrden, long idMuestra) throws BiosLISDAOException {
    ExamenPacienteDTO examenPacienteCurvaDTO = datosFichasDAO.getExamenPacienteCurva(nOrden, idMuestra);
    return examenPacienteCurvaDTO;
  }

  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  public CfgDerivadoresDAO getCfgDerivadoresDAO() {
    return cfgDerivadoresDAO;
  }

  public void setCfgDerivadoresDAO(CfgDerivadoresDAO cfgDerivadoresDAO) {
    this.cfgDerivadoresDAO = cfgDerivadoresDAO;
  }

  @Override
  public List<TestRepetidosDTO> getTestRepetidosExamenes() throws BiosLISDAOException {

    return cfgExamenesDAO.getTestRepetidosExamenes();
  }

  public CfgExamenesDAO getCfgExamenesDAO() {
    return cfgExamenesDAO;
  }

  public void setCfgExamenesDAO(CfgExamenesDAO cfgExamenesDAO) {
    this.cfgExamenesDAO = cfgExamenesDAO;
  }

  @Override
  public List<TestRepetidosDTO> checkTestRepetidosExamenes(Integer[] testIdArray) throws BiosLISDAOException {

    List<TestRepetidosDTO> lst = cfgExamenesDAO.getTestRepetidosExamenes();
    List<TestRepetidosDTO> lstAux = new ArrayList<TestRepetidosDTO>();

    for (TestRepetidosDTO testRepetidosDTO : lst) {
      for (Integer idTest : testIdArray) {
        if (idTest.equals(testRepetidosDTO.getIdTest())) {
          lstAux.add(testRepetidosDTO);
        }
      }
    }
    return lstAux;
  }

  @Override
  public String getNombresTestRepetidosExamenes(Integer[] testIdArray) throws BiosLISDAOException {

    List<TestRepetidosDTO> lstTest = this.checkTestRepetidosExamenes(testIdArray);
    StringBuilder sb = new StringBuilder("Examenes ");
    lstTest.forEach((t) -> {
      sb.append(t.getDescripcionTest()).append("\n");
    });
    return sb.toString();
  }

  @Override
  public List<TestRepetidosDTO> getTestRepetidosExamenes(Integer[] lstExamenes) throws BiosLISDAOException {

    return cfgExamenesDAO.getTestRepetidosExamenes(lstExamenes);

  }

  @Override
  public String getNombresTestRepetidosExamenes(CfgExamenesDTO[] lstExamenesDto) throws BiosLISDAOException {

    Integer[] lstId = new Integer[lstExamenesDto.length];

    int i = 0;
    for (CfgExamenesDTO cfgExamenDTO : lstExamenesDto) {
      lstId[i] = Long.valueOf(cfgExamenDTO.getCeIdexamen()).intValue();
    }

    return this.getNombresTestRepetidosExamenes(lstId);
  }

  @Override
  public List<CfgTest> getTestOpcionalesExamenes(Long idExamen) throws BiosLISDAOException {
    return cfgExamenesDAO.getTestOpcionalesExamenes(idExamen);
  }

  // métodos para Módulo de exámenes por Marco Caracciolo 21/09/22
  @Override
  public List<CfgExamenes> getListaExamanesFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    List<CfgExamenes> listaExamenes = cfgExamenesDAO.getExamenesFiltro(filtros);
    /*
     * List<CfgExamenes> listaExamenes; String codigo =
     * filtros.get("codigo").toUpperCase(); String nombre =
     * filtros.get("nombre").toUpperCase(); int idSeccion =
     * Integer.parseInt(filtros.get("idSeccion")); String activo =
     * filtros.get("activo"); if (codigo.length() > 0) { listaExamenes =
     * cfgExamenesDAO.getExamenesByCodigoLike(codigo, activo); } else {
     * listaExamenes = cfgExamenesDAO.getExamenesByDescLike(nombre, idSeccion,
     * activo); }
     */
    return listaExamenes;
  }

  public CfgSeccionesDAO getCfgSeccionesDAO() {
    return cfgSeccionesDAO;
  }

  public void setCfgSeccionesDAO(CfgSeccionesDAO cfgSeccionesDAO) {
    this.cfgSeccionesDAO = cfgSeccionesDAO;
  }

  public CfgPesquisasDAO getCfgPesquisasDAO() {
    return cfgPesquisasDAO;
  }

  public void setCfgPesquisasDAO(CfgPesquisasDAO cfgPesquisasDAO) {
    this.cfgPesquisasDAO = cfgPesquisasDAO;
  }

  public LdvLoincDAO getLdvLoincDAO() {
    return ldvLoincDAO;
  }

  public void setLdvLoincDAO(LdvLoincDAO ldvLoincDAO) {
    this.ldvLoincDAO = ldvLoincDAO;
  }

  public LdvFormatosDAO getLdvFormatosDAO() {
    return ldvFormatosDAO;
  }

  public void setLdvFormatosDAO(LdvFormatosDAO ldvFormatosDAO) {
    this.ldvFormatosDAO = ldvFormatosDAO;
  }

  public LdvGruposExamenesDAO getLdvGruposExamenesDAO() {
    return ldvGruposExamenesDAO;
  }

  public void setLdvGruposExamenesDAO(LdvGruposExamenesDAO ldvGruposExamenesDAO) {
    this.ldvGruposExamenesDAO = ldvGruposExamenesDAO;
  }

  public CfgExamenesTestDAO getCfgExamenesTestDAO() {
    return cfgExamenesTestDAO;
  }

  public void setCfgExamenesTestDAO(CfgExamenesTestDAO cfgExamenesTestDAO) {
    this.cfgExamenesTestDAO = cfgExamenesTestDAO;
  }

  public CfgGruposMuestrasExaDAO getCfgGruposMuestrasExaDAO() {
    return cfgGruposMuestrasExaDAO;
  }

  public void setCfgGruposMuestrasExaDAO(CfgGruposMuestrasExaDAO cfgGruposMuestrasExaDAO) {
    this.cfgGruposMuestrasExaDAO = cfgGruposMuestrasExaDAO;
  }

  public CfgExamenesIndicacionesTMDAO getCfgExamenesIndicacionesTMDAO() {
    return cfgExamenesIndicacionesTMDAO;
  }

  public void setCfgExamenesIndicacionesTMDAO(CfgExamenesIndicacionesTMDAO cfgExamenesIndicacionesTMDAO) {
    this.cfgExamenesIndicacionesTMDAO = cfgExamenesIndicacionesTMDAO;
  }

  public CfgGruposExamenesFonasaDAO getGruposExamenesFonasaDAO() {
    return gruposExamenesFonasaDAO;
  }

  public void setGruposExamenesFonasaDAO(CfgGruposExamenesFonasaDAO gruposExamenesFonasaDAO) {
    this.gruposExamenesFonasaDAO = gruposExamenesFonasaDAO;
  }

  public CfgMetodosDAO getCfgMetodosDAO() {
    return cfgMetodosDAO;
  }

  public void setCfgMetodosDAO(CfgMetodosDAO cfgMetodosDAO) {
    this.cfgMetodosDAO = cfgMetodosDAO;
  }

  public LdvTiposExamenesDAO getTiposExamenesDAO() {
    return tiposExamenesDAO;
  }

  public void setTiposExamenesDAO(LdvTiposExamenesDAO tiposExamenesDAO) {
    this.tiposExamenesDAO = tiposExamenesDAO;
  }

  @Override
  public ExamenFullDTO getExamenById(long idExamen) throws BiosLISDAOException, BiosLISDAONotFoundException {
    ExamenFullDTO examDTO = new ExamenFullDTO();
    CfgExamenes cfgExamen = cfgExamenesDAO.getExamenById(idExamen);
    examDTO.setCfgExamenes(cfgExamen);
    CfgDerivadores cfgDerivadores = cfgDerivadoresDAO
        .getDerivadorById(cfgExamen.getCfgDerivadores().getCderivIdderivador());
    examDTO.setCfgDerivadores(cfgDerivadores);
    CfgMuestras cfgMuestras = cfgMuestrasDAO.getMuestraById(cfgExamen.getCfgMuestras().getCmueIdtipomuestra());
    examDTO.setCfgMuestras(cfgMuestras);
    CfgSecciones cfgSecciones = cfgSeccionesDAO.getSeccionById(cfgExamen.getCfgSecciones().getCsecIdseccion());
    examDTO.setCfgSecciones(cfgSecciones);
    CfgPesquisas cfgPesquisas = cfgPesquisasDAO.getPesquisaById(cfgExamen.getCfgPesquisas().getCpeIdpesquisa());
    examDTO.setCfgPesquisas(cfgPesquisas);
    if (cfgExamen.getLdvIndicacionesexamenes() != null) { // ok
      LdvIndicacionesexamenes indicacionesexamenes = indicacionesExamenesDAO.getIndicacionesExamenByIdExamen(idExamen);
      examDTO.setLdvIndicacionesexamenes(indicacionesexamenes);
    }
    // if (cfgExamen.getLdvLoinc() != null) { // ok
    // LdvLoinc ldvLoinc = ldvLoincDAO.getCodigoLoincByExamen(idExamen);
    // examDTO.setLdvLoinc(ldvLoinc);
    // }
    if (cfgExamen.getLdvFormatos() != null) { // ok
      LdvFormatos ldvFormatos = ldvFormatosDAO.getFormatoByExamen(idExamen);
      examDTO.setLdvFormatos(ldvFormatos);
    }
    if (cfgExamen.getCfgGruposexamenesfonasa() != null) { // ok
      CfgGruposexamenesfonasa gruposexamenesfonasa = gruposExamenesFonasaDAO.getGrupoExamenesFonasaByExamen(idExamen);
      examDTO.setCfgGruposexamenesfonasa(gruposexamenesfonasa);
    }
    if (cfgExamen.getLdvGruposexamenes() != null) {
      LdvGruposexamenes gruposexamenes = ldvGruposExamenesDAO.getGrupoExamenesByIDExamen(idExamen);
      examDTO.setLdvGruposexamenes(gruposexamenes);
    }
    if (cfgExamen.getLdvTiposexamenes() != null) {
      LdvTiposexamenes tiposexamenes = tiposExamenesDAO.getTipoExamen(idExamen);
      examDTO.setLdvTiposexamenes(tiposexamenes);
    }
    CfgExamenesconjuntos cec = null;
    if (cfgExamen.getCfgExamenesconjuntos() != null) {
      cec = examenesConjuntosDAO.getExamenesConjuntosByExamen(idExamen);
      examDTO.setCfgExamenesconjuntos(cec);
    }
    if (cfgExamen.getCeIdtablareferenciaexamen() != null) {
      CfgExamenestablasanexas ceta = tablasreferenciasexamenesDAO
          .getTablaAnexaById(cfgExamen.getCeIdtablareferenciaexamen());
      examDTO.setExamenestablasanexas(ceta);
    }
    List<CfgExamenestest> listExamenesTest = cfgExamenesTestDAO.getTestByExamenId((int) idExamen);
    CfgExamenestest[] examenesTest = listExamenesTest.toArray(new CfgExamenestest[0]);
    examDTO.setListTestsExamen(listExamenesTest.toArray(examenesTest));

    List<CfgGruposmuestrasexa> listGruposmuestrasexa = cfgGruposMuestrasExaDAO.getGruposMuestrasExamen(idExamen);
    CfgGruposmuestrasexa[] gruposmuestrasexa = listGruposmuestrasexa.toArray(new CfgGruposmuestrasexa[0]);
    examDTO.setListGruposmuestrasexa(listGruposmuestrasexa.toArray(gruposmuestrasexa));

    List<CfgExamenesindicacionestm> listExamenesindicacionestm = cfgExamenesIndicacionesTMDAO
        .getIndicacionesTM(idExamen);
    CfgExamenesindicacionestm[] eExamenesIndicacionesTM = listExamenesindicacionestm
        .toArray(new CfgExamenesindicacionestm[0]);
    examDTO.setListExamenesIndicacionesTM(listExamenesindicacionestm.toArray(eExamenesIndicacionesTM));

    if (cfgExamen.getCfgExamenesconjuntos() != null) {

      List<CfgExamenesconjuntodetalle> listExamenesConjuntos = examenesConjuntosDetalleDAO
          .getExamenesConjuntosById(cec.getCecIdexamenesconjuntos());
      CfgExamenesconjuntodetalle[] examenesConjuntosDetalle = listExamenesConjuntos
          .toArray(new CfgExamenesconjuntodetalle[0]);
      examDTO.setExamenesconjuntodetalles(listExamenesConjuntos.toArray(examenesConjuntosDetalle));
    }

    if (cfgExamen.getCeIdtablareferenciaexamen() != null) {
      CfgExamenestablasanexas tablaAnexa = tablasreferenciasexamenesDAO
          .getTablaAnexaById(cfgExamen.getCeIdtablareferenciaexamen());

      List<CfgTablasreferenciasexamenes> lisTablaReferencias = tablasreferenciasexamenesDAO
          .getCeldasTablaReferenciasById(tablaAnexa.getCetaIdexamentabla());
      CfgTablasreferenciasexamenes[] tablaReferencias = lisTablaReferencias
          .toArray(new CfgTablasreferenciasexamenes[0]);
      examDTO.setTablaReferencias(lisTablaReferencias.toArray(tablaReferencias));
    }

    return examDTO;
  }

  @Override
  public void unsign(DatosFichasexamenesId iddatosFichasExamenes) {
    datosFichasExamenesDAO.unsignExamen(iddatosFichasExamenes.getDfeNorden(), iddatosFichasExamenes.getDfeIdexamen());

  }

  @Override
  public void sign(List<ExamenOrdenDTO> listaExamenes, Long idUsuario) {
    // TODO Auto-generated method stub

  }

  @Override
  public void sign(DatosFichasexamenesId iddatosFichasExamenes, Long idUsuario) {

  }

  @Override
  public void sign(ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISDAOException {

    this.datosFichasExamenesDAO.signExamen(dfetId.getDF_NORDEN().longValueExact(),
        dfetId.getDFE_IDEXAMEN().longValueExact(), idUsuario);
  }

  @Override
  public List<LdvIndicacionesexamenes> getIndicacionesExamenes() throws BiosLISDAOException {
    return indicacionesExamenesDAO.getListIndicacionesExamen();
  }

  @Override
  public void insertExamen(ExamenFullDTO efdto , Long idUsuario) throws BiosLISDAOException {
    // guardar examenes conjuntos
    CfgExamenes newExamen = efdto.getCfgExamenes();
    /*
     * newExamen.setCfgMuestras(efdto.getCfgMuestras()); CfgDerivadores derivador =
     * cfgDerivadoresDAO.getDerivadorById(efdto.getIdDerivador());
     * newExamen.setCfgDerivadores(derivador);
     * newExamen.setCfgDerivadores(cfgDerivadoresDAO.getDerivadorById(efdto.
     * getCfgDerivadores().getCderivIdderivador()));
     * 
     * newExamen.setCfgSecciones(efdto.getCfgSecciones());
     * newExamen.setLdvIndicacionesexamenes(efdto.getLdvIndicacionesexamenes());
     * newExamen.setLdvFormatos(efdto.getLdvFormatos());
     * newExamen.setLdvLoinc(efdto.getLdvLoinc());
     * newExamen.setCfgGruposexamenesfonasa(efdto.getCfgGruposexamenesfonasa());
     * newExamen.setLdvGruposexamenes(efdto.getLdvGruposexamenes());
     * newExamen.setCfgPesquisas(efdto.getCfgPesquisas());
     * newExamen.setLdvTiposexamenes(efdto.getLdvTiposexamenes());
     * newExamen.setCfgMetodos(efdto.getCfgMetodos());
     */
    if (efdto.getExamenesconjuntodetalles().length > 0) {
      // cec.setCecDescripcion("Exámenes conjuntos de "+newExamen.getCeAbreviado());
      CfgExamenesconjuntos cec = new CfgExamenesconjuntos();
      examenesConjuntosDAO.insertExamenesConjuntos(cec);
      // newExamen.setCfgExamenesconjuntos(cec);

      for (CfgExamenesconjuntodetalle cecd : efdto.getExamenesconjuntodetalles()) {
        CfgExamenesconjuntodetalleId ceid = cecd.getId();
        ceid.setCecdIdexamenesconjuntos(cec.getCecIdexamenesconjuntos());
        cecd.setCfgExamenesconjuntos(cec);
        examenesConjuntosDetalleDAO.insertExamenesConjuntosDetalle(cecd);
        
      
      }
      efdto.setCfgExamenesconjuntos(cec);

    }

    long idExamen = cfgExamenesDAO.insertExamenesNativo(efdto, efdto.getIdDerivador(), idUsuario);
    // long idExamen = newExamen.getCeIdexamen();

    // guardar test de examenes
    for (CfgExamenestest examenTest : efdto.getListTestsExamen()) {
      CfgExamenestestId id = new CfgExamenestestId();
      id.setCetIdexamen((int) idExamen);
      id.setCetIdtest(examenTest.getCfgTest().getCtIdtest());
      examenTest.setId(id);
      // examenTest.setCfgEnvases(new CfgEnvases(1));
      examenTest.setCetPosicion(Short.valueOf("1"));
      cfgExamenesTestDAO.insertExamenTest(examenTest);
     
    }

    // guardar grupo de muestras
    for (CfgGruposmuestrasexa gruposMuestras : efdto.getListGruposmuestrasexa()) {
      CfgGruposmuestrasexaId gmeId = gruposMuestras.getId();
      gmeId.setCgmeIdexamen(idExamen);
      gruposMuestras.setId(gmeId);
      cfgGruposMuestrasExaDAO.insertGruposMuestrasExa(gruposMuestras);
    }
    // guardar indicaciones tm de examen
    if (efdto.getListExamenesIndicacionesTM() != null) {
      for (CfgExamenesindicacionestm indicacionestm : efdto.getListExamenesIndicacionesTM()) {
        indicacionestm.getId().setCeitmIdexamen(idExamen);
        cfgExamenesIndicacionesTMDAO.insertCfgExamenesIndicaciones(indicacionestm);
      }
    }

    // guardar tabla referencias
    if (efdto.getTablaReferencias().length > 0) {
      CfgExamenestablasanexas ceta = efdto.getExamenestablasanexas();
      ceta.setCetaIdexamen(idExamen);
      tablasreferenciasexamenesDAO.insertTablaAnexa(ceta);

      //aqui se realiza insert de log tablas de los test del examen  **********************  
     
      
      LogCfgtablas lct = new LogCfgtablas();
      lct.setLctIdtabla( (int) ceta.getCetaIdexamen());
      lct.setCfgAccionesdatos(1);
      lct.setLctNombretabla("CFG_EXAMENESTABLASANEXAS");  
      lct.setLctNombrecampo("CETA_TITULOTABLA");
      lct.setLctValornuevo(ceta.getCetaTitulotabla());
      lct.setLctIdusuario(idUsuario.intValue());
      lct.setLctDescripcion(newExamen.getCeAbreviado());

      logCfgTablasDAO.insertLogTablas(lct); 
      
      //***********************************************************   
      
      
      for (CfgTablasreferenciasexamenes ctre : efdto.getTablaReferencias()) {
        CfgTablasreferenciasexamenesId ctreId = ctre.getId();
        ctreId.setCtreIdtablareferenciaexamen(ceta.getCetaIdexamentabla());
        ctre.setId(ctreId);
        tablasreferenciasexamenesDAO.insertTablaReferencia(ctre);
        
      }

      cfgExamenesDAO.updateTablaReferenciaExamen(idExamen, ceta.getCetaIdexamentabla());
      // newExamen.setCeIdtablareferenciaexamen(ceta.getCetaIdexamentabla());
      // cfgExamenesDAO.updateExamenes(newExamen);

    }

  }

  @Override
  public void updateExamenFull(ExamenFullDTO efdto, Long idUsuario ) throws BiosLISDAOException {
    CfgExamenes examenUpd = efdto.getCfgExamenes();
  
    
    
    /*
     * examenUpd.setCfgMuestras(efdto.getCfgMuestras());
     * examenUpd.setCfgSecciones(efdto.getCfgSecciones());
     * examenUpd.setLdvIndicacionesexamenes(efdto.getLdvIndicacionesexamenes());
     * examenUpd.setLdvFormatos(efdto.getLdvFormatos());
     * examenUpd.setLdvLoinc(efdto.getLdvLoinc());
     * examenUpd.setCfgGruposexamenesfonasa(efdto.getCfgGruposexamenesfonasa());
     * examenUpd.setLdvGruposexamenes(efdto.getLdvGruposexamenes());
     * examenUpd.setCfgPesquisas(efdto.getCfgPesquisas());
     * examenUpd.setLdvTiposexamenes(efdto.getLdvTiposexamenes());
     * examenUpd.setCfgExamenesconjuntos(efdto.getCfgExamenesconjuntos());
     */

   
//aqui se realiza log eventos tablas **********************************************
    ExamenFullDTO examenAnterior = new ExamenFullDTO();
    try {
    	examenAnterior = this.getExamenById(efdto.getCfgExamenes().getCeIdexamen());
		
		try {
			//logCfgTablasDAO.comparadorObjetos(examenAnterior, efdto,"", 1, 0, null, 0, null);
			logCfgTablasDAO.compararTablasExamenes(efdto, examenAnterior, idUsuario, efdto.getCfgExamenes().getCeAbreviado());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	} catch (BiosLISDAONotFoundException | BiosLISDAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}    
   //******************************************************************************* 
   
    cfgExamenesDAO.updateExamenesNativo(efdto, efdto.getIdDerivador());
    // Test
    for (CfgExamenestest examenTest : efdto.getListTestsExamen()) {
      if (!cfgExamenesTestDAO.existeTest((int) examenUpd.getCeIdexamen(), examenTest.getCfgTest().getCtIdtest())) {
        CfgExamenestestId id = new CfgExamenestestId();
        id.setCetIdexamen((int) examenUpd.getCeIdexamen());
        id.setCetIdtest(examenTest.getCfgTest().getCtIdtest());
        examenTest.setId(id);
        examenTest.setCfgEnvases(new CfgEnvases(1));
        examenTest.setCetPosicion(Short.valueOf("1"));
        cfgExamenesTestDAO.insertExamenTest(examenTest);
        
        //aqui se realiza insert de log tablas de los test del examen  **********************  
        CfgTest test = new CfgTest();
        try {
			 test = cfgTestDAO.getTestById(examenTest.getCfgTest().getCtIdtest());
		} catch (BiosLISDAONotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiosLISDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LogCfgtablas lct = new LogCfgtablas();
        lct.setLctIdtabla( (int) examenUpd.getCeIdexamen());
        lct.setCfgAccionesdatos(1);
        lct.setLctNombretabla("CFG_EXAMENES");  
        lct.setLctNombrecampo("CET_IDTEST");
        lct.setLctValornuevo(test.getCtAbreviado());
        lct.setLctIdusuario(idUsuario.intValue());
        lct.setLctDescripcion(examenUpd.getCeAbreviado());
   
   
        logCfgTablasDAO.insertLogTablas(lct);         
        //***********************************************************   
      }
    }

    // busca los test que han sido borrados
    List<CfgExamenestest> listExamenesTest = cfgExamenesTestDAO.getExamenTest((int) examenUpd.getCeIdexamen());
    for (CfgExamenestest cet : listExamenesTest) {
      boolean aunExisteTest = false;
      for (CfgExamenestest testDel : efdto.getListTestsExamen()) {
        if (Objects.equals(testDel.getCfgTest().getCtIdtest(), cet.getCfgTest().getCtIdtest())) {
          aunExisteTest = true;
          break;
        }
      }
      if (aunExisteTest == false) {
        cfgExamenesTestDAO.deleteExamenTest(cet);
        
        //aqui se realiza insert de log tablas de los test del examen eliminados  **********************  
        CfgTest test = new CfgTest();
        try {
			 test = cfgTestDAO.getTestById(cet.getId().getCetIdtest());
		} catch (BiosLISDAONotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiosLISDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LogCfgtablas lct = new LogCfgtablas();
        lct.setLctIdtabla( (int) examenUpd.getCeIdexamen());
        lct.setCfgAccionesdatos(3);
        lct.setLctNombretabla("CFG_EXAMENES");  
        lct.setLctNombrecampo("CET_IDTEST");
        lct.setLctValoranterior(test.getCtAbreviado());
        lct.setLctIdusuario(idUsuario.intValue());   
        lct.setLctDescripcion(examenUpd.getCeAbreviado());
        logCfgTablasDAO.insertLogTablas(lct);         
        //***********************************************************   
      }
    }

    // actualizar grupo de muestras
    for (CfgGruposmuestrasexa gruposMuestras : efdto.getListGruposmuestrasexa()) {
      CfgGruposmuestrasexa gmexa = cfgGruposMuestrasExaDAO.getGruposMuestrasExamen(examenUpd.getCeIdexamen(),
          gruposMuestras.getId().getCgmeIdtipomuestra());
      if (gmexa == null) {
        gmexa = new CfgGruposmuestrasexa();
        CfgGruposmuestrasexaId gmexaId = gruposMuestras.getId();
        gmexaId.setCgmeIdexamen(examenUpd.getCeIdexamen());
        // gmexaId.setCgmeIdtipomuestra(examenUpd.getCfgMuestras().getCmueIdtipomuestra());
        gmexa.setId(gmexaId);
        cfgGruposMuestrasExaDAO.insertGruposMuestrasExa(gmexa);
      }
    }

    List<CfgGruposmuestrasexa> listGruposMuestras = cfgGruposMuestrasExaDAO
        .getGruposMuestrasExamen(examenUpd.getCeIdexamen());
    for (CfgGruposmuestrasexa cgm : listGruposMuestras) {
      boolean aunExisteMuestra = false;
      for (CfgGruposmuestrasexa muestraDel : efdto.getListGruposmuestrasexa()) {
        if (muestraDel.getId().getCgmeIdtipomuestra() == cgm.getId().getCgmeIdtipomuestra()) {
          aunExisteMuestra = true;
          break;
        }
      }
      if (aunExisteMuestra == false) {
        cfgGruposMuestrasExaDAO.deleteGruposMuestrasExa(cgm);
      }
    }

    // guardar indicaciones tm de examen
    for (CfgExamenesindicacionestm indicacionestm : efdto.getListExamenesIndicacionesTM()) {
      if (!cfgExamenesIndicacionesTMDAO.existeIndicacionesTM(examenUpd.getCeIdexamen(),
          indicacionestm.getId().getCeitmIdindicaciontm())) {
        indicacionestm.getId().setCeitmIdexamen(examenUpd.getCeIdexamen());
        // indicacionestm.setCfgExamenes(examenUpd);
        cfgExamenesIndicacionesTMDAO.insertCfgExamenesIndicaciones(indicacionestm);
      }
    }

    List<CfgExamenesindicacionestm> listExamenesIndicacionesTM = cfgExamenesIndicacionesTMDAO
        .getIndicacionesTM(examenUpd.getCeIdexamen());
    for (CfgExamenesindicacionestm ceitm : listExamenesIndicacionesTM) {
      boolean aunExisteIndicacion = false;
      for (CfgExamenesindicacionestm indicacionDel : efdto.getListExamenesIndicacionesTM()) {
        if (indicacionDel.getId().getCeitmIdindicaciontm() == ceitm.getId().getCeitmIdindicaciontm()) {
          aunExisteIndicacion = true;
          break;
        }
      }
      if (aunExisteIndicacion == false) {
        cfgExamenesIndicacionesTMDAO.deleteCfgExamenesIndicaciones(ceitm);
      }
    }

    // guardar examenes conjuntos
    if (efdto.getExamenesconjuntodetalles().length > 0) {
      long idExamenesConjuntos = efdto.getExamenesconjuntodetalles()[0].getId().getCecdIdexamenesconjuntos();
      if (idExamenesConjuntos == 0) {
        CfgExamenesconjuntos cec = new CfgExamenesconjuntos();
        examenesConjuntosDAO.insertExamenesConjuntos(cec);
        idExamenesConjuntos = cec.getCecIdexamenesconjuntos();
        cfgExamenesDAO.updateIdExamenesConjuntos(examenUpd.getCeIdexamen(), idExamenesConjuntos);
        
        LogCfgtablas lct = new LogCfgtablas();
        lct.setLctIdtabla( (int) examenUpd.getCeIdexamen());
        lct.setCfgAccionesdatos(3);
        lct.setLctNombretabla("CFG_EXAMENESCONJUNTOS");  
        lct.setLctNombrecampo("CEC_CODIGOEXAMENESCONJUNTOS");
        lct.setLctValornuevo(String.valueOf(idExamenesConjuntos));
        lct.setLctIdusuario(idUsuario.intValue());   
        lct.setLctDescripcion(examenUpd.getCeAbreviado());
        logCfgTablasDAO.insertLogTablas(lct);         
        //***********************************************************  
 
        
      }
      for (CfgExamenesconjuntodetalle cecd : efdto.getExamenesconjuntodetalles()) {
        // idExamenesConjuntos = cecd.getId().getCecdIdexamenesconjuntos();
        // esto es en caso de que no se haya agregado examenes conjuntos anteriormente

        if (!examenesConjuntosDetalleDAO.existeExamenConjunto(cecd.getId().getCecdIdexamen(), idExamenesConjuntos)) {
          CfgExamenesconjuntodetalleId cecdId = cecd.getId();
          cecdId.setCecdIdexamen(cecd.getId().getCecdIdexamen());
          cecdId.setCecdIdexamenesconjuntos(idExamenesConjuntos);
          cecd.setId(cecdId);
          examenesConjuntosDetalleDAO.insertExamenesConjuntosDetalle(cecd);
        }
      }

      List<CfgExamenesconjuntodetalle> listExamenesConjuntos = examenesConjuntosDetalleDAO
          .getExamenesConjuntosById(idExamenesConjuntos);
      for (CfgExamenesconjuntodetalle ce : listExamenesConjuntos) {
        boolean aunExisteExamenconj = false;
        for (CfgExamenesconjuntodetalle exconjdel : efdto.getExamenesconjuntodetalles()) {
          if (exconjdel.getId().getCecdIdexamen() == ce.getId().getCecdIdexamen()) {
            aunExisteExamenconj = true;
            break;
          }
        }
        if (aunExisteExamenconj == false) {
          examenesConjuntosDetalleDAO.deleteExamenesConjuntosDetalle(ce);
        }
      }
    }else {    	
    	if(examenAnterior.getCfgExamenesconjuntos() != null) {
    	      List<CfgExamenesconjuntodetalle> listExamenesConjuntos = examenesConjuntosDetalleDAO
    	          .getExamenesConjuntosById(examenAnterior.getCfgExamenesconjuntos().getCecIdexamenesconjuntos());
    	      for (CfgExamenesconjuntodetalle ce : listExamenesConjuntos) {
    	    	  examenesConjuntosDetalleDAO.deleteExamenesConjuntosDetalle(ce);
    	      }
    	      
    	      //la tabla no deja agregar un cero en idexamenesconjuntos de tabla cgf_examenes comentado por cristian 11-05
    	    // cfgExamenesDAO.updateIdExamenesConjuntos(examenUpd.getCeIdexamen(), 0);
    	}
    	
    }

    boolean updateTablaAnexa = true;

    // guardartabla referencias
    if (efdto.getTablaReferencias().length > 0) {
      Short idTablaReferencia = efdto.getTablaReferencias()[0].getId().getCtreIdtablareferenciaexamen();
      if (idTablaReferencia == 0) {
        updateTablaAnexa = false;
        CfgExamenestablasanexas ceta = efdto.getExamenestablasanexas();      
        ceta.setCetaIdexamen(examenUpd.getCeIdexamen());
        tablasreferenciasexamenesDAO.insertTablaAnexa(ceta);
        idTablaReferencia = ceta.getCetaIdexamentabla();
        cfgExamenesDAO.updateTablaReferenciaExamen(examenUpd.getCeIdexamen(), idTablaReferencia);
       
        
        //aqui se realiza insert de log tablas de los tabla anexa cuando se confirma **********************  
	      
	        LogCfgtablas lct = new LogCfgtablas();
	        lct.setLctIdtabla( (int) examenUpd.getCeIdexamen());
	        lct.setCfgAccionesdatos(1);
	        lct.setLctNombretabla("CFG_EXAMENESTABLASANEXAS");  
	        lct.setLctNombrecampo("CETA_TITULOTABLA");
	        lct.setLctValornuevo(ceta.getCetaTitulotabla());
	        lct.setLctIdusuario(idUsuario.intValue());  
	        lct.setLctDescripcion(examenUpd.getCeAbreviado());
	        logCfgTablasDAO.insertLogTablas(lct);         
        //***********************************************************   
        
      }
      for (CfgTablasreferenciasexamenes ctr : efdto.getTablaReferencias()) {
        if (!tablasreferenciasexamenesDAO.existeCeldaTablaReferencia(idTablaReferencia, ctr.getId().getCtreCampo())) {
          CfgTablasreferenciasexamenes newCelda = new CfgTablasreferenciasexamenes();
          CfgTablasreferenciasexamenesId ctreId = new CfgTablasreferenciasexamenesId();
          ctreId.setCtreIdtablareferenciaexamen(idTablaReferencia);
          ctreId.setCtreCampo(ctr.getId().getCtreCampo());
          newCelda.setId(ctreId);
          newCelda.setCtreValor(ctr.getCtreValor());
          tablasreferenciasexamenesDAO.insertTablaReferencia(newCelda);
          // SE ENVIA A GUARDAR LOS DATOS EN LOG TABLA************
         
          logCfgTablasDAO.validarDatos(ctr.getId().getCtreCampo(), null, idUsuario, "CTRE_CAMPO",newCelda.getId().getCtreIdtablareferenciaexamen() ,"CFG_EXAMENESTABLASANEXAS",examenUpd.getCeAbreviado() );
          logCfgTablasDAO.validarDatos(ctr.getCtreValor(), null, idUsuario, "CTRE_VALOR",newCelda.getId().getCtreIdtablareferenciaexamen() ,"CFG_EXAMENESTABLASANEXAS" , examenUpd.getCeAbreviado() );
          //********************************************************************
         
        } else {
        	// SE TOMAN DATOS PARA AGREGAR A LOG TABLAS ************************
        	CfgTablasreferenciasexamenesId ctrIdAntiguo = new CfgTablasreferenciasexamenesId();
        	ctrIdAntiguo.setCtreIdtablareferenciaexamen(ctr.getId().getCtreIdtablareferenciaexamen());
        	ctrIdAntiguo.setCtreCampo( ctr.getId().getCtreCampo());        	
        	CfgTablasreferenciasexamenes ctrAntiguo = tablasreferenciasexamenesDAO.getCeldasTablaReferenciasByIdandCampo(ctrIdAntiguo);
        	//******************************************************************
          tablasreferenciasexamenesDAO.updateTablaReferencia(ctr);
          
          // SE ENVIA A GUARDAR LOS DATOS EN LOG TABLA************         
        logCfgTablasDAO.validarDatos(ctr.getCtreValor(), ctrAntiguo.getCtreValor(), idUsuario, "CTRE_VALOR",ctr.getId().getCtreIdtablareferenciaexamen() ,"CFG_EXAMENESTABLASANEXAS" , examenUpd.getCeAbreviado());
      //  *****************************************************
        }
      }
    } else {
      updateTablaAnexa = false;
    }

    if (updateTablaAnexa) {
    	// SE TOMAN DATOS PARA AGREGAR A LOG TABLAS ************************    
    	CfgExamenestablasanexas cetaAntiguo = new CfgExamenestablasanexas();
    	cetaAntiguo = tablasreferenciasexamenesDAO.getTablaAnexaById(efdto.getExamenestablasanexas().getCetaIdexamentabla());
    	//****************************************************************
    	
      CfgExamenestablasanexas ceta = efdto.getExamenestablasanexas();     
      ceta.setCetaIdexamen(examenUpd.getCeIdexamen());     
      tablasreferenciasexamenesDAO.updateTablaAnexa(ceta);
      
      // SE ENVIA A GUARDAR LOS DATOS EN LOG TABLA************  
      	logCfgTablasDAO.comparadorObjetos(cetaAntiguo, ceta, 2, idUsuario.intValue(),(int) examenUpd.getCeIdexamen() ,examenUpd.getCeAbreviado(), null);
    //***************************************************************
	
       
    }

  }

  public CfgExamenesConjuntosDAO getExamenesConjuntosDAO() {
    return examenesConjuntosDAO;
  }

  public void setExamenesConjuntosDAO(CfgExamenesConjuntosDAO examenesConjuntosDAO) {
    this.examenesConjuntosDAO = examenesConjuntosDAO;
  }

  public CfgExamenesConjuntosDetalleDAO getExamenesConjuntosDetalleDAO() {
    return examenesConjuntosDetalleDAO;
  }

  public void setExamenesConjuntosDetalleDAO(CfgExamenesConjuntosDetalleDAO examenesConjuntosDetalleDAO) {
    this.examenesConjuntosDetalleDAO = examenesConjuntosDetalleDAO;
  }

  public CfgTablasreferenciasexamenesDAO getTablasreferenciasexamenesDAO() {
    return tablasreferenciasexamenesDAO;
  }

  public void setTablasreferenciasexamenesDAO(CfgTablasreferenciasexamenesDAO tablasreferenciasexamenesDAO) {
    this.tablasreferenciasexamenesDAO = tablasreferenciasexamenesDAO;
  }

  @Override
  public List<CfgExamenes> getExamenByCodigo(String codigoExamen) throws BiosLISDAOException {
    return cfgExamenesDAO.getExamenByCodigo(codigoExamen);
  }

  @Override
  public void autorizar(List<ExamenOrdenDTO> listaExamenes, Long idUsuario) throws BiosLISDAOException {

    this.datosFichasExamenesDAO.autorizar(listaExamenes, idUsuario);
  }

  @Override

  public List<DatosFichasexamenestest> getResultadosDigitados(Integer nroOrden, Integer idExamen)
      throws BiosLISDAONotFoundException, BiosLISDAOException {

    return this.datosFichasExamenesTestDAO.getResultadosDigitados(nroOrden, idExamen);

  }

  @Override
  public List<CfgTest> getCalculatedTest(Integer idExamen) throws BiosLISDAOException, BiosLISDAONotFoundException {
    return this.cfgTestDAO.getCalculatedTest(idExamen);
  }

  @Override
  public List<CfgTest> getCalculatedTestDependsOn(Integer i) {
    // TODO Auto-generated method stub
    return null;
  }

  public CfgTestDAO getCfgTestDAO() {
    return cfgTestDAO;
  }

  public void setCfgTestDAO(CfgTestDAO cfgTestDAO) {
    this.cfgTestDAO = cfgTestDAO;
  }

  @Override
  public List<ExamenOrdenDTO> getListExamenesExcel() throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return cfgExamenesDAO.getExamenesExcel();
  }

@Override
public List<CfgExamenes> getExamenesBySeccion(int idSeccion) throws BiosLISDAOException {
	// TODO Auto-generated method stub
	return cfgExamenesDAO.getExamenesBySeccion(idSeccion);
}

}
