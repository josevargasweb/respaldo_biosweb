package com.grupobios.bioslis.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the CFG_BACMEDIOSCULTIVOS database table.
 * 
 */
@Entity
@Table(name = "CFG_BACMEDIOSCULTIVOS")
@NamedQuery(name = "CfgBacmedioscultivo.findAll", query = "SELECT c FROM CfgBacmedioscultivo c")
public class CfgBacmedioscultivo implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "CFG_BACMEDIOSCULTIVOS_CBMCIDMEDIOCULTIVO_GENERATOR", sequenceName = "SEQ_CFG_BACMEDIOSCULTIVOS")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CFG_BACMEDIOSCULTIVOS_CBMCIDMEDIOCULTIVO_GENERATOR")
  @Column(name = "CBMC_IDMEDIOCULTIVO")
  private long cbmcIdmediocultivo;

  @Column(name = "CBMC_ACTIVO")
  private String cbmcActivo;

  @Column(name = "CBMC_CODIGO")
  private String cbmcCodigo;

  @Column(name = "CBMC_DESCRIPCION")
  private String cbmcDescripcion;

  @Column(name = "CBMC_PREFIJO")
  private String cbmcPrefijo;

  @Column(name = "CBMC_SORT")
  private BigDecimal cbmcSort;

  public CfgBacmedioscultivo() {
  }

  public long getCbmcIdmediocultivo() {
    return this.cbmcIdmediocultivo;
  }

  public void setCbmcIdmediocultivo(long cbmcIdmediocultivo) {
    this.cbmcIdmediocultivo = cbmcIdmediocultivo;
  }

  public String getCbmcActivo() {
    return this.cbmcActivo;
  }

  public void setCbmcActivo(String cbmcActivo) {
    this.cbmcActivo = cbmcActivo;
  }

  public String getCbmcCodigo() {
    return this.cbmcCodigo;
  }

  public void setCbmcCodigo(String cbmcCodigo) {
    this.cbmcCodigo = cbmcCodigo;
  }

  public String getCbmcDescripcion() {
    return this.cbmcDescripcion;
  }

  public void setCbmcDescripcion(String cbmcDescripcion) {
    this.cbmcDescripcion = cbmcDescripcion;
  }

  public String getCbmcPrefijo() {
    return this.cbmcPrefijo;
  }

  public void setCbmcPrefijo(String cbmcPrefijo) {
    this.cbmcPrefijo = cbmcPrefijo;
  }

  public BigDecimal getCbmcSort() {
    return this.cbmcSort;
  }

  public void setCbmcSort(BigDecimal cbmcSort) {
    this.cbmcSort = cbmcSort;
  }

}