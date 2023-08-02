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
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.microbiologia.entity.MBAntibiogram;

public class MBAntibiogramDAORDBMS implements MBAntibiogramDAO {

  static Logger log = LogManager.getLogger(MBAntibiogramDAORDBMS.class.getName());

  public MBAntibiogram getAntibiogramByIds(int orderId, int examId, int testId) {
    HashMap<String, String> antibiogramData = getAntibiogramData(orderId, examId, testId);
    if (antibiogramData == null) {
      return null;
    }

    return new MBAntibiogram(Integer.toString(orderId), Integer.toString(examId), Integer.toString(testId),
        antibiogramData.get("colonyCount"), antibiogramData.get("infectionType"),
        getAntibioticList(orderId, examId, testId));
  }

  @SuppressWarnings("unchecked")
  public void updateOrCreate(MBAntibiogram antibiogram) {
    List<String> storedMicroorganism = getStoredAntibiogramList(antibiogram);

    for (int i = 1; i <= 4; i++) { // es el numero de aislamientos el cual por ahora es hasta 4 --rara ves se llega
                                   // a pasar 4 veces.. posiblemente s dejara dinamico en algun momento
      HashMap<String, Object> antibioticList = (HashMap<String, Object>) antibiogram.getAntibioticList().get(i);

      Boolean dato = antibioticList.get("microorganism") == null ? false : true;
      try {
        // if (((HashMap<String, String>)
        // antibioticList.get("microorganism")).containsKey("name")) {
        if (dato) {
          if (!antibiogramListIsCreated(antibiogram, i)) {
            createAntibiogramList(antibiogram, i);
            createOrUpdateAntibioticForAntibiogramList(antibiogram, i);
            createOrUpdateMethodForAntibiogramList(antibiogram, i);
          } else {
            updateAntibiogramList(antibiogram, i);
            createOrUpdateAntibioticForAntibiogramList(antibiogram, i);
            storedMicroorganism.remove(storedMicroorganism
                .indexOf(((HashMap<String, String>) antibioticList.get("microorganism")).get("name").toString()));
            createOrUpdateMethodForAntibiogramList(antibiogram, i);
          }

          // createOrUpdateMethodForAntibiogramList(antibiogram, i);

        }
      } catch (Exception e) {
        log.error("error en updateOrCreate " + e.getMessage());
        ;
      }
    }

    deleteStoredAntibiograms(antibiogram, storedMicroorganism); // elimina si lo encuentra agregado y este se elimino
                                                                // desde el front

  }

  @SuppressWarnings("unchecked")
  public String getAntibioticResistance(String antibioticId, String microorganismId, String method, String value) {
    String result = "";
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "SELECT CBAH_SENSIBLEVALOR, CBAH_SENSIBLEINTERMEDIOSIGNO, CBAH_RESISTENTEVALOR, CBAH_INTERMEDIORESISTENTESIGNO "
              + " FROM BIOSLIS.CFG_BACANTIBIOTICOSHALO INNER JOIN BIOSLIS.CFG_BACANTIBIOTICOS ON "
              + " BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_IDBACANTIBIOTICO = BIOSLIS.CFG_BACANTIBIOTICOS.CBA_IDBACANTIBIOTICO "
              + " INNER JOIN BIOSLIS.CFG_BACMICROORGANISMOS ON "
              + " BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_IDBACMICROORGANISMO = BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_IDBACMICROORGANISMO "
              + " WHERE CBA_DESCRIPCION = :abId AND CBMO_DESCRIPCION = :moId AND CBAH_IDMETODOEVALUACIONR = :methodId ");
      query.setParameter("moId", microorganismId);
      query.setParameter("abId", antibioticId);
      query.setParameter("methodId", method);
      Object[] resistance = (Object[]) query.uniqueResult();

      if (this.compareValues(resistance[0].toString(), resistance[1].toString(), value)) {
        result = "SENSIBLE";
      } else {
        if (this.compareValues(value, resistance[3].toString(), resistance[2].toString())) {
          result = "RESISTENTE";
        } else {
          result = "INTERMEDIO";
        }
      }
    } catch (Exception ex) {
      result = "";
    } finally {
      session.close();
    }
    return result;
  }

  private Boolean compareValues(String val1, String operator, String val2) {
    if ("<".equals(operator))
      return Float.parseFloat(val1) < Float.parseFloat(val2);
    if ("<=".equals(operator))
      return Float.parseFloat(val1) <= Float.parseFloat(val2);
    if (">".equals(operator))
      return Float.parseFloat(val1) > Float.parseFloat(val2);
    if (">=".equals(operator))
      return Float.parseFloat(val1) >= Float.parseFloat(val2);
    return false;
  }

  private HashMap<String, String> getAntibiogramData(int orderId, int examId, int testId) {
    Object[] data = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACRECUENTOCOLONIAS, BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACTIPOINFECCION "
              + "FROM BIOSLIS.DATOS_FICHASEXAMENESTESTMO " + "WHERE "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN = :orderId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN = :examId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST = :testId");
      query.setParameter("orderId", orderId);
      query.setParameter("examId", examId);
      query.setParameter("testId", testId);
      data = (Object[]) query.list().get(0);
      /*
       * if (query.list().size() > 0) { data = (Object[]) query.list().get(0); }
       */
    } catch (Exception e) {
      log.error("error en getAntibiogramData " + e.getMessage());
    } finally {
      session.close();
    }

    if (data == null) {
      return null;
    }

    String colonycount = data[0] == null ? "" : getColonyCountName((BigDecimal) data[0]);
    String infectionType = data[1] == null ? "" : getInfectionTypeName((BigDecimal) data[1]);

    return new HashMap<String, String>() {
      {
        put("colonyCount", colonycount);
        put("infectionType", infectionType);
      }
    };

  }

  @SuppressWarnings("unchecked")
  private HashMap<Integer, Object> getAntibioticList(int orderId, int examId, int testId) {
    List<Object> data = null;
    HashMap<Integer, Object> result = new HashMap<Integer, Object>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {

      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACMICROORGANISMO, BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACANTIBIOGRAMA "
              + "FROM BIOSLIS.DATOS_FICHASEXAMENESTESTMO " + "WHERE "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN = :orderId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN = :examId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST = :testId");
      query.setParameter("orderId", orderId);
      query.setParameter("examId", examId);
      query.setParameter("testId", testId);
      data = (List<Object>) query.list();
      session.close();
      int index = 1;
      for (Object register : data) {
        Object[] registerData = (Object[]) register;
        String nameMicroorganismo = getMicroorganismName((BigDecimal) registerData[0]);
        String nameAntibiograma = getAntibiogramName((BigDecimal) registerData[1]);

        result.put(index, new HashMap<String, Object>() {
          {
            put("microorganism", new HashMap<String, Object>() {
              {
                put("name", nameMicroorganismo);
                put("id", nameMicroorganismo);
              }
            });
            put("antibiogram", new HashMap<String, Object>() {
              {
                put("name", registerData[1] == null ? "" : nameAntibiograma);
                put("id", registerData[1] == null ? "" : nameAntibiograma);
              }
            });
            put("antibiotics", getAntibiotics(orderId, examId, testId, (BigDecimal) registerData[0]));
            put("methods", getMethods(orderId, examId, testId, (BigDecimal) registerData[0]));
          }
        });
        index++;
      }

      for (int i = index; i <= 4; i++) {
        result.put(i, new HashMap<String, Object>() {
          {
            put("microorganism", new HashMap<String, Object>());
            put("antibiogram", new HashMap<String, Object>());
            put("antibiotics", new ArrayList<String>());
            put("methods", new ArrayList<String>());
          }
        });
      }
    } catch (Exception e) {
      log.error("error en getAntibioticList" + e.getMessage());
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private ArrayList<Object> getMethods(int orderId, int examId, int testId, BigDecimal microorganismId) {
    BigDecimal methodListId = getMethodListId(orderId, examId, testId, microorganismId);
    ArrayList<Object> result = new ArrayList<Object>();
    if (methodListId == null) {
      return result;
    }
    List<Object> data = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {

      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_DESCRIPCION, BIOSLIS.LDV_BACRESULTADOSMARCADORR.LDVBRMR_DESCRIPCIÓN "
              + "FROM BIOSLIS.DATOS_BACTESTMOMARCADORESR " + "INNER JOIN BIOSLIS.CFG_BACMARCADORESRESISTENCIA "
              + "ON BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDMARCADORRESISTENCIA = BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_IDMARCADORRESISTENCIA "
              + "INNER JOIN BIOSLIS.LDV_BACRESULTADOSMARCADORR "
              + "ON BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDRESULTADOMARCADORR = BIOSLIS.LDV_BACRESULTADOSMARCADORR.LDVBRMR_IDRESULTADOMARCADORR "
              + "WHERE BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDTESTMOMARCADORR = :methodListId");

      query.setParameter("methodListId", methodListId);

      /*
       * if (query.list().size() > 0) { data = (List<Object>) query.list();
       */
      data = (List<Object>) query.list();
      if (data.size() > 0) {
        for (Object register : data) {
          Object[] registerData = (Object[]) register;
          result.add(new HashMap<String, Object>() {
            {
              put("method", registerData[0].toString());
              put("result", registerData[1].toString());
            }
          });

        }
      }
    } catch (Exception e) {
      log.error("error en getMethods " + e.getMessage());
    } finally {
      session.close();
    }

    return result;
  }

  @SuppressWarnings("unchecked")
  private ArrayList<Object> getAntibiotics(int orderId, int examId, int testId, BigDecimal microorganismId) {
    ArrayList<Object> result = new ArrayList<Object>();
    List<Object> data = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACANTIBIOTICOS.CBA_DESCRIPCION, BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_CIM, "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_KIRBYBAUER, BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_INCLUIRENINFORME, "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_INTERPRETACION "
              + "FROM BIOSLIS.DATOS_FICHASEXAMENESTESTATB " + "INNER JOIN BIOSLIS.CFG_BACANTIBIOTICOS "
              + "ON BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDANTIBIOTICO = BIOSLIS.CFG_BACANTIBIOTICOS.CBA_IDBACANTIBIOTICO "
              + "WHERE " + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_NORDEN = :orderId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDEXAMEN = :examId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDTEST = :testId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDMICROORGANISMO = :microorganismId");
      query.setParameter("orderId", orderId);
      query.setParameter("examId", examId);
      query.setParameter("testId", testId);
      query.setParameter("microorganismId", microorganismId);

      data = (List<Object>) query.list();

      session.close();

      for (Object register : data) {
        Object[] registerData = (Object[]) register;
        result.add(new HashMap<String, Object>() {
          {
            put("antibiotic", registerData[0].toString());
            put("cim", (BigDecimal) registerData[1]);
            put("diameter", (BigDecimal) registerData[2]);
            put("includeinreport", "S".equals(registerData[3].toString()));
            put("interpretation", registerData[4] == null ? "" : registerData[4].toString());
          }
        });

      }
    } catch (Exception e) {
      log.error("error en getAntibiotics " + e.getMessage());
      if (session.isOpen()) {
        session.close();
      }
    }

    return result;
  }

  private void deleteStoredAntibiograms(MBAntibiogram antibiogram, List<String> microorganismToDelte) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("DELETE FROM BIOSLIS.DATOS_FICHASEXAMENESTESTATB "
          + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_NORDEN = :orderId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDEXAMEN = :examId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDTEST = :testId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDMICROORGANISMO = :microorganismId");

      for (String microorganismName : microorganismToDelte) {
        query.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
        query.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
        query.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));
        query.setParameter("microorganismId", getMicroorganismId(microorganismName));
        query.executeUpdate();

      }
    } catch (Exception e) {
      log.error("error en deleteStoredAntibiograms " + e.getMessage());
    } finally {
      session.getTransaction().commit();
      session.close();
    }

    session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query2 = session.createSQLQuery("DELETE FROM BIOSLIS.DATOS_FICHASEXAMENESTESTMO "
          + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN = :orderId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN = :examId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST = :testId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACMICROORGANISMO = :microorganismId");

      for (String microorganismName : microorganismToDelte) {
        query2.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
        query2.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
        query2.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));
        query2.setParameter("microorganismId", getMicroorganismId(microorganismName));
        query2.executeUpdate();
        session.getTransaction().commit();
      }
    } finally {
      session.close();
    }
  }

  private BigDecimal getColonyCountId(String colonyCountName) {
    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_IDBACRECUENTOCOLONIA " + "FROM BIOSLIS.CFG_BACRECUENTOCOLONIAS "
              + "WHERE BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_DESCRIPCION = :colonyCountName");
      query.setString("colonyCountName", colonyCountName);
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en getColonyCountId " + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  private String getColonyCountName(BigDecimal colonyCountId) {
    String result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_DESCRIPCION " + "FROM BIOSLIS.CFG_BACRECUENTOCOLONIAS "
              + "WHERE BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_IDBACRECUENTOCOLONIA = :colonyCountId");
      query.setParameter("colonyCountId", colonyCountId);
      result = query.list().get(0).toString();
    } catch (Exception e) {
      log.error("error en getColonyCountName " + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  private BigDecimal getInfectionTypeId(String infectionTypeName) {
    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACTIPOSINFECCIONES.CBTI_IDBACTIPOINFECCION " + "FROM BIOSLIS.CFG_BACTIPOSINFECCIONES "
              + "WHERE BIOSLIS.CFG_BACTIPOSINFECCIONES.CBTI_DESCRIPCION = :infectionTypeName");
      query.setString("infectionTypeName", infectionTypeName);
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en getInfectionTypeId " + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  private String getInfectionTypeName(BigDecimal infectionTypeId) {
    String result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACTIPOSINFECCIONES.CBTI_DESCRIPCION " + "FROM BIOSLIS.CFG_BACTIPOSINFECCIONES "
              + "WHERE BIOSLIS.CFG_BACTIPOSINFECCIONES.CBTI_IDBACTIPOINFECCION = :infectionTypeId");
      query.setParameter("infectionTypeId", infectionTypeId);
      result = query.list().get(0).toString();
    } catch (Exception e) {
      log.error("error en getInfectionTypeName" + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  private BigDecimal getMicroorganismId(String microorganismName) {
    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_IDBACMICROORGANISMO " + "FROM BIOSLIS.CFG_BACMICROORGANISMOS "
              + "WHERE BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_DESCRIPCION = :microorganismName");
      query.setString("microorganismName", microorganismName);
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en getMicroorganismId " + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  private String getMicroorganismName(BigDecimal microorganismId) {
    String result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_DESCRIPCION " + "FROM BIOSLIS.CFG_BACMICROORGANISMOS "
              + "WHERE BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_IDBACMICROORGANISMO = :microorganismId");
      query.setParameter("microorganismId", microorganismId);
      result = query.list().get(0).toString();
    } catch (Exception e) {
      log.error("error en getMicroorganismName " + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  private BigDecimal getAntibiogramId(String antibiogramName) {
    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_IDBACANTIBIOGRAMA " + "FROM BIOSLIS.CFG_BACANTIBIOGRAMAS "
              + "WHERE BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_DESCRIPCION = :antibiogramName");
      query.setString("antibiogramName", antibiogramName);
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en getAntibiogramId" + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  private String getAntibiogramName(BigDecimal antibiogramId) {
    String result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session
          .createSQLQuery("SELECT BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_DESCRIPCION " + "FROM BIOSLIS.CFG_BACANTIBIOGRAMAS "
              + "WHERE BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_IDBACANTIBIOGRAMA = :antibiogramId");
      query.setParameter("antibiogramId", antibiogramId);
      result = query.list().get(0).toString();
    } catch (Exception e) {
      log.error("error en getAntibiogramName " + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  private BigDecimal getAntibioticId(String antibioticName) {
    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACANTIBIOTICOS.CBA_IDBACANTIBIOTICO " + "FROM BIOSLIS.CFG_BACANTIBIOTICOS "
              + "WHERE BIOSLIS.CFG_BACANTIBIOTICOS.CBA_DESCRIPCION = :antibioticName");
      query.setString("antibioticName", antibioticName);
      result = (BigDecimal) query.list().get(0);

    } finally {
      session.close();
    }
    return result;
  }

  private BigDecimal getPatientIdForOrderId(int orderId) {
    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("SELECT BIOSLIS.DATOS_FICHAS.DF_IDPACIENTE " + "FROM BIOSLIS.DATOS_FICHAS "
          + "WHERE BIOSLIS.DATOS_FICHAS.DF_NORDEN = :orderId");
      query.setParameter("orderId", orderId);
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en  getPatientIdForOrderId " + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private void createOrUpdateAntibioticForAntibiogramList(MBAntibiogram antibiogram, int listId)
      throws BiosLISDAOException {

    HashMap<String, Object> antibioticList = (HashMap<String, Object>) antibiogram.getAntibioticList().get(listId);
    List<HashMap<String, Object>> antibiotics = (List<HashMap<String, Object>>) antibioticList.get("antibiotics");
    List<String> storedAntibiotics = getStoredAntibioticForAntibiogramList(antibiogram, listId);

    // for (HashMap<String, Object> antibiotic : antibiotics) {
    antibiotics.forEach((antibiotic) -> {

      BigDecimal idMicroorganismo = getMicroorganismId(
          ((HashMap<String, String>) antibioticList.get("microorganism")).get("name"));
      BigDecimal idAntibiograma = getAntibiogramId(
          ((HashMap<String, String>) antibioticList.get("antibiogram")).get("name"));
      BigDecimal idAntibiotic = getAntibioticId((antibiotic.get("name").toString()));

      Session session = HibernateUtil.getSessionFactory().openSession();
      try {

        Query query;
        // if (storedAntibiotics.contains(antibiotic.get("antibiotic").toString())) {
        if (storedAntibiotics.contains(antibiotic.get("name"))) {

          String q2 = " UPDATE  DATOS_FICHASEXAMENESTESTATB set  "
              + "  DATOS_FICHASEXAMENESTESTATB.DFETA_IDANTIBIOGRAMA = :antibiogramId, "
              + "  DATOS_FICHASEXAMENESTESTATB.DFETA_INTERPRETACION = :interpretation, "
              + "  DATOS_FICHASEXAMENESTESTATB.DFETA_INCLUIRENINFORME =  :includeInInform ";

          if (antibiotic.get("etest") != null && antibiotic.get("etest") != "") {
            q2 = q2 + ",DATOS_FICHASEXAMENESTESTATB.DFETA_ETEST = :etest ";
          }

          if (antibiotic.get("cim") != null && antibiotic.get("cim") != "") {
            q2 = q2 + ",DATOS_FICHASEXAMENESTESTATB.DFETA_CIM = :cim ";
          }

          if (antibiotic.get("diameter") != null && antibiotic.get("diameter") != "") {
            q2 = q2 + ", DATOS_FICHASEXAMENESTESTATB.DFETA_KIRBYBAUER = :diameter ";
          }

          q2 = q2
              + "    WHERE DATOS_FICHASEXAMENESTESTATB.DFETA_NORDEN = :orderId AND DATOS_FICHASEXAMENESTESTATB.DFETA_IDEXAMEN = :examId AND "
              + "    DATOS_FICHASEXAMENESTESTATB.DFETA_IDTEST = :testId AND DATOS_FICHASEXAMENESTESTATB.DFETA_IDMICROORGANISMO = :microorganismId AND  "
              + "    DATOS_FICHASEXAMENESTESTATB.DFETA_IDANTIBIOTICO = :antibioticId  ";

          query = session.createSQLQuery(q2);

          query.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
          query.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
          query.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));

          query.setParameter("microorganismId", idMicroorganismo);

          query.setParameter("antibiogramId", idAntibiograma);

          // query.setParameter("antibioticId",
          // getAntibioticId((antibiotic.get("antibiotic").toString())));
          query.setParameter("antibioticId", idAntibiotic);

          if (antibiotic.get("etest") != null && antibiotic.get("etest") != "") {
            query.setParameter("etest", Integer.parseInt(antibiotic.get("etest").toString()));
          }

          if (antibiotic.get("cim") != null && antibiotic.get("cim") != "") {
            query.setParameter("cim", Integer.parseInt(antibiotic.get("cim").toString()));
          }

          if (antibiotic.get("diameter") != null && antibiotic.get("diameter") != "") {
            query.setParameter("diameter", Integer.parseInt(antibiotic.get("diameter").toString()));
          }

          query.setString("interpretation", antibiotic.get("interpretation").toString());

          query.setString("includeInInform",
              (antibiotic.get("includeinreport") == null ? "N" : antibiotic.get("includeinreport").toString()));

          session.beginTransaction();
          query.executeUpdate();
          session.getTransaction().commit();

          storedAntibiotics.remove(storedAntibiotics.indexOf(antibiotic.get("name").toString()));
        } else {

          DatosFichas df = (DatosFichas) session.get(DatosFichas.class, Integer.parseInt(antibiogram.getOrderId()));

          String q2 = " INSERT INTO  DATOS_FICHASEXAMENESTESTATB  "
              + "  ( DATOS_FICHASEXAMENESTESTATB.DFETA_NORDEN,  DATOS_FICHASEXAMENESTESTATB.DFETA_IDEXAMEN,  DATOS_FICHASEXAMENESTESTATB.DFETA_IDTEST,  "
              + "    DATOS_FICHASEXAMENESTESTATB.DFETA_IDMICROORGANISMO,  DATOS_FICHASEXAMENESTESTATB.DFETA_IDPACIENTE, DATOS_FICHASEXAMENESTESTATB.DFETA_IDANTIBIOGRAMA,  DATOS_FICHASEXAMENESTESTATB.DFETA_IDANTIBIOTICO,  "
              + "      DATOS_FICHASEXAMENESTESTATB.DFETA_INTERPRETACION,  "
              + "   DATOS_FICHASEXAMENESTESTATB.DFETA_INCLUIRENINFORME ,  DATOS_FICHASEXAMENESTESTATB.DFETA_FECHAORDEN ";

          String q1 = ") VALUES (:orderId, :examId, :testId, :microorganismId, :patientId, :antibiogramId, :antibioticId, :interpretation, :includeInInform , :fechaOrden ";
          if (antibiotic.get("etest") != null && antibiotic.get("etest") != "") {
            q1 += ", :etest ";
            q2 += " , DATOS_FICHASEXAMENESTESTATB.DFETA_ETEST ";
          }

          if (antibiotic.get("cim") != null && antibiotic.get("cim") != "") {
            q1 += ", :cim ";
            q2 += " , DATOS_FICHASEXAMENESTESTATB.DFETA_CIM ";
          }

          if (antibiotic.get("diameter") != null && antibiotic.get("diameter") != "") {
            q1 += ", :diameter  ";
            q2 += ", DATOS_FICHASEXAMENESTESTATB.DFETA_KIRBYBAUER ";
          }

          q2 += q1 + " )";

          query = session.createSQLQuery(q2);
          query.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
          query.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
          query.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));
          query.setParameter("microorganismId", idMicroorganismo);

          query.setParameter("patientId", getPatientIdForOrderId(Integer.parseInt(antibiogram.getOrderId())));
          query.setParameter("antibiogramId", idAntibiograma);
          query.setParameter("antibioticId", idAntibiotic);
          query.setString("interpretation", antibiotic.get("interpretation").toString());
          query.setString("includeInInform", antibiotic.get("includeinreport").toString());
          query.setParameter("fechaOrden", df.getDfFechaorden());

          if (antibiotic.get("cim") != null && antibiotic.get("cim") != "") {
            query.setParameter("cim", Integer.parseInt(antibiotic.get("cim").toString()));
          }

          if (antibiotic.get("diameter") != null && antibiotic.get("diameter") != "") {
            query.setParameter("diameter", Integer.parseInt(antibiotic.get("diameter").toString()));
          }

          if (antibiotic.get("etest") != null && antibiotic.get("etest") != "") {
            query.setParameter("etest", Integer.parseInt(antibiotic.get("etest").toString()));
          }

          session.beginTransaction();
          query.executeUpdate();
          session.getTransaction().commit();
        }
      } catch (Exception e) {
        log.error("error en  createOrUpdateAntibioticForAntibiogramList " + e.getMessage());
        e.printStackTrace();
      } finally {
        session.close();
      }

    });

    Session session = HibernateUtil.getSessionFactory().openSession();
    try {

      Query query = session.createSQLQuery(
          "DELETE FROM DATOS_FICHASEXAMENESTESTATB " + "WHERE DATOS_FICHASEXAMENESTESTATB.DFETA_NORDEN = :orderId AND "
              + "DATOS_FICHASEXAMENESTESTATB.DFETA_IDEXAMEN = :examId AND "
              + "DATOS_FICHASEXAMENESTESTATB.DFETA_IDTEST = :testId AND "
              + "DATOS_FICHASEXAMENESTESTATB.DFETA_IDMICROORGANISMO = :microorganismId AND "
              + "DATOS_FICHASEXAMENESTESTATB.DFETA_IDANTIBIOTICO = :antibioticId");

      session.beginTransaction();
      for (String antibioticName : storedAntibiotics) {
        query.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
        query.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
        query.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));
        query.setParameter("microorganismId",
            getMicroorganismId(((HashMap<String, String>) antibioticList.get("microorganism")).get("name")));
        query.setParameter("antibioticId", getAntibioticId(antibioticName));

        query.executeUpdate();

      }
    } catch (Exception e) {
      log.error("error en createOrUpdateAntibioticForAntibiogramList " + e.getMessage());
      e.printStackTrace();
    } finally {
      session.getTransaction().commit();
      session.close();
    }

  }

  @SuppressWarnings("unchecked")
  private void createOrUpdateMethodForAntibiogramList(MBAntibiogram antibiogram, int listId) {
    HashMap<String, Object> methodList = (HashMap<String, Object>) antibiogram.getAntibioticList().get(listId);

    BigDecimal methodListId = getOrCreateMethodListId(antibiogram, listId);

    List<String> storedMethods = getStoredMethodsForAntibiogramList(methodListId);
    List<HashMap<String, Object>> methods = (List<HashMap<String, Object>>) methodList.get("methods");
    if (methods == null) {
      return;
    }

    for (HashMap<String, Object> method : methods) {

      BigDecimal idMethod = getMethodId(method.get("method"));
      BigDecimal idResult = getMethodResultId(method.get("result"));

      Session session = HibernateUtil.getSessionFactory().openSession();
      try {
        session.beginTransaction();
        Query query;

        if (storedMethods.contains(method.get("method").toString())) {
          query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_BACTESTMOMARCADORESR "
              + "SET BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDRESULTADOMARCADORR = :resultId "
              + "WHERE BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDTESTMOMARCADORR = :methodListId AND "
              + "BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDMARCADORRESISTENCIA = :methodId ");
          query.setParameter("methodListId", methodListId);
          query.setParameter("methodId", idMethod);
          query.setParameter("resultId", idResult);
          query.executeUpdate();
          session.getTransaction().commit();
          storedMethods.remove(storedMethods.indexOf(method.get("method").toString()));
        } else {
          query = session.createSQLQuery("INSERT INTO BIOSLIS.DATOS_BACTESTMOMARCADORESR ("
              + "BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDTESTMOMARCADORR, "
              + "BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDMARCADORRESISTENCIA, "
              + "BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDRESULTADOMARCADORR) "
              + "VALUES (:methodListId, :methodId, :resultId)");
          query.setParameter("methodListId", methodListId);
          query.setParameter("methodId", idMethod);
          query.setParameter("resultId", idResult);
          query.executeUpdate();
          session.getTransaction().commit();
        }
      } catch (Exception e) {
        log.error("error en createOrUpdateMethodForAntibiogramList" + e.getMessage());
        e.printStackTrace();
      } finally {
        session.close();
      }
    }

    try {
      if (storedMethods.size() > 0) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {

          Query query = session.createSQLQuery("DELETE FROM BIOSLIS.DATOS_BACTESTMOMARCADORESR "
              + "WHERE BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDTESTMOMARCADORR = :methodListId AND "
              + "BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDMARCADORRESISTENCIA = :methodId ");

          for (String methodName : storedMethods) {
            query.setParameter("methodListId", methodListId);
            query.setParameter("methodId", getMethodId(methodName));
            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();
          }
        } catch (Exception e) {
          log.error("error en delete  BACTESTMOMARCADORESR " + e.getMessage());
          e.printStackTrace();
        } finally {
          session.close();
        }
      }

    } catch (Exception e) {
      log.error("error en delete  BACTESTMOMARCADORESR" + e.getMessage());
      e.printStackTrace();
    }
  }

  private BigDecimal getMethodId(Object methodName) {

    if (methodName == null) {
      return null;
    }
    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_IDMARCADORRESISTENCIA "
          + "FROM BIOSLIS.CFG_BACMARCADORESRESISTENCIA "
          + "WHERE BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_DESCRIPCION = :methodName");
      query.setParameter("methodName", methodName);
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en getMethodId " + e.getMessage());
    } finally {
      session.close();
    }

    return result;
  }

  private BigDecimal getMethodResultId(Object methodResult) {
    if (methodResult == null) {
      return null;
    }

    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("SELECT BIOSLIS.LDV_BACRESULTADOSMARCADORR.LDVBRMR_IDRESULTADOMARCADORR "
          + "FROM BIOSLIS.LDV_BACRESULTADOSMARCADORR "
          + "WHERE BIOSLIS.LDV_BACRESULTADOSMARCADORR.LDVBRMR_DESCRIPCIÓN = :methodResult");
      query.setParameter("methodResult", methodResult);
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en getMethodResultId " + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  private BigDecimal getOrCreateMethodListId(MBAntibiogram antibiogram, int listId) {

    BigDecimal result = getMethodListId(antibiogram, listId);

    if (result == null) {
      result = createMethodListId(antibiogram, listId);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private BigDecimal getMethodListId(MBAntibiogram antibiogram, int listId) {

    HashMap<String, Object> methodList = (HashMap<String, Object>) antibiogram.getAntibioticList().get(listId);
    BigDecimal idMicroorganismo = getMicroorganismId(
        ((HashMap<String, String>) methodList.get("microorganism")).get("name"));

    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {

      Query query = session
          .createSQLQuery("SELECT DATOS_FICHASEXAMENESTESTMO.dfetmo_idtestmomarcadorr FROM  DATOS_FICHASEXAMENESTESTMO "
              + " WHERE  DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN = :orderId AND "
              + " DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN = :examId AND "
              + " DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST = :testId AND "
              + " DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACMICROORGANISMO = :microorganismId ");
      query.setParameter("orderId", antibiogram.getOrderId());
      query.setParameter("examId", antibiogram.getExamId());
      query.setParameter("testId", antibiogram.getTestId());
      query.setParameter("microorganismId", idMicroorganismo);

      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en " + e.getMessage());
      e.printStackTrace();
    } finally {
      session.close();
    }

    return result;
  }

  @SuppressWarnings("unchecked")
  private BigDecimal getMethodListId(int orderId, int examId, int testId, BigDecimal microorganismId) {
    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("SELECT DFETMO_IDTESTMOMARCADORR FROM BIOSLIS.DATOS_FICHASEXAMENESTESTMO "
          + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN = :orderId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN = :examId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST = :testId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACMICROORGANISMO = :microorganismId ");
      query.setParameter("orderId", orderId);
      query.setParameter("examId", examId);
      query.setParameter("testId", testId);
      query.setParameter("microorganismId", microorganismId);
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en getMethodListId " + e.getMessage());
      e.printStackTrace();
    } finally {
      session.close();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private BigDecimal createMethodListId(MBAntibiogram antibiogram, int listId) {

    HashMap<String, Object> methodList = (HashMap<String, Object>) antibiogram.getAntibioticList().get(listId);
    BigDecimal newIndex = getMethodListNewIndex();
    BigDecimal idMicroorganismo = getMicroorganismId(
        ((HashMap<String, String>) methodList.get("microorganism")).get("name"));

    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session
          .createSQLQuery("UPDATE BIOSLIS.DATOS_FICHASEXAMENESTESTMO " + "SET DFETMO_IDTESTMOMARCADORR = :newIndex "
              + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN = :orderId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN = :examId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST = :testId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACMICROORGANISMO = :microorganismId");
      query.setParameter("newIndex", newIndex);
      query.setParameter("orderId", antibiogram.getOrderId());
      query.setParameter("examId", antibiogram.getExamId());
      query.setParameter("testId", antibiogram.getTestId());
      query.setParameter("microorganismId", idMicroorganismo);
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      log.error("error en createMethodListId " + e.getMessage());
      e.printStackTrace();
    } finally {
      session.close();
    }
    return newIndex;
  }

  private BigDecimal getMethodListNewIndex() {
    BigDecimal result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("SELECT BIOSLIS.SEQ_DATOS_BACTESTMOMARCADORESR.NEXTVAL FROM DUAL");
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en getMethodListNewIndex " + e.getMessage());
    } finally {
      session.close();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private List<String> getStoredAntibioticForAntibiogramList(MBAntibiogram antibiogram, int listId) {

    List<String> result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      HashMap<String, Object> antibioticList = (HashMap<String, Object>) antibiogram.getAntibioticList().get(listId);
      session.beginTransaction();
      Query query = session
          .createSQLQuery("SELECT BIOSLIS.CFG_BACANTIBIOTICOS.CBA_DESCRIPCION FROM BIOSLIS.DATOS_FICHASEXAMENESTESTATB "
              + "INNER JOIN BIOSLIS.CFG_BACANTIBIOTICOS "
              + "ON BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDANTIBIOTICO = BIOSLIS.CFG_BACANTIBIOTICOS.CBA_IDBACANTIBIOTICO "
              + "WHERE " + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_NORDEN = :orderId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDEXAMEN = :examId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDTEST = :testId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDMICROORGANISMO = :microorganismId");
      query.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
      query.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
      query.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));
      query.setParameter("microorganismId",
          getMicroorganismId(((HashMap<String, String>) antibioticList.get("microorganism")).get("name")));

      result = (List<String>) query.list();

    } finally {
      session.close();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private List<String> getStoredMethodsForAntibiogramList(BigDecimal methodListId) {

    List<String> result = new ArrayList<String>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {

      Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_DESCRIPCION "
          + "FROM BIOSLIS.DATOS_BACTESTMOMARCADORESR " + "INNER JOIN BIOSLIS.CFG_BACMARCADORESRESISTENCIA "
          + "ON BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDMARCADORRESISTENCIA = BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_IDMARCADORRESISTENCIA "
          + "WHERE BIOSLIS.DATOS_BACTESTMOMARCADORESR.DBTMMR_IDTESTMOMARCADORR = :methodListId");
      query.setParameter("methodListId", methodListId);

      result = (List<String>) query.list();

    } finally {
      session.close();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private List<String> getStoredAntibiogramList(MBAntibiogram antibiogram) {

    List<String> result = null;
    Session session = HibernateUtil.getSessionFactory().openSession();

    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "SELECT BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_DESCRIPCION FROM BIOSLIS.DATOS_FICHASEXAMENESTESTMO "
              + "INNER JOIN BIOSLIS.CFG_BACMICROORGANISMOS "
              + "ON BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACMICROORGANISMO = BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_IDBACMICROORGANISMO "
              + "WHERE " + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN = :orderId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN = :examId AND "
              + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST = :testId");
      query.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
      query.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
      query.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));

      result = (List<String>) query.list();

    } finally {
      session.close();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private void createAntibiogramList(MBAntibiogram antibiogram, int listId) {

    HashMap<String, Object> antibioticList = (HashMap<String, Object>) antibiogram.getAntibioticList().get(listId);
    BigDecimal idMicroorganismo = getMicroorganismId(
        ((HashMap<String, String>) antibioticList.get("microorganism")).get("name"));
    BigDecimal idColonyCount = getColonyCountId(antibiogram.getColonycount());
    BigDecimal idInfeccion = getInfectionTypeId(antibiogram.getInfectionType());
    BigDecimal idAntibiograma = getAntibiogramId(
        ((HashMap<String, String>) antibioticList.get("antibiogram")).get("name"));

    Session session = HibernateUtil.getSessionFactory().openSession();

    DatosFichas df = new DatosFichas();
    df = (DatosFichas) session.get(DatosFichas.class, Integer.parseInt(antibiogram.getOrderId()));

    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("INSERT INTO BIOSLIS.DATOS_FICHASEXAMENESTESTMO "
          + "(BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN, BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN, BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST, "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACMICROORGANISMO, BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACRECUENTOCOLONIAS, BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACANTIBIOGRAMA, "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACTIPOINFECCION , BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_FECHAORDEN) "
          + "VALUES (:orderId, :examId, :testId, :microorganismId, :colonyCountId, :antibiogramId, :infectionTypeId , :fechaOrden)");

      query.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
      query.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
      query.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));
      query.setParameter("microorganismId", idMicroorganismo);
      query.setParameter("colonyCountId", idColonyCount);
      query.setParameter("infectionTypeId", idInfeccion);
      query.setParameter("antibiogramId", idAntibiograma);
      query.setParameter("fechaOrden", df.getDfFechaorden());
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      log.error("error en createAntibiogramList");
    } finally {
      session.close();
    }

  }

  @SuppressWarnings("unchecked")
  private void updateAntibiogramList(MBAntibiogram antibiogram, int listId) {

    HashMap<String, Object> antibioticList = (HashMap<String, Object>) antibiogram.getAntibioticList().get(listId);
    BigDecimal idMicroorganismo = getMicroorganismId(
        ((HashMap<String, String>) antibioticList.get("microorganism")).get("name"));
    BigDecimal idColonyCount = getColonyCountId(antibiogram.getColonycount());
    BigDecimal idInfeccion = getInfectionTypeId(antibiogram.getInfectionType());
    BigDecimal idAntibiograma = getAntibiogramId(
        ((HashMap<String, String>) antibioticList.get("antibiogram")).get("name"));

    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_FICHASEXAMENESTESTMO "
          + "SET BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACRECUENTOCOLONIAS = :colonyCountId, "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACANTIBIOGRAMA = :antibiogramId, "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACTIPOINFECCION = :infectionTypeId "
          + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN = :orderId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN = :examId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST = :testId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACMICROORGANISMO = :microorganismId");

      query.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
      query.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
      query.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));
      query.setParameter("microorganismId", idMicroorganismo);
      query.setParameter("colonyCountId", idColonyCount);
      query.setParameter("infectionTypeId", idInfeccion);
      query.setParameter("antibiogramId", idAntibiograma);

      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      log.error("error en updateAntibiogramList " + e.getMessage());
    } finally {
      session.close();
    }
  }

  @SuppressWarnings("unchecked")
  private Boolean antibiogramListIsCreated(MBAntibiogram antibiogram, int listId) {
    BigDecimal result = null;

    HashMap<String, Object> antibioticList = (HashMap<String, Object>) antibiogram.getAntibioticList().get(listId);
    BigDecimal idMicroorganismo = getMicroorganismId(
        ((HashMap<String, String>) antibioticList.get("microorganism")).get("name"));

    Session session = HibernateUtil.getSessionFactory().openSession();
    try {

      session.beginTransaction();
      Query query = session.createSQLQuery("SELECT COUNT(*) FROM BIOSLIS.DATOS_FICHASEXAMENESTESTMO "
          + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_NORDEN = :orderId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDEXAMEN = :examId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDTEST = :testId AND "
          + "BIOSLIS.DATOS_FICHASEXAMENESTESTMO.DFETMO_IDBACMICROORGANISMO = :microorganismId");
      query.setParameter("orderId", Integer.parseInt(antibiogram.getOrderId()));
      query.setParameter("examId", Integer.parseInt(antibiogram.getExamId()));
      query.setParameter("testId", Integer.parseInt(antibiogram.getTestId()));
      query.setParameter("microorganismId", idMicroorganismo);
      result = (BigDecimal) query.list().get(0);
    } catch (Exception e) {
      log.error("error en antibiogramListIsCreated" + e.getMessage());
    } finally {
      session.close();
    }

    return result.equals(new BigDecimal(1));
  }
}
