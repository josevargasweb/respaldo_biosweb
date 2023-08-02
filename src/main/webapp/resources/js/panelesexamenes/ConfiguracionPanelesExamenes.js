var dtVT = new DataTableVisualTarget("#listExamenes");
var dt = new DataSource([]);
vt = new VisualTarget(dt, dtVT);
vs = new VisualSource();
test = genVSFiller("#idDualDataVisualSource", vs, "#idSearcher");

var movedor = new Mover(dtVT, dt, vt, vs);

vs.onDoubleClick(movedor);
let svc = new CfgExamen();
svc.getExamenes(test);

function eventoAdd() {
    let itemId = vs.getSelectedItemId();
    let item = vs.getSelectedItemById(itemId);
    vs.removeItemById(itemId);
    vt.addItem(item);
}

function eventoDel() {
    let items = vt.getSelectedItems();
    vt.removeItems(items);
    vs.addItems(items);
}

function eventoLimpiar() {
    let items = vt.getItems();
    vt.removeItems(items);
    vs.addItems(items);
}

$("#clickAddBtn").click(eventoAdd);
$("#clickDelBtn").click(eventoDel);

$(document).ready(function(){
    $(".ocultar").hide();
    filtrarLike();
    desactivarEdit();
    let messageSuccess = $("#messageSuccess").val();
    let messageError = $("#messageError").val();
    
    if (typeof messageSuccess !== 'undefined' && messageSuccess!==null){
        handlerMessageOk(messageSuccess);
    }
    if (typeof messageError !== 'undefined' && messageError!==null){
        handlerMessageError(messageError);
    }
});

function filtrarLike() {
    let nombre = $("#txtNombreFiltro").val();
    let activo = $("#chkMostrarActivos").is(":checked") ? 1 : 0;
    $.ajax({
        type: "post",
        data: "filtro=1&nombre=" + nombre + "&activo=" + activo,
        url: "ConfiguracionPanelesExamenes",
        datatype: "json",
        success: function (mensaje) {
            var dato = JSON.parse(mensaje);
            if (!dato.hasOwnProperty('error')){
                var cont = 0;
                $("#tbodyFiltro").empty();
                if (dato.paneles.length > 0) {
                    dato.paneles.forEach(function (panel) {
                        cont++;
                        $("#tbodyFiltro").append("<tr class='pointer' onclick='selectPanel(this)' ><th class='row mx-auto'>" + cont + "</th><td class=''>" + panel.Descripcion + "</td><td class='idPanel' style='display:none'>" + panel.Id + "</td></tr>");
                    });
                    $("#lblErrorFiltro").hide();
                    $("#lblCantidadExamenes").show();
                    $("#spanCantExamenes").text(cont);
                } else {
                    $("#lblErrorFiltro").show();
                    $("#lblCantidadExamenes").hide();
                }
            } else {
                handlerMessageError(dato.error);
            }
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Ha ocurrido un error");
        }
    });
}

$("#txtNombreFiltro").keyup(function () {
    filtrarLike();
});

$("#chkMostrarActivos").click(function () {
    filtrarLike();
});

function selectPanel(a){
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idPanel = $row.find(".idPanel").text();
    buscarPorId(idPanel);
    activarEdit();
}

function activarEdit() {
    $("#iEditPanel").removeClass("text-dark-50");
    $("#iEditPanel").addClass("text-primary");
}

function buscarPorId(idPanel,collapse = true){
    $.ajax({
        type: "post",
        data: "buscarPorId=1&idPanel=" + idPanel,
        async: false,
        datatype: "json",
        success: function (mensaje) {
            if(collapse){
                $("#collapseHeader").collapse('hide');
            }
            let dato = JSON.parse(mensaje);
            let panel = dato.paneles[0];
            $("#txtIdPanel").val(panel.panelId);
            $("#txtNombrePanel").val(panel.panelNombre);
            $(".switch-content").addClass("disabled");
            panel.panelActivo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            if (panel.panelActivo === "S") {
                $("#checkBoxActivo").prop("checked", true);
                $("#lblEstado").text("Activo");
            } else {
                $("#checkBoxActivo").prop("checked", false);
                $("#lblEstado").text("Inactivo");
            }
            $(".formPanelEx").prop("disabled", true);
            $("#btnAgregarPanel").prop("disabled", true);
            $("#divAgregarBtn").show();
            $("#divActualizarBtn").hide();
            //$("#listbox_examenes").empty();
            //recargarDatos(panel.examenesPanel);
            //$("#listbox").hide();
            //$("#examenesTest").show();
            //$("#examenesTest").empty();
            eventoLimpiar();
            panel.examenesPanel.forEach(function (examen) {
                //$("#examenesTest").append('<li>' + '<input type="hidden" value="' + examen.examenId + '" name="ExamenesResumenes">' + examen.examenNombre + '</li>');    
                let itemTest = new DataExamen(examen.examenId, examen.examenCodigo, examen.examenNombre, examen.examenDescripcion);
                let item = new Item(itemTest);
                vt.addItem(item);
                vs.removeItemById(item.id);
            });
            
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

$("#nuevoPanel").click(function () {
    $("#collapseHeader").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#buscarPanel").click(function () {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
});

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtNombreFiltro").val("");
    $("#txtNombreFiltro").prop("disabled", false);
    $("#chkMostrarActivos").prop("checked", false);
    filtrarLike();
});

$("#btnLimpiar").click(function () {
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

$("#btnEditarPanel").click(function() {
    if (localStorage.getItem("botonEditar") === "activo"){
        $(".switch-content").removeClass("disabled");
        $(".formPanelEx").prop("disabled", false);
        $("#btnAgregarPanel").prop("disabled", false);
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").show();
        $('#btnLimpiar').addClass('disabled');
        desactivarEdit();
    }
});

function limpiarFormulario(){
    $(".switch-content").removeClass("disabled inactivo");
    $(".formPanelEx").prop("disabled", false);
    $("#txtNombrePanel").val("");
    $("#idSearcher").val("");
    $("#checkBoxActivo").prop("checked", "checked");
    $("#lblEstado").text("Activo");
    $("#divBtnAgregar").show();
    $("#btnAgregarPanel").prop("disabled", false);
    $("#divBtnUpdate").hide();
    eventoLimpiar();
    desactivarEdit();
}

function revisarTestRepetidos(idTest) {
    $('#examenesTest li').each(function (){
        examenesTest = $(this).val();
        if (idTest == examenesTest) {
            console.log('TEST REPETIDO ' + examenesTest);
        }

    });
}

$("#btnCancelUpdate").click(function() {
    $(".switch-content").addClass("disabled");
    $(".formPanelEx").prop("disabled", true);
    $("#divBtnUpdate").hide();
    $("#divBtnAgregar").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});

function activarEdit() {
    $("#iEditPanel").removeClass("text-dark-50");
    $("#iEditPanel").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}
function desactivarEdit() {
    $("#iEditPanel").addClass("text-dark-50");
    $("#iEditPanel").removeClass("text-blue");
    localStorage.setItem("botonEditar", "inactivo");
}

$("#formRegistroPanel").submit(function(){
    let nombre = $("#txtNombrePanel").val();
    let cont = 0;
    /*$(".dual-listbox__selected li").each(function (index) {
        
        console.log(index + ": " + $(this).text());
        console.log(index + ": " + $(this).attr("data-id"));

        $("#examenesTest").append('<li>' + '<input type="hidden" value="' + $(this).attr("data-id") + '" name="ExamenesResumenes">' + $(this).text() + '</li>');
        cont++;
    });*/
    
    let items = vt.getAllItems();
    items.forEach((examen) => {
        $("#examenesTest").append('<li>' + '<input type="hidden" value="' + examen.id + '" name="ExamenesResumenes">'+examen.visibleValue+'</li>');
        cont++;
    });
    
    if (nombre.trim() === "" || cont === 0){
        if (nombre.trim() === ""){
            $("#txtNombrePanel").addClass('is-invalid');
            handlerMessageError("Nombre no debe estar vacío");
        }
        if (cont === 0){
            handlerMessageError("Debe seleccionar al menos un examen");
            //$("#kt_dual_listbox_2").addClass('is-invalid');
            //$.notify({ message: "Debe seleccionar al menos un examen" }, { type: "danger" });
        }
        return false;
    } else {
        //$.notify({ message: "Panel ingresado correctamente" }, { type: "success" });
        return true;
    }
    
});


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
    var idCausa = $row.querySelector('td.idPanel').textContent;
    buscarPorId(idCausa,false);
}
