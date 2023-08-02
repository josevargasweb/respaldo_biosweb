const TipoDocRun = "1";
const TipoDocPasaporte = "2";
const TipoDocRecienNacido = "4";
const TipoDocSinIdentificacion = "5";
const TipoDocTodos = "6";


let cfgTipoDoc = new CfgTipoDocumento();
let fnc = cfgTipoDoc.genTipoDocumentosFiller("#selectTipoDocumentoFiltro");
cfgTipoDoc.getTiposDocumento(fnc);


$("#selectTipoDocumentoFiltro").val("1");


$("#txtFiltroNombre, #txtFiltroApellido, #txtFiltroSegundoApellido").keyup(function(input) {
  if (input.currentTarget.value.length > 0) {
    $("#txtFiltroRut").val("");
    $("#txtPasaporteFiltro").val("");
    $("#txtPasaporteFiltro").prop("disabled", true);
    $("#txtFiltroRut").prop("disabled", true);
  } else {
    $("#txtPasaporteFiltro").prop("disabled", false);
    $("#txtFiltroRut").prop("disabled", false);
  }
  if (input.which === 13) {
    $("#btnBuscarFiltro").click();
  }

});

$("#txtFiltroRut, #txtPasaporteFiltro, #txtFiltroNN").keyup(function(input) {
  if (input.which === 13) {
    $("#btnBuscarFiltro").click();
  }
});


$("#txtFiltroRut").keyup(function() {
  if ($("#txtFiltroRut").val().length > 0) {
    $("#txtFiltroNombre").val("");
    $("#txtFiltroApellido").val("");
    $("#txtFiltroSegundoApellido").val("");
    $("#txtFiltroNombre").prop("disabled", true);
    $("#txtFiltroApellido").prop("disabled", true);
    $("#txtFiltroSegundoApellido").prop("disabled", true);
  } else {
    $("#txtFiltroNombre").prop("disabled", false);
    $("#txtFiltroApellido").prop("disabled", false);
    $("#txtFiltroSegundoApellido").prop("disabled", false);
  }

});

$("#txtPasaporteFiltro").keyup(function() {
  if ($("#txtPasaporteFiltro").val().length > 0) {
    $("#txtFiltroNombre").val("");
    $("#txtFiltroApellido").val("");
    $("#txtFiltroSegundoApellido").val("");
    $("#txtFiltroNombre").prop("disabled", true);
    $("#txtFiltroApellido").prop("disabled", true);
    $("#txtFiltroSegundoApellido").prop("disabled", true);
  } else {
    $("#txtFiltroNombre").prop("disabled", false);
    $("#txtFiltroApellido").prop("disabled", false);
    $("#txtFiltroSegundoApellido").prop("disabled", false);
  }

});



$("#selectTipoDocumentoFiltro").change(function() {
  habilitarFiltro();
})



$("#btnBuscarFiltro").click(
  clickBotonBuscarFiltro
);

$("#buscarPaciente").click(function() {
  $("#panelBusquedaPaciente").collapse('show');
  $('html, body').animate({
    scrollTop: $("div#panelSuperior").offset().top
  }, 700);
});


$("#btnLimpiarFiltro").click(limpiarFiltro);





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
  $("#tbodyFiltro").empty();
  $('.selectpicker').selectpicker('refresh');
}


function habilitarFiltro() {
  $("#txtFiltroNN").val("");
  $("#txtFiltroRut").val("");
  $("#txtPasaporteFiltro").val("");
  $("#txtFiltroNombre").prop("disabled", false);
  $("#txtFiltroApellido").prop("disabled", false);
  $("#txtFiltroSegundoApellido").prop("disabled", false);
  const tipoDocSelected = $("#selectTipoDocumentoFiltro :selected").val();
  $("#txtFiltroRut, #txtPasaporteFiltro, #txtFiltroNN").prop("disabled", false);
  switch (tipoDocSelected) {

    case TipoDocTodos:
      $("#divTxtPasaporteFiltro").hide();
      $("#divTxtPasaporte").show();
      $("#divTxtRutFiltro").hide();
      $("#divTxtNNFiltro").hide();
      break;
    case TipoDocPasaporte:
      $("#divTxtPasaporteFiltro").show();
      $("#divTxtPasaporte").show();
      $("#divTxtRutFiltro").hide();
      $("#divTxtNNFiltro").hide();
      break;
    case TipoDocRun:
      $("#lblRutFiltro").show();
      $("#lblRutFiltroMadre").hide();
      $("#divTxtPasaporteFiltro").hide();
      $("#divTxtPasaporte").hide();
      $("#divTxtRutFiltro").show();
      $("#divTxtNNFiltro").hide();
      break;
    case TipoDocRecienNacido:
      $("#lblRutFiltro").hide();
      $("#lblRutFiltroMadre").show();
      $("#divTxtPasaporteFiltro").hide();
      $("#divTxtPasaporte").hide();
      $("#divTxtRutFiltro").show();
      $("#divTxtNNFiltro").hide();

      break;
    case TipoDocSinIdentificacion:
      $("#divTxtPasaporteFiltro").hide();
      $("#divTxtPasaporte").hide();
      $("#divTxtRutFiltro").hide();
      $("#divTxtNNFiltro").show();
      break;
    default:
  }
  $('.selectpicker').selectpicker('refresh');
}

function clickBotonBuscarFiltro() {
  try {
    let sRut = $("#txtFiltroRut").val();
    filtrarByRutRest(sRut);
  } catch (e) {
    handlerMessageError(e);
  }
}



function clickBotonBuscarFiltroOld() {
  let rut;
  let tipoDocumentoFiltro = $("#selectTipoDocumentoFiltro :selected").val();
  let se_busca_por_nombre = false;
  if ($("#txtFiltroNombre").val().trim().length >= 1 || $("#txtFiltroApellido").val().trim().length >= 1 || $("#txtFiltroSegundoApellido").val().trim().length >= 1) {
    se_busca_por_nombre = true;
  }

  if (se_busca_por_nombre && ($("#txtFiltroNombre").val().trim().length < 2 && $("#txtFiltroApellido").val().trim().length < 2 && $("#txtFiltroSegundoApellido").val().trim().length < 2)) {
    handlerMessageError('Se deben ingresar dos o más caracteres');
    return false;
  }

  if (se_busca_por_nombre) {
    filtrarByNombre($("#txtFiltroNombre").val(), $("#txtFiltroApellido").val(), $("#txtFiltroSegundoApellido").val(), $("#selectTipoDocumentoFiltro :selected").val());
  }
  else {
    switch (tipoDocumentoFiltro) {
      case TipoDocSinIdentificacion:
        if (validaParametrosNN()) {
          filtrarNN($("#txtFiltroNN").val(),TipoDocSinIdentificacion);
        }
        break;
      case TipoDocRecienNacido:
        if (validaParametrosRN()) {
          filtrarByRutMadre($("#txtFiltroRut").val());
        }
        else {
          handlerMessageError('Debe ingresar al menos un criterio');
        }
        break;
      case TipoDocRun:
        rut = $("#txtFiltroRut").val().trim();
        if (rut.length > 0) {
          filtrarByRut(rut);
        }
        else {
          handlerMessageError('Debe ingresar al menos un criterio');
        }
        break;
      case TipoDocPasaporte:
        rut = "PASAPORTE" + $("#txtPasaporteFiltro").val();
        filtrarByRut(rut);
        break;
      case TipoDocTodos:
        if (validaParametrosTodos()) {
          filtrarByNombre($("#txtFiltroNombre").val(), $("#txtFiltroApellido").val(), $("#txtFiltroSegundoApellido").val(), $("#selectTipoDocumentoFiltro :selected").val());
        }
        break;
      default:
        handlerMessageError('Error inseperado, por favor comuniquese con el administrador');
        break;
    }
  }
}



function rellenarFormulario(paciente, contacto, rutMadre) {

  $("#txtIdPaciente").val(paciente.dpIdpaciente);
  $("#selectTipoDocumento").val(paciente.ldvTiposdocumentos);
  $("#tbodyPatologias").empty();

  switch ($("#selectTipoDocumento").val()) {
    case TipoDocRun:
      $("#txtRut").val($.formatRut(paciente.dpRun));
      //      alert($.rut.formatRut(paciente.dpRun,'.'));
      $("#divTxtRut").show();
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

  $("#txtNombre").val(paciente.dpNombres);
  $("#txtNombre").removeClass("is-invalid");
  $("#txtPrimerApellido").val(paciente.dpPrimerapellido);
  $("#txtPrimerApellido").removeClass("is-invalid");
  $("#txtSegundoApellido").val(paciente.dpSegundoapellido);
  $("#txtFechaNacimiento").val(paciente.dpFnacimiento);
  $("#txtFechaNacimiento").removeClass("is-invalid");
  $("#selectSexo").val(paciente.ldvSexo);
  $("#selectSexo").removeClass("is-invalid");
  $("#txtDireccion").val(paciente.dpDireccion.toUpperCase());
  $("#selectEstadoCivil").val(paciente.ldvEstadosciviles);
  $("#selectPais").val(paciente.ldvPaises);
  $("#selectRegion").val(paciente.ldvRegiones);
  $("#selectComuna").val(paciente.ldvComunas);

  $("#txtNombreSocial").val(paciente.dpNombresocial);
  $("#txtTelefono").val(paciente.dpTelefono);
  $("#txtCodigoPostal").val(paciente.dpDireccodpostal);
  $("#chkExitus").show();
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
    $("#chkExitus").show();
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

  $("#txtRutMadre").val(rutMadre);
  if (paciente.dpRnpartomultiple === 'S') {
    $("#checkboxPartoMultiple").prop("checked", true);
    $("#checkboxPartoMultiple").val("S");
    $("#txtNroPartoMultiple").val(paciente.dpRnnumero);
  }


  if (paciente.listaPatologias !== null && paciente.listaPatologias !== undefined && paciente.listaPatologias.length > 0) {
    paciente.listaPatologias.forEach(function(patologia) {
      let observacion;
      if (patologia.dppObservacion == undefined) {
        observacion = "";
      } else {
        observacion = patologia.dppObservacion;
      }
      $("#tbodyPatologias").append("<tr><td><input type='hidden' value=" + patologia.ldvPatologias.ldvpatIdpatologia + " name='liPatologias' /> " + patologia.ldvPatologias.ldvpatDescripcion + "</td><td><div class='form-group'><div class='col-md-12'><input name='observacionesPatologias' type='text' class='form-control disabledForm' disabled='true' value='" + observacion + "' /></div></div></td><td><button type='button' onclick='removeRow(this)' class='pointer disabledForm' disabled='true' ><i class='la la-trash icon-md text-danger'></i></button></li></tr>");
    });
  }

  $("#anchorRegistro").click();
}
