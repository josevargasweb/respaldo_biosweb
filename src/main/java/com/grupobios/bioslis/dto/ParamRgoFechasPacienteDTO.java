package com.grupobios.bioslis.dto;

public class ParamRgoFechasPacienteDTO {

  private Integer idPaciente;
  private String fInicio;
  private String fFin;

  public Integer getIdPaciente() {
    return idPaciente;
  }

  public void setIdPaciente(Integer idPaciente) {
    this.idPaciente = idPaciente;
  }

  public String getfInicio() {
    return fInicio;
  }

  public void setfInicio(String fInicio) {
    this.fInicio = fInicio;
  }

  public String getfFin() {
    return fFin;
  }

  public void setfFin(String fFin) {
    this.fFin = fFin;
  }

}
