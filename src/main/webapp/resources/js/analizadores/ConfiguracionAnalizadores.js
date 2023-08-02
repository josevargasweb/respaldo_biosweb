$(document).ready(function () {
    $(".ocultar").hide();
    //cargarSecciones(secciones());
    listarAnalizadores();
});
/*
let secciones = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/secciones/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

function cargarSecciones(secciones){
    let options = `<option value="0" selected>Seleccione</option>`;
    secciones.forEach((element) => {
        options += `<option value="${element.csecIdseccion}">${element.csecDescripcion}</option>`;
    });
    $("#selectSeccionFiltro").html(options);
}
*/
$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 1) {
        $("#txtNombreFiltro").prop("disabled", true);
        //$("#selectSeccionFiltro").prop("disabled", true);
        //filtrarParametro(url);
    } else {
        $("#txtNombreFiltro").prop("disabled", false);
        //$("#selectSeccionFiltro").prop("disabled", false);
        //cargarListaUsuarios();
    }
    listarAnalizadores();
});

$("#txtNombreFiltro").keyup(function () {
    if ($("#txtNombreFiltro").val().length > 1) {
        $("#txtCodigoFiltro").prop("disabled", true);
        //$("#selectSeccionFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        //$("#selectSeccionFiltro").prop("disabled", false);
    }
    listarAnalizadores();
});
/*
$("#selectSeccionFiltro").on('change', function () {
    if ($("#selectSeccionFiltro").val() > 0) {
        $("#txtCodigoFiltro").prop("disabled", true);
        $("#txtNombreFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtNombreFiltro").prop("disabled", false);
    }
    listarAnalizadores();
});
*/



$("#nuevoAnalizador").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtNombreFiltro").val("");
    $("#tbodyFiltro").empty();
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtNombreFiltro").prop("disabled", false);
    listarAnalizadores();
});

function listarAnalizadores(){
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        nombre: $("#txtNombreFiltro").val(),
        //idSeccion: $("#selectSeccionFiltro").val()
    });
    
    $.ajax({
        method: "POST",
        url: "api/analizadores/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        dataType: "json",
        success: function (mensaje) {
            var cont = 0;
            let dato = mensaje.dato;
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            if (dato.length > 0) {
                dato.forEach(function (analizador) {
                    cont++;
                    $("#tbodyFiltro").append(`<tr class="pointer" onclick="buscarCodigo(this)" data-id-analizador="${analizador.saIdanalizador}">
                                                <th class="row mx-auto">${cont}</th>
                                                <td class='codigoAnalizador'>${analizador.saCodigoanalizador}</td>
                                                <td class=''>${analizador.saDescripcion}</td>
                                              </tr>`);
                });
                $("#lblTotalFiltro").show();
            } else {
                $("#lblTotalFiltro").hide();
                $("#lblErrorFiltro").show();
            }
            $("#totalFiltro").text(cont);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

function buscarCodigo(a){
    let $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    let id = $row.attr("data-id-analizador");
    buscarAnalizadorPorId(id);
}

function buscarAnalizadorPorId(idAnalizador,collapse = true){
    $.ajax({
        type: "GET",
        url: getctx + "/api/analizador/" + idAnalizador,
        dataType: 'json',
        success: function (json) {
            if (json.status === 200){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                let data = json.dato;
                $("#txtIdAnalizador").val(data.sigmaAnalizadores.saIdanalizador);
                $("#txtCodigo").val(data.sigmaAnalizadores.saCodigoanalizador);
                $("#txtNombre").val(data.sigmaAnalizadores.saDescripcion);
                nombreTitulo($("#txtNombre").val());
                $("#txtModelo").val(data.sigmaAnalizadores.saModelo);
                $("#txtEmpresa").val(data.sigmaAnalizadores.saEmpresa);
                $("#txtCodigoProceso1").val(data.sigmaAnalizadores.saCodigoproceso1);
                $("#txtCodigoProceso2").val(data.sigmaAnalizadores.saCodigoproceso2);
                $("#txtCodigoProceso3").val(data.sigmaAnalizadores.saCodigoproceso3);
                if (data.imagenAnalizador!==null){
                    $("#imagenWrapper").css("background-image", "url(data:image/jpeg;base64," + data.imagenAnalizador + ")");
                } else {
                    $("#imagenWrapper").css("background-image", "");
                }
                $("#cambiarImagenAnalizador").hide();
                $("#eliminarImageAnalizador").hide();
                $("#removeImageAnalizador").hide();
                activarEdit();
                $("#btnAgregarAnalizador").prop("disabled", true);
                $(".formAnalizador").prop("disabled",true);
            } else {
                handlerMessageError(json.message);
            }
        }
    });
}

$("#txtNombre").keyup(function () {
    nombreTitulo($("#txtNombre").val());
});

function nombreTitulo(nombre) {
    if (nombre !== ""){
        $("#tituloRegistro").text("Analizador: " + nombre);
    } else {
        $("#tituloRegistro").text("Analizador");
    }
    
}

$("#iBuscarAnalizador").click(function() {
    $("#collapseHeader").collapse('show');
    $(window).scrollTop(0);
});

$("#btnEditar").click(function() {
    if (
        localStorage.getItem("botonEditar") === "activo"
    ) {
        $(".formAnalizador").prop("disabled",false);
        $("#cambiarImagenAnalizador").show();
        $("#eliminarImageAnalizador").show();
        $("#removeImageAnalizador").show();
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").css("display", "flex");
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');
    }
});

function activarEdit() {
    $("#iEditAnalizador").removeClass("text-dark-50");
    $("#iEditAnalizador").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditAnalizador").removeClass("text-primary");
    $("#iEditAnalizador").addClass("text-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
}

$("#btnLimpiar").click(function() {
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

$("#btnCancelAnalizador").click(function() {
    $(".formAnalizador").prop("disabled", true);
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});
$("#btnUpdateAnalizador").click(function() {
    $(".formAnalizador").prop("disabled", true);
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
});

$("#txtCodigo").keyup(function () {
    if ($("#txtCodigo").val().length > 0) {
        $("#txtCodigo").removeClass("is-invalid");
    }
});

$("#txtNombre").keyup(function () {
    if ($("#txtNombre").val().length > 0) {
        $("#txtNombre").removeClass("is-invalid");
    }
});

function limpiarFormulario(){
    $("#txtIdAnalizador").val("");
    $("#txtCodigo").val("");
    $("#txtNombre").val("");
    nombreTitulo("");
    $("#txtModelo").val("");
    $("#txtEmpresa").val("");
    $("#txtCodigoProceso1").val("");
    $("#txtCodigoProceso2").val("");
    $("#txtCodigoProceso3").val("");
    /** Imagen **/
    $("#imageAnalizador").val("");
    $("#imagenWrapper").css("background-image", "");
    $("#cambiarImagenAnalizador").show();
    $("#eliminarImageAnalizador").show();
    $("#removeImageAnalizador").show();
    /** Formulario **/
    $("#btnAgregarAnalizador").prop("disabled", false);
    $(".formAnalizador").prop("disabled", false);
}

/*** IMAGEN ***/
var avatar1 = new KTImageInput('inputImageAnalizador');

$("#eliminarImageAnalizador").click(function(){
    //$("#imgUsuario").removeAttr('src');
    //$("#imgUsuario").hide();
    $("#imageAnalizador").val("");
    $("#eliminarImageAnalizador").hide();
});

const tableNav = new TableNavigation("tableFiltro",selectCausaAuto);
tableNav.changeContenedor(".table-container");
function selectCausaAuto(a = null) {
    let $row;
    if(a == null){
        let tabla = document.getElementById("tableFiltro");
        let tbody = tabla.querySelector("tbody");
        let primerTR = tbody.querySelector("tr:first-child");
        $row = primerTR;
    }else{
        $row = a;
    }
    var idCausa = $row.getAttribute("data-id-analizador");
    buscarAnalizadorPorId(idCausa,false);
}
