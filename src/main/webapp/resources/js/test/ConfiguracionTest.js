var dtVT = new DataTableVisualTarget("#tableMetodos");
var dt = new DataSource([]);
vt = new VisualTarget(dt, dtVT);
vs = new VisualSource();
test = genVSFiller("#idDualDataVisualSource", vs, "#idSearcher");

var movedor = new Mover(dtVT, dt, vt, vs);

vs.onDoubleClick(movedor);
let svc = new CfgMetodos();
svc.getMetodos(test, 0);

var dtVTGlosas = new DataTableGlosas("#tableGlosas");

var dtG = new DataSource([]);
vtG = new VisualTarget(dtG, dtVTGlosas);
vsG = new VisualSource();
testGlosa = genVSFillerGlosa("#idDualDataVisualSourceGlosa", vsG, "#idGlosaSearcher");

var movedor2 = new Mover(dtVTGlosas, dtG, vtG, vsG);

vsG.onDoubleClick(movedor2);
let svcGlosas = new CfgGlosas();
svcGlosas.getGlosas(testGlosa, 0, 0);

//var testID = 0;
/*
new ImaskInputNumber('txtNroDecimales', true ,0, 999,99);

new ImaskInputNumber('txtValoresEvaluar', false ,0, 999);
new ImaskInputNumber('txtPorcentaje', false ,0, 99);
new ImaskInputNumber('txtDiasEvaluables', false ,0, 999);

new ImaskInputNumber('ctSort', false ,0, 99);
new ImaskInputNumber('txtOrdenReporte', false ,0, 99);*/



function eventoAdd() {
    let itemId = vs.getSelectedItemId();
    let item = vs.getSelectedItemById(itemId);
    vs.removeItemById(itemId);
    vt.addItem(item);
}

function eventoDel() {
    let items = vt.getSelectedItems();
    console.log(items);
    vt.removeItems(items);
    vs.addItems(items);
}

function eventoLimpiar() {
    let items = vt.getItems();
    if (items.length>0) {
        vt.removeItems(items);
        vs.addItems(items);
    }
}

function addGlosa() {
    let itemId = vsG.getSelectedItemId();
    let item = vsG.getSelectedItemById(itemId);
    vsG.removeItemById(itemId);
    vtG.addItem(item);
}

function delGlosa() {
    let items = vtG.getSelectedItems();
    console.log(items);
    vtG.removeItems(items);
    vsG.addItems(items);
}

function eventoLimpiarGlosa() {
    let items = vtG.getItems();
    if (items.length>0) {
        vtG.removeItems(items);
        vsG.addItems(items);
    }
}


$("#clickAddBtn").click(eventoAdd);
$("#clickDelBtn").click(eventoDel);

$("#clickAddBtnGlosa").click(addGlosa);
$("#clickDelBtnGlosa").click(delGlosa);

let tipoResultado = 0;

var tablaValoresReferencia = null;

$(document).ready(function () {
    $(".ocultar").hide();
    cargarMuestras();
    cargarLaboratorios();
    cargarBacGruposTests();
    cargarGruposFonasa();
    cargarSeccionesFiltro(secciones());
    cargarEnvases(envases());
    cargarExamenes(examenes());
    listarAntecedentes();
    initTablaValoresReferencia(0);
    //listarMetodos();
    $(".disabledForm").prop("disabled", true);
    //$("#selectObligado").prop("disabled", true);
    $('.selectpicker').selectpicker('refresh');
    //$(".dual-listbox__search").attr("placeholder", "Buscar");
    $('#selectTipoResultado').selectpicker('val', '');
    $('#selectUnidadMedida').selectpicker('val', '');
});

/************** FILTRO TABLA ***************************************************************/
IMask( document.getElementById('txtVolumenMuestra'), {
    mask: Number,  
    scale: 2,
    signed: false, 
    // thousandsSeparator: '.',  
    padFractionalZeros: true, 
    normalizeZeros: true,  
    radix: '.',  // radix: ',',  
    // mapToRadix: [',', '.'], 
    max: 5000,
    min: 0,
  });

$("#txtCodigoFiltro").keyup(function () {
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

$("#txtNombreTestFiltro").keyup(function () {
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

$("#selectSeccionFiltro").change(function () {
    if ($("#selectSeccionFiltro :selected").val() != 0) {
        $("#selectExamenFiltro").val(0);
        $("#selectExamenFiltro").prop("disabled", true);
        $("#txtCodigoFiltro").prop("disabled", true);
        //$('.selectpicker').selectpicker('refresh');
    } else {
        $("#selectExamenFiltro").prop("disabled", false);
        $("#txtCodigoFiltro").prop("disabled", false);
        //$('.selectpicker').selectpicker('refresh');
    }
});

$("#selectExamenFiltro").change(function () {
    if ($("#selectExamenFiltro :selected").val() != 0) {
        $("#selectSeccionFiltro").val(0);
        $("#selectSeccionFiltro").prop("disabled", true);
        $("#txtCodigoFiltro").prop("disabled", true);
        //$('.selectpicker').selectpicker('refresh');
    } else {
        $("#selectSeccionFiltro").prop("disabled", false);
        $("#txtCodigoFiltro").prop("disabled", false);
        //$('.selectpicker').selectpicker('refresh');
    }

});

$("#txtNombreTestFiltro, #txtCodigoFiltro").keyup(function(input) {
    if (input.which === 13) {
      $("#btnBuscarFiltro").click();
    }
  });

$("#btnBuscarFiltro").click(function () {
    if (validCodigo() === true && validNombre() === true && $("#selectExamenFiltro :selected").val() == 0 && $("#selectSeccionFiltro :selected").val() == 0) {
        if($("#txtNombreTestFiltro").val().length == 0 ){
            handlerMessageError("Se debe completar al menos un campo de búsqueda");
        }
    } else {
        /*let codigo = $("#txtCodigoFiltro").val().toUpperCase();
        let nombre = $("#txtNombreTestFiltro").val().toUpperCase();
        let idSeccion = $("#selectSeccionFiltro :selected").val();
        let idExamen = $("#selectExamenFiltro :selected").val();*/
        filtrarLike();
        
    }
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtNombreTestFiltro").val("");
    $("#selectExamenFiltro").val(0);
    $("#selectSeccionFiltro").val(0);
    $("#tbodyFiltro").empty();
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtNombreTestFiltro").prop("disabled", false);
    $("#selectExamenFiltro").prop("disabled", false);
    $("#selectSeccionFiltro").prop("disabled", false);
    //$('.selectpicker').selectpicker('refresh');
    $("#lblErrorFiltro").hide();
    $("#lblTotalFiltro").hide();
});

$("#nuevoTest").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

function filtrarLike() {
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val().toUpperCase(),
        nombre: $("#txtNombreTestFiltro").val().toUpperCase(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N",
        idSeccion: $("#selectSeccionFiltro :selected").val(),
        idExamen: $("#selectExamenFiltro :selected").val()
    });
    /*let url = getctx + "/api/test/";
    if (codigo !== ""){
        url += "codigo/" + codigo;
    } else {
        if (idSeccion !== "N"){
            url += "seccion/" + idSeccion;
            if (nombre !== ""){
                url += "/" + nombre;
            }
        } else if (idExamen !== "N" ){
            url += "examen/" + idExamen;
            if (nombre !== ""){
                url += "/" + nombre;
            }
        } else {
            url += "nombre/" + nombre;
        }
    }*/
    $.ajax({
        //type: "get",
        type: "post",
        //url: url,
        url: gCte.getctx + "/api/tests/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (json) {
            $("#tbodyFiltro").empty();
            let cont = 0;
            if (json.dato.length > 0) {
                json.dato.forEach(function (test) {
                    //if (($("#chkMostrarActivos").is(":checked") && test.ctActivo === "S") || !$("#chkMostrarActivos").is(":checked")) {
                        cont++;
                        $("#tbodyFiltro")
                                .append("<tr class='pointer' onclick='buscarCodigo(this)' >\n\
                                            <th class='row mx-auto'>" + cont + "</th>\n\
                                            <td class='codigoTest'>" + test.ctCodigo + "</td>\n\
                                            <td class=''>" + test.ctAbreviado + "</td>\n\
                                            <td class='idTest' style='display:none'>" + test.ctIdtest + "</td>\n\
                                        </tr>");
                        
                    //}


                });
                $("#lblErrorFiltro").hide();
                $("#lblTotalFiltro").show();
                $("#totalFiltro").text(cont);
            } else {
                $("#lblErrorFiltro").show();
                $("#lblTotalFiltro").hide();
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}
/*************** BUSQUEDA POR ID ********************************************************/
function buscarCodigo(a) {
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    var idTest = $row.find(".idTest").text(); // Find the text
    buscarTestPorId(idTest);
    $('.selectpicker').selectpicker('refresh');
}

function buscarTestPorId(idTest,collapse = true){
	if(idTest != undefined){
    $.ajax({
        type: "get",
        url: getctx + "/api/test/" + idTest,
        datatype: "json",
        success: function (json) {
            if(collapse){
                $("#collapseHeader").collapse('hide');
            }
            ui.limpiarTodosTooltip("#panelTestResultados");
            $(".is-invalid").removeClass("is-invalid");
            if ($("#spanDatosBasicos").length > 0) {
                $("#spanDatosBasicos").remove();
              }
            if ($("#spanGlosas").length > 0) {
                $("#spanGlosas").remove();
              }
            if ($("#spanResultados").length > 0) {
                $("#spanResultados").remove();
              }
            // $("#collapseOne8").collapse('hide');
            $("#collapseOne8").collapse('hide')
            //testID = json.cfgTest.ctIdtest;
            /*** DATOS BÁSICOS ***/
            $("#txtIdTest").val(json.cfgTest.ctIdtest);
            $("#txtCodigo").val(json.cfgTest.ctCodigo);
            $("#txtNombre").val(json.cfgTest.ctAbreviado);
            $("#txtDescripcion").val(json.cfgTest.ctDescripcion);
            $("#selectTipoMuestra").val(json.idTipoMuestra);
            $("#selectLab").val(json.cfgSecciones.cfgLaboratorios.clabIdlaboratorio);
            cargarSecciones(json.cfgSecciones.cfgLaboratorios.clabIdlaboratorio);
            $("#selectSeccion").val(json.cfgSecciones.csecIdseccion);
            $("#selectEnvase").val(json.idEnvase);
            $("#checkBoxPlanillaHistorica").prop("checked", json.cfgTest.ctPlanillahistorica === "S" ? true : false);
            $("#checkBoxHojaTrabajo").prop("checked", json.cfgTest.ctTesthojatrabajo === "S" ? true : false);
            $("#checkBoxRequiereCultivo").prop("checked", json.cfgTest.ctEscultivo === "S" ? true : false);
            /*** RESULTADO ***/
            tipoResultado = json.idTipoderesultado;
            $("#selectTipoResultado").val(tipoResultado);
            habilitarBtnModales(tipoResultado);
            
            
            //definir segun valor de tipo resultado
            $("#selectUnidadMedida").val(json.idUnidadmedida || 0);
            $("#txtNroDecimales").val(json.cfgTest.ctDecimales);
            $("#txtResultadoOmision").val(json.cfgTest.ctResultadoomision);
            /*** DATOS EXTRA ***/
            $("#txtVolumenMuestra").val(json.cfgTest.ctVolumenmuestra);
            $("#txtCodigoLoinc").val(json.cfgTest.ctCodigoloinc);
            $("#selectGrupoMicrobiologia").val(json.idBacgrupotest || 0);
            $("#selectGrupoFonasa").val(json.cfgTest.ctIdgrupoexamenesfonasa || 0);
            $("#checkboxObligado").prop("checked", json.cfgTest.ctResultadoobligado === "S" ? true : false);
            let ctCondicion = null;
            if (json.cfgTest.ctCondicion!== 'N'){
                ctCondicion = json.cfgTest.ctCondicion.split(" ");
            }
            $("#selectObligado").val(ctCondicion!==null?ctCondicion[0]:"");
           
            $("#txtObligado").val((ctCondicion!==null && ctCondicion[1] !== 'undefined' ) ? ctCondicion[1] : "");
            $("#chbxOpcional").prop("checked", json.cfgTest.ctOpcional === "S" ? true : false);
            $("#txtOrdenSistema").val(json.cfgTest.ctSort);
            $("#txtOrdenReporte").val(json.cfgTest.ctTestreporte);
            /*** MÉTODOS (se usa funciones de dual-data-metodos.js) ***/
            eventoLimpiar();
            
            json.listMetodos.forEach(function(metodo){
                let itemTest = new DataItem(metodo.cmetIdmetodo, metodo.cmetDescripcion, metodo.cmtActivo,
                                            (metodo.cmtEsvalorpordefecto !== null ? metodo.cmtEsvalorpordefecto : "N"));
                let item = new Item(itemTest);
                vt.addItemByIdTest(item);
                vs.removeItemById(item.id);
            });
            /*** GLOSAS (se usa funciones de dual-data-metodos.js y asignacionGlosas.js) ***/
            eventoLimpiarGlosa();
            json.listGlosas.forEach(function(glosa){
                let itemGlosa = new DataGlosas(glosa.cgIdglosa, glosa.cgCodigoglosa, glosa.cgDescripcion,
                                                (glosa.cgtEsglosacritica !== null ? glosa.cgtEsglosacritica : "N"),
                                                (glosa.cgtSefirmaporlotes !== null ? glosa.cgtSefirmaporlotes : "N"));
                let itemg = new Item(itemGlosa);
                vtG.addItem(itemg);
                vsG.removeItemById(itemg.id);
            });
            /*** VALORES DE REFERENCIA***/
            initTablaValoresReferencia(json.cfgTest.ctIdtest);
            /*** DELTA CHECK ***/
            $("#txtValoresEvaluar").val(json.cfgTest.ctDeltacantidad);
            $("#txtPorcentaje").val(json.cfgTest.ctDeltaporcentaje);
            $("#txtDiasEvaluables").val(json.cfgTest.ctDiasevaluables);
            /*** ANTECEDENTES ***/
            $(".chkAntecedentes").prop("checked", false);
            json.listAntecedentes.forEach(function (antecedente){
                $("#antecedente-"+antecedente.caIdantecedente).prop("checked", true);
            });
            /*** HOST  ***/
            $("#txtHost").val(json.cfgTest.ctHost);
            $("#txtHostMicro").val(json.cfgTest.ctHostmicro);
            /***  ***/
            $("#btnAgregarTest").prop("disabled", true);
            nombreTitulo(json.cfgTest.ctAbreviado);
            $(".formTest").prop("disabled",true);
            //falta guardar el activo e inactivo
            //   json.ceActivo === "S" ? $(".switch-content").addClass("inactivo") : $(".switch-content").removeClass("inactivo")
            //falta guardar el activo e inactivo
            $(".switch-content").addClass("disabled");
            $("#selectUnidadMedida").prop("disabled",true);
            $("#txtNroDecimales").prop("disabled",true);
            $("#btnLimpiar").prop("disabled",true);
            activarEdit();
            $('.selectpicker').selectpicker('refresh');
        },
        error: function (msg) {
            console.log(msg);
        }
    });
    }else{
	console.log("idTest undefined")
}
}
/*************** SELECTS ********************************************************/
let examenes = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/cfgexamen/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

function cargarExamenes(examenes){
    let options = `<option value="0" selected>TODOS</option>`;
    examenes.forEach((element) => {
        options += `<option value="${element.ceIdexamen}">${element.ceAbreviado}</option>`;
    });
    $("#selectExamenFiltro").html(options);
}

function cargarMuestras(){
    $.ajax({ 
        type: "GET",
        url: getctx + "/api/dominio/muestras/list",
        datatype: "json",
        async: false,
        success: function(data){
            for (let muestra of data) {
                $('#selectTipoMuestra').append($('<option>', {
                    value: muestra.cmueIdtipomuestra,
                    text: muestra.cmueDescripcionabreviada
                }));
            }
            $('#selectTipoMuestra').selectpicker('val', '');
            $('#selectTipoMuestra').selectpicker('refresh');
        },
        error: function (msg) {
            handlerMessageError(msg);
        } 
    });
}

function cargarLaboratorios() {    
    $.ajax({
        type: "GET",
        url: getctx + "/api/laboratorios/centros/list",
        async: false,
        datatype: "json",
        success: function(data){
            for (let lab of data){
                $('#selectLab').append($('<option>', {
                    value: lab.clab_IDLABORATORIO,
                    text: lab.clab_DESCRIPCION + " - " + lab.ccds_DESCRIPCION
                }));
            }
          
            $('#selectLab').selectpicker('val', '');
            $('#selectLab').selectpicker('refresh');

            $('#selectSeccion').empty();
            $('#selectSeccion').selectpicker('refresh');
        },
        error: function(msg){
            handlerMessageError(msg);
        }
    });
}

let secciones = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/secciones/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

let envases = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/envases/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

function cargarSeccionesFiltro(secciones){
    let options = `<option value="0" disabled selected>TODOS</option>`;
    secciones.forEach((element) => {
        options += `<option value="${element.csecIdseccion}">${element.csecDescripcion}</option>`;
    });
    $("#selectSeccionFiltro, #selectSeccionGlosa").html(options);
}

$("#selectLab").on('change', function (){
    $('#selectSeccion').empty();
    $('#selectSeccion').selectpicker('refresh');
    let idLab = $("#selectLab").val();
    if (idLab !== 'N'){
        cargarSecciones(idLab)
        $('.selectpicker').selectpicker('refresh');
    }
});

function cargarSecciones(idLab){
    $.ajax({
        type: "GET",
        url: getctx + "/api/secciones/laboratorios/list/"+idLab,
        datatype: "json",
        async: false,
        success: function(data){
            for (let secc of data) {
                $('#selectSeccion').append($('<option>', {
                    value: secc.csec_IDSECCION,
                    text: secc.csec_DESCRIPCION + " - " + secc.clab_DESCRIPCION
                }));
            }
            $("#selectSeccion")
            .removeClass("is-invalid")
            .selectpicker("setStyle")
            .parent()
            .removeClass("is-invalid");
            $('#selectSeccion').selectpicker('val', '');
            $('#selectSeccion').selectpicker('refresh');
        },
        error: function (msg) {
            handlerMessageError(msg);
        } 
    });
}

function cargarBacGruposTests(){
    $.ajax({
        type: "GET",
        url: getctx + "/api/test/bacGruposTests",
        datatype: "json",
        async: false,
        success: function(data){
            $("#selectGrupoMicrobiologia").html('<option value="0" disabled selected>Seleccionar</option>'); 
            data.forEach(bac=>{
                $('#selectGrupoMicrobiologia').append($('<option>', {
                    value: bac.cbgtIdbacgrupotest,
                    text: bac.cbgtDescripcion
                }));
            });
        },
        error: function (msg) {
            handlerMessageError(msg);
        } 
    });
}

function cargarGruposFonasa(){
    $.ajax({
        type: "GET",
        url: getctx + "/api/test/gruposExamenesFonasa",
        datatype: "json",
        async: false,
        success: function(data){
            $("#selectGrupoFonasa").html('<option value="0" disabled selected>Seleccionar</option>'); 
            data.forEach(fonasa=>{
                $('#selectGrupoFonasa').append($('<option>', {
                    value: fonasa.cgefIdgrupoexamenfonasa,
                    text: fonasa.cgefDescripcion
                }));
            });
        },
        error: function (msg) {
            handlerMessageError(msg);
        } 
    });
}

function cargarEnvases(envases){
    let options = ``;
    envases.forEach((element) => {
        options += `<option value="${element.cenvIdenvase}">${element.cenvDescripcion}</option>`;
    });
    $("#selectEnvase").html(options);
    $('#selectEnvase').selectpicker('val', '');
    $('#selectEnvase').selectpicker('refresh');
}

/*
function cargarMetodos(){
    $.ajax({
        type: "GET",
        url: getctx + "/api/test/metodos",
        datatype: "json",
        async: false,
        success: function(data){
            $("#selectMetodos").empty(); 
            data.forEach(metodo=>{
                $('#selectMetodos').append($('<option>', {
                    value: metodo.cmetIdmetodo,
                    text: metodo.cmetDescripcion
                }));
            });
        },
        error: function (msg) {
            handlerMessageError(msg);
        } 
    });
}*/

/*
 * function cargarEnvases();
 * function cargarTiposderesultados();
 * function cargarUnidadesdemedida();
 */

function listarAntecedentes(){
    $.ajax({
        type: "GET",
        url: getctx + "/api/test/antecedentes",
        datatype: "json",
        async: false,
        success: function (data) {
            $("#listAntecedentes").empty();
            for (let antecedente of data){
                if (antecedente.caActivo === "S"){
                    $("#listAntecedentes")
                            .append('<li class="col-6" style="list-style:none;">\n\
                                        <input id="antecedente-'+antecedente.caIdantecedente+'" class="chkAntecedentes formTest" type="checkbox"> \n\
                                        <label for="antecedente-'+antecedente.caIdantecedente+'">'+antecedente.caDescripcion+'</label>\n\
                                    </li>');
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            handlerMessageError("Error");
        }
    });
}

function listarMetodos(){
    $.ajax({
        type: "GET",
        url: getctx + "/api/test/metodos",
        datatype: "json",
        async: false,
        success: function (data) {
            $("#listMetodos").empty();
            for (let metodo of data){
                $("#listMetodos")
                        .append('<li style="list-style:none;">\n\
                                    <input id="metodo-'+metodo.cmetIdmetodo+'" class="chkMetodos" type="checkbox"> \n\
                                    <label for="metodo-'+metodo.cmetIdmetodo+'">'+metodo.cmetDescripcion+'</label>\n\
                                </li>');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            handlerMessageError("Error");
        }
    });
}

/*************** CRUD ********************************************************/

$("#txtCodigo").blur(function () {
    if ($("#txtCodigo").val() !== null && $("#txtCodigo").val().trim() !== "") {
        comprobarCodigoExiste($("#txtCodigo").val().toUpperCase());
    }
});

function comprobarCodigoExiste(codigo){
    $.ajax({
        type: "get",
        url: getctx + "/api/test/busquedacodigo/"+codigo,
        datatype: "json",
        success: function(json){
            if (json.status === 200){
                handlerMessageError(json.message);
                buscarTestPorId(json.dato[0].ctIdtest);
            }
        },
        error: function (msg){
            handlerMessageError(msg);
        }
    });
}

$("#txtNombre").keyup(function () {
    nombreTitulo($("#txtNombre").val());
});

$("#checkBoxLever").click(function () {
    nombreTitulo($("#txtNombre").val());
    if ($(this).is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($(this).is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

function nombreTitulo(nombre) {
    if (!($("#checkBoxLever").is(":checked"))) {
        if(nombre != ""){
            $("#tituloRegistro").text("Test: " + nombre);
        }else{
            $("#tituloRegistro").text("Test");
        }
        $("#tituloRegistro").css("color","red");
    } else {
        if(nombre != ""){
            $("#tituloRegistro").text("Test: " + nombre);
        }else{
            $("#tituloRegistro").text("Test");
        }
        $("#tituloRegistro").css("color","black");
    }
}

$("#selectTipoResultado").change(function () {
    let tipoResultado = $("#selectTipoResultado :selected").val();
    console.log(tipoResultado)
    habilitarBtnModales(tipoResultado);
    habilitarSelectTextResultados(tipoResultado);
    $('.selectpicker').selectpicker('refresh');
});

function habilitarBtnModales (tipoResultado){
    switch (parseInt(tipoResultado)){
        case 2:
        case 11:
            $("#btnGlosas").prop("disabled", false);
            $("#btnValoresReferencia").prop("disabled", true);
            $("#btnDeltaCheck").prop("disabled", true);
            $("#btnFormula").prop("disabled", true);
            break;
        case 3:
            $("#btnGlosas").prop("disabled", true);
            $("#btnValoresReferencia").prop("disabled", false);
            $("#btnDeltaCheck").prop("disabled", false);
            $("#btnFormula").prop("disabled", true);
            break;
        case 5:
            $("#btnGlosas").prop("disabled", true);
            $("#btnValoresReferencia").prop("disabled", true);
            $("#btnDeltaCheck").prop("disabled", true);
            $("#btnFormula").prop("disabled", true);
            break;
        case 6:
            $("#btnGlosas").prop("disabled", true);
            $("#btnValoresReferencia").prop("disabled", false);
            $("#btnDeltaCheck").prop("disabled", false);
            
         //  $("#btnFormula").prop("disabled", false);
            
            
            break;
        default:
            break;
    }
}

function habilitarSelectTextResultados(tipoResultado){
    switch (parseInt(tipoResultado)){
        case 2:
        case 5:
        case 6:
            $("#txtNroDecimales").prop("disabled", false);
            $("#selectUnidadMedida").prop("disabled", false);
            break;
        case 11:
            $("#txtNroDecimales").prop("disabled", true);
            $("#txtNroDecimales").val("");
            $("#selectUnidadMedida").prop("disabled", true);
            $("#selectUnidadMedida").val(1);
            break;
        case 3:
            $("#txtNroDecimales").prop("disabled", false);
            $("#selectUnidadMedida").prop("disabled", false);
            break;
        default:
            break;
    }
    //$('.selectpicker').selectpicker('refresh');
}

$("#checkboxObligado").click(function () {	
    if ($('#checkboxObligado').is(":checked")) {	
        //$("#checkboxObligado").val("1");
        $("#selectObligado").prop("disabled", false);
        //$('.selectpicker').selectpicker('refresh');
    } else {
        //$("#checkboxObligado").val("0");
        $("#selectObligado").prop("disabled", true);
        $("#txtObligado").prop("disabled", true);
         $("#selectObligado").val("");
        $("#txtObligado").val("");
        //$('.selectpicker').selectpicker('refresh');
    }
});

$("#selectObligado").change(function () {
    var valor = $("#selectObligado :selected").val();
    if (valor !== "N") {
        $("#txtObligado").prop("disabled", false);
        // $("#txtObligado").addClass("is-invalid");
    } else {
        $("#txtObligado").prop("disabled", true);
        $("#txtObligado").val("");
        $("#txtObligado").removeClass("is-invalid");
    }
});

$("#txtObligado").keyup(function () {
    var valor = $("#txtObligado").val().length;
    console.log(valor);
    if (valor > 0) {
        $("#txtObligado").removeClass("is-invalid");
    } else {
        $("#txtObligado").addClass("is-invalid");
    }
});

$("#btnEditar").click(function() {
    if (
      localStorage.getItem("botonEditar") === "activo"
    ) {
        $(".formTest").prop("disabled",false);
        $(".switch-content").removeClass("disabled");
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").css("display", "flex");
        $('#btnLimpiar').addClass('disabled');
        habilitarSelectTextResultados(tipoResultado);
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');

    }
  });

function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    } else {
        return true;
    }
}

$("#circuloAgregarTest").hover(
        function () {
            $("#iAgregarTest").removeClass("text-primary");
            $("#iAgregarTest").addClass("text-white");
        },
        function () {
            $("#iAgregarTest").removeClass("text-white");
            $("#iAgregarTest").addClass("text-primary");
        }
);
$("#circuloLimpiarFiltro").hover(
        function () {
            $("#iLimpiarFiltro").removeClass("text-primary");
            $("#iLimpiarFiltro").addClass("text-white");
        },
        function () {
            $("#iLimpiarFiltro").removeClass("text-white");
            $("#iLimpiarFiltro").addClass("text-primary");
        }
);

function removeRow(event) {
    $($(event).closest("tr")).remove();
}

function alpha(e) {
    var k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 45 || k == 32 || k == 39 || k == 241 || k == 209 || (k >= 48 && k <= 57));
}


function validCodigo() {
    if ($("#txtCodigoFiltro").val().length >= 0 && $("#txtCodigoFiltro").val().length <= 1) {
        return true;
    } else {
        return false;
    }
}

function validNombre() {
    if ($("#txtNombreTestFiltro").val().length >= 0 && $("#txtNombreTestFiltro").val().length < 2) {
        return true;
    } else {
        return false;
    }
}

function activarEdit() {
    $("#iEditPaciente").removeClass("text-dark-50");
    $("#iEditPaciente").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");

}

function desactivarEdit() {
    $("#iEditPaciente").removeClass("text-primary");
    $("#iEditPaciente").addClass("text-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
}

function notificacionDosCaracteres() {
    $.notify({
        message: "Se requiere al menos 2 caracteres para buscar"
    }, {
        // settings
        element: 'body',
        position: null,
        type: "danger",
        allow_dismiss: true,
        newest_on_top: false,
        showProgressbar: false,
        placement: {
            from: "top",
            align: "left"
        },
        offset: 20,
        spacing: 10,
        z_index: 1031,
        delay: 5000,
        timer: 1000,
        url_target: '_blank',
        mouse_over: null,
        animate: {
            enter: 'animated fadeInDown',
            exit: 'animated fadeOutUp'
        },
        onShow: null,
        onShown: null,
        onClosed: null
    });
}

$("#circuloLimpiar").hover(
        function () {
            $("#iLimpiar").removeClass("text-primary");
            $("#iLimpiar").addClass("text-white");
        },
        function () {
            $("#iLimpiar").removeClass("text-white");
            $("#iLimpiar").addClass("text-primary");
        }
);
$("#circuloLimpiarFiltro").hover(
        function () {
            $("#iLimpiarFiltro").removeClass("text-primary");
            $("#iLimpiarFiltro").addClass("text-white");
        },
        function () {
            $("#iLimpiarFiltro").removeClass("text-white");
            $("#iLimpiarFiltro").addClass("text-primary");
        }
);
$("#circuloAgregarPaciente").hover(
        function () {
            $("#iAgregarPaciente").removeClass("text-primary");
            $("#iAgregarPaciente").addClass("text-white");
        },
        function () {
            $("#iAgregarPaciente").removeClass("text-white");
            $("#iAgregarPaciente").addClass("text-primary");
        }
);
$("#circuloEditarPaciente").hover(        
        function () {
        console.log('esta activado',localStorage.getItem("botonEditar"))
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarPaciente").addClass("bg-hover-blue");
                $("#iEditPaciente").removeClass("text-blue", "text-primary");
                $("#iEditPaciente").addClass("text-blue");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "inactivo") {
                $("#circuloEditarPaciente").removeClass("bg-hover-primary");
                $("#iEditPaciente").removeClass("text-white","text-blue");
                $("#iEditPaciente").addClass("text-dark-50");
            }
        }

);


$("#circuloBuscarPaciente").hover(
        function () {
            $("#iBuscarPaciente").removeClass("text-primary");
            $("#iBuscarPaciente").addClass("text-white");
        },
        function () {
            $("#iBuscarPaciente").removeClass("text-white");
            $("#iBuscarPaciente").addClass("text-primary");
        }
);


$("#iBuscarPaciente").click(function() {
    // $("#collapseOne8 ").collapse('show');
    $("#collapseOne8").collapse('show')
    $(window).scrollTop(0);
});

$("#btnLimpiar").click(function() {
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    	 $('.selectpicker').selectpicker('refresh');
});

$("#btnCancelTest").click(function() {
$(".formTest").prop("disabled", true);
$(".switch-content").addClass("disabled");
  $("#divActualizarBtn").hide();
  $("#divAgregarBtn").show();
  $('#btnLimpiar').removeClass('disabled');
  $('.selectpicker').selectpicker('refresh');
});

$(".cerrarModal").click(function() {
    handlerMessageWarning("Presione guardar o confirmar para registrar los cambios");
});


$("#btnGuardarMetodos").click(function() {
    let contVPD = 0;
    let items = vt.getAllItems();
    items.forEach(function (data){
        if (data.dataValue.cmtEsvalorpordefecto==='S'){
            contVPD++;
        }
    });
    //console.log(items)
    if (contVPD>1){
        handlerMessageError("Sólo debe asignar un método como valor por defecto");
        return false;
    } else if (contVPD===0 && items.length>0){
        handlerMessageError("Debe asignar algún método como valor por defecto");
        return false;
    } else {
        handlerMessageWarning("Presione guardar o confirmar para registrar los cambios");
    }
});

function limpiarFormulario(){
    //testID = 0;
    $("#txtIdTest").val("");
    $("#txtCodigo").val("");
    $("#txtNombre").val("");
    $("#tituloRegistro").text("Test");
    $("#txtDescripcion").val("");
    // $("#selectTipoMuestra").val("0");
    $('#selectTipoMuestra').selectpicker('val', '');
    $("#selectLab").val("N");
    $("#selectSeccion").html('<option value="0">Seleccionar</option>'); 
    //$("#selectSeccion").val("N");
    $("#selectEnvase").val("N");
    $("#lblEstado").text("Activo");
    $("#checkBoxLever").prop("checked", true);
    $("#checkBoxPlanillaHistorica").prop("checked", false);
    $("#checkBoxHojaTrabajo").prop("checked", false);
    $("#checkBoxRequiereCultivo").prop("checked", false);
    /*** RESULTADO ***/
    $("#selectTipoResultado").val("N");
    $("#btnGlosas").prop("disabled", true);
    $("#btnValoresReferencia").prop("disabled", true);
    $("#btnDeltaCheck").prop("disabled", true);
    $("#btnFormula").prop("disabled", true);
    $("#txtResultadoOmision").val("");
    /*** DATOS EXTRA ***/
    $("#txtVolumenMuestra").val("");
    $("#txtCodigoLoinc").val("");
    $("#selectGrupoMicrobiologia").val("0");
    $("#selectGrupoFonasa").val("0");
    $("#checkboxObligado").prop("checked", true);
    $("#selectObligado").val("N");
    $("#txtObligado").val("");
    $("#chbxOpcional").prop("checked", false);
    $("#txtOrdenSistema").val("");
    $("#txtOrdenReporte").val("");
    /*** DELTA CHECK ***/
    $("#txtValoresEvaluar").val("");
    $("#txtPorcentaje").val("");
    $("#txtDiasEvaluables").val("");
    /*** ANTECEDENTES ***/
    $(".chkAntecedentes").prop("checked", false);
    /*** METOODOS ***/
    eventoLimpiar();
    /*** GLOSAS ***/
    $("#selectSeccionGlosa").val("N");
    eventoLimpiarGlosa();
    /*** VALORES REFERENCIA ***/
    initTablaValoresReferencia(0);
    /*** HOST  ***/
    $("#txtHost").val("");
    $("#txtHostMicro").val("");
    $("#divAgregarBtn").show();
    $("#btnAgregarTest").prop("disabled", false);
    $("#divActualizarBtn").hide();
    $(".formTest").prop("disabled",false);
    $(".switch-content").removeClass("disabled inactivo");
    $("#selectUnidadMedida").prop("disabled", true);
    $("#selectUnidadMedida").val("0");
    $("#txtNroDecimales").prop("disabled", true);
    $("#txtNroDecimales").val("");

    $(".is-invalid").removeClass("is-invalid");
    if ($("#spanDatosBasicos").length > 0) {
        $("#spanDatosBasicos").remove();
      }
    if ($("#spanGlosas").length > 0) {
        $("#spanGlosas").remove();
      }
      if ($("#spanMetodos").length > 0) {
        $("#spanMetodos").remove();
      }
    if ($("#spanResultados").length > 0) {
        $("#spanResultados").remove();
      }
      ui.limpiarTodosTooltip("#panelTestResultados");
      $('.selectpicker').selectpicker('refresh');
}

$('#modalMetodos').on('shown.bs.modal', function () {
    $($.fn.dataTable.tables(true)).DataTable()
       .columns.adjust();
 });

 $('#exampleModalSizeSm').on('shown.bs.modal', function () {
    $($.fn.dataTable.tables(true)).DataTable()
       .columns.adjust();
 });
 
 
$("#btnValoresReferencia").click(function(){
    //tablaValoresReferencia = new TablaValoresReferencia("#tablaValoresReferencia", testID);
    let items = vt.getAllItems();
    if (items.length>0){
        $("#modalValoresReferencia").modal('show');
       // initTablaValoresReferencia(testID);
        vt.metodosTemp.forEach((metodo)=>{
            metodosTest.push(metodo.dataValue);
        });
    } else {
        handlerMessageError("Primero debe agregar métodos");
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
    var idCausa = $row.querySelector('td.idTest').textContent;
    buscarTestPorId(idCausa,false);
    $('.selectpicker').selectpicker('refresh');
}

$("#descargarExcelTest").click(function () {
getTestParaExcel();
});
function getTestParaExcel(){
	 $.ajax({
        type: "get",
        url: getctx + "/api/test/getParaExcel",
        dataType: 'json',
        success: function (json) {
			$.each( json.dato, function( index, value ){
				
			    var newRowContent = `<tr>
									    <td>${value.ct_ACTIVO}</td>
									    <td>${value.ct_CODIGO}</td>
									    <td>${value.ct_ABREVIADO}</td>
									    <td>${value.ct_DESCRIPCION}</td>
									    <td>${value.cmue_DESCRIPCION}</td>
									    <td>${value.clab_DESCRIPCION}</td>
									    <td>${value.csec_DESCRIPCION}</td>
									    <td>${value.cenv_DESCRIPCION}</td>
									    <td>${value.cmet_DESCRIPCION}</td>
									    <td>${value.ctr_DESCRIPCION}</td>
									    <td>${value.cum_DESCRIPCION}</td>
									    <td>${value.ct_DECIMALES}</td>
									    <td>${value.valores_REF}</td>
									    <td>${value.ct_VOLUMENMUESTRA}</td>
									    <td>${value.cgef_DESCRIPCION}</td>
									    
								    </tr>`;
			    $("#tb tbody").append(newRowContent);
			    
			});

			agregarExcelUtiles("Lista Test");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            
        }
    });

    
}
