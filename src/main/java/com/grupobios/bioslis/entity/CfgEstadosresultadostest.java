package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1



/**
 * CfgEstadosresultadostest generated by hbm2java
 */
public class CfgEstadosresultadostest  implements java.io.Serializable {


     private byte certIdestadoresultadotest;
     private String certEstadoresultadotest;
     private String certDescripcionestadotest;
     private String certActivo;

    public CfgEstadosresultadostest() {
    }

	
    public CfgEstadosresultadostest(byte certIdestadoresultadotest) {
        this.certIdestadoresultadotest = certIdestadoresultadotest;
    }
    public CfgEstadosresultadostest(byte certIdestadoresultadotest, String certEstadoresultadotest, String certDescripcionestadotest, String certActivo) {
       this.certIdestadoresultadotest = certIdestadoresultadotest;
       this.certEstadoresultadotest = certEstadoresultadotest;
       this.certDescripcionestadotest = certDescripcionestadotest;
       this.certActivo = certActivo;
    }
   
    public byte getCertIdestadoresultadotest() {
        return this.certIdestadoresultadotest;
    }
    
    public void setCertIdestadoresultadotest(byte certIdestadoresultadotest) {
        this.certIdestadoresultadotest = certIdestadoresultadotest;
    }
    public String getCertEstadoresultadotest() {
        return this.certEstadoresultadotest;
    }
    
    public void setCertEstadoresultadotest(String certEstadoresultadotest) {
        this.certEstadoresultadotest = certEstadoresultadotest;
    }
    public String getCertDescripcionestadotest() {
        return this.certDescripcionestadotest;
    }
    
    public void setCertDescripcionestadotest(String certDescripcionestadotest) {
        this.certDescripcionestadotest = certDescripcionestadotest;
    }
    public String getCertActivo() {
        return this.certActivo;
    }
    
    public void setCertActivo(String certActivo) {
        this.certActivo = certActivo;
    }




}

