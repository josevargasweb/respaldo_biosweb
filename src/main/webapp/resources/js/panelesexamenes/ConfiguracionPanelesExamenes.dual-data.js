class DataExamen {
    ceIdexamen;
    ceCodigoexamen;
    ceAbreviado;
    ceDescripcion;
    data;
    constructor(ceIdexamen, ceCodigoexamen, ceAbreviado, ceDescripcion){
        this.ceIdexamen = ceIdexamen;
        this.ceCodigoexamen = ceCodigoexamen;
        this.ceAbreviado = ceAbreviado;
        this.ceDescripcion = ceDescripcion;
        this.data = {
            "ceIdexamen": this.ceIdexamen,
            "ceCodigoexamen": this.ceCodigoexamen,
            "ceAbreviado": this.ceAbreviado,
            "ceDescripcion": this.ceDescripcion
        };
    }
    
    get id() {
        return this.ceIdexamen;
    }
    set id(code) {
        this.ceIdexamen = code;
    }
    get text() {
        return this.ceCodigoexamen.concat(" - ").concat(this.ceAbreviado);
    }
    get data() {
        return this.data;
    }

    get searchText() {
        return this.ceCodigoexamen.concat(" ").concat(this.ceAbreviado).concat(" ").concat(this.ceDescripcion);
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
  idPaciente;

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

  setPaciente(idPaciente) {
    this.idPaciente = idPaciente;
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
  srcDatosPanel;
  srcDatosExamen;
  suscriptores;

  constructor() {
    this.suscriptores = new Array();

  }

  onDoubleClick(observador) {
    this.suscriptores.push(observador);
  }

  swapDs2Panel() {
    this.srcDatos = this.srcDatosPanel;
  }

  swapDs2Examen() {
    this.srcDatos = this.srcDatosExamen;
  }

  load(datasource, visualElement) {
    this.srcDatos = datasource;
    this.srcDatosExamen = datasource;
    this.visualElement = visualElement;
    this.visualElement.suscriptores = this.suscriptores;
    //   this.visualElement.init(datasource);
  }



  loadPanel(datasource, visualElement) {
    this.srcDatosPanel = datasource;
    this.visualElement = visualElement;
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
  getFirstItemId() {
    return this.visualElement.getFirstItemId();
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

  removePanelItem(item) {

    if (this.visualElement.jqid === "#idPanelDualDataVisualSource") {
      this.visualElement.jqid = "#idDualDataVisualSource";
      let examenes = item.dataValue.lstExamenes;
      for (let i in examenes) {
        this.removeItemById(examenes[i].ceIdexamen);
      }
      this.visualElement.jqid = "#idPanelDualDataVisualSource";
    }
  }
};

class SelectVisualSource {

  jqid;
  suscriptores;

  constructor(jqid) {
    this.jqid = jqid;
    let me = this;
    console.log(jqid);
    $(this.jqid).change(this.selectCallback(this.jqid));
    $(this.jqid).dblclick(function() {
      // alert($(jqid.concat(" option:selected")).val());
        let itemId = $(jqid.concat(" option:selected")).val();
      itemId = parseInt(itemId);
      if(jqid === '#idDualDataVisualSource'){
        me.suscriptores.forEach((suscriptor) => { 
          console.log('suscriptor',suscriptor);
          suscriptor.add(suscriptor,itemId);});
      }else if(jqid === '#idPanelDualDataVisualSource'){
        let item =  me.getSelectedItemById(itemId);
        console.log(item);
      }
    });
    $(this.jqid).keyup(function(e) {
      let keycode = (e.keyCode ? e.keyCode : e.which);
      if(keycode == '13'){
        let itemId = $(jqid.concat(" option:selected")).val();
        itemId = parseInt(itemId);
        if(jqid === '#idDualDataVisualSource'){
          me.suscriptores.forEach((suscriptor) => { suscriptor.add(suscriptor,itemId);});
        }else if(jqid === '#idPanelDualDataVisualSource'){
          let item =  me.getSelectedItemById(itemId);
          console.log(item);
        }
      }
    });
  }

  init(datasource) {
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

  getFirstItemId() {
    let nId = parseInt($(this.jqid + "  option:visible:not(:disabled)").val());
    return nId;
  }

  removeItem(item) {
    this.removeItemById(item.id);
  }

  removeItemById(itemId) {

    if (this.jqid !== "#idPanelDualDataVisualSource")
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
    $(this.jqid + " option:contains('No hay exámenes para mostrar')").remove();
    $(this.jqid).append(`<option value="" disabled style="color:#000;text-align:center">No hay exámenes para mostrar</option>`);
  }

  removeEmpty(){
    $(this.jqid + " option:contains('No hay exámenes para mostrar')").remove();
    // $(this.jqid + " option:contains('Seleccionar')").show();
  }

  addItem(item) {

    let opt = new Option(item.visibleValue, item.id);
    $("#idDualDataVisualSource").append($(opt));
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

  clear() { };
}

class VisualTarget {

  srcDatos;
  visualElement;
  idPaciente;

  constructor(datasource, visualElement) {
    this.srcDatos = datasource;
    this.visualElement = visualElement;
    this.visualElement.init(datasource);
  }

  setPaciente(idPaciente) {
    this.idPaciente = idPaciente;
    this.srcDatos.idPaciente = idPaciente;
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
    this.srcDatos.add(item);
    this.visualElement.addItem(item);
  }

  addAll() {
    let items = this.getSelectedItems();
    this.srcDatos.addAll(items);
  }

  removeItem(item) {
    console.log("item eliminado",item);
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
        "rowId": "dataValue.ceIdexamen",
        "searching": false,
        "lengthChange": false,
        "scrollY": getScreenDevice(),
        "scrollCollapse": true,
        "paging":   false,
        // "ordering": false,
        "info":     false,
        "autoWidth": false,
        "responsive": false,
        "searchDelay": 500,
        "orderCellsTop": true,
        "select": {
          "style": 'single',
          "className": 'row-selected',
        },
        "data": listItems,
        "language": {
          url: getctx + "/resources/js/orden/Spanish.json",
          select: {
              rows: {
                  _: "%d filas seleccionadas",
                  0: "",
                  1: "1 fila seleccionada"
              }
          }
      },
        "columnDefs": [
          
        ],
        "columns": [
          { "data": "dataValue.ceCodigoexamen",width: '10%'},
          { "data": "dataValue.ceAbreviado",width: '27%' },
        ],
      }
    );
    this.dataTable.on('select', function(e, dt, type, indexes) {
      // console.table(indexes);
      let filas = dt.rows({ selected: true }).data();
      let esUrgente = filas[0].dataValue.ceEsurgente;

      if (esUrgente === 'S') {
        // alert('Cambiar a N');
        filas[0].dataValue.ceEsurgente = 'N';

      }
      else {
        // alert('Cambiar a S');
        filas[0].dataValue.ceEsurgente = 'S';
      }



    });
    this.dataTable.on('deselect', function(e, dt, type, indexes) {
      console.table(indexes);
      let filas = dt.rows(indexes).data();

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
 //   this.dataTable.adjust().draw(false);
  }

  removeItem(item) {
    let id = "#".concat(item.id.toString());
    this.dataTable.row(id).remove();
    this.dataTable.draw();
//    this.dataTable.adjust().draw(false);
  }

  addItem(item) {
    if (item.dataValue.itemPanel !== undefined) {
      this.addPanelItem(item);
    }
    else {
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
//        this.dataTable.adjust().draw(false);
      }
    }
  }

  addPanelItem(item) {

    let examenes = item.dataValue.lstExamenes;
    let rows = this.dataTable.rows();
    for (let i in examenes) {
      let exist = false;
      const dataRows = rows.data();
      if (dataRows !== undefined && dataRows.length !== 0) {
        let n = dataRows.length;
        for (let j = 0; j < n; j++) {
          if (dataRows[j].id === examenes[i].ceIdexamen) {
            exist = true;
          }
        }
      }
      if (!exist) {
        let dataItemAux = new DataItem(examenes[i].ceIdexamen, examenes[i].ceCodigoexamen, examenes[i].cfgMuestras.cmueDescripcion,
          examenes[i].ceAbreviado, examenes[i].ceDescripcion,
          examenes[i].cfgSecciones.csecIdseccion,
          examenes[i].cfgSecciones.csecDescripcion,
          examenes[i].cfgSecciones.cfgLaboratorios !== null ? examenes[i].cfgSecciones.cfgLaboratorios.clabIdlaboratorio : "",
          examenes[i].cfgSecciones.cfgLaboratorios !== null ? examenes[i].cfgSecciones.cfgLaboratorios.clabIdCentroSalud : "",
          examenes[i].cfgSecciones.cfgLaboratorios !== null ? examenes[i].cfgSecciones.cfgLaboratorios.clabDescripcion : "",
          examenes[i].cfgCentrosdesalud !== null ? examenes[i].cfgCentrosdesalud.ccdsDescripcion : "",
          examenes[i].ceEsurgente !== null ?  examenes[i].ceEsurgente : "");
        let itemAux = new Item(dataItemAux);
        this.dataTable.row.add(itemAux);
        //        this.removeItem(itemAux);

      }
    }
    this.dataTable.draw();
 //   this.dataTable.adjust().draw(false);
  }
  clear() {

    this.dataTable.rows().remove();
    this.dataTable.draw();
//    this.dataTable.adjust().draw(false);

  }
}

function genVSFiller(jqid, vs) {
  return function(data) {
    let listItems = new Array();
    let n = data.dato.length;
    console.log('dddddaaaa',data);
    for (i = 0; i < n; i++) {
      let itemex = data.dato[i];
      let datoExamen = new DataExamen(itemex.ceIdexamen, itemex.ceCodigoexamen, itemex.ceAbreviado, itemex.ceDescripcion);
      let item = new Item(datoExamen);
      listItems.push(item);
    }
    let ds = new DataSource(listItems);
    let selectVS = new SelectVisualSource(jqid);

    this.srcDatos = ds;
    this.visualElement = selectVS;
    this.visualElement.init(ds);
    vs.load(ds, selectVS);

    const visualSearcher = new VisualSearcher("#idSearcher", vs);
    $('#idBtnExamen').prop('disabled', false);
    if(listItems.length === 0){
      $(this.jqid + " option:contains('Seleccionar')").hide();
      $(this.jqid + " option:contains('No hay exámenes para mostrar')").remove();
      $(this.jqid).append(`<option value="" disabled style="color:#000;text-align:center">No hay exámenes para mostrar</option>`);
    }else{
      $(this.jqid + " option:contains('No hay exámenes para mostrar')").remove();
      $(this.jqid + " option:contains('Seleccionar')").show();
    }
    return listItems;
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
    movedor.vs.removePanelItem(item);
  }

}

function getScreenDevice(resta){
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

  
}

class VisualSearcher {
  dataSearcher;
  jqid;
  visualSource;
  constructor(jqid, visualSource) {
    this.jqid = jqid;
    this.visualSource = visualSource;
    this.dataSearcher = new DataSearcher(this.visualSource.srcDatos);
    let myDataSearcher = this.dataSearcher;
    let myVisualSource = this.visualSource;
    
    $(this.jqid).change(function(e) {
      let delItems = myDataSearcher.searchByVal(this.value);
      myVisualSource.removeVisualItems(delItems);
    });
    $(this.jqid).keyup(function(e) {
      let keycode = (e.keyCode ? e.keyCode : e.which);
      if(keycode == '40'){
        $(myVisualSource.visualElement.jqid).focus(); 
        $(myVisualSource.visualElement.jqid).val($(myVisualSource.visualElement.jqid+" option:visible:first").val()); 
      }
      let delItems = myDataSearcher.searchByVal(this.value);
      myVisualSource.removeVisualItems(delItems);
    });
    $(this.jqid).keypress(function(e) {
      let keycode = (e.keyCode ? e.keyCode : e.which);
      if(keycode == '13'){
         let countVal = myDataSearcher.countVal(this.value);
         if(countVal.length == 1){
          console.log(countVal);
          //envia el item la otra tabla
          myVisualSource.suscriptores.forEach((suscriptor) => { suscriptor.add(suscriptor,parseInt(countVal.toString())); console.log(suscriptor) });
          $(jqid).val('');
         }else if(countVal.length > 0 && myVisualSource.visualElement.jqid == '#idPanelDualDataVisualSource'){
            let itemId = myVisualSource.getFirstItemId();
            let item =  myVisualSource.getSelectedItemById(itemId);
            myVisualSource.removeItemById(itemId);
            myVisualSource.suscriptores.forEach((suscriptor) => { 
              let vt = suscriptor.vt;
              vt.addItem(item);
            });

            $(jqid).val('');
         }
      }
  });
  
  }
}