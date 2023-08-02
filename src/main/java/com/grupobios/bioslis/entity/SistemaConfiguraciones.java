/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Marco Caracciolo
 */
public class SistemaConfiguraciones implements java.io.Serializable {
    
    private byte scIdcentrodesalud;
    private short scPwdiascaducapassword;
    private short scPwminlargocaracteres;
    private String scPwcambiarprimeringreso;
    @JsonIgnore
    private CfgCentrosdesalud cfgCentrosdesalud;

    public byte getScIdcentrodesalud() {
        return scIdcentrodesalud;
    }

    public void setScIdcentrodesalud(byte scIdcentrodesalud) {
        this.scIdcentrodesalud = scIdcentrodesalud;
    }

    public short getScPwdiascaducapassword() {
        return scPwdiascaducapassword;
    }

    public void setScPwdiascaducapassword(short scPwdiascaducapassword) {
        this.scPwdiascaducapassword = scPwdiascaducapassword;
    }

    public short getScPwminlargocaracteres() {
        return scPwminlargocaracteres;
    }

    public void setScPwminlargocaracteres(short scPwminlargocaracteres) {
        this.scPwminlargocaracteres = scPwminlargocaracteres;
    }

    public String getScPwcambiarprimeringreso() {
        return scPwcambiarprimeringreso;
    }

    public void setScPwcambiarprimeringreso(String scPwcambiarprimeringreso) {
        this.scPwcambiarprimeringreso = scPwcambiarprimeringreso;
    }

    public CfgCentrosdesalud getCfgCentrosdesalud() {
        return cfgCentrosdesalud;
    }

    public void setCfgCentrosdesalud(CfgCentrosdesalud cfgCentrosdesalud) {
        this.cfgCentrosdesalud = cfgCentrosdesalud;
    }
    
}
