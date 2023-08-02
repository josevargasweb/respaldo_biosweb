//const { lte } = require("lodash");
var gTableAlarmasProceso = new TablaAlarmasProceso();

$(document).ready(function(){
    $(".ocultar").hide();
    cargarSecciones(secciones());
    cargarEstaciones();
    cargarCentrosdesalud(centrosSalud());
    cargarTiposComunicacion(tiposComunicacion());
    cargarExamenes(examenes());
    cargarTiposMuestrasSigma(sigmatiposmuestras());
    cargarTiposResultado(tiposResultado());
    cargarTiposCondicion(tiposcondicion());
    cargarOperacionesmath(operacionesmath());
    listarProcesos();
    gTableAlarmasProceso.loadTablaAlarmas("N");
    $('.selectpicker').selectpicker('refresh');
});
new ImaskInputNumber('txtOrden', false, 1, 9999);
new ImaskInputNumber('txtSegundosCargaTemporal', false, 0, 999);
new ImaskInputNumber('txtSegundosCargaProceso', false, 0, 999);

function listarProcesos(){
    let filtros = JSON.stringify({
        codigo: $("#txtCodigoFiltro").val(),
        nombre: $("#txtNombreFiltro").val(),
        idSeccion: $("#selectSeccionFiltro").val(),
        idEstacion: $("#selectEstacionFiltro").val(),
        activo: $("#chkMostrarActivos").is(":checked") ? "S" : "N"
    });
    
    $.ajax({
        method: "POST",
        url: "api/procesos/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        dataType: "json",
        success: function (mensaje) {
            var cont = 0;
            let dato = mensaje.dato;
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            if (dato.length > 0) {
                dato.forEach(function (proceso) {
                    cont++;
                    $("#tbodyFiltro").append(`<tr class="pointer" onclick="selectProceso(this)" data-id-proceso="${proceso.spCodigoproceso}">
                                                <th class="row mx-auto">${cont}</th>
                                                <td class=''>${proceso.spEstacionsigma} | ${proceso.spActivo} | ${proceso.spDescripcion}</td>
                                              </tr>`);
                });
                $("#lblTotalFiltro").show();
            } else {
                $("#lblTotalFiltro").hide();
                $("#lblErrorFiltro").show();
            }
            $("#totalFiltro").text(cont);
            $('.selectpicker').selectpicker('refresh');
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

$("#txtCodigoFiltro").keyup(function () {
    if ($("#txtCodigoFiltro").val().length > 1) {
        $("#txtNombreFiltro").prop("disabled", true);
        $("#selectSeccionFiltro").prop("disabled", true);
        $("#selectEstacionFiltro").prop("disabled", true);
    } else {
        $("#txtNombreFiltro").prop("disabled", false);
        $("#selectSeccionFiltro").prop("disabled", false);
        $("#selectEstacionFiltro").prop("disabled", false);
    }
    listarProcesos();
    $(".selectpicker").selectpicker("refresh");
});

$("#txtNombreFiltro").keyup(function () {
    if ($("#txtNombreFiltro").val().length > 1) {
        $("#txtCodigoFiltro").attr("disabled", true);
        $("#selectSeccionFiltro").prop("disabled", true);
        $("#selectEstacionFiltro").prop("disabled", true);
    } else {
        $("#txtCodigoFiltro").attr("disabled", false);
        $("#selectSeccionFiltro").prop("disabled", false);
        $("#selectEstacionFiltro").prop("disabled", false);
    }
    listarProcesos();
    $(".selectpicker").selectpicker("refresh");
});

$("#selectSeccionFiltro").on('change', function () {
    if ($("#selectSeccionFiltro").val() > 0) {
        $("#selectEstacionFiltro").prop("disabled", true);
        $("#txtCodigoFiltro").prop("disabled", true);
        $("#txtNombreFiltro").prop("disabled", true);
    } else {
        $("#selectEstacionFiltro").prop("disabled", false);
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtNombreFiltro").prop("disabled", false);
    }
    listarProcesos();
    $(".selectpicker").selectpicker("refresh");
});

$("#selectEstacionFiltro").on('change', function () {
    if ($("#selectEstacionFiltro").val() > 0) {
        $("#selectSeccionFiltro").prop("disabled", true);
        $("#txtCodigoFiltro").prop("disabled", true);
        $("#txtNombreFiltro").prop("disabled", true);
    } else {
        $("#selectSeccionFiltro").prop("disabled", false);
        $("#txtCodigoFiltro").prop("disabled", false);
        $("#txtNombreFiltro").prop("disabled", false);
    }
    listarProcesos();
    $(".selectpicker").selectpicker("refresh");
});

$("#chkMostrarActivos").change(function(){
    listarProcesos();
});

$("#nuevoProceso").click(function () {
    $(".collapse").collapse('hide');
    $('html, body').animate({
        scrollTop: $("div#divForm").offset().top
    }, 700);
    limpiarFormulario();
});

$("#btnLimpiarFiltro").click(function () {
    $("#txtCodigoFiltro").val("");
    $("#txtNombreFiltro").val("");
    $("#selectSeccionFiltro").val(0);
    $("#selectEstacionFiltro").val(0);
    $("#chkMostrarActivos").prop("checked", false);
    $("#tbodyFiltro").empty();
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtNombreFiltro").prop("disabled", false);
    $("#selectSeccionFiltro").prop("disabled", false);
    $("#selectEstacionFiltro").prop("disabled", false);
    listarProcesos();
    $('.selectpicker').selectpicker('refresh');
});

function activarEdit() {
    $("#iEditProceso").removeClass("text-dark-50");
    $("#iEditProceso").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditProceso").removeClass("text-blue");
    $("#iEditProceso").addClass("text-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
}

$("#btnLimpiar").click(function() {
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

function selectProceso(a){
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    // var id = $row.find(".codigoProceso").text();
    let id = $row.attr("data-id-proceso");
    buscarPorId(id);
    activarEdit();
}

function buscarPorId(codigo,collapse = true){
    $.ajax({
        type: "GET",
        url: "api/proceso/"+codigo,
        datatype: "json",
        success: function (json) {
            if (json.status === 200){
                if(collapse){
                    $("#collapseHeader").collapse('hide');
                }
                let data = json.dato;
                $("#txtIdProceso").val(data.sigmaProcesos.spIdproceso);
                $("#txtCodigo").val(data.sigmaProcesos.spCodigoproceso);
                $("#txtNombre").val(data.sigmaProcesos.spDescripcion);
                $("#txtParametros").val(data.sigmaProcesos.spParametros);
                $("#selectTipoComunicacion").val(data.ldvTipocomunicacion !== null ? data.ldvTipocomunicacion.ldvtcIdtipocomunicacion : 0);
                $("#selectSeccion").val(data.sigmaProcesos.spIdseccion);
                $("#selectCentroSalud").val(data.sigmaProcesos.spIdcentrodesalud);
                $("#selectCodigoBarras").val(data.sigmaProcesos.spCodigobarras);
                $("#txtCodigoHost").val(data.sigmaProcesos.spCodigohost);
                $("#txtCodigoQC").val(data.sigmaProcesos.spCodigoqc);
                $("#selectEstacion").val(data.sigmaProcesos.spEstacionsigma);
                $("#txtOrden").val(data.sigmaProcesos.spSort);
                $("#txtSender").val(data.sigmaProcesos.spSenderid);
                $("#txtReceiver").val(data.sigmaProcesos.spReceiverid);
                $("#chbxControlAlarmas").prop("checked", data.sigmaProcesos.spControlalarmas === "S" ? true : false);
                $("#chbxResultadosTemporales").prop("checked", data.sigmaProcesos.spResultadostemporales === "S" ? true : false);
                $("#chbxNroMuestraCorrelativo").prop("checked", data.sigmaProcesos.spNmuestralargo12 === "S" ? true : false);
                $("#chbxQC").prop("checked", data.sigmaProcesos.spQc === "S" ? true : false);
                $("#chbxCargaTemporal").prop("checked", data.sigmaProcesos.spCargatemporal === "S" ? true : false);
                $("#txtSegundosCargaTemporal").val(data.sigmaProcesos.spTiempocargatemporalseg);
                $("#chbxCargaProceso").prop("checked", data.sigmaProcesos.spCargaproceso === "S" ? true : false);
                $("#txtSegundosCargaProceso").val(data.sigmaProcesos.spTiempocargaprocesoseg);
                $("#chbxMuestraRecepcionada").prop("checked", data.sigmaProcesos.spMuestrarecepcionada === "S" ? true : false);
                $("#selectSeccion").val(data.sigmaProcesos.spIdseccion || 0);
                $("#selectCentroSalud").val(data.sigmaProcesos.spIdcentrodesalud || 0);
                /*** Activo ***/
                $("#checkBoxActivo").prop("checked", data.sigmaProcesos.spActivo === "S" ? true : false);
                $(".switch-content").addClass("disabled");
                data.sigmaProcesos.spActivo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
                $("#lblEstado").text(data.sigmaProcesos.spActivo === "S" ? 'Activo' : 'Inactivo');
                /*** Procesos Test ***/
                dataTableTests.clear();
                limpiarTest();
                data.procesosTests.forEach(function (procesoTest){
                    let item = vst.getSelectedItemById(procesoTest.idTest);
                    item.dataValue.guardadoEnDB = 'S';
                    item.dataValue.sptCodigorecepcion = procesoTest.sigmaProcesostest.sptCodigorecepcion;
                    item.dataValue.sptCodigoenvio = procesoTest.sigmaProcesostest.sptCodigoenvio;
                    item.dataValue.sptActivo = procesoTest.sigmaProcesostest.sptActivo;
                    item.dataValue.idSigmaTipoMuestra = procesoTest.idSigmaTipoMuestra;
                    item.dataValue.idtiporesultado = procesoTest.idtiporesultado;
                    item.dataValue.conversiones = procesoTest.conversiones;
                    vtt.addItem(item);
                    vst.removeItemById(item.id);
                });
                llenarTablaTests();
                /*** Tabla Alarmas ***/
                gTableAlarmasProceso.loadTablaAlarmas(data.sigmaProcesos.spCodigoproceso);
                /*** ***/
                activarEdit();
                nombreTitulo(data.sigmaProcesos.spDescripcion, data.sigmaProcesos.spEstacionsigma);
                $("#btnAgregarProceso").prop("disabled", true);
                $("#btnAddTests").prop("disabled", true);
                $("#btnModalAlarmas").prop("disabled", true);
                $("#divActualizarBtn").hide();
                $("#divAgregarBtn").show();
                $(".formProceso").prop("disabled",true);
            } else {
                handlerMessageError(json.message);
            }
            $('.selectpicker').selectpicker('refresh');
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

$("#txtNombre").keyup(function () {
    nombreTitulo($("#txtNombre").val(), $("#selectEstacion").val());
});

$("#selectEstacion").change(function () {
    nombreTitulo($("#txtNombre").val(), $("#selectEstacion").val());
});

function nombreTitulo(nombre, estacion) {
    if (nombre !== "" || estacion > 0){
        $("#tituloRegistro").text("Proceso: " + nombre.toString().toUpperCase() + " | " + estacion);
    } else {
        $("#tituloRegistro").text("Proceso");
    }
}

$("#buscarProceso").click(function () {
    $("#collapseProcesos").collapse('show');
    $(window).scrollTop(0);
});

$("#checkBoxActivo").click(function () {
    if ($("#checkBoxActivo").is(":checked")) {
        $("#lblEstado").text("Activo");
    }
    if (!($("#checkBoxActivo").is(":checked"))) {
        $("#lblEstado").text("Inactivo");
    }
});

$("#iLimpiar").click(function () {
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

$("#btnEditar").click(function() {
    if (localStorage.getItem("botonEditar") === "activo") {
        $(".formProceso").prop("disabled",false);
        $("#txtCodigo").prop("disabled", true);
        $(".switch-content").removeClass("disabled");
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").css("display", "flex");
        $("#btnAddTests").prop("disabled", false);
        $("#btnModalAlarmas").prop("disabled", false);
        $('#btnLimpiar').addClass('disabled');
        $(".switch-content").removeClass("disabled");
        desactivarEdit();
        $('.selectpicker').selectpicker('refresh');

    }
});

$("#btnCancelProceso").click(function() {
    $(".formProceso").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $("#btnAddTests").prop("disabled", true);
    $("#btnModalAlarmas").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
});
$("#btnUpdateProceso").click(function() {

    $('#btnLimpiar').removeClass('disabled');

});

function limpiarFormulario(){
    nombreTitulo("", 0);
    $("#txtIdProceso").val("");
    $("#txtCodigo").val("");
    $("#txtNombre").val("");
    $("#txtParametros").val("");
    $("#selectTipoComunicacion").val(0);
    $("#selectSeccion").val(0);
    $("#selectCentroSalud").val(0);
    $("#selectCodigoBarras").val("N");
    $("#txtCodigoHost").val("");
    $("#txtCodigoQC").val("");
    $("#selectEstacion").val(0);
    $("#txtOrden").val("");
    $("#txtSender").val("");
    $("#txtReceiver").val("");
    $("#chbxControlAlarmas").prop("checked", false);
    $("#chbxResultadosTemporales").prop("checked", false);
    $("#chbxNroMuestraCorrelativo").prop("checked", false);
    $("#chbxCargaTemporal").prop("checked", false);
    $("#chbxQC").prop("checked", false);
    $("#txtSegundosCargaTemporal").val("");
    $("#chbxCargaProceso").prop("checked", false);
    $("#txtSegundosCargaProceso").val("");
    $("#chbxMuestraRecepcionada").prop("checked", false);
    $("#selectSeccion").val(0);
    $("#selectCentroSalud").val(0);
    $("#checkBoxActivo").prop("checked", true);
    $("#lblEstado").text("Activo");
    /** Tabla test **/
    limpiarTablaTests();
    dataTableTests.clear();
    /** Tabla alarmas **/
    gTableAlarmasProceso.clear();
    /** Formulario **/
    $(".is-invalid").removeClass("is-invalid");
    $("#divAgregarBtn").show();
    $("#btnAgregarProceso").prop("disabled", false);
    $("#divActualizarBtn").hide();
    $("#btnAddTests").prop("disabled", false);
    $("#btnModalAlarmas").prop("disabled", false);
    $(".formProceso").prop("disabled", false);
    $(".switch-content").removeClass("disabled inactivo");
    $('.selectpicker').selectpicker('refresh');
}

function limpiarTablaTests(){
    let countItems= vtt.countItems();
    if (countItems>0){
        limpiarTest();
    }
}

$("#txtCodigo").blur(function () {
    if ($("#txtCodigo").val() !== null && $("#txtCodigo").val().trim() !== "") {
        comprobarCodigoExiste($("#txtCodigo").val().toUpperCase());
    }
});
    
function comprobarCodigoExiste(codigo){
    $.ajax({
        type: "get",
        url: getctx + "/api/proceso/busquedacodigo/"+codigo,
        datatype: "json",
        success: function(json){
            if (json.status === 200){
                handlerMessageError(json.message);
                buscarPorId(json.dato[0].spCodigoproceso);
            }
        },
        error: function (msg){
            handlerMessageError(msg);
        }
    });
}

$("#chbxCondicional").click(function(){
    if ($("#chbxCondicional").is(":checked")){
        $(".formConditional").prop("disabled", false);
    } else {
        $(".formConditional").prop("disabled", true);
        
    }
});

$("#chbkCalculo").click(function(){
    if ($("#chbkCalculo").is(":checked")){
        $(".formCalculo").prop("disabled", false);
    } else {
        $(".formCalculo").prop("disabled", true);
        
    }
});

//agrega pestaña
function agregarTabs(counter){
    //toma el div original y lo clona
    let originalDiv = $("div#condicion-sub");
    let cloneDiv = originalDiv.clone();    

    //cambiar datos de los divs clonados
    cloneDiv.attr('id','condicion-sub'+counter);
    cloneDiv.removeClass("show active");

    $("#selectOperando",cloneDiv).attr({'id':'selectOperando'+counter});    //le cambia el nombre de la id
    $("#selectOperando"+counter,cloneDiv).val(0);

    $("#textValorEntrada",cloneDiv).attr({'id':'textValorEntrada'+counter});
    $("#textValorEntrada"+counter,cloneDiv).val("");

    $("#chbxCritico",cloneDiv).attr({'id':'chbxCritico'+counter});    
    $("#chbxCritico"+counter,cloneDiv).prop("checked", false); 

    $("#textValorSalida",cloneDiv).attr({'id':'textValorSalida'+counter});
    $("#textValorSalida"+counter,cloneDiv).val("");

    $("#chbxReprocesar",cloneDiv).attr({'id':'chbxReprocesar'+counter});    
    $("#chbxReprocesar"+counter,cloneDiv).prop("checked", false); 

    $("#chbxBloquear",cloneDiv).attr({'id':'chbxBloquear'+counter});    
    $("#chbxBloquear"+counter,cloneDiv).prop("checked", false); 

    $("#chbxFirmar",cloneDiv).attr({'id':'chbxFirmar'+counter});    
    $("#chbxFirmar"+counter,cloneDiv).prop("checked", false); 

    // //agrega en el div los datos ya formatiados
    $("#myTabContent").append(cloneDiv);


    //agregando al tab
    let originalTab = $("li#presentacion");
    let cloneTab = originalTab.clone(); 

    cloneTab.attr('id','presentacion'+counter);
    $("#condicion",cloneTab).attr({'id':'condicion'+counter,'data-target':'#condicion-sub'+counter,'aria-controls':"condicion-sub"+counter,'aria-selected':"false"});    //cambia los atributos del tab
    $("#condicion"+counter,cloneTab).removeClass("active");
    $("#condicion"+counter,cloneTab).text("Condicion "+counter)
    $("#nav-item-agregar").before(cloneTab);
    //incrementa el contador
    counter++;      
    $("#agregar-tab").attr("onclick","agregarTabs("+counter+")");
    $('.selectpicker').selectpicker('refresh');
};



//ALARMAS
$("#btnAddAlarma").click(function () {
    gTableAlarmasProceso.addAlarma();
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
    var idCausa = $row.getAttribute("data-id-proceso");
    buscarPorId(idCausa,false);
    $('.selectpicker').selectpicker('refresh');
}
