class EventosCapturaResultados {
    orderId;
    examId;
    
    constructor(){
        
    }
    
    localSetCurrentOrderId(orderId) {
        this.orderId = orderId;
    } 
    
    orderDetail(orderId) {
        this.localSetCurrentOrderId(orderId);
        $.getJSON("Microbiologia/api/getOrderData", { orderId: this.orderId })
            .done(function(order) {
                showOrderData(order);
            })
            .fail(function(jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
            });
    }
    
    orderEventsDetail(orderId, filtro, idTest){
        this.localSetCurrentOrderId(orderId);
        $.getJSON("api/orden/eventos/fichas/", { orderId: orderId, filtro:filtro, idTest:idTest})
            .done(function(order) {
                showOrderEvents(order.dato);
            $('#OrderCanvasEvents').addClass('show active');
            })
            .fail(function(jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
            });
    }
    
}


/*var GlobalCurrentOrderId;

function orderDetail(id) {
  localSetCurrentOrderId(id);
  $.getJSON("Microbiologia/api/getOrderData", { orderId: id })
    .done(function(order) {
      showOrderData(order);
      showOrderEvents(order.events);
      $("#OrderCanvas").show();
      $("#ExamCanvas").hide();
      $("#TestCanvas").hide();
      $("#AntibiogramCanvas").hide();
      showExamList(order.exams);
      $("#btnTasksOpen").attr("href", "javascript:void(0)");
      $("#ExamsListToggle").click();
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function getOrderDetail(idOrden) {
  $("#datosPacienteModal").modal('show');
  orderDetail(idOrden);
  localSetCurrentOrderId(idOrden);
}

function localSetCurrentOrderId(examId) {
  GlobalCurrentOrderId = examId;
}
*/
//Funciones de eventos de ordenes
function getOrderEventsDetail(idOrden) {
    $("#tituloEvento").text("Eventos de la Orden: "+ idOrden)	
    $("#buscarEventoOrden").show();
    $("#buscarEventoTest").hide();

    $("#eventosOrdenModal").modal('show');
    orderEventsDetail(idOrden);
}

function orderEventsDetail(orderId, filtro, idTest){
  localSetCurrentOrderId(orderId);
  /*Se modifica getJSON por Marco Caracciolo 20/03/2023
    */
   $.getJSON("api/orden/eventos/fichas/", { orderId: orderId, filtro:filtro, idTest:idTest})
    .done(function(order) {
      showOrderEvents(order.dato);
      $('#OrderCanvasEvents').addClass('show active');
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function showOrderData(order) {
	console.log("entre a showOrderData")
	
  $("#idOrderCanvasDataName").text(order.patient.name);
  // agregado por cristian 16-01-2023
  if(order.patient.tipoDocumento == "1"){
	$("#idOrderCanvasDataDocumento").text(cambiarDatoRut(order.patient.nroDocumento)); 
	}else{
		 $("#idOrderCanvasDataDocumento").text(order.patient.nroDocumento);
	}
  $("#idOrderCanvasDataHost").text(order.order.host);
  $("#idOrderCanvasDataGender").text(order.patient.gender);
  $("#idOrderCanvasDataAge").text(normalizarEdad(order.patient.age));
  $("#idOrderCanvasDataDateOfBirth").text(YYYY_MM_DD2DD_MM_YY(order.patient.dateOfBirth,'-','/')); 
  $("#idOrderCanvasDataPhoneNumber").text(order.patient.phoneNumber);
  $("#idOrderCanvasDataAtentionType").text(order.patient.atentionType);
  $("#idOrderCanvasDataLocalization").val(order.patient.localization);
  $("#idOrderCanvasDataPatientObservation").text(order.patient.observation);
  $("#idOrderCanvasDataPathologies").text(order.patient.pathologies);

  $("#idOrderCanvasDataOrderId").text(order.order.id);
 // $("#idOrderCanvasDataDate").text(YYYY_MM_DD2DD_MM_YY(order.order.date,'-','/'));
 $("#idOrderCanvasDataDate").text( order.order.date == "" ? "" : convertirDateDD_MM_YYYY_HH_mm_SS(new Date(order.order.date), '-', ':'));
  $("#idOrderCanvasDataContract").text(order.order.contract);
  $("#idOrderCanvasDataPhysician").text(order.order.physician);
  $("#idOrderCanvasDataDiagnostic").text(order.order.diagnostic);
  $("#idOrderCanvasDataOrderObservation").text(order.order.observation);
  $("#idOrderCanvasDataService").text(order.order.service);
  $("#idOrderCanvasDataOrigin").text(order.order.procedencia);

  let orders = order.previousOrders;
  let ordersList = " "
  if(order.previousOrders != null){
	  for (let i=0;i<gCte.nroMaxOrdenesHistoricas && i < 4 ;i++){
		
		if(orders[i] != undefined){
	   	 ordersList =  ordersList.concat(orders[i]).concat(' , ');
	    }
	  }
	  if(orders[4] != undefined){
	 	  ordersList =  ordersList.concat(orders[4]);
	   }
 } 
  $("#idOrderCanvasDataPreviousOrders").text(ordersList);
}
/*
function showOrderEvents(events) {
  $("#idOrderCanvasEventsTable").empty();
  let html = "";
  $("#idOrdenEvento").val(events[0].lef_NORDEN)
  $("#idTestEvento").val(events[0].ct_IDTEST)
  
   events.forEach(function (dato) {
	console.log("eventos *****")
	console.log(dato)
	//*****************************  agregado desde aqui 
	if(dato.lef_IDEVENTO == 1){
		let mensaje = "";
		if(dato.ce_ABREVIADO == null && dato.lef_CODIGOBARRA == null){
			console.log(dato.lef_NOMBRETABLA)
			if(dato.lef_NOMBRETABLA == "DATOS_FICHASANTECEDENTES"){
				mensaje = "  Crea antecedente <b>"+ dato.lef_VALORNUEVO + "</b> en Orden  N° <b>" + dato.lef_NORDEN +"</b>"
			}else{
				mensaje = "  Crea Orden N° <b>" + dato.lef_NORDEN +"</b>"
			}
		}else{
			if(dato.ce_ABREVIADO == null){
				mensaje = " Crea Muestra : <b>" + dato.lef_CODIGOBARRA +"</b>";
			}else{
				mensaje = " Crea examen : <b>" + dato.ce_ABREVIADO +"</b>";
			}
		}
		
		
		html += "<tr><td>" + (new Date(dato.lef_FECHAREGISTRO).toLocaleString())
		 + "</td><td>" + (dato.du_NOMBRES == null ? "" : dato.du_NOMBRES) + " " + (dato.du_PRIMERAPELLIDO == null ? "" : dato.du_PRIMERAPELLIDO ) +  " "  +	(dato.du_SEGUNDOAPELLIDO == null ? "" : dato.du_SEGUNDOAPELLIDO )
		  + "</td><td>" + (dato.lef_IPESTACION == null ? "" : dato.lef_IPESTACION  ) +" </td><td>"+  dato.nombrepaciente +" </td><td>"+ dato.lef_NORDEN  +" </td><td>"+ 
		   (dato.ce_ABREVIADO == null ? "" : dato.ce_ABREVIADO ) + " </td><td>"+ ( dato.ct_ABREVIADO == null ? "" : dato.ct_ABREVIADO  ) + "  </td><td>" + 
		    (dato.lef_CODIGOBARRA == null ? "" : dato.lef_CODIGOBARRA ) +" </td><td>"+ 
		    mensaje + "</td></tr>"
			
	}else{
		html += "<tr><td>" + (new Date(dato.lef_FECHAREGISTRO).toLocaleString())
		 + "</td><td>" + (dato.du_NOMBRES == null ? "" : dato.du_NOMBRES) + " " + (dato.du_PRIMERAPELLIDO == null ? "" : dato.du_PRIMERAPELLIDO ) +  " "  +	(dato.du_SEGUNDOAPELLIDO == null ? "" : dato.du_SEGUNDOAPELLIDO )
		  + "</td><td>"+   (dato.lef_IPESTACION == null ? "" : dato.lef_IPESTACION  ) +" </td><td>"+  dato.nombrepaciente + " </td><td>" +  dato.lef_NORDEN  + "  </td><td>" +
		   (dato.ce_ABREVIADO == null ? "" : dato.ce_ABREVIADO ) + " </td><td>"+ ( dato.ct_ABREVIADO == null ? "" : dato.ct_ABREVIADO  ) + "  </td><td>" + 
		    (dato.lef_CODIGOBARRA == null ? "" : dato.lef_CODIGOBARRA ) +" </td><td>"+ 
		    " Se modificó el campo <b>"  + dato.lef_NOMBRECAMPO + "</b>  de <b>" + (dato.lef_VALORANTERIOR == null ? "-" :dato.lef_VALORANTERIOR  ) 
		    + "</b> a valor nuevo <b>"+ (dato.lef_VALORNUEVO == null ? "-" : dato.lef_VALORNUEVO )+ "</b></td></tr>"			
	}
				
  });
  $("#idOrderCanvasEventsTable").html(html);
		
}
*/
$("#btnExcelEvento").click(function(){	
	agregarExcel("Eventos de la Orden")
})

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


//var table = document.querySelector("#tb");
//var sheet = XLSX.utils.table_to_sheet(table); // Convertir un objeto de tabla en un objeto de hoja


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


