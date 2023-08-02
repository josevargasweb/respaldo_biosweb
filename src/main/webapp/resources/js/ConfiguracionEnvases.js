var cfgMuestras = new CfgMuestras();
cfgMuestras.getMuestras();

function cargarMuestras(muestras){
    let options = `<option value="0" selected>Seleccione</option>`;
    muestras.cfgMuestras.forEach((element) => {
        options += `<option value="${element.id}">${element.descripcion}</option>`;
    });
    $("#selectTipoMuestraFiltro").html(options);
    $('.selectpicker').selectpicker('refresh');
}

$(document).ready(function () {
    $(".ocultar").hide();
    KTKBootstrapTouchspin.init();
    filtrarEnvases();
    cargarMuestras(cfgMuestras)
    $('.selectpicker').selectpicker('refresh');
});
/*
$("#btnBuscarFiltro").click(function () {
    filtrarEnvases($("#txtCodigoFiltro").val(), $("#txtDescripcionFiltro").val(), $("#selectTipoMuestraFiltro").val());
});
*/

var avatar1 = new KTImageInput('kt_image_5');

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
    filtrarEnvases();
});

$("#circuloAgregarTest").hover(
        function () {
            $("#iAgregarTest").removeClass("text-primary");
            $("#iAgregarTest").addClass("text-white");
        },
        function () {
            $("#iAgregarTest").removeClass("text-white");
            $("#iAgregarTest").addClass("text-primary");
        }
);
$("#circuloLimpiarFiltro").hover(
        function () {
            $("#iLimpiarFiltro").removeClass("text-primary");
            $("#iLimpiarFiltro").addClass("text-white");
        },
        function () {
            $("#iLimpiarFiltro").removeClass("text-white");
            $("#iLimpiarFiltro").addClass("text-primary");
        }
);
$("#circuloBuscarEnvase").hover(
        function () {
            $("#iBuscarPaciente").removeClass("text-primary");
            $("#iBuscarPaciente").addClass("text-white");
        },
        function () {
            $("#iBuscarPaciente").removeClass("text-white");
            $("#iBuscarPaciente").addClass("text-primary");
        }
);
$("#circuloEditarEnvase").hover(
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarEnvase").addClass("bg-hover-primary");
                $("#iEditEnvase").removeClass("text-primary");
                $("#iEditEnvase").addClass("text-white");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#iEditEnvase").removeClass("text-white");
                $("#iEditEnvase").addClass("text-primary");
            }
        }
);
$("#circuloLimpiar").hover(
        function () {
            $("#iLimpiar").removeClass("text-primary");
            $("#iLimpiar").addClass("text-white");
        },
        function () {
            $("#iLimpiar").removeClass("text-white");
            $("#iLimpiar").addClass("text-primary");
        }
);
$("#nuevaGlosa").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
});
$("#buscarPaciente").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#accordionExample8").offset().top
    }, 700);
});

$("#btnEditarEnvase").click(function() {
    $(".formEnvases").prop("disabled", false);
    $("#btnAgregarMuestra").prop("disabled", true);
    $("#btnAgregarMuestra").removeClass("disabled");
    $("#btnAgregarMuestra").hide();
    $("#btnGuardarUpdate").show();
    $("#btnElegirPrefijo").show();
});

$("#btnLimpiar").click(function (){
    $("#txtCodigo").val("");
    $("#txtCodigo").prop("disabled",false);
    $("#txtDescripcion").val("");
    $("#txtDescripcion").prop("disabled",false);
    $("#selectTipoMuestra").val("N");
    $("#selectTipoMuestra").prop("disabled",false);
    $("#txtOrden").val("");
    $("#txtOrden").prop("disabled",false);
    $("#kt_touchspin_5").val("0");
    $("#kt_touchspin_5").prop("disabled",false);
    $('.selectpicker').selectpicker('refresh');
    
});
function filtrarEnvases() {
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        descripcion: $("#txtDescripcionFiltro").val(),
        idMuestra: $("#selectTipoMuestraFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        type: "post",
        //data: "filtro=1&codigo=" + codigo + "&descripcion=" + descripcion + "&idMuestra=" + idMuestra,
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
                $("#lblErrorFiltro").hide();
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
                    $("#lblTotalFiltro").show();
                    $("#totalFiltro").text(cont);
                } else {
                    $("#lblErrorFiltro").show();
                    $("#lblTotalFiltro").hide();
                }
            } else {
                
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

function selectEnvase(a) {
    var $row = $(a).closest("tr"); // Find the row
    var idEnvase = $row.find(".idEnvase").text();
    filtrarbyId(idEnvase);
    activarEdit();
}

function activarEdit() {
    $("#iEditEnvase").removeClass("text-dark-50");
    $("#iEditEnvase").addClass("text-primary");
    localStorage.setItem("botonEditar", "activo");
}
function filtrarbyId(idEnvase) {
    $.ajax({
        type: "post",
        data: "filtroId=1&idEnvase=" + idEnvase,
        datatype: "json",
        success: function (mensaje) {
            //var cont = 0;
            var dato = JSON.parse(mensaje);
            //$("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            var envase = dato.envase[0];
            $(".formEnvases").prop("disabled", true);
            $("#txtIdEnvase").val(envase.IdEnvase);
            $("#txtCodigo").val(envase.CodigoEnvase);
            $("#txtCodigo").prop("disabled", true);
            $("#selectTipoMuestra").val(envase.TipoMuestra);
            $("#selectTipoMuestra").prop("disabled", true);
            $("#txtDescripcion").val(envase.DescripcionEnvase);
            $("#txtDescripcion").prop("disabled", true);
            $("#txtOrden").val(envase.OrdenEnvase);
            $("#txtOrden").prop("disabled", true);
            if (envase.EstadoEnvase == "S") {
                $("#checkBoxLever").prop("checked", true);
                $("#lblEstado").text("Activo");
                $("#txtEstado").val(envase.EstadoEnvase);
            } else {
                $("#checkBoxLever").prop("checked", false);
                $("#lblEstado").text("Inactivo");
                $("#txtEstado").val(envase.EstadoEnvase);
            }
            $("#checkBoxLever").prop("disabled", true);
            $(".image-input-wrapper").css("background-image", "url(data:image/jpeg;base64," + envase.imagen + ")");
            $("#btnAgregarGlosa").prop("disabled", true);
            $('.selectpicker').selectpicker('refresh');
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

var KTKBootstrapTouchspin = function () {

    // Private functions
    var demos = function () {
        // minimum setup
        $('#kt_touchspin_1, #kt_touchspin_2_1').TouchSpin({
            buttondown_class: 'btn btn-secondary',
            buttonup_class: 'btn btn-secondary',

            min: 0,
            max: 100,
            step: 0.1,
            decimals: 2,
            boostat: 5,
            maxboostedstep: 10,
        });

        // with prefix
        $('#kt_touchspin_2, #kt_touchspin_2_2').TouchSpin({
            buttondown_class: 'btn btn-secondary',
            buttonup_class: 'btn btn-secondary',

            min: -1000000000,
            max: 1000000000,
            stepinterval: 50,
            maxboostedstep: 10000000,
            prefix: '$'
        });

        // vertical button alignment:
        $('#kt_touchspin_3, #kt_touchspin_2_3').TouchSpin({
            buttondown_class: 'btn btn-secondary',
            buttonup_class: 'btn btn-secondary',

            min: -1000000000,
            max: 1000000000,
            stepinterval: 50,
            maxboostedstep: 10000000,
            postfix: '$'
        });

        // vertical buttons with custom icons:
        $('#kt_touchspin_4, #kt_touchspin_2_4').TouchSpin({
            buttondown_class: 'btn btn-secondary',
            buttonup_class: 'btn btn-secondary',
            verticalbuttons: true,
            verticalup: '<i class="ki ki-plus"></i>',
            verticaldown: '<i class="ki ki-minus"></i>'
        });

        // vertical buttons with custom icons:
        $('#kt_touchspin_5, #kt_touchspin_2_5').TouchSpin({
            buttondown_class: 'btn btn-secondary',
            buttonup_class: 'btn btn-secondary',
            verticalbuttons: true,
            verticalup: '<i class="ki ki-arrow-up"></i>',
            verticaldown: '<i class="ki ki-arrow-down"></i>'
        });
    }

    return {
        // public functions
        init: function () {
            demos();
        }
    };
}();

