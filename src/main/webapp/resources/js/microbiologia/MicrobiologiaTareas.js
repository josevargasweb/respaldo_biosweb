var GlobalExamId;
var GlobalOrderId;
var GlobalTestId;
var GlobalSampleId;
var GlobalPatientId;
var GlobalStepArray;
var GlobalOrderDate;

$(document).ready(function () {
    
    $("#tableBottomTests").DataTable({
        "dom": "t",
        "select": true,
        //"scrollY": "200px",
        //"scrollCollapse": true,
        "paging": false,
        "responsive": true,
        "language": {
            "emptyTable": "Cargando tests..."
        },
        columnDefs: [
            { width: "400px", targets: [1] },
        ]
    });
    $("#selPatientSampleType").select2();
    $("#selPatientBodyRegion").select2();
    $("#tableBottomTests").DataTable().column(0).visible(false);
    $("#tableBottomTests").DataTable().columns.adjust().draw();
    let searchParams = new URLSearchParams(window.location.search);
    let examId = null;
    let orderId = null;
    fillBottomSelects();
    if (searchParams.has('examId') && searchParams.has('orderId')) {
        examId = searchParams.get('examId');
        setExamId(examId);
        orderId = searchParams.get('orderId');
        setOrderId(orderId);
        fillExamData();
        showOrderDataModal(orderId);
    } else {
        alert("No se pudo cargar la información del examen: Se requiere los ID de examen y orden.");
    }
});

$('#selPatientSampleType').on('change', function () {
    if ($('#selPatientSampleType option:selected').val() != "") {
        putSampleType($('#selPatientSampleType option:selected').text());
    }
});

$('#selPatientBodyRegion').on('change', function () {
    if ($('#selPatientBodyRegion option:selected').val() != "") {
        putBodyPart($('#selPatientBodyRegion option:selected').text());
    }
});

$('#btnBottomExamEvents').click(function () { $("#tableExamEventsModal").DataTable().draw(); });

$('#tableBottomTests tbody').on('click', 'tr', function () {
    var currentRowData = $('#tableBottomTests').DataTable().row(this).data();
    $('#idTasksWizardContent').prop('hidden', false);
    setTestId(currentRowData[0]);
    showTestInfo();
});

$("#chkStep2IsReady").click(function () {
    if ($("#chkStep2IsReady").prop("checked")) {
        completeStep2();
    }
});

function fillExamData() {
    let examId = getExamId();
    let orderId = getOrderId();
    $.getJSON("Microbiologia/api/getExamData", { orderId: orderId, examId: examId })
        .done(function (exam) {
            $('#txtBottomExamName').text(exam.observations.order.exam);
            $('#txtPatientName').val(exam.observations.patient.name);
            $('#txtPatientDocumentN').val(exam.observations.patient.nDocument);
            $('#txtPatientAge').val(exam.observations.patient.age);
            $("#txtPatientOrderN").val(orderId);
            fillTestsTable(exam);
            setGlobalOrderDate(exam.observations.order.date);
            $("#tableBottomTests tbody tr:eq(0)").click();
            $("#tableBottomTests").DataTable().row(':eq(0)').select().draw();
            showExamNotesModal(exam.notes);
            showExamEventsModal(exam.events);
            fillStep1(exam, orderId);
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function fillTestsTable(exam) {
    $("#tableBottomTests").DataTable().clear().draw();
    exam.testsList.forEach(function (test, i) {
        if (test.isCulture == "true") {
            $("#tableBottomTests").DataTable().row.add(
                [
                    test.id,
                    test.test
                ]
            ).draw();
        }
    });
}

function fillBottomSelects() {
    $("#selPatientBodyRegion").empty();
    var newOption = new Option("-", "", false, false);
    $('#selPatientBodyRegion').append(newOption).trigger('change');
    $.getJSON("Microbiologia/Mantenedores/api/getBAList", { activeOnly: "true" }, function (BodyAreaList) {
        BodyAreaList.forEach(function (bodyArea) {
            var newOption = new Option(bodyArea.name, bodyArea.id, false, false);
            $('#selPatientBodyRegion').append(newOption);
        })
        $('#selPatientBodyRegion').trigger('change');
        $("#selPatientBodyRegion").prop("disabled", false);
    });
/*

            String descripcion = request.getParameter("cmueDescripcion").toUpperCase();
            String descripcionAbreviada = request.getParameter("cmueDescripcionabreviada").toUpperCase();
            String prefijo = request.getParameter("cmuePrefijotipomuestra").toUpperCase();
            String idGrupo = request.getParameter("grupoMuestras");
            String orden = request.getParameter("cmueSort");
            String ip = request.getParameter("ipEquipo");*/


    $("#selPatientSampleType").empty();
    $("#selPatientSampleType").append('<option value="" > - </option>');
    console.log("entre a configuracion muestras")
    $.ajax({
        type: "post",
        data: "filtroId=1&idTipoMuestra=0",
        url: "ConfiguracionMuestras",
        datatype: "json",
        success: function (mensaje) {
	
            var dato =JSON.parse(mensaje);
        
            var cont = 0;
            if (dato.muestra.length > 0) {
                dato.muestra.forEach(function (muestra) {
                    cont++;
                    $('#selPatientSampleType').append($('<option>', {
                        value: muestra.id,
                        text: muestra.descripcion
                    }));
                    console.log('exito');
                });
            } else {
                //log error en tipos de muestras
            }
            $("#selPatientSampleType").prop("disabled", false);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
};

function showTestInfo() {
    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();
    $.getJSON("Microbiologia/api/getTestData", { orderId: orderId, examId: examId, testId: testId })
        .done(function (test) {
            setGlobalStepArray();
            setSampleId(test.data.sampleCode);
            setPatientId(test.data.patientId);
            if ($("#selPatientSampleType option[value='" + test.data.sampleId + "']").length > 0)
                $("#selPatientSampleType").val(test.data.sampleId);
            else
                $("#selPatientSampleType").val("");
            $("#selPatientSampleType").trigger('change');
            $("#selPatientBodyRegion").val(test.data.bodyPartId);
            $("#selPatientBodyRegion").trigger('change');
            fillStep2(test);
            fillStep3(test);
            fillStep4(test);
            fillStep5(test);
            fillStep6(test);
            fillStep7(test);
            fillStep8(test);
            setStep();
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function putBodyPart(bodyPart) {
    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();
    $.ajax({
        url: "Microbiologia/api/putTestBodyPart",
        data: JSON.stringify({ orderId: orderId, examId: examId, testId: testId, bodyPart: bodyPart }),
        headers: { 'Content-Type': 'application/json' },
        method: 'put',
        dataType: 'json',
        success: function (test) {
            $('#txtTaskStep1BodyPart').val(test.data.bodyPart);
        },
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });
}

function putSampleType(sampleType) {
    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();

    $.ajax({
        url: "Microbiologia/api/putTestSampleType",
        data: JSON.stringify({ orderId: orderId, examId: examId, testId: testId, sampleType: sampleType }),
        headers: { 'Content-Type': 'application/json' },
        method: 'put',
        dataType: 'json',
        success: function (test) {
            $('#txtTaskStep1SampleType').val(test.data.sample);
            $('#txtTaskStep2SampleType').val(test.data.sample);
            $('#txtTaskStep2SamplePrefix').val(test.data.prefix);
        },
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });
}


function setStep() {
    $.getJSON("Microbiologia/api/getTask", { sampleId: getSampleId(), testId: getTestId(), examId: getExamId() })
        .done(function (task) {
            setGlobalStepArray();
            var step = 1;
            if (!$.isEmptyObject(task)) {
                setGlobalStepArrayValue(2);
                disableIfEnded(2);
                completeStep2(true);
                step = 3;
                setGlobalStepArrayValue(step-1);
                GlobalStepArray[step-1]=1;
                if (task.siembra == "true") {
                    completeStep3(true);
                    disableIfEnded(step);
                    step = 4;
                    setGlobalStepArrayValue(step-1);
                }
                if (task.incubar == "true") {
                    completeStep4(true);
                    disableIfEnded(step);
                    step = 5;
                    setGlobalStepArrayValue(step-1);
                }
                if (task.revPlacas == "true") {
                    completeStep5(true);
                    disableIfEnded(step);
                    step = 6;
                    setGlobalStepArrayValue(step-1);
                    disableIfEnded(step-1);
                }
                if (task.pruebas == "true") {
                    completeStep6(true);
                    disableIfEnded(step)
                    step = 7;
                    setGlobalStepArrayValue(step-1);
                }
                if (task.identificacion == "true") {
                    completeStep7(true);
                    disableIfEnded(step);
                    step = 8;
                    setGlobalStepArrayValue(step-1);
                }
                if (task.sensibilidad == "true") {
                    completeStep8(true);
                    step = 8;
                    setGlobalStepArrayValue(step);
                    //disableIfEndedStep8();
                }
            }
            KTWizard3.goTo(step);

        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function completeStep2(loading = false) {
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();
    let patientId = getPatientId();

    if (!$('#chkStep2IsReady').is(':checked'))
        $('#chkStep2IsReady').attr("checked", true);
    $('#chkStep2IsReady').prop("disabled", true);

    if (!loading) {
        $.ajax({
            url: "Microbiologia/api/putTask",
            data: JSON.stringify({
                sampleId: sampleId,
                examId: examId,
                testId: testId,
                patientId: patientId,
                task: "INICIO"
            }),
            headers: { 'Content-Type': 'application/json' },
            method: 'put',
            dataType: 'json',
            success: function (test) {
                // Notificar al usuario que la tarea se ha completado
                setGlobalStepArrayValue(2);
                disableIfEnded(2);
            },
            error: function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
                $('#chkStep2IsReady').prop("disabled", false);
                $('#chkStep2IsReady').click();
            }
        });
    }
}

function fillStep1(exam, orderId) {
    $('#txtTaskStep1PatientName').val(exam.observations.patient.name);
    $("#txtTaskStep1OrderN").val(orderId);
    $('#txtTaskStep1SampleType').val($('#selPatientSampleType option:selected').text());
    $('#txtTaskStep1BodyPart').val($('#selPatientBodyRegion option:selected').text());
    $('#txtTaskStep1SamplingState').val(exam.observations.sampling.status);
    $('#txtTaskStep1SamplingObservation').val(exam.observations.sampling.observation);
    $('#txtTaskStep1SamplingUser').val(exam.observations.sampling.user);
    $('#txtTaskStep1SamplingDate').val(exam.observations.sampling.date);
    $('#txtTaskStep1ReceptionState').val(exam.observations.reception.status);
    $('#txtTaskStep1ReceptionObservation').val(exam.observations.reception.observation);
    $('#txtTaskStep1ReceptionUser').val(exam.observations.reception.user);
    $('#txtTaskStep1ReceptionDate').val(exam.observations.reception.date);
    $('#txtTaskStep2PatientName').val(exam.observations.patient.name);
}

function fillStep2(test) {
    $('#txtTaskStep2ExamName').text(test.data.exam);
    $("#txtTaskStep2OrderN").val(getOrderId());
    $('#txtTaskStep2SampleType').val(test.data.sample);
    $('#txtTaskStep2SamplePrefix').val(test.data.prefix);
    $('#txtTaskStep2ExamLabel').val(test.data.labelCode);
    $('#chkStep2IsReady').prop("disabled", false);
    if ($('#chkStep2IsReady').is(':checked'))
        $('#chkStep2IsReady').click();
    if ($('#chkStep2Preview').is(':checked'))
        $('#chkStep2Preview').click();
}

function fillStep3(test) {
    clearSelectedCMTable();
    $('#txtTaskStep3SampleType').val(test.data.sample);
    $('#txtTaskStep3SampleTypeId').val(test.data.sampleId);
    $('#txtTaskStep3TestName').text(test.data.name);
    $("#idStep3SeedingDate").val(test.actions.seedingDate);
    $("#idStep3SeedingTime").val(test.actions.seedingTime);
    $("#idStep3ReseedingDate").val(test.actions.reseedingDate);
    $("#idStep3ReseedingTime").val(test.actions.reseedingTime);
    $('#chkStep3IsReady').prop("disabled", true);
    $('#chkStep3IsReady').attr("checked", false);

    var BreakException = {};
    for (let culture in test.cultureMedium) {
        try {
            $("#tableAvailableCMDetails").DataTable().rows({ filter: 'applied' }).every(function () {
                var data = this.data();
                if (data[0] == test.cultureMedium[culture].cultureId) {
                    addCMToTables(test.cultureMedium[culture].cultureId, data[2], true);
                    throw BreakException;
                }
            });
        } catch (e) {
            if (e !== BreakException) throw e;
        }
    };
    disableIfEnded(3)
}

function fillStep4(test) {
    $('#txtTaskStep4SampleType').val(test.data.sample);
    $('#txtTaskStep4SampleTypeId').val(test.data.sampleId);
    $('#txtTaskStep4TestName').text(test.data.name);
    $('#chkStep4IsReady').attr("checked", false);
    $('#chkStep4IsReady').prop("disabled", false);
    getSearchOptionsAndLoadStep4Table(test);
}

function fillStep5(test) {
    $('#txtTaskStep5SampleType').val(test.data.sample);
    $('#txtTaskStep5SampleTypeId').val(test.data.sampleId);
    $('#txtTaskStep5TestName').text(test.data.name);
    $('#chkStep5IsReady').attr("checked", false);
    $('#chkStep5IsReady').prop("disabled", true);
    
    LoadStep5TableData(test);
}

function fillStep6(test) {
    $('#txtTaskStep6TestName').text(test.data.name);
    $('#chkStep6IsReady').attr("checked", false);
    LoadStep6TableData();
    disableIfEnded(6)
}

function fillStep7(test) {
    $('#txtTaskStep7TestName').text(test.data.name);
    LoadStep7TableData();
}

function fillStep8(test) {
    $('#txtTaskStep8TestName').text(test.data.name);
    LoadStep8Tables();
}

function closeFromStep5(){
    //Cerrar etapa 5,6,7,8
    $('#chkStep5IsReady').attr("checked", true);
    $('#chkStep6IsReady').attr("checked", true);
    $('#chkStep7IsReady').attr("checked", true);
    $('#chkStep8IsReady').attr("checked", true);
    completeStep5();
    completeStep6();
    completeStep7();
    completeStep8();
}

function setPatientId(patientId) {
    GlobalPatientId = patientId;
}

function getPatientId() {
    return GlobalPatientId;
}

function setSampleId(sampleId) {
    GlobalSampleId = sampleId;
}

function getSampleId() {
    return GlobalSampleId;
}

function setTestId(testId) {
    GlobalTestId = testId;
}

function getTestId() {
    return GlobalTestId;
}

function setExamId(examId) {
    GlobalExamId = examId;
}

function getExamId() {
    return GlobalExamId;
}

function setOrderId(orderId) {
    GlobalOrderId = orderId;
}

function getOrderId() {
    return GlobalOrderId;
}

function getGlobalStepArray(){
    return GlobalStepArray;
}

function setGlobalStepArrayValue(step){
    GlobalStepArray[step-1]=1;
}

function setGlobalStepArray(){
    GlobalStepArray = [1,0,0,0,0,0,0,0]
}

function setGlobalOrderDate(date){
    GlobalOrderDate = date;
}

function getOrderDate(){
    return GlobalOrderDate;
}

function disableIfEnded(task){
    var steps = getGlobalStepArray();
    if (steps[task-1]==1){
        $("#chkStep"+task+"IsReady").attr("checked", true)
        $("#chkStep"+task+"IsReady").prop("disabled", true);
        $(".step"+task).find("input,button,textarea,select").attr("disabled", true);

        if (task=="3"){
            $("#btnAddCMDetails").prop("disabled", true);
            $("#btnRemoveCMDetails").prop("disabled", true);

        }
    }
}
