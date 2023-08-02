

$(document).ready(function() {
  let qryString = window.location.search;
  let origen = qryString.substring("origen=".length + 1);
  let custom_cel_options = {
    translation: {
      0: { pattern: /\d/ },
      X: { pattern: /[29]/ },
    },
    placeholder: "+56 _ ____ ____",
  };

  $(".phone_cl").mask("+56 X-0000-0000", custom_cel_options);

  let custom_mail_options = {
    translation: {
      E: { pattern: /[\w@\-.+]/, recursive: true },
    },
    placeholder: "",
  };

  $(".email_cl").mask("E", custom_mail_options);

  $(".selectpicker").selectpicker({
    noneSelectedText: ''
  });
  $('#selectRegion').selectpicker('val', '');
  $('#selectComuna').selectpicker('val', '');
  $('#selectPacienteRelacion').selectpicker('val', '');
  $('#selectSexoContacto').selectpicker('val', '');

  // Seteo inicial de la visualizacion, manjeo de eventos de teclado y eventos focus.

  loadInit();

  /************* Viene de Orden **********************************/
  if (localStorage.getItem("Origen") === "Buscar paciente") {
    $("#panelBusquedaPaciente").collapse("show");
    $("#formRegistroPaciente").prop("disabled", true);
    $("#btnConfirmarDatosPac").show();
    $("#btnCancelEdit").css("display", "inline-block");
    $("#btnAgregarPaciente").hide();
    $("#btnLimpiar").hide();
    $("#btnEditar").hide();
    $("#buscarPaciente").hide();
  } else if (localStorage.getItem("Origen") === "Agregar paciente") {
    $("#headingOne8").hide();
    $("#panelBusquedaPaciente").hide();
    //   $("#panelBusquedaPaciente").collapse('hide');
    //   $("#panelSuperior").prop('disabled', true);
    $("#btnConfirmarDatosPac").hide();
    $("#btnLimpiar").hide();
    $("#btnEditar").hide();
    $("#buscarPaciente").hide();
    $("#btnAgregarPaciente").show();
    $("#btnCancelEdit").css("display", "inline-block");
    localStorage.setItem("Origen", "Registrar paciente");
  } else if (localStorage.getItem("Origen") === "Registrar paciente") {
    let params = new URL(document.location).searchParams;
    let idPaciente = params.get("idPaciente"); // is the string "Jonathan Smith".
    setLocalPaciente(idPaciente);
    console.log(idPaciente);

    window.location.href = getctx + "/Orden";
  } else {
    if (origen === "nuevo") {
      $("#panelBusquedaPaciente").collapse("hide");
      $("#txtRutFiltro").focus();
    } else if (origen === "antiguo") {
      $("#panelBusquedaPaciente").collapse("show");
      $("#txtRut").focus();
    }
  }

  $(".disabledForm").prop("disabled", true);
  $("#selectTipoDocumentoFiltro").val(TipoDocRun);
  $("#selectTipoDocumento").val(TipoDocRun);
  $("#selectPais").val("38");
  $(".selectpicker").selectpicker("refresh");
  $("#checkboxAfro").val("0");
  $("#checkboxVIP").val("0");
  $("#checkBoxExitus").hide();
  $("#divIdCheckBoxExitus").hide();

  localStorage.setItem("editar", "no");
  localStorage.setItem("botonEditar", "inactivo");
  localStorage.setItem("botonLimpiar", "inactivo");

  //MENSAJES DE ERROR
  let mensaje = getUrlParameter("message");
  if (mensaje !== undefined) {
    if (mensaje.includes("Paciente ingresado correctamente")) {
      handlerMessageOk(mensaje);
      $("#liDatosMadres").hide();
      $("#divTxtRut").show();
      $("#divTxtRut").hide();
      $("#divTxtPasaporte").hide();
      $(".disabledForm").prop("disabled", false);
    } else {
      handlerMessageError(mensaje);
    }
  }
  let menosRut = getUrlParameter("menosRut");
  if (menosRut === "1") {
    $("#selectTipoDocumentoFiltro option[value='1']").remove();
    $("#selectTipoDocumentoFiltro").selectpicker("refresh");
    $("#selectTipoDocumento option[value='1']").remove();
    $("#selectTipoDocumento").selectpicker("refresh");
  }
  $("#btnUpdatePaciente").click(updateCliente);

  const input1 = new ImaskInputCaracter('txtNombre', 20);
  const input2 = new ImaskInputCaracter('txtPrimerApellido', 20);
  const input3 = new ImaskInputCaracter('txtSegundoApellido', 20);
});

agregarIconoFunciones('.icon-buenos',[anadir_circulo_icon]);

const targetElements = document.querySelectorAll(".circulo-plus");
targetElements.forEach((element) => {
  element.innerHTML = anadir_circulo_icon();
});

const targetCleanElements = document.querySelectorAll(".limpiar-icon");
targetCleanElements.forEach((element) => {
  element.innerHTML = limpiar_icon();
});

const targetSearchElements = document.querySelectorAll(".buscar-icon");
targetSearchElements.forEach((element) => {
  element.innerHTML = buscar_icon();
});

$(function() {
  $("#txtRut")
    .rut({ formatOn: "keyup", validateOn: "blur" })
    .on("rutInvalido", function() {
      if ($("#txtRut").val().length > 0) {
        $("#txtRut").removeClass("is-valid");
        $("#txtRut").addClass("is-invalid");
        ui.imprimirTooltip("#txtRut", "Rut inválido");
        $(".disabledForm").prop("disabled", true);
        $(".selectpicker").selectpicker("refresh");
      } else {
        ui.limpiarTooltip("#txtRut");
        $("#txtRut").removeClass("is-invalid");
        $(".disabledForm").prop("disabled", false);
        $(".selectpicker").selectpicker("refresh");
      }
    })
    .on("rutValido", function() {
      ui.limpiarTooltip("#txtRut");
      $("#txtRut").removeClass("is-invalid");
      $(".disabledForm").prop("disabled", false);
      $(".selectpicker").selectpicker("refresh");
    });

  $("#txtFiltroRut")
    .rut({ formatOn: "keyup", validateOn: "blur" })
    .on("rutInvalido", function() {
      if ($("#txtFiltroRut").val().length > 0) {
        $("#txtFiltroRut").addClass("is-invalid");
        $("#btnBuscarFiltro").addClass("disabled");
        // handlerMessageError('Rut inválido');
        ui.imprimirTooltip("#txtFiltroRut", "Rut inválido");
      } else {
        $("#txtFiltroRut").removeClass("is-invalid");
        $("#btnBuscarFiltro").removeClass("disabled");
        ui.limpiarTooltip("#txtFiltroRut");
      }
    })
    .on("rutValido", function() {
      $("#txtFiltroRut").removeClass("is-invalid");
      $("#btnBuscarFiltro").removeClass("disabled");
      ui.limpiarTooltip("#txtFiltroRut");
    });
  $("#txtRutMadre")
    .rut({ formatOn: "keyup", validateOn: "blur" })
    .on("rutInvalido", function() {
      $("#divFeedbackRutMadre").text("RUN Inválido");
      $("#txtRutMadre").addClass("is-invalid");
    })
    .on("rutValido", function() {
      $("#txtRutMadre").removeClass("is-invalid");
      ui.limpiarTooltip("#txtRutMadre");
      //  restCallGetPacienteXRut($("#txtRutMadre").val());
      let datos = restCallGetPacienteXRut($("#txtRutMadre").val());



    });

  ui.validarNombres("#txtFiltroNombre");
  ui.validarNombres("#txtFiltroApellido");

  ui.validarNombres("#txtNombre");
  ui.validarNombres("#txtPrimerApellido");

  ui.validarEmail("#txtEmail");

  $("#txtNroPartoMultiple").blur(function() {
    if ($(this).val() <= 0) {
      ui.imprimirTooltip($(this), "Parto múltiple inválido");
      $(this).addClass("is-invalid");
    } else {
      ui.limpiarTooltip("#txtNroPartoMultiple");
      $(this).removeClass("is-invalid");
    }
  });



  $("#txtNombreContacto").on('blur focus', function() {
    if (typeof $(this).attr('data-custom-class') !== 'undefined') {
      ui.limpiarTooltip($(this));
      $(this).removeClass("is-invalid");
    }
  });
  $("#txtTelefonoContacto").blur(function() {
    if (typeof $(this).attr('data-custom-class') !== 'undefined') {
      ui.limpiarTooltip($(this));
      $(this).removeClass("is-invalid");
    }
  });

  $("#idModalPacienteNuevo").find('.btn-no').click(function(e) {
    $("#idModalPacienteNuevo").removeAttr("data-set");
  });

  $("#idModalPacienteNuevo").find('.btn-si').click(function(e) {
    console.log($("#selectTipoDocumentoFiltro").val())
    let val = $("#selectTipoDocumentoFiltro").val();
    let documento;
    if (val == 1) {
      documento = $('#txtFiltroRut').val();
    } else {
      documento = $('#txtPasaporteFiltro').val();
    }
    $('#iAgregarPaciente').click();

    $("#selectTipoDocumento").val(val);
    $(".selectpicker").selectpicker("refresh");
    $("#selectTipoDocumento").change();
    if (val == 1) {
      $('#txtRut').val(documento);
    } else {
      $('#txtPasaporte').val(documento);
    }
    $("#idModalPacienteNuevo").modal('toggle');

  });

  var today = new Date();
  var minDate = new Date(today.getFullYear() - 150, today.getMonth(), today.getDate());

  $('#txtFechaNacimiento').datepicker({
    endDate: "today",
    format: "dd/mm/yyyy", // formato de fecha dd/mm/aaaa
    startDate: minDate // fecha mínima permitida
  });
});

ui.normalTooltip($("#circuloAgregarPaciente"));
ui.normalTooltip($("#circuloLimpiarFiltro"));
ui.normalTooltip($("#circuloEditarPaciente"));
ui.normalTooltip($("#circuloBuscarPaciente"));
ui.normalTooltip($("#circuloLimpiar"));

$("#txtRutMadre").change(function() {
  // restCallGetPacienteXRut($("#txtRutMadre").val());
});

/*********** Botones circulares ****************/
$("#circuloLimpiar").hover(
  function() {
    $("#iLimpiar").removeClass("text-primary");
    $("#iLimpiar").addClass("text-white");
  },
  function() {
    $("#iLimpiar").removeClass("text-white");
    $("#iLimpiar").addClass("text-primary");
  }
);
$("#circuloLimpiarFiltro").hover(
  function() {
    $("#iLimpiarFiltro").removeClass("text-primary");
    $("#iLimpiarFiltro").addClass("text-white");
  },
  function() {
    $("#iLimpiarFiltro").removeClass("text-white");
    $("#iLimpiarFiltro").addClass("text-primary");
  }
);
$("#circuloAgregarPaciente").hover(
  function() {
    $("#iAgregarPaciente").removeClass("text-primary");
    $("#iAgregarPaciente").addClass("text-white");
  },
  function() {
    $("#iAgregarPaciente").removeClass("text-white");
    $("#iAgregarPaciente").addClass("text-primary");
  }
);

$("#circuloEditarPaciente").hover(
  function() {
    if (localStorage.getItem("botonEditar") === "activo") {
      $("#circuloEditarPaciente").addClass("bg-hover-blue");
      $("#iEditPaciente").removeClass("text-blue", "text-primary");
      // $("#iEditPaciente").addClass("text-white");
      $("#iEditPaciente").addClass("text-blue");
    }
  },
  function() {
    if (localStorage.getItem("botonEditar") === "activo") {
      $("#iEditPaciente").removeClass("text-white");
      $("#iEditPaciente").addClass("text-blue", "text-primary");
    }
  }
);

$("#circuloBuscarPaciente").hover(
  function() {
    $("#iBuscarPaciente").removeClass("text-primary");
    $("#iBuscarPaciente").addClass("text-white");
  },
  function() {
    $("#iBuscarPaciente").removeClass("text-white");
    $("#iBuscarPaciente").addClass("text-primary");
  }
);

$("#txtFiltroNombre, #txtFiltroApellido, #txtFiltroSegundoApellido").keyup(
  function(input) {
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
  }
);

$("#txtFiltroRut, #txtPasaporteFiltro, #txtFiltroNN").keyup(function(input) {
  if (input.which === 13) {
    $("#btnBuscarFiltro").click();
  }
});

$("#btnEditar").click(function() {
  if (
    $("#txtIdPaciente").val() !== "0" &&
    localStorage.getItem("botonEditar") === "activo"
  ) {
    $("#btnAgregarPaciente").hide();
    $("#btnUpdatePaciente").css("display", "inline-block");
    $("#checkBoxExitus").removeClass("ocultar");
    $("#divIdCheckBoxExitus").show();

    setModoRegistroCliente(ModoEdicion);
    $(".selectpicker").selectpicker("refresh");
  }
});

$("#btnCancelEdit").click(function() {
  if (
    localStorage.getItem("Origen") === "Registrar paciente" ||
    localStorage.getItem("Origen") === "Buscar paciente"
  ) {
    window.location.href = getctx + "/Orden";
  } else {
    setModoRegistroCliente(ModoVisualizacion);
  }

  //recargarDatosOriginales
});

$("#txtFechaNacimiento").change(function() {
  if (validarEdadMinima() === true) {
    $("#spanContacto").show();
  } else {
    $("#spanContacto").hide();
  }
  generarEdad();
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

$("#txtPasaporte").keyup(function(event) {
  if ($("#txtPasaporte").val().length > 0) {
    $(".disabledForm").prop("disabled", false);
    $(".selectpicker").selectpicker("refresh");
    if (event.code == "Enter") {
      filtrarByPasaporte($("#txtPasaporte").val());
    }
  }
});

$("#txtPasaporte").blur(function(event) {
  console.log("blur");
  if ($("#txtPasaporte").val().length > 0) {
    $(".disabledForm").prop("disabled", false);
    $(".selectpicker").selectpicker("refresh");
    filtrarByPasaporte($("#txtPasaporte").val());
  }
});

$("#selectTipoDocumentoFiltro").change(function() {
  habilitarFiltro();
  console.log("asdasdasdasd")
});

$("#selectSexo").change(function() {
  $("#selectSexo")
    .removeClass("is-invalid")
    .selectpicker("setStyle")
    .parent()
    .removeClass("is-invalid");
  // $('#selectSexo').selectpicker('setStyle', 'is-invalid', 'remove');
  $("#selectSexo").selectpicker("refresh");

  $("#txtFechaNacimiento").focus();
});

$("#btnBuscarFiltro").click(clickBotonBuscarFiltro);

$("#nuevoPaciente").click(function(event) {
  event.preventDefault();
  $("#panelBusquedaPaciente").collapse("hide");
  $("#panelRegistroPaciente").collapse("show");
  $("html, body").animate(
    {
      scrollTop: $("div#divForm").offset().top,
    },
    700
  );
  setModoAgregacion();
  //  $("#panelBusquedaPaciente").collapse('hide');
  $("#txtRut").focus();
});

$("#buscarPaciente").click(function() {
  $("#panelBusquedaPaciente").collapse("show");
  $("#panelRegistroPaciente").collapse("hide");
  $("html, body").animate(
    {
      // scrollTop: $("div#panelSuperior").offset().top
    },
    700
  );
});

$("#checkboxVIP").click(function() {
  if ($("#checkboxVIP").is(":checked")) {
    $("#checkboxVIP").val("1");
  } else {
    $("#checkboxVIP").val("0");
  }
});


$("#txtRut").blur(function() {
  if (!chequearClase("#txtRut", "is-invalid")) {
    if ($("#txtRut").val() !== null && $("#txtRut").val().trim() !== "") {
      filtrarByRut($("#txtRut").val());
    }
  }
}).keyup(function(input) {
  if (input.which === 13) {
    if ($("#txtRut").val() !== null && $("#txtRut").val().trim() !== "") {
      filtrarByRut($("#txtRut").val());
    }
  }
  input.preventDefault();
});




$("#checkboxAfro").click(function() {
  if ($("#checkboxAfro").is(":checked")) {
    $("#checkboxAfro").val("1");
  } else {
    $("#checkboxAfro").val("0");
  }
});

$("#checkBoxExitus").click(function() {
  if ($("#checkBoxExitus").is(":checked")) {
    $("#checkBoxExitus").val("1");
  } else {
    $("#checkBoxExitus").val("0");
  }
});

$("#checkboxPartoMultiple").click(function() {
  if ($("#checkboxPartoMultiple").is(":checked")) {
    $("#checkboxPartoMultiple").val("S");
    $("#txtNroPartoMultiple").prop("disabled", false);
  } else {
    $("#checkboxPartoMultiple").val("N");
    $("#txtNroPartoMultiple").prop("disabled", true);
    $("#txtNroPartoMultiple").removeClass("is-invalid").val("");
    ui.limpiarTooltip("#txtNroPartoMultiple");
  }
});

/******************************** Patologías************************ */
$("#btnPatologia").click(function() {
  let cont = 0;

  $("#tbodyPatologias")
    .find("tr")
    .each(function() {
      cont++;
    });
  if (cont <= 0) {
    if ($("#selectPatologias :selected").val() !== "N") {
      $("#tbodyPatologias").append(
        "<tr><td><input type='hidden' value=" +
        $("#selectPatologias :selected").val() +
        " name='liPatologias' /> " +
        $("#selectPatologias :selected").text() +
        "</td><td><div class='form-group'><div class='col-md-12'><input name='observacionesPatologias' type='text' class='form-control' /></div></div></td><td><a type='button' onclick='removeRow(this)' class='pointer' ><i class='la la-trash icon-xl text-danger'></i></a></li></tr>"
      );
    }
  } else {
    let array = new Array();
    let validador = 0;
    $("#tbodyPatologias >tr").each(function(i, item) {
      let tds = $(this).find("td:eq(0)");
      array.push($(tds).text().trim());
      array.forEach(function(texto) {
        if (texto === $("#selectPatologias :selected").text().trim()) {
          validador++;
        }
      });
    });
    if (validador > 0) {
    } else {
      if ($("#selectPatologias :selected").val() !== "N") {
        $("#tbodyPatologias").append(
          "<tr><td><input type='hidden' value=" +
          $("#selectPatologias :selected").val() +
          " name='liPatologias' /> " +
          $("#selectPatologias :selected").text() +
          "</td><td><div class='form-group'><div class='col-md-12'><input name='observacionesPatologias' type='text' class='form-control' /></div></div></td><td><a type='button' onclick='removeRow(this)' class='pointer' ><i class='la la-trash icon-xl text-danger'></i></a></li></tr>"
        );
      }
    }
  }
});

/******************************** Patologías************************ */

$("#selectTipoDocumento").change(function() {
  $("#txtFechaNacimiento").val("");
  if (
    $("#selectTipoDocumento :selected").val() === TipoDocSinIdentificacion
  ) {
    $("#divTxtDni").hide();
    $("#liDatosMadres").hide();
    $("#divTxtRut").hide();
    $("#divTxtPasaporte").hide();
    $(".disabledForm").prop("disabled", false);
    $("#txtFechaNacimiento").removeClass("is-invalid");
    $("#txtEdad").val("");
  } else if (
    $("#selectTipoDocumento :selected").val() === TipoDocRecienNacido
  ) {
    // $("#spanDatosMadre").show();
    $("#divTxtDni").hide();
    $("#lblRunMadre").text("RUN Madre *");
    $("#antecedentesRN").show(500);
    $("#divTxtRut").hide();
    $("#divTxtPasaporte").hide();
    $("#liDatosMadres").show();
    $(".disabledForm").prop("disabled", false);
    let hoy = new Date();
    $("#txtFechaNacimiento").val(convertirDateDDMMYYYY(hoy));
    let edad = getAge($("#txtFechaNacimiento").val(), 3);
    let array = edad.split("-");
    $("#txtAnio").val(array[0]);
    $("#txtMeses").val(array[1]);
    $("#txtDias").val(array[2]);
    $("#txtEdad").val(
      array[0]
        .concat("a ")
        .concat(array[1])
        .concat("m ")
        .concat(array[2])
        .concat("d")
    );
    $("#txtFechaNacimiento").removeClass("is-invalid");

  } else if ($("#selectTipoDocumento :selected").val() === TipoDocPasaporte) {
    $("#divTxtDni").hide();
    $("#liDatosMadres").hide();
    $("#divTxtRut").hide();
    $("#divTxtRut").hide();
    $("#divTxtPasaporte").show();
    $(".disabledForm").prop("disabled", false);

    $("#txtEdad").val("");
  } else if ($("#selectTipoDocumento :selected").val() === TipoDocDni) {
    $("#liDatosMadres").hide();
    $("#divTxtRut").hide();
    $("#divTxtRut").hide();
    $("#divTxtDni").show();
    $("#divTxtPasaporte").hide();
    $(".disabledForm").prop("disabled", false);

    $("#txtEdad").val("");
  } else if ($("#selectTipoDocumento :selected").val() === TipoDocRun) {
    $("#divTxtDni").hide();
    $("#liDatosMadres").hide();
    $("#divTxtRut").show();
    // $("#divTxtRut").css("visibility", "visible");
    $("#divTxtPasaporte").hide();
    $(".disabledForm").prop("disabled", false);
    $("#txtEdad").val("");
  } else {
    $("#lblRutFiltro").text("RUN");
    $("#lblRunMadre").text("RUN Madre");
    // $("#divTxtRut").css("visibility", "visible");
    $("#antecedentesRN").hide(500);
    $(".disabledForm").prop("disabled", true);
    $("#txtEdad").val("");
  }
  $(".selectpicker").selectpicker("refresh");
});

$("#btnLimpiarFiltro").click(limpiarFiltro);

$("#btnLimpiar").click(function() {
  if (localStorage.getItem("botonLimpiar") === "activo") {
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
  }
});

$("#txtNombreContacto, #txtTelefonoContacto").keyup(function() {
  if (
    $("#txtNombreContacto").val().length > 0 &&
    $("#txtTelefonoContacto").val().length > 0
  ) {
    $("#spanContacto").hide();
  } else {
    $("#spanContacto").show();
  }
});

$("#divTxtRut").click(function(e) {
  if (localStorage.getItem("editar") === "si") {
    let nombre = $("#txtNombre").val();
    let apellido = $("#txtPrimerApellido").val();
    if (
      nombre.length > 0 &&
      apellido.length > 0 &&
      localStorage.getItem("editar") === "no"
    ) {
      Swal.fire({
        title: "¿Cambiar RUN?",
        text: "¿Estás seguro de cambiar RUN?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Sí'",
        cancelButtonText: "No, cancelar!",
        reverseButtons: true,
      }).then(function(result) {
        if (result.value) {
          localStorage.setItem("editar", "si");
          //$("#txtRut").prop("disabled", false);
          // result.dismiss can be "cancel", "overlay",
          // "close", and "timer"
        } else if (result.dismiss === "cancel") {
          localStorage.setItem("editar", "no");
        }
      });
    }
  }
});



$("#selectRegion").change(function() {
  let idRegion = $("#selectRegion :selected").val();
  buscarComuna(idRegion);
});

$("#txtNroPartoMultiple").keyup(function() {
  $.ajax({
    type: "post",
    data:
      "validarRnnumero=1&rnnumero=" +
      $("#txtNroPartoMultiple").val() +
      "&rutMadreRN=" +
      $("#txtRutMadre").val(),
    datatype: "json",
    success: function(mensaje) {
      try {
        let dato = JSON.parse(mensaje);
        if (dato === "ya existe") {
          $("#txtNroPartoMultiple").addClass("invalid");
          $("#btnAgregarPaciente").prop("disabled", true);
        } else {
          $("#txtNroPartoMultiple").removeClass("invalid");
          $("#btnAgregarPaciente").prop("disabled", false);
        }
      } catch (e) {
        console.log(e);
        handlerMessageError(e);
      }
    },
    error: function(msg) {
      $("#txtNroPartoMultiple").removeClass("invalid");
      $("#btnAgregarPaciente").prop("disabled", false);
      handlerMessageError(msg);
    },
  });
});

$("form").submit(submitPaciente);

$("#btnConfirmarDatosPac").click(function() {
  let rut = $("#txtRut").val();
  rut = $("#txtIdPaciente").val();
  if (rut === "") {
    setLocalPaciente(pasaporte);
  } else {
    setLocalPaciente(rut);
  }

  if (localStorage.getItem("Origen") === "Buscar paciente") {
    $("#formRegistroPaciente").submit();
    window.location.href = getctx + "/Orden";
  }
});

$("#btnCancelPaciente").click(function() {
  window.location.href = getctx + "/Orden";
});

$("#btnTrazabilidad").click(async function() {
  if ($("#txtNombre").val() === "" || $("#txtIdPaciente").val() == 0) {

    handlerMessageError("Debe Seleccionar un Paciente");
  } else {

    let dato = await getEventosPacienteById($("#txtIdPaciente").val())
    console.log(dato)
    let html = "";
    dato.dato.forEach(function(dato) {

      if (dato.lp_IDACCIONDATO == 1) {
        html += "<tr><td>" + (new Date(dato.lp_FECHAMODIFICACION)).toLocaleString() + "</td><td>" + (dato.du_NOMBRES == null ? "" : dato.du_NOMBRES)
          + " " + (dato.du_PRIMERAPELLIDO == null ? "" : dato.du_PRIMERAPELLIDO) + " " +
          (dato.du_SEGUNDOAPELLIDO == null ? "" : dato.du_SEGUNDOAPELLIDO) + "</td><td>" + dato.lp_IPUSUARIO +
          "</td><td> " + " Se creo paciente n°doc <b>" + dato.dp_NRODOCUMENTO + "</b></td></tr>"
      } else {
        html += "<tr><td>" + (new Date(dato.lp_FECHAMODIFICACION)).toLocaleString() + "</td><td>" + (dato.du_NOMBRES == null ? "" : dato.du_NOMBRES)
          + " " + (dato.du_PRIMERAPELLIDO == null ? "" : dato.du_PRIMERAPELLIDO) + " " +
          (dato.du_SEGUNDOAPELLIDO == null ? "" : dato.du_SEGUNDOAPELLIDO) + "</td><td>" + dato.lp_IPUSUARIO +
          "</td><td> Se modificó el campo  <b>" + dato.lp_CAMPOMODIFICADO + "</b> de <b>" + (dato.lp_VALORANTERIOR == null ? "---" : dato.lp_VALORANTERIOR)
          + "</b> a valor nuevo <b>" + (dato.lp_VALORNUEVO == null ? "---" : dato.lp_VALORNUEVO) + "</b></td></tr>"
      }

    })
    $("#idModalEventsPaciente").html(html)
    $("#eventosPacienteModal").modal("show")
  }

})


const urlParams = new URLSearchParams(window.location.search);
if (urlParams.has('BuscarPaciente')) {
  const buscarPaciente = urlParams.get('BuscarPaciente');
  $('#panelBusquedaPaciente').collapse('show');
  $('#panelRegistroPaciente').collapse('hide');
}else{
  $('#panelBusquedaPaciente').collapse('hide');
  $('#panelRegistroPaciente').collapse('show');
}