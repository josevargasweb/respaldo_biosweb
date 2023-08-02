function CfgMetodos() {
    this.misMetodos = [];
    this.cmetIdmetodo;
    this.cmetDescripcion;
    this.cmtActivo;
    this.cmtEsvalorpordefecto;
    this.ctIdtest;
}

CfgMetodos.prototype.getMetodos = function (testID) {
    var jqXHR = $.ajax({
        //url: getctx + "/api/test/metodos",
        url: getctx + "/api/test/metodosTest/"+testID,
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgMetodos.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((metodo) => {
        arrayDato.push({
            id: metodo.cmetIdmetodo,
            descripcion: metodo.cmetDescripcion,
        });
    });
    this.misMetodos = arrayDato;
};

function getMetodos(svc, callback,testID) {
    //let url = getctx + "/api/test/metodos";
    let url = getctx + "/api/test/metodosTest/"+testID;
    $.get(url, function (data) {
        console.log(data);
        svc.misMetodos = data;
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

CfgMetodos.prototype.getMetodos = function (callback,testID) {
    //let url = getctx + "/api/test/metodos";
    let url = getctx + "/api/test/metodosTest/"+testID;
    $.get(url, function (data) {
        console.log(data);
        this.misMetodos = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

CfgMetodos.prototype.fill = function (datos) {
    this.misMetodos = datos;
};

CfgMetodos.prototype.getById = function (id) {
    return this.misMetodos.filter((svc) => svc.cmetIdmetodo == id);
};

CfgMetodos.prototype.fillSelect = function (jqIdSelect) {
    this.misMetodos.forEach((metodo) => {
        const opt = new Option(metodo.cmetIdmetodo, metodo.cmetDescripcion);
        $(jqIdSelect).append($(opt));
    });
};
