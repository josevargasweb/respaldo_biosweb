package com.grupobios.bioslis.entity.microbiologia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import com.grupobios.bioslis.microbiologia.bs.TestBuilder;
import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBExamDAO;
import com.grupobios.bioslis.microbiologia.dao.MBTestDAO;

@ExtendWith(SystemStubsExtension.class)
public class MBTestTest {
/*
    @Test
    public void testMBTestDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO dao = daoFactory.getDAO("MBTest");

        MBTest test = dao.getTestByIds(142, 36, 43);
        System.out.println("******** DATOS TEST *********");
        System.out.println(test.getTestName());
        System.out.println(test.getResult());
        System.out.println(test.getResultType());
        System.out.println(test.getResultOptions());
        System.out.println(test.getStatus());
        System.out.println(test.getStatusOptions());
        System.out.println(test.getMicrobiologyStatus());
        System.out.println(test.getMicrobiologyStatusOptions());
        System.out.println(test.getSampleType());
        System.out.println(test.getTestCode());
        System.out.println(test.getSamplePrefix());
        System.out.println(test.getBodyPart());
        System.out.println(test.getCriticalValuesAsText());
        System.out.println("resultDate: " + test.getResultDate());
        System.out.println("seedingDate: " + test.getSeedingDate());
        System.out.println("reseedingDate: " + test.getReseedingDate());
        System.out.println(test.getEvents());

    }

    @Test
    public void testSetResultToMBTestDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO dao = daoFactory.getDAO("MBTest");
        MBTest testResetStatus = dao.getTestByIds(142, 36, 43);
        testResetStatus.setStatus("PENDIENTE");
        dao.update(testResetStatus);

        MBTest test1 = dao.getTestByIds(142, 36, 43);
        test1.setResult("Negativo");
        dao.update(test1);
        assertEquals("Negativo", dao.getTestByIds(142, 36, 43).getResult());

        int eventsCount = dao.getTestByIds(142, 36, 43).getEvents().size();
        MBTest test2 = dao.getTestByIds(142, 36, 43);
        Date now = new Date();
        test2.setResult("Positivo");
        dao.update(test2);
        assertEquals("Positivo", dao.getTestByIds(142, 36, 43).getResult());
        assertEquals(eventsCount + 1, dao.getTestByIds(142, 36, 43).getEvents().size());
        assertTrue(Math.abs(now.getTime() - dao.getTestByIds(142, 36, 43).getResultDate().getTime()) < 1000);
        assertEquals("DIGITADO", dao.getTestByIds(142, 36, 43).getStatus());
    }

    @Test
    public void testSetStatusToMBTestDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO dao = daoFactory.getDAO("MBTest");

        MBTest test1 = dao.getTestByIds(142, 36, 43);
        test1.setStatus("PENDIENTE");
        dao.update(test1);
        assertEquals("PENDIENTE", dao.getTestByIds(142, 36, 43).getStatus());

        int eventsCount = dao.getTestByIds(142, 36, 43).getEvents().size();
        MBTest test2 = dao.getTestByIds(142, 36, 43);
        System.out.println("****************************");
        System.out.println(test2.getEvents());
        System.out.println(test2.getStatus());
        System.out.println("-------- SET STATUS DIGITADO -----------");
        test2.setStatus("DIGITADO");
        System.out.println(test2.getEvents());
        System.out.println(test2.getStatus());
        System.out.println("****************************");
        dao.update(test2);
        assertEquals("DIGITADO", dao.getTestByIds(142, 36, 43).getStatus());
        assertEquals(eventsCount + 1, dao.getTestByIds(142, 36, 43).getEvents().size());

    }

    @Test
    public void testSetMicrobiologyStatusToMBTestDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO dao = daoFactory.getDAO("MBTest");

        MBTest test1 = dao.getTestByIds(142, 36, 43);
        test1.setMicrobiologyStatus("EN PROCESO");
        dao.update(test1);
        assertEquals("EN PROCESO", dao.getTestByIds(142, 36, 43).getMicrobiologyStatus());

        int eventsCount = dao.getTestByIds(142, 36, 43).getEvents().size();
        MBTest test2 = dao.getTestByIds(142, 36, 43);
        System.out.println("****************************");
        System.out.println(test2.getEvents());
        System.out.println(test2.getStatus());
        System.out.println("-------- SET STATUS POSITIVO -----------");
        test2.setMicrobiologyStatus("POSITIVO");
        System.out.println(test2.getEvents());
        System.out.println(test2.getStatus());
        System.out.println("****************************");

        dao.update(test2);
        assertEquals("POSITIVO", dao.getTestByIds(142, 36, 43).getMicrobiologyStatus());
        assertEquals(eventsCount + 1, dao.getTestByIds(142, 36, 43).getEvents().size());

    }

    @Test
    public void testSetResultDateToMBTestDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO dao = daoFactory.getDAO("MBTest");

        MBTest test1 = dao.getTestByIds(142, 36, 43);
        Date now = new Date();
        int eventsCount = dao.getTestByIds(142, 36, 43).getEvents().size();

        test1.setResultDate(now);
        ;
        dao.update(test1);
        assertTrue(Math.abs(now.getTime() - dao.getTestByIds(142, 36, 43).getResultDate().getTime()) < 1000);
        assertEquals(eventsCount + 1, dao.getTestByIds(142, 36, 43).getEvents().size());

        System.out.println("****************************");
        System.out.println(dao.getTestByIds(142, 36, 43).getEvents());
        System.out.println(dao.getTestByIds(142, 36, 43).getResultDate());

    }

    @Test
    public void testSetSampleTypeToMBTestDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO dao = daoFactory.getDAO("MBTest");

        MBTest test1 = dao.getTestByIds(142, 36, 43);
        test1.setSampleType("SANGRE");
        ;
        dao.update(test1);
        assertEquals("SANGRE", dao.getTestByIds(142, 36, 43).getSampleType());

        int eventsCount = dao.getTestByIds(142, 36, 43).getEvents().size();
        MBTest test2 = dao.getTestByIds(142, 36, 43);
        test2.setSampleType("SALIVA");
        ;
        dao.update(test2);
        assertEquals("SALIVA", dao.getTestByIds(142, 36, 43).getSampleType());
        assertEquals(eventsCount + 1, dao.getTestByIds(142, 36, 43).getEvents().size());

        System.out.println("**************SET SAMPLE TYPE**************");
        System.out.println(dao.getTestByIds(142, 36, 43).getEvents());
        System.out.println(dao.getTestByIds(142, 36, 43).getSampleType());
        System.out.println(dao.getTestByIds(142, 36, 43).getSamplePrefix());

    }

    @Test
    public void testSetBodyPartMBTestDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO dao = daoFactory.getDAO("MBTest");

        MBTest test1 = dao.getTestByIds(142, 36, 43);
        test1.setBodyPart("MANO DERECHA");
        dao.update(test1);
        assertEquals("MANO DERECHA", dao.getTestByIds(142, 36, 43).getBodyPart());

        int eventsCount = dao.getTestByIds(142, 36, 43).getEvents().size();
        MBTest test2 = dao.getTestByIds(142, 36, 43);
        test2.setBodyPart("MANO IZQUIERDA");
        ;
        dao.update(test2);
        assertEquals("MANO IZQUIERDA", dao.getTestByIds(142, 36, 43).getBodyPart());
        assertEquals(eventsCount + 1, dao.getTestByIds(142, 36, 43).getEvents().size());

        System.out.println("**************SET BODY PART**************");
        System.out.println(dao.getTestByIds(142, 36, 43).getEvents());
        System.out.println(dao.getTestByIds(142, 36, 43).getBodyPart());

    }
*/
}
