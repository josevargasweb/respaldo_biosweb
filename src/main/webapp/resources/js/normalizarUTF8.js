//CAMBIAR POST ESCRITO
var normalize = (function () {
    var from = "ÃÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàáäâèéëêìíïîòóöôùúüûÑñÇç",
            to = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc",
            mapping = {};

    for (var i = 0, j = from.length; i < j; i++)
        mapping[ from.charAt(i) ] = to.charAt(i);

    return function (str) {
        var ret = [];
        for (var i = 0, j = str.length; i < j; i++) {
            var c = str.charAt(i);
            if (mapping.hasOwnProperty(str.charAt(i)))
                ret.push(mapping[ c ]);
            else
                ret.push(c);
        }
        return ret.join('');
    }

})();


//PARA EVITAR QUE COLOQUEN LETRAS ESPECIALES
function check(e) {
    tecla = (document.all) ? e.keyCode : e.which;

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla == 8) {
        return true;
    }

    // Patron de entrada, en este caso solo acepta numeros y letras
    patron = /[ A-Za-z0-9-'á-úÁ-ÚñÑ´ä-üÄ-Ü-_]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}
function checkParaFechaNac(e) {
    tecla = (document.all) ? e.keyCode : e.which;

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla == 8) {
        return true;
    }

    // Patron de entrada, en este caso solo acepta numeros y letras
    patron = /[-]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}

//CAMBIA LETRAS ESPECIALES
String.prototype.replaceLatinChar = function(){
 return output = this.replace(/á|é|í|ó|ú|ñ|ä|ë|ï|ö|ü|à/ig,function (str,offset,s) {
        var str =str=="á"?"a":str=="é"?"e":str=="í"?"i":str=="ó"?"o":str=="ú"?"u":str=="ñ"?"n":str;
		   str =str=="Á"?"A":str=="É"?"E":str=="Í"?"I":str=="Ó"?"O":str=="Ú"?"U":str;
		   str =str=="Á"?"A":str=="É"?"E":str=="Í"?"I":str=="Ó"?"O":str=="Ú"?"U":str;
		   str =str=="ä"?"a":str=="ë"?"e":str=="ï"?"i":str=="ö"?"o":str=="ü"?"u":str;
		   str =str=="Ä"?"A":str=="Ë"?"E":str=="Ï"?"I":str=="Ö"?"O":str=="Ü"?"U":str;
                   str =str=="À"?"A":str=="à"?"a":str=="Ñ"?"N":str=="ñ"?"n":str;
        return (str);
        })
	
}

function validarEmail(valor) {
  if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3,4})+$/.test(valor)){
   alert("La dirección de email " + valor + " es correcta.");
  } else {
   alert("La dirección de email es incorrecta.");
  }
}
