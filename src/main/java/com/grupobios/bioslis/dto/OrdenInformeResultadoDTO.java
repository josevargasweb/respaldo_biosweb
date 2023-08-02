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
public class OrdenInformeResultadoDTO {

	private String SDF_FECHAORDEN;
	private BigDecimal DF_NORDEN;
	private String DP_NOMBRES;
	// tipo de documento
	private String LDVTD_DESCRIPCION;
	private String DP_NRODOCUMENTO;
	// tipo de atencion
	private String CTA_DESCRIPCION;
	// procedencia
	private String CP_DESCRIPCION;
	// servicio
	private String CS_DESCRIPCION;
	private String DP_EMAIL;
	private BigDecimal DP_IDPACIENTE;
	private String DP_PRIMERAPELLIDO;
	private String DP_SEGUNDOAPELLIDO;
	// sexo paciente
	private String LDVS_DESCRIPCION;
	// observacion paciente
	private String DP_OBSERVACION;
	// convenios
	private String CC_ABREVIADO;
	// diagnostico
	private String CD_DESCRIPCION;
	// observacion orden
	private String DF_OBSERVACION;
	private String CM_NOMBRES;
	private String CM_PRIMERAPELLIDO;
	private String CM_SEGUNDOAPELLIDO;
	// fecha nacimiento paciente
	private String SDP_FNACIMIENTO;
	private String DFE_CODIGOESTADOEXAMEN;
	private String CEE_DESCRIPCIONESTADOEXAMEN;
	private String DFE_EXAMENESURGENTE;
	private String CE_CODIGOEXAMEN;
	// descripcion abreviada examen
	private String CE_ABREVIADO;
	// descripcion seccion
	private String CSEC_DESCRIPCION;
	private BigDecimal DFE_IDEXAMEN;
	private String hayExamenesPendientes;
	private String hayExamenesUrgentes;
	private String LOCALIZACION;
	// agregado por cristian 09-11
	private BigDecimal DF_IDPREVISION;
	private BigDecimal DF_IDLOCALIZACION;
	private BigDecimal DF_IDCENTRODESALUD;
	private BigDecimal DF_IDPRIORIDADATENCION;
	private BigDecimal DF_IDFICHAESTADOTM;
	// estado toma muestra descripcion
	private String LDVFET_DESCRIPCION;
	private String TIENECULTIVO;
	private BigDecimal DFE_IDBACESTADO;
	// descripcion bacestado examen
	private String CBE_DESCRIPCION;
	private BigDecimal DF_IDPROCEDENCIA;
	// Añadido por Marco Caracciolo
	// modificado por jan error en DataTables Busqueda cambio -> String a BigDecimal
	private String TESTSENPROCESO;
	// agregado x jan (09-06-2023)
	private String ESTADOANTECEDENTE;
	// agregado x jan (22-06-2023)
	private BigDecimal CS_IDSERVICIO;
		// agregado x jo (09-06-2023)
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

	private List<BigDecimal> lstSecciones;

	public List<BigDecimal> getLstSecciones() {
		return lstSecciones;
	}

	public void setLstSecciones(List<BigDecimal> lstSecciones) {
		this.lstSecciones = lstSecciones;
	}

	public String getSDF_FECHAORDEN() {
		return SDF_FECHAORDEN;
	}

	public void setSDF_FECHAORDEN(String DF_FECHAORDEN) {
		this.SDF_FECHAORDEN = DF_FECHAORDEN;
	}

	public Integer getDF_NORDEN() {
		return DF_NORDEN.intValue();
	}

	public void setDF_NORDEN(BigDecimal DF_NORDEN) {
		this.DF_NORDEN = DF_NORDEN;
	}

	public String getDP_NOMBRES() {
		return DP_NOMBRES;
	}

	public void setDP_NOMBRES(String DP_NOMBRES) {
		this.DP_NOMBRES = DP_NOMBRES;
	}

	public String getLDVTD_DESCRIPCION() {
		return LDVTD_DESCRIPCION;
	}

	public void setLDVTD_DESCRIPCION(String LDVTD_DESCRIPCION) {
		this.LDVTD_DESCRIPCION = LDVTD_DESCRIPCION;
	}

	public String getDP_NRODOCUMENTO() {
		return DP_NRODOCUMENTO;
	}

	public void setDP_NRODOCUMENTO(String DP_NRODOCUMENTO) {
		this.DP_NRODOCUMENTO = DP_NRODOCUMENTO;
	}

	public String getCTA_DESCRIPCION() {
		return CTA_DESCRIPCION;
	}

	public void setCTA_DESCRIPCION(String CTA_DESCRIPCION) {
		this.CTA_DESCRIPCION = CTA_DESCRIPCION;
	}

	public String getCP_DESCRIPCION() {
		return CP_DESCRIPCION;
	}

	public void setCP_DESCRIPCION(String CP_DESCRIPCION) {
		this.CP_DESCRIPCION = CP_DESCRIPCION;
	}

	public String getCS_DESCRIPCION() {
		return CS_DESCRIPCION;
	}

	public void setCS_DESCRIPCION(String CS_DESCRIPCION) {
		this.CS_DESCRIPCION = CS_DESCRIPCION;
	}

	public String getDP_EMAIL() {
		return DP_EMAIL;
	}

	public void setDP_EMAIL(String DP_EMAIL) {
		this.DP_EMAIL = DP_EMAIL;
	}

	public Integer getDP_IDPACIENTE() {
		return DP_IDPACIENTE.intValue();
	}

	public void setDP_IDPACIENTE(BigDecimal DP_IDPACIENTE) {
		this.DP_IDPACIENTE = DP_IDPACIENTE;
	}

	public String getDP_PRIMERAPELLIDO() {
		return DP_PRIMERAPELLIDO;
	}

	public void setDP_PRIMERAPELLIDO(String DP_PRIMERAPELLIDO) {
		this.DP_PRIMERAPELLIDO = DP_PRIMERAPELLIDO;
	}

	public String getDP_SEGUNDOAPELLIDO() {
		return DP_SEGUNDOAPELLIDO;
	}

	public void setDP_SEGUNDOAPELLIDO(String DP_SEGUNDOAPELLIDO) {
		this.DP_SEGUNDOAPELLIDO = DP_SEGUNDOAPELLIDO;
	}

	public String getSDP_FNACIMIENTO() {
		return SDP_FNACIMIENTO;
	}

	public void setSDP_FNACIMIENTO(String SDP_FNACIMIENTO) {
		this.SDP_FNACIMIENTO = SDP_FNACIMIENTO;
	}

	public String getLDVS_DESCRIPCION() {
		return LDVS_DESCRIPCION;
	}

	public void setLDVS_DESCRIPCION(String LDVS_DESCRIPCION) {
		this.LDVS_DESCRIPCION = LDVS_DESCRIPCION;
	}

	public String getDP_OBSERVACION() {
		return DP_OBSERVACION;
	}

	public void setDP_OBSERVACION(String DP_OBSERVACION) {
		this.DP_OBSERVACION = DP_OBSERVACION;
	}

	public String getCC_ABREVIADO() {
		return CC_ABREVIADO;
	}

	public void setCC_ABREVIADO(String CC_ABREVIADO) {
		this.CC_ABREVIADO = CC_ABREVIADO;
	}

	public String getCD_DESCRIPCION() {
		return CD_DESCRIPCION;
	}

	public void setCD_DESCRIPCION(String CD_DESCRIPCION) {
		this.CD_DESCRIPCION = CD_DESCRIPCION;
	}

	public String getDF_OBSERVACION() {
		return DF_OBSERVACION;
	}

	public void setDF_OBSERVACION(String DF_OBSERVACION) {
		this.DF_OBSERVACION = DF_OBSERVACION;
	}

	public String getCM_NOMBRES() {
		return CM_NOMBRES;
	}

	public void setCM_NOMBRES(String CM_NOMBRES) {
		this.CM_NOMBRES = CM_NOMBRES;
	}

	public String getCM_PRIMERAPELLIDO() {
		return CM_PRIMERAPELLIDO;
	}

	public void setCM_PRIMERAPELLIDO(String CM_PRIMERAPELLIDO) {
		this.CM_PRIMERAPELLIDO = CM_PRIMERAPELLIDO;
	}

	public String getCM_SEGUNDOAPELLIDO() {
		return CM_SEGUNDOAPELLIDO;
	}

	public void setCM_SEGUNDOAPELLIDO(String CM_SEGUNDOAPELLIDO) {
		this.CM_SEGUNDOAPELLIDO = CM_SEGUNDOAPELLIDO;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.DF_NORDEN);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final OrdenInformeResultadoDTO other = (OrdenInformeResultadoDTO) obj;
		if (!Objects.equals(this.DF_NORDEN, other.DF_NORDEN)) {
			return false;
		}
		return true;
	}

	public String getDFE_CODIGOESTADOEXAMEN() {
		return DFE_CODIGOESTADOEXAMEN;
	}

	public void setDFE_CODIGOESTADOEXAMEN(String dFE_CODIGOESTADOEXAMEN) {
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
		CEE_DESCRIPCIONESTADOEXAMEN = cEE_DESCRIPCIONESTADOEXAMEN;
	}

	public String getDFE_EXAMENESURGENTE() {
		return DFE_EXAMENESURGENTE;
	}

	public void setDFE_EXAMENESURGENTE(String dFE_EXAMENESURGENTE) {
		DFE_EXAMENESURGENTE = dFE_EXAMENESURGENTE;
	}

	@Override
	public String toString() {
		return "OrdenInformeResultadoDTO [SDF_FECHAORDEN=" + SDF_FECHAORDEN + ", DF_NORDEN=" + DF_NORDEN
				+ ", DP_NOMBRES=" + DP_NOMBRES + ", LDVTD_DESCRIPCION=" + LDVTD_DESCRIPCION + ", DP_NRODOCUMENTO="
				+ DP_NRODOCUMENTO + ", CTA_DESCRIPCION=" + CTA_DESCRIPCION + ", CP_DESCRIPCION=" + CP_DESCRIPCION
				+ ", CS_DESCRIPCION=" + CS_DESCRIPCION + ", DP_EMAIL=" + DP_EMAIL + ", DP_IDPACIENTE=" + DP_IDPACIENTE
				+ ", DP_PRIMERAPELLIDO=" + DP_PRIMERAPELLIDO + ", DP_SEGUNDOAPELLIDO=" + DP_SEGUNDOAPELLIDO
				+ ", LDVS_DESCRIPCION=" + LDVS_DESCRIPCION + ", DP_OBSERVACION=" + DP_OBSERVACION + ", CC_ABREVIADO="
				+ CC_ABREVIADO + ", CD_DESCRIPCION=" + CD_DESCRIPCION + ", DF_OBSERVACION=" + DF_OBSERVACION
				+ ", CM_NOMBRES=" + CM_NOMBRES + ", CM_PRIMERAPELLIDO=" + CM_PRIMERAPELLIDO + ", CM_SEGUNDOAPELLIDO="
				+ CM_SEGUNDOAPELLIDO + ", SDP_FNACIMIENTO=" + SDP_FNACIMIENTO + ", DFE_CODIGOESTADOEXAMEN="
				+ DFE_CODIGOESTADOEXAMEN + ", CEE_DESCRIPCIONESTADOEXAMEN=" + CEE_DESCRIPCIONESTADOEXAMEN
				// Agregado por marco
				+ ", TESTSENPROCESO=" + TESTSENPROCESO + ", TIENECULTIVO=" + TIENECULTIVO + "]";
	}

	public String getCE_CODIGOEXAMEN() {
		return CE_CODIGOEXAMEN;
	}

	public void setCE_CODIGOEXAMEN(String cE_CODIGOEXAMEN) {
		CE_CODIGOEXAMEN = cE_CODIGOEXAMEN;
	}

	public String getCE_ABREVIADO() {
		return CE_ABREVIADO;
	}

	public void setCE_ABREVIADO(String cE_ABREVIADO) {
		CE_ABREVIADO = cE_ABREVIADO;
	}

	public String getCSEC_DESCRIPCION() {
		return CSEC_DESCRIPCION;
	}

	public void setCSEC_DESCRIPCION(String cSEC_DESCRIPCION) {
		CSEC_DESCRIPCION = cSEC_DESCRIPCION;
	}

	public BigDecimal getDFE_IDEXAMEN() {
		return DFE_IDEXAMEN;
	}

	public void setDFE_IDEXAMEN(BigDecimal dFE_IDEXAMEN) {
		DFE_IDEXAMEN = dFE_IDEXAMEN;
	}

	public String getHayExamenesPendientes() {
		return hayExamenesPendientes;
	}

	public void setHayExamenesPendientes(String hayExamenesPendientes) {
		this.hayExamenesPendientes = hayExamenesPendientes;
	}

	public String getHayExamenesUrgentes() {
		return hayExamenesUrgentes;
	}

	public void setHayExamenesUrgentes(String hayExamenesUrgentes) {
		this.hayExamenesUrgentes = hayExamenesUrgentes;
	}

	public String getLOCALIZACION() {
		return LOCALIZACION;
	}

	public void setLOCALIZACION(String lOCALIZACION) {
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

	public String getTIENECULTIVO() {
		return TIENECULTIVO;
	}

	public void setTIENECULTIVO(String tIENECULTIVO) {
		TIENECULTIVO = tIENECULTIVO;
	}

	public BigDecimal getDFE_IDBACESTADO() {
		return DFE_IDBACESTADO;
	}

	public void setDFE_IDBACESTADO(BigDecimal dFE_IDBACESTADO) {
		DFE_IDBACESTADO = dFE_IDBACESTADO;
	}

	public String getCBE_DESCRIPCION() {
		return CBE_DESCRIPCION;
	}

	public void setCBE_DESCRIPCION(String cBE_DESCRIPCION) {
		CBE_DESCRIPCION = cBE_DESCRIPCION;
	}

	public BigDecimal getDF_IDPROCEDENCIA() {
		return this.DF_IDPROCEDENCIA;
	}

	public void setDF_IDPROCEDENCIA(BigDecimal DF_IDPROCEDENCIA) {
		this.DF_IDPROCEDENCIA = DF_IDPROCEDENCIA;
	}

	public String getTESTSENPROCESO() {
		return TESTSENPROCESO;
	}

	public void setTESTSENPROCESO(String TESTSENPROCESO) {
		this.TESTSENPROCESO = TESTSENPROCESO;
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
