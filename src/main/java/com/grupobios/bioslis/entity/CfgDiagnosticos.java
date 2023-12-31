package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashSet;
import java.util.Set;

/**
 * CfgDiagnosticos generated by hbm2java
 */
public class CfgDiagnosticos  implements java.io.Serializable {


     @JsonInclude(value = JsonInclude.Include.NON_NULL)
     private short cdIddiagnostico;
     //@JsonIgnore
     private LdvCie10 ldvCie10;
     private String cdDescripcion;
     private short cdSort;
     private String cdActivo;
     private String cdHost;
     @JsonIgnore
     private Set datosFichases = new HashSet(0);

    public CfgDiagnosticos() {
    }

	
    public CfgDiagnosticos(short cdIddiagnostico, short cdSort) {
        this.cdIddiagnostico = cdIddiagnostico;
        this.cdSort = cdSort;
    }
    public CfgDiagnosticos(short cdIddiagnostico, LdvCie10 ldvCie10, String cdDescripcion, short cdSort, String cdActivo, String cdHost, Set datosFichases) {
       this.cdIddiagnostico = cdIddiagnostico;
       this.ldvCie10 = ldvCie10;
       this.cdDescripcion = cdDescripcion;
       this.cdSort = cdSort;
       this.cdActivo = cdActivo;
       this.cdHost = cdHost;
       this.datosFichases = datosFichases;
    }
   
    public short getCdIddiagnostico() {
        return this.cdIddiagnostico;
    }
    
    public void setCdIddiagnostico(short cdIddiagnostico) {
        this.cdIddiagnostico = cdIddiagnostico;
    }
    public LdvCie10 getLdvCie10() {
        return this.ldvCie10;
    }
    
    public void setLdvCie10(LdvCie10 ldvCie10) {
        this.ldvCie10 = ldvCie10;
    }
    public String getCdDescripcion() {
        return this.cdDescripcion;
    }
    
    public void setCdDescripcion(String cdDescripcion) {
        this.cdDescripcion = cdDescripcion;
    }
    public short getCdSort() {
        return this.cdSort;
    }
    
    public void setCdSort(short cdSort) {
        this.cdSort = cdSort;
    }
    public String getCdActivo() {
        return this.cdActivo;
    }
    
    public void setCdActivo(String cdActivo) {
        this.cdActivo = cdActivo;
    }
    public String getCdHost() {
        return this.cdHost;
    }
    
    public void setCdHost(String cdHost) {
        this.cdHost = cdHost;
    }
    public Set getDatosFichases() {
        return this.datosFichases;
    }
    
    public void setDatosFichases(Set datosFichases) {
        this.datosFichases = datosFichases;
    }




}


