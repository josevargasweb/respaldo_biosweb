package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * DatosMuestrasrechazadas generated by hbm2java
 */
public class DatosMuestrasrechazadas  implements java.io.Serializable {


     private Long dmrIdmuestra;
     private DatosFichasmuestras datosFichasmuestras;
     private CfgCausasrechazosmuestras cfgCausasrechazosmuestras;
     private String dmrRechazoparcialototal;
     private String dmrCodigobarra;
     private String dmrNota;
     private Long dmrIdusuariorechazo;
     private Date dmrFecharechazo;
     private Long dmrIdnuevamuestra;

    public DatosMuestrasrechazadas() {
    }
   
    public Long getDmrIdmuestra() {
        return this.dmrIdmuestra;
    }
    
    public void setDmrIdmuestra(Long dmrIdmuestra) {
        this.dmrIdmuestra = dmrIdmuestra;
    }
    public DatosFichasmuestras getDatosFichasmuestras() {
        return this.datosFichasmuestras;
    }
    
    public void setDatosFichasmuestras(DatosFichasmuestras datosFichasmuestras) {
        this.datosFichasmuestras = datosFichasmuestras;
    }
    public CfgCausasrechazosmuestras getCfgCausasrechazosmuestras() {
        return this.cfgCausasrechazosmuestras;
    }
    
    public void setCfgCausasrechazosmuestras(CfgCausasrechazosmuestras cfgCausasrechazosmuestras) {
        this.cfgCausasrechazosmuestras = cfgCausasrechazosmuestras;
    }
    public String getDmrRechazoparcialototal() {
        return this.dmrRechazoparcialototal;
    }
    
    public void setDmrRechazoparcialototal(String dmrRechazoparcialototal) {
        this.dmrRechazoparcialototal = dmrRechazoparcialototal;
    }
    public String getDmrCodigobarra() {
        return this.dmrCodigobarra;
    }
    
    public void setDmrCodigobarra(String dmrCodigobarra) {
        this.dmrCodigobarra = dmrCodigobarra;
    }
    public String getDmrNota() {
        return this.dmrNota;
    }
    
    public void setDmrNota(String dmrNota) {
        this.dmrNota = dmrNota;
    }
    public Long getDmrIdusuariorechazo() {
        return this.dmrIdusuariorechazo;
    }
    
    public void setDmrIdusuariorechazo(Long dmrIdusuariorechazo) {
        this.dmrIdusuariorechazo = dmrIdusuariorechazo;
    }
    public Date getDmrFecharechazo() {
        return this.dmrFecharechazo;
    }
    
    public void setDmrFecharechazo(Date dmrFecharechazo) {
        this.dmrFecharechazo = dmrFecharechazo;
    }

    public Long getDmrIdnuevamuestra() {
        return dmrIdnuevamuestra;
    }

    public void setDmrIdnuevamuestra(Long dmrIdnuevamuestra) {
        this.dmrIdnuevamuestra = dmrIdnuevamuestra;
    }




}


