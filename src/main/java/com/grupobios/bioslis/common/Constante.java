package com.grupobios.bioslis.common;

import java.math.BigDecimal;

public class Constante {

  private Constante() {
  }

  public static final String LDVS_OTRO = "O";
  public static final Integer LDVS_IDOTRO = 0;
  public static final String LDVS_FEMENINO = "F";
  public static final Integer LDVS_IDFEMENINO = 1;
  public static final String LDVS_MASCULINO = "M";
  public static final Integer LDVS_IDMASCULINO = 2;
  public static final String CVRAMBOSSEXOS = "A";
  public static final Integer CFG_PROCEDENCIAS_SIN_PROCEDENCIA = 0;
  public static final Integer CFG_SERVICIOS_SIN_SERVICIO = 0;
  public static final Integer SIN_SERVICIO = -1;
  public static final Integer SIN_PROCEDENCIA = -1;
  public static final Integer SIN_LOCALIZAR = 0;
  public static final Integer SIN_TIPOATENCION = 4;
  public static final Integer ESTADOFICHAESPERA = 2;

  public static final int LDVTD_RUN = 1;
  public static final int LDVTD_PASAPORTE = 2;
  public static final int LDVTD_RECIENNACIDO = 4;
  public static final int LDVTD_SINIDENTIFICACION = 5;
  public static final int LDVTD_SINTIPO = 6;
  public static final BigDecimal IR = new BigDecimal(1L);// IR INSERTA REGISTRO
  public static final BigDecimal AR = new BigDecimal(2L);// AR ACTUALIZA REGISTRO
  public static final BigDecimal ER = new BigDecimal(3L);// ER ELIMINAR REGISTRO

  public static final BigDecimal CREACION_DATOS_FICHAS = new BigDecimal(1L);
  public static final BigDecimal MODIFICA_DATOS_FICHAS = new BigDecimal(2L);
  public static final BigDecimal ELIMINA_DATOS_FICHAS = new BigDecimal(3L);
  public static final BigDecimal AGREGA_EXAMEN = new BigDecimal(4L);
  public static final BigDecimal MODIFICA_EXAMEN = new BigDecimal(5L);
  public static final BigDecimal ELIMINA_EXAMEN = new BigDecimal(6L);
  public static final BigDecimal AGREGA_USUARIO = new BigDecimal(7L);
  public static final BigDecimal MODIFICA_USUARIO = new BigDecimal(8L);
  public static final BigDecimal ELIMINA_USUARIO = new BigDecimal(9L);

  public static final String PROPERTY_TABLE_NAME = "TABLE_NAME";
  public static final String SCHEMA = "BIOSLIS";
  public static final String SEPARADOR_MILES = "\\.";
  public static final String SEPARADOR_DECIMALES = "\\,";
  public static final int CD_IDDIAGNOSTICO_SIN_ESPECIFICAR = 1;

  public static final String DIGITOS_ENC_PASAPORTE = "ABC-D";
  public static final String DIGITOS_ENC_RN = "ABC-D";
  public static final String DIGITOS_ENC_NN = "ABC-D";

  public static final String CODIGO_NO_MICROBIOLOGIA = "TODOS EXCEPTO MICROBIOLOGÍA";
  public static final String DESCRIPCION_NO_MICROBIOLOGIA = "TODOS EXCEPTO MICROBIOLOGÍA";
  public static final Integer IDSECCION_NO_MICROBIOLOGIA = -1;
  public static final String CTR_FORMULA = "F";

  // Agregado por Marco Caracciolo 13/12/2022
  public static final String ESTADO_ACTIVO = "S";
  public static final String ESTADO_NO_ACTIVO = "N";
  /**********************************************************/

  /******************** Configuración de fórmulas **********************/
  public static final String OPERADOR_MENOR = "<";
  public static final String OPERADOR_MENOROIGUAL = "<=";
  public static final String OPERADOR_MAYOR = ">";
  public static final String OPERADOR_MAYOROIGUAL = ">=";
  public static final String OPERADOR_IGUAL = "=";
  public static final String OPERADOR_DISTINTO = "!=";

  public static final int ID_ANTECEDENTE_SEXO = 11;
  public static final int ID_ANTECEDENTE_AFRO = 12;
  public static final String VALOR_SEXO_HOMBRE = "M";
  public static final String VALOR_AFRO = "S";
  public static final String VALOR_SEXO_MUJER = "F";
  public static final String VALOR_NOAFRO = "N";

  public static final Integer TIPORESULTADO_SIN_ESPECIFICAR = 1;
  public static final Integer TIPORESULTADO_GLOSA = 2;
  public static final Integer TIPORESULTADO_NUMERICO = 3;
  public static final Integer TIPORESULTADO_TEXTO = 5;
  public static final Integer TIPORESULTADO_FORMULA = 6;
  public static final Integer TIPORESULTADO_IMAGEN = 9;
  public static final Integer TIPORESULTADO_GLOSA_MULTIPLE = 11;
  public static final Integer NOTIFICACION_EXAMEN = 2;
  public static final Integer NOTIFICACION_TODOS = 1;
  public static final Integer NOTIFICACION_UNICA = 0;
  public static final String NOTIFICA_ANULACION = "A";
  public static final String NOTIFICA_ERROR = "E";

  // Agregado por Marco C 27/04/2023
  public static final Integer TIPOEXAMEN_MICROBIOLOGIA = 4;

// Colocar aqui los http status que deben devolver los rest  
  public static final Integer HTTP_STATUS_NOSESION = 401;
  public static final Integer HTTP_STATUS_NOTFOUND = 404;
  public static final Integer HTTP_STATUS_ERROR = 500;

}
