package com.grupobios.bioslis.microbiologia.dto;

import java.math.BigDecimal;

public class MicrobiologiaTestDTO {
	private String TEST;
	private BigDecimal ORDERID;
	private String MICROBIOLOGYSTATUS;
	private BigDecimal IDMICROBIOLOGYSTATUS;
	private String ISCULTURE;
	private String RESULT;
	private String SAMPLECODE;
	private BigDecimal EXAMID;
	private BigDecimal TESTID;
	private BigDecimal ID;
	private String RESULTTYPE;
	private String LABELCODE;
	private String STATUS;
	private BigDecimal IDSTATUS;
	private String CULTIVO;
	private String ESTADO;
	private String CTRCODIGO;
	private String RESULTADOOBLIGADO;
	private String RESULTADOMISION;
	private BigDecimal DECIMALES;
	private BigDecimal RESULTADONUMERICO;
        //agregado por marco
        private String RESULTADOCRITICO;
	
	
	

	public String getRESULTADOOBLIGADO() {
		return RESULTADOOBLIGADO;
	}
	public void setRESULTADOOBLIGADO(String rESULTADOOBLIGADO) {
		RESULTADOOBLIGADO = rESULTADOOBLIGADO;
	}
	public String getRESULTADOMISION() {
		return RESULTADOMISION;
	}
	public void setRESULTADOMISION(String rESULTADOMISION) {
		RESULTADOMISION = rESULTADOMISION;
	}
	public BigDecimal getDECIMALES() {
		return DECIMALES;
	}
	public void setDECIMALES(BigDecimal dECIMALES) {
		DECIMALES = dECIMALES;
	}
	public BigDecimal getRESULTADONUMERICO() {
		return RESULTADONUMERICO;
	}
	public void setRESULTADONUMERICO(BigDecimal rESULTADONUMERICO) {
		RESULTADONUMERICO = rESULTADONUMERICO;
	}
	public String getCTRCODIGO() {
		return CTRCODIGO;
	}
	public void setCTRCODIGO(String cTRCODIGO) {
		CTRCODIGO = cTRCODIGO;
	}
	public String getMICROBIOLOGYSTATUS() {
		return MICROBIOLOGYSTATUS;
	}
	public void setMICROBIOLOGYSTATUS(String mICROBIOLOGYSTATUS) {
		MICROBIOLOGYSTATUS = mICROBIOLOGYSTATUS;
	}
	public String getTEST() {
		return TEST;
	}
	public void setTEST(String tEST) {
		TEST = tEST;
	}
	
	public BigDecimal getIDSTATUS() {
		return IDSTATUS;
	}
	public void setIDSTATUS(BigDecimal iDSTATUS) {
		IDSTATUS = iDSTATUS;
	}
	public BigDecimal getIDMICROBIOLOGYSTATUS() {
		return IDMICROBIOLOGYSTATUS;
	}
	public void setIDMICROBIOLOGYSTATUS(BigDecimal iDMICROBIOLOGYSTATUS) {
		IDMICROBIOLOGYSTATUS = iDMICROBIOLOGYSTATUS;
	}
	public BigDecimal getORDERID() {
		return ORDERID;
	}
	public void setORDERID(BigDecimal oRDERID) {
		ORDERID = oRDERID;
	}
	public String getISCULTURE() {
		return ISCULTURE;
	}
	public void setISCULTURE(String iSCULTURE) {
		ISCULTURE = iSCULTURE;
	}
	public String getRESULT() {
		return RESULT;
	}
	public void setRESULT(String rESULT) {
		RESULT = rESULT;
	}
	public String getSAMPLECODE() {
		return SAMPLECODE;
	}
	public void setSAMPLECODE(String sAMPLECODE) {
		SAMPLECODE = sAMPLECODE;
	}
	public BigDecimal getEXAMID() {
		return EXAMID;
	}
	public void setEXAMID(BigDecimal eXAMID) {
		EXAMID = eXAMID;
	}
	public BigDecimal getTESTID() {
		return TESTID;
	}
	public void setTESTID(BigDecimal tESTID) {
		TESTID = tESTID;
	}
	public BigDecimal getID() {
		return ID;
	}
	public void setID(BigDecimal iD) {
		ID = iD;
	}
	public String getRESULTTYPE() {
		return RESULTTYPE;
	}
	public void setRESULTTYPE(String rESULTTYPE) {
		RESULTTYPE = rESULTTYPE;
	}
	public String getLABELCODE() {
		return LABELCODE;
	}
	public void setLABELCODE(String lABELCODE) {
		LABELCODE = lABELCODE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getCULTIVO() {
		return CULTIVO;
	}
	public void setCULTIVO(String cULTIVO) {
		CULTIVO = cULTIVO;
	}
	public String getESTADO() {
		return ESTADO;
	}
	public void setESTADO(String eSTADO) {
		ESTADO = eSTADO;
	}

        public String getRESULTADOCRITICO() {
            return RESULTADOCRITICO;
        }

        public void setRESULTADOCRITICO(String RESULTADOCRITICO) {
            this.RESULTADOCRITICO = RESULTADOCRITICO;
        }

	

}
