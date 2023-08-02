function validarContactoEdadMinima() {
    if (
        $("#txtNombreContacto").val().trim() !== "" &&
        $("#txtTelefonoContacto").val().trim() !== ""
    ) {
        return true;
    }
    return false;
}

function validarTabContacto() {
    if (
        ($("#txtNombreContacto").val().trim() === "" &&
            $("#txtTelefonoContacto").val().trim() === "") ||
        ($("#txtNombreContacto").val().trim() !== "" &&
            $("#txtTelefonoContacto").val().trim() !== "")
    ) {
        return true;
    }
    handlerMessageError(
        "Nombre y fono contacto ambos obligatorios, en caso de estar definido contacto."
    );
    return false;
}

function validarEdadMinima() {
    let valorSelectTipoDocumento = $("#selectTipoDocumento").val();
    if (valorSelectTipoDocumento === TipoDocSinIdentificacion) {
        return true;
    }
    let today = new Date();
    let year = today.getFullYear();
    let yearPaciente = $("#txtFechaNacimiento").val().substring(6, 10);

    if (validarFechaNacimientoMenorFechaActual() === false) {
        return false;
    }
    let anio = year - yearPaciente;
    return anio <= 14;
}

function validarEdadMaxima() {
    let valorSelectTipoDocumento = $("#selectTipoDocumento").val();
    if (valorSelectTipoDocumento === TipoDocSinIdentificacion) {
        return true;
    }
    let today = new Date();
    let year = today.getFullYear();
    let yearPaciente = $("#txtFechaNacimiento").val().substring(6, 10);

    if (validarFechaNacimientoMenorFechaActual() === false) {
        return false;
    }
    let anio = year - yearPaciente;
    return anio <= 150;
}




function validarContactos() {
    let valorSelectTipoDocumento = $("#selectTipoDocumento").val();
    if (valorSelectTipoDocumento === TipoDocSinIdentificacion) {
        return true;
    }
    if (validarEdadMinima()) {
        if (!validarContactoEdadMinima()) {
            // handlerMessageError(
            //     "Contacto es obligatorio para pacientes con 14 años o menos",
            // );
            return false;
        } else if (!validarTabContacto()) {
            return false;
        }
        return true;
    }
}

function validarGuardar() {
    $("#selectTipoDocumento").removeClass("is-invalid");
    $("#txtRutMadre").removeClass("is-invalid");
    $("#txtNombre").removeClass("is-invalid");
    $("#txtPrimerApellido").removeClass("is-invalid");
    $("#txtFechaNacimiento").removeClass("is-invalid");
    $("#selectSexo").removeClass("is-invalid").selectpicker("setStyle");
    if(typeof $("#txtNombreContacto").attr('data-custom-class') !== 'undefined'){
        ui.limpiarTooltip("#txtNombreContacto");
    }
    if(typeof $("#txtTelefonoContacto").attr('data-custom-class') !== 'undefined'){
        ui.limpiarTooltip("#txtTelefonoContacto");
    }

    if (validarDatosObligatorios() === false) {
        setInputVisualEstado();
//        handlerMessageError("Faltan datos obligatorios");
        return false;
    }

    setInputVisualEstado();

    // if (validarContactos() === false) {
    //     $("#txtNombreContacto").addClass("is-invalid");
    //     $("#txtTelefonoContacto").addClass("is-invalid");
    //     return false;
    // }
    if(validarFechaNacimientoNotEmpty() === false){
        return false;
    }

    if (validarFechaNacimiento() === false) {
        $("#txtFechaNacimiento").addClass("is-invalid");
        return false;
    }
   
    if (validarFormatos() === false) {
        handlerMessageError("Formato inválido");
        return false;
    }

    if (validarMultipara() === false) {
        // handlerMessageError("Debe indicar número de hijos");
        return false;
    }

    if (validarLargos() === false) {
        // handlerMessageError("Debe ingresar al menos dos letras.");
        return false;
    }

    if (validarLargoCodigoPostal() === false) {
        // handlerMessageError("El codigo postal excede el largo permitido");
        return false;
    }

    return true;
}


function validarPacienteByRut(rutPaciente) {
    rutPaciente.replace(".", "");
    $.ajax({
        type: "post",
        data: "rutFiltro=" + rutPaciente,
        datatype: "json",
        success: function (pacientes) {
            try {
                var infoPaciente = JSON.parse(pacientes);

                var paciente = infoPaciente.pacientes; //PACIENTE QUE SE RETORNO DESDE JAVA
                if (paciente === undefined) {
                    $("#txtRutMadre").removeClass("is-invalid");
                    $("#btnAgregarPaciente").prop("disabled", false);
                    $("#btnUpdatePaciente").prop("disabled", false);
                } else if (paciente.Rut === "0") {
                    console.log(paciente);
                    $("#divFeedbackRutMadre").text(
                        "RUN no esta registrado como paciente"
                    );
                    $("#txtRutMadre").addClass("is-invalid");
                    $("#btnAgregarPaciente").prop("disabled", true);
                    $("#btnUpdatePaciente").prop("disabled", true);
                } else {
                }
            } catch (e) {
                console.log(e);
                handlerMessageError(e);
            }
        },
        error: function (msg) {
            console.log(msg);
            handlerMessageError(msg);
        },
    });
}

function validarFechaNacimiento() {
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1;
    let day = today.getDate();

    let yearPaciente = parseInt(
        $("#txtFechaNacimiento").val().substring(6, 10)
    );
    let monthPaciente = parseInt(
        $("#txtFechaNacimiento").val().substring(3, 5)
    );
    let dayPaciente = parseInt($("#txtFechaNacimiento").val().substring(0, 2));

    if (yearPaciente > year) {
        handlerMessageError(
            "Fecha de nacimiento no puede ser mayor que la fecha de hoy."
        );
        return false;
    } else if (yearPaciente == year) {
        if (monthPaciente > month) {
            handlerMessageError(
                "Fecha de nacimiento no puede ser mayor que la fecha de hoy."
            );
            return false;
        } else if (monthPaciente == month) {
            if (dayPaciente > day) {
                handlerMessageError(
                    "Fecha de nacimiento no puede ser mayor que la fecha de hoy."
                );
                return false;
            }
        }
    }
    
    if (!validarEdadMaxima()){
                handlerMessageError(
                    "Edad excede máximo permitido."
                );
                return false;
    }
    
    let valorSelectTipoDocumento = $("#selectTipoDocumento").val();
    let fechaNacimiento = $("#txtFechaNacimiento").val();
    if (
        fechaNacimiento.length <= 0 &&
        valorSelectTipoDocumento !== TipoDocSinIdentificacion
    ) {
        $("#txtFechaNacimiento").addClass("is-invalid");
        // handlerMessageError("Debe ingresar Fecha de nacimiento.");
        return false;
    }

    return true;
}

function validarFechaNacimientoNotEmpty() {
    
    let menorEdad = false;
    let sinError = true;

    if($("#selectTipoDocumento").val() == 5){
        return sinError;
    }

    if($("#txtFechaNacimiento").val() !== ""){
        let edad = getAge($("#txtFechaNacimiento").val(), 3);
        let array = edad.split("-");
        if(array[0] < 14){
            menorEdad = true;
        }
    }

    if(menorEdad && $("#txtTelefonoContacto").val() === "" && $("#txtNombreContacto").val() === ""){
        $("#txtNombreContacto").addClass("is-invalid");
        $("#txtTelefonoContacto").addClass("is-invalid");
        if(typeof $("#txtNombreContacto").attr('data-custom-class') === 'undefined'){
            ui.imprimirTooltip("#txtNombreContacto", "Para menores de 14 años debe registrar contacto");
        }
        if(typeof $("#txtTelefonoContacto").attr('data-custom-class') === 'undefined'){
            ui.imprimirTooltip("#txtTelefonoContacto", "Para menores de 14 años debe registrar contacto");
        }
        sinError = false;
    }
    else if(menorEdad &&  $("#txtNombreContacto").val() !== "" && $("#txtTelefonoContacto").val() === ""){
        $("#txtTelefonoContacto").addClass("is-invalid");
        if(typeof $("#txtTelefonoContacto").attr('data-custom-class') === 'undefined'){
            ui.imprimirTooltip("#txtTelefonoContacto", "Debe ingresar teléfono de contacto");
        }
        sinError = false;
    }
    else if(menorEdad &&  $("#txtTelefonoContacto").val() !== "" && $("#txtNombreContacto").val() === ""){
        $("#txtNombreContacto").addClass("is-invalid");
        if(typeof $("#txtNombreContacto").attr('data-custom-class') === 'undefined'){
            ui.imprimirTooltip("#txtNombreContacto", "Debe ingresar nombre de contacto");
        }
        sinError = false;
    }
    else if(!menorEdad &&  $("#txtNombreContacto").val() !== "" && $("#txtTelefonoContacto").val() === ""){
        $("#txtTelefonoContacto").addClass("is-invalid");
        if(typeof $("#txtTelefonoContacto").attr('data-custom-class') === 'undefined'){
            ui.imprimirTooltip("#txtTelefonoContacto", "Debe ingresar teléfono de contacto");
        }
        sinError = false;
    }else if(!menorEdad &&  $("#txtTelefonoContacto").val() !== "" && $("#txtNombreContacto").val() === ""){
        $("#txtNombreContacto").addClass("is-invalid");
        if(typeof $("#txtNombreContacto").attr('data-custom-class') === 'undefined'){
            ui.imprimirTooltip("#txtNombreContacto", "Debe ingresar nombre de contacto");
        }
        sinError = false;
    }else{
        if(typeof $("#txtNombreContacto").attr('data-custom-class') !== 'undefined'){
            ui.limpiarTooltip("#txtNombreContacto");
        }
        $("#txtNombreContacto").removeClass("is-invalid");
        if(typeof $("#txtTelefonoContacto").attr('data-custom-class') !== 'undefined'){
            ui.limpiarTooltip("#txtTelefonoContacto");
        }
        $("#txtTelefonoContacto").removeClass("is-invalid");
    }
    return sinError;
}



function validarFechaNacimientoMenorFechaActual() {
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1;
    let day = today.getDate();

    let yearPaciente = parseInt(
        $("#txtFechaNacimiento").val().substring(6, 10)
    );
    let monthPaciente = parseInt(
        $("#txtFechaNacimiento").val().substring(3, 5)
    );
    let dayPaciente = parseInt($("#txtFechaNacimiento").val().substring(0, 2));

    if (yearPaciente > year) {
        handlerMessageError(
            "Fecha de nacimiento no puede ser mayor que la fecha de hoy."
        );
        return false;
    } else if (yearPaciente == year) {
        if (monthPaciente > month) {
            handlerMessageError(
                "Fecha de nacimiento no puede ser mayor que la fecha de hoy."
            );
            return false;
        } else if (monthPaciente == month) {
            if (dayPaciente > day) {
                handlerMessageError(
                    "Fecha de nacimiento no puede ser mayor que la fecha de hoy."
                );
                return false;
            }
        }
    }
    return true;
}

function validarDatosObligatorios() {
    let emailRegex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    let bRpta = false;
    let bRptaFinal = true;
    bRptaFinal = validarContacto();
    let valorSelectTipoDocumento = $("#selectTipoDocumento").val();
    let rut = $("#txtRut").val();
    let dni = $("#txtDni").val();
    let pasaporte = $("#txtPasaporte").val();
    let rutMadre = $("#txtRutMadre").val();
    let nombres = $("#txtNombre").val();
    let primerApellido = $("#txtPrimerApellido").val();
    let valorSexo = $("#selectSexo").val();
    let fechaNacimiento = $("#txtFechaNacimiento").val();
    let partoMultiple = $("#txtNroPartoMultiple").val();

    let email = $("#txtEmail").val();
    if(email.trim() !== ""){
        bRpta = emailRegex.test(email.trim());
        bRptaFinal = bRptaFinal && bRpta;
        gMapCamposObligatorios.set("#txtEmail", bRpta);
    }

    bRpta = nombres.trim() !== "";
    bRptaFinal = bRptaFinal && bRpta;
    gMapCamposObligatorios.set("#txtNombre", bRpta);
    bRpta = primerApellido.trim() !== "";
    bRptaFinal = bRptaFinal && bRpta;
    gMapCamposObligatorios.set("#txtPrimerApellido", bRpta);
    bRpta = valorSexo !== "" && valorSexo !== null && valorSexo !== "N";
    bRptaFinal = bRptaFinal && bRpta;
    gMapCamposObligatorios.set("#selectSexo", bRpta);
    gMapCamposObligatorios.set("#txtRut", true);
    gMapCamposObligatorios.set("#txtPasaporte", true);
    gMapCamposObligatorios.set("#txtRutMadre", true);
    gMapCamposObligatorios.set("#txtDni", true);
    switch (valorSelectTipoDocumento) {
        case TipoDocRun:
            bRpta = fechaNacimiento !== "" && fechaNacimiento !== null;
            bRptaFinal = bRptaFinal && bRpta;
            gMapCamposObligatorios.set("#txtFechaNacimiento", bRpta);
            bRpta = rut.trim() !== "";
            bRptaFinal = bRptaFinal && bRpta;
            gMapCamposObligatorios.set("#txtRut", bRpta);
            break;
        case TipoDocPasaporte:
            bRpta = fechaNacimiento !== "" && fechaNacimiento !== null;
            bRptaFinal = bRptaFinal && bRpta;
            gMapCamposObligatorios.set("#txtFechaNacimiento", bRpta);
            bRpta = pasaporte.trim() !== "";
            bRptaFinal = bRptaFinal && bRpta;
            gMapCamposObligatorios.set("#txtPasaporte", bRpta);
            break;
        case TipoDocRecienNacido:
            bRpta = fechaNacimiento !== "" && fechaNacimiento !== null;
            bRptaFinal = bRptaFinal && bRpta;
            gMapCamposObligatorios.set("#txtFechaNacimiento", bRpta);
            bRpta = rutMadre.trim() !== "";
            bRptaFinal = bRptaFinal && bRpta;
            gMapCamposObligatorios.set("#txtRutMadre", bRpta);
            if($("#checkboxPartoMultiple").val() == "S"){
                bRpta =  $("#txtNroPartoMultiple").is(":not(:disabled)") && partoMultiple.trim() !== "" && partoMultiple <= 10;
                bRptaFinal = bRptaFinal && bRpta;
                gMapCamposObligatorios.set("#txtNroPartoMultiple", bRpta);
            }
            break;
        case TipoDocSinIdentificacion:
            bRpta = true;
            gMapCamposObligatorios.set("#txtFechaNacimiento", bRpta);
            break;
        case TipoDocDni:
            bRpta = fechaNacimiento !== "" && fechaNacimiento !== null;
            bRptaFinal = bRptaFinal && bRpta;
            console.log('bRptaFinal', bRptaFinal,bRpta,fechaNacimiento);
            gMapCamposObligatorios.set("#txtFechaNacimiento", bRpta);
            bRpta = dni.trim() !== "";
            bRptaFinal = bRptaFinal && bRpta;
            gMapCamposObligatorios.set("#txtDni", bRpta);

            break;
        default:
            bRptaFinal = false;
            break;
    }
    return bRptaFinal;
}

function setInputVisualEstado() {
    let menorEdad = false;
    $(".disabledForm").prop("disabled", false);
    $(".selectpicker").selectpicker("refresh");

    if (!gMapCamposObligatorios.get("#txtNombre")) {
        $("#txtNombre").addClass("is-invalid");
        $("#txtNombre").removeClass("is-disabledForm");
        $("#txtNombre").removeAttr("disabled");
        ui.imprimirTooltip("#txtNombre", "Mínimo dos caracteres");
    } else {
        $("#txtNombre").removeClass("is-invalid");
        ui.limpiarTooltip("#txtNombre");
    }
    if (!gMapCamposObligatorios.get("#txtRutMadre") && $("#txtNroPartoMultiple").prop("disabled")) {
        $("#txtRutMadre").addClass("is-invalid");
        ui.imprimirTooltip("#txtRutMadre", "Rut inválido");
        if (
            $("#liDatosMadres").is(":visible") && !gMapCamposObligatorios.get("#txtRutMadre") &&
            $("#txtNroPartoMultiple").prop("disabled") && $("#checkboxPartoMultiple").val() == "N" &&
            $("#spanDatosMadre").length == 0
        ) {
           let spanDatosMadre = `<span id="spanDatosMadre"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanDatosMadre").css('display', 'inline-block');
            $("#liDatosMadres").find('a').append(spanDatosMadre);
        }
    }else if(!gMapCamposObligatorios.get("#txtRutMadre") &&  $("#txtNroPartoMultiple").is(":not(:disabled)") && $("#checkboxPartoMultiple").val() == "S"){
        
        let error = false;
        $("#txtRutMadre").addClass("is-invalid");
        ui.imprimirTooltip("#txtRutMadre", "Rut inválido");
        if ($("#checkboxPartoMultiple").val() == "S") {
            let txtNroPartoMultiple = document.querySelector("#txtNroPartoMultiple");
            txtNroPartoMultiple.classList.add("is-invalid");
            // $("#txtNroPartoMultiple").addClass("is-invalid");
            ui.imprimirTooltip(
                "#txtNroPartoMultiple",
                "Parto múltiple inválido"
            );
        } else {
            $("#txtNroPartoMultiple").removeClass("is-invalid");
        }

        if (
            $("#liDatosMadres").is(":visible") && !gMapCamposObligatorios.get("#txtRutMadre") &&
            $("#txtNroPartoMultiple").is(":not(:disabled)") && $("#checkboxPartoMultiple").val() == "S" &&
            $("#spanDatosMadre").length == 0
        ) {
           let spanDatosMadre = `<span id="spanDatosMadre"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanDatosMadre").css('display', 'inline-block');
            $("#liDatosMadres").find('a').append(spanDatosMadre);
        }
    }  
    else if(gMapCamposObligatorios.get("#txtRutMadre") &&  $("#txtNroPartoMultiple").is(":not(:disabled)") && $("#checkboxPartoMultiple").val() == "S"){
        
        let error = false;
        ui.imprimirTooltip("#txtRutMadre", "Rut inválido");
        if ($("#checkboxPartoMultiple").val() == "S") {
            let txtNroPartoMultiple = document.querySelector("#txtNroPartoMultiple");
            txtNroPartoMultiple.classList.add("is-invalid");
            // $("#txtNroPartoMultiple").addClass("is-invalid");
            ui.imprimirTooltip(
                "#txtNroPartoMultiple",
                "Parto múltiple inválido"
            );
        } else {
            $("#txtNroPartoMultiple").removeClass("is-invalid");
        }

        if (
            $("#liDatosMadres").is(":visible") &&
            $("#txtNroPartoMultiple").is(":not(:disabled)") && $("#checkboxPartoMultiple").val() == "S" &&
            $("#spanDatosMadre").length == 0
        ) {
           let spanDatosMadre = `<span id="spanDatosMadre"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanDatosMadre").css('display', 'inline-block');
            $("#liDatosMadres").find('a').append(spanDatosMadre);
        }
    }  
    else {
        $("#txtRutMadre").removeClass("is-invalid");
        ui.limpiarTooltip("#txtRutMadre");
        $("#spanDatosMadre").remove();
    }
    if (!gMapCamposObligatorios.get("#txtPrimerApellido")) {
        $("#txtPrimerApellido").addClass("is-invalid");
        $("#txtPrimerApellido").removeClass("is-disabledForm");
        $("#txtPrimerApellido").removeAttr("disabled");
        ui.imprimirTooltip("#txtPrimerApellido", "Mínimo dos caracteres");
    } else {
        $("#txtPrimerApellido").removeClass("is-invalid");
    }
    if (!gMapCamposObligatorios.get("#selectSexo")) {
        $("#selectSexo").addClass("is-invalid").selectpicker("setStyle");
    } else {
        $("#selectSexo").removeClass("is-invalid").selectpicker("setStyle");
    }
    if (
        !gMapCamposObligatorios.get("#txtFechaNacimiento") &&
        $("#selectTipoDocumento").val() !== "SIN IDENTIFICACION"
    ) {
        $("#txtFechaNacimiento").addClass("is-invalid");
        $("#txtFechaNacimiento").removeClass("is-disabledForm");
        $("#txtFechaNacimiento").removeAttr("disabled");
    } else {
        $("#txtFechaNacimiento").removeClass("is-invalid");
    }

    if (!gMapCamposObligatorios.get("#txtRut")) {
        $("#txtRut").addClass("is-invalid");
        ui.imprimirTooltip("#txtRut", "Rut inválido");
    }
    if (!gMapCamposObligatorios.get("#txtPasaporte")) {
        $("#txtPasaporte").addClass("is-invalid");
    } else {
        $("#txtPasaporte").removeClass("is-invalid");
    }
  
    if (!gMapCamposObligatorios.get("#txtDni")) {
        $("#txtDni").addClass("is-invalid");
    } else {
        $("#txtDni").removeClass("is-invalid");
    }
    

    if (!$("#txtNroPartoMultiple").prop("disabled")) {
        if (!gMapCamposObligatorios.get("#txtNroPartoMultiple")) {
            $("#txtNroPartoMultiple").addClass("is-invalid");
            ui.imprimirTooltip(
                "#txtNroPartoMultiple",
                "Parto múltiple inválido"
            );
            if (
                $("#liDatosMadres").is(":visible") &&
                !$("#txtNroPartoMultiple").prop("disabled") &&
                $("#spanDatosMadre").css("display") !== "inline-block"
            ) {
                $("#spanDatosMadre").css("display", "inline-block");
            }
        } else {
            $("#txtNroPartoMultiple").removeClass("is-invalid");
        }
    } else {
        $("#txtNroPartoMultiple").removeClass("is-invalid");
        ui.limpiarTooltip("#txtNroPartoMultiple");
    }

        if($("#txtFechaNacimiento").val() !== ""){
        let edad = getAge($("#txtFechaNacimiento").val(), 3);
        let array = edad.split("-");
        if(array[0] < 14){
           menorEdad = true;
        }
    }
    if(!gMapCamposObligatorios.get("#txtNombre") || !gMapCamposObligatorios.get("#txtPrimerApellido") || !gMapCamposObligatorios.get("#selectSexo") || ( !gMapCamposObligatorios.get("#txtFechaNacimiento") &&
    $("#selectTipoDocumento").val() !== "SIN IDENTIFICACION") || !gMapCamposObligatorios.get("#txtRut") || !gMapCamposObligatorios.get("#txtPasaporte")){
        if($("#spanDatosBasicos").length === 0){
            let spanDatosBasicos = `<span id="spanDatosBasicos"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanDatosBasicos").css('display', 'inline-block');
            $("#anchorRegistro").append(spanDatosBasicos);
        }
    }else if(menorEdad && (($("#txtNombreContacto").val() === "" && $("#txtTelefonoContacto").val() === "") || ($("#txtNombreContacto").val() !== "" && $("#txtTelefonoContacto").val() === "")  || ($("#txtNombreContacto").val() === "" && $("#txtTelefonoContacto").val() !== ""))){
        if($("#spanDatosBasicos").length === 0){
            let spanDatosBasicos = `<span id="spanDatosBasicos"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanDatosBasicos").css('display', 'inline-block');
            $("#anchorRegistro").append(spanDatosBasicos);
        }
    }else if(!menorEdad && (($("#txtNombreContacto").val() !== "" && $("#txtTelefonoContacto").val() === "")  || ($("#txtNombreContacto").val() === "" && $("#txtTelefonoContacto").val() !== ""))){
        if($("#spanDatosBasicos").length === 0){
            let spanDatosBasicos = `<span id="spanDatosBasicos"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanDatosBasicos").css('display', 'inline-block');
            $("#anchorRegistro").append(spanDatosBasicos);
        }
    }else{
        $("#spanDatosBasicos").remove();
    }
}

function validarDatosNN() {}

function validarFormatos() {
    let res = true;

    if($("#selectTipoDocumento").val() == 5){
        return res;
    }

    if (validarFormatoTelefono($("#txtTelefonoContacto").val()) === false) {
        $("#txtTelefonoContacto").addClass("is-invalid");
        res = false;
    } else {
        $("#txtTelefonoContacto").removeClass("is-invalid");
    }

    if (validarFormatoTelefono($("#txtTelefono").val()) === false) {
        $("#txtTelefono").addClass("is-invalid");
        res = false;
    } else {
        $("#txtTelefono").removeClass("is-invalid");
    }

    if (validarFormatoFecha($("#txtFechaNacimiento").val()) === false) {
        $("#txtFechaNacimiento").addClass("is-invalid");
        res = false;
    } else {
        $("#txtFechaNacimiento").removeClass("is-invalid");
    }

    return res;
}

function validarContacto() {
    let res = true;
    if (($("#txtNombreContacto").val() !== "" || $("#selectPacienteRelacion").val() != null  || ($("#selectSexoContacto").val() !== "" && $("#selectSexoContacto").val() != null)) && $("#txtTelefonoContacto").val() == "") {
        $("#txtTelefonoContacto").addClass("is-invalid");
        if(typeof $("#txtTelefonoContacto").attr('data-custom-class') === 'undefined'){
            ui.imprimirTooltip("#txtTelefonoContacto", "Debe ingresar teléfono de contacto");
        }
        res = false;
    } else {
        $("#txtTelefonoContacto").removeClass("is-invalid");
        if(typeof $("#txtTelefonoContacto").attr('data-custom-class') !== 'undefined'){
            ui.limpiarTooltip("#txtTelefonoContacto");
        }
    }
    return res;
}

function validarLargoCodigoPostal() {
    let res = true;
    let codigoPostal = $("#txtCodigoPostal").val();
    if (codigoPostal !== "" && codigoPostal.length > 10) {
        $("#txtCodigoPostal").addClass("is-invalid");
        res = false;
    } else {
        $("#txtCodigoPostal").removeClass("is-invalid");
    }
    return res;
}

function validarFormatoTelefono(fonoValor) {
    let regex = /\+56 [9|2]\-\d{4}\-\d{4}/;
    if (fonoValor !== null && fonoValor !== "") {
        return regex.test(fonoValor);
    }
    return true;
}

function validarFormatoFecha(fonoValor) {
    let regex = /\d{2}\/\d{2}\/\d{4}/;
    if (fonoValor !== null && fonoValor !== "") {
        return regex.test(fonoValor);
    }
    return true;
}

function validarMultipara() {
    if (
        $("#esPartoMultiple").val() === "S" 
    ) {
        if (
            $("#txtNroPartoMultiple").val() === "" ||
            $("#txtNroPartoMultiple").val() < 1
        ) {
            $("#txtNroPartoMultiple").addClass("is-invalid");
            return false;
        }
    }
    $("#txtFechaNacimiento").removeClass("is-invalid");
    return true;
}

function validarLargos() {
    let rpta = false;
    let len = $("#txtNombre").val().length;

    if (len == 1) {
        $("#txtNombre").addClass("is-invalid");
    } else {
        $("#txtNombre").removeClass("is-invalid");
        rpta = true;
    }

    len = $("#txtPrimerApellido").val().length;
    if (len == 1) {
        $("#txtPrimerApellido").addClass("is-invalid");
        rpta = rpta && false;
    } else {
        $("#txtPrimerApellido").removeClass("is-invalid");
        rpta = rpta && true;
    }

    len = $("#txtSegundoApellido").val().length;
    if (len == 1) {
        $("#txtSegundoApellido").addClass("is-invalid");
        rpta = rpta && false;
    } else {
        $("#txtSegundoApellido").removeClass("is-invalid");
        rpta = rpta && true;
    }

    if (rpta == false) {
        return false;
    }

    if (
        $("#txtNombre").val().length >= 2 ||
        $("#txtNombre").val().length >= 2 ||
        $("#txtNombre").val().length >= 2
    ) {
        $("#txtSegundoApellido").removeClass("is-invalid");
        $("#txtPrimerApellido").removeClass("is-invalid");
        $("#txtNombre").removeClass("is-invalid");
        ui.limpiarTooltip("#txtNombre");
        ui.limpiarTooltip("#txtPrimerApellido");
        return true;
    }

    return false;
}


//valida spanBasico
function validarParaSpan() {

    if (validarDatosObligatorios() === false) {
        return false;
    }
    
    if(validarFechaNacimientoNotEmpty() === false){
        return false;
    }

    if (validarFechaNacimiento() === false) {
        return false;
    }

   
    if (validarFormatos() === false) {
        return false;
    }


    if (validarMultipara() === false) {
        return false;
    }


    if (validarLargos() === false) {
        return false;
    }


    if (validarLargoCodigoPostal() === false) {
        return false;
    }


    return true;
}
