package com.grupobios.bioslis.entity.microbiologia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.channels.NonWritableChannelException;
import java.util.Date;

import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import com.grupobios.bioslis.microbiologia.bs.TestBuilder;
import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBExamDAO;

@ExtendWith(SystemStubsExtension.class)
public class MBExamTest {
/*
    @Test
    public void testMBExamDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBExamDAO dao = daoFactory.getDAO("MBExam");

        MBExam exam1 = dao.getExamByIds(36, 2);
        System.out.println("++++++++++++++++");
        System.out.println(exam1.getExamName());
        System.out.println(exam1.getSample());
        System.out.println(exam1.getBodyPart());
        System.out.println(exam1.getProcessStatus());
        System.out.println(exam1.getSeedingDate());
        System.out.println(exam1.getReseedingDate());
        System.out.println(exam1.isUrgent());
        System.out.println("Internal Note: " + exam1.getInternalNote());
        System.out.println("Exam Note: " + exam1.getExamNote());
        System.out.println("Foorter Note: " + exam1.getFooterInformNote());
        System.out.println(exam1.getSamplingObservation());
        System.out.println(exam1.getSamplingStatus());
        System.out.println(exam1.getSamplingUser());
        System.out.println(exam1.getSamplingDate());
        System.out.println(exam1.getReceptionObservation());
        System.out.println(exam1.getReceptionStatus());
        System.out.println(exam1.getReceptionUser());
        System.out.println(exam1.getReceptionDate());
        System.out.println(exam1.getUser());
        System.out.println(exam1.getEvents());
        System.out.println(exam1.getTestList());
        System.out.println(exam1.getOptionalTests());

        MBExam exam2 = dao.getExamByIds(142, 35);
        System.out.println("++++++++++++++++");
        System.out.println(exam2.getExamName());
        System.out.println(exam2.getSample());
        System.out.println(exam2.getBodyPart());
        System.out.println(exam2.getProcessStatus());
        System.out.println(exam2.getSeedingDate());
        System.out.println(exam2.getReseedingDate());
        System.out.println(exam2.isUrgent());
        System.out.println("Internal Note: " + exam2.getInternalNote());
        System.out.println("Exam Note: " + exam2.getExamNote());
        System.out.println("Foorter Note: " + exam2.getFooterInformNote());
        System.out.println(exam2.getSamplingObservation());
        System.out.println(exam2.getSamplingStatus());
        System.out.println(exam2.getSamplingUser());
        System.out.println(exam2.getSamplingDate());
        System.out.println(exam2.getReceptionObservation());
        System.out.println(exam2.getReceptionStatus());
        System.out.println(exam2.getReceptionUser());
        System.out.println(exam2.getReceptionDate());
        System.out.println(exam2.getUser());
        System.out.println(exam2.getEvents());
        System.out.println(exam2.getTestList());
        System.out.println(exam2.getOptionalTests());
    }*/
/*
    @Test
    public void testEditMBExamNote() {
        DAOFactory daoFactory = new DAOFactory();
        MBExamDAO dao = daoFactory.getDAO("MBExam");

        MBExam exam = dao.getExamByIds(142, 35);
        int eventsCount = exam.getEvents().size(); 

        exam.setInternalNote("This is the first test internal note", "JLIRA");
        exam.setInternalNote("This is the first test internal note", "JLIRA");
        dao.update(exam);
        assertEquals("This is the first test internal note", dao.getExamByIds(142, 35).getInternalNote());
        exam.setInternalNote("This is the second test internal note", "JLIRA");
        dao.update(exam);
        assertEquals("This is the second test internal note", dao.getExamByIds(142, 35).getInternalNote());
        assertEquals(eventsCount + 2, exam.getEvents().size());

        exam.setExamNote("This is the first test exam note");
        exam.setExamNote("This is the first test exam note");
        dao.update(exam);
        assertEquals("This is the first test exam note", dao.getExamByIds(142, 35).getExamNote());
        exam.setExamNote("This is the second test exam note");
        dao.update(exam);
        assertEquals("This is the second test exam note", dao.getExamByIds(142, 35).getExamNote());
        assertEquals(eventsCount + 4, exam.getEvents().size());

        exam.setFooterInformNote("This is the first test footer note");
        exam.setFooterInformNote("This is the first test footer note");
        dao.update(exam);
        assertEquals("This is the first test footer note", dao.getExamByIds(142, 35).getFooterInformNote());
        exam.setFooterInformNote("This is the second test footer note");
        dao.update(exam);
        assertEquals("This is the second test footer note", dao.getExamByIds(142, 35).getFooterInformNote());
        assertEquals(eventsCount + 6, exam.getEvents().size());
        
        System.out.println("**********************");
        System.out.println(dao.getExamByIds(142, 35).getInternalNote());
        System.out.println(dao.getExamByIds(142, 35).getExamNote());
        System.out.println(dao.getExamByIds(142, 35).getFooterInformNote());
        System.out.println(dao.getExamByIds(142, 35).getEvents());
        System.out.println("**********************");


    }
*/ /*
    @Test
    public void testSetMBExamDates() {
        DAOFactory daoFactory = new DAOFactory();
        MBExamDAO dao = daoFactory.getDAO("MBExam");

        MBExam exam = dao.getExamByIds(142, 35);
        int eventsCount = exam.getEvents().size();

        Date now = new Date();

        exam.setSeedingDate(now);
        exam.setReseedingDate(now);
        exam.setResultDate(now);
        dao.update(exam);
        assertTrue(Math.abs(now.getTime() - dao.getExamByIds(142, 35).getSeedingDate().getTime()) < 1000);
        assertTrue(Math.abs(now.getTime() - dao.getExamByIds(142, 35).getReseedingDate().getTime()) < 1000);
        assertTrue(Math.abs(now.getTime() - dao.getExamByIds(142, 35).getResultDate().getTime()) < 1000);
        assertEquals(eventsCount + 3, exam.getEvents().size());

        System.out.println("************UPDATE DATES**********");
        System.out.println(dao.getExamByIds(142, 35).getSeedingDate());
        System.out.println(dao.getExamByIds(142, 35).getReseedingDate());
        System.out.println(dao.getExamByIds(142, 35).getResultDate());
        System.out.println("**********************************");

        exam.setReseedingDate(null);
        dao.update(exam);
        assertEquals(null, dao.getExamByIds(142, 35).getReseedingDate());
        assertEquals(eventsCount + 4, exam.getEvents().size());

    }

    @Test
    public void testSetMBExamSNewOptionalTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBExamDAO dao = daoFactory.getDAO("MBExam");
        MBExam exam = dao.getExamByIds(142, 35);

        MBTest newOptionalTest = (new TestBuilder()).buildTestForExam(exam, "HEMOCULTIVO 2DO MO");

        System.out.println("********* OPTIONAL TEST ************");

        exam.deleteTest("HEMOCULTIVO 2DO MO");
        dao.update(exam);
        int eventsCount = exam.getEvents().size();
        int testCount = exam.getTestList().size();

        exam.setOptionalTest(newOptionalTest);
        dao.update(exam);
        assertEquals(eventsCount + 1, exam.getEvents().size());
        assertEquals(testCount + 1, exam.getTestList().size());

        exam.deleteTest("HEMOCULTIVO 2DO MO");
        dao.update(exam);
        assertEquals(eventsCount + 2, exam.getEvents().size());
        assertEquals(testCount, exam.getTestList().size());
    }*/
}
