package com.grupobios.bioslis.dto;

public class FichaFiltroMicroDTO extends FichaFiltroDTO {

  private Integer nOrdenFin;
  private Integer idServicio;
  private Integer idExamen;

  public FichaFiltroMicroDTO() {
  }

  public Integer getnOrdenFin() {
    return nOrdenFin;
  }

  public void setnOrdenFin(Integer nOrdenFin) {
    this.nOrdenFin = nOrdenFin;
  }

  public Integer getIdServicio() {
    return idServicio;
  }

  public void setIdServicio(Integer idServicio) {
    this.idServicio = idServicio;
  }

  public Integer getIdExamen() {
    return idExamen;
  }

  public void setIdExamen(Integer idExamen) {
    this.idExamen = idExamen;
  }

}
