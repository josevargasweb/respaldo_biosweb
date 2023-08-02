var estadoResultado = 1;
var tableResultadosExamenesOrden;
var intervaloResultado = setInterval(function() {
  if (estadoResultado === 0) {
    tableResultadosExamenesOrden.ajax.reload(); // user paging is not reset on reload
    console.log("actualizando resultado...")
  }
}, 50000);
// var estado = 0;

// //Actualización automática
$('#idChkAutorefreshres').change(function() {
  console.log($(this).prop("checked"), estado);
  if ($(this).prop("checked") === true) {
    estadoResultado = 0;
    intervaloResultado = setInterval(function() {
      tableResultadosExamenesOrden.ajax.reload(); // user paging is not reset on reload
      console.log("actualizando resultado...")
    }, 10000);
  }
  if ($(this).prop("checked") === false) {
    estadoResultado = 1;
    console.log("desactivando actualizacion resultado")
    clearInterval(intervaloResultado);
  }


  //console.log("estado: " +estado);
});


var getParametros = function(d) {
  return JSON.stringify(d);
};

var initTableResultadosExamenesOrden = function(jqDtId) {
  nroOrden = -1;
  tableResultadosExamenesOrden = $(jqDtId).DataTable({
    ajax: {
      "url": gCte.getctx + "/api/orden/" + nroOrden + "/examenes/resultados",
      "type": "POST",
      "dataSrc": function(response) {
        let resultado = response.dato;

        if (resultado === null) {
          resultado=[];
        } else // (resultado != null) {
          if (resultado.length < 6) {
            $("#resultadosExamenesOrdenesDatatable_wrapper").find(".dataTables_scrollBody").css({ 'overflow': '' });
          } else {
            $("#resultadosExamenesOrdenesDatatable_wrapper").find(".dataTables_scrollBody").css({ 'overflow': 'auto' });
          }
          resultado.sort((a, b) => {
            if (a.ct_SORT < b.ct_SORT) return -1;
            if (a.ct_SORT > b.ct_SORT) return 1;
            if (quitarTildes(a.ce_ABREVIADO2) < quitarTildes(b.ce_ABREVIADO2)) return -1;
            if (quitarTildes(a.ce_ABREVIADO2) > quitarTildes(b.ce_ABREVIADO2)) return 1;
            if (quitarTildes(a.ct_ABREVIADO) < quitarTildes(b.ct_ABREVIADO)) return -1;
            if (quitarTildes(a.ct_ABREVIADO) > quitarTildes(b.ct_ABREVIADO)) return 1;
            return 0;
          });
        console.log("testyresultado", resultado);
        return resultado;
      },
      "data": function(d) { return JSON.stringify(paramResultadosExamenesSeleccionados); },
      "contentType": "application/json",
      processData: false,
    },
    serverSide: true, 
    //stateSave: true,
    lengthChange: false,
    scrollCollapse: true,
    responsive: true,
    paging: false,
    searchDelay: 500,
    scrollY: calcularHeight('panelSuperior', ['panelExamanes'], 212),
    // "order": [[ 14, 'asc' ],[ 1, 'asc' ], [ 2, 'asc' ]],
    scrollX: true,
    rowId: function(a) {
      return a.df_NORDEN.toString().concat('-').concat(a.dfe_IDEXAMEN.toString()).concat('-').concat(a.dfet_IDTEST.toString())
    },
    info: false,
    "language": {
      "info": "Mostrando _TOTAL_ órdenes ",
      "infoFiltered": "filtradas de un total de _MAX_ ",
      "loadingRecords": "Por favor espere - cargando ...",
      "emptyTable": "No hay datos disponibles",
      "zeroRecords": "No se encontraron datos",
    },
    // order: [[16, "asc"],[ 15, 'asc' ],[ 17, 'asc' ]], 
    columnDefs: [
      { orderable: false, targets: '_all' },
      {
        targets: 0,
        width: '30px',
        className: 'dt-left',
        //  orderable: false,
        render: function(data, type, row, meta) {
          return `<input type="checkbox" data-colselector-test="x" value="" class="dt-left checkable" style="width:${t1 * 3}px"/> `;
        },
      },
      {
        targets: 1,
        //   orderable: true,
        render: function(data, type, row, meta) {
          if (row.dfe_EXAMENANULADO === "S") {

            return `<div style="width:${t1 * 12}px">${largoTextoAutomatico(row.ct_ABREVIADO, '.res-examen')}</div>`;
          } else {
            return `<div style="width:${t1 * 12}px">${largoTextoAutomatico(row.ce_ABREVIADO2, '.res-examen')}</div>`;
          }

        },
      },
      {
        targets: 2,
        //   orderable: true,
        render: function(data, type, row, meta) {
          if (row.dfe_EXAMENANULADO === "S") {
            return `<div style="width:${t1 * 15}px"><span title="${row.ct_DESCRIPCION != null ? row.ct_DESCRIPCION : ''}">${largoTextoAutomatico(row.ct_ABREVIADO, '.res-test')}</span></div>`

          } else {
            return `<div style="width:${t1 * 15}px"><span title="${row.ct_DESCRIPCION != null ? row.ct_DESCRIPCION : ''}">${largoTextoAutomatico(row.ct_ABREVIADO, '.res-test')}</span></div>`
          }

        },
      },
      {
        targets: 3,
        //     orderable: false,
        render: genAlerta,
      },
      {
        targets: 4,
        //   orderable: false,
        render: genInputRespuesta,
      },
      {
        targets: 5,
        //    orderable: false,
        render: genUnidad,
      },
      {
        targets: 6,
        //      orderable: false,
        render: genValoresReferencia,
      },
      {
        targets: 7,
        //     orderable: false,
        render: genCell,
      },
      {
        targets: 8,
        //     orderable: false,
        render: genLabel,
      },
      {
        targets: 9,
        //    orderable: false,
        render: genDeltaCheckV2,
      },
      {
        targets: 10,
        //     orderable: false,
        render: genHistoria,
      },

      //  *********************  AQUI REALIZAR LOGICA DE HAMBURGUESA ****************************
      {
        targets: 11,
        data: null,
        defaultContent: '',
        //     orderable: false,
        title: 'Acciones',
        render: function(data, type, row, meta) {
          let disabledClass = "";
          // if(row.dfet_REFERENCIADESDE >= row.dfet_RESULTADO && row.dfet_REFERENCIAHASTA <= row.dfet_RESULTADO){
          //   disabledClass = "disabled"; 
          // }

          let val = row.dfet_RCRITICO;
          console.log('es critico', val);
          switch (val) {
            case 'Normal':
              disabledClass = 'disabled';
              break;
            case 'B':
              disabledClass = '';
              break;
            case 'CB':
              disabledClass = '';
              break;
            case 'A':
              disabledClass = '';
              break;
            case 'CA':
              disabledClass = '';
              break;
            default:
              disabledClass = 'disabled';
              break;
          }


          console.log('row notifty', row)
          let formula = "sin formula";
          let classFormula = "disabled"

          if (data.formulatraducida != null) {
            formula = data.formulatraducida;
            classFormula = ""
          }

          let muestra = "";
          let classMuestra = "disabled"
          if (row.dfet_IDMUESTRA != null) {
            muestra = ` <span class="label label-lg font-weight-bold label-inline"> ${row.dfm_CODIGOBARRA} </span>`;
            classMuestra = ""
          }

          if(row.ctr_DESCRIPCION == "FÓRMULA"){
            classFormula = ""
            classMuestra = "disabled"
          }


          return ` 	
		<div class="col-md-4 d-flex" style="width:${t1 * 4}px">
          <div class="dropdown"  ><a class="navi-link mr-0 d-flex align-items-center" data-toggle="dropdown" aria-expanded="false">
              <i class="fas fa-bars text-blue text-primary " style="font-size: 2rem"></i> </a>
                <div class="dropdown-menu dropdown-menu-right" style="width:250px" >
                  <div class="col-12">
                        <a href="#" class="${classMuestra}">
                      <i class="fa fa-flask "   style="color:${classMuestra === 'disabled' ? '#858383' : 'black'} !important;"  aria-hidden="true"></i> Número de Muestra ${muestra}</a>  </br>
                        <a href="#" class="${classFormula}" data-toggle="tooltip" data-placement="bottom" title="${formula}" onclick="getModalFormula('${formula}')">
                          <i class="fa fa-calculator" style="color:${classFormula === 'disabled' ? '#858383' : 'black'} !important;" aria-hidden="true"></i> Formula</a>  </br>
                        <a href="#" class="${disabledClass}" onclick="notifyTestResult( ${row.df_NORDEN} ,  ${row.dfe_IDEXAMEN} , ${row.dfet_IDTEST} );" >
                      <i class="fas fa-phone-alt" style="color:${disabledClass === 'disabled' ? '#858383' : 'black'} !important;" ></i>  Notificar</a>  </br>
                      <a href="#" onclick="iniciarLineaTiempo(${row.df_NORDEN} ,  ${row.dfe_IDEXAMEN} , ${row.dfet_IDTEST});" >
                      <i class="far fa-clock" style="color:black !important;" ></i>  Línea de tiempo	</a></br>						           
                    <a href="#" onclick="getTestDetail( ${row.df_NORDEN} ,  ${row.dfe_IDEXAMEN} , ${row.dfet_IDTEST} );" >
                            <i class="fa fa-search" style="color:black !important;" ></i>  Detalle</a><br>   
                        <a href="#" onclick="getTestEvents(${row.df_NORDEN} ,  ${row.dfe_IDEXAMEN} , ${row.dfet_IDTEST});" >
                        <i class="fas fa-history" style="color:black !important;" ></i>  Eventos	</a></br>              
                      
                  </div>
                </div>       	             	        
          </div>       	             	        
     	</div>`;



        },
      },
      {
        targets: [13, 14, 15],
        visible: false,
        //   orderable: false,
      },
      {
        targets: 12,
        data: null,
        defaultContent: '',
        //     orderable: false,       
        render: function(data, type, row, meta) {
          let limpiar = "";
          if (row.cert_DESCRIPCIONESTADOTEST === "DIGITADO" || row.cert_DESCRIPCIONESTADOTEST === "TRANSMITIDO") {
            limpiar = `
		              <a href="#" onclick="cleanValue(${row.df_NORDEN} ,  ${row.dfe_IDEXAMEN} , ${row.dfet_IDTEST});" class="p-1 pl-2 pr-2" title="Limpiar">
		        <i id="" class="la la-broom icon-xl"></i>
		                      </a>`;
          } else {
            limpiar = `
		              <a disabled href="#"  class="p-1 pl-2 pr-2" title="Limpiar">
		        <i id="" class="la la-broom icon-xl"></i>
		                      </a>`;
          }

          return `<div style="width:${t1 * 3}px">${limpiar}</div>`

        },
      },
      {
        targets: [16],
        visible: false,
        render: function(data, type, row, meta) {
          return quitarTildes(data);

        }
      },
      {
        targets: [17],
        visible: false,
        render: function(data, type, row, meta) {
          return quitarTildes(data);

        }
      },
    ],
    select: {
      style: 'multi',
      selector: 'td:first-child input',
      items: 'row'
    },
    columns: [
      { "": "", "width": "3%" },
      { "data": "ce_ABREVIADO2", "width": "14%" },
      { "data": "ct_ABREVIADO", "width": "18%" },
      { "": "", "width": "5%" },
      { "data": "cum_DESCRIPCION", "width": "13%" },
      { "": "", "width": "5%" },
      { "": "", "width": "6%" },
      { "": "", "width": "9%" },
      { "data": "cert_DESCRIPCIONESTADOTEST", "width": "5%" },
      { "data": "deltaCheck", "width": "5%" },
      { "data": "resultadoHistorico", "width": "10%" },
      { "": "", "width": "4%" },
      { "": "", "width": "4%" },
      { "data": "ct_IDSECCION" },
      { "data": "dfe_EXAMENANULADO" },
      { "data": "ct_SORT"},
      { "data": "ce_ABREVIADO2"},
      { "data": "ct_ABREVIADO" },

    ],
    "search": {
      "smart": false
    },

    "initComplete": function(settings, json) {
      $("#resultadosExamenesOrdenesDatatable_filter").hide();
      console.log("tableResultadosExamenesOrden", myTableColFilter)
      tableResultadosExamenesOrden.filtro = myTableColFilter;
      let rowFilter = $('<tr class="filter"></tr>').appendTo($(tableResultadosExamenesOrden.table().header()));

//      this.api().columns().every(function() {
//        let columna = this;
//        tableResultadosExamenesOrden.filtro(columna.index(), rowFilter, tableResultadosExamenesOrden);
//      });
 
      let idSeccion = $("#idSeccion").val()
      gSeccionSeleccionada = idSeccion.concat("$");
      searchResultadosAnulados(gSeccionSeleccionada);
      let count = tableResultadosExamenesOrden.rows({ search: 'applied' }).length;
      //  alert(count);
      if (count === 0) {
        tableResultadosExamenesOrden.clear().draw();
      }
    }
  });

  myTableColFilter = function(colIndex, rowFilter, table) {

    switch (colIndex) {
      case 0:
        genTableColCheckSelector(colIndex, rowFilter, table);
        break;
      case 1:
      case 2:
      case 11:
        genTableColInputFilter(colIndex, rowFilter, table);
        break;
      case 8:
        genTableColSelectFilter(colIndex, rowFilter, table);
        break;
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
        break;
      default:
        genNoFilter(colIndex, rowFilter);
    }
  }

  tableResultadosExamenesOrden.on('draw', function() {
    if (tableResultadosExamenesOrden.settings()[0].oFeatures.bServerSide){
      tableResultadosExamenesOrden.settings()[0].oFeatures.bServerSide = false;
      tableResultadosExamenesOrden.draw();
    }
  
    $('.containerTableTest').removeClass('hidden');
    $(".spinnerContainer3").remove();
    //carga y deja desabilitado el boton
    $("#btnFirmaResultados").prop('disabled', true)
    $("#seleccionaTodasLosResultados").prop('checked', false)
    registro_resultado = tableResultadosExamenesOrden.rows().count();
    //console.log('total rows',registros_muestras);
    let rows_filtroMue = tableResultadosExamenesOrden.rows({ search: 'applied' }).count();
    $("#dt_registros_resultado").html("Mostrando <b>" + rows_filtroMue + "</b> de <b>" + registro_resultado + "</b> test en total");
    //$("#resultadosExamenesOrdenesDatatable_wrapper").find(".dataTables_scrollBody").css({ 'overflow' : ''});
    let inputs = $("input[data-tipo-resultado]");
    let n = inputs.length;
    for (let i = 0; i < n; i++) {
      inputs[i].onblur = changeValue;
      inputs[i].onkeydown = keypressValue;
      inputs[i].onfocus = focusValue;
    }
    let select = $("select[data-tipo-resultado]");

    let contador = select.length;
    for (let i = 0; i < contador; i++) {
      select[i].onblur = changeValue;
      select[i].onkeydown = keypressValue;
      select[i].onfocus = focusValue;
    }


    let inputDec = $("input[data-tipo-resultado='N']").get();
    n = inputDec.length;
    for (let i = 0; i < n; i++) {
      let escala = inputDec[i].dataset['nDecimales'];
      //     IMask($("input[data-tipo-resultado='N']").get(0), {
      IMask(inputDec[i], {
        mask: Number,  // enable number mask

        // other options are optional with defaults below
        scale: escala,  // digits after point, 0 for integers
        signed: false,  // disallow negative
        thousandsSeparator: '.',  // any single char
        padFractionalZeros: true,  // if true, then pads zeros at end to the length of scale
        normalizeZeros: true,  // appends or removes zeros at ends
        radix: ',',  // fractional delimiter
        mapToRadix: [',', '.'], // symbols to process as radix
        min: 0,
        //        max: 10000
      });
    }

      globalselects = $("select[data-tipo-resultado='G']"  );
      n = globalselects.length;
      for (let i = 0; i < n; i++) {
        globalselects[i].onchange = changeValue;
        // inputs[i].onchange = changeValue;      
      }
    getDatosTest();
    setGMClick();


  });



  tableResultadosExamenesOrden.on('xhr', function(e, settings, json, xhr) {
    if (json !== null && json !== undefined) {
      setSelect(json, 'cert_DESCRIPCIONESTADOTEST', 8);
      searchResultadosAnulados();
    }
  });


  tableResultadosExamenesOrden.on("change", 'input[data-colselector-test="x"]', function (e) {
    e.preventDefault();
    // let isChecked = $(this).is(":checked");
    // let isAnyChecked = $('input[data-colselector-test="x"]:checked').length > 0;
    const resultados = obtenerResultadosSeleccionados2(tableResultadosExamenesOrden);
    if(resultados){
      let existeElemento = resultados.some(function(elemento) {
        return elemento.cert_DESCRIPCIONESTADOTEST !== "TRANSMITIDO" && elemento.cert_DESCRIPCIONESTADOTEST !== "DIGITADO";
      })
      
      let existeElementoRetirarFirma = resultados.every(function(elemento) {
        return elemento.cert_DESCRIPCIONESTADOTEST === "FIRMADO"
      })
      
      if(existeElemento){
        $("#btnFirmaResultados").prop('disabled', true)
      }else{
        $("#btnFirmaResultados").prop('disabled', false)
      }
     
      if(existeElementoRetirarFirma){
        $("#btnRetiraFirma").removeClass('disabled')
      }else{
        $("#btnRetiraFirma").addClass('disabled')
      }
    }else{
      $("#btnFirmaResultados").prop('disabled', true)
      $("#btnRetiraFirma").addClass('disabled')
    }

})

}



var loadResultadosExamenesSeleccionadosOrden = function(nroOrden, key = false) {
  let url = gCte.getctx + "/api/orden/" + nroOrdenSeleccionada + "/examenes/resultados";
  $("#accordionPanelExamenes").collapse('hide')
  pLstExamenesSeleccionados = obtenerExamenesSeleccionados(tableInfoExamenesOrden);
  console.log('estos son filtros', pLstExamenesSeleccionados);
  paramResultadosExamenesSeleccionados.examenes = pLstExamenesSeleccionados;
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

/*** modal formula  */

function getModalFormula(formula) {
  $("#formlaModalText").text(formula);
  $("#formulaTestModal").modal("show")
}
/**** MOdal detalle eventos test  */

$("#salirModalFormula").click(function() {
  $("#formlaModalText").text();
  $("#formulaTestModal").modal("hide")
})

//cambiado para mostrar y ocultar boton de busqueda
function getTestEvents(idOrden, idExamen, idTest) {
  // $("#eventosTestModal").modal('show');
  $("#buscarEventoOrden").hide();
  $("#buscarEventoTest").show();
  $("#tituloEvento").text("Eventos del Test")
  $("#eventosOrdenModal").modal('show');
  //$("#idOrdenEvento").val(idOrden)
  eventTest(idOrden, idExamen, idTest);
}
//hasta aca  *******************************
function eventTest(idOrden, idExamen, idTest) {
  let url = gCte.getctx + '/api/test/events';
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify({ dfetNorden: idOrden, dfetIdexamen: idExamen, dfetIdtest: idTest }),
    success: function(order) {
      showOrderEvents(order.dato)
    },   //            showRestTestEvents,
    error: handlerMessageError,
    contentType: 'application/json; charset=utf-8'
  });
}


const eventsModalRowHtmlTemplate = `<tr class='pointer'>
                                    <td class='mx-auto'>{lefFecharegistro}</td>
                                    <td class='mx-auto'>{null}</td>
                                    <td class='mx-auto'>{lefNombrecampo}</td>
                                    <td class='mx-auto'>{lefValoranterior}</td>
                                    <td class='mx-auto'>{lefValornuevo}</td>
                                </tr>`;





function showRestTestEvents(events) {
  $("#idTestCanvasEventsTable").empty();
  events.dato.forEach(function(event) {
    let tr = fillTemplate(eventsModalRowHtmlTemplate, event);
    console.log(tr);
    $("#idTestCanvasEventsTable").append(tr);
  })
}

function getTestDetail(idOrden, idExamen, idTest) {
  $("#detallesTestModal").modal('show');
  detailTest(idOrden, idExamen, idTest);
}


function detailTest(idOrden, idExamen, idTest) {

    $.ajax({
        type: "POST",
        url: gCte.getctx +"/api/test/testDetail",
        contentType: "application/json",
        data: JSON.stringify({
            "idOrden": idOrden,
            "idExam": idExamen,
            "idTest": idTest
        }),
        success: function(response) {
            
            showTestDataCR(response.dato[0]);
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
/* Comentado x jan 11-05-2023
  $.getJSON("Microbiologia/api/getTestData", { orderId: idOrden, examId: idExamen, testId: idTest })
    .done(function(test) {
      $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function(antibiogramOptions) {
          showTest(test);
        })
        .fail(function(jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });*/
}

function showTest(test, enableABG) {
 // showTestData(test.data);
  showTestEvents(test.events);
  showTestNotification(test.notification);
}

function showTestDataCR(data) {
  $("#idTestCanvasDataName").empty(data.nombrexamen);
  $("#idTestCanvasDataName").append(data.nombrexamen);
  $("#idTestCanvasDataExam").text(data.nombrexamen);
  $("#idTestCanvasDataSample").text(data.nombremuestra);
  $("#idTestCanvasDataResultType").text(data.tipodesultado);
  $("#idTestCanvasDataCode").text(data.codigo);
  $("#idTestCanvasDataPrefix").text(data.prefijo);
  $("#idTestCanvasDataResult").text(data.resultado);
  $("#idTestCanvasDataCriticalValue").text(data.valorcritico);
  //$("#idTestCanvasDataBodyPart").text(data.bodyPart);
 // $("#idTestCanvasDataStatus").text(data.status);
}

function showTestEvents(events) {
  $("#idTestCanvasEventsTable").empty();
  events.forEach(function(event) {
    $("#idTestCanvasEventsTable").append(fillTemplate(eventsRowHtmlTemplate, event));
  })
}



function addEventsElements(element) {
  let inputs = $(element);
  let n = inputs.length;
  for (let i = 0; i < n; i++) {
    inputs[i].onblur = changeValue;
    inputs[i].onkeydown = keypressValue;
    inputs[i].onfocus = focusValue;
  }

  if ($(element).attr("data-tipo-resultado") === "N") {
    for (let i = 0; i < n; i++) {
      let escala = inputs[i].dataset['nDecimales'];
      //     IMask($("input[data-tipo-resultado='N']").get(0), {
      IMask(inputs[i], {
        mask: Number,  // enable number mask

        // other options are optional with defaults below
        scale: escala,  // digits after point, 0 for integers
        signed: false,  // disallow negative
        thousandsSeparator: '.',  // any single char
        padFractionalZeros: true,  // if true, then pads zeros at end to the length of scale
        normalizeZeros: true,  // appends or removes zeros at ends
        radix: ',',  // fractional delimiter
        mapToRadix: [',', '.'], // symbols to process as radix
        min: 0,
        //        max: 10000
      });
    }
  }



  if ($(element).attr("data-tipo-resultado") === "G") {
    for (let i = 0; i < n; i++) {
      inputs[i].onchange = changeValue;
    }
  }
  getDatosTest();
  setGMClick();
}


$(function() {
  $('[data-toggle="tooltip"]').tooltip()
})


