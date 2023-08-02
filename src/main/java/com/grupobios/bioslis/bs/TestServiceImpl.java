package com.grupobios.bioslis.bs;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.grupobios.bioslis.common.ActionExamenExecutor;
import com.grupobios.bioslis.common.ActionExecutorFactory;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisExamenSignerException;
import com.grupobios.bioslis.common.CondicionAntecedenteExecutor;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.common.TestBiosAction;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.BiosLISNotFoundException;
import com.grupobios.bioslis.dao.CapturaResultadosDAO;
import com.grupobios.bioslis.dao.CfgCondicionesformulasDAO;
import com.grupobios.bioslis.dao.CfgTestDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesTestDAO;
import com.grupobios.bioslis.dao.DatosFichasmuestrasDAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dto.CRDetalleTestDTO;
import com.grupobios.bioslis.dto.CfgCondicionesformulasDTO;
import com.grupobios.bioslis.dto.CfgTestDTO;
import com.grupobios.bioslis.dto.ExamenesResultadosDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.FiltroTestDTO;
import com.grupobios.bioslis.dto.HijosTest;
import com.grupobios.bioslis.dto.LogFichasExamenesTestDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.entity.CfgCondicionesformulas;
import com.grupobios.bioslis.entity.CfgCondicionesformulasId;
import com.grupobios.bioslis.entity.CfgEstadosresultadostest;
import com.grupobios.bioslis.entity.CfgGlosas;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasexamenestestId;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.LogEventosfichas;

public class TestServiceImpl implements TestService {

  private static Logger logger = LogManager.getLogger(TestServiceImpl.class);
  private ActionExecutorFactory actionExecutorFactory;
  private LogEventosfichasDAO logEventosfichasDAO;
  private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;
  private CfgTestDAO cfgTestDAO;
  private CapturaResultadosDAO capturaResultadosDAO;
  private CfgCondicionesformulasDAO cfgCondicionesformulasDAO;
  private FormulaEvaluador fe;
  private DatosFichasExamenesDAO datosFichasExamenesDAO;

  public DatosFichasExamenesDAO getDatosFichasExamenesDAO() {
    return datosFichasExamenesDAO;
  }

  public void setDatosFichasExamenesDAO(DatosFichasExamenesDAO datosFichasExamenesDAO) {
    this.datosFichasExamenesDAO = datosFichasExamenesDAO;
  }

  public FormulaEvaluador getFe() {
    return fe;
  }

  public void setFe(FormulaEvaluador fe) {
    this.fe = fe;
  }

  public CfgCondicionesformulasDAO getCfgCondicionesformulasDAO() {
    return cfgCondicionesformulasDAO;
  }

  public void setCfgCondicionesformulasDAO(CfgCondicionesformulasDAO cfgCondicionesformulasDAO) {
    this.cfgCondicionesformulasDAO = cfgCondicionesformulasDAO;
  }

  public ActionExecutorFactory getActionExecutorFactory() {
    return actionExecutorFactory;
  }

  public void setActionExecutorFactory(ActionExecutorFactory actionExecutorFactory) {
    this.actionExecutorFactory = actionExecutorFactory;
  }

  private Map<Pair<Short, String>, Short> TestEstadomatrizTransicion = new HashMap<>();

  public TestServiceImpl() {
    this.TestEstadoAccionMapInit();
  }

  @Override
  public List<LogFichasExamenesTestDTO> getTestEvents(DatosFichasexamenestestId dfetId)
      throws BiosLISDAONotFoundException, BiosLISDAOException {
    return logEventosfichasDAO.getLogEventosTest(dfetId);
  }

  /**
   * @return the logEventosfichasDAO
   */
  public LogEventosfichasDAO getLogEventosfichasDAO() {
    return logEventosfichasDAO;
  }

  /**
   * @param logEventosfichasDAO the logEventosfichasDAO to set
   */
  public void setLogEventosfichasDAO(LogEventosfichasDAO logEventosfichasDAO) {
    this.logEventosfichasDAO = logEventosfichasDAO;
  }

  public DatosFichasExamenesTestDAO getDatosFichasExamenesTestDAO() {
    return datosFichasExamenesTestDAO;
  }

  public void setDatosFichasExamenesTestDAO(DatosFichasExamenesTestDAO datosFichasExamenesTestDAO) {
    this.datosFichasExamenesTestDAO = datosFichasExamenesTestDAO;
  }

  @Override
  public DatosFichasexamenestest doAction(String actionCode, ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISDAOException, BiosLISException, BiosLisExamenSignerException,
      IOException {

    DatosFichasexamenestest dfet = this.executeTransition(actionCode, dfetId, idUsuario); //
                                                                                          // test.
    this.executeAction(actionCode, dfetId, idUsuario);
    return dfet;
  }

  private void executeAction(String actionCode, ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario)
      throws BiosLISDAOException, BiosLISException, BiosLISDAONotFoundException, BiosLisExamenSignerException,
      IOException {

    ActionExamenExecutor executor = actionExecutorFactory.getActionExamenInstance(actionCode.toUpperCase());
    if (executor != null) {
      executor.exec(dfetId, idUsuario); //
    }
  }

  private DatosFichasexamenestest executeTransition(String actionCode, ResultadoNumericoTestExamenOrdenDTO dfetId,
      Long idUsuario) throws BiosLISDAONotFoundException, BiosLISBSException, BiosLISDAOException {

    DatosFichasexamenestest dfet = datosFichasExamenesTestDAO.getDatosFichasExamenesTestByIdTest(dfetId);
    Short valorAntiguo = dfet.getDfetIdestadoresultadotest();
    if (dfet == null) {
      logger.debug("no se encontro datosfichasexamenes ");
      throw new BiosLISDAONotFoundException("No se encontró datosfichasexamenes");
    }

    if (actionCode.equals("RETIRARFIRMA")) {
      DatosFichasexamenes dfe = datosFichasExamenesDAO
          .getDatosFichasExamenesByExamenyOrden(dfet.getId().getDfetIdexamen(), dfet.getId().getDfetNorden());

      if (dfe.getDfeCodigoestadoexamen().equals("A")) {
        logger.debug("examen autorizado no se puede retrirar firma test ");
        throw new BiosLISDAONotFoundException("Examen autorizado, no se puede retirar firma");
      }

    }

    // agregado para agregar usuario firma *** 15-11 por cristian
    logger.debug(actionCode + "en esta accion  testserviceimpl 161");
    // modificado para agregar fecha firma *** 15-11 por cristian
    Timestamp ts = BiosLisCalendarService.getInstance().getTS();

    // agregado para que acepte resultadoomision como digitado -- agregado por
    // cristian 31-01 y este pueda cambiar a firmado
    Short testEstado = null;
    String ResultadoPorDefecto = "";
    if (dfetId.getCERT_DESCRIPCIONESTADOTEST() != null) {
      ResultadoPorDefecto = dfetId.getCERT_DESCRIPCIONESTADOTEST();
    }
    if (ResultadoPorDefecto.equals("DIGITADO") && dfetId.getDFET_RESULTADO() != null) {
      logger.debug("Estado actual es digitado por ser resultadoomision ");
      testEstado = this.getNextState((short) 2, actionCode);

    } else {
      logger.debug("Estado actual {}", dfet.getDfetIdestadoresultadotest());
      testEstado = this.getNextState(dfet.getDfetIdestadoresultadotest(), actionCode);
    }

    if (testEstado == null) {
      logger.error("No se puede {} cuando estado actual es {}", actionCode, dfet.getDfetIdestadoresultadotest());
      throw new BiosLISBSException("No se pudo ejecutar acción " + actionCode);
    }

    if (actionCode.equals("FIRMAR")) {
      dfet.setDfetFechafirma(ts);
      dfet.setDfetIdusuariofirma(idUsuario);
      dfet.setDfetTestfirmado("S");
      dfet.setDfetReferenciadesde(dfetId.getDFET_REFERENCIADESDE());
      dfet.setDfetReferenciahasta(dfetId.getDFET_REFERENCIAHASTA());
      if (dfet.getDfetResultado() == null) {
        dfet.setDfetResultado(dfetId.getDFET_RESULTADO());
      }
      /*
      if (ResultadoPorDefecto.equals("DIGITADO")) {
        dfet.setDfetFechaingresoresultado(ts);
        dfet.setDfetIdusuariodigita(idUsuario);
      }
*/
    } else {
      if (actionCode.equals("RETIRARFIRMA")) {
        dfet.setDfetFechafirma(null);
        dfet.setDfetIdusuariofirma(null);
        dfet.setDfetTestfirmado("N");
        dfet.setDfetReferenciadesde("");
        dfet.setDfetReferenciahasta("");
      }
    }

    dfet.setDfetIdestadoresultadotest(testEstado);
    logger.debug("Estado finall {}", dfet.getDfetIdestadoresultadotest());

    // aqui se agregan datos a log eventos fichas *****************

    LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();

    lef.setDatosFichas(dfet.getDatosFichas().getDfNorden());
    lef.setLefFechaorden(dfet.getDfetFechaorden());
    lef.setLefIdpaciente((int) dfet.getDfetIdpaciente());
    lef.setLefNombretabla("DATOS_FICHASEXAMENESTESTS");
    lef.setLefIdexamen((int) dfet.getId().getDfetIdexamen());
    lef.setLefIdtest((int) dfet.getId().getDfetIdtest());
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefIdusuario(idUsuario.intValue());
    lef.setCfgEventos(2);
    lef.setLefNombrecampo("DFET_IDESTADORESULTADOTEST");

    CfgEstadosresultadostest estadoResultado = new CfgEstadosresultadostest();
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      DatosFichasmuestrasDAO datosFichasmuestrasDAO = new DatosFichasmuestrasDAO();
      DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(dfet.getDfetIdmuestra());
      lef.setLefCodigobarra(dfm.getDfmCodigobarra());
      lef.setLefIdmuestra(dfet.getDfetIdmuestra().intValue());
      lef.setCfgEventos(2);
      estadoResultado = (CfgEstadosresultadostest) sesion.get(CfgEstadosresultadostest.class,
          dfet.getDfetIdestadoresultadotest().byteValue());
      lef.setLefValornuevo(estadoResultado.getCertDescripcionestadotest());
      estadoResultado = (CfgEstadosresultadostest) sesion.get(CfgEstadosresultadostest.class, valorAntiguo.byteValue());
      lef.setLefValoranterior(estadoResultado.getCertDescripcionestadotest());
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    logEventosfichasDAO.insertLogEventosFichas(lef);

    // *****************************aca se acaba agregado a log
    // eventos********************************

    datosFichasExamenesTestDAO.updateDFExamenesTest(dfet);

    datosFichasExamenesDAO.signExamen(dfetId.getDF_NORDEN().longValue(), dfetId.getDFE_IDEXAMEN().longValue(),
        idUsuario);
    return dfet;

  }

  /********************
   * Cambiar por implementacion basada en clases automata
   ****************************/
  private Short getNextState(Short dfetIdestadoresultadotest, String actionCode) {

    Pair<Short, String> testEstadoAccion = Pair.of(dfetIdestadoresultadotest, actionCode);

    return this.TestEstadomatrizTransicion.get(testEstadoAccion);
  }

  private void TestEstadoAccionMapInit() {

    Pair<Short, String> testEstadoAccion = Pair.of(EstadosSistema.DFET_IDRESULTADOTEST_DIGITADO,
        TestBiosAction.FIRMAR.toString());
    this.TestEstadomatrizTransicion.put(testEstadoAccion, EstadosSistema.DFET_IDRESULTADOTEST_FIRMADO);
    testEstadoAccion = Pair.of(EstadosSistema.DFET_IDRESULTADOTEST_FIRMADO, TestBiosAction.RETIRARFIRMA.toString());
    this.TestEstadomatrizTransicion.put(testEstadoAccion, EstadosSistema.DFET_IDRESULTADOTEST_DIGITADO);
    testEstadoAccion = Pair.of(EstadosSistema.DFET_IDRESULTADOTEST_FIRMADO, TestBiosAction.BLOQUEAR.toString());
    this.TestEstadomatrizTransicion.put(testEstadoAccion, EstadosSistema.DFET_IDRESULTADOTEST_BLOQUEADO);
    testEstadoAccion = Pair.of(EstadosSistema.DFET_IDRESULTADOTEST_DIGITADO, TestBiosAction.BLOQUEAR.toString());
    this.TestEstadomatrizTransicion.put(testEstadoAccion, EstadosSistema.DFET_IDRESULTADOTEST_BLOQUEADO);
    testEstadoAccion = Pair.of(EstadosSistema.DFET_IDRESULTADOTEST_FIRMADO, TestBiosAction.BLOQUEAR.toString());
    this.TestEstadomatrizTransicion.put(testEstadoAccion, EstadosSistema.DFET_IDRESULTADOTEST_BLOQUEADO);
    testEstadoAccion = Pair.of(EstadosSistema.DFET_IDRESULTADOTEST_PENDIENTE, TestBiosAction.BLOQUEAR.toString());
    this.TestEstadomatrizTransicion.put(testEstadoAccion, EstadosSistema.DFET_IDRESULTADOTEST_BLOQUEADO);
    testEstadoAccion = Pair.of(EstadosSistema.DFET_IDRESULTADOTEST_TRANSMITIDO, TestBiosAction.FIRMAR.toString());
    this.TestEstadomatrizTransicion.put(testEstadoAccion, EstadosSistema.DFET_IDRESULTADOTEST_FIRMADO);
    testEstadoAccion = Pair.of(EstadosSistema.DFET_IDRESULTADOTEST_BLOQUEADO, TestBiosAction.CAMBIARESTADO.toString());
    this.TestEstadomatrizTransicion.put(testEstadoAccion, EstadosSistema.DFET_IDRESULTADOTEST_PENDIENTE);
    testEstadoAccion = Pair.of(EstadosSistema.DFET_IDRESULTADOTEST_BLOQUEADO, TestBiosAction.DESBLOQUEAR.toString());
    this.TestEstadomatrizTransicion.put(testEstadoAccion, EstadosSistema.DFET_IDRESULTADOTEST_PENDIENTE);
  }

  @Override
  public void doListAction(String actionCode, List<ResultadoNumericoTestExamenOrdenDTO> test, Long idUsuario)
      throws BiosLISBSException {

    StringBuilder sb = new StringBuilder();
  
    test.forEach(t -> {
       
      try {
        this.doAction(actionCode, t, idUsuario);
       
      } catch (BiosLISDAONotFoundException | BiosLISDAOException | BiosLISException | IOException e) {
        logger.debug("error  " + e.getMessage());
      // sb.append(t.toString()).append(" ").append(e.getLocalizedMessage()).append("");
       if(sb.toString().isEmpty()) {
            sb.append(e.getLocalizedMessage()).append("");
       }
      } catch (BiosLisExamenSignerException e) {
      }
    });

    if (!sb.toString().isEmpty()) {   
      String msg = sb.toString();
      logger.error("testServiceImpl  321 " + msg);
      throw new BiosLISBSException(msg);
    }
  }

  @Override
  public List<CfgGlosas> getTestGM(Integer idTest) throws BiosLISDAOException, BiosLISDAONotFoundException {
    return cfgTestDAO.getTestGM(idTest);
  }

  public CfgTestDAO getCfgTestDAO() {
    return cfgTestDAO;
  }

  public void setCfgTestDAO(CfgTestDAO cfgTestDAO) {
    this.cfgTestDAO = cfgTestDAO;
  }

  @Override
  public ExamenesResultadosDeUnaOrdenDTO getResultadoTest(Integer nroOrden, Integer idExamen, Integer idTest)
      throws BiosLISDAOException, BiosLISDAONotFoundException {
    return datosFichasExamenesTestDAO.getResultadoTest(nroOrden, idExamen, idTest);
  }

  @Override
 public List<CfgTest> getCalculatedTest() throws BiosLISDAOException, BiosLISDAONotFoundException {	  
    return cfgTestDAO.getCalculatedTest();
  }

  @Override
  public List<CfgTest> getFromFormula(String formula)
      throws BiosLISDAOException, BiosLISDAONotFoundException, BiosLISNotFoundException {

    FormulaEvaluador fe = new FormulaEvaluador();
    List<String> lst = fe.parse(formula);
    List<CfgTest> rpta = null;
    rpta = cfgTestDAO.getCfgTest(lst);

    if (rpta == null) {
      throw new BiosLISDAONotFoundException("No se encontraron tests asociados a la fórmula");
    }

    return rpta;

  }

  // Si el test buscado tiene formula se devuelve la lista de test relacionados.
  @Override
  public List<CfgTest> getRelFormulaTest(Integer idTest)
      throws BiosLISDAOException, BiosLISDAONotFoundException, BiosLISNotFoundException {

    // Buscando test que dependen de idtest
    logger.debug("Test en la formula de id:{}", idTest);
    // hashmap que contiene para cada id de test, el test y la lista de los test que
    // se usan en su cálculo.
    Map<Integer, HijosTest> hmTestConFormulas = this.getRelFormulaTest();

    // Si el test buscado tiene formula se devuelve la lista de test relacionados.
    if (hmTestConFormulas.get(idTest) != null) {
      return this.getRelFormulaTest().get(idTest).getHijos();
    }

    throw new BiosLISNotFoundException();
  }

  // Si el test buscado tiene formula se devuelve la lista de test relacionados.
  @Override
  public HijosTest getRelFormulaSonTest(Integer idTest)
      throws BiosLISDAOException, BiosLISDAONotFoundException, BiosLISNotFoundException {

    // Buscando test que dependen de idtest
    logger.debug("Test en la formula de id:{}", idTest);
    // hashmap que contiene para cada id de test, el test y la lista de los test que
    // se usan en su cálculo.
    return this.getRelFormulaTest().get(idTest);

  }

  // Obtiene los test cuyos resultados se calculan por formulas, además para cada
  // test se recupera la lista de test presentes en la formula
  public Map<Integer, HijosTest> getRelFormulaTest()
      throws BiosLISDAOException, BiosLISDAONotFoundException, BiosLISNotFoundException {

    Map<Integer, HijosTest> testDependientes = new HashMap<>();
    // Se recupera todos los test que son calculados por formula.
    List<CfgTest> lstCalculatedTest = this.getCalculatedTest();
    int n = lstCalculatedTest.size();
    String formula;
    for (int i = 0; i < n; i++) {
      // Para cada test con formula se encuentra los test involucrados en su cálculo.
      CfgTest test = lstCalculatedTest.get(i);
      formula = test.getCtFormula();
      logger.debug("Test {} Formula {}:", test.getCtAbreviado(), formula);
      HijosTest td = new HijosTest();
      td.setPadre(test);
      testDependientes.put(td.getPadre().getCtIdtest(), td);
      if (formula != null) {
        List<CfgTest> lstTestFromFormula = this.getFromFormula(formula);
        logger.debug("Para test: {}, se encontraron {} test en la formula", test.getCtAbreviado(),
            lstTestFromFormula.size());
        lstTestFromFormula.forEach(t -> td.getHijos().add(t));
      }
    }
    return testDependientes;
  }

  // Obtiene los test cuyos resultados se calculan por formulas y que dependen de
  // testId , además para cada
  // test se recupera la lista de test presentes en la formula
  @Override
  public Map<Integer, HijosTest> getRelFormulaTestDependsOn(Integer testId)
      throws BiosLISDAOException, BiosLISDAONotFoundException, BiosLISNotFoundException {

    Map<Integer, HijosTest> testDependientes = new HashMap<>();
    // Se recupera todos los test que son  formula.
    List<CfgTest> lstCalculatedTest = this.getCalculatedTest();
    int n = lstCalculatedTest.size();
    String formula;
   
    for (int i = 0; i < n; i++) {
    	
		      // Para cada test con formula se encuentra los test involucrados en su cálculo.
		      CfgTest test = lstCalculatedTest.get(i);
		      formula = test.getCtFormula();
		      logger.debug("Test {} Formula {}:", test.getCtAbreviado(), formula);
		      if (formula != null) {
		        List<CfgTest> lstTestFromFormula = this.getFromFormula(formula);
		        logger.debug("Para test: {}, se encontraron {} test en la formula", test.getCtAbreviado(),
		            lstTestFromFormula.size());
		        if (lstTestFromFormula.stream().filter(t -> t.getCtIdtest().equals(testId)).findAny().isPresent()) {
		          HijosTest td = new HijosTest();
		          td.setPadre(test);
		          testDependientes.put(td.getPadre().getCtIdtest(), td);
		          lstTestFromFormula.forEach(t -> td.getHijos().add(t));
		        }
		      }
    	
    }
  
    return testDependientes;
  }

  @Override
  public List<CfgTest> getDependentTest(Integer testId) {

    return null;
  }

  /*
   * Calcula las formulas que depende de este test. resultadoGenerador: test y su
   * resultado del cual dependen los hijos test lstResultadosDigitadosExamen: los
   * resultados de este examen que están digitados.
   * 
   */
  @Override
  public void calcular(ResultadoNumericoTestExamenOrdenDTO resultadoGenerador, HijosTest hijosTest,
      List<DatosFichasexamenestest> lstResultadosDigitadosExamen) {

    // Obtiene los resultados de los hijos que están ok.
    List<DatosFichasexamenestest> lstResultadosDigitados = this.valorizar(hijosTest, lstResultadosDigitadosExamen);
    Double valor = 0D;
  
    try {
      if (hijosTest != null) {
        valor = fe.eval(hijosTest.getPadre().getCtFormula(), lstResultadosDigitados,
            resultadoGenerador.getDF_NORDEN().longValueExact());
      } else {
        return;
      }
    } catch (BiosLisFormulaResultIsNaNException | BiosLISDAOException e1) {
      logger.debug("No se pudo calcular resultado para esta formula . Error:  ");
      // hijosTest.getPadre().getCtFormula(), e1.getMessage());
      // Se vuelve sin hacer nada.
      return;
    }
    ResultadoNumericoTestExamenOrdenDTO resultado = new ResultadoNumericoTestExamenOrdenDTO();
    try {
      NumberFormat numberFormatter = NumberFormat.getInstance(Locale.FRANCE);

      resultado.setCTR_CODIGO(Constante.CTR_FORMULA);
      resultado.setDF_NORDEN(resultadoGenerador.getDF_NORDEN());
      resultado.setDFE_IDEXAMEN(resultadoGenerador.getDFE_IDEXAMEN());
      resultado.setDFET_IDTEST(BigDecimal.valueOf(hijosTest.getPadre().getCtIdtest()));
     
      String paso = valor.toString().replace(".",",");
   
  
      if(numberFormatter.format(valor).equals("0")) {
          resultado.setDFET_RESULTADO("");  
         
      }else {    	 
          //resultado.setDFET_RESULTADO(numberFormatter.format(valor));
    	  resultado.setDFET_RESULTADO(paso);
          
      }
      resultado.setFechaDigita(BiosLisCalendarService.getInstance().getTS());
      resultado.setIdUsuarioDigita(resultadoGenerador.getIdUsuarioDigita());
      
      logger.debug("calcular: ResultadoNumericoTestExamenOrdenDTO a ingresar {}", resultado);

    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
       
      this.capturaResultadosDAO.updateResultadoTestExamenOrden(resultado);
    } catch (BiosLISDAOException | ParseException e) {
      logger.error("No se pudo actualizar test dependiente {}. Error {}", hijosTest.getPadre().getCtAbreviado(),
          e.getLocalizedMessage());
      e.printStackTrace();
    }
   
  }

  /*
   * Recorre los test que dependen y ve si ya está digitado, si es así lo agrega a
   * la rpta.
   * 
   */
  private List<DatosFichasexamenestest> valorizar(HijosTest hijosTest,
      List<DatosFichasexamenestest> lstResultadosDigitadosExamen) {
  

    List<DatosFichasexamenestest> rpta = new ArrayList<>();
    try {

    if(hijosTest != null) {
       if(hijosTest.getHijos().size() > 0) {
         
int contador = 0;
        for (CfgTest ct : hijosTest.getHijos()) {
        
          Optional<DatosFichasexamenestest> res = lstResultadosDigitadosExamen.stream()
              .filter(dfet -> dfet.getId().getDfetIdtest() == ct.getCtIdtest()).findAny();          
          if (res.isPresent() ) {
        	if(res.get().getDfetResultado() == null) {
        		contador ++;
        	}
            rpta.add(res.get());
          }
        
        }
        if(contador > 0) {
        	rpta = null;
        }
       }
      }
    
    } catch (Exception e) {
      e.printStackTrace();
      logger.debug(e.getMessage());

    }
  
    return rpta;
  }

  public CapturaResultadosDAO getCapturaResultadosDAO() {
    return capturaResultadosDAO;
  }

  public void setCapturaResultadosDAO(CapturaResultadosDAO capturaResultadosDAO) {
    this.capturaResultadosDAO = capturaResultadosDAO;
  }

  @Override
  public CfgTest setFormula(Integer testId, String formula, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISDAOException, BiosLISException {
    return this.cfgTestDAO.setFormula(testId, formula, idUsuario);

  }

  @Override
  public CfgTest getCfgTest(Integer testId) throws BiosLISDAONotFoundException, BiosLISDAOException {

    return this.cfgTestDAO.getTestById(testId);
  }

  @Override
  public List<CfgTestDTO> getTestByFiltroTest(FiltroTestDTO filtroTest) throws BiosLISDAOException {
    return this.cfgTestDAO.getTestByFiltroTest(filtroTest);
  }

  // No sé quien desarrolló esto pero lo dejé con manejo de exception (Marco
  // Caracciolo 06/01/2023)
  @Override
  public List<CfgTest> getTestNumericos() throws BiosLISDAOException {

    return this.cfgTestDAO.getTestResultadoNumerico();
  }

  @Override
  public CfgTest saveCond(CfgCondicionesformulasDTO cfgCond, String condicion) throws BiosLISDAOException {

    CfgCondicionesformulasId cfi = new CfgCondicionesformulasId(cfgCond.getId().getCcfIdtest(),
        cfgCond.getCcfNrcondicionanidada());

    CfgCondicionesformulas cf = new CfgCondicionesformulas();
    cf.setCcfCondicionoperador(cfgCond.getCcfCondicionoperador());
    cf.setCcfCondicionvar1(cfgCond.getCcfCondicionvar1());
    cf.setCcfCondicionvar2(cfgCond.getCcfCondicionvar2());
    cf.setCcfFallaestado(cfgCond.getCcfFallaestado());
    cf.setCcfFallanota(cfgCond.getCcfFallanota());
    cf.setCcfFallaresultado(cfgCond.getCcfFallaresultado());
    cf.setCcfNrcondicionanidada(cfgCond.getCcfNrcondicionanidada());
    cf.setCcfTestoantecedente(cfgCond.getCcfTestoantecedente());
    cf.setId(cfi);
    return this.cfgTestDAO.saveCond(cf, condicion);
  }

  @Override
  public List<CfgCondicionesformulas> getCondicionesTest(Integer idTest) throws BiosLISDAOException {
    return cfgCondicionesformulasDAO.getCondicionesTest(idTest);
  }

  @Override
  public ResultadoNumericoTestExamenOrdenDTO aplicarCondicion(ResultadoNumericoTestExamenOrdenDTO resultado,
      CfgCondicionesformulas t) throws BiosLISDAOException, BiosLISException {

    // determinar si condicion es por antecedente o test

    if (t.getCcfTestoantecedente().equals("T")) {
    } else if (t.getCcfTestoantecedente().equals("A")) {

      CondicionAntecedenteExecutor cae = new CondicionAntecedenteExecutor();
      cae.exec(resultado, t);

    } else {
      throw new BiosLISException("Tipo de condición de fórmula inválido.");
    }
    // si es por test verificar resultado (nro_orden,examen,test)
    // si es por antecedente verificar si orden la cumple (nro_orden,id_antecedente)

    return null;
  }

  @Override
  public List<String> getAntecedenteFromFormula(String formula) {

    FormulaEvaluador feval = new FormulaEvaluador();
    return feval.parseAntecedentesFormula(formula);
  }

  @Override
  public String validateFormula(String formula) throws BiosLisFormulaResultIsNaNException {
    FormulaEvaluador feval = new FormulaEvaluador();
    return feval.validateFormula(formula);
  }

@Override
public List<CRDetalleTestDTO> getDetallesTest(int idOrden, int idExam, int idTest) throws BiosLISDAOException {
	// TODO Auto-generated method stub
	return this.cfgTestDAO.getDetailTest(idOrden, idExam, idTest);
}

  /*****************************************************************************************************/

}
