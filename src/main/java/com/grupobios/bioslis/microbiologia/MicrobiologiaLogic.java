package com.grupobios.bioslis.microbiologia;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MicrobiologiaDAO;

public class MicrobiologiaLogic {

    private static DAOFactory daoFactory;

    public MicrobiologiaLogic(){
        daoFactory = new DAOFactory();
    }

    public String saySomething(){
        MicrobiologiaDAO mDao = daoFactory.getDAO();
        if(mDao.getAll().size() > 0){
            return mDao.getAll().get(0).getName();
        } else {
            return "There's nothing stored for Microbiologia";
        }
        
    }
    
}
