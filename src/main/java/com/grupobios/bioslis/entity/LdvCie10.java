package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/**
 * LdvCie10 generated by hbm2java
 */
public class LdvCie10  implements java.io.Serializable {


     private String ldvcieCodigocie10;
     private String ldvcieDescripcion;
     @JsonIgnore
     private Set cfgDiagnosticoses = new HashSet(0);

    public LdvCie10() {
    }

	
    public LdvCie10(String ldvcieCodigocie10) {
        this.ldvcieCodigocie10 = ldvcieCodigocie10;
    }
    public LdvCie10(String ldvcieCodigocie10, String ldvcieDescripcion, Set cfgDiagnosticoses) {
       this.ldvcieCodigocie10 = ldvcieCodigocie10;
       this.ldvcieDescripcion = ldvcieDescripcion;
       this.cfgDiagnosticoses = cfgDiagnosticoses;
    }
   
    public String getLdvcieCodigocie10() {
        return this.ldvcieCodigocie10;
    }
    
    public void setLdvcieCodigocie10(String ldvcieCodigocie10) {
        this.ldvcieCodigocie10 = ldvcieCodigocie10;
    }
    public String getLdvcieDescripcion() {
        return this.ldvcieDescripcion;
    }
    
    public void setLdvcieDescripcion(String ldvcieDescripcion) {
        this.ldvcieDescripcion = ldvcieDescripcion;
    }
    public Set getCfgDiagnosticoses() {
        return this.cfgDiagnosticoses;
    }
    
    public void setCfgDiagnosticoses(Set cfgDiagnosticoses) {
        this.cfgDiagnosticoses = cfgDiagnosticoses;
    }




}


