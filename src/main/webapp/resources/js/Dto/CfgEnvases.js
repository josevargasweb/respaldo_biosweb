function CfgEnvases() {

  this.cfgEnvases = [];
  this.envases = [];
  this.cenvIdenvase;
  this.cenvDescripcion;
 }
  
function getEnvases(env, callback) {
  let url = gCte.getctx + '/api/dominio/envases/list';
  $.get(url, function(data) {
    env.cfgEnvases = data;
    callback(data)
  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      console.log(err);
    });
}

function getCfgEnvases(mue, callback) {
  let url = gCte.getctx + '/api/dominio/cfgenvases/list';
  $.get(url, function(data) {
    mue.envases = data;
    callback(data)
  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      console.log(err);
    });
}



CfgEnvases.prototype.getById = function(id) {
  return this.cfgEnvases.filter(env => env.cenvIdenvase == id);
};

CfgEnvases.prototype.fillSelect = function(jqIdSelect) {

  this.cfgEnvases.forEach(envase => {
    const opt = new Option(envase.cenvIdenvase, envase.cenvDescripcion);
    $(jqIdSelect).append($(opt));
  });
  $(jqIdSelect).selectpicker('refresh')
};
