/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.common.Edad;

import java.math.BigDecimal;

/**
 *
 * @author marco.c
 */
public class OrdenesTMDTO {
	private BigDecimal NORDEN;
	private BigDecimal IDPACIENTE;
	private String FECHAORDEN;
	private String NOMBRES;
	private String FECHANAC;
	private String COLORPRIO;
	private String PROCEDENCIA;
	private String SERVICIO;
	private String ESTADO;
	private String OBSERVACION;
	private String PRIORIDADATENCION;
	private BigDecimal PRIORIDAD;
	private String TIPOATENCION;
	private String TMCLICK;
	private BigDecimal IDSERVICIO;
	// agregado por jan 13-06-2023
	private String ESTADOANTECEDENTETM;

	public String getESTADOANTECEDENTETM() {
		return ESTADOANTECEDENTETM;
	}

	public void setESTADOANTECEDENTETM(String eSTADOANTECEDENTETM) {
		ESTADOANTECEDENTETM = eSTADOANTECEDENTETM;
	}

	public BigDecimal getNORDEN() {
		return NORDEN;
	}

	public void setNORDEN(BigDecimal NORDEN) {
		this.NORDEN = NORDEN;
	}

	public BigDecimal getIDPACIENTE() {
		return IDPACIENTE;
	}

	public void setIDPACIENTE(BigDecimal IDPACIENTE) {
		this.IDPACIENTE = IDPACIENTE;
	}

	public String getFECHAORDEN() {
		return FECHAORDEN;
	}

	public void setFECHAORDEN(String FECHAORDEN) {
		this.FECHAORDEN = FECHAORDEN;
	}

	public String getNOMBRES() {
		return NOMBRES;
	}

	public void setNOMBRES(String NOMBRES) {
		this.NOMBRES = NOMBRES;
	}

	public String getFECHANAC() {
		return FECHANAC;
	}

	public void setFECHANAC(String FECHANAC) {
		this.FECHANAC = FECHANAC;
	}

	public String getCOLORPRIO() {
		return COLORPRIO;
	}

	public void setCOLORPRIO(String COLORPRIO) {
		this.COLORPRIO = COLORPRIO;
	}

	public String getPROCEDENCIA() {
		return PROCEDENCIA;
	}

	public void setPROCEDENCIA(String PROCEDENCIA) {
		this.PROCEDENCIA = PROCEDENCIA;
	}

	public String getSERVICIO() {
		return SERVICIO;
	}

	public void setSERVICIO(String SERVICIO) {
		this.SERVICIO = SERVICIO;
	}

	public String getESTADO() {
		return ESTADO;
	}

	public void setESTADO(String ESTADO) {
		this.ESTADO = ESTADO;
	}

	public String getOBSERVACION() {
		return OBSERVACION;
	}

	public void setOBSERVACION(String OBSERVACION) {
		this.OBSERVACION = OBSERVACION;
	}

	public String getPRIORIDADATENCION() {
		return PRIORIDADATENCION;
	}

	public void setPRIORIDADATENCION(String PRIORIDADATENCION) {
		this.PRIORIDADATENCION = PRIORIDADATENCION;
	}

	public BigDecimal getPRIORIDAD() {
		return PRIORIDAD;
	}

	public void setPRIORIDAD(BigDecimal PRIORIDAD) {
		this.PRIORIDAD = PRIORIDAD;
	}

	public String getTIPOATENCION() {
		return TIPOATENCION;
	}

	public void setTIPOATENCION(String TIPOATENCION) {
		this.TIPOATENCION = TIPOATENCION;
	}

	public String getTMCLICK() {
		return TMCLICK;
	}

	public void setTMCLICK(String TMCLICK) {
		this.TMCLICK = TMCLICK;
	}

	public BigDecimal getIDSERVICIO() {
		return IDSERVICIO;
	}

	public void setIDSERVICIO(BigDecimal IDSERVICIO) {
		this.IDSERVICIO = IDSERVICIO;
	}

	/*
	 * public String getEdad() throws BiosLISException { return
	 * Edad.getEdadActual(FECHANAC).toString(); }
	 */

	@Override
	public String toString() {
		return "OrdenesTMDTO{" + "NORDEN=" + NORDEN + ", IDPACIENTE=" + IDPACIENTE + ", FECHAORDEN=" + FECHAORDEN
				+ ", NOMBRES=" + NOMBRES + ", FECHANAC=" + FECHANAC + ", COLORPRIO=" + COLORPRIO + ", PROCEDENCIA="
				+ PROCEDENCIA + ", SERVICIO=" + SERVICIO + ", ESTADO=" + ESTADO + ", OBSERVACION=" + OBSERVACION
				+ ", PRIORIDADATENCION=" + PRIORIDADATENCION + ", PRIORIDAD=" + PRIORIDAD + ", TIPOATENCION="
				+ TIPOATENCION + ", TMCLICK=" + TMCLICK + '}';
	}

}
