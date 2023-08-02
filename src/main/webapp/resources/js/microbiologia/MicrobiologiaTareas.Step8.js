var ABinterpretationSelect; 

$(document).ready(function () {
    $('#tableStep8OptABModalDetails').DataTable({
        "dom": "t",
        "language": {
            "emptyTable": "No hay antibióticos registrados"
        },
        "paging": false,
        columnDefs: [
            {"targets": [0],
            "visible": false,
            "searchable": false
            }
        ]
    });

    $('#tableStep8ABDetails').DataTable({
        "dom": "t",
        "language": {
            "emptyTable": "No hay antibiograma seleccionado"
        },
        "paging": false, 
        columnDefs: [
            {"targets": [0], 
            "visible": false, 
            "searchable": false
            },
            {"targets": [1,2, 3, 4],
            "orderable": false}, 
            {"width": "35%", 
            "targets": [1,4] }
        ]
    });

    $("#tableAvailableAddABModal").DataTable({
        "dom": "t",
        "select": true,
        "scrollY": "200px",
        "scrollCollapse": true,
        "paging": false,
        "language": {
            "emptyTable": "Cargando lista de antibioticos..."
        }
    });
    
    $('#tableSelectedAddABModal').DataTable({
        "dom": "t",
        "scrollY": "200px",
        "scrollCollapse": true,
        "paging": false,
        "select": true,
        columnDefs: [{
            "targets": 1,
            'searchable': false,
            'orderable': false,
            'className': 'dt-body-center'
        }
     ]
    });

    $('#tableStep8MarkersDetails').DataTable({
        "dom": "t",
        "language": {
            "emptyTable": "No hay marcadores registrados"
        },
        paging: false,
        searching: false,
        "order":[[0,"asc"]],
        columnDefs: [ 
            {"width": "60%", 
            "targets": [0] }, 
            {"width": "40%", 
            "targets": [1] }, 
            {"targets": [0,1],
            "searchable": false
            }, 
            {
                "targets": [0,1],
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center'
            }
        ]
    });
    
    setABInterpretation();
});

function LoadStep8Tables() {
    $("#tableStep8ABDetails").DataTable().clear().draw();
    $("#tableStep8MarkersDetails").DataTable().clear().draw();
    $("#tableAvailableAddABModal").DataTable().clear().draw();
    $("#tableSelectedAddABModal").DataTable().clear().draw();
    $("#tableStep8OptABModalDetails").DataTable().clear().draw();

    loadStep8AntibiogramSelect();
    getTestMO();
    loadStep8ResistanceMarkersSelect();
    
}

function getTestMO(){
    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();
    $.getJSON("Microbiologia/api/getTestMOList", { orderId: orderId, examId: examId, testId: testId })
        .done(function (testMOList) {
            let nMO = testMOList.length; 
            let nRow = 0;
            let txtSelect = '<option data-ag="" value="" data-name=""></option>'
            if (nMO>0){
                for (let o in testMOList) {
                    nRow = parseInt(o)+parseInt(1);
                    txtSelect = txtSelect+'<option data-cc="'+testMOList[o].colonyCountId+
                    '" data-it="' + testMOList[o].infectionTypeId +
                    '" data-ag="' + testMOList[o].antibiogramId + 
                    '" data-rid="' + testMOList[o].resistanceId +
                    '" value="' + testMOList[o].microorganismId + 
                    '" data-name= "' + testMOList[o].microorganismName +
                    '"  >' + nRow + ' </option>'
                }
                $("#selTaskStep8MONumber").html(txtSelect);
            }
            
            $("#txtTaskStep8MONumber").val(nMO);

        })

        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });

}

function loadStep8AntibiogramSelect() {
    
    $.getJSON("Microbiologia/Mantenedores/api/getAGList", {code: "", name: "", activeOnly: true})
    .done(function (antibiogramOptions) {

        let txtSelect = '<option>  </option>'
        for (let o in antibiogramOptions) {
            txtSelect = txtSelect+'<option value="' + antibiogramOptions[o].id + '" >' + antibiogramOptions[o].name + ' </option>'
        }

        $("#step8AntibiogramSelect").html(txtSelect);

    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });

}

function loadStep8ResistanceMarkersSelect(){
    $.getJSON("Microbiologia/Mantenedores/api/getRMList", { code: "", name: "", activeOnly: true })
    .done(function (RMList) {
        loadStep8RMResultsSelect(RMList);
        
        
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function loadStep8RMResultsSelect(RMList){
    $.getJSON("Microbiologia/api/getResistanceMethodResultList")
    .done(function (resultList) {
        let RMSelect = getStep7SelectForm(RMList);
        let resultSelect = getStep7SelectForm(resultList);
        var t = $("#tableStep8MarkersDetails").DataTable();
        t.row.add(
        [   
            '<select id="Step8RM_N_blank_blank" onchange="newStep8MarkerElement(this);" class="form-control">' + 
            RMSelect, 
            '<select id="Step8RM_R_blank_blank" onchange="putMOResistance(this);" class="form-control">' + 
            resultSelect
        ]
        ).node().id = 'Step8RM_blank';
        t.draw();
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function newStep8MarkerElement(sel){
    let moId = $("#selTaskStep8MONumber").find('option:selected').val();
    if (moId == undefined){
        moId = "";
    }
    if (moId.length>0){

    let idRow = '';
    let id = "";
    let name = "";
    let resultId = "";
    if ( sel.value == undefined){
        idRow = 'Step8RM_blank';
        id = sel[0];
        resultId = sel[1];
    }
    else {
        idRow = $(sel).closest('tr').attr('id');
        id = $('#'+idRow).find('td:nth-child(1) select option:selected').val();
        name = $('#'+idRow).find('td:nth-child(1) select option:selected').text();
        resultId = $('#'+idRow).find('td:nth-child(2) select option:selected').text();
    }

    var t = $("#tableStep8MarkersDetails").DataTable();
    var newRow = [];
    $("#"+idRow+" > td").each(function() {
        newRow.push(this);
    });
    var newId = "Step8RM_"+id;
    t.row.add(
        [
            newRow[0].outerHTML,
            newRow[1].outerHTML
        ]
    ).node().id = newId;
    t.draw();

    if ( sel.value == undefined){
        $('#'+newId).find('td:nth-child(1) option[value='+id+']').attr('selected', true);
        $('#'+newId).find('td:nth-child(2) option[value='+resultId+']').attr('selected', true);
    }
    else {
        if ($('#'+idRow).find('td:nth-child(2) option:selected').val().length > 0){
            putMOResistance(sel);
        }
    }
    }
    else{
        $('#Step8RM_blank').find('td:nth-child(1) select').val('');

    }
}

function changeMOSelector(sel){
    let name = $(sel).find('option:selected').attr('data-name');
    let ag = $(sel).find('option:selected').attr('data-ag');
    $("#txtTaskStep8MOName").val(name);
    let rid = $(sel).find('option:selected').attr('data-rid');

    var elemId = "";
    var ABDetailsTable = $("#tableStep8ABDetails").DataTable();
    ABDetailsTable.clear().draw();
    var optTable = $("#tableStep8OptABModalDetails").DataTable();
    optTable.clear().draw();
    var avTable = $("#tableAvailableAddABModal").DataTable();
    avTable.clear().draw();
    var selTable = $("#tableSelectedAddABModal").DataTable();
    selTable.clear().draw();

    if (ag.length>0){
        loadAGData(ag);
    }
    else{
        $("#step8AntibiogramSelect").val("");
    }

    loadRMData(rid);

}

function updateAntibiogramMO(AGId){
    let examId = getExamId();
    let testId = getTestId();
    let orderId = getOrderId();
    let rowElement = {};
    let microorganismId = $("#selTaskStep8MONumber").find('option:selected').val();
    let name = $("#selTaskStep8MONumber").find('option:selected').attr('data-name');
    rowElement ={ 
        orderId: orderId,
        examId: examId,
        testId: testId,
        microorganismId: microorganismId,
        microorganismName: name,
        antibiogramId : AGId, 
        colonyCountId: $("#selTaskStep8MONumber").find('option:selected').attr('data-cc'),
        infectionTypeId: $("#selTaskStep8MONumber").find('option:selected').attr('data-it'), 
        resistanceId: $("#selTaskStep8MONumber").find('option:selected').attr('data-rid')
    }

    
    $.ajax({
        url: "Microbiologia/api/putTestMO",
        data: JSON.stringify(rowElement),
        headers: { 'Content-Type': 'application/json' },
        method: 'put',
        dataType: 'json',
        success: function (test) {
            $("#selTaskStep8MONumber").find('option:selected').attr('data-ag', AGId);
            $("#selTaskStep8MONumber").find('option:selected').attr('data-rid', test.resistanceId);


        },
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });

}

function deleteABTable(){
    var elemId = "";
    $("#tableStep8ABDetails > tbody  > tr").each(function() {
        elemId = this.id;
        var [idName, cmId, idAB] = elemId.split("_");
        if (elemId != ""){
            deleteMOAntibiotic(idAB);
        }
    });

}

function loadAGData(sel){
    let valueAG = '';
    let onlyMOChange = false;
    if (sel.value == undefined){
        valueAG = sel;
        $("#step8AntibiogramSelect").val(valueAG);
        onlyMOChange = true;
    }
    else {
        valueAG = sel.value;
    }

    let microorganismId = $("#selTaskStep8MONumber").find('option:selected').val();
    if (microorganismId == undefined){
        microorganismId = "";
    }
    if (microorganismId.length > 0){       
        $.getJSON("Microbiologia/Mantenedores/api/getAG", {id: valueAG})
        .done(function (antibiogram) {
        
        deleteABTable();
        updateAntibiogramMO(valueAG);
        AGIndexes = [];

        var mainTable = $("#tableStep8ABDetails").DataTable();
        mainTable.clear().draw();
        var optTable = $("#tableStep8OptABModalDetails").DataTable();
        optTable.clear().draw();

        let i = 0;
        if (antibiogram.antibiotics.length > 0){
        for (let o in antibiogram.antibiotics) {
            AGIndexes.push(antibiogram.antibiotics[o].id);
            if (antibiogram.antibiotics[o].optional == "S"){
                
                optTable.row.add(
                    [
                        antibiogram.antibiotics[o].id,
                        antibiogram.antibiotics[o].name, 
                        '<input onchange="includeABFromOptToMain(this);" class="form-check-input-reverse" type="checkbox" value="" id="chkStep8Opt_'+antibiogram.antibiotics[o].id+'">'
                    ]
                ).node().id = 'Step8_OptAB_'+antibiogram.antibiotics[o].id;
                optTable.draw();
            }
            else{
                addABToMainTable(antibiogram.antibiotics[o].id, antibiogram.antibiotics[o].name)
                
            }
            i = i+1;
        }

        }

        loadAntibioticsList(AGIndexes);
        })
        .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        $("#step8AntibiogramSelect").val("");
        });
    }
    else {
        alert("Debes seleccionar un microorganismo primero");
        $("#step8AntibiogramSelect").val("");
    }
}

function loadAntibioticsList(AGIndexes){
    var avTable = $("#tableAvailableAddABModal").DataTable();
    avTable.clear().draw();

    var selTable = $("#tableSelectedAddABModal").DataTable();
    selTable.clear().draw();

    $("#tableAvailableAddABModal").DataTable().column(0).visible(false);

    $.getJSON("Microbiologia/Mantenedores/api/getABList")
    .done(function (antibioticOptions) {
        antibioticOptions.forEach(function (antibiotic) {
            if (AGIndexes.indexOf(antibiotic.id) == -1){
            $("#tableAvailableAddABModal").DataTable().row.add(
                [
                    antibiotic.id,
                    "[" + antibiotic.code + "] " + antibiotic.name
                ]
            ).node().id = 'Step8_AvaAB_'+antibiotic.id;
            $("#tableAvailableAddABModal").DataTable().draw(); 
            }
        });
        let moId = $("#selTaskStep8MONumber").find('option:selected').val();
        if (moId.length > 0){
            loadMOAntibioticList(moId);
        }
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
        });
}

function loadMOAntibioticList(moId){
    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();

    $.getJSON("Microbiologia/api/getMOAntibioticsList", { orderId: orderId, examId: examId, testId: testId, moId: moId })
    .done(function (antibioticList) {
        let isInOptional = false;
        let isInAvailable = false;
        antibioticList.forEach(function (antibiotic) {
            isInOptional = checkIfABInTable("tableStep8OptABModalDetails", antibiotic.antibioticId);
            isInAvailable = checkIfABInTable("tableAvailableAddABModal", antibiotic.antibioticId);

            if(isInOptional){
                $("#chkStep8Opt_"+antibiotic.antibioticId).prop('checked', true);
                addABToMainTable(antibiotic.antibioticId, antibiotic.antibioticName);
            }
            else if (isInAvailable){
                addABToTableModal(antibiotic.antibioticId, antibiotic.antibioticName);
                addABToMainTable(antibiotic.antibioticId, antibiotic.antibioticName);
            }

            $("#Step8_MainAB_"+antibiotic.antibioticId).find('td:nth-child(2) input').val(antibiotic.cim);
            $("#Step8_MainAB_"+antibiotic.antibioticId).find('td:nth-child(3) input').val(antibiotic.diameter);
            if (antibiotic.interpretation == ""){
                $("#Step8I_"+antibiotic.antibioticId).val("");
            }
            else{
                $("#Step8I_"+antibiotic.antibioticId+" option:contains("+antibiotic.interpretation+")").attr('selected', true);
            }
            if (antibiotic.includeInReport == "true"){
                $("#chkStep8_"+antibiotic.antibioticId).prop('checked', true);
            }
        });
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
        });

}

$("#btnAddStep8ABDetails").click(function () {
    if ($("#btnAddStep8ABDetails").prop("disabled")) {
        return;
    }
    
    if (!$("#tableAvailableAddABModal").DataTable().rows('.selected').any()){
        return;
    }

    let data = $("#tableAvailableAddABModal").DataTable().row({ selected: true }).data();
    if (data == null) {
        return;
    }
    $.getJSON("Microbiologia/Mantenedores/api/getAB", { id: data[0] })
        .done(function (ab) {
            addABToTableModal(ab.id, ab.name);
            addABToMainTable(ab.id, ab.name);
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
    $("#tableAvailableAddABModal").DataTable().rows().deselect();
});

function addABToTableModal(id, name) {
    $("#tableSelectedAddABModal").DataTable().row.add(
        [
            id,
            name
        ]
    ).node().id = 'Step8_SelAB_'+id;
    $("#tableSelectedAddABModal").DataTable().draw(); 

    let filter_str = $("#tableSelectedAddABModal").DataTable().column(0).data().join("|");
    $("#tableAvailableAddABModal").DataTable().column(0).search('^(?!(' + filter_str + ')$).*', true, false).draw();


}

$("#btnRemoveStep8ABDetails").click(function () {
    if ($("#btnRemoveStep8ABDetails").prop("disabled")) {
        return;
    }
    if (!$("#tableSelectedAddABModal").DataTable().rows('.selected').any()){
        return;
    }

    let ABId = $("#tableSelectedAddABModal").DataTable().row({ selected: true }).data()[0];
    deleteMOAntibiotic(ABId);
 
});


function includeABFromOptToMain(sel){
    var mainTable = $("#tableStep8ABDetails").DataTable();
    let idRow = $(sel).closest('tr').attr('id');
    var row = $('#'+idRow);
    var [idName, cmId, idAB] = idRow.split("_");
    let name = row.find('td:nth-child(1)').text();
    if ($(sel).prop("checked")){
        addABToMainTable(idAB, name);
    }
    else{
        deleteMOAntibiotic(idAB);
    }
 
}

function setABInterpretation(){
    $.getJSON("Microbiologia/api/getAntibioticResultList")
    .done(function (interpretationList) {
        ABinterpretationSelect = getStep7SelectForm(interpretationList);
    })
    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function getABInterpretation() {
    return ABinterpretationSelect;
}

function addABToMainTable(id, name){
    var mainTable = $("#tableStep8ABDetails").DataTable();
    let interpretationSelect = getABInterpretation();
    mainTable.row.add(
        [
            id,
            name, 
            '<input onchange="getInterpretationM2(this);" id="step8CIM_'+id+'" class="form-control" value=""></input>',
            '<input onchange="getInterpretationM3(this);" id="step8D_'+id+'" class="form-control" value=""></input>',
            '<select onchange="updateMOAntibiotic(this);" id="Step8I_'+id+'" class="form-control">' + interpretationSelect,
            '<input onchange="updateMOAntibiotic(this);" class="form-check-input-reverse" type="checkbox" value="" id="chkStep8_'+id+'">', 
            '<div class="float" style="text-align: left; float: left;">'+
                '<a  onclick="deleteMainAB(this);" data-rowid = "" id="step8Trash_'+id+'_0" class="navi-link">' +
                    '<span class="symbol symbol-40 mr-3">' +
                        '<span id="Step8Trashbtn" class="symbol-label bg-hover-primary hoverIcon"' +
                            'href="" data-toggle="tooltip" title="Eliminar">' +
                            '<i class="la la-trash icon-xl text-primary">' +
                            '</i>' +
                        '</span>' +
                    '</span>' +
                    '<span class="navi-text"></span>' +
                '</a>' +
            '</div>'
        ]
    ).node().id = 'Step8_MainAB_'+id;
    mainTable.draw();
}

function deleteMainAB(sel){
    let idRow = $(sel).closest('tr').attr('id');
    var [idName, cmId, idAB] = idRow.split("_");
    deleteMOAntibiotic(idAB);
}

function removeABFromMainTable(id){
    let isInOptional = checkIfABInTable("tableStep8OptABModalDetails", id);
    let isInSelected = checkIfABInTable("tableSelectedAddABModal", id);

    var mainTable = $("#tableStep8ABDetails").DataTable();
    mainTable.row("#Step8_MainAB_"+ id).remove();
    mainTable.draw(); 
    
    
    if (isInOptional){
        $("#chkStep8Opt_"+id).prop('checked', false);;
    }

    else if (isInSelected){

        $("#tableSelectedAddABModal").DataTable().row("#Step8_SelAB_"+ id).remove().draw();
        let filter_str = $("#tableSelectedAddABModal").DataTable().column(0).data().join("|");
        $("#tableAvailableAddABModal").DataTable().column(0).search('^(?!(' + filter_str + ')$).*', true, false).draw();
        $("#tableSelectedAddABModal").DataTable().rows().deselect();
    }

    else {
         
    }
}

function deleteMOAntibiotic(idAB){
    let examId = getExamId();
    let testId = getTestId();
    let orderId = getOrderId();
    let patientId = getPatientId();
    let microorganismId = $("#selTaskStep8MONumber").find('option:selected').val();

    let rowElement ={ 
        orderId: orderId,
        examId: examId,
        testId: testId,
        patientId: patientId,
        microorganismId: microorganismId,
        antibioticId: idAB
    }

    $.ajax({
        url: "Microbiologia/api/delMOAntibiotics",
        data: JSON.stringify(rowElement),
        headers: { 'Content-Type': 'application/json' },
        method: 'put',
        dataType: 'json',
        success: function (test) {
            removeABFromMainTable(idAB);
        },
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });
    
}

function getInterpretationM2(sel){
    let microorganismId = $("#selTaskStep8MONumber").find('option:selected').val();
    let idRow = $(sel).closest('tr').attr('id');
    var [idName, cmId, idAB] = idRow.split("_");
    let row = $("#"+idRow);
    let cim = row.find('td:nth-child(2) input').val();

    $.getJSON("Microbiologia/api/getAntibiogramResistance", { antibioticId: idAB, microorganismId: microorganismId, method: "2", cim: cim  })
    .done(function (response) {
        if (response.interpretation != ""){
            $("#Step8I_"+idAB+" option:contains("+response.interpretation+")").attr('selected', true);
        }
        else{
            $('#Step8I_'+idAB).val("");
        }
        updateMOAntibiotic(sel);
    })
    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function getInterpretationM3(sel){
    let microorganismId = $("#selTaskStep8MONumber").find('option:selected').val();
    let idRow = $(sel).closest('tr').attr('id');
    var [idName, cmId, idAB] = idRow.split("_");
    let row = $("#"+idRow);
    let radio = row.find('td:nth-child(3) input').val();
    
    $.getJSON("Microbiologia/api/getAntibiogramResistance", { antibioticId: idAB, microorganismId: microorganismId, method: "3", radio: radio  })
    .done(function (response) {
        if (response.interpretation != ""){
            $("#Step8I_"+idAB+" option:contains("+response.interpretation+")").attr('selected', true);
        }
        else{
            $('#Step8I_'+idAB).val("");
        }
        updateMOAntibiotic(sel);
    })
    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function updateMOAntibiotic(sel){
    let examId = getExamId();
    let testId = getTestId();
    let orderId = getOrderId();
    let patientId = getPatientId();
    let microorganismId = $("#selTaskStep8MONumber").find('option:selected').val();
    let idRow = $(sel).closest('tr').attr('id');
    let AGId = $("#step8AntibiogramSelect").find('option:selected').val();
    var [idName, cmId, idAB] = idRow.split("_");
    let row = $("#"+idRow);
    let rowElement = {};
    let interpretationTxt = row.find('td:nth-child(4) select option:selected').text();

    let includeInReport = "false";
    if ($("#chkStep8_"+idAB).prop('checked')){
        includeInReport = "true";
    }
    rowElement ={ 
        orderId: orderId,
        examId: examId,
        testId: testId,
        patientId: patientId,
        antibiogramId: AGId,
        microorganismId: microorganismId,
        antibioticId: idAB, 
        cim: row.find('td:nth-child(2) input').val(),
        diameter: row.find('td:nth-child(3) input').val(),
        includeInReport: includeInReport,
        interpretation: interpretationTxt
    }

    $.ajax({
        url: "Microbiologia/api/putMOAntibiotic",
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

function checkIfABInTable(tableId, idAB){
    var idArray = [];
    $("#" + tableId + " > tbody  > tr").each(function() {
        var rowId = this.id;
        var [idStep, idType, rowIdAB] = rowId.split("_");
        idArray.push(rowIdAB);
    });

    let checkIndex = idArray.indexOf(idAB);
    if (checkIndex > -1){
        return true
    }
    else{
        return false
    }
}

function loadRMData(rid){
    var tableRM = $("#tableStep8MarkersDetails").DataTable();
    $("#tableStep8MarkersDetails > tbody  > tr").each(function() {
        var idRow = this.id;
        var [idName, resistanceId] = idRow.split("_");
        if (resistanceId != 'blank'){
            tableRM.row("#"+idRow).remove();
            tableRM.draw();
        }

    });
    $('#Step8RM_blank').find('td:nth-child(1) select').attr("disabled", false);
    $('#Step8RM_blank').find('td:nth-child(1) select').val("");
    $('#Step8RM_blank').find('td:nth-child(2) select').val("");

    if (rid!=""){
    $.getJSON("Microbiologia/api/getMOResistanceList", { moResistanceId: rid })
    .done(function (resistanceMarkerList) {
        let idRow = ""
        let dataArray =[];
        resistanceMarkerList.forEach(function (RM){
            idRow = "Step8RM_"+RM.marcadorId;
            dataArray = [];
            dataArray.push(RM.marcadorId);
            dataArray.push(RM.resultId);
            newStep8MarkerElement(dataArray)
            $('#'+idRow).find('td:nth-child(1) select').attr("disabled", true)
        })
    })
    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}
}

function putMOResistance(sel){
    let moId = $("#selTaskStep8MONumber").find('option:selected').val();
    let idRow = $(sel).closest('tr').attr('id');

    if (moId.length>0){

    
    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();
    
    let marcadorId = $('#'+idRow).find('td:nth-child(1) select option:selected').val();
    let resultId = $('#'+idRow).find('td:nth-child(2) select option:selected').val(); 

    let MOResistance = {
        orderId: orderId,
        examId: examId,
        testId: testId,
        microorganismId: moId, 
        marcadorId: marcadorId,
        resultId: resultId  
    }

    $.ajax({
    url: "Microbiologia/api/putMOResistance",
    data: JSON.stringify(MOResistance),
    headers: { 'Content-Type': 'application/json' },
    method: 'put',
    dataType: 'json',
    success: function (testMO) {
        $('#'+idRow).find('td:nth-child(1) select').attr("disabled", true)
        if ($("#selTaskStep8MONumber").find('option:selected').attr('data-rid') == ""){
            $("#selTaskStep8MONumber").find('option:selected').attr('data-rid', testMO.resistanceId);
            testMO.colonyCountId = $("#selTaskStep8MONumber").find('option:selected').attr('data-cc');
            testMO.infectionTypeId = $("#selTaskStep8MONumber").find('option:selected').attr('data-it');
            testMO.antibiogramId = $("#selTaskStep8MONumber").find('option:selected').attr('data-ag');
            $.ajax({
                url: "Microbiologia/api/putTestMO",
                data: JSON.stringify(testMO),
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
    },
    error: function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    }
    });
}
    else{
        $('#'+idRow).find('td:nth-child(2) select').val('');
    }
}

$("#txtSearchAvailableABDetails").on('keyup', function () {
    $('#tableAvailableAddABModal')
        .DataTable().column(1)
        .search($('#txtSearchAvailableABDetails').val(), false, true)
        .draw();
});

$("#chkStep8IsReady").click(function () {
    if ($("#chkStep8IsReady").prop("checked")) {
        
        completeStep8();
    }
});

function completeStep8(loading = false) {
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();

    if (!$('#chkStep8IsReady').is(':checked')) {
        $('#chkStep8IsReady').attr("checked", true);
    }
    $('#chkStep8IsReady').prop("disabled", true);

    if (!loading) {
        $.ajax({
            url: "Microbiologia/api/putTask",
            data: JSON.stringify({
                sampleId: sampleId,
                examId: examId,
                testId: testId,
                task: "SENSIBILIDAD"
            }),
            headers: { 'Content-Type': 'application/json' },
            method: 'put',
            dataType: 'json',
            success: function (test) {
                $(".step8").find("input,button,textarea,select").attr("disabled", true);
                setGlobalStepArrayValue(8);
                setStep();
                $("#selTaskStep8MONumber").attr("disabled", false);
                $("#Step8RM_R_blank_blank").attr("disabled", false);
                alert("Proceso terminado exitosamente");
            },
            error: function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
                $('#chkStep8IsReady').prop("disabled", false);
                $('#chkStep8IsReady').click();
                alert("Error al guardar, por favor intentar de nuevo");
            }
        });

    }
    //disableIfEndedStep8()
}

$("#saveStepButton").click(function () {
    completeStep8();
});

function disableIfEndedStep8(){
    var steps = getGlobalStepArray();
    if (steps[7]==1){
        $(".step8").find("input,button,textarea,select").attr("disabled", true);
        $("#selTaskStep8MONumber").attr("disabled", false);
    }
}

function enableStep8(){
    $(".step8").find("input,button,textarea,select").attr("disabled", false);
}
