$(document).ready(function () {
    $("#tableExamEventsModal").DataTable({
        "dom": "t",
        "select": true,
        "scrollY": "200px",
        "scrollCollapse": true,
        "paging": false,
        "responsive": true,
        "language": {
            "emptyTable": "Cargando eventos..."
        }
    });
});

$('#modExamEventsModal').on('shown.bs.modal', function () {
    var table = $('#tableExamEventsModal').DataTable();
    table.columns.adjust();
});

function showExamEventsModal(events) {
    $("#tableExamEventsModal").DataTable().clear().draw();
    events.forEach(function (event, i) {
        $("#tableExamEventsModal").DataTable().row.add(
            [
                event.date,
                event.user,
                event.field,
                event.oldValue,
                event.newValue
            ]
        ).draw();
    });
}