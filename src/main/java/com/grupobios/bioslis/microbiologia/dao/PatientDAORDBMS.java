package com.grupobios.bioslis.microbiologia.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import com.grupobios.bioslis.microbiologia.entity.Patient;
import com.grupobios.bioslis.config.HibernateUtil;

public class PatientDAORDBMS implements PatientDAO {
    
    static Logger log = LogManager.getLogger(PatientDAORDBMS.class.getName());
    
    private String sqlQueryGetById = "SELECT BIOSLIS.DATOS_PACIENTES.DP_NOMBRES, BIOSLIS.DATOS_PACIENTES.DP_PRIMERAPELLIDO, BIOSLIS.DATOS_PACIENTES.DP_SEGUNDOAPELLIDO, "
            + "BIOSLIS.DATOS_PACIENTES.DP_FNACIMIENTO, BIOSLIS.LDV_SEXO.LDVS_DESCRIPCION, BIOSLIS.DATOS_PACIENTES.DP_OBSERVACION, "
            + "BIOSLIS.DATOS_PACIENTES.DP_TELEFONO, BIOSLIS.DATOS_PACIENTES.DP_EMAIL, BIOSLIS.DATOS_PACIENTES.DP_NRODOCUMENTO , BIOSLIS.DATOS_PACIENTES.DP_IDTIPODOCUMENTO "
            + "FROM BIOSLIS.DATOS_PACIENTES " + "INNER JOIN BIOSLIS.LDV_SEXO "
            + "ON BIOSLIS.DATOS_PACIENTES.DP_IDSEXO = BIOSLIS.LDV_SEXO.LDVS_IDSEXO "
            + "WHERE BIOSLIS.DATOS_PACIENTES.DP_IDPACIENTE = :patientId";

    private String sqlQueryPathologies = "SELECT BIOSLIS.LDV_PATOLOGIAS.LDVPAT_DESCRIPCION "
            + "FROM BIOSLIS.DATOS_PACIENTESPATOLOGIAS " + "INNER JOIN BIOSLIS.LDV_PATOLOGIAS "
            + "ON BIOSLIS.DATOS_PACIENTESPATOLOGIAS.DPP_IDPATOLOGIA = BIOSLIS.LDV_PATOLOGIAS.LDVPAT_IDPATOLOGIA "
            + "WHERE BIOSLIS.DATOS_PACIENTESPATOLOGIAS.DPP_IDPACIENTE = :patientId";

    
    //AQUI AGREGA TODOS LOS DATOS DEL PACIENTE 
    @Override
    public Patient getById(String patientId,  int orderId ) {    
        Session session = HibernateUtil.getSessionFactory().openSession();
        Patient result = null;;
        try {          
            Query query = session.createSQLQuery(this.sqlQueryGetById);
            
            query.setParameter("patientId", Integer.parseInt(patientId));
            HashMap<String, Object> patientData = parsePatientData((Object[]) query.list().get(0));
            session.close();       
            result = new Patient((String) patientId, (String) patientData.get("names"),
                    (String) patientData.get("firstSurname"), (String) patientData.get("secondSurname"),
                    (Date) patientData.get("dateOfBirth"), (String) patientData.get("gender"),
                    (String) patientData.get("observation"), (String) patientData.get("phoneNumber"),
                    getPatientPathologies(patientId), getOrderList(patientId, orderId), (String) patientData.get("email"),
                   // getPatientPathologies(patientId), getOrderList(patientId), (String) patientData.get("email"),
                    (String) patientData.get("nroDocumento") , (BigDecimal) patientData.get("tipoDocumento") );
        } catch(Exception e) {
           log.error("error en getById paciente "+e.getMessage() );
           e.printStackTrace();
            if(session.isOpen()) {
                session.close();                
            }
            
        }
        return result;
    }

    private List<String> getPatientPathologies(String patientId) {
        List<String> result = new ArrayList<String>(); 
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createSQLQuery(this.sqlQueryPathologies);
            query.setParameter("patientId", Integer.parseInt(patientId));
            for (Object pathology : query.list()) {
                result.add((String) pathology);
                }
        }catch(Exception e) {
            log.error("error en getPatientPathologies "+e.getMessage() );
            e.printStackTrace();
        
        } finally {    
            sesion.close();
        }
        return result;
    }

    private HashMap<String, Object> parsePatientData(Object[] patientData) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            result.put("names", patientData[0] == null ? "" : patientData[0].toString());
            result.put("firstSurname", patientData[1] == null ? "" : patientData[1].toString());
            result.put("secondSurname", patientData[2] == null ? "" : patientData[2].toString());
            result.put("dateOfBirth", patientData[3] == null ? ""
                    : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(patientData[3].toString()));
            result.put("gender", patientData[4] == null ? "" : patientData[4].toString());
            result.put("observation", patientData[5] == null ? "" : patientData[5].toString());
            result.put("phoneNumber", patientData[6] == null ? "" : patientData[6].toString());
            result.put("email", patientData[7] == null ? "" : patientData[7].toString());
            result.put("nroDocumento", patientData[8] == null ? "" : patientData[8].toString());
            result.put("tipoDocumento",  patientData[9] );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<String> getOrderList(String patientId , int orderId) {
        List<String> result = new ArrayList<String>();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createSQLQuery("SELECT BIOSLIS.DATOS_FICHAS.DF_NORDEN " + "FROM BIOSLIS.DATOS_FICHAS "
                    + "WHERE BIOSLIS.DATOS_FICHAS.DF_IDPACIENTE = :patientId  and  BIOSLIS.DATOS_FICHAS.DF_norden < :norden order by BIOSLIS.DATOS_FICHAS.DF_NORDEN  DESC");
            query.setParameter("patientId", Integer.parseInt(patientId));
            query.setParameter("norden", orderId);
            for (Object order : query.list()) {
                result.add(order.toString());
            }
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            sesion.close();
        }
        return result;
    }

}
