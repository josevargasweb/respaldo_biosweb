package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1



/**
 * DatosFichasantecedentesId generated by hbm2java
 */
public class DatosFichasantecedentesId  implements java.io.Serializable {


     private long dfaNorden;
     private short dfaIdantecedente;

    public DatosFichasantecedentesId() {
    }

    public DatosFichasantecedentesId(long dfaNorden, short dfaIdantecedente) {
       this.dfaNorden = dfaNorden;
       this.dfaIdantecedente = dfaIdantecedente;
    }
   
    public long getDfaNorden() {
        return this.dfaNorden;
    }
    
    public void setDfaNorden(long dfaNorden) {
        this.dfaNorden = dfaNorden;
    }
    public short getDfaIdantecedente() {
        return this.dfaIdantecedente;
    }
    
    public void setDfaIdantecedente(short dfaIdantecedente) {
        this.dfaIdantecedente = dfaIdantecedente;
    }

    @Override
    public String toString() {
        return "DatosFichasantecedentesId{" + "dfaNorden=" + dfaNorden + ", dfaIdantecedente=" + dfaIdantecedente + '}';
    }




}


