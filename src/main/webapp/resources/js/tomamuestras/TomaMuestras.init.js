var tableMuestras = null;//tableMuestras
var registros_muestras = 0;
var idTipoMuestra = null;
var idEnvase = null;
var idDerivador = null;
var comparteMuestra = null;
var idMuestra = null;
var today = new Date();
var centroSelected = 0;
var labSelected = 0;
today = convertirDateGuionDDMMYYYY(today);


var datosMuestra = null;

$(document).ready(function (){
    $('.ocultarTodoModalAntecedentes').hide();
    $(".ocultar").hide();
    $('#filtroFecha').val(today);

    let loader = iconLoading('spinnerContainer1');
    let loader2 = iconLoading('spinnerContainer2');
    $('.agregandoClass').append(loader);
    $('.containerTableMuestras').parent().append(loader2);
    
    initTableOrdenes();
    initTableMuestras();
    $("#tablaOrdenesCollapse").on('hidden.bs.collapse',function(){
      $("#divTablaMuestras").show();
    });
    $("#tablaOrdenesCollapse").on('shown.bs.collapse',function(){
      $("#divTablaMuestras").hide();
    });
       $('body').on('keydown', function(e) {
    if(["ArrowUp","ArrowDown"].indexOf(e.code) > -1) {
                e.preventDefault();
            }
    if(e.target.id === "busquedaOrden" || e.target.id === "pacienteFiltro"){
        return;
    }
        let $table = $('#tablaPrioridadAtencion');
        let $table2 = $('#tablaMuestras');
        let key = e.which;
        if($table.is(":visible")){
            moveInTables($table, key,"tablaPrioridadAtencion");
        } else{
            moveInTables($table2, key,"tablaMuestras");
        }
    });


});

$("#volverNomalidad").click(function () {
    tmClickNormal();
});

function tmClickNormal() {//rut

    $.ajax({
        type: "post",
        data: "volverNomalidad=" + "nOrden",
        datatype: "json",
        success: function () {
            alert("Todos Disponibles");

        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

function moveInTables($table,key,id){
    if ($('.modal.show').length || $(':focus').is('input')) {
        return; // Return without executing the function if a Bootstrap modal is open
      }

    let data = "";
    const UP = 38;
    const DOWN = 40;
    const ENTER = 13;
    const ARROWS = [UP, DOWN,ENTER];
    const HIGHLIGHT = 'muestra-blue';

    if (ARROWS.includes(key)) {
        let selectedRow = -1;
        let $rows = $table.find('tbody tr:not(":hidden")');
        $rows.each(function(i, row) {
          if ($(row).hasClass(HIGHLIGHT)) {
            selectedRow = i;
          }
        });
        if (key == UP && selectedRow > 0) {
          $rows.removeClass(HIGHLIGHT);
          $rows.eq(selectedRow - 1).addClass(HIGHLIGHT);
          if($rows.eq(selectedRow - 1).is('tr')){
              $table.parent().scrollTop($rows.eq(selectedRow - 1).position().top);
          }
        } else if (key == DOWN && selectedRow < $rows.length - 1) {
          $rows.removeClass(HIGHLIGHT);
          $rows.eq(selectedRow + 1).addClass(HIGHLIGHT);
          if($rows.eq(selectedRow + 1).is('tr')){
              $table.parent().scrollTop($rows.eq(selectedRow + 1).position().top);
          }
        }
        if(id == "tablaPrioridadAtencion"){
            data = table.row($rows[selectedRow]).data();
        }else{
            data = tableMuestras.row($rows[selectedRow]).data();
        }

        if(typeof data !== "undefined" && key == ENTER && id == "tablaPrioridadAtencion"){
            tablaOrdenesData(data);
        }
        // else if(typeof data !== "undefined" && key == ENTER && id == "tablaMuestras"){

        // }
    }
  
}


/*

function IngresarDatosMuestra(a) {
    var $row = $(a).closest("tr"); // Find the row
    //Paciente
    var idPrefijo = $row.find(".prefijoMuestra").text(); // Find the text
    var nOrden = $("#idPacienteTabla").val();
    var idUsuario = $("#idPacienteTabla").val();
    var idPaciente = $("#idPacienteTabla").val();
    $.ajax({
        type: "post",
        data: "IngresoMuestra=" + nOrden + "&idUsuario=" + idUsuario + "&idPaciente=" + idPaciente + "&idPrefijo=" + idPrefijo,
        datatype: "json",
        success: function () {

            location.reload();
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}


function cargarEstadosPaciente(){
    $.ajax({
       type: "POST",
       data: "selectEstadoPaciente=1",
       datatype: "json",
       success: function(data){
           let options = "";
           var datos = JSON.parse(data);
           for (variable of datos.estados) {
              options += variable.descripcion
           }
           $("#estadosPacienteLoad").val(options)
       },
       error: function (msg) {
           console.log(msg);
       } 
    });
}

function llenarSelectEstadoPaciente(idSelect, estado){
    $.ajax({
       type: "POST",
       data: "selectEstadoPaciente=1",
       datatype: "json",
       success: function(data){
           $("#"+idSelect).empty();
           var datos = JSON.parse(data);
           for (variable of datos.estados) {
  
               if (variable.descripcion == estado){
                   $("#"+idSelect).append('<option value='+variable.descripcion+' selected>'+variable.descripcion+'</option>'); 
               } else { 
                   $("#"+idSelect).append('<option value='+variable.descripcion+'>'+variable.descripcion+'</option>'); 
               }
           }
       },
       error: function (msg) {
           console.log(msg);
       } 
    });
}



*/

