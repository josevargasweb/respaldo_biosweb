package com.grupobios.bioslis.microbiologia.dto;

import java.math.BigDecimal;
import java.util.Date;

public class MicrobiologiaOrdenPacienteDTO {

    
    public BigDecimal DF_NORDEN;
    public Date DF_FECHAORDEN;
    public Date  DP_FNACIMIENTO;
    public String DP_NOMBRES;
    public String DP_PRIMERAPELLIDO;
    public String DP_SEGUNDOAPELLIDO;
    public String LDVS_DESCRIPCION;
    public String CCDS_DESCRIPCION;
    public String CTA_DESCRIPCION;
    public String CS_DESCRIPCION;
    public String CE_ESCULTIVO;
    public String CEE_CODIGOESTADOEXAMEN;
    public String CBET_BACESTADOTEST;
    
    public MicrobiologiaOrdenPacienteDTO() {

    }

    public BigDecimal getDF_NORDEN() {
        return DF_NORDEN;
    }

    public void setDF_NORDEN(BigDecimal dF_NORDEN) {
        DF_NORDEN = dF_NORDEN;
    }

    public Date getDF_FECHAORDEN() {
        return DF_FECHAORDEN;
    }

    public void setDF_FECHAORDEN(Date dF_FECHAORDEN) {
        DF_FECHAORDEN = dF_FECHAORDEN;
    }

    public Date getDP_FNACIMIENTO() {
        return DP_FNACIMIENTO;
    }

    public void setDP_FNACIMIENTO(Date dP_FNACIMIENTO) {
        DP_FNACIMIENTO = dP_FNACIMIENTO;
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

   
    public String getLDVS_DESCRIPCION() {
        return LDVS_DESCRIPCION;
    }

    public void setLDVS_DESCRIPCION(String lDVS_DESCRIPCION) {
        LDVS_DESCRIPCION = lDVS_DESCRIPCION;
    }

    public String getCCDS_DESCRIPCION() {
        return CCDS_DESCRIPCION;
    }

    public void setCCDS_DESCRIPCION(String cCDS_DESCRIPCION) {
        CCDS_DESCRIPCION = cCDS_DESCRIPCION;
    }

    public String getCTA_DESCRIPCION() {
        return CTA_DESCRIPCION;
    }

    public void setCTA_DESCRIPCION(String cTA_DESCRIPCION) {
        CTA_DESCRIPCION = cTA_DESCRIPCION;
    }

    public String getCS_DESCRIPCION() {
        return CS_DESCRIPCION;
    }

    public void setCS_DESCRIPCION(String cS_DESCRIPCION) {
        CS_DESCRIPCION = cS_DESCRIPCION;
    }

    public String getCE_ESCULTIVO() {
        return CE_ESCULTIVO;
    }

    public void setCE_ESCULTIVO(String cE_ESCULTIVO) {
        CE_ESCULTIVO = cE_ESCULTIVO;
    }

    public String getCEE_CODIGOESTADOEXAMEN() {
        return CEE_CODIGOESTADOEXAMEN;
    }

    public void setCEE_CODIGOESTADOEXAMEN(String cEE_CODIGOESTADOEXAMEN) {
        CEE_CODIGOESTADOEXAMEN = cEE_CODIGOESTADOEXAMEN;
    }

    public String getCBET_BACESTADOTEST() {
        return CBET_BACESTADOTEST;
    }

    public void setCBET_BACESTADOTEST(String cBET_BACESTADOTEST) {
        CBET_BACESTADOTEST = cBET_BACESTADOTEST;
    }

   
    
    
}
