var datos = initParmTableInfoOrdenes(null, dateTimeAddHH(new Date(Date.now()), -1 * ParametrosApp.capturaResultadosWindow), new Date(Date.now()), "", "");
var tableInfoOrdenes = null;
var tableIterator = null;
var nroOrdenSeleccionada = -1;
var gtableInfoExamenesOrden;
var tableResultadosExamenesOrden;
var gFechaNacimientoPaciente;
var gSexoPaciente = 'M';
var servicioSecciones = new CfgSecciones();
var servicioServicios = new CfgServicios();
var servicioTiposAtencion = new CfgTipoAtencion();
var servicioProcedencias = new CfgProcedencia();
var servicioCfgExamenes = new CfgExamen();
var servicioCfgMuestras = new CfgMuestras();
var servicioCfgEnvases = new CfgEnvases();
var servicioCfgEstadosExamenes = new CfgEstadoExamen();
var userData = new Object();
var tipoAtencionColorMap = new Array();
var isTableLoaded = false;
var reloadTable = false;
var intervalID = null;
var fechaIsParm = null;
var gSeccionSeleccionada = "";
var estado = 1;

tipoAtencionColorMap['AMBULATORIO'] = 'ambulatorio';
tipoAtencionColorMap['HOSPITALIZADO'] = 'hospitalizado';
tipoAtencionColorMap['URGENCIA'] = 'urgencia';
tipoAtencionColorMap['NO SE ESPECIFICA'] = 'light';

class filtrosOrdenes extends ManejadorDatos {
  constructor() {
      super();
      this.datosOriginales = [];
  }

  agregarLimpiarDatosOriginales(elementos){
      this.datosOriginales = [];
      this.datosOriginales = [...elementos];
  }

  agregarYLimpiarElementos(elementos){
      this.limpiarArray();
      this.array = [...elementos];
  }

  fiterDataTable(idTabla, datosEnviados, valorBuscado, propiedad) {
      this.agregarYLimpiarElementos(datosEnviados)
      let datosFiltrados = this.filtrarPorValorProiedad(propiedad, valorBuscado);
      let tabla = $(`#${idTabla}`).DataTable();
      tabla.clear().rows.add(datosFiltrados).draw();
  }

  filterFirstData(datosEnviados, objPropValor){
    this.agregarYLimpiarElementos(datosEnviados)
    return this.filtrarPorPropiedadesValores(objPropValor);
  }

  fiterDataIncludeSeccion(idTabla, datosEnviados, objPropValor,valorBuscado) {
    this.agregarYLimpiarElementos(this.filterFirstData(datosEnviados,objPropValor))
    let datosFiltrados = this.filtrarIncludeValorPropiedad('lstSecciones', valorBuscado);

    let tabla = $(`#${idTabla}`).DataTable();
    tabla.clear().rows.add(datosFiltrados).draw();
}

  filterMultipleData(idTabla, datosEnviados, objPropValor){
      this.agregarYLimpiarElementos(datosEnviados)
      let datosFiltrados = this.filtrarPorPropiedadesValores(objPropValor);
      let tabla = $(`#${idTabla}`).DataTable();
      tabla.clear().rows.add(datosFiltrados).draw();
  }
}

let manejadorDatosOrdenes = new filtrosOrdenes();


function initParmTableInfoOrdenes(nOrden, sFIni, sFFin, nombre, apellido) {
  sFIni = convertirDateDD_MM_YYYY_HH_mm_SS(sFIni, '-', ':');
  sFFin = convertirDateDD_MM_YYYY_HH_mm_SS(sFFin, '-', ':');
  return { "nOrden": nOrden, "fIni": sFIni, "fFin": sFFin, "nombre": nombre, "apellido": apellido };
}

function getOrderDetail(idOrden) {
  $("#datosPacienteModal").modal('show');
  orderDetail(idOrden);
  localSetCurrentOrderId(idOrden);
}

function getAntecedentePacientesModal(idOrden) {
  $("#modalAntecedentesPac").modal('show');
  mostrarModalAntecentesPac(idOrden, 0);
}
//cambiado para usar dos botones segun sea orden o test
$("#btnCerrarModalEvento").click(function (){
	$("#textBuscarEvento").val("")
	$("#idTestEvento").val("")
})


$("#buscarEventoOrden").click(function (){	
	let filtro = $("#textBuscarEvento").val()	
	let id = $("#idOrdenEvento").val();
	//let idTest = $("#idTestEvento").val();
	  orderEventsDetail(id, filtro);

})

$("#buscarEventoTest").click(function (){	
	let filtro = $("#textBuscarEvento").val()	
	let id = $("#idOrdenEvento").val();
	let idTest = $("#idTestEvento").val();
	  orderEventsDetail(id, filtro, idTest);

})

$("#btnExcelEvento").click(function(){	
	agregarExcel("Eventos de la Orden")
})

function getOrderEventsDetail(idOrden) {

	$("#tituloEvento").text("Eventos de la Orden: "+ idOrden)	
	$("#buscarEventoOrden").show();
	$("#buscarEventoTest").hide();

  $("#eventosOrdenModal").modal('show');
  orderEventsDetail(idOrden);
 
}
// hasta aca es el cambio ********************
function setFiltroOrden() {
  if (isTableLoaded) {
    let filtroOrden = new FiltroOrden();
    filtroOrden.fill("#nOrden", "#fIni", "#fFin", "#nombre", "#apellido",
      "#tipoDocumento", "#nroDocumento", "#tipoAtencion",
      "#idServicio", "#idServicio");
    return JSON.stringify(filtroOrden);
  }
  else {
    datos = initParmTableInfoOrdenes(null, dateTimeAddHH(new Date(Date.now()), -1 * ParametrosApp.capturaResultadosWindow), new Date(Date.now()), "", "");
    return JSON.stringify(datos);
  }
}


tableInfoOrdenes = $('#ordenesDatatable').DataTable({
  ajax: {
    url: gCte.getctx + "/api2/bordenes/examenes/secciones",
    contentType: "application/json",
    type: "POST",
    data: boBuscador.boFnGetFormData,//setFiltroOrden
    "dataSrc": function(json) {
      let ordenes = json.dato

      const n = ordenes.length;
      for (let i = 0; i < n; i++) {
        let secciones = ordenes[i].lstSecciones;
        let n = 0;
        if (secciones !== null & secciones !== undefined) {
          n = secciones.length;
        }
        if (n === 0) {
          console.log("No hay secciones");
        }
        let tieneSeccionExcluida = false;
        let rpta = '';
        for (let i = 0; i < n; i++) {
          if (secciones[i] === gCte.idSeccionExcluida) {
            tieneSeccionExcluida = true;
          }
          if(!rpta.includes(`${secciones[i]}X`)){
            rpta += `${secciones[i]}X `;
          }
        }
        if (tieneSeccionExcluida === false && n !== 0) {
       		 rpta += `99X`;
        }
        ordenes[i].lstSecciones = rpta.trim();
      }

      let ordenados = ordenes.sort((a, b) => {
        if (a.hayExamenesUrgentes === "S" && b.hayExamenesUrgentes === "N") {
          return -1;
        } else if (a.hayExamenesUrgentes === "N" && b.hayExamenesUrgentes === "S") {
          return 1;
        } else {
          return a.df_NORDEN - b.df_NORDEN;
        }
      });

      console.log("ordenes",ordenados)
   
      manejadorDatosOrdenes.agregarLimpiarDatosOriginales(ordenados)
      
      return ordenados;
    },
    processData: false,
  },
  serverSide: true, 
  "language": {
    "info": "Mostrando _TOTAL_ órdenes ",
    "infoFiltered": "filtradas de un total de _MAX_ ",
    "emptyTable": "No hay datos disponibles",
    "loadingRecords": "Por favor espere - cargando ...",
    "zeroRecords": "No se encontraron datos",
  },
  select: {
    style: 'single',
    className: 'selected',
  },
  pageLength: 5,
  lengthChange: false,
  paging: false,
  searchDelay: 500,
  orderCellsTop: true,
  info: false,
  scrollX: true,
  scrollY: calcularHeight('panelSuperior', ['panelExamanes'], 212),
  columnDefs: [
    { orderable: false, targets: [0,1,2,3,4,5,6,7,8,9
      // ,10
      // ,11
    ] },
    {
      //Orden
      targets: 0,
      render: function ( data, type, row ) {
          return `<div style="width:${t1*6}px">${data}</div>`;
      
      }
    },
    {
    orderable: false,
    targets: [9],
    data: null,
    defaultContent: '',
    className: "notTMClick",
    render: genAccion,
  }, {
    targets: [5],
    render: cambiarColor,
  }, {
    targets: [6],
    render: cambiarTextoSinProcedencia,
  }, {
    targets: [7],
    render: cambiarTextoSinEspecificar,
  }, {
    targets: [8],
    render: cambiarTextoUrgencia,
  }, {
    targets: [1],
    render: generarFecha,
  },
  {
    targets: [2],
    render: generarNombre,
  },
  {
    //Sexo
    targets: 3,
    render: function ( data, type, row ) {
        return `<div style="width:${t1*6}px">${data}</div>`;
    
    }
  },
  {
    //Edad
    targets: 4,
    render: function ( data, type, row ) {
        return `<div style="width:${t1*5}px">${calcularEdadTM(data)}</div>`;
    
    }
  },
  { visible: false, "targets": 
    [
      // 10,
      // 11,
      // 12,
      // 13
    ] 
  },
  ],
  columns: [
    { "data": "df_NORDEN",type:"html"},
    { "data": "sdf_FECHAORDEN"},
    { "data": "dp_NOMBRES" },
    { "data": "ldvs_DESCRIPCION"},
    { "data": "dp_FECHANACIMIENTO"},
    { "data": "cta_DESCRIPCION"},
    { "data": "cp_DESCRIPCION"},
    { "data": "cs_DESCRIPCION"},
    { "data": "dfe_EXAMENESURGENTE"},
    { "": "" },
    // { "data": "lstSecciones" },
    // { "data": "hayExamenesPendientes" },
    // { "data": "hayExamenesUrgentes" },
    // { "data": "df_NORDEN" },
  ],
  "ordering": false,
  "initComplete": function(settings, json) {
    $("#ordenesDatatable_filter").hide();
    let idSeccion = $("#idSeccion").val() == 0 ? "" : $("#idSeccion").val();
    getSec = function() {
      gSeccionSeleccionada = idSeccion;
      $("#seccionSelect").val(idSeccion);
      $("#seccionSelect").selectpicker('render');
      searchSeccion(idSeccion);
    }
    getSeccionesSTEM(servicioSecciones, getSec);
    let rowFilter = $('<tr class="filterOrden"></tr>').appendTo($(tableInfoOrdenes.table().header()));
    this.api().columns().every(function() {
      let columna = this;
      genColFilter(columna.index(), rowFilter);
    });
    let pendiente = "P";
    searchPendiente(pendiente);
    isTableLoaded = true;
  },
  autoWidth: false,
})

function selectRowInfoOrdenes(data, key = false) {

  fillOrdenData(data, key);
  if (tableInfoOrdenes.colClicked !== 9) {
    $("#accordionPanelSeleccionOrden").collapse('hide');
    $("#accordionTestResultados").collapse('hide');
    tableInfoOrdenes.colClicked = -1;
  }
  //  tableResultadosExamenesOrden.clear();
}

tableInfoOrdenes.on('select', function(e, dt, type, indexes) {
  let data = tableInfoOrdenes.rows(indexes).data();
  selectRowInfoOrdenes(data[0]);
})

tableInfoOrdenes.on('click', 'tbody td', function(e) {
  tableInfoOrdenes.colClicked = e.currentTarget._DT_CellIndex.column;
});

$("#seccionSelect").change(function() {
  $(this).selectpicker('refresh');
  const seccion = $(this).val();
  console.log("esta es la seccion *************")
  console.log(seccion )
  gSeccionSeleccionada = seccion;
  searchSeccion(seccion);
  // searchExamenSeccion(seccion);
  gSeccionSeleccionada = seccion;
})

tableInfoOrdenes.on('draw', function() {
  if (tableInfoOrdenes.settings()[0].oFeatures.bServerSide){
    tableInfoOrdenes.settings()[0].oFeatures.bServerSide = false;
    tableInfoOrdenes.draw();
  }

  $('.containerTable').removeClass('hidden');
  $(".spinnerContainer1").remove();

  registros_muestras = tableInfoOrdenes.rows().count();
  //console.log('total rows',registros_muestras);
  let rows_filtroMue = tableInfoOrdenes.rows({ search: 'applied' }).count();
  //console.log('rows count:', rows_filtroMue);
  $("#dt_registros_orden").html("Mostrando <b>" + rows_filtroMue + "</b> de <b>" + manejadorDatosOrdenes.datosOriginales.length + "</b> ordenes en total");
  $("#count_orden_inicio").text(0);
  $("#count_orden_fin").text(rows_filtroMue);
  $("#order-progress").attr("aria-valuenow", 0);
  $("#order-progress").attr("aria-valuemin", 0);
  $("#order-progress").attr("aria-valuemax", rows_filtroMue);
});

function searchSeccion(seccion) {
  buscarTodosfiltros(seccion)
  resetOrdenProgress();
  resetTables();
}

$("#estadoPendiente").change(function() {
  let pendiente = $("#estadoPendiente:checked")[0];
  if (pendiente === undefined) {
    pendiente = "";
  }
  else {
    pendiente = "P";
  }
  searchPendiente(pendiente);
});

function searchPendiente(pendiente) {
  manejadorDatosOrdenes.filterMultipleData('ordenesDatatable',manejadorDatosOrdenes.datosOriginales,{'hayExamenesPendientes':pendiente})
  // let columna = $('#ordenesDatatable').DataTable().column(11);
  // columna.search(pendiente, true, false, false).draw();
}

function genSecciones(data, type, row, meta) {
  //  let data = tableInfoOrdenes.rows(indexes[0].row).data();
  let secciones = row.lstSecciones;
  const n = secciones.length;

  if (n === 0) {
    return "";
  }

  let rpta = '';
  for (let i = 0; i < n; i++) {
    rpta = rpta.concat(secciones[i], 'X', ' ');
  }
  return rpta;
}

function fillOrdenData(data, key = false) {
  console.log('key',key);
  nroOrdenSeleccionada = data.df_NORDEN;
  gFechaNacimientoPaciente = data.sdp_FNACIMIENTO;
  gSexoPaciente = data.ldvs_DESCRIPCION;
  loadInfoExamenesOrden(nroOrdenSeleccionada, key);
  setUserData(data);
  loadEnabledOrdenMedica(nroOrdenSeleccionada)
}

function genAccion(data, type, row, meta) {
  const antecedentesEnabled = row.estadoantecedente === "S" ? false : true;
  // `<div style="width:${t1*5}px"></div>`
  return `
  <div class="btn-toolbar row" style="width:${t1*7}px">
    <a href="#" onclick="getOrderDetail(${row.df_NORDEN});" class="p-2" title="Datos Paciente">
                    <i class="fas fa-user-md"></i>
    </a>
    <a href="#" onclick="getAntecedentePacientesModal(${row.df_NORDEN});"class="p-2 ${antecedentesEnabled ? 'disabled' : ''}" title="Antecedentes">
      <i class="fas fa-notes-medical" ${antecedentesEnabled ? 'style="color:#858383 !important;"' : ''}></i>
    </a>
    <a  href="#" onclick="getOrderEventsDetail(${row.df_NORDEN});"class="p-2" title="Orden/Exámenes">
      <i class="fa fa-search"></i>
    </a>
  </div>
  `;

}

function cambiarColor(data, type, row, meta) {
  const color = tipoAtencionColorMap[row.cta_DESCRIPCION];
  //  const texto = `<span style="font-size:50%;" class="badge badge-${color}" >${row.cta_DESCRIPCION}</span>`;
  const texto = `<div style="width:${t1*9}px"><span class="label label-lg font-weight-bold label-inline badge-${color}" >${row.cta_DESCRIPCION}</span></div>`;
  return texto;
}

function cambiarTextoSinProcedencia(data, type, row, meta) {
  let texto = row.cp_DESCRIPCION;
  if (texto === 'SIN PROCEDENCIA')
    return `<div style="width:${t1*18}px"></div>`;
  return `<div style="width:${t1*18}px">${texto}</div>`;
}

function cambiarTextoSinEspecificar(data, type, row, meta) {
  let texto = row.cs_DESCRIPCION;
  if (texto === null || texto === 'SIN ESPECIFICAR')
    return `<div style="width:${t1*9}px">Sin especificar</div>`;
  return  `<div style="width:${t1*9}px">${texto}</div>`;
}

function cambiarTextoUrgencia(data, type, row, meta) {
  let texto = row.hayExamenesUrgentes;
  if (texto === 'N') {
    return `<div style="width:${t1*6}px"></div>`;
  }
  texto = `<div class="btn-toolbar justify-content-center" style="width:${t1*6}px"><a class="p-2"><i class="fas fa-exclamation-triangle" style="color:red;"></i></a></div>`;
  return texto;
}

function generarFecha(data, type, row, meta) {
  var hora = data.substring(0, 5);
  var dia = data.substring(6, 8);
  var mes = data.substring(9, 11);
  var año = data.substring(12, 16);
  var fechaFormateada = dia + "-" + mes + "-" + año + " " + hora;

  //  return hora + " " + dia + "/" + nombresMeses(mes) + "/" + year;
  return  `<div style="width:${t1*10}px">${fechaFormateada}</div>` ;
}

function generarNombre(data, type, row, meta) {

  let texto = row.dp_NOMBRES;
  if (row.dp_PRIMERAPELLIDO != null)
    texto = texto + ' ' + row.dp_PRIMERAPELLIDO;
  if (row.dp_SEGUNDOAPELLIDO !== undefined && row.dp_SEGUNDOAPELLIDO != null)
    texto = texto + ' ' + row.dp_SEGUNDOAPELLIDO;
  return  `<div style="width:${t1*19}px">${texto}</div>`;
}

function orderDetail(id) {
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


function verOrden() {

  let idOrden = $("#txtNroOrden").text();
  getOrderDetail(idOrden);

}

//cambiado por cristian 09-02-2023 
// esta era la api anterior  Microbiologia/api/getOrderData  ******* 
function orderEventsDetail(id, filtro, idTest) {
	
  localSetCurrentOrderId(id);
  $.getJSON("api/orden/eventos/fichas/", { orderId: id , filtro:filtro, idTest:idTest} )
    .done(function(order) {
      showOrderEvents(order.dato);
      $('#OrderCanvasEvents').addClass('show active');
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function mostrarModalAntecentes(a) {

  let nOrden = a;

  $('#nOrdenAntecedentes').val(nOrden);
  $("#idAntecedentes").empty();
  let url = getctx + "/api/paciente/antecedentes/" + nOrden;

  $.ajax({
    type: "post",
    url: url,
    datatype: "json",
    success: function(antecedentesResponse) {
      if (antecedentesResponse.status !== 200) {
        console.table(antecedentesResponse);
        handlerMessageError(antecedentesResponse.message);
        return;
      }
      let antecedentes = antecedentesResponse.dato;
      fillAntecedentesUser(antecedentes);

    },
    error: function(msg) {
      console.log(msg);
    }
  });

}


function fillAntecedentesUser(antecedentes) {

  for (let ant of antecedentes) {
    switch (ant.ca_CODIGOANTECEDENTE) {
      case 'DIURESIS':
        $("#antecedentesPacienteDiuresis").val(ant.dfa_VALORANTECEDENTE);
        break;
      case 'FIO2':
        $("#antecedentesPacienteFiO2").val(ant.dfa_VALORANTECEDENTE);
        break;
      case 'TEMPERATURA':
        $("#antecedentesPacienteTemperatura").val(ant.dfa_VALORANTECEDENTE);
        break;
      case 'FUR':
        $("#antecedentesPacienteFUR").val(ant.dfa_VALORANTECEDENTE);
        break;
      case 'GESTACIÓN':
        $("#antecedentesPacienteGestante").val(ant.dfa_VALORANTECEDENTE);
        break;
      case 'MEDICACIÓN':
        $("#antecedentesPacienteMedicacion").val(ant.dfa_VALORANTECEDENTE);
        break;
      case 'ULT DOSIS':
        $("#antecedentesPaciente").val(ant.dfa_VALORANTECEDENTE);
        break;
      case 'EDAD':
        $("#antecedentesPacienteEdad").val(ant.dfa_VALORANTECEDENTE);
        break;
      case 'PESO':
        $("#antecedentesPacientePeso").val(ant.dfa_VALORANTECEDENTE);
        break;
      case 'ESTATURA':
        $("#antecedentesPacienteEstatura").val(ant.dfa_VALORANTECEDENTE);
        break;
      default:
        break;
    }
  }
}


function filtrar(e) {
  e.preventDefault();
  var params = {};
  $(rowFilter).find('.datatable-input').each(function() {
    var i = $(this).data('col-index');
    if (params[i]) {
      params[i] += '|' + $(this).val();
    } else {
      params[i] = $(this).val();
    }
  });
  $.each(params, function(i, val) {
    // apply search params to datatable
    table.column(i).search(val ? val : '', false, false);
  });
  table.table().draw();
}

function searchValue(e) {
  let columna = $('#ordenesDatatable').DataTable().column(this.dataset["colIndex"]);
  console.log('searchValue ' + this.dataset["colIndex"]);
  if (this.dataset["colIndex"] === "8" || this.dataset["colIndex"] === "7") {
    let value = this.value;
    // columna = $('#ordenesDatatable').DataTable().column(12);
    // columna.search(value, true, false, false).draw();
    buscarTodosfiltros($("#seccionSelect").val())
  }
  else {
    columna.search(this.value, true, false, false).draw();
  }
  cleanAll();

}

function searchKey(e) {
  let columna = $('#ordenesDatatable').DataTable().column(this.dataset["colIndex"]);
  columna.search(e.target.value.toUpperCase(), true, false, false).draw();
  cleanAll();
}

function setOptionServicios(colIndex) {

  function fillSelect(jqIdSelect) {
    function fill(data) {
      data.forEach(servicio => {
        let opt = new Option(servicio.csDescripcion, servicio.csIdservicio);
        $(jqIdSelect).append($(opt));
      });
      let opt = new Option('TODOS', '', true, true);
      $(jqIdSelect).prepend($(opt));
    }
    return fill;
  }

  let fillSelectServicios = fillSelect(`select[data-col-index="` + colIndex + `"]`)
  getServicios(servicioServicios, fillSelectServicios);
}

function setOptionTiposAtencion(colIndex) {

  function fillSelect(jqIdSelect) {
    function fill(data) {

      data.forEach(tipo => {
        let opt = new Option(tipo.ctaDescripcion, tipo.ctaDescripcion);
        $(jqIdSelect).append($(opt));
      });
      let opt = new Option('TODOS', '', true, true);
      $(jqIdSelect).prepend($(opt));
    }
    return fill;
  }

  let fillSelectTiposAtencion = fillSelect(`select[data-col-index="` + colIndex + `"]`)
  getTiposAtencion(servicioTiposAtencion, fillSelectTiposAtencion);
}

function setOptionUrgencias(colIndex) {
  return '<option selected value="">TODOS</option><option value="N">Normal</option><option value="S">Urgente</option>';
}

function setOptionSelCol(colIndex) {

  switch (colIndex) {
    case 3:
      return setOptionSexo();
    case 7:
      return setOptionServicios(colIndex);
    case 8:
      return setOptionUrgencias(colIndex);
    case 5:
      return setOptionTiposAtencion(colIndex);
    case 6:
      return setOptionProcedencia(colIndex);
    default:
      break;
  }
  return '<option value="">TODOS</option>';
}

function genInputSelect(colIndex,size = 6) {
  return $(`<select class="form-control form-control-sm form-filter datatable-input" data-col-index="` + colIndex + `" style="width:${t1*size}px">` + setOptionSelCol(colIndex) + `</select>`);
}

function genLimpiar(colIndex, rowFilter) {
  let inputLimpiar = $(`<span class="symbol symbol-30 symbol-circle">
                        <span id="btnFilterClean" data-toggle="tooltip" title="Limpiar" class="symbol-label bg-hover-blue ">
                            <i id="" class="la la-broom icon-xl  text-blue"></i>
                        </span>
                      </span>`);
  $(inputLimpiar).appendTo($('<th>').appendTo(rowFilter));
  $("#btnFilterClean").click(limpiarFiltroOrdenes);
}


function genColSelectFilter(colIndex, rowFilter,size = 6) {
  let inputSelect = genInputSelect(colIndex,size);
  $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
  $(`select[data-col-index="` + colIndex + `"]`).change(searchValue);
}


function genColInputFilter(colIndex, rowFilter,size) {
  let inputText = $(`<input type="text" class="form-control form-control-sm form-filter datatable-input" data-col-index="` + colIndex + `" style="width:${t1*size}px"/>`);
  $(inputText).appendTo($('<th>').appendTo(rowFilter));
  $(`input[data-col-index="` + colIndex + `"]`).on('keyup change clear', searchKey);
}

function genColDateFilter(colIndex, rowFilter) {
  let inputText = $(`
                  <div class="input date" style="width:${t1*10}px">
                  <input type="text" class="form-control form-control-sm datatable-input kt_datepicker_1_modal  " readonly placeholder="" id="kt_datepicker_1_modal"
                     data-col-index="` + colIndex + `"/>
                  </div>
                  `);
  $(inputText).appendTo($('<th>').appendTo(rowFilter));
  $('.kt_datepicker_1_modal').datepicker({
    todayHighlight: true,
    orientation: "bottom left",
    language: 'es',
    endDate: "+Infinity",
    format: 'dd-mm-yyyy',
    autoclose: true,
  });
  let today = new Date();
  fecha = convertirDateGuionDDMMYYYY(today);
  $('.kt_datepicker_1_modal').val(fecha);
  $('.kt_datepicker_1_modal').datepicker()
    .on('changeDate', function(e) {

      let columna = $('#ordenesDatatable').DataTable().column(1);
      console.log(e.target.value);
      // let fechas = e.target.value.split("/");
      // columna.search(fechas[0].concat("-").concat(fechas[1]), true, false, false).draw();
      // let currentTime = moment().format("HH:mm:ss");
      // let dateComplete = `${e.target.value} ${currentTime}`;
      // let momentObj = moment(dateComplete, "DD/MM/YYYY HH:mm:ss");
      // let formattedDateTime = momentObj.format("DD-MM-YYYY HH:mm:ss");
      boBuscador.boSearchFechas(e.target.value);
      tableInfoOrdenes.ajax.reload();
      boBuscador.boSearchFechasClean();
      cleanAll();
    });

}


function genNoFilter(colIndex, rowFilter,size = 6) {
  let inputText = $(`<div style="width:${t1*size}px"></div>`);
  $(inputText).appendTo($('<th>').appendTo(rowFilter));
}

function genColFilter(colIndex, rowFilter) {

  switch (colIndex) {
    case 5:
      genColSelectFilter(colIndex, rowFilter,9);
      break;
    case 6:
      genColSelectFilter(colIndex, rowFilter,18);
      break;
    case 7:
      genColSelectFilter(colIndex, rowFilter,9);
      break;
    case 8:
      genColSelectFilter(colIndex, rowFilter,6);
      break;
    case 1:
      genColDateFilter(colIndex, rowFilter);
      break;
    case 0:
      genColInputFilter(colIndex, rowFilter,6);
      break;
    case 2:
      genColInputFilter(colIndex, rowFilter,19);
      break;
    case 3:
      genNoFilter(colIndex, rowFilter,6);
      break;
    case 4:
      genNoFilter(colIndex, rowFilter,5);
      break;
    case 10:
      genLimpiar(colIndex, rowFilter);
      break;
    case 9:
    // case 11:
    // case 12:
    // case 13:
    // case 14:
      // break;
    default:
      genNoFilter(colIndex, rowFilter,6);
      break;
  }
}

function setOptionProcedencia(colIndex) {

  function fillSelect(jqIdSelect) {
    function fill(data) {

      data.forEach(proc => {
        let opt = new Option(proc.cpDescripcion, proc.cpDescripcion);
        $(jqIdSelect).append($(opt));
      });
      let opt = new Option('TODOS', '', true, true);
      $(jqIdSelect).prepend($(opt));
    }
    return fill;
  }

  let fillSelectProcedencia = fillSelect(`select[data-col-index="` + colIndex + `"]`)
  getProcedencias(servicioProcedencias, fillSelectProcedencia);
}

function setOptionSexo() {
  return '<option value="F">Femenino</option><option value="M">M</option><option selected value="">TODOS</option>';
}

function reloadOrdenes(pTableInfoOrdenes, fecha) {

  reloadTable = true;
  fechaIsParm = false;
  if (fecha !== null && fecha !== undefined && fecha !== "") {
    fechaIsParm = true;
  }
  pTableInfoOrdenes.ajax.reload();
}

function setUserData(ordenSeleccionada) {
  console.log("orden seleccionada",ordenSeleccionada.dp_SEGUNDOAPELLIDO != null)
  console.log(ordenSeleccionada)
  let nombreCompleto = ordenSeleccionada.dp_NOMBRES + ' ' + ordenSeleccionada.dp_PRIMERAPELLIDO;
  if (ordenSeleccionada.dp_SEGUNDOAPELLIDO != null) {
    nombreCompleto += ' '+ordenSeleccionada.dp_SEGUNDOAPELLIDO
  }


  $("#txtNombrePaciente").text(nombreCompleto);
  $("#txtSexoPaciente").text(ordenSeleccionada.ldvs_DESCRIPCION);
  $("#txtEdadPaciente").text(ordenSeleccionada.sdp_FNACIMIENTO);
  $("#txtTipoAtencion").text(ordenSeleccionada.cta_DESCRIPCION);
  //****agregado por cristian  09-11 **********
  $("#txtLocalizacion").text(ordenSeleccionada.cs_DESCRIPCION);
  $("#txtProcedencia").text(ordenSeleccionada.cp_DESCRIPCION)
  //*****************************************************
  $("#txtNroOrden").text(ordenSeleccionada.df_NORDEN);
  $("#txtFechaOrden").text(ordenSeleccionada.sdf_FECHAORDEN);
  $("#btnRegDocOrden").prop('disabled', false);
  $("#btnVerOrden").prop('disabled', false);
  positionOrden(ordenSeleccionada.df_NORDEN);
}

function getUserData() {
  $("#txtNombrePaciente").text(ordenSeleccionada.dp_NOMBRES + ' ' + ordenSeleccionada.dp_PRIMERAPELLIDO + ' ' + ordenSeleccionada.dp_SEGUNDOAPELLIDO);
  $("#txtSexoPaciente").text(ordenSeleccionada.ldvs_DESCRIPCION);
  $("#txtEdadPaciente").text(ordenSeleccionada.sdp_FNACIMIENTO);
  $("#txtTipoAtencion").text(ordenSeleccionada.cta_DESCRIPCION);
  //****agregado por cristian  09-11 **********
  $("#txtLocalizacion").text(ordenSeleccionada.cs_DESCRIPCION);
  $("#txtProcedencia").text(ordenSeleccionada.cp_DESCRIPCION)
  //*******************************************************
  $("#txtNroOrden").text(ordenSeleccionada.df_NORDEN);
  $("#txtFechaOrden").text(ordenSeleccionada.sdf_FECHAORDEN);
}

function limpiarTableInfoOrdenes() {
  tableInfoOrdenes.clear();
}


function limpiarUserData() {

  $("#txtNombrePaciente").text("");
  $("#txtSexoPaciente").text("");
  $("#txtEdadPaciente").text("");
  $("#txtTipoAtencion").text("");
  $("#txtLocalizacion").text("");
  $("#txtNroOrden").text("");
  $("#txtFechaOrden").text("");
}

function cleanAll() {
  limpiarUserData();
  limpiarTableResultadosExamenesOrden();
  limpiarTableInfoExamenesOrden();
}

var intervalo = setInterval( function () {
  if(estado === 0){
    tableInfoOrdenes.ajax.reload(); // user paging is not reset on reload
      console.log("actualizando...")
  }
}, 50000 );
// var estado = 0;

// //Actualización automática
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

  searchPendiente(pendiente);
  tableInfoOrdenes.ajax.reload(); // user paging is not reset on reload
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


// $("#idChkAutorefresh").change(function() {

//   let autoResfresh = $("#idChkAutorefresh:checked")[0];

//   if (autoResfresh === undefined) {
//     autoResfresh = false;
//     clearInterval(intervalID);
//   }
//   else {
//     autoResfresh = true;
//     intervalID = setInterval(function() {
//       console.log('recargaTabla');
//       tableInfoOrdenes.ajax.reload();
//     }, 100000);
//   }

//   let pendiente = $("#estadoPendiente:checked")[0];
//   if (pendiente === undefined) {
//     pendiente = "";
//   }
//   else {
//     pendiente = "P";
//   }

//   searchPendiente(pendiente);
// });

function limpiarFiltroOrdenes() {
  resetColSelectFilter();
  resetColInputFilter();
  resetColDateFilter();
  resetOrdenProgress();
}

function resetColSelectFilter() {
  const selCols = [5, 6, 7, 8];
  selCols.forEach((colIndex) => {
    $(`select[data-col-index="` + colIndex + `"]`).val("");
    $(`select[data-col-index="` + colIndex + `"]`).trigger("change")
  });
}

function resetColInputFilter() {
  const inputCols = [0, 2];
  inputCols.forEach((colIndex) => {
    $(`input[data-col-index="` + colIndex + `"]`).val("");
    $(`input[data-col-index="` + colIndex + `"]`).trigger("change")
  });
}

function resetColDateFilter() {
  const dateCols = [1];
  dateCols.forEach((colIndex) => {
    $(`input[data-col-index="` + colIndex + `"]`).val("");
    $(`input[data-col-index="` + colIndex + `"]`).trigger("change")
  });
}
function resetOrdenProgress() {
  $("#order-progress").css('width', "0%");
}

function resetTables() {

  gSeccionSeleccionada = gSeccionSeleccionada.concat("$");
  searchExamenesAnulados(gSeccionSeleccionada);
  searchResultadosAnulados(gSeccionSeleccionada);
  let count = tableInfoExamenesOrden.rows( {search:'applied'} ).length;
//  alert(count);
  if (count === 0){
  tableInfoExamenesOrden.clear().draw();
  tableResultadosExamenesOrden.clear().draw();
  }

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





function loadEnabledOrdenMedica(nOrden){
  let url = `${gCte.getctx}/api/${nOrden}/EstadoDocumento`;
  $.ajax({
    type: "GET",
    url: url,
    success: function(response) {
      if(response.status === 200 && response.dato === 'S'){
        $("#btnRegDocOrden").prop('disabled', false);
      }else{
        $("#btnRegDocOrden").prop('disabled', true);
      }
    },
    error: function(xhr, status, error) {
      $("#btnRegDocOrden").prop('disabled', true);
    }
  });
}

function escapeRegexJa(str) {
  return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

function buscarTodosfiltros(seccion){
  let val2 = escapeRegexJa(seccion).concat('X');


  let orden = $('[data-col-index="0"]').val();
  // let paciente = $('[data-col-index="2"]').val(); 
  let atencion = $('[data-col-index="5"]').val();
  let procedencia = $('[data-col-index="6"]').val();
  let servicio = $('[data-col-index="7"]').val(); // falta agregar
  let urgente = $('[data-col-index="8"]').val();
  let pendiente = $("#estadoPendiente:checked")[0];
  if (pendiente === undefined) {
    pendiente = "";
  }
  else {
    pendiente = "P";
  }

  //falta agregar servicio
  manejadorDatosOrdenes.fiterDataIncludeSeccion('ordenesDatatable',manejadorDatosOrdenes.datosOriginales,{
    'df_NORDEN':orden,
    'cta_DESCRIPCION':atencion,
    'cp_DESCRIPCION':procedencia,
    'hayExamenesUrgentes':urgente,
    'hayExamenesPendientes':pendiente,
    'cs_IDSERVICIO':servicio,
  },val2)
}