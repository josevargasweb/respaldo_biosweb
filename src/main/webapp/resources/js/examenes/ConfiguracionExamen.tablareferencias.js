var rowCount = 1;
var colCount = 1;
const alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
    if (localStorage.getItem("botonEditar") === "inactivo") {
        //let columnNumber = (document.getElementById('tablaReferencias').rows[0].cells.length + 1);
        let columnHeader = getColumnValue(colCount);
        $("#theadTablaRef tr").append(`<th id="${columnHeader}" onmouseover="showBtnDel(this)" onmouseout="hideBtnDel(this)" style="background-color: #ddd"><label id="lbl${columnHeader}">${columnHeader}</label>
                                            <button type="button" class="btn btn-sm btn-danger btnDelete" style="display:none;" onclick="eliminarColumna('${columnHeader}')"><i class="far fa-trash-alt"></i></button></th>`);
        let cont = 1;
        $("#tbodyTablaRef tr").each(function(){
            let id = getColumnValue(colCount)+cont;
            $(this).append(`<td id="${id}"></td>`);
            cont++;
        });
        colCount++;
    }
});

$("#btnAddRow").click(function () {
    if (localStorage.getItem("botonEditar") === "inactivo") {
        //var td = createTd($("#theadTablaRef td").length);
        rowCount++;
        var td = createTd(colCount);
        $("#tbodyTablaRef").append(`<tr id="${rowCount}">
                                        <th id="${rowCount}" onmouseover='showBtnDel(this)' onmouseout='hideBtnDel(this)' style="background-color: #ddd"><label>${rowCount}</label>
                                            <button type="button" class="btn btn-sm btn-danger btnDelete" style="display:none;" onclick="eliminarFila(${rowCount})"><i class="far fa-trash-alt"></i></button>
                                        </th>${td}</tr>`);
    }
});

function createTd(largo) {
    var td = "";
    //var rowNumber = ($("#tablaReferencias tr").length + 1);
    //var cont = 0;
    for (var i = 0; i < largo; i++) {
        //cont++;
        var id = getColumnValue(i) + "" + (rowCount);
        td = td + "<td id="+ id +"><input id=" + id + " type='text' name='valorReferencia' style='visibility: hidden' /></td>";
    }
    return td;
}

/**
 * Transforma el valor de la columna en letra
 */
function getColumnValue(num){
    let division = Math.trunc(num/alfabeto.length);
    let resto = num%alfabeto.length;
    let letterIndex = resto + 'A'.charCodeAt(0);
    let letter = '';
    if (division>0){
    	do {
            let letterIndex1 = division + 'A'.charCodeAt(0)-1;
            letter += String.fromCharCode(letterIndex1);
            division = Math.trunc(division/alfabeto.length);
        } while(division >0)
   	 
    }
    letter += String.fromCharCode(letterIndex);
    return letter;
}

function showBtnDel(e){
    if (localStorage.getItem("botonEditar") === "inactivo") {
        let $row = $(e).closest("th");
        let btn = $row.find(".btnDelete");
        btn.show();
    }
}

function hideBtnDel(e){
    if (localStorage.getItem("botonEditar") === "inactivo") {
        let $row = $(e).closest("tr");
        let btn = $row.find(".btnDelete");
        btn.hide();
    }
}

function eliminarColumna(col){
    $(`#${col}`).remove();
    for (let i=1; i<=rowCount; i++){
        $(`#${col}${i}`).remove();
    }
    colCount--;
    //console.log(colCount);
    reordenarColumnas();
}

function eliminarFila(nRow){
    $(`#${nRow}`).remove();
    for (let i=0; i<colCount; i++){
        let col = getColumnValue(i);
        $(`#${col}${nRow}`).remove();
    }
    //si hay filas más abajo, se restan su valor a -1
    if(rowCount > nRow){
        nRow++;
        console.log("nRow:"+nRow);
        //$("#tbodyTablaRef").find(`#${nRow}`).attr({'id':nRow-1});
        $("#tbodyTablaRef tr").each(function(){
            if ($(this).attr("id")==nRow){
                $(this).attr({'id':nRow-1});
                $(this).find('th').attr({'id':nRow-1});
                $(this).find('label').text(nRow-1);
                $(this).find('button').attr('onclick', `eliminarFila(${nRow-1})`);
                let nCol = 0;
                $(this).find('td').each(function(){
                    let idCol = getColumnValue(nCol);
                    $(this).attr({'id':`${idCol}${nRow-1}`});
                    nCol++;
                });
                nRow++;
            }
        });
    }
    rowCount--;
    //console.log(rowCount);
}

function reordenarColumnas(){
    let nCol = 0;
    $("#theadTablaRef tr th").each(function(){
        let idCol = getColumnValue(nCol);
        $(this).attr({'id':idCol});
        $(this).find('label').attr({'id':`lbl${idCol}`});
        $(`#lbl${idCol}`).text(idCol);
        nCol++;
    });
    count = 1;
    $("#tbodyTablaRef tr").each(function(){
        nCol = 0;
        $(this).find('td').each(function (){
            let idCol = getColumnValue(nCol);
            $(this).attr({'id':`${idCol}${count}`});
            nCol++;
        });
        count++;
    });
}