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
public class CfgUsuariosModulos implements Serializable {
    
    private CfgUsuariosModulosId id;
    private DatosUsuarios datosUsuarios;

    public CfgUsuariosModulosId getId() {
        return id;
    }

    public void setId(CfgUsuariosModulosId id) {
        this.id = id;
    }

    public DatosUsuarios getDatosUsuarios() {
        return datosUsuarios;
    }

    public void setDatosUsuarios(DatosUsuarios datosUsuarios) {
        this.datosUsuarios = datosUsuarios;
    }
            
}
