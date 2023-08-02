$(document).ready(function () {
    fillAntibioticSelects();
    clearAntibioticSearch();
    clearAntibioticDetails();
    showAntibioticList();
    bindInputFilter()

    
})

$("#btnClearABFilter").click(function () {
    clearAntibioticSearch();
    clearAntibioticDetails();
})

$("#btnNewAB").click(function () {
    centerViewTo("#formABDetails");
    clearAntibioticDetails();
    enableABDetailsEdit();
})

$("#btnClearABDetails").click(function () {
    clearAntibioticDetails();
})

$("#btnEditABDetails").click(function () {
    enableABDetailsEdit();
})

$("#btnSaveABDetails").click(function () {
    if (!$("#btnSaveABDetails").prop("disabled")) {
        saveAntibioticDetails();
        saveResistances();
        clearAntibioticSearch();
        showAntibioticList();
    }
})

$('#tableABFilter tbody').on('click', 'tr', function () {
    showAntibioticDetail($(this).data('id'))
});


$('#chkActiveABFilter').on('change', function() {
    var rows = $('#DataABFilter tr');
    $('.mb-encontrados').html('');
    if ($('#cbActiveABDetails').prop('checked')) {
        rows.each(function() {
            var activo = $(this).data('activo');
            $(this).toggle(activo === 'S');
        });
        let filteredRows = rows.filter(function() {
            return $(this).data('activo') === 'S';
        });
        let count = filteredRows.length;
        $('.mb-encontrados').html(`Microorganismos encontrados: ${count}`)
    } else {
      rows.show();
        $('.mb-encontrados').html(`Microorganismos encontrados: ${rows.length}`)
    }
  })



  $('#txClassABFilter').on('change', function() {
    const tableBody = $('#tableABFilter');
    const selectedValue = $(this).val();
    const filteredRows = selectedValue === '' ?
      tableBody.find('tr') :
      tableBody.find(`tr[data-class="${selectedValue}"]`);

    tableBody.find('tr').hide();
    filteredRows.show();
    $('.mb-encontrados').html(`Microorganismos encontrados: ${filteredRows.length}`)
  })
  

$("#radMethodABDetails3").click(function () {
    filterAntibioticResistance("3");
})

$("#radMethodABDetails2").click(function () {
    filterAntibioticResistance("2");
})

$("#radMethodABDetails1").click(function () {
    filterAntibioticResistance("1");
})

$("#cbActiveABDetails").click(function () {
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo")
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo")
    }
})

function bindInputFilter() {
    const inputCode = $('#txtCodeABFilter');
    const inputName = $('#txtNameABFilter');
    const tableRows = $('#DataABFilter');
    inputCode.on('keyup', function() {
        let count = 0;
        const searchTerm = inputCode.val().toLowerCase();
        tableRows.find('tr').each(function() {
            const firstTd = $(this).find('td:first');
            const code = firstTd.text().toLowerCase();
            if (code.includes(searchTerm)) {
            $(this).show();
            count++;
            } else {
            $(this).hide();
            }
        })
        $('.mb-encontrados').html(`Antibiogramas encontrados: ${count}`)
    })
  
    inputName.on('keyup', function() {
        let count = 0;
        const searchTerm = inputName.val().toLowerCase();
        tableRows.find('tr').each(function() {
            const secondTd = $(this).find('td:nth-child(2)');
            const name = secondTd.text().toLowerCase();
            if (name.includes(searchTerm)) {
            $(this).show();
            count++;
            } else {
            $(this).hide();
            }
        })
        $('.mb-encontrados').html(`Antibiogramas encontrados: ${count}`)
    })
}



function showAntibioticList() {
    $('#tableABFilterContainer').scrollTop(0);
    $("#DataABFilter").html("");
    $.getJSON("Microbiologia/Mantenedores/api/getABList", { code: "", name: "", class: "", activeOnly: "false" }, function (antibioticList) {
        $('.mb-encontrados').html('');
        $('.mb-encontrados').html(`Antibióticos encontrados: ${antibioticList.length}`)
        antibioticList.forEach(function (antibiotic) {
            $("#DataABFilter")
            .append(
                `<tr class='' data-id='${antibiotic.id}' data-activo='${antibiotic.active}' data-class='${antibiotic.class}'>
                    <td class='codigoTest'>${antibiotic.code}</td>
                    <td>${antibiotic.name}</td>
                </tr>`
                );
        })
    });
}

function clearAntibioticSearch() {
    $("#lblABFilterError").prop("hidden", "hidden");
    $('#tableABFilterContainer').scrollTop(0);
    $("#txtCodeABFilter").val('');
    $("#txtNameABFilter").val('');
    $("#txClassABFilter").val('');
    if ($("#chkActiveABFilter").prop("checked") == true) {
        $("#chkActiveABFilter").click()
    }
    // $("#tableABFilter").DataTable().columns().every(function () {
    //     this.search("", false, true).draw();
    // })
}

function clearAntibioticDetails() {
    disableABDetailsEdit();
    $("#txtIdABDetails").val('');
    $("#txtCodeABDetails").val('');
    $("#txtNameABDetails").val('');
    $("#txtLISBacABDetails").val('');
    $("#txtOrderABDetails").val('');
    $("#txClassABDetails").val('');
    $(".switch-content").removeClass("disabled inactivo");
    setActiveABDetailsState(false)
    $("#DataABDetails").empty();
}

function showAntibioticDetail(id) {
    centerViewTo("#formABDetails");
    clearAntibioticDetails();
    $.getJSON("Microbiologia/Mantenedores/api/getAB", { id: id })
        .done(function (ab) {
            $("#txtIdABDetails").val(ab.id);
            $("#txtCodeABDetails").val(ab.code);
            $("#txtNameABDetails").val(ab.name);
            $("#txtLISBacABDetails").val(ab.host);
            $("#txtOrderABDetails").val(ab.sort);
            $("#txClassABDetails").val(ab.class);
            setActiveABDetailsState(ab.active == "true");
            if (ab.antibiotics != null) {
                ab.antibiotics.forEach(function (antibiotic) {
                    addABToTables(antibiotic.id, antibiotic.name, antibiotic.optional);
                });
            }
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
    showAntibioticResistance(id);
}

function showAntibioticResistance(id) {
    $("#DataABDetails").empty();
    $.getJSON("Microbiologia/Mantenedores/api/getABResistanceList", { id: id })
        .done(function (resistanceList) {
            resistanceList.forEach(function (resistance) {
                $("#DataABDetails").append(fillTemplate(resistanceHtmlTemplate, resistance));
                $("#txMOABDetails" + resistance.id).append($("#txMOABDetailsReference option").clone());
                $("#txMOABDetails" + resistance.id).val(resistance.moId);
                $("#txSensibleSignABDetails" + resistance.id).val(resistance.sensibleSign);
                $("#txResistantSignABDetails" + resistance.id).val(resistance.resistantSign);
            })
            filterAntibioticResistance($("input[name=radTypeABDetails]:checked").val());
        });
}

function filterAntibioticResistance(method) {
    $("#DataABDetails tr").each(function () {
        if ($(this).find("input.method").val() == method) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
}

function saveAntibioticDetails() {
    var dict = {
        "id": $("#txtIdABDetails").val(),
        "code": $("#txtCodeABDetails").val(),
        "name": $("#txtNameABDetails").val(),
        "host": $("#txtLISBacABDetails").val(),
        "sort": $("#txtOrderABDetails").val(),
        "class": $("#txClassABDetails").val(),
        "active": false,
    };
    if ($("#cbActiveABDetails").prop("checked") == true) {
        dict["active"] = true
    }

    $.ajax({
        url: "Microbiologia/Mantenedores/api/putAB",
        data: JSON.stringify(dict),
        headers: { 'Content-Type': 'application/json' },
        method: 'put',
        dataType: 'json',
        //success: function (exam) { },
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });
}

function saveResistances() {
    $("#DataABDetails tr").each(function () {
        var id = $(this).find("input.id").val();
        dict = {
            "id": $("#txtIdABDetails" + id).val(),
            "moId": $("#txMOABDetails" + id).val(),
            "abId": $("#txtIdABDetails").val(),
            "sensible": $("#txtSensibleABDetails" + id).val(),
            "sensibleSign": $("#txSensibleSignABDetails" + id).val(),
            "resistant": $("#txtResistantABDetails" + id).val(),
            "resistantSign": $("#txResistantSignABDetails" + id).val(),
            "method": $("#txtMethodABDetails" + id).val()
        };
        $.ajax({
            url: "Microbiologia/Mantenedores/api/putABResistance",
            data: JSON.stringify(dict),
            headers: { 'Content-Type': 'application/json' },
            method: 'put',
            dataType: 'json',
            //success: function (exam) { },
            error: function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
            }
        });
    });
}

function fillAntibioticSelects() {
    $("#txClassABDetails").empty();
    $("#txClassABFilter").empty();
    $("#txClassABDetails").append('<option value="" > - </option>');
    $("#txClassABFilter").append('<option value="" > - </option>');
    $.getJSON("Microbiologia/Mantenedores/api/getABClassOptions", {}, function (classOptions) {
        classOptions.forEach(function (class_) {
            let htmlOption = '<option value="' + class_.id + '" >' + class_.name + '</option>';
            $("#txClassABDetails").append(htmlOption);
            $("#txClassABFilter").append(htmlOption);
        })
    });
    $("#txMOABDetailsReference").empty();
    $.getJSON("Microbiologia/Mantenedores/api/getMOList", {}, function (moOptions) {
        moOptions.forEach(function (mo) {
            let htmlOption = '<option value="' + mo.id + '" >' + mo.name + '</option>';
            $("#txMOABDetailsReference").append(htmlOption);
        })
    });
}

function fillTemplate(template, data) {
    const pattern = /{\s*(\w+?)\s*}/g;
    return template.replace(pattern, (_, token) => data[token] || '');
}

function centerViewTo(element) {
    $('html, body').animate({
        scrollTop: $(element).offset().top
    }, 700);
}

function setActiveABDetailsState(state) {
    if ($("#cbActiveABDetails").prop("checked") != state) {
        let previous_state = $("#cbActiveABDetails").prop("disabled");
        $("#cbActiveABDetails").prop("disabled", false);
        $("#cbActiveABDetails").click();
        $("#cbActiveABDetails").prop("disabled", previous_state)
        if(previous_state){
            $(".switch-content").addClass("disabled");
           }else{
           $(".switch-content").removeClass("disabled");
           }
    }
}

function enableABDetailsEdit() {
    $("#txtCodeABDetails").prop("disabled", false);
    $("#txtNameABDetails").prop("disabled", false);
    $("#txtLISBacABDetails").prop("disabled", false);
    $("#txtOrderABDetails").prop("disabled", false);
    $("#txClassABDetails").prop("disabled", false);
    $("#cbActiveABDetails").prop("disabled", false);
    $(".switch-content").removeClass("disabled");
    $("#btnSaveABDetails").prop("disabled", false);
}

function disableABDetailsEdit() {
    $("#txtCodeABDetails").prop("disabled", true);
    $("#txtNameABDetails").prop("disabled", true);
    $("#txtLISBacABDetails").prop("disabled", true);
    $("#txtOrderABDetails").prop("disabled", true);
    $("#txClassABDetails").prop("disabled", true);
    $("#cbActiveABDetails").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#btnSaveABDetails").prop("disabled", true);
}