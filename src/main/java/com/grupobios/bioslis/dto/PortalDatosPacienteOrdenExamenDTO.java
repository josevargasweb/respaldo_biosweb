package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/*Resalizado por cristian ---  para portal de pacientes*/
public class PortalDatosPacienteOrdenExamenDTO {

	
	     private String NOMBREPACIENTE	;
	     private String CONVENIO;
	     private String NRODOCUMENTO;
	     private Date FECHA;
	     private String MEDICO;
	     private BigDecimal ORDEN;
	    
	     private List<PortalExamenesDTO> examenes;
	    

		public PortalDatosPacienteOrdenExamenDTO() {
			this.examenes = new ArrayList<PortalExamenesDTO>();
		}


		public String getNOMBREPACIENTE() {
			return NOMBREPACIENTE;
		}


		public void setNOMBREPACIENTE(String nOMBREPACIENTE) {
			NOMBREPACIENTE = nOMBREPACIENTE;
		}


		public String getCONVENIO() {
			return CONVENIO;
		}


		public void setCONVENIO(String cONVENIO) {
			CONVENIO = cONVENIO;
		}


		public String getNRODOCUMENTO() {
			return NRODOCUMENTO;
		}


		public void setNRODOCUMENTO(String nRODOCUMENTO) {
			NRODOCUMENTO = nRODOCUMENTO;
		}


		public Date getFECHA() {
			return FECHA;
		}


		public void setFECHA(Date fECHA) {
			FECHA = fECHA;
		}


		public String getMEDICO() {
			return MEDICO;
		}


		public void setMEDICO(String mEDICO) {
			MEDICO = mEDICO;
		}


		public BigDecimal getORDEN() {
			return ORDEN;
		}


		public void setORDEN(BigDecimal oRDEN) {
			ORDEN = oRDEN;
		}


		public List<PortalExamenesDTO> getExamenes() {
			return examenes;
		}


		public void setExamenes(List<PortalExamenesDTO> examenes) {
			this.examenes = examenes;
		}




	     
}
