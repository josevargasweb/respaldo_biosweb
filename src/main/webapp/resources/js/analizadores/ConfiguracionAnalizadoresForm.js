function validaDatos(){
    let codigo = $("#txtCodigo").val();
    let nombre = $("#txtNombre").val();
    let validar = true;
    
    if (codigo.length <= 0) {
        $("#txtCodigo").addClass("is-invalid");
        handlerMessageError("Código no debe estar vacío");
        validar = false;
    }
    if (nombre.length <= 0) {
        $("#txtNombre").addClass("is-invalid");
        handlerMessageError("Nombre no debe estar vacío");
        validar = false;
    }
    return validar;
}

function createArrayData(){
    let analizadores = {
        "saIdanalizador": $("#txtIdAnalizador").val(),
        "saCodigoanalizador": $("#txtCodigo").val().toUpperCase(),
        "saDescripcion": $("#txtNombre").val().toUpperCase(),
        "saModelo": $("#txtModelo").val().toUpperCase(),
        "saEmpresa": $("#txtEmpresa").val().toUpperCase(),
        "saCodigoproceso1": $("#txtCodigoProceso1").val(),
        "saCodigoproceso2": $("#txtCodigoProceso2").val(),
        "saCodigoproceso3": $("#txtCodigoProceso3").val()
    };
    return analizadores;
}

$("#btnAgregarAnalizador").click(function (){
    if (validaDatos()){
        let dataAnalizador = createArrayData();
        
        let formData = new FormData();
        formData.append('dataAnalizador', JSON.stringify(dataAnalizador));
        
        let imagen = $('input[name=imageAnalizador]')[0].files[0];
        
        if (typeof imagen === 'undefined'){
            formData.append('imagen', new File([""], "sinFoto.txt", {type: "text/plain"}));
        } else {
            formData.append('imagen', imagen);
        }
        
        $.ajax({
            type: "POST",
            url: "api/analizador/insert",
            data: formData,
            success: function(data){
                if (data.status === 200){
                    handlerMessageOk(data.message);
                    $(".formAnalizador").prop("disabled", true);
                    limpiarFormulario();
                    listarAnalizadores();
                } else {
                    handlerMessageError(data.message);
                }
            },
            contentType: false, // NEEDED, DON'T OMIT THIS
            processData: false // NEEDED, DON'T OMIT THIS
        });
    }
});

$("#btnUpdateAnalizador").click(function (){
    if (validaDatos()){
        let dataAnalizador = createArrayData();
        
        let formData = new FormData();
        formData.append('dataAnalizador', JSON.stringify(dataAnalizador));
        
        let imagen = $('input[name=imageAnalizador]')[0].files[0];
        
        if (typeof imagen === 'undefined'){
            if ($('#imagenWrapper').css('background-image') != 'none') {
                formData.append('imagen', new File([""], "conservarFoto.txt", {type: "text/plain"}));
                console.log('Conservar foto');
            } else {
                formData.append('imagen', new File([""], "sinFoto.txt", {type: "text/plain"}));
            }
        } else {
            formData.append('imagen', imagen);
        }
        
        $.ajax({
            type: "POST",
            url: "api/analizador/update",
            data: formData,
            success: function(data){
                if (data.status === 200){
                    handlerMessageOk(data.message);
                    $(".formAnalizador").prop("disabled", true);
                    //limpiarFormulario();
                    listarAnalizadores();
                } else {
                    handlerMessageError(data.message);
                }
            },
            contentType: false, // NEEDED, DON'T OMIT THIS
            processData: false // NEEDED, DON'T OMIT THIS
        });
    }
});