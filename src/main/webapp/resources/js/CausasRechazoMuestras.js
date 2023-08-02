/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $(".ocultar").hide();
    filtrarLike();
    let messageSuccess = $("#messageSuccess").val();
    let messageError = $("#messageError").val();
    let errorPageLoad = $("#errorPageLoad").val();
    if (typeof messageSuccess !== 'undefined' && messageSuccess!==null){
        handlerMessageOk(messageSuccess);
    }
    if (typeof messageError !== 'undefined' && messageError!==null){
        handlerMessageError(messageError);
    }
    if (typeof errorPageLoad !== 'undefined' && errorPageLoad!==null){
        handlerMessageError(errorPageLoad);
    }
    desactivarEdit()
});
new ImaskInputNumber('txtOrden', false, 0, 99);
$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtCausaFiltro").prop("disabled", true);
    } else {
        $("#txtCausaFiltro").prop("disabled", false);
    }
    filtrarLike();
});

$("#txtCausaFiltro").keyup(function () {
    if ($("#txtCausaFiltro").val().length > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
    }
    filtrarLike();
});

$("#chkMostrarActivos").change(function(){
    filtrarLike();
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtCausaFiltro").val("");
    $("#txtCausaFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked",false);
    filtrarLike();
});

$("#nuevaCausa").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    desactivarEdit();
    limpiarFormulario();
});



$("#buscarCausa").click(function () {
    $(".collapse").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionExample8").offset().top
    }, 700);
    desactivarEdit();
    $("#btnAgregarCausa").show();
    $("#btnGuardarUpdate").hide();
    filtrarLike();
});

$("#btnLimpiar").click(function () {
	$("#collapseHeader").collapse('show');
    desactivarEdit();
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

function limpiarFormulario(){
    $("#txtIdCausaRechazo").val("");
    $("#txtCodigo").val("");
    $("#txtDescripcionCausaRechazo").val("");
    $("#txtOrden").val("");
    $("#checkBoxActivo").prop("checked", true);
    $("#lblEstado").text("Activo");
    
    $("#txtCodigo").prop("disabled", false);
    $("#txtDescripcionCausaRechazo").prop("disabled", false);
    $("#txtOrden").prop("disabled", false);
    $("#checkBoxActivo").prop("disabled", false);
    $("#btnAgregarCausa").prop("disabled", false);
    
    $("#btnAgregarCausa").show();
    $("#btnGuardarUpdate").hide();
    $('.selectpicker').selectpicker('refresh');
    $(".switch-content").removeClass("disabled inactivo");
}

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

$("#btnEditarCausa").click(function() {
    if (localStorage.getItem("btnEditarCausa") === "activo"){
        $(".switch-content").removeClass("disabled");
        $("#txtCodigo").prop("disabled", false);
        $("#txtDescripcionCausaRechazo").prop("disabled", false);
        $("#txtOrden").prop("disabled", false);
        $("#checkBoxActivo").prop("disabled", false);
        //$("#btnAgregarCausa").prop("disabled", true);
        //$("#btnAgregarCausa").removeClass("disabled");
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
        desactivarEdit();
        $('#btnLimpiar').addClass('disabled');
    }
});
let idCausaRechazo = 0;
function filtrarLike(){
    let codigo = $("#txtCodigoFiltro").val();
    let descripcion = $("#txtCausaFiltro").val();
    let activo = $("#chkMostrarActivos").is(":checked") ? "S" : "N";
    $.ajax({
        type: "post",
        data: "filtro=1&codigo=" + codigo + "&descripcion=" + descripcion + "&activo=" + activo,
        url: "CausasRechazoMuestras",
        datatype: "json",
        success: function (mensaje) {
            var cont = 0;
            var dato = JSON.parse(mensaje);
            //console.log(dato);
            if (!dato.hasOwnProperty('error')){
                $("#tbodyFiltro").empty();
                if (dato.causa.length > 0) {
                    dato.causa.forEach(function (causa) {
                        cont++;
                        $("#tbodyFiltro").append("<tr class='pointer' onclick='selectCausa(this)' ><th class='row mx-auto'>" + cont + "</th><td class=''>" + causa.codigo + "</td><td class=''>" + causa.descripcion + "</td><td class='idCausa' style='display:none'>" + causa.id + "</td></tr>");
                    });
                    $("#lblErrorFiltro").hide();
                    $("#lblTotalFiltro").show();
                    $("#totalFiltro").text(cont);
                } else {
                    $("#lblErrorFiltro").show();
                    $("#lblTotalFiltro").hide();
                }
            } else {
                handlerMessageError(dato.error);
            }
        },
        error: function (msg) {
            handlerMessageError("Ha ocurrido un error");
            console.log(msg.responseText);
        }
    });    
}
function insertCausaRechazo(){
	if(!validarForm())
    	return;
    
    let data = JSON.stringify({
        ccrmCodigo: $("#txtCodigo").val(),
        ccrmDescripcion: $("#txtDescripcionCausaRechazo").val(),
        ccrmSort: $("#txtOrden").val(),
        ccrmActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/CausaRechazoMuestras/insert",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
			console.log(data)
			if(data.status == 200)
				handlerMessageOk(data.message);
				filtrarLike();
				$('#btnLimpiar').click();
				
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al ingresar");
        }
    });   
}
$( "#btnAgregarCausa" ).click(function() {
  insertCausaRechazo();
});
function updateCausaRechazo(){
	if(!validarForm())
    	return;
    let data = JSON.stringify({
		ccrmIdcausarechazo:idCausaRechazo,
        ccrmCodigo: $("#txtCodigo").val(),
        ccrmDescripcion: $("#txtDescripcionCausaRechazo").val(),
        ccrmSort: $("#txtOrden").val(),
        ccrmActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/CausaRechazoMuestras/update",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
			console.log(data)
			if(data.status == 200)
				handlerMessageOk(data.message);
				filtrarLike()
				
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al ingresar");
        }
    });   
}

$( "#btnGuardarUpdate" ).click(function() {
  updateCausaRechazo();
});

function validarForm(){
    let codigo = $("#txtCodigo").val();
    let nombre = $("#txtDescripcionCausaRechazo").val();
    let validador = true;
    if (codigo.trim() === ""){
        $("#txtCodigo").addClass('is-invalid');
        handlerMessageError("Código no debe estar vacío");
        validador = false;
    }else{
		$("#txtCodigo").removeClass('is-invalid');
	}
    if (nombre.trim() === ""){
        $("#txtDescripcionCausaRechazo").addClass('is-invalid');
        handlerMessageError("Causa de rechazo no debe estar vacío");
        validador = false;
    }else{
		$("#txtDescripcionCausaRechazo").removeClass('is-invalid');
	}
	return validador;
}
function selectCausa(a) {
    var $row = $(a).closest("tr"); // Find the row
    var idCausa = $row.find(".idCausa").text();
    filtrarbyId(idCausa);
    seleccionarFila(a,'#tableFiltro');
}



function activarEdit() {
    $("#iEditCausa").removeClass("text-dark-50");
    $("#iEditCausa").addClass("text-blue");
    
    localStorage.setItem("btnEditarCausa", "activo");
}

function desactivarEdit() {
    $("#iEditCausa").removeClass("text-blue");
    $("#iEditCausa").addClass("text-dark-50");
    
    localStorage.setItem("btnEditarCausa", "inactivo");
}

function filtrarbyId(idCausa,collapse = true) {
    $.ajax({
        type: "post",
        data: "filtroId=1&idCausa=" + idCausa,
        datatype: "json",
        success: function (mensaje) {
            var dato = JSON.parse(mensaje);
            if(!dato.hasOwnProperty('error')){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                let causa = dato.causa[0];
                idCausaRechazo = parseInt(causa.idCausa);
                $("#txtIdCausaRechazo").val(causa.idCausa);
                $("#txtCodigo").val(causa.codigo);
                $("#txtCodigo").prop("disabled", true);
                $("#txtDescripcionCausaRechazo").val(causa.descripcion);
                $("#txtDescripcionCausaRechazo").prop("disabled", true);
                $("#txtOrden").val(causa.orden);
                $("#txtOrden").prop("disabled", true);
                $(".switch-content").addClass("disabled");
                causa.activo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                if (causa.activo == "S") {
                    $("#checkBoxActivo").prop("checked", true);
                    $("#lblEstado").text("Activo");
                } else {
                    $("#checkBoxActivo").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                }
                $("#checkBoxActivo").prop("disabled", true);

                $("#divAgregarBtn").show();
                $("#divActualizarBtn").hide();
                $("#btnAgregarCausa").prop("disabled", true);
                activarEdit();
            } else {
                handlerMessageError(dato.error);
            }
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error.");
        }
    });
}



$("#btnCancelUpdate").click(function() {
    $("#txtCodigo").prop("disabled", true);
    $("#txtDescripcionCausaRechazo").prop("disabled", true);
    $("#txtOrden").prop("disabled", true);
    $("#checkBoxActivo").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
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
    var idCausa = $row.querySelector('td.idCausa').textContent;
    filtrarbyId(idCausa,false);
}


function getNotificationModalData() {
    let rechazo = {
        "idCausaRechazo": $("#txtIdCausaRechazo").val(),
        "c": $("#ipEquipo").val(),
        "ccrmCodigo": $("#txtCodigo").val(),
        "ccrmDescripcion": $("#txtDescripcionCausaRechazo").val(),
        "ccrmSort": $("#txtOrden").val(),
        "ccrmActivo": $("#checkBoxActivo").val(),
    };
    return rechazo;
  
  }

  function createArrayData(){
    const formData = new FormData();
    formData.append("idCausaRechazo", $("#txtIdCausaRechazo").val());
    formData.append("c", $("#ipEquipo").val());
    formData.append("ccrmCodigo", $("#txtCodigo").val());
    formData.append("ccrmDescripcion", $("#txtDescripcionCausaRechazo").val());
    formData.append("ccrmSort", $("#txtOrden").val());
    formData.append("ccrmActivo", $("#checkBoxActivo").val());

   
    return formData;
}

// $("#btnGuardarUpdate").click(function (e) {
//     console.log(e);
// 	e.preventDefault();
//     $.ajax({
//         type: "post",
//         data:"update=1&idCausaRechazo=" + $("#txtIdCausaRechazo").val() + "&c=" + $("#ipEquipo").val() + "&ccrmCodigo=" + $("#txtCodigo").val() + "&ccrmDescripcion=" + $("#txtDescripcionCausaRechazo").val() + "&ccrmSort=" + $("#txtOrden").val() + "&ccrmActivo=" + $("#checkBoxActivo").val(),
//         success: function (mensaje) {
//             console.log(mensaje);
//         }
//     });
// });
