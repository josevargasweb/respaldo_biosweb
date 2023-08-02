package com.grupobios.bioslis.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CFG_BACMETODOSRESISTENCIA database table.
 * 
 */
@Entity
@Table(name="CFG_BACMETODOSRESISTENCIA")
@NamedQuery(name="CfgBacmetodosresistencia.findAll", query="SELECT c FROM CfgBacmetodosresistencia c")
public class CfgBacmetodosresistencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CFG_BACMETODOSRESISTENCIA_CMRIDMETODORESISTENCIA_GENERATOR", sequenceName="SEQ_CFG_BACMETODOSRESISTENCIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CFG_BACMETODOSRESISTENCIA_CMRIDMETODORESISTENCIA_GENERATOR")
	@Column(name="CMR_IDMETODORESISTENCIA")
	private long cmrIdmetodoresistencia;

	@Column(name="CMR_ACTIVO")
	private String cmrActivo;

	@Column(name="CMR_CODIGO")
	private String cmrCodigo;

	@Column(name="CMR_DESCRIPCION")
	private String cmrDescripcion;

	@Column(name="CMR_HOST")
	private String cmrHost;

	public CfgBacmetodosresistencia() {
	}

	public long getCmrIdmetodoresistencia() {
		return this.cmrIdmetodoresistencia;
	}

	public void setCmrIdmetodoresistencia(long cmrIdmetodoresistencia) {
		this.cmrIdmetodoresistencia = cmrIdmetodoresistencia;
	}

	public String getCmrActivo() {
		return this.cmrActivo;
	}

	public void setCmrActivo(String cmrActivo) {
		this.cmrActivo = cmrActivo;
	}

	public String getCmrCodigo() {
		return this.cmrCodigo;
	}

	public void setCmrCodigo(String cmrCodigo) {
		this.cmrCodigo = cmrCodigo;
	}

	public String getCmrDescripcion() {
		return this.cmrDescripcion;
	}

	public void setCmrDescripcion(String cmrDescripcion) {
		this.cmrDescripcion = cmrDescripcion;
	}

	public String getCmrHost() {
		return this.cmrHost;
	}

	public void setCmrHost(String cmrHost) {
		this.cmrHost = cmrHost;
	}

}