$(document).ready(function () {
    $(".ocultar").hide();
    $('#colorpicker').farbtastic('#color');
    filtrarLike();
    desactivarEdit();
    let messageSuccess = $("#messageSuccess").val();
    let messageError = $("#messageError").val();
    if (typeof messageSuccess !== 'undefined' && messageSuccess!==null && messageSuccess != ""){
        handlerMessageOk(messageSuccess);
    }
    if (typeof messageError !== 'undefined' &&messageError!==null  && messageError != ""){
        handlerMessageError(messageError);
    }

});

$("#txtNombreMetodoFiltro").keyup(function (){
    //$("#txtNombreMetodo").removeClass("is-invalid"); 
    filtrarLike($("#txtNombreMetodoFiltro").val());
});

$("#txtNombreMetodo").keyup(function (){
    $("#txtNombreMetodo").removeClass("is-invalid"); 
});

$("#chkMostrarActivos").change(function(){
    filtrarLike();
});

$("#btnLimpiarFiltro").click(function (){
    $("#txtNombreMetodoFiltro").val("");
    $("#chkMostrarActivos").prop("checked", false);
    filtrarLike();
});

$("#btnLimpiar").click(function () {
	$("#collapseHeader").collapse('show');
    limpiarForm();
});

function limpiarForm(){
    $("#txtIdMetodo").val("");
    $("#txtNombreMetodo").prop("disabled", false);
    $("#txtNombreMetodo").val("");
    $("#lblEstado").text("Activo");
    $("#checkBoxLever").prop("disabled", false);
    $("#checkBoxLever").prop("checked", true);
    $("#btnAgregarMetodo").prop("disabled",false);
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    desactivarEdit();
    $('.selectpicker').selectpicker('refresh');
}

$("#btnEditar").click(function () {
    if (localStorage.getItem("botonEditar") === "activo"){
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
        $("#txtNombreMetodo").prop("disabled", false);
        $("#checkBoxLever").prop("disabled", false);
        $('#btnLimpiar').addClass('disabled');
        $(".switch-content").removeClass("disabled");
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');
    }
});

$("#btnCancelMetodo").click(function() {
    $("#txtNombreMetodo").prop("disabled", true);
    $("#checkBoxLever").prop("disabled", true);
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $(".switch-content").addClass("disabled");
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
});

$("#nuevoMetodo").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarForm();
});

$("#buscarMetodo").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionMetodos").offset().top
    }, 700);
});

$("#circuloAgregarMetodo").hover(
        function () {
            $("#iAgregarMetodo").removeClass("text-blue");
            $("#iAgregarMetodo").addClass("text-white");
        },
        function () {
            $("#iAgregarMetodo").removeClass("text-white");
            $("#iAgregarMetodo").addClass("text-blue");
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

$("#circuloBuscarMetodo").hover(
        function () {
            $("#iBuscarMetodo").removeClass("text-blue");
            $("#iBuscarMetodo").addClass("text-white");
        },
        function () {
            $("#iBuscarMetodo").removeClass("text-white");
            $("#iBuscarMetodo").addClass("text-blue");
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
    let nombre = $("#txtNombreMetodoFiltro").val();
    let activo = $("#chkMostrarActivos").is(":checked") ? "S" : "N";
    $.ajax({
        type: "post",
        data: "filtro=1&nombre=" + nombre + "&activo=" + activo,
        datatype: "json",
        success: function (mensaje) {
            let cont = 0;
            let dato = JSON.parse(mensaje);
            if (!dato.hasOwnProperty('error')){
                $("#tbodyFiltro").empty();
                if (dato.metodo.length > 0) {
                    dato.metodo.forEach(function (metodo) {
                        cont++;
                        $("#tbodyFiltro").append("<tr class='pointer' onclick='selectMetodo(this)' ><th class='row mx-auto'>" + cont + "</th><td class='nombreMetodo'>" + metodo.NombreMetodo + "</td><td class='idMetodo' style='display:none'>" + metodo.IdMetodo + "</td><td class='colorEstado' style='display:none'>" + metodo.ColorMetodo + "</td></tr>");
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
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error.");
        }
    });
}
let idMetodoParaUpdate;
function selectMetodo(a,collapse = true) {
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var nombreMetodo = $row.find(".nombreMetodo").text();
    var idMetodo = $row.find(".idMetodo").text(); // Find the text
    var estadoMetodo = $row.find(".estadoMetodo").text();
    var colorMetodo = $row.find(".colorEstado").text();

    $("#txtNombreMetodo").val(nombreMetodo);
    $(".switch-content").addClass("disabled");
    estadoMetodo=== "S" ? $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
    if (estadoMetodo === "S") {
        $("#checkBoxLever").prop("checked", true);
        $("#lblEstado").text("Activo");
    } else {
        $("#checkBoxLever").prop("checked", false);
        $("#lblEstado").text("Inactivo");
    }
    idMetodoParaUpdate = idMetodo;
    $("#txtIdMetodo").val(idMetodo);
    $('#color').val(colorMetodo);
    $('#colorpicker').farbtastic('#color');
    $("#txtNombreMetodo").prop("disabled", true);
    $("#btnAgregarMetodo").prop("disabled", true);
    $("#checkBoxLever").prop("disabled", true);
    if(collapse){
        $("#collapseHeader").collapse('hide');
    }
    activarEdit();
}

function activarEdit() {
    $("#iEditMetodo").removeClass("text-dark-50");
    $("#iEditMetodo").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditMetodo").removeClass("text-blue");
    $("#iEditMetodo").addClass("ttext-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
}

$("#circuloEditarMetodo").hover(
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarMetodo").addClass("bg-hover-blue");
                $("#iEditMetodo").removeClass("text-blue");
                $("#iEditMetodo").addClass("text-white");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#iEditMetodo").removeClass("text-white");
                $("#iEditMetodo").addClass("text-blue");
            }
        }
);
/*
$('form').submit(function () {
    if ($("#txtNombreMetodo").val().length > 0) {
        return true;
    } else {
        handlerMessageError("Se requiere llenar el campo Nombre")
        $("#txtNombreMetodo").addClass("is-invalid");
        return false;
    }
});*/
function ValidarFormMetodo(){
	    if ($("#txtNombreMetodo").val().length > 0) {
        return true;
    } else {
        handlerMessageError("Se requiere llenar el campo Nombre")
        $("#txtNombreMetodo").addClass("is-invalid");
        return false;
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
    selectMetodo($row,false);
}

$("#btnAgregarMetodo").click(function() {
	insertMetodo();
});
function insertMetodo() {
	if(!ValidarFormMetodo())
		return;
		
    let data = JSON.stringify({
		//cmetIdmetodo: idMuestraParaUpdate,
        cmetDescripcion: $("#txtNombreMetodo").val().toUpperCase(),
        cmetActivo: $("#checkBoxLever").is(":checked") ? "S" : "N",
        cmetColor:$('#color').val(),
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/metodos/insert",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
			filtrarLike();
			limpiarForm();
			$('#btnLimpiar').removeClass('disabled');
			if(data.status == 200)
				handlerMessageOk(data.message);
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al cargar Metodos");
        }
    });
}
$("#btnUpdateMetodo").click(function() {
	updateMetodo();
});
function updateMetodo() {
	if(!ValidarFormMetodo())
		return;
		
    let data = JSON.stringify({
		cmetIdmetodo: idMetodoParaUpdate,
        cmetDescripcion: $("#txtNombreMetodo").val().toUpperCase(),
        cmetActivo: $("#checkBoxLever").is(":checked") ? "S" : "N",
        cmetColor:$('#color').val(),
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/metodos/update",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
			filtrarLike();
			$('#btnLimpiar').removeClass('disabled');
			if(data.status == 200)
				handlerMessageOk(data.message);
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al cargar Metodos");
        }
    });
}
