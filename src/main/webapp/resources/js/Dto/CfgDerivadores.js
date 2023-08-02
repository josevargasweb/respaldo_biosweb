function CfgDerivadores() {

  this.cfgDerivadores = [];
  this.cderivIdderivador;
  this.cderivDescripcion;
 }
  
function getDerivadores(der, callback) {
  let url = getctx + '/api/dominio/derivadores/list';
  $.get(url, function(data) {
    //console.log(data);
    der.cfgDerivadores = data;
    callback(data)
  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      console.log(err);
    });
}


CfgDerivadores.prototype.getById = function(id) {
  return this.cfgDerivadores.filter(der => der.cderivIdderivador == id);
};

CfgDerivadores.prototype.fillSelect = function(jqIdSelect) {

  this.cfgDerivadores.forEach(deriv => {
    const opt = new Option(deriv.cderivIdderivador, deriv.cderivDescripcion);
    $(jqIdSelect).append($(opt));
  });
  $(jqIdSelect).selectpicker('refresh')
};
