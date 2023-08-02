/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupobios.bioslis.entity;

import java.io.Serializable;


public class CfgCargosmodulosblId implements Serializable {
     
    private short ccmIdcargo;
    private short ccmIdbioslismodulo;

    public short getCcmIdcargo() {
        return ccmIdcargo;
    }

    public void setCcmIdcargo(short ccmIdcargo) {
        this.ccmIdcargo = ccmIdcargo;
    }

    public short getCcmIdbioslismodulo() {
        return ccmIdbioslismodulo;
    }

    public void setCcmIdbioslismodulo(short ccmIdbioslismodulo) {
        this.ccmIdbioslismodulo = ccmIdbioslismodulo;
    }
    
}
