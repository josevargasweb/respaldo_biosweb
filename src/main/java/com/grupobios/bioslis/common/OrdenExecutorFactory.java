package com.grupobios.bioslis.common;

public class OrdenExecutorFactory {

  private SignOrdenExecutor signOrdenExecutor;

  public SignOrdenExecutor getSignExecutor() {
    return signOrdenExecutor;
  }

  public void setSignOrdenExecutor(SignOrdenExecutor signExecutor) {
    this.signOrdenExecutor = signExecutor;
  }

  public ActionOrdenExecutor getInstance(String actionCode) {

    switch (actionCode) {

    case "FIRMAR":
      return signOrdenExecutor;
    default:
      return null;
    }

  }

}
