function validaDatos(){
    let codigo = $("#txtCodigo").val();
    let nombre = $("#txtNombre").val();
    let codigoBarras = $("#selectCodigoBarras").val();
    console.log(codigoBarras);
    let validar = true;
    
    if (codigo.length <= 0) {
        $("#txtCodigo").addClass("is-invalid");
        handlerMessageError("Código no debe estar vacío");
        validar = false;
    }
    if (nombre.length <= 0) {
        $("#txtNombre").addClass("is-invalid");
        handlerMessageError("Nombre no debe estar vacío");
        validar = false;
    }
    if (codigoBarras === null || codigoBarras === "N") {
        //$("#txtCodigoBarras").addClass("is-invalid");
        $("#selectCodigoBarras").addClass("is-invalid");
        handlerMessageError("Código de barras no debe estar vacío");
        validar = false;
    }
    return validar;
}

function createArrayData(){
    let procesoDTO = new Object();
    
    let sigmaProcesos = {
        "spCodigoproceso": $("#txtCodigo").val(),
        "spIdproceso": $("#txtIdProceso").val(),
        "spDescripcion": $("#txtNombre").val(),
        "spEstacionsigma": $("#selectEstacion").val(),
        "spParametros": $("#txtParametros").val(),
        "spCargatemporal": $("#chbxCargaTemporal").is(":checked")?'S':'N',
        "spTiempocargatemporalseg": $("#txtSegundosCargaTemporal").val(),
        "spCargaproceso": $("#chbxCargaProceso").is(":checked")?'S':'N',
        "spTiempocargaprocesoseg": $("#txtSegundosCargaProceso").val(),
        "spActivo": $("#checkBoxActivo").is(":checked")?'S':'N',
        "spCodigohost": $("#txtCodigoHost").val(),
        //"spCodigobarras": $("#txtCodigoBarras").val().toUpperCase(),
        "spCodigobarras": $("#selectCodigoBarras").val(),
        "spResultadostemporales": $("#chbxResultadosTemporales").is(":checked")?'S':'N',
        "spNmuestralargo12": $("#chbxNroMuestraCorrelativo").is(":checked")?'S':'N',
        "spMuestrarecepcionada": $("#chbxMuestraRecepcionada").is(":checked")?'S':'N',
        "spControlalarmas": $("#chbxControlAlarmas").is(":checked")?'S':'N',
        "spSort": $("#txtOrden").val(),
        "spCodigoqc": $("#txtCodigoQC").val(),
        "spQc": $("#chbxQC").is(":checked")?'S':'N',
        //"spFirmaexamenes": $("#").val(),
        //"spAutorizaexamenes": $("#").val(),
        //"spStdfechadesde": $("#").val(),
        //"spStdfechahasta": $("#").val(),
        //"spStdfechaprocesoactual": $("#").val(),
        "spSenderid": $("#txtSender").val(),
        "spReceiverid": $("#txtReceiver").val(),
        "spIdseccion": $("#selectSeccion").val(),
        "spIdcentrodesalud": $("#selectCentroSalud").val()
    };
    procesoDTO.sigmaProcesos = sigmaProcesos;
    procesoDTO.ldvTipocomunicacion = {
        "ldvtcIdtipocomunicacion": $("#selectTipoComunicacion").val()
    };
    let testsProceso = vtt.getAllItems();
    let procesosTests = [];
    for (let i=0; i<testsProceso.length; i++){
        let datosProceso = testsProceso[i].dataValue;
        let procesoTest = {
            "sigmaProcesostest": {
                "id": {
                    "sptIdtest": datosProceso.ctIdtest,
                    "sptIdproceso": $("#txtIdProceso").val()
                },
                "sptCodigorecepcion": datosProceso.sptCodigorecepcion,
                "sptCodigoenvio": datosProceso.sptCodigoenvio,
                "sptActivo": datosProceso.sptActivo
            },
            "idTest": datosProceso.ctIdtest,
            "idSigmaTipoMuestra": datosProceso.idSigmaTipoMuestra,
            "idtiporesultado": datosProceso.idtiporesultado,
            "cmueIdtipomuestra": datosProceso.idTipoMuestra,
            "prefijoTipoMuestra": datosProceso.prefijoTipoMuestra,
            "conversiones": datosProceso.conversiones || null
        };
        procesosTests.push(procesoTest);
        
    }
    //Alarmas
    let alarmas = gTableAlarmasProceso.tablaAlarmasProceso.rows().data();
    let itemsAlarmas = new Array();
    let na = alarmas.data().length;
    for (let i = 0; i < na; i++) {
        itemsAlarmas.push(alarmas.data()[i]);
    }
    procesoDTO.procesosAlarmas = itemsAlarmas;
    procesoDTO.procesosTests = procesosTests;
    console.log(procesoDTO);
    return procesoDTO;
}

$("#btnAgregarProceso").click(function (){
    if (validaDatos()){
        let dataProceso = createArrayData();
        $.ajax({
            type: "POST",
            url: "api/proceso/save",
            data: JSON.stringify(dataProceso),
            contentType: "application/json",
            success: function(data){
                if (data.status === 200){
                    handlerMessageOk(data.message);
                    $(".formProceso").prop("disabled", true);
                    limpiarFormulario();
                    listarProcesos();
                } else {
                    handlerMessageError(data.message);
                }
            },
            error: function (msg) {
                handlerMessageError("Error al guardar");
            } 
        });
    }
});

$("#btnUpdateProceso").click(function (){
    if (validaDatos()){
        let dataProceso = createArrayData();
        $.ajax({
            type: "PUT",
            url: "api/proceso/update",
            data: JSON.stringify(dataProceso),
            contentType: "application/json",
            success: function(data){
                if (data.status === 200){
                    handlerMessageOk(data.message);
                    $(".formProceso").prop("disabled", true);
                    //limpiarFormulario();
                    listarProcesos();
                } else {
                    handlerMessageError(data.message);
                }
            },
            error: function (msg) {
                handlerMessageError("Error al guardar");
            } 
        });
    }
});

$("#txtCodigo").keyup(function () {
    if ($("#txtCodigo").val().length > 0) {
        $("#txtCodigo").removeClass("is-invalid");
    }
});

$("#txtNombre").keyup(function () {
    if ($("#txtNombre").val().length > 0) {
        $("#txtNombre").removeClass("is-invalid");
    }
});

$("#txtCodigoBarras").keyup(function () {
    if ($("#txtCodigoBarras").val().length > 0) {
        $("#txtCodigoBarras").removeClass("is-invalid");
    }
});

$("#selectCodigoBarras").change(function () {
    if ($("#selectCodigoBarras").val() !== "") {
        $("#selectCodigoBarras").removeClass("is-invalid");
    }
});
