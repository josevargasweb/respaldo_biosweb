/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function CfgServicios() {
    this.misServicios = [];
    this.csIdservicio;
    this.cfgCentrosdesalud;
    this.csCodigo;
    this.csDescripcion;
    this.csEmail;
    this.csActivo;
    this.csIdCentroDeSalud;
}

CfgServicios.prototype.getServicios2 = function () {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/dominio/servicios/list",
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgServicios.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((tipo) => {
        arrayDato.push({
            id: tipo.csIdservicio,
            descripcion: tipo.csDescripcion,
        });
    });
    this.misServicios = arrayDato;
};

function getServicios(svc, callback) {
    let url = gCte.getctx + "/api/dominio/servicios/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misServicios = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

CfgServicios.prototype.getServicios = function (callback) {
    let url = gCte.getctx + "/api/dominio/servicios/list";
    $.get(url, function (data) {
        console.log(data);
        this.misServicios = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

CfgServicios.prototype.fill = function (datos) {
    this.misServicios = datos;
};

CfgServicios.prototype.getById = function (id) {
    return this.misServicios.filter((svc) => svc.csIdservicio == id);
};

CfgServicios.prototype.fillSelect = function (jqIdSelect) {
    this.misServicios.forEach((servicio) => {
        const opt = new Option(servicio.csCodigo, servicio.csDescripcion);
        $(jqIdSelect).append($(opt));
    });
};

function CfgSalas() {
    this.misSalas = [];
}

CfgSalas.prototype.getSalas = function (idServicios,jqId) {
    let url = gCte.getctx + "/Orden";
    var jqXHR = $.ajax({
        type: "post",
        url:url,
        data: "salasServicios=" + idServicios,
        datatype: "json",
        async: false,
    });
    console.log("jqXHR.responseText",jqXHR.responseText);
    if(jqXHR.responseText !== "0"){
      let jqXHRJSON = JSON.parse(jqXHR.responseText);
      $(jqId).prop("disabled", false);
      this.convertirArray(jqXHRJSON.salasS);
    }else{
      this.misSalas = [];
    }
};

CfgSalas.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((tipo) => {
        arrayDato.push({ id: tipo.idSalasS, descripcion: tipo.detalleSalasS });
    });
    this.misSalas = arrayDato;
};

function CfgCamas() {
    this.misCamas = [];
}

CfgCamas.prototype.getCamas = function (idServiciosSalas,jqId) {
    let url = gCte.getctx + "/Orden";
    var jqXHR = $.ajax({
        type: "post",
        url:url,
        data: "CamasSalas=" + idServiciosSalas,
        datatype: "json",
        async: false,
    });
    console.log(jqXHR);
    if(jqXHR.responseText !== "0"){
      let jqXHRJSON = JSON.parse(jqXHR.responseText);
      $(jqId).prop("disabled", false);
      this.convertirArray(jqXHRJSON.camasSalas);
    }else{
      this.misCamas = [];
    }
};

CfgCamas.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    console.log(dato);
    dato.forEach((tipo) => {
        arrayDato.push({ id: tipo.idCamasS, descripcion: tipo.detalleCamasS });
    });
    this.misCamas = arrayDato;
};



function CfgSecciones() {
    this.misSecciones = [];
    this.csecIdseccion;
    this.csecCodigo;
    this.csecDescripcion;
    this.csecActiva;
}

function getSeccionesCol(svc, callback) {
    let url = gCte.getctx + "/api/dominio/secciones/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misSecciones = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

function getSecciones(svc,f) {
    let url = gCte.getctx + "/api/dominio/secciones/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misSecciones = data;
        svc.fillSelect("#seccionSelect");
        $('.selectpicker').selectpicker('refresh');
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
    $('.selectpicker').selectpicker('refresh');
};

CfgSecciones.prototype.getSecciones = function (callback) {
    let url = gCte.getctx + "/api/dominio/secciones/list";
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

function f() {
    let columna = tableInfoOrdenes.column(9);
    let filas = columna.data();
    let m = filas.length;
    for (let i = 0; i < m; i++) {
        let n = filas[i].length;
        let valor = "";
        for (let j = 0; j < n; j++) {
            console.log(filas[i][j]);
            valor = valor.concat(filas[i][j]);
            console.log("v:" + valor);
        }
        tableInfoOrdenes.cell(i, 9).data(valor);
    }
}

function CfgMedicos() {
    this.misMedicos = [];
}

CfgMedicos.prototype.getMedicos = function () {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/dominio/obtenerMedicos/list",
        async: false,
    });
    
    this.convertirArray(jqXHR.responseJSON);
};

CfgMedicos.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((tipo) => {
        arrayDato.push({ id: tipo[0], descripcion: tipo[1] + " " + tipo[2] });
    });
    this.misMedicos = arrayDato;

    console.log(arrayDato);
};

function CfgTipoAtencion() {
    this.mistipos = [];
    this.ctaIdtipoatencion;
    this.ctaCodigo;
    this.ctaDescripcion;
    this.ctaActivo;
    this.ctaSort;
    this.ctaHost;
}

CfgTipoAtencion.prototype.getTiposAtencion2 = function () {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/dominio/tipoatencion/list",
        async: false,
        contentType: 'application/json',
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgTipoAtencion.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((tipo) => {
        arrayDato.push({
            id: tipo.ctaIdtipoatencion,
            descripcion: tipo.ctaDescripcion,
        });
    });
    this.mistipos = arrayDato;
};

function getTiposAtencion(svc, callback) {
    let url = gCte.getctx + "/api/dominio/tipoatencion/list";
    $.get(url, function (data) {
        svc.mistipos = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}
CfgTipoAtencion.prototype.getTiposAtencion = function (callback) {
    let url = gCte.getctx + "/api/dominio/tipoatencion/list";
    $.get(url, function (data) {
        console.log(data);
        this.mistipos = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

CfgTipoAtencion.prototype.getById = function (id) {
    return this.mistipos.filter((svc) => svc.ctaIdtipoatencion == id);
};

CfgTipoAtencion.prototype.fillSelect = function (jqIdSelect) {
    this.mistipos.forEach((tipo) => {
        const opt = new Option(tipo.ctaIdtipoatencion, tipo.ctaDescripcion);
        $(jqIdSelect).append($(opt));
    });
};

CfgTipoAtencion.prototype.genTipoAtencionFiller = function (jqIdSelect) {
    return function (data) {
        data.forEach((tipo) => {
            const opt = new Option(tipo.ctaIdtipoatencion, tipo.ctaDescripcion);
            $(jqIdSelect).append($(opt));
        });
    };
};

function CfgProcedencia() {
    this.misProcedencias = [];
    this.cpIdprocedencia;
    this.cpCodigo;
    this.cpDescripcion;
    this.cpSort;
    this.cpActivo;
    this.cpHorario;
    this.cpGrupo;
    this.cpTomamuestraautomatica;
    this.cpMembrete;
    this.cpIdtipoconexionhost;
    this.cpProcedenciarestringida;
    this.cpHost;
    this.cpHost2;
    this.cpHost3;
    this.cpVentabonos;
    this.cpActivarcuposcitas;
    this.cpCitascuposlunes;
    this.cpCitascuposmartes;
    this.cpCitascuposmiercoles;
    this.cpCitascuposjueves;
    this.cpCitascuposviernes;
    this.cpCitascupossabado;
    this.cpCitascuposdomingo;
    this.cpEmail;
}

function getProcedencias(svc, callback) {
    let url = gCte.getctx + "/api/dominio/procedencia/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misProcedencias = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

CfgProcedencia.prototype.getProcedencias2 = function () {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/dominio/procedencia/list",
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgProcedencia.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((tipo) => {
        arrayDato.push({
            id: tipo.cpIdprocedencia,
            descripcion: tipo.cpDescripcion,
        });
    });
    this.misProcedencias = arrayDato;
};

CfgProcedencia.prototype.getProcedencias = function (callback) {
    let url = gCte.getctx + "/api/dominio/procedencia/list";
    $.get(url, function (data) {
        this.misProcedencias = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

CfgProcedencia.prototype.fillSelect = function (jqIdSelect) {
    this.misProcedencias.forEach((tipo) => {
        const opt = new Option(proc.cpIdprocedencia, proc.cpDescripcion);
        $(jqIdSelect).append($(opt));
    });
};

function CfgEstadoExamen() {
    this.misEstados = [];
    this.ceeIdestadoexamen;
    this.ceeCodigoestadoexamen;
    this.ceeDescripcionestadoexamen;
}

CfgEstadoExamen.prototype.getById = function (id) {
    return this.misEstados.filter(
        (estado) => (estado.ceeCodigoestadoexamen == id)
    );
};

CfgEstadoExamen.prototype.fillSelect = function (jqIdSelect) {
    this.misEstados.forEach((tipo) => {
        const opt = new Option(estado.cpIdprocedencia, estado.cpDescripcion);
        $(jqIdSelect).append($(opt));
    });
};

function getEstadosExamen(svc, callback) {
    let url = gCte.getctx + "/api/dominio/estadoexamen/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misEstados = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

function CfgExamen() {
    this.misExamenes = [];
    this.ceIdexamen = null;
    this.ceCodigoexamen = null;
    this.ceDescripcion = null;
    this.ceAbreviado = null;
}

CfgExamen.prototype.getById = function (id) {
    return this.misExamenes.filter((ex) => ex.ceCodigoexamen == id);
};

CfgExamen.prototype.fillSelect = function (jqIdSelect) {
    this.misExamenes.forEach((ex) => {
        const opt = new Option(ex.ceAbreviado, ex.ceAbreviado);
        $(jqIdSelect).append($(opt));
    });
};

CfgExamen.prototype.getExamenes = function (callback) {
    let url = gCte.getctx + "/api/dominio/cfgexamen/listincmuestras";
    $.get(url, function (data) {
        this.misExamenes = data;
        callback(data);
    })
        .done(function () {
           // console.log("done jan");
        })
        .fail(function (err) {
            console.log(err);
        });
};

CfgExamen.prototype.getSoloExamenes = function (callback) {
    let url = gCte.getctx + "/api/dominio/cfgexamen/list";
    $.get(url, function (data) {
        this.misExamenes = data;
        callback(data);
    })
        .done(function () {
           // console.log("done jan");
        })
        .fail(function (err) {
            console.log(err);
        });
};



CfgExamen.prototype.getExamenesIncMuestras = function (callback) {
    let url = gCte.getctx + "/api/dominio/cfgexamen/listincmuestras";
    $.get(url, function (data) {
        this.misExamenes = data;
        callback(data);
    })
        .done(function () {
           // console.log("done jan");
        })
        .fail(function (err) {
            console.log(err);
        });
};



CfgProcedencia.prototype.fillSelect = function (jqIdSelect) {
    this.misProcedencias.forEach((tipo) => {
        const opt = new Option(proc.cpIdprocedencia, proc.cpDescripcion);
        $(jqIdSelect).append($(opt));
    });
};

function getExamenes(svc, callback) {
    let url = gCte.getctx + "/api/dominio/cfgexamen/listincmuestras";
    $.get(url, function (data) {
        console.log(data);
        svc.misExamenes = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

function getCfgExamenes(svc, callback, ds) {
    let url = gCte.getctx + "/api/dominio/cfgexamen/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misExamenes = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

function CfgPanel() {
    this.cpe_NOMBREPANELEXAMEN;
    this.misExamenesPanel = [];
}

function ExamenPanel() {
    this.cpe_NOMBREPANELEXAMEN;
    this.ce_IDEXAMEN;
    this.ce_DESCRIPCION;
    this.misTest = [];
}

function PanelExamenDTO() {
    this.cpe_NOMBREPANELEXAMEN;
    this.ce_IDEXAMEN;
    this.ce_DESCRIPCION;
    this.ct_IDTEST;
    this.ct_ABREVIADO;
}

function CfgPanelService() {
    this.panels = [];
    this.init();
}

CfgPanelService.prototype.fillPanelLst = function () {
    const n = this.panels.length;
    for (let i = 0; i < n; i++) {
        let panel = this.panels[i];
    }
};

CfgPanelService.prototype.init = function () {
    let url = gCte.getctx + "/api/dominio/panelexamenes/list";
    me = this;
    $.get(url, function (data) {
        if (data.status != 200) {
            handlerMessageError(data.message);
            return;
        }
        me.panels = data.dato;
        changeOptions(me);
        console.log(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            handlerMessageError(err);
        });
};

function changeOptions(panelSvc) {
    let examenSelect = $("#idPanelDualDataVisualSource");
    examenSelect.empty();
    let paneles = panelSvc.panels;
    let n = paneles.length;
    console.log("Numero de paneles " + n);

    for (let i = 0; i < n; i++) {
        const option = new Option(paneles[i].namePanel, paneles[i].itemPanel);
        examenSelect[0].append(option);
    }
}

function entryPanel() {
    this.panelName;
    this.lstPanelExamenTest = new Array();
}

function entryPanelExamen() {
    this.examenId;
    this.testLst = new Array();
}

function CfgLocalizacion() {}

function getProcedencias(svc, callback) {
    let url = gCte.getctx + "/api/dominio/procedencia/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misProcedencias = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}

CfgProcedencia.prototype.getProcedencias = function (callback) {
    let url = gCte.getctx + "/api/dominio/procedencia/list";
    $.get(url, function (data) {
        this.misProcedencias = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

CfgProcedencia.prototype.fillSelect = function (jqIdSelect) {
    this.misProcedencias.forEach((tipo) => {
        const opt = new Option(proc.cpIdprocedencia, proc.cpDescripcion);
        $(jqIdSelect).append($(opt));
    });
};


function CfgPrioridad() {
    this.misPrioridades = [];
}

CfgPrioridad.prototype.getPrioridades = function () {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/dominio/prioridadAtencion/list",
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgPrioridad.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((tipo) => {
        arrayDato.push({ id: tipo.cpaIdprioridadatencion, descripcion: tipo.cpaDescripcion });
    });
    this.misPrioridades = arrayDato;
};


function CfgConvenios() {
    this.misConvenios = [];
}

CfgConvenios.prototype.getConvenios = function () {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/dominio/obtenerConvenios/list",
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgConvenios.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((tipo) => {
        arrayDato.push({ id: tipo.ccIdconvenio, descripcion: tipo.ccDescripcion });
    });
    this.misConvenios = arrayDato;
};

function CfgDiagnosticos() {
    this.misDiagnosticos = [];
}

CfgDiagnosticos.prototype.getDiagnosticos = function () {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/dominio/obtenerDiagnosticos/list",
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgDiagnosticos.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((tipo) => {
        arrayDato.push({ id: tipo[0], descripcion: tipo[1] });
    });
    this.misDiagnosticos = arrayDato;
};


class TipoDocServicio {
    docTypes;
    constructor() {
        this.docTypes = [];
    }

    getById(id) {
        return this.docTypes.filter(
            (estado) => estado.ceeCodigoestadoexamen == id);
    }

    fillSelect(jqIdSelect) {
        this.docTypes.forEach((tipo) => {
            const opt = new Option(tipo.cpIdprocedencia, tipo.cpDescripcion);
            $(jqIdSelect).append($(opt));
        });
    }
}

function CfgTipoDocumento() {
    this.misTiposDeDocumento = [];
}

CfgTipoDocumento.prototype.getTiposDocumento = function (callback) {
    let url = gCte.getctx + "/api/dominio/tipodocumento/list";

    $.get(url, function (data) {
        this.misTiposDeDocumento = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

CfgTipoDocumento.prototype.fillSelect = function (jqIdSelect) {
    this.misTiposDeDocumento.forEach((documento) => {
        const opt = new Option(
            documento.ldvtdDescripcion,
            documento.ldvtdIdtipodocumento
        );
        $(jqIdSelect).append($(opt));
    });
};

CfgTipoDocumento.prototype.genTipoDocumentosFiller = function (jqIdSelect) {
    return function (data) {
        data.forEach((documento) => {
            const opt = new Option(
                documento.ldvtdDescripcion,
                documento.ldvtdIdtipodocumento
            );
            $(jqIdSelect).append($(opt));
        });
        $(jqIdSelect).val(TipoDocRun);
    };
};

function LdvSexo() {
    this.misSexos = [];
}

LdvSexo.prototype.getSexos = function (callback) {
    let url = gCte.getctx + "/api/dominio/sexo/list";

    $.get(url, function (data) {
        this.misSexos = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

LdvSexo.prototype.fillSelect = function (jqIdSelect) {
    this.misSexos.forEach((sexo) => {
        const opt = new Option(sexo.ldvsDescripcion, sexo.ldvsIdsexo);
        $(jqIdSelect).append($(opt));
    });
};

LdvSexo.prototype.genSexosFiller = function (jqIdSelect) {
    return function (data) {
        data.forEach((sexo) => {
            const opt = new Option(sexo.ldvsDescripcion, sexo.ldvsIdsexo);
            $(jqIdSelect).append($(opt));
        });
        $(jqIdSelect).val(TipoDocRun);
    };
};

function LdvRegion() {
    this.misRegiones = [];
}

LdvRegion.prototype.getRegiones = function (callback) {
    let url = gCte.getctx + "/api/dominio/region/list";

    $.get(url, function (data) {
        this.misRegiones = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

LdvRegion.prototype.fillSelect = function (jqIdSelect) {
    this.misRegiones.forEach((region) => {
        const opt = new Option(region.ldvrDescripcion, region.ldvrIdregion);
        $(jqIdSelect).append($(opt));
    });
};

LdvRegion.prototype.genRegionesFiller = function (jqIdSelect) {
    return function (data) {
        data.forEach((region) => {
            const opt = new Option(region.ldvrDescripcion, region.ldvrIdregion);
            $(jqIdSelect).append($(opt));
        });
    };
};

function LdvEstadoCivil() {
    this.misEstadoCiviles = [];
}

LdvEstadoCivil.prototype.getEstadosCiviles = function (callback) {
    let url = gCte.getctx + "/api/dominio/estadocivil/list";

    $.get(url, function (data) {
        this.misEstadoCiviles = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

LdvEstadoCivil.prototype.fillSelect = function (jqIdSelect) {
    this.misEstadoCiviles.forEach((EstadoCivil) => {
        const opt = new Option(
            EstadoCivil.ldvecDescripcion,
            EstadoCivil.ldvecIdestadocivil
        );
        $(jqIdSelect).append($(opt));
    });
};

LdvEstadoCivil.prototype.genEstadoCivilesFiller = function (jqIdSelect) {
    return function (data) {
        data.forEach((EstadoCivil) => {
            const opt = new Option(
                EstadoCivil.ldvecDescripcion,
                EstadoCivil.ldvecIdestadocivil
            );
            $(jqIdSelect).append($(opt));
        });
    };
};

function LdvPais() {
    this.misPaises = [];
}

LdvPais.prototype.getPaises = function (callback) {
    let url = gCte.getctx + "/api/dominio/pais/list";

    $.get(url, function (data) {
        this.misPaises = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

LdvPais.prototype.fillSelect = function (jqIdSelect) {
    this.misPaises.forEach((Pais) => {
        const opt = new Option(Pais.ldvpDescripcion, Pais.ldvpIdpais);
        $(jqIdSelect).append($(opt));
    });
};

LdvPais.prototype.genPaisesFiller = function (jqIdSelect) {
    return function (data) {
        data.forEach((Pais) => {
            const opt = new Option(Pais.ldvpDescripcion, Pais.ldvpIdpais);
            $(jqIdSelect).append($(opt));
        });
    };
};

function LdvPatologia() {
    this.misPatologiaes = [];
}

LdvPatologia.prototype.getPatologias = function (callback) {
    let url = gCte.getctx + "/api/dominio/patologia/list";

    $.get(url, function (data) {
        this.misPatologias = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

LdvPatologia.prototype.fillSelect = function (jqIdSelect) {
    this.misPatologias.forEach((Patologia) => {
        const opt = new Option(
            Patologia.ldvpatDescripcion,
            Patologia.ldvpatIdpatologia
        );
        $(jqIdSelect).append($(opt));
    });
};

LdvPatologia.prototype.genPatologiasFiller = function (jqIdSelect) {
    return function (data) {
        data.forEach((Patologia) => {
            const opt = new Option(
                Patologia.ldvpatDescripcion,
                Patologia.ldvpatIdpatologia
            );
            $(jqIdSelect).append($(opt));
        });
    };
};

function LdvContactopacientesrelacion() {
    this.misContactopacientesrelacion = [];
}

LdvContactopacientesrelacion.prototype.getContactopacientesrelacion = function (
    callback
) {
    let url = gCte.getctx + "/api/dominio/contactopacientesrelacion/list";

    $.get(url, function (data) {
        this.misContactopacientesrelacion = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

LdvContactopacientesrelacion.prototype.fillSelect = function (jqIdSelect) {
    this.misContactopacientesrelacion.forEach((Contactopacientesrelacion) => {
        const opt = new Option(
            Contactopacientesrelacion.ldvpatDescripcion,
            Contactopacientesrelacion.ldvpatIdpatologia
        );
        $(jqIdSelect).append($(opt));
    });
};

LdvContactopacientesrelacion.prototype.genContactopacientesrelacionFiller =
    function (jqIdSelect) {
        return function (data) {
            data.forEach((Patologia) => {
                const opt = new Option(
                    Contactopacientesrelacion.ldvpatDescripcion,
                    Contactopacientesrelacion.ldvpatIdpatologia
                );
                $(jqIdSelect).append($(opt));
            });
        };
    };
 

 
    function CfgMicrobiologia() {
        this.misMicrobiologia = [];
    }
    
    CfgMicrobiologia.prototype.getMicrobiologia = function () {
        var jqXHR = $.ajax({
            url: gCte.getctx + "/api/microbiologia/getListMicrobiology",
            async: false,
        });
        if(jqXHR.responseJSON.status == 200){
            this.misMicrobiologia = jqXHR.responseJSON.dato;
        }
    };
    


    function CfgStatus() {
        this.misStatus = {};
    }
    
    CfgStatus.prototype.getStatus = function () {
        var jqXHR = $.ajax({
            url: gCte.getctx + "/api/microbiologia/getListStatus",
            async: false,
        });
        if(jqXHR.responseJSON.status == 200){
            this.misStatus = jqXHR.responseJSON.dato;
        }
    };
    

    CfgStatus.prototype.obtenerId = function (dato) {
        if(!jQuery.isEmptyObject(this.misStatus)){
            const key = Object.keys(this.misStatus).find(k => this.misStatus[k] === dato);
            return parseInt(key);
        }else{
            return "";
        }
};



function CfgUsuario() {
    this.misUsuarios = [];
}

CfgUsuario.prototype.getUsuario = function () {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/usuarios",
        async: false,
    });
    this.misUsuarios = jqXHR.responseJSON.dato;
};

CfgUsuario.prototype.obtenerPorId = function (dato) {
    return $.grep(this.misUsuarios, function(e){ return e.duIdusuario == dato; });
};

