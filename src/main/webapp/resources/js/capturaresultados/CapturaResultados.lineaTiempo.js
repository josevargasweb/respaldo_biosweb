/**
 * Realizado por cristian 11-10-2022
 */

let gfechasArrayLineaTiempo = [];
let gusuariosArrayLineaTiempo = [];
let gapellidosArrayLineaTiempo = [];

async function iniciarLineaTiempo(nOrden, idExamen, idTest) {

	/*prueba de fechas****
	
	
		let fecha = moment([2002, 3, 2, 00,00,00])
		console.log(fecha)
		let fecha2  = moment([2002, 3, 1, 18,22,33])
		console.log(fecha2)
		
		console.log(moment.duration(moment(fecha).diff(moment(fecha2))))
	
	*/




	const datos = await getDatos(nOrden, idExamen, idTest);
	$("#nombreExamenLineaTiempo").html(datos.nombreexamen);
	$("#nombreTestLineaTiempo").html(datos.nombretest);
	gfechasArrayLineaTiempo = [datos.forden, datos.ftomamuestra, datos.frecepcionmuestra, datos.fingresoresultado, datos.ffirma, datos.fautoriza, datos.fimpresion];
	gusuariosArrayLineaTiempo = [datos.nombreusuarioorden, datos.nombreusuariomuestra, datos.nombreusuariorecepcionmuestra, datos.nombreusuariodigita,
	datos.nombreusuariofirma, datos.nombreusuarioautoriza, datos.nombreusuarioimprime];
	gapellidosArrayLineaTiempo = [datos.apellidousuarioorden, datos.apellidousuariomuestra, datos.apellidousuariorecepcionmuestra, datos.apellidousuariodigita,
	datos.apellidousuariofirma, datos.apellidousuarioautoriza, datos.apellidousuarioimprime];


	calculoFechas(gfechasArrayLineaTiempo, gusuariosArrayLineaTiempo, gapellidosArrayLineaTiempo, 0, 7);

	$("#lineaTiempoModal").modal('show');

}


$('.form-checkLineaTiempo').on('click', function() {
	if ($('#checkedOrdenLineaTiempo').is(':checked') || $('#checkedInformeLineaTiempo').is(':checked')) {
		if ($('#checkedOrdenLineaTiempo').is(':checked') && $('#checkedInformeLineaTiempo').is(':checked')) {
			calculoFechas(gfechasArrayLineaTiempo, gusuariosArrayLineaTiempo, gapellidosArrayLineaTiempo, 0, 7);
		} else {
			if ($('#checkedInformeLineaTiempo').is(':checked')) {
				calculoFechas(gfechasArrayLineaTiempo, gusuariosArrayLineaTiempo, gapellidosArrayLineaTiempo, 1, 7);
			} else {
				calculoFechas(gfechasArrayLineaTiempo, gusuariosArrayLineaTiempo, gapellidosArrayLineaTiempo, 0, 6);
			}
		}
	} else {
		calculoFechas(gfechasArrayLineaTiempo, gusuariosArrayLineaTiempo, gapellidosArrayLineaTiempo, 1, 6);
	}

});



function calculoFechas(fechasArray, usuariosArray, apellidosArray, inicio, fin) {
	let htmlDiferencia = "<td><strong>Tiempo de  Acción</strong><td></td></td>";
	let htmlFechas = "<td><strong>Fecha</strong></td>";
	let htmlUsuarios = "<td><strong>Usuario</strong></td>";
	let htmlAcumulado = "<td><strong>Tiempo Acumulado</strong><td></td></td> ";
	let segundos = 0;
	let minutos = 0;
	let horas = 0;
	let segundosTotal = 0;
	let minutosTotal = 0;
	let horasTotal = 0;

	for (let i = 0; i < 7; i++) {

		if (fechasArray[i] != null) {
			if (i > 0) {
				let diferencia = moment.duration(moment(fechasArray[i]).diff(moment(fechasArray[i - 1])));
				htmlDiferencia = llenarHtmlDiferencia(diferencia, htmlDiferencia);

				//****  calculo de acumulacion de tiempo  */
				segundos = segundos + diferencia._data.seconds;
				if (segundos > 60) {
					segundos = segundos - 60;
					minutos++;
				}
				minutos = minutos + diferencia._data.minutes;
				if (minutos > 60) {
					minutos = minutos - 60;
					horas++;
				}
				if (diferencia._data.days > 0) {
					horas = horas + (diferencia._data.days * 24) + diferencia._data.hours;
				} else {
					horas = horas + diferencia._data.hours;
				}


				htmlAcumulado = llenarHtmlAcumulado(htmlAcumulado, segundos, minutos, horas);

				//****  calculo de acumulacion de tiempoTotal  */
				if (i > inicio && i < fin) {

					diferencia = moment.duration(moment(fechasArray[i]).diff(moment(fechasArray[i - 1])));
					segundosTotal = segundosTotal + diferencia._data.seconds;
					if (segundosTotal > 60) {
						segundosTotal = segundosTotal - 60;
						minutosTotal++;
					}
					minutosTotal = minutosTotal + diferencia._data.minutes;
					if (minutosTotal > 60) {
						minutosTotal = minutosTotal - 60;
						horasTotal++;
					}
					if (diferencia._data.days > 0) {
						horasTotal = horasTotal + (diferencia._data.days * 24) + diferencia._data.hours;
					} else {
						horasTotal = horasTotal + diferencia._data.hours;
					}
				}

			}
			//************************************************* */

			let fecha = new Date(fechasArray[i]);
			htmlFechas = llenarHtmlFechas(fecha, htmlFechas);

		} else {
			htmlFechas = htmlFechas + "<td>--------- </td>";
			htmlDiferencia = htmlDiferencia + "<td></td>";
			htmlAcumulado = htmlAcumulado + "<td></td>";
		}

		if (usuariosArray[i] != null) {
			htmlUsuarios = llenarHtmlUsuarios(usuariosArray[i], apellidosArray[i], htmlUsuarios);
		} else {
			htmlUsuarios = htmlUsuarios + "<td></td>";
		}
	}

	// $("#tiempoTotalLineaTiempo").html("<input class='text-center' readOnly value='" + (horasTotal <  10 ? "0" + horasTotal : horasTotal) + ":" + (minutosTotal < 10 ?"0" + minutosTotal : minutosTotal) + ":" + (segundosTotal < 10 ? "0"+ segundosTotal :  segundosTotal) + "' style='width:50% ; background-color : #D5DBDB' >");
	$("#tiempoTotalLineaTiempo").html(`<span class="text-dark ml-1"><strong>${(horasTotal <  10 ? "0" + horasTotal : horasTotal)}:${(minutosTotal < 10 ?"0" + minutosTotal : minutosTotal)}:${(segundosTotal < 10 ? "0"+ segundosTotal :  segundosTotal)}</strong></span>`)
	$("#filatiempoAccion").html(htmlDiferencia);
	$("#filaLineaTiempoFechas").html(htmlFechas);
	$("#filaLineaTiempoUsuarios").html(htmlUsuarios);
	$("#filatiempoAcumulado").html(htmlAcumulado)

}


function llenarHtmlAcumulado(htmlAcumulado, segundos, minutos, horas) {
	// htmlAcumulado = htmlAcumulado + "<td><input class='text-center' readOnly value='" + (horas < 10 ? "0" + horas : horas) + ":" + (minutos < 10 ? "0" + minutos : minutos) + ":" + (segundos < 10 ? "0" + segundos : segundos) + "' style='width:40% ; background-color : #D5DBDB' ></td>";
	htmlAcumulado += `<td><strong>${(horas < 10 ? "0" + horas : horas)}:${(minutos < 10 ? "0" + minutos : minutos)}:${(segundos < 10 ? "0" + segundos : segundos)}</strong></td>`
	return htmlAcumulado;
}


function llenarHtmlDiferencia(diferencia, htmlDiferencia) {
	let fechaAccion = moment();
	fechaAccion.set({ date: diferencia._data.days, month: 0, year: 0, hour: diferencia._data.hours, minute: diferencia._data.minutes, second: diferencia._data.seconds });	
	// htmlDiferencia = htmlDiferencia + "<td><input class='text-center' readOnly value='" + (diferencia._data.days > 0 ? (diferencia._data.days * 24) + diferencia._data.hours : diferencia._data.hours) +
	// 	":" + (diferencia._data.minutes < 10 ? "0" + diferencia._data.minutes : diferencia._data.minutes) + ":" + (diferencia._data.minutes < 10 ? "0" + diferencia._data.seconds : diferencia._data.seconds) + "' style='width:40% ; background-color : #D5DBDB' ></td>";
	htmlDiferencia += `<td><strong>${(diferencia._data.days > 0 ? (diferencia._data.days * 24) + diferencia._data.hours : diferencia._data.hours)}:${(diferencia._data.minutes < 10 ? "0" + diferencia._data.minutes : diferencia._data.minutes)}:${(diferencia._data.minutes < 10 ? "0" + diferencia._data.seconds : diferencia._data.seconds)}</strong></td>`
	return htmlDiferencia;
}

function llenarHtmlFechas(fecha, html) {
	// html = html + "<td>" + fecha.toLocaleTimeString('en-GB') + "<br>" + fecha.toLocaleDateString('en-GB') + "</td>"
	html = html + "<td>" + fecha.toLocaleTimeString('en-GB') + " " + fecha.toLocaleDateString('en-GB') + "</td>"
	return html;
}

function llenarHtmlUsuarios(nombre, apellido, html) {
	// html = html + "<td>" + nombre + "<br>" + apellido + " </td>"
	html = html + "<td>" + nombre + " " + apellido + " </td>"
	return html;
}

async function getDatos(nOrden, idExamen, idTest) {
	let url = gCte.getctx + '/api/lineatiempo/orden/' + nOrden + '/examen/' + idExamen + '/test/' + idTest;
	const result = await $.ajax({
		type: "get",
		url: url,
		success: function(data) {
			return data;
		}
	});
	return result.dato;
}
