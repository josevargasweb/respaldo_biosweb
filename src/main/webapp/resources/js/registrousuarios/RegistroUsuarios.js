let cargoUsuario = 0;

class UI {
    imprimirTooltip(elemento, mensaje,tipo = 'enable') {
  
      $(elemento).attr({
          "data-toggle": "tooltip",
          "data-placement": "top",
          "data-custom-class":"tooltip-warning",
          "title": mensaje,
          "data-original-title": mensaje,
        });
        $(elemento)
        .tooltip({
          template: '<div class="tooltip tooltip-danger" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',
        }).tooltip(tipo);
    }
    limpiarTooltip(elemento) {
      $(elemento).removeAttr("data-toggle data-placement data-custom-class title data-original-title");
      $(elemento).tooltip("disable");
      $(elemento).tooltip('update');
    }

    limpiarTodosTooltip(contenedorPadre){
        let contenedor = $(contenedorPadre).find("[data-custom-class='tooltip-warning']");
        contenedor.each(function(){
          let id = $(this).attr('id');
          $("#"+id).removeAttr("data-toggle data-placement data-custom-class title data-original-title");
          $("#"+id).tooltip("disable");
          $("#"+id).tooltip('update');
        })
      }
  
    normalTooltip(elemento){
      $(elemento)
        .tooltip({
          template: '<div class="tooltip tooltip-normal" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',
        });
    }
    
  }
  
  const ui = new UI();
  

$(document).ready(function(){
    $(".ocultar").hide();
    cargarListaUsuarios();
    generarAccesosUsuario(0);

    $('.selectpicker').selectpicker({
        noneSelectedText: '',
        noneResultsText: 'No hay resultados'
    });
    $('.selectpicker').selectpicker('refresh');

    $('#selectCargo').selectpicker('val', '');
    $('#selectCentro').selectpicker('val', '');
    $('#selectProfesion').selectpicker('val', '');
    
    $('.selectpicker').selectpicker('refresh');

        
});
function cargarListaUsuarios() {    
    $.ajax({
        type: "get",
        url: getctx + "/api/usuarios",
        datatype: "json",
        success: function (message) {
            var cont = 0;
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            if (message.dato.length > 0) {
                message.dato.forEach(function (user) {
                    cont++;
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='selectUsuario(this)' >\
                                                    <th class='row mx-auto'>" + cont + "</th>\
                                                    <td class=''>" + user.duUsuario + "</td>\
                                                    <td class=''>" + user.duNombres + " " + user.duPrimerapellido + "</td>\
                                                    <td class='id' style='display:none'>" + user.duIdusuario + "</td></tr>");
                });
            } else {
                $("#lblErrorFiltro").show();
            }
            $("#spanCantidad").text(cont);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
    
}

function filtrarParametro(url) {
    $.ajax({
        type: "get",
        url: url,
        datatype: "json",
        success: function (message) {
            var cont = 0;
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            if (message.dato.length > 0) {
                message.dato.forEach(function (user) {
                    cont++;
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='selectUsuario(this)' >\
                                                <th class='row mx-auto'>" + cont + "</th>\
                                                <td class=''>" + user.duUsuario + "</td>\
                                                <td class=''>" + user.duNombres + " " + user.duPrimerapellido + "</td>\
                                                <td class='id' style='display:none'>" + user.duIdusuario + "</td></tr>");
                });
            } else {
                $("#lblErrorFiltro").show();
            }
            $("#spanCantidad").text(cont);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

$("#txtNombreFiltro").keyup(function () {
    if ($("#txtNombreFiltro").val().length > 1) {
        $("#codCentroFiltro").prop("disabled", true);
        $("#codCargoFiltro").prop("disabled", true);
        let url = getctx + "/api/usuarios/nombre/" + $("#txtNombreFiltro").val().trim();
        filtrarParametro(url);
    } else {
        $("#codCentroFiltro").prop("disabled", false);
        $("#codCargoFiltro").prop("disabled", false);
        cargarListaUsuarios();
    }
    $('.selectpicker').selectpicker('refresh');
});

$("#txtNombreFiltro").blur(function () {
    if ($("#txtNombreFiltro").val().length > 1 || $("#txtNombreFiltro").val().length == 0) {
        ui.limpiarTooltip($(this));
        $(this).removeClass('is-invalid');
    } else if($("#txtNombreFiltro").val().length > 0 && $("#txtNombreFiltro").val().length < 2) {
        $(this).addClass('is-invalid');
        ui.imprimirTooltip($(this),"Mínimo dos caracteres");
    }
});


$('#chkMostrarActivos').change(function() {
	let activo = $("#chkMostrarActivos").is(":checked") ? "S" : "N";
	if(activo == 'S'){
		let url = getctx + "/api/usuarios/activo/" + activo;
		filtrarParametro(url);
	}else{
		cargarListaUsuarios();
	}
});

$("#codCentroFiltro").on('change', function () {
    if ($("#codCentroFiltro").val() > 0) {
        $("#txtNombreFiltro").prop("disabled", true);
        //$("#codCargoFiltro").prop("disabled", true);
        var codcentro = $("#codCentroFiltro").val();
        let url = getctx + "/api/usuarios/centro/" + codcentro;
        filtrarParametro(url);
    } else {
        $("#txtNombreFiltro").prop("disabled", false);
        //$("#codCargoFiltro").prop("disabled", false);
        cargarListaUsuarios();
    }
    $('.selectpicker').selectpicker('refresh');
});

$("#codCargoFiltro").on('change', function () {
    if ($("#codCargoFiltro").val() > 0) {
        $("#txtNombreFiltro").prop("disabled", true);
        //$("#codCentroFiltro").prop("disabled", true);
        var codcargo = $("#codCargoFiltro").val();
        let url = getctx + "/api/usuarios/cargo/" + codcargo;
        filtrarParametro(url);
    } else {
        $("#txtNombreFiltro").prop("disabled", false);
        //$("#codCentroFiltro").prop("disabled", false);
        cargarListaUsuarios();
    }
    $('.selectpicker').selectpicker('refresh');
});

function selectUsuario(a){
    var $row = $(a).closest("tr"); // Find the row
    var id = $row.find(".id").text();
    buscarPorId(id);
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
}

function activarEdit() {
    $("#iEditUsuario").removeClass("text-dark-50");
    $("#iEditUsuario").addClass("text-blue");
    $("#circuloEditarUsuario").addClass("bg-hover-blue");
    localStorage.setItem("botonEditar", "activo");
    $('.selectpicker').selectpicker('refresh');
}

function desactivarEdit() {
    $("#iEditUsuario").addClass("text-dark-50");
    $("#iEditUsuario").removeClass("text-white","text-blue");
    $("#circuloEditarUsuario").removeClass("bg-hover-blue");
    localStorage.setItem("botonEditar", "inactivo");
    $('.selectpicker').selectpicker('refresh');
}

function buscarPorId(id){
    generarAccesosUsuario(id);
    $.ajax({
        type: "get",
        url: getctx + "/api/usuario/" + id,
        async: false,
        datatype: "json",
        success: function (message) {
            $("#collapseHeader").collapse('hide');
            let user = message.dato; //reemplazar elementos por nombre del array json definido en controller
            deshabilitarCampos();
            $("#collapseHeader").collapse('hide');
            $("#txtRun").removeClass('is-invalid');
            $("#txtUsuario").removeClass('is-invalid');
            $("#txtNombre").removeClass('is-invalid');
            $("#txtPrimerApellido").removeClass('is-invalid');
            $("#txtPassword").removeClass('is-invalid');
            $("#selectCargo").removeClass('is-invalid');
            $("#txtID").val(user.datosUsuarios.duIdusuario);
            $("#txtNombre").val(user.datosUsuarios.duNombres);
            $("#titleNombre").text(user.datosUsuarios.duNombres);
            $("#txtPrimerApellido").val(user.datosUsuarios.duPrimerapellido);
            $(".titleNombre").text(user.datosUsuarios.duNombres);
            $(".titleApellido").text(user.datosUsuarios.duPrimerapellido);
            $("#titleApellido").text(user.datosUsuarios.duPrimerapellido);
            $("#txtSegundoApellido").val(user.datosUsuarios.duSegundoapellido);
            cargoUsuario = user.ldvCargosusuarios!==null ? user.ldvCargosusuarios.ldvcuIdcargo : 0;
            $("#selectCargo").val(cargoUsuario);
            if (user.cfgCentrosdesalud!==null){
                $("#selectCentro").val(user.cfgCentrosdesalud.ccdsIdcentrodesalud);
                cargarLaboratorios(user.cfgCentrosdesalud.ccdsIdcentrodesalud);
                cargarProcedenciasxIDCentro(user.cfgCentrosdesalud.ccdsIdcentrodesalud);
            } else {
                $("#selectCentro").val(0);
            }
            if (user.cfgPerfilesusuarios!==null && user.cfgPerfilesusuarios.cpuIdlaboratorio > 0){
                $("#selectLab").val(user.cfgPerfilesusuarios.cpuIdlaboratorio);
                cargarSecciones(user.cfgPerfilesusuarios.cpuIdlaboratorio);
            } else {
                $("#selectLab").val(0);
            }
            $("#selectSecc").val(user.cfgPerfilesusuarios!==null ? user.cfgPerfilesusuarios.cpuIdseccion : 0);
            
            $("#selectProc").val(user.cfgPerfilesusuarios!==null ? user.cfgPerfilesusuarios.cpuIdprocedencia : 0);
            $("#txtUsuario").val(user.datosUsuarios.duUsuario);
            $("#lblPassword").html("<b>Presione el botón de editar para cambiar clave</b>");
            $("#divTxtPassword").hide();
            if (user.fotoUsuario!==null){
                $("#fotoUsuarioWrapper").css("background-image", "url(data:image/jpeg;base64," + user.fotoUsuario + ")");
            } else {
                $("#fotoUsuarioWrapper").css("background-image", "");
            }
            $("#cambiarFotoUsuario").hide();
            $("#eliminarFotoUsuario").hide();
            $("#removeFotoUsuario").hide();
            if (user.firmaUsuario!==null){
                $("#firmaUsuarioWrapper").css("background-image", "url(data:image/jpeg;base64," + user.firmaUsuario + ")");
            } else {
                $("#firmaUsuarioWrapper").css("background-image", "");
            }
            $("#cambiarFirmaUsuario").hide();
            $("#eliminarFirmaUsuario").hide();
            $("#removeFirmaUsuario").hide();
            $(".switch-content").addClass("disabled");
            user.datosUsuarios.duActivo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            if (user.datosUsuarios.duActivo === "S") {
                $("#checkBoxActivo").prop("checked", true);
                $("#lblEstado").text("Activo");
            } else {
                $("#checkBoxActivo").prop("checked", false);
                $("#lblEstado").text("Inactivo");
            }
            $('.selectpicker').selectpicker('refresh');
            /**************** PERMISOS *****************************************************/
            if (user.cfgPerfilesusuarios.cpuEditadatospaciente === "S") {
                $("#editadatospaciente").prop("checked", true);
            } else {
                $("#editadatospaciente").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuRegistraexamenesderivados === "S") {
                $("#registraexamenesderivados").prop("checked", true);
            } else {
                $("#registraexamenesderivados").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuEditasoloordsprocedencia === "S") {
                $("#editasoloordsprocedencia").prop("checked", true);
            } else {
                $("#editasoloordsprocedencia").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuPideautorizeditorden === "S") {
                $("#pideautorizeditorden").prop("checked", true);
            } else {
                $("#pideautorizeditorden").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuEliminarexamenes === "S") {
                $("#eliminarexamenes").prop("checked", true);
            } else {
                $("#eliminarexamenes").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuFlebotomista === "S") {
                $("#flebotomista").prop("checked", true);
            } else {
                $("#flebotomista").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuRecepcionxexamen === "S") {
                $("#recepcionxexamen").prop("checked", true);
            } else {
                $("#recepcionxexamen").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuFirmaexamenes === "S") {
                $("#firmaexamenes").prop("checked", true);
            } else {
                $("#firmaexamenes").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuAutorizaexamenes === "S") {
                $("#autorizaexamenes").prop("checked", true);
            } else {
                $("#autorizaexamenes").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuQuitarautorizacion === "S") {
                $("#quitarautorizacion").prop("checked", true);
            } else {
                $("#quitarautorizacion").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuEditaresultadoscriticos === "S") {
                $("#editaresultadoscriticos").prop("checked", true);
            } else {
                $("#editaresultadoscriticos").prop("checked", false);
            }
            if (user.cfgPerfilesusuarios.cpuEnviaresultadosemail === "S") {
                $("#enviaresultadosemail").prop("checked", true);
            } else {
                $("#enviaresultadosemail").prop("checked", false);
            }
            /**************** DATOS EXTRAS *****************************************************/
            $("#txtRun").val(user.datosUsuarios.duRun);
            $("#txtPrefijo").val(user.cfgPerfilesusuarios !== null ? user.cfgPerfilesusuarios.cpuPrefijoprofesion : "");
            $("#selectSexo").val(user.ldvSexo !== null ? user.ldvSexo.ldvsIdsexo : 0);
            $("#selectProfesion").val(user.ldvProfesionesusuarios !== null ? user.ldvProfesionesusuarios.ldvpuIdprofesion : 0);
            //$("#txtProfesion").val(user.cfgPerfilesusuarios !== null ? user.cfgPerfilesusuarios.cpuProfesion : "");
            $("#txtEmail").val(user.datosUsuarios.duEmail);
            $("#txtHost").val(user.cfgPerfilesusuarios !== null ? user.cfgPerfilesusuarios.cpuHost : "");
            $("#txtHostMicro").val(user.cfgPerfilesusuarios !== null ? user.cfgPerfilesusuarios.cpuHostmicro : "");
            $('.selectpicker').selectpicker('refresh');
            /**************** SEGURIDAD ******************************************************************/
            if (user.datosUsuarios.duPasswordexpira === "S") {
                $("#checkboxExpira").prop("checked", true);
            } else {
                $("#checkboxExpira").prop("checked", false);
            }
            let fcp = user.datosUsuarios.duFechacaducapassword;
            let fcpFormat = "";
            if (fcp!==null){
                let diaFcp = fcp.substring(8, 10);
                let mesFcp = fcp.substring(5, 7);
                let yearFcp = fcp.substring(0, 4);
                fcpFormat = diaFcp +"/"+mesFcp+"/"+yearFcp;
            }
            $("#txtFechaVencimiento").val(fcpFormat);
            $('.selectpicker').selectpicker('refresh');
            /**************** BOTONES AGREGAR Y EDITAR ***************************************************/
    $('.selectpicker').selectpicker('refresh');
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

$('#txtFechaVencimiento').datepicker({
    language: 'es',
    format: 'dd/mm/yyyy',
    autoclose: true
});

$("#nuevoUsuario").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarUsuario").click(function () {
    // $(".collapse").collapse('show');
    // $('html, body').animate({
    //     scrollTop: $("div#accordionExample8").offset().top
    // }, 700);
    $("#collapseHeader").collapse('show');
    $(window).scrollTop(0);
});

$("#checkBoxActivo").click(function () {
    nombreTitulo($("#txtNombre").val(),$("#txtPrimerApellido").val());
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

function nombreTitulo(nombre,apellido) {
    if (!($("#checkBoxActivo").is(":checked"))) {
        if(nombre != ""){
            $(".titleNombre").text(nombre);
            $(".titleApellido").text(apellido);
        }
        $(".titleContainer").css("color","red");
    } else {
        if(nombre != ""){
            $(".titleNombre").text(nombre);
            $(".titleApellido").text(apellido);
        }
        $(".titleContainer").css("color","black");
    }
}

$("#btnLimpiarFiltro").click(function () {
    $("#txtNombreFiltro").val("");
    $("#txtNombreFiltro").prop("disabled", false);
    $("#codCentroFiltro").val("N");
    $("#codCentroFiltro").prop("disabled", false);
    $("#codCargoFiltro").val("N");
    $("#codCargoFiltro").prop("disabled", false);
    $('.selectpicker').selectpicker('refresh');
    cargarListaUsuarios();
    $('.selectpicker').selectpicker('refresh');

});

$("#btnLimpiar").click(function () {
	//$('#btnLimpiar').addClass('disabled');
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');

});

$("#btnEditarUsuario").click(function() {
    if (
        localStorage.getItem("botonEditar") === "activo"
      ) {
            $(".switch-content").removeClass("disabled");
            $("#txtRun").prop("disabled", false);
            $("#txtRun").prop("readonly", true);
            $("#txtNombre").prop("disabled", false);
            $("#txtPrimerApellido").prop("disabled", false);
            $("#txtSegundoApellido").prop("disabled", false);
            $("#selectCargo").prop("disabled", false);
            $("#selectCentro").prop("disabled", false);
            $("#selectLab").prop("disabled", false);
            $("#selectSecc").prop("disabled", false);
            $("#selectProc").prop("disabled", false);
            $("#txtUsuario").prop("disabled", false);
            $("#txtUsuario").prop("readonly", true);
            //$("#txtPassword").prop("disabled", false);
            //$("#showPassword").prop("disabled", false);
            $("#divBtnCambioPassword").show();
            $("#btnCambiarPasword").text("Cambiar contraseña");
            //$("#divTxtPassword").show();
            $("#lblPassword").text("Clave:");
            $("#fotoUsuario").prop("disabled", false);
            $("#cambiarFotoUsuario").show();
            $("#eliminarFotoUsuario").show();
            $("#removeFotoUsuario").show();
            $("#firmaUsuario").prop("disabled", false);
            $("#cambiarFirmaUsuario").show();
            $("#eliminarFirmaUsuario").show();
            $("#removeFirmaUsuario").show();
            $("#checkBoxActivo").prop("disabled", false);
            $("#checkBoxActivo").prop("disabled", false);
            $("#txtPrefijo").prop("disabled", false);
            $("#selectSexo").prop("disabled", false);
            $("#selectProfesion").prop("disabled", false);
            $("#txtProfesion").prop("disabled", false);
            $("#txtEmail").prop("disabled", false);
            $("#txtHost").prop("disabled", false);
            $("#txtHostMicro").prop("disabled", false);
            $("#checkboxExpira").prop("disabled", false);
            $("#txtFechaVencimiento").prop("disabled", false);
            $('.selectpicker').selectpicker('refresh');
            /**************** PERMISOS *******************************************************/
            $(".checkModulo").prop("disabled", false);
            $("#editadatospaciente").prop("disabled", false);
            $("#registraexamenesderivados").prop("disabled", false);
            $("#editasoloordsprocedencia").prop("disabled", false);
            $("#pideautorizeditorden").prop("disabled", false);
            $("#eliminarexamenes").prop("disabled", false);
            $("#flebotomista").prop("disabled", false);
            $("#recepcionxexamen").prop("disabled", false);
            $("#firmaexamenes").prop("disabled", false);
            $("#autorizaexamenes").prop("disabled", false);
            $("#quitarautorizacion").prop("disabled", false);
            $("#editaresultadoscriticos").prop("disabled", false);
            $("#enviaresultadosemail").prop("disabled", false);
            /***/
            $("#divAgregarBtn").hide();
            $("#btnUpdate").removeClass("disabled");
            $("#divActualizarBtn").show();
            $("#iEditUsuario").addClass("text-dark-50");
            $("#iEditUsuario").removeClass("text-white","text-blue");
            $("#circuloEditarUsuario").removeClass("bg-hover-blue");
            $("#txtNombreFiltro").val("");
      }
    $('.selectpicker').selectpicker('refresh');

});

function limpiarFormulario(){
    $(".switch-content").removeClass("disabled inactivo");
    //generarAccesosUsuario(0);
    $("#titleNombre").text("");
    $("#titleApellido").text("");
    $("#txtID").val("");
    $("#txtRun").prop("readonly", false);
    $("#txtRun").prop("disabled", false);
    $("#txtRun").val("");
    $("#txtNombre").prop("disabled", false);
    $("#txtNombre").val("");
    $("#txtPrimerApellido").prop("disabled", false);
    $("#txtPrimerApellido").val("");
    $("#txtSegundoApellido").prop("disabled", false);
    $("#txtSegundoApellido").val("");
    cargoUsuario = 0;
    $("#selectCargo").prop("disabled", false);
    $("#selectCargo").val(cargoUsuario);
    $("#selectCentro").prop("disabled", false);
    $("#selectCentro").val(0);
    $("#selectLab").prop("disabled", false);
    $("#selectLab").val(0);
    $("#selectSecc").prop("disabled", false);
    $("#selectSecc").val(0);
    $("#selectProc").prop("disabled", false);
    $("#selectProc").val(0);
    $("#txtUsuario").prop("readonly", false);
    $("#txtUsuario").prop("disabled", false);
    $("#txtUsuario").val("");
    //PASSWORD
    $("#lblPassword").show();
    $("#divTxtPassword").show();
    $("#lblPassword").text("Clave:");
    $("#txtPassword").prop("disabled", false);
    $("#txtPassword").val("");
    $("#divBtnCambioPassword").hide();
    //$("#showPassword").prop("disabled", false);
    $("#checkBoxActivo").prop("disabled", false);
    $("#checkBoxActivo").prop("checked", true);
    //IMAGENES
    $("#fotoUsuario").val("");
    $("#fotoUsuarioWrapper").css("background-image", "");
    $("#cambiarFotoUsuario").show();
    $("#eliminarFotoUsuario").show();
    $("#removeFotoUsuario").show();
    $("#firmaUsuario").val("");
    $("#firmaUsuarioWrapper").css("background-image", "");
    $("#cambiarFirmaUsuario").show();
    $("#eliminarFirmaUsuario").show();
    $("#removeFirmaUsuario").show();
    $('.selectpicker').selectpicker('refresh');
    /**************** DATOS EXTRAS *****************************************************/
    $("#txtPrefijo").prop("disabled", false);
    $("#txtPrefijo").val("");
    $("#selectSexo").prop("disabled", false);
    $("#selectSexo").val("0");
//    $("#txtProfesion").prop("disabled", false);
//    $("#txtProfesion").val("");
    $("#selectProfesion").prop("disabled", false);
    $("#selectProfesion").val("0");
    $("#txtEmail").prop("disabled", false);
    $("#txtEmail").val("");
    $("#txtHost").prop("disabled", false);
    $("#txtHost").val("");
    $("#txtHostMicro").prop("disabled", false);
    $("#txtHostMicro").val("");
    $('.selectpicker').selectpicker('refresh');
    /**************** PERMISOS *******************************************************/
    $(".checkModulo").prop("disabled", false);
    $(".checkModulo").prop("checked", false);
    $("#editadatospaciente").prop("disabled", false);
    $("#editadatospaciente").prop("checked", false);
    $("#registraexamenesderivados").prop("disabled", false);
    $("#registraexamenesderivados").prop("checked", false);
    $("#editasoloordsprocedencia").prop("disabled", false);
    $("#editasoloordsprocedencia").prop("checked", false);
    $("#pideautorizeditorden").prop("disabled", false);
    $("#pideautorizeditorden").prop("checked", false);
    $("#eliminarexamenes").prop("disabled", false);
    $("#eliminarexamenes").prop("checked", false);
    $("#flebotomista").prop("disabled", false);
    $("#flebotomista").prop("checked", false);
    $("#recepcionxexamen").prop("disabled", false);
    $("#recepcionxexamen").prop("checked", false);
    $("#firmaexamenes").prop("disabled", false);
    $("#firmaexamenes").prop("checked", false);
    $("#autorizaexamenes").prop("disabled", false);
    $("#autorizaexamenes").prop("checked", false);
    $("#quitarautorizacion").prop("disabled", false);
    $("#quitarautorizacion").prop("checked", false);
    $("#editaresultadoscriticos").prop("disabled", false);
    $("#editaresultadoscriticos").prop("checked", false);
    $("#enviaresultadosemail").prop("checked", false);
    $("#enviaresultadosemail").prop("disabled", false);
    /**************** SEGURIDAD *******************************************************/
    $("#checkboxExpira").prop("disabled", false);
    $("#checkboxExpira").prop("checked", false);
    $("#txtFechaVencimiento").prop("disabled", true);
    $("#txtFechaVencimiento").val("");
    $('.selectpicker').selectpicker('refresh');
    /**************** ESTADO *****************************************************/
    $("#lblEstado").text("Activo");
    $('.selectpicker').selectpicker('refresh');
    /** BOTONES AGREGAR Y EDITAR**/
    $("#btnAgregar").prop("disabled", false);
    desactivarEdit();
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $(".titleNombre").text("");
    $(".titleApellido").text("");
    $(".titleContainer").css("color","black");
    ui.limpiarTodosTooltip("#panelUsuariosResultados");
    $(".is-invalid").removeClass("is-invalid");
    if ($("#spanDatosBasicos").length > 0) {
        $("#spanDatosBasicos").remove();
      }
    $("#iEditUsuario").removeClass("text-blue");
    $('.selectpicker').selectpicker('refresh');

}

function deshabilitarCampos(){
    $(".switch-content").addClass("disabled");
    $("#txtNombre").prop("disabled", true);
    $("#txtPrimerApellido").prop("disabled", true);
    $("#txtSegundoApellido").prop("disabled", true);
    $("#selectCargo").prop("disabled", true);
    $("#selectCentro").prop("disabled", true);
    $("#selectLab").prop("disabled", true);
    $("#selectSecc").prop("disabled", true);
    $("#selectProc").prop("disabled", true);
    $("#txtUsuario").prop("disabled", true);
    $("#lblPassword").html("<b>Presione el botón de editar para cambiar clave</b>");
    $("#txtPassword").prop("disabled", true);
    $("#divTxtPassword").hide();
    $("#fotoUsuario").prop("disabled", true);
    $("#firmaUsuario").prop("disabled", true);
    $("#checkBoxActivo").prop("disabled", true);
    $('.selectpicker').selectpicker('refresh');
    /**************** DATOS EXTRAS *****************************************************/
    $("#txtRun").prop("disabled", true);
    $("#txtPrefijo").prop("disabled", true);
    $("#selectSexo").prop("disabled", true);
    //$("#txtProfesion").prop("disabled", true);
    $("#selectProfesion").prop("disabled", true);
    $("#txtEmail").prop("disabled", true);
    $("#txtHost").prop("disabled", true);
    $("#txtHostMicro").prop("disabled", true);
    $('.selectpicker').selectpicker('refresh');
    /**************** PERMISOS ********************************************************/
    $(".checkModulo").prop("disabled", true);
    $("#editadatospaciente").prop("disabled", true);
    $("#registraexamenesderivados").prop("disabled", true);
    $("#editasoloordsprocedencia").prop("disabled", true);
    $("#pideautorizeditorden").prop("disabled", true);
    $("#eliminarexamenes").prop("disabled", true);
    $("#flebotomista").prop("disabled", true);
    $("#recepcionxexamen").prop("disabled", true);
    $("#firmaexamenes").prop("disabled", true);
    $("#autorizaexamenes").prop("disabled", true);
    $("#quitarautorizacion").prop("disabled", true);
    $("#editaresultadoscriticos").prop("disabled", true);
    $("#enviaresultadosemail").prop("disabled", true);
    /**************** SEGURIDAD ******************************************************************/
    $("#checkboxExpira").prop("disabled", true);
    $("#txtFechaVencimiento").prop("disabled", true);
    /**************** BOTONES AGREGAR Y EDITAR ***************************************************/
    $("#btnAgregar").prop("disabled", true);
    $('.selectpicker').selectpicker('refresh');
}

function cargarLaboratorios(idCentro){
    $.ajax({
       type: "GET",
       url: getctx + "/api/instituciones/laboratorios/list/"+idCentro,
       datatype: "json",
       async: false,
       success: function(data){
            $("#selectLab").html('<option selected disabled hidden></option>'); 
            for (let lab of data) {
                $("#selectLab").append('<option value='+lab.clabIdlaboratorio+'>'+lab.clabDescripcion+'</option>'); 
            }
            $('.selectpicker').selectpicker('refresh');

       },
       error: function (msg) {
           console.log(msg);
       } 
    });
}
function cargarProcedenciasxIDCentro(idCentro){
    $.ajax({
       type: "GET",
       url: getctx + "/api/procedencia/"+idCentro,
       datatype: "json",
       async: false,
       success: function(data){
		console.table(data.dato);
            $("#selectProc").html(''); 
            for (let lab of data.dato) {
                $("#selectProc").append('<option value='+lab.cp_IDPROCEDENCIA+'>'+lab.cp_DESCRIPCION+'</option>'); 
                
            }
            $('.selectpicker').selectpicker('refresh');
       },
       error: function (msg) {
           console.log(msg);
       } 
    });
}

function cargarSecciones(idLab){
    $.ajax({
       type: "GET",
       url: getctx + "/api/instituciones/secciones/list/"+idLab,
       datatype: "json",
       async: false,
       success: function(data){
            $("#selectSecc").html(''); 
            for (let secc of data) {
                $("#selectSecc").append('<option value='+secc.csecIdseccion+'>'+secc.csecDescripcion+'</option>'); 
            }
            $('.selectpicker').selectpicker('refresh');
       },
       error: function (msg) {
           console.log(msg);
       } 
    });
}

$("#selectCentro").on('change', function() {
    let idCentro = this.value;
    cargarLaboratorios(idCentro);
    cargarProcedenciasxIDCentro(idCentro);
    $("#selectSecc").html("");
    $.ajax({
        type: "GET",
        url: getctx + "/api/instituciones/sistemaConfiguraciones/"+idCentro,
        datatype: "json",
        async: false,
        success: function(data){
            $("#diasCaducaPwd").val(data.scPwdiascaducapassword);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
});

$("#selectLab").on('change', function() {
    let idLab = this.value;
    cargarSecciones(idLab);
    $('.selectpicker').selectpicker('refresh');
});

$("#checkboxExpira").on('change', function() {
    if ($(this).is(":checked")){
        $("#txtFechaVencimiento").prop("disabled", false);
    } else {
        $("#txtFechaVencimiento").prop("disabled", true);
        $("#txtFechaVencimiento").val("");
    }
});

$("#btnCambiarPasword").click(function(){
    if (!$("#divTxtPassword").is(":visible")){
        console.log("mostrar divTxtPassword");
        $("#divTxtPassword").show();
        $("#txtPassword").prop("disabled", false);
        $("#btnCambiarPasword").text("Cancelar");
    } else {
        $("#txtPassword").prop("disabled", true);
        $("#divTxtPassword").hide();
        $("#txtPassword").val("");
        $("#btnCambiarPasword").text("Cambiar contraseña");
    } 
});

$("#eliminaFotoUsuario").click(function(){
    $("#imgUsuario").removeAttr('src');
    $("#imgUsuario").hide();
    $("#fotoUsuario").val("");
    $("#eliminaFotoUsuario").hide();
});

$("#eliminaFirmaUsuario").click(function(){
    $("#imgFirmaUsuario").removeAttr('src');
    $("#imgFirmaUsuario").hide();
    $("#firmaUsuario").val("");
    $("#eliminaFirmaUsuario").hide();
});

$("#btnCancel").click(function() {
    deshabilitarCampos();
    $('#btnLimpiar').removeClass('disabled');
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $("#divBtnCambioPassword").hide();
    $("#circuloEditarUsuario").addClass("bg-hover-blue");
    $("#cambiarFotoUsuario").hide();
    $("#eliminarFotoUsuario").hide();
    $("#removeFotoUsuario").hide();
    $("#cambiarFirmaUsuario").hide();
    $("#eliminarFirmaUsuario").hide();
    $("#removeFirmaUsuario").hide();
    });
 
var avatar1 = new KTImageInput('inputFotoUsuario');

var avatar2 = new KTImageInput('inputFirmaUsuario');


$(".cerrarModal").click(function() {
    handlerMessageWarning("Presione guardar o confirmar para registrar los cambios");
});


$("#txtRun").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
$("#txtRun").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
$("#txtNombre").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
$("#txtPrimerApellido").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
$("#selectCargo").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});

$("#txtUsuario").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
$("#txtPassword").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});