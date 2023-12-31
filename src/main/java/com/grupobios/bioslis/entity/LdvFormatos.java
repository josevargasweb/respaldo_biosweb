package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/**
 * LdvFormatos generated by hbm2java
 */
public class LdvFormatos  implements java.io.Serializable {


     private byte ldvfIdformato;
     private String ldvfDescripcion;
     @JsonIgnore
     private Set cfgExameneses = new HashSet(0);

    public LdvFormatos() {
    }

	
    public LdvFormatos(byte ldvfIdformato) {
        this.ldvfIdformato = ldvfIdformato;
    }
    public LdvFormatos(byte ldvfIdformato, String ldvfDescripcion, Set cfgExameneses) {
       this.ldvfIdformato = ldvfIdformato;
       this.ldvfDescripcion = ldvfDescripcion;
       this.cfgExameneses = cfgExameneses;
    }
   
    public byte getLdvfIdformato() {
        return this.ldvfIdformato;
    }
    
    public void setLdvfIdformato(byte ldvfIdformato) {
        this.ldvfIdformato = ldvfIdformato;
    }
    public String getLdvfDescripcion() {
        return this.ldvfDescripcion;
    }
    
    public void setLdvfDescripcion(String ldvfDescripcion) {
        this.ldvfDescripcion = ldvfDescripcion;
    }
    public Set getCfgExameneses() {
        return this.cfgExameneses;
    }
    
    public void setCfgExameneses(Set cfgExameneses) {
        this.cfgExameneses = cfgExameneses;
    }




}


