/* funciones menu */
jQuery.fn.clickOutside = function (callback) {
    var $me = this;
    $(document).mouseup(function (e) {
        if (!$me.is(e.target) && $me.has(e.target).length === 0) {
            callback.apply($me);
        }
    });
};


$('#menu-lateral-contenido,#menu-nube').clickOutside(function () {
    $('#menu-lateral-contenido').collapse('hide');
});

$(document).ready(function () {
  $('#menu-lateral-contenido').on('show.bs.collapse', function () {
        $("#menu-lateral").removeClass("menu-lateral-hide");
        
    });
    $('#menu-lateral-contenido').on('hide.bs.collapse', function () {
        $("#menu-lateral").addClass("menu-lateral-hide");
    });
 
    //obtiene el sector donde se realizo el click y luego le asigna alguna clase o color
    $(".btn-main").click(function () {
        if ($(this).attr('aria-expanded')) {
            $(this).parent().parent().addClass("active-card-header");
            $(this).parent().parent().parent().siblings().children(".card-header").removeClass("active-card-header");
        }
    });
});

function formatearISO8601(fecha, separator) {

  if (fecha instanceof Date) {
    throw new ValidationError('Parametro es del tipo fecha');
  }

  if (fecha === '') return '';
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
    let d = partes[2].concat("-").concat(partes[1]).concat("-").concat(partes[0]);
    return d;
  }

    return null;

}
