/**
 * 
 */

//import BiolisBuscadorOrdenes from "resources/js/common/ModuloBiosbo.js";

var boBuscador = null;
var dtExamenesOrden = null;
var bo_OrdenesTable = null;
var paramsDtExamenesOrden = [];

/*function genCallback(dtExamenesOrden) {

  return function(data) {
    console.table(data);
    let nroOrdenSeleccionada = data[0].df_NORDEN;
    paramsDtExamenesOrden = [nroOrdenSeleccionada];
    dtExamenesOrden.ajax.reload();
  }
}
*/
function genCallback(dtExamenesOrden) {

  return function(data) {
    console.table(data);
  }
}

$(document).ready(function() {

  boBuscador = new BiolisBuscadorOrdenes([
    "#bo_div_fIni",
    "#bo_div_fFin",
    "#bo_div_nombre",
    "#bo_div_apellforo",
    "#bo_div_tipoDocumento",
    "#bo_div_nroDocumento",
    "#bo_div_tipoAtencion",
    "#bo_div_localizacion",
    "#bo_div_procedencia",
    "#bo_div_servicio",
    "#bo_div_seccion", "#bo_div_examen"]);

  let parms = new Array();
  dtExamenesOrden = $("#fmr_t_ordenes").DataTable({
    "ajax": {
      "url": gCte.getctx + "/api/ordenes/examenesseccion",
      "contentType": "application/json",
      "type": "POST",
      "dataSrc": function(jsonData) {
        if (jsonData.status === 200) {
          return jsonData.dato;
        }
        else {
          return [];
        }
      },

      "processData": false,
      "data": function(d) { return JSON.stringify(paramsDtExamenesOrden) },
    },
    select: {
      style: 'multi',
      className: 'row-selected',
      selector: 'td:first-child',
    },

    columnDefs: [{
      orderable: false,
      targets: [1, 7],
      data: null,
      defaultContent: '',
    },
    {
      orderable: false,
      className: 'select-checkbox',
      targets: 0,
      data: null,
      defaultContent: '',
    },
    ],
    "columns": [
      { "data": "ordenInformeResultadoDTO.df_NORDEN" },
      { "": "" },
      { "data": "ordenInformeResultadoDTO.df_NORDEN" },
      { "data": "ordenInformeResultadoDTO.ce_CODIGOEXAMEN" },
      { "data": "ordenInformeResultadoDTO.ce_ABREVIADO" },
      { "data": "ordenInformeResultadoDTO.csec_DESCRIPCION" },
      { "data": "ordenInformeResultadoDTO.cee_DESCRIPCIONESTADOEXAMEN" },
      { "": "" },
    ],
  });

  dtExamenesOrden.on('select', function(e, dt, type, indexes) {
    alert('click');
  });


  bo_OrdenesTable = new BiolisDatatableOrdenes('#bo_t_ordenes', boBuscador, genCallback(dtExamenesOrden));

  $("#bo_btnBuscarOrden").click(bo_OrdenesTable.doSearch);
  $("#fmr_btn_firmar_examenes").click(firmarExamenes);
  $("#fmr_btn_firmar_ordenes").click(firmarOrdenes);
  $("#fmr_btn_revisar_ordenes").click(revisarOrdenes);
  


});


firmarExamenes = function() {

  let lstExamenes = getExamenesSeleccionados(dtExamenesOrden);
  let actioncode = 'FIRMAR';
  try {
    $.ajax({
      url: gCte.getctx + "/api/examen/list/action/" + actioncode,
      data: JSON.stringify(lstExamenes),
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'post',
      dataType: 'json',
      success: function(data) {
        if (data.status !== 200) {
          handlerMessageError(data.message);
        } else {
          handlerMessageOk('Se autorizaron examens. ');
        }
      },
      error: function(jqxhr, textStatus, error) {
        let err = 'Status:' + jqxhr.status + ' ' + textStatus + ", " + error;
        handlerMessageError(err);
        console.log("Request Failed: " + err);
      }
    });
  } catch (e) {
    handlerMessageError(e);
  }


};


getExamenesSeleccionados = function(_dtExamenesOrden) {
  const examenesSeleccionados = _dtExamenesOrden.rows({ selected: true }).data();
  let examenesId = new Array();

  if (examenesSeleccionados === undefined || examenesSeleccionados === null) {
    handlerMessageError('Revisar selección de mensajes.');
    return false;
  }
  let n = examenesSeleccionados.count();

  if (n === 0) {
    handlerMessageError('Verifique si ha seleccionado mensajes.');
    return false;
  }

  for (let i = 0; i < n; i++) {
    let tupla = new Object();
    tupla.dfe_NORDEN = examenesSeleccionados[i].df_NORDEN;
    tupla.dfe_IDEXAMEN = examenesSeleccionados[i].dfe_IDEXAMEN;
    examenesId.push(tupla);
  }
  return examenesId;
}


revisarOrdenes = function(){
  
  let lstOrdenesSleccionadas = bo_OrdenesTable.getOrdenesSeleccionadas();
  paramsDtExamenesOrden = lstOrdenesSleccionadas;
  dtExamenesOrden.ajax.reload();
}

firmarOrdenes = function(){
  
  let lstOrdenesSleccionadas = bo_OrdenesTable.getOrdenesSeleccionadas();
  paramsDtExamenesOrden = lstOrdenesSleccionadas;
}