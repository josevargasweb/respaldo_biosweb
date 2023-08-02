package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CfgNivelesaccesos generated by hbm2java
 */
public class CfgNivelesaccesos  implements java.io.Serializable {


     private short cnaIdnivelacceso;
     private Short cnaNivelacceso;
     private String cnaDescripcion;
     private String cnaActivo;
     private Set cfgPerfilesusuarioses = new HashSet(0);

    public CfgNivelesaccesos() {
    }

	
    public CfgNivelesaccesos(short cnaIdnivelacceso) {
        this.cnaIdnivelacceso = cnaIdnivelacceso;
    }
    public CfgNivelesaccesos(short cnaIdnivelacceso, Short cnaNivelacceso, String cnaDescripcion, String cnaActivo, Set cfgPerfilesusuarioses) {
       this.cnaIdnivelacceso = cnaIdnivelacceso;
       this.cnaNivelacceso = cnaNivelacceso;
       this.cnaDescripcion = cnaDescripcion;
       this.cnaActivo = cnaActivo;
       this.cfgPerfilesusuarioses = cfgPerfilesusuarioses;
    }
   
    public short getCnaIdnivelacceso() {
        return this.cnaIdnivelacceso;
    }
    
    public void setCnaIdnivelacceso(short cnaIdnivelacceso) {
        this.cnaIdnivelacceso = cnaIdnivelacceso;
    }
    public Short getCnaNivelacceso() {
        return this.cnaNivelacceso;
    }
    
    public void setCnaNivelacceso(Short cnaNivelacceso) {
        this.cnaNivelacceso = cnaNivelacceso;
    }
    public String getCnaDescripcion() {
        return this.cnaDescripcion;
    }
    
    public void setCnaDescripcion(String cnaDescripcion) {
        this.cnaDescripcion = cnaDescripcion;
    }
    public String getCnaActivo() {
        return this.cnaActivo;
    }
    
    public void setCnaActivo(String cnaActivo) {
        this.cnaActivo = cnaActivo;
    }
    public Set getCfgPerfilesusuarioses() {
        return this.cfgPerfilesusuarioses;
    }
    
    public void setCfgPerfilesusuarioses(Set cfgPerfilesusuarioses) {
        this.cfgPerfilesusuarioses = cfgPerfilesusuarioses;
    }




}

