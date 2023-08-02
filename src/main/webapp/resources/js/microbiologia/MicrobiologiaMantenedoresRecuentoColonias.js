$(document).ready(function () {
    clearColonyCountSearch();
    clearColonyCountDetails();
    showColonyCountList();
    bindInputFilter();
})

$("#btnClearCCFilter").click(function () {
    clearColonyCountSearch();
    clearColonyCountDetails();
})

$("#btnNewCC").click(function () {
    $("#collapseOne8").collapse('hide')
    centerViewTo("#formCCDetails");
    clearColonyCountDetails();
    enableCCDetailsEdit();
})

$("#btnClearCCDetails").click(function () {
    clearColonyCountDetails();
})

$("#btnEditCCDetails").click(function () {
    enableCCDetailsEdit();
})

$("#btnSaveCCDetails").click(function () {
    if (!$("#btnSaveCCDetails").prop("disabled")) {
        saveColonyCountDetails();
        clearColonyCountSearch();
        showColonyCountList();
    }
})

$('#tableCCFilter tbody').on('click', 'tr', function () {
    showColonyCountDetail($(this).data('id'))
});

$('#chkActiveCCFilter').on('change', function () {
    var rows = $('#DataCCFilter tr');
    $('.mb-encontrados').html('');
    if ($('#chkActiveCCFilter').prop('checked')) {
        rows.each(function() {
            var activo = $(this).data('activo');
            $(this).toggle(activo === 'S');
        });
        let filteredRows = rows.filter(function() {
            return $(this).data('activo') === 'S';
        });
        let count = filteredRows.length;
        $('.mb-encontrados').html(`Colonias encontradas: ${count}`)
    } else {
      rows.show();
        $('.mb-encontrados').html(`Colonias encontradas: ${rows.length}`)
    }
})

$("#btnAddPwr2").click(function () {
    $("#txtNameCCDetails").val($("#txtNameCCDetails").val() + "²");
})

$("#btnAddPwr3").click(function () {
    $("#txtNameCCDetails").val($("#txtNameCCDetails").val() + "³");
})

$("#btnAddPwr4").click(function () {
    $("#txtNameCCDetails").val($("#txtNameCCDetails").val() + "⁴");
})

$("#btnAddPwr5").click(function () {
    $("#txtNameCCDetails").val($("#txtNameCCDetails").val() + "⁵");
})

$("#btnAddPwr6").click(function () {
    $("#txtNameCCDetails").val($("#txtNameCCDetails").val() + "⁶");
})

$("#btnAddPwr7").click(function () {
    $("#txtNameCCDetails").val($("#txtNameCCDetails").val() + "⁷");
})

$("#btnAddPwr8").click(function () {
    $("#txtNameCCDetails").val($("#txtNameCCDetails").val() + "⁸");
})

$("#btnAddPwr9").click(function () {
    $("#txtNameCCDetails").val($("#txtNameCCDetails").val() + "⁹");
})

function bindInputFilter() {

    const inputCode = $('#txtCodeCCFilter');
    const inputName = $('#txtNameCCFilter');
    const tableRows = $('#DataCCFilter');
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
        $('.mb-encontrados').html(`Colonias encontradas: ${count}`)
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
        $('.mb-encontrados').html(`Colonias encontradas: ${count}`)
    })
}

$("#cbActiveCCDetails").click(function () {
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo")
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo")
    }
})

function showColonyCountList() {
    $('#tableCCFilterContainer').scrollTop(0);
    // $("#tableCCFilter").DataTable().clear();
    // $("#tableCCFilter").DataTable().column(3).visible(false);
    $("#DataCCFilter").html("");
    $.getJSON("Microbiologia/Mantenedores/api/getCCList", { code: $("#txtCodeCCFilter").val(), name: $("#txtNameCCFilter").val(), activeOnly: $("#chkActiveCCFilter").prop("checked") }, function (cultureMediumList) {
        $('.mb-encontrados').html('');
        $('.mb-encontrados').html(`Colonias encontradas: ${cultureMediumList.length}`)
        cultureMediumList.forEach(function (cultureMedium) {
            $("#DataCCFilter")
            .append(
                `<tr class='' data-id='${cultureMedium.id}' data-activo='${cultureMedium.active}'>
                    <td class='codigoTest'>${cultureMedium.code}</td>
                    <td>${cultureMedium.name}</td>
                </tr>`
                );
        })
    });
}

function clearColonyCountSearch() {
    $("#lblCCFilterError").prop("hidden", "hidden");
    $('#tableCCFilterContainer').scrollTop(0);
    $("#txtCodeCCFilter").val('');
    $("#txtNameCCFilter").val('');
    if ($("#chkActiveCCFilter").prop("checked") == true) {
        $("#chkActiveCCFilter").click()
    }
}

function clearColonyCountDetails() {
    disableCCDetailsEdit();
    $("#txtIdCCDetails").val('');
    $("#txtCodeCCDetails").val('');
    $("#txtNameCCDetails").val('');
    $("#txtOrderCCDetails").val('');
    $("#txtLISBacCCDetails").val('');
    $(".switch-content").removeClass("disabled inactivo");
    setActiveCCDetailsState(false)
}

function showColonyCountDetail(id) {
    centerViewTo("#formCCDetails");
    clearColonyCountDetails();

    $.getJSON("Microbiologia/Mantenedores/api/getCC", { id: id })
        .done(function (cc) {
            $("#collapseOne8").collapse('hide')
            $("#txtIdCCDetails").val(cc.id);
            $("#txtCodeCCDetails").val(cc.code);
            $("#txtNameCCDetails").val(cc.name);
            $("#txtOrderCCDetails").val(cc.sort);
            $("#txtLISBacCCDetails").val(cc.host);
            cc.active === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            setActiveCCDetailsState(cc.active == "S")
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function saveColonyCountDetails() {
    var dict = {
        "id": $("#txtIdCCDetails").val().toUpperCase(),
        "code": $("#txtCodeCCDetails").val().toUpperCase(),
        "name": $("#txtNameCCDetails").val().toUpperCase(),
        "sort": $("#txtOrderCCDetails").val().toUpperCase(),
        "host": $("#txtLISBacCCDetails").val().toUpperCase(),
        "active": false,
    };
    if ($("#cbActiveCCDetails").prop("checked") == true) {
        dict["active"] = true
    }

    $.ajax({
        url: "Microbiologia/Mantenedores/api/putCC",
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

function setActiveCCDetailsState(state) {
    if ($("#cbActiveCCDetails").prop("checked") != state) {
        let previous_state = $("#cbActiveCCDetails").prop("disabled");
        console.log(previous_state);
        $("#cbActiveCCDetails").prop("disabled", false);
        $("#cbActiveCCDetails").click();
        $("#cbActiveCCDetails").prop("disabled", previous_state)
        if(previous_state){
            $(".switch-content").addClass("disabled");
           }else{
           $(".switch-content").removeClass("disabled");
           }
    }
}

function enableCCDetailsEdit() {
    $("a[id^=btnAddPwr]").removeAttr("hidden");
    $("#txtCodeCCDetails").prop("disabled", false);
    $("#txtNameCCDetails").prop("disabled", false);
    $("#txtOrderCCDetails").prop("disabled", false);
    $("#txtLISBacCCDetails").prop("disabled", false);
    $("#cbActiveCCDetails").prop("disabled", false);
    $("#btnSaveCCDetails").prop("disabled", false);
    $(".switch-content").removeClass("disabled");
}

function disableCCDetailsEdit() {
    $("a[id^=btnAddPwr]").prop("hidden", true);
    $("#txtCodeCCDetails").prop("disabled", true);
    $("#txtNameCCDetails").prop("disabled", true);
    $("#txtOrderCCDetails").prop("disabled", true);
    $("#txtLISBacCCDetails").prop("disabled", true);
    $("#cbActiveCCDetails").prop("disabled", true);
    $("#btnSaveCCDetails").prop("disabled", true);
    $(".switch-content").addClass("disabled");
}