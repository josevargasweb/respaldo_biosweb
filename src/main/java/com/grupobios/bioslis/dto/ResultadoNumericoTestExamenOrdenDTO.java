package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class ResultadoNumericoTestExamenOrdenDTO {

  private BigDecimal DF_NORDEN;
  private BigDecimal DFE_IDEXAMEN;
  private String DFET_RESULTADO;
  private BigDecimal DFET_IDTEST;
  private String CTR_CODIGO;
  // agregado por cristian 04-11
  private Long idUsuarioDigita;
  private Timestamp fechaDigita;
  private String TextResultado;
  private String CERT_DESCRIPCIONESTADOTEST;
  private String RESULTADOTEXTO;
  private Date FECHARESULTADO;
  private String DFET_REFERENCIADESDE;  
  private String DFET_REFERENCIAHASTA;
  private String tipoResultado;


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

  public String getCTR_CODIGO() {
    return CTR_CODIGO;
  }

  public void setCTR_CODIGO(String cTR_CODIGO) {
    CTR_CODIGO = cTR_CODIGO;
  }

  // **agregado por cristian 07-11/*******************
  public Long getIdUsuarioDigita() {
    return idUsuarioDigita;
  }

  public void setIdUsuarioDigita(Long idUsuarioDigita) {
    this.idUsuarioDigita = idUsuarioDigita;
  }

  public Timestamp getFechaDigita() {
    return fechaDigita;
  }

  public void setFechaDigita(Timestamp fechaDigita) {
    this.fechaDigita = fechaDigita;
  }

 

@Override
public String toString() {
    return "ResultadoNumericoTestExamenOrdenDTO [DF_NORDEN=" + DF_NORDEN + ", DFE_IDEXAMEN=" + DFE_IDEXAMEN
            + ", DFET_RESULTADO=" + DFET_RESULTADO + ", DFET_IDTEST=" + DFET_IDTEST + ", CTR_CODIGO=" + CTR_CODIGO
            + ", idUsuarioDigita=" + idUsuarioDigita + ", fechaDigita=" + fechaDigita + ", TextResultado="
            + TextResultado + ", CERT_DESCRIPCIONESTADOTEST=" + CERT_DESCRIPCIONESTADOTEST + ", RESULTADOTEXTO="
            + RESULTADOTEXTO + ", FECHARESULTADO=" + FECHARESULTADO + ", DFET_REFERENCIADESDE=" + DFET_REFERENCIADESDE
            + ", DFET_REFERENCIAHASTA=" + DFET_REFERENCIAHASTA + ", tipoResultado=" + tipoResultado + "]";
}

public String getTextResultado() {
    return TextResultado;
}

public void setTextResultado(String textResultado) {
    TextResultado = textResultado;
}

public String getCERT_DESCRIPCIONESTADOTEST() {
    return CERT_DESCRIPCIONESTADOTEST;
}

public void setCERT_DESCRIPCIONESTADOTEST(String cERT_DESCRIPCIONESTADOTEST) {
    CERT_DESCRIPCIONESTADOTEST = cERT_DESCRIPCIONESTADOTEST;
}

public String getRESULTADOTEXTO() {
    return RESULTADOTEXTO;
}

public void setRESULTADOTEXTO(String rESULTADOTEXTO) {
    RESULTADOTEXTO = rESULTADOTEXTO;
}

public Date getFECHARESULTADO() {
    return FECHARESULTADO;
}

public void setFECHARESULTADO(Date fECHARESULTADO) {
    FECHARESULTADO = fECHARESULTADO;
}

public String getDFET_REFERENCIADESDE() {
    return DFET_REFERENCIADESDE;
}

public void setDFET_REFERENCIADESDE(String dFET_REFERENCIADESDE) {
    DFET_REFERENCIADESDE = dFET_REFERENCIADESDE;
}

public String getDFET_REFERENCIAHASTA() {
    return DFET_REFERENCIAHASTA;
}

public void setDFET_REFERENCIAHASTA(String dFET_REFERENCIAHASTA) {
    DFET_REFERENCIAHASTA = dFET_REFERENCIAHASTA;
}

public String getTipoResultado() {
    return tipoResultado;
}

public void setTipoResultado(String tipoResultado) {
    this.tipoResultado = tipoResultado;
}


//****************************************************** 



}
