package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class DeltaCheckDTO {

  private BigDecimal VALORMAX;
  private BigDecimal VALORMIN;
  private BigDecimal DELTA;
  private BigDecimal PROMEDIO;
  private BigDecimal DFET_RESULTADONUMERICO;
  private BigDecimal CT_DELTAPORCENTAJE;
  private BigDecimal DF_NORDEN;

  public BigDecimal getDFET_RESULTADONUMERICO() {
    return DFET_RESULTADONUMERICO;
  }

  public void setDFET_RESULTADONUMERICO(BigDecimal dFET_RESULTADONUMERICO) {
    DFET_RESULTADONUMERICO = dFET_RESULTADONUMERICO;
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

  public BigDecimal getDELTA() {
    return DELTA;
  }

  public void setDELTA(BigDecimal dELTA) {
    DELTA = dELTA;
  }

  public BigDecimal getPROMEDIO() {
    return PROMEDIO;
  }

  public void setPROMEDIO(BigDecimal pROMEDIO) {
    PROMEDIO = pROMEDIO;
  }

  public BigDecimal getDF_NORDEN() {
    return DF_NORDEN;
  }

  public void setDF_NORDEN(BigDecimal dF_NORDEN) {
    DF_NORDEN = dF_NORDEN;
  }

//  public DeltaCheckDTO(DeltaCheckAptoDTO dcaDto) {
//
//    this.CT_DELTAPORCENTAJE = dcaDto.getCT_DELTAPORCENTAJE();
//    this.DELTA = dcaDto.getCT_DELTAPORCENTAJE();
//    this.PROMEDIO = dcaDto.getPROMEDIO();
//    this.DFET_RESULTADONUMERICO = dcaDto.getDFET_RESULTADONUMERICO();
//    this.VALORMAX = dcaDto.getVALORMAX();
//    this.VALORMIN = dcaDto.getVALORMIN();
//
//  }

}