/**
 * 
 */
 
 
function ItemPanelService(){
  
  this.panels=[];
}

ItemPanelService.prototype.init = function() {

  let url = gCte.getctx + '/api/dominio/itempanelexamenes/list';
  me = this;
  $.get(url, function(data) {

    if (data.status != 200) {
      handlerMessageError(data.message);
      return;
    }
    me.panels = data.dato;
//    me.fillPanelLst();
    changeOptions(me);

    console.log(data);
  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      handlerMessageError(err);
    });
}


ItemPanelService.prototype.getPaneles = function(callback) {
  let url = gCte.getctx + '/api/dominio/itempanelexamenes/list';

  $.get(url, function(data) {
    this.misExamenes = data;
    callback(data.dato);
  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      console.log(err);
    });
};
