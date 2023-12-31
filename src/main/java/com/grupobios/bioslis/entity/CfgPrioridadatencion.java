package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

/**
 * CfgPrioridadatencion generated by hbm2java
 */
public class CfgPrioridadatencion  implements java.io.Serializable {


     private short cpaIdprioridadatencion;
     private String cpaDescripcion;
     private String cpaActivo;
     private Byte cpaSort;
     private String cpaHost;
     private Byte cpaPrioridad;
     private String cpaColorprioridad;
     @JsonIgnore
     private Blob cpaIconoprioridad;
     @JsonIgnore
     private Set datosFichases = new HashSet(0);

    public CfgPrioridadatencion() {
    }

	
    public CfgPrioridadatencion(short cpaIdprioridadatencion) {
        this.cpaIdprioridadatencion = cpaIdprioridadatencion;
    }
    public CfgPrioridadatencion(short cpaIdprioridadatencion, String cpaDescripcion, String cpaActivo, Byte cpaSort, String cpaHost, Byte cpaPrioridad, String cpaColorprioridad, Blob cpaIconoprioridad, Set datosFichases) {
       this.cpaIdprioridadatencion = cpaIdprioridadatencion;
       this.cpaDescripcion = cpaDescripcion;
       this.cpaActivo = cpaActivo;
       this.cpaSort = cpaSort;
       this.cpaHost = cpaHost;
       this.cpaPrioridad = cpaPrioridad;
       this.cpaColorprioridad = cpaColorprioridad;
       this.cpaIconoprioridad = cpaIconoprioridad;
       this.datosFichases = datosFichases;
    }
   
    public short getCpaIdprioridadatencion() {
        return this.cpaIdprioridadatencion;
    }
    
    public void setCpaIdprioridadatencion(short cpaIdprioridadatencion) {
        this.cpaIdprioridadatencion = cpaIdprioridadatencion;
    }
    public String getCpaDescripcion() {
        return this.cpaDescripcion;
    }
    
    public void setCpaDescripcion(String cpaDescripcion) {
        this.cpaDescripcion = cpaDescripcion;
    }
    public String getCpaActivo() {
        return this.cpaActivo;
    }
    
    public void setCpaActivo(String cpaActivo) {
        this.cpaActivo = cpaActivo;
    }
    public Byte getCpaSort() {
        return this.cpaSort;
    }
    
    public void setCpaSort(Byte cpaSort) {
        this.cpaSort = cpaSort;
    }
    public String getCpaHost() {
        return this.cpaHost;
    }
    
    public void setCpaHost(String cpaHost) {
        this.cpaHost = cpaHost;
    }
    public Byte getCpaPrioridad() {
        return this.cpaPrioridad;
    }
    
    public void setCpaPrioridad(Byte cpaPrioridad) {
        this.cpaPrioridad = cpaPrioridad;
    }
    public String getCpaColorprioridad() {
        return this.cpaColorprioridad;
    }
    
    public void setCpaColorprioridad(String cpaColorprioridad) {
        this.cpaColorprioridad = cpaColorprioridad;
    }
    public Blob getCpaIconoprioridad() {
        return this.cpaIconoprioridad;
    }
    
    public void setCpaIconoprioridad(Blob cpaIconoprioridad) {
        this.cpaIconoprioridad = cpaIconoprioridad;
    }
    public Set getDatosFichases() {
        return this.datosFichases;
    }
    
    public void setDatosFichases(Set datosFichases) {
        this.datosFichases = datosFichases;
    }




}


