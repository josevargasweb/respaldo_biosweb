package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class AntecedentePacienteDTO {

	private BigDecimal DFA_NORDEN;
	private String CA_DESCRIPCION;
	private String DFA_VALORANTECEDENTE;
	private String CA_CODIGOUM;
	private String CA_FORMATOUM;
	private String CA_CODIGOANTECEDENTE;
	private BigDecimal CA_IDANTECEDENTE;
	// campos agregados por Marco Caracciolo 04/01/2023
	private String CA_SOLICITARTM;
	private String CA_VALIDARESNUMERICO;
	private String CA_OBLIGATORIO;
	private String CA_ACTIVO;

	public BigDecimal getDFA_NORDEN() {
		return DFA_NORDEN;
	}

	public void setDFA_NORDEN(BigDecimal dFA_NORDEN) {
		DFA_NORDEN = dFA_NORDEN;
	}

	public String getDFA_VALORANTECEDENTE() {
		return DFA_VALORANTECEDENTE;
	}

	public void setDFA_VALORANTECEDENTE(String dFA_VALORANTECEDENTE) {
		DFA_VALORANTECEDENTE = dFA_VALORANTECEDENTE;
	}

	public String getCA_DESCRIPCION() {
		return CA_DESCRIPCION;
	}

	public void setCA_DESCRIPCION(String cA_DESCRIPCION) {
		CA_DESCRIPCION = cA_DESCRIPCION;
	}

	public String getCA_CODIGOUM() {
		return CA_CODIGOUM;
	}

	public void setCA_CODIGOUM(String cA_CODIGOUM) {
		CA_CODIGOUM = cA_CODIGOUM;
	}

	public String getCA_FORMATOUM() {
		return CA_FORMATOUM;
	}

	public void setCA_FORMATOUM(String cA_FORMATOUM) {
		CA_FORMATOUM = cA_FORMATOUM;
	}

	/**
	 * @return the cA_CODIGOANTECEDENTE
	 */
	public String getCA_CODIGOANTECEDENTE() {
		return CA_CODIGOANTECEDENTE;
	}

	/**
	 * @param cA_CODIGOANTECEDENTE the cA_CODIGOANTECEDENTE to set
	 */
	public void setCA_CODIGOANTECEDENTE(String cA_CODIGOANTECEDENTE) {
		CA_CODIGOANTECEDENTE = cA_CODIGOANTECEDENTE;
	}

	public BigDecimal getCA_IDANTECEDENTE() {
		return CA_IDANTECEDENTE;
	}

	public void setCA_IDANTECEDENTE(BigDecimal CA_IDANTECEDENTE) {
		this.CA_IDANTECEDENTE = CA_IDANTECEDENTE;
	}

	public String getCA_SOLICITARTM() {
		return CA_SOLICITARTM;
	}

	public void setCA_SOLICITARTM(String CA_SOLICITARTM) {
		this.CA_SOLICITARTM = CA_SOLICITARTM;
	}

	public String getCA_VALIDARESNUMERICO() {
		return CA_VALIDARESNUMERICO;
	}

	public void setCA_VALIDARESNUMERICO(String CA_VALIDARESNUMERICO) {
		this.CA_VALIDARESNUMERICO = CA_VALIDARESNUMERICO;
	}

	public String getCA_OBLIGATORIO() {
		return CA_OBLIGATORIO;
	}

	public void setCA_OBLIGATORIO(String CA_OBLIGATORIO) {
		this.CA_OBLIGATORIO = CA_OBLIGATORIO;
	}

	public String getCA_ACTIVO() {
		return CA_ACTIVO;
	}

	public void setCA_ACTIVO(String CA_ACTIVO) {
		this.CA_ACTIVO = CA_ACTIVO;
	}

	@Override
	public String toString() {
		return "AntecedentePacienteDTO [DFA_NORDEN=" + DFA_NORDEN + ", CA_DESCRIPCION=" + CA_DESCRIPCION
				+ ", DFA_VALORANTECEDENTE=" + DFA_VALORANTECEDENTE + ", CA_CODIGOUM=" + CA_CODIGOUM + ", CA_FORMATOUM="
				+ CA_FORMATOUM + ", CA_CODIGOANTECEDENTE=" + CA_CODIGOANTECEDENTE + ", CA_IDANTECEDENTE="
				+ CA_IDANTECEDENTE + ", CA_SOLICITARTM=" + CA_SOLICITARTM + ", CA_VALIDARESNUMERICO="
				+ CA_VALIDARESNUMERICO + ", CA_OBLIGATORIO=" + CA_OBLIGATORIO + ", CA_ACTIVO=" + CA_ACTIVO + "]";
	}

};