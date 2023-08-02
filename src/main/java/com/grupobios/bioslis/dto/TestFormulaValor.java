package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

import com.grupobios.bioslis.entity.CfgTest;

public class TestFormulaValor {

  private CfgTest cfgtest;
  private BigDecimal valor;

  public TestFormulaValor(CfgTest cfgtest, BigDecimal valor) {
    super();
    this.setCfgtest(cfgtest);
    this.setValor(valor);
  }

  public CfgTest getCfgtest() {
    return cfgtest;
  }

  public void setCfgtest(CfgTest cfgtest) {
    this.cfgtest = cfgtest;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

}
