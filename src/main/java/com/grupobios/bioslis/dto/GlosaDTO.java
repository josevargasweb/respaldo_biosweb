package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class GlosaDTO {

	BigDecimal CT_IDTEST;
	String CTR_CODIGO;
	BigDecimal CG_IDGLOSA;
	String CG_DESCRIPCION;
	String CG_CODIGOGLOSA;
	String CGT_ESGLOSACRITICA;

	
	
	
	public String getCGT_ESGLOSACRITICA() {
		return CGT_ESGLOSACRITICA;
	}

	public void setCGT_ESGLOSACRITICA(String cGT_ESGLOSACRITICA) {
		CGT_ESGLOSACRITICA = cGT_ESGLOSACRITICA;
	}

	public BigDecimal getCT_IDTEST() {
		return CT_IDTEST;
	}

	public void setCT_IDTEST(BigDecimal cT_IDTEST) {
		CT_IDTEST = cT_IDTEST;
	}

	public String getCTR_CODIGO() {
		return CTR_CODIGO;
	}

	public void setCTR_CODIGO(String cTR_CODIGO) {
		CTR_CODIGO = cTR_CODIGO;
	}

	public BigDecimal getCG_IDGLOSA() {
		return CG_IDGLOSA;
	}

	public void setCG_IDGLOSA(BigDecimal cG_IDGLOSA) {
		CG_IDGLOSA = cG_IDGLOSA;
	}

	public String getCG_DESCRIPCION() {
		return CG_DESCRIPCION;
	}

	public void setCG_DESCRIPCION(String cG_DESCRIPCION) {
		CG_DESCRIPCION = cG_DESCRIPCION;
	}

	/**
	 * @return the cG_CODIGOGLOSA
	 */
	public String getCG_CODIGOGLOSA() {
		return CG_CODIGOGLOSA;
	}

	/**
	 * @param cG_CODIGOGLOSA the cG_CODIGOGLOSA to set
	 */
	public void setCG_CODIGOGLOSA(String cG_CODIGOGLOSA) {
		CG_CODIGOGLOSA = cG_CODIGOGLOSA;
	}

}
