package com.grupobios.bioslis.common;

public class ActionExamenExecutorFactory {

  private AutorizarExamenExecutor autorizarExamenExecutor;
  private UnsignExamenExecutor unsignExecutor;
  private SignExamenExecutor signExecutor;
  private ExamenNoExecutor examenNoExecutor;
  private DesautorizarExamenExecutor desautorizarExamenExecutor;

  public SignExamenExecutor getSignExecutor() {
    return signExecutor;
  }

  public void setSignExecutor(SignExamenExecutor signExecutor) {
    this.signExecutor = signExecutor;
  }

  public ActionExecutor getInstance(String actionCode) {

    switch (actionCode) {

    case "FIRMAR":
      return signExecutor;
    case "DIGITAR":
      return examenNoExecutor;
    case "TRANSMITIR":
      return examenNoExecutor;
    case "BLOQUEAR":
      return examenNoExecutor;
    case "RETIRARFIRMA":
      return examenNoExecutor;
    case "DESAUTORIZAR":
      return desautorizarExamenExecutor;
    case "AUTORIZAR":
      return autorizarExamenExecutor;
    default:
      return examenNoExecutor;

    }

  }

  public AutorizarExamenExecutor getAutorizarExamenExecutor() {
    return autorizarExamenExecutor;
  }

  public void setAutorizarExamenExecutor(AutorizarExamenExecutor autorizarExamenExecutor) {
    this.autorizarExamenExecutor = autorizarExamenExecutor;
  }

  public ExamenNoExecutor getExamenNoExecutor() {
    return examenNoExecutor;
  }

  public void setExamenNoExecutor(ExamenNoExecutor examenNoExecutor) {
    this.examenNoExecutor = examenNoExecutor;
  }

  public DesautorizarExamenExecutor getDesautorizarExamenExecutor() {
    return desautorizarExamenExecutor;
  }

  public void setDesautorizarExamenExecutor(DesautorizarExamenExecutor desautorizarExamenExecutor) {
    this.desautorizarExamenExecutor = desautorizarExamenExecutor;
  }

  public UnsignExamenExecutor getUnsignExecutor() {
    return unsignExecutor;
  }

  public void setUnsignExecutor(UnsignExamenExecutor unsignExecutor) {
    this.unsignExecutor = unsignExecutor;
  }

}
