package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class ParamResultadoExamenDTO {

	private BigDecimal DFE_IDEXAMEN;
	private BigDecimal DFE_NORDEN;
	private Integer edadYear;
	private Integer edadMonth;
	private Integer edadDay;

	public BigDecimal getDFE_NORDEN() {
		return DFE_NORDEN;
	}

	public void setDFE_NORDEN(BigDecimal dFE_NORDEN) {
		DFE_NORDEN = dFE_NORDEN;
	}

	public BigDecimal getDFE_IDEXAMEN() {
		return DFE_IDEXAMEN;
	}

	public void setDFE_IDEXAMEN(BigDecimal dFE_IDEXAMEN) {
		DFE_IDEXAMEN = dFE_IDEXAMEN;
	}

	public Integer getEdadYear() {
		return edadYear;
	}

	public void setEdadYear(Integer edadYear) {
		this.edadYear = edadYear;
	}

	public Integer getEdadMonth() {
		return edadMonth;
	}

	public void setEdadMonth(Integer edadMonth) {
		this.edadMonth = edadMonth;
	}

	public Integer getEdadDay() {
		return edadDay;
	}

	public void setEdadDay(Integer edadDay) {
		this.edadDay = edadDay;
	}

}
