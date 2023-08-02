function CfgTests() {
    this.me = this;
    this.misTests = [];
    this.misTestsNumericos = [];
    this.ctIdtest;
    this.ctCodigo;
    this.ctAbreviado;
    this.cmueIdtipomuestra;
    this.cmueDescripcionabreviada;
    this.csecDescripcion;
    this.cmetDescripcion;
    this.ctActivo;
}

CfgTests.prototype.getTests = function (idSeccion) {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/test/examenes/"+idSeccion,
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgTests.prototype.getTestsBySeccionExamen = function (idSeccion, idExamen) {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/test/examenes/"+idSeccion+"/"+idExamen,
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgTests.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((test) => {
        arrayDato.push({
            id: test.cfgTest.ctIdtest,
            descripcion: test.cfgTest.ctAbreviado + " " + test.cfgTest.ctDescripcion
        });
    });
    this.misTests = arrayDato;
};

function getTests(svc, callback,idSeccion) {
    let url = gCte.getctx + "/api/test/examenes/" + idSeccion;
    $.get(url, function (data) {
        console.log(data);
        svc.misTests = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

function getTestsBySeccionExamen(svc, callback, idSeccion, idExamen) {
    let url = gCte.getctx + "/api/test/examenes/" + idSeccion + "/" + idExamen;
    $.get(url, function (data) {
        console.log(data);
        svc.misTests = data;
        //svc.cfgMetodostest = data.cfgMetodostest;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

CfgTests.prototype.getTests = function (callback, idSeccion) {
    let url = gCte.getctx + "/api/test/examenes/" + idSeccion;
    $.get(url, function (data) {
        console.log(data);
        this.misTests = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};


CfgTests.prototype.getTestsBySeccionExamen = function (callback, idSeccion, idExamen) {
    let url = gCte.getctx + "/api/test/examenes/" + idSeccion + "/" + idExamen;
    $.get(url, function (data) {
        console.log(data);
        this.misTests = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

CfgTests.prototype.fill = function (datos) {
    this.misTests = datos;
};

CfgTests.prototype.getById = function (id) {
    return this.misTests.filter((svc) => svc.cfgTest.ctIdtest == id);
};

CfgTests.prototype.fillSelect = function (jqIdSelect) {
    this.misTests.forEach((test) => {
        const opt = new Option(test.cfgTest.ctIdtest, test.cfgTest.ctAbreviado + " " + test.cfgTest.ctDescripcion);
        $(jqIdSelect).append($(opt));
    });
};

CfgTests.prototype.getTestNumericos = function (me) {
    let jqXHR = $.ajax({
        url: gCte.getctx + "/api/test/numericos",
        async: false,
        success:function(rpta){
          if (rpta.status != 200){
            handlerMessageError(rpta.message);
            return;
          }
          me.misTestsNumericos = new Array();
          rpta.dato.forEach(t => me.misTestsNumericos.push(t));
          //this.misTestsNumericos = rpta.dato;
        }
    });
};




function mostrarSpinner() {
    $(".container-main-content").append(`
    <div id="overlay"></div>
    <div id="loading-spinner"></div>
    `);
    $("#loading-spinner").show();
    $("#overlay").show();
  }


  function ocultarSpinner() {
    $("#loading-spinner").hide();
    $("#overlay").hide();
    $("#overlay").remove();
    $("#loading-spinner").remove();
  }