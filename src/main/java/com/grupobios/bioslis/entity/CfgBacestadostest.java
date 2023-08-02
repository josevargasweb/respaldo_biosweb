package com.grupobios.bioslis.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the CFG_BACESTADOSTEST database table.
 * 
 */
@Entity
@Table(name = "CFG_BACESTADOSTEST")
@NamedQuery(name = "CfgBacestadostest.findAll", query = "SELECT c FROM CfgBacestadostest c")
public class CfgBacestadostest implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "CFG_BACESTADOSTEST_CBETIDBACESTADOTEST_GENERATOR", sequenceName = "SEQ_CFG_BACESTADOSTEST")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CFG_BACESTADOSTEST_CBETIDBACESTADOTEST_GENERATOR")
  @Column(name = "CBET_IDBACESTADOTEST")
  private long cbetIdbacestadotest;

  @Column(name = "CBET_BACESTADOTEST")
  private String cbetBacestadotest;

  @Column(name = "CBET_DESCRIPCION")
  private String cbetDescripcion;

  public CfgBacestadostest() {
  }

  public long getCbetIdbacestadotest() {
    return this.cbetIdbacestadotest;
  }

  public void setCbetIdbacestadotest(long cbetIdbacestadotest) {
    this.cbetIdbacestadotest = cbetIdbacestadotest;
  }

  public String getCbetBacestadotest() {
    return this.cbetBacestadotest;
  }

  public void setCbetBacestadotest(String cbetBacestadotest) {
    this.cbetBacestadotest = cbetBacestadotest;
  }

  public String getCbetDescripcion() {
    return this.cbetDescripcion;
  }

  public void setCbetDescripcion(String cbetDescripcion) {
    this.cbetDescripcion = cbetDescripcion;
  }

}