package com.grupobios.bioslis.microbiologia.dao;

import java.util.HashMap;
import java.util.List;

public interface MBTaskDAO {
    public HashMap<String, String> getTask(String idMuestra, String idExamen, String idTest);

    public void updateTask(HashMap<String, String> task);

    public void createTask(HashMap<String, String> task);

    public void deleteTask(String idMuestra, String idExamen, String idTest);

    public List<HashMap<String, String>> getTaskList(String orderId, String startDate,
            String endDate, String names, String surname, String documentType,
            String documentId, String atentionType, String serviceId);

    public HashMap<String, String> getOxigenations();

    public HashMap<String, String> getTemperatures();

    public HashMap<String, String> getRevisionTimes();

    public void updateSeedingDates(HashMap<String, String> data);

    public List<HashMap<String, String>> getRevisionPlacasList(String seedingId);

    public void updateRevisionPlacas(HashMap<String, String> data);

    public void delRevisionPlacas(HashMap<String, String> data);

    public List<HashMap<String, String>> getSampleTestList(String sampleId, String examId, String testId);

    public void updateSampleTest(HashMap<String, String> data);

    public List<String> getInfectionType();

    public List<HashMap<String, String>> getTestMOList(String orderId, String examId, String testId);

    public void updateTestMO(HashMap<String, String> data);

    public void delTestMO(HashMap<String, String> data);

    public List<HashMap<String, String>> getResistanceMethodResultList();

    public List<HashMap<String, String>> getAntibioticResultList();

    List<HashMap<String, String>> getMOAntibioticsList(String orderId, String examId,
            String testId, String moId);

    public void updateMOAntibiotic(HashMap<String, String> data);

    public void delMOAntibiotic(HashMap<String, String> data);

    public List<HashMap<String, String>> getMOResistanceList(String moResistanceId);

    public void updateMOResistance(HashMap<String, String> data);

    public String getMethodListNewIndex();
}
