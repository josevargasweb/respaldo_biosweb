/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.CfgBioslismodulos;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class ItemsMenuDTO {
    
   // private CfgBioslismodulos bioslismodulos;
    
    private String url;
    private String icono;
    private String tieneAcceso;
    
    private BigDecimal CBMIDBIOSLISMODULO;
    private String CBMNOMBREMODULO;
    private BigDecimal CBMPRIMERNIVEL;
    private BigDecimal CBMSEGUNDONIVEL;
    private BigDecimal CBMTERCERNIVEL;
    private String CBMACTIVO;
    
    private List<ItemsMenuDTO> subitems;


    public BigDecimal getCBMIDBIOSLISMODULO() {
		return CBMIDBIOSLISMODULO;
	}
	public void setCBMIDBIOSLISMODULO(BigDecimal cBMIDBIOSLISMODULO) {
		CBMIDBIOSLISMODULO = cBMIDBIOSLISMODULO;
	}
	public String getCBMNOMBREMODULO() {
		return CBMNOMBREMODULO;
	}
	public void setCBMNOMBREMODULO(String cBMNOMBREMODULO) {
		CBMNOMBREMODULO = cBMNOMBREMODULO;
	}

	public String getCBMACTIVO() {
		return CBMACTIVO;
	}
	public void setCBMACTIVO(String cBMACTIVO) {
		CBMACTIVO = cBMACTIVO;
	}
	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getTieneAcceso() {
        return tieneAcceso;
    }

    public void setTieneAcceso(String tieneAcceso) {
        this.tieneAcceso = tieneAcceso;
    }
    
    public List<ItemsMenuDTO> getSubitems() {
        return subitems;
    }

    public void setSubitems(List<ItemsMenuDTO> subitems) {
        this.subitems = subitems;
    }
	public BigDecimal getCBMPRIMERNIVEL() {
		return CBMPRIMERNIVEL;
	}
	public void setCBMPRIMERNIVEL(BigDecimal cBMPRIMERNIVEL) {
		CBMPRIMERNIVEL = cBMPRIMERNIVEL;
	}
	public BigDecimal getCBMSEGUNDONIVEL() {
		return CBMSEGUNDONIVEL;
	}
	public void setCBMSEGUNDONIVEL(BigDecimal cBMSEGUNDONIVEL) {
		CBMSEGUNDONIVEL = cBMSEGUNDONIVEL;
	}
	public BigDecimal getCBMTERCERNIVEL() {
		return CBMTERCERNIVEL;
	}
	public void setCBMTERCERNIVEL(BigDecimal cBMTERCERNIVEL) {
		CBMTERCERNIVEL = cBMTERCERNIVEL;
	}
    

}
