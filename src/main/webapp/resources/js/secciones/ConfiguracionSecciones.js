$(document).ready(function () {
    $(".ocultar").hide();
    $(".selectpicker").selectpicker({
        noneSelectedText: "",
    });
    filtrarLike();
    cargarLabs(laboratorios());
    cargarLabs2(laboratorios());
    desactivarEdit();
});
new ImaskInputNumber('numSort', false, 1, 9999);
let laboratorios = function(){
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/instituciones/laboratorios/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

function cargarLabs(laboratorios){
    let options = ``;
    laboratorios.forEach((element) => {
        options += `<option value="${element.clabIdlaboratorio}">${element.clabDescripcion}</option>`;
    });
    $("#selectLabFiltro").html(options);
    $('.selectpicker').selectpicker('refresh');
    $('#selectLabFiltro').selectpicker('val', '');
    $('#selectLabFiltro').selectpicker('refresh');
}
function cargarLabs2(laboratorios){
    let options = `<option value="0" selected disabled hidden></option>`;
    laboratorios.forEach((element) => {
        options += `<option value="${element.clabIdlaboratorio}">${element.clabDescripcion}</option>`;
    });
    $("#selectLab").html(options);
    $('.selectpicker').selectpicker('refresh');
}

$("#btnLimpiarFiltro").click(function (){
    $("#txtCodigoFiltro").val("");
    $("#txtCodigoFiltro").attr("disabled", false);
    $("#txtDescripcionFiltro").val("");
    $("#txtDescripcionFiltro").prop("disabled", false);
    $("#selectLabFiltro").val(0);
    $("#selectLabFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked", false);
    filtrarLike();
    $('.selectpicker').selectpicker('refresh');
});

$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtDescripcionFiltro").prop("disabled", true);
        $("#selectLabFiltro").prop("disabled", true);
    } else {
        $("#txtDescripcionFiltro").prop("disabled", false);
        $("#selectLabFiltro").prop("disabled", false);
    }
    filtrarLike();
    $('.selectpicker').selectpicker('refresh');
});

$("#txtDescripcionFiltro").keyup(function () {
    if ($("#txtDescripcionFiltro").val().length > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
        $("#selectLabFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
        $("#selectLabFiltro").prop("disabled", false);
    }
    filtrarLike();
    $('.selectpicker').selectpicker('refresh');
});

$("#selectLabFiltro").change(function () {
    if ($("#selectLabFiltro").val() > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
        $("#txtDescripcionFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
        $("#txtDescripcionFiltro").prop("disabled", false);
    }
    filtrarLike();
    $('.selectpicker').selectpicker('refresh');
});

$("#chkMostrarActivos").change(function(){
    filtrarLike();
});

$("#circuloAgregarSeccion").hover(
        function () {
            $("#iAgregarSeccion").removeClass("text-blue");
            $("#iAgregarSeccion").addClass("text-white");
        },
        function () {
            $("#iAgregarSeccion").removeClass("text-white");
            $("#iAgregarSeccion").addClass("text-blue");
        }
);

$("#circuloLimpiarFiltro").hover(
        function () {
            $("#iLimpiarFiltro").removeClass("text-blue");
            $("#iLimpiarFiltro").addClass("text-white");
        },
        function () {
            $("#iLimpiarFiltro").removeClass("text-white");
            $("#iLimpiarFiltro").addClass("text-blue");
        }
);

$("#circuloBuscarSeccion").hover(
        function () {
            $("#iBuscarSeccion").removeClass("text-blue");
            $("#iBuscarSeccion").addClass("text-white");
        },
        function () {
            $("#iBuscarSeccion").removeClass("text-white");
            $("#iBuscarSeccion").addClass("text-blue");
        }
);

$("#circuloEditarSeccion").hover(
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarSeccion").addClass("bg-hover-blue");
                $("#iEditSeccion").removeClass("text-blue");
                $("#iEditSeccion").addClass("text-white");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#iEditSeccion").removeClass("text-white");
                $("#iEditSeccion").addClass("text-blue");
            }
        }
);

$("#circuloLimpiar").hover(
        function () {
            $("#iLimpiar").removeClass("text-blue");
            $("#iLimpiar").addClass("text-white");
        },
        function () {
            $("#iLimpiar").removeClass("text-white");
            $("#iLimpiar").addClass("text-blue");
        }
);

function filtrarLike() {
    console.log($("#selectLabFiltro").val());
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        descripcion: $("#txtDescripcionFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N",
        idLab: $("#selectLabFiltro").val() == null ? 0 : $("#selectLabFiltro").val()
    });
    
    $.ajax({
        method: "POST",
        url: getctx + "/api/secciones/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
            var cont = 0;
            if (data.status === 200){
                let dato = data.dato;
                $("#tbodyFiltro").empty();
                if (dato.length > 0) {
                    dato.forEach(function (seccion) {
                        cont++;
                        $("#tbodyFiltro").append(`<tr class='pointer' onclick='selectSeccion(this)' onmouseover='mostrarExamenes(this)'>
                                                    <th class='row mx-auto'>${cont}</th>
                                                    <td class=''>${seccion.csecCodigo}</td>
                                                    <td class=''>${seccion.csecDescripcion}</td>
                                                    <td class='idSeccion' style='display:none'>${seccion.csecIdseccion}</td>
                                                </tr>`);
                    });
                    $("#contRegistros").text(cont);
                    $("#lblTotalFiltro").show();
                    $("#lblErrorFiltro").hide();
                } else {
                    $("#lblTotalFiltro").hide();
                    $("#lblErrorFiltro").show();
                }
            } else {
                handlerMessageError(data.message);
            }
        },
        error: function (msg) {
            handlerMessageError("Ha ocurrido un error");
            console.log(msg.responseText);
        }
    });
}

function mostrarExamenes(a) {
    var $row = $(a).closest("tr"); // Find the row
    var idSeccion = $row.find(".idSeccion").text();
    $.ajax({
        type: "GET",
        url: getctx + "/api/countExamenes/seccion/" + idSeccion,
        datatype: "json",
        success: function (data) {
            if (data.status === 200){
                let cantExamenes = data.dato;
                $row.attr("title", "Exámenes: "+cantExamenes);
            } else {
                handlerMessageError(data.message);
            }
        },
        error: function (msg) {
            handlerMessageError("Ha ocurrido un error");
            console.log(msg.responseText);
        }
    });
}

function selectSeccion(a){
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idSeccion = $row.find(".idSeccion").text();
    filtrarById(idSeccion);
    activarEdit();
}

function filtrarById(idSeccion,collapse = true){
    $.ajax({
        type: "GET",
        url: "api/seccion/"+idSeccion,
        datatype: "json",
        success: function (json) {
            if (json.status === 200){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                let data = json.dato;
                $(".formSecciones").prop("disabled", true);
                $("#txtIdSeccion").val(data.csecIdseccion);
                $("#txtCodigo").val(data.csecCodigo);
                $("#txtDescripcion").val(data.csecDescripcion);
                $("#numSort").val(data.csecSort);
                $("#selectLab").val(data.cfgLaboratorios.clabIdlaboratorio);
                $(".switch-content").addClass("disabled");
                data.csecActiva === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                $("#checkBoxActivo").prop("checked", data.csecActiva === 'S' ? true : false);
                $("#lblEstado").text(data.csecActiva === 'S' ? "Activo" : "Inactivo");
                $("#btnAgregarSeccion").prop("disabled", true);
                $('.selectpicker').selectpicker('refresh');
            } else {
                handlerMessageError(json.message);
            }
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error");
            
        }
    });
}

$("#nuevaSeccion").click(function () {
    $("#collapseHeader").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarSeccion").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionSecciones").offset().top
    }, 700);
});

$("#btnEditar").click(function () {
    if (localStorage.getItem("botonEditar") === "activo"){
        $(".switch-content").removeClass("disabled");
        $(".formSecciones").prop("disabled", false);
        $("#divBtnAgregar").hide();
        $("#divBtnUpdate").show();
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');

    }
});


function activarEdit() {
    $("#iEditSeccion").removeClass("text-dark-50");
    $("#iEditSeccion").addClass("text-blue");
    
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditSeccion").removeClass("text-blue");
    $("#iEditSeccion").addClass("text-dark-50");
    
    localStorage.setItem("botonEditar", "inactivo");
}

$("#checkBoxActivo").click(function () {
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

$("#btnCancelUpdate").click(function() {
    $(".switch-content").addClass("disabled");
    $(".formSecciones").prop("disabled", true);
    $("#divBtnUpdate").hide();
    $("#divBtnAgregar").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});

$("#btnLimpiar").click(function (){
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});
$("#btnUpdateSeccion").click(function (){
    $(".formSecciones").prop("disabled", true);
    $("#divBtnUpdate").hide();
    $("#divBtnAgregar").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $(".selectpicker").selectpicker("refresh");
});
function limpiarFormulario(){
    $(".switch-content").removeClass("disabled inactivo");
    $(".formSecciones").prop("disabled", false);
    $("#txtIdSeccion").val("");
    $("#txtCodigo").val("");
    $("#txtDescripcion").val("");
    $("#numSort").val(1);
    $("#selectLab").val(1);
    $("#checkBoxActivo").prop("checked", true);
    $("#lblEstado").text("Activo");
    $("#btnAgregarSeccion").prop("disabled", false);
    $("#divAgregarBtn").show();
    $("#divActualizarBtn").hide();
    desactivarEdit();
    $('.selectpicker').selectpicker('refresh');
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
    var idCausa = $row.querySelector('td.idSeccion').textContent;
    filtrarById(idCausa,false);
}
