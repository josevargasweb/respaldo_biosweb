package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

//***creado por cristian ** se pueden agregar mas datos.
public class TestEdicionOrdenDTO {

    
    private BigDecimal IDTEST;
    private BigDecimal IDEXAMEN;
    private BigDecimal IDTIPOMUESTRA;
    private BigDecimal IDENVASE;
    private BigDecimal IDSECCION;
    private BigDecimal IDMETODO;
    private String VALORPORDEFECTO;
    //AGREGADO POR CRISTIAN 21-11
    private BigDecimal IDMUESTRA;
    private String CODIGO;
    private String TESTABREVIADO;
    private String RESULTADO;
    private String VALORCRITICODESDE;
    private String VALORCRITICOHASTA;
    private String CONCLUSION;
    private String FORDEN;
    private String FFIRMA;
    private String NOMBRE;
    
    
    public TestEdicionOrdenDTO() {
     }

    
    
    
    public BigDecimal getIDENVASE() {
        return IDENVASE;
    }




    public void setIDENVASE(BigDecimal iDENVASE) {
        IDENVASE = iDENVASE;
    }




    public BigDecimal getIDTEST() {
        return IDTEST;
    }

    public void setIDTEST(BigDecimal iDTEST) {
        IDTEST = iDTEST;
    }

    public BigDecimal getIDEXAMEN() {
        return IDEXAMEN;
    }

    public void setIDEXAMEN(BigDecimal iDEXAMEN) {
        IDEXAMEN = iDEXAMEN;
    }

    public BigDecimal getIDTIPOMUESTRA() {
        return IDTIPOMUESTRA;
    }

    public void setIDTIPOMUESTRA(BigDecimal iDTIPOMUESTRA) {
        IDTIPOMUESTRA = iDTIPOMUESTRA;
    }

    public BigDecimal getIDSECCION() {
        return IDSECCION;
    }

    public void setIDSECCION(BigDecimal iDSECCION) {
        IDSECCION = iDSECCION;
    }

    public BigDecimal getIDMETODO() {
        return IDMETODO;
    }

    public void setIDMETODO(BigDecimal iDMETODO) {
        IDMETODO = iDMETODO;
    }

    public String getVALORPORDEFECTO() {
        return VALORPORDEFECTO;
    }

    public void setVALORPORDEFECTO(String vALORPORDEFECTO) {
        VALORPORDEFECTO = vALORPORDEFECTO;
    }

    @Override
    public String toString() {
        return "TestEdicionOrdenDTO [IDTEST=" + IDTEST + ", IDEXAMEN=" + IDEXAMEN + ", IDTIPOMUESTRA=" + IDTIPOMUESTRA
                + ", IDSECCION=" + IDSECCION + ", IDMETODO=" + IDMETODO + ", VALORPORDEFECTO=" + VALORPORDEFECTO + "]";
    }



//AGREGADO 21-11 POR CRISTIAN
    public BigDecimal getIDMUESTRA() {
        return IDMUESTRA;
    }




    public void setIDMUESTRA(BigDecimal iDMUESTRA) {
        IDMUESTRA = iDMUESTRA;
    }




    public String getCODIGO() {
        return CODIGO;
    }




    public void setCODIGO(String cODIGO) {
        CODIGO = cODIGO;
    }




    public String getTESTABREVIADO() {
        return TESTABREVIADO;
    }




    public void setTESTABREVIADO(String tESTABREVIADO) {
        TESTABREVIADO = tESTABREVIADO;
    }




    public String getCONCLUSION() {
        return CONCLUSION;
    }




    public void setCONCLUSION(String cONCLUSION) {
        CONCLUSION = cONCLUSION;
    }




    public String getFORDEN() {
        return FORDEN;
    }




    public void setFORDEN(String fORDEN) {
        FORDEN = fORDEN;
    }




    public String getRESULTADO() {
        return RESULTADO;
    }




    public void setRESULTADO(String rESULTADO) {
        RESULTADO = rESULTADO;
    }




    public String getVALORCRITICODESDE() {
        return VALORCRITICODESDE;
    }




    public void setVALORCRITICODESDE(String vALORCRITICODESDE) {
        VALORCRITICODESDE = vALORCRITICODESDE;
    }




    public String getVALORCRITICOHASTA() {
        return VALORCRITICOHASTA;
    }




    public void setVALORCRITICOHASTA(String vALORCRITICOHASTA) {
        VALORCRITICOHASTA = vALORCRITICOHASTA;
    }




    public String getFFIRMA() {
        return FFIRMA;
    }




    public void setFFIRMA(String fFIRMA) {
        FFIRMA = fFIRMA;
    }




    public String getNOMBRE() {
        return NOMBRE;
    }




    public void setNOMBRE(String nOMBRE) {
        NOMBRE = nOMBRE;
    }
    
    
    
    
    
    
}
