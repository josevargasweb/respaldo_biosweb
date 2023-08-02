/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.util.Arrays;

import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgExamenesconjuntodetalle;
import com.grupobios.bioslis.entity.CfgExamenesconjuntos;
import com.grupobios.bioslis.entity.CfgExamenesindicacionestm;
import com.grupobios.bioslis.entity.CfgExamenestablasanexas;
import com.grupobios.bioslis.entity.CfgExamenestest;
import com.grupobios.bioslis.entity.CfgGruposexamenesfonasa;
import com.grupobios.bioslis.entity.CfgGruposmuestrasexa;
import com.grupobios.bioslis.entity.CfgMetodos;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgPesquisas;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.CfgTablasreferenciasexamenes;
import com.grupobios.bioslis.entity.LdvFormatos;
import com.grupobios.bioslis.entity.LdvGruposexamenes;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;
import com.grupobios.bioslis.entity.LdvLoinc;
import com.grupobios.bioslis.entity.LdvTiposexamenes;

/**
 *
 * @author Marco Caracciolo
 */
public class ExamenFullDTO {
    
    private CfgExamenes cfgExamenes;
    private CfgDerivadores cfgDerivadores;
    private CfgMuestras cfgMuestras;
    private CfgSecciones cfgSecciones;
    private CfgPesquisas cfgPesquisas;//ok
    private LdvIndicacionesexamenes ldvIndicacionesexamenes;//ok
    private LdvFormatos ldvFormatos;//ok
    // private LdvLoinc ldvLoinc;//ok
    private CfgGruposexamenesfonasa cfgGruposexamenesfonasa;//ok
    private CfgExamenesconjuntos cfgExamenesconjuntos;
    private LdvGruposexamenes ldvGruposexamenes;//ok
    private LdvTiposexamenes ldvTiposexamenes;
    private CfgMetodos cfgMetodos;
    private CfgExamenestablasanexas examenestablasanexas;
    private CfgExamenestest[] listTestsExamen;
    private CfgGruposmuestrasexa[] listGruposmuestrasexa;
    private CfgExamenesindicacionestm[] listExamenesIndicacionesTM;
    private CfgExamenesconjuntodetalle[] examenesconjuntodetalles;
    private CfgTablasreferenciasexamenes[] tablaReferencias;
    private short idDerivador;

    public CfgExamenes getCfgExamenes() {
        return cfgExamenes;
    }

    public void setCfgExamenes(CfgExamenes cfgExamenes) {
        this.cfgExamenes = cfgExamenes;
    }

    public CfgDerivadores getCfgDerivadores() {
        return cfgDerivadores;
    }

    public void setCfgDerivadores(CfgDerivadores cfgDerivadores) {
        this.cfgDerivadores = cfgDerivadores;
    }

    public CfgMuestras getCfgMuestras() {
        return cfgMuestras;
    }

    public void setCfgMuestras(CfgMuestras cfgMuestras) {
        this.cfgMuestras = cfgMuestras;
    }

    public CfgSecciones getCfgSecciones() {
        return cfgSecciones;
    }

    public void setCfgSecciones(CfgSecciones cfgSecciones) {
        this.cfgSecciones = cfgSecciones;
    }

    public CfgPesquisas getCfgPesquisas() {
        return cfgPesquisas;
    }

    public void setCfgPesquisas(CfgPesquisas cfgPesquisas) {
        this.cfgPesquisas = cfgPesquisas;
    }

    public LdvIndicacionesexamenes getLdvIndicacionesexamenes() {
        return ldvIndicacionesexamenes;
    }

    public void setLdvIndicacionesexamenes(LdvIndicacionesexamenes ldvIndicacionesexamenes) {
        this.ldvIndicacionesexamenes = ldvIndicacionesexamenes;
    }

    public LdvFormatos getLdvFormatos() {
        return ldvFormatos;
    }

    public void setLdvFormatos(LdvFormatos ldvFormatos) {
        this.ldvFormatos = ldvFormatos;
    }

    // public LdvLoinc getLdvLoinc() {
    //     return ldvLoinc;
    // }

    // public void setLdvLoinc(LdvLoinc ldvLoinc) {
    //     this.ldvLoinc = ldvLoinc;
    // }

    public CfgGruposexamenesfonasa getCfgGruposexamenesfonasa() {
        return cfgGruposexamenesfonasa;
    }

    public void setCfgGruposexamenesfonasa(CfgGruposexamenesfonasa cfgGruposexamenesfonasa) {
        this.cfgGruposexamenesfonasa = cfgGruposexamenesfonasa;
    }

    public LdvGruposexamenes getLdvGruposexamenes() {
        return ldvGruposexamenes;
    }

    public void setLdvGruposexamenes(LdvGruposexamenes ldvGruposexamenes) {
        this.ldvGruposexamenes = ldvGruposexamenes;
    }

    public CfgExamenestest[] getListTestsExamen() {
        return listTestsExamen;
    }

    public void setListTestsExamen(CfgExamenestest[] listTestsExamen) {
        this.listTestsExamen = listTestsExamen;
    }

    public CfgGruposmuestrasexa[] getListGruposmuestrasexa() {
        return listGruposmuestrasexa;
    }

    public void setListGruposmuestrasexa(CfgGruposmuestrasexa[] listGruposmuestrasexa) {
        this.listGruposmuestrasexa = listGruposmuestrasexa;
    }

    public CfgExamenesconjuntos getCfgExamenesconjuntos() {
        return cfgExamenesconjuntos;
    }

    public void setCfgExamenesconjuntos(CfgExamenesconjuntos cfgExamenesconjuntos) {
        this.cfgExamenesconjuntos = cfgExamenesconjuntos;
    }

    public CfgExamenesindicacionestm[] getListExamenesIndicacionesTM() {
        return listExamenesIndicacionesTM;
    }

    public void setListExamenesIndicacionesTM(CfgExamenesindicacionestm[] listExamenesIndicacionesTM) {
        this.listExamenesIndicacionesTM = listExamenesIndicacionesTM;
    }

    public LdvTiposexamenes getLdvTiposexamenes() {
        return ldvTiposexamenes;
    }

    public void setLdvTiposexamenes(LdvTiposexamenes ldvTiposexamenes) {
        this.ldvTiposexamenes = ldvTiposexamenes;
    }

    public CfgMetodos getCfgMetodos() {
        return cfgMetodos;
    }

    public void setCfgMetodos(CfgMetodos cfgMetodos) {
        this.cfgMetodos = cfgMetodos;
    }

    public CfgExamenesconjuntodetalle[] getExamenesconjuntodetalles() {
        return examenesconjuntodetalles;
    }

    public void setExamenesconjuntodetalles(CfgExamenesconjuntodetalle[] examenesconjuntodetalles) {
        this.examenesconjuntodetalles = examenesconjuntodetalles;
    }

    public CfgTablasreferenciasexamenes[] getTablaReferencias() {
        return tablaReferencias;
    }

    public void setTablaReferencias(CfgTablasreferenciasexamenes[] tablaReferencias) {
        this.tablaReferencias = tablaReferencias;
    }

    public CfgExamenestablasanexas getExamenestablasanexas() {
        return examenestablasanexas;
    }

    public void setExamenestablasanexas(CfgExamenestablasanexas examenestablasanexas) {
        this.examenestablasanexas = examenestablasanexas;
    }

    public short getIdDerivador() {
        return idDerivador;
    }

    public void setIdDerivador(short idDerivador) {
        this.idDerivador = idDerivador;
    }

	@Override
	public String toString() {
		return "ExamenFullDTO [cfgExamenes=" + cfgExamenes + ", cfgDerivadores=" + cfgDerivadores + ", cfgMuestras="
				+ cfgMuestras + ", cfgSecciones=" + cfgSecciones + ", cfgPesquisas=" + cfgPesquisas
				+ ", ldvIndicacionesexamenes=" + ldvIndicacionesexamenes + ", ldvFormatos=" + ldvFormatos
				+ ", cfgGruposexamenesfonasa=" + cfgGruposexamenesfonasa + ", cfgExamenesconjuntos="
				+ cfgExamenesconjuntos + ", ldvGruposexamenes=" + ldvGruposexamenes + ", ldvTiposexamenes="
				+ ldvTiposexamenes + ", cfgMetodos=" + cfgMetodos + ", examenestablasanexas=" + examenestablasanexas
				+ ", listTestsExamen=" + Arrays.toString(listTestsExamen) + ", listGruposmuestrasexa="
				+ Arrays.toString(listGruposmuestrasexa) + ", listExamenesIndicacionesTM="
				+ Arrays.toString(listExamenesIndicacionesTM) + ", examenesconjuntodetalles="
				+ Arrays.toString(examenesconjuntodetalles) + ", tablaReferencias=" + Arrays.toString(tablaReferencias)
				+ ", idDerivador=" + idDerivador + "]";
	}
    

}
