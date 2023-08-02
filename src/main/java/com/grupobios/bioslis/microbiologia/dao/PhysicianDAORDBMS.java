package com.grupobios.bioslis.microbiologia.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.microbiologia.entity.Physician;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

public class PhysicianDAORDBMS implements PhysicianDAO{

    static Logger log = LogManager.getLogger(PhysicianDAORDBMS.class.getName());
    private String sqlQueryGetById = "SELECT BIOSLIS.CFG_MEDICOS.CM_NOMBRES, BIOSLIS.CFG_MEDICOS.CM_PRIMERAPELLIDO, BIOSLIS.CFG_MEDICOS.CM_SEGUNDOAPELLIDO, " +
                                        "BIOSLIS.CFG_MEDICOS.CM_TELEFONO, BIOSLIS.CFG_MEDICOS.CM_EMAIL " +
                                     "FROM BIOSLIS.CFG_MEDICOS " +
                                     "WHERE BIOSLIS.CFG_MEDICOS.CM_IDMEDICO = :physicianId";


    @Override
    public Physician getById(String physicianId){     
        Session session = HibernateUtil.getSessionFactory().openSession();
        Physician result = null;
        try{
            Query query = session.createSQLQuery(this.sqlQueryGetById);
            query.setParameter("physicianId", Integer.parseInt(physicianId));
            Object[] data = (Object[]) query.list().get(0);
            result = new Physician(
                        physicianId,
                        data[0] != null ? data[0].toString() : "",
                        data[1] != null ? data[1].toString() : "",
                        data[2] != null ? data[2].toString() : "",
                        data[3] != null ? data[3].toString() : "",
                        data[4] != null ? data[4].toString() : ""
                        );
        }catch(Exception e) {
            log.error("error en Physician getById " +e.getMessage() );
            e.printStackTrace();
          } finally {          
            session.close();
        }
        return result;

    }   
}
