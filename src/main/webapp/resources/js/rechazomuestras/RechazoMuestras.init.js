//var getctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

var today = new Date();
today = convertirDateDDMMYYYY(today);
var dia = today.substring(0, 2);
var mes = today.substring(3, 5);
var year = today.substring(6, 10);
//today = "12/03/2021";
var tablaOrdenesRechazo = null;
var tablaMuestrasRechazo = null;
var cfgMuestras = new CfgMuestras();
var cfgEnvases = new CfgEnvases();
var nOrden = null;
var listaExamenesMuestra = null;
var idMuestraRechazar = null;
const sesionUsr = $('#sesionUsuario').val();

$(document).ready(function (){
    $("#causaRechazoMuestras").prop("disabled", true);
    $('.ocultarTodoModalAntecedentes').hide();
    $(".ocultar").hide();
    $('#filtroFecha').val(today);
    let errorPageLoad = $("#errorPageLoad").val();
    if (typeof errorPageLoad !== 'undefined' && errorPageLoad!==null){
        handlerMessageError(errorPageLoad);
    }
    $('.selectpicker').selectpicker('refresh');
    });

$('#txtFecha').datepicker({
    language: 'es',
    endDate: "+Infinity",
    autoclose: true
})

$("#txtNOrden").keyup(function () {
    if ($("#txtNOrden").val().length > 0) {
        $("#txtFecha").prop("disabled", true);
        $("#txtFecha").val("");
        $("#txtFiltroNombre").prop("disabled", true);
        $("#txtFiltroNombre").val("");
        $("#txtFiltroApellido").prop("disabled", true);
        $("#txtFiltroApellido").val("");
        $("#txtNDocumento").prop("disabled", true);
        $("#txtNDocumento").val("");
        $("#selectTipoDocumentoFiltro").prop("disabled", true);
        $("#selectTipoDocumentoFiltro").val(-1);
        $('#codCentroFiltro').selectpicker('refresh');
    } else {
        $("#txtFecha").prop("disabled", false);
        $("#txtFiltroNombre").prop("disabled", false);
        $("#txtFiltroApellido").prop("disabled", false);
        $("#selectTipoDocumentoFiltro").prop("disabled", false);
        $('#codCentroFiltro').selectpicker('refresh');
    }
});

$("#selectTipoDocumentoFiltro").change(function () {
    if ($("#selectTipoDocumentoFiltro").val() != -1) {
        $("#txtNDocumento").prop("disabled", false);
    } else {
        $("#txtNDocumento").prop("disabled", true);
    }
});

$("#causaRechazoMuestras").on('change', function () {
    let causaRechazo = $("#causaRechazoMuestras").val();
    if (causaRechazo != 0){
        $("#causaRechazoMuestras")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
        $('.selectpicker').selectpicker('refresh');
    }
});