$('form').submit(function () {
    event.preventDefault();
    let formRegistro = $("#formRegistroUsuario").serializeArray();
    let dataObj = fillDataForm(formRegistro);
    let validaData = validaDatos(dataObj);
    let validarUsername = validarDatosUsername();
    
    if (validaData === true && validarUsername === true){
        let dtoUsuariosPerfiles = createArrayData(dataObj);
        let formData = new FormData();
        formData.append('data', JSON.stringify(dtoUsuariosPerfiles)); 
        
        let accesosUsuario = [];
        
        $(".checkModulo:checkbox:checked").each(function() {
            console.log($(this).val());
            accesosUsuario.push($(this).val());
        });
        
        formData.append('accesosUsuario', accesosUsuario);
        
        let duFoto = $('input[name=fotoUsuario]')[0].files[0];
        
        if (typeof duFoto === 'undefined'){
            formData.append('foto', new File([""], "sinFoto.txt", {type: "text/plain"}));
        } else {
            formData.append('foto', duFoto);
        }
        
        let cpuUsuariofirma = $('input[name=firmaUsuario]')[0].files[0];
        
        if (typeof cpuUsuariofirma === 'undefined'){
            formData.append('firma', new File([""], "sinFirma.txt", {type: "text/plain"}));
        } else {
            formData.append('firma', cpuUsuariofirma);
        }
        
        $.ajax({
            url: getctx + "/api/usuario/save",
            data: formData,
            type: "POST",
            success: function(){
                handlerMessageOk("Se registró el Usuario satisfactoriamente");
                cargarListaUsuarios();
                limpiarFormulario();
            },
            error: function (msg) {
                console.log(msg);
                handlerMessageError("Error al guardar: " + msg );
            },
            contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
            processData: false // NEEDED, DON'T OMIT THIS
            
        });
        
    }
 
});

$("#btnUpdate").click(function() {
    event.preventDefault();
    let formRegistro = $("#formRegistroUsuario").serializeArray();
    let dataObj = fillDataForm(formRegistro);
    let validaData = validaDatos(dataObj);
    
    if (validaData === true){
        let dtoUsuariosPerfiles = createArrayData(dataObj);
        let formData = new FormData();
        formData.append('data', JSON.stringify(dtoUsuariosPerfiles)); 
        
        let accesosUsuario = [];
        
        $(".checkModulo:checkbox:checked").each(function() {
            //console.log($(this).val());
            accesosUsuario.push($(this).val());
        });
        
        formData.append('accesosUsuario', accesosUsuario);
        
        let duFoto = $('input[name=fotoUsuario]')[0].files[0];
        
        if (typeof duFoto === 'undefined'){
            if ($('#fotoUsuarioWrapper').css('background-image') != 'none') {
                formData.append('foto', new File([""], "conservarFoto.txt", {type: "text/plain"}));
                console.log('Conservar foto');
            } else {
                formData.append('foto', new File([""], "sinFoto.txt", {type: "text/plain"}));
                console.log('Sin foto');
            }
        } else {
            formData.append('foto', duFoto);
        }
        
        let cpuUsuariofirma = $('input[name=firmaUsuario]')[0].files[0];
        
        if (typeof cpuUsuariofirma === 'undefined'){
            if ($('#firmaUsuarioWrapper').css('background-image') != 'none') {
                formData.append('firma', new File([""], "conservarFirma.txt", {type: "text/plain"}));
                console.log('Conservar firma');
            } else {
                formData.append('firma', new File([""], "sinFirma.txt", {type: "text/plain"}));
                console.log('Sin firma');
            }
        } else {
            formData.append('firma', cpuUsuariofirma);
        }

        $.ajax({
            type: "POST",
            url: getctx + "/api/usuario/update",
            data: formData,
            success: function(){
                handlerMessageOk("Se modificaron los datos satisfactoriamente");
                cargarListaUsuarios();
                deshabilitarCampos();  
                $("#divActualizarBtn").css("display", "none");  
                $("#circuloEditarUsuario").addClass("bg-hover-blue");
                $("#divAgregarBtn").show();
                $("#divBtnCambioPassword").hide();
                $('.selectpicker').selectpicker('refresh');
                
            },
            error: function (msg) {
                handlerMessageError("Error al guardar")
                //$.notify({ message: "Error al guardar: " + msg }, { type: "danger" });
            },
            contentType: false, 
            processData: false
        });
        
    }
    
});

function fillDataForm (form){
    let obj = {};
    $(form).each(function(i, field){
        obj[field.name] = field.value;
        //console.log(field.name + ": "+field.value);
    }); 
    return obj;
}

function validaDatos(data){
    let validado = true;
    const pattern = new RegExp('^[A-ZÁÉÍÓÚÑ \u00D1 \u00F1 ]+$', 'i');
    if (data['duRun'].trim() === ""){
        $("#txtRun").addClass('is-invalid');
        ui.imprimirTooltip("#txtRun","Rut inválido");
        validado = false;
    }
    if (data['duNombres'].trim() === "" || (data['duNombres'].trim().length > 0 && data['duNombres'].trim().length < 2)){
        $("#txtNombre").addClass('is-invalid');
        ui.imprimirTooltip("#txtNombre","Mínimo dos caracteres");
        validado = false;
    }
    if (!pattern.test(data['duNombres']) && data['duNombres'].trim() !== ""){
        $("#txtNombre").addClass('is-invalid');
        ui.imprimirTooltip("#txtNombre","Nombre debe contener solo letras");
        validado = false;
    }
    if (data['duUsuario'].trim()=== ""){
        $("#txtUsuario").addClass('is-invalid');
         ui.imprimirTooltip("#txtUsuario","Nombre de usuario no debe estar vacío");
        validado = false;
    }

    if (!pattern.test(data['duUsuario']) && data['duUsuario'].trim()!== ""){
        $("#txtUsuario").addClass('is-invalid');
        ui.imprimirTooltip("#txtUsuario","Nombre de usuario debe contener solo letras");
        validado = false;
    }else if(pattern.test(data['duUsuario']) && data['duUsuario'].trim()!== ""){
        ui.limpiarTooltip("#txtUsuario");
    }
    if (data['duPrimerapellido'].trim()=== "" || (data['duPrimerapellido'].trim().length > 0 && data['duPrimerapellido'].trim().length < 2)){
        $("#txtPrimerApellido").addClass('is-invalid');
        ui.imprimirTooltip("#txtPrimerApellido","Mínimo dos caracteres");
        validado = false;
    }
    if (data['cargoUsuario'] == 0 || data['cargoUsuario'] == null){
        $("#selectCargo").addClass("is-invalid").selectpicker("setStyle");
        $('.selectpicker').selectpicker('refresh');
        ui.imprimirTooltip("#selectCargo","Cargo no debe estar vacío");
        validado = false;
    }
    //if (data['duPassword']){
        if (data['duPassword'] === ""){
            $("#txtPassword").addClass('is-invalid');
            $('.selectpicker').selectpicker('refresh');
            ui.imprimirTooltip("#txtPassword","Contraseña no debe estar vacía");
            validado = false;
        }
   // }

   if(validado == false){
    if ($("#spanDatosBasicos").length === 0) {
        let spanDatosBasicos = `<span id="spanDatosBasicos"  class="label label-danger font-weight-bold label-inline ocultar">
        <i class="fa la-exclamation-circle text-white"></i></span>`;
        $("#spanDatosBasicos").css('display', 'inline-block');
        $("#anchorRegistro").append(spanDatosBasicos);
      }
}else{
    if ($("#spanDatosBasicos").length > 0) {
        $("#spanDatosBasicos").remove();
      }
}
    return validado;
    
}

function validarDatosUsername(){
    let validado = true;
    console.log($("#txtUsuario").val());
    if ( $("#txtUsuario").val().trim() != "" && comprobarUsername($("#txtUsuario").val())){
        $("#txtUsuario").addClass('is-invalid');
         ui.imprimirTooltip("#txtUsuario","Usuario ya registrado");
        validado = false;
    }
    return validado;
}

function createArrayData(dataObj){
    
    let run = dataObj['duRun'].toUpperCase().replace(/\./g, "");
    let nombres = dataObj['duNombres'].toString().toUpperCase();
    let username = dataObj['duUsuario'].toString().toUpperCase();
    let apellido1 = dataObj['duPrimerapellido'].toString().toUpperCase();
    let apellido2 = dataObj['duSegundoapellido'].toString().toUpperCase();
    
    let arrayData = {};
    
    arrayData = {
        "datosUsuarios" : {
            "duIdusuario": dataObj['idUsuario'],
            "duUsuario": username,
            "duRun": run,
            "duNombres": nombres,
            "duPrimerapellido": apellido1,
            "duSegundoapellido": apellido2,
            "duActivo": dataObj['duActivo']==="on" ? "S" : "N",
            //"duPassword": dataObj['duPassword'],
            "duFechacaducapassword": dataObj['duFechacaducapassword'],
            //"duFechaultimaconexion": dataObj['duFechaultimaconexion'],
            "duEmail": dataObj['duEmail'],
            "duPasswordexpira": dataObj['duPasswordexpira']==="on" ? "S" : "N"
        },
        "cfgPerfilesusuarios": {
            "cpuIdusuario": dataObj['idUsuario'],
            //"cpuProfesion": dataObj['cpuProfesion'],
            "cpuProfesion": null,
            "cpuPrefijoprofesion": dataObj['cpuPrefijoprofesion'],
            "cpuIdseccion": dataObj['seccUsuario']!==null ? dataObj['seccUsuario'] : 0,
            "cpuIdlaboratorio": dataObj['labUsuario']!==null ? dataObj['labUsuario'] : 0,
            "cpuUsuariorestringido": dataObj['cpuUsuariorestringido']==="on" ? "S" : "N",
            "cpuHost": dataObj['cpuHost'],
            "cpuHostmicro": dataObj['cpuHostmicro'],
            "cpuIdprocedencia": dataObj['procUsuario'],
            "cpuFirmaexamenes": dataObj['cpuFirmaexamenes']==="on" ? "S" : "N",
            "cpuFirmaexaseleccionados": null,
            //"cpuFlebotomista": dataObj['cargoUsuario'] == 4 ? "S" : "N",
            "cpuFlebotomista": dataObj['cpuFlebotomista']==="on" ? "S" : "N",
            "cpuAutorizaexamenes": dataObj['cpuAutorizaexamenes']==="on" ? "S" : "N",
            "cpuEnviaresultadosemail": dataObj['cpuEnviaresultadosemail']==="on" ? "S" : "N",
            "cpuUsuariocalidad": null,
            "cpuEliminarexamenes": dataObj['cpuEliminarexamenes']==="on" ? "S" : "N",
            "cpuQuitarautorizacion": dataObj['cpuQuitarautorizacion']==="on" ? "S" : "N",
            "cpuRegistraexamenesderivados": dataObj['cpuRegistraexamenesderivados']==="on" ? "S" : "N",
            "cpuProcedenciarestringida": null,
            "cpuPideautorizeditorden": dataObj['cpuPideautorizeditorden']==="on" ? "S" : "N",
            "cpuImprimeconveniorestringido": null,
            "cpuEditadatospaciente": dataObj['cpuEditadatospaciente']==="on" ? "S" : "N",
            "cpuRecepcionxexamen": dataObj['cpuRecepcionxexamen']==="on" ? "S" : "N",
            "cpuEditasoloordsprocedencia": dataObj['cpuEditasoloordsprocedencia']==="on" ? "S" : "N",
            "cpuEditaresultadoscriticos": dataObj['cpuEditaresultadoscriticos']==="on" ? "S" : "N"
        },
        "ldvCargosusuarios": {
            "ldvcuIdcargo": dataObj['cargoUsuario']
        },
        "ldvSexo": {
            "ldvsIdsexo": dataObj['sexoUsuario']!=0 ? dataObj['sexoUsuario'] : 3
        },
        "cfgCentrosdesalud": {
            "ccdsIdcentrodesalud" : dataObj['centroUsuario']
        },
        "ldvProfesionesusuarios": {
            "ldvpuIdprofesion": dataObj['profesionUsuario']!=0 ? dataObj['profesionUsuario'] : null
        },
        "password": dataObj['duPassword'] != "" ? dataObj['duPassword'] : null
    }; 
    console.log(arrayData);
    
    return arrayData;
    
}

/********************** FUNCIONES DE PASSWORD *******************************************/
//CheckBox mostrar contraseña
$('#showPassword').click(function () {
    if ($('#txtPassword').attr('type') === "password"){
        $('#txtPassword').attr('type','text');
        $('.icon').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
    } else {
        $('#txtPassword').attr('type','password');
        $('.icon').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
    }
});
/*****************************/

$("#txtRun").rut({ formatOn: 'keyup', validateOn: 'blur' }).on('rutInvalido', function() {
    if ($("#txtRun").val().length > 0) {
      $("#txtRun").removeClass('is-valid');
      $("#txtRun").addClass('is-invalid');
      $(".disabledForm").prop("disabled", true);
      $('.selectpicker').selectpicker('refresh');
      $.notify({ message: "RUN inválido" }, { type: "danger" });
    } else {
      $("#txtRun").removeClass('is-invalid');
      $(".disabledForm").prop("disabled", false);
      $('.selectpicker').selectpicker('refresh');
    }
  }).on('rutValido', function() {
    $("#txtRun").removeClass('is-invalid');
    $(".disabledForm").prop("disabled", false);
    $('.selectpicker').selectpicker('refresh');
  });

$("#txtRun").blur(function() {
  if (!chequearClase("#txtRun", "is-invalid")) {
    if ($("#txtRun").val() !== null && $("#txtRun").val().trim() !== "") {
      comprobarRunUsuarioExiste($("#txtRun").val());
    }
  }
});

$("#txtRun").keyup(function(input) {
  if (input.which === 13) {
    if ($("#txtRun").val() !== null && $("#txtRun").val().trim() !== "") {
      comprobarRunUsuarioExiste($("#txtRun").val());
    }
  }
  input.preventDefault();
});

$("#txtUsuario").blur(function() {
    // if (!chequearClase("#txtUsuario", "is-invalid")) {
    //     if ($("#txtUsuario").val() !== null && $("#txtUsuario").val().trim() !== "") {
    //         comprobarUsernameExiste($("#txtUsuario").val());
    //     }
    // } 
});

$("#txtUsuario").keyup(function(input) {
    if (input.which === 13) {
        if ($("#txtUsuario").val() !== null && $("#txtUsuario").val().trim() !== "") {
            comprobarUsernameExiste($("#txtUsuario").val());
        } 
    } 
    input.preventDefault();
});

/***************** REMOVER CLASS IS-INVALID CAMPOS OBLIGATORIOS ************/
$("#txtRun").keyup(function () {
    if ($("#txtRun").val().trim().length > 0) {
        $("#txtRun").removeClass('is-invalid');
    }
});
$("#txtNombre").keyup(function () {
    if ($("#txtNombre").val().trim().length > 0) {
        $("#txtNombre").removeClass('is-invalid');
    }
    $(".titleNombre").text($("#txtNombre").val().toUpperCase());
});
$("#txtPrimerApellido").keyup(function () {
    if ($("#txtPrimerApellido").val().trim().length > 0) {
        $("#txtPrimerApellido").removeClass('is-invalid');
    }
    $(".titleApellido").text($("#txtPrimerApellido").val().toUpperCase());
});
$("#txtUsuario").keyup(function () {
    if ($("#txtUsuario").val().trim().length > 0) {
        $("#txtUsuario").removeClass('is-invalid');
    }
});
$("#txtPassword").keyup(function () {
    if ($("#txtPassword").val().trim().length > 0) {
        $("#txtPassword").removeClass('is-invalid');
    }
});
$("#selectCargo").change(function(e) {
    if ($("#selectCargo").val() > 0) {
        e.stopPropagation();
        $(this)
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
        $(this).parent('.bootstrap-select').removeClass('is-invalid');
        $('.selectpicker').selectpicker('refresh');
    }
});
/**********************************************************************/
/*
fotoUsuario.onchange = evt => {
  const [file] = fotoUsuario.files
  if (file) {
    imgUsuario.style.display = 'block';
    imgUsuario.src = URL.createObjectURL(file)
  }
}
*/
/*
firmaUsuario.onchange = evt => {
  let validaFirma = false;
  const [file] = firmaUsuario.files
  
  console.log("width: "+file.width)
  
  
  if (validaFirma) {
    imgFirmaUsuario.style.display = 'block';
    imgFirmaUsuario.src = URL.createObjectURL(file)
  }
}
*/

$("#fotoUsuario").change(function () {	
	console.log("entre a cambiar imagen")
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

$("#firmaUsuario").change(function () {
    let imgFirma = this.files[0];
    
    if(imgFirma && this.files[0].type.indexOf("image") != -1) {
        let size = this.files[0].size;
        let imgFirmaUsuario = new Image();
        let objectUrl = URL.createObjectURL(imgFirma);
        imgFirmaUsuario.onload = function () {
            console.log(this.width + " " + this.height);
            // Compara peso (tamaño), alto y ancho
            if(this.width !== 300 && this.height !== 180) {
                $("#confirmok").modal();
                $("#confirmok .btn-si").click(function(){
                    $('#confirmok').modal('hide');
                });
                $("#firmaUsuarioWrapper").css("background-image", "");
            } 
        };
        imgFirmaUsuario.src = objectUrl;
    } else {
        handlerMessageError('Error en archivo o no es una imágen válida.');
    }
});

function comprobarRunUsuarioExiste(run){
    $.ajax({
        type: "get",
        url: getctx + "/api/usuario/run/"+run.toUpperCase().replace(/\./g, ""),
        datatype: "json",
        success: function(usr){
            console.log(usr.status)
            if (usr.status == 200){
                handlerMessageError("RUN ya registrado");
                buscarPorId(usr.dato.duIdusuario);
            }
        },
        error: function (msg){
            console.log(msg);
        }
    });
}

function comprobarUsername(username){
    var validado = false;

    
    var jqXHR = $.ajax({
        url: getctx + "/api/usuario/username/"+username.toString().toUpperCase(),
        datatype: "json",
        async: false,
    });

    if(jqXHR.responseJSON.status == 200){
        validado = true;
    }
    return validado;
}

function comprobarUsernameExiste(username){
    $.ajax({
        type: "get",
        url: getctx + "/api/usuario/username/"+username.toString().toUpperCase(),
        datatype: "json",
        success: function(usr){
            if (usr.status == 200){
                handlerMessageError("Usuario ya registrado")
                $("#txtUsuario").addClass('is-invalid');
                if ($('#btnAgregar').is(':visible')) {
                    $("#btnAgregar").prop("disabled", true);
                }
                if ($('#btnUpdate').is(':visible')) {
                    $("#btnUpdate").prop("disabled", true);
                }
            } else {
                if ($('#btnAgregar').is(':visible')) {
                    $("#btnAgregar").prop("disabled", false);
                }
                if ($('#btnUpdate').is(':visible')) {
                    $("#btnUpdate").prop("disabled", false);
                }
            }
        },
        error: function (msg){
            console.log(msg);
        }
    });
}