package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * CfgCitascupos generated by hbm2java
 */
public class CfgCitascupos  implements java.io.Serializable {


     private short cccIdcitascupos;
     private CfgProcedencias cfgProcedencias;
     private Date cccFecha;
     private Short cccNrocupos;
     private String cccMotivo;

    public CfgCitascupos() {
    }

	
    public CfgCitascupos(short cccIdcitascupos, CfgProcedencias cfgProcedencias) {
        this.cccIdcitascupos = cccIdcitascupos;
        this.cfgProcedencias = cfgProcedencias;
    }
    public CfgCitascupos(short cccIdcitascupos, CfgProcedencias cfgProcedencias, Date cccFecha, Short cccNrocupos, String cccMotivo) {
       this.cccIdcitascupos = cccIdcitascupos;
       this.cfgProcedencias = cfgProcedencias;
       this.cccFecha = cccFecha;
       this.cccNrocupos = cccNrocupos;
       this.cccMotivo = cccMotivo;
    }
   
    public short getCccIdcitascupos() {
        return this.cccIdcitascupos;
    }
    
    public void setCccIdcitascupos(short cccIdcitascupos) {
        this.cccIdcitascupos = cccIdcitascupos;
    }
    public CfgProcedencias getCfgProcedencias() {
        return this.cfgProcedencias;
    }
    
    public void setCfgProcedencias(CfgProcedencias cfgProcedencias) {
        this.cfgProcedencias = cfgProcedencias;
    }
    public Date getCccFecha() {
        return this.cccFecha;
    }
    
    public void setCccFecha(Date cccFecha) {
        this.cccFecha = cccFecha;
    }
    public Short getCccNrocupos() {
        return this.cccNrocupos;
    }
    
    public void setCccNrocupos(Short cccNrocupos) {
        this.cccNrocupos = cccNrocupos;
    }
    public String getCccMotivo() {
        return this.cccMotivo;
    }
    
    public void setCccMotivo(String cccMotivo) {
        this.cccMotivo = cccMotivo;
    }




}

