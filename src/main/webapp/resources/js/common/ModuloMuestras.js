class MuestraList{
  
  
  jqId;
  tableMuestras;
  currentorden;
  isFlebotomista;
  constructor(){
    
    this.jqId = "#tablaMuestras";
    this.currentorden = -1;
    this.isFlebotomista = 'N';

  }
  
  loadMuestraorden(nOrden){
    
    if (this.currentorden === -1)
    {
      this.currentorden = nOrden;
      this.initDataTable();    
    }else{
      this.currentorden = nOrden;
      let url = gCte.getctx + "/api/tomaMuestras/muestras/"+this.currentorden;
      this.tableMuestras.ajax.url(url).load().draw();
      
    }
    
    
  }
  
  initDataTable(){
    
    let isFlebotomista = this.isFlebotomista;
      this.tableMuestras = $(this.jqId).DataTable({
       paging: false,
       searchDelay: 500,
       orderCellsTop: true,
       scrollY: 300,
       scrollX: true,
       info: false,
       "autoWidth": false,
        ajax: {
                url: gCte.getctx + "/api/tomaMuestras/muestras/"+this.currentorden,
                type: "POST",
                contentType: "application/json",
                dataSrc:function(response){ 
                    let respuesta = response.dato;
                    if(respuesta!= null){
                        respuesta.sort(function(a, b) {
                            if (a.codigobarras < b.codigobarras) return -1;
                            if (a.codigobarras > b.codigobarras) return 1;
                            return 0;
                          });
                    }
                    return respuesta
                },
                dataType: "json"
        },
        columns: [
                {data: 'Seleccion', responsivePriority: -2,"width": "5%"},
                {data: 'muestradesc',"width": "8%"},
                {data: 'estadotm', type: "select","width": "8%"},
                {data: 'envasedesc',"width": "10%"},
//                {data: 'usrflebotomista', type: "select"},
                {data: 'codigobarras',"width": "8%"},
                {data: 'observaciontm',"width": "61%"},
//                {data: 'escurvatolerancia'},
//                {data: 'Microbiología'},
//                {data: 'Botones'}
        ],
        //aquí defino el formato de las columnas
        columnDefs: [
            { orderable: false, targets: '_all' },
            {
                //Selección
                targets: 0,
                 width: '30px',
                 className: 'dt-center',
               //  orderable: false,
                 render: function(data, type, row, meta) {
                   return `<input type="checkbox" data-colselector-test="x" value="" class="checkable"/> `;
                 },
               },
                // {
                //     //Selección
                //     targets: 0,
                //     title: 'Sel.',
                //     className: 'select-checkbox notChildRow',
                //     data: null,
                //     defaultContent: ''
                // },
                {
                    targets: 1,
                    render: function (data, type, meta) {
                        return `<div style="width:${t1*10}px">${data}</div>`;
                    }
                },
                {
                    //Estado
                    targets: 2,
                    render: function ( data, type, row ) {
                        let bgColor = "";
                        let estado = "";
                        switch(data.trim()) {
                            case 'TOMADA':
                              bgColor = "#ACECD6";
                              estado = "TOMADA";
                              break;
                            case 'PENDIENTE':
                              bgColor = "#FF9AA2";
                              estado = "PENDIENTE";
                              break;
                            case 'BLOQUEADA':
                              bgColor = "#DFC7C1";
                              estado = "BLOQUEADA";
                              break;
                            case 'RECHAZADA':
                              bgColor = "#FFF9AA";
                              estado = "RECHAZO PARCIAL";
                              break;
                            default:
                              bgColor = "#FF9AA2";
                              estado = "";
                              break;
                        }
                        if (estado===""){
                            return `
                                <div style="width:${t1*10}px">
                                    <span style="background-color:${bgColor}" title="${data}" class="label label-lg font-weight-bold label-inline" >
                                    PENDIENTE</span>
                                </div>`;
                        //Cuando la muestra está rechazada o está tomada y recepcionada
                        } else if (estado === "RECHAZADA" || (estado==="TOMADA" && row['estadorm']==="R")) {
                            return `
                            <div style="width:${t1*10}px">
                                <span style="background-color:${bgColor}" title="${data}" class="label label-lg font-weight-bold label-inline">
                                ${estado}
                                </span>
                            </div>
                            `;
                        } else {
                            if (isFlebotomista === 'S'){
                                return cargarEstadosMuestras(estado,row['idmuestra']);
                               
                            } else {
                                return `
                                <div style="width:${t1*10}px">
                                    <span id="lblEstadoMuestra${row['idmuestra']}" title="${data}" style="background-color:${bgColor}" class="label label-lg font-weight-bold label-inline">
                                    ${estado}</span>
                                </div>
                                `;
                            }
                                
                        }
                    }
                },
                {
                    targets: 3,
                    render: function (data, type, meta) {
                        return `<div style="width:${t1*11}px">${data}</div>`;
                    }
                },
                {
                    targets: 4,
                    render: function (data, type, meta) {
                        return `<div style="width:${t1*7}px">${data}</div>`;
                    }
                },
                /*
                {
                    //Flebotomista
                    targets: 4,
                    className: 'notChildRow',
                    render: function ( data, type, row ) {
                        if (row['idmuestra']!==null){
                            if (isFlebotomista === 'S'){
                                return cargarFlebotomistaData(flebotomistaData(),data,nOrden,row['idmuestra']);
                            } else {
                                return '<span id="lblUsuario'+row['idmuestra']+'" class="label label-lg font-weight-bold label-inline">\
                                        ' + data + '</span>';
                            }
                        } else {
                            return "";
                        }
                    }
                },
                */{
                    //Observación
                    targets: 5,
                    className: 'notChildRow',
                    render: function ( data, type, row ) {
                        if (row['idmuestra']!==null){
                            if (isFlebotomista === 'S'){
                                return `<p id="txtObsMuestra${row['idmuestra']}" data-set="${row['idmuestra']}" style="width:${t1*20}px">${data.trim()}</p>`
 //                               return '<input type="text" id="txtObsMuestra'+row['idmuestra']+'" value="'+data.trim()+'" onchange="guardarObservacionMuestra(' + row['idmuestra'] + ')">';
                            } else {
                                return `<div style="width:${t1*20}px">${data.trim()}</div>`;
                            }
                        }
                        return `<div style="width:${t1*20}px">${data}</div>`;
                    }
                },
                /*{
                    //Curvas
                    targets: 7,
                    className: 'notChildRow',
                    render: function ( data, type, row ) {
                        let htmlCurva = "";
                        if (row['idmuestra']!==null){
                            if (data==="S"){
                                htmlCurva = '<a href="#" id="curvaTolerancia" class="p-2" title="Curva Tolerancia" data-toggle="modal" data-target="#modalCurvaTolerancia" onclick=\'cargarCurvasTolerancia('+nOrden+','+row['idmuestra']+')\' >\
                                            <i class="far fa-clock"></i><span class="nav-text"></span>\
                                        </a>';
                            }
                        }
                        return htmlCurva;
                    }
                },{
                    //Microbiología
                    targets: 8,
                    "className": 'dt-microbiologia notChildRow',
                    render: function ( data, type, row ) {
                        let datos = JSON.stringify(row)
                        return '\<div class="row">\
                                        <a href="#" id="dt_tipomuestra" class="p-2" title="Tipo de Muestra: '+ row['muestradesc'] +'" data-toggle="modal" data-target="#modalDetalleMuestra" onclick=\'modalTipoMuestra('+datos+'); mostrarDatosPacienteOrden('+ nOrden +') \'>\
                                            <i class="fas fa-lightbulb"></i><span class="nav-text"></span>\
                                        </a>\
                                        <a href="#" id="" class="p-2" title="'+ (row['zonacuerpo']!==null ? "Zona de cuerpo: "+row['zonacuerpo'] : "Sin zona de cuerpo") +'" data-toggle="modal" data-target="#modalZonaCuerpo" onclick=\'modalZonaCuerpo('+row['idmuestra']+','+row['idzonacuerpo']+'); mostrarDatosPacienteOrden('+ nOrden +') \'>\
                                            <i class="fas fa-child"></i><span class="nav-text"></span>\
                                        </a>\
                                    </div>';
                    }
                },{
                    //Acciones
                    targets: 9,
                    title: 'Acciones',
                    orderable: false,
                    className: 'notChildRow',
                    render: function(data, type, row, meta) {
                        let datos = JSON.stringify(row);
                        return '\<div class="row">\
                                    <a href="#" id="" class="p-2" title="Indicaciones TM" data-toggle="modal" data-target="#exampleModal4" onclick=\'MostrarIndicaciones(' + datos + ') \'>\
                                        <i class="fas fa-user-md"></i><span class="nav-text"></span>\
                                    </a>'
                                    + (row['estadorm']!=="R" && (isFlebotomista === 'S') ? 
                                    '<a href="#" id="" class="p-2" title="Rechazo de Muestra" data-toggle="modal" data-target="#exampleModal5" onclick=\'rechazarMuestra(' + nOrden + ', ' + row['idmuestra'] + ') \'>\
                                        <i class="fas fa-hand-paper"></i><span class="nav-text"></span>\
                                    </a>\
                                </div>' : "");
                    }
                }
                */
        ],
 /*
        "drawCallback": function() {
            //esto es para habilitar o deshabilitar botón de impresión de etiquetas
            const filas = this.tableMuestras.rows().data();
            const n = filas.count();
            let habilitarBotones = false;
            for (i=0; i<n ; i++){
                let codigoBarra = filas[i].codigobarras;
                if (codigoBarra === ""){
                    habilitarBotones = true;
                }
            }
            if (habilitarBotones === true){
                $('#btnImpresionEtiquetas').removeAttr('disabled');
            }
        },
*/
        select: {
            style:    'multi',
            selector: 'td:first-child',
            "style": 'multiple',
            "className": 'selected',
            //selector: 'td:first-child input[type="checkbox"]'
        },
        /*
        createdRow: function(){
            const registros = this.tableMuestras.rows().count();
            $("#dt_registros_m").html("Muestras: <b>"+registros+"</b>");
            //función para filtrar tablas de muestra y envase
            this.tableMuestras.api().columns([1]).every( function () {
                var column = this;
                var select = $('<select class="form-control form-control-sm p-1 selectFiltroMue"><option value="">TODOS</option></select>')
                    //.appendTo( $(column.header()).empty())
                    .appendTo( $("#filtroMuestra").empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );

                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );

                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
            this.tableMuestras.api().columns([2]).every( function () {
                var column = this;
                var select = $('<select class="form-control form-control-sm p-1 selectFiltroMue"><option value="">TODOS</option></select>')
                    .appendTo( $("#filtroEnvase").empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );

                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );

                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        },
        */
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


  getRowsSelected(){
    
    
    
  }
}

$("#tablaMuestras thead tr:eq(1) th").each( function ( i ) {
    if (i === 0){
        $(this).html(`<input type="checkbox" name="" id="seleccionaTodasLasMuestras" class="checkable" style="width:${t1*5}px"/>`);
        $(this).addClass('dt-center');
    }
    else if (i===1){
        $(this).html( `<div style="width:${t1*10}px"></div>`);
    }
    else if (i===2){
        $(this).html( `<div style="width:${t1*10}px"></div>`);
    }
    else if (i===3){
    $(this).html( `<div style="width:${t1*11}px"></div>`);
    }
    else if (i===4){
        $(this).html( `<div style="width:${t1*7}px"></div>`);
    }
    else if (i===5){
        $(this).html( `<div style="width:${t1*20}px"></div>`);
    }
} );

$("#seleccionaTodasLasMuestras").on( "click", function(e) {
    console.log(this);
    let table = $("#tablaMuestras").DataTable();
    if ($(this).is(":checked")){
        table.rows().select();
        $('#tablaMuestras tbody').find(':checkbox').attr('checked', true);
    } else {
        table.rows().deselect(); 
        $('#tablaMuestras tbody').find(':checkbox').attr('checked', false);
    }

});

/*
    $("#tablaMuestras thead tr:eq(1) th").each( function ( i ) {
        if (i === 0){
            $(this).html('<input type="checkbox" name="" id="seleccionaTodasLasMuestras" class="checkable" />\
                                    ');
            $(this).addClass('dt-center');
        } else if (i===3){
            //select para estado
            var select = $('<select id="estadoPaciente" class="form-control form-control-sm p-1 selectFiltroMue"><option value="">TODOS</option></select>')
                 .appendTo($(this).empty())
                 .on('change', function () {
                     var term = $(this).val();

                    $.fn.dataTable.ext.search.push(
                        function( settings, data, dataIndex ) {
                            if ( settings.nTable.id !== 'tablaMuestras' ) {
                                return true;
                              }
                            var selected_rarities = [];
                            let text = (($(data[i]).is('select') && $('#estadoPaciente').val() == $(data[i]).find(":selected").text()) || ($(data[i]).is('span') && $('#estadoPaciente').val() == $(data[i]).text().trim()));
                            
                            console.log(data);
                            if($('#estadoPaciente').val() != "" && text){
                                selected_rarities.push(1);
                            }else if($('#estadoPaciente').val() == ""){
                                selected_rarities.push(1);
                            }
                            if(selected_rarities.length !== 0) {
                                return true;
                            }else{
                                return false;
                            }
                        }
                      );
                      tableMuestras.draw();
                    //  tableMuestras.column(i).search(term, false, false).draw();
                 });
            select.append('<option value="TOMADA">TOMADA</option>');
            select.append('<option value="PENDIENTE">PENDIENTE</option>');
            select.append('<option value="BLOQUEADA">BLOQUEADA</option>');
        }
        else if (i === 4) {
            let title = $(this).text();

            
            $.fn.dataTable.ext.search.push(
                function( settings, data, dataIndex ) {
                    let selected_rarities = [];
                    let textoption = (($(data[i]).is('select') && $(data[i]).find(":selected").text().trim().toLowerCase()) || ($(data[i]).is('span') && $(data[i]).text().trim().toLowerCase()));

                    if ( settings.nTable.id !== 'tablaMuestras' ) {
                        return true;
                      }
                    if($("#searchFlebo").val() == ""){
                        selected_rarities.push(1);
                    }else if($("#searchFlebo").val()!= ""){
                        substrRegex = new RegExp($("#searchFlebo").val(), 'i');
                        if(substrRegex.test(textoption)){
                            selected_rarities.push(1);
                        }
                    }
                    if(selected_rarities.length !== 0) {
                        return true;
                    }else{
                        return false;
                    }
                }
              );
            $(this).html('<input type="text" id="searchFlebo" class="form-control form-control-sm p-1 txtFiltroMue" placeholder="Buscar '+title+'" />');


          //redraw table upon search 'keyup'
          $('#searchFlebo').on('keyup', () => tableMuestras.draw());
           
        }
        else if (i === 5) {
            let title = $(this).text();

            $(this).html('<input type="text" class="form-control form-control-sm p-1 txtFiltroMue" placeholder="Buscar '+title+'" />');

            $('input', this).on('keyup change', function () {
                if (tableMuestras.column(i).search() !== this.value) {
                    tableMuestras
                        .column(i)
                        .search(this.value)
                        .draw();
                }
            });
        } else if (i===9){
            $(this).html('<button id="limpiarFiltroTablaMuestras" class="btn btn-sm btn-light-primary font-weight-bold" >Limpiar</button>');
        } else if (i===6 || i === 7 || i === 8) {
            $(this).empty();
        }
    } );
    
    $("#seleccionaTodasLasMuestras").on( "click", function(e) {
        if ($(this).is(":checked")){
            tableMuestras.rows().select();
            //$('#tablaMuestras tbody').find(':checkbox').attr('checked', true);
        } else {
            tableMuestras.rows().deselect(); 
            //$('#tablaMuestras tbody').find(':checkbox').attr('checked', false);
        }
    });
    
    $('#tablaMuestras tbody').on('click', 'td:not(.notChildRow)', function () {
        var tr = $(this).closest('tr');
        var row = tableMuestras.row( tr );
        let data = row.data();
        
        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            let url = "";
            // Open this row
            if (data.idmuestra!=null){
                url = getctx + "/api/tomaMuestras/examenes/muestra/"+data.idmuestra;
            } else {
                url = getctx + "/api/tomaMuestras/examenes/"+data.norden+"/"+data.idtipomuestra+"/"+data.idenvase+"/"+data.idderivador+"/"+data.compartemuestra;
            }
            $.ajax({
                type: "GET",
                url: url,
                datatype: "json",
                async: false,
                success: function (data) {
                    let table = '<table cellpadding="5" cellspacing="0" border="0"><tr>';
                    for (let examen of data){
                        table += '<td></td><td>'+examen.ce_CODIGOEXAMEN+' - '+examen.ce_ABREVIADO+'</td>';
                    }
                    table += '</tr></table>';
                    row.child( table ).show();
                }
            });
            tr.addClass('shown');
        }
    });
    
    tableMuestras.on( 'draw', function () {
        registros_muestras = tableMuestras.rows().count();
        //console.log('total rows',registros_muestras);
        let rows_filtroMue = tableMuestras.rows( {search:'applied'} ).count();
        //console.log('rows count:', rows_filtroMue);
        
        $("#dt_registros_m").html("Mostrando <b>"+rows_filtroMue+"</b> de <b>"+registros_muestras+"</b> muestras en total");
    });
    
    //Desactiva boton de confirmar toma de muestra al seleccionar alguna de ellas está en estado rechazado o nulo (sin imprimir etiquetas)
    tableMuestras.on( 'select', function ( e, dt, type) {
        if ( type === 'row' ) {
            let data = tableMuestras.rows('.selected').data();
            let n = data.count();
            let activaBotonConfirmarMuestra = true;
            for (i=0; i<n ; i++){
                let estado = data[i].estadotm;
                if (estado === "RECHAZADA" || estado === ""){
                    activaBotonConfirmarMuestra = false;
                }
            }
            if (activaBotonConfirmarMuestra === false){
                $('#btnConfirmarTomaMuestra').prop('disabled', true);
            } else {
                $('#btnConfirmarTomaMuestra').prop('disabled', false);
            }
            
        }
    });
    
    $('#limpiarFiltroTablaMuestras').click(function(){
        $('.selectFiltroMue').val("");
        $('.txtFiltroMue').val("");
        for (let i=0; i<9; i++){
            tableMuestras
                .column(i)
                .search("", false, false)
                .draw();
            
        }
    });
}

    
  }
*/  
