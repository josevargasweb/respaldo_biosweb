var muestraPorDefecto = 0;
var seccionPorDefecto = 0;
var metodoPorDefecto = 0;

$(document).ready(function () {
    $(".ocultar").hide();
    cargarSecciones(secciones());
    cargarDerivadores(derivadores());
    cargarIndicacionesExamenes(indicacionesExamenes());
    cargarPesquisas(pesquisas());
    // cargarLoincs(loincs());
    cargarGruposFonasa(gruposFonasa());
    cargarFormatos(formatos());
    cargarGruposExamenes(gruposExamenes());
    $('.selectpicker').selectpicker('refresh');
    desactivarEdit();
    $( "#btnAgregarExamen" ).prop( "disabled", true );
});
new ImaskInputNumber('txtEstabilidadAmbiental', false, 0, 9999);
new ImaskInputNumber('txtEstabilidadRefrigerado', false, 0, 9999);
new ImaskInputNumber('txtEstabilidadCongelado', false, 0, 9999);
new ImaskInputNumber('txtVolumenAdulto', true, 0, 9999,2);
new ImaskInputNumber('txtVolumenPediatrica', true, 0, 9999,2);

new ImaskInputNumber('txtTiempoEntregaNormal', false, 0, 99);
new ImaskInputNumber('txtTiempoEntregaHospitalizado', false, 0, 99);
new ImaskInputNumber('txtTiempoEntregaUrgencia', false, 0, 99);

new ImaskInputNumber('txtDiasValidezAmbulatorio', false, 0, 99);
new ImaskInputNumber('txtDiasValidezHospitalizado', false, 0, 99);

new ImaskInputNumber('txtEtiquetasCantidad', false, 0, 99);
new ImaskInputNumber('txtContableCantidad', false, 0, 99);

new ImaskInputNumber('txtSortSeccion', false, 0, 99);

$("#nuevoExamen").click(function () {
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
    $("#chkMostrarActivos").prop("checked", false);
    $("#tbodyFiltro").empty();
    $("#txtCodigoFiltro").prop("disabled", false);
    $("#txtNombreFiltro").prop("disabled", false);
    $("#selectSeccionFiltro").prop("disabled", false);
    $("#lblTotalFiltro").hide();
});

$("#txtNombreFiltro, #txtCodigoFiltro").keyup(function(input) {
    if (input.which === 13) {
      $("#btnBuscarFiltro").click();
    }
  });

$("#btnBuscarFiltro").click(function () {
	
    var codigoFiltro = $("#txtCodigoFiltro").val();
    var descFiltro = $("#txtNombreFiltro").val();
    let idSeccion = $("#selectSeccionFiltro").val();
    let activo = $("#chkMostrarActivos").is(":checked") ? "S" : "N";
    if (codigoFiltro.length < 2 && descFiltro.length < 2 && idSeccion == 0) {
        notificacionDosCaracteres();
    } else {
        filtrarExamen(codigoFiltro, descFiltro, idSeccion, activo);
    }
});

$("#selectPesquisa").change(function (){
    if ($("#selectPesquisa").val() > 0) {
        $("#chkPesquisaObligatoria").prop("disabled", false);
    } else {
        $("#chkPesquisaObligatoria").prop("disabled", true);
        $("#chkPesquisaObligatoria").prop("checked", false);
    }
});

function filtrarExamen(codigo, nombre, idSeccion, activo) {
    let filtros = JSON.stringify({
        codigo: codigo,
        nombre: nombre,
        idSeccion: idSeccion,
        activo: activo
    });
    //console.log(filtros)
    $.ajax({
        method: "POST",
        url: "api/examen/filtro",
        data: filtros,
        headers: {
          'Content-Type': 'application/json'
        },
        dataType: "json",
        success: function (mensaje) {
            var cont = 0;
            //var dato = JSON.parse(mensaje);
            let dato = mensaje.dato;
            $("#tbodyFiltro").empty();
            $("#lblErrorFiltro").hide();
            if (dato.length > 0) {
                dato.forEach(function (examen) {
                    cont++;
                    $("#tbodyFiltro").append(`<tr class="pointer" onclick="buscarCodigo(this)" data-id-examen="${examen.ceIdexamen}">
                                                <td class="row mx-auto"><b>${cont}</b></td>
                                                <td class='codigoTest'>${examen.ceCodigoexamen}</td>
                                                <td class=''>${examen.ceAbreviado}</td>
                                              </tr>`);
                });
                $("#lblTotalFiltro").show();
            } else {
                $("#lblTotalFiltro").hide();
                $("#lblErrorFiltro").show();
            }
            $("#totalFiltro").text(cont);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

function buscarCodigo(a) {
    var $row = $(a).closest("tr"); // Find the row
    seleccionarFila(a,'#tableFiltro');
    // var id = $row.find(".idExamen").text(); // Find the text
    let id = $row.attr("data-id-examen");
    buscarExamenPorId(id);
    $('.selectpicker').selectpicker('refresh');
}

function buscarExamenPorId(idExamen,collapse = true){
    $.ajax({
        type: "get",
        url: getctx + "/api/examen/" + idExamen,
        dataType: 'json',
        success: function (json) {
            if(collapse){
                $("#collapseHeader").collapse('hide');
            }
             limpiarMuestras();
            activarEdit();
            let data = json.dato;
            /** Datos Básicos **/
            $("#txtIdExamen").val(data.cfgExamenes.ceIdexamen);
            $("#txtCodigo").val(data.cfgExamenes.ceCodigoexamen);
            $("#txtNombre").val(data.cfgExamenes.ceAbreviado);
            nombreTitulo($("#txtNombre").val());
            $("#txtDescripcion").val(data.cfgExamenes.ceDescripcion);
            $("#txtEtiqueta").val(data.cfgExamenes.ceAbreviado2);
            $("#txtCodigoFonasa").val(data.cfgExamenes.ceCodigofonasa);
            $("#selectDerivador").val(data.cfgDerivadores.cderivIdderivador);
            $("#chkSoloPrestacion").prop("checked", data.cfgExamenes.ceEsexamen === "N" ? true : false);
            $("#chkEsCultivo").prop("checked", data.cfgExamenes.ceEscultivo === "S" ? true : false);
            $("#chkMicrobiologia").prop("checked", (data.ldvTiposexamenes !== null && data.ldvTiposexamenes.ldvteIdtipoexamen === 4) ? true : false);
            /** Activo **/
            $("#checkBoxLever").prop("checked", data.cfgExamenes.ceActivo === "S" ? true : false);
            data.cfgExamenes.ceActivo === "S" ? $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
            $("#lblEstado").text(data.cfgExamenes.ceActivo === "S" ? 'Activo' : 'Inactivo');
            /** Test **/
            console.log(data)
            limpiarTest();
            data.listTestsExamen.forEach(function (itemex){
	console.log(itemex)
                let item = vst.getSelectedItemById(itemex.cfgTest.ctIdtest);
                vtt.addItem(item);
                vst.removeItemById(item.id);
            });
            /** Grupos Muestras **/
            $("#checkBoxCambioMuestra").prop("checked", data.cfgExamenes.ceDatosmuestra === "S" ? true : false);
            $("#btnModalCambioMuestras").prop("disabled", data.cfgExamenes.ceDatosmuestra === "S" ? false : true);

            /** Datos de la Muestra **/
            let selectEstabilidadAmbiental = "";
            if(data.cfgExamenes.ceEstabilidadambiental != null){
                selectEstabilidadAmbiental = data.cfgExamenes.ceEstabilidadambiental.match(/\b(días|minutos|horas|No aplica)\b/g);
            }
            
            let selectEstabilidadRefrigerado = "";
            if(data.cfgExamenes.ceEstabilidadrefrigerado != null){
                selectEstabilidadRefrigerado = data.cfgExamenes.ceEstabilidadrefrigerado.match(/\b(días|minutos|horas|No aplica)\b/g);
            }
            
            let selectEstabilidadCongelado = "";
            if(data.cfgExamenes.ceEstabilidadcongelado != null){
                selectEstabilidadCongelado = data.cfgExamenes.ceEstabilidadcongelado.match(/\b(días|minutos|horas|No aplica)\b/g);
            }

            console.log("selectEstabilidadAmbiental",selectEstabilidadAmbiental)
            console.log("inputEstabilidadRefrigerado",data.cfgExamenes.ceEstabilidadrefrigerado)
            console.log("selectEstabilidadRefrigerado",selectEstabilidadRefrigerado)
            console.log("selectEstabilidadCongelado",data.cfgExamenes.ceEstabilidadcongelado)
            console.log("selectEstabilidadCongelado",selectEstabilidadCongelado)

            $("#txtEstabilidadAmbiental").val(data.cfgExamenes.ceEstabilidadambiental != null ? data.cfgExamenes.ceEstabilidadambiental.replace(/\b(días|minutos|horas)\b/g, '').trim() : '');
            $("#selectEstabilidadAmbiental").val(selectEstabilidadAmbiental != "" && selectEstabilidadAmbiental != null ? selectEstabilidadAmbiental[0] : "días");
            $("#txtEstabilidadRefrigerado").val(data.cfgExamenes.ceEstabilidadrefrigerado != null ? data.cfgExamenes.ceEstabilidadrefrigerado.replace(/\b(días|minutos|horas)\b/g, '').trim() : '');
            $("#selectEstabilidadRefrigerado").val(selectEstabilidadRefrigerado != "" &&  selectEstabilidadRefrigerado != null ? selectEstabilidadRefrigerado[0] : "días");
            $("#txtEstabilidadCongelado").val(data.cfgExamenes.ceEstabilidadcongelado!= null ? data.cfgExamenes.ceEstabilidadcongelado.replace(/\b(días|minutos|horas)\b/g, '').trim() : '');
            $("#selectEstabilidadCongelado").val(selectEstabilidadCongelado != "" && selectEstabilidadCongelado != null ? selectEstabilidadCongelado[0] : "días");
            $("#txtVolumenAdulto").val(data.cfgExamenes.ceVolumenmuestraadulto);
            $("#txtVolumenPediatrica").val(data.cfgExamenes.ceVolumenmuestrapediatrica);
            $("#txtNroEtiquetas").val(data.cfgExamenes.ceNumerodeetiquetas);
            /** Indicaciones toma de muestra **/
            $("#tbodyIndicaciones").empty();
            //let options = "";
            data.listExamenesIndicacionesTM.forEach((element) => {
                //options += `<option value="${element.ldvIndicacionestm.ldvitmIdindicacionestm}">${element.ldvIndicacionestm.ldvitmDescripcionindicacion}</option>`;
                cargarIndicacionesTM(indicacionesTM(),element.ldvIndicacionestm.ldvitmIdindicacionestm);
            });
            /*if (data.listExamenesIndicacionesTM.length > 0){
                $("#tbodyIndicaciones")
                        .append(`<tr>
                                    <td class="col-8"><select class="selectIndicacionesTM formExamen form-control">${options}</select></td>
                                    <td class="eliminar col-4">
                                        <button type="button" class="btn p-2" title="Eliminar indicación" onclick="eliminarIndicacion(this)">
                                            <i class="far fa-trash-alt"></i><span class="nav-text"></span>
                                        </button>
                                    </td>
                                </tr>`);
            }*/
            /** Datos de Proceso **/
            $("#chkDiaProcesoLunes").prop("checked", data.cfgExamenes.ceDiaprocesolunes === "S" ? true : false);
            $("#chkDiaProcesoMartes").prop("checked", data.cfgExamenes.ceDiaprocesomartes === "S" ? true : false);
            $("#chkDiaProcesoMiercoles").prop("checked", data.cfgExamenes.ceDiaprocesomiercoles === "S" ? true : false);
            $("#chkDiaProcesoJueves").prop("checked", data.cfgExamenes.ceDiaprocesojueves === "S" ? true : false);
            $("#chkDiaProcesoViernes").prop("checked", data.cfgExamenes.ceDiaprocesoviernes === "S" ? true : false);
            $("#chkDiaProcesoSabado").prop("checked", data.cfgExamenes.ceDiaprocesosabado === "S" ? true : false);
            $("#chkDiaProcesoDomingo").prop("checked", data.cfgExamenes.ceDiaprocesodomingo === "S" ? true : false);
            /** Tiempo de entrega (pendiente) **/
            $("#txtTiempoEntregaNormal").val( data.cfgExamenes.ceTiempoentreganormal != null && data.cfgExamenes.ceTiempoentreganormal != "" ?  transformarMinsaTiempo(data.cfgExamenes.ceTiempoentreganormal) : 0);
            $("#txtTiempoEntregaHospitalizado").val( data.cfgExamenes.ceTiempoentreganormal != null && data.cfgExamenes.ceTiempoentreganormal != "" ?  transformarMinsaTiempo(data.cfgExamenes.ceTiempoentregahospitalizado) : 0);
            $("#txtTiempoEntregaUrgencia").val( data.cfgExamenes.ceTiempoentregaurgente != null && data.cfgExamenes.ceTiempoentregaurgente != "" ? transformarMinsaTiempo(data.cfgExamenes.ceTiempoentregaurgente) : 0);

            $("#selectTiempoEntregaNormal").val( data.cfgExamenes.ceTiempoentreganormal != null && data.cfgExamenes.ceTiempoentreganormal != "" ?  transformarMinsaTiempoTexto(data.cfgExamenes.ceTiempoentreganormal) : "d");
            $("#selectTiempoEntregaHospitalizado").val( data.cfgExamenes.ceTiempoentreganormal != null && data.cfgExamenes.ceTiempoentreganormal != "" ?  transformarMinsaTiempoTexto(data.cfgExamenes.ceTiempoentregahospitalizado) : "d");
            $("#selectTiempoEntregaUrgencia").val( data.cfgExamenes.ceTiempoentregaurgente != null && data.cfgExamenes.ceTiempoentregaurgente != "" ? transformarMinsaTiempoTexto(data.cfgExamenes.ceTiempoentregaurgente) : "d");
            $("#txtDiasValidezAmbulatorio").val(data.cfgExamenes.ceDiasdevalidez);
            
            $("#chkObligadoAmbulatorio").prop("checked", data.cfgExamenes.ceDiasvalidezambulatorioblq === "S" ? true : false);
            $("#txtDiasValidezHospitalizado").val(data.cfgExamenes.ceDiasvalidezhospitalizado);
            $("#chkObligadoHospitalizado").prop("checked", data.cfgExamenes.ceDiasvalidezhospitalizadoblq === "S" ? true : false);
            $("#selectIndicacionesPaciente").val(data.ldvIndicacionesexamenes !== null ? data.ldvIndicacionesexamenes.ldvieIdindicacionesexamen : 0);
            $("#txtContableCantidad").val(data.cfgExamenes.ceContablecantidad);
            $("#txtEtiquetasCantidad").val(data.cfgExamenes.ceNumerodeetiquetas != null ? data.cfgExamenes.ceNumerodeetiquetas : 0 );
            /** Datos Estadísticos **/
            $("#chkEstadistica").prop("checked", data.cfgExamenes.ceEstadisticas === "S" ? true : false);
            $("#chkIndicador").prop("checked", data.cfgExamenes.ceEsindicador === "S" ? true : false);
            $("#chkEstadistica").prop("checked", data.cfgExamenes.ceEstadisticas === "S" ? true : false);
            $("#chkHojaTrabajo").prop("checked", data.cfgExamenes.ceHojatrabajo === "S" ? true : false);
            $("#chkPlanillaHistorica").prop("checked", data.cfgExamenes.cePlanillahistorica === "S" ? true : false);
            $("#chkPesquisaObligatoria").prop("checked", data.cfgExamenes.cePesquisaobligatoria === "S" ? true : false);
            $("#selectPesquisa").val(data.cfgPesquisas.cpeIdpesquisa);
            $("#selectGrupoFonasa").val(data.cfgGruposexamenesfonasa !== null ? data.cfgGruposexamenesfonasa.cgefIdgrupoexamenfonasa : 0);
            $("#txtSortSeccion").val(data.cfgExamenes.ceSortseccion);
            // $("#selectLoinc").val(data.ldvLoinc !== null ? data.ldvLoinc.ldvlCodigoloinc : "");
            /** Interoperabilidad (Host) **/
            $("#txtHost1").val(data.cfgExamenes.ceHost);
            $("#txtHost2").val(data.cfgExamenes.ceHost2);
            $("#txtHost3").val(data.cfgExamenes.ceHost3);
            $("#txtHostMicro").val(data.cfgExamenes.ceHostmicro);
            /** Datos Extra **/
            $("#chkExamenUrgente").prop("checked", data.cfgExamenes.ceEsurgente === "S" ? true : false);
            $("#chkExamenConfidencial").prop("checked", data.cfgExamenes.ceEsconfidencial === "S" ? true : false);
            $("#chkAutovalidacion").prop("checked", data.cfgExamenes.ceAutovalidar === "S" ? true : false);
            $("#chkDiagnosticoObligatorio").prop("checked", data.cfgExamenes.ceDiagnosticoobligatorio === "S" ? true : false);
            $("#chkCompartirMuestra").prop("checked", data.cfgExamenes.ceCompartemuestra === "S" ? true : false);
            $("#chkExamenTipoCurva").prop("checked", data.cfgExamenes.ceEscurvatolerancia === "S" ? true : false);
            $("#chkTieneTestOpcionales").prop("checked", data.cfgExamenes.ceTienetestopcionales === "S" ? true : false);
            $("#chkCellCounter").prop("checked", data.cfgExamenes.ceCellcounter === "S" ? true : false);
            $("#chkDisponibleWeb").prop("checked", data.cfgExamenes.ceDisponibleweb === "S" ? true : false);
            $("#txtNombreWeb").val(data.cfgExamenes.ceNombreweb);
            $("#txtNota").val(data.cfgExamenes.ceNota);
            /** Exámenes conjuntos **/
            limpiarExamenes();
            $("#txtIdExamenesConjuntos").val(data.cfgExamenesconjuntos!==null ? data.cfgExamenesconjuntos.cecIdexamenesconjuntos : "");
            if (data.examenesconjuntodetalles!==null){
                
                data.examenesconjuntodetalles.forEach(function (examen){
                    let iteme = vse.getSelectedItemById(examen.id.cecdIdexamen);
                    vte.addItem(iteme);
                    vse.removeItemById(iteme.id);
                });
            }
            /** Tabla de referencia **/
            if (data.cfgExamenes.ceIdtablareferenciaexamen!==null){
                $("#txtIdTablaReferencia").val(data.cfgExamenes.ceIdtablareferenciaexamen);
            }
            /*$("#theadTablaRef").html(`<tr>
                                <td id="A0">columna 1<input id="A0" type='text' name='valorReferencia' style='visibility: hidden' value='' /></td>
                            </tr>`);*/
            //if (data.tablaReferencias !== null) {
                $("#tbodyTablaRef").html(`<tr id="1">
                                <th style="background-color: #ddd">1 </th>
                                <td id="A1"><input id="A1" type='text' name='valorReferencia' style='visibility: hidden' value='' /></td>
                            </tr>`);
                let columnas = 0;
                let filas = 0;
                if (data.examenestablasanexas !== null){
                    $("#tituloTablaAnexa").val(data.examenestablasanexas.cetaTitulotabla);
                    $("#subtituloTablaAnexa").val(data.examenestablasanexas.cetaSubtitulo);
                    columnas = data.examenestablasanexas.cetaAnchotabla;
                    filas = data.examenestablasanexas.cetaLargotabla;
                }

                //añadir columnas al header cuando hay más de una
                for (let i=1; i<columnas; i++){
                    let columnHeader = getColumnValue(i);
                        $("#theadTablaRef tr").append(`<th id="${columnHeader}" onmouseover="showBtnDel(this)" onmouseout="hideBtnDel(this)" style="background-color: #ddd"><label id="lbl${columnHeader}">${columnHeader}</label>
                                                        <button type="button" class="btn btn-sm btn-danger btnDelete" style="display:none;" onclick="eliminarColumna('${columnHeader}')"><i class="far fa-trash-alt"></i></button></th>`);
                }

                let htmlRef = "";
                for (let i=1; i<=filas; i++){
                    if (i==1){
                        $("#tbodyTablaRef tr#1").remove();
                        htmlRef += `<tr id="${i}">
                                        <th id="${i}" style="background-color: #ddd"><label>${i}</label></th>`;
                    } else {
                        htmlRef += `<tr id="${i}">
                                        <th id="${i}" onmouseover='showBtnDel(this)' onmouseout='hideBtnDel(this)' style="background-color: #ddd"><label>${i}</label>
                                            <button type="button" class="btn btn-sm btn-danger btnDelete" style="display:none;" onclick="eliminarFila(${i})"><i class="far fa-trash-alt"></i></button>
                                        </th>`;
                    }
                    for (let j=0; j<columnas; j++){

                        let id = getColumnValue(j)+i;
                        htmlRef += `<td id="${id}"></td>`;
                    }
                    htmlRef += `</tr>`;
                    /*if (i===0){
                        $("#theadTablaRef").html(htmlRef);
                        htmlRef = "";
                    }*/
                }
                rowCount = filas>0 ? filas : 1;
                colCount = columnas>0 ? columnas : 1;
                $("#tbodyTablaRef").append(htmlRef);
            if(data.tablaReferencias!==null){
                data.tablaReferencias.forEach(function (tRef){
                    let campo = tRef.id.ctreCampo;
                    $("#"+campo).html(`${tRef.ctreValor}<input id="${campo}" type='text' name='valorReferencia' style='visibility: hidden' value="${tRef.ctreValor}">`);
                });
            }
            /** Datos Impresión **/
            $("#chkImprimirSeparado").prop("checked", data.cfgExamenes.ceImprimirseparado === "S" ? true : false);
            $("#chkImprimirDescripcion").prop("checked", data.cfgExamenes.ceImprimirdescripcion === "S" ? true : false);
            $("#chkImprimirPorSeccion").prop("checked", data.cfgExamenes.ceImprimirporseccion === "S" ? true : false);
            $("#chkImprimirTabla").prop("checked", data.cfgExamenes.ceImprimirtabla === "S" ? true : false);
            $("#chkMultiformato").prop("checked", data.cfgExamenes.ceMultiformato === "S" ? true : false);
            $("#selectFormatoImpresion").val(data.ldvFormatos !== null ? data.ldvFormatos.ldvfIdformato : 0);
            $("#selectGrupoExamenes").val(data.ldvGruposexamenes !== null ? data.ldvGruposexamenes.ldvgeIdgrupoexamen : 0);
            /** Formulario **/
            $("#btnAgregarExamen").prop("disabled", true);
            $(".formExamen").prop("disabled",true);
            $(".switch-content").addClass("disabled");
                   	     /*  		*/
            $("#muestraExamen").val(data.cfgMuestras.cmueDescripcionabreviada);
            muestraPorDefecto = data.cfgMuestras.cmueIdtipomuestra;
                       
            if (data.listGruposmuestrasexa!==null){
                data.listGruposmuestrasexa.forEach(function (muestra){
                    let itemm = vsm.getSelectedItemById(muestra.id.cgmeIdtipomuestra);
                    vtm.addItem(itemm);
                    vsm.removeItemById(itemm.id);
                });
            }
            $('.selectpicker').selectpicker('refresh');
            
        },
        error: function (jqXHR, textStatus, errorThrown) {
            
        }
    });
}

$("#txtNombre").keyup(function () {
    nombreTitulo($("#txtNombre").val());
    $("#exampleModalLabel5").text($("#txtNombre").val());
});

function nombreTitulo(nombre) {
    $("#tituloRegistro").text("Examen: " + nombre);
    $("#titleModalTablaReferencias").text("Tabla de referencias: "+ nombre);
}

function notificacionDosCaracteres() {
    handlerMessageError("Se requiere al menos 2 caracteres para buscar");
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
$("#circuloAgregarExamen").hover(
        function () {
            $("#iAgregarPaciente").removeClass("text-primary");
            $("#iAgregarPaciente").addClass("text-white");
        },
        function () {
            $("#iAgregarPaciente").removeClass("text-white");
            $("#iAgregarPaciente").addClass("text-primary");
        }
);
$("#circuloEditarExamen").hover(        
        function () {
        console.log('esta activado',localStorage.getItem("botonEditar"))
            if (localStorage.getItem("botonEditar") === "activo") {
                $("#circuloEditarExamen").addClass("bg-hover-blue");
                $("#iEditExamen").removeClass("text-blue", "text-blue");
                $("#iEditExamen").addClass("text-blue");
            }
        },
        function () {
            if (localStorage.getItem("botonEditar") === "inactivo") {
                $("#circuloEditarExamen").removeClass("bg-hover-blue");
                $("#iEditExamen").removeClass("text-white","text-blue");
                $("#iEditExamen").addClass("text-dark-50");
            }
        }

);

$("#buscarExamen").click(function() {
    $("#collapseHeader").collapse('show');
    $('html, body').animate({
        scrollTop: 0
    }, 700);
});

$("#btnEditar").click(function() {
    if (
        localStorage.getItem("botonEditar") === "activo"
    ) {
        $(".formExamen").prop("disabled",false);
        $(".switch-content").removeClass("disabled");
        $("#divAgregarBtn").hide();
        $("#divActualizarBtn").css("display", "flex");
        desactivarEdit();
        removePropOApp($("#selectEstabilidadAmbiental").val(),"#txtEstabilidadAmbiental");
        removePropOApp($("#selectEstabilidadRefrigerado").val(),"#txtEstabilidadRefrigerado");
        removePropOApp($("#selectEstabilidadCongelado").val(),"#txtEstabilidadCongelado");
        $('#btnLimpiar').addClass('disabled');
        $('.selectpicker').selectpicker('refresh');
        $('.selectpicker').selectpicker('refresh');
    }
});

function activarEdit() {
    $("#iEditExamen").removeClass("text-dark-50");
    $("#iEditExamen").addClass("text-blue");
    localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
    $("#iEditExamen").removeClass("text-blue");
    $("#iEditExamen").addClass("text-dark-50");
    localStorage.setItem("botonEditar", "inactivo");
}

$("#btnLimpiar").click(function() {
	$("#collapseHeader").collapse('show');
    limpiarFormulario();
    $('.selectpicker').selectpicker('refresh');
});

$("#btnCancelExamen").click(function() {
    $(".formExamen").prop("disabled", true);
    $(".switch-content").addClass("disabled");
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $('#btnLimpiar').removeClass('disabled');
    activarEdit();
    $('.selectpicker').selectpicker('refresh');
});

$("#checkBoxCambioMuestra").click(function (){
    $("#btnModalCambioMuestras").prop("disabled", $(this).is(":checked") ? false : true);
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

$("#checkBoxLever").click(function () {
    nombreTitulo($("#txtNombre").val());
    if ($(this).is(":checked")) {
        $("#tituloRegistro").css("color", "black");
        $("#lblEstado").text("Activo");
    }
    if (!($(this).is(":checked"))) {
        $("#tituloRegistro").css("color", "red");
        $("#lblEstado").text("Inactivo");
    }
});

function limpiarFormulario(){
    $("#txtIdExamen").val("");
    $("#txtCodigo").val("");
    $("#txtNombre").val("");
    nombreTitulo("");
    $("#tituloRegistro").css("color", "black");
    $("#txtDescripcion").val("");
    $("#txtEtiqueta").val("");
    $("#txtCodigoFonasa").val("");
    $("#selectDerivador").val(0);
    $("#chkSoloPrestacion").prop("checked", false);
    $("#chkMicrobiologia").prop("checked", false);
    $("#chkEsCultivo").prop("checked", false);
    $("#checkBoxLever").prop("checked", true);
    $("#lblEstado").text("Activo");
    /** Test **/
    limpiarTest();
    /** Grupos Muestras **/
    $("#checkBoxCambioMuestra").prop("checked", false);
    $("#btnModalCambioMuestras").prop("disabled", true);
    limpiarMuestras();
    /** Datos de la Muestra **/
    $("#txtEstabilidadAmbiental").val("");
    $("#txtEstabilidadRefrigerado").val("");
    $("#txtEstabilidadCongelado").val("");
    $("#txtVolumenAdulto").val("");
    $("#txtVolumenPediatrica").val("");
    $("#txtNroEtiquetas").val(0);
    /** Datos de la Muestra **/
    $("#txtEstabilidadAmbiental").val("");
    $("#txtEstabilidadRefrigerado").val("");
    $("#txtVolumenAdulto").val("");
    $("#txtVolumenPediatrica").val("");
    $("#txtNroEtiquetas").val(1);
    /** Indicaciones toma de muestra **/
    $("#tbodyIndicaciones").empty();
    /** Datos de Proceso **/
    $("#chkDiaProcesoLunes").prop("checked", false);
    $("#chkDiaProcesoMartes").prop("checked", false);
    $("#chkDiaProcesoMiercoles").prop("checked", false);
    $("#chkDiaProcesoJueves").prop("checked", false);
    $("#chkDiaProcesoViernes").prop("checked", false);
    $("#chkDiaProcesoSabado").prop("checked", false);
    $("#chkDiaProcesoDomingo").prop("checked", false);
    /** Tiempo de entrega **/
    $("#txtTiempoEntregaNormal").val(0);
    $("#selectTiempoEntregaNormal").val("d");
    $("#txtTiempoEntregaHospitalizado").val(0);
    $("#selectTiempoEntregaHospitalizado").val("d");
    $("#txtTiempoEntregaUrgencia").val(0);
    $("#selectTiempoEntregaUrgencia").val("d");
    /** Días de Validez **/
    $("#txtDiasValidezAmbulatorio").val(0);
    $("#chkObligadoAmbulatorio").prop("checked", false);
    $("#txtDiasValidezHospitalizado").val(0);
    $("#chkObligadoHospitalizado").prop("checked", false);
    $("#selectIndicacionesPaciente").val(0);
    $("#txtContableCantidad").val(0);
    $("#txtEtiquetasCantidad").val(0);
    /** Datos Estadísticos **/
    $("#chkEstadistica").prop("checked", false);
    $("#chkIndicador").prop("checked", false);
    $("#chkEstadistica").prop("checked", false);
    $("#chkHojaTrabajo").prop("checked", false);
    $("#chkPlanillaHistorica").prop("checked", false);
    $("#selectPesquisa").val(0);
    $("#chkPesquisaObligatoria").prop("checked", false);
    $("#chkPesquisaObligatoria").prop("disabled", true);
    $("#selectGrupoFonasa").val(0);
    $("#txtSortSeccion").val(0);
    $("#selectLoinc").val("");
    /** Interoperabilidad (Host) **/
    $("#txtHost1").val("");
    $("#txtHost2").val("");
    $("#txtHost3").val("");
    $("#txtHostMicro").val("");
    /** Datos Extra **/
    $("#chkExamenUrgente").prop("checked", false);
    $("#chkExamenConfidencial").prop("checked", false);
    $("#chkAutovalidacion").prop("checked", false);
    $("#chkDiagnosticoObligatorio").prop("checked", false);
    $("#chkCompartirMuestra").prop("checked", false);
    $("#chkExamenTipoCurva").prop("checked", false);
    $("#chkTieneTestOpcionales").prop("checked", false);
    $("#chkCellCounter").prop("checked", false);
    $("#chkDisponibleWeb").prop("checked", false);
    $("#txtNombreWeb").val("");
    $("#txtNota").val("");
    /** Exámenes conjuntos **/
    $("#idExamenesConjuntos").val("");
    limpiarExamenes();
    /** Tabla de referencia **/
    rowCount = 1;
    colCount = 1;
    $("#txtIdTablaReferencia").val("");
    $("#tituloTablaAnexa").val("");
    $("#subtituloTablaAnexa").val("");
    $("#theadTablaRef").html(`<tr>
                                <td style="background-color: #ddd"></td>
                                <th id="A" style="background-color: #ddd">A </th>
                            </tr>`);
    $("#tbodyTablaRef").html(`<tr id="1">
                                <th style="background-color: #ddd">1 </th>
                                <td id="A1"><input id="A1" type='text' name='valorReferencia' style='visibility: hidden' value='' /></td>
                            </tr>`);
    /** Datos Impresión **/
    $("#chkImprimirSeparado").prop("checked", false);
    $("#chkImprimirDescripcion").prop("checked", false);
    $("#chkImprimirPorSeccion").prop("checked", false);
    $("#chkImprimirTabla").prop("checked", false);
    $("#chkMultiformato").prop("checked", false);
    $("#selectFormatoImpresion").val(0);
    $("#selectGrupoExamenes").val(0);
    /** Formulario **/
    $("#btnAgregarExamen").prop("disabled", false);
    $("#divActualizarBtn").hide();
    $("#divAgregarBtn").show();
    $(".formExamen").prop("disabled", false);
    $(".switch-content").removeClass("disabled inactivo");
    $('#selectEstabilidadRefrigerado').val('días');
    $('#selectEstabilidadCongelado').val('días');
    desactivarEdit();
    $('.selectpicker').selectpicker('refresh');
}

function selectValorPorDefecto(id, descripcion){
    //console.log("id muestra: "+idTipoMuestra);
    muestraPorDefecto = id;
    $("#muestraExamen").val(descripcion);
}

$("#addIndicacion").click(function(){
    cargarIndicacionesTM(indicacionesTM(),0);
});

function cargarIndicacionesTM(indicacionesTM, option){
    let options = ``;
    indicacionesTM.dato.forEach((element) => {
        options += `<option value="${element.ldvitmIdindicacionestm}" ${element.ldvitmIdindicacionestm===option?'selected':''}>${element.ldvitmDescripcionindicacion}</option>`;
    });
    $("#tbodyIndicaciones")
            .append(`<tr>
                        <td class="col-12"><select class="selectIndicacionesTM selec formExamen form-control">${options}</select></td>
                        <td class="eliminar col-4">
                            <button type="button" class="btn p-2" title="Eliminar indicación" onclick="eliminarIndicacion(this)">
                                <i class="far fa-trash-alt text-blue"></i><span class="nav-text"></span>
                            </button>
                        </td>
                    </tr>`);
}

function eliminarIndicacion(e){
    $(e).parent().parent().remove();
}

$("#txtCodigo").blur(function () {
    if ($("#txtCodigo").val() !== null && $("#txtCodigo").val().trim() !== "") {
        comprobarCodigoExiste($("#txtCodigo").val().toUpperCase());
    }
});

function comprobarCodigoExiste(codigo){
    $.ajax({
        type: "get",
        url: getctx + "/api/examen/busquedacodigo/"+codigo,
        datatype: "json",
        success: function(json){
            if (json.status === 200){
                handlerMessageError(json.message);
                buscarExamenPorId(json.dato[0].ceIdexamen);
            }
        },
        error: function (msg){
            handlerMessageError(msg);
        }
    });
}


$("#selectEstabilidadAmbiental").change(function (){
    removePropOApp($(this).val(),"#txtEstabilidadAmbiental");
});

$("#selectEstabilidadRefrigerado").change(function (){
    removePropOApp($(this).val(),"#txtEstabilidadRefrigerado");
});

$("#selectEstabilidadCongelado").change(function (){
    removePropOApp($(this).val(),"#txtEstabilidadCongelado");
});


function removePropOApp(valor,jqid){
    if (valor == "No aplica") {
        $(jqid).val("");
        $(jqid).prop("disabled", true);
    }else{
        $(jqid).removeAttr("disabled");
    }
    console.log('entra aca',valor);
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
    var idCausa = $row.getAttribute("data-id-examen");
    buscarExamenPorId(idCausa,false);
    $('.selectpicker').selectpicker('refresh');
}


$("#descargaExcelExamen").click(function () {
getExamenesParaExcel();
});
function getExamenesParaExcel(){
	 $.ajax({
        type: "get",
        url: getctx + "/api/examen/getParaExcel",
        dataType: 'json',
        success: function (json) {
			$.each( json.dato, function( index, value ){
				let codigo = value.ce_CODIGOEXAMEN.toString();
			    var newRowContent = `<tr>
									    <td>${value.ce_ACTIVO}</td>
									    <td>${codigo}</td>
									    <td>${value.ce_ABREVIADO}</td>
									    <td>${value.cs_DESCRIPCION}</td>
									    <td>${value.ce_ABREVIADO2}</td>
									    <td>${value.ce_CODIGOFONASA}</td>
									    <td>${value.cderiv_DESCRIPCION}</td>
									    
								    </tr>`;
			    $("#tb tbody").append(newRowContent);
			    
			});

			agregarExcelUtiles("Lista Exámenes");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            
        }
    });
}




