package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1



/**
 * This class contains the microbiologia detail.
 */
public class Microbiologia  implements java.io.Serializable {


     private long id;
     private String name;

    public Microbiologia() {
    }

    public Microbiologia(String name) {
       this.name = name;
    }
   
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }




}


