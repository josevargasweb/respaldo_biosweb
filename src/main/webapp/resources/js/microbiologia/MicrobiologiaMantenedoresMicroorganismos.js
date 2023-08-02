$(document).ready(function () {
    fillMicroorganismSelects();
    clearMicroorganismSearch();
    clearMicroorganismDetails();
    showMicroorganismList();
    bindInputFilter()
})

$("#btnClearMOFilter").click(function () {
    clearMicroorganismSearch();
    clearMicroorganismDetails();
})

$("#btnNewMO").click(function () {
    centerViewTo("#formMODetails");
    clearMicroorganismDetails();
    enableMODetailsEdit();
})

$("#btnClearMODetails").click(function () {
    clearMicroorganismDetails();
})

$("#btnEditMODetails").click(function () {
    enableMODetailsEdit();
})

$("#btnSaveMODetails").click(function () {
    if (!$("#btnSaveMODetails").prop("disabled")) {
        saveMicroorganismDetails();
        clearMicroorganismSearch();
        showMicroorganismList();
    }
})

$('#tableMOFilter tbody').on('click', 'tr', function () {
    showMicroorganismDetail($(this).data('id'))
});


$('#chkActiveMOFilter').on('change', function() {
    var rows = $('#DataMOFilter tr');
    $('.mb-encontrados').html('');
    if ($('#chkActiveMOFilter').prop('checked')) {
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
  });

$("#cbActiveMODetails").click(function () {
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo")
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo")
    }
})

$("#buscarMuestra").click(function() {
    $("#collapseOne8").collapse('show');
})


function bindInputFilter() {
    const inputCode = $('#txtCodeMOFilter');
    const inputName = $('#txtNameMOFilter');
    const tableRows = $('#DataMOFilter');
    inputCode.on('keyup', function() {
      const searchTerm = inputCode.val().toLowerCase();
      let count = 0;
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
      const searchTerm = inputName.val().toLowerCase();
      let count = 0;
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




function showMicroorganismList() {
    $('#tableMOFilterContainer').scrollTop(0);
    $("#DataMOFilter").html("");
    $.getJSON("Microbiologia/Mantenedores/api/getMOList", { code: "", name: "", activeOnly: "false" }, function (microorganismList) {
        $('.mb-encontrados').html('');
        $('.mb-encontrados').html(`Microorganismos encontrados: ${microorganismList.length}`)
        microorganismList.forEach(function (microorganism) {
            $("#DataMOFilter")
            .append(
                `<tr class='' data-id='${microorganism.id}' data-activo='${microorganism.active}'>
                    <td class='codigoTest'>${microorganism.code}</td>
                    <td>${microorganism.name}</td>
                </tr>`
                );
        })
    });
}

function clearMicroorganismSearch() {
    $("#lblFilterError").prop("hidden", "hidden");
    $('#tableFilterContainer').scrollTop(0);
    $("#txtCodeMOFilter").val('');
    $("#txtNameMOFilter").val('');
    if ($("#chkActiveMOFilter").prop("checked") == true) {
        $("#chkActiveMOFilter").click()
    }
    // $("#tableMOFilter").DataTable().columns().every(function () {
    //     this.search("", false, true).draw();
    // })
}

function clearMicroorganismDetails() {
    disableMODetailsEdit();
    $("#txtCodeMODetails").val('');
    $("#txtNameMODetails").val('');
    $("#txtLISBacMODetails").val('');
    $("#txStainingMODetails").val('');
    $("#txMorphologyDetails").val('');
    $("#txGenderDetails").val('');
    $("#idNoteMODetails").val('');
    $("#txtIdMODetails").val('');
    $(".switch-content").removeClass("disabled inactivo");
    setActiveMODetailsState(false)
    $("#collapseOne8").collapse('show');
}

function showMicroorganismDetail(id) {
    centerViewTo("#formMODetails");
    $.getJSON("Microbiologia/Mantenedores/api/getMO", { id: id })
        .done(function (mo) {
            $("#collapseOne8").collapse('hide');
            $("#txtIdMODetails").val(mo.id);
            $("#txtCodeMODetails").val(mo.code);
            $("#txtNameMODetails").val(mo.name);
            $("#txtLISBacMODetails").val(mo.lisbac);
            $("#txStainingMODetails").val(mo.staining);
            $("#txMorphologyDetails").val(mo.morphology);
            $("#txGenderDetails").val(mo.gender);
            $("#idNoteMODetails").val(mo.notes);
            setActiveMODetailsState(mo.active == "true")
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function saveMicroorganismDetails() {
    var dict = {
        "id": $("#txtIdMODetails").val(),
        "code": $("#txtCodeMODetails").val(),
        "name": $("#txtNameMODetails").val(),
        "lisbac": $("#txtLISBacMODetails").val(),
        "staining": $("#txStainingMODetails").val(),
        "morphology": $("#txMorphologyDetails").val(),
        "gender": $("#txGenderDetails").val(),
        "notes": $("#idNoteMODetails").val(),
        "active": false,
    };
    if ($("#cbActiveMODetails").prop("checked") == true) {
        dict["active"] = true
    }

    $.ajax({
        url: "Microbiologia/Mantenedores/api/putMO",
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

function centerViewTo(element) {
    $('html, body').animate({
        scrollTop: $(element).offset().top
    }, 700);
}

function fillMicroorganismSelects() {
    $("#txGenderDetails").empty();
    $("#txGenderDetails").append('<option value="" > - </option>');
    $.getJSON("Microbiologia/Mantenedores/api/getMOGenderOptions", {}, function (genderOptions) {
        genderOptions.forEach(function (gender) {
            let htmlOption = '<option value="' + gender.id + '" >' + gender.name + '</option>';
            $("#txGenderDetails").append(htmlOption);
        })
    });

    $("#txMorphologyDetails").empty();
    $("#txMorphologyDetails").append('<option value="" > - </option>');
    $.getJSON("Microbiologia/Mantenedores/api/getMOMorphologyOptions", {}, function (morphologyOptions) {
        morphologyOptions.forEach(function (morphology) {
            let htmlOption = '<option value="' + morphology.id + '" >' + morphology.name + '</option>';
            $("#txMorphologyDetails").append(htmlOption);
        })
    });

    $("#txStainingMODetails").empty();
    $("#txStainingMODetails").append('<option value="" > - </option>');
    $.getJSON("Microbiologia/Mantenedores/api/getMOStainingOptions", {}, function (stainingOptions) {
        stainingOptions.forEach(function (staining) {
            let htmlOption = '<option value="' + staining.id + '" >' + staining.name + '</option>';
            $("#txStainingMODetails").append(htmlOption);
        })
    });
}

function enableMODetailsEdit() {
    $("#txtCodeMODetails").prop("disabled", false);
    $("#txtNameMODetails").prop("disabled", false);
    $("#txtLISBacMODetails").prop("disabled", false);
    $("#txStainingMODetails").prop("disabled", false);
    $("#txMorphologyDetails").prop("disabled", false);
    $("#txGenderDetails").prop("disabled", false);
    $("#idNoteMODetails").prop("disabled", false);
    $("#cbActiveMODetails").prop("disabled", false);
    $(".switch-content").removeClass("disabled");
    $("#btnSaveMODetails").prop("disabled", false);
}

function disableMODetailsEdit() {
    $("#txtCodeMODetails").prop("disabled", true);
    $("#txtNameMODetails").prop("disabled", true);
    $("#txtLISBacMODetails").prop("disabled", true);
    $("#txStainingMODetails").prop("disabled", true);
    $("#txMorphologyDetails").prop("disabled", true);
    $("#txGenderDetails").prop("disabled", true);
    $("#idNoteMODetails").prop("disabled", true);
    $("#cbActiveMODetails").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#btnSaveMODetails").prop("disabled", true);
}

function setActiveMODetailsState(state) {
    if ($("#cbActiveMODetails").prop("checked") != state) {
        let previous_state = $("#cbActiveMODetails").prop("disabled");
        $("#cbActiveMODetails").prop("disabled", false);
        $("#cbActiveMODetails").click();
        $("#cbActiveMODetails").prop("disabled", previous_state)
        if(previous_state){
         $(".switch-content").addClass("disabled");
        }else{
        $(".switch-content").removeClass("disabled");
        }
    }
}