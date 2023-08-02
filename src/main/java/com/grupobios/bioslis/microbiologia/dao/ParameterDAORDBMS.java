package com.grupobios.bioslis.microbiologia.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;

public class ParameterDAORDBMS implements ParameterDAO {

    static Logger log = LogManager.getLogger(ParameterDAORDBMS.class.getName());
  @Override
  public HashMap<String, Object> getSearchParameters() {
    HashMap<String, Object> result = new HashMap<String, Object>() {
      {
        put("documentType", getDocumentTypeOptions());
        put("section", getSectionOptions());
        put("exam", getExamOptions());
        put("service", getServiceOptions());
        put("attentionType", getAttentionTypeOptions());
      }
    };
    return result;
  }

  @Override
  public List<String> getBodyPartParameters() {
    ArrayList<String> result = new ArrayList<String>(); 
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session
          .createSQLQuery("SELECT BIOSLIS.CFG_BACZONACUERPO.CBZC_DESCRIPCION FROM BIOSLIS.CFG_BACZONACUERPO");
      for (Object bodyPart : query.list()) {
        if (bodyPart != null) {
          result.add(bodyPart.toString());
        }
      }
    }catch(Exception e) {
        e.printStackTrace();
    } finally {
      session.close();  
    }
    return result;
  }

  @Override
  public List<String> getSampleTypeParameters() {
      ArrayList<String> result = new ArrayList<String>();

    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_MUESTRAS.CMUE_DESCRIPCION FROM BIOSLIS.CFG_MUESTRAS");
      for (Object sampleType : query.list()) {
        if (sampleType != null) {
          result.add(sampleType.toString());
        }
      }
    }catch(Exception e) {
        e.printStackTrace();
    } finally {    
      session.close();
    }
    return result;
  }

  @Override
  public HashMap<String, Object> getNewTestParameters(String testName) {
    HashMap<String, Object> result = new HashMap<String, Object>();

    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_TEST.CT_IDTEST, BIOSLIS.CFG_TIPOSDERESULTADO.CTR_DESCRIPCION, BIOSLIS.CFG_TEST.CT_CODIGO "
              + "FROM BIOSLIS.CFG_TEST " + "INNER JOIN BIOSLIS.CFG_TIPOSDERESULTADO "
              + "ON BIOSLIS.CFG_TEST.CT_IDTIPORESULTADO = BIOSLIS.CFG_TIPOSDERESULTADO.CTR_IDTIPORESULTADO "
              + "WHERE BIOSLIS.CFG_TEST.CT_ABREVIADO = :testName");
      query.setParameter("testName", testName);

      Object[] testData = (Object[]) query.uniqueResult();
      session.close();
      
      result.put("testId", testData[0] == null ? null : testData[0].toString());
      result.put("examId", null);
      result.put("orderId", null);
      result.put("testName", testName);
      result.put("result", "");
      result.put("resultType", decodeTestResultType(testData[1].toString()));
      result.put("resultOptions", getTestResultOptions((BigDecimal) testData[0]));
      result.put("status", "");
      result.put("statusOptions", getTestStatusOptions());
      result.put("sampleType", null);
      result.put("testCode", testData[2]  == null ? null : testData[2].toString());
      result.put("samplePrefix", null);
      result.put("bodyPart", null);
      result.put("resultDate", null);
      result.put("seedingDate", null);
      result.put("events", new ArrayList<HashMap<String, String>>());
      result.put("isCulture", null);
      
    } catch(Exception e) {
      e.printStackTrace();
      if(session.isOpen()) {
          session.close();
      }
    }

    return result;
  }

  @Override
  public HashMap<String, Object> getAntibiogramParameters() {
    HashMap<String, Object> result = new HashMap<String, Object>();

    result.put("interpretations", getInterpretations());
    result.put("microorganism", getMicroorganism());
    result.put("antibiogramTypes", getAntibiograms());
    result.put("infectionTypes", getInfectionType());
    result.put("coloniesCount", getColoniescount());
    result.put("antibioticList", getAntibioticList());
    result.put("methodList", getResistanceMethodList());
    result.put("methodResults", getMethodResultList());

    return result;
  }

  @Override
  public List<String> getAntibiogramAntibiotics(String antibiogramName) {
    ArrayList<String> result = new ArrayList<String>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_BACANTIBIOTICOS.CBA_DESCRIPCION "
          + "FROM BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT " + "INNER JOIN BIOSLIS.CFG_BACANTIBIOTICOS "
          + "ON BIOSLIS.CFG_BACANTIBIOTICOS.CBA_IDBACANTIBIOTICO = BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_IDBACANTIBIOTICO "
          + "INNER JOIN BIOSLIS.CFG_BACANTIBIOGRAMAS "
          + "ON BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_IDBACANTIBIOGRAMA = BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_IDBACANTIBIOGRAMA "
          + "WHERE BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_DESCRIPCION = :antibiogramName");
      query.setParameter("antibiogramName", antibiogramName);
      for (Object parameter : query.list()) {
        result.add(parameter.toString());
      }
    } finally {
      session.close();
    }
    return result;
  }

  private List<String> getInterpretations() {
    ArrayList<String> result = new ArrayList<String>();
     Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.LDV_BACINTERPRETACIONES.LDVBI_INTERPRETACION " + "FROM BIOSLIS.LDV_BACINTERPRETACIONES");
      for (Object parameter : query.list()) {
        result.add(parameter.toString());
      }
    }catch(Exception e) {
        e.printStackTrace();
    } finally {  
      session.close();
    }
    return result;
  }

  private List<String> getResistanceMethodList() {
    ArrayList<String> result = new ArrayList<String>();  
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_DESCRIPCION "
          + "FROM BIOSLIS.CFG_BACMARCADORESRESISTENCIA");
      for (Object parameter : query.list()) {
        result.add(parameter.toString());
      }
    }catch(Exception e ) {
        e.printStackTrace();
    } finally {
      session.close();
    }
    return result;
  }

  private List<String> getMethodResultList() {
    ArrayList<String> result = new ArrayList<String>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.LDV_BACRESULTADOSMARCADORR.LDVBRMR_DESCRIPCIÓN " + "FROM BIOSLIS.LDV_BACRESULTADOSMARCADORR");
      for (Object parameter : query.list()) {
        result.add(parameter.toString());
      }
    }catch(Exception e) {
        e.printStackTrace();
    } finally {
      session.close(); 
    }
    return result;
  }

  private List<String> getAntibioticList() {
    ArrayList<String> result = new ArrayList<String>(); 
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session
          .createSQLQuery("SELECT BIOSLIS.CFG_BACANTIBIOTICOS.CBA_DESCRIPCION " + "FROM BIOSLIS.CFG_BACANTIBIOTICOS");
      for (Object parameter : query.list()) {
        result.add(parameter.toString());
      }
    }catch(Exception e) {
        e.printStackTrace();
    } finally {
      session.close(); 
    }
    return result;
  }

  private List<String> getColoniescount() {
    ArrayList<String> result = new ArrayList<String>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_DESCRIPCION " + "FROM BIOSLIS.CFG_BACRECUENTOCOLONIAS");
      for (Object parameter : query.list()) {
        result.add(parameter.toString());
      }
    }catch(Exception e) {
        e.printStackTrace();
    } finally {
      session.close();
    }
    return result;
  }

  private List<String> getInfectionType() {
    ArrayList<String> result = new ArrayList<String>();  
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACTIPOSINFECCIONES.CBTI_DESCRIPCION " + "FROM BIOSLIS.CFG_BACTIPOSINFECCIONES");
      for (Object parameter : query.list()) {
        result.add(parameter.toString());
      }
    }catch(Exception e){
        e.printStackTrace();
    } finally { 
      session.close();
    }
    return result;
  }

  private List<String> getMicroorganism() {
    ArrayList<String> result = new ArrayList<String>(); 
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_DESCRIPCION " + "FROM BIOSLIS.CFG_BACMICROORGANISMOS");
      for (Object parameter : query.list()) {
        result.add(parameter.toString());
      }
    }catch(Exception e) {
        e.printStackTrace();
    } finally {  
      session.close();
    }
    return result;
  }

  private List<String> getAntibiograms() {
    ArrayList<String> result = new ArrayList<String>(); 
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {    
      Query query = session
          .createSQLQuery("SELECT BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_DESCRIPCION " + "FROM BIOSLIS.CFG_BACANTIBIOGRAMAS");
      for (Object parameter : query.list()) {
        result.add(parameter.toString());
      }
    }catch(Exception e) {
        e.printStackTrace();
    } finally { 
      session.close();
    }
    return result;
  }

  private String decodeTestResultType(String flag) {
    if ("GLOSA".equals(flag)) {
      return "select";
    } else {
      return "text";
    }
  }

  private ArrayList<HashMap<String, Object>> getTestResultOptions(BigDecimal testId) {
 
    Session session = HibernateUtil.getSessionFactory().openSession();
    ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
    try {
      Query query = session
          .createSQLQuery("SELECT BIOSLIS.CFG_GLOSAS.CG_DESCRIPCION, BIOSLIS.CFG_GLOSASTEST.CGT_ESGLOSACRITICA "
              + "FROM BIOSLIS.CFG_GLOSASTEST " + "INNER JOIN BIOSLIS.CFG_GLOSAS "
              + "ON  BIOSLIS.CFG_GLOSASTEST.CGT_IDGLOSATEST = BIOSLIS.CFG_GLOSAS.CG_IDGLOSA "
              + "WHERE BIOSLIS.CFG_GLOSASTEST.CGT_IDTEST = :testId");
      query.setParameter("testId", testId);
      for (Object option : query.list()) {
        Object[] resultOptionsData = (Object[]) option;
        result.add(new HashMap<String, Object>() {
          {
            put("name", resultOptionsData[0].toString());
            put("critical", "S".equals(resultOptionsData[0].toString()));
          }
        });
      }
    }catch(Exception e) {
        e.printStackTrace();
    } finally {
      session.close();   
    }
    return result;
  }

  private ArrayList<String> getTestStatusOptions() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    ArrayList<String> result = new ArrayList<String>();
    try {
      Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_ESTADOSRESULTADOSTEST.CERT_DESCRIPCIONESTADOTEST "
          + "FROM BIOSLIS.CFG_ESTADOSRESULTADOSTEST");
      for (Object option : query.list()) {
        result.add(option.toString());
      }
    } finally {
      session.close();
    }
    return result;
  }

  private HashMap<String, String> getOptionsForQuery(String queryString) {
    HashMap<String, String> result = new HashMap<String, String>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(queryString);
      for (Object parameter : query.list()) {
        if (((Object[]) parameter)[0] != null && ((Object[]) parameter)[1] != null) {
          result.put(((Object[]) parameter)[0].toString(), ((Object[]) parameter)[1].toString());
        }
      }
    }catch(Exception e) {
     log.error("error en getOptionsForQuery " +e.getMessage());    
   e.printStackTrace();
    } finally {  
      session.close();
    }
    return result;
  }

  private HashMap<String, String> getDocumentTypeOptions() {
    String queryString = "SELECT BIOSLIS.LDV_TIPOSDOCUMENTOS.LDVTD_IDTIPODOCUMENTO, BIOSLIS.LDV_TIPOSDOCUMENTOS.LDVTD_DESCRIPCION "
        + "FROM BIOSLIS.LDV_TIPOSDOCUMENTOS";
    return getOptionsForQuery(queryString);
  }

  private HashMap<String, String> getSectionOptions() {
    String queryString = "SELECT BIOSLIS.CFG_SECCIONES.CSEC_IDSECCION, BIOSLIS.CFG_SECCIONES.CSEC_DESCRIPCION "
        + "FROM BIOSLIS.CFG_SECCIONES";
    return getOptionsForQuery(queryString);
  }

  private HashMap<String, String> getExamOptions() {
    String queryString = "SELECT BIOSLIS.CFG_EXAMENES.CE_IDEXAMEN,  BIOSLIS.CFG_EXAMENES.CE_ABREVIADO "
        + "FROM BIOSLIS.CFG_EXAMENES " + "WHERE BIOSLIS.CFG_EXAMENES.CE_ACTIVO = 'S'";
    return getOptionsForQuery(queryString);
  }

  private HashMap<String, String> getServiceOptions() {
    String queryString = "SELECT BIOSLIS.CFG_SERVICIOS.CS_IDSERVICIO,  BIOSLIS.CFG_SERVICIOS.CS_DESCRIPCION "
        + "FROM BIOSLIS.CFG_SERVICIOS " + "WHERE BIOSLIS.CFG_SERVICIOS.CS_ACTIVO = 'S'";
    return getOptionsForQuery(queryString);
  }

  private HashMap<String, String> getAttentionTypeOptions() {
    String queryString = "SELECT BIOSLIS.CFG_TIPOATENCION.CTA_IDTIPOATENCION,  BIOSLIS.CFG_TIPOATENCION.CTA_DESCRIPCION "
        + "FROM BIOSLIS.CFG_TIPOATENCION " + "WHERE BIOSLIS.CFG_TIPOATENCION.CTA_ACTIVO = 'S'";
    return getOptionsForQuery(queryString);
  }
}
