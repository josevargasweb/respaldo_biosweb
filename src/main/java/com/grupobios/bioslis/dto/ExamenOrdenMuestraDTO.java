/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

/**
 *
 * @author Marco Caracciolo
 */
public class ExamenOrdenMuestraDTO {
    
    private String CE_CODIGOEXAMEN;
    private String CMUE_DESCRIPCION;
    private String CMUE_DESCRIPCIONABREVIADA;
    private String CENV_DESCRIPCION;
    private String CE_DESCRIPCION;
    private String CCDS_DESCRIPCION;
    private String CLAB_DESCRIPCION;
    private String CSEC_DESCRIPCION;
    private String CE_COMPARTEMUESTRA;
    private String DF_OBSERVACION;
    private String CD_DESCRIPCION;
    private String CDERIV_DESCRIPCION;
    private BigDecimal CDERIV_IDDERIVADOR;
    private BigDecimal CE_IDEXAMEN;
    private BigDecimal DFET_IDMUESTRA;
    private BigDecimal CCDS_IDCENTRODESALUD;
    private BigDecimal CLAB_IDLABORATORIO;
    private BigDecimal CSEC_IDSECCION;
    private BigDecimal CMUE_IDTIPOMUESTRA;
    private BigDecimal CMUE_IDGRUPOMUESTRA;
    private String CE_TIENEGRUPOMUESTRA;
    private String DFE_EXAMENANULADO;

    public String getCE_CODIGOEXAMEN() {
        return CE_CODIGOEXAMEN;
    }

    public void setCE_CODIGOEXAMEN(String CE_CODIGOEXAMEN) {
        this.CE_CODIGOEXAMEN = CE_CODIGOEXAMEN;
    }

    public String getCMUE_DESCRIPCION() {
        return CMUE_DESCRIPCION;
    }

    public void setCMUE_DESCRIPCION(String CMUE_DESCRIPCION) {
        this.CMUE_DESCRIPCION = CMUE_DESCRIPCION;
    }

    public String getCMUE_DESCRIPCIONABREVIADA() {
        return CMUE_DESCRIPCIONABREVIADA;
    }

    public void setCMUE_DESCRIPCIONABREVIADA(String CMUE_DESCRIPCIONABREVIADA) {
        this.CMUE_DESCRIPCIONABREVIADA = CMUE_DESCRIPCIONABREVIADA;
    }

    public String getCENV_DESCRIPCION() {
        return CENV_DESCRIPCION;
    }

    public void setCENV_DESCRIPCION(String CENV_DESCRIPCION) {
        this.CENV_DESCRIPCION = CENV_DESCRIPCION;
    }

    public String getCE_DESCRIPCION() {
        return CE_DESCRIPCION;
    }

    public void setCE_DESCRIPCION(String CE_DESCRIPCION) {
        this.CE_DESCRIPCION = CE_DESCRIPCION;
    }

    public String getCCDS_DESCRIPCION() {
        return CCDS_DESCRIPCION;
    }

    public void setCCDS_DESCRIPCION(String CCDS_DESCRIPCION) {
        this.CCDS_DESCRIPCION = CCDS_DESCRIPCION;
    }

    public String getCLAB_DESCRIPCION() {
        return CLAB_DESCRIPCION;
    }

    public void setCLAB_DESCRIPCION(String CLAB_DESCRIPCION) {
        this.CLAB_DESCRIPCION = CLAB_DESCRIPCION;
    }

    public String getCSEC_DESCRIPCION() {
        return CSEC_DESCRIPCION;
    }

    public void setCSEC_DESCRIPCION(String CSEC_DESCRIPCION) {
        this.CSEC_DESCRIPCION = CSEC_DESCRIPCION;
    }

    public String getCE_COMPARTEMUESTRA() {
        return CE_COMPARTEMUESTRA;
    }

    public void setCE_COMPARTEMUESTRA(String CE_COMPARTEMUESTRA) {
        this.CE_COMPARTEMUESTRA = CE_COMPARTEMUESTRA;
    }

    public String getDF_OBSERVACION() {
        return DF_OBSERVACION;
    }

    public void setDF_OBSERVACION(String DF_OBSERVACION) {
        this.DF_OBSERVACION = DF_OBSERVACION;
    }

    public String getCD_DESCRIPCION() {
        return CD_DESCRIPCION;
    }

    public void setCD_DESCRIPCION(String CD_DESCRIPCION) {
        this.CD_DESCRIPCION = CD_DESCRIPCION;
    }

    public String getCDERIV_DESCRIPCION() {
        return CDERIV_DESCRIPCION;
    }

    public void setCDERIV_DESCRIPCION(String CDERIV_DESCRIPCION) {
        this.CDERIV_DESCRIPCION = CDERIV_DESCRIPCION;
    }

    public BigDecimal getCDERIV_IDDERIVADOR() {
        return CDERIV_IDDERIVADOR;
    }

    public void setCDERIV_IDDERIVADOR(BigDecimal CDERIV_IDDERIVADOR) {
        this.CDERIV_IDDERIVADOR = CDERIV_IDDERIVADOR;
    }

    public BigDecimal getCE_IDEXAMEN() {
        return CE_IDEXAMEN;
    }

    public void setCE_IDEXAMEN(BigDecimal CE_IDEXAMEN) {
        this.CE_IDEXAMEN = CE_IDEXAMEN;
    }

    public BigDecimal getDFET_IDMUESTRA() {
        return DFET_IDMUESTRA;
    }

    public void setDFET_IDMUESTRA(BigDecimal DFET_IDMUESTRA) {
        this.DFET_IDMUESTRA = DFET_IDMUESTRA;
    }

    public BigDecimal getCCDS_IDCENTRODESALUD() {
        return CCDS_IDCENTRODESALUD;
    }

    public void setCCDS_IDCENTRODESALUD(BigDecimal CCDS_IDCENTRODESALUD) {
        this.CCDS_IDCENTRODESALUD = CCDS_IDCENTRODESALUD;
    }

    public BigDecimal getCLAB_IDLABORATORIO() {
        return CLAB_IDLABORATORIO;
    }

    public void setCLAB_IDLABORATORIO(BigDecimal CLAB_IDLABORATORIO) {
        this.CLAB_IDLABORATORIO = CLAB_IDLABORATORIO;
    }

    public BigDecimal getCSEC_IDSECCION() {
        return CSEC_IDSECCION;
    }

    public void setCSEC_IDSECCION(BigDecimal CSEC_IDSECCION) {
        this.CSEC_IDSECCION = CSEC_IDSECCION;
    }

    public BigDecimal getCMUE_IDTIPOMUESTRA() {
        return CMUE_IDTIPOMUESTRA;
    }

    public void setCMUE_IDTIPOMUESTRA(BigDecimal CMUE_IDTIPOMUESTRA) {
        this.CMUE_IDTIPOMUESTRA = CMUE_IDTIPOMUESTRA;
    }

    public BigDecimal getCMUE_IDGRUPOMUESTRA() {
        return CMUE_IDGRUPOMUESTRA;
    }

    public void setCMUE_IDGRUPOMUESTRA(BigDecimal CMUE_IDGRUPOMUESTRA) {
        this.CMUE_IDGRUPOMUESTRA = CMUE_IDGRUPOMUESTRA;
    }

    public String getCE_TIENEGRUPOMUESTRA() {
        return CE_TIENEGRUPOMUESTRA;
    }

    public void setCE_TIENEGRUPOMUESTRA(String CE_TIENEGRUPOMUESTRA) {
        this.CE_TIENEGRUPOMUESTRA = CE_TIENEGRUPOMUESTRA;
    }

    public String getDFE_EXAMENANULADO() {
        return DFE_EXAMENANULADO;
    }

    public void setDFE_EXAMENANULADO(String DFE_EXAMENANULADO) {
        this.DFE_EXAMENANULADO = DFE_EXAMENANULADO;
    }
    
}
