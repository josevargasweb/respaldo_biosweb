$(document).ready(function () {

    $('#tableIncubationDetails').DataTable({
        "dom": "t",
        "language": {
            "emptyTable": "No hay medios de cultivo"
        },
        "paging": false
    });

    var tableTest = $("#tableIncubationDetails").DataTable();
    tableTest.draw();

});

function loadStep4ToComplete(test){
    if (test === undefined){
        let orderId = getOrderId();
        let examId = getExamId();
        let testId = getTestId();
        $.getJSON("Microbiologia/api/getTestData", { orderId: orderId, examId: examId, testId: testId })
            .done(function (testData) {
                getSearchOptionsAndLoadStep4Table(testData);
            })

            .fail(function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
            });
    }

    else { 
        getSearchOptionsAndLoadStep4Table(test);
    }
}

function getSearchOptionsAndLoadStep4Table(test) {
    $("#tableIncubationDetails").DataTable().clear();
    $.getJSON("Microbiologia/api/getTemperatures")
        .done(function (tempOptions) {
            getOxigenations(test, tempOptions)
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function getOxigenations(test, tempOptions) {
    $.getJSON("Microbiologia/api/getOxigenations")
        .done(function (oxiOptions) {
            loadIncubationTable(test, tempOptions, oxiOptions)
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function loadIncubationTable(test, tempOptions, oxiOptions) {
    var oxiSelect = getTxtSelectForm(oxiOptions);
    var tempSelect = getTxtSelectForm(tempOptions);

    for (let c in test.cultureMedium) {
        let culture = test.cultureMedium[c];
        var CMname = "";
        $("#tableSelectedCMDetails").DataTable().rows().every(function () {
            var data = this.data();
            if (data[0] == culture.cultureId) {
                CMname = data[1];
            }
        });

        $("#tableIncubationDetails").DataTable().row.add(
            [
                '<label class="col-form-label" id="Step4CM_' + culture.cultureId + ' "/>' + CMname + '</label>',
                '<select id="step4C_' + culture.id + '" data-cmid = "' +
                culture.cultureId + '" data-type = "C" data-CM = "' + culture.cultureId +
                '" onchange="getStep4Val(this);" class="form-control">' +
                oxiSelect,
                '<select id="step4T_' + culture.id + '" data-cmid = "' +
                culture.cultureId + '" data-type = "T" data-CM = "' + culture.cultureId +
                '" onchange="getStep4Val(this);" class="form-control">' +
                tempSelect
            ]
        ).draw();

        if (culture.oxigenation.length > 0) {
            $('#step4C_' + culture.id).val(culture.oxigenation);
            $('#step4C_' + culture.id).attr("disabled", true);
        }

        if (culture.temperature.length > 0) {
            $('#step4T_' + culture.id).val(culture.temperature);
            $('#step4T_' + culture.id).attr("disabled", true);
        }


    }

    var steps = getGlobalStepArray();
    disableIfEnded(4)

}


function getTxtSelectForm(optionsArray) {
    let txtSelect = '<option>  </option>'
    for (let o in optionsArray) {
        txtSelect = txtSelect + '<option> ' + optionsArray[o] + ' </option>'
    }
    txtSelect = txtSelect + '</select>';
    return txtSelect;
}

function getStep4Val(sel) {
    
    sel.getAttribute('data-cmid');

    var cultureMediumId = sel.getAttribute('data-CM');
    var idRow = sel.getAttribute('id');

    var CMID = idRow.split("_")[1];
    var oxiValue = $("#step4C_" + CMID).val();
    var tempValue = $("#step4T_" + CMID).val();

    putTestCultureMediumStep4(cultureMediumId, oxiValue, tempValue, CMID);
}

function putTestCultureMediumStep4(cultureMediumId, oxiValue, tempValue, CMID) {
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
            cultureId: cultureMediumId,
            id: CMID,
            sampleId: sampleId,
            oxigenation: oxiValue,
            temperature: tempValue
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

$("#chkStep4IsReady").click(function () {
    if ($("#chkStep4IsReady").prop("checked")) {
        completeStep4();      
    }
});

function completeStep4(loading = false) {
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();

    if (!$('#chkStep4IsReady').is(':checked')) {
        $('#chkStep4IsReady').attr("checked", true);
    }
    $('#chkStep4IsReady').prop("disabled", true);

    if (!loading) {
        $.ajax({
            url: "Microbiologia/api/putTask",
            data: JSON.stringify({
                sampleId: sampleId,
                examId: examId,
                testId: testId,
                task: "INCUBAR"
            }),
            headers: { 'Content-Type': 'application/json' },
            method: 'put',
            dataType: 'json',
            success: function (test) {
                // Notificar al usuario que la tarea se ha completado
                setGlobalStepArrayValue(4);
                disableIfEnded(4)

            },
            error: function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
                $('#chkStep4IsReady').prop("disabled", false);
                $('#chkStep4IsReady').click();
            }
        });

    }
}