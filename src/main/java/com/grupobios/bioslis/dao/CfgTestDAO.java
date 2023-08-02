/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.DaoHelper;
import com.grupobios.bioslis.common.TestHelper;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.AntecedentePacienteDTO;
import com.grupobios.bioslis.dto.CRDetalleTestDTO;
import com.grupobios.bioslis.dto.CfgTestDTO;
import com.grupobios.bioslis.dto.CfgTestExcel;
import com.grupobios.bioslis.dto.FiltroTestDTO;
import com.grupobios.bioslis.dto.TestExamenSeccionDto;
import com.grupobios.bioslis.dto.TestProcesoDTO;
import com.grupobios.bioslis.entity.CfgCondicionesformulas;
import com.grupobios.bioslis.entity.CfgExamenestest;
import com.grupobios.bioslis.entity.CfgGlosas;
import com.grupobios.bioslis.entity.CfgGlosastest;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.LogCfgtablas;

/**
 *
 * @author Nacho
 */
public class CfgTestDAO {

  private static Logger logger = LogManager.getLogger(CfgTestDAO.class);
  
  LogCfgTablasDAO logCfgTablasDAO;

  
  
  public LogCfgTablasDAO getLogCfgTablasDAO() {
	return logCfgTablasDAO;
}

public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
	this.logCfgTablasDAO = logCfgTablasDAO;
}

public void insertTest(CfgTest ct) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();    
      
      sesion.beginTransaction();
      sesion.save(ct);
      sesion.getTransaction().commit();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateTest(CfgTest ct) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      sesion.update(ct);
      sesion.getTransaction().commit();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgTest> getTests() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion
          .createQuery("select ct " + "from com.grupobios.bioslis.entity.CfgTest ct order by ct.ctSort asc, ct.ctAbreviado asc ");
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgTest> getTestbyCodigo(String codigo) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion
          .createQuery("select ct " + "from com.grupobios.bioslis.entity.CfgTest ct " + "where ct.ctCodigo = :codigo");
      query.setParameter("codigo", codigo);
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // Creado a partir del método anterior, solo que esta consulta coincidencias por
  // ctDescripcion y ctAbreviado.
  public List<CfgTest> getTestByLikeNombreAyD(String nombre) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select ct " + "from com.grupobios.bioslis.entity.CfgTest ct "
          + "where (translate(upper(ct.ctDescripcion), 'ÁÉÍÓÚ', 'AEIOU')) like :nombre "
          + "or (translate(upper(ct.ctAbreviado), 'ÁÉÍÓÚ', 'AEIOU')) like :nombre " + "order by ct.ctAbreviado asc");
      query.setParameter("nombre", "%" + nombre + "%");
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgTest> getTestFormulaByLikeNombre(String nombre) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select ct " + "from com.grupobios.bioslis.entity.CfgTest ct "
          + "left join fetch ct.cfgTiposderesultado ctr " + "where ct.ctDescripcion like :nombre "
          + "and ct.cfgTiposderesultado.ctrIdtiporesultado = 6 " + "order by ct.ctDescripcion asc");
      query.setParameter("nombre", "%" + nombre + "%");
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgTest> getTestBySeccion(int idSeccion, String nombre) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(
          "select ct " + "from com.grupobios.bioslis.entity.CfgTest ct " + "where ct.ctDescripcion like :nombre "
              + "and ct.cfgSecciones = :idSeccion " + "order by ct.ctDescripcion asc");
      query.setParameter("nombre", "%" + nombre + "%");
      query.setParameter("idSeccion", idSeccion);
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // Creado a partir del método getTestBySeccion, solo que esta consulta busca
  // solo por id seccion.
  public List<CfgTest> getTestBySeccionId(int idSeccion) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sql = "from CfgTest ct";
      if (idSeccion > 0) {
        sql += " where ct.cfgSecciones = :idSeccion";
      }
      sql += " order by ct.ctAbreviado asc";
      Query query = sesion.createQuery(sql);
      if (idSeccion > 0) {
        query.setParameter("idSeccion", idSeccion);
      }
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // Creado a partir del método getTestBySeccion, solo que esta consulta
  // coincidencias por ctDescripcion y ctAbreviado.
  public List<CfgTest> getTestBySeccionNombre(int idSeccion, String nombre) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select ct " + "from com.grupobios.bioslis.entity.CfgTest ct "
          + "where ((translate(upper(ct.ctDescripcion), 'ÁÉÍÓÚ', 'AEIOU')) like :nombre "
          + "or (translate(upper(ct.ctAbreviado), 'ÁÉÍÓÚ', 'AEIOU')) like :nombre) "
          + "and ct.cfgSecciones = :idSeccion " + "order by ct.ctAbreviado asc");
      query.setParameter("nombre", "%" + nombre + "%");
      query.setParameter("idSeccion", idSeccion);
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgTest> getAllTestBySeccionFormula(int idSeccion) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select ct " + "from com.grupobios.bioslis.entity.CfgTest ct "
          + "left join fetch ct.cfgTiposderesultado ctr " + "where ct.cfgSecciones = :idSeccion "
          + "and ct.cfgTiposderesultado.ctrIdtiporesultado = 6 " + "order by ct.ctDescripcion asc");
      query.setParameter("idSeccion", idSeccion);
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public CfgTest getTestById(int idTest) throws BiosLISDAONotFoundException, BiosLISDAOException {

    Session sesion = null;
    CfgTest test = new CfgTest();
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select ct " + "from com.grupobios.bioslis.entity.CfgTest ct "
          + "join fetch ct.cfgMuestras cm " + "where ct.ctIdtest = :idTest");
      query.setParameter("idTest", idTest);
      test = (CfgTest) query.uniqueResult();
      sesion.close();
      if (test == null) {
        logger.warn("No se encontró test {}", idTest);
        throw new BiosLISDAONotFoundException("No se encontró test " + idTest);
      }

    } catch (HibernateException he) {
      logger.error("Error al obtener test {}", idTest);
      throw new BiosLISDAOException("Error al obtener test {}" + idTest);
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }
    return test;
  }

  public List<CfgTest> getTestResultadoNumerico() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query query = sesion.createQuery(
          "select ct " + "from com.grupobios.bioslis.entity.CfgTest ct " + "left join fetch ct.cfgTiposderesultado ctr "
              + "where ctr.ctrIdtiporesultado =  3 " //+ "order by ct.ctDescripcion asc");
              + "order by ct.ctSort asc, ct.ctDescripcion asc");
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgTest> getTestResultadoFormula() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(
          "select ct " + "from com.grupobios.bioslis.entity.CfgTest ct " + "left join fetch ct.cfgTiposderesultado ctr "
              + "where ctr.ctrIdtiporesultado =  6 " + "order by ct.ctDescripcion asc");
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgGlosas> getTestGM(Integer idTest) throws BiosLISDAOException, BiosLISDAONotFoundException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query qry = sesion.createQuery(hqlgetTestGM);
      qry.setInteger("idTest", idTest);
      List<Object> rs = qry.list();
      sesion.close();
      int n = rs.size();
      if (n == 0) {
        throw new BiosLISDAONotFoundException("No existen valores de glosa.");
      }
      List<CfgGlosas> lstGlosas = new ArrayList<CfgGlosas>(rs.size());
      for (Object e : rs) {

        CfgGlosastest cg = (CfgGlosastest) e;
        lstGlosas.add(cg.getCfgGlosas());
      }

      return lstGlosas;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("Error al buscar glosas multiples de un test: ".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public List<CfgTest> getTestBySeccionExamen(int idSeccion, long idExamen) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sCond = "";
      if (idSeccion > 0) {
        sCond += " ct.ct_idseccion = :idSeccion and";
      }
      if (idExamen > 0) {
        sCond += " cet.cet_idexamen = :idExamen and";
      }
      int pos = sCond.lastIndexOf("and");
      if (pos != -1) {
        sCond = sCond.substring(0, pos);
        sCond = " where" + sCond;
      }
      String sql = "select * from cfg_test ct" + " join cfg_examenestest cet on cet.cet_idtest = ct.ct_idtest" + sCond
          + " order by ct.ct_descripcion asc";
      Query query = sesion.createSQLQuery(sql).addEntity(CfgTest.class);
      if (idSeccion > 0) {
        query.setInteger("idSeccion", idSeccion);
      }
      if (idExamen > 0) {
        query.setLong("idExamen", idExamen);
      }
      List<CfgTest> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgTest> getDependentTest() throws BiosLISDAOException {

    Session sesion = null;
    List<CfgTest> lst = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();

      return lst;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }
    // return lst;

  }

  public List<CfgTest> getCalculatedTest() throws BiosLISDAOException, BiosLISDAONotFoundException {

    Session sesion = null;
    List<CfgTest> lst = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query qry = sesion.createQuery(hqlGetCalculatedResultTest);
      lst = qry.list();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException(
          "Error al buscar test con tipo de refultado fórmula : ".concat(he.getLocalizedMessage()));

    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

    if (lst == null) {
      logger.warn("Test con resultado tipo fórmula no hay.");
      throw new BiosLISDAONotFoundException("Test con resultado tipo fórmula no hay.");
    }
    logger.debug("Hay {} test con resultado tipo fórmula.", lst.size());
    return lst;

  }

  public List<CfgTest> getCalculatedTest(Integer idExamen) throws BiosLISDAOException, BiosLISDAONotFoundException {

    Session sesion = null;
    List<CfgExamenestest> lst = null;
    List<CfgTest> lstTest = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query qry = sesion.createQuery(hqlGetCalculatedResultTestExamen);
      qry.setInteger("idExamen", idExamen);
      lst = qry.list();
      lstTest = lst.stream().map(cet -> cet.getCfgTest()).collect(Collectors.toList());
      sesion.close();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException(
          "Error al buscar test con tipo de resultado fórmula en un examen: ".concat(he.getLocalizedMessage()));

    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

    if (lstTest == null) {
      logger.warn("Test con resultado tipo fórmula no hay.");
      throw new BiosLISDAONotFoundException("Test con resultado tipo fórmula no hay.");
    }
    logger.debug("Hay {} test con resultado tipo fórmula.", lst.size());  
    return lstTest;

  }

  private static String hqlGetCalculatedResultTest = "select ct from CfgTest ct inner join ct.cfgTiposderesultado ctr where ctr.ctrCodigo like 'F' AND ct.ctFormula is not null ";


  private static String hqlGetCalculatedResultTestExamen = "select cet from CfgExamenestest cet inner join fetch cet.cfgTest ct inner join  ct.cfgTiposderesultado ctr  "
      + "where ctr.ctrCodigo like 'F' AND ct.ctFormula is not null AND cet.id.cetIdexamen =:idExamen";

  private static String hqlgetTestGM = "select cgt from CfgGlosastest cgt inner join fetch cgt.cfgGlosas cg inner join fetch cgt.cfgTest ct inner join fetch ct.cfgTiposderesultado ctr "
      + "where ct.ctIdtest = :idTest  " + " and ctr.ctrCodigo like 'GM'";

  private static String hqlGetTestFromList = "select ct from CfgTest ct where ct.ctIdtest in (:lst) ";

  public List<CfgTest> getCfgTest(List<String> lst) throws BiosLISDAOException, BiosLISNotFoundException {

    if (lst.isEmpty()) {
      throw new BiosLISNotFoundException("Se busca cfgTest con lista de id vacía ");
    }

    List<Integer> idLst = new ArrayList<>(lst.size());
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {
      lst.forEach(id -> idLst.add(TestHelper.prmFunction2Integer(id)));
      Query qry = sesion.createQuery(hqlGetTestFromList);
      qry.setParameterList("lst", idLst);
      List<CfgTest> lista = qry.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("Error al buscar lista de test  : ".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  public CfgTest setFormula(Integer testId, String formula, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISDAOException, BiosLISException {
	  CfgTest test = this.getTestById(testId);
    Session sesion = null;

    if (testId == null || formula == null) {

      throw new BiosLISException("Parametros no pueden ser null");
    }
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query qry = sesion.createQuery(hqlsetFormula);
      qry.setInteger("testId", testId);
      CfgTest ct = (CfgTest) qry.uniqueResult();
      if (ct == null) {
        sesion.getTransaction().rollback();
        throw new BiosLISDAONotFoundException("No se encontró test.");
      }
      ct.setCtFormula(formula);
      sesion.update(ct);
      sesion.getTransaction().commit();
      sesion.close();
      // se cambia formula en test y se agrega a log tablas
      logCfgTablasDAO.validarDatos(ct.getCtFormula(), test.getCtFormula(), idUsuario, "CT_FORMULA", testId, "CFG_TEST", test.getCtAbreviado());
      //***********************************
      return ct;
    } catch (HibernateException he) {
      logger.error("Error DAO buscando test {}", he.getLocalizedMessage());

      throw new BiosLISDAOException("Error DAO buscando test.");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  private static String hqlsetFormula = "select ct from CfgTest ct where ct.ctIdtest =:testId";

  @SuppressWarnings("unchecked")
  public List<CfgTestDTO> getTestByFiltroTest(FiltroTestDTO filtroTest) throws BiosLISDAOException {

    Session sesion = null;
    //String sQuery = DaoHelper.getSqlTestByFiltroTest(filtroTest);
    String sQuery = DaoHelper.getSqlTestByFiltroTest2(filtroTest);
    
    sQuery += " ORDER BY CT_SORT,CT_ABREVIADO asc ";
    logger.debug("Qry : \n{}", sQuery);
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query qry = sesion.createSQLQuery(sQuery).setResultTransformer(Transformers.aliasToBean(CfgTestDTO.class));
      if (sQuery.contains(":testId")) {
        qry.setInteger("testId", filtroTest.getTestId());
      }
      if (sQuery.contains(":labId")) {
        qry.setInteger("labId", filtroTest.getLabId());
      }
      if (sQuery.contains(":seccionId")) {
        qry.setInteger("seccionId", filtroTest.getSeccion());
      }
      if (sQuery.contains(":examenId")) {
          qry.setInteger("examenId", filtroTest.getExamenId());
      }

      List<CfgTestDTO> lista = qry.list();
      sesion.close();
      return lista;

    } catch (HibernateException he) {
      logger.error("No se pudo ejecutar ok consulta {}", he.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo ejecutar ok consulta {}".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  public List<CfgTest> getTestFiltro(String codigo, String nombre, String activo, int idSeccion)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      String sqlTestsFilter = "from CfgTest ct"

          + " where (translate(upper(ct.ctCodigo), 'ÁÉÍÓÚ', 'AEIOU')) like :codigo"
          + " and ((translate(upper(ct.ctDescripcion), 'ÁÉÍÓÚ', 'AEIOU')) like :nombre"
          + " or (translate(upper(ct.ctAbreviado), 'ÁÉÍÓÚ', 'AEIOU')) like :nombre)";

      /*
       * + " where ct.ctCodigo like :codigo" +
       * " and (ct.ctDescripcion like :nombre or ct.ctAbreviado like :nombre)";
       */
      if (idSeccion > 0) {
        sqlTestsFilter += " and ct.cfgSecciones = :idSeccion";
      }
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sqlTestsFilter += " and ct.ctActivo = 'S'";
      }
      sqlTestsFilter += " order by ct.ctSort asc, ct.ctAbreviado asc";
      Query query = sesion.createQuery(sqlTestsFilter);
      query.setParameter("codigo", "%" + codigo + "%");
      query.setParameter("nombre", "%" + nombre + "%");
      if (idSeccion > 0) {
        query.setInteger("idSeccion", idSeccion);
      }
      List<CfgTest> listTest = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public CfgTest saveCond(CfgCondicionesformulas cfgCond, String condicion) throws BiosLISDAOException {

    Session sesion = null;

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      sesion.save(cfgCond);     
      sesion.getTransaction().commit();      
      sesion.close();
      return null;
    } catch (HibernateException he) {
      logger.error("No se pudo guardar condición para test {}", he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // DAO optimizado para búsqueda de tests en Configuración de Procesos
  public List<TestProcesoDTO> obtenerTestsBusquedaProceso(int idSeccion, long idExamen) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sCond = "";
      if (idSeccion > 0) {
        sCond += " CT.CT_IDSECCION = :idSeccion AND";
      }
      if (idExamen > 0) {
        sCond += " CET.CET_IDEXAMEN = :idExamen AND";
      }
      int pos = sCond.lastIndexOf("AND");
      if (pos != -1) {
        sCond = sCond.substring(0, pos);
        sCond = " WHERE" + sCond;
      }
      String sql = "SELECT DISTINCT CT.CT_IDTEST, CT.CT_CODIGO, CT.CT_ABREVIADO, CT.CT_DESCRIPCION, CT.CT_ACTIVO,"
          + " CT.CT_DECIMALES, CSEC.CSEC_IDSECCION, CSEC.CSEC_DESCRIPCION, CMUE.CMUE_IDTIPOMUESTRA, CMUE.CMUE_PREFIJOTIPOMUESTRA"
          + " FROM CFG_TEST CT" + " JOIN CFG_SECCIONES CSEC ON CSEC.CSEC_IDSECCION = CT.CT_IDSECCION"
          + " JOIN CFG_MUESTRAS CMUE ON CMUE.CMUE_IDTIPOMUESTRA = CT.CT_IDTIPOMUESTRA" + sCond
          + " ORDER BY CT.CT_ABREVIADO";
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TestProcesoDTO.class));
      if (idSeccion > 0) {
        query.setInteger("idSeccion", idSeccion);
      }
      if (idExamen > 0) {
        query.setLong("idExamen", idExamen);
      }
      List<TestProcesoDTO> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error("No se pudo obtener test para procesos. ", he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgTestExcel> getTestParaExcel() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgTestExcel> listTest = null;
    try {
      sesion.beginTransaction();
      String sqlTestsFilter = "SELECT ct.CT_ACTIVO,ct.CT_CODIGO,ct.CT_ABREVIADO,ct.CT_DESCRIPCION,cm.CMUE_DESCRIPCION ,cl.CLAB_DESCRIPCION ,cs.CSEC_DESCRIPCION ,ce.CENV_DESCRIPCION "
          + ",cmet.CMET_DESCRIPCION ," + "ctr.CTR_DESCRIPCION ,cu.CUM_DESCRIPCION ,ct.CT_DECIMALES ,"
          + "CASE WHEN cvv.CVR_DIASHASTA IS NULL THEN 'N'" + "    ELSE 'S' END AS VALORES_REF"
          + ",ct.CT_VOLUMENMUESTRA ,cgef.CGEF_DESCRIPCION " + "FROM CFG_TEST ct "
          + "LEFT JOIN CFG_MUESTRAS cm ON cm.CMUE_IDTIPOMUESTRA = ct.CT_IDTIPOMUESTRA "
          + "LEFT JOIN CFG_SECCIONES cs ON cs.CSEC_IDSECCION = ct.CT_IDSECCION "
          + "LEFT JOIN CFG_ENVASES ce ON ce.CENV_IDENVASE = ct.CT_IDENVASE "
          + "LEFT JOIN CFG_TIPOSDERESULTADO ctr ON ctr.CTR_IDTIPORESULTADO = ct.CT_IDTIPORESULTADO "
          + "LEFT JOIN CFG_UNIDADESDEMEDIDA cu ON cu.CUM_IDUNIDADMEDIDA = ct.CT_IDUNIDADMEDIDA "
          + "LEFT JOIN CFG_GRUPOSEXAMENESFONASA cgef ON cgef.CGEF_IDGRUPOEXAMENFONASA = ct.CT_IDGRUPOEXAMENESFONASA "
          + "LEFT JOIN CFG_LABORATORIOS cl ON cl.CLAB_IDLABORATORIO = cs.CSEC_IDLABORATORIO "
          + "LEFT JOIN CFG_VALORESREFERENCIA cvv ON cvv.CVR_IDVALORREFERENCIA = ct.CT_IDTEST "
          + "LEFT JOIN CFG_METODOSTEST cmt ON cmt.CMT_IDTEST =ct.CT_IDTEST "
          + "LEFT JOIN CFG_METODOS cmet ON cmet.CMET_IDMETODO = cmt.CMT_IDMETODO " + "ORDER BY 3";

      Query query = sesion.createSQLQuery(sqlTestsFilter)
          .setResultTransformer(Transformers.aliasToBean(CfgTestExcel.class));

      listTest = query.list();
      sesion.getTransaction().commit();
      sesion.close();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listTest;
  }

  public CfgTest getTestById(int idTest, Session sesion) throws BiosLISDAONotFoundException, BiosLISDAOException {
    CfgTest test = null;
    try {
      Query query = sesion.createQuery("select ct " + "from com.grupobios.bioslis.entity.CfgTest ct "
          + "join fetch ct.cfgMuestras cm " + "where ct.ctIdtest = :idTest");
      query.setParameter("idTest", idTest);
      test = (CfgTest) query.uniqueResult();
//       sesion.close();
      if (test == null) {
        logger.warn("No se encontró test {}", idTest);
        throw new BiosLISDAONotFoundException("No se encontró test " + idTest);
      }

    } catch (HibernateException he) {
      logger.error("Error al obtener test {}", idTest);
      throw new BiosLISDAOException("Error al obtener test {}" + idTest);
    } finally {
//      if (sesion != null && sesion.isOpen()) {
//        sesion.close();
//      }

    }
    return test;
  }

  // Creado a partir del método getTestBySeccion, solo que esta consulta busca
  // solo por id seccion.
  public List<TestExamenSeccionDto> getTestBySeccionIdNativo(int idSeccion) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sql = sqlGetTestsExamenesBySeccion;
      Query query = sesion.createSQLQuery(sql);
      query.setParameter("idSec", idSeccion).setResultTransformer(Transformers.aliasToBean(TestExamenSeccionDto.class));
      List<TestExamenSeccionDto> listTest = query.list();
      sesion.close();
      return listTest;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }
  public List<CRDetalleTestDTO> getDetailTest(int idOrden, int idExam, int idTest) throws BiosLISDAOException {

	  Session sesion = HibernateUtil.getSessionFactory().openSession();
	  List<CRDetalleTestDTO> detalletest = null ;
	    try {
	        String sql = sqlGetDetailTest;
	        Query query = sesion.createSQLQuery(sql);
	        query.setParameter("nOrden", idOrden);
	        query.setParameter("idExamen", idExam);
	        query.setParameter("idTest", idTest);
	        query.setResultTransformer(Transformers.aliasToBean(CRDetalleTestDTO.class));

	        detalletest = query.list();
	        sesion.close();
	    } catch (HibernateException he) {
	      logger.error("No se pudo guardar condición para test {}", he.getLocalizedMessage());
	      throw new BiosLISDAOException(he.getLocalizedMessage());
	    } finally {
	      if (sesion != null && sesion.isOpen()) {
	        sesion.close();
	      }
	    }
	    return detalletest;
	  }
  public List<CfgTestDTO> getTestByExamen(long idExamen) throws BiosLISDAOException {

	  Session sesion = HibernateUtil.getSessionFactory().openSession();
	  List<CfgTestDTO> detalletest = null ;
	    try {
	        String sql = sqlgetTestByExamen;
	        Query query = sesion.createSQLQuery(sql);
	        query.setParameter("idExamen", idExamen);
	        query.setResultTransformer(Transformers.aliasToBean(CfgTestDTO.class));

	        detalletest = query.list();
	        sesion.close();
	    } catch (HibernateException he) {
	      logger.error( he.getLocalizedMessage());
	      throw new BiosLISDAOException(he.getLocalizedMessage());
	    } finally {
	      if (sesion != null && sesion.isOpen()) {
	        sesion.close();
	      }
	    }
	    return detalletest;
	  }
  public List<AntecedentePacienteDTO> getAntecedentesByExamen(long idExamen) throws BiosLISDAOException {

	  Session sesion = HibernateUtil.getSessionFactory().openSession();
	  List<AntecedentePacienteDTO> detalletest = null ;
	    try {
	        String sql = sqlgetAntecedentesByExamen;
	        Query query = sesion.createSQLQuery(sql);
	        query.setParameter("idExamen", idExamen);
	        query.setResultTransformer(Transformers.aliasToBean(AntecedentePacienteDTO.class));

	        detalletest = query.list();
	        sesion.close();
	    } catch (HibernateException he) {
	      logger.error( he.getLocalizedMessage());
	      throw new BiosLISDAOException(he.getLocalizedMessage());
	    } finally {
	      if (sesion != null && sesion.isOpen()) {
	        sesion.close();
	      }
	    }
	    return detalletest;
	  }

  private static String sqlGetTestsExamenesBySeccion = "SELECT ct.CT_IDTEST as \"ctIdtest\" ,"
      + "cs.CSEC_IDSECCION as \"csecIdseccion\"," + "cmue.CMUE_IDTIPOMUESTRA as \"cmueIdtipomuestra\","
      + "cmet.CMET_IDMETODO as \"cmetIdmetodo\"," + "ct.CT_CODIGO as \"ctCodigo\","
      + "ct.CT_ABREVIADO as \"ctAbreviado\"," + "cmue.CMUE_DESCRIPCIONABREVIADA as \"cmueDescripcionabreviada\","
      + "cmue.CMUE_COLOR as \"cmueColor\"," + "cs.CSEC_DESCRIPCION as \"csecDescripcion\","
      + "cs.CSEC_COLOR as \"csecColor\"," + "cmet.CMET_DESCRIPCION as \"cmetDescripcion\"" + " FROM CFG_TEST ct "
      + "INNER JOIN CFG_SECCIONES cs ON (ct.CT_IDSECCION = cs.CSEC_IDSECCION ) "
      + "INNER JOIN CFG_MUESTRAS cmue ON (ct.CT_IDTIPOMUESTRA = cmue.CMUE_IDTIPOMUESTRA) "
      + "INNER JOIN CFG_METODOSTEST  cmt ON (cmt.CMT_IDTEST = ct.CT_IDTEST) "
      + "INNER JOIN CFG_METODOS  cmet ON (cmet.CMET_IDMETODO = cmt.CMT_IDMETODO) "
      + "WHERE ct.CT_IDSECCION = :idSec or  0 = :idSec " + "ORDER BY 1,2,3,4";
  
  private static String sqlGetDetailTest = ""
  		+ "SELECT "
  		+ "CE.CE_ABREVIADO AS NOMBREXAMEN, "
  		+ "	CM.CMUE_DESCRIPCIONABREVIADA AS NOMBREMUESTRA, "
  		+ "	CTR.CTR_DESCRIPCION AS TIPODESULTADO, "
  		+ "	DFET.DFET_RESULTADO AS RESULTADO,"
  		+ "	DFET.DFET_RCRITICO AS VALORCRITICO, "
  		+ "	CT.CT_CODIGO AS CODIGO, "
  		+ "	CM.CMUE_PREFIJOTIPOMUESTRA AS PREFIJO "
  		+ "FROM DATOS_FICHASEXAMENESTEST DFET "
  		+ "INNER JOIN CFG_EXAMENES CE ON DFET.DFET_IDEXAMEN = CE.CE_IDEXAMEN  "
  		+ "INNER JOIN CFG_TEST CT ON DFET.DFET_IDTEST = CT.CT_IDTEST "
  		+ "LEFT JOIN CFG_MUESTRAS CM ON  CE.CE_IDGRUPOMUESTRA = CM.CMUE_IDGRUPOMUESTRA  "
  		+ "INNER JOIN CFG_TIPOSDERESULTADO CTR ON CT.CT_IDTIPORESULTADO = CTR.CTR_IDTIPORESULTADO  "
  		+ "WHERE DFET_NORDEN = :nOrden AND DFET_IDEXAMEN = :idExamen AND DFET_IDTEST = :idTest";


private static String sqlgetTestByExamen = ""
  		+ " SELECT ct.CT_ABREVIADO CT_ABREVIADO,ct.CT_DESCRIPCION CT_DESCRIPCION,ct.CT_SORT CT_SORT"
  		+ " FROM CFG_EXAMENESTEST cet"
  		+ " INNER JOIN CFG_TEST ct ON ct.CT_IDTEST = cet.CET_IDTEST "
  		+ " WHERE cet.CET_IDEXAMEN = :idExamen";
private static String sqlgetAntecedentesByExamen = ""
  		+ "SELECT ca.CA_IDANTECEDENTE CA_IDANTECEDENTE,ca.CA_DESCRIPCION CA_DESCRIPCION FROM CFG_EXAMENESTEST cet "
  		+ "INNER JOIN CFG_ANTECEDENTESTEST cat ON cet.CET_IDTEST = cat.CAT_IDTEST "
  		+ "INNER JOIN CFG_ANTECEDENTES ca ON cat.CAT_IDANTECEDENTE = ca.CA_IDANTECEDENTE "
  		+ "WHERE cet.CET_IDEXAMEN = :idExamen";

}
