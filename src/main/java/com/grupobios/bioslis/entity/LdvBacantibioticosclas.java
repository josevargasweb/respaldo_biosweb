package com.grupobios.bioslis.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LDV_BACANTIBIOTICOSCLASES database table.
 * 
 */
@Entity
@Table(name="LDV_BACANTIBIOTICOSCLASES")
@NamedQuery(name="LdvBacantibioticosclas.findAll", query="SELECT l FROM LdvBacantibioticosclas l")
public class LdvBacantibioticosclas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LDV_BACANTIBIOTICOSCLASES_LDVACIDANTIBIOTICOCLASE_GENERATOR", sequenceName="SEQ_CFG_BACESTADOSTEST")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LDV_BACANTIBIOTICOSCLASES_LDVACIDANTIBIOTICOCLASE_GENERATOR")
	@Column(name="LDVAC_IDANTIBIOTICOCLASE")
	private long ldvacIdantibioticoclase;

	@Column(name="LDVAC_DESCRIPCION")
	private String ldvacDescripcion;

	public LdvBacantibioticosclas() {
	}

	public long getLdvacIdantibioticoclase() {
		return this.ldvacIdantibioticoclase;
	}

	public void setLdvacIdantibioticoclase(long ldvacIdantibioticoclase) {
		this.ldvacIdantibioticoclase = ldvacIdantibioticoclase;
	}

	public String getLdvacDescripcion() {
		return this.ldvacDescripcion;
	}

	public void setLdvacDescripcion(String ldvacDescripcion) {
		this.ldvacDescripcion = ldvacDescripcion;
	}

}