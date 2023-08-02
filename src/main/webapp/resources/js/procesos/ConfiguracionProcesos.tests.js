class DataItem { 

  ctIdtest;
  ctCodigo;
  ctAbreviado;
  ctDescripcion;
  ctActivo;
  ctDecimales;
  csecIdseccion;
  csecDescripcion;
  sptActivo;
  idExamenes;
  examenes;
  sptCodigorecepcion;
  sptCodigoenvio;
  sptConversion;
  idSigmaTipoMuestra;
  idtiporesultado;
  idTipoMuestra;
  prefijoTipoMuestra;
  conversiones;
  //ldvomIdoperadormath;
  //sptcValoroperando;
  guardadoEnDB;
  data;
  constructor(ctIdtest, ctCodigo, ctAbreviado, ctDescripcion, ctActivo, ctDecimales, csecIdseccion, csecDescripcion, sptActivo, idExamenes, examenes, idTipoMuestra, prefijoTipoMuestra) {
    this.ctIdtest = ctIdtest;
    this.ctCodigo = ctCodigo;
    this.ctAbreviado = ctAbreviado;
    this.ctDescripcion = ctDescripcion;
    this.ctActivo = ctActivo;
    this.ctDecimales = ctDecimales;
    this.csecIdseccion = csecIdseccion;
    this.csecDescripcion = csecDescripcion;
    this.sptActivo = sptActivo;
    this.idExamenes = idExamenes;
    this.examenes = examenes;
    this.sptCodigorecepcion = null;
    this.sptCodigoenvio = null;
    this.sptConversion = null;
    this.idSigmaTipoMuestra = null;
    this.idtiporesultado = null;
    this.idTipoMuestra = idTipoMuestra;
    this.prefijoTipoMuestra = prefijoTipoMuestra;
    //this.ldvomIdoperadormath = null;
    //this.sptcValoroperando = null;
    this.guardadoEnDB = 'N';
    this.conversiones = null;
    this.data = {
        "ctIdtest": this.ctIdtest,
        "ctCodigo": this.ctCodigo,
        "ctAbreviado": this.ctAbreviado,
        "ctDescripcion": this.ctDescripcion,
        "ctActivo": this.ctActivo,
        "ctDecimales": this.ctDecimales,
        "csecIdseccion": this.csecIdseccion,
        "csecDescripcion": this.csecDescripcion,
        "sptActivo": this.sptActivo,
        "idExamenes": this.idExamenes,
        "examenes": this.examenes,
        "sptCodigorecepcion": this.sptCodigorecepcion,
        "sptCodigoenvio": this.sptCodigoenvio,
        "sptConversion": this.sptConversion,
        "idSigmaTipoMuestra": this.idSigmaTipoMuestra,
        "idtiporesultado": this.idtiporesultado,
        "idTipoMuestra": this.idTipoMuestra,
        "prefijoTipoMuestra": this.prefijoTipoMuestra,
        "guardadoEnDB": this.guardadoEnDB
    };
  }

  get id() {
    return this.ctIdtest;
  }
  set id(code) {
    this.ctIdtest = code;
  }
  get text() {
    return this.ctCodigo + " - " + this.ctAbreviado;
  }
  get data() {
    return this.data;
  }

  get searchText() {
    return this.ctCodigo + " " + this.ctAbreviado + " " + this.ctDescripcion;
  }
}

class Item {
  id;
  visibleValue;
  dataValue;
  searchValue;

  constructor(dataItem) {
    this.id = dataItem.id;
    this.visibleValue = dataItem.text;
    this.dataValue = dataItem.data;
    this.searchValue = dataItem.searchText;
  }
}

class DataSource {
  srcDatos;
  loaderFunction;

  constructor(datasourceArray) {
    this.srcDatos = new Map();
    this.addAll(datasourceArray);
  }

  load() {
    this.loaderFunction();
    return function() {
      this.addAll();
    }
  }

  add(item) {
    this.srcDatos.set(item.id, item);
  }

  get(itemId) {
    let item = this.srcDatos.get(itemId);
    return item;
  }

  addItems(lstItem) {
    lstItem.forEach((item) => { this.srcDatos.set(item.id, item); });
  }

  addAll(lstItem) {
    this.srcDatos.clear();
    lstItem.forEach((item) => { this.srcDatos.set(item.id, item); });
  }

  remove(item) {
    this.srcDatos.delete(item.id);
  }

  removeById(itemId) {
    this.srcDatos.delete(itemId);
  }

  removeAll() {
    this.srcDatos.clear();
  }

  clear() {
    this.srcDatos.clear();
  }

  length() {
    return this.srcDatos.size;
  }

};

class VisualSource {

  srcDatos;
  visualElement;
  srcDatosMetodos;
  suscriptores;

  constructor() {
    this.suscriptores = new Array();

  }
/*
  cambiarValorSeccion(idSeccion){
      //console.log("id seccion: " + idSeccion);
      this.visualElement.filtrarBySeccion(idSeccion);
  }
*/
  onDoubleClick(observador) {
    this.suscriptores.push(observador);
  }

  swapDs2Examen() {
    this.srcDatos = this.srcDatosMetodos;
  }

  load(datasource, visualElement) {
    this.srcDatos = datasource;
    this.srcDatosMetodos = datasource;
    this.visualElement = visualElement;
    this.visualElement.suscriptores = this.suscriptores;
  }



  getDataCallBack(datasource) {
    this.visualElement.init(datasource);
  }

  init() {
    this.visualElement.init(datasource);
  }

  getSelectedItemId() {
    return this.visualElement.getSelectedItemId();
  }

  getSelectedItemById(itemId) {
    let auxItem = this.srcDatos.get(itemId);
    return auxItem;
  }

  getSelectedItems() {
    return this.visualElement.getSelectedItems();
  }

  addItem(item) {
    this.visualElement.addItem(item);
    this.srcDatos.add(item.id, item);
  }

  addItems(items) {
    this.visualElement.addItems(items);
    this.srcDatos.addItems(items);
  }

  addAll() {
    let items = this.getSelectedItems();
    this.srcDatos.addAll(items);
  }

  remove(item) {
    this.srcDatos.removeById(item.id);
    this.visualElement.removeItem();
  }

  removeItemById(itemId) {
    this.srcDatos.removeById(itemId);
    this.visualElement.removeItemById(itemId);
  }

  removeVisualItems(items) {
    this.visualElement.removeVisualItems(items);
  }

  removeAll() {
    this.srcDatos.clear();
    this.visualElement.clear();
  }

  clear() {
    this.srcDatos.clear();
    this.visualElement.clear();
  }

};

function testdc(e) {

  console.table(e);

}

class SelectVisualSource {

  jqid;
  suscriptores;
  dataSource;

  constructor(jqid) {
    this.jqid = jqid;
    let me = this;
    console.log(jqid);
    $(this.jqid).change(this.selectCallback(this.jqid));
    $(this.jqid).dblclick(function() {
      // alert($(jqid.concat(" option:selected")).val());

      let itemId = $(jqid.concat(" option:selected")).val();
      itemId = parseInt(itemId);
      me.suscriptores.forEach((suscriptor) => { suscriptor.add(suscriptor,itemId);});
    }
    );
  }

  init(datasource) {
      this.dataSource = datasource;
    $(this.jqid + " option").each(() => { console.log('OKa'); });
    datasource.srcDatos.forEach((k, v, m) => {
      let opt = new Option(k.visibleValue, v);
      $(this.jqid).append($(opt));
    })
  }

  selectCallback(jqid) {
    return function(e) {
      // console.log($(jqid + " option:selected").val());
    }

  }

  getSelectedItemId() {
    let nId = parseInt($(this.jqid + "  option:selected").val());
    return nId;
  }

  removeItem(item) {
    this.removeItemById(item.id);
  }

  removeItemById(itemId) {

    $(this.jqid + " option[value='" + itemId + "']").remove();
  }


  hideItemById(itemId) {
    $(this.jqid + " option[value='" + itemId + "']").hide();
  }

  showItemById(itemId) {
    $(this.jqid + " option[value='" + itemId + "']").show();
  }

  addEmpty() {
    // $(this.jqid + " option:contains('Seleccionar')").hide();
    let elemento = "tests";
    /*if (this.jqid === "#idDualDataVisualSourceGlosa"){
        elemento = "glosas";
    } else {
        elemento = "métodos";
    }*/
    $(this.jqid + ` option:contains('No hay ${elemento} para mostrar')`).remove();
    $(this.jqid).append(`<option value="" disabled style="color:#000;text-align:center">No hay ${elemento} para mostrar</option>`);
  }

  removeEmpty(){
    let elemento = "tests";
    /*if (this.jqid === "#idDualDataVisualSourceGlosa"){
        elemento = "glosas";
    } else {
        elemento = "métodos";
    }*/
    $(this.jqid + ` option:contains('No hay ${elemento} para mostrar')`).remove();
    // $(this.jqid + " option:contains('Seleccionar')").show();
  }

  addItem(item) {

    let opt = new Option(item.visibleValue, item.id);
    $("#idDualDataVisualSourceTest").append($(opt));
  }

  addItems(items) {
    items.forEach((item) => { this.addItem(item); });
  }

  // Revisar y utilizar map
  removeVisualItems(items) {
    let options = $(this.jqid + " option");
    let n = options.length;
    for (let i = n - 1; i > 0; i--) {
      let found = items.find(id => (id.toString() === options[i].value));
      if (found !== undefined) {
        this.hideItemById(found);
      }
      else {
        this.showItemById(options[i].value);
      }
    }
    if($(this.jqid + " option:visible:not(:disabled)").length === 0){
      this.addEmpty();
    }else{
      this.removeEmpty();
    }
  };
  /*
  filtrarBySeccion(idSeccion){
      console.log("seccion: "+idSeccion);
      //Filtrar datasource por idSeccion (datasource contiene items de tipo datoglosa)
      //reinicializar select
      //basarse en init
      
  }*/
  
  reinicializarSelect(){
      
  }

  clear() { };
}

class VisualTarget {

  srcDatos;
  visualElement;

  constructor(datasource, visualElement) {
    this.srcDatos = datasource;
    this.visualElement = visualElement;
    this.visualElement.init(datasource);
  }
  
  getItems(){
    return this.visualElement.getAllItems();
  }

  getSelectedItem() {
    let item = this.visualElement.getSelectedItem();
    return item;
  };

  getSelectedItems() {
    return this.visualElement.getSelectedItems();
  };

  getAll() {
    return this.srcDatos.getSelectedItems();
  }

  getAllItems() {

    return this.visualElement.getAllItems();
  }

  add() {
    let item = this.getSelectedItem();
    this.srcDatos.add(item.id, item);
  }

  addItem(item) {
    this.srcDatos.add(item);// Ver lo de examen
    this.visualElement.addItem(item);
  }
  
  addMuestra(item){
    this.srcDatos.add(item);
    this.visualElement.addItem(item);
  }

  addAll() {
    let items = this.getSelectedItems();
    this.srcDatos.addAll(items);
  }

  removeItem(item) {
    this.visualElement.removeItem(item);
    this.srcDatos.removeById(item.id);
  }
  
  removeMuestra(item) {
    this.visualElement.removeItem(item);
    this.srcDatos.removeById(item.id);
  }

  removeAll() {
    this.srcDatos.clear();
    this.visualElement.clear();
  }

  removeItems(items) {
    let n = items.length;
    for (let i = 0; i < n; i++) {
      this.removeItem(items[i]);
    }
  }

  clear() {
    this.visualElement.clear();
  }
  
  countItems(){
    let items = this.visualElement.getAllItems();
    return items.length;
  }
};

class DataTableVisualTarget {

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
            targets: 2,
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let checked = '';
              if (data === 'S') {
                checked = 'checked';
              }
              return `<input type="checkbox" class="checkActivo checkable formProceso" ${checked} />`;
            }
          }
        ],
        "columns": [
          //{ "": ""},
          { "data": "dataValue.ctCodigo"},
          { "data": "dataValue.ctAbreviado" },
          { "data": "dataValue.sptActivo" }
        ],
      }
    );
    
    this.dataTable.on('select', function(e, dt, type, indexes) {
      let filas = dt.rows({ selected: true }).data();
      let sptActivo = filas[0].dataValue.sptActivo;

      if (sptActivo === 'S') {
        filas[0].dataValue.sptActivo = 'N';
      } else {
        filas[0].dataValue.sptActivo = 'S';
      }
      
      if (filas[0].dataValue.guardadoEnDB === 'S'){
          $("#clickDelBtnTest").prop("disabled", true);
      } else {
          $("#clickDelBtnTest").prop("disabled", false);
      }
    });
    
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
function getScreenDevice(){
  let total = "";
  let screen = {
    width: window.screen.width,
    height: window.screen.height
  };
  if(screen.width === 1024 && screen.height === 768 || screen.width >= 1400 && screen.height >= 900){
      total = 15;
  }else if(screen.width === 1152 && screen.height === 864 || screen.width === 1280 && screen.height === 960 || screen.width === 1280 && screen.height === 1024){
    total = 15;
  }else{
    total = 10;
  }
  return total+"rem";
}

function genVSFiller(jqid, vs) {
  return function(data) {
    let listItems = new Array();
    let n = data.dato.length;
    for (i = 0; i < n; i++) {
      let itemex = data.dato[i];
      let idExamenes = new Array();
      let examenes = new Array();
      itemex.listExamenesTest.forEach(function(exam){
          //idExamenes.push(exam.ceIdexamen);
          //examenes.push(exam.ceAbreviado);
          idExamenes.push(exam.ce_IDEXAMEN);
          examenes.push(exam.ce_ABREVIADO);
      });
      /*
      let datoTest = new DataItem(itemex.cfgTest.ctIdtest, itemex.cfgTest.ctCodigo, itemex.cfgTest.ctAbreviado, itemex.cfgTest.ctDescripcion, itemex.cfgTest.ctActivo, itemex.cfgTest.ctDecimales,
      itemex.cfgSecciones.csecIdseccion, itemex.cfgSecciones.csecDescripcion, 'S', idExamenes, examenes,itemex.cfgMuestras.cmueIdtipomuestra, itemex.cfgMuestras.cmuePrefijotipomuestra);
       */
      let datoTest = new DataItem(itemex.ct_IDTEST, itemex.ct_CODIGO, itemex.ct_ABREVIADO, itemex.ct_DESCRIPCION, itemex.ct_ACTIVO, itemex.ct_DECIMALES,
      itemex.csec_IDSECCION, itemex.csec_DESCRIPCION, 'S', idExamenes, examenes,itemex.cmue_IDTIPOMUESTRA, itemex.cmue_PREFIJOTIPOMUESTRA);
      let item = new Item(datoTest);
      listItems.push(item);
    }
    let ds = new DataSource(listItems);
    let selectVS = new SelectVisualSource(jqid);

    this.srcDatos = ds;
    this.visualElement = selectVS;
    this.visualElement.init(ds);
    vs.load(ds, selectVS);

    const visualSearcher = new VisualSearcher("#idSearcherTests", vs, "#selectSeccionTest", "#selectExamenTest");
    //$('#idBtnExamen').prop('disabled', false);
    if(listItems.length === 0){
      $(this.jqid + " option:contains('Seleccionar')").hide();
      $(this.jqid + " option:contains('No hay tests para mostrar')").remove();
      $(this.jqid).append(`<option value="" disabled style="color:#000;text-align:center">No hay tests para mostrar</option>`);
    }else{
      $(this.jqid + " option:contains('No hay tests para mostrar')").remove();
      $(this.jqid + " option:contains('Seleccionar')").show();
    }
    return listItems;
  }
}



class Searcher {
  dataSearcher;
  visualSearcher;
  visualSource;
  constructor(dsearch, vsearch, visualSource) {
    this.dataSearcher = dsearch;
    this.visualSearcher = vsearch;
    this.visualSource = visualSource;
  }
  removeItem() {
    vs.removeItemById(entry[0]);
  }
}

class DataSearcher {
  dataSource;
  constructor(ds) {
    this.dataSource = ds;
  }
  searchByVal(str) {
    let lstNokIndx = new Array();
    for (let entry of this.dataSource.srcDatos.entries()) {
      let value = entry[1].searchValue;
      if (value.toUpperCase().indexOf(str.toUpperCase()) < 0) {
        lstNokIndx.push(entry[1].id);
      }
    }
    return lstNokIndx;
  }
  countVal(str) {
    let lstNokIndx = new Array();
    for (let entry of this.dataSource.srcDatos.entries()) {
      let value = entry[1].searchValue;
      if (value.toUpperCase().indexOf(str.toUpperCase()) >= 0) {
        lstNokIndx.push(entry[1].id);
      }
    }
    return lstNokIndx;
  }
  filtrarBySeccion(id){
    let idSeccion = parseInt(id);
    let lstNokIndx = new Array();
    if (idSeccion > 0){
        for (let entry of this.dataSource.srcDatos.entries()) {
          let value = entry[1].dataValue.csecIdseccion;
          if (value!==idSeccion) {
            lstNokIndx.push(entry[1].id);
          }
        }  
    }
    return lstNokIndx;
  }
  
  filtrarByExamen(id){
    let idExamen = parseInt(id);
    let lstNokIndx = new Array();
    if (idExamen > 0){
        for (let entry of this.dataSource.srcDatos.entries()) {
          let examenes = entry[1].dataValue.idExamenes;
          let found = false;
          examenes.forEach(function(ex){
              if (idExamen===ex){
                  found = true;
              }
          });
          if (found===false){
              lstNokIndx.push(entry[1].id);
          }
        }  
    }
    return lstNokIndx;
  }
  
}

class VisualSearcher {
  dataSearcher;
  jqid;
  visualSource;
  selectSeccion;
  selectExamen;
  
  constructor(jqid, visualSource, selectSeccion, selectExamen) {
    this.jqid = jqid;
    this.visualSource = visualSource;
    this.selectSeccion = selectSeccion;
    this.selectExamen = selectExamen;
    this.dataSearcher = new DataSearcher(this.visualSource.srcDatos);
    let myDataSearcher = this.dataSearcher;
    let myVisualSource = this.visualSource;
    
    $(this.jqid).change(function(e) {
      let delItems = myDataSearcher.searchByVal(this.value);
      myVisualSource.removeVisualItems(delItems);
    });
    $(this.jqid).keyup(function(e) {
      let delItems = myDataSearcher.searchByVal(this.value);
      myVisualSource.removeVisualItems(delItems);
    });
    $(this.jqid).keypress(function(e) {
      let keycode = (e.keyCode ? e.keyCode : e.which);
      if(keycode == '13'){
         let countVal = myDataSearcher.countVal(this.value);
         if(countVal.length == 1){
          //envia el item la otra tabla
          myVisualSource.suscriptores.forEach((suscriptor) => { suscriptor.add(suscriptor,parseInt(countVal.toString())); console.log(suscriptor) });
         }
      }
  });
    $(this.selectSeccion).change(function(e) {
      let delItems = myDataSearcher.filtrarBySeccion(this.value);
      myVisualSource.removeVisualItems(delItems);
    });
    $(this.selectExamen).change(function(e) {
      let delItems = myDataSearcher.filtrarByExamen(this.value);
      myVisualSource.removeVisualItems(delItems);
    });
  }
}


// Mover a clase
function buscar(str) {

  for (let entry of ds.srcDatos.entries()) {
    let value = entry[1].visibleValue.toUpperCase();
    let src = src.toUpperCase();
    if (value.indexOf(str) < 0) {
      vs.removeItemById(entry[0]);
    };
  }
}

class Mover {

  dtVT;
  dt;
  vt;
  vs;
  me;

  constructor(dtVT, dt, vt, vs) {
    this.dtVT = dtVT;
    this.dt = dt;
    this.vt = vt;
    this.vs = vs;
  }

  add(movedor, itemId) {
    let item = movedor.vs.getSelectedItemById(itemId);
    movedor.vs.removeItemById(itemId);
    movedor.vt.addItem(item);
    //movedor.vs.removePanelItem(item);
  }

}

class DualData {

  dtVT;
  dt;
  vt;
  vs;
  jqidDataTarget;
  jqidVisualSource;
  jqidSearcher;

  constructor(jqidDataTarget, jqidVisualSource, jqidSearcher, jqidPanelVisualSource) { // "#idDataTarget"

    this.jqidVisualSource = jqidVisualSource;
    this.jqidSearcher = jqidSearcher;
    this.jqidDataTarget = jqidDataTarget;
    this.dtVT = new DataTableVisualTarget(this.jqidDataTarget);
    this.dt = new DataSource([]);
    this.vt = new VisualTarget(dt, dtVT);
    this.vs = new VisualSource();
    test = genVSFiller(this.jqidVisualSource, this.vs, this.jqidSearcher); //#idDualDataVisualSource" "#idSearcher"

    let svc = new CfgMetodos();
    svc.getMetodos(test);

    $("#clickAddBtn").click(this.eventoAdd);
    $("#clickDelBtn").click(this.eventoDel);
    $(this.jqidPanelVisualSource).hide();

  }

  eventoAdd() {
    let itemId = this.jqidVisualSourcevs.getSelectedItemId();
    let item = this.jqidVisualSourcevs.getSelectedItemById(itemId);
    console.log('empieza eventadd'); 
    console.log(itemId);
    console.log(item);
    console.log('termina eventadd'); 
    this.jqidVisualSourcevs.removeItemById(itemId);
    this.jqidVisualSourcevt.addItem(item);
    //this.jqidVisualSourcevs.removePanelItem(item);
  };

  eventoDel() {
    let items = vt.getSelectedItems();
    this.jqidVisualSourcevt.removeItems(items);
    this.jqidVisualSourcevs.addItems(items);
  }

}


class UI {
  imprimirTooltip(elemento, mensaje,tipo = 'enable') {

    $(elemento).attr({
        "data-toggle": "tooltip",
        "data-placement": "top",
        "data-custom-class":"tooltip-warning",
        "title": mensaje,
        "data-original-title": mensaje,
      });
      $(elemento)
      .tooltip({
        template: '<div class="tooltip tooltip-danger" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',
      }).tooltip(tipo);
  }
  limpiarTooltip(elemento) {
    $(elemento).removeAttr("data-toggle data-placement data-custom-class title data-original-title");
    $(elemento).tooltip("disable");
    $(elemento).tooltip('update');
  }

  limpiarTodosTooltip(contenedorPadre){
    let contenedor = $(contenedorPadre).find("[data-custom-class='tooltip-warning']");
    contenedor.each(function(){
      let id = $(this).attr('id');
      $("#"+id).removeAttr("data-toggle data-placement data-custom-class title data-original-title");
      $("#"+id).tooltip("disable");
      $("#"+id).tooltip('update');
    })
  }

  normalTooltip(elemento){
    $(elemento)
      .tooltip({
        template: '<div class="tooltip tooltip-normal" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',
      });
  }
  
}

const ui = new UI();

// ConvencioComboBoxM:'N'
const RegistroOden = {
  medicoComboBox: null,
  tipoDeAtencion:-1,
  ProcedenciaComboBoxM:-1,
  ServicioComboBoxM:-1,
  SalaComboBoxM:null,
  cama:null,
  selectPrioridadAtencionPac:null,
  ConvencioComboBoxM:null,
  DiagnosticoComboBoxM:1,
};


class DataTypeahead{
  jqid;
  type;
  subtype;
  constructor(jqid) {
    this.jqid = jqid;
  }

  init(dato,funccion,focus) {
  $("#"+this.jqid).typeahead("destroy");
  this.type = $("#"+this.jqid).typeahead({
      classNames: {
      menu:'tt-menu',
      input: 'Typeahead-input',
      hint: 'Typeahead-hint',
      selectable: 'Typeahead-selectable'
      },
      minLength: 0,
      highlight: true
    },{
    displayKey: 'id',
    name: 'best-pictures',
    display: 'descripcion',
    source: dato,
    limit: 50,
    templates: {
    empty: [
      '<div class="col-12 empty-message">',
      'No hay resultados',
      '</div>'
    ].join('\n'),
    suggestion: function(data){
      return  "<div class='container-typeahead col-12'><span class='col-form-label' data-set='"+data.id+"' style='overflow:hidden'>"+ data.descripcion+"</span></div>"
    },
    }
    }).on('typeahead:selected', function(event, suggestion){
       if(typeof funccion !== 'undefined'){
         typeof focus !== 'undefined' ? funccion(suggestion.id,focus) : funccion(suggestion.id);
       }
     });

  }
  selectedFunction(funccion,focus){
   $("#"+this.jqid).on('typeahead:selected', function(event, suggestion){
     if(typeof funccion !== 'undefined'){
       typeof focus !== 'undefined' ? funccion(suggestion.id,focus) : funccion(suggestion.id);
     }
   });
  }

  clear(){
    $("#"+this.jqid).typeahead('val','');
  }
}


let buscarDato = function (strs) {
  return function buscarIguales(q, cb) {
      let matches, substringRegex;
      matches = [];
      substrRegex = new RegExp(q, 'i');
      $.each(strs, function (i, str) {
          if (substrRegex.test(str.descripcion)) {
              matches.push(str);
          }else if(q==''){
            matches.push(str);
          }
      });
      cb(matches);
  };
};


function seleccionarDatoEnter(jqId,funccion,focus){
  $("#"+jqId).on('keydown', function(evt) {
    if (evt.which == 13 || evt.which == 9) {
      const typeahead = $(this).data().ttTypeahead;
      const hintText = typeahead.input.$hint.val();
      const menu = typeahead.menu;
      const sel = menu.getActiveSelectable() || menu.getTopSelectable();
      if (menu.isOpen()) {
        menu.trigger('selectableClicked', sel);
        let resultado = $("#"+jqId).siblings(".tt-menu").find("span:contains('"+typeahead.input.query+"')").data('set');
        if (typeof resultado === "undefined") {
            $("#"+jqId).typeahead('val','');
        }else{
          $("#"+jqId+"typeahead").val(resultado);
        }
        if(typeof funccion !== 'undefined'){
          if(typeof focus !== 'undefined' && typeof focus ==='object'){
            evt.which === 13 ? funccion(resultado,focus.focus[1]) :funccion(resultado,focus.focus[0]);
          }else if(typeof focus !== 'undefined' && typeof focus !=='object'){
            funccion(resultado,focus);
          }else{
            funccion(resultado);
          }
        }
        evt.preventDefault();
      }
    }
  });
}




