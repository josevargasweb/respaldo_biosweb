$(document).ready(function () {
    $(".ocultar").hide();
    filtrarDiagnosticos();
    cargarCodigosCie10(cie10s());
    desactivarEdit();
});
new ImaskInputNumber('numSort', false, 0, 9999);
let cie10s = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/cie10/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

function cargarCodigosCie10(listcie10){
    let options = `<option value="0" selected>Seleccione</option>`;
    listcie10.dato.forEach((element) => {
        options += `<option value="${element.ldvcieCodigocie10}">${element.ldvcieCodigocie10} - ${element.ldvcieDescripcion}</option>`;
    });
    $("#selectCie10").html(options);
}

$("#txtDescripcionFiltro").keyup(function () {
    /*if ($("#txtDescripcionFiltro").val().length > 0) {
        $("#txtCodigoFiltro").prop("disabled", true);
        $("#txtDescripcionFiltro").prop("disabled", false);
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtDescripcionFiltro").prop("disabled", false);
    }*/
    filtrarDiagnosticos();
});

$("#chkMostrarActivos").change(function(){
    filtrarDiagnosticos();
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtDescripcionFiltro").val("");
    $("#txtDescripcionFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked",false);
    filtrarDiagnosticos();
});

function filtrarDiagnosticos(){
    let filtros = JSON.stringify({
        descripcion: $("#txtDescripcionFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    $.ajax({
        method: "POST",
        url: getctx + "/api/diagnosticos/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
            let cont = 0;
            if (data.status === 200){
                let dato = data.dato;
                $("#tbodyFiltro").empty();
                if (dato.length > 0) {
                    dato.forEach(function (diagnostico){
                        cont++;
                        $("#tbodyFiltro").append(`<tr class='pointer' onclick='selectDiagnostico(this)' >
                                                    <th class='row mx-auto'>${cont}</th>
                                                    <td class='' >${diagnostico.cdDescripcion}</td>
                                                    <td class='idDiagnostico' style='display:none'>${diagnostico.cdIddiagnostico}</td>
                                                </tr>`);
                    });
                    $("#lblErrorFiltro").hide();
                    $("#lblTotalFiltro").show();
                    $("#totalFiltro").text(cont);
                } else {
                    $("#lblErrorFiltro").show();
                    $("#lblTotalFiltro").hide();
                }
            } else {
                handlerMessageError(data.message);
            }
        },
        error: function (msg) {
            handlerMessageError(msg.responseText);
        }
    });
}

function selectDiagnostico(a){
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idDiagnostico = $row.find(".idDiagnostico").text();
    buscarPorId(idDiagnostico);
    activarEdit();
}

function buscarPorId(idDiagnostico,collapse = true){
    $.ajax({
        type: "GET",
        url: getctx + "/api/diagnostico/" + idDiagnostico,
        datatype: "json",
        success: function (json) {
            if (json.status === 200){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                let data = json.dato;
                $("#txtIdDiagnostico").val(data.cdIddiagnostico);
                $("#txtDescripcion").val(data.cdDescripcion);
                $("#txtHost").val(data.cdHost);
                $("#numSort").val(data.cdSort);
                $("#checkBoxActivo").prop("checked", data.cdActivo === 'S' ? true : false);
                $(".switch-content").addClass("disabled");
                data.cdActivo === "S" ? $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                $("#lblEstado").text(data.cdActivo === 'S' ? "Activo" : "Inactivo");
                $("#selectCie10").val(data.ldvCie10 !== null ? data.ldvCie10.ldvcieCodigocie10 : "0");
                $(".formDiagnosticos").prop("disabled",true);
                $("#divAgregarBtn").show();
                $("#divActualizarBtn").hide();
                $("#btnAgregar").prop("disabled", true);
                activarEdit();
            } else {
                handlerMessageError(json.message);
            }
        },
        error: function (msg) {
            handlerMessageError(msg.responseText);
        }
    });
}

$("#nuevoDiagnostico").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarDiagnostico").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionExample8").offset().top
    }, 700);
});

$("#btnEditarDiagnostico").click(function() {
    if (localStorage.getItem("botonEditar") === "activo"){
        $(".formDiagnosticos").prop("disabled", false);
        $(".switch-content").removeClass("disabled");
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
    }
});

$("#btnCancelUpdate").click(function() {
    $(".formDiagnosticos").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});

$("#btnLimpiar").click(function (){
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

function limpiarFormulario(){
    $("#txtIdDiagnostico").val("");
    $("#txtDescripcion").val("");
    $("#txtHost").val("");
    $("#numSort").val(1);
    $("#checkBoxActivo").prop("checked", true);
    $("#lblEstado").text("Activo");
    $("#selectCie10").val("0");
    $(".switch-content").removeClass("disabled inactivo");
    $(".formDiagnosticos").prop("disabled",false);
    $("#divAgregarBtn").show();
    $("#divActualizarBtn").hide();
    $("#btnAgregar").prop("disabled", false);
}

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

function activarEdit() {
    $("#iEditDiagnostico").removeClass("text-dark-50");
    $("#iEditDiagnostico").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditDiagnostico").removeClass("text-blue");
    $("#iEditDiagnostico").addClass("text-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
}


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
    var idCausa = $row.querySelector('td.idDiagnostico').textContent;
    buscarPorId(idCausa,false);
}