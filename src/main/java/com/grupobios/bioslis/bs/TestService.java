/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisExamenSignerException;
import com.grupobios.bioslis.common.BiosLisExecutorException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.BiosLISNotFoundException;
import com.grupobios.bioslis.dto.CRDetalleTestDTO;
import com.grupobios.bioslis.dto.CfgCondicionesformulasDTO;
import com.grupobios.bioslis.dto.CfgTestDTO;
import com.grupobios.bioslis.dto.ExamenesResultadosDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.FiltroTestDTO;
import com.grupobios.bioslis.dto.HijosTest;
import com.grupobios.bioslis.dto.LogFichasExamenesTestDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.entity.CfgCondicionesformulas;
import com.grupobios.bioslis.entity.CfgGlosas;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasexamenestestId;
import com.grupobios.bioslis.entity.LogEventosfichas;

/**
 *
 * @author eric.nicholls
 */
public interface TestService {

  List<LogFichasExamenesTestDTO> getTestEvents(DatosFichasexamenestestId dfetId)
      throws BiosLISDAONotFoundException, BiosLISDAOException;

  DatosFichasexamenestest doAction(String actionCode, ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISBSException, BiosLISDAOException, BiosLISJasperException,
      BiosLISException, BiosLisExamenSignerException, BiosLisExecutorException, IOException;

  void doListAction(String actionCode, List<ResultadoNumericoTestExamenOrdenDTO> examenes, Long idUsuario)
      throws BiosLISBSException;

  List<CfgGlosas> getTestGM(Integer idTest) throws BiosLISDAOException, BiosLISDAONotFoundException;

  ExamenesResultadosDeUnaOrdenDTO getResultadoTest(Integer nroOrden, Integer idExamen, Integer idTest)
      throws BiosLISDAOException, BiosLISDAONotFoundException;

  List<CfgTest> getCalculatedTest() throws BiosLISDAOException, BiosLISDAONotFoundException;

  List<CfgTest> getFromFormula(String formula)
      throws BiosLISDAOException, BiosLISDAONotFoundException, BiosLISNotFoundException;

  List<CfgTest> getRelFormulaTest(Integer idTest)
      throws BiosLISDAOException, BiosLISDAONotFoundException, BiosLISNotFoundException;

  List<CfgTest> getDependentTest(Integer testId);

  HijosTest getRelFormulaSonTest(Integer idTest)
      throws BiosLISDAOException, BiosLISDAONotFoundException, BiosLISNotFoundException;

  Map<Integer, HijosTest> getRelFormulaTestDependsOn(Integer testId)
      throws BiosLISDAOException, BiosLISDAONotFoundException, BiosLISNotFoundException;

  void calcular(ResultadoNumericoTestExamenOrdenDTO resultado, HijosTest hijosTest,
      List<DatosFichasexamenestest> lstResultadosDigitadosExamen);

  CfgTest setFormula(Integer testId, String formula, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISDAOException, BiosLISException;

  CfgTest getCfgTest(Integer testId) throws BiosLISDAONotFoundException, BiosLISDAOException;

  List<CfgTestDTO> getTestByFiltroTest(FiltroTestDTO filtroTest) throws BiosLISDAOException;

  List<CfgTest> getTestNumericos() throws BiosLISDAOException;

  CfgTest saveCond(CfgCondicionesformulasDTO cfgCond, String condicion) throws BiosLISDAOException;

  public List<CfgCondicionesformulas> getCondicionesTest(Integer idTest) throws BiosLISDAOException;

  ResultadoNumericoTestExamenOrdenDTO aplicarCondicion(ResultadoNumericoTestExamenOrdenDTO resultado,
      CfgCondicionesformulas t) throws BiosLISDAOException, BiosLISException;

  List<String> getAntecedenteFromFormula(String formula);

  String validateFormula(String formula) throws BiosLisFormulaResultIsNaNException;
  
  List<CRDetalleTestDTO> getDetallesTest(int idOrden, int idExam, int idTest) throws BiosLISDAOException;

}
