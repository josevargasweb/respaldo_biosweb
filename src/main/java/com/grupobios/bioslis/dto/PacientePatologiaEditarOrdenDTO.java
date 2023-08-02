package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class PacientePatologiaEditarOrdenDTO {

    
    private BigDecimal IDPACIENTEPATOLOGIA;
    private BigDecimal IDPATOLOGIA;
    private String PATOLOGIA;
    private String OBSERVACION;
    
    public PacientePatologiaEditarOrdenDTO() {
    }

    public BigDecimal getIDPACIENTEPATOLOGIA() {
        return IDPACIENTEPATOLOGIA;
    }

    public void setIDPACIENTEPATOLOGIA(BigDecimal iDPACIENTEPATOLOGIA) {
        IDPACIENTEPATOLOGIA = iDPACIENTEPATOLOGIA;
    }

    public BigDecimal getIDPATOLOGIA() {
        return IDPATOLOGIA;
    }

    public void setIDPATOLOGIA(BigDecimal iDPATOLOGIA) {
        IDPATOLOGIA = iDPATOLOGIA;
    }

    public String getPATOLOGIA() {
        return PATOLOGIA;
    }

    public void setPATOLOGIA(String pATOLOGIA) {
        PATOLOGIA = pATOLOGIA;
    }

    public String getOBSERVACION() {
        return OBSERVACION;
    }

    public void setOBSERVACION(String oBSERVACION) {
        OBSERVACION = oBSERVACION;
    }
    
    
    
    
}
