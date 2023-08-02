 // const { Console } = require("console");
//IIFE 
$(function() {    
  function nTipoDocumento(documento){
    let tipoDocumento = 0;
    switch (documento) {
      case 1:
          tipoDocumento = "RUN";
        break;
        case 2:
          tipoDocumento = "PASAPORTE";
        break;
        case 3:
          tipoDocumento = "RECIEN NACIDO";
        break;
        case 4:
          tipoDocumento = "SIN IDENTIFICACION";
        break;
      default:
        break;
    }

    return tipoDocumento;
  }





  class BusquedaPacientes{

    boBuscador;
    bo_OrdenesTable;
    tm_MuestrasTable;
    nro_Orden;
    
    constructor(){
      this.boBuscador = null;
      this.bo_OrdenesTable = null;
      this.tm_MuestrasTable = new MuestraList();
      this.nro_Orden = -1;  
      }
    }
    
    
    
    
    
    var gBusquedaPacientes = new BusquedaPacientes();
 
 function clickOnOrden(data){
 let nOrden = data[0].df_NORDEN 
 
   GvalorInicioRow = 0;
   $("#idOrdenEvento").val(nOrden)
   orderEventsDetail(nOrden, "")
 
 }

$("#buscarEventoOrden").click(function (){	
	let nOrden = $("#idOrdenEvento").val();
	let filtro =  $("#textBuscarEvento").val();
	 orderEventsDetail(nOrden, filtro)

})
$("#btnExcelOrden").click(function(){	
	agregarExcel("Eventos de la Orden")
})


$("#btnLimpiarTabla").click(function (){
	 $("#idOrdenEvento").val("")
	  $("#idOrderCanvasEventsTable").html("") 
	  $("#tituloAcciones").text("")
	  $("#textBuscarEvento").val("")
	   $("#panelBusquedaOrden").collapse('show');
	   $("#OrdenEvents").collapse('hide')
})

function orderEventsDetail(id, filtro, idTest) {	 
	  
  localSetCurrentOrderId(id);
  $.getJSON("api/orden/eventos/fichas/", { orderId: id , filtro:filtro, idTest:idTest} )
    .done(function(order) {
	  $("#tituloAcciones").text("Acciones de la Orden N°"+id)
      showOrderEvents(order.dato);
     
      $("#OrdenEvents").collapse('show')
       $("#panelBusquedaOrden").collapse('hide');
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}



//*************************************   Funciones para crear Excel *************************** */

function agregarExcel(nombreExcel) {

	var table = document.querySelector("#tb");
	var sheet = XLSX.utils.table_to_sheet(table); // Convertir un objeto de tabla en un objeto de hoja
	
		openDownloadDialog(sheet2blob(sheet), nombreExcel + '.xlsx'); // Nombre del archivo
}

function sheet2blob(sheet, sheetName) {
	sheetName = sheetName || 'sheet1';
	var workbook = {
		SheetNames: [sheetName],
		Sheets: {}
	};
	workbook.Sheets[sheetName] = sheet; // Generar elementos de configuración de Excel

	var wopts = {
		bookType: 'xlsx', // El tipo de archivo que se generará
		bookSST: false, // Ya sea para generar una tabla de cadenas compartidas, la explicación oficial es que si activa la velocidad de generación, disminuirá, pero tiene una mejor compatibilidad en dispositivos IOS inferiores
		type: 'binary'
	};
	var wbout = XLSX.write(workbook, wopts);
	var blob = new Blob([s2ab(wbout)], {
		type: "application/octet-stream"
	}); // Cadena a ArrayBuffer

	function s2ab(s) {
		var buf = new ArrayBuffer(s.length);
		var view = new Uint8Array(buf);
		for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
		return buf;
	}
	return blob;
}


var table = document.querySelector("#tb");
var sheet = XLSX.utils.table_to_sheet(table); // Convertir un objeto de tabla en un objeto de hoja


function openDownloadDialog(url, saveName) {
	if (typeof url == 'object' && url instanceof Blob) {
		url = URL.createObjectURL(url);
	}
	var aLink = document.createElement('a');
	aLink.href = url;
	aLink.download = saveName || '';
	var event;
	if (window.MouseEvent) event = new MouseEvent('click');
	else {
		event = document.createEvent('MouseEvents');
		event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
	}
	aLink.dispatchEvent(event);
}






















    function opcionesDisponibles(row){
      return '\<div class="row">\
                  <a href="#" class="btn btn-sm btn-clean btn-icon" title="Datos Paciente" data-toggle="modal" data-target="#ModalDatosPaciente" onclick=\'MostrarDatosPaciente(' + row['df_NORDEN'] + ',' + row['dp_IDPACIENTE'] +') \'>\
                    <i class="fas fa-user-md"></i><span class="nav-text"></span>\
                  </a>\
                 <a href="#" class="btn btn-sm btn-clean btn-icon" title="Orden/Exámenes" data-toggle="modal" data-target="#ModalDatosOrden" onclick=\'MostrarDatosOrden(' + row['df_NORDEN'] + ') \'>\
                      <i class="fa fa-search" aria-hidden="true"></i><span class="nav-text"></span>\
                  </a>\
            </div>'
    }





    $(document).ready(function() {


      const opciones = [
        {
          targets: [0,4,5,9,10,11,12,13,14,15,16,19,20,21,22,23,24,26,27,28,29], //targets no visibles
          visible:false,
        }
      ,{
        targets: 2, //fecha formatiada
        visible:true,
        render: function (data, type, row){
           let dia = data.substring(0, 2);
          let mes = data.substring(3, 5);
          let year = data.substring(8, 10);
          let hora = data.substring(11, 16);
         return data;
        //  return hora + " " + dia + "/" + nombresMeses(mes) + "/" + year;
        }
      }
      ,{
        targets: 3, //Estado
        visible:true,
        render: function (data, type, row){
          const color = estadoOrdenColor[row["ldvfet_DESCRIPCION"]];
          return `<span class="label label-lg font-weight-bold label-inline estadoOrden-${color}" data-id="${row["df_IDFICHAESTADOTM"]}"  >${row["ldvfet_DESCRIPCION"]}</span>`;
        }
        
      }
      ,{
        targets: 7, //Rut formatiado
        visible:true,
        render: function (data, type, row){
          if(row["ldvtd_DESCRIPCION"] == "RUN"){
            const nDocumento = cambiarDatoRut(row["dp_NRODOCUMENTO"]);
            return nDocumento;
          }else{
            return row["dp_NRODOCUMENTO"];
          }
        }
      }
      ,{
        targets: 8, //Nombre completo
        visible:true,
        render: function (data, type, row){
            return data+" "+row['dp_PRIMERAPELLIDO']+" "+(row['dp_SEGUNDOAPELLIDO'] || "");
        }
      },
      {
        targets: 14, //Edad
        visible:true,
        render: function(data, type, full, meta) {
            return data!==null ? calcularEdadTM(data) : "";
        }
        
      },
    ];


      gBusquedaPacientes.boBuscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
        "#bo_div_fIni",
        "#bo_div_fFin",
        "#bo_div_nombre",
        "#bo_div_apellido",
        "#bo_div_tipoDocumento",
        "#bo_div_nroDocumento",
        "#bo_div_procedencia",
        "#bo_div_servicio"]);
        gBusquedaPacientes.bo_OrdenesTable = new BiolisDatatableOrdenes('#bo_t_ordenes', gBusquedaPacientes.boBuscador,clickOnOrden,opciones);
      $("#bo_btnBuscarOrden").click(gBusquedaPacientes.bo_OrdenesTable.doSearch);
      $("#btnLimpiarFiltro").click(gBusquedaPacientes.bo_OrdenesTable.doLimpiarForm);
   
    });
});