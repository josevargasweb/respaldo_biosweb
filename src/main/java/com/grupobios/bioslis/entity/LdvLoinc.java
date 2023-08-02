package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/**
 * LdvLoinc generated by hbm2java
 */
public class LdvLoinc  implements java.io.Serializable {


     private String ldvlCodigoloinc;
     private String ldvlDescripcion;
     @JsonIgnore
     private Set cfgExameneses = new HashSet(0);

    public LdvLoinc() {
    }

	
    public LdvLoinc(String ldvlCodigoloinc) {
        this.ldvlCodigoloinc = ldvlCodigoloinc;
    }
    public LdvLoinc(String ldvlCodigoloinc, String ldvlDescripcion, Set cfgExameneses) {
       this.ldvlCodigoloinc = ldvlCodigoloinc;
       this.ldvlDescripcion = ldvlDescripcion;
       this.cfgExameneses = cfgExameneses;
    }
   
    public String getLdvlCodigoloinc() {
        return this.ldvlCodigoloinc;
    }
    
    public void setLdvlCodigoloinc(String ldvlCodigoloinc) {
        this.ldvlCodigoloinc = ldvlCodigoloinc;
    }
    public String getLdvlDescripcion() {
        return this.ldvlDescripcion;
    }
    
    public void setLdvlDescripcion(String ldvlDescripcion) {
        this.ldvlDescripcion = ldvlDescripcion;
    }
    public Set getCfgExameneses() {
        return this.cfgExameneses;
    }
    
    public void setCfgExameneses(Set cfgExameneses) {
        this.cfgExameneses = cfgExameneses;
    }




}

