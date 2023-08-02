$('form').submit(function () {
    event.preventDefault();
    let formRegistroTest = $("#formRegistroTest").serializeArray();
    let dataObj = fillDataForm(formRegistroTest);
    let items = vt.getAllItems();
    let itemsGlosas = vtG.getAllItems();
    let itemsValoresReferencia = tableValoresReferencia.rows().data();
    
    let validaData = validaDatos(dataObj, items);
    let validaDataResultado = validarResultado(dataObj,itemsGlosas);
    let validarDataExtra = validarDatosExtras(dataObj);
    let validarValoresRef = validarDatosVR(itemsValoresReferencia);
    if (validarValoresRef === false) {
        if ($("#spanValoresReferencia").length === 0) {
            let spanValoresReferencia = `<span id="spanValoresReferencia"  class="label label-danger font-weight-bold label-inline ocultar">
                <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#btnValoresReferencia").append(spanValoresReferencia);
        }
    } else {
        if ($("#spanValoresReferencia").length > 0) {
            $("#spanValoresReferencia").remove();
        }
    }
    
    if (validaDataResultado === false) {
        if ($("#spanResultados").length === 0) {
            let spanResultados = `<span id="spanResultados"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanResultados").css('display', 'inline-block');
            $("#anchorResultado").append(spanResultados);
        }
    } else {
        if ($("#spanResultados").length > 0) {
            $("#spanResultados").remove();
        }
    }
    
    if (validaData === true && validaDataResultado === true && validarDataExtra === true && validarValoresRef === true){
        if ($("#spanDatosBasicos").length > 0) {
            $("#spanDatosBasicos").remove();
          }
          if ($("#spanResultados").length > 0) {
            $("#spanResultados").remove();
          }
        let dtoTests = createArrayData(dataObj, items, itemsGlosas);
        let formData = new FormData();
        formData.append('data', JSON.stringify(dtoTests)); 
        
        let antecedentes = [];
        $('#listAntecedentes li input:checked').each(function() {
            let id = $(this).attr('id');
            let valor = id.replace("antecedente-", "");
            antecedentes.push(valor);
        });
        formData.append('antecedentes', antecedentes);
        
        let itemsValRef = new Array();
        let n = itemsValoresReferencia.data().length;
        for (let i = 0; i < n; i++) {
            itemsValRef.push(itemsValoresReferencia.data()[i]);
        }
        
        formData.append('valoresReferencia', JSON.stringify(itemsValRef));
        $.ajax({
            type: "POST",
            url: getctx + "/api/test/save",
            data: formData,
            success: function(data){
                if (data.status === 200){
                    handlerMessageOk(data.message);
                    buscarTestPorId(data.dato.ctIdtest);
                    $(".formTest").prop("disabled", true);
                    limpiarFormulario();
                } else {
                    handlerMessageError(data.message);
                }
            },
            error: function () {
                handlerMessageError("Error al ingresar test");
            },
            contentType: false, 
            processData: false
        });
    }
});

$("#btnUpdateTest").click(function() {
    event.preventDefault();
    $('#btnLimpiar').removeClass('disabled');
    let formRegistroTest = $("#formRegistroTest").serializeArray();
    let dataObj = fillDataForm(formRegistroTest);
    let items = vt.getAllItems();
    let itemsGlosas = vtG.getAllItems();
    let itemsValoresReferencia = tableValoresReferencia.rows().data();
    
    let validaData = validaDatos(dataObj, items);
    let validaDataResultado = validarResultado(dataObj,itemsGlosas);
    let validarDataExtra = validarDatosExtras(dataObj);
    let validarValoresRef = validarDatosVR(itemsValoresReferencia);
    if (validarValoresRef === false) {
        if ($("#spanValoresReferencia").length === 0) {
            let spanValoresReferencia = `<span id="spanValoresReferencia"  class="label label-danger font-weight-bold label-inline ocultar">
                <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#btnValoresReferencia").append(spanValoresReferencia);
        }
        if ($("#spanResultados").length === 0) {
            let spanResultados = `<span id="spanResultados"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanResultados").css('display', 'inline-block');
            $("#anchorResultado").append(spanResultados);
        }
    } else {
        if ($("#spanValoresReferencia").length > 0) {
            $("#spanValoresReferencia").remove();
        }
        if ($("#spanResultados").length > 0) {
            $("#spanResultados").remove();
        }
    }
    if (validaData === true && validaDataResultado === true && validarDataExtra === true && validarValoresRef === true){        
        $("#circuloEditarPaciente").removeClass("bg-hover-blue");
        if ($("#spanDatosBasicos").length > 0) {
            $("#spanDatosBasicos").remove();
          }
          if ($("#spanResultados").length > 0) {
            $("#spanResultados").remove();
          }
        let dtoTests = createArrayData(dataObj, items, itemsGlosas);
        // dtoTests.cfgTest.ctVolumenmuestra = dtoTests.cfgTest.ctVolumenmuestra.replace(/,/g, '.');;
        let formData = new FormData();
        formData.append('data', JSON.stringify(dtoTests)); 
        
        let antecedentes = [];
        $('#listAntecedentes li input:checked').each(function() {
            let id = $(this).attr('id');
            let valor = id.replace("antecedente-", "");
            antecedentes.push(valor);
        });
        formData.append('antecedentes', antecedentes);
        
        let itemsValRef = new Array();
        let n = itemsValoresReferencia.data().length;
        for (let i = 0; i < n; i++) {
            itemsValRef.push(itemsValoresReferencia.data()[i]);
        }
        
        formData.append('valoresReferencia', JSON.stringify(itemsValRef));
        

        console.log("formData",formData);
        

        $.ajax({
            type: "POST",
            url: getctx + "/api/test/update",
            data: formData,
            success: function(data){
                if (data.status === 200){
                    handlerMessageOk(data.message);
                    //limpiarFormulario();
                    buscarTestPorId(data.dato.ctIdtest);
                    $("#iEditPaciente").removeClass("text-blue");
                } else {
                    handlerMessageError(data.message);
                }
            },
            error: function (data) {
                console.log(data.responseText);
                handlerMessageError("Error al ingresar test");
            },
            contentType: false, 
            processData: false
        });
    }
});

function fillDataForm (form){
    let obj = {};
    $(form).each(function(i, field){
        obj[field.name] = field.value;
        console.log(field.name + ": "+field.value);
    }); 
    return obj;
}

//Valida que campos obligatorios estén completos entre otras validaciones
function validaDatos(data, metodos){
    let validado = true;
    const pattern = new RegExp('^[A-ZÁÉÍÓÚÑ \u00D1 \u00F1 ]+$', 'i');
    if (data['ctCodigo'].trim() === ""){
        $("#txtCodigo").addClass('is-invalid');
        ui.imprimirTooltip("#txtCodigo","Código no debe estar vacío");
        validado = false;
    }
    if (data['ctAbreviado'].trim() === ""){
        $("#txtNombre").addClass('is-invalid');
        ui.imprimirTooltip("#txtNombre","Nombre no debe estar vacío");
        validado = false;
    }else if(data['ctAbreviado'].trim() !== "" && data['ctAbreviado'].trim().length > 0 && data['ctAbreviado'].trim().length < 2){
        $("#txtNombre").addClass('is-invalid');
        ui.imprimirTooltip("#txtNombre","Nombre debe contener al menos 2 caracteres");
        validado = false;
    }
    /*if (!pattern.test(data['ctAbreviado']) && data['ctAbreviado'].trim() !== ""){
        $("#txtNombre").addClass('is-invalid');
        $.notify({ message: "Nombre debe contener solo letras" }, { type: "danger" });
        validado = false;
    }*/
    if (data['ctDescripcion'].trim() === ""){
        $("#txtDescripcion").addClass('is-invalid');
        ui.imprimirTooltip("#txtDescripcion","Descripción no debe estar vacía");
        validado = false;
    }
    console.log(data);
    if (data['cmueIdtipomuestra'] === "0" || typeof data['cmueIdtipomuestra'] === 'undefined'){
        $("#selectTipoMuestra").addClass('is-invalid');
        ui.imprimirTooltip("#selectTipoMuestra","Debe seleccionar un tipo de muestra");
        $('#selectTipoMuestra').selectpicker('refresh');
        validado = false;
    }
    if (data['idLab'] === "0" || typeof data['idLab'] === 'undefined'){
        $("#selectLab").addClass('is-invalid');
        ui.imprimirTooltip("#selectLab","Debe seleccionar un laboratorio");
        $('#selectLab').selectpicker('refresh');
        validado = false;
    }
        let selectSeccion = $('#selectSeccion');
        let cantidadElementos = selectSeccion.children('option').length;
    if ((data['csecIdseccion'] === "0" || typeof data['csecIdseccion'] === 'undefined') && cantidadElementos > 0){
        $("#selectSeccion").addClass('is-invalid');
        ui.imprimirTooltip("#selectSeccion","Debe seleccionar una sección");
        $('#selectSeccion').selectpicker('refresh');
        validado = false;
    }
    if (data['cenvIdenvase'] == 0 || typeof data['cenvIdenvase'] === 'undefined'){
        $("#selectEnvase").addClass('is-invalid');
        ui.imprimirTooltip("#selectEnvase","Debe seleccionar un envase");
        $('#selectEnvase').selectpicker('refresh');
        validado = false;
    }
   
    let contVPD = 0;
    metodos.forEach(function (data){
        if (data.dataValue.cmtEsvalorpordefecto==='S'){
            contVPD++;
        }
    });
    
    if (metodos.length===0){
        if ($("#spanDatosBasicos").length === 0) {
            let spanMetodos = `<span id="spanMetodos"  class="label label-danger font-weight-bold label-inline pr-0 ocultar">
                <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#btnMetodos").append(spanMetodos);
        }
        validado = false;
    }
    
    if (contVPD>1){
        handlerMessageError("Sólo debe asignar un método como valor por defecto");
        validado = false;
    }
    if (contVPD===0 && metodos.length>0){
        handlerMessageError("Debe asignar algún método como valor por defecto");
        validado = false;
    }

    if(validado == false){
        if ($("#spanDatosBasicos").length === 0) {
            let spanDatosBasicos = `<span id="spanDatosBasicos"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanDatosBasicos").css('display', 'inline-block');
            $("#anchorRegistro").append(spanDatosBasicos);
          }
    }else{
        if ($("#spanDatosBasicos").length > 0) {
            $("#spanDatosBasicos").remove();
          }
          if ($("#spanMetodos").length > 0) {
            $("#spanMetodos").remove();
          }
    }

    return validado;
}

function validarResultado(data,itemsGlosas){
    let validado = true;
    if (data['tipoResultado'] === "N" || typeof data['tipoResultado'] === 'undefined'){
        $("#selectTipoResultado").addClass('is-invalid');
        $('#selectTipoResultado').selectpicker('refresh');
        ui.imprimirTooltip("#selectTipoResultado","Debe seleccionar un tipo de resultado");
        if ($("#spanResultados").length === 0) {
            let spanResultados = `<span id="spanResultados"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanResultados").css('display', 'inline-block');
            $("#anchorResultado").append(spanResultados);
          }
        validado = false;
    }else if(data['tipoResultado'] === '2' && jQuery.isEmptyObject(itemsGlosas)){           
            if ($("#spanResultados").length === 0) {
                let spanResultados = `<span id="spanResultados"  class="label label-danger font-weight-bold label-inline ocultar">
                <i class="fa la-exclamation-circle text-white"></i></span>`;
                $("#spanResultados").css('display', 'inline-block');
                $("#anchorResultado").append(spanResultados);
              }

              if ($("#spanGlosas").length === 0) {
                let spanGlosas = `<span id="spanGlosas"  class="label label-danger font-weight-bold label-inline ml-2 ocultar">
                <i class="fa la-exclamation-circle text-white pr-0"></i></span>`;
                $("#spanGlosas").css('display', 'inline-block');
                $("#btnGlosas").append(spanGlosas);
              }
              validado = false;
        }else{
            if ($("#spanResultados").length > 0) {
                $("#spanResultados").remove();
              }
            if ($("#spanGlosas").length > 0) {
                $("#spanGlosas").remove();
              }
        }
     
        
    return validado;
}

// (jQuery.isEmptyObject($("#kt_dual_listbox_2").val()) || jQuery.type($("#selectSeccionGlosa").val()) === 'null')

function validarDatosExtras(data){
    console.log(data);
    let validado = true;
    if (typeof data['ctResultadoobligado'] !== 'undefined'){
	console.log("distinto de indefinido ctResultadoobligado")
	console.log(data['ctCondicion'] + " //// " + data['ctCondicionTxt'] + " **** "+ data['ctResultadoobligado'] )
        if( typeof data['ctCondicion'] === 'undefined' || data['ctCondicion'] === "N" &&  data['ctCondicionTxt'] != " "){
            $("#selectObligado").addClass('is-invalid');
            ui.imprimirTooltip("#selectObligado","Debe seleccionar un comparador");
            validado = false;
        }
        if(typeof data['ctCondicion'] !== 'undefined' &&  data['ctCondicion'] !== "N" && data['ctCondicionTxt'] === ""){
            $("#txtObligado").addClass('is-invalid');
            ui.imprimirTooltip("#txtObligado","Comparador no debe estar vacía");
            validado = false;
        }
    }else{
        if ($("#spanObligado").length > 0) {
            $("#spanObligado").remove();
          }

          
    }
    if(validado == false && $("#spanObligado").length === 0){
        let spanObligado = `<span id="spanObligado"  class="label label-danger font-weight-bold label-inline ocultar">
        <i class="fa la-exclamation-circle text-white"></i></span>`;
        $("#spanObligado").css('display', 'inline-block');
        $("#anchorExtra").append(spanObligado);
    }else{
        if ($("#spanObligado").length > 0) {
            $("#spanObligado").remove();
          }
    }
           
    return validado;
}

function createArrayData(dataObj, items, itemsGlosas){
    
    let metodos = new Array();
    items.forEach((itemm) => { metodos.push(itemm.dataValue); });
    
    let glosas = new Array();
    itemsGlosas.forEach((itemg) => { glosas.push(itemg.dataValue); });
    
    let arrayData = {};
    console.log(dataObj['ctCondicion'] + "---" + dataObj['ctCondicionTxt'])
    arrayData = {
        "cfgTest": {
            "ctIdtest": dataObj['idTest'],
            "ctCodigo": dataObj['ctCodigo'].toString().toUpperCase(),
            "ctAbreviado": dataObj['ctAbreviado'].toString().toUpperCase(),
            "ctDescripcion": dataObj['ctDescripcion'].toString().toUpperCase(),
            "ctResultadoobligado": dataObj['ctResultadoobligado']==="on" ? "S" : "N",
            "ctResultadoomision": dataObj['ctResultadoomision'],
            "ctDecimales": dataObj['ctDecimales'],
            "ctFormula": null,
            "ctFormulaexamendependiente": null,
            "ctCondicion": typeof dataObj['ctCondicion'] !== 'undefined' ? dataObj['ctCondicion'] + " " + dataObj['ctCondicionTxt'] : "N",
            "ctIdgrupoglosas": null,
            "ctOpcional": dataObj['ctOpcional']==="on" ? "S" : "N",
            "ctTieneantecedentes": "N",
            "ctTestreporte": dataObj['ctTestreporte'],
            "ctHost": dataObj['ctHost'],
            "ctHostmicro": dataObj['ctHostmicro'],
            "ctSort": dataObj['ctSort'],
            "ctActivo": dataObj['ctActivo']==="on" ? "S" : "N",
            "ctSeparadormil": "N",
            "ctTesthojatrabajo": dataObj['ctTesthojatrabajo']==="on" ? "S" : "N",
            "ctCodigoloinc": dataObj['ctCodigoloinc'],
            "ctPlanillahistorica": dataObj['ctPlanillahistorica']==="on" ? "S" : "N",
            "ctVolumenmuestra": dataObj['ctVolumenmuestra'],
            "ctIdgrupoexamenesfonasa": dataObj['ctIdgrupoexamenesfonasa'],
            "ctValorinferior": null,
            "ctValorsuperior": null,
            "ctDeltacantidad": dataObj['ctDeltacantidad'],
            "ctDeltaporcentaje": dataObj['ctDeltaporcentaje'],
            "ctDiasevaluables": dataObj['ctDiasevaluables'],
            "ctEscultivo": dataObj['ctEscultivo']==="on" ? "S" : "N"
        },
        "cfgSecciones": {
            "csecIdseccion": dataObj['csecIdseccion']
        },
        "idTipoMuestra": dataObj['cmueIdtipomuestra'],
        "idTipoderesultado": dataObj['tipoResultado'],
        "idEnvase": dataObj['cenvIdenvase'],
        "idUnidadmedida": dataObj['cumIdunidadmedida'] || 1,
        "idBacgrupotest": dataObj['grupoMicrobiologia'] || 0,
        "listMetodos" : metodos,
        "listGlosas" : glosas
    }
    return arrayData;
}

/***************** REMOVER CLASS IS-INVALID CAMPOS OBLIGATORIOS ************/
$("#txtCodigo").keyup(function () {
    if ($("#txtCodigo").val().trim().length > 0) {
        $("#txtCodigo").removeClass('is-invalid');
        ui.limpiarTooltip("#txtCodigo");
    }
});

$("#txtNombre").keyup(function () {
    if ($("#txtNombre").val().trim().length > 0) {
        $("#txtNombre").removeClass('is-invalid');
        ui.limpiarTooltip("#txtNombre");
    }
});

$("#txtDescripcion").keyup(function () {
    if ($("#txtDescripcion").val().trim().length > 0) {
        $("#txtDescripcion").removeClass('is-invalid');
        ui.limpiarTooltip("#txtDescripcion");
    }
});

$("#selectTipoMuestra").change(function(e) {
    if ($("#selectTipoMuestra").val() !== "N") {
        $("#selectTipoMuestra")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
        ui.limpiarTooltip("#selectTipoMuestra");
        $('#selectTipoMuestra').selectpicker('refresh');
    }
});

$("#selectLab").change(function(e) {
    if ($("#selectLab").val()>0) {
        $("#selectLab")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
        ui.limpiarTooltip("#selectLab");
        $('#selectLab').selectpicker('refresh');
    }
});

$("#selectSeccion").change(function(e) {
    if ($("#selectSeccion").val()>0) {
        $("#selectSeccion")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
        
        ui.limpiarTooltip("#selectSeccion");
        $('#selectSeccion').selectpicker('refresh');
    }
});

$("#selectEnvase").change(function(e) {
    if ($("#selectEnvase").val() !== "N") {
        $("#selectEnvase")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
        ui.limpiarTooltip("#selectEnvase");
        $('#selectEnvase').selectpicker('refresh');
    }
});

$("#selectSeccion").change(function(e) {
    if ($("#selectSeccion").val()>0) {
        $("#selectSeccion")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
        ui.limpiarTooltip("#selectSeccion");
        $('#selectSeccion').selectpicker('refresh');
    }
});
$("#selectSeccionGlosa").change(function(e) {
        $("#selectSeccionGlosa").removeClass('is-invalid');
        ui.limpiarTooltip("#selectSeccionGlosa");
});


$("#selectTipoResultado").change(function(e) {
    if ($("#selectTipoResultado").val()!=="N") {
        $("#selectTipoResultado")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
        ui.limpiarTooltip("#selectTipoResultado");
        $("#selectTipoResultado").selectpicker("refresh");
    }
    if ($("#spanResultados").length > 0) {
        $("#spanResultados").remove();
      }
    if ($("#spanGlosas").length > 0) {
        $("#spanGlosas").remove();
      }
});
  $("#txtDescripcion").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectTipoMuestra").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectLab").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectSeccion").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});

  $("#selectEnvase").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectTipoResultado").on("blur", function() {
    $(this)
    .removeClass("is-invalid")
    .selectpicker("setStyle")
    .parent()
    .removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectSeccionGlosa").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});


$("#selectObligado").change(function(e) {
        $("#selectObligado").removeClass('is-invalid');
        ui.limpiarTooltip("#selectObligado");
});



$("#txtNombreTestFiltro").on("blur", function() {
    if($(this).val().trim() !== "" && $(this).val().trim().length >= 1 && $(this).val().trim().length < 2){
      $(this).addClass('is-invalid');
      ui.imprimirTooltip($(this),"Mínimo dos caracteres");
    }else{
      $(this).removeClass("is-invalid");
      ui.limpiarTooltip($(this));
    }
  });

  $("#txtCodigo").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#txtNombre").on("blur", function() {
    if($(this).val().trim() !== "" && $(this).val().trim().length >= 1 && $(this).val().trim().length < 2){
        $(this).addClass('is-invalid');
        ui.imprimirTooltip($(this),"Mínimo dos caracteres");
      }else{
        $(this).removeClass("is-invalid");
        ui.limpiarTooltip($(this));
      }
});
  $("#txtDescripcion").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectTipoMuestra").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectLab").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectSeccion").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});

  $("#selectEnvase").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectTipoResultado").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectSeccionGlosa").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});

  $("#txtObligado").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});
  $("#selectObligado").on("blur", function() {
    $(this).removeClass("is-invalid");
    ui.limpiarTooltip($(this));
});

$('#exampleModalSizeSm').on('hidden.bs.modal', function (e) {
    if ($("#spanGlosas").length > 0) {
        $("#spanGlosas").remove();
      }
    if ($("#spanResultados").length > 0) {
        $("#spanResultados").remove();
      }
});

$("#btnGuardarMetodos").click(function () {
    let items = vt.getAllItems();
    if (items.length>0){
        $("#spanMetodos").remove();
    }
});