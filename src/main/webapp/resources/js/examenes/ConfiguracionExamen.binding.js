//Selects que se ocuparán
let secciones = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/secciones/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let derivadores = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/derivadores/lstSelect",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let indicacionesExamenes = function () {
    var jqXHR = $.ajax({
        async: false,
        url: "api/indicaciones/examenes",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let pesquisas = function () {
    var jqXHR = $.ajax({
        async: false,
        url: "api/dominio/pesquisas/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let loincs = function () {
    var jqXHR = $.ajax({
        async: false,
        url: "api/dominio/loinc/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let gruposFonasa = function () {
    var jqXHR = $.ajax({
        async: false,
        url: "api/test/gruposExamenesFonasa",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let formatos = function () {
    var jqXHR = $.ajax({
        async: false,
        url: "api/dominio/formatos/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let gruposExamenes = function () {
    var jqXHR = $.ajax({
        async: false,
        url: "api/dominio/gruposExamenes/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let indicacionesTM = function () {
    var jqXHR = $.ajax({
        async: false,
        url: "api/dominio/indicacionesTM/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

/** Seleccion de tests **/
var dtVtt = new DataTableVisualTarget("#tablaTestExamenes");
var dtt = new DataSource([]);
vtt = new VisualTarget(dtt, dtVtt);
vst = new VisualSource();
test = genVSFiller("#idDualDataVisualSourceTest", vst, "#idSearcherTest");

var movedor = new Mover(dtVtt, dtt, vtt, vst);
vst.onDoubleClick(movedor);

let svc = new CfgTests();
svc.getTests(test, 0);

function addTest() {
    let itemId = vst.getSelectedItemId();
    let item = vst.getSelectedItemById(itemId);
    vst.removeItemById(itemId);
    vtt.addItem(item);
}

function delTest() {
    let items = vtt.getSelectedItems();
    vtt.removeItems(items);
    vst.addItems(items);
    // console.log('eliminando test',items[0].dataValue.cmueDescripcionabreviada == $("#muestraExamen").val(),items[0].dataValue.cmueDescripcionabreviada,$("#muestraExamen").val())
    
}

function limpiarTest() {
    let items = vtt.getItems();
    if (items.length > 0){
        vtt.removeItems(items);
        vst.addItems(items);
    }
}

$("#clickAddBtnTest").click(addTest);
$("#clickDelBtnTest").click(delTest);

/** Seleccion de muestras **/
var dtVtm = new DataTableMuestras("#tablaTestMuestras");
var dtm = new DataSource([]);
vtm = new VisualTarget(dtm, dtVtm);
vsm = new VisualSource();
test2 = genVSFillerMuestras("#idDualDataVisualSourceMuestra", vsm, "#idSearcherMuestra");

var movedor2 = new Mover(dtVtm, dtm, vtm, vsm);
vsm.onDoubleClick(movedor2);

let svcm = new CfgMuestras();
svcm.getMuestras(test2);

function addMuestra() {
    let itemId = vsm.getSelectedItemId();
    let item = vsm.getSelectedItemById(itemId);
    vsm.removeItemById(itemId);
    vtm.addItem(item);
}

function delMuestra() {
    let items = vtm.getSelectedItems();
    console.log(items);
    vtm.removeItems(items);
    vsm.addItems(items);
    if(items.length > 0 && items[0].visibleValue == $("#muestraExamen").val()){
        $("#muestraExamen").val("");
    }
}



function limpiarMuestras() {
    let items = vtm.getItems();
    if (items.length > 0){
        vtm.removeItems(items);
        vsm.addItems(items);
    }
}

$("#clickAddBtnMuestra").click(addMuestra);
$("#clickDelBtnMuestra").click(delMuestra);

/** Seleccion de examenes conjuntos**/
var dtVte = new DataTableExamenes("#tablaExamenesConjuntos");
var dte = new DataSource([]);
vte = new VisualTarget(dte, dtVte);
vse = new VisualSource();
testExamen = genVSFillerExamenes("#idDualDataVisualSourceExamen", vse, "#idSearcherExamen");

var movedor3 = new Mover(dtVte, dte, vte, vse);
vse.onDoubleClick(movedor3);

let svce = new CfgExamen();
svce.getExamenes(testExamen);

function addExamen() {
    let itemId = vse.getSelectedItemId();
    let item = vse.getSelectedItemById(itemId);
    vse.removeItemById(itemId);
    vte.addItem(item);
}

function delExamen() {
    let items = vte.getSelectedItems();
    console.log(items);
    vte.removeItems(items);
    vse.addItems(items);
}

function limpiarExamenes() {
    let items = vte.getItems();
    vte.removeItems(items);
    vse.addItems(items);
}

$("#clickAddBtnExamen").click(addExamen);
$("#clickDelBtnExamen").click(delExamen);



function cargarSecciones(secciones){
     let options = `<option value="0" selected>TODOS</option>`;
    //let options = ``;
    secciones.forEach((element) => {
        options += `<option value="${element.csecIdseccion}">${element.csecDescripcion}</option>`;
    });
    $("#selectSeccionFiltro, #selectSeccionTest, #selectSeccionExamenesConjuntos").html(options);
}

function cargarDerivadores(derivadores){
    let options = "";
    derivadores.forEach((element) => {
        if (element.cderivIdderivador === 0){
            options += `<option value="${element.cderivIdderivador}">SIN DERIVADOR</option>`;
//            options += `<option value="${element.cderivIdderivador}">SIN ESPECIFICAR</option>`;
        } else {
            options += `<option value="${element.cderivIdderivador}">${element.cderivDescripcion}</option>`;
        }
    });
    $("#selectDerivador").html(options);
}

function cargarIndicacionesExamenes(indicacionesExamenes){
    let options = ``;
    indicacionesExamenes.dato.forEach((element) => {
        options += `<option value="${element.ldvieIdindicacionesexamen}" ${element.ldvieIdindicacionesexamen == 0 ? 'selected' : ''}>${element.ldvieDescripcion}</option>`;
    });
    $("#selectIndicacionesPaciente").html(options);
}

function cargarPesquisas(pesquisas){
    let options = "";// = `<option value="0" selected>SIN INDICACIONES</option>`;
    pesquisas.dato.forEach((element) => {
        options += `<option value="${element.cpeIdpesquisa}" ${element.cpeIdpesquisa == 0 ? 'selected' : ''}>${element.cpeDescripcion}</option>`;
    });
    $("#selectPesquisa").html(options);
    $('.selectpicker').selectpicker('refresh');
}

function cargarGruposFonasa(gruposFonasa){
	//agregar valor en DB
    let options = `<option value="0" selected>SIN GRUPO FONASA</option>`;
    gruposFonasa.forEach((element) => {
        options += `<option value="${element.cgefIdgrupoexamenfonasa}" ${element.cgefIdgrupoexamenfonasa == 0 ? 'selected' : ''}>${element.cgefDescripcion}</option>`;
    });
    $("#selectGrupoFonasa").html(options);
    $('.selectpicker').selectpicker('refresh');
}

function cargarLoincs(loincs){
    let options = `<option value="N" selected>NINGUNO</option>`;
    loincs.dato.forEach((element) => {
        options += `<option value="${element.ldvlCodigoloinc}">${element.ldvlDescripcion}</option>`;
    });
    $("#selectLoinc").html(options);
}

function cargarFormatos(formatos){
    let options = ``;
    formatos.dato.forEach((element) => {
        options += `<option value="${element.ldvfIdformato}" ${element.ldvfIdformato == 0 ? 'selected' : ''}>${element.ldvfDescripcion}</option>`;
    });
    $("#selectFormatoImpresion").html(options);
    $('.selectpicker').selectpicker('refresh');
}

function cargarGruposExamenes(gruposExamenes){
    let options = ``;
    gruposExamenes.dato.forEach((element) => {
        options += `<option value="${element.ldvgeIdgrupoexamen}" ${element.ldvgeIdgrupoexamen == 0 ? 'selected' : ''}>${element.ldvgeDescripcion}</option>`;
    });
    $("#selectGrupoExamenes").html(options);
    $('.selectpicker').selectpicker('refresh');
}

$('a[data-toggle="tab"]').on('shown.bs.tab', function(e){
    $("#tablaTestExamenes").DataTable().columns.adjust().draw(false);
});  

$('#modalDatosMuestras').on('shown.bs.modal', function(e){
    $("#tablaTestMuestras").DataTable().columns.adjust().draw(false);
});  

// $(".containerTablaTestMuestras").height(getScreenDevice());
$('.containerTablaTestMuestras').css('min-height', getScreenDevice() + 1);