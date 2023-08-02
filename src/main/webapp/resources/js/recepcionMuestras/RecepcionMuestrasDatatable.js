var tableMuestras = null;
let usuario = $('select[name=dUsuario] option').filter(':selected').val();
const recepcionaMuestras = $("#recepcionaMuestras").val();
var dia = 0;
var mes = 0;
var year = 0;



let initTableMuestrasRM = function(){
    //$('#tablaMuestras thead tr').clone(true).appendTo('#tablaMuestras thead');
  tableMuestras = $('#tablaMuestras').DataTable({
        "ajax": {
            url: getctx + "/api/recepcionMuestras/muestras/"+dia+"/"+mes+"/"+year,
            type: "GET",
            "contentType": "application/json",
            processData:false,
            "dataSrc": function(response){
                let resultado = response.dato;
                if(resultado!= null || resultado.length !== 0){

                    resultado.sort(function(a, b) {
                        const fechaA = new Date(a.fechatm.replace(/(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3"));
                        const fechaB = new Date(b.fechatm.replace(/(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3"));
                        if (fechaA < fechaB) {
                          return -1;
                        }
                        if (fechaA > fechaB) {
                          return 1;
                        }
                        // Si las fechas son iguales, se ordenan por el código de barras
                        if (a.codigobarras < b.codigobarras) {
                          return -1;
                        }
                        if (a.codigobarras > b.codigobarras) {
                          return 1;
                        }
                        return 0;
                      });
                }

                return resultado;
            },
        },
        scrollY: calcularHeight('tablaMuestras','recep-botomcont-mid'),
        lengthChange: false,
        scrollCollapse: true,
        responsive: false,
        paging: false,
        searchDelay: 500,
        scrollX: true,
        info: false,
        // "language": {
        //     url: getctx + "/resources/js/recepcionMuestras/Spanish.json",
        //     select: {
        //         rows: {
        //             _: "%d filas seleccionadas",
        //             0: "",
        //             1: "1 fila seleccionada"
        //         }
        //     }
        // },
        "order": [],
        "language": {
            "info": "Mostrando _TOTAL_ órdenes ",
            "infoFiltered": "filtradas de un total de _MAX_ ",
            "loadingRecords": "Por favor espere - cargando ...",
            "emptyTable": "No hay datos disponibles",
            "zeroRecords": "No se encontraron datos",
          },
        "columns": [
            {data: 'codigobarras'},
            {data: 'fechatm',type:'html'},
            {data: 'muestradesc',type: 'span'},
            {data: 'envasedesc',type: 'span'},
            {data: 'estadorm'},
            {data: 'norden'},
            {data: 'usrreceptor',type:'select'},
            {data: 'observacionrm'},
            {data: 'derivador',type:'select'},
            {data: ''},
        ],
        // order: [],
        "columnDefs": [
            { orderable: false, targets: '_all'},
            { orderable: false, "targets"  : 'no-sort'},
            {
                targets:0,
                className: 'dt-left',
                render: function(data, type, row){
                  return data;
                }
            },
            {
                className: "dt-nowrap",
                targets:[1],
                render: function(data, type, row){
                   return `<div style="width:${t1*8}px">${nuevoFormato(data)}</div>`;
                }
            },{
                className: "dt-nowrap",
                targets:[2],
                render: function(data, type, row){
                    let title = data;
                        if(data != null && data.length > 10){
                            title = data.substr(0,25);
                        }else if(data == null){
                            return `<div style="width:${t1*13}px"></div>`;
                        }
                    return `<div class="muestra-table" title="${data}" style="width:${t1*13}px">${title}</div>`;
                }
            }
            ,{
                className: "dt-nowrap",
                targets:[3],
                render: function(data, type, row){
                    let title = data;
                        if(data != null && data.length > 10){
                            title = data.substr(0,24);
                        }else if(data == null){
                            return `<div style="width:${t1*13}px"></div>`;
                        }
                    return `<span class="envase-table" title="${data}" style="width:${t1*13}px">${title}</span>`;
                }
            }
            ,{
                targets: 4,
                render: function (data, type, meta) {
                    var status = {
                        R: {'title': 'Recepcionada', 'class': ' label-light-success'},
                        P: {'title': 'Pendiente', 'class': ' label-light-danger'}
                    };
                    if (typeof status[data] === 'undefined') {
                        return `<div style="width:${t1*9}px">${data}</div>`;
                    }
                    return `<div class="muestra-table" style="width:${t1*9}px"><span class="label label-md font-weight-bold${status[data].class} label-inline">${status[data].title}</span></div>`;
                }
            },
            {
                targets:[5],
                render: function(data, type, row){
                    return `<div style="width:${t1*5}px;text-align:center;">${data}</div>`;
                }
            },
            {
                className: "dt-nowrap",
                targets: 6,
                title: "Receptor",
                render: function(data, type, row){
                    if (row['estadorm'] == "R" && recepcionaMuestras === 'S'){
                        let title = data;
                        if(data.length > 10){
                            title = data.substr(0,10);
                        }

                       let usuario = row['idusrreceptor'];
                       let idMuestra = row['idmuestra'];

                        return cargarReceptores(CfgUsuariosR2.misUsuarios,data,row['idmuestra']);
                       
                    } else {
                        return `<span class="receptor-table block" style="width:${t1*11}px">${data}</span>`;
                    }
                }
            },{
                targets: 7,
                title: 'Observación',
                render: function(data, type, row) {
                    let title = data;
                    if(data.length > 19){
                        title = data.substr(0,20);
                    }
                    if (recepcionaMuestras === 'S'){
                        return `<div class="row" style="width:${t1*14}px">
                        <p id="txtObservacion${row['idmuestra']}" data-set="${row['idmuestra']}" title="${data}">${title}</p>
                        <a class="btnObsMuestra" class="p-2 ml-2">
                            <i class="ml-2 fas fa-pen" aria-hidden="true"></i>
                        </a>
                    </div>`
                    } else {
                        return `<div style="width:${t1*14}px" title="${data}">${title}</div>`;
                    }
                }
            },{
                className: "dt-nowrap",
                targets: 8,
                title: 'Derivador',
                render: function(data, type, row) {
                    if (recepcionaMuestras === 'S'){
                        let idDerivador = row['idderivador'];
                        let idMuestra = row['idmuestra'];
                        return cargarDerivadores(CfgDerivadores2.misDerivadores,idDerivador,idMuestra);
                    } else {
                        return data;
                    }
                }
            },{
                targets: 9,
                title: 'Acciones',
                className: "dt-center",
                render: function(data, type, row) {
                        let datos = JSON.stringify(row);

                        const pendienteEnabled = row.estadorm === "P" ? true : false;

                        return `<a href="#" id="" class="p-2 ${pendienteEnabled ? 'disabled' : ''}" title="Rechazo de Muestra" onclick="rechazarMuestra(${row['idmuestra']}) ">                                        
                                <i class="fas fa-hand-paper" aria-hidden="true" ${pendienteEnabled ? 'style="color:#858383 !important;"' : ''}></i><span class="nav-text"></span>                                    
                            </a>`;
                }
            },
        ],
        "search": {
            "smart": false
          },
        "rowCallback": function(row, data, index){
            //tacha el row completo si el examen está anulado
            if(data['allexamenesanulados']==='S'){
                $("td", row).css('text-decoration','line-through');
            }
        },
        "initComplete": function(settings, json) {
            $('body').find('.dataTables_scrollBody').addClass("scrollbar");
            let data = tableMuestras.row($('#tablaMuestras tbody tr:eq(0)')).data();
            if(typeof data !== "undefined"){
                let idMuestra = data['idmuestra'];
                let estado = data['estadorm'];
                seleccionarMuestra(idMuestra, estado);
                getMuestraById(idMuestra);
            }

            $.each($('#tablaMuestras tbody tr'), function(idx, val) {
                $(this).removeClass('muestra-blue');
              });

              $('#tablaMuestras tbody tr:eq(0)').addClass("muestra-blue");
              registros_muestras = tableMuestras.rows().count();
              let rows_filtroMue = tableMuestras.rows( {search:'applied'} ).count();
              DeleteAddClassdataTable('#tablaMuestras_wrapper',rows_filtroMue);

              let rowFilter = $('<tr class="filter"></tr>').appendTo($(tableMuestras.table().header()));
              this.api().columns().every(function() {
                let columna = this;
                genColFilterRecepcion(columna.index(), rowFilter);
              });
        
              let count = tableMuestras.rows( {search:'applied'} ).length;
            //  alert(count);
              if (count === 0){
                tableMuestras.clear().draw();
              }
        }
    });

    tableMuestras.on( 'draw', function () {
        registros_muestras = tableMuestras.rows().count();
        //console.log('total rows',registros_muestras);
        let rows_filtroMue = tableMuestras.rows( {search:'applied'} ).count();
        //console.log('rows count:', rows_filtroMue);
        DeleteAddClassdataTable('#tablaMuestras_wrapper',rows_filtroMue);
        $("#dt_registros_m").html("Mostrando <b>"+rows_filtroMue+"</b> de <b>"+registros_muestras+"</b> muestras en total");
    });
    
    
  
    
    //por defecto se filtran las órdenes en espera
    tableMuestras.column(4).search("").draw();
    $("#selectEstadoRM option[value='']").prop("selected", true);
    

    $('#filtroFechaRM').datepicker({
        language: 'es',
        endDate: "today",
        format: 'dd/mm/yyyy',
        autoclose: true
    }).change(function(){
        fecha = $('#filtroFechaRM').val();
        dia = fecha.substring(0, 2);
        mes = fecha.substring(3, 5);
        year = fecha.substring(6, 10);

        let newUrl = getctx + "/api/recepcionMuestras/muestras/"+dia+"/"+mes+"/"+year;
        tableMuestras.ajax.url(newUrl).load();
        limpiarMuestra();
    });
    
    $('#tablaMuestras tbody').on('dblclick', 'tr', function () {
        if (recepcionaMuestras === 'S'){
            let data = tableMuestras.row(this).data();
            //alert( 'You clicked on '+data['idmuestra']+' row' );
            let idMuestra = data['idmuestra'];
            let estado = data['estadorm'];
            let codBarras = data['codigobarras'];
            console.log(data);
            if (estado == "R"){
                $("#confirmVolverPendiente").modal("show");
                $("#idMuestraP").val(idMuestra);
                $("#codMuestraP").text(codBarras);
            } else {
                recepcionarMuestra(idMuestra);
            }
        }
    } );
    
    $('#tablaMuestras tbody').on('click', 'tr', function () {
        let data = tableMuestras.row(this).data();

        let idMuestra = data['idmuestra'];
        // Eliminar los primos que tienen la misma clase
        $("#txtCodigoBarras").removeClass("is-invalid");
        $(this).siblings(".muestra-blue").removeClass("muestra-blue");
        // agrega una clase a elemento elegido
        $(this).closest("tr").addClass("muestra-blue");

        let estado = data['estadorm'];
        seleccionarMuestra(idMuestra, estado);
        getMuestraById(idMuestra);
    });
    
    //limpiar filtro tabla ---- cambiar este limpiar
    $('#limpiarFiltroTabla').click(function(){
        $('.form-filter').val("");
        $('.txtFiltro').val("");
        $('.selectFiltro').val("");
        for (let i=0; i<9; i++){
            if (i!==4){
                tableMuestras
                    .column(i)
                    .search("", false, false)
                    .draw();
            } else {
                $("#selectEstadoRM option[value='']").prop('selected', true);
                tableMuestras.column(4).search("").draw();
            }
        }
        fecha = convertirDateDDMMYYYY(today);
        //console.log(fecha)
        $('#filtroFechaRM').val(fecha);
        dia = fecha.substring(0, 2);
        mes = fecha.substring(3, 5);
        year = fecha.substring(6, 10);
        let newUrl = getctx + "/api/recepcionMuestras/muestras/"+dia+"/"+mes+"/"+year;
        tableMuestras.ajax.url(newUrl).load();
        
    });
    
}

initTableMuestrasRM();


function recepcionarMuestra(idMuestra) {
    $.ajax({
        type: "patch",
        url: getctx + "/api/recepcionMuestras/recepcionaMuestra/"+idMuestra+"/"+usuario,
        datatype: "json",
        success: function (data) {
            if (data.status === 200) {
                handlerMessageOk(data.message);
            } else {
                handlerMessageError(data.message);
            }
            actualizar();
        },
        error: function (err) {
            handlerMessageError(err);
        }
    });
    
}

function guardarObservacion(idMuestra,valor){
    // var key = window.event.keyCode;
    // if (key === 13) {
        // var txtObservacion = $('#txtObservacion'+idMuestra).val();
        $.ajax({
            type: "patch",
            url : getctx + "/api/recepcionMuestras/observacion/save/"+idMuestra+"/"+valor,
            datatype: "json",
            success: function () {
                tableMuestras.ajax.reload(null, false);
            },
            error: function (msg) {
                console.log(msg);
            }
        });
         
        //return false;
    // } else {
    //     //esto es para mantener el textarea funcionando (mientras no se presione la tecla Enter
    //     return true;
    // }
    
}

function actualizar(){
    let src = getctx + "/resources/img/loader.gif";
    $('#tbodyPendientesFiltro').html("<div class='loading'><img src='"+src+"' alt='loading' /><br/>Actualizando...</div>");
    $("#tbodyPendientesFiltro").fadeIn(500).html("");
    //$('#tablaMuestras tbody').html("<div class='loading'><img src='"+src+"' alt='loading' /><br/>Actualizando...</div>");
    //$("#tablaMuestras tbody").fadeIn(500).html("");
    rellenarTablaMuestrasPendientes();
    rellenarTablaMuestrasRecepcionadas();
    rellenarTablaMuestrasConDerivador();
    tableMuestras.ajax.reload(null, false);
    
}

function genColFilter(colIndex, rowFilter) {

  switch (colIndex) {
    case 2:
    case 3:
      genColSelectFilter(colIndex, rowFilter);
      break;
    default:
      genNoFilter(colIndex, rowFilter);

  }
}

function genColSelectFilter(colIndex, rowFilter) {
  let inputSelect = genInputSelect(colIndex);
  $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
  $(`select[data-col-index="` + colIndex + `"]`).change(searchValue);
}

function searchValue(e) {
  let columna = $('#tablaMuestras').DataTable().column(this.dataset["colIndex"]);
  columna.search(this.value, true, false, false).draw();
}

function setOptionSelCol(colIndex) {

  switch (colIndex) {

    case 2:
      return setOptionMuestra(colIndex);
    case 3:
      return setOptionEnvases(colIndex);
    default:
      break;
  }
  return '<option value="">TODOS</option>';
}


function genInputSelect(colIndex) {
  return $(`<select class="form-control form-filter datatable-input" data-col-index="` + colIndex + `">` + setOptionSelCol(colIndex) + `</select>`);
}


function setOptionMuestra(colIndex) {

  function fillSelect(jqIdSelect) {
    function fill(data) {
      let opt = new Option('TODOS', '');
      $(jqIdSelect).append($(opt));
      data.forEach(mue => {
          opt = new Option(mue.cmueDescripcion, mue.cmueDescripcion);
        $(jqIdSelect).append($(opt));
      });
    }
    return fill;
  }

  let fillSelectMuestras = fillSelect(`select[data-col-index="` + colIndex + `"]`)
  getMuestras(cfgMuestras, fillSelectMuestras);
}


function setOptionEnvases(colIndex) {

  function fillSelectEnv(jqIdSelect) {
    function fill(data) {
        let opt = new Option('TODOS', '');
        $(jqIdSelect).append($(opt));
        data.forEach(env => {
           opt = new Option(env.cenvDescripcion, env.cenvDescripcion);
          $(jqIdSelect).append($(opt));
        });
    }
    return fill;
  }

  let fillSelectEnvases = fillSelectEnv(`select[data-col-index="` + colIndex + `"]`)
  getEnvases(cfgEnvases, fillSelectEnvases);
}

//se agrega el id -- debido a que tomaba todos los que eran button.btn-si
//$('button.btn-si').click(function(e){
$('#confirmVolverPendiente button.btn-si').click(function(e){
    let idMuestra = $("#idMuestraP").val();
    //const usuario = $('select[name=dUsuario] option').filter(':selected').val();
    $.ajax({
        type: "patch",
        url: getctx + "/api/recepcionMuestras/volverEstadoPendiente/"+idMuestra+"/"+usuario,
        datatype: "json",
        success: function (data) {
            if (data.status === 200) {
                handlerMessageOk(data.message);
            } else {
                handlerMessageError(data.message);
            }
            actualizar();
        },
        error: function (msg) {
            handlerMessageError(msg);
        }
    });
});

// $('button.btn-si').click(function(e){
//     let idMuestra = $("#idMuestraP").val();
//     //const usuario = $('select[name=dUsuario] option').filter(':selected').val();
//     $.ajax({
//         type: "patch",
//         url: getctx + "/api/recepcionMuestras/volverEstadoPendiente/"+idMuestra+"/"+usuario,
//         datatype: "json",
//         success: function (data) {
//             if (data.status === 200) {
//                 handlerMessageOk(data.message);
//             } else {
//                 handlerMessageError(data.message);
//             }
//             actualizar();
//         },
//         error: function (msg) {
//             handlerMessageError(msg);
//         }
//     });
// });


function selectCambioDerivador(idDerivador,idMuestra){
    $.ajax({
    type: "GET",
    url: getctx + "/api/dominio/derivadores/list",
    datatype: "json",
    success: function(data){
        let idSelect = "#selectDerivador"+idMuestra;
        $("#lblDerivador"+idMuestra).hide();
        $("#selectDerivador"+idMuestra).show();
        $("#btnCancelar"+idMuestra).show();
        $(idSelect).empty();
        for (variable of data) {
            if (variable.cderivIdderivador == idDerivador){
                $(idSelect).append('<option value='+variable.cderivIdderivador+' selected>'+variable.cderivDescripcion+'</option>'); 
            } else {
                $(idSelect).append('<option value='+variable.cderivIdderivador+'>'+variable.cderivDescripcion+'</option>'); 
            }
        }
        $("#spnUsuario"+idMuestra).append('<button class="btn" onclick=\'cancelarCambioDerivador("'+idMuestra+'")\'><i class="far fa-times-circle"></i></button>');
    },
    error: function (msg) {
        console.log(msg);
    }
    });
}



function cancelarCambioDerivador(idMuestra){
    $("#lblDerivador"+idMuestra).show();
    $("#selectDerivador"+idMuestra).hide();
    $("#btnCancelar"+idMuestra).hide();
}

function cambiarDerivador(e, idMuestra){
    let derivador = e.value;
    $.ajax({
        type: "patch",
        url: getctx + "/api/recepcionMuestras/derivador/update/"+idMuestra+"/"+derivador,
        datatype: "json",
        success: function () {
            //cancelarCambioDerivador(idMuestra);
            //$.notify({ message: "Se ha cambiado derivador" }, { type: "success" });
            actualizar();
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}
/*
function selectCambioUsuario(usuario,idMuestra){
    $.ajax({
        type: "GET",
        url: getctx + "/api/recepcionMuestras/recepcionistas/list",
        datatype: "json",
        success: function(data){
            $("#lblUsuario"+idMuestra).hide();
            $("#selectUsuario"+idMuestra).show();
            $("#btnCancelarU"+idMuestra).show();
            let idSelect = "#selectUsuario"+idMuestra;
            $(idSelect).empty();
            for (let receptor of data) {
                if (receptor.nombre == usuario){
                    $(idSelect).append('<option value='+receptor.id+' selected>'+receptor.nombre+'</option>'); 
                } else {
                    $(idSelect).append('<option value='+receptor.id+'>'+receptor.nombre+'</option>'); 
                }
            }
        },
        error: function (msg) {
            console.log(msg);
        } 
    });
}
*/
function cancelarCambioUsuario(idMuestra){
    $("#lblUsuario"+idMuestra).show();
    $("#selectUsuario"+idMuestra).hide();
    $("#btnCancelarU"+idMuestra).hide();
}

function cambiarUsuario(f, idMuestra){
    let usuarioRecepciona = f.value;
    console.log("usuario recepciona ")
    console.log(usuarioRecepciona)
    $("#receptorMuestra").modal();
    $("#receptorMuestra .btn-si").click(function(){
        $.ajax({
            type: "patch",
            url: getctx + "/api/recepcionMuestras/receptor/update/"+idMuestra+"/"+usuarioRecepciona,
            datatype: "json",
            success: function (data) {
                actualizar();
                if (data.status===200){
                    handlerMessageOk(data.message);
                } else {
                    handlerMessageError(data.message);
                }
            },
            error: function (msg) {
                handlerMessageError(msg);
                console.log(msg);
            }
        });
    });
    $("#receptorMuestra .btn-no").click(function(){
        actualizar();
    });
}


function recepcionarMuestraCodBarra(codigoBarras) {
    $.ajax({
        type: "patch",
        url: getctx + "/api/recepcionMuestras/recepcionaMuestra/codigoBarras/"+codigoBarras+"/"+usuario,
        datatype: "json",
        success: function (data) {
            if(data.status === 200){
                handlerMessageOk(data.message);
                $("#txtCodigoBarras").removeClass("is-invalid");
                getMuestraById(data.dato);
                $("#txtCodigoBarras").val("");
            } else if (data.status === 409){
                handlerMessageWarning(data.message);
                $("#txtCodigoBarras").removeClass("is-invalid");
                $("#txtCodigoBarras").val("");
            }else if (data.status === 401){ 
                if(data.message === "Muestra no disponible para recepción"){
                    // abrir modal y mostrar muestra no tomada para el usuario
                    $("#muestraNoTomada").modal("show");
                    $("#codMuestraNoTomada").text(codigoBarras);
                }else{
                    handlerMessageError("Muesta no identificada");
                }
                $("#txtCodigoBarras").removeClass("is-invalid");
                $("#txtCodigoBarras").val("");
            }
             else{
                handlerMessageError("Muesta no identificada");
                $("#txtCodigoBarras").addClass("is-invalid");
            }
            actualizar();
        },
        error: function (err) {
            handlerMessageError(err);
        }
    });
}

function rechazarMuestra(idMuestra){
    setWithExpiry("idMuestra", idMuestra, 100000);

    $("#confirmModal").modal();
    $("#confirmModal").find(".modal-title").html("Rechazo de muestra");
    $("#confirmModal").find(".modal-body").html("<p>La muestra seleccionada será rechazada, ¿desea continuar?</p>");

    $("#confirmModal .btn-si").click(function(){
        $.ajax({
            type: "get",
            url: getctx+"/api/recepcionMuestras/datosOrden/"+idMuestra,
            datatype: "json",
            success: function (muestra) {
                let nOrden = muestra.norden;
                setWithExpiry("nOrden", nOrden, 100000);
                window.location.href = getctx + "/RechazoMuestras";
            },
            error: function (msg) {
                console.log(msg);
            }
        });
    });
  
}


function limpiarMuestra(){
    $("#ulExamenes").empty();
    $("#txtNombrePaciente").text("");
    $("#txtSexoPaciente").text("");
    $("#txtEdadPaciente").text("");
    $("#txtTipoAtencion").text("");
    $("#labelLocalizacion").html("");
    $("#txtLocalizacion").text("");
    $("#txtNOrden").text("");
    $("#txtFechaOrden").text("");
    $("#txtObservacionOrden").text("");
    $("#txtFechaTM").text("");
    $("#txtObservacionTM").text("");
    $("#tbodyRecepcionadasFiltro").find("tr.muestra-blue").removeClass("muestra-blue");
    $("#tbodyPendientesFiltro").find("tr.muestra-blue").removeClass("muestra-blue");
    $("#tbodyFiltro").find("tr.muestra-blue").removeClass("muestra-blue");
    $("#txtCodigoBarras").removeClass("is-invalid");
}

function seleccionarMuestra(idMuestra, estado){
   let tabla = "";
    if(estado === "R"){
        tabla = $("#tbodyRecepcionadasFiltro");
    }else if(estado === "P"){
        tabla = $("#tbodyPendientesFiltro");
    }
    $("#tbodyRecepcionadasFiltro").find("tr.muestra-blue").removeClass("muestra-blue");
    $("#tbodyPendientesFiltro").find("tr.muestra-blue").removeClass("muestra-blue");
    tabla.find("tr").find(".idMuestra:contains('"+idMuestra+"')").parent().addClass("muestra-blue");
}

// $('#tablaMuestras tbody').on('click','tr', function (e) {
//     let text = "";
//     let id = "";
//     let dataset = "";
//     if(!$(e.target).is("input") && $(e.target).is("td") && $(e.target).closest("input").length === 0 && $(e.target).find("input").length === 0){
//         text = $(e.target).find("p").text();
//         id = $(e.target).find("p").attr('id');
//         dataset =  $(e.target).find("p").data("set");
//         cambiarEstadoParrafo(text,id);
//     }else if(!$(e.target).is("input") && $(e.target).is("p")){
//         text = $(e.target).text();
//         id = $(e.target).attr('id');
//         dataset =  $(e.target).data("set");
//         cambiarEstadoParrafo(text,id);
//     }
  
// });

$('#tablaMuestras').on('click','.btnObsMuestra', function (e) {
    let dataset = "";
    let id = $(this).siblings("p").attr('id');
    let text = $(this).siblings("p").attr('title');
    cambiarEstadoParrafobutton(text,id);
});


$('#tablaMuestras tbody').on('blur', 'input', function (e) {
    // if(e.keyCode === 13){
        let input = e.target;
        let value = $(input).val();
        $(input).prop('disabled', true);
        guardarObservacion($(input).parent().data("set"),value);
    // }
});

/* Desarrollo nuevo */

function cargarReceptores(usuarios,usuario,idMuestra) {
    let select = '';
    let option = '';
    // if(usuarios === undefined || usuarios != null){return ''};
    usuarios.forEach((element) => {
    if (element.duNombres+" "+element.duPrimerapellido == usuario) {
        option += "<option value="+element.duIdusuario+" selected>"+element.duNombres+" "+element.duPrimerapellido+"</option>";
    } else {
        option += "<option value=" +element.duIdusuario+">"+element.duNombres+" "+element.duPrimerapellido+"</option>";
    }
});
 select = `<span id="spnUsuario${idMuestra}" title="${usuario}">
 <select id="selectUsuario${idMuestra}" class="form-control receptor-table" onchange="cambiarUsuario(this,${idMuestra})" style="width:${t1*11}px">`;
    select += option;
select += '</select></span>';
return select;
}

function cargarDerivadores(derivadores,derivador,idMuestra) {
    let select = `<span id="spnDerivador${idMuestra}">
    <select id="selectDerivador${idMuestra}" class="form-control derivador" onchange="cambiarDerivador(this,${idMuestra})" style="width:${t1*11}px">`;
    select += '<option value="0" selected>SIN DERIVADOR</option>';
    derivadores.forEach((element) => {
    if (element.cderivIdderivador == derivador) {
        select += "<option value="+element.cderivIdderivador+" selected>"+element.cderivDescripcion+"</option>";
    } else {
        select += "<option value=" +element.cderivIdderivador+">"+element.cderivDescripcion+"</option>";
    }
});

select += '</select></span>';
return select;

}



// $( document ).ready(function() {
//     const UP = 38;
//     const DOWN = 40;
//     const ENTER = 13;
//     const ARROWS = [UP, DOWN,ENTER];
//     const HIGHLIGHT = 'muestra-blue';
//    $('body').on('keydown', function(e) {
//     if(["ArrowUp","ArrowDown"].indexOf(e.code) > -1) {
//                 e.preventDefault();
//             }
//     let $table = $('#tablaMuestras');
//     let key = e.which;
//     if (ARROWS.includes(key) && !$(e.target).is('input')) {
//         let data;
//         let selectedRow = -1;
//         let $rows = $table.find('tbody tr:not(":hidden")');
//         $rows.each(function(i, row) {
//             if ($(row).hasClass(HIGHLIGHT)) {
//                 selectedRow = i;
//             }
//         });
//         if (key == UP && selectedRow > 0) {
//         $rows.removeClass(HIGHLIGHT);
//         $rows.eq(selectedRow - 1).addClass(HIGHLIGHT);
//             if($rows.eq(selectedRow - 1).is('tr')){
//                 $table.parent().scrollTop($rows.eq(selectedRow - 1).position().top);
//                 data = tableMuestras.row($rows[selectedRow - 1]).data();
//             }
//         } else if (key == DOWN && selectedRow < $rows.length - 1) {
//         $rows.removeClass(HIGHLIGHT);
//         $rows.eq(selectedRow + 1).addClass(HIGHLIGHT);
//             if($rows.eq(selectedRow + 1).is('tr')){
//                 $table.parent().scrollTop($rows.eq(selectedRow + 1).position().top);
//                 data = tableMuestras.row($rows[selectedRow + 1]).data();
//             }
//         }
        
//         if(typeof data !== "undefined" && key != ENTER){
//             let idMuestra = data['idmuestra'];
//             let estado = data['estadorm'];
//             seleccionarMuestra(idMuestra, estado);
//             getMuestraById(idMuestra);
//         }else if($($rows[selectedRow]).hasClass(HIGHLIGHT) && typeof data !== "undefined" && key == ENTER){
//             let data = tableMuestras.row($rows[selectedRow]).data();
//             let idMuestra = data['idmuestra'];
//             let estado = data['estadorm'];
//             let codBarras = data['codigobarras'];
//             if (estado == "R"){
//                 $("#confirmVolverPendiente").modal("show");
//                 $("#confirmVolverPendiente").find('.btn-si').focus();
//                 $("#idMuestraP").val(idMuestra);
//                 $("#codMuestraP").text(codBarras);
//             } else if($($rows[selectedRow]).hasClass(HIGHLIGHT)) {
//                 recepcionarMuestra(idMuestra);
//             }
//         }
    
//     }
    
// });
  
// });


//$("#selectUsuario").html(cargarReceptores(usuarios(),usuario),0);

const tableNav = new TableNavigation("tablaMuestras",selectCausaAuto,enterMuestras);
function selectCausaAuto(a = null) {
    let $row;
    if(a == null){
        let tabla = document.getElementById("tablaMuestras");
        let tbody = tabla.querySelector("tbody");
        let primerTR = tbody.querySelector("tr:first-child");
        $row = primerTR;
    }else{
        $row = a;
    }
    let data = tableMuestras.row($row).data();
    console.log(data);
    let idMuestra = data['idmuestra'];
    let estado = data['estadorm'];
    seleccionarMuestra(idMuestra, estado);
    getMuestraById(idMuestra);
}

function enterMuestras(a = null){
    let $row;
    if(a == null){
        let tabla = document.getElementById("tablaMuestras");
        let tbody = tabla.querySelector("tbody");
        let primerTR = tbody.querySelector("tr:first-child");
        $row = primerTR;
    }else{
        $row = a;
    }

    let data = tableMuestras.row($row).data();
    let idMuestra = data['idmuestra'];
    let estado = data['estadorm'];
    let codBarras = data['codigobarras'];
    if (estado == "R"){
        $("#confirmVolverPendiente").modal("show");
        $("#confirmVolverPendiente").find('.btn-si').focus();
        $('button.btn-si').unbind('click',volverEstado);
        $('button.btn-si').bind('click',volverEstado);
        
        $("#idMuestraP").val(idMuestra);
        $("#codMuestraP").text(codBarras);
    } else{
        recepcionarMuestra(idMuestra);
    }
}



function genColFilterRecepcion(colIndex, rowFilter) {

    switch (colIndex) {
      case 0:
        genColInputFilter(colIndex, rowFilter,4);
        break;
      case 1:
        genColDateFilter(colIndex, rowFilter,8);
        break;
      case 2:
        genNoFilter(colIndex, rowFilter,13);
        break;
      case 3:
        genNoFilter(colIndex, rowFilter,13);
        break;
      case 4:
        genColSelectFilter(colIndex, rowFilter,9);
        break;
      case 5:
        genColInputFilter(colIndex, rowFilter,5);
        break;
      case 6:
        genColInputFilter(colIndex, rowFilter,11);
        break;
      case 7:
        genNoFilter(colIndex, rowFilter,11);
        break;
      case 8:
        genColInputFilter(colIndex, rowFilter,11);
        break;
      case 9:
        genLimpiar(colIndex, rowFilter,3);
        break;
    }
  }



  function genColInputFilter(colIndex, rowFilter,size) {
    let inputText = $(`<input type="text" class="form-control form-control-sm form-filter datatable-input" data-col-index="` + colIndex + `" style="width:${t1*size}px"/>`);
    $(inputText).appendTo($('<th>').appendTo(rowFilter));
    $(`input[data-col-index="` + colIndex + `"]`).on('keyup change clear', searchKey);
  }
  

  function genColDateFilter(colIndex, rowFilter,size) {

  
    let inputText = $(`
                    <div class="input date" style="width:${t1*size}px">
                    <input type="text" class="form-control form-control-sm datatable-input" data-date-format="dd/mm/yyyy" placeholder="" id="filtroFechaRM"
                       data-col-index="` + colIndex + `"/>
                    </div>
                    `);
    $(inputText).appendTo($('<th>').appendTo(rowFilter));
    $('#filtroFechaRM').datepicker({
      todayHighlight: true,
      orientation: "bottom left",
      language: 'es',
      endDate: "today",
      format: 'dd/mm/yyyy',
      autoclose: true,
    });
    let today = new Date();
    fecha = convertirDateDDMMYYYY(today);
    $('#filtroFechaRM').val(fecha);
    $('#filtroFechaRM').datepicker()
    .on('changeDate', function(e) {
    fecha = $('#filtroFechaRM').val();
    dia = fecha.substring(0, 2);
    mes = fecha.substring(3, 5);
    year = fecha.substring(6, 10);

    let newUrl = getctx + "/api/recepcionMuestras/muestras/"+dia+"/"+mes+"/"+year;
    tableMuestras.ajax.url(newUrl).load();
    limpiarMuestra();
    });


   
  }
  
  function genNoFilter(colIndex, rowFilter,size = 6) {
    let inputText = $(`<div style="width:${t1*size}px"></div>`);
    $(inputText).appendTo($('<th>').appendTo(rowFilter));
  }


  function genColSelectFilter(colIndex, rowFilter,size = 6) {
    let inputSelect =  $(`
    <select class="form-control form-control-sm form-filter datatable-input" data-col-index="` + colIndex + `" style="width:${t1*size}px">
        <option value="">TODOS</option>
        <option value="Recepcionada">RECEPCIONADA</option>
        <option value="Pendiente">PENDIENTE</option>
    </select>`);
    $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
    $(`select[data-col-index="` + colIndex + `"]`).change(searchValue);
  }


  function genLimpiar(colIndex, rowFilter) {
    let inputLimpiar = $(`<span class="symbol symbol-30 symbol-circle">
                            <span id="limpiarFiltroTabla" data-toggle="tooltip"
                                title="Limpiar"
                                class="symbol-label bg-hover-blue ">
                                <i id=""
                                    class="la la-broom icon-xl  text-blue"></i></span>
                        </span>`);
    $(inputLimpiar).appendTo($('<th>').appendTo(rowFilter));
    $("#limpiarFiltroTabla").click(limpiarFiltroRecepcion);
  }


  function limpiarFiltroRecepcion() {
    resetColSelectFilter();
    resetColInputFilter();
    resetColDateFilter();
  }
  
  function resetColSelectFilter() {
    const selCols = [4];
    selCols.forEach((colIndex) => {
      $(`select[data-col-index="` + colIndex + `"]`).val("");
      $(`select[data-col-index="` + colIndex + `"]`).trigger("change")
    });
  }
  
  function resetColInputFilter() {
    const inputCols = [0, 5, 6, 8];
    inputCols.forEach((colIndex) => {
      $(`input[data-col-index="` + colIndex + `"]`).val("");
      $(`input[data-col-index="` + colIndex + `"]`).trigger("change")
    });
  }
  
  function resetColDateFilter() {
    const dateCols = [1];
    dateCols.forEach((colIndex) => {
      $(`input[data-col-index="` + colIndex + `"]`).val("");
      $(`input[data-col-index="` + colIndex + `"]`).trigger("change")
    });
  }

  function searchKey(e) {
    let columna = $('#tablaMuestras').DataTable().column(this.dataset["colIndex"]);
    columna.search(e.target.value.toUpperCase(), true, false, false).draw();
  }


  