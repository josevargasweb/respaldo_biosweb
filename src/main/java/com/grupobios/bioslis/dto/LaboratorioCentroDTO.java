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
public class LaboratorioCentroDTO {
    
    private BigDecimal CLAB_IDLABORATORIO;
    private BigDecimal CLAB_IDCENTRODESALUD;
    private String CLAB_DESCRIPCION;
    private String CCDS_DESCRIPCION;

    public BigDecimal getCLAB_IDLABORATORIO() {
        return CLAB_IDLABORATORIO;
    }

    public void setCLAB_IDLABORATORIO(BigDecimal CLAB_IDLABORATORIO) {
        this.CLAB_IDLABORATORIO = CLAB_IDLABORATORIO;
    }

    public BigDecimal getCLAB_IDCENTRODESALUD() {
        return CLAB_IDCENTRODESALUD;
    }

    public void setCLAB_IDCENTRODESALUD(BigDecimal CLAB_IDCENTRODESALUD) {
        this.CLAB_IDCENTRODESALUD = CLAB_IDCENTRODESALUD;
    }

    public String getCLAB_DESCRIPCION() {
        return CLAB_DESCRIPCION;
    }

    public void setCLAB_DESCRIPCION(String CLAB_DESCRIPCION) {
        this.CLAB_DESCRIPCION = CLAB_DESCRIPCION;
    }

    public String getCCDS_DESCRIPCION() {
        return CCDS_DESCRIPCION;
    }

    public void setCCDS_DESCRIPCION(String CCDS_DESCRIPCION) {
        this.CCDS_DESCRIPCION = CCDS_DESCRIPCION;
    }
    
}
