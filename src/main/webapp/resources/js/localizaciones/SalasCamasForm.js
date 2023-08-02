function validaDatosSalas(){
    let codigo = $("#txtCodigoSala").val();
    let nombre = $("#txtNombreSala").val();
    let valid = true;
    if (codigo.trim() === ""){
        $("#txtCodigoSala").addClass('is-invalid');
        handlerMessageError("Código sala no debe estar vacío");
        valid = false;
    }
    if (nombre.trim() === ""){
        $("#txtNombreSala").addClass('is-invalid');
        handlerMessageError("Nombre sala no debe estar vacío");
        valid = false;
    }
    return valid;
}

function validaDatosCamas(){
    let codigo = $("#txtCodigoCama").val();
    let nombre = $("#txtNombreCama").val();
    let valid = true;
    if (codigo.trim() === ""){
        $("#txtCodigoCama").addClass('is-invalid');
        handlerMessageError("Código cama no debe estar vacío");
        valid = false;
    }
    if (nombre.trim() === ""){
        $("#txtNombreCama").addClass('is-invalid');
        handlerMessageError("Nombre cama no debe estar vacío");
        valid = false;
    }
    return valid;
}

$("#txtCodigoSala").keyup(function () {
    if ($("#txtCodigoSala").val().trim().length > 0 && $("#txtCodigoSala").hasClass('is-invalid')) {
        $("#txtCodigoSala").removeClass('is-invalid');
    }
});

$("#txtNombreSala").keyup(function () {
    if ($("#txtNombreSala").val().trim().length > 0 && $("#txtNombreSala").hasClass('is-invalid')) {
        $("#txtNombreSala").removeClass('is-invalid');
    }
});

$("#txtCodigoCama").keyup(function () {
    if ($("#txtCodigoCama").val().trim().length > 0 && $("#txtCodigoCama").hasClass('is-invalid')) {
        $("#txtCodigoCama").removeClass('is-invalid');
    }
});

$("#txtNombreCama").keyup(function () {
    if ($("#txtNombreCama").val().trim().length > 0 && $("#txtNombreCama").hasClass('is-invalid')) {
        $("#txtNombreCama").removeClass('is-invalid');
    }
});

function createJsonSalas(){
    let SalasServiciosDTO = JSON.stringify({
        "css_IDSALASERVICIO": $("#idSala").val(),
        "css_CODIGOSALA": $("#txtCodigoSala").val().toUpperCase(),
        "css_DESCRIPCION": $("#txtNombreSala").val().toUpperCase(),
        "css_IDSERVICIO": $("#idServicio").val()
    });
    return SalasServiciosDTO;
}

function createJsonCamas(){
    let CamasSalasDTO = JSON.stringify({
        "ccs_IDCAMASALA": $("#idCama").val(),
        "ccs_CODIGOCAMA": $("#txtCodigoCama").val().toUpperCase(),
        "ccs_DESCRIPCION": $("#txtNombreCama").val().toUpperCase(),
        "ccs_IDSALASERVICIO": $("#idSalaCama").val()
    });
    return CamasSalasDTO;
}

$("#btnGuardarSala").click(function (){
    if (validaDatosSalas()){
        let data = createJsonSalas();
        $.ajax({
            type: "POST",
            url: getctx + "/api/localizaciones/sala/save",
            data: data,
            async: false,
            datatype: "json",
            contentType: 'application/json;',
            success: function (data) {
                if (data.status === 200){
                    handlerMessageOk(data.message);
                    getSalasServicio(data.dato.cfgServicios.csIdservicio);
                    limpiarModalSala();
                    $('#modalSalas').modal('toggle');
                } else {
                    handlerMessageError(data.message);
                }
            },
            error: function (msg) {
                console.log(msg.responseText);
                handlerMessageError("Error. No se pudo guardar sala.");
            }
        });
    }
});

$("#btnGuardarCama").click(function (){
    if (validaDatosCamas()){
        let data = createJsonCamas();
        $.ajax({
            type: "POST",
            url: getctx + "/api/localizaciones/cama/save",
            data: data,
            async: false,
            datatype: "json",
            contentType: 'application/json;',
            success: function (data) {
                if (data.status === 200){
                    handlerMessageOk(data.message);
                    getCamasSala(data.dato.cfgSalasservicios.cssIdsalaservicio);
                    limpiarModalSala();
                    $('#modalCamas').modal('toggle');
                } else {
                    handlerMessageError(data.message);
                }
            },
            error: function (msg) {
                console.log(msg.responseText);
                handlerMessageError("Error. No se pudo guardar sala.");
            }
        });
    }
});

function limpiarModalSala(){
    $("#idSala").val(0);
    $("#txtCodigoSala").val("");
    $("#txtNombreSala").val("");
    $("#idServicio").val(0);
    $("#tituloCrudSala").text("Nueva Sala");
}

function limpiarModalCama(){
    $("#idCama").val(0);
    $("#txtCodigoCama").val("");
    $("#txtNombreCama").val("");
    $("#idSala").val(0);
    $("#tituloCrudCama").text("Nueva Cama");
}

function activarModalNuevaSala(idServicio){
    limpiarModalSala();
    $("#idServicio").val(idServicio);
}

function activarModalNuevaCama(idSala){
    limpiarModalCama();
    $("#idSalaCama").val(idSala);
}

function habilitarEdicionSala(sala){
    $("#tituloCrudSala").text("Editar Sala");
    $("#idSala").val(sala.css_IDSALASERVICIO);
    $("#idServicio").val(sala.css_IDSERVICIO);
    $("#txtCodigoSala").val(sala.css_CODIGOSALA);
    $("#txtNombreSala").val(sala.css_DESCRIPCION);
}

function habilitarEdicionCama(cama){
    $("#tituloCrudCama").text("Editar Cama");
    $("#idCama").val(cama.ccs_IDCAMASALA);
    $("#idSalaCama").val(cama.ccs_IDSALASERVICIO);
    $("#txtCodigoCama").val(cama.ccs_CODIGOCAMA);
    $("#txtNombreCama").val(cama.ccs_DESCRIPCION);
}

function eliminarSala(idSala){
    alert("Desea eliminar sala con id " +idSala +"?")
}

function eliminarCama(idCama){
    alert("Desea eliminar cama con id " +idCama +"?")
}