var cfgMuestras = new CfgMuestras();


$(document).ready(function () {
    $(".ocultar").hide();
    filtrarEnvases();
    cargarMuestras();
    desactivarEdit();

    $('.selectpicker').selectpicker('refresh');

});
new ImaskInputNumber('txtOrden', false ,1, 999);
new ImaskInputNumber('numVolTotal', true ,1, 999);
new ImaskInputNumber('numVolUtil', false ,1, 999999);

function cargarMuestras() {

  function fillSelect(jqIdSelect) {
    function fill(data) {
        //let opt = new Option('TODOS', '', true, true);
      data.forEach(muestra => {
         opt = new Option(muestra.cmueDescripcion, muestra.cmueIdtipomuestra);
        $(jqIdSelect).append($(opt));
      });
      
      $(jqIdSelect).append($(opt));
      $('.selectpicker').selectpicker('refresh');
    }
    return fill;
  }

  let fillSelectMuestraFiltro = fillSelect("#selectTipoMuestraFiltro");
  getMuestras(cfgMuestras, fillSelectMuestraFiltro);
  let fillSelectMuestra = fillSelect("#selectTipoMuestra");
  getMuestras(cfgMuestras, fillSelectMuestra);
  $('.selectpicker').selectpicker('refresh');
}
/*
function cargarMuestras(muestras){
    let options = `<option value="0" selected>Seleccione</option>`;
    muestras.cfgMuestras.forEach((element) => {
        options += `<option value="${element.id}">${element.descripcion}</option>`;
    });
    $("#selectTipoMuestraFiltro, #selectTipoMuestra").html(options);
}*/


$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtDescripcionFiltro").prop("disabled", true);
        $("#selectTipoMuestraFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtDescripcionFiltro").prop("disabled", false);
        $("#selectTipoMuestraFiltro").prop("disabled", false);
    }
    filtrarEnvases();
});

$("#txtDescripcionFiltro").keyup(function () {
    if ($("#txtDescripcionFiltro").val().length > 0) {
        $("#txtCodigoFiltro").prop("disabled", true);
        $("#txtDescripcionFiltro").prop("disabled", false);
        $("#selectTipoMuestraFiltro").prop("disabled", false);
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtDescripcionFiltro").prop("disabled", false);
        $("#selectTipoMuestraFiltro").prop("disabled", false);
    }
    filtrarEnvases();
});

$("#selectTipoMuestraFiltro").change(function () {
    if ($("#selectTipoMuestraFiltro").val() > 0) {
        $("#txtCodigoFiltro").prop("disabled", true);
        $("#txtDescripcionFiltro").prop("disabled", false);
        $("#selectTipoMuestraFiltro").prop("disabled", false);
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtDescripcionFiltro").prop("disabled", false);
        $("#selectTipoMuestraFiltro").prop("disabled", false);
    }
    filtrarEnvases();
});

$("#chkMostrarActivos").change(function(){
    filtrarEnvases();
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtDescripcionFiltro").val("");
    $("#txtDescripcionFiltro").prop("disabled", false);
    $("#selectTipoMuestraFiltro").val(0);
    $("#selectTipoMuestraFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked",false);
    $('.selectpicker').selectpicker('refresh')
    filtrarEnvases();
});

$("#circuloAgregarEnvase").hover(
        function () {
            $("#iAgregarEnvase").removeClass("text-blue");
            $("#iAgregarEnvase").addClass("text-white");
        },
        function () {
            $("#iAgregarEnvase").removeClass("text-white");
            $("#iAgregarEnvase").addClass("text-blue");
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
$("#circuloBuscarEnvase").hover(
        function () {
            $("#iBuscarEnvase").removeClass("text-blue");
            $("#iBuscarEnvase").addClass("text-white");
        },
        function () {
            $("#iBuscarEnvase").removeClass("text-white");
            $("#iBuscarEnvase").addClass("text-blue");
        }
);
$("#circuloEditarEnvase").hover(
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarEnvase").addClass("bg-hover-primary");
                $("#iEditEnvase").removeClass("text-blue");
                $("#iEditEnvase").addClass("text-white");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#iEditEnvase").removeClass("text-white");
                $("#iEditEnvase").addClass("text-blue");
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
$("#nuevoEnvase").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});
$("#buscarEnvase").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionExample8").offset().top
    }, 700);
});

$("#btnEditarEnvase").click(function() {
    if (localStorage.getItem("botonEditar") === "activo"){

		$('#btnLimpiar').addClass('disabled');
        $(".formEnvases").prop("disabled", false);
        $(".switch-content").removeClass("disabled");
        $("#cambiarImagenEnvase").show();
        $("#cancelImage").show();
        $("#removeImage").show();
        $("#btnAgregarMuestra").prop("disabled", true);
        $("#btnAgregarMuestra").removeClass("disabled");
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
        $("#btnElegirPrefijo").show();
        $('.selectpicker').selectpicker('refresh');
        desactivarEdit();
    }
});

$("#btnLimpiar").click(function (){
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});


function limpiarFormulario(){
    $(".formEnvases").prop("disabled",false);
    $(".switch-content").removeClass("disabled inactivo");
    $("#txtCodigo").val("");
    $("#txtDescripcion").val("");
    $("#selectTipoMuestra").val(0);
    $("#txtOrden").val(1);
    $("#numVolTotal").val(0);
    $("#numVolUtil").val(0);
    $("#cambiarImagenEnvase").show();
    $("#cancelImage").show();
    $("#removeImage").show();
    $(".image-input-wrapper").css("background-image", "");
    
    $('.selectpicker').selectpicker('refresh');
    $("#lblEstado").text("Activo");
    $("#checkBoxActivo").prop("checked", true);
    $("#btnAgregarEnvase").prop("disabled", false);
    $("#divAgregarBtn").show();
    $("#divActualizarBtn").hide();
    desactivarEdit();
}

function filtrarEnvases() {
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        descripcion: $("#txtDescripcionFiltro").val(),
        idMuestra: $("#selectTipoMuestraFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "post",
        url: getctx + "/api/envases/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
            var cont = 0;
            //var dato = JSON.parse(mensaje);
            if (data.status === 200){
                let dato = data.dato;
                $("#tbodyFiltro").empty();
                if (dato.length > 0) {
                    dato.forEach(function (envase) {
                        cont++;
                        $("#tbodyFiltro").append(`<tr class='pointer' onclick='selectEnvase(this)' >
                                                    <th class='row mx-auto'>${cont}</th>
                                                    <td class=''>${envase.cenvCodigo}</td>
                                                    <td class='' >${envase.cenvDescripcion}</td>
                                                    <td class='idEnvase' style='display:none'>${envase.cenvIdenvase}</td>
                                                </tr>`);
                    });
                    $("#lblErrorFiltro").hide();
                    $("#lblTotalFiltro").show();
                    $("#totalFiltro").text(cont);
                } else {
                    $("#lblErrorFiltro").show();
                    $("#lblTotalFiltro").hide();
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

function selectEnvase(a) {
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idEnvase = $row.find(".idEnvase").text();
    filtrarbyId(idEnvase);

    activarEdit();
    $('.selectpicker').selectpicker('refresh');

}

function activarEdit() {
    $("#iEditEnvase").removeClass("text-dark-50");
    $("#iEditEnvase").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
    $('.selectpicker').selectpicker('refresh');
}

function desactivarEdit() {
    $("#iEditEnvase").removeClass("text-blue");
    $("#iEditEnvase").addClass("text-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
    $('.selectpicker').selectpicker('refresh');
}
function filtrarbyId(idEnvase,collapse = true) {
    $.ajax({
        type: "get",
        url: getctx + "/api/envase/" + idEnvase,
        datatype: "json",
        success: function (json) {
            if (json.status === 200){
                envase = json.dato.cfgEnvases;
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                $(".formEnvases").prop("disabled", true);
                $(".switch-content").addClass("disabled");
                $("#txtIdEnvase").val(envase.cenvIdenvase);
                $("#txtCodigo").val(envase.cenvCodigo);
                console.log(envase.cenvIdtipomuestra);
                $("#selectTipoMuestra").val(envase.cenvIdtipomuestra);
                $("#txtDescripcion").val(envase.cenvDescripcion);
                $("#txtOrden").val(envase.cenvSort);
                $("#numVolTotal").val(envase.cenvVolumenml);
                $("#numVolUtil").val(envase.cenvVolumenutilmicrolt);
                $("#txtHost").val(envase.cenvHost);
                
                if (envase.cenvActivo == "S") {
                    $("#checkBoxActivo").prop("checked", true);
                    $("#lblEstado").text("Activo");
                    $("#txtEstado").val(envase.EstadoEnvase);
                } else {
                    $("#checkBoxActivo").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                    $("#txtEstado").val(envase.EstadoEnvase);
                }
                envase.cenvActivo === "S" ? $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
             
              //  $(".image-input-wrapper").css("background-image", "url(data:image/jpeg;base64," + json.dato.base64Img + ")");
              //  $(".image-input-wrapper").css("background-size", "300px 300px");
               // $('input[name=imagenEnvase]').attr("src",  json.dato.base64Img );
                
                $("#fotoEnvaseWrapper").css("background-image", "url(data:image/jpeg;base64," + json.dato.base64Img + ")");
               $("#fotoEnvaseWrapper").css("background-size", "300px 300px");
                $("#btnAgregarEnvase").prop("disabled", true);
                $("#cambiarImagenEnvase").hide();
                $("#cancelImage").hide();
                $("#removeImage").hide();
                $('.selectpicker').selectpicker('refresh');
            
                activarEdit();
            } else {
                handlerMessageError(json.message);
            }
            /*
            let dato = JSON.parse(mensaje);
            if (!dato.hasOwnProperty('error')){
                $("#collapseHeader").collapse('hide');
                $("#lblErrorFiltro").hide();
                var envase = dato.envase[0];
                $(".formEnvases").prop("disabled", true);
                $("#txtIdEnvase").val(envase.IdEnvase);
                $("#txtCodigo").val(envase.CodigoEnvase);
                $("#selectTipoMuestra").val(envase.TipoMuestra);
                $("#txtDescripcion").val(envase.DescripcionEnvase);
                $("#txtOrden").val(envase.OrdenEnvase);
                $("#numVolTotal").val(envase.TotalEnvase);
                $("#numVolUtil").val(envase.UtilEnvase);
                if (envase.EstadoEnvase == "S") {
                    $("#checkBoxActivo").prop("checked", true);
                    $("#lblEstado").text("Activo");
                    $("#txtEstado").val(envase.EstadoEnvase);
                } else {
                    $("#checkBoxActivo").prop("checked", false);
                    $("#lblEstado").text("Inactivo");
                    $("#txtEstado").val(envase.EstadoEnvase);
                }
                $("#checkBoxActivo").prop("disabled", true);
                $(".image-input-wrapper").css("background-image", "url(data:image/jpeg;base64," + envase.imagen + ")");
                $("#btnAgregarEnvase").prop("disabled", true);
                $("#cambiarImagenEnvase").hide();
                $("#cancelImage").hide();
                $("#removeImage").hide();
                $('.selectpicker').selectpicker('refresh');
                activarEdit();
            } else {
                handlerMessageError(dato.error);
            }*/
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al seleccionar contenedor.");
        }
    });
}

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

$("#btnCancelEnvase").click(function() {
    $(".formEnvases").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $("#cambiarImagenEnvase").hide();
    $("#cancelImage").hide();
    $("#removeImage").hide();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});
$("#btnUpdateEnvase").click(function() {
	$('#btnLimpiar').removeClass('disabled');
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
    var idCausa = $row.querySelector('td.idEnvase').textContent;
    filtrarbyId(idCausa,false);
    $('.selectpicker').selectpicker('refresh');
}

var avatar1 = new KTImageInput('kt_image_5');
