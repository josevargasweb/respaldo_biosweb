package com.grupobios.bioslis.microbiologia.dao;

import java.util.HashMap;
import java.util.List;

public interface ParameterDAO {
    public HashMap<String, Object> getSearchParameters();
    public List<String> getBodyPartParameters();
    public List<String> getSampleTypeParameters();
    public HashMap<String, Object> getNewTestParameters(String testName);
    public HashMap<String, Object> getAntibiogramParameters();
    public List<String> getAntibiogramAntibiotics(String antibiogramName);
}
