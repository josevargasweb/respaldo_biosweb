/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.entity;

import java.io.Serializable;

/**
 *
 * @author Marco Caracciolo
 */
public class CfgUsuariosModulosId implements Serializable {
    
    private long cumodIdusuario;
    private short cumodIdbioslismodulo;

    public long getCumodIdusuario() {
        return cumodIdusuario;
    }

    public void setCumodIdusuario(long cumodIdusuario) {
        this.cumodIdusuario = cumodIdusuario;
    }

    public short getCumodIdbioslismodulo() {
        return cumodIdbioslismodulo;
    }

    public void setCumodIdbioslismodulo(short cumodIdbioslismodulo) {
        this.cumodIdbioslismodulo = cumodIdbioslismodulo;
    }
    
}
