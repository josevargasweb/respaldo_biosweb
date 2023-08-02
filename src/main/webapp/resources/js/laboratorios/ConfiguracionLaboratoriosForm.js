function validaDatos(){
    let codigo = $("#txtCodigo").val();
    let nombre = $("#txtDescripcion").val();
    let validar = true;
    
    if (codigo.length <= 0) {
        $("#txtCodigo").addClass("is-invalid");
        handlerMessageError("Código no debe estar vacío");
        validar = false;
    }
    if (nombre.length <= 0) {
        $("#txtDescripcion").addClass("is-invalid");
        handlerMessageError("Nombre no debe estar vacío");
        validar = false;
    }
    return validar;
}

$("#btnAgregarLab").click(function (){
    if (validaDatos()){
        let data = createArrayData();
        $.ajax({
            type: "POST",
            url: "api/laboratorio/insert",
            data: JSON.stringify(data),
            async: false,
            contentType: "application/json",
            success: function(json){
                if (json.status === 200){
                    handlerMessageOk(json.message);
                    limpiarFormulario();
                    filtrarLike();
                } else {
                    handlerMessageError(json.message);
                }
            },
            error: function (msg) {
                console.log(msg.responseText);
                handlerMessageError("Error. No se pudo guardar laboratorio.");
            }
        });
    }
});

$("#btnUpdateLab").click(function (){
    if (validaDatos()){
        let data = createArrayData();
        data.clabSort = $('#numSort').val();
        $.ajax({
            type: "PUT",
            url: "api/laboratorio/update",
            data: JSON.stringify(data),
            async: false,
            contentType: "application/json",
            success: function(json){
                if (json.status === 200){
                    handlerMessageOk(json.message);
                    $(".formLab").prop("disabled", true);
                    $('#btnLimpiar').removeClass('disabled');
                    filtrarLike();
	                $(".selectpicker").selectpicker("refresh");
                   // limpiarFormulario();
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

function createArrayData(){
    let cfgLaboratorios = {
        "clabIdlaboratorio": $("#txtIdLab").val() || 0,
        "clabIdCentroSalud": $("#selectCentroSalud").val(),
	"clabCodigo": $("#txtCodigo").val().toUpperCase(),
	"clabDescripcion": $("#txtDescripcion").val().toUpperCase(),
	"clabActivo": $("#checkBoxActivo").is(":checked") ? "S" : "N",
	"clabPreinforme": $("#chkPreInformes").is(":checked") ? "S" : "N",
	"clabHost": $("#txtHost").val(),
	"clabDerivador": $("#chkDerivador").is(":checked") ? "S" : "N",
	//"clabImagenologia": $("#chkImagenologia").is(":checked") ? "S" : "N",
        "clabSort": $("#txtSort").val()
    };
    return cfgLaboratorios;
}

$("#txtCodigo").keyup(function () {
    if ($("#txtCodigo").val().length > 0) {
        $("#txtCodigo").removeClass("is-invalid");
    }
});

$("#txtDescripcion").keyup(function () {
    if ($("#txtDescripcion").val().length > 0) {
        $("#txtDescripcion").removeClass("is-invalid");
    }
});