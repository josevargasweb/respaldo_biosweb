$(document).ready(function () {
    $(".ocultar").hide();
    $(".selectpicker").selectpicker({
        noneSelectedText: "",
    });
    filtrarGlosas();
    desactivarEdit();
    $('.selectpicker').selectpicker('refresh');
    $("#selectSeccion").selectpicker('val', '');
});


$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtGlosaFiltro").prop("disabled", true);
        $("#txtGlosaFiltro").val("");
        $("#selectSeccionFiltro").prop("disabled", true);
        $("#selectSeccionFiltro").val("0");
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtGlosaFiltro").prop("disabled", false);
        $("#selectSeccionFiltro").prop("disabled", false);
    }
    filtrarGlosas();
});

$("#txtGlosaFiltro").keyup(function () {
    if ($("#txtGlosaFiltro").val().length > 0) {
        $("#txtCodigoFiltro").prop("disabled", true);
        $("#txtCodigoFiltro").val("");
        $("#selectSeccionFiltro").prop("disabled", true);
        $("#selectSeccionFiltro").val("0");
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtGlosaFiltro").prop("disabled", false);
        $("#selectSeccionFiltro").prop("disabled", false);
    }
    filtrarGlosas();
});

$("#selectSeccionFiltro").change(function () {
    if ($("#selectSeccionFiltro").val() > 0) {
        $("#txtCodigoFiltro").prop("disabled", true);
        $("#txtCodigoFiltro").val("");
        $("#txtGlosaFiltro").prop("disabled", true);
        $("#txtGlosaFiltro").val("");
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtGlosaFiltro").prop("disabled", false);
        $("#selectSeccionFiltro").prop("disabled", false);
    }
    filtrarGlosas();
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtGlosaFiltro").val("");
    $("#txtGlosaFiltro").prop("disabled", false);
    $("#selectSeccionFiltro").val("0");
    $("#selectSeccionFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked",false);
    filtrarGlosas();
});

$("#chkMostrarActivos").change(function(){
    filtrarGlosas();
});

$("#btnLimpiar").click(function () {
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});


$("#nuevaGlosa").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

function limpiarFormulario(){
    $("#txtIdGlosa").val("");
    $("#txtCodigo").val("");
    $("#selectSeccion").val(0);
    $("#txtGlosa").val("");
    $("#numOrden").val(1);
    $("#txtHost").val("");
    $("#lblEstado").text("Activo");
    $("#checkBoxActivo").prop("checked", true);
    $("#txtEstado").val("S");
    $("#divBtnAgregar").show();
    $("#btnAgregarGlosa").prop("disabled", false);
    $("#divBtnUpdate").hide();
    $(".formGlosas").prop("disabled",false);
    $(".switch-content").removeClass("disabled inactivo");
    desactivarEdit();
}

$("#buscarGlosa").click(function () {
    $("#busquedaGlosas").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionBusquedaGlosa").offset().top
    }, 700);
});
$("#circuloBuscarGlosa").hover(
        function () {
            $("#iBuscarGlosa").removeClass("text-blue");
            $("#iBuscarGlosa").addClass("text-white");
        },
        function () {
            $("#iBuscarGlosa").removeClass("text-white");
            $("#iBuscarGlosa").addClass("text-blue");
        }
);
$("#circuloAgregarGlosa").hover(
        function () {
            $("#iAgregarGlosa").removeClass("text-blue");
            $("#iAgregarGlosa").addClass("text-white");
        },
        function () {
            $("#iAgregarGlosa").removeClass("text-white");
            $("#iAgregarGlosa").addClass("text-blue");
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

$("#circuloEditarGlosa").hover(
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarGlosa").addClass("bg-hover-blue");
                $("#iEditGlosa").removeClass("text-blue");
                $("#iEditGlosa").addClass("text-white");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#iEditGlosa").removeClass("text-white");
                $("#iEditGlosa").addClass("text-blue");
            }
        }
);

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
        $("#txtEstado").val("S");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
        $("#txtEstado").val("N");
    }
});

$("#btnEditar").click(function () {
    if (localStorage.getItem("botonEditar") === "activo"){
        $("#txtCodigo").prop("disabled", false);
        $("#selectSeccion").prop("disabled", false);
        $("#txtGlosa").prop("disabled", false);
        $("#txtHost").prop("disabled", false);
        $("#numOrden").prop("disabled", false);
        $("#checkBoxActivo").prop("disabled", false);
        $(".switch-content").removeClass("disabled");
        $('.selectpicker').selectpicker('refresh');
        $("#divBtnAgregar").hide();
        $("#divBtnUpdate").show();
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');
    }
});

function filtrarGlosas() {
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        nombre: $("#txtGlosaFiltro").val(),
        idSeccion: $("#selectSeccionFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    $.ajax({
        method: "POST",
        url: getctx + "/api/glosas/filtro",
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
                    dato.forEach(function (glosa) {
                        cont++;
                        $("#tbodyFiltro").append(`<tr class='pointer' onclick='selectGlosa(this)' >
                                                    <th class='row mx-auto'>${cont}</th>
                                                    <td class=''>${glosa.cgCodigoglosa}</td>
                                                    <td class='' >${glosa.cgDescripcion}</td>
                                                    <td class='idGlosa' style='display:none'>${glosa.cgIdglosa}</td>
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
                handlerMessageError(data.message)
            }
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error.");
        }
    });
}



function selectGlosa(a) {
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idGlosa = $row.find(".idGlosa").text();
    getGlosaById(idGlosa);
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
}
function activarEdit() {
    $("#iEditGlosa").removeClass("text-dark-50");
    $("#iEditGlosa").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
    $('.selectpicker').selectpicker('refresh');
}
function desactivarEdit() {
    $("#iEditGlosa").addClass("text-dark-50");
    $("#iEditGlosa").removeClass("text-blue");
    localStorage.setItem("botonEditar", "inactivo");
    $('.selectpicker').selectpicker('refresh');
}
function getGlosaById(idGlosa,collapse = true) {
    $.ajax({
        type: "GET",
        url: getctx + "/api/glosa/" + idGlosa,
        datatype: "json",
        success: function (json) {
            if (json.status === 200) {
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                $(".formGlosas").prop("disabled", true);
                let glosa = json.dato;
                $("#txtIdGlosa").val(glosa.cgIdglosa);
                $("#txtCodigo").val(glosa.cgCodigoglosa);
                $("#selectSeccion").val(glosa.cgIdSeccion);
                $("#txtGlosa").val(glosa.cgDescripcion);
                $("#numOrden").val(glosa.cgSort);
                $("#txtHost").val(glosa.cgHost);
                if (glosa.cgActivo === "S") {
                    $("#checkBoxActivo").prop("checked", true);
                    $("#lblEstado").text("Activo");
                } else {
                    $("#checkBoxActivo").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                }
                $(".switch-content").addClass("disabled");
                glosa.cgActivo === "S" ? $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                $("#checkBoxActivo").prop("disabled", true);
                $("#btnAgregarGlosa").prop("disabled", true);
                activarEdit();
            } else {
                handlerMessageError(json.message);
            }
        },
        error: function (msg) {
            handlerMessageError("Error. No se pudo obtener datos de glosa");
            console.log(msg.responseText);
        }
    });
}

function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    } else {
        return true;
    }


}
$("#btnUpdateGlosa").click(function() {
    $('#btnLimpiar').removeClass('disabled');
});
$("#btnCancelUpdate").click(function() {
    $(".formGlosas").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#divBtnUpdate").hide();
    $("#divBtnAgregar").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
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
    var idCausa = $row.querySelector('td.idGlosa').textContent;
    getGlosaById(idCausa,false);
}


new ImaskInputNumber('idnumber', false ,0, 9999);