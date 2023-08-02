/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.CfgAntecedentesDAO;
import com.grupobios.bioslis.dao.CfgAntecedentestestDAO;
import com.grupobios.bioslis.dao.CfgBacgrupostestDAO;
import com.grupobios.bioslis.dao.CfgEnvasesDAO;
import com.grupobios.bioslis.dao.CfgExamenesTestDAO;
import com.grupobios.bioslis.dao.CfgGlosasDAO;
import com.grupobios.bioslis.dao.CfgGlosastestDAO;
import com.grupobios.bioslis.dao.CfgGruposExamenesFonasaDAO;
import com.grupobios.bioslis.dao.CfgLaboratoriosDAO;
import com.grupobios.bioslis.dao.CfgMetodosDAO;
import com.grupobios.bioslis.dao.CfgMetodosTestDAO;
import com.grupobios.bioslis.dao.CfgMuestrasDAO;
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.dao.CfgTestDAO;
import com.grupobios.bioslis.dao.CfgTiposderesultadoDAO;
import com.grupobios.bioslis.dao.CfgUnidadesdemedidaDAO;
import com.grupobios.bioslis.dao.CfgValoresreferenciaDAO;
import com.grupobios.bioslis.dao.LogCfgTablasDAO;
import com.grupobios.bioslis.dto.AntecedentePacienteDTO;
import com.grupobios.bioslis.dto.CfgTestDTO;
import com.grupobios.bioslis.dto.CfgTestExcel;
import com.grupobios.bioslis.dto.ExamenThinDTO;
import com.grupobios.bioslis.dto.GlosasTestDTO;
import com.grupobios.bioslis.dto.LaboratorioCentroDTO;
import com.grupobios.bioslis.dto.MetodosTestDTO;
import com.grupobios.bioslis.dto.SeccionLabDTO;
import com.grupobios.bioslis.dto.TestDTO;
import com.grupobios.bioslis.dto.TestExamenSeccionDto;
import com.grupobios.bioslis.dto.TestProcesoDTO;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgAntecedentestest;
import com.grupobios.bioslis.entity.CfgAntecedentestestId;
import com.grupobios.bioslis.entity.CfgBacgrupostest;
import com.grupobios.bioslis.entity.CfgExamenestest;
import com.grupobios.bioslis.entity.CfgGlosas;
import com.grupobios.bioslis.entity.CfgGlosastest;
import com.grupobios.bioslis.entity.CfgGruposexamenesfonasa;
import com.grupobios.bioslis.entity.CfgMetodos;
import com.grupobios.bioslis.entity.CfgMetodostest;
import com.grupobios.bioslis.entity.CfgMetodostestId;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.CfgTiposderesultado;
import com.grupobios.bioslis.entity.CfgUnidadesdemedida;
import com.grupobios.bioslis.entity.CfgValoresreferencia;
import com.grupobios.bioslis.entity.LogCfgtablas;

/**
 *
 * @author Marco Caracciolo
 */
@Service
public class ConfiguracionTestServiceImpl implements ConfiguracionTestService {

  CfgTestDAO cfgTestDAO;
  CfgExamenesTestDAO cfgExamenesTestDAO;
  CfgMuestrasDAO cfgMuestrasDAO;
  CfgSeccionesDAO cfgSeccionesDAO;
  CfgEnvasesDAO cfgEnvasesDAO;
  CfgLaboratoriosDAO cfgLaboratoriosDAO;
  CfgTiposderesultadoDAO cfgTiposderesultadoDAO;
  CfgBacgrupostestDAO cfgBacgrupostestDAO;
  CfgAntecedentesDAO cfgaDAO;
  CfgAntecedentestestDAO cfgAntecedentestestDAO;
  CfgUnidadesdemedidaDAO cfgUnidadesdemedidaDAO;
  CfgGruposExamenesFonasaDAO gruposExamenesFonasaDAO;
  CfgMetodosDAO cfgMetodosDAO;
  CfgMetodosTestDAO cfgMetodosTestDAO;
  CfgGlosasDAO cfgGlosasDAO;
  CfgGlosastestDAO cfgGlosastestDAO;
  CfgValoresreferenciaDAO cvrDAO;
  LogCfgTablasDAO logCfgTablasDAO;

  
  
 
public LogCfgTablasDAO getLogCfgTablasDAO() {
	return logCfgTablasDAO;
}

public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
	this.logCfgTablasDAO = logCfgTablasDAO;
}

public CfgTestDAO getCfgTestDAO() {
    return cfgTestDAO;
  }

  public void setCfgTestDAO(CfgTestDAO cfgTestDAO) {
    this.cfgTestDAO = cfgTestDAO;
  }

  public CfgExamenesTestDAO getCfgExamenesTestDAO() {
    return cfgExamenesTestDAO;
  }

  public void setCfgExamenesTestDAO(CfgExamenesTestDAO cfgExamenesTestDAO) {
    this.cfgExamenesTestDAO = cfgExamenesTestDAO;
  }

  public CfgMuestrasDAO getCfgMuestrasDAO() {
    return cfgMuestrasDAO;
  }

  public void setCfgMuestrasDAO(CfgMuestrasDAO cfgMuestrasDAO) {
    this.cfgMuestrasDAO = cfgMuestrasDAO;
  }

  public CfgSeccionesDAO getCfgSeccionesDAO() {
    return cfgSeccionesDAO;
  }

  public void setCfgSeccionesDAO(CfgSeccionesDAO cfgSeccionesDAO) {
    this.cfgSeccionesDAO = cfgSeccionesDAO;
  }

  public CfgEnvasesDAO getCfgEnvasesDAO() {
    return cfgEnvasesDAO;
  }

  public void setCfgEnvasesDAO(CfgEnvasesDAO cfgEnvasesDAO) {
    this.cfgEnvasesDAO = cfgEnvasesDAO;
  }

  public CfgLaboratoriosDAO getCfgLaboratoriosDAO() {
    return cfgLaboratoriosDAO;
  }

  public void setCfgLaboratoriosDAO(CfgLaboratoriosDAO cfgLaboratoriosDAO) {
    this.cfgLaboratoriosDAO = cfgLaboratoriosDAO;
  }

  public CfgTiposderesultadoDAO getCfgTiposderesultadoDAO() {
    return cfgTiposderesultadoDAO;
  }

  public void setCfgTiposderesultadoDAO(CfgTiposderesultadoDAO cfgTiposderesultadoDAO) {
    this.cfgTiposderesultadoDAO = cfgTiposderesultadoDAO;
  }

  public CfgBacgrupostestDAO getCfgBacgrupostestDAO() {
    return cfgBacgrupostestDAO;
  }

  public void setCfgBacgrupostestDAO(CfgBacgrupostestDAO cfgBacgrupostestDAO) {
    this.cfgBacgrupostestDAO = cfgBacgrupostestDAO;
  }

  public CfgAntecedentesDAO getCfgaDAO() {
    return cfgaDAO;
  }

  public void setCfgaDAO(CfgAntecedentesDAO cfgaDAO) {
    this.cfgaDAO = cfgaDAO;
  }

  public CfgAntecedentestestDAO getCfgAntecedentestestDAO() {
    return cfgAntecedentestestDAO;
  }

  public void setCfgAntecedentestestDAO(CfgAntecedentestestDAO cfgAntecedentestestDAO) {
    this.cfgAntecedentestestDAO = cfgAntecedentestestDAO;
  }

  public CfgUnidadesdemedidaDAO getCfgUnidadesdemedidaDAO() {
    return cfgUnidadesdemedidaDAO;
  }

  public void setCfgUnidadesdemedidaDAO(CfgUnidadesdemedidaDAO cfgUnidadesdemedidaDAO) {
    this.cfgUnidadesdemedidaDAO = cfgUnidadesdemedidaDAO;
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

  public CfgMetodosTestDAO getCfgMetodosTestDAO() {
    return cfgMetodosTestDAO;
  }

  public void setCfgMetodosTestDAO(CfgMetodosTestDAO cfgMetodosTestDAO) {
    this.cfgMetodosTestDAO = cfgMetodosTestDAO;
  }

  public CfgGlosasDAO getCfgGlosasDAO() {
    return cfgGlosasDAO;
  }

  public void setCfgGlosasDAO(CfgGlosasDAO cfgGlosasDAO) {
    this.cfgGlosasDAO = cfgGlosasDAO;
  }

  public CfgGlosastestDAO getCfgGlosastestDAO() {
    return cfgGlosastestDAO;
  }

  public void setCfgGlosastestDAO(CfgGlosastestDAO cfgGlosastestDAO) {
    this.cfgGlosastestDAO = cfgGlosastestDAO;
  }

  public CfgValoresreferenciaDAO getCvrDAO() {
    return cvrDAO;
  }

  public void setCvrDAO(CfgValoresreferenciaDAO cvrDAO) {
    this.cvrDAO = cvrDAO;
  }

  @Override
  public TestDTO getTestById(int idTest) throws BiosLISDAONotFoundException, BiosLISDAOException {
    TestDTO testDTO = new TestDTO();
    CfgTest test = cfgTestDAO.getTestById(idTest);
    CfgSecciones seccion = cfgSeccionesDAO.getSeccionById(test.getCfgSecciones());
    testDTO.setCfgTest(test);
    testDTO.setCfgSecciones(seccion);
    testDTO.setIdTipoMuestra(new BigDecimal(test.getCfgMuestras().getCmueIdtipomuestra()));
    testDTO.setIdEnvase(new BigDecimal(test.getCfgEnvases().getCenvIdenvase()));
    testDTO.setIdUnidadmedida(new BigDecimal(test.getCfgUnidadesdemedida().getCumIdunidadmedida()));
    if (test.getCfgTiposderesultado() != null) {
      testDTO.setIdTipoderesultado(new BigDecimal(test.getCfgTiposderesultado().getCtrIdtiporesultado()));
    }
    if (test.getCfgBacgrupostest() != null) {
      testDTO.setIdBacgrupotest(new BigDecimal(test.getCfgBacgrupostest().getCbgtIdbacgrupotest()));
    }
    List<CfgAntecedentes> listAntecedentes = new ArrayList<>();
    List<CfgAntecedentestest> listAntecedenTest = cfgAntecedentestestDAO.getAntecedentes(idTest);
    for (CfgAntecedentestest antTest : listAntecedenTest) {
      listAntecedentes.add(antTest.getCfgAntecedentes());
    }
    testDTO.setListAntecedentes(listAntecedentes);
    List<MetodosTestDTO> lstMetodoDto = new ArrayList<>();
    List<CfgMetodos> listMetodos = cfgMetodosDAO.getMetodosByTest(idTest);
    for (CfgMetodos metodo : listMetodos) {
      MetodosTestDTO mdto = new MetodosTestDTO();
      CfgMetodostest metodotest = cfgMetodosTestDAO.getMetodoByIdyTest(idTest, (short) metodo.getCmetIdmetodo());
      if (metodotest != null) {
        mdto.setCtIdtest((int) metodotest.getId().getCmtIdtest());
        mdto.setCmtActivo(metodotest.getCmtActivo());
        mdto.setCmtEsvalorpordefecto(metodotest.getCmtEsvalorpordefecto());
        if (metodotest.getCmtEsvalorpordefecto().equals('S')) {
          testDTO.setMetodoPorDefecto(metodo);
        }
      }
      mdto.setCmetIdmetodo(metodo.getCmetIdmetodo());
      mdto.setCmetDescripcion(metodo.getCmetDescripcion());
      lstMetodoDto.add(mdto);
    }
    testDTO.setListMetodos(lstMetodoDto);
    List<GlosasTestDTO> lstGlosasDTO = new ArrayList<>();
    List<CfgGlosastest> listGlosasTest = cfgGlosastestDAO.getGlosasByTest(test);
    for (CfgGlosastest gt : listGlosasTest) {
      GlosasTestDTO gtd = new GlosasTestDTO();
      gtd.setCgIdglosa(gt.getCfgGlosas().getCgIdglosa());
      gtd.setCgCodigoglosa(gt.getCfgGlosas().getCgCodigoglosa());
      gtd.setCgDescripcion(gt.getCfgGlosas().getCgDescripcion());
      gtd.setCgtEsglosacritica(gt.getCgtEsglosacritica());
      gtd.setCgtSefirmaporlotes(gt.getCgtSefirmaporlotes());
      lstGlosasDTO.add(gtd);
    }
    testDTO.setListGlosas(lstGlosasDTO);
    return testDTO;
  }

  @Override
  public List<LaboratorioCentroDTO> getLaboratoriosCentros() throws BiosLISDAOException {
    return cfgLaboratoriosDAO.getLaboratoriosCentroSalud();
  }

  @Override
  public List<SeccionLabDTO> getSeccionesLaboratorios(int idLab) throws BiosLISDAOException {
    return cfgSeccionesDAO.getSeccionesLaboratorios(idLab);
  }

  @Override
  public List<CfgTest> getTestsByCodigo(String codigo) throws BiosLISDAOException {
    return cfgTestDAO.getTestbyCodigo(codigo);
  }

  @Override
  public List<CfgAntecedentes> listAntecedentes() throws BiosLISDAOException {
    return cfgaDAO.getAntecedentes();
  }

  @Override
  public List<CfgUnidadesdemedida> listUnidadesMedida() throws BiosLISDAOException {
    return cfgUnidadesdemedidaDAO.getUnidadesMedida();
  }

  @Override
  public List<CfgTiposderesultado> listTiposderesultado() throws BiosLISDAOException {
    return cfgTiposderesultadoDAO.getTiposResultado();
  }

  @Override
  public List<CfgBacgrupostest> listBacGruposTest() throws BiosLISDAOException {
    return cfgBacgrupostestDAO.getListBacGruposTests();
  }

  @Override
  public List<CfgGruposexamenesfonasa> listGruposExamenesFonasa() throws BiosLISDAOException {
    return gruposExamenesFonasaDAO.listaGruposExamenesFonasa();
  }

  @Override
  public void agregarTest(TestDTO testDTO, List<String> antecedentes, List<CfgValoresreferencia> listaVR, Long idUsuario)
      throws BiosLISDAOException {
    CfgTest newTest = testDTO.getCfgTest();
    newTest.setCfgSecciones(testDTO.getCfgSecciones().getCsecIdseccion());
    newTest.setCfgEnvases(cfgEnvasesDAO.getEnvaseById(testDTO.getIdEnvase().intValue()));
    newTest.setCfgMuestras(cfgMuestrasDAO.getMuestraById(testDTO.getIdTipoMuestra().shortValue()));
    newTest
        .setCfgTiposderesultado(cfgTiposderesultadoDAO.getTipoResultadoById(testDTO.getIdTipoderesultado().intValue()));
    if (testDTO.getIdUnidadmedida().intValue() > 0) {
      CfgUnidadesdemedida unidadesdemedida = cfgUnidadesdemedidaDAO
          .getUnidadesdemedidaById(testDTO.getIdUnidadmedida().intValue());
      newTest.setCfgUnidadesdemedida(unidadesdemedida);
    }
    if (testDTO.getIdBacgrupotest().byteValue() > 0) {
      CfgBacgrupostest bacgrupostest = cfgBacgrupostestDAO
          .getBacGruposTestById(testDTO.getIdBacgrupotest().byteValue());
      newTest.setCfgBacgrupostest(bacgrupostest);
    }
    newTest.setCtTieneantecedentes(antecedentes.isEmpty() ? "N" : "S");
    cfgTestDAO.insertTest(newTest);
    
  //aqui se realiza insert de log tablas   ******************* 
    
    LogCfgtablas lct = new LogCfgtablas();
    lct.setLctIdtabla(newTest.getCtIdtest());
    lct.setCfgAccionesdatos(1);
    lct.setLctNombretabla("CFG_TEST");  
    lct.setLctValornuevo("");
    lct.setLctIdusuario(idUsuario.intValue());
    lct.setLctDescripcion(newTest.getCtAbreviado());
    lct.setLctValoranterior("");

    logCfgTablasDAO.insertLogTablas(lct); 
    //*********************************************************
    
    for (String id : antecedentes) {
      CfgAntecedentes ca = cfgaDAO.getAntecedenteById(Integer.parseInt(id));
      cfgaDAO.getAntecedenteById(Integer.parseInt(id));
      CfgAntecedentestest cat = new CfgAntecedentestest();
      CfgAntecedentestestId catId = new CfgAntecedentestestId();
      catId.setCatIdantecedente(ca.getCaIdantecedente());
      catId.setCatIdtest(newTest.getCtIdtest());
      cat.setId(catId);
      cat.setCfgAntecedentes(ca);
      cfgAntecedentestestDAO.insertAntecedentesTest(cat);
    }
    for (MetodosTestDTO mtDto : testDTO.getListMetodos()) {
      CfgMetodostest cmt = new CfgMetodostest();
      cmt.setCfgTest(newTest);
      cmt.setCmtActivo("S");
      cmt.setCmtEsvalorpordefecto(mtDto.getCmtEsvalorpordefecto());
      cmt.setCmtNrometodo(Byte.valueOf("1"));
      CfgMetodostestId cmtId = new CfgMetodostestId();
      cmtId.setCmtIdmetodo((short) mtDto.getCmetIdmetodo());
      cmtId.setCmtIdtest(newTest.getCtIdtest());
      cmt.setId(cmtId);
      cfgMetodosTestDAO.insertMetodosTest(cmt);
    }

    for (GlosasTestDTO gtDto : testDTO.getListGlosas()) {
      CfgGlosastest cgt = new CfgGlosastest();
      cgt.setCfgTest(newTest);
      CfgGlosas cg = cfgGlosasDAO.getGlosaById(gtDto.getCgIdglosa());
      cgt.setCfgGlosas(cg);
      cgt.setCgtEsglosacritica(gtDto.getCgtEsglosacritica());
      cgt.setCgtSefirmaporlotes(gtDto.getCgtSefirmaporlotes());
      cfgGlosastestDAO.insertGlosasTest(cgt);
    }

    for (CfgValoresreferencia cvr : listaVR) {    	
      cvr.setCvrIdtest(newTest.getCtIdtest());
      if (cvr.getCvrIdvalorreferencia() == 0) {
        cvrDAO.insertValoresReferencias(cvr, idUsuario);
      } else {         
        cvrDAO.updateValoresReferencias(cvr);         
      }
    }

  }

  @Override
  public void actualizarTest(TestDTO testDTO, List<String> antecedentes, List<CfgValoresreferencia> listaVR, Long idUsuario)
      throws BiosLISDAOException {

	  CfgTest testAntiguo = new CfgTest();
	try {
		testAntiguo = cfgTestDAO.getTestById(testDTO.getCfgTest().getCtIdtest());
	} catch (BiosLISDAONotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (BiosLISDAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
    CfgTest test = testDTO.getCfgTest();
    test.setCfgSecciones(testDTO.getCfgSecciones().getCsecIdseccion());
    test.setCfgEnvases(cfgEnvasesDAO.getEnvaseById(testDTO.getIdEnvase().intValue()));
    test.setCfgMuestras(cfgMuestrasDAO.getMuestraById(testDTO.getIdTipoMuestra().shortValue()));
    test.setCfgTiposderesultado(cfgTiposderesultadoDAO.getTipoResultadoById(testDTO.getIdTipoderesultado().intValue()));
    if (testDTO.getIdUnidadmedida().intValue() > 0) {
      CfgUnidadesdemedida unidadesdemedida = cfgUnidadesdemedidaDAO
          .getUnidadesdemedidaById(testDTO.getIdUnidadmedida().intValue());
      test.setCfgUnidadesdemedida(unidadesdemedida);
    }
    if (testDTO.getIdBacgrupotest().byteValue() > 0) {
      CfgBacgrupostest bacgrupostest = cfgBacgrupostestDAO
          .getBacGruposTestById(testDTO.getIdBacgrupotest().byteValue());
      test.setCfgBacgrupostest(bacgrupostest);
    }
    test.setCtTieneantecedentes(antecedentes.isEmpty() ? "N" : "S");

    for (String ida : antecedentes) {
      String existeAntecente = cfgAntecedentestestDAO.buscarAntecedenteTestById(test.getCtIdtest(),
          Integer.parseInt(ida));
      if (existeAntecente.equals("N")) {
        CfgAntecedentes ca = cfgaDAO.getAntecedenteById(Integer.parseInt(ida));
        CfgAntecedentestest cat = new CfgAntecedentestest();
        CfgAntecedentestestId catId = new CfgAntecedentestestId();
        catId.setCatIdantecedente(ca.getCaIdantecedente());
        catId.setCatIdtest(test.getCtIdtest());
        cat.setId(catId);
        cat.setCfgAntecedentes(ca);
        cfgAntecedentestestDAO.insertAntecedentesTest(cat);
        
      //Se agregan antecedentes a log tablas **********************
        LogCfgtablas logTabla = new LogCfgtablas();
        logTabla.setCfgAccionesdatos(2);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_ANTECEDENTESTEST");
        logTabla.setLctNombrecampo("CAT_IDANTECEDENTE");
        logTabla.setLctValornuevo(cat.getCfgAntecedentes().getCaDescripcion());
        logTabla.setLctIdtabla(test.getCtIdtest().intValue());
        logTabla.setLctDescripcion(test.getCtAbreviado());
        logCfgTablasDAO.insertLogTablas(logTabla);
      //***********************************************************
      }
    }

    List<CfgAntecedentestest> listAntecedentesTest = cfgAntecedentestestDAO.getAntecedentes(test.getCtIdtest());
    for (CfgAntecedentestest cat : listAntecedentesTest) {
      boolean aunExisteAnt = false;
      for (String idae : antecedentes) {
        if (cat.getCfgAntecedentes().getCaIdantecedente() == Integer.parseInt(idae)) {
          aunExisteAnt = true;
          break;
        }
      }
      if (aunExisteAnt == false) {
        cfgAntecedentestestDAO.deleteAntecedentesTest(cat);
      }
    }
    // chequear los métodos que ya existen, agregar los nuevos y eliminar o
    // desactivar los que se desmarcaron
    for (MetodosTestDTO mtDto : testDTO.getListMetodos()) {
      String existeMetodo = cfgMetodosTestDAO.buscarMetodoTestById(test.getCtIdtest().longValue(),
          (short) mtDto.getCmetIdmetodo());
      if (existeMetodo.equals("N")) {
        CfgMetodos cm = cfgMetodosDAO.getMetodoById(mtDto.getCmetIdmetodo());
        CfgMetodostest cmt = new CfgMetodostest();
        CfgMetodostestId cmtId = new CfgMetodostestId();
        cmtId.setCmtIdmetodo((short) cm.getCmetIdmetodo());
        cmtId.setCmtIdtest(test.getCtIdtest());
        cmt.setId(cmtId);
        cmt.setCmtActivo("S");
        cmt.setCmtNrometodo(Byte.valueOf("1"));
        cmt.setCmtEsvalorpordefecto(mtDto.getCmtEsvalorpordefecto());
        cmt.setCfgTest(test);
        cfgMetodosTestDAO.insertMetodosTest(cmt);
        //Se agregan metodos a log tablas **********************
	        LogCfgtablas logTabla = new LogCfgtablas();
	        logTabla.setCfgAccionesdatos(2);
	        logTabla.setLctIdusuario(idUsuario.intValue());
	        logTabla.setLctNombretabla("CFG_METODOSTEST");
	        logTabla.setLctNombrecampo("CMT_IDMETODO");
	        logTabla.setLctValornuevo(cm.getCmetDescripcion());
	        logTabla.setLctIdtabla(test.getCtIdtest().intValue());
	        logTabla.setLctDescripcion(test.getCtAbreviado());
	        logCfgTablasDAO.insertLogTablas(logTabla);
      //***********************************************************
      } else {
        // esto es para cuando se cambia el valor por defecto
        CfgMetodostest cmt = cfgMetodosTestDAO.getMetodoByIdyTest(test.getCtIdtest().longValue(),
            (short) mtDto.getCmetIdmetodo());
        String valorAntiguo = cmt.getCmtEsvalorpordefecto();
        cmt.setCmtEsvalorpordefecto(mtDto.getCmtEsvalorpordefecto());
        cfgMetodosTestDAO.updateMetodosTest(cmt);
        
       //Se cambia valor por defecto metodo a log tablas **********************     
        logCfgTablasDAO.validarDatos(mtDto.getCmtEsvalorpordefecto() , valorAntiguo, idUsuario , "CMT_ESVALORPORDEFECTO" ,  test.getCtIdtest().intValue(), "CFG_METODOSTEST", test.getCtAbreviado());
     //***********************************************************
      }
    }

    List<CfgMetodostest> listMetodosTest = cfgMetodosTestDAO.getMetodos(test.getCtIdtest().longValue());
    for (CfgMetodostest cmt : listMetodosTest) {
      boolean aunExisteMet = false;
      for (MetodosTestDTO metodoDel : testDTO.getListMetodos()) {
        if (cmt.getId().getCmtIdmetodo() == metodoDel.getCmetIdmetodo()) {
          aunExisteMet = true;
        }
      }
      if (aunExisteMet == false) {
        cfgMetodosTestDAO.deleteMetodoTest(cmt);
        
        //Se agrega  metodo eliminado a log tablas **********************     
        CfgMetodos cmAntiguo = cfgMetodosDAO.getMetodoById(cmt.getId().getCmtIdmetodo());
        LogCfgtablas logTabla = new LogCfgtablas();
        logTabla.setCfgAccionesdatos(3);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_METODOSTEST");
        logTabla.setLctNombrecampo("CMT_IDMETODO");
        logTabla.setLctValoranterior(cmAntiguo.getCmetDescripcion());
        logTabla.setLctIdtabla(test.getCtIdtest().intValue());
        logTabla.setLctDescripcion(test.getCtAbreviado());
        logCfgTablasDAO.insertLogTablas(logTabla);
  //***********************************************************
     
      }
    }

    // chequear las glosas que ya existen, agregar nuevos y eliminar o desactivar
    // los que se desmarcaron
    for (GlosasTestDTO gtDto : testDTO.getListGlosas()) {
      if (!cfgGlosastestDAO.existeGlosa(gtDto.getCgIdglosa(), test.getCtIdtest())) {
        CfgGlosastest cgt = new CfgGlosastest();
        cgt.setCfgTest(test);
        cgt.setCfgGlosas(new CfgGlosas(gtDto.getCgIdglosa(), gtDto.getCgCodigoglosa()));
        cgt.setCgtEsglosacritica(gtDto.getCgtEsglosacritica());
        cgt.setCgtSefirmaporlotes(gtDto.getCgtSefirmaporlotes());
        cfgGlosastestDAO.insertGlosasTest(cgt);
        
        //Se agrega  glosa a log tablas **********************     
        LogCfgtablas logTabla = new LogCfgtablas();
        logTabla.setCfgAccionesdatos(2);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_GLOSASTEST");
        logTabla.setLctNombrecampo("CGT_IDGLOSA");
        logTabla.setLctValornuevo(gtDto.getCgDescripcion());
        logTabla.setLctIdtabla(test.getCtIdtest().intValue());
        logTabla.setLctDescripcion(test.getCtAbreviado());
        logCfgTablasDAO.insertLogTablas(logTabla);
  //***********************************************************
        
      } else {
        CfgGlosastest cgt = cfgGlosastestDAO.getGlosaByIdAndTest(gtDto.getCgIdglosa(), test.getCtIdtest());
        CfgGlosastest cgtAntiguo = cfgGlosastestDAO.getGlosaByIdAndTest(gtDto.getCgIdglosa(), test.getCtIdtest());
        cgt.setCgtEsglosacritica(gtDto.getCgtEsglosacritica());
        cgt.setCgtSefirmaporlotes(gtDto.getCgtSefirmaporlotes());
        cfgGlosastestDAO.updateGlosasTest(cgt);
        
       //se agrega  modifica valor glosacritica y sefirmaporlotes en log evento
        logCfgTablasDAO.validarDatos(cgt.getCgtEsglosacritica(), cgtAntiguo.getCgtEsglosacritica(), idUsuario , "CGT_ESGLOSACRITICA" , (int) testAntiguo.getCtIdtest(), "CFG_GLOSASTEST", testAntiguo.getCtAbreviado());
        logCfgTablasDAO.validarDatos(cgt.getCgtSefirmaporlotes(), cgtAntiguo.getCgtSefirmaporlotes(), idUsuario , "CGT_SEFIRMAPORLOTES" , (int) testAntiguo.getCtIdtest(), "CFG_GLOSASTEST", testAntiguo.getCtAbreviado());
   	    
        
  //***********************************************************
      }
    }

    List<CfgGlosastest> listGlosasTest = cfgGlosastestDAO.getGlosasByTest(test);
    for (CfgGlosastest cgt : listGlosasTest) {
      boolean aunExisteGlosa = false;
      for (GlosasTestDTO glosadel : testDTO.getListGlosas()) {
        if (cgt.getCfgGlosas().getCgIdglosa() == glosadel.getCgIdglosa()) {
          aunExisteGlosa = true;
          break;
        }
      }
      if (aunExisteGlosa == false) {
        cfgGlosastestDAO.deleteGlosasTest(cgt);
        
       //Se elimina  glosa a log tablas **********************    
       
        LogCfgtablas logTabla = new LogCfgtablas();
        logTabla.setCfgAccionesdatos(3);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_GLOSASTEST");
        logTabla.setLctNombrecampo("CGT_IDGLOSA");        
        logTabla.setLctValoranterior(cgt.getCfgGlosas().getCgDescripcion());
        logTabla.setLctIdtabla(test.getCtIdtest().intValue());
        logTabla.setLctDescripcion(test.getCtAbreviado());
        logCfgTablasDAO.insertLogTablas(logTabla);
      //********************************************************************
      }
    }

    for (CfgValoresreferencia cvr : listaVR) {    	
      if (cvr.getCvrIdvalorreferencia() == 0) {
    	  
        cvrDAO.insertValoresReferencias(cvr, idUsuario);          
        //Se agregan valoresreferencia a log tablas **********************    
        
         LogCfgtablas logTabla = new LogCfgtablas();
         logTabla.setCfgAccionesdatos(2);
         logTabla.setLctIdusuario(idUsuario.intValue());
         logTabla.setLctNombretabla("CFG_VALORESREFERENCIA");
         logTabla.setLctNombrecampo("CVR_IDVALORREFERENCIA");        
         logTabla.setLctValornuevo(String.valueOf(cvr.getCvrIdvalorreferencia()));
         logTabla.setLctIdtabla((int) cvr.getCvrIdtest());
         logTabla.setLctDescripcion(test.getCtAbreviado());
         logCfgTablasDAO.insertLogTablas(logTabla);
       //********************************************************************
      } else {    
    	
    	 CfgValoresreferencia cvrAntiguo = cvrDAO.getValorReferenciaById(cvr.getCvrIdvalorreferencia());
        cvrDAO.updateValoresReferencias(cvr);       
        logCfgTablasDAO.compararTablasValoresDeReferencia(cvr, cvrAntiguo, idUsuario, test.getCtAbreviado());
      }
    }

    cfgTestDAO.updateTest(test);
    
    
    // SE ENVIA A GUARDAR LOS DATOS EN LOG TABLA************  
    	logCfgTablasDAO.compararTablasTest(test, testAntiguo,  idUsuario,test.getCtAbreviado());
  //**************************************************************
  }

  @Override
  public List<MetodosTestDTO> listaMetodosTest(int idTest) throws BiosLISDAOException {
    List<MetodosTestDTO> lstMetodoDto = new ArrayList<>();
    List<CfgMetodos> listMetodos = cfgMetodosDAO.getMetodos();
    for (CfgMetodos metodo : listMetodos) {
      MetodosTestDTO mdto = new MetodosTestDTO();
      CfgMetodostest metodotest = cfgMetodosTestDAO.getMetodoByIdyTest(idTest, (short) metodo.getCmetIdmetodo());
      if (metodotest != null) {
        mdto.setCtIdtest((int) metodotest.getId().getCmtIdtest());
        mdto.setCmtActivo(metodotest.getCmtActivo());
        mdto.setCmtEsvalorpordefecto(metodotest.getCmtEsvalorpordefecto());
      }
      mdto.setCmetIdmetodo(metodo.getCmetIdmetodo());
      mdto.setCmetDescripcion(metodo.getCmetDescripcion());
      lstMetodoDto.add(mdto);
    }
    return lstMetodoDto;
  }

  @Override
  public List<GlosasTestDTO> listaGlosasTest(int idTest, int idSeccion) throws BiosLISDAOException {
    List<GlosasTestDTO> lstGlosasDto = new ArrayList<>();
    List<CfgGlosas> listGlosas = new ArrayList<>();
    if (idSeccion > 0) {
      listGlosas = cfgGlosasDAO.getGlosasByIdSeccion(idSeccion);
    } else {
      listGlosas = cfgGlosasDAO.getGlosasActivas();
    }

    for (CfgGlosas glosa : listGlosas) {
      GlosasTestDTO gdto = new GlosasTestDTO();
      CfgGlosastest glosasTest = cfgGlosastestDAO.getGlosaByIdAndTest(glosa.getCgIdglosa(), idTest);
      gdto.setCgIdglosa(glosa.getCgIdglosa());
      gdto.setCgCodigoglosa(glosa.getCgCodigoglosa());
      gdto.setCgDescripcion(glosa.getCgDescripcion());
      gdto.setCgIdSeccion(glosa.getCgIdSeccion());
      if (glosasTest != null) {
        gdto.setCgtEsglosacritica(glosasTest.getCgtEsglosacritica());
        gdto.setCgtSefirmaporlotes(glosasTest.getCgtSefirmaporlotes());
      }
      lstGlosasDto.add(gdto);
    }
    return lstGlosasDto;
  }

  @Override
  public List<MetodosTestDTO> getMetodosTest(int idTest) throws BiosLISDAOException {
    List<MetodosTestDTO> lstMetodoDto = new ArrayList<>();
    List<CfgMetodos> listMetodos = cfgMetodosDAO.getMetodosByTest(idTest);
    for (CfgMetodos metodo : listMetodos) {
      MetodosTestDTO mdto = new MetodosTestDTO();
      CfgMetodostest metodotest = cfgMetodosTestDAO.getMetodoByIdyTest(idTest, (short) metodo.getCmetIdmetodo());
      if (metodotest != null) {
        mdto.setCtIdtest((int) metodotest.getId().getCmtIdtest());
        mdto.setCmtActivo(metodotest.getCmtActivo());
        mdto.setCmtEsvalorpordefecto(metodotest.getCmtEsvalorpordefecto());
      }
      mdto.setCmetIdmetodo(metodo.getCmetIdmetodo());
      mdto.setCmetDescripcion(metodo.getCmetDescripcion());
      lstMetodoDto.add(mdto);
    }
    return lstMetodoDto;

  }

  @Override
  public List<TestDTO> getTestDtoBySeccion(int idSeccion) throws BiosLISDAOException {
    List<TestDTO> lisTestDTO = new ArrayList<>();
    List<CfgTest> listTest = cfgTestDAO.getTestBySeccionId(idSeccion);
    for (CfgTest test : listTest) {
      TestDTO testDTO = new TestDTO();
      CfgSecciones seccion = cfgSeccionesDAO.getSeccionById(test.getCfgSecciones());
      testDTO.setCfgTest(test);
      testDTO.setCfgSecciones(seccion);
      CfgMuestras muestras = cfgMuestrasDAO.getMuestraById(test.getCfgMuestras().getCmueIdtipomuestra());
      testDTO.setCfgMuestras(muestras);
      CfgMetodos metodoDefault = cfgMetodosDAO.getMetodoPorDefecto(test.getCtIdtest());
      testDTO.setMetodoPorDefecto(metodoDefault);
      lisTestDTO.add(testDTO);
    }
    return lisTestDTO;
  }

  @Override
  public List<TestDTO> getTestDtoBySeccionExamen(int idSeccion, long idExamen) throws BiosLISDAOException {
    List<TestDTO> lisTestDTO = new ArrayList<>();
    List<CfgTest> listTests = cfgTestDAO.getTestBySeccionExamen(idSeccion, idExamen);
    listTests.forEach((ct) -> {
      try {
        TestDTO testDTO = new TestDTO();
        testDTO.setCfgTest(ct);
        CfgSecciones seccion = cfgSeccionesDAO.getSeccionById(ct.getCfgSecciones());
        testDTO.setCfgSecciones(seccion);
        CfgMuestras muestras = cfgMuestrasDAO.getMuestraById(ct.getCfgMuestras().getCmueIdtipomuestra());
        testDTO.setCfgMuestras(muestras);
        lisTestDTO.add(testDTO);
      } catch (BiosLISDAOException ex) {
        Logger.getLogger(ConfiguracionTestServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
      }
    });
    return lisTestDTO;
  }

  @Override
  public List<CfgTest> getTestsFiltro(HashMap<String, String> filtros)
      throws BiosLISDAOException, BiosLISDAONotFoundException {
    String codigo = filtros.get("codigo").toUpperCase();
    String nombre = filtros.get("nombre").toUpperCase();

    int idSeccion = (filtros.get("idSeccion") == null) ? 0 : Integer.parseInt(filtros.get("idSeccion"));
    int idExamen = (filtros.get("idExamen") == null) ? 0 : Integer.parseInt(filtros.get("idExamen"));

    String activo = filtros.get("activo");

    List<CfgTest> listaTests = new ArrayList<>();
    if (idExamen > 0) {
      List<CfgExamenestest> listaExamenesTest;
      if (nombre.length() > 0) {
        listaExamenesTest = cfgExamenesTestDAO.getTestByExamenIdyNombre(idExamen, nombre, activo);
      } else {
        listaExamenesTest = cfgExamenesTestDAO.getTestByExamenIdActivo(idExamen, activo);
      }
      for (CfgExamenestest ext : listaExamenesTest) {
        CfgTest test = cfgTestDAO.getTestById(ext.getId().getCetIdtest());
        listaTests.add(test);
      }
    } else {
      listaTests = cfgTestDAO.getTestFiltro(codigo, nombre, activo, idSeccion);
    }
    return listaTests;
  }

  @Override
  public List<TestProcesoDTO> getTestsBusquedaProcesos(int idSeccion, long idExamen) throws BiosLISDAOException {
    List<TestProcesoDTO> listaTestsProceso = cfgTestDAO.obtenerTestsBusquedaProceso(idSeccion, idExamen);
    for (TestProcesoDTO tpd : listaTestsProceso) {
      List<ExamenThinDTO> examenesTest = cfgExamenesTestDAO.getExamenesTest(tpd.getCT_IDTEST().intValue());
      tpd.setListExamenesTest(examenesTest);
    }
    return listaTestsProceso;
  }

  @Override
  public List<CfgTestExcel> getTestParaExcel() throws BiosLISDAOException, BiosLISDAONotFoundException {
    // TODO Auto-generated method stub
    return cfgTestDAO.getTestParaExcel();
  }

  @Override
  public List<TestExamenSeccionDto> getTestDtoBySeccionNativo(Integer idSeccion) throws BiosLISDAOException {
    List<TestExamenSeccionDto> lstTestExamenSeccionDto = cfgTestDAO.getTestBySeccionIdNativo(idSeccion);
    return lstTestExamenSeccionDto;
  }

@Override
public List<CfgTestDTO> getTestByExamen(long idExamen) throws BiosLISDAOException {
	// TODO Auto-generated method stubCfgTestDTO
	return cfgTestDAO.getTestByExamen(idExamen);
}

@Override
public List<AntecedentePacienteDTO> getAntecedenteByExamen(long idExamen) throws BiosLISDAOException {
	// TODO Auto-generated method stub
	return cfgTestDAO.getAntecedentesByExamen(idExamen);
}

}
