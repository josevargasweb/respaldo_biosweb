package com.grupobios.bioslis.bs.microbiologia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import java.util.List;
import java.util.HashMap;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBExamDAO;
import com.grupobios.bioslis.microbiologia.dao.ParameterDAO;
import com.grupobios.bioslis.microbiologia.entity.MBExam;
import com.grupobios.bioslis.microbiologia.entity.MBTest;

@ExtendWith(SystemStubsExtension.class)
public class TestBuilderTest {
/*
    @Test
    public void tesNewTestBuilder() {
        DAOFactory daoFactory = new DAOFactory();
        ParameterDAO pDao = daoFactory.getDAO("Parameter");
        //HashMap<String, Object> testParameters = pDao.getNewTestParameters("HEMOCULTIVO 2DO MO");

        //System.out.println(testParameters);

        DAOFactory daoExamFactory = new DAOFactory();
        MBExamDAO dao = daoExamFactory.getDAO("MBExam");
        MBExam exam = dao.getExamByIds(142, 35);

        TestBuilder builder = new TestBuilder();

        MBTest test = builder.buildTestForExam(exam, "HEMOCULTIVO 2DO MO");

        System.out.println("******** DATOS TEST *********");
        System.out.println(test.getTestName());
        System.out.println(test.getResult());
        System.out.println(test.getResultType());
        System.out.println(test.getResultOptions());
        System.out.println(test.getStatus());
        System.out.println(test.getStatusOptions());
        System.out.println(test.getSampleType());
        System.out.println(test.getTestCode());
        System.out.println(test.getSamplePrefix());
        System.out.println(test.getBodyPart());
        System.out.println(test.getCriticalValuesAsText());
        System.out.println("resultDate: " + test.getResultDate());
        System.out.println("seedingDate: " + test.getSeedingDate());
        System.out.println("reseedingDate: " + test.getReseedingDate());
        System.out.println(test.getEvents());
        
    }*/
}
