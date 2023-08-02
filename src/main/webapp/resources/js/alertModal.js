/*Modal Confirm*/

function modalAlerta(obj) {

    $('#alertModal').modal({
        endingTop: '30%',
        dismissible: false
    });
    $('#alertModal').modal('open');
    $("#descripcionAlertaModal").text(obj.descripcion);
    if (!obj.okModal) {
        $("#okAlertaModal").toggle(false);
    }
    $("#cancelAlertaHora").text(obj.textCancelModal);
}

function modalAlertConfirm() {
    $('#alertModalConfirm').modal({
        endingTop: '30%',
        dismissible: false
    });
    $('#alertModalConfirm').modal('open');
    $("#descripcionAlertaModalConfirm").text("message");



}

function resolveAfter2Seconds() {
    return new Promise(resolve => {
        $('#btnYes').click(function () {
            console.log(true);
        });
    });
}
function modalAlerta(obj) {

    $('#alertModal').modal({
        endingTop: '30%',
        dismissible: false
    });
    $('#alertModal').modal('open');
    $("#descripcionAlertaModal").text(obj.descripcion);
    if (obj.okModal == null || obj.okModal == false) {
        $("#okAlertaModal").toggle(false);
    }
    if (obj.okModal == true) {
        $("#okAlertaModal").toggle(true);
    }
    $("#cancelAlertaHora").text(obj.textCancelModal);
}

function modalAlertConfirm(yesCallback) {

    $('#okAlertaModal').click(function () {
        yesCallback();
        modalAlertConfirm.modalAlertConfirm('close');
    });
}