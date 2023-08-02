package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.Date;

public class UltimoLogEventoCampoDatoFicha {

  private BigDecimal DATOSFICHAS;
  private String LEFNOMBRETABLA;
  private String LEFNOMBRECAMPO;
  private Date UFECHAREGISTRO;

  public Date getUFECHAREGISTRO() {
    return UFECHAREGISTRO;
  }

  public void setUFECHAREGISTRO(Date UFECHAREGISTRO) {
    this.UFECHAREGISTRO = UFECHAREGISTRO;
  }

  public BigDecimal getDATOSFICHAS() {
    return DATOSFICHAS;
  }

  public void setDATOSFICHAS(BigDecimal dATOSFICHAS) {
    DATOSFICHAS = dATOSFICHAS;
  }

  public String getLEFNOMBRETABLA() {
    return LEFNOMBRETABLA;
  }

  public void setLEFNOMBRETABLA(String LEFNOMBRETABLA) {
    this.LEFNOMBRETABLA = LEFNOMBRETABLA;
  }

  public String getLEFNOMBRECAMPO() {
    return LEFNOMBRECAMPO;
  }

  public void setLEFNOMBRECAMPO(String LEFNOMBRECAMPO) {
    this.LEFNOMBRECAMPO = LEFNOMBRECAMPO;
  }

  @Override
  public String toString() {
    return "UltimoLogEventoCampoDatoFicha [DATOSFICHAS=" + DATOSFICHAS + ", LEFNOMBRETABLA=" + LEFNOMBRETABLA
        + ", LEFNOMBRECAMPO=" + LEFNOMBRECAMPO + ", UFECHAREGISTRO=" + UFECHAREGISTRO + "]";
  }

}
