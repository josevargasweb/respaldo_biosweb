package com.grupobios.bioslis.entity;

import javax.persistence.Transient;

import com.grupobios.bioslis.cfg.cache.CfgAbstracto;

// Generated 30-03-2020 9:50:08 by Hibernate Tools 4.3.1

/**
 * LdvSexo generated by hbm2java
 */
public class LdvSexo extends CfgAbstracto {

  private int ldvsIdsexo;
  private String ldvsDescripcion;
  private String ldvsCodigo;

  public LdvSexo() {
  }

  public int getLdvsIdsexo() {
    return ldvsIdsexo;
  }

  public void setLdvsIdsexo(int ldvsIdsexo) {
    this.ldvsIdsexo = ldvsIdsexo;
  }

  public String getLdvsDescripcion() {
    return this.ldvsDescripcion;
  }

  public void setLdvsDescripcion(String ldvsDescripcion) {
    this.ldvsDescripcion = ldvsDescripcion;
  }

  /**
   * @return the ldvsCodigo
   */
  public String getLdvsCodigo() {
    return ldvsCodigo;
  }

  /**
   * @param ldvsCodigo the ldvsCodigo to set
   */
  public void setLdvsCodigo(String ldvsCodigo) {
    this.ldvsCodigo = ldvsCodigo;
  }

  @Transient
  @Override
  public Integer getId() {
    return ldvsIdsexo;
  }

  @Transient
  @Override
  public String getDescripcion() {
    // TODO Auto-generated method stub
    return ldvsDescripcion;
  }

}
