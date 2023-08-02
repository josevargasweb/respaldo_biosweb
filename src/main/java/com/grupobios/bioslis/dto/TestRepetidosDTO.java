package com.grupobios.bioslis.dto;

public class TestRepetidosDTO {

  private Integer idTest;
  private String descripcionTest;
  private Integer cantidad;

  public Integer getIdTest() {
    return idTest;
  }

  public void setIdTest(Integer idTest) {
    this.idTest = idTest;
  }

  public String getDescripcionTest() {
    return descripcionTest;
  }

  public void setDescripcionTest(String descripcionTest) {
    this.descripcionTest = descripcionTest;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Long cantidad) {
    this.cantidad = cantidad.intValue();
  }
}
