$(function() {
  var dtExamenesOrden = null;
  var paramsDtExamenesOrden = [];

const estadoExamenColor = new Array();
estadoExamenColor['FIRMADO'] = 'pendiente';
estadoExamenColor['PENDIENTE'] = 'pendiente';
estadoExamenColor['INGRESADO'] = 'pendiente';
estadoExamenColor['PROCESO'] = 'pendiente';
estadoExamenColor['BLOQUEADO'] = 'pendiente';
estadoExamenColor['AUTORIZADO'] = 'firmado';

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
      function clickOnOrden(data){
        let urlTableTipoMuestra = gCte.getctx + "/api/ordenes/examenesseccion";
        paramsDtExamenesOrden = []
        paramsDtExamenesOrden.push(data[0].df_NORDEN)
        dtExamenesOrden.ajax.url(urlTableTipoMuestra).load();


      }
      $(document).ready(function() {
        const opciones = [
          {
            targets: [0,4,5,9,10,11,12,13,14,15,16,19,20,21,22,23,24,26,27,28,29], //targets no visibles
            visible:false,
          }
        ,{
          targets: 2, //fecha formatiada
          visible:true,
          render: function (data, type, row){
            let dia = data.substring(0, 2);
            let mes = data.substring(3, 5);
            let year = data.substring(8, 10);
            let hora = data.substring(11, 16);
          return data;
          //  return hora + " " + dia + "/" + nombresMeses(mes) + "/" + year;
          }
        }
        ,{
          targets: 3, //Estado
          visible:true,
          render: function (data, type, row){
            const color = estadoOrdenColor[row["ldvfet_DESCRIPCION"]];
            return `<span class="label label-lg font-weight-bold label-inline estadoOrden-${color}" data-id="${row["df_IDFICHAESTADOTM"]}"  >${row["ldvfet_DESCRIPCION"]}</span>`;
          }
        
        }
        ,{
          targets: 7, //Rut formatiado
          visible:true,
          render: function (data, type, row){
            if(row["ldvtd_DESCRIPCION"] == "RUN"){
              const nDocumento = cambiarDatoRut(row["dp_NRODOCUMENTO"]);
              return nDocumento;
            }else{
              return row["dp_NRODOCUMENTO"];
            }
          }
        }
        ,{
          targets: 8, //Nombre completo
          visible:true,
          render: function (data, type, row){
              return data+" "+row['dp_PRIMERAPELLIDO']+" "+(row['dp_SEGUNDOAPELLIDO'] || "");
          }
        },
        {
          targets: 14, //Edad
          visible:true,
          render: function(data, type, full, meta) {
              return data!==null ? calcularEdadTM(data) : "";
          }
        
        },
      ];

        gBusquedaPacientes.boBuscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
          "#bo_div_fIni",
          "#bo_div_fFin",
          "#bo_div_nombre",
          "#bo_div_apellido",
          "#bo_div_tipoDocumento",
          "#bo_div_nroDocumento",
          "#bo_div_procedencia",
          "#bo_div_servicio"]);
          gBusquedaPacientes.bo_OrdenesTable = new BiolisDatatableOrdenesExtendida('#bo_t_ordenes', gBusquedaPacientes.boBuscador,clickOnOrden,opciones);
          gBusquedaPacientes.bo_OrdenesTable.miMetodoExtendido();
        $("#bo_btnBuscarOrden").click(gBusquedaPacientes.bo_OrdenesTable.doSearch);
        $("#btnLimpiarFiltro").click(gBusquedaPacientes.bo_OrdenesTable.doLimpiarForm);

      });

      //datatables firma
      let parms = new Array();
      dtExamenesOrden = $("#fmr_t_ordenes").DataTable({
        "ajax": {
          "url": gCte.getctx + "/api/ordenes/examenesseccion",
          "contentType": "application/json",
          "type": "POST",
          "dataSrc": function(jsonData) {
            if (jsonData.status === 200 && jsonData.dato !== null) {
              return jsonData.dato;
            }
            else {
              return [];
            }
          },
          "processData": false,
          "data": function(d) { return JSON.stringify(paramsDtExamenesOrden) },
        },
        scrollX: true,
        paging: false,
        info: false,
        "language": {
          "info": "Mostrando _TOTAL_ órdenes ",
          "infoFiltered": "filtradas de un total de _MAX_ ",
          "loadingRecords": "Por favor espere - cargando ...",
          "emptyTable": "No hay datos disponibles",
          "zeroRecords": "No se encontraron datos",
        },
        "autoWidth": false,
        scrollCollapse: true,
        select: {
          style: 'multi',
          className: 'row-selected',
          selector: 'td:first-child',
        },
        columnDefs: [
       {
        targets: 0,
        width: '30px',
        className: 'dt-left',
        //  orderable: false,
        render: function(data, type, row, meta) {
          return `<input type="checkbox" data-colselector-test="x" value="" class="dt-left checkable" /> `;
        },
      },
       {
        targets: 5,
        render: function(data, type, row, meta) {
          const color =  estadoExamenColor[row.cee_DESCRIPCIONESTADOEXAMEN.toUpperCase()]
          console.log(color)
         return ` <span class="label label-lg font-weight-bold label-inline estadoBadge-${color}"  >${data}</span>`
        },
      },
      
        ],
        "columns": [
          { "": "" },
          { "data": "df_NORDEN" },
          { "data": "ce_CODIGOEXAMEN" },
          { "data": "ce_ABREVIADO" },
          { "data": "csec_DESCRIPCION" },
          { "data": "cee_DESCRIPCIONESTADOEXAMEN" },
        ],
        "initComplete": function(settings, json) {
          let rowFilter = $('<tr class="filterOrden"></tr>').appendTo($(dtExamenesOrden.table().header()));
          this.api().columns().every(function() {
            let columna = this;
            genColFilter(columna.index(), rowFilter);
          });
        }
      });




  });




firmarExamenes = function() {

  let lstExamenes = getExamenesSeleccionados();
  console.log(typeof lstExamenes)
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


getExamenesSeleccionados = function() {
  let pTableInfoExamenesOrden = $('#fmr_t_ordenes').DataTable();
  function tuplaExamen() {
  this.dfe_NORDEN = -1;
  this.dfe_IDEXAMEN = -1;
}
let inputs = $("input[data-colselector-test]");
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
let examenesId = new Array();
for (let i = 0; i < n; i++) {
  let tupla = new tuplaExamen();
  tupla.dfe_NORDEN = examenesSeleccionados[i].df_NORDEN;
  tupla.dfe_IDEXAMEN = examenesSeleccionados[i].dfe_IDEXAMEN;

  examenesId.push(tupla);
}
return examenesId;
}
$("#fmr_btn_firmar").click(firmarExamenes);


// function revisarOrdenes(){
//   let lstOrdenesSleccionadas = gBusquedaPacientes.bo_OrdenesTable.getOrdenesSeleccionadas();
//   paramsDtExamenesOrden = lstOrdenesSleccionadas;
//   dtExamenesOrden.ajax.reload();
// }

// function firmarOrdenes(){
//   let lstOrdenesSleccionadas = gBusquedaPacientes.bo_OrdenesTable.getOrdenesSeleccionadas();
//   paramsDtExamenesOrden = lstOrdenesSleccionadas;
// }


function genColFilter(colIndex, rowFilter) {

  switch (colIndex) {
    case 0:
      genColCheckboxFilter(colIndex, rowFilter);
      break;
    default:
      genNoFilter(colIndex, rowFilter);
      break;
  }
}


function genNoFilter(colIndex, rowFilter,size = 6) {
  let inputText = $(`<div></div>`);
  $(inputText).appendTo($('<th>').appendTo(rowFilter));
}

function genColCheckboxFilter(colIndex, rowFilter,size) {
  let inputText = $(`<input type="checkbox" data-col-index="` + colIndex + `" value="" class="dt-left checkable" />`);
  $(inputText).appendTo($('<th>').appendTo(rowFilter));
  $(`input[data-col-index="` + colIndex + `"]`).change(selectFirmas);
}

function selectFirmas(e) {
  let inputs = $("input[data-colselector-test]");
  if (e.target.checked) {
    $(inputs).prop('checked', true);
  } else {
    $(inputs).prop('checked', false);
  }
}