package com.grupobios.bioslis.entity.microbiologia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import java.util.List;
import java.util.HashMap;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBOrderDAO;

@ExtendWith(SystemStubsExtension.class)
public class MBOrderTest {
/*
    private HashMap<String, String> sqlQuerySearchs = new HashMap<String, String>() {{
        
        /*
        put("startId", "142");
        put("endId", "142");
        put("startDate", "2021-01-10");
        put("endDate", "2021-07-02");
        put("atentionType", "1"); //
        put("exam", "12"); //
        put("section", "14"); //
        put("names", "AN");
        put("surname", "SA");
        put("documentType", "1"); //
        put("documentId", "19100247-4");
        put("serviceId", "2");
        put("statusPending", "true");
        put("statusForSignature", "true");
        */
    //}};
/*
    @Test
    public void tesMBOrderDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBOrderDAO mDao = daoFactory.getDAO("MBOrder");
        List<MBOrder> orders = mDao.search(this.sqlQuerySearchs);
        for(MBOrder o : orders){
            System.out.println("--------------");
            System.out.println(o.getId());
            System.out.println(o.getPatient().getFullName());
            System.out.println(o.getPatient().getEmail());
            System.out.println(o.getService());
            for(HashMap<String, String> event : o.getEvents()){
                System.out.println(event);
            }
            for(HashMap<String, String> exam : o.getExamList()){
                System.out.println(exam);
            }
            System.out.println(o.getPhysician().getFullName());
            System.out.println(o.getPhysician().getPhoneNumber());
            System.out.println(o.getPhysician().getEmail());
            System.out.println(o.getPhysician().getEmail());

        }
        
    }*/
}
