package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1



/**
 * LdvTiposdocumentos generated by hbm2java
 */
public class LdvTiposdocumentos  implements java.io.Serializable {


     private int ldvtdIdtipodocumento;
     private String ldvtdDescripcion;

    public LdvTiposdocumentos() {
    }

    public LdvTiposdocumentos(int ldvtdIdtipodocumento, String ldvtdDescripcion) {
       this.ldvtdIdtipodocumento = ldvtdIdtipodocumento;
       this.ldvtdDescripcion = ldvtdDescripcion;
    }
   
    public int getLdvtdIdtipodocumento() {
        return this.ldvtdIdtipodocumento;
    }
    
    public void setLdvtdIdtipodocumento(int ldvtdIdtipodocumento) {
        this.ldvtdIdtipodocumento = ldvtdIdtipodocumento;
    }
    public String getLdvtdDescripcion() {
        return this.ldvtdDescripcion;
    }
    
    public void setLdvtdDescripcion(String ldvtdDescripcion) {
        this.ldvtdDescripcion = ldvtdDescripcion;
    }




}


