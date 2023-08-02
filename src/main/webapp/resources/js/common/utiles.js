/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Funcion para sacar URL
var getUrlParameter = function getUrlParameter(sParam) {
  var sPageURL = window.location.search.substring(1),
    sURLVariables = sPageURL.split('&'),
    sParameterName,
    i;
  for (i = 0; i < sURLVariables.length; i++) {
    sParameterName = sURLVariables[i].split('=');
    if (sParameterName[0] === sParam) {
      return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
    }
  }
};


function handlerMessageError(mensajeEnviado) {
  let tipoMensaje = "danger";
  if (mensajeEnviado === "ingresadoCorrectamente") {
    tipoMensaje = "success";
    mensajeEnviado = "La operación se realizó con éxito";
  }
  $.notify({
    message: mensajeEnviado
  }, {
    // settings
    element: 'body',
    position: null,
    type: tipoMensaje,
    allow_dismiss: true,
    newest_on_top: false,
    showProgressbar: false,
    placement: {
      from: "top",
      align: "right"
    },
    offset: 20,
    spacing: 10,
    z_index: 1031,
    delay: 5000,
    timer: 1000,
    url_target: '_blank',
    mouse_over: null,
    animate: {
      enter: 'animated fadeInDown',
      exit: 'animated fadeOutUp'
    },
    onShow: null,
    onShown: null,
    onClosed: null
  });

}

function handlerMessageOk(mensajeEnviado) {
  handlerMessage(mensajeEnviado, 'success');
}

function handlerMessageWarning(mensajeEnviado) {
  handlerMessage(mensajeEnviado, 'warning');
}

function handlerResponse401(response) {
  if (response.status === 401) {
    handlerMessageError(err);
    //Matar sesion y login
  }
}


function handlerResponseOk(response) {
  if (response.status === 200) {
    handlerMessageOk(response.message);
  }
  else {
    handlerMessageError(err);
  }
}

function handlerResponseError() {
  handlerMessageError(err);
}

function handlerMessage(mensajeEnviado, tipoMensaje) {
  if (tipoMensaje === undefined || tipoMensaje == null) {
    tipoMensaje = 'error';
  }
  if (mensajeEnviado === "ingresadoCorrectamente") {
    tipoMensaje = "success";
    mensajeEnviado = "La operación se realizó con éxito";
  }
  $.notify({
    message: mensajeEnviado
  }, {
    // settings
    element: 'body',
    position: null,
    type: tipoMensaje,
    allow_dismiss: true,
    newest_on_top: false,
    showProgressbar: false,
    placement: {
      from: "top",
      align: "right"
    },
    offset: 20,
    spacing: 10,
    z_index: 1031,
    delay: 5000,
    timer: 1000,
    url_target: '_blank',
    mouse_over: null,
    animate: {
      enter: 'animated fadeInDown',
      exit: 'animated fadeOutUp'
    },
    onShow: null,
    onShown: null,
    onClosed: null
  });

}

function handlerMessageWarning(mensajeEnviado) {
  handlerMessage(mensajeEnviado, 'warning');
}



function chequearClase(jqId, classId) {

  var classList = $(jqId).attr("class");
  var classArr = classList.split(/\s+/);
  const n = classArr.length;

  for (let i = 0; i < n; i++) {
    if (classArr[i] === classId) {
      return true;
    }
  }
  return false;
}

function filtraTextoNombre(e) {
  let txt = String.fromCharCode(e.which);
  if (!txt.match(/[A-Za-z~\- '\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00f1\u00d1\u00c4\u00cb\u00cf\u00d6\u00dc\u00e4\u00eb\u00ef\u00f6\\u00fc]/)) {
    return false;
  }
}


function filtraTextoCelular(e) {
  let txt = String.fromCharCode(e.which);
  if (!txt.match(/\+56[9|2] [0-9]{8}/)) {
    return false;
  }
}


function cambiarEstadoParrafo(text,id){
  let input = $(`<input id="${id}_id"  type="text" value="${text}"  />`);
   $('#'+id).text('').append(input);
}

function cambiarEstadoParrafobutton(text,id){
  let input = $(`<input id="${id}_id"  type="text" value="${text}"  />`);
   $('#'+id).text('').append(input);
   $('#'+id).parent().find('a').hide();
}

//funciones para acortar textos

let largoTexto = function (texto, largo) {
  let title = "";
  if (typeof texto !== "object" && texto !== null && texto.length >= largo) {	
      title = texto.substr(0, largo);
  	}
  	
  if(texto != null){
	    if(texto.length < largo){
	  		 title = texto;
	    }
  }
    return title != ""
      ? '<span title="' + texto + '">' + title + "</span>"
      : title;
};

let largoTexto2 = function (texto, largo) {
  let title = "";
  if (typeof texto !== "object" && texto !== null && texto.length >= largo) {
      title = texto.substr(0, largo);
  }else{
    title = texto;
  }

  return title != ""
      ? title
      : "";
};


function cutText(text, length) {
  if (text.length > length) {
    let cut = text.substring(0, length);
    let lastSpaceIndex = cut.lastIndexOf(' ');
    let nextLineIndex = cut.indexOf('\n');
    if (lastSpaceIndex !== -1 && lastSpaceIndex > nextLineIndex) {
      cut = cut.substring(0, lastSpaceIndex);
    } else if (nextLineIndex !== -1) {
      cut = cut.substring(0, nextLineIndex);
    }
    return `<span title="${text}">${cut}</span>`;
  } else {
    return text;
  }
}

let largoTextoAutomatico = function (texto,clase) {
  const th = document.querySelector(clase);
  const style = window.getComputedStyle(th);
  const padding = style.padding;
  const paddingArray = padding.split(' ').map(value => parseInt(value));
  let paddingSum = paddingArray.reduce((accumulator, currentValue) => accumulator + currentValue);
  paddingArray.length == 4 ? paddingSum : paddingArray.length == 2 ? paddingSum * 2 : 0;
  const width = parseFloat(style.width);
  const fontSize = parseFloat(style.fontSize);
  const charsPerLine = Math.floor((width + paddingSum) / fontSize);
  let title = "";
  if (typeof texto !== "object" && texto !== null && texto != "") {
      title = texto.substr(0, charsPerLine);
  }

  return title != ""
      ?  title
      : texto;
};

function acortarTexto(texto, porcentaje, tamanoTabla) {
  const longitudTotal = tamanoTabla;
  const longitudAcortada = Math.round((porcentaje / 100) * longitudTotal);

  if (longitudAcortada >= longitudTotal) {
    return texto;
  }

  const textoAcortado = texto.slice(0, longitudAcortada);
  return textoAcortado;
}


//funciones para acortar textos

let calcularHeight = function (jdId,contenedorInferior,datoExtra) {
  let position = $("#" + jdId).offset();
  let contI = 0;
  let tamano = 0;
  if(!$.isArray(contenedorInferior) && $('#'+contenedorInferior).length > 0){
      contI = $('#'+contenedorInferior).height();
  }else if($.isArray(contenedorInferior)){
    contenedorInferior.forEach(element => {
      contI+=$('#'+element).height();
    });
  }
  if(datoExtra === undefined){
    datoExtra = 140;
  }
  tamano = Math.floor(
    screen.height -
        position.top -
        datoExtra -
        (screen.height - (85 / 100) * screen.height) - contI)

  return (
     tamano < 200 ? "200px" : tamano+"px"
  );
};
let calcularHeightSolo = function (jdId,datoExtra) {
  let position = $("#" + jdId).offset();
  let contI = 0;
  let tamano = 0;

  if(datoExtra === undefined){
    datoExtra = 140;
  }
  tamano = Math.floor(
    screen.height -
        position.top -
        datoExtra -
        (screen.height - (85 / 100) * screen.height) - contI)

  return (
     tamano < 100 ? "100px" : tamano+"px"
  );
};

let calcularContentHeightSolo = function (jdId) {
  let position = $("#" + jdId).height();
  console.log(position,jdId);
  let tamano;
  if($("#" + jdId).length == 0){
    tamano = 200;
  }else{
    tamano = Math.floor(
      position - 100)
  }
  
  return (
     tamano < 200 ? "200px" : tamano+"px"
  );

};


let flebotomistaData = function () {
  var jqXHR = $.ajax({
      type: "GET",
      url: getctx + "/api/recepcionMuestras/recepcionistas/list",
      datatype: "json",
      async: false,
  });
  return JSON.parse(jqXHR.responseText);
};


function cambiarDatoRut(rut){
  let value = rut.replace(/\./g, '').replace('-', '');
  
  if (value.match(/^(\d{2})(\d{3}){2}(\w{1})$/)) {
    value = value.replace(/^(\d{2})(\d{3})(\d{3})(\w{1})$/, '$1.$2.$3-$4');
  }
  else if (value.match(/^(\d)(\d{3}){2}(\w{0,1})$/)) {
    value = value.replace(/^(\d)(\d{3})(\d{3})(\w{0,1})$/, '$1.$2.$3-$4');
  }
  else if (value.match(/^(\d)(\d{3})(\d{0,2})$/)) {
    value = value.replace(/^(\d)(\d{3})(\d{0,2})$/, '$1.$2.$3');
  }
  else if (value.match(/^(\d)(\d{0,2})$/)) {
    value = value.replace(/^(\d)(\d{0,2})$/, '$1.$2');
  }
  return rut = value;
}


async function innerJoin({leftArray, rightArray, key}){
  const map = new Map()
  //buscar y rellena el map segun el item encontrado
  leftArray.forEach(item =>{
    map.set(item[key],item)
  });
  console.log(leftArray,"leftArray");
  let join = [];

  rightArray.forEach(rightItem => {
    const leftItem = map.get(rightItem[key.toLowerCase()])
    if(leftItem === undefined) return;
    join.push([...leftItem,...rightItem]);
  });

  return join
}



//convertir blob a imagen
function jpegBase64(imagen) {
  let resultado = ""
  if(imagen != null){
    let blob = `data:image/jpeg;base64,${imagen}`;
    resultado = blob;
  }
  return resultado;
}

function calcularTamanoContenedor(jdId,tamano) {
  // Obtenemos la altura de la pantalla
  var alturaPantalla = $(window).height();
  // Obtenemos la posición del contenedor
  var posicionContenedor = $("#"+jdId).offset().top;
  // Calculamos el tamaño del contenedor restando la posición del contenedor del 90% de la altura de la pantalla, y restando cualquier tamaño sobrante
  var tamañoContenedor = (alturaPantalla * tamano) - (posicionContenedor % alturaPantalla);
  // Devolvemos el tamaño del contenedor en píxeles
  return tamañoContenedor;
}

class ImaskInputNumber {
  constructor(elementId, tieneComas, minValor, maxValor, escala = 0) {
    this.element = document.getElementById(elementId);
    this.tieneComas = tieneComas;
    this.minValor = minValor || Number.MIN_SAFE_INTEGER;
    this.maxValor = maxValor || Number.MAX_SAFE_INTEGER;
    this.escala = escala != '' && tieneComas ? escala : 0;
    this.initializeMask();
  }

  initializeMask() {
    this.maskOptions = {
      mask: Number,
      scale: this.escala,
      signed: false,
      thousandsSeparator: this.tieneComas ? '.' : '',
      padFractionalZeros: true,
      normalizeZeros: true,
      radix: this.tieneComas ? ',' : '.',
      mapToRadix: this.tieneComas ? [',', '.'] : ['.'],
    };
    this.mask = IMask(this.element, this.maskOptions);
  }

}

class ImaskInputCaracter { // const myInput = new ImaskInputCaracter('txtPrimerApellido', 20);
  constructor(elementId, maxCaracteres) {
    this.element = document.getElementById(elementId);
    this.maskOptions = {
      mask: /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/,
      lazy: false,
    };
    this.mask = IMask(this.element, this.maskOptions);

    this.element.addEventListener('input', () => {
      if (this.element.value.length > maxCaracteres) {
        const truncatedValue = this.element.value.slice(0, maxCaracteres);
        this.element.value = truncatedValue;
        this.mask.value = truncatedValue; 
      }
    });
  }
}

function detectarTamanoVertical(contenedor) {
  let posicion = contenedor.offset().top;
  let espacioDisponible = $(window).height() - posicion - contenedor.outerHeight();
  let tamanioVertical = posicion + espacioDisponible;
  return tamanioVertical;
}

function detectarTamanoVerticalConDato(contenedor,dato) {
  // Obtenemos el tamaño vertical del contenedor
  var tamanoContenedor = contenedor.outerHeight();

  // Calculamos el 90% del tamaño vertical del contenedor
  var calculo = Math.round(tamanoContenedor * (dato ? dato : 0.9));

  // Devolvemos el resultado
  return calculo;
}


function calculartmPorCaracteres(texto, numCaracteres) {
  const fontSize = parseFloat(window.getComputedStyle(document.body).getPropertyValue('font-size'));
  const tempElement = document.createElement('span');
  tempElement.style.fontSize = `${fontSize}px`;
  tempElement.style.visibility = 'hidden';
  tempElement.innerHTML = texto.slice(0, numCaracteres);
  document.body.appendChild(tempElement);
  const width = tempElement.offsetWidth;
  document.body.removeChild(tempElement);
  return width;
}



function convertirAOrdinal(num) {
  let unos = num % 10;
  let decenas = Math.floor(num / 10) % 10;
  let sufijo = "";

  if (decenas == 1) {
    sufijo = "va";
    switch (unos) {
      case 0:
        sufijo = "ma";
        break;
      default:
        sufijo = "va";
        break;
    }
  } else {
    switch (unos) {
      case 1:
        sufijo = "ra";
        break;
      case 2:
        sufijo = "da";
        break;
      case 3:
        sufijo = "ra";
        break;
      case 4:
      case 5:
      case 6:
        sufijo = "ta";
        break;
      case 7:
        sufijo = "ma";
        break;
      case 8:
        sufijo = "va";
        break;
      default:
        sufijo = "na";
        break;
    }
  }
  return num + sufijo;
}



class TableNavigation {
  constructor(tableId, fn = null, fn2 = null) {
    this.table = document.getElementById(tableId);
    this.body = this.table.querySelector("tbody");
    this.currentRow = this.findSelectedTable() == -1 ? null : this.rows[this.findSelectedTable()];
    this.table.addEventListener("click", this.handleClick.bind(this));
    this.contenedor = null;
    // console.log("getComputedStyle",getComputedStyle(this.table.parentNode).getPropertyValue('overflow') === 'auto')
    // console.log("getComputedStyle",getComputedStyle(this.table.parentNode).getPropertyValue('overflow') === 'scroll')
    // console.log("getComputedStyle",getComputedStyle(this.table.parentNode).getPropertyValue('overflow-y'))
    this.fn = fn;
    this.fn2 = fn2;
    document.addEventListener("keydown", this.handleKeyPress.bind(this));
    this.movimiento = null;
    this.primero = null;
    this.ultimo = null;
  }

  handleClick(event) {
    console.log('hace click jiji')
    if (!this.isModalOpen()) {
      this.rows = this.body.querySelectorAll("tbody tr");
        this.changeCurrentPosition();
    }
  }

  handleKeyPress(event) {
    if (!this.isModalOpen()) {
      this.rows = this.body.querySelectorAll("tbody tr"); //toma los datos actualizados
      this.contenedor = this.contenedor == null ? this.table.parentNode : null;
      console.log(event.key);
        // event.preventDefault(); // Prevenir el movimiento del scroll del contenedor
      if(this.tablaVisible() && document.activeElement === document.body){
          this.movimiento = event.key;
          if (event.key === "Enter") {
            event.preventDefault();
            this.enter();
          }else if (event.key === "ArrowUp") {
          event.preventDefault();
          this.moveUp();
        } else if (event.key === "ArrowDown") {
          event.preventDefault();
          this.moveDown();
        }
    }
    }
  }
  enter(){
    if (typeof this.fn === 'function') {
      this.fn2(this.currentRow);
    }
  }

  moveUp() {
    if (!this.currentRow) {
      this.currentRow = this.rows[0];
    } else {
      const index = Array.from(this.rows).indexOf(this.currentRow);
      if (index > 0) {
        this.currentRow.classList.remove("muestra-blue");
        this.currentRow = this.rows[index - 1];
      }
    }
    this.currentRow.classList.add("muestra-blue");
     if (this.contenedor !=  null) {
      this.scrollToRow(this.currentRow);
     }
     if (typeof this.fn === 'function') {
      this.fn(this.currentRow);
    }
  }

  moveDown() {
    if (!this.currentRow || typeof this.currentRow === 'undefined') {
      this.currentRow = this.rows[0];
    } else {
      const index = Array.from(this.rows).indexOf(this.currentRow);
      if (index < this.rows.length - 1) {
        this.currentRow.classList.remove("muestra-blue");
        this.currentRow = this.rows[index + 1];
      }
    }
    this.currentRow.classList.add("muestra-blue");
    if (this.contenedor !=  null) {
     
      this.scrollToRow(this.currentRow);
     }
      if (typeof this.fn === 'function') {
      this.fn(this.currentRow);
    }
  }
  
  findSelectedTable(){
    const rows = this.table.querySelectorAll("tbody tr");
    let position = -1;

    for (let i = 0; i < rows.length; i++) {
      if (rows[i].classList.contains('muestra-blue')) {
        position = i;
        break;
      }
    }
    return position;
  }
  
  changeCurrentPosition(){ // obtiene la posicion del tr seleccionada con click  y se lo asigna a la variable global la cual sera el punto de partida para moverse en la tabla
    this.currentRow = this.findSelectedTable() == -1 ? null : this.rows[this.findSelectedTable()]
  }
  
  changeContenedor(contenedor){ //se agrega un contenedor que tiene el scroll
    this.contenedor = document.querySelector(contenedor);
  }
  
  changeContenedorParent(contenedor){ //se agrega un contenedor que tiene el scroll
    this.contenedor = contenedor;
  }
  
   scrollToRow(row) { // movimiento del scroll segun la posicion del siguiente o anterior tr
      
      if(this.movimiento === "ArrowUp" &&  !this.trVisible(row.previousElementSibling)){
        //&& row.previousElementSibling != null
          this.contenedor.scrollTop = this.primero == null  ? getComputedStyle(this.table.querySelector('tr:first-child')).position === 'sticky' && row.previousElementSibling != null ? row.previousElementSibling.offsetTop : row.offsetTop : 0;
          this.verificaPrimero(row);
      }

      if(this.movimiento === "ArrowDown" && !this.trVisible(row.nextElementSibling)){
          this.contenedor.scrollTop =  this.ultimo == null ? this.scrollPosition(row) : this.contenedor.scrollHeight - this.contenedor.clientHeight;
          this.verificaUltimo(row);
      }
  }
  
  tablaVisible() { //verifica si la tabla esta visibile
  const rect = this.table.getBoundingClientRect();
  return (
    rect.left > 0 &&
    rect.right > 0
  );
}

  trVisible(row) { //verifica si el elemento se encuentra dentro del contenido visible
      let resultado = false;
       if(row == null){ return resultado}
      let containerTop = this.contenedor.scrollTop;
      let containerBottom = containerTop + this.contenedor.clientHeight;
      let trTop = row.offsetTop;
      let trBottom = trTop + row.clientHeight;
      if(trTop >= containerTop && trBottom <= containerBottom){
         resultado = true;
      }
      return resultado;
  }

  verificaPrimero(row){
      if(row.previousElementSibling == null){ this.primero = true;this.ultimo = null;return;}
      const elementSelected = Array.from(this.rows).indexOf(row.previousElementSibling);
      if (elementSelected === 0) {
          this.primero = true;
      }else{
          this.primero = null;
      }
      this.ultimo = null;
  }

  verificaUltimo(row){
      const elementSelected = Array.from(this.rows).indexOf(row.nextElementSibling);
      const ultimoElemento = this.rows.length - 1;
      if (elementSelected === ultimoElemento) {
          this.ultimo = true;
      }else{
          this.ultimo = null;
      }
      this.primero = null;
  }

  scrollPosition(row){
      let selectedElementTop = row.offsetTop;
      let selectedElementHeight = row.clientHeight;
      let contenedor = this.contenedor.clientHeight;
      let scroll = (selectedElementTop + selectedElementHeight - contenedor);
      let scrollPosition = scroll < 0 ? 0 : scroll;
      return scrollPosition;
  }

  getCurrentRow(){
    return this.currentRow;
  }
  isModalOpen() {
    const modal = document.querySelector(".modal.show");
    return modal !== null && modal !== undefined;
  }

}


function seleccionarFila(row,tabla) {
  row.classList.add('muestra-blue');
  const table = document.querySelector(tabla); //cambiar el id segun la tabla
const filas = table.querySelectorAll('tbody tr');
  for (let i = 0; i < filas.length; i++) {
    if (filas[i] != row) {
      filas[i].classList.remove('muestra-blue');
    }
  }

}

function quitarTildes(palabra) {
  if(palabra != null && palabra != ""){
    palabra = palabra.normalize('NFD').replace(/[\u0300-\u036f]/g, "");
    palabra = palabra.replace(/[^a-zA-Z0-9\s]/g, "");
  }
  return palabra;
}


function actualizarAnchoTd(caracteres_cantidad, array_columnas) {
  var fontSize = window.getComputedStyle(document.body).getPropertyValue('font-size');
  fontSize = parseFloat(fontSize);
  var widthPerChar = fontSize * 0.5; 
  var totalWidth = widthPerChar * caracteres_cantidad;

  $('tr').each(function() {
    for (var i = 0; i < array_columnas.length; i++) {
      $(this).find('td, th').filter(':nth-child(' + array_columnas.join('), :nth-child(') + ')').css('width', totalWidth + 'px');
    }
  });
}

//actualizarAnchoTd(25,[2,7]) el primer parametro entregado es la cantidad de caracteres que se desean y el segundo parametro son las columnas a las cuales se les va aplicar ese cambio


function pixelsXCaracteres(numCaracteres) {
  const elemento = document.body;
  const tamanioFuente = parseFloat(window.getComputedStyle(elemento).getPropertyValue('font-size'));
  const span = document.createElement('span');
  span.style.fontFamily = 'monospace';
  span.style.visibility = 'hidden'; 
  span.textContent = 'X'; 
  document.body.appendChild(span); 
  const anchoCaracter = span.offsetWidth;
  document.body.removeChild(span);
  const anchoTexto = anchoCaracter * numCaracteres;
  const pixeles = tamanioFuente * (anchoTexto / elemento.offsetWidth);
  return pixeles;
}

function DeleteAddClassdataTable(jqid,cantidad){
  if(cantidad == 0){
    let headWidth = $(`${jqid} .dataTables_scrollHead` ).find('table').width();
    $(`${jqid} .dataTables_scrollBody`).find('table').css('width', headWidth);
    $(`${jqid} .dataTables_scrollBody`).find('.dataTables_empty').css('text-align', 'center');
    $(`${jqid} .dataTables_scrollBody`).find('table').removeClass('dataTable'); 
}else{
    $(`${jqid} .dataTables_scrollBody`).find('table').addClass('dataTable'); 
}
}


function orderByProperty(array, prop) {
  return array.sort(function(a, b) {
    const propA = a[prop] ? a[prop].toUpperCase() : '';
    const propB = b[prop] ? b[prop].toUpperCase() : '';

    if (propA < propB) {
      return -1;
    }
    if (propA > propB) {
      return 1;
    }
    return 0;
  });
}



function rellenarSelectpicker(selectID, opciones,valorDefectoVacio = '') {
  var selectElement = $('#' + selectID);

  selectElement.empty();

  $.each(opciones, function(index, opcion) {
    selectElement.append($('<option>', {
      value: opcion.id,
      text: opcion.descripcion
    }));
  });

  selectElement.selectpicker('val', valorDefectoVacio);

  selectElement.selectpicker('refresh');
}

//funcion optimizada por marco caracciolo 08/05/2023
function rellenarSelectpicker2(selectID, opciones,valorDefectoVacio = '') {
  var selectElement = $('#' + selectID);

  selectElement.empty();

  $.each(opciones, function(index, opcion) {
    if (opcion.id === 0){
      selectElement.append($('<option>', {
        value: opcion.id,
        text: "SIN ESPECIFICAR"
      }));
    } else {
      selectElement.append($('<option>', {
        value: opcion.id,
        text: opcion.descripcion
      }));
    }
    
  });

  selectElement.selectpicker('val', valorDefectoVacio);

  selectElement.selectpicker('refresh');
}

cambiarColorSwitch()

function cambiarColorSwitch(){
  const switchLabel = document.querySelector('.switch-active')
  if (switchLabel) {
    switchLabel.classList.remove('inactivo');
    const switchInput = switchLabel.querySelector('input[type="checkbox"]')
    switchInput.addEventListener('change', function() {
      if (this.checked) {
        switchLabel.classList.remove('inactivo');
      } else {
        switchLabel.classList.add('inactivo');
      }
    })
  }
}


class ManejadorDatos {
    constructor() {
      this.array = [];
    }

    agregarElementos(elementos) {
        this.array = [...elementos];
      }

    limpiarArray() {
      this.array = [];
    }

    filtrarPorValorProiedad(propiedad,valorBuscado) {
        return this.array.filter(function(fila) {
            if (valorBuscado != '') {
              return fila[propiedad] == valorBuscado ? true : false;
            } else {
              return true;
            }
          });
    }

    filtrarIncludeValorPropiedad(propiedad,valorBuscado) {
      return this.array.filter(item => item[propiedad].includes(valorBuscado));
    }


    // para manejar los datos necesita enviarlos de la siguiente manera debe utilizar las propiedades reales,
    //  puede pasar tantos parametros con valores desee
    //  manejadorDatos.filtrarPorPropiedadesValores(
    //   { nombres: 'DIANA PRINCE', norden: 35}
    //  );
    filtrarPorPropiedadesValores(propiedadesValores) {
        return this.array.filter(function(fila) {
          for (const [propiedad, valorBuscado] of Object.entries(propiedadesValores)) {
            if (valorBuscado !== '' && fila[propiedad] != valorBuscado) {
              return false;
            }
          }
          return true;
        });
      }

  }


  // alerta con texto
  const UiMensajes = {
    imprimirTooltip: function(elemento, mensaje, tipo = 'enable') {
      $(elemento).attr({
        "data-toggle": "tooltip",
        "data-placement": "top",
        "data-custom-class": "tooltip-warning",
        "title": mensaje,
        "data-original-title": mensaje,
      });
      $(elemento)
        .tooltip({
          template: '<div class="tooltip tooltip-danger" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',
        }).tooltip(tipo);
    },
    limpiarTooltip: function(elemento) {
      $(elemento).removeAttr("data-toggle data-placement data-custom-class title data-original-title");
      $(elemento).tooltip("disable");
      $(elemento).tooltip('update');
    },
    normalTooltip: function(elemento) {
      $(elemento)
        .tooltip({
          template: '<div class="tooltip tooltip-normal" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',
        });
    }
  };
  

  function iconLoading(spinnerContainer = ''){
    return `<div class="${spinnerContainer} spinnerContainer">
                <div class="lds-ripple">
                    <div></div>
                    <div></div>
                </div>
                <div class="loadingText">Cargando...</div>
            </div>`
  }
