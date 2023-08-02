class DataTableTests {

  jqid;
  dataTable;

  constructor(jqid) {
    this.jqid = jqid;
  }

  init(listItems) {

    this.dataTable = $(this.jqid).DataTable(
      {
        "rowId": "dataValue.ctIdtest",
        "searching": false,
        "lengthChange": false,
        "scrollY": getScreenDevice(),
        "scrollCollapse": true,
        "paging":   false,
        // "ordering": false,
        "info":     false,
        "autoWidth": false,
        "select": {
          "style": 'single',
          "className": 'row-selected',
          //"selector": 'td:last-child input[type="checkbox"]'
        },
        "data": listItems,
        "language": {
          url: "//cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json",
          select: {
              rows: {
                  _: "%d filas seleccionadas",
                  0: "",
                  1: "1 fila seleccionada"
              }
          }
      },
       "columnDefs": [
          {
            targets: 0,
            searchable: false,
            className: 'dt-left',
            orderable: false
          },{
            targets: 2,
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
                let ul = "";
                for (let examen of data){
                    ul += '<li class="pl-0 pr-0">'+examen+'</li>';
                }
                let table = `<div class="col-6 form-group pl-0 pr-0 mb-0" title="">
                    <ul id="ulExamenes-muestras" class="mb-0 pl-3">
                        ${ul}
                    </ul></div>`;
                    return table;
            }
          },{
            targets: 4,
            orderable: false,
            render: function(data, type, row, meta) {
                let checked = '';
                if (data === 'S') {
                    checked = 'checked';
                }
                return `<input type="checkbox" class="checkActivo checkable formProceso" ${checked} />`;
            }
          },{
            targets: 5,
            orderable: false,
            render: function(data, type, row, meta) {
                //let arrayData = JSON.stringify(row.dataValue);
                return `<a href="#" class="btn btnEditarTest formProceso" class="p-2 ml-2">
                            <i class="fas fa-pen"></i>
                        </a>`;
            }
          }
        ],
        "columns": [
          //{ "": ""},
          { "data": "dataValue.ctAbreviado" },
          { "data": "dataValue.ctCodigo"},
          { "data": "dataValue.examenes"},
          { "data": "dataValue.csecDescripcion"},
          { "data": "dataValue.sptActivo" },
          { "": "" }
        ],
        fixedColumns: true,
        "createdRow": function( row, data, dataIndex ) {
            
            
            
        }
      }
    );
    /*
    this.dataTable.on('select', function(e, dt, type, indexes) {
      let filas = dt.rows({ selected: true }).data();
      let sptActivo = filas[0].dataValue.sptActivo;

      if (sptActivo === 'S') {
        filas[0].dataValue.sptActivo = 'N';
      } else {
        filas[0].dataValue.sptActivo = 'S';
      }
    });
    
    this.dataTable.on('deselect', function(e, dt, type, indexes) {
      let filas = dt.rows({ selected: true }).data();
      let sptActivo = filas[0].dataValue.sptActivo;

      if (sptActivo === 'S') {
        filas[0].dataValue.sptActivo = 'N';
      } else {
        filas[0].dataValue.sptActivo = 'S';
      }
      
    });
    */
  }
    

  getSelectedItems() {
    let indx = this.dataTable.rows({ selected: true }).data();
    let items = new Array();
    let n = indx.length;
    for (let i = 0; i < n; i++) {
      items.push(indx[i]);
    }
    return items;
  }

  getSelectedItem() {
    let indx = this.dataTable.rows({ selected: true }).data();
    let items = new Array();
    let n = indx.data().length;
    for (let i = 0; i < n; i++) {
      items.push(indx.data()[i]);
    }
    return items;
  }

  getSelectedItemsId() {
    let rows = this.dataTable.rows({ selected: true }).data();
    return rows;
  }

  getAllItems() {
    let indx = this.dataTable.rows().data();
    let items = new Array();
    let n = indx.data().length;
    for (let i = 0; i < n; i++) {
      items.push(indx.data()[i]);
    }
    return items;
  }

  removeItemsSelected() {
    this.getSelectedItem();
    this.dataTable.rows({ selected: true }).remove();
    this.dataTable.draw();
  }

  removeItem(item) {
    let id = "#".concat(item.id.toString());
    this.dataTable.row(id).remove();
    this.dataTable.draw();
  }

  addItem(item) {
    
      let rows = this.dataTable.rows();
      let exist = false;
      const dataRows = rows.data();
      if (dataRows !== undefined && dataRows.length !== 0) {
        let n = dataRows.length;
        for (let j = 0; j < n; j++) {
          if (dataRows[j].id === item.id) {
            exist = true;
          }
        }
      }
      if (!exist) {
        this.dataTable.row.add(item);
        this.dataTable.draw();
      }
    
  }
  
  clear() {

    this.dataTable.rows().remove();
    this.dataTable.draw();

  }
}

var dataTableTests = new DataTableTests("#tablaProcesosTests");
//var dsTests = new DataSource([]);
let visualTests = new VisualTarget(dtt, dataTableTests);

$("#btnCerrarModalTests").click(function(){
    /*dataTableTests.clear();
    let tablaTests = vtt.getAllItems();
    for (let i=0; i<tablaTests.length; i++){
        dataTableTests.addItem(tablaTests[i]);
    }*/
    llenarTablaTests();
});

$("#tablaProcesosTests").on("change", ".checkActivo", function (e) {
    e.preventDefault();
    let table = $("#tablaProcesosTests").DataTable();
    let row = $(this).closest("tr");
    let that = table.row(row).data();
    let activo = "";
    if ( row.find(".checkActivo").val() != null && row.find(".checkActivo").val() != "" ) {
        activo = row.find(".checkActivo").is(":checked") ? 'S' : 'N';
    }
    
    if (activo != ""){
        that.dataValue.sptActivo = activo;
        table.row(row).data(that).draw();
    }
});

$("#tablaProcesosTests").on("click", ".btnEditarTest", function (e) {
    $("#modalTestsProceso").modal("show");
    let table = $("#tablaProcesosTests").DataTable();
    let row = $(this).closest("tr");
    let that = table.row(row).data();
    $("#tituloProceso").text(tituloProceso());
    $("#tituloTest").text(`Test: ${that.dataValue.ctAbreviado}`);
    $("#txtCodigoRecepcion").val(that.dataValue.sptCodigorecepcion);
    $("#txtCodigoEnvio").val(that.dataValue.sptCodigoenvio);
    $("#selectTipoMuestra").val(that.dataValue.idSigmaTipoMuestra || 0);
    $("#selectTipoResultado").val(that.dataValue.idtiporesultado || 0);
    $("#txtIdTest").val(that.dataValue.ctIdtest);
    $("#txtCodigoTest").val(that.dataValue.ctCodigo);
    $("#txtDescripcionTest").val(that.dataValue.ctDescripcion);
    $("#txtExamenes").val(that.dataValue.examenes.toString());
    $("#txtPrefijoMuestra").val(that.dataValue.prefijoTipoMuestra);
    $("#hidIdTipoMuestra").val(that.dataValue.idTipoMuestra);
    $("#txtDecimales").val(that.dataValue.ctDecimales);
    $("#lblEstadoTest").text(that.dataValue.sptActivo === "S" ? 'Activo' : 'Inactivo');
    that.dataValue.sptActivo === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
    $("#checkBoxTestActivo").prop("checked", that.dataValue.sptActivo === "S" ? true : false);
    limpiarTabsConversiones();
    $("#chbkCalculo").prop("checked", false);
    $("#selectOperador").val("");
    $("#numValorOperando").val("");
    if (that.dataValue.conversiones.length>0){
        that.dataValue.conversiones.forEach(function (conversion) {
            let nCond = conversion.sptcNumerocondicion;
            if (conversion.sptcCondicionalsn === 'S'){
                $("#chbxCondicional").prop("checked", true);
                if (localStorage.getItem("botonEditar") === "inactivo") {
                    $(".formConditional").prop("disabled", false);
                }
                if (nCond === 1){
                    $("#selectOperando").val(conversion.ldvtcondIdtipocondicion);
                    $("#textValorEntrada").val(conversion.sptcValorentrada);
                    $("#chbxCritico").prop("checked", conversion.sptcCriticosn === "S" ? true : false);
                    $("#textValorSalida").val(conversion.sptcValorsalida);
                    $("#chbxReprocesar").prop("checked", conversion.sptcReprocesarsn === "S" ? true : false);
                    $("#chbxBloquear").prop("checked", conversion.sptcBloquearsn === "S" ? true : false);
                    $("#chbxFirmar").prop("checked", conversion.sptcFirmarsn === "S" ? true : false);
                } else {
                    agregarTabs(nCond);
                    $(`#selectOperando${nCond}`).val(conversion.ldvtcondIdtipocondicion);
                    $(`#textValorEntrada${nCond}`).val(conversion.sptcValorentrada);
                    $(`#chbxCritico${nCond}`).prop("checked", conversion.sptcCriticosn === "S" ? true : false);
                    $(`#textValorSalida${nCond}`).val(conversion.sptcValorsalida);
                    $(`#chbxReprocesar${nCond}`).prop("checked", conversion.sptcReprocesarsn === "S" ? true : false);
                    $(`#chbxBloquear${nCond}`).prop("checked", conversion.sptcBloquearsn === "S" ? true : false);
                    $(`#chbxFirmar${nCond}`).prop("checked", conversion.sptcFirmarsn === "S" ? true : false);
                }
            }
            if (conversion.sptcCalculosn === 'S'){
                if (localStorage.getItem("botonEditar") === "inactivo") {
                    $(".formCalculo").prop("disabled", false);
                }
                $("#chbkCalculo").prop("checked", true);
                $("#selectOperador").val(conversion.ldvomIdoperadormath);
                $("#numValorOperando").val(conversion.sptcValoroperando);
            }
        });
    }
});

function llenarTablaTests(){
    dataTableTests.clear();
    let tablaTests = vtt.getAllItems();
    for (let i=0; i<tablaTests.length; i++){
        dataTableTests.addItem(tablaTests[i]);
    }
}
/*
function mostrarDatosTestEditar(data){
    $("#modalTestsProceso").modal("show");
    $("#tituloProceso").text(tituloProceso());
    $("#tituloTest").text(`Test: ${data.ctAbreviado}`);
    $("#txtCodigoRecepcion").val(data.sptCodigorecepcion);
    $("#txtCodigoEnvio").val(data.sptCodigoenvio);
    $("#selectTipoMuestra").val(data.idSigmaTipoMuestra || 0);
    $("#selectTipoResultado").val(data.idtiporesultado || 0);
    $("#txtIdTest").val(data.ctIdtest);
    $("#txtCodigoTest").val(data.ctCodigo);
    $("#txtDescripcionTest").val(data.ctDescripcion);
    $("#txtExamenes").val(data.examenes.toString());
    $("#txtPrefijoMuestra").val(data.prefijoTipoMuestra);
    $("#hidIdTipoMuestra").val(data.idTipoMuestra);
    $("#txtDecimales").val(data.ctDecimales);
    $("#lblEstadoTest").text(data.sptActivo === "S" ? 'Activo' : 'Inactivo');
    $("#checkBoxTestActivo").prop("checked", data.sptActivo === "S" ? true : false);
    limpiarTabsConversiones();
    if (data.conversiones.length>0){
        data.conversiones.forEach(function (conversion) {
            let nCond = conversion.sptcNumerocondicion;
            if (conversion.sptcCondicionalsn === 'S'){
                $("#chbxCondicional").prop("checked", true);
                if (localStorage.getItem("botonEditar") === "inactivo") {
                    $(".formConditional").prop("disabled", false);
                }
                if (nCond === 1){
                    $("#selectOperando").val(conversion.ldvtcondIdtipocondicion);
                    $("#textValorEntrada").val(conversion.sptcValorentrada);
                    $("#chbxCritico").prop("checked", conversion.sptcCriticosn === "S" ? true : false);
                    $("#textValorSalida").val(conversion.sptcValorsalida);
                    $("#chbxReprocesar").prop("checked", conversion.sptcReprocesarsn === "S" ? true : false);
                    $("#chbxBloquear").prop("checked", conversion.sptcBloquearsn === "S" ? true : false);
                    $("#chbxFirmar").prop("checked", conversion.sptcFirmarsn === "S" ? true : false);
                } else {
                    agregarTabs(nCond);
                    $(`#selectOperando${nCond}`).val(conversion.ldvtcondIdtipocondicion);
                    $(`#textValorEntrada${nCond}`).val(conversion.sptcValorentrada);
                    $(`#chbxCritico${nCond}`).prop("checked", conversion.sptcCriticosn === "S" ? true : false);
                    $(`#textValorSalida${nCond}`).val(conversion.sptcValorsalida);
                    $(`#chbxReprocesar${nCond}`).prop("checked", conversion.sptcReprocesarsn === "S" ? true : false);
                    $(`#chbxBloquear${nCond}`).prop("checked", conversion.sptcBloquearsn === "S" ? true : false);
                    $(`#chbxFirmar${nCond}`).prop("checked", conversion.sptcFirmarsn === "S" ? true : false);
                }
            }
            if (conversion.sptcCalculosn === 'S'){
                if (localStorage.getItem("botonEditar") === "inactivo") {
                    $(".formCalculo").prop("disabled", false);
                }
                $("#chbkCalculo").prop("checked", true);
                $("#selectOperador").val(conversion.ldvomIdoperadormath);
                $("#numValorOperando").val(conversion.sptcValoroperando);
            }
        });
    }
}
*/
function tituloProceso(){
    let estacion = $("#selectEstacion").val();
    let activo = $("#checkBoxActivo").is(":checked") ? "S" : "N";
    let nombre = $("#txtNombre").val().toUpperCase();
    return `Proceso: ${estacion} | ${activo} | ${nombre}`;
}

$("#btnGuardarTestProceso").click(function () {
    let id = parseInt($("#txtIdTest").val());
    let items = vtt.getAllItems();
    let item = items.filter(test => test.id === id);
    item[0].dataValue.sptCodigorecepcion = $("#txtCodigoRecepcion").val();
    item[0].dataValue.sptCodigoenvio = $("#txtCodigoEnvio").val();
    item[0].dataValue.idSigmaTipoMuestra = $("#selectTipoMuestra").val();
    item[0].dataValue.idtiporesultado = $("#selectTipoResultado").val();
    item[0].dataValue.prefijoTipoMuestra = $("#txtPrefijoMuestra").val();
    item[0].dataValue.idTipoMuestra = $("#hidIdTipoMuestra").val();
    item[0].dataValue.sptActivo = $("#checkBoxActivo").is(":checked") ? "S" : "N";
    //item[0].dataValue.ldvomIdoperadormath = $("#selectOperador").val();
    //item[0].dataValue.sptcValoroperando = $("#numValorOperando").val();
    let count = 0;
    let conversiones = new Array();
    if ($("#chbxCondicional").is(":checked")){
        $("#myTab li.condition").each(function(){
            count++;
            let conversion = {};
            if (count===1){
                conversion.ldvtcondIdtipocondicion = $("#selectOperando").val();
                conversion.sptcValorentrada = $("#textValorEntrada").val();
                conversion.sptcCriticosn = $("#chbxCritico").is(":checked")?"S":"N";
                conversion.sptcValorsalida = $("#textValorSalida").val();
                conversion.sptcReprocesarsn = $("#chbxReprocesar").is(":checked")?"S":"N";
                conversion.sptcBloquearsn = $("#chbxBloquear").is(":checked")?"S":"N";
                conversion.sptcFirmarsn = $("#chbxFirmar").is(":checked")?"S":"N";
            } else {
                conversion.ldvtcondIdtipocondicion = $(`#selectOperando${count}`).val();
                conversion.sptcValorentrada = $(`#textValorEntrada${count}`).val();
                conversion.sptcCriticosn = $(`#chbxCriticor${count}`).is(":checked")?"S":"N";
                conversion.sptcValorsalida = $(`#textValorSalida${count}`).val();
                conversion.sptcReprocesarsn = $(`#chbxReprocesar${count}`).is(":checked")?"S":"N";
                conversion.sptcBloquearsn = $(`#chbxBloquear${count}`).is(":checked")?"S":"N";
                conversion.sptcFirmarsn = $(`#chbxFirmar${count}`).is(":checked")?"S":"N";
            }
            conversion.sptcCondicionalsn = "S";
            conversion.sptcCalculosn = "N";
            conversion.sptcNumerocondicion = count;
            conversiones.push(conversion);

        });
    }
    
    if ($("#chbkCalculo").is(":checked")){
        let calculo = {};
        calculo.ldvomIdoperadormath = $("#selectOperador").val();
        calculo.sptcValoroperando = $("#numValorOperando").val();
        calculo.sptcCondicionalsn = "N";
        calculo.sptcCalculosn = "S";
        calculo.sptcNumerocondicion = count+1;
        conversiones.push(calculo);
    }
    item[0].dataValue.conversiones = conversiones;
    console.log(item);
    $("#modalTestsProceso").modal('toggle');
});

function limpiarTabsConversiones(){
    $("#chbxCondicional").prop("checked", false);
    let count = 1;
    $("#myTab").each(function(){
        count++;
        $("#presentacion"+count).remove();
        $("#condicion-sub"+count).remove();
    });
    $("#condicion-sub").addClass("active show");
    $("#selectOperando").val(0);$("#textValorEntrada").val();
    $("#textValorEntrada").val("");
    $("#chbxCritico").prop("checked", false);
    $("#textValorSalida").val("");
    $("#chbxReprocesar").prop("checked", false);
    $("#chbxBloquear").prop("checked", false);
    $("#chbxFirmar").prop("checked", false);
    $("#agregar-tab").attr("onclick","agregarTabs(2)");
    $(".switch-content").removeClass("disabled inactivo");
}

$("#checkBoxTestActivo").click(function () {
    if ($("#checkBoxTestActivo").is(":checked")) {
        $("#lblEstadoTest").text("Activo");
    }
    if (!($("#checkBoxTestActivo").is(":checked"))) {
        $("#lblEstadoTest").text("Inactivo");
    }
});