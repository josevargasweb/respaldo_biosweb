/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

/**
 *
 * borrar
 */
public class MuestrasRMDTO {
    
    private String MR_CODIGOBARRAS;
    private String MR_FECHATM;
    private String MR_FECHARM;
    private BigDecimal MR_NORDEN;
    private String MR_MUESTRA;
    private String MR_ENVASE;
    private String MR_USUARIO;
    private String MR_ESTADORM;
    private String MR_OBSERVACION;
    private BigDecimal MR_IDMUESTRA;
    private BigDecimal MR_IDDERIVADOR;
    private String MR_DERIVADOR;
    //AGREGADO POR CRISTIAN 28-11
    private String MR_CAUSARECHAZO;
    private String MR_USUARIOTM;
    private String MR_TIPORECHAZO;
    private String MR_EXAMEN;

    public String getMR_CODIGOBARRAS() {
        return MR_CODIGOBARRAS;
    }

    public void setMR_CODIGOBARRAS(String MR_CODIGOBARRAS) {
        this.MR_CODIGOBARRAS = MR_CODIGOBARRAS;
    }

    public String getMR_FECHATM() {
        return MR_FECHATM;
    }

    public void setMR_FECHATM(String MR_FECHATM) {
        this.MR_FECHATM = MR_FECHATM;
    }

    public String getMR_FECHARM() {
        return MR_FECHARM;
    }

    public void setMR_FECHARM(String MR_FECHARM) {
        this.MR_FECHARM = MR_FECHARM;
    }

    public BigDecimal getMR_NORDEN() {
        return MR_NORDEN;
    }

    public void setMR_NORDEN(BigDecimal MR_NORDEN) {
        this.MR_NORDEN = MR_NORDEN;
    }

    public String getMR_ENVASE() {
        return MR_ENVASE;
    }

    public void setMR_ENVASE(String MR_ENVASE) {
        this.MR_ENVASE = MR_ENVASE;
    }

    public String getMR_MUESTRA() {
        return MR_MUESTRA;
    }

    public void setMR_MUESTRA(String MR_MUESTRA) {
        this.MR_MUESTRA = MR_MUESTRA;
    }

    public String getMR_USUARIO() {
        return MR_USUARIO;
    }

    public void setMR_USUARIO(String MR_USUARIO) {
        this.MR_USUARIO = MR_USUARIO;
    }

    public String getMR_ESTADORM() {
        return MR_ESTADORM;
    }

    public void setMR_ESTADORM(String MR_ESTADORM) {
        this.MR_ESTADORM = MR_ESTADORM;
    }

    public String getMR_OBSERVACION() {
        return MR_OBSERVACION;
    }

    public void setMR_OBSERVACION(String MR_OBSERVACION) {
        this.MR_OBSERVACION = MR_OBSERVACION;
    }

    public BigDecimal getMR_IDMUESTRA() {
        return MR_IDMUESTRA;
    }

    public void setMR_IDMUESTRA(BigDecimal MR_IDMUESTRA) {
        this.MR_IDMUESTRA = MR_IDMUESTRA;
    }

    public BigDecimal getMR_IDDERIVADOR() {
        return MR_IDDERIVADOR;
    }

    public void setMR_IDDERIVADOR(BigDecimal MR_IDDERIVADOR) {
        this.MR_IDDERIVADOR = MR_IDDERIVADOR;
    }

    public String getMR_DERIVADOR() {
        return MR_DERIVADOR;
    }

    public void setMR_DERIVADOR(String MR_DERIVADOR) {
        this.MR_DERIVADOR = MR_DERIVADOR;
    }

    public String getMR_CAUSARECHAZO() {
        return MR_CAUSARECHAZO;
    }

    public void setMR_CAUSARECHAZO(String mR_CAUSARECHAZO) {
        MR_CAUSARECHAZO = mR_CAUSARECHAZO;
    }

    public String getMR_USUARIOTM() {
        return MR_USUARIOTM;
    }

    public void setMR_USUARIOTM(String mR_USUARIOTM) {
        MR_USUARIOTM = mR_USUARIOTM;
    }

    public String getMR_TIPORECHAZO() {
        return MR_TIPORECHAZO;
    }

    public void setMR_TIPORECHAZO(String mR_TIPORECHAZO) {
        MR_TIPORECHAZO = mR_TIPORECHAZO;
    }

    public String getMR_EXAMEN() {
        return MR_EXAMEN;
    }

    public void setMR_EXAMEN(String mR_EXAMEN) {
        MR_EXAMEN = mR_EXAMEN;
    }
    
    
}
