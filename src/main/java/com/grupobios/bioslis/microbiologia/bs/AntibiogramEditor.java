package com.grupobios.bioslis.microbiologia.bs;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBAntibiogramDAO;
import com.grupobios.bioslis.microbiologia.dao.MBTestDAO;
import com.grupobios.bioslis.microbiologia.entity.MBAntibiogram;
import com.grupobios.bioslis.microbiologia.entity.MBTest;
import com.grupobios.bioslis.microbiologia.rest.MicrobiologiaRestController;

public class AntibiogramEditor {
    static Logger log = LogManager.getLogger(AntibiogramEditor.class.getName());
    
    private String orderId;
    private String examId;
    private String testId;

    public AntibiogramEditor(String orderId, String examId, String testId){
        this.orderId = orderId;
        this.examId = examId;
        this.testId = testId;
    }

    @SuppressWarnings("unchecked")
    public void createOrUpdate(HashMap<String, Object> antibiogramData){        
      
        DAOFactory daoFactory = new DAOFactory();

        MBTestDAO testDao = daoFactory.getDAO("MBTest");
        
        MBTest test = testDao.getTestByIds(
            Integer.parseInt(this.orderId), 
            Integer.parseInt(this.examId), 
            Integer.parseInt(this.testId));
        
        if("FIRMADO".equals(test.getStatus())){   
            log.info("Test firmado no se hace insert ni update ");
            return;
        }
      
        MBAntibiogramDAO dao = daoFactory.getDAO("MBAntibiogram");

        HashMap<Integer, Object> antibioticList = new HashMap<Integer, Object>();
        HashMap<String, Object> antibioticListData = (HashMap<String, Object>) antibiogramData.get("antibioticLists");

        for (Map.Entry<String, Object> entry : antibioticListData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            antibioticList.put(Integer.parseInt(key), value);
        }
    
        MBAntibiogram antibiogram = new MBAntibiogram(
               
            this.orderId,
            this.examId,
            this.testId,
            antibiogramData.get("colonyCount").toString(),
            antibiogramData.get("infectionType").toString(),
            antibioticList
        );
    
        dao.updateOrCreate(antibiogram);
    }

}
