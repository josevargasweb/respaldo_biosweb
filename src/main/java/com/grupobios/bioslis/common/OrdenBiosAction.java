package com.grupobios.bioslis.common;

public enum OrdenBiosAction implements BiosAction {

  FIRMAR("FIRMAR");

  private String actionText;

  OrdenBiosAction(String _actionText) {
    this.actionText = _actionText;
  }

}
