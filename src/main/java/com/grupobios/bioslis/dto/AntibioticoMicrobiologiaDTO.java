package com.grupobios.bioslis.dto;

import java.util.ArrayList;
import java.util.List;

import com.grupobios.bioslis.microbiologia.dto.MicroorganismoMicrobiologiaDTO;

public class AntibioticoMicrobiologiaDTO {

   
    private MicroorganismoMicrobiologiaDTO microorganism;
    private DatosAntibiogramMicrobiologiaDTO antibiogram;
    private List<MethodsMicrobiologiaDTO> methods;
    private List<DatosAntibioticosMicrobiologiaDTO> antibiotics;
    

    public AntibioticoMicrobiologiaDTO() {
        this.methods = new ArrayList<MethodsMicrobiologiaDTO>();
        this.antibiotics = new ArrayList<DatosAntibioticosMicrobiologiaDTO>();
    }
   
    public MicroorganismoMicrobiologiaDTO getMicroorganism() {
        return microorganism;
    }
    public void setMicroorganism(MicroorganismoMicrobiologiaDTO microorganism) {
        this.microorganism = microorganism;
    }
    public DatosAntibiogramMicrobiologiaDTO getAntibiogram() {
        return antibiogram;
    }
    public void setAntibiogram(DatosAntibiogramMicrobiologiaDTO antibiogram) {
        this.antibiogram = antibiogram;
    }
    public List<MethodsMicrobiologiaDTO> getMethods() {
        return methods;
    }
    public void setMethods(List<MethodsMicrobiologiaDTO> methods) {
        this.methods = methods;
    }
    
    
    public List<DatosAntibioticosMicrobiologiaDTO> getAntibiotics() {
        return antibiotics;
    }
    public void setAntibiotics(List<DatosAntibioticosMicrobiologiaDTO> antibiotics) {
        this.antibiotics = antibiotics;
    }
    
    
   
    
    
    
}
