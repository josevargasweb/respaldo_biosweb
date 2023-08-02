/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.grupobios.bioslis.common.ActionOrdenExecutor;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisExecutorException;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.OrdenExecutorFactory;
import com.grupobios.bioslis.common.RangoValorResultado;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.BiosLISNotFoundException;
import com.grupobios.bioslis.dao.CapturaResultadosDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgLocalizacionesDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesTestDAO;
import com.grupobios.bioslis.dao.DatosFichas_DAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.BOOrdenesFiltroDTO;
import com.grupobios.bioslis.dto.BiosLisEtiquetaDTO;
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.DatosFichasDTO;
import com.grupobios.bioslis.dto.DatosLineaTiempoDTO;
import com.grupobios.bioslis.dto.ExamenNotasDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ExamenesResultadosDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.FichaFiltroDTO;
import com.grupobios.bioslis.dto.FilaExamenesMuestrasDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.HijosTest;
import com.grupobios.bioslis.dto.LogFichasExamenesTestDTO;
import com.grupobios.bioslis.dto.ObservacionCRDTO;
import com.grupobios.bioslis.dto.OrdenExamenCapturaResultadoDTO;
import com.grupobios.bioslis.dto.OrdenExamenValidoDTO;
import com.grupobios.bioslis.dto.OrdenFullDTO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoTestNotificacionDeUnaOrdenDTO;
import com.grupobios.bioslis.entity.CfgCellcounter;
import com.grupobios.bioslis.entity.CfgCondicionesformulas;
import com.grupobios.bioslis.entity.CfgLocalizaciones;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.microbiologia.dao.BiosLISDaoException;
import com.grupobios.common.Edad;

/**
 *
 * @author eric.nicholls
 */

public class OrdenServiceImpl implements OrdenService {

  private static Logger logger = LogManager.getLogger(OrdenServiceImpl.class);

  @Autowired
  private CfgLocalizacionesDAO cfgLocalizacionesDAO;
  private CapturaResultadosDAO capturaResultadosDAO;
  private DatosFichas_DAO datosFichas_dao;
  private DatosFichasDAO datosFichasDAO;
  private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;
  private TestService testService;
  private ExamenService examenService;

  public void setDatosFichasExamenesTestDAO(DatosFichasExamenesTestDAO datosFichasExamenesTestDAO) {
    this.datosFichasExamenesTestDAO = datosFichasExamenesTestDAO;
  }

  @Autowired
  private CfgExamenesDAO cfgExamenesDAO;
  @Autowired
  private PacienteService pacienteService;

  @Autowired
  private OrdenExecutorFactory ordenExecutorFactory;

  private OrdenServiceImpl() throws BiosLISDaoException {

  }

  public CfgExamenesDAO getCfgExamenesDAO() {
    return cfgExamenesDAO;
  }

  @Override
  public List<FilaExamenesMuestrasDeUnaOrdenDTO> getMuestrasExamenesOrden(Long nroOrden) throws BiosLISDAOException {

    return capturaResultadosDAO.getFilasMuestrasExamenesDeUnaOrden(nroOrden);
  }

  public CapturaResultadosDAO getCapturaResultadosDAO() {
    return capturaResultadosDAO;
  }

  public DatosFichasExamenesTestDAO getDatosFichasExamenesTestDAO() {
    return datosFichasExamenesTestDAO;
  }

  public void setCapturaResultadosDAO(CapturaResultadosDAO capturaResultadosDAO) {
    this.capturaResultadosDAO = capturaResultadosDAO;
  }

  public DatosFichas_DAO getDatosFichas_dao() {
    return datosFichas_dao;
  }

  public void setDatosFichas_dao(DatosFichas_DAO datosFichas_dao) {
    this.datosFichas_dao = datosFichas_dao;
  }

  @Override
  public List<ExamenesResultadosDeUnaOrdenDTO> getResultadosExamenesOrden(Long nroOrden) throws BiosLISDAOException {
    return capturaResultadosDAO.getResultadosExamenesOrden(nroOrden);
  }

  @Override
  public void updateResultadosExamenesOrdenes(List<ExamenesResultadosDeUnaOrdenDTO> lista) throws BiosLISDAOException {
    capturaResultadosDAO.updateResultadosExamenesOrdenes(lista);
  }

  @Override
  public List<ExamenesResultadosDeUnaOrdenDTO> getResultadosExamenesOrden(Long nroOrden,
      ParamResultadoExamenOrden prmResultadoExamenOrden)
      throws BiosLISException, BiosLISDAONotFoundException, BiosLISDAOException {

    return capturaResultadosDAO.getResultadosExamenesOrden(nroOrden, prmResultadoExamenOrden);
  }

  @Override
  public List<OrdenInformeResultadoDTO> getOrdenesConSeccionesCapturaResultados(FichaFiltroDTO ffDto)
      throws BiosLISException, BiosLISDAOException {

    return datosFichas_dao.getOrdenesCapturaResultados(ffDto);
  }

  @Override
  public RangoValorResultado updateResultadoTestExamenOrden(ResultadoNumericoTestExamenOrdenDTO resultado)
      throws BiosLISDAOException, ParseException {

    RangoValorResultado rvr = capturaResultadosDAO.updateResultadoTestExamenOrden(resultado);
    List<CfgTest> lstCalculadosTest;
    Map<Integer, HijosTest> hmAfectadosTest;
    List<DatosFichasexamenestest> lstResultadosDigitadosExamen;
    try {

      // Obtener los resultados de este examen que están digitados.
      lstResultadosDigitadosExamen = examenService.getResultadosDigitados(resultado.getDF_NORDEN().intValueExact(),
          resultado.getDFE_IDEXAMEN().intValueExact());

      if (lstResultadosDigitadosExamen.isEmpty())
        return rvr;

      // Obtener los resultados de este examen que son fórmulas
      lstCalculadosTest = examenService.getCalculatedTest(resultado.getDFE_IDEXAMEN().intValueExact());
      if (lstCalculadosTest.isEmpty())
        return rvr;

      // Obtener los resultados de este examen que son fórmulas y cuyos cálculos son
      // afectados por este test.
      hmAfectadosTest = testService
          .getRelFormulaTestDependsOn(Integer.valueOf(resultado.getDFET_IDTEST().intValueExact()));
      if (hmAfectadosTest.size() == 0)
        return rvr;

      lstCalculadosTest.forEach(
          t -> testService.calcular(resultado, hmAfectadosTest.get(t.getCtIdtest()), lstResultadosDigitadosExamen));

      // Ver si hay condiciones asociadas al test.
      List<CfgCondicionesformulas> lstccf = testService.getCondicionesTest(resultado.getDFET_IDTEST().intValueExact());

      lstccf.forEach(t -> {
        try {
          testService.aplicarCondicion(resultado, t);
        } catch (BiosLISDAOException | BiosLISException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      });

    } catch (BiosLISDAOException | BiosLISDAONotFoundException | BiosLISNotFoundException e) {
      logger.error("No se pudo actualizar resultados relacionados {}", e.getMessage());
    }

    return rvr;

  }

  @Override
  public List<ExamenOrdenDTO> readyToSign(Long nroOrden) throws BiosLISDAOException {
    return datosFichas_dao.readyToSign(nroOrden);
  }

  @Override
  public List<OrdenExamenCapturaResultadoDTO> getOrdenesxFiltro(BCFichaFiltroDTO bcFichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException {
    return datosFichas_dao.getOrdenesxFiltro(bcFichaFiltroDTO);

  }

  @Override
  public List<OrdenExamenCapturaResultadoDTO> getOrdenesExamenesSeccionesxFiltro(BCFichaFiltroDTO bcFichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException {
    return datosFichas_dao.getOrdenesExamenesSeccionesxFiltro(bcFichaFiltroDTO);

  }

  // cambiando de ordenfullDto a OrdenInformeResultadoDTO
  @Override
  public List<OrdenInformeResultadoDTO> getSoloOrdenesxFiltro(BCFichaFiltroDTO bcFichaFiltroDTO)
      throws BiosLISException {
    return datosFichas_dao.getSoloOrdenesxFiltro(bcFichaFiltroDTO);
  }

  @Override
  public List<OrdenInformeResultadoDTO> getOrdenesConSeccionesCapturaResultados(List<Integer> lstOrdenes)
      throws BiosLISDAOException {
    return datosFichas_dao.getOrdenesCapturaResultados(lstOrdenes);
  }

  @Override
  public List<OrdenExamenCapturaResultadoDTO> getOrdenesExamenesCapturaResultados(List<Integer> listaOrdenes)
      throws BiosLISDAOException {
    return datosFichas_dao.getOrdenesExamenesCapturaResultados(listaOrdenes);
  }

  @Override
  public void doAction(String actionCode, List<Long> listaOrdenes, Long idUsuario) throws BiosLISDAOException,
      BiosLISJasperException, BiosLISException, BiosLISDAONotFoundException, BiosLisExecutorException, IOException {

    for (Long nroOrden : listaOrdenes) {
      this.doAction(actionCode, nroOrden, idUsuario);
    }

  }

  private void doAction(String actionCode, Long nroOrden, Long idUsuario)
      throws BiosLISDAOException, BiosLisExecutorException {

    ActionOrdenExecutor orderExecutor = ordenExecutorFactory.getInstance(actionCode);
    if (orderExecutor == null) {
      throw new BiosLisExecutorException("Executor no encontrado para: ".concat(actionCode));
    }
    orderExecutor.exec(nroOrden, idUsuario);
  }

  @Override
  public List<DatosFichasexamenes> getExamenes(Long nroOrden) throws BiosLISDAOException {
    return datosFichas_dao.getDatosFichasexamenesOrden(nroOrden);
  }

  public OrdenExecutorFactory getOrdenExecutorFactory() {
    return ordenExecutorFactory;
  }

  public void setOrdenExecutorFactory(OrdenExecutorFactory ordenExecutorFactory) {
    this.ordenExecutorFactory = ordenExecutorFactory;
  }

  @Override
  public DatosFichas insertOrden(DatosFichasDTO medReq, Integer[] examenes, String ipEquipo, Long idUsuario)
      throws BiosLISDAOException, NumberFormatException, BiosLISException, BiosLISDAONotFoundException {

    DatosFichas med = medReq.toDatosFicha();
    if (med.getCfgDiagnosticos() == 0) {
      med.setCfgDiagnosticos(1);
    }
    logger.debug("Procedencia {}", med.getCfgProcedencias());
    logger.debug("Servicio {}", med.getCfgServicios());

    if (med.getCfgServicios() == Constante.SIN_SERVICIO) {
      med.setCfgServicios(Constante.CFG_SERVICIOS_SIN_SERVICIO);
    }

    if (med.getCfgProcedencias() == Constante.SIN_PROCEDENCIA) {
      med.setDfCodigolocalizacion("");
      med.setCfgLocalizaciones(Constante.SIN_LOCALIZAR);
      med.setCfgProcedencias(Constante.CFG_PROCEDENCIAS_SIN_PROCEDENCIA);
    } else {
      Integer idSalas = medReq.getSalas() == null ? 0
          : (medReq.getSalas().equals("N") ? 0 : Integer.valueOf(medReq.getSalas()));
      Integer idCamas = medReq.getCamas() == null ? 0
          : (medReq.getCamas().equals("N") ? 0 : Integer.valueOf(medReq.getCamas()));
      Integer idServicios = medReq.getCfgServicios() == null ? 0
          : (medReq.getCfgServicios().equals("N") ? 0 : Integer.valueOf(medReq.getCfgServicios()));

      CfgLocalizaciones cl = cfgLocalizacionesDAO.getLocalizacion(idServicios, idSalas, idCamas);

      if (cl != null) {
        med.setDfCodigolocalizacion(cl.getClCodigolocalizacion());
        med.setCfgLocalizaciones(cl.getClIdlocalizacion());
      } else {
        med.setDfCodigolocalizacion("");
        med.setCfgLocalizaciones(Constante.SIN_LOCALIZAR);
      }
    }

    datosFichasDAO.insertOrden(med, examenes, ipEquipo, idUsuario);
    return med;

  }

  public CfgLocalizacionesDAO getCfgLocalizacionesDAO() {
    return cfgLocalizacionesDAO;
  }

  public void setCfgLocalizacionesDAO(CfgLocalizacionesDAO cfgLocalizacionesDAO) {
    this.cfgLocalizacionesDAO = cfgLocalizacionesDAO;
  }

  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  @Override
  public List<DatosFichas> selectOrdenxRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, Integer idPac)
      throws BiosLISDAOException {
    return datosFichasDAO.selectOrdenxRangoFechas(fechaInicio, fechaFin, idPac);
  }

  @Override
  public boolean verificarMasDeUnaOrden(Integer idPac) {
    return false;
  }

  @Override
  public List<OrdenExamenValidoDTO> getOrdenesConExamenesValidosDeUnPaciente(Integer idPac) throws BiosLISDAOException {
    return pacienteService.getOrdenesConExamenesValidosDeUnPaciente(idPac);
  }

  @Override
  public String getOrdenesConExamenesValidosDeUnPaciente(DatosFichas med, Integer[] examenes)
      throws BiosLISDAOException {

    Boolean hayExamenesValidos = false;
    List<OrdenExamenValidoDTO> lstOrdenes = this.getOrdenesConExamenesValidosDeUnPaciente(med.getDatosPacientes());
    String mensaje = "Orden ".concat(Integer.toString(med.getDfNorden()));
    StringBuilder sb = new StringBuilder(mensaje);
    Long currentOrden = -1L;

    if (!lstOrdenes.isEmpty()) {
      hayExamenesValidos = true;
      sb.append(" tiene validos los examenes siguientes: ");
      for (OrdenExamenValidoDTO ordenExamenValidoDTO : lstOrdenes) {
        if (!currentOrden.equals(ordenExamenValidoDTO.getDFE_NORDEN())) {
          currentOrden = ordenExamenValidoDTO.getDFE_NORDEN();
          sb.append("Orden:").append(currentOrden).append("\n");
        }
        for (Integer idTest : examenes) {
          if (ordenExamenValidoDTO.getCE_IDEXAMEN().equals(idTest)) {
            sb.append(ordenExamenValidoDTO.getCE_ABREVIADO()).append("\n");
          }
        }
      }
    }
    if (hayExamenesValidos) {
      return sb.toString();
    } else {
      return "";
    }
  }

  public PacienteService getPacienteService() {
    return pacienteService;
  }

  public void setPacienteService(PacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  @Override
  public DatosFichas insertOrden(DatosFichasDTO medReq, String ipEquipo, Long idUsuario)
      throws BiosLISDAOException, BiosLISException, BiosLISDAONotFoundException {

    logger.debug("Servicio {}", medReq.getCfgServicios());
    DatosFichas med = medReq.toDatosFicha();

    if (med.getCfgDiagnosticos() == 0) {
      med.setCfgDiagnosticos(Constante.CD_IDDIAGNOSTICO_SIN_ESPECIFICAR);
    }

    logger.debug("Procedencia {}", med.getCfgProcedencias());

    if (med.getCfgServicios() == Constante.SIN_SERVICIO) {
      med.setCfgServicios(Constante.CFG_SERVICIOS_SIN_SERVICIO);
    }
    logger.debug("Servicio {}", med.getCfgServicios());

    if (med.getCfgProcedencias() == Constante.SIN_PROCEDENCIA) {
      CfgLocalizaciones clo = cfgLocalizacionesDAO.getLocalizacion(0, null, null);
      med.setDfCodigolocalizacion(clo.getClCodigolocalizacion());
      med.setCfgLocalizaciones(Constante.SIN_LOCALIZAR);
      med.setCfgProcedencias(Constante.CFG_PROCEDENCIAS_SIN_PROCEDENCIA);
    } else {
      Integer idSalas = (medReq.getSalas() == null || medReq.getSalas() == "-1") ? 0
          : (medReq.getSalas().equals("N") ? 0 : Integer.valueOf(medReq.getSalas()));
      Integer idCamas = medReq.getCamas() == null ? 0
          : (medReq.getCamas().equals("N") ? 0 : Integer.valueOf(medReq.getCamas()));
      Integer idServicios = (medReq.getCfgServicios() == null ? 0 : medReq.getCfgServicios());

      CfgLocalizaciones cl = cfgLocalizacionesDAO.getLocalizacion(idServicios, idSalas, idCamas);

      if (cl != null) {
        med.setDfCodigolocalizacion(cl.getClCodigolocalizacion());
        med.setCfgLocalizaciones(cl.getClIdlocalizacion());
      } else {
        med.setDfCodigolocalizacion("SIN LOCALIZAR");
        med.setCfgLocalizaciones(Constante.SIN_LOCALIZAR);
      }
    }

    datosFichasDAO.insertOrden(med, medReq.getLstExamenesDto(), ipEquipo, idUsuario);
    return med;
  }

  @Override
  public String getOrdenesConExamenesValidosDeUnPaciente(DatosFichas med, CfgExamenesDTO[] lstExamenesDto)
      throws BiosLISDAOException {

    int n = lstExamenesDto.length;
    Integer[] lstIdeExamen = new Integer[n];

    for (int i = 0; i < n; i++) {
      lstIdeExamen[i] = Integer.valueOf(Long.valueOf(lstExamenesDto[i].getCeIdexamen()).toString());
    }

    return this.getOrdenesConExamenesValidosDeUnPaciente(med, lstIdeExamen);
  }

  @Override
  public List<OrdenInformeResultadoDTO> getBOOrdenesConSeccionesCapturaResultados(BOOrdenesFiltroDTO fichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException {

    FichaFiltroDTO ffDto = new FichaFiltroDTO();

    ffDto.setApellido(fichaFiltroDTO.getBo_apellido());
    ffDto.setfFin(fichaFiltroDTO.getBo_fFin());
    ffDto.setfIni(fichaFiltroDTO.getBo_fIni());
    ffDto.setLocalizacion(fichaFiltroDTO.getBo_localizacion());
    ffDto.setNombre(fichaFiltroDTO.getBo_nombre());
    ffDto.setnOrden(fichaFiltroDTO.getBo_nOrdenIni());
    ffDto.setNroDocumento(fichaFiltroDTO.getBo_nroDocumento());
    ffDto.setTipoAtencion(fichaFiltroDTO.getBo_tipoAtencion());
    ffDto.setTipoDocumento(fichaFiltroDTO.getBo_tipoDocumento());
    // ffDto.set(fichaFiltroDTO.getBo_nOrdenFin());

    return datosFichas_dao.getOrdenesCapturaResultados(ffDto);

  }

  @Override
  public List<OrdenInformeResultadoDTO> getBOOrdenesConSecciones(BCFichaFiltroDTO fichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException {

    return datosFichas_dao.getBOOrdenesConSecciones(fichaFiltroDTO);

  }

  @Override
  public void addTestOpcional2Examen(Integer ordenId, Integer examenId, Integer testId, Long idUsuario)
      throws BiosLISDAOException, BiosLISDAONotFoundException {
    datosFichasDAO.addTestOpcional2Examen(ordenId, examenId, testId, idUsuario);
  }

  @Override
  public void addTestOpcionalList2Examen(Integer ordenId, Integer examenId, Integer[] testIdLst, Long idUsuario)
      throws BiosLISDAOException, BiosLISDAONotFoundException {
    for (Integer testId : testIdLst) {
      datosFichasDAO.addTestOpcional2Examen(ordenId, examenId, testId, idUsuario);
    }

  }

  @Override
  public ResultadoTestNotificacionDeUnaOrdenDTO getDatosTestNotificacion(Long nroOrden, Long examenId, Long testId)
      throws BiosLISDAOException {
    return capturaResultadosDAO.getDatosTestNotificacion(nroOrden, examenId, testId);
  }

  @Override
  public String insertDatosTestNotificacion(Long nroOrden, Long examenId, Long testId,
      ResultadoTestNotificacionDeUnaOrdenDTO notif, Long idUsuario, Integer opcionNotificacion)
      throws BiosLISDAOException, ParseException, BiosLISBSException {

    // Obtener los otros test involucrados

    if (opcionNotificacion.equals(Constante.NOTIFICACION_TODOS)) {
      return capturaResultadosDAO.insertDatosFichasNotificacionOrdenTestRelacionados(nroOrden, examenId, testId, notif,
          idUsuario);
    } else if (opcionNotificacion.equals(Constante.NOTIFICACION_EXAMEN)) {
      return capturaResultadosDAO.insertDatosFichasNotificacionExamenTestRelacionados(nroOrden, examenId, testId, notif,
          idUsuario);
    } else if (opcionNotificacion == Constante.NOTIFICACION_UNICA) {
      return capturaResultadosDAO.insertDatosFichasNotificacionTest(nroOrden, examenId, testId, notif, idUsuario);
    } else {
      throw new BiosLISBSException(null);
    }
  }

//  @Override
//  public String anulaoYerraNotificacion(ResultadoTestNotificacionDeUnaOrdenDTO notif) {
//    return capturaResultadosDAO.anulaoYerraNotificacion(notif);
//
//  }

  @Override
  public Boolean ValidarNumeroNotificaciones(Long nroOrden, Long examenId, Long testId, Long notificacionId)
      throws BiosLISDAOException, ParseException {
    return capturaResultadosDAO.ValidarNumeroNotificaciones(nroOrden, examenId, testId, notificacionId);
  }

  @Override
  public OrdenFullDTO getDatosOrdenFull(int nOrden) throws BiosLISDAOException {
    return datosFichas_dao.getDatosOrdenFull(nOrden);
  }

  @Override
  public void updateEstadoResultadoExamenesOrdenes(Long nroOrden, Long examenId, Long testId, String estado,
      Long idUsuario) throws BiosLISDAOException {
    this.capturaResultadosDAO.updateEstadoResultadoExamenesOrdenes(nroOrden, examenId, testId, estado, idUsuario);

  }

  // CREADO POR CRISTIAN 05-09-2022 PRUEBA ACTUALIZAR FECHA RESULTADO TEST
  @Override
  public void updateContadorFichaExamenTest(Long nroOrden, Long examenId, Long testId, String fechaResultado)
      throws BiosLISDAOException {
    this.capturaResultadosDAO.updateContadorFichaExamenTest(nroOrden, examenId, testId, fechaResultado);

  }

  // CREADO POR CRISTIAN 05-09-2022 GRABAR DATOS CONTADOR CELULAS
  @Override
  public void insertDatosContadorCelulas(CfgCellcounter cellCounter) throws BiosLISDAOException {
    this.capturaResultadosDAO.insertDatosContadorCelulas(cellCounter);

  }

  // CREADO POR CRISTIAN DEVUELVE DATOS CELLCOUNTER
  @Override
  public CfgCellcounter getCellCounter() throws BiosLISDAOException {
    return this.capturaResultadosDAO.getCellCounter();
  }

  @Override
  public void updateEstadoTransmitidoResultadoTestExamenOrden(ResultadoNumericoTestExamenOrdenDTO resultado)
      throws BiosLISDAOException, ParseException {
    this.capturaResultadosDAO.updateEstadoTransmitidoResultadoTestExamenOrden(resultado);

  }

  @Override
  public List<Object> getDatosPacienteTest(Long idPaciente, Long idTest) throws BiosLISDAOException {
    List<Object> result = this.capturaResultadosDAO.getPacienteTest(idPaciente, idTest);
    return result;
  }

  @Override
  public List<DatosFichasexamenestest> getDatosFichasExamenesTestByOrden(long orden) throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return datosFichasExamenesTestDAO.getDatosFichasExamenesTestByOrden(orden);
  }

  @Override
  public Object datosExamenById(Long examen) throws BiosLISDAOException, BiosLISDAONotFoundException {
    Object resultado = this.capturaResultadosDAO.datosExamenById(examen);
    return resultado;
  }

  @Override
  public Object unidadMedidaById(int idMedida) throws BiosLISDAOException {

    return this.capturaResultadosDAO.unidadMedidaById(idMedida);
  }

  @Override
  public List<Object> getDatosOrdenTestUnidad(Long nOrden) throws BiosLISDAOException {
    return this.capturaResultadosDAO.getDatosOrdenTestUnidad(nOrden);
  }

  @Override
  public List<CfgTest> getTestAll() throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return this.capturaResultadosDAO.getTestAll();
  }

  // *************************************************************************
  @Override
  public List<CfgTest> getTestOpcionalesExamenesOrden(Long nOrden, Long idExamen) throws BiosLISDAOException {
    return this.datosFichasDAO.getTestOpcionalesExamenesOrden(nOrden, idExamen);
  }

  @Override
  public RangoValorResultado resetResultadoTestExamenOrden(ResultadoNumericoTestExamenOrdenDTO resultado,
      DatosUsuarios usuario) throws BiosLISDAOException, ParseException {
    return this.capturaResultadosDAO.resetResultadoTestExamenOrden(resultado, usuario);
  }

  // creado por cristian 12-10
  @Override
  public DatosLineaTiempoDTO getDatosLineaTiempo(Long nOrden, Long idExamen, Long idTest) throws BiosLISDAOException {
    return this.capturaResultadosDAO.getDatosLineaTiempo(nOrden, idExamen, idTest);
  }

  // *****creado por cristian 18-10 edicion de orden ** aca segun servicio, cama ,
  // sala se consige la localizacion -- id y codigo localizacion
  public Boolean updatefichaExamenTest(DatosFichasDTO datosFichasExamenTest, Long idUsuario)
      throws BiosLISDAOException {
    Integer idSalas = datosFichasExamenTest.getSalas() == null ? 0
        : (datosFichasExamenTest.getSalas().equals("N") ? 0 : Integer.valueOf(datosFichasExamenTest.getSalas()));
    Integer idCamas = datosFichasExamenTest.getCamas() == null ? 0
        : (datosFichasExamenTest.getCamas().equals("N") ? 0 : Integer.valueOf(datosFichasExamenTest.getCamas()));
    Integer idServicios = (datosFichasExamenTest.getCfgServicios() == null ? 0
        : datosFichasExamenTest.getCfgServicios());

    CfgLocalizaciones cl = cfgLocalizacionesDAO.getLocalizacion(idServicios, idSalas, idCamas);
    datosFichasExamenTest.setCfgLocalizaciones(cl.getClIdlocalizacion());
    datosFichasExamenTest.setDfCodigolocalizacion(cl.getClCodigolocalizacion());

    return this.datosFichasExamenesTestDAO.updateFichasExamenesTest(datosFichasExamenTest, idUsuario);
  }

  public TestService getTestService() {
    return testService;
  }

  public void setTestService(TestService testService) {
    this.testService = testService;
  }

  public ExamenService getExamenService() {
    return examenService;
  }

  public void setExamenService(ExamenService examenService) {
    this.examenService = examenService;
  }

  @Override
  public List<LogFichasExamenesTestDTO> getEventosFichasByOrder(Long orderId, String filtro, String idTest)
      throws BiosLISDAOException {
    LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();

    return lefDAO.getEventosFichasByOrder(orderId, filtro, idTest);
  }

  @Override
  public List<BiosLisEtiquetaDTO> getEtiquetasOrden(Long nOrden) throws BiosLISDAOException, BiosLISException {

    List<BiosLisEtiquetaDTO> lst = datosFichasDAO.getEtiquetasOrden(nOrden);

    for (BiosLisEtiquetaDTO biosLisEtiquetaDTO : lst) {
      biosLisEtiquetaDTO.setETIQUETAEDAD_(Edad.getEdadActual(biosLisEtiquetaDTO.getETIQUETAFECHA_()).toString());
    }
    return lst;
  }

  @Override
  public List<BiosLisEtiquetaDTO> getEtiquetasOrdenYCodigosBarras(Long nOrden, String[] codigos)
      throws BiosLISDAOException, BiosLISException {
    List<BiosLisEtiquetaDTO> lst = datosFichasDAO.getEtiquetasOrdenYCodigosBarras(nOrden, codigos);
    for (BiosLisEtiquetaDTO biosLisEtiquetaDTO : lst) {
      biosLisEtiquetaDTO.setETIQUETAEDAD_(Edad.getEdadActual(biosLisEtiquetaDTO.getETIQUETAFECHA_()).toString());
    }
    return lst;
  }

  @Override
  public List<BigDecimal> getExamenByOrdenAndTest(Long orden, Long idTest) throws BiosLISDAOException {
    return datosFichasExamenesTestDAO.getExamenByOrdenAndTest(orden, idTest);
  }

  @Override
  public List<OrdenInformeResultadoDTO> getBOOrdenesConSecciones_Urgentes(BCFichaFiltroDTO fichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException {

    // Sexo,TipoAtencion,Procedencia
    return datosFichas_dao.getBOOrdenesConSecciones_Urgentes(fichaFiltroDTO);

  }

@Override
public String getEstadoDocumento(Long nOrden) throws BiosLISDAOException {
	// TODO Auto-generated method stub
	return capturaResultadosDAO.getEstadoDocumento(nOrden);
}

@Override
public ObservacionCRDTO getObservacionesExamen(Long nOrden, Long idExamen) throws BiosLISDAOException {
	// TODO Auto-generated method stub
	return capturaResultadosDAO.getObsExamenes(nOrden, idExamen);
}

@Override
public Boolean updateNotasExamen(HashMap<String, Object> notes) throws BiosLISDAOException {
	// TODO Auto-generated method stub
	return capturaResultadosDAO.updateNotasExamen(notes);
}

@Override
public ExamenNotasDTO getNotasExamen(Long nOrden, Long idExamen) throws BiosLISDAOException {
	// TODO Auto-generated method stub
	return capturaResultadosDAO.getNotasExamen(nOrden, idExamen);
}

}
