package com.grupobios.bioslis.bs.microbiologia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import java.util.List;
import java.util.HashMap;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBConfigurationDAO;

@ExtendWith(SystemStubsExtension.class)
public class MBConfigurationTest {
/*
    @Test
    public void microorganismGetterTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> testList = moDao.getMOList("", "", "");

        System.out.println(testList);
        String sample_code = testList.get(1).get("id");
        HashMap<String, String> testMO = moDao.getMicroorganism(sample_code);

        System.out.println(testMO);
    }

    @Test
    public void microorganismParametersGetterTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> genders = moDao.getGenderOptions();
        System.out.println(genders);

        List<HashMap<String, String>> morphologies = moDao.getMorphologyOptions();
        System.out.println(morphologies);

        List<HashMap<String, String>> stainings = moDao.getStainingOptions();
        System.out.println(stainings);
    }

    @Test
    public void microorganismUpdateTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");

        List<HashMap<String, String>> testList = moDao.getMOList("", "", "");
        String sample_id = testList.get(testList.size() - 1).get("id");
        HashMap<String, String> testMO = moDao.getMicroorganism(sample_id);
        if ("true".equals(testMO.get("active"))) {
            testMO.put("active", "false");
        } else {
            testMO.put("active", "true");
        }
        moDao.updateMicroorganism(testMO);

        HashMap<String, String> testMO2 = moDao.getMicroorganism(sample_id);
        if (!testMO.get("active").equals(testMO2.get("active"))) {
            throw new java.lang.Error("Unable to update Microorganism");
        }
        if ("true".equals(testMO2.get("active"))) {
            testMO2.put("active", "false");
        } else {
            testMO2.put("active", "true");
        }
        moDao.updateMicroorganism(testMO2);

        System.out.println(testMO2);

    }

    @Test
    public void microorganismCreateTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");

        List<HashMap<String, String>> testList = moDao.getMOList("", "", "");
        String sample_code = testList.get(testList.size() - 1).get("id");
        HashMap<String, String> referenceMO = moDao.getMicroorganism(sample_code);

        referenceMO.put("id", "");
        referenceMO.put("code", "TEST");
        moDao.createMicroorganism(referenceMO);

        HashMap<String, String> createdMO = moDao.getMOList("TEST", "", "").get(0);
        System.out.println(createdMO.get("id") + " : " + createdMO.get("code"));

        moDao.deleteMicroorganism(createdMO.get("id"), createdMO.get("code"));
    }

    @Test
    public void AntibioticGetterTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> testList = moDao.getABList("", "", "", "true");

        System.out.println(testList);
        String sample_id = testList.get(1).get("id");
        HashMap<String, String> testAB = moDao.getAntibiotic(sample_id);

        System.out.println(testAB);
    }

    @Test
    public void AntibioticResistanceTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> testList = moDao.getABResistanceList("3");

        System.out.println(testList);
    }

    @Test
    public void CultureMediumGetterTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> testList = moDao.getCMList("", "", "true");

        System.out.println(testList);
        String sample_id = testList.get(1).get("id");
        HashMap<String, String> testCM = moDao.getCultureMedium(sample_id);

        System.out.println(testCM);
    }

    @Test
    public void ResistanceMethodsGetterTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> testList = moDao.getRMList("", "", "true");

        System.out.println(testList);
        String sample_id = testList.get(1).get("id");
        HashMap<String, String> testRM = moDao.getResistanceMethod(sample_id);

        System.out.println(testRM);
    }

    @Test
    public void PruebasManualesGetterTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> testList = moDao.getPMList("", "", "true");

        System.out.println(testList);
        String sample_id = testList.get(1).get("id");
        HashMap<String, String> testPM = moDao.getPruebaManual(sample_id);

        System.out.println(testPM);
    }

    @Test
    public void BodyAreaGetterTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> testList = moDao.getBAList("", "", "true");

        System.out.println(testList);
        String sample_id = testList.get(1).get("id");
        HashMap<String, String> testBA = moDao.getBodyArea(sample_id);

        System.out.println(testBA);
    }

    @Test
    public void ColonyCountGetterTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> testList = moDao.getCCList("", "", "true");

        System.out.println(testList);
        String sample_id = testList.get(1).get("id");
        HashMap<String, String> testCC = moDao.getColonyCount(sample_id);

        System.out.println(testCC);
    }

    @Test
    public void AntibiogramGetterTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBConfigurationDAO moDao = daoFactory.getDAO("MBConfiguration");
        List<HashMap<String, String>> testList = moDao.getAGList("", "", "true");

        System.out.println(testList);
        String sample_id = testList.get(1).get("id");
        HashMap<String, Object> testAG = moDao.getAntibiogram(sample_id);

        System.out.println(testAG);
    }*/
}
