package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ResultadoTestPacienteDTO {

  private BigDecimal DFET_IDTEST;
  private Timestamp DFET_FECHAINGRESORESULTADO;
  private String DFET_RESULTADO;
  private BigDecimal DF_NORDEN;
  private BigDecimal DFE_IDEXAMEN;
  private BigDecimal DFET_RESULTADONUMERICO;
  private BigDecimal DFET_IDUNIDADDEMEDIDA;

  public ResultadoTestPacienteDTO() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @return the dFET_IDTEST
   */
  public BigDecimal getDFET_IDTEST() {
    return DFET_IDTEST;
  }

  /**
   * @param dFET_IDTEST the dFET_IDTEST to set
   */
  public void setDFET_IDTEST(BigDecimal dFET_IDTEST) {
    DFET_IDTEST = dFET_IDTEST;
  }

  /**
   * @return the dFET_FECHAINGRESORESULTADO
   */
  public Timestamp getDFET_FECHAINGRESORESULTADO() {
    return DFET_FECHAINGRESORESULTADO;
  }

  /**
   * @param dFET_FECHAINGRESORESULTADO the dFET_FECHAINGRESORESULTADO to set
   */
  public void setDFET_FECHAINGRESORESULTADO(Timestamp dFET_FECHAINGRESORESULTADO) {
    DFET_FECHAINGRESORESULTADO = dFET_FECHAINGRESORESULTADO;
  }

  /**
   * @return the dFET_RESULTADO
   */
  public String getDFET_RESULTADO() {
    return DFET_RESULTADO;
  }

  /**
   * @param dFET_RESULTADO the dFET_RESULTADO to set
   */
  public void setDFET_RESULTADO(String dFET_RESULTADO) {
    DFET_RESULTADO = dFET_RESULTADO;
  }

  /**
   * @return the dF_NORDEN
   */
  public BigDecimal getDF_NORDEN() {
    return DF_NORDEN;
  }

  /**
   * @param dF_NORDEN the dF_NORDEN to set
   */
  public void setDF_NORDEN(BigDecimal dF_NORDEN) {
    DF_NORDEN = dF_NORDEN;
  }

  /**
   * @return the dFE_IDEXAMEN
   */
  public BigDecimal getDFE_IDEXAMEN() {
    return DFE_IDEXAMEN;
  }

  /**
   * @param dFE_IDEXAMEN the dFE_IDEXAMEN to set
   */
  public void setDFE_IDEXAMEN(BigDecimal dFE_IDEXAMEN) {
    DFE_IDEXAMEN = dFE_IDEXAMEN;
  }

  /**
   * @return the dFET_RESULTADONUMERICO
   */
  public BigDecimal getDFET_RESULTADONUMERICO() {
    return DFET_RESULTADONUMERICO;
  }

  /**
   * @param dFET_RESULTADONUMERICO the dFET_RESULTADONUMERICO to set
   */
  public void setDFET_RESULTADONUMERICO(BigDecimal dFET_RESULTADONUMERICO) {
    DFET_RESULTADONUMERICO = dFET_RESULTADONUMERICO;
  }

  /**
   * @return the dFET_IDUNIDADDEMEDIDA
   */
  public BigDecimal getDFET_IDUNIDADDEMEDIDA() {
    return DFET_IDUNIDADDEMEDIDA;
  }

  /**
   * @param dFET_IDUNIDADDEMEDIDA the dFET_IDUNIDADDEMEDIDA to set
   */
  public void setDFET_IDUNIDADDEMEDIDA(BigDecimal dFET_IDUNIDADDEMEDIDA) {
    DFET_IDUNIDADDEMEDIDA = dFET_IDUNIDADDEMEDIDA;
  }

  @Override
  public String toString() {
    return "ResultadoTestPacienteDTO [DFET_IDTEST=" + DFET_IDTEST + ", DFET_FECHAINGRESORESULTADO="
        + DFET_FECHAINGRESORESULTADO + ", DFET_RESULTADO=" + DFET_RESULTADO + ", DF_NORDEN=" + DF_NORDEN
        + ", DFE_IDEXAMEN=" + DFE_IDEXAMEN + ", DFET_RESULTADONUMERICO=" + DFET_RESULTADONUMERICO
        + ", DFET_IDUNIDADDEMEDIDA=" + DFET_IDUNIDADDEMEDIDA + "]";
  }

}
