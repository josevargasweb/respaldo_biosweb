package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class DeltaCheckAptoDTO {

  private BigDecimal VALORMAX;
  private BigDecimal VALORMIN;
  private BigDecimal PROMEDIO;
  private BigDecimal DFET_RESULTADONUMERICO;
  private BigDecimal CT_DELTACANTIDAD;
  private BigDecimal CT_DELTAPORCENTAJE;
//private BigDecimal DELTA;
//private BigDecimal DF_NORDEN;

  public DeltaCheckAptoDTO() {      
}

  
  public BigDecimal getDFET_RESULTADONUMERICO() {
    return DFET_RESULTADONUMERICO;
  }


public void setDFET_RESULTADONUMERICO(BigDecimal dFET_RESULTADONUMERICO) {
    DFET_RESULTADONUMERICO = dFET_RESULTADONUMERICO;
  }

  public BigDecimal getCT_DELTACANTIDAD() {
    return CT_DELTACANTIDAD;
  }

  public void setCT_DELTACANTIDAD(BigDecimal cT_DELTACANTIDAD) {
    CT_DELTACANTIDAD = cT_DELTACANTIDAD;
  }

  public BigDecimal getCT_DELTAPORCENTAJE() {
    return CT_DELTAPORCENTAJE;
  }

  public void setCT_DELTAPORCENTAJE(BigDecimal cT_DELTAPORCENTAJE) {
    CT_DELTAPORCENTAJE = cT_DELTAPORCENTAJE;
  }

  public BigDecimal getVALORMAX() {
    return VALORMAX;
  }

  public void setVALORMAX(BigDecimal vALORMAX) {
    VALORMAX = vALORMAX;
  }

  public BigDecimal getVALORMIN() {
    return VALORMIN;
  }

  public void setVALORMIN(BigDecimal vALORMIN) {
    VALORMIN = vALORMIN;
  }

  public BigDecimal getPROMEDIO() {
    return PROMEDIO;
  }

  public void setPROMEDIO(BigDecimal pROMEDIO) {
    PROMEDIO = pROMEDIO;
  }

  public DeltaCheckDTO toDeltaCheckDTO() {

    DeltaCheckDTO dcDto = new DeltaCheckDTO();
    dcDto.setCT_DELTAPORCENTAJE(this.getCT_DELTAPORCENTAJE());
    dcDto.setDELTA(this.getCT_DELTAPORCENTAJE());
    dcDto.setPROMEDIO(this.getPROMEDIO());
    dcDto.setDFET_RESULTADONUMERICO(this.getDFET_RESULTADONUMERICO());
    dcDto.setVALORMAX(this.getVALORMAX());
    dcDto.setVALORMIN(this.getVALORMIN());
    return dcDto;
  }

}
