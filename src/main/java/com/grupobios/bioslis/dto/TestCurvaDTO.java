/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Marco Caracciolo
 */
public class TestCurvaDTO {
    private String TEST;
    private BigDecimal MINUTOS;
    private String NROMUESTRA;
    private String MUESTRA;
    private String FLEBOTOMISTA;
    private BigDecimal IDMUESTRA;
    private String TOMADA;
    private String ESTADOMUESTRA;

    public String getTEST() {
        return TEST;
    }

    public void setTEST(String TEST) {
        this.TEST = TEST;
    }

    public BigDecimal getMINUTOS() {
        return MINUTOS;
    }

    public void setMINUTOS(BigDecimal MINUTOS) {
        this.MINUTOS = MINUTOS;
    }

    public String getNROMUESTRA() {
        return NROMUESTRA;
    }

    public void setNROMUESTRA(String NROMUESTRA) {
        this.NROMUESTRA = NROMUESTRA;
    }

    public String getMUESTRA() {
        return MUESTRA;
    }

    public void setMUESTRA(String MUESTRA) {
        this.MUESTRA = MUESTRA;
    }

    public String getFLEBOTOMISTA() {
        return FLEBOTOMISTA;
    }

    public void setFLEBOTOMISTA(String FLEBOTOMISTA) {
        this.FLEBOTOMISTA = FLEBOTOMISTA;
    }

    public BigDecimal getIDMUESTRA() {
        return IDMUESTRA;
    }

    public void setIDMUESTRA(BigDecimal IDMUESTRA) {
        this.IDMUESTRA = IDMUESTRA;
    }

    public String getTOMADA() {
        return TOMADA;
    }

    public void setTOMADA(String TOMADA) {
        this.TOMADA = TOMADA;
    }

    public String getESTADOMUESTRA() {
        return ESTADOMUESTRA;
    }

    public void setESTADOMUESTRA(String ESTADOMUESTRA) {
        this.ESTADOMUESTRA = ESTADOMUESTRA;
    }
    
}
