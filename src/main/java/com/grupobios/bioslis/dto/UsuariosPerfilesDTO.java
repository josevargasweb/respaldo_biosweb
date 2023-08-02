package com.grupobios.bioslis.dto;


import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.CfgNivelesaccesos;
import com.grupobios.bioslis.entity.CfgPerfilesusuarios;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LdvCargosusuarios;
import com.grupobios.bioslis.entity.LdvProfesionesusuarios;
import com.grupobios.bioslis.entity.LdvSexo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marco Caracciolo
 */
public class UsuariosPerfilesDTO {
    
    private DatosUsuarios datosUsuarios;
    private CfgPerfilesusuarios cfgPerfilesusuarios;
    private LdvCargosusuarios ldvCargosusuarios;
    private LdvSexo ldvSexo;
    private CfgCentrosdesalud cfgCentrosdesalud;
    private LdvProfesionesusuarios ldvProfesionesusuarios;
    private String fotoUsuario;
    private String firmaUsuario;
    private String password;

    public DatosUsuarios getDatosUsuarios() {
        return datosUsuarios;
    }

    public void setDatosUsuarios(DatosUsuarios datosUsuarios) {
        this.datosUsuarios = datosUsuarios;
    }

    public CfgPerfilesusuarios getCfgPerfilesusuarios() {
        return cfgPerfilesusuarios;
    }

    public void setCfgPerfilesusuarios(CfgPerfilesusuarios cfgPerfilesusuarios) {
        this.cfgPerfilesusuarios = cfgPerfilesusuarios;
    }

    public LdvCargosusuarios getLdvCargosusuarios() {
        return ldvCargosusuarios;
    }

    public void setLdvCargosusuarios(LdvCargosusuarios ldvCargosusuarios) {
        this.ldvCargosusuarios = ldvCargosusuarios;
    }

    public LdvSexo getLdvSexo() {
        return ldvSexo;
    }

    public void setLdvSexo(LdvSexo ldvSexo) {
        this.ldvSexo = ldvSexo;
    }

    public CfgCentrosdesalud getCfgCentrosdesalud() {
        return cfgCentrosdesalud;
    }

    public void setCfgCentrosdesalud(CfgCentrosdesalud cfgCentrosdesalud) {
        this.cfgCentrosdesalud = cfgCentrosdesalud;
    }

    public LdvProfesionesusuarios getLdvProfesionesusuarios() {
        return ldvProfesionesusuarios;
    }

    public void setLdvProfesionesusuarios(LdvProfesionesusuarios ldvProfesionesusuarios) {
        this.ldvProfesionesusuarios = ldvProfesionesusuarios;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getFirmaUsuario() {
        return firmaUsuario;
    }

    public void setFirmaUsuario(String firmaUsuario) {
        this.firmaUsuario = firmaUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsuariosPerfilesDTO{" + "datosUsuarios=" + datosUsuarios + ", cfgPerfilesusuarios=" + cfgPerfilesusuarios + ", ldvCargosusuarios=" + ldvCargosusuarios + ", ldvSexo=" + ldvSexo + ", cfgCentrosdesalud=" + cfgCentrosdesalud + ", fotoUsuario=" + fotoUsuario + ", firmaUsuario=" + firmaUsuario + ", password=" + password + '}';
    }
    
}
