/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class MuestraRechazadaDTO {
    
    private BigDecimal DFM_IDMUESTRA;
    private BigDecimal DMR_IDNUEVAMUESTRA;
    private String DFM_ESTADOTM;
    private String DMR_NOTA;
    private BigDecimal DMR_IDCAUSARECHAZO;
    private BigDecimal DFM_NROVECESTOMADA;
    private String DFM_ESTADONUEVAMUESTRA;
    private String DFM_CODIGOBARRA;
    private List<ExamenPacienteDTO> listaExamenes;

    public BigDecimal getDFM_IDMUESTRA() {
        return DFM_IDMUESTRA;
    }

    public void setDFM_IDMUESTRA(BigDecimal DFM_IDMUESTRA) {
        this.DFM_IDMUESTRA = DFM_IDMUESTRA;
    }

    public BigDecimal getDMR_IDNUEVAMUESTRA() {
        return DMR_IDNUEVAMUESTRA;
    }

    public void setDMR_IDNUEVAMUESTRA(BigDecimal DMR_IDNUEVAMUESTRA) {
        this.DMR_IDNUEVAMUESTRA = DMR_IDNUEVAMUESTRA;
    }

    public String getDFM_ESTADOTM() {
        return DFM_ESTADOTM;
    }

    public void setDFM_ESTADOTM(String DFM_ESTADOTM) {
        this.DFM_ESTADOTM = DFM_ESTADOTM;
    }

    public String getDMR_NOTA() {
        return DMR_NOTA;
    }

    public void setDMR_NOTA(String DMR_NOTA) {
        this.DMR_NOTA = DMR_NOTA;
    }

    public BigDecimal getDMR_IDCAUSARECHAZO() {
        return DMR_IDCAUSARECHAZO;
    }

    public void setDMR_IDCAUSARECHAZO(BigDecimal DMR_IDCAUSARECHAZO) {
        this.DMR_IDCAUSARECHAZO = DMR_IDCAUSARECHAZO;
    }

    public BigDecimal getDFM_NROVECESTOMADA() {
        return DFM_NROVECESTOMADA;
    }

    public void setDFM_NROVECESTOMADA(BigDecimal DFM_NROVECESTOMADA) {
        this.DFM_NROVECESTOMADA = DFM_NROVECESTOMADA;
    }

    public String getDFM_ESTADONUEVAMUESTRA() {
        return DFM_ESTADONUEVAMUESTRA;
    }

    public void setDFM_ESTADONUEVAMUESTRA(String DFM_ESTADONUEVAMUESTRA) {
        this.DFM_ESTADONUEVAMUESTRA = DFM_ESTADONUEVAMUESTRA;
    }

    public String getDFM_CODIGOBARRA() {
        return DFM_CODIGOBARRA;
    }

    public void setDFM_CODIGOBARRA(String DFM_CODIGOBARRA) {
        this.DFM_CODIGOBARRA = DFM_CODIGOBARRA;
    }

    public List<ExamenPacienteDTO> getListaExamenes() {
        return listaExamenes;
    }

    public void setListaExamenes(List<ExamenPacienteDTO> listaExamenes) {
        this.listaExamenes = listaExamenes;
    }
    
}
