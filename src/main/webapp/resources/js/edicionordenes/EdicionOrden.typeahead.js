$(document).ready(function () {
  cargarAtencioOrden();
  cargarProcedenciaCombo();
  cargarServicioComboBoxM();
  cargarMedicos();
  cargarPriorarAtencion();
  cargarConvenio();
  cargarDiagnosticosC();


  

  $('#tipoDeAtencion').on('changed.bs.select', function() {
    addAtencion($(this).val());
  $(this).selectpicker('toggle');
  if($(this).val() == ""){
    RegistroOden.tipoDeAtencion = -1;
  }
  if(RegistroOden.tipoDeAtencion == -1 && $(this).val() != ""){
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

  if($(this).val() == ""){
      RegistroOden.ProcedenciaComboBoxM = -1;
  }
  if(RegistroOden.ProcedenciaComboBoxM == -1  && $(this).val() != ""){
      $('button[data-id="ProcedenciaComboBoxM"]').focus();
      $('#ProcedenciaComboBoxM').selectpicker('val', '');
      $('#ProcedenciaComboBoxM').selectpicker('toggle');
      var event = new KeyboardEvent('keydown', { keyCode: 13 });
      document.querySelector('button[data-id="ProcedenciaComboBoxM"]').dispatchEvent(event);
  }else if(!isNaN(RegistroOden.ProcedenciaComboBoxM) && $(this).val() != ""){
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
if($(this).val() == ""){
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


if(RegistroOden.ServicioComboBoxM == -1  && $(this).val() != ""){
  $('#ServicioComboBoxM').selectpicker('val', '');
  $('button[data-id="ServicioComboBoxM"]').focus();
  $('#ServicioComboBoxM').selectpicker('toggle');
 }

 if(!isNaN(RegistroOden.ServicioComboBoxM) && $(this).val() != ""){
  $('button[data-id="medicoBox"]').focus();
  $('#medicoBox').selectpicker('toggle');
 }

 $(this).selectpicker("refresh");
});


  
  $('.selectpicker').selectpicker('refresh');



})


  /* Typeahead   */


//  Medico
function cargarMedicos(){
  let cfgMedicos = new CfgMedicos();
  cfgMedicos.getMedicos();
  rellenarSelectpicker('medicoBox',cfgMedicos.misMedicos)
}



$('#medicoBox').on('change', function() {
  filtrarByIdM($(this).val());
});


$("#medicoBox").blur(function () {
  if($(this).val() == ""){
   RegistroOden.medicoComboBox = null;
   $("#txtRutMedico").val("");
  }
  if( RegistroOden.medicoComboBox != null){
      // $("#idSearcher").focus();
  }
});

//  Atencion
function cargarAtencioOrden(){
  let cfgTipoAtencion = new CfgTipoAtencion();
  cfgTipoAtencion.getTiposAtencion2();
  rellenarSelectpicker('tipoDeAtencion',cfgTipoAtencion.mistipos)
}

function cambiarFocusAtencionOrden(){
  if(RegistroOden.tipoDeAtencion == 1){
      $('button[data-id="ProcedenciaComboBoxM"]').focus();
      $('#ProcedenciaComboBoxM').selectpicker('toggle');
    }else if(RegistroOden.tipoDeAtencion == 2 || RegistroOden.tipoDeAtencion == 3){
      $('button[data-id="ServicioComboBoxM"]').focus();
      $('select[data-id="ServicioComboBoxM"]').selectpicker('toggle');
    }else if(RegistroOden.tipoDeAtencion == -1 ){
      $('#tipoDeAtencion').selectpicker('val', '');
      $('button[data-id="tipoDeAtencion"]').focus();
      $('#tipoDeAtencion').selectpicker('toggle');
    }
}


function addAtencion(atencion){
   let atenciones = parseInt(atencion);
   RegistroOden.tipoDeAtencion = !isNaN(atenciones) ? atenciones : -1;
}


function reglaTipoAtencion() {
    let idTipoAtencion = $(g_jqIdTipoAtencion).val();
    //  reglaExclusionLocalizacion(idTipoAtencion);
  }


// Procedencia
function cargarProcedenciaCombo(){
  let cfgProcedencia = new CfgProcedencia();
  cfgProcedencia.getProcedencias2();
  rellenarSelectpicker('ProcedenciaComboBoxM',cfgProcedencia.misProcedencias)
}


function addProcedencia(procedencia){
   let procedencias = parseInt(procedencia);
   RegistroOden.ProcedenciaComboBoxM = !isNaN(procedencias) ? procedencias : -1;
}


$("#pasoResumen").click(function () {
   var nombrePac =
       $("#txtNombreP").val() + " " + $("#txtPrimerApellidoP").val() + " ";
   var nombreMed =
       $("#txtNombreM").val() + " " + $("#txtPrimerApellidoM").val();
   $("#txtNombrePacienteR").text(nombrePac);
   $("#txtNombreMedicoR").text(nombreMed);
});


// Servicio
function cargarServicioComboBoxM(){
  let cfgServicios = new CfgServicios();
  cfgServicios.getServicios2();
  rellenarSelectpicker('ServicioComboBoxM',cfgServicios.misServicios)
}



// Salas
var cfgSalas = new CfgSalas();
function loadDataSalas(servicio){
  $("#CamaComboBoxM").attr("disabled", true);
  let servicios = parseInt(servicio);
  RegistroOden.ServicioComboBoxM = !isNaN(servicios) ? servicios : -1;
  RegistroOden.SalaComboBoxM = null;
  RegistroOden.cama = null;
  cfgSalas.getSalas(servicio, "#SalaComboBoxM");
  if (cfgSalas.misSalas.length > 0) {
      rellenarSelectpicker('SalaComboBoxM',cfgSalas.misSalas)
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
 $('#SalaComboBoxM').change(function(e){
  $('#CamaComboBoxM').selectpicker('val', '');
  if($(this).val() == ""){
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

//  $('#ServicioComboBoxM').on('keydown',function(e){

//    function timer(){
//        if(e.which == 13 && RegistroOden.ServicioComboBoxM != -1){
//            $("#medicoBox").focus();
//        }else if(e.which == 9 && RegistroOden.ServicioComboBoxM != -1){
//            $("#SalaComboBoxM").focus();
//        }
//      }
  
//    setTimeout(timer,100);   

//  });

 $("#SalaComboBoxM").on('blur',function () { 

});


// Camas
var cfgCamas = new CfgCamas();
function loadDataCamas(sala){
  $('#CamaComboBoxM').selectpicker('val', '');
  let salas = parseInt(sala);
RegistroOden.SalaComboBoxM = !isNaN(salas) ? salas : null;
RegistroOden.cama = null;
  cfgCamas.getCamas(sala, "#CamaComboBoxM");
  if (cfgCamas.misCamas.length > 0) {
      rellenarSelectpicker('CamaComboBoxM',cfgCamas.misCamas)
      $('.selectpicker').selectpicker();
      $("#CamaComboBoxM").focus();
  } else {
      $("#CamaComboBoxM").val("");
      $("#CamaComboBoxM").attr("disabled", true);
      $('.selectpicker').selectpicker();
  }
}

$('#CamaComboBoxM').change(function(e){
  addCama($(this).val());

  if($(this).val() == ""){
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

$("#CamaComboBoxM").on('blur',function () { 

});

function addCama(cama){
   let camas = parseInt(cama);
 RegistroOden.cama = !isNaN(camas) ? camas : null;

 if (!isNaN(camas)) {
  //  $("#medicoBox").focus();
}
}




// Prioridad
function cargarPriorarAtencion(){
  let cfgPrioridad = new CfgPrioridad();
  cfgPrioridad.getPrioridades();
  rellenarSelectpicker('selectPrioridadAtencionPac',cfgPrioridad.misPrioridades)
}

$('#selectPrioridadAtencionPac').on('change', function() {
  addPrior($(this).val());
});


$("#selectPrioridadAtencionPac").on('blur change',function () {
  if($(this).val() == ""){
   RegistroOden.selectPrioridadAtencionPac = null;
  }
  if($(this).val() != "" && RegistroOden.selectPrioridadAtencionPac == null){
  //  typePrio.clear();
  $(this).selectpicker('val', '');
  }
});


function addPrior(prio){
   let prioridad = parseInt(prio);
   RegistroOden.selectPrioridadAtencionPac = !isNaN(prioridad) ? prioridad : null;
 }



//   Convenior
function cargarConvenio(){
  let cfgConvenios = new CfgConvenios();
  cfgConvenios.getConvenios();
  rellenarSelectpicker('ConvencioComboBoxM',cfgConvenios.misConvenios)
}

$('#ConvencioComboBoxM').on('change', function() {
  addConv($(this).val());
  if($(this).val() == ""){
    RegistroOden.ConvencioComboBoxM = 'N';
   }
   if($(this).val() != "" &&  RegistroOden.ConvencioComboBoxM == 'N'){
    // typeConv.clear();
    $(this).selectpicker('val', '');
   }
});

$("#ConvencioComboBoxM").on('blur change',function () {

});


function addConv(conv){
   let convenio = parseInt(conv);
   RegistroOden.ConvencioComboBoxM = !isNaN(convenio) ? convenio : 'N';
   if(!isNaN(convenio)){
      //  $("#idSearcher").focus();
   }
 }

//   Diagnosticos
function cargarDiagnosticosC(){
  let cfgDiagnosticos = new CfgDiagnosticos();
  cfgDiagnosticos.getDiagnosticos();
  rellenarSelectpicker('DiagnosticoComboBoxM',cfgDiagnosticos.misDiagnosticos)
}

$('#DiagnosticoComboBoxM').on('change', function() {
  addDiag($(this).val());
  if($(this).val() == ""){
    RegistroOden.DiagnosticoComboBoxM = 1;
   }
});


$("#DiagnosticoComboBoxM").on('blur change',function () {

});


function addDiag(diag){
   let diagnostico = parseInt(diag);
   RegistroOden.DiagnosticoComboBoxM = !isNaN(diagnostico) ? diagnostico : 1;
 }

/* fin Typeahead   */

function filtrarByIdM(id,focus) {
  $("#tbodyFiltro").empty();
  // console.log(id);
  if (id === '0' ) {
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
        $("#txtRutMedico").text(cambiarDatoRut(Medico.Rut));
        $("#txtNombreM").val(Medico.Nombres);
        let apellidos = Medico.PrimerApellido + " " + (Medico.SegundoApellido !== undefined ? Medico.SegundoApellido : "");
        $("#txtPrimerApellidoM").val(apellidos);
        $("#medicoBox").val(Medico.Nombres + " " + Medico.PrimerApellido);

        typeof focus !== 'undefined' ? $("#"+focus).focus() : '';
      }
    },
    error: function(msg) {
      console.log(msg);
    }
  });
}




