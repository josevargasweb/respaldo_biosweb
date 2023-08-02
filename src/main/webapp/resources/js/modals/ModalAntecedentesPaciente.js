$('#guardarAntecedentes').prop('disabled',true)

function mostrarModalAntecentesPac(nOrden,edit) {
    /*
     * parametro edit 
     * 0 = solo lectura
     * 1 = permite edición y guardado
     */
    //console.log(edit == 0,'edit');
    $("#nOrdenAntecedentes").text(nOrden);
    if (edit == 0){
        $('#guardarAntecedentes').hide();
    } else {
        $('#guardarAntecedentes').show();
    }
    $('#guardarAntecedentes').prop('disabled',true)
    $('.ocultarTodoModalAntecedentes').hide();
    $("#formAntecedentes").empty();
    let url = getctx + "/api/antecedentes/" + nOrden;
    $.ajax({
        type: "post",
        url: url,
        datatype: "json",
        success: function(antecedentesResponse) {
            if (antecedentesResponse.status !== 200) {
                //console.table(antecedentesResponse);
                //handlerMessageError(antecedentesResponse.message);
                $('#guardarAntecedentes').hide();
                $("#sinAntecedentes").show();
                $("#sinAntecedentes").html(`<b>${antecedentesResponse.message}</b>`);
                return;
            }
            let antecedentes = antecedentesResponse.dato;
            fillDatosAntecedentes(antecedentes, edit);

        },
        error: function(msg) {
            handlerMessageError("Error al obtener antecedentes");
            console.log(msg.responseText);
        }
    });

}

function fillDatosAntecedentes(antecedentes, edit) {
    
    $("#listAntecedentes").val(JSON.stringify(antecedentes));
    //let disabled = (edit === 1) ? false : true;
    let cont = 0;

    let hpy = new Date();
    let day = String(hpy.getDate()).padStart(2, '0');
    let month = String(hpy.getMonth() + 1).padStart(2, '0');
    let year = hpy.getFullYear();
    let currentDate = `${day}/${month}/${year}`;

    for (let ant of antecedentes) {
        console.log(ant);
        if ((ant.ca_SOLICITARTM === 'S' && edit !== 0) || edit === 0) {
            cont++;
            if (ant.ca_VALIDARESNUMERICO === 'S'){
                let formatoUM = ant.ca_FORMATOUM.replace(',', '.');
                let separadorDec = formatoUM.split(".");
                //let enteros = separadorDec[0];
                let decimal = separadorDec[1];
                let decimales = 0;
                if (typeof decimal !== 'undefined'){
                    decimales = decimal.length;
                }
                //formatoUM = parseFloat(formatoUM);
                $("#formAntecedentes").append(`<div class="mb-2">
                <div class="row">
                    <label class="col-6">${ant.ca_DESCRIPCION}</label>
                    <div class="form-group d-flex col-6">
                        <input data-id-antecedente="${ant.ca_IDANTECEDENTE}" data-obligatorio="${ant.ca_OBLIGATORIO}" value="${ant.dfa_VALORANTECEDENTE!==null ? ant.dfa_VALORANTECEDENTE : ''}"
                                type="text" class="form-control ${ant.ca_FORMATOUM === 'DD-MM-YYYY' ? 'fecha' : ''} datepickerToday ${ant.ca_FORMATOUM === 'DD-MM-YYYY' ? 'isDataPicker' : ''}" autocomplete="off"
                                ${ant.ca_FORMATOUM === 'DD-MM-YYYY' ? 'data-provide="datepicker" data-date-format="dd/mm/yyyy" data-date="' + currentDate + '"' : ''}
                                placeholder="" style="width: 50%" 
                                ${edit === 0 ? "disabled" : ""}
                                />
                        <label style="display: ${ant.ca_CODIGOUM===null ? 'none;' : 'inline-block;'}">&nbsp;${ant.ca_CODIGOUM}</label>
                    </div>
                </div>
            </div>`);
                
                IMask($(`input[data-id-antecedente=${ant.ca_IDANTECEDENTE}]`).get(0), {
                    mask: Number,  // enable number mask
                    // other options are optional with defaults below
                    scale: decimales,  // digits after point, 0 for integers
                    signed: false,  // disallow negative
                    //thousandsSeparator: '.',  // any single char
                    padFractionalZeros: true,  // if true, then pads zeros at end to the length of scale
                    normalizeZeros: true,  // appends or removes zeros at ends
                    radix: ',',  // fractional delimiter
                    mapToRadix: [',', '.'], // symbols to process as radix
                    min: 0,
                    max: formatoUM
                });
            } else {
                $("#formAntecedentes").append(`<div class="mb-2">
                    <div class="row">
                        <label class="col-6">${ant.ca_DESCRIPCION}</label>
                        <div class="form-group d-flex col-6">
                            <input data-id-antecedente="${ant.ca_IDANTECEDENTE}" data-obligatorio="${ant.ca_OBLIGATORIO}" value="${ant.dfa_VALORANTECEDENTE!==null ? ant.dfa_VALORANTECEDENTE : ''}"
                                    type="text" class="form-control ${ant.ca_FORMATOUM === 'DD-MM-YYYY' ? 'fecha' : ''} datepickerToday ${ant.ca_FORMATOUM === 'DD-MM-YYYY' ? 'isDataPicker' : ''}" autocomplete="off"
                                    ${ant.ca_FORMATOUM === 'DD-MM-YYYY' ? 'data-provide="datepicker" data-date-format="dd/mm/yyyy" data-date="' + currentDate + '"' : ''}
                                    placeholder="" style="width: 50%" 
                                    ${edit === 0 ? "disabled" : ""}
                                    />
                            <label style="display: ${ant.ca_CODIGOUM===null ? 'none;' : 'inline-block;'}">&nbsp;${ant.ca_CODIGOUM}</label>
                        </div>
                    </div>
                </div>`);
            }

            var today = new Date();

            $('.isDataPicker').datepicker({
                endDate: "today",
                format: "dd/mm/yyyy",
                defaultDate: today 
            });
            
        }
    }

    validarInputs()

    let faltanDatosObligatorios = validarFormulario();
    // if(!faltanDatosObligatorios){
    //     $('#guardarAntecedentes').prop('disabled',false)
    // }else{
    //     $('#guardarAntecedentes').prop('disabled',true)
    // }
    if (cont===0){
        $("#sinAntecedentes").show();
        $("#sinAntecedentes").html(`<b>No se encontraron antecedentes requeridos</b>`);
    } else {
        $("#sinAntecedentes").hide();
    }

  agregarSaltoEntreCampos()

}

$('form').submit(function (event) {
    event.preventDefault();
    let faltanDatosObligatorios = validarFormulario();
    if(!faltanDatosObligatorios){
        guardarAntecedentesPac();
    }else{
        handlerMessageError("Faltan Antecedentes");
    }
    return false;
});

function guardarAntecedentesPac(){
    let antecedentes = JSON.parse($("#listAntecedentes").val());
   
    for (let ant of antecedentes) {
        if (ant.ca_SOLICITARTM === 'S') {
			 ant.dfa_VALORANTECEDENTE = $(`input[data-id-antecedente=${ant.ca_IDANTECEDENTE}]`).val();           
        }
    }
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/antecedentes/save",
        data: JSON.stringify(antecedentes),
        success: function(){
            $('#modalAntecedentesPac').modal('toggle');
            handlerMessageOk("Antecedentes guardados");
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al guardar");
        },
        contentType: 'application/json; charset=utf-8'
    });
    
}
/*
$('#antecedentesPacienteFUR').datepicker({
    language: 'es',
    endDate: "+1D"
});
    */







$('#antecedentesPacienteUltimaDosis').datepicker({
    language: 'es',
    endDate: "+1D"
});

$('.fecha').datepicker({
    format: 'mm/dd/yyyy',
    language: 'es',
    endDate: "+1D",
    autoclose: true
});

$('#modalAntecedentesPac').on('hidden.bs.modal', function (event) {
        $(this).find("#sinAntecedentes").html(`<b>No requiere antecedentes</b>`)
  })
  
  
//  onkeypress="return isNumberKey(event)"
/*
function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    } else {
        return true;
    }
}*/


function validarFormulario() {
    let inputs = $("#formAntecedentes input");
    let faltanDatosObligatorios = false;
    inputs.each(function() {
      let input = $(this);
      if (input.data("obligatorio") === "S") {
        if (input.val() === "") {
          faltanDatosObligatorios = true;
          input.addClass("is-invalid");
        } else {
          input.removeClass("is-invalid");
        }
      }
    })
    return faltanDatosObligatorios
  }


  function validarInputs() {
    let formulario = $("#formAntecedentes")
    const inputsConDataPicker = formulario.find(
        "input[isDataPicker][data-obligatorio='S']"
      );
      const inputsSinDataPicker = formulario.find(
        "input:not([isDataPicker])[data-obligatorio='S']"
      );
      const todosLosInputsSinDataPicker = formulario.find(
        "input:not([isDataPicker])[data-obligatorio='N']"
      );
      const todosLosInputsConDataPicker = formulario.find(
        "input[isDataPicker][data-obligatorio='N']"
      );
    let botonGuardar = $("#guardarAntecedentes")

    inputsSinDataPicker.on("blur", verificarValores)
    todosLosInputsSinDataPicker.on("blur", verificarValores)
    inputsConDataPicker.on("change", verificarValores)
    todosLosInputsConDataPicker.on("change", verificarValores)

    function verificarValores(){
        let todosTienenValor = true
        if(inputsSinDataPicker.length > 0){
            inputsSinDataPicker.each(function() {
              let input = $(this)
              if (input.val() === "") {
                todosTienenValor = false
              }
            })
        }

        if(todosTienenValor && inputsConDataPicker.length > 0){
            inputsConDataPicker.each(function() {
                let input = $(this)
                if (input.val() === "") {
                    todosTienenValor = false
                }
            })
      
        }

        if(todosTienenValor 
            && todosLosInputsSinDataPicker.length > 0 
            && inputsSinDataPicker.length === 0 
            && inputsConDataPicker.length === 0
            ){
            todosLosInputsSinDataPicker.each(function() {
                let input = $(this)
                console.log(' input',input)
                if (input.val() === "") {
                    todosTienenValor = false
                }
            })
      
        }

        if(todosTienenValor 
            && todosLosInputsConDataPicker.length > 0
            && inputsSinDataPicker.length === 0 
            && inputsConDataPicker.length === 0
            ){
            todosLosInputsConDataPicker.each(function() {
                let input = $(this)
                if (input.val() === "") {
                    todosTienenValor = false
                }
            })
      
        }

        validarFormulario()

        if (todosTienenValor) {
          botonGuardar.prop("disabled", false)
        } else {
          botonGuardar.prop("disabled", true)
        }
    }
  }

