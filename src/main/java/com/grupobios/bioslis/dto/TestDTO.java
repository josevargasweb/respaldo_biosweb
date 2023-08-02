/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgMetodos;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.CfgTest;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class TestDTO {
    
    private CfgTest cfgTest;
    private CfgSecciones cfgSecciones;
    private BigDecimal idTipoMuestra;
    private BigDecimal idTipoderesultado;
    private BigDecimal idEnvase;
    private BigDecimal idUnidadmedida;
    private BigDecimal idBacgrupotest;
    private List<CfgAntecedentes> listAntecedentes;
    private List<MetodosTestDTO> listMetodos;
    private List<GlosasTestDTO> listGlosas;
    private CfgMuestras cfgMuestras;
    private CfgMetodos metodoPorDefecto;
    private List<ExamenThinDTO> listExamenesTest;

    public CfgTest getCfgTest() {
        return cfgTest;
    }

    public void setCfgTest(CfgTest cfgTest) {
        this.cfgTest = cfgTest;
    }

    public CfgSecciones getCfgSecciones() {
        return cfgSecciones;
    }

    public void setCfgSecciones(CfgSecciones cfgSecciones) {
        this.cfgSecciones = cfgSecciones;
    }

    public BigDecimal getIdTipoMuestra() {
        return idTipoMuestra;
    }

    public void setIdTipoMuestra(BigDecimal idTipoMuestra) {
        this.idTipoMuestra = idTipoMuestra;
    }

    public BigDecimal getIdTipoderesultado() {
        return idTipoderesultado;
    }

    public void setIdTipoderesultado(BigDecimal idTipoderesultado) {
        this.idTipoderesultado = idTipoderesultado;
    }

    public BigDecimal getIdEnvase() {
        return idEnvase;
    }

    public void setIdEnvase(BigDecimal idEnvase) {
        this.idEnvase = idEnvase;
    }

    public BigDecimal getIdUnidadmedida() {
        return idUnidadmedida;
    }

    public void setIdUnidadmedida(BigDecimal idUnidadmedida) {
        this.idUnidadmedida = idUnidadmedida;
    }

    public BigDecimal getIdBacgrupotest() {
        return idBacgrupotest;
    }

    public void setIdBacgrupotest(BigDecimal idBacgrupotest) {
        this.idBacgrupotest = idBacgrupotest;
    }

    public List<CfgAntecedentes> getListAntecedentes() {
        return listAntecedentes;
    }

    public void setListAntecedentes(List<CfgAntecedentes> listAntecedentes) {
        this.listAntecedentes = listAntecedentes;
    }

    public List<MetodosTestDTO> getListMetodos() {
        return listMetodos;
    }

    public void setListMetodos(List<MetodosTestDTO> listMetodos) {
        this.listMetodos = listMetodos;
    }

    public List<GlosasTestDTO> getListGlosas() {
        return listGlosas;
    }

    public void setListGlosas(List<GlosasTestDTO> listGlosas) {
        this.listGlosas = listGlosas;
    }

    public CfgMuestras getCfgMuestras() {
        return cfgMuestras;
    }

    public void setCfgMuestras(CfgMuestras cfgMuestras) {
        this.cfgMuestras = cfgMuestras;
    }

    public CfgMetodos getMetodoPorDefecto() {
        return metodoPorDefecto;
    }

    public void setMetodoPorDefecto(CfgMetodos metodoPorDefecto) {
        this.metodoPorDefecto = metodoPorDefecto;
    }

    public List<ExamenThinDTO> getListExamenesTest() {
        return listExamenesTest;
    }

    public void setListExamenesTest(List<ExamenThinDTO> listExamenesTest) {
        this.listExamenesTest = listExamenesTest;
    }
    
}
