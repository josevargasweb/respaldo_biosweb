/**
 * 
 */

//import BiolisBuscadorOrdenes from "resources/js/common/ModuloBiosbo.js";

class EtiquetaImpresion{

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


var gEtiquetaImpresion = new EtiquetaImpresion();


function clickOnOrden(data){
  console.table(data[0]);
  gEtiquetaImpresion.nro_Orden = data[0].df_NORDEN;
  gEtiquetaImpresion.tm_MuestrasTable.loadMuestraorden(gEtiquetaImpresion.nro_Orden );
  
}

$(document).ready(function() {
  const opciones = [
    {
      targets : '_all',
      orderable: false
    },
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

gEtiquetaImpresion.boBuscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
  "#bo_div_fIni",
  "#bo_div_fFin",
  "#bo_div_nombre",
  "#bo_div_apellido",
  "#bo_div_tipoDocumento",
  "#bo_div_nroDocumento",
  "#bo_div_procedencia",
  "#bo_div_servicio"]);
  gEtiquetaImpresion.bo_OrdenesTable = new BiolisDatatableOrdenes('#bo_t_ordenes', gEtiquetaImpresion.boBuscador,clickOnOrden,opciones);
$("#bo_btnBuscarOrden").click(gEtiquetaImpresion.bo_OrdenesTable.doSearch);
$("#btnLimpiarFiltro").click(gEtiquetaImpresion.bo_OrdenesTable.doLimpiarForm);

});

$("#btnPreviewEtiquetas").click(function(){
  
  let nOrden = gEtiquetaImpresion.nro_Orden ;
  let url = `http://127.0.0.1:8080/rest/muestrasPreviewRest`;
  
  const filas = gEtiquetaImpresion.tm_MuestrasTable.tableMuestras.rows('.selected').data();
  const n = filas.count();

  if (n===0){
      $.notify({ message: "Debe seleccionar una muestra" }, { type: "warning" });
      return;
  }
  
  let muestras = new Array();
  for (let i=0;i<n;i++){
    muestras.push(filas[i]);
  }


 if (n> 0){
  url = `http://127.0.0.1:8080/rest/muestrasPreviewRest`;
//   url = `http://152.230.106.58:8080/rest/muestrasPreview`;
//   url = `http://192.168.5.5:8080/rest/muestrasPreview`;
   data = new Object();
   data.nroOrden = nOrden;
   data.listaMuestras = new Array();
   muestras.forEach((m)=>{data.listaMuestras.push(m.codigobarras)});
   mostrar = data.listaMuestras[0];
  }  
  $.ajax({
    "url":url,
    "method":"POST",
    "data":JSON.stringify(data),
    "contentType": "application/json",
     "success": function (data) {
     $("#modalPreview").modal('show');
 
        let lstBarcodes = data;//JSON.parse(data);
        let n = lstBarcodes.length; 
        $('#lstBarCodeTable').empty();
        for (let i=0;i<n;i++){
        $('#lstBarCodeTable').append(`<tr><td><img style='border:1px;' id="bc${i}" src="data:image/png;base64, ${lstBarcodes[i]}"/></td></tr>`);
        };       
        
      
//       let dataSrc='data:image/png;base64, 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' ;
//    $("#printPreview").attr("src","file://C:\\Users\\eric.nichols\\Downloads\\biosbarras\\BiosLisPrintServer\\bin\\Debug\\tmp\\drawing"+mostrar+".png");
//    $("#printPreview").attr("src",dataSrc);
//          handlerMessageOk("Ok");
      }
      ,
      "error": function (e) {
          handlerMessageError("Error");
      }
  });



});



$("#btnImpresionEtiquetas").click(function(){
  let nOrden = gEtiquetaImpresion.nro_Orden ;
  let url = `http://127.0.0.1:8080/labelPrint/${nOrden}`;
  
  const filas = gEtiquetaImpresion.tm_MuestrasTable.tableMuestras.rows('.selected').data();
  const n = filas.count();

  if (n===0){
      $.notify({ message: "Debe seleccionar una muestra" }, { type: "warning" });
      return;
  }
  
  let muestras = new Array();
  for (let i=0;i<n;i++){
    muestras.push(filas[i]);
  }


 if (n> 0){
   url = "http://127.0.0.1:8080/rest/muestrasPrintRest";
   data = new Object();
   data.nroOrden = nOrden;
   data.listaMuestras = new Array();
   muestras.forEach((m)=>{data.listaMuestras.push(m.codigobarras)});
  }  
  $.ajax({
    "url":url,
    "method":"POST",
    "data":JSON.stringify(data),
    "headers": {
    'Access-Control-Request-Private-Network': 'true',
    },
    "contentType": "application/json",
     "success": function () {
          handlerMessageOk("Ok");
      },
      "error": function (e) {
          handlerMessageError("Error."+e);
      }
  });



});




// View a list of images

/*
    $.ajax({
        type: "post",
        url: getctx + "/api/antecedentes/" + nOrden,
        async: false,
        datatype: "json",
        success: function (response) {
            let validAntecedentes = true;
            for (let dato of response.dato){
                //console.log(dato)
                if (dato.dfa_VALORANTECEDENTE===null)
                    validAntecedentes = false;
            }
            if (validAntecedentes===true){
                const filas = tableMuestras.rows().data();
                const n = filas.count();
                let muestras = [];
                
                for (i=0; i<n ; i++){
                    let usuario = $('select[name=dUsuario] option').filter(':selected').val();
                    
                    muestras.push({
                        "nOrden" : nOrden,
                        "idPaciente" : idPaciente,
                        "idTipoMuestra" : filas[i].idtipomuestra,
                        "idEnvase" : filas[i].idenvase,
                        "idDerivador" : filas[i].idderivador,
                        "comparteMuestra" : filas[i].compartemuestra,
                        "prefijo" : filas[i].prefijo,
                        "usuario" : usuario
                    });
                }
                
                let listaString = JSON.stringify(muestras);
                //console.log(listaString)
                
                $.ajax({
                    type: "post",
                    data: "impresionEtiquetas=" + listaString,
                    async: false,
                    datatype: "json",
                    success: function () {
                        tableMuestras.ajax.reload( function(){
                            //seleccion por defecto
                            tableMuestras.rows().select();
                        });
                        $.notify({ message: "Muestras ingresadas" }, { type: "success" });
                        $('#btnImpresionEtiquetas').prop('disabled', true);
                    },
                    error: function (e) {
                        $.notify({ message: "Error al ingresar muestra: " + e.message }, { type: "danger" });
                    }
                });
                
            } else {
                $.notify({ message: "Debe ingresar antecedentes requeridos" }, { type: "danger" });
                $("#tablaOrdenesCollapse").collapse('show');
                $('html, body').animate({
                    scrollTop: $("div#divTablaOrdenes").offset().top
                }, 700);
                $("#btnModalAntecedentes"+nOrden).addClass('btn-danger');
                setTimeout(function () {
                    $("#btnModalAntecedentes"+nOrden).removeClass('btn-danger');
               },5000);
            }
        }
    });
    */