$(document).ready(function (){
    $(".ocultar").hide();
    /*if (getWithExpiry('idUsuario') !== null) {
        idUsuario = getWithExpiry('idUsuario');
        $("#txtIdUsuario").text(idUsuario);
    }*/
});

$('form').submit(function () {
    event.preventDefault();
    let idUsuario = $("#txtIdUsuario").val();
    let passwordActual = $("#txtPasswordActual").val();
    let passwordNueva = $("#txtPasswordNueva").val();
    let passwordConfirm = $("#txtPasswordConfirm").val();
    let formValid = true;
    
    if (passwordActual !== ""){
        $.ajax({
            type: "POST",
            url: getctx + "/api/usuario/passwordCoincide/" + passwordActual + "/" + idUsuario,
            async: false,
            success: function (message) {
                if (message === 'N'){
                    $.notify({ message: "Contraseña actual incorrecta" }, { type: "danger" });
                    formValid = false;
                }
            }
        });
    } else {
        $.notify({ message: "Debe ingresar su password actual" }, { type: "danger" });
        formValid = false;
    }
    
    if (passwordNueva === ""){
        $.notify({ message: "Debe ingresar password nueva" }, { type: "danger" });
        formValid = false;
    } else if (passwordConfirm === ""){
        $.notify({ message: "Debe confirmar password nueva" }, { type: "danger" });
        formValid = false;
    } else if (passwordNueva !== passwordConfirm){
        $.notify({ message: "Contraseñas no coinciden" }, { type: "danger" });
        formValid = false;
    } else if (passwordActual === passwordNueva){
        $.notify({ message: "La contraseña nueva no debe ser la misma que la actual" }, { type: "danger" });
        formValid = false;
    }
    
    
    //valida espacios n  
    let espacios = false;
    let cont = 0;

    while (!espacios && (cont < passwordNueva.length)) {
      if (passwordNueva.charAt(cont) == " ")
        espacios = true;
      cont++;
    }

    if (espacios) {
        $.notify({ message: "Su contraseña no puede contener espacios en blanco" }, { type: "danger" });
        formValid = false;
    }
    /*
    if (passwordNueva.length < 8){
        $.notify({ message: "Su contraseña debe contener al menos 8 caracteres" }, { type: "danger" });
        formValid = false;
    }
*/
    if (formValid === true){
        $.ajax({
            type: "PUT",
            url: getctx + "/api/usuario/actualizaPassword/" + passwordNueva + "/" + idUsuario,
            async: false,
            success: function (json) {
                if (json.status === 200){
                    handlerMessageOk(json.message);
                } else {
                    handlerMessageError(json.message);
                }
            }
        });
    }
});

//CheckBox mostrar contraseña
$('#showPassword1').click(function () {
    if ($('#txtPasswordActual').attr('type') === "password"){
        $('#txtPasswordActual').attr('type','text');
        $('.icon1').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
    } else {
        $('#txtPasswordActual').attr('type','password');
        $('.icon1').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
    }
});

$('#showPassword2').click(function () {
    if ($('#txtPasswordNueva').attr('type') === "password"){
        $('#txtPasswordNueva').attr('type','text');
        $('.icon2').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
    } else {
        $('#txtPasswordNueva').attr('type','password');
        $('.icon2').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
    }
});

$('#showPassword3').click(function () {
    if ($('#txtPasswordConfirm').attr('type') === "password"){
        $('#txtPasswordConfirm').attr('type','text');
        $('.icon3').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
    } else {
        $('#txtPasswordConfirm').attr('type','password');
        $('.icon3').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
    }
});