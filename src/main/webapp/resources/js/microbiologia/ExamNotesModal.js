function showExamNotesModal() {
    $("#txtExamenNotesModalInternalNote").val('asdasdasdasd');
    $("#txtExamenNotesModalExamNote").val();
    $("#txtExamenNotesModalFooterNote").val();
}



$("#txtExamenNotesModalInternalNote").change(saveExamNotesModal);
$("#txtExamenNotesModalExamNote").change(saveExamNotesModal);
$("#txtExamenNotesModalFooterNote").change(saveExamNotesModal);

function saveExamNotesModal() {
    $.ajax({
        url: "Microbiologia/api/putExamNotes",
        data: JSON.stringify({
            orderId: getOrderId(),
            examId: getExamId(),
            internalNote: $("#txtExamenNotesModalInternalNote").val(),
            examNote: $("#txtExamenNotesModalExamNote").val(),
            footerNote: $("#txtExamenNotesModalFooterNote").val()
        }),
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'put',
        dataType: 'json',
        //success: function (exam) {showExam(exam);},
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });
}