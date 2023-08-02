
var estadoResultado = 1;

var intervaloResultado = setInterval( function () {
  if(estadoResultado === 0){
    tableResultadosExamenesOrden.ajax.reload(); // user paging is not reset on reload
      console.log("actualizando resultado...")
  }
}, 50000 );
// var estado = 0;

// //Actualización automática
$('#idChkAutorefreshres').change(function(){
console.log($(this).prop("checked"),estado);
if ($(this).prop("checked") === true){
  estadoResultado = 0;
  intervaloResultado = setInterval( function () {
  tableResultadosExamenesOrden.ajax.reload(); // user paging is not reset on reload
  console.log("actualizando resultado...")
}, 10000 );
}
if($(this).prop("checked") === false){
  estadoResultado = 1;
console.log("desactivando actualizacion resultado")
clearInterval(intervaloResultado);
}


//console.log("estado: " +estado);
});

$("#btnRefreshBuscarResultado").click(function () {
    console.log("actualizando manualmente...")
    tableResultadosExamenesOrden.ajax.reload();
});

// Test y resultados
let micro = new CfgMicrobiologia();
micro.getMicrobiologia();

console.log("micro.misMicrobiologia",micro.misMicrobiologia);


let stats = new CfgStatus();
stats.getStatus();



let loadResultadosExamenesOrden = function(nroOrden,examid, key = false) {
  let url = `${gCte.getctx }/api/microbiologia/${nroOrden}/examenes/getTestxExamen`;
  $("#accordionPanelExamenes").collapse('hide')
  tableResultadosExamenesOrden.ajax.url(url).load().draw();
  $("#accordionTestResultados").collapse('show');

}



let initTableResultadosExamenesOrden = function(jqDtId) {
    
  nroOrden = -1;
  examid = -1;""
  url = `${gCte.getctx }/api/microbiologia/${nroOrden}/examenes/getTestxExamen`;
  tableResultadosExamenesOrden = $(jqDtId).DataTable({
    rowId: function(a) {
      return a.orderid.toString().concat('_').concat(a.examid.toString()).concat('_').concat(a.testid.toString())
    },
    ajax: {
      "url": url,
      "type": "POST",
      contentType: 'application/json',
      // "dataSrc": "testsList"
      "dataSrc": "dato",
      "data": function(d) { 
          console.log("Examenes Seleccionados:  ")
	console.log(paramResultadosExamenesSeleccionados)
          return JSON.stringify(paramResultadosExamenesSeleccionados); },
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
    scrollY: "200px",
    scrollX: true,
    columnDefs: [
      {
        targets: 0,
        width: '30px',
        className: 'dt-left',
        orderable: false,
        render: function(data, type, row, meta) {
          return `<input type="checkbox" value=""  data-colselector-test='x' class="checkable chck-test" /> `;
        },
      },
      {
        orderable: false,
        targets: [2],
        data: null,
        defaultContent: '',
        render: genInputRespuesta,
      },
      {
        orderable: false,
        targets: 3,
        data: null,
        defaultContent: '',
        render: estadotest,
      },
      {
      orderable: false,
      targets: [4],
      data: null,
      defaultContent: '',
      render: selecttest,
      order: [[2, 'asc']]
    },{
      orderable: false,
      targets: [5],
      data: null,
      defaultContent: '',
      render: genAcciontest,
      order: [[2, 'asc']]
    },{
      orderable: false,
      targets: [6,7,8],
      visible: false,
    },{
      targets: 9,
      data: null,
      defaultContent: '',
      orderable: false,       
      render: function(data, type, row, meta) {
        let limpiar = "";
        if (row.status === "DIGITADO" || row.status === "TRANSMITIDO"){
            limpiar = `
                    <a href="#" onclick="cleanValue(${row.orderid} ,  ${row.examid} , ${row.id});" class="p-1 pl-2 pr-2" title="Limpiar">
                        <i id="" class="la la-broom icon-xl"></i>
                    </a>`;
	} else {
          limpiar = `
                    <a disabled href="#"  class="p-1 pl-2 pr-2" title="Limpiar">
                        <i id="" class="la la-broom icon-xl"></i>
                    </a>`;
	}
        return`<div style="width:${t1*3}px">${limpiar}</div>`
      }
    }
  ],
    select: {
      style: 'multi',
      //selector: 'td:first-child ',
      selector: 'td:first-child input[type="checkbox"]',
      //selector: 'td:not(:last-child)',
      "className": 'row-selected',
      items: 'cell',

    },
    columns: [
      { "": ""},
      { "data": "test", orderable: false },
      { "": ""},
      { "data": "status", orderable: false },
      { "": ""},
      { "": ""},
      { "data": "cultivo"},
      { "data": "estado"},
      { "data": "isculture"},
      { "": ""}
    ],
    "initComplete": function(settings, json) {
      $(jqDtId+"_filter").hide();

      $("#resultadosExamenesOrdenesDatatable_filter").hide();
      let rowFilter = $('<tr id="filterExamen" class="filterExamen"></tr>').appendTo($(tableInfoExamenesOrden.table().header()));
      this.api().columns().every(function() {
        let columna = this;
      });
      //si existen los elementos se le agregan
      let pendiente = $("#estadoPendiente").length ? "P": "";
      let enProceso = $("#estadoEnProceso").length ? "E": "";
      let  text = [pendiente, enProceso, "F","I","B","A"].filter(Boolean).join("|");
      
      let cultivo = $('input[name="cultivoChck"]:checked').val();

      let esCultivo = $("#estadoCultivo").length ? "S":"";

      searchResultadoPendiente(text,cultivo,esCultivo,jqDtId)


    },
    "rowCallback": function( row, data, index ) {
        if ($('#resultadosExamenesOrdenesDatatable tbody tr td:eq(0)').hasClass("row-selected")){
            $(this).find(".chck-test").prop("checked", true);
        }
    }
  });




  tableResultadosExamenesOrden.on('draw', function() {
    if (tableResultadosExamenesOrden.settings()[0].oFeatures.bServerSide){
      tableResultadosExamenesOrden.settings()[0].oFeatures.bServerSide = false;
      tableResultadosExamenesOrden.draw();
    }
  
    $('.containerTableTest').removeClass('hidden');
    $(".spinnerContainer3").remove();
    let registro_tests = tableResultadosExamenesOrden.rows().count();

    let rows_filtroTests = tableResultadosExamenesOrden.rows({ search: 'applied' }).count();
    $("#dt_registros_tests").html("Mostrando <b>" + rows_filtroTests + "</b> de <b>" + registro_tests + "</b> tests en total");
    
    // let inputs = $("input[data-tipo-resultado]");
    // let n = inputs.length;
    // for (let i = 0; i < n; i++) {
    //   inputs[i].onblur = changeValue;
    //   inputs[i].onkeydown = keypressValue;
    //   inputs[i].onfocus = focusValue;
    // }
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

      globalselects = $("select[data-tipo-resultado='G']");
      n = globalselects.length;
      for (let i = 0; i < n; i++) {
        globalselects[i].onchange = changeValue;
  
      }
    getDatosTest();
    setGMClick();

  });
  
  
  
  $("#seleccionaTodasLosResultados").on("click", function(e) {
    let inputs = $("input[data-colselector-test]");
    if ($(this).is(":checked")) {
      $(inputs).prop('checked', true);
      tableResultadosExamenesOrden.rows().select();
    } else {
      $(inputs).prop('checked', false);
      tableResultadosExamenesOrden.rows().deselect();
    }
  });
  

}

function getDatosTest() {

  let cfgGlosas = new CfgGlosas();
  cfgGlosas.getAllGlosas();
}



function changeValue(e) {
  let input = e.target;
  let oldValue = input.dataset.valor;
  if (oldValue !== undefined) {
    console.log(oldValue);
  }
  if (input.dataset['tipoResultado'] === 'N') {
    let newValue = e.target.value.replaceAll('.', '');
    newValue = newValue.replaceAll(',', '.');
    console.log(oldValue + ' --------- ' + newValue);

    if (oldValue === newValue) {
      console.log('son iguales');
      return;
    }
    else if (newValue != null && newValue === "") {
      e.target.value = oldValue;
      //      handlerMessageWarning("No se puede dejar sin valor.");
      return;
    }

    // actualizarValNumerico(input, nroOrdenSeleccionada);
    input.dataset.valor = e.target.value;
    console.log('tr');

    let thisRow = $(input).parent().parent();
    let row = $('#resultadosExamenesOrdenesDatatable tbody tr').index(thisRow);
    let data = tableResultadosExamenesOrden.row(row).data();
    let val = data['dfet_RCRITICO'];
    let tipo_alerta = getTipoValorAlerta(val);
    if (tipo_alerta == 'anormal') {
      handlerMessageWarning("Valor anormal");
    } else if (tipo_alerta == 'critico') {
      handlerMessageError("Valor crítico");
    }

  }
  else if (input.dataset['tipoResultado'] === 'G') {
    // actualizarValNumerico(input, nroOrdenSeleccionada);
  }
  else {
    console.log('Otro tipo');
  }
}

function keypressValue(e) {
  if (e.key == 'Enter') {
    let input = e.target;
    let oldValue = input.dataset.valor;
    if (oldValue !== undefined) {
      console.log(oldValue);
    }
    let newValue = e.target.value.replaceAll('.', '');
    newValue = newValue.replaceAll(',', '.');

    if (oldValue === newValue) {
      console.log('son iguales');
      return;
    }
    rowSeleccionado++;
    $(input).blur();
    $(input).focus();
  }
}

function focusValue(e) {

  let input = e.target;
  let oldValue = input.dataset.valor;
  if (oldValue !== undefined) {
    console.log(oldValue);
  }
}


// function actualizarValNumerico(inputElement, nroOrden) {

//   let valor = inputElement.value;
//   let nOrden = nroOrden;
//   let idExamen = inputElement.dataset['idexamen'];
//   let idTest = inputElement.dataset['idtest'];
//   let codigoTr = inputElement.dataset['tipoResultado'];
//   let obligado = inputElement.dataset['obligado'];
//   let omision = inputElement.dataset['omision'];
  
  
//   let rowId = nOrden.toString().concat('-').concat(idExamen).concat('-').concat(idTest);
//   let url = gCte.getctx + "/api/orden/update/examen/test/resultado";
//   let postData = JSON.stringify({
//     "df_NORDEN": nOrden,
//     "dfe_IDEXAMEN": idExamen,
//     "dfet_IDTEST": idTest,
//     "dfet_RESULTADO": valor,
//     "ctr_CODIGO": codigoTr

//   });
//   let fnExito = function(result, status, xhr) {
//     let respuesta = result;
//     if (respuesta.status == 200) {

//       tableResultadosExamenesOrden.row("#".concat(rowId)).data(result.dato.resultadoExamen);
      
//       if (result.dato.resultadoExamen.ctr_CODIGO==='G'){
        
//         let selectCur = $(`select[data-idtest="${result.dato.resultadoExamen.dfet_IDTEST}"][data-idexamen="${result.dato.resultadoExamen.dfe_IDEXAMEN}"]`);
//         const estadoResultado = result.dato.resultadoExamen.cert_DESCRIPCIONESTADOTEST;
//           selectCur.val(valor);

//         if ((valor === null || valor === '') && obligado === 'S') {
//           valor = omision;
//           selectCur.val(valor);
//         }

//         if (estadoResultado === 'INGRESADO' || estadoResultado === 'FIRMADO') {
//           selectCur.val(valor);
//           selectCur.prop('disabled',true);
//         }
//       }
      
//       handlerMessageOk(respuesta.message);
//     }
//     else if (respuesta.status == 503) {
//       tableResultadosExamenesOrden.ajax.reload(null, false);
//       tableInfoExamenesOrden.ajax.reload(null, false);
//       handlerMessageWarning(respuesta.message);
//     }
//     else {
//       handlerMessageError(respuesta.message);
//     }
//     //    searchResultadosAnulados();

//   };

  

//   $.ajax({
//     type: "POST",
//     url: url,
//     data: postData,
//     success: fnExito,
//     error: fracaso,
//     contentType: 'application/json; charset=utf-8'
//   });

// }

function fracaso(xhr, status, error) {
  if(error === ""){return}
  console.table(error);
  handlerMessageError(error);
}


function getTipoValorAlerta(val) {
  let rpta;
  switch (val) {
    case 'B':
      rpta = 'anormal';
      break;
    case 'CB':
      rpta = 'critico';
      break;
    case 'A':
      rpta = 'anormal';
      break;
    case 'CA':
      rpta = 'critico';
      break;
    default:
      break;
  }

  return rpta;
}



function searchResultadoPendiente(pendiente,cultivo,escultivo,id) {
  console.log('son los datos',pendiente,cultivo,escultivo,id)
  $(id).DataTable().column(6).search(cultivo, true, false, false)
                    .column(7).search(pendiente, true, false, false)
                    .column(8).search(escultivo, true, false, false)
                  .draw();
                  // .column(10).search(pendiente, true, false, false).draw();
}

$('#resultadosExamenesOrdenesDatatable').on('change', '.chck-test', function (e) {
  e.preventDefault();
  let table =$('#resultadosExamenesOrdenesDatatable').DataTable();
  let row = $(this).closest('tr');
    if(row.find(".chck-test").is(':checked')){
      that= table.row( row ).data();
      tableResultadosExamenesOrden.rows(row).select();
      getDetailsTest(that.orderid,that.examid,that.testid,that.test,that.status);
    }else{
      tableResultadosExamenesOrden.rows(row).deselect();
      let checkbox = this;
      let parent = checkbox.parentNode;
      let row1 = parent.parentNode;
      let rows = row1.parentNode.querySelectorAll('tr');
      let checkedSiblingCount = Array.from(rows).reduce(function(count, row) {
        let checkboxes = row.querySelectorAll('input[type="checkbox"]');
            return count + Array.from(checkboxes).filter(function(checkbox) {
          return checkbox.checked;
        }).length;
      }, 0);

      if (checkedSiblingCount > 0) {
        let lastChecked = Array.from(rows).reduce(function(lastChecked, row) {
          let checkboxes = row.querySelectorAll('input[type="checkbox"]');
          let lastCheckedInRow = Array.from(checkboxes).reduce(function(lastChecked, checkbox) {
            if (checkbox.checked && (!lastChecked || checkbox.name > lastChecked.name)) {
              return checkbox;
            }
            return lastChecked;
          }, null);
          if (lastCheckedInRow && (!lastChecked || lastCheckedInRow.name > lastChecked.name)) {
            return lastCheckedInRow;
          }
          return lastChecked;
        }, null);
        that= table.row(lastChecked.parentNode.parentNode).data();
        getDetailsTest(that.orderid,that.examid,that.testid,that.test,that.status);
      } else {
        disableSelectedDetails();
      }
    }
  });


function genAcciontest(data, type, row, meta) {
    let disabledClass = "";
    
    let val = row.resultadocritico;
    switch (val){
        case 'Normal':
            disabledClass = 'disabled';
            break;
        case 'B':
        case 'CB':
        case 'A':
        case 'CA':
            disabledClass = '';
            break;
        default:
            disabledClass = 'disabled';
            break;
              
    }
    
    let muestra = "";
    if (row.labelcode != null) {
      muestra = ` <span class="label label-lg font-weight-bold label-inline"> ${row.labelcode} </span>`;
    }
    
    return `
        <div class="col-md-4 d-flex" style="width:${t1*4}px">
          <div class="dropdown"  ><a class="navi-link mr-0 d-flex align-items-center" data-toggle="dropdown" aria-expanded="false">
              <i class="fas fa-bars text-blue text-primary " style="font-size: 2rem"></i> </a>
                <div class="dropdown-menu dropdown-menu-right" style="width:250px" >
                  <div class="col-12">
                    <a href="#" >
                       <i class="fa fa-flask "   style="color:black !important;"  aria-hidden="true"></i> Número de Muestra ${muestra}</a>  </br>
                    <a href="#" class="${disabledClass}" onclick="notifyTestResult( ${row.orderid} ,  ${row.examid} , ${row.id} );" >
                       <i class="fas fa-phone-alt" style="color:black !important;" ></i>  Notificar</a>  </br>
                    <a href="#" onclick="iniciarLineaTiempo(${row.orderid} ,  ${row.examid} , ${row.id});" >
                       <i class="far fa-clock" style="color:black !important;" ></i>  Línea de tiempo	</a></br>						           
                    <a href="#" onclick="getTestDetail( ${row.orderid} ,  ${row.examid} , ${row.id} );" >
                       <i class="fa fa-search" style="color:black !important;" ></i>  Detalle</a><br>   
                    <a href="#" onclick="getTestEvents(${row.orderid} ,  ${row.examid} , ${row.id});" >
                       <i class="fas fa-history" style="color:black !important;" ></i>  Eventos	</a></br>              
                      
                  </div>
                </div>       	             	        
          </div>       	             	        
     	</div>`;
}

function inputest(data, type, row, meta) {
  console.log('es cultivio',row);
  if (row.resultType == "select") {
    let select;
    if(row.status == "FIRMADO"){
      select = `<select class="form-control" disabled>`;
    }else{
      select = `<select id="testResult-${row.id}" class="form-control select-resultado">`;
    }

    let opcion = "";
    let selected = false;
    resultOptions.forEach(function (option) {
      if(option == row.result){
        opcion += `<option value="${option}"  selected>${option}</option>`;
        selected = true;
      }else{
        opcion += `<option value="${option}" >${option}</option>`;
      }
    });

    select += `<option value="" ${!selected ? "selected": ""}>Seleccione</option>`;
    select += opcion;

    select +=`</select>`;
    return select;
  } else {
    let valor = "";
    if(row.result !== undefined && row.result != "" && row.result != null){
      valor = row.result;
    }
    return `<input class="form-control input-resultado" id="testResult-${row.id}" value="${valor}" type="text"/>`;
  }
 
}

//respuesta de input
function genInputRespuesta(data, type, row, meta) {

  if (row.cultivo === 'S') {
    return gencultivo(row);
  }
  switch (row.ctrcodigo) {
    case '0':
      return genResTexto(row);
    case 'G':
      return genResGlosa(row);
    case 'GM':
      return genResGlosaMultiple(row);
    case 'N':
      return genResNumerico(row);
    case 'T':
      return genResTexto(row);
    case 'F':
      return genResFormula(row);
    case 'I':
      return genResImagen(row);
    default:
      break;
  }

  handlerMessageError('');
  return '';
}


function gencultivo(row) {
  if (row.resultType == "select") {
    let select;
    if(row.status == "FIRMADO"){
      select = `<select class="form-control" disabled>`;
    }else{
      select = `<select id="testResult-${row.id}" class="form-control select-resultado">`;
    }

    let opcion = "";
    let selected = false;
    resultOptions.forEach(function (option) {
      if(option == row.result){
        opcion += `<option value="${option}"  selected>${option}</option>`;
        selected = true;
      }else{
        opcion += `<option value="${option}" >${option}</option>`;
      }
    });

    select += `<option value="" disabled ${!selected ? "selected": ""}>Seleccione</option>`;
    select += opcion;

    select +=`</select>`;
    return select;
  } else {
    let valor = "";
    if(row.result !== undefined && row.result != "" && row.result != null){
      valor = row.result;
    }
    return `<input class="form-control input-resultado" id="testResult-${row.id}" value="${valor}" type="text"/>`;
  }
 
}



function genResTexto(row) {
  let valor = "";
  if(row.result !== undefined && row.result != null && row.result != ""){
    valor = row.result;
  }
  let rpta = `<input disabled type='text' data-idtest="${row.testid}" data-idexamen="${row.examid}" data-tipo-resultado="T" class='form-control input-resultado' value='${valor}' />`;
  return rpta;
}

/*
  Genera select con el resultado recuperado de la tabla DATOSFICHASEXAMENESTEST 
 */
function genResGlosa(row) {
  const estadoResultado = row.status;

  let rpta = `<select data-idtest="${row.testid}" data-idexamen="${row.examid}"  data-tipo-resultado="G" class='form-control select-resultado' data-value= "${row.result}" data-obligado="${row.resultadoobligado}" data-omision="${row.resultadomision}">    </select>`;

  if (estadoResultado === 'INGRESADO' || estadoResultado === 'FIRMADO') {
    rpta = `<select disabled data-idtest="${row.testid}" data-idexamen="${row.examid}"  data-tipo-resultado="G" class='form-control select-resultado' data-value= "${row.result}">
   </select>`;
  }

  if ((row.result === null || row.result === '') && row.resultadoobligado === 'S') {
    row.result = row.resultadomision;
    rpta = `<select selected data-idtest="${row.testid}" data-idexamen="${row.examid}"  data-tipo-resultado="G" class='form-control select-resultado' data-value= "${row.result}">
   </select>`;
  }

  return rpta;
}

function genResGlosaMultiple(row) {
  const estadoResultado = row.status;

  let valor = "";
  let rpta;
  if(row.result !== undefined && row.result != null){
    let substrings = row.result.split(",").slice(0, 2).join(",");
    valor = substrings;

    rpta = `<button data-norden="${row.orderid}" data-idtest="${row.testid}" data-idexamen="${row.examid}"  data-tipo-resultado="GM" class='form-control' title="${row.result}" data-value= "${row.result}" style="font-size:.9rem;">${valor}</button>`;
  }else{
    rpta = `<button data-norden="${row.orderid}" data-idtest="${row.testid}" data-idexamen="${row.examid}"  data-tipo-resultado="GM" class='form-control' data-value= "${row.result}">Ver resultados</button>`;
  }

  

  return rpta;
}



function genResNumerico(row) {

  const estadoResultado = row.status;
  let nroFormateado = (row.result === null || row.result === 'null') ? '' : row.result;
  let rpta = `<input type='text' data-valor="${row.result}"  data-idtest="${row.testid}" data-idexamen="${row.examid}" data-tipo-resultado="N" data-n-decimales="${row.decimales}" class='form-control input-resultado' value='${nroFormateado}'/>`;
  //Se añade condición si el usuario no tiene permiso para editar resultados críticos --> Marco Caracciolo 5/12/5022
  if (estadoResultado === 'INGRESADO' || estadoResultado === 'FIRMADO' || ($("#editaResultadosCriticos").length && $("#editaResultadosCriticos").val() == "N")) {
    rpta = `<input disabled type='text' data-valor="${row.result}" data-idtest="${row.testid}" data-idexamen="${row.examid}" data-tipo-resultado="N" class='form-control input-resultado' value='${nroFormateado} '/>`;
  }

  return rpta;
}

function genResFormula(row) {
  //Se añade condición si el usuario no tiene permiso para editar resultados críticos --> Marco Caracciolo 5/12/5022
  let rpta = '';
  let valor = "";
  if(row.result !== undefined && row.result != null && row.result != ""){
    valor = row.result;
  }

  if ($("#editaResultadosCriticos").length && $("#editaResultadosCriticos").val() == "N") {
    rpta = `<input disabled type='text' data-idtest="${row.testid}" data-idexamen="${row.examid}"  data-tipo-resultado="F"  class='form-control input-resultado' value='${valor}'/>`;
  } else {
    rpta = `<input type='text' data-idtest="${row.testid}" data-idexamen="${row.examid}"  data-tipo-resultado="F"  class='form-control input-resultado' value='${valor}'/>`;
  }
  return rpta;
}

function genResImagen(row) {
  let valor = "";
  if(row.result !== undefined && row.result != null && row.result != ""){
    valor = row.result;
  }
  let rpta = `<input  disabled type='file' data-idtest="${row.testid}" data-idexamen=""${row.examid}"  data-tipo-resultado="I" class='form-control input-resultado' value='${valor}'/>`;
  return rpta;
}

//repuesta input


function estadotest(data, type, row, meta) {
  const color = estadoTestYResultadosColorMap[row.status];
  const estado = row.status;
  console.log("row.status",row.status)
  if (estado !== "PENDIENTE" && estado !== "BLOQUEADO") {
    return `<span class="label label-lg font-weight-bold label-inline estadoTestBadge-${color}" >${row.status}</span>`;
    //Cuando la muestra está rechazada o está tomada y recepcionada
  } else {
    return cargarEstadosTestYResultados(estado, row);
  }
}

function cargarEstadosTestYResultados(estado, row) {
  const color = estadoTestYResultadosColorMap[estado];
  let select = `<select id="selectEstadoTestResultados${row.testid}" class="form-control select-estado estadoTestBadge-${color}" >`;
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

$('#resultadosExamenesOrdenesDatatable').on( 'change', '.select-resultado', function () {
  let table = tableResultadosExamenesOrden;
  let row = table.row($(this).closest('tr'));
  let data = row.data();
  let texto = "";
 
  console.log('datadatat',table);
  console.log('this',$(this).find("option:selected").text());
  console.log('data',data);
  data.result = $(this).find("option:selected").text();
  data.status = "DIGITADO";


  console.log(data);
  /*añadido por marco caracciolo */
  if(texto != "Seleccione" ){
    let postData = JSON.stringify({
      "df_NORDEN": data.orderid,
      "dfe_IDEXAMEN": data.examid,
      "dfet_IDTEST": data.id,
      "dfet_RESULTADO": $(this).find("option:selected").val(),
      "ctr_CODIGO": data.ctrcodigo,
      "textResultado" : $(this).find("option:selected").text()

    });
    $.ajax({
      type: "POST",
      url: gCte.getctx + "/api/orden/update/examen/test/resultado",
      data: postData,
      success: fnExito,
      error: fracaso,
      contentType: 'application/json; charset=utf-8'
    });
  }
  /**/
  /*
  $.ajax({
    url: `${gCte.getctx}/api/microbiologia/updateTestList`,
    method: 'POST',
    data:JSON.stringify(data),
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    success: function(datos) {
      if(datos.status !== undefined && datos.status == 200){
        console.log('entra aca',datos.status);
        let recibido = {
          "ctrcodigo":datos.dato.ctrcodigo,
          "cultivo": datos.dato.cultivo,
          "decimales": datos.dato.decimales,
          "estado":datos.dato.estado,
          "examid": datos.dato.examid,
          "id": datos.dato.id,
          "idmicrobiologystatus": datos.dato.idmicrobiologystatus,
          "isculture": datos.dato.isculture,
          "idstatus": datos.dato.idstatus,
          "labelcode": datos.dato.labelcode,
          "microbiologystatus": datos.dato.microbiologystatus,
          "orderid": datos.dato.orderid,
          "result": datos.dato.result,
          "resultadomision":  datos.dato.resultadomision,
          "resultadoobligado": datos.dato.resultadoobligado,
          "resultadonumerico": datos.dato.resultadonumerico,
          "resulttype":datos.dato.resulttype,
          "samplecode": datos.dato.samplecode,
          "status":  datos.dato.status,
          "test": datos.dato.test,
          "testid": datos.dato.testid,
        }
        
        row.data(recibido);
        table.draw(false);
      }
    }
  });
  */
});
$('#resultadosExamenesOrdenesDatatable').on( 'blur', '.input-resultado', function () {
  let table = tableResultadosExamenesOrden;
  let row = table.row($(this).closest('tr'));
  let data = row.data();
  let url =""
 
  console.log('datadatat',table);
  console.log('this',$(this).val());
  console.log('data',data);
  let recentStatus = data.status;
  data.result = String($(this).val());
  data.status = "DIGITADO";

  
  let obligado = "";

  console.log(data);
  
  let rowId = data.orderid.toString().concat('-').concat(data.examid).concat('-').concat(data.id);
  
  let postData = JSON.stringify({
      "df_NORDEN": data.orderid,
      "dfe_IDEXAMEN": data.examid,
      "dfet_IDTEST": data.id,
      "dfet_RESULTADO": data.result,
      "ctr_CODIGO": data.ctrcodigo,

  });
    $.ajax({
      type: "POST",
      url: gCte.getctx + "/api/orden/update/examen/test/resultado",
      data: postData,
      success: function(result, status, xhr){
        let respuesta = result;
        if (respuesta.status === 200) {    
            tableResultadosExamenesOrden.ajax.reload(null, false);
          //tableResultadosExamenesOrden.row("#".concat(rowId)).data(result.dato.resultadoExamen);

          if (result.dato.resultadoExamen.ctr_CODIGO ==='G'){

            let selectCur = $(`select[data-idtest="${result.dato.resultadoExamen.dfet_IDTEST}"][data-idexamen="${result.dato.resultadoExamen.dfe_IDEXAMEN}"]`);
            const estadoResultado = result.dato.resultadoExamen.cert_DESCRIPCIONESTADOTEST;
              selectCur.val(valor);

            if ((valor === null || valor === '') && obligado === 'S') {
              valor = omision;
              selectCur.val(valor);
            }

            if (estadoResultado === 'INGRESADO' || estadoResultado === 'FIRMADO') {
              selectCur.val(valor);
              selectCur.prop('disabled',true);
            }
            addEventsElements(selectCur);
          }else if(result.dato.resultadoExamen.ctr_CODIGO ==='N'){
            let inputs = $(`input[data-idtest="${result.dato.resultadoExamen.dfet_IDTEST}"][data-idexamen="${result.dato.resultadoExamen.dfe_IDEXAMEN}"]`);
            addEventsElements(inputs);
            actualizaTotal();
          }

          handlerMessageOk(respuesta.message);
        }
        else if (respuesta.status == 503) {
          tableResultadosExamenesOrden.ajax.reload(null, false);
          tableInfoExamenesOrden.ajax.reload(null, false);
          handlerMessageWarning(respuesta.message);
        }
        else {
          handlerMessageError(respuesta.message);
        }
      },
      error: fracaso,
      contentType: 'application/json; charset=utf-8'
    });
  
  
 /* 
  $.ajax({
    url: `${gCte.getctx}/api/microbiologia/updateTestList`,
    method: 'POST',
    data:JSON.stringify(data),
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    success: function(datos) {
      if(datos.status !== undefined && datos.status == 200){
        let recibido = {
          "ctrcodigo":datos.dato.ctrcodigo,
          "cultivo": datos.dato.cultivo,
          "decimales": datos.dato.decimales,
          "estado":datos.dato.estado,
          "examid": datos.dato.examid,
          "id": datos.dato.id,
          "idmicrobiologystatus": datos.dato.idmicrobiologystatus,
          "isculture": datos.dato.isculture,
          "idstatus": datos.dato.idstatus,
          "labelcode": datos.dato.labelcode,
          "microbiologystatus": datos.dato.microbiologystatus,
          "orderid": datos.dato.orderid,
          "result": datos.dato.result,
          "resultadomision":  datos.dato.resultadomision,
          "resultadoobligado": datos.dato.resultadoobligado,
          "resultadonumerico": datos.dato.resultadonumerico,
          "resulttype":datos.dato.resulttype,
          "samplecode": datos.dato.samplecode,
          "status":  datos.dato.status,
          "test": datos.dato.test,
          "testid": datos.dato.testid,
        }
        console.log('result', datos.dato.result)
        row.data(recibido);
        table.draw(false);
      }
    }
  });
*/
});

$('#resultadosExamenesOrdenesDatatable').on( 'change', '.select-estado', function () {
  let table = tableResultadosExamenesOrden;
  let row = table.row($(this).closest('tr'));
  let data = row.data();
  data.status = $(this).find("option:selected").text();
  let url =""
  $.ajax({
    url: `${gCte.getctx}/api/microbiologia/updateTestList`,
    method: 'POST',
    data:JSON.stringify(data),
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    success: function(datos) {
      if(datos.status !== undefined && datos.status == 200){
        console.log('dato recibido',datos);
        let recibido = {
          "ctrcodigo":datos.dato.ctrcodigo,
          "cultivo": datos.dato.cultivo,
          "decimales": datos.dato.decimales,
          "estado":datos.dato.estado,
          "examid": datos.dato.examid,
          "id": datos.dato.id,
          "idmicrobiologystatus": datos.dato.idmicrobiologystatus,
          "isculture": datos.dato.isculture,
          "idstatus": datos.dato.idstatus,
          "labelcode": datos.dato.labelcode,
          "microbiologystatus": datos.dato.microbiologystatus,
          "orderid": datos.dato.orderid,
          "result": datos.dato.result,
          "resultadomision":  datos.dato.resultadomision,
          "resultadoobligado": datos.dato.resultadoobligado,
          "resultadonumerico": datos.dato.resultadonumerico,
          "resulttype":datos.dato.resulttype,
          "samplecode": datos.dato.samplecode,
          "status":  datos.dato.status,
          "test": datos.dato.test,
          "testid": datos.dato.testid,
        }
        row.data(recibido);
        table.draw(false);
      }
    }
  });
 
 
  console.log('datadatat',table);
  console.log('this',$(this).val());
  console.log('data',data);
});



function selecttest(data, type, row, meta) {
  let select;
  if(row.status == "FIRMADO"){
    select = `<select class="form-control" disabled>`;
  }else{
    select = `<select id="testMicrobiologyStatus-${row.testid}" class="form-control select-microbiologia">`;
  }

  // let select = `<select id="testMicrobiologyStatus-${row.testId}" class="form-control select-color" onchange="changeTestDataExamen(${row.id})" >`;
  if(row.microbiologystatus == ""){
    select += "<option value='' disabled  selected>Seleccione</option>";
  }

  for (let key in micro.misMicrobiologia) {
    if (row.microbiologystatus != "" && micro.misMicrobiologia[key] == row.microbiologystatus) {
      select += `<option value="${key}" selected>${micro.misMicrobiologia[key]}</option>`;
    } else {
      select += `<option value="${key}" >${micro.misMicrobiologia[key]}</option>`;
    }
  }



  select += '</select>';
  return select;
}

function getDetailsTest(orderid,examid,id,testSeleccionado,status){
  $.ajax({
    url: `${gCte.getctx}/api/microbiologia/orden/${orderid}/examen/${examid}/test/${id}/getActions`,
    method: 'get',
    dataType: 'json',
    success: function (test) {
      if(test.status !== undefined && test.status == 200){
        $("#idTestCanvasAntibiogramIsolatedNumber").val('1');
        limpiarSelectedDefailts();
        eventoLimpiar();
        eventoLimpiarOpcional();
        eventoLimpiarMetodo();
        limpiarSelectedAnti();
        $("#origenAntibioText").text('');
        if(!$("#panelBiograma").hasClass("d-none")){
          $("#panelBiograma").addClass('d-none');
        }
        testSelected.orderid = orderid;
        testSelected.examid = examid;
        testSelected.testid = id;
        showTestActionsResultados(test.dato,testSeleccionado,status);
        $('#idTestCanvasAntibiogramsAddAntibiotic').attr('disabled', 'disabled');
        $('#idAntibiogramsAddResistanceMethodOptional').attr('disabled', 'disabled');
      }
      // disableSelectedDetails();
    },
    error: function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });


  // $.getJSON("Microbiologia/api/getTestData", { orderid: orderid, examId: examId, testId: id })
  // .done(function (test) {
  //   testSelected.orderid = orderid;
  //   testSelected.examid = examid;
  //   testSelected.testid = id;
  //   showTestActionsResultados(test.actions,test.data.exam);
  // })
  // .fail(function (jqxhr, textStatus, error) {
  //   var err = textStatus + ", " + error;
  //   console.log("Request Failed: " + err);
  // });
}

//rellena datos
function showTestActionsResultados(actions,exam,status) {
  console.log('mldshow',actions,exam,status);

  $("#idTestCanvasActionsSample").val(actions[0].sampletype);

  $("#idTestCanvasActionsBodyPart").val(actions[0].bodypart);

  $("#idTestCanvasActionsSample").val(actions[0].sampletype);

  $("#idTestCanvasActionsBodyPart").val(actions[0].bodypart);

  $("#idTestCanvasActionsResultDate").val(actions[0].resultdate);
  
  $("#idTestCanvasActionsResultTime").val(actions[0].resulttime);
  
  $("#idTestCanvasActionsAlertTime").val(actions[0].alertyime);
  
  if(status != "FIRMADO"){
    $('#idTestCanvasActionsSample').removeAttr('disabled');
    $('#idTestCanvasActionsBodyPart').removeAttr('disabled');
    $('#idTestCanvasActionsSample').removeAttr('disabled');
    $('#idTestCanvasActionsBodyPart').removeAttr('disabled');
    $('#idTestCanvasActionsResultDate').removeAttr('disabled');
    $('#idTestCanvasActionsResultTime').removeAttr('disabled');
    $('#idTestCanvasActionsAlertTime').removeAttr('disabled');
    $('#fechaSiembra').removeAttr('disabled');
  }else{
    $('#idTestCanvasActionsSample').attr("disabled", "disabled");
    $('#idTestCanvasActionsBodyPart').attr("disabled", "disabled");
    $('#idTestCanvasActionsSample').attr("disabled", "disabled");
    $('#idTestCanvasActionsBodyPart').attr("disabled", "disabled");
    $('#idTestCanvasActionsResultDate').attr("disabled", "disabled");
    $('#idTestCanvasActionsResultTime').attr("disabled", "disabled");
    $('#idTestCanvasActionsAlertTime').attr("disabled", "disabled");
    $('#fechaSiembra').attr("disabled", "disabled");
  }
  if(actions[0].seedingdate != null && actions[0].seedingdate != ""){
    $('#fechaSiembra').prop('checked', true);
    let datetimeS = moment(actions[0].seedingtime, 'HH:mm:ss');
    let seedingtime = datetimeS.format('HH:mm');

    $("#idTestCanvasActionsSeedingTime").val(seedingtime);
    $("#idTestCanvasActionsSeedingDate").val(actions[0].seedingdate);
    
    if(status != "FIRMADO"){
      $('#idTestCanvasActionsSeedingTime').removeAttr('disabled');
      $('#idTestCanvasActionsSeedingDate').removeAttr('disabled');
    }else{
      $('#idTestCanvasActionsSeedingTime').attr("disabled", "disabled");
      $('#idTestCanvasActionsSeedingDate').attr("disabled", "disabled");

    }
  }

  $('#fechaResiembra').removeAttr('disabled');
  console.log('is resending compadre',actions[0].reseedingdate == null || actions[0].reseedingdate == "")
  if(actions[0].reseedingdate != null && actions[0].reseedingdate != ""){
    $('#fechaResiembra').prop('checked', true);
    let datetimeR = moment(actions[0].reseedingtime, 'HH:mm:ss');
    let reseedingtime = datetimeR.format('HH:mm');

    $("#idTestCanvasActionsReseedingDate").val(actions[0].reseedingdate);
    $("#idTestCanvasActionsReseedingTime").val(reseedingtime);
    
    if(status != "FIRMADO"){
      $('#idTestCanvasActionsReseedingTime').removeAttr('disabled');
      $('#idTestCanvasActionsReseedingDate').removeAttr('disabled');
    }else{
      $('#idTestCanvasActionsReseedingTime').attr("disabled", "disabled");
      $('#idTestCanvasActionsReseedingDate').attr("disabled", "disabled");

    }
  }
  $("#testSelectedText").text(exam);
}

function disableSelectedDetails(){


  $("#idTestCanvasAntibiogramIsolatedNumber").val('1');
  $('#fechaSiembra').prop('checked', false);
  $('#fechaSiembra').attr('disabled');


  $('#fechaResiembra').prop('checked', false);
  $('#fechaResiembra').attr('disabled');

  $("#testSelectedText").text("");

  $("#idTestCanvasActionsSample").val("");
  $('#idTestCanvasActionsSample').attr('disabled');

  $("#idTestCanvasActionsBodyPart").val("");
  $('#idTestCanvasActionsBodyPart').attr('disabled');

  $("#idTestCanvasActionsSeedingDate").val("");
  $('#idTestCanvasActionsSeedingDate').attr('disabled');

  $("#idTestCanvasActionsSeedingTime").val("");
  $('#idTestCanvasActionsSeedingTime').attr('disabled');

  $("#idTestCanvasActionsReseedingDate").val("");
  $('#idTestCanvasActionsReseedingDate').attr('disabled');

  $("#idTestCanvasActionsReseedingTime").val("");
  $('#idTestCanvasActionsReseedingTime').attr('disabled');

  testSelected.orderid = "";
  testSelected.examid = "";
  testSelected.testid = "";

  limpiarSelectedDefailts();
  $("#origenAntibioText").text('');
      eventoLimpiar();
      eventoLimpiarOpcional();
      eventoLimpiarMetodo();
      limpiarSelectedAnti();
      if(!$("#panelBiograma").hasClass("d-none")){
        $("#panelBiograma").addClass('d-none');
      }
}

function limpiarSelectedDefailts(){

  $('#fechaSiembra').prop('checked', false);

  $('#fechaResiembra').prop('checked', false);

  $("#testSelectedText").text("");
  $("#idTestCanvasActionsSample").val("");
  $("#idTestCanvasActionsBodyPart").val("");
  $("#idTestCanvasActionsSeedingDate").val("");
  $("#idTestCanvasActionsSeedingTime").val("");
  $("#idTestCanvasActionsReseedingDate").val("");
  $("#idTestCanvasActionsReseedingTime").val("");

  $("#idTestCanvasActionsSeedingDate").attr('disabled', 'disabled');
  $("#idTestCanvasActionsSeedingTime").attr('disabled', 'disabled');

  $("#idTestCanvasActionsReseedingDate").attr('disabled', 'disabled');
  $("#idTestCanvasActionsReseedingTime").attr('disabled', 'disabled');

  testSelected.orderid = "";
  testSelected.examid = "";
  testSelected.testid = "";
}


function changeTestDataExamen(id) {
  localSetCurrentTestId(id);
  $.getJSON("Microbiologia/api/getTestData", { orderId: localReadCurrentOrderId(), examId: localReadCurrentExamId(), testId: id })
    .done(function (test) {
      localStoreGlobalTest(test);
      localStoreGlobalId(id);
      $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function (antibiogramOptions) {
          $.getJSON("Microbiologia/api/getTestMOList", { orderId: localReadCurrentOrderId(), examId: localReadCurrentExamId(), testId: id })
          .done(function (testMOList) {
            localStoreTestParameters(localReadCurrentOrderId(), localReadCurrentExamId(), id, test.data.patientId);
            localStoreTestMOList(testMOList);
            localStoreAntibiogramOptions(antibiogramOptions)
            localStoreAGOptions({});
            var AGValuesForDB = JSON.parse(JSON.stringify(test.antibiogram));
            localStoreAntibiogram(test.antibiogram);
            localStoreDBAntibiogram(AGValuesForDB);
            localStoreGlobalFlag(true);
            addAGOptionsToDataTest();
            saveEstadoTest();
          })

          .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
          });

          $('#resultadosExamenesOrdenesDatatable').DataTable().ajax.reload();

        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}


function saveEstadoTest() {
  let data = {
    orderId: localReadCurrentOrderId(),
    examId: localReadCurrentExamId(),
    testId: localReadCurrentTestId(),
    result: $("#testResult-" + localReadCurrentTestId()).val(),
    microbiologystatus: $("#testMicrobiologyStatus-" + localReadCurrentTestId()).val(),
    sampleType: $("#idTestCanvasActionsSample").val(),
    bodyPart: $("#idTestCanvasActionsBodyPart").val(),
    seedingDate: $("#idTestCanvasActionsSeedingDate").val() + " " + $("#idTestCanvasActionsSeedingTime").val(),
    reseedingDate: $("#idTestCanvasActionsReseedingDate").val() + " " + $("#idTestCanvasActionsReseedingTime").val(),
    resultDate: $("#idTestCanvasActionsResultDate").val() + " " + $("#idTestCanvasActionsResultTime").val(),
    alertTime: $("#idTestCanvasActionsAlertTime").val()
  }

  
  $.ajax({
    url: "Microbiologia/api/putTestData",
    data: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json'
    },
    method: 'put',
    dataType: 'json',
    success: function (test) {
      $.getJSON("Microbiologia/api/getExamData", { orderId: localReadCurrentOrderId(), examId: localReadCurrentExamId() })
        .done(function (exam) {
          showTestAntibiograma(test);
        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    },
    error: function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });
}

$('#resultadosExamenesOrdenesDatatable').on( 'change', '.select-microbiologia', function (e) {
  e.preventDefault();
  let table = tableResultadosExamenesOrden;
  let row = table.row($(this).closest('tr'));
  let data = row.data();
 
  console.log('datadatat',table);
  console.log('this',$(this).find("option:selected").text());
  console.log('data',data);

  data.microbiologystatus = $(this).find("option:selected").text();

  $.ajax({
    url: `${gCte.getctx}/api/microbiologia/updateTestList`,
    method: 'POST',
    data:JSON.stringify(data),
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    async:false,
    success: function(datos) {
      if(datos.status !== undefined && datos.status == 200){
        let recibido = {
          "ctrcodigo":datos.dato.ctrcodigo,
          "cultivo": datos.dato.cultivo,
          "decimales": datos.dato.decimales,
          "estado":datos.dato.estado,
          "examid": datos.dato.examid,
          "id": datos.dato.id,
          "idmicrobiologystatus": datos.dato.idmicrobiologystatus,
          "isculture": datos.dato.isculture,
          "idstatus": datos.dato.idstatus,
          "labelcode": datos.dato.labelcode,
          "microbiologystatus": datos.dato.microbiologystatus,
          "orderid": datos.dato.orderid,
          "result": datos.dato.result,
          "resultadomision":  datos.dato.resultadomision,
          "resultadoobligado": datos.dato.resultadoobligado,
          "resultadonumerico": datos.dato.resultadonumerico,
          "resulttype":datos.dato.resulttype,
          "samplecode": datos.dato.samplecode,
          "status":  datos.dato.status,
          "test": datos.dato.test,
          "testid": datos.dato.testid,
        }
        row.data(recibido);
        table.draw(false);
        console.log("resultado actualizado")
        //gBusquedaPacientes.bo_OrdenesTable.reloadOrdenes() ;
        table.ajax.reload(null, false);
        //$(this).closest('tr').find(".chck-test").prop("checked", true);
      }
      //$(this).closest('tr').find(".chck-test").prop("checked", true);
      // changeTestDataSelect(data.id,datos.orderId,datos.examId);

      // $.getJSON("Microbiologia/api/getExamData", { orderId: datos.orderId, examId: datos.examId })
      //   .done(function (exam) {
      //     showTestAntibiograma(test);
      //   })
      //   .fail(function (jqxhr, textStatus, error) {
      //     var err = textStatus + ", " + error;
      //     console.log("Request Failed: " + err);
      //   });
    }
  });

});


function changeTestDataSelect(id,orden = localReadCurrentOrderId(),examId = localReadCurrentExamId()) {
  localSetCurrentTestId(id);
  $.getJSON("Microbiologia/api/getTestData", { orderId: orden, examId:examId, testId: id })
    .done(function (test) {
      localStoreGlobalTest(test);
      localStoreGlobalId(id);
      $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function (antibiogramOptions) {
          $.getJSON("Microbiologia/api/getTestMOList", { orderId: orden, examId: examId, testId: id })
          .done(function (testMOList) {
            localStoreTestParameters(orden, examId, id, test.data.patientId);
            localStoreTestMOList(testMOList);
            localStoreAntibiogramOptions(antibiogramOptions)
            localStoreAGOptions({});
            var AGValuesForDB = JSON.parse(JSON.stringify(test.antibiogram));
            localStoreAntibiogram(test.antibiogram);
            localStoreDBAntibiogram(AGValuesForDB);
            localStoreGlobalFlag(true);
            addAGOptionsToData();
          })

          .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
          });

          $('#resultadosExamenesOrdenesDatatable').DataTable().ajax.reload();

        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}


function getTaskOpen(orderId,examId){
  return function(e){
    e.preventDefault();
    window.location.href = `${getctx}/Microbiologia/Tareas?orderId=${orderId}&examId=${examId}`;
  }
}

// function selectRowInfoTest(data) {
//   testDetailTest(data.orderId, data.examId, data.id);
//   console.log('ordenId',data.orderId,'examId',data.examId,'id',data.id)
// }





function getTestDetail(idOrden, idExamen, idTest) {
  $("#detallesTestModal").modal('show');
  detailTest(idOrden, idExamen, idTest);
}


function detailTest(idOrden, idExamen, idTest) {

  $.getJSON("Microbiologia/api/getTestData", { orderId: idOrden, examId: idExamen, testId: idTest })
    .done(function(test) {
      $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function(antibiogramOptions) {
          showTestAntibiograma(test);
        })
        .fail(function(jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}


function showTestAntibiograma(test, enableABG) {
  showTestData(test.data);
  showTestEvents(test.events);
  showTestNotification(test.notification);
}


function showTestData(data) {
  $("#idTestCanvasDataName").empty(data.name);
  $("#idTestCanvasDataName").append(data.name);
  $("#idTestCanvasDataExam").text(data.exam);
  $("#idTestCanvasDataSample").text(data.sample);
  $("#idTestCanvasDataResultType").text(data.resultType);
  $("#idTestCanvasDataCode").text(data.code);
  $("#idTestCanvasDataPrefix").text(data.prefix);
  $("#idTestCanvasDataResult").text(data.result);
  $("#idTestCanvasDataCriticalValue").text(data.criticalValue);
  $("#idTestCanvasDataBodyPart").text(data.bodyPart);
  $("#idTestCanvasDataStatus").text(data.status);
}

function showTestEvents(events) {
  $("#idTestCanvasEventsTable").empty();
  events.forEach(function(event) {
    $("#idTestCanvasEventsTable").append(fillTemplate(eventsRowHtmlTemplate, event));
  })
}

function showRestTestEvents(events) {
  $("#idTestCanvasEventsTable").empty();
  events.dato.forEach(function(event) {
    let tr = fillTemplate(eventsModalRowHtmlTemplate, event);
    console.log(tr);
    $("#idTestCanvasEventsTable").append(tr);
  })
}

function getDataTestNotification() {

  let notification = new Object();
 // let idNotification = $("#notificacionResultadosModal").data("test");
  notification.result = $("#idTestCanvasNotificationsResult").text();
  notification.criticalResult = $("#idTestCanvasNotificationsCriticalResult").text();
  notification.physician = $("#idTestCanvasNotificationsPhysician").text();
  notification.patient = $("#idTestCanvasNotificationsPatient").text();
  notification.patientPhone = $("#idTestCanvasNotificationsPatientPhone").text();
  notification.physicianPhone = $("#idTestCanvasNotificationsPhysicianPhone").text();
  notification.patientEmail = $("#idTestCanvasNotificationsPatientEmail").text();
  notification.physicianEmail = $("#idTestCanvasNotificationsPhysicianEmail").text();
  notification.message = $("#idTestCanvasNotificationsMesage").val();
  notification.nroOrden = $("#nOrden").val();//idNotification.idOrden;
  notification.idExamen = $("#examenId").val(); //idNotification.idExamen;
  notification.idTest = $("#testId").val(); //idNotification.idTest;
  notification.notificationsSendTo = $("#idTestCanvasNotificationsSendTo").val();

  return notification;
}

function showTestNotification(notification) {
  $("#idTestCanvasNotificationsResult").text(notification.result);
  $("#idTestCanvasNotificationsCriticalResult").text(notification.criticalResult);
  $("#idTestCanvasNotificationsPhysician").text(notification.physician);
  $("#idTestCanvasNotificationsPatient").text(notification.patient);
  $("#idTestCanvasNotificationsPatientPhone").text(notification.patientPhone);
  $("#idTestCanvasNotificationsPhysicianPhone").text(notification.physicianPhone);
  $("#idTestCanvasNotificationsPatientEmail").text(notification.patientEmail);
  $("#idTestCanvasNotificationsPhysicianEmail").text(notification.physicianEmail);
}


/*
function notifyTestResultMB(idOrden, idExamen, idTest) {

  let url = gCte.getctx + `/api/orden/${idOrden}/examen/${idExamen}/test/${idTest}`;

  $.ajax({
    type: "get",
    url: url,
    success: function(data) {  
      limpiarTabsNotificacion();
      fillNotificacionResultadosModal(data.dato, idOrden, idExamen, idTest); },
    error: function(data){   alert('Errror');  },
    contentType: 'application/json; charset=utf-8'
  });

  $("#notificacionResultadosModal").modal('show');
}
*/

/*
function fillNotificacionResultadosModal(datos, nOrden, examenId, testId) {


  limpiarTabsNotificacion();
  //if (datos != null) {
    let val = datos.dfet_RCRITICO;
    switch (val) {
      case 'Normal':
        rpta = '';
        break;
      case 'B':
        rpta = '<i class="fas fa-arrow-down text-danger"></i>';
        break;
      case 'CB':
        rpta = '<i class="fas fa-arrow-down text-danger"></i>';
        rpta = rpta + '<i class="fas fa-exclamation-triangle text-danger"></i>';
        break;
      case 'A':
        rpta = '<i class="fas fa-arrow-up text-danger"></i>';
        break;
      case 'CA':
        rpta = '<i class="fas fa-arrow-up text-danger"></i>';
        rpta = rpta + '<i class="fas fa-exclamation-triangle text-danger"></i>';
        break;
      default:
        rpta = '';
        break;
    }




  $("#idTestCanvasNotificationsResult").text(datos.result);
  $("#idTestCanvasNotificationsCriticalResult").html(rpta);

  $("#idTestCanvasNotificationsPhysician").text(datos.cm_NOMBRES);
  $("#idTestCanvasNotificationsPatient").text(datos.dp_NOMBRES);
  $("idTestCanvasNotificationsPhysicianPhone").text(datos.cm_TELEFONO);
  $("#idTestCanvasNotificationsPatientPhone").text(datos.dp_TELEFONO);
  $("#idTestCanvasNotificationsPhysicianEmail").text(datos.cm_EMAIL);
  $("#idTestCanvasNotificationsPatientEmail").text(datos.dp_EMAIL);

  $("#idTestCanvasNotificationsMesage").val(datos.dftn_MENSAJENOTIFICACION);
  $("#idTestCanvasNotificationsSendTo").val(datos.dftn_EMAILENVIO);
  $("#idTestCanvasNotificationsComunicationChannel").val(datos.dftn_IDVIANOTIFICACION);
  $("#idTestCanvasNotificationsCommunicationAttempts").val(datos.dftn_NUMEROINTENTOS);
  $("#idTestCanvasNotificationsNote").val(datos.dftn_NOTA);

  $("#nOrden").val(nOrden);
  $("#examenId").val(examenId);
  $("#testId").val(testid);

}


function limpiarTabsNotificacion() {
  let count = 1;
  $("#myTabNotify").each(function() {
    count++;
    $("#presentacion" + count).remove();
    $("#condicion-sub" + count).remove();
  });
  $(".contenedor-copia").remove();
  $(`#condicion-sub textarea, #condicion-sub input[type="checkbox"], #condicion-sub input[type="text"], #condicion-sub select`).removeAttr('disabled');
  $(`#idTestCanvasNotificationsDate, #idTestCanvasNotificationsUser`).prop('disabled', true);
  $("#condicion-sub").addClass("active show");
  $("#condicion").addClass("active");
  $("#agregar-tab").attr("onclick", "agregarTabs(2)");

  $("#idTestCanvasNotificationsabr").val("");
  $("#idTestCanvasNotificationsDate").val("");
  $("#idTestCanvasNotificationsUser").val("");

  $("#idTestCanvasNotificationsMesage").val("");
  $("#idTestCanvasNotificationsSendTo").val("");
  $("#selectNotificacionTipoReceptor").val("");
  $("#idTestCanvasNotificationsName").val("");
  $("#idTestCanvasNotificationsNote").val("");
  $("#idTestCanvasNotificationsComunicationChannel").val("");
  $("#idTestCanvasNotificationsConfirmsReception").prop("checked", false);
  $("#idTestCanvasNotificationsTypeError").val("");
  $("#idTestCanvasNotificationsErrorNote").val("");
  $("#chckTestCanvasNotificationsError").prop("checked", false);
  $(".testCanvasNotificationsContainer").each(function() {
    if (!$(this).hasClass("ocultar")) {
      $(this).addClass("ocultar");
      $(this).css("display", "none");
    }
  });

  $(".chkErrorContainer").each(function() {
    if (!$(this).hasClass("ocultar")) {
      $(this).addClass("ocultar");
      $(this).css("display", "none");
    }
  });

  $(".typeErrorContainer").each(function() {
    if (!$(this).hasClass("ocultar")) {
      $(this).addClass("ocultar");
      $(this).css("display", "none");
    }
  });

  $(`#condicion`).removeAttr('style');
  $(`#condicion`).removeClass("green-color");

  $("#nOrden").val("");
  $("#examenId").val("");
  $("#testId").val("");
  $("#idFichaTestNotificacion").val("");

}
*/

//replicar lo de captura de resultados
function getTestEvents(idOrden, idExamen, idTest) {
  //$("#eventosTestModal").modal('show');
  $("#buscarEventoOrden").hide();
  $("#buscarEventoTest").show();
  $("#tituloEvento").text("Eventos del Test")
  $("#eventosOrdenModal").modal('show');
// console.log(idOrden + " orden al revisar eventos ****")
  eventTest(idOrden, idExamen, idTest);
}


function eventTest(idOrden, idExamen, idTest) {
  let url = gCte.getctx + '/api/test/events';
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify({ dfetNorden: idOrden, dfetIdexamen: idExamen, dfetIdtest: idTest }),
    success: function(order) {
      showOrderEvents(order.dato)
      //eventosMicrobiologia.showOrderEvents(order.dato);
      /*
      if (order.dato!==null){
          eventosMicrobiologia.showOrderEvents(order.dato);
      } else {
          handlerMessageError("No se encontraron eventos de test")
      }
      */
    },
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
  if(events.status !== undefined && events.status == 200){
    events.dato.forEach(function(event) {
      let tr = fillTemplate(eventsModalRowHtmlTemplate, event);
      $("#idTestCanvasEventsTable").append(tr);
    })
  }
}


$( "#btnAntibiograma" ).on( "click", function() {
  const tests = obtenerResultadosSeleccionados(tableResultadosExamenesOrden);
  if (!tests===false){
    let valida = validaPositivoMicrobiologia(tests);
    if(valida){

      if(tests[0].status == "FIRMADO"){
        $("#idTestCanvasAntibiogramMicroorganism, #idTestCanvasAntibiogramType, #idTestCanvasAntibiogramColoniesCount, #idTestCanvasAntibiogramInfectionType, #idDataTargetFull, #antibiogramaMetodoResistencia, #idDualDataVisualSource, #idSearcher, #clickAddBtn, #clickDelBtn, #idDataTargetOpcional").addClass("disabled-element");

        $("#btnEditarAntibiograma").addClass("d-none");

        $("#accordionPanelSeleccionTest").collapse('show');
      }else{
        $("#idTestCanvasAntibiogramMicroorganism, #idTestCanvasAntibiogramType, #idTestCanvasAntibiogramColoniesCount, #idTestCanvasAntibiogramInfectionType, #idDataTargetFull, #antibiogramaMetodoResistencia, #idDualDataVisualSource , #idSearcher, #clickAddBtn, #clickDelBtn, #idDataTargetOpcional").removeClass("disabled-element");

        $("#btnEditarAntibiograma").removeClass("d-none");

        $("#accordionPanelSeleccionTest").collapse('hide');
      }

      $("#panelBiograma").removeClass('d-none');
      testDetailTest(tests[0].orderid, tests[0].examid, tests[0].id);
      $('html, body').animate({
        scrollTop: $("div#panelBiograma").offset().top
    }, 700);
    }else{
          handlerMessageError("Test debe tener estado de microbiología Positivo para incluir Antibiograma");
      if(!$("#panelBiograma").hasClass("d-none")){
        $("#panelBiograma").addClass('d-none');
      }
    }
  }
});




function validaPositivoMicrobiologia(examenes){
  return Object.keys(examenes).some(function(k) {
    let result = examenes[k].microbiologystatus;
     return result.toUpperCase()==='POSITIVO';
  });
  
}



function testDetailTest(orderId, examId, id){
  let url = gCte.getctx + `/api/microbiologia/getAntibiogramList?nOrder=${orderId}&idExamen=${examId}&idTest=${id}`;


  $.ajax({
    url: url,
    method: 'get',
    dataType: 'json',
    success: function (antibioticos) {
      if(antibioticos.status !== undefined && antibioticos.status == 200){
        // limpiarFormularioAntibiograma();
        //rellenar antibiograma
        objectoNuevo = rellenarObjectoNuevo(antibioticos.dato);
  
        rellenarAntibiograma(antibioticos.dato);
      }

    },
    error: function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });
}

//fimar resultados
$( "#btnFirmaResultados" ).on( "click", function() {
  const examenes = obtenerExamenesSeleccionadosExamenes2(tableResultadosExamenesOrden);

  console.log('voz vboz voz',examenes);
  if (examenes.length === 0 || !examenes) {
    handlerMessageWarning('Seleccionar al menos un test');
    return;
  }
  for (const element of examenes) {
    if (element.status !== "DIGITADO" && element.status !== "TRANSMITIDO") {
      handlerMessageError("Los resultados escogidos deben estar en estado Transmitido o Digitado");
      // tableResultadosExamenesOrden.ajax.reload( null, false );
      return;
    }

  }


  actionListaTest("FIRMAR", examenes);

});

//retirar firma resultados
$( "#btnRetiraFirma" ).on( "click", function() {
  const examenes = obtenerExamenesSeleccionadosExamenes2(tableResultadosExamenesOrden);

  if (examenes.length === 0 || !examenes) {
    handlerMessageWarning('Seleccionar al menos un test');
    return;
  }
  for (const element of examenes) {
    if (element.status !== "FIRMADO") {
      handlerMessageError("Los resultados escogidos deben estar en estado Firmado");
      // tableResultadosExamenesOrden.ajax.reload( null, false );
      return;
    }

  }

  const examenesEstanAutorizados = obtenerExamenesTieneAutorizados(tableResultadosExamenesOrden);

  if(examenesEstanAutorizados){
    handlerMessageError("No se puede retirar la firma ya que existe un examen Autorizado");
    // tableResultadosExamenesOrden.ajax.reload( null, false );
    return;
  }

  actionListaTest("RETIRARFIRMA", examenes);

});

function actionListaTest(action, tests) {

  const ids = []; // arreglo para almacenar las ID de los objetos

  console.log('para firmar',action,tests,typeof tests);





  $.ajax({
    url: `${gCte.getctx}/api/microbiologia/test/action/list/${action}`,
    method: 'PUT',
    headers: {
      "Content-Type": "application/json"
    },
    data: JSON.stringify(tests),
    success: function(datos) {
      if(datos.status !== undefined && datos.status == 200){
        datos.dato.forEach(element => {
            let table = $("#resultadosExamenesOrdenesDatatable").DataTable();
            let row = $(`#resultadosExamenesOrdenesDatatable`).find(`tr#${element.orderid}_${element.examid}_${element.testid}`);
            let tabladata = table.row(row).data();
            tabladata.status = element.status;
            tabladata.checked = "checked";
            table.row(row).data(tabladata).draw();

            $.ajax({
              type: "GET",
              url: `${gCte.getctx}/api/microbiologia/orden/${element.orderid}/examen/${element.examid}/getExamenPorIds`,
              success: function(examen){
                if(examen.status !== undefined && examen.status == 200){
                  console.log("examen",examen)
                  let tableExamen = $("#examenesOrdenesDatatable").DataTable();
                  let rowExamen = $(`#examenesOrdenesDatatable`).find(`tr#${examen.dato.id}`);
                  let tabladataExamen = tableExamen.row(rowExamen).data();
                  tabladataExamen.processstatus = examen.dato.processstatus;
                  tableExamen.row(rowExamen).data(tabladataExamen).draw();
                }
              },
              contentType: 'application/json; charset=utf-8'
            });
            getDetailsTest(tabladata.orderid,tabladata.examid,tabladata.id,tabladata.test,tabladata.status);
        });
        //esto se agrega el ajax cuando este listo el backend
        let text = ids.length > 1 ? `Se ${action == "RETIRARFIRMA" ? "retiraron firmas" : "firmaron"} con exito` : `Se ${action == "RETIRARFIRMA" ? "retiro firma" : "firmo"} con exito` ;
        disableSelectedDetails();
        handlerMessageOk(text);
        
        
        
      }
    }
  });
  // cambiarEstadoExamen(action,tests);

}


function cambiarEstadoExamen(action,tests){
  const results = [];
  tests.forEach(obj => {
    if (obj.hasOwnProperty("examid") && obj.hasOwnProperty("orderid")) {
      const result = {
        "examid": obj["examid"],
        "orderid": obj["orderid"]
      };
      if (!results.some(r => r.examid === result.examid && r.orderid === result.orderid)) {
        results.push(result);
      }
    }
  });



  let estado

  if(action == "DIGITADO"){
    estado = "PENDIENTE"
  }else if(action == "FIRMADO"){
    estado = "FIRMADO"
  }

  results.forEach(result =>{
    $.ajax({
      method: 'GET',
      url: `http://localhost:3002/testsList?orderId=${result.orderid}&examId=${result.examid}`,
      success: function(datos) {
        let consulta = action == "FIRMADO" ? existeTodosAccion(datos,"status",action) : existeAlgunaccion(datos,"status",action);
        console.log(consulta);
        if(consulta){
            let table = $("#examenesOrdenesDatatable").DataTable();
            let row = $(`#examenesOrdenesDatatable`).find(`tr#${result.examid}`);
            let tabladata = table.row(row).data();
            $.ajax({
              method: 'PUT',
              url: `http://localhost:3002/exams/${tabladata.id}`,
              data: {
                cultivo:tabladata.cultivo,
                exam : tabladata.exam,
                estado : tabladata.estado,
                processStatus : estado,
                orderid : tabladata.orderid,
                seedingDate : tabladata.seedingDate,
                reseedingDate : tabladata.reseedingDate,
                urgency : tabladata.urgency,
                id : tabladata.id,
                sample : tabladata.sample,
                bodyPart : tabladata.bodyPart,
                status : tabladata.status,
              },
              success: function(datos) {
                  table.row(row).data(datos).draw();
              }
            });
            tabladata.processStatus = estado;

            console.log('tabla data',tabladata);
            // table.row(row).data(tabladata).draw();
        }
      }
    });
  })


  console.log('results',results);
}

function existeAlgunaccion(array, property, value) {
    for (const obj of array) {
      if (obj.hasOwnProperty(property) && obj[property] === value) {
        return true;
      }
    }
    return false;
}

function existeTodosAccion(array, property, value){
  for (const element of array) {
    if (element[property] !== value) {
      return false;
    }
  }
  return true;
}


  //diferencia de objectos
  function getiguales(leftObjects, rightObjects, property) {
    const result = {};
  
    const propertyValues = Object.keys(leftObjects).map(key => leftObjects[key][property].toString());

    for (const key in rightObjects) {
      const value = rightObjects[key][property].toString();
  
    if (propertyValues.includes(value)) {
        result[key] = rightObjects[key];
      }
    }
    return result;
  }




//formulario superior

$("#fechaSiembra").change(function() { 
  if($(this).is(':checked')){
    $("#idTestCanvasActionsSeedingDate").removeAttr("disabled");
    $("#idTestCanvasActionsSeedingTime").removeAttr("disabled");
    $('#idTestCanvasActionsSeedingDate').datepicker('setDate', nowDate);
    $('#idTestCanvasActionsSeedingTime').datetimepicker('defaultDate', nowTime);
    saveForm(testSelected);
  }else{
    $("#idTestCanvasActionsSeedingDate").val("");
    $("#idTestCanvasActionsSeedingTime").val("");
    $("#idTestCanvasActionsSeedingDate").attr('disabled', 'disabled');
    $("#idTestCanvasActionsSeedingTime").attr('disabled', 'disabled');
    saveForm(testSelected);
  }
});

$("#fechaResiembra").change(function() { 
  if($(this).is(':checked')){
    $("#idTestCanvasActionsReseedingDate").removeAttr("disabled");
    $("#idTestCanvasActionsReseedingTime").removeAttr("disabled");

    $('#idTestCanvasActionsReseedingDate').datepicker('setDate', nowDate);
    $('#idTestCanvasActionsReseedingTime').datetimepicker('defaultDate', nowTime);
    saveForm(testSelected);
  }else{
    $("#idTestCanvasActionsReseedingDate").val("");
    $("#idTestCanvasActionsReseedingTime").val("");
    $("#idTestCanvasActionsReseedingDate").attr('disabled', 'disabled');
    $("#idTestCanvasActionsReseedingTime").attr('disabled', 'disabled');
    saveForm(testSelected);
  }
});


$("#idTestCanvasActionsSample, #idTestCanvasActionsBodyPart").change(function() { 
 if(testSelected.orderid != "" && testSelected.examid != "" && testSelected.testid != ""){
  let isEmpty = false;
  if($("#fechaSiembra").is(':checked') && ($("#idTestCanvasActionsSeedingDate").val() == "" || $("#idTestCanvasActionsSeedingTime").val() == "" )){
    isEmpty = true;
  }
 
  if($("#fechaResiembra").is(':checked') && ($("#idTestCanvasActionsReseedingDate").val() == "" || $("#idTestCanvasActionsReseedingTime").val() == "" )){
      isEmpty = true;
  }

  if(isEmpty){
    handlerMessageWarning("Debe selecciona una fecha y hora");
    return ;
  }

  saveForm(testSelected);
 }
});

$('#idTestCanvasActionsSeedingDate, #idTestCanvasActionsSeedingTime, #idTestCanvasActionsReseedingDate, #idTestCanvasActionsReseedingTime').on('blur', function() {
  let isEmpty = false;
  if($("#fechaSiembra").is(':checked') && ($("#idTestCanvasActionsSeedingDate").val() == "" || $("#idTestCanvasActionsSeedingTime").val() == "" )){
    isEmpty = true;
  }
 
  if($("#fechaResiembra").is(':checked') && ($("#idTestCanvasActionsReseedingDate").val() == "" || $("#idTestCanvasActionsReseedingTime").val() == "" )){
      isEmpty = true;
  }

  if(isEmpty){
    handlerMessageWarning("Debe selecciona una fecha y hora");
    return ;
  }

  saveForm(testSelected);
});




function saveForm(testSelected){

  let fechaseeding;

  if( $("#idTestCanvasActionsSeedingDate").val()){
    const date = moment($("#idTestCanvasActionsSeedingDate").val(), 'DD/MM/YYYY');
    fechaseeding = date.format('DD-MM-YYYY');

  }else{
    fechaseeding = "";
  }
  
  let fechareseeding;

  if( $("#idTestCanvasActionsReseedingDate").val()){
    const date = moment($("#idTestCanvasActionsReseedingDate").val(), 'DD/MM/YYYY');
    fechareseeding = date.format('DD-MM-YYYY');

  }else{
    fechareseeding = "";
  }


  let data = {
    "id": testSelected.testid,
    "orderid":testSelected.orderid,
    "examid": testSelected.examid,
    "testid": testSelected.testid,
    "bodypart":  $("#idTestCanvasActionsBodyPart").val() ? $("#idTestCanvasActionsBodyPart").val() : "",
    "reseedingdate": fechareseeding,
    "reseedingtime": $("#idTestCanvasActionsReseedingTime").val() ? $("#idTestCanvasActionsReseedingTime").val() : "",
    "reseedingchk":$("#fechaResiembra").is(':checked') ? "S" : "",
    "sampletype":$("#idTestCanvasActionsSample").val() ? $("#idTestCanvasActionsSample").val() : "",
    "seedingdate": fechaseeding,
    "seedingtime": $("#idTestCanvasActionsSeedingTime").val() ? $("#idTestCanvasActionsSeedingTime").val() : "",
    "seedingchk":$("#fechaSiembra").is(':checked') ? "S" : "",
    "status":""
  }


  $.ajax({
    url: `${gCte.getctx}/api/microbiologia/updateBodyPart`,
    method: 'POST',
    data:JSON.stringify(data),
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    success: function(datos) {
       if(datos.status == 200){
         console.log(datos,'se cambiaron los datos');
         handlerMessageOk("Datos modificados satisfactoriamente");
   /*
         let table = $("#examenesOrdenesDatatable").DataTable();
         let row = $(`#examenesOrdenesDatatable`).find(`#${datos.dato.id}`);
         let tabladata = table.row(row).data();
   
         if(tabladata !== undefined){
           let exam = {
             "processstatus": tabladata.processstatus,
             "estado": tabladata.estado,
             "orderid": tabladata.orderid,
             "bodypartid":tabladata.bodypartid,
             "sample": datos.dato.sample, //dato que cambia
             "cultivo":tabladata.cultivo,
             "exam": tabladata.exam,
             "processstatus2": tabladata.processstatus2,
             "reseedingdate":datos.dato.reseedingdate != "" && datos.dato.reseedingdate != "-" && datos.dato.seedingdate != null ?   datos.dato.reseedingdate : "", //datos
             "urgency": tabladata.urgency,
             "seedingdate":datos.dato.seedingdate != "" && datos.dato.seedingdate != "-" && datos.dato.seedingdate != null ?  datos.dato.seedingdate: "", //datos
             "id": tabladata.id,
             "bodypart": datos.dato.bodypart, //datos
             "status": tabladata.status,
           }
           
           table.row(row).data(exam).draw();
  
           console.log('exam',exam);
         }*/
         tableInfoExamenesOrden.ajax.reload(null, false);
       } else {
         handlerMessageError(datos.message);
       }
      },
      error: function () {
            console.log("hubo un error")
        }
  });
}


//fin formulario superior


function cleanValue(idOrden, idExamen, idTest) {

  let url = gCte.getctx + "/api/orden/reset/examen/test/resultado";
  let postData = JSON.stringify({
    "df_NORDEN": idOrden,
    "dfe_IDEXAMEN": idExamen,
    "dfet_IDTEST": idTest,
    "dfet_RESULTADO": ""
  });
  $.ajax({
    type: "POST",
    url: url,
    data: postData,
    success: function(result){
        //revisar
        if(result.status === 200){
            tableResultadosExamenesOrden.ajax.reload(null, false);
            handlerMessageOk(result.message);
        } else {
            handlerMessageError(respuesta.message);
        }
    },
    error: fracaso,
    contentType: 'application/json; charset=utf-8'
  });

}

function actualizaTotal(){
	
    $('td input[data-tipo-total="T"]').each(function() {

      let id = $(this).closest('tr').attr('id');
      let valoresSeparados  = id.split('-');
      let nOrden = valoresSeparados[0];
      let idExamen = valoresSeparados[1];
      let idTest = valoresSeparados[2];
      let rowId = nOrden.toString().concat('-').concat(idExamen).concat('-').concat(idTest);
      let url = gCte.getctx + "/api/orden/examen/test/formula"; 
      let postData = JSON.stringify({
            "df_NORDEN": nOrden,
            "dfe_IDEXAMEN": idExamen,
            "dfet_IDTEST": idTest,
      });
      $.ajax({
        type: "POST",
        url: url,
        data: postData,
        success: function(result, status, xhr) {
            let respuesta = result;
            if (respuesta.status == 200) {
                tableResultadosExamenesOrden.row("#".concat(rowId)).data(result.dato.resultadoExamen);
                tableResultadosExamenesOrden.ajax.reload(null, false);
            }
        },
        error: function(xhr, status, error){
            console.log(error);
        },
        contentType: 'application/json; charset=utf-8'
      });

    });
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