package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class LogEventoFichaDtoId {

	private BigDecimal nOrden;
	private BigDecimal idExamen;
	private BigDecimal idTest;
	private BigDecimal idMuestra;

	/**
	 * @return the nOrden
	 */
	public BigDecimal getnOrden() {
		return nOrden;
	}

	/**
	 * @param nOrden the nOrden to set
	 */
	public void setnOrden(BigDecimal nOrden) {
		this.nOrden = nOrden;
	}

	/**
	 * @return the idExamen
	 */
	public BigDecimal getIdExamen() {
		return idExamen;
	}

	/**
	 * @param idExamen the idExamen to set
	 */
	public void setIdExamen(BigDecimal idExamen) {
		this.idExamen = idExamen;
	}

	/**
	 * @return the idTest
	 */
	public BigDecimal getIdTest() {
		return idTest;
	}

	/**
	 * @param idTest the idTest to set
	 */
	public void setIdTest(BigDecimal idTest) {
		this.idTest = idTest;
	}

	/**
	 * @return the idMuestra
	 */
	public BigDecimal getIdMuestra() {
		return idMuestra;
	}

	/**
	 * @param idMuestra the idMuestra to set
	 */
	public void setIdMuestra(BigDecimal idMuestra) {
		this.idMuestra = idMuestra;
	}

	public LogEventoFichaDtoId(BigDecimal nOrden, BigDecimal idExamen, BigDecimal idTest, BigDecimal idMuestra) {
		super();
		this.nOrden = nOrden;
		this.idExamen = idExamen;
		this.idTest = idTest;
		this.idMuestra = idMuestra;
	}

}
