package com.grupobios.bioslis.common;

public enum TestBiosAction implements BiosAction {

  DIGITAR("DIGITAR"), TRANSMITIR("TRANSMITIR"), BLOQUEAR("BLOQUEAR"), FIRMAR("FIRMAR"), RETIRARFIRMA("RETIRARFIRMA"),
  CAMBIARESTADO("CAMBIARESTADO"), DESBLOQUEAR("DESBLOQUEAR");
  ;

  private String actionText;

  TestBiosAction(String _actionText) {
    this.actionText = _actionText;
  }

}
