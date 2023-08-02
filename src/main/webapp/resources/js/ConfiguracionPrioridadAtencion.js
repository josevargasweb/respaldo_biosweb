$(document).ready(function () {
    $(".ocultar").hide();
    $('#colorpicker').farbtastic('#color');
    darNumerosPrioridad();
    var avatar1 = new KTImageInput('kt_image_5');
});

$("#spanCheck").click(function () {
    if ($("#checkBoxLeverCromp").is(":checked")) {
        $("#lblComprobanteOVoucher").text("Activo");
        $("#txtEstado").val("S");
    }
    if (!($("#checkBoxLeverCromp").is(":checked"))) {
        $("#lblComprobanteOVoucher").text("");
        $("#txtEstado").val("N");
    }
});

function darNumerosPrioridad() {
    for (var i = 0, max = 20; i <= max; i++) {
        $("#NivelPrioIngreso").append("<option value=" + i + ">" + i + "</option>");
    }
}

$("#busqueda").click(function () {
    var codigo = $('#codigoBusqueda').val();
    var nombre = $('#nombreBusqueda').val();

    if (codigo === "") {
        busquedaPorNombre(nombre);
    }
});

function busquedaPorNombre(nombre) {
    $.ajax({
        type: "post",
        data: "BusquedaPrioAteNombre=" + nombre,
        datatype: "json",
        success: function (ordenes) {

            var ordenes = JSON.parse(ordenes);


            if (ordenes === "0") {
                alert('agrega algo');
                $('#tablaPrioridadAtencionBody').empty();
            } else {
                var contador = 1;

                $('#tablaPrioridadAtencionBody').empty();
                ordenes.ordenesTotales.forEach(function (orden) {
                    $("#tablaPrioridadAtencionBody").append("<tr onclick='rellenarEdicionPorTabla(this)'> class='pointer'><th >" + contador + "</th><td class='nombre'>" + orden.codigo + "</td><td class='id'>" + orden.prioAtencion + "</td> </tr>");
                    contador++;
                });


            }

        },
        error: function (msg) {
            console.log(msg);
        }
    });
}
function busquedaPoridParaRellenarDatos(id) {
    alert(id)
    $.ajax({
        type: "post",
        data: "BusquedaPrioAteidRellenarDatos=" + id,
        datatype: "json",
        success: function (a) {

            var ordenes = JSON.parse(a);
            console.table(ordenes)
            var prioridadFinal = ordenes.Prioridad[0];
            console.table(prioridadFinal)

            $("#codigoIngreso").val();
            $("#nombreIngreso").val(prioridadFinal.descripcion);
            $("#NivelPrioIngreso").val(prioridadFinal.prioAtencion);
            $("#ordenIngreso").val();
            $("#color").val(prioridadFinal.color);
            $(".image-input-wrapper").css("background-image", "url(data:image/jpeg;base64," + prioridadFinal.imagen + ")");
            $('.selectpicker').selectpicker('refresh');




        },
        error: function (msg) {
            console.log(msg);
        }
    });
}
function rellenarEdicionPorTabla(a) {

    var $row = $(a).closest("tr"); // Find the row
    //Paciente
    var id = $row.find(".id").text(); // Find the text
    busquedaPoridParaRellenarDatos(id);


}
function clean(){
    $("#codigoBusqueda").val("");
    $("#nombreBusqueda").val("");
    $('#tablaPrioridadAtencionBody').empty();
}

