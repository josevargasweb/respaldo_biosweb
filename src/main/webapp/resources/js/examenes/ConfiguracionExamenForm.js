function validaDatos(){
    let codigo = $("#txtCodigo").val();
    let nombre = $("#txtNombre").val();
    let tablaTest = vtt.getAllItems();
    let nTests = tablaTest.length;
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
    if (nTests===0 && !$("#chkSoloPrestacion").is(":checked")) {
        handlerMessageError("Examen debe contener al menos un test");
        validar = false;
    }
    let indicaciones = [];
    
    $(".selectIndicacionesTM").each(function (){
        let idIndicacionTM = $(this).val();
        if (idIndicacionTM == 0){
            handlerMessageError("Debe seleccionar indicación de toma de muestras");
            validar = false;
        }
        indicaciones.push(idIndicacionTM);
    });
    
    if (validaArrayElementosRepetidos(indicaciones)){
        handlerMessageError("Indicaciones de toma de muestras no deben estar repetidas");
        validar = false;
    }
    
    return validar;
}

function transformarTiempoaMins(tiempo, unidadTiempo){
    let tiempoTransform = 0;
    switch (unidadTiempo){
        case "d":
            tiempoTransform = tiempo*1440;
            break;
        case "h":
            tiempoTransform = tiempo*60;
            break;
        case "m":
            tiempoTransform = tiempo;
            break;
    }
    return tiempoTransform;
}

function transformarMinsaTiempo(tiempo){
    let tiempoTransform = 0;

    if(Number.isInteger(tiempo/1440)){
        tiempoTransform = tiempo/1440;
    }else if(Number.isInteger(tiempo/60)){
        tiempoTransform = tiempo/60;
    }else{
        tiempoTransform = tiempo;
    }
   
    return tiempoTransform;
}

function transformarMinsaTiempoTexto(tiempo){
    let tiempoTransform = 0;

    if(Number.isInteger(tiempo/1440)){
        tiempoTransform = "d";
    }else if(Number.isInteger(tiempo/60)){
        tiempoTransform = "h";
    }else{
        tiempoTransform = "m";
    }
   
    return tiempoTransform;
}



function createArrayData(){
    
    let tablaTests = vtt.getAllItems();
    let multiSeccion = 'N';
    let seccionAux = 0;
    let examenesTests = [];
    let grupoMuestras = [];
    let tieneMuestrasAdicionales = 'N';
    for (let i=0; i<tablaTests.length; i++){
        if (seccionAux !== 0 && seccionAux !== tablaTests[i].dataValue.csecIdseccion ){
            multiSeccion = 'S';
        }
        let id = {
            "cfgTest": {
                //"cetIdexamen": $("#txtIdExamen").val(),
                "ctIdtest": tablaTests[i].dataValue.ctIdtest
            }
        };
        examenesTests.push(id);
        
        seccionAux = tablaTests[i].dataValue.csecIdseccion;
    }
    
    let tablaMuestras = vtm.getAllItems();
    if (tablaMuestras.length > 0){
        tieneMuestrasAdicionales = 'S';
    }
    for (let i=0; i<tablaMuestras.length; i++){
        let id = {
            "id": {
                "cgmeIdtipomuestra": tablaMuestras[i].dataValue.cmueIdtipomuestra
            }
        };
        grupoMuestras.push(id);
    }
    
    let indicacionesTM = [];
    $(".selectIndicacionesTM").each(function (){
        let indicacionTM = {
            "id": {
                "ceitmIdindicaciontm": $(this).val()
            }
        };
        indicacionesTM.push(indicacionTM);
    });
    
    let idExamenesConjuntos = $("#txtIdExamenesConjuntos").val();
    
    let examenesConjuntos = vte.getAllItems();
    let examenesConjuntosDetalle = [];
    if (examenesConjuntos.length > 0){
        for (let i=0; i<examenesConjuntos.length; i++){
            let detalles = {
                "id": {
                    "cecdIdexamenesconjuntos": idExamenesConjuntos,
                    "cecdIdexamen": examenesConjuntos[i].dataValue.ceIdexamen
                }
            };
            examenesConjuntosDetalle.push(detalles);
        }
        
    }
    
    let idTablaReferencias = $("#txtIdTablaReferencia").val();
    let tablaReferencias = [];
    
    //if (!$("#tbodyTablaRef").is(':empty')){
    //if (colCount === 1)
        $("#tablaReferencias tr").each(function (){
            $(this).find('td').each (function() {
                if ($(this).text() !== ""){
                    let ctr = {
                        "id" : {
                            "ctreCampo": $(this).attr('id'),
                            "ctreIdtablareferenciaexamen": idTablaReferencias
                        },
                        "ctreValor": $(this).text()
                    };
                    tablaReferencias.push(ctr);
                }
            });
        });
    //}
    let examenTablaAnexa = null;
            
    if (tablaReferencias.length > 0) {
        examenTablaAnexa = {
            "cetaIdexamentabla": idTablaReferencias,
            "cetaAnchotabla": colCount,
            "cetaLargotabla": rowCount,
            "cetaTitulotabla": $("#tituloTablaAnexa").val(),
            "cetaSubtitulo": $("#subtituloTablaAnexa").val()
        };
    }
    
    let examenesDTO = new Object();
    examenesDTO.cfgExamenes = {
        "ceIdexamen": $("#txtIdExamen").val(),
        "ceCodigoexamen": $("#txtCodigo").val().toUpperCase(),
        "ceDescripcion": $("#txtDescripcion").val().toUpperCase(),
        "ceAbreviado": $("#txtNombre").val().toUpperCase(),
        "ceAbreviado2": $("#txtEtiqueta").val().toUpperCase(),
        "ceActivo": $("#checkBoxLever").is(":checked")?'S':'N',
        "ceImprimirseparado": $("#chkImprimirSeparado").is(":checked")?'S':'N',
        "ceImprimirdescripcion": $("#chkImprimirDescripcion").is(":checked")?'S':'N',
        "ceImprimirporseccion": $("#chkImprimirPorSeccion").is(":checked")?'S':'N',
        "ceImprimirtabla": $("#chkImprimirTabla").is(":checked")?'S':'N',
        "ceMultiformato": $("#chkMultiformato").is(":checked")?'S':'N',
        //"ceTipoexamensort": 1,
        "ceIdtablareferenciaexamen": null,//$("#").val(),
        "ceSeleccionable": null,//$("#").val(),
        "ceHost": $("#txtHost1").val(),
        "ceHostmicro": $("#txtHostMicro").val(),
        "ceHost2": $("#txtHost2").val(),
        "ceHost3": $("#txtHost3").val(),
        "ceCodigofonasa": $("#txtCodigoFonasa").val(),
        "ceSortseccion": $("#txtSortSeccion").val() || 1,
        "ceEstadisticas": $("#chkEstadistica").is(":checked")?'S':'N',
        "ceNota": $("#txtNota").val(),
        "ceEspesquisa": $("#selectPesquisa").val()>0?'S':'N',
        "cePesquisaobligatoria": $("#chkPesquisaObligatoria").is(":checked")?'S':'N',
        "ceDatosmuestra": $("#checkBoxCambioMuestra").is(":checked")?'S':'N',
        "ceTiempoentreganormal": transformarTiempoaMins($("#txtTiempoEntregaNormal").val(), $("#selectTiempoEntregaNormal").val()),
        "ceTiempoentregahospitalizado": transformarTiempoaMins($("#txtTiempoEntregaHospitalizado").val(), $("#selectTiempoEntregaHospitalizado").val()),
        "ceTiempoentregaurgente": transformarTiempoaMins($("#txtTiempoEntregaUrgencia").val(), $("#selectTiempoEntregaUrgencia").val()),
        "ceDiaprocesolunes": $("#chkDiaProcesoLunes").is(":checked")?'S':'N',
        "ceDiaprocesomartes": $("#chkDiaProcesoMartes").is(":checked")?'S':'N',
        "ceDiaprocesomiercoles": $("#chkDiaProcesoMiercoles").is(":checked")?'S':'N',
        "ceDiaprocesojueves": $("#chkDiaProcesoJueves").is(":checked")?'S':'N',
        "ceDiaprocesoviernes": $("#chkDiaProcesoViernes").is(":checked")?'S':'N',
        "ceDiaprocesosabado": $("#chkDiaProcesoSabado").is(":checked")?'S':'N',
        "ceDiaprocesodomingo": $("#chkDiaProcesoDomingo").is(":checked")?'S':'N',
        "ceVolumenmuestraadulto": $("#txtVolumenAdulto").val() || 0,
        "ceVolumenmuestrapediatrica": $("#txtVolumenPediatrica").val() || 0,
        "ceEstabilidadambiental": $("#selectEstabilidadAmbiental").val() == "No aplica" ? "No aplica" : $("#txtEstabilidadAmbiental").val() != "" ? $("#txtEstabilidadAmbiental").val().toUpperCase().concat(' ').concat( $("#selectEstabilidadAmbiental").val()) : null,
        "ceEstabilidadrefrigerado": $("#selectEstabilidadRefrigerado").val() == "No aplica" ? "No aplica" : ($("#txtEstabilidadRefrigerado").val() != "" ? $("#txtEstabilidadRefrigerado").val().toUpperCase().concat(' ').concat( $("#selectEstabilidadRefrigerado").val()) : null),
        "ceEstabilidadcongelado": $("#selectEstabilidadCongelado").val() == "No aplica" ? "No aplica" : $("#txtEstabilidadCongelado").val() != "" ? $("#txtEstabilidadCongelado").val().toUpperCase().concat(' ').concat( $("#selectEstabilidadCongelado").val()) : null,
        "ceContablecantidad": $("#txtContableCantidad").val() || 1,
        "ceNumerodeetiquetas": $("#txtEtiquetasCantidad").val() || 1,
        "ceEsexamen": $("#chkSoloPrestacion").is(":checked")?'N':'S',
        "ceTienetestopcionales": $("#chkTieneTestOpcionales").is(":checked")?'S':'N',
        "ceEsurgente": $("#chkExamenUrgente").is(":checked")?'S':'N',
        "ceEscultivo": $("#chkEsCultivo").is(":checked")?'S':'N',
        "ceEsconfidencial": $("#chkExamenConfidencial").is(":checked")?'S':'N',
        "ceEsindicador": $("#chkIndicador").is(":checked")?'S':'N',
        "ceNombreweb": $("#txtNombreWeb").val().toUpperCase(),
        "ceDisponibleweb": $("#chkDisponibleWeb").is(":checked")?'S':'N',
        "ceDiagnosticoobligatorio": $("#chkDiagnosticoObligatorio").is(":checked")?'S':'N',
        "ceHojatrabajo": $("#chkHojaTrabajo").is(":checked")?'S':'N',
        "ceEscurvatolerancia": $("#chkExamenTipoCurva").is(":checked")?'S':'N',
        "cePlanillahistorica": $("#chkPlanillaHistorica").is(":checked")?'S':'N',
        "ceDiasdevalidez": $("#txtDiasValidezAmbulatorio").val() || 0,
        "ceDiasvalidezhospitalizado": $("#txtDiasValidezHospitalizado").val() || 0,
        "ceDiasvalidezambulatorioblq": $("#chkObligadoAmbulatorio").is(":checked")?'S':'N',
        "ceDiasvalidezhospitalizadoblq": $("#chkObligadoHospitalizado").is(":checked")?'S':'N',
        "ceEsexamenmultiseccion": multiSeccion,
        "ceAutovalidar": $("#chkAutovalidacion").is(":checked")?'S':'N',
        "ceCompartemuestra": $("#chkCompartirMuestra").is(":checked")?'S':'N',
        "ceTienegrupomuestra": tieneMuestrasAdicionales,
        "ceCellcounter": $("#chkCellCounter").is(":checked")?'S':'N'
        //"cfgExamenesindicacionestm": null
    };
    examenesDTO.cfgMuestras = {
        "cmueIdtipomuestra": muestraPorDefecto
    };
    console.log("metodo por defecto ----"+ metodoPorDefecto)
    examenesDTO.cfgMetodos = metodoPorDefecto == 0 ? null : {
        "cmetIdmetodo": metodoPorDefecto
    };
    /*examenesDTO.cfgDerivadores = {
        "cderivIdderivador": $("#selectDerivador").val()
    };*/
    examenesDTO.cfgSecciones = {
        "csecIdseccion": seccionPorDefecto
    };
    examenesDTO.cfgPesquisas = {
        "cpeIdpesquisa": $("#selectPesquisa").val()
    };
    examenesDTO.ldvTiposexamenes = $("#chkMicrobiologia").is(":checked")? {
        "ldvteIdtipoexamen": 4
    } : null; 
    let ldvIndicacionesexamenes = $("#selectIndicacionesPaciente").val() == 0 ? null : {
        "ldvieIdindicacionesexamen": $("#selectIndicacionesPaciente").val()
    };
    examenesDTO.ldvIndicacionesexamenes = ldvIndicacionesexamenes;
    let ldvFormatos = $("#selectFormatoImpresion").val() == 0 ? null : {
        "ldvfIdformato": $("#selectFormatoImpresion").val()
    };
    examenesDTO.ldvFormatos = ldvFormatos;
    let ldvGruposexamenes = $("#selectGrupoExamenes").val() == 0 ? null : {
        "ldvgeIdgrupoexamen": $("#selectGrupoExamenes").val()
    };
    examenesDTO.ldvGruposexamenes = ldvGruposexamenes;
    // let ldvLoinc = $("#selectLoinc").val() === "" ? "" : {
    //     "ldvlCodigoloinc": $("#selectLoinc").val()
    // };
    // examenesDTO.ldvLoinc = ldvLoinc;
	if($("#selectGrupoFonasa").val() === "0"){
	    examenesDTO.cfgGruposexamenesfonasa = {
	        "cgefIdgrupoexamenfonasa": null
	    };
	}else{
	    examenesDTO.cfgGruposexamenesfonasa = {
	        "cgefIdgrupoexamenfonasa": $("#selectGrupoFonasa").val()
	    };
    }
    examenesDTO.idDerivador = $("#selectDerivador").val();
    examenesDTO.listTestsExamen = examenesTests;
    examenesDTO.listGruposmuestrasexa = grupoMuestras;
    examenesDTO.listExamenesIndicacionesTM = indicacionesTM;
    examenesDTO.examenesconjuntodetalles = examenesConjuntosDetalle;
    examenesDTO.examenestablasanexas = examenTablaAnexa;
    examenesDTO.tablaReferencias = tablaReferencias;
    console.log(examenesDTO);
    return examenesDTO;
}

$("#btnAgregarExamen").click(function (){
    if (validaDatos()){
        let dataExamen = createArrayData();
        $.ajax({
            type: "POST",
            url: "api/examen/insert",
            data: JSON.stringify(dataExamen),
            async: false,
            contentType: "application/json",
            success: function(data){
				if(data.status == 500){
					handlerMessageError(`Sucedio un error: ${data.message}`);
				}
				if(data.status == 200){
	                handlerMessageOk("Se registró el examen satisfactoriamente");
	                $(".formExamen").prop("disabled", true);
	                limpiarFormulario();
				}

            },
            error: function (msg) {
                console.log(msg);
                handlerMessageError("Error al guardar: " + msg );
            }
            
        });
    }
});

$("#btnUpdateExamen").click(function (){
	$('#btnLimpiar').removeClass('disabled');
    if (validaDatos()){
        let dataExamen = createArrayData();
        dataExamen.cfgExamenes.ceVolumenmuestraadulto = parseFloat($('#txtVolumenAdulto').val().replace(',', '.'));
        dataExamen.cfgExamenes.ceVolumenmuestrapediatrica = parseFloat($('#txtVolumenPediatrica').val().replace(',', '.'));
        $.ajax({
            type: "PUT",
            url: "api/examen/full/update",
            data: JSON.stringify(dataExamen),
            async: false,
            contentType: "application/json",
            success: function(data){
				if(data.status == 200)
               		 handlerMessageOk("Se actualizó el examen satisfactoriamente");
				if(data.status == 500)
               		 handlerMessageError("Hubo un error al actualizar");
                $(".formExamen").prop("disabled", true);
               //limpiarFormulario();
               $('#btnCancelExamen').click();
            },
            error: function (msg) {
                //console.log(msg);
                handlerMessageError("Error al actualizar: " + msg );
            }
            
        });
    }
});