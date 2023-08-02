$(document).ready(function () {
    clearResistanceMethodSearch();
    clearResistanceMethodDetails();
    showResistanceMethodList();
    bindInputFilter();
})

$("#btnClearRMFilter").click(function () {
    clearResistanceMethodSearch();
    clearResistanceMethodDetails();
})

$("#btnNewRM").click(function () {
    centerViewTo("#formRMDetails");
    $("#collapseOne8").collapse('hide')
    clearResistanceMethodDetails();
    enableRMDetailsEdit();
})

$("#btnClearRMDetails").click(function () {
    clearResistanceMethodDetails();
})

$("#btnEditRMDetails").click(function () {
    enableRMDetailsEdit();
})

$("#btnSaveRMDetails").click(function () {
    if (!$("#btnSaveRMDetails").prop("disabled")) {
        saveResistanceMethodDetails();
        clearResistanceMethodSearch();
        showResistanceMethodList();
    }
})

$('#tableRMFilter tbody').on('click', 'tr', function () {
    showResistanceMethodDetail($(this).data('id'))
});

$('#chkActiveRMFilter').on('change', function () {
    var rows = $('#DataRMFilter tr');
    $('.mb-encontrados').html('');
    if ($('#chkActiveRMFilter').prop('checked')) {
        rows.each(function() {
            var activo = $(this).data('activo');
            $(this).toggle(activo === 'S');
        });
        let filteredRows = rows.filter(function() {
            return $(this).data('activo') === 'S';
        });
        let count = filteredRows.length;
        $('.mb-encontrados').html(`Métodos de resistencia: ${count}`)
    } else {
      rows.show();
        $('.mb-encontrados').html(`Métodos de resistencia: ${rows.length}`)
    }
})

$("#cbActiveRMDetails").click(function () {
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo")
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo")
    }
})

function bindInputFilter() {
    const inputCode = $('#txtCodeRMFilter');
    const inputName = $('#txtNameRMFilter');
    const tableRows = $('#DataRMFilter');
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
        $('.mb-encontrados').html(`Métodos de resistencia: ${count}`)
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
        $('.mb-encontrados').html(`Métodos de resistencia: ${count}`)
    })
}

function showResistanceMethodList() {
    $('#tableRMFilterContainer').scrollTop(0);
    $("#DataRMFilter").html("");
    $.getJSON("Microbiologia/Mantenedores/api/getRMList", { code: $("#txtCodeRMFilter").val(), name: $("#txtNameRMFilter").val(), activeOnly: $("#chkActiveRMFilter").prop("checked") }, function (cultureMediumList) {
        $('.mb-encontrados').html('');
        $('.mb-encontrados').html(`Antibióticos encontrados: ${cultureMediumList.length}`)
        cultureMediumList.forEach(function (cultureMedium) {
            $("#DataRMFilter")
            .append(
                `<tr class='' data-id='${cultureMedium.id}' data-activo='${cultureMedium.active}'>
                    <td class='codigoTest'>${cultureMedium.code}</td>
                    <td>${cultureMedium.name}</td>
                </tr>`
                );
        })
    });
}

function clearResistanceMethodSearch() {
    $("#lblRMFilterError").prop("hidden", "hidden");
    $('#tableRMFilterContainer').scrollTop(0);
    $("#txtCodeRMFilter").val('');
    $("#txtNameRMFilter").val('');
    if ($("#chkActiveRMFilter").prop("checked") == true) {
        $("#chkActiveRMFilter").click()
    }
}

function clearResistanceMethodDetails() {
    disableRMDetailsEdit();
    $("#txtIdRMDetails").val('');
    $("#txtCodeRMDetails").val('');
    $("#txtNameRMDetails").val('');
    $("#txtLISBacRMDetails").val('');
    $(".switch-content").removeClass("disabled inactivo");
    setActiveRMDetailsState(false)
}

function showResistanceMethodDetail(id) {
    centerViewTo("#formRMDetails");
    clearResistanceMethodDetails();

    $.getJSON("Microbiologia/Mantenedores/api/getRM", { id: id })
        .done(function (cm) {
            $("#collapseOne8").collapse('hide')
            $("#txtIdRMDetails").val(cm.id);
            $("#txtCodeRMDetails").val(cm.code);
            $("#txtNameRMDetails").val(cm.name);
            $("#txtLISBacRMDetails").val(cm.host);
            cm.active === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            setActiveRMDetailsState(cm.active == "S")
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function saveResistanceMethodDetails() {
    var dict = {
        "id": $("#txtIdRMDetails").val(),
        "code": $("#txtCodeRMDetails").val().toUpperCase(),
        "name": $("#txtNameRMDetails").val().toUpperCase(),
        "host": $("#txtLISBacRMDetails").val().toUpperCase(),
        "active": false,
    };
    if ($("#cbActiveRMDetails").prop("checked") == true) {
        dict["active"] = true
    }

    $.ajax({
        url: "Microbiologia/Mantenedores/api/putRM",
        data: JSON.stringify(dict),
        headers: { 'Content-Type': 'application/json' },
        method: 'put',
        dataType: 'json',
        success: function (msj) { 
			if(msj.status == 200)
				handlerMessageOk(msj.message);
			if(msj.status == 300)
				handlerMessageError(msj.message);
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

function setActiveRMDetailsState(state) {
    if ($("#cbActiveRMDetails").prop("checked") != state) {
        let previous_state = $("#cbActiveRMDetails").prop("disabled");
        $("#cbActiveRMDetails").prop("disabled", false);
        $("#cbActiveRMDetails").click();
        $("#cbActiveRMDetails").prop("disabled", previous_state)
        if(previous_state){
            $(".switch-content").addClass("disabled");
           }else{
           $(".switch-content").removeClass("disabled");
           }
    }
}

function enableRMDetailsEdit() {
    $("#txtCodeRMDetails").prop("disabled", false);
    $("#txtNameRMDetails").prop("disabled", false);
    $("#txtLISBacRMDetails").prop("disabled", false);
    $("#cbActiveRMDetails").prop("disabled", false);
    $("#btnSaveRMDetails").prop("disabled", false);
    $(".switch-content").removeClass("disabled");
}

function disableRMDetailsEdit() {
    $("#txtCodeRMDetails").prop("disabled", true);
    $("#txtNameRMDetails").prop("disabled", true);
    $("#txtLISBacRMDetails").prop("disabled", true);
    $("#cbActiveRMDetails").prop("disabled", true);
    $("#btnSaveRMDetails").prop("disabled", true);
    $(".switch-content").addClass("disabled");
}