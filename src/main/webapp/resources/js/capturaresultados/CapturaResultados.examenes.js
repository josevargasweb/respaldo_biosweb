var estadoExamenesColorMap = new Array();
estadoExamenesColorMap['FIRMADO'] = 'firmado';
estadoExamenesColorMap['PENDIENTE'] = 'pendiente';
estadoExamenesColorMap['INGRESADO'] = 'ingresado';
estadoExamenesColorMap['EN PROCESO'] = 'proceso';
estadoExamenesColorMap['BLOQUEADO'] = 'bloqueado';
estadoExamenesColorMap['AUTORIZADO'] = 'autorizado';

var gOptionalTestContent = new OptionalTestContent();
var tableInfoExamenesOrden;

//estado botones
$("#btnAutorizaResultados").prop('disabled', true);
$("#examOptionalTestsButton").prop('disabled', true);
$("#examModalNotesButton").prop('disabled', true);
$("#btnImprimirCR").addClass('disabled')
$("#btnContadorCelulas").addClass('disabled')
$("#examOptionalTestsButton").addClass('disabled')





function obtenerExamenesSeleccionados(pTableInfoExamenesOrden) {

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
  const n = examenesSeleccionados.length;
  if (n === 0) {
    handlerMessageWarning('Seleccionar al menos un examen');
    return false;
  }
  let idSeleccionados = new Array();
  for (let i = 0; i < n; i++) {
    let tupla = new tuplaExamen();
    tupla.df_NORDEN = examenesSeleccionados[i].dfe_NORDEN;
    tupla.dfe_IDEXAMEN = examenesSeleccionados[i].dfe_IDEXAMEN;
    tupla.dfe_CODIGOESTADOEXAMEN = examenesSeleccionados[i].dfe_CODIGOESTADOEXAMEN;
    tupla.ce_ABREVIADO = examenesSeleccionados[i].ce_ABREVIADO;
    tupla.ce_CODIGOEXAMEN = examenesSeleccionados[i].ce_CODIGOEXAMEN;
    tupla.cee_DESCRIPCIONESTADOEXAMEN = examenesSeleccionados[i].cee_DESCRIPCIONESTADOEXAMEN;

    idSeleccionados.push(tupla);
  }
  return idSeleccionados;
}


  


function generarQryString(examenesSeleccionados) {
  let lstIdExamenes = 'pLstExId=';
  let n = examenesSeleccionados.length;
  for (let i = 0; i < n; i++) {
    lstIdExamenes += examenesSeleccionados[i].idExamen + ',';
  }
  if (examenesSeleccionados.length > 0) {
    lstIdExamenes = lstIdExamenes.substr(0, lstIdExamenes.length - 1);
  }
  return lstIdExamenes;
}

function cargarResultadosExamenesTabla(pNroOrden, key = false) {
  loadResultadosExamenesSeleccionadosOrden(pNroOrden, key);
}

function getExamenesOrden(filtro) {
  try {
    datosInfoExamenesOrden = filtro;
    tableInfoExamenesOrden.ajax.reload(); // con POST
  } catch (e) {
    handlerMessageError(e);
  }
}

var getParametros = function(d) {
  return JSON.stringify(d);
}

function genEstado(data, type, row, meta) {
  const color = estadoExamenesColorMap[row.cee_DESCRIPCIONESTADOEXAMEN];
  const estado = row.cee_DESCRIPCIONESTADOEXAMEN;
  if (estado !== "PENDIENTE" && estado !== "BLOQUEADO") {
    //Cuando la muestra está rechazada o está tomada y recepcionada
    return `<div style="width:${t1 * 9}px"><span class="label label-lg font-weight-bold label-inline estadoBadge-${color}" >${row.cee_DESCRIPCIONESTADOEXAMEN}</span></div>`;
  } else {
    return cargarEstadosExamen(estado, row['dfe_IDEXAMEN']);
  }
}

function genDescripcion(data, type, row, meta) {
  if (row.dfe_EXAMENANULADO === "S") {
    return `<div style="width:${t1 * 14}px"><s>${largoTexto2(row.ce_ABREVIADO != null ? row.ce_ABREVIADO : "", 20)}</s></div>`;
  } else {
    return `<div style="width:${t1 * 14}px">${largoTexto2(row.ce_ABREVIADO != null ? row.ce_ABREVIADO : "", 20)}</div>`;
  }
}

function genAccion(data, type, row, meta) {
  const testOpcionalesEnabled = row.ce_TIENETESTOPCIONALES === "S" ? false : true;
  const observacionEnabled = row.estadoobservacion === "S" ? false : true;
  // const notasEnabled = row.estadonota === "S" ? false : true;

  return `
  <div class="row btn-toolbar" style="width:'+ t1 * 9 + 'px">
    <a href="#"  onclick="getOpcTestExamenDetail('${row.dfe_NORDEN}','${row.dfe_IDEXAMEN}', '${row.dfe_CODIGOESTADOEXAMEN}')" class="p-2 ${testOpcionalesEnabled ? 'disabled' : ''}" title="Tests Opcionales">
                        <i class="fas fa-vials" ${testOpcionalesEnabled ? 'style="color:#858383 !important;"' : ''} ></i>
                    </a>
    <a href="#" onclick="getObsExamenDetail('${row.dfe_NORDEN}','${row.dfe_IDEXAMEN}');"class="p-2 ${observacionEnabled ? 'disabled' : ''}" title="Observaciones">
      <i class="fa fa-search" ${observacionEnabled ? 'style="color:#858383 !important;"' : ''}></i>
    </a>
    <a href="#" onclick="getNotasExamenDetail('${row.dfe_NORDEN}','${row.dfe_IDEXAMEN}');"class="p-2" title="Notas">
      <i class="fas fa-clipboard"></i>
    </a>
    <a href="#" onclick="getDocumentos('${row.dfe_NORDEN}','${row.dfe_IDEXAMEN}');"class="p-2" title="Documentos">
      <i class="far fa-file-alt"></i>
    </a>
</div>`

}

function cargarEstadosExamen(estado, idExamen) {
  const color = estadoExamenesColorMap[estado];
  let select = `<select id="selectEstadoexamenm${idExamen}" class="form-control select-color estadoBadge-${color}" onchange="" style="width:${t1 * 9}px">`;
  let estados = ["PENDIENTE", "BLOQUEADA"];

  estados.forEach((element) => {
    if (element == estado) {
      select += "<option value=" + element + " selected>" + element + "</option>";
    } else {
      select += "<option value=" + element + ">" + element + "</option>";
    }
  });

  select += '</select>';
  return select;
}

var initTableInfoExamenesOrden = function(jqDtId) {
  nroOrden = -1;
  tableInfoExamenesOrden = $(jqDtId).DataTable({
    ajax: {
      "url": gCte.getctx + "/api/orden/" + nroOrden + "/examenes/muestras",
      "type": "GET",
      "dataSrc": function(response) {
        let resultado = response.dato;

        if (resultado != null) {

          resultado.sort((a, b) => {
            if (a.dfe_EXAMENESURGENTE === "S" && b.dfe_EXAMENESURGENTE !== "S") {
              return -1;
            } else if (a.dfe_EXAMENESURGENTE !== "S" && b.dfe_EXAMENESURGENTE === "S") {
              return 1;
            } else {
              return quitarTildes(a.ce_ABREVIADO).localeCompare(quitarTildes(b.ce_ABREVIADO));
            }
          });

        }

        return resultado;
      },
    },
    serverSide: true, 
    "language": {
      "thousands": ".",
      "decimal": ",",
      "emptyTable": "No hay datos disponibles",
      "loadingRecords": "Por favor espere - cargando ...",
      "zeroRecords": "No se encontraron datos",
    },
    // scroller: true,
    "info": false,
    // scrollY: '200px',
    // scrollX: false,
    // scrollCollapse: false,
    // "paging": true,
    lengthChange: false,
    "pageLength": 1,
    // scrollY: '25',
    autoWidth: false,
    paging: false,
    searchDelay: 500,
    orderCellsTop: true,
    // "order": [[ 11, 'desc' ],[ 2, 'asc' ]],
    colReorder: true,
    scrollY: "100px",
    scrollX: true,
    rowId: function(a) {
      return a.dfe_IDEXAMEN.toString()
    },
    // scrollCollapse: true,
    // searching: true,
    // autoWidth: false,
    columnDefs: [
      { orderable: false, targets: '_all' },
      {
        targets: [0],
        width: '30px',
        className: 'dt-left',
        render: function(data, type, full, meta) {
          // console.log('examen', data, type, full, meta)
          //   var cell = table.cell(1, 2);
          // // Agregar el atributo "data-my-attribute" con valor "myValue"
          // $(cell.node()).attr('data-my-attribute', 'myValue');
          return `<input type="checkbox" value=""  data-colselector='x' class="checkable chck-examen" checked style="width:${t1 * 3}px"/> `;
        },
      },

      {
        targets: [1],
        render: function(data, type, full, meta) {
          return `<div style="width:${t1 * 7}px">${data != null ? data : ""}</div>`;
        },
      },

      {
        targets: [2],
        render: genDescripcion,
      },
      {
        "targets": [3, 4, 5],
        "visible": false,
      },
      {
        targets: [6],
        data: null,
        className: 'notTMClick',
        defaultContent: '',
        render: genEstado,
      },
      {
        targets: 7,
        width: '30px',
        className: 'urg',
        orderable: true,
        render: function(data, type, full, meta) {
          let checked = '';
          if (data === 'S') {
            checked = 'checked';
          }
          return `<label class="checkbox checkbox-single" style="width:${t1 * 3}px">
                              <input type="checkbox" ${checked} value="${data}" class="checkable"/>
                              <span></span>
                          </label>`;
        }
      },
      {
        targets: [8],
        data: null,
        className: 'notTMClick',
        defaultContent: '',
        render: genAccion,
      },
      {
        targets: [9, 10],
        visible: false,
      },
      {
        targets: [11],
        visible: false,
      },
    ],
    select: {
      style: 'multi',
      selector: 'td:not(:last-child)',
      items: 'cell',
      "style": 'multiple',
      "className": 'selected',
    },
    columns: [
      { "": "" },
      { "data": "ce_CODIGOEXAMEN" },
      { "data": "ce_ABREVIADO" },
      { "data": "cenv_DESCRIPCION" },
      { "data": "csec_DESCRIPCION" },
      { "data": "dfm_CODIGOBARRA" },
      { "data": "cee_DESCRIPCIONESTADOEXAMEN" },
      { "data": "dfe_EXAMENESURGENTE" },
      { "": "" },
      { "data": "ce_IDSECCION" },
      { "data": "dfe_EXAMENANULADO" },
      { "data": "dfe_EXAMENESURGENTE" },
    ],

    "initComplete": function(settings, json) {
      $("#examenesOrdenesDatatable_filter").hide();
      let rowFilter = $('<tr id="filterExamen" class="filterExamen"></tr>').appendTo($(tableInfoExamenesOrden.table().header()));
      this.api().columns().every(function() {
        let columna = this;
        genColFilterExamen(columna.index(), rowFilter);
      });
      let idSeccion = $("#idSeccion").val()
      gSeccionSeleccionada = idSeccion.concat("$");
      searchExamenesAnulados(gSeccionSeleccionada);
      let count = tableInfoExamenesOrden.rows({ search: 'applied' }).length;
      //  alert(count);

      if (count === 0) {
        tableInfoExamenesOrden.clear().draw();
      }
    }

  });




  //  tableInfoExamenesOrden.on('select', 'td:not(.notTMClick)', function(e, dt, type, indexes) {
  tableInfoExamenesOrden.on('select', function(e, dt, type, indexes) {
    if (type === 'cell') {
      if (indexes[0].column === 7) {
        doAction('getEventUrgenciaDetail', indexes);
      }
      else {
        console.table(dt);
      }
    }
  });

  tableInfoExamenesOrden.on('xhr', function(e, settings, json, xhr) {
    if (json !== null && json !== undefined) {
      setSelect(json, 'ce_ABREVIADO', 1);
      setSelect(json, 'cmue_DESCRIPCIONABREVIADA', 2);
      setSelect(json, 'cenv_DESCRIPCION', 3);

    }
  });

  $("#seleccionaTodasLosExamenes").on("click", function(e) {
    let inputs = $("input[data-colselector]");

    if ($(this).is(":checked")) {
      $(inputs).prop('checked', true);
      tableInfoExamenesOrden.rows().select();
    } else {
      $(inputs).prop('checked', false);
      tableInfoExamenesOrden.rows().deselect();
      $("#btnAutorizaResultados").prop('disabled', true);
    }

    const examenes = getSelectedExams(tableInfoExamenesOrden);
    if(examenes){
      let existeElemento = examenes.some(function(elemento) {
        return elemento.cee_DESCRIPCIONESTADOEXAMEN !== "FIRMADO" && elemento.cee_DESCRIPCIONESTADOEXAMEN !== "AUTORIZADO";
      })
      
      if(existeElemento){
        $("#btnAutorizaResultados").prop('disabled', true)
      }else{
        $("#btnAutorizaResultados").prop('disabled', false)
      }
    }


  });

  tableInfoExamenesOrden.on('draw', function() {
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

    const datos = tableInfoExamenesOrden.rows().data();
    let existeAutorizado = false;
    datos.each(function(elemento) {
      if(elemento.cee_DESCRIPCIONESTADOEXAMEN === "AUTORIZADO"){
        existeAutorizado = true
      }
    })
    // let existeAutorizado = datos.some(function(elemento) {
    //   return elemento.cee_DESCRIPCIONESTADOEXAMEN === "AUTORIZADO";
    // })

    if(existeAutorizado){
      $("#btnImprimirCR").removeClass('disabled')
    }else{
      $("#btnImprimirCR").addClass('disabled')
    }


    const examenes = getSelectedExams(tableInfoExamenesOrden);
    if(examenes){
      let existeElemento = examenes.some(function(elemento) {
        return elemento.cee_DESCRIPCIONESTADOEXAMEN !== "FIRMADO" && elemento.cee_DESCRIPCIONESTADOEXAMEN !== "AUTORIZADO";
    })
      if(existeElemento){
        $("#btnAutorizaResultados").prop('disabled', true)
      }else{
        $("#btnAutorizaResultados").prop('disabled', false)
      }
    }

    if (tableInfoExamenesOrden.rows().count() > 0) {
      $('#seleccionaTodasLosExamenes').attr('checked', true);
    } else {
      $('#seleccionaTodasLosExamenes').attr('checked', false);
      $("#btnAutorizaResultados").prop('disabled', true);
    }


    let existeContador = false;
    datos.each(function(elemento) {
      if(elemento.estadocontador === "S"){
        existeContador = true
      }
    })

    if(existeContador){
      $("#btnContadorCelulas").removeClass('disabled')
    }else{
      $("#btnContadorCelulas").addClass('disabled')
    }
  });

  tableInfoExamenesOrden.on("change", 'input[data-colselector="x"]', function (e) {
    e.preventDefault();
 // // let isChecked = $(this).is(":checked");
    // let isAnyChecked = $('input[data-colselector="x"]:checked').length > 0;
    // let checkeado = false;
    // if (isChecked || isAnyChecked) {
    //   checkeado = true
    // }
    
    // if(checkeado){
    //   $("#btnAutorizaResultados").prop('disabled', false)
    // }else{
    //   $("#btnAutorizaResultados").prop('disabled', true)
    // }

    const examenes = getSelectedExams(tableInfoExamenesOrden);
    if(examenes){
      let existeElemento = examenes.some(function(elemento) {
        return elemento.cee_DESCRIPCIONESTADOEXAMEN !== "FIRMADO" && elemento.cee_DESCRIPCIONESTADOEXAMEN !== "AUTORIZADO";
    })
      
      if(existeElemento){
        $("#btnAutorizaResultados").prop('disabled', true)
      }else{
        $("#btnAutorizaResultados").prop('disabled', false)
      }
    }else{
      $("#btnAutorizaResultados").prop('disabled', true)
    }

})

}

function getObsExamenDetail(idOrden, idExamen) {
  $("#obsExamenModal").modal('show');
  examObs(idExamen, idOrden);
}
let idExamenNota;
let idOrderNota;
function getNotasExamenDetail(idOrden, idExamen) {
  $("#examModalNotesButton").prop('disabled',true)
  idExamenNota = idExamen;
  idOrderNota = idOrden;
  $("#notasExamenModal").modal('show');
  notasExamenes(idOrden,idExamen)
  $('#ExamCanvasNotes').addClass('show active');
	
  //localStorage.setItem("idExamen", idExamen);
}
function notasExamenes(idOrden,idExamen){
  $.ajax({
    url: gCte.getctx +`/api/${idOrden}/${idExamen}/getNotasExamen`,
    headers: {
      'Content-Type': 'application/json'
    },
    method: 'get',
    dataType: 'json',
    success: function(data) {
	  if(data.status == 200){
      	$('#idExamCanvasNotesInternalNote').val(data.dato.notainterna);
      	$('#idExamCanvasNotesExamNote').val(data.dato.notaexamen);
      	$('#idExamCanvasNotesFooterNote').val(data.dato.notapie);
      	}
	  if(data.status == 505)
      	handlerMessageOk('Error ' + data.message);
    },
    error: function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });
}
function getEventExamenDetail(idOrden, idExamen) {
  $("#eventosExamenModal").modal('show');
  examEvents(idExamen, idOrden);
}

function setUrgenciaDetail(indexes) {
  var data = tableInfoExamenesOrden.rows(indexes[0].row).data();
  updateInfoExamenesOrden(data[0].dfe_NORDEN, data[0].dfe_IDEXAMEN, data[0].dfe_EXAMENESURGENTE === 'N' ? 'S' : 'N');
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

function examEvents(id, orderId) {
  localSetCurrentExamId(id);
  $.getJSON("Microbiologia/api/getExamData", { orderId: orderId, examId: id })
    .done(function(exam) {
      $('#ExamCanvasEvents').addClass('show active');
      showExamEvents(exam.events);
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function examNotes(id, orderId) {
  
  //showExamNotes();

}


var loadInfoExamenesOrden = function(nroOrden, key = false) {
  let url = gCte.getctx + "/api/orden/" + nroOrden + "/examenes/muestras";
  let setExamenesSeleccionados = genSetExamenSeleccionado(nroOrden, key);
  //  tableInfoExamenesOrden.ajax.url(url).load(setExamenesSeleccionados);
  tableInfoExamenesOrden.ajax.url(url).load(searchExamenesAnulados);

  let pLstExamenesSeleccionados = [];//obtenerExamenesDeTablaDeExamenes(tableInfoExamenesOrden);
  paramResultadosExamenesSeleccionados.examenes = pLstExamenesSeleccionados;
  paramResultadosExamenesSeleccionados.paciente.fnac = gFechaNacimientoPaciente;
  paramResultadosExamenesSeleccionados.paciente.sexo = gSexoPaciente;

  url = gCte.getctx + "/api/orden/" + nroOrden + "/examenes/resultados";
  tableResultadosExamenesOrden.ajax.url(url).load(searchResultadosAnulados);
  //    setExamenesSeleccionados();

}

function disable(id) {
  $(id).val("");
}

function disableSel(id) {
  $(id).val("-1");
}

function setDisablerQueue(enablerQueue) {

  enablerQueue.listen("#txtFInicio", disable);
  enablerQueue.listen("#txtFFin", disable);
  enablerQueue.listen("#txtFInicio", disable);
  enablerQueue.listen("#txtFiltroNombre", disable);
  enablerQueue.listen("#txtFiltroApellido", disable);
  enablerQueue.listen("#selectTipoDocumentoFiltro", disable);
  enablerQueue.listen("#selectTipoAtencionFiltro", disable);
  enablerQueue.listen("#selectServicio", disable);
  enablerQueue.listen("#selectServicio", disable);
  enablerQueue.listen("#txtNDocumento", disable);
}

function enable(id) {
  $(id).prop("readonly", "");
}

function setEnablerQueue(enablerQueue) {
  enablerQueue.listen("#txtFInicio", enable);
  enablerQueue.listen("#txtFFin", enable);
  enablerQueue.listen("#txtFInicio", enable);
  enablerQueue.listen("#txtFiltroNombre", enable);
  enablerQueue.listen("#txtFiltroApellido", enable);
  enablerQueue.listen("#selectTipoDocumentoFiltro", enable);
  enablerQueue.listen("#selectTipoAtencionFiltro", enable);
  enablerQueue.listen("#selectServicio", enable);
  enablerQueue.listen("#txtNDocumento", enable);
}

function genTableColFilter(colIndex, rowFilter, table) {

  switch (colIndex) {

    case 0:
      genTableColCheckSelector(colIndex, rowFilter, table);
      break;
    case 7:
      genTableColCheckFilter(colIndex, rowFilter, table);
      break;
    case 1:
    case 2:
    case 3:
    case 4:
    case 6:
      genTableColSelectFilter(colIndex, rowFilter, table);
      break;
    case 5:
      genTableColInputFilter(colIndex, rowFilter, table);
      break;
    default:
      genNoFilter(colIndex, rowFilter);
  }
}

function genTableColInputFilter(colIndex, rowFilter, table) {
  let inputText = $(`<input type="text" class="form-control form-control-sm form-filter datatable-input" data-colexa-index="` + colIndex + `"/>`);
  $(inputText).appendTo($('<th>').appendTo(rowFilter));
  let searcher = searchTableKey(table);
  $(`input[data-colexa-index="` + colIndex + `"]`).on('keyup change clear', searcher);
}

function genTableColSelectFilter(colIndex, rowFilter, table) {
  let columnas = table.column(colIndex);
  console.log("columnas");
  console.table(columnas);
  let inputSelect = genTableInputSelect(colIndex);
  $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
  let searcher = searchTableKey(table);
  $(`select[data-colexa-index="` + colIndex + `"]`).change(searcher);
}

function genTableColCheckFilter(colIndex, rowFilter, table) {
  let inputSelect = $(`<label class="checkbox checkbox-single"> <input type="checkbox" class="form-control form-control-sm form-filter datatable-input" data-colexa-index="` + colIndex + `"/><span></span></label>`);
  $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
  let searcher = searchTableKey(table);
  $(`input[data-colexa-index="` + colIndex + `"]`).change(searcher);
}

function genTableColCheckSelector(colIndex, rowFilter, table) {
  let inputSelect = $(`<label class="checkbox checkbox-single"> <input type="checkbox" class="form-control form-control-sm form-filter datatable-input" data-colexa-index="` + colIndex + `"/><span></span></label>`);
  $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
  let selecter = selTableKey(table);
  $(`input[data-colexa-index="` + colIndex + `"]`).change(selecter);
}

function genTableInputSelect(colIndex) {
  return $(`<select class="form-control form-control-sm form-filter datatable-input" data-colexa-index="` + colIndex + `">` + setTableOptionSelCol(colIndex) + `</select>`);
}

function searchTableKey(table) {
  function searchTKey(e) {

    let columna = table.column(this.dataset["colexaIndex"]);
    columna.search(e.target.value, true, false, false).draw();
  }
  return searchTKey;
}

function selTableKey(table) {
  function selTKey(e) {
    //    let columna = table.column(this.dataset["colexaIndex"]);
    if (e.target.checked) {
      $('#resultadosExamenesOrdenesDatatable').find(':checkbox').attr('checked', true);
      tableResultadosExamenesOrden.rows().select();

    } else {
      $('#resultadosExamenesOrdenesDatatable').find(':checkbox').attr('checked', false);
      tableResultadosExamenesOrden.rows().deselect();
    }
  }
  return selTKey;
}

function setTableOptionSelCol(colIndex, table) {

  switch (colIndex) {
    case 1:
      return setOptionExamen(colIndex);
    case 2:
      return setOptionMuestra(colIndex);
    case 3:
      return setOptionEnvase(colIndex);
    case 4:
      return setOptionSeccion(colIndex);
    case 6:
      return setOptionEstadoExamen(colIndex);
    default:
      break;
  }
  return '<option value="">TODOS</option>';
}

function setOptionExamen(colIndex) {

  function fillSelect(jqIdSelect) {
    function fill(data) {

      data.forEach(exa => {
        let opt = new Option(exa.ceDescripcion, exa.ceDescripcion);
        $(jqIdSelect).append($(opt));
      });
      let opt = new Option('TODOS', '', true);
      $(jqIdSelect).append($(opt));
    }
    return fill;
  }

  let fillSelectExamen = fillSelect(`select[data-colexa-index="` + colIndex + `"]`)
  getCfgExamenes(servicioCfgExamenes, fillSelectExamen);
}

function setOptionMuestra(colIndex) {

  function fillSelect(jqIdSelect) {
    function fill(data) {

      data.forEach(muestra => {
        let opt = new Option(muestra.cmueDescripcion, muestra.cmueDescripcion);
        $(jqIdSelect).append($(opt));
      });
      let opt = new Option('TODOS', '', true, true);
      $(jqIdSelect).append($(opt));
    }
    return fill;
  }

  let fillSelectMuestra = fillSelect(`select[data-colexa-index="` + colIndex + `"]`)
  getMuestras(servicioCfgMuestras, fillSelectMuestra);
}


function setOptionEnvase(colIndex) {

  function fillSelect(jqIdSelect) {
    function fill(data) {

      data.forEach(envase => {
        let opt = new Option(envase.cenvDescripcion, envase.cenvDescripcion);
        $(jqIdSelect).append($(opt));
      });
      let opt = new Option('TODOS', '', true, true);
      $(jqIdSelect).append($(opt));
    }
    return fill;
  }

  let fillSelectEnvase = fillSelect(`select[data-colexa-index="` + colIndex + `"]`)
  getCfgEnvases(servicioCfgEnvases, fillSelectEnvase);
}

function setOptionSeccion(colIndex) {

  function fillSelect(jqIdSelect) {
    function fill(data) {

      data.forEach(sec => {
        let opt = new Option(sec.csecDescripcion, sec.csecDescripcion);
        $(jqIdSelect).append($(opt));
      });
      let opt = new Option('TODOS', '', true, true);
      $(jqIdSelect).append($(opt));
    }
    return fill;
  }

  let fillSelectSeccion = fillSelect(`select[data-colexa-index="` + colIndex + `"]`)
  getSeccionesCol(servicioSecciones, fillSelectSeccion);
}



function setOptionEstadoExamen(colIndex) {

  function fillSelect(jqIdSelect) {
    function fill(data) {

      data.forEach(estado => {
        let opt = new Option(estado.ceeDescripcionestadoexamen, estado.ceeDescripcionestadoexamen);
        $(jqIdSelect).append($(opt));
      });
      let opt = new Option('TODOS', '', true, true);
      $(jqIdSelect).append($(opt));
    }
    return fill;
  }

  let fillSelectEstadoExamen = fillSelect(`select[data-colexa-index="` + colIndex + `"]`)
  getEstadosExamen(servicioCfgEstadosExamenes, fillSelectEstadoExamen);
}

function updateInfoExamenesOrden(nroOrden, idExamen, esUrgente) {

  let url = gCte.getctx + '/api/examen/update/';
  let examen = {
    "iddatosFichasExamenes": {
      "dfeNorden": nroOrden,
      "dfeIdexamen": idExamen
    },
    "dfeExamenesurgente": esUrgente
  };

  let postData = JSON.stringify(examen);

  $.post({
    type: "POST",
    url: url,
    data: postData,
    contentType: 'application/json; charset=utf-8',
    success: function(data) {
      let status = data.status;
      if (status === 200) {
        tableInfoExamenesOrden.ajax.reload();
        // handleMessageOk(data.message);
      }
      else {
        handleMessageError(data.message);
      }
    },

  }
  );
  //  .fail('Error en actualizacion. Http Error');
}

$("#examModalNotesButton").click(function() {

  $.ajax({
    url: gCte.getctx +"/api/PUTNotasExamen",
    data: JSON.stringify({
      orderId: idOrderNota,
      examId: idExamenNota,
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
	  if(exam.status == 200)
      	handlerMessageOk('Se actualizaron notas. ');
	  if(exam.status == 505)
      	handlerMessageOk('Error al ingresar notas.');
    },
    error: function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });
})


function genMuestrasDisponibles(jsonData) {

  return genOpcionesDisponibles(jsonData.dato, 'cenv_DESCRIPCION');
}

function genOpcionesDisponibles(datos, fieldName) {
  let n = datos.length;
  let muestrasSet = new Set();
  for (let i = 0; i < n; i++) {
    let lst = getListaValores(datos[i], fieldName);
    let m = lst.length;
    for (let j = 0; j < m; j++) {
      muestrasSet.add(lst[j]);
    }
  }
  return muestrasSet;
}


function parseBR(examen_i) {
  let lst = examen_i.cenv_DESCRIPCION.split("<BR\/>");
  lst.splice(lst.length - 1, 1);

  console.table(lst);
  return lst;
}

function getListaValores(examen_i, field) {
  let lst = examen_i[field].split("<BR\/>");
  if (lst.length > 1) {
    lst.splice(lst.length - 1, 1);
  }
  console.table(lst);
  return lst;
}



function setSelect(json, fieldName, index) {
  if (json !== null && json !== undefined) {
    if ($("select[data-colexa-index=" + index + "]").length !== undefined && $("select[data-colexa-index=" + index + "]").length > 0) {
      let n = $("select[data-colexa-index=" + index + "]")[0].options.length;
      for (let i = 0; i < n; i++) {
        $("select[data-colexa-index=" + index + "]")[0].remove(n - i - 1);
      }
      let muestras = genOpcionesDisponibles(json.dato, fieldName);
      let opc = new Option('TODOS', 'TODOS');
      $("select[data-colexa-index=" + index + "]")[0].append(opc);
      muestras.forEach(m => {
        console.log('Muestra: ' + m); opc = new Option(m, m); $("select[data-colexa-index=" + index + "]")[0].append(opc);
      });
    }
  }
}

function genSetExamenSeleccionado(nroOrden, key = false) {

  return function setExamenesSeleccionados() {
    let inputs = $("input[data-colselector]");
    const nInputs = inputs.length;
    if (nInputs === 0) {
      return;
    }
    for (let i = 0; i < nInputs; i++) {
      inputs[i].checked = true;
    }
    $("#seleccionaTodasLosExamenes").prop('checked', true);
    cargarResultadosExamenesTabla(nroOrden, key);

  }
}


function autorizarExamenes() {
  //Permiso de autorizar exámenes añadido por Marco Caracciolo 05/12/2022
  if ($("#autorizaExamenes").length && $("#autorizaExamenes").val() == "S") {
    const examenes = obtenerExamenesSeleccionados(tableInfoExamenesOrden);
    if (examenes.length === 0) {
      handlerMessageError("Debe escoger al menos un resultado");
      return;
    }

    fillModalExamenesAutorizacion(examenes, $("#txtNombrePaciente").text());
    //  actionTests("AUTORIZAR",examenes);
  } else {
    handlerMessageError("Usuario no está autorizado para autorizar exámenes.")
  }
}


function actionExamenes(action, examenesSeleccionados) {

  let url = gCte.getctx + `/api/examenes/action/${action}`;
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(examenesSeleccionado),
    success: exito,
    error: handlerMessageError,
    contentType: 'application/json; charset=utf-8'
  });
}



function guardarTestOpcionales() {
	console.log("entre a buscar testopcionales")
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

function getExamenSelected() {

  let tests = document.getElementsByName("exopcional");
  let examenId = -1;
  tests.forEach((n) => {
    if (n.checked) {
      examenId = parseInt(n.dataset.examenid);
    }
  });
  return examenId;
}

function searchExamenSeccion(seccionValue) {
  let columna = tableInfoExamenesOrden.column(4);
  columna.search(seccionValue, true, false, false).draw();
  //  searchExamenSeccion(val);
}

function limpiarTableInfoExamenesOrden() {

  tableInfoExamenesOrden.clear();
  tableInfoExamenesOrden.draw();
}


// Examens opcionales 
function getOpcTestExamenDetail(idOrden, idExamen, estado) {

  if (estado == 'P') {
    $("#opcTestExamenModal").modal('show');
    gOptionalTestContent.examOpcTest(idExamen, idOrden);
  }
}

// Documentos de examenes  
function getDocumentos(idOrden, idExamen) {
  $("#testDocModal").modal('show');
  modalUploadGetDocumentosExamenesOrden(idOrden, idExamen);
}

function searchExamenesAnulados() {

  let val = 'N|F';
  if (tableInfoExamenesOrden !== null && tableInfoExamenesOrden !== undefined) {
    let columna = tableInfoExamenesOrden.column(10);
    columna.search(val, true, false, false).draw();
    resetOrdenProgress();
    searchExamenesSeccion(gSeccionSeleccionada);
  }
}

function searchExamenesSeccion(seccion) {
  let columna = tableInfoExamenesOrden.column(9);
  columna.search(seccion, true, false, false).draw();
  resetOrdenProgress();
}

function obtenerExamenesDeTablaDeExamenes(pTableInfoExamenesOrden) {

  let inputs = $("input[data-colselector]");
  let examenesSeleccionados = new Array();

  const nInputs = inputs.length;

  for (let i = 0; i < nInputs; i++) {
    examenesSeleccionados.push(pTableInfoExamenesOrden.row(i).data());
  }

  if (examenesSeleccionados === undefined || examenesSeleccionados === null) {
    handlerMessageError('Revisar selección de mensajes.');
    return false;
  }
  const n = examenesSeleccionados.length;
  if (n === 0) {
    handlerMessageWarning('Seleccionar al menos un examen');
    return false;
  }
  let idSeleccionados = new Array();
  for (let i = 0; i < n; i++) {
    let tupla = new Object();
    tupla.df_NORDEN = examenesSeleccionados[i].dfe_NORDEN;
    tupla.dfe_IDEXAMEN = examenesSeleccionados[i].dfe_IDEXAMEN;
    tupla.dfe_CODIGOESTADOEXAMEN = examenesSeleccionados[i].dfe_CODIGOESTADOEXAMEN;
    tupla.ce_ABREVIADO = examenesSeleccionados[i].ce_ABREVIADO;
    tupla.ce_CODIGOEXAMEN = examenesSeleccionados[i].ce_CODIGOEXAMEN;
    tupla.cee_DESCRIPCIONESTADOEXAMEN = examenesSeleccionados[i].cee_DESCRIPCIONESTADOEXAMEN;
    idSeleccionados.push(tupla);
  }
  return idSeleccionados;
}

$('#examenesOrdenesDatatable').on('change', '.chck-examen', function(e) {
  e.preventDefault();
  loadResultExameSeleccOrEmpty();
});


$('#seleccionaTodasLosExamenes').on('change', function(e) {
  e.preventDefault();
  loadResultExameSeleccOrEmpty();
});


var loadResultExameSeleccOrEmpty = function(nroOrden, key = false) {
  console.log("nroOrdenSeleccionada", nroOrdenSeleccionada);
  let url = gCte.getctx + "/api/orden/" + nroOrdenSeleccionada + "/examenes/resultados";
  $("#accordionPanelExamenes").collapse('hide')
  pLstExamenesSeleccionados = obtenerExamenesSeleccionadosOrEmpty(tableInfoExamenesOrden);
  paramResultadosExamenesSeleccionados.examenes = pLstExamenesSeleccionados;
  console.log("pLstExamenesSeleccionados", pLstExamenesSeleccionados);
  console.log(paramResultadosExamenesSeleccionados);
  if (key == 13) {
    tableResultadosExamenesOrden.on('draw', function() {
      let $table = $('#ordenesDatatable');
      let $table2 = $('#resultadosExamenesOrdenesDatatable');
      if (!$table.is(":visible")) {
        autoMoveTable($table2, rowSeleccionado);
      }
    });
  }
  if (paramResultadosExamenesSeleccionados.examenes == false) { return; }
  paramResultadosExamenesSeleccionados.paciente.fnac = gFechaNacimientoPaciente;
  paramResultadosExamenesSeleccionados.paciente.sexo = gSexoPaciente;
  tableResultadosExamenesOrden.ajax.url(url).load(null, false).draw('page');
  $("#accordionTestResultados").collapse('show');

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

  console.log("examenesSeleccionados 1", examenesSeleccionados)
  let idSeleccionados = new Array();
  for (let i = 0; i < n; i++) {
    let tupla = new tuplaExamen();
    tupla.df_NORDEN = examenesSeleccionados[i].dfe_NORDEN;
    tupla.dfe_IDEXAMEN = examenesSeleccionados[i].dfe_IDEXAMEN;
    tupla.dfe_CODIGOESTADOEXAMEN = examenesSeleccionados[i].dfe_CODIGOESTADOEXAMEN;
    tupla.ce_ABREVIADO = examenesSeleccionados[i].ce_ABREVIADO;
    tupla.ce_CODIGOEXAMEN = examenesSeleccionados[i].ce_CODIGOEXAMEN;
    tupla.cee_DESCRIPCIONESTADOEXAMEN = examenesSeleccionados[i].cee_DESCRIPCIONESTADOEXAMEN;

    idSeleccionados.push(tupla);
  }
  return idSeleccionados;
}
function obtenerExamenesSeleccionadosCR(examenesSeleccionados) {

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

function preverPdfCR(pTableInfoExamenesOrden, nOrden) {
  let tuplasSeleccionadas = obtenerExamenesSeleccionadosCR(pTableInfoExamenesOrden);
  let n = tuplasSeleccionadas.length;
  let lstIdExamenes = "";
  for (let i = 0; i < n; i++) {
    lstIdExamenes += tuplasSeleccionadas[i].idExamen + ' ';
  }

  let url = getctx + '/CRViewer?pNroOrden=' + nOrden + '&pIdExamen=' + lstIdExamenes;
  window.open(url, "pdfWin");

}
let idExamCR = [];
let idExamCRSinAu = [];
let nOrdenCR;
$("#btnImprimirCR").click(function(){
    //let destinatarios = obtenerExamenesSeleccionadosCR("#examenesOrdenesDatatable");
    //examenesOrdenesDatatable
    
    
	$("#examenesNoImprimir").empty();
    idExamCR = [];
    idExamCRSinAu = [];
 	nOrdenCR = '';
    var table = $('#examenesOrdenesDatatable').DataTable();
	    table.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
	    var data = this.data();
	    if(data.dfe_CODIGOESTADOEXAMEN == 'A' || data.dfe_CODIGOESTADOEXAMEN == 'F'){
		    //idExamCR[rowIdx] = data.dfe_IDEXAMEN;
		    idExamCR.push(data.dfe_IDEXAMEN);
			nOrdenCR = data.dfe_NORDEN
			 let url = gCte.getctx + '/api/orden/impresion/informe/orden/'+nOrdenCR;
				 $.ajax({
			    type: "get",
			    url: url,
			    success: function(data) {
			      console.log("");      
			    },
			    error: console.log("")
			  });
			
		}else{
			//idExamCRSinAu[rowIdx] = data.ce_ABREVIADO;
			idExamCRSinAu.push(data.ce_ABREVIADO);
			    var newRowContent = `<tr>
					    <td>${data.ce_ABREVIADO}</td>
				    </tr>`;

			    $("#examenesNoImprimir").append(newRowContent);
	
    }
  });
  if (idExamCR.length == 0) {
    handlerMessageError('No hay exámenes autorizados');
    return;
  }
  if (localStorageAvailable()) {
    if (localStorage.DoNotShowMessageAgain && localStorage.DoNotShowMessageAgain === "true") {
      // user doesn't want to see the message again, so handle accordingly.
      preverPdfCR(idExamCR, nOrdenCR);
      return;
    }
  };
  if (idExamCRSinAu.length == 0) {
    preverPdfCR(idExamCR, nOrdenCR);
  } else {
    $('#imprimirModal').modal('toggle');
  }

});
$("#botonImprimirModal").click(function() {
	//esto agregado para log eventos usario 
	 let url = gCte.getctx + '/api/orden/impresion/informe/orden/'+nOrdenCR;
	 $.ajax({
    type: "get",
    url: url,
    success: function(data) {
      console.log("");      
    },
    error: console.log("")
  });
	

  preverPdfCR(idExamCR, nOrdenCR);
  $('#imprimirModal').modal('hide');
});
function localStorageAvailable() {
  if (typeof (Storage) !== "undefined") {
    return true;
  }
  else {
    return false;
  }
}
$('#checkBoxNoMostrar').click(function() {

  if (localStorageAvailable())
    localStorage.DoNotShowMessageAgain = "true";

})


//preverPdfCR(idExamCR,nOrdenCR);



function genColFilterExamen(colIndex, rowFilter) {

  switch (colIndex) {
    case 0:
      genNoFilter(colIndex, rowFilter, 3);
      break;
    case 1:
      genNoFilter(colIndex, rowFilter, 7);
      break;
    case 2:
      genNoFilter(colIndex, rowFilter, 14);
      break;
    case 6:
      genNoFilter(colIndex, rowFilter, 9);
      break;
    case 7:
      genNoFilter(colIndex, rowFilter, 3);
      break;
    case 8:
      genNoFilter(colIndex, rowFilter, 9);
      break;
  }
}

function getSelectedExams(table) {
  const inputs = document.querySelectorAll('input[data-colselector]:checked');
  const selectedExams = [];

  inputs.forEach((input) => {
    selectedExams.push(table.row(input.closest('tr')).data());
  });

  if (selectedExams.length === 0) {
    return false;
  }

  const exams = selectedExams.map((exam) => ({
    df_NORDEN: exam.dfe_NORDEN,
    dfe_IDEXAMEN: exam.dfe_IDEXAMEN,
    dfe_CODIGOESTADOEXAMEN: exam.dfe_CODIGOESTADOEXAMEN,
    ce_ABREVIADO: exam.ce_ABREVIADO,
    ce_CODIGOEXAMEN: exam.ce_CODIGOEXAMEN,
    cee_DESCRIPCIONESTADOEXAMEN: exam.cee_DESCRIPCIONESTADOEXAMEN,
  }));

  return exams;
}



