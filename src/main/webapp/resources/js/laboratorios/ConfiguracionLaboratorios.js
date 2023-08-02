$(document).ready(function () {
    $(".ocultar").hide();
    filtrarLike();
    desactivarEdit();
    cargarCentrosSalud(centrosSalud());
});

let centrosSalud = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/instituciones/centrosdesalud/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};
new ImaskInputNumber('numSort', false ,1, 999);
function cargarCentrosSalud(centrossalud){
    let options = ``;
    centrossalud.forEach((element) => {
        options += `<option value="${element.ccdsIdcentrodesalud}">${element.ccdsDescripcion}</option>`;
    });
    $("#selectCentroSalud").html(options);
    $('.selectpicker').selectpicker('refresh');
}

$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtDescripcionFiltro").prop("disabled", true);
    } else {
        $("#txtDescripcionFiltro").prop("disabled", false);
    }
    filtrarLike();
});

$("#txtDescripcionFiltro").keyup(function () {
    if ($("#txtDescripcionFiltro").val().length > 0) {
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
    $("#txtDescripcionFiltro").val("");
    $("#txtDescripcionFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked",false);
    filtrarLike();
});

$("#circuloAgregarLab").hover(
        function () {
            $("#iAgregarLab").removeClass("text-blue");
            $("#iAgregarLab").addClass("text-white");
        },
        function () {
            $("#iAgregarLab").removeClass("text-white");
            $("#iAgregarLab").addClass("text-blue");
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
$("#circuloBuscarLab").hover(
        function () {
            $("#iBuscarLab").removeClass("text-blue");
            $("#iBuscarLab").addClass("text-white");
        },
        function () {
            $("#iBuscarLab").removeClass("text-white");
            $("#iBuscarLab").addClass("text-blue");
        }
);
$("#circuloEditarLab").hover(
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarLab").addClass("bg-hover-blue");
                $("#iEditLab").removeClass("text-blue");
                $("#iEditLab").addClass("text-white");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#iEditLab").removeClass("text-white");
                $("#iEditLab").addClass("text-blue");
            }
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
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        descripcion: $("#txtDescripcionFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "post",
        url: getctx + "/api/laboratorios/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
            var cont = 0;
            if (data.status === 200){
                let dato = data.dato;
                $("#tbodyFiltro").empty();
                if (dato.length > 0) {
                    dato.forEach(function (laboratorio) {
                        cont++;
                        $("#tbodyFiltro").append(`<tr class='pointer' onclick='selectLab(this)' onmouseover='mostrarExamenes(this)' >
                                                    <th class='row mx-auto'>${cont}</th>
                                                    <td class=''>${laboratorio.clabCodigo}</td>
                                                    <td class=''>${laboratorio.clabDescripcion}</td>
                                                    <td class='idLab' style='display:none'>${laboratorio.clabIdlaboratorio}</td>
                                                </tr>`);
                    });
                    $("#contRegistros").text(cont);
                    $("#lblTotalFiltro").show();
                    $("#lblErrorFiltro").hide();
                } else {
                    $("#lblTotalFiltro").hide();
                    $("#lblErrorFiltro").show();
                }
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

function mostrarExamenes(a) {
    var $row = $(a).closest("tr"); // Find the row
    var idLab = $row.find(".idLab").text();
    $.ajax({
        type: "GET",
        url: getctx + "/api/countExamenes/laboratorio/" + idLab,
        datatype: "json",
        success: function (data) {
            let cantExamenes = data.dato;
            $row.attr("title", "Exámenes: "+cantExamenes);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

function selectLab(a){
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idLab = $row.find(".idLab").text();
    getLaboratorioById(idLab);
    activarEdit();
}

function getLaboratorioById(idLab,collapse = true){
    $.ajax({
        type: "GET",
        url: getctx + "/api/laboratorio/" + idLab,
        datatype: "json",
        success: function (json) {
            if (json.status === 200){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                let data = json.dato;
                $(".formLab").prop("disabled", true);
                $("#txtIdLab").val(data.clabIdlaboratorio);
                $("#txtCodigo").val(data.clabCodigo);
                $("#txtDescripcion").val(data.clabDescripcion);
                $("#numSort").val(data.clabSort);
                $("#selectCentroSalud").val(data.clabIdCentroSalud);
                $("#chkDerivador").prop("checked", data.clabDerivador === 'S' ? true : false);
                $("#chkPreInformes").prop("checked", data.clabPreinforme === 'S' ? true : false);
                $("#checkBoxActivo").prop("checked", data.clabActivo === 'S' ? true : false);
                $(".switch-content").addClass("disabled");
                data.clabActivo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                $("#lblEstado").text(data.clabActivo === 'S' ? "Activo" : "Inactivo");
                $("#btnAgregarLab").prop("disabled", true);
                $("#txtHost").val(data.clabHost);
                activarEdit();
                $('.selectpicker').selectpicker('refresh');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            handlerMessageError("Error");
        }
    });
}

$("#nuevoLab").click(function () {
    $("#collapseHeader").collapse('hide');
    //$(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarLab").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionLab").offset().top
    }, 700);
});

$("#btnEditar").click(function () {
    if (localStorage.getItem("botonEditar") === "activo"){
        $(".formLab").prop("disabled", false);
        $(".switch-content").removeClass("disabled");
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
		$('#btnLimpiar').addClass('disabled');      
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');
    }
});

function activarEdit() {
    $("#iEditLab").removeClass("text-dark-50");
    $("#iEditLab").addClass("text-blue");
    
    localStorage.setItem("botonEditar", "activo");
    $('.selectpicker').selectpicker('refresh');
}

function desactivarEdit() {
    $("#iEditLab").removeClass("text-blue");
    $("#iEditLab").addClass("text-dark-50");
    
    localStorage.setItem("botonEditar", "inactivo");
    $('.selectpicker').selectpicker('refresh');
}

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

$("#btnCancelUpdate").click(function() {
    $(".formLab").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});

$("#btnLimpiar").click(function (){
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

function limpiarFormulario(){
    $("#txtIdLab").val("");
    $("#txtCodigo").val("");
    $("#txtDescripcion").val("");
    $("#numSort").val(1);
    $('#selectCentroSalud').selectpicker('val', '');
    $("#txtHost").val("");
    $("#chkDerivador").prop("checked", false);
    $("#chkPreInformes").prop("checked", false);
    $("#checkBoxActivo").prop("checked", true);
    $("#lblEstado").text("Activo");
    $(".formLab").prop("disabled", false);
    $(".switch-content").removeClass("disabled inactivo");
    $("#btnAgregarLab").prop("disabled", false);
    $("#divAgregarBtn").show();
    $("#divActualizarBtn").hide();
    desactivarEdit();
    $('.selectpicker').selectpicker('refresh');
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
    var idCausa = $row.querySelector('td.idLab').textContent;
    getLaboratorioById(idCausa,false);
    $('.selectpicker').selectpicker('refresh');
}
