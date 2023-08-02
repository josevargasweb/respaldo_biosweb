$(document).on("click", "#tablaReferencias td", function () {
    var $this = $(this);
    var $input = $('<input>', {
        value: $this.text(),
        class: 'form-control',
        type: 'text',
        keypress: function () {

        },
        blur: function () {
            $this.text(this.value);
            //console.log($this.attr('class'));
        },
        keyup: function (e) {

            if (e.which === 13)
                $input.blur();
        }
    }).appendTo($this.empty()).focus();
});

$("#btnAddColumn").click(function () {
    var columnNumber = (document.getElementById('tablaReferencias').rows[0].cells.length + 1);
    var id = 1 + "" + columnNumber;
    $("#theadTablaRef tr").append("<td>nueva columna<input id=" + id + " type='text' name='valorReferencia' style='visibility: hidden' value='" + id + "/valor" + id + "' /></td>");
});

$("#btnAddRow").click(function () {
    var td = createTd($("#theadTablaRef td").length);
    $("#tobdyTablaRef").append("<tr>" + td + "</tr>");
});

function createTd(largo) {
    var td = "";
    var rowNumber = ($("#tablaReferencias tr").length + 1);
    var cont = 0;
    for (var i = 0; i < largo; i++) {
        cont++;
        var id = rowNumber + "" + cont;
        td = td + "<td>valor<input id=" + id + " type='text' name='valorReferencia' style='visibility: hidden' value='" + id + "/valor" + id + "' /></td>";
    }
    return td;
}