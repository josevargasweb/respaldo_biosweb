package com.grupobios.bioslis.common;

public class AutomataEntry {

  private Short estadoInicial;
  private Short estadoFinal;
  private BiosAction biosAction;

  public BiosAction getBiosAction() {
    return biosAction;
  }

  public void setBiosAction(BiosAction biosAction) {
    this.biosAction = biosAction;
  }

}
