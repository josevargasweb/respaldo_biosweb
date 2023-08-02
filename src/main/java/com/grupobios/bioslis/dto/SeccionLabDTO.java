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
public class SeccionLabDTO {
    
    private BigDecimal CSEC_IDSECCION;
    private BigDecimal CSEC_IDLABORATORIO;
    private String CSEC_DESCRIPCION;
    private String CLAB_DESCRIPCION;

    public BigDecimal getCSEC_IDSECCION() {
        return CSEC_IDSECCION;
    }

    public void setCSEC_IDSECCION(BigDecimal CSEC_IDSECCION) {
        this.CSEC_IDSECCION = CSEC_IDSECCION;
    }

    public BigDecimal getCSEC_IDLABORATORIO() {
        return CSEC_IDLABORATORIO;
    }

    public void setCSEC_IDLABORATORIO(BigDecimal CSEC_IDLABORATORIO) {
        this.CSEC_IDLABORATORIO = CSEC_IDLABORATORIO;
    }

    public String getCSEC_DESCRIPCION() {
        return CSEC_DESCRIPCION;
    }

    public void setCSEC_DESCRIPCION(String CSEC_DESCRIPCION) {
        this.CSEC_DESCRIPCION = CSEC_DESCRIPCION;
    }

    public String getCLAB_DESCRIPCION() {
        return CLAB_DESCRIPCION;
    }

    public void setCLAB_DESCRIPCION(String CLAB_DESCRIPCION) {
        this.CLAB_DESCRIPCION = CLAB_DESCRIPCION;
    }

}
