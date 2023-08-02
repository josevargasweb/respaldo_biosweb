$(document).ready(function () {
    $(".ocultar").hide();
    filtrarLike("", "");
});

$("#txtCodigoFiltro").keyup(function () {
    filtrarLike($("#txtCodigoFiltro").val().trim(), "");
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtDescripcionFiltro").prop("disabled", true);
    } else {
        $("#txtDescripcionFiltro").prop("disabled", false);
    }
});

$("#txtDescripcionFiltro").keyup(function () {
    filtrarLike("", $("#txtDescripcionFiltro").val().trim());
    if ($("#txtDescripcionFiltro").val().length > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
    }
});

function filtrarLike(codigo, descripcion) {
    $.ajax({
        type: "post",
        data: "filtro=1&codigo=" + codigo + "&descripcion=" + descripcion,
        datatype: "json",
        success: function (mensaje) {
            var cont = 0;
            var dato = JSON.parse(mensaje);
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            if (dato.laboratorio.length > 0) {
                dato.laboratorio.forEach(function (laboratorio) {
                    cont++;
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='selectMetodo(this)' onmouseover='mostrarExamenes(this)' ><th class='row mx-auto'>" + cont + "</th><td class=''>" + laboratorio.CodigoLab + "</td><td class=''>" + laboratorio.DescripcionLab + "</td><td class='idLab' style='display:none'>" + laboratorio.IdLab + "</td></tr>");
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

function mostrarExamenes(a) {
    var $row = $(a).closest("tr"); // Find the row
    var idSeccion = $row.find(".idLab").text();
    cantidadExamenesByLab(idSeccion);
}

function cantidadExamenesByLab(idLab) {
    $.ajax({
        type: "post",
        data: "examenes=1&idLab=" + idLab,
        datatype: "json",
        success: function (mensaje) {
            var dato = JSON.parse(mensaje);
            var examenes = dato.laboratorio[0];
            $("#lblCantidadExamenes").text("Exámenes: "+examenes.CountExamenes);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}