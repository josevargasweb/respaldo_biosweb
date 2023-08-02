class DataExamen {
    ceIdexamen;
    ceCodigoexamen;
    ceAbreviado;
    ceDescripcion;
    csecIdseccion;
    data;
    constructor(ceIdexamen, ceCodigoexamen, ceAbreviado, ceDescripcion, csecIdseccion){
        this.ceIdexamen = ceIdexamen;
        this.ceCodigoexamen = ceCodigoexamen;
        this.ceAbreviado = ceAbreviado;
        this.ceDescripcion = ceDescripcion;
        this.csecIdseccion = csecIdseccion;
        this.data = {
            "ceIdexamen": this.ceIdexamen,
            "ceCodigoexamen": this.ceCodigoexamen,
            "ceAbreviado": this.ceAbreviado,
            "ceDescripcion": this.ceDescripcion,
            "csecIdseccion": this.csecIdseccion
        };
    }

  get id() {
    return this.ceIdexamen;
  }
  set id(code) {
    this.ceIdexamen = code;
  }
  get text() {
    return this.ceAbreviado;
  }
  get data() {
    return this.data;
  }

  get searchText() {
    return this.ceAbreviado + " " + this.ceDescripcion + " " + this.csecIdseccion;
  }
}

class DataTableExamenes {

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
       /*"columnDefs": [
          {
            targets: 1,
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
                let checked = '';
                if (row.dataValue.cmueIdtipomuestra === muestraPorDefecto || meta.row === 0){
                    checked = 'checked';
                }
                return `<input type="radio" ${checked} name="defaultValue" class="checkable" onclick="selectValorPorDefecto(${row.dataValue.cmueIdtipomuestra});" formExamen"/>`;
            }
          }
        ],*/
        "columns": [
          //{ "data": "dataValue.ceCodigoexamen" },
          { "data": "dataValue.ceAbreviado","width": "100%" }
        ],
        /*initComplete: function () {
            const filas = tablaExamenes.rows().data();
            $("#ModalDatosOrdenDiagnostico").text(filas[0].cd_DESCRIPCION);
            $("#ModalDatosOrdenObs").text(filas[0].df_OBSERVACION);
        },*/
        /*"createdRow": function( row, data, dataIndex ) {
            if (dataIndex === 0){
                muestraPorDefecto = data.dataValue.cmueIdtipomuestra;
            }
        }*/
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

function genVSFillerExamenes(jqid, vs) {
  return function(data) {
    let listItems = new Array();
    let n = data.dato.length;
    for (i = 0; i < n; i++) {
      let itemex = data.dato[i];
      let dataExamen = new DataExamen(itemex.ceIdexamen, itemex.ceCodigoexamen, itemex.ceAbreviado, itemex.ceDescripcion, itemex.cfgSecciones.csecIdseccion
      );
      let item = new Item(dataExamen);
      listItems.push(item);
    }
    let ds = new DataSource(listItems);
    let selectVS = new SelectVisualSource(jqid);

    this.srcDatos = ds;
    this.visualElement = selectVS;
    this.visualElement.init(ds);
    vs.load(ds, selectVS);

    const visualSearcher = new VisualSearcher("#idSearcherExamen", vs, "#selectSeccionExamenesConjuntos");
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


$('#modalExamenConjunto').on('shown.bs.modal', function(e){
  $("#tablaExamenesConjuntos").DataTable().columns.adjust().draw(false);
});  