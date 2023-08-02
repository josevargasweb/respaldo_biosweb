package com.grupobios.bioslis.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LDV_BACMETODOSEVALUACIONR database table.
 * 
 */
@Entity
@Table(name="LDV_BACMETODOSEVALUACIONR")
@NamedQuery(name="LdvBacmetodosevaluacionr.findAll", query="SELECT l FROM LdvBacmetodosevaluacionr l")
public class LdvBacmetodosevaluacionr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LDV_BACMETODOSEVALUACIONR_LDVMERIDMETODOEVALUACIONR_GENERATOR", sequenceName="SEQ_CFG_BACESTADOSTEST")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LDV_BACMETODOSEVALUACIONR_LDVMERIDMETODOEVALUACIONR_GENERATOR")
	@Column(name="LDVMER_IDMETODOEVALUACIONR")
	private long ldvmerIdmetodoevaluacionr;

	@Column(name="LDVMER_DESCRIPCION")
	private String ldvmerDescripcion;

	public LdvBacmetodosevaluacionr() {
	}

	public long getLdvmerIdmetodoevaluacionr() {
		return this.ldvmerIdmetodoevaluacionr;
	}

	public void setLdvmerIdmetodoevaluacionr(long ldvmerIdmetodoevaluacionr) {
		this.ldvmerIdmetodoevaluacionr = ldvmerIdmetodoevaluacionr;
	}

	public String getLdvmerDescripcion() {
		return this.ldvmerDescripcion;
	}

	public void setLdvmerDescripcion(String ldvmerDescripcion) {
		this.ldvmerDescripcion = ldvmerDescripcion;
	}

}