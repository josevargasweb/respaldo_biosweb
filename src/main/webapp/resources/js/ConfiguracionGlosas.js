$(document).ready(function () {
    $(".ocultar").hide();
    filtrarGlosas();
    desactivarEdit();
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
    limpiarFormulario();
});

$("#nuevaGlosa").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

function limpiarFormulario(){
    $("#txtIdGlosa").val("");
    $("#txtCodigo").val("");
    $("#selectSeccion").val(0);
    $("#txtGlosa").val("");
    $("#numOrden").val(1);
    $("#txtHost").val("");
    $("#lblEstado").text("Activo");
    $("#checkBoxLever").prop("checked", true);
    $("#txtEstado").val("S");
    $("#divBtnAgregar").show();
    $("#btnAgregarGlosa").prop("disabled", false);
    $("#divBtnUpdate").hide();
    $(".formGlosas").prop("disabled",false);
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
/*
$("#btnBuscarFiltro").click(function () {
    var codigo = $("#txtCodigoFiltro").val();
    var nombre = $("#txtGlosaFiltro").val();
    var idSeccion = $("#selectSeccionFiltro :selected").val();
    //if (codigo.length < 1 && nombre.length < 1 && idSeccion === "N") {
        //notificacionDosCaracteres();
    //} else {
        filtrarGlosas();
    //}
});
*/

$("#checkBoxLever").click(function () {
    if ($("#checkBoxLever").is(":checked")) {
        $("#lblEstado").text("Activo");
        $("#txtEstado").val("S");
    }
    if (!($("#checkBoxLever").is(":checked"))) {
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
        $("#checkBoxLever").prop("disabled", false);
        $('.selectpicker').selectpicker('refresh');
        $("#divBtnAgregar").hide();
        $("#divBtnUpdate").show();
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');
    }
});


$('form').submit(function () {
    if ($("#txtCodigo").val().length > 0 && $("#txtGlosa").val().length > 0) {
        return true;
    } else {
        if ($("#txtCodigo").val().length == 0) {
            $("#txtCodigo").addClass("is-invalid");
        }
        if ($("#txtGlosa").val().length == 0) {
            $("#txtGlosa").addClass("is-invalid");
        }
        return false;
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
    var idGlosa = $row.find(".idGlosa").text();
    filtrarbyId(idGlosa);
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
function filtrarbyId(idGlosa) {
    $.ajax({
        type: "post",
        data: "filtroId=1&idGlosa=" + idGlosa,
        datatype: "json",
        success: function (mensaje) {
            let dato = JSON.parse(mensaje);
            if (!dato.hasOwnProperty('error')){
                $("#collapseHeader").collapse('hide');
                //$("#tbodyFiltro").empty();
                $("#lblErrorFiltro").hide();
                var glosa = dato.glosa[0];
                $("#txtIdGlosa").val(glosa.IdGlosa);
                $("#txtCodigo").val(glosa.CodigoGlosa);
                $("#txtCodigo").prop("disabled", true);
                $("#selectSeccion").val(glosa.SeccionGlosa);
                $("#selectSeccion").prop("disabled", true);
                $("#txtGlosa").val(glosa.NombreGlosa);
                $("#txtGlosa").prop("disabled", true);
                $("#numOrden").val(glosa.OrdenGlosa);
                $("#numOrden").prop("disabled", true);
                $("#txtHost").val(glosa.HostGlosa);
                $("#txtHost").prop("disabled", true);
                if (glosa.EstadoGlosa == "S") {
                    $("#checkBoxLever").prop("checked", true);
                    $("#lblEstado").text("Activo");
                    $("#txtEstado").val(glosa.EstadoGlosa);
                } else {
                    $("#checkBoxLever").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                    $("#txtEstado").val(glosa.EstadoGlosa);
                }
                $("#checkBoxLever").prop("disabled", true);
                $("#btnAgregarGlosa").prop("disabled", true);
                activarEdit();
                $('.selectpicker').selectpicker('refresh');
            } else {
                handlerMessageError(dato.error);
            }

            $('.selectpicker').selectpicker('refresh');
        },
        error: function (msg) {
            console.log(msg);
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

$("#formRegistroGlosa").submit(function(){
    let codigo = $("#txtCodigo").val();
    let glosa = $("#txtGlosa").val();
    if (codigo.trim() === "" || glosa.trim() === ""){
        if (codigo.trim() === ""){
            $("#txtCodigo").addClass('is-invalid');
            handlerMessageError("Código no debe estar vacío");
        }
        if (glosa.trim() === ""){
            $("#txtGlosa").addClass('is-invalid');
            handlerMessageError("Glosa no debe estar vacía");
        }
        return false;
    } else {
        return true;
    }
});

$("#txtCodigo").keyup(function () {
    if ($("#txtCodigo").val().trim().length > 0) {
        $("#txtCodigo").removeClass('is-invalid');
    }
});

$("#txtGlosa").keyup(function () {
    if ($("#txtGlosa").val().trim().length > 0) {
        $("#txtGlosa").removeClass('is-invalid');
    }
});

$("#btnCancelUpdate").click(function() {
    $(".formGlosas").prop("disabled", true);
    $("#divBtnUpdate").hide();
    $("#divBtnAgregar").show();
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});



