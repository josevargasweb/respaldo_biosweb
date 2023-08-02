package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.Date;

/*Resalizado por cristian 12-10 ---  para modal linea de tiempo*/
public class DatosLineaTiempoDTO {

    private String NOMBREEXAMEN;
    private String NOMBRETEST;
    private Date FORDEN;
    private String NOMBREUSUARIOORDEN;
    private String APELLIDOUSUARIOORDEN;
    private Date FTOMAMUESTRA;
    private String NOMBREUSUARIOMUESTRA;
    private String APELLIDOUSUARIOMUESTRA;
    private Date  FRECEPCIONMUESTRA;
    private String NOMBREUSUARIORECEPCIONMUESTRA;
    private String APELLIDOUSUARIORECEPCIONMUESTRA;
    private Date  FINGRESORESULTADO;
    private String NOMBREUSUARIODIGITA; 
    private String APELLIDOUSUARIODIGITA; 
    private Date  FFIRMA;
    private String NOMBREUSUARIOFIRMA;
    private String APELLIDOUSUARIOFIRMA;
    private Date  FAUTORIZA;
    private String NOMBREUSUARIOAUTORIZA;
    private String APELLIDOUSUARIOAUTORIZA;
    private Date  FIMPRESION;
    private String NOMBREUSUARIOIMPRIME;
    private String APELLIDOUSUARIOIMPRIME;
    //AGREGADO POR CRISTIAN PARA PORTAL ESTADISTICA 22-11
    private BigDecimal NORDEN;
    private BigDecimal IDEXAMEN;
    private BigDecimal IDTEST;
    private Date FECHAENTREGA;
    private BigDecimal IDMUESTRA;
    private BigDecimal TIEMPOTOTAL;
    
    
    public DatosLineaTiempoDTO() {        
    }

    public String getNOMBREEXAMEN() {
        return NOMBREEXAMEN;
    }

    public void setNOMBREEXAMEN(String nOMBREEXAMEN) {
        NOMBREEXAMEN = nOMBREEXAMEN;
    }

    public String getNOMBRETEST() {
        return NOMBRETEST;
    }

    public void setNOMBRETEST(String nOMBRETEST) {
        NOMBRETEST = nOMBRETEST;
    }

    public Date getFORDEN() {
        return FORDEN;
    }

    public void setFORDEN(Date fORDEN) {
        FORDEN = fORDEN;
    }

    public String getNOMBREUSUARIOORDEN() {
        return NOMBREUSUARIOORDEN;
    }

    public void setNOMBREUSUARIOORDEN(String nOMBREUSUARIOORDEN) {
        NOMBREUSUARIOORDEN = nOMBREUSUARIOORDEN;
    }

    public String getAPELLIDOUSUARIOORDEN() {
        return APELLIDOUSUARIOORDEN;
    }

    public void setAPELLIDOUSUARIOORDEN(String aPELLIDOUSUARIOORDEN) {
        APELLIDOUSUARIOORDEN = aPELLIDOUSUARIOORDEN;
    }

    public Date getFTOMAMUESTRA() {
        return FTOMAMUESTRA;
    }

    public void setFTOMAMUESTRA(Date fTOMAMUESTRA) {
        FTOMAMUESTRA = fTOMAMUESTRA;
    }

    public String getNOMBREUSUARIOMUESTRA() {
        return NOMBREUSUARIOMUESTRA;
    }

    public void setNOMBREUSUARIOMUESTRA(String nOMBREUSUARIOMUESTRA) {
        NOMBREUSUARIOMUESTRA = nOMBREUSUARIOMUESTRA;
    }

    public String getAPELLIDOUSUARIOMUESTRA() {
        return APELLIDOUSUARIOMUESTRA;
    }

    public void setAPELLIDOUSUARIOMUESTRA(String aPELLIDOUSUARIOMUESTRA) {
        APELLIDOUSUARIOMUESTRA = aPELLIDOUSUARIOMUESTRA;
    }

    public Date getFRECEPCIONMUESTRA() {
        return FRECEPCIONMUESTRA;
    }

    public void setFRECEPCIONMUESTRA(Date fRECEPCIONMUESTRA) {
        FRECEPCIONMUESTRA = fRECEPCIONMUESTRA;
    }

    public String getNOMBREUSUARIORECEPCIONMUESTRA() {
        return NOMBREUSUARIORECEPCIONMUESTRA;
    }

    public void setNOMBREUSUARIORECEPCIONMUESTRA(String nOMBREUSUARIORECEPCIONMUESTRA) {
        NOMBREUSUARIORECEPCIONMUESTRA = nOMBREUSUARIORECEPCIONMUESTRA;
    }

    public String getAPELLIDOUSUARIORECEPCIONMUESTRA() {
        return APELLIDOUSUARIORECEPCIONMUESTRA;
    }

    public void setAPELLIDOUSUARIORECEPCIONMUESTRA(String aPELLIDOUSUARIORECEPCIONMUESTRA) {
        APELLIDOUSUARIORECEPCIONMUESTRA = aPELLIDOUSUARIORECEPCIONMUESTRA;
    }

    public Date getFINGRESORESULTADO() {
        return FINGRESORESULTADO;
    }

    public void setFINGRESORESULTADO(Date fINGRESORESULTADO) {
        FINGRESORESULTADO = fINGRESORESULTADO;
    }

    public String getNOMBREUSUARIODIGITA() {
        return NOMBREUSUARIODIGITA;
    }

    public void setNOMBREUSUARIODIGITA(String nOMBREUSUARIODIGITA) {
        NOMBREUSUARIODIGITA = nOMBREUSUARIODIGITA;
    }

    public String getAPELLIDOUSUARIODIGITA() {
        return APELLIDOUSUARIODIGITA;
    }

    public void setAPELLIDOUSUARIODIGITA(String aPELLIDOUSUARIODIGITA) {
        APELLIDOUSUARIODIGITA = aPELLIDOUSUARIODIGITA;
    }

    public Date getFFIRMA() {
        return FFIRMA;
    }

    public void setFFIRMA(Date fFIRMA) {
        FFIRMA = fFIRMA;
    }

    public String getNOMBREUSUARIOFIRMA() {
        return NOMBREUSUARIOFIRMA;
    }

    public void setNOMBREUSUARIOFIRMA(String nOMBREUSUARIOFIRMA) {
        NOMBREUSUARIOFIRMA = nOMBREUSUARIOFIRMA;
    }

    public String getAPELLIDOUSUARIOFIRMA() {
        return APELLIDOUSUARIOFIRMA;
    }

    public void setAPELLIDOUSUARIOFIRMA(String aPELLIDOUSUARIOFIRMA) {
        APELLIDOUSUARIOFIRMA = aPELLIDOUSUARIOFIRMA;
    }

    public Date getFAUTORIZA() {
        return FAUTORIZA;
    }

    public void setFAUTORIZA(Date fAUTORIZA) {
        FAUTORIZA = fAUTORIZA;
    }

    public String getNOMBREUSUARIOAUTORIZA() {
        return NOMBREUSUARIOAUTORIZA;
    }

    public void setNOMBREUSUARIOAUTORIZA(String nOMBREUSUARIOAUTORIZA) {
        NOMBREUSUARIOAUTORIZA = nOMBREUSUARIOAUTORIZA;
    }

    public String getAPELLIDOUSUARIOAUTORIZA() {
        return APELLIDOUSUARIOAUTORIZA;
    }

    public void setAPELLIDOUSUARIOAUTORIZA(String aPELLIDOUSUARIOAUTORIZA) {
        APELLIDOUSUARIOAUTORIZA = aPELLIDOUSUARIOAUTORIZA;
    }

    public Date getFIMPRESION() {
        return FIMPRESION;
    }

    public void setFIMPRESION(Date fIMPRESION) {
        FIMPRESION = fIMPRESION;
    }

    public String getNOMBREUSUARIOIMPRIME() {
        return NOMBREUSUARIOIMPRIME;
    }

    public void setNOMBREUSUARIOIMPRIME(String nOMBREUSUARIOIMPRIME) {
        NOMBREUSUARIOIMPRIME = nOMBREUSUARIOIMPRIME;
    }

    public String getAPELLIDOUSUARIOIMPRIME() {
        return APELLIDOUSUARIOIMPRIME;
    }

    public void setAPELLIDOUSUARIOIMPRIME(String aPELLIDOUSUARIOIMPRIME) {
        APELLIDOUSUARIOIMPRIME = aPELLIDOUSUARIOIMPRIME;
    }

    public BigDecimal getNORDEN() {
        return NORDEN;
    }

    public void setNORDEN(BigDecimal nORDEN) {
        NORDEN = nORDEN;
    }

    public BigDecimal getIDEXAMEN() {
        return IDEXAMEN;
    }

    public void setIDEXAMEN(BigDecimal iDEXAMEN) {
        IDEXAMEN = iDEXAMEN;
    }

    public BigDecimal getIDTEST() {
        return IDTEST;
    }

    public void setIDTEST(BigDecimal iDTEST) {
        IDTEST = iDTEST;
    }

    public Date getFECHAENTREGA() {
        return FECHAENTREGA;
    }

    public void setFECHAENTREGA(Date fECHAENTREGA) {
        FECHAENTREGA = fECHAENTREGA;
    }

    public BigDecimal getIDMUESTRA() {
        return IDMUESTRA;
    }

    public void setIDMUESTRA(BigDecimal iDMUESTRA) {
        IDMUESTRA = iDMUESTRA;
    }

    public BigDecimal getTIEMPOTOTAL() {
        return TIEMPOTOTAL;
    }

    public void setTIEMPOTOTAL(BigDecimal tIEMPOTOTAL) {
        TIEMPOTOTAL = tIEMPOTOTAL;
    }

   
   
    
    
    
}
