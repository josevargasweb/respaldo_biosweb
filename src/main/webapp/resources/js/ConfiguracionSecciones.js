$(document).ready(function () {
    $(".ocultar").hide();
    filtrarLike("", "");
    console.log("ready")
});

$("#iLimpiarFiltroSeccion").click(function (){
	console.log("pase por limpiar filtro")
	$("#txtCodigoFiltro").val(" ");
	$("#txtDescripcionFiltro").val(" ");
	 filtrarLike("", "");
})



$("#txtCodigoFiltro").keyup(function () {
	console.log("codigofiltro")
    filtrarLike($("#txtCodigoFiltro").val().trim(), "");
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtDescripcionFiltro").prop("disabled", true);
    } else {
        $("#txtDescripcionFiltro").prop("disabled", false);
    }
});

$("#txtDescripcionFiltro").keyup(function () {
	console.log("descripcion filtro")
    filtrarLike("", $("#txtDescripcionFiltro").val().trim());
    if ($("#txtDescripcionFiltro").val().length > 0) {
        $("#txtCodigoFiltro").attr("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
    }
});

function filtrarLike(codigo, descripcion) {
	console.log("filtrar like")
    $.ajax({
        type: "post",
        data: "filtro=1&codigo=" + codigo + "&descripcion=" + descripcion,
        datatype: "json",
        success: function (mensaje) {
	
            var cont = 0;
            var dato = JSON.parse(mensaje);
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            console.log("datos")
            console.log(dato);
            if (dato.seccion.length > 0) {
                dato.seccion.forEach(function (seccion) {
                    cont++;
                   // $("#tbodyFiltro").append("<tr class='pointer' onclick='selectMetodo(this)' onmouseover='mostrarExamenes(this)' ><th class='row mx-auto'>" + cont + "</th><td class=''>" + seccion.CodigoSeccion + "</td><td class=''>" + seccion.DescripcionSeccion + "</td><td class='idSeccion' style='display:none'>" + seccion.IdSeccion + "</td></tr>");
               	$("#tbodyFiltro").append("<tr class='pointer' onclick='selectMetodo(this)' onmouseover='mostrarExamenes(this)' ><th class='row mx-auto'>" + cont + "</th><td class=''>" + seccion.CodigoSeccion + "</td><td class=''>" + seccion.DescripcionSeccion + "</td><td class='idSeccion' style='display:none'>" + seccion.IdSeccion + "</td></tr>");
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
    var idSeccion = $row.find(".idSeccion").text();
    cantidadExamenesBySeccion(idSeccion);
}

function cantidadExamenesBySeccion(idSeccion) {
    $.ajax({
        type: "post",
        data: "examenes=1&idSeccion=" + idSeccion,
        datatype: "json",
        success: function (mensaje) {
            var dato = JSON.parse(mensaje);
           
            var examenes = dato.seccion[0];
            $("#lblCantidadExamenes").text("Exámenes: "+examenes.CountExamenes);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}


function selectMetodo(datos){
	console.log("entre a seleccionar metodo");
	console.log(datos)
}


