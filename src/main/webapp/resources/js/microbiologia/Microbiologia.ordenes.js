//ordenes
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
  var estado = 1;

  $(document).ready(function() {

    let colFilter = [
        {"col": 0, "type": "orden" },
        {"col": 1, "type": "date" },
        {"col": 2, "type": "text" },
        {"col": 3, "type": "sinfiltro" },
        {"col": 4, "type": "sinfiltro" },
        {"col": 5, "type": "select" },
        {"col": 6, "type": "select" },
        {"col": 7, "type": "select" },
        {"col": 8, "type": "selectUrgente" },
        {"col": 9, "type": "limpiar" },
        {"col": 10, "type": "" },
        {"col": 11, "type": "" },
        {"col": 12, "type": "" },
        {"col": 13, "type": "" }
    ];
    
    gBusquedaPacientes.boBuscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
      "#bo_div_nOrdenFin",
      "#bo_div_fIni",
      "#bo_div_fFin",
      "#bo_div_nombre",
      "#bo_div_apellido",
      "#bo_div_tipoDocumento",
      "#bo_div_nroDocumento",
      "#bo_div_procedencia",
      "#bo_div_tipoAtencion",
      "#bo_div_servicio",
      "#bo_div_seccion",
      "#bo_div_examen"]);
      
      gBusquedaPacientes.bo_OrdenesTable = new BiolisTableOrdenesMicrobiologia('#bo_t_ordenes', gBusquedaPacientes.boBuscador,clickOnOrden);
      gBusquedaPacientes.bo_OrdenesTable.setColFilter(colFilter);
    
    
    /* Agregado por Marco Caracciolo*/    
      gBusquedaPacientes.bo_OrdenesTable.searchPendiente(["P"],"","");

      searchExamenPendiente(["P"],"","#examenesOrdenesDatatable");
      
      //searchResultadoPendiente(text,cultivo2,cultivo,"#resultadosExamenesOrdenesDatatable");
      /***/
      $("#bo_btnBuscarOrden").click(gBusquedaPacientes.bo_OrdenesTable.doSearch);
    $("#bo_btnBuscarOrden").click(function(e) {
      let info = gBusquedaPacientes.bo_OrdenesTable.getResultado();
      if(info == 'ok'){
        $("#buscarOrdenesModal").modal('hide');
      }
    })

    $("#btnRefreshBuscarOrden").click(function f() { 
        gBusquedaPacientes.bo_OrdenesTable.reloadOrdenes(gBusquedaPacientes.boBuscador) ;
    });
    
    $('#idChkAutorefresh').change(function(){
        console.log($(this).prop("checked"),estado);
        if ($(this).prop("checked") === true){
        estado = 0;
        intervalo = setInterval( function () {
          let pendiente = $("#estadoPendiente:checked")[0];
          if (pendiente === undefined) {
            pendiente = "";
          }
          else {
            pendiente = "P";
          }

          gBusquedaPacientes.bo_OrdenesTable.searchPendiente(["P"],"","");
          gBusquedaPacientes.bo_OrdenesTable.reloadOrdenes(gBusquedaPacientes.boBuscador); // user paging is not reset on reload
          console.log("actualizando...")
        }, 10000 );
        }
        if($(this).prop("checked") === false){
        estado = 1;
        console.log("desactivando actualizacion")
        clearInterval(intervalo);
        }

    //console.log("estado: " +estado);
    });
    var intervalo = setInterval( function () {
        if(estado === 0){
          gBusquedaPacientes.bo_OrdenesTable.reloadOrdenes(gBusquedaPacientes.boBuscador); // user paging is not reset on reload
            console.log("actualizando...")
        }
      }, 50000 );

      var tableInfoOrdenes = $('#bo_t_ordenes').DataTable();

      tableInfoOrdenes.on('draw', function() {
        if (tableInfoOrdenes.settings()[0].oFeatures.bServerSide){
          tableInfoOrdenes.settings()[0].oFeatures.bServerSide = false;
          tableInfoOrdenes.draw();
        }
      
        $('.containerTable').removeClass('hidden');
        $(".spinnerContainer1").remove();
      });
   
  });
/*
$("#seccionSelect").change(function() {
  const seccion = $(this).val();
  gSeccionSeleccionada = seccion;
  searchSeccion(seccion);
  searchExamenSeccion(seccion);
  gSeccionSeleccionada = seccion;
})


function searchSeccion(seccion) {
    //let val = $.fn.dataTable.util.escapeRegex(seccion);
//  let val = $.fn.dataTable.util.escapeRegex(seccion).concat('X');
  let columna = $('#bo_t_ordenes').DataTable().column(10);
  columna.search(seccion, true, false, false).draw();
  //resetOrdenProgress();
  $("#order-progress").css('width', "0%");
    //resetTables();
}
*/

  filtrarEstadosMicro("#estadoPendiente");
  filtrarEstadosMicro("#estadoEnProceso");
  filtrarEstadosMicro("#estadoCultivo");

  function filtrarEstadosMicro(element){
    $(element).change(function(){
        let pendiente = $("#estadoPendiente").is(":checked") ? "P" : "";
        let enProceso = $("#estadoEnProceso").is(":checked") ? "S" : "";
        let cultivo = $("#estadoCultivo").is(":checked") ? "S" : "";
        
        let cultivo2 = $('input[name="cultivoChck"]:checked').val();
          
      let  text = null; //[pendiente, enProceso, "F","I","B","A"].filter(Boolean).join("|");
      if (pendiente === "P"){
          text = ["P"];
      } else {
          text = ["P", "F","I","B","A"].filter(Boolean).join("|");
      }
      gBusquedaPacientes.bo_OrdenesTable.searchPendiente(text,cultivo,enProceso);
      //gBusquedaPacientes.boDatatableMicrobiologia.searchPendiente(text,cultivo,enProceso);
      searchExamenPendiente(text,cultivo,"#examenesOrdenesDatatable");
      if($("#txtNroOrden").text() != ""){
        gBusquedaPacientes.bo_OrdenesTable.positionOrden($("#txtNroOrden").text());
        //gBusquedaPacientes.boDatatableMicrobiologia.positionOrden($("#txtNroOrden").text());
      }
      searchResultadoPendiente(text,cultivo2,cultivo,"#resultadosExamenesOrdenesDatatable");
    });
  }

  filtrarEstadosResultados('input[type=radio][name=cultivoChck]');

  function filtrarEstadosResultados(element){
    $(element).change(function(){
      let pendiente = $("#estadoPendiente:checked")[0];
      let enProceso = $("#estadoEnProceso:checked")[0];
      let cultivo = $("#estadoCultivo:checked")[0];
      if (pendiente === undefined) {
        pendiente = "";
      }
      else {
        pendiente = "P";
      }
  
      if(enProceso === undefined){
          enProceso = "";
        }else{
          enProceso = "E";
      }
      if(cultivo === undefined){
        cultivo = "";
        }else{
          cultivo = "S";
      }

      if(pendiente == "" && enProceso == ""){
        pendiente = "P";
        enProceso = "E";
    }


      let  text = [pendiente, enProceso,"F","I","B","A"].filter(Boolean).join("|");
      let cultivo2 = $('input[name="cultivoChck"]:checked').val();
      searchResultadoPendiente(text,cultivo2,cultivo,"#resultadosExamenesOrdenesDatatable");
    });
  }

  function clickOnOrden(data){
    selectRowInfoOrdenes(data[0]);
  }


function selectRowInfoOrdenes(data, key = false) {
    fillOrdenData(data, key);
    // if (tableInfoOrdenes.colClicked !== 9) {
      $("#accordionPanelSeleccionOrden").collapse('hide');
      $("#accordionTestResultados").collapse('hide');
      // tableInfoOrdenes.colClicked = -1;
    // }
    // tableResultadosExamenesOrden.clear();
  }

function fillOrdenData(data, key = false) {
  nroOrdenSeleccionada = data.df_NORDEN;
  nroOrdenSeleccionadaTest = data.df_NORDEN;
  localSetCurrentOrderId(nroOrdenSeleccionada);
  loadInfoExamenesOrden(nroOrdenSeleccionada, key);
  loadResultadosExamenesOrden(nroOrdenSeleccionada,0,key)
  setUserData(nroOrdenSeleccionada);
  gBusquedaPacientes.bo_OrdenesTable.positionOrden(nroOrdenSeleccionada);
}


function setUserData(ordenSeleccionada) {

  $.getJSON("Microbiologia/api/getOrderData", { orderId: ordenSeleccionada })
  .done(function (order) {
    $("#txtNombrePaciente").text(order.patient.name);
    $("#txtSexoPaciente").text(order.patient.gender);
    $("#txtEdadPaciente").text(normalizarEdad(order.patient.age));
    $("#txtTipoAtencion").text(order.patient.atentionType);
    $("#txtLocalizacion").text(order.order.service);
    $("#txtProcedencia").text(order.order.procedencia);
    $("#txtNroOrden").text(order.order.id);
    $("#txtFechaOrden").text(order.order.date);
    //$("#txtFechaOrden").text(DD_MM_YY2DD_MM_YY(order.order.date,'-','/'));
  })
  .fail(function (jqxhr, textStatus, error) {
    var err = textStatus + ", " + error;
    console.log("Request Failed: " + err);
  });

  // positionOrden(ordenSeleccionada.id);
}


function getOrderDetail(idOrden) {
  $("#datosPacienteModal").modal('show');
  eventosMicrobiologia.orderDetail(idOrden);
  //orderDetailPac(idOrden);
  //localSetCurrentOrderId(idOrden);
}


function getAntecedentePacientesModal(idOrden) {
  $("#modalAntecedentesPac").modal('show');
  mostrarModalAntecentesPac(idOrden, 0);
}

function orderDetailPac(id) {
  localSetCurrentOrderId(id);
  $.getJSON("Microbiologia/api/getOrderData", { orderId: id })
    .done(function(order) {
      showOrderData(order);
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}


$("#btnPrevOrder").click(navegarPrev);
$("#btnFirstOrder").click(navegarFirst);
$("#btnNextOrder").click(navegarNext);
$("#btnLastOrder").click(navegarLast);


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
  let rows =  gBusquedaPacientes.bo_OrdenesTable.getRows();
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

//Funciones de eventos de ordenes
/*
function getOrderEventsDetail(idOrden) {
    $("#tituloEvento").text("Eventos de la Orden: "+ idOrden)	
    $("#buscarEventoOrden").show();
    $("#buscarEventoTest").hide();

    $("#eventosOrdenModal").modal('show');
    orderEventsDetail(idOrden);
}


function orderEventsDetail(orderId, filtro, idTest){
  localSetCurrentOrderId(orderId);
  //Se modifica getJSON por Marco Caracciolo 20/03/2023
   
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

*/

/**
 * 
 * Se cambia función de acuerdo a lo que hay en Microbiologia.js
 * Marco Caracciolo 20/03/2023
 *
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
/*
$("#buscarEventoOrden").click(function (){	
	let filtro = $("#textBuscarEvento").val()	
	let id = $("#idOrdenEvento").val();
	//let idTest = $("#idTestEvento").val();
	  orderEventsDetail(id, filtro);

})
*/
$("#buscarEventoTest").click(function (){	
	let filtro = $("#textBuscarEvento").val()	
	let id = $("#idOrdenEvento").val();
	let idTest = $("#idTestEvento").val();
	  orderEventsDetail(id, filtro, idTest);

})

$("#textBuscarEvento").keyup(function (){	
	let filtro = $("#textBuscarEvento").val()	
	let id = $("#idOrdenEvento").val();
	//let idTest = $("#idTestEvento").val();
	  orderEventsDetail(id, filtro);

})
/*
$("#limpiarBusqueda").click(function (){
    $("#textBuscarEvento").val("");
    let id = $("#idOrdenEvento").val();
    orderEventsDetail(id);
});*/