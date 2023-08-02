/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.util.List;

/**
 *
 * @author eric.nicholls
 */
public class PacienteDTO {
    
    private List<PatologiaDTO> patologias;

    public List<PatologiaDTO> getPatologias() {
        return patologias;
    }

    public void setPatologias(List<PatologiaDTO> patologias) {
        this.patologias = patologias;
    }
   
    
}
