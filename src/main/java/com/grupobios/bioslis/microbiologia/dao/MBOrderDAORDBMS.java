package com.grupobios.bioslis.microbiologia.dao;

import com.grupobios.bioslis.common.Constante;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.microbiologia.entity.MBExam;
import com.grupobios.bioslis.microbiologia.entity.MBOrder;
import com.grupobios.bioslis.microbiologia.entity.Patient;
import com.grupobios.bioslis.microbiologia.entity.Physician;
import com.grupobios.bioslis.microbiologia.rest.MicrobiologiaRestController;

public class MBOrderDAORDBMS implements MBOrderDAO {

  static Logger log = LogManager.getLogger(MBOrderDAORDBMS.class.getName());
  private HashMap<String, String> sqlQuerySearchs = new HashMap<String, String>() {
    {
      put("startId", "AND DATOS_FICHAS.DF_NORDEN >= :startId ");
      put("endId", "AND DATOS_FICHAS.DF_NORDEN <= :endId ");
      put("startDate", "AND DATOS_FICHAS.DF_FECHAORDEN >= :startDate ");
      put("endDate", "AND DATOS_FICHAS.DF_FECHAORDEN <= :endDate ");
      put("atentionType", "AND DATOS_FICHAS.DF_IDTIPOATENCION = :atentionType ");
      put("exam", "AND DATOS_FICHASEXAMENES.DFE_IDEXAMEN = :exam ");
      put("section", "AND DATOS_FICHASEXAMENESTEST.DFET_IDSECCION = :section ");
      put("names", "AND DATOS_PACIENTES.DP_NOMBRES LIKE :names ");
      put("surname",
          "AND ((DATOS_PACIENTES.DP_PRIMERAPELLIDO LIKE :surname) OR (DATOS_PACIENTES.DP_SEGUNDOAPELLIDO LIKE :surname)) ");
      put("documentType", "AND DATOS_PACIENTES.DP_IDTIPODOCUMENTO = :documentType ");
      put("documentId", "AND DATOS_PACIENTES.DP_RUN = :documentId ");
      put("serviceId", "AND BIOSLIS.CFG_LOCALIZACIONES.CL_IDSERVICIO = :serviceId ");
    }
  };

  private String sqlQuerySelect = "SELECT DISTINCT DATOS_FICHAS.DF_NORDEN " + "FROM DATOS_FICHAS "
      + "INNER JOIN DATOS_FICHASEXAMENES " + "ON DATOS_FICHAS.DF_NORDEN = DATOS_FICHASEXAMENES.DFE_NORDEN "
      + "INNER JOIN DATOS_FICHASEXAMENESTEST "
      + "ON DATOS_FICHASEXAMENESTEST.DFET_NORDEN = DATOS_FICHASEXAMENES.DFE_NORDEN " + "INNER JOIN DATOS_PACIENTES "
      + "ON DATOS_FICHAS.DF_IDPACIENTE = DATOS_PACIENTES.DP_IDPACIENTE " + "INNER JOIN CFG_EXAMENES "
      + "ON DATOS_FICHASEXAMENES.DFE_IDEXAMEN = CFG_EXAMENES.CE_IDEXAMEN " + "INNER JOIN BIOSLIS.CFG_LOCALIZACIONES "
      + "ON DATOS_FICHAS.DF_IDLOCALIZACION = BIOSLIS.CFG_LOCALIZACIONES.CL_IDLOCALIZACION "
      + "INNER JOIN BIOSLIS.DATOS_FICHASEXAMENES "
      + "ON DATOS_FICHAS.DF_NORDEN = BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN "
      + "WHERE CFG_EXAMENES.CE_IDTIPOEXAMEN = 4 ";

  // prueba de cristian 22-12 --- esta cambiando la query de arriba
  private String sqlQuerySelect2 = "SELECT DISTINCT DATOS_FICHAS.DF_NORDEN ,DATOS_FICHAS.DF_fechaorden, DATOS_PACIENTES.DP_nombres, "
      + "ldv_sexo.ldvs_descripcion, DATOS_PACIENTES.DP_fnacimiento, cfg_centrosdesalud.ccds_descripcion, cfg_servicios.cs_descripcion, "
      + "cfg_tipoatencion.cta_descripcion "
      + "FROM DATOS_FICHAS  "
      + "INNER JOIN DATOS_FICHASEXAMENES ON DATOS_FICHAS.DF_NORDEN = DATOS_FICHASEXAMENES.DFE_NORDEN  "
      + "INNER JOIN DATOS_FICHASEXAMENESTEST ON DATOS_FICHASEXAMENESTEST.DFET_NORDEN = DATOS_FICHASEXAMENES.DFE_NORDEN  "
      + "INNER JOIN DATOS_PACIENTES ON DATOS_FICHAS.DF_IDPACIENTE = DATOS_PACIENTES.DP_IDPACIENTE  "
      + "INNER JOIN CFG_EXAMENES ON DATOS_FICHASEXAMENES.DFE_IDEXAMEN = CFG_EXAMENES.CE_IDEXAMEN "
      + "INNER JOIN BIOSLIS.CFG_LOCALIZACIONES ON DATOS_FICHAS.DF_IDLOCALIZACION = BIOSLIS.CFG_LOCALIZACIONES.CL_IDLOCALIZACION  "
      + "INNER JOIN BIOSLIS.DATOS_FICHASEXAMENES ON DATOS_FICHAS.DF_NORDEN = BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN  "
      + "INNER JOIN ldv_sexo ON ldv_sexo.ldvs_idsexo = datos_pacientes.dp_idsexo "
      + "INNER JOIN  cfg_centrosdesalud on cfg_centrosdesalud.ccds_idcentrodesalud = DATOS_FICHAS.df_idcentrodesalud "
      + "inner join cfg_servicios on cfg_servicios.cs_idservicio = DATOS_FICHAS.df_idservicio "
      + "inner join cfg_tipoatencion on cfg_tipoatencion.cta_idtipoatencion = DATOS_FICHAS.df_idtipoatencion ";

  private String sqlQueryGetById = "SELECT BIOSLIS.DATOS_FICHAS.DF_NORDEN, BIOSLIS.DATOS_FICHAS.DF_FECHAORDEN, BIOSLIS.CFG_TIPOATENCION.CTA_DESCRIPCION, "
      + "BIOSLIS.CFG_CENTROSDESALUD.CCDS_DESCRIPCION , BIOSLIS.CFG_CONVENIOS.CC_DESCRIPCION, "
      + "CONCAT(CONCAT(CONCAT(CONCAT(BIOSLIS.CFG_MEDICOS.CM_NOMBRES, ' '), BIOSLIS.CFG_MEDICOS.CM_PRIMERAPELLIDO), ' '), BIOSLIS.CFG_MEDICOS.CM_SEGUNDOAPELLIDO), "
      + "BIOSLIS.DATOS_FICHAS.DF_OBSERVACION, BIOSLIS.CFG_DIAGNOSTICOS.CD_DESCRIPCION, BIOSLIS.CFG_SERVICIOS.CS_DESCRIPCION, BIOSLIS.DATOS_FICHAS.DF_IDPROCEDENCIA, "
      + "BIOSLIS.CFG_PROCEDENCIAS.CP_DESCRIPCION , BIOSLIS.DATOS_FICHAS.DF_HOST " 
       + " FROM BIOSLIS.DATOS_FICHAS "
      + "INNER JOIN BIOSLIS.CFG_TIPOATENCION "
      + "ON BIOSLIS.DATOS_FICHAS.DF_IDTIPOATENCION = BIOSLIS.CFG_TIPOATENCION.CTA_IDTIPOATENCION "
      + "INNER JOIN BIOSLIS.CFG_CONVENIOS "
      + "ON BIOSLIS.DATOS_FICHAS.DF_IDCONVENIO = BIOSLIS.CFG_CONVENIOS.CC_IDCONVENIO "
      + "LEFT JOIN BIOSLIS.CFG_MEDICOS " + "ON BIOSLIS.DATOS_FICHAS.DF_IDMEDICO = BIOSLIS.CFG_MEDICOS.CM_IDMEDICO "
      + "INNER JOIN BIOSLIS.CFG_DIAGNOSTICOS "
      + "ON BIOSLIS.DATOS_FICHAS.DF_IDDIAGNOSTICO = BIOSLIS.CFG_DIAGNOSTICOS.CD_IDDIAGNOSTICO "
      + "INNER JOIN BIOSLIS.CFG_LOCALIZACIONES "
      + "ON BIOSLIS.DATOS_FICHAS.DF_IDLOCALIZACION = BIOSLIS.CFG_LOCALIZACIONES.CL_IDLOCALIZACION "
      + "INNER JOIN BIOSLIS.CFG_SERVICIOS "
      + "ON BIOSLIS.DATOS_FICHAS.DF_IDSERVICIO = BIOSLIS.CFG_SERVICIOS.CS_IDSERVICIO "
      + "INNER JOIN BIOSLIS.CFG_CENTROSDESALUD "
      + "ON BIOSLIS.CFG_LOCALIZACIONES.CL_IDCENTRODESALUD = BIOSLIS.CFG_CENTROSDESALUD.CCDS_IDCENTRODESALUD "
      + "INNER JOIN BIOSLIS.CFG_PROCEDENCIAS "
      + "ON BIOSLIS.CFG_PROCEDENCIAS.CP_IDPROCEDENCIA = BIOSLIS.DATOS_FICHAS.DF_IDPROCEDENCIA "

      + "WHERE BIOSLIS.DATOS_FICHAS.DF_NORDEN = :orderId";

  private String sqlQueryGetPatientId = "SELECT BIOSLIS.DATOS_FICHAS.DF_IDPACIENTE " + "FROM BIOSLIS.DATOS_FICHAS "
      + "WHERE BIOSLIS.DATOS_FICHAS.DF_NORDEN = :orderId";

  private String sqlQueryGetPhysicianId = "SELECT BIOSLIS.DATOS_FICHAS.DF_IDMEDICO " + "FROM BIOSLIS.DATOS_FICHAS "
      + "WHERE BIOSLIS.DATOS_FICHAS.DF_NORDEN = :orderId";

  private String sqlQueryGetEvents = "SELECT BIOSLIS.LOG_EVENTOSFICHAS.LEF_FECHAREGISTRO, BIOSLIS.DATOS_USUARIOS.DU_USUARIO, BIOSLIS.LOG_EVENTOSFICHAS.LEF_NOMBRECAMPO, "
      + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORANTERIOR, BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORNUEVO "
      + "FROM BIOSLIS.LOG_EVENTOSFICHAS " + " LEFT JOIN BIOSLIS.DATOS_USUARIOS "
      + "ON BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDUSUARIO = BIOSLIS.DATOS_USUARIOS.DU_IDUSUARIO "
      + "WHERE BIOSLIS.LOG_EVENTOSFICHAS.LEF_NORDEN = :orderId";

  //Modificado por Marco Caracciolo 28/04/23 para exámenes solamente de Microbiología
  private String sqlQueryGetExamsId = "SELECT DFE.DFE_IDEXAMEN "
      + " FROM BIOSLIS.DATOS_FICHASEXAMENES DFE"
      + " JOIN BIOSLIS.CFG_EXAMENES CE ON CE.CE_IDEXAMEN = DFE.DFE_IDEXAMEN" 
      + " WHERE DFE.DFE_NORDEN = :orderId AND CE.CE_IDTIPOEXAMEN = :examTypeId" ;

  @Override
  public List<MBOrder> search(HashMap<String, String> queryData) {
    List<MBOrder> result = new ArrayList<MBOrder>();
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createSQLQuery(this.generateSearchQueryString(queryData));
      this.setParameters(query, queryData);

      for (Object orderId : query.list()) {

        MBOrder order = getById((BigDecimal) orderId);

        if (order != null) {
          if (isSelected("statusPending", queryData) && isSelected("statusForSignature", queryData)) {
            if (order.isPending() || order.isReadyForSignature()) {
              result.add(order);
            }
          } else if (isSelected("statusPending", queryData)) {
            if (order.isPending()) {
              result.add(order);
            }
          } else if (isSelected("statusForSignature", queryData)) {
            if (order.isReadyForSignature()) {
              result.add(order);
            }
          } else {
            result.add(order);
          }
        }

      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      sesion.close();
    }
    return result;
  }

  @Override
  public MBOrder getById(int orderId) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    MBOrder result = new MBOrder();

    try {
      Query query = session.createSQLQuery(this.sqlQueryGetById);
      query.setParameter("orderId", orderId);
      Object[] objeto = (Object[]) query.uniqueResult();
      HashMap<String, Object> orderData = null;
      if (objeto != null) {
        orderData = parseOrderData(objeto);
      }

      session.close();
      if (orderData != null) {
        // HashMap<String, Object> orderData = parseOrderData((Object[])
        // query.list().get(0));
        result = new MBOrder((String) orderData.get("id"), (Date) orderData.get("date"),
            (String) orderData.get("attentionType"), (String) orderData.get("institution"),
            (String) orderData.get("service"), (String) orderData.get("contract"),
            (String) orderData.get("observation"), (String) orderData.get("diagnostic"), getPatientForOrder(orderId),
            getEventsForOrder(orderId), getExamForOrder(orderId), getPhysicianForOrder(orderId),
            //getEventsForOrder(orderId),// getExamForOrder(orderId), getPhysicianForOrder(orderId),
            (String) orderData.get("origin"), (String) orderData.get("procedencia") , 
            (String)  orderData.get("host"));   //agregado por cristian 16-01-2023
       } else {
        result = new MBOrder();
      }
    } catch (Exception e) {
      log.error("error en getById " + e.getMessage());
      e.printStackTrace();
      if (session.isOpen()) {
        session.close();
      }

    }
    return result;
  }

  private Boolean isSelected(String key, HashMap<String, String> queryData) {
    return queryData.containsKey(key) && queryData.get(key).equals("true");
  }

  private HashMap<String, MBExam> getExamForOrder(int orderId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, MBExam> result = new HashMap<String, MBExam>();
    MBExamDAO dao = (new DAOFactory()).getDAO("MBExam");
    try {
      Query query = session.createSQLQuery(this.sqlQueryGetExamsId);
      query.setParameter("orderId", orderId);
      query.setParameter("examTypeId", Constante.TIPOEXAMEN_MICROBIOLOGIA);
      List<Object> lista = query.list();
      session.close();

      for (Object examId : lista) {
        result.put(examId.toString(), dao.getExamByIds(orderId, Integer.parseInt(examId.toString())));
      }

    } catch (Exception e) {
      log.error("error en getExamForOrder " + e.getMessage());
      e.printStackTrace();
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  private List<HashMap<String, String>> getEventsForOrder(int orderId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    // aqui se llenan los eventos -- p
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(this.sqlQueryGetEvents);
      query.setParameter("orderId", orderId);
      List<Object> lista = query.list();
      session.close();
      for (Object event : lista) {
        result.add(parseEventData(event));
      }
    } catch (Exception e) {
      log.error("error en getEventsForOrder " + e.getMessage());
      e.printStackTrace();
      if (session.isOpen()) {
        session.close();
      }
    }

    return result;
  }

  private HashMap<String, String> parseEventData(Object event) {
    Object[] eventData = (Object[]) event;
    HashMap<String, String> result = new HashMap<String, String>() {
      {
        put("date", eventData[0] == null ? "-" : eventData[0].toString());
        put("user", eventData[1] == null ? "-" : eventData[1].toString());
        put("field", eventData[2] == null ? "-" : eventData[2].toString());
        put("oldValue", eventData[3] == null ? "-" : eventData[3].toString());
        put("newValue", eventData[4] == null ? "-" : eventData[4].toString());
      }
    };
    return result;
  }

  private Patient getPatientForOrder(int orderId) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    PatientDAO dao = null;
    BigDecimal patientId = null;
    try {
      Query query = sesion.createSQLQuery(this.sqlQueryGetPatientId);
      query.setParameter("orderId", orderId);
      patientId = (BigDecimal) query.list().get(0);
      sesion.close();
      DAOFactory daoFactory = new DAOFactory();
      dao = daoFactory.getDAO("Patient");

    } catch (Exception e) {
      log.error("error en " + e.getMessage());
      e.printStackTrace();
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return dao.getById(patientId.toString() , orderId);
  }

  private Physician getPhysicianForOrder(int orderId) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      Query query = sesion.createSQLQuery(this.sqlQueryGetPhysicianId);
      query.setParameter("orderId", orderId);
      BigDecimal physicianId = (BigDecimal) query.uniqueResult();
      sesion.close();
      if (physicianId != null) {
        DAOFactory daoFactory = new DAOFactory();
        PhysicianDAO dao = daoFactory.getDAO("Physician");
        return dao.getById(physicianId.toString());
      }
    } catch (Exception e) {
      log.error("error en getPhysicianForOrder " + e.getMessage());
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return null;
  }

  private MBOrder getById(BigDecimal order) {
    return this.getById((Integer) order.intValue());
  }

  private String generateSearchQueryString(HashMap<String, String> queryData) {
    String result = this.sqlQuerySelect;

    for (Map.Entry<String, String> querySet : queryData.entrySet()) {
      if (this.sqlQuerySearchs.containsKey(querySet.getKey())) {
        result += this.sqlQuerySearchs.get(querySet.getKey());
      }
    }

    return result;
  }

  private void setParameters(Query query, HashMap<String, String> queryData) {
    for (Map.Entry<String, String> querySet : queryData.entrySet()) {
      if (NumberUtils.isParsable(querySet.getValue())) {
        query.setParameter(querySet.getKey(), Integer.parseInt(querySet.getValue()));
      } else if (isValidDate(querySet.getValue())) {
        query.setDate(querySet.getKey(), toDate(querySet.getValue()));
      } else {
        if (querySet.getKey() == "statusPending" || querySet.getKey() == "statusForSignature") {
          continue;
        }
        if (querySet.getKey() == "surname" || querySet.getKey() == "names") {
          query.setParameter(querySet.getKey(), "%" + querySet.getValue() + "%");
        } else {
          query.setParameter(querySet.getKey(), querySet.getValue());
        }
      }
    }
  }

  private static boolean isValidDate(String inDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    try {
      dateFormat.parse(inDate.trim());
    } catch (ParseException pe) {
      return false;
    }
    return true;
  }

  private static Date toDate(String inDAte) {
    Date result = null;
    try {
      result = new SimpleDateFormat("yyyy-MM-dd").parse(inDAte);
      return result;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return result;
  }

  private HashMap<String, Object> parseOrderData(Object[] orderData) {
    HashMap<String, Object> result = new HashMap<String, Object>();
    try {
      result.put("id", orderData[0] == null ? "" : orderData[0].toString());
      result.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderData[1].toString()));
      result.put("attentionType", orderData[2] == null ? "" : orderData[2].toString());
      result.put("institution", orderData[3] == null ? "" : orderData[3].toString());
      result.put("contract", orderData[4] == null ? "" : orderData[4].toString());
      result.put("physician", orderData[5] == null ? "" : orderData[5].toString());
      result.put("observation", orderData[6] == null ? "" : orderData[6].toString());
      result.put("diagnostic", orderData[7] == null ? "" : orderData[7].toString());
      result.put("service", orderData[8] == null ? "" : orderData[8].toString());
      result.put("procedencia", orderData[10] == null ? "" : orderData[10].toString());
      result.put("proced", orderData[8] == null ? "" : orderData[8].toString());
      result.put("host", orderData[11] == null ? "" : orderData[11].toString());
      } catch (ParseException e) {
      e.printStackTrace();
    }
    return result;
  }
}
