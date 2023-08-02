/**
 * 
 */

$(document).ready(function() {
  $("#selectSexo").selectpicker("val", null);
});


function setModoRegistroCliente(modo) {
  console.log(modo);
  switch (modo) {
    case ModoVisualizacion:
      $("#btnUpdatePaciente").hide();
      $("#btnAgregarPaciente").hide();
      $("#btnCancelEdit").hide();
      $("#divbtnLimpiar").prop("disabled", true);
      $("#divbtnEditar").prop("disabled", false);
      $("#buscarPaciente").prop("disabled", false);
      activarBtnEdit();
      desactivarLimpiar();
      habilitarDatosEditables(false);
      break;
    case ModoEdicion:
      habilitarDatosEditables(true);
      $("#btnUpdatePaciente").css('display', 'inline-block');
      $("#btnAgregarPaciente").hide();
      $("#btnCancelEdit").css('display', 'inline-block');
      $("#selectTipoDocumento").prop("disabled", true);
      $("#txtRut").prop("disabled", true);
      $("#txtPasaporte").prop("disabled", true);
      $("#divbtnLimpiar").prop("disabled", true);
      $("#divbtnEditar").prop("disabled", true);
      $("#buscarPaciente").prop("disabled", false);
      desactivarBtnEdit();
      desactivarLimpiar();
      break;
    case ModoAgregacion:
      habilitarDatosEditables(true);
      $("#btnUpdatePaciente").hide();
      $("#btnAgregarPaciente").show();
      $("#btnCancelEdit").hide();
      $("#divbtnLimpiar").prop("disabled", false);
      $("#divbtnEditar").prop("disabled", true);
      $("#buscarPaciente").prop("disabled", false);
      desactivarBtnEdit();
      activarLimpiar();
      break;
  }

}


function getDpFromForm() {

  let dp = new Object();
  dp.dpIdpaciente = $("#txtIdPaciente").val();
  dp.dpNombres = $("#txtNombre").val();
  dp.dpPrimerapellido = $("#txtPrimerApellido").val();
  dp.dpSegundoapellido = $("#txtSegundoApellido").val();
  dp.dpFnacimiento = $("#txtFechaNacimiento").val();
  dp.dpFnacimiento = dp.dpFnacimiento.concat(' 00:00:00');
  dp.dpDireccion = $("#txtDireccion").val();
  dp.dpNombresocial = $("#txtNombreSocial").val();
  dp.dpTelefono = $("#txtTelefono").val();
  dp.dpEmail = $("#txtEmail").val();
  dp.dpDireccodpostal = $("#txtCodigoPostal").val();
  dp.dpObservacion = $("#txtObservaciones").val();

  dp.dpIdmadre = $("#dpIdmadre").val();
  dp.dpExitus = $("#checkBoxExitus").val();
  dp.dpAbo = $("#dpNrodocumento").val();
  dp.dpReciennacido = $("#dpNrodocumento").val();
  if (dp.dpReciennacido === gCte.TipoDocRecienNacido) {
    dp.dpReciennacido = "S";
  }
  else {
    dp.dpReciennacido = "N";
  }
  dp.dpRnpartomultiple = $("#checkboxPartoMultiple").val();
  dp.dpRnnumero = $("#txtNroPartoMultiple").val();

  dp.dpEsvip = $("#checkboxVIP").val();
  dp.dpEsafroamericano = $("#checkboxAfro").val();

  dp.ldvEstadosciviles = $("#selectEstadoCivil").val();
  dp.ldvPaises = $("#selectPais").val();
  dp.ldvSexo = $("#selectSexo").val();
  dp.ldvComunas = $("#selectComuna").val();
  dp.ldvRegiones = $("#selectRegion").val();
  dp.ldvTiposdocumentos = $("#selectTipoDocumento").val();

  switch (dp.ldvTiposdocumentos) {

    case gCte.TipoDocRun:
      dp.dpNrodocumento = $("#txtRut").val();
      break;
    case gCte.TipoDocPasaporte:
      dp.dpNrodocumento = $("#txtPasaporte").val();
      break;
    case gCte.TipoDocRecienNacido:
    case gCte.TipoDocSinIdentificacion:
    default:
      dp.dpNrodocumento = "";
      break;
  }

  let dc = new Object();

  dc.dcNombre = $("#txtNombreContacto").val();
  dc.ldvContactospacientesrelacion = new Object();
  dc.ldvContactospacientesrelacion.ldvcprIdcontactopacienterel = $("#selectPacienteRelacion").val();
  dc.dcTelefono = $("#txtTelefonoContacto").val();
  dc.dcIdsexo = $("#selectSexoContacto").val();

  lstPatologias = $("#tbodyPatologias").find("input[name='liPatologias']");
  lstObservacionesPatologias = $("#tbodyPatologias").find("input[name='observacionesPatologias']");
  let n = lstPatologias.length;
  let listaPatologias = new Array();
  for (let i = 0; i < n; i++) {
    let patologia = new Object();
    patologia.dppIdpacientepatologia = $("#txtdppIdpacientepatologia").val();       //dp.dpIdpaciente;
    patologia.dppObservacion = lstObservacionesPatologias[i].value;
    let ldvPatologias = new Object();
    ldvPatologias.ldvpatIdpatologia = lstPatologias[i].value;
    console.log(lstPatologias[i].value);
    patologia.ldvPatologias = ldvPatologias;
    listaPatologias.push(patologia);
  }

  observacionesPatologias = $("#tbodyPatologias").find("input[name='observacionesPatologias']");

  let dpdto = new Object();
  dpdto.dp = dp;
  dpdto.dc = dc;
  dpdto.listaPatologias = listaPatologias;

  if (validarDatosObligatorios() === false) {
    setInputVisualEstado();
    return false;
  }

  setInputVisualEstado();


  if (validarFechaNacimientoNotEmpty() === false) {
    return false;
  }

  if (validarFechaNacimiento() === false) {
    $("#txtFechaNacimiento").addClass("is-invalid");
    return false;
  }

  if (validarFormatos() === false) {
    handlerMessageError("Formato inválido");
    if ($("#spanDatosBasicos").length === 0) {
      let spanDatosBasicos = `<span id="spanDatosBasicos"  class="label label-danger font-weight-bold label-inline ocultar">
    <i class="fa la-exclamation-circle text-white"></i></span>`;
      $("#spanDatosBasicos").css('display', 'inline-block');
      $("#anchorRegistro").append(spanDatosBasicos);
    }
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
  console.log(dpdto);
  return dpdto;
}




function removeRow(event) {
  $($(event).closest("tr")).remove();
}


function limpiarFiltro() {
  $("#selectTipoDocumentoFiltro").val("1");
  $("#txtFiltroNombre").val("");
  $("#txtFiltroApellido").val("");
  $("#txtFiltroSegundoApellido").val("");
  $("#txtFiltroRut").val("");
  $("#txtPasaporteFiltro").val("");
  $("#selectTipoDocumentoFiltro").removeClass("is-invalid");;
  $("#txtFiltroNombre").removeClass("is-invalid");
  $("#txtFiltroApellido").removeClass("is-invalid");
  $("#txtFiltroSegundoApellido").removeClass("is-invalid");
  $("#txtFiltroRut").removeClass("is-invalid");
  $("#txtPasaporteFiltro").removeClass("is-invalid");

  $("#txtFiltroNombre").prop("disabled", false);
  $("#txtFiltroApellido").prop("disabled", false);
  $("#txtFiltroSegundoApellido").prop("disabled", false);
  $("#txtFiltroRut").prop("disabled", false);
  $("#txtPasaporteFiltro").prop("disabled", false);


  $("#tbodyFiltro").empty();
  $('.selectpicker').selectpicker('refresh');
  $('#txtFiltroRut', "#txtFiltroNombre", "#txtFiltroApellido").removeAttr("data-toggle data-placement data-custom-class title data-original-title");
  $("#txtFiltroNombre").tooltip('disable');
  $("#lblTotalFiltro").attr("hidden", "hidden");
  $("#totalFiltro").text("");
}


function habilitarDatosEditables(phabilitador) {

  let habilitador = !phabilitador;
  $(".fallecimiento-checkbox").css("display", "inline-block");
  $("#checkBoxExitus").prop("disabled", habilitador);
  $(".disabledForm").prop("disabled", habilitador);
  $("#btnAgregarPaciente").prop("disabled", habilitador);
  $("#btnPatologia").prop("disabled", habilitador);//
  $("#selectPatologias").prop("disabled", habilitador);//
  $("#txtNombreContacto").prop("disabled", habilitador);//
  $("#selectPacienteRelacion").prop("disabled", habilitador);//
  $("#txtTelefonoContacto").prop("disabled", habilitador);//
  $("#txtTelefono").prop("disabled", habilitador);//
  $("#txtEmail").prop("disabled", habilitador);//
  $("#selectSexoContacto").prop("disabled", habilitador);//
  $("#txtRut").prop("disabled", habilitador);
  $("#txtPasaporte").prop("disabled", habilitador);
  $("#selectTipoDocumento").prop("disabled", habilitador);
  $("#checkBoxExitus").removeClass("ocultar");
  $("#txtRutMadre").prop("disabled", habilitador);//
  $("#checkboxPartoMultiple").prop("disabled", habilitador);//
  $("#txtNroPartoMultiple").prop("disabled", habilitador);//



  if (habilitador) {
    $("#divIdCheckBoxExitus").hide();
  }
  else {
    $("#divIdCheckBoxExitus").show();
  }


}


function vaciarFormularioRegistroCliente() {
  $("#txtIdPaciente").val(0);
  $("#selectTipoDocumento").val(TipoDocRun);
  $("#txtRut").val('');
  $("#divTxtRut").show();
  $("#divTxtPasaporte").hide();
  $("#txtNombre").val("");
  $("#txtNombre").removeClass("is-invalid");
  $("#txtPrimerApellido").val("");
  $("#txtPrimerApellido").removeClass("is-invalid");
  $("#txtSegundoApellido").val("");
  $("#txtFechaNacimiento").val("");
  $("#txtFechaNacimiento").removeClass("is-invalid");
  $("#selectSexo").val("N");
  $("#selectSexo").removeClass("is-invalid");
  $("#txtDireccion").val("");
  $("#selectEstadoCivil").val('');
  $("#selectPais").val("");
  $("#selectRegion").val("");
  $("#selectComuna").val("");

  $("#txtNombreSocial").val("");
  $("#txtTelefono").val("");
  $("#txtCodigoPostal").val("");
  $("#checkBoxExitus").show();
  $("#checkBoxExitus").prop("disabled", true);
  $("#divIdCheckBoxExitus").show();
  $("#txtEmail").val('');
  $("#txtObservaciones").val("");
  $(".disabledForm").prop("disabled", true);
  $("#selectTipoDocumento").prop("disabled", true);
  $("#txtRut").prop("disabled", true);
  $("#txtPasaporte").prop("disabled", true);
  $("#btnAgregarPaciente").prop("disabled", true);
  $("#btnPatologia").prop("disabled", true);//
  $("#selectPatologias").prop("disabled", true);//
  $("#txtNombreContacto").prop("disabled", true);//
  $("#selectPacienteRelacion").prop("disabled", true);//
  $("#txtTelefonoContacto").prop("disabled", true);//
  $("#selectSexoContacto").prop("disabled", true);//

  $("#checkboxVIP").prop("checked", false);
  $("#checkboxAfro").prop("checked", false);
  $("#checkBoxExitus").prop("checked", false);

  $("#txtNombreContacto").val("");
  $("#selectPacienteRelacion").val("");
  $("#txtTelefonoContacto").val("");
  $("#selectSexoContacto").val('');
  $("#checkboxPartoMultiple").prop("checked", false);
  $("#checkboxPartoMultiple").val("S");
  $("#txtNroPartoMultiple").val("");

  $("#tbodyPatologias").empty();
  $('.selectpicker').selectpicker('refresh');
  $("#liDatosMadres").hide();

}

function rellenarFormulario(paciente, contacto, rutMadre) {
  console.log("rellenar formulario")
  console.log(paciente)

  $("#lblTotalFiltro").attr("hidden", "hidden");
  $("#totalFiltro").text("");
  $("#txtIdPaciente").val(paciente.dpIdpaciente);
  $("#selectTipoDocumento").val(paciente.ldvTiposdocumentos);
  $("#tbodyPatologias").empty();
  $(".is-invalid").removeClass("is-invalid");
  $("#spanDatosBasicos").remove();
  $("#spanDatosMadre").remove();
  switch ($("#selectTipoDocumento").val()) {
    case TipoDocRun:
      $("#txtRut").val($.formatRut(paciente.dpRun));
      //      alert($.rut.formatRut(paciente.dpRun,'.'));
      $("#divTxtRut").show();
      $("#divTxtRut").css('visibility', '');
      $("#divTxtPasaporte").hide();
      $("#liDatosMadres").hide();
      break;
    case TipoDocPasaporte:
      $("#txtPasaporte").val(paciente.dpNrodocumento);
      $("#divTxtRut").hide();
      $("#divTxtPasaporte").show();
      $("#liDatosMadres").hide();
      break;
    case TipoDocRecienNacido:
      $("#liDatosMadres").show();
      break;
    case TipoDocSinIdentificacion:
      $("#divTxtRut").hide();
      $("#divTxtPasaporte").hide();
      $("#liDatosMadres").hide();
    default:
      console.log("Revisar tipo de documento.");
  }
  $("#txtRut").removeClass("is-invalid");
  $("#txtNombre").val(paciente.dpNombres);
  $("#txtNombre").removeClass("is-invalid");
  $("#txtPrimerApellido").val(paciente.dpPrimerapellido);
  $("#txtPrimerApellido").removeClass("is-invalid");
  $("#txtSegundoApellido").val(paciente.dpSegundoapellido);
  $("#txtFechaNacimiento").val(paciente.dpFnacimiento);
  $("#txtFechaNacimiento").removeClass("is-invalid");
  $("#selectSexo").val(paciente.ldvSexo);
  // $("#selectSexo").removeClass("is-invalid");
  $('#selectSexo').removeClass('is-invalid').selectpicker('setStyle').parent().removeClass('is-invalid');
  $("#txtDireccion").val(paciente.dpDireccion);
  $("#selectEstadoCivil").val(paciente.ldvEstadosciviles);
  $("#selectPais").val(paciente.ldvPaises);
  $("#selectRegion").val(paciente.ldvRegiones);
  $("#selectComuna").val(paciente.ldvComunas);

  $("#txtNombreSocial").val(paciente.dpNombresocial);
  $("#txtTelefono").val(paciente.dpTelefono);
  $("#txtCodigoPostal").val(paciente.dpDireccodpostal);
  $("#checkBoxExitus").show();
  $("#divIdCheckBoxExitus").show();
  $("#txtEmail").val(paciente.dpEmail);
  $("#txtObservaciones").val(paciente.dpObservacion);

  setModoRegistroCliente(ModoVisualizacion);

  $('.selectpicker').selectpicker('refresh');

  if (paciente.dpEsvip === "S") {
    $("#checkboxVIP").prop("checked", true);
    $("#checkboxVIP").val("1");
  } else {
    $("#checkboxVIP").prop("checked", false);
  }
  if (paciente.dpEsafroamericano === "S") {
    $("#checkboxAfro").prop("checked", true);
    $("#checkboxAfro").val("1");
  } else {
    $("#checkboxAfro").prop("checked", false);
  }
  if (paciente.dpExitus === "S") {
    $("#divIdCheckBoxExitus").show();
    $("#chkcheckBoxExitus").show();
    $("#checkBoxExitus").prop("checked", true);
    $("#checkBoxExitus").val("1");
  } else {
    $("#checkBoxExitus").prop("checked", false);
  }

  $("#dpIdmadre").val(paciente.dpIdmadre);
  if (contacto !== undefined && contacto !== null) {
    $("#txtNombreContacto").val(contacto.dcNombre);
    if (contacto.ldvContactospacientesrelacion !== null && contacto.ldvContactospacientesrelacion !== undefined) {
      $("#selectPacienteRelacion").val(contacto.ldvContactospacientesrelacion.ldvcprIdcontactopacienterel);
    }
    $("#txtTelefonoContacto").val(contacto.dcTelefono);
    $("#selectSexoContacto").val(contacto.dcIdsexo);
    $('.selectpicker').selectpicker('refresh');
  }

  if (rutMadre !== undefined && rutMadre !== null) {
    $("#txtRutMadre").val(cambiarDatoRut(rutMadre));
  }
  if (paciente.dpRnpartomultiple === 'S') {
    $("#checkboxPartoMultiple").prop("checked", true);
    $("#checkboxPartoMultiple").val("S");
    $("#txtNroPartoMultiple").val(paciente.dpRnnumero);
  } else {
    $("#checkboxPartoMultiple").prop("checked", false);
  }


  if (paciente.listaPatologias !== null && paciente.listaPatologias !== undefined && paciente.listaPatologias.length > 0) {
    $("#txtdppIdpacientepatologia").val(paciente.listaPatologias[0].dppIdpacientepatologia)
    paciente.listaPatologias.forEach(function(patologia) {
      let observacion;
      if (patologia.dppObservacion == undefined) {
        observacion = "";
      } else {
        observacion = patologia.dppObservacion;
      }
      $("#tbodyPatologias").append("<tr><td><input type='hidden' value=" + patologia.ldvPatologias.ldvpatIdpatologia + " name='liPatologias' /> " + patologia.ldvPatologias.ldvpatDescripcion + "</td><td><div class='form-group'><div class='col-md-12'><input name='observacionesPatologias' type='text' class='form-control disabledForm' disabled='true' value='" + observacion + "' /></div></div></td><td><button type='button' onclick='removeRow(this)' class='pointer disabledForm' disabled='true' ><i class='la la-trash icon-xl text-danger'></i></button></li></tr>");
    });
  }

  $("#anchorRegistro").click();
}


function limpiarFormulario() {
  $("#selectTipoDocumento").val(TipoDocRun);
  $("#txtRut").val("");
  $("#txtPasaporte").val("");
  $("#divTxtPasaporte").hide();
  $("#divTxtRut").show();
  // $("#divTxtRut").show();

  $("#txtNombre").val("");
  $("#txtPrimerApellido").val("");
  $("#txtSegundoApellido").val("");
  $("#txtFechaNacimiento").val("");
  $("#txtEdad").val("");
  $("#selectSexo").val("N");
  $("#txtDireccion").val("");
  $("#selectEstadoCivil").val("");
  $("#selectPais").val("");
  $("#selectRegion").val("");
  $("#selectComuna").val("");
  $("#txtTelefono").val("");
  $("#txtEmail").val("");
  $("#txtObservaciones").val("");
  $("#txtCodigoPostal").val("");
  $("#txtRutMadre").val("");
  $("#checkboxPartoMultiple").prop("checked", false);
  $("#txtNroPartoMultiple").val("");
  $("#txtNombreContacto").val("");
  $("#selectPacienteRelacion").val("N");
  $("#txtTelefonoContacto").val("");
  $("#selectSexoContacto").val("N");
  $("#spanContacto").hide();
  $("#txtAnio").val("");
  $("#txtMeses").val("");
  $("#txtDias").val("");
  $("#txtNombreSocial").val("");
  $("#tbodyPatologias").empty();
  $(".disabledForm").prop("disabled", false);
  $('.selectpicker').selectpicker('refresh');
  $("#anchorRegistro").click();
  $("#liDatosMadres").hide();
}


function habilitarFiltro() {
  $("#txtFiltroNN").val("");
  $("#txtFiltroRut").val("");
  $("#txtPasaporteFiltro").val("");
  $("#txtDniFiltro").val("");
  $("#txtFiltroNombre").prop("disabled", false);
  $("#txtFiltroApellido").prop("disabled", false);
  $("#txtFiltroSegundoApellido").prop("disabled", false);
  const tipoDocSelected = $("#selectTipoDocumentoFiltro :selected").val();
  $("#txtFiltroRut, #txtPasaporteFiltro, #txtFiltroNN, #txtDniFiltro").prop("disabled", false);
  switch (tipoDocSelected) {

  
    case TipoDocDni:
      // $("#divTxtDni").show();
      $("#divTxtDniFiltro").css('display', 'flex');
      $("#divTxtPasaporteFiltro").hide();
      // $("#divTxtPasaporte").hide();
      $("#divTxtRutFiltro").hide();
      $("#divTxtNNFiltro").hide();
      // $('#divTxtRut').hide()
      // $('#selectTipoDocumento').val(tipoDocSelected);
      // $(".selectpicker").selectpicker("refresh");
      break;
    case TipoDocPasaporte:
      // $("#divTxtDni").hide();
      $("#divTxtDniFiltro").hide();
      $("#divTxtPasaporteFiltro").css('display', 'flex');
      // $("#divTxtPasaporte").show();
      $("#divTxtRutFiltro").hide();
      $("#divTxtNNFiltro").hide();
      // $('#divTxtRut').hide()
      // $('#selectTipoDocumento').val(tipoDocSelected);
      $(".selectpicker").selectpicker("refresh");
      break;
    case TipoDocRun:
      // $("#divTxtDni").hide();
      $("#divTxtDniFiltro").hide();
      $("#lblRutFiltro").show();
      $("#lblRutFiltroMadre").hide();
      $("#divTxtPasaporteFiltro").hide();
      // $("#divTxtPasaporte").hide();
      $("#divTxtRutFiltro").css('display', 'flex');
      $("#divTxtNNFiltro").hide();
      // $('#divTxtRut').show()
      // $('#selectTipoDocumento').val(tipoDocSelected);
      $(".selectpicker").selectpicker("refresh");
      break;
    case TipoDocRecienNacido:
      // $("#divTxtDni").hide();
      $("#divTxtDniFiltro").hide();
      $("#lblRutFiltro").hide();
      $("#lblRutFiltroMadre").css('display', 'flex');
      $("#divTxtPasaporteFiltro").hide();
      // $("#divTxtPasaporte").hide();
      $("#divTxtRutFiltro").css('display', 'flex');
      $("#divTxtNNFiltro").hide();
      // $('#selectTipoDocumento').val(tipoDocSelected);
      $(".selectpicker").selectpicker("refresh");

      break;
    case TipoDocSinIdentificacion:
      // $("#divTxtDni").hide();
      $("#divTxtDniFiltro").hide();
      $("#divTxtPasaporteFiltro").hide();
      // $("#divTxtPasaporte").hide();
      $("#divTxtRutFiltro").hide();
      $("#divTxtNNFiltro").css('display', 'flex');
      // $('#selectTipoDocumento').val(tipoDocSelected);
      $(".selectpicker").selectpicker("refresh");
      break;
      case TipoDocTodos:
        // $("#divTxtRut").show();
        // $("#divTxtDni").hide();
        $("#divTxtDniFiltro").hide();
        $("#divTxtPasaporteFiltro").hide();
        // $("#divTxtPasaporte").hide();
        $("#divTxtRutFiltro").hide();
        $("#divTxtNNFiltro").hide();
        // $('#selectTipoDocumento').val(TipoDocRun);
        break;
    default:
  }
  $('.selectpicker').selectpicker('refresh');
}


function loadInit() {

  $("#txtNombre").keypress(filtraTextoNombre);
  $("#txtPrimerApellido").keypress(filtraTextoNombre);
  $("#txtSegundoApellido").keypress(filtraTextoNombre);
  $("#txtFiltroNombre").keypress(filtraTextoNombre);
  $("#txtFiltroApellido").keypress(filtraTextoNombre);
  $("#txtFiltroSegundoApellido").keypress(filtraTextoNombre);

  $("#selectTipoDocumento").focusin(setOk);
  $("#txtFechaNacimiento").focusin(setOk);
  $("#selectSexo").focusin(setOk);

  $("#txtRutMadre").focusin(setOk);
  $("#txtNombre").focusin(setOk);
  $("#txtPrimerApellido").focusin(setOk);
  $("#txtNombreContacto").focusin(setOk);
  $("#txtTelefonoContacto").focusin(setOk);
  $("#txtPasaporte").focusin(setOk);
  $("#txtDni").focusin(setOk);

  $("#txtNombre").on("input", function() {
    setSpan();
  });
  
  $("#txtPrimerApellido").on("input", function() {
    setSpan();
  });
  
  $("#txtNombreContacto").on("input", function() {
    setSpan();
  });
  
  $("#txtTelefonoContacto").on("input", function() {
    setSpan();
  });
  
  $("#txtPasaporte").on("input", function() {
    setSpan();
  });
  
  $("#txtDni").on("input", function() {
    setSpan();
  });
  

  $(".ocultar").hide();
  $("#lblRutFiltroMadre").hide();
  $('[data-toggle="tooltip"]').tooltip();
  $("#confirmarDatos").hide();
  $('#antecedentesRN').hide();
  $("#txtNroPartoMultiple").prop("disabled", true);
  $("#txtAnio, #txtMeses, #txtDias").prop("disabled", true);
  $('#validadorOrden').hide();
  $("#selectPacienteRelacion").prop("disabled", true);
  $("#selectSexoContacto").prop("disabled", true);

}