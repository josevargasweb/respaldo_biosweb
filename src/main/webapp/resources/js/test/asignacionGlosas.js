class DataGlosas { 
  cgIdglosa;
  cgCodigoglosa;
  cgDescripcion;
  cgtEsglosacritica;
  cgtSefirmaporlotes;
  cgIdSeccion;
  data;
  
  constructor(cgIdglosa, cgCodigoglosa, cgDescripcion, cgtEsglosacritica, cgtSefirmaporlotes, cgIdSeccion) {
    this.cgIdglosa = cgIdglosa;
    this.cgCodigoglosa = cgCodigoglosa;
    this.cgDescripcion = cgDescripcion;
    this.cgtEsglosacritica = cgtEsglosacritica;
    this.cgtSefirmaporlotes = cgtSefirmaporlotes;
    this.cgIdSeccion = cgIdSeccion;
    this.data = {
      "cgIdglosa": this.cgIdglosa, 
      "cgCodigoglosa": this.cgCodigoglosa, 
      "cgDescripcion": this.cgDescripcion,
      "cgtEsglosacritica": this.cgtEsglosacritica,
      "cgtSefirmaporlotes": this.cgtSefirmaporlotes,
      "cgIdSeccion": this.cgIdSeccion
    };
  }
  
  get id() {
    return this.cgIdglosa;
  }
  set id(code) {
    this.cgIdglosa = code;
  }
  get text() {
    return this.cgCodigoglosa + " - " + this.cgDescripcion;
  }
  get data() {
    return this.data;
  }

  get searchText() {
    return this.cgCodigoglosa + " - " + this.cgDescripcion + " " + this.cgIdSeccion;
  }
}

class DataTableGlosas {
  jqid;
  dataTable;

  constructor(jqid) {
    this.jqid = jqid;
  }
  
  init(listItems) {

    this.dataTable = $(this.jqid).DataTable(
      {
        "rowId": "dataValue.cgIdglosa",
        "table": "#tableGlosas",
        "searching": false,
        "lengthChange": false,
        "scrollY": "100px",
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
          url: getctx + "/resources/js/orden/Spanish.json",
          select: {
              rows: {
                  _: "%d filas seleccionadas",
                  0: "",
                  1: "1 fila seleccionada"
              }
          }
      },
       
        "columns": [
          { "data": "dataValue.cgCodigoglosa"},
          { "data": "dataValue.cgDescripcion"},
          { "data": "dataValue.cgtEsglosacritica"},
          { "data": "dataValue.cgtSefirmaporlotes" }
        ],
        "columnDefs": [
          {
            targets: 2,
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let checked = '';
              if (row.dataValue.cgtEsglosacritica !== undefined && row.dataValue.cgtEsglosacritica === 'S') {
                checked = 'checked';
              }
              
              return `<input type="checkbox" ${checked} class="checkGlosaCritica checkable formTest"/>`;
            }
          },{
             targets: 3,
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
              let checked = '';
              if (row.dataValue.cgtSefirmaporlotes !== undefined && row.dataValue.cgtSefirmaporlotes === 'S') {
                checked = 'checked';
              }
              
              return `<input type="checkbox" ${checked} class="checkFirmaPorLotes checkable formTest"/>`;
            } 
          }
        ]
      }
      
    );

    this.dataTable.on('user-select', function(e, dt, type, cell) {
      let data = dt.row( cell.node() ).data();
      let data_id = "#"+data.id;
      
      data.dataValue.cgtEsglosacritica = $(data_id).find('input.checkGlosaCritica').is(':checked') ? 'S' : 'N';
      data.dataValue.cgtSefirmaporlotes = $(data_id).find('input.checkFirmaPorLotes').is(':checked') ? 'S' : 'N';
      
      console.log("cgtEsglosacritica: "+data.dataValue.cgtEsglosacritica)
      console.log("cgtSefirmaporlotes: "+data.dataValue.cgtSefirmaporlotes)
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

function genVSFillerGlosa(jqid, vsg) {
  return function(data) {
    let listItems = new Array();
    let n = data.dato.length;
    for (i = 0; i < n; i++) {
      let itemex = data.dato[i];
      let datoGlosa = new DataGlosas(itemex.cgIdglosa, itemex.cgCodigoglosa, itemex.cgDescripcion, 
        (itemex.cgtEsglosacritica !== null ? itemex.cgtEsglosacritica : "N"),
        (itemex.cgtSefirmaporlotes !== null ? itemex.cgtSefirmaporlotes : "N"), itemex.cgIdSeccion
      );
      let item = new Item(datoGlosa);
      listItems.push(item);
    }
    let ds = new DataSource(listItems);
    let selectVS = new SelectVisualSource(jqid);

    this.srcDatos = ds;
    this.visualElement = selectVS;
    this.visualElement.init(ds);
    vsg.load(ds, selectVS);

    const visualSearcher = new VisualSearcher("#idGlosaSearcher", vsg, "#selectSeccionGlosa");
    //$('#idBtnExamen').prop('disabled', false);
    if(listItems.length === 0){
      $(this.jqid + " option:contains('Seleccionar')").hide();
      $(this.jqid + " option:contains('No hay glosas para mostrar')").remove();
      $(this.jqid).append(`<option value="" disabled style="color:#000;text-align:center">No hay glosas para mostrar</option>`);
    }else{
      $(this.jqid + " option:contains('No hay glosas para mostrar')").remove();
      $(this.jqid + " option:contains('Seleccionar')").show();
    }
    return listItems;
  }
}

