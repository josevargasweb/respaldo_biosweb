package com.grupobios.bioslis.microbiologia.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MBOrder {

  private String id;
  private Date date;
  private String attentionType;
  private String institution;
  private String service;
  private String contract;
  private String observation;
  private String diagnostic;
  private Patient patient;
  private Physician physician;
  private String origin;
  //agregado por cristian 09-11
  private String procedencia;
//agregado por cristian 16-01-2023
  private String host;

  private List<HashMap<String, String>> events;
  private HashMap<String, MBExam> exams;

  public MBOrder(String id, Date date, String attentionType, String institution, String service, String contract,
      String observation, String diagnostic, Patient patient, List<HashMap<String, String>> events,
      HashMap<String, MBExam> exams, Physician physician, String origin, String procedencia, String host) {

    this.id = id;
    this.date = date;
    this.attentionType = attentionType;
    this.institution = institution;
    this.service = service;
    this.contract = contract;
    this.observation = observation;
    this.diagnostic = diagnostic;
    this.patient = patient;
    this.events = events;
    this.exams = exams;
    this.physician = physician;
    this.origin = origin;
    this.procedencia =procedencia;
    this.host = host;
  }

  public MBOrder() {
  }

  public String getId() {
    return this.id;
  }

  public Date getDate() {
    return this.date;
  }

  public String getAtentionType() {
    return this.attentionType;
  }

  public String getInstitution() {
    return this.institution;
  }

  public String getService() {
    return this.service;
  }

  public String getObservation() {
    return this.observation;
  }

  public String getContract() {
    return this.contract;
  }

  public String getDiagnostic() {
    return this.diagnostic;
  }

  public Patient getPatient() {
    return this.patient;
  }

  public List<HashMap<String, String>> getEvents() {
    return this.events;
  }

  public List<HashMap<String, String>> getExamList() {

    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

    if (this.exams == null || this.exams.values() == null) {
      return null;
    }

    for (MBExam exam : this.exams.values()) {
      if (exam != null) {

        result.add(new HashMap<String, String>() {
          {
            put("orderid", exam.getOrderId());
            put("id", exam.getExamId());
            put("exam", exam.getExamName());
            put("sample", exam.getSample());
            put("bodypart", exam.getBodyPart());
            put("processstatus", exam.getProcessStatus());
            put("seedingdate", exam.getSeedingDate() == null ? ""
                : (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(exam.getSeedingDate()));
            put("reseedingdate", exam.getReseedingDate() == null ? ""
                : (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(exam.getReseedingDate()));
            put("urgency", exam.isUrgent() ? "SI" : "NO");
            put("status", exam.getProcessStatus());
          }
        });
      }

    }

    return result;
  }

  public List<HashMap<String, String>> getExamList_Microbiologia() {

    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

    if (this.exams == null || this.exams.values() == null) {
      List<HashMap<String, String>> myList = new ArrayList<>();

      return myList;
    }

    for (MBExam exam : this.exams.values()) {
      if (exam != null) {

        result.add(new HashMap<String, String>() {
          {
            put("cultivo", exam.getCultivo());
            put("estado", exam.getEstado());
            put("orderid", exam.getOrderId());
            put("id", exam.getExamId());
            put("exam", exam.getExamName());
            put("sample", exam.getSample());
            put("bodypart", exam.getBodyPart());
            put("bodypartid", exam.getBodyPartId());
            put("processstatus2", exam.getProcessStatus());
            put("seedingdate", exam.getSeedingDate() == null ? ""
                : (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(exam.getSeedingDate()));
            put("reseedingdate", exam.getReseedingDate() == null ? ""
                : (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(exam.getReseedingDate()));
            put("urgency", exam.isUrgent() ? "S" : "N");
            put("status", exam.getProcessStatus());
            put("processstatus", exam.getDescripcionEstado());
            put("codigo", exam.getCodigo());
          }
        });
      } else {

      }

    }

    return result;
  }

  public Boolean isReadyForSignature() {
    for (HashMap<String, String> exam : this.getExamList()) {
      if (!exam.get("status").equals("B") || !exam.get("status").equals("C") || !exam.get("status").equals("I")) {
        return false;
      }
    }
    return true;
  }

  public Boolean isPending() {
    for (HashMap<String, String> exam : this.getExamList()) {
      if (exam.get("status").equals("P")) {
        return true;
      }
    }
    return false;
  }

  public MBExam getExam(String examId) {
    return this.exams.get(examId);
  }

  public MBExam getExam_Solo_Con_Orden(String examId) {
    return this.exams.get(examId);
  }

  public Physician getPhysician() {
    return this.physician;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

public String getProcedencia() {
    return procedencia;
}

public void setProcedencia(String procedencia) {
    this.procedencia = procedencia;
}

public String getHost() {
    return host;
}

public void setHost(String host) {
    this.host = host;
}

  
  
}
