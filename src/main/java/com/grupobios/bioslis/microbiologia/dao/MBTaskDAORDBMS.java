package com.grupobios.bioslis.microbiologia.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;

import com.grupobios.bioslis.config.HibernateUtil;

public class MBTaskDAORDBMS implements MBTaskDAO {
    public HashMap<String, String> getTask(String idMuestra, String idExamen, String idTest) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        HashMap<String, String> result = new HashMap<String, String>();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DMETT_IDPACIENTE, DMETT_REALIZADOSIEMBRA, "
                    + "DMETT_REALIZADOINCUBAR, DMETT_REALIZADOREVISIONPLACAS, DMETT_REALIZADOPRUEBAS, "
                    + "DMETT_REALIZADOIDENTIFICACION, DMETT_REALIZADOSENSIBILIDAD FROM "
                    + "BIOSLIS.DATOS_MUESTRASEXAMTESTTAREAS "
                    + "WHERE DMETT_IDMUESTRA = :sampleId AND DMETT_IDEXAMEN = :examId AND DMETT_IDTEST = :testId");
            query.setParameter("sampleId", idMuestra);
            query.setParameter("examId", idExamen);
            query.setParameter("testId", idTest);
            Object[] task = (Object[]) query.uniqueResult();
            if (task != null) {
                result.put("sampleId", idMuestra);
                result.put("examId", idExamen);
                result.put("testId", idTest);
                result.put("patientId", task[0].toString());
                result.put("siembra", "N".equals(task[1].toString()) ? "false" : "true");
                result.put("incubar", "N".equals(task[2].toString()) ? "false" : "true");
                result.put("revPlacas", "N".equals(task[3].toString()) ? "false" : "true");
                result.put("pruebas", "N".equals(task[4].toString()) ? "false" : "true");
                result.put("identificacion", "N".equals(task[5].toString()) ? "false" : "true");
                result.put("sensibilidad", "N".equals(task[6].toString()) ? "false" : "true");
            }
        } finally {
            session.close();
        }
        return result;
    }

    public void updateTask(HashMap<String, String> task) {
        if ("INICIO".equals(task.get("task")))
            return;
        HashMap<String, String> oldData = new HashMap<String, String>();
        HashMap<String, String> newData = new HashMap<String, String>();
        HashMap<String, String> fields = new HashMap<String, String>() {
            {
                put("SIEMBRA", "DMETT_REALIZADOSIEMBRA");
                put("INCUBAR", "DMETT_REALIZADOINCUBAR");
                put("REV. PLACAS", "DMETT_REALIZADOREVISIONPLACAS");
                put("PRUEBAS", "DMETT_REALIZADOPRUEBAS");
                put("IDENTIFICACION", "DMETT_REALIZADOIDENTIFICACION");
                put("SENSIBILIDAD", "DMETT_REALIZADOSENSIBILIDAD");
            }
        };

        oldData.put(fields.get(task.get("task").toString()), "N");
        newData.put(fields.get(task.get("task").toString()), "S");

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_MUESTRASEXAMTESTTAREAS SET "
                    + fields.get(task.get("task").toString()) + " = 'S' "
                    + "WHERE DMETT_IDMUESTRA = :sampleId AND DMETT_IDEXAMEN = :examId AND DMETT_IDTEST = :testId");
            query.setString("sampleId", task.get("sampleId"));
            query.setString("examId", task.get("examId"));
            query.setString("testId", task.get("testId"));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        this.logUpdate(oldData, newData, "DATOS_MUESTRASEXAMTESTTAREAS", task.get("sampleId"));
        return;
    }

    public void createTask(HashMap<String, String> task) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("INSERT INTO BIOSLIS.DATOS_MUESTRASEXAMTESTTAREAS "
                    + "(DMETT_IDMUESTRA, DMETT_IDEXAMEN, DMETT_IDTEST, DMETT_IDPACIENTE) "
                    + "VALUES ( :sampleId, :examId, :testId, :patientId)");
            query.setParameter("sampleId", task.get("sampleId").toString());
            query.setParameter("examId", task.get("examId"));
            query.setParameter("testId", task.get("testId"));
            query.setParameter("patientId", task.get("patientId"));

            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        HashMap<String, String> changes = new HashMap<String, String>() {
            {
                put("DMETT_IDMUESTRA", task.get("sampleId").toString());
                put("DMETT_IDEXAMEN", task.get("examId").toString());
                put("DMETT_IDTEST", task.get("testId").toString());
                put("DMETT_IDPACIENTE", task.get("patientId").toString());
            }
        };
        this.logCreation(changes, "DATOS_MUESTRASEXAMTESTTAREAS", task.get("sampleId").toString());
        return;
    }

    public void deleteTask(String idMuestra, String idExamen, String idTest) {
        return;
    }

    public List<HashMap<String, String>> getTaskList(String orderId, String startDate,
            String endDate, String names, String surname, String documentType,
            String documentId, String atentionType, String serviceId) {
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            String date_strings = "";
            date_strings = startDate.isEmpty() ? date_strings
                    : date_strings + "muestras.DFM_FECHACREACION >= TO_DATE('" + startDate + "', 'YYYY-MM-DD') AND ";
            date_strings = startDate.isEmpty() ? date_strings
                    : date_strings + "muestras.DFM_FECHACREACION <= TO_DATE('" + endDate + "', 'YYYY-MM-DD') AND ";
            String atencion_str = atentionType.isEmpty() ? ""
                    : "fichas.DF_IDTIPOATENCION LIKE '%" + atentionType + "%' AND ";
            String servicio_str = serviceId.isEmpty() ? "" : "AND fichas.DF_IDSERVICIO LIKE '%" + serviceId + "%' ";
            Query query = session.createSQLQuery(
                    "SELECT DMETT_IDMUESTRA, muestras.DFM_FECHACREACION, muestras.DFM_NORDEN, "
                            + "pacientes.DP_NOMBRES, pacientes.DP_PRIMERAPELLIDO, pacientes.DP_SEGUNDOAPELLIDO, "
                            + "DMETT_IDEXAMEN, DMETT_IDTEST, "
                            + "DMETT_REALIZADOSIEMBRA, DMETT_REALIZADOINCUBAR, DMETT_REALIZADOREVISIONPLACAS, "
                            + "DMETT_REALIZADOPRUEBAS, DMETT_REALIZADOIDENTIFICACION, DMETT_REALIZADOSENSIBILIDAD, "
                            + "DMETT_IDPACIENTE "
                            + "FROM BIOSLIS.DATOS_MUESTRASEXAMTESTTAREAS "
                            + "LEFT JOIN BIOSLIS.DATOS_FICHASMUESTRAS muestras ON (muestras.DFM_IDMUESTRA = DMETT_IDMUESTRA) "
                            + "LEFT JOIN BIOSLIS.DATOS_PACIENTES pacientes ON (pacientes.DP_IDPACIENTE = DMETT_IDPACIENTE) "
                            + "LEFT JOIN BIOSLIS.DATOS_FICHAS fichas ON (fichas.DF_NORDEN = muestras.DFM_NORDEN) "
                            + "WHERE  muestras.DFM_NORDEN LIKE '%" + orderId + "%' AND "
                            + date_strings
                            + "LOWER(pacientes.DP_NOMBRES) LIKE '%" + names.toLowerCase() + "%' AND "
                            + "(LOWER(pacientes.DP_PRIMERAPELLIDO) LIKE '%" + surname.toLowerCase() + "%' OR "
                            + "LOWER(pacientes.DP_SEGUNDOAPELLIDO) LIKE '%" + surname.toLowerCase() + "%') AND "
                            + "pacientes.DP_IDTIPODOCUMENTO LIKE '%" + documentType + "%' AND "
                            + "pacientes.DP_NRODOCUMENTO LIKE '%" + documentId + "%' "
                            + atencion_str
                            + servicio_str);
            for (Object taskData : query.list()) {
                Object[] task = (Object[]) taskData;
                if (task != null) {
                    result.add(new HashMap<String, String>() {
                        {
                            put("id", task[0].toString());
                            put("date", task[1].toString());
                            put("orderNumber", task[2].toString());
                            put("name", (task[3] == null ? "" :  task[3].toString()) + " " + (task[4] == null ? "" :  task[4].toString())
                                    + " " +   (task[5] == null ? " " : task[5].toString()));
                            put("task", "N".equals(task[8].toString()) ? "INICIO"
                                    : "N".equals(task[9].toString()) ? "SIEMBRA"
                                            : "N".equals(task[10].toString()) ? "INCUBAR"
                                                    : "N".equals(task[11].toString()) ? "REV. PLACAS"
                                                            : "N".equals(task[12].toString()) ? "PRUEBAS"
                                                                    : "N".equals(task[13].toString()) ? "IDENTIFICACION"
                                                                            : "SENSIBILIDAD");
                            put("examId", task[6].toString());
                            put("testId", task[7].toString());
                            put("patientId", task[14].toString());
                        }
                    });
                }
            }
        } finally {
            session.close();
        }
        return result;

    }

    public HashMap<String, String> getOxigenations() {
        HashMap<String, String> result = new HashMap<String, String>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT LDVBO_IDBACOXIGENACION, LDVBO_DESCOXIGENACION "
                            + "FROM BIOSLIS.LDV_BACOXIGENACION");
            for (Object oxiData : query.list()) {
                Object[] oxi = (Object[]) oxiData;
                if (oxi != null) {
                    result.put(oxi[0].toString(), oxi[1].toString());
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public HashMap<String, String> getTemperatures() {
        HashMap<String, String> result = new HashMap<String, String>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT LDVBT_IDBACTEMPERATURA, LDVBT_DESCRIPCION "
                            + "FROM BIOSLIS.LDV_BACTEMPERATURAS");
            for (Object tempData : query.list()) {
                Object[] temp = (Object[]) tempData;
                if (temp != null) {
                    result.put(temp[0].toString(), temp[1].toString());
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public HashMap<String, String> getRevisionTimes() {
        HashMap<String, String> result = new HashMap<String, String>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT LDVTRP_IDTIEMPOREVISIONPLACA, LDVTRP_DESCTIEMPOREVISION "
                            + "FROM BIOSLIS.LDV_BACTIEMPOSREVISIONPLACAS");
            for (Object revData : query.list()) {
                Object[] rev = (Object[]) revData;
                if (rev != null) {
                    result.put(rev[0].toString(), rev[1].toString());
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public void updateSeedingDates(HashMap<String, String> data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Date seedingDate = " ".equals(data.get("seedingDate")) || "".equals(data.get("seedingDate")) ? null
                    : new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(data.get("seedingDate"));
            Date reseedingDate = " ".equals(data.get("reseedingDate")) || "".equals(data.get("reseedingDate")) ? null
                    : new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(data.get("reseedingDate"));
            Query query = session.createSQLQuery(
                    "UPDATE BIOSLIS.DATOS_FICHASEXAMENES "
                            + "SET DFE_BACFECHAINICIO = TO_DATE(:seedingDate, 'YYYY-MM-DD HH24:MI'), "
                            + "DFE_BACFECHAINICIO2 = TO_DATE(:reseedingDate ,'YYYY-MM-DD HH24:MI') "
                            + "WHERE DFE_NORDEN = :orderId AND DFE_IDEXAMEN = :examId");
            query.setParameter("seedingDate",
                    seedingDate == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(seedingDate));
            query.setParameter("reseedingDate",
                    reseedingDate == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reseedingDate));
            query.setParameter("examId", data.get("examId"));
            query.setParameter("orderId", data.get("orderId"));
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    public List<HashMap<String, String>> getRevisionPlacasList(String seedingId) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT DRP_NUMEROREVISION, DRP_DESCTIEMPOREVISION, DRP_DESCESTADOSIEMBRA, DRP_NOTAREVISION FROM BIOSLIS.DATOS_REVISIONESPLACAS "
                            + "WHERE DRP_IDMUESTRASIEMBRA = :seedingId");
            query.setParameter("seedingId", seedingId);
            for (Object revData : query.list()) {
                Object[] rev = (Object[]) revData;
                if (rev != null) {
                    result.add(new HashMap<String, String>() {
                        {
                            put("id", rev[0] == null ? "" : rev[0].toString());
                            put("time", rev[1] == null ? "" : rev[1].toString());
                            put("status", rev[2] == null ? "" : rev[2].toString());
                            put("note", rev[3] == null ? "" : rev[3].toString());
                            put("seedingId", seedingId);
                        }
                    });
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public void updateRevisionPlacas(HashMap<String, String> data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("MERGE INTO BIOSLIS.DATOS_REVISIONESPLACAS rev "
                    + "USING (SELECT :seedingId AS seedingId, :revision AS revisionId FROM dual) src "
                    + "ON (rev.DRP_IDMUESTRASIEMBRA = src.seedingId AND rev.DRP_NUMEROREVISION = src.revisionId) "
                    + "WHEN MATCHED THEN "
                    + "UPDATE SET DRP_DESCTIEMPOREVISION = :time, DRP_DESCESTADOSIEMBRA = :status, DRP_NOTAREVISION = :note "
                    + "WHEN NOT MATCHED THEN "
                    + "INSERT (DRP_IDMUESTRASIEMBRA, DRP_NUMEROREVISION, DRP_DESCTIEMPOREVISION, DRP_DESCESTADOSIEMBRA, DRP_NOTAREVISION) "
                    + "VALUES (src.seedingId, src.revisionId, :time, :status, :note)");

            query.setParameter("seedingId", data.get("seedingId"));
            query.setParameter("revision", data.get("revision"));
            query.setParameter("time", data.get("time"));
            query.setParameter("status", data.get("status"));
            query.setParameter("note", data.get("note"));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void delRevisionPlacas(HashMap<String, String> data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM BIOSLIS.DATOS_REVISIONESPLACAS "
                    + "WHERE DRP_IDMUESTRASIEMBRA = :seedingId AND DRP_NUMEROREVISION = :revision");
            query.setParameter("seedingId", data.get("seedingId"));
            query.setParameter("revision", data.get("revision"));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<HashMap<String, String>> getSampleTestList(String sampleId, String examId, String testId) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT DMP_IDMUESTRAPRUEBA, DMP_IDPRUEBA, DMP_IDPACIENTE, DMP_NOTAPRUEBA "
                            + "FROM BIOSLIS.DATOS_MUESTRASPRUEBAS "
                            + "WHERE DMP_IDMUESTRA = :sampleId AND DMP_IDEXAMEN = :examId AND DMP_IDTEST = :testId");
            query.setParameter("sampleId", sampleId);
            query.setParameter("examId", examId);
            query.setParameter("testId", testId);
            for (Object testData : query.list()) {
                Object[] test = (Object[]) testData;
                if (test != null) {
                    result.add(new HashMap<String, String>() {
                        {
                            put("id", test[0] == null ? "" : test[0].toString());
                            put("pruebaId", test[1] == null ? "" : test[1].toString());
                            put("patientId", test[2] == null ? "" : test[2].toString());
                            put("note", test[3] == null ? "" : test[3].toString());
                        }
                    });
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public void updateSampleTest(HashMap<String, String> data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("INSERT INTO BIOSLIS.DATOS_MUESTRASPRUEBAS "
                    + "(DMP_IDMUESTRA, DMP_IDEXAMEN, DMP_IDTEST, DMP_IDPRUEBA, DMP_IDPACIENTE, DMP_NOTAPRUEBA) "
                    + "VALUES (:sampleId, :examId, :testId, :pruebaId, :patientId, :note) ");
            query.setParameter("sampleId", data.get("sampleId"));
            query.setParameter("examId", data.get("examId"));
            query.setParameter("testId", data.get("testId"));
            query.setParameter("pruebaId", data.get("pruebaId"));
            query.setParameter("patientId", data.getOrDefault("patientId", null));
            query.setParameter("note", data.getOrDefault("note", ""));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<String> getInfectionType() {
        List<String> result = new ArrayList<String>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT CBTI_DESCRIPCION FROM BIOSLIS.CFG_BACTIPOSINFECCIONES");
            for (Object type : query.list()) {
                if (type != null) {
                    result.add(type.toString());
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public List<HashMap<String, String>> getTestMOList(String orderId, String examId, String testId) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();  
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT DFETMO_IDBACMICROORGANISMO, BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_DESCRIPCION, "
                            + "DFETMO_IDBACRECUENTOCOLONIAS, DFETMO_IDBACTIPOINFECCION, DFETMO_IDBACANTIBIOGRAMA, "
                            + "DFETMO_IDTESTMOMARCADORR "
                            + "FROM BIOSLIS.DATOS_FICHASEXAMENESTESTMO INNER JOIN BIOSLIS.CFG_BACMICROORGANISMOS "
                            + "ON DFETMO_IDBACMICROORGANISMO = BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_IDBACMICROORGANISMO "
                            + "WHERE DFETMO_NORDEN = :orderId AND "
                            + "DFETMO_IDEXAMEN = :examId AND "
                            + "DFETMO_IDTEST = :testId");
            query.setParameter("orderId", orderId);
            query.setParameter("examId", examId);
            query.setParameter("testId", testId);
            for (Object testData : query.list()) {
                Object[] test = (Object[]) testData;
                if (test != null) {
                    result.add(new HashMap<String, String>() {
                        {
                            put("orderId", orderId);
                            put("examId", examId);
                            put("testId", testId);
                            put("microorganismId", test[0] == null ? "" : test[0].toString());
                            put("microorganismName", test[1] == null ? "" : test[1].toString());
                            put("colonyCountId", test[2] == null ? "" : test[2].toString());
                            put("infectionTypeId", test[3] == null ? "" : test[3].toString());
                            put("antibiogramId", test[4] == null ? "" : test[4].toString());
                            put("resistanceId", test[5] == null ? "" : test[5].toString());
                        }
                    });
                }
            }
        }catch(Exception e ) {
            e.printStackTrace();
        } finally {
            session.close();
         
        }
        return result;
    }

    
    public void updateTestMO(HashMap<String, String> data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("BEGIN UPDATE BIOSLIS.DATOS_FICHASEXAMENESTESTMO SET "
                    + "DFETMO_IDBACMICROORGANISMO = :microorganismId, "
                    + "DFETMO_IDBACRECUENTOCOLONIAS = :colonyCountId, "
                    + "DFETMO_IDBACANTIBIOGRAMA = :antibiogramId, "
                    + "DFETMO_IDBACTIPOINFECCION = :infectionTypeId, "
                    + "DFETMO_IDTESTMOMARCADORR = :resistanceId, "
                    + "DFETMO_FECHAORDEN = TO_DATE(:orderDate, 'dd/mm/yyyy hh24:mi:ss') "
                    + "WHERE DFETMO_NORDEN = :orderId AND DFETMO_IDEXAMEN = :examId AND "
                    + "DFETMO_IDTEST = :testId AND DFETMO_IDBACMICROORGANISMO = :microorganismId ; "
                    + "IF (SQL%rowcount = 0) THEN "
                    + "INSERT INTO BIOSLIS.DATOS_FICHASEXAMENESTESTMO "
                    + "(DFETMO_NORDEN, DFETMO_IDEXAMEN, DFETMO_IDTEST, DFETMO_IDBACMICROORGANISMO, "
                    + "DFETMO_IDBACRECUENTOCOLONIAS, DFETMO_IDBACTIPOINFECCION, DFETMO_IDBACANTIBIOGRAMA, DFETMO_IDTESTMOMARCADORR, DFETMO_FECHAORDEN) "
                    + "VALUES (:orderId, :examId, :testId, :microorganismId, :colonyCountId, :infectionTypeId, :antibiogramId, :resistanceId, TO_DATE(:orderDate, 'dd/mm/yyyy hh24:mi:ss')) ; "
                    + "END IF; END;");
            query.setParameter("orderId", data.get("orderId"));
            query.setParameter("examId", data.get("examId"));
            query.setParameter("testId", data.get("testId"));
            query.setParameter("microorganismId", data.get("microorganismId"));
            query.setParameter("colonyCountId", data.getOrDefault("colonyCountId", ""));
            query.setParameter("infectionTypeId", data.getOrDefault("infectionTypeId", ""));
            query.setParameter("antibiogramId", data.getOrDefault("antibiogramId", ""));
            query.setParameter("resistanceId", data.getOrDefault("resistanceId", ""));
            query.setParameter("orderDate", data.getOrDefault("orderDate", "") );
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void delTestMO(HashMap<String, String> data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM BIOSLIS.DATOS_FICHASEXAMENESTESTMO "
                    + "WHERE DFETMO_NORDEN = :orderId AND "
                    + "DFETMO_IDEXAMEN = :examId AND "
                    + "DFETMO_IDTEST = :testId AND "
                    + "DFETMO_IDBACMICROORGANISMO = :microorganismId");
            query.setParameter("orderId", data.get("orderId"));
            query.setParameter("examId", data.get("examId"));
            query.setParameter("testId", data.get("testId"));
            query.setParameter("microorganismId", data.get("microorganismId"));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<HashMap<String, String>> getResistanceMethodResultList() {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            Query query = session.createSQLQuery("SELECT LDVBRMR_IDRESULTADOMARCADORR, "
                    + "LDVBRMR_DESCRIPCIÓN FROM BIOSLIS.LDV_BACRESULTADOSMARCADORR");
            for (Object testData : query.list()) {
                Object[] test = (Object[]) testData;
                if (test != null) {
                    result.add(new HashMap<String, String>() {
                        {
                            put("id", test[0] == null ? "" : test[0].toString());
                            put("name", test[1] == null ? "" : test[1].toString());
                        }
                    });
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public List<HashMap<String, String>> getAntibioticResultList() {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            Query query = session.createSQLQuery("SELECT LDVBI_IDBACINTERPRETACION, "
                    + "LDVBI_INTERPRETACION FROM BIOSLIS.LDV_BACINTERPRETACIONES");
            for (Object testData : query.list()) {
                Object[] test = (Object[]) testData;
                if (test != null) {
                    result.add(new HashMap<String, String>() {
                        {
                            put("id", test[0] == null ? "" : test[0].toString());
                            put("name", test[1] == null ? "" : test[1].toString());
                        }
                    });
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public List<HashMap<String, String>> getMOAntibioticsList(String orderId, String examId, String testId,
            String moId) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            Query query = session.createSQLQuery(
                    "SELECT BIOSLIS.CFG_BACANTIBIOTICOS.CBA_DESCRIPCION, DFETA_CIM, "
                            + "DFETA_KIRBYBAUER, DFETA_INCLUIRENINFORME, DFETA_INTERPRETACION, DFETA_IDANTIBIOTICO "
                            + "FROM BIOSLIS.DATOS_FICHASEXAMENESTESTATB " + "INNER JOIN BIOSLIS.CFG_BACANTIBIOTICOS "
                            + "ON BIOSLIS.DATOS_FICHASEXAMENESTESTATB.DFETA_IDANTIBIOTICO = BIOSLIS.CFG_BACANTIBIOTICOS.CBA_IDBACANTIBIOTICO "
                            + "WHERE DFETA_NORDEN = :orderId AND "
                            + "DFETA_IDEXAMEN = :examId AND "
                            + "DFETA_IDTEST = :testId AND "
                            + "DFETA_IDMICROORGANISMO = :microorganismId");
            query.setParameter("orderId", orderId);
            query.setParameter("examId", examId);
            query.setParameter("testId", testId);
            query.setParameter("microorganismId", moId);
            for (Object abData : query.list()) {
                Object[] ab = (Object[]) abData;
                if (ab != null) {
                    result.add(new HashMap<String, String>() {
                        {
                            put("antibioticName", ab[0] == null ? "" : ab[0].toString());
                            put("cim", ab[1] == null ? "" : ab[1].toString());
                            put("diameter", ab[2] == null ? "" : ab[2].toString());
                            put("includeinreport",
                                    ab[3] == null ? "false" : "S".equals(ab[3].toString()) ? "true" : "false");
                            put("interpretation", ab[4] == null ? "" : ab[4].toString());
                            put("antibioticId", ab[5] == null ? "" : ab[5].toString());
                        }
                    });
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public void updateMOAntibiotic(HashMap<String, String> data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("BEGIN UPDATE BIOSLIS.DATOS_FICHASEXAMENESTESTATB "
                    + "SET DFETA_CIM = :cim, "
                    + "DFETA_KIRBYBAUER = :diameter, "
                    + "DFETA_INCLUIRENINFORME = :includeInReport, "
                    + "DFETA_IDPACIENTE = :patientId, "
                    + "DFETA_INTERPRETACION = :interpretation "
                    + "WHERE DFETA_NORDEN = :orderId AND "
                    + "DFETA_IDEXAMEN = :examId AND "
                    + "DFETA_IDTEST = :testId AND "
                    + "DFETA_IDMICROORGANISMO = :microorganismId AND "
                    + "DFETA_IDANTIBIOTICO = :antibioticId ; "
                    + "IF (SQL%rowcount = 0) THEN "
                    + "INSERT INTO BIOSLIS.DATOS_FICHASEXAMENESTESTATB "
                    + "(DFETA_NORDEN, DFETA_IDEXAMEN, DFETA_IDTEST, DFETA_IDMICROORGANISMO, "
                    + "DFETA_IDANTIBIOTICO, DFETA_CIM, DFETA_KIRBYBAUER, DFETA_INCLUIRENINFORME, "
                    + "DFETA_INTERPRETACION, DFETA_IDPACIENTE) "
                    + "VALUES (:orderId, :examId, :testId, :microorganismId, "
                    + ":antibioticId, :cim, :diameter, :includeInReport, "
                    + ":interpretation, :patientId); "
                    + "END IF; END;");
            query.setParameter("orderId", data.get("orderId"));
            query.setParameter("examId", data.get("examId"));
            query.setParameter("testId", data.get("testId"));
            query.setParameter("microorganismId", data.get("microorganismId"));
            query.setParameter("antibioticId", data.get("antibioticId"));
            query.setParameter("cim", data.get("cim"));
            query.setParameter("diameter", data.get("diameter"));
            query.setParameter("includeInReport", "true".equals(data.get("includeinreport")) ? "S" : "N");
            query.setParameter("interpretation", data.get("interpretation"));
            query.setParameter("patientId", data.getOrDefault("patientId", "0"));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void delMOAntibiotic(HashMap<String, String> data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM BIOSLIS.DATOS_FICHASEXAMENESTESTATB "
                    + "WHERE DFETA_NORDEN = :orderId AND "
                    + "DFETA_IDEXAMEN = :examId AND "
                    + "DFETA_IDTEST = :testId AND "
                    + "DFETA_IDMICROORGANISMO = :microorganismId AND "
                    + "DFETA_IDANTIBIOTICO = :antibioticId");
            query.setParameter("orderId", data.get("orderId"));
            query.setParameter("examId", data.get("examId"));
            query.setParameter("testId", data.get("testId"));
            query.setParameter("microorganismId", data.get("microorganismId"));
            query.setParameter("antibioticId", data.get("antibioticId"));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<HashMap<String, String>> getMOResistanceList(String moResistanceId) {
        List<HashMap<String, String>> result = new ArrayList<>();
        if (moResistanceId == null || moResistanceId.isEmpty()) {
            return result;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createSQLQuery("SELECT DBTMMR_IDMARCADORRESISTENCIA, "
                    + "DBTMMR_IDRESULTADOMARCADORR "
                    + "FROM BIOSLIS.DATOS_BACTESTMOMARCADORESR "
                    + "WHERE DBTMMR_IDTESTMOMARCADORR = :resistanceId");
            query.setParameter("resistanceId", moResistanceId);
            for (Object resistanceData : query.list()) {
                Object[] resistance = (Object[]) resistanceData;
                if (resistance != null) {
                    result.add(new HashMap<String, String>() {
                        {
                            put("marcadorId", resistance[0] == null ? "" : resistance[0].toString());
                            put("resultId", resistance[1] == null ? "" : resistance[1].toString());
                        }
                    });
                }
            }
        } finally {
            session.close();
        }
        return result;
    }

    public void updateMOResistance(HashMap<String, String> data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (data.getOrDefault("resistanceId", "").isEmpty())
            data.put("resistanceId", getMethodListNewIndex());
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("BEGIN UPDATE BIOSLIS.DATOS_BACTESTMOMARCADORESR "
                    + "SET DBTMMR_IDRESULTADOMARCADORR = :resultId "
                    + "WHERE DBTMMR_IDTESTMOMARCADORR = :resistanceId AND "
                    + "DBTMMR_IDMARCADORRESISTENCIA = :marcadorId ; "
                    + "IF (SQL%rowcount = 0) THEN "
                    + "INSERT INTO BIOSLIS.DATOS_BACTESTMOMARCADORESR "
                    + "(DBTMMR_IDTESTMOMARCADORR, DBTMMR_IDMARCADORRESISTENCIA, "
                    + "DBTMMR_IDRESULTADOMARCADORR) "
                    + "VALUES (:resistanceId, :marcadorId, :resultId); "
                    + "END IF; END;");
            query.setParameter("resistanceId", data.get("resistanceId"));
            query.setParameter("marcadorId", data.get("marcadorId"));
            query.setParameter("resultId", data.get("resultId"));
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public String getMethodListNewIndex() {
        String result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT BIOSLIS.SEQ_DATOS_BACTESTMOMARCADORESR.NEXTVAL FROM DUAL");
            result = query.uniqueResult().toString();
        } finally {
            session.close();
        }
        return result;
    }

    private void logCreation(HashMap<String, String> data, String table, String id) {
        String date = "TO_DATE('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())
                + "','YYYY-MM-DD HH24:MI:SS')";
        String queryStr = "INSERT INTO BIOSLIS.LOG_CFGBACTABLAS (LCBT_NOMBRETABLA, LCBT_NOMBRECAMPO, "
                + " LCBT_IDACCIONDATO, LCBT_VALORNUEVO, LCBT_IDUSUARIO, LCBT_FECHAMODIFICACION, LCBT_IPUSUARIO, "
                + " LCBT_NOMBREEQUIPO, LCBT_IDTABLA) VALUES ";
        for (HashMap.Entry<String, String> entry : data.entrySet()) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query query = session.createSQLQuery(queryStr + " ('" + table + "', '" + entry.getKey() + "', '1', '"
                        + entry.getValue() + "', '1', " + date + ", '1.1.1.1', '1','" + id + "')");
                query.executeUpdate();
                Transaction transaction = session.getTransaction();
                if (!transaction.wasCommitted())
                    transaction.commit();
            } finally {
                session.close();
            }
        }
    }

    private void logUpdate(HashMap<String, String> prevData, HashMap<String, String> newData, String table, String id) {
        String date = "TO_DATE('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())
                + "','YYYY-MM-DD HH24:MI:SS')";
        String queryStr = "INSERT INTO BIOSLIS.LOG_CFGBACTABLAS (LCBT_NOMBRETABLA, LCBT_NOMBRECAMPO, "
                + " LCBT_IDACCIONDATO, LCBT_VALORANTERIOR, LCBT_VALORNUEVO, LCBT_IDUSUARIO, LCBT_FECHAMODIFICACION, LCBT_IPUSUARIO, "
                + " LCBT_NOMBREEQUIPO, LCBT_IDTABLA) VALUES ";
        for (HashMap.Entry<String, String> entry : newData.entrySet()) {
            if (entry.getValue().equals(prevData.get(entry.getKey())))
                continue;
            Session session = HibernateUtil.getSessionFactory().openSession();

            try {
                session.beginTransaction();

                Query query = session.createSQLQuery(
                        queryStr + " ('" + table + "', '" + entry.getKey() + "', '2', '" + prevData.get(entry.getKey())
                                + "', '" + entry.getValue() + "', '1', " + date + ", '1.1.1.1', '1','" + id + "')");
                query.executeUpdate();
                Transaction transaction = session.getTransaction();
                if (!transaction.wasCommitted())
                    transaction.commit();
            } finally {
                session.close();
            }
        }
    }

}
