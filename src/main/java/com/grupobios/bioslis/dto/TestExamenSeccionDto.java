package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class TestExamenSeccionDto {

  public TestExamenSeccionDto() {
  }

  private BigDecimal ctIdtest;
  private String ctCodigo;
  private String ctAbreviado;
  private BigDecimal cmueIdtipomuestra;
  private String cmueDescripcionabreviada;
  private String cmueColor;
  private BigDecimal csecIdseccion;
  private String csecDescripcion;
  private String csecColor;
  private BigDecimal cmetIdmetodo;
  private String cmetDescripcion;

  public String getCmetDescripcion() {
    return cmetDescripcion;
  }

  public void setCmetDescripcion(String cmetDescripcion) {
    this.cmetDescripcion = cmetDescripcion;
  }

  public BigDecimal getCtIdtest() {
    return ctIdtest;
  }

  public void setCtIdtest(BigDecimal ctIdtest) {
    this.ctIdtest = ctIdtest;
  }

  public String getCtCodigo() {
    return ctCodigo;
  }

  public void setCtCodigo(String ctCodigo) {
    this.ctCodigo = ctCodigo;
  }

  public String getCtAbreviado() {
    return ctAbreviado;
  }

  public void setCtAbreviado(String ctAbreviado) {
    this.ctAbreviado = ctAbreviado;
  }

  public BigDecimal getCmueIdtipomuestra() {
    return cmueIdtipomuestra;
  }

  public void setCmueIdtipomuestra(BigDecimal cmueIdtipomuestra) {
    this.cmueIdtipomuestra = cmueIdtipomuestra;
  }

  public String getCmueDescripcionabreviada() {
    return cmueDescripcionabreviada;
  }

  public void setCmueDescripcionabreviada(String cmueDescripcionabreviada) {
    this.cmueDescripcionabreviada = cmueDescripcionabreviada;
  }

  public String getCmueColor() {
    return cmueColor;
  }

  public void setCmueColor(String cmueColor) {
    this.cmueColor = cmueColor;
  }

  public BigDecimal getCsecIdseccion() {
    return csecIdseccion;
  }

  public void setCsecIdseccion(BigDecimal csecIdseccion) {
    this.csecIdseccion = csecIdseccion;
  }

  public String getCsecDescripcion() {
    return csecDescripcion;
  }

  public void setCsecDescripcion(String csecDescripcion) {
    this.csecDescripcion = csecDescripcion;
  }

  public String getCsecColor() {
    return csecColor;
  }

  public void setCsecColor(String csecColor) {
    this.csecColor = csecColor;
  }

  public BigDecimal getCmetIdmetodo() {
    return cmetIdmetodo;
  }

  public void setCmetIdmetodo(BigDecimal cmetIdmetodo) {
    this.cmetIdmetodo = cmetIdmetodo;
  }

}
