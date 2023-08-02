/**
 * 
 */

//import BiolisBuscadorOrdenes from "resources/js/common/ModuloBiosbo.js";

var boBuscador = null;
var bo_OrdenesTable= null;
function clickOnOrden(data){
  
  console.table(data);
  let nOrden = data[0].df_NORDEN;
  let url = `http://localhost:8080/labelPrint/${nOrden}`;
  
  $.ajax({
    "url":url,
    "method":"GET",
  });

}

$(document).ready(function() {
  boBuscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
    "#bo_div_nOrdenFin",
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
  bo_OrdenesTable = new BiolisDatatableOrdenes('#bo_t_ordenes', boBuscador,clickOnOrden);
  $("#bo_btnBuscarOrden").click(bo_OrdenesTable.doSearch);
//  bo_OrdenesTable.bo_OrdenesDatable.on('select',function(e,dt,type,indexes){alert('Select');});


});

