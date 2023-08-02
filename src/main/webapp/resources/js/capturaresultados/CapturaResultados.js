var boBuscador = null;
var listaTiposReceptores = null;
var listaViasNotificaciones = null;
var rowSeleccionado = 0;
var rowConCalse = 0;
var pLstExamenesSeleccionados = [];
var paramResultadosExamenesSeleccionados = { "paciente": { "sexo": "", "fnac": "" }, "examenes": pLstExamenesSeleccionados };
var globalselects = null;
var estadoTestYResultadosColorMap = new Array();
estadoTestYResultadosColorMap['FIRMADO'] = 'firmado';
estadoTestYResultadosColorMap['PENDIENTE'] = 'pendiente';
estadoTestYResultadosColorMap['DIGITADO'] = 'digitado';
estadoTestYResultadosColorMap['TRANSMITIDO'] = 'transmitido';
estadoTestYResultadosColorMap['BLOQUEADO'] = 'bloqueado';
var listaCodTestContador;
var listaTestOrden;
// estados botones
$("#btnRegDocOrden").prop('disabled', true);
$("#btnVerOrden").prop('disabled', true);
$("#btnRegDocOrden").prop('disabled', true);





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

  normalTooltip(elemento){
    $(elemento)
      .tooltip({
        template: '<div class="tooltip tooltip-normal" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',
      });
  }
  
}

const ui = new UI();

boBuscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
  "#bo_div_nOrdenFin",
  "#bo_div_fIni",
  "#bo_div_fFin",
  "#bo_div_nombre",
  "#bo_div_apellido",
  "#bo_div_apellforo",
  "#bo_div_tipoDocumento",
  "#bo_div_nroDocumento",
  "#bo_div_tipoAtencion",
  "#bo_div_localizacion",
  "#bo_div_procedencia",
  "#bo_div_servicio",
  "#bo_div_seccion", "#bo_div_examen"]);


$(document).ready(function() {
//obtener Permisos De Usuario
//verificar si tiene permiso  de firmar
//habilitar o deshabilitar todos los elementos visuales asoc a ese permiso
//btnFirmaResultados
//autorizarExamenes
//
let loader = iconLoading('spinnerContainer1');
let loader2 = iconLoading('spinnerContainer2');
let loader3 = iconLoading('spinnerContainer3');
$('#accordionPanelSeleccionOrden').append(loader);
$('.containerTableExamen').parent().append(loader2);
$('.containerTableTest').parent().append(loader3);

$( "#btnBuscarOrden" ).on( "click", function() {
	$('.bo_input').val('');
	$(".bo_select").val('0');
	
	$('.bo_container  input, .bo_container  select').removeAttr('disabled');
	$('.bo_container  input, .bo_container  select').prop('readonly', false);
	
	$('.bo_select').selectpicker('refresh');
	boBuscador.boFnFillSelect();
});

$( "#bo_btnCerrarModal" ).on( "click", function() {
	$('.bo_input').val('');
	$(".bo_select").val('0');
	
	$('.bo_container  input, .bo_container  select').removeAttr('disabled');
	$('.bo_container  input, .bo_container  select').prop('readonly', false);
	
	$('.bo_select').selectpicker('refresh');
});
  $(".selectpicker").selectpicker({
    noneSelectedText: 'Seleccionar'
  });
  $(".ocultar").hide();
  initTableInfoExamenesOrden("#examenesOrdenesDatatable");
  initTableResultadosExamenesOrden("#resultadosExamenesOrdenesDatatable");
  $("#btnBuscarFiltro").click(loadResultadosExamenesSeleccionadosOrden);
  $("#btnBuscarOrden").click(buscarOrden);
  $("#btnVerOrden").click(verOrden);  
  $("#btnRefreshBuscarOrden").click(function f() { reloadOrdenes(tableInfoOrdenes) ;});
  $('.datepicker').datepicker({
    language: 'es',
    endDate: "+Infinity"
  });
  $(".testSendMailButton").click(function(){
    notifyResultTest(this);
  });
  $("#btnAutorizaResultados").click(autorizarExamenes);
  $("#btnBloqueaTest").click(bloquearTest);
  $("#btnRetiraFirma").click(retirarFirma);
  $("#btnFirmaResultados").click(firmarResultados);
  $("#bo_btnBuscarOrden").click(buscarOrdenes);
  $("#btnPrevOrder").click(navegarPrev);
  $("#btnFirstOrder").click(navegarFirst);
  $("#btnNextOrder").click(navegarNext);
  $("#btnLastOrder").click(navegarLast);
  $("#examOptionalTestsButton").click(guardarTestOpcionales);
  $("#btnContadorCelulas").click(validarContadorExamen);
  $("#btnConfiguradorContador").click(showConfiguradorContador);
  $("#btnSalirContador").click(salirContador);
  $("#btnRegDocOrden").click(registrarDocOrdenes);
  $(document).keypress(operaEvento);
  listaTiposReceptores = new LdvTiposReceptores("#selectNotificacionTipoReceptor");
  listaTiposReceptores.loadTipos();
  listaViasNotificaciones = new LdvViasNotificaciones("#idTestCanvasNotificationsComunicationChannel");
  listaViasNotificaciones.loadVias();
  $(".switch-gm input").change(cambiarOrdenGMSwitch);


});

function navegarFirst() {
  navegar(gCte.first);
}
function navegarPrev() {
  navegar(gCte.prev);
}
function navegarNext() {
  navegar(gCte.next);
}
function navegarLast() {
  navegar(gCte.last);
}

function navegar(dir) {
  let rows = tableInfoOrdenes.rows({ search: 'applied' }).data();
  let n = rows.length;
  let j = 0;
  let nOrden = parseInt($("#txtNroOrden").text());

  if (isNaN(nOrden)) {
    alert('Nro de orden inválido');
    return;
  }

  if (dir === gCte.first) {
    j = 0;
  }
  else if (dir === gCte.last) {
    j = n - 1;
  }
  else {
    for (let i = 0; i < n; i++) {
      if (rows[i].df_NORDEN === nOrden) {
        j = i;
        break;
      }
    }
    if (dir === gCte.prev) {
      j = j - 1;
    }
    else {
      j = j + 1;
    }
  }

  if (j >= 0 && j < n) {
    selectRowInfoOrdenes(rows[j]);
  }
}

function buscarOrdenes() {

  if(validarBusquedaOrdenes()){
    boBuscador.boSetFechas();
    tableInfoOrdenes.ajax.reload();
    $("#buscarOrdenesModal").modal('hide');

  }
}

function validarBusquedaOrdenes(){
  let re = true;

  //orden y fecha
  if ($('#bo_nOrdenIni').val() === '' && $('#bo_nOrdenFin').val() === '' && $('#bo_fIni').val() === '' && $('#bo_fFin').val() === '') {
    re = false;
    $("#bo_nOrdenIni").addClass("is-invalid");
    $("#bo_nOrdenFin").addClass("is-invalid");
    $("#bo_fIni").addClass("is-invalid");
    $("#bo_fFin").addClass("is-invalid");
  }

  //Datos paciente
  if($("#bo_nombre").val() !== "" && $("#bo_nombre").val().length > 0 && $("#bo_nombre").val().length < 2){
    $("#bo_nombre").addClass("is-invalid");
    ui.imprimirTooltip("#bo_nombre", "Mínimo dos caracteres");
    re = false;
  }else{
    $("#bo_nombre").removeClass("is-invalid");
    ui.limpiarTooltip("#bo_nombre");
  }
  
  if($("#bo_apellido").val() !== "" && $("#bo_apellido").val().length > 0 && $("#bo_apellido").val().length < 2){
    $("#bo_apellido").addClass("is-invalid");
    ui.imprimirTooltip("#bo_apellido", "Mínimo dos caracteres");
    re = false;
  }else{
    $("#bo_apellido").removeClass("is-invalid");
    ui.limpiarTooltip("#bo_apellido");
  }

  

  //Datos laboratorios

  //checkbox
  if ($('#estadoPendiente2').prop('checked') || $('#estadoFirmar').prop('checked')) {
    if($('#bo_nOrdenIni').val() === '' && $('#bo_nOrdenFin').val() === '' && $('#bo_fIni').val() === '' && $('#bo_fFin').val() === ''){
      re = false;
    }
  }
  
  if(re == false){
    handlerMessageError('Se debe completar al menos un campo de búsqueda');

  }

  return re;
}




function resultadoNumerico(elemento) {
  $(document).on("click", elemento, function() {
    let $this = $(this);
    let $input = $('<input>', {
      value: $this.text(),
      class: 'form-control',
      type: 'text',
      keypress: function() {
        validarNumero($this);
      },
      blur: function() {
        validarResultadoxValorReferencia(this, this.value);
        $this.text(this.value);
      },
      keyup: function(e) {
        if (e.which === 13)
          $input.blur();
      }
    }).appendTo($this.empty()).focus();
  });
};

function validarResultadoxValorReferencia(a, valorResultado) {
  var $row = $(a).closest("tr"); // Find the row
  var valoresreferencias = $row.find(".valoresReferencias").text(); // Find the text
  var valorCriticoBajo = $row.find(".valorCriticoBajo").text();
  var valorCriticoAlto = $row.find(".valorCriticoAlto").text();
  var arrayValores = valoresreferencias.split("-");
  var valorbajo = arrayValores[0].trim();
  var valoralto = arrayValores[1].trim();
  console.log(parseInt(valorResultado) <= parseInt(valorbajo));

  switch (true) {
    case (parseInt(valorResultado) >= parseInt(valorbajo) && parseInt(valorResultado) <= parseInt(valoralto)):
      $row.find(".alerta").empty();
      break;
    case (parseInt(valorResultado) < parseInt(valorCriticoBajo) + 1):
      $row.find(".alerta").empty();
      $row.find(".alerta").append("<i class='fas fa-arrow-down text-danger'></i><i class='fas fa-exclamation-circle text-danger'></i>");
      break;
    case (parseInt(valorResultado) <= parseInt(valorbajo)):
      $row.find(".alerta").empty();
      $row.find(".alerta").append("<i class='fas fa-arrow-down text-danger'></i>");
      break;
    case (parseInt(valorResultado) >= parseInt(valoralto) && parseInt(valorResultado) < parseInt(valorCriticoAlto)):
      $row.find(".alerta").empty();
      $row.find(".alerta").append("<i class='fas fa-arrow-up text-danger'></i>");
      break;
    case (parseInt(valorResultado) >= parseInt(valorCriticoAlto)):
      $row.find(".alerta").empty();
      $row.find(".alerta").append("<i class='fas fa-arrow-up text-danger'></i><i class='fas fa-exclamation-circle text-danger'></>");
      break;
  }
}
function resultado() {
  $(".resultado").each(function() {
    tipoResultado(this);
  });
}

function agregarGlosa(idtest) {
  $.ajax({
    type: "post",
    data: "getGlosas=1&idTest=" + idtest,
    datatype: "json",
    success: function(mensaje) {
      console.log(mensaje);
      let dato = JSON.parse(mensaje);
      dato.Glosa.forEach(function(glosa) {
        $("." + idtest + "Select").append("<option>" + glosa.Descripcion + "</option>");
      });
    },
    error: function(msg) {
      console.log(msg);
    }
  });
}

function tipoResultado(a) {
  var $row = $(a).closest("tr");
  let tResultado = $row.find(".tipoResultado").text();
  let result = $row.find("." + idTest + "Resultado").text();
  var idTest = $row.find(".idTest").text();
  if (result == "") {
    switch (tResultado) {
      case "2":
        $row.find(".resultado").append("<select class='" + idTest + "Select' >" + agregarGlosa(idTest) + "</select>");
        break;
      case "3":
        console.log("numerico");
        resultadoNumerico("." + idTest + "Resultado");
        break;
      case "5":
        console.log("Texto");
        break;
      case "6":
        console.log("formula");
        $row.find(".resultado").text("formula");
        break;
      case "9":
        console.log("imagen");
        $row.find(".resultado").text("imagen");
        break;
    }
  } else {
    console.log("tiene resultado");
  }
}

function validarNumero(input) {
  input.keypress(function(e) {
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum == 8))
      return true;
    return /\d/.test(String.fromCharCode(keynum));
  });
}


function doAction(action, indexes) {

  switch (action) {

    case 'getOrderDetail':

      getOrderDetail(indexes);
      break;

    case 'getOrderEventsDetail':

      getOrderEventsDetail(indexes);
      break;

    case 'getEventExamenDetail':

      getEventExamenDetail(indexes);
      break;

    case 'getNotasExamenDetail':

      getNotasExamenDetail(indexes);
      break;

    case 'getObsExamenDetail':

      getObsExamenDetail(indexes);
      break;

    case 'getOpcTestExamenDetail':

      getOpcTestExamenDetail(indexes);
      break;

    case 'getAntecedentePacientesModal':

      getAntecedentePacientesModal(indexes);
      break;

    case 'getEventUrgenciaDetail':

      setUrgenciaDetail(indexes);
      break;

    default:



      break;

  }

}

function buscarOrden() {
	
  $("#accordionPanelSeleccionOrden").collapse('show');
  $("#estadoPendiente2").prop('checked', false);
  $("#estadoFirmar").prop('checked', false);
  $("#bo_btnBuscarOrden").prop('disabled', true);
  $("#buscarOrdenesModal").modal('show');
}

$( document ).ready(function() {
  $('body').on('keydown', function(e) {
   if(["ArrowUp","ArrowDown"].indexOf(e.code) > -1) {
               e.preventDefault();
           }
           if (e.target.tagName === "INPUT" || e.target.tagName === "SELECT") {
            // El evento se originó en un input o select, no se ejecuta ninguna acción adicional
            return;
          }
       let $table = $('#ordenesDatatable');
       let $table2 = $('#resultadosExamenesOrdenesDatatable');
       let key = e.which;
       if($table.is(":visible")){
           moveInTables($table, key,"ordenesDatatable",-1);
       }
   });

  
});





function moveInTables($table, key, id, rowselected) {
  let data = "";
  const UP = 38;
  const DOWN = 40;
  const ENTER = 13;
  const ARROWS = [UP, DOWN, ENTER];
  const HIGHLIGHT = 'muestra-blue';

  if (ARROWS.includes(key)) {
    let selectedRow = rowselected;
    let $rows = id == "ordenesDatatable" ? $table.children('tbody').children('tr:not(":hidden")') : $table.children('tbody').children('tr:not(":hidden")').filter(function(td) {
      if (!$(this).children('td:eq(3)').children("input").attr('disabled')) {
        return true;
      }
    }).closest('tr');

    $rows.each(function(i, row) {
      if ($(row).hasClass(HIGHLIGHT)) {
        selectedRow = i;
      }
    });

    $table.children().find(':focus').each(function() {
      if ($(this).closest('table').is($table)) {
        if (key == UP && selectedRow > 0) {
          $rows.removeClass(HIGHLIGHT);
          $rows.eq(selectedRow - 1).addClass(HIGHLIGHT);
          if ($rows.eq(selectedRow - 1).is('tr')) {
            $table.parent().scrollTop($rows.eq(selectedRow - 1).position().top);
          }
        } else if (key == DOWN && selectedRow < $rows.length - 1) {
          $rows.removeClass(HIGHLIGHT);
          $rows.eq(selectedRow + 1).addClass(HIGHLIGHT);
          if ($rows.eq(selectedRow + 1).is('tr')) {
            $table.parent().scrollTop($rows.eq(selectedRow + 1).position().top);
          }
        }
        if (id == "ordenesDatatable") {
          data = tableInfoOrdenes.row($rows[selectedRow]).data();
        } else {
          data = tableResultadosExamenesOrden.row($rows[selectedRow]).data();
        }
        if (typeof data !== "undefined" && key == ENTER && id == "ordenesDatatable") {
          let data2 = tableInfoOrdenes.rows($rows[selectedRow]).data();
          selectRowInfoOrdenes(data2[0], key);
        }
      }
    });
  }
}

function autoMoveTable($table, rowselected) {
  console.log("rowselected", rowselected);
  let data = "";
  const HIGHLIGHT = 'muestra-blue';

  let $rows = $table.children('tbody').children('tr:not(":hidden")').filter(function(td) {
    if (!$(this).children('td:eq(3)').children("input").attr('disabled')) {
      return true;
    }
  }).closest('tr');
  let selectedRow = $rows.length < rowselected ? $rows.length : rowselected;
  $rows.each(function(i, row) {
    if ($(row).hasClass(HIGHLIGHT)) {
      selectedRow = i;
    }
  });

  $rows.removeClass(HIGHLIGHT);
  $rows.eq(selectedRow).addClass(HIGHLIGHT);
  let $focusedElement = $(document.activeElement);
  if ($table.has($focusedElement).length > 0) {
    $($rows[selectedRow]).children("td:eq(3)").children("input").focus();
  }
}

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

const capitalizeMultipletLetter = (s) => s.replace(/\b\w/g, c => c.toUpperCase());

function positionOrden(nOrden){
  let rows = tableInfoOrdenes.rows({ search: 'applied' }).data();
  let n = rows.length;
  let j = 0;
  for (let i = 0; i < n; i++) {
    if (rows[i].df_NORDEN === nOrden) {
      j = i;
      break;
    }
  }
  if (j >= 0 && j < n) {
    const element = document.getElementById('order-progress2');
    console.log('total datos',n)
    $("#order-progress2").attr("aria-valuenow",j + 1); 
    let valuenow =  $("#order-progress2").attr("aria-valuenow");
    // let valuemax = $("#order-progress2").attr("aria-valuemax"); 
    // let valuenow =  $("#count_orden_inicio").text();
    let valuemax = $("#count_orden_fin").text(); 
    let total = (valuenow * 100) / valuemax;
    element.style.width = `${total}%`;
    console.log(valuenow,valuemax,"total");
    console.log(total,"total");
    $("#count_orden_inicio").text(valuenow);
    // $("#order-progress").css('width', total.toFixed(2)+"%");
  }
}

