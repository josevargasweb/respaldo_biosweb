function CfgSecciones() {
    this.misSecciones = [];
    this.csecIdseccion;
    this.csecCodigo;
    this.csecDescripcion;
    this.csecActiva;
}

function getSeccionesSTEM(svc,f) {
    let url = gCte.getctx + "/api/dominio/secciones/list/sm";
    $.get(url, function (data) {
        console.log(data);
        svc.misSecciones = data;
        svc.fillSelect("#seccionSelect");
        if (f !== undefined){
        f();
        }
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

CfgSecciones.prototype.getById = function (id) {
    return this.misSecciones.filter((svc) => svc.csecCodigo == id);
};

CfgSecciones.prototype.fillSelect = function (jqIdSelect) {
    this.misSecciones.forEach((seccion) => {
        const opt = new Option(seccion.csecDescripcion, seccion.csecIdseccion);
        $(jqIdSelect).append($(opt));
    });
    $(jqIdSelect).selectpicker("refresh");
};

CfgSecciones.prototype.getSeccionesSTEM = function (callback) {
    let url = gCte.getctx + "/api/dominio/secciones/list/sm";
    $.get(url, function (data) {
        console.log(data);
        this.misSecciones = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};