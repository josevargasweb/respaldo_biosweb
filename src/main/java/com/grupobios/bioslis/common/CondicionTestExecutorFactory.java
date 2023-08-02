package com.grupobios.bioslis.common;

public class CondicionTestExecutorFactory {

  private static CondicionTestExecutorFactory theInstance;

  public static synchronized CondicionTestExecutorFactory getInstance() {
    if (theInstance == null) {
      theInstance = new CondicionTestExecutorFactory();
    }
    return theInstance;
  }

  private CondicionTestExecutorFactory() {
  }

  private void checkType(Long idTestAntecedente) {

    // if es test
    // devolver evaluadorResultado

    // if no es test
    // ver si está en datos ficha antecedente

    // si está devolver evaluador daro ficha antedente

    // si no ver de que "extra se trata" sexo, edad, afro.

  }

}
