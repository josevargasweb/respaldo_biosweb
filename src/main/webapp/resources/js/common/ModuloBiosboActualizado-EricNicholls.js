let now = new Date();
now.setHours(0, 00, 0);
var ParametrosApp = { capturaResultadosWindow: 120 };

const estadoOrdenColor = new Array();
estadoOrdenColor['ATENDIDO'] = 'atendido';
estadoOrdenColor['ESPERA'] = 'espera';
estadoOrdenColor['BLOQUEADA'] = 'bloqueada';

class BiolisBuscadorOrdenes {

  fields;
  hfields;
  inputFields;
  jqueryObjectList;
  boFnBuscarOrden;
  listaServicios;
  listaTiposAtencion;
  listaProcedencias;
  listaTiposDocumento;
  listaLocalizaciones;
  listaSecciones;
  listaExamenes;
  isDataTargetLoaded;
  isEmptyData;

  constructor(options) {

    this.boFnGetFormData = this.boFnGetFormData.bind(this);
    this.isDataTargetLoaded = false;
    this.fields = new Array();
    this.hfields = new Array();
    this.inputFields = new Array();
    this.jqueryObjectList = new Array();

    this.inputFields["#bo_nOrdenIni"] = "#bo_nOrdenIni";
    this.inputFields["#bo_nOrdenFin"] = "#bo_nOrdenFin";
    this.inputFields["#bo_fIni"] = "#bo_fIni";
    this.inputFields["#bo_fFin"] = "#bo_fFin";
    this.inputFields["#bo_nombre"] = "#bo_nombre";
    this.inputFields["#bo_apellido"] = "#bo_apellido";
    this.inputFields["#bo_tipoDocumento"] = "#bo_tipoDocumento";
    this.inputFields["#bo_nroDocumento"] = "#bo_nroDocumento";
    this.inputFields["#bo_tipoAtencion"] = "#bo_tipoAtencion";
    this.inputFields["#bo_localizacion"] = "#bo_localizacion";
    this.inputFields["#bo_procedencia"] = "#bo_procedencia";
    this.inputFields["#bo_servicio"] = "#bo_servicio";
    this.inputFields["#bo_seccion"] = "#bo_seccion";
    this.inputFields["#bo_examen"] = "#bo_examen";
    this.inputFields["#bo_nroPasaporte"] = "#bo_nroDocumento";
    this.inputFields["#bo_nroRunMadre"] = "#bo_nroRunMadre";
    this.inputFields["#bo_fechaIngreso"] = "#bo_fechaIngreso";


    this.fields[0] = "#bo_nOrdenIni";
    this.fields[1] = "#bo_nOrdenFin";
    this.fields[2] = "#bo_fIni";
    this.fields[3] = "#bo_fFin";
    this.fields[4] = "#bo_nombre";
    this.fields[5] = "#bo_apellido";
    this.fields[6] = "#bo_tipoDocumento";
    this.fields[7] = "#bo_nroDocumento";
    this.fields[8] = "#bo_tipoAtencion";
    this.fields[9] = "#bo_localizacion";
    this.fields[10] = "#bo_procedencia";
    this.fields[11] = "#bo_servicio";
    this.fields[12] = "#bo_seccion";
    this.fields[13] = "#bo_examen";
    this.fields[14] = "#bo_nroDocumento";
    this.fields[15] = "#bo_nroRunMadre";
    this.fields[16] = "#bo_fechaIngreso";

    this.hfields["#bo_div_nOrdenIni"] = false;
    this.hfields["#bo_div_nOrdenFin"] = false;
    this.hfields["#bo_div_fIni"] = false;
    this.hfields["#bo_div_fFin"] = false;
    this.hfields["#bo_div_nombre"] = false;
    this.hfields["#bo_div_apellforo"] = false;
    this.hfields["#bo_div_tipoDocumento"] = false;
    this.hfields["#bo_div_nroDocumento"] = false;
    this.hfields["#bo_div_tipoAtencion"] = false;
    this.hfields["#bo_div_localizacion"] = false;
    this.hfields["#bo_div_procedencia"] = false;
    this.hfields["#bo_div_servicio"] = false;
    this.hfields["#bo_div_seccion"] = false;
    this.hfields["#bo_div_examen"] = false;

    this.hfields["#bo_nroPasaporte"] = false;
    this.hfields["#bo_nroRunMadre"] = false;
    this.hfields["#bo_fechaIngreso"] = false;

    this.jqueryObjectList["#bo_nOrdenIni"] = $("#bo_nOrdenIni");
    this.jqueryObjectList["#bo_nOrdenFin"] = $("#bo_nOrdenFin");
    this.jqueryObjectList["#bo_fIni"] = $("#bo_fIni");
    this.jqueryObjectList["#bo_fFin"] = $("#bo_fFin");
    this.jqueryObjectList["#bo_nombre"] = $("#bo_nombre");
    this.jqueryObjectList["#bo_apellido"] = $("#bo_apellido");
    this.jqueryObjectList["#bo_tipoDocumento"] = $("#bo_tipoDocumento");
    this.jqueryObjectList["#bo_nroDocumento"] = $("#bo_nroDocumento");
    this.jqueryObjectList["#bo_tipoAtencion"] = $("#bo_tipoAtencion");
    this.jqueryObjectList["#bo_localizacion"] = $("#bo_localizacion");
    this.jqueryObjectList["#bo_procedencia"] = $("#bo_procedencia");
    this.jqueryObjectList["#bo_servicio"] = $("#bo_servicio");
    this.jqueryObjectList["#bo_seccion"] = $("#bo_seccion");
    this.jqueryObjectList["#bo_examen"] = $("#bo_examen");
    this.jqueryObjectList["#bo_nroPasaporte"] = $("#bo_nroDocumento");
    this.jqueryObjectList["#bo_nroRunMadre"] = $("#bo_nroRunMadre");
    this.jqueryObjectList["#bo_fechaIngreso"] = $("#bo_fechaIngreso");


    options.forEach(opt => { 
        $(opt).css("display", "flex"); 
        $(opt).removeAttr("hidden");
        this.hfields[opt] = true; 
    });
    this.boFnSetFocusHandler(this);
    this.boFnBuscarOrden = this.boFnSetGetFormData(this);
    this.boFnFillSelect();
    this.isEmptyData = this.boFnValidarFiltros(options);

    $('#bo_fFin').datepicker({
      todayHighlight: true,
      orientation: "bottom left",
      language: 'es',
      endDate: "+Infinity",
      format: 'dd/mm/yyyy',
      autoclose: true,
    });

    $('#bo_fIni').datepicker({
      todayHighlight: true,
      orientation: "bottom left",
      language: 'es',
      endDate: "+Infinity",
      format: 'dd/mm/yyyy',
      autoclose: true,
    });

   


  };

  boGetJqDivId(jqId) {

    let jqIdParts = jqId.split('_');
    return jqIdParts[0].concat('_').concat('div').concat('_').concat(jqIdParts[1]);

  };

  boFnInit(options) {
    options.forEach(opt => { $(opt).hide(); });
  };

  boFnSetGetFormData(me) {

    return function(e) {
      console.table(e);
      e.preventDefault();
      if (!me.boFnValida()) {
        alert('Datos invalidos');
        return false;
      }
      me.boFnGetFormData();
    }
  };

  boFnValida() {
    try {
      let formValidator = new FormValidator();
      let nroOrdenValidator = new NroOrdenValidator("#bo_nOrdenIni", 0, 0);
      formValidator.add(nroOrdenValidator);
      let fechaIniMenorFinValidator = new FechaIniMenorFinValidator("#bo_fIni", "#bo_fFin");
      formValidator.add(fechaIniMenorFinValidator);
      let parmOk = formValidator.validate();
      if (parmOk === false) {
        handlerMessageError(formValidator.message);
        return false;
      }
    }
    catch (error) {
      handlerMessageError(error);
      return false;
    }
    return true;
  }

  boFnGetFormData() {
    let boFormData = new Object();
    if (!this.isDataTargetLoaded) {

      boFormData.bo_fIni = convertirDateDD_MM_YYYY_HH_mm_SS(dateTimeAddHH(new Date(Date.now()), -1 * ParametrosApp.capturaResultadosWindow), '-', ':');
      boFormData.bo_fFin = convertirDateDD_MM_YYYY_HH_mm_SS(new Date(Date.now()), '-', ':');
      boFormData.bo_nOrdenIni = null;
      boFormData.bo_nOrdenFin = null;
      boFormData.bo_nombre = "";
      boFormData.bo_apellido = "";
      this.isDataTargetLoaded = true;
    }
    else {
      
      
      
      if ( this.hfields["#bo_div_nOrdenIni"] === true && this.hfields["#bo_div_nOrdenFin"] === false)
      {
        boFormData.bo_nOrdenIni = $("#bo_nOrdenIni").val();
        boFormData.bo_nOrdenFin = $("#bo_nOrdenIni").val();
      }
      else if ( this.hfields["#bo_div_nOrdenIni"] === false && this.hfields["#bo_div_nOrdenFin"] === true)
      {
        boFormData.bo_nOrdenIni = $("#bo_nOrdenFin").val();
        boFormData.bo_nOrdenFin = $("#bo_nOrdenFin").val();
      }
      else if(this.hfields["#bo_div_nOrdenIni"] === true && this.hfields["#bo_div_nOrdenFin"] === true) {
        boFormData.bo_nOrdenIni = $("#bo_nOrdenIni").val();
        boFormData.bo_nOrdenFin = $("#bo_nOrdenFin").val();
      }
      
      boFormData.bo_fIni = $("#bo_fIni").val();
      boFormData.bo_fFin = $("#bo_fFin").val();
      boFormData.bo_nombre = $("#bo_nombre").val();
      boFormData.bo_apellido = $("#bo_apellido").val();
      boFormData.bo_tipoDocumento = $("#bo_tipoDocumento").val();
      boFormData.bo_nroDocumento = $("#bo_nroDocumento").val();
      boFormData.bo_tipoAtencion = $("#bo_tipoAtencion").val();
      boFormData.bo_localizacion = $("#bo_localizacion").val();
      boFormData.bo_procedencia = $("#bo_procedencia").val();
      boFormData.bo_servicio = $("#bo_servicio").val();
      boFormData.bo_seccion = $("#bo_seccion").val();
      boFormData.bo_examen = $("#bo_examen").val();
    }
    
    // Revisar
    if(this.hfields["#bo_div_procedencia"] === true && $('#procedenciaUsuario').length && $("#procedenciaUsuario").val() != 0){
      console.log($("#procedenciaUsuario").val(),'procedenciaUsuario')
      boFormData.bo_procedencia = $("#procedenciaUsuario").val();
    }
    return JSON.stringify(boFormData);

  }

  boFnChange(e) {
    console.table(e);
  }

  boFnReglaExclusionFiltroNroOrden(on) {
    this.boFnReglaExclusionFiltro(on, "#bo_nOrdenIni", "#bo_nOrdenFin");
  }

  boFnReglaExclusionFiltroNroDocumento(on) {
    this.boFnReglaExclusionFiltro(on, "#bo_tipoDocumento", "#bo_nroDocumento");
  }

  boFnValidarFiltros(me){
    return function(e) {
      let isEmpty = false;
      me.forEach(opt => { 
        let el = $(opt).find('input');
       if(el.is('input') && el.val() != ""){
         isEmpty = true;
        }  
      });
        return isEmpty;
    }
  }

  boFnReglaExclusionFiltro(on, jqid1, jqid2) {
    for (let index in this.inputFields) {
      console.log(index);
      if((index === "#bo_nombre" && $(index).val() !== "" && $(index).val().length > 0 && $(index).val().length < 2) || (index === "#bo_apellido" && $(index).val() !== "" && $(index).val().length > 0 && $(index).val().length < 2)){
        return false;
      }
      if (index !== jqid1 && index !== jqid2) {
        $(index).prop("readonly", on);
        $(this.boGetJqDivId(index)).addClass("boDisabled");
        this.jqueryObjectList.forEach((jqObj)=>
        { 
          if (jqObj !== $(jqid1) && jqObj !== $(jqid1))
           {jqObj.val("")}; } );
     }
      else {
        $(index).prop("readonly", !on);
        $(this.boGetJqDivId(index)).removeClass("boDisabled");
      }
    }
  };

  boFnFocus(me) {
    return function(e) {
      console.table(e.currentTarget.id);

      let jqId = "#" + e.currentTarget.id;

      if (jqId === "#bo_nOrdenIni" || jqId === "#bo_nOrdenFin") {
        me.boFnReglaExclusionFiltroNroOrden(true);
        
        
      }
      else if (jqId === "#bo_tipoDocumento" || jqId === "#bo_nroDocumento") {
        me.boFnReglaExclusionFiltroNroDocumento(true);
      }
      else {
        me.boFnReglaExclusionFiltroNroOrden(false);
        me.boFnReglaExclusionFiltroNroDocumento(false);
      }
    }
  }

  boFnSetFocusHandler(me) {
    for (let jqObj in this.jqueryObjectList) {
      this.jqueryObjectList[jqObj].focus(this.boFnFocus(me));
    }
  }

  boFnFillSelect() {

    this.listaServicios = new CfgServicios();
    this.listaServicios.getServicios(this.boGenFillSelect("#bo_servicio"));
    this.listaTiposAtencion = new CfgTipoAtencion();
    this.listaTiposAtencion.getTiposAtencion(this.boGenFillSelectFields("#bo_tipoAtencion", "ctaDescripcion", "ctaIdtipoatencion"));
    this.listaProcedencias = new CfgProcedencia();
    this.listaProcedencias.getProcedencias(this.boGenFillSelectFieldsProcedencia("#bo_procedencia", "cpDescripcion", "cpIdprocedencia","#procedenciaUsuario"));
    this.listaSecciones = new CfgSecciones();
    this.listaSecciones.getSecciones(this.boGenFillSelectFields("#bo_seccion", "csecCodigo", "csecIdseccion"));
    this.listaTiposDocumento = new CfgTipoDocumento();
    this.listaTiposDocumento.getTiposDocumento(this.boGenFillSelectFieldsTipoDoc("#bo_tipoDocumento", "ldvtdDescripcion", "ldvtdIdtipodocumento"));
    this.listaLocalizaciones = new CfgLocalizacion();
    this.listaExamenes = new CfgExamen();
    this.listaExamenes.getExamenes(this.boGenFillSelectFields("#bo_examen", "ceAbreviado", "ceCodigoexamen"));
  }

  boGenFillSelect(jqIdSelect) {
    function fill(data) {
      let opt = new Option('Todos', '', true, true);
      $(jqIdSelect).append($(opt));
      data.forEach(proc => {
        let opt = new Option(proc.csDescripcion, proc.csIdservicio);
        $(jqIdSelect).append($(opt));
      });
    }
    return fill;
  }

  boGenFillSelectFields(jqIdSelect, descriptionField, idField,existeValue) {
    function fill(data) {
      if (data !== undefined && data !== null) {
        if (data.status !== undefined) {
          data = data.dato;
        }
        let opt = new Option('Todos', '', true, true); 
        $(jqIdSelect).append($(opt));
        data.forEach(proc => {
            let opt = new Option(proc[descriptionField], proc[idField]);
            $(jqIdSelect).append($(opt));
        });
      }
      
    }
    return fill;
  }
  boGenFillSelectFieldsTipoDoc(jqIdSelect, descriptionField, idField) {
    function fill(data) {
      if (data !== undefined && data !== null) {
        if (data.status !== undefined) {
          data = data.dato;
        }
        let opt = new Option('Todos', '', true, true);
        $(jqIdSelect).append($(opt));
        data.forEach(proc => {
          if(proc.ldvtdIdtipodocumento === 1 || proc.ldvtdIdtipodocumento === 2 || proc.ldvtdIdtipodocumento === 6){
            let opt = new Option(proc[descriptionField], proc[idField]);
            $(jqIdSelect).append($(opt));
          }
        });
      }
    }
    return fill;
  }

  boGenFillSelectFieldsProcedencia(jqIdSelect, descriptionField, idField,existeValue) {
    function fill(data) {
      if (data !== undefined && data !== null) {
        if (data.status !== undefined) {
          data = data.dato;
        }
        data.forEach(proc => {
          if($(existeValue).length && $(existeValue).val() != 0){
            if(proc[idField] == $(existeValue).val()){
              let opt = new Option(proc[descriptionField], proc[idField],true, true);
              $(jqIdSelect).append($(opt));
            }
          }else{
            let opt = new Option(proc[descriptionField], proc[idField]);
            $(jqIdSelect).append($(opt));
          }
         
        });
      }
      if(!$(existeValue).length){
        let opt = new Option('Todos', '', true, true);
        $(jqIdSelect).append($(opt));
      }
    }
    return fill;
  }


}


class BiolisDatatableOrdenes {

  miBuscadorOrdenes;
  bo_OrdenesDatable;
  hfields;
  inputFields;
  jqueryObjectList;
  doSearch;
  selectedRows;
  rowCallback;

  constructor(jqidDataTable, buscadorOrdenes, selectCallback) {
    this.miBuscadorOrdenes = buscadorOrdenes;
    this.bo_OrdenesDatable = $(jqidDataTable).DataTable({
      "ajax": {
        "url": gCte.getctx + "/api/common/ordenes",
        "contentType": "application/json",
        "type": "POST",
        "dataSrc": function(jsonData) { return jsonData.dato},
        "processData": false,
        "data": this.miBuscadorOrdenes.boFnGetFormData,
      },
      "language": {
        "info": "Mostrando _TOTAL_ órdenes ",
        "infoFiltered": "filtradas de un total de _MAX_ ",
        "emptyTable": "No hay datos disponibles",
        "loadingRecords": "Por favor espere - cargando ...",
        "zeroRecords": "No se encontraron datos",
      },
      scrollX: true,
      searching: false,
      paging: false,
      info: false,
      "autoWidth": false,
      scrollY:200,
      scrollCollapse: true,
      "columns": [
        { "data": "df_NORDEN"},
        { "data": "df_NORDEN"},
        { "data": "sdf_FECHAORDEN" },
        { "data": "ldvfet_DESCRIPCION"},
        { "data": "df_OBSERVACION" },
        { "data": "dp_IDPACIENTE" },
        { "data": "ldvtd_DESCRIPCION" },
        { "data": "dp_NRODOCUMENTO" },
        { "data": "dp_NOMBRES" },
        { "data": "dp_NOMBRES" },
        { "data": "dp_PRIMERAPELLIDO" },
        { "data": "dp_SEGUNDOAPELLIDO" },
        { "data": "dp_EMAIL" },
        { "data": "sdp_FNACIMIENTO" },
        { "data": "ldvs_DESCRIPCION" },
        { "data": "dp_OBSERVACION" },
        { "data": "cta_DESCRIPCION" },
        { "data": "cp_DESCRIPCION" },
        { "data": "cc_ABREVIADO" },
        { "data": "df_IDPREVISION" },
        { "data": "df_IDLOCALIZACION" },
        { "data": "cd_DESCRIPCION" },
        { "data": "df_IDCENTRODESALUD" },
        { "data": "df_IDPRIORIDADATENCION" },
        { "data": "cs_DESCRIPCION" },      
      ],
            "columnDefs":
        [
          {
            targets: 3, //Estado
            visible:true,
            render: function (data, type, row){
              const color = estadoOrdenColor[row["ldvfet_DESCRIPCION"]];
              return `<span class="label label-lg font-weight-bold label-inline estadoOrden-${color}" data-id="${row["df_IDFICHAESTADOTM"]}"  >${row["ldvfet_DESCRIPCION"]}</span>`;
            }
            
        },
          {
            targets: 7, //numero documento
            visible:true,
            render: function (data, type, row){
              if(row["ldvtd_DESCRIPCION"] == "RUN"){
                const nDocumento = cambiarDatoRut(row["dp_NRODOCUMENTO"]);
                return nDocumento;
              }else{
                return row["dp_NRODOCUMENTO"];
              }
            }
        }
          ,{
            targets: 8, //Nombre completo
            visible:true,
            render: function (data, type, row){
                return data+" "+row['dp_PRIMERAPELLIDO']+" "+(row['dp_SEGUNDOAPELLIDO'] || "");
            }
            
        }],
      fixedColumns: true,
      select: {
        style: 'single',
//        className: 'row-selected',
        items: 'row'
      },
    });
    

    this.rowCallback = selectCallback;
    this.doSearch = this.genDoSearch(this);
    
    this.bo_OrdenesDatable.on('select', function(e, dt, type, indexes) {
      if (type === 'row') {
        let data = dt.rows(indexes).data();
        selectCallback(data);
      }
    });

    
//    this.onSelect = this.setOnSelect(this, selectCallback);
  };

  genDoSearch(me) {

    return function(e) {
      e.preventDefault();
      if(me.miBuscadorOrdenes.isEmptyData()){
        me.bo_OrdenesDatable.ajax.reload();
      }else{
        handlerMessageError('Se debe completar al menos un campo de búsqueda');
      }
    }

  };


/*
  setOnSelect(me, callback) {
    me.bo_OrdenesDatable.on('select', function(e, dt, type, indexes) {
      if (type === 'row') {
        let data = dt.rows(indexes).data();
        callback(data);
      }
    });
  }
*/



  getOrdenesSeleccionadas() {
    const ordenesSeleccionadas = this.bo_OrdenesDatable.rows({ selected: true }).data();

    let ordenesId = new Array();

    if (ordenesSeleccionadas === undefined || ordenesSeleccionadas === null) {
      handlerMessageError('Revisar selección de órdenes.');
      return false;
    }
    let n = ordenesSeleccionadas.count();

    if (n === 0) {
      handlerMessageError('Verifique si ha seleccionado órdenes.');
      return false;
    }

    for (let i = 0; i < n; i++) {
      ordenesId.push(ordenesSeleccionadas[i].df_NORDEN);
    }
    return ordenesId;
  }



}





//export {BiolisBuscadorOrdenes}; 