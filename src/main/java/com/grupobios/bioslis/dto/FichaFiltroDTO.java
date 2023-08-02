/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

/**
 *
 * @author eric.nicholls
 */
public class FichaFiltroDTO implements BiosFilterDTO {

  private Integer nOrden;
  private String fIni;
  private String fFin;
  private String nombre;
  private String apellido;
  private Integer tipoDocumento;
  private String nroDocumento;
  private Integer tipoAtencion;
  private Integer localizacion;
  private Integer nOrdenFin;

  public Integer getnOrden() {
    return nOrden;
  }

  public void setnOrden(Integer nOrden) {
    this.nOrden = nOrden;
  }

  public String getfIni() {
    return fIni;
  }

  public void setfIni(String fIni) {
    this.fIni = fIni;
  }

  public String getfFin() {
    return fFin;
  }

  public void setfFin(String fFin) {
    this.fFin = fFin;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public Integer getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(Integer tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getNroDocumento() {
    return nroDocumento;
  }

  public void setNroDocumento(String nroDocumento) {
    this.nroDocumento = nroDocumento;
  }

  public Integer getTipoAtencion() {
    return tipoAtencion;
  }

  public void setTipoAtencion(Integer tipoAtencion) {
    this.tipoAtencion = tipoAtencion;
  }

  public Integer getLocalizacion() {
    return localizacion;
  }

  public void setLocalizacion(Integer localizacion) {
    this.localizacion = localizacion;
  }

  @Override
  public String toString() {
    return "FichaFiltroDTO{" + "nOrden=" + nOrden + ", fIni=" + fIni + ", fFin=" + fFin + ", nombre=" + nombre
        + ", apellido=" + apellido + ", tipoDocumento=" + tipoDocumento + ", nroDocumento=" + nroDocumento
        + ", tipoAtencion=" + tipoAtencion + ", localizacion=" + localizacion + '}';
  }

  public Integer getnOrdenFin() {
    return nOrdenFin;
  }

  public void setnOrdenFin(Integer nOrdenFin) {
    this.nOrdenFin = nOrdenFin;
  }

}
