class  MixinValidation{
   validar(itemId){
    let resultado = false;
    if(Ordeneslst.muestratomada == 'S'){
      try {
	
        let data = Ordeneslst.tests.reduce((acc,test,indice) =>{
	
          if(itemId == test.idexamen){
	console.log(test.idexamen , test.idenvase)
            let d = {"idexamen":test.idexamen,"idtest":test.idtest,"idmuestra":test.idmuestra,"idenvase":test.idenvase};
            return d;
          }
            return acc;
          },{});
          if(!jQuery.isEmptyObject(data)){
            Ordeneslst.testsParaEnviar = [...Ordeneslst.testsParaEnviar,data];
            Ordeneslst.testsParaEnviar = [...new Map(Ordeneslst.testsParaEnviar.map(item => [item["idexamen"], item])).values()];
            resultado = true;
          }else{
            handlerMessageError("No se puede agregar el examen ya que no contiene muestras tomadas");
            resultado = false;
          }
          // Ordeneslst.tests.splice(data, 1);
      } catch (error) {
        return false;
      }
   
      } else if(Ordeneslst.muestratomada == 'N'){
        return true;
      }else{
        handlerMessageError("No se puede agregar el examen ya que no contiene muestras tomadas");
      }
      return resultado;
   }
   validarPaneles(examenes){
    let resultado = false;
    if(Ordeneslst.muestratomada == 'S'){
      try {
        let data = Ordeneslst.tests.reduce((acc,test,indice) =>{
          //verifica que exista
         let itemId = Object.keys(examenes).some(function(k) {
            return examenes[k]["ceIdexamen"] === test.idexamen ;
          })
  
          if(itemId){
            return acc[0] = [...acc, {"idexamen":test.idexamen,"idtest":test.idtest,"idmuestra":test.idmuestra,"idenvase":test.idenvase}];
          }
            return acc;
          },[]);
          if(!jQuery.isEmptyObject(data)){
            Ordeneslst.testsParaEnviar = [...Ordeneslst.testsParaEnviar,...data];
            Ordeneslst.testsParaEnviar = [...new Map(Ordeneslst.testsParaEnviar.map(item => [item["idexamen"], item])).values()];
            // data.forEach(element =>{
            //   Ordeneslst.tests.splice(element, 1);
            // })
  
            return true;
          }else{
            return false;
          }
      } catch (error) {
          return false;
      }
        
      } else if(Ordeneslst.muestratomada == 'N'){
        return true;
      }
      return resultado;
   }
}


class DataItem { 

  ceCodigoexamen;
  ceIdexamen;
  tipoMuestra;
  ceAbreviado;
  data;
  ceDescripcion;
  csecIdseccion;
  csecDescripcion;
  clabIdlaboratorio;
  clabIdCentroSalud;
  clabDescripcion;
  ccdsDescripcion;
  ceEsurgente;
  ldvieIdindicacionesexamen;
  ldvieDescripcion;
  constructor(examen, codigo, tipoMuestra, ceAbreviado, ceDescripcion,
    csecIdseccion,
    csecDescripcion,
    clabIdlaboratorio,
    clabIdCentroSalud,
    clabDescripcion,
    ccdsDescripcion,
    ceEsurgente,
    ldvieIdindicacionesexamen,
    ldvieDescripcion,examenanulado,codestadoexamen,idusuarioanula,derivador) {
    this.ceCodigoexamen = codigo;
    this.ceIdexamen = examen;
    this.tipoMuestra = tipoMuestra;
    this.ceAbreviado = ceAbreviado;
    this.ceDescripcion = ceDescripcion;
    this.csecDescripcion = csecDescripcion;
    this.clabIdlaboratorio = clabIdlaboratorio;
    this.clabIdCentroSalud = clabIdCentroSalud;
    this.clabDescripcion = clabDescripcion;
    this.csecIdseccion = csecIdseccion;
    this.ccdsDescripcion = ccdsDescripcion;
    this.ceEsurgente = ceEsurgente;
    this.ldvieIdindicacionesexamen = ldvieIdindicacionesexamen;
    this.ldvieDescripcion = ldvieDescripcion;
    this.ceActivo = examenanulado;
    this.codestadoexamen = codestadoexamen;
    this.idusuarioanula = idusuarioanula;
    this.derivador = derivador;
    this.data = {
      "ceCodigoexamen": this.ceCodigoexamen,
      "ceIdexamen": this.ceIdexamen,
      "tipoMuestra": this.tipoMuestra,
      "ceAbreviado": this.ceAbreviado,
      "ceDescripcion": this.ceDescripcion,
      "csecIdseccion": this.csecIdseccion,
      "csecDescripcion": this.csecDescripcion,
      "clabIdlaboratorio": this.clabIdlaboratorio,
      "clabIdCentroSalud": this.clabIdCentroSalud,
      "clabDescripcion": this.clabDescripcion,
      "ccdsDescripcion": this.ccdsDescripcion,
      "ceEsurgente": ceEsurgente,
      "ceSinAcento": quitarTildes(this.ceAbreviado),
      "ceTieneUrgentes": ceEsurgente == "N" ? 2 : 1,
      "ldvieIdindicacionesexamen":ldvieIdindicacionesexamen,
      "ldvieDescripcion" :ldvieDescripcion,
      "ceActivo" :examenanulado,
      "codestadoexamen" :codestadoexamen,
      "idusuarioanula" :idusuarioanula,
      "derivador" :derivador,
    };
  }

  get id() {
    return this.ceIdexamen;
  }
  set id(code) {
    this.ceIdexamen = code;
  }
  get text() {
    return this.ceCodigoexamen.concat("-").concat(this.ceAbreviado);
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

  checkHayExamenValido(item) {

    let dataItem = item.dataValue;
    checkExamenEsValidoPaciente(this.idPaciente,dataItem.ceIdexamen);
    return true;
  }

  checkHayTestRepetidos(item) {
    let dataItem = item.dataValue;
    let lstExamenes = new Array();

    this.srcDatos.forEach((ex)=>{ lstExamenes.push(ex.dataValue.ceIdexamen) });
    lstExamenes.push(dataItem.ceIdexamen)
    fnCheckHayTestRepetidos(lstExamenes);
    return true;
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

function testdc(e) {

  console.table(e);

}

class SelectVisualSource extends MixinValidation{

  jqid;
  suscriptores;

  constructor(jqid) {
    super();
    this.jqid = jqid;
    let me = this;
    $(this.jqid).change(this.selectCallback(this.jqid));
    $(this.jqid).dblclick(function() {
      // alert($(jqid.concat(" option:selected")).val());
        let itemId = $(jqid.concat(" option:selected")).val();
      itemId = parseInt(itemId);
      let esvalido = me.validar(itemId);
      if(esvalido){
        if(jqid === '#idDualDataVisualSource'){
          me.suscriptores.forEach((suscriptor) => { suscriptor.add(suscriptor,itemId);});
        }else if(jqid === '#idPanelDualDataVisualSource'){
          console.log(me);
          let item = vs.getSelectedItemById(itemId);
          vt.addItem(item);
          // let item =  me.getSelectedItemById(itemId);
          console.log(item);
        }
      }
    });
    $(this.jqid).keyup(function(e) {
      let keycode = (e.keyCode ? e.keyCode : e.which);
      if(keycode == '13'){
        let itemId = $(jqid.concat(" option:selected")).val();
        itemId = parseInt(itemId);
        let esvalido = me.validar(itemId);
        if(esvalido){
          if(jqid === '#idDualDataVisualSource'){
            me.suscriptores.forEach((suscriptor) => { suscriptor.add(suscriptor,itemId);});
          }else if(jqid === '#idPanelDualDataVisualSource'){
            let item = vs.getSelectedItemById(itemId);
            vt.addItem(item);
          }
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

  validar(itemId){return super.validar(itemId)}
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
    if(item.dataValue.derivador > 0 && $("#registraExamanesDerivados").length && $("#registraExamanesDerivados").val() == "N"){
      handlerMessageError("El usuario no puede agregar el examen derivado");
      return
    }
    this.srcDatos.add(item);// Ver lo de examen
    this.srcDatos.checkHayExamenValido(item);
    //this.srcDatos.checkHayTestRepetidos(item);
    this.visualElement.addItem(item);
  }

  addItems(items) {
    this.visualElement.addItems(items);
    this.srcDatos.addItems(items);
  }

  addAll() {
    let items = this.getSelectedItems();
    this.srcDatos.addAll(items);
  }

  removeItem(item) {
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
    let me = this;
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
      order: [[ 9, 'asc' ],[ 8, 'asc' ]],
        "columnDefs": [
          { orderable: false, targets: [0,1,2,3,4,5,6,7] },
          {
            targets: 0,
            render: function(data, type, row, meta) {
              return `<span title="${data != null ? data : ''}">${largoTexto2(data,'20')}</span>`;
            }
          },
          {
            targets: 1,
            render: function(data, type, row, meta) {
              return `<span title="${data != null ? data : ''}">${largoTexto2(data,'20')}</span>`;
            }
          },
          {
            targets: 2,
            render: function(data, type, row, meta) {
              let title = data;
                        if(data != null && data.length > 6 && !data.includes("-")){
                            title = data.substr(0,6);
                        }else{
                         let tituloFiltrado = data.match(/[^\s-]+/g);
                         title = tituloFiltrado[0];
                        }
                    return '<span class="" title="'+data+'">' +title + '</span>';
            }
          },
          {
            targets: 3,
            render: function(data, type, row, meta) {
              let title = data;
              if(data != null && data.length > 10){
                let tituloFiltrado = data.substr(0,10).split(" ");
                title = tituloFiltrado[0];
              }
              return '<span class="" title="'+data+'">' +title + '</span>';
            }
          },
          {
            targets: 4,
            render: function(data, type, row, meta) {
              let title = data;
              if(data != null && data.length > 21){
                const elemento = data.substr(0,21).split(" ");
                let elementoFiltrado = "";
                if(elemento.length > 1){
                  elementoFiltrado = elemento.map((element,indice) => indice == 0 ? element.substr(0,3).concat("."): element);
                }else{
                  elementoFiltrado = elemento;
                }

                  title = elementoFiltrado.join(" ");
              }
          return '<span class="" title="'+data+'">' +title + '</span>';
            }
          },
          {
            targets: 5,
            render: function(data, type, row, meta) {
                let title = data.split(" ");
                return '<span class="" title="'+data+'">' +title[0] + '</span>';
            }
          },
          {
            targets: 6,
            className: 'dt-left',
            type:"checkbox",
            render: function(data, type, row, meta) {
              let item = row;
              let checked = '';
              if (row.dataValue.ceEsurgente !== undefined && row.dataValue.ceEsurgente === 'S') {
                checked = 'checked';
              }

              let estaDisabled = '';
              if (row.dataValue?.ceActivo !== undefined && row.dataValue?.ceActivo === 'S') {
                checked = 'checked';
                estaDisabled = 'disabled';
              }
            return `<label class="checkbox checkbox-single">
                                  <input data-urgente="${row.id}" type="checkbox" ${checked} value="" class="checkable chck-urgente" ${estaDisabled}/>
                                  <span></span>
                              </label>`;
            }
          },
          {
            targets: 7,
            className: 'dt-left',
            render: function(data, type, row, meta) {
              let item = row;
              let checked = '';
              let estaDisabled = '';
              if (row.dataValue?.ceActivo !== undefined && row.dataValue?.ceActivo === 'S') {
                checked = 'checked';
                estaDisabled = 'disabled';
              }

              if($("#anulaExamenes").length && $("#anulaExamenes").val() == "N"){
                estaDisabled = 'disabled';
              }

              return `<label class="checkbox checkbox-single">
                                    <input data-anulado="${row.id}" type="checkbox" ${checked} value="" class="checkable chck-anulado" ${estaDisabled} />
                                    <span></span>
                                </label>`;
            }
          },
          {
            targets: [8],
            visible:false,
            render: function(data, type, row, meta) {
              return quitarTildes(data);

            }
          },
          {
            targets: [9],
            visible:false,
            render: function(data, type, row, meta) {
              return data;
            }
          },
        ],
        createdRow : function (row, data, index) {
          if(data.dataValue.ceActivo == 'S'){
            $(row).addClass('esInactivoRow');
          }
        },
        "columns": [
          { "data": "dataValue.ceCodigoexamen",width: '13%'},
          { "data": "dataValue.ceAbreviado",width: '18%' },
          { "data": "dataValue.tipoMuestra",width: '12%' },
          { "data": "dataValue.csecDescripcion",width: '14%' },
          { "data": "dataValue.clabDescripcion",width: '15%' },
          { "data": "dataValue.ccdsDescripcion",width: '12%'},
          { "data": "dataValue.ceEsurgente",width: '8%' },
          { "data": "dataValue.ceActivo",width: '8%' },
          { "data": "dataValue.ceSinAcento",width: '0%' },
          { "data": "dataValue.ceTieneUrgentes",width: '0%'  },
        ],
      }
    );

      let test = this.dataTable;
    this.dataTable.on('select',function(e, dt, type, indexes) {
      let filas = dt.rows({ selected: true }).data();
      
      // var data = dt.rows( function ( idx, data, node ) {
      //   console.log($(node).find(`[data-urgente="${filas[0].id}"]`).is(':checked'));
      //   return $(node).find(`[data-urgente="${filas[0].id}"]`).prop('checked');
      //   }).data().toArray();
        
      //   console.log('jusjuesjus',data);
      // let esUrgente = filas[0].dataValue.ceEsurgente;
        // if(!$(`[data-urgente="${filas[0].id}"]`).is(':checked')){
        //   filas[0].dataValue.ceEsurgente = 'S';
        // }else if($(`[data-urgente="${filas[0].id}"]`).is(':checked')){
        //   filas[0].dataValue.ceEsurgente = 'N';
        // }
      
        // if($(`[data-anulado="${filas[0].id}"]`).is(':checked')){
        //   $("#usuarioAnula").modal();
        //   $("#usuarioAnula").find(".modal-title").html(`Anular examen`);
        //   $("#usuarioAnula").find(".modal-body").html(`Una vez anulado el examen no se podra volver a utilizar, &iquest;Desea continuar?`);
        //   $("#usuarioAnula .btn-si-examen").click(function(){
        //     filas[0].dataValue.ceActivo = 'S';
        //     $(`tr#${filas[0].id}`).addClass('esInactivoRow');
        //     $(`[data-anulado="${filas[0].id}"]`).prop('disabled', true);;
        //   });
          
        //   $("#usuarioAnula .btn-no-examen").click(function(){
        //     filas[0].dataValue.ceActivo = 'N';
        //     $(`tr#${filas[0].id}`).removeClass('esInactivoRow');
        //     $(`[data-anulado="${filas[0].id}"]`).removeAttr("disabled");
        //   });
        // }
        // else if($(`[data-urgente="${filas[0].id}"]`).is(':checked')){
        //   filas[0].dataValue.ceActivo = 'N';
        // }

    });

    this.dataTable.on('deselect', function(e, dt, type, indexes) {
      console.table('deselect',indexes);
      let filas = dt.rows(indexes).data();

    });


  
    // this.dataTable.on( 'change', 'input.chck-anulado', function () {
    //   let items = me.getSelectedItem();
    //   let element = $(this);
    //   let tr = $(this).closest("tr");
    //   console.log('aca oo',this);
    //   items.forEach((item) => {
    //     if(tr.attr('id') == item.id && item.dataValue.codestadoexamen != "F"){ 
    //       if($(this).is(':checked')){
    //         $("#usuarioAnula").modal();
    //         $("#usuarioAnula").find(".modal-title").html(`Anular examen`);
    //         $("#usuarioAnula").find(".modal-body").html(`Una vez anulado el examen no se podra volver a utilizar, &iquest;Desea continuar?`);
    //         $("#usuarioAnula .btn-si-examen").click(function(){
    //           item.dataValue.ceActivo = 'S';
    //           $(tr[0]).addClass('esInactivoRow');
    //         });
            
    //         $("#usuarioAnula .btn-no-examen").click(function(){
    //           item.dataValue.ceActivo = 'N';
    //           $(tr[0]).removeClass('esInactivoRow');
    //         });
            
    //       }
    //     }
        
    //   });
    //   console.log('estos son los',tr);
    // });
  
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
          examenes[i].ceEsurgente !== null ?  examenes[i].ceEsurgente : "",
          examenes[i].examenanulado !== null ?  examenes[i].examenanulado : "",
          examenes[i].codestadoexamen,
          examenes[i].idusuarioanula !== null ?  examenes[i].idusuarioanula : "",
          examenes[i].derivador !== null ?  examenes[i].derivador : ""
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


$('.chck-urgente').change(function() {
  console.log('hola');
  if($(this).is(':checked')) 
      $(this).addClass('jox'); 
 else 
     $(this).removeClass('jox')
});


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
    let n = data.dato.length;
    for (i = 0; i < n; i++) {
      let itemex = data.dato[i];
      let datoExamen = new DataItem(itemex.ceIdexamen, itemex.ceCodigoexamen, itemex.cfgMuestras.cmueDescripcionabreviada,
        itemex.ceAbreviado, itemex.ceDescripcion
        , itemex.cfgSecciones.csecIdseccion, itemex.cfgSecciones.csecDescripcion,
        itemex.cfgSecciones.cfgLaboratorios !== null ? itemex.cfgSecciones.cfgLaboratorios.clabIdlaboratorio : "",
        itemex.cfgSecciones.cfgLaboratorios !== null ? itemex.cfgSecciones.cfgLaboratorios.clabIdCentroSalud : "",
        itemex.cfgSecciones.cfgLaboratorios !== null ? itemex.cfgSecciones.cfgLaboratorios.clabDescripcion : "",
        itemex.cfgCentrosdesalud !== null ? itemex.cfgCentrosdesalud.ccdsDescripcion : "",
        itemex.ceEsurgente !== null ? itemex.ceEsurgente : "",
//        itemex.ldvIndicacionesexamenes!== null ? itemex.ldvIndicacionesexamenes.ldvieIdindicacionesexamen : "",
//        itemex.ldvIndicacionesexamenes!== null ? itemex.ldvIndicacionesexamenes.ldvieDescripcion : "",
        itemex.examenanulado !== null ? itemex.examenanulado : "",
        itemex.codestadoexamen !== null ? itemex.codestadoexamen : "",
        itemex.idusuarioanula !== null ? itemex.idusuarioanula : "",
        itemex.derivador !== null ? itemex.derivador : "",
      );
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


function genVSPanelFiller(jqid, vs) {

  return function(data) {
    let listItems = new Array();
    let n = data.length;
    for (i = 0; i < n; i++) {
      let itemPanel = data[i];
      let datoPanel = new PanelDataItem(itemPanel.itemPanel, itemPanel.namePanel, itemPanel.lstExamenes);
      let item = new Item(datoPanel);
      listItems.push(item);
    }

    let ds = new DataSource(listItems);
    let selectVS = new SelectVisualSource(jqid);

    this.srcDatos = ds;
    this.visualElement = selectVS;
    this.visualElement.init(ds);
    vs.loadPanel(ds, selectVS);
    $('#idBtnPanel').prop('disabled', false);
    vs.visualElement.jqid = "#idDualDataVisualSource";
    vs.swapDs2Examen();

    if(listItems.length === 0){
      $(this.jqid + " option:contains('Seleccionar')").hide();
      $(this.jqid + " option:contains('No hay paneles para mostrar')").remove();
      $(this.jqid).append(`<option value="" disabled style="color:#000;text-align:center">No hay paneles para mostrar</option>`);
    }else{
      $(this.jqid + " option:contains('No hay paneles para mostrar')").remove();
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

class VisualSearcher extends MixinValidation {
  dataSearcher;
  jqid;
  visualSource;
  constructor(jqid, visualSource) {
    super();
    let me = this;
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
           console.log(countVal  ,'countVal');
           let esvalido = me.validar(parseInt(countVal.toString()));
            if(esvalido){
              myVisualSource.suscriptores.forEach((suscriptor) => { suscriptor.add(suscriptor,parseInt(countVal.toString())); console.log(suscriptor) });
            }
         }else if(countVal.length > 0 && myVisualSource.visualElement.jqid == '#idPanelDualDataVisualSource'){
            let itemId = myVisualSource.getFirstItemId();
            let item =  myVisualSource.getSelectedItemById(itemId);
            if(item !== undefined){
              let esvalido = me.validarMultiple(item.dataValue.lstExamenes);
              if(esvalido){
                myVisualSource.removeItemById(itemId);
                myVisualSource.suscriptores.forEach((suscriptor) => { 
                  let vt = suscriptor.vt;
                  vt.addItem(item);
                });
              }
            }
          }
          $(jqid).val('');
      }
  });
  
  }

  validar(itemId){return super.validar(itemId)}

  validarMultiple(examenes){
     return super.validarPaneles(examenes);
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


class PanelDataItem {

  itemPanel;
  namePanel;
  lstExamenes;
  data;

  constructor(itemPanel, namePanel, lstExamenes) {
    this.itemPanel = itemPanel;
    this.namePanel = namePanel;
    this.lstExamenes = lstExamenes;
    this.data = { "itemPanel": this.itemPanel, "namePanel": this.namePanel, "lstExamenes": this.lstExamenes };
  }

  get id() {
    return this.itemPanel;
  }
  set id(code) {
    this.itemPanel = code;
  }
  get text() {
    return this.namePanel;
  }
  get data() {
    return this.data;
  }

  get searchText() {
    return this.namePanel;
  }
}

function checkHayExamenValido(item) {

  let dataItem = item.dataValue;
  lstExamenesSolicitados = getListaExameneSolicitados();


  return true;
}

function fnCheckHayTestRepetidos(lstTest) {
  let url = gCte.getctx + "/api/examen/test/repetidos";


  $.ajax({
    "url": url,
    "type": "POST",
    contentType: 'application/json',
    data: JSON.stringify(lstTest),
    dataType:"json",
    success: function(data) {
      if (data.status !== 200) {
        handlerMessageError(data.message);
        return;
      }
      else {

        lstTest = data.dato;
        if (lstTest.length >= 1) {
          const listaConcat = lstTest.map(element=>{
            return `<b>${element.descripcionTest}</b>`
          }).join(',');
          $("#idOrdenYaRegistrada").modal();
          $("#idOrdenYaRegistrada").find(".modal-title").html(`Exámenes contienen el mismo test`);
          $("#idOrdenYaRegistrada").find(".modal-body").html(`Dos o m&aacute;s exámenes seleccionados contienen el mismo test: ${listaConcat} &iquest;Desea continuar?`);
          $('#idOrdenYaRegistrada').on('shown.bs.modal', function() {
            $('.btn-si').focus();
          });
        };
      }

    },
    error: function(msg) {
        handlerMessageError(msg);
      console.log(msg);
    }
  });



}


function checkHayTestRepetidos(lstExamenes) {
  let url = gCte.getctx + "/api/examenes/test/repetidos";
  $.post(url, function(data) {

    if (data.status !== 200) {
      handlerMessageError(data.message);
      return;
    }
    else {

      lstTest = data.dato;
      lst = lstTest.filter((e) => (e.idTest === idTest));
      if (lst.length === 1) {
        $("#idOrdenYaRegistrada").modal();
        $("#idOrdenYaRegistrada").find(".modal-title").html(`Examen repetido en periodo de validez `);
        $("#idOrdenYaRegistrada").find(".modal-body").html(`El examen: ${lst[0].descripcionTest} ya se solicito &iquest;Desea continuar?`);
        // handlerMessageWarning('Paciente ya tiene un examen '.concat(lst[0].descripcionTest).concat(' válido.'));
        $('#idOrdenYaRegistrada').on('shown.bs.modal', function() {
          $('.btn-si').focus();
        });
      };
    }



  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      console.log(err);
    });
}


function checkExamenEsValidoPaciente(idPaciente, idExamen) {

  if (idPaciente === null || idPaciente === undefined) {

    return;
  }

  let url = gCte.getctx + '/api/paciente/'.concat(idPaciente).concat('/examenes/validos');
  $.get(url, function(data) {

    if (data.status !== 200) {
      handlerMessageError(data.message);
      return;
    }
    else {

      lstExamenes = data.dato;
      lst = lstExamenes.filter((e) => (e.ce_IDEXAMEN === idExamen));
      if (lst.length === 1) {
        $("#idOrdenYaRegistrada").modal();
        $("#idOrdenYaRegistrada").find(".modal-title").html(`Examen repetido en periodo de validez `);
        $("#idOrdenYaRegistrada").find(".modal-body").html(`El examen: ${lst[0].ce_ABREVIADO} ya se solicito &iquest;Desea continuar?`);
        // handlerMessageWarning('Paciente ya tiene un examen '.concat(lst[0].ce_ABREVIADO).concat(' válido.'));
        $('#idOrdenYaRegistrada').on('shown.bs.modal', function() {
          $('.btn-si').focus();
        });
      };
    }



  })
    .done(function() {
      console.log("done");
    })
    .fail(function(err) {
      console.log(err);
    });
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

class DualData {

  dtVT;
  dt;
  vt;
  vs;
  jqidDataTarget;
  jqidVisualSource;
  jqidPanelVisualSource;
  jqidSearcher;

  constructor(jqidDataTarget, jqidVisualSource, jqidSearcher, jqidPanelVisualSource) { // "#idDataTarget"

    this.jqidVisualSource = jqidVisualSource;
    this.jqidSearcher = jqidSearcher;
    this.jqidDataTarget = jqidDataTarget;
    this.jqidPanelVisualSource = jqidPanelVisualSource;
    this.dtVT = new DataTableVisualTarget(this.jqidDataTarget);
    this.dt = new DataSource([]);
    this.vt = new VisualTarget(dt, dtVT);
    this.vs = new VisualSource();
    test = genVSFiller(this.jqidVisualSource, this.vs, this.jqidSearcher); //#idDualDataVisualSource" "#idSearcher"
    testPaneles = genVSPanelFiller(this.jqidPanelVisualSource, vs, this.jqidSearcher); //"#idPanelDualDataVisualSource"

    let svcPanel = new ItemPanelService();
    svcPanel.getPaneles(testPaneles);
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

const Ordeneslst = {
  idexamen: [],
  muestratomada: "N",
  tests:[],
  testsParaEnviar:[],
  puedeEditarORden:"S",
  df_IDPROCEDENCIA:0
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


function autoSeleccionar(jqId,id){
  if(id != null){
    $("#"+jqId).focus();
    const typeahead = $("#"+jqId).data().ttTypeahead;
    const menu = typeahead?.menu;
    if(menu?.open() !== 'undefined'){
      //gatilla el evento de abrir el menu de typeahead
      menu?.open();
    
      //busca el texto segun la id enviada
      let textoDato = $("#"+jqId).siblings(".tt-menu").find(`[data-set='${id}']`).text();
      //selecciona el campo
      $("#"+jqId).typeahead('val', textoDato);
    
      const sel = menu?.getTopSelectable();
      if (menu?.isOpen()) {
        menu?.trigger('selectableClicked', sel);
    
      }
    }
    $("#"+jqId).blur();
  }
}

