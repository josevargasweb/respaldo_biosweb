let now = new Date();
now.setHours(0, 00, 0);
var ParametrosApp = { capturaResultadosWindow: 120 };
var servicioSecciones = new CfgSecciones();
var isTableLoaded = false;

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
  boFnLimpiar;
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
    this.boFnLimpiar = this.boLimpiarFormulario();

    var today = new Date();

    $('#bo_fFin').datepicker({
      todayHighlight: true,
      orientation: "bottom left",
      language: 'es',
      endDate: "+Infinity",
      format: 'dd/mm/yyyy',
      defaultDate: today,
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

    $("#bo_tipoDocumento").change(function(){
      $("#bo_nroDocumento").val("");
      if($(this).val() == 1){
        $("#bo_nroDocumento").rut({ formatOn: 'keyup', validateOn: 'blur' });
        $("#bo_nroDocumento").attr("minlength", "11");
        $("#bo_nroDocumento").attr("maxlength", "12");
        $("#bo_nroDocumento").attr("autocomplete", "off");
        $("#bo_nroDocumento").attr("onkeyup", "this.value = this.value.toUpperCase()");

      }else{
        $( "#bo_nroDocumento").unbind( "keyup" );
        $('#bo_nroDocumento').removeAttr('minlength');
        $('#bo_nroDocumento').removeAttr('maxlength');
        $("#bo_nroDocumento").attr("maxlength", "40");
        $('#bo_nroDocumento').removeAttr('autocomplete');
        $('#bo_nroDocumento').removeAttr('onkeyup');
      }
    })


  }

  boGetJqDivId(jqId) {

    let jqIdParts = jqId.split('_');
    return jqIdParts[0].concat('_').concat('div').concat('_').concat(jqIdParts[1]);

  }

  boFnInit(options) {
    options.forEach(opt => { $(opt).hide(); });
  }

  boFnSetGetFormData(me) {
console.log("entre a a getformdata")
    return function(e) {
      console.table(e);
      e.preventDefault();
      if (!me.boFnValida()) {
        alert('Datos invalidos');
        return false;
      }
      me.boFnGetFormData();
    }
  }

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
      let today = new Date();
      let today2 = convertirDateDDMMYYYY(today);
      boFormData.bo_fIni = convertirDateDD_MM_YYYY_HH_mm_SS(dateTimeAddHH(new Date(Date.now()), -1 * ParametrosApp.capturaResultadosWindow), '-', ':');
      boFormData.bo_fFin = convertirDateDD_MM_YYYY_HH_mm_SS(new Date(Date.now()), '-', ':');
      $("#bo_fFin").val(today2)
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
      boFormData.bo_nroDocumento = this.boFormatDocument($("#bo_nroDocumento").val());
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
      boFormData.bo_procedencia = $("#bo_procedencia").val();
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

 
  boFormatDocument(formatDoc){
    if(this.hfields['#bo_div_tipoDocumento'] && this.hfields['#bo_div_nroDocumento']){
      return formatDoc.replace(/[.]/g,'');
    }
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
      if((index === "#bo_nombre" && $(index).val() !== "" && $(index).val().length > 0 && $(index).val().length < 2) || (index === "#bo_apellido" && $(index).val() !== "" && $(index).val().length > 0 && $(index).val().length < 2)){
        return false;
      }
      if (index !== jqid1 && index !== jqid2) {
        $(index).prop("readonly", on);
        $(this.boGetJqDivId(index)).addClass("boDisabled");
        this.jqueryObjectList.forEach((jqObj)=>
        { 
          if (jqObj !== $(jqid1) && jqObj !== $(jqid1))
           {jqObj.val("")} } );
     }
      else {
        $(index).prop("readonly", !on);
        $(this.boGetJqDivId(index)).removeClass("boDisabled");
      }
    }
  }

  boFnFocus(me) {
    return function(e) {
      // console.table(e.currentTarget.id);

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

      data.forEach(proc => {
        let opt = new Option(proc.csDescripcion, proc.csIdservicio);
        $(jqIdSelect).append($(opt));
      });
      let opt = new Option('Todos', '', true, true);
      $(jqIdSelect).append($(opt));
    }
    return fill;
  }

  boGenFillSelectFields(jqIdSelect, descriptionField, idField,existeValue) {
    function fill(data) {
      if (data !== undefined && data !== null) {
        if (data.status !== undefined) {
          data = data.dato;
        }
        data.forEach(proc => {
            let opt = new Option(proc[descriptionField], proc[idField]);
            $(jqIdSelect).append($(opt));
        });

        $('.selectpicker').selectpicker('refresh');
      }
        let opt = new Option('Todos', '', true, true); 
        $(jqIdSelect).append($(opt));

        $('.selectpicker').selectpicker('refresh');
    }
    return fill;
  }
  boGenFillSelectFieldsTipoDoc(jqIdSelect, descriptionField, idField) {
    function fill(data) {
      if (data !== undefined && data !== null) {
        if (data.status !== undefined) {
          data = data.dato;
        }
        data.forEach(proc => {
          if(proc.ldvtdIdtipodocumento === 1 || proc.ldvtdIdtipodocumento === 2 || proc.ldvtdIdtipodocumento === 6){
            let opt = new Option(proc[descriptionField], proc[idField]);
            $(jqIdSelect).append($(opt));
          }
        });
      }
        let opt = new Option('Todos', '', true, true);
        $(jqIdSelect).append($(opt));
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
        $('.selectpicker').selectpicker('refresh');
      }
      if(!$(existeValue).length){
        let opt = new Option('Todos', '', true, true);
        $(jqIdSelect).append($(opt));
      }
    }
    $('.selectpicker').selectpicker('refresh');
    return fill;
  }

 
  boLimpiarFormulario(){
    $("#bo_nOrdenIni").val("");
    $("#bo_nOrdenFin").val("");
    $("#bo_fIni").val("");
    $("#bo_fFin").val("");
    $("#bo_nombre").val("");
    $("#bo_apellido").val("");
    $("#bo_tipoDocumento").val("");
    $("#bo_nroDocumento").val("");
    $("#bo_tipoAtencion").val("");
    $("#bo_localizacion").val("");
    $("#bo_procedencia").val("");
    $("#bo_servicio").val("");
    $("#bo_seccion").val("");
    $("#bo_examen").val("");
    $("#bo_procedencia").val("");
  }

}


class BiolisDatatableOrdenes {

  miBuscadorOrdenes;
  bo_OrdenesDatable;
  hfields;
  inputFields;
  jqueryObjectList;
  doSearch;
  doLimpiarForm;
  selectedRows;
  rowCallback;
  doActions;
  doResponse;
  TableName;
  colFilter;
  constructor(jqidDataTable, buscadorOrdenes, selectCallback,opciones = this.defaultContent()) { 
    this.TableName = jqidDataTable;
    let me = this;
    this.colFilter = null;
    this.miBuscadorOrdenes = buscadorOrdenes;
    this.bo_OrdenesDatable = $(jqidDataTable).DataTable({
      "ajax": {
        "url": gCte.getctx + "/api/common/ordenes",
        "contentType": "application/json",
        "type": "POST",
        "dataSrc": function(jsonData) {return jsonData.dato},
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
      paging: false,
      info: false,
      "autoWidth": false,
      scrollY:calcularContentHeightSolo("divmotor-busqueda"),
      scrollCollapse: true,
      "columns": [
        { "data": "df_NORDEN" }, 
        { "data": "df_NORDEN" },
        { "data": "sdf_FECHAORDEN" },
        { "data": "ldvfet_DESCRIPCION"},
        { "data": "df_OBSERVACION" },
        { "data": "dp_IDPACIENTE" },
        { "data": "ldvtd_DESCRIPCION" },
        { "data": "dp_NRODOCUMENTO"},
        { "data": "dp_NOMBRES" },
        { "data": "dp_NOMBRES" },
        { "data": "dp_PRIMERAPELLIDO" },
        { "data": "dp_SEGUNDOAPELLIDO" },
        { "data": "dp_EMAIL" },
        { "data": "sdp_FNACIMIENTO" },
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
        { "data": "lstSecciones" },
        { "data": "dfe_CODIGOESTADOEXAMEN" },
        { "data": "tienecultivo" },    
        { "data": "","defaultContent": ""}
      ],
      "search": {
        "smart": false
      },
      "columnDefs":opciones,
      "initComplete": function(settings, json) {
        $(jqidDataTable+"_filter").hide();
        if($("#seccionSelect").length){
          let getSec = function() {
            let idSeccion = $("#idSeccion").val()
            $("#seccionSelect").val(idSeccion);
            $("#seccionSelect").selectpicker('render');
            searchSeccion(idSeccion);
          }
          getSecciones(servicioSecciones, getSec);
        }
        let rowFilter = $('<tr class="filterOrden"></tr>').appendTo($(me.bo_OrdenesDatable.table().header()));
        console.log(rowFilter,'rowFilter')
        
        if (me.colFilter !== null) {
            this.api().columns().every(function() {
              let columna = this;
               me.genColFilter(columna, rowFilter, me); //ingreso a los filtros
               
            });
        }
          
        //si existen los elementos se le agregan
          let pendiente = $("#estadoPendiente").length ? "P": "";
          let enProceso = $("#estadoEnProceso").length ? "E": "";
		  let text;
          if( $("#estadoPendiente").length && pendiente == "" && $("#estadoEnProceso").length && enProceso == ""){
            pendiente = "P"
            enProceso = "E"
             text = [pendiente, enProceso].filter(Boolean).join("|");
          }else{
             text = [pendiente, enProceso].filter(Boolean).join("|");
          }


          let cultivo = $("#estadoCultivo").length ? "S":"";
//          me.searchPendiente(text,cultivo)
          isTableLoaded = true;
      },
      select: {
        style: 'single',
  //        className: 'row-selected',
      //  className: 'muestra-blue',
        items: 'row'
      },
    });

    this.rowCallback = selectCallback;
    this.doSearch = this.genDoSearch(this);
    this.doLimpiarForm = this.genDoLimpiarForm(this);
    
    this.bo_OrdenesDatable.on('select', function(e, dt, type, indexes) {
      if (type === 'row') {
        console.log('type',type)
        console.log('dt',dt)
        console.log('indexes',indexes)
        console.log('datos',dt.rows(indexes).data())
        let data = dt.rows(indexes).data();
        selectCallback(data);
      }
    });


    me.bo_OrdenesDatable.on('draw', function() {
  let registros_muestras = me.bo_OrdenesDatable.rows().count();
  let rows_filtroMue = me.bo_OrdenesDatable.rows({ search: 'applied' }).count();
  if($('#dt_registros_orden').length){
    $("#dt_registros_orden").html("Mostrando <b>" + rows_filtroMue + "</b> de <b>" + registros_muestras + "</b> ordenes en total");
  }
  if($('#count_orden_inicio').length){
  $("#count_orden_inicio").text(0);
  }
  if($('#count_orden_fin').length){
    $("#count_orden_fin").text(rows_filtroMue);
  }
  if($('#order-progress').length){
    $("#order-progress").attr("aria-valuenow", 0);
    $("#order-progress").attr("aria-valuemin", 0);
    $("#order-progress").attr("aria-valuemax", rows_filtroMue);
  }
});
    
  }

  genDoSearch(me) {

    return function(e) {
      e.preventDefault();
      if(me.miBuscadorOrdenes.isEmptyData()){
        me.bo_OrdenesDatable.ajax.reload();
        me.setResultado('ok');
      }else{
        handlerMessageError('Se debe completar al menos un campo de búsqueda');
        me.setResultado('error');
      }
    }

  }

  genDoLimpiarForm(me) {
    return function(e) {
      e.preventDefault();
      me.miBuscadorOrdenes.boLimpiarFormulario();
      me.miBuscadorOrdenes.isDataTargetLoaded = false;
      me.bo_OrdenesDatable.ajax.reload();
    }

  }

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

  defaultContent(){
    return [
          {
            targets: [0,26,27,28,29,30], //targets no visibles... se añade el 30 por Marco Caracciolo
            visible:false,
          },
          {
            targets: 3, //Estado
            visible:true,
            render: function (data, type, row){
              const color = estadoOrdenColor[row["ldvfet_DESCRIPCION"]];
              return `<span class="label label-lg font-weight-bold label-inline estadoOrden-${color}" data-id="${row["df_IDFICHAESTADOTM"]}"  >${row["ldvfet_DESCRIPCION"]}</span>`;
            }
            
        }

          ,{
            targets: 8, //Nombre completo
            visible:true,
            render: function (data, type, row){
                return data+" "+row['dp_PRIMERAPELLIDO']+" "+(row['dp_SEGUNDOAPELLIDO'] || "");
            }
            
        },
        {
          targets: 14, //Edad
          visible:true,
          render: function(data, type, full, meta) {
              return data!==null ? calcularEdadTM(data) : "";
          }
          
      },
        {
          targets: 29, //Acciones
          visible:true,
          orderable: false,
          className: "notTMClick",
          render: function(data, type, row) {
            return '';
          }
      }
    ];
  }

/*** Cambios Marco Caracciolo ***/

  setColFilter(colFilter){
      return this.colFilter = colFilter;
  }

  genColFilter(col, rowFilter, me){
      Object.entries(this.colFilter).forEach(entry => {
        //console.log(key, colFilter[key]);
        const [key, value] = entry;
        if (col.index() === value.col){
            switch (value.type){
                case "text":
                    this.genColInputFilter(key, rowFilter, me);
                    break;
                case "select":
                    this.genColSelectFilter(key, rowFilter, col);
                    break;
                case "date":
                    this.genColDateFilter(key, rowFilter, me);
                    break;
                case "limpiar":
                    this.genLimpiar(rowFilter, me);
                    break;
                case "sinfiltro":
                    this.genNoFilter(key, rowFilter);
                    break;
                default:
                    break;
            }
        }
      });
  }
  
  genColInputFilter(colIndex, rowFilter, me) {
    let inputText = $(`<input type="text" class="form-control form-control-sm form-filter datatable-input" data-col-index="` + colIndex + `"/>`);
    $(inputText).appendTo($('<th>').appendTo(rowFilter));
    
    $(`input[data-col-index="` + colIndex + `"]`).on('keyup change clear', function(){
        let columna = $(me.TableName).DataTable().column(colIndex);
        let val = this.value.toUpperCase();
        columna.search(val, true, false, false).draw();
    });
  }
  
  genColSelectFilter(colIndex, rowFilter, columna) {
    let inputSelect = $(`<select class="form-control form-control-sm form-filter p-1 data-col-index="` + colIndex + `""><option value="">TODOS</option></select>`)
        .appendTo($('<th>').appendTo(rowFilter))
        .on('change', function () {
            let val = $.fn.dataTable.util.escapeRegex($(this).val());

            columna.search(val ? '^' + val + '$' : '', true, false).draw();
        });
    columna.data().unique().sort().each( function ( d, j ) {
        inputSelect.append( '<option value="'+d+'">'+d+'</option>' )
    } );
    
  }
  
  genColDateFilter(colIndex, rowFilter, me) {
    let inputText = $(`
                    <div class="input date">
                    <input type="text" class="form-control form-control-sm datatable-input mbfilter_datepicker  " readonly placeholder="" id="mbfilter_datepicker"
                       data-col-index="` + colIndex + `"/>
                    </div>
                    `);
    $(inputText).appendTo($('<th>').appendTo(rowFilter));
    $('.mbfilter_datepicker').datepicker({
      todayHighlight: true,
      orientation: "bottom left",
      language: 'es',
      endDate: "+Infinity",
      format: 'dd/mm/yyyy',
      autoclose: true,
    });
    let today = new Date();
    let fecha = convertirDateDDMMYYYY(today);
    $('.mbfilter_datepicker').val(fecha);
    $('.mbfilter_datepicker').datepicker()
      .on('changeDate', function(e) {
          me.miBuscadorOrdenes.boSearchFechas(e.target.value);
        $(me.TableName).DataTable().ajax.reload();
      });

  }
  
  genNoFilter(colIndex, rowFilter) {
    let inputText = $("<td/>");
    $(inputText).appendTo($('<th>').appendTo(rowFilter));
  }
  
  genLimpiar(rowFilter, me) {
      let inputLimpiar = $(`<span class="symbol symbol-30 symbol-circle">
                            <span id="btnFilterClean" data-toggle="tooltip" title="Limpiar" class="symbol-label bg-hover-blue ">
                                <i id="" class="la la-broom icon-xl  text-blue"></i>
                            </span>
                          </span>`);
      $(inputLimpiar).appendTo($('<th>').appendTo(rowFilter));
      $("#btnFilterClean").click(function(){
          me.limpiarFiltroOrdenes();
      });
      //$("#btnFilterClean").click(this.limpiarFiltroOrdenes());
      
  }  

  limpiarFiltroOrdenes() {
    let colFilter = this.colFilter;
    let tabla = $(this.TableName).DataTable();
    $(".filterOrden th").each(function(){
        $(':input').val("");
        //$(':input').trigger("change")
    });
    for (let i=0; i<colFilter.length; i++){
        tabla
            .column(i)
            .search("", false, false)
            .draw();

    }
    this.searchPendiente("P","N","");
    $("#estadoPendiente").prop("checked", true);
    $("#estadoEnProceso").prop("checked", false);
    $("#estadoCultivo").prop("checked", false);
    
    let today = new Date();
    let fecha = convertirDateDDMMYYYY(today);
    $('.mbfilter_datepicker').val(fecha);
    
    this.miBuscadorOrdenes.setFechabo_fIni(convertirDateDD_MM_YYYY_HH_mm_SS(dateTimeAddHH(new Date(Date.now()), -1 * ParametrosApp.capturaResultadosWindow), '-', ':'))
    this.miBuscadorOrdenes.setFechabo_fFin(convertirDateDD_MM_YYYY_HH_mm_SS(new Date(Date.now()), '-', ':'))
    
    tabla.ajax.reload();
  }
  
  limpiarUserData() {

      $("#txtNombrePaciente").text("");
      $("#txtSexoPaciente").text("");
      $("#txtEdadPaciente").text("");
      $("#txtTipoAtencion").text("");
      $("#txtLocalizacion").text("");
      $("#txtNroOrden").text("");
      $("#txtFechaOrden").text("");
    }
    
  cleanAll() {
    this.limpiarUserData();
    //this.limpiarTableResultadosExamenesOrden();
    //this.limpiarTableInfoExamenesOrden();
  }
  
  searchKey(e) {
    let columna = $(this.jqidDataTable).DataTable().column(this.dataset["colIndex"]);
    console.log('searchKey ' + this.dataset["colIndex"]);
        columna.search(e.target.value.toUpperCase(), true, false, false).draw();
    //limpiarUserData();
        //this.cleanAll();
  }
  
    searchValue(e) {
      let columna = $(this.jqidDataTable).DataTable().column(this.dataset["colIndex"]);
      console.log('searchValue ' + this.dataset["colIndex"]);
      if (this.dataset["colIndex"] === "8") {
        columna = $('#ordenesDatatable').DataTable().column(12);
        let value = this.value;
        columna.search(value, true, false, false).draw();
      }
      else {
        columna.search(this.value, true, false, false).draw();
      }
      //limpiarUserData();
      //this.cleanAll();

    }

  setOptionSelCol(colIndex) {

    
    return '<option value="">TODOS</option>';
  }  
  

    
    /* end cambios Marco Caracciolo*/

  getTableName(){
    return this.TableName;
 }

  getResultado(){
    return this.doResponse;
  }

  setResultado(doResponse){
    return this.doResponse = doResponse;
  }
 
  //filtros checkbox
  searchPendiente(pendiente,cultivo,testsEnProceso, id = this) {
    $(id.getTableName()).DataTable().column(27).search(pendiente, true, false, false)
                    .column(28).search(cultivo, true, false, false)
                    .column(30).search(testsEnProceso, true, false, false).draw();
  }

  positionOrden(nOrden){
    let rows = this.bo_OrdenesDatable.rows({ search: 'applied' }).data();
    let n = rows.length;
    let j = 0;
    for (let i = 0; i < n; i++) {
      if (rows[i].df_NORDEN === nOrden) {
        j = i;
        break;
      }
    }
    if (j >= 0 && j < n) {
      $("#order-progress").attr("aria-valuenow",j + 1); 
      let valuenow =  $("#order-progress").attr("aria-valuenow");
      let valuemax = $("#order-progress").attr("aria-valuemax"); 
      let total = (valuenow * 100) / valuemax;
      console.log(total,"total");
      $("#count_orden_inicio").text(valuenow);
      $("#order-progress").css('width', total+"%");
    }
  }

  getRows(){
    return this.bo_OrdenesDatable.rows({ search: 'applied' }).data();

  }


}


//export {BiolisBuscadorOrdenes}; 



