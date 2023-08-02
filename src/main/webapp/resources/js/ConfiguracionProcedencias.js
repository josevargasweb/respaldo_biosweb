//var getctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(document).ready(function(){
    $(".ocultar").hide();
    filtrarLike();
    cargarConvenios();
    cargarCentros();
    desactivarEdit();
    let messageSuccess = $("#messageSuccess").val();
    let messageError = $("#messageError").val();
    
    if (typeof messageSuccess !== 'undefined' && messageSuccess!==null){
        handlerMessageOk(messageSuccess);
    }
    if (typeof messageError !== 'undefined' && messageError!==null){
        handlerMessageError(messageError);
    }
    $('.selectpicker').selectpicker('refresh')
});
new ImaskInputNumber('numOrdenProcedencia', false ,1, 99999);
let idProcedenciaParaUpdate = 0;
function filtrarLike() {
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        nombre: $("#txtNombreFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    $.ajax({
        type: "post",
        url: getctx + "/api/procedencias/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (json) {
            let cont = 0;
            if (json.status === 200){
                let dato = json.dato;
                $("#tbodyFiltro").empty();
                if (dato.length > 0) {
                    dato.forEach(function (procedencia) {
                        cont++;
                        $("#tbodyFiltro").append("<tr class='pointer' onclick='selectProcedencia(this)' >\
                                                    <th class='row mx-auto'>" + cont + "</th>\
                                                    <td class=''>" + procedencia.cpCodigo + "</td>\
                                                    <td class=''>" + procedencia.cpDescripcion + "</td>\
                                                    <td class='idProcedencia' style='display:none'>" + procedencia.cpIdprocedencia + "</td>\
                                                </tr>");

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
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error.");
        }
    });
}

$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtNombreFiltro").prop("disabled", true);
    } else {
        $("#txtNombreFiltro").prop("disabled", false);
    }
    filtrarLike();
});

$("#txtNombreFiltro").keyup(function () {
    if ($("#txtNombreFiltro").val().length > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
    }
    filtrarLike();
});

$("#chkMostrarActivos").change(function(){
    filtrarLike();
});

function selectProcedencia(a){
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idProcedencia = $row.find(".idProcedencia").text();
    buscarPorId(idProcedencia);
    activarEdit();
}

function activarEdit() {
    $("#iEditProcedencia").removeClass("text-dark-50");
    $("#iEditProcedencia").addClass("text-primary");
}

function buscarPorId(idProcedencia,collapse = true){
    $.ajax({
        type: "POST",
        data: "filtroId=1&idProcedencia=" + idProcedencia,
        async: false,
        datatype: "json",
        success: function (json) {
            var dato = JSON.parse(json);
            if (!dato.hasOwnProperty('error')){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                let procedencia = dato.procedencia[0];
                $("#btnAgregar").prop("disabled", true);
                $("#txtCodigoProcedencia").removeClass('is-invalid');
                $("#txtCodigoProcedencia").prop("disabled", true);
                $("#txtNombreProcedencia").removeClass('is-invalid');
                $("#txtNombreProcedencia").prop("disabled", true);
                $("#numOrdenProcedencia").prop("disabled", true);
                $("#txtEmailProcedencia").prop("disabled", true);
                $("#selectCentro").prop("disabled", true);
                $("#selectConv").prop("disabled", true);
                $("#txtGrupoProcedencia").prop("disabled", true);
                $("#txtHost1").prop("disabled", true);
                $("#txtHost2").prop("disabled", true);
                $("#txtHost3").prop("disabled", true);
                $("#txtInformeProcedencia").prop("disabled", true);
                $("#checkBoxActivo").prop("disabled", true);
                $("#checkBoxRestringida").prop("disabled", true);
                $("#checkBoxTomaMuestraAuto").prop("disabled", true);
                $("#checkBoxMembrete").prop("disabled", true);
                idProcedenciaParaUpdate = procedencia.IdProcedencia;
                $("#txtIdProcedencia").val(procedencia.IdProcedencia);
                $("#txtCodigoProcedencia").val(procedencia.Codigo);
                $("#txtNombreProcedencia").val(procedencia.Descripcion);
                $("#numOrdenProcedencia").val(procedencia.Orden);
                $("#txtEmailProcedencia").val(procedencia.Email);
                $("#selectCentro").val(procedencia.CodCentro);
                $("#selectConv").val(procedencia.Convenio);
                $("#txtGrupoProcedencia").val(procedencia.Grupo);
                $("#txtHost1").val(procedencia.Host);
                $("#txtHost2").val(procedencia.Host2);
                $("#txtHost3").val(procedencia.Host3);
                $("#btnLimpiarModal").hide();
                $("#txtInformeProcedencia").val(procedencia.TextoInforme);
                $(".switch-content").addClass("disabled");
                procedencia.Activo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                if (procedencia.Activo === "S") {
                    $("#checkBoxActivo").prop("checked", true);
                    $("#lblEstado").text("Activo");
                } else {
                    $("#checkBoxActivo").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                }
                if (procedencia.Restringida === "S") {
                    $("#checkBoxRestringida").prop("checked", true);
                } else {
                    $("#checkBoxRestringida").prop("checked", false);
                }
                if (procedencia.TomaMuestraAutomatica === "S") {
                    $("#checkBoxTomaMuestraAuto").prop("checked", true);
                } else {
                    $("#checkBoxTomaMuestraAuto").prop("checked", false);
                }
                if (procedencia.Membrete === "S") {
                    $("#checkBoxMembrete").prop("checked", true);
                } else {
                    $("#checkBoxMembrete").prop("checked", false);
                }
                $('.selectpicker').selectpicker('refresh');
            } else {
                handlerMessageError(dato.error);
            }
            $('.selectpicker').selectpicker('refresh')
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error.");
        }
    });
}

$("#nuevaProcedencia").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarProcedencia").click(function () {
    $(".collapse").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionExample8").offset().top
    }, 700);
});

$("#spanCheck").click(function () {
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
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

$("#btnEditarProcedencia").click(function() {
    if (
        localStorage.getItem("botonEditar") === "activo"
    ) {
        $("#txtCodigoProcedencia").prop("disabled", false);
        $("#txtNombreProcedencia").prop("disabled", false);
        $("#numOrdenProcedencia").prop("disabled", false);
        $("#txtEmailProcedencia").prop("disabled", false);
        $(".switch-content").removeClass("disabled");
        $("#selectCentro").prop("disabled", false);
        $("#selectConv").prop("disabled", false);
        $("#txtGrupoProcedencia").prop("disabled", false);
        $("#txtHost1").prop("disabled", false);
        $("#txtHost2").prop("disabled", false);
        $("#txtHost3").prop("disabled", false);
        $("#txtInformeProcedencia").prop("disabled", false);
        $("#checkBoxActivo").prop("disabled", false);
        $("#checkBoxRestringida").prop("disabled", false);
        $("#checkBoxTomaMuestraAuto").prop("disabled", false);
        $("#checkBoxMembrete").prop("disabled", false);
        $('.selectpicker').selectpicker('refresh');
        $("#divBtnAgregar").hide();
        $("#divBtnUpdate").show();
        $("#btnLimpiarModal").show();
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
    }
});

$("#btnCancelUpdate").click(function() {	
	console.log(  $("#txtIdProcedencia").val());
    $(".formProc").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#divBtnUpdate").hide();
    $("#divBtnAgregar").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
    buscarPorId( $("#txtIdProcedencia").val())
});

function limpiarFormulario(){
    $(".switch-content").removeClass("disabled inactivo");
    $("#txtIdProcedencia").val("");
    $("#txtCodigoProcedencia").val("");
    $("#txtNombreProcedencia").val("");
    $("#numOrdenProcedencia").val("");
    $("#txtEmailProcedencia").val("");
    $("#selectCentro").val("0");
    $("#selectConv").val("0");
    $("#txtGrupoProcedencia").val("");
    $("#txtHost1").val("");
    $("#txtHost2").val("");
    $("#txtHost3").val("");
    $("#txtInformeProcedencia").val("");
    
    $("#checkBoxActivo").prop("checked", true);
    $("#checkBoxRestringida").prop("checked", false);
    $("#checkBoxTomaMuestraAuto").prop("checked", false);
    $("#checkBoxMembrete").prop("checked", false);
    
    $("#txtCodigoProcedencia").prop("disabled", false);
    $("#txtNombreProcedencia").prop("disabled", false);
    $("#numOrdenProcedencia").prop("disabled", false);
    $("#txtEmailProcedencia").prop("disabled", false);
    $("#selectCentro").prop("disabled", false);
    $("#selectConv").prop("disabled", false);
    $("#txtGrupoProcedencia").prop("disabled", false);
    $("#txtHost1").prop("disabled", false);
    $("#txtHost2").prop("disabled", false);
    $("#txtHost3").prop("disabled", false);
    $("#txtInformeProcedencia").prop("disabled", false);
    $("#checkBoxActivo").prop("disabled", false);
    $("#lblEstado").text("Activo");
    $("#checkBoxRestringida").prop("disabled", false);
    $("#checkBoxTomaMuestraAuto").prop("disabled", false);
    $("#checkBoxMembrete").prop("disabled", false);
    $('.selectpicker').selectpicker('refresh');
    
    $("#btnAgregar").show();
    $("#btnAgregar").prop("disabled", false);
    $("#btnUpdate").hide();
    desactivarEdit();
    $('.selectpicker').selectpicker('refresh')
}

$("#btnLimpiarModal").click(function () {
    $("#txtHost1").val("");
    $("#txtHost2").val("");
    $("#txtHost3").val("");
});

$("#txtCodigoProcedencia").keyup(function () {
    if ($("#txtCodigoProcedencia").val().trim().length > 0) {
        $("#txtCodigoProcedencia").removeClass('is-invalid');
    }
});

$("#txtNombreProcedencia").keyup(function () {
    if ($("#txtNombreProcedencia").val().trim().length > 0) {
        $("#txtNombreProcedencia").removeClass('is-invalid');
    }
});

//insertar todos los campos que obligatorios
/*
$("#formRegistroProcedencia").submit(function(){
    let codigo = $("#txtCodigoProcedencia").val();
    let nombre = $("#txtNombreProcedencia").val();
    if (nombre.trim() === "" || nombre.trim() === ""){
        if (codigo.trim() === ""){
            $("#txtCodigoProcedencia").addClass('is-invalid');
            $.notify({ message: "Código no debe estar vacío" }, { type: "danger" });
        }
        if (nombre.trim() === ""){
            $("#txtNombreProcedencia").addClass('is-invalid');
            $.notify({ message: "Nombre no debe estar vacío" }, { type: "danger" });
        }
        return false;
    } else {
        return true;
    }
    
});*/
function validarProcedencia(){
	    let codigo = $("#txtCodigoProcedencia").val();
    let nombre = $("#txtNombreProcedencia").val();
    if (nombre.trim() === "" || nombre.trim() === ""){
        if (codigo.trim() === ""){
            $("#txtCodigoProcedencia").addClass('is-invalid');
            $.notify({ message: "Código no debe estar vacío" }, { type: "danger" });
        }
        if (nombre.trim() === ""){
            $("#txtNombreProcedencia").addClass('is-invalid');
            $.notify({ message: "Nombre no debe estar vacío" }, { type: "danger" });
        }
        return false;
    } else {
        return true;
    }
}

function cargarConvenios(){
    $.ajax({
        type: "GET",
        url: getctx + "/api/instituciones/convenios/list",
        async: false,
        datatype: "json",
        success: function(data){
            $('#selectConv').empty();
            for (let convenio of data){
                $('#selectConv').append($('<option>', {
                    value: convenio.ccIdconvenio,
                    text: convenio.ccDescripcion
                }));
            }
        },
        error: function(msg){
            console.log(msg.responseText);
        }
    });
}

function cargarCentros(){
    $.ajax({
        type: "GET",
        url: getctx + "/api/instituciones/centrosdesalud/list",
        async: false,
        datatype: "json",
        success: function(data){
            $('#selectCentro').empty();
            for (let centro of data){
                $('#selectCentro').append($('<option>', {
                    value: centro.ccdsIdcentrodesalud,
                    text: centro.ccdsDescripcion
                }));
            }

            $('.selectpicker').selectpicker('refresh')
        },
        error: function(msg){
            console.log(msg.responseText);
        }
    });
}


function activarEdit() {
    $("#iEditProcedencia").removeClass("text-dark-50");
    $("#iEditProcedencia").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
    $('.selectpicker').selectpicker('refresh')
}

function desactivarEdit() {
    $("#iEditProcedencia").removeClass("text-blue");
    $("#iEditProcedencia").addClass("ttext-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
    $('.selectpicker').selectpicker('refresh')
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
    var idCausa = $row.querySelector('td.idProcedencia').textContent;
    buscarPorId(idCausa,false);
    $('.selectpicker').selectpicker('refresh')
}

$("#btnAgregar").click(function() {
	insertProcedencia();
});
function insertProcedencia() {
	if(!validarProcedencia())
		return;
		
    let data = JSON.stringify({
		//ccdsIdcentrodesalud: idCentroSaludSelec,
        cpCodigo: $("#txtCodigoProcedencia").val().toUpperCase(),
     	  cpDesc: $("#txtNombreProcedencia").val().toUpperCase(),
        cpSort: $("#numOrdenProcedencia").val(),
        cpEmail: $("#txtEmailProcedencia").val().toUpperCase(),
        cfgCentroDeSalud: $("#selectCentro").val(),
        cfgConvenios: $("#selectConv").val(),
        cpTextoInforme: $("#txtInformeProcedencia").val().toUpperCase(),
        cpCodigoGrupo: $("#txtGrupoProcedencia").val(),
        cpProcedenciarestringida: $("#checkBoxRestringida").is(":checked") ? "S" : "N",
        cpTomamuestraautomatica: $("#checkBoxTomaMuestraAuto").is(":checked") ? "S" : "N",
        cpMembrete: $("#checkBoxMembrete").is(":checked") ? "S" : "N",
        cpActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N",
         cpHost: $("#txtHost1").val(),
        cpHost2: $("#txtHost2").val(),
        cpHost3: $("#txtHost3").val()
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/procedencia/insert",
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
$("#btnUpdate").click(function() {
	updateProcedencia();
    $('.selectpicker').selectpicker('refresh');
});
function updateProcedencia() {
	if(!validarProcedencia())
		return;
		
    let data = JSON.stringify({
		cpidProcedencia: idProcedenciaParaUpdate,
        cpCodigo: $("#txtCodigoProcedencia").val().toUpperCase(),
        cpDesc: $("#txtNombreProcedencia").val().toUpperCase(),
        cpSort: $("#numOrdenProcedencia").val(),
        cpEmail: $("#txtEmailProcedencia").val().toUpperCase(),
        cfgCentroDeSalud: $("#selectCentro").val(),
        cfgConvenios: $("#selectConv").val(),
        cpTextoInforme: $("#txtInformeProcedencia").val().toUpperCase(),
        cpCodigoGrupo: $("#txtGrupoProcedencia").val(),
        cpProcedenciarestringida: $("#checkBoxRestringida").is(":checked") ? "S" : "N",
        cpTomamuestraautomatica: $("#checkBoxTomaMuestraAuto").is(":checked") ? "S" : "N",
        cpMembrete: $("#checkBoxMembrete").is(":checked") ? "S" : "N",
        cpActivo: $("#checkBoxActivo").is(":checked") ? "S" : "N",
        cpHost: $("#txtHost1").val(),
        cpHost2: $("#txtHost2").val(),
        cpHost3: $("#txtHost3").val()
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/procedencia/update",
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
            handlerMessageError("Error al cargar centros de salud");
        }
    });
}
