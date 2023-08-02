//var getctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(document).ready(function(){
    $(".ocultar").hide();
    filtrarLike("", 1);
    cargarListBox(null);
});

function filtrarLike(nombre, activo) {
    $.ajax({
        type: "post",
        data: "filtro=1&nombre=" + nombre + "&activo=" + activo,
        url: "ConfiguracionPanelesExamenes",
        datatype: "json",
        success: function (mensaje) {
            var cont = 0;
            var dato = JSON.parse(mensaje);
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            if (dato.paneles.length > 0) {
                dato.paneles.forEach(function (panel) {
                    cont++;
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='selectPanel(this)' ><th class='row mx-auto'>" + cont + "</th><td class=''>" + panel.Descripcion + "</td><td class='idPanel' style='display:none'>" + panel.Id + "</td></tr>");
                });
            } else {
                $("#lblErrorFiltro").show();
            }
            $("#spanCantExamenes").text(cont);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

$("#txtNombreFiltro").keyup(function () {
    if ($("#checkBoxInactivos").is(":checked")) {
        filtrarLike($("#txtNombreFiltro").val().trim(), 0);
    } else {
        filtrarLike($("#txtNombreFiltro").val().trim(), 1);
    }
});

$("#checkBoxInactivos").click(function () {
    if ($("#checkBoxInactivos").is(":checked")) {
        filtrarLike($("#txtNombreFiltro").val().trim(), 0);
    } else {
        filtrarLike($("#txtNombreFiltro").val().trim(), 1);
    }
});

function selectPanel(a){
    var $row = $(a).closest("tr"); // Find the row
    var idPanel = $row.find(".idPanel").text();
    buscarPorId(idPanel);
    activarEdit();
}

function activarEdit() {
    $("#iEditPanel").removeClass("text-dark-50");
    $("#iEditPanel").addClass("text-primary");
}

function buscarPorId(idPanel){
    $.ajax({
        type: "post",
        data: "buscarPorId=1&idPanel=" + idPanel,
        async: false,
        datatype: "json",
        success: function (mensaje) {
            let dato = JSON.parse(mensaje);
            let panel = dato.paneles[0];
            $("#btnAgregarPanel").prop("disabled", true);
            $("#txtNombrePanel").prop("disabled", true);
            $("#checkBoxActivo").prop("disabled", true);
            $("#txtIdPanel").val(panel.panelId);
            $("#txtNombrePanel").val(panel.panelNombre);
            if (panel.panelActivo === "S") {
                $("#checkBoxActivo").prop("checked", true);
                $("#lblEstado").text("Activo");
            } else {
                $("#checkBoxActivo").prop("checked", false);
                $("#lblEstado").text("Inactivo");
            }
            $("#listbox_examenes").empty();
            recargarDatos(panel.examenesPanel);
            $("#listbox").hide();
            $("#examenesTest").show();
            $("#examenesTest").empty();
            panel.examenesPanel.forEach(function (examen) {
                $("#examenesTest").append('<li>' + '<input type="hidden" value="' + examen.examenId + '" name="ExamenesResumenes">' + examen.examenNombre + '</li>');    
            });
            
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

$("#nuevoPanel").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarPanel").click(function () {
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
    $("#txtNombreFiltro").val("");
    $("#txtNombreFiltro").prop("disabled", false);
    filtrarLike("");
});

$("#iLimpiar").click(function () {
    limpiarFormulario();
});

$("#btnEditarPanel").click(function() {
    $("#txtNombrePanel").prop("disabled", false);
    $("#checkBoxActivo").prop("disabled", false);
    $("#btnAgregarPanel").removeClass("disabled");
    $("#btnAgregarPanel").hide();
    $("#btnUpdatePanel").show();
    $("#listbox").show();
    $("#examenesTest").hide();
});

function limpiarFormulario(){
    $("#txtNombrePanel").val("");
    $("#txtNombrePanel").prop("disabled", false);
    $("#checkBoxActivo").prop("checked", "checked");
    $("#lblEstado").text("Activo");
    $("#checkBoxActivo").prop("disabled", false);
    $("#btnAgregarPanel").show();
    $("#btnAgregarPanel").prop("disabled", false);
    $("#btnUpdatePanel").hide();
    $("#listbox").show();
    $("#examenesTest").hide();
    $("#examenesTest").empty();
    recargarDatos(null);
}

function revisarTestRepetidos(idTest) {
    $('#examenesTest li').each(function (){
        examenesTest = $(this).val();
        if (idTest == examenesTest) {
            console.log('TEST REPETIDO ' + examenesTest);
        }

    });
}

$("#formRegistroPanel").submit(function(){
    let nombre = $("#txtNombrePanel").val();
    let cont = 0;
    $(".dual-listbox__selected li").each(function (index) {
        
        console.log(index + ": " + $(this).text());
        console.log(index + ": " + $(this).attr("data-id"));

        $("#examenesTest").append('<li>' + '<input type="hidden" value="' + $(this).attr("data-id") + '" name="ExamenesResumenes">' + $(this).text() + '</li>');
        cont++;
    });
    
    if (nombre.trim() === "" || cont === 0){
        if (nombre.trim() === ""){
            $("#txtNombrePanel").addClass('is-invalid');
            $.notify({ message: "Nombre no debe estar vacío" }, { type: "danger" });
        }
        if (cont === 0){
            $("#kt_dual_listbox_2").addClass('is-invalid');
            $.notify({ message: "Debe seleccionar al menos un examen" }, { type: "danger" });
        }
        return false;
    } else {
        //$.notify({ message: "Panel ingresado correctamente" }, { type: "success" });
        return true;
    }
    
});