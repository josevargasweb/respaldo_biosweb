package com.grupobios.bioslis.entity.microbiologia;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.ParameterDAO;

@ExtendWith(SystemStubsExtension.class)
public class ParameterTest {
/*
    @Test
    public void testParameterDAO() {
        DAOFactory daoFactory = new DAOFactory();
        ParameterDAO dao = daoFactory.getDAO("Parameter");

        HashMap<String, Object> parameters = dao.getSearchParameters();
        List<String> bodyPart = dao.getBodyPartParameters();
        List<String> sampleType = dao.getSampleTypeParameters();
        HashMap<String, Object> antibiogramParameter = dao.getAntibiogramParameters();
        List<String> antibiogramAntibiotics = dao.getAntibiogramAntibiotics("ENTEROBACTERIA");


        System.out.println("--------------");
        System.out.println(parameters);
        System.out.println(bodyPart);
        System.out.println(sampleType);
        System.out.println(antibiogramParameter);
        System.out.println(antibiogramAntibiotics);
        System.out.println(antibiogramParameter.get("methodList"));
        System.out.println(antibiogramParameter.get("methodResults"));
        System.out.println("--------------");
    }*/
}
