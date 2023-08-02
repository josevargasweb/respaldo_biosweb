package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1

import java.util.Set;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * CfgLaboratorios generated by hbm2java
 */
public class CfgLaboratorios {

  private int clabIdlaboratorio;
  private Integer clabIdCentroSalud;
  private String clabCodigo;
  private String clabDescripcion;
  private String clabActivo;
  private String clabPreinforme;
  private String clabHost;
  private String clabDerivador;
  private String clabImagenologia;
  private short clabSort;
  @JsonIgnore
  private Set<CfgSecciones> cfgSecciones;

  public CfgLaboratorios() {
  }

  public Integer getClabIdCentroSalud() {
    return clabIdCentroSalud;
  }

  public void setClabIdCentroSalud(Integer clabIdCentroSalud) {
    this.clabIdCentroSalud = clabIdCentroSalud;
  }

  public int getClabIdlaboratorio() {
    return this.clabIdlaboratorio;
  }

  public void setClabIdlaboratorio(int clabIdlaboratorio) {
    this.clabIdlaboratorio = clabIdlaboratorio;
  }

  public String getClabCodigo() {
    return this.clabCodigo;
  }

  public void setClabCodigo(String clabCodigo) {
    this.clabCodigo = clabCodigo;
  }

  public String getClabDescripcion() {
    return this.clabDescripcion;
  }

  public void setClabDescripcion(String clabDescripcion) {
    this.clabDescripcion = clabDescripcion;
  }

  public String getClabActivo() {
    return this.clabActivo;
  }

  public void setClabActivo(String clabActivo) {
    this.clabActivo = clabActivo;
  }

  public String getClabPreinforme() {
    return this.clabPreinforme;
  }

  public void setClabPreinforme(String clabPreinforme) {
    this.clabPreinforme = clabPreinforme;
  }

  public String getClabHost() {
    return this.clabHost;
  }

  public void setClabHost(String clabHost) {
    this.clabHost = clabHost;
  }

  public String getClabDerivador() {
    return this.clabDerivador;
  }

  public void setClabDerivador(String clabDerivador) {
    this.clabDerivador = clabDerivador;
  }

  public String getClabImagenologia() {
    return this.clabImagenologia;
  }

  public void setClabImagenologia(String clabImagenologia) {
    this.clabImagenologia = clabImagenologia;
  }

  public short getClabSort() {
    return this.clabSort;
  }

  public void setClabSort(short clabSort) {
    this.clabSort = clabSort;
  }

  public Set<CfgSecciones> getCfgSecciones() {
    return cfgSecciones;
  }

  public void setCfgSecciones(Set<CfgSecciones> cfgSecciones) {
    this.cfgSecciones = cfgSecciones;
  }

  @Transient
  public void copySinSecciones(CfgLaboratorios from) {

//    this.cfgSecciones = from.cfgSecciones;
    this.clabActivo = from.clabActivo;
    this.clabIdCentroSalud = from.clabIdCentroSalud;
    this.clabCodigo = from.clabCodigo;
    this.clabDescripcion = from.clabDescripcion;
    this.clabActivo = from.clabActivo;
    this.clabPreinforme = from.clabPreinforme;
    this.clabHost = from.clabHost;
    this.clabDerivador = from.clabDerivador;
    this.clabImagenologia = from.clabImagenologia;
    this.clabSort = from.clabSort;
  }

  @Override
  public String toString() {
    return "CfgLaboratorios [clabIdlaboratorio=" + clabIdlaboratorio + ", clabIdCentroSalud=" + clabIdCentroSalud
        + ", clabCodigo=" + clabCodigo + ", clabDescripcion=" + clabDescripcion + ", clabActivo=" + clabActivo
        + ", clabPreinforme=" + clabPreinforme + ", clabHost=" + clabHost + ", clabDerivador=" + clabDerivador
        + ", clabImagenologia=" + clabImagenologia + ", clabSort=" + clabSort + "]";
  }

}
