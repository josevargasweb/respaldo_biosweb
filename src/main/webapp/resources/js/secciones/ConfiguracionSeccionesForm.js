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

$("#btnAgregarSeccion").click(function (){
    if (validaDatos()){
        let data = createArrayData();
        $.ajax({
            type: "POST",
            url: "api/seccion/save",
            data: JSON.stringify(data),
            async: false,
            contentType: 'application/json',
            success: function (json) {
                if (json.status === 200){
                    if(json.message == 'Ya existe'){
                        handlerMessageWarning('El código ingresado ya existe');
                    }else{
                        handlerMessageOk(json.message);
                    }
                    limpiarFormulario();
                    filtrarLike();
                } else {
                    handlerMessageError(json.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                handlerMessageError("Error.");
            }
        });
    }
});

$("#btnUpdateSeccion").click(function (){
    if (validaDatos()){
        let data = createArrayData();
        $.ajax({
            type: "PUT",
            url: "api/seccion/update",
            data: JSON.stringify(data),
            async: false,
            contentType: 'application/json',
            success: function (json) {
                if (json.status === 200){
                    if(json.message == 'Ya existe'){
                        handlerMessageWarning('El código ingresado ya existe');
                    }else{
                        handlerMessageOk(json.message);
                    }
                    filtrarLike();
                } else {
                    handlerMessageError(json.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                handlerMessageError("Error.");
            }
        });
    }
});

function createArrayData(){
    let cfgSecciones = {
        "csecIdseccion": $("#txtIdSeccion").val() || 0,
        "csecCodigo": $("#txtCodigo").val().toUpperCase(),
        "csecDescripcion": $("#txtDescripcion").val().toUpperCase(),
        "csecActiva": $("#checkBoxActivo").is(":checked") ? "S" : "N",
        "csecSort": $("#numSort").val(),
        "cfgLaboratorios": {"clabIdlaboratorio": $("#selectLab").val()}
    };
    return cfgSecciones;
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