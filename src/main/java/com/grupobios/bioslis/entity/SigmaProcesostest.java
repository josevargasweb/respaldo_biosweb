package com.grupobios.bioslis.entity;
// Generated 08-11-2022 1:16:04 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/**
 * SigmaProcesostest generated by hbm2java
 */
public class SigmaProcesostest  implements java.io.Serializable {


     private SigmaProcesostestId id;
     @JsonIgnore
     private SigmaTiposmuestras sigmaTiposmuestras;
     @JsonIgnore
     private CfgTiposderesultado cfgTiposderesultado;
     @JsonIgnore
     private CfgTest cfgTest;
     @JsonIgnore
     private CfgMuestrasprefijos cfgMuestrasprefijos;
     @JsonIgnore
     private SigmaProcesos sigmaProcesos;
     private String sptCodigorecepcion;
     private String sptCodigoenvio;
     private String sptActivo;
     private String sptConversion;
     @JsonIgnore
     private Set sigmaProcesotestconversions = new HashSet(0);

    public SigmaProcesostest() {
    }

	
    public SigmaProcesostest(SigmaProcesostestId id, CfgTest cfgTest, SigmaProcesos sigmaProcesos) {
        this.id = id;
        this.cfgTest = cfgTest;
        this.sigmaProcesos = sigmaProcesos;
    }
    public SigmaProcesostest(SigmaProcesostestId id, SigmaTiposmuestras sigmaTiposmuestras, CfgTiposderesultado cfgTiposderesultado, CfgTest cfgTest, CfgMuestrasprefijos cfgMuestrasprefijos, SigmaProcesos sigmaProcesos, String sptCodigorecepcion, String sptCodigoenvio, String sptActivo, String sptConversion, Set sigmaProcesotestconversions) {
       this.id = id;
       this.sigmaTiposmuestras = sigmaTiposmuestras;
       this.cfgTiposderesultado = cfgTiposderesultado;
       this.cfgTest = cfgTest;
       this.cfgMuestrasprefijos = cfgMuestrasprefijos;
       this.sigmaProcesos = sigmaProcesos;
       this.sptCodigorecepcion = sptCodigorecepcion;
       this.sptCodigoenvio = sptCodigoenvio;
       this.sptActivo = sptActivo;
       this.sptConversion = sptConversion;
       this.sigmaProcesotestconversions = sigmaProcesotestconversions;
    }
   
    public SigmaProcesostestId getId() {
        return this.id;
    }
    
    public void setId(SigmaProcesostestId id) {
        this.id = id;
    }
    public SigmaTiposmuestras getSigmaTiposmuestras() {
        return this.sigmaTiposmuestras;
    }
    
    public void setSigmaTiposmuestras(SigmaTiposmuestras sigmaTiposmuestras) {
        this.sigmaTiposmuestras = sigmaTiposmuestras;
    }
    public CfgTiposderesultado getCfgTiposderesultado() {
        return this.cfgTiposderesultado;
    }
    
    public void setCfgTiposderesultado(CfgTiposderesultado cfgTiposderesultado) {
        this.cfgTiposderesultado = cfgTiposderesultado;
    }
    public CfgTest getCfgTest() {
        return this.cfgTest;
    }
    
    public void setCfgTest(CfgTest cfgTest) {
        this.cfgTest = cfgTest;
    }
    public CfgMuestrasprefijos getCfgMuestrasprefijos() {
        return this.cfgMuestrasprefijos;
    }
    
    public void setCfgMuestrasprefijos(CfgMuestrasprefijos cfgMuestrasprefijos) {
        this.cfgMuestrasprefijos = cfgMuestrasprefijos;
    }
    public SigmaProcesos getSigmaProcesos() {
        return this.sigmaProcesos;
    }
    
    public void setSigmaProcesos(SigmaProcesos sigmaProcesos) {
        this.sigmaProcesos = sigmaProcesos;
    }
    public String getSptCodigorecepcion() {
        return this.sptCodigorecepcion;
    }
    
    public void setSptCodigorecepcion(String sptCodigorecepcion) {
        this.sptCodigorecepcion = sptCodigorecepcion;
    }
    public String getSptCodigoenvio() {
        return this.sptCodigoenvio;
    }
    
    public void setSptCodigoenvio(String sptCodigoenvio) {
        this.sptCodigoenvio = sptCodigoenvio;
    }
    public String getSptActivo() {
        return this.sptActivo;
    }
    
    public void setSptActivo(String sptActivo) {
        this.sptActivo = sptActivo;
    }
    public String getSptConversion() {
        return this.sptConversion;
    }
    
    public void setSptConversion(String sptConversion) {
        this.sptConversion = sptConversion;
    }
    public Set getSigmaProcesotestconversions() {
        return this.sigmaProcesotestconversions;
    }
    
    public void setSigmaProcesotestconversions(Set sigmaProcesotestconversions) {
        this.sigmaProcesotestconversions = sigmaProcesotestconversions;
    }




}

