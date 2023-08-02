/**
 * 
 */

function filtrarByRutM(rutPaciente) {
  rutPaciente.replace(".", "");
  $("#tbodyFiltro").empty();
  $.ajax({
    type: "post",
    data: "rutFiltroMedico=" + rutPaciente,
    url: 'RegistroMedico',
    datatype: "json",
    success: function(Medico) {

      var Medico = JSON.parse(Medico);
      var Medico = Medico.pacientes[0]; //PACIENTE QUE SE RETORNO DESDE JAVA

      console.table(Medico);
      if (Medico.Rut === "0") {


      } else {
        //RELLENAR FORMULARIO
		 RegistroOden.medicoComboBox = Medico.idMedico;
        $("#idMedico").val(Medico.idMedico);
        $("#txtNombreM").val(Medico.Nombres);
        $("#txtPrimerApellidoM").val(Medico.PrimerApellido);
        $("#txtSegundoApellidoM").val(Medico.SegundoApellido);
        $('#medicoBox').val(Medico.idMedico);
        // $('#medicoBox').typeahead('val', Medico.idMedico);
        // $('#medicoComboBox').trigger('change')
               $('.selectpicker').selectpicker('refresh');

      }
    },
    error: function(msg) {
      console.log(msg);
    }
  });
}

function filtrarByIdM(id,focus) {
  $("#tbodyFiltro").empty();
  // console.log(id);
  if (id === '0' || id === null) {
    $("#txtRutMedico").val('');
    return;
  }
  RegistroOden.medicoComboBox = id;
  $.ajax({
    type: "post",
    data: "idFiltroMedico=" + id,
    url: 'RegistroMedico',
    datatype: "json",
    success: function(Medico) {
      // console.table(Medico);
      var Medico = JSON.parse(Medico);
      var Medico = Medico.pacientes[0]; //PACIENTE QUE SE RETORNO DESDE JAVA


      if (Medico.Rut === "0") {
      } else {
        //RELLENAR FORMULARIO
        $("#idMedico").val(Medico.idMedico);
        $("#txtRutMedico").val(cambiarDatoRut(Medico.Rut));
        $("#txtNombreM").val(Medico.Nombres);
        let apellidos = Medico.PrimerApellido + " " + (Medico.SegundoApellido !== undefined ? Medico.SegundoApellido : "");
        $("#txtPrimerApellidoM").val(apellidos);
        $("#medicoBox").val(Medico.idMedico);
        $('.selectpicker').selectpicker('refresh');
        // typeof focus !== 'undefined' ? $("#"+focus).focus() : '';
        // $("#txtRutMedico").focus();
      }
    },
    error: function(msg) {
      console.log(msg);
    }
  });
}

function filtrarByRut(rutPaciente) {//rut
  rutPaciente = rutPaciente.replace(".", "");
  $("#tbodyFiltro").empty();
  $.ajax({
    type: "post",
    data: "rutFiltro=" + rutPaciente,
    datatype: "json",
    success: function(pacientes) {
      try {
        var lstPacientes = JSON.parse(pacientes);
        var paciente = lstPacientes.pacientes[0];
        console.log(paciente);
        if (paciente.Rut === "0") {
          handlerMessageOk('Se creará paciente.');
          document.getElementById("AgregarPac").click();
        } else {
          //formato fecha
          if (paciente.Exitus === 'S') {
            handlerMessageError('Paciente fallecido.');
            return;
          }
          let dia = paciente.FechaNacimiento.substring(0, 2);
          let mes = paciente.FechaNacimiento.substring(3, 5);
          let anio = paciente.FechaNacimiento.substring(6, 11);
          let fecha = dia + "-" + mes + "-" + anio;
          fecha = getAge(fecha, 3);
          fecha = fecha.split("-");

          rellenarFormulario(paciente);
          prioridadAtencionxEdad(fecha[0]);
        }
      }
      catch (e) {
        handlerMessageError('No se encontró al paciente.' + e.message);

      }
    },
    error: function(msg) {
      handlerMessageError('No se encontró al paciente.');
    }
  });
}




function filtrarByPasaporte(pasaportePaciente) {//rut
  $("#tbodyFiltro").empty();
  $.ajax({
    type: "post",
    data: "nroDocumento=" + pasaportePaciente,
    datatype: "json",
    success: function(pacientes) {
      try {
        var lstPacientes = JSON.parse(pacientes);
        var paciente = lstPacientes.pacientes[0];
        console.log(paciente);
        if (paciente.Rut === "0") {
          document.getElementById("AgregarPac").click();
        } else {
          //formato fecha
          let dia = paciente.FechaNacimiento.substring(0, 2);
          let mes = paciente.FechaNacimiento.substring(3, 5);
          let anio = paciente.FechaNacimiento.substring(6, 11);
          let fecha = dia + "-" + mes + "-" + anio;
          fecha = getAge(fecha, 3);
          fecha = fecha.split("-");
          rellenarFormulario(paciente);
          prioridadAtencionxEdad(fecha[0]);
        }
      }
      catch (e) {
        handlerMessageError('No se encontró al paciente.' + e.message);

      }
    },
    error: function(msg) {
      handlerMessageError('No se encontró al paciente.');
    }
  });
}
function filtrarByTipoNroDocRest(nroDoc, tipoDoc) {//rut // obtener los examens validos
   
  let url = gCte.getctx;
  let val;
  if (tipoDoc === gCte.TipoDocRun) {
    nroDoc = nroDoc.replaceAll(".", "");
    url = url + "/api/paciente/ordenes/rut/" + nroDoc;
    val = true;
  }
  else if (tipoDoc === gCte.TipoDocPasaporte) {
    url = url + "/api/paciente/ordenes/pasaporte/" + nroDoc;
  }
  else if (tipoDoc === gCte.TipoDocDni) {
    url = url + "/api/paciente/ordenes/dni/" + nroDoc;
  }
  else {
    handlerMessageError('Tipo documento inexistente.');
    return;
  }

  limpiarDatosNoClavePaciente();
  $.ajax({
    "url": url,
    "type": "get",
    "contentType": "application/json",
    success: function(data) {
      try {


        if (data.status === 404) {

          let rpta = confirm('¿Desea crear nuevo usuario?');
          if (rpta === true) {
            document.getElementById("AgregarPac").click();
          }

          return;
        }
        if (data.status !== 200 && data.status !== 201) {
          handlerMessageError(data.message);

          return;
        }


        let pacienteDto = data.dato;
        let paciente = pacienteDto.dp;
        let patologias = pacienteDto.listaPatologias
        let ordenes = pacienteDto.listaOrdenesDelDia;
        // console.log(paciente);
        if (data.status === 201) {
          ordenesId = ordenes.reduce(function(previousValue, currentValue) {
            return previousValue.concat('\n').concat(currentValue.dfNorden.toString());
          }, ' ');
          $("#usuarioConOrden").modal();
          $("#usuarioConOrden").find(".modal-title").html(`Paciente con orden Registrada`);
          $("#usuarioConOrden").find(".modal-body").html(`Paciente ya cuenta con una Orden registrada hoy ${ordenesId}, &iquest;Desea continuar?`);
          $('#usuarioConOrden').on('shown.bs.modal', function() {
            $('.btn-si-orden').focus();
          });
         }
        if (paciente.dpIdpaciente === 0) {
          $("#idModalPacienteNuevo").modal();
          $("#idModalPacienteNuevo").on('click',function(e) {
            if($(e.target).hasClass('btn-si')){
              handlerMessageOk('Se creará paciente.');
              document.getElementById("AgregarPac").click();
              $("#idModalPacienteNuevo").modal('toggle');
            }else{
              return;
            }
          });
        } else {
          let dia = paciente.dpFnacimiento.substring(0, 2);
          let mes = paciente.dpFnacimiento.substring(3, 5);
          let anio = paciente.dpFnacimiento.substring(6, 11);
          let fecha = dia + "-" + mes + "-" + anio;
          fecha = getAge(fecha, 3);
          fecha = fecha.split("-");
          vt.setPaciente(paciente.dpIdpaciente);
          if (paciente.dpExitus === 'S') {
            handlerMessageError('Paciente fallecido.');
            //agregado por cristian .. para cuando paciente esta fallecido se vuelva digitar rut
            $("#txtRutP").val("")
			$("#txtRutP").focus();
            return ;
          }
          
          rellenarFormulario(paciente);
          rellenarPatologias(patologias);
          prioridadAtencionxEdad(fecha[0]);
          $("#txtRutP").val($.formatRut($("#txtRutP").val()));
         
        }
      }
      catch (e) {
        handlerMessageError(e.message);

      }
    },
    error: function(msg) {
      handlerMessageError('No se encontró al paciente.');
    }
  });
}


function filtrarByIdPaciente(idPaciente) {//rut
  $("#tbodyFiltro").empty();
  if (idPaciente === undefined || idPaciente === null) {
    return;
  }
  $.ajax({
    type: "post",
    url: gCte.getctx + '/api/paciente/' + idPaciente,
    success: function(rpta) {
      try {
        console.table(rpta.dato);//
        let paciente = rpta.dato.dp;
        //        let contacto = rpta.dato.dc;
        if (paciente.Rut === "0") {
          document.getElementById("AgregarPac").click();
        } else {

          let tDoc = 'PASAPORTE';

          switch (paciente.ldvTiposdocumentos.toString()) {
            case gCte.TipoDocRun:
              tDoc = 'RUN';
              $('#divTxtRutFiltro').show();
              $('#divTxtPasaporteFiltro').hide();
              $('#divTxtDniFiltro').hide();
              break;
            case gCte.TipoDocPasaporte:
              $('#divTxtRutFiltro').hide();
              $('#divTxtPasaporteFiltro').show();
              $('#divTxtDniFiltro').hide();
              break;
            case gCte.TipoDocRecienNacido:
              tDoc = 'Recien nacido';
              $('#divTxtRutFiltro').hide();
              $('#divTxtPasaporteFiltro').hide();
              $('#divTxtDniFiltro').hide();
              break;
            case gCte.TipoDocSinIdentificacion:
              tDoc = 'Sin identificación';
              $('#divTxtRutFiltro').hide();
              $('#divTxtPasaporteFiltro').hide();
              $('#divTxtDniFiltro').hide();
              break;
              case gCte.TipoDocDni:
                let tDoc = 'DNI';
                $('#divTxtRutFiltro').hide();
                $('#divTxtPasaporteFiltro').hide();
                $('#divTxtDniFiltro').show();
                break;
            default:
              break;
          }

          $("#tipoDocLabel").text(tDoc);
          $('#txtRutP').val(paciente.dpRun);
          $('#txtPasaporte').val(paciente.dpNrodocumento);
          // $('#txtDni').val(paciente.dpDni);
          $("#selectTipoDocumento").val(paciente.ldvTiposdocumentos);

          rellenarFormulario(paciente);
          rellenarPatologias(rpta.dato.listaPatologias);
        }
      } catch (e) {
        console.log(e);
        handlerMessageError(e);
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError(msg);
    }
  });
}


 function filtrarByRutRest(rutPaciente) {//rut // obtener los examens validos

  filtrarByTipoNroDocRest(rutPaciente, gCte.TipoDocRun);
  return;
}


function filtrarByPasaporteRest(pasaporte) {//rut // obtener los examens validos

  filtrarByTipoNroDocRest(pasaporte, gCte.TipoDocPasaporte);
}



function filtrarByDniRest(dni) {//rut // obtener los examens validos

  filtrarByTipoNroDocRest(dni, gCte.TipoDocDni);
}
