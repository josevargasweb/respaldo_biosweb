

async function getProcedencias() {
  let url = getctx + "/api/dominio/procedencia/list";
  let result = await $.ajax({
    type: "get",
    url: url,
    success: function(data) { return data },
    error: function() { console.log("error") },

  });
  return result
}


async function getServicios() {
  let url = getctx + "/api/dominio/servicios/list";
  let result = await $.ajax({
    type: "get",
    url: url,
    success: function(data) { return data },
    error: function() { console.log("error") },

  });
  return result
}




async function getSecciones() {
  let url = getctx + "/api/dominio/secciones/list";
  let result = await $.ajax({
    type: "get",
    url: url,
    success: function(data) { return data },
    error: function() { console.log("error") },

  });
  return result
}



async function getConvenios() {
  let url = getctx + "/api/dominio/obtenerConvenios/list";
  let result = await $.ajax({
    type: "get",
    url: url,
    success: function(data) { return data },
    error: function() { console.log("error") },

  });
  return result
}


async function getLaboratorios() {
  let url = getctx + "/api/instituciones/laboratorios/list";
  let result = await $.ajax({
    type: "get",
    url: url,
    success: function(data) { return data },
    error: function() { console.log("error") },

  });
  return result
}


async function getExamenes() {
  let url = getctx + "/api/dominio/cfgexamen/list";
  let result = await $.ajax({
    type: "get",
    url: url,
    success: function(data) { return data },
    error: function() { console.log("error") },

  });
  return result
}

async function getTipoAtencion() {
  let url = getctx + "/api/dominio/tipoatencion/list";
  let result = await $.ajax({
    type: "get",
    url: url,
    success: function(data) { return data },
    error: function() { console.log("error") },

  });
  return result
}

$(document).ready(async function() {

  let data = await getProcedencias();
  llenarSelectProcedencia(data);
  data = await getServicios();
  llenarSelectServicios(data);
  data = await getSecciones();
  llenarSelectSecciones(data);
  data = await getConvenios();
  llenarSelectConvenios(data);
  data = await getLaboratorios();
  llenarSelectLaboratorios(data);
  data = await getExamenes();
  llenarSelectExamenes(data);
  data = await getTipoAtencion();
  llenarSelectTipoAtencion(data);

});

function llenarSelectSecciones(data) {
  data.forEach(function(dato) {
    $("#bo_seccion").append($("<option>", {
      value: dato.csecIdseccion,
      text: dato.csecDescripcion
    }))
  })

}

function llenarSelectServicios(data) {
  data.forEach(function(dato) {
    $("#bo_servicio").append($("<option>", {
      value: dato.csIdservicio,
      text: dato.csDescripcion
    }))
  })

}


function llenarSelectProcedencia(data) {
  data.forEach(function(dato) {
    $("#bo_procedencia").append($("<option>", {
      value: dato.cpIdprocedencia,
      text: dato.cpDescripcion
    }))
  })

}

function llenarSelectConvenios(data) {
  data.forEach(function(dato) {
    $("#bo_convenio").append($("<option>", {
      value: dato.ccIdconvenio,
      text: dato.ccDescripcion
    }))
  })

}

function llenarSelectLaboratorios(data) {
  data.forEach(function(dato) {
    $("#bo_laboratorio").append($("<option>", {
      value: dato.clabIdlaboratorio,
      text: dato.clabDescripcion
    }))
  })

}


function llenarSelectExamenes(data) {
  data.forEach(function(dato) {
    $("#bo_examen").append($("<option>", {
      value: dato.ceIdexamen,
      text: dato.ceAbreviado
    }))
  })

}


function llenarSelectTipoAtencion(data) {
  data.forEach(function(dato) {

    $("#bo_tipoAtencion").append($("<option>", {
      value: dato.ctaIdtipoatencion,
      text: dato.ctaDescripcion
    }))
  })

}




$("#tituloBusqueda").click(function() {
  limpiar();
  $("#TiempoEntregaSwitch").css("display", "none");
  $("#estadisticaHistorico").css("display", "none");

})

function limpiar() {

  let html = ""
  $("#bodyTablaBusqueda").html(html)
  $("#bo_nOrdenIni").val("")
  $("#bo_nOrdenFin").val("")
  $("#bo_fIni").val("")
  $("#bo_fFin").val("")
  $("#bo_nombre").val("")
  $("#bo_apellido").val("")
  $("#bo_segundoApellido").val("")
  $("#bo_nroDocumento").val("")
  $("#bo_procedencia").val("-1");
  $("#bo_servicio").val("-1");
  $("#bo_seccion").val("-1");
  $("#bo_laboratorio").val("-1");
  $("#bo_convenio").val("-1");
  $("#bo_tipoAtencion").val("-1");
  $("#bo_examen").val("-1");
  $("#estadisticaHistorico").css("display", "none");
  // $("#collapseOne8").toggle("true");
  $("#collapseOne8").collapse('show')
  $("#btnEstadisticaTiempoEntrega").prop('hidden', 'true');
  $("#btnEstadisticaTotalPorExamen").prop('hidden', 'true');
  $("#btnEstadisticaMuestrasRechazadas").prop('hidden', 'true');
  $("#btnEstadisticaExamenesDerivados").prop('hidden', 'true');
  $("#btnEstadisticaResultadosCriticos").prop('hidden', 'true');
  $("#btnEstadisticaTiempoEntregaExcel").prop('hidden', 'true');
  $("#btnEstadisticaTotalPorExamenExcel").prop('hidden', 'true');
  $("#btnEstadisticaMuestrasRechazadasExcel").prop('hidden', 'true');
  $("#btnEstadisticaExamenesDerivadosExcel").prop('hidden', 'true');
  $("#btnEstadisticaResultadosCriticosExcel").prop('hidden', 'true');

  $("#bo_nOrdenIni").prop('disabled', false);
  $("#bo_nOrdenFin").prop('disabled', false);
  $("#bo_fIni").prop('disabled', false);
  $("#bo_fFin").prop('disabled', false);
  $("#bo_nombre").prop('disabled', false);
  $("#bo_apellido").prop('disabled', false);
  $("#bo_segundoApellido").prop('disabled', false);
  $("#bo_nroDocumento").prop('disabled', false);
  $("#bo_procedencia").prop('disabled', false);
  $("#bo_servicio").prop('disabled', false);
  $("#bo_seccion").prop('disabled', false);
  $("#bo_laboratorio").prop('disabled', false);
  $("#bo_convenio").prop('disabled', false);
  $("#bo_tipoAtencion").prop('disabled', false);
  $("#bo_examen").prop('disabled', false);


}



function validarDatosVacios() {


  if ($("#bo_nOrdenIni").val() === "" && $("#bo_nOrdenFin").val() === "" && $("#bo_nombre").val() === "" && $("#bo_apellido").val() === "" && $("#bo_segundoApellido").val() === ""
    && $("#bo_nroDocumento").val() === ""
    && $("#bo_fIni").val() === "" && $("#bo_fFin").val() === ""
    && ($("#bo_procedencia option:selected").val() === "-1")
    && ($("#bo_servicio option:selected").val() === "-1")
    && ($("#bo_seccion option:selected").val() === "-1")
    && ($("#bo_laboratorio option:selected").val() === "-1")
    && ($("#bo_convenio option:selected").val() === "-1")
    && ($("#bo_tipoAtencion option:selected").val() === "-1")
    && ($("#bo_examen option:selected").val() === "-1")) {
    handlerMessageError("Debe agregar algun parametro");
    return false
  }
  return true
}





//*************************************   Funciones para crear Excel *************************** */

function agregarExcel(nombreExcel) {

  var table = document.querySelector("#tb");
  var sheet = XLSX.utils.table_to_sheet(table); // Convertir un objeto de tabla en un objeto de hoja

  openDownloadDialog(sheet2blob(sheet), nombreExcel + '.xlsx'); // Nombre del archivo
}

function sheet2blob(sheet, sheetName) {
  sheetName = sheetName || 'sheet1';
  var workbook = {
    SheetNames: [sheetName],
    Sheets: {}
  };
  workbook.Sheets[sheetName] = sheet; // Generar elementos de configuración de Excel

  var wopts = {
    bookType: 'xlsx', // El tipo de archivo que se generará
    bookSST: false, // Ya sea para generar una tabla de cadenas compartidas, la explicación oficial es que si activa la velocidad de generación, disminuirá, pero tiene una mejor compatibilidad en dispositivos IOS inferiores
    type: 'binary'
  };
  var wbout = XLSX.write(workbook, wopts);
  var blob = new Blob([s2ab(wbout)], {
    type: "application/octet-stream"
  }); // Cadena a ArrayBuffer

  function s2ab(s) {
    var buf = new ArrayBuffer(s.length);
    var view = new Uint8Array(buf);
    for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
    return buf;
  }
  return blob;
}


var table = document.querySelector("#tb");
var sheet = XLSX.utils.table_to_sheet(table); // Convertir un objeto de tabla en un objeto de hoja


function openDownloadDialog(url, saveName) {
  if (typeof url == 'object' && url instanceof Blob) {
    url = URL.createObjectURL(url);
  }
  var aLink = document.createElement('a');
  aLink.href = url;
  aLink.download = saveName || '';
  var event;
  if (window.MouseEvent) event = new MouseEvent('click');
  else {
    event = document.createEvent('MouseEvents');
    event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
  }
  aLink.dispatchEvent(event);
}





