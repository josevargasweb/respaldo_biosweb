package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class TransporteMuestrasDTO {

	private BigDecimal IDMUESTRA; // nMuestra
	private BigDecimal IDTIPOMUESTRA;
	private BigDecimal IDENVASE;
	private BigDecimal IDUSUARIOCREACION;

	private String CODIGOESTADO;
	private String CODIGOBARRA;
	private String TIPOMUESTRA;
	private String CONTENEDOR;
	private String NOMBREFLEBOTOMISTA;
	private String ESTADOTM;

	public BigDecimal getIDMUESTRA() {
		return IDMUESTRA;
	}

	public void setIDMUESTRA(BigDecimal iDMUESTRA) {
		IDMUESTRA = iDMUESTRA;
	}

	public BigDecimal getIDTIPOMUESTRA() {
		return IDTIPOMUESTRA;
	}

	public void setIDTIPOMUESTRA(BigDecimal iDTIPOMUESTRA) {
		IDTIPOMUESTRA = iDTIPOMUESTRA;
	}

	public BigDecimal getIDENVASE() {
		return IDENVASE;
	}

	public void setIDENVASE(BigDecimal iDENVASE) {
		IDENVASE = iDENVASE;
	}

	public BigDecimal getIDUSUARIOCREACION() {
		return IDUSUARIOCREACION;
	}

	public void setIDUSUARIOCREACION(BigDecimal iDUSUARIOCREACION) {
		IDUSUARIOCREACION = iDUSUARIOCREACION;
	}

	public String getCODIGOESTADO() {
		return CODIGOESTADO;
	}

	public void setCODIGOESTADO(String cODIGOESTADO) {
		CODIGOESTADO = cODIGOESTADO;
	}

	public String getCODIGOBARRA() {
		return CODIGOBARRA;
	}

	public void setCODIGOBARRA(String cODIGOBARRA) {
		CODIGOBARRA = cODIGOBARRA;
	}

	public String getTIPOMUESTRA() {
		return TIPOMUESTRA;
	}

	public void setTIPOMUESTRA(String tIPOMUESTRA) {
		TIPOMUESTRA = tIPOMUESTRA;
	}

	public String getCONTENEDOR() {
		return CONTENEDOR;
	}

	public void setCONTENEDOR(String cONTENEDOR) {
		CONTENEDOR = cONTENEDOR;
	}

	public String getNOMBREFLEBOTOMISTA() {
		return NOMBREFLEBOTOMISTA;
	}

	public void setNOMBREFLEBOTOMISTA(String nOMBREFLEBOTOMISTA) {
		NOMBREFLEBOTOMISTA = nOMBREFLEBOTOMISTA;
	}

	public String getESTADOTM() {
		return ESTADOTM;
	}

	public void setESTADOTM(String eSTADOTM) {
		ESTADOTM = eSTADOTM;
	}

}
