package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/**
 * CfgAntecedentes generated by hbm2java
 */
public class CfgAntecedentes implements java.io.Serializable {

	private int caIdantecedente;
	private String caCodigoantecedente;
	private String caDescripcion;
	private String caActivo;
	private String caObligatorio;
	private Short caOrdenamiento;
	private String caCodigoum;
	private String caFormatoum;
	private String caSolicitartm;
	private String caValidaresnumerico;
	@JsonIgnore
        private Set datosFichasantecedenteses = new HashSet(0);
	@JsonIgnore
	private Set cfgAntecedentestests = new HashSet(0);

	public CfgAntecedentes() {
	}

	public int getCaIdantecedente() {
		return this.caIdantecedente;
	}

	public void setCaIdantecedente(int caIdantecedente) {
		this.caIdantecedente = caIdantecedente;
	}

	public String getCaCodigoantecedente() {
		return this.caCodigoantecedente;
	}

	public void setCaCodigoantecedente(String caCodigoantecedente) {
		this.caCodigoantecedente = caCodigoantecedente;
	}

	public String getCaDescripcion() {
		return this.caDescripcion;
	}

	public void setCaDescripcion(String caDescripcion) {
		this.caDescripcion = caDescripcion;
	}

	public String getCaActivo() {
		return this.caActivo;
	}

	public void setCaActivo(String caActivo) {
		this.caActivo = caActivo;
	}

	public String getCaObligatorio() {
		return this.caObligatorio;
	}

	public void setCaObligatorio(String caObligatorio) {
		this.caObligatorio = caObligatorio;
	}

	public Short getCaOrdenamiento() {
		return this.caOrdenamiento;
	}

	public void setCaOrdenamiento(Short caOrdenamiento) {
		this.caOrdenamiento = caOrdenamiento;
	}

	public String getCaCodigoum() {
		return this.caCodigoum;
	}

	public void setCaCodigoum(String caCodigoum) {
		this.caCodigoum = caCodigoum;
	}

	public String getCaFormatoum() {
		return this.caFormatoum;
	}

	public void setCaFormatoum(String caFormatoum) {
		this.caFormatoum = caFormatoum;
	}

	public String getCaSolicitartm() {
		return this.caSolicitartm;
	}

	public void setCaSolicitartm(String caSolicitartm) {
		this.caSolicitartm = caSolicitartm;
	}

	public String getCaValidaresnumerico() {
		return this.caValidaresnumerico;
	}

	public void setCaValidaresnumerico(String caValidaresnumerico) {
		this.caValidaresnumerico = caValidaresnumerico;
	}

	public Set getDatosFichasantecedenteses() {
		return this.datosFichasantecedenteses;
	}

	public void setDatosFichasantecedenteses(Set datosFichasantecedenteses) {
		this.datosFichasantecedenteses = datosFichasantecedenteses;
	}

	public Set getCfgAntecedentestests() {
		return this.cfgAntecedentestests;
	}

	public void setCfgAntecedentestests(Set cfgAntecedentestests) {
		this.cfgAntecedentestests = cfgAntecedentestests;
	}

    @Override
    public String toString() {
        return "CfgAntecedentes{" + "caIdantecedente=" + caIdantecedente + '}';
    }

}
