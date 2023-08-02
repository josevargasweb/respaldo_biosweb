$(document).ready(function () {
	
    $(".ocultar").hide();
    filtrarLike();
    cargarCentrosSalud(centrosSalud());
    desactivarEdit();
    let messageSuccess = $("#messageSuccess").val();
    let messageError = $("#messageError").val();
    if (typeof messageSuccess !== 'undefined' && messageSuccess!==null){
        if(messageSuccess == 'Ya existe'){
            handlerMessageWarning('El código ingresado ya existe');
        }else{
            handlerMessageOk(messageSuccess);
        }
    }
    if (typeof messageError !== 'undefined' &&messageError!==null){
        handlerMessageError(messageError);
    }
    $(".selectpicker").selectpicker({
        noneSelectedText: "",
    });
    $('.selectpicker').selectpicker('refresh');
});
new ImaskInputNumber('numOrden', false, 1, 9999);


let centrosSalud = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/instituciones/centrosdesalud/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

function cargarCentrosSalud(centrossalud){
    let options = `<option value="0" selected disabled hidden></option>`;
    centrossalud.forEach((element) => {
        options += `<option value="${element.ccdsIdcentrodesalud}">${element.ccdsDescripcion}</option>`;
    });
    $("#idCentroFiltro, #csCentro").html(options);
    
}

$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtDescripcionFiltro").prop("disabled", true);
        $("#idCentroFiltro").prop("disabled", true);
    } else {
        $("#txtDescripcionFiltro").prop("disabled", false);
        $("#idCentroFiltro").prop("disabled", false);
    }
    $('.selectpicker').selectpicker('refresh');
    filtrarLike();
});

$("#txtDescripcionFiltro").keyup(function () {
    if ($("#txtDescripcionFiltro").val().length > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
        $("#idCentroFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
        $("#idCentroFiltro").prop("disabled", false);
    }
    $('.selectpicker').selectpicker('refresh');
    filtrarLike();
});

$("#idCentroFiltro").on('change', function () {
    if ($("#idCentroFiltro").val() > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
        $("#txtDescripcionFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
        $("#txtDescripcionFiltro").prop("disabled", false);
    }
    $('.selectpicker').selectpicker('refresh');
    filtrarLike();
});

$("#chkMostrarActivos").change(function(){
    filtrarLike();
});


$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtDescripcionFiltro").val("");
    $("#txtDescripcionFiltro").prop("disabled", false);
    $("#idCentroFiltro").val(1);
    $("#idCentroFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked", false);
    filtrarLike();
});

$("#nuevoServicio").click(function () {
    $("#collapseHeader").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarServicio").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionServicios").offset().top
    }, 700);
});
/*
$("#btnLimpiarModal").click(function () {
    $("#txtHost1").val("");
    $("#txtHost2").val("");
    $("#txtHost3").val("");
    $("#txtHostMicro").val("");
});
*/
function filtrarLike() {
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        descripcion: $("#txtDescripcionFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N",
        idCentro: $("#idCentroFiltro").val() == null ? 0 : $("#idCentroFiltro").val()
    });
    
    console.log($("#idCentroFiltro").val());

    $.ajax({
        type: "post",
        url: getctx + "/api/servicios/filtro",
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
                    dato.forEach(function (servicio) {
                        cont++;
                        $("#tbodyFiltro").append("<tr class='pointer' onclick='selectServicio(this)' >\n\
                                                    <th class='row mx-auto'>" + cont + "</th>\n\
                                                    <td class=''>" + servicio.csCodigo + "</td>\n\
                                                    <td class=''>" + servicio.csDescripcion + "</td>\n\
                                                    <td class='idServicio' style='display:none'>" + servicio.csIdservicio + "</td>\n\
                                                </tr>");
                    });
                    $("#lblErrorFiltro").hide();
                    $("#lblTotalFiltro").show();
                    $("#totalFiltro").text(cont);
                } else {
                    $("#lblTotalFiltro").hide();
                    $("#lblErrorFiltro").show();
                }
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

function selectServicio(a) {
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idServicio = $row.find(".idServicio").text();
    filtrarbyId(idServicio);
    activarEdit();
}

function activarEdit() {
    $("#iEditServicio").removeClass("text-dark-50");
    $("#iEditServicio").addClass("text-blue");
    
    localStorage.setItem("btnEditarServicio", "activo");
}

function desactivarEdit() {
    $("#iEditServicio").removeClass("text-blue");
    $("#iEditServicio").addClass("text-dark-50");
    
    localStorage.setItem("btnEditarServicio", "inactivo");
}
let idCfgServicios = 1;
function filtrarbyId(idServicio,collapse = true) {
    $.ajax({
        type: "post",
        data: "filtroId=1&idServicio=" + idServicio,
        datatype: "json",
        success: function (mensaje) {
            let dato = JSON.parse(mensaje);
            if (!dato.hasOwnProperty('error')){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                var servicio = dato.servicio[0];
                idCfgServicios = servicio.IdServicio;
                $("#txtIdServicio").val(servicio.IdServicio);
                $("#txtCodigo").val(servicio.Codigo);
                $("#txtCodigo").prop("disabled", true);
                $("#txtDescripcion").val(servicio.Descripcion);
                $("#txtDescripcion").prop("disabled", true);
                $("#csCentro").val(servicio.CodCentro);
                $("#csCentro").prop("disabled", true);
                $("#numOrden").val(servicio.Orden);
                $("#numOrden").prop("disabled", true);
                $("#txtEmail").val(servicio.Email);
                $("#txtEmail").prop("disabled", true);
                $("#txtHost1").val(servicio.Host1);
                $("#txtHost1").prop("disabled", true);
                $("#txtHost2").val(servicio.Host2);
                $("#txtHost2").prop("disabled", true);
                $("#txtHost3").val(servicio.Host3);
                $("#txtHost3").prop("disabled", true);
                $("#txtHostMicro").val(servicio.HostMicro);
                $("#txtHostMicro").prop("disabled", true);
                $(".switch-content").addClass("disabled");
                servicio.Activo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                if (servicio.Activo == "S") {
                    $("#checkBoxActivo").prop("checked", true);
                    $("#lblEstado").text("Activo");
                } else {
                    $("#checkBoxActivo").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                }
                $("#checkBoxActivo").prop("disabled", true);
                if (servicio.Urgente == "S") {
                    $("#checkBoxUrgente").prop("checked", true);
                } else {
                    $("#checkBoxUrgente").prop("checked", false);
                }
                $("#checkBoxUrgente").prop("disabled", true);
                if (servicio.Indicador == "S") {
                    $("#checkBoxIndicador").prop("checked", true);
                } else {
                    $("#checkBoxIndicador").prop("checked", false);
                }
                $("#checkBoxIndicador").prop("disabled", true);

                $("#btnAgregarServicio").prop("disabled", true);
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

$("#btnEditarServicio").click(function() {
    if (localStorage.getItem("btnEditarServicio") === "activo"){
        $(".switch-content").removeClass("disabled");
        $(".formServicio").prop("disabled", false);
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
    }
});

$("#btnLimpiar").click(function () {
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

function limpiarFormulario(){
    $(".switch-content").removeClass("disabled inactivo");
    $("#txtIdServicio").val("");
    $("#txtCodigo").val("");
    $("#txtDescripcion").val("");
    $("#csCentro").val("");
    $("#numOrden").val(1);
    $("#txtEmail").val("");
    $("#txtHost1").val("");
    $("#txtHost2").val("");
    $("#txtHost3").val("");
    $("#txtHostMicro").val("");
    $("#csCentro").val(1);
    $("#lblEstado").text("Activo");
    $("#checkBoxActivo").prop("checked", true);
    $("#checkBoxUrgente").prop("checked", false);
    $("#checkBoxIndicador").prop("checked", false);
    $(".formServicio").prop("disabled", false);
    $("#btnAgregarServicio").prop("disabled", false);
    $("#divAgregarBtn").show();
    $("#divActualizarBtn").hide();
    desactivarEdit();
}

$.getJSON("https://api.ipify.org?format=json", function (data) {
    $("#ipEquipo").val(data.ip);
});

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

$("#btnCancelUpdate").click(function() {
    $(".switch-content").addClass("disabled");
    $(".formServicio").prop("disabled", true);
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});
/*
$("#formRegistroServicio").submit(function(){
    let codigo = $("#txtCodigo").val();
    let descripcion = $("#txtDescripcion").val();
    let idCentro = $("#csCentro").val();
    if (codigo.trim() === "" || descripcion.trim() === ""){
        if (codigo.trim() === ""){
            $("#txtCodigo").addClass('is-invalid');
            handlerMessageError("Código no debe estar vacío");
        }
        if (descripcion.trim() === ""){
            $("#txtDescripcion").addClass('is-invalid');
            handlerMessageError("Descripción no debe estar vacía");
        }
        if (idCentro == 0){
            $("#csCentro").addClass('is-invalid');
            handlerMessageError("Debe seleccionar centro de salud");
        }
        return false;
    } else {
        return true;
    }
});*/
function validarServicios(){
	    let codigo = $("#txtCodigo").val();
    let descripcion = $("#txtDescripcion").val();
    let idCentro = $("#csCentro").val();
    if (codigo.trim() === "" || descripcion.trim() === ""){
        if (codigo.trim() === ""){
            $("#txtCodigo").addClass('is-invalid');
            handlerMessageError("Código no debe estar vacío");
        }
        if (descripcion.trim() === ""){
            $("#txtDescripcion").addClass('is-invalid');
            handlerMessageError("Descripción no debe estar vacía");
        }
        if (idCentro == 0 || idCentro == null){
            $("#csCentro").addClass("is-invalid").selectpicker("setStyle");
            handlerMessageError("Debe seleccionar centro de salud");
        }
         $('.selectpicker').selectpicker('refresh');
        return false;
    } else {
        return true;
    }
}

$("#txtCodigo").keyup(function () {
    if ($("#txtCodigo").val().trim().length > 0) {
        $("#txtCodigo").removeClass('is-invalid');
    }
});

$("#txtDescripcion").keyup(function () {
    if ($("#txtDescripcion").val().trim().length > 0) {
        $("#txtDescripcion").removeClass('is-invalid');
    }
});

$("#csCentro").change(function () {
    if ($("#csCentro").val() > 0) {
        $("#csCentro")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
    }
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
    var idCausa = $row.querySelector('td.idServicio').textContent;
    filtrarbyId(idCausa,false);
}
$("#btnAgregarServicio").click(function() {
	insertServicio();
});
function insertServicio() {
	if(!validarServicios())
		return;
		
    let data = JSON.stringify({
		//ccdsIdcentrodesalud: idCentroSaludSelec,
        csCodigo: $("#txtCodigo").val().toUpperCase(),
        csDescripcion: $("#txtDescripcion").val().toUpperCase(),
        cfgCentrosdesalud: $("#csCentro").val(),
        csSort: $("#numOrden").val().toUpperCase(),
        csEmail: $("#txtEmail").val(),
        csActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N",
        csUrgente: $("#checkBoxUrgente").is(":checked") ? "S" : "N",
        csIndicador: $("#checkBoxIndicador").is(":checked") ? "S" : "N",
        csHost: $("#txtHost1").val().toUpperCase(),
        csHost2: $("#txtHost2").val().toUpperCase(),
        csHost3: $("#txtHost3").val().toUpperCase(),
        csHostmicro: $("#txtHostMicro").val().toUpperCase()
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/servicios/insert",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
	console.log(data)
			filtrarLike();
			limpiarFormulario();
			if(data.status == 200)
				handlerMessageOk(data.message);
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al cargar Servicios");
        }
    });
}
$("#btnUpdateServicio").click(function() {
	updateServicio();
});
function updateServicio() {
	if(!validarServicios())
		return;
		
    let data = JSON.stringify({
		csIdservicio: idCfgServicios,
        csCodigo: $("#txtCodigo").val().toUpperCase(),
        csDescripcion: $("#txtDescripcion").val().toUpperCase(),
        cfgCentrosdesalud: $("#csCentro").val(),
        csSort: $("#numOrden").val().toUpperCase(),
        csEmail: $("#txtEmail").val(),
        csActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N",
        csUrgente: $("#checkBoxUrgente").is(":checked") ? "S" : "N",
        csIndicador: $("#checkBoxIndicador").is(":checked") ? "S" : "N",
        csHost: $("#txtHost1").val().toUpperCase(),
        csHost2: $("#txtHost2").val().toUpperCase(),
        csHost3: $("#txtHost3").val().toUpperCase(),
        csHostmicro: $("#txtHostMicro").val().toUpperCase()
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/servicios/update",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
	console.log(data)
			filtrarLike();
			$('#btnLimpiar').removeClass('disabled');
			if(data.status == 200)
				handlerMessageOk(data.message);
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al cargar Servicios");
        }
    });
}