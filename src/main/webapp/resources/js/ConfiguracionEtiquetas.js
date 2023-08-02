/**
 * 
 */



$(document).ready(async function() {
	$("#divCancelEdit").hide();
	bloquearCampos();
	let datosEtiquetas = await getDatosEtiquetas();
	let html = await llenarTablaEtiquetas(datosEtiquetas);
	$("#divBtnLimpiar2").hide();
	$("#bodyEtiquetas").html(html);
	$("#ce_btnGuardarEtiqueta").attr('disabled', true);

});


$("#agregarEtiqueta").click(function (){
	limpiarFormulario();
	habilitarCampos();
	$("#codigoEtiqueta").focus();
	$("#ce_btnGuardarEtiqueta").attr('disabled', false);
	$("#divBtnEditar").show();
	$("#divCancelEdit").hide();
})

$("#ce_btnImprimirEtiqueta").click(function() {
	alert("entre a imprimir")
})

$("#ce_btnVisualizarEtiqueta").click(function() {
	alert("entre a visualizar")
})

$("#btnLimpiar").click(function() {	
	limpiarFormulario();
	$("#ce_btnGuardarEtiqueta").attr('disabled', true);
	bloquearCampos();
})

$("#divBtnEditar").click( function (){
	$("#divBtnEditar").hide();
	$("#divCancelEdit").show();		
	habilitarCampos();
	$("#divBtnLimpiar *").hide();
	$("#divBtnLimpiar2").show();
	$("#ce_btnGuardarEtiqueta").attr('disabled', false);
	$('.selectpicker').selectpicker('refresh');
})

$("#divCancelEdit").click( function (){
	$("#divCancelEdit").hide();
	$("#divBtnEditar").show();
	$("#divBtnLimpiar *").show();
	$("#divBtnLimpiar2").hide();
	$("#ce_btnGuardarEtiqueta").attr('disabled', true);
	
	
	bloquearCampos();	
})



$("#ce_btnGuardarEtiqueta").click(function (){
	let data = tomarDatosFormulario();
	insertUpateEtiqueta(data);
	limpiarFormulario();
	$("#divBtnEditar").show();
	$("#divCancelEdit").hide();
	$("#ce_btnGuardarEtiqueta").attr('disabled', true);
	bloquearCampos();	
})


 function insertUpateEtiqueta(data){
    
    datos = JSON.stringify(data);
    console.log(datos);
    $.ajax({
        type: "post",
        url: gCte.getctx + "/api/configuracionetiquetas/insertUpdate",
        data: datos,       
    	  async: false,
        contentType: "application/json",
        success: function (data) {
            try {
                if (data.status == 200) {
                    handlerMessageOk(data.message);
                    return;
                }
                
            } catch (e) {
                handlerMessageError("No se pudo agregar Etiqueta");
            }
        },
        error: function (msg) {
            console.log(msg);
            handlerMessageError("No se pudo agregar Etiqueta");
        },
    });
    
}



function llenarTablaEtiquetas(datos) {

	console.log(datos)
	let html = "";
	for (let i = 0; i < datos.length; i++) {
		let codigo = datos[i].fontcodigobarra;
		console.log(codigo)
		html = html + `<tr onclick ="llenarCamposTablaEtiquetas('${datos[i].cecb_CODIGO}' )"> <td>${i}</td> <td> ${datos[i].cecb_DESCRIPCION}</td></tr>`;
	}

	return html;
}


let llenarCamposTablaEtiquetas = async (codigo) => {
	let datosEtiqueta = await getDatoEtiquetaByCodigo(codigo);
	llenarFormulario(datosEtiqueta);
}


function llenarFormulario(datos) {

	$("#codigoEtiqueta").val(datos.cecb_CODIGO);
	$("#nombreEtiqueta").val(datos.cecb_DESCRIPCION);	
	if(datos.cecb_FONTCODIGOBARRA == null){
		datos.cecb_FONTCODIGOBARRA = "Sin especificar";
	}
	$("#selectFont>option[value='" + datos.cecb_FONTCODIGOBARRA + "']").attr("selected", false);
	$("#selectFont>option[value='" + datos.cecb_FONTCODIGOBARRA + "']").attr("selected", true);

	if (datos.cecb_PROCEDENCIA == "S") {
		$("#checkboxProcedencia").prop("checked", true);
	} else {
		$("#checkboxProcedencia").prop("checked", false);
	}
	if (datos.cecb_SERVICIO == "S") {
		$("#checkboxServicio").prop("checked", true)
	} else {
		$("#checkboxServicio").prop("checked", false)
	}
	$("#textNota").val(datos.cecb_NOTA);
	if (datos.cecb_PARAMETRIZADA == "S") {
		$("#checkboxParametrizada").prop("checked", true)
	} else {
		$("#checkboxParametrizada").prop("checked", false)
	}
	//***************Linea 1 ************************************ */
	$("#idCodigoBarraF0").val(datos.cecb_FILACODIGOBARRAS);
	$("#idCodigoBarraC0").val(datos.cecb_COLUMNACODIGOBARRAS);

	if (datos.cecb_ACTIVOCODIGOBARRAS == "S") {
		$("#checkboxCodigoBarraA0").prop("checked", true)
	} else {
		$("#checkboxCodigoBarraA0").prop("checked", false)
	}
	$("#idCodigoBarraT0").val(datos.cecb_TAMANOFONTCODIGOBARRAS);

	//***************Linea 2 ************************************ */

	$("#idNumeroCodigoBarraF").val(datos.cecb_TEXTO1FILA)
	$("#idNumeroCodigoBarraC").val(datos.cecb_TEXTO1COLUMNA);

	if (datos.cecb_ACTIVOTEXTO1 == "S") {
		$("#checkboxNumeroCodigoBarraA").prop("checked", true)
	} else {
		$("#checkboxNumeroCodigoBarraA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA1 == "S") {
		$("#checkboxNumeroCodigoBarraN").prop("checked", true)
	} else {
		$("#checkboxNumeroCodigoBarraN").prop("checked", false)
	}

	$("#idNumeroCodigoBarraT").val(datos.cecb_TEXTO1TAMANO);

	//***************Linea 3 ************************************ */

	$("#iTipoAtencionF").val(datos.cecb_TEXTO2FILA)
	$("#iTipoAtencionC").val(datos.cecb_TEXTO2COLUMNA);
	$("#iTipoAtencionL").val(datos.cecb_TEXTO2LARGO);
	if (datos.cecb_ACTIVOTEXTO2 == "S") {
		$("#checkboxTipoAtencionA").prop("checked", true)
	} else {
		$("#checkboxTipoAtencionA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA2 == "S") {
		$("#checkboxTipoAtencionN").prop("checked", true)
	} else {
		$("#checkboxTipoAtencionN").prop("checked", false)
	}
	$("#idTipoAtencionT").val(datos.cecb_TEXTO2TAMANO);
	$("#idTipoAtencionPrefijo").val(datos.cecb_TIPOPACPREFIJO);
	$("#idTipoAtencionSufijo").val(datos.cecb_TIPOPACSUFIJO);
	//***************Linea 4 ************************************ */
	$("#idProcedenciaF").val(datos.cecb_TEXTO3FILA)
	$("#idProcedenciaC").val(datos.cecb_TEXTO3COLUMNA);
	$("#idProcedenciaL").val(datos.cecb_TEXTO3LARGO);
	if (datos.cecb_ACTIVOTEXTO3 == "S") {
		$("#checkboxProcedenciaA").prop("checked", true)
	} else {
		$("#checkboxProcedenciaA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA3 == "S") {
		$("#checkboxProcedenciaN").prop("checked", true)
	} else {
		$("#checkboxProcedenciaN").prop("checked", false)
	}
	$("#idProcedenciaT").val(datos.cecb_TEXTO3TAMANO);
	$("#idProcedenciaPrefijo").val(datos.cecb_PROCEPREFIJO);
	$("#idProcedenciaSufijo").val(datos.cecb_PROCESUFIJO);
	//***************Linea 5 ************************************ */
	$("#idServicioF").val(datos.cecb_TEXTO4FILA)
	$("#idServicioC").val(datos.cecb_TEXTO4COLUMNA);
	$("#idServicioL").val(datos.cecb_TEXTO4LARGO);
	if (datos.cecb_ACTIVOTEXTO4 == "S") {
		$("#checkboxServicioA").prop("checked", true)
	} else {
		$("#checkboxServicioA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA4 == "S") {
		$("#checkboxServicioN").prop("checked", true)
	} else {
		$("#checkboxServicioN").prop("checked", false)
	}
	$("#idServicioT").val(datos.cecb_TEXTO4TAMANO);
	$("#idServicioPrefijo").val(datos.cecb_SERVPREFIJO);
	$("#idServicioSufijo").val(datos.cecb_SERVSUFIJO);
	//***************Linea 6 ************************************ */
	$("#idNombrePacienteF").val(datos.cecb_TEXTO5FILA)
	$("#idNombrePacienteC").val(datos.cecb_TEXTO5COLUMNA);
	$("#idNombrePacienteL").val(datos.cecb_TEXTO5LARGO);
	if (datos.cecb_ACTIVOTEXTO5 == "S") {
		$("#checkboxNombrePacienteA").prop("checked", true)
	} else {
		$("#checkboxNombrePacienteA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA5 == "S") {
		$("#checkboxNombrePacienteN").prop("checked", true)
	} else {
		$("#checkboxNombrePacienteN").prop("checked", false)
	}
	$("#idNombrePacienteT").val(datos.cecb_TEXTO5TAMANO);
	$("#idNombrePacientePrefijo").val(datos.cecb_NOMBPREFIJO);
	$("#idNombrePacienteSufijo").val(datos.cecb_NOMBSUFIJO);
	//***************Linea 7 ************************************ */
	$("#idRunF").val(datos.cecb_TEXTO6FILA)
	$("#idRunC").val(datos.cecb_TEXTO6COLUMNA);
	$("#idRunL").val(datos.cecb_TEXTO6LARGO);
	if (datos.cecb_ACTIVOTEXTO6 == "S") {
		$("#checkboxRunA").prop("checked", true)
	} else {
		$("#checkboxRunA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA6 == "S") {
		$("#checkboxRunN").prop("checked", true)
	} else {
		$("#checkboxRunN").prop("checked", false)
	}
	$("#idRunT").val(datos.cecb_TEXTO6TAMANO);
	$("#idRunPrefijo").val(datos.cecb_RUTPREFIJO);
	$("#idRunSufijo").val(datos.cecb_RUTSUFIJO);
	//***************Linea 8 ************************************ */
	$("#idSexoF").val(datos.cecb_TEXTO7FILA)
	$("#idSexoC").val(datos.cecb_TEXTO7COLUMNA);
	$("#idSexoL").val(datos.cecb_TEXTO7LARGO);
	if (datos.cecb_ACTIVOTEXTO7 == "S") {
		$("#checkboxSexoA").prop("checked", true)
	} else {
		$("#checkboxSexoA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA7 == "S") {
		$("#checkboxSexoN").prop("checked", true)
	} else {
		$("#checkboxSexoN").prop("checked", false)
	}
	$("#idSexoT").val(datos.cecb_TEXTO7TAMANO);
	$("#idSexoPrefijo").val(datos.cecb_SEXOPREFIJO);
	$("#idSexoSufijo").val(datos.cecb_SEXOSUFIJO);
	//***************Linea 9 ************************************ */
	$("#idEdadF").val(datos.cecb_TEXTO8FILA)
	$("#idEdadC").val(datos.cecb_TEXTO8COLUMNA);
	$("#idEdadL").val(datos.cecb_TEXTO8LARGO);
	if (datos.cecb_ACTIVOTEXTO8 == "S") {
		$("#checkboxEdadA").prop("checked", true)
	} else {
		$("#checkboxEdadA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA8 == "S") {
		$("#checkboxEdadN").prop("checked", true)
	} else {
		$("#checkboxEdadN").prop("checked", false)
	}
	$("#idEdadT").val(datos.cecb_TEXTO8TAMANO);
	$("#idEdadPrefijo").val(datos.cecb_EDADPREFIJO);
	$("#idEdadSufijo").val(datos.cecb_EDADSUFIJO);
	//***************Linea 10 ************************************ */
	$("#idTipoMuestraF").val(datos.cecb_TEXTO9FILA)
	$("#idTipoMuestraC").val(datos.cecb_TEXTO9COLUMNA);
	$("#idTipoMuestraL").val(datos.cecb_TEXTO9LARGO);
	if (datos.cecb_ACTIVOTEXTO9 == "S") {
		$("#checkboxTipoMuestraA").prop("checked", true)
	} else {
		$("#checkboxTipoMuestraA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA9 == "S") {
		$("#checkboxTipoMuestraN").prop("checked", true)
	} else {
		$("#checkboxTipoMuestraN").prop("checked", false)
	}
	$("#idTipoMuestraT").val(datos.cecb_TEXTO9TAMANO);
	$("#idTipoMuestraPrefijo").val(datos.cecb_TIPMUEPREFIJO);
	$("#idTipoMuestraSufijo").val(datos.cecb_TIPMUESUFIJO);

	//***************Linea 11 ************************************ */
	$("#idEnvaseF").val(datos.cecb_TEXTO10FILA)
	$("#idEnvaseC").val(datos.cecb_TEXTO10COLUMNA);
	$("#idEnvaseL").val(datos.cecb_TEXTO10LARGO);
	if (datos.cecb_ACTIVOTEXTO10 == "S") {
		$("#checkboxEnvaseA").prop("checked", true)
	} else {
		$("#checkboxEnvaseA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA10 == "S") {
		$("#checkboxEnvaseN").prop("checked", true)
	} else {
		$("#checkboxEnvaseN").prop("checked", false)
	}
	$("#idEnvaseT").val(datos.cecb_TEXTO10TAMANO);
	$("#idEnvasePrefijo").val(datos.cecb_ENVAPREFIJO);
	$("#idEnvaseSufijo").val(datos.cecb_ENVASUFIJO);

	//***************Linea 12 ************************************ */
	$("#idFechaRegistroF").val(datos.cecb_TEXTO11FILA)
	$("#idFechaRegistroC").val(datos.cecb_TEXTO11COLUMNA);
	$("#idFechaRegistroL").val(datos.cecb_TEXTO11LARGO);
	if (datos.cecb_ACTIVOTEXTO11 == "S") {
		$("#checkboxFechaRegistroA").prop("checked", true)
	} else {
		$("#checkboxFechaRegistroA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA11 == "S") {
		$("#checkboxFechaRegistroN").prop("checked", true)
	} else {
		$("#checkboxFechaRegistroN").prop("checked", false)
	}
	$("#idFechaRegistroT").val(datos.cecb_TEXTO11TAMANO);
	$("#idFechaRegistroPrefijo").val(datos.cecb_FECHAPREFIJO);
	$("#idFechaRegistroSufijo").val(datos.cecb_FECHASUFIJO);

	//***************Linea 13 ************************************ */
	$("#idExamenesF").val(datos.cecb_TEXTO12FILA)
	$("#idExamenesC").val(datos.cecb_TEXTO12COLUMNA);

	if (datos.cecb_ACTIVOTEXTO12 == "S") {
		$("#checkboxExamenesA").prop("checked", true)
	} else {
		$("#checkboxExamenesA").prop("checked", false)
	}
	if (datos.cecb_NEGRITA12 == "S") {
		$("#checkboxExamenesN").prop("checked", true)
	} else {
		$("#checkboxExamenesN").prop("checked", false)
	}

//***************Nro Orden ******************* */

  $("#idNOrdenF").val(datos.cecb_FILANORDEN)
  $("#idNOrdenC").val(datos.cecb_COLUMNANORDEN);
  $("#idNOrdenT").val(datos.cecb_TAMANONORDEN);
  
  if (datos.cecb_ACTIVONORDEN == "S") {
    $("#checkboxNOrdenA").prop("checked", true)
  } else {
    $("#checkboxNOrdenA").prop("checked", false)
  }
  if (datos.cecb_NEGRITANORDEN == "S") {
    $("#checkboxNOrdenN").prop("checked", true)
  } else {
    $("#checkboxNOrdenN").prop("checked", false)
  }





bloquearCampos();
$("#ce_btnGuardarEtiqueta").attr('disabled', true);

}

function tomarDatosFormulario(){
	let datos = new Object();
	datos.cecbCodigo = $("#codigoEtiqueta").val();
	datos.cecbDescripcion = $("#nombreEtiqueta").val();
	

	datos.cecbFontcodigobarra = $("#selectFont option:selected").text();
	if( $('#checkboxProcedencia').prop('checked') ) {
		datos.cecbProcedencia ="S";
    }else{
		datos.cecbProcedencia= "N";
	}
	if( $('#checkboxServicio').prop('checked') ) {
		datos.cecbServicio ="S";
    }else{
		datos.cecbServicio = "N";
	}
	datos.cecbNota = $("#textNota").val();
	if ($("#checkboxParametrizada").prop("checked")) {
		datos.cecbParametrizada = "S"
	} else {
		datos.cecbParametrizada= "N"
	}
	//****************************************************
	datos.cecbFilacodigobarras = $("#idCodigoBarraF0").val();
	datos.cecbColumnacodigobarras = $("#idCodigoBarraC0").val();	
	datos.cecbTamanofontcodigobarras = $("#idCodigoBarraT0").val();	
	
	if ($("#checkboxCodigoBarraA0").prop("checked")) {
		datos.cecbActivocodigobarras = "S"
	} else {
		datos.cecbActivocodigobarras  = "N"
	}
	//*************************************************
	datos.cecbTexto1fila = $("#idNumeroCodigoBarraF").val();
	datos.cecbTexto1columna = $("#idNumeroCodigoBarraC").val();

	if ($("#checkboxNumeroCodigoBarraN").prop("checked")) {
		datos.cecbActivotexto1 = "S"
	} else {
		datos.cecbActivotexto1 = "N"
	}
	if ($("#checkboxNumeroCodigoBarraA").prop("checked")) {
		datos.cecbNegrita1 = "S"
	} else {
		datos.cecbNegrita1 = "N"
	}
	datos.cecbTexto1tamano = $("#idNumeroCodigoBarraT").val();
//*******************************************************************
	datos.cecbTexto2fila = $("#iTipoAtencionF").val();
	datos.cecbTexto2columna = $("#iTipoAtencionC").val();
	datos.cecbTexto2largo = $("#iTipoAtencionL").val();
	if ($("#checkboxTipoAtencionA").prop("checked")) {
		datos.cecbActivotexto2 = "S"
	} else {
		datos.cecbActivotexto2 = "N"
	}
	if ($("#checkboxTipoAtencionN").prop("checked")) {
		datos.cecbNegrita2 = "S"
	} else {
		datos.cecbNegrita2 = "N"
	};
	
	datos.cecbTexto2tamano = $("#idTipoAtencionT").val();
	datos.cecbTipopacprefijo = $("#idTipoAtencionPrefijo").val();
	datos.cecbTipopacsufijo = $("#idTipoAtencionSufijo").val();
	//***************Linea 4 ************************************ 
	datos.cecbTexto3fila = $("#idProcedenciaF").val();
	datos.cecbTexto3columna = $("#idProcedenciaC").val();
	datos.cecbTexto3largo =  $("#idProcedenciaL").val();
	if ($("#checkboxProcedenciaA").prop("checked")) {
		datos.cecbActivotexto3 = "S"
	} else {
		datos.cecbActivotexto3 = "N"
	};
	if ($("#checkboxProcedenciaN").prop("checked")) {
		datos.cecbNegrita3 = "S"
	} else {
		datos.cecbNegrita3 = "N"
	};	
	datos.cecbTexto3tamano = $("#idProcedenciaT").val();
	datos.cecbProceprefijo = $("#idProcedenciaPrefijo").val();
	datos.cecbProcesufijo = $("#idProcedenciaSufijo").val();
	
	//***************Linea 5 ************************************ 
	datos.cecbTexto4fila = $("#idServicioF").val()
	datos.cecbTexto4columna = $("#idServicioC").val();
	datos.cecbTexto4largo = $("#idServicioL").val();
	if ($("#checkboxServicioA").prop("checked")) {
		datos.cecbActivotexto4 = "S"
	} else {
		datos.cecbActivotexto4 = "N"
	};
	if ($("#checkboxServicioN").prop("checked")) {
		datos.cecbNegrita4 = "S"
	} else {
		datos.cecbNegrita4 = "N"
	};
	datos.cecbTexto4tamano  = $("#idServicioT").val();
	datos.cecbServprefijo = $("#idServicioPrefijo").val();
	datos.cecbServsufijo = $("#idServicioSufijo").val();
	//***************Linea 6 ************************************ 
	datos.cecbTexto5fila  = $("#idNombrePacienteF").val()
	datos.cecbTexto5columna = $("#idNombrePacienteC").val();
	datos.cecbTexto5largo = $("#idNombrePacienteL").val();
	if ($("#checkboxNombrePacienteA").prop("checked")) {
		datos.cecbActivotexto5 = "S"
	} else {
		datos.cecbActivotexto5 = "N"
	};
	if ($("#checkboxNombrePacienteN").prop("checked")) {
		datos.cecbNegrita5 = "S"
	} else {
		datos.cecbNegrita5 = "N"
	};
	
	datos.cecbTexto5tamano = $("#idNombrePacienteT").val();
	datos.cecbNombprefijo = $("#idNombrePacientePrefijo").val();
	datos.cecbNombsufijo = $("#idNombrePacienteSufijo").val();

	//***************Linea 7 ************************************ 
	datos.cecbTexto6fila = $("#idRunF").val()
	datos.cecbTexto6columna = $("#idRunC").val();
	datos.cecbTexto6largo = $("#idRunL").val();
	if ($("#checkboxRunA").prop("checked")) {
		datos.cecbActivotexto6 = "S"
	} else {
		datos.cecbActivotexto6 = "N"
	};
	if ($("#checkboxRunN").prop("checked")) {
		datos.cecbNegrita6 = "S"
	} else {
		datos.cecbNegrita6 = "N"
	};
	
	datos.cecbTexto6tamano = $("#idRunT").val();
	datos.cecbRutprefijo = $("#idRunPrefijo").val();
	datos.cecbRutsufijo = $("#idRunSufijo").val();

	//***************Linea 8 ************************************ 
	datos.cecbTexto7fila = $("#idSexoF").val();
	datos.cecbTexto7columna = $("#idSexoC").val();
	datos.cecbTexto7largo = $("#idSexoL").val();
	if ($("#checkboxSexoA").prop("checked")) {
		datos.cecbActivotexto7 = "S"
	} else {
		datos.cecbActivotexto7 = "N"
	};
	if ($("#checkboxSexoN").prop("checked")) {
		datos.cecbNegrita7 = "S"
	} else {
		datos.cecbNegrita7 = "N"
	};
	
	datos.cecbTexto7tamano = $("#idSexoT").val();
	datos.cecbSexoprefijo = $("#idSexoPrefijo").val();
	datos.cecbSexosufijo = $("#idSexoSufijo").val();
	//***************Linea 9 ************************************ 
	datos.cecbTexto8fila = $("#idEdadF").val();
	datos.cecbTexto8columna = $("#idEdadC").val();
	datos.cecbTexto8largo = $("#idEdadL").val();
	if ($("#checkboxEdadA").prop("checked")) {
		datos.cecbActivotexto8 = "S"
	} else {
		datos.cecbActivotexto8 = "N"
	};
	if ($("#checkboxEdadN").prop("checked")) {
		datos.cecbNegrita8 = "S"
	} else {
		datos.cecbNegrita8 = "N"
	};
	
	datos.cecbTexto8tamano = $("#idEdadT").val();
	datos.cecbEdadprefijo = $("#idEdadPrefijo").val();
	datos.cecbEdadsufijo = $("#idEdadSufijo").val();
	//***************Linea 10 ************************************ 
	datos.cecbTexto9fila= $("#idTipoMuestraF").val()
	datos.cecbTexto9columna = $("#idTipoMuestraC").val();
	datos.cecbTexto9largo = $("#idTipoMuestraL").val();
	if ($("#checkboxTipoMuestraA").prop("checked")) {
		datos.cecbActivotexto9 = "S"
	} else {
		datos.cecbActivotexto9 = "N"
	};
	if ($("#checkboxTipoMuestraN").prop("checked")) {
		datos.cecbNegrita9 = "S"
	} else {
		datos.cecbNegrita9 = "N"
	};
	
	datos.cecbTexto9tamano = $("#idTipoMuestraT").val();
	datos.cecbTipmueprefijo = $("#idTipoMuestraPrefijo").val();
	datos.cecbTipmuesufijo = $("#idTipoMuestraSufijo").val();

	//***************Linea 11 ************************************ 
	datos.cecbTexto10fila = $("#idEnvaseF").val()
	datos.cecbTexto10columna = $("#idEnvaseC").val();
	datos.cecbTexto10largo = $("#idEnvaseL").val();
	if ($("#checkboxEnvaseA").prop("checked")) {
		datos.cecbActivotexto10 = "S"
	} else {
		datos.cecbActivotexto10 = "N"
	};
	if ($("#checkboxEnvaseN").prop("checked")) {
		datos.cecbNegrita10 = "S"
	} else {
		datos.cecbNegrita10 = "N"
	};
	
	datos.cecbTexto10tamano = $("#idEnvaseT").val();
	datos.cecbEnvaprefijo = $("#idEnvasePrefijo").val();
	datos.cecbEnvasufijo = $("#idEnvaseSufijo").val();


	//***************Linea 12 ************************************ 
	datos.cecbTexto11fila  = $("#idFechaRegistroF").val()
	datos.cecbTexto11columna = $("#idFechaRegistroC").val();
	datos.cecbTexto11largo = $("#idFechaRegistroL").val();
	if ($("#checkboxFechaRegistroA").prop("checked")) {
		datos.cecbActivotexto11 = "S"
	} else {
		datos.cecbActivotexto11 = "N"
	};
	if ($("#checkboxFechaRegistroN").prop("checked")) {
		datos.cecbNegrita11 = "S"
	} else {
		datos.cecbNegrita11 = "N"
	};
	
	datos.cecbTexto11tamano = $("#idFechaRegistroT").val();
	datos.cecbFechaprefijo = $("#idFechaRegistroPrefijo").val();
	
	datos.cecbFechasufijo = $("#idFechaRegistroSufijo").val();
	//***************Linea 13 ************************************ 
	datos.cecbTexto12fila = $("#idExamenesF").val()
	datos.cecbTexto12columna = $("#idExamenesC").val();
	if ($("#checkboxExamenesA").prop("checked")) {
		datos.cecbActivotexto12 = "S"
	} else {
		datos.cecbActivotexto12 = "N"
	};
	if ($("#checkboxExamenesN").prop("checked")) {
		datos.cecbNegrita12 = "S"
	} else {
		datos.cecbNegrita12 = "N"
	};
	
  //***************Linea Norden************************************ 
  datos.cecbFilaNorden = $("#idNOrdenF").val()
  datos.cecbColumnaNorden = $("#idNOrdenC").val();
  datos.cecbTamanoNorden = $("#idNOrdenT").val();
  if ($("#checkboxNOrdenA").prop("checked")) {
    datos.cecbActivoNorden = "S"
  } else {
    datos.cecbActivoNorden = "N"
  };
  if ($("#checkboxNOrdenN").prop("checked")) {
    datos.cecbNegritaNorden = "S"
  } else {
    datos.cecbNegritaNorden = "N"
  };
  
	return datos;
}

function limpiarFormulario() {
	$(".limpiarEtiqueta").val("");
    $(".form-check-input").prop("checked", false);
    
	$("#selectFont>option[value='Sin especificar']").attr("selected", false);
	$("#selectFont>option[value='Sin especificar']").attr("selected", true);	
	
	
}


function bloquearCampos(){
	$(".limpiarEtiqueta").prop('disabled', true);
    $(".form-check-input").prop('disabled', true);
	$("#selectFont").prop('disabled', true);
	
}


function habilitarCampos(){
	$(".limpiarEtiqueta").prop('disabled', false);
    $(".form-check-input").prop('disabled', false);
	$("#selectFont").prop('disabled', false);
	
	
}

async function getDatoEtiquetaByCodigo(codigo) {
	let url = gCte.getctx + '/api/configuracionetiquetas/codigo/' + codigo;
	const result = await $.ajax({
		type: "get",
		url: url,
		success: function(data) {
			return data;
		}
	});
	return result.dato;
}


async function getDatosEtiquetas() {
	let url = gCte.getctx + '/api/configuracionetiquetas';
	const result = await $.ajax({
		type: "get",
		url: url,
		success: function(data) {
			return data;
		}
	});
	return result.dato;
}




