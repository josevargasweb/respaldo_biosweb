$(document).ready(function () {
   

    $('#tableStep6TestsDetails').DataTable({
        "dom": "t",
        "language": {
            "emptyTable": "No hay medios de cultivo"
        },
        "paging": false, 
        "order":[[0,"asc"]],
        columnDefs: [
            {"targets": [1,2],
            "orderable": false}, 
            {"width": "40%", 
            "targets": [0, 1] }
        ]

    });

    
    
});

function LoadStep6TableData() {
    $("#tableStep6TestsDetails").DataTable().clear();
    $('#chkStep6IsReady').attr("disabled", false);
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();

    $.getJSON("Microbiologia/api/getSampleTestList", { sampleId:sampleId, examId: examId, testId:testId })
      .done(function (sampleTestList) {
        getSearchOptionsStep6Table(sampleTestList)
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      });
}

function getSearchOptionsStep6Table(sampleTestList) {
    
    $.getJSON("Microbiologia/Mantenedores/api/getPMList", { code: "", name: "", activeOnly: true })
      .done(function (searchOptions) {
        loadStep6Table(sampleTestList, searchOptions)
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      });
    
  }



function loadStep6Table(PM, searchOptions) {
    var testSelect = getTestsSelectForm(searchOptions);

    var t = $("#tableStep6TestsDetails").DataTable();
    let i = 0;
    for (let pmId in PM) {
        t.row.add(
            [   
                '<select id="Step6T_'+PM[pmId].id+'_'+pmId+'_0" onchange="newStep6Element(this);" class="form-control">' + 
                testSelect, 
                '<input id="step6R_'+PM[pmId].id+'_'+pmId+'_0" class="form-control" value=""></input>',
                '<div class="float" style="text-align: left; float: left;">'+
                    '<a  onclick="" data-rowid = "'+pmId+'" id="step6Trash_'+PM[pmId].id+'_'+pmId+'_0" class="navi-link">' +
                        '<span class="symbol symbol-40 mr-3">' +
                            '<span id="Step6Trashbtn'+pmId+'" class="symbol-label"' +
                                'href="" data-toggle="tooltip" title="Eliminar">' +
                                '<i class="la la-trash icon-xl text-primary">' +
                                '</i>' +
                            '</span>' +
                        '</span>' +
                        '<span class="navi-text"></span>' +
                    '</a>' +
                '</div>'

            ]
        ).node().id = 'Step6_'+PM[pmId].id+'_'+pmId+'_0';
        t.draw();

        $('#Step6_'+PM[pmId].id+'_'+pmId+'_0').attr('data-rowid',pmId);
        //$('#Step6_'+PM[pmId].id+'_'+pmId+'_0').select2();

        if (PM[pmId].pruebaId.length > 0){
            $('#Step6T_'+PM[pmId].id+'_'+pmId+'_0').val(PM[pmId].pruebaId);
        }
        

        if (PM[pmId].note.length > 0){
            $('#step6R_'+PM[pmId].id+'_'+pmId+'_0').val(PM[pmId].note);
        }

        $('#Step6_'+PM[pmId].id+'_'+pmId+'_0').find("input,button,textarea,select").attr("disabled", true);


        i= i+1;
    }

    t.row.add(
        [   
            '<select id="Step6T_blank_blank_blank" onchange="newStep6Element(this);" class="form-control">' + 
            testSelect, 
            '<input id="step6R_blank_blank_blank" class="form-control" value=""></input>',
            '<div class="float" style="text-align: left; float: left;">'+
                '<a  onclick="eraseStep6Element(this);" data-rowid = "" id="step6Trash_blank_blank_blank" class="navi-link">' +
                    '<span class="symbol symbol-40 mr-3">' +
                        '<span id="Step6Trashbtn" class="symbol-label"' +
                            'href="" data-toggle="tooltip" title="Eliminar">' +
                            '<i class="la la-trash icon-xl text-primary">' +
                            '</i>' +
                        '</span>' +
                    '</span>' +
                    '<span class="navi-text"></span>' +
                '</a>' +
            '</div>'

        ]
    ).node().id = 'Step6_blank_blank_blank';
    t.draw();

    $('#Step6_blank_blank_blank').attr('data-rowid',"");

    //$("#Step6T_blank_blank_blank").select2();

    var steps = getGlobalStepArray();
    disableIfEnded(6)
    
}


function eraseStep6Element(sel){
    var rowId = $(sel).closest('tr').attr('id');

    var regId = rowId.split("_")[1];
    var initId = rowId.split("_")[3];

    if (regId != "blank" || initId != "blank"){
        var t = $("#tableStep6TestsDetails").DataTable();
        t.row("#"+ rowId).remove();
        t.draw();
    }
    else{
        alert("No se puede borrar el registro")
    }

}

function getTestsSelectForm(optionsArray){
    let txtSelect = '<option>  </option>'
    for (let o in optionsArray) {
        txtSelect = txtSelect+'<option value="' + optionsArray[o].id + '" >' + "[" + optionsArray[o].code + "] " + optionsArray[o].name + ' </option>'
      }
    txtSelect = txtSelect + '</select>';
    return txtSelect;
}

function newStep6Element(sel){
    var t = $("#tableStep6TestsDetails").DataTable();
    var blankId = "Step6_blank_blank_blank";

    var newRow = [];
    $("#"+blankId+" > td").each(function() {
        newRow.push(this);
    });

    var newId = "Step6_"+sel.value+"_99_1";

    t.row.add(
        [
            newRow[0].outerHTML,
            newRow[1].outerHTML,
            newRow[2].outerHTML,
        ]
    ).node().id = blankId;
    t.draw();

    var idRow = $(sel).closest('tr').attr('id');
    t.row("#"+idRow).nodes().id = newId;
    $('#'+idRow).attr('id', newId);

    $('#'+newId+' .symbol-label').addClass('bg-hover-primary  hoverIcon'); 

    //$("#Step6_"+sel.value+"_99_1").select2();
    
}

function putStep6Table() {
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();
    let patientId = getPatientId();

    var tableData = [];
    var rowElement = {};
    var nEnabled = 0; 
    var pruebaIdValue = "";
    var notesValue = "";
    $("#tableStep6TestsDetails > tbody  > tr").each(function() {
        var elemId = this.id;
        nEnabled = $("#"+elemId+" select:enabled").length
        pruebaIdValue = $("#"+elemId).find('td:nth-child(1) select option:selected').text();

        if (elemId != "Step6_blank_blank_blank" && nEnabled>0 && pruebaIdValue.length>0){
            pruebaIdValue = $("#"+elemId).find('td:nth-child(1) select option:selected').val();
            notesValue = $('#'+elemId).find('td:nth-child(2) input').val();
            rowElement ={ 
                sampleId: sampleId,
                examId: examId,
                testId: testId,
                pruebaId: pruebaIdValue,
                patientId: patientId,
                note: notesValue
                 
            }
            tableData.push(rowElement);
            
            $.ajax({
                url: "Microbiologia/api/putSampleTest",
                data: JSON.stringify(rowElement),
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
    });

}

$("#chkStep6IsReady").click(function () {
    if ($("#chkStep6IsReady").prop("checked")) {
        putStep6Table();
        completeStep6();
    }
});

function completeStep6(loading = false) {
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();

    if (!$('#chkStep6IsReady').is(':checked')) {
        $('#chkStep6IsReady').attr("checked", true);
    }
    $('#chkStep6IsReady').prop("disabled", true);

    if (!loading) {
        $.ajax({
            url: "Microbiologia/api/putTask",
            data: JSON.stringify({
                sampleId: sampleId,
                examId: examId,
                testId: testId,
                task: "PRUEBAS"
            }),
            headers: { 'Content-Type': 'application/json' },
            method: 'put',
            dataType: 'json',
            success: function (test) {
                // Notificar al usuario que la tarea se ha completado
                setGlobalStepArrayValue(6);
                disableIfEnded(6)
            },
            error: function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
                $('#chkStep6IsReady').prop("disabled", false);
                $('#chkStep6IsReady').click();
            }
        });

    }
}