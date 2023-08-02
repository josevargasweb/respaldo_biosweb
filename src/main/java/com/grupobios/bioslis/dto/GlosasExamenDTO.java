package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.List;

public class GlosasExamenDTO {

	BigDecimal CE_IDEXAMEN;
	List<GlosaDTO> lstglosas;

	/**
	 * @return the cE_IDEXAMEN
	 */
	public BigDecimal getCE_IDEXAMEN() {
		return CE_IDEXAMEN;
	}

	/**
	 * @param cE_IDEXAMEN the cE_IDEXAMEN to set
	 */
	public void setCE_IDEXAMEN(BigDecimal cE_IDEXAMEN) {
		CE_IDEXAMEN = cE_IDEXAMEN;
	}

	/**
	 * @return the lstglosas
	 */
	public List<GlosaDTO> getLstglosas() {
		return lstglosas;
	}

	/**
	 * @param lstglosas the lstglosas to set
	 */
	public void setLstglosas(List<GlosaDTO> lstglosas) {
		this.lstglosas = lstglosas;
	}
}
