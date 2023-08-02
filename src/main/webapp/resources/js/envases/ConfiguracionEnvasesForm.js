$("#btnAgregarEnvase").click(function () {
	if($("#txtCodigo").val().length < 1 && $("#txtDescripcion").val().length < 1 && $("#selectTipoMuestra").val() == 0){
		handlerMessageError("Faltan datos");
		$("#txtCodigo").addClass('is-invalid');
		$("#txtDescripcion").addClass('is-invalid');
		$("#selectTipoMuestra").addClass('is-invalid');
		
		return;
	}
    let formData = new FormData();
    let data = createArrayData();
    formData.append("data", JSON.stringify(data));
    
 	let imagen = $('input[name=imagenEnvase]')[0].files[0];
   
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
    
    $.ajax({
        type: "POST",
        url: "api/envase/insert",
        data: formData,
        async: false,
        //contentType: "application/json",
        success: function (json) {
            if (json.status === 200){
                handlerMessageOk(json.message);
                limpiarFormulario();
                filtrarEnvases();
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




$("#btnUpdateEnvase").click(function () {
    let formData = new FormData();
    let data = createArrayData();
    data.cenvVolumenml = $('#numVolTotal').val();
    formData.append("data", JSON.stringify(data));
    console.log(data)
    let imagen = $('input[name=imagenEnvase]')[0].files[0];
  //let imagen = $('input[name=fotoEnvaseWrapper]')[0].files[0];
    console.log("imagen")
    console.log(imagen)
   
    
    if (typeof imagen === 'undefined'  ){
        if ($('#fotoEnvaseWrapper').css('background-image') != 'none') {	
            formData.append('imagen', new File([""], "conservarFoto.txt", {type: "text/plain"}));
            console.log('Conservar foto');
            console.log($('#fotoEnvaseWrapper'))
        } else {
            formData.append('imagen', new File([""], "sinFoto.txt", {type: "text/plain"}));
        }
    } else {
        formData.append('imagen', imagen);
    }
    console.log(formData)
    $.ajax({
        type: "POST",
        url: "api/envase/update",
        data: formData,
        async: false,
        contentType: false, // NEEDED, DON'T OMIT THIS
        processData: false, // NEEDED, DON'T OMIT THIS
        success: function (json) {
            if (json.status === 200){
                handlerMessageOk(json.message);
               // limpiarFormulario();
                filtrarEnvases();
            } else {
                handlerMessageError(json.message);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            handlerMessageError("Error al actualizar.")
        }
    });
});

function createArrayData(){
    let cfgEnvases = {
        "cenvIdenvase": $("#txtIdEnvase").val() || 0,
	"cenvCodigo": $("#txtCodigo").val().toUpperCase(),
	"cenvDescripcion": $("#txtDescripcion").val().toUpperCase(),
	"cenvIdtipomuestra": $("#selectTipoMuestra").val(),
	"cenvActivo": $("#checkBoxActivo").is(":checked") ? "S" : "N",
	"cenvSort": $("#txtOrden").val() || 1,
	"cenvHost": $("#txtHost").val(),
	"cenvVolumenml": $("#numVolTotal").val(),
	"cenvVolumenutilmicrolt": $("#numVolUtil").val()
    };
    return cfgEnvases;
}


$("#imagenEnvase").change(function () {	
	console.log("entre na cambiar imagen")
    let imgFoto = this.files[0];
    if(imgFoto && this.files[0].type.indexOf("image") != -1) {
        let size = this.files[0].size;
        let imgFotoUsuario = new Image();
        let objectUrl = URL.createObjectURL(imgFoto);
        $("#imgUsuario").attr("src", objectUrl);
        $("#imgUsuario").css("display", "block");
        $("#eliminaFotoUsuario").show();
        imgFotoUsuario.src = objectUrl;
    } else {
        handlerMessageError('Error en archivo o no es una imágen válida.');
    }
});