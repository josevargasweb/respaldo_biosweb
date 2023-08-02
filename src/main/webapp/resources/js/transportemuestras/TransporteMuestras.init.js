//var getctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

var today = new Date();
var centroSelected = 0;
var labSelected = 0;
today = convertirDateGuionDDMMYYYY(today);


var datosMuestra = null;

$(document).ready(function (){

    // $(".ocultar").hide();
    // $('#fechaTitulo').val(today);
    
    initTable();
    agregarSaltoEntreCampos();

});

