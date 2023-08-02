package com.grupobios.bioslis.entity;
// Generated 22-04-2020 16:13:26 by Hibernate Tools 4.3.1

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * CfgPerfilesusuarios generated by hbm2java
 */
public class CfgPerfilesusuarios implements java.io.Serializable {

  private long cpuIdusuario;
  @JsonIgnore
  private CfgCentrosdesalud cfgCentrosdesalud;
  @JsonIgnore
  private DatosUsuarios datosUsuarios;
  private String cpuProfesion;
  private String cpuPrefijoprofesion;
  private short cpuIdseccion;
  @JsonIgnore
  private Blob cpuUsuariofirma;
  private int cpuIdprocedencia;
  private short cpuIdlaboratorio;
  private String cpuFirmaexamenes;
  private String cpuFirmaexaseleccionados;
  private String cpuFlebotomista;
  private String cpuAutorizaexamenes;
  private String cpuUsuariorestringido;
  private String cpuEnviaresultadosemail;
  private String cpuUsuariocalidad;
  private String cpuEliminarexamenes;
  private String cpuQuitarautorizacion;
  private String cpuRegistraexamenesderivados;
  private String cpuProcedenciarestringida;
  private String cpuPideautorizeditorden;
  private String cpuImprimeconveniorestringido;
  private String cpuEditadatospaciente;
  private String cpuRecepcionxexamen;
  private String cpuEditasoloordsprocedencia;
  private String cpuEditaresultadoscriticos;
  private String cpuHost;
  private String cpuHostmicro;
  // private String cpuUsuarioimed;
  // private String cpuClaveimed;

  public CfgPerfilesusuarios() {
  }

  public CfgPerfilesusuarios(DatosUsuarios datosUsuarios, CfgNivelesaccesos cfgNivelesaccesos, short cpuIdseccion,
      int cpuIdprocedencia, short cpuIdlaboratorio) {
    this.datosUsuarios = datosUsuarios;
    this.cpuIdseccion = cpuIdseccion;
    this.cpuIdprocedencia = cpuIdprocedencia;
    this.cpuIdlaboratorio = cpuIdlaboratorio;
  }

  public long getCpuIdusuario() {
    return this.cpuIdusuario;
  }

  public void setCpuIdusuario(long cpuIdusuario) {
    this.cpuIdusuario = cpuIdusuario;
  }

  public CfgCentrosdesalud getCfgCentrosdesalud() {
    return cfgCentrosdesalud;
  }

  public void setCfgCentrosdesalud(CfgCentrosdesalud cfgCentrosdesalud) {
    this.cfgCentrosdesalud = cfgCentrosdesalud;
  }

  public DatosUsuarios getDatosUsuarios() {
    return this.datosUsuarios;
  }

  public void setDatosUsuarios(DatosUsuarios datosUsuarios) {
    this.datosUsuarios = datosUsuarios;
  }

  public String getCpuProfesion() {
    return this.cpuProfesion;
  }

  public void setCpuProfesion(String cpuProfesion) {
    this.cpuProfesion = cpuProfesion;
  }

  public String getCpuPrefijoprofesion() {
    return this.cpuPrefijoprofesion;
  }

  public void setCpuPrefijoprofesion(String cpuPrefijoprofesion) {
    this.cpuPrefijoprofesion = cpuPrefijoprofesion;
  }

  public short getCpuIdseccion() {
    return this.cpuIdseccion;
  }

  public void setCpuIdseccion(short cpuIdseccion) {
    this.cpuIdseccion = cpuIdseccion;
  }

  public Blob getCpuUsuariofirma() {
    return this.cpuUsuariofirma;
  }

  public void setCpuUsuariofirma(Blob cpuUsuariofirma) {
    this.cpuUsuariofirma = cpuUsuariofirma;
  }

  public int getCpuIdprocedencia() {
    return this.cpuIdprocedencia;
  }

  public void setCpuIdprocedencia(int cpuIdprocedencia) {
    this.cpuIdprocedencia = cpuIdprocedencia;
  }

  public short getCpuIdlaboratorio() {
    return this.cpuIdlaboratorio;
  }

  public void setCpuIdlaboratorio(short cpuIdlaboratorio) {
    this.cpuIdlaboratorio = cpuIdlaboratorio;
  }

  public String getCpuFirmaexamenes() {
    return this.cpuFirmaexamenes;
  }

  public void setCpuFirmaexamenes(String cpuFirmaexamenes) {
    this.cpuFirmaexamenes = cpuFirmaexamenes;
  }

  public String getCpuFirmaexaseleccionados() {
    return this.cpuFirmaexaseleccionados;
  }

  public void setCpuFirmaexaseleccionados(String cpuFirmaexaseleccionados) {
    this.cpuFirmaexaseleccionados = cpuFirmaexaseleccionados;
  }

  public String getCpuFlebotomista() {
    return this.cpuFlebotomista;
  }

  public void setCpuFlebotomista(String cpuFlebotomista) {
    this.cpuFlebotomista = cpuFlebotomista;
  }

  public String getCpuAutorizaexamenes() {
    return this.cpuAutorizaexamenes;
  }

  public void setCpuAutorizaexamenes(String cpuAutorizaexamenes) {
    this.cpuAutorizaexamenes = cpuAutorizaexamenes;
  }

  public String getCpuUsuariorestringido() {
    return this.cpuUsuariorestringido;
  }

  public void setCpuUsuariorestringido(String cpuUsuariorestringido) {
    this.cpuUsuariorestringido = cpuUsuariorestringido;
  }

  public String getCpuEnviaresultadosemail() {
    return this.cpuEnviaresultadosemail;
  }

  public void setCpuEnviaresultadosemail(String cpuEnviaresultadosemail) {
    this.cpuEnviaresultadosemail = cpuEnviaresultadosemail;
  }

  public String getCpuUsuariocalidad() {
    return this.cpuUsuariocalidad;
  }

  public void setCpuUsuariocalidad(String cpuUsuariocalidad) {
    this.cpuUsuariocalidad = cpuUsuariocalidad;
  }

  public String getCpuEliminarexamenes() {
    return this.cpuEliminarexamenes;
  }

  public void setCpuEliminarexamenes(String cpuEliminarexamenes) {
    this.cpuEliminarexamenes = cpuEliminarexamenes;
  }

  public String getCpuQuitarautorizacion() {
    return this.cpuQuitarautorizacion;
  }

  public void setCpuQuitarautorizacion(String cpuQuitarautorizacion) {
    this.cpuQuitarautorizacion = cpuQuitarautorizacion;
  }

  public String getCpuRegistraexamenesderivados() {
    return this.cpuRegistraexamenesderivados;
  }

  public void setCpuRegistraexamenesderivados(String cpuRegistraexamenesderivados) {
    this.cpuRegistraexamenesderivados = cpuRegistraexamenesderivados;
  }

  public String getCpuProcedenciarestringida() {
    return this.cpuProcedenciarestringida;
  }

  public void setCpuProcedenciarestringida(String cpuProcedenciarestringida) {
    this.cpuProcedenciarestringida = cpuProcedenciarestringida;
  }

  public String getCpuPideautorizeditorden() {
    return this.cpuPideautorizeditorden;
  }

  public void setCpuPideautorizeditorden(String cpuPideautorizeditorden) {
    this.cpuPideautorizeditorden = cpuPideautorizeditorden;
  }

  public String getCpuImprimeconveniorestringido() {
    return this.cpuImprimeconveniorestringido;
  }

  public void setCpuImprimeconveniorestringido(String cpuImprimeconveniorestringido) {
    this.cpuImprimeconveniorestringido = cpuImprimeconveniorestringido;
  }

  public String getCpuEditadatospaciente() {
    return this.cpuEditadatospaciente;
  }

  public void setCpuEditadatospaciente(String cpuEditadatospaciente) {
    this.cpuEditadatospaciente = cpuEditadatospaciente;
  }

  public String getCpuRecepcionxexamen() {
    return this.cpuRecepcionxexamen;
  }

  public void setCpuRecepcionxexamen(String cpuRecepcionxexamen) {
    this.cpuRecepcionxexamen = cpuRecepcionxexamen;
  }

  public String getCpuEditasoloordsprocedencia() {
    return this.cpuEditasoloordsprocedencia;
  }

  public void setCpuEditasoloordsprocedencia(String cpuEditasoloordsprocedencia) {
    this.cpuEditasoloordsprocedencia = cpuEditasoloordsprocedencia;
  }

  public String getCpuEditaresultadoscriticos() {
    return this.cpuEditaresultadoscriticos;
  }

  public void setCpuEditaresultadoscriticos(String cpuEditaresultadoscriticos) {
    this.cpuEditaresultadoscriticos = cpuEditaresultadoscriticos;
  }

  public String getCpuHost() {
    return this.cpuHost;
  }

  public void setCpuHost(String cpuHost) {
    this.cpuHost = cpuHost;
  }

  public String getCpuHostmicro() {
    return this.cpuHostmicro;
  }

  public void setCpuHostmicro(String cpuHostmicro) {
    this.cpuHostmicro = cpuHostmicro;
  }

  /*
   * public String getCpuUsuarioimed() { return this.cpuUsuarioimed; }
   * 
   * public void setCpuUsuarioimed(String cpuUsuarioimed) { this.cpuUsuarioimed =
   * cpuUsuarioimed; }
   * 
   * public String getCpuClaveimed() { return this.cpuClaveimed; }
   * 
   * public void setCpuClaveimed(String cpuClaveimed) { this.cpuClaveimed =
   * cpuClaveimed; }
   */
}
