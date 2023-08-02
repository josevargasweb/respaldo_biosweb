package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class PanelExamenDTO {

  String CPE_NOMBREPANELEXAMEN;
  BigDecimal CE_IDEXAMEN;
  String CE_DESCRIPCION;
  BigDecimal CT_IDTEST;
  String CT_ABREVIADO;
  BigDecimal CPE_IDPANELEXAMENES;
  BigDecimal CSEC_IDSECCION;
  String CSEC_DESCRIPCION;
  String CSEC_CODIGO;
  BigDecimal CLAB_IDLABORATORIO;
  String CLAB_DESCRIPCION;
  BigDecimal LDVIE_IDINDICACIONESEXAMEN;
  String LDVIE_DESCRIPCION;

  public String getCPE_NOMBREPANELEXAMEN() {
    return CPE_NOMBREPANELEXAMEN;
  }

  public void setCPE_NOMBREPANELEXAMEN(String cPE_NOMBREPANELEXAMEN) {
    CPE_NOMBREPANELEXAMEN = cPE_NOMBREPANELEXAMEN;
  }

  public BigDecimal getCE_IDEXAMEN() {
    return CE_IDEXAMEN;
  }

  public void setCE_IDEXAMEN(BigDecimal cE_IDEXAMEN) {
    CE_IDEXAMEN = cE_IDEXAMEN;
  }

  public String getCE_DESCRIPCION() {
    return CE_DESCRIPCION;
  }

  public void setCE_DESCRIPCION(String cE_DESCRIPCION) {
    CE_DESCRIPCION = cE_DESCRIPCION;
  }

  public BigDecimal getCT_IDTEST() {
    return CT_IDTEST;
  }

  public void setCT_IDTEST(BigDecimal cT_IDTEST) {
    CT_IDTEST = cT_IDTEST;
  }

  public String getCT_ABREVIADO() {
    return CT_ABREVIADO;
  }

  public void setCT_ABREVIADO(String cT_ABREVIADO) {
    CT_ABREVIADO = cT_ABREVIADO;
  }

  @Override
  public String toString() {
    return "PanelExamenDTO [CPE_NOMBREPANELEXAMEN=" + CPE_NOMBREPANELEXAMEN + ", CE_IDEXAMEN=" + CE_IDEXAMEN
        + ", CE_DESCRIPCION=" + CE_DESCRIPCION + ", CT_IDTEST=" + CT_IDTEST + ", CT_ABREVIADO=" + CT_ABREVIADO + "]";
  }

  public BigDecimal getCPE_IDPANELEXAMENES() {
    return CPE_IDPANELEXAMENES;
  }

  public void setCPE_IDPANELEXAMENES(BigDecimal cPE_IDPANELEXAMENES) {
    CPE_IDPANELEXAMENES = cPE_IDPANELEXAMENES;
  }

  public BigDecimal getCSEC_IDSECCION() {
    return CSEC_IDSECCION;
  }

  public void setCSEC_IDSECCION(BigDecimal cSEC_IDSECCION) {
    CSEC_IDSECCION = cSEC_IDSECCION;
  }

  public String getCSEC_DESCRIPCION() {
    return CSEC_DESCRIPCION;
  }

  public void setCSEC_DESCRIPCION(String cSEC_DESCRIPCION) {
    CSEC_DESCRIPCION = cSEC_DESCRIPCION;
  }

  public String getCSEC_CODIGO() {
    return CSEC_CODIGO;
  }

  public void setCSEC_CODIGO(String cSEC_CODIGO) {
    CSEC_CODIGO = cSEC_CODIGO;
  }

  public BigDecimal getCLAB_IDLABORATORIO() {
    return CLAB_IDLABORATORIO;
  }

  public void setCLAB_IDLABORATORIO(BigDecimal cLAB_IDLABORATORIO) {
    CLAB_IDLABORATORIO = cLAB_IDLABORATORIO;
  }

  public String getCLAB_DESCRIPCION() {
    return CLAB_DESCRIPCION;
  }

  public void setCLAB_DESCRIPCION(String cLAB_DESCRIPCION) {
    CLAB_DESCRIPCION = cLAB_DESCRIPCION;
  }


  public BigDecimal getLDVIE_IDINDICACIONESEXAMEN() {
    return LDVIE_IDINDICACIONESEXAMEN;
}

public void setLDVIE_IDINDICACIONESEXAMEN(BigDecimal lDVIE_IDINDICACIONESEXAMEN) {
    LDVIE_IDINDICACIONESEXAMEN = lDVIE_IDINDICACIONESEXAMEN;
}

public String getLDVIE_DESCRIPCION() {
    return LDVIE_DESCRIPCION;
  }

  public void setLDVIE_DESCRIPCION(String lDVIE_DESCRIPCION) {
    LDVIE_DESCRIPCION = lDVIE_DESCRIPCION;
  }

}
