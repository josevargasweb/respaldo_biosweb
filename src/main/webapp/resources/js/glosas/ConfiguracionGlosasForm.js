function validaDatos(){
    let codigo = $("#txtCodigo").val();
    let glosa = $("#txtGlosa").val();
    let validar = true;
    
    if (codigo.length <= 0) {
        $("#txtCodigo").addClass("is-invalid");
        handlerMessageError("Código no debe estar vacío");
        validar = false;
    }
    if (glosa.length <= 0) {
        $("#txtGlosa").addClass("is-invalid");
        handlerMessageError("Glosa no debe estar vacía");
        validar = false;
    }
    return validar;
}

$("#btnAgregarGlosa").click(function (){
    if (validaDatos()){
        let data = createArrayData();
        $.ajax({
            type: "POST",
            url: "api/glosas/insert",
            data: JSON.stringify(data),
            async: false,
            contentType: "application/json",
            success: function (json) {
                if (json.status === 200){
                    handlerMessageOk(json.message);
                    limpiarFormulario();
                    filtrarGlosas();
                } else {
                    handlerMessageError(json.message);
                }
            },
            error: function (msg) {
                console.log(msg.responseText);
                handlerMessageError("Error. No se puedo guardar glosa");
            }
        });
    }
});

$("#btnUpdateGlosa").click(function (){
    if (validaDatos()){
        let data = createArrayData();
        $.ajax({
            type: "PUT",
            url: "api/glosas/update",
            data: JSON.stringify(data),
            async: false,
            contentType: "application/json",
            success: function (json) {
                if (json.status === 200){
                    handlerMessageOk(json.message);
                    $(".formGlosas").prop("disabled", true);
                    //limpiarFormulario();
                    filtrarGlosas();
                    $("#divBtnAgregar").show();
                    $("#btnAgregarGlosa").prop("disabled", true);
                    $("#divBtnUpdate").hide();
                    activarEdit();
                } else {
                    handlerMessageError(json.message);
                }
            },
            error: function (msg) {
                console.log(msg.responseText);
                handlerMessageError("Error. No se puedo actualizar glosa");
            }
        });
    }
});

function createArrayData(){
    let cfgGlosas = {
        "cgIdglosa": $("#txtIdGlosa").val(),
        "cgCodigoglosa": $("#txtCodigo").val(),
        "cgDescripcion": $("#txtGlosa").val(),
        "cgSort": $("#numOrden").val(),
        "cgActivo": $("#checkBoxActivo").is(":checked") ? "S" : "N",
        "cgIdSeccion": $("#selectSeccion").val(),
        "cgHost": $("#txtHost").val()
    };
    return cfgGlosas;
}

$("#txtCodigo").keyup(function () {
    if ($("#txtCodigo").val().trim().length > 0) {
        $("#txtCodigo").removeClass('is-invalid');
    }
});

$("#txtGlosa").keyup(function () {
    if ($("#txtGlosa").val().trim().length > 0) {
        $("#txtGlosa").removeClass('is-invalid');
    }
});