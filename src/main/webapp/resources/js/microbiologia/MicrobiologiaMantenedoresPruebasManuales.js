$(document).ready(function () {
    clearPruebasManualesSearch();
    clearPruebasManualesDetails();
    showPruebasManualesList();
    bindInputFilter();
})

$("#btnClearPMFilter").click(function () {
    clearPruebasManualesSearch();
    clearPruebasManualesDetails();
})

$("#btnNewPM").click(function () {
    centerViewTo("#formPMDetails");
    clearPruebasManualesDetails();
    enablePMDetailsEdit();
})

$("#btnClearPMDetails").click(function () {
    clearPruebasManualesDetails();
})

$("#btnEditPMDetails").click(function () {
    enablePMDetailsEdit();
})

$("#btnSavePMDetails").click(function () {
    if (!$("#btnSavePMDetails").prop("disabled")) {
        savePruebasManualesDetails();
        clearPruebasManualesSearch();
        showPruebasManualesList();
    }
})

$('#tablePMFilter tbody').on('click', 'tr', function () {
    showPruebasManualesDetail($(this).data('id'))
});

$('#chkActivePMFilter').on('change', function () {
    var rows = $('#DataPMFilter tr');
    $('.mb-encontrados').html('');
    if ($('#chkActivePMFilter').prop('checked')) {
        rows.each(function() {
            var activo = $(this).data('activo');
            $(this).toggle(activo === 'S');
        });
        let filteredRows = rows.filter(function() {
            return $(this).data('activo') === 'S';
        });
        let count = filteredRows.length;
        $('.mb-encontrados').html(`Pruebas manuales encontradas: ${count}`)
    } else {
      rows.show();
        $('.mb-encontrados').html(`Pruebas manuales encontradas: ${rows.length}`)
    }
})

$("#cbActivePMDetails").click(function () {
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo")
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo")
    }
})

function bindInputFilter() {
    const inputCode = $('#txtCodePMFilter');
    const inputName = $('#txtNamePMFilter');
    const tableRows = $('#DataPMFilter');
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
        $('.mb-encontrados').html(`Pruebas manuales encontradas: ${count}`)
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
        $('.mb-encontrados').html(`Pruebas manuales encontradas: ${count}`)
    })
}

function showPruebasManualesList() {
    $('#tablePMFilterContainer').scrollTop(0);
    $("#DataPMFilter").html("");
    $.getJSON("Microbiologia/Mantenedores/api/getPMList", { code: $("#txtCodePMFilter").val(), name: $("#txtNamePMFilter").val(), activeOnly: $("#chkActivePMFilter").prop("checked") }, function (cultureMediumList) {
        $('.mb-encontrados').html('');
        $('.mb-encontrados').html(`Pruebas manuales encontradas: ${cultureMediumList.length}`)
        cultureMediumList.forEach(function (cultureMedium) {
            $("#DataPMFilter")
            .append(
                `<tr class='' data-id='${cultureMedium.id}' data-activo='${cultureMedium.active}'>
                    <td>${cultureMedium.code}</td>
                    <td>${cultureMedium.name}</td>
                </tr>`
                );
        })
    });
}

function clearPruebasManualesSearch() {
    $("#lblPMFilterError").prop("hidden", "hidden");
    $('#tablePMFilterContainer').scrollTop(0);
    $("#txtCodePMFilter").val('');
    $("#txtNamePMFilter").val('');
    if ($("#chkActivePMFilter").prop("checked") == true) {
        $("#chkActivePMFilter").click()
    }
}

function clearPruebasManualesDetails() {
    disablePMDetailsEdit();
    $("#txtIdPMDetails").val('');
    $("#txtCodePMDetails").val('');
    $("#txtNamePMDetails").val('');
    $("#txtLISBacPMDetails").val('');
    $(".switch-content").removeClass("disabled inactivo");
    setActivePMDetailsState(false)
}

function showPruebasManualesDetail(id) {
    centerViewTo("#formPMDetails");
    clearPruebasManualesDetails();

    $.getJSON("Microbiologia/Mantenedores/api/getPM", { id: id })
        .done(function (pm) {
            $("#txtIdPMDetails").val(pm.id);
            $("#txtCodePMDetails").val(pm.code);
            $("#txtNamePMDetails").val(pm.name);
            $("#txtLISBacPMDetails").val(pm.sort);
            pm.active === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            setActivePMDetailsState(pm.active == "S")
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function savePruebasManualesDetails() {
    var dict = {
        "id": $("#txtIdPMDetails").val(),
        "code": $("#txtCodePMDetails").val(),
        "name": $("#txtNamePMDetails").val(),
        "sort": $("#txtLISBacPMDetails").val(),
        "active": false,
    };
    if ($("#cbActivePMDetails").prop("checked") == true) {
        dict["active"] = true
    }

    $.ajax({
        url: "Microbiologia/Mantenedores/api/putPM",
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

function setActivePMDetailsState(state) {
    if ($("#cbActivePMDetails").prop("checked") != state) {
        let previous_state = $("#cbActivePMDetails").prop("disabled");
        $("#cbActivePMDetails").prop("disabled", false);
        $("#cbActivePMDetails").click();
        $("#cbActivePMDetails").prop("disabled", previous_state)
        if(previous_state){
            $(".switch-content").addClass("disabled");
           }else{
           $(".switch-content").removeClass("disabled");
           }
    }
}

function enablePMDetailsEdit() {
    $("#txtCodePMDetails").prop("disabled", false);
    $("#txtNamePMDetails").prop("disabled", false);
    $("#txtLISBacPMDetails").prop("disabled", false);
    $("#cbActivePMDetails").prop("disabled", false);
    $("#btnSavePMDetails").prop("disabled", false);
    $(".switch-content").removeClass("disabled");
}

function disablePMDetailsEdit() {
    $("#txtCodePMDetails").prop("disabled", true);
    $("#txtNamePMDetails").prop("disabled", true);
    $("#txtLISBacPMDetails").prop("disabled", true);
    $("#cbActivePMDetails").prop("disabled", true);
    $("#btnSavePMDetails").prop("disabled", true);
    $(".switch-content").addClass("disabled");
}