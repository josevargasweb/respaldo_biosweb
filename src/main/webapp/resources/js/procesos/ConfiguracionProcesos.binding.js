let secciones = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/secciones/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let centrosSalud = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/instituciones/centrosdesalud/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let tiposComunicacion = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/tipoComunicacion/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let examenes = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/cfgexamen/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let sigmatiposmuestras = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/sigmatiposmuestras/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let tiposResultado = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/tiposResultado/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let tiposcondicion = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/tiposcondicion/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let operacionesmath = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/operacionesmath/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

function cargarSecciones(secciones){
    let optionsFiltro = `<option value="0" selected>TODOS</option>`;
    let optionsConfig = `<option value="0" selected>SIN ESPECIFICAR</option>`;
    let options = "";
    secciones.forEach((element) => {
        options += `<option value="${element.csecIdseccion}">${element.csecDescripcion}</option>`;
    });
    optionsFiltro += options;
    optionsConfig += options;
    $("#selectSeccionFiltro, #selectSeccionTest").html(optionsFiltro);
    $("#selectSeccion").html(optionsConfig);
    $(".selectpicker").selectpicker("refresh");
}

function cargarEstaciones(){
    let optionsFiltro = `<option value="0" selected>TODOS</option>`;
    let optionsConfig = `<option value="0" selected>SIN ESPECIFICAR</option>`;
    let options = ``;
    for (let i=1; i<=50; i++){
        options += `<option value="${i}">NRO. ${i}</option>`;
    }
    optionsFiltro += options;
    optionsConfig += options;
    $("#selectEstacionFiltro").html(optionsFiltro);
    $("#selectEstacion").html(optionsConfig);
    $(".selectpicker").selectpicker("refresh");
}

function cargarCentrosdesalud(centrosSalud){
    let options = `<option value="0" selected>SIN ESPECIFICAR</option>`;
    centrosSalud.forEach((element) => {
        options += `<option value="${element.ccdsIdcentrodesalud}">${element.ccdsDescripcion}</option>`;
    });
    $("#selectCentroSalud").html(options);
}

function cargarTiposComunicacion(tiposComunicacion){
    let options = `<option value="0" selected>SIN ESPECIFICAR</option>`;
    tiposComunicacion.dato.forEach((element) => {
        options += `<option value="${element.ldvtcIdtipocomunicacion}">${element.ldvtcDescripcion}</option>`;
    });
    $("#selectTipoComunicacion").html(options);
}

function cargarExamenes(examenes){
    let options = `<option value="0" selected>TODOS</option>`;
    examenes.forEach((element) => {
        options += `<option value="${element.ceIdexamen}">${element.ceAbreviado}</option>`;
    });
    $("#selectExamenTest").html(options);
}

function cargarTiposMuestrasSigma(sigmatiposmuestras){
    let options = ``;
    sigmatiposmuestras.dato.forEach((element) => {
        options += `<option value="${element.stmIdsigmatipomuestra}">${element.stmDescripcion}</option>`;
    });
    $("#selectTipoMuestra").html(options);
}

function cargarTiposResultado(tiposResultado){
    let options = `<option value="0" selected>SIN ESPECIFICAR</option>`;
    tiposResultado.dato.forEach((element) => {
        options += `<option value="${element.ctrIdtiporesultado}">${element.ctrDescripcion}</option>`;
    });
    $("#selectTipoResultado").html(options);
}

function cargarTiposCondicion(tiposcondicion){
    let options = ``;
    tiposcondicion.dato.forEach((element) => {
        options += `<option value="${element.ldvtcondIdtipocondicion}">${element.ldvtcondDescripcion}</option>`;
    });
    $("#selectOperando").html(options);
}

function cargarOperacionesmath(operacionesmath){
    let options = ``;
    operacionesmath.dato.forEach((element) => {
        options += `<option value="${element.ldvomIdoperadormath}">${element.ldvomDescripcion}</option>`;
    });
    $("#selectOperador").html(options);
}

/** Seleccion de tests **/
var dtVtt = new DataTableVisualTarget("#tablaTests");
var dtt = new DataSource([]);
vtt = new VisualTarget(dtt, dtVtt);
vst = new VisualSource();
test = genVSFiller("#idDualDataVisualSourceTest", vst);

var movedor = new Mover(dtVtt, dtt, vtt, vst);
vst.onDoubleClick(movedor);

let svc = new CfgTests();
//svc.getTests(test, 0);
svc.getTestsBySeccionExamen(test, 0, 0);


function addTest() {
    let itemId = vst.getSelectedItemId();
    let item = vst.getSelectedItemById(itemId);
    vst.removeItemById(itemId);
    vtt.addItem(item);
}

function delTest() {
    let items = vtt.getSelectedItems();
    console.log(items);
    vtt.removeItems(items);
    vst.addItems(items);
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