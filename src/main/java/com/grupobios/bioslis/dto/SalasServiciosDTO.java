/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.CfgSalasservicios;
import com.grupobios.bioslis.entity.CfgServicios;
import java.math.BigDecimal;

/**
 *
 * @author Marco Caracciolo
 */
public class SalasServiciosDTO {
    private BigDecimal CSS_IDSALASERVICIO;
    private String CSS_CODIGOSALA;
    private String CSS_DESCRIPCION;
    private BigDecimal CSS_IDSERVICIO;

    public BigDecimal getCSS_IDSALASERVICIO() {
        return CSS_IDSALASERVICIO;
    }

    public void setCSS_IDSALASERVICIO(BigDecimal CSS_IDSALASERVICIO) {
        this.CSS_IDSALASERVICIO = CSS_IDSALASERVICIO;
    }

    public String getCSS_CODIGOSALA() {
        return CSS_CODIGOSALA;
    }

    public void setCSS_CODIGOSALA(String CSS_CODIGOSALA) {
        this.CSS_CODIGOSALA = CSS_CODIGOSALA;
    }

    public String getCSS_DESCRIPCION() {
        return CSS_DESCRIPCION;
    }

    public void setCSS_DESCRIPCION(String CSS_DESCRIPCION) {
        this.CSS_DESCRIPCION = CSS_DESCRIPCION;
    }

    public BigDecimal getCSS_IDSERVICIO() {
        return CSS_IDSERVICIO;
    }

    public void setCSS_IDSERVICIO(BigDecimal CSS_IDSERVICIO) {
        this.CSS_IDSERVICIO = CSS_IDSERVICIO;
    }

    public CfgSalasservicios transformToEntity(){
        CfgSalasservicios salasservicios = new CfgSalasservicios();
        salasservicios.setCssIdsalaservicio(this.CSS_IDSALASERVICIO.intValue());
        salasservicios.setCssCodigosala(this.CSS_CODIGOSALA);
        salasservicios.setCssDescripcion(this.CSS_DESCRIPCION);
        salasservicios.setCfgServicios(new CfgServicios(this.CSS_IDSERVICIO.intValue()));
        return salasservicios;
    }
     
}
