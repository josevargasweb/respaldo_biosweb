package com.grupobios.bioslis.entity;
// Generated 22-04-2020 16:13:26 by Hibernate Tools 4.3.1



/**
 * DatosPacientespatologias generated by hbm2java
 */
public class DatosPacientespatologias  implements java.io.Serializable {


     private Integer dppIdpacientepatologia;
     private DatosPacientes datosPacientes;
     private LdvPatologias ldvPatologias;
     private String dppObservacion;

    public DatosPacientespatologias() {
    }

	/*
    public DatosPacientespatologias(int dppIdpacientepatologia, DatosPacientes datosPacientes, LdvPatologias ldvPatologias) {
        this.dppIdpacientepatologia = dppIdpacientepatologia;
        this.datosPacientes = datosPacientes;
        this.ldvPatologias = ldvPatologias;
    }
    public DatosPacientespatologias(int dppIdpacientepatologia, DatosPacientes datosPacientes, LdvPatologias ldvPatologias, String dppObservacion) {
       this.dppIdpacientepatologia = dppIdpacientepatologia;
       this.datosPacientes = datosPacientes;
       this.ldvPatologias = ldvPatologias;
       this.dppObservacion = dppObservacion;
    }
    */

    public Integer getDppIdpacientepatologia() {
        return dppIdpacientepatologia;
    }

    public void setDppIdpacientepatologia(Integer dppIdpacientepatologia) {
        this.dppIdpacientepatologia = dppIdpacientepatologia;
    }
    
    public DatosPacientes getDatosPacientes() {
        return this.datosPacientes;
    }
    
    public void setDatosPacientes(DatosPacientes datosPacientes) {
        this.datosPacientes = datosPacientes;
    }
    public LdvPatologias getLdvPatologias() {
        return this.ldvPatologias;
    }
    
    public void setLdvPatologias(LdvPatologias ldvPatologias) {
        this.ldvPatologias = ldvPatologias;
    }
    public String getDppObservacion() {
        return this.dppObservacion;
    }
    
    public void setDppObservacion(String dppObservacion) {
        this.dppObservacion = dppObservacion;
    }

    @Override
    public String toString() {
        return "DatosPacientespatologias [dppIdpacientepatologia=" + dppIdpacientepatologia + ", datosPacientes="
                + datosPacientes + ", ldvPatologias=" + ldvPatologias + ", dppObservacion=" + dppObservacion + "]";
    }




}


