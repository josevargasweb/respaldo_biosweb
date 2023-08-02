//var getctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(document).ready(function(){
    $(".ocultar").hide();
    filtrarLike();
    desactivarEdit();
    let messageSuccess = $("#messageSuccess").val();
    let messageError = $("#messageError").val();
    
    if (typeof messageSuccess !== 'undefined' && messageSuccess!==null){
        handlerMessageOk(messageSuccess);
    }
    if (typeof messageError !== 'undefined' && messageError!==null){
        handlerMessageError(messageError);
        }
});
new ImaskInputNumber('numOrdenDerivador', false ,1, 999);

function filtrarLike() {
    let filtros = JSON.stringify({
        "codigo": $("#txtCodigoFiltro").val(),
        "nombre": $("#txtNombreFiltro").val(),
        "activo": $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "post",
        url: "api/derivadores/filtro",
        data: filtros,
        datatype: "json",
        headers: {
          'Content-Type': 'application/json'
        },
        success: function (json) {
            let cont = 0;
            if (json.status === 200){
                let dato = json.dato;
                $("#tbodyFiltro").empty();
                if (dato.length > 0) {
                    dato.forEach(function (deriv) {
                        cont++;
                        $("#tbodyFiltro").append(`<tr class='pointer' onclick='selectDerivador(this)' >
                                                    <th class='row mx-auto'>${cont}</th>
                                                    <td class=''>${deriv.cderivCodigo}</td>
                                                    <td class=''>${deriv.cderivDescripcion}</td>
                                                    <td class='idDerivador' style='display:none'>${deriv.cderivIdderivador}</td>
                                                </tr>`);
                    });
                    $("#lblTotalFiltro").show();
                    $("#contRegistros").text(cont);
                    $("#lblErrorFiltro").hide();
                } else {
                    $("#lblTotalFiltro").hide();
                    $("#lblErrorFiltro").show();
                }
            } else {
                handlerMessageError(json.message);
            }
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error.");
        }
    });
}

$("#txtCodigoFiltro").keyup(function () {
    //filtrarLike($("#txtCodigoFiltro").val().trim(), "");
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtNombreFiltro").prop("disabled", true);
    } else {
        $("#txtNombreFiltro").prop("disabled", false);
    }
    filtrarLike();
});

$("#txtNombreFiltro").keyup(function () {
    if ($("#txtNombreFiltro").val().length > 0) {
        $("#txtCodigoFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
    }
    filtrarLike();
});

$("#chkMostrarActivos").change(function(){
    filtrarLike();
});

function selectDerivador(a){
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idDerivador = $row.find(".idDerivador").text();
    buscarPorId(idDerivador);
    activarEdit();
}

function activarEdit() {    
    $("#iEditDerivador").removeClass("text-dark-50");
    $("#iEditDerivador").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditDerivador").addClass("text-dark-50");
    $("#iEditDerivador").removeClass("text-blue");
    localStorage.setItem("botonEditar", "inactivo");
}
let idDerivadorParaUpdate = 0;
function buscarPorId(idDerivador,collapse = true){
    $.ajax({
        type: "post",
        data: "buscarPorId=1&idDerivador=" + idDerivador,
        async: false,
        datatype: "json",
        success: function (mensaje) {
            let dato = JSON.parse(mensaje);
            if (!dato.hasOwnProperty('error')){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                let deriv = dato.derivadores[0];
                $("#btnAgregar").prop("disabled", true);
                idDerivadorParaUpdate = deriv.Id;
                $("#txtIdDerivador").val(deriv.Id);
                $("#txtCodigoDerivador").prop("disabled", true);
                $("#txtCodigoDerivador").val(deriv.Codigo);
                $("#txtNombreDerivador").prop("disabled", true);
                $("#txtNombreDerivador").val(deriv.Nombre);
                $("#numOrdenDerivador").prop("disabled", true);
                $("#numOrdenDerivador").val(deriv.Orden);
                $("#txtHostDerivador").prop("disabled", true);
                $("#txtHostDerivador").val(deriv.Host);
                $("#txtHoraDerivador").prop("disabled", true);
                $("#txtHoraDerivador").val(deriv.HorarioAtencion);
                $("#txtFonoDerivador").prop("disabled", true);
                $("#txtFonoDerivador").val(deriv.Telefono);
                $("#txtDireccionDerivador").prop("disabled", true);
                $("#txtDireccionDerivador").val(deriv.Direccion);
                $("#txtEmailDerivador").prop("disabled", true);
                $("#txtEmailDerivador").val(deriv.Email);
                $("#txtEncargadoDerivador").prop("disabled", true);
                $("#txtEncargadoDerivador").val(deriv.Encargado);
                $("#checkBoxActivo").prop("disabled", true);
                $(".switch-content").addClass("disabled");
                deriv.Activo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                if (deriv.Activo === "S") {
                    $("#checkBoxActivo").prop("checked", true);
                    $("#lblEstado").text("Activo");
                } else {
                    $("#checkBoxActivo").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                }
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

$("#nuevoDerivador").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarDerivador").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionHeader").offset().top
    }, 700);
});

$("#circuloBuscarDerivador").hover(
        function () {
            $("#iBuscarDerivador").removeClass("text-blue");
            $("#iBuscarDerivador").addClass("text-white");
        },
        function () {
            $("#iBuscarDerivador").removeClass("text-white");
            $("#iBuscarDerivador").addClass("text-blue");
        }
);
$("#circuloAgregarDerivador").hover(
        function () {
            $("#iAgregarDerivador").removeClass("text-blue");
            $("#iAgregarDerivador").addClass("text-white");
        },
        function () {
            $("#iAgregarDerivador").removeClass("text-white");
            $("#iAgregarDerivador").addClass("text-blue");
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
            $("#iLimpiar").removeClass("text-primary");
            $("#iLimpiar").addClass("text-white");
        },
        function () {
            $("#iLimpiar").removeClass("text-white");
            $("#iLimpiar").addClass("text-blue");
        }
);

$("#circuloEditarDerivador").hover(
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarDerivador").addClass("bg-hover-blue");
                $("#iEditDerivador").removeClass("text-blue");
                $("#iEditDerivador").addClass("text-white");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#iEditDerivador").removeClass("text-white");
                $("#iEditDerivador").addClass("text-blue");
            }
        }
);

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
    $("#chkMostrarActivos").prop("checked", false);
    filtrarLike();
});

$("#btnLimpiar").click(function () {
    limpiarFormulario();
    $("#collapseHeader").collapse('show');
    $('.selectpicker').selectpicker('refresh');
});

$("#btnCancelUpdate").click(function() {
    $(".switch-content").addClass("disabled");
    $("#txtCodigoDerivador").prop("disabled", true);
    $("#txtNombreDerivador").prop("disabled", true);
    $("#numOrdenDerivador").prop("disabled", true);
    $("#txtHostDerivador").prop("disabled", true);
    $("#txtHoraDerivador").prop("disabled", true);
    $("#txtFonoDerivador").prop("disabled", true);
    $("#txtDireccionDerivador").prop("disabled", true);
    $("#txtEmailDerivador").prop("disabled", true);
    $("#txtEncargadoDerivador").prop("disabled", true);
    $("#checkBoxActivo").prop("disabled", true);
    $("#divBtnUpdate").hide();
    $("#divBtnAgregar").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});

$("#btnEditarDerivador").click(function() {
    if (localStorage.getItem("botonEditar") === "activo"){
        $(".switch-content").removeClass("disabled");
        $("#txtCodigoDerivador").prop("disabled", false);
        $("#txtNombreDerivador").prop("disabled", false);
        $("#numOrdenDerivador").prop("disabled", false);
        $("#txtHostDerivador").prop("disabled", false);
        $("#txtHoraDerivador").prop("disabled", false);
        $("#txtFonoDerivador").prop("disabled", false);
        $("#txtDireccionDerivador").prop("disabled", false);
        $("#txtEmailDerivador").prop("disabled", false);
        $("#txtEncargadoDerivador").prop("disabled", false);
        $("#checkBoxActivo").prop("disabled", false);
        $("#divBtnAgregar").hide();
        $("#divBtnUpdate").show();
    	$('#btnLimpiar').addClass('disabled');
        desactivarEdit();
    }
});

function limpiarFormulario(){
    $(".switch-content").removeClass("disabled inactivo");
    $("#txtIdDerivador").val("");
    $("#txtCodigoDerivador").prop("disabled", false);
    $("#txtCodigoDerivador").val("");
    $("#txtNombreDerivador").prop("disabled", false);
    $("#txtNombreDerivador").val("");
    $("#numOrdenDerivador").prop("disabled", false);
    $("#numOrdenDerivador").val("");
    $("#txtHostDerivador").prop("disabled", false);
    $("#txtHostDerivador").val("");
    $("#txtHoraDerivador").prop("disabled", false);
    $("#txtHoraDerivador").val("");
    $("#txtFonoDerivador").prop("disabled", false);
    $("#txtFonoDerivador").val("");
    $("#txtDireccionDerivador").prop("disabled", false);
    $("#txtDireccionDerivador").val("");
    $("#txtEmailDerivador").prop("disabled", false);
    $("#txtEmailDerivador").val("");
    $("#txtEncargadoDerivador").prop("disabled", false);
    $("#txtEncargadoDerivador").val("");
    $("#checkBoxActivo").prop("disabled", false);
    $("#checkBoxActivo").prop("checked", "checked");
    $("#lblEstado").text("Activo");
    $("#btnAgregar").show();
    $("#btnAgregar").prop("disabled", false);
    $("#btnEditarDerivador").prop("disabled", true);
    $("#iEditDerivador").addClass("text-dark-50");
    $("#iEditDerivador").removeClass("text-primary");
    $("#btnUpdate").hide();
    desactivarEdit();
}

/*
$("#formRegistro").submit(function(){
    let codigo = $("#txtCodigoDerivador").val();
    let nombre = $("#txtNombreDerivador").val();
    if (nombre.trim() === "" || nombre.trim() === ""){
        if (codigo.trim() === ""){
            $("#txtCodigoDerivador").addClass('is-invalid');
            $.notify({ message: "Código no debe estar vacío" }, { type: "danger" });
        }
        if (nombre.trim() === ""){
            $("#txtNombreDerivador").addClass('is-invalid');
            $.notify({ message: "Nombre no debe estar vacío" }, { type: "danger" });
        }
        return false;
    } else {
        return true;
    }
    
});
*/
function validarFormDerivador(){
    let codigo = $("#txtCodigoDerivador").val();
    let nombre = $("#txtNombreDerivador").val();
    if (nombre.trim() === "" || nombre.trim() === ""){
        if (codigo.trim() === ""){
            $("#txtCodigoDerivador").addClass('is-invalid');
            $.notify({ message: "Código no debe estar vacío" }, { type: "danger" });
        }
        if (nombre.trim() === ""){
            $("#txtNombreDerivador").addClass('is-invalid');
            $.notify({ message: "Nombre no debe estar vacío" }, { type: "danger" });
        }
        return false;
    } else {
        return true;
    }
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
    var idCausa = $row.querySelector('td.idDerivador').textContent;
    buscarPorId(idCausa,false);
}

$("#btnAgregar").click(function() {
	insertDerivador();
});
function insertDerivador() {
	if(!validarFormDerivador())
		return;
		
    let data = JSON.stringify({
		//ccdsIdcentrodesalud: idCentroSaludSelec,
        cderivCodigo: $("#txtCodigoDerivador").val().toUpperCase(),
        cderivDescripcion: $("#txtNombreDerivador").val().toUpperCase(),
        cderivSort: $("#numOrdenDerivador").val(),
        cderivHost: $("#txtHostDerivador").val().toUpperCase(),
        cderivHorarioatencion: $("#txtHoraDerivador").val(),
        cderivTelefono: $("#txtFonoDerivador").val(),
        cderivDireccion: $("#txtDireccionDerivador").val().toUpperCase(),
        cderivEmail: $("#txtEmailDerivador").val(),
        cderivNombreencargado: $("#txtEncargadoDerivador").val().toUpperCase(),
        cderivActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/derivadores/insert",
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
            handlerMessageError("Error al cargar centros de salud");
        }
    });
}
$("#btUpdate").click(function() {
	updateDerivador();
});
function updateDerivador() {
	if(!validarFormDerivador())
		return;
		
    let data = JSON.stringify({
		cderivIdderivador: idDerivadorParaUpdate,
        cderivCodigo: $("#txtCodigoDerivador").val().toUpperCase(),
        cderivDescripcion: $("#txtNombreDerivador").val().toUpperCase(),
        cderivSort: $("#numOrdenDerivador").val(),
        cderivHost: $("#txtHostDerivador").val().toUpperCase(),
        cderivHorarioatencion: $("#txtHoraDerivador").val(),
        cderivTelefono: $("#txtFonoDerivador").val(),
        cderivDireccion: $("#txtDireccionDerivador").val().toUpperCase(),
        cderivEmail: $("#txtEmailDerivador").val(),
        cderivNombreencargado: $("#txtEncargadoDerivador").val().toUpperCase(),
        cderivActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/derivadores/update",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
	console.log(data)
			filtrarLike();
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
