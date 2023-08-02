package com.grupobios.bioslis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CFG_BACPRUEBAS database table.
 * 
 */
@Entity
@Table(name="CFG_BACPRUEBAS")
@NamedQuery(name="CfgBacprueba.findAll", query="SELECT c FROM CfgBacprueba c")
public class CfgBacprueba implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CFG_BACPRUEBAS_CBPIDPRUEBA_GENERATOR", sequenceName="SEQ_CFG_BACPRUEBAS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CFG_BACPRUEBAS_CBPIDPRUEBA_GENERATOR")
	@Column(name="CBP_IDPRUEBA")
	private long cbpIdprueba;

	@Column(name="CBP_ACTIVO")
	private String cbpActivo;

	@Column(name="CBP_CODIGO")
	private String cbpCodigo;

	@Column(name="CBP_DESCRIPCION")
	private String cbpDescripcion;

	@Column(name="CBP_SORT")
	private BigDecimal cbpSort;

	@Column(name="CBP_TIPO")
	private String cbpTipo;

	public CfgBacprueba() {
	}

	public long getCbpIdprueba() {
		return this.cbpIdprueba;
	}

	public void setCbpIdprueba(long cbpIdprueba) {
		this.cbpIdprueba = cbpIdprueba;
	}

	public String getCbpActivo() {
		return this.cbpActivo;
	}

	public void setCbpActivo(String cbpActivo) {
		this.cbpActivo = cbpActivo;
	}

	public String getCbpCodigo() {
		return this.cbpCodigo;
	}

	public void setCbpCodigo(String cbpCodigo) {
		this.cbpCodigo = cbpCodigo;
	}

	public String getCbpDescripcion() {
		return this.cbpDescripcion;
	}

	public void setCbpDescripcion(String cbpDescripcion) {
		this.cbpDescripcion = cbpDescripcion;
	}

	public BigDecimal getCbpSort() {
		return this.cbpSort;
	}

	public void setCbpSort(BigDecimal cbpSort) {
		this.cbpSort = cbpSort;
	}

	public String getCbpTipo() {
		return this.cbpTipo;
	}

	public void setCbpTipo(String cbpTipo) {
		this.cbpTipo = cbpTipo;
	}

}