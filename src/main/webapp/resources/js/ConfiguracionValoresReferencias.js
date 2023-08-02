class ConfiguracionValoresReferencia {
    jqId;
}

$(document).ready(function () {
//    var datatable = $('#kt_datatable').KTDatatable(options);
    $(".ocultar").hide();
    var table = $('#kt_datatable').DataTable();
    table.MakeCellsEditable({
        "onUpdate": myCallbackFunction
    });

    var md = ["<tr>\n\
                <td><select class='form-control selectpicker'><option value='1'>Femenino</option><option value='2'>Masculino</option><option value='3'>Ambos</option></select></td>\n\
                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
\n\                <td>0</td>\n\
                <td></td>\n\
                </tr>"];

    var datatable = $('#dt').DataTable();


    $('#addRow').on('click', function (evt) {
        datatable.destroy();
        $.each(md, function (i, item) {

            $('#kt_datatable tbody').append(item)
        });
        datatable = $('#kt_datatable').DataTable();
        var table = $('#kt_datatable').DataTable();
        table.MakeCellsEditable({
            "onUpdate": myCallbackFunction
        });
    })


});

function myCallbackFunction(updatedCell, updatedRow, oldValue) {
    console.log("The new value for the cell is: " + updatedCell.data());
    console.log("The old value for that cell was: " + oldValue);
    console.log("The values for each cell in that row are: " + updatedRow.data());
}