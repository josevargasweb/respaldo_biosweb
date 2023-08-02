package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EntryPanel {

  private String panelName;
  @JsonIgnore
  private ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>> hmPanelExamenLst;
  private List<EntryPanelExamen> lstPanelExamenTest;

  public EntryPanel(String panelName, ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>> hmPanelExamenTestLst) {
    this.hmPanelExamenLst = hmPanelExamenTestLst;
    this.panelName = panelName;
    this.lstPanelExamenTest = new ArrayList<EntryPanelExamen>();

    for (HashMap<BigDecimal, ArrayList<BigDecimal>> hashMap : hmPanelExamenLst) {

      for (BigDecimal idExamen : hashMap.keySet()) {

        EntryPanelExamen epe = new EntryPanelExamen(idExamen, hashMap.get(idExamen));
        lstPanelExamenTest.add(epe);
      }
    }
  }

  public List<EntryPanelExamen> getLstPanelExamenTest() {
    return lstPanelExamenTest;
  }

  public void setLstPanelExamenTest(List<EntryPanelExamen> lstPanelExamenTest) {
    this.lstPanelExamenTest = lstPanelExamenTest;
  }

  public String getPanelName() {
    return panelName;
  }

  public void setPanelName(String panelName) {
    this.panelName = panelName;
  }

  @JsonIgnore
  public ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>> getHmPanelExamenTestLst() {
    return hmPanelExamenLst;
  }

  public void setHmPanelExamenTestLst(ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>> hmPanelExamenTestLst) {
    this.hmPanelExamenLst = hmPanelExamenTestLst;
  }

}
