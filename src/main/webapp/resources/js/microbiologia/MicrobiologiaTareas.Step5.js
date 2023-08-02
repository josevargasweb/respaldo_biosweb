$(document).ready(function () {
   

    $('#tablePlateReviewDetails').DataTable({
        "dom": "t",
        "language": {
            "emptyTable": "No hay medios de cultivo"
        },
        "paging": false, 
        "order":[[0,"asc"]],
        columnDefs: [
            {"targets": [0],
            "visible": false,
            "searchable": false
            },
            {"targets": [1,2,3,4,5],
            "orderable": false},
            { 
            "width": "35%", 
            "targets": 5 }
        ]

    });

    
    
});


function LoadStep5TableData(test) {
    $("#tablePlateReviewDetails").DataTable().clear();
    if (test === undefined){
        let orderId = getOrderId();
        let examId = getExamId();
        let testId = getTestId();
        $.getJSON("Microbiologia/api/getTestData", { orderId: orderId, examId: examId, testId: testId })
            .done(function (testData) {
                getRevisionPlateList(testData);
            })

            .fail(function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
            });
    }

    else { 
        getRevisionPlateList(test);
    }
}

function getRevisionPlateList(test) {
    var cmArray = [];
    var stringRPList = "";
    for (let culture in test.cultureMedium) {
        cmArray.push(test.cultureMedium[culture].id);
        stringRPList = stringRPList+","+test.cultureMedium[culture].id;
    }
    stringRPList = stringRPList.substring(1);
    $.getJSON("Microbiologia/api/getRevisionPlacasList", { seedingId:stringRPList})
      .done(function (revisionPlateList) {
        getSearchOptionsStep5Table(test, revisionPlateList);
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      });
    
  }


function getSearchOptionsStep5Table(test, revisionPlateList) {
    $.getJSON("Microbiologia/api/getRevisionTimes")
      .done(function (searchOptions) {
        loadPlateRevisionTable(test, revisionPlateList, searchOptions)
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      });
    
  }

function loadPlateRevisionTable(test, revisionPlateList, searchOptions) {
    var revisionTimeSelect = getRevisionTimesSelectForm(searchOptions);
    var stateSelect = '<option>  </option> <option>Neg. Parcial</option><option>Neg. Definitivo</option><option>Positivo</option>  </select>';

    var t = $("#tablePlateReviewDetails").DataTable();
    let i = 0;
    var CMname = "";

    cultureIndexArray = [];
    cultureTypeId = [];
    var RPCultureTypeId = [];
    let flag = 0;

    for (let indexCM in test.cultureMedium){
        flag = 0; 
        for (let indexRP in revisionPlateList){
            if (test.cultureMedium[indexCM].id == revisionPlateList[indexRP].seedingId){
                flag = 1; 
                RPCultureTypeId[indexRP] = test.cultureMedium[indexCM].cultureId
            }
        }

        if (flag == 0){
            cultureIndexArray.push(indexCM);
            cultureTypeId.push(test.cultureMedium[indexCM].cultureId);
        }
        else{

        }
    }

    for (let idRP in revisionPlateList) {
        CMname = "";
        $("#tableSelectedCMDetails").DataTable().rows().every(function () {
            var data = this.data();
            if (data[0] == RPCultureTypeId[idRP]) {
                CMname = data[1];
            }
        });

        
        t.row.add(
            [   'Step5RP_'+revisionPlateList[idRP].seedingId+'_0',
                '<label class="col-form-label" id="step5CM' + i + ' "/>'+CMname+'</label>',
                '<select id="step5R'+i+'" data-type = "R" data-CM = "'+ cultureTypeId[idRP] +
                '" onchange="updateStep5Element(this);" class="form-control">' + 
                revisionTimeSelect, 
                '<select id="step5S'+i+'" data-type = "S" data-cmid="'+revisionPlateList[idRP].seedingId+'" data-rowid = "'+i+
                '" data-CM = "'+ cultureTypeId[idRP] +
                '" onchange="updateStep5Element(this);" class="form-control">' + 
                stateSelect, 
                '<input id="step5N'+i+'" onchange="updateStep5Element(this);" data-type = "N" data-CM = "'+ cultureTypeId[idRP] +
                '" class="form-control" value=""></input>',
                '<div class="float" style="text-align: left; float: left;">'+
                    '<a  onclick="eraseStep5Element(this);" data-rowid = "'+i+'" id="step5Trash'+i+'" class="navi-link">' +
                        '<span class="symbol symbol-40 mr-3 ">' +
                            '<span id="Step5Trashbtn'+i+'" class="symbol-label"' +
                                'href="" data-toggle="tooltip" title="Eliminar">' +
                                '<i class="la la-trash icon-xl text-primary">' +
                                '</i>' +
                            '</span>' +
                        '</span>' +
                        '<span class="navi-text"></span>' +
                    '</a>' +
                '</div>'
            ]

        ).node().id = 'Step5RP_'+revisionPlateList[idRP].seedingId+'_'+revisionPlateList[idRP].id;
        t.draw();

        $('Step5RP_'+revisionPlateList[idRP].seedingId+'_'+revisionPlateList[idRP].id).attr('data-rowid',i);
        $('Step5RP_'+revisionPlateList[idRP].seedingId+'_'+revisionPlateList[idRP].id).attr('data-cmid',revisionPlateList[idRP].seedingId);
        
        if (revisionPlateList[idRP].time.length > 0){
            $('#step5R'+i).val(revisionPlateList[idRP].time);
        }

        if (revisionPlateList[idRP].status.length > 0){
            if (revisionPlateList[idRP].status.toLowerCase().indexOf("definitivo")>-1){
                $('#step5S'+i).val("Neg. Definitivo");   
            }
            else if (revisionPlateList[idRP].status.toLowerCase().indexOf("parcial")>-1){
                $('#step5S'+i).val("Neg. Parcial");     
            }
            else {
                $('#step5S'+i).val(revisionPlateList[idRP].status);
            }
            //$('#step5S'+i).attr("disabled", true);
        }

        if (revisionPlateList[idRP].note.length > 0){
            $('#step5N'+i).val(revisionPlateList[idRP].note);
        }

        //$('#Step5RP_'+revisionPlateList[idRP].seedingId+'_'+revisionPlateList[idRP].id).find("input,button,textarea,select").attr("disabled", true);
        
        i= i+1;
        
    }

    
    
    for (let culture in cultureIndexArray) {
        var CMname = "";
        $("#tableSelectedCMDetails").DataTable().rows().every(function () {
            var data = this.data();
            if (data[0] == test.cultureMedium[cultureIndexArray[culture]].cultureId) {
                CMname = data[1];
            }
        });

        t.row.add(
            [   'Step5RP_'+test.cultureMedium[cultureIndexArray[culture]].id+'_0',
                '<label class="col-form-label" id="step5CM' + i + ' "/>'+CMname+'</label>',
                '<select id="step5R'+i+'" data-type = "R" data-CM = "'+ test.cultureMedium[cultureIndexArray[culture]].cultureId +
                '" onchange="updateStep5Element(this);" class="form-control">' + 
                revisionTimeSelect, 
                '<select id="step5S'+i+'" data-type = "S" data-cmid="'+test.cultureMedium[cultureIndexArray[culture]].id+'" data-rowid = "'+i+
                '" data-CM = "'+ test.cultureMedium[cultureIndexArray[culture]].cultureId +
                '" onchange="updateStep5Element(this);" class="form-control">' + 
                stateSelect, 
                '<input id="step5N'+i+'" onchange="updateStep5Element(this);" data-type = "N" data-CM = "'+ test.cultureMedium[cultureIndexArray[culture]].cultureId +
                '" class="form-control" value=""></input>',
                '<div class="float" style="text-align: left; float: left;">'+
                    '<a  onclick="eraseStep5Element(this);" data-rowid = "'+i+'" id="step5Trash'+i+'" class="navi-link">' +
                        '<span class="symbol symbol-40 mr-3 ">' +
                            '<span id="Step5Trashbtn'+i+'" class="symbol-label"' +
                                'href="" data-toggle="tooltip" title="Eliminar">' +
                                '<i class="la la-trash icon-xl text-primary">' +
                                '</i>' +
                            '</span>' +
                        '</span>' +
                        '<span class="navi-text"></span>' +
                    '</a>' +
                '</div>'

            ]
        ).node().id = 'Step5RP_'+test.cultureMedium[cultureIndexArray[culture]].id+'_0';
        t.draw();

        $('Step5RP_'+test.cultureMedium[cultureIndexArray[culture]].id+'_0').attr('data-rowid',i);
        $('Step5RP_'+test.cultureMedium[cultureIndexArray[culture]].id+'_0').attr('data-cmid',test.cultureMedium[cultureIndexArray[culture]].id);
        i= i+1;

    }
    
    var nRows = $("#tablePlateReviewDetails").DataTable().rows().count();
    if (nRows > 0){
        checkStep5State(false);
    }
    disableIfEnded(5);
}

function eraseStep5Element(sel){
    var rowId = $(sel).closest('tr').attr('id');

    var pmId = parseInt(rowId.split("_")[1]);
    var regId = parseInt(rowId.split("_")[2]);
    

    if (regId > 0){
        var t = $("#tablePlateReviewDetails").DataTable();
        
        t.row("#"+ rowId).remove();
        t.draw();
        deleteObs(pmId, regId);
    }
    else{
        alert("No se puede borrar el registro")
    }

}

function deleteObs(pmId, nRevision){
    $.ajax({
        url: "Microbiologia/api/delRevisionPlacas",
        data: JSON.stringify({
            seedingId: nRevision,
            revision: pmId
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

function getRevisionTimesSelectForm(optionsArray){
    let txtSelect = '<option>  </option>'
    for (let o in optionsArray) {
        txtSelect = txtSelect+'<option>'+optionsArray[o]+'</option>'
      }
    txtSelect = txtSelect + '</select>';
    return txtSelect;
}

function checkIfPosteriorElementExists(idRow){
    var [idName, cmId, revNumber] = idRow.split("_");
    let numerator = parseInt(revNumber)+1;
    var newId = idName+"_"+cmId+"_"+numerator;

    if($('#'+newId).length){
        return true;
    }else{
        return false;
    }

}

function updateStep5Element(sel){
    var idRow = $(sel).closest('tr').attr('id');
    if (checkIfPosteriorElementExists(idRow) & sel.getAttribute('data-type')=="S"){
        alert("No se puede modificar estado intermedio")
        $('#'+sel.getAttribute('id')).val("Neg. Parcial")
    }
    else{
        saveRevision(sel);
        
        if(sel.getAttribute('data-type')=="S"){
            if (checkStep5State(true)){
                closeFromStep5();
            }

            if (sel.value.toLowerCase().indexOf("parcial") > -1){

                var t = $("#tablePlateReviewDetails").DataTable();
                
                var newRow = [];
                $("#"+idRow+" > td").each(function() {
                    newRow.push(this);
                });

                var cmid = sel.getAttribute('data-cmid');
                var maxId = getMaxId(cmid);

                var newId = "Step5RP_"+cmid+'_'+maxId;

                t.row.add(
                    [
                        newId,
                        newRow[0].outerHTML,
                        newRow[1].outerHTML,
                        newRow[2].outerHTML,
                        newRow[3].outerHTML,
                        newRow[4].outerHTML
                    ]
                ).node().id = newId;
                t.draw();

                $('#'+newId+' .symbol-label').addClass('bg-hover-primary  hoverIcon'); 
            }
            //$('#'+sel.getAttribute('id')).prop("disabled", true);
            }

        }
}

function saveRevision(sel){
    let idRow = $(sel).closest('tr').attr('id');
    var [idName, cmId, revNumber] = idRow.split("_");

    let  newRow = [];
    newRow.push($('#'+idRow).find('td:nth-child(2) select option:selected').text());
    newRow.push($('#'+idRow).find('td:nth-child(3) select option:selected').text()); 
    newRow.push($('#'+idRow).find('td:nth-child(4) input').val());
    newRow.push(cmId);
    newRow.push(revNumber);

    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();
    
    $.ajax({
        url: "Microbiologia/api/putRevisionPlacas",
        data: JSON.stringify({
            seedingId: cmId, 
            revision: revNumber,
            time: newRow[0],
            status: newRow[1],
            note: newRow[2]
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

function getMaxId(cmid){

    var maxId = 0;
    t = $("#tablePlateReviewDetails").DataTable();

    $("#tablePlateReviewDetails > tbody  > tr").each(function() {
        var elemId = this.id;
        var [idName, row_cmid, rowId] = elemId.split("_");
        if (row_cmid == cmid){
            rowId = parseInt(rowId);
            if (rowId > maxId){
                maxId = rowId;
            }
        }
    });
    return maxId+1;
}

function checkStep5State(flag){

    var values = [];
    var indexValues =[]; 
    var nPositives = 0;
    var nNegDef = 0;
    var index = 0;
    var lastRow = [];

    $("#tablePlateReviewDetails > tbody  > tr").each(function() {
        var elemId = this.id;
        var selectedOption = $(this).find('td:nth-child(3) select').val();
        var [idName, row_cmid, rowId] = elemId.split("_");
        if (values.indexOf(row_cmid) == -1){
            values.push(row_cmid);
            lastRow.push(rowId);
            indexValues.push(selectedOption);
        }
        else {
            index = values.indexOf(row_cmid);
            if (lastRow[index] < rowId)
                indexValues[index]= selectedOption;
                lastRow[index] = rowId;
        }

    });

    var nCMID = indexValues.length;

    for (element in indexValues){
        
        if (indexValues[element].toLowerCase() == "positivo"){
            nPositives = nPositives + 1;
        }
        else if (indexValues[element].toLowerCase().indexOf("definitivo") > -1){
            nNegDef = nNegDef + 1;
        }
    }

    if (nPositives > 0){
        $('#chkStep5IsReady').prop("disabled", false);
    }

    else {
        $('#chkStep5IsReady').prop("disabled", true);
    }

    if (nNegDef == nCMID){
        $('#chkStep5IsReady').prop("disabled", false);
        $('#chkStep5PreReport').prop("disabled", false);
        
        if (flag){
            return true;
        }
    }

    else if (nPositives+nNegDef == nCMID){
        $('#chkStep5IsReady').attr("checked", true);
        $('#chkStep5IsReady').prop("disabled", false);

        completeStep5();

    }
    return false;   
}


function putTestCultureMediumParameters(cultureMedium, value, valueType) {
    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();

    var dataRequest = {
        orderId: orderId,
        examId: examId,
        testId: testId,
        cultureId: cultureMedium,
        sampleId: $("#txtTaskStep4SampleTypeId").val()
    }

    $.ajax({
        url: "Microbiologia/api/putTestCultureMedium",
        data: dataRequest,
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

$("#chkStep5IsReady").click(function () {
    if ($("#chkStep5IsReady").prop("checked")) {
        completeStep5();
        
    }
});

function completeStep5(loading = false) {
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();

    if (!$('#chkStep5IsReady').is(':checked')) {
        $('#chkStep5IsReady').attr("checked", true);
    }
    $('#chkStep5IsReady').prop("disabled", true);
    

    if (!loading) {
        $.ajax({
            url: "Microbiologia/api/putTask",
            data: JSON.stringify({
                sampleId: sampleId,
                examId: examId,
                testId: testId,
                task: "REV. PLACAS"
            }),
            headers: { 'Content-Type': 'application/json' },
            method: 'put',
            dataType: 'json',
            success: function (test) {
                setGlobalStepArrayValue(5);
                disableIfEnded(5)
                // Notificar al usuario que la tarea se ha completado
            },
            error: function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
                $('#chkStep5IsReady').prop("disabled", false);
                $('#chkStep5IsReady').click();
            }
        });

    }
}