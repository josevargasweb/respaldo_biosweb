package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class OrdenExamenValidoDTO {

  private Long DF_IDPACIENTE;
  private Long DFE_NORDEN;
  private Long CE_IDEXAMEN;
  private String CE_ABREVIADO;
  private Short CE_DIASDEVALIDEZ;
  private Short CE_DIASVALIDEZHOSPITALIZADO;
  private String CE_DIASVALIDEZAMBULATORIOBLQ;
  private String CE_DIASVALIDEZHOSPITALIZADOBLQ;

  public Long getDF_IDPACIENTE() {
    return DF_IDPACIENTE;
  }

  public void setDF_IDPACIENTE(BigDecimal dF_IDPACIENTE) {
    DF_IDPACIENTE = dF_IDPACIENTE.longValueExact();
  }

  public Long getDFE_NORDEN() {
    return DFE_NORDEN;
  }

  public void setDFE_NORDEN(BigDecimal dFE_NORDEN) {
    DFE_NORDEN = dFE_NORDEN.longValueExact();
  }

  public Long getCE_IDEXAMEN() {
    return CE_IDEXAMEN;
  }

  public void setCE_IDEXAMEN(BigDecimal cE_IDEXAMEN) {
    CE_IDEXAMEN = cE_IDEXAMEN.longValueExact();
  }

  public Short getCE_DIASDEVALIDEZ() {
    return CE_DIASDEVALIDEZ;
  }

  public void setCE_DIASDEVALIDEZ(BigDecimal cE_DIASDEVALIDEZ) {
    CE_DIASDEVALIDEZ = cE_DIASDEVALIDEZ.shortValue();
  }

  public Short getCE_DIASVALIDEZHOSPITALIZADO() {
    return CE_DIASVALIDEZHOSPITALIZADO;
  }

  public void setCE_DIASVALIDEZHOSPITALIZADO(BigDecimal cE_DIASVALIDEZHOSPITALIZADO) {
    CE_DIASVALIDEZHOSPITALIZADO = cE_DIASVALIDEZHOSPITALIZADO.shortValue();
  }

  public String getCE_DIASVALIDEZAMBULATORIOBLQ() {
    return CE_DIASVALIDEZAMBULATORIOBLQ;
  }

  public void setCE_DIASVALIDEZAMBULATORIOBLQ(String cE_DIASVALIDEZAMBULATORIOBLQ) {
    CE_DIASVALIDEZAMBULATORIOBLQ = cE_DIASVALIDEZAMBULATORIOBLQ;
  }

  public String getCE_DIASVALIDEZHOSPITALIZADOBLQ() {
    return CE_DIASVALIDEZHOSPITALIZADOBLQ;
  }

  public void setCE_DIASVALIDEZHOSPITALIZADOBLQ(String cE_DIASVALIDEZHOSPITALIZADOBLQ) {
    CE_DIASVALIDEZHOSPITALIZADOBLQ = cE_DIASVALIDEZHOSPITALIZADOBLQ;
  }

  public String getCE_ABREVIADO() {
    return CE_ABREVIADO;
  }

  public void setCE_ABREVIADO(String cE_ABREVIADO) {
    CE_ABREVIADO = cE_ABREVIADO;
  }

}
