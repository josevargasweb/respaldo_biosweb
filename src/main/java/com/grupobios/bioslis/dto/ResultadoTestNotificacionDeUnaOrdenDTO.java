package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ResultadoTestNotificacionDeUnaOrdenDTO {

  private BigDecimal DFET_RESULTADONUMERICO;
  private BigDecimal DFET_IDUNIDADDEMEDIDA;
  private String DFET_REFERENCIADESDE;
  private String DFET_REFERENCIAHASTA;
  private String DFET_VALORCRITICODESDE;
  private String DFET_VALORCRITICOHASTA;
  private BigDecimal DFE_IDEXAMEN;
  private BigDecimal DF_NORDEN;
  private BigDecimal DFET_IDTEST;
  private String CE_ABREVIADO;
  private String CT_ABREVIADO;
  private String CTR_DESCRIPCION;
  private String CT_FORMULA;
  private String CUM_DESCRIPCION;
  private BigDecimal CT_IDUNIDADMEDIDA;
  private BigDecimal CERT_IDESTADORESULTADOTEST;
  private String CERT_DESCRIPCIONESTADOTEST;
  private String CTR_CODIGO;
  private String DFET_RESULTADO;
  private BigDecimal CVR_VALORCRITICOBAJO;
  private BigDecimal CVR_VALORCRITICOALTO;
  private BigDecimal CVR_VALORBAJO;
  private BigDecimal CVR_VALORALTO;
  private String CVR_VALORTEXTO;
  private BigDecimal CVR_DIASDESDE;
  private BigDecimal CVR_MESESDESDE;
  private BigDecimal CVR_ANOSDESDE;
  private BigDecimal CVR_DIASHASTA;
  private BigDecimal CVR_MESESHASTA;
  private BigDecimal CVR_ANOSHASTA;
  private String CVR_SEXO;
  private String DFET_RCRITICO;
  private BigDecimal DP_IDPACIENTE;
  private BigDecimal DFM_IDMUESTRA;
  private BigDecimal DFET_IDMUESTRA;
  private BigDecimal CT_DECIMALES;
  private String DFM_CODIGOBARRA;
  private String DP_NOMBRES;
  private String DP_PRIMERAPELLIDO;
  private String DP_SEGUNDOAPELLIDO;
  private String CM_NOMBRES;
  private String CM_PRIMERAPELLIDO;
  private String CM_SEGUNDOAPELLIDO;
  private BigDecimal CM_IDMEDICO;
  private String DP_TELEFONO;
  private String CM_TELEFONO;
  private String DP_EMAIL;
  private String CM_EMAIL;
  private BigDecimal DFTN_IDFICHATESTNOTIFICACION;
  private String DFTN_MENSAJENOTIFICACION;
  private String DFTN_EMAILENVIO;
  private BigDecimal DFTN_IDTIPORECEPTOR;
  private BigDecimal DFTN_IDUSUARIOENVIA;
  private BigDecimal DFTN_IDSTATUSNOTIFICACION;
  private Date DFTN_FECHAENVIO;
  private Date DFTN_FECHARECEPCION;
  private String DFTN_NOMBRERECEPTOR;
  private BigDecimal DFTN_IDVIANOTIFICACION;
  private BigDecimal DFTN_NUMEROINTENTOS;
  private String DFTN_NOTA;
  // agregado por jan 12-01-23
  private String DFTN_VALORCRITICONOTIFICADO;
  private BigDecimal DFET_IDFICHATESTNOTIFICACION;
  private List<datosfichastestnotificacionesDTO> NOTIFICACION;
  private Integer TIPO_NOTIFICACION;

  public Integer getTIPO_NOTIFICACION() {
    return TIPO_NOTIFICACION;
  }

  public void setTIPO_NOTIFICACION(Integer tIPO_NOTIFICACION) {
    TIPO_NOTIFICACION = tIPO_NOTIFICACION;
  }

  public String getDFM_CODIGOBARRA() {
    return DFM_CODIGOBARRA;
  }

  public void setDFM_CODIGOBARRA(String dFM_CODIGOBARRA) {
    DFM_CODIGOBARRA = dFM_CODIGOBARRA;
  }

  public BigDecimal getDFET_IDMUESTRA() {
    return DFET_IDMUESTRA;
  }

  public void setDFET_IDMUESTRA(BigDecimal dFET_IDMUESTRA) {
    DFET_IDMUESTRA = dFET_IDMUESTRA;
  }

  private String resultadoHistorico;

  public BigDecimal getDF_NORDEN() {
    return DF_NORDEN;
  }

  public void setDF_NORDEN(BigDecimal dFE_NORDEN) {
    DF_NORDEN = dFE_NORDEN;
  }

  public BigDecimal getDFE_IDEXAMEN() {
    return DFE_IDEXAMEN;
  }

  public void setDFE_IDEXAMEN(BigDecimal dFE_IDEXAMEN) {
    DFE_IDEXAMEN = dFE_IDEXAMEN;
  }

  public String getCE_ABREVIADO() {
    return CE_ABREVIADO;
  }

  public void setCE_ABREVIADO(String cE_ABREVIADO) {
    CE_ABREVIADO = cE_ABREVIADO;
  }

  public BigDecimal getDFET_RESULTADONUMERICO() {
    return DFET_RESULTADONUMERICO;
  }

  public void setDFET_RESULTADONUMERICO(BigDecimal dFET_RESULTADONUMERICO) {
    DFET_RESULTADONUMERICO = dFET_RESULTADONUMERICO;
  }

  public BigDecimal getDFET_IDUNIDADDEMEDIDA() {
    return DFET_IDUNIDADDEMEDIDA;
  }

  public void setDFET_IDUNIDADDEMEDIDA(BigDecimal dFET_IDUNIDADDEMEDIDA) {
    DFET_IDUNIDADDEMEDIDA = dFET_IDUNIDADDEMEDIDA;
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

  public BigDecimal getDFET_IDTEST() {
    return DFET_IDTEST;
  }

  public void setDFET_IDTEST(BigDecimal dFET_IDTEST) {
    DFET_IDTEST = dFET_IDTEST;
  }

  public String getCT_ABREVIADO() {
    return CT_ABREVIADO;
  }

  public void setCT_ABREVIADO(String cT_ABREVIADO) {
    CT_ABREVIADO = cT_ABREVIADO;
  }

  public String getCTR_DESCRIPCION() {
    return CTR_DESCRIPCION;
  }

  public void setCTR_DESCRIPCION(String cTR_DESCRIPCION) {
    CTR_DESCRIPCION = cTR_DESCRIPCION;
  }

  public String getCT_FORMULA() {
    return CT_FORMULA;
  }

  public void setCT_FORMULA(String cT_FORMULA) {
    CT_FORMULA = cT_FORMULA;
  }

  public String getCUM_DESCRIPCION() {
    return CUM_DESCRIPCION;
  }

  public void setCUM_DESCRIPCION(String cUM_DESCRIPCION) {
    CUM_DESCRIPCION = cUM_DESCRIPCION;
  }

  public BigDecimal getCT_IDUNIDADMEDIDA() {
    return CT_IDUNIDADMEDIDA;
  }

  public void setCT_IDUNIDADMEDIDA(BigDecimal cT_IDUNIDADMEDIDA) {
    CT_IDUNIDADMEDIDA = cT_IDUNIDADMEDIDA;
  }

  public BigDecimal getCERT_IDESTADORESULTADOTEST() {
    return CERT_IDESTADORESULTADOTEST;
  }

  public void setCERT_IDESTADORESULTADOTEST(BigDecimal cERT_IDESTADORESULTADOTEST) {
    CERT_IDESTADORESULTADOTEST = cERT_IDESTADORESULTADOTEST;
  }

  public String getCERT_DESCRIPCIONESTADOTEST() {
    return CERT_DESCRIPCIONESTADOTEST;
  }

  public void setCERT_DESCRIPCIONESTADOTEST(String cERT_DESCRIPCIONESTADOTEST) {
    CERT_DESCRIPCIONESTADOTEST = cERT_DESCRIPCIONESTADOTEST;
  }

  public String getCTR_CODIGO() {
    return CTR_CODIGO;
  }

  public void setCTR_CODIGO(String cTR_CODIGO) {
    CTR_CODIGO = cTR_CODIGO;
  }

  public String getDFET_RESULTADO() {
    return DFET_RESULTADO;
  }

  public void setDFET_RESULTADO(String dFET_RESULTADO) {
    DFET_RESULTADO = dFET_RESULTADO;
  }

  public BigDecimal getCVR_VALORCRITICOBAJO() {
    return CVR_VALORCRITICOBAJO;
  }

  public void setCVR_VALORCRITICOBAJO(BigDecimal cVR_VALORCRITICOBAJO) {
    CVR_VALORCRITICOBAJO = cVR_VALORCRITICOBAJO;
  }

  public BigDecimal getCVR_VALORCRITICOALTO() {
    return CVR_VALORCRITICOALTO;
  }

  public void setCVR_VALORCRITICOALTO(BigDecimal cVR_VALORCRITICOALTO) {
    CVR_VALORCRITICOALTO = cVR_VALORCRITICOALTO;
  }

  public BigDecimal getCVR_VALORBAJO() {
    return CVR_VALORBAJO;
  }

  public void setCVR_VALORBAJO(BigDecimal cVR_VALORBAJO) {
    CVR_VALORBAJO = cVR_VALORBAJO;
  }

  public BigDecimal getCVR_VALORALTO() {
    return CVR_VALORALTO;
  }

  public void setCVR_VALORALTO(BigDecimal cVR_VALORALTO) {
    CVR_VALORALTO = cVR_VALORALTO;
  }

  public String getCVR_VALORTEXTO() {
    return CVR_VALORTEXTO;
  }

  public void setCVR_VALORTEXTO(String cVR_VALORTEXTO) {
    CVR_VALORTEXTO = cVR_VALORTEXTO;
  }

  public BigDecimal getCVR_DIASDESDE() {
    return CVR_DIASDESDE;
  }

  public void setCVR_DIASDESDE(BigDecimal cVR_DIASDESDE) {
    CVR_DIASDESDE = cVR_DIASDESDE;
  }

  public BigDecimal getCVR_ANOSDESDE() {
    return CVR_ANOSDESDE;
  }

  public void setCVR_ANOSDESDE(BigDecimal cVR_ANOSDESDE) {
    CVR_ANOSDESDE = cVR_ANOSDESDE;
  }

  public BigDecimal getCVR_DIASHASTA() {
    return CVR_DIASHASTA;
  }

  public void setCVR_DIASHASTA(BigDecimal cVR_DIASHASTA) {
    CVR_DIASHASTA = cVR_DIASHASTA;
  }

  public BigDecimal getCVR_ANOSHASTA() {
    return CVR_ANOSHASTA;
  }

  public void setCVR_ANOSHASTA(BigDecimal cVR_ANOSHASTA) {
    CVR_ANOSHASTA = cVR_ANOSHASTA;
  }

  public BigDecimal getCVR_MESESDESDE() {
    return CVR_MESESDESDE;
  }

  public void setCVR_MESESDESDE(BigDecimal cVR_MESESDESDE) {
    CVR_MESESDESDE = cVR_MESESDESDE;
  }

  public BigDecimal getCVR_MESESHASTA() {
    return CVR_MESESHASTA;
  }

  public void setCVR_MESESHASTA(BigDecimal cVR_MESESHASTA) {
    CVR_MESESHASTA = cVR_MESESHASTA;
  }

  public String getCVR_SEXO() {
    return CVR_SEXO;
  }

  public void setCVR_SEXO(String cVR_SEXO) {
    CVR_SEXO = cVR_SEXO;
  }

  /**
   * @return the dFET_RCRITICO
   */
  public String getDFET_RCRITICO() {
    return DFET_RCRITICO;
  }

  /**
   * @param dFET_RCRITICO the dFET_RCRITICO to set
   */
  public void setDFET_RCRITICO(String dFET_RCRITICO) {
    DFET_RCRITICO = dFET_RCRITICO;
  }

  /**
   * @return the dP_IDPACIENTE
   */
  public BigDecimal getDP_IDPACIENTE() {
    return DP_IDPACIENTE;
  }

  /**
   * @param dP_IDPACIENTE the dP_IDPACIENTE to set
   */
  public void setDP_IDPACIENTE(BigDecimal dP_IDPACIENTE) {
    DP_IDPACIENTE = dP_IDPACIENTE;
  }

  /**
   * @return the resultadoHistorico
   */
  public String getResultadoHistorico() {
    return resultadoHistorico;
  }

  /**
   * @param resultadoHistorico the resultadoHistorico to set
   */
  public void setResultadoHistorico(String resultadoHistorico) {
    this.resultadoHistorico = resultadoHistorico;
  }

  /**
   * @return the dFM_IDMUESTRA
   */
  public BigDecimal getDFM_IDMUESTRA() {
    return DFM_IDMUESTRA;
  }

  /**
   * @param dFM_IDMUESTRA the dFM_IDMUESTRA to set
   */
  public void setDFM_IDMUESTRA(BigDecimal dFM_IDMUESTRA) {
    DFM_IDMUESTRA = dFM_IDMUESTRA;
  }

  public BigDecimal getCT_DECIMALES() {
    return CT_DECIMALES;
  }

  public void setCT_DECIMALES(BigDecimal cT_DECIMALES) {
    CT_DECIMALES = cT_DECIMALES;
  }

  public String getDP_NOMBRES() {
    return DP_NOMBRES;
  }

  public void setDP_NOMBRES(String dP_NOMBRES) {
    DP_NOMBRES = dP_NOMBRES;
  }

  public String getDP_PRIMERAPELLIDO() {
    return DP_PRIMERAPELLIDO;
  }

  public void setDP_PRIMERAPELLIDO(String dP_PRIMERAPELLIDO) {
    DP_PRIMERAPELLIDO = dP_PRIMERAPELLIDO;
  }

  public String getDP_SEGUNDOAPELLIDO() {
    return DP_SEGUNDOAPELLIDO;
  }

  public void setDP_SEGUNDOAPELLIDO(String dP_SEGUNDOAPELLIDO) {
    DP_SEGUNDOAPELLIDO = dP_SEGUNDOAPELLIDO;
  }

  public String getCM_NOMBRES() {
    return CM_NOMBRES;
  }

  public void setCM_NOMBRES(String cM_NOMBRES) {
    CM_NOMBRES = cM_NOMBRES;
  }

  public String getCM_PRIMERAPELLIDO() {
    return CM_PRIMERAPELLIDO;
  }

  public void setCM_PRIMERAPELLIDO(String cM_PRIMERAPELLIDO) {
    CM_PRIMERAPELLIDO = cM_PRIMERAPELLIDO;
  }

  public String getCM_SEGUNDOAPELLIDO() {
    return CM_SEGUNDOAPELLIDO;
  }

  public void setCM_SEGUNDOAPELLIDO(String cM_SEGUNDOAPELLIDO) {
    CM_SEGUNDOAPELLIDO = cM_SEGUNDOAPELLIDO;
  }

  public BigDecimal getCM_IDMEDICO() {
    return CM_IDMEDICO;
  }

  public void setCM_IDMEDICO(BigDecimal cM_IDMEDICO) {
    CM_IDMEDICO = cM_IDMEDICO;
  }

  public String getDP_TELEFONO() {
    return DP_TELEFONO;
  }

  public void setDP_TELEFONO(String dP_TELEFONO) {
    DP_TELEFONO = dP_TELEFONO;
  }

  public String getCM_TELEFONO() {
    return CM_TELEFONO;
  }

  public void setCM_TELEFONO(String cM_TELEFONO) {
    CM_TELEFONO = cM_TELEFONO;
  }

  public String getDP_EMAIL() {
    return DP_EMAIL;
  }

  public void setDP_EMAIL(String dP_EMAIL) {
    DP_EMAIL = dP_EMAIL;
  }

  public String getCM_EMAIL() {
    return CM_EMAIL;
  }

  public void setCM_EMAIL(String cM_EMAIL) {
    CM_EMAIL = cM_EMAIL;
  }

  public BigDecimal getDFTN_IDFICHATESTNOTIFICACION() {
    return DFTN_IDFICHATESTNOTIFICACION;
  }

  public void setDFTN_IDFICHATESTNOTIFICACION(BigDecimal dFTN_IDFICHATESTNOTIFICACION) {
    DFTN_IDFICHATESTNOTIFICACION = dFTN_IDFICHATESTNOTIFICACION;
  }

  public String getDFTN_MENSAJENOTIFICACION() {
    return DFTN_MENSAJENOTIFICACION;
  }

  public void setDFTN_MENSAJENOTIFICACION(String dFTN_MENSAJENOTIFICACION) {
    DFTN_MENSAJENOTIFICACION = dFTN_MENSAJENOTIFICACION;
  }

  public String getDFTN_EMAILENVIO() {
    return DFTN_EMAILENVIO;
  }

  public void setDFTN_EMAILENVIO(String dFTN_EMAILENVIO) {
    DFTN_EMAILENVIO = dFTN_EMAILENVIO;
  }

  public BigDecimal getDFTN_IDTIPORECEPTOR() {
    return DFTN_IDTIPORECEPTOR;
  }

  public void setDFTN_IDTIPORECEPTOR(BigDecimal dFTN_IDTIPORECEPTOR) {
    DFTN_IDTIPORECEPTOR = dFTN_IDTIPORECEPTOR;
  }

  public BigDecimal getDFTN_IDUSUARIOENVIA() {
    return DFTN_IDUSUARIOENVIA;
  }

  public void setDFTN_IDUSUARIOENVIA(BigDecimal dFTN_IDUSUARIOENVIA) {
    DFTN_IDUSUARIOENVIA = dFTN_IDUSUARIOENVIA;
  }

  public BigDecimal getDFTN_IDSTATUSNOTIFICACION() {
    return DFTN_IDSTATUSNOTIFICACION;
  }

  public void setDFTN_IDSTATUSNOTIFICACION(BigDecimal dFTN_IDSTATUSNOTIFICACION) {
    DFTN_IDSTATUSNOTIFICACION = dFTN_IDSTATUSNOTIFICACION;
  }

  public Date getDFTN_FECHAENVIO() {
    return DFTN_FECHAENVIO;
  }

  public void setDFTN_FECHAENVIO(Date dFTN_FECHAENVIO) {
    DFTN_FECHAENVIO = dFTN_FECHAENVIO;
  }

  public Date getDFTN_FECHARECEPCION() {
    return DFTN_FECHARECEPCION;
  }

  public void setDFTN_FECHARECEPCION(Date dFTN_FECHARECEPCION) {
    DFTN_FECHARECEPCION = dFTN_FECHARECEPCION;
  }

  public String getDFTN_NOMBRERECEPTOR() {
    return DFTN_NOMBRERECEPTOR;
  }

  public void setDFTN_NOMBRERECEPTOR(String dFTN_NOMBRERECEPTOR) {
    DFTN_NOMBRERECEPTOR = dFTN_NOMBRERECEPTOR;
  }

  public BigDecimal getDFTN_IDVIANOTIFICACION() {
    return DFTN_IDVIANOTIFICACION;
  }

  public void setDFTN_IDVIANOTIFICACION(BigDecimal dFTN_IDVIANOTIFICACION) {
    DFTN_IDVIANOTIFICACION = dFTN_IDVIANOTIFICACION;
  }

  public BigDecimal getDFTN_NUMEROINTENTOS() {
    return DFTN_NUMEROINTENTOS;
  }

  public void setDFTN_NUMEROINTENTOS(BigDecimal dFTN_NUMEROINTENTOS) {
    DFTN_NUMEROINTENTOS = dFTN_NUMEROINTENTOS;
  }

  public String getDFTN_NOTA() {
    return DFTN_NOTA;
  }

  public void setDFTN_NOTA(String dFTN_NOTA) {
    DFTN_NOTA = dFTN_NOTA;
  }

  // Agregado JAN
  public String getDFTN_VALORCRITICONOTIFICADO() {
    return DFTN_VALORCRITICONOTIFICADO;
  }

  public void setDFTN_VALORCRITICONOTIFICADO(String dFTN_VALORCRITICONOTIFICADO) {
    DFTN_VALORCRITICONOTIFICADO = dFTN_VALORCRITICONOTIFICADO;
  }

  public List<datosfichastestnotificacionesDTO> getNOTIFICACION() {
    return NOTIFICACION;
  }

  public void setNOTIFICACION(List<datosfichastestnotificacionesDTO> nOTIFICACION) {
    NOTIFICACION = nOTIFICACION;
  }

  public BigDecimal getDFET_IDFICHATESTNOTIFICACION() {
    return DFET_IDFICHATESTNOTIFICACION;
  }

  public void setDFET_IDFICHATESTNOTIFICACION(BigDecimal dFET_IDFICHATESTNOTIFICACION) {
    DFET_IDFICHATESTNOTIFICACION = dFET_IDFICHATESTNOTIFICACION;
  }

  public String getDFET_VALORCRITICODESDE() {
    return DFET_VALORCRITICODESDE;
  }

  public void setDFET_VALORCRITICODESDE(String dFET_VALORCRITICODESDE) {
    DFET_VALORCRITICODESDE = dFET_VALORCRITICODESDE;
  }

  public String getDFET_VALORCRITICOHASTA() {
    return DFET_VALORCRITICOHASTA;
  }

  public void setDFET_VALORCRITICOHASTA(String dFET_VALORCRITICOHASTA) {
    DFET_VALORCRITICOHASTA = dFET_VALORCRITICOHASTA;
  }

  @Override
  public String toString() {
    return "ResultadoTestNotificacionDeUnaOrdenDTO [DFET_RESULTADONUMERICO=" + DFET_RESULTADONUMERICO
        + ", DFET_IDUNIDADDEMEDIDA=" + DFET_IDUNIDADDEMEDIDA + ", DFET_REFERENCIADESDE=" + DFET_REFERENCIADESDE
        + ", DFET_REFERENCIAHASTA=" + DFET_REFERENCIAHASTA + ", DFE_IDEXAMEN=" + DFE_IDEXAMEN + ", DF_NORDEN="
        + DF_NORDEN + ", DFET_IDTEST=" + DFET_IDTEST + ", CE_ABREVIADO=" + CE_ABREVIADO + ", CT_ABREVIADO="
        + CT_ABREVIADO + ", CTR_DESCRIPCION=" + CTR_DESCRIPCION + ", CT_FORMULA=" + CT_FORMULA + ", CUM_DESCRIPCION="
        + CUM_DESCRIPCION + ", CT_IDUNIDADMEDIDA=" + CT_IDUNIDADMEDIDA + ", CERT_IDESTADORESULTADOTEST="
        + CERT_IDESTADORESULTADOTEST + ", CERT_DESCRIPCIONESTADOTEST=" + CERT_DESCRIPCIONESTADOTEST + ", CTR_CODIGO="
        + CTR_CODIGO + ", DFET_RESULTADO=" + DFET_RESULTADO + ", CVR_VALORCRITICOBAJO=" + CVR_VALORCRITICOBAJO
        + ", CVR_VALORCRITICOALTO=" + CVR_VALORCRITICOALTO + ", CVR_VALORBAJO=" + CVR_VALORBAJO + ", CVR_VALORALTO="
        + CVR_VALORALTO + ", CVR_VALORTEXTO=" + CVR_VALORTEXTO + ", CVR_DIASDESDE=" + CVR_DIASDESDE
        + ", CVR_MESESDESDE=" + CVR_MESESDESDE + ", CVR_ANOSDESDE=" + CVR_ANOSDESDE + ", CVR_DIASHASTA=" + CVR_DIASHASTA
        + ", CVR_MESESHASTA=" + CVR_MESESHASTA + ", CVR_ANOSHASTA=" + CVR_ANOSHASTA + ", CVR_SEXO=" + CVR_SEXO
        + ", DFET_RCRITICO=" + DFET_RCRITICO + ", DP_IDPACIENTE=" + DP_IDPACIENTE + ", DFM_IDMUESTRA=" + DFM_IDMUESTRA
        + ", DFET_IDMUESTRA=" + DFET_IDMUESTRA + ", CT_DECIMALES=" + CT_DECIMALES + ", DFM_CODIGOBARRA="
        + DFM_CODIGOBARRA + ", DP_NOMBRES=" + DP_NOMBRES + ", DP_PRIMERAPELLIDO=" + DP_PRIMERAPELLIDO
        + ", DP_SEGUNDOAPELLIDO=" + DP_SEGUNDOAPELLIDO + ", CM_NOMBRES=" + CM_NOMBRES + ", CM_PRIMERAPELLIDO="
        + CM_PRIMERAPELLIDO + ", CM_SEGUNDOAPELLIDO=" + CM_SEGUNDOAPELLIDO + ", CM_IDMEDICO=" + CM_IDMEDICO
        + ", DP_TELEFONO=" + DP_TELEFONO + ", CM_TELEFONO=" + CM_TELEFONO + ", DP_EMAIL=" + DP_EMAIL + ", CM_EMAIL="
        + CM_EMAIL + ", DFTN_IDFICHATESTNOTIFICACION=" + DFTN_IDFICHATESTNOTIFICACION + ", DFTN_MENSAJENOTIFICACION="
        + DFTN_MENSAJENOTIFICACION + ", DFTN_EMAILENVIO=" + DFTN_EMAILENVIO + ", DFTN_IDTIPORECEPTOR="
        + DFTN_IDTIPORECEPTOR + ", DFTN_IDUSUARIOENVIA=" + DFTN_IDUSUARIOENVIA + ", DFTN_IDSTATUSNOTIFICACION="
        + DFTN_IDSTATUSNOTIFICACION + ", DFTN_FECHAENVIO=" + DFTN_FECHAENVIO + ", DFTN_FECHARECEPCION="
        + DFTN_FECHARECEPCION + ", DFTN_NOMBRERECEPTOR=" + DFTN_NOMBRERECEPTOR + ", DFTN_IDVIANOTIFICACION="
        + DFTN_IDVIANOTIFICACION + ", DFTN_NUMEROINTENTOS=" + DFTN_NUMEROINTENTOS + ", DFTN_NOTA=" + DFTN_NOTA
        + ", DFTN_VALORCRITICONOTIFICADO=" + DFTN_VALORCRITICONOTIFICADO + ", DFET_IDFICHATESTNOTIFICACION="
        + DFET_IDFICHATESTNOTIFICACION + ", NOTIFICACION=" + NOTIFICACION + ", resultadoHistorico=" + resultadoHistorico
        + "TIPO_NOTIFICACION=" + TIPO_NOTIFICACION + "]";
  }

}
