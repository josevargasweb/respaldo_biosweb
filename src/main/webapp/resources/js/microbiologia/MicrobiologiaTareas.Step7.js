var blankRow;

$(document).ready(function () {
    $('#tableStep7MODetails').DataTable({
        "dom": "t",
        "language": {
            "emptyTable": "No hay medios de cultivo"
        },
        "paging": false, 
        "order":[[0,"asc"]],
        columnDefs: [
            {"targets": [0,1,2, 3, 4],
            "orderable": false}, 
            {"width": "40%", 
            "targets": 1 }
        ]
    });
});


function LoadStep7TableData() { 
    $("#tableStep7MODetails").DataTable().clear();

    let orderId = getOrderId();
    let examId = getExamId();
    let testId = getTestId();
    $.getJSON("Microbiologia/api/getTestMOList", { orderId: orderId, examId: examId, testId: testId })
        .done(function (testMOList) {
            getMOSearchOptionsStep7Table(testMOList);
        })

        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function getMOSearchOptionsStep7Table(testMOList) {
    $.getJSON("Microbiologia/Mantenedores/api/getMOList", {code: "", name: "", activeOnly: true})
      .done(function (searchOptionsMO) {
        getCCSearchOptionsStep7Table(testMOList, searchOptionsMO)
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      });
    
}

function getCCSearchOptionsStep7Table(testMOList, searchOptionsMO) {
    $.getJSON("Microbiologia/Mantenedores/api/getCCList", { code: "", name: "", activeOnly: true })
      .done(function (searchOptionsCC) {
        getITSearchOptionsStep7Table(testMOList, searchOptionsMO, searchOptionsCC);
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      });
    
}

function getITSearchOptionsStep7Table(testMOList, searchOptionsMO, searchOptionsCC) {
    $.getJSON("Microbiologia/api/getInfectionTypeList")
      .done(function (searchOptionsIT) {
        loadStep7Table(testMOList, searchOptionsMO, searchOptionsCC, searchOptionsIT);
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      });
    
}



function loadStep7Table(testMOList, searchOptionsMO, searchOptionsCC, searchOptionsIT) {
    var MOSelect = getStep7SelectMOForm(searchOptionsMO);
    var CCSelect = getStep7SelectForm(searchOptionsCC);
    var ITSelect = getStep7SelectITForm(searchOptionsIT);
    
    var t = $("#tableStep7MODetails").DataTable();
    let i = 1;
    for (let MOId in testMOList) {
        t.row.add(
            [   i,
                '<select id="Step7_MO_'+testMOList[MOId].microorganismId+'_'+i+'" class="form-control">' + 
                MOSelect, 
                '<select id="Step7_CC_'+testMOList[MOId].microorganismId+'_'+i+'" class="form-control">' + 
                CCSelect, 
                '<select id="Step7_IT_'+testMOList[MOId].microorganismId+'_'+i+'" class="form-control">' + 
                ITSelect, 
                '<div class="float" style="text-align: left; float: left;">'+
                    '<a  onclick="" data-rowid = "'+i+'" id="step7Trash_'+testMOList[MOId].microorganismId+'_'+i+'" class="navi-link">' +
                        '<span class="symbol symbol-40 mr-3">' +
                            '<span id="Step7Trashbtn'+i+'" class="symbol-label"' +
                                'href="" data-toggle="tooltip" title="Eliminar">' +
                                '<i class="la la-trash icon-xl text-primary">' +
                                '</i>' +
                            '</span>' +
                        '</span>' +
                        '<span class="navi-text"></span>' +
                    '</a>' +
                '</div>'

            ]
        ).node().id = 'Step7_'+testMOList[MOId].microorganismId+'_'+i;
        t.draw();

        //$('#Step7_MO_'+testMOList[MOId].microorganismId+'_'+i).select2(); 

        if (testMOList[MOId].microorganismId.length > 0){
            $('#Step7_MO_'+testMOList[MOId].microorganismId+'_'+i).val(testMOList[MOId].microorganismId);
        }

        if (testMOList[MOId].colonyCountId.length > 0){
            $('#Step7_CC_'+testMOList[MOId].microorganismId+'_'+i).val(testMOList[MOId].colonyCountId);
        }
        

        if (testMOList[MOId].infectionTypeId.length > 0){
            $('#Step7_IT_'+testMOList[MOId].microorganismId+'_'+i).val(testMOList[MOId].infectionTypeId);
        }

        $('#Step7_'+testMOList[MOId].microorganismId+'_'+i).find("input,button,textarea,select").attr("disabled", true);

        i=i+1;
    }

    if (i>1){
        $("#chkStep7IsReady").attr("checked", false)
        $("#chkStep7IsReady").attr("disabled", false)
    }

    var blankValues = [   "",
    '<select id="Step7_MO_blank_blank" onchange="newStep7Element(this);" class="form-control">' + 
    MOSelect, 
    '<select id="Step7_CC_blank_blank" class="form-control">' + 
    CCSelect, 
    '<select id="Step7_IT_blank_blank" class="form-control">' + 
    ITSelect, 
    '<div class="float" style="text-align: left; float: left;">'+
        '<a  onclick="eraseStep7Element(this);" data-rowid = "'+i+'" id="step7Trash_blank_blank" class="navi-link">' +
            '<span class="symbol symbol-40 mr-3">' +
                '<span id="Step7Trashbtn'+i+'" class="symbol-label"' +
                    'href="" data-toggle="tooltip" title="Eliminar">' +
                    '<i class="la la-trash icon-xl text-primary">' +
                    '</i>' +
                '</span>' +
            '</span>' +
            '<span class="navi-text"></span>' +
        '</a>' +
    '</div>'

    ];
    setBlankrow(blankValues);
    if (i<5){
        t.row.add(
            [   "",
                '<select id="Step7_MO_blank_blank" onchange="newStep7Element(this);" class="form-control">' + 
                MOSelect, 
                '<select id="Step7_CC_blank_blank" class="form-control">' + 
                CCSelect, 
                '<select id="Step7_IT_blank_blank" class="form-control">' + 
                ITSelect, 
                '<div class="float" style="text-align: left; float: left;">'+
                    '<a  onclick="eraseStep7Element(this);" data-rowid = "'+i+'" id="step7Trash_blank_blank" class="navi-link">' +
                        '<span class="symbol symbol-40 mr-3">' +
                            '<span id="Step7Trashbtn'+i+'" class="symbol-label"' +
                                'href="" data-toggle="tooltip" title="Eliminar">' +
                                '<i class="la la-trash icon-xl text-primary">' +
                                '</i>' +
                            '</span>' +
                        '</span>' +
                        '<span class="navi-text"></span>' +
                    '</a>' +
                '</div>'

            ]

        ).node().id = 'Step7_blank_blank_blank';
        t.draw();

        //$("#Step7_MO_blank_blank").select2();
    }
    checkStep7State();

    var steps = getGlobalStepArray();
    disableIfEnded(7)
}


function eraseStep7Element(sel){
    var rowId = $(sel).closest('tr').attr('id');
    var MOId = rowId.split("_")[2];
    var t = $("#tableStep7MODetails").DataTable();
    var nRows = t.rows().count();

    if (MOId != "blank" ){
        var tableId = $("#"+rowId).find('td:nth-child(1)').text();
        t.row("#"+ rowId).remove();
        t.draw();
        t = $("#tableStep7MODetails").DataTable();
        var rowIdMO = 0;
        var elemId = "";
        $("#tableStep7MODetails > tbody  > tr").each(function() {
            elemId = this.id;
            editRow = t.row('[id='+elemId+']').index();
            rowIdMO = $("#"+elemId).find('td:nth-child(1)').text();
            if (rowIdMO > tableId && elemId != "Step7_blank_blank_blank"){
                t.cell({row:editRow, column: 0}).data(rowIdMO-1);
            }
        });

        $("#tableStep7MODetails").DataTable().order([0, 'asc' ]).draw();

    }
    else{
        alert("No se puede borrar el registro")
    }
    
    t = $("#tableStep7MODetails").DataTable();
    nRows = t.rows().count();
    if (nRows < 4 && $('#Step7_blank_blank_blank').length<1){
        t.row.add(getBlankRow()).node().id = "Step7_blank_blank_blank";
        t.draw();

        var editRow = t.row('[id=Step7_blank_blank_blank]').index();
        t.cell({row:editRow, column: 0}).data("").draw();

    }

}

function getStep7SelectForm(optionsArray){
    let txtSelect = '<option value=""></option>'
    for (let o in optionsArray) {
        txtSelect = txtSelect+'<option value="' + optionsArray[o].id + '" >' + optionsArray[o].name + '</option>'
      }
    txtSelect = txtSelect + '</select>';
    return txtSelect;
}

function getStep7SelectMOForm(optionsArray){
    let txtSelect = '<option value=""></option>'
    for (let o in optionsArray) {
        txtSelect = txtSelect+'<option value="' + optionsArray[o].id + '" >' + "[" + optionsArray[o].code + "] " + optionsArray[o].name + '</option>'
      }
    txtSelect = txtSelect + '</select>';
    return txtSelect;
}

function getStep7SelectITForm(optionsArray){
    let txtSelect = '<option>  </option>'
    let i = 1;
    for (let o in optionsArray) {
        txtSelect = txtSelect+'<option value="' + i + '" >' + optionsArray[o] + ' </option>'
        i = i + 1;
      }
    txtSelect = txtSelect + '</select>';
    return txtSelect;
}


function newStep7Element(sel){
    
    var t = $("#tableStep7MODetails").DataTable();
    var nRows = t.rows().count();
    var rowId = $(sel).closest('tr').attr('id');
    var blankId = "Step7_blank_blank_blank";
    if (nRows<5 && rowId==blankId){
        
        let rowIdentifiers = ["1","2","3","4"];
        let rowIdMO = "";
        let posIndex = 0;
        $("#tableStep7MODetails > tbody  > tr").each(function() {
            var elemId = this.id;
            rowIdMO = $("#"+elemId).find('td:nth-child(1)').text();
            posIndex = rowIdentifiers.indexOf(rowIdMO);
            if (posIndex > -1){
                rowIdentifiers.splice(posIndex,1);
            }
        });
        let newIdNumber = Math.min(...rowIdentifiers);

        var newId = "Step7_"+sel.value+"_"+newIdNumber;
    
        var idRow = $(sel).closest('tr').attr('id');
        t.row("#"+idRow).nodes().id = newId;
        $('#'+idRow).attr('id', newId);
    
        $('#'+newId+' .symbol-label').addClass('bg-hover-primary  hoverIcon');   
        var editRow = t.row('[id='+newId+']').index();
        t.cell({row:editRow, column: 0}).data(newIdNumber).draw();


        //$('#Step7_MO_'+sel.value+"_"+nRows).select2();

        
    }

    t = $("#tableStep7MODetails").DataTable();
    var nRows = t.rows().count();
    if (nRows < 4 && $('#Step7_blank_blank_blank').length<1){
        t.row.add(getBlankRow()).node().id = "Step7_blank_blank_blank";
        t.draw();
        var editRow = t.row('[id=Step7_blank_blank_blank]').index();
        t.cell({row:editRow, column: 0}).data("").draw();
    }

    $("#tableStep7MODetails").DataTable().order([0, 'asc' ]).draw();
    checkStep7State();
}

function putStep7Table() {
    let examId = getExamId();
    let testId = getTestId();
    let orderId = getOrderId();
    let orderDate = getOrderDate();

    var tableData = [];
    var rowElement = {};
    var nEnabled = 0; 
    var MOName = "";
    $("#tableStep7MODetails > tbody  > tr").each(function() {
        var elemId = this.id;
        nEnabled = $("#"+elemId+" select:enabled").length
        MOName = $("#"+elemId).find('td:nth-child(2) select option:selected').text();

        if (elemId != "Step7_blank_blank_blank" && nEnabled>0 && MOName.length>0){
            rowElement ={ 
                orderId: orderId,
                examId: examId,
                testId: testId,
                microorganismId: $(this).find('td:nth-child(2) select').val(),
                microorganismName: MOName,
                colonyCountId: $(this).find('td:nth-child(3) select').val(),
                infectionTypeId: $(this).find('td:nth-child(4) select').val()
            }

            $.ajax({
                url: "Microbiologia/api/putTestMO",
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

function checkStep7State(){
    var nRows = $("#tableStep7MODetails").DataTable().rows().count();
    if (nRows>1){
        $("#chkStep7IsReady").attr("checked", false)
        $("#chkStep7IsReady").attr("disabled", false)
    }
    else{
        $("#chkStep7IsReady").attr("checked", false)
        $("#chkStep7IsReady").attr("disabled", true)
    }
}

$("#chkStep7IsReady").click(function () {
    if ($("#chkStep7IsReady").prop("checked")) {
        putStep7Table();
        $("#tableStep7MODetails").find("input,button,textarea,select").attr("disabled", true);
        completeStep7();
    }
    else{
        $("#tableStep7MODetails").find("input,button,textarea,select").attr("disabled", false);
    }
});

function setBlankrow(values){
    blankRow = values;
}

function getBlankRow(){
    return blankRow;
}

function completeStep7(loading = false) {
    let examId = getExamId();
    let testId = getTestId();
    let sampleId = getSampleId();


    if (!loading) {
        $.ajax({
            url: "Microbiologia/api/putTask",
            data: JSON.stringify({
                sampleId: sampleId,
                examId: examId,
                testId: testId,
                task: "IDENTIFICACION"
            }),
            headers: { 'Content-Type': 'application/json' },
            method: 'put',
            dataType: 'json',
            success: function (test) {
                setGlobalStepArrayValue(7);
                disableIfEnded(7)
            },
            error: function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
                $('#chkStep7IsReady').prop("disabled", false);
                $('#chkStep7IsReady').click();
            }
        });

    }
}