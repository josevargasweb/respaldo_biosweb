class DataMuestras {
    cmueIdtipomuestra;
    cmuePrefijotipomuestra;
    cmueDescripcionabreviada;
    data;
    constructor(cmueIdtipomuestra, cmuePrefijotipomuestra, cmueDescripcionabreviada){
        this.cmueIdtipomuestra = cmueIdtipomuestra;
        this.cmuePrefijotipomuestra = cmuePrefijotipomuestra;
        this.cmueDescripcionabreviada = cmueDescripcionabreviada;
        this.data = {
            "cmueIdtipomuestra": this.cmueIdtipomuestra,
            "cmuePrefijotipomuestra": this.cmuePrefijotipomuestra,
            "cmueDescripcionabreviada": this.cmueDescripcionabreviada
        };
    }

  get id() {
    return this.cmueIdtipomuestra;
  }
  set id(code) {
    this.cmueIdtipomuestra = code;
  }
  get text() {
    return this.cmueDescripcionabreviada;
  }
  get data() {
    return this.data;
  }

  get searchText() {
    return this.cmuePrefijotipomuestra + " " + this.cmueDescripcionabreviada;
  }
}

class DataTableMuestras {

  jqid;
  dataTable;

  constructor(jqid) {
    this.jqid = jqid;
  }

  init(listItems) {

    this.dataTable = $(this.jqid).DataTable(
      {
        "rowId": "dataValue.cmueIdtipomuestra",
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
          "sProcessing":     "Procesando...",
          "sZeroRecords":    "No se encontraron muestras",
          "sEmptyTable":     "No se encontraron muestras seleccionadas",
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
            targets: 1,
            className: 'dt-left',
            orderable: false,
            render: function(data, type, row, meta) {
                let checked = '';
                if (row.dataValue.cmueIdtipomuestra === muestraPorDefecto){
                    checked = 'checked';
                }
                let input;
                input = `<input type="radio" ${checked} name="defaultValue" class="checkable" onclick="selectValorPorDefecto(${row.dataValue.cmueIdtipomuestra}, '${row.dataValue.cmueDescripcionabreviada}');" formExamen"/>`;
            	checked ='';
            	return input;
            }
          }
        ],
        "columns": [
          { "data": "dataValue.cmueDescripcionabreviada","width": "60%" },
          { "": "","width": "40%" }
        ],
        /*initComplete: function () {
            const filas = tablaExamenes.rows().data();
            $("#ModalDatosOrdenDiagnostico").text(filas[0].cd_DESCRIPCION);
            $("#ModalDatosOrdenObs").text(filas[0].df_OBSERVACION);
        },*/
        "createdRow": function( row, data, dataIndex ) {
            if (dataIndex === 0){
               // muestraPorDefecto = data.dataValue.cmueIdtipomuestra;
               // $("#muestraExamen").val(data.dataValue.cmueDescripcionabreviada);
            }
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

function genVSFillerMuestras(jqid, vs) {
  return function(data) {
    let listItems = new Array();
    let n = data.length;
    for (i = 0; i < n; i++) {
      let itemex = data[i];
      let datoMuestra = new DataMuestras(itemex.cmueIdtipomuestra, itemex.cmuePrefijotipomuestra, itemex.cmueDescripcionabreviada
      );
      let item = new Item(datoMuestra);
      listItems.push(item);
    }
    let ds = new DataSource(listItems);
    let selectVS = new SelectVisualSource(jqid);

    this.srcDatos = ds;
    this.visualElement = selectVS;
    this.visualElement.init(ds);
    vs.load(ds, selectVS);

    const visualSearcher = new VisualSearcher("#idSearcherMuestra", vs, "#selectSeccionMuestra");
    //$('#idBtnExamen').prop('disabled', false);
    if(listItems.length === 0){
      $(this.jqid + " option:contains('Seleccionar')").hide();
      $(this.jqid + " option:contains('No hay muestras para mostrar')").remove();
      $(this.jqid).append(`<option value="" disabled style="color:#000;text-align:center">No hay métodos para mostrar</option>`);
    }else{
      $(this.jqid + " option:contains('No hay muestras para mostrar')").remove();
      $(this.jqid + " option:contains('Seleccionar')").show();
    }
    return listItems;
  }
}