package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * LdvIndicacionesexamenes generated by hbm2java
 */
public class LdvIndicacionesexamenes {

  private long ldvieIdindicacionesexamen;
  private String ldvieDescripcion;

  //@JsonIgnore
//  private Set<CfgExamenes> cfgExameneses;

 

public LdvIndicacionesexamenes() {
	super();
}

  
  public LdvIndicacionesexamenes(long ldvieIdindicacionesexamen, String ldvieDescripcion) {	
	this.ldvieIdindicacionesexamen = ldvieIdindicacionesexamen;
	this.ldvieDescripcion = ldvieDescripcion;
}





public long getLdvieIdindicacionesexamen() {
    return this.ldvieIdindicacionesexamen;
  }

  public void setLdvieIdindicacionesexamen(long ldvieIdindicacionesexamen) {
    this.ldvieIdindicacionesexamen = ldvieIdindicacionesexamen;
  }

  public String getLdvieDescripcion() {
    return this.ldvieDescripcion;
  }

  public void setLdvieDescripcion(String ldvieDescripcion) {
    this.ldvieDescripcion = ldvieDescripcion;
  }

//  public Set<CfgExamenes> getCfgExameneses() {
//    return this.cfgExameneses;
//  }
//
//  public void setCfgExameneses(Set<CfgExamenes> cfgExameneses) {
//    this.cfgExameneses = cfgExameneses;
//  }

}
