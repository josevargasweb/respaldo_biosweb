package com.grupobios.bioslis.dto;

public class FiltroTestDTO {

  @Override
  public String toString() {
    return "FiltroTestDTO [labId=" + labId + ", seccion=" + seccion + ", testId=" + testId + "]";
  }

  private Integer labId;
  private Integer seccion;
  private Integer testId;
  private Integer examenId;

  public FiltroTestDTO() {
  }

  public Integer getLabId() {
    return labId;
  }

  public void setLabId(Integer labId) {
    this.labId = labId;
  }

  public Integer getSeccion() {
    return seccion;
  }

  public void setSeccion(Integer seccion) {
    this.seccion = seccion;
  }

  public Integer getTestId() {
    return testId;
  }

  public void setTestId(Integer testId) {
    this.testId = testId;
  }

	public Integer getExamenId() {
		return examenId;
	}
	
	public void setExamenId(Integer examenId) {
		this.examenId = examenId;
	}
  

}
