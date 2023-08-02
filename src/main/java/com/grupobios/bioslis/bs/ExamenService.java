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
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.ExamenFullDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ExamenPacienteDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.dto.TestRepetidosDTO;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenesId;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;

/**
 *
 * @author eric.nicholls
 */
public interface ExamenService {

  void updateExamen(DatosFichasexamenes dfe) throws BiosLISDAOException;

  public List<DatosFichasexamenes> getAll() throws BiosLISDAOException;

  public Boolean readyToSign(Long idExamen, Long nOrden) throws BiosLISDAOException;

  public Boolean readyToSign(Long nOrden) throws BiosLISDAOException;

  void sign(ResultadoNumericoTestExamenOrdenDTO dfetId) throws BiosLISDAONotFoundException, BiosLISDAOException;

  boolean readyToSign(DatosFichasexamenesId iddatosFichasExamenes);

  void sign(Long nroOrden);

  void sign(DatosFichasexamenesId iddatosFichasExamenes, Long idUsuario);

  void unsign(DatosFichasexamenesId iddatosFichasExamenes);

  public ExamenPacienteDTO getExamenesPaciente(long nOrden, long idMuestra) throws BiosLISDAOException;

  List<TestRepetidosDTO> getTestRepetidosExamenes() throws BiosLISDAOException;

  List<TestRepetidosDTO> checkTestRepetidosExamenes(Integer[] testId) throws BiosLISDAOException;

  String getNombresTestRepetidosExamenes(Integer[] testIdArray) throws BiosLISDAOException;

  List<TestRepetidosDTO> getTestRepetidosExamenes(Integer[] lstExamenes) throws BiosLISDAOException;

  String getNombresTestRepetidosExamenes(CfgExamenesDTO[] lstExamenesDto) throws BiosLISDAOException;

  List<CfgTest> getTestOpcionalesExamenes(Long idExamen) throws BiosLISDAOException;

  // métodos para Módulo de exámenes por Marco Caracciolo 21/09/22
  ExamenFullDTO getExamenById(long idExamen) throws BiosLISDAOException, BiosLISDAONotFoundException;

  List<CfgExamenes> getListaExamanesFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
  List<ExamenOrdenDTO> getListExamenesExcel() throws BiosLISDAOException;

  List<LdvIndicacionesexamenes> getIndicacionesExamenes() throws BiosLISDAOException;

  void insertExamen(ExamenFullDTO efdto, Long idUsuario) throws BiosLISDAOException;

  void updateExamenFull(ExamenFullDTO efdto, Long idUsuario) throws BiosLISDAOException;

  List<CfgExamenes> getExamenByCodigo(String codigoExamen) throws BiosLISDAOException;

  List<DatosFichasexamenes> doAction(String actionCode, List<ExamenOrdenDTO> listaExamenes, Long idUsuario)
      throws BiosLISBSException, BiosLISDAONotFoundException, BiosLISDAOException;

  void sign(List<ExamenOrdenDTO> listaExamenes, Long idUsuario);

  void sign(ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISDAOException;

  void autorizar(List<ExamenOrdenDTO> listaExamenes, Long idUsuario) throws BiosLISDAOException;

  List<DatosFichasexamenestest> getResultadosDigitados(Integer nroOrden, Integer idExamen)
      throws BiosLISDAONotFoundException, BiosLISDAOException;

  List<CfgTest> getCalculatedTest(Integer i) throws BiosLISDAOException, BiosLISDAONotFoundException;

  List<CfgTest> getCalculatedTestDependsOn(Integer i);
  
  List<CfgExamenes> getExamenesBySeccion(int idSeccion) throws BiosLISDAOException;

}
