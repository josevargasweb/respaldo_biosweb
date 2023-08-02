package com.grupobios.bioslis.common;


public class ResponseTemplateGen <T>{
	
	private Integer status;
	private String message;
	private T dato;
	
	public ResponseTemplateGen(T data,Integer status, String message) {
		
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

	public T getDato() {
		return dato;
	}

	public void setDato(T dato) {
		this.dato = dato;
	}


	
	
	
	
}
