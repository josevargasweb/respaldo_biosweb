/*
 * 
 * Este BiosBO está creado exclusivamente para captura de resultados de microbiología
 * Marco Caracciolo 05/04/2023
 */

let now = new Date();
now.setHours(0, 00, 0);
var ParametrosApp = { capturaResultadosWindow: 120 };
var targetUrgentes = 11;
var targetPendiente = 12;
var targetEnProceso = 13;
var targetCultivo = 14;

var tipoAtencionColorMap = new Array();
tipoAtencionColorMap['AMBULATORIO'] = 'ambulatorio';
tipoAtencionColorMap['HOSPITALIZADO'] = 'hospitalizado';
tipoAtencionColorMap['URGENCIA'] = 'urgencia';
tipoAtencionColorMap['NO SE ESPECIFICA'] = 'light';
  
class BiolisBuscadorOrdenes {

  fields;
  hfields;
  inputFields;
  jqueryObjectList;
  boFnBuscarOrden;
  //boFnLimpiar;
  listaServicios;
  listaTiposAtencion;
  listaProcedencias;
  listaTiposDocumento;
  listaLocalizaciones;
  listaSecciones;
  listaExamenes;
  isDataTargetLoaded;
  isDataTargetLoaded2;
  isEmptyData;

  constructor(options) {

    this.boFnGetFormData = this.boFnGetFormData.bind(this);
    this.isDataTargetLoaded = false;
    this.isDataTargetLoaded2 = false;
    
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

    this.bo_fIni;
    this.bo_fFin;
    this.bo_nOrdenIni;
    this.bo_nOrdenFin;
    this.bo_nombre;
    this.bo_apellido;
    this.bo_tipoDocumento;
    this.bo_nroDocumento;
    this.bo_tipoAtencion;
    this.bo_localizacion;
    this.bo_procedencia;
    this.bo_servicio;
    this.bo_seccion;
    this.bo_examen;
    //this.boSetFechas();


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

  boSetFechas(){
    if (!this.isDataTargetLoaded2) {
      this.setFechabo_fIni(convertirDateDD_MM_YYYY_HH_mm_SS(dateTimeAddHH(new Date(Date.now()), -1 * ParametrosApp.capturaResultadosWindow), '-', ':'))
      this.setFechabo_fFin(convertirDateDD_MM_YYYY_HH_mm_SS(new Date(Date.now()), '-', ':'))
      this.setbo_nOrdenIni(null);
      this.setbo_nOrdenFin(null);
      this.setbo_nombre("");
      this.setbo_apellido("");
      this.isDataTargetLoaded2 = true;
    }else{
      this.setFechabo_fIni($("#bo_fIni").val())
      this.setFechabo_fFin($("#bo_fFin").val())

      if ( this.hfields["#bo_div_nOrdenIni"] === true && this.hfields["#bo_div_nOrdenFin"] === false)
      {
        this.setbo_nOrdenIni($("#bo_nOrdenIni").val());
        this.setbo_nOrdenFin($("#bo_nOrdenIni").val());
      }
      else if ( this.hfields["#bo_div_nOrdenIni"] === false && this.hfields["#bo_div_nOrdenFin"] === true)
      {
        this.setbo_nOrdenIni($("#bo_nOrdenFin").val());
        this.setbo_nOrdenFin($("#bo_nOrdenFin").val());
      }
      else if(this.hfields["#bo_div_nOrdenIni"] === true && this.hfields["#bo_div_nOrdenFin"] === true) {
        this.setbo_nOrdenIni($("#bo_nOrdenIni").val());
        this.setbo_nOrdenFin($("#bo_nOrdenFin").val());
      }
      
      
      this.setbo_nombre($("#bo_nombre").val());
      this.setbo_apellido($("#bo_apellido").val());
      this.setbo_tipoDocumento($("#bo_tipoDocumento").val());
      this.setbo_nroDocumento($("#bo_nroDocumento").val());
      this.setbo_tipoAtencion($("#bo_tipoAtencion").val());
      this.setbo_localizacion($("#bo_localizacion").val());
      this.setbo_procedencia($("#bo_procedencia").val());
      this.setbo_servicio($("#bo_servicio").val());
      this.setbo_seccion($("#bo_seccion").val());
      this.setbo_examen($("#bo_examen").val());
    }

      // Revisar
      if(this.hfields["#bo_div_procedencia"] === true && $('#procedenciaUsuario').length && $("#procedenciaUsuario").val() != 0){
        console.log($("#procedenciaUsuario").val(),'procedenciaUsuario')
        this.setbo_procedencia($("#procedenciaUsuario").val());
      }

  }

  boSearchFechas(fecha){
      
      let boFormData = new Object();
      let momentObj = moment(fecha, "DD-MM-YYYY HH:mm:ss");
      momentObj.startOf("day");
      let formattedDateTime = momentObj.format("DD-MM-YYYY HH:mm:ss");
      boFormData.bo_fIni = formattedDateTime;
      boFormData.bo_fFin = fecha;
      boFormData.bo_nOrdenIni = null;
      boFormData.bo_nOrdenFin = null;
      boFormData.bo_nombre = "";
      boFormData.bo_apellido = "";
      boFormData.bo_tipoDocumento = "";
      boFormData.bo_nroDocumento = "";
      boFormData.bo_tipoAtencion = "";
      boFormData.bo_localizacion = "";
      boFormData.bo_procedencia = "";
      boFormData.bo_servicio = "";
      boFormData.bo_seccion = "";
      boFormData.bo_examen = "";
      
      
      /*let momentObj = moment(fecha, "DD-MM-YYYY HH:mm:ss");
      momentObj.startOf("day");
      let formattedDateTime = momentObj.format("DD-MM-YYYY HH:mm:ss");
      this.setFechabo_fIni(formattedDateTime)
      this.setFechabo_fFin(fecha)
      this.setbo_nOrdenIni(null);
      this.setbo_nOrdenFin(null);
      this.setbo_nombre("");
      this.setbo_apellido("");
      this.setbo_tipoDocumento("");
      this.setbo_nroDocumento("");
      this.setbo_tipoAtencion("");
      this.setbo_localizacion("");
      this.setbo_procedencia("");
      this.setbo_servicio("");
      this.setbo_seccion("");
      this.setbo_examen("");*/
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
      
      if (!this.isDataTargetLoaded2) {
          boFormData.bo_fIni = $("#bo_fIni").val();
          boFormData.bo_fFin = $("#bo_fFin").val();
          //esto debe quedar vacío
          $("#mbfilter_datepicker").val("");
      } else {
          if ($("#bo_filter_ordenes").val().trim() !== ""){
            boFormData.bo_nOrdenIni = $("#bo_filter_ordenes").val();
            boFormData.bo_nOrdenFin = $("#bo_filter_ordenes").val();
          } else {
            boFormData.bo_fIni = $("#mbfilter_datepicker").val();
            boFormData.bo_fFin = $("#mbfilter_datepicker").val();
          }
          this.isDataTargetLoaded2 = false;
      }
      
      
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
    //limpia el formulario una vez realizada la búsqueda
    this.boLimpiarFormulario();
    return JSON.stringify(boFormData);

  }
  
 
  boFormatDocument(formatDoc){
    if(this.hfields['#bo_div_tipoDocumento'] && this.hfields['#bo_div_nroDocumento']){
      return formatDoc.replace(/[.]/g,'');
    }
  }

  //getters and setters
  
  getFechabo_fIni(){
    return this.bo_fIni;
  }
  
  setFechabo_fIni(bo_fIni){
    return this.bo_fIni = bo_fIni;
  }
  
  getFechabo_fFin(){
    return this.bo_fFin;
  }

  setFechabo_fFin(bo_fFin){
    return this.bo_fFin = bo_fFin;
  }

  getbo_nOrdenIni(){
    return this.bo_nOrdenIni;
  }

  setbo_nOrdenIni(bo_nOrdenIni){
    return this.bo_nOrdenIni = bo_nOrdenIni;
  }

  getbo_nOrdenFin(){
    return this.bo_nOrdenFin;
  }

  setbo_nOrdenFin(bo_nOrdenFin){
    return this.bo_nOrdenFin = bo_nOrdenFin;
  }

  getbo_nombre(){
    return this.bo_nombre;
  }
  
  setbo_nombre(bo_nombre){
    return this.bo_nombre = bo_nombre;
  }

  getbo_apellido(){
    return this.bo_apellido;
  }
  
  setbo_apellido(bo_apellido){
    return this.bo_apellido = bo_apellido;
  }

  getbo_tipoDocumento(){
    return this.bo_tipoDocumento;
  }
  
  setbo_tipoDocumento(bo_tipoDocumento){
    return this.bo_tipoDocumento = bo_tipoDocumento;
  }

  getbo_nroDocumento(){
    return this.bo_nroDocumento;
  }
  
  setbo_nroDocumento(bo_nroDocumento){
    return this.bo_nroDocumento = bo_nroDocumento;
  }

  getbo_tipoAtencion(){
    return this.bo_tipoAtencion;
  }
  
  setbo_tipoAtencion(bo_tipoAtencion){
    return this.bo_tipoAtencion = bo_tipoAtencion;
  }

  getbo_localizacion(){
    return this.bo_localizacion;
  }
  
  setbo_localizacion(bo_localizacion){
    return this.bo_localizacion = bo_localizacion;
  }

  getbo_procedencia(){
    return this.bo_procedencia;
  }
  
  setbo_procedencia(bo_procedencia){
    return this.bo_procedencia = bo_procedencia;
  }

  getbo_servicio(){
    return this.bo_servicio;
  }
  
  setbo_servicio(bo_servicio){
    return this.bo_servicio = bo_servicio;
  }

  getbo_seccion(){
    return this.bo_seccion;
  }
  
  setbo_seccion(bo_seccion){
    return this.bo_seccion = bo_seccion;
  }

  getbo_examen(){
    return this.bo_examen;
  }
  
  setbo_examen(bo_examen){
    return this.bo_examen = bo_examen;
  }

  
  //getters and setters

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
      // console.log(index);
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
    this.listaSecciones.getSeccionesSTEM(this.boGenFillSelectFields("#bo_seccion", "csecCodigo", "csecIdseccion"));
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
      }
        let opt = new Option('Todos', '', true, true);
        $(jqIdSelect).append($(opt));
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
      }
      if(!$(existeValue).length){
        let opt = new Option('Todos', '', true, true);
        $(jqIdSelect).append($(opt));
      }
    }
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

class BiolisTableOrdenesMicrobiologia {
    
    miBuscadorOrdenes;
    bo_OrdenesTableMb;
    doSearch;
    doLimpiarForm;
    rowCallback;
    doResponse;
    TableName;
    colFilter;
    reloadTable;
    fechaIsParm;
    
    constructor(jqidDataTable, buscadorOrdenes, selectCallback, opciones= this.defaultContent()){
        this.TableName = jqidDataTable;
        let me = this;
        this.colFilter = null;
        this.miBuscadorOrdenes = buscadorOrdenes;
        this.reloadTable = false;
        this.fechaIsParm = null;
        this.bo_OrdenesTableMb = $(jqidDataTable).DataTable({
            "ajax": {
                url: gCte.getctx + "/api/microbiologia/ordenes",
                contentType: "application/json",
                type: "POST",
                data: this.miBuscadorOrdenes.boFnGetFormData,//setFiltroOrden,
                "dataSrc": function(jsonData) {return jsonData.dato},
                processData: false,
              },
            "language": {
                "info": "Mostrando _TOTAL_ órdenes ",
                "infoFiltered": "filtradas de un total de _MAX_ ",
                "emptyTable": "No hay datos disponibles",
                "loadingRecords": "Por favor espere - cargando ...",
                "zeroRecords": "No se encontraron datos",
            },
            order: [[11, "desc"],[ 12, 'asc' ]], 
            scrollX: true,
            paging: false,
            info: false,
            "autoWidth": false,
            scrollY:calcularContentHeightSolo("divmotor-busqueda"),
            //scrollCollapse: true,
            columns: [
                { "data": "df_NORDEN" }, //0
                { "data": "sdf_FECHAORDEN" }, //1
                { "data": "dp_NOMBRES" }, //2
                { "data": "ldvs_DESCRIPCION" }, //3
                { "data": "sdp_FNACIMIENTO" }, //4
                { "data": "cta_DESCRIPCION" }, //5
                { "data": "cp_DESCRIPCION" }, //6
                { "data": "cs_DESCRIPCION" }, //7
                { "data": "dfe_EXAMENESURGENTE" }, //8
                { "data": "","defaultContent": "" }, //9
                { "data": "hayExamenesPendientes" }, //10
                { "data": "hayExamenesUrgentes" }, //11
                { "data": "dfe_CODIGOESTADOEXAMEN" }, //12
                { "data": "testsenproceso" }, //13
                { "data": "tienecultivo" } //14
            ],
            "search": {
              "smart": false
            },
            "columnDefs": opciones,
            "initComplete": function(settings, json) {
                /*
                if($("#seccionSelect").length){
                    let getSec = function() {
                      //let idSeccion = 16;
                      $("#seccionSelect").val(gIdSecccionMicrobiologia);
                      $("#seccionSelect").selectpicker('render');
                      searchSeccion(gIdSecccionMicrobiologia);
                      searchExamenSeccion(gIdSecccionMicrobiologia);
                    }
                    getSeccionesSTEM(servicioSecciones, getSec);
                }
                */
                $(jqidDataTable+"_filter").hide();
                let rowFilter = $('<tr class="filterOrden"></tr>').appendTo($(me.bo_OrdenesTableMb.table().header()));
                if (this.colFilter !== null) {
                    this.api().columns().every(function() {
                      let columna = this;
                       me.genColFilter(columna, rowFilter, me); //ingreso a los filtros

                    });
                }
            },
            select: {
              style: 'single',
              className: 'row-selected',
            },
        });
        
        this.rowCallback = selectCallback;
        this.doSearch = this.genDoSearch(this);
        this.doLimpiarForm = this.genDoLimpiarForm(this);
        
        me.bo_OrdenesTableMb.on('draw', function() {
            let registros_muestras = me.bo_OrdenesTableMb.rows().count();
            let rows_filtroMue = me.bo_OrdenesTableMb.rows({ search: 'applied' }).count();
            if($('#dt_registros_orden').length){
              $("#dt_registros_orden").html("Mostrando <b>" + rows_filtroMue + "</b> de <b>" + registros_muestras + "</b> órdenes en total");
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
        
        this.bo_OrdenesTableMb.on('select', function(e, dt, type, indexes) {
            console.log("esto no funca")
            if (type === 'row') {
              console.log('type',type)
              console.log('dt',dt)
              console.log('indexes',indexes)
              console.log('datos',dt.rows(indexes).data())
              let data = dt.rows(indexes).data();
              selectCallback(data);
            }
          });
        
    }
  
  defaultContent(){
    return [
        {
            targets: [10,11,12,13,14], //targets no visibles
            visible:false,
        },
        {
          targets: 2, //Nombre completo
          visible:true,
          render: function (data, type, row){
              return data+" "+row['dp_PRIMERAPELLIDO']+" "+(row['dp_SEGUNDOAPELLIDO'] || "");
          }
        },
        {
          targets: 5,
          visible: true,
          render : function(data, type, row){
              const color = tipoAtencionColorMap[row.cta_DESCRIPCION];
              const texto = `<div style="width:${t1*9}px"><span class="label label-lg font-weight-bold label-inline badge-${color}" >${row.cta_DESCRIPCION}</span></div>`;
              return texto;
          }
        },
        {
          targets: 8,
          visible:true,
          render: function (data, type, row){
              let texto = row.hayExamenesUrgentes;
              if (texto === 'N') {
                return `<div style="width:${t1*6}px"></div>`;
              }
              texto = `<div class="btn-toolbar justify-content-center" style="width:${t1*6}px"><a class="p-2"><i class="fas fa-exclamation-triangle" style="color:red;"></i></a></div>`;
              return texto;
          }
        },
        {
          targets: 9, //Acciones
          visible:true,
          orderable: false,
          className: "notTMClick",
          render: function(data, type, row) {
            return `
              <div class="btn-toolbar row">
                  <a href="#" onclick="getOrderDetail('${row.df_NORDEN}');" class="p-2" title="Datos Paciente"><i class="fas fa-user-md" aria-hidden="true"></i></a>
                  <a href="#" onclick="getAntecedentePacientesModal('${row.df_NORDEN}');"class="p-2" title="Antecedentes">
                      <i class="fas fa-notes-medical" aria-hidden="true"></i>
                    </a>
                  <a href="#" onclick="getOrderEventsDetail('${row.df_NORDEN}');" class="p-2" title="Trazabilidad">  <i class="fa fa-search" aria-hidden="true"></i></a>

              </div>
            `; //Se añade antecedentes. Marco Caracciolo --> Se cambia el orden, antecedentes primero que eventos de orden
          }
        }
    ];
  }
   
  genDoSearch(me) {

    return function(e) {
      e.preventDefault();
      if(me.miBuscadorOrdenes.isEmptyData()){
        me.bo_OrdenesTableMb.ajax.reload();
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
      me.bo_OrdenesTableMb.ajax.reload();
    }

  }
    
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
                case "selectUrgente":
                    this.genColSelectUrgente(key, rowFilter, me);
                    break;
                case "date":
                    this.genColDateFilter(key, rowFilter, me);
                    break;
                case "orden":
                    this.genColInputBuscaOrdenes(key, rowFilter, me);
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
  
  genColInputBuscaOrdenes(colIndex, rowFilter, me) {
    let inputText = $(`<input type="text" id="bo_filter_ordenes" class="form-control form-control-sm form-filter datatable-input" data-col-index="` + colIndex + `"/>`);
    $(inputText).appendTo($('<th>').appendTo(rowFilter));
    $(inputText).on('keypress', function(e){
        if(e.which == 13) {
            me.miBuscadorOrdenes.isDataTargetLoaded2 = true;
            $(me.TableName).DataTable().ajax.reload();
        }
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
  
  genColSelectUrgente(colIndex, rowFilter, me) {
    let inputSelect = $(`<select class="form-control form-control-sm form-filter p-1 data-col-index="` + colIndex + `"">
                            <option selected value="">TODOS</option><option value="N">Normal</option><option value="S">Urgente</option>
                        </select>`)
        .appendTo($('<th>').appendTo(rowFilter))
        .on('change', function () {
            let val = $.fn.dataTable.util.escapeRegex($(this).val());
            let columna = $(me.TableName).DataTable().column(targetUrgentes);
            columna.search(val ? '^' + val + '$' : '', true, false).draw()
        });
    
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
          me.miBuscadorOrdenes.isDataTargetLoaded2 = true;
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
          //$("#seccionSelect").val(gIdSecccionMicrobiologia);
          //$("#seccionSelect").selectpicker('render');
          //searchSeccion(gIdSecccionMicrobiologia);
          me.limpiarFiltroOrdenes();
          //limpiarTodo();
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
    this.searchPendiente(["P"],"","");
    $("#estadoPendiente").prop("checked", true);
    $("#estadoEnProceso").prop("checked", false);
    $("#estadoCultivo").prop("checked", false);
    
    let today = new Date();
    let fecha = convertirDateDDMMYYYY(today);
    $('.mbfilter_datepicker').val(fecha);
    
    this.miBuscadorOrdenes.isDataTargetLoaded = false;
    //this.miBuscadorOrdenes.setFechabo_fIni(convertirDateDD_MM_YYYY_HH_mm_SS(dateTimeAddHH(new Date(Date.now()), -1 * ParametrosApp.capturaResultadosWindow), '-', ':'))
    //this.miBuscadorOrdenes.setFechabo_fFin(convertirDateDD_MM_YYYY_HH_mm_SS(new Date(Date.now()), '-', ':'))
    
    tabla.ajax.reload();
  }
  
  limpiarTodo() {
    //limpiarUserData();
    limpiarTableResultadosExamenesOrden();
    limpiarTableInfoExamenesOrden();
  }
  
  searchPendiente(pendiente,cultivo,testsEnProceso) {
    $(this.getTableName()).DataTable().column(targetPendiente).search(pendiente, true, false, false)
                    .column(targetCultivo).search(cultivo, true, false, false)
                    .column(targetEnProceso).search(testsEnProceso, true, false, false).draw();
  }
  
  positionOrden(nOrden){
    let rows = this.bo_OrdenesTableMb.rows({ search: 'applied' }).data();
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
  
  getTableName(){
    return this.TableName;
  }

  getResultado(){
    return this.doResponse;
  }

  setResultado(doResponse){
    return this.doResponse = doResponse;
  }

  getRows(){
    return this.bo_OrdenesTableMb.rows({ search: 'applied' }).data();

  }
  
  reloadOrdenes(buscadorOrdenes, fecha = null) {
      
    let tabla = $(this.TableName).DataTable();
    this.reloadTable = true;
    this.fechaIsParm = false;
    if (fecha !== null && fecha !== undefined && fecha !== "") {
      this.fechaIsParm = true;
      //buscadorOrdenes.
    }
    
    buscadorOrdenes.isDataTargetLoaded = false;
    console.log("Actualizando...")
    tabla.ajax.reload();
    
    let today = new Date();
    let fechaHoy = convertirDateDDMMYYYY(today);
    $('.mbfilter_datepicker').val(fechaHoy);
  }
  
}  





//export {BiolisBuscadorOrdenes}; 