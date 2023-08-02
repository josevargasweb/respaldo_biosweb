// examens
var pLstExamenesSeleccionados = [];
var paramResultadosExamenesSeleccionados = { "examenes": pLstExamenesSeleccionados };

var estadoExamenesColorMap = new Array();
estadoExamenesColorMap['FIRMADO'] = 'firmado';
estadoExamenesColorMap['PENDIENTE'] = 'pendiente';
estadoExamenesColorMap['INGRESADO'] = 'ingresado';
estadoExamenesColorMap['EN PROCESO'] = 'proceso';
estadoExamenesColorMap['BLOQUEADO'] = 'bloqueado';
estadoExamenesColorMap['AUTORIZADO'] = 'autorizado';

var gOptionalTestContent = new OptionalTestContent();

let loadInfoExamenesOrden = function(nroOrden, key = false) {

  let url = gCte.getctx + "/Microbiologia/api/getExamenesByOrderId?orderId="+nroOrden;
  
  tableInfoExamenesOrden.ajax.url(url).load(function(){
      genSetExamenSeleccionado(nroOrden, key);
  });
}

let initTableInfoExamenesOrden = function(jqDtId) {
 let nroOrden = -1;
 let me = this;
  tableInfoExamenesOrden = $(jqDtId).DataTable({
    "rowId": "id",
    ajax: {
      "url": gCte.getctx + "/Microbiologia/api/getExamenesByOrderId?orderId="+nroOrden,
      "type": "GET",
      "dataSrc": "exams",
     
    },
    serverSide: true, 
    "language": {
      "thousands": ".",
      "decimal": ",",
      "emptyTable": "No hay datos disponibles",
      "loadingRecords": "Por favor espere - cargando ...",
      "zeroRecords": "No se encontraron datos",
    },
    "info": false,
    lengthChange: false,
    "pageLength": 1,
    autoWidth: false,
    paging: false,
    searchDelay: 500,
    orderCellsTop: true,
    scrollY: 200,
    scrollX: true,
    scrollCollapse: true,
    columnDefs: [
      {
        targets: 0,
        width: '30px',
        className: 'dt-left',
        orderable: false,
        render: function(data, type, full, meta) {
          return `<input type="checkbox" value=""  data-colselector='x' class="checkable chck-examen"/> `;
        },
      },
      {
        targets: 4,
        width: '30px',
        className: 'dt-left',
        orderable: false,
        render: zonaExamen
      },
      {
        targets: 5,
        width: '30px',
        className: 'dt-left',
        orderable: false,
        render: estadoExamen
      },
      {
        targets: [6,7],
        width: '30px',
        className: 'dt-left',
        orderable: false,
        render: fechaExamen
      },
      {
        targets: 8,
        width: '30px',
        className: 'urg',
        orderable: false,
        render: function(data, type, row, meta) {
          let checked = "";
          if(row.urgency == "S"){
              checked = "checked"
          }

          return `<input type="checkbox" ${checked} value="${data}"  class="checkable urgenci-examen" ${checked}/>`;
        }
      },
      {
      orderable: false,
      targets: [9],
      data: null,
      defaultContent: '',
      className: "notTMClick",
      render: genAccionExamenes,
      order: [[3, 'asc']]
    },
    {
      targets: [10,11],
      orderable: false,
      visible:false,
    },
  ],
    select: {
      style: 'multi',
      selector: 'td:not(:last-child)',
      items: 'cell',
      "className": 'row-selected',

    },
    columns: [
      { "": ""},
      { "data": "codigo", orderable: false,"width": "9%"},
      { "data": "exam", orderable: false,"width": "11%"},
      { "data": "sample", orderable: false,"width": "10%" },
      { "data": "bodypart", orderable: false,"width": "10%"},
      { "data": "processstatus", orderable: false,"width": "12%"},
      { "data": "seedingdate", orderable: false,"width": "12%"},
      { "data": "reseedingdate", orderable: false,"width": "12%"},
      { "data": "urgency", orderable: true,"width": "11%"},
      { "": "","width": "13%"},
      { "data": "cultivo","width": "0%"},
      { "data": "estado","width": "0%"},
    ],
    "initComplete": function(settings, json) {
      $(jqDtId+"_filter").hide();

      $("#examenesOrdenesDatatable_filter").hide();
      let rowFilter = $('<tr id="filterExamen" class="filterExamen"></tr>').appendTo($(tableInfoExamenesOrden.table().header()));
      
      this.api().columns().every(function() {
        let columna = this;
      });
      /*
      //si existen los elementos se le agregan
      let pendiente = $("#estadoPendiente").length ? "P": "";
      let enProceso = $("#estadoEnProceso").length ? "E": "";
      let  text = [pendiente, enProceso,"F","I","B","A"].filter(Boolean).join("|");

      let cultivo = $("#estadoCultivo").length ? "S":"";
      searchExamenPendiente(text,cultivo,jqDtId)
*/

    },
    "rowCallback": function( row, data, index ) {
        if ($('#examenesOrdenesDatatable tbody tr td:eq(0)').hasClass("row-selected")){
            $(this).find(".chck-examen").prop("checked", true);
        }
    }
  });


  tableInfoExamenesOrden.on('select', function(e, dt, type, indexes) {
    let data = tableInfoExamenesOrden.rows(indexes[0].row).data();
    // selectRowInfoExamenes(data[0]);
    
  })
  
  $("#seleccionaTodasLosExamenes").on("click", function(e) {
    selectAllExams();
  });

  tableInfoExamenesOrden.on( 'draw', function () {
    if (tableInfoExamenesOrden.settings()[0].oFeatures.bServerSide){
      tableInfoExamenesOrden.settings()[0].oFeatures.bServerSide = false;
      tableInfoExamenesOrden.draw();
    }
  
    $('.containerTableExamen').removeClass('hidden');
    $(".spinnerContainer2").remove();
    registro_examenes = tableInfoExamenesOrden.rows().count();
    //console.log('total rows',registros_muestras);
    let rows_filtroMue = tableInfoExamenesOrden.rows({ search: 'applied' }).count();
    $("#dt_registros_examenes").html("Mostrando <b>" + rows_filtroMue + "</b> de <b>" + registro_examenes + "</b> exámenes en total");
    $("#seleccionaTodasLosExamenes").prop('checked', true);        
    selectAllExams();

  });
  
}

function selectAllExams(){
    let inputs = $("input[data-colselector]");
    if ($("#seleccionaTodasLosExamenes").is(":checked")) {
      $(inputs).prop('checked', true);
      tableInfoExamenesOrden.rows().select();
    } else {
      $(inputs).prop('checked', false);
      tableInfoExamenesOrden.rows().deselect();
    }
}

function genSetExamenSeleccionado(nroOrden, key = false) {

  return function setExamenesSeleccionados() {
    //tableInfoExamenesOrden.rows().select();
    
    //Ver si funciona o no
    cargarResultadosExamenesTabla(nroOrden, key);
    //loadResultExameSeleccOrEmpty
  }
}

function cargarResultadosExamenesTabla(pNroOrden, key = false) {
  loadResultExameSeleccOrEmpty(pNroOrden, key);
}


 //filtros checkbox
function searchExamenPendiente(pendiente,cultivo,id) {
  $(id).DataTable().column(10).search(cultivo, true, false, false)
                  .column(11).search(pendiente, true, false, false).draw();
}

function genAccionExamenes(data, type, row, meta) {
  return `
    <div class="btn-toolbar row">
      <a href="#" onclick="getOpcTestExamenDetail('${row.id}','${row.orderid}','${row.estado}');" class="p-2" title="Tests Opcionales"><i class="fas fa-vials" aria-hidden="true"></i></a>
      <a href="#" onclick="getObsExamenDetail('${row.orderid}','${row.id}');" class="p-2" title="Observaciones"><i class="fa fa-search" aria-hidden="true"></i></a>  
      <a href="#" onclick="getNotasExamenDetail('${row.orderid}','${row.id}');" class="p-2" title="Notas"><i class="fas fa-clipboard" aria-hidden="true"></i></a>
      <a href="#" onclick="getDocumentos('${row.orderid}','${row.id}');"class="p-2" title="Documentos"><i class="far fa-file-alt"></i></a>
    </div>
  `;
}



function selectRowInfoExamenes(data) {
  localSetCurrentExamId(data.id);
  // loadResultadosExamenesOrden(data.orderid, data.id);
  $.getJSON("Microbiologia/api/getExamData", { orderId: data.orderid, examId: data.id })
    .done(function (exam) {
      showExamenes(exam);
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function showExamenes(exam) {
  showExamObservations(exam.observations);
  showExamEvents(exam.events);
  showExamNotes(exam.notes);
}



function getOpcTestExamenDetail(id,orderId,estado) {
  if(estado == 'P'){
    $("#opcTestExamenModal").modal("show");
    gOptionalTestContent.examOpcTest(id, orderId);
  }
  /*
  $("#ExamCanvasOptionalTests").tab('show');
  $("#optionalTestsContainer").empty();
  localSetCurrentExamId(id);
  $.getJSON("Microbiologia/api/getExamData", { orderId: orderId, examId: id })
    .done(function (exam) {
      let tests = exam.optionalTests
      tests.forEach(function (test) {
        $("#optionalTestsContainer").append(fillTemplate(optionalTestHtmlTemplate, test));
        $("#optionalTest-" + test.id).prop("checked", test.selected);
        optionalTestDisplay(test.id);
      });
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });*/
}


function zonaExamen(data, type, row, meta) {
  let valor = "";
  if(data !== undefined && data != null){
    valor = data.substring(0, 9)
  }else if(data == "-"){
    valor = "";
  }
  return `<span title="${data}">${valor}</span>`
}

function estadoExamen(data, type, row, meta) {
  const color = estadoTestYResultadosColorMap[row.processstatus];
  console.log('¿estado badge',row);

  const estado = row.processstatus;
  if (estado !== "PENDIENTE" && estado !== "BLOQUEADO") {
    return `<span class="label label-lg font-weight-bold label-inline  estadoBadge-${color}" >${row.processstatus}</span>`;
    //Cuando la muestra está rechazada o está tomada y recepcionada
  } else {
    return cargarEstadosExamen(estado, row);
  }
}

function fechaExamen(data, type, row, meta) {
  if (data != "" && data != "-") {
    return nuevoFormato(data);
    // nuevoFormato(data);
    //Cuando la muestra está rechazada o está tomada y recepcionada
  } else {
    return "";
  }
}

function cargarEstadosExamen(estado, row) {
  const color = estadoExamenesColorMap[estado];
  let select = `<select id="selectEstadoTestResultados${row.id}" class="form-control select-estado-examen estadoBadge-${color} select-color" >`;
  let estados = ["PENDIENTE", "BLOQUEADO"];

  estados.forEach((element) => {
    if (element === estado) {
      select += "<option value=" + element + " selected>" + element + "</option>";
    } else {
      select += "<option value=" + element + ">" + element + "</option>";
    }
  });

  select += '</select>';
  return select;
}

function getObsExamenDetail(idOrden, idExamen) {
  $("#obsExamenModal").modal('show');
  examObs(idExamen, idOrden);
}

function examObs(id, orderId) {
  localSetCurrentExamId(id);
  $.getJSON("Microbiologia/api/getExamData", { orderId: orderId, examId: id })
    .done(function(exam) {
      $('#ExamCanvasObservations').addClass('show active');
      showExamObservations(exam.observations);
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}



function getNotasExamenDetail(idOrden, idExamen) {
  $("#notasExamenModal").modal('show');
  examNotes(idExamen, idOrden);
  localStorage.setItem("idExamen", idExamen);
}

function examNotes(id, orderId) {
  localSetCurrentExamId(id);
  $.getJSON("Microbiologia/api/getExamData", { orderId: orderId, examId: id })
    .done(function(exam) {
      $('#ExamCanvasNotes').addClass('show active');
      showExamNotes(exam.notes);

    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}


// autorizar resultados
$( "#btnAutorizaResultados" ).on( "click", function() {
  const examenes = obtenerExamenesSeleccionadosExamenes(tableInfoExamenesOrden);
  console.log('examenmes',examenes);
  if (examenes.length === 0) {
    handlerMessageError("Debe escoger al menos un examen");
    return;
  }else{
    fillModalExamenesAutorizacion(examenes, $("#txtNombrePaciente").text());
  }
});




$("#examModalNotesButton").click(function() {
  $.ajax({
    url: "Microbiologia/api/putExamNotes",
    data: JSON.stringify({
      orderId: nroOrdenSeleccionada,
      examId: localStorage.getItem("idExamen"),
      internalNote: $("#idExamCanvasNotesInternalNote").val(),
      examNote: $("#idExamCanvasNotesExamNote").val(),
      footerNote: $("#idExamCanvasNotesFooterNote").val()
    }),
    headers: {
      'Content-Type': 'application/json'
    },
    method: 'put',
    dataType: 'json',
    success: function(exam) {
      handlerMessageOk('Se actualizaron notas. ');
    },
    error: function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });
})

$('#examenesOrdenesDatatable').on( 'change', '.select-estado-examen', function () {
  let table = tableInfoExamenesOrden;
  let row = table.row($(this).closest('tr'));
  let data = row.data();
  data.processstatus = $(this).val();
  let url =""
  saveEstadoExamen(row,data)
});

$('#examenesOrdenesDatatable').on( 'change', '.urgenci-examen', function () {
  let table = tableInfoExamenesOrden;
  let row = table.row($(this).closest('tr'));
  let data = row.data();
  if($(this).is(':checked')){
    data.urgency = "S";
  }else{
    data.urgency = "N";
  }
  let url =""
  saveEstadoExamen(row,data)
});


function saveEstadoExamen(tr,data){
  let d = {
    "processstatus": data.processstatus,
    "estado": data.estado,
    "orderid": data.orderid,
    "bodypartid":data.bodypartid,
    "sample": data.sample,
    "cultivo": data.cultivo,
    "exam": data.exam,
    "processstatus2": data.processstatus2,
    "reseedingdate": data.reseedingdate,
    "urgency": data.urgency,
    "seedingdate": data.seedingdate,
    "id": data.id,
    "bodyPart": data.bodyPart,
    "status": data.status,
    "codigo" : data.codigo
  }


  $.ajax({
    url: `${gCte.getctx}/api/microbiologia/updateExam`,
    method: 'POST',
    data:JSON.stringify(d),
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    success: function(datos) {
      if(datos.status !== undefined && datos.status == 200){
        /*let dnew = {
          "processstatus": datos.dato.processstatus,
          "estado": d.estado,
          "orderid": datos.dato.orderid,
          "bodypartid":datos.dato.bodypartid,
          "sample": datos.dato.sample,
          "cultivo": datos.dato.cultivo,
          "exam": datos.dato.exam,
          "processstatus2": datos.dato.processstatus2,
          "reseedingdate": datos.dato.reseedingdate,
          "urgency": datos.dato.urgency,
          "seedingdate": datos.dato.seedingdate,
          "id": datos.dato.id,
          "bodyPart": datos.dato.bodyPart,
          "status": datos.dato.status
        }*/

         //let table = tableInfoExamenesOrden;
      //let row = table.row(tr);
      //let data = row.data();
      //row.data(dnew);
      //table.draw(false);
                handlerMessageOk("Estado de examen actualizado correctamente");
        tableInfoExamenesOrden.ajax.reload(null, false);
      } else {
                handlerMessageError(datos.message);
      }
      console.log("datos",datos)
     
    }
  });
}

$('#examenesOrdenesDatatable').on('change', '.chck-examen', function (e) {
    e.preventDefault();
    if (!$(this).is("checked")){
        $("#seleccionaTodasLosExamenes").prop("checked", false);
    }
    loadResultExameSeleccOrEmpty();
  });

/*
$('#seleccionaTodasLosExamenes').on('change', function (e) {
    e.preventDefault();
    loadResultExameSeleccOrEmpty();
  });*/


  let loadResultExameSeleccOrEmpty = function(nroOrden, key = false) {
	
    let url =  `${gCte.getctx }/api/microbiologia/${nroOrdenSeleccionadaTest}/examenes/getTestxExamen`;
   // $("#accordionPanelExamenes").collapse('hide')
    pLstExamenesSeleccionados = obtenerExamenesSeleccionadosOrEmpty(tableInfoExamenesOrden);
    paramResultadosExamenesSeleccionados.examenes = pLstExamenesSeleccionados;
    console.log("pLstExamenesSeleccionados",pLstExamenesSeleccionados);
    console.log(paramResultadosExamenesSeleccionados);
    /*
    if (key == 13) {
      tableResultadosExamenesOrden.on('draw', function() {
        let $table = $('#ordenesDatatable');
        let $table2 = $('#resultadosExamenesOrdenesDatatable');
        if (!$table.is(":visible")) {
          autoMoveTable($table2, rowSeleccionado);
        }
      });
    }*/
    if (paramResultadosExamenesSeleccionados.examenes == false) { return; }
    
  //  paramResultadosExamenesSeleccionados.paciente.fnac = gFechaNacimientoPaciente;
   // paramResultadosExamenesSeleccionados.paciente.sexo = gSexoPaciente;
    //tableResultadosExamenesOrden.ajax.load(null, false).draw('page');
    tableResultadosExamenesOrden.ajax.url(url).load(null, false).draw('page');
   // $("#accordionTestResultados").collapse('show');
  
  }


  function obtenerExamenesSeleccionadosOrEmpty(pTableInfoExamenesOrden) {

    function tuplaExamen() {
      this.df_NORDEN = -1;
      this.dfe_IDEXAMEN = -1;
    }
    let inputs = $("input[data-colselector]");
    let examenesSeleccionados = new Array();
  
    const nInputs = inputs.length;
  
    for (let i = 0; i < nInputs; i++) {
      if (inputs[i].checked) {
        examenesSeleccionados.push(pTableInfoExamenesOrden.row(inputs[i].closest("tr")).data());
      }
    }
  
    if (examenesSeleccionados === undefined || examenesSeleccionados === null) {
      handlerMessageError('Revisar selección de mensajes.');
      return false;
    }
    let n = examenesSeleccionados.length;
    if (n === 0) {
      for (let i = 0; i < nInputs; i++) {
        examenesSeleccionados.push(pTableInfoExamenesOrden.row(inputs[i].closest("tr")).data());
      }
      n = examenesSeleccionados.length;
    }
  
    console.log("examenesSeleccionados 1",examenesSeleccionados)
    let idSeleccionados = new Array();
    for (let i = 0; i < n; i++) {
      let tupla = new tuplaExamen();
      tupla.df_NORDEN = examenesSeleccionados[i].orderid;
      tupla.dfe_IDEXAMEN = examenesSeleccionados[i].id;
     // tupla.dfe_CODIGOESTADOEXAMEN = examenesSeleccionados[i].dfe_CODIGOESTADOEXAMEN;
    //  tupla.ce_ABREVIADO = examenesSeleccionados[i].exam;	
	// tupla.ce_CODIGOEXAMEN = examenesSeleccionados[i].ce_CODIGOEXAMEN;
     // tupla.cee_DESCRIPCIONESTADOEXAMEN = examenesSeleccionados[i].cee_DESCRIPCIONESTADOEXAMEN;
  
      idSeleccionados.push(tupla);
    }
    return idSeleccionados;
  }
  
  function getDocumentos(idOrden, idExamen) {
  $("#testDocModal").modal('show');
        modalUploadGetDocumentosExamenesOrden(idOrden,idExamen);
  }

//Funciones de tests opcionales agregadas por Marco Caracciolo 04/04/2023
function guardarTestOpcionales() {
  gOptionalTestContent.guardarTestOpcionales();
}


function exito(result, status, xhr) {
console.log("entre a exito ******-----------------")
console.log(result)
  let respuesta = result;

  if (respuesta.status == 200) {
    tableResultadosExamenesOrden.ajax.reload();
    handlerMessageOk(respuesta.message);
  }
  else if (respuesta.status == 503) {

    tableResultadosExamenesOrden.ajax.reload();
    handlerMessageWarning(respuesta.message);

  }
  else {
    handlerMessageError(respuesta.message);
  }

}

function fracaso(xhr, status, error) {

  console.table(error);
  handlerMessageError(error);
}


function getTestSelected() {

  let tests = document.getElementsByName("exopcional");
  let testsSeleccionados = new Array();
  tests.forEach((n) => {
    if (n.checked) { let testId = n.dataset.testid; testsSeleccionados.push(testId); }
  }
  );
  return testsSeleccionados;
}
/*
function searchExamenSeccion(seccionValue) {
  let columna = $('#examenesOrdenesDatatable').DataTable().column(12);
  columna.search(seccionValue, true, false, false).draw();
}
*/