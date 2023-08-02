function validaDatos(){
    //let codigo = $("#txtCodigo").val();
    let nombre = $("#txtDescripcion").val();
    let validar = true;
    /*
    if (codigo.length <= 0) {
        $("#txtCodigo").addClass("is-invalid");
        handlerMessageError("Código no debe estar vacío");
        validar = false;
    }*/
    if (nombre.length <= 0) {
        $("#txtDescripcion").addClass("is-invalid");
        handlerMessageError("Nombre no debe estar vacío");
        validar = false;
    }
    return validar;
}

$("#btnAgregar").click(function (){
    if (validaDatos()){
        let data = createArrayData();
        $.ajax({
            type: "POST",
            url: "api/diagnostico/insert",
            data: JSON.stringify(data),
            async: false,
            contentType: "application/json",
            success: function(json){
                if (json.status === 200){
                    handlerMessageOk(json.message);
                    limpiarFormulario();
                    filtrarDiagnosticos();
                } else {
                    handlerMessageError(json.message);
                }
            },
            error: function (msg) {
                console.log(msg);
                handlerMessageError("Error al guardar: " + msg );
            }
        });
    }
});

$("#btnUpdate").click(function (){
	$('#btnLimpiar').removeClass('disabled');
    if (validaDatos()){
        let data = createArrayData();
        $.ajax({
            type: "PUT",
            url: "api/diagnostico/update",
            data: JSON.stringify(data),
            async: false,
            contentType: "application/json",
            success: function(json){
                if (json.status === 200){
                    handlerMessageOk(json.message);
                   // $(".formDiagnosticos").prop("disabled", true);
                    filtrarDiagnosticos();
                    //limpiarFormulario();
                } else {
                    handlerMessageError(json.message);
                }
                $('.selectpicker').selectpicker('refresh');
            },
            error: function (msg) {
                console.log(msg);
                handlerMessageError("Error al guardar: " + msg );
            }
        });
    }
});

function createArrayData(){
    let cfgDiagnosticos = {
        "cdIddiagnostico": $("#txtIdDiagnostico").val() || 0,
	"cdDescripcion": $("#txtDescripcion").val().toUpperCase(),
	"cdActivo": $("#checkBoxActivo").is(":checked") ? "S" : "N",
	"cdHost": $("#txtHost").val(),
        "cdSort": $("#numSort").val(),
        "ldvCie10": $("#selectCie10").val() != 0 ? {
            ldvcieCodigocie10: $("#selectCie10").val()
        } : null
    };
    return cfgDiagnosticos;
}
/*
$("#txtCodigo").keyup(function () {
    if ($("#txtCodigo").val().length > 0) {
        $("#txtCodigo").removeClass("is-invalid");
    }
});
*/
$("#txtDescripcion").keyup(function () {
    if ($("#txtDescripcion").val().length > 0) {
        $("#txtDescripcion").removeClass("is-invalid");
    }
});