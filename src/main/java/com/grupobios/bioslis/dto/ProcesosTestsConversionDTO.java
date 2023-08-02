/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.LdvOperacionesmath;
import com.grupobios.bioslis.entity.LdvTiposcondicion;
import com.grupobios.bioslis.entity.SigmaProcesostest;
import com.grupobios.bioslis.entity.SigmaProcesotestconversion;
import com.grupobios.bioslis.entity.SigmaProcesotestconversionId;

/**
 *
 * @author Marco Caracciolo
 */
public class ProcesosTestsConversionDTO {
    private String sptcValorentrada;
    private String sptcValorsalida;
    private String sptcCriticosn;
    private String sptcReprocesarsn;
    private String sptcBloquearsn;
    private String sptcFirmarsn;
    private String sptcCondicionalsn;
    private String sptcCalculosn;
    private Integer sptcValoroperando;
    private byte sptcNumerocondicion;
    private byte ldvtcondIdtipocondicion;
    private byte ldvomIdoperadormath;

    public String getSptcValorentrada() {
        return sptcValorentrada;
    }

    public void setSptcValorentrada(String sptcValorentrada) {
        this.sptcValorentrada = sptcValorentrada;
    }

    public String getSptcValorsalida() {
        return sptcValorsalida;
    }

    public void setSptcValorsalida(String sptcValorsalida) {
        this.sptcValorsalida = sptcValorsalida;
    }

    public String getSptcCriticosn() {
        return sptcCriticosn;
    }

    public void setSptcCriticosn(String sptcCriticosn) {
        this.sptcCriticosn = sptcCriticosn;
    }

    public String getSptcReprocesarsn() {
        return sptcReprocesarsn;
    }

    public void setSptcReprocesarsn(String sptcReprocesarsn) {
        this.sptcReprocesarsn = sptcReprocesarsn;
    }

    public String getSptcBloquearsn() {
        return sptcBloquearsn;
    }

    public void setSptcBloquearsn(String sptcBloquearsn) {
        this.sptcBloquearsn = sptcBloquearsn;
    }

    public String getSptcFirmarsn() {
        return sptcFirmarsn;
    }

    public void setSptcFirmarsn(String sptcFirmarsn) {
        this.sptcFirmarsn = sptcFirmarsn;
    }

    public String getSptcCondicionalsn() {
        return sptcCondicionalsn;
    }

    public void setSptcCondicionalsn(String sptcCondicionalsn) {
        this.sptcCondicionalsn = sptcCondicionalsn;
    }

    public String getSptcCalculosn() {
        return sptcCalculosn;
    }

    public void setSptcCalculosn(String sptcCalculosn) {
        this.sptcCalculosn = sptcCalculosn;
    }

    public Integer getSptcValoroperando() {
        return sptcValoroperando;
    }

    public void setSptcValoroperando(Integer sptcValoroperando) {
        this.sptcValoroperando = sptcValoroperando;
    }

    

    public byte getSptcNumerocondicion() {
        return sptcNumerocondicion;
    }

    public void setSptcNumerocondicion(byte sptcNumerocondicion) {
        this.sptcNumerocondicion = sptcNumerocondicion;
    }

    public byte getLdvtcondIdtipocondicion() {
        return ldvtcondIdtipocondicion;
    }

    public void setLdvtcondIdtipocondicion(byte ldvtcondIdtipocondicion) {
        this.ldvtcondIdtipocondicion = ldvtcondIdtipocondicion;
    }

    public byte getLdvomIdoperadormath() {
        return ldvomIdoperadormath;
    }

    public void setLdvomIdoperadormath(byte ldvomIdoperadormath) {
        this.ldvomIdoperadormath = ldvomIdoperadormath;
    }
    
    public SigmaProcesotestconversion toSigmaProcesotestconversion(SigmaProcesotestconversionId sptcId,
            SigmaProcesostest sigmaProcesostest, LdvTiposcondicion ldvTiposcondicion, LdvOperacionesmath ldvOperacionesmath){
        
        SigmaProcesotestconversion target = new SigmaProcesotestconversion();
        target.setSptcBloquearsn(this.sptcBloquearsn);
        target.setSptcCalculosn(this.sptcCalculosn);
        target.setSptcCondicionalsn(this.sptcCondicionalsn);
        target.setSptcCriticosn(this.sptcCriticosn);
        target.setSptcFirmarsn(this.sptcFirmarsn);
        target.setSptcReprocesarsn(this.sptcReprocesarsn);
        target.setSptcValorentrada(this.sptcValorentrada);
        target.setSptcValorentrada(this.sptcValorentrada);
        target.setSptcValoroperando(this.sptcValoroperando);
        target.setSptcValorsalida(this.sptcValorsalida);
        target.setId(sptcId);
        target.setSigmaProcesostest(sigmaProcesostest);
        target.setLdvTiposcondicion(ldvTiposcondicion);
        target.setLdvOperacionesmath(ldvOperacionesmath);
        return target;
    }
    
}
