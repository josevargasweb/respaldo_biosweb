//estado botones
$("#btnFirmaResultados").prop('disabled', true);
$("#btnRetiraFirma").addClass('disabled')

var getParametros = function(d) {
  return JSON.stringify(d);
};

function obtenerResultadosSeleccionados(pTableResultadosExamenesOrden) {

  function tuplaResultadoExamen() {
    this.df_NORDEN = -1;
    this.dfe_IDEXAMEN = -1;
    this.dfet_IDTEST = -1;
    this.dfet_RESULTADO = "";
    this.cert_DESCRIPCIONESTADOTEST = "";
    this.dfet_REFERENCIADESDE;
    this.dfet_REFERENCIAHASTA;
  }

  const resultadosSeleccionados = pTableResultadosExamenesOrden.rows({ selected: true }).data();
  if (resultadosSeleccionados === undefined || resultadosSeleccionados === null) {
    handlerMessageError('Revisar selección de resultados.');
    return false;
  }
  const n = resultadosSeleccionados.count();
  if (n === 0) {
    handlerMessageError('Verifique si ha seleccionado resultados.');
    return false;
  }
  let idSeleccionados = new Array();
  for (let i = 0; i < n; i++) {	
    let tupla = new tuplaResultadoExamen();
    tupla.df_NORDEN = resultadosSeleccionados[i].df_NORDEN;
    tupla.dfe_IDEXAMEN = resultadosSeleccionados[i].dfe_IDEXAMEN;
    tupla.dfet_IDTEST = resultadosSeleccionados[i].dfet_IDTEST;
    
    tupla.dfet_RESULTADO = resultadosSeleccionados[i].dfet_RESULTADO; 
    tupla.cert_DESCRIPCIONESTADOTEST = resultadosSeleccionados[i].cert_DESCRIPCIONESTADOTEST;
    tupla.dfet_REFERENCIADESDE =  resultadosSeleccionados[i].dfet_REFERENCIADESDE;
    tupla.dfet_REFERENCIAHASTA =  resultadosSeleccionados[i].dfet_REFERENCIAHASTA;
    
  //*********************************************** */
    idSeleccionados.push(tupla);
  } 
  return idSeleccionados;
}



// Eventos y funciones relacionados con filas de resultados


function changeValue(e) {
	 let omision = e.target.dataset.resultadoOmision;
  let input = e.target;
  let oldValue = input.dataset.valor;
  	
   console.log("entre a cambio de valor ")
  if (oldValue !== undefined) {
    console.log(oldValue);
  }
  if (input.dataset['tipoResultado'] === 'N') {
	    
	
    let newValue = e.target.value.replaceAll(',', '.');
    
  let valorAnteriorNumerico = parseFloat(oldValue.replaceAll(',', '.'));
   let valorNuevoNumerico = parseFloat(newValue);
   // let valorAnteriorNumerico = oldValue.replaceAll(',', '.');
   // let valorNuevoNumerico =newValue;
    
    
    if (valorAnteriorNumerico == valorNuevoNumerico && omision === "N") {
       return;
    }
   
   if(Number.isNaN(valorAnteriorNumerico) && Number.isNaN(valorNuevoNumerico) ){
	return;
}
   
    newValue = newValue.replaceAll(',', '.');
    
 

    actualizarValNumerico(input, nroOrdenSeleccionada);
    input.dataset.valor = e.target.value;
   

    let thisRow = $(input).parent().parent();
    let row = $('#resultadosExamenesOrdenesDatatable tbody tr').index(thisRow);
    let data = tableResultadosExamenesOrden.row(row).data();
    let val = data['dfet_RCRITICO'];
    let tipo_alerta = getTipoValorAlerta(val);
    if (tipo_alerta == 'anormal') {
      handlerMessageWarning("Valor anormal");
    } else if (tipo_alerta == 'critico') {
      handlerMessageError("Valor crítico");
    }

  }
	  else if (input.dataset['tipoResultado'] === 'G' || input.dataset['tipoResultado'] === 'T') {
		console.log("entre a grabar nuevamente")
	    actualizarValNumerico(input, nroOrdenSeleccionada);
	  }
	  else {
	    console.log('Otro tipo');
	  }
 
}


function keypressValue(e) {
		
    console.log(e.target.dataset.resultadoOmision)
    let omision = e.target.dataset.resultadoOmision;
  if (e.key == "Enter" || e.key == "Tab") {	
	let input = e.target;	
	 let foco ="";
	if((((input.parentNode).parentNode).nextElementSibling)){
		// console.log((((input.parentNode).parentNode).nextElementSibling).children[4])
			 foco = (((input.parentNode).parentNode).nextElementSibling).children[4]
		//console.log("este es el foco *******************************")
		//console.log(foco.children[0])
		foco.children[0].focus();
		
	}
   
	
   let oldValue = parseFloat(input.dataset.valor);
    // let oldValue = input.dataset.valor;
    if (oldValue !== undefined) {
      console.log(oldValue);
     }
    
   
    let newValue =  parseFloat(e.target.value.replaceAll('.', ''));
   // let newValue = e.target.value.replaceAll('.', '');

//    newValue = newValue.replaceAll(',', '.');


    if (oldValue === newValue && omision === "N") {     
      return;
    }
   rowSeleccionado++;
   
 //	 $(input).blur();
  //  $(input).focus();
  // changeValue(e);
   if(foco != "" ){
  	 foco.children[0].focus();
   }
  }
}

function focusValue(e) {
console.log("focusValue ")
  let input = e.target;
  let oldValue = input.dataset.valor;
   if (oldValue !== undefined) {
    console.log(oldValue);
  }
}

function actualizarValNumerico(inputElement, nroOrden) {
	
let texto = "";

//agregado por cristian 30-01  para enviar nombre de glosa******************
if(inputElement.dataset['tipoResultado'] === "G"){
	let selectedOption = inputElement.options[inputElement.selectedIndex];
	 texto = selectedOption.textContent;
}

//agregado por cristian para que no entre a grabar cuando texto es Seleccione.
if(texto != "Seleccione" ){
//***************************************** */

  let valor = inputElement.value;  
 
  let nOrden = nroOrden;
  let idExamen = inputElement.dataset['idexamen'];
  let idTest = inputElement.dataset['idtest'];
  let codigoTr = inputElement.dataset['tipoResultado'];
  let obligado = inputElement.dataset['obligado'];
  let omision = inputElement.dataset['omision'];
  
  
  let rowId = nOrden.toString().concat('-').concat(idExamen).concat('-').concat(idTest);
  let url = gCte.getctx + "/api/orden/update/examen/test/resultado";
  let postData = JSON.stringify({
    "df_NORDEN": nOrden,
    "dfe_IDEXAMEN": idExamen,
    "dfet_IDTEST": idTest,
    "dfet_RESULTADO": valor,
    "ctr_CODIGO": codigoTr,
    "textResultado" : texto

  });
  let fnExito = function(result, status, xhr) {
    let respuesta = result;
    if (respuesta.status == 200) {    
      tableResultadosExamenesOrden.row("#".concat(rowId)).data(result.dato.resultadoExamen);
      
      if (result.dato.resultadoExamen.ctr_CODIGO ==='G'){
        
        let selectCur = $(`select[data-idtest="${result.dato.resultadoExamen.dfet_IDTEST}"][data-idexamen="${result.dato.resultadoExamen.dfe_IDEXAMEN}"]`);
        const estadoResultado = result.dato.resultadoExamen.cert_DESCRIPCIONESTADOTEST;
          selectCur.val(valor);

        if ((valor === null || valor === '') && obligado === 'S') {
          valor = omision;
          selectCur.val(valor);
        }

        if (estadoResultado === 'INGRESADO' || estadoResultado === 'FIRMADO') {
          selectCur.val(valor);
          selectCur.prop('disabled',true);
        }
        addEventsElements(selectCur);
      }else if(result.dato.resultadoExamen.ctr_CODIGO ==='N'){
        let inputs = $(`input[data-idtest="${result.dato.resultadoExamen.dfet_IDTEST}"][data-idexamen="${result.dato.resultadoExamen.dfe_IDEXAMEN}"]`);
        addEventsElements(inputs);
        actualizaTotal();
      }
      
      handlerMessageOk(respuesta.message);
    }
    else if (respuesta.status == 503) {
      tableResultadosExamenesOrden.ajax.reload(null, false);
      tableInfoExamenesOrden.ajax.reload(null, false);
      handlerMessageWarning(respuesta.message);
    }
    else {
      handlerMessageError(respuesta.message);
    }
    //    searchResultadosAnulados();

  };

  $.ajax({
    type: "POST",
    url: url,
    data: postData,
    success: fnExito,
    error: fracaso,
    contentType: 'application/json; charset=utf-8'
  });

}
}



function exito(result, status, xhr) {

  let respuesta = result;
  if (respuesta.status == 200) {
    tableResultadosExamenesOrden.ajax.reload(null, false);
    tableInfoExamenesOrden.ajax.reload(null, false);
    handlerMessageOk(respuesta.message);
  }
  else if (respuesta.status == 503) {
    tableResultadosExamenesOrden.ajax.reload(null, false);
    tableInfoExamenesOrden.ajax.reload(null, false);
    handlerMessageWarning(respuesta.message);
  }
  else {
    handlerMessageError(respuesta.message);
  }
  //searchResultadosAnulados();
}

function fracaso(xhr, status, error) {
  console.table(error);
  handlerMessageError(error);
}


function disable(id) {
  $(id).val("");
}

function disableSel(id) {
  $(id).val("-1");
}


function enable(id) {
  $(id).prop("readonly", "");

}

/****************************************  Generacion de celdas cuando se carga o recarga el datatable de resultados. ********************************************************* */



function genInputRespuesta(data, type, row, meta) {

  if (row.ct_ESCULTIVO === 'S') {
    return genResBloqueado(row);
    ;
  }

  if(row.cert_IDESTADORESULTADOTEST == '4'){
	
	 return genEstadoBloqueado(row);
}
 
  switch (row.ctr_CODIGO) {
    case '0':
      return genResTexto(row);
    case 'G':
      return genResGlosa(row);
    case 'GM':
      return genResGlosaMultiple(row);
    case 'N':
      return genResNumerico(row);
    case 'T':
      return genResTexto(row);
    case 'F':
      return genResFormula(row);
    case 'I':
      return genResImagen(row);
    default:
      break;
  }

  handlerMessageError('');
  return '';
}

function genResBloqueado(row) {
  let rpta = `<input disabled type='text' data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}" data-tipo-resultado="T" class='form-control' value='CULTIVO' style="width:${t1*14}px"/>`;
  return rpta;
}


function genEstadoBloqueado(row) {
  let rpta = `<input disabled type='text' data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}" data-tipo-resultado="T" class='form-control' value='' style="width:${t1*14}px"/>`;
  return rpta;
}

function genResTexto(row) {
  let valor = "";
  let rpta ="";
  if(row.dfet_RESULTADO == null && row.ct_RESULTADOOMISION != null){
	valor = row.ct_RESULTADOOMISION;
	}else{
		if(row.dfet_RESULTADO != null && row.dfet_RESULTADO != ""){
     valor = row.dfet_RESULTADO;
  		}
	}
   if ( row.cert_DESCRIPCIONESTADOTEST === 'INGRESADO' ||  row.cert_DESCRIPCIONESTADOTEST === 'FIRMADO'  ) {
 		  rpta = `<input  disabled data-valor="${valor}" type='text' data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}" data-tipo-resultado="T" class='form-control' value='${valor}'  style="width:${t1*14}px"/>`;
 	}else{
	  rpta = `<input  type='text' data-valor="${valor}" data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}" data-tipo-resultado="T" class='form-control' value='${valor}'  style="width:${t1*14}px"/>`;
	}
  return rpta;
}

/*
  Genera select con el resultado recuperado de la tabla DATOSFICHASEXAMENESTEST 
 */
function genResGlosa(row) {
  const estadoResultado = row.cert_DESCRIPCIONESTADOTEST;

// se cambia en data-value "${row.dfet_RESULTADO}" por  ${row.cg_IDGLOSA} -- ya no viene el id en resultado de las glosas, ahora viene el nombre de glosa
//cambiado por cristian 31-01 *******************
 // let rpta = `<select data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="G" class='form-control' data-value= "${row.dfet_RESULTADO}" data-obligado="${row.ct_RESULTADOOBLIGADO}" data-omision="${row.ct_RESULTADOOMISION}">    </select>`;
let rpta = `<select data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="G" class='form-control' data-value= "${row.cg_IDGLOSA}" data-obligado="${row.ct_RESULTADOOBLIGADO}" data-omision="${row.dfet_RESULTADO}" style="width:${t1*14}px">    </select>`;
  if (estadoResultado === 'INGRESADO' || estadoResultado === 'FIRMADO'  ) {
		if((row.dfet_RESULTADO === null || row.dfet_RESULTADO === '') && row.ct_RESULTADOOMISION != null){
			rpta = `<select selected disabled data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="G" class='form-control' data-value= "${row.cg_IDGLOSA}" style="width:${t1*14}px">
	   </select>`;
		}else{
	    rpta = `<select disabled data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="G" class='form-control' data-value= "${row.cg_IDGLOSA}" style="width:${t1*14}px">
	   </select>`;
   
 	 }
}
  if ((row.dfet_RESULTADO === null || row.dfet_RESULTADO === '') && row.ct_RESULTADOOBLIGADO === 'S') {  
	if(estadoResultado === 'INGRESADO' || estadoResultado === 'FIRMADO'){
			rpta = `<select selected disabled data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="G" class='form-control' data-value= "${row.cg_IDGLOSA}" style="width:${t1*14}px" >
	   </select>`;
		}else{
	   	 rpta = `<select selected data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="G" class='form-control' data-value= "${row.cg_IDGLOSA}" style="width:${t1*14}px">
	  	 </select>`;
   }
  }

  return rpta;
}

function genResGlosaMultiple(row) {
  const estadoResultado = row.cert_DESCRIPCIONESTADOTEST;
  let rpta;
  let valor = "";
  if(row.dfet_RESULTADO == null && row.ct_RESULTADOOMISION != null){
	row.dfet_RESULTADO = row.ct_RESULTADOOMISION;
}
  if(row.dfet_RESULTADO !== null){
    let substrings = row.dfet_RESULTADO.split(",").slice(0, 2).join(",");
    valor = substrings;
    if(row.cert_DESCRIPCIONESTADOTEST === "INGRESADO" || row.cert_DESCRIPCIONESTADOTEST === "FIRMADO"  ){
		rpta = `
	    <button disabled data-norden="${row.df_NORDEN}" data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="GM" class='form-control' title="${row.dfet_RESULTADO}" data-value= "${row.dfet_RESULTADO}" style="width:${t1*14}px">${valor}</button>`;
		}else{
			rpta = `
		    <button data-norden="${row.df_NORDEN}" data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="GM" class='form-control' title="${row.dfet_RESULTADO}" data-value= "${row.dfet_RESULTADO}" style="width:${t1*14}px">${valor}</button>`;
		}
    
  }else{
	if(row.cert_DESCRIPCIONESTADOTEST === "INGRESADO" || row.cert_DESCRIPCIONESTADOTEST === "FIRMADO"  ){
     	rpta = `<button disabled data-norden="${row.df_NORDEN}" data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="GM" class='form-control' data-value= "${row.dfet_RESULTADO}" style="width:${t1*14}px">Ver resultados</button>`;
	  }else{
		rpta = `<button data-norden="${row.df_NORDEN}" data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-resultado="GM" class='form-control' data-value= "${row.dfet_RESULTADO}" style="width:${t1*14}px">Ver resultados</button>`;
	 }
}
  return rpta;
}



function genResNumerico(row) {

let estadoResultado =row.cert_DESCRIPCIONESTADOTEST;

  if(row.dfet_RESULTADO == null && row.ct_RESULTADOOMISION != null){				
		row.dfet_RESULTADONUMERICO = row.ct_RESULTADOOMISION;
	}
  let rpta="";
  let nroFormateado = (row.dfet_RESULTADONUMERICO === null || row.dfet_RESULTADONUMERICO === 'null') ? '' : new Intl.NumberFormat('es-ES').format(row.dfet_RESULTADONUMERICO);
console.log(nroFormateado + "   numero formateado ***************")
nroFormateado = nroFormateado.replaceAll('.', '')
 if(row.dfet_RESULTADO === null && row.ct_RESULTADOOMISION != null){	
  	 rpta = `<input type='text' data-resultado-omision ="S" data-valor="${row.dfet_RESULTADONUMERICO}"  data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}" data-tipo-valor="V" data-tipo-resultado="N" data-n-decimales="${row.ct_DECIMALES}" class='form-control' value='${nroFormateado}' style="width:${t1*14}px"/>`;
 }else{
	 rpta = `<input type='text'  data-resultado-omision ="N" data-valor="${row.dfet_RESULTADONUMERICO}"  data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}" data-tipo-valor="V" data-tipo-resultado="N" data-n-decimales="${row.ct_DECIMALES}" class='form-control' value='${nroFormateado}' style="width:${t1*14}px"/>`;
}
 
  //Se añade condición si el usuario no tiene permiso para editar resultados críticos --> Marco Caracciolo 5/12/5022
  if (estadoResultado === 'INGRESADO' || estadoResultado === 'FIRMADO' || ($("#editaResultadosCriticos").length && $("#editaResultadosCriticos").val() == "N")) {
   // rpta = `<input disabled type='text' data-valor="${row.dfet_RESULTADONUMERICO}" data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}" data-tipo-resultado="N" class='form-control' value='${nroFormateado} '/>`;data-n-decimales="${row.ct_DECIMALES}"
	
  	 rpta = `<input disabled type='text' data-valor="${row.dfet_RESULTADONUMERICO}" data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}" data-tipo-valor="V" data-tipo-resultado="N" data-n-decimales="${row.ct_DECIMALES}" class='form-control' value='${nroFormateado} ' style="width:${t1*14}px"/>`;
  }

  return rpta;
}

function genResFormula(row) {
  //Se añade condición si el usuario no tiene permiso para editar resultados críticos --> Marco Caracciolo 5/12/5022
  let rpta = '';
  let valor = "";
  if(row.dfet_RESULTADO != null && row.dfet_RESULTADO != ""){
    valor = row.dfet_RESULTADO.replace(",", ".");
    valor = parseFloat(valor).toFixed(row.ct_DECIMALES)
    valor = valor.replace(".", ",")    
  }
  if ($("#editaResultadosCriticos").length && $("#editaResultadosCriticos").val() == "N") {
    rpta = `<input disabled type='text' data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}" data-tipo-total="T" data-tipo-resultado="F"  data-n-decimales="${row.ct_DECIMALES}" class='form-control' value='${valor}' style="width:${t1*14}px"/>`;
  } else {
    rpta = `<input disabled  type='text' data-idtest="${row.dfet_IDTEST}" data-idexamen="${row.dfe_IDEXAMEN}"  data-tipo-total="T" data-tipo-resultado="F"  data-n-decimales="${row.ct_DECIMALES}" class='form-control' value='${valor}' style="width:${t1*14}px"/>`;
  }
  return rpta;
}

function genResImagen(row) {
  let rpta = `<input  disabled type='file' data-idtest="${row.dfet_IDTEST}" data-idexamen=""${row.dfe_IDEXAMEN}"  data-tipo-resultado="I" class='form-control' value='${row.dfet_RESULTADO}' style="width:${t1*14}px"/>`;
  return rpta;
}


function genValoresReferencia(data, type, row, meta) {
  // let rpta = '<span class="text-nowrap"  onclick="iniciarLineaTiempo(' + row.df_NORDEN + ',' + row.dfe_IDEXAMEN + ',' + row.dfet_IDTEST + ' )" > ' + genValorRef(row) + '</span>';
  let rpta = `<div style="width:${t1*5}px"><span class="text-nowrap">${genValorRef(row)}</span></div>`;
  return rpta;
}


function genLabel(data, type, row, meta) {	
  const color = estadoTestYResultadosColorMap[row.cert_DESCRIPCIONESTADOTEST];
  const estado = row.cert_DESCRIPCIONESTADOTEST;
   
  if (estado !== "PENDIENTE" && estado !== "BLOQUEADO") {
	
    return  `<div style="width:${t1*9}px"><span class="label label-lg font-weight-bold label-inline estadoTestBadge-${color}" >${row.cert_DESCRIPCIONESTADOTEST}</span></div>`;
    //Cuando la muestra está rechazada o está tomada y recepcionada
  } else{
		 return cargarEstadosTestYResultados(estado, row);
  } 
  
  
  /*
  else {  			// agregado por cristian 31-01  para colocar estado digitado si es que llega resultado por omision y no tenga resultado ya agregado
	if(row.dfet_RESULTADO == null && row.ct_RESULTADOOMISION  != null && estado == "PENDIENTE" && row.ctr_CODIGO !="N"){
		let colorDigitado = estadoTestYResultadosColorMap["DIGITADO"]
		 return `<span class="label label-lg font-weight-bold label-inline estadoTestBadge-${colorDigitado}" >DIGITADO</span>`;
	}else{
		 return cargarEstadosTestYResultados(estado, row);
	}
  
  }
  */
  //  let rpta = '<span class="text-nowrap">' + row.cert_DESCRIPCIONESTADOTEST + '</span>';
  //  return rpta;
}

function genUnidad(data, type, row, meta) {
  let texto = row.cum_DESCRIPCION === 'Sin especificar' ? '' : row.cum_DESCRIPCION;
  let rpta = `<div style="width:${t1*4}px"><span class="text-no-wrap">${texto}</span></div>`;
  return rpta;
}


function genHistoria(data, type, row, meta) {
let fecha =new Date();
let fechaTexto ="";

if(row.dfet_FECHAULTIMORESULTADO != null){	
	 fecha = new Date(row.dfet_FECHAULTIMORESULTADO )
	fechaTexto = convertirDateDDMMYYYY(fecha)
}



  let resHistorico = row.dfet_ULTIMORESULTADOANT + " (" +fechaTexto+")"  
  if(resHistorico === null+" "+"()"){
	resHistorico= "-1"
}

  if(resHistorico == "-1" && row.ctr_CODIGO === "N"){
	resHistorico = "*"
  }else{
	if(resHistorico == "-1"){
		resHistorico = "null";		
	}
}
  let rpta = '<span class="font-weight-bold label-inline pointer" data-toggle="tooltip" data-html="true" title="' + (resHistorico == "null" ?  " " :   resHistorico ) +'"  onclick="iniciarHistorico(' + row.df_NORDEN + ',' + row.dfe_IDEXAMEN + ',' + row.dfet_IDTEST + ' )">' + (resHistorico == "null" ?  " " :   resHistorico ) + '</span>';
  if (row.resultadoHistorico == null || row.ctr_CODIGO != "N") {
    rpta = '<span class="font-weight-bold label-inline pointer" onclick="iniciarHistorico(' + row.df_NORDEN + ',' + row.dfe_IDEXAMEN + ',' + row.dfet_IDTEST + ' )"> </span>';
  }
  return `<div style="width:${t1*8}px">${rpta}</div>`;
}

/******************************************************************************************************************************** */

function formatHistoria(resultadoHistorico) {


  if (resultadoHistorico === null || resultadoHistorico === undefined) {
    return "*";
  }

  let partes = resultadoHistorico.split(',');
  if (partes[0] === null || partes[0] === undefined) {
    return "*";
  }
  partes = partes[0].split('|');
  if (partes[1] === null || partes[1] === undefined || partes[1] === 'null') {
    return "*";
  }
  const valorRes = partes[0];
  partes = partes[1].split(' ');
  if (partes[0] === null || partes[0] === undefined || partes[0] === 'null') {
    return "*";
  }

  let visualHistorico = valorRes.concat(' ').concat("(" + YYYY_MM_DD2DD_MM_YY_format(partes[0], '-', '/') + ")");
  return visualHistorico;

}

function genMuestras(data, type, row, meta) {
  if (row.dfet_IDMUESTRA == null) return "";
  // let rpta = '<span class="label label-lg font-weight-bold label-inline">' + row.dfm_CODIGOBARRA + '</span>';
  let rpta = row.dfm_CODIGOBARRA;
  return rpta;
}

function genCell(data, type, row, meta) {
	let rpta="";  
	if(row.metodo != null){
   		//rpta = "<input type='text' disabled class='form-control' value='"+row.metodo +"' data-toggle='tooltip'  title='"+row.metodo+"'/>";
   		rpta =`<div style="width:${t1*8}px"><span class='font-weight-bold' title='${row.metodo}' >${largoTexto(row.metodo, 12)}</span></div>`;
  	}else{
		//rpta = "<input type='text' disabled class='form-control' value='***'/>";
		rpta = `<div style="width:${t1*8}px"><span class='font-weight-bold' > **** </span></div>`
	}
  return rpta;
  
}

function genSec(data, type, row, meta) {
  let rpta = `<input type='text' disabled class='form-control' value=${row.ct_IDSECCION}>`;
  return rpta;
}

function genNulo(data, type, row, meta) {
  let rpta = `<input type='text' disabled class='form-control' value=${row.dfe_EXAMENANULADO}>`;
  return rpta;
}



function genDeltaCheck(data, type, row, meta) {
  let lst = row.deltaCheckLst;
  let nOrden = row.df_NORDEN;
  let rpta = '';
  let min = "";
  let max = "";
  let promedio = "";
  let deltaCheckValue;
  let deltaCheckRango;

  if (lst !== null && lst.length > 0) {
    lst.forEach(e => {
      if (e.df_NORDEN === nOrden) {
        deltaCheckValue = e.promedio;
        min = e.valormin;
        max = e.valormax;
        deltaCheckRango = e.ct_DELTAPORCENTAJE;
      }
    }
    );

    let icono = "";
    if (deltaCheckValue === undefined || deltaCheckRango === undefined) {
      deltaCheckValue = "";
      icono = "";
    }
    else if (deltaCheckValue > deltaCheckRango) {
	
      icono = '<i class="fas fa-arrow-up text-danger"></i>';
    }
    //else if (deltaCheckValue < -1 * deltaCheckRango) { cambiado por cristian 31-01
	 else if (deltaCheckValue <  deltaCheckRango) {
      icono = '<i class="fas fa-arrow-down text-danger"></i>';
    }


    rpta = icono;
  }

  return rpta;
}

function genDeltaCheckV2(data, type, row, meta) {

  let delta = row.deltaCheckCalculated;
 
  if (delta === undefined || delta === null || delta === '') return '';
  let nOrden = row.df_NORDEN;
  let rpta = '';
  let min = delta.valormin;
  let max = delta.valormax; 
  let deltaCheckValue = delta.dfet_RESULTADONUMERICO;
 // let deltaCheckRango = delta.ct_DELTAPORCENTAJE * promedio / 100;
  let toolTip = `title="${min} - ${max}"`;
  let icono = "";

  if (deltaCheckValue === undefined || deltaCheckValue == null || (min == null && max == null ) ) {
    deltaCheckValue = "";
    icono = "";

  }
  
  else if (deltaCheckValue > max) {		
    icono = `<i class="fas fa-arrow-up text-danger" data-toggle="tooltip" ${toolTip}  ></i> `;
  }
	else if (deltaCheckValue < min) {
    icono = `<i class="fas fa-arrow-down text-danger " data-toggle="tooltip"  ${toolTip} ></i> `;
  }

  rpta = icono;
  return `<div style="width:${t1*3}px">${rpta}</div>`;
}

function genAlerta(data, type, row, meta) {
  let rpta;
  let val;

   val = row.dfet_RCRITICO;
  switch (val) {
    case 'N':
      rpta = '';
      break;
    case 'B':
      rpta = '<i class="fas fa-arrow-down text-danger"></i>';
      break;
    case 'CB':
      rpta = '<i class="fas fa-arrow-down text-danger"></i>';
      rpta = rpta + '<i class="fas fa-exclamation-triangle text-danger"></i>';
      break;
    case 'A':
      rpta = '<i class="fas fa-arrow-up text-danger"></i>';
  	case 'S':
      rpta = '<i class="fas fa-arrow-up text-danger"></i>';
      break;
    case 'CA':
      rpta = '<i class="fas fa-arrow-up text-danger"></i>';
      rpta = rpta + '<i class="fas fa-exclamation-triangle text-danger"></i>';
      break;
    default:
      rpta = '';
      break;
  }

  return  `<div style="width:${t1*3}px">${rpta}</div>`;
}

function getTipoValorAlerta(val) {
  let rpta = "";
  switch (val) {
    case 'B':
      rpta = 'anormal';
      break;
    case 'CB':
      rpta = 'critico';
      break;
    case 'A':
      rpta = 'anormal';
      break;
    case 'CA':
      rpta = 'critico';
      break;
    default:
      break;
  }

  return rpta;
}




function genValorRef(row) {

  let desde = row.dfet_REFERENCIADESDE === null ? '<' : row.dfet_REFERENCIADESDE;
  let hasta = row.dfet_REFERENCIAHASTA === null ? '>' : row.dfet_REFERENCIAHASTA;
  if(row.dfet_REFERENCIADESDE === null || row.dfet_REFERENCIAHASTA === null){
	if(row.dfet_REFERENCIADESDE === null & row.dfet_REFERENCIAHASTA === null){
		return ` `;
	}else{
		return `${desde} ${hasta}`;
	}
	 
}else{
	 return `${desde}-${hasta}`;
}
 
}
/************** Tipos de resultado **********************************
Tipos de resultado
0 SIN ESPECIFICAR
G GLOSA
N NUMÉRICO
T TEXTO
F FÓRMULA
I IM�?GEN
GM GLOSA MÚLTIPLE
*********************************************************************/


function obtenerResultadoModificado(pTableResultadosExamenesOrden) {

  function tuplaResultadoExamen() {
    this.df_NORDEN = -1;
    this.dfe_IDEXAMEN = -1;
    this.dfet_IDTEST = -1;
    this.dfet_RESULTADO = "";
  }

  let inputs = $("input[data-tipo-resultado]");
  const nInputs = inputs.length;

  for (let i = 0; i < nInputs; i++) {

    console.log(inputs[i].value);
    console.table(inputs[i].dataset['tipoResultado']);

  }

  const resultadosSeleccionados = pTableResultadosExamenesOrden.rows({ selected: true }).data();
  if (resultadosSeleccionados === undefined || resultadosSeleccionados === null) {
    handlerMessageError('Revisar selección de resultados.');
    return false;
  }
  const n = resultadosSeleccionados.count();
  if (n === 0) {
    handlerMessageError('Verifique si ha seleccionado resultados.');
    return false;
  }
  let idSeleccionados = new Array();
  for (let i = 0; i < n; i++) {
    let tupla = new tuplaResultadoExamen();
    tupla.df_NORDEN = resultadosSeleccionados[i].df_NORDEN;
    tupla.dfe_IDEXAMEN = resultadosSeleccionados[i].dfe_IDEXAMEN;
    tupla.dfet_IDTEST = resultadosSeleccionados[i].dfet_IDTEST;
    tupla.dfet_RESULTADO = resultadosSeleccionados[i].dfet_RESULTADO;
    idSeleccionados.push(tupla);
  }

  return idSeleccionados;
}

function getDominiosExamenes(selects) {
  let url = gCte.getctx + '/api/orden/' + nroOrdenSeleccionada + '/examenes/resultados/dominios';

  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(paramResultadosExamenesSeleccionados),
    success: processResponse,
    error: handlerMessageError,
    contentType: 'application/json; charset=utf-8'
  });
}


function processResponse(data) {
  let lst = setDominiosExamenes(data);
  if (lst !== undefined) {
    fillSelects(globalselects, lst);
  }
}

function setDominiosExamenes(responseTxt) {

  if (responseTxt.dato === undefined) return;

  let listaExamenesGlosas = responseTxt.dato;

  const nExamenes = listaExamenesGlosas.length;
  let listaGlosasXIdTest = new Array();

  for (let i = 0; i < nExamenes; i++) {

    const lstGlosaTest = responseTxt.dato[i].lstglosas;
    const n = lstGlosaTest.length;

    if (n === 0) {
      continue;
    }

    let curIdTest = -1;
    let j = 0;
    let item = { "idTest": -1, "lstGlosa": null };
    for (let i = 0; i < n; i++) {
      let glosaItem = lstGlosaTest[i];

      if (glosaItem.ct_IDTEST !== curIdTest) {
        j = 0;
        let lstGlosa = new Array();
        item = { "idTest": glosaItem.ct_IDTEST, "lstGlosa": lstGlosa };
        curIdTest = glosaItem.ct_IDTEST;
        listaGlosasXIdTest.push(item);
      }
      item.lstGlosa.push(glosaItem);
      j++;
    }
  }
  return listaGlosasXIdTest;
}


function getLstGlosasxIdtest(lst, idTest) {

  const n = lst.length;
  for (let i = 0; i < n; i++) {

    if (lst[i].idTest == idTest) {
      return lst[i].lstGlosa;
    }
  }
  return [];
}

function fillSelects(selects, listaGlosasXIdTest) {
  const nSelects = selects.length;

  for (let i = 0; i < nSelects; i++) {
    let idTest = selects[i].dataset['idtest'];
    let lst = getLstGlosasxIdtest(listaGlosasXIdTest, idTest);
    const n = lst.length;
    console.log(selects[i].dataset['value']);
    let opt0 = document.createElement("option");
    opt0.value = '';
    opt0.text = 'Seleccionar';
    selects[i].add(opt0);
    for (let j = 0; j < n; j++) {
      let opt = document.createElement("option");
      opt.value = lst[j].cg_IDGLOSA;
      opt.text = lst[j].cg_DESCRIPCION;

      if (opt.value == selects[i].dataset['value'] || opt.text == selects[i].dataset['value']) {
        opt.selected = 'selected;'
        console.log('Le apunto ' + selects[i].dataset['value']);
      }
      selects[i].add(opt);
    }
  }
}

function getDataTestNotification() {

  let notification = new Object();
 // let idNotification = $("#notificacionResultadosModal").data("test");
  notification.result = $("#idTestCanvasNotificationsResult").text();
  notification.criticalResult = $("#idTestCanvasNotificationsCriticalResult").text();
  notification.physician = $("#idTestCanvasNotificationsPhysician").text();
  notification.patient = $("#idTestCanvasNotificationsPatient").text();
  notification.patientPhone = $("#idTestCanvasNotificationsPatientPhone").text();
  notification.physicianPhone = $("#idTestCanvasNotificationsPhysicianPhone").text();
  notification.patientEmail = $("#idTestCanvasNotificationsPatientEmail").text();
  notification.physicianEmail = $("#idTestCanvasNotificationsPhysicianEmail").text();
  notification.message = $("#idTestCanvasNotificationsMesage").val();
  notification.nroOrden = $("#nOrden").val();//idNotification.idOrden;
  notification.idExamen = $("#examenId").val(); //idNotification.idExamen;
  notification.idTest = $("#testId").val(); //idNotification.idTest;
  notification.notificationsSendTo = $("#idTestCanvasNotificationsSendTo").val();

  return notification;
}

function getDataTestNotificationMultiples(id) {
  let counter = parseInt(id.match(/\d+/));
  counter = isNaN(counter) ? "" : counter;

  let notification = new Object();
  //let idNotification = $(`#notificacionResultadosModal`).data(`test`);
  notification.result = $(`#idTestCanvasNotificationsResult`).text();
  notification.criticalResult = $(`#idTestCanvasNotificationsCriticalResult`).text();
  notification.physician = $(`#idTestCanvasNotificationsPhysician`).text();
  notification.patient = $(`#idTestCanvasNotificationsPatient`).text();
  notification.patientPhone = $(`#idTestCanvasNotificationsPatientPhone`).text();
  notification.physicianPhone = $(`#idTestCanvasNotificationsPhysicianPhone`).text();
  notification.patientEmail = $(`#idTestCanvasNotificationsPatientEmail`).text();
  notification.physicianEmail = $(`#idTestCanvasNotificationsPhysicianEmail`).text();
  notification.message = $(`#idTestCanvasNotificationsMesage${counter}`).val();
  notification.notificationsSendTo = $(`#idTestCanvasNotificationsSendTo${counter}`).val();
  notification.nroOrden = $(`#nOrden`).val();//idNotification.idOrden;
  notification.idExamen = $(`#examenId`).val(); //idNotification.idExamen;
  notification.idTest = $(`#testId`).val(); //idNotification.idTest;

  return notification;
}

function showTestNotification(notification) {
  $("#idTestCanvasNotificationsResult").text(notification.result);
  $("#idTestCanvasNotificationsCriticalResult").text(notification.criticalResult);
  $("#idTestCanvasNotificationsPhysician").text(notification.physician);
  $("#idTestCanvasNotificationsPatient").text(notification.patient);
  $("#idTestCanvasNotificationsPatientPhone").text(notification.patientPhone);
  $("#idTestCanvasNotificationsPhysicianPhone").text(notification.physicianPhone);
  $("#idTestCanvasNotificationsPatientEmail").text(notification.patientEmail);
  $("#idTestCanvasNotificationsPhysicianEmail").text(notification.physicianEmail);
}

function bloquearTest() {
  const examenes = obtenerResultadosSeleccionados(tableResultadosExamenesOrden);
  console.log('bloquearTest' + examenes.length);
}
function retirarFirma() {
  const examenes = obtenerResultadosSeleccionados(tableResultadosExamenesOrden);
  console.log("datos de examenes")
  console.log(examenes)
  if (examenes === false) {
    handlerMessageError("Seleccionar al menos un Test");
    return;
  }
  for (let i = 0; i < examenes.length; i++) {
          if (examenes[i].cert_DESCRIPCIONESTADOTEST !== "FIRMADO" && examenes[i].cert_DESCRIPCIONESTADOTEST !== "BLOQUEADO") {
            handlerMessageError("Los resultados escogidos deben estar en estado Firmado");
            return;
          }

        }
  tableInfoExamenesOrden.ajax.reload();

  //actionTest("RETIRARFIRMA", examenes[0]);
 actionListaTest("RETIRARFIRMA", examenes);

}


function firmarResultados() {
    //Permiso de firmar resultados añadido por Marco Caracciolo 02/12/2022
    if ($("#firmaResultadosTest").length && $("#firmaResultadosTest").val() == "S"){
        const examenes = obtenerResultadosSeleccionados(tableResultadosExamenesOrden);
        if (examenes === false) {
          handlerMessageError("Seleccionar al menos un Test");
          return;
        }
        console.log("examenes para firmar *********************")
         console.log(examenes)
         
      
        for (let i = 0; i < examenes.length; i++) {
          if (examenes[i].cert_DESCRIPCIONESTADOTEST !== "DIGITADO" && examenes[i].cert_DESCRIPCIONESTADOTEST !== "TRANSMITIDO" && examenes[i].cert_DESCRIPCIONESTADOTEST !== "BLOQUEADO") {
            handlerMessageError("Los resultados escogidos deben estar en estado Transmitido o Digitado");
            return;
          }

        }
        tableInfoExamenesOrden.ajax.reload();
        actionListaTest("FIRMAR", examenes);
    } else {
        handlerMessageError("Usuario no está autorizado para firmar resultados.")
    }
}

function cambiarEstado(elemento, nOrden, idExamen, idTest, estado) {

  console.table(elemento);
  if (estado !== elemento.value) {
    let examen = new Object();
    examen.df_NORDEN = nOrden;
    examen.dfe_IDEXAMEN = idExamen;
    examen.dfet_IDTEST = idTest;
    examen.dfet_RESULTADO = "";

    if (elemento.value === "BLOQUEADO") {
      examen.cert_DESCRIPCIONESTADOTEST = estado;
      actionTest("BLOQUEAR", examen);
    }
    else if (elemento.value === "PENDIENTE") {

      actionTest("DESBLOQUEAR", examen);
    }
  }
}

function setSelect(json, fieldName, index) {
  if (json !== null && json !== undefined) {
    if ($("select[data-colexa-index=" + index + "]").length !== undefined && $("select[data-colexa-index=" + index + "]").length > 0) {
      let n = $("select[data-colexa-index=" + index + "]")[0].options.length;
      for (let i = 0; i < n; i++) {
        $("select[data-colexa-index=" + index + "]")[0].remove(n - i - 1);
      }
      let muestras = genOpcionesDisponibles(json.dato, fieldName);
      let opc = new Option('TODOS', '');
      $("select[data-colexa-index=" + index + "]")[0].append(opc);
      muestras.forEach(m => {
        console.log('Muestra: ' + m); opc = new Option(m, m); $("select[data-colexa-index=" + index + "]")[0].append(opc);
      });
    }
  }
}

function actionTest(action, examenSeleccionado) {

  let url = gCte.getctx + '/api/test/action/' + action;
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(examenSeleccionado),
    success: exito,
    error: handlerMessageError,
    contentType: 'application/json; charset=utf-8'
  });
}

function actionListaTest(action, examenes) {
  let url = gCte.getctx + '/api/test/action/list/' + action;


  let fnExitoAction = function(result, status, xhr) {
    let respuesta = result;
    tableResultadosExamenesOrden.rows({ selected: true }).deselect();
    if (respuesta.status == 200) {
   
      tableResultadosExamenesOrden.ajax.reload(null, false);
      tableInfoExamenesOrden.ajax.reload(null, false);
      handlerMessageOk(respuesta.message);
    }
    else if (respuesta.status == 503) {
      tableResultadosExamenesOrden.ajax.reload(null, false);
      tableInfoExamenesOrden.ajax.reload(null, false);
      handlerMessageWarning(respuesta.message);
    }
    else {
      handlerMessageError(respuesta.message);
    }

  };

  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(examenes),
    success: fnExitoAction,
    error: handlerMessageError,
    contentType: 'application/json; charset=utf-8'
  });
}

function limpiarTableResultadosExamenesOrden() {
  tableResultadosExamenesOrden.clear();
  tableResultadosExamenesOrden.draw();
}

function cargarEstadosTestYResultados(estado, row) {
  const color = estadoTestYResultadosColorMap[estado];
  let select = `<select id="selectEstadoTestResultados${row.dfet_IDMUESTRA}" class="form-control select-color estadoTestBadge-${color}" onchange="cambiarEstado(this,${row.df_NORDEN},${row.dfe_IDEXAMEN},${row.dfet_IDTEST},'${row.cert_DESCRIPCIONESTADOTEST}')"  style="width:${t1*9}px">`;
  let estados = ["PENDIENTE", "BLOQUEADO"];

  estados.forEach((element) => {
    if (element === estado) {
      select += "<option value=" + element + " selected>" + element + "</option>";
    } else {
      select += "<option value=" + element + ">" + element + "</option>";
    }
  });

  select += '</select>';
  return select;
}


function cleanValue(idOrden, idExamen, idTest) {

  let url = gCte.getctx + "/api/orden/reset/examen/test/resultado";
  let postData = JSON.stringify({
    "df_NORDEN": idOrden,
    "dfe_IDEXAMEN": idExamen,
    "dfet_IDTEST": idTest,
    "dfet_RESULTADO": ""
  });
  $.ajax({
    type: "POST",
    url: url,
    data: postData,
    success: exito,
    error: fracaso,
    contentType: 'application/json; charset=utf-8'
  });

}




function searchResultadosAnulados() {
  let val = 'N|F';
  if (tableResultadosExamenesOrden !== null && tableResultadosExamenesOrden !== undefined){
  let columna = tableResultadosExamenesOrden.column(14);
  console.log('**************Resultados**********************');
  console.table(columna.data());
  //  console.table(tableResultadosExamenesOrden.data());
  console.log('**************Resultados**********************');
  columna.search(val, true, false, false).draw();
  resetOrdenProgress();
  searchResultadosSeccion(gSeccionSeleccionada);
  }
}

function searchResultadosSeccion(seccion) {
  if (tableResultadosExamenesOrden !== null && tableResultadosExamenesOrden !== undefined){
    let columna = tableResultadosExamenesOrden.column(13);
    columna.search(seccion, true, false, false).draw();
    resetOrdenProgress();
  }

}

var gGlosasResultados = null;
function getDatosTest() {

//  let cfgGlosas = new CfgGlosas();
//  cfgGlosas.getAllGlosas();
  
 // if (gGlosasResultados === null ){
    gGlosasResultados = new CfgGlosas();
    gGlosasResultados.getAllGlosasxTest();
 // }
}






/********************************** Para borrar  */



function generarQryString(resultadosSeleccionados) {
  let lstIdResultados = 'pLstExId=';
  let n = resultadosSeleccionados.length;
  for (let i = 0; i < n; i++) {
    lstIdResultados += resultadosSeleccionados[i].idExamen + ',';
  }
  if (resultadosSeleccionados.length > 0) {
    lstIdResultados = lstIdResultados.substr(0, lstIdExamenes.length - 1);
  }
  return lstIdResultados;
}

function getExamenesOrden(filtro) {
  try {
    pTableResultadosExamenesOrden.ajax.reload(); // con POST
  } catch (e) {
    handlerMessageError(e);
  }
}


function setDisablerQueue(enablerQueue) {

  enablerQueue.listen("#txtFInicio", disable);
  enablerQueue.listen("#txtFFin", disable);
  enablerQueue.listen("#txtFInicio", disable);
  enablerQueue.listen("#txtFiltroNombre", disable);
  enablerQueue.listen("#txtFiltroApellido", disable);
  enablerQueue.listen("#selectTipoDocumentoFiltro", disable);
  enablerQueue.listen("#selectTipoAtencionFiltro", disable);
  enablerQueue.listen("#selectServicio", disable);
  enablerQueue.listen("#selectServicio", disable);
  enablerQueue.listen("#txtNDocumento", disable);

}

function setEnablerQueue(enablerQueue) {

  enablerQueue.listen("#txtFInicio", enable);
  enablerQueue.listen("#txtFFin", enable);
  enablerQueue.listen("#txtFInicio", enable);
  enablerQueue.listen("#txtFiltroNombre", enable);
  enablerQueue.listen("#txtFiltroApellido", enable);
  enablerQueue.listen("#selectTipoDocumentoFiltro", enable);
  enablerQueue.listen("#selectTipoAtencionFiltro", enable);
  enablerQueue.listen("#selectServicio", enable);
  enablerQueue.listen("#txtNDocumento", enable);
}

function guardarResultadosExamenesOrden() {
  let resultadosSeleccionados = obtenerResultadosSeleccionados(tableResultadosExamenesOrden);
  let url = gCte.getctx + '/api/orden/update/examenes/resultados';
  let postData = JSON.stringify(resultadosSeleccionados);
  let _responseDataText = "";
  let _jqXHR, _msg, _httpStatus = null;

  $.ajax({
    type: "POST",
    url: url,
    data: postData,
    success: handlerMessageError(_responseDataText),
    error: handlerMessageError(_jqXHR, _msg, _httpStatus),
    contentType: 'application/json; charset=utf-8'
  });
}


$("#seleccionaTodasLosResultados").on("click", function(e) {
  let inputs = $("input[data-colselector-test]");
  if ($(this).is(":checked")) {
    $(inputs).prop('checked', true);
    tableResultadosExamenesOrden.rows().select();
  } else {
    $(inputs).prop('checked', false);
    tableResultadosExamenesOrden.rows().deselect();
    $("#btnFirmaResultados").prop('disabled', true)
    $("#btnRetiraFirma").addClass('disabled')
  
  }

  const resultados = obtenerResultadosSeleccionados2(tableResultadosExamenesOrden);
  if(resultados){
    let existeElemento = resultados.some(function(elemento) {
      return elemento.cert_DESCRIPCIONESTADOTEST !== "TRANSMITIDO" && elemento.cert_DESCRIPCIONESTADOTEST !== "DIGITADO";
    })

    let existeElementoRetirarFirma = resultados.every(function(elemento) {
      return elemento.cert_DESCRIPCIONESTADOTEST === "FIRMADO"
    })
  
    
    if(existeElemento){
      $("#btnFirmaResultados").prop('disabled', true)
    }else{
      $("#btnFirmaResultados").prop('disabled', false)
    }

    if(existeElementoRetirarFirma){
      $("#btnRetiraFirma").removeClass('disabled')
    }else{
      $("#btnRetiraFirma").addClass('disabled')
    }
  }
});



function actualizaTotal(){
	
			$('td input[data-tipo-total="T"]').each(function() {
				
			  let id = $(this).closest('tr').attr('id');
			  let valoresSeparados  = id.split('-');
			  let nOrden = valoresSeparados[0];
			  let idExamen = valoresSeparados[1];
			  let idTest = valoresSeparados[2];
			  let rowId = nOrden.toString().concat('-').concat(idExamen).concat('-').concat(idTest);
			  let url = gCte.getctx + "/api/orden/examen/test/formula"; 
			  let postData = JSON.stringify({
				"df_NORDEN": nOrden,
				"dfe_IDEXAMEN": idExamen,
				"dfet_IDTEST": idTest,
			  });
			  $.ajax({
				type: "POST",
				url: url,
				data: postData,
				success: function(result, status, xhr) {
				let respuesta = result;
				if (respuesta.status == 200) {
					 tableResultadosExamenesOrden.row("#".concat(rowId)).data(result.dato.resultadoExamen);
					}
				},
				error: function(xhr, status, error){
					console.log(error);
				},
				contentType: 'application/json; charset=utf-8'
			  });

			});
		}


    function genColFilterResultados(colIndex, rowFilter) {

      switch (colIndex) {
        case 0:
          genNoFilter(colIndex, rowFilter,3);
          break;
        case 1:
          genNoFilter(colIndex, rowFilter,12);
          break;
        case 2:
          genNoFilter(colIndex, rowFilter,15);
          break;
        case 3:
          genNoFilter(colIndex, rowFilter,3);
          break;
        case 4:
          genNoFilter(colIndex, rowFilter,14);
          break;
        case 5:
          genNoFilter(colIndex, rowFilter,4);
          break;
        case 6:
          genNoFilter(colIndex, rowFilter,5);
          break;
        case 7:
          genNoFilter(colIndex, rowFilter,8);
          break;
        case 8:
          genNoFilter(colIndex, rowFilter,9);
          break;
        case 9:
          genNoFilter(colIndex, rowFilter,3);
          break;
        case 10:
          genNoFilter(colIndex, rowFilter,8);
          break;
        case 11:
          genNoFilter(colIndex, rowFilter,4);
          break;
        case 12:
          genNoFilter(colIndex, rowFilter,5);
          break;
      }
    }


    
function obtenerResultadosSeleccionados2(pTableResultadosExamenesOrden) {

  function tuplaResultadoExamen() {
    this.df_NORDEN = -1;
    this.dfe_IDEXAMEN = -1;
    this.dfet_IDTEST = -1;
    this.dfet_RESULTADO = "";
    this.cert_DESCRIPCIONESTADOTEST = "";
    this.dfet_REFERENCIADESDE;
    this.dfet_REFERENCIAHASTA;
  }

  const resultadosSeleccionados = pTableResultadosExamenesOrden.rows({ selected: true }).data();
  if (resultadosSeleccionados === undefined || resultadosSeleccionados === null) {
    return false;
  }

  const n = resultadosSeleccionados.count();
  if (n === 0) {
    // handlerMessageError('Verifique si ha seleccionado resultados.');
    return false;
  }
  let idSeleccionados = new Array();
  for (let i = 0; i < n; i++) {	
    let tupla = new tuplaResultadoExamen();
    tupla.df_NORDEN = resultadosSeleccionados[i].df_NORDEN;
    tupla.dfe_IDEXAMEN = resultadosSeleccionados[i].dfe_IDEXAMEN;
    tupla.dfet_IDTEST = resultadosSeleccionados[i].dfet_IDTEST;
    
    tupla.dfet_RESULTADO = resultadosSeleccionados[i].dfet_RESULTADO; 
    tupla.cert_DESCRIPCIONESTADOTEST = resultadosSeleccionados[i].cert_DESCRIPCIONESTADOTEST;
    tupla.dfet_REFERENCIADESDE =  resultadosSeleccionados[i].dfet_REFERENCIADESDE;
    tupla.dfet_REFERENCIAHASTA =  resultadosSeleccionados[i].dfet_REFERENCIAHASTA;
    
  //*********************************************** */
    idSeleccionados.push(tupla);
  } 
  return idSeleccionados;
}


