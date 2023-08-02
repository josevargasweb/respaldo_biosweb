let now = new Date();
now.setHours(0, 0, 0);
var ParametrosApp = { capturaResultadosWindow: 120 };

class BiolisBuscadorOrdenes {

  fields;
  hfields;
  inputFields;
  jqueryObjectList;
  boFnBuscarOrden;
  /*
  listaServicios;
  listaTiposAtencion;
  listaProcedencias;
  listaTiposDocumento;
  listaLocalizaciones;
  listaSecciones;
  listaExamenes;*/
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

    this.hfields["#bo_fechaIngreso"] = false;

    this.hfields["#bo_checkbox_filtros"] = false;

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

    this.jqueryObjectList["#estadoPendiente2"] = $("#estadoPendiente2");
    this.jqueryObjectList["#estadoFirmar"] = $("#estadoFirmar");
    this.bo_btnBuscarOrden = $("#bo_btnBuscarOrden");
    this.bo_btnBuscarOrden.prop('disabled', true);

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
    this.estadoPendiente2;
    this.estadoFirmar;
    this.boSetFechas();
    let mis = this;


    options.forEach(opt => { 
        $(opt).css("display", "block"); 
        $(opt).removeAttr("hidden");
        this.hfields[opt] = true; 
    });
    this.boFnSetFocusHandler(this);
    this.boFnBuscarOrden = this.boFnSetGetFormData(this);
    //this.boFnFillSelect();
    this.isEmptyData = this.boFnValidarFiltros(options);

    $('#bo_fFin').datepicker({
      todayHighlight: true,
      orientation: "bottom left",
      language: 'es',
      endDate: "+Infinity",
      format: 'dd/mm/yyyy',
      autoclose: true,
    }).on('change',function(){
      // mis.probandoFn() modificar
    });

    $('#bo_fIni').datepicker({
      todayHighlight: true,
      orientation: "bottom left",
      language: 'es',
      endDate: "+Infinity",
      format: 'dd/mm/yyyy',
      autoclose: true,
    });


    $("#bo_nroDocumento").rut({ formatOn: 'keyup', validateOn: 'blur' });
    $('#bo_nroDocumento').attr({
      'minlength': '11',
      'maxlength': '12',
      'autocomplete': 'off',
      'onkeyup': 'this.value = this.value.toUpperCase()'
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

    if ($('.filtros-estados').length) {
      $('.filtros-estados').click(function() {
        $('.filtros-estados').not(this).prop('checked', false);
      });
    }


    $("#bo_nOrdenIni").on('blur', function() {
      var texto = $(this).val();
      $("#bo_nOrdenFin").val(texto);
      $("#bo_nOrdenFin").focus()
      if ($("#bo_nOrdenFin").hasClass('selectpicker')) {
        $("#bo_nOrdenFin")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
      }else{
        $("#bo_nOrdenFin").removeClass("is-invalid");
      }
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

  boSetFechas(){
    if (!this.isDataTargetLoaded2) {
      this.setFechabo_fIni(convertirDateDD_MM_YYYY_HH_mm_SS(dateTimeAddHH(new Date(Date.now()), -1 * ParametrosApp.capturaResultadosWindow), '-', ':'))
      this.setFechabo_fFin(convertirDateDD_MM_YYYY_HH_mm_SS(new Date(Date.now()), '-', ':'))
      this.setbo_nOrdenIni(null);
      this.setbo_nOrdenFin(null);
      this.setbo_nombre("");
      this.setbo_apellido("");
      this.setbo_estadoPendiente2(false);
      this.setbo_estadoFirmar(false);
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
      this.setbo_estadoPendiente2($('#estadoPendiente2').prop('checked') ? true : false);
      this.setbo_estadoFirmar($('#estadoFirmar').prop('checked') ? true : false);
    }

      // Revisar
      if(this.hfields["#bo_div_procedencia"] === true && $('#procedenciaUsuario').length && $("#procedenciaUsuario").val() != 0){
        console.log($("#procedenciaUsuario").val(),'procedenciaUsuario')
        this.setbo_procedencia($("#procedenciaUsuario").val());
      }

  }

  boSearchFechas(fecha){
      let momentObj = moment(fecha, "DD-MM-YYYY HH:mm:ss");
      momentObj.startOf("day");
      let formattedDateTime = momentObj.format("DD-MM-YYYY HH:mm:ss");
      $("#bo_fIni").val(formattedDateTime);
      $("#bo_fFin").val(fecha);
  }

  boSearchFechasClean(){
    $("#bo_fIni").val("");
    $("#bo_fFin").val("");
}

  


boFnGetFormData() {
    let boFormData = new Object();
    if (!this.isDataTargetLoaded) {

      boFormData.bo_fIni = convertirDateDD_MM_YYYY_HH_mm_SS(dateTimeAddHH(new Date(Date.now()), -1 * ParametrosApp.capturaResultadosWindow), '-', ':');
      boFormData.bo_fFin = convertirDateDD_MM_YYYY_HH_mm_SS(new Date(Date.now()), '-', ':');
      boFormData.bo_nOrdenIni = null;
      boFormData.bo_nOrdenFin = null;
      boFormData.bo_nombre = null;
      boFormData.bo_apellido = null;
      boFormData.estadoPendiente2 = null;
      boFormData.estadoFirmar = null;
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
      boFormData.estadoPendiente2 =  $('#estadoPendiente2').prop('checked') ? true : false;
      boFormData.estadoFirmar =  $('#estadoFirmar').prop('checked') ? true : false;
    }
    
    // Revisar
    if(this.hfields["#bo_div_procedencia"] === true && $('#procedenciaUsuario').length && $("#procedenciaUsuario").val() != 0){
      console.log($("#procedenciaUsuario").val(),'procedenciaUsuario')
      boFormData.bo_procedencia = $("#bo_procedencia").val();
    }
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

  getbo_estadoPendiente2(){
    return this.estadoPendiente2;
  }
  
  setbo_estadoPendiente2(estadoPendiente2){
    return this.estadoPendiente2 = estadoPendiente2;
  }

  getbo_estadoFirmar(){
    return this.estadoFirmar;
  }
  
  setbo_estadoFirmar(estadoFirmar){
    return this.estadoFirmar = estadoFirmar;
  }



  //getters and setters

  boFnChange(e) {
    console.table(e);
  }

  boFnReglaExclusionFiltroNroOrden(me) {
    this.editarSoloLecturaCamposElegidos(me,["#bo_nOrdenIni", "#bo_nOrdenFin"],["#bo_nOrdenIni", "#bo_nOrdenFin","#bo_fIni","#bo_fFin"]);
    this.focusEnter(me,["#bo_nOrdenIni", "#bo_nOrdenFin", "#bo_nombre"])
  }
  
  boFnBlurFiltroNroOrden(me) {
    this.setDisabledInBlur(me,["#bo_nOrdenIni", "#bo_nOrdenFin"],["#bo_nOrdenIni", "#bo_nOrdenFin","#bo_fIni","#bo_fFin"]);
  }

  boFnReglaExclusionFiltroFecha(me) {
    this.editarSoloLecturaCamposElegidos(me,["#bo_fIni", "#bo_fFin"],["#bo_nOrdenIni", "#bo_nOrdenFin","#bo_fIni","#bo_fFin"]);
  }

  boFnBlurFiltroFecha(me) {
    this.setDisabledInBlur(me,["#bo_fIni", "#bo_fFin"],["#bo_nOrdenIni", "#bo_nOrdenFin","#bo_fIni","#bo_fFin"]);
  }

  boFnReglaExclusionFiltroIzq(me){
    this.editarSoloLecturaCamposElegidos(me,["#bo_nombre", "#bo_apellido", "#bo_nroDocumento"],["#bo_nombre", "#bo_apellido", "#bo_nroDocumento","#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"]);
    this.focusEnter(me,["#bo_nombre", "#bo_apellido", "#bo_tipoAtencion"])
  }

  boFnBlurFiltroIzq(me) {
    this.setDisabledInBlur(me,["#bo_nombre", "#bo_apellido", "#bo_nroDocumento"],["#bo_nombre", "#bo_apellido", "#bo_nroDocumento","#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"]);
  }

  boFnReglaExclusionFiltroDer(me){
    this.editarSoloLecturaCamposElegidos(me,["#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"],["#bo_nombre", "#bo_apellido", "#bo_tipoDocumento", "#bo_nroDocumento","#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"]);
  }

  boFnBlurFiltroDer(me) {
    this.setDisabledInBlur(me,["#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"],["#bo_nombre", "#bo_apellido", "#bo_tipoDocumento", "#bo_nroDocumento","#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"]);
  }

  onFnChangeFiltroSelectpicker(me){
    this.focusChange(me,["#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"])
    this.editarSoloLecturaCamposElegidos(me,["#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"],["#bo_nombre", "#bo_apellido", "#bo_tipoDocumento", "#bo_nroDocumento","#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"]);
    this.setDisabledInBlur(me,["#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"],["#bo_nombre", "#bo_apellido", "#bo_tipoDocumento", "#bo_nroDocumento","#bo_tipoAtencion","#bo_procedencia","#bo_servicio","#bo_seccion","#bo_examen"],true);
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

  /*
    habilita y desabilita elementos
  */


  
  setDisabledInBlur(me,fieldsToExclude,otherFields,esSelect = false){
    console.log('setDisabledInBlur',fieldsToExclude,otherFields)
    let validacion
    if(!esSelect){
      validacion = me.itemsSinInfo(fieldsToExclude)
    }else{
      validacion = me.itemsSinInfoSelect(fieldsToExclude)
    }

    console.log('validtin',validacion)

    if (validacion) {
    for (const field of otherFields) {
      if (!fieldsToExclude.includes(field)) {
        $(field).prop('readonly', false);
        $(field).removeAttr('disabled');
        if ($(field).hasClass('selectpicker')) {
          $(field).selectpicker('refresh');
        }
      }
      }
    }
  }

  itemsSinInfo(fieldIds) {
    for (let i = 0; i < fieldIds.length; i++) {
      const field = $(fieldIds[i]);
      if (field.val() !== '' && field.val() !== null) {
        return false;
      }
    }
    return true;
  }

  itemsSinInfoSelect(fieldIds) {
    for (let i = 0; i < fieldIds.length; i++) {
      const field = $(fieldIds[i]);
      if (field.val() !== 'a' && field.val() !== null) {
        return false;
      }
    }
    return true;
  }

  editarSoloLecturaCamposElegidos(me,fieldsToExclude,otherFields) {
    // if(me.tienenInfor(me,fieldsToExclude)){
      for (const field of otherFields) {
        if (fieldsToExclude.includes(field)) {
            $(field).prop('readonly', false);
            $(field).removeAttr('disabled');
            $(field).on('blur', function () {
              me.checkAndSetReadonly(me,fieldsToExclude,otherFields);
            });
            if ($(field).hasClass('selectpicker')) {
              $('.selectpicker').selectpicker('refresh');
            }
        } else {
          $(field).prop('readonly', true);
          $(field).prop('disabled', true);
          

          if ($(field).hasClass('selectpicker')) {
            $(field)
            .removeClass("is-invalid")
            .selectpicker("setStyle")
            .parent()
            .removeClass("is-invalid");
            $('.selectpicker').selectpicker('refresh');
          }else{
            $(field).removeClass("is-invalid");
          }
        }
      }
    // }
  }

  tienenInfor(me, fields) {
    let tieneValor = false;
    console.log('fields',fields)
    for (const field in me.inputFields) {
      if (fields.includes(field)) {
        console.log('fields',field,$(field).val(),$(field).val() !== ''  )
        if ($(field).is('select') && field !== '#bo_tipoDocumento') {
          if ($(field).val() !== 'a' && $(field).val() !== undefined) {
            tieneValor = true;
          }
        } else if(field !== '#bo_tipoDocumento'){
          if ($(field).val() !== '' && $(field).val() !== undefined) {
            tieneValor = true;
          }
        }
      }
    }
    console.log('tieneValor',tieneValor)
    return tieneValor;
  }


 

  checkAndSetReadonly(me,fieldsToExclude,otherFields) {
    let hasValue = false;
    for (const field of fieldsToExclude) {
      console.log(field,$(field).val(),'eoka')
      if ($(field).is('select') && $(field).val() !== 'a') {
        hasValue = true;
      } else if ($(field).val() !== '') {
        hasValue = true;
      }
      // if ($(field).val() !== '' ) {
      //   hasValue = true;
      // }
    }

    if (hasValue) {
      for (const field in otherFields) {
        if (!fieldsToExclude.includes(field)) {
          $(field).prop('readonly', true);
          $(field).prop('disabled', true);
          if ($(field).hasClass('selectpicker')) {
            $(field).selectpicker('refresh');
            $(field)
            .removeClass("is-invalid")
            .selectpicker("setStyle")
            .parent()
            .removeClass("is-invalid");
          }else{
            $(field).removeClass("is-invalid");
          }
        }
      }
    }
  }
   /*
    habilita y desabilita elementos
  */



     /*
   focus inputs
  */
   focusEnter(me,fields){
    $.each(fields, function(index, value) {
      $(value).on('keydown', function(event) {
        if (event.which === 13) {
          console.log(document.activeElement);
          event.preventDefault(); // previene el comportamiento por defecto de la tecla Enter
          if ($(fields[index + 1]).hasClass('selectpicker')) {
            $(`button[data-id="${fields[index + 1].substring(1)}"]`).focus();
            $(fields[index + 1]).selectpicker('toggle');
            // $(fields[index + 1]).selectpicker('refresh');
          }else{
            $(fields[index + 1]).focus();
          }
        }
      });
    });
   }

   focusChange(me, fields) {
    var ultimosValores = {};
    console.log(document.activeElement);
  
    $.each(fields, function (index, value) {
      $(value).on('change', function (event) {
        event.preventDefault();
        var valorActual = this.value;
        if (valorActual !== ultimosValores[value]) {
          ultimosValores[value] = valorActual;
          if ($(fields[index + 1]).hasClass('selectpicker')) {
            $(`button[data-id="${fields[index + 1].substring(1)}"]`).focus();
            // $(fields[index + 1]).selectpicker('toggle');
          } else {
            $(fields[index + 1]).focus();
          }
        }
      });
  
      // Agregar manejador de eventos keydown para controlar el foco
      $(value).on('keydown', function (event) {
        if (event.which === 9) { // Tecla Tab
          event.preventDefault(); // Evitar el comportamiento predeterminado del tabulador
          var valorActual = this.value;
          if (valorActual !== ultimosValores[value]) {
            ultimosValores[value] = valorActual;
            if ($(fields[index + 1]).hasClass('selectpicker')) {
              $(`button[data-id="${fields[index + 1].substring(1)}"]`).focus();
              // $(fields[index + 1]).selectpicker('toggle');
            } else {
              $(fields[index + 1]).focus();
            }
          }
        }
      });
    });
  }
  

    /*
    focus inputs
  */


  boFnFocus(me) {
    return function(e) {
      let jqId = "#" + e.currentTarget.id;
      if (jqId === "#bo_nOrdenIni" || jqId === "#bo_nOrdenFin") {
        me.boFnReglaExclusionFiltroNroOrden(me);
      }else if(jqId === "#bo_fIni" || jqId === "#bo_fFin"){
        me.boFnReglaExclusionFiltroFecha(me);
      }
      else if(jqId === "#bo_nombre" || jqId === "#bo_apellido" || jqId === "#bo_nroDocumento"){
			if(jqId === "#bo_nroDocumento"){
				$(jqId).on( "focusout", function(){
					if ( !($(".valizq").filter(function() { return $(this).val(); }).length > 0)) {
			            $('.bo_select').prop('readonly', false);
	          			$('.bo_select').removeAttr('disabled');
	          			$('.selectpicker').selectpicker('refresh');
          			}
				});
			}
        me.boFnReglaExclusionFiltroIzq(me);
      }
      else if(jqId === "#bo_tipoAtencion" || jqId === "#bo_procedencia" || jqId === "#bo_servicio" || jqId === "#bo_seccion" || jqId === "#bo_examen"){
        me.boFnReglaExclusionFiltroDer(me);
      }
      else {
        // me.boFnReglaExclusionFiltroNroOrden(false);
        // me.boFnReglaExclusionFiltroNroDocumento(false);
      }
    }
  }


  boFnBlur(me) {
    return function(e) {
      let jqId = "#" + e.currentTarget.id;
      let noOnlyOrden = true;
      if (jqId === "#bo_nOrdenIni" || jqId === "#bo_nOrdenFin") {
        me.boFnBlurFiltroNroOrden(me);
        noOnlyOrden = false
      }
      else if (jqId === "#bo_tipoDocumento" || jqId === "#bo_nroDocumento") {
        // me.boFnBlurFiltroNroDocumento(me);
      }
      else if(jqId === "#bo_nombre" || jqId === "#bo_apellido" || jqId === "#bo_nroDocumento"){
        me.boFnBlurFiltroIzq(me);
        
      }
      else if(jqId === "#bo_tipoAtencion" || jqId === "#bo_procedencia" || jqId === "#bo_servicio" || jqId === "#bo_seccion" || jqId === "#bo_examen"){
        me.boFnBlurFiltroDer(me);
      }
      else {
        // me.boFnReglaExclusionFiltroNroOrden(false);
        // me.boFnReglaExclusionFiltroNroDocumento(false);
      }

      if(!noOnlyOrden){
        me.setEnableButton(me);
      }else{
        me.setEnableButton(me,jqId);
      }
      
    }
  }



  boFnChangeDate(me) {
    return function(e) {
      let jqId = "#" + e.currentTarget.id;
      let noOnlyDate = true;
     if (jqId === "#bo_fIni" || jqId === "#bo_fFin") {
        me.boFnBlurFiltroFecha(me);
        noOnlyDate = false;
      }
      else if(jqId === "#bo_tipoAtencion" || jqId === "#bo_procedencia" || jqId === "#bo_servicio" || jqId === "#bo_seccion" || jqId === "#bo_examen"){
      me.onFnChangeFiltroSelectpicker(me);
      }else{
        
      }
      if ($(jqId).hasClass('selectpicker')) {
        $(jqId)
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
      }else{
        $(jqId).removeClass("is-invalid");
      }
      if(!noOnlyDate){
        me.setEnableButton(me);
      }else{
        me.setEnableButton(me,jqId);
      }
    }
  }

  setEnableButton(me,field = ''){
    const nOrdenIniVacio = $(me.inputFields['#bo_nOrdenIni']).val().trim() === '';
    const nOrdenFinVacio = $(me.inputFields['#bo_nOrdenFin']).val().trim() === '';
    const fIniVacio = $(me.inputFields['#bo_fIni']).val().trim() === '';
    const fFinVacio = $(me.inputFields['#bo_fFin']).val().trim() === '';
    console.log(nOrdenIniVacio,nOrdenFinVacio,fFinVacio,fIniVacio,field)
    if(field === ''){
      if(
        (!fIniVacio && !fFinVacio) 
        || (!nOrdenIniVacio && !nOrdenFinVacio)
      ){
        me.bo_btnBuscarOrden.prop('disabled', false)
      }else{
        me.bo_btnBuscarOrden.prop('disabled', true)
      }
    }else{
        let validador
        var nodeName = $(field).prop("nodeName").toLowerCase();
        if (nodeName === "input") {
          var inputType = $(field).attr("type").toLowerCase();
          if(inputType === "checkbox"){
            validador = $(field).prop('checked')
          }else{
            validador = $(field).val().trim() === ''
          }
        }else{
          validador = $(field).val() === ''
        }
         
        if((!validador && !fIniVacio && !fFinVacio) || 
        (!validador && !nOrdenIniVacio && !nOrdenFinVacio)){
          me.bo_btnBuscarOrden.prop('disabled', false)
        }else{
          me.bo_btnBuscarOrden.prop('disabled', true)
        }
    }
  }

  //debe detectar los cambios en selectpicker
  boFnSetFocusHandler(me) {
    for (let jqObj in this.jqueryObjectList) {
      this.jqueryObjectList[jqObj].focus(this.boFnFocus(me));
      this.jqueryObjectList[jqObj].blur(this.boFnBlur(me));
      this.jqueryObjectList[jqObj].change(this.boFnChangeDate(me));
 
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
    //no pude hacer funcionar examenes con boGenFillSelectFields
	this.examenesSelectBD('#bo_examen');



  }
examenesSelectBD(jqIdSelect){
	//let jqIdSelect ='#bo_examen';
	if ($(jqIdSelect+' option:first').val()!== '-1'){
		let cfgExamen = new CfgExamen();
		cfgExamen.getSoloExamenes((data) => {
		    for (const proc of data) {
		        const opt = new Option(proc.ceAbreviado, proc.ceIdexamen);
		  		  $(jqIdSelect).append($(opt));
		    }
	      let opt = new Option('Todos', '-1', true, true);
	      $(jqIdSelect).prepend($(opt));
		    $(jqIdSelect).selectpicker('refresh');
		});
	}
}
  boGenFillSelect(jqIdSelect) {
	if ($(jqIdSelect+' option:first').val() !== '-1'){
	    function fill(data) {
	      data.forEach(proc => {
	        let opt = new Option(proc.csDescripcion, proc.csIdservicio);
	        $(jqIdSelect).append($(opt));
	      });
	      let opt = new Option('Todos', '-1', true, true);
	      $(jqIdSelect).prepend($(opt));
	      if ($(jqIdSelect).hasClass('selectpicker')) {
	          $(jqIdSelect).selectpicker('refresh');
	      }
	
	    }
	    return fill;
    }
    
  }

  boGenFillSelectFields(jqIdSelect, descriptionField, idField,existeValue) {
	if ($(jqIdSelect+' option:first').val() !== '-1'){
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
	        let opt = new Option('Todos', '-1', true, true);
	        $(jqIdSelect).prepend($(opt));
	        if ($(jqIdSelect).hasClass('selectpicker')) {
	          $(jqIdSelect).selectpicker('refresh');
	      }
	    }
	    return fill;
	  }
  }

  boGenFillSelectFieldsTipoDoc(jqIdSelect, descriptionField, idField) {
	let selectElement = document.getElementById("bo_tipoDocumento");//no pude hacerlo con jquery (jan)
	
	if (selectElement.options.length > 0) {
	  console.log("The select has options");
	} else {
	  	if ($(jqIdSelect+' option:first').val() !== '-1'){
		    function fill(data) {
		      if (data !== undefined && data !== null) {
		        if (data.status !== undefined) {
		          data = data.dato;
		        }
		        data.forEach(proc => {
		          if(proc.ldvtdIdtipodocumento === 1 || proc.ldvtdIdtipodocumento === 2 || proc.ldvtdIdtipodocumento === 6 ){
		            let opt = new Option(proc[descriptionField], proc[idField]);
		            $(jqIdSelect).append($(opt));
		          }
		        });
		      }
		        // let opt = new Option('Todos', '', true, true);
		        // $(jqIdSelect).prepend($(opt));
		        if ($(jqIdSelect).hasClass('selectpicker')) {
		          $(jqIdSelect).selectpicker('refresh');
		        }
		    }
		    return fill;
	  }
	}

  }


  boGenFillSelectFieldsProcedencia(jqIdSelect, descriptionField, idField,existeValue) {
	if ($(jqIdSelect+' option:first').val() !== '-1'){
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
	        if ($(jqIdSelect).hasClass('selectpicker')) {
	          $(jqIdSelect).selectpicker('refresh');
	        }
	      }
	      if(!$(existeValue).length){
	        let opt = new Option('Todos', '-1', true, true);
	        $(jqIdSelect).prepend($(opt));
	        if ($(jqIdSelect).hasClass('selectpicker')) {
	          $(jqIdSelect).selectpicker('refresh');
	        }
	      }
	    }
	    return fill;
	  }
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
        { "data": "df_NORDEN" },
        { "data": "df_NORDEN" },
        { "data": "sdf_FECHAORDEN" },
        { "data": "df_OBSERVACION" },
        { "data": "dp_IDPACIENTE" },
        { "data": "ldvtd_DESCRIPCION" },
        { "data": "dp_NRODOCUMENTO" },
        { "data": "dp_NOMBRES" },
        { "data": "dp_NOMBRES" },
        { "data": "dp_PRIMERAPELLIDO" },
        { "data": "dp_SEGUNDOAPELLIDO" },
        { "data": "sdp_FNACIMIENTO" },
        { "data": "dp_EMAIL" },
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
        { "data": "ldvfet_DESCRIPCION" },
      ],
            "columnDefs":
        [
            {
                targets: 7, //Nombre completo
                visible:false,
                render: function (data, type, row){
                    return data+" "+row['dp_PRIMERAPELLIDO']+" "+(row['dp_SEGUNDOAPELLIDO'] || "");
                }
            
            },{
                targets: 24,
                render: function (data, type, meta) {
                    var status = {
                        "ATENDIDO": {'class': ' label-light-success'},
                        "ESPERA": {'class': ' label-light-danger'},
                        "BLOQUEADA": {'class': ' label-light-default'}
                    };
                    if (typeof status[data] === 'undefined') {
                        return data;
                    }
                    return `<span class="muestra-table"><span class="label label-md font-weight-bold' ${status[data].class} label-inline">${data}</span></span>`;
                }
            }
        ],
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



}/*

//Busqueda detallada providencias
function procedenciasSelectBD(){
	let cfgProcedencia = new CfgProcedencia();
	cfgProcedencia.getProcedencias((data) => {
        const opt = new Option('Seleccionar',0);
	    $('#bo_procedencia').append($(opt));
	    for (const proc of data) {
	        const opt = new Option(proc.cpDescripcion, proc.cpIdprocedencia);
	  		  $('#bo_procedencia').append($(opt));
	    }
	    $('#bo_procedencia').selectpicker('refresh');
	});
}
function tipoAtencionSelectBD(){
	let cfgTipoAtencion = new CfgTipoAtencion();
	cfgTipoAtencion.getTiposAtencion((data) => {
        const opt = new Option('Seleccionar',null);
	    $('#bo_tipoAtencion').append($(opt));
	    for (const proc of data) {
	        const opt = new Option(proc.ctaDescripcion, proc.ctaIdtipoatencion);
	  		  $('#bo_tipoAtencion').append($(opt));
	    }
	    $('#bo_tipoAtencion').selectpicker('refresh');
	});
}

function servicioSelectBD(){
	let cfgServicios = new CfgServicios();
	cfgServicios.getServicios((data) => {
        const opt = new Option('Seleccionar',null);
	    $('#bo_servicio').append($(opt));
	    for (const proc of data) {
	        const opt = new Option(proc.csDescripcion, proc.csIdservicio);
	  		  $('#bo_servicio').append($(opt));
	    }
	    $('#bo_servicio').selectpicker('refresh');
	});
}
function seccionesSelectBD(){
	let cfgSecciones = new CfgSecciones();
	cfgSecciones.getSecciones((data) => {
        const opt = new Option('Seleccionar',null);
	    $('#bo_seccion').append($(opt));
	    for (const proc of data) {
	        const opt = new Option(proc.csecCodigo, proc.csecIdseccion);
	  		  $('#bo_seccion').append($(opt));
	    }
	    $('#bo_seccion').selectpicker('refresh');
	});
}
function tipoDocumentoSelectBD(){
	let cfgTipoDocumento = new CfgTipoDocumento();
	cfgTipoDocumento.getTiposDocumento((data) => {
        const opt = new Option('Seleccionar',null);
	    $('#bo_tipoDocumento').append($(opt));
	    for (const proc of data) {
	        const opt = new Option(proc.ldvtdDescripcion, proc.ldvtdIdtipodocumento);
	  		  $('#bo_tipoDocumento').append($(opt));
	    }
	    $('#bo_tipoDocumento').selectpicker('refresh');
	});
}
function examenesSelectBD(){
	let cfgExamen = new CfgExamen();
	cfgExamen.getSoloExamenes((data) => {
       const opt = new Option('Seleccionar',null);
	   $('#bo_examen').append($(opt));
	    for (const proc of data) {
	        const opt = new Option(proc.ceAbreviado, proc.ceIdexamen);
	  		  $('#bo_examen').append($(opt));
	    }
	    $('#bo_examen').selectpicker('refresh');
	});
}

$( "#btnBuscarOrden" ).on( "click", function() {
  procedenciasSelectBD();
  tipoAtencionSelectBD();
  seccionesSelectBD();
  servicioSelectBD();
  tipoDocumentoSelectBD();
  examenesSelectBD();

  $('.bo_select')
    .find('option')
    .remove()
    .end()
;
});*/

