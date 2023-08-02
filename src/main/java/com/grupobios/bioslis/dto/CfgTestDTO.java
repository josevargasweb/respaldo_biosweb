package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class CfgTestDTO {

	public BigDecimal getCT_SORT() {
		return CT_SORT;
	}

	public void setCT_SORT(BigDecimal cT_SORT) {
		CT_SORT = cT_SORT;
	}

	public String getCT_FORMULA() {
		return CT_FORMULA;
	}

	public void setCT_FORMULA(String cT_FORMULA) {
		CT_FORMULA = cT_FORMULA;
	}

	public BigDecimal getCT_IDTEST() {
		return CT_IDTEST;
	}

	public void setCT_IDTEST(BigDecimal cT_IDTEST) {
		CT_IDTEST = cT_IDTEST;
	}

	public String getCT_ABREVIADO() {
		return CT_ABREVIADO;
	}

	public void setCT_ABREVIADO(String cT_ABREVIADO) {
		CT_ABREVIADO = cT_ABREVIADO;
	}

	public String getCT_CODIGO() {
		return CT_CODIGO;
	}

	public void setCT_CODIGO(String cT_CODIGO) {
		CT_CODIGO = cT_CODIGO;
	}

	public String getCT_DESCRIPCION() {
		return CT_DESCRIPCION;
	}

	public void setCT_DESCRIPCION(String cT_DESCRIPCION) {
		CT_DESCRIPCION = cT_DESCRIPCION;
	}

	private BigDecimal CT_IDTEST;
	private BigDecimal CT_SORT;
	private String CT_ABREVIADO;
	private String CT_CODIGO;
	private String CT_FORMULA;
	private String CT_DESCRIPCION;

	public CfgTestDTO() {
	}

}
