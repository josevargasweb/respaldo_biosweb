/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupobios.bioslis.entity;

import java.io.Serializable;


public class CfgCargosmodulosbl implements Serializable {
     
    private CfgCargosmodulosblId id;
    private LdvCargosusuarios ldvCargosusuarios;

    public CfgCargosmodulosblId getId() {
        return id;
    }

    public void setId(CfgCargosmodulosblId id) {
        this.id = id;
    }

    public LdvCargosusuarios getLdvCargosusuarios() {
        return ldvCargosusuarios;
    }

    public void setLdvCargosusuarios(LdvCargosusuarios ldvCargosusuarios) {
        this.ldvCargosusuarios = ldvCargosusuarios;
    }
    
}
