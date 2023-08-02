package com.grupobios.bioslis.microbiologia.dto;

import java.util.List;

public class MicrobiologiaCapturaResultadoDTO {


	private List<MicrobiologiaTestDTO> TESTLIST;
	private List<MicrobiologiaActionsDTO> ACTIONS;

	public List<MicrobiologiaTestDTO> getTESTLIST() {
		return TESTLIST;
	}
	public void setTESTLIST(List<MicrobiologiaTestDTO> tESTLIST) {
		TESTLIST = tESTLIST;
	}
	public List<MicrobiologiaActionsDTO> getACTIONS() {
		return ACTIONS;
	}
	public void setACTIONS(List<MicrobiologiaActionsDTO> aCTIONS) {
		ACTIONS = aCTIONS;
	}
	


}
