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


  const btnUpload = {
    docOrdenMedica:true,
    browseDocOrdenMedica:true,
  };


    //llama al modulo de fileinputBiosbo.js
const ordenMedicaFile = new filesInput("#ordenSoloMedicaFile");


function getDocumentosOrden(nOrden) {
    $("#ordenSoloMedicaFile").val(null);
    gRegistroDocumentosExamenes.docOrdenMedica = null;
    gRegistroDocumentosExamenes.nro_Orden=nOrden;
    
    $.ajax({
      type: "GET",
      url: getctx + "/api/documentos/orden/" + nOrden,
      async: false,
      datatype: "json",
      success: function(data) {
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