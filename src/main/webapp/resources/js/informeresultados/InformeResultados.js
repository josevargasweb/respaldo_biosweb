function buscarOrdenes() {
  try {
    let formValidator = new FormValidator();
    let nroOrdenValidator = new NroOrdenValidator("#txtNOrden", 0, 0);
    formValidator.add(nroOrdenValidator);
    let fechaIniMenorFinValidator = new FechaIniMenorFinValidator("#txtFInicio", "#txtFFin");
    formValidator.add(fechaIniMenorFinValidator);
    let lenNombreMinValidator = new LenMinValidator("#txtFiltroNombre", 2);
    formValidator.add(lenNombreMinValidator);
    let lenApellidoMinValidator = new LenMinValidator("#txtFiltroNombre", 2);
    formValidator.add(lenApellidoMinValidator);

    let parmOk = formValidator.validate();
    if (parmOk === false) {
      handlerMessageError(formValidator.message);
      return;
    }
    let filtroOrden = new FiltroOrden();
    filtroOrden.fill("#txtNOrden", "#txtFInicio", "#txtFFin", "#txtFiltroNombre", "#txtFiltroApellido",
      "#selectTipoDocumentoFiltro", "#txtNDocumento", "#selectTipoAtencionFiltro",
      "#selectServicio", "#selectServicio");
    getOrdenes(filtroOrden);
  }
  catch (error) {
    handlerMessageError(error);
  }
}

function getOrdenes(filtro) {
  datos = filtro;
  tableInfoResultados.ajax.reload().draw(); // con POST
}

tableInfoResultados = $('#kt_datatable').DataTable({
  ajax: {
    url: UrlBase + "/api/ordenes",
    contentType: "application/json",
    type: "POST",
    dataSrc: "dato",
    processData: false,
    data: function(d) {

      return JSON.stringify(datos);
    }
  },
  autoWidth: true,
  colReorder: true,
  select: {
    style: 'single',
    className: 'row-selected',

  },
  language: {
    infoEmpty: "No hay datos.",
    "zeroRecords": "No se encontraron resultados",
    "sSearch": "Buscar:",
    "info": "Registros desde _START_ al _END_ de _TOTAL_ registros",
    "lengthMenu": "Mostrando _MENU_ registros",
    "infoFiltered": "(filtrado de un total de _MAX_ registros)",
    "loadingRecords": "Cargando...",
    "emptyTable": "No hay datos disponibles",
  },
  scrollY: '50vh',
  scrollX: true,
  scrollCollapse: true,
  searching: false,
  "lengthChange": false,
  columns: [
    { "data": "sdf_FECHAORDEN" },
    { "data": "df_NORDEN" },
    { "data": "dp_NOMBRES" },
    { "data": "ldvtd_DESCRIPCION" },
    { "data": "dp_NRODOCUMENTO" },
    { "data": "cta_DESCRIPCION" },
    { "data": "cp_DESCRIPCION" },
    { "data": "cs_DESCRIPCION" },
    { "data": "dp_EMAIL" },
    { "data": "dp_IDPACIENTE" }
  ]
});

tableInfoResultados.on('select', function(e, dt, type, indexes) {
  if (type === 'row') {
    var data = tableInfoResultados.rows(indexes).data();
    fillOrdenData(data);
    $("#collapseInformeResultado").collapse('hide')
    tableIterator = new TableIterator(tableInfoResultados);
    tableIterator.setIndex(indexes);
  }
});

function fillOrdenData(data) {
  let ordenSeleccionada = JSON.parse(JSON.stringify(data[0]))
  nroOrdenSeleccionada = data[0].df_NORDEN;
  setUserData(ordenSeleccionada);
  setOrdenData(ordenSeleccionada);
  loadInfoExamenesOrden(nroOrdenSeleccionada);
  gBusquedaPacientes.bo_OrdenesTable.positionOrden(nroOrdenSeleccionada);
};

function setUserData(ordenSeleccionada) {
  let nombrePaciente;
  if (ordenSeleccionada.dp_NOMBRES != null) {
    nombrePaciente = ordenSeleccionada.dp_NOMBRES + ' ' + ordenSeleccionada.dp_PRIMERAPELLIDO;
    if (ordenSeleccionada.cm_SEGUNDOAPELLIDO != null) {
      nombrePaciente += ' ' + ordenSeleccionada.dp_SEGUNDOAPELLIDO;
    }
  }
  $("#datosPacienteModalNombre").text(nombrePaciente);
  $("#datosPacienteModalSexo").text(ordenSeleccionada.ldvs_DESCRIPCION);
  $("#datosPacienteModalEdad").text(DD_MM_YYYY2ANNOS(ordenSeleccionada.sdp_FNACIMIENTO, "DD-MM-YYYY"));
  $("#datosPacienteModalFNacimiento").text(DD_MM_YY2DD_MM_YY(ordenSeleccionada.sdp_FNACIMIENTO, '-', '/'));
  $("#datosPacienteModalEmail").text(ordenSeleccionada.dp_EMAIL);
  $("#datosPacienteModalServicio").text(ordenSeleccionada.cs_DESCRIPCION);
  $("#datosPacienteModalLocalizacion").text(ordenSeleccionada.cp_DESCRIPCION);
  $("#datosPacienteModalTipoAtencion").text(ordenSeleccionada.cta_DESCRIPCION);
  $("#datosPacienteModalObservacion").text(ordenSeleccionada.dp_OBSERVACION);
  $("#registroRecepcionModalNombre").text(ordenSeleccionada.dp_NOMBRES + ' ' + ordenSeleccionada.dp_PRIMERAPELLIDO + ' ' + ordenSeleccionada.dp_SEGUNDOAPELLIDO);
  $("#registroRecepcionModalRUT").text(ordenSeleccionada.dp_NRODOCUMENTO);
  $("#idModalSvcEmail").text(ordenSeleccionada.dp_EMAIL);
  $("#idModalPacienteEmail").val(ordenSeleccionada.dp_EMAIL);
  $("#idModalPacienteNombre").val(nombrePaciente);
}

function setOrdenData(ordenSeleccionada) {
  let nombreMedico;
  if (ordenSeleccionada.cm_NOMBRES != null) {
    nombreMedico = ordenSeleccionada.cm_NOMBRES + ' ' + ordenSeleccionada.cm_PRIMERAPELLIDO;
    if (ordenSeleccionada.cm_SEGUNDOAPELLIDO != null) {
      nombreMedico += ' ' + ordenSeleccionada.cm_SEGUNDOAPELLIDO;
    }
  }
  let nombrePaciente;
  if (ordenSeleccionada.dp_NOMBRES != null) {
    nombrePaciente = ordenSeleccionada.dp_NOMBRES + ' ' + ordenSeleccionada.dp_PRIMERAPELLIDO;
    if (ordenSeleccionada.cm_SEGUNDOAPELLIDO != null) {
      nombrePaciente += ' ' + ordenSeleccionada.dp_SEGUNDOAPELLIDO;
    }
  }

  $("#idDatosCardEmail2").text(ordenSeleccionada.dp_EMAIL);
  $("#datosPacienteOrdenModalNro").text(ordenSeleccionada.df_NORDEN);
  $("#datosPacienteOrdenModalFecha").text(ordenSeleccionada.sdf_FECHAORDEN);
  $("#datosPacienteOrdenModalConvenio").text(ordenSeleccionada.cc_ABREVIADO);
  $("#datosPacienteOrdenModalMedico").text(nombreMedico);
  $("#datosPacienteOrdenModalDiagnostico").text(ordenSeleccionada.cd_DESCRIPCION);
  $("#datosPacienteOrdenModalObservacion").text(ordenSeleccionada.df_OBSERVACION);
  $("#idDatosCardFecha").text(ordenSeleccionada.sdf_FECHAORDEN);
  $("#txtNroOrden").text(ordenSeleccionada.df_NORDEN);
  $("#idDatosCardTipoAtencion").text(ordenSeleccionada.cta_DESCRIPCION);
  $("#idDatosCardEdad").text(DD_MM_YYYY2ANNOS(ordenSeleccionada.sdp_FNACIMIENTO, "DD-MM-YYYY"));
  $("#idDatosCardSexo").text(ordenSeleccionada.ldvs_DESCRIPCION);
  $("#idDatosCardNombre").text(nombrePaciente);
  // dp_EMAIL
}

class BusquedaPacientes {

  boBuscador;
  bo_OrdenesTable;
  tm_MuestrasTable;
  nro_Orden;

  constructor() {
    this.boBuscador = null;
    this.bo_OrdenesTable = null;
    this.tm_MuestrasTable = new MuestraList();
    this.nro_Orden = -1;
  }
}

var gBusquedaPacientes = new BusquedaPacientes();

function clickOnOrden(data) {
  // console.table(data[0]);
  // gEtiquetaImpresion.nro_Orden = data[0].df_NORDEN;
  // gEtiquetaImpresion.tm_MuestrasTable.loadMuestraorden(gEtiquetaImpresion.nro_Orden );
  let ordenSeleccionada = JSON.parse(JSON.stringify(data[0]))
  nroOrdenSeleccionada = data[0].df_NORDEN;
  setUserData(ordenSeleccionada);
  setOrdenData(ordenSeleccionada);
  loadInfoExamenesOrden(nroOrdenSeleccionada);
  selectRowInfoOrdenes(data[0]);
}


    $(document).ready(function() {


      //   const opciones = [
      //     {
      //     //targets: [0,4,5,9,10,11,12,13,14,15,16,19,20,21,22,23,24,26,27,28,29,30], //targets no visibles
      //       targets: [0,4,5,9,10,11,12,13,14,15,16,19,20,21,22,23,24,26,27,28,29],
      //       visible:false,
      //     }
      //   ,{
      //     targets: 2, //fecha formatiada
      //     visible:true,
      //     render: function (data, type, row){
      //        let dia = data.substring(0, 2);
      //       let mes = data.substring(3, 5);
      //       let year = data.substring(8, 10);
      //       let hora = data.substring(11, 16);
      //      return data;
      //     //  return hora + " " + dia + "/" + nombresMeses(mes) + "/" + year;
      //     }
      //   }
      //   ,{
      //     targets: 3, //Estado
      //     visible:true,
      //     render: function (data, type, row){
      //       const color = estadoOrdenColor[row["ldvfet_DESCRIPCION"]];
      //       return `<span class="label label-lg font-weight-bold label-inline estadoOrden-${color}" data-id="${row["df_IDFICHAESTADOTM"]}"  >${row["ldvfet_DESCRIPCION"]}</span>`;
      //     }
          
      //   }
      //   ,{
      //     targets: 7, //Rut formatiado
      //     visible:true,
      //     render: function (data, type, row){
      //       if(row["ldvtd_DESCRIPCION"] == "RUN"){
      //         const nDocumento = cambiarDatoRut(row["dp_NRODOCUMENTO"]);
      //         return nDocumento;
      //       }else{
      //         return row["dp_NRODOCUMENTO"];
      //       }
      //     }
      //   }
      //   ,{
      //     targets: 8, //Nombre completo
      //     visible:true,
      //     render: function (data, type, row){
      //         return data+" "+row['dp_PRIMERAPELLIDO']+" "+(row['dp_SEGUNDOAPELLIDO'] || "");
      //     }
      //   },
      //   {
      //     targets: 14, //Edad
      //     visible:true,
      //     render: function(data, type, full, meta) {
      //         return data!==null ? calcularEdadTM(data) : "";
      //     }
          
      //   },
      // ];
  

      //   gBusquedaPacientes.boBuscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
      //     "#bo_div_fIni",
      //     "#bo_div_fFin",
      //     "#bo_div_nombre",
      //     "#bo_div_apellido",
      //     "#bo_div_tipoDocumento",
      //     "#bo_div_nroDocumento",
      //     "#bo_div_procedencia",
          // "#bo_div_servicio"]);
         //JAN COMENTO ESTO 18-05-23 POR QUE BUGIO IMPRESION gBusquedaPacientes.bo_OrdenesTable = new BiolisDatatableOrdenes('#bo_t_ordenes', gBusquedaPacientes.boBuscador,clickOnOrden,opciones);
        // $("#bo_btnBuscarOrden").click(gBusquedaPacientes.bo_OrdenesTable.doSearch);
        // $("#btnLimpiarFiltro").click(gBusquedaPacientes.bo_OrdenesTable.doLimpiarForm);
      // let columnas =  [0,4,5,9,10,11,12,13,14,15,16,19,20,21,22,23,24,26];
      // const n = gBusquedaPacientes.bo_OrdenesTable.bo_OrdenesDatable.columns().nodes().length;
      // for (let i=0; i<n; i++){
      //     for (let j=0; j<columnas.length; j++){
      //         if (i===columnas[j]){
      //           gBusquedaPacientes.bo_OrdenesTable.bo_OrdenesDatable.column(i).visible(false)
      //         }
      //     }
      // }
      // gBusquedaPacientes.bo_OrdenesTable.bo_OrdenesDatable.column(7).visible(true);
      });


      

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
  let rows = gBusquedaPacientes.bo_OrdenesTable.getRows();
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

function selectRowInfoOrdenes(data, key = false) {
  fillOrdenData2(data, key);
}

function fillOrdenData2(data) {
  let ordenSeleccionada = JSON.parse(JSON.stringify(data))
  nroOrdenSeleccionada = data.df_NORDEN;
  setUserData(ordenSeleccionada);
  setOrdenData(ordenSeleccionada);
  loadInfoExamenesOrden(nroOrdenSeleccionada);
  gBusquedaPacientes.bo_OrdenesTable.positionOrden(nroOrdenSeleccionada);
}