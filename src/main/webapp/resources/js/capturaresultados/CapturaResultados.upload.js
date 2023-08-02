/**
 * 
 */
class RegistroDocumentosExamenes {

  nro_Orden;
  idExamen;
  paciente;
  docOrdenMedica;
  tipoDocOrdenMedica;

  constructor() {
    this.nro_Orden = -1;
    this.idExamen = -1;
    this.paciente = null;
    this.docOrdenMedica = null;
    this.tipoDocOrdenMedica = null;
    
   }
}
var gRegistroDocumentosExamenes = new RegistroDocumentosExamenes();


function registrarDocOrdenes() {
  let txtOrden = $("#txtNroOrden").text();
  if(txtOrden == ""){
    txtOrden = -1
  }
  gRegistroDocumentosExamenes.nro_Orden = txtOrden;
  //$("#testDocModal").modal('show');
  //gRegistroDocumentosExamenes(gRegistroDocumentosExamenes.nro_Orden);
    $("#CapturaResultadoSoloDoc").modal('show');
	modalGetDocumentosExamenesOrden(gRegistroDocumentosExamenes.nro_Orden);
};



$('#formOM').submit(function(e) {
  const fileInput = document.querySelector("input[name='ordenMedicaFile']");
  const file = fileInput.files[0];  
  const formData = new FormData();
  formData.append('documento', file);
  console.log("formData",formData,file)
  saveDoc(formData, gRegistroDocumentosExamenes.nro_Orden,gRegistroDocumentosExamenes.idExamen);
  return false; //para que no se refresque la página
});


function saveDoc(formData, nOrden,examenId) {

  console.log("formData2",formData)
  $.ajax({
    
    url: getctx + `/api/documento/orden/${nOrden}/examen/${examenId}/save` ,
    data: formData,
//    dataType: 'json',
    type: 'POST',
    contentType: 'multipart/form-data', // Important
//    processData: false, // Important
    success: function() {
      handlerMessageOk("Documento guardado");
      $('#ordenMedicaFile').fileinput('clear');
      modalUploadGetDocumentosExamenesOrden(gRegistroDocumentosExamenes.nro_Orden,gRegistroDocumentosExamenes.idExamen);
    },
    error: function() {
      handlerMessageError("Error al subir documento");
    }
  });
}
// function getDocumentosSoloOrden(nOrden){
//     let html = "";
//     let extension = "";
//     $.ajax({
//         type: "GET",
//         url: getctx + "/api/documentos/orden/" + nOrden,
//         async: false,
//         datatype: "json",
//         success: function (data) {
//             $('#ordenMedicaFile').fileinput('destroy');
//             if (data.status === 200){
//                 $("#messageError").hide();
//                 $("#docOrdenBody").show();
//                 data.dato.forEach(function(doc){
//                     if (doc.idtipodocumentoanexo === 1){
//                         let tipoArchivo = doc.documento!==null?detectMimeType(doc.documento):"";

//                         switch (tipoArchivo){
//                             case "image/png" :
//                             case "image/jpg" :
//                             case "image/jpeg" :
//                                 //html = `<img src="data:image/jpeg;base64,${doc.documento}" class="file-preview-image">`;
//                                 html = `data:image/jpeg;base64,${doc.documento}`;
//                                 //extension = tipoArchivo.replace("image/","");
//                                 extension = 'image';
//                                 break;
//                             case "application/pdf": 
//                                 //html = "<iframe src='data:application/pdf;base64,"+doc.documento+"' class='file-preview-text responsive-iframe'></iframe>";
//                                 html = `data:application/pdf;base64,${doc.documento}`;
//                                 //html = base64toPDF(doc.documento);
//                                 //html = doc.documento;
//                                 extension = 'pdf';
//                                 break;
//                             default:
//                                 break;
//                         }
//                     }
//                 });
//             } else {
//                 //handlerMessageError(data.message);
//                 $("#messageError").show();
//                 $("#messageError").text(data.message);
//                 $("#docOrdenBody").hide();
//             }
//         },
//         error: function () {
//             handlerMessageError("Error");
//         }
//     });
    
//     $("#ordenMedicaFile").fileinput({
//         language: "es",
//         showUpload: false,
//         dropZoneEnabled: false,
//         showRemove: false,
//         showDelete: false,
//         showZoom: true,
//         allowedFileExtensions: ["jpg", "jpeg", "png", "pdf"],
//         initialPreviewAsData: true,
//         initialPreview: [html],
//         initialPreviewFileType: 'image',
//         initialPreviewConfig: [
//             {type: extension,
//             downloadUrl: html,
//             caption: `Orden médica ${nOrden}`}
//         ],
//         //deleteUrl: getctx + '/api/documento/borrar/'+gRegistroDocs.nro_Orden+'/1'
//     });
    
// +    $(".file-caption").hide();
//     $(".kv-file-remove").hide();
    
    
// };



  //cambiando fileinput class

  const btnUpload = {
    docOrdenMedica:true,
    browseDocOrdenMedica:true,
  };


  //llama al modulo de fileinputBiosbo.js
const ordenMedicaFile = new filesInput("#ordenSoloMedicaFile");



function modalGetDocumentosExamenesOrden(nOrden) {
  $("#ordenMedicaFile").val(null);
  $("#ordenSoloMedicaFile").val(null);
  gRegistroDocumentosExamenes.docOrdenMedica = null;
  gRegistroDocumentosExamenes.nro_Orden=nOrden;
  
  $.ajax({
    type: "GET",
    url: getctx + "/api/documentos/orden/" + nOrden,
    async: false,
    datatype: "json",
    success: function(data) {
      console.log('rden solo',data)
      $('#ordenMedicaFile').fileinput('destroy');
      $('#ordenSoloMedicaFile').fileinput('destroy');
      if (data.dato !=null) {
        data.dato.forEach(function(doc) {
          let tipoArchivo = doc.documento !== null ? detectMimeType(doc.documento) : "";
          let hmtl = "";
          let extension = "";
          switch (tipoArchivo) {
            case "image/png":
            case "image/jpg":
            case "image/jpeg":
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
          switch (doc.idtipodocumentoanexo) {
            case 1:
              if (tipoArchivo !== null) {
                gRegistroDocumentosExamenes.docOrdenMedica = html;
                gRegistroDocumentosExamenes.tipoDocOrdenMedica = extension;
              } else {
                gRegistroDocumentosExamenes.docOrdenMedica = null;
              }
              break;
            default:
              break;
          }
        });
  
      }
      btnUpload.docOrdenMedica = false;
      btnUpload.browseDocOrdenMedica = false;
    },
    error: function() {
      handlerMessageError("Error");
    }
  });


  ordenMedicaFile.init(gRegistroDocumentosExamenes,btnUpload,"","No hay Documento por visualizar");
    $("#formSoloOM").find('.file-caption').remove();
    $("#formSoloOM").find('.kv-file-remove').remove();
  }




// examenes documento

const examnordenMedicaFile = new filesInput("#ordenMedicaFile");

function modalUploadGetDocumentosExamenesOrden(nOrden,idExamen) {
  $("#ordenMedicaFile").val(null);
  gRegistroDocumentosExamenes.docOrdenMedica = null;
  gRegistroDocumentosExamenes.nro_Orden=nOrden;
  gRegistroDocumentosExamenes.idExamen=idExamen;
  
  $.ajax({
    type: "GET",
   	url: getctx + "/api/documentos/orden/" + nOrden + "/examen/"+idExamen,
    async: false,
    datatype: "json",
    success: function(data) {
      $('#ordenMedicaFile').fileinput('destroy');
      $('#ordenSoloMedicaFile').fileinput('destroy');
      console.log('entra aca despues de l ajax',data.dato.length);
            if (data.dato !=null && data.dato.length > 0) {
         		 data.dato.forEach(function(doc) {
			          let tipoArchivo = doc.documento !== null ? detectMimeType(doc.documento) : "";
			          let hmtl = "";
			          let extension = "";
			          switch (tipoArchivo) {
			            case "image/png":
			            case "image/jpg":
			            case "image/jpeg":
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
			          switch (doc.idtipodocumentoanexo) {
			            case 1:
			              if (tipoArchivo !== null) {
			                gRegistroDocumentosExamenes.docOrdenMedica = html;
			                gRegistroDocumentosExamenes.tipoDocOrdenMedica = extension;
                      btnUpload.docOrdenMedica = false;
                      btnUpload.browseDocOrdenMedica = false;
			              } else {
			                gRegistroDocumentosExamenes.docOrdenMedica = null;
                      gRegistroDocumentosExamenes.tipoDocOrdenMedica = null;
                      if($("#ordenMedicaFile").hasClass("file-no-browse")){
                        $('#ordenMedicaFile').removeClass('file-no-browse');
                      }
			              }
			              break;
			            default:
			              break;
		          }
       		 });
  
      	}else{
          console.log('entra aca si tiene errorr');
          gRegistroDocumentosExamenes.docOrdenMedica = null;
          gRegistroDocumentosExamenes.tipoDocOrdenMedica = null;
          btnUpload.docOrdenMedica = true;
          btnUpload.browseDocOrdenMedica = true;
          if($("#ordenMedicaFile").hasClass("file-no-browse")){
            $('#ordenMedicaFile').removeClass('file-no-browse');
          }
        }
    },
    error: function() {
      handlerMessageError("Error");
    }
  });

  examnordenMedicaFile.init(gRegistroDocumentosExamenes,btnUpload,`${getctx}/api/documento/borrar/nOrden/${gRegistroDocumentosExamenes.nro_Orden}/idExamen/${gRegistroDocumentosExamenes.idExamen}/tipoDocumento/1`);

}


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
          action: function() {
            resolve();
          }
        },
        "cancelar": function() {
          //$.alert('File deletion was aborted! ' + krajeeGetCount('file-6'));
        }
      }
    });
  });
})
  .on('filedeleted', function() {
    handlerMessageOk("Eliminado correctamente");
    examnordenMedicaFile.refresh(gRegistroDocumentosExamenes,btnUpload,`${getctx}/api/documento/borrar/nOrden/${gRegistroDocumentosExamenes.nro_Orden}/idExamen/${gRegistroDocumentosExamenes.idExamen}/tipoDocumento/1`);
  });