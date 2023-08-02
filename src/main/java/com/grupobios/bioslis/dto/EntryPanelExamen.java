package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EntryPanelExamen {

  private Short cpeIdpanelexamenes;
  private BigDecimal examenId;
  private ArrayList<BigDecimal> testLst;

  public ArrayList<BigDecimal> getTestLst() {
    return testLst;
  }

  public void setTestLst(ArrayList<BigDecimal> testLst) {
    this.testLst = testLst;
  }

  public BigDecimal getExamenId() {
    return examenId;
  }

  public void setExamenId(BigDecimal examenId) {
    this.examenId = examenId;
  }

  public EntryPanelExamen(BigDecimal examenId, ArrayList<BigDecimal> testList) {
    this.examenId = examenId;
    this.testLst = testList;
  }

}
