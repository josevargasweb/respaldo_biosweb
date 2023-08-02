package com.grupobios.bioslis.microbiologia.bs;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.ParameterDAO;
import com.grupobios.bioslis.microbiologia.entity.MBExam;
import com.grupobios.bioslis.microbiologia.entity.MBTest;

public class TestBuilder {

    @SuppressWarnings("unchecked")
    public MBTest buildTestForExam(MBExam exam, String testName) {
        DAOFactory daoFactory = new DAOFactory();
        ParameterDAO pDao = daoFactory.getDAO("Parameter");
        HashMap<String, Object> testParameters = pDao.getNewTestParameters("HEMOCULTIVO 2DO MO");
        return new MBTest((String) testParameters.get("testId"), exam.getExamId(), exam.getOrderId(),
                (String) testParameters.get("testName"), (String) testParameters.get("result"),
                (String) testParameters.get("resultType"),
                (ArrayList<HashMap<String, Object>>) testParameters.get("resultOptions"),
                (String) testParameters.get("status"), (List<String>) testParameters.get("statusOptions"),
                (String) testParameters.get("status"), (List<String>) testParameters.get("statusOptions"),
                exam.getSampleData().get("sampleType"), exam.getSampleId(), (String) testParameters.get("sampleCode"),
                (String) testParameters.get("testCode"), exam.getSampleData().get("samplePrefix"),
                exam.getSampleData().get("bodyPart"), exam.getSampleData().get("bodyPartId"), null, null, null,
                new ArrayList<HashMap<String, String>>(), (String) testParameters.get("isCulture"),
                new ArrayList<HashMap<String, Object>>(), "", "1");
    }

}
