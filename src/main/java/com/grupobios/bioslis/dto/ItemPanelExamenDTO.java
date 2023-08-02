package com.grupobios.bioslis.dto;

import java.util.List;

public class ItemPanelExamenDTO {

  private Short itemPanel;
  private String namePanel;
  private List<CfgExamenesDTO> lstExamenes;

  public Short getItemPanel() {
    return itemPanel;
  }

  public void setItemPanel(Short itemPanel) {
    this.itemPanel = itemPanel;
  }

  public String getNamePanel() {
    return namePanel;
  }

  public void setNamePanel(String namePanel) {
    this.namePanel = namePanel;
  }

  public List<CfgExamenesDTO> getLstExamenes() {
    return lstExamenes;
  }

  public void setLstExamenes(List<CfgExamenesDTO> arrayList) {
    this.lstExamenes = arrayList;

  }

}
