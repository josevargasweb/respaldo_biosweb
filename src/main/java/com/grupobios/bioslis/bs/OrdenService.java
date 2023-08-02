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

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisExecutorException;
import com.grupobios.bioslis.common.RangoValorResultado;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.BiosLISNotFoundException;
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
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author eric.nicholls
 */
public interface OrdenService {

  List<FilaExamenesMuestrasDeUnaOrdenDTO> getMuestrasExamenesOrden(Long nroOrden) throws BiosLISDAOException;

  List<ExamenesResultadosDeUnaOrdenDTO> getResultadosExamenesOrden(Long nroOrden) throws BiosLISDAOException;

  void updateResultadosExamenesOrdenes(List<ExamenesResultadosDeUnaOrdenDTO> lista) throws BiosLISDAOException;

  List<ExamenesResultadosDeUnaOrdenDTO> getResultadosExamenesOrden(Long nroOrden,
      ParamResultadoExamenOrden prmResultadoExamenOrden)
      throws BiosLISException, BiosLISDAONotFoundException, BiosLISDAOException;

  RangoValorResultado updateResultadoTestExamenOrden(ResultadoNumericoTestExamenOrdenDTO resultado)
      throws BiosLISDAOException, ParseException, BiosLISDAONotFoundException, BiosLISNotFoundException;

  List<ExamenOrdenDTO> readyToSign(Long nroOrden) throws BiosLISDAOException;

  List<OrdenExamenCapturaResultadoDTO> getOrdenesxFiltro(BCFichaFiltroDTO bcFichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException;

  List<OrdenInformeResultadoDTO> getSoloOrdenesxFiltro(BCFichaFiltroDTO bcFichaFiltroDTO) throws BiosLISException;

  List<OrdenInformeResultadoDTO> getOrdenesConSeccionesCapturaResultados(List<Integer> lstOrdenes)
      throws BiosLISDAOException;

  List<OrdenExamenCapturaResultadoDTO> getOrdenesExamenesCapturaResultados(List<Integer> listaOrdenes)
      throws BiosLISDAOException;

  void doAction(String actionCode, List<Long> listaOrdenes, Long idUsuario) throws BiosLISDAOException,
      BiosLISJasperException, BiosLISException, BiosLISDAONotFoundException, BiosLisExecutorException, IOException;

  List<DatosFichasexamenes> getExamenes(Long nroOrden) throws BiosLISDAOException;

  boolean verificarMasDeUnaOrden(Integer idPac);

  List<DatosFichas> selectOrdenxRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, Integer idPac)
      throws BiosLISDAOException;

  List<OrdenExamenValidoDTO> getOrdenesConExamenesValidosDeUnPaciente(Integer idPac) throws BiosLISDAOException;

  String getOrdenesConExamenesValidosDeUnPaciente(DatosFichas med, Integer[] examenes) throws BiosLISDAOException;

  DatosFichas insertOrden(DatosFichasDTO medReq, String ipEquipo, Long idUsuario)
      throws BiosLISDAOException, BiosLISException, BiosLISDAONotFoundException;

  String getOrdenesConExamenesValidosDeUnPaciente(DatosFichas med, CfgExamenesDTO[] lstExamenesDto)
      throws BiosLISDAOException;

  List<OrdenInformeResultadoDTO> getBOOrdenesConSeccionesCapturaResultados(BOOrdenesFiltroDTO fichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException;

  List<OrdenInformeResultadoDTO> getOrdenesConSeccionesCapturaResultados(FichaFiltroDTO ffDto)
      throws BiosLISException, BiosLISDAOException;

  void addTestOpcional2Examen(Integer ordenId, Integer examenId, Integer testId, Long idUsuario)
      throws BiosLISDAOException, BiosLISDAONotFoundException;

  void addTestOpcionalList2Examen(Integer ordenId, Integer examenId, Integer[] testIdLst, Long idUsuario)
      throws BiosLISDAOException, BiosLISDAONotFoundException;

  ResultadoTestNotificacionDeUnaOrdenDTO getDatosTestNotificacion(Long nroOrden, Long examenId, Long testId)
      throws BiosLISDAOException;

  String insertDatosTestNotificacion(Long nroOrden, Long examenId, Long testId,
      ResultadoTestNotificacionDeUnaOrdenDTO notif, Long idUsuario, Integer opcionNotificacion)
      throws BiosLISDAOException, ParseException, BiosLISBSException;

  Boolean ValidarNumeroNotificaciones(Long nroOrden, Long examenId, Long testId, Long notificacionId)
      throws BiosLISDAOException, ParseException;

  DatosFichas insertOrden(DatosFichasDTO medReq, Integer[] examenes, String ipEquipo, Long idUsuario)
      throws BiosLISDAOException, NumberFormatException, BiosLISException, BiosLISDAONotFoundException;

  OrdenFullDTO getDatosOrdenFull(int nOrden) throws BiosLISDAOException;

  List<OrdenExamenCapturaResultadoDTO> getOrdenesExamenesSeccionesxFiltro(BCFichaFiltroDTO bcFichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException;

  List<OrdenInformeResultadoDTO> getBOOrdenesConSecciones(BCFichaFiltroDTO fichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException;

  void updateEstadoResultadoExamenesOrdenes(Long nroOrden, Long examenId, Long testId, String estado, Long idUsuario)
      throws BiosLISDAOException;

  void updateContadorFichaExamenTest(Long nroOrden, Long examenId, Long testId, String fechaResultado)
      throws BiosLISDAOException;

  void insertDatosContadorCelulas(CfgCellcounter cellCounter) throws BiosLISDAOException;

  CfgCellcounter getCellCounter() throws BiosLISDAOException;;

  void updateEstadoTransmitidoResultadoTestExamenOrden(ResultadoNumericoTestExamenOrdenDTO resultado)
      throws BiosLISDAOException, ParseException;

  List<Object> getDatosPacienteTest(Long idPaciente, Long idTest) throws BiosLISDAOException;

  List<DatosFichasexamenestest> getDatosFichasExamenesTestByOrden(long orden) throws BiosLISDAOException;

  Object datosExamenById(Long examen) throws BiosLISDAOException, BiosLISDAONotFoundException;

  public Object unidadMedidaById(int idMedida) throws BiosLISDAOException;

  List<Object> getDatosOrdenTestUnidad(Long nOrden) throws BiosLISDAOException;

  List<CfgTest> getTestOpcionalesExamenesOrden(Long nOrden, Long idExamen) throws BiosLISDAOException;

  RangoValorResultado resetResultadoTestExamenOrden(ResultadoNumericoTestExamenOrdenDTO resultado,
      DatosUsuarios usuario) throws BiosLISDAOException, ParseException;

  List<CfgTest> getTestAll() throws BiosLISDAOException;

  DatosLineaTiempoDTO getDatosLineaTiempo(Long nOrden, Long idExamen, Long idTest) throws BiosLISDAOException;

  public Boolean updatefichaExamenTest(DatosFichasDTO datosFichasExamenTest, Long idUsuario) throws BiosLISDAOException;

  public List<BiosLisEtiquetaDTO> getEtiquetasOrden(Long nOrden) throws BiosLISDAOException, BiosLISException;

  public List<BiosLisEtiquetaDTO> getEtiquetasOrdenYCodigosBarras(Long nOrden, String[] codigos)
      throws BiosLISDAOException, BiosLISException;

  public List<LogFichasExamenesTestDTO> getEventosFichasByOrder(Long orderId, String filtro, String idTest)
      throws BiosLISDAOException;

  public List<BigDecimal> getExamenByOrdenAndTest(Long orden, Long idTest) throws BiosLISDAOException;

  List<OrdenInformeResultadoDTO> getBOOrdenesConSecciones_Urgentes(BCFichaFiltroDTO fichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException;
  String getEstadoDocumento(Long nOrden) throws BiosLISDAOException;
  ObservacionCRDTO getObservacionesExamen(Long nOrden,Long idExamen) throws BiosLISDAOException;
  ExamenNotasDTO getNotasExamen(Long nOrden,Long idExamen) throws BiosLISDAOException;
  Boolean updateNotasExamen(HashMap<String, Object> notes) throws BiosLISDAOException;
  
}
