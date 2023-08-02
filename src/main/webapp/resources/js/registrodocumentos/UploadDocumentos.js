$('#formOM').submit(function(e){
    formData = new FormData();
    formData.append("documento", $('input[name=ordenMedicaFile]')[0].files[0]);
    //console.log("orden: " + gRegistroDocs.nro_Orden);
    saveDoc(formData, 1);
    return false; //para que no se refresque la página
});

$('#formCO').submit(function(e){
    formData = new FormData();
    formData.append("documento", $('input[name=consentimientoFile]')[0].files[0]);
    saveDoc(formData, 2);
    return false; //para que no se refresque la página
});

$('#formOD').submit(function(e){
    formData = new FormData();
    //console.log("input",$('input[name=otrosDocsFile]')[0].files[0]);
    formData.append("documento", $('input[name=otrosDocsFile]')[0].files[0]);
    //console.log("formData",formData);
    saveDoc(formData, 3);
    return false; //para que no se refresque la página
});

function saveDoc(formData, tipoDoc){
    $.ajax({
        url: getctx + "/api/documento/save/"+gRegistroDocs.nro_Orden+"/"+tipoDoc+"?modulo=2",
        data: formData,
        dataType: 'json',
        type: 'POST',
        contentType: false, // Important
        processData: false, // Important
        success: function () {
            handlerMessageOk("Documento guardado");
            $('#ordenMedicaFile').fileinput('clear');
            $('#consentimientoFile').fileinput('clear');
            $('#otrosDocsFile').fileinput('clear');
            getDocumentosOrden(gRegistroDocs.nro_Orden);
        },
        error: function () {
            handlerMessageError("Error al subir documento");
        }
    });
}