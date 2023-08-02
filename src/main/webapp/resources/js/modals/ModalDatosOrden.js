var tablaExamenes = null;
var nroOrden = -1;

   //lista derivadores
   let derivadores = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/derivadores/list",
        type: 'GET',
        async: false,
    });
    return jqXHR.responseJSON;
  };
   
  let centros = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/instituciones/centrosdesalud/list",
        type: 'GET',
        async: false,
    });
    return jqXHR.responseJSON;
  };

  let laboratorios = function (idCentro) {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/instituciones/laboratorios/list/"+idCentro,
        type: 'GET',
        async: false,
    });
    return jqXHR.responseJSON;
  };

  let secciones = function (idLab) {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/instituciones/secciones/list/"+idLab,
        type: 'GET',
        async: false,
    });
    return jqXHR.responseJSON;
  };

function MostrarDatosOrden(nOrden) {
    nroOrden = nOrden;
    $("#btnDocumentosOrden").prop('disabled', true)
    loadEnabledOrdenMedica(nOrden)
    $('#DatosOrdenModalTitulo').text(nOrden);

    if ( $.fn.DataTable.isDataTable('#tablaExamanesOrden') ){
        $('#tablaExamanesOrden').DataTable().clear().destroy();
    }
    
    $.ajax({
        type: "GET",
        url: getctx + "/api/ordenFull/"+nOrden,
        datatype: "json",
        async: false,
        success: function (message) {
            let orden = message.dato;
            let dia = orden.sdf_FECHAORDEN.substring(0, 2);
            let mes = orden.sdf_FECHAORDEN.substring(3, 5);
            let year = orden.sdf_FECHAORDEN.substring(8, 10);
            let hora = orden.sdf_FECHAORDEN.substring(11, 16);
            $('#ModalDatosOrdenNombrePac').text(orden.dp_NOMBRES+" "+orden.dp_PRIMERAPELLIDO+" "+(orden.dp_SEGUNDOAPELLIDO || ""));
            $('#ModalDatosHost').text(orden.df_HOST);
            $('#ModalDatosFecha').text(hora + " " + dia + "/" + nombresMeses(mes) + "/" + year);
            $('#ModalDatosOrdenDiagnostico').text(orden.cd_DESCRIPCION);
            $("#ModalDatosOrdenObs").text(orden.df_OBSERVACION);
        }
    });
    
    $('#tablaExamanesOrden tbody').empty();
    

    tablaExamenes = $('#tablaExamanesOrden').DataTable({
        responsive: false,
        paging: false,
        searchDelay: 500,
        orderCellsTop: true,
        "ordering": false,
        scrollY: 300,
        scrollX: true,
        info: false,
        dom: '<"top"i>rt<"bottom"flp><"clear">',
        "ajax": {
                url: getctx + "/api/examenes/orden/" + nOrden,
                type: "GET",
                contentType: "json",
                dataSrc: ""
        },
        "columns": [
                {data: 'ce_DESCRIPCION'},
                {data: 'ce_CODIGOEXAMEN'},
                {data: 'ccds_DESCRIPCION',type:'select'},
                {data: 'clab_DESCRIPCION',type:'select'},
                {data: 'csec_DESCRIPCION',type:'select'},
                {data: 'cmue_DESCRIPCION'},
                {data: 'cenv_DESCRIPCION'},
                {data: 'ce_COMPARTEMUESTRA'},
                {data: 'cderiv_DESCRIPCION',type:'select'}
        ],
        "rowCallback": function(row, data, index){
            //tacha el row completo si el examen está anulado
            if(data['dfe_EXAMENANULADO']==='S'){
                $("td", row).css('text-decoration','line-through');
            }
        },
        "columnDefs": [
            {
                targets: 2,
                orderable: false,
                render: function ( data, type, row ) {
                    let idexamen = row['ce_IDEXAMEN'];
                    let centro = row['ccds_IDCENTRODESALUD'];
                    let idLab = row['clab_IDLABORATORIO'];
                    let idmuestra = row['dfet_IDMUESTRA'];
                    let descripcion = row['ccds_DESCRIPCION'];
                    if(idmuestra===null ){
                        return cargarCentro(centros(),centro,idexamen,nOrden);
                    }else{
                        return `<span>${largoTexto(descripcion,16)}</span>`;
                    }
                }
            },
            {
                targets: 3,
                orderable: false,
                render: function ( data, type, row ) {
                    let idexamen = row['ce_IDEXAMEN'];
                    let centro = row['ccds_IDCENTRODESALUD'];
                    let idLab = row['clab_IDLABORATORIO'];
                    let idmuestra = row['dfet_IDMUESTRA'];
                    let descripcion = row['clab_DESCRIPCION'];

                    if(idmuestra===null ){
                        return cargarLab(laboratorios(centro),idLab,idexamen,nOrden);
                    }else{
                        return `<span>${largoTexto(descripcion,11)}</span>`;
                    }
                  
                }
            },
            {
                targets: 4,
                orderable: false,
                render: function ( data, type, row ) {
                    let idexamen = row['ce_IDEXAMEN'];
                    let idLab = row['clab_IDLABORATORIO'];
                    let seccion = row['csec_IDSECCION'];
                    let idmuestra = row['dfet_IDMUESTRA'];
                    let descripcion = row['csec_DESCRIPCION'];    
                    if(idmuestra===null ){
                        return cargarSeccion(secciones(idLab),seccion,idLab,idexamen,nOrden);
                    }else{
                        return `<span>${largoTexto(descripcion,11)}</span>`;
                    }
                   
                }
            },
            {
                targets: 7,
                orderable: false,
                render: function ( data, type, row ) {
                    return data === "S" ? "SI" : "NO";
                }
            },
            {
                targets: 8,
                
                render: function ( data, type, row ) {
                    let idexamen = row['ce_IDEXAMEN'];
                    let derivador = row['cderiv_IDDERIVADOR']
                    let idmuestra = row['dfet_IDMUESTRA'];
                    let descripcion = row['cderiv_DESCRIPCION'];
                    if(idmuestra===null ){
                        return cargarDerivadores(derivadores(),derivador,idexamen,nOrden);
                    }else{
                        return `<span>${largoTexto(descripcion,16)}</span>`;
                    }
                    
                }
            }
        ],
        /*initComplete: function () {
            const filas = tablaExamenes.rows().data();
            $("#ModalDatosOrdenDiagnostico").text(filas[0].cd_DESCRIPCION);
            $("#ModalDatosOrdenObs").text(filas[0].df_OBSERVACION);
        },*/
        
        "language": {
            url: "//cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json",
            select: {
                rows: {
                    _: "%d filas seleccionadas",
                    0: "",
                    1: "1 fila seleccionada"
                }
            }
        }
    });

    
  

}

$('#ModalDatosOrden').on('shown.bs.modal', function(e){
    $($.fn.dataTable.tables(true)).DataTable()
       .columns.adjust();
 })


// tablaExamenes.on( 'draw', function () {
//     tablaExamenes.columns.adjust().draw();
// } );




function salirDeEdicion(idExamn){
    $("#lblCentro"+idExamn).show();
    $("#selectCentro"+idExamn).hide();
    $("#lblLab"+idExamn).show();
    $("#selectLab"+idExamn).hide();
    $("#lblSecc"+idExamn).show();
    $("#selectSecc"+idExamn).hide();
    $("#btnCancelCentro"+idExamn).hide();
    $("#btnCancelLab"+idExamn).hide();
    $("#btnCancelSecc"+idExamn).hide();
}

$("#btnDocumentosOrden").click(function(){
    $("#modalDocumentos").modal("show");
    //$("#ModalDatosOrden").modal("hide");
    getDocumentosOrden(nroOrden);
});


function cargarCentro(centros,centro,idExamen) {
        let select = `<select id="selectCentro${idExamen}" class="form-control largo-select selectpicker" data-examen="${idExamen}" onchange="selectCambioLab(this,${idExamen},${nOrden})">`;
        select += '<option value="0" selected>SIN DERIVADOR</option>';
        centros.forEach((element) => {
        if (element.ccdsIdcentrodesalud == centro) {
            select += "<option value="+element.ccdsIdcentrodesalud+" selected>"+element.ccdsDescripcion+"</option>";
        } else {
            select += "<option value=" +element.ccdsIdcentrodesalud+">"+element.ccdsDescripcion+"</option>";
            }
        });
        
        select += '</select>';
        return select;
}

//entra en la funcion cuando hay un cambio en el select de centro
function selectCambioLab(e,idExamen,nOrden){
    let idCentro = e.value;
    let select = `<select id="selectLab${idExamen}" class="form-control largo-select" data-examen="${idExamen}" onchange="selectCambioSeccion(this,${idExamen},${nOrden})">`;
    select += '<option value="" selected>SELECCIONAR</option>';
    if(idCentro > 0){
        let labs = laboratorios(idCentro);
        labs.forEach((element) => {
            select += "<option value=" +element.clabIdlaboratorio+">"+element.clabDescripcion+"</option>";
        });
    }
        
    select += '</select>';
    $("#selectLab"+idExamen).html(select);

    let selectSecc = `<select id="selectLab${idExamen}" class="form-control largo-select" data-examen="${idExamen}" onchange="selectCambioSeccion(this,${idExamen},${nOrden})">
    <option value="">SELECCIONAR</option>
    </select>`;
    $("#selectSecc"+idExamen).html(selectSecc);


}

//Carga inicial de laboratorio
function cargarLab(labs,idLab,idExamen,nOrden) {
    let select = `<select id="selectLab${idExamen}" class="form-control largo-select" data-examen="${idExamen}" onchange="selectCambioSeccion(this,${idExamen},${nOrden})">`;
    select += '<option value="">SELECCIONAR</option>';
    labs.forEach((element) => {
    if (element.clabIdlaboratorio == idLab) {
        select += "<option value="+element.clabIdlaboratorio+" selected>"+element.clabDescripcion+"</option>";
    } else {
        select += "<option value=" +element.clabIdlaboratorio+">"+element.clabDescripcion+"</option>";
        }
    });
    
    select += '</select>';
    return select;
}

//entra en la funcion cuando hay un cambio en el select de lab
function selectCambioSeccion(e,idExamen,nOrden){
    let idLab = e.value;
    let select = `<select id="selectSecc${idExamen}" class="form-control largo-select" data-examen="${idExamen}" onchange="cambiarSeccion(this,${idExamen},${nOrden})>`;
    select += '<option value="" selected disabled>Seleccionar</option>';
    if(idLab > 0){
        let secs = secciones(idLab);
        secs.forEach((element) => {
            select += "<option value=" +element.csecIdseccion+">"+element.csecDescripcion+"</option>";
        });
    }        
    select += '</select>';
    $("#selectSecc"+idExamen).html(select);
}

//Carga inicial de seccion
function cargarSeccion(secciones,seccion,idLab,idExamen,nOrden) {
    let select = `<select id="selectSecc${idExamen}" class="form-control largo-select" data-examen="${idExamen}" onchange="cambiarSeccion(this,${idExamen},${nOrden})">`;
    if(idLab > 0){
        secciones.forEach((element) => {
            if (element.csecIdseccion == seccion) {
                select += "<option value="+element.csecIdseccion+" selected>"+element.csecDescripcion+"</option>";
            } else {
                select += "<option value=" +element.csecIdseccion+">"+element.csecDescripcion+"</option>";
                }
        });
    }        
    select += '</select>';
    return select;
}

// Guarda los cambios al momento de seleccionar una seccion
function cambiarSeccion(e, idExamen, nOrden){
    let idSeccion = e.value;
    if (idSeccion > 0){
        $.ajax({
            type: "post",
            url: "TomaMuestras",
            data: "cambiarInstitucion=1&idSeccion=" + idSeccion 
                    + "&idExamen=" + idExamen + "&nOrden=" + nOrden,
            datatype: "json",
            success: function () {
                $.notify({ message: "Se ha cambiado institución" }, { type: "success" });
                tablaExamenes.ajax.reload( null, false );
            },
            error: function (msg) {
                console.log(msg);
            }
        });
    } 
}


/* Derivador */
function cargarDerivadores(derivadores,derivador,idexamen,nOrden) {
        let select = `<select id="selectDeriv${idexamen}" class="form-control derivador largo-select" onchange="cambiarDerivador(this,${idexamen},${nOrden})" >`;
        select += '<option value="0" selected>SIN ESPECIFICAR</option>';
        derivadores.forEach((element) => {
        if (element.cderivIdderivador == derivador) {
            select += "<option value="+element.cderivIdderivador+" selected>"+element.cderivDescripcion+"</option>";
        } else {
            select += "<option value=" +element.cderivIdderivador+">"+element.cderivDescripcion+"</option>";
        }
        });
    
        select += '</select>';
        return select;
}

function cambiarDerivador(e, idExamn, nOrden){
    let derivador = e.value;
    
    $.ajax({
        type: "post",
        url: "TomaMuestras",
        data: "cambiarDerivador=1&idDerivador=" + derivador + "&idExamen=" + idExamn + "&nOrden=" + nOrden,
        datatype: "json",
        success: function () {
            cancelarCambioDerivador(idExamn);
            $.notify({ message: "Se ha cambiado derivador" }, { type: "success" });
            tablaExamenes.ajax.reload( null, false );
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}



function loadEnabledOrdenMedica(nOrden){
    let url = `${getctx}/api/${nOrden}/EstadoDocumento`;
    $.ajax({
      type: "GET",
      url: url,
      success: function(response) {
        if(response.status === 200 && response.dato === 'S'){
          $("#btnDocumentosOrden").prop('disabled', false);
        }else{
          $("#btnDocumentosOrden").prop('disabled', true);
        }
      },
      error: function(xhr, status, error) {
        $("#btnDocumentosOrden").prop('disabled', true);
      }
    });
  }