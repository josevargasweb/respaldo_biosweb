$(document).ready(function () {
    clearCultureMediumSearch();
    clearCultureMediumDetails();
    showCultureMediumList();
    bindInputFilter();
})

$("#btnClearCMFilter").click(function () {
    clearCultureMediumSearch();
    clearCultureMediumDetails();
})

$("#btnNewCM").click(function () {
    $("#collapseOne8").collapse('hide')
    centerViewTo("#formCMDetails");
    clearCultureMediumDetails();
    enableCMDetailsEdit();
})

$("#btnClearCMDetails").click(function () {
    clearCultureMediumDetails();
})

$("#btnEditCMDetails").click(function () {
    enableCMDetailsEdit();
})

$("#btnSaveCMDetails").click(function () {
    if (!$("#btnSaveCMDetails").prop("disabled")) {
        saveCultureMediumDetails();
    }
})

$('#tableCMFilter tbody').on('click', 'tr', function () {
    showCultureMediumDetail($(this).data('id'))
});

$('#chkActiveCMFilter').on('change', function () {
    var rows = $('#DataCMFilter tr');
    $('.mb-encontrados').html('');
    if ($('#chkActiveCMFilter').prop('checked')) {
        rows.each(function() {
            var activo = $(this).data('activo');
            $(this).toggle(activo === 'S');
        });
        let filteredRows = rows.filter(function() {
            return $(this).data('activo') === 'S';
        });
        let count = filteredRows.length;
        $('.mb-encontrados').html(`Medios de cultivo: ${count}`)
    } else {
      rows.show();
        $('.mb-encontrados').html(`Medios de cultivo: ${rows.length}`)
    }
})

$("#cbActiveCMDetails").click(function () {
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo")
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo")
    }
})

function bindInputFilter() {
    const inputCode = $('#txtCodeCMFilter');
    const inputName = $('#txtNameCMFilter');
    const tableRows = $('#DataCMFilter');
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
        $('.mb-encontrados').html(`Medios de cultivo: ${count}`)
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
        $('.mb-encontrados').html(`Medios de cultivo: ${count}`)
    })
}

function showCultureMediumList() {
    $('#tableCMFilterContainer').scrollTop(0);
    $("#DataCMFilter").html("");
    $.getJSON("Microbiologia/Mantenedores/api/getCMList", { code: $("#txtCodeCMFilter").val(), name: $("#txtNameCMFilter").val(), activeOnly: $("#chkActiveCMFilter").prop("checked") }, function (cultureMediumList) {
        $('.mb-encontrados').html('');
        $('.mb-encontrados').html(`Medios de cultivo: ${cultureMediumList.length}`)
        cultureMediumList.forEach(function (cultureMedium) {
            $("#DataCMFilter")
            .append(
                `<tr class='' data-id='${cultureMedium.id}' data-activo='${cultureMedium.active}'>
                    <td class='codigoTest'>${cultureMedium.code}</td>
                    <td>${cultureMedium.name}</td>
                </tr>`
                );
        })
    });
}

function clearCultureMediumSearch() {
    $("#lblCMFilterError").prop("hidden", "hidden");
    $('#tableCMFilterContainer').scrollTop(0);
    $("#txtCodeCMFilter").val('');
    $("#txtNameCMFilter").val('');
    if ($("#chkActiveCMFilter").prop("checked") == true) {
        $("#chkActiveCMFilter").click()
    }
}

function clearCultureMediumDetails() {
    disableCMDetailsEdit();
    $("#txtIdCMDetails").val('');
    $("#txtCodeCMDetails").val('');
    $("#txtPrefixCMDetails").val('');
    $("#txtNameCMDetails").val('');
    $("#txtLISBacCMDetails").val('');
    $(".switch-content").removeClass("disabled inactivo");
    setActiveCMDetailsState(false)
}

function showCultureMediumDetail(id) {
    centerViewTo("#formCMDetails");
    clearCultureMediumDetails();

    $.getJSON("Microbiologia/Mantenedores/api/getCM", { id: id })
        .done(function (cm) {
            $("#collapseOne8").collapse('hide')
            $("#txtIdCMDetails").val(cm.id);
            $("#txtCodeCMDetails").val(cm.code);
            $("#txtPrefixCMDetails").val(cm.prefix);
            $("#txtNameCMDetails").val(cm.name);
            $("#txtLISBacCMDetails").val(cm.sort);
            cm.active === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            setActiveCMDetailsState(cm.active == "S")
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function saveCultureMediumDetails() {
    var dict = {
        "id": $("#txtIdCMDetails").val(),
        "code": $("#txtCodeCMDetails").val(),
        "prefix": $("#txtPrefixCMDetails").val(),
        "name": $("#txtNameCMDetails").val(),
        "sort": $("#txtLISBacCMDetails").val(),
        "active": false,
    };
    if ($("#cbActiveCMDetails").prop("checked") == true) {
        dict["active"] = true
    }

    $.ajax({
        url: "Microbiologia/Mantenedores/api/putCM",
        data: JSON.stringify(dict),
        headers: { 'Content-Type': 'application/json' },
        method: 'put',
        dataType: 'json',
        success: function (exam) {
            clearCultureMediumSearch();
            showCultureMediumList();
        },
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

function setActiveCMDetailsState(state) {
    if ($("#cbActiveCMDetails").prop("checked") != state) {
        let previous_state = $("#cbActiveCMDetails").prop("disabled");
        $("#cbActiveCMDetails").prop("disabled", false);
        $("#cbActiveCMDetails").click();
        $("#cbActiveCMDetails").prop("disabled", previous_state)
        if(previous_state){
            $(".switch-content").addClass("disabled");
           }else{
           $(".switch-content").removeClass("disabled");
           }
    }
}

function enableCMDetailsEdit() {
    $("#txtCodeCMDetails").prop("disabled", false);
    $("#txtPrefixCMDetails").prop("disabled", false);
    $("#txtNameCMDetails").prop("disabled", false);
    $("#txtLISBacCMDetails").prop("disabled", false);
    $("#cbActiveCMDetails").prop("disabled", false);
    $(".switch-content").removeClass("disabled");
    $("#btnSaveCMDetails").prop("disabled", false);
}

function disableCMDetailsEdit() {
    $("#txtCodeCMDetails").prop("disabled", true);
    $("#txtPrefixCMDetails").prop("disabled", true);
    $("#txtNameCMDetails").prop("disabled", true);
    $("#txtLISBacCMDetails").prop("disabled", true);
    $("#cbActiveCMDetails").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#btnSaveCMDetails").prop("disabled", true);
}