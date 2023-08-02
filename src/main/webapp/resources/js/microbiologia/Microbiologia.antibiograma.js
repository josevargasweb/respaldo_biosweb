
// antibiograma
$(document).ready(function () {
    var tableAntiogramaAntibiotico = null;
    initTableAntiogramaAntibiotico("#antibioticoDatatable");
});

let antibioticosObj = {
    id:"",
    orderid: "",
    examid: "",
    testid: "",
    test: "",
    colonycount: "",
    infectiontype: "",
    antibioticLists: {
        1: {
            microorganism: {},
            antibiogram: {},
            methods: [],
            antibiotics: [],
        },
        2: {
            microorganism: {},
            antibiogram: {},
            methods: [],
            antibiotics: [],
        },
        3: {
            microorganism: {},
            antibiogram: {},
            methods: [],
            antibiotics: [],
        },
        4: {
            microorganism: {},
            antibiogram: {},
            methods: [],
            antibiotics: [],
        },
    },
};


let objectoNuevo;
let antibiogramaOpcionales;

function rellenarAntibiograma(antibioticos) {
    console.log('rellenando',antibioticos)
    antibioticosObj.id = antibioticos.id;
    antibioticosObj.orderid = antibioticos.orderid;
    antibioticosObj.examid = antibioticos.examnid;
    antibioticosObj.testid = antibioticos.testid;
    antibioticosObj.test = antibioticos.test;
    antibioticosObj.colonycount = antibioticos.colonycount;
    antibioticosObj.infectiontype = antibioticos.infectiontype;
    antibioticosObj.antibioticLists = antibioticos.antibioticLists;

    //items seleccionados
    $("#origenAntibioText").text(antibioticos.test);

    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let antibiocoLista = antibioticos.antibioticLists[parseInt(nAislado)];

    let microorganismo = document.getElementById(
        "idTestCanvasAntibiogramMicroorganism",
    );

  

    //rellena Microorganismo
    if (
        antibiocoLista.microorganism != null && typeof antibiocoLista.microorganism.name !== "undefined" &&
        antibiocoLista.microorganism.name != ""
    ) {
        microorganismo.value = String(antibiocoLista.microorganism.name);
        microorganismo.setAttribute("disabled", true);
        loadInfoMicroOrgani(
            antibiocoLista,
            antibioticos.colonycount,
            antibioticos.infectiontype,
        );
    }else{
        let tieneClase = document.getElementById("idTestCanvasAntibiogramMicroorganism").classList.contains("disabled-element");
        if(tieneClase){
            microorganismo.setAttribute("disabled", true);
        }
    }
    
}

//copiar un objeto quitando la refencia
function rellenarObjectoNuevo(antibioticos) {
    // Convertir objeto original a formato JSON
  let json = JSON.stringify(antibioticos);
  // Convertir formato JSON a objeto y almacenar en la variable inicializada
    return JSON.parse(json);
  }
  

$("#idTestCanvasAntibiogramIsolatedNumber").change(function () {
    eventoLimpiar();
    eventoLimpiarOpcional();
    eventoLimpiarMetodo();
    limpiarSelectedAnti();
    //items seleccionados
    let nAislado = $(this).val();

    let antibiocoLista = antibioticosObj.antibioticLists[parseInt(nAislado)];

    let microorganismo = document.getElementById(
        "idTestCanvasAntibiogramMicroorganism",
    );

    if (
        antibiocoLista.microorganism != null && typeof antibiocoLista.microorganism.name !== "undefined" &&
        antibiocoLista.microorganism.name != ""
    ) {
        microorganismo.value = String(antibiocoLista.microorganism.name);
        microorganismo.setAttribute("disabled", true);

        loadInfoMicroOrgani(
            antibiocoLista,
            antibioticosObj.colonycount,
            antibioticosObj.infectiontype,
        );
    }
    
});
$("#idTestCanvasAntibiogramMicroorganism").change(function () {
    //items seleccionados
    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let microorga = $(this).val();

    antibioticosObj.antibioticLists[parseInt(nAislado)].microorganism = {
        name: microorga,
        id: microorga,
    };
});



function loadInfoOpcionales(arr){
    $.ajax({
        url: `${gCte.getctx}/api/microbiologia/getlistantibiogramaantibiotico?antibiogramName=${arr.antibiogram != null &&  typeof arr.antibiogram.name !== "undefined" ? arr.antibiogram.name : ""}`,
        method: "get",
        dataType: "json",
        success: function (antibioticos) {
            if(antibioticos.status !== undefined && antibioticos.status == 200){
                loadAntibioticosTable(arr.antibiotics,antibioticos.dato);
            }
        },
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        },
    });
}

function loadInfoMicroOrgani(antibiocoLista, colonycount, infectiontype) {
    //load Antibiograma
    $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function (samples) {
            let tieneClase = document.getElementById("idTestCanvasAntibiogramType").classList.contains("disabled-element");
            if(!tieneClase){
                $("#idTestCanvasAntibiogramType").removeAttr("disabled");
            }
            $("#idTestCanvasAntibiogramType").empty();

            let htmlOption = "";
            let selected = "selected";
            samples.antibiogramTypes.forEach((o, i) => {
                if (
                    antibiocoLista.antibiogram != null &&
                    typeof antibiocoLista.antibiogram.name !== "undefined" &&
                    antibiocoLista.antibiogram.name != "" &&
                    antibiocoLista.antibiogram.name == o
                ) {
                    selected = "";
                    htmlOption +=
                        '<option value="' +
                        o +
                        '"  selected>' +
                        o +
                        "</option>";
                } else {
                    htmlOption +=
                        '<option value="' + o + '" >' + o + "</option>";
                }
            });
            $("#idTestCanvasAntibiogramType").append(
                '<option value="" disabled ' +
                    selected +
                    ">Seleccione</option>",
            );
            $("#idTestCanvasAntibiogramType").append(htmlOption);
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });

    //load Recuento de colonias
    $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function (samples) {
            let tieneClase = document.getElementById("idTestCanvasAntibiogramColoniesCount").classList.contains("disabled-element");
            if(!tieneClase){
                $("#idTestCanvasAntibiogramColoniesCount").removeAttr("disabled");
            }
            $("#idTestCanvasAntibiogramColoniesCount").empty();
            let htmlOption = "";
            let selected = "selected";
            samples.coloniesCount.forEach((o, i) => {
                if (colonycount != "" && colonycount == o) {
                    selected = "";
                    htmlOption +=
                        '<option value="' +
                        o +
                        '"  selected>' +
                        o +
                        "</option>";
                } else {
                    htmlOption +=
                        '<option value="' + o + '" >' + o + "</option>";
                }
            });
            $("#idTestCanvasAntibiogramColoniesCount").append(
                '<option value="" disabled ' +
                    selected +
                    ">Seleccione</option>",
            );
            $("#idTestCanvasAntibiogramColoniesCount").append(htmlOption);
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });

    //load Tipo de infección
    $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function (samples) {
            let tieneClase = document.getElementById("idTestCanvasAntibiogramInfectionType").classList.contains("disabled-element");
            if(!tieneClase){
                $("#idTestCanvasAntibiogramInfectionType").removeAttr("disabled");
            }
            $("#idTestCanvasAntibiogramInfectionType").empty();
            let htmlOption = "";
            let selected = "selected";
            samples.infectionTypes.forEach((o, i) => {
                if (infectiontype != "" && infectiontype == o) {
                    selected = "";
                    htmlOption +=
                        '<option value="' +
                        o +
                        '"  selected>' +
                        o +
                        "</option>";
                } else {
                    htmlOption +=
                        '<option value="' + o + '" >' + o + "</option>";
                }
            });
            $("#idTestCanvasAntibiogramInfectionType").append(
                '<option value="" disabled ' +
                    selected +
                    ">Seleccione</option>",
            );
            $("#idTestCanvasAntibiogramInfectionType").append(htmlOption);
        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
            return;
        });

        if (
            antibiocoLista.antibiogram != null &&
            typeof antibiocoLista.antibiogram.name !== "undefined" &&
            antibiocoLista.antibiogram.name != "" 
        ) {
            $("#idTestCanvasAntibiogramsAddAntibiotic").removeAttr("disabled");
            $("#idAntibiogramsAddResistanceMethodOptional").removeAttr("disabled");
        }


    loadInfoOpcionales(antibiocoLista);
    loadResistenciaTable(antibiocoLista.methods);
    enableDisableAddData();
}

function loadAntibioticosTable(antibiotics,antibiotics2) {
    const difAntibiotics = getDifferenceAntibiotics(antibiotics,antibiotics2, "id");

    antibiotics.forEach((itemex) => {
        let dataItemAux = new DataItem(
            itemex.id,
            itemex.name,
            itemex.code,
            itemex.active,
            itemex.opcional,
            itemex.incluir,
            itemex.cim,
            itemex.diameter,
            itemex.etest,
            itemex.interpretation,
            itemex.includeinreport,
        );
        let itemAux = new Item(dataItemAux);
        vt.addItem(itemAux);
        vs.removeItemById(itemex.id);
        if (itemex.opcional == "S") {
            let dataItemAux = new DataItem(
                itemex.id,
                itemex.name,
                itemex.code,
                itemex.active,
                itemex.opcional,
                itemex.incluir,
                itemex.cim,
                itemex.diameter,
                itemex.etest,
                itemex.interpretation,
                itemex.includeinreport,
            );
            let itemAux = new Item(dataItemAux);
            vt.addItemOptional(itemAux);
        }
    });

    let keys = Object.getOwnPropertyNames(difAntibiotics);

    if(keys.length > 0){
        for (const element of keys) {
            if (difAntibiotics[element].opcional == "S") {
                let dataItemAux = new DataItem(
                    difAntibiotics[element].id,
                    difAntibiotics[element].name,
                    difAntibiotics[element].code,
                    difAntibiotics[element].active,
                    difAntibiotics[element].opcional,
                    difAntibiotics[element].incluir,
                    difAntibiotics[element].cim,
                    difAntibiotics[element].diameter,
                    difAntibiotics[element].etest,
                    difAntibiotics[element].interpretation,
                    difAntibiotics[element].includeinreport,
                );
                let itemAux = new Item(dataItemAux);
                vt.addItemOptional(itemAux);
            }
          }

    }

   
}

function enableDisableAddData(){
    let tieneClase = document.getElementById("idSearcher").classList.contains("disabled-element");
    if(tieneClase){
        $("#idSearcher").attr("disabled", "disabled");
    }else{
        $("#idSearcher").removeAttr("disabled");
    }
    
    let tieneClase2 = document.getElementById("idDualDataVisualSource").classList.contains("disabled-element");
    if(tieneClase2){
        $("#idDualDataVisualSource").attr("disabled", "disabled");
    }else{
        $("#idDualDataVisualSource").removeAttr("disabled");
    }
   
    let tieneClaseAdd = document.getElementById("clickAddBtn").classList.contains("disabled-element");
    if(tieneClaseAdd){
        $("#clickAddBtn").attr("disabled", "disabled");
    }else{
        $("#clickAddBtn").removeAttr("disabled");
    }
   
    let tieneClaseDel = document.getElementById("clickDelBtn").classList.contains("disabled-element");
    if(tieneClaseDel){
        $("#clickDelBtn").attr("disabled", "disabled");
    }else{
        $("#clickDelBtn").removeAttr("disabled");
    }
}

let loadAntibiogramaAntibiotico = function (
    nroOrden,
    examid,
    testid,
    numeroAislado,
    key = false,
) {
    let url =
        gCte.getctx +
        `/Microbiologia/api/getTestData?orderId=${nroOrden}&examId=${examid}&testId=${testid}`;
    let dataSrc = `antibiogram.antibioticList[${numeroAislado}].antibiotics`;
    tableAntiogramaAntibiotico.ajax.url(url).load().draw();
    // $("#accordionTestResultados").collapse('show');
};


let initTableAntiogramaAntibiotico = function (jqDtId) {
    let nroOrden = -1;
    let examid = -1;
    let testid = -1;
    let numeroAislado = 1;
    tableAntiogramaAntibiotico = $(jqDtId).DataTable({
        ajax: {
            url:
                gCte.getctx +
                `/Microbiologia/api/getTestData?orderId=${nroOrden}&examId=${examid}&testId=${testid}`,
            type: "GET",
            dataSrc: "antibiogram",
        },
        language: {
            thousands: ".",
            decimal: ",",
            emptyTable: "No hay datos disponibles",
            loadingRecords: "Por favor espere - cargando ...",
            zeroRecords: "No se encontraron datos",
        },
        info: false,
        lengthChange: false,
        pageLength: 1,
        autoWidth: false,
        paging: false,
        searchDelay: 500,
        orderCellsTop: true,
        scrollY: "100px",
        scrollX: true,
        columnDefs: [
            {
                targets: 0,
                width: "30px",
                className: "dt-left",
                orderable: false,
                render: function (data, type, full, meta) {
                    console.log("dasdsadsadad", data, type, full, meta);
                    return data;
                },
            },
        ],
        select: {
            style: "multi",
            //selector: 'td:first-child',
            selector: "td:not(:last-child)",
            items: "cell",
        },
        columns: [
            { data: "antibioticLists.antibioticLists[1].antibiotic" },
            { data: "antibioticLists.antibioticLists[1].cim" },
            { data: "antibioticLists.antibioticLists[1].diameter" },
            { data: "antibioticLists.antibioticLists[1].interpretation" },
            { data: "antibioticLists.antibioticLists[1].includeinreport" },
        ],
        initComplete: function (settings, json) {
            // $("#resultadosExamenesOrdenesDatatable_filter").hide();
            // tableResultadosExamenesOrden.filtro = myTableColFilter;
            // let rowFilter = $('<tr class="filter"></tr>').appendTo($(tableResultadosExamenesOrden.table().header()));
            // this.api().columns().every(function() {
            //   let columna = this;
            //   tableResultadosExamenesOrden.filtro(columna.index(), rowFilter, tableResultadosExamenesOrden);
            // });
        },
    });
};

function showAntibiogramTest() {
    setMicroorganismOptions();
    setAntibiogramaTypesOptions();
    setAntibiogramColoniesCountOptions();
    setAntibiogramInfectionTypeOptions();
    // showAntibiogramAntibioticLista();
    showAntibiogramResistanceMethodList();
    showAddAntibioticList();
    // showAddResistanceMethodList();
    showOptionalAntibioticList();
    enableOrDisableIfMO();
    enableOrDisableRM();
    enableOrDisableAB();
}

function setAntibiogramaTypesOptions() {
    let options = getAntibiogramOptions().antibiogramTypes;
    $("#idTestCanvasAntibiogramType").empty();
    options.forEach(function (antibiogramTypeName) {
        $("#idTestCanvasAntibiogramType").append(
            `<option value="` +
                antibiogramTypeName +
                `">` +
                antibiogramTypeName +
                `</option>`,
        );
    });
    $("#idTestCanvasAntibiogramType").val(
        localReadAntibiogram().antibioticLists[
            localReadCurrentAntibioticListIndex()
        ].antibiogram.name,
    );
}

$("#idTestCanvasAntibiogramIsolatedNumber").change(function () {
    // localStoreCurrentAntibioticList();
    // localSetCurrentAntibioticListIndex($(this).val());
    // showAntibiogramTest();
});

//pasar datos entre tablas
$("#idTestCanvasAntibiogramsAddAntibiotic").on("click", function () {
    if ($("#idTestCanvasAntibiogramType").val() != "") {
        $("#TestCanvasAntibiogramsAddAntibiotic").modal("show");
    }
});

function CfgAntibioticos() {
    this.misAntibioticos = [];
}

CfgAntibioticos.prototype.getAnbitioticos = function (callback) {
    let url =  gCte.getctx + `/Microbiologia/Mantenedores/api/getABList`;
    $.get(url, function (data) {
        this.misAntibioticos = data;
        callback(data);
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
};

var dualDataBS;
var dualDataSource = new Array();
var dualDataTarget = new Array();
var vt;
var vs;
var visualSearcher;
var dataSearcher;

$("#idSearcher").attr("autocomplete", "off");

var dtVT = new DataTableVisualTarget("#idDataTarget"); // tabla protectada con datos en modal
var dtFVT = new DatatableFullVisualTarget("#idDataTargetFull"); // tabla protectada con datos fuera del modal
var dtOVT = new DatatableOpcionalVisualTarget("#idDataTargetOpcional"); // tabla protectada con datos fuera del modal
var dt = new DataSource([]);
vt = new VisualTarget(dt, dtVT, dtFVT, dtOVT);
vs = new VisualSource();
test = genVSFiller("#idDualDataVisualSource", vs, "#idSearcher"); // tabla y buscador con datos en modal

var movedor = new Mover(dtVT, dtFVT, dtOVT, dt, vt, vs);

vs.onDoubleClick(movedor);
// let svc = new CfgExamen();
// svc.getExamenes(test);
let svc = new CfgAntibioticos();
svc.getAnbitioticos(test);

function eventoAddMultiple(antibioticos) {
    antibioticos.forEach((element) => {
        if (element.opcional != "S") {
            let dataItemAux = new DataItem(
                element.id,
                element.name,
                element.code,
                element.active,
                element.opcional,
                typeof element.incluir !== "undefined" &&
                element.incluir !== null
                    ? element.incluir
                    : "",
                typeof element.cim !== "undefined" && element.cim !== null
                    ? element.cim
                    : "",
                typeof element.diameter !== "undefined" &&
                element.diameter !== null
                    ? element.diameter
                    : "",
                    typeof element.etest !== "undefined" &&
                element.etest !== null
                    ? element.etest
                    : "",
                typeof element.interpretation !== "undefined" &&
                element.interpretation !== null
                    ? element.interpretation
                    : ""
                    ,
                typeof element.includeinreport !== "undefined" &&
                element.includeinreport !== null
                    ? element.includeinreport
                    : "S",
            );
            let itemAux = new Item(dataItemAux);
            vt.addItem(itemAux);
            vs.removeItemById(element.id);
        }
    });
}

function eventoAddMultipleOpcional(antibioticos) {
    antibioticos.forEach((element) => {
        if (element.opcional == "S") {
            let dataItemAux = new DataItem(
                element.id,
                element.name,
                element.code,
                element.active,
                element.opcional,
                typeof element.incluir !== "undefined" &&
                element.incluir !== null
                    ? element.incluir
                    : "",
                typeof element.cim !== "undefined" && element.cim !== null
                    ? element.cim
                    : "",
                typeof element.diameter !== "undefined" &&
                element.diameter !== null
                    ? element.diameter
                    : "",
                typeof element.etest !== "undefined" &&
                element.etest !== null
                    ? element.etest
                    : "",
                typeof element.interpretation !== "undefined" &&
                element.interpretation !== null
                    ? element.interpretation
                    : "",
                typeof element.includeinreport !== "undefined" &&
                element.includeinreport !== null
                    ? element.includeinreport
                    : "",
            );
            let itemAux = new Item(dataItemAux);
            vt.addItemOptional(itemAux);
        }
    });
}

function cambiarAntibioticos(antibioticos) {
    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let antibiocoLista = antibioticosObj.antibioticLists[parseInt(nAislado)];

    antibioticosObj.antibioticLists[parseInt(nAislado)].antibiotics = [];

    let anti = [];

    antibioticos.forEach((element) => {
        if (element.opcional != "S") {
            anti.push({
                code: element.code,
                name: element.name,
                active: element.active,
                id: element.id,
                class: element.class,
                opcional: element.opcional,
                incluir: "",
                cim: "",
                diameter: "",
                etest: "",
                interpretation: "",
                includeinreport: "S",
            });
        }
    });

    antibioticosObj.antibioticLists[parseInt(nAislado)].antibiotics = anti;

    let Antibiograma = $("#idTestCanvasAntibiogramType").val();

    antibioticosObj.antibioticLists[parseInt(nAislado)].antibiogram = {
        name: Antibiograma,
        id: Antibiograma,
    };
}

function eventoAdd() {
    let itemId = vs.getSelectedItemId();
    console.log("eventoadd", itemId);
    let item = vs.getSelectedItemById(itemId);
    console.log("getSelectedItemById", item);
    vs.removeItemById(itemId);
    vt.addItem(item);
    console.log(vt);
    agregarObjAntibioticos(item);
    //    vs.removePanelItem(item);
}

function eventoDel() {
    let items = vt.getSelectedItems();
    console.log("items", items);
    vt.removeItems(items);
    vs.addItems(items);
    eliminarObjAntibioticos(items[0]);
}

function eventoLimpiar() {
    let items = vt.getItems();
    vt.removeItems(items);
    vs.addItems(items);
}

function eventoLimpiarOpcional() {
    let items = vt.getItemsOpcional();
    vt.removeItemsOpcional(items);
}

function limpiarSelectedAnti() {
    $("#idTestCanvasAntibiogramMicroorganism").val("");

    let tieneClase = document.getElementById("idTestCanvasAntibiogramMicroorganism").classList.contains("disabled-element");
    if(!tieneClase){
        $("#idTestCanvasAntibiogramMicroorganism").removeAttr("disabled");
    }
    $("#idTestCanvasAntibiogramType").val("");
    $("#idTestCanvasAntibiogramType").attr("disabled", "disabled");
    $("#idTestCanvasAntibiogramColoniesCount").val("");
    $("#idTestCanvasAntibiogramColoniesCount").attr("disabled", "disabled");
    $("#idTestCanvasAntibiogramInfectionType").val("");
    $("#idTestCanvasAntibiogramInfectionType").attr("disabled", "disabled");
    $("#idTestCanvasAntibiogramsAddAntibiotic").attr("disabled", "disabled");
    $("#idAntibiogramsAddResistanceMethodOptional").attr("disabled", "disabled");
}



$("#clickAddBtn").click(eventoAdd);
$("#clickDelBtn").click(eventoDel);
$("#idPanelDualDataVisualSource").hide();

//selecciona antibiograma
$("#idTestCanvasAntibiogramType").change(function () {
    $.ajax({
        url: `${gCte.getctx}/api/microbiologia/getlistantibiogramaantibiotico?antibiogramName=${$(
            this,
        ).val()}`,
        method: "get",
        dataType: "json",
        success: function (antibioticos) {
            if(antibioticos.status !== undefined && antibioticos.status == 200){
                eventoLimpiar();
                eventoLimpiarOpcional();
                eventoAddMultiple(antibioticos.dato);
                eventoAddMultipleOpcional(antibioticos.dato);
                cambiarAntibioticos(antibioticos.dato);
            }
        },
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        },
    });
});

$("#idDataTargetOpcional").on("change", ".chck-anti", function (e) {
    e.preventDefault();
    let table = $("#idDataTargetOpcional").DataTable();
    let row = $(this).closest("tr");
    let that = table.row(row).data();
    if ($(this).is(":checked")) {
        that.dataValue.includeinreport = "S";
        that.dataValue.incluir = "S";

        console.log("este es el that",that)
        let item = vs.getSelectedItemById(that.id);
        vs.removeItemById(that.id);
        item.dataValue.includeinreport = "S";

        console.log('este es el item',item);
        vt.addItem(item);
        agregarObjAntibioticos(that)
        table.row(row).data(that).draw();
    } 
    // else {
    //     let newArr = [];
    //     newArr.push(that);
    //     vt.removeItems(newArr);
    //     vs.addItems(newArr);
    //     eliminarObjAntibioticos(that)
    // }
});

$("#idDataTargetFull").on("blur", ".input-cim, .input-diametro, .input-etest", function (e) {
    e.preventDefault();
    let table = $("#idDataTargetFull").DataTable();
    let row = $(this).closest("tr");
    let that = table.row(row).data();
    let cim = "";
    if (
        row.find(".input-cim").val() != null &&
        row.find(".input-cim").val() != ""
    ) {
        cim = row.find(".input-cim").val();
    }
    let diameter = "";
    if (
        row.find(".input-diametro").val() != null &&
        row.find(".input-diametro").val() != ""
    ) {
        diameter = row.find(".input-diametro").val();
    }
    let etest = "";
    if (
        row.find(".input-etest").val() != null &&
        row.find(".input-etest").val() != ""
    ) {
        etest = row.find(".input-etest").val();
    }

    if (diameter != "" || cim != "" || etest != "") {
        let metResis = new CfgResistance();
        metResis.getResistencia(that.searchValue, cim, diameter);
            if (metResis.misResistencias.interpretation != null) {
            that.dataValue.interpretation =  metResis.misResistencias.interpretation;
            }
            that.dataValue.cim = cim;
            that.dataValue.diameter = diameter;
            that.dataValue.etest = etest;
            table.row(row).data(that).draw();

            modificarObjAntibioticos(that);
    }
});

function modificarObjAntibioticos(that) {
    let dataValue = that.dataValue;

    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let antibiocoLista = antibioticosObj.antibioticLists[parseInt(nAislado)];

    let antibiotics = antibiocoLista.antibiotics;

    antibiotics = $.map(antibiotics, function (obj) {
        return $.grep(antibiotics, function (n) {
            if (n.id == dataValue.id) {
                n.cim = dataValue.cim;
                n.diameter = dataValue.diameter;
                n.etest = dataValue.etest;
                n.interpretation = dataValue.interpretation;
            }
            return n;
        });
    });
}

$("#idDataTargetFull").on("change", ".select-interpretacion", function (e) {
    e.preventDefault();
    let table = $("#idDataTargetFull").DataTable();
    let row = $(this).closest("tr");
    let that = table.row(row).data();
    let interpretation = "";
    if (
        row.find(".select-interpretacion").val() != null &&
        row.find(".select-interpretacion").val() != ""
    ) {
        interpretation = row.find(".select-interpretacion").val();
    }

    if (interpretation != "") {
        that.dataValue.interpretation = interpretation;
        table.row(row).data(that).draw();
        modificarObjInterpretacion(that);
    }
});

function modificarObjInterpretacion(that) {
    console.log("interpretacion", that);
    let dataValue = that.dataValue;

    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let antibiocoLista = antibioticosObj.antibioticLists[parseInt(nAislado)];

    let antibiotics = antibiocoLista.antibiotics;

    antibiotics = $.map(antibiotics, function (obj) {
        return $.grep(antibiotics, function (n) {
            if (n.id == dataValue.id) {
                n.interpretation = dataValue.interpretation;
            }
            return n;
        });
    });
}

$("#idDataTargetFull").on("change", ".chck-informe", function (e) {
    e.preventDefault();
    let table = $("#idDataTargetFull").DataTable();
    let row = $(this).closest("tr");
    let that = table.row(row).data();
    let includeinreport = "";
    if ($(this).is(":checked")) {
        includeinreport = "S";
    } else {
        includeinreport = "N";
    }
    
    if (includeinreport != "") {
        that.dataValue.includeinreport = includeinreport;
        table.row(row).data(that).draw();
        modificarObjInforme(that);
    }
});

function modificarObjInforme(that) {
    console.log("incluirInforme", that);
    let dataValue = that.dataValue;

    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let antibiocoLista = antibioticosObj.antibioticLists[parseInt(nAislado)];

    let antibiotics = antibiocoLista.antibiotics;

    antibiotics = $.map(antibiotics, function (obj) {
        return $.grep(antibiotics, function (n) {
            if (n.id == dataValue.id) {
                n.includeinreport = dataValue.includeinreport;
            }
            return n;
        });
    });
}

// elimina el dato de antibiograma
$("#idDataTargetFull").on("click", ".eliminar-antibiograma", function (e) {
    e.preventDefault();
    let table = $("#idDataTargetFull").DataTable();
    let row = $(this).closest("tr");
    let that = table.row(row).data();

    console.log(that,'eliminar antibiograma')
    if(that.dataValue.opcional == "S"){
        consultaOpcional(that);
    }else{
        borrarNormal(that);
    }
 
});

function consultaOpcional(that){
   let table = $("#idDataTargetOpcional").DataTable();
   let row = $(`#idDataTargetOpcional`).find(`tr#${that.id}`);
   let data = table.row(row).data();
   if(typeof data !== "undefined"){
       data.dataValue.incluir = "";
       table.row(row).data(data).draw();
   }
   eliminarObjAntibioticos(that);
    let newArr = [];
    newArr.push(that);
    vt.removeItems(newArr);
    vs.addItems(newArr);
}

function borrarNormal(that){
    eliminarObjAntibioticos(that);
    let newArr = [];
    newArr.push(that);
    vt.removeItems(newArr);
    vs.addItems(newArr);
}


function agregarObjAntibioticos(that) {
    let dataValue = that.dataValue;

    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    antibioticosObj.antibioticLists[parseInt(nAislado)].antibiotics.push(  {
      "code": dataValue.code,
      "name": dataValue.name,
      "active": dataValue.active,
      "id": dataValue.id,
      "opcional": dataValue.opcional,
      "incluir":dataValue.incluir,
      "cim":dataValue.cim,
      "diameter":dataValue.diameter,
      "etest":dataValue.etest,
      "interpretation":dataValue.interpretation,
      "includeinreport":dataValue.includeinreport
    });
}
function eliminarObjAntibioticos(that) {
    let dataValue = that.dataValue;

    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let antibiocoLista = antibioticosObj.antibioticLists[parseInt(nAislado)];

    let antibiotics = antibiocoLista.antibiotics;

    let deletes = [];
    for (let i = 0; i < antibiotics.length; i++) {
        if (antibiotics[i].id !== dataValue.id) {
            deletes.push(antibiotics[i]);
        }
    }
    antibioticosObj.antibioticLists[parseInt(nAislado)].antibiotics = deletes;
}

//Recuento de colonias
$("#idTestCanvasAntibiogramColoniesCount").change(function () {
    let colonia = $("#idTestCanvasAntibiogramColoniesCount").val();
    antibioticosObj.colonycount = colonia;
});

//Tipo de infección
$("#idTestCanvasAntibiogramInfectionType").change(function () {
    let infeccion = $("#idTestCanvasAntibiogramInfectionType").val();
    antibioticosObj.infectiontype = infeccion;
});

// metodos de resistencia

var dtResis = new DatatableResistencia("#antibiogramaMetodoResistencia"); // tabla protectada con datos fuera del modal
var dtR = new DataSourceResistencia([]);
vtR = new VisualTargetResistencia(dtR, dtResis);
vsR = new VisualSourceResistencia();

function eventoLimpiarMetodo() {
    vtR.removeAll();
}

function loadResistenciaTable(resistencias) {
    resistencias.forEach((itemex) => {
        let dataItemAux = new DataItemResistencia(
            itemex.method,
            itemex.method,
            itemex.result,
        );
        let itemAux = new ItemResistencia(dataItemAux);
        vtR.addItem(itemAux);
    });
    let dataItemAuxEmpty = new DataItemResistencia(
        dtResis.getCounts() + 1,
        "",
        "",
    );
    let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
    if(!tieneClase){
        let itemAuxEmpty = new ItemResistencia(dataItemAuxEmpty);
        vtR.addItem(itemAuxEmpty);
    }
}

$("#antibiogramaMetodoResistencia").on(
    "change",
    ".select-resistencia, .select-resultado",
    function (e) {
        e.preventDefault();
        let table = $("#antibiogramaMetodoResistencia").DataTable();
        let row = $(this).closest("tr");
        let that = table.row(row).data();
        let resistencia = "";
        if (
            row.find(".select-resistencia").val() != null &&
            row.find(".select-resistencia").val() != ""
        ) {
            resistencia = row
                .find(".select-resistencia  option:selected")
                .text();
        }
        let resultado = "";
        if (
            row.find(".select-resultado").val() != null &&
            row.find(".select-resultado").val() != ""
        ) {
            resultado = row.find(".select-resultado  option:selected").text();
        }
        if (resistencia != "" && resultado != "" && Number.isInteger(that.id)) {
            console.log(" entra resistencia y resultado");
            that.dataValue.id = resistencia;
            that.dataValue.method = resistencia;
            that.dataValue.result = resultado;
            that.id = resistencia;
            that.searchValue = resistencia;
            that.visibleValue = resistencia;
            table.row(row).data(that).draw();

            let dataItemAuxEmpty = new DataItemResistencia(
                dtResis.getCounts() + 1,
                "",
                "",
            );
            let itemAuxEmpty = new ItemResistencia(dataItemAuxEmpty);
            vtR.addItem(itemAuxEmpty);

            addResistencia(that);
        } else if (
            resistencia != "" &&
            resultado != "" &&
            !Number.isInteger(that.id)
        ) {
            let idAnterior = that.dataValue.id;
            that.dataValue.id = resistencia;
            that.dataValue.method = resistencia;
            that.dataValue.result = resultado;
            that.id = resistencia;
            that.searchValue = resistencia;
            that.visibleValue = resistencia;
            table.row(row).data(that).draw();

            modificarResistencia(that, idAnterior);
        }
    },
);

function addResistencia(that) {
    let dataValue = that.dataValue;

    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let antibiocoLista = antibioticosObj.antibioticLists[parseInt(nAislado)];

    let methods = antibiocoLista.methods;

    console.log("metods", methods, "datava", dataValue);

    methods.push({
        result: dataValue.result,
        method: dataValue.method,
    });

    console.log(antibiocoLista);
}

function modificarResistencia(that, idAnterior) {
    console.log("incluirInforme", that);
    let dataValue = that.dataValue;

    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let antibiocoLista = antibioticosObj.antibioticLists[parseInt(nAislado)];

    let methods = antibiocoLista.methods;

    methods = $.map(methods, function (obj) {
        return $.grep(methods, function (n) {
            if (n.method == idAnterior) {
                n.result = that.dataValue.result;
                n.method = that.dataValue.method;
            }
            return n;
        });
    });
}

// elimina el dato de resistencia
$("#antibiogramaMetodoResistencia").on(
    "click",
    ".eliminar-metodo",
    function (e) {
        e.preventDefault();
        let table = $("#antibiogramaMetodoResistencia").DataTable();
        let row = $(this).closest("tr");
        let that = table.row(row).data();
        if (typeof that.id === "string") {
            eliminarObjResistencia(that);
            let newArr = [];
            newArr.push(that);
            vtR.removeItems(newArr);
        }
        // table.row(row).remove().draw();
    },
);

function eliminarObjResistencia(that) {
    let dataValue = that.dataValue;

    let nAislado = $("#idTestCanvasAntibiogramIsolatedNumber").val();

    let antibiocoLista = antibioticosObj.antibioticLists[parseInt(nAislado)];

    let methods = antibiocoLista.methods;


    let deletes = [];
    for (let i = 0; i < methods.length; i++) {
        if (methods[i].method !== dataValue.id) {
            deletes.push(methods[i]);
        }
    }
    antibioticosObj.antibioticLists[parseInt(nAislado)].methods = deletes;
}


function getDifferenceAntibiotics(leftObjects, rightObjects, property) {
    const result = {};
  
    const propertyValues = Object.keys(leftObjects).map(key => leftObjects[key][property].toString());

    for (const key in rightObjects) {
      const value = rightObjects[key][property].toString();
  
    if (!propertyValues.includes(value)) {
        result[key] = rightObjects[key];
      }
    }
    return result;
  }