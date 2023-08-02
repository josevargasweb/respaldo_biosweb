$(document).ready(function(){
    $(".ocultar").hide();
    cargarDatosCentros();
    cargarInstituciones(instituciones());
    desactivarEdit();
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
});

let instituciones = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/instituciones/institucionesdesalud",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

function cargarInstituciones(instituciones){
    let options = "";
    instituciones.forEach((element) => {
        //Dejar fijo la institución SAN BORJA
        if (element.cidsIdinstituciondesalud === 3){
            options += `<option value="${element.cidsIdinstituciondesalud}">${element.cidsDescripcion}</option>`;
        }
    });
   // $("#selectInstituciones").html(options);
}

function cargarDatosCentros() {
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        nombre: $("#txtNombreFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/instituciones/centrosdesalud/filtro",
        data: filtros,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
            let cont = 0;
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            $("#lblTotalFiltro").show();
            
            for (let centro of data){
                cont++;
                $("#tbodyFiltro").append("<tr class='pointer' onclick='selectCentro(this)' ><th class='row mx-auto'>" + cont + "</th><td class=''>" + centro.ccdsCodigo + "</td><td class=''>" + centro.ccdsDescripcion + "</td><td class='id' style='display:none'>" + centro.ccdsIdcentrodesalud + "</td></tr>");
            }
            if (cont === 0){
                $("#lblErrorFiltro").show();
                $("#lblTotalFiltro").hide();
            }
            $("#contRegistros").text(cont);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al cargar centros de salud");
        }
    });
}

$("#txtCodigoFiltro").keyup(function () {
    cargarDatosCentros();
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtNombreFiltro").prop("disabled", true);
    } else {
        $("#txtNombreFiltro").prop("disabled", false);
    }
});

$("#txtNombreFiltro").keyup(function () {
    cargarDatosCentros();
    if ($("#txtNombreFiltro").val().length > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
    }
});

$("#chkMostrarActivos").change(function(){
    cargarDatosCentros();
});

function selectCentro(a){
    var $row = $(a).closest("tr"); // Find the row
    var id = $row.find(".id").text();
    buscarPorId(id);
    seleccionarFila(a,'#tableFiltro');
}

function activarEdit() {
    $("#iEditCentro").removeClass("text-dark-50");
    $("#iEditCentro").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditCentro").removeClass("text-blue");
    $("#iEditCentro").addClass("text-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
}
let idCentroSaludSelec;
function buscarPorId(id,collapse = true){
    $.ajax({
        type: "get",
        //data: "buscarPorId=1&id=" + id,
        url : getctx + "/api/instituciones/centrosdesalud/list/"+id,
        async: false,
        datatype: "json",
        success: function (centro) {
            //let dato = JSON.parse(data);
            //let variable = dato.elementos[0]; //reemplazar elementos por nombre del array json definido en controller
            if(collapse){
                $("#collapseHeader").collapse('hide');
            }
            idCentroSaludSelec = centro.ccdsIdcentrodesalud;
            $("#btnAgregar").prop("disabled", true);
            $("#txtNombreCentro").prop("disabled", true);
            $("#txtCodigoCentro").prop("disabled", true);
            $("#txtDireccionCentro").prop("disabled", true);
            $("#txtTelefonoCentro").prop("disabled", true);
            $("#checkBoxActivo").prop("disabled", true);
            $("#txtIdCentro").val(centro.ccdsIdcentrodesalud);
            $("#txtCodigoCentro").val(centro.ccdsCodigo);
            $("#txtNombreCentro").val(centro.ccdsDescripcion);
            $("#txtInstitucionCentro").val(centro.ccdsIdinstituciondesalud);
            $("#txtDireccionCentro").val(centro.ccdsDireccion);
            $("#txtTelefonoCentro").val(centro.ccdsTelefono);
            $(".switch-content").addClass("disabled");
            centro.ccdsActivo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            if (centro.ccdsActivo === "S") {
                $("#checkBoxActivo").prop("checked", true);
                $("#lblEstado").text("Activo");
            } else {
                $("#checkBoxActivo").prop("checked", false);
                $("#lblEstado").text("Inactivo");
            }
            activarEdit();
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

$("#nuevoCentro").click(function () {
    $("#collapseHeader").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarCentro").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionHeader").offset().top
    }, 700);
});

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtNombreFiltro").val("");
    $("#txtNombreFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked",false);
    cargarDatosCentros();
});

$("#btnLimpiar").click(function () {
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    v
});

$("#btnEditarCentro").click(function() {
    if (localStorage.getItem("botonEditar") === "activo"){
        $(".switch-content").removeClass("disabled");
        $("#txtCodigoCentro").prop("disabled", false);
        $("#txtNombreCentro").prop("disabled", false);
        $("#txtDireccionCentro").prop("disabled", false);
        $("#txtTelefonoCentro").prop("disabled", false);
        $("#checkBoxActivo").prop("disabled", false);
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
    }
});

function limpiarFormulario(){
    $(".switch-content").removeClass("disabled inactivo");
    $("#txtCodigoCentro").val("");
    $("#txtCodigoCentro").prop("disabled", false);
    $("#txtNombreCentro").val("");
    $("#txtNombreCentro").prop("disabled", false);
    $("#txtDireccionCentro").val("");
    $("#txtDireccionCentro").prop("disabled", false);
    $("#txtTelefonoCentro").val("");
    $("#txtTelefonoCentro").prop("disabled", false);
    $("#checkBoxActivo").prop("checked", "checked");
    $("#lblEstado").text("Activo");
    $("#checkBoxActivo").prop("disabled", false);
    $("#btnAgregar").prop("disabled", false);
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    desactivarEdit();
}

$("#btnCancelUpdate").click(function() {
    $(".switch-content").addClass("disabled");
    $("#txtCodigoCentro").prop("disabled", true);
    $("#txtNombreCentro").prop("disabled", true);
    $("#txtDireccionCentro").prop("disabled", true);
    $("#txtTelefonoCentro").prop("disabled", true);
    $("#checkBoxActivo").prop("disabled", true);
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});

//insertar todos los campos que obligatorios
/*
$("#formRegistroCentro").submit(function(){
    let codigo = $("#txtCodigoCentro").val();
    let nombre = $("#txtNombreCentro").val();
    let validar = true;
    if (codigo.trim() === ""){
        $("#txtCodigoCentro").addClass('is-invalid');
        handlerMessageError("Código no debe estar vacío");
        validar = false;
    }
    if (nombre.trim() === ""){
        $("#txtNombreCentro").addClass('is-invalid');
        handlerMessageError("Nombre no debe estar vacío");
        validar = false;
    }
    return validar;
    
});*/
function validarForm(){
	let codigo = $("#txtCodigoCentro").val();
    let nombre = $("#txtNombreCentro").val();
    let validar = true;
    if (codigo.trim() === ""){
        $("#txtCodigoCentro").addClass('is-invalid');
        handlerMessageError("Código no debe estar vacío");
        validar = false;
    }
    if (nombre.trim() === ""){
        $("#txtNombreCentro").addClass('is-invalid');
        handlerMessageError("Nombre no debe estar vacío");
        validar = false;
    }
    return validar;
    

}

$("#txtCodigoCentro").blur(function () {
    if ($("#txtCodigoCentro").val() !== null && $("#txtCodigoCentro").val().trim() !== "") {
        comprobarCodigoExiste($("#txtCodigoCentro").val().toUpperCase());
    }
});
    
function comprobarCodigoExiste(codigo){
    $.ajax({
        type: "get",
        url: getctx + "/api/instituciones/existeCentrodesalud/"+codigo,
        datatype: "json",
        success: function(json){
            if (json.status === 200){
                handlerMessageError(json.message);
                buscarPorId(json.dato.ccdsIdcentrodesalud);
            }
            if (json.status === 500){
                handlerMessageError(json.message);
            }
        },
        error: function (msg){
            handlerMessageError("Error al comprobar existencia de código");
            console.log(msg.responseText);
        }
    });
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
    var idCausa = $row.querySelector('td.id').textContent;
    buscarPorId(idCausa,false);
}

$("#btnAgregar").click(function() {
	insertCentrosSalud();
});
function insertCentrosSalud() {
	if(!validarForm())
		return;
    let data = JSON.stringify({
        ccdsCodigo: $("#txtCodigoCentro").val().toUpperCase(),
        ccdsDescripcion: $("#txtNombreCentro").val().toUpperCase(),
        ccdsDireccion: $("#txtDireccionCentro").val().toUpperCase(),
        ccdsTelefono: $("#txtTelefonoCentro").val(),
        ccdsActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/centrosSalud/insert",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
			$('#btnLimpiar').click();
			cargarDatosCentros();
			if(data.status == 200)
				handlerMessageOk(data.message);
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al cargar centros de salud");
        }
    });
}

$("#btnUpdate").click(function() {
	updateCentrosSalud();
    $('.selectpicker').selectpicker('refresh');
});
function updateCentrosSalud() {
	if(!validarForm())
		return;
    let data = JSON.stringify({
		ccdsIdcentrodesalud: idCentroSaludSelec,
        ccdsCodigo: $("#txtCodigoCentro").val().toUpperCase(),
        ccdsDescripcion: $("#txtNombreCentro").val().toUpperCase(),
        ccdsDireccion: $("#txtDireccionCentro").val().toUpperCase(),
        ccdsTelefono: $("#txtTelefonoCentro").val(),
        ccdsActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/centrosSalud/update",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
			cargarDatosCentros();
			if(data.status == 200)
				handlerMessageOk(data.message);
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al cargar centros de salud");
        }
    });
}
