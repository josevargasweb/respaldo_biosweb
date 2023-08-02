class Constante {

  get getctx() { return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2)); }
  get TipoDocRun() { return "1"; }
  get TipoDocPasaporte() { return "2"; }
  get TipoDocRecienNacido() { return "4"; }
  get TipoDocSinIdentificacion() { return "5"; }
  get TipoDocDni() { return "6"; }
  get TipoDocTodos() { return "-1"; }
  get ModoAgregacion() { return "Agregacion"; }
  get ModoEdicion() { return "Edicion"; }
  get ModoBusqueda() { return "Busqueda"; }
  get TipoAtencionAmbulatorio() { return "1"; }
  get TipoAtencionAmbulatorio() { return "1"; }
  get ComboProcedenciaDefault() { return "-1"; }
  get ComboServicioDefault() { return "-1"; }
  get DiagnosticoSinEspecificar() {return 1;}
  get idSeccionExcluida() {return 16;}
  get nroMaxOrdenesHistoricas() {return 5;}
  get first() {return 0;};  
  get prev() {return 1;};  
  get next() {return 2;};  
  get last() {return 3;};  
  get idMicrobiologiaGtd() {return 16;};
  get idMicrobiologiaCloud() {return 5;};
}

var gCte = new Constante();


const g_ambulatorio = gCte.TipoAtencionAmbulatorio;
const g_jqIdSalas = "#SalaComboBoxM";
const g_jqIdCamas = "#CamaComboBoxM";
const g_jqIdServicios = "#ServicioComboBoxM";
const g_jqIdTipoAtencion = "#tipoDeAtencion"
const g_currentList = "Paneles";
const g_jqIdProcedencias = "#ProcedenciaComboBoxM";

var gCodTipoAtencion = 'A';
var gCodProcedencia = 'SIN PROCEDENCIA';
var gProcedenciaObligatoria = (gCodTipoAtencion === 'A');
var gLocalizacionObligatoria = !gProcedenciaObligatoria;

var host = window.location.protocol + "//" + window.location.host;
//console.log("host :" + host)
var gIdSecccionMicrobiologia;

if (host === ":http://144.22.44.112:9080"){ //Ambiente cloud
    gIdSecccionMicrobiologia = gCte.idMicrobiologiaCloud;
} else {
    gIdSecccionMicrobiologia = gCte.idMicrobiologiaGtd;
}


$(document).ready(function() {
  $('.selectpicker').selectpicker({
    noneSelectedText: '',
    noneResultsText: 'No hay resultados'
  });
});
