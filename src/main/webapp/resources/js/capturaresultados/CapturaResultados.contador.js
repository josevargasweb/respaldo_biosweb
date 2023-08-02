/**
 * 
 */
 
let examenesArray = [];
let valor = "";
let valorContador = 0;
let inicioContadorCelulas = false;
let totalValorModificado = 100;
let valorBarra = 0;


 
 // cristian ---- contador celulas********************
//funcion que consulta tecla que ha sido presionada
function operaEvento(evento) {

  valor = "";
  valorContador = parseInt($("#valorTotalContador").val());
  if ((valorContador < parseInt(totalValorModificado) && inicioContadorCelulas == true)) {
	
    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra1").text().toUpperCase()) {
      valor = $("#valor1").val();
      valor++;
      $("#valor1").val(valor);
      barraContador();
    }

    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra2").text().toUpperCase()) {
      valor = $("#valor2").val();
      valor++;
      $("#valor2").val(valor);
      barraContador();
    }
    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra3").text().toUpperCase()) {
      valor = $("#valor3").val();
      valor++;
      $("#valor3").val(valor);
      barraContador();
    }
    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra4").text().toUpperCase()) {
      valor = $("#valor4").val();
      valor++;
      $("#valor4").val(valor);
      barraContador();
    }
    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra5").text().toUpperCase()) {
      valor = $("#valor5").val();
      valor++;
      $("#valor5").val(valor);
      barraContador();
    }
    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra6").text().toUpperCase()) {
      valor = $("#valor6").val();
      valor++;
      $("#valor6").val(valor);
      barraContador();
    }
    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra7").text().toUpperCase()) {
      valor = $("#valor7").val();
      valor++;
      $("#valor7").val(valor);
      barraContador();
    }
    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra8").text().toUpperCase()) {
      valor = $("#valor8").val();
      valor++;
      $("#valor8").val(valor);
      barraContador();
    }
    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra9").text().toUpperCase()) {
      valor = $("#valor9").val();
      valor++;
      $("#valor9").val(valor);
      barraContador();
    }
    if (String.fromCharCode(evento.which).toUpperCase() == $("#letra10").text().toUpperCase()) {
      valor = $("#valor10").val();
      valor++;
      $("#valor10").val(valor);
      barraContador();
    }
  }
};

async function buscarExamenesOrden(numOrden) {	
  examenesArray.splice(0, (examenesArray.length));
  let url = gCte.getctx + '/api/orden/' + numOrden + '/examenes';
  const result = await $.ajax({
    type: "get",
    url: url,
    success: function(data) {
      for (let i = 0; i < Object.values(data).length; i++) {
        let paso = Object.values(data)[i].dfe_IDEXAMEN
        examenesArray.push(paso);
      }
    },
    error: console.log("")
  });
  return result;
}

function barraContador() {
  valorContador++;
  valorBarra = valorBarra + (100 / totalValorModificado);
  $("#myBar").css("width", valorBarra + "%")
  $("#valorTotalContador").val(valorContador);
}

function inicioContador() {
  if ($("#valorNuevoContador").val() != 0) {
    totalValorModificado = $("#valorNuevoContador").val();
  } else {
    totalValorModificado = 100;
  }
  $("#valorNuevoContador").prop("readonly", true);
  inicioContadorCelulas = true;
}

function salirContador() {
  limpiaDatosModalCelulas();
  inicioContadorCelulas = false;

};

async function validarContadorExamen(e){	
  e.preventDefault();
  console.log("span --------------");
  console.log($("span.estadoTestBadge-undefined").length)
  let contador = 0;
  if ($("#txtNroOrden").text() == "") {
    handlerMessageError("Selecciones una orden");
  } else {
    let respuesta1 = null;
    let respuesta = [];
    let iniciador = $("input[data-idexamen]");
    
    for (let i = 0; i < iniciador.length; i++) {
      respuesta.push(iniciador[i].dataset.idexamen);
    }

    let result = respuesta.filter((item, index) => {
      return respuesta.indexOf(item) === index;
    })

    console.log('result result result',result)

    for (let i = 0; i < result.length; i++) {
      respuesta1 = await buscarExamenCounter(result[i])   
      if ( respuesta1.dato[1] != "N" ){
	
        contador++;       
      }
    }
    if(contador > 0){
	
		if($("span.estadoTestBadge-undefined").length == 0){			
		     	 valorBarra = 0;
		        $("#myBar").css("width", 0 + "%")
		        $('#ContadorCelulasModal').modal("hide");
		        $("#valorNuevoContador").prop("readonly", false);
		        getContador();	      
	     }else{
			 handlerMessageError("Los test no pueden estar en estado ingresado ");
		}
    }else{
      handlerMessageError("Examen no tiene contador");
    }
   }

}

async function buscarExamenCounter(idExamen) {
  
  let url = gCte.getctx + '/api/examenes/' + idExamen;
  const respuesta = await $.ajax({
    type: "get",
    url: url,
    success: function(data) {
    }
  });
  return respuesta;
}

function getContador() {
  let url = gCte.getctx + '/api/contadorCelulas';
  $.ajax({
    type: "get",
    url: url,
    success: function(data) {

      cargarModalContadorCelulas(data);
    }
  });
  $("#valorNuevoContador").prop("readonly", false);
  $("#ContadorCelulasModal").modal('show');
  inicioContadorCelulas = false;

}

function cargarModalContadorCelulas(data) {
  $("#letra1").html(data.dato.cccTeclabas.toUpperCase());
  $("#letra2").html(data.dato.cccTeclaeos.toUpperCase());
  $("#letra3").html(data.dato.cccTeclamie.toUpperCase());
  $("#letra4").html(data.dato.cccTeclajuv.toUpperCase());
  $("#letra5").html(data.dato.cccTeclabac.toUpperCase());
  $("#letra6").html(data.dato.cccTeclaseg.toUpperCase());
  $("#letra7").html(data.dato.cccTeclalin.toUpperCase());
  $("#letra8").html(data.dato.cccTeclamon.toUpperCase());
  $("#letra9").html(data.dato.cccTeclapro.toUpperCase());
  $("#letra10").html(data.dato.cccTeclabla.toUpperCase());
  limpiaDatosModalCelulas();
  $("#pacienteContadorCelulas").html($("#txtNombrePaciente").text());
  $("#ordenContadorCelulas").html($("#txtNroOrden").text());
  listaCodTestContador = [data.dato.cccTestbas, data.dato.cccTesteos, data.dato.cccTestmie, data.dato.cccTestjuv, data.dato.cccTestbac, data.dato.cccTestseg, data.dato.cccTestlin,
  data.dato.cccTestmon, data.dato.cccTestpro, data.dato.cccTestbla];
}

function limpiaDatosModalCelulas() {
  $("#valor1").val(0);
  $("#valor2").val(0);
  $("#valor3").val(0);
  $("#valor4").val(0);
  $("#valor5").val(0);
  $("#valor6").val(0);
  $("#valor7").val(0);
  $("#valor8").val(0);
  $("#valor9").val(0);
  $("#valor10").val(0);
  valorBarra = 0;
  $("#myBar").css("width", 0 + "%")
  $("#valorTotalContador").val(0);
  $("#valorNuevoContador").val(100);
}





async function saveContadorCelulas() {
  valorContador = parseInt($("#valorTotalContador").val());
  if (valorContador == totalValorModificado) {

    let orden = $("#txtNroOrden").text();
   
   let respuesta = await buscarExamenesOrden(orden);

    let listaValores = [$("#valor1").val(), $("#valor2").val(), $("#valor3").val(), $("#valor4").val(), $("#valor5").val(),
    $("#valor6").val(), $("#valor7").val(), $("#valor8").val(), $("#valor9").val(), $("#valor10").val()];

 function tuplaResultadoContador() {
    this.df_NORDEN = -1;
    this.dfe_IDEXAMEN = -1;
    this.dfet_IDTEST = -1;
    this.dfet_RESULTADO = "";
    this.tipoResultado = "";
    this.ctr_CODIGO = "";
  }

    let url = " "
   // for (let j = 0; j < examenesArray.length; j++) {
	 for (let j = 0; j < 1; j++) {
	  let testSeleccionados = new Array();
      for (let i = 0; i < 10; i++) {
	let tupla = new tuplaResultadoContador();
    tupla.df_NORDEN = orden;  
    tupla.dfet_IDTEST = listaCodTestContador[i];
    tupla.dfet_RESULTADO = (listaValores[i] * 100) / totalValorModificado;
    tupla.tipoResultado = "TRANSMITIDO";
    tupla.ctr_CODIGO = "N"
    testSeleccionados.push(tupla);
	
	
	/*  cambiado por cristian 07-03-2023

        url = gCte.getctx + "/api/orden/" + orden + "/examen/" + examenesArray[j] + "/test/" + listaCodTestContador[i];
        $.ajax({
          type: "get",
          url: url,
          success: function(data) {	
            if (data.dato != null) {
              let valor = (listaValores[i] * 100) / totalValorModificado;
              let nOrden = orden;
              let idExamen = examenesArray[j];
              let idTest = listaCodTestContador[i];             
              updateOrdenExamenTestResultado(valor, nOrden, idExamen, idTest);
            }
          }
        });
       */
        
      };
       updateOrdenExamenTestResultado(testSeleccionados);
    };
    $("#ContadorCelulasModal").modal('hide');
    return respuesta;
  }else{    
    handlerMessageError("Tiene que llegar al 100% para poder grabar");
  }
}


//function updateOrdenExamenTestResultado(testSeleccionados, valor, nOrden, idExamen, idTest) {
function updateOrdenExamenTestResultado(testSeleccionados) {
		  let url = gCte.getctx + "/api/orden/update/examen/resultado/transmitido";
		  /*cambiado por cristian 07-03-2023
		  let postData = JSON.stringify({
		    "df_NORDEN": nOrden,
		    "dfe_IDEXAMEN": idExamen,
		    "dfet_IDTEST": idTest,
		    "dfet_RESULTADO": valor,
		    "ctr_CODIGO":"N",
		   "tipoResultado": "TRANSMITIDO"
		   */
		    let postData = JSON.stringify(testSeleccionados);
		
		
		  $.ajax({
		    type: "POST",
		    url: url,
		    data: postData,
		    success: exito,
		    error: fracaso,
		    contentType: 'application/json; charset=utf-8'
		  });
		   
}





function showConfiguradorContador() {

  let url = gCte.getctx + '/api/contadorCelulas';
  $.ajax({
    type: "get",
    url: url,
    success: function(data) {
      inicioContadorCelulas = false;
      agregarDatosSelectContador($("#selectTeclaBasofilos"));
      $("#selectTeclaBasofilos option[value=" + data.dato.cccTeclabas.toUpperCase().charCodeAt() + "]").attr("selected", true);
      agregarDatosSelectContador($("#selectTeclaEosinofilos"));
      $("#selectTeclaEosinofilos option[value=" + data.dato.cccTeclaeos.toUpperCase().charCodeAt() + "]").attr("selected", true);
      agregarDatosSelectContador($("#selectTeclaMielocitos"));
      $("#selectTeclaMielocitos option[value=" + data.dato.cccTeclamie.toUpperCase().charCodeAt() + "]").attr("selected", true);
      agregarDatosSelectContador($("#selectTeclaJuveniles"));
      $("#selectTeclaJuveniles option[value=" + data.dato.cccTeclajuv.toUpperCase().charCodeAt() + "]").attr("selected", true);
      agregarDatosSelectContador($("#selectTeclaBaciliformes"));
      $("#selectTeclaBaciliformes option[value=" + data.dato.cccTeclabac.toUpperCase().charCodeAt() + "]").attr("selected", true);
      agregarDatosSelectContador($("#selectTeclaSegmentados"));
      $("#selectTeclaSegmentados option[value=" + data.dato.cccTeclaseg.toUpperCase().charCodeAt() + "]").attr("selected", true);
      agregarDatosSelectContador($("#selectTeclaLinfocitos"));
      $("#selectTeclaLinfocitos option[value=" + data.dato.cccTeclalin.toUpperCase().charCodeAt() + "]").attr("selected", true);
      agregarDatosSelectContador($("#selectTeclaMonocitos"));
      $("#selectTeclaMonocitos option[value=" + data.dato.cccTeclamon.toUpperCase().charCodeAt() + "]").attr("selected", true);
      agregarDatosSelectContador($("#selectTeclaPromielocitos"));
      $("#selectTeclaPromielocitos option[value=" + data.dato.cccTeclapro.toUpperCase().charCodeAt() + "]").attr("selected", true);
      agregarDatosSelectContador($("#selectTeclaBlastos"));
      $("#selectTeclaBlastos option[value=" + data.dato.cccTeclabla.toUpperCase().charCodeAt() + "]").attr("selected", true);

      cargarSelectTestContador(data);
      $("#ConfiguradorCelulasModal").modal('show');
    },
    error: console.log("")
  });

};

async function cargarSelectTestContador(dataCounterCell) {
  let url = gCte.getctx + '/api/test/all'

  const result = await $.ajax({
    type: "get",
    url: url,
    success: function(data) {
      data.dato.forEach((dat) => {
        $("#selectContadorBasofilos").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');
        $("#selectContadorEosinofilos").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');
        $("#selectContadorMielocitos").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');
        $("#selectContadorJuveniles").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');
        $("#selectContadorBaciliformes").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');
        $("#selectContadorSegmentados").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');
        $("#selectContadorLinfocitos").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');
        $("#selectContadorMonocitos").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');
        $("#selectContadorPromielocitos").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');
        $("#selectContadorBlastos").append('<option value="' + dat.ctIdtest + '">' + dat.ctAbreviado + '</option>');

      });

    },
    error: console.log("")
  });
  $("#selectContadorBasofilos option[value=" + dataCounterCell.dato.cccTestbas + "]").attr("selected", true);
  $("#selectContadorEosinofilos option[value=" + dataCounterCell.dato.cccTesteos + "]").attr("selected", true);
  $("#selectContadorMielocitos option[value=" + dataCounterCell.dato.cccTestmie + "]").attr("selected", true);
  $("#selectContadorJuveniles option[value=" + dataCounterCell.dato.cccTestjuv + "]").attr("selected", true);
  $("#selectContadorBaciliformes option[value=" + dataCounterCell.dato.cccTestbac + "]").attr("selected", true);
  $("#selectContadorSegmentados option[value=" + dataCounterCell.dato.cccTestseg + "]").attr("selected", true);
  $("#selectContadorLinfocitos option[value=" + dataCounterCell.dato.cccTestlin + "]").attr("selected", true);
  $("#selectContadorMonocitos option[value=" + dataCounterCell.dato.cccTestmon + "]").attr("selected", true);
  $("#selectContadorPromielocitos option[value=" + dataCounterCell.dato.cccTestpro + "]").attr("selected", true);
  $("#selectContadorBlastos option[value=" + dataCounterCell.dato.cccTestbla + "]").attr("selected", true);
  return result;
}


function agregarDatosSelectContador(selectorLetra) {
  for (let i = 65; i <= 90; i++) {
    selectorLetra.append('<option value="' + i + '">' + String.fromCharCode(i) + '</option>')
  }
  for (let i = 48; i <= 57; i++) {
    selectorLetra.append('<option value="' + i + '">' + String.fromCharCode(i) + '</option>')
  }
}

function saveConfiguradorContadorCelulas() {
  let letrasArray = [];

  if (letrasArray.includes($("#selectTeclaBasofilos").val())) {
    handlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaBasofilos").val());
  }
  if (letrasArray.includes($("#selectTeclaEosinofilos").val())) {
    handlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaEosinofilos").val());
  }

  if (letrasArray.includes($("#selectTeclaMielocitos").val())) {
    handlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaMielocitos").val());
  }

  if (letrasArray.includes($("#selectTeclaJuveniles").val())) {
    handlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaJuveniles").val());
  }

  if (letrasArray.includes($("#selectTeclaBaciliformes").val())) {
    handlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaBaciliformes").val());
  }
  if (letrasArray.includes($("#selectTeclaSegmentados").val())) {
    handlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaSegmentados").val());
  }
  if (letrasArray.includes($("#selectTeclaLinfocitos").val())) {
    ahandlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaLinfocitos").val());
  }
  if (letrasArray.includes($("#selectTeclaMonocitos").val())) {
    handlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaMonocitos").val());
  }
  if (letrasArray.includes($("#selectTeclaPromielocitos").val())) {
    handlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaPromielocitos").val());
  }
  if (letrasArray.includes($("#selectTeclaBlastos").val())) {
    handlerMessageError("Tecla Repetida");
    return;
  } else {
    letrasArray.push($("#selectTeclaBlastos").val());
  }


  let codigosTestArray = [$("#selectContadorBasofilos").val(), $("#selectContadorEosinofilos").val(), $("#selectContadorMielocitos").val(),
  $("#selectContadorJuveniles").val(), $("#selectContadorBaciliformes").val(), $("#selectContadorSegmentados").val(),
  $("#selectContadorLinfocitos").val(), $("#selectContadorMonocitos").val(), $("#selectContadorPromielocitos").val(),
  $("#selectContadorBlastos").val()];

  saveconfiguradorContadorCelulas(letrasArray, codigosTestArray);
  $("#ConfiguradorCelulasModal").modal('hide');
  validarContadorExamen();
}

function saveconfiguradorContadorCelulas(letrasArray, codigosTestArray) {
  let url = gCte.getctx + "/api/contadorcelulas";

  let postData = JSON.stringify({
    "cccIdcellcounter": 1,
    "cccTeclabas": String.fromCharCode(letrasArray[0]).toUpperCase(),
    "cccTestbas": codigosTestArray[0],
    "cccTeclaeos": String.fromCharCode(letrasArray[1]).toUpperCase(),
    "cccTesteos": codigosTestArray[1],
    "cccTeclamie": String.fromCharCode(letrasArray[2]).toUpperCase(),
    "cccTestmie": codigosTestArray[2],
    "cccTeclajuv": String.fromCharCode(letrasArray[3]).toUpperCase(),
    "cccTestjuv": codigosTestArray[3],
    "cccTeclabac": String.fromCharCode(letrasArray[4]).toUpperCase(),
    "cccTestbac": codigosTestArray[4],
    "cccTeclaseg": String.fromCharCode(letrasArray[5]).toUpperCase(),
    "cccTestseg": codigosTestArray[5],
    "cccTeclalin": String.fromCharCode(letrasArray[6]).toUpperCase(),
    "cccTestlin": codigosTestArray[6],
    "cccTeclamon": String.fromCharCode(letrasArray[7]).toUpperCase(),
    "cccTestmon": codigosTestArray[7],
    "cccTeclapro": String.fromCharCode(letrasArray[8]).toUpperCase(),
    "cccTestpro": codigosTestArray[8],
    "cccTeclabla": String.fromCharCode(letrasArray[9]).toUpperCase(),
    "cccTestbla": codigosTestArray[9],
    "cccSonidocelulas": "SonidoCelulas",
    "cccSonidototal": "SonidoTotal",
    "cccSonidograbar": "SonidoGrabar",
    "cccSonidosalir": "SonidoSalir",
    "cccSonidoerror": "SonidoError"

  });

  $.ajax({
    type: "POST",
    url: url,
    data: postData,
    contentType: 'application/json; charset=utf-8'

  });
}


//******************************************* FIN CONTADOR CELULAS*********************************************************************

