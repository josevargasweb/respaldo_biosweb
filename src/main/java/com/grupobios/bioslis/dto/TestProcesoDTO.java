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
public class TestProcesoDTO {
    
    private BigDecimal CT_IDTEST;
    private String CT_CODIGO;
    private String CT_ABREVIADO;
    private String CT_DESCRIPCION;
    private String CT_ACTIVO;
    private BigDecimal CT_DECIMALES;
    private BigDecimal CSEC_IDSECCION;
    private String CSEC_DESCRIPCION;
    private BigDecimal CMUE_IDTIPOMUESTRA;
    private String CMUE_PREFIJOTIPOMUESTRA;
    private List<ExamenThinDTO> listExamenesTest;

    public BigDecimal getCT_IDTEST() {
        return CT_IDTEST;
    }

    public void setCT_IDTEST(BigDecimal CT_IDTEST) {
        this.CT_IDTEST = CT_IDTEST;
    }

    public String getCT_CODIGO() {
        return CT_CODIGO;
    }

    public void setCT_CODIGO(String CT_CODIGO) {
        this.CT_CODIGO = CT_CODIGO;
    }

    public String getCT_ABREVIADO() {
        return CT_ABREVIADO;
    }

    public void setCT_ABREVIADO(String CT_ABREVIADO) {
        this.CT_ABREVIADO = CT_ABREVIADO;
    }

    public String getCT_DESCRIPCION() {
        return CT_DESCRIPCION;
    }

    public void setCT_DESCRIPCION(String CT_DESCRIPCION) {
        this.CT_DESCRIPCION = CT_DESCRIPCION;
    }

    public String getCT_ACTIVO() {
        return CT_ACTIVO;
    }

    public void setCT_ACTIVO(String CT_ACTIVO) {
        this.CT_ACTIVO = CT_ACTIVO;
    }

    public BigDecimal getCT_DECIMALES() {
        return CT_DECIMALES;
    }

    public void setCT_DECIMALES(BigDecimal CT_DECIMALES) {
        this.CT_DECIMALES = CT_DECIMALES;
    }

    public BigDecimal getCSEC_IDSECCION() {
        return CSEC_IDSECCION;
    }

    public void setCSEC_IDSECCION(BigDecimal CSEC_IDSECCION) {
        this.CSEC_IDSECCION = CSEC_IDSECCION;
    }

    public String getCSEC_DESCRIPCION() {
        return CSEC_DESCRIPCION;
    }

    public void setCSEC_DESCRIPCION(String CSEC_DESCRIPCION) {
        this.CSEC_DESCRIPCION = CSEC_DESCRIPCION;
    }

    public BigDecimal getCMUE_IDTIPOMUESTRA() {
        return CMUE_IDTIPOMUESTRA;
    }

    public void setCMUE_IDTIPOMUESTRA(BigDecimal CMUE_IDTIPOMUESTRA) {
        this.CMUE_IDTIPOMUESTRA = CMUE_IDTIPOMUESTRA;
    }

    public String getCMUE_PREFIJOTIPOMUESTRA() {
        return CMUE_PREFIJOTIPOMUESTRA;
    }

    public void setCMUE_PREFIJOTIPOMUESTRA(String CMUE_PREFIJOTIPOMUESTRA) {
        this.CMUE_PREFIJOTIPOMUESTRA = CMUE_PREFIJOTIPOMUESTRA;
    }

    public List<ExamenThinDTO> getListExamenesTest() {
        return listExamenesTest;
    }

    public void setListExamenesTest(List<ExamenThinDTO> listExamenesTest) {
        this.listExamenesTest = listExamenesTest;
    }
    
}
