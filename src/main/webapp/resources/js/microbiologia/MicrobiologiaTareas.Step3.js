$(document).ready(function () {

    $("#tableAvailableCMDetails").DataTable({
        "dom": "t",
        "select": true,
        "scrollY": "200px",
        "scrollCollapse": true,
        "paging": false,
        "language": {
            "emptyTable": "Cargando lista de medios de cultivo..."
        }
    });
    $("#tableAvailableCMDetails").DataTable().column(0).visible(false);
    $("#tableAvailableCMDetails").DataTable().column(2).visible(false);
    $('#tableSelectedCMDetails').DataTable({
        "dom": "t",
        "scrollY": "200px",
        "scrollCollapse": true,
        "paging": false,
        "select": true,
        "columnDefs": [{
            "targets": 2,
            'searchable': false,
            'orderable': false,
            'className': 'dt-body-center',
            'render': function (data, type, row, meta) {
                var input = $("<input/>", {
                    "data-type": "all",
                    "data-id": row[0],
                    "type": "checkbox"
                });
                if (data === "true") {
                    input.attr("checked", true);
                }
                return input.prop("outerHTML");
            }
        }]
    });
    fillCultureMediumTable();
});

function fillCultureMediumTable() {
    $("#tableAvailableCMDetails").DataTable().column(0).visible(false);
    $.getJSON("Microbiologia/Mantenedores/api/getCMList", { "activeOnly": true }, function (cmList) {
        var t = $("#tableAvailableCMDetails").DataTable();
        cmList.forEach(function (cultureMedium) {

            t.row.add(
                [
                    cultureMedium.id,
                    "[" + cultureMedium.code + "] " + cultureMedium.name,
                    cultureMedium.name
                ]
            ).node().id = 'Step3CM' + cultureMedium.id;
            t.draw();

            $('#Step3CM' + cultureMedium.id).attr('data-cm', cultureMedium.name);

        });
    });

}
$("#txtSearchAvailableCMDetails").on('keyup', function () {
    $('#tableAvailableCMDetails')
        .DataTable().column(1)
        .search($('#txtSearchAvailableCMDetails').val(), false, true)
        .draw();
});
$("#btnAddCMDetails").click(function () {
    if ($("#chkStep3IsReady").prop("checked")) {
        return;
    }
    let data = $("#tableAvailableCMDetails").DataTable().row({ selected: true }).data();
    if (data == null) {
        return;
    }
    $("#tableAvailableCMDetails").DataTable().rows().deselect();
    addCMToTables(data[0], data[2]);
});
$("#btnRemoveCMDetails").click(function () {
    if ($("#chkStep3IsReady").prop("checked")) {
        return;
    }
    $("#tableSelectedCMDetails").DataTable().row({ selected: true }).remove().draw();
    let filter_str = $("#tableSelectedCMDetails").DataTable().column(0).data().join("|");
    $("#tableAvailableCMDetails").DataTable().column(0).search('^(?!(' + filter_str + ')$).*', true, false).draw();
    $("#tableSelectedCMDetails").DataTable().rows().deselect();
    if ($("#tableSelectedCMDetails").DataTable().rows().count() == 0) {
        if ($("#chkStep3IsReady").prop("checked"))
            $("#chkStep3IsReady").click();
        $("#chkStep3IsReady").prop("disabled", true);
    }
});

$("#chkStep3IsReady").click(function () {
    if ($("#chkStep3IsReady").prop("checked")) {
        completeStep3();
        $("#tableSelectedCMDetails").DataTable().rows().every(function () {
            var data = this.data();
            putTestCultureMedium(data[0]);
        });
    }
});


function addCMToTables(id, name, loading = false) {
    var t = $("#tableSelectedCMDetails").DataTable();
    t.row.add(
        [
            id,
            name,
            ""
        ]
    ).node().id = 'Step3CM' + id;
    t.draw();

    $('#Step3CM' + id).attr('data-cm', name);

    let filter_str = $("#tableSelectedCMDetails").DataTable().column(0).data().join("|");
    $("#tableAvailableCMDetails").DataTable().column(0).search('^(?!(' + filter_str + ')$).*', true, false).draw();
    if ($("#tableSelectedCMDetails").DataTable().rows().count()) {
        $("#chkStep3IsReady").prop("disabled", false);
    }
}

function clearSelectedCMTable() {
    $("#tableSelectedCMDetails").DataTable().clear().draw();
    let filter_str = $("#tableSelectedCMDetails").DataTable().column(0).data().join("|");
    $("#tableAvailableCMDetails").DataTable().column(0).search('^(?!(' + filter_str + ')$).*', true, false).draw();
}

function putTestCultureMedium(cultureMedium) {
    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();
    let patientId = getPatientId();
    
    $.ajax({
        url: "Microbiologia/api/putTestCultureMedium",
        data: JSON.stringify({
            orderId: orderId,
            examId: examId,
            testId: testId,
            patientId: patientId,
            cultureId: cultureMedium,
            sampleId: sampleId
        }),
        headers: { 'Content-Type': 'application/json' },
        method: 'put',
        dataType: 'json',
        success: function (test) {
           
        },
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });
}

function completeStep3(loading = false) {
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();

    if (!$('#chkStep3IsReady').is(':checked')) {
        $('#chkStep3IsReady').attr("checked", true);
    }
    $('#chkStep3IsReady').prop("disabled", true);

    if (!loading) {
        $.ajax({
            url: "Microbiologia/api/putTask",
            data: JSON.stringify({
                sampleId: sampleId,
                examId: examId,
                testId: testId,
                task: "SIEMBRA"
            }),
            headers: { 'Content-Type': 'application/json' },
            method: 'put',
            dataType: 'json',
            success: function (test) {
                // Notificar al usuario que la tarea se ha completado
                setGlobalStepArrayValue(3);
                disableIfEnded(3);
            },
            error: function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
                $('#chkStep3IsReady').prop("disabled", false);
                $('#chkStep3IsReady').click();
            }
        });

        $.ajax({
            url: "Microbiologia/api/putSeedingDates",
            data: JSON.stringify({
                examId: examId,
                orderId: getOrderId(),
                seedingDate: $('#idStep3SeedingDate').val() + " " + $('#idStep3SeedingTime').val(),
                reseedingDate: $('#idStep3ReseedingDate').val() + " " + $('#idStep3ReseedingTime').val()
            }),
            headers: { 'Content-Type': 'application/json' },
            method: 'put',
            dataType: 'json',
            success: function (test) {
                // Notificar al usuario que la tarea se ha completado
            },
            error: function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
            }
        });
    }
}