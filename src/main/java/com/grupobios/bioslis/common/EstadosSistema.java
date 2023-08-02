/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.common;

/**
 *
 * @author Marco Caracciolo
 */
public class EstadosSistema {

  public static final String DFM_ESTADOTM_RECHAZADA = "R";
  public static final String DFM_ESTADOTM_TOMADA = "T";
  public static final String DFM_ESTADOTM_PENDIENTE = "P";
  public static final String DFM_ESTADOTM_BLOQUEADA = "B";
  public static final String DFM_ESTADORM_RECEPCIONADA = "R";
  public static final String DFM_ESTADORM_PENDIENTE = "P";
  public static final String DFE_CODIGOESTADOEXAMEN_PENDIENTE = "P";
  public static final String DFE_CODIGOESTADOEXAMEN_INGRESADO = "I";
  public static final String DFE_CODIGOESTADOEXAMEN_BLOQUEADO = "B";
  public static final String DFE_CODIGOESTADOEXAMEN_ENPROCESO = "E";
  public static final String DFE_CODIGOESTADOEXAMEN_FIRMADO = "F";
  public static final String DFE_CODIGOESTADOEXAMEN_AUTORIZADO = "A";

  public static final Short DFE_IDESTADOEXAMEN_PENDIENTE = 2;
  public static final Short DFE_IDESTADOEXAMEN_INGRESADO = 1;
  public static final Short DFE_IDESTADOEXAMEN_BLOQUEADO = 3;
  public static final Short DFE_IDESTADOEXAMEN_ENPROCESO = 4;
  public static final Short DFE_IDESTADOEXAMEN_FIRMADO = 5;
  public static final Short DFE_IDESTADOEXAMEN_AUTORIZADO = 8;

  public static final Short DFET_IDRESULTADOTEST_PENDIENTE = 1;
  public static final Short DFET_IDRESULTADOTEST_DIGITADO = 2;
  public static final Short DFET_IDRESULTADOTEST_TRANSMITIDO = 3;
  public static final Short DFET_IDRESULTADOTEST_BLOQUEADO = 4;
  public static final Short DFET_IDRESULTADOTEST_FIRMADO = 5;
  public static final Short DFET_IDRESULTADOTEST_INGRESADO = 7;
  public static final String DMR_RECHAZOPARCIALOTOTAL_TOTAL = "T";
  public static final String DMR_RECHAZOPARCIALOTOTAL_PARCIAL = "P";

  public static final Integer DF_FICHASESTADOSTM_ATENDIDO = 1;
  public static final Integer DF_FICHASESTADOSTM_ESPERA = 2;
  public static final Integer DF_FICHASESTADOSTM_BLOQUEADO = 3;

}
