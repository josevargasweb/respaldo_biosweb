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
public class TMClickDTO {
    private BigDecimal NORDEN;
    private BigDecimal IDUSUARIO;
    private String OCUPADO;
    private String NOMUSUARIO;

    public BigDecimal getNORDEN() {
        return NORDEN;
    }

    public void setNORDEN(BigDecimal NORDEN) {
        this.NORDEN = NORDEN;
    }

    public BigDecimal getIDUSUARIO() {
        return IDUSUARIO;
    }

    public void setIDUSUARIO(BigDecimal IDUSUARIO) {
        this.IDUSUARIO = IDUSUARIO;
    }

    public String getOCUPADO() {
        return OCUPADO;
    }

    public void setOCUPADO(String OCUPADO) {
        this.OCUPADO = OCUPADO;
    }

    public String getNOMUSUARIO() {
        return NOMUSUARIO;
    }

    public void setNOMUSUARIO(String NOMUSUARIO) {
        this.NOMUSUARIO = NOMUSUARIO;
    }
    
}
