$("#guardarDetalleMuestra").on('click', function(event) {
    guardarMuestraCambiada(event);
  });

let initTableTipoMuestra = function(){
    tableTipoMuestras = $("#tablaTipoMuestra").DataTable({
        responsive: false,
        paging: false,
        searchDelay: 500,
        orderCellsTop: true,
        "ordering": false,
        scrollY: 300,
        scrollX: true,
        info: false,
        rowId: function(a) {
            return a.ce_IDEXAMEN.toString()
        },
        "ajax": {
                url: getctx + "/api/tomaMuestras/tipoMuestra",
                "contentType": "application/json",
                //"processData": false,
                //type: "GET",
                type: "POST",
                //dataType: "json",
                "dataSrc": "dato",
                "data": function(d) { return JSON.stringify(datosMuestra); }
                //"data": JSON.stringify(datosMuestra) 
        },
        "columns": [
            {data: 'ce_DESCRIPCION'},
            {data: 'ce_CODIGOEXAMEN'},
            {data: 'cmue_IDTIPOMUESTRA'},
            {data: 'cenv_DESCRIPCION'},
            {data: 'ce_COMPARTEMUESTRA'}
        ],
        columnDefs: [
            {
                targets: 2,
                render: function ( data, type, row ) {
                    if (row['ce_TIENEGRUPOMUESTRA']==="S"){
                        let idExamen = row['ce_IDEXAMEN'];
                        let idMuestra = row['dfet_IDMUESTRA'];
                        let idTipoMuestra = row['cmue_IDTIPOMUESTRA'];
                        //let muestraDefault = row["cmue_DESCRIPCIONABREVIADA"];
                        return cargarTipoMuestrasGrupo(idExamen, idMuestra, idTipoMuestra);
                        //return row["cmue_DESCRIPCION"];
                    } else {
                       return row["cmue_DESCRIPCION"];
                    }
                }
            },
            {
                targets: 4,
                render: function ( data, type, row ) {
                    return data === "S" ? "SI" : "NO";
                }
            }
        ]
    });

    $('#modalDetalleMuestra').on('shown.bs.modal', function(e){
        $("#guardarDetalleMuestra").prop('disabled',true)
    $($.fn.dataTable.tables(true)).DataTable()
       .columns.adjust();
 })

 tableTipoMuestras.on( 'draw', function () {
    $('select[data-muestra="x"]').off('change', modificarBottoonGuardar);
    $('select[data-muestra="x"]').on('change', modificarBottoonGuardar);
});


}


function cambiarTipoMuestra(newTipoMuestra, idMuestra){
    // newTipoMuestra = e.value;//validar si esta variable está vacía no debe hacer nada...
    
    if (idMuestra!==null){
        // let text = "Se borrará la etiqueta asociada a la muestra. ¿Desea continuar?";
        // if (confirm(text) === true) {
            $.ajax({
                type: "post",
                url: getctx + "/api/tomaMuestras/tipoMuestra/update/"+idMuestra+"/"+newTipoMuestra,
                datatype: "json",
                async: false,
                success: function(){
                    tableMuestras.ajax.reload( null, true );
                    datosMuestra['idTipoMuestra'] = newTipoMuestra;
                    tableTipoMuestras.ajax.reload( null, true );
                    mensajeExtiroCerrarModal() 
                }
            });
        // } else {
        //     tableTipoMuestras.ajax.reload( null, false );
        // }
    } else {
        // let text = "¿Confirma que desea cambiar la muestra?";
        // if (confirm(text) === true) {
            $.ajax({
                type: "post",
                url: getctx + "/api/tomaMuestras/tipoMuestra/update/"+nOrden+"/"+datosMuestra['idTipoMuestra']+"/"+datosMuestra['idEnvase']+"/"+datosMuestra['idDerivador']+"/"+datosMuestra['comparteMuestra']+"/"+newTipoMuestra,
                datatype: "json",
                async: false,
                success: function(){
                    tableMuestras.ajax.reload( null, false );
                    datosMuestra['idTipoMuestra'] = newTipoMuestra;
                    tableTipoMuestras.ajax.reload( null, false );
                    idTipoMuestra = newTipoMuestra;
                    mensajeExtiroCerrarModal()
                }
            });
        // } else {
        //     tableTipoMuestras.ajax.reload( null, false );
        //     // cancelarCambioTipoMuestra(idExamen);
        // }
    }
}

/* nuevo desarrollo */
let tipomuestrasGrupo = function (idExamen) {
    let jqXHR = $.ajax({
        type: "GET",
        url:  getctx + "/api/dominio/muestras/grupo/list/"+idExamen,
        datatype: "json",
        async: false
    });
    return jqXHR.responseJSON;
  };


  function cargarTipoMuestrasGrupo(idExamen,idMuestra,idTipoMuestra){
    let muestras = tipomuestrasGrupo(idExamen);
    // let select = `<select id="selectMuestrasGrupo${idExamen}" class="form-control largo-select" data-muestra="x" onchange="cambiarTipoMuestra(this,${idMuestra})" >`;
    let select = `<select id="selectMuestrasGrupo${idExamen}" class="form-control largo-select" data-muestra="x" >`;
    //select += `<option value="">${muestraDefault}</option>`;
    Object.entries(muestras).forEach(element => {
        if (element[1].cmueIdtipomuestra==idTipoMuestra) {
            select+= `<option value="${element[1].cmueIdtipomuestra}" selected>${element[1].cmueDescripcion}</option>`; 
        } else {
            select+= `<option value="${element[1].cmueIdtipomuestra}">${element[1].cmueDescripcion}</option>`; 
        }
      
    });

    select += "</select>";

    return select;
  }

function modificarBottoonGuardar(){
    var newValue = $(this).val();
    let table = $("#tablaTipoMuestra").DataTable();
    let row = $(this).closest("tr");
    let that = table.row(row).data();
    if (that.dfet_IDMUESTRA!==null){
        handlerMessageWarning("Se borrará la etiqueta asociada a la muestra");
    }

    that.cmue_IDTIPOMUESTRA = newValue;
    table.row(row).data(that).draw();

    $("#guardarDetalleMuestra").prop('disabled',false)
}


function guardarMuestraCambiada(event){
    event.preventDefault();
    let table = $("#tablaTipoMuestra").DataTable().rows().data().toArray();
    const { dfet_IDMUESTRA, cmue_IDTIPOMUESTRA } = table[0];
    cambiarTipoMuestra(cmue_IDTIPOMUESTRA, dfet_IDMUESTRA)
}


function mensajeExtiroCerrarModal() {
    $('#modalDetalleMuestra').modal('hide');
    handlerMessageOk("Se ha cambiado tipo de muestra")
    // $('#modalDetalleMuestra').on('hidden.bs.modal', function () {
    // });
  }