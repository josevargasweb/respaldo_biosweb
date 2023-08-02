function CfgGlosas() {
  this.misGlosas = [];
  this.cgIdglosa;
  this.cgCodigoglosa;
  this.cgDescripcion;
  this.cgtEsglosacritica;
  this.cgtSefirmaporlotes;
  this.cgIdSeccion;
}

CfgGlosas.prototype.getGlosas = function(testID, idSeccion) {
  var jqXHR = $.ajax({
    url: gCte.getctx + "/api/test/glosasTest/" + testID + "/" + idSeccion,
    async: false,
  });
  this.convertirArray(jqXHR.responseJSON);
};

CfgGlosas.prototype.convertirArray = function(dato) {
  let arrayDato = [];
  dato.forEach((glosa) => {
    arrayDato.push({
      id: glosa.cgIdglosa,
      descripcion: glosa.cgCodigoglosa + " - " + glosa.cgDescripcion + " " + glosa.cgIdSeccion
    });
  });
  this.misGlosas = arrayDato;
};

CfgGlosas.prototype.getGlosas = function(callback, testID, idSeccion) {
  let url = gCte.getctx + "/api/test/glosasTest/" + testID + "/" + idSeccion;
  $.get(url, function(data) {
    this.misGlosas = data;
    callback(data);
  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      console.log(err);
    });
};

CfgGlosas.prototype.fill = function(datos) {
  this.misGlosas = datos;
};

CfgGlosas.prototype.getById = function(id) {
  return this.misGlosas.filter((svc) => svc.cgIdglosa == id);
};

CfgGlosas.prototype.getBySeccion = function(idSeccion) {
  return this.misGlosas.filter((svc) => svc.cgIdSeccion == idSeccion);
};

CfgGlosas.prototype.fillSelect = function(jqIdSelect) {
  this.misGlosas.forEach((glosa) => {
    const opt = new Option(glosa.cgIdglosa, glosa.cgCodigoglosa + " - " + glosa.cgDescripcion + " " + glosa.cgIdSeccion);
    $(jqIdSelect).append($(opt));
  });
};

CfgGlosas.prototype.getAllGlosas = function() {
  let url = gCte.getctx + "/api/dominio/glosas/list";
  $.ajax({
    type: "get",
    url: url,
    success: this.exito,
    error: fracaso,
    contentType: 'application/json; charset=utf-8'
  });

};

CfgGlosas.prototype.exito = function(result, status, xhr) {
  let datos = result.dato;
  const n = datos.length;
  glosasMap = new Map();
  let curId = -1;
  for (let i = 0; i < n; i++) {
    let item = new Object();
    item.cg_CODIGOGLOSA = datos[i].cg_CODIGOGLOSA;
    item.ctr_CODIGO = datos[i].ctr_CODIGO;
    item.cg_DESCRIPCION = datos[i].cg_DESCRIPCION;
    item.ct_IDTEST = datos[i].ct_IDTEST;
    item.cg_IDGLOSA = datos[i].cg_IDGLOSA;
    item.cgt_ESGLOSACRITICA = datos[i].cgt_ESGLOSACRITICA;

    if (datos[i].ct_IDTEST !== curId) {
      glosasArray = new Array();
      if(glosasMap.get(datos[i].ct_IDTEST) != undefined){
        glosasArray.push(item,...glosasMap.get(datos[i].ct_IDTEST));
      }else{
        glosasArray.push(item);
      }
      glosasMap.set(datos[i].ct_IDTEST, glosasArray);
      curId = datos[i].ct_IDTEST;
    }
    else {
      glosasArray.push(item);
    }

  }


  let resultados = tableResultadosExamenesOrden.rows().data();
  if (resultados !== undefined && resultados.length !== 0) {
    let n = resultados.length;
    for (let i = 0; i < n; i++) {
      if (resultados[i].ctrcodigo === 'G') {
        // console.table(resultados[i]);
        let selectCur = $(`select[data-idtest="${resultados[i].testid}"]`);
        let gArray = glosasMap.get(resultados[i].testid);
        // console.log(selectCur);
        let m = gArray.length;
        selectCur.empty();
        selectCur.append(`<option selected disabled>Seleccione</option>`);
        for (let j=0; j< m;j++){
          if(gArray[j].cg_DESCRIPCION == resultados[i].result){
            selectCur.append(`<option value="${gArray[j].cg_IDGLOSA}" selected>${gArray[j].cg_DESCRIPCION}</option>`);
          }else{
            selectCur.append(`<option value="${gArray[j].cg_IDGLOSA}">${gArray[j].cg_DESCRIPCION}</option>`);

          }
        }
        
      }
    }

  }



}



