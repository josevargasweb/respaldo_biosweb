package com.grupobios.bioslis.common;



public class ResponseTemplate{
	
	private Integer status;
	private String message;
	private Object dato;
	
	public ResponseTemplate(Object data,Integer status, String message) {
		
		this.dato = data;
		this.status = status;
		this.message = message;
		
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDato() {
		return dato;
	}

	public void setDato(Object dato) {
		this.dato = dato;
	}


	
	
	
	
}
