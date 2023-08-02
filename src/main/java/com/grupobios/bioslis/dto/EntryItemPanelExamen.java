package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EntryItemPanelExamen {

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

  public EntryItemPanelExamen(BigDecimal examenId, ArrayList<BigDecimal> testList) {
    this.examenId = examenId;
    this.testLst = testList;
  }

  public String toString() {

    String s;
//
//    s = this.panelName + "\n";
//    s += "{\n";
//
//    for (HashMap<BigDecimal, ArrayList<BigDecimal>> hashMap : hmPanelExamenTestLst) {
//
//      for (BigDecimal idExamen : hashMap.keySet()) {
//
//        s += " - idExamen: " + idExamen;
//        s += "{\n";
//
//        List<BigDecimal> lstTest = hashMap.get(idExamen);
//
//        for (BigDecimal idTest : lstTest) {
//          s += " - idTest: " + idTest;
//        }
//        s += "}\n";
//
//      }
//      s += "}\n";
//
//    }
    return null;

  }

}
