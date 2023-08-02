/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.CfgMetodostest;

/**
 *
 * @author Marco Caracciolo
 */
public class MetodosDTO {
    private int cmetIdmetodo;
     private String cmetDescripcion;
     private String cmetActivo;
     private CfgMetodostest cfgMetodostest;

    public int getCmetIdmetodo() {
        return cmetIdmetodo;
    }

    public void setCmetIdmetodo(int cmetIdmetodo) {
        this.cmetIdmetodo = cmetIdmetodo;
    }

    public String getCmetDescripcion() {
        return cmetDescripcion;
    }

    public void setCmetDescripcion(String cmetDescripcion) {
        this.cmetDescripcion = cmetDescripcion;
    }

    public String getCmetActivo() {
        return cmetActivo;
    }

    public void setCmetActivo(String cmetActivo) {
        this.cmetActivo = cmetActivo;
    }

    public CfgMetodostest getCfgMetodostest() {
        return cfgMetodostest;
    }

    public void setCfgMetodostest(CfgMetodostest cfgMetodostest) {
        this.cfgMetodostest = cfgMetodostest;
    }
     
     
}
