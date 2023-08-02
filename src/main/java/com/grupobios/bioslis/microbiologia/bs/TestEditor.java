package com.grupobios.bioslis.microbiologia.bs;

import java.util.Date;
import java.util.HashMap;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBExamDAO;
import com.grupobios.bioslis.microbiologia.dao.MBTestDAO;
import com.grupobios.bioslis.microbiologia.entity.MBExam;
import com.grupobios.bioslis.microbiologia.entity.MBTest;

public class TestEditor {

    private String orderId;
    private String examId;
    private String testId;

    public TestEditor(String orderId, String examId, String testId) {
        this.orderId = orderId;
        this.examId = examId;
        this.testId = testId;
    }

    public void updateData(String result, String microbiologyStatus, String sampleType, String bodyPart,
            Date seedingDate, Date reseedingDate, Date resultDate) {
        DAOFactory daoFactory = new DAOFactory();

        MBTestDAO testDao = daoFactory.getDAO("MBTest");
        MBTest test = testDao.getTestByIds(Integer.parseInt(orderId), Integer.parseInt(examId),
                Integer.parseInt(testId));

        if ("FIRMADO".equals(test.getStatus())) {
            return;
        }

        MBExamDAO examDao = daoFactory.getDAO("MBExam");
        MBExam exam = examDao.getExamByIds(Integer.parseInt(orderId), Integer.parseInt(examId));

        test.setResult(result);
        test.setMicrobiologyStatus(microbiologyStatus);
        test.setSampleType(sampleType);
        test.setBodyPart(bodyPart);

        exam.setResultDate(resultDate);
        exam.setSeedingDate(seedingDate);
        exam.setReseedingDate(reseedingDate);

        testDao.update(test);
        examDao.update(exam);

    }

    public Boolean signTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO testDao = daoFactory.getDAO("MBTest");
        MBTest test = testDao.getTestByIds(Integer.parseInt(orderId), Integer.parseInt(examId),
                Integer.parseInt(testId));

        if ("POSITIVO".equals(test.getMicrobiologyStatus()) || "NEGATIVO".equals(test.getMicrobiologyStatus())) {
            test.setStatus("FIRMADO");
            testDao.update(test);
            return true;
        } else {
            return false;
        }
    }

    public void updateSampleType(String sampleType) {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO testDao = daoFactory.getDAO("MBTest");
        MBTest test = testDao.getTestByIds(Integer.parseInt(orderId), Integer.parseInt(examId),
                Integer.parseInt(testId));
        test.setSampleType(sampleType);
        testDao.update(test);
    }

    public void updateBodyPart(String bodyPart) {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO testDao = daoFactory.getDAO("MBTest");
        MBTest test = testDao.getTestByIds(Integer.parseInt(orderId), Integer.parseInt(examId),
                Integer.parseInt(testId));
        test.setBodyPart(bodyPart);
        testDao.update(test);
    }

    public void createOrUpdateCultureMedium(HashMap<String, Object> cultureMedium) {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO testDao = daoFactory.getDAO("MBTest");
        MBTest test = testDao.getTestByIds(Integer.parseInt(orderId), Integer.parseInt(examId),
                Integer.parseInt(testId));
        test.setCultureMedium(cultureMedium);
        testDao.update(test);
    }
}
