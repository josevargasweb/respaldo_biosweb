/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.SigmaAnalizadores;

/**
 *
 * @author Marco Caracciolo
 */
public class AnalizadorDTO {
    private SigmaAnalizadores sigmaAnalizadores;
    private String imagenAnalizador;

    public SigmaAnalizadores getSigmaAnalizadores() {
        return sigmaAnalizadores;
    }

    public void setSigmaAnalizadores(SigmaAnalizadores sigmaAnalizadores) {
        this.sigmaAnalizadores = sigmaAnalizadores;
    }

    public String getImagenAnalizador() {
        return imagenAnalizador;
    }

    public void setImagenAnalizador(String imagenAnalizador) {
        this.imagenAnalizador = imagenAnalizador;
    }
}
