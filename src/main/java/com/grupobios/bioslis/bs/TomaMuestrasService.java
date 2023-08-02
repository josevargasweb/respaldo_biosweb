/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.util.HashMap;
import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ExamenOrdenMuestraDTO;
import com.grupobios.bioslis.dto.ExamenPacienteDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.dto.OrdenesTMDTO;
import com.grupobios.bioslis.dto.TMClickDTO;
import com.grupobios.bioslis.dto.TestCurvaDTO;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvFichasestadostm;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * 
 * @author marco caracciolo
 */
public interface TomaMuestrasService {
  public List<OrdenesTMDTO> getListaOrdenesTM(String fechaInicio, String fechaFin) throws BiosLISDAOException;

  public List<MuestrasDTO> getListaMuestrasTM(long nOrden) throws BiosLISDAOException, UnsupportedEncodingException, SQLException;

  public void updateTipoMuestra(MuestrasDTO muestraDTO) throws BiosLISDAOException;

  public OrdenesTMDTO getUltimoRegistro(long nOrden) throws BiosLISDAOException;

  public List<TestCurvaDTO> getListaTestCurva(long nOrden, long idExamen) throws BiosLISDAOException;

  public List<ExamenOrdenMuestraDTO> getExamenesOrdenMuestra(long nOrden) throws BiosLISDAOException;

  public TMClickDTO validarTmClick(Integer nOrden, Long idUsuario) throws BiosLISDAOException;

  public List<TMClickDTO> volverNormalidadOrdenAnterior(Integer nOrden, Long idUsuario) throws BiosLISDAOException;

  public void volverNormalidadOrden(Integer nOrden) throws BiosLISDAOException;

  public void volverNormalidadUsuario(Long idUsuario) throws BiosLISDAOException;

  public List<ExamenOrdenDTO> getExamenesMuestra(long idMuestra) throws BiosLISDAOException;

  public List<ExamenOrdenDTO> getExamenesMuestraSinIdMuestra(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra,Long idExamen) throws BiosLISDAOException;

  public List<CfgExamenes> getExamenesMuestraAntiguo(long idMuestra) throws BiosLISDAOException;

  public List<CfgExamenes> getExamenesMuestraSinIdMuestraAntiguo(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra) throws BiosLISDAOException;

  public OrdenesTMDTO tomarMuestraCurva(Long idMuestra, Long idUsuario) throws BiosLISDAOException;

  public OrdenesTMDTO tomarMuestraManualCurva(Long idMuestra, Long time, Long idUsuario) throws BiosLISDAOException;

  public ExamenPacienteDTO getCurvaTolerancia(long nOrden, long idExamen) throws BiosLISDAOException;

  public List<ExamenOrdenMuestraDTO> getDetalleMuestra(long idMuestra) throws BiosLISDAOException;

  public List<ExamenOrdenMuestraDTO> getDetalleMuestraSM(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra) throws BiosLISDAOException;

  public void cambiarTipoMuestraCM(Long idMuestra, Short idTipoMuestra, Long idUsuario) throws BiosLISDAOException;

  public void cambiarTipoMuestraSM(Long nOrden, Short idTipoMuestra, Short idEnvase, Short idDerivador,
      String comparteMuestra, Short newTipoMuestra, Long idUsuario) throws BiosLISDAOException;

  public void cambiarTipoMuestra(HashMap<String, String> datosMuestra) throws BiosLISDAOException;

  public void updateMuestra(DatosFichasmuestras dfm) throws BiosLISDAOException;
  
  public String updateMuestraZonaCuerpo(long idMuestra,long idCuerpo, Long idUsuario) throws BiosLISDAOException;

  public List<DatosUsuarios> getListaFlebotomistas() throws BiosLISDAOException;

  public OrdenesTMDTO cambioEstadoMuestra(long idMuestra, long idUsuario, String estado) throws BiosLISDAOException;

  public DatosFichasmuestras getMuestraByCodigoBarras(String codigoBarras) throws BiosLISDAOException;

  public DatosFichasmuestras getMuestraById(long idMuestra) throws BiosLISDAOException;

  public void guardarObservacion(DatosFichasmuestras dfm, String observacion, Long idUsuario) throws BiosLISDAOException;

  public OrdenesTMDTO confirmarTomaMuestra(DatosFichasmuestras muestra, long idUsuario) throws BiosLISDAOException;

  public List<MuestrasDTO> generarMuestra(List<MuestrasDTO> listaMuestras, Long idUsuario) throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException;

  public void cambiarEstadoUrgente(int nOrden, Long idUsuario) throws BiosLISDAOException;

  public List<OrdenesTMDTO> getListaOrdenesByOrden(int nOrden) throws BiosLISDAOException;

  public List<LdvFichasestadostm> getListaEstadosOrden() throws BiosLISDAOException;
  
  public OrdenesTMDTO actualizarEstadoOrden(long nOrden, int estado) throws BiosLISDAOException; 
  
  public String cambiarInstitucionOrden() throws BiosLISDAOException;
}
