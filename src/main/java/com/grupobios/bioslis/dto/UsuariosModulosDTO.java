/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.CfgBioslismodulos;
import com.grupobios.bioslis.entity.CfgUsuariosModulos;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class UsuariosModulosDTO {
    
    private DatosUsuarios datosUsuarios;
    private List<CfgBioslismodulos> listCfgBioslismodulos;

    public DatosUsuarios getDatosUsuarios() {
        return datosUsuarios;
    }

    public void setDatosUsuarios(DatosUsuarios datosUsuarios) {
        this.datosUsuarios = datosUsuarios;
    }

    public List<CfgBioslismodulos> getListCfgBioslismodulos() {
        return listCfgBioslismodulos;
    }

    public void setListCfgBioslismodulos(List<CfgBioslismodulos> listCfgBioslismodulos) {
        this.listCfgBioslismodulos = listCfgBioslismodulos;
    }
    
}
