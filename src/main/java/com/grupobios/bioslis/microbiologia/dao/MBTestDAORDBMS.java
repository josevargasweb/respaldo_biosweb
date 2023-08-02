package com.grupobios.bioslis.microbiologia.dao;

import com.grupobios.bioslis.microbiologia.entity.MBTest;

import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import com.grupobios.bioslis.back.api.CapturaResultadosRestController;
import com.grupobios.bioslis.config.HibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MBTestDAORDBMS implements MBTestDAO {

    private static Logger log = LogManager.getLogger(MBTestDAORDBMS.class);

    private String selectQuery = "SELECT BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_RESULTADO, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_RESULTADONUMERICO, "
            + "BIOSLIS.CFG_TEST.CT_ABREVIADO, BIOSLIS.CFG_TIPOSDERESULTADO.CTR_DESCRIPCION, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDESTADORESULTADOTEST, "
            + "BIOSLIS.CFG_TEST.CT_CODIGO, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTIPOMUESTRA, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDMUESTRA, "
            + " BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_FECHAINGRESORESULTADO, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDBACESTADOTEST, BIOSLIS.CFG_TEST.CT_ESCULTIVO, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDPACIENTE "
            + "FROM BIOSLIS.DATOS_FICHASEXAMENESTEST " + "INNER JOIN BIOSLIS.CFG_TEST "
            + "ON  DATOS_FICHASEXAMENESTEST.DFET_IDTEST = BIOSLIS.CFG_TEST.CT_IDTEST "
            + "INNER JOIN BIOSLIS.CFG_TIPOSDERESULTADO "
            + "ON  BIOSLIS.CFG_TEST.CT_IDTIPORESULTADO = BIOSLIS.CFG_TIPOSDERESULTADO.CTR_IDTIPORESULTADO "
            + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :orderId AND "
            + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :examId AND "
            + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = :testId";

    private String selectQuery2 = " SELECT dfet.DFET_IDTEST as testId , dfet.DFET_RESULTADO ,   dfet.DFET_RESULTADONUMERICO ,  ct.CT_ABREVIADO as testName , "
            + " ctr.CTR_DESCRIPCION ,  dfet.DFET_IDESTADORESULTADOTEST  ,  cert.cert_descripcionestadotest as status,   ct.CT_CODIGO as tesCode, "
            + " dfet.DFET_IDTIPOMUESTRA as sampleTypeId ,  dfet.DFET_IDMUESTRA as sampleCode, nvl(dfm.dfm_idzonacuerpo , '-1') as bodyPartId ,  "
            + "  nvl(cbzc.cbzc_descripcion, ' ') as bodyPart , "
            + " nvl(dfm.dfm_codigobarra,' ') as labelCode,  cmue.cmue_prefijotipomuestra as sampleprefix  ,nvl(cmue.cmue_descripcion, ' ') as sampleType , "
            + " TO_CHAR(dfet.DFET_FECHAINGRESORESULTADO,'dd-mm-yyyy hh:MM:ss'), "
            + " dfet.DFET_IDBACESTADOTEST, cbet.cbet_descripcion microbiologyStatus , ct.CT_ESCULTIVO, dfet.DFET_IDPACIENTE FROM DATOS_FICHASEXAMENESTEST  dfet  "
            + " INNER JOIN CFG_TEST  ct  ON  dfet.DFET_IDTEST = ct.CT_IDTEST INNER JOIN CFG_TIPOSDERESULTADO  ctr ON  ct.CT_IDTIPORESULTADO = ctr.CTR_IDTIPORESULTADO "
            + " left join CFG_ESTADOSRESULTADOSTEST cert  on cert.cert_idestadoresultadotest = dfet.DFET_IDESTADORESULTADOTEST  left join CFG_BACESTADOSTEST cbet "
            + " on cbet.cbet_idbacestadotest = dfet.DFET_IDBACESTADOTEST left join CFG_MUESTRAS cmue  on cmue.cmue_idtipomuestra =  dfet.DFET_IDTIPOMUESTRA "
            + " left join datos_fichasmuestras dfm  on dfm.dfm_idmuestra =  dfet.DFET_IDMUESTRA left join  cfg_baczonacuerpo cbzc  on cbzc.cbzc_idzonacuerpo = dfm.dfm_idzonacuerpo "
            + " WHERE dfet.DFET_NORDEN =  :orderId AND  dfet.DFET_IDEXAMEN =:examId AND  dfet.DFET_IDTEST =:testId ";

    private String sqlQueryGetEvents = "SELECT BIOSLIS.LOG_EVENTOSFICHAS.LEF_FECHAREGISTRO, BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDUSUARIO, BIOSLIS.LOG_EVENTOSFICHAS.LEF_NOMBRECAMPO, "
            + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORANTERIOR, BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORNUEVO "
            + "FROM BIOSLIS.LOG_EVENTOSFICHAS " + "WHERE BIOSLIS.LOG_EVENTOSFICHAS.LEF_NORDEN = :orderId "
            + "AND BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDEXAMEN = :examId "
            + "AND BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDTEST = :testId";

    @Override
    public MBTest getTestByIds(int orderId, int examId, int testId) {

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        MBTest result = new MBTest();
        Object[] testData = null;
        try {

            Query query = sesion.createSQLQuery(this.selectQuery2);
            query.setParameter("orderId", orderId);
            query.setParameter("examId", examId);
            query.setParameter("testId", testId);
            testData = (Object[]) (query.list().get(0));
        } catch (Exception e) {
            log.error("error en getTestByIds" + e.getMessage());
            e.printStackTrace();
        } finally {
            sesion.close();
        }

        Date reviewDate = null;
        if (testData.length > 0) {
            try {
                if (testData[15] != null) {
                    reviewDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(testData[15].toString());
                }
            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();

            }

            result = new MBTest(Integer.toString(testId), Integer.toString(examId), Integer.toString(orderId),
                    testData[3] == null ? "" : testData[3].toString(),
                    // result ---- aca va a buscar con un resultado en formato STRING (resultado del
                    // test, resultado numerico del test y descripcion tiporesultado test)
                    decodeTestResult(testData[1] == null ? "" : testData[1].toString(), // dfet_resultado -- resultado
                                                                                        // del test
                            (BigDecimal) testData[2], // dfet_resultado numerico, resultado numerico del test
                            testData[4] == null ? "" : testData[4].toString()), // ctr_descripcion --> descripcion
                                                                                // tiporesultado test
                    decodeTestResultType(testData[4].toString()), // resultType --- aqui agrega select si es glosa y
                                                                  // text para los demas tipos de resultado
                    getTestResultOptions((BigDecimal) testData[0]), // lista resultOptions
                    testData[6].toString(), // status --- cert_descripcionestadotest
                    getTestStatusOptions(), // lista statusOptions
                    testData[17] == null ? "" : testData[17].toString(), // microbiologyStatus
                    getTestMicrobiologyStatusOptions(), // lista microbiologyStatusOptions
                    testData[14] == null ? "" : testData[14].toString(),
                    testData[9] == null ? "" : testData[9].toString(), // sampleTypeId idtipomuestra
                    testData[10] == "-1" ? "" : testData[10].toString(), // samplecode
                    testData[7] == null ? "" : testData[7].toString(), // testcode
                    testData[13] == null ? "" : testData[13].toString(), // samplprefix
                    testData[11] == null ? "" : testData[11].toString(),
                    testData[10] == null ? "" : testData[10].toString(),
                    reviewDate, null, null,
                    null,
                    testData[18] == null ? "" : "S".equals(testData[18].toString()) ? "true" : "false", // isCulture
                    getCultureMediums(Integer.toString(examId), Integer.toString(testId),
                            testData[9] == null ? "" : testData[9].toString()), // lista cultureMediums
                    testData[12] == null ? "" : testData[12].toString(), // codigo barra
                    testData[19] == null ? "" : testData[19].toString() // patientId
            );
        }

        return result;
    }

    @Override
    public void update(MBTest test) {
        updateResults(test);
        updateStatus(test);
        updateMicrobiologyStatus(test);
        updateResultDate(test);
        updateSampleType(test);
        updateBodyPart(test);
        updateEvents(test);
        updateCultureMediums(test);
    }

    @Override
    public void create(MBTest test) {

    }

    @Override
    public void delete(String orderId, String examId, String testId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(" DELETE FROM BIOSLIS.DATOS_FICHASEXAMENESTEST WHERE "
                    + " BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :orderId AND "
                    + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :examId AND "
                    + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = :testId");

            query.setParameter("orderId", Integer.parseInt(orderId));
            query.setParameter("examId", Integer.parseInt(examId));
            query.setParameter("testId", Integer.parseInt(testId));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    private void updateBodyPart(MBTest test) {
        if (getBodyPartId(test.getBodyPart()) != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_FICHASMUESTRAS "
                        + "SET BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDZONACUERPO = :bodyPartId "
                        + "WHERE BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDMUESTRA = :sampleCode");
                query.setParameter("bodyPartId", getBodyPartId(test.getBodyPart()));
                query.setParameter("sampleCode", _getSampleCode(test));
                query.executeUpdate();
                session.getTransaction().commit();
            } finally {
                session.close();
            }
        }
    }

    private void updateSampleType(MBTest test) {
        if (this.getSampleTypeId(test.getSampleType()) != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_FICHASEXAMENESTEST "
                        + "SET BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTIPOMUESTRA = :sampleTypeCode "
                        + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :idOrder AND "
                        + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :idExam AND "
                        + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = :idTest");

                query.setParameter("sampleTypeCode", this.getSampleTypeId(test.getSampleType()));
                query.setParameter("idOrder", Integer.parseInt(test.getOrderId()));
                query.setParameter("idExam", Integer.parseInt(test.getExamId()));
                query.setParameter("idTest", Integer.parseInt(test.getId()));
                query.executeUpdate();
                session.getTransaction().commit();
            } finally {
                session.close();
            }
        }
    }

    private void updateResultDate(MBTest test) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_FICHASEXAMENESTEST "
                    + "SET BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_FECHAINGRESORESULTADO = :resultDate "
                    + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :idOrder AND "
                    + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :idExam AND "
                    + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = :idTest");

            query.setTimestamp("resultDate", test.getResultDate());
            query.setParameter("idOrder", Integer.parseInt(test.getOrderId()));
            query.setParameter("idExam", Integer.parseInt(test.getExamId()));
            query.setParameter("idTest", Integer.parseInt(test.getId()));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    private void updateResults(MBTest test) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(this.resultUpdateQueryString(test));

            if (this.getDBResultType(test).equals("NUMÉRICO")) {
                query.setParameter("result", Integer.parseInt(test.getResult()));
            } else {
                query.setString("result", test.getResult());
            }

            query.setParameter("idOrder", Integer.parseInt(test.getOrderId()));
            query.setParameter("idExam", Integer.parseInt(test.getExamId()));
            query.setParameter("idTest", Integer.parseInt(test.getId()));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    private void updateStatus(MBTest test) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_FICHASEXAMENESTEST "
                    + "SET BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDESTADORESULTADOTEST = :statusId "
                    + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :idOrder AND "
                    + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :idExam AND "
                    + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = :idTest");

            query.setParameter("statusId", decodeTestStatusId(test.getStatus()));
            query.setParameter("idOrder", Integer.parseInt(test.getOrderId()));
            query.setParameter("idExam", Integer.parseInt(test.getExamId()));
            query.setParameter("idTest", Integer.parseInt(test.getId()));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    private void updateMicrobiologyStatus(MBTest test) {
        if ("".equals(test.getMicrobiologyStatus())) {
            return;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_FICHASEXAMENESTEST "
                    + "SET BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDBACESTADOTEST = :microbiologyStatusId "
                    + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :idOrder AND "
                    + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :idExam AND "
                    + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = :idTest");

            query.setParameter("microbiologyStatusId", decodeTestMicrobiologyStatusId(test.getMicrobiologyStatus()));
            query.setParameter("idOrder", Integer.parseInt(test.getOrderId()));
            query.setParameter("idExam", Integer.parseInt(test.getExamId()));
            query.setParameter("idTest", Integer.parseInt(test.getId()));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    private String resultUpdateQueryString(MBTest test) {

        String textResult = "UPDATE BIOSLIS.DATOS_FICHASEXAMENESTEST "
                + "SET BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_RESULTADO = :result "
                + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :idOrder AND "
                + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :idExam AND "
                + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = :idTest";

        String numericResult = "UPDATE BIOSLIS.DATOS_FICHASEXAMENESTEST "
                + "SET BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_RESULTADONUMERICO = :result "
                + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :idOrder AND "
                + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :idExam AND "
                + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = :idTest";

        String dbResultType = this.getDBResultType(test);

        if (dbResultType.equals("NUMÉRICO")) {
            return numericResult;
        } else {
            return textResult;
        }
    }

    private String getDBResultType(MBTest test) {
        String result = "";
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(this.selectQuery);
            query.setParameter("orderId", Integer.parseInt(test.getOrderId()));
            query.setParameter("examId", Integer.parseInt(test.getExamId()));
            query.setParameter("testId", Integer.parseInt(test.getId()));
            Object[] testData = (Object[]) (query.list().get(0));
            result = testData[4].toString();
        } finally {
            session.close();
        }
        return result;
    }

    private void updateEvents(MBTest test) {
        for (HashMap<String, String> event : test.getEvents()) {
            if (this.eventIsNotRegistered(event, Integer.parseInt(test.getOrderId()),
                    Integer.parseInt(test.getExamId()), Integer.parseInt(test.getId()))) {
                this.registerEvent(event, Integer.parseInt(test.getOrderId()), Integer.parseInt(test.getExamId()),
                        Integer.parseInt(test.getId()));
            }
        }
    }

    private void updateCultureMediums(MBTest test) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<String> cultureMediumsIds = new ArrayList<String>();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DMS_IDMEDIOCULTIVO FROM BIOSLIS.DATOS_MUESTRASSIEMBRAS "
                    + "WHERE DMS_IDEXAMEN = :examId AND DMS_IDTEST = :testId AND DMS_IDMUESTRA = :sampleId");

            query.setParameter("examId", test.getExamId());
            query.setParameter("testId", test.getId());
            query.setParameter("sampleId", test.getSampleCode());
            if (query.list().size() > 0) {
                cultureMediumsIds = ((ArrayList<Object>) query.list()).stream().map(object -> {
                    return object.toString();
                }).collect(Collectors.toList());
            }
        } finally {
            session.close();
        }
        for (HashMap<String, Object> cultureMedium : test.getCultureMediums()) {
            if (cultureMediumsIds.stream().anyMatch(cultureMedium.get("cultureId").toString()::equals)) {
                // sql update
                try {
                    session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_MUESTRASSIEMBRAS "
                            + "SET DMS_IDPACIENTE = :patientId , DMS_DESCTEMPERATURA = :temperature , DMS_DESCOXIGENACION = :oxigenation "
                            + "WHERE DMS_IDEXAMEN = :examId AND DMS_IDTEST = :testId AND DMS_IDMUESTRA = :sampleId AND DMS_IDMEDIOCULTIVO = :cultureId");
                    query.setParameter("examId", test.getExamId());
                    query.setParameter("testId", test.getId());
                    query.setParameter("sampleId", test.getSampleCode());
                    query.setParameter("cultureId", cultureMedium.get("cultureId").toString());
                    query.setParameter("patientId", cultureMedium.get("patientId").toString());
                    query.setParameter("temperature", cultureMedium.get("temperature").toString());
                    query.setParameter("oxigenation", cultureMedium.get("oxigenation").toString());
                    query.executeUpdate();
                    session.getTransaction().commit();
                } finally {
                    session.close();
                }
            } else {
                // sql insert
                try {
                    session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    Query query = session.createSQLQuery("INSERT INTO BIOSLIS.DATOS_MUESTRASSIEMBRAS "
                            + "( DMS_IDEXAMEN, DMS_IDTEST, DMS_IDMUESTRA, DMS_IDMEDIOCULTIVO, "
                            + "DMS_IDPACIENTE, DMS_DESCTEMPERATURA, DMS_DESCOXIGENACION) "
                            + "VALUES ( :examId, :testId, :sampleId, :cultureId, :patientId, :temperature, :oxigenation)");
                    query.setParameter("examId", test.getExamId());
                    query.setParameter("testId", test.getId());
                    query.setParameter("sampleId", test.getSampleCode());
                    query.setParameter("cultureId", cultureMedium.get("cultureId").toString());
                    query.setParameter("patientId", cultureMedium.get("patientId").toString());
                    query.setParameter("temperature", cultureMedium.get("temperature").toString());
                    query.setParameter("oxigenation", cultureMedium.get("oxigenation").toString());
                    query.executeUpdate();
                    session.getTransaction().commit();
                } finally {
                    session.close();
                }
            }
        }
    }

    private void registerEvent(HashMap<String, String> event, int orderId, int examId, int testId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Date eventDate = null;
        try {
            eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event.get("date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int userId = this.getUserId(event.get("user"));
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("INSERT INTO BIOSLIS.LOG_EVENTOSFICHAS "
                    + "(BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDLOGEVENTOFICHA, BIOSLIS.LOG_EVENTOSFICHAS.LEF_NORDEN, BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDEXAMEN, BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDTEST, BIOSLIS.LOG_EVENTOSFICHAS.LEF_FECHAREGISTRO, "
                    + (userId == -1 ? "" : "BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDUSUARIO, ")
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_NOMBRECAMPO, " + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORANTERIOR, "
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORNUEVO) "
                    + "VALUES (BIOSLIS.SEQ_LOG_EVENTOSFICHAS.NEXTVAL, :orderId, :examId, :testId, :eventDate, "
                    + (userId == -1 ? "" : ":userId, ") + ":field, :oldValue, :newValue)");

            query.setParameter("orderId", orderId);
            query.setParameter("examId", examId);
            query.setParameter("testId", testId);
            query.setTimestamp("eventDate", eventDate);
            if (userId != -1) {
                query.setParameter("userId", userId);
            }
            query.setString("field", event.get("field"));
            query.setString("oldValue", event.get("oldValue"));
            query.setString("newValue", event.get("newValue"));

            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    private int getUserId(String username) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        int result = -1;
        try {
            sesion.beginTransaction();
            Query query = sesion.createSQLQuery("SELECT BIOSLIS.DATOS_USUARIOS.DU_IDUSUARIO "
                    + "FROM BIOSLIS.DATOS_USUARIOS " + "WHERE BIOSLIS.DATOS_USUARIOS.DU_USUARIO = :username");
            query.setString("username", username);
            if (query.list().size() > 0) {
                result = ((BigDecimal) query.list().get(0)).intValue();
            }
        } finally {
            sesion.close();
        }
        return result;
    }

    private Boolean eventIsNotRegistered(HashMap<String, String> event, int orderId, int examId, int testId) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();

        Date eventDate = null;
        try {
            eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event.get("date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Boolean result = false;
        try {
            sesion.beginTransaction();
            Query query = sesion.createSQLQuery("SELECT * " + "FROM BIOSLIS.LOG_EVENTOSFICHAS "
                    + "WHERE BIOSLIS.LOG_EVENTOSFICHAS.LEF_NORDEN = :orderId AND "
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDEXAMEN = :examId AND "
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDTEST = :testId AND "
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_FECHAREGISTRO = :eventDate AND "
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_NOMBRECAMPO = :fieldName AND "
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORANTERIOR = :oldValue AND "
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORNUEVO = :newValue");
            query.setParameter("orderId", orderId);
            query.setParameter("examId", examId);
            query.setParameter("testId", testId);
            query.setTimestamp("eventDate", eventDate);
            query.setString("fieldName", event.get("field"));
            query.setString("oldValue", event.get("oldValue"));
            query.setString("newValue", event.get("newValue"));
            result = query.list().size() == 0;
        } finally {
            sesion.close();
        }
        return result;
    }

    private String decodeTestResult(String stringValue, BigDecimal numericalValue, String resultTypeFlag) {
        if ("NUMÉRICO".equals(resultTypeFlag)) {
            return numericalValue == null ? "" : numericalValue.toString();
        } else {
            return stringValue == null ? "" : stringValue.toString();
        }
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
        List<Object> objetos = null;
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT BIOSLIS.CFG_GLOSAS.CG_DESCRIPCION, BIOSLIS.CFG_GLOSASTEST.CGT_ESGLOSACRITICA "
                            + "FROM BIOSLIS.CFG_GLOSASTEST " + "INNER JOIN BIOSLIS.CFG_GLOSAS "
                            + "ON  BIOSLIS.CFG_GLOSASTEST.CGT_IDGLOSA = BIOSLIS.CFG_GLOSAS.CG_IDGLOSA "
                            + "WHERE BIOSLIS.CFG_GLOSASTEST.CGT_IDTEST = :testId");
            query.setParameter("testId", testId);
            objetos = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("error en getTestResultOptions " + e.getMessage());
        } finally {
            session.close();
        }
        // for (Object option : query.list()) {
        for (Object option : objetos) {
            Object[] resultOptionsData = (Object[]) option;
            result.add(new HashMap<String, Object>() {
                {
                    put("name", resultOptionsData[0].toString());
                    put("critical", "S".equals(resultOptionsData[0].toString()));
                }
            });
        }

        return result;
    }

    private String decodeTestStatus(BigDecimal testStatusFlag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String result = "";
        if (testStatusFlag == null) {
            return result;
        }
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_ESTADOSRESULTADOSTEST.CERT_DESCRIPCIONESTADOTEST "
                    + "FROM BIOSLIS.CFG_ESTADOSRESULTADOSTEST "
                    + "WHERE BIOSLIS.CFG_ESTADOSRESULTADOSTEST.CERT_IDESTADORESULTADOTEST = :testStatusFlag");
            query.setParameter("testStatusFlag", testStatusFlag);
            List<String> obj = query.list();
            if (obj.size() > 0) {
                result = obj.get(0).toString();
            }
        } finally {
            session.close();
        }

        return result;
    }

    private String decodeTestMicrobiologyStatus(BigDecimal testMicrobiologyStatusFlag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String result = "";
        if (testMicrobiologyStatusFlag == null) {
            return result;
        }
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT BIOSLIS.CFG_BACESTADOSTEST.CBET_DESCRIPCION " + "FROM BIOSLIS.CFG_BACESTADOSTEST "
                            + "WHERE BIOSLIS.CFG_BACESTADOSTEST.CBET_IDBACESTADOTEST = :testMicrobiologyStatusFlag");
            query.setParameter("testMicrobiologyStatusFlag", testMicrobiologyStatusFlag);
            if (query.list().size() > 0) {
                result = query.list().get(0).toString();
            }
        } finally {
            session.close();
        }
        return result;
    }

    private BigDecimal decodeTestStatusId(String testStatus) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        BigDecimal result = null;
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_ESTADOSRESULTADOSTEST.CERT_IDESTADORESULTADOTEST "
                    + "FROM BIOSLIS.CFG_ESTADOSRESULTADOSTEST "
                    + "WHERE BIOSLIS.CFG_ESTADOSRESULTADOSTEST.CERT_DESCRIPCIONESTADOTEST = :testStatus");
            query.setParameter("testStatus", testStatus);
            if (query.list().size() > 0) {
                result = (BigDecimal) query.list().get(0);
            }
        } finally {
            session.close();
        }
        return result;
    }

    private BigDecimal decodeTestMicrobiologyStatusId(String testMicrobiologyStatus) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        BigDecimal result = null;
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT BIOSLIS.CFG_BACESTADOSTEST.CBET_IDBACESTADOTEST " + "FROM BIOSLIS.CFG_BACESTADOSTEST "
                            + "WHERE BIOSLIS.CFG_BACESTADOSTEST.CBET_DESCRIPCION = :testMicrobiologyStatus");
            query.setParameter("testMicrobiologyStatus", testMicrobiologyStatus);
            if (query.list().size() > 0) {
                result = (BigDecimal) query.list().get(0);
            }
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
        } catch (Exception e) {
            log.error("error en getTestStatusOptions " + e.getMessage());
            e.printStackTrace();
        } finally {

            session.close();
        }
        return result;
    }

    private ArrayList<String> getTestMicrobiologyStatusOptions() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<String> result = new ArrayList<String>();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT BIOSLIS.CFG_BACESTADOSTEST.CBET_DESCRIPCION " + "FROM BIOSLIS.CFG_BACESTADOSTEST");
            for (Object option : query.list()) {
                result.add(option.toString());
            }
        } finally {
            session.close();
        }
        return result;
    }

    private ArrayList<String> getSampleTypeData(BigDecimal sampleTypeId) {
        ArrayList<String> result = new ArrayList<String>();
        if (sampleTypeId == null) {
            result.add("");
            result.add("");
            return result;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_MUESTRAS.CMUE_PREFIJOTIPOMUESTRA, "
                    + "BIOSLIS.CFG_MUESTRAS.CMUE_DESCRIPCION " + "FROM BIOSLIS.CFG_MUESTRAS "
                    + "WHERE BIOSLIS.CFG_MUESTRAS.CMUE_IDTIPOMUESTRA = :sampleTypeId");
            query.setParameter("sampleTypeId", sampleTypeId);
            Object[] std = (Object[]) query.uniqueResult();
            if (std != null) {
                result.add(std[0] == null ? "" : std[0].toString());
                result.add(std[1] == null ? "" : std[1].toString());
            } else {
                result.add("");
                result.add("");
            }
        } finally {
            session.close();
        }
        return result;
    }

    private ArrayList<String> _getSampleData(BigDecimal sampleId) {
        ArrayList<String> result = new ArrayList<String>();
        if (sampleId == null) {
            result.add("");
            result.add("");
            result.add("");
            return result;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDZONACUERPO, "
                    + "BIOSLIS.CFG_BACZONACUERPO.CBZC_DESCRIPCION, " + "BIOSLIS.DATOS_FICHASMUESTRAS.DFM_CODIGOBARRA "
                    + "FROM BIOSLIS.DATOS_FICHASMUESTRAS " + "LEFT JOIN BIOSLIS.CFG_BACZONACUERPO "
                    + "ON BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDZONACUERPO = BIOSLIS.CFG_BACZONACUERPO.CBZC_IDZONACUERPO "
                    + "WHERE BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDMUESTRA = :sampleId");
            query.setParameter("sampleId", sampleId);
            Object[] std = (Object[]) query.uniqueResult();
            if (std != null) {
                result.add(std[0] == null ? "" : std[0].toString());
                result.add(std[1] == null ? "" : std[1].toString());
                result.add(std[2] == null ? "" : std[2].toString());
            } else {
                result.add("");
                result.add("");
                result.add("");
            }
        } finally {
            session.close();
        }
        return result;
    }

    private String getSampleType(BigDecimal sampleTypeCode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String result = "";
        if (sampleTypeCode == null) {
            return result;
        }
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_MUESTRAS.CMUE_DESCRIPCION "
                    + "FROM BIOSLIS.CFG_MUESTRAS " + "WHERE BIOSLIS.CFG_MUESTRAS.CMUE_IDTIPOMUESTRA = :sampleTypeCode");
            query.setParameter("sampleTypeCode", sampleTypeCode);
            if (query.list().size() > 0) {
                result = query.list().get(0).toString();
            }
        } finally {
            session.close();
        }
        return result;
    }

    private BigDecimal getSampleTypeId(String sampleTypeName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        BigDecimal result = null;
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_MUESTRAS.CMUE_IDTIPOMUESTRA "
                    + "FROM BIOSLIS.CFG_MUESTRAS " + "WHERE BIOSLIS.CFG_MUESTRAS.CMUE_DESCRIPCION = :sampleTypeName");
            query.setParameter("sampleTypeName", sampleTypeName);
            if (query.list().size() > 0 && query.list().get(0) != null) {
                result = (BigDecimal) query.list().get(0);
            }
        } finally {
            session.close();
        }
        return result;
    }

    private String getSamplePrefix(BigDecimal sampleTypeCode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String result = "";
        if (sampleTypeCode == null) {
            return result;
        }
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_MUESTRAS.CMUE_PREFIJOTIPOMUESTRA "
                    + "FROM BIOSLIS.CFG_MUESTRAS " + "WHERE BIOSLIS.CFG_MUESTRAS.CMUE_IDTIPOMUESTRA = :sampleTypeCode");
            query.setParameter("sampleTypeCode", sampleTypeCode);
            if (query.list().size() > 0 && query.list().get(0) != null) {
                result = query.list().get(0).toString();
            }
        } finally {
            session.close();
        }
        return result;
    }

    private String getBodyPart(BigDecimal sampleCode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String result = "";
        if (sampleCode == null) {
            return result;
        }
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT BIOSLIS.CFG_BACZONACUERPO.CBZC_DESCRIPCION "
                    + "FROM BIOSLIS.DATOS_FICHASMUESTRAS " + "INNER JOIN BIOSLIS.CFG_BACZONACUERPO "
                    + "ON BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDZONACUERPO = BIOSLIS.CFG_BACZONACUERPO.CBZC_IDZONACUERPO "
                    + "WHERE BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDMUESTRA = :sampleCode");
            query.setParameter("sampleCode", sampleCode);
            if (query.list().size() > 0 && query.list().get(0) != null) {
                result = query.list().get(0).toString();
            }
        } finally {
            session.close();
        }
        return result;
    }

    private BigDecimal _getSampleCode(MBTest test) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        BigDecimal result = null;
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDMUESTRA " + "FROM BIOSLIS.DATOS_FICHASEXAMENESTEST "
                            + "WHERE BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :orderId AND "
                            + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :examId AND "
                            + "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = :testId");
            query.setParameter("orderId", Integer.parseInt(test.getOrderId()));
            query.setParameter("examId", Integer.parseInt(test.getExamId()));
            query.setParameter("testId", Integer.parseInt(test.getId()));
            if (query.list().size() > 0 && query.list().get(0) != null) {
                result = (BigDecimal) query.list().get(0);
            }
        } finally {
            session.close();
        }
        return result;
    }

    private BigDecimal getBodyPartId(String bodyPart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        BigDecimal result = null;
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT BIOSLIS.CFG_BACZONACUERPO.CBZC_IDZONACUERPO " + "FROM BIOSLIS.CFG_BACZONACUERPO "
                            + "WHERE BIOSLIS.CFG_BACZONACUERPO.CBZC_DESCRIPCION = :bodyPart");
            query.setParameter("bodyPart", bodyPart);
            if (query.list().size() > 0 && query.list().get(0) != null) {
                result = (BigDecimal) query.list().get(0);
            }
        } finally {
            session.close();
        }
        return result;
    }

    private List<HashMap<String, String>> getEventsForTest(int orderId, int examId, int testId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(this.sqlQueryGetEvents);
            query.setParameter("orderId", orderId);
            query.setParameter("examId", examId);
            query.setParameter("testId", testId);
            for (Object event : query.list()) {
                result.add(parseEventData(event));
            }
        } finally {
            session.close();
        }
        return result;
    }

    private HashMap<String, String> parseEventData(Object event) {
        Object[] eventData = (Object[]) event;
        HashMap<String, String> result = new HashMap<String, String>() {
            {
                put("date", eventData[0] == null ? "-" : eventData[0].toString());
                put("user", eventData[1] == null ? "-" : getUserName((BigDecimal) eventData[1]));
                put("field", eventData[2] == null ? "-" : eventData[2].toString());
                put("oldValue", eventData[3] == null ? "-" : eventData[3].toString());
                put("newValue", eventData[4] == null ? "-" : eventData[4].toString());
            }
        };
        return result;
    }

    private String getUserName(BigDecimal userId) {
        if (userId == null) {
            return "";
        }
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String result = "";
        try {
            sesion.beginTransaction();
            Query query = sesion.createSQLQuery("SELECT BIOSLIS.DATOS_USUARIOS.DU_USUARIO "
                    + "FROM BIOSLIS.DATOS_USUARIOS " + "WHERE BIOSLIS.DATOS_USUARIOS.DU_IDUSUARIO = :userId");
            query.setParameter("userId", userId);
            if (query.list().size() > 0) {
                result = (String) (query.list().get(0));
            }
        } finally {
            sesion.close();
        }
        return result;
    }

    private List<HashMap<String, Object>> getCultureMediums(String examId, String testId, String sampleId) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DMS_IDMUESTRASIEMBRA, DMS_IDMUESTRA, "
                    + "DMS_IDMEDIOCULTIVO, DMS_IDPACIENTE, DMS_DESCTEMPERATURA, DMS_DESCOXIGENACION "
                    + "FROM BIOSLIS.DATOS_MUESTRASSIEMBRAS WHERE DMS_IDEXAMEN = :examId AND DMS_IDTEST = :testId AND DMS_IDMUESTRA = :sampleId");
            query.setParameter("examId", examId);
            query.setParameter("testId", testId);
            query.setParameter("sampleId", sampleId);
            for (Object medium : query.list()) {
                Object[] mediumData = (Object[]) medium;
                result.add(new HashMap<String, Object>() {
                    {
                        put("id", mediumData[0] == null ? "" : mediumData[0].toString());
                        put("sampleId", mediumData[1] == null ? "" : mediumData[1].toString());
                        put("cultureId", mediumData[2] == null ? "" : mediumData[2].toString());
                        put("patientId", mediumData[3] == null ? "" : mediumData[3].toString());
                        put("temperature", mediumData[4] == null ? "" : mediumData[4].toString());
                        put("oxigenation", mediumData[5] == null ? "" : mediumData[5].toString());
                    }
                });

            }
        } catch (Exception e) {
            log.error("error en getCultureMediums " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }
}
