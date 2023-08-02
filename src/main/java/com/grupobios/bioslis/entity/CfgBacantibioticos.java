package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CfgBacantibioticos generated by hbm2java
 */
public class CfgBacantibioticos  implements java.io.Serializable {


     private short cbaIdbacantibiotico;
     private String cbaCodigo;
     private String cbaDescripcion;
     private String cbaActivo;
     private Short cbaSort;
     private String cbaHost;
     private Set cfgBacantibiogramasantibiots = new HashSet(0);

    public CfgBacantibioticos() {
    }

	
    public CfgBacantibioticos(short cbaIdbacantibiotico, String cbaCodigo) {
        this.cbaIdbacantibiotico = cbaIdbacantibiotico;
        this.cbaCodigo = cbaCodigo;
    }
    public CfgBacantibioticos(short cbaIdbacantibiotico, String cbaCodigo, String cbaDescripcion, String cbaActivo, Short cbaSort, String cbaHost, Set cfgBacantibiogramasantibiots) {
       this.cbaIdbacantibiotico = cbaIdbacantibiotico;
       this.cbaCodigo = cbaCodigo;
       this.cbaDescripcion = cbaDescripcion;
       this.cbaActivo = cbaActivo;
       this.cbaSort = cbaSort;
       this.cbaHost = cbaHost;
       this.cfgBacantibiogramasantibiots = cfgBacantibiogramasantibiots;
    }
   
    public short getCbaIdbacantibiotico() {
        return this.cbaIdbacantibiotico;
    }
    
    public void setCbaIdbacantibiotico(short cbaIdbacantibiotico) {
        this.cbaIdbacantibiotico = cbaIdbacantibiotico;
    }
    public String getCbaCodigo() {
        return this.cbaCodigo;
    }
    
    public void setCbaCodigo(String cbaCodigo) {
        this.cbaCodigo = cbaCodigo;
    }
    public String getCbaDescripcion() {
        return this.cbaDescripcion;
    }
    
    public void setCbaDescripcion(String cbaDescripcion) {
        this.cbaDescripcion = cbaDescripcion;
    }
    public String getCbaActivo() {
        return this.cbaActivo;
    }
    
    public void setCbaActivo(String cbaActivo) {
        this.cbaActivo = cbaActivo;
    }
    public Short getCbaSort() {
        return this.cbaSort;
    }
    
    public void setCbaSort(Short cbaSort) {
        this.cbaSort = cbaSort;
    }
    public String getCbaHost() {
        return this.cbaHost;
    }
    
    public void setCbaHost(String cbaHost) {
        this.cbaHost = cbaHost;
    }
    public Set getCfgBacantibiogramasantibiots() {
        return this.cfgBacantibiogramasantibiots;
    }
    
    public void setCfgBacantibiogramasantibiots(Set cfgBacantibiogramasantibiots) {
        this.cfgBacantibiogramasantibiots = cfgBacantibiogramasantibiots;
    }




}


