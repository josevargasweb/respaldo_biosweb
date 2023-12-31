package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CfgCausasrechazosmuestras generated by hbm2java
 */
public class CfgCausasrechazosmuestras  implements java.io.Serializable {


     private short ccrmIdcausarechazo;
     private String ccrmCodigo;
     private String ccrmDescripcion;
     private String ccrmActivo;
     private Byte ccrmSort;
     private Set datosMuestrasrechazadases = new HashSet(0);

    public CfgCausasrechazosmuestras() {
    }

	
    public CfgCausasrechazosmuestras(short ccrmIdcausarechazo, String ccrmCodigo) {
        this.ccrmIdcausarechazo = ccrmIdcausarechazo;
        this.ccrmCodigo = ccrmCodigo;
    }
    public CfgCausasrechazosmuestras(short ccrmIdcausarechazo, String ccrmCodigo, String ccrmDescripcion, String ccrmActivo, Byte ccrmSort, Set datosMuestrasrechazadases) {
       this.ccrmIdcausarechazo = ccrmIdcausarechazo;
       this.ccrmCodigo = ccrmCodigo;
       this.ccrmDescripcion = ccrmDescripcion;
       this.ccrmActivo = ccrmActivo;
       this.ccrmSort = ccrmSort;
       this.datosMuestrasrechazadases = datosMuestrasrechazadases;
    }
   
    public short getCcrmIdcausarechazo() {
        return this.ccrmIdcausarechazo;
    }
    
    public void setCcrmIdcausarechazo(short ccrmIdcausarechazo) {
        this.ccrmIdcausarechazo = ccrmIdcausarechazo;
    }
    public String getCcrmCodigo() {
        return this.ccrmCodigo;
    }
    
    public void setCcrmCodigo(String ccrmCodigo) {
        this.ccrmCodigo = ccrmCodigo;
    }
    public String getCcrmDescripcion() {
        return this.ccrmDescripcion;
    }
    
    public void setCcrmDescripcion(String ccrmDescripcion) {
        this.ccrmDescripcion = ccrmDescripcion;
    }
    public String getCcrmActivo() {
        return this.ccrmActivo;
    }
    
    public void setCcrmActivo(String ccrmActivo) {
        this.ccrmActivo = ccrmActivo;
    }
    public Byte getCcrmSort() {
        return this.ccrmSort;
    }
    
    public void setCcrmSort(Byte ccrmSort) {
        this.ccrmSort = ccrmSort;
    }
    public Set getDatosMuestrasrechazadases() {
        return this.datosMuestrasrechazadases;
    }
    
    public void setDatosMuestrasrechazadases(Set datosMuestrasrechazadases) {
        this.datosMuestrasrechazadases = datosMuestrasrechazadases;
    }




}


