package com.grupobios.bioslis.microbiologia.bs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBOrderDAO;
import com.grupobios.bioslis.microbiologia.entity.MBOrder;

public class OrderSearcher {

  static Logger log = LogManager.getLogger(OrderSearcher.class.getName());

  public HashMap<String, Object> searchOrder_Examenes_Only(String orderId) {
    MBOrderDAO dao = (new DAOFactory()).getDAO("MBOrder");
    MBOrder order = dao.getById(Integer.parseInt(orderId));

    HashMap<String, Object> result = new HashMap<String, Object>();

    result.put("exams", order.getExamList_Microbiologia());

    return result;
  }

  public HashMap<String, Object> searchOrder(String orderId) {
    MBOrderDAO dao = (new DAOFactory()).getDAO("MBOrder");
    MBOrder order = dao.getById(Integer.parseInt(orderId));

    HashMap<String, Object> result = new HashMap<String, Object>();

    result.put("patient", new HashMap<String, String>() {
      {
        put("name", order.getPatient().getFullName());
        put("gender", order.getPatient().getGender());
        put("age", order.getPatient().getAgeAsText());
        put("dateOfBirth", (new SimpleDateFormat("yyyy-MM-dd")).format(order.getPatient().getDateOfBirth()));
        put("phoneNumber", order.getPatient().getPhoneNumber());
        put("atentionType", order.getAtentionType());
        put("localization", order.getInstitution() + " / " + order.getService());

        put("observation", order.getPatient().getObservation());
        put("pathologies", order.getPatient().getPathologiesASText());
        put("nroDocumento", order.getPatient().getNDocument());
        put("tipoDocumento", order.getPatient().getTipoDocumento().toString());
      }
    });

    result.put("order", new HashMap<String, String>() {
      {
        put("id", order.getId());
        //put("date", (new SimpleDateFormat("yyyy-MM-dd")).format(order.getDate()));
        /*put("date", order.getDate()== null ? ""
                : (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(order.getDate()));*/
        put("date", order.getDate()== null ? ""
                : (new SimpleDateFormat("HH:mm dd/MM/yyyy")).format(order.getDate()));
        put("contract", order.getContract());
        put("physician", order.getPhysician() != null ? order.getPhysician().getFullName() : "");
        put("diagnostic", order.getDiagnostic());
        put("observation", order.getObservation());
        put("service", order.getService());
        put("origin", order.getOrigin());
        //agregado por cristian 09-11
        put("procedencia", order.getProcedencia());
      //agregado por cristian 16-01-2023
        put("host", order.getHost());
      }
    });

    result.put("previousOrders", order.getPatient().getOrdersList());
    result.put("exams", order.getExamList());
    /*result.put("events", order.getEvents()

    );*/

    return result;
  }

  public List<HashMap<String, String>> searchList(String startId, String endId, String startDate, String endDate,
      String atentionType, String exam, String section, String names, String surname, String documentType,
      String documentId, String serviceId, String statusPending, String statusForSignature) {

    MBOrderDAO dao = (new DAOFactory()).getDAO("MBOrder");
    
    List<MBOrder> orders = dao.search(searchParameters(startId, endId, startDate, endDate, atentionType, exam, section,
        names, surname, documentType, documentId, serviceId, statusPending, statusForSignature));

    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    for (MBOrder o : orders) {
      result.add(new HashMap<String, String>() {
        {
          put("name", o.getPatient().getFullName());
          put("id", o.getId());
        //  put("date", (new SimpleDateFormat("yyyy-MM-dd")).format(o.getDate()));
          put("date", o.getDate()== null ? ""
                  : (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(o.getDate()));
          put("gender", o.getPatient().getGender());
          put("age", o.getPatient().getAgeAsText());
          put("attentionType", o.getAtentionType());
          put("institution", o.getInstitution());
          put("service", o.getService());
        }
      });
    }

    return result;
  }

  HashMap<String, String> searchParameters(String startId, String endId, String startDate, String endDate,
      String atentionType, String exam, String section, String names, String surname, String documentType,
      String documentId, String serviceId, String statusPending, String statusForSignature) {
    HashMap<String, String> result = new HashMap<String, String>();

    if (!startId.equals("")) {
      result.put("startId", startId);
    }
    if (!endId.equals("")) {
      result.put("endId", endId);
    }
    if (!startDate.equals("")) {
      result.put("startDate", startDate);
    }
    if (!endDate.equals("")) {
      result.put("endDate", endDate);
    }
    if (!atentionType.equals("")) {
      result.put("atentionType", atentionType);
    }
    if (!exam.equals("")) {
      result.put("exam", exam);
    }
    if (!section.equals("")) {
      result.put("section", section);
    }
    if (!names.equals("")) {
      result.put("names", names);
    }
    if (!surname.equals("")) {
      result.put("surname", surname);
    }
    if (!documentType.equals("")) {
      result.put("documentType", documentType);
    }
    if (!documentId.equals("")) {
      result.put("documentId", documentId);
    }
    if (!serviceId.equals("")) {
      result.put("serviceId", serviceId);
    }
    if (!statusPending.equals("")) {
      result.put("statusPending", statusPending);
    }
    if (!statusForSignature.equals("")) {
      result.put("statusForSignature", statusForSignature);
    }

    return result;
  }
}
