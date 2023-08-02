var intervalo = null;
var estado = 1;
//var getctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
var today = new Date();
var fecha = convertirDateDDMMYYYY(today);
var fechaFormat = convertirFechayHora(today);
///var dia = fecha.substring(0, 2);
//var mes = fecha.substring(3, 5);
//var year = fecha.substring(6, 10);
var cfgMuestras = new CfgMuestras();
var cfgEnvases = new CfgEnvases();
$("#modalAntecedentesRM").prop('disabled', true);

$(document).ready(function () {
    $('.ocultarTodoModalAntecedentes').hide();
    $('#guardarAntecedentes').hide();
    $(".ocultar").hide();
    rellenarTablaMuestrasPendientes();
    rellenarTablaMuestrasRecepcionadas();
    rellenarTablaMuestrasConDerivador();
});


function CfgDerivadores() {
    this.misDerivadores = [];
  }


  CfgDerivadores.prototype.getDerivadores = function(testID, idSeccion) {
    var jqXHR = $.ajax({
      url: getctx + "/api/dominio/derivadores/list",
      async: false,
    });
    this.misDerivadores = jqXHR.responseJSON;
  };


  function CfgUsuariosR() {
    this.misUsuarios = [];
  }


  CfgUsuariosR.prototype.getUsuarios = function(testID, idSeccion) {
    var jqXHR = $.ajax({
      url: getctx + "/api/recepcionMuestras/recepcionistas/list",
      async: false,
    });
    this.misUsuarios = jqXHR.responseJSON;
  };


  let CfgDerivadores2 = new CfgDerivadores();
  CfgDerivadores2.getDerivadores();

  let CfgUsuariosR2 = new CfgUsuariosR();
  CfgUsuariosR2.getUsuarios();



//Botón actualizar
$('#actualizarTabla').click(function() {
    actualizar();
});

var intervalo = setInterval( function () {
    if(estado === 0){
        console.log("actualizando")
        actualizar();
    }
}, 50000 );

//Actualización automática
$('#chkActivos').change(function(){
    console.log()
    if ($(this).is(':checked')){
        estado = 0;
        intervalo = setInterval( function () {
            actualizar();
        }, 10000 );
    }
    if(!$(this).is(':checked')){
        estado = 1;
        console.log("desactivando actualizacion")
        clearInterval(intervalo);
    }else{
        
    }
    console.log("estado: " +estado);
});

$("#txtCodigoBarras").keyup(function (e) {
    if (e.key === 'Enter' || e.keyCode === 13) {
        try {
            recepcionarMuestraCodBarra($("#txtCodigoBarras").val().toUpperCase());
        } catch (error) {

        }
    }
});

function myCallbackFunction(updatedCell, updatedRow, oldValue) {
    console.log("The new value for the cell is: " + updatedCell.data());
    console.log("The old value for that cell was: " + oldValue);
    console.log("The values for each cell in that row are: " + updatedRow.data());
}

//Buscar Muestra
$("div#divTablaPendientes tbody, #tablaRecepcionadas tbody, #tablaDerivadas tbody").on('click', 'tr', function() {
    var $row = $(this).closest("tr"); // Find the row
    var idMuestra = $row.find(".idMuestra").text(); // Find the text
    var nroMuestra = $row.find(".nroMuestra").text();
    let contenedor = $(this).parent();
    //elimina todos los resaltados
    $("#tbodyFiltro").find("tr.muestra-blue").removeClass("muestra-blue");
    $("#tbodyRecepcionadasFiltro").find("tr.muestra-blue").removeClass("muestra-blue");
    $("#tbodyPendientesFiltro").find("tr.muestra-blue").removeClass("muestra-blue");
     // agrega una clase a elemento elegido
    $(this).closest("tr").addClass("muestra-blue");
    seleccionarMuestraCentral(nroMuestra);
    modificarFiltroOrden($(this).data('orden'));
    getMuestraById(idMuestra);
    resaltarMuestra(nroMuestra);
});

$('div#divTablaRecepcionadas').on('hidden.bs.collapse', function () {
    $('div#divTablaDerivadas').collapse('show');
  });

$('div#divTablaDerivadas').on('hidden.bs.collapse', function () {
    $('div#divTablaRecepcionadas').collapse('show');
  });

function buscarMuestraPrueba(){
    alert("probando")
}

function resaltarMuestra(nroMuestra) {
    $("#" + nroMuestra + "").css("background-color", "#6993FF");
    $("#" + nroMuestra + "").css("color", "#FFFFFF");
}

function calcularEdadRM(f){
    let dia = f.substring(8, 10);
    let mes = f.substring(5, 7);
    let anio = f.substring(0, 4);
    let fecha = dia + "-" + mes + "-" + anio;

    fecha = getAge(fecha, 3);
    fecha = fecha.split("-");

    let edad = "";
    if (parseInt(fecha[0])>0){
        edad = fecha[0] + " Años ";
    } else if (parseInt(fecha[1])>0) {
        edad = fecha[1] + " Meses ";
    } else {
        fecha[2] + " Dias";
    }

    return edad;
}

function getMuestraById(idMuestra) {
    $.ajax({
        type: "get",
        url: getctx+"/api/recepcionMuestras/datosOrden/"+idMuestra,
        datatype: "json",
        success: function (muestra) {
            $("#modalAntecedentesRM").prop('disabled', true);
            $("#ulExamenes").empty();
            $("#txtNombrePaciente").text(muestra.nombres);
            $("#txtSexoPaciente").text(muestra.sexo);
            $("#txtEdadPaciente").text(muestra.edad);
            $("#txtTipoAtencion").text(muestra.tipo_ATENCION);
            $("#txtProcedencia").text(muestra.procedencia);
            $("#txtServicio").text(muestra.servicio);
            $("#txtNOrden").text(muestra.norden);
            $("#txtFechaOrden").text(nuevoFormato(muestra.fecha_ORDEN));
            $("#txtObservacionOrden").text(muestra.obs_ORDEN);
            $("#txtFechaTM").text(muestra.fecha_TM);
            $("#txtObservacionTM").text(muestra.obs_TM);
            if (muestra.list_EXAMENES.length > 0) {
                muestra.list_EXAMENES.forEach(function (examen) {
                    let htmlExamen = examen.ce_ABREVIADO;
                    if (examen.dfe_EXAMENANULADO === 'S'){
                        htmlExamen = `<del>${examen.ce_ABREVIADO}</del>`;
                    } 
                    $("#ulExamenes").append(`<li>${htmlExamen}</li>`);
                });
            }
            let estadoantecedenterm = muestra.estadoantecedenterm === 'N' ? true : false;
            if(estadoantecedenterm){
                $('#modalAntecedentesRM').removeAttr('onclick');

            }else{
                $("#modalAntecedentesRM").prop('disabled', false);
                $("#modalAntecedentesRM").attr('onClick', 'mostrarModalAntecentesPac('+muestra.norden+',0);');
            }
        },
        error: function (msg) {
            console.log(msg);
            handlerMessageError("No registrada correctamente");
        }
    });
}

function rellenarTablaMuestrasPendientes(){
    $.ajax({
        type: "get",
        url: getctx + "/api/recepcionMuestras/muestrasPendientes",
        datatype: "json",
        success: function (data){
            $("#tbodyPendientesFiltro").empty();
            if (data.length > 0){
               data.forEach(function (muestra){
                    // let hora = muestra.fechatm.substring(11, 16);
                    let hora = formatearDiaHoras(muestra.fechatm);
                    let muestraenvase = muestra.muestradesc + ' - ' + muestra.envasedesc;
                    if(muestraenvase.length > 10 && $(window).width() < 1200){
                        muestraenvase = muestraenvase.substr(0,10);
                    }
                    $("#tbodyPendientesFiltro").append("<tr class='pointer' data-orden="+muestra.norden+">\
                                                <td>"+hora+"</td>\
                                                <td class='nroMuestra'>"+muestra.codigobarras+"</td>\
                                                <td title='"+muestra.muestradesc + ' - ' + muestra.envasedesc+"'>"+ muestraenvase.substr(0,28)+"</td>\
                                                <td class='idMuestra' style='display:none;'>"+ muestra.idmuestra+"</td>\
                                            </tr>");
                   
               });
            } else {
                $("#tbodyPendientesFiltro").append("<tr><td colspan='3' align='center'>No hay muestras pendientes</td></tr>");
            }
        },
        error: function (msg){
             console.log(msg);
        }
    });
}

function rellenarTablaMuestrasRecepcionadas(){
    $.ajax({
        type: "get",
        url: getctx + "/api/recepcionMuestras/muestrasRecepcionadas",
        datatype: "json",
        success: function (data){
            $("#tbodyRecepcionadasFiltro").empty();
            if (data.length > 0){
                data.forEach(function (muestra){
                    // let hora =  muestra.fecharm.substring(11, 16);
                    let hora = formatearDiaHoras(muestra.fecharm);
                    let muestraenvase = muestra.muestradesc + ' - ' + muestra.envasedesc;
                    if(muestraenvase.length > 10 && $(window).width() < 1200){
                        muestraenvase = muestraenvase.substr(0,10);
                    }
                    $("#tbodyRecepcionadasFiltro").append("<tr class='pointer' data-orden="+muestra.norden+">\
                                                <td>"+hora+"</td>\
                                                <td class='nroMuestra'>"+muestra.codigobarras+"</td>\
                                                <td title='"+ muestra.muestradesc + ' - ' + muestra.envasedesc+"'>"+muestraenvase.substr(0,28)+"</td>\
                                                <td class='idMuestra' style='display:none;'>"+muestra.idmuestra+"</td>\
                                            </tr>");
               });
            } else {
                $("#tbodyRecepcionadasFiltro").append("<tr><td colspan='3' align='center'>No se encontraron muestras</td></tr>");
            }
        },
        error: function (msg){
             console.log(msg);
        }
    });
}

function rellenarTablaMuestrasConDerivador(){
    $.ajax({
        type: "get",
        url: getctx + "/api/recepcionMuestras/muestrasConDerivador",
        datatype: "json",
        success: function (data){
            $("#tbodyFiltroDerivadas").empty();
            if (data.length > 0){
                data.forEach(function (muestra){
                    // let hora = muestra.fecharm.substring(11, 16);
                    let hora = formatearDiaHoras(muestra.fecharm);
                    let muestras = muestra.muestradesc + ' - ' + muestra.envasedesc;
                    if(muestras.length > 10 && $(window).width() < 1200){
                        muestras = muestras.substr(0,28);
                    }
                    $("#tbodyFiltroDerivadas").append("<tr class='pointer' data-orden="+muestra.norden+">\
                                                <td>"+hora+"</td>\
                                                <td class='nroMuestra'>"+muestra.codigobarras+"</td>\
                                                <td title='"+muestra.muestradesc + ' - ' + muestra.envasedesc+"'>"+muestras+"</td>\
                                                <td>"+muestra.derivador+"</td>\
                                                <td class='idMuestra' style='display:none;'>"+muestra.idmuestra+"</td>\
                                            </tr>");
               });
            } else {
                $("#tbodyFiltroDerivadas").append("<tr><td colspan='4' align='center'>No se encontraron muestras</td></tr>");
            }
        },
        error: function (msg){
             console.log(msg);
        }
    });
}

function seleccionarMuestraCentral(nroMuestra){
    var tableRow = $('#tablaMuestras tbody tr td').filter(function() {
        return $(this).text() == nroMuestra;
    });
    tableRow.parent().addClass('muestra-blue');
}

function modificarFiltroOrden(orden){
	$(".txtFiltroOrden ").val(orden);
    const inputCols = [5];
    inputCols.forEach((colIndex) => {
      $(`input[data-col-index="` + colIndex + `"]`).val(orden);
      $(`input[data-col-index="` + colIndex + `"]`).trigger("change")
    });

        tableMuestras
            .column(5)
            .search(orden)
            .draw();
        
}