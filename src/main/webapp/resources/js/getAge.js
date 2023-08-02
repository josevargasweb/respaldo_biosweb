function getAge(dateString, dateType) {

    var now = new Date();
    var today = new Date(now.getYear(), now.getMonth(), now.getDate());

    var yearNow = now.getYear();
    var monthNow = now.getMonth();
    var dateNow = now.getDate();

    if (dateType == 1)
        var dob = new Date(dateString.substring(0, 4),
                dateString.substring(4, 6) - 1,
                dateString.substring(6, 8));
    else if (dateType == 2)
        var dob = new Date(dateString.substring(0, 2),
                dateString.substring(2, 4) - 1,
                dateString.substring(4, 6));
    else if (dateType == 3)
        var dob = new Date(dateString.substring(6, 10),
                dateString.substring(3, 5) - 1,
                dateString.substring(0, 2));
    else if (dateType == 4)
        var dob = new Date(dateString.substring(6, 8),
                dateString.substring(3, 5) - 1,
                dateString.substring(0, 2));
    else
        return '';

    var yearDob = dob.getYear();
    var monthDob = dob.getMonth();
    var dateDob = dob.getDate();

    yearAge = yearNow - yearDob;

    if (monthNow >= monthDob)
        var monthAge = monthNow - monthDob;
    else {
        yearAge--;
        var monthAge = 12 + monthNow - monthDob;
    }

    if (dateNow >= dateDob)
        var dateAge = dateNow - dateDob;
    else {
        monthAge--;
        var dateAge = 31 + dateNow - dateDob;

        if (monthAge < 0) {
            monthAge = 11;
            yearAge--;
        }
    }
    if (String(monthAge).length === 1) {
        monthAge = '0' + String(monthAge);
    }
    if (String(dateAge).length === 1) {
        dateAge = '0' + String(dateAge);
    }

    return String(yearAge) + '-' + String(monthAge) + '-' + String(dateAge);
}



function calcularEdadTM(f){
    var dia = f.substring(0, 2);
    var mes = f.substring(3, 5);
    var anio = f.substring(6, 10);
    var fecha = dia + "-" + mes + "-" + anio;

    fecha = getAge(fecha, 3);
    fecha = fecha.split("-");

    let edad = "";
    if (parseInt(fecha[0])>0){
        edad = parseInt(fecha[0],10) + (parseInt(fecha[0])>1 ? " Años " : " Año");
    } else if (parseInt(fecha[1])>0) {
        edad = parseInt(fecha[1],10) + (parseInt(fecha[1])>1 ? " Meses " : " Mes");
    } else {
        edad = parseInt(fecha[2],10) + (parseInt(fecha[2])>1 ? " Días" : " Día");
    }

    return edad;
}



function normalizarEdad(f){
  
  let partes = f.split(" ");
  
  if (partes[0].substring(partes[0].length-1)==='a' &&  partes[0].substring(0,0)!=='0'){
    return partes[0].substring(0,partes[0].length-1).concat(' años');
  }
  if (partes[1].substring(partes[1].length-1)==='m' &&  partes[1].substring(0,0)!=='0'){
    return partes[1].substring(partes[1].length-1).concat(' meses');;
  }
  if (partes[2].substring(partes[2].length-1)==='m' &&  partes[2].substring(0,0)!=='0'){
    return partes[2].substring(partes[2].length-1).concat(' días');
  }
}

