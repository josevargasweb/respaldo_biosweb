$(document).ready(function () {
    // $("#tableBAFilter").DataTable({
    //     "dom": "t",
    //     "paging": false
    // });
    clearBodyAreasSearch();
    clearBodyAreasDetails();
    showBodyAreasList();
    bindInputFilter();
})

$("#btnClearBAFilter").click(function () {
    clearBodyAreasSearch();
    clearBodyAreasDetails();
})

$("#btnNewBA").click(function () {
    centerViewTo("#formBADetails");
    $("#collapseOne8").collapse('hide')
    clearBodyAreasDetails();
    enableBADetailsEdit();
})

$("#btnClearBADetails").click(function () {
    clearBodyAreasDetails();
})

$("#btnEditBADetails").click(function () {
    enableBADetailsEdit();
})

$("#btnSaveBADetails").click(function () {
    if (!$("#btnSaveBADetails").prop("disabled")) {
        saveBodyAreasDetails();
        clearBodyAreasSearch();
        showBodyAreasList();
    }
})

$('#tableBAFilter tbody').on('click', 'tr', function () {
    showBodyAreasDetail($(this).data('id'))
});

$('#chkActiveBAFilter').on('change', function () {
    var rows = $('#DataBAFilter tr');
    $('.mb-encontrados').html('');
    if ($('#chkActiveBAFilter').prop('checked')) {
        rows.each(function() {
            var activo = $(this).data('activo');
            $(this).toggle(activo === 'S');
        });
        let filteredRows = rows.filter(function() {
            return $(this).data('activo') === 'S';
        });
        let count = filteredRows.length;
        $('.mb-encontrados').html(`Zonas del cuerpo encontradas: ${count}`)
    } else {
      rows.show();
        $('.mb-encontrados').html(`Zonas del cuerpo encontradas: ${rows.length}`)
    }
})

function bindInputFilter() {
    const inputName = $('#txtNameBAFilter');
    const tableRows = $('#DataBAFilter');
  
    inputName.on('keyup', function() {
        let count = 0;
        const searchTerm = inputName.val().toLowerCase();
        tableRows.find('tr').each(function() {
            const secondTd = $(this).find('td:nth-child(1)');
            const name = secondTd.text().toLowerCase();
            if (name.includes(searchTerm)) {
            $(this).show();
            count++;
            } else {
            $(this).hide();
            }
        })
        $('.mb-encontrados').html(`Zonas del cuerpo encontradas: ${count}`)
    })
}

$("#cbActiveBADetails").click(function () {
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo")
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo")
    }
})

function showBodyAreasList() {
    $('#tableBAFilterContainer').scrollTop(0);
    // $("#tableBAFilter").DataTable().clear();
    // $("#tableBAFilter").DataTable().column(2).visible(false);
    $("#DataBAFilter").html("");
    $.getJSON("Microbiologia/Mantenedores/api/getBAList", { code: $("#txtCodeBAFilter").val(), name: $("#txtNameBAFilter").val(), activeOnly: $("#chkActiveBAFilter").prop("checked") }, function (cultureMediumList) {
        $('.mb-encontrados').html('');
        $('.mb-encontrados').html(`Zonas del cuerpo encontradas: ${cultureMediumList.length}`)
        cultureMediumList.forEach(function (cultureMedium) {
            $("#DataBAFilter")
            .append(
                `<tr class='' data-id='${cultureMedium.id}' data-activo='${cultureMedium.active}'>
                    <td>${cultureMedium.name}</td>
                </tr>`
                );
            // $("#tableBAFilter").DataTable().row.add(
            //     [
            //         cultureMedium.id,
            //         cultureMedium.name,
            //         cultureMedium.active
            //     ]
            // ).draw();
        })
    });
}

function clearBodyAreasSearch() {
    $("#lblBAFilterError").prop("hidden", "hidden");
    $('#tableBAFilterContainer').scrollTop(0);
    $("#txtCodeBAFilter").val('');
    $("#txtNameBAFilter").val('');
    if ($("#chkActiveBAFilter").prop("checked") == true) {
        $("#chkActiveBAFilter").click()
    }
    // $("#tableBAFilter").DataTable().columns().every(function () {
    //     this.search("", false, true).draw();
    // })
}

function clearBodyAreasDetails() {
    disableBADetailsEdit();
    $("#txtIdBADetails").val('');
    $("#txtCodeBADetails").val('');
    $("#txtNameBADetails").val('');
    $("#txtLISBacBADetails").val('');
    $(".switch-content").removeClass("disabled inactivo");
    setActiveBADetailsState(false)
}

function showBodyAreasDetail(id) {
    centerViewTo("#formBADetails");
    clearBodyAreasDetails();

    $.getJSON("Microbiologia/Mantenedores/api/getBA", { id: id })
        .done(function (ba) {
            $("#collapseOne8").collapse('hide')
            $("#txtIdBADetails").val(ba.id);
            //$("#txtCodeBADetails").val(ba.code);
            $("#txtNameBADetails").val(ba.name);
            $("#txtLISBacBADetails").val(ba.sort);
            ba.active === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            setActiveBADetailsState(ba.active == "S")
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}

function saveBodyAreasDetails() {
    var dict = {
        "id": $("#txtIdBADetails").val().toUpperCase(),
        //"code": $("#txtCodeBADetails").val().toUpperCase(),
        "name": $("#txtNameBADetails").val().toUpperCase(),
        "sort": $("#txtLISBacBADetails").val().toUpperCase(),
        "active": false,
    };
    if ($("#cbActiveBADetails").prop("checked") == true) {
        dict["active"] = true
    }

    $.ajax({
        url: "Microbiologia/Mantenedores/api/putBA",
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

function setActiveBADetailsState(state) {
    if ($("#cbActiveBADetails").prop("checked") != state) {
        let previous_state = $("#cbActiveBADetails").prop("disabled");
        $("#cbActiveBADetails").prop("disabled", false);
        $("#cbActiveBADetails").click();
        $("#cbActiveBADetails").prop("disabled", previous_state)
        if(previous_state){
            $(".switch-content").addClass("disabled");
           }else{
           $(".switch-content").removeClass("disabled");
           }
    }
}

function enableBADetailsEdit() {
    $("#txtCodeBADetails").prop("disabled", false);
    $("#txtNameBADetails").prop("disabled", false);
    $("#txtLISBacBADetails").prop("disabled", false);
    $("#cbActiveBADetails").prop("disabled", false);
    $(".switch-content").removeClass("disabled");
    $("#btnSaveBADetails").prop("disabled", false);
}

function disableBADetailsEdit() {
    $("#txtCodeBADetails").prop("disabled", true);
    $("#txtNameBADetails").prop("disabled", true);
    $("#txtLISBacBADetails").prop("disabled", true);
    $("#cbActiveBADetails").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#btnSaveBADetails").prop("disabled", true);
}