class filesInput{
    jqid;
    registroDocs;
    btnUpload;
    fInput;
    constructor(jqid){
        this.jqid = jqid;
    }

    init(registroDocs,btnUpload,deleteUrl,textoDrop = 'Arrastre y suelte aquí los archivos &hellip;'){
        $(this.jqid).fileinput('destroy');
        $(this.jqid).val(null);
        let initConfigPrevia = this.initConfiguracionPrevia(registroDocs);
        let initPrev = this.initPrevia(registroDocs);
        let btnShowUpload = this.initBtnShowUpload(btnUpload);
        let btnShowbrose = this.initBtnShowbrowse(btnUpload);
        this.habilitarBoton(btnShowbrose);
     this.fInput = $(this.jqid).fileinput({
            language: "es",
            browseClass: "btn btn-blue-primary",
            browseLabel: "Seleccionar",
            uploadClass: "btn btn-success",
            uploadLabel: "Guardar",
            uploadIcon: "<i class=\"bi-upload\"></i>",
            dropZoneTitle: textoDrop,
            /*removeClass: "btn btn-danger",
            removeLabel: "Eliminar",
            removeIcon: "<i class=\"bi-trash\"></i>",*/
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
            deleteUrl:deleteUrl, //modificar para habilitar o no
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

    refresh(registroDocs,btnUpload,habilitar,deleteUrl,textoDrop){
        $(this.jqid).fileinput('destroy');
        $(this.jqid).val(null);

        //default config
        registroDocs.docOrdenMedica = null;
        registroDocs.tipoDocOrdenMedica = null;

        btnUpload.docOrdenMedica=habilitar;
        btnUpload.browseDocOrdenMedica=habilitar;
        this.init(registroDocs,btnUpload,deleteUrl);
    }

    initConfiguracionPrevia(registroDocs){
         let obj = {}
        obj =  {type: registroDocs.tipoDocOrdenMedica,
            downloadUrl: registroDocs.docOrdenMedica,
            caption: `Orden médica`};

         return obj;
    }

    initPrevia(registroDocs){
      let variable = null;
      variable = registroDocs.docOrdenMedica;
      return variable;
    }

    initBtnShowUpload(btnUpload){
        let variable = null;
        variable = btnUpload.docOrdenMedica;
        return variable;
    }
    
    initBtnShowbrowse(btnUpload){
      let variable = null;
      variable = btnUpload.browseDocOrdenMedica;
      return variable;
    }
    
    habilitarBoton(btnShowbrose){
        if(btnShowbrose){
            $(this.jqid).removeClass("file-no-browse");
        }
    }
}

//ejemplo base de como se debe montar

// class RegistroDocumentosExamenes {

//     nro_Orden;
//     idExamen;
//     paciente;
//     docOrdenMedica;
//     tipoDocOrdenMedica;
  
//     constructor() {
//       this.nro_Orden = -1;
//       this.idExamen = -1;
//       this.paciente = null;
//       this.docOrdenMedica = null;
//       this.tipoDocOrdenMedica = null;
      
//      }
//   }
//   var gRegistroDocumentosExamenes = new RegistroDocumentosExamenes();



//   const btnUpload = {
//     docOrdenMedica:true,
//     browseDocOrdenMedica:true,
//   };


//   //llama al modulo de fileinputBiosbo.js
// const ordenMedicaFile = new filesInput("#ordenSoloMedicaFile");


// function modalGetDocumentosExamenesOrden(nOrden) {
//     $("#ordenMedicaFile").val(null);
//     $("#ordenSoloMedicaFile").val(null);
//     gRegistroDocumentosExamenes.docOrdenMedica = null;
//     gRegistroDocumentosExamenes.nro_Orden=nOrden;
    
//     $.ajax({
//       type: "GET",
//       url: getctx + "/api/documentos/orden/" + nOrden,
//       async: false,
//       datatype: "json",
//       success: function(data) {
//         $('#ordenMedicaFile').fileinput('destroy');
//         $('#ordenSoloMedicaFile').fileinput('destroy');
//         if (data.dato !=null) {
//           data.dato.forEach(function(doc) {
//             let tipoArchivo = doc.documento !== null ? detectMimeType(doc.documento) : "";
//             let hmtl = "";
//             let extension = "";
//             switch (tipoArchivo) {
//               case "image/png":
//               case "image/jpg":
//               case "image/jpeg":
//                 //html = `<img src="data:image/jpeg;base64,${doc.documento}" class="file-preview-image">`;
//                 html = `data:image/jpeg;base64,${doc.documento}`;
//                 //extension = tipoArchivo.replace("image/","");
//                 extension = 'image'
//                 break;
//               case "application/pdf":
//                 //html = "<iframe src='data:application/pdf;base64,"+doc.documento+"' class='file-preview-text responsive-iframe'></iframe>";
//                 html = `data:application/pdf;base64,${doc.documento}`;
//                 //html = base64toPDF(doc.documento);
//                 //html = doc.documento;
//                 extension = 'pdf';
//                 break;
//               default:
//                 html = "";
//                 break;
//             }
//             switch (doc.idtipodocumentoanexo) {
//               case 1:
//                 if (tipoArchivo !== null) {
//                   gRegistroDocumentosExamenes.docOrdenMedica = html;
//                   gRegistroDocumentosExamenes.tipoDocOrdenMedica = extension;
//                 } else {
//                   gRegistroDocumentosExamenes.docOrdenMedica = null;
//                 }
//                 break;
//               default:
//                 break;
//             }
//           });
    
//         }
//         btnUpload.docOrdenMedica = false;
//         btnUpload.browseDocOrdenMedica = false;
//       },
//       error: function() {
//         handlerMessageError("Error");
//       }
//     });
  
  
//     ordenMedicaFile.init(gRegistroDocumentosExamenes,btnUpload,"","No hay Documento por visualizar");
//       $("#formSoloOM").find('.file-caption').remove();
//       $("#formSoloOM").find('.kv-file-remove').remove();
//     }