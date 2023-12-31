package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1



/**
 * DatosFichasexamenestestatb generated by hbm2java
 */
public class DatosFichasexamenestestatb  implements java.io.Serializable {


     private DatosFichasexamenestestatbId id;
     private long dfetaIdpaciente;
     private Byte dfeaNromicroorganismo;
     private Byte dfeaIdmicroorganismo;
     private Byte dfeaIdantibiograma;
     private Short dfeaIdantibiotico;
     private String dfeaCmi;
     private Short dfeaDiametromilimetros;
     private String dfeaInterpretacion;

    public DatosFichasexamenestestatb() {
    }

	
    public DatosFichasexamenestestatb(DatosFichasexamenestestatbId id, long dfetaIdpaciente) {
        this.id = id;
        this.dfetaIdpaciente = dfetaIdpaciente;
    }
    public DatosFichasexamenestestatb(DatosFichasexamenestestatbId id, long dfetaIdpaciente, Byte dfeaNromicroorganismo, Byte dfeaIdmicroorganismo, Byte dfeaIdantibiograma, Short dfeaIdantibiotico, String dfeaCmi, Short dfeaDiametromilimetros, String dfeaInterpretacion) {
       this.id = id;
       this.dfetaIdpaciente = dfetaIdpaciente;
       this.dfeaNromicroorganismo = dfeaNromicroorganismo;
       this.dfeaIdmicroorganismo = dfeaIdmicroorganismo;
       this.dfeaIdantibiograma = dfeaIdantibiograma;
       this.dfeaIdantibiotico = dfeaIdantibiotico;
       this.dfeaCmi = dfeaCmi;
       this.dfeaDiametromilimetros = dfeaDiametromilimetros;
       this.dfeaInterpretacion = dfeaInterpretacion;
    }
   
    public DatosFichasexamenestestatbId getId() {
        return this.id;
    }
    
    public void setId(DatosFichasexamenestestatbId id) {
        this.id = id;
    }
    public long getDfetaIdpaciente() {
        return this.dfetaIdpaciente;
    }
    
    public void setDfetaIdpaciente(long dfetaIdpaciente) {
        this.dfetaIdpaciente = dfetaIdpaciente;
    }
    public Byte getDfeaNromicroorganismo() {
        return this.dfeaNromicroorganismo;
    }
    
    public void setDfeaNromicroorganismo(Byte dfeaNromicroorganismo) {
        this.dfeaNromicroorganismo = dfeaNromicroorganismo;
    }
    public Byte getDfeaIdmicroorganismo() {
        return this.dfeaIdmicroorganismo;
    }
    
    public void setDfeaIdmicroorganismo(Byte dfeaIdmicroorganismo) {
        this.dfeaIdmicroorganismo = dfeaIdmicroorganismo;
    }
    public Byte getDfeaIdantibiograma() {
        return this.dfeaIdantibiograma;
    }
    
    public void setDfeaIdantibiograma(Byte dfeaIdantibiograma) {
        this.dfeaIdantibiograma = dfeaIdantibiograma;
    }
    public Short getDfeaIdantibiotico() {
        return this.dfeaIdantibiotico;
    }
    
    public void setDfeaIdantibiotico(Short dfeaIdantibiotico) {
        this.dfeaIdantibiotico = dfeaIdantibiotico;
    }
    public String getDfeaCmi() {
        return this.dfeaCmi;
    }
    
    public void setDfeaCmi(String dfeaCmi) {
        this.dfeaCmi = dfeaCmi;
    }
    public Short getDfeaDiametromilimetros() {
        return this.dfeaDiametromilimetros;
    }
    
    public void setDfeaDiametromilimetros(Short dfeaDiametromilimetros) {
        this.dfeaDiametromilimetros = dfeaDiametromilimetros;
    }
    public String getDfeaInterpretacion() {
        return this.dfeaInterpretacion;
    }
    
    public void setDfeaInterpretacion(String dfeaInterpretacion) {
        this.dfeaInterpretacion = dfeaInterpretacion;
    }




}


