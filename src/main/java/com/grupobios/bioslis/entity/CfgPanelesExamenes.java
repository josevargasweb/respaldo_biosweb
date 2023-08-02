/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.entity;

/**
 *
 * @author Marco Caracciolo
 */
public class CfgPanelesExamenes {

  private short cpeIdpanelexamenes;
  private String cpeNombrepanelexamen;
  private String cpeActivo;

  public CfgPanelesExamenes() {
  }

  public short getCpeIdpanelexamenes() {
    return cpeIdpanelexamenes;
  }

  public void setCpeIdpanelexamenes(short cpeIdpanelexamenes) {
    this.cpeIdpanelexamenes = cpeIdpanelexamenes;
  }

  public String getCpeNombrepanelexamen() {
    return cpeNombrepanelexamen;
  }

  public void setCpeNombrepanelexamen(String cpeNombrepanelexamen) {
    this.cpeNombrepanelexamen = cpeNombrepanelexamen;
  }

  public String getCpeActivo() {
    return cpeActivo;
  }

  public void setCpeActivo(String cpeActivo) {
    this.cpeActivo = cpeActivo;
  }

  @Override
  public String toString() {
    return "CfgPanelesExamenes [cpeIdpanelexamenes=" + cpeIdpanelexamenes + ", cpeNombrepanelexamen="
        + cpeNombrepanelexamen + ", cpeActivo=" + cpeActivo + "]";
  }

}
