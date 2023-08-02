function CfgMuestras() {

  this.cfgMuestras = [];
  this.cmueIdtipomuestra;
  this.cmueDescripcion;
 }

CfgMuestras.prototype.getMuestras = function () {
    var jqXHR = $.ajax({
        url: getctx + "/api/dominio/muestras/list",
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgMuestras.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((muestra) => {
        arrayDato.push({
            id: muestra.cmueIdtipomuestra,
            descripcion: muestra.cmuePrefijotipomuestra + " " + muestra.cmueDescripcionabreviada
        });
    });
    this.cfgMuestras = arrayDato;
};

function getMuestras(mue, callback) {
  let url = getctx + '/api/dominio/muestras/list';
  $.get(url, function(data) {
      console.log(data)
    mue.cfgMuestras = data;
    callback(data)
  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      console.log(err);
    });
}

CfgMuestras.prototype.getMuestras = function (callback) {
    let url = getctx + '/api/dominio/muestras/list';
    $.get(url, function(data) {
      console.log(data)
    this.cfgMuestras = data;
    callback(data);
  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      console.log(err);
    });
};

CfgMuestras.prototype.getById = function(id) {
  return this.cfgMuestras.filter(mue => mue.cmueIdtipomuestra == id);
};

CfgMuestras.prototype.fillSelect = function(jqIdSelect) {

  this.cfgMuestras.forEach(muestra => {
    const opt = new Option(muestra.cmueIdtipomuestra, muestra.cmueDescripcion);
    $(jqIdSelect).append($(opt));
  });
  $(jqIdSelect).selectpicker('refresh')
};

