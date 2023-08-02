$(document).ready(function () {
    $(".ocultar").hide();
//     $('#colorpicker').farbtastic('#txtColor');
    filtrarLike();
    desactivarEdit();
    let messageSuccess = $("#messageSuccess").val();
    let messageError = $("#messageError").val();
    if (typeof messageSuccess !== 'undefined' && messageSuccess!==null){
        handlerMessageOk(messageSuccess);
    }
    if (typeof messageError !== 'undefined' &&messageError!==null){
        handlerMessageError(messageError);
    }

    $(".selectpicker").selectpicker({
        noneSelectedText: "",
    });
});
new ImaskInputNumber('numOrden', false, 1, 9999);

$("#txtMuestraFiltro").keyup(function () {
    if ($("#txtMuestraFiltro").val().length > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
        $("#selectGrupoMuestra").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
        $("#selectGrupoMuestra").prop("disabled", false);
    }
    filtrarLike();
});

$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtMuestraFiltro").prop("disabled", true);
        $("#selectGrupoMuestra").prop("disabled", true);
    } else {
        $("#txtMuestraFiltro").prop("disabled", false);
        $("#selectGrupoMuestra").prop("disabled", false);
    }
    filtrarLike();
});

$("#selectGrupoMuestra").on('change', function () {
    if ($("#selectGrupoMuestra").val() > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
        $("#txtMuestraFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
        $("#txtMuestraFiltro").prop("disabled", false);
    }
    filtrarLike();
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtMuestraFiltro").val("");
    $("#txtMuestraFiltro").prop("disabled", false);
    $("#selectGrupoMuestra").val(0);
    $("#selectGrupoMuestra").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked",false);
    filtrarLike();
});

$("#chkMostrarActivos").change(function(){
    filtrarLike();
});

$("#nuevaMuestra").click(function () {
    $("#collapseHeader").collapse('hide');
    
    //$(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarMuestra").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#headingMuestras").offset().top
    }, 700);
    //$("#divAgregarBtn").show();
    //$("#divActualizarBtn").hide();
    filtrarLike();
});

$("#btnLimpiar").click(function () {
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
        $("#checkBoxActivo").val("S")
        
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
         $("#checkBoxActivo").val("N")
    }
});

$("#checkBoxPuntodeCurva").change(function(){
    if (this.checked) {
        $("#activaCurvasMinutos").show();
    } else {
        $("#activaCurvasMinutos").hide();
    }
});

$("#btnEditarMuestra").click(function() {
    if (localStorage.getItem("botonEditar") === "activo"){
        $("#txtCodigo").prop("disabled", false);
        $("#txtMuestra").prop("disabled", false);
        $("#txtDescripcion").prop("disabled", false);
        $("#txtDescripcionAbrev").prop("disabled", false);
        $("#cmueGrupo").prop("disabled", false);
        $("#numOrden").prop("disabled", false);
        $("#txtHost").prop("disabled", false);
        $("#txtPrefijo").prop("disabled", false);
        $("#txtLISBac").prop("disabled", false);
        $("#checkBoxActivo").prop("disabled", false);
        $("#checkBoxMicrobiología").prop("disabled", false);
        $("#checkBoxPuntodeCurva").prop("disabled", false);
        $(".switch-content").removeClass("disabled");
        $("#color").prop("disabled", false);
        if ($("#checkBoxPuntodeCurva").is(":checked")) {
            $("#txtCurvasMinutos").prop("disabled", false);
        }
        $('.selectpicker').selectpicker('refresh');
        //$("#btnAgregar").prop("disabled", true);
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
        $("#btnElegirPrefijo").show();
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
    }
});

$("#btnCancelUpdate").click(function() {
    $(".formMuestras").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#color").prop("disabled", false);
    if ($("#checkBoxPuntodeCurva").is(":checked")) {
        $("#txtCurvasMinutos").prop("disabled", false);
    }
    $('.selectpicker').selectpicker('refresh');
    $("#divAgregarBtn").show();
    $("#divActualizarBtn").hide();
    $("#btnElegirPrefijo").hide();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});

$("#txtBuscaPrefijo").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tablePrefijos tr ").filter(function() {
       $(this).toggle($(this).find("td:first").text().toLowerCase().indexOf(value) > -1)
    });
    
});

$("#btnVerExamenes").click(function() {
    let idMuestra = 0;
    let prefijo = '00';
    $('#selectMuestraEx').selectpicker('val', '');
    $("#txtBuscaPrefijoEx").val('00');
    filtrarExamenes(idMuestra, prefijo);
});

function filtrarLike(){
    let filtros = JSON.stringify({
        prefijo: $("#txtCodigoFiltro").val(),
        descripcion: $("#txtMuestraFiltro").val(),
        idGrupo: $("#selectGrupoMuestra").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        method: "POST",
        url: getctx + "/api/muestras/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        dataType: "json",
        success: function (data) {
            let cont = 0;
            if (data.status === 200){
                let dato = data.dato;
                $("#tbodyFiltro").empty();
                if (dato.length > 0) {
                    dato.forEach(function (muestra) {
                        cont++;
                        $("#tbodyFiltro").append(`<tr class="pointer" onclick="selectTipoMuestra(this)" data-id-muestra=${muestra.cmueIdtipomuestra}>
                                                    <th class="row mx-auto">${cont}</th>
                                                    <td class=''>${muestra.cmuePrefijotipomuestra} </td>
                                                    <td class=''>${muestra.cmueDescripcionabreviada} </td>
                                                  </tr>`);
                    });
                    $("#lblErrorFiltro").hide();
                    $("#lblTotalFiltro").show();
                    $("#contRegistros").text(cont);
                } else {
                    $("#lblTotalFiltro").hide();
                    $("#lblErrorFiltro").show();
                }
            } else {
                handlerMessageError("Error: "+data.message);
            }
        },
        error: function (msg) {
            handlerMessageError("Ha ocurrido un error.");
            console.log(msg.responseText);
        }
    });
    
    
}

function seleccionarPrefijo(prefijo){
    $("#txtPrefijo").val(prefijo);
    $('#modalPrefijos').modal('toggle');
}

function selectTipoMuestra(a){
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    let idTipoMuestra = $row.attr("data-id-muestra");
    filtrarbyId(idTipoMuestra);
    activarEdit();
    $("#btnAgregar").prop('disabled', true);

}

$("#circuloEditarMuestra").hover(
    function () {
        if (localStorage.getItem("botonEditar") === "activo") {
            $("#circuloEditarMuestra").addClass("bg-hover-blue");
            $("#iEditMuestra").removeClass("text-blue");
            $("#iEditMuestra").addClass("text-white");
        }
    },
    function () {
        if (localStorage.getItem("botonEditar") === "activo") {
            $("#iEditMuestra").removeClass("text-white");
            $("#iEditMuestra").addClass("text-blue");
        }
    }
);

function activarEdit() {
    $("#iEditMuestra").removeClass("text-dark-50");
    $("#iEditMuestra").addClass("text-blue");
    
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditMuestra").removeClass("text-blue");
    $("#iEditMuestra").addClass("text-dark-50");
    
    localStorage.setItem("botonEditar", "inactivo");
}

function limpiarFormulario(){
    $("#txtIdTipoMuestra").val("");
    $("#txtMuestra").val("");
    $("#txtMuestra").prop("disabled", false);
    $("#txtDescripcion").val("");
    $("#txtDescripcion").prop("disabled", false);
    $("#txtDescripcionAbrev").val("");
    $("#txtDescripcionAbrev").prop("disabled", false);
    $("#txtDescripcionAbrev").removeClass('is-invalid');
    $("#cmueGrupo").val(0);
    $("#cmueGrupo").prop("disabled", false);
    $("#numOrden").val(1);
    $("#numOrden").prop("disabled", false);
    let prefijoDefault = $("#prefijoDefault").val();
    $("#txtPrefijo").val(prefijoDefault);
    $("#txtPrefijo").prop("disabled", false);
    $("#txtHost").val("");
    $("#txtHost").prop("disabled", false);
    $("#txtLISBac").val("");
    $("#txtLISBac").prop("disabled", false);
    $("#color").val("#123456");
    $.farbtastic("#colorpicker").setColor("#123456");
    $("#color").prop("disabled", false);
    $("#txtCurvasMinutos").val("");
    $("#txtCurvasMinutos").prop("disabled", false);
    $("#txtCurvasMinutos").removeClass('is-invalid');
    $("#lblEstado").text("Activo");
    $("#checkBoxActivo").prop("checked", true);
    $("#checkBoxActivo").prop("disabled", false);
    $("#checkBoxMicrobiología").prop("checked", false);
    $("#checkBoxMicrobiología").prop("disabled", false);
    $("#checkBoxPuntodeCurva").prop("checked", false);
    $("#checkBoxPuntodeCurva").prop("disabled", false);
    $(".switch-content").removeClass("disabled inactivo");
    
    $("#btnAgregar").prop("disabled", false);
    //$("#btnVerExamenes").hide();
    $("#btnElegirPrefijo").show();
    $("#divAgregarBtn").show();
    $("#divActualizarBtn").hide();
    $('.selectpicker').selectpicker('refresh');
    desactivarEdit();
}
let idMuestraParaUpdate = 0;
function filtrarbyId(idTipoMuestra,collapse = true) {
    $.ajax({
        type: "post",
        data: "filtroId=1&idTipoMuestra=" + idTipoMuestra,
        datatype: "json",
        success: function (mensaje) {
            let dato = JSON.parse(mensaje);
            if (!dato.hasOwnProperty('error')){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                let muestra = dato.muestra[0];
                $("#lblErrorFiltro").hide();
                idMuestraParaUpdate  = muestra.id;
                $("#txtIdTipoMuestra").val(muestra.id);
                $("#txtCodigo").val(muestra.prefijo);
                $("#txtCodigo").prop("disabled", true);
                $("#txtMuestra").prop("disabled", true);
                $("#txtDescripcion").val(muestra.descripcion);
                $("#txtDescripcion").prop("disabled", true);
                $("#txtDescripcionAbrev").val(muestra.descripcionabr);
                $("#txtDescripcionAbrev").prop("disabled", true);
                $("#txtDescripcionAbrev").removeClass('is-invalid');
                $("#cmueGrupo").val(muestra.grupo);
                $("#cmueGrupo").prop("disabled", true);
                $("#numOrden").val(muestra.orden);
                $("#numOrden").prop("disabled", true);
                $("#txtPrefijo").val(muestra.prefijo);
                $("#txtPrefijo").prop("disabled", true);
                $("#txtHost").val(muestra.host);
                $("#txtHost").prop("disabled", true);
                $("#txtLISBac").val(muestra.hostmicro);
                $("#txtLISBac").prop("disabled", true);
                $("#color").val(muestra.color);
                $("#color").prop("disabled", true);
                $.farbtastic("#colorpicker").setColor(muestra.color);

                $("#checkBoxActivo").prop("disabled", true);
                $(".switch-content").addClass("disabled")
                if (muestra.activo == "S") {
                    $("#checkBoxActivo").prop("checked", true);
                    $(".switch-content").removeClass("inactivo")
                    $("#lblEstado").text("Activo");
                } else {
                    $(".switch-content").addClass("inactivo")
                    $("#checkBoxActivo").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                }
                $("#checkBoxMicrobiología").prop("disabled", true);
                if (muestra.microbiologia == 'S') {
                    $("#checkBoxMicrobiología").prop("checked", true);
                } else {
                    $("#checkBoxMicrobiología").prop("checked", false);
                }
                $("#checkBoxPuntodeCurva").prop("disabled", true);
                if (muestra.tipocurva == 'S') {        
                    $("#activaCurvasMinutos").show();
                    $("#checkBoxPuntodeCurva").prop("checked", true);
                    $("#txtCurvasMinutos").val(muestra.curvasmin);
                    $("#txtCurvasMinutos").prop("disabled", true);
                    $("#txtCurvasMinutos").removeClass('is-invalid');
                } else {
                    $("#activaCurvasMinutos").hide();
                    $("#checkBoxPuntodeCurva").prop("checked", false);
                }
                $("#btnAgregarMuestra").prop("disabled", false);
                $("#btnVerExamenes").show();
                $("#btnElegirPrefijo").hide();
                $('.selectpicker').selectpicker('refresh');
            } else {
                handlerMessageError(dato.error);
            }

            $('.selectpicker').selectpicker('refresh');
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error.");
        }
    });
}

function filtrarExamenes(idMuestra,prefijo){
    $.ajax({
        type: "post",
        data: "filtroEx=1&idMuestra="+idMuestra+"&prefijo="+prefijo,
        datatype: "json",
        success: function (mensaje) {
			$("#tbodyEx").empty();
            let dato = JSON.parse(mensaje);
			if(dato.examen[0].muestra == "vacio"){
				handlerMessageError("Prefijo no encontrado");
				$("#selectMuestraEx").val(0);
				$('.selectpicker').selectpicker('refresh');
				return;
			}
            if (!dato.hasOwnProperty('error')){
                $("#lblErrorEx").hide();
                if (dato.examen.length > 0) {
					if(dato.examen[0].muestra){
						$("#selectMuestraEx").val(dato.examen[0].muestra);
						
					}else{
						$("#txtBuscaPrefijoEx").val(dato.examen[0].prefijo);

					}
					if(dato.examen[0].descripcion){
	                    dato.examen.forEach(function (examen) {
	                        $("#tbodyEx").append("<tr class='pointer'><td class=''>" + examen.descripcion + "</td></tr>");
	                        //si no esta en el for el refresh no recarga automaticamente desde prefijos
	                        $('.selectpicker').selectpicker('refresh');
	                    });
					}else{
						$("#tbodyEx").append("<tr class='pointer'><td class=''>no hay exámenes en esta muestra</td></tr>");
					}

                } else {
                    $("#lblErrorEx").show();
                }
            } else {
                handlerMessageError(dato.error);
            }
            $('.selectpicker').selectpicker('refresh');
        },
        error: function (msg) {
            handlerMessageError("Ha ocurrido un error");
            console.log(msg.responseText);
        }
    });
    
}

$("#selectMuestraEx").on('change', function () {
    filtrarExamenes($("#selectMuestraEx").val(), "");
    if ($("#selectMuestraEx").val() > 0) {
        $("#txtBuscaPrefijoEx").val("");
    }
    $('.selectpicker').selectpicker('refresh');
});

$("#txtBuscaPrefijoEx").keyup(function () {
	
	if ($("#txtBuscaPrefijoEx").val().length > 1) {
		
    filtrarExamenes("", $("#txtBuscaPrefijoEx").val().trim())
       // $("#selectMuestraEx").val(0);
    }
    $('.selectpicker').selectpicker('refresh');
});

//validarCampos
/*
$("#formRegistroTest").submit(function(){
    let muestra = $("#txtDescripcionAbrev").val();
    let curvamin = $("#txtCurvasMinutos").val();
    if (muestra === "" || (curvamin === "" && $("#checkBoxPuntodeCurva").is(":checked"))){
        if (muestra === ""){
            $("#txtDescripcionAbrev").addClass('is-invalid');
        }
        if (curvamin === "" && $("#checkBoxPuntodeCurva").is(":checked")){
            $("#txtCurvasMinutos").addClass('is-invalid');
        }
        return false;
    } else {
        return true;
    }
    
});*/
function validarFormMuestra(){
	    let muestra = $("#txtDescripcionAbrev").val();
    let curvamin = $("#txtCurvasMinutos").val();
    if (muestra === "" || (curvamin === "" && $("#checkBoxPuntodeCurva").is(":checked"))){
        if (muestra === ""){
            $("#txtDescripcionAbrev").addClass('is-invalid');
        }
        if (curvamin === "" && $("#checkBoxPuntodeCurva").is(":checked")){
            $("#txtCurvasMinutos").addClass('is-invalid');
        }
        return false;
    } else {
        return true;
    }
}

$(".isRequired").keyup(function () {
    console.log($(this).val());
    if ($(this).val() !== ""){
        $(this).removeClass('is-invalid');
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
    var idCausa = $row.getAttribute("data-id-muestra");
    filtrarbyId(idCausa,false);
}

$("#btnAgregar").click(function() {
	insertMuestra();
});
function insertMuestra() {
	if(!validarFormMuestra())
		return;
	let curva = 0;
	if($("#txtCurvasMinutos").val() == null || $("#txtCurvasMinutos").val() == ''){
	
	}else{
		curva=$("#txtCurvasMinutos").val();
	}
    let data = JSON.stringify({
		//cmueIdtipomuestra: idMuestraParaUpdate,
        cmueDescripcionabreviada: $("#txtDescripcionAbrev").val().toUpperCase(),
        cmueDescripcion: $("#txtDescripcion").val().toUpperCase(),
        cmuePrefijotipomuestra: $("#txtPrefijo").val(),
        cmueActivo: $("#checkBoxActivo").val().toUpperCase(),
        //cmueEsmultimuestra: $("#selectCentro").val(),
        //cmueEspesquisa: $("#selectConv").val(),
        cmueSort: $("#numOrden").val(),
        cmueHost: $("#txtHost").val().toUpperCase(),
        cmueHostmicro: $("#txtLISBac").val().toUpperCase(),
        cmueEsmicrobiologia: $("#checkBoxMicrobiología").is(":checked") ? "S" : "N",
        cmueEstipocurva: $("#checkBoxPuntodeCurva").is(":checked") ? "S" : "N",
        cmueCurvasminutos: curva,
        cmueColor: $("#color").val(),
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/muestras/insert",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
			filtrarLike();
			limpiarFormulario();
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
$("#btnUpdate").click(function() {
	updateMuestra();
    $('.selectpicker').selectpicker('refresh');
});
function updateMuestra() {
	if(!validarFormMuestra())
		return;
	let curva = 0;
	if($("#txtCurvasMinutos").val() == null || $("#txtCurvasMinutos").val() == ''){
	
	}else{
		curva=$("#txtCurvasMinutos").val();
	}
    let data = JSON.stringify({
		cmueIdtipomuestra: idMuestraParaUpdate,
        cmueDescripcionabreviada: $("#txtDescripcionAbrev").val().toUpperCase(),
        cmueDescripcion: $("#txtDescripcion").val().toUpperCase(),
        cmuePrefijotipomuestra: $("#txtPrefijo").val(),
        cmueActivo: $("#checkBoxActivo").val().toUpperCase(),
        //cmueEsmultimuestra: $("#selectCentro").val(),
        //cmueEspesquisa: $("#selectConv").val(),
        cmueSort: $("#numOrden").val(),
        cmueHost: $("#txtHost").val().toUpperCase(),
        cmueHostmicro: $("#txtLISBac").val().toUpperCase(),
        cmueEsmicrobiologia: $("#checkBoxMicrobiología").is(":checked") ? "S" : "N",
        cmueEstipocurva: $("#checkBoxPuntodeCurva").is(":checked") ? "S" : "N",
        cmueCurvasminutos: curva,
        cmueColor: $("#color").val(),
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/muestras/update",
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
            handlerMessageError("Error al cargar centros de salud");
        }
    });
}
