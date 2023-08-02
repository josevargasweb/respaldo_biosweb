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
public class PatologiaDTO {
    
    private BigDecimal LDVPAT_IDPATOLOGIA;
    private String LDVPAT_DESCRIPCION;
    private String DPP_OBSERVACION;
    
    public BigDecimal getLDVPAT_IDPATOLOGIA() {
        return LDVPAT_IDPATOLOGIA;
    }

    public void setLDVPAT_IDPATOLOGIA(BigDecimal LDVPAT_IDPATOLOGIA) {
        this.LDVPAT_IDPATOLOGIA = LDVPAT_IDPATOLOGIA;
    }

    public String getLDVPAT_DESCRIPCION() {
        return LDVPAT_DESCRIPCION;
    }

    public void setLDVPAT_DESCRIPCION(String LDVPAT_DESCRIPCION) {
        this.LDVPAT_DESCRIPCION = LDVPAT_DESCRIPCION;
    }

    public String getDPP_OBSERVACION() {
        return DPP_OBSERVACION;
    }

    public void setDPP_OBSERVACION(String dPP_OBSERVACION) {
        DPP_OBSERVACION = dPP_OBSERVACION;
    }

    @Override
    public String toString() {
        return "PatologiaDTO [LDVPAT_IDPATOLOGIA=" + LDVPAT_IDPATOLOGIA + ", LDVPAT_DESCRIPCION=" + LDVPAT_DESCRIPCION
                + ", DPP_OBSERVACION=" + DPP_OBSERVACION + "]";
    }

    
  
    
    
}
