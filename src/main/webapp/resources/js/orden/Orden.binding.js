var dualDataBS;
var dualDataSource = new Array();
var dualDataTarget = new Array();
var vt;
var vs;
var visualSearcher;
var dataSearcher;
var demo;
var listaSexos;
var lstExamenesDisponibles;
var lstPanelesDisponibles;
var lstExamenesSeleccionados;

// Obtener los examenes con test comunes.


$(document).ready(function() {
  $("#btnPacienteBasico").click(habilitaPanelBasicoPaciente);
  $("#btnPacienteExtra").click(habilitaPanelExtraPaciente);
  $("#btnOrdenBasico").click(habilitaPanelBasicoOrden);
  $("#btnOrdenExtra").click(habilitaPanelExtraOrden);
  $("#btnGuardarOrden").click(insertarOrden);
  $("#btnLimpiarOrden").click(function() {
    limpiarTodo(vs, vt);
  });
  habilitaPanelBasicoPaciente();
  habilitaPanelBasicoOrden();
  localStorage.setItem("Origen", "");
  limpiarMedic();
  $("#idNumPage").val(0);
  $("#idPaciente").hide();
  $("#txtRutP").focus()
  $("#medicoComboBox").select2({ width: "100%" });
  $("#divTxtRutFiltro").show();
  $("#divTxtPasaporteFiltro").hide();
  $("#DiagnosticoComboBoxM").val("SIN ESPECIFICAR");
  $(".switch-orden input").change(cambiarEstadoSwitch);
  $(".switch-comprobante input").change(cambiarEstadoComprobante);
  $('#idSearcher').attr('autocomplete', 'off');
  const dpIdPaciente = localStorage.getItem("Rut");
  localStorage.setItem("Rut", 0);
  if (dpIdPaciente !== undefined && dpIdPaciente != 0) {
    $("#datosPacientes").val(dpIdPaciente);
    filtrarByIdPaciente(dpIdPaciente);
  }

  if (getWithExpiry("rutM") !== null) {
    var rutMed = getWithExpiry("rutM");
    $("#txtRutMedico").val(rutMed);
    filtrarByRutM(rutMed);
  }

  $(".ocultar").hide();

  $(function() {
    $("#txtRutP")
      .rut({ formatOn: "keyup", validateOn: "blur" })
      .on("rutInvalido", function() {
        var rut;
        rut = $("#txtRutP").val();
        $("#txtRutP").addClass("is-invalid");
        if (typeof $(this).attr("data-custom-class") === "undefined") {
          ui.imprimirTooltip($(this), "RUN no válido");
        }
        if (rut.length === 0) {
          $("#txtRutP").removeClass("is-invalid");
          if (
            typeof $(this).attr("data-custom-class") !== "undefined"
          ) {
            ui.limpiarTooltip($(this));
          }
        }
      })
      .on("rutValido", function(e) {
        var rut;
        rut = $("#txtRutP").val();
        $("#txtRutP").addClass("is-invalid");
        if (typeof $(this).attr("data-custom-class") === "undefined") {
          ui.imprimirTooltip($(this), "RUN no válido");
        }
        if (rut.length > 10) {
          $("#txtRutP").removeClass("is-invalid");
          // filtrarxRutRest();
          if (
            typeof $(this).attr("data-custom-class") !== "undefined"
          ) {
            ui.limpiarTooltip($(this));
          }
        }
      });
    $("#txtRutMedico")
      .rut({ formatOn: "keyup", validateOn: "blur" })
      .on("rutInvalido", function() {
        var rut;
        rut = $("#txtRutMedico").val();
        $("#txtRutMedico").addClass("is-invalid");
        if (typeof $(this).attr("data-custom-class") === "undefined") {
          ui.imprimirTooltip($(this), "RUN no válido");
        }
        if (rut.length === 0) {
          $("#txtRutMedico").removeClass("is-invalid");
          if (
            typeof $(this).attr("data-custom-class") !== "undefined"
          ) {
            ui.limpiarTooltip($(this));
          }
        }
      })
      .on("rutValido", function() {
        var rut;
        rut = $("#txtRutMedico").val();
        $("#txtRutMedico").addClass("is-invalid");
        if (typeof $(this).attr("data-custom-class") === "undefined") {
          ui.imprimirTooltip($(this), "RUN no válido");
        }
        if (rut.length > 10) {
          $("#txtRutMedico").removeClass("is-invalid");
          if (
            typeof $(this).attr("data-custom-class") !== "undefined"
          ) {
            ui.limpiarTooltip($(this));
          }
        }
      });
  });
  reglaTipoAtencion();

  // Nuevo manejo examenes. hay que seguir modularizando en dual-data.js
  vs = new VisualSource();
  var dtVT = new DataTableVisualTarget("#idDataTarget",vs);
  var dt = new DataSource([]);
  vt = new VisualTarget(dt, dtVT);
  test = genVSFiller("#idDualDataVisualSource", vs, "#idSearcher");
  testPaneles = genVSPanelFiller("#idPanelDualDataVisualSource", vs,"#idSearcher");

  var movedor = new Mover(dtVT, dt, vt, vs);

  vs.onDoubleClick(movedor);
  let svcPanel = new ItemPanelService();
  svcPanel.getPaneles(testPaneles);
  let svc = new CfgExamen();
  svc.getExamenes(test);
  
  function eventoAdd() {
    let itemId = vs.getSelectedItemId()
    let item = vs.getSelectedItemById(itemId)
    if (vs.visualElement.jqid !== "#idPanelDualDataVisualSource") {
      vs.removeItemById(itemId)
    }else{
      vs.removePanelItem(item)
    }
    vt.addItem(item)
  }

  function eventoDel() {
    let items = vt.getSelectedItems();
    vt.removeItems(items);
    vs.addItems(items);
  }

  function eventoLimpiar() {
    let items = vt.getItems();
    vt.removeItems(items);
    vs.addItems(items);
  }

  $("#clickAddBtn").click(eventoAdd);
  $("#clickDelBtn").click(eventoDel);
  $("#idModalPacienteNuevo").find(".btn-no").click(eventoLimpiar);
  $("#idPanelDualDataVisualSource").hide();
  $("#idBtnExamen").click(function(e) {
	console.log($("#idDualDataVisualSource"))
    $("#idDualDataVisualSource").show();
    $("#idPanelDualDataVisualSource").hide();
    vs.visualElement.jqid = "#idDualDataVisualSource";
    vs.swapDs2Examen();
  });
  $("#idBtnPanel").click(function(e) {
	console.log($( "#idPanelDualDataVisualSource"))
    $("#idPanelDualDataVisualSource").show();
    $("#idDualDataVisualSource").hide();
    vs.visualElement.jqid = "#idPanelDualDataVisualSource";
    vs.swapDs2Panel();
  });

  function cambiarEstadoSwitch() {
    var dad = $(this).parent();
    if ($(this).is(":checked")) {
      dad.addClass("switch-orden-checked");
      $("#idPanelDualDataVisualSource").show();
      $("#idDualDataVisualSource").hide();
      vs.visualElement.jqid = "#idPanelDualDataVisualSource";
      $('.label-panel').removeClass('text-black-50');
      $('.label-examen').addClass('text-black-50');
      vs.swapDs2Panel();
    } else {
      dad.removeClass("switch-orden-checked");
      $("#idDualDataVisualSource").show();
      $("#idPanelDualDataVisualSource").hide();
      vs.visualElement.jqid = "#idDualDataVisualSource";
      $('.label-panel').addClass('text-black-50');
      $('.label-examen').removeClass('text-black-50');
      vs.swapDs2Examen();
    }
  }

  function cambiarEstadoComprobante() {
    var dad = $(this).parent();
    if ($(this).is(":checked")) {
      $("#idOrderConfirmationModal").find("#checkBoxLeverCromp").val("S");
      $("#idOrderCreatedModal").find("#checkBoxLeverCromp").val("S");
      $("#idOrderConfirmationModal").find("#checkBoxLeverCromp").prop("checked", true);
      $("#idOrderCreatedModal").find("#checkBoxLeverCromp").prop("checked", true);

      $("#idOrderConfirmationModal").find("#txtEstado").val("S");

      $('.label-voucher').addClass('text-black-50');
      $('.label-comprobante').removeClass('text-black-50');
    } else {
      $("#idOrderConfirmationModal").find("#checkBoxLeverCromp").val("N");
      $("#idOrderCreatedModal").find("#checkBoxLeverCromp").val("N");
      $("#idOrderConfirmationModal").find("#checkBoxLeverCromp").prop("checked", false);
      $("#idOrderCreatedModal").find("#checkBoxLeverCromp").prop("checked", false);

      $("#idOrderConfirmationModal").find("#txtEstado").val("N");

      $('.label-voucher').removeClass('text-black-50');
      $('.label-comprobante').addClass('text-black-50');
    }
  }


  $(".selectpicker").selectpicker({
    noneSelectedText: "",
  });
  $(".SSM").selectpicker({
    noneSelectedText: "",
  });

  cargarAtencioOrden();
  cargarProcedenciaCombo();
  cargarServicioComboBoxM();
  cargarMedicos();
  cargarPriorarAtencion();
  cargarConvenio();
  cargarDiagnosticosC();
  $('.selectpicker').selectpicker('refresh');
  // Fin nuevo manejo examens

  $('#tipoDeAtencion').on('changed.bs.select', function() {
    addAtencion($(this).val());

    $(this).selectpicker('toggle');
    if ($(this).val() == "") {
      RegistroOden.tipoDeAtencion = -1;
    }
    if (RegistroOden.tipoDeAtencion == -1 && $(this).val() != "") {
      $('#tipoDeAtencion').selectpicker('val', '');
      $('button[data-id="tipoDeAtencion"]').focus();
      $('#tipoDeAtencion').selectpicker('toggle');
    }
    $(".SSM").val(0);

    $(this)
      .removeClass("is-invalid")
      .selectpicker("setStyle")
      .parent()
      .removeClass("is-invalid");

    ui.limpiarTooltip($(this));
    reglaTipoAtencion();

    $(this).selectpicker("refresh");

    cambiarFocusAtencionOrden();

  });


  $('#ProcedenciaComboBoxM').on('changed.bs.select', function() {
    addProcedencia(this.value);

    if ($(this).val() == "") {
      RegistroOden.ProcedenciaComboBoxM = -1;
    }
    if (RegistroOden.ProcedenciaComboBoxM == -1 && $(this).val() != "") {
      $('button[data-id="ProcedenciaComboBoxM"]').focus();
      $('#ProcedenciaComboBoxM').selectpicker('val', '');
      $('#ProcedenciaComboBoxM').selectpicker('toggle');

    } else if (!isNaN(RegistroOden.ProcedenciaComboBoxM) && $(this).val() != "") {
      $('button[data-id="medicoBox"]').focus();
      $('#medicoBox').selectpicker('toggle');
    }
    $(this)
      .removeClass("is-invalid")
      .selectpicker("setStyle")
      .parent()
      .removeClass("is-invalid");
    $(this).selectpicker("refresh");
  });


  $('#ServicioComboBoxM').on('changed.bs.select', function() {
    loadDataSalas($(this).val());
    $('#SalaComboBoxM').selectpicker('val', '');
    $('#CamaComboBoxM').selectpicker('val', '');
    $(this)
      .removeClass("is-invalid")
      .selectpicker("setStyle")
      .parent()
      .removeClass("is-invalid");
    if ($(this).val() == "") {
      RegistroOden.ServicioComboBoxM = -1;
      RegistroOden.SalaComboBoxM = null;
      RegistroOden.cama = null;
      $("#SalaComboBoxM").empty();
      $("#SalaComboBoxM").attr("disabled", true);
      $("#CamaComboBoxM").empty();
      $('#SalaComboBoxM').selectpicker('val', '');
      $("#CamaComboBoxM").attr("disabled", true);
      $('#CamaComboBoxM').selectpicker('val', '');
      $("#SalaComboBoxM").selectpicker('refresh');
      $("#CamaComboBoxM").selectpicker('refresh');
    }


    if (RegistroOden.ServicioComboBoxM == -1 && $(this).val() != "") {
      $('#ServicioComboBoxM').selectpicker('val', '');
      $('button[data-id="ServicioComboBoxM"]').focus();
      $('#ServicioComboBoxM').selectpicker('toggle');
    }

    if (!isNaN(RegistroOden.ServicioComboBoxM) && $(this).val() != "") {
      $('button[data-id="medicoBox"]').focus();
      $('#medicoBox').selectpicker('toggle');
    }

    $(this).selectpicker("refresh");
  });


  $('#medicoBox').on('changed.bs.select', function() {
    filtrarByIdM($(this).val());

    if ($(this).val() == "") {
      RegistroOden.medicoComboBox = null;
      $('#txtRutMedico').selectpicker('val', '');
      $('button[data-id="txtRutMedico"]').focus();
      $('#txtRutMedico').selectpicker('toggle');
    }
    if (RegistroOden.medicoComboBox != null) {
      $("#idSearcher").focus();
    }
  });


  $('#txtRutP').on('keydown', function(e) {
    if (e.key === 'Tab') {
      e.preventDefault();
    }

    if (e.key === 'Enter') {
      // Enfocar el siguiente campo
      var rut;
      rut = $("#txtRutP").val();

      if (rut.length > 10) {
        filtrarxRutRest();
      }
    }
  });


});

// FIn onReady

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

$(".circuloBuscarPaciente").hover(
  function() {
    $("#iBuscarPaciente").removeClass("text-primary");
    $("#iBuscarPaciente").addClass("text-white");
  },
  function() {
    $("#iBuscarPaciente").removeClass("text-white");
    $("#iBuscarPaciente").addClass("text-primary");
  }
);

$("#BuscarPac").click(function() {
  limpiarLocalStorage();
  localStorage.setItem("Origen", "Buscar paciente");
  //  $("#idModalBuscarPaciente").modal('show');
});

$("#AgregarPac").click(function() {
  limpiarLocalStorage();
  localStorage.setItem("Origen", "Agregar paciente");
});

$("#btnEditar").click(function(e) {
  const nombre = $("#txtNombreP").val();
  if (nombre.length === 0) {
    e.preventDefault();
  } else {
    const rut = $("#txtRutP").val();
    editarPacienteLocal(rut);
    setWithExpiry("validadorEditPac", "2", 100000);
    $('.selectpicker').selectpicker('refresh');
  }
});

//cookies medicos
$("#BuscarMed, #AgregarMed").click(function() {
  limpiarLocalStorage();
  setWithExpiry("validadorBuscarMed", "1", 100000);
}); 
//cambiado x jan por BUG rut orden
function filtrarxRutRest() {
  // $("#tipoDeAtencion").focus();  
  $('button[data-id="tipoDeAtencion"]').focus();
  $('#tipoDeAtencion').selectpicker('toggle');
  if (!$("#txtRutP").hasClass("is-invalid")) {
    $("#tableBodyDiagnosticoPaciente").empty();
    filtrarByRutRest($("#txtRutP").val());
    // $("#tipoDeAtencion").focus();   	
    $('button[data-id="tipoDeAtencion"]').focus();
    $('#tipoDeAtencion').selectpicker('toggle');
  } else {
    $("#txtRutP").focus();
  }
}


/*
$("#txtRutP").change(function () {
  //cambiado por cristian 12-12  para validar rut antes de buscar en la BD
  $("#tipoDeAtencion").focus();  	
        if (!$("#txtRutP").hasClass("is-invalid")) {		
         $("#tableBodyDiagnosticoPaciente").empty();		    
              filtrarByRutRest($("#txtRutP").val());             
        $("#tipoDeAtencion").focus();   			
         }else{
      $("#txtRutP").focus();
    }
});*/

$("#txtPasaporte").blur(function() {
  $(this).removeClass("is-invalid");
});
$("#txtPasaporte").change(function() {
  setTimeout(function() {
    $("#tableBodyDiagnosticoPaciente").empty();
    filtrarByPasaporteRest($("#txtPasaporte").val());
  }, 100);
});


$("#txtDni").blur(function() {
  $(this).removeClass("is-invalid");
});
$("#txtDni").change(function() {
  setTimeout(function() {
    $("#tableBodyDiagnosticoPaciente").empty();
    filtrarByDniRest($("#txtDni").val());
  }, 100);
});

$("#txtRutMedico").change(function() {
  setTimeout(function() {
    if (!$("#txtRutMedico").hasClass("is-invalid")) {
      filtrarByRutM($("#txtRutMedico").val());
    }
  }, 100);
});

$("#medicoComboBox").change(function() {
  filtrarByIdM($("#medicoComboBox").val());
});
/* Selectpicker   */


//  Medico
function cargarMedicos(){
    let cfgMedicos = new CfgMedicos();
    cfgMedicos.getMedicos();
    rellenarSelectpicker('medicoBox',cfgMedicos.misMedicos, 0)
}


//  Atencion
function cargarAtencioOrden() {
  let cfgTipoAtencion = new CfgTipoAtencion();
  cfgTipoAtencion.getTiposAtencion2();
  rellenarSelectpicker('tipoDeAtencion', cfgTipoAtencion.mistipos)
}

function cambiarFocusAtencionOrden() {
  if (RegistroOden.tipoDeAtencion == 1) {
    $('button[data-id="ProcedenciaComboBoxM"]').focus();
    $('#ProcedenciaComboBoxM').selectpicker('toggle');
  } else if (RegistroOden.tipoDeAtencion == 2 || RegistroOden.tipoDeAtencion == 3) {
    $('button[data-id="ServicioComboBoxM"]').focus();
    $('select[data-id="ServicioComboBoxM"]').selectpicker('toggle');
  } else if (RegistroOden.tipoDeAtencion == -1) {
    $('#tipoDeAtencion').selectpicker('val', '');
    $('button[data-id="tipoDeAtencion"]').focus();
    $('#tipoDeAtencion').selectpicker('toggle');
  }
}

function addAtencion(atencion) {
  let atenciones = parseInt(atencion);
  RegistroOden.tipoDeAtencion = !isNaN(atenciones) ? atenciones : -1;

}

// Procedencia
function cargarProcedenciaCombo() {
  let cfgProcedencia = new CfgProcedencia();
  cfgProcedencia.getProcedencias2();
  rellenarSelectpicker('ProcedenciaComboBoxM', cfgProcedencia.misProcedencias)
}

function addProcedencia(procedencia) {
  let procedencias = parseInt(procedencia);
  RegistroOden.ProcedenciaComboBoxM = !isNaN(procedencias) ? procedencias : -1;
}


$("#pasoResumen").click(function() {
  var nombrePac =
    $("#txtNombreP").val() + " " + $("#txtPrimerApellidoP").val() + " ";
  var nombreMed =
    $("#txtNombreM").val() + " " + $("#txtPrimerApellidoM").val();
  $("#txtNombrePacienteR").text(nombrePac);
  $("#txtNombreMedicoR").text(nombreMed);
});


// Servicio
function cargarServicioComboBoxM() {
  let cfgServicios = new CfgServicios();
  cfgServicios.getServicios2();
  rellenarSelectpicker('ServicioComboBoxM', cfgServicios.misServicios)
}




// $("#ServicioComboBoxM").on("blur", function (evt) {

// });


// Salas
var cfgSalas = new CfgSalas();
function loadDataSalas(servicio) {
  $("#CamaComboBoxM").attr("disabled", true);
  let servicios = parseInt(servicio);
  RegistroOden.ServicioComboBoxM = !isNaN(servicios) ? servicios : -1;
  RegistroOden.SalaComboBoxM = null;
  RegistroOden.cama = null;
  cfgSalas.getSalas(servicio, "#SalaComboBoxM");
  if (cfgSalas.misSalas.length > 0) {
    rellenarSelectpicker('SalaComboBoxM', cfgSalas.misSalas)
    $("#SalaComboBoxM").attr("disabled", false);
  } else {
    $('#SalaComboBoxM').empty();
    $('#CamaComboBoxM').empty();
    $("#SalaComboBoxM").attr("disabled", true);
    $("#CamaComboBoxM").attr("disabled", true);
    $('#SalaComboBoxM').selectpicker('refresh');
    $('#CamaComboBoxM').selectpicker('refresh');
  }
  $('.selectpicker').selectpicker();
}

// redirecciona segun tecla presionada
$('#SalaComboBoxM').change(function(e) {
  $('#CamaComboBoxM').selectpicker('val', '');
  if ($(this).val() == "") {
    RegistroOden.SalaComboBoxM = null;
    RegistroOden.cama = null;
    $("#CamaComboBoxM")
      .removeClass("is-invalid")
      .selectpicker("setStyle")
      .parent()
      .removeClass("is-invalid");
    $("#CamaComboBoxM").attr("disabled", true);
    $('#CamaComboBoxM').selectpicker('val', '');
  }

  if (cfgSalas.misSalas.length > 0 && RegistroOden.SalaComboBoxM == null && $(this).val() != "") {
    loadDataCamas($(this).val())
    // $("#SalaComboBoxM").focus();
  }
  $(this)
    .removeClass("is-invalid")
    .selectpicker("setStyle")
    .parent()
    .removeClass("is-invalid");

  $(this).selectpicker("refresh");
});

//   $('#ServicioComboBoxM').on('keydown',function(e){

//     function timer(){
//         if(e.which == 13 && RegistroOden.ServicioComboBoxM != -1){
//             $("#medicoBox").focus();
//         }else if(e.which == 9 && RegistroOden.ServicioComboBoxM != -1){
//             $("#SalaComboBoxM").focus();
//         }
//       }

//     setTimeout(timer,100);   

//   });

$("#SalaComboBoxM").on('blur', function() {

});


// Camas
var cfgCamas = new CfgCamas();
function loadDataCamas(sala) {
  $('#CamaComboBoxM').selectpicker('val', '');
  let salas = parseInt(sala);
  RegistroOden.SalaComboBoxM = !isNaN(salas) ? salas : null;
  RegistroOden.cama = null;
  cfgCamas.getCamas(sala, "#CamaComboBoxM");
  if (cfgCamas.misCamas.length > 0) {
    rellenarSelectpicker('CamaComboBoxM', cfgCamas.misCamas)
    $('.selectpicker').selectpicker();
    $("#CamaComboBoxM").focus();
  } else {
    $("#CamaComboBoxM").val("");
    $("#CamaComboBoxM").attr("disabled", true);
    $('.selectpicker').selectpicker();
  }
}

$('#CamaComboBoxM').change(function(e) {
  addCama($(this).val());

  if ($(this).val() == "") {
    RegistroOden.cama = null;
    $("#CamaComboBoxM").val("");
  }

  $(this)
    .removeClass("is-invalid")
    .selectpicker("setStyle")
    .parent()
    .removeClass("is-invalid");

  $(this).selectpicker("refresh");
});


$("#CamaComboBoxM").on('blur', function() {
});

function addCama(cama) {
  let camas = parseInt(cama);
  RegistroOden.cama = !isNaN(camas) ? camas : null;

  if (!isNaN(camas)) {
    // $("#medicoBox").focus();
  }
}




// Prioridad
function cargarPriorarAtencion(){
    let cfgPrioridad = new CfgPrioridad();
    cfgPrioridad.getPrioridades();
    rellenarSelectpicker('selectPrioridadAtencionPac',cfgPrioridad.misPrioridades, 0)
}

$('#selectPrioridadAtencionPac').on('change', function() {
  addPrior($(this).val());
});



$("#selectPrioridadAtencionPac").on('blur change', function() {
  if ($(this).val() == "") {
    RegistroOden.selectPrioridadAtencionPac = null;
  }
  if ($(this).val() != "" && RegistroOden.selectPrioridadAtencionPac == null) {
    // typePrio.clear();
  }
});


function addPrior(prio) {
  let prioridad = parseInt(prio);
  RegistroOden.selectPrioridadAtencionPac = !isNaN(prioridad) ? prioridad : null;
}



//   Convenior
function cargarConvenio(){
    let cfgConvenios = new CfgConvenios();
    cfgConvenios.getConvenios();
    rellenarSelectpicker('ConvencioComboBoxM',cfgConvenios.misConvenios, 0)
}

$('#ConvencioComboBoxM').on('change', function() {
  addConv($(this).val());
});



$("#ConvencioComboBoxM").on('blur change', function() {
  if ($(this).val() == "") {
    RegistroOden.ConvencioComboBoxM = '';
  }
  if ($(this).val() != "" && RegistroOden.ConvencioComboBoxM == '') {
    // typeConv.clear();
  }
});


function addConv(conv) {
  let convenio = parseInt(conv);
  RegistroOden.ConvencioComboBoxM = !isNaN(convenio) ? convenio : '';
  if (!isNaN(convenio)) {
    // $("#idSearcher").focus();
  }
}

//   Diagnosticos
function cargarDiagnosticosC() {
  let cfgDiagnosticos = new CfgDiagnosticos();
  cfgDiagnosticos.getDiagnosticos();
  rellenarSelectpicker('DiagnosticoComboBoxM', cfgDiagnosticos.misDiagnosticos)
}

$('#DiagnosticoComboBoxM').on('change', function() {
  addDiag($(this).val());
});


$("#DiagnosticoComboBoxM").on('blur change', function() {
  if ($(this).val() == "") {
    RegistroOden.DiagnosticoComboBoxM = 1;
  }

});



function addDiag(diag) {
  let diagnostico = parseInt(diag);
  RegistroOden.DiagnosticoComboBoxM = !isNaN(diagnostico) ? diagnostico : 1;
}

/* fin Typeahead   */
$("#CamaComboBoxM").on("blur change", function() {
  if ($(this).val() == "") {
    RegistroOden.cama = null;
  }
  $(this).removeClass("is-invalid");
});

$("#txtObservacion").keydown(function() {
  $("#txtObservacionR").val(this.value);
});


$("#selectTipoDocumento").change(function() {
  let tDoc = "DNI";
  if ($(this).val() == 1) {
    tDoc = "RUN";
    $("#divTxtRutFiltro").show();
    $("#divTxtPasaporteFiltro").hide();
    $('#divTxtDniFiltro').hide();
  } else if($(this).val() == 2){ 
    tDoc = "Pasaporte";
    $("#divTxtRutFiltro").hide();
    $("#divTxtPasaporteFiltro").show();
    $('#divTxtDniFiltro').hide();
  }else{
    $('#divTxtRutFiltro').hide();
    $('#divTxtPasaporteFiltro').hide();
    $('#divTxtDniFiltro').show();
  }
  $("#tipoDocLabel").text(tDoc);

  limpiarTabPaciente();
});

/********************  ojo con esto **************************** repensar en el ready *****************************/

var mensaje = getUrlParameter("v");
var rutPacienteEditado = getUrlParameter("rp");
var rutMedicoEditado = getUrlParameter("rm");
var modal = getUrlParameter("message");

if (mensaje !== null) {
  $("#medicoComboBox").val(mensaje);
}

if (modal !== undefined) {
  $("#numeroOrdenTitulo").text("Nº" + parseInt(modal));
  document.getElementById("BotonAbrirModalExitoso").click();
}

if (rutPacienteEditado !== undefined) {
  $("#txtRutP").val(rutPacienteEditado);
  filtrarByRut(rutPacienteEditado);
}
if (rutMedicoEditado !== undefined) {
  $("#txtRutMedico").val(rutMedicoEditado);
  filtrarByRutM(rutMedicoEditado);
}

function habilitaPanelBasicoPaciente() {
  $("#panelContenidoBasicoPaciente").show();
  $("#panelContenidoExtraPaciente").hide();
}

function habilitaPanelExtraPaciente() {
  $("#panelContenidoBasicoPaciente").hide();
  $("#panelContenidoExtraPaciente").show();
}

function habilitaPanelBasicoOrden() {
  $("#panelContenidoBasicoOrden").show();
  $("#panelContenidoExtraOrden").hide();
}

function habilitaPanelExtraOrden() {
  $("#panelContenidoBasicoOrden").hide();
  $("#panelContenidoExtraOrden").show();
}

$("#btnModalCancel").click(function(e) {
  $("#idOrderConfirmationModal").modal("hide");
});

$("#btnCreatedModalOk").click(function(e) {
  $("#idOrderCreatedModal").modal("hide");
  // location.reload();
});

let ordenDoc = null;

$("#btnModalSave").click(function(e) {
  let datos = $("#jsonFormData").val();
  datos = JSON.parse(datos);
  datos.dfObservacion = $("#txtObservacionRC").val();
  datos = JSON.stringify(datos);
  console.log(datos);
  $.ajax({
    type: "post",
    url: gCte.getctx + "/api/ordenes/insert",
    data: datos,
    async: false,
    contentType: "application/json",
    success: function(data) {
      try {
        if (data.status !== 200) {
          handlerMessageError(data.message);
          return;
        }
        let orden = data.dato;
        ordenDoc = orden;
        //        $("#msjeRegistro").html(data.message);
        $("#numeroOrdenTitulo").text("Nº" + orden.dfNorden);
        $("#idOrderCreatedModal").modal("show");
        limpiarTodo(vs, vt);
      } catch (e) {
        handlerMessageError("No se pudo agregar la orden.");
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError("No se pudo agregar la orden. ");
    },
  });

  let documento = $('input[name=ordenMedicaFile]')[0].files[0];

  if (typeof documento !== 'undefined' && documento !== null) {
    formData = new FormData();
    formData.append("documento", documento);
    $.ajax({
      url: getctx + "/api/documento/save/" + ordenDoc.dfNorden + "/1?modulo=1",
      data: formData,
      dataType: 'json',
      type: 'POST',
      async: false,
      contentType: false, // Important
      processData: false, // Important
      success: function() {
        //handlerMessageOk("Documento guardado");
        console.log("Documento guardado");
      },
      error: function() {
        handlerMessageError("Error al subir documento");
      }
    });
  }

  $("#idOrderConfirmationModal").modal("hide");
});



$("#idOrdenYaRegistrada").on("click", function(e) {
  if ($(e.target).hasClass("btn-si")) {
    $("#idOrdenYaRegistrada").modal("toggle");
  } else {
    //  limpiarDatosBasicosPaciente();
    // limpiarDatosExtrasPaciente();

    //consultar a don eric
  }
});

$("#usuarioConOrden").on("click", function(e) {
  if ($(e.target).hasClass("btn-si-orden")) {
    $("#usuarioConOrden").modal("toggle");
    $('button[data-id="tipoDeAtencion"]').focus();
    $('#tipoDeAtencion').selectpicker('toggle');
  } else {
    limpiarDatosBasicosPaciente();
    limpiarDatosExtrasPaciente();

    //consultar a don eric
  }
});


$("#btnOrdenYaRegistradaNo").on("click", function(e) {
  let ordenTable = $('#idDataTarget').DataTable();
  let elemento = $(".ultimo-seleccionado").first();
  console.log(elemento[0]);
  //ordenTable.row( elemento[0]).remove().draw();
  ultimoEliminado();
});

$("#idOrdenYaRegistrada").on("hide.bs.modal", function(event) {
  // $("#idSearcher").focus();
});


$("#usuarioConOrden").on("hide.bs.modal", function(event) {
  // $("#tipoDeAtencion").focus();
});


class filesInput {
  jqid;
  registroDocs;
  btnUpload;
  fInput;
  constructor(jqid) {
    this.jqid = jqid;
  }

  init(registroDocs, btnUpload, deleteUrl, textoDrop = 'Arrastre y suelte aquí los archivos &hellip;') {
    $(this.jqid).fileinput('destroy');
    // $(this.jqid).val(null);
    let initConfigPrevia = this.initConfiguracionPrevia(registroDocs);
    let initPrev = this.initPrevia(registroDocs);
    let btnShowUpload = this.initBtnShowUpload(btnUpload);
    let btnShowbrose = this.initBtnShowbrowse(btnUpload);
    this.habilitarBoton(btnShowbrose);
    this.fInput = $(this.jqid).fileinput({
      language: "es",
      browseClass: "btn btn-blue-primary",
      browseLabel: "Seleccionar",
      uploadClass: "btn btn-success",
      uploadLabel: "Guardar",
      uploadIcon: "<i class=\"bi-upload\"></i>",
      removeLabel: 'Eliminar',
      dropZoneTitle: textoDrop,
      /*removeClass: "btn btn-danger",
      removeLabel: "Eliminar",
      removeIcon: "<i class=\"bi-trash\"></i>",*/
      showUpload: btnShowUpload,
      showBrowse: btnShowbrose,
      overwriteInitial: true,
      maxFileCount: 1,
      previewClass: 'p-0',
      showRemove: true,
      showClose: false,
      allowedFileExtensions: ["jpg", "jpeg", "png", "pdf"],
      initialPreviewAsData: true,
      initialPreview: [initPrev],
      initialPreviewFileType: 'image',
      initialPreviewConfig: [initConfigPrevia],
      deleteUrl: deleteUrl, //modificar para habilitar o no
      fileActionSettings: {
        showRemove: false,
        showDrag: false,
        showUpload: true,
        indicatorNew: "",
        indicatorSuccess: "",
        indicatorError: "",
      },
      frameClass: 'w-100 p-0',
    });
  }

  refresh(registroDocs, btnUpload, habilitar, deleteUrl, textoDrop) {
    $(this.jqid).fileinput('destroy');
    $(this.jqid).val(null);

    //default config
    registroDocs.docOrdenMedica = null;
    registroDocs.tipoDocOrdenMedica = null;

    btnUpload.docOrdenMedica = habilitar;
    btnUpload.browseDocOrdenMedica = habilitar;
    this.init(registroDocs, btnUpload, deleteUrl);
  }

  initConfiguracionPrevia(registroDocs) {
    let obj = {}
    obj = {
      type: registroDocs.tipoDocOrdenMedica,
      downloadUrl: registroDocs.docOrdenMedica,
      caption: `Orden médica`
    };

    return obj;
  }

  initPrevia(registroDocs) {
    let variable = null;
    variable = registroDocs.docOrdenMedica;
    return variable;
  }

  initBtnShowUpload(btnUpload) {
    let variable = null;
    variable = btnUpload.docOrdenMedica;
    return variable;
  }

  initBtnShowbrowse(btnUpload) {
    let variable = null;
    variable = btnUpload.browseDocOrdenMedica;
    return variable;
  }

  habilitarBoton(btnShowbrose) {
    if (btnShowbrose) {
      $("#ordenMedicaFile").removeClass("file-no-browse");
    }
  }
}

class RegistroDocumentosExamenes {

  nro_Orden;
  idExamen;
  paciente;
  docOrdenMedica;
  tipoDocOrdenMedica;

  constructor() {
    this.nro_Orden = -1;
    this.idExamen = -1;
    this.paciente = null;
    this.docOrdenMedica = null;
    this.tipoDocOrdenMedica = null;

  }
}
var gRegistroDocumentosExamenes = new RegistroDocumentosExamenes();


const btnUpload = {
  docOrdenMedica: true,
  browseDocOrdenMedica: true,
};


//llama al modulo de fileinputBiosbo.js
const ordenMedicaFile = new filesInput("#ordenMedicaFile");


//ingresar al modal bs open
$('#adjuntarDocumentosModal').on('show.bs.modal', function(e) {
  gRegistroDocumentosExamenes.docOrdenMedica = null;
  gRegistroDocumentosExamenes.nro_Orden = null;
  ordenMedicaFile.init(gRegistroDocumentosExamenes, btnUpload, "");
  $("#adjuntarDocumentosModal").find("[type='submit']").remove();
});
//agregado por jan imprimir  orden

function obtenerExamenesSeleccionados2(examenesSeleccionados) {

  function tuplaExamen() {
    this.nOrden = -1;
    this.idExamen = -1;
    this.ceCodigoExamen = "";
    this.ceAbreviado = "";
    this.ceeDescripcionEstadoExamen = "";
    this.sdfeFechaRetiraExamen = "";
  }
  //const examenesSeleccionados = pTableInfoExamenesOrden.rows({ selected: true }).data();

  //let tableExamen = $('#idDataTarget').DataTable();
  //let examenesSeleccionados  = tableExamen.rows().data();
  console.log(examenesSeleccionados);

  if (examenesSeleccionados === undefined || examenesSeleccionados === null) {
    handlerMessageError('Revisar selección de mensajes.');
    return false;
  }
  console.log()
  const n = examenesSeleccionados.length;
  if (n === 0) {
    handlerMessageError('Verifique si ha seleccionado mensajes.');
    return false;
  }
  let idSeleccionados = new Array();

  for (let i = 0; i < n; i++) {
    let tupla = new tuplaExamen();
    // tupla.nOrden = examenesSeleccionados[i].dfNorden;
    tupla.idExamen = examenesSeleccionados[i];
    //tupla.ceAbreviado = examenesSeleccionados[i].ce_ABREVIADO;
    // tupla.ceCodigoExamen = examenesSeleccionados[i].ce_CODIGOEXAMEN;
    // tupla.ceeDescripcionEstadoExamen = examenesSeleccionados[i].cee_DESCRIPCIONESTADOEXAMEN;
    //  tupla.sdfeFechaRetiraExamen = examenesSeleccionados[i].sdfe_FECHARETIRAEXAMEN;
    idSeleccionados.push(tupla);
  }

  return idSeleccionados;
}

function preverPdf(pTableInfoExamenesOrden, nOrden) {
  let tuplasSeleccionadas = obtenerExamenesSeleccionados2(pTableInfoExamenesOrden);
  let n = tuplasSeleccionadas.length;
  let lstIdExamenes = "";
  for (let i = 0; i < n; i++) {
    lstIdExamenes += tuplasSeleccionadas[i].idExamen + ' ';
  }

  let url = getctx + '/CRViewer?pNroOrden=' + nOrden + '&pIdExamen=' + lstIdExamenes;
  console.log(url);
  window.open(url, "pdfWin");

}
$("#imprimirComprobante2").click(function(e) {
  let datosParse = $("#jsonFormData").val();
  datosParse = JSON.parse(datosParse);

  datosParse.dfObservacion = $("#txtObservacionRC").val();
  datos = JSON.stringify(datosParse);

  $.ajax({
    type: "post",
    url: gCte.getctx + "/api/ordenes/insert",
    data: datos,
    async: false,
    contentType: "application/json",
    success: function(data) {
      try {
        if (data.status !== 200) {
          handlerMessageError(data.message);
          return;
        }
        console.log(datosParse)
        let orden = data.dato;
        ordenDoc = orden;
        //        $("#msjeRegistro").html(data.message);
        $("#numeroOrdenTitulo").text("Nº" + orden.dfNorden);
        $("#idOrderCreatedModal").modal("show");
        preverPdf(datosParse.lstExamenes, orden.dfNorden);
        limpiarTodo(vs, vt);
      } catch (e) {
        handlerMessageError("No se pudo agregar la orden.");
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError("No se pudo agregar la orden. ");
    },
  });

  let documento = $('input[name=ordenMedicaFile]')[0].files[0];

  if (typeof documento !== 'undefined' && documento !== null) {
    formData = new FormData();
    formData.append("documento", documento);
    $.ajax({
      url: getctx + "/api/documento/save/" + ordenDoc.dfNorden + "/1",
      data: formData,
      dataType: 'json',
      type: 'POST',
      async: false,
      contentType: false, // Important
      processData: false, // Important
      success: function() {
        //handlerMessageOk("Documento guardado");
        console.log("Documento guardado");
      },
      error: function() {
        handlerMessageError("Error al subir documento");
      }
    });
  }


  let url = gCte.getctx + '/api/orden/impresion/comprobante/orden/' + ordenDoc.dfNorden;
  $.ajax({
    type: "get",
    url: url,
    success: function(data) {
      console.log("");
    },
    error: console.log("")
  });


  $("#idOrderConfirmationModal").modal("hide");
});
/*
$("#imprimirComprobante2").click(function(){
    let destinatarios = obtenerExamenesSeleccionados2("#idDataTarget");
    if (destinatarios === false){
        return;
    }
   //preverPdf(tableInfoExamenesOrden);
   });

*/
function ultimoEliminado() {
  let items = vt.getUltimoSelecionado();
  console.log(items);
  vt.removeItems(items);
  vs.addItems(items);
}

