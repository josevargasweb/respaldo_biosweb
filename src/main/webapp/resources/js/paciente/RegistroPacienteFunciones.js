/**
 * 
 */

const TipoDocRun = "1";
const TipoDocPasaporte = "2";
const TipoDocRecienNacido = "4";
const TipoDocSinIdentificacion = "5";
const TipoDocDni = "6";
const TipoDocTodos = "-1";
const ModoAgregacion = "Agregacion";
const ModoEdicion = "Edicion";
const ModoVisualizacion = "Visualizacion";
const ModoBusqueda = "Busqueda";

var gMapCamposObligatorios = new Map();
let modoVigente = ModoAgregacion;



function setFormLocalStorage() {
  let pasaporte = $("#txtPasaporte").val();
  let rut = $("#txtRut").val();
  let aux = getWithExpiry('validadorBuscarPac');
  if (aux === 'reasignacionOrden2') {
    if (rut === '') {
      setLocalPaciente2(pasaporte);
    }
    else {
      setLocalPaciente2(rut);
    }
  }
  else {
    if (rut === '') {
      setLocalPaciente(pasaporte);
    }
    else {
      setLocalPaciente(rut);
    }
  }

  aux = getWithExpiry('');
  if (aux === 'S') {
    setWithExpiry('creandoOrden', 'OK', 100000);
    $("#formRegistroPaciente").submit();
  }

  aux = getWithExpiry('buscandoOrden');
  if (aux === 'S') {
    setWithExpiry('buscandoOrden', 'OK', 100000);
    $("#ipEquipo").val("Postback");
    $("#formRegistroPaciente").submit();
  }

};

function generarEdad() {
  if (validarFechaNacimientoMenorFechaActual() === false) {
    return;
  }
  if (validarEdadMaxima() === false) {

    handlerMessageError('No puede tener más de 150 años');
    return;
  }


  let valorSelectTipoDocumento = $("#selectTipoDocumento").val();

  if (valorSelectTipoDocumento === TipoDocSinIdentificacion) {
    $("#txtAnio").val("");
    $("#txtMeses").val("");
    $("#txtDias").val("");
    return;
  }
  var edad = getAge($("#txtFechaNacimiento").val(), 3);
  var array = edad.split("-");
  $("#txtAnio").val(array[0]);
  $("#txtMeses").val(array[1]);
  $("#txtDias").val(array[2]);
  edad = calcularEdadTM($("#txtFechaNacimiento").val());
  $("#txtEdad").val(edad);


}

function obtenerEdad() {
  if (validarFechaNacimientoMenorFechaActual() === false) {
    return;
  }
  let valorSelectTipoDocumento = $("#selectTipoDocumento").val();

  if (valorSelectTipoDocumento === TipoDocSinIdentificacion) {
    $("#txtAnio").val("");
    $("#txtMeses").val("");
    $("#txtDias").val("");
    return;
  }
  var edad = getAge($("#txtFechaNacimiento").val(), 3);
  var array = edad.split("-");
  return array[0];
}

function buscarRut(a) {
  let $row = $(a).closest("tr"); // Find the row
  let select = $("#selectTipoDocumentoFiltro :selected").val();
  if (select === 4) {
    let idPaciente = $row.find(".idPaciente").text(); // Find the text
    getPacientexId(idPaciente);
  }
  else {
    let rut = $row.find(".rutFiltro").text(); // Find the text
    filtrarByRut(rut);
  }
}

function buscarComuna(idRegion, idComuna = false) {
  $.ajax({
    type: "post",
    data: "getComunas='s'&idRegion=" + idRegion,
    datatype: "json",
    success: function(comunas) {
      try {
        $("#selectComuna").empty();
        let listaComunas = JSON.parse(comunas);
        console.table(listaComunas.comunas);
        listaComunas.comunas.forEach(function(comuna) {
          $("#selectComuna").append("<option value='" + comuna.idComuna + "' >" + comuna.nombreComuna + "</option>");
        });
        console.log(idComuna);
        if (idComuna != null) {
          $("#selectComuna").val(idComuna);
        }
        $('.selectpicker').selectpicker('refresh');
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


function activarBtnEdit() {
  console.log('activaEdit');
  $("#iEditPaciente").removeClass("text-dark-50");
  // $("#iEditPaciente").addClass("text-primary");
  $("#iEditPaciente").addClass("text-blue");
  localStorage.setItem("botonEditar", "activo");
}

function desactivarBtnEdit() {
  console.log('desactivaEdit');
  $("#circuloEditarPaciente").removeClass("bg-hover-blue");
  $("#iEditPaciente").removeClass("text-blue", "text-primary");
  $("#iEditPaciente").addClass("text-dark-50");
  localStorage.setItem("botonEditar", "inactivo");
}


function activarLimpiar() {
  $("#iLimpiar").removeClass("text-dark-50");
  $("#iLimpiar").addClass("text-primary");
  localStorage.setItem("botonLimpiar", "activo");
}

function desactivarLimpiar() {
  $("#iLimpiar").addClass("text-dark-50");
  $("#iLimpiar").removeClass("text-primary");
  localStorage.setItem("botonLimpiar", "inactivo");
}


function restCallGetPacienteXRut(sRut) {

  try {
    if (sRut !== null && sRut !== "") {
      sRut = sRut.replaceAll('.', '');
      $.ajax({
        type: "get",
        url: getctx + "/api/paciente/" + sRut,
        success: function(response) {
          try {
            console.table(response);//
            var infoPaciente = response;
            if (infoPaciente.status === 404) {
              console.log('entra madre');
              handlerMessageError("Se debe registrar a la madre primero");
            }
            $("#dpIdmadre").val(response.dato.dp.dpIdpaciente)
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
  } catch (e) {
    handlerMessageError(e);
  }
}

function submitPaciente() {
  try {

    const nombres = $("#txtNombre").val();
    const primerApellido = $("#txtPrimerApellido").val();
    let fechaNacimiento = $("#txtFechaNacimiento").val();
    let rut = "";

    if (!validarGuardar()) {
      return false;
    }

    if ($("#txtRut").val().length <= 0 && $("#txtDni").val().length <= 0 && $("#txtPasaporte").val().length > 0) {
      rut = $("#txtPasaporte").val();
    } else if ($("#txtDni").val().length <= 0 && $("#txtPasaporte").val().length <= 0 && $("#txtRut").val().length > 0) {
      rut = $("#txtRut").val();
    } else {
      rut = $("#txtDni").val();
    }

    fechaNacimiento = formatearISO8601(fechaNacimiento, '/');

    $("#dpFnacimiento").val(fechaNacimiento);
    $("#txtFechaNacimiento").val(fechaNacimiento);

    if (getWithExpiry('validador') === '1') {
      setLocalPaciente({
        rut: rut,
        nombre: nombres,
        apellido: primerApellido,
        apellido2: $("#txtSegundoApellido").val(),
        edad: fechaNacimiento
      });
      scrollToAnchor('kt_tab_pane_1');
      return true;
    }
    if (fechaNacimiento === undefined || fechaNacimiento === null || fechaNacimiento === "") {
      let today = new Date();
      $("#dpFnacimiento").val(formatearISO8601(today.toLocaleDateString(), '/'));
      $("#txtFechaNacimiento").val(formatearISO8601(today.toLocaleDateString(), '/'));
    }

  } catch (err) {
    handlerMessageError(err);
    return false;
  }
}

function setModoAgregacion() {
  modoVigente = ModoAgregacion;
  limpiarFormulario();
  limpiarFiltro();
  setModoRegistroCliente(ModoAgregacion);

}


function goBack() {
  window.history.back();
}

function GetComputerName() {
  var network = new ActiveXObject('WScript.Network');
  alert(network.computerName);
}

function limpiarBusqueda() {
  limpiarFormulario();
}

function scrollToAnchor(aid) {
  //    var aTag = $("a[name='"+ aid +"']");
  //    $('html,body').animate({scrollTop: aTag.offset().top},'slow');
}


function setOk() {
  $(this).removeClass('is-invalid');
  if (validarParaSpan()) {
    $("#spanDatosBasicos").remove();
  }
}

function setSpan() {
  if (validarParaSpan()) {
    $("#spanDatosBasicos").remove();
  }
}

function getPacientexId(a) {
  let $row = $(a).closest("tr"); // Find the row
  let idPaciente = $row.find(".idPaciente").text(); // Find the text

  $("#tbodyFiltro").empty();
  $.ajax({
    type: "post",
    url: gCte.getctx + '/api/paciente/' + idPaciente,
    success: function(rpta) {
      try {
        if (rpta.status != 200) {
          $('html, body').animate({
            scrollTop: $("div#divForm").offset().top
          }, 700);
          $("#confirmarDatos").hide();
          handlerMessageError(rpta.message);
          return;
        }
        //          $('#txtRut').val(paciente.RutV);

        console.table(rpta.dato);//
        let paciente = rpta.dato.dp;
        let contacto = rpta.dato.dc;
        $("#nDocumento").text('N° DOCUMENTO');
        if (paciente.dpRun === "0") {
          $('html, body').animate({
            scrollTop: $("div#divForm").offset().top
          }, 700);
          //          $('#txtRut').val(paciente.RutV);
          $("#confirmarDatos").hide();
          handlerMessageError('No se encontraron pacientes');
        } else {
          //RELLENAR FORMULARIO
          rellenarFormulario(paciente, contacto, rpta.dato.rutMadre);
          $("#divBtnEditar").show();
          $("divIdCheckBoxExitus").show();
          $("#divBtnLimpiar").hide();
          $('html, body').animate({
            scrollTop: $("div#divForm").offset().top
          }, 700);

          activarBtnEdit();
          generarEdad();
          localStorage.setItem("editar", "no");

          if (paciente.ldvRegiones != undefined) {
            $("#selectRegion").val(paciente.ldvRegiones);
            let idRegion = $("#selectRegion :selected").val();
            let idComuna = paciente.ldvComunas.toString();
            setDDLBComuna(idRegion, idComuna);
          }
          $("#panelBusquedaPaciente").collapse('hide')

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

function setDDLBComuna(idRegion, idComuna) {
  $.ajax({
    type: "post",
    data: "getComunas='s'&idRegion=" + idRegion,
    datatype: "json",
    success: function(comunas) {
      try {
        $("#selectComuna").empty();
        let listaComunas = JSON.parse(comunas);
        console.table(listaComunas.comunas);
        listaComunas.comunas.forEach(function(comuna) {
          $("#selectComuna").append("<option value='" + comuna.idComuna + "' >" + comuna.nombreComuna + "</option>");
        });
        $("#selectComuna").val(idComuna);
        $('.selectpicker').selectpicker('refresh');
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

async function wait() {
  await (new Promise((resolve) => setTimeout(resolve, 5000)));
  return 10;
}



async function getEventosPacienteById(idPaciente) {
  const eventos = await $.ajax({
    type: "get",
    url: getctx + "/api/paciente/eventos/" + idPaciente,
    datatype: "json",
    success: function(response) {
      return response
    },

    error: function(msg) {
      console.log(msg);
      handlerMessageError(msg);
    }
  });

  return eventos;
}



