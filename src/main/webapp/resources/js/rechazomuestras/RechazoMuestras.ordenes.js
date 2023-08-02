var nOrden = null;
if (getWithExpiry('nOrden') !== null) {
    nOrden = getWithExpiry('nOrden');
    $("#txtNOrden").val(nOrden);
    let idMuestra = getWithExpiry('idMuestra');
    rellenarTablaMuestras(nOrden, idMuestra);
    llenarFormRechazo(idMuestra);
} else {
    nOrden = -1;
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

 

    let buscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
    "#bo_div_fIni",
    "#bo_div_fFin",
    "#bo_div_nombre",
    "#bo_div_apellido",
    "#bo_div_tipoDocumento",
    "#bo_div_nroDocumento",
    "#bo_div_procedencia",
    "#bo_div_servicio"]);
  var tableOrdenes = new BiolisDatatableOrdenes('#bo_t_ordenes', buscador, clickOnOrden,opciones);
  $("#bo_btnBuscarOrden").click(tableOrdenes.doSearch);
  $("#btnLimpiarFiltro").click(tableOrdenes.doLimpiarForm);
  
  // let columnas = [0,4,5,6,7,9,10,11,12,13,15,16,17,18,19,20,21,22,23,24,25];
  // let columnas = [0,4,5,6,7,9,10,11,12,13,15,16,17,18,19,20,21,22,23,24,25,26,27,28];
  // tableOrdenes.ocultarColumnas(columnas);
    /*
  let nro = tableOrdenes.bo_OrdenesDatable.columns().nodes().length;
  console.log("nro columnas: "+nro);
    for (let i=0; i<nro; i++){
      for (let j=0; j<columnas.length; j++){
          if (i===columnas[j]){
              tableOrdenes.bo_OrdenesDatable.column(i).visible(false);
          }
      }
  }
  */
  //tableOrdenes.bo_OrdenesDatable.column(7).visible(true);
});

function clickOnOrden(data){
  //console.table(data);
  nOrden = data[0].df_NORDEN;
  $("#divRegistroDocumentos").show();
  //$("#divBuscadorOrdenes").collapse("hide");
  $("#lblOrden").text(nOrden);
  rellenarTablaMuestras(nOrden, null);
  limpiarFormRechazo();
}

function limpiarFormRechazo(){
    $("#lblPrimerRechazo").hide();
    $("#lblSegundoRechazo").hide();
    $("#lblNoMasRechazos").hide();
    $("#lblNuevaMuestra").hide();
    $("#lblNuevaMuestraPendiente").hide();
    $("#lblNuevaMuestraLista").hide();
    $("#causaRechazoMuestras").attr("disabled", true);
    $("#causaRechazoMuestras").val(0);
    $("#observacion").attr("disabled", true);
    $("#observacion").val("");
    $("#btnRechazarMuestra").attr("disabled", true);
    $("#btnRechazoParcial").attr("disabled", true);
    $("#btnRechazoParcial").hide();
    $('.selectpicker').selectpicker('refresh');
}