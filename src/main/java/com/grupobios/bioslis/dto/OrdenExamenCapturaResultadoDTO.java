/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author eric.nicholls
 */
public class OrdenExamenCapturaResultadoDTO {

	private OrdenInformeResultadoDTO ordenInformeResultadoDTO;

	private String SDF_FECHAORDEN;
	private BigDecimal DF_NORDEN;
	private String DP_NOMBRES;
	private String LDVTD_DESCRIPCION;
	private String DP_NRODOCUMENTO;
	private String CTA_DESCRIPCION;
	private String CP_DESCRIPCION;
	private String CS_DESCRIPCION;
	private String DP_EMAIL;
	private BigDecimal DP_IDPACIENTE;
	private String DP_PRIMERAPELLIDO;
	private String DP_SEGUNDOAPELLIDO;
	private String LDVS_DESCRIPCION;
	private String DP_OBSERVACION;
	private String CC_ABREVIADO;
	private String CD_DESCRIPCION;
	private String DF_OBSERVACION;
	private String CM_NOMBRES;
	private String CM_PRIMERAPELLIDO;
	private String CM_SEGUNDOAPELLIDO;
	private String SDP_FNACIMIENTO;
	private String DFE_CODIGOESTADOEXAMEN;
	private String CEE_DESCRIPCIONESTADOEXAMEN;
	private String DFE_EXAMENESURGENTE;
	private BigDecimal CE_IDSECCION;
	private String CE_CODIGOEXAMEN;
	private String CE_ABREVIADO;
	private String CSEC_DESCRIPCION;
	private BigDecimal DFE_IDEXAMEN;
	private String DF_HOST;
	private String LOCALIZACION;
	private List<BigDecimal> lstSecciones;
	// agregado por cristian 09-11
	private BigDecimal DF_IDPREVISION;
	private BigDecimal DF_IDLOCALIZACION;
	private BigDecimal DF_IDCENTRODESALUD;
	private BigDecimal DF_IDPRIORIDADATENCION;
	private BigDecimal DF_IDFICHAESTADOTM;
	private String LDVFET_DESCRIPCION;
	// agregado x jan
	private List<DatosFichasExamenesDTO> examenesLst;
	private BigDecimal DFE_IDUSUARIOIMPRIME;
	// agregado x marco 16/03/2023
	private String TIENECULTIVO;
	private BigDecimal TESTSENPROCESO;

	private BigDecimal DP_IDSEXO;
	private BigDecimal DF_IDTIPOATENCION;
	private BigDecimal DF_IDPROCEDENCIA;
	// agregado x jan (09-06-2023)
	private String ESTADOANTECEDENTE;
	// agregado x jan (22-06-2023)
	private BigDecimal CS_IDSERVICIO;
	// agregado x jan (22-06-2023)
	private String DP_FECHANACIMIENTO;

	public BigDecimal getCS_IDSERVICIO() {
		return CS_IDSERVICIO;
	}

	public void setCS_IDSERVICIO(BigDecimal cS_IDSERVICIO) {
		CS_IDSERVICIO = cS_IDSERVICIO;
	}

	public String getESTADOANTECEDENTE() {
		return ESTADOANTECEDENTE;
	}

	public void setESTADOANTECEDENTE(String eSTADOANTECEDENTE) {
		ESTADOANTECEDENTE = eSTADOANTECEDENTE;
	}

	public void setDF_IDPROCEDENCIA(BigDecimal dF_IDPROCEDENCIA) {
		DF_IDPROCEDENCIA = dF_IDPROCEDENCIA;
	}

	public BigDecimal getDP_IDSEXO() {
		return DP_IDSEXO;
	}

	public void setDP_IDSEXO(BigDecimal dP_IDSEXO) {
		DP_IDSEXO = dP_IDSEXO;
	}

	public OrdenExamenCapturaResultadoDTO() {

		this.ordenInformeResultadoDTO = new OrdenInformeResultadoDTO();
	}

	public String getSDF_FECHAORDEN() {
		return SDF_FECHAORDEN;
	}

	public void setSDF_FECHAORDEN(String DF_FECHAORDEN) {
		this.ordenInformeResultadoDTO.setSDF_FECHAORDEN(DF_FECHAORDEN);
		this.SDF_FECHAORDEN = DF_FECHAORDEN;
	}

	public Integer getDF_NORDEN() {
		return DF_NORDEN.intValue();
	}

	public void setDF_NORDEN(BigDecimal DF_NORDEN) {
		this.ordenInformeResultadoDTO.setDF_NORDEN(DF_NORDEN);
		this.DF_NORDEN = DF_NORDEN;
	}

	public String getDP_NOMBRES() {
		return DP_NOMBRES;
	}

	public void setDP_NOMBRES(String DP_NOMBRES) {
		this.ordenInformeResultadoDTO.setDP_NOMBRES(DP_NOMBRES);
		this.DP_NOMBRES = DP_NOMBRES;
	}

	public String getLDVTD_DESCRIPCION() {
		return LDVTD_DESCRIPCION;
	}

	public void setLDVTD_DESCRIPCION(String LDVTD_DESCRIPCION) {
		this.ordenInformeResultadoDTO.setLDVTD_DESCRIPCION(LDVTD_DESCRIPCION);
		this.LDVTD_DESCRIPCION = LDVTD_DESCRIPCION;
	}

	public String getDP_NRODOCUMENTO() {
		return DP_NRODOCUMENTO;
	}

	public void setDP_NRODOCUMENTO(String DP_NRODOCUMENTO) {
		this.ordenInformeResultadoDTO.setDP_NRODOCUMENTO(DP_NRODOCUMENTO);
		this.DP_NRODOCUMENTO = DP_NRODOCUMENTO;
	}

	public String getCTA_DESCRIPCION() {
		return CTA_DESCRIPCION;
	}

	public void setCTA_DESCRIPCION(String CTA_DESCRIPCION) {
		this.ordenInformeResultadoDTO.setCTA_DESCRIPCION(CTA_DESCRIPCION);
		this.CTA_DESCRIPCION = CTA_DESCRIPCION;
	}

	public String getCP_DESCRIPCION() {
		return CP_DESCRIPCION;
	}

	public void setCP_DESCRIPCION(String CP_DESCRIPCION) {
		this.ordenInformeResultadoDTO.setCP_DESCRIPCION(CP_DESCRIPCION);
		this.CP_DESCRIPCION = CP_DESCRIPCION;
	}

	public String getCS_DESCRIPCION() {
		return CS_DESCRIPCION;
	}

	public void setCS_DESCRIPCION(String CS_DESCRIPCION) {
		this.ordenInformeResultadoDTO.setCS_DESCRIPCION(CS_DESCRIPCION);
		this.CS_DESCRIPCION = CS_DESCRIPCION;
	}

	public String getDP_EMAIL() {
		return DP_EMAIL;
	}

	public void setDP_EMAIL(String DP_EMAIL) {
		this.ordenInformeResultadoDTO.setDP_EMAIL(DP_EMAIL);
		this.DP_EMAIL = DP_EMAIL;
	}

	public Integer getDP_IDPACIENTE() {
		return DP_IDPACIENTE.intValue();
	}

	public void setDP_IDPACIENTE(BigDecimal DP_IDPACIENTE) {
		this.ordenInformeResultadoDTO.setDP_IDPACIENTE(DP_IDPACIENTE);
		this.DP_IDPACIENTE = DP_IDPACIENTE;
	}

	public String getDP_PRIMERAPELLIDO() {
		return DP_PRIMERAPELLIDO;
	}

	public void setDP_PRIMERAPELLIDO(String DP_PRIMERAPELLIDO) {
		this.ordenInformeResultadoDTO.setDP_PRIMERAPELLIDO(DP_PRIMERAPELLIDO);
		this.DP_PRIMERAPELLIDO = DP_PRIMERAPELLIDO;
	}

	public String getDP_SEGUNDOAPELLIDO() {
		return DP_SEGUNDOAPELLIDO;
	}

	public void setDP_SEGUNDOAPELLIDO(String DP_SEGUNDOAPELLIDO) {
		this.ordenInformeResultadoDTO.setDP_SEGUNDOAPELLIDO(DP_SEGUNDOAPELLIDO);
		this.DP_SEGUNDOAPELLIDO = DP_SEGUNDOAPELLIDO;
	}

	public String getSDP_FNACIMIENTO() {
		return SDP_FNACIMIENTO;
	}

	public void setSDP_FNACIMIENTO(String SDP_FNACIMIENTO) {
		this.ordenInformeResultadoDTO.setSDP_FNACIMIENTO(SDP_FNACIMIENTO);
		this.SDP_FNACIMIENTO = SDP_FNACIMIENTO;
	}

	public String getLDVS_DESCRIPCION() {
		return LDVS_DESCRIPCION;
	}

	public void setLDVS_DESCRIPCION(String LDVS_DESCRIPCION) {
		this.ordenInformeResultadoDTO.setLDVS_DESCRIPCION(LDVS_DESCRIPCION);
		this.LDVS_DESCRIPCION = LDVS_DESCRIPCION;
	}

	public String getDP_OBSERVACION() {
		return DP_OBSERVACION;
	}

	public void setDP_OBSERVACION(String DP_OBSERVACION) {
		this.ordenInformeResultadoDTO.setDP_OBSERVACION(DP_OBSERVACION);
		this.DP_OBSERVACION = DP_OBSERVACION;
	}

	public String getCC_ABREVIADO() {
		return CC_ABREVIADO;
	}

	public void setCC_ABREVIADO(String CC_ABREVIADO) {
		this.ordenInformeResultadoDTO.setCC_ABREVIADO(CC_ABREVIADO);
		this.CC_ABREVIADO = CC_ABREVIADO;
	}

	public String getCD_DESCRIPCION() {
		return CD_DESCRIPCION;
	}

	public void setCD_DESCRIPCION(String CD_DESCRIPCION) {
		this.ordenInformeResultadoDTO.setCD_DESCRIPCION(CD_DESCRIPCION);
		this.CD_DESCRIPCION = CD_DESCRIPCION;
	}

	public String getDF_OBSERVACION() {
		return DF_OBSERVACION;
	}

	public void setDF_OBSERVACION(String DF_OBSERVACION) {
		this.ordenInformeResultadoDTO.setDF_OBSERVACION(DF_OBSERVACION);
		this.DF_OBSERVACION = DF_OBSERVACION;
	}

	public String getCM_NOMBRES() {
		return CM_NOMBRES;
	}

	public void setCM_NOMBRES(String CM_NOMBRES) {
		this.ordenInformeResultadoDTO.setCM_NOMBRES(CM_NOMBRES);
		this.CM_NOMBRES = CM_NOMBRES;
	}

	public String getCM_PRIMERAPELLIDO() {
		return CM_PRIMERAPELLIDO;
	}

	public void setCM_PRIMERAPELLIDO(String CM_PRIMERAPELLIDO) {
		this.ordenInformeResultadoDTO.setCM_PRIMERAPELLIDO(CM_PRIMERAPELLIDO);
		this.CM_PRIMERAPELLIDO = CM_PRIMERAPELLIDO;
	}

	public String getCM_SEGUNDOAPELLIDO() {
		return CM_SEGUNDOAPELLIDO;
	}

	public void setCM_SEGUNDOAPELLIDO(String CM_SEGUNDOAPELLIDO) {
		this.ordenInformeResultadoDTO.setCM_SEGUNDOAPELLIDO(CM_SEGUNDOAPELLIDO);
		this.CM_SEGUNDOAPELLIDO = CM_SEGUNDOAPELLIDO;
	}

	public BigDecimal getCE_IDSECCION() {
		return CE_IDSECCION;
	}

	public void setCE_IDSECCION(BigDecimal cE_IDSECCION) {
		CE_IDSECCION = cE_IDSECCION;
	}

	public OrdenInformeResultadoDTO getOrdenInformeResultadoDTO() {
		return ordenInformeResultadoDTO;
	}

	public void setOrdenInformeResultadoDTO(OrdenInformeResultadoDTO ordenInformeResultadoDTO) {
		this.ordenInformeResultadoDTO = ordenInformeResultadoDTO;
	}

	public String getDFE_CODIGOESTADOEXAMEN() {
		return DFE_CODIGOESTADOEXAMEN;
	}

	public void setDFE_CODIGOESTADOEXAMEN(String dFE_CODIGOESTADOEXAMEN) {
		this.ordenInformeResultadoDTO.setDFE_CODIGOESTADOEXAMEN(dFE_CODIGOESTADOEXAMEN);
		DFE_CODIGOESTADOEXAMEN = dFE_CODIGOESTADOEXAMEN;
	}

	/**
	 * @return the cEE_DESCRIPCIONESTADOEXAMEN
	 */
	public String getCEE_DESCRIPCIONESTADOEXAMEN() {
		return CEE_DESCRIPCIONESTADOEXAMEN;
	}

	/**
	 * @param cEE_DESCRIPCIONESTADOEXAMEN the cEE_DESCRIPCIONESTADOEXAMEN to set
	 */
	public void setCEE_DESCRIPCIONESTADOEXAMEN(String cEE_DESCRIPCIONESTADOEXAMEN) {
		this.ordenInformeResultadoDTO.setCEE_DESCRIPCIONESTADOEXAMEN(cEE_DESCRIPCIONESTADOEXAMEN);

		CEE_DESCRIPCIONESTADOEXAMEN = cEE_DESCRIPCIONESTADOEXAMEN;
	}

	@Override
	public int hashCode() {
		return Objects.hash(CC_ABREVIADO, CD_DESCRIPCION, CEE_DESCRIPCIONESTADOEXAMEN, CE_IDSECCION, CM_NOMBRES,
				CM_PRIMERAPELLIDO, CM_SEGUNDOAPELLIDO, CP_DESCRIPCION, CS_DESCRIPCION, CTA_DESCRIPCION,
				DFE_CODIGOESTADOEXAMEN, DF_NORDEN, DF_OBSERVACION, DP_EMAIL, DP_IDPACIENTE, DP_NOMBRES, DP_NRODOCUMENTO,
				DP_OBSERVACION, DP_PRIMERAPELLIDO, DP_SEGUNDOAPELLIDO, LDVS_DESCRIPCION, LDVTD_DESCRIPCION,
				SDF_FECHAORDEN, SDP_FNACIMIENTO
				// agregado por marco
				, TIENECULTIVO, TESTSENPROCESO, ESTADOANTECEDENTE);
	}

	public String getDFE_EXAMENESURGENTE() {
		return DFE_EXAMENESURGENTE;
	}

	public void setDFE_EXAMENESURGENTE(String dFE_EXAMENESURGENTE) {
		this.ordenInformeResultadoDTO.setDFE_EXAMENESURGENTE(dFE_EXAMENESURGENTE);
		DFE_EXAMENESURGENTE = dFE_EXAMENESURGENTE;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdenExamenCapturaResultadoDTO other = (OrdenExamenCapturaResultadoDTO) obj;
		return Objects.equals(CC_ABREVIADO, other.CC_ABREVIADO) && Objects.equals(CD_DESCRIPCION, other.CD_DESCRIPCION)
				&& Objects.equals(CEE_DESCRIPCIONESTADOEXAMEN, other.CEE_DESCRIPCIONESTADOEXAMEN)
				&& Objects.equals(CE_IDSECCION, other.CE_IDSECCION) && Objects.equals(CM_NOMBRES, other.CM_NOMBRES)
				&& Objects.equals(CM_PRIMERAPELLIDO, other.CM_PRIMERAPELLIDO)
				&& Objects.equals(CM_SEGUNDOAPELLIDO, other.CM_SEGUNDOAPELLIDO)
				&& Objects.equals(CP_DESCRIPCION, other.CP_DESCRIPCION)
				&& Objects.equals(CS_DESCRIPCION, other.CS_DESCRIPCION)
				&& Objects.equals(CTA_DESCRIPCION, other.CTA_DESCRIPCION)
				&& Objects.equals(DFE_CODIGOESTADOEXAMEN, other.DFE_CODIGOESTADOEXAMEN)
				&& Objects.equals(DF_NORDEN, other.DF_NORDEN) && Objects.equals(DF_OBSERVACION, other.DF_OBSERVACION)
				&& Objects.equals(DP_EMAIL, other.DP_EMAIL) && Objects.equals(DP_IDPACIENTE, other.DP_IDPACIENTE)
				&& Objects.equals(DP_NOMBRES, other.DP_NOMBRES)
				&& Objects.equals(DP_NRODOCUMENTO, other.DP_NRODOCUMENTO)
				&& Objects.equals(DP_OBSERVACION, other.DP_OBSERVACION)
				&& Objects.equals(DP_PRIMERAPELLIDO, other.DP_PRIMERAPELLIDO)
				&& Objects.equals(DP_SEGUNDOAPELLIDO, other.DP_SEGUNDOAPELLIDO)
				&& Objects.equals(LDVS_DESCRIPCION, other.LDVS_DESCRIPCION)
				&& Objects.equals(LDVTD_DESCRIPCION, other.LDVTD_DESCRIPCION)
				&& Objects.equals(SDF_FECHAORDEN, other.SDF_FECHAORDEN)
				&& Objects.equals(SDP_FNACIMIENTO, other.SDP_FNACIMIENTO)
				// Agregado por Marco
				&& Objects.equals(TIENECULTIVO, other.TIENECULTIVO)
				&& Objects.equals(TESTSENPROCESO, other.TESTSENPROCESO)
				&& Objects.equals(ESTADOANTECEDENTE, other.ESTADOANTECEDENTE);
	}

	@Override
	public String toString() {
		return "OrdenExamenCapturaResultadoDTO [ordenInformeResultadoDTO=" + ordenInformeResultadoDTO
				+ ", SDF_FECHAORDEN=" + SDF_FECHAORDEN + ", DF_NORDEN=" + DF_NORDEN + ", DP_NOMBRES=" + DP_NOMBRES
				+ ", LDVTD_DESCRIPCION=" + LDVTD_DESCRIPCION + ", DP_NRODOCUMENTO=" + DP_NRODOCUMENTO
				+ ", CTA_DESCRIPCION=" + CTA_DESCRIPCION + ", CP_DESCRIPCION=" + CP_DESCRIPCION + ", CS_DESCRIPCION="
				+ CS_DESCRIPCION + ", DP_EMAIL=" + DP_EMAIL + ", DP_IDPACIENTE=" + DP_IDPACIENTE
				+ ", DP_PRIMERAPELLIDO=" + DP_PRIMERAPELLIDO + ", DP_SEGUNDOAPELLIDO=" + DP_SEGUNDOAPELLIDO
				+ ", LDVS_DESCRIPCION=" + LDVS_DESCRIPCION + ", DP_OBSERVACION=" + DP_OBSERVACION + ", CC_ABREVIADO="
				+ CC_ABREVIADO + ", CD_DESCRIPCION=" + CD_DESCRIPCION + ", DF_OBSERVACION=" + DF_OBSERVACION
				+ ", CM_NOMBRES=" + CM_NOMBRES + ", CM_PRIMERAPELLIDO=" + CM_PRIMERAPELLIDO + ", CM_SEGUNDOAPELLIDO="
				+ CM_SEGUNDOAPELLIDO + ", SDP_FNACIMIENTO=" + SDP_FNACIMIENTO + ", DFE_CODIGOESTADOEXAMEN="
				+ DFE_CODIGOESTADOEXAMEN + ", CEE_DESCRIPCIONESTADOEXAMEN=" + CEE_DESCRIPCIONESTADOEXAMEN
				+ ", DFE_EXAMENESURGENTE=" + DFE_EXAMENESURGENTE + ", CE_IDSECCION=" + CE_IDSECCION
				+ ", CE_CODIGOEXAMEN=" + CE_CODIGOEXAMEN + ", CE_ABREVIADO=" + CE_ABREVIADO + ", CSEC_DESCRIPCION="
				+ CSEC_DESCRIPCION + ", DFE_IDEXAMEN=" + DFE_IDEXAMEN + ", DF_HOST=" + DF_HOST + ", LOCALIZACION="
				+ LOCALIZACION + ", lstSecciones=" + lstSecciones + ", DF_IDPREVISION=" + DF_IDPREVISION
				+ ", DF_IDLOCALIZACION=" + DF_IDLOCALIZACION + ", DF_IDCENTRODESALUD=" + DF_IDCENTRODESALUD
				+ ", DF_IDPRIORIDADATENCION=" + DF_IDPRIORIDADATENCION + ", DF_IDFICHAESTADOTM=" + DF_IDFICHAESTADOTM
				+ ", LDVFET_DESCRIPCION=" + LDVFET_DESCRIPCION + ", examenesLst=" + examenesLst
				// agregado por Marco Caracciolo
				+ ", TESTSENPROCESO=" + TESTSENPROCESO + ", TIENECULTIVO=" + TIENECULTIVO + ""
				// agregado por jan
				+ ", ESTADOANTECEDENTE=" + ESTADOANTECEDENTE + "]";
	}

	public String getCE_CODIGOEXAMEN() {
		return CE_CODIGOEXAMEN;
	}

	public void setCE_CODIGOEXAMEN(String ce_CODIGOEXAMEN) {
		this.ordenInformeResultadoDTO.setCE_CODIGOEXAMEN(ce_CODIGOEXAMEN);
		CE_CODIGOEXAMEN = ce_CODIGOEXAMEN;
	}

	public String getCE_ABREVIADO() {
		return CE_ABREVIADO;
	}

	public void setCE_ABREVIADO(String cE_ABREVIADO) {
		this.ordenInformeResultadoDTO.setCE_ABREVIADO(cE_ABREVIADO);
		CE_ABREVIADO = cE_ABREVIADO;
	}

	public String getCSEC_DESCRIPCION() {
		return CSEC_DESCRIPCION;
	}

	public void setCSEC_DESCRIPCION(String cSEC_DESCRIPCION) {
		this.ordenInformeResultadoDTO.setCSEC_DESCRIPCION(cSEC_DESCRIPCION);
		CSEC_DESCRIPCION = cSEC_DESCRIPCION;
	}

	public BigDecimal getDFE_IDEXAMEN() {
		return DFE_IDEXAMEN;
	}

	public void setDFE_IDEXAMEN(BigDecimal dFE_IDEXAMEN) {
		this.ordenInformeResultadoDTO.setDFE_IDEXAMEN(dFE_IDEXAMEN);
		DFE_IDEXAMEN = dFE_IDEXAMEN;
	}

	public String getDF_HOST() {
		return DF_HOST;
	}

	public void setDF_HOST(String dF_HOST) {
		DF_HOST = dF_HOST;
	}

	public List<BigDecimal> getLstSecciones() {
		return lstSecciones;
	}

	public void setLstSecciones(List<BigDecimal> lstSecciones) {
		this.ordenInformeResultadoDTO.setLstSecciones(lstSecciones);
		this.lstSecciones = lstSecciones;
	}

	public String getLOCALIZACION() {
		return LOCALIZACION;
	}

	public void setLOCALIZACION(String lOCALIZACION) {
		this.ordenInformeResultadoDTO.setLOCALIZACION(lOCALIZACION);
		LOCALIZACION = lOCALIZACION;
	}

	public BigDecimal getDF_IDPREVISION() {
		return DF_IDPREVISION;
	}

	public void setDF_IDPREVISION(BigDecimal dF_IDPREVISION) {
		DF_IDPREVISION = dF_IDPREVISION;
	}

	public BigDecimal getDF_IDLOCALIZACION() {
		return DF_IDLOCALIZACION;
	}

	public void setDF_IDLOCALIZACION(BigDecimal dF_IDLOCALIZACION) {
		DF_IDLOCALIZACION = dF_IDLOCALIZACION;
	}

	public BigDecimal getDF_IDCENTRODESALUD() {
		return DF_IDCENTRODESALUD;
	}

	public void setDF_IDCENTRODESALUD(BigDecimal dF_IDCENTRODESALUD) {
		DF_IDCENTRODESALUD = dF_IDCENTRODESALUD;
	}

	public BigDecimal getDF_IDPRIORIDADATENCION() {
		return DF_IDPRIORIDADATENCION;
	}

	public void setDF_IDPRIORIDADATENCION(BigDecimal dF_IDPRIORIDADATENCION) {
		DF_IDPRIORIDADATENCION = dF_IDPRIORIDADATENCION;
	}

	public BigDecimal getDF_IDFICHAESTADOTM() {
		return DF_IDFICHAESTADOTM;
	}

	public void setDF_IDFICHAESTADOTM(BigDecimal dF_IDFICHAESTADOTM) {
		DF_IDFICHAESTADOTM = dF_IDFICHAESTADOTM;
	}

	public String getLDVFET_DESCRIPCION() {
		return LDVFET_DESCRIPCION;
	}

	public void setLDVFET_DESCRIPCION(String lDVFET_DESCRIPCION) {
		LDVFET_DESCRIPCION = lDVFET_DESCRIPCION;
	}

	public List<DatosFichasExamenesDTO> getExamenesLst() {
		return examenesLst;
	}

	public void setExamenesLst(List<DatosFichasExamenesDTO> examenesLst) {
		this.examenesLst = examenesLst;
	}

	public BigDecimal getDFE_IDUSUARIOIMPRIME() {
		return DFE_IDUSUARIOIMPRIME;
	}

	public void setDFE_IDUSUARIOIMPRIME(BigDecimal dFE_IDUSUARIOIMPRIME) {
		DFE_IDUSUARIOIMPRIME = dFE_IDUSUARIOIMPRIME;
	}

	public String getTIENECULTIVO() {
		return TIENECULTIVO;
	}

	public void setTIENECULTIVO(String TIENECULTIVO) {
		this.TIENECULTIVO = TIENECULTIVO;
	}

	public BigDecimal getTESTSENPROCESO() {
		return TESTSENPROCESO;
	}

	public void setTESTSENPROCESO(BigDecimal TESTSENPROCESO) {
		this.TESTSENPROCESO = TESTSENPROCESO;
	}

	public BigDecimal getDF_IDTIPOATENCION() {
		return DF_IDTIPOATENCION;
	}

	public void setDF_IDTIPOATENCION(BigDecimal dF_IDTIPOATENCION) {
		DF_IDTIPOATENCION = dF_IDTIPOATENCION;
	}

	public BigDecimal getDF_IDPROCEDENCIA() {
		return DF_IDPROCEDENCIA;
	}


    /**
     * @return String return the DP_FECHANACIMIENTO
     */
    public String getDP_FECHANACIMIENTO() {
        return DP_FECHANACIMIENTO;
    }

    /**
     * @param DP_FECHANACIMIENTO the DP_FECHANACIMIENTO to set
     */
    public void setDP_FECHANACIMIENTO(String DP_FECHANACIMIENTO) {
        this.DP_FECHANACIMIENTO = DP_FECHANACIMIENTO;
    }

}
