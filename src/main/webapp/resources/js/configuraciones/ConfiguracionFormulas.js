var caretPos = 0;
var inputexp;
var cfgTestNumericos = new CfgTests();
var cfgAntecedentes = new CfgAntecedentes();
//agregado por marco 12/05/2023
var cfgLaboratorios = new CfgLaboratorios();
var cfgSecciones = new CfgSecciones();
var cfgExamenes = new CfgExamenes();
//
var cfgFormulaContext = new Object();

$(document).ready(function() {
	$(".ocultar").hide();
	cfgTestNumericos.getTestNumericos(cfgTestNumericos);
	cfgAntecedentes.getAntecedentes(cfgAntecedentes);
	$("#btnGuardarCond").click(guardarCondicion);
	$("#btnAgregarFormula").click(guardarFormula);
	$('.selectpicker').selectpicker('refresh');
	cfgLaboratorios.getLaboratorios();
	rellenarSelectpicker('selectLaboFormula', cfgLaboratorios.laboratorios);

});

$("#rbTest").click(function(e) {

	$("#selectTestCond").show();
	$("#selectAntecedenteCond").hide();

})

$("#rbAntecedente").click(function(e) {

	$("#selectTestCond").hide();
	$("#selectAntecedenteCond").show();

})


$("#selectTest").change(function() {
	var texto = $("#textAreaFormula").val();
	$("#txtFormula").val(caretPos);
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	if ($("#selectTest :selected").val() === "null") { return; }
	texto = prefijo + "[" + $("#selectTest :selected").val() + "]" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += ("[" + $("#selectTest :selected").val() + "]").length));
	fPosition();
});

$("#selectAntecedente").change(function() {
	var texto = $("#textAreaFormula").val();
	$("#txtFormula").val(caretPos);
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	if ($("#selectAntecedente :selected").text() === "Seleccionar") { return; }
	texto = prefijo + "[" + $("#selectAntecedente :selected").text() + "]" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	console.log("texto", texto)
	$("#txtFormula").val((caretPos += ("[" + $("#selectAntecedente :selected").text() + "]").length));
	fPosition();
});

//agregado por marco caracciolo 12/05/2023
function loadSecciones(lab) {
	let idLab = parseInt(lab);
	cfgSecciones.getSecciones(idLab, "#selectSeccionFormula");
	if (cfgSecciones.seccionesLab.length > 0) {
		rellenarSelectpicker('selectSeccionFormula', cfgSecciones.seccionesLab)
		$("#selectSeccionFormula").attr("disabled", false);
	} else {
		$("#selectSeccionFormula").empty();
		$("#selectSeccionFormula").attr("disabled", true);
		$('#selectSeccionFormula').selectpicker('val', '');
		$("#selectSeccionFormula").selectpicker('refresh');
	}
}

$('#selectLaboFormula').on('change', function() {
	$("#selectExamenFormula").empty();
	$("#selectExamenFormula").attr("disabled", true);
	$('#selectExamenFormula').selectpicker('val', '');
	$("#selectExamenFormula").selectpicker('refresh');
	loadSecciones($(this).val());
	$('#selectSeccionFormula').selectpicker('val', '');
	$(this)
		.removeClass("is-invalid")
		.selectpicker("setStyle")
		.parent()
		.removeClass("is-invalid");
	if ($(this).val() == "") {
		$("#selectSeccionFormula").empty();
		$("#selectSeccionFormula").attr("disabled", true);
		$('#selectSeccionFormula').selectpicker('val', '');
		$("#selectSeccionFormula").selectpicker('refresh');
	}
	$(this).selectpicker("refresh");
});

function loadExamenes(seccion) {
	let idSeccion = parseInt(seccion);
	cfgExamenes.getExamenes(idSeccion, "#selectExamenFormula");
	if (cfgExamenes.examenesSecc.length > 0) {
		rellenarSelectpicker('selectExamenFormula', cfgExamenes.examenesSecc)
		$("#selectExamenFormula").attr("disabled", false);
	} else {
		$("#selectExamenFormula").empty();
		$("#selectExamenFormula").attr("disabled", true);
		$('#selectExamenFormula').selectpicker('val', '');
		$("#selectExamenFormula").selectpicker('refresh');
	}
}

$('#selectSeccionFormula').on('change', function() {
	loadExamenes($(this).val());
	$('#selectExamenFormula').selectpicker('val', '');
	$(this)
		.removeClass("is-invalid")
		.selectpicker("setStyle")
		.parent()
		.removeClass("is-invalid");
	if ($(this).val() == "") {
		$("#selectExamenFormula").empty();
		$("#selectExamenFormula").attr("disabled", true);
		$('#selectExamenFormula').selectpicker('val', '');
		$("#selectExamenFormula").selectpicker('refresh');
	}
	$(this).selectpicker("refresh");
});
/** end */

function fPosition() {
	const input = document.getElementById('textAreaFormula');
	input.focus();
	input.setSelectionRange(caretPos, caretPos);
}

$("#sumar").click(function() {
	var texto = $("#textAreaFormula").val();
	$("#txtFormula").val(caretPos);

	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "+" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "+".length));
	fPosition();
});

$("#restar").click(function() {
	var texto = $("#textAreaFormula").val();
	$("#txtFormula").val(caretPos);
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "-" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "-".length));
	fPosition();
});

$("#multiplicar").click(function() {
	var texto = $("#textAreaFormula").val();
	$("#txtFormula").val(caretPos);
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "*" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "*".length));
	fPosition();
});

$("#dividir").click(function() {
	var texto = $("#textAreaFormula").val();
	$("#txtFormula").val(caretPos);
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "/" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "/".length));
	fPosition();
});

$("#potencia").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "^" + sufijo;
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "^".length));
	fPosition();
});

$("#parizq").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "()" + sufijo;
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "()".length));
	fPosition();
});

$("#parder").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + ")" + sufijo;
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += ")".length));
	fPosition();
});



$("#log").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "log()" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "log()".length));
	fPosition();
});

$("#raiz").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "sqrt()" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "sqrt()".length));
	fPosition();
});


$("#ln").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "ln()" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "ln()".length));
	fPosition();
});

$("#min").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "min(,)" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "min(,)".length));
	fPosition();
});

$("#max").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "max(,)" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "max(,)".length));
	fPosition();
});



$("#if").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "if(Cond,V,F)" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "if(Cond,V,F)".length));
	fPosition();
});

$("#lt").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "<" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "<".length));
	fPosition();
});

$("#lte").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "<=" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "<=".length));
	fPosition();
});


$("#gt").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + ">" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += ">".length));
	fPosition();
});

$("#gte").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + ">=" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += ">=".length));
	fPosition();
});

$("#noteq").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "!=" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "!=".length));
	fPosition();
});

$("#eq").click(function() {
	$("#txtFormula").val(caretPos);
	var texto = $("#textAreaFormula").val();
	let prefijo = texto.substring(0, caretPos);
	let sufijo = texto.substring(caretPos);
	texto = prefijo + "=" + sufijo;
	console.log(texto);
	$("#textAreaFormula").val(texto);
	$("#txtFormula").val((caretPos += "=".length));
	fPosition();
});


function isNumberKey(evt) {
	let charCode = (evt.which) ? evt.which : evt.keyCode;
	console.log(charCode);
	if (charCode != 43 && charCode > 31
		&& (charCode < 40 || charCode > 57))
		return false;

	return true;
}
$("#btnRevisar").click(function() {
	revisarFormula($("#textAreaFormula").val());
})

$("#textAreaFormula").keyup(function() {
	$("#textAreaFormula").removeClass("is-valid");
	$("#textAreaFormula").removeClass("is-invalid");
});

//mejorar ya que borra datos extras
$('#textAreaFormula').keydown(function(event) {
	if (event.which == 8) {
		var input = $(this);
		var value = input.val();
		var selectionStart = input[0].selectionStart;
		var currentValue = $(this).val();
		if (value[selectionStart - 1] === "[" || value[selectionStart - 1] === "]") {
			var startBracketIndex = value.lastIndexOf("[", selectionStart - 1);
			var endBracketIndex = value.indexOf("]", startBracketIndex);

			if (startBracketIndex !== -1 && endBracketIndex !== -1) {
				var newValue = value.slice(0, (startBracketIndex)) + value.slice(endBracketIndex + 1) + ' ';
				// console.log("slice 1",value.slice(0, (startBracketIndex)))
				// console.log("slice 2",value.slice(endBracketIndex + 1))
				// console.log("newValue",newValue)
				// console.log("startBracketIndex",startBracketIndex)
				// console.log("endBracketIndex",endBracketIndex)
				input.val(newValue);
				input.focus();
			}
		}
	}
});


$("#textAreaFormula").keydown(function(evt) {

	let charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode == 8 || charCode == 37 && caretPos > -1) {
		caretPos = this.selectionStart - 1;
	}
	if (charCode == 39) {
		caretPos = this.selectionStart + 1;
	}

	$("#txtFormula").val(caretPos);
});

$("#textAreaFormula").click(function() {
	caretPos = this.selectionStart;
	$("#txtFormula").val(caretPos);

});


$("#btnBuscarFiltro").click(function() {
	let selectLab = $("#selectLaboFormula").val();
	let selectSeccion = $("#selectSeccionFormula").val();
	let selectExamen = $("#selectExamenFormula").val();
	if (selectLab != null && selectSeccion != null && selectExamen != null) {
		filtroTestFormula(selectLab, selectSeccion, selectExamen);
		rellenado_SelectTest(selectExamen);
		rellenado_SelectAtecedentes(selectExamen);
		$('#selectTest').prop("disabled", true);
		$('#selectAntecedente').prop("disabled", true);
	} else {
		handlerMessageError("Debe completar todos los filtros de búsqueda");
	}

})

function getDataSearchForm() {

}
function filtroTestFormula(laboratorio, seccion, desc) {

	let url = gCte.getctx + '/api/configuracionformulas/getxfiltro';
	let data = JSON.stringify({ "labId": laboratorio, "seccion": seccion, "examenId": desc });
	$.ajax({
		type: "post",
		url: url,
		data: data,
		contentType: "application/json;charset=utf-8",
		datatype: "json",
		success: function(rpta) {

			if (rpta.status !== 200) {
				handlerMessageError(rpta.mesage);
				return;
			}
			var dato = rpta.dato;
			let cont = 0;
			$("#tbodyFiltro").empty();
			if (dato.length > 0) {
				dato.forEach(function(test) {
					cont++;
					//          cfgFormulaContext.idTest=test.ct_IDTEST;
					$("#tbodyFiltro").append("<tr class='pointer' onclick='selectTestFormula(this)' ><td class='row mx-auto'><b>" + cont + "</b></td><td class=''>" + test.ct_CODIGO + "</td><td class='' >" + test.ct_ABREVIADO + "</td><td class='idTest' style='display:none'>" + test.ct_IDTEST + "</td></tr>");
				});
				$("#lblErrorFiltro").hide();
				$("#lblTotalFiltro").show();
				$("#totalFiltro").text(cont);
			} else {
				$("#lblErrorFiltro").show();
				$("#lblTotalFiltro").hide();

			}
		},
		error: function(msg) {
			handlerMessageError(msg);
		}
	});
}

function selectTestFormula(a) {
	var $row = $(a).closest("tr"); // Find the row
	seleccionarFila(a, '#tableFiltro');
	var idTest = $row.find(".idTest").text();
	filtrarbyId(idTest);
	activarEdit(idTest);
	cfgFormulaContext.idTest = idTest;
	$('#selectTest').prop("disabled", false);
	let $selectAntecendentes = $("#selectAntecedente"); // Replace "mySelect" with the ID of your <select> element

	if ($selectAntecendentes.find('option').length === 0) {
		$selectElement.prop("disabled", true);
	} else {
		$selectElement.prop("disabled", false);
	}
		$('.selectpicker').selectpicker('refresh');
}

function rellenado_SelectTest(idExamen) {
	let url = gCte.getctx + `/api/${idExamen}/getTestxExamen`;
	$.ajax({
		type: "get",
		url: url,
		contentType: "application/json;charset=utf-8",
		datatype: "json",
		success: function(data) {
			if (data.status == 200) {
				const $selectElement = $("#selectTest");
				$selectElement.empty();
				data.dato.forEach((item) => {
					const $option = $("<option></option>");
					$option.val(item.ct_ABREVIADO);
					$option.text(item.ct_DESCRIPCION);
					$selectElement.append($option);
				});
				$('.selectpicker').selectpicker('refresh');
			} else {
				handlerMessageError('Error al encontrar test por exámenes')
			}

		},
		error: function(msg) {
			console.log(msg);
		}
	});
}
function rellenado_SelectAtecedentes(idExamen) {
	let url = gCte.getctx + `/api/${idExamen}/getAntecedentexExamen`;
	$.ajax({
		type: "get",
		url: url,
		contentType: "application/json;charset=utf-8",
		datatype: "json",
		success: function(data) {
			const $selectElement = $("#selectAntecedente");
			$selectElement.prop("disabled", false);
			if (data.status == 200) {
				$selectElement.empty();
				data.dato.forEach((item) => {
					const $option = $("<option></option>");
					$option.val(item.CA_IDANTECEDENTE);
					$option.text(item.CA_DESCRIPCION);
					$selectElement.append($option);
				});
			}
			if (data.status == 201) {
				$selectElement.prop("disabled", true);
			}
			if (data.status == 500) {
				handlerMessageError('Error al cargar Antecedentes del examen');
				console.log(data.message);
			}
			$('.selectpicker').selectpicker('refresh');
		},
		error: function(msg) {
			console.log(msg);
		}
	});
}

function revisarFormula(formula) {
	try {
		let formula = normalize($("#textAreaFormula").val());
		formula = formula.replaceAll('\[', '');
		formula = formula.replaceAll('\]', '');
		formula = formula.replaceAll('A', '');
		$("#textAreaFormula").addClass("is-valid");
		checkFormula(formula);
	} catch (err) {
		var error = err.message;
		error = error.replace("Unexpected operator", "Error:");
		error = error.replace("char", "caracter");
		Swal.fire(error);
		$("#textAreaFormula").addClass("is-invalid");
	}
}

function filtrarbyId(idTest, collapse = true) {
	let url = gCte.getctx + '/api/configuracionformulas/getxfiltro';
	let data = JSON.stringify({ "labId": null, "seccion": null, "testId": idTest });
	$.ajax({
		type: "post",
		url: url,
		data: data,
		contentType: "application/json;charset=utf-8",
		datatype: "json",
		success: function(rpta) {
			var test = rpta.dato[0];
			if (collapse) {
				$("#panelBusquedaPaciente").collapse('hide');
			}
			// $("#tbodyFiltro").empty();
			$("#lblErrorFiltro").hide();
			$("#tituloRegistro").text(test.ct_ABREVIADO);
			if (test.ct_FORMULA !== null && test.ct_FORMULA !== "") {
				$("#textAreaFormula").val(normalizeInv(test.ct_FORMULA));
			}

			$('.selectpicker').selectpicker('refresh');
		},
		error: function(msg) {
			console.log(msg);
		}
	});
}

function activarEdit(testId) {
	$("#iEditFormula").removeClass("text-dark-50");
	$("#iEditFormula").addClass("text-blue");
	localStorage.setItem("botonEditar", "activo");
	localStorage.setItem("testId", testId);

}

function desactivarEdit() {
	$("#iEditFormula").removeClass("text-primary");
	$("#iEditFormula").addClass("text-dark-50");
	localStorage.setItem("botonEditar", "inactivo");
}

class FormulaController {

	testId;
	formula;

	getFormula() {

	}

	updateTest() {

	}
}

function normalize(formula) {

	formula = normalizeTest(formula);
	formula = normalizeAntecedentes(formula);
	alert(formula);
	return formula;
}

function normalizeInv(formula) {

	formula = normalizeTestInv(formula);
	formula = normalizeAntecedentesInv(formula);

	return formula;
}

function normalizeTest(formula) {
	let n = cfgTestNumericos.misTestsNumericos.length;
	for (let i = 0; i < n; i++) {
		formula = formula.replaceAll('[' + cfgTestNumericos.misTestsNumericos[i].ctAbreviado + ']', '[' + cfgTestNumericos.misTestsNumericos[i].ctIdtest + ']');
	}

	return formula;
}


function normalizeTestInv(formula) {
	let n = cfgTestNumericos.misTestsNumericos.length;
	for (let i = 0; i < n; i++) {
		formula = formula.replaceAll("[" + cfgTestNumericos.misTestsNumericos[i].ctIdtest + "]", "[" + cfgTestNumericos.misTestsNumericos[i].ctAbreviado + "]");
	}
	return formula;
}

function normalizeAntecedentes(formula) {

	formula = formula.replaceAll("=\"S\"", "=1");
	formula = formula.replaceAll("=\"N\"", "=0");
	formula = formula.replaceAll("=\"M\"", "=1");
	formula = formula.replaceAll("=\"F\"", "=0");

	let n = cfgAntecedentes.misAntecedentes.length;
	for (let i = 0; i < n; i++) {
		formula = formula.replaceAll(cfgAntecedentes.misAntecedentes[i].caDescripcion, "A".concat(cfgAntecedentes.misAntecedentes[i].caIdantecedente));
	}
	return formula;
}

function normalizeAntecedentesInv(formula) {

	formula = formula.replaceAll("=1", "=\"S\"");
	formula = formula.replaceAll("=0", "=\"N\"");
	formula = formula.replaceAll("=1", "=\"M\"");
	formula = formula.replaceAll("=0", "=\"F\"");


	let n = cfgAntecedentes.misAntecedentes.length;
	for (let i = 0; i < n; i++) {
		formula = formula.replaceAll("[A" + cfgAntecedentes.misAntecedentes[i].caIdantecedente + "]", "[" + cfgAntecedentes.misAntecedentes[i].caDescripcion + "]");
	}
	return formula;
}

var regex = /\[\w+]\]/g;
function parse(formula) {

	let nomTest;
	while ((nomTest = regex.exec(formula)) !== null) {
		console.log(nomTest[0] + regex.lastIndex);
	}
}


function guardarCondicion() {

	if (cfgFormulaContext.idTest === undefined || cfgFormulaContext.idTest === null) {
		handlerMessageError("idTest no definido.");
		return;
	}
	let url = gCte.getctx + '/api/configuracionformulas/savecond';
	let data = JSON.stringify(getDataConf());
	debugger;
	$.ajax({
		type: "post",
		url: url,
		data: data,
		contentType: "application/json;charset=utf-8",
		datatype: "json",
		success: function(rpta) {
			var test = rpta.dato[0];
			$("#tbodyFiltro").empty();
			$("#lblErrorFiltro").hide();
			//      $("#tituloRegistro").text("Registro de Fórmulas " + test.ct_ABREVIADO);
			if (test.ct_FORMULA !== NULL) {
				$("#textAreaFormula").val(normalizeInv(test.ct_FORMULA));
			}
		},
		error: function(msg) {
			console.log(msg);
		}
	});
}



function getDataConf() {
	// validar condición

	let conversiones = new Array();
	let count = 0;
	$("#myTab li.condition").each(function() {
		count++;
		let cfgCondicionesformulasDTO = {};
		if (count === 1) {
			cfgCondicionesformulasDTO.idAntTest = $("#selectTestCond").val();
			cfgCondicionesformulasDTO.idCondBorde = $("#condBorde").val(); //verificar si estan vacias

			cfgCondicionesformulasDTO.idCondicion = $("#condicion-sub").data("id-contenedor");
			cfgCondicionesformulasDTO.ccfCondicionoperador = $("#selectRelation").val();
			if ($("#checkAssignResult").val() === "on") {
				cfgCondicionesformulasDTO.ccfFallaresultado = $("#txtAsignarResultado").val();
			}
			if ($("#checkCambiarEstado").val() === "on") {
				cfgCondicionesformulasDTO.ccfFallaestado = $("#selectEstado").val();
			}
			if ($("#checkAnadirNota").val() === "on") {
				cfgCondicionesformulasDTO.ccfFallanota = $("#txtAnadirNota").val();
			}
			cfgCondicionesformulasDTO.ccfTestoantecedente = "A";

			if ($('#rbTest').is(':checked')) {
				cfgCondicionesformulasDTO.ccfTestoantecedente = "A";
			}
			cfgCondicionesformulasDTO.ccfNrcondicionanidada = "0";

		} else {
			cfgCondicionesformulasDTO.idAntTest = $("#selectTestCond" + count).val();
			cfgCondicionesformulasDTO.idCondBorde = $("#condBorde" + count).val(); //verificar si estan vacias

			cfgCondicionesformulasDTO.idCondicion = $("#condicion-sub" + count).data("id-contenedor");
			cfgCondicionesformulasDTO.ccfCondicionoperador = $("#selectRelation" + count).val();
			if ($("#checkAssignResult" + count).val() === "on") {
				cfgCondicionesformulasDTO.ccfFallaresultado = $("#txtAsignarResultado" + count).val();
			}
			if ($("#checkCambiarEstado" + count).val() === "on") {
				cfgCondicionesformulasDTO.ccfFallaestado = $("#selectEstado" + count).val();
			}
			if ($("#checkAnadirNota" + count).val() === "on") {
				cfgCondicionesformulasDTO.ccfFallanota = $("#txtAnadirNota" + count).val();
			}
			cfgCondicionesformulasDTO.ccfTestoantecedente = "A";

			if ($('#rbTest' + count).is(':checked')) {
				cfgCondicionesformulasDTO.ccfTestoantecedente = "A";
			}
			cfgCondicionesformulasDTO.ccfNrcondicionanidada = "0";
		}
		conversiones.push(cfgCondicionesformulasDTO);
	});


	return conversiones;
	//verificar si tiene alguna condicion sin elegir


	// let idAntTest = $("#selectTestCond").val();
	// if (idAntTest === undefined || idAntTest === null) {
	//   handlerMessageError('Se debe escoger antecedente');
	//   return false;
	// }

	// let idCondBorde = $("#condBorde").val();
	// if (idCondBorde === undefined || idCondBorde === null || idCondBorde.trim() === "") {
	//   handlerMessageError('Se debe definir valor para condición');
	//   return false;
	// }

	let id = new Object();
	id.ccfIdtest = cfgFormulaContext.idTest;
	// Pendiente
	id.ccfNumerocondicion = 1;

	// let cfgCondicionesformulasDTO = new Object();
	// cfgCondicionesformulasDTO.id = id;

	// cfgCondicionesformulasDTO.ccfCondicionoperador = $("#selectRelation").val();
	// if ($("#checkAssignResult").val() === "on") {
	//   cfgCondicionesformulasDTO.ccfFallaresultado = $("#txtAsignarResultado").val();
	// }

	// if ($("#checkCambiarEstado").val() === "on") {
	//   cfgCondicionesformulasDTO.ccfFallaestado = $("#selectEstado").val();
	// }

	// if ($("#checkAnadirNota").val() === "on") {
	//   cfgCondicionesformulasDTO.ccfFallanota = $("#txtAnadirNota").val();
	// }

	// cfgCondicionesformulasDTO.ccfTestoantecedente = "A";

	// if ($('#rbTest').is(':checked')) {
	//   cfgCondicionesformulasDTO.ccfTestoantecedente = "A";
	// }
	// cfgCondicionesformulasDTO.ccfCondicionvar1 = idAntTest;
	// cfgCondicionesformulasDTO.ccfCondicionvar2 = idCondBorde;
	// cfgCondicionesformulasDTO.ccfNrcondicionanidada = "0";
	// return cfgCondicionesformulasDTO
}


function guardarFormula() {
	try {
		console.log('getDataConf()', getDataConf());
		let formula = normalize($("#textAreaFormula").val());
		//    math.evaluate(formula);
		$("#textAreaFormula").addClass("is-valid");
		let url = gCte.getctx + '/api/configuracionformulas/set';
		let data = JSON.stringify({ "formula": formula, "testId": localStorage.getItem("testId") });
		$.ajax({
			type: "post",
			url: url,
			data: data,
			contentType: "application/json;charset=utf-8",
			datatype: "json",
			success: function(rpta) {
				var test = rpta.dato[0];
				$("#tbodyFiltro").empty();
				$("#lblErrorFiltro").hide();
				//        $("#tituloRegistro").text("Registro de Fórmulas " + test.ct_ABREVIADO);
				if (test.ct_FORMULA !== NULL) {
					$("#textAreaFormula").val(normalizeInv(test.ctFormula));
				}
				handlerMessageOk("Formula guardada correctamente.");
			},
			error: function(msg) {
				console.log(msg);
			}
		});
		return true;
	} catch (err) {
		var error = err.message;
		error = error.replace("Unexpected operator", "Error:");
		error = error.replace("char", "caracter");
		handlerErrorMessage(error);
		$("#textAreaFormula").addClass("is-invalid");
		return false;
	}
}

function checkFormula(formula) {
	try {
		let url = gCte.getctx + '/api/configuracionformulas/check';
		let data = formula;
		$.ajax({
			type: "post",
			url: url,
			data: data,
			contentType: "application/json;charset=utf-8",
			datatype: "json",
			success: function(rpta) {
				if (rpta.status === 200) {
					handlerMessageError(rpta.message);
				}
				else {
					handlerMessageError(rpta.message);
				}
			},
			error: function(msg) {
				console.log(msg);
			}
		});
		return true;
	} catch (err) {
		var error = err.message;
		error = error.replace("Unexpected operator", "Error:");
		error = error.replace("char", "caracter");
		handlerErrorMessage(error);
		$("#textAreaFormula").addClass("is-invalid");
		return false;
	}
}


//agrega pestaña
function agregarTabs(counter) {
	//toma el div original y lo clona
	let originalDiv = $("div#condicion-sub");
	let cloneDiv = originalDiv.clone();

	//cambiar datos de los divs clonados
	cloneDiv.attr({ 'id': 'condicion-sub' + counter, 'data-id-contenedor': counter });
	cloneDiv.removeClass("show active");

	$("#rbTest", cloneDiv).attr({ 'id': 'rbTest' + counter, 'name': 'rbSelector' + counter });
	$("#rbTest" + counter, cloneDiv).prop("checked", true);

	$("#rbAntecedente", cloneDiv).attr({ 'id': 'rbAntecedente' + counter, 'name': 'rbSelector' + counter });
	$("#rbAntecedente" + counter, cloneDiv).prop("checked", false);

	$("#selectTestCond", cloneDiv).attr({ 'id': 'selectTestCond' + counter });    //le cambia el nombre de la id
	$("#selectTestCond" + counter, cloneDiv).val(null);

	$("#selectRelation", cloneDiv).attr({ 'id': 'selectRelation' + counter });
	$("#selectRelation" + counter, cloneDiv).val(">");

	$("#condBorde", cloneDiv).attr({ 'id': 'condBorde' + counter });
	$("#condBorde" + counter, cloneDiv).val("");

	$("#checkAssignResult", cloneDiv).attr({ 'id': 'checkAssignResult' + counter });
	$("#checkAssignResult" + counter, cloneDiv).prop("checked", false);

	$("#txtAsignarResultado", cloneDiv).attr({ 'id': 'txtAsignarResultado' + counter });
	$("#txtAsignarResultado" + counter, cloneDiv).val("");

	$("#checkCambiarEstado", cloneDiv).attr({ 'id': 'checkCambiarEstado' + counter });
	$("#checkCambiarEstado" + counter, cloneDiv).prop("checked", false);

	$("#selectEstado", cloneDiv).attr({ 'id': 'selectEstado' + counter });
	$("#selectEstado" + counter, cloneDiv).val("N");

	$("#checkAnadirNota", cloneDiv).attr({ 'id': 'checkAnadirNota' + counter });
	$("#checkAnadirNota" + counter, cloneDiv).prop("checked", false);

	$("#txtAnadirNota", cloneDiv).attr({ 'id': 'txtAnadirNota' + counter });
	$("#txtAnadirNota" + counter, cloneDiv).val("");


	// //agrega en el div los datos ya formatiados
	$("#myTabContent").append(cloneDiv);


	//agregando al tab
	let originalTab = $("li#presentacion");
	let cloneTab = originalTab.clone();

	cloneTab.attr('id', 'presentacion' + counter);
	$("#condicion", cloneTab).attr({ 'id': 'condicion' + counter, 'data-target': '#condicion-sub' + counter, 'aria-controls': "condicion-sub" + counter, 'aria-selected': "false" });    //cambia los atributos del tab
	$("#condicion" + counter, cloneTab).removeClass("active");
	$("#condicion" + counter, cloneTab).text("Condicion " + counter)
	$("#nav-item-agregar").before(cloneTab);
	//incrementa el contador
	counter++;
	$("#agregar-tab").attr("onclick", "agregarTabs(" + counter + ")");
	$('.selectpicker').selectpicker();
};


const tableNav = new TableNavigation("tableFiltro", selectCausaAuto);
tableNav.changeContenedor(".table-container");
function selectCausaAuto(a = null) {
	let $row;
	if (a == null) {
		let tabla = document.getElementById("tableFiltro");
		let tbody = tabla.querySelector("tbody");
		let primerTR = tbody.querySelector("tr:first-child");
		$row = primerTR;
	} else {
		$row = a;
	}
	var idCausa = $row.querySelector('td.idTest').textContent;
	filtrarbyId(idCausa, false);
	$('.selectpicker').selectpicker('refresh');
}
