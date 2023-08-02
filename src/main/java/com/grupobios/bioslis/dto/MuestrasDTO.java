/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.sql.Blob;

/**
 *
 * @author marco.caracciolo
 */
public class MuestrasDTO {

	private BigDecimal NORDEN;
	private BigDecimal IDMUESTRA;
	private BigDecimal IDTIPOMUESTRA;
	private BigDecimal IDENVASE;
	private BigDecimal IDEXAMEN;
	private String CODIGOBARRAS;
	private String PREFIJO;
	private String FECHATM;
	private String FECHARM;
	private String MUESTRAABREV;
	private String MUESTRADESC;
	private String ENVASEDESC;
	private String USRFLEBOTOMISTA;
	private String USRRECEPTOR;
	private String ESTADOTM;
	private String ESTADORM;
	private String OBSERVACIONTM;
	private String OBSERVACIONRM;
	private String COMPARTEMUESTRA;
	private BigDecimal IDDERIVADOR;
	private String DERIVADOR;
	private BigDecimal NROVECESTOMADA;
	private BigDecimal IDZONACUERPO;
	private String ZONACUERPO;
	private String ESCURVATOLERANCIA;
	private BigDecimal IDUSRFLEBOTOMISTA;
	private BigDecimal IDUSRRECEPTOR;
	private String ALLEXAMENESANULADOS;
	private BigDecimal IDPACIENTE;
	// agregado x jan
	private String IMGENVASE;
	//AGREGADO X JAN 14-06-2023
	private String PUEDECAMBIARMUESTRA;
	
	

	public BigDecimal getIDEXAMEN() {
		return IDEXAMEN;
	}

	public void setIDEXAMEN(BigDecimal iDEXAMEN) {
		IDEXAMEN = iDEXAMEN;
	}

	public String getPUEDECAMBIARMUESTRA() {
		return PUEDECAMBIARMUESTRA;
	}

	public void setPUEDECAMBIARMUESTRA(String pUEDECAMBIARMUESTRA) {
		PUEDECAMBIARMUESTRA = pUEDECAMBIARMUESTRA;
	}

	public BigDecimal getNORDEN() {
		return NORDEN;
	}

	public void setNORDEN(BigDecimal NORDEN) {
		this.NORDEN = NORDEN;
	}

	public BigDecimal getIDMUESTRA() {
		return IDMUESTRA;
	}

	public void setIDMUESTRA(BigDecimal IDMUESTRA) {
		this.IDMUESTRA = IDMUESTRA;
	}

	public BigDecimal getIDTIPOMUESTRA() {
		return IDTIPOMUESTRA;
	}

	public void setIDTIPOMUESTRA(BigDecimal IDTIPOMUESTRA) {
		this.IDTIPOMUESTRA = IDTIPOMUESTRA;
	}

	public BigDecimal getIDENVASE() {
		return IDENVASE;
	}

	public void setIDENVASE(BigDecimal IDENVASE) {
		this.IDENVASE = IDENVASE;
	}

	public String getCODIGOBARRAS() {
		return CODIGOBARRAS;
	}

	public void setCODIGOBARRAS(String CODIGOBARRAS) {
		this.CODIGOBARRAS = CODIGOBARRAS;
	}

	public String getPREFIJO() {
		return PREFIJO;
	}

	public void setPREFIJO(String PREFIJO) {
		this.PREFIJO = PREFIJO;
	}

	public String getFECHATM() {
		return FECHATM;
	}

	public void setFECHATM(String FECHATM) {
		this.FECHATM = FECHATM;
	}

	public String getFECHARM() {
		return FECHARM;
	}

	public void setFECHARM(String FECHARM) {
		this.FECHARM = FECHARM;
	}

	public String getMUESTRAABREV() {
		return MUESTRAABREV;
	}

	public void setMUESTRAABREV(String MUESTRAABREV) {
		this.MUESTRAABREV = MUESTRAABREV;
	}

	public String getMUESTRADESC() {
		return MUESTRADESC;
	}

	public void setMUESTRADESC(String MUESTRADESC) {
		this.MUESTRADESC = MUESTRADESC;
	}

	public String getENVASEDESC() {
		return ENVASEDESC;
	}

	public void setENVASEDESC(String ENVASEDESC) {
		this.ENVASEDESC = ENVASEDESC;
	}

	public String getUSRFLEBOTOMISTA() {
		return USRFLEBOTOMISTA;
	}

	public void setUSRFLEBOTOMISTA(String USRFLEBOTOMISTA) {
		this.USRFLEBOTOMISTA = USRFLEBOTOMISTA;
	}

	public String getUSRRECEPTOR() {
		return USRRECEPTOR;
	}

	public void setUSRRECEPTOR(String USRRECEPTOR) {
		this.USRRECEPTOR = USRRECEPTOR;
	}

	public String getESTADOTM() {
		return ESTADOTM;
	}

	public void setESTADOTM(String ESTADOTM) {
		this.ESTADOTM = ESTADOTM;
	}

	public String getESTADORM() {
		return ESTADORM;
	}

	public void setESTADORM(String ESTADORM) {
		this.ESTADORM = ESTADORM;
	}

	public String getOBSERVACIONTM() {
		return OBSERVACIONTM;
	}

	public void setOBSERVACIONTM(String OBSERVACIONTM) {
		this.OBSERVACIONTM = OBSERVACIONTM;
	}

	public String getOBSERVACIONRM() {
		return OBSERVACIONRM;
	}

	public void setOBSERVACIONRM(String OBSERVACIONRM) {
		this.OBSERVACIONRM = OBSERVACIONRM;
	}

	public String getCOMPARTEMUESTRA() {
		return COMPARTEMUESTRA;
	}

	public void setCOMPARTEMUESTRA(String COMPARTEMUESTRA) {
		this.COMPARTEMUESTRA = COMPARTEMUESTRA;
	}

	public BigDecimal getIDDERIVADOR() {
		return IDDERIVADOR;
	}

	public void setIDDERIVADOR(BigDecimal IDDERIVADOR) {
		this.IDDERIVADOR = IDDERIVADOR;
	}

	public String getDERIVADOR() {
		return DERIVADOR;
	}

	public void setDERIVADOR(String DERIVADOR) {
		this.DERIVADOR = DERIVADOR;
	}

	public BigDecimal getNROVECESTOMADA() {
		return NROVECESTOMADA;
	}

	public void setNROVECESTOMADA(BigDecimal NROVECESTOMADA) {
		this.NROVECESTOMADA = NROVECESTOMADA;
	}

	public BigDecimal getIDZONACUERPO() {
		return IDZONACUERPO;
	}

	public void setIDZONACUERPO(BigDecimal IDZONACUERPO) {
		this.IDZONACUERPO = IDZONACUERPO;
	}

	public String getZONACUERPO() {
		return ZONACUERPO;
	}

	public void setZONACUERPO(String ZONACUERPO) {
		this.ZONACUERPO = ZONACUERPO;
	}

	public String getESCURVATOLERANCIA() {
		return ESCURVATOLERANCIA;
	}

	public void setESCURVATOLERANCIA(String ESCURVATOLERANCIA) {
		this.ESCURVATOLERANCIA = ESCURVATOLERANCIA;
	}

	public BigDecimal getIDUSRFLEBOTOMISTA() {
		return IDUSRFLEBOTOMISTA;
	}

	public void setIDUSRFLEBOTOMISTA(BigDecimal IDUSRFLEBOTOMISTA) {
		this.IDUSRFLEBOTOMISTA = IDUSRFLEBOTOMISTA;
	}

	public BigDecimal getIDUSRRECEPTOR() {
		return IDUSRRECEPTOR;
	}

	public void setIDUSRRECEPTOR(BigDecimal IDUSRRECEPTOR) {
		this.IDUSRRECEPTOR = IDUSRRECEPTOR;
	}

	public String getALLEXAMENESANULADOS() {
		return ALLEXAMENESANULADOS;
	}

	public void setALLEXAMENESANULADOS(String ALLEXAMENESANULADOS) {
		this.ALLEXAMENESANULADOS = ALLEXAMENESANULADOS;
	}

	public BigDecimal getIDPACIENTE() {
		return IDPACIENTE;
	}

	public void setIDPACIENTE(BigDecimal IDPACIENTE) {
		this.IDPACIENTE = IDPACIENTE;
	}

	public String getIMGENVASE() {
		return IMGENVASE;
	}

	public void setIMGENVASE(String iMGENVASE) {
		IMGENVASE = iMGENVASE;
	}

	@Override
	public String toString() {
		return "MuestrasDTO{" + "NORDEN=" + NORDEN + ", IDMUESTRA=" + IDMUESTRA + ", IDTIPOMUESTRA=" + IDTIPOMUESTRA
				+ ", IDENVASE=" + IDENVASE + ", CODIGOBARRAS=" + CODIGOBARRAS + ", PREFIJO=" + PREFIJO + ", FECHATM="
				+ FECHATM + ", FECHARM=" + FECHARM + ", MUESTRADESC=" + MUESTRADESC + ", ENVASEDESC=" + ENVASEDESC
				+ ", USRFLEBOTOMISTA=" + USRFLEBOTOMISTA + ", USRRECEPTOR=" + USRRECEPTOR + ", ESTADOTM=" + ESTADOTM
				+ ", ESTADORM=" + ESTADORM + ", OBSERVACIONTM=" + OBSERVACIONTM + ", OBSERVACIONRM=" + OBSERVACIONRM
				+ ", COMPARTEMUESTRA=" + COMPARTEMUESTRA + ", IDDERIVADOR=" + IDDERIVADOR + ", DERIVADOR=" + DERIVADOR
				+ ", NROVECESTOMADA=" + NROVECESTOMADA + ", IDZONACUERPO=" + IDZONACUERPO + ", ZONACUERPO=" + ZONACUERPO
				+ ", ESCURVATOLERANCIA=" + ESCURVATOLERANCIA + ", IDUSRFLEBOTOMISTA=" + IDUSRFLEBOTOMISTA
				+ ", IDUSRRECEPTOR=" + IDUSRRECEPTOR + ", ALLEXAMENESANULADOS=" + ALLEXAMENESANULADOS + ", IDPACIENTE="
				+ IDPACIENTE + '}';
	}

}
