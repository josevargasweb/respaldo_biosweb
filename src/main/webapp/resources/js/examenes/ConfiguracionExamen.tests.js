class DataItem { 

  ctIdtest;
  ctCodigo;
  ctAbreviado;
  ctDescripcion;
  cmueIdtipomuestra;
  cmueDescripcionabreviada;
  cmueColor
  csecDescripcion;
  csecIdseccion;
  csecColor;
  cmetIdmetodo;
  cmetDescripcion;
  data;
  constructor(ctIdtest, ctCodigo, ctAbreviado, ctDescripcion, cmueIdtipomuestra, cmueDescripcionabreviada, cmueColor, csecDescripcion, csecIdseccion, csecColor, cmetIdmetodo, cmetDescripcion) {
    this.ctIdtest = ctIdtest;
    this.ctCodigo = ctCodigo;
    this.ctAbreviado = ctAbreviado;
    this.ctDescripcion = ctDescripcion;
    this.cmueIdtipomuestra = cmueIdtipomuestra;
    this.cmueDescripcionabreviada = cmueDescripcionabreviada;
    this.cmueColor = cmueColor;
    this.csecDescripcion = csecDescripcion;
    this.csecIdseccion = csecIdseccion;
    this.csecColor = csecColor;
    this.cmetIdmetodo = cmetIdmetodo;
    this.cmetDescripcion = cmetDescripcion;
    this.data = {
      "ctIdtest": this.ctIdtest, 
      "ctCodigo": this.ctCodigo, 
      "ctAbreviado": this.ctAbreviado, 
      "ctDescripcion": this.ctDescripcion, 
      "cmueIdtipomuestra": this.cmueIdtipomuestra, 
      "cmueDescripcionabreviada": this.cmueDescripcionabreviada,
      "cmueColor": this.cmueColor,
      "csecDescripcion": this.csecDescripcion,
      "csecIdseccion": this.csecIdseccion,
      "csecColor": this.csecColor,
      "cmetIdmetodo": this.cmetIdmetodo,
      "cmetDescripcion": this.cmetDescripcion
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

  constructor(datasourceArray) {
    this.srcDatos = new Map();
    this.addAll(datasourceArray);
  }

  load() {
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
      let opt = `<option value="${v}" title="${k.dataValue.ctDescripcion != null ? k.dataValue.ctDescripcion : ""}" class="pointer">${k.visibleValue}</option>`
      // let opt = new Option(k.visibleValue, v);
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
	 console.log("addItem(item) ")
    console.log(item)
    this.srcDatos.add(item);// Ver lo de examen
    //el siguiente if es para que no se cambie al momento de agregar items desde grupo de muestras
    if (typeof item.dataValue.ctIdtest !== 'undefined'){
        $("#divMuestras").empty();
        let muestras = [];
        $("#divSeccion").empty();
        let secciones = [];
        let metodos = [];
        this.srcDatos.srcDatos.forEach(function(datos){
            let muestra = datos.dataValue.cmueDescripcionabreviada;
            let colorMuestra = datos.dataValue.cmueColor;
            let seccion = datos.dataValue.csecDescripcion;
            let colorSeccion = datos.dataValue.csecColor;
            let metodo = datos.dataValue.cmetDescripcion;
            //if (!muestras.includes(muestra)){
            if (muestras.filter(e => e.descripcion == muestra).length === 0) {
                let muestrarray = {
                    'descripcion': muestra,
                    'color': colorMuestra
                }
                muestras.push(muestrarray);
            }
            /*if (!secciones.includes(seccion)){
                secciones.push(seccion);
            }*/
            if (secciones.filter(e => e.descripcion == seccion).length === 0) {
                let seccionarray = {
                    'descripcion': seccion,
                    'color': colorSeccion
                }
                secciones.push(seccionarray);
            }
            if (!metodos.includes(metodo) && metodo!==""){
                metodos.push(metodo);
            }
        });
        //console.log(muestras);
        muestras.forEach((element, index) => {
            if(index == 0 && $("#muestraExamen").val() == ""){
              //$("#muestraExamen").val(element.descripcion);
            }
            if (index<muestras.length-1){
                $("#divMuestras").append(`<span class="${element.color != null ? "label label-lg font-weight-bold label-inline" : ""}" style="${element.color != null ? "background-color:"+element.color : ""}">${element.descripcion}</span>,&nbsp;`);
            } else {
                $("#divMuestras").append(`<span class="${element.color != null ? "label label-lg font-weight-bold label-inline" : ""}" style="${element.color != null ? "background-color:"+element.color : ""}">${element.descripcion}<s/pan>`);
            }
        });
        secciones.forEach((element, index) => {
            if (index<secciones.length-1){
                $("#divSeccion").append(`<span class="${element.color != null ? "label label-lg font-weight-bold label-inline" : ""}" style="${element.color != null ? "background-color:"+element.color : ""}">${element.descripcion}</span>,&nbsp;`);
            } else {
                $("#divSeccion").append(`<span class="${element.color != null ? "label label-lg font-weight-bold label-inline" : ""}" style="${element.color != null ? "background-color:"+element.color : ""}">${element.descripcion}</span>`);
            }
        });
        //$("#divMuestras").html(muestras.join(", "));
        //$("#divSeccion").html(secciones.join(", "));
        $("#divMetodo").html(metodos.join(", "));
    }
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
    // console.log('item',item.length,item.dataValue.cmueDescripcionabreviada,$("#muestraExamen").val(),item[0].dataValue.cmueDescripcionabreviada == $("#muestraExamen").val())
    if(item.dataValue.cmueDescripcionabreviada == $("#muestraExamen").val()){
      $("#muestraExamen").val("");
  }
    this.visualElement.removeItem(item);
    this.srcDatos.removeById(item.id);
    //el siguiente if es para que no se cambie al momento de eliminar items desde grupo de muestras
    if (typeof item.dataValue.ctIdtest !== 'undefined'){
        let muestras = [];
        $("#divMuestras").empty();
        let secciones = [];
        $("#divSeccion").empty();
        let metodos = [];
        this.srcDatos.srcDatos.forEach(function(datos){
            let muestra = datos.dataValue.cmueDescripcionabreviada;
            let colorMuestra = datos.dataValue.cmueColor;
            let seccion = datos.dataValue.csecDescripcion;
            let colorSeccion = datos.dataValue.csecColor;
            let metodo = datos.dataValue.cmetDescripcion;
            if (muestras.filter(e => e.descripcion == muestra).length === 0) {
                let muestrarray = {
                    'descripcion': muestra,
                    'color': colorMuestra
                }
                muestras.push(muestrarray);
            }
            if (secciones.filter(e => e.descripcion == seccion).length === 0) {
                let seccionarray = {
                    'descripcion': seccion,
                    'color': colorSeccion
                }
                secciones.push(seccionarray);
            }
            if (!metodos.includes(metodo) && metodo!==""){
                metodos.push(metodo);
            }
        });
        muestras.forEach((element, index) => {
          if(index == 0 && $("#muestraExamen").val() == ""){
           // $("#muestraExamen").val(element.descripcion);
          }
            if (index<muestras.length-1){
                $("#divMuestras").append(`<span class="${element.color != null ? "label label-lg font-weight-bold label-inline" : ""}" style="background-color:${element.color}">${element.descripcion}<span>,&nbsp;`);
            } else {
                $("#divMuestras").append(`<span class="${element.color != null ? "label label-lg font-weight-bold label-inline" : ""}" style="background-color:${element.color}">${element.descripcion}<span>`);
            }
        });
        secciones.forEach((element, index) => {
            if (index<secciones.length-1){
                $("#divSeccion").append(`<span class="${element.color != null ? "label label-lg font-weight-bold label-inline" : ""}" style="background-color:${element.color}">${element.descripcion}<span>,&nbsp;`);
            } else {
                $("#divSeccion").append(`<span class="${element.color != null ? "label label-lg font-weight-bold label-inline" : ""}" style="background-color:${element.color}">${element.descripcion}<span>`);
            }
        });
        $("#divMetodo").html(metodos.join(", "));
    }
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
  
  getMuestras(){
      return this.muestras;
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
          "sProcessing":     "Procesando...",
          "sZeroRecords":    "No se encontraron tests",
          "sEmptyTable":     "No se encontraron tests seleccionados",
          "select": {
              "rows": {
                  _: "%d filas seleccionadas",
                  0: "",
                  1: "1 fila seleccionada"
              }
          }
      },
       "columnDefs": [
        {
          targets:1,
          className:'pointer',
          render: function(data, type, row, meta) {
            if(data != null && data.length > 6){
              return `<span title="${data}">${data.substr(0,6).replace(/[^a-zA-Z]+$/g, "")}</span>`;
            }else if(data != null && data.length <= 6){
              return data;
            }
            else{
              return "";
            }
          }
        },
        {
          targets:2,
          className:'pointer',
          render: function(data, type, row, meta) {
            if(data != null && data.length > 25){
              return `<span title="${data}">${data.substr(0,25).replace(/[^a-zA-Z]+$/g, "")}</span>`;
            }else if(data != null && data.length <= 25){
              return data;
            }
            else{
              return "";
            }
          }
        },
        {
          targets:3,
          className:'pointer',
          render: function(data, type, row, meta) {
            if(data != null && data.length > 31){
              return `<span title="${data}">${data.substr(0,31).replace(/[^a-zA-Z]+$/g, "")}</span>`;
            }else if(data != null && data.length <= 31){
              return data;
            }
            else{
              return "";
            }
          }
        }
          /*{
            targets: 3,
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
                if (data!==null){
                    let html = "<ul>";
                    data.forEach(function (met){
                        html += `<li>${met.cmetDescripcion}</li>`
                    });
                    html += "</ul>";
                    return html;
                }
                return null;
            }
          }*/
        ],
        "columns": [
          //{ "": ""},
          { "data": "dataValue.ctCodigo","width": "10%"},
          { "data": "dataValue.ctAbreviado","width": "20%"},
          { "data": "dataValue.cmueDescripcionabreviada" ,"width": "10%"},
          { "data": "dataValue.csecDescripcion" ,"width": "20%"},
          { "data": "dataValue.cmetDescripcion","width": "20%" }
        ],
        /*initComplete: function () {
            const filas = tablaExamenes.rows().data();
            $("#ModalDatosOrdenDiagnostico").text(filas[0].cd_DESCRIPCION);
            $("#ModalDatosOrdenObs").text(filas[0].df_OBSERVACION);
        },*/
        "createdRow": function( row, data, dataIndex ) {
            if (dataIndex === 0){
                muestraPorDefecto = data.dataValue.cmueIdtipomuestra;
                seccionPorDefecto = data.dataValue.csecIdseccion;
            }
           // console.log("data value metodo "+ data.dataValue.cmetIdmetodo)
           // console.log(metodoPorDefecto)
        //    if (metodoPorDefecto == 0 && data.dataValue.cmetIdmetodo != 0){
                metodoPorDefecto = data.dataValue.cmetIdmetodo;
           // }
            
            
        }
      }
    );
    /*
    this.dataTable.on('select', function(e, dt, type, indexes) {
      let filas = dt.rows({ selected: true }).data();
      let valorPorDefecto = filas[0].dataValue.cmtEsvalorpordefecto;

      if (valorPorDefecto === 'N') {
        filas[0].dataValue.cmtEsvalorpordefecto = 'S';
        console.log('Cambiar a S');
        
        let otrasfilas = dt.rows({ selected: false }).data();
        for (let i=0; i<otrasfilas.length; i++){
            otrasfilas[i].dataValue.cmtEsvalorpordefecto = 'N';
        }
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
/*
      let datoTest = new DataItem(itemex.cfgTest.ctIdtest, itemex.cfgTest.ctCodigo, itemex.cfgTest.ctAbreviado, itemex.cfgTest.ctDescripcion, itemex.cfgMuestras.cmueIdtipomuestra, 
        itemex.cfgMuestras.cmueDescripcionabreviada, itemex.cfgMuestras.cmueColor,
        itemex.cfgSecciones.csecDescripcion, itemex.cfgSecciones.csecIdseccion, itemex.cfgSecciones.csecColor, (itemex.metodoPorDefecto!==null?itemex.metodoPorDefecto.cmetIdmetodo:""),
        (itemex.metodoPorDefecto!==null?itemex.metodoPorDefecto.cmetDescripcion:"")
      );
*/
// Se cambia para ajustarse a nueva implementacion API. Eric Nicholls 2023-04-14

      let datoTest = new DataItem(itemex.ctIdtest, itemex.ctCodigo, itemex.ctAbreviado, itemex.ctDescripcion, itemex.cmueIdtipomuestra, 
        itemex.cmueDescripcionabreviada, itemex.cmueColor,
        itemex.csecDescripcion, itemex.csecIdseccion, itemex.csecColor, (itemex.cmetIdmetodo!==null?itemex.cmetIdmetodo:""),
        (itemex.cmetIdmetodo!==null?itemex.cmetDescripcion:"")
      );
      let item = new Item(datoTest);
      listItems.push(item);
    }
    let ds = new DataSource(listItems);
    let selectVS = new SelectVisualSource(jqid);

    this.srcDatos = ds;
    this.visualElement = selectVS;
    this.visualElement.init(ds);
    vs.load(ds, selectVS);

    const visualSearcher = new VisualSearcher("#idSearcherTest", vs, "#selectSeccionTest");
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
  
}

class VisualSearcher {
  dataSearcher;
  jqid;
  visualSource;
  selectSeccion;
  
  constructor(jqid, visualSource, selectSeccion) {
    this.jqid = jqid;
    this.visualSource = visualSource;
    this.selectSeccion = selectSeccion;
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

