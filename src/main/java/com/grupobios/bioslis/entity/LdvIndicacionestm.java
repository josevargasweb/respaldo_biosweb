package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/**
 * LdvIndicacionestm generated by hbm2java
 */
public class LdvIndicacionestm  implements java.io.Serializable {


     private short ldvitmIdindicacionestm;
     private String ldvitmDescripcionindicacion;
     @JsonIgnore
     private Set cfgExamenesindicacionestms = new HashSet(0);

    public LdvIndicacionestm() {
    }

	
    public LdvIndicacionestm(short ldvitmIdindicacionestm) {
        this.ldvitmIdindicacionestm = ldvitmIdindicacionestm;
    }
    public LdvIndicacionestm(short ldvitmIdindicacionestm, String ldvitmDescripcionindicacion, Set cfgExamenesindicacionestms) {
       this.ldvitmIdindicacionestm = ldvitmIdindicacionestm;
       this.ldvitmDescripcionindicacion = ldvitmDescripcionindicacion;
       this.cfgExamenesindicacionestms = cfgExamenesindicacionestms;
    }
   
    public short getLdvitmIdindicacionestm() {
        return this.ldvitmIdindicacionestm;
    }
    
    public void setLdvitmIdindicacionestm(short ldvitmIdindicacionestm) {
        this.ldvitmIdindicacionestm = ldvitmIdindicacionestm;
    }
    public String getLdvitmDescripcionindicacion() {
        return this.ldvitmDescripcionindicacion;
    }
    
    public void setLdvitmDescripcionindicacion(String ldvitmDescripcionindicacion) {
        this.ldvitmDescripcionindicacion = ldvitmDescripcionindicacion;
    }
    public Set getCfgExamenesindicacionestms() {
        return this.cfgExamenesindicacionestms;
    }
    
    public void setCfgExamenesindicacionestms(Set cfgExamenesindicacionestms) {
        this.cfgExamenesindicacionestms = cfgExamenesindicacionestms;
    }




}


