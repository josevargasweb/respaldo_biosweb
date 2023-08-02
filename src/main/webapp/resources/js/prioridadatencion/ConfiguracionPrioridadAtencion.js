$(document).ready(function () {
    $(".ocultar").hide();
    $('#colorpicker').farbtastic('#txtColor');
    var avatar1 = new KTImageInput('kt_image_5');
    filtrarBusqueda();
    desactivarEdit();
    let messageSuccess = $("#messageSuccess").val();
    let messageError = $("#messageError").val();
    if (typeof messageSuccess !== 'undefined' && messageSuccess!==null){
        handlerMessageOk(messageSuccess);
    }
    if (typeof messageError !== 'undefined' &&messageError!==null){
        handlerMessageError(messageError);
    }
});
new ImaskInputNumber('numPrioridad', false, 0, 99);
new ImaskInputNumber('numOrdenIngreso', false, 1, 99);

$("#btnLimpiarFiltro").click(function () {
    $("#txtNombreFiltro").val("");
    $("#txtNombreFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked", false);
    filtrarBusqueda();
});

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

$("#txtNombreFiltro").keyup(function () {
    filtrarBusqueda();
});

$("#chkMostrarActivos").change(function(){
    filtrarBusqueda();
});

function filtrarBusqueda(){
    let filtros = JSON.stringify({
        descripcion: $("#txtNombreFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    $.ajax({
        method: "POST",
        url: getctx + "/api/prioridadatencion/filtro",
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
                    dato.forEach(function (prioridad) {
                        cont++;
                        $("#tbodyFiltro").append(`<tr class='pointer' onclick='selectPrioridadAtencion(this)' >
                                                    <th class='row mx-auto'>${cont}</th>
                                                    <td class='' >${prioridad.cpaDescripcion}</td>
                                                    <td class='idPrio' style='display:none'>${prioridad.cpaIdprioridadatencion}</td>
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
            handlerMessageError("Ha ocurrido un error.");
            console.log(msg.responseText);
        }
    });
}

$("#nuevaPrioridadAtencion").click(function () {
    $("#collapseHeader").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});


function selectPrioridadAtencion(a){
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    //Paciente
    var id = $row.find(".idPrio").text(); // Find the text
    busquedaPoridParaRellenarDatos(id);
}

function busquedaPoridParaRellenarDatos(id,collapse = true) {
    //alert(id)
    $.ajax({
        type: "post",
        data: "BusquedaPrioAteidRellenarDatos=" + id,
        datatype: "json",
        success: function (a) {
            let dato = JSON.parse(a);
            if (!dato.hasOwnProperty('error')){
                let prioridadFinal = dato.Prioridad[0];
                if(collapse){   
                    $("#collapseHeader").collapse('hide');
                }
                //console.table(prioridadFinal)
                $(".formPrioridad").prop("disabled", true);
                $("#txtIdPrioridad").val(prioridadFinal.id);
                $("#txtDescripcion").val(prioridadFinal.descripcion);
                $("#numPrioridad").val(prioridadFinal.prioAtencion);
                $("#numOrdenIngreso").val(prioridadFinal.sort);
                $("#txtColor").val(prioridadFinal.color);
                $("#colorpicker").hide();
                $.farbtastic("#colorpicker").setColor(prioridadFinal.color);
                if (prioridadFinal.imagen != null){
                    $(".image-input-wrapper").css("background-image", "url(data:image/jpeg;base64," + prioridadFinal.imagen + ")");
                } else {
                    $(".image-input-wrapper").css("background-image", "");
                }
                $('.selectpicker').selectpicker('refresh');
                $(".switch-content").addClass("disabled");
                prioridadFinal.activo === "S" ? $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                if (prioridadFinal.activo === "S") {
                    $("#checkBoxActivo").prop("checked", true);
                    $("#lblEstado").text("Activo");
                } else {
                    $("#checkBoxActivo").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                }
                $("#cambiarImagen").hide();
                $("#cancelImage").hide();
                $("#removeImage").hide();
                $("#btnAgregar").prop("disabled", true);
                $("#divAgregarBtn").show();
                $("#divActualizarBtn").hide();
                activarEdit();
            } else {
                handlerMessageError(dato.error);
            }
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error.");
        }
    });
}

function activarEdit() {
    $("#iEditPrioridad").removeClass("text-dark-50");
    $("#iEditPrioridad").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditPrioridad").removeClass("text-blue");
    $("#iEditPrioridad").addClass("text-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
}

$("#btnEditar").click(function() {
    if (localStorage.getItem("botonEditar") === "activo"){
        $(".formPrioridad").prop("disabled", false);
        $(".switch-content").removeClass("disabled");
        $("#cambiarImagen").show();
        $("#cancelImage").show();
        $("#removeImage").show();
        $("#colorpicker").show();
        //$("#btnAgregar").prop("disabled", true);
        //$("#btnAgregar").removeClass("disabled");
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
        $('.selectpicker').selectpicker('refresh');
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');

    }
});

$("#btnCancelUpdate").click(function() {
    $(".formPrioridad").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#colorpicker").hide();
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
    $(".formPrioridad").prop("disabled", false);
    $(".switch-content").removeClass("disabled inactivo");
    $("#txtIdPrioridad").val("");
    $("#txtDescripcion").val("");
    $("#numPrioridad").val(0);
    $("#numOrdenIngreso").val(1);
    $("#txtColor").val("123456");
    $.farbtastic("#colorpicker").setColor("#123456");
    $("#colorpicker").show();
    $(".image-input-wrapper").css("background-image", "");
    $("#cambiarImagen").show();
    $("#cancelImage").show();
    $("#removeImage").show();
    $("#checkBoxActivo").prop("checked", true);
    $("#lblEstado").text("Activo");
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $("#btnAgregar").prop("disabled", false);
    $('.selectpicker').selectpicker('refresh');
    desactivarEdit();
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
    var idCausa = $row.querySelector('td.idPrio').textContent;
    busquedaPoridParaRellenarDatos(idCausa,false);
}