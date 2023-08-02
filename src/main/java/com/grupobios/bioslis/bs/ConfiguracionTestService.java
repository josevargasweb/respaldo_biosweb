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
import com.grupobios.bioslis.dto.AntecedentePacienteDTO;
import com.grupobios.bioslis.dto.CfgTestDTO;
import com.grupobios.bioslis.dto.CfgTestExcel;
import com.grupobios.bioslis.dto.GlosasTestDTO;
import com.grupobios.bioslis.dto.LaboratorioCentroDTO;
import com.grupobios.bioslis.dto.MetodosTestDTO;
import com.grupobios.bioslis.dto.SeccionLabDTO;
import com.grupobios.bioslis.dto.TestDTO;
import com.grupobios.bioslis.dto.TestExamenSeccionDto;
import com.grupobios.bioslis.dto.TestProcesoDTO;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgBacgrupostest;
import com.grupobios.bioslis.entity.CfgGruposexamenesfonasa;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.CfgTiposderesultado;
import com.grupobios.bioslis.entity.CfgUnidadesdemedida;
import com.grupobios.bioslis.entity.CfgValoresreferencia;

/**
 *
 * @author Marco Caracciolo
 */
public interface ConfiguracionTestService {
  // Reemplazados por getTestsFiltro()
  public TestDTO getTestById(int idTest) throws BiosLISDAONotFoundException, BiosLISDAOException;

  public List<LaboratorioCentroDTO> getLaboratoriosCentros() throws BiosLISDAOException;

  public List<SeccionLabDTO> getSeccionesLaboratorios(int idLab) throws BiosLISDAOException;

  public List<CfgTest> getTestsByCodigo(String codigo) throws BiosLISDAOException;

  public List<CfgAntecedentes> listAntecedentes() throws BiosLISDAOException;

  public List<CfgTiposderesultado> listTiposderesultado() throws BiosLISDAOException;

  public List<CfgUnidadesdemedida> listUnidadesMedida() throws BiosLISDAOException;

  public List<CfgBacgrupostest> listBacGruposTest() throws BiosLISDAOException;

  public List<CfgGruposexamenesfonasa> listGruposExamenesFonasa() throws BiosLISDAOException;

  public List<MetodosTestDTO> listaMetodosTest(int idTest) throws BiosLISDAOException;

  public void agregarTest(TestDTO testDTO, List<String> antecedentes, List<CfgValoresreferencia> listaVR, Long idUsuario)
      throws BiosLISDAOException;

  public void actualizarTest(TestDTO testDTO, List<String> antecedentes, List<CfgValoresreferencia> listaVR, Long idUsuario)
      throws BiosLISDAOException;

  public List<GlosasTestDTO> listaGlosasTest(int idTest, int idSeccion) throws BiosLISDAOException;

  public List<MetodosTestDTO> getMetodosTest(int idTest) throws BiosLISDAOException;

  public List<TestDTO> getTestDtoBySeccion(int idSeccion) throws BiosLISDAOException;

  public List<TestDTO> getTestDtoBySeccionExamen(int idSeccion, long idExamen) throws BiosLISDAOException;

  public List<CfgTest> getTestsFiltro(HashMap<String, String> filtros)
      throws BiosLISDAOException, BiosLISDAONotFoundException;

  public List<CfgTestExcel> getTestParaExcel() throws BiosLISDAOException, BiosLISDAONotFoundException;

  public List<TestProcesoDTO> getTestsBusquedaProcesos(int idSeccion, long idExamen) throws BiosLISDAOException;

  public List<TestExamenSeccionDto> getTestDtoBySeccionNativo(Integer idSeccion) throws BiosLISDAOException;
  
  public List<CfgTestDTO> getTestByExamen(long idExamen) throws BiosLISDAOException;
  
  public List<AntecedentePacienteDTO> getAntecedenteByExamen(long idExamen) throws BiosLISDAOException;

}
