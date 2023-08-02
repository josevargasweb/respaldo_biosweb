package com.grupobios.bioslis.dto;

import java.util.ArrayList;
import java.util.List;

import com.grupobios.bioslis.entity.CfgTest;

public class HijosTest {

  public HijosTest() {

    hijos = new ArrayList<>();

  }

  private CfgTest padre;
  private List<CfgTest> hijos;

  public CfgTest getPadre() {
    return padre;
  }

  public void setPadre(CfgTest padre) {
    this.padre = padre;
  }

  public List<CfgTest> getHijos() {
    return hijos;
  }

  public void setHijos(List<CfgTest> hijos) {
    this.hijos = hijos;
  }

@Override
public String toString() {
    return "HijosTest [padre=" + padre + ", hijos=" + hijos + "]";
}

  
}
