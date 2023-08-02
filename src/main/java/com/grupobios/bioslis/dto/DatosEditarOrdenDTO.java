package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//**realizado por cristian *** se puede agregar dats
public class DatosEditarOrdenDTO {

    private BigDecimal IDTIPODOCUMENTO;
    private String RUNPACIENTE;
    private String NRDOCUMENTOPACIENTE;
    private String NOMBREPACIENTE;
    private BigDecimal SEXOPACIENTE;
    private Date FNACIMIENTOPACIENTE;
    private BigDecimal EDADPACIENTE;
    private String  OBSERVACIONPACIENTE;
    private String EMAILPACIENTE;
    private BigDecimal CTAIDTIPOATENCION; //ctaIdtipoatencion 
   private BigDecimal IDPROCEDENCIA;
    private BigDecimal IDSERVICIO;
    private BigDecimal IDSALASERVICIO;
    private BigDecimal IDCAMASALA;
    private BigDecimal IDPRIORIDADATENCION;
    private BigDecimal IDMEDICO;
    private String NOMBREMEDICO;
    private String RUNMEDICO;
    private BigDecimal IDCONVENIO;
    private BigDecimal IDDIAGNOSTICO;
    private String  OBSERVACIONORDEN;
    private String  SEXODESCRIPCION;
    private Date FECHAORDEN;
    private String ENVIARRESULTADOSEMAIL;
    
    
    private List<PacientePatologiaEditarOrdenDTO> patologias;
    private List<ExamenEditarOrdenDTO> examenes;
  //agregado por cristian 21-11
    private String MUESTRATOMADA;
    //agregado por cristian 21-11
    private List<TestEdicionOrdenDTO> tests;
    
  




    public DatosEditarOrdenDTO() {   
        this.patologias = new ArrayList<PacientePatologiaEditarOrdenDTO>();
        this.examenes = new ArrayList<ExamenEditarOrdenDTO>();
    }
    
 
   
    public BigDecimal getIDTIPODOCUMENTO() {
        return IDTIPODOCUMENTO;
    }

    public void setIDTIPODOCUMENTO(BigDecimal iDTIPODOCUMENTO) {
        IDTIPODOCUMENTO = iDTIPODOCUMENTO;
    }

    public String getRUNPACIENTE() {
        return RUNPACIENTE;
    }

    public void setRUNPACIENTE(String rUNPACIENTE) {
        RUNPACIENTE = rUNPACIENTE;
    }

    public String getNRDOCUMENTOPACIENTE() {
        return NRDOCUMENTOPACIENTE;
    }

    public void setNRDOCUMENTOPACIENTE(String nRDOCUMENTOPACIENTE) {
        NRDOCUMENTOPACIENTE = nRDOCUMENTOPACIENTE;
    }

    public String getNOMBREPACIENTE() {
        return NOMBREPACIENTE;
    }

    public void setNOMBREPACIENTE(String nOMBREPACIENTE) {
        NOMBREPACIENTE = nOMBREPACIENTE;
    }

    public BigDecimal getSEXOPACIENTE() {
        return SEXOPACIENTE;
    }

    public void setSEXOPACIENTE(BigDecimal sEXOPACIENTE) {
        SEXOPACIENTE = sEXOPACIENTE;
    }

    public Date getFNACIMIENTOPACIENTE() {
        return FNACIMIENTOPACIENTE;
    }

    public void setFNACIMIENTOPACIENTE(Date fNACIMIENTOPACIENTE) {
        FNACIMIENTOPACIENTE = fNACIMIENTOPACIENTE;
    }

    public BigDecimal getEDADPACIENTE() {
        return EDADPACIENTE;
    }

    public void setEDADPACIENTE(BigDecimal eDADPACIENTE) {
        EDADPACIENTE = eDADPACIENTE;
    }

    public String getEMAILPACIENTE() {
        return EMAILPACIENTE;
    }

    public void setEMAILPACIENTE(String eMAILPACIENTE) {
        EMAILPACIENTE = eMAILPACIENTE;
    }

    public BigDecimal getCTAIDTIPOATENCION() {
        return CTAIDTIPOATENCION;
    }

    public void setCTAIDTIPOATENCION(BigDecimal cTAIDTIPOATENCION) {
        CTAIDTIPOATENCION = cTAIDTIPOATENCION;
    }

    public BigDecimal getIDPROCEDENCIA() {
        return IDPROCEDENCIA;
    }

    public void setIDPROCEDENCIA(BigDecimal iDPROCEDENCIA) {
        IDPROCEDENCIA = iDPROCEDENCIA;
    }

    public BigDecimal getIDSERVICIO() {
        return IDSERVICIO;
    }

    public void setIDSERVICIO(BigDecimal iDSERVICIO) {
        IDSERVICIO = iDSERVICIO;
    }

    public BigDecimal getIDSALASERVICIO() {
        return IDSALASERVICIO;
    }

    public void setIDSALASERVICIO(BigDecimal iDSALASERVICIO) {
        IDSALASERVICIO = iDSALASERVICIO;
    }

    public BigDecimal getIDCAMASALA() {
        return IDCAMASALA;
    }

    public void setIDCAMASALA(BigDecimal iDCAMASALA) {
        IDCAMASALA = iDCAMASALA;
    }

    public BigDecimal getIDPRIORIDADATENCION() {
        return IDPRIORIDADATENCION;
    }

    public void setIDPRIORIDADATENCION(BigDecimal iDPRIORIDADATENCION) {
        IDPRIORIDADATENCION = iDPRIORIDADATENCION;
    }

    public BigDecimal getIDMEDICO() {
        return IDMEDICO;
    }

    public void setIDMEDICO(BigDecimal iDMEDICO) {
        IDMEDICO = iDMEDICO;
    }

    public String getNOMBREMEDICO() {
        return NOMBREMEDICO;
    }

    public void setNOMBREMEDICO(String nOMBREMEDICO) {
        NOMBREMEDICO = nOMBREMEDICO;
    }

    public String getRUNMEDICO() {
        return RUNMEDICO;
    }

    public void setRUNMEDICO(String rUNMEDICO) {
        RUNMEDICO = rUNMEDICO;
    }

    public BigDecimal getIDCONVENIO() {
        return IDCONVENIO;
    }

    public void setIDCONVENIO(BigDecimal iDCONVENIO) {
        IDCONVENIO = iDCONVENIO;
    }

    public BigDecimal getIDDIAGNOSTICO() {
        return IDDIAGNOSTICO;
    }

    public void setIDDIAGNOSTICO(BigDecimal iDDIAGNOSTICO) {
        IDDIAGNOSTICO = iDDIAGNOSTICO;
    }

     public String getOBSERVACIONPACIENTE() {
        return OBSERVACIONPACIENTE;
    }



    public void setOBSERVACIONPACIENTE(String oBSERVACIONPACIENTE) {
        OBSERVACIONPACIENTE = oBSERVACIONPACIENTE;
    }



    public String getOBSERVACIONORDEN() {
        return OBSERVACIONORDEN;
    }



    public void setOBSERVACIONORDEN(String oBSERVACIONORDEN) {
        OBSERVACIONORDEN = oBSERVACIONORDEN;
    }

    public String getSEXODESCRIPCION() {
        return SEXODESCRIPCION;
    }

    public void setSEXODESCRIPCION(String sEXODESCRIPCION) {
        SEXODESCRIPCION = sEXODESCRIPCION;
    }



    public Date getFECHAORDEN() {
        return FECHAORDEN;
    }



    public void setFECHAORDEN(Date fECHAORDEN) {
        FECHAORDEN = fECHAORDEN;
    }



    public String getENVIARRESULTADOSEMAIL() {
        return ENVIARRESULTADOSEMAIL;
    }



    public void setENVIARRESULTADOSEMAIL(String eNVIARRESULTADOSEMAIL) {
        ENVIARRESULTADOSEMAIL = eNVIARRESULTADOSEMAIL;
    }



    public List<PacientePatologiaEditarOrdenDTO> getPatologias() {
        return patologias;
    }



    public void setPatologias(List<PacientePatologiaEditarOrdenDTO> patologias) {
        this.patologias = patologias;
    }



    public List<ExamenEditarOrdenDTO> getExamenes() {
        return examenes;
    }



    public void setExamenes(List<ExamenEditarOrdenDTO> examenes) {
        this.examenes = examenes;
    }






    
    

    public String getMUESTRATOMADA() {
        return this.MUESTRATOMADA;
    }

    public void setMUESTRATOMADA(String MUESTRATOMADA) {
        this.MUESTRATOMADA = MUESTRATOMADA;
    }
    
    

    public List<TestEdicionOrdenDTO> getTests() {
        return this.tests;
    }

    public void setTests(List<TestEdicionOrdenDTO> tests) {
        this.tests = tests;
    }
    
    
}
