/**
 * Cristian
 */


let arrayPosicion = [];
let datosUnidad;
let datosGraficos = [];
let labels = [];


async function iniciarHistorico(nOrden, idExamen, idTest) {

	

	let datosFicha = await getFichaExamenTest(nOrden, idExamen, idTest);
	
	//agregado 07-10
	$("#idTest").val(datosFicha.dfet_IDTEST)
	$("#idPaciente").val(datosFicha.dp_IDPACIENTE)

	//agregado 07-10
	$("#idTest").val(datosFicha.dfet_IDTEST)
	$("#idPaciente").val(datosFicha.dp_IDPACIENTE)

	let datos = await getPacienteTest(datosFicha.dp_IDPACIENTE, datosFicha.dfet_IDTEST);

	let testNumerico = false;

	//***se consulta si es un test numerico -- si no es numerico no deja pasar */
	datos.forEach(element => {
		if (element[10] == 3 || element[10] == 6) {
			testNumerico = true;
		}
	})


	if (testNumerico) {
		//let htmlTabla = agregarPrimerDatoHistoricoTabla1(datos, nOrden)		

		agregarDatosHistoricoTabla1(datos, nOrden, "", "");
		let resultado = 0;
		agregarDatosPaciente(datosFicha);
		
		datos.forEach(element => {
			if (element[3] == nOrden) {
				if (element[5] != null) {
					resultado = element[5];
				}
			}
		})

		agregarDatosHistoricoTabla2(nOrden, resultado)
		crearGraficoHistorico(datos, nOrden);
		$("#historicoModal").modal('show');
	} else {
		console.log("error, no son resultados numericos")
	}
}



//AQUI SE CONSTRUYE GRAFICO *****************************************
function crearGraficoHistorico(datos, nOrden) {
	const $grafica = document.querySelector("#graficaHistorico");
	// Las etiquetas son las que van en el eje X. 
	const fechasX = [];
	const datosY = [];

	fechasX.splice(0, fechasX.length - 1)
	datosY.splice(0, fechasX.length - 1)
	datos.forEach(element => {		//Datos en objecto [0]-> idTest [1]->fechaingresoresultado [2]->resultado [3]->nOrden [4]->idExamen -> [5]->resultadonumerico [6]->unidadMedida [7]-->CodigoMedida
		
		if (element[9] === 5) {
			let date;
			let resultado;
			if (element[1] == null) {
				date = new Date(Date.now());
			} else {
				date = new Date(element[1]);
			}
			fechaDate = date.getDate() +
				"/" + (date.getMonth() + 1) +
				"/" + date.getFullYear();
			if (element[5] != null) {
				resultado = element[5];
			} else {
				resultado = 0;
			}
			fechasX.push(fechaDate);
			datosY.push(resultado);			
		}
	})
	fechasX.reverse();
	datosY.reverse();
	agregarDatosGrafico(fechasX, datosY, datos[0][7]);
}


function agregarDatosPaciente(datos) {
	$("#pacienteHistorico").html(datos.dp_NOMBRES + " " + datos.dp_PRIMERAPELLIDO +" "+ datos.dp_SEGUNDOAPELLIDO);
	$("#testHistorico").html(datos.ct_ABREVIADO);
}


async function agregarDatosHistoricoTabla1(datos, nOrden, cantidad, porcentaje) {
	console.log("agregarDatosHistoricoTabla1 ")
console.log(datos[0])
	let cantidadHistorico;
	let porcentajeHistorico;
	let htmlTabla = "";

	//*****LLENANDO DELTACHECK -- Modificado 07-10 ******-- */
	if (cantidad == "") {
		cantidadHistorico = (datos[0][11] != null) ? datos[0][11] : 0;
	} else {
		cantidadHistorico = cantidad;
	}

	if (porcentaje == "") {
		porcentajeHistorico = (datos[0][12] != null) ? datos[0][12] : 0;
	} else {
		porcentajeHistorico = porcentaje;
	}

	$("#cantidadHistorico").val(cantidadHistorico)
	$("#porcentajeHistorico").val(porcentajeHistorico)

	//*************************************** */

	let contador = 0;
	totalDelta = 0;

	datos.forEach(element => {
	
		let resultado = "";
		if (element[9] === 5) {
			//Datos en objecto [0]-> idTest [1]->fechaingresoresultado [2]->resultado [3]->nOrden [4]->idExamen -> [5]->resultadonumerico [6]->unidadMedida [7]-->CodigoMedida [8]-->examenfirmado
			//[8]-->examenfirmado [9] -->idestadoResultadoTest [10]--> cidtipoResultado  [11] -->ct_deltacantidad  [12]-->ct_deltaPorcentaje
			if (element[5] != null) {
				resultado = element[5];
			}

		
			//CALCULANDO DELTA CHECK *** modificado 07-10
			if (contador < cantidadHistorico) {
				contador++;
				totalDelta = totalDelta + resultado;
			}

			let date = new Date(element[1]);
			if (element[1] == null) {
				date = new Date(Date.now());
			} else {
				date = new Date(element[1]);
			}

			htmlTabla = htmlTabla + "<tr class='text-center'>"
				+ "<td>" + date.getDate() +
				"/" + (date.getMonth() + 1) +
				"/" + date.getFullYear() + "</td>"
				+ "<td><a href='#' id='nOrdenHistorico' onclick='agregarDatosHistoricoTabla2(" + element[3] + "," + resultado + ")' style='color:blue;'>" + element[3] + "</a></td>"
				+ "<td>" + resultado + "</td>"
				+ "<td>" + element[7] + "</td>"
				+ "</tr>";
		}		

	})

	//AGREGANDO DATOS A DELTACHECK**********************************************************

	if (contador > 0) {
		let totalDeltaPromedio = totalDelta / contador;

		totalDeltaPorcentaje = totalDeltaPromedio * (porcentajeHistorico / 100)

		$("#intervaloHistorico").val((totalDeltaPromedio - totalDeltaPorcentaje).toFixed(2) + "  -  " + (totalDeltaPorcentaje + totalDeltaPromedio).toFixed(2))
	} else {
		$("#intervaloHistorico").val("inf - sup")
	}

	//******************************************************************************* */
	$("#tabla1ModalHistorico").html(htmlTabla)
}


//agregado 07-10  ********************
$("#cantidadHistorico, #porcentajeHistorico").change(async function() {

	let idPaciente = $("#idPaciente").val();
	let idTest = $("#idTest").val();
	let datos = await getPacienteTest(idPaciente, idTest);
	agregarDatosHistoricoTabla1(datos, nOrden, $("#cantidadHistorico").val(), $("#porcentajeHistorico").val());
})

//**************************************************** */

async function agregarDatosHistoricoTabla2(nOrden, resul) {
	let datosTabla2 = await getOrdenTabla2(nOrden);
	$("#resultadoDeltaCheck").val(resul);
	agregaDatosTabla2(datosTabla2, nOrden);
}

function agregaDatosTabla2(dato, nOrden) {
	let date = new Date(Date.now())

	if (dato[0][0] != null) {
		date = new Date(dato[0][0]);
	}
	fechaHtml = date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
	$("#ordenHistorico").html(nOrden);
	$("#fechaHistorico").html(fechaHtml);
	let htmlTabla = "";
	dato.forEach(element => {
		//Datos en objecto [0]->fechaOrden [1]->descTest [2]->resultado [3]->unidadMedida []4]->descExamen
		let resultado = "";
		if (element[2] != null) {
			resultado = element[2];
		}
		htmlTabla = htmlTabla + "<tr class='text-center'>"
			+ "<td>" + element[1] + "</td>"
			+ "<td>" + resultado + " </td>"
			+ "<td>" + element[3] + "</td>"
			+ "<td>" + element[4] + "</td>"
			+ "</tr>";
	})
	$("#tabla2ModalHistorico").html(htmlTabla);
}

async function getOrdenTabla2(nOrden) {
	let url = gCte.getctx + '/api/orden/' + nOrden + '/examentest';
	const result = await $.ajax({
		type: "get",
		url: url,
		success: function(data) {
			//prueba(data);
		}
	});
	return result.dato;
}



async function getFichaExamenTest(nOrden, idExamen, idTest) {
	let url = gCte.getctx + '/api/orden/' + nOrden + '/examen/' + idExamen + '/test/' + idTest;
	const result = await $.ajax({
		type: "get",
		url: url,
		success: function(data) {
		}
	});
	return result.dato;
}



async function getPacienteTest(idPaciente, idTest) {

	let url = gCte.getctx + '/api/paciente/' + idPaciente + '/test/' + idTest;
	const result = await $.ajax({
		type: "get",
		url: url,
		success: function(data) {
			return data;
		}
	});

	return result.dato;
}


//**********************************GRAFICO Listo/
function agregarDatosGrafico(fechasX, datosY, unidad) {

	var valores = datosY;

	/*CONSTRUCCIÓN DE LA GRÁFICA*/
	Highcharts.chart('graficaHistorico', {
		chart: {
			type: 'spline'
		},
		tooltip: {
			headerFormat: '<b>Fecha {point.x}</b><br>',
			pointFormat: 'Unidad {point.y} '
		},

		title: {
			text: ''
		},
		yAxis: {
			title: {
				text: '<b>Unidad ' + unidad + '</b>'
			}
		},
		xAxis: {
			categories: fechasX
		},

		series: [{
			color: '#8bbc21',
			marker: {
				fillOpacity: 1,
				lineWidth: 3,
				lineColor: '#000000'
			},
			name: 'Fecha',
			data: (function() {
				var data = [];
				for (var i = 0; i < valores.length; i++) {
					data.push([valores[i]]);
				}
				return data;
			})(),

		}],

		credits: {
			enabled: false
		}

	});

}
