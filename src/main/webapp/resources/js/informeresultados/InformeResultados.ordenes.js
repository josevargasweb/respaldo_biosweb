var datos = {"nOrden":null,"fIni":null,"fFin":null,"nombre":"","apellido":""};

tableInfoResultados = $('#kt_datatable').DataTable({
    ajax: {url: UrlBase+"/api/ordenes",
           contentType: "application/json",
           type: "POST",
           dataSrc:"",
           processData:false,
           data:function(d){
                return JSON.stringify(datos);
            }
    },        
    colReorder: true,
    select:{
        style:     'single',
        className: 'row-selected',
        
    },
    language: {
        infoEmpty: "No hay datos.",
        "zeroRecords": "No se encontraron resultados",
        "sSearch" : "Buscar:",
        "info" : "Registros desde _START_ al _END_ de _TOTAL_ registros",
        "lengthMenu": "Mostrando _MENU_ registros", 
        "infoFiltered" : "(filtrado de un total de _MAX_ registros)",
        "loadingRecords": "Cargando...",
     },
    scrollY: '50vh',
    scrollX: true,
    scrollCollapse: true,
    searching: false,
    "lengthChange": false,
    columns: [
        { "": "" },
        { "data": "df_NORDEN" },
        { "data": "dp_NOMBRES" },
        { "data": "ldvtd_DESCRIPCION" },
        { "data": "dp_NRODOCUMENTO" },
        { "data": "cta_DESCRIPCION" },
        { "data": "cp_DESCRIPCION" },
        { "data": "cs_DESCRIPCION" },
        { "data": "dp_EMAIL" },
        { "data": "dp_IDPACIENTE" }
    ]
});


function buscarOrdenes(){
     try {
        let formValidator = new FormValidator();
        let nroOrdenValidator = new NroOrdenValidator("#txtNOrden",0,0);
        formValidator.add(nroOrdenValidator);
        let fechaIniMenorFinValidator = new FechaIniMenorFinValidator("#txtFInicio","#txtFFin");
        formValidator.add(fechaIniMenorFinValidator);
        let parmOk = formValidator.validate();
        if (parmOk === false){
            handlerMessageError(formValidator.message);
            return;
        }
        let filtroOrden = new FiltroOrden();
        filtroOrden.fill("#txtNOrden","#txtFInicio","#txtFFin","#txtFiltroNombre","#txtFiltroApellido",
                          "#selectTipoDocumentoFiltro","#txtNDocumento","#selectTipoAtencionFiltro",
                          "#selectServicio","#selectServicio");
        getOrdenes(filtroOrden);
   }
    catch(error){
        handlerMessageError(error);
   }
}

function getOrdenes(filtro){
    datos = filtro;
    tableInfoResultados.ajax.reload(); // con POST
 };


function fillOrdenData(data){

        let ordenSeleccionada = JSON.parse(JSON.stringify(data[0]))
        nroOrdenSeleccionada = data[0].df_NORDEN;
        setUserData(ordenSeleccionada);
        setOrdenData(ordenSeleccionada);
        loadInfoExamenesOrden(nroOrdenSeleccionada);
};

function setUserData(ordenSeleccionada){
    
    $("#datosPacienteModalNombre").val(ordenSeleccionada.dp_NOMBRES + ' ' + ordenSeleccionada.dp_PRIMERAPELLIDO + ' ' + ordenSeleccionada.dp_SEGUNDOAPELLIDO);
    $("#datosPacienteModalSexo").val(ordenSeleccionada.ldvs_DESCRIPCION);
    $("#datosPacienteModalEdad").val(ordenSeleccionada.sdp_FNACIMIENTO);
    $("#datosPacienteModalEmail").val(ordenSeleccionada.dp_EMAIL);
    $("#datosPacienteModalLocalizacion").val(ordenSeleccionada.cs_DESCRIPCION);
    $("#datosPacienteModalTipoAtencion").val(ordenSeleccionada.cta_DESCRIPCION);
    $("#datosPacienteModalObservacion").val(ordenSeleccionada.dp_OBSERVACION);
    $("#registroRecepcionModalNombre").val(ordenSeleccionada.dp_NOMBRES + ' ' + ordenSeleccionada.dp_PRIMERAPELLIDO + ' ' + ordenSeleccionada.dp_SEGUNDOAPELLIDO == null ? '':  ordenSeleccionada.dp_SEGUNDOAPELLIDO);
    $("#registroRecepcionModalRUT").val(ordenSeleccionada.dp_NRODOCUMENTO);
    $("#idModalSvcEmail").val(ordenSeleccionada.dp_EMAIL);
    $("#idModalPacienteEmail").val(ordenSeleccionada.dp_EMAIL);
    $("#idModalPacienteNombre").val(ordenSeleccionada.dp_NOMBRES + ' ' + ordenSeleccionada.dp_PRIMERAPELLIDO + ' ' + ordenSeleccionada.dp_SEGUNDOAPELLIDO);
    
}

function setOrdenData(ordenSeleccionada){
    $("#datosPacienteOrdenModalNro").val(ordenSeleccionada.df_NORDEN);
    $("#datosPacienteOrdenModalFecha").val(ordenSeleccionada.sdf_FECHAORDEN);
    $("#datosPacienteOrdenModalConvenio").val(ordenSeleccionada.cc_ABREVIADO);
    $("#datosPacienteOrdenModalMedico").val(ordenSeleccionada.cm_NOMBRES + ' ' + ordenSeleccionada.cm_PRIMERAPELLIDO + ' ' + ordenSeleccionada.cm_SEGUNDOAPELLIDO);
    $("#datosPacienteOrdenModalDiagnostico").val(ordenSeleccionada.cd_DESCRIPCION);
    $("#datosPacienteOrdenModalObservacion").val(ordenSeleccionada.df_OBSERVACION);
    $("#idDatosCardFecha").text(ordenSeleccionada.sdf_FECHAORDEN);
    $("#idDatosCardNOrden").text(ordenSeleccionada.df_NORDEN);
    $("#idDatosCardEMail").text(ordenSeleccionada.dp_EMAIL);
    $("#idDatosCardTipoAtencion").text(ordenSeleccionada.cta_DESCRIPCION);
    $("#idDatosCardEdad").text(ordenSeleccionada.sdp_FNACIMIENTO);
    $("#idDatosCardSexo").text(ordenSeleccionada.ldvs_DESCRIPCION);
    $("#idDatosCardNombre").text(ordenSeleccionada.dp_NOMBRES + ' ' + ordenSeleccionada.dp_PRIMERAPELLIDO + ' ' + ordenSeleccionada.dp_SEGUNDOAPELLIDO);
    
    
}

function generarQryString(examenesSeleccionados){
    let lstIdExamenes = 'pLstExId=';
    let n = examenesSeleccionados.length;
    for (i=0; i<n ; i++){
        lstIdExamenes += examenesSeleccionados[i].idExamen + ',';
    }
    if (examenesSeleccionados.length >0){
        lstIdExamenes = lstIdExamenes.substr(0,lstIdExamenes.length-1);
    }
    return lstIdExamenes;
}



