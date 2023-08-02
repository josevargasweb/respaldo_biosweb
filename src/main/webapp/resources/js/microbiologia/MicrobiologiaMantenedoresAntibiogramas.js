$(document).ready(function () {
    $("#tableAvailableAGDetails").DataTable({
        "dom": "t",
        "select": true,
        "scrollY": "200px",
        "scrollCollapse": true,
        "paging": false,
        "language": {
            "emptyTable": "Cargando lista de antibioticos..."
        }
    });
    $('#tableSelectedAGDetails').DataTable({
        "dom": "t",
        "scrollY": "200px",
        "scrollCollapse": true,
        "paging": false,
        "select": true,
        "columnDefs": [{
            "targets": 2,
            'searchable': false,
            'orderable': false,
            'className': 'dt-body-center',
            'render': function (data, type, row, meta) {
                var input = $("<input/>", {
                    "data-type": "all",
                    "data-id": row[0],
                    "type": "checkbox"
                });
                if (data === "true") {
                    input.attr("checked", "checked");
                }
                return input.prop("outerHTML");
            }
        }]
    });
    fillAntibioticTable();
    clearAntibiogramSearch();
    clearAntibiogramDetails();
    showAntibiogramList();
    bindInputFilter();
})

$("#btnClearAGFilter").click(function () {
    clearAntibiogramSearch();
    clearAntibiogramDetails();
})

$("#btnNewAG").click(function () {
    centerViewTo("#formAGDetails");
    $("#collapseOne8").collapse('hide')
    clearAntibiogramDetails();
    enableAGDetailsEdit();
})

$("#btnClearAGDetails").click(function () {
    clearAntibiogramDetails();

})

$("#btnEditAGDetails").click(function () {
    enableAGDetailsEdit();
})

$("#btnSaveAGDetails").click(function () {
    if (!$("#btnSaveAGDetails").prop("disabled")) {
        saveAntibiogramDetails();
        clearAntibiogramSearch();
        showAntibiogramList();
    }
})

$("#btnAddABAGDetails").click(function () {
    if ($("#btnAddABAGDetails").prop("disabled")) {
        return;
    }
    let data = $("#tableAvailableAGDetails").DataTable().row({ selected: true }).data();
    if (data == null) {
        return;
    }
    $.getJSON("Microbiologia/Mantenedores/api/getAB", { id: data[0] })
        .done(function (ab) {
            addABToTables(ab.id, ab.name, false);
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
    $("#tableAvailableAGDetails").DataTable().rows().deselect();
});
$("#btnRemoveABAGDetails").click(function () {
    if ($("#btnRemoveABAGDetails").prop("disabled")) {
        return;
    }
    let data = $("#tableSelectedAGDetails").DataTable().row({ selected: true }).remove().draw();
    let filter_str = $("#tableSelectedAGDetails").DataTable().column(0).data().join("|");
    $("#tableAvailableAGDetails").DataTable().column(0).search('^(?!(' + filter_str + ')$).*', true, false).draw();
    $("#tableSelectedAGDetails").DataTable().rows().deselect();
});

$('#tableAGFilter tbody').on('click', 'tr', function () {
    showAntibiogramDetail($(this).data('id'))
});

$('#chkActiveAGFilter').on('change', function () {
    var rows = $('#DataAGFilter tr');
    $('.mb-encontrados').html('');
    if ($('#chkActiveAGFilter').prop('checked')) {
        rows.each(function() {
            var activo = $(this).data('activo');
            $(this).toggle(activo === 'S');
        });
        let filteredRows = rows.filter(function() {
            return $(this).data('activo') === 'S';
        });
        let count = filteredRows.length;
        $('.mb-encontrados').html(`Antibiogramas encontrados: ${count}`)
    } else {
      rows.show();
        $('.mb-encontrados').html(`Antibiogramas encontrados: ${rows.length}`)
    }
    $('#tableAGFilterContainer').scrollTop(0);
});

$('#tableSelectedAGDetails tbody').on('click', 'td', function (e) {
    if ($(e.target).attr("type") === "checkbox") {
        var cell = $('#tableSelectedAGDetails').DataTable().cell(this);
        cell.data($(this).find("input").prop("checked").toString());
        $('#tableSelectedAGDetails').DataTable().draw(false);
    }
});

$("#txtSearchAvailableAGDetails").on('keyup', function () {
    $('#tableAvailableAGDetails')
        .DataTable().column(1)
        .search($('#txtSearchAvailableAGDetails').val(), false, true)
        .draw();
});

$("#cbActiveAGDetails").click(function () {
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo")
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo")
    }
})


function addABToTables(id, name, optional) {
    $("#tableSelectedAGDetails").DataTable().row.add(
        [
            id,
            name,
            ""
        ]
    ).draw();
    if (optional) {
        $("#tableSelectedAGDetails").DataTable().rows().every(function () {
            if (this.data()[0] == id) {
                $(this.node()).find('input').click();
            }
        })
    }
    let filter_str = $("#tableSelectedAGDetails").DataTable().column(0).data().join("|");
    $("#tableAvailableAGDetails").DataTable().column(0).search('^(?!(' + filter_str + ')$).*', true, false).draw();
}

function fillAntibioticTable() {
    $("#tableAvailableAGDetails").DataTable().column(0).visible(false);
    $.getJSON("Microbiologia/Mantenedores/api/getABList", {}, function (antibioticOptions) {
        antibioticOptions.forEach(function (antibiotic) {
            $("#tableAvailableAGDetails").DataTable().row.add(
                [
                    antibiotic.id,
                    "[" + antibiotic.code + "] " + antibiotic.name
                ]
            ).draw();
        });
    });

}

function bindInputFilter() {
    const inputCode = $('#txtCodeAGFilter');
    const inputName = $('#txtNameAGFilter');
    const tableRows = $('#DataAGFilter');
    
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

function showAntibiogramList() {
    $('#tableAGFilterContainer').scrollTop(0);
    $("#DataAGFilter").html("");
    $.getJSON("Microbiologia/Mantenedores/api/getAGList", { code: $("#txtCodeAGFilter").val(), name: $("#txtNameAGFilter").val(), activeOnly: $("#chkActiveAGFilter").prop("checked") }, function (cultureMediumList) {
        $('.mb-encontrados').html('');
        $('.mb-encontrados').html(`Antibiogramas encontrados: ${cultureMediumList.length}`)
        cultureMediumList.forEach(function (cultureMedium) {
            $("#DataAGFilter")
            .append(
                `<tr class='' data-id='${cultureMedium.id}' data-activo='${cultureMedium.active}'>
                    <td class='codigoTest'>${cultureMedium.code}</td>
                    <td>${cultureMedium.name}</td>
                </tr>`
                );
        })
    });
}

function clearAntibiogramSearch() {
    $("#lblAGFilterError").prop("hidden", "hidden");
    $('#tableAGFilterContainer').scrollTop(0);
    $("#txtCodeAGFilter").val('');
    $("#txtNameAGFilter").val('');
    if ($("#chkActiveAGFilter").prop("checked") == true) {
        $("#chkActiveAGFilter").click()
    }
}

function clearAntibiogramDetails() {
    disableAGDetailsEdit();
    $("#txtIdAGDetails").val('');
    $("#txtCodeAGDetails").val('');
    $("#txtNameAGDetails").val('');
    $("#txtOrderAGDetails").val('');
    $("#txtLISBacAGDetails").val('');
    $(".switch-content").removeClass("disabled inactivo");
    setActiveAGDetailsState(false)
    $("#tableSelectedAGDetails").DataTable().clear().draw();
}

function showAntibiogramDetail(id) {
    centerViewTo("#formAGDetails");
    clearAntibiogramDetails();

    $.getJSON("Microbiologia/Mantenedores/api/getAG", { id: id })
        .done(function (ag) {
            $("#collapseOne8").collapse('hide')
            $("#txtIdAGDetails").val(ag.id);
            $("#txtCodeAGDetails").val(ag.code);
            $("#txtNameAGDetails").val(ag.name);
            $("#txtOrderAGDetails").val(ag.sort);
            $("#txtLISBacAGDetails").val(ag.host);
            setActiveAGDetailsState(ag.active == "S");
            ag.active === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            ag.antibiotics.forEach(function (antibiotic) {
                addABToTables(antibiotic.id, antibiotic.name, "S" == antibiotic.optional);
            })
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function saveAntibiogramDetails() {
    var dict = {
        "id": $("#txtIdAGDetails").val(),
        "code": $("#txtCodeAGDetails").val().toUpperCase(),
        "name": $("#txtNameAGDetails").val().toUpperCase(),
        "sort": $("#txtOrderAGDetails").val().toUpperCase(),
        "host": $("#txtLISBacAGDetails").val().toUpperCase(),
        "active": false,
        "antibiotics": $("#tableSelectedAGDetails").DataTable().columns([0, 2]).rows().data().toArray(),
    };
    if ($("#cbActiveAGDetails").prop("checked") == true) {
        dict["active"] = true
    }

    $.ajax({
        url: "Microbiologia/Mantenedores/api/putAG",
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

function setActiveAGDetailsState(state) {
    if ($("#cbActiveAGDetails").prop("checked") != state) {
        let previous_state = $("#cbActiveAGDetails").prop("disabled");
        $("#cbActiveAGDetails").prop("disabled", false);
        $("#cbActiveAGDetails").click();
        $("#cbActiveAGDetails").prop("disabled", previous_state)
        if(previous_state){
            $(".switch-content").addClass("disabled");
           }else{
           $(".switch-content").removeClass("disabled");
           }
    }
}

function enableAGDetailsEdit() {
    $("#txtCodeAGDetails").prop("disabled", false);
    $("#txtNameAGDetails").prop("disabled", false);
    $("#txtLISBacAGDetails").prop("disabled", false);
    $("#cbActiveAGDetails").prop("disabled", false);
    $(".switch-content").removeClass("disabled");
    $("#btnSaveAGDetails").prop("disabled", false);
    $("#btnAddABAGDetails").prop("disabled", false);
    $("#btnRemoveABAGDetails").prop("disabled", false);
}

function disableAGDetailsEdit() {
    $("#txtCodeAGDetails").prop("disabled", true);
    $("#txtNameAGDetails").prop("disabled", true);
    $("#txtLISBacAGDetails").prop("disabled", true);
    $("#cbActiveAGDetails").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#btnSaveAGDetails").prop("disabled", true);
    $("#btnAddABAGDetails").prop("disabled", true);
    $("#btnRemoveABAGDetails").prop("disabled", true);
}