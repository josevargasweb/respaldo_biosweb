package com.grupobios.bioslis.dto;

import java.util.Date;
import java.util.List;

import com.grupobios.bioslis.entity.DatosFichas;

public class DatosFichasDTO implements java.io.Serializable {

  private int dfNorden;
  private int datosPacientes;
  private Integer cfgServicios;
  private int cfgDiagnosticos;
  private int cfgConvenios;
  private int cfgProcedencias;
  private int ldvFichasestadostm;
  private int cfgPrioridadatencion;
  private Integer cfgTipoatencion;
  private int cfgInstitucionesdesalud;
  private Date dfFechaorden;
  private Short dfIdprevision;
  private String dfIdmedico;
  private int cfgLocalizaciones;
  private String dfCodigolocalizacion;
  private String dfObservacion;
  private String dfHost;
  private String dfEnviarresultadosemail;
  private String dfTmclick;
  private String salas;
  private String camas;
  private Integer[] lstExamenes;
  private CfgExamenesDTO[] lstExamenesDto;
  //agregado por cristian 22-11 *****
  private List<TestEdicionOrdenDTO> tests;

  public DatosFichasDTO() {
  }

  
  public int getDfNorden() {
    return this.dfNorden;
  }

  public void setDfNorden(int dfNorden) {
    this.dfNorden = dfNorden;
  }

  public int getDatosPacientes() {
    return this.datosPacientes;
  }

  public void setDatosPacientes(int datosPacientes) {
    this.datosPacientes = datosPacientes;
  }

  public Integer getCfgServicios() {
    return this.cfgServicios;
  }

  public void setCfgServicios(Integer cfgServicios) {
    this.cfgServicios = cfgServicios;
  }

  public int getCfgDiagnosticos() {
    return this.cfgDiagnosticos;
  }

  public void setCfgDiagnosticos(int cfgDiagnosticos) {
    this.cfgDiagnosticos = cfgDiagnosticos;
  }

  public int getCfgConvenios() {
    return this.cfgConvenios;
  }

  public void setCfgConvenios(int cfgConvenios) {
    this.cfgConvenios = cfgConvenios;
  }

  public int getCfgProcedencias() {
    return this.cfgProcedencias;
  }

  public void setCfgProcedencias(int cfgProcedencias) {
    this.cfgProcedencias = cfgProcedencias;
  }

  public int getLdvFichasestadostm() {
    return this.ldvFichasestadostm;
  }

  public void setLdvFichasestadostm(int ldvFichasestadostm) {
    this.ldvFichasestadostm = ldvFichasestadostm;
  }

  public int getCfgPrioridadatencion() {
    return this.cfgPrioridadatencion;
  }

  public void setCfgPrioridadatencion(int cfgPrioridadatencion) {
    this.cfgPrioridadatencion = cfgPrioridadatencion;
  }

  public int getCfgTipoatencion() {
    return this.cfgTipoatencion;
  }

  public void setCfgTipoatencion(int cfgTipoatencion) {
    this.cfgTipoatencion = cfgTipoatencion;
  }

  public int getCfgInstitucionesdesalud() {
    return this.cfgInstitucionesdesalud;
  }

  public void setCfgInstitucionesdesalud(int cfgInstitucionesdesalud) {
    this.cfgInstitucionesdesalud = cfgInstitucionesdesalud;
  }

  public Date getDfFechaorden() {
    return this.dfFechaorden;
  }

  public void setDfFechaorden(Date dfFechaorden) {
    this.dfFechaorden = dfFechaorden;
  }

  public Short getDfIdprevision() {
    return this.dfIdprevision;
  }

  public void setDfIdprevision(Short dfIdprevision) {
    this.dfIdprevision = dfIdprevision;
  }

  public String getDfIdmedico() {
    return this.dfIdmedico;
  }

  public void setDfIdmedico(String dfIdmedico) {
    this.dfIdmedico = dfIdmedico;
  }

  
  public int getCfgLocalizaciones() {
    return cfgLocalizaciones;
}


public void setCfgLocalizaciones(int cfgLocalizaciones) {
    this.cfgLocalizaciones = cfgLocalizaciones;
}


public String getDfCodigolocalizacion() {
    return this.dfCodigolocalizacion;
  }

  public void setDfCodigolocalizacion(String dfCodigolocalizacion) {
    this.dfCodigolocalizacion = dfCodigolocalizacion;
  }

  public String getDfObservacion() {
    return this.dfObservacion;
  }

  public void setDfObservacion(String dfObservacion) {
    this.dfObservacion = dfObservacion;
  }

  public String getDfHost() {
    return this.dfHost;
  }

  public void setDfHost(String dfHost) {
    this.dfHost = dfHost;
  }

  public String getDfEnviarresultadosemail() {
    return this.dfEnviarresultadosemail;
  }

  public void setDfEnviarresultadosemail(String dfEnviarresultadosemail) {
    this.dfEnviarresultadosemail = dfEnviarresultadosemail;
  }

  public String getDfTmclick() {
    return this.dfTmclick;
  }

  public void setDfTmclick(String dfTmclick) {
    this.dfTmclick = dfTmclick;
  }

  public String getSalas() {
    return salas;
  }

  public void setSalas(String salas) {
    this.salas = salas;
  }

  public String getCamas() {
    return camas;
  }

  public void setCamas(String camas) {
    this.camas = camas;
  }

  public void setCfgTipoatencion(Integer cfgTipoatencion) {
    this.cfgTipoatencion = cfgTipoatencion;
  }

  public DatosFichas toDatosFicha() {

    DatosFichas target = new DatosFichas();

    target.setCfgConvenios(this.cfgConvenios);
    target.setCfgDiagnosticos(this.cfgDiagnosticos);
    target.setCfgInstitucionesdesalud(this.cfgInstitucionesdesalud);
    target.setCfgPrioridadatencion(this.cfgPrioridadatencion);
    target.setCfgProcedencias(this.cfgProcedencias);
    target.setCfgServicios(this.cfgServicios);
    target.setCfgTipoatencion(this.cfgTipoatencion);
    target.setDatosPacientes(this.datosPacientes);
    target.setDfCodigolocalizacion(this.dfCodigolocalizacion);
    //cambiado por cristian 06-12 , enviar resultado viene con N o S no con on
   // target.setDfEnviarresultadosemail(this.dfEnviarresultadosemail.equals("on") ? "S" : "N");
    target.setDfEnviarresultadosemail(this.dfEnviarresultadosemail);
    target.setDfFechaorden(this.dfFechaorden);
    target.setDfHost(this.dfHost);
    if (this.dfIdmedico == null || this.dfIdmedico.isEmpty()) {
      target.setDfIdmedico(null);
    } else {
      target.setDfIdmedico(Integer.valueOf(this.dfIdmedico));
    }
   
    target.setDfIdprevision(this.dfIdprevision);
    target.setDfNorden(this.dfNorden);
    target.setDfObservacion(this.dfObservacion);
    target.setDfTmclick(this.dfTmclick);
    target.setLdvFichasestadostm(this.ldvFichasestadostm);

    return target;

  }

  public Integer[] getLstExamenes() {
    return lstExamenes;
  }

  public void setLstExamenes(Integer[] lstExamenes) {
    this.lstExamenes = lstExamenes;
  }

  public CfgExamenesDTO[] getLstExamenesDto() {
    return lstExamenesDto;
  }

  public void setLstExamenesDto(CfgExamenesDTO[] lstExamenesDto) {
    this.lstExamenesDto = lstExamenesDto;
  }


public List<TestEdicionOrdenDTO> getTests() {
    return tests;
}


public void setTests(List<TestEdicionOrdenDTO> tests) {
    this.tests = tests;
}



}
