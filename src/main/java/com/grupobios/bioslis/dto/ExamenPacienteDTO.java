/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */

public class ExamenPacienteDTO {
    private BigDecimal DP_IDPACIENTE;
    private String NOMBRES;
    private BigDecimal CE_IDEXAMEN;
    private String CE_ABREVIADO;
    private String DFE_EXAMENANULADO;
    //private List<TestCurvaDTO> lstCurvas;

    public BigDecimal getDP_IDPACIENTE() {
        return DP_IDPACIENTE;
    }

    public void setDP_IDPACIENTE(BigDecimal DP_IDPACIENTE) {
        this.DP_IDPACIENTE = DP_IDPACIENTE;
    }

    public String getNOMBRES() {
        return NOMBRES;
    }

    public void setNOMBRES(String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }

    public BigDecimal getCE_IDEXAMEN() {
        return CE_IDEXAMEN;
    }

    public void setCE_IDEXAMEN(BigDecimal CE_IDEXAMEN) {
        this.CE_IDEXAMEN = CE_IDEXAMEN;
    }

    public String getCE_ABREVIADO() {
        return CE_ABREVIADO;
    }

    public void setCE_ABREVIADO(String CE_ABREVIADO) {
        this.CE_ABREVIADO = CE_ABREVIADO;
    }
/*
    public List<TestCurvaDTO> getLstCurvas() {
        return lstCurvas;
    }

    public void setLstCurvas(List<TestCurvaDTO> lstCurvas) {
        this.lstCurvas = lstCurvas;
    }
    */

    public String getDFE_EXAMENANULADO() {
        return DFE_EXAMENANULADO;
    }

    public void setDFE_EXAMENANULADO(String DFE_EXAMENANULADO) {
        this.DFE_EXAMENANULADO = DFE_EXAMENANULADO;
    }
}
