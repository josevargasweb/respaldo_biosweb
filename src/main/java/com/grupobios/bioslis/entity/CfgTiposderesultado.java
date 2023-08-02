package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/**
 * CfgTiposderesultado generated by hbm2java
 */
public class CfgTiposderesultado  implements java.io.Serializable {


     private int ctrIdtiporesultado;
     private String ctrCodigo;
     private String ctrDescripcion;
     private int ctrSort;
     @JsonIgnore
     private Set cfgTests = new HashSet(0);

    public CfgTiposderesultado() {
    }

    public CfgTiposderesultado(String ctrCodigo, String ctrDescripcion, int ctrSort, Set cfgTests) {
       this.ctrCodigo = ctrCodigo;
       this.ctrDescripcion = ctrDescripcion;
       this.ctrSort = ctrSort;
       this.cfgTests = cfgTests;
    }
   
    public int getCtrIdtiporesultado() {
        return this.ctrIdtiporesultado;
    }
    
    public void setCtrIdtiporesultado(int ctrIdtiporesultado) {
        this.ctrIdtiporesultado = ctrIdtiporesultado;
    }
    public String getCtrCodigo() {
        return this.ctrCodigo;
    }
    
    public void setCtrCodigo(String ctrCodigo) {
        this.ctrCodigo = ctrCodigo;
    }
    public String getCtrDescripcion() {
        return this.ctrDescripcion;
    }
    
    public void setCtrDescripcion(String ctrDescripcion) {
        this.ctrDescripcion = ctrDescripcion;
    }
    public int getCtrSort() {
        return this.ctrSort;
    }
    
    public void setCtrSort(int ctrSort) {
        this.ctrSort = ctrSort;
    }
    public Set getCfgTests() {
        return this.cfgTests;
    }
    
    public void setCfgTests(Set cfgTests) {
        this.cfgTests = cfgTests;
    }




}

