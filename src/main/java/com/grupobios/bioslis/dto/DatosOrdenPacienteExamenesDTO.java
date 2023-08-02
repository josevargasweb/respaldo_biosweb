/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.common.Edad;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class DatosOrdenPacienteExamenesDTO {
	private String NOMBRES;
	private String SEXO;
	private Date FECHA_NAC;
	private String TIPO_ATENCION;
	private String SERVICIO;
	private String PROCEDENCIA;
	private BigDecimal NORDEN;
	private String FECHA_ORDEN;
	private String OBS_ORDEN;
	private String FECHA_TM;
	private String OBS_TM;
	private List<ExamenOrdenDTO> LIST_EXAMENES;
	// agregado por jan 15-06-2023
	private String ESTADOANTECEDENTERM;

	public String getESTADOANTECEDENTERM() {
		return ESTADOANTECEDENTERM;
	}

	public void setESTADOANTECEDENTERM(String eSTADOANTECEDENTERM) {
		ESTADOANTECEDENTERM = eSTADOANTECEDENTERM;
	}

	public String getNOMBRES() {
		return NOMBRES;
	}

	public void setNOMBRES(String NOMBRES) {
		this.NOMBRES = NOMBRES;
	}

	public String getSEXO() {
		return SEXO;
	}

	public void setSEXO(String SEXO) {
		this.SEXO = SEXO;
	}

	public Date getFECHA_NAC() {
		return FECHA_NAC;
	}

	public void setFECHA_NAC(Date FECHA_NAC) {
		this.FECHA_NAC = FECHA_NAC;
	}

	public String getTIPO_ATENCION() {
		return TIPO_ATENCION;
	}

	public void setTIPO_ATENCION(String TIPO_ATENCION) {
		this.TIPO_ATENCION = TIPO_ATENCION;
	}

	public String getSERVICIO() {
		return SERVICIO;
	}

	public void setSERVICIO(String SERVICIO) {
		this.SERVICIO = SERVICIO;
	}

	public String getPROCEDENCIA() {
		return PROCEDENCIA;
	}

	public void setPROCEDENCIA(String PROCEDENCIA) {
		this.PROCEDENCIA = PROCEDENCIA;
	}

	public BigDecimal getNORDEN() {
		return NORDEN;
	}

	public void setNORDEN(BigDecimal NORDEN) {
		this.NORDEN = NORDEN;
	}

	public String getFECHA_ORDEN() {
		return FECHA_ORDEN;
	}

	public void setFECHA_ORDEN(String FECHA_ORDEN) {
		this.FECHA_ORDEN = FECHA_ORDEN;
	}

	public String getOBS_ORDEN() {
		return OBS_ORDEN;
	}

	public void setOBS_ORDEN(String OBS_ORDEN) {
		this.OBS_ORDEN = OBS_ORDEN;
	}

	public String getFECHA_TM() {
		return FECHA_TM;
	}

	public void setFECHA_TM(String FECHA_TM) {
		this.FECHA_TM = FECHA_TM;
	}

	public String getOBS_TM() {
		return OBS_TM;
	}

	public void setOBS_TM(String OBS_TM) {
		this.OBS_TM = OBS_TM;
	}

	public List<ExamenOrdenDTO> getLIST_EXAMENES() {
		return LIST_EXAMENES;
	}

	public void setLIST_EXAMENES(List<ExamenOrdenDTO> LIST_EXAMENES) {
		this.LIST_EXAMENES = LIST_EXAMENES;
	}

	public String getEdad() {
		if (this.FECHA_NAC != null) {
			return Edad.getEdadActual(FECHA_NAC).toString();
		}
		return null;
	}

}
