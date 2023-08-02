/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.util.Objects;

/**
 *
 * @author eric.nicholls
 */
public class BCFichaFiltroDTO implements BiosFilterDTO {

  private Integer bo_nOrdenIni;
  private Integer bo_nOrdenFin;
  private String bo_fIni;
  private String bo_fFin;
  private String bo_nombre;
  private String bo_apellido;
  private Integer bo_tipoDocumento;
  private String bo_nroDocumento;
  private Integer bo_tipoAtencion;
  private Integer bo_localizacion;
  private Integer bo_procedencia;
  private Integer bo_servicio;
  private Integer bo_seccion;
  private Integer bo_examen;
  private String bo_host;
  //agregado por cristian 30-11
  private String bo_segundoApellido;
  private Integer bo_laboratorio;
  private Integer bo_convenio;
  //AGREGADO X JAN
  //private boolean bo_estadoPendiente2;
  //private boolean bo_estadoFirmar;
  

  public String getBo_fIni() {
    return bo_fIni;
  }

  public void setBo_fIni(String bo_fIni) {
    this.bo_fIni = bo_fIni;
  }

  public String getBo_fFin() {
    return bo_fFin;
  }

  public void setBo_fFin(String bo_fFin) {
    this.bo_fFin = bo_fFin;
  }

  public String getBo_nombre() {
    return bo_nombre;
  }

  public void setBo_nombre(String bo_nombre) {
    this.bo_nombre = bo_nombre;
  }

  public String getBo_apellido() {
    return bo_apellido;
  }

  public void setBo_apellido(String bo_apellido) {
    this.bo_apellido = bo_apellido;
  }

  public Integer getBo_tipoDocumento() {
    return bo_tipoDocumento;
  }

  public void setBo_tipoDocumento(Integer bo_tipoDocumento) {
    this.bo_tipoDocumento = bo_tipoDocumento;
  }

  public String getBo_nroDocumento() {
    return bo_nroDocumento;
  }

  public void setBo_nroDocumento(String bo_nroDocumento) {
    this.bo_nroDocumento = bo_nroDocumento;
  }

  public Integer getBo_tipoAtencion() {
    return bo_tipoAtencion;
  }

  public void setBo_tipoAtencion(Integer bo_tipoAtencion) {
    this.bo_tipoAtencion = bo_tipoAtencion;
  }

  public Integer getBo_localizacion() {
    return bo_localizacion;
  }

  public void setBo_localizacion(Integer bo_localizacion) {
    this.bo_localizacion = bo_localizacion;
  }

  public Integer getBo_procedencia() {
    return bo_procedencia;
  }

  public void setBo_procedencia(Integer bo_procedencia) {
    this.bo_procedencia = bo_procedencia;
  }

  public Integer getBo_servicio() {
    return bo_servicio;
  }

  public void setBo_servicio(Integer bo_servicio) {
    this.bo_servicio = bo_servicio;
  }

  public Integer getBo_seccion() {
    return bo_seccion;
  }

  public void setBo_seccion(Integer bo_seccion) {
    this.bo_seccion = bo_seccion;
  }

  public Integer getBo_examen() {
    return bo_examen;
  }

  public void setBo_examen(Integer bo_examen) {
    this.bo_examen = bo_examen;
  }

  public Integer getBo_nOrdenIni() {
    return bo_nOrdenIni;
  }

  public void setBo_nOrdenIni(Integer bo_nOrdenIni) {
    this.bo_nOrdenIni = bo_nOrdenIni;
  }

  public Integer getBo_nOrdenFin() {
    return bo_nOrdenFin;
  }

  public void setBo_nOrdenFin(Integer bo_nOrdenFin) {
    this.bo_nOrdenFin = bo_nOrdenFin;
  }

  @Override
  public int hashCode() {
    return Objects.hash(bo_apellido, bo_examen, bo_fFin, bo_fIni, bo_localizacion, bo_nOrdenFin, bo_nOrdenIni,
        bo_nombre, bo_nroDocumento, bo_procedencia, bo_seccion, bo_servicio, bo_tipoAtencion, bo_tipoDocumento);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BCFichaFiltroDTO other = (BCFichaFiltroDTO) obj;
    return Objects.equals(bo_apellido, other.bo_apellido) && Objects.equals(bo_examen, other.bo_examen)
        && Objects.equals(bo_fFin, other.bo_fFin) && Objects.equals(bo_fIni, other.bo_fIni)
        && Objects.equals(bo_localizacion, other.bo_localizacion) && Objects.equals(bo_nOrdenFin, other.bo_nOrdenFin)
        && Objects.equals(bo_nOrdenIni, other.bo_nOrdenIni) && Objects.equals(bo_nombre, other.bo_nombre)
        && Objects.equals(bo_nroDocumento, other.bo_nroDocumento)
        && Objects.equals(bo_procedencia, other.bo_procedencia) && Objects.equals(bo_seccion, other.bo_seccion)
        && Objects.equals(bo_servicio, other.bo_servicio) && Objects.equals(bo_tipoAtencion, other.bo_tipoAtencion)
        && Objects.equals(bo_tipoDocumento, other.bo_tipoDocumento);
  }

  @Override
  public String toString() {
    return "BCFichaFiltroDTO [bo_nOrdenIni=" + bo_nOrdenIni + ", bo_nOrdenFin=" + bo_nOrdenFin + ", bo_fIni=" + bo_fIni
        + ", bo_fFin=" + bo_fFin + ", bo_nombre=" + bo_nombre + ", bo_apellido=" + bo_apellido + ", bo_tipoDocumento="
        + bo_tipoDocumento + ", bo_nroDocumento=" + bo_nroDocumento + ", bo_tipoAtencion=" + bo_tipoAtencion
        + ", bo_localizacion=" + bo_localizacion + ", bo_procedencia=" + bo_procedencia + ", bo_servicio=" + bo_servicio
        + ", bo_seccion=" + bo_seccion + ", bo_examen=" + bo_examen + "]";
  }

  public String getBo_host() {
    return bo_host;
  }

  public void setBo_host(String bo_host) {
    this.bo_host = bo_host;
  }

  //agregado por cristian 30-11


public Integer getBo_laboratorio() {
    return bo_laboratorio;
}

public String getBo_segundoApellido() {
    return bo_segundoApellido;
}

public void setBo_segundoApellido(String bo_segundoApellido) {
    this.bo_segundoApellido = bo_segundoApellido;
}

public void setBo_laboratorio(Integer bo_laboratorio) {
    this.bo_laboratorio = bo_laboratorio;
}

public Integer getBo_convenio() {
    return bo_convenio;
}

public void setBo_convenio(Integer bo_convenio) {
    this.bo_convenio = bo_convenio;
}

  //agregado jan
  
}
