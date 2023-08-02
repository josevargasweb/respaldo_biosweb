package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.common.RangoValorResultado;

public class ResultadoActualizacionTestDTO {

  private RangoValorResultado rangoValorResultado;
  private ExamenesResultadosDeUnaOrdenDTO resultadoExamen;

  public RangoValorResultado getRangoValorResultado() {
    return rangoValorResultado;
  }

  public void setRangoValorResultado(RangoValorResultado rangoValorResultado) {
    this.rangoValorResultado = rangoValorResultado;
  }

  public ExamenesResultadosDeUnaOrdenDTO getResultadoExamen() {
    return resultadoExamen;
  }

  public void setResultadoExamen(ExamenesResultadosDeUnaOrdenDTO resultadoExamen) {
    this.resultadoExamen = resultadoExamen;
  }

}
