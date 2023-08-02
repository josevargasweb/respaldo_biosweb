function CfgLaboratorios() {
    this.laboratorios = [];
    this.clabIdlaboratorio;
    this.clabCodigo;
    this.clabDescripcion;
    this.clabActivo;
}

CfgAntecedentes.prototype.constructor = function () {   
  this.laboratorios = new Array();
  };

CfgLaboratorios.prototype.getById = function (id) {
    return this.laboratorios.filter((svc) => svc.clabIdlaboratorio == id);
};

CfgLaboratorios.prototype.fillSelect = function (jqIdSelect) {
    this.laboratorios.forEach((lab) => {
        const opt = new Option(lab.clabDescripcion, lab.clabIdlaboratorio);
        $(jqIdSelect).append($(opt));
    });
    $(jqIdSelect).selectpicker("refresh");
};

CfgLaboratorios.prototype.getLaboratorios = function (me) {
    let jqXHR = $.ajax({
        url: gCte.getctx + "/api/instituciones/laboratorios/list",
        async: false,
        type: "GET",
        success:function(rpta){
          if (rpta.status != 200){
            handlerMessageError(rpta.message);
            return;
          }
          me.laboratorios = new Array();
          rpta.dato.forEach(t => me.laboratorios.push(t));
        }
    });
};

CfgLaboratorios.prototype.getLaboratorios = function () {
    var jqXHR = $.ajax({
        url: gCte.getctx + "/api/instituciones/laboratorios/list",
        async: false,
    });
    this.convertirArray(jqXHR.responseJSON);
};

CfgLaboratorios.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((tipo) => {
        arrayDato.push({
            id: tipo.clabIdlaboratorio,
            descripcion: tipo.clabDescripcion,
        });
    });
    this.laboratorios = arrayDato;
};

function CfgSecciones() {
	this.seccionesLab = [];
}

CfgSecciones.prototype.getSecciones = function (idLab,jqId) {
    let url = gCte.getctx + "/api/instituciones/secciones/list/"+idLab;
    var jqXHR = $.ajax({
        type: "GET",
        url:url,
        datatype: "json",
        async: false,
    });
    console.log("jqXHR.responseText",jqXHR.responseText);
    if(jqXHR.responseText !== "0"){
      let jqXHRJSON = JSON.parse(jqXHR.responseText);
      $(jqId).prop("disabled", false);
      this.convertirArray(jqXHRJSON);
    }else{
      this.seccionesLab = [];
    }
};

CfgSecciones.prototype.convertirArray = function (dato) {
    let arrayDato = [];
    dato.forEach((secc) => {
        arrayDato.push({ id: secc.csecIdseccion, descripcion: secc.csecDescripcion });
    });
    this.seccionesLab = arrayDato;
};

function CfgExamenes() {
	this.examenesSecc = [];
}

CfgExamenes.prototype.getExamenes = function (idSeccion,jqId) {
    let url = gCte.getctx + "/api/examenes/seccion/"+idSeccion;
    var jqXHR = $.ajax({
        type: "GET",
        url:url,
        datatype: "json",
        async: false,
    });
    console.log("jqXHR.responseText",jqXHR.responseText);
    if(jqXHR.responseText !== "0"){
      let jqXHRJSON = JSON.parse(jqXHR.responseText);
      $(jqId).prop("disabled", false);
      this.convertirArray(jqXHRJSON);
    }else{
      this.examenesSecc = [];
    }
};

CfgExamenes.prototype.convertirArray = function (json) {
    let arrayDato = [];
    json.dato.forEach((examen) => {
        arrayDato.push({ id: examen.ceIdexamen, descripcion: examen.ceAbreviado });
    });
    this.examenesSecc = arrayDato;
};