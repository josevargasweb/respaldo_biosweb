$(document).ready(function () {
    $("#ocultar").hide();
    listarNotas();
});

$("#btnAddRow").click(function (){
   addRow(); 
});

function listarNotas() {
    $.ajax({
        type: "post",
        data: "getNotas=1",
        datatype: "json",
        success: function (mensaje) {
            var cont = 0;
            var dato = JSON.parse(mensaje);
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            if (dato.nota.length > 0) {
                dato.nota.forEach(function (nota) {
                    cont++;
                    $("#tbodyNotas").append(
                            "<tr>" +
                            "<td>" + nota.DescripcionNota + "</td>" +
                            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
                            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
                            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
                            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
                            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
                            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
                            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
                            "</tr>"
                            );
                });
            } else {

            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });

}

function addRow() {
    $("#tbodyNotas").append(
            "<tr>" +
            "<td class'descripcion'><input type='text' class='form-control' /></td>" +
            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
            "<td class='text-center'><label class='checkbox pb-2' > <input type='checkbox' name='checkbox' /> <span></span> </label></td>" +
            "</tr>"
            );
}

