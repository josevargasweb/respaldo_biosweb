function CfgAntecedentes() {};

CfgAntecedentes.prototype.constructor = function () {   
  this.misAntecedentes = new Array();
  };


CfgAntecedentes.prototype.getAntecedentes = function (me) {
    let jqXHR = $.ajax({
        url: gCte.getctx + "/api/dominio/cfgantecedentes/list",
        async: false,
        success:function(rpta){
          if (rpta.status != 200){
            handlerMessageError(rpta.message);
            return;
          }
          me.misAntecedentes = new Array();
          rpta.dato.forEach(t => me.misAntecedentes.push(t));
        }
    });
};


