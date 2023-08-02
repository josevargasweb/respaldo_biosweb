/*


Requiere moment.js


 */


/*export*/ function convertirYYYYMMDD(fecha) {
  var dd = String(fecha.getDate()).padStart(2, '0');
  var mm = String(fecha.getMonth() + 1).padStart(2, '0'); //January is 0!
  var yyyy = fecha.getFullYear();

  return yyyy + '-' + dd + '-' + mm;
}

/*export*/ function convertirDateDDMMYYYY(fecha) {

  if (!fecha instanceof Date) {
    throw new ValidationError('Parametro no es del tipo fecha');
  }
  var dd = String(fecha.getDate()).padStart(2, '0');
  var mm = String(fecha.getMonth() + 1).padStart(2, '0'); //January is 0!
  var yyyy = fecha.getFullYear();

  return dd + '/' + mm + '/' + yyyy;
}

/*export*/ function convertirFechayHora(fecha) {

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

/*export*/ function convertirDateDD_MM_YYYY_HH_mm_SS(fecha, clDSeparator, clTSeparator) {

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



/*export*/ function ParseFechaCL(fecha) {

  if (!fecha instanceof Date) {
    throw new ValidationError('Parametro no es del tipo fecha');
  }
  if (fecha === undefined) {
    return null;
  }

  let d = ParseFechaCLcSeparator(fecha, '-');

  if (d === null) {
    return ParseFechaCLcSeparator(fecha, '/');;
  }
  return d;

}

/*export*/ function ParseFechaCLcSeparator(fecha, separator) {

  let regexDia = /[0-2][0-9]|[30|31]/;
  let regexMonth = /0[0-9]|[10|11|12]/;
  let regexYear = /[1|2][0-9]{3}/;

  if (fecha === undefined) {
    return null;
  }
  if (!fecha instanceof Date) {
    throw new ValidationError('Parametro no es del tipo fecha');
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

/*export*/ function nombresMeses(mes) {
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

/*export*/ function dateTimeAddHH(dt, horas) {

  let rpta = new Date();
  let ms = dt.getTime();
  ms = ms + horas * 60 * 60 * 1000;
  rpta.setTime(ms);
  return rpta;
}

/*export*/ function dateTimeAddMS(dt, msec) {

  let rpta = new Date();
  let ms = dt.getTime();
  ms = ms + msec;
  rpta.setTime(ms);
  return rpta;
}

/*export*/ function getCurrentDate() {

  const curDate = Date.now();


}


/*export*/ class BOValidationError {
  constructor(message) {
    this.message = message;

  }
}


/*export*/ function YYYY_MM_DD2DD_MM_YY(fechayyyy_mm_dd, inputSeparator, outputSeparator) {
  let partes = fechayyyy_mm_dd.split(inputSeparator);

  return partes[2].concat(outputSeparator).concat(partes[1]).concat(outputSeparator).concat(partes[0]);
}

/*export*/ function YYYY_MM_DD2DD_MM_YY_format(fechayyyy_mm_dd, inputSeparator, outputSeparator) {
  let partes = fechayyyy_mm_dd.split(inputSeparator);

  return partes[2].concat(outputSeparator).concat(partes[1]).concat(outputSeparator).concat(partes[0].slice(-2));
}


/*export*/ function DD_MM_YY2DD_MM_YY(fechayyyy_mm_dd, inputSeparator, outputSeparator) {
  let partes = fechayyyy_mm_dd.split(inputSeparator);

  return partes[0].concat(outputSeparator).concat(partes[1]).concat(outputSeparator).concat(partes[2]);
}

function DD_MM_YYYY2ANNOS(fechayyyy_mm_dd) {
  return moment().diff(moment(fechayyyy_mm_dd, "DD-MM-YYYY"), "years") + " años";
}

///*export*/ {YYYY_MM_DD2DD_MM_YY}


function DDMMYYYYHHMMSS2HHMMDDMMYYYY(fechayyyy_mm_dd){
  let formatoDeseado = "hh:mm DD/mm/yyyy";
  let momento = moment(fechayyyy_mm_dd, "DD-MM-YYYY HH:mm:ss");
  return fechaConFormatoDeseado = momento.format(formatoDeseado);
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