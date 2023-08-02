function getDocumentosOrden(nOrden,examenId){
    let html = "";
    let extension = "";
    $.ajax({
        type: "GET",
        url: getctx + "/api/documentos/orden/" + nOrden + "/examen/"+examenId,
        async: false,
        datatype: "json",
        success: function (data) {
            $('#ordenMedicaFile').fileinput('destroy');
            data.dato.forEach(function(doc){
                if (doc.idtipodocumentoanexo === 1){
                    let tipoArchivo = doc.documento!==null?detectMimeType(doc.documento):"";
                    
                    switch (tipoArchivo){
                        case "image/png" :
                        case "image/jpg" :
                        case "image/jpeg" :
                            //html = `<img src="data:image/jpeg;base64,${doc.documento}" class="file-preview-image">`;
                            html = `data:image/jpeg;base64,${doc.documento}`;
                            //extension = tipoArchivo.replace("image/","");
                            extension = 'image';
                            break;
                        case "application/pdf": 
                            //html = "<iframe src='data:application/pdf;base64,"+doc.documento+"' class='file-preview-text responsive-iframe'></iframe>";
                            html = `data:application/pdf;base64,${doc.documento}`;
                            //html = base64toPDF(doc.documento);
                            //html = doc.documento;
                            extension = 'pdf';
                            break;
                        default:
                            break;
                    }
                }
            });
        },
        error: function () {
            handlerMessageError("Error");
        }
    });
    
    $("#ordenMedicaFile").fileinput({
        language: "es",
        showUpload: false,
        dropZoneEnabled: false,
        showRemove: false,
        showDelete: false,
        showZoom: true,
        allowedFileExtensions: ["jpg", "jpeg", "png", "pdf"],
        initialPreviewAsData: true,
        initialPreview: [html],
        initialPreviewFileType: 'image',
        initialPreviewConfig: [
            {type: extension,
            downloadUrl: html,
            caption: `Orden médica ${nOrden}`}
        ],
        //deleteUrl: getctx + '/api/documento/borrar/'+gRegistroDocs.nro_Orden+'/1'
    });
    
    $(".file-caption").hide();
    $(".kv-file-remove").hide();
    
    
};