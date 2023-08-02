$(document).ready(function () {
    $(".ocultar").hide();
    KTDualListbox.init();
    KTDualListbox2.init();
    $(".prototypedual-listbox__search").attr("placeholder", "Buscar");
});

$("#txtNombre").keyup(function () {
    nombreTitulo($("#txtNombre").val());
    $("#exampleModalLabel5").text($("#txtNombre").val());
});

function nombreTitulo(nombre) {
    $("#tituloRegistro").text("Examen: " + nombre);
    $("#titleModalTablaReferencias").text("Tabla de referencias: "+ nombre);
}


$(document).on("click", "#tablaReferencias td", function () {
    var $this = $(this);
    var $input = $('<input>', {
        value: $this.text(),
        class: 'form-control',
        type: 'text',
        keypress: function () {

        },
        blur: function () {
            $this.text(this.value);
            //console.log($this.attr('class'));
        },
        keyup: function (e) {

            if (e.which === 13)
                $input.blur();
        }
    }).appendTo($this.empty()).focus();
});

$("#btnBuscarFiltro").click(function () {
    var codigoFiltro = $("#txtCodigoFiltro").val();
    var descFiltro = $("#txtNombreTestFiltro").val();
    if (codigoFiltro.length > 1 || descFiltro.length > 1) {
        filtrarExamen(codigoFiltro, descFiltro);
    } else {
        notificacionDosCaracteres();
    }

});

$("#btnAddColumn").click(function () {
    var columnNumber = (document.getElementById('tablaReferencias').rows[0].cells.length + 1);
    var id = 1 + "" + columnNumber;
    $("#theadTablaRef tr").append("<td>nueva columna<input id=" + id + " type='text' name='valorReferencia' style='visibility: hidden' value='" + id + "/valor" + id + "' /></td>");
});

$("#btnAddRow").click(function () {
    var td = createTd($("#theadTablaRef td").length);
    $("#tobdyTablaRef").append("<tr>" + td + "</tr>");
});

function createTd(largo) {
    var td = "";
    var rowNumber = ($("#tablaReferencias tr").length + 1);
    var cont = 0;
    for (var i = 0; i < largo; i++) {
        cont++;
        var id = rowNumber + "" + cont;
        td = td + "<td>valor<input id=" + id + " type='text' name='valorReferencia' style='visibility: hidden' value='" + id + "/valor" + id + "' /></td>";
    }
    return td;
}

function filtrarExamen(codigo, descripcion) {
    $.ajax({
        type: "post",
        data: "filtro=1&codigoFiltro=" + codigo + "&descripcionFiltro=" + descripcion,
        datatype: "json",
        success: function (mensaje) {
            var cont = 0;
            var dato = JSON.parse(mensaje);
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            if (dato.examen.length > 0) {
                dato.examen.forEach(function (examen) {
                    cont++;
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='buscarCodigo(this)' ><td class='row mx-auto'><b>" + cont + "</b></td><td class='codigoTest'>" + examen.CodigoExamen + "</td><td class=''>" + examen.DescripcionExamen + "</td><td style='visibility: hidden'>" + examen.IdExamen + "</td></tr>");
                });
            } else {
                $("#lblErrorFiltro").show();
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

function notificacionDosCaracteres() {
    $.notify({
        message: "Se requiere al menos 2 caracteres para buscar"
    }, {
        // settings
        element: 'body',
        position: null,
        type: "danger",
        allow_dismiss: true,
        newest_on_top: false,
        showProgressbar: false,
        placement: {
            from: "top",
            align: "left"
        },
        offset: 20,
        spacing: 10,
        z_index: 1031,
        delay: 5000,
        timer: 1000,
        url_target: '_blank',
        mouse_over: null,
        animate: {
            enter: 'animated fadeInDown',
            exit: 'animated fadeOutUp'
        },
        onShow: null,
        onShown: null,
        onClosed: null
    });
}

$("#btnCerrarModalTest").click(function () {
    var cont = 0;
    $(".inputTest").each(function (item) {
        $.ajax({
            type: "post",
            data: "getTest=1&idTest=" + this.value,
            datatype: "json",
            success: function (mensaje) {
                var dato = JSON.parse(mensaje);
                dato.test.forEach(function (test) {
                    cont++;
                    var numero = $('#divMuestras').children().length;
                    switch (numero) {
                        case 0:
                            $("#divMuestras").append("<span class='label label-inline label-light-success font-weight-bolder'>" + test.MuestraTest + "</span>");
                            break;
                        case 1:
                            $("#divMuestras").append("<span class='label label-inline label-light-primary font-weight-bolder ml-2'>" + test.MuestraTest + "</span>");
                            break;
                        case 2:
                            $("#divMuestras").append("<span class='label label-inline label-light-danger font-weight-bolder ml-2'>" + test.MuestraTest + "</span>");
                            break;
                        case 3:
                            $("#divMuestras").append("<span class='label label-inline label-light-warning font-weight-bolder ml-2'>" + test.MuestraTest + "</span>");
                            break;
                        case 4:
                            $("#divMuestras").append("<span class='label label-inline label-light-info font-weight-bolder ml-2'>" + test.MuestraTest + "</span>");
                            break;
                        case 5:
                            $("#divMuestras").append("<span class='label label-inline label-light-success font-weight-bolder ml-2'>" + test.MuestraTest + "</span>");
                            break;
                        case 6:
                            $("#divMuestras").append("<span class='label label-inline label-light-primary font-weight-bolder ml-2'>" + test.MuestraTest + "</span>");
                            break;
                        case 7:
                            $("#divMuestras").append("<span class='label label-inline label-light-danger font-weight-bolder ml-2'>" + test.MuestraTest + "</span>");
                            break;
                        case 8:
                            $("#divMuestras").append("<span class='label label-inline label-light-warning font-weight-bolder ml-2'>" + test.MuestraTest + "</span>");
                            break;
                        case 9:
                            $("#divMuestras").append("<span class='label label-inline label-light-info font-weight-bolder ml-2'>" + test.MuestraTest + "</span>");
                            break;
                    }
                    //$("#txtMuestraTest").text(<span class="label label-warning label-pill label-inline mr-2">test.MuestraTest</span>);
                    //$("#divMuestras").append("<span class='label label-inline label-light-success font-weight-bolder'>" + test.MuestraTest + "</span>");
                    $("#tbodyTestSeleccionados").append("<tr>" +
                            "<d class='row mx-auto'><b>" + cont + "</b></td>" +
                            "<td>" + test.DescripcionTest + "</td>" +
                            "<td>" + test.MuestraTest + "</td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td><a href='javascript:;' class='btn btn-sm btn-clean btn-icon' onclick='removeRow(this)' title='Delete'>	<i class='la la-trash'></i></a></td>" +
                            "</tr>");
                });
            },
            error: function (msg) {
                console.log(msg);
            }
        });
    });
});

// Class definition
var KTDualListbox = function () {
    // Private functions
    var demo2 = function () {
        // Dual Listbox
        var $this = $('#kt_dual_listbox_2');

        // get options
        var options = [];
        $this.children('option').each(function () {
            var value = $(this).val();
            var label = $(this).text();
            options.push({
                text: label,
                value: value
            });
        });

        // init dual listbox
        var dualListBox = new DualListbox($this.get(0), {
            addEvent: function (value) {
                $("#tbodyTest").append("<tr><td><input id='" + value + "' type='text' value='" + value + "' class='inputTest' /></td></tr>");

            },
            removeEvent: function (value) {
                $("#" + value + "").remove();
            },
            availableTitle: "Test disponibles",
            selectedTitle: "Tests seleccionados",
            addButtonText: "<i class='flaticon2-next'></i>",
            removeButtonText: "<i class='flaticon2-back'></i>",
            addAllButtonText: "<i class='flaticon2-fast-next'></i>",
            removeAllButtonText: "<i class='flaticon2-fast-back'></i>",
            options: options,
        });
    };

    return {
        // public functions
        init: function () {
            demo2();
        },
    };
}();

// Class definition
var KTDualListbox2 = function () {
    // Private functions
    var demo2 = function () {
        // Dual Listbox
        var $this = $('#selectExamenConjunto');

        // get options
        var options = [];
        $this.children('option').each(function () {
            var value = $(this).val();
            var label = $(this).text();
            options.push({
                text: label,
                value: value
            });
        });

        // init dual listbox
        var dualListBox = new DualListbox($this.get(0), {
            addEvent: function (value) {
                console.log(value);
            },
            removeEvent: function (value) {
                console.log(value);
            },
            availableTitle: "Examen disponibles",
            selectedTitle: "Tests seleccionados",
            addButtonText: "<i class='flaticon2-next'></i>",
            removeButtonText: "<i class='flaticon2-back'></i>",
            addAllButtonText: "<i class='flaticon2-fast-next'></i>",
            removeAllButtonText: "<i class='flaticon2-fast-back'></i>",
            options: options,
        });
    };

    return {
        // public functions
        init: function () {
            demo2();
        },
    };
}();

function removeRow(event) {
    Swal.fire({
        title: '¿Estás segudo de eliminar el Test seleccioando?',
        text: '',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí',
        cancelButtonText: 'No',
        reverseButtons: true
    }).then(function (result) {
        if (result.value) {
            $($(event).closest("tr")).remove();
            Swal.fire(
                    'Eliminado!',
                    'El test ha sido eliminado',
                    'success'
                    )
            // result.dismiss can be "cancel", "overlay",
            // "close", and "timer"
        } else if (result.dismiss === 'cancel') {
//            Swal.fire(
//                    'Cancelled',
//                    'Your imaginary file is safe :)',
//                    'error'
//                    )
        }
    });

}

$("#txtCodigo").keyup(function () {
    if ($("#txtCodigo").val().length > 0) {
        $("#txtCodigo").removeClass("is-invalid");
    }
});

$("#txtNombre").keyup(function () {
    if ($("#txtNombre").val().length > 0) {
        $("#txtNombre").removeClass("is-invalid");
    }
});

$('form').submit(function () {
    var codigo = $("#txtCodigo").val();
    var nombre = $("#txtNombre").val();
    var tablaTest = $("#tbodyTestSeleccionados tr").length;
    if (codigo.length <= 0 || nombre.length <= 0 || tablaTest <= 0) {
        if (codigo.length <= 0) {
            $("#txtCodigo").addClass("is-invalid");
        }
        if (nombre.length <= 0) {
            $("#txtNombre").addClass("is-invalid");
        }
        if (tablaTest <= 0) {
            alert("seleccionar minimo 1 test");
        }
        return false;
    } else {
        return true;
    }
});