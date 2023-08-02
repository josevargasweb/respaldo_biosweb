package com.grupobios.bioslis.common;

public class ActionExecutorFactory {

  private UnsignExamenExecutor unsignExecutor;
  private SignExamenExecutor signExecutor;
  private BloquearExamenExecutor blockExecutor;

  public BloquearExamenExecutor getBlockExecutor() {
    return blockExecutor;
  }

  public void setBlockExecutor(BloquearExamenExecutor blockExecutor) {
    this.blockExecutor = blockExecutor;
  }

  public BloquearTestExecutor getBlockTestExecutor() {
    return blockTestExecutor;
  }

  public void setBlockTestExecutor(BloquearTestExecutor blockTestExecutor) {
    this.blockTestExecutor = blockTestExecutor;
  }

  public DesbloquearTestExecutor getUnBlockTestExecutor() {
    return unBlockTestExecutor;
  }

  public void setUnBlockTestExecutor(DesbloquearTestExecutor unBlockTestExecutor) {
    this.unBlockTestExecutor = unBlockTestExecutor;
  }

  private BloquearTestExecutor blockTestExecutor;
  private DesbloquearTestExecutor unBlockTestExecutor;

  public SignExamenExecutor getSignExecutor() {
    return signExecutor;
  }

  public BloquearExamenExecutor getChangeEstadoExecutor() {
    return blockExecutor;
  }

  public void setSignExecutor(SignExamenExecutor signExecutor) {
    this.signExecutor = signExecutor;
  }

  public ActionExecutor getInstance(String actionCode) {

    switch (actionCode) {

    case "FIRMAR":
      return signExecutor;
    case "DIGITAR":
      break;
    case "TRANSMITIR":
      break;
    case "BLOQUEAR":
      break;
    case "RETIRARFIRMA":
      break;
    default:
      return null;

    }
    return null;

  }

  public ActionExamenExecutor getActionExamenInstance(String actionCode) {
    switch (actionCode) {

    case "FIRMAR":
      return signExecutor;
    case "DIGITAR":
      break;
    case "TRANSMITIR":
      break;
    case "BLOQUEAR":
      return blockExecutor;
    case "RETIRARFIRMA":
      break;
    case "DESBLOQUEAR":
      return blockExecutor;
    default:
      return null;

    }
    return null;

  }

  public ActionTestExecutor getActionTestInstance(String actionCode) {
    switch (actionCode) {

    case "FIRMAR":
      return null;
    case "DIGITAR":
      break;
    case "TRANSMITIR":
      break;
    case "BLOQUEAR":
      return blockTestExecutor;
    case "RETIRARFIRMA":
      break;
    case "DESBLOQUEAR":
      return unBlockTestExecutor;
    default:
      return null;

    }
    return null;

  }

  public BloquearExamenExecutor getChangeExecutor() {
    return blockExecutor;
  }

  public void setChangeExecutor(BloquearExamenExecutor changeExecutor) {
    this.blockExecutor = changeExecutor;
  }

  public UnsignExamenExecutor getUnsignExecutor() {
    return unsignExecutor;
  }

  public void setUnsignExecutor(UnsignExamenExecutor unsignExecutor) {
    this.unsignExecutor = unsignExecutor;
  }

}
