package com.grupobios.bioslis.microbiologia.bs;

import java.util.HashMap;
import java.util.List;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.ParameterDAO;

public class ParameterSearcher {
    
    public HashMap<String, Object> getSearchParameters(){
        ParameterDAO dao = (new DAOFactory()).getDAO("Parameter");
        return dao.getSearchParameters();
    }

    public List<String> getBodyPartParameters(){
        ParameterDAO dao = (new DAOFactory()).getDAO("Parameter");
        return dao.getBodyPartParameters();
    }

    public List<String> getSampleTypeParameters(){
        ParameterDAO dao = (new DAOFactory()).getDAO("Parameter");
        return dao.getSampleTypeParameters();
    }

    public HashMap<String, Object> getAntibiogramParameters(){
        ParameterDAO dao = (new DAOFactory()).getDAO("Parameter");
        return dao.getAntibiogramParameters();
    }

    public List<String> getAntibiogramAntibiotics(String antibiogramName){
        ParameterDAO dao = (new DAOFactory()).getDAO("Parameter");
        return dao.getAntibiogramAntibiotics(antibiogramName);
    }

}
