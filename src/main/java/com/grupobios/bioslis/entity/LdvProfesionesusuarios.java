package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/**
 * LdvProfesionesusuarios generated by hbm2java
 */
public class LdvProfesionesusuarios  implements java.io.Serializable {


     private byte ldvpuIdprofesion;
     private String ldvpuDescripcion;
     @JsonIgnore
     private Set datosUsuarioses = new HashSet(0);

    public LdvProfesionesusuarios() {
    }

	
    public LdvProfesionesusuarios(byte ldvpuIdprofesion) {
        this.ldvpuIdprofesion = ldvpuIdprofesion;
    }
    public LdvProfesionesusuarios(byte ldvpuIdprofesion, String ldvpuDescripcion, Set datosUsuarioses) {
       this.ldvpuIdprofesion = ldvpuIdprofesion;
       this.ldvpuDescripcion = ldvpuDescripcion;
       this.datosUsuarioses = datosUsuarioses;
    }
   
    public byte getLdvpuIdprofesion() {
        return this.ldvpuIdprofesion;
    }
    
    public void setLdvpuIdprofesion(byte ldvpuIdprofesion) {
        this.ldvpuIdprofesion = ldvpuIdprofesion;
    }
    public String getLdvpuDescripcion() {
        return this.ldvpuDescripcion;
    }
    
    public void setLdvpuDescripcion(String ldvpuDescripcion) {
        this.ldvpuDescripcion = ldvpuDescripcion;
    }
    public Set getDatosUsuarioses() {
        return this.datosUsuarioses;
    }
    
    public void setDatosUsuarioses(Set datosUsuarioses) {
        this.datosUsuarioses = datosUsuarioses;
    }




}

