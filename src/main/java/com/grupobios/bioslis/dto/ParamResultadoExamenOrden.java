package com.grupobios.bioslis.dto;

import java.util.List;

public class ParamResultadoExamenOrden {

	private ParamPaciente paciente;

	private List<ParamResultadoExamenDTO> examenes;

	public ParamPaciente getPaciente() {
		return paciente;
	}

	public void setPaciente(ParamPaciente paciente) {
		this.paciente = paciente;
	}

	public List<ParamResultadoExamenDTO> getExamenes() {
		return examenes;
	}

	public void setExamenes(List<ParamResultadoExamenDTO> examenes) {
		this.examenes = examenes;
	}

	@Override
	public String toString() {
		return "ParamResultadoExamenOrden [paciente=" + paciente + ", examenes=" + examenes + ", getPaciente()="
				+ getPaciente() + ", getExamenes()=" + getExamenes() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	
}
