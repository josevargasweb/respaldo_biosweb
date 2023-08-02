/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

/**
 *
 * @author eric.nicholls
 */
public class SendMailDTO {

  private String[] lstIdExamenes;
  private String[] destinatarios;
  private int tipoDeEnvio;

  public int getTipoDeEnvio() {
	return tipoDeEnvio;
}

public void setTipoDeEnvio(int tipoDeEnvio) {
	this.tipoDeEnvio = tipoDeEnvio;
}

public String[] getLstIdExamenes() {
    return lstIdExamenes;
  }

  public void setLstIdExamenes(String[] lstIdExamenes) {
    this.lstIdExamenes = lstIdExamenes;
  }

  public String[] getDestinatarios() {
    return destinatarios;
  }

  public void setDestinatarios(String[] destinatarios) {
    this.destinatarios = destinatarios;
  }

}
