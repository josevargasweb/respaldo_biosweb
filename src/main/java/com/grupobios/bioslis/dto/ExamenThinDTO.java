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
public class ExamenThinDTO {
    private BigDecimal CE_IDEXAMEN;
    private String CE_ABREVIADO;

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
    
}
