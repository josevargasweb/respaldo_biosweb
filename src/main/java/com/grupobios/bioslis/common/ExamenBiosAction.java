package com.grupobios.bioslis.common;

public enum ExamenBiosAction implements BiosAction {

  DIGITAR("DIGITAR"), TRANSMITIR("TRANSMITIR"), BLOQUEAR("BLOQUEAR"), FIRMAR("FIRMAR"), RETIRARFIRMA("RETIRARFIRMA"),
  AUTORIZAR("AUTORIZAR"), DESAUTORIZAR("DESAUTORIZAR");

  private String actionText;

  ExamenBiosAction(String _actionText) {
    this.actionText = _actionText;
  }

}
