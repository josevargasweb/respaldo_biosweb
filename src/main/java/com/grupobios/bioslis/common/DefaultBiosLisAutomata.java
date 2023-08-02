package com.grupobios.bioslis.common;

import java.util.List;

public class DefaultBiosLisAutomata implements BiosLisAutomata {

  private List<AutomataEntry> matrizTransiciones;

  public DefaultBiosLisAutomata() {

    AutomataEntry aEntry = new AutomataEntry();
//    aEntry.setEstadoInicial(EstadosSistema.DFET_IDRESULTADOTEST_DIGITADO);
//    aEntry.setEstadoFinal(null);
    aEntry.setBiosAction(TestBiosAction.BLOQUEAR);

  }

  public void execute(BiosAction action) {

  }

}
