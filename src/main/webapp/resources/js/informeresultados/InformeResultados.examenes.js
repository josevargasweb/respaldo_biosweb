

var estadoExamenesColorMap = new Array();
estadoExamenesColorMap['FIRMADO'] = 'firmado';
estadoExamenesColorMap['PENDIENTE'] = 'pendiente';
estadoExamenesColorMap['INGRESADO'] = 'ingresado';
estadoExamenesColorMap['EN PROCESO'] = 'proceso';
estadoExamenesColorMap['BLOQUEADO'] = 'bloqueado';
estadoExamenesColorMap['AUTORIZADO'] = 'autorizado';

var estadoImpresionColorMap = new Array();
estadoImpresionColorMap['ENTREGADO'] = 'entregado';
estadoImpresionColorMap['PENDIENTE'] = 'entregado';
function obtenerExamenesSeleccionados(pTableInfoExamenesOrden) {

  function tuplaExamen() {
    this.nOrden = -1;
    this.idExamen = -1;
    this.ceCodigoExamen = "";
    this.ceAbreviado = "";
    this.ceeDescripcionEstadoExamen = "";
    this.dfe_IMPRESO = "";
    this.sdfeFechaRetiraExamen = "";
  }
  let inputs = $("input[data-colselector]");
  let examenesSeleccionados = new Array();

  const nInputs = inputs.length;

  for (let i = 0; i < nInputs; i++) {

    if (inputs[i].checked) {
      examenesSeleccionados.push(pTableInfoExamenesOrden.row(i).data());
    }
  }


  if (examenesSeleccionados === undefined || examenesSeleccionados === null) {
    handlerMessageError('Revisar selección de mensajes.');
    return false;
  }
  const n = examenesSeleccionados.length;
  if (n === 0) {
    handlerMessageError('Verifique si ha seleccionado mensajes.');
    return false;
  }
  let idSeleccionados = new Array();
  console.log("examenesSeleccionados",examenesSeleccionados)
  for (let i = 0; i < n; i++) {
    let tupla = new tuplaExamen();
    tupla.nOrden = examenesSeleccionados[i].dfe_NORDEN;
    tupla.idExamen = examenesSeleccionados[i].dfe_IDEXAMEN;
    tupla.ceAbreviado = examenesSeleccionados[i].ce_ABREVIADO;
    tupla.ceCodigoExamen = examenesSeleccionados[i].ce_CODIGOEXAMEN;
    tupla.ceeDescripcionEstadoExamen = examenesSeleccionados[i].cee_DESCRIPCIONESTADOEXAMEN;
    tupla.dfe_IMPRESO = examenesSeleccionados[i].dfe_IMPRESO;
    tupla.sdfeFechaRetiraExamen = examenesSeleccionados[i].sdfe_FECHARETIRAEXAMEN;
    tupla.nombreusuarioimprime = examenesSeleccionados[i].nombreusuarioimprime;
    tupla.rutusuarioimprime = examenesSeleccionados[i].rutusuarioimprime;
    idSeleccionados.push(tupla);
  }

  return idSeleccionados;
}

function obtenerExamenesRecepcionados(pTableInfoExamenesOrden) {

  let filas = document.querySelectorAll('tr[data-id-examen]');
  let n = filas.length;
  let resultados = new Object();
  let examenesId = new Array();
  let nOrden = $("#txtNroOrden").text();

  if($("#registroRecepcionModalNombre").val() == "" || $("#registroRecepcionModalRut").val() == ""){
    handlerMessageWarning('Debe ingresar todos los datos para recepcionar los resultados');
    return;
  }

  for (let i = 0; i < n; i++) {
      let examen = new Object();
      examen.dfe_IDEXAMEN = filas[i].dataset.idExamen;
      examen.cee_DESCRIPCIONESTADOEXAMEN = filas[i].dataset.idEstado;
      examen.ceAbreviado = filas[i].dataset.idAbre;
      examen.ceCodigoExamen = filas[i].dataset.idCodigo;
  
      examenesId.push(examen); 
  }
  resultados.dp_NOMBRES = $("#registroRecepcionModalNombre").val();
  resultados.dp_NRODOCUMENTO = $("#registroRecepcionModalRut").val();
  resultados.df_NORDEN = nOrden;
  resultados.examenesLst = examenesId;
  return resultados;
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


function obtenerDatosModalMail() {

  let destinatarios = new Array();
  if ($("#mailServicioCheckbox").prop("checked") === true) {
    destinatarios.push($("#idModalSvcDescEmail").val());
  }
  if ($("#mailPacienteCheckbox").prop("checked") === true) {
    destinatarios.push($("#idModalPacienteEmail").val());
  }
  return destinatarios;
}

function enviarMail(pTableInfoExamenesOrden, destinatarios, ordenSeleccionada,tipoDeEnvio) {
  let tuplasSeleccionadas = obtenerExamenesSeleccionados(pTableInfoExamenesOrden);
  enviarPdfExamenes(tuplasSeleccionadas, destinatarios, ordenSeleccionada,tipoDeEnvio);
}

function preverPdf(pTableInfoExamenesOrden) {
  let tuplasSeleccionadas = obtenerExamenesSeleccionados(pTableInfoExamenesOrden);
  console.log("pase por aqui ***** ")
  let n = tuplasSeleccionadas.length;
  let lstIdExamenes = "";
  let idexamenes = new Array();
  let destinatarios = new Array('sin correo');
  for (let i = 0; i < n; i++) {
    lstIdExamenes += tuplasSeleccionadas[i].idExamen + ' ';
    idexamenes.push(tuplasSeleccionadas[i].idExamen);
  }
  
  updatearExamenesImpresos(idexamenes,destinatarios,nroOrdenSeleccionada,1,UrlBase)
  let url = UrlBase+'/PdfInformeResultados?pNroOrden='+ nroOrdenSeleccionada + '&pIdExamen=' + lstIdExamenes;
//  let url = UrlBase + '/CRViewer?pNroOrden=' + nroOrdenSeleccionada + '&pIdExamen=' + lstIdExamenes;
  window.open(url, "pdfWin");

}

function generarPdfExamenes(examenesSeleccionados, ordenSeleccionada) {
  let lstIdExamenes = generarQryString(examenesSeleccionados);

  let n = examenesSeleccionados.length;
  for (let i = 0; i < n; i++) {
    lstIdExamenes += examenesSeleccionados[i].idExamen + ',';
  }
  if (examenesSeleccionados.length > 0) {
    lstIdExamenes = lstIdExamenes.substr(0, lstIdExamenes.length - 1);
  }
  let url = UrlBase + '/cr/crviewer2.jsp?pNroOrden=' + ordenSeleccionada + '&pIdExamen=' + lstIdExamenes;
  window.open(url, "pdfWin");
}

function enviarPdfExamenes(examenesSeleccionados, destinatarios, ordenSeleccionada,tipoDeEnvio) {
  let url = UrlBase + '/api/orden/' + ordenSeleccionada + '/mail';
  let idexamenes = new Array();
  let n = examenesSeleccionados.length;

  for (i = 0; i < n; i++) {
    idexamenes.push(examenesSeleccionados[i].idExamen);
  }

  let postData = JSON.stringify({ "lstIdExamenes": idexamenes, "destinatarios": destinatarios,"tipoDeEnvio":tipoDeEnvio });
  let _responseDataText = "";
  let _jqXHR, _msg, _httpStatus = null;

  $.post({
    type: "POST",
    url: url,
    data: postData,
    success: handlerMessageError(_responseDataText),
    error: handlerMessageError(_jqXHR, _msg, _httpStatus),
    contentType: 'application/json; charset=utf-8'
  });
}

function registrarRecepcionExamenes(pTableInfoExamenesOrden) {
  let lstIdExamenes = obtenerExamenesSeleccionados(pTableInfoExamenesOrden);
  console.log("listaexamenes", lstIdExamenes)
  $('#registroRecepcionModalNombre').val("");
  $('#registroRecepcionModalRut').val("");
  $('#tbodyRegistrarRecepcion').html("");
  let n = lstIdExamenes.length;
  console.log("lstIdExamenes",lstIdExamenes)

  if(lstIdExamenes.length > 0){
    // cambiarDatoRut
    console.log(lstIdExamenes[0].rutusuarioimprime)
      $("#registroRecepcionModalNombre").val(lstIdExamenes[0].nombreusuarioimprime)
      $("#registroRecepcionModalRut").val(lstIdExamenes[0].rutusuarioimprime != null ? cambiarDatoRut(lstIdExamenes[0].rutusuarioimprime) : "")
  }

  for (i = 0; i < n; i++) {
    let estado = lstIdExamenes[i].dfe_IMPRESO === "S" ? "ENTREGADO" : "PENDIENTE";
    console.log('estado',estado);
    const color = estadoImpresionColorMap[estado];
    console.log('color',color);
    $('#tbodyRegistrarRecepcion').append(`<tr data-id-examen="${lstIdExamenes[i].idExamen}" data-id-estado="${lstIdExamenes[i].ceeDescripcionEstadoExamen}" data-id-abre="${lstIdExamenes[i].ceAbreviado}" data-id-codigo="${lstIdExamenes[i].ceCodigoExamen}">
             <th scope="row">${i+1}</th>
             <td>${lstIdExamenes[i].ceCodigoExamen}</td>
             <td>${lstIdExamenes[i].ceAbreviado}</td>
             <td><span class="label label-lg font-weight-bold label-inline impresion-${color}" >${lstIdExamenes[i].dfe_IMPRESO === "S" ? 'ENTREGADO' : "PENDIENTE"}</span></td>
             <td>${lstIdExamenes[i].sdfeFechaRetiraExamen == null ? "" : DDMMYYYYHHMMSS2HHMMDDMMYYYY(lstIdExamenes[i].sdfeFechaRetiraExamen)}</td>
           </tr>`);
  }
}

function ReloadRecepcionExamenes(dato) {
  $('#registroRecepcionModalNombre').val("");
  $('#registroRecepcionModalRut').val("");
  $('#tbodyRegistrarRecepcion').html("");
  let n = dato.examenes.length;
  $("#registroRecepcionModalNombre").val(dato.dp_NOMBRES != null ? dato.dp_NOMBRES : '')
  $("#registroRecepcionModalRut").val(dato.dp_NRODOCUMENTO  != null ? cambiarDatoRut(dato.dp_NRODOCUMENTO) : '')

  for (i = 0; i < n; i++) {
    let estado = dato.examenes[i].dfe_IMPRESO=== "S" ? "ENTREGADO" : "PENDIENTE";
    const color = estadoImpresionColorMap[estado];
    $('#tbodyRegistrarRecepcion').append(`<tr data-id-examen="${dato.examenes[i].dfe_IDEXAMEN}" data-id-estado="${dato.examenes[i].cee_DESCRIPCIONESTADOEXAMEN}" data-id-abre="${dato.examenes[i].ceAbreviado}" data-id-codigo="${dato.examenes[i].ceCodigoExamen}">
             <th scope="row">${i+1}</th>
             <td>${dato.examenes[i].ceCodigoExamen}</td>
             <td>${dato.examenes[i].ceAbreviado}</td> 
             <td><span class="label label-lg font-weight-bold label-inline impresion-${color}" >${dato.examenes[i].dfe_IMPRESO === "S" ? 'ENTREGADO' : "PENDIENTE"}</span></td>
             <td>${dato.examenes[i].dfe_FECHARETIRAEXAMEN == null ? "" : DDMMYYYYHHMMSS2HHMMDDMMYYYY(dato.examenes[i].dfe_FECHARETIRAEXAMEN)}</td>
           </tr>`);

           //FALTO EL ABREVIADO
  }

   tableInfoResultados.ajax.reload().draw(); 
}

function getExamenesOrden(filtro) {
  try {
    datosInfoExamenesOrden = filtro;
    tableInfoExamenesOrden.ajax.reload(); // con POST
  } catch (e) {
    handlerMessageError(e);
  }
};



var getParametros = function(d) {
  return JSON.stringify(d);
};


var initTableInfoExamenesOrden = function() {
  nroOrden = -1;
  tableInfoExamenesOrden = $('#kt_datatable1').DataTable({
    ajax: {
      "url": UrlBase + "/api/orden/" + nroOrden + "/examenes",
      "type": "GET",
      dataSrc:function(response){ 
        let respuesta = response;
        if(respuesta!= null){
            respuesta.sort(function(a, b) {
                if (quitarTildes(a.ce_ABREVIADO) < quitarTildes(b.ce_ABREVIADO)) return -1;
                if (quitarTildes(a.ce_ABREVIADO) > quitarTildes(b.ce_ABREVIADO)) return 1;
                return 0;
              });
        }
        return respuesta
    },
    },
    scrollY: calcularHeightSolo("divTablaMuestras"),
    scrollX: true,
    scrollCollapse: true,
    searching: false,
    autoWidth: false,
    paging: false,
    "info": false,
    columnDefs: [{
      targets: 0,
      width: '30px',
      className: 'dt-left',
      orderable: false,
      render: function(data, type, full, meta) {
        return `<input type="checkbox" value=""  data-colselector='x' class="checkable " checked/> `;
      },
    },
    {
      targets: 2,
      orderable: false,
      render: function(data, type, full, meta) {
        console.log(data);
        return `<div title="${data}" style="width:${t1*6}px">${largoTexto2(data,10)}</div>`
      },
    },
    {
      targets: 3,
      orderable: false,
      render: function(data, type, full, meta) {
        console.log(data);
        return `<div title="${data}" style="width:${t1*9}px">${largoTexto2(data,15)}</div>`
      },
    },
    {
      targets: 4, //Estado
      visible:true,
      render: function (data, type, row){
        const color = estadoExamenesColorMap[row["cee_DESCRIPCIONESTADOEXAMEN"]];
        return `<div class="label label-lg font-weight-bold label-inline estadoBadge-${color}"  style="width:${t1*7}px">${row["cee_DESCRIPCIONESTADOEXAMEN"]}</div>`;
      }
      
  },
  {
    targets: 5,
    orderable: false,
    render: function(data, type, full, meta) {
      console.log(data);
      return `<div style="width:${t1*7}px">${data != null ? data : ""}</div>`;
    },
  },
  {
    targets: 6,
    orderable: false,
    render: function(data, type, full, meta) {
      console.log(data);
      return `<div style="width:${t1*9}px">${data != null ? largoTexto2(data,15) : ""}</div>`;
    },
  },
  {
    targets: 7,
    orderable: false,
    render: function(data, type, full, meta) {
      console.log(data);
      return `<div style="width:${t1*3}px">${data != null ? data : ""}</div>`;
    },
  },
  {
    targets: 8,
    orderable: false,
    render: function(data, type, full, meta) {
      console.log(data);
      return `<div style="width:${t1*7}px">${data != null ? data : ""}</div>`;
    },
  },
    { visible: false, "targets": -1 },
    { visible: false, "targets": -3 },
    { visible: false, "targets": -4 },
    { visible: false, "targets": -5 },
    { visible: false, "targets": -6 },
    { visible: false, "targets": -7 },
    { visible: false, "targets": -8 },
    { visible: false, "targets": -9 },
    { visible: false, "targets": -10 },

    ],
    select: {
      style:    'multi',
      selector: 'td:first-child',
      "style": 'multiple',
      "className": 'selected',
      //selector: 'td:first-child input[type="checkbox"]'
  },
  "rowCallback": function(row, data, index){
    //tacha el row completo si el examen está anulado
    $(row).addClass('selected');
},
    language: {
      infoEmpty: "No hay datos.",
      "zeroRecords": "No se encontraron resultados",
      "sSearch" : "Buscar:",
      "info" : "Registros desde _START_ al _END_ de _TOTAL_ registros",
      "lengthMenu": "Mostrando _MENU_ registros", 
      "infoFiltered" : "(filtrado de un total de _MAX_ registros)",
      "loadingRecords": "Cargando...",
      "emptyTable": "No hay datos disponibles",
   },
    columns: [
      { "": "" },
      { "data": "dfe_NORDEN" },
      { "data": "ce_CODIGOEXAMEN" },
      { "data": "ce_ABREVIADO" },
      { "data": "cee_DESCRIPCIONESTADOEXAMEN" },
      { "data": "sdfe_FECHAIMPRESION" },
      { "data": "nombreusuarioimprime" },
      { "data": "dfe_CANTIDAD" },
      { "data": "sdfe_FECHAEMAIL" },
      { "data": "sdfe_FECHAEMAIL" },
      { "data": "dfe_IDUSUARIOEMAIL" },
      { "data": "dfe_IDEXAMEN" },
      { "data": "dp_NOMBRES" },
      { "data": "dp_PRIMERAPELLIDO" },
      { "data": "dp_SEGUNDOAPELLIDO" },
      { "data": "dp_EMAIL" },
      { "data": "cs_DESCRIPCION" },
      { "data": "cs_EMAIL" },
      { "data": "sdfe_FECHARETIRAEXAMEN" },
    ],
    "initComplete": function(settings, json) {

      let rowFilter = $('<tr class="filter"></tr>').appendTo($(tableInfoExamenesOrden.table().header()));
      this.api().columns().every(function() {
        let columna = this;
        genColFilterInforme(columna.index(), rowFilter);
      });
    }
  });

  tableInfoExamenesOrden.on( 'draw', function () {
    if(tableInfoExamenesOrden.rows().count() > 0){
      $('#seleccionaTodasLasMuestras').attr('checked', true);
    }else{
      $('#seleccionaTodasLasMuestras').attr('checked', false);
    }
  } );
}

var loadInfoExamenesOrden = function(nroOrden) {
  let url = UrlBase + "/api/orden/" + nroOrden + "/examenes";
  tableInfoExamenesOrden.ajax.url(url).load();
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

};


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


// $("#kt_datatable1 thead tr:eq(1) th").each( function ( i ) {
//   if (i === 0){
//       $(this).html('<input type="checkbox" name="" id="seleccionaTodasLasMuestras" class="checkable" />\
//                               ');
//       $(this).addClass('dt-center');
//   }
// } );

$("#seleccionaTodasLasMuestras").on( "click", function(e) {
  console.log(this);
  let table = $("#kt_datatable1").DataTable();
  if ($(this).is(":checked")){
      table.rows().select();
      $('#kt_datatable1 tbody').find(':checkbox').attr('checked', true);
  } else {
      table.rows().deselect(); 
      $('#kt_datatable1 tbody').find(':checkbox').attr('checked', false);
  }
});

 function updatearExamenesImpresos(examenesSeleccionados, destinatarios, ordenSeleccionada,tipoDeEnvio,UrlBase) {
	

  	let url ;
	if(tipoDeEnvio === 3){
		 url = UrlBase + '/api/orden/' + ordenSeleccionada + '/portalResultado';
	}else{
		 url = UrlBase + '/api/orden/' + ordenSeleccionada + '/imprimirInformes';
	}

  let postData = JSON.stringify({ "lstIdExamenes": examenesSeleccionados, "destinatarios": destinatarios,"tipoDeEnvio":tipoDeEnvio });
  let _responseDataText = "";
  let _jqXHR, _msg, _httpStatus = null;

  $.post({
    type: "POST",
    url: url,
    data: postData,
    success: handlerMessageError(_responseDataText),
    error: handlerMessageError(_jqXHR, _msg, _httpStatus),
    contentType: 'application/json; charset=utf-8'
  });
}

function genColFilterInforme(colIndex, rowFilter) {

  switch (colIndex) {
    case 0:
      genTableColCheckFilter(colIndex, rowFilter,3);
      break;
    case 1:
      genNoFilter(colIndex, rowFilter);
      break;
    case 2:
      genNoFilter(colIndex, rowFilter,6);
      break;
    case 3:
      genNoFilter(colIndex, rowFilter,9);
      break;
    case 4:
      genNoFilter(colIndex, rowFilter,7);
      break;
    case 5:
      genNoFilter(colIndex, rowFilter,7);
      break;
    case 6:
      genNoFilter(colIndex, rowFilter,9);
      break;
    case 7:
      genNoFilter(colIndex, rowFilter,3);
      break;
    case 8:
      genNoFilter(colIndex, rowFilter,7);
      break;
  }
}


function genTableColCheckFilter(colIndex, rowFilter, table) {
  let inputSelect = $(`<div class="dt-center" style="width:${t1*3}px"><input type="checkbox" name="" id="seleccionaTodasLasMuestras" class="checkable" checked="checked"></div>`);
  $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
}

function genNoFilter(colIndex, rowFilter,size = 6) {
  let inputText = $(`<div style="width:${t1*size}px"></div>`);
  $(inputText).appendTo($('<th>').appendTo(rowFilter));
}