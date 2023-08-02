/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

/**
 *
 * @author eric.nicholls
 */
public class ResultadoExamenOrdenDTO {
  
    
    private String CE_CODIGOEXAMEN;
    private String CE_ABREVIADO;
    private BigDecimal CT_IDTIPOMUESTRA;
    private BigDecimal DFE_NORDEN;
    private BigDecimal DFE_IDEXAMEN;
    private String SDFE_FECHAEMAIL ;
    private String SDFE_FECHAIMPRESION ;
    private BigDecimal DFE_IDUSUARIOEMAIL;
    private BigDecimal DFE_IDUSUARIOIMPRIME; 
    private String DFE_CODIGOESTADOEXAMEN;
    private BigDecimal DFE_CANTIDAD;
    private String DP_NOMBRES;
    private String DP_PRIMERAPELLIDO;
    private String DP_SEGUNDOAPELLIDO; 
    private String DP_EMAIL;
    private String CS_DESCRIPCION; 
    private String CS_EMAIL   ;
    private String CEE_DESCRIPCIONESTADOEXAMEN ;
    private String SDFE_FECHARETIRAEXAMEN;
   
    public String getSDFE_FECHAEMAIL() {
        return SDFE_FECHAEMAIL;
    }

    public void setSDFE_FECHAEMAIL(String SDFE_FECHAEMAIL) {
        this.SDFE_FECHAEMAIL = SDFE_FECHAEMAIL;
    }

    public String getSDFE_FECHAIMPRESION() {
        return SDFE_FECHAIMPRESION;
    }

    public void setSDFE_FECHAIMPRESION(String SDFE_FECHAIMPRESION) {
        this.SDFE_FECHAIMPRESION = SDFE_FECHAIMPRESION;
    }

    public BigDecimal getDFE_IDUSUARIOEMAIL() {
        return DFE_IDUSUARIOEMAIL;
    }

    public void setDFE_IDUSUARIOEMAIL(BigDecimal DFE_IDUSUARIOEMAIL) {
        this.DFE_IDUSUARIOEMAIL = DFE_IDUSUARIOEMAIL;
    }

    public BigDecimal getDFE_IDUSUARIOIMPRIME() {
        return DFE_IDUSUARIOIMPRIME;
    }

    public void setDFE_IDUSUARIOIMPRIME(BigDecimal DFE_IDUSUARIOIMPRIME) {
        this.DFE_IDUSUARIOIMPRIME = DFE_IDUSUARIOIMPRIME;
    }

    public BigDecimal getDFE_NORDEN() {
        return DFE_NORDEN;
    }

    public void setDFE_NORDEN(BigDecimal DFE_NORDEN) {
        this.DFE_NORDEN = DFE_NORDEN;
    }

    public BigDecimal getDFE_IDEXAMEN() {
        return DFE_IDEXAMEN;
    }

    public void setDFE_IDEXAMEN(BigDecimal DFE_IDEXAMEN) {
        this.DFE_IDEXAMEN = DFE_IDEXAMEN;
    }

    public String getDFE_CODIGOESTADOEXAMEN() {
        return DFE_CODIGOESTADOEXAMEN;
    }

    public void setDFE_CODIGOESTADOEXAMEN(String DFE_CODIGOESTADOEXAMEN) {
        this.DFE_CODIGOESTADOEXAMEN = DFE_CODIGOESTADOEXAMEN;
    }

    public BigDecimal getDFE_CANTIDAD() {
        return DFE_CANTIDAD;
    }

    public void setDFE_CANTIDAD(BigDecimal DFE_CANTIDAD) {
        this.DFE_CANTIDAD = DFE_CANTIDAD;
    }

    public String getCE_CODIGOEXAMEN() {
        return CE_CODIGOEXAMEN;
    }

    public void setCE_CODIGOEXAMEN(String CE_CODIGOEXAMEN) {
        this.CE_CODIGOEXAMEN = CE_CODIGOEXAMEN;
    }

    public String getCE_ABREVIADO() {
        return CE_ABREVIADO;
    }

    public void setCE_ABREVIADO(String CE_ABREVIADO) {
        this.CE_ABREVIADO = CE_ABREVIADO;
    }

    public BigDecimal getCT_IDTIPOMUESTRA() {
        return CT_IDTIPOMUESTRA;
    }

    public void setCT_IDTIPOMUESTRA(BigDecimal CT_IDTIPOMUESTRA) {
        this.CT_IDTIPOMUESTRA = CT_IDTIPOMUESTRA;
    }

    public String getDP_NOMBRES() {
        return DP_NOMBRES;
    }

    public void setDP_NOMBRES(String DP_NOMBRES) {
        this.DP_NOMBRES = DP_NOMBRES;
    }

    public String getDP_PRIMERAPELLIDO() {
        return DP_PRIMERAPELLIDO;
    }

    public void setDP_PRIMERAPELLIDO(String DP_PRIMERAPELLIDO) {
        this.DP_PRIMERAPELLIDO = DP_PRIMERAPELLIDO;
    }

    public String getDP_SEGUNDOAPELLIDO() {
        return DP_SEGUNDOAPELLIDO;
    }

    public void setDP_SEGUNDOAPELLIDO(String DP_SEGUNDOAPELLIDO) {
        this.DP_SEGUNDOAPELLIDO = DP_SEGUNDOAPELLIDO;
    }

    public String getDP_EMAIL() {
        return DP_EMAIL;
    }

    public void setDP_EMAIL(String DP_EMAIL) {
        this.DP_EMAIL = DP_EMAIL;
    }

    public String getCS_DESCRIPCION() {
        return CS_DESCRIPCION;
    }

    public void setCS_DESCRIPCION(String CS_DESCRIPCION) {
        this.CS_DESCRIPCION = CS_DESCRIPCION;
    }

    public String getCS_EMAIL() {
        return CS_EMAIL;
    }

    public void setCS_EMAIL(String CS_EMAIL) {
        this.CS_EMAIL = CS_EMAIL;
    }

    public String getCEE_DESCRIPCIONESTADOEXAMEN() {
        return CEE_DESCRIPCIONESTADOEXAMEN;
    }

    public void setCEE_DESCRIPCIONESTADOEXAMEN(String CEE_DESCRIPCIONESTADOEXAMEN) {
        this.CEE_DESCRIPCIONESTADOEXAMEN = CEE_DESCRIPCIONESTADOEXAMEN;
    }

    public String getSDFE_FECHARETIRAEXAMEN() {
        return SDFE_FECHARETIRAEXAMEN;
    }

    public void setSDFE_FECHARETIRAEXAMEN(String SDFE_FECHARETIRAEXAMEN) {
        this.SDFE_FECHARETIRAEXAMEN = SDFE_FECHARETIRAEXAMEN;
    }

    
    
}
