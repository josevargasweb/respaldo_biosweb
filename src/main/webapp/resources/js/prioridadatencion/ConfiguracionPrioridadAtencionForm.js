$("#btnAgregar").click(function () {
    let formData = new FormData();
    let data = createArrayData();
    formData.append("data", JSON.stringify(data));
    let imagen = $('input[name=imagen]')[0].files[0];
    let nombre = $('#txtDescripcion').val();
    let nivelPrioridad = $('#numPrioridad').val();
    
    if (typeof imagen === 'undefined'){
        if ($('.image-input').css('background-image') != 'none') {
            formData.append('imagen', new File([""], "conservarFoto.txt", {type: "text/plain"}));
            console.log('Conservar foto');
        } else {
            formData.append('imagen', new File([""], "sinFoto.txt", {type: "text/plain"}));
        }
    } else {
        formData.append('imagen', imagen);
    }
    if(nombre == "" || nombre === null){
		handlerMessageError("Falta nombre");
		return;
	}
	if(nivelPrioridad == "" || nivelPrioridad === null){
		handlerMessageError("Falta nivel de prioridad");
		return;
	}
    
    $.ajax({
        type: "POST",
        url: "api/prioridadatencion/insert",
        data: formData,
        async: false,
        //contentType: "application/json",
        success: function (json) {
            if (json.status === 200){
                handlerMessageOk(json.message);
                limpiarFormulario();
                filtrarBusqueda();
            } else {
                handlerMessageError(json.message);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            handlerMessageError("Error al guardar.");
        },
        contentType: false, // NEEDED, DON'T OMIT THIS
        processData: false // NEEDED, DON'T OMIT THIS
    });
});

$("#btnUpdate").click(function () {
    let formData = new FormData();
    let data = createArrayData();
    formData.append("data", JSON.stringify(data));
    let imagen = $('input[name=imagen]')[0].files[0];
    
    console.log(imagen)
    
    if (typeof imagen === 'undefined'){
        if ($('.image-input-wrapper').css('background-image') != 'none') {
            formData.append('imagen', new File([""], "conservarFoto.txt", {type: "text/plain"}));
            console.log('Conservar foto');
        } else {
            formData.append('imagen', new File([""], "sinFoto.txt", {type: "text/plain"}));
            console.log("sin foto ")
        }
    } else {
        formData.append('imagen', imagen);
    }
    
    $.ajax({
        type: "POST",
        url: "api/prioridadatencion/update",
        data: formData,
        async: false,
        contentType: false, // NEEDED, DON'T OMIT THIS
        processData: false, // NEEDED, DON'T OMIT THIS
        success: function (json) {
			$('#btnLimpiar').removeClass('disabled');
            if (json.status === 200){
                handlerMessageOk(json.message);
                //limpiarFormulario();
                filtrarBusqueda();
            } else {
                handlerMessageError(json.message);
            }
            $('.selectpicker').selectpicker('refresh');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            handlerMessageError("Error al actualizar.")
        }
    });
});

function createArrayData(){
    let cfgPrioridadatencion = {
        "cpaIdprioridadatencion": $("#txtIdPrioridad").val() || 0,
	"cpaDescripcion": $("#txtDescripcion").val().toUpperCase(),
	"cpaActivo": $("#checkBoxActivo").is(":checked") ? "S" : "N",
	"cpaSort": $("#numOrdenIngreso").val() || 1,
	//"cpaHost": $("#txtHost").val(),
	"cpaPrioridad": $("#numPrioridad").val(),
	"cpaColorprioridad": $("#txtColor").val()
    };
    return cfgPrioridadatencion;
}

$("#txtDescripcion").blur(function () {
    if ($("#txtDescripcion").val() !== null && $("#txtDescripcion").val().trim() !== "") {
        comprobarCodigoExiste($("#txtDescripcion").val().toUpperCase());
    }
});
    
function comprobarCodigoExiste(codigo){
    $.ajax({
        type: "get",
        url: getctx + "/api/existePrioridad/"+codigo,
        datatype: "json",
        success: function(json){
            if (json.status === 200){
                handlerMessageError(json.message);
                busquedaPoridParaRellenarDatos(json.dato.cpaIdprioridadatencion);
            }
        },
        error: function (msg){
            handlerMessageError(msg);
        }
    });
}