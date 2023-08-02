package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.Date;

/*Resalizado por cristian ---  para portal de pacientes*/
public class PortalExamenesDTO {

	 private BigDecimal IDEXAMEN;
	 private String NOMBRE;
	 private String ESTADO;
	 private String MUESTRA;
	 private String DISPONIBLEWEB;
	 private String NOMBREWEB;
	 private String EXAMENANULADO;
	 private String MUESTRARECEPCION;
	 private String ESTADOMUESTRA;
	 //AGREGADO POR CRISTIAN 01-12
	 private BigDecimal IDDERIVADOR;
	 //agregado por jan 19-04-23
	 private Date FECHAWEBDISPONIBLE;
	
	public BigDecimal getIDEXAMEN() {
		return IDEXAMEN;
	}
	public void setIDEXAMEN(BigDecimal iDEXAMEN) {
		IDEXAMEN = iDEXAMEN;
	}
	public String getNOMBRE() {
		return NOMBRE;
	}
	public void setNOMBRE(String nOMBRE) {
		NOMBRE = nOMBRE;
	}
	public String getESTADO() {
		return ESTADO;
	}
	public void setESTADO(String eSTADO) {
		ESTADO = eSTADO;
	}
	public String getMUESTRA() {
		return MUESTRA;
	}
	public void setMUESTRA(String mUESTRA) {
		MUESTRA = mUESTRA;
	}	 
	 
	public String getDISPONIBLEWEB() {
		return DISPONIBLEWEB;
	}

	public void setDISPONIBLEWEB(String dISPONIBLEWEB) {
		DISPONIBLEWEB = dISPONIBLEWEB;
	}

	public String getNOMBREWEB() {
		return NOMBREWEB;
	}

	public void setNOMBREWEB(String nOMBREWEB) {
		NOMBREWEB = nOMBREWEB;
	}
    public String getEXAMENANULADO() {
        return EXAMENANULADO;
    }
    public void setEXAMENANULADO(String eXAMENANULADO) {
        EXAMENANULADO = eXAMENANULADO;
    }
    public String getMUESTRARECEPCION() {
        return MUESTRARECEPCION;
    }
    public void setMUESTRARECEPCION(String mUESTRARECEPCION) {
        MUESTRARECEPCION = mUESTRARECEPCION;
    }
    public String getESTADOMUESTRA() {
        return ESTADOMUESTRA;
    }
    public void setESTADOMUESTRA(String eSTADOMUESTRA) {
        ESTADOMUESTRA = eSTADOMUESTRA;
    }
  //AGREGADO POR CRISTIAN 01-12
    public BigDecimal getIDDERIVADOR() {
        return IDDERIVADOR;
    }
    public void setIDDERIVADOR(BigDecimal iDDERIVADOR) {
        IDDERIVADOR = iDDERIVADOR;
    }
	public Date getFECHAWEBDISPONIBLE() {
		return FECHAWEBDISPONIBLE;
	}
	public void setFECHAWEBDISPONIBLE(Date fECHAWEBDISPONIBLE) {
		FECHAWEBDISPONIBLE = fECHAWEBDISPONIBLE;
	}
   
    
    
    
}
