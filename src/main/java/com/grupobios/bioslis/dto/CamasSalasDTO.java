/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.CfgCamassalas;
import com.grupobios.bioslis.entity.CfgSalasservicios;
import java.math.BigDecimal;

/**
 *
 * @author Marco Caracciolo
 */
public class CamasSalasDTO {
    private BigDecimal CCS_IDCAMASALA;
    private BigDecimal CCS_IDSALASERVICIO;
    private String CCS_CODIGOCAMA;
    private String CCS_DESCRIPCION;

    public BigDecimal getCCS_IDCAMASALA() {
        return CCS_IDCAMASALA;
    }

    public void setCCS_IDCAMASALA(BigDecimal CCS_IDCAMASALA) {
        this.CCS_IDCAMASALA = CCS_IDCAMASALA;
    }

    public BigDecimal getCCS_IDSALASERVICIO() {
        return CCS_IDSALASERVICIO;
    }

    public void setCCS_IDSALASERVICIO(BigDecimal CCS_IDSALASERVICIO) {
        this.CCS_IDSALASERVICIO = CCS_IDSALASERVICIO;
    }

    public String getCCS_CODIGOCAMA() {
        return CCS_CODIGOCAMA;
    }

    public void setCCS_CODIGOCAMA(String CCS_CODIGOCAMA) {
        this.CCS_CODIGOCAMA = CCS_CODIGOCAMA;
    }

    public String getCCS_DESCRIPCION() {
        return CCS_DESCRIPCION;
    }

    public void setCCS_DESCRIPCION(String CCS_DESCRIPCION) {
        this.CCS_DESCRIPCION = CCS_DESCRIPCION;
    }

    public CfgCamassalas transformToEntity(){
        CfgCamassalas camassalas = new CfgCamassalas();
        camassalas.setCcsIdcamasala(this.CCS_IDCAMASALA.intValue());
        camassalas.setCcsCodigocama(this.CCS_CODIGOCAMA);
        camassalas.setCcsDescripcion(this.CCS_DESCRIPCION);
        camassalas.setCfgSalasservicios(new CfgSalasservicios(this.CCS_IDSALASERVICIO.intValue()));
        return camassalas;
    }
}
