/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function convertirYYYYMMDD(fecha) {
  if (!fecha instanceof Date) {
    throw new ValidationError('Parametro no es del tipo fecha');
  }
  var dd = String(fecha.getDate()).padStart(2, '0');
  var mm = String(fecha.getMonth() + 1).padStart(2, '0'); //January is 0!
  var yyyy = fecha.getFullYear();

  return yyyy + '-' + dd + '-' + mm;
}

function convertirDateDDMMYYYY(fecha) {
  if (!fecha instanceof Date) {
    throw new ValidationError('Parametro no es del tipo fecha');
  }
  var dd = String(fecha.getDate()).padStart(2, '0');
  var mm = String(fecha.getMonth() + 1).padStart(2, '0'); //January is 0!
  var yyyy = fecha.getFullYear();
  return dd + '/' + mm + '/' + yyyy;
}

function convertirDateGuionDDMMYYYY(fecha) {
  if (!fecha instanceof Date) {
    throw new ValidationError('Parametro no es del tipo fecha');
  }

  return fecha.toLocaleDateString('es-CL', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
  }).replace(/\//g, '-');

  //   var dd = String(fecha.getDate()).padStart(2, '0');
  //   var mm = String(fecha.getMonth() + 1).padStart(2, '0'); //January is 0!
  //   var yyyy = fecha.getFullYear();

  //   return dd + '-' + mm + '-' + yyyy;
}


function convertirFechayHora(fecha) {
  if (!fecha instanceof Date) {
    throw new ValidationError('Parametro no es del tipo fecha');
  }
  var dd = String(fecha.getDate()).padStart(2, '0');
  var mm = String(fecha.getMonth() + 1).padStart(2, '0'); //January is 0!
  //var yyyy = fecha.getFullYear();

  var hh = String(fecha.getHours()).padStart(2, '0');
  var mins = String(fecha.getMinutes() + 1).padStart(2, '0');
  return hh + ":" + mins + " " + dd + '/' + mm;
}

function convertirDateDD_MM_YYYY_HH_mm_SS(fecha, clDSeparator, clTSeparator) {
  if (!fecha instanceof Date) {
    throw new ValidationError('Parametro no es del tipo fecha');
  }
  var dd = String(fecha.getDate()).padStart(2, '0');
  var mm = String(fecha.getMonth() + 1).padStart(2, '0'); //January is 0!
  var yyyy = fecha.getFullYear();
  var hh = String(fecha.getHours()).padStart(2, '0');
  var mi = String(fecha.getMinutes()).padStart(2, '0');
  var ss = String(fecha.getSeconds()).padStart(2, '0');
  return dd + clDSeparator + mm + clDSeparator + yyyy + ' ' + hh + clTSeparator + mi + clTSeparator + ss;
}



function ParseFechaCL(fecha) {

  if (fecha === undefined) {
    return null;
  }

  let d = ParseFechaCLcSeparator(fecha, '-');

  if (d === null) {
    return ParseFechaCLcSeparator(fecha, '/');;
  }
  return d;

}

function ParseFechaCLcSeparator(fecha, separator) {

  let regexDia = /[0-2][0-9]|[30|31]/;
  let regexMonth = /0[0-9]|[10|11|12]/;
  let regexYear = /[1|2][0-9]{3}/;

  if (fecha === undefined) {
    return null;
  }
  let partes = fecha.split(separator);
  if (partes.length !== 3) {
    return null;
  }

  if (regexDia.test(partes[0]) && regexMonth.test(partes[1]) && regexYear.test(partes[2])) {
    let d = new Date();
    d.setDate(partes[0] * 1);
    d.setMonth(partes[1] * 1 - 1);
    d.setYear(partes[2] * 1);
    return d;
  }

  return null;

};

function nombresMeses(mes) {
  switch (parseInt(mes)) {
    case 1:
      return ("Ene");
      break;
    case 2:
      return ("Feb");
      break;
    case 3:
      return ("Mar");
      break;
    case 4:
      return ("Abr");
      break;
    case 5:
      return ("Mayo");
      break;
    case 6:
      return ("Jun");
      break;
    case 7:
      return ("Jul");
      break;
    case 8:
      return ("Ago");
      break;
    case 9:
      return ("Sep");
      break;
    case 10:
      return ("Oct");
      break;
    case 11:
      return ("Nov");
      break;
    case 12:
      return ("Dic");
      break;
  }
}

function dateTimeAddHH(dt, horas) {

  let rpta = new Date();
  let ms = dt.getTime();
  ms = ms + horas * 60 * 60 * 1000;
  rpta.setTime(ms);
  return rpta;
}

function dateTimeAddMS(dt, msec) {

  let rpta = new Date();
  let ms = dt.getTime();
  ms = ms + msec;
  rpta.setTime(ms);
  return rpta;
}

function nuevoFormato(fecha) {
  if (fecha != '') {
    var dia = fecha.substring(0, 2);
    var mes = fecha.substring(3, 5);
    var year = fecha.substring(8, 10);
    var hora = fecha.substring(11, 16);

    return hora + " " + dia + "/" + nombresMeses(mes) + "/" + year;
  } else {
    return '';
  }

}

function formatearDiaHoras(fecha) {
  if (fecha != '') {
    var dia = fecha.substring(0, 2);
    var mes = fecha.substring(3, 5);
    var year = fecha.substring(8, 10);
    var hora = fecha.substring(11, 16);

    return hora + " " + dia + "/" + nombresMeses(mes);
  } else {
    return '';
  }

}




