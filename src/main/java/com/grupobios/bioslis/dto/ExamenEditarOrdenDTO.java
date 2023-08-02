package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


//***creado por cristian ** se puede agregar mas datos
public class ExamenEditarOrdenDTO {
    
    private BigDecimal IDEXAMEN;
    private String CODEXAMEN;
    private String EXAMENABREVIADO;
    private BigDecimal IDMUESTRA;
    private String DESCRIPCIONMUESTRA;
    private BigDecimal IDTIPOEXAMEN;
    private BigDecimal IDSECCION;
    private String DESCRIPCIONSECCION;
    private BigDecimal IDLABORATORIO;
    private String DESCRIPCIONLABORATORIO;
    private BigDecimal IDCENTROSALUD;
    private String DESCRIPCIONCENTROSALUD;
    private String CEESURGENTE;
    private Date FECHAENTREGA;
    private BigDecimal TIEMPOENTREGA;
    private BigDecimal CANTIDAD;
    private BigDecimal IDDERIVADOR;
    private Date FECHAEXAMEN;
    //DESCRIPCION EXAMEN
    private String CEDESCRIPCION;
    //codigo estado examen
    private String CODESTADOEXAMEN;
    private String EXAMENANULADO;
    private BigDecimal IDUSUARIOANULA;
    // descripcion indicaciones examen
    private String LDVIEDESCRIPCION;
    private BigDecimal LDVIEIDINDICACIONESEXAMEN;
    private BigDecimal IDENVASE;


    
    public BigDecimal getIDEXAMEN() {
        return this.IDEXAMEN;
    }

    public void setIDEXAMEN(BigDecimal IDEXAMEN) {
        this.IDEXAMEN = IDEXAMEN;
    }

    public String getCODEXAMEN() {
        return this.CODEXAMEN;
    }

    public void setCODEXAMEN(String CODEXAMEN) {
        this.CODEXAMEN = CODEXAMEN;
    }

    public String getEXAMENABREVIADO() {
        return this.EXAMENABREVIADO;
    }

    public void setEXAMENABREVIADO(String EXAMENABREVIADO) {
        this.EXAMENABREVIADO = EXAMENABREVIADO;
    }

    public BigDecimal getIDMUESTRA() {
        return this.IDMUESTRA;
    }

    public void setIDMUESTRA(BigDecimal IDMUESTRA) {
        this.IDMUESTRA = IDMUESTRA;
    }

    public String getDESCRIPCIONMUESTRA() {
        return this.DESCRIPCIONMUESTRA;
    }

    public void setDESCRIPCIONMUESTRA(String DESCRIPCIONMUESTRA) {
        this.DESCRIPCIONMUESTRA = DESCRIPCIONMUESTRA;
    }

    public BigDecimal getIDTIPOEXAMEN() {
        return this.IDTIPOEXAMEN;
    }

    public void setIDTIPOEXAMEN(BigDecimal IDTIPOEXAMEN) {
        this.IDTIPOEXAMEN = IDTIPOEXAMEN;
    }

    public BigDecimal getIDSECCION() {
        return this.IDSECCION;
    }

    public void setIDSECCION(BigDecimal IDSECCION) {
        this.IDSECCION = IDSECCION;
    }

    public String getDESCRIPCIONSECCION() {
        return this.DESCRIPCIONSECCION;
    }

    public void setDESCRIPCIONSECCION(String DESCRIPCIONSECCION) {
        this.DESCRIPCIONSECCION = DESCRIPCIONSECCION;
    }

    public BigDecimal getIDLABORATORIO() {
        return this.IDLABORATORIO;
    }

    public void setIDLABORATORIO(BigDecimal IDLABORATORIO) {
        this.IDLABORATORIO = IDLABORATORIO;
    }

    public String getDESCRIPCIONLABORATORIO() {
        return this.DESCRIPCIONLABORATORIO;
    }

    public void setDESCRIPCIONLABORATORIO(String DESCRIPCIONLABORATORIO) {
        this.DESCRIPCIONLABORATORIO = DESCRIPCIONLABORATORIO;
    }

    public BigDecimal getIDCENTROSALUD() {
        return this.IDCENTROSALUD;
    }

    public void setIDCENTROSALUD(BigDecimal IDCENTROSALUD) {
        this.IDCENTROSALUD = IDCENTROSALUD;
    }

    public String getDESCRIPCIONCENTROSALUD() {
        return this.DESCRIPCIONCENTROSALUD;
    }

    public void setDESCRIPCIONCENTROSALUD(String DESCRIPCIONCENTROSALUD) {
        this.DESCRIPCIONCENTROSALUD = DESCRIPCIONCENTROSALUD;
    }

    public String getCEESURGENTE() {
        return this.CEESURGENTE;
    }

    public void setCEESURGENTE(String CEESURGENTE) {
        this.CEESURGENTE = CEESURGENTE;
    }

    public Date getFECHAENTREGA() {
        return this.FECHAENTREGA;
    }

    public void setFECHAENTREGA(Date FECHAENTREGA) {
        this.FECHAENTREGA = FECHAENTREGA;
    }

    public BigDecimal getTIEMPOENTREGA() {
        return this.TIEMPOENTREGA;
    }

    public void setTIEMPOENTREGA(BigDecimal TIEMPOENTREGA) {
        this.TIEMPOENTREGA = TIEMPOENTREGA;
    }

    public BigDecimal getCANTIDAD() {
        return this.CANTIDAD;
    }

    public void setCANTIDAD(BigDecimal CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public BigDecimal getIDDERIVADOR() {
        return this.IDDERIVADOR;
    }

    public void setIDDERIVADOR(BigDecimal IDDERIVADOR) {
        this.IDDERIVADOR = IDDERIVADOR;
    }

    public Date getFECHAEXAMEN() {
        return this.FECHAEXAMEN;
    }

    public void setFECHAEXAMEN(Date FECHAEXAMEN) {
        this.FECHAEXAMEN = FECHAEXAMEN;
    }

    public String getCEDESCRIPCION() {
        return this.CEDESCRIPCION;
    }

    public void setCEDESCRIPCION(String CEDESCRIPCION) {
        this.CEDESCRIPCION = CEDESCRIPCION;
    }
    
    public String getCODESTADOEXAMEN() {
        return this.CODESTADOEXAMEN;
    }

    public void setCODESTADOEXAMEN(String CODESTADOEXAMEN) {
        this.CODESTADOEXAMEN = CODESTADOEXAMEN;
    }

    public String getEXAMENANULADO() {
        return this.EXAMENANULADO;
    }

    public void setEXAMENANULADO(String EXAMENANULADO) {
        this.EXAMENANULADO = EXAMENANULADO;
    }


    public BigDecimal getIDUSUARIOANULA() {
        return this.IDUSUARIOANULA;
    }

    public void setIDUSUARIOANULA(BigDecimal IDUSUARIOANULA) {
        this.IDUSUARIOANULA = IDUSUARIOANULA;
    }


    public String getLDVIEDESCRIPCION() {
        return this.LDVIEDESCRIPCION;
    }

    public void setLDVIEDESCRIPCION(String LDVIEDESCRIPCION) {
        this.LDVIEDESCRIPCION = LDVIEDESCRIPCION;
    }

    public BigDecimal getLDVIEIDINDICACIONESEXAMEN() {
        return this.LDVIEIDINDICACIONESEXAMEN;
    }

    public void setLDVIEIDINDICACIONESEXAMEN(BigDecimal LDVIEIDINDICACIONESEXAMEN) {
        this.LDVIEIDINDICACIONESEXAMEN = LDVIEIDINDICACIONESEXAMEN;
    }

    public BigDecimal getIDENVASE() {
        return IDENVASE;
    }

    public void setIDENVASE(BigDecimal iDENVASE) {
        IDENVASE = iDENVASE;
    }

  


   
   
    
    
    
}

