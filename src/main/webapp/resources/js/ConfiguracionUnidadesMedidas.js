$(document).ready(function () {
    $(".ocultar").hide();
    filtrarUnidadesMedida();
    desactivarEdit();
});

new ImaskInputNumber('numOrden', false, 1, 9999);
$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtUnidadFiltro").prop("disabled", true);
        $("#txtUnidadFiltro").val("");
    } else {
        $("#txtUnidadFiltro").prop("disabled", false);
    }
    filtrarUnidadesMedida();
});

$("#txtUnidadFiltro").keyup(function () {
    if ($("#txtUnidadFiltro").val().length > 0) {
        $("#txtCodigoFiltro").prop("disabled", true);
        $("#txtCodigoFiltro").val("");
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
    }
    filtrarUnidadesMedida();
});

$("#chkMostrarActivos").change(function(){
    filtrarUnidadesMedida();
});

$("#circuloAgregarUnidadMedida").hover(
        function () {
            $("#iAgregarUnidadMedida").removeClass("text-blue");
            $("#iAgregarUnidadMedida").addClass("text-white");
        },
        function () {
            $("#iAgregarUnidadMedida").removeClass("text-white");
            $("#iAgregarUnidadMedida").addClass("text-blue");
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
$("#circuloBuscarUnidadMedida").hover(
        function () {
            $("#iBuscarUnidadMedida").removeClass("text-blue");
            $("#iBuscarUnidadMedida").addClass("text-white");
        },
        function () {
            $("#iBuscarUnidadMedida").removeClass("text-white");
            $("#iBuscarUnidadMedida").addClass("text-blue");
        }
);
$("#circuloEditarUnidadMedida").hover(
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarUnidadMedida").addClass("bg-hover-blue");
                $("#iEditUnidadMedida").removeClass("text-blue");
                $("#iEditUnidadMedida").addClass("text-white");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#iEditUnidadMedida").removeClass("text-white");
                $("#iEditUnidadMedida").addClass("text-blue");
            }
        }
);
$("#nuevaUnidad").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarForm();
});
$("#buscarUnidad").click(function () {
    $(".collapse").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionExample8").offset().top
    }, 700);
});
$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtCodigoFiltro").prop("disabled",false);
    $("#txtUnidadFiltro").val("");
    $("#txtUnidadFiltro").prop("disabled",false);
    $("#tbodyFiltro").empty();
    $("#lblErrorFiltro").hide();
    $("#chkMostrarActivos").prop("checked",false);
    filtrarUnidadesMedida();
});
$("#circuloLimpiar").hover(
        function () {
            $("#iLimpiar").removeClass("text-primary");
            $("#iLimpiar").addClass("text-white");
        },
        function () {
            $("#iLimpiar").removeClass("text-white");
            $("#iLimpiar").addClass("text-primary");
        }
);

$("#spanCheck").click(function () {
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
        $("#txtDescripcion").prop("disabled", false);
        $("#numOrden").prop("disabled", false);
        $("#checkBoxLever").prop("disabled", false);
        $(".switch-content").removeClass("disabled");
        $("#divBtnAgregar").hide();
        $("#divBtnUpdate").show();
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');
    }
});
function validaDatos(){
    let codigo = $("#txtCodigo").val();
    let nombre = $("#txtDescripcion").val();
    let validar = true;
    
    if (codigo.length <= 0) {
        $("#txtCodigo").addClass("is-invalid");
        handlerMessageError("Código no debe estar vacío");
        validar = false;
    }
    if (nombre.length <= 0) {
        $("#txtDescripcion").addClass("is-invalid");
        handlerMessageError("Nombre no debe estar vacío");
        validar = false;
    }
    return validar;
}

$("#btnAgregarUnidadMedida").click(function() {
	$('#btnLimpiar').removeClass('disabled');
	if(!validaDatos())
			return;

	    let filtros = JSON.stringify({
        cumCodigo: $("#txtCodigo").val(),
        cumDescripcion: $("#txtDescripcion").val(),
        cumSort: $("#numOrden").val(),
        cumActivo: $("#checkBoxLever").is(":checked") ? "S" : "N"
        
    });
    $.ajax({
        method: "POST",
        url: getctx + "/api/unidadesMedida/save",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
		if(data.status == 300){
			handlerMessageWarning(data.dato);
		}
		if(data.status == 200){
			handlerMessageOk(data.dato);
		}
			limpiarForm();
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error.");
        }
    });
});
$("#btnUpdateUnidadMedida").click(function() {
	$('#btnLimpiar').removeClass('disabled');
	if(!validaDatos())
			return;
		
	    let filtros = JSON.stringify({
        cumCodigo: $("#txtCodigo").val(),
        cumDescripcion: $("#txtDescripcion").val(),
        cumSort: $("#numOrden").val(),
        cumActivo: $("#checkBoxLever").is(":checked") ? "S" : "N",
        cumIdunidadmedida: $("#txtIdUnidadMedida").val()
    });
    $.ajax({
        method: "POST",
        url: getctx + "/api/unidadesMedida/update",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
		if(data.status == 300){
			handlerMessageWarning(data.dato);
		}
		if(data.status == 200){
			handlerMessageOk(data.dato);
		}
		    $(".formSecciones").prop("disabled", true);
		    $("#divBtnUpdate").hide();
		    $("#divBtnAgregar").show();
		    $('#btnLimpiar').removeClass('disabled');
            $(".switch-content").removeClass("disabled");
		    activarEdit();
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error.");
        }
    });
});
$("#btnCancelUnidadMedida").click(function() {
    $("#txtCodigo").prop("disabled", true);
    $("#txtDescripcion").prop("disabled", true);
    $("#numOrden").prop("disabled", true);
    $("#checkBoxLever").prop("disabled", true);
    $("#divBtnUpdate").hide();
    $("#divBtnAgregar").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
});

$("#btnBuscarFiltro").click(function () {
    filtrarUnidadesMedida($("#txtCodigoFiltro").val(), $("#txtUnidadFiltro").val());
});


$("#btnLimpiar").click(function (){
	$("#collapseHeader").collapse('show');
    limpiarForm();
    $('.selectpicker').selectpicker('refresh');
});

$("#potenciaUno").click(function () {
    concatenar("¹");
});
$("#potenciaDos").click(function () {
    concatenar("²");
});
$("#potenciaTres").click(function () {
    concatenar("³");
});
$("#potenciaCuatro").click(function () {
    concatenar("⁴");
});
$("#potenciaCinco").click(function () {
    concatenar("⁵");
});
$("#potenciaSeis").click(function () {
    concatenar("⁶");
});
$("#potenciaSiete").click(function () {
    concatenar("⁷");
});
$("#potenciaOcho").click(function () {
    concatenar("⁸");
});
$("#potenciaNueve").click(function () {
    concatenar("⁹");
});
$("#potenciaCero").click(function () {
    concatenar("⁰");
});

$("#superindexruno").click(function () {
    concatenar("⁻");
});
$("#superindexrdos").click(function () {
    concatenar("°");
});
$("#superindexrtres").click(function () {
    concatenar("ª");
});
$("#superindexcuatro").click(function () {
    concatenar("µ");
});

function filtrarUnidadesMedida() {
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        unidad: $("#txtUnidadFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "post",
        //data: "filtro=1&codigo=" + codigo + "&descripcion=" + descripcion,
        url: getctx + "/api/unidadesMedida/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
            var cont = 0;
            if (data.status === 200){
                $("#tbodyFiltro").empty();
                if (data.dato.length > 0) {
                    $("#lblErrorFiltro").hide();
                    data.dato.forEach(function (um) {
                        cont++;
                        $("#tbodyFiltro").append(`<tr class='pointer' onclick='selectUnidad(this)' >
                                                    <th class='row mx-auto'>${cont}</th>
                                                    <td>${um.cumCodigo}</td>
                                                    <td>${um.cumDescripcion}</td>
                                                    <td class='idUnidad' style='display:none'>${um.cumIdunidadmedida}</td>
                                                </tr>`);
                    });
                    $("#lblTotalFiltro").show();
                    $("#totalFiltro").text(cont);
                } else {
                    $("#lblErrorFiltro").show();
                    $("#lblTotalFiltro").hide();
                }
            } else {
                handlerMessageError(data.message);
                console.log(data.message);
            }
        },
        error: function (msg) {
            handlerMessageError("Ha ocurrido un error");
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

function concatenar(texto) {
    var textoUnidad = $("#txtDescripcion").val();
    var nuevoTexto = textoUnidad + texto;
    if ($("#txtDescripcion").prop("disabled")) {

    } else {
        $("#txtDescripcion").val(nuevoTexto);
    }

}
function selectUnidad(a) {
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idUnidad = $row.find(".idUnidad").text();
    filtrarbyId(idUnidad);
    activarEdit();
}
function activarEdit() {
    $("#iEditUnidadMedida").removeClass("text-dark-50");
    $("#iEditUnidadMedida").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditUnidadMedida").removeClass("text-blue");
    $("#iEditUnidadMedida").addClass("text-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
}

function filtrarbyId(idUnidad,collapse = true) {
    $.ajax({
        type: "post",
        data: "getUnidad=1&idUnidad=" + idUnidad,
        datatype: "json",
        success: function (mensaje) {
            let dato = JSON.parse(mensaje);
            if (!dato.hasOwnProperty('error')){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                var unidadmedida = dato.unidadmedida[0];
                $("#txtIdUnidadMedida").val(unidadmedida.IdUnidad);
                $("#txtCodigo").val(unidadmedida.CodigoUnidad);
                $("#txtCodigo").prop("disabled", true);
                $("#txtDescripcion").val(unidadmedida.DescripcionUnidad);
                $("#txtDescripcion").prop("disabled", true);
                $("#numOrden").val(unidadmedida.OrdenUnidad);
                $("#numOrden").prop("disabled", true);
                if (unidadmedida.EstadoUnidad == "S") {
                    $("#checkBoxLever").prop("checked", true);
                    $("#lblEstado").text("Activo");
                    $("#txtEstado").val(unidadmedida.EstadoUnidad);
                } else {
                    $("#checkBoxLever").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                    $("#txtEstado").val(unidadmedida.EstadoUnidad);
                }
                unidadmedida.EstadoUnidad === "S" ? $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                $("#checkBoxLever").prop("disabled", true);
                $("#btnAgregarGlosa").prop("disabled", true);
                $(".switch-content").addClass("disabled");
                $('.selectpicker').selectpicker('refresh');
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

function limpiarForm(){
    $("#txtCodigo").val("");
    $("#txtCodigo").prop("disabled",false);
    $("#txtDescripcion").val("");
    $("#txtDescripcion").prop("disabled",false);
    $("#numOrden").val(1);
    $("#numOrden").prop("disabled",false);
    $("#lblEstado").text("Activo");
    $("#checkBoxLever").prop("checked",true);
    $("#checkBoxLever").prop("disabled",false);
    $(".switch-content").removeClass("disabled inactivo");
    $("#divBtnEditar").hide();
    $("#divBtnAgregar").show();
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
    var idCausa = $row.querySelector('td.idUnidad').textContent;
    filtrarbyId(idCausa,false);
}
