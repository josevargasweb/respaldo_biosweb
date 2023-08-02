
function disable(jqId) {
  $(jqId).prop("disabled", true);
  $('.selectpicker').selectpicker('refresh');
}

function enable(jqId) {
  $(jqId).prop("disabled", false);
  $('.selectpicker').selectpicker('refresh');
}



function limpiarMedic() {
  $(".cleanMedic").val('');
}

function prioridadAtencionxEdad(edad) {

  if (edad < 16) {
    RegistroOden.selectPrioridadAtencionPac = 1;
    $("#selectPrioridadAtencionPac").val(1);
    $('#selectPrioridadAtencionPac').selectpicker('refresh');
  }
  if (edad > 65) {
    RegistroOden.selectPrioridadAtencionPac = 2;
    $("#selectPrioridadAtencionPac").val(2);
    $('#selectPrioridadAtencionPac').selectpicker('refresh');
  }
}

function prioridadAtencionxDiagnostico(diag) {

  if (diag === "Gestante") {
    RegistroOden.selectPrioridadAtencionPac = 3;
    $("#selectPrioridadAtencionPac").val(3);
    $('#selectPrioridadAtencionPac').selectpicker('refresh');
  }
}


function circuloHover(jqId) {

  $(jqId).removeClass("text-primary");
  $(jqId).addClass("text-white");

}

function limpiarTabPaciente() {

  $(".cleanPaciente").val('');
  $("#tableBodyDiagnosticoPaciente").empty();

}


function reglaExclusionLocalizacion(idTipoAtencion) {

  switch (idTipoAtencion) {
    case g_ambulatorio:
      disable(g_jqIdSalas);
      disable(g_jqIdCamas);
      disable(g_jqIdServicios);
      enable(g_jqIdProcedencias);
      $(g_jqIdServicios).val(gCte.ComboServicioDefault); // Agregar consta
      break;

    case "-1":
      disable(g_jqIdSalas);
      disable(g_jqIdCamas);
      disable(g_jqIdServicios);
      disable(g_jqIdProcedencias);
      break;

    default:
      enable(g_jqIdSalas);
      enable(g_jqIdCamas);
      enable(g_jqIdServicios);
      disable(g_jqIdProcedencias);
      $(g_jqIdProcedencias).val(gCte.ComboProcedenciaDefault); // Agregar consta
      break;
  }

  if (RegistroOden.tipoDeAtencion == gCte.TipoAtencionAmbulatorio) {
    $("#txtLugarR").text($('#ProcedenciaComboBoxM option:selected').text());

    /* actual */
    $("#divtxtServicioR").show();
    $("#divtxtProcedenciaR").show();
    $("#txtProcedenciaR").text($('#ProcedenciaComboBoxM option:selected').text());
  } else {
    $("#txtUbicacionR").val( $('#ServicioComboBoxM option:selected').text());

    /* actual */
    $("#divtxtServicioR").show();
    let servicio = $('#ServicioComboBoxM option:selected').text();
    let sala = $('#SalaComboBoxM option:selected').text() != "" ? "/" + $('#SalaComboBoxM option:selected').text() : '';
    let cama = $('#CamaComboBoxM option:selected').text() != "" ? "/" + $('#CamaComboBoxM option:selected').text() : '';
    $("#txtServicioR").text(servicio + sala + cama);
    $("#txtServicioR").css({
      'white-space': 'nowrap',
      'overflow': 'hidden',
      'width': '100%',
      'display': 'inline-block',
      'text-overflow': 'ellipsis'
    });
  }
  $("#txtObservacionR").val($("#txtObservacion").val())

  $('.selectpicker').selectpicker('refresh');
}

function reglaTipoAtencion() {

  let idTipoAtencion = $(g_jqIdTipoAtencion).val();
  //  reglaExclusionLocalizacion(idTipoAtencion);
}


function elementoListaExamen() {
  this.ceIdexamen;
  this.ceCodigoexamen;
  this.ceAbreviado;
  this.ceDescripcion
}




function limpiarDatosNoClavePaciente() {
  $("#tableBodyDiagnosticoPaciente").empty();
  $("txtNombreP").val('');
  $("txtPrimerApellidoP").val('');
  $("sexoPacienteOrden").val('');
  $("txtEdad").val('');
  $("txtEmailP").val('');
}


function getFormData() {
  let datosFichasDTO = new Object();
  datosFichasDTO.datosPacientes = $("#idPaciente").val();
  datosFichasDTO.cfgServicios = RegistroOden.ServicioComboBoxM;
  datosFichasDTO.cfgDiagnosticos = RegistroOden.DiagnosticoComboBoxM;
  datosFichasDTO.cfgConvenios = RegistroOden.ConvencioComboBoxM;
  datosFichasDTO.cfgProcedencias = RegistroOden.ProcedenciaComboBoxM;
  datosFichasDTO.cfgPrioridadatencion = RegistroOden.selectPrioridadAtencionPac;
  datosFichasDTO.cfgTipoatencion = RegistroOden.tipoDeAtencion;
  datosFichasDTO.dfIdmedico = RegistroOden.medicoComboBox;
  datosFichasDTO.dfEnviarresultadosemail = $("#chbxRestutToMail").val();
  datosFichasDTO.dfIdprevision = RegistroOden.ConvencioComboBoxM;
  datosFichasDTO.salas = RegistroOden.SalaComboBoxM;
  datosFichasDTO.camas = RegistroOden.cama;
  datosFichasDTO.lstExamenesDto = new Array();
  datosFichasDTO.lstExamenes = new Array();

  datosFichasDTO.dfObservacion = $("#txtObservacionR").val();
  datosFichasDTO.dfCodigolocalizacion = $("#ServicioComboBoxM").val();
  
  let items = vt.getAllItems();

  items.forEach((item) => { datosFichasDTO.lstExamenesDto.push(item.dataValue); });
  items.forEach((item) => { datosFichasDTO.lstExamenes.push(item.id); });
  setExamenes(items);

  return datosFichasDTO;

}

function setExamenes(items) {
  let i = 0;
  $("#txtExamenesR tbody").empty();
  items.forEach((item) => {
    let descripcion = (typeof item.dataValue.ldvieDescripcion !== "undefined") ? item.dataValue.ldvieDescripcion : "-";
    console.log('descripcion');
    $("#txtExamenesR tbody").append('<tr>' + '<td><input type="hidden" value="' + item.dataValue.ceAbreviado + '" name="ExamenesResumenes">' + item.dataValue.ceAbreviado + '</td><td>' + (descripcion === "" ? "<div class='w-100 text-center'>-</div>" : descripcion) +
      '</td></tr>');

  });
}

function insertarOrden() {//rut

  let dpDto = getFormData();
  if (!validaOrdenSubmit(dpDto)) {
    handlerMessageError('Faltan campos obligatorios');
    if ($("#spanDatosBasicos").length === 0) {
      let spanDatosBasicos = `<span id="spanDatosBasicos"  class="label label-danger font-weight-bold label-inline ocultar">
      <i class="fa la-exclamation-circle text-white"></i></span>`;
      $("#spanDatosBasicos").css('display', 'inline-block');
      $("#btnPacienteBasico").append(spanDatosBasicos);
    }
    return;
  };
  if ($("#spanDatosBasicos").length > 0) {
    $("#spanDatosBasicos").remove();
  }
  $("#jsonFormData").val(JSON.stringify(dpDto));
  $("#idOrderConfirmationModal").modal('show');
  $("#txtObservacionRC").val($("#txtObservacionR").val())
  let nombreMed =  $('#medicoBox option:selected').text() == "" ? "" : $('#medicoBox option:selected').text();
  if (nombreMed === undefined) {
    nombreMed = "";
  }
  $("#txtNombreMedicoR").text(nombreMed);

    $("#txtLugarR").text($('#ProcedenciaComboBoxM option:selected').text());
    $("#divtxtServicioR").show();
    $("#divtxtProcedenciaR").show();
    $("#txtProcedenciaR").text($('#ProcedenciaComboBoxM option:selected').text());
    $("#txtUbicacionR").val( $('#ServicioComboBoxM option:selected').text());
    let servicio = $('#ServicioComboBoxM option:selected').text();
    let sala = $('#SalaComboBoxM option:selected').text() != "" ? "/" + $('#SalaComboBoxM option:selected').text() : '';
    let cama = $('#CamaComboBoxM option:selected').text() != "" ? "/" + $('#CamaComboBoxM option:selected').text() : '';
    $("#txtServicioR").text(servicio + sala + cama);
    $("#txtServicioR").css({
      'white-space': 'nowrap',
      'overflow': 'hidden',
      'width': '100%',
      'display': 'inline-block',
      'text-overflow': 'ellipsis'
    });
}


function rellenarFormulario(paciente) {

  // Comunes
  let nombre_orden = $("#nombre_orden");
  let sexo_orden = $("#sexo_orden");
  let edad_orden = $("#edad_orden");
  let email_orden = $("#email_orden");

  if (RegistroOden.tipoDeAtencion == gCte.TipoAtencionAmbulatorio) {
    $("#txtLugarR").text($('#ProcedenciaComboBoxM option:selected').text());

    $("#divtxtServicioR").show();
    $("#divtxtProcedenciaR").show();
    $("#txtProcedenciaR").text($('#ProcedenciaComboBoxM option:selected').text());
  } else {
    $("#txtUbicacionR").val( $('#ServicioComboBoxM option:selected').text());

    $("#divtxtServicioR").show();
    $("#divtxtProcedenciaR").show();
    let servicio = $('#ServicioComboBoxM option:selected').text();
    let sala = $('#SalaComboBoxM option:selected').text() != "" ? "/" + $('#SalaComboBoxM option:selected').text() : '';
    let cama = $('#CamaComboBoxM option:selected').text() != "" ? "/" + $('#CamaComboBoxM option:selected').text() : '';
    $("#txtServicioR").text(servicio + sala + cama);
    $("#txtServicioR").css({
      'white-space': 'nowrap',
      'overflow': 'hidden',
      'width': '100%',
      'display': 'inline-block',
      'text-overflow': 'ellipsis'
    });
  }
  $("#txtObservacionR").val($("#txtObservacionR").val())
  $("#txtExamenesR tbody").empty();

  if (paciente.dpFnacimiento !== undefined && paciente.dpFnacimiento !== null && paciente.dpFnacimiento !== '') {
    let fecha = calcularEdadTM(paciente.dpFnacimiento);
    $("#txtEdad").val(fecha);
  }
  $('#idPaciente').val(paciente.dpIdpaciente);
  $('#datosPaciente').val(paciente.dpIdpaciente);
  $("#txtNombreP").val(paciente.dpNombres);
  $("#txtPrimerApellidoP").val(paciente.dpPrimerapellido + " " + (paciente.dpSegundoapellido === undefined ? "" : paciente.dpSegundoapellido));
  $("#txtObservacionP").val(paciente.dpObservacion);
  $("#sexoPacienteOrden").val(paciente.ldvSexo);
  pacSexo = paciente.ldvSexo;

  listaSexos = new LdvSexo();
  function callback(lstSexos) {
    let sexo = lstSexos.find(sex => sex.ldvsIdsexo === pacSexo);
    if (sexo !== undefined) {
      $("#sexoPacienteOrden").val(sexo.ldvsDescripcion);
      nombre_orden.text($("#txtNombreP").val() + " " + $("#txtPrimerApellidoP").val());
      sexo_orden.text($("#sexoPacienteOrden").val());
      edad_orden.text($("#txtEdad").val());
      email_orden.text($("#txtEmailP").val());

    }
  }
  listaSexos.getSexos(callback);

  $("#txtEmailP").val(paciente.dpEmail);
  let nombrePac = $("#txtNombreP").val() + ' ' + $("#txtPrimerApellidoP").val() + ' ';
  let nombreMed = $("#txtNombreM").val() === undefined ? "" : $("#txtNombreM").val() + ' ' + $("#txtPrimerApellidoM").val() === undefined ? "" : $("#txtPrimerApellidoM").val();
  $("#txtNombrePacienteR").text(nombrePac);
  if (nombreMed === undefined) {
    nombreMed = "";
  }
  $("#txtNombreMedicoR").text(nombreMed);
  nombre_orden.text($("#txtNombreP").val() + " " + $("#txtPrimerApellidoP").val());
  sexo_orden.text($("#sexoPacienteOrden").val());
  edad_orden.text($("#txtEdad").val());
  email_orden.text($("#txtEmailP").val());

  if ($("#txtEmailP").val() !== undefined && $("#txtEmailP").val() !== "") {
    $("#chbxRestutToMail").prop('disabled', false);
    $("#chbxRestutToMail").prop('checked', true);
    //agregado por cristian 07-12
    $("#chbxRestutToMail").val('S');
    
  }
  else {
    $("#chbxRestutToMail").prop('disabled', true);
    //agregado por cristian 07-12
     $("#chbxRestutToMail").val('N');
  }

  switch (paciente.ldvTiposdocumentos.toString()) {
    case gCte.TipoDocRun:
      $('#divTxtRutFiltro').show();
      $('#divTxtPasaporteFiltro').hide();
      $('#txtRutP').val(paciente.dpNrodocumento);
      break;
  }
}
//agregado por cristian 07-12
$("#chbxRestutToMail").change(function() {
	if ($(this).is(":checked")) {
		$("#chbxRestutToMail").val("S");
	} else {
		$("#chbxRestutToMail").val("N");
	}
});


function rellenarPatologias(listaPatologias) {
  let cont = 0;
  if (listaPatologias === undefined || listaPatologias === null) {
    $("#tableDiagnosticoPaciente").append("<tr colspan ='3'' class=''><th class='row mx-auto'>Paciente no tiene patolog&iacute;as</th></tr>");
  } else {
    let npac = listaPatologias.length;
    for (i = 0; i < npac; i++) {
      cont++;
      let observacion = "";
      if (listaPatologias[i].dppObservacion !== undefined) {
        observacion = listaPatologias[i].dppObservacion;
      }
      $("#tableDiagnosticoPaciente").append("<tr class=''><th class='row mx-auto'>" + cont + "</th><td'" + listaPatologias[i].ldvPatologias.ldvpatIdpatologia + "'></td><td>" + listaPatologias[i].ldvPatologias.ldvpatDescripcion + "</td> <td>" + observacion + "</td></tr>");
    }
  }

}



function limpiarTodo(vs, vt) {

  limpiarDatosPaciente();
  limpiarDatosOrdenes();
  limpiarDatosExamenes(vs, vt);

}

function limpiarDatosPaciente() {

  limpiarDatosBasicosPaciente();
  limpiarDatosExtrasPaciente();

}

function limpiarDatosOrdenes() {
  limpiarDatosBasicosOrden();
  limpiarDatosExtrasOrden();
}

function limpiarDatosExamenes(vs, vt) {

    let items = vt.getItems();
    vt.removeItems(items);
    vs.addItems(items);
  //vs.clear();
  //vt.clear();
}

function limpiarDatosBasicosPaciente() {

  $("#selectTipoDocumento").val(gCte.TipoDocRun);//
  $("#txtRutP").val("");
  $("#txtPasaporte").val("");
  $("#txtDni").val("");
  $("#txtNombreP").val("");
  $("#chbxRestutToMail").prop("checked", false);
  //$("#tipoDeAtencion").val("").change();
  //$("#tipoDeAtencion").selectpicker('val', '');
  $("#tipoDeAtencion").val(null);
  //$("#ProcedenciaComboBoxM").val("").change();
  //$("#ProcedenciaComboBoxM").selectpicker('val', '');
  $("#ProcedenciaComboBoxM").val(null);
  //$("#ServicioComboBoxM").val("").change();
  //$("#ServicioComboBoxM").selectpicker('val', '');
  $("#ServicioComboBoxM").val(null);
  //$("#SalaComboBoxM").val("").change();
  //$("#SalaComboBoxM").selectpicker('val', '');
  $("#SalaComboBoxM").val(null);
  //$("#CamaComboBoxM").val("").change();
  //$("#CamaComboBoxM").selectpicker('val', '');
  $("#CamaComboBoxM").val(null);
  //$("#ProcedenciaComboBoxM").val("").change();
  $("#ProcedenciaComboBoxM").val(null);
  $("#idPaciente").val("");
  $("#txtEdad").val("");
  $("#dfEnviarresultadosemail").val("");
  //$("#sexoPacienteOrden").val("").change();
  $("#sexoPacienteOrden").val(null);
  $("#txtPrimerApellidoP").val("");
  $("#txtEmailP").val("");

  $("#nombre_orden").text("");
  $("#sexo_orden").text("");
  $("#edad_orden").text("");
  $("#email_orden").text("");
	
	$('.selectpicker').selectpicker('refresh');
}

function limpiarDatosExtrasPaciente() {

  $("#tableBodyDiagnosticoPaciente").empty();
  $("#txtObservacionP").val("");
  RegistroOden.selectPrioridadAtencionPac = null;
  $("#selectPrioridadAtencionPac").val("");
  $("#selectPrioridadAtencionPac").selectpicker('val', '');
}


function limpiarDatosBasicosOrden() {

  $("#medicoBox").val("");
  $("#txtRutMedico").val("");
  $("#txtNombreM").val("");
  $("#ConvencioComboBoxM").val(null);
  $("#idMedico").val("");
  $("#medicoBox").selectpicker('val', '');
  
  $('.selectpicker').selectpicker('refresh');

}

function limpiarDatosExtrasOrden() {
  $("#txtObservacionR").val("");
  $("#DiagnosticoComboBoxM").val("");
  $("#DiagnosticoComboBoxM").selectpicker('val', '');
}


