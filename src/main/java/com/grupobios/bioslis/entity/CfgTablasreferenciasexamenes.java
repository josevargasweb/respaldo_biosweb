package com.grupobios.bioslis.entity;
// Generated 13-10-2022 16:32:08 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;




/**
 * CfgTablasreferenciasexamenes generated by hbm2java
 */
public class CfgTablasreferenciasexamenes  implements java.io.Serializable {


     private CfgTablasreferenciasexamenesId id;
     @JsonIgnore
     private CfgExamenestablasanexas cfgExamenestablasanexas;
     private String ctreValor;

    public CfgTablasreferenciasexamenes() {
    }

	
    public CfgTablasreferenciasexamenes(CfgTablasreferenciasexamenesId id, CfgExamenestablasanexas cfgExamenestablasanexas) {
        this.id = id;
        this.cfgExamenestablasanexas = cfgExamenestablasanexas;
    }
    public CfgTablasreferenciasexamenes(CfgTablasreferenciasexamenesId id, CfgExamenestablasanexas cfgExamenestablasanexas, String ctreValor) {
       this.id = id;
       this.cfgExamenestablasanexas = cfgExamenestablasanexas;
       this.ctreValor = ctreValor;
    }
   
    public CfgTablasreferenciasexamenesId getId() {
        return this.id;
    }
    
    public void setId(CfgTablasreferenciasexamenesId id) {
        this.id = id;
    }
    public CfgExamenestablasanexas getCfgExamenestablasanexas() {
        return this.cfgExamenestablasanexas;
    }
    
    public void setCfgExamenestablasanexas(CfgExamenestablasanexas cfgExamenestablasanexas) {
        this.cfgExamenestablasanexas = cfgExamenestablasanexas;
    }
    public String getCtreValor() {
        return this.ctreValor;
    }
    
    public void setCtreValor(String ctreValor) {
        this.ctreValor = ctreValor;
    }




}


