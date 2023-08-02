/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.CfgEnvases;

/**
 *
 * @author Marco Caracciolo
 */
public class EnvaseDTO {
    private CfgEnvases cfgEnvases;
    private String base64Img;

    public CfgEnvases getCfgEnvases() {
        return cfgEnvases;
    }

    public void setCfgEnvases(CfgEnvases cfgEnvases) {
        this.cfgEnvases = cfgEnvases;
    }

    public String getBase64Img() {
        return base64Img;
    }

    public void setBase64Img(String base64Img) {
        this.base64Img = base64Img;
    }
    
}
