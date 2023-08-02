class DataItem { 
  id;
  name;
  code;
  active;
  opcional;
  incluir;
  cim;
  diameter;
  etest;
  interpretation;
  includeinreport;
  data;
  constructor(id,antibiotico,codigo,active,opcional,incluir,cim,diameter,etest,interpretation,includeinreport) {
    this.id = id;
    this.name = antibiotico;
    this.code = codigo;
    this.active = active;
    this.opcional = opcional;
    this.incluir = incluir;
    this.cim = cim;
    this.diameter = diameter;
    this.etest = etest;
    this.interpretation = interpretation;
    this.includeinreport = includeinreport;
    this.data = {
      "id": this.id,
      "name": this.name,
      "code": this.code,
      "active": this.active,
      "opcional": this.opcional,
      "incluir": this.incluir,
      "cim": this.cim,
      "diameter": this.diameter,
      "etest": this.etest,
      "interpretation": this.interpretation,
      "includeinreport": this.includeinreport,
    }
  }

  // itemex.id, itemex.name, itemex.code,itemex.active
  get id() {
    return this.id;
  }
  set id(code) {
    this.id = code;
  }
  get text() {
    return this.name;
  }
  get data() {
    return this.data;
  }

  get searchText() {
    return this.name;
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
  srcDatosExamen;
  suscriptores;

  constructor() {
    this.suscriptores = new Array();

  }

  onDoubleClick(observador) {
    this.suscriptores.push(observador);
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

function testdc(e) {

  console.table(e);

}

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
      itemId = String(itemId);
      if(jqid === '#idDualDataVisualSource'){
        let item = vs.getSelectedItemById(itemId);
        vs.removeItemById(itemId);
        vt.addItem(item);
        agregarObjAntibioticos(item)
      }
    });
    $(this.jqid).keyup(function(e) {
      let keycode = (e.keyCode ? e.keyCode : e.which);
      if(keycode == '13'){
        let itemId = $(jqid.concat(" option:selected")).val();
        itemId = String( itemId);
        if(jqid === '#idDualDataVisualSource'){
          console.log("itemId2",typeof itemId)
          let item = vs.getSelectedItemById(itemId);
          vs.removeItemById(itemId);
          vt.addItem(item);
          agregarObjAntibioticos(item)
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
    let nId = $(this.jqid + "  option:selected").val();
    return nId;
  }

  getFirstItemId() {
    let nId = $(this.jqid + "  option:visible:not(:disabled)").val();
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
  visualElementFull;
  visualElementOpcional;
  idPaciente;

  constructor(datasource, visualElement, visualElementFull,visualElementOpcional) {
    this.srcDatos = datasource;
    this.visualElement = visualElement;
    this.visualElement.init(datasource);

    this.visualElementFull = visualElementFull;
    this.visualElementFull.init(datasource);
 
    this.visualElementOpcional = visualElementOpcional;
    this.visualElementOpcional.init(visualElementOpcional);
  }

  setPaciente(idPaciente) {
    this.idPaciente = idPaciente;
    this.srcDatos.idPaciente = idPaciente;
  }

  getItems(){
    return this.visualElementFull.getAllItems();
  }
  getItemsOpcional(){
    return this.visualElementOpcional.getAllItems();
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
    console.log("item selected",item);
    this.srcDatos.add(item.id, item);
  }

  addItem(item) {
    this.srcDatos.add(item);// Ver lo de examen
    this.visualElement.addItem(item);
    this.visualElementFull.addItem(item);
  }

  addItemOptional(item) {
    this.visualElementOpcional.addItem(item);
  }

  addAll() {
    let items = this.getSelectedItems();
    this.srcDatos.addAll(items);
  }

  removeItem(item) {
    this.visualElement.removeItem(item);
    this.visualElementFull.removeItem(item);
    this.srcDatos.removeById(item.id);
  }
 
  removeItemOpcional(item) {
    this.visualElementOpcional.removeItem(item);
  }

  removeItem2(item) {
    this.visualElement.removeItemElement(item);
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
  removeItemsOpcional(items) {
    let n = items.length;
    for (let i = 0; i < n; i++) {
      this.removeItemOpcional(items[i]);
    }
  }

  removeItems2(items) {
    let n = items.length;
    for (let i = 0; i < n; i++) {
      this.removeItem2(items[i]);
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
    console.log('esta es',listItems)

    this.dataTable = $(this.jqid).DataTable(
      {
        "rowId": "dataValue.id",
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
          "thousands": ".",
          "decimal": ",",
          "emptyTable": "No se encontraron antibioticos seleccionados",
          "loadingRecords": "Por favor espere - cargando ...",
          "zeroRecords": "No se encontraron antibioticos",
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
      
          },
        ],
        "columns": [
          { "data": "dataValue.name",width: '100%' },
        ],
      }
    );
    this.dataTable.on('select', function(e, dt, type, indexes) {
      // console.table(indexes);
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
 
  removeItemElement(item) {
    let id = "#".concat(item.id);
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
          if (dataRows[j].id === examenes[i].id) {
            exist = true;
          }
        }
      }
      if (!exist) {
        let dataItemAux = new DataItem(examenes[i].id, examenes[i].name, examenes[i].code,
          examenes[i].active,examenes[i].opcional, typeof examenes[i].incluir !== "undefined" && examenes[i].incluir !== null ? examenes[i].incluir: "", typeof examenes[i].cim !== "undefined" && examenes[i].cim !== null ? examenes[i].cim : "",typeof examenes[i].diameter !== "undefined" && examenes[i].diameter !== null ? examenes[i].diameter: "",typeof examenes[i].etest !== "undefined" && examenes[i].etest !== null ? examenes[i].etest: "",typeof examenes[i].interpretation!== "undefined" && examenes[i].interpretation!== null ? examenes[i].interpretation: "", typeof examenes[i].includeinreport!== "undefined" && examenes[i].includeinreport!== null ? examenes[i].includeinreport: "");

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


class DatatableFullVisualTarget {

  jqid;
  dataTable;

  constructor(jqid) {
    this.jqid = jqid;
  }

  init(listItems) {
    this.dataTable = $(this.jqid).DataTable(
      {
        "rowId": "dataValue.id",
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
          "thousands": ".",
          "decimal": ",",
          "emptyTable": "No se encontraron antibioticos seleccionados",
          "loadingRecords": "Por favor espere - cargando ...",
          "zeroRecords": "No se encontraron antibioticos",
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
            targets: 1,
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let valor = "";
              if(row.dataValue.cim !== undefined && row.dataValue.cim != 0 && row.dataValue.cim != null){
                valor = row.dataValue.cim;
              }
              let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
              if(!tieneClase){
                return `<input class="form-control input-cim" value="${valor}" type="text"/>`;
              }else{
                return `<input class="form-control" value="${valor}" type="text" disabled/>`;
              }    
            },
          },
          {
            targets: 2,
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let valor = "";
              if(row.dataValue.diameter !== undefined && row.dataValue.diameter != 0 && row.dataValue.diameter != null){
                valor = row.dataValue.diameter;
              }

              let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
              if(!tieneClase){
                return `<input class="form-control input-diametro" value="${valor}" type="text"/>`;
              }else{
                return `<input class="form-control" value="${valor}" type="text" disabled/>`;
              }    
             
            },
          },
          {
            targets: 3,
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let valor = "";
              if(row.dataValue.etest !== undefined && row.dataValue.etest != 0 && row.dataValue.etest != null){
                valor = row.dataValue.etest;
              }
              let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
              if(!tieneClase){
                return `<input class="form-control input-etest" value="${valor}" type="text"/>`;
              }else{
                return `<input class="form-control" value="${valor}" type="text" disabled/>`;
              }    
              
            },
          },
          {
            targets: [4],
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let estado = "";
              if(row.dataValue.interpretation !== undefined && row.dataValue.interpretation != ""  && row.dataValue.interpretation != null){
                estado = row.dataValue.interpretation;
              }
              let estados = ["SENSIBLE", "RESISTENTE", "INTERMEDIO"];
              let select;
              let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
              if(!tieneClase){
                select = `<select  class="form-control select-interpretacion" >`
              }else{
                select = `<select  class="form-control"  disabled>`
              }  
             
              select += `<option value="" disabled selected>Seleccione</option>`
              estados.forEach((element) => {
                if (element === estado) {
                  select += "<option value=" + element + " selected>" + element + "</option>";
                } else {
                  select += "<option value=" + element + ">" + element + "</option>";
                }
              });
            
              select += '</select>';
              return select;
            },
          },
          {
            targets: [5],
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let checked = "";
              if(row.dataValue.includeinreport == "S"){
                checked = "checked";
              }
              let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
              if(!tieneClase){
                return `<input type="checkbox" value=""  data-incluir-examen='x' class="checkable chck-informe" ${checked}/>`;
              }else{
                return `<input type="checkbox" value=""  class="checkable" ${checked} disabled/>`;
              }  

           
            },
          },
          {
            targets: [6],
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, full, meta) {
              let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
              if(!tieneClase){
                return `
                  <div class="row btn-toolbar flex-nowrap">        
                    <a href="#"  class="p-2 eliminar-antibiograma" title="Eliminar Antibiograma"><i class="fas fa-trash-alt" aria-hidden="true"></i></a>
                  </div>
                `;
              }else{
                return `
                <div class="row btn-toolbar flex-nowrap">        
                  <span class="p-2" title="Eliminar Antibiograma"><i class="fas fa-unlink" aria-hidden="true" disabled></i></span>
                </div>
              `;
              }  
            },
          },
        ],
        "columns": [
          { "data": "dataValue.name",width: '25%' },
          { "data": "",width: '10%' },
          { "data": "",width: '10%' },
          { "data": "",width: '10%' },
          { "data": "",width: '15%' },
          { "data": "",width: '10%' },
          { "data": "",width: '10%' },
        ],
      }
    );
    this.dataTable.on('select', function(e, dt, type, indexes) {
      // console.table(indexes);
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
          if (dataRows[j].id === examenes[i].id) {
            exist = true;
          }
        }
      }
      if (!exist) {
        let dataItemAux = new DataItem(examenes[i].id, examenes[i].name, examenes[i].code,
          examenes[i].active,examenes[i].opcional,   typeof examenes[i].incluir !== "undefined" && examenes[i].incluir !== null ? examenes[i].incluir: "", typeof examenes[i].cim !== "undefined" && examenes[i].cim !== null ? examenes[i].cim : "",typeof examenes[i].diameter !== "undefined" && examenes[i].diameter !== null ? examenes[i].diameter: "",typeof examenes[i].etest !== "undefined" && examenes[i].etest !== null ? examenes[i].etest: "",typeof examenes[i].interpretation!== "undefined" && examenes[i].interpretation!== null ? examenes[i].interpretation: "", typeof examenes[i].includeinreport!== "undefined" && examenes[i].includeinreport!== null ? examenes[i].includeinreport: ""
          );
       
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

class DatatableOpcionalVisualTarget {

  jqid;
  dataTable;

  constructor(jqid) {
    this.jqid = jqid;
  }

  init(listItems) {
    this.dataTable = $(this.jqid).DataTable(
      {
        "rowId": "dataValue.id",
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
          "thousands": ".",
          "decimal": ",",
          "emptyTable": "No se encontraron antibioticos seleccionados",
          "loadingRecords": "Por favor espere - cargando ...",
          "zeroRecords": "No se encontraron antibioticos",
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
            targets: 1,
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let checked = "";
              let disabled = "";
              if(row.dataValue.incluir == "S"){
                  checked = "checked";
                  disabled = "disabled";
              }

              let tieneClase = document.getElementById("idDataTargetOpcional").classList.contains("disabled-element");
              if(!tieneClase){
                return `<input type="checkbox" value=""  data-opcional-anti='x' class="chck-anti" ${checked} ${disabled}/> `;
              }else{
                return `<input type="checkbox" value="" class="" ${checked} ${disabled} disabled/> `;
              }
            },
          },
        ],
        "columns": [
          { "data": "dataValue.name",width: '50%' },
          { "data": "",width: '50%' },
        ],
      }
    );
    this.dataTable.on('select', function(e, dt, type, indexes) {
      // console.table(indexes);
    });
    this.dataTable.on('deselect', function(e, dt, type, indexes) {
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
          if (dataRows[j].id === examenes[i].id) {
            exist = true;
          }
        }
      }
      if (!exist) {
        let dataItemAux = new DataItem(examenes[i].id, examenes[i].name, examenes[i].code,
          examenes[i].active,examenes[i].opcional,typeof examenes[i].incluir !== "undefined" && examenes[i].incluir !== null ? examenes[i].incluir: "", typeof examenes[i].cim !== "undefined" && examenes[i].cim !== null ? examenes[i].cim : "",typeof examenes[i].diameter !== "undefined" && examenes[i].diameter !== null ? examenes[i].diameter: "",typeof examenes[i].etest !== "undefined" && examenes[i].etest !== null ? examenes[i].etest: "",typeof examenes[i].interpretation!== "undefined" && examenes[i].interpretation!== null ? examenes[i].interpretation: "", typeof examenes[i].includeinreport!== "undefined" && examenes[i].includeinreport!== null ? examenes[i].includeinreport: "");
       

          
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

// Mover a clase
function genVSFiller(jqid, vs) {
  return function(data) {
    let listItems = new Array();
    let n = data.length;
    console.log('dddddaaaa',data);
    for (i = 0; i < n; i++) {
      let itemex = data[i];
      let datoExamen = new DataItem(itemex.id, itemex.name, itemex.code,itemex.active,itemex.opcional, typeof itemex.incluir !== "undefined" && itemex.incluir !== null ? itemex.incluir: "", typeof itemex.cim !== "undefined" && itemex.cim !== null ? itemex.cim : "",typeof itemex.diameter !== "undefined" && itemex.diameter !== null ? itemex.diameter: "",typeof itemex.etest !== "undefined" && itemex.etest !== null ? itemex.etest: "",typeof itemex.interpretation !== "undefined" && itemex.interpretation !== null ? itemex.interpretation : "", typeof itemex.includeinreport !== "undefined" && itemex.includeinreport !== null ? itemex.includeinreport : "");
      let item = new Item(datoExamen);
      listItems.push(item);
    }

    console.log("listItems",listItems);
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
      $(this.jqid + " option:contains('No hay antibióticos para mostrar')").remove();
      $(this.jqid).append(`<option value="" disabled style="color:#000;text-align:center">No hay antibióticos para mostrar</option>`);
    }else{
      $(this.jqid + " option:contains('No hay antibióticos para mostrar')").remove();
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
          //envia el item la otra tabla
          let item = vs.getSelectedItemById(countVal.toString());
          vs.removeItemById(countVal.toString());
          vt.addItem(item);
          $(jqid).val('');
         }
      }
  });
  
  }
}



class Mover {

  dtVT;
  dtFVT;
  dt;
  vt;
  vs;
  me;

  constructor(dtVT, dtFVT, dt, vt, vs) {
    this.dtVT = dtVT;
    this.dtFVT = dtFVT;
    this.dt = dt;
    this.vt = vt;
    this.vs = vs;
  }

  add(movedor, itemId) {
    console.log('entra aca par amover');
    let item = movedor.vs.getSelectedItemById(itemId);
    movedor.vs.removeItemById(itemId);
    movedor.vt.addItem(item);
    movedor.vs.removePanelItem(item);
  }

}

class DualData {

  dtVT;
  dt;
  vt;
  vs;
  jqidDataTarget;
  jqidDataFullTarget;
  jqidVisualSource;
  jqidPanelVisualSource;
  jqidSearcher;

  constructor(jqidDataTarget, jqidDataFullTarget, jqidVisualSource, jqidSearcher, jqidPanelVisualSource,jqidDataOpcionalTarget) { // "#idDataTarget"

    this.jqidVisualSource = jqidVisualSource;
    this.jqidSearcher = jqidSearcher;
    this.jqidDataTarget = jqidDataTarget;
    this.jqidDataFullTarget = jqidDataFullTarget;
    this.jqidPanelVisualSource = jqidPanelVisualSource;
    this.dtVT = new DataTableVisualTarget(this.jqidDataTarget);
    this.dtFVT = new DatatableFullVisualTarget(this.jqidDataFullTarget);
    this.dtOVT = new DatatableFullVisualTarget(this.jqidDataOpcionalTarget);
    this.dt = new DataSource([]);
    this.vt = new VisualTarget(dt, dtVT, dtFVT, dtOVT);
    this.vs = new VisualSource();
    test = genVSFiller(this.jqidVisualSource, this.vs, this.jqidSearcher); //#idDualDataVisualSource" "#idSearcher"

    console.log("test",test)

    let svc = new CfgExamen();
    svc.getExamenes(test);

    $("#clickAddBtn").click(this.eventoAdd);
    $("#clickDelBtn").click(this.eventoDel);
    $(this.jqidPanelVisualSource).hide();

    $('#idBtnExamen').click(function(e) {
      $(this.jqidVisualSource).show();
      $(this.jqidPanelVisualSource).hide();
      this.vs.visualElement.jqid = this.jqidVisualSource;
      this.vs.swapDs2Examen();
    });
    $('#idBtnPanel').click(function(e) {
      $(this.jqidPanelVisualSource).show();
      $(this.jqidVisualSource).hide();
      this.vs.visualElement.jqid = this.jqidPanelVisualSource;
      this.vs.swapDs2Panel();
    });


  }

  eventoAdd() {
    let itemId = this.jqidVisualSourcevs.getSelectedItemId();
    let item = this.jqidVisualSourcevs.getSelectedItemById(itemId);
    console.log(item);
    console.log('empieza eventadd'); 
    console.log(itemId);
    console.log(item);
    console.log('termina eventadd'); 
    this.jqidVisualSourcevs.removeItemById(itemId);
    this.jqidVisualSourcevt.addItem(item);
    this.jqidVisualSourcevs.removePanelItem(item);
  };

  eventoDel() {
    let items = vt.getSelectedItems();
    this.jqidVisualSourcevt.removeItems(items);
    this.jqidVisualSourcevs.addItems(items);
  }

}




// metodo resistencia

class VisualTargetResistencia {

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
    console.log("item selected",item);
    this.srcDatos.add(item.id, item);
  }

  addItem(item) {
    this.srcDatos.add(item);// Ver lo de examen
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
 

  removeItem2(item) {
    this.visualElement.removeItemElement(item);
    this.srcDatos.removeById(item.id);
  }

  removeAll() {
    this.srcDatos.clear();
    this.visualElement.clear();
  }

  removeItems(items) {
    let n = items.length;
    console.log(n,"length")
    for (let i = 0; i < n; i++) {
      this.removeItem(items[i]);
    }
  }
  removeItemsOpcional(items) {
    let n = items.length;
    console.log(n,"length")
    for (let i = 0; i < n; i++) {
      this.removeItemOpcional(items[i]);
    }
  }

  removeItems2(items) {
    console.log("item remove2",items);
    let n = items.length;
    console.log(n,"length")
    for (let i = 0; i < n; i++) {
      this.removeItem2(items[i]);
    }
  }

  clear() {
    this.visualElement.clear();
  }
};


class DataSourceResistencia {
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


class VisualSourceResistencia {

  srcDatos;
  visualElement;
  srcDatosExamen;
  suscriptores;

  constructor() {
    this.suscriptores = new Array();

  }

  onDoubleClick(observador) {
    this.suscriptores.push(observador);
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



class DataItemResistencia { 
  id;
  method;
  result;
  data;
  constructor(id,method,result) {
    this.id = id;
    this.method = method;
    this.result = result;
    this.data = {
      "id": this.id,
      "method": this.method,
      "result": this.result,
    }
  }

  // itemex.id, itemex.name, itemex.code,itemex.active
  get id() {
    return this.id;
  }
  set id(code) {
    this.method = code;
  }
  get text() {
    return this.method;
  }
  get data() {
    return this.data;
  }

  get searchText() {
    return this.method;
  }
}

class ItemResistencia {
  id;
  visibleValue;
  dataValue;
  searchValue;

  constructor(dataItem) {
    this.id = dataItem.id;
    this.visibleValue = dataItem.method;
    this.dataValue = dataItem.data;
    this.searchValue = dataItem.searchText;
  }
}


class DatatableResistencia {

  jqid;
  dataTable;

  constructor(jqid) {
    this.jqid = jqid;
  }

  init(listItems) {

    this.dataTable = $(this.jqid).DataTable(
      {
        "rowId": "id",
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
          "thousands": ".",
          "decimal": ",",
          "emptyTable": "No se encontraron metodos de resistencia seleccionados",
          "loadingRecords": "Por favor espere - cargando ...",
          "zeroRecords": "No se encontraron metodos de resistencia",
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
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let estado = "";
              if(typeof row.dataValue !== "undefined" && typeof row.dataValue.method !== "undefined" && row.dataValue.method != ""){
                estado = row.dataValue.method;
              }
              let misMetodos = [];
              if(metds.misMetodos.length !== 0){
                misMetodos = [...metds.misMetodos];
              }

              let select;
              let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
              if(!tieneClase){
                select = `<select  class="form-control select-resistencia">`;
              }else{
                select = `<select  class="form-control"  disabled>`
              }  
              select += `<option value="" disabled selected>Seleccione</option>`
              metds.misMetodos.forEach((element) => {
                if (element === estado) {
                  select += "<option value=" + element + " selected>" + element + "</option>";
                } else {
                  select += "<option value=" + element + ">" + element + "</option>";
                }
              });
            
              select += '</select>';
              return select;
            },
          },
          {
            targets: 1,
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let estado = "";
              if(typeof row.dataValue !== "undefined" && typeof row.dataValue.result !== "undefined" && row.dataValue.result != ""){
                estado = row.dataValue.result;
              }
              let misMetodos = [];
              if(metds.misMetodosResult.length !== 0){
                misMetodos = [...metds.misMetodosResult];
              }

              let select;
              let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
              if(!tieneClase){
                select = `<select  class="form-control select-resultado">`;
              }else{
                select = `<select  class="form-control"  disabled>`
              }  
              select += `<option value="" disabled selected>Seleccione</option>`
              misMetodos.forEach((element) => {
                if (element === estado) {
                  select += "<option value=" + element + " selected>" + element + "</option>";
                } else {
                  select += "<option value=" + element + ">" + element + "</option>";
                }
              });
            
              select += '</select>';
              return select;
            },
          },
          {
            targets: 2,
            width: '30px',
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              if(typeof row.id === "string"){
                let tieneClase = document.getElementById("idDataTargetFull").classList.contains("disabled-element");
                if(!tieneClase){
                  return `
                  <div class="row btn-toolbar flex-nowrap">        
                    <a href="#"  class="p-2 eliminar-metodo" title="Eliminar metodo"><i class="fas fa-trash-alt" aria-hidden="true"></i></a>
                  </div>
                  `;
                }else{
                  return `
                <div class="row btn-toolbar flex-nowrap">        
                  <span class="p-2" title="Eliminar metodo"><i class="fas fa-unlink" aria-hidden="true"></i></span>
                </div>
                `;
                }  

              
              }else{
                return "";
              }
            },
          },
        ],
        "columns": [
          { "data": "",width: '40%' },
          { "data": "",width: '40%' },
          { "data": "",width: '20%' },
        ],
      }
    );
    this.dataTable.on('select', function(e, dt, type, indexes) {
      // console.table(indexes);
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

  getCounts() {
    let indx = this.dataTable.rows().data();
    let row_counts = indx.data().length;
    return row_counts;
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
          if (dataRows[j].id === examenes[i].id) {
            exist = true;
          }
        }
      }
      if (!exist) {
        let dataItemAux = new DataItemResistencia(examenes[i].id, typeof examenes[i].method !== "undefined" && examenes[i].method !== null ? examenes[i].method: "",typeof examenes[i].result !== "undefined" && examenes[i].result !== null ? examenes[i].result: "");
       

          
        let itemAux = new ItemResistencia(dataItemAux);
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


function CfgMethodList() {
  this.misMetodos = [];
  this.misMetodosResult = [];
}

CfgMethodList.prototype.getMetodos = function () {
  var jqXHR = $.ajax({
      url: gCte.getctx + "/Microbiologia/api/getAntibiogramOptions",
      async: false,
  });
  this.misMetodos = jqXHR.responseJSON.methodList;
  this.misMetodosResult = jqXHR.responseJSON.methodResults;
};

let metds = new CfgMethodList();
metds.getMetodos();


function CfgResistance() {
  this.misResistencias = [];
}

CfgResistance.prototype.getResistencia = function (antibiotico,method,cim) {
  let microorganismo = $("#idTestCanvasAntibiogramMicroorganism").val();
  var jqXHR = $.ajax({
      url: gCte.getctx + `/Microbiologia/api/getAntibiogramResistance?antibioticId=${antibiotico}&microorganismId=${microorganismo}&method=${method}&cim=${cim}`,
      async: false,
  });
  this.misResistencias = jqXHR.responseJSON;
};
