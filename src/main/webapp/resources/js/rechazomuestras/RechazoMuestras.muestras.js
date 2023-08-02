function rellenarTablaMuestras(nOrden, idMuestra){
    $('#divTablaMuestras').css('display', 'block');
    $("#panelBusquedaOrden").collapse('hide');
    let contenedor = $("#divTablaMuestras");
    let tamanoVertical = detectarTamanoVertical(contenedor);
    $("#divTablaMuestras").css('min-height', `${tamanoVertical}px`);

    let tamanoVertical2 = detectarTamanoVerticalConDato(contenedor);
    console.log("tamanoVertical2",tamanoVertical2)
    $(".containerTablaTestMuestras").css('min-height', `${tamanoVertical2}px`);
    if ( $.fn.DataTable.isDataTable('#tablaMuestras') ){
        $('#tablaMuestras').DataTable().clear().destroy();
        //$("#tablaMuestras thead tr:eq(1)").remove();
    }
    $('#tablaMuestras tbody').empty();
    $("#lblNroOrden").html(nOrden);
    
    //$('#tablaMuestras thead tr').clone(true).appendTo('#tablaMuestras thead');

    tablaMuestrasRechazo = $('#tablaMuestras').DataTable({
        responsive: false,
        searchDelay: 500,
        orderCellsTop: true,
        scrollY: 250,
        scrollX: true,
        paging: false,
      info: false,
      "autoWidth": false,
      scrollCollapse: true,
        dom: 'lrt',
        "ajax": {
            url: getctx + "/api/rechazoMuestras/muestras/"+ nOrden,
            type: "GET",
            contentType: "application/json",
            dataSrc:function(jsonData){ 
                if(jsonData!= null){
                    jsonData.sort(function(a, b) {
                        if (a.codigobarras < b.codigobarras) return -1;
                        if (a.codigobarras > b.codigobarras) return 1;
                        return 0;
                      });
                }
                return jsonData
            },
            dataType: "json"
        },
        rowId: 'idmuestra',
        columns: [
                {data: 'codigobarras'},
                {data: 'estadotm'},
                {data: 'muestradesc'},
                {data: 'envasedesc'},
                {data: 'nrovecestomada'},
                {data: 'usrflebotomista'},
                {data: 'observaciontm'}
        ],
        columnDefs: [
            { orderable: false, targets: '_all' },
            {
                targets: 0,
                render: function (data, type, meta) {
                    return `<div style="width:${t1*5}px">${data != null ? data : ''}</div>`;
                }
            },
            {
                targets: 1,
                render: function (data, type, meta) {
                    var status = {
                        T: {'title': 'Tomada', 'class': ' label-light-success'},
                        R: {'title': 'Rechazada', 'class': ' label-light-danger'},
                        P: {'title': 'Pendiente', 'class': ' label-light-warning'},
                        B: {'title': 'Bloqueada', 'class': ' label-secondary'}
                    };
                    if (typeof status[data] === 'undefined') {
                        return `<div style="width:${t1*7}px">${data}</div>`;
                    }
                    return `<div class="label label-lg font-weight-bold${status[data].class} label-inline" style="width:${t1*7}px">${status[data].title}</div>`;
                }
            },
            {
                targets: 2,
                render: function (data, type, meta) {
                    return `<div style="width:${t1*5}px">${cutText(data,13)}</div>`;
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
                    return `<div style="width:${t1*7}px">${!isNaN(data) ? convertirAOrdinal(data) : data}</div>`;
                }
            },
            {
                targets: 5,
                render: function (data, type, meta) {
                    return `<div style="width:${t1*8}px">${data}</div>`;
                }
            },
            {
                targets: 6,
                render: function (data, type, meta) {
                    return `<div style="width:${t1*9}px" title="${data != null ? data : ''}">${data != null ? data : ''}</div>`;
                }
            },
        ],
          "select": {
          "style": 'single',
          "className": 'row-selected',
        },
        "rowCallback": function(row, data, index){
            //tacha el row completo si el examen está anulado
            if(data['allexamenesanulados']==='S'){
                $("td", row).css('text-decoration','line-through');
            }
        },
        "initComplete": function () {
            if (idMuestra!==null){
                let rowToSelect = '#' + idMuestra;
                tablaMuestrasRechazo.row(rowToSelect).select();
            }
            if (tablaMuestrasRechazo.rows().count() === 0){
                $.notify({ message: "No se han registrado muestras en la orden seleccionada" }, { type: "danger" });
            }
        },
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
    
    $('#tablaMuestras tbody').on('click', 'tr', function () {
        let data = tablaMuestrasRechazo.row(this).data();
        idMuestraRechazar = data['idmuestra'];
        console.log("Muestra seleccionada: " + idMuestraRechazar);
        llenarFormRechazo();
    });
    
}

function llenarFormRechazo(){
    //$("#divRechazo").show();
    $("#lblPrimerRechazo").hide();
    $("#lblPrimerRechazo").removeClass('disabled')
    $("#btnRechazarMuestra").prop('disabled',true)
    $("#btnRechazoParcial").prop('disabled',true)
    $("#lblSegundoRechazo").hide();
    $("#lblNoMasRechazos").hide();
    $("#lblNuevaMuestra").hide();
    $("#lblNuevaMuestraPendiente").hide();
    $("#lblNuevaMuestraLista").hide();
    
    $.ajax({
        type: "GET",
        url: getctx + "/api/rechazoMuestras/muestra/"+idMuestraRechazar,
        datatype: "json",
        success: function (muestra) {

            listaExamenesMuestra = muestra.listaExamenes;
            //console.log("listaExamenesMuestra: "+JSON.stringify(listaExamenesMuestra))
            //console.log("cant. examenes: "+listaExamenesMuestra.length)
            if (listaExamenesMuestra.length<2){
                $("#btnRechazoParcial").hide();
            } else {
                $("#btnRechazoParcial").show();
            }
            
            if (muestra.dmr_IDCAUSARECHAZO===null){
                muestra.dmr_IDCAUSARECHAZO=0;
            }
            switch(muestra.dfm_ESTADOTM) {
                case "R":
                    $("#causaRechazoMuestras").attr("disabled", true);
                    $("#observacion").attr("disabled", true);
                    // $("#btnRechazarMuestra").attr("disabled", true);
                    // $("#btnRechazoParcial").attr("disabled", true);
                    break;
                case "T":
                    $("#causaRechazoMuestras").attr("disabled", false);
                    $("#observacion").attr("disabled", false);
                    // $("#btnRechazarMuestra").attr("disabled", false);
                    // $("#btnRechazoParcial").attr("disabled", false);
                    break;
                case "P":
                    handlerMessageError("Muestra aún no ha sido tomada");
                    $("#causaRechazoMuestras").attr("disabled", true);
                    $("#observacion").attr("disabled", true);
                    // $("#btnRechazarMuestra").attr("disabled", true);
                    // $("#btnRechazoParcial").attr("disabled", true);
                    break;
                case "B":
                    $("#causaRechazoMuestras").attr("disabled", true);
                    $("#observacion").attr("disabled", true);
                    // $("#btnRechazarMuestra").attr("disabled", true);
                    // $("#btnRechazoParcial").attr("disabled", true);s
                    break;
            }
            
            if (muestra.dmr_IDNUEVAMUESTRA !== null){
                $("#lblNuevaMuestra").show();
                $("#lblIdNuevaMuestra").text(muestra.dfm_CODIGOBARRA);
            }
            
            switch (muestra.dfm_ESTADONUEVAMUESTRA){
                case "P":
                    $("#lblNuevaMuestraPendiente").show();
                    break;
                case "T":
                    $("#lblNuevaMuestraLista").show();
                    break;
            }
            
            if (muestra.dfm_NROVECESTOMADA === 1){
                $("#lblPrimerRechazo").show();
            }
            if (muestra.dfm_NROVECESTOMADA === 2){
                $("#lblSegundoRechazo").show();
            }
            if (muestra.dfm_NROVECESTOMADA === 3){
                $("#lblNoMasRechazos").show();
                // $("#btnRechazarMuestra").attr("disabled", true);
                // $("#btnRechazoParcial").attr("disabled", true);
                // $("#causaRechazoMuestras").attr("disabled", true);
                $("#observacion").attr("disabled", true);
            }
            if(muestra.dmr_IDCAUSARECHAZO !== 0){
                // $("#lblPrimerRechazo").removeClass('disabled')
                // $("#btnRechazarMuestra").prop('disabled',true)
            }else{
                // $("#lblPrimerRechazo").addClass('disabled')
                // $("#btnRechazarMuestra").prop('disabled',false)
            }
            $("#btnRechazoParcial").attr("disabled", true);
            $("#lblPrimerRechazo").addClass('disabled')
            $("#causaRechazoMuestras").val(muestra.dmr_IDCAUSARECHAZO);
            $("#observacion").val(muestra.dmr_NOTA);
            $('.selectpicker').selectpicker('refresh');

            
        },
        error: function (msg) {
            handlerMessageError("Error al mostrar datos");
            console.log(msg);
        }
    });
}

/*************** RECHAZO TOTAL **************/
$("#btnRechazarMuestra").click(function(){
    let causaRechazo = $("#causaRechazoMuestras").val();
    if (causaRechazo == 0 || causaRechazo == null){
        $("#causaRechazoMuestras").addClass("is-invalid").selectpicker("setStyle");
        $('.selectpicker').selectpicker('refresh');
        handlerMessageError("Debe seleccionar Causa de rechazo");
    } else {
        let observacion = $("#observacion").val();
        rechazoMuestraTotal(causaRechazo, observacion);
    }
});

function rechazoMuestraTotal(causaRechazo, observacion){
    let formData = new FormData();
    formData.append('idUsuario', sesionUsr);
    formData.append('causaRechazo', causaRechazo);
    formData.append('observacion', observacion);
    
    $.ajax({
        type: "post",
        url: getctx+"/api/rechazoMuestras/rechazoTotal/"+idMuestraRechazar,
        data: formData,
        contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
        processData: false, // NEEDED, DON'T OMIT THIS
        success: function (json) {
            $("#lblNuevaMuestraPendiente").show();
            $("#lblNuevaMuestra").show();
            $("#btnRechazarMuestra").attr("disabled", true);
            $("#btnRechazoParcial").attr("disabled", true);
            $("#causaRechazoMuestras").attr("disabled", true);
            $("#observacion").attr("disabled", true);
            $("#lblIdNuevaMuestra").text(json.dato.dfmCodigobarra);
            handlerMessageOk(json.message);
            tablaMuestrasRechazo.ajax.reload(null, false);
            if (json.dato.dfmNrovecestomada === 3){
                alert("Esta es la última muestra que se generará");
            }
        },
        error: function () {
            handlerMessageError("Error al rechazar muestra");
        }
    });
}

/*************** RECHAZO PARCIAL **************/
$("#btnRechazoParcial").click(function(){
    let causaRechazo = $("#causaRechazoMuestras").val();
    if (causaRechazo == 0 || causaRechazo == null){
        $("#causaRechazoMuestras").addClass("is-invalid").selectpicker("setStyle");
        $('.selectpicker').selectpicker('refresh');
        $.notify({ message: "Debe seleccionar Causa de rechazo" }, { type: "danger" });
    } else {
        // $("#causaRechazoMuestras").removeClass('is-invalid');
        $("#causaRechazoMuestras")
        .removeClass("is-invalid")
        .selectpicker("setStyle")
        .parent()
        .removeClass("is-invalid");
        $('#modalRechazoParcial').modal('show');
        let data = tablaMuestrasRechazo.row(`#${idMuestraRechazar}`).data();
        //let nOrden  = data.norden;
        $("#nroMuestraModal").text(data.codigobarras);
        $("#pacienteModal").text(listaExamenesMuestra[0].nombres);
        $("#examenesRechazoBody").empty();
        let cont = 0;
        listaExamenesMuestra.forEach(function (examen){
            cont++;
            $("#examenesRechazoBody").
                    append(`<tr class=''>
                                <th class='row mx-auto'>${cont}</th>
                                <td class='' ${examen.dfe_EXAMENANULADO === 'S' ? 'style="text-decoration-line: line-through;"' : ''}>${examen.ce_ABREVIADO}</td>
                                <td class='row rechazaExamen' style="align-items: center; justify-content: center;">
                                    <input id="${examen.ce_IDEXAMEN}" value='' type='checkbox' ${examen.dfe_EXAMENANULADO === 'S' ? 'disabled' : ''} >
                                </td>
                            </tr>`);
        });
    }
    
});

$("#confirmRechazoParcial").click(function(){
    let causaRechazo = $("#causaRechazoMuestras").val();
    let observacion = $("#observacion").val();
    
    let examenes = [];
    $('#examenesRechazoBody tr input:checked').each(function() {
        examenes.push($(this).attr('id'));
    });
    
    let nocheckeados = [];
    $('#examenesRechazoBody tr input:checkbox:not(:checked)').each(function() {
        nocheckeados.push($(this).attr('id'));
    });
    //console.log("examenes: "+examenes);
    if (nocheckeados.length === 0){
        rechazoMuestraTotal(causaRechazo, observacion);
    } else {
        if (examenes.length > 0){
            let formData = new FormData();
            formData.append('idUsuario', sesionUsr);
            formData.append('causaRechazo', causaRechazo);
            formData.append('observacion', observacion);
            formData.append('examenesRechazados', examenes);
            formData.append('examenesNoRechazados', nocheckeados);
            $.ajax({
                type: "POST",
                url: getctx + "/api/rechazoMuestras/rechazoParcial/"+idMuestraRechazar,
                data: formData,
                contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
                processData: false, // NEEDED, DON'T OMIT THIS
                success: function (json) {
                    $("#lblNuevaMuestraPendiente").show();
                    $("#lblNuevaMuestra").show();
                    $("#btnRechazarMuestra").attr("disabled", true);
                    $("#btnRechazoParcial").attr("disabled", true);
                    $("#causaRechazoMuestras").attr("disabled", true);
                    $("#observacion").attr("disabled", true);
                    $('#modalRechazoParcial').modal('toggle');
                    $("#lblIdNuevaMuestra").text(json.dato.dfmCodigobarra);
                    handlerMessageOk(json.message);
                    tablaMuestrasRechazo.ajax.reload(null, false);
                    if (json.dato.dfmNrovecestomada === 3){
                        alert("Esta es la última muestra que se generará");
                    }
                },
                error: function () {
                    handlerMessageError("Error al rechazar muestra");
                }
            }); 
        } else {
            handlerMessageError("No ha seleccionado ningún examen");
        }
    }
});





$('#tablaMuestras thead tr:eq(1) th').each( function (i){
    //busqueda por texto
    if (i===0){
        $(this).html( `<div style="width:${t1*5}px"></div>`)
    } else if (i===1){
        $(this).html( `<div style="width:${t1*7}px"></div>`)
    } else if (i===2){
        $(this).html( `<div style="width:${t1*5}px"></div>`)
    }
    else if (i===3){
        $(this).html( `<div style="width:${t1*11}px"></div>`)
    } else if (i===4){
        $(this).html( `<div style="width:${t1*7}px"></div>`)
    }
    else if (i===5){
        $(this).html( `<div style="width:${t1*8}px"></div>`)
    }
    else if (i===6){
        $(this).html( `<div style="width:${t1*9}px"></div>`)
    }
});

$('#causaRechazoMuestras').on('changed.bs.select', function (e) {

    const filasSeleccionadas = tablaMuestrasRechazo.rows({ selected: true }).data().toArray();
    let dfm_ESTADOTM = {
        R:'disabled',
        P:'disabled',
        B:'disabled',
    }

    $("#lblPrimerRechazo").removeClass('disabled')
    $("#btnRechazarMuestra").prop('disabled',false)

    if(!(filasSeleccionadas.dfm_ESTADOTM in dfm_ESTADOTM)){
        $("#btnRechazoParcial").prop('disabled',false)
    }

})

