package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1



/**
 * CfgAntecedentestestId generated by hbm2java
 */
public class CfgAntecedentestestId  implements java.io.Serializable {


     private int catIdtest;
     private int catIdantecedente;

    public CfgAntecedentestestId() {
    }

    public CfgAntecedentestestId(int catIdtest, int catIdantecedente) {
       this.catIdtest = catIdtest;
       this.catIdantecedente = catIdantecedente;
    }
   
    public int getCatIdtest() {
        return this.catIdtest;
    }
    
    public void setCatIdtest(int catIdtest) {
        this.catIdtest = catIdtest;
    }
    public int getCatIdantecedente() {
        return this.catIdantecedente;
    }
    
    public void setCatIdantecedente(int catIdantecedente) {
        this.catIdantecedente = catIdantecedente;
    }




}

