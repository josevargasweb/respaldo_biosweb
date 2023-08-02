/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Marco Caracciolo
 */
public class ViewNames {

  public static final int ID_CAMBIO_PASSWORD = -1;
  public static final int ID_HOME = 0;
  public static final int ID_REGISTRO_PACIENTE = 2;
  public static final int ID_MODIFICAR_PACIENTE = 3;
  public static final int ID_ORDEN = 4;
  public static final int ID_EDICION_ORDENES = 5;
  public static final int ID_REASIGNACION_ORDENES = 6;
  public static final int ID_REGISTRO_DOCUMENTOS = 7;
  public static final int ID_TOMA_MUESTRAS = 9;
  public static final int ID_RECEPCION_MUESTRAS = 10;
  public static final int ID_INDICACIONES = 69;
  public static final int ID_RECHAZO_MUESTRAS = 12;
  public static final int ID_CAPTURA_RESULTADOS = 14;
  public static final int ID_INFORMES = 15;
  public static final int ID_INFORME_RESULTADOS = 16;
  public static final int ID_MICROBIOLOGIA = 18;
  public static final int ID_MICROBIOLOGIA_TAREAS = 19;
  public static final int ID_MICROBIOLOGIA_TAREASPENDIENTES = 20;
  public static final int ID_MICROBIOLOGIA_MICROORGANISMOS = 22;
  public static final int ID_MICROBIOLOGIA_ANTIBIOTICOS = 23;
  public static final int ID_MICROBIOLOGIA_ANTIBIOGRAMAS = 24;
  public static final int ID_MICROBIOLOGIA_RECUENTOCOLONIAS = 25;
  public static final int ID_MICROBIOLOGIA_ZONASCUERPO = 26;
  public static final int ID_MICROBIOLOGIA_PRUEBASMANUALES = 27;
  public static final int ID_MICROBIOLOGIA_MEDIOSCULTIVO = 28;
  public static final int ID_MICROBIOLOGIA_METODOSRESISTENCIA = 29;
  //public static final int ID_MICROBIOLOGIA_REGLASALERTAS = 30;
  public static final int ID_CONFIGURACION_EXAMEN = 33;
  public static final int ID_CONFIGURACION_TEST = 34;
  public static final int ID_CONFIGURACION_MUESTRAS = 35;
  public static final int ID_CONFIGURACION_ENVASES = 36;
  public static final int ID_CONFIGURACION_UNIDADES_MEDIDAS = 37;
  public static final int ID_CONFIGURACION_METODOS = 38;
  public static final int ID_CONFIGURACION_FORMULAS = 39;
  public static final int ID_CONFIGURACION_GLOSAS = 40;
  public static final int ID_CONFIGURACION_VALORES_REFERENCIAS = 41;
  public static final int ID_REGISTRO_MEDICO = 43;
  public static final int ID_CONFIGURACION_DIAGNOSTICOS = 44;
  public static final int ID_CONFIGURACION_PRIORIDAD_ATENCION = 45;
  public static final int ID_CONFIGURACION_NOTAS = 46;
  public static final int ID_CAUSAS_RECHAZO_MUESTRAS = 47;
  public static final int ID_CONFIGURACION_CENTROS_SALUD = 49;
  public static final int ID_CONFIGURACION_PROCEDENCIAS = 50;
  public static final int ID_CONFIGURACION_LABORATORIOS = 51;
  public static final int ID_CONFIGURACION_SECCIONES = 52;
  public static final int ID_CONFIGURACION_DERIVADORES = 53;
  public static final int ID_CONFIGURACION_SERVICIOS = 54;
  public static final int ID_LOCALIZACION_SALAS_CAMAS = 55;
  public static final int ID_REGISTRO_USUARIOS = 58;
  public static final int ID_CONFIGURACION_PERMISOS = 59;
  public static final int ID_CONFIGURACION_PANELES_EXAMENES = 60;
  public static final int ID_IMPRESION_ETIQUETAS = 61;
  public static final int ID_CONFIGURACION_ANALIZADORES = 63;
  public static final int ID_CONFIGURACION_PROCESOS = 64;
  public static final int ID_CONFIGURACION_ETIQUETAS = 65;
  public static final int ID_PORTAL_ESTADISTICA = 67;
  public static final int ID_FIRMA_MASIVA_RESULTADOS = 68;
  public static final int ID_INDICACIONESTM = 70;
  
  public static final int ID_ACCIONES_ORDENES = 72;
  public static final int ID_ACCIONES_USUARIOS = 73;
  
  public static final int ID_TRANSPORTE_MUESTRAS = 74;

  public static final String CAMBIO_PASSWORD = "CambioPassword";
  public static final String CAPTURA_RESULTADOS = "CapturaResultados";
  public static final String CAUSAS_RECHAZO_MUESTRAS = "CausasRechazoMuestras";
  public static final String CONFIGURACION_CENTROS_SALUD = "ConfiguracionCentrosDeSalud";
  public static final String CONFIGURACION_DERIVADORES = "ConfiguracionDerivadores";
  public static final String CONFIGURACION_ENVASES = "ConfiguracionEnvases";
  public static final String CONFIGURACION_EXAMEN = "ConfiguracionExamen";
  public static final String CONFIGURACION_FORMULAS = "ConfiguracionFormulas";
  public static final String CONFIGURACION_GLOSAS = "ConfiguracionGlosas";
  public static final String CONFIGURACION_LABORATORIOS = "ConfiguracionLaboratorios";
  public static final String CONFIGURACION_METODOS = "ConfiguracionMetodos";
  public static final String CONFIGURACION_MUESTRAS = "ConfiguracionMuestras";
  public static final String CONFIGURACION_NOTAS = "ConfiguracionNotas";
  public static final String CONFIGURACION_PANELES_EXAMENES = "ConfiguracionPanelesExamenes";
  public static final String CONFIGURACION_PERMISOS = "ConfiguracionPermisos";
  public static final String CONFIGURACION_PRIORIDAD_ATENCION = "ConfiguracionPrioridadAtencion";
  public static final String CONFIGURACION_PROCEDENCIAS = "ConfiguracionProcedencias";
  public static final String CONFIGURACION_SECCIONES = "ConfiguracionSecciones";
  public static final String CONFIGURACION_SERVICIOS = "ConfiguracionServicios";
  public static final String CONFIGURACION_TEST = "ConfiguracionTest";
  public static final String CONFIGURACION_UNIDADES_MEDIDAS = "ConfiguracionUnidadesMedidas";
  public static final String CONFIGURACION_VALORES_REFERENCIAS = "ConfiguracionValoresReferencias";
  public static final String EDICION_ORDENES = "EdicionOrdenes";
  public static final String HOME = "Home";
  public static final String INDICACIONES = "Indicaciones";
  public static final String INFORME_RESULTADOS = "InformeResultados";
  public static final String JASPER = "Jasper";
  public static final String LOCALIZACION_SALAS_CAMAS = "LocalizacionSalasCamas";
  public static final String LOGIN = "Login";
  public static final String LOGOUT = "Logout";
  public static final String MICROBIOLOGIA = "Microbiologia";
  public static final String MICROBIOLOGIA_TAREAS = "MicrobiologiaTareas";
  public static final String MICROBIOLOGIA_TAREASPENDIENTES = "MicrobiologiaTareasPendientes";
  public static final String MICROBIOLOGIA_MICROORGANISMOS = "MicrobiologiaMantenedoresMicroorganismos";
  public static final String MICROBIOLOGIA_ANTIBIOTICOS = "MicrobiologiaMantenedoresAntibioticos";
  public static final String MICROBIOLOGIA_ANTIBIOGRAMAS = "MicrobiologiaMantenedoresAntibiogramas";
  public static final String MICROBIOLOGIA_RECUENTOCOLONIAS = "MicrobiologiaMantenedoresRecuentoColonias";
  public static final String MICROBIOLOGIA_ZONASCUERPO = "MicrobiologiaMantenedoresZonasCuerpo";
  public static final String MICROBIOLOGIA_PRUEBASMANUALES = "MicrobiologiaMantenedoresPruebasManuales";
  public static final String MICROBIOLOGIA_MEDIOSCULTIVO = "MicrobiologiaMantenedoresMediosCultivo";
  public static final String MICROBIOLOGIA_METODOSRESISTENCIA = "MicrobiologiaMantenedoresMetodosResistencia";
  public static final String ORDEN = "Orden";
  public static final String PDF_INFORME_RESULTADOS = "PdfInformeResultados";
  public static final String REASIGNACION_ORDENES = "ReasignacionOrdenes";
  public static final String RECEPCION_MUESTRAS = "RecepcionMuestras";
  public static final String RECHAZO_MUESTRAS = "RechazoMuestras";
  public static final String REGISTRO_DOCUMENTOS = "RegistroDocumentos";
  public static final String REGISTRO_MEDICO = "RegistroMedico";
  public static final String REGISTRO_PACIENTE = "RegistroPaciente";
  public static final String REGISTRO_PACIENTE_FRONT = "RegistroPacienteFront";
  public static final String REGISTRO_USUARIOS = "RegistroUsuarios";
  public static final String TOMA_MUESTRAS = "TomaMuestras";
  public static final String IMPRESION_ETIQUETAS = "ImpresionEtiquetas";
  public static final String CONFIGURACION_ANALIZADORES = "ConfiguracionAnalizadores";
  public static final String CONFIGURACION_PROCESOS = "ConfiguracionProcesos";
  public static final String CONFIGURACION_ETIQUETAS = "ConfiguracionEtiquetas";
  public static final String PORTAL_ESTADISTICA = "PortalEstadistica";
  public static final String FIRMA_MASIVA_RESULTADOS = "FirmaMasivaResultados";
  public static final String CONFIGURACION_DIAGNOSTICOS = "ConfiguracionDiagnosticos";
  public static final String INDICACIONESTM = "IndicacionesTM";
  
  public static final String ACCIONESORDENES = "AccionesOrdenes";
  public static final String ACCIONESUSUARIOS = "AccionesUsuarios";
  
  public static final String TRANSPORTEMUESTRAS = "TransporteMuestras";

  public static Map<Integer, String> viewModulos = createMap();

  private static Map<Integer, String> createMap() {
    Map<Integer, String> modulos = new HashMap<>();
    modulos.put(ID_HOME, HOME);
    modulos.put(ID_CAMBIO_PASSWORD, CAMBIO_PASSWORD);
    modulos.put(ID_REGISTRO_PACIENTE, REGISTRO_PACIENTE);
    modulos.put(ID_MODIFICAR_PACIENTE, REGISTRO_PACIENTE+"?BuscarPaciente=true");
    modulos.put(ID_ORDEN, ORDEN);
    modulos.put(ID_EDICION_ORDENES, EDICION_ORDENES);
    modulos.put(ID_REASIGNACION_ORDENES, REASIGNACION_ORDENES);
    modulos.put(ID_REGISTRO_DOCUMENTOS, REGISTRO_DOCUMENTOS);
    modulos.put(ID_TOMA_MUESTRAS, TOMA_MUESTRAS);
    modulos.put(ID_RECEPCION_MUESTRAS, RECEPCION_MUESTRAS);
    modulos.put(ID_INDICACIONES, INDICACIONES);
    modulos.put(ID_RECHAZO_MUESTRAS, RECHAZO_MUESTRAS);
    modulos.put(ID_CAPTURA_RESULTADOS, CAPTURA_RESULTADOS);
    modulos.put(ID_MICROBIOLOGIA, MICROBIOLOGIA);
    modulos.put(ID_MICROBIOLOGIA_TAREAS, MICROBIOLOGIA_TAREAS);
    modulos.put(ID_MICROBIOLOGIA_TAREASPENDIENTES, MICROBIOLOGIA_TAREASPENDIENTES);
    modulos.put(ID_MICROBIOLOGIA_MICROORGANISMOS, MICROBIOLOGIA_MICROORGANISMOS);
    modulos.put(ID_MICROBIOLOGIA_ANTIBIOTICOS, MICROBIOLOGIA_ANTIBIOTICOS);
    modulos.put(ID_MICROBIOLOGIA_ANTIBIOGRAMAS, MICROBIOLOGIA_ANTIBIOGRAMAS);
    modulos.put(ID_MICROBIOLOGIA_RECUENTOCOLONIAS, MICROBIOLOGIA_RECUENTOCOLONIAS);
    modulos.put(ID_MICROBIOLOGIA_ZONASCUERPO, MICROBIOLOGIA_ZONASCUERPO);
    modulos.put(ID_MICROBIOLOGIA_PRUEBASMANUALES, MICROBIOLOGIA_PRUEBASMANUALES);
    modulos.put(ID_MICROBIOLOGIA_MEDIOSCULTIVO, MICROBIOLOGIA_MEDIOSCULTIVO);
    modulos.put(ID_MICROBIOLOGIA_METODOSRESISTENCIA, MICROBIOLOGIA_METODOSRESISTENCIA);
    modulos.put(ID_CONFIGURACION_EXAMEN, CONFIGURACION_EXAMEN);
    modulos.put(ID_CONFIGURACION_TEST, CONFIGURACION_TEST);
    modulos.put(ID_CONFIGURACION_MUESTRAS, CONFIGURACION_MUESTRAS);
    modulos.put(ID_CONFIGURACION_ENVASES, CONFIGURACION_ENVASES);
    modulos.put(ID_CONFIGURACION_UNIDADES_MEDIDAS, CONFIGURACION_UNIDADES_MEDIDAS);
    modulos.put(ID_CONFIGURACION_METODOS, CONFIGURACION_METODOS);
    modulos.put(ID_CONFIGURACION_FORMULAS, CONFIGURACION_FORMULAS);
    modulos.put(ID_CONFIGURACION_GLOSAS, CONFIGURACION_GLOSAS);
    modulos.put(ID_CONFIGURACION_VALORES_REFERENCIAS, CONFIGURACION_VALORES_REFERENCIAS);
    modulos.put(ID_REGISTRO_MEDICO, REGISTRO_MEDICO);
    modulos.put(ID_CONFIGURACION_PRIORIDAD_ATENCION, CONFIGURACION_PRIORIDAD_ATENCION);
    modulos.put(ID_CONFIGURACION_NOTAS, CONFIGURACION_NOTAS);
    modulos.put(ID_CAUSAS_RECHAZO_MUESTRAS, CAUSAS_RECHAZO_MUESTRAS);
    modulos.put(ID_CONFIGURACION_CENTROS_SALUD, CONFIGURACION_CENTROS_SALUD);
    modulos.put(ID_CONFIGURACION_PROCEDENCIAS, CONFIGURACION_PROCEDENCIAS);
    modulos.put(ID_CONFIGURACION_LABORATORIOS, CONFIGURACION_LABORATORIOS);
    modulos.put(ID_CONFIGURACION_SECCIONES, CONFIGURACION_SECCIONES);
    modulos.put(ID_CONFIGURACION_DERIVADORES, CONFIGURACION_DERIVADORES);
    modulos.put(ID_CONFIGURACION_SERVICIOS, CONFIGURACION_SERVICIOS);
    modulos.put(ID_LOCALIZACION_SALAS_CAMAS, LOCALIZACION_SALAS_CAMAS);
    modulos.put(ID_REGISTRO_USUARIOS, REGISTRO_USUARIOS);
    modulos.put(ID_CONFIGURACION_PERMISOS, CONFIGURACION_PERMISOS);
    modulos.put(ID_CONFIGURACION_PANELES_EXAMENES, CONFIGURACION_PANELES_EXAMENES);
    modulos.put(ID_IMPRESION_ETIQUETAS, IMPRESION_ETIQUETAS);
    modulos.put(ID_CONFIGURACION_ANALIZADORES, CONFIGURACION_ANALIZADORES);
    modulos.put(ID_INFORME_RESULTADOS, INFORME_RESULTADOS);
    modulos.put(ID_INFORMES, INFORME_RESULTADOS);
    modulos.put(ID_CONFIGURACION_PROCESOS, CONFIGURACION_PROCESOS);
    modulos.put(ID_CONFIGURACION_ETIQUETAS, CONFIGURACION_ETIQUETAS);
    modulos.put(ID_PORTAL_ESTADISTICA, PORTAL_ESTADISTICA);
    modulos.put(ID_FIRMA_MASIVA_RESULTADOS, FIRMA_MASIVA_RESULTADOS);
    modulos.put(ID_CONFIGURACION_DIAGNOSTICOS, CONFIGURACION_DIAGNOSTICOS);
    modulos.put(ID_INDICACIONESTM, INDICACIONESTM);
    
    modulos.put(ID_ACCIONES_ORDENES, ACCIONESORDENES);
    modulos.put(ID_ACCIONES_USUARIOS, ACCIONESUSUARIOS);
    
    modulos.put(ID_TRANSPORTE_MUESTRAS, TRANSPORTEMUESTRAS);
    return modulos;
  }
}
