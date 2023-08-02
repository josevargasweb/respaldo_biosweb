/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

/**
 *
 * @author JanPer
 */
public class ValidacionMantenedoresDTO {
	private BigDecimal ID;
	private String CODIGO;
	private String ABREVIADO;
	
	public BigDecimal getID() {
		return ID;
	}
	public void setID(BigDecimal iD) {
		ID = iD;
	}
	public String getCODIGO() {
		return CODIGO;
	}
	public void setCODIGO(String cODIGO) {
		CODIGO = cODIGO;
	}
	public String getABREVIADO() {
		return ABREVIADO;
	}
	public void setABREVIADO(String aBREVIADO) {
		ABREVIADO = aBREVIADO;
	}
	
	
}
