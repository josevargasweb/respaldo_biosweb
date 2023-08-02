/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

/**
 *
 * @author eric.nicholls
 */
public class DatosFichasExamenesDTO {
	
	private BigDecimal DFE_IDEXAMEN;
	private String cee_DESCRIPCIONESTADOEXAMEN;
	private String dfe_FECHARETIRAEXAMEN;
	private String dfe_IMPRESO;
	private String ceAbreviado;
	private String ceCodigoExamen;
	

	public String getCeCodigoExamen() {
		return ceCodigoExamen;
	}
	public void setCeCodigoExamen(String ceCodigoExamen) {
		this.ceCodigoExamen = ceCodigoExamen;
	}
	public String getDfe_IMPRESO() {
		return dfe_IMPRESO;
	}
	public void setDfe_IMPRESO(String dfe_IMPRESO) {
		this.dfe_IMPRESO = dfe_IMPRESO;
	}
	public String getCeAbreviado() {
		return ceAbreviado;
	}
	public void setCeAbreviado(String ceAbreviado) {
		this.ceAbreviado = ceAbreviado;
	}
	public BigDecimal getDFE_IDEXAMEN() {
		return DFE_IDEXAMEN;
	}
	public void setDFE_IDEXAMEN(BigDecimal dFE_IDEXAMEN) {
		DFE_IDEXAMEN = dFE_IDEXAMEN;
	}
	public String getCee_DESCRIPCIONESTADOEXAMEN() {
		return cee_DESCRIPCIONESTADOEXAMEN;
	}
	public void setCee_DESCRIPCIONESTADOEXAMEN(String cee_DESCRIPCIONESTADOEXAMEN) {
		this.cee_DESCRIPCIONESTADOEXAMEN = cee_DESCRIPCIONESTADOEXAMEN;
	}
	public String getDfe_FECHARETIRAEXAMEN() {
		return dfe_FECHARETIRAEXAMEN;
	}
	public void setDfe_FECHARETIRAEXAMEN(String dfe_FECHARETIRAEXAMEN) {
		this.dfe_FECHARETIRAEXAMEN = dfe_FECHARETIRAEXAMEN;
	}
	
	

        
}
