/* 
 * Clases en construcción. La idea es que a futuro funcione así
 * Mientras, sigue funcionando estructuralmente.
 */

class BusquedaTests {
    data;
    filtroCodigo;
    filtroNombre;
    filtroSecciones;
    filtroExamenes;
    tablaTests;
    testBuscador;
    
    constructor(){
        this.data = null;
        cargarSecciones();
        cargarExamenes();
        this.filtroCodigo = "#txtCodigoFiltro";
        this.filtroNombre = "#txtNombreTestFiltro";
        this.filtroSecciones = "#selectSeccionFiltro";
        this.filtroExamenes = "#selectExamenFiltro";
        this.tablaTest = null;
    }
    
}

class TestBuscador{
    
}

class TablaTests {
    idTable;
    tablaFiltroTests;
    
    constructor(idTable) {
        this.idTable = idTable;
        this.tablaFiltroTests = $(this.idTable).DataTable({
            
        });
    }
    
}

var bTests = new BusquedaTests();

$(document).ready(function() {
    cargarSecciones();
    cargarExamenes();
    //$("#btnBuscarFiltro").click();
    //bTests.testBuscador = new TestBuscador();
    //bTests.tablaTests = new TablaTests("#tableFiltro");

});

function cargarSecciones(){
    $.ajax({
        type: "GET",
        url: getctx + "/api/dominio/secciones/list",
        datatype: "json",
        success: function(data){
            $("#selectSeccionFiltro").html('<option value="0" selected>TODOS</option>');
            data.forEach(seccion=>{
                $("#selectSeccionFiltro").append($('<option>', {
                    value: seccion.csecIdseccion,
                    text: seccion.csecDescripcion 
                }));
            });
    $('.selectpicker').selectpicker('refresh');

        },
        error: function (msg) {
            handlerMessageError(msg);
        }
    });
}

function cargarExamenes(){
    $.ajax({
        type: "GET",
        url: getctx + "/api/dominio/cfgexamen/list",
        datatype: "json",
        success: function(data){
            data.forEach(examen=>{
                $("#selectExamenFiltro").append($('<option>', {
                    value: examen.ceIdexamen,
                    text: examen.ceAbreviado 
                }));
            });
    $('.selectpicker').selectpicker('refresh');

        },
        error: function (msg) {
            handlerMessageError(msg);
        }
    });
}

function alpha(e) {
    var k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || k == 39 || k == 241 || k == 209 || (k >= 48 && k <= 57));
}

function validCodigo() {
	
    if ($("#txtCodigoFiltro").val().length < 1) {
        return true;
    } else {
        return false;
    }
}

function validNombre() {

    if ($("#txtNombreTestFiltro").val().length < 1) {
        return true;
    } else {
        return false;
    }
}
/************** FILTRO TABLA ***************************************************************/



$("#txtCodigoFiltro").keyup(function (e) {
    if(e.which == 13) {
		if($("#txtCodigoFiltro").val().length > 1)
        	$('#btnBuscarFiltro').click();
    }
    if ($("#txtCodigoFiltro").val().length > 0) {
        $("#txtNombreTestFiltro").val("");
        $("#txtNombreTestFiltro").prop("disabled", true);
        $("#selectSeccionFiltro").prop("disabled", true);
        $("#selectExamenFiltro").prop("disabled", true);
        //$('.selectpicker').selectpicker('refresh');
    } else {
        $("#txtNombreTestFiltro").prop("disabled", false);
        $("#selectSeccionFiltro").prop("disabled", false);
        $("#selectExamenFiltro").prop("disabled", false);
        //$('.selectpicker').selectpicker('refresh');
    }
});

$("#txtNombreTestFiltro").keyup(function (e) {
    if(e.which == 13) {
		if($("#txtNombreTestFiltro").val().length > 1)
        	$('#btnBuscarFiltro').click();
    }
    if ($("#txtNombreTestFiltro").val().length > 0) {
        $("#txtCodigoFiltro").val("");
        $("#txtCodigoFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#selectSeccionFiltro").prop("disabled", false);
        $("#selectExamenFiltro").prop("disabled", false);
        //$('.selectpicker').selectpicker('refresh');
    }
});

$("#selectSeccionFiltro").change(function (e) {

    if ($("#selectSeccionFiltro :selected").val() !== "0") {
        $("#selectExamenFiltro").val("0");
        $("#selectExamenFiltro").prop("disabled", true);
        $("#txtCodigoFiltro").prop("disabled", true);
        $('#btnBuscarFiltro').click();
        //$('.selectpicker').selectpicker('refresh');
    } else {
        $("#selectExamenFiltro").prop("disabled", false);
        $("#txtCodigoFiltro").prop("disabled", false);
        //$('.selectpicker').selectpicker('refresh');
    }
});

$("#selectExamenFiltro").change(function () {
    if ($("#selectExamenFiltro :selected").val() !== "0") {
        $("#selectSeccionFiltro").val("0");
        $("#selectSeccionFiltro").prop("disabled", true);
        $("#txtCodigoFiltro").prop("disabled", true);
        $('#btnBuscarFiltro').click();
        //$('.selectpicker').selectpicker('refresh');
    } else {
        $("#selectSeccionFiltro").prop("disabled", false);
        $("#txtCodigoFiltro").prop("disabled", false);
        //$('.selectpicker').selectpicker('refresh');
    }

});

$("#btnBuscarFiltro").click(function () {
	
    if (validCodigo() === true && validNombre() === true && $("#selectExamenFiltro :selected").val() == "0" && $("#selectSeccionFiltro :selected").val() == "0") {
        handlerMessageWarning('Minimo 2 letras')
    } else {
        let codigo = $("#txtCodigoFiltro").val().toUpperCase();
        let nombre = $("#txtNombreTestFiltro").val().toUpperCase();
        let idSeccion = $("#selectSeccionFiltro :selected").val();
        let idExamen = $("#selectExamenFiltro :selected").val();
        let activo = $("#chkMostrarActivos").is(":checked") ? "S" : "N";
        filtrarLike(codigo, nombre, idSeccion, idExamen,activo);
    }
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtNombreTestFiltro").val("");
    $("#selectExamenFiltro").val("0");
    $("#selectSeccionFiltro").val("0");
    $("#tbodyFiltro").empty();
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtNombreTestFiltro").prop("disabled", false);
    $("#selectExamenFiltro").prop("disabled", false);
    $("#selectSeccionFiltro").prop("disabled", false);
    //$('.selectpicker').selectpicker('refresh');
});

function filtrarLike(codigo, nombre, idSeccion, idExamen,activo) {
	
    let filtros = JSON.stringify({
        codigo: codigo,
        nombre: nombre,
        idSeccion: idSeccion,
        idExamen: idExamen,
        activo: activo
    });
    
    $.ajax({
        type: "POST",
        url: gCte.getctx + "/api/tests/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (json) {
            if (json.status === 200){
                $("#tbodyFiltro").empty();
                let cont = 0;
                if (json.dato.length > 0) {
                    json.dato.forEach(function (test) {
                        //if (($("#chkMostrarActivos").is(":checked") && test.ctActivo === "S") || !$("#chkMostrarActivos").is(":checked")) {
                            cont++;
                            $("#tbodyFiltro")
                                    .append(`<tr class='pointer' onclick='buscarCodigo(this)'>
                                                <td class='row mx-auto'><b>${cont}</b></td>
                                                <td class='codigoTest'>${test.ctCodigo}</td>
                                                <td class=''>${test.ctAbreviado}</td>
                                                <td class='idTest' style='display:none'>${test.ctIdtest}</td>
                                            </tr>`);

                        //}


                    });
                    $("#lblErrorFiltro").hide();
                    $("#lblTotalFiltro").show();
                    $("#totalFiltro").text(cont);
                } else {
                    $("#lblErrorFiltro").show();
                    $("#lblTotalFiltro").hide();
                }
            } else {
                handlerMessageError(json.message);
            }
        },
        error: function (msg) {
            handlerMessageError("Error. No se pudo realizar la búsqueda.")
            console.log(msg.responseText);
        }
    });
}

function buscarCodigo(a) {
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idTest = $row.find(".idTest").text(); // Find the text
    $("#collapseOne8").collapse('hide');
    buscarTestPorId(idTest);
}

function buscarTestPorId(idTest){
    $.ajax({
        type: "get",
        url: getctx + "/api/test/" + idTest,
        datatype: "json",
        success: function (json) {
            selectCallback(json);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}


const tableNav = new TableNavigation("tableFiltro",selectCausaAuto);
tableNav.changeContenedor(".table-container");
function selectCausaAuto(a = null) {
    let $row;
    if(a == null){
        let tabla = document.getElementById("tableFiltro");
        let tbody = tabla.querySelector("tbody");
        let primerTR = tbody.querySelector("tr:first-child");
        $row = primerTR;
    }else{
        $row = a;
    }
    var idCausa = $row.querySelector('td.idTest').textContent;
    buscarTestPorId(idCausa,false);
}

