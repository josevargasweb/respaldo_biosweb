class RegistroDocumentos{

boBuscador;
bo_OrdenesTable;
nro_Orden;
paciente;
docOrdenMedica;
tipoDocOrdenMedica;
docConsentimiento;
tipoDocConsentimiento;
docOtros;
tipoDocOtros;

constructor(){
  this.boBuscador = null;
  this.bo_OrdenesTable = null;
  this.nro_Orden = -1;
  this.paciente = null;
  this.docOrdenMedica = null;
  this.tipoDocOrdenMedica = null;
  this.docConsentimiento = null;
  this.tipoDocConsentimiento = null;
  this.docOtros = null;
  this.tipoDocOtros = null;
  }
}

var gRegistroDocs = new RegistroDocumentos();

const btnUpload = {
    docOrdenMedica:true,
    browseDocOrdenMedica:true,
    docConsentimiento:true,
    browseDocConsentimiento:true,
    docOtros:true,
    browseDocOtros:true
  };


class filesInput{
    jqid;
    registroDocs;
    btnUpload;
    fInput;
    constructor(jqid){
        this.jqid = jqid;
    }

    init(registroDocs,btnUpload,tipoApi){
        let initConfigPrevia = this.initConfiguracionPrevia(registroDocs,tipoApi);
        let initPrev = this.initPrevia(registroDocs,tipoApi);
        let btnShowUpload = this.initBtnShowUpload(btnUpload,tipoApi);
        let btnShowbrose = this.initBtnShowbrowse(btnUpload,tipoApi);
        this.habilitarBoton(btnShowbrose,tipoApi);
     this.fInput = $(this.jqid).fileinput({
            language: "es",
            browseClass: "btn btn-blue-primary",
            browseLabel: "Seleccionar",
            uploadClass: "btn btn-success",
            uploadLabel: "Guardar",
            uploadIcon: "<i class=\"bi-upload\"></i>",
            cancelClass: 'btn btn-danger',
            cancelLabel: 'Cancelar',
            /*removeClass: "btn btn-danger",
            removeLabel: "Eliminar",
            removeIcon: "<i class=\"bi-trash\"></i>",*/
            showCancel:true,
            showUpload:btnShowUpload,
            showBrowse:btnShowbrose,
            overwriteInitial: true,
            maxFileCount : 1,
            previewClass:'p-0',
            showRemove: false,
            showClose: false,
            allowedFileExtensions: ["jpg", "jpeg", "png", "pdf"],
            initialPreviewAsData: true,
            initialPreview: [initPrev],
            initialPreviewFileType: 'image',
            initialPreviewConfig: [initConfigPrevia],
            deleteUrl: getctx + '/api/documento/borrar/'+registroDocs.nro_Orden+'/'+tipoApi,
            fileActionSettings : {
                showRemove: false,
                showDrag: false,
                showUpload: true,
                indicatorNew: "",
                indicatorSuccess: "",
                indicatorError: "",
                },
            frameClass:'w-100 p-0',
        });
    }

    refresh(registroDocs,btnUpload,tipoApi){
        $(this.jqid).fileinput('destroy');

        //default config
        registroDocs.docOrdenMedica = null;
        registroDocs.tipoDocOrdenMedica = null;
        registroDocs.docConsentimiento = null;
        registroDocs.tipoDocConsentimiento = null;
        registroDocs.docOtros = null;
        registroDocs.tipoDocOtros = null;


        btnUpload.docOrdenMedica=true;
        btnUpload.browseDocOrdenMedica=true;
        btnUpload.docConsentimiento=true;
        btnUpload.browseDocConsentimiento=true;
        btnUpload.docOtros=true;
        btnUpload.browseDocOtros=true;
        this.init(registroDocs,btnUpload,tipoApi);
    }

    initConfiguracionPrevia(registroDocs,tipoApi){
         let obj = {}

         if(tipoApi == 1){
            obj =  {type: registroDocs.tipoDocOrdenMedica,
                downloadUrl: registroDocs.docOrdenMedica,
                caption: `Orden médica`};
         }else if(tipoApi == 2){
            obj =  {type: registroDocs.tipoDocConsentimiento,
                downloadUrl: registroDocs.docConsentimiento,
                caption: `Consentimiento`};
         }else if(tipoApi == 3){
            obj =  {type: registroDocs.tipoDocOtros,
                downloadUrl: registroDocs.docOtros,
                caption: `Documento`};
         }

         return obj;
    }

    initPrevia(registroDocs,tipoApi){
         let variable = null;
         if(tipoApi == 1){
            variable = registroDocs.docOrdenMedica;
         }else if(tipoApi == 2){
            variable = registroDocs.docConsentimiento;
         }else if(tipoApi == 3){
            variable = registroDocs.docOtros;
         }
         return variable;
    }

    initBtnShowUpload(btnUpload,tipoApi){
        let variable = null;
        if(tipoApi == 1){
           variable = btnUpload.docOrdenMedica;
        }else if(tipoApi == 2){
           variable = btnUpload.docConsentimiento;
        }else if(tipoApi == 3){
           variable = btnUpload.docOtros;
        }
        return variable;
    }
    
    initBtnShowbrowse(btnUpload,tipoApi){
        let variable = null;
        if(tipoApi == 1){
           variable = btnUpload.browseDocOrdenMedica;
          }else if(tipoApi == 2){
           variable = btnUpload.browseDocConsentimiento;
        }else if(tipoApi == 3){
           variable = btnUpload.browseDocOtros;
        }
        return variable;
    }
    
    habilitarBoton(btnShowbrose,tipoApi){
        if(btnShowbrose && tipoApi == 1){
            $("#ordenMedicaFile").removeClass("file-no-browse");
        }else if(btnShowbrose && tipoApi == 2){
            $("#consentimientoFile").removeClass("file-no-browse");
        }else if(btnShowbrose && tipoApi == 3){
            $("#otrosDocsFile").removeClass("file-no-browse");
        }
     
    }
}


const ordenMedicaFile = new filesInput("#ordenMedicaFile");
const consentimientoFile = new filesInput("#consentimientoFile");
const otrosDocsFile = new filesInput("#otrosDocsFile");





function clickOnOrden(data){
  
  //console.table(data);
  gRegistroDocs.nro_Orden = data[0].df_NORDEN;
  gRegistroDocs.paciente = data[0].dp_NOMBRES+" "+data[0].dp_PRIMERAPELLIDO+" "+(data[0].dp_SEGUNDOAPELLIDO || "");
  //gRegistroDocs.tm_MuestrasTable.loadMuestraorden(gRegistroDocs.nro_Orden );
  $("#divRegistroDocumentos").show();
  $("#divBuscadorOrdenes").collapse("hide");
  $("#lblOrden").text(gRegistroDocs.nro_Orden);
  $("#lblPaciente").text(gRegistroDocs.paciente);
  getDocumentosOrden(gRegistroDocs.nro_Orden);
}

function getDocumentosOrden(nOrden){
    gRegistroDocs.docOrdenMedica = null;
    gRegistroDocs.docConsentimiento = null;
    gRegistroDocs.docOtros = null;

    $.ajax({
        type: "GET",
        url: getctx + "/api/documentos/orden/" + nOrden,
        async: false,
        datatype: "json",
        success: function (data) {
            $('#ordenMedicaFile').fileinput('destroy');
            $('#consentimientoFile').fileinput('destroy');
            $('#otrosDocsFile').fileinput('destroy');
            if(data.dato != null){
                data.dato.forEach(function(doc){
                    console.log(doc.documento != null);
                    let tipoArchivo = doc.documento!==null?detectMimeType(doc.documento):null;
                    let hmtl = "";
                    let extension = "";
                    switch (tipoArchivo){
                        case "image/png" :
                        case "image/jpg" :
                        case "image/jpeg" :
                            //html = `<img src="data:image/jpeg;base64,${doc.documento}" class="file-preview-image">`;
                            html = `data:image/jpeg;base64,${doc.documento}`;
                            //extension = tipoArchivo.replace("image/","");
                            extension = 'image'
                            break;
                        case "application/pdf": 
                            //html = "<iframe src='data:application/pdf;base64,"+doc.documento+"' class='file-preview-text responsive-iframe'></iframe>";
                            html = `data:application/pdf;base64,${doc.documento}`;
                            //html = base64toPDF(doc.documento);
                            //html = doc.documento;
                            extension = 'pdf';
                            break;
                        default:
                            html = "";
                            break;
                    }
                    switch(doc.idtipodocumentoanexo){
                        case 1:
                            if (tipoArchivo!==null){
                                gRegistroDocs.docOrdenMedica = html;
                                gRegistroDocs.tipoDocOrdenMedica = extension;
                                btnUpload.docOrdenMedica = false;
                                btnUpload.browseDocOrdenMedica = false;
                            
                            } else {
                                gRegistroDocs.docOrdenMedica = null;
                                btnUpload.docOrdenMedica = true;
                                btnUpload.browseDocOrdenMedica = true;
                            }
                            break;
                        case 2:
                            if (tipoArchivo!==null){
                                gRegistroDocs.docConsentimiento = html;
                                gRegistroDocs.tipoDocConsentimiento = extension;
                                btnUpload.docConsentimiento = false;
                                btnUpload.browseDocConsentimiento = false;
                                
                            } else {
                                gRegistroDocs.docConsentimiento = null;
                                btnUpload.docConsentimiento = true;
                                btnUpload.browseDocConsentimiento = true;
                            }
                            break;
                        case 3:
                            if (tipoArchivo!==null){
                                gRegistroDocs.docOtros = html;
                                gRegistroDocs.tipoDocOtros = extension;
                                btnUpload.docOtros = false;
                                btnUpload.browseDocOtros = false;
                            } else {
                                gRegistroDocs.docOtros = null;
                                btnUpload.docOtros = true;
                                btnUpload.browseDocOtros = true;
                            }
                            break;
                        default:
                            break;
                    }
                });
            }else{
				gRegistroDocs.docOrdenMedica = null;
                btnUpload.docOrdenMedica = true;
                btnUpload.browseDocOrdenMedica = true;
                gRegistroDocs.docConsentimiento = null;
                btnUpload.docConsentimiento = true;
                btnUpload.browseDocConsentimiento = true;
                gRegistroDocs.docOtros = null;
                btnUpload.docOtros = true;
                btnUpload.browseDocOtros = true;
                
}
            
        },
        error: function () {
            handlerMessageError("Error");
        }
    });
  
    ordenMedicaFile.init(gRegistroDocs,btnUpload,1);
    consentimientoFile.init(gRegistroDocs,btnUpload,2);
    otrosDocsFile.init(gRegistroDocs,btnUpload,3);


}

$("#btnVerOrden").click(function(){
    $("#datosPacienteModal").modal('show');
    orderDetail(gRegistroDocs.nro_Orden);
    $(".divOrdenesPrevias").hide();
    localSetCurrentOrderId(gRegistroDocs.nro_Orden);
});

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
  

  gRegistroDocs.boBuscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
      "#bo_div_fIni",
      "#bo_div_fFin",
      "#bo_div_nombre",
      "#bo_div_apellido",
      "#bo_div_tipoDocumento",
      "#bo_div_nroDocumento",
      "#bo_div_procedencia",
      "#bo_div_servicio"]);
      gRegistroDocs.bo_OrdenesTable = new BiolisDatatableOrdenes('#bo_t_ordenes', gRegistroDocs.boBuscador,clickOnOrden,opciones);
    $("#bo_btnBuscarOrden").click(gRegistroDocs.bo_OrdenesTable.doSearch);
    $("#btnLimpiarFiltro").click(gRegistroDocs.bo_OrdenesTable.doLimpiarForm);
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

$("#ordenMedicaFile").on('filebeforedelete', function() {
    return new Promise(function(resolve, reject) {
        $.confirm({
            title: 'Eliminar documento',
            content: '¿Confirma que desea eliminar documento?',
            type: 'red',
            buttons: {   
                ok: {
                    btnClass: 'btn-primary text-white',
                    keys: ['enter'],
                    action: function(){
                        resolve();
                    }
                },
                "cancelar": function(){
                    //$.alert('File deletion was aborted! ' + krajeeGetCount('file-6'));
                }
            }
        });
    });
})
.on('filedeleted', function(config) {
    handlerMessageOk("Eliminado correctamente");
    ordenMedicaFile.refresh(gRegistroDocs,btnUpload,1);
});

$("#consentimientoFile").on('filebeforedelete', function() {
    return new Promise(function(resolve, reject) {
        $.confirm({
            title: 'Eliminar documento',
            content: '¿Confirma que desea eliminar documento?',
            type: 'red',
            buttons: {   
                ok: {
                    btnClass: 'btn-primary text-white',
                    keys: ['enter'],
                    action: function(){
                        resolve();
                    }
                },
                "cancelar": function(){
                    //$.alert('File deletion was aborted! ' + krajeeGetCount('file-6'));
                }
            }
        });
    });
})
.on('filedeleted', function() {
    handlerMessageOk("Eliminado correctamente");
    consentimientoFile.refresh(gRegistroDocs,btnUpload,2);
});

$("#otrosDocsFile").on('filebeforedelete', function() {
    return new Promise(function(resolve, reject) {
        $.confirm({
            title: 'Eliminar documento',
            content: '¿Confirma que desea eliminar documento?',
            type: 'red',
            buttons: {   
                ok: {
                    btnClass: 'btn-primary text-white',
                    keys: ['enter'],
                    action: function(){
                        resolve();
                    }
                },
                "cancelar": function(){
                    //$.alert('File deletion was aborted! ' + krajeeGetCount('file-6'));
                }
            }
        });
    });
})
.on('filedeleted', function() {
    handlerMessageOk("Eliminado correctamente");
    otrosDocsFile.refresh(gRegistroDocs,btnUpload,3);
});
