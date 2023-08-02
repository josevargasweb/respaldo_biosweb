package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.DatosPacientes;

public class DatosPacienteRnDTO {

  private DatosPacientes dp;

  public DatosPacientes getDp() {
    return dp;
  }

  public void setDp(DatosPacientes dp) {
    this.dp = dp;
  }

  private String rutMadre;

  public DatosPacienteRnDTO() {
    super();
  }

  public String getRutMadre() {
    return rutMadre;
  }

  public void setRutMadre(String rutMadre) {
    this.rutMadre = rutMadre;
  }

}
