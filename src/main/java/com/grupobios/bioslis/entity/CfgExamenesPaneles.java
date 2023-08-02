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
public class CfgExamenesPaneles {

  private CfgExamenesPanelesId cfgExamenesPanelesId;
  private CfgExamenes cfgExamenes;
  private CfgPanelesExamenes cfgPanelesExamenes;
  private short cepSort;

  public CfgExamenesPaneles() {
  }

  public CfgExamenesPaneles(CfgExamenesPanelesId cfgExamenesPanelesId, short cepSort) {
    this.cfgExamenesPanelesId = cfgExamenesPanelesId;
    this.cepSort = cepSort;
  }

  public CfgExamenesPanelesId getCfgExamenesPanelesId() {
    return cfgExamenesPanelesId;
  }

  public void setCfgExamenesPanelesId(CfgExamenesPanelesId cfgExamenesPanelesId) {
    this.cfgExamenesPanelesId = cfgExamenesPanelesId;
  }

  public short getCepSort() {
    return cepSort;
  }

  public void setCepSort(short cepSort) {
    this.cepSort = cepSort;
  }

  public CfgExamenes getCfgExamenes() {
    return cfgExamenes;
  }

  public void setCfgExamenes(CfgExamenes cfgExamenes) {
    this.cfgExamenes = cfgExamenes;
  }

  public CfgPanelesExamenes getCfgPanelesExamenes() {
    return cfgPanelesExamenes;
  }

  public void setCfgPanelesExamenes(CfgPanelesExamenes cfgPanelesExamenes) {
    this.cfgPanelesExamenes = cfgPanelesExamenes;
  }

  @Override
  public String toString() {
    return "CfgExamenesPaneles [cfgExamenesPanelesId=" + cfgExamenesPanelesId + ", cepSort=" + cepSort + "]";
  }

}
