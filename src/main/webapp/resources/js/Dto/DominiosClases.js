/**
 * 
 */


class LdvTiposReceptores {

  misTipos;
  jqIdSelect;

  constructor(jqIdSelect) {
    this.jqIdSelect = jqIdSelect;
    this.misTipos = [];
  }

  fillSelect() {

    this.misTipos.forEach((tipo) => {
      const opt = new Option(tipo.ldvtrDescripcion, tipo.ldvtrIdtiporeceptor);
      $(this.jqIdSelect).append($(opt));
    });

  }

  loadTipos() {
    let url = gCte.getctx + "/api/dominio/tiposreceptores/list";
    let me = this;
    $.get(url, function(data) {
      if (data.status !== 200) {
        handlerMessageError(data.message);
        return;
      }
      me.misTipos = data.dato;
      $(me.jqIdSelect).append(`<option value="" selected>Seleccione</option>`);
      me.misTipos.forEach((tipo) => {
        const opt = new Option(tipo.ldvtrDescripcion, tipo.ldvtrIdtiporeceptor);
        $(me.jqIdSelect).append($(opt));
      });
    })
      .done(function() {
        console.log("done");
      })
      .fail(function(err) {
        console.log(err);
      });
  }

}



class LdvViasNotificaciones {

  misVias;
  jqIdSelect;

  constructor(jqIdSelect) {
    this.jqIdSelect = jqIdSelect;
    this.misVias = [];
  }

  fillSelect() {
    $(this.jqIdSelect).append(`<option value="" selected>Seleccione</option>`);
    this.misVias.forEach((tipo) => {
      const opt = new Option(tipo.ldvvnDescripcion, tipo.ldvnnIdvianotificacion);
      $(this.jqIdSelect).append($(opt));
    });

  }

  loadVias() {
    let url = gCte.getctx + "/api/dominio/viasnotificaciones/list";
    let me = this;
    $.get(url, function(data) {
      if (data.status !== 200) {
        handlerMessageError(data.message);
        return;
      }
      me.misVias = data.dato;
      me.fillSelect();
    })
      .done(function() {
        console.log("done");
      })
      .fail(function(err) {
        console.log(err);
      });
  }

}

