package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.Comparator;

public class FilaExamenesMuestrasDeUnaOrdenDTO {

	private BigDecimal DFE_NORDEN;
	private String CE_ABREVIADO;
	private String CT_DESCRIPCION;
	private String CMUE_DESCRIPCIONABREVIADA;
	private String CENV_DESCRIPCION;
	private String CSEC_DESCRIPCION;
	private String CT_CODIGO;
	private BigDecimal CT_IDTIPOMUESTRA;
	private BigDecimal DFE_IDEXAMEN;
	private String DFE_CODIGOESTADOEXAMEN;
	private BigDecimal DFE_CANTIDAD;
	private String DFE_EXAMENESURGENTE;
	private String CEE_DESCRIPCIONESTADOEXAMEN;
	private String DFM_CODIGOBARRA;
	private String CE_CODIGOEXAMEN;
	private String CT_FORMULA;
	private String DFE_EXAMENANULADO;
	private BigDecimal CE_IDSECCION;
	// Agregado x jan 12-06-2023
	private String CE_TIENETESTOPCIONALES;
	private String ESTADOOBSERVACION;
	private String ESTADONOTA;
	private String ESTADOCONTADOR;

	public String getESTADOCONTADOR() {
		return ESTADOCONTADOR;
	}

	public void setESTADOCONTADOR(String eSTADOCONTADOR) {
		ESTADOCONTADOR = eSTADOCONTADOR;
	}

	public String getESTADONOTA() {
		return ESTADONOTA;
	}

	public void setESTADONOTA(String eSTADONOTA) {
		ESTADONOTA = eSTADONOTA;
	}

	public String getESTADOOBSERVACION() {
		return ESTADOOBSERVACION;
	}

	public void setESTADOOBSERVACION(String eSTADOOBSERVACION) {
		ESTADOOBSERVACION = eSTADOOBSERVACION;
	}

	public String getCE_TIENETESTOPCIONALES() {
		return CE_TIENETESTOPCIONALES;
	}

	public void setCE_TIENETESTOPCIONALES(String cE_TIENETESTOPCIONALES) {
		CE_TIENETESTOPCIONALES = cE_TIENETESTOPCIONALES;
	}

	public BigDecimal getCE_IDSECCION() {
		return CE_IDSECCION;
	}

	public void setCE_IDSECCION(BigDecimal cE_IDSECCION) {
		CE_IDSECCION = cE_IDSECCION;
	}

	public String getDFE_EXAMENANULADO() {
		return DFE_EXAMENANULADO;
	}

	public void setDFE_EXAMENANULADO(String dFE_EXAMENANULADO) {
		DFE_EXAMENANULADO = dFE_EXAMENANULADO;
	}

	public String getDFM_CODIGOBARRA() {
		return DFM_CODIGOBARRA;
	}

	public void setDFM_CODIGOBARRA(String dFM_CODIGOBARRA) {
		DFM_CODIGOBARRA = dFM_CODIGOBARRA;
	}

	public BigDecimal getDFE_NORDEN() {
		return DFE_NORDEN;
	}

	public void setDFE_NORDEN(BigDecimal dFE_NORDEN) {
		DFE_NORDEN = dFE_NORDEN;
	}

	public String getCT_DESCRIPCION() {
		return CT_DESCRIPCION;
	}

	public void setCT_DESCRIPCION(String cT_DESCRIPCION) {
		CT_DESCRIPCION = cT_DESCRIPCION;
	}

	public String getCMUE_DESCRIPCIONABREVIADA() {
		return CMUE_DESCRIPCIONABREVIADA;
	}

	public void setCMUE_DESCRIPCIONABREVIADA(String cMUE_DESCRIPCIONABREVIADA) {
		CMUE_DESCRIPCIONABREVIADA = cMUE_DESCRIPCIONABREVIADA;
	}

	public String getCENV_DESCRIPCION() {
		return CENV_DESCRIPCION;
	}

	public void setCENV_DESCRIPCION(String cENV_DESCRIPCION) {
		CENV_DESCRIPCION = cENV_DESCRIPCION;
	}

	public String getCSEC_DESCRIPCION() {
		return CSEC_DESCRIPCION;
	}

	public void setCSEC_DESCRIPCION(String cSEC_DESCRIPCION) {
		CSEC_DESCRIPCION = cSEC_DESCRIPCION;
	}

	public String getCT_CODIGO() {
		return CT_CODIGO;
	}

	public void setCT_CODIGO(String cT_CODIGO) {
		CT_CODIGO = cT_CODIGO;
	}

	public BigDecimal getCT_IDTIPOMUESTRA() {
		return CT_IDTIPOMUESTRA;
	}

	public void setCT_IDTIPOMUESTRA(BigDecimal cT_IDTIPOMUESTRA) {
		CT_IDTIPOMUESTRA = cT_IDTIPOMUESTRA;
	}

	public BigDecimal getDFE_IDEXAMEN() {
		return DFE_IDEXAMEN;
	}

	public void setDFE_IDEXAMEN(BigDecimal dFE_IDEXAMEN) {
		DFE_IDEXAMEN = dFE_IDEXAMEN;
	}

	public String getDFE_CODIGOESTADOEXAMEN() {
		return DFE_CODIGOESTADOEXAMEN;
	}

	public void setDFE_CODIGOESTADOEXAMEN(String dFE_CODIGOESTADOEXAMEN) {
		DFE_CODIGOESTADOEXAMEN = dFE_CODIGOESTADOEXAMEN;
	}

	public BigDecimal getDFE_CANTIDAD() {
		return DFE_CANTIDAD;
	}

	public void setDFE_CANTIDAD(BigDecimal dFE_CANTIDAD) {
		DFE_CANTIDAD = dFE_CANTIDAD;
	}

	public String getCE_ABREVIADO() {
		return CE_ABREVIADO;
	}

	public void setCE_ABREVIADO(String cE_ABREVIADO) {
		CE_ABREVIADO = cE_ABREVIADO;
	}

	@Override
	public String toString() {
		return "FilaExamenesMuestrasDeUnaOrdenDTO [DFE_NORDEN=" + DFE_NORDEN + ", CE_ABREVIADO=" + CE_ABREVIADO
				+ ", CT_DESCRIPCION=" + CT_DESCRIPCION + ", CMUE_DESCRIPCIONABREVIADA=" + CMUE_DESCRIPCIONABREVIADA
				+ ", CENV_DESCRIPCION=" + CENV_DESCRIPCION + ", CSEC_DESCRIPCION=" + CSEC_DESCRIPCION + ", CT_CODIGO="
				+ CT_CODIGO + ", CT_IDTIPOMUESTRA=" + CT_IDTIPOMUESTRA + ", DFE_IDEXAMEN=" + DFE_IDEXAMEN
				+ ", DFE_CODIGOESTADOEXAMEN=" + DFE_CODIGOESTADOEXAMEN + ", DFE_CANTIDAD=" + DFE_CANTIDAD + "]";
	}

	public FilaExamenesMuestrasDeUnaOrdenDTO() {
		super();
		DFE_NORDEN = null;
		CE_ABREVIADO = "";
		CT_DESCRIPCION = "";
		CMUE_DESCRIPCIONABREVIADA = "";
		CENV_DESCRIPCION = "";
		CSEC_DESCRIPCION = "";
		CT_CODIGO = "";
		CT_IDTIPOMUESTRA = null;
		DFE_IDEXAMEN = null;
		DFE_CODIGOESTADOEXAMEN = "";
		DFE_CANTIDAD = null;
		CE_CODIGOEXAMEN = "";
	}

	/**
	 * @return the dFE_EXAMENESURGENTE
	 */
	public String getDFE_EXAMENESURGENTE() {
		return DFE_EXAMENESURGENTE;
	}

	/**
	 * @param dFE_EXAMENESURGENTE the dFE_EXAMENESURGENTE to set
	 */
	public void setDFE_EXAMENESURGENTE(String dFE_EXAMENESURGENTE) {
		DFE_EXAMENESURGENTE = dFE_EXAMENESURGENTE;
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

	public String getCE_CODIGOEXAMEN() {
		return CE_CODIGOEXAMEN;
	}

	public void setCE_CODIGOEXAMEN(String cE_CODIGOEXAMEN) {
		CE_CODIGOEXAMEN = cE_CODIGOEXAMEN;
	}

	public String getCT_FORMULA() {
		return CT_FORMULA;
	}

	public void setCT_FORMULA(String cT_FORMULA) {
		CT_FORMULA = cT_FORMULA;
	}

	public class Comparador implements Comparator<FilaExamenesMuestrasDeUnaOrdenDTO> {

		@Override
		public int compare(FilaExamenesMuestrasDeUnaOrdenDTO o1, FilaExamenesMuestrasDeUnaOrdenDTO o2) {

			return -1 * o1.DFE_EXAMENESURGENTE.compareTo(o2.DFE_EXAMENESURGENTE);
		}

	}
}
