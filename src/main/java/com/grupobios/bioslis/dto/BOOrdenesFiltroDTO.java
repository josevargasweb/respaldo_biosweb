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
public class BOOrdenesFiltroDTO implements BiosFilterDTO {

  Integer bo_nOrdenIni;
  Integer bo_nOrdenFin;
  String bo_fIni;
  String bo_fFin;
  String bo_nombre;
  String bo_apellido;
  Integer bo_tipoDocumento;
  String bo_nroDocumento;
  Integer bo_tipoAtencion;
  Integer bo_localizacion;
  Integer bo_procedencia;
  Integer bo_servicio;
  Integer bo_seccion;
  Integer bo_examen;

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

  @Override
  public String toString() {
    return "BOOrdenesFiltroDTO [bo_nOrdenIni=" + bo_nOrdenIni + ", bo_nOrdenFin=" + bo_nOrdenFin + ", bo_fIni="
        + bo_fIni + ", bo_fFin=" + bo_fFin + ", bo_nombre=" + bo_nombre + ", bo_apellido=" + bo_apellido
        + ", bo_tipoDocumento=" + bo_tipoDocumento + ", bo_nroDocumento=" + bo_nroDocumento + ", bo_tipoAtencion="
        + bo_tipoAtencion + ", bo_localizacion=" + bo_localizacion + ", bo_procedencia=" + bo_procedencia
        + ", bo_servicio=" + bo_servicio + ", bo_seccion=" + bo_seccion + ", bo_examen=" + bo_examen + "]";
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
    BOOrdenesFiltroDTO other = (BOOrdenesFiltroDTO) obj;
    return Objects.equals(bo_apellido, other.bo_apellido) && Objects.equals(bo_examen, other.bo_examen)
        && Objects.equals(bo_fFin, other.bo_fFin) && Objects.equals(bo_fIni, other.bo_fIni)
        && Objects.equals(bo_localizacion, other.bo_localizacion) && Objects.equals(bo_nOrdenFin, other.bo_nOrdenFin)
        && Objects.equals(bo_nOrdenIni, other.bo_nOrdenIni) && Objects.equals(bo_nombre, other.bo_nombre)
        && Objects.equals(bo_nroDocumento, other.bo_nroDocumento)
        && Objects.equals(bo_procedencia, other.bo_procedencia) && Objects.equals(bo_seccion, other.bo_seccion)
        && Objects.equals(bo_servicio, other.bo_servicio) && Objects.equals(bo_tipoAtencion, other.bo_tipoAtencion)
        && Objects.equals(bo_tipoDocumento, other.bo_tipoDocumento);
  }
}
