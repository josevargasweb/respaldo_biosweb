package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1



/**
 * LdvRegiones generated by hbm2java
 */
public class LdvRegiones  implements java.io.Serializable {


     private int ldvrIdregion;
     private String ldvrDescripcion;

    public LdvRegiones() {
    }

    public LdvRegiones(int ldvrIdregion, String ldvrDescripcion) {
       this.ldvrIdregion = ldvrIdregion;
       this.ldvrDescripcion = ldvrDescripcion;
    }
   
    public int getLdvrIdregion() {
        return this.ldvrIdregion;
    }
    
    public void setLdvrIdregion(int ldvrIdregion) {
        this.ldvrIdregion = ldvrIdregion;
    }
    public String getLdvrDescripcion() {
        return this.ldvrDescripcion;
    }
    
    public void setLdvrDescripcion(String ldvrDescripcion) {
        this.ldvrDescripcion = ldvrDescripcion;
    }




}

