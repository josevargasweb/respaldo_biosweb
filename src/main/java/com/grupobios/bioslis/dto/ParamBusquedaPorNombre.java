package com.grupobios.bioslis.dto;

public class ParamBusquedaPorNombre {

  private String nombrePaciente;
  private String apellidoPaciente;
  private String segundoApellidoPaciente;

  public String getNombrePaciente() {
    return nombrePaciente;
  }

  public void setNombrePaciente(String nombrePaciente) {
    this.nombrePaciente = nombrePaciente;
  }

  public String getApellidoPaciente() {
    return apellidoPaciente;
  }

  public void setApellidoPaciente(String apellidoPaciente) {
    this.apellidoPaciente = apellidoPaciente;
  }

  public String getSegundoApellidoPaciente() {
    return segundoApellidoPaciente;
  }

  public void setSegundoApellidoPaciente(String segundoApellidoPaciente) {
    this.segundoApellidoPaciente = segundoApellidoPaciente;
  }

}
