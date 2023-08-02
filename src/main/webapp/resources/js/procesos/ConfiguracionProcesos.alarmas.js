class TablaAlarmasProceso{
    
    jqId;
    tablaAlarmasProceso;
    codigoProceso;
    counter;
    
    constructor(){
    
        this.jqId = "#tablaAlarmasProceso";
        this.codigoProceso = "";
        this.counter = 1;
    }
    
    loadTablaAlarmas(codigoProceso){
        this.codigoProceso = codigoProceso;
        if (!$.fn.dataTable.isDataTable( this.jqId )) {
            this.initDataTable();
        } else {
            let url = getctx + "/api/alarmas/proceso/"+this.codigoProceso;
            this.tablaAlarmasProceso.ajax.url(url).load();
        }
    }
    
    initDataTable(){
        this.tablaAlarmasProceso = $(this.jqId).DataTable({
            paging: false,
            searchDelay: 500,
            orderCellsTop: true,
            scrollY: 300,
            scrollX: true,
            info: false,
            "rowId": "spaIdprocesoalarma",
            ajax: {
                url: getctx + "/api/alarmas/proceso/"+this.codigoProceso,
                type: "GET",
                contentType: "application/json",
                dataSrc: "dato",
                dataType: "json"
            },
            columns: [
                {data: 'spaIdprocesoalarma'},
                {data: 'spaCodigoalarma'},
                {data: 'spaAlarmadescripcion'},
                {data: 'spaBloquear'},
                {data: 'spaActivo'},
                {data: ''}
            ],
            columnDefs: [
                {
                    targets: 0,
                    orderable: false,
                    visible: false,
                    searchable: false
                    
                },{
                    targets: 1,
                    className: 'edit-title',
                    render: function ( data, type, row ) {
                        let dataId = row['spaIdprocesoalarma'] || '_' + gTableAlarmasProceso.counter;
                        //let html = `<p id="codigoAlarma${dataId}" class="spaCodigoalarma" data-set="${dataId}">${data}</p>`;
                        let html = `<input id="codigoAlarma${dataId}" type="text" class="spaCodigoalarma" value="${data}" data-set="${dataId}" disabled></input>`;
                        return html;
                    }
                },{
                    targets: 2,
                    className: 'edit-title',
                    render: function ( data, type, row ) {
                        let dataId = row['spaIdprocesoalarma'] || '_' + gTableAlarmasProceso.counter;
                        //let html = `<p id="descripcionAlarma${dataId}" class="spaAlarmadescripcion" data-set="${dataId}">${data}</p>`;
                        let html = `<input id="descripcionAlarma${dataId}" type="text" class="spaAlarmadescripcion" value="${data}" data-set="${dataId}" disabled></input>`;
                        return html;
                    }
                },{
                    targets: 3,
                    orderable: false,
                    className: 'edit-title',
                    render: function(data, type, row, meta) {
                        let dataId = row['spaIdprocesoalarma'] || '_' + gTableAlarmasProceso.counter;
                        let checked = '';
                        if (data === 'S') {
                            checked = 'checked';
                        }
                        return `<input id="chbxBloquear${dataId}" type="checkbox" data-set="${dataId}" class="spaBloquear" ${checked} disabled />`;
                    }
                },{
                    targets: 4,
                    orderable: false,
                    className: 'edit-title',
                    render: function(data, type, row, meta) {
                        let dataId = row['spaIdprocesoalarma'] || '_' + gTableAlarmasProceso.counter;
                        let checked = '';
                        if (data === 'S') {
                            checked = 'checked';
                        }
                        return `<input id="chbxActivo${dataId}" type="checkbox" data-set="${dataId}" class="spaActivo" ${checked} disabled />`;
                    }
                },{
                    targets: 5,
                    className: 'dt-left',
                    orderable: false,
                    render: function(data, type, row, meta) {
                        return `<label class="checkbox checkbox-single">
                                              <input data-checked="${row['spaIdprocesoalarma']}" check-edit="" type="checkbox" value="" class="checkable chck-anulado"/>
                                              <span></span>
                                          </label>`;
                    }
                }
            ],
            /*select: {
                style:    'multi',
                selector: 'td:first-child'
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
        /*
        $(this.jqId).on('click','.editar', function (e) {
            //let dataset = "";
            let id = $(this).siblings("p").attr('id');
            let clase = $(this).siblings("p").attr('class');
            let text = $(this).siblings("p").text();
            cambiarEstadoParrafobutton(text,id,clase);
        });
        
        $(this.jqId).on('click','.chbxBloquear', function (e) {
            //let dataset = "";
            let input = e.target;
            let dataId = $(input).attr("data-set");
            let fila = gTableAlarmasProceso.tablaAlarmasProceso.row(`#${dataId}`).data();
            fila.spaBloquear = $(input).is(":checked") ? "S" : "N";
            console.log(fila)
        });
        
        $(this.jqId).on('click','.chbxActivo', function (e) {
            //let dataset = "";
            let input = e.target;
            let dataId = $(input).attr("data-set");
            let fila = gTableAlarmasProceso.tablaAlarmasProceso.row(`#${dataId}`).data();
            fila.spaActivo = $(input).is(":checked") ? "S" : "N";
            console.log(fila)
        });
        
        $(this.jqId + " tbody").on('blur', 'input', function (e) {
            let input = e.target;
            let value = $(input).val();
            let classname = input.className;
            let rowName = classname.replace("form-control form-control-sm ", "")
            let dataId = $(input).parent().attr("data-set");
            let fila = gTableAlarmasProceso.tablaAlarmasProceso.row(`#${dataId}`).data();
            //let fila = this.tablaAlarmasProceso.row(`#${dataId}`).data();
            fila[rowName] = value;
            $(input).prop('disabled', true);
            $(this).parent().parent().find('a').show();
        });
        */
        $(this.jqId).on( 'change', 'input.chck-anulado', function () {
            let id = $(this).data('checked');
            
            if($(`[data-checked="${id}"]`).is(':checked')){
                $(`[data-checked="${id}"]`).closest('td').siblings('.edit-title').each(
                    function(){
                        //let t = $(this).text();
                        //let clase = $(this).find("p").attr('class');
                        $(this).find("input").prop("disabled", false);
                        //$(this).html($(`<input class="form-control ${clase}" />`,{'value' : t}).val(t));
                    });
                
            } else {
                $(`[data-checked="${id}"]`).closest('td').siblings('.edit-title').each(
                    function(){
                        //let newValue = $(this).find('input').val();
                        $(this).find("input").prop("disabled", true);
                        let clase = $(this).find("input").attr('class');
                        let fila = gTableAlarmasProceso.tablaAlarmasProceso.row(`#${id}`).data();
                        if (clase === "spaBloquear" || clase === "spaActivo"){
                            fila[clase] = $(this).find("input").is(":checked") ? "S" : "N";
                        } else {
                            fila[clase] = $(this).find('input').val();
                        }
                    });

            }
        });
        
    }
    
    addAlarma(){
        this.tablaAlarmasProceso.row.add({
            'spaCodigoalarma': '',
            'spaAlarmadescripcion': '',
            'spaBloquear': 'N',
            'spaActivo': 'N',
            'spaIdprocesoalarma': 0
        })
        .draw(false)
        .node().id = '_'+this.counter;

        this.counter++;
    }
    
    clear() {

        this.tablaAlarmasProceso.rows().remove();
        this.tablaAlarmasProceso.draw();

    }
    
}
/*
function cambiarEstadoParrafobutton(text,id,clase){
  let input = $(`<input id="${id}" type="text" class="form-control form-control-sm ${clase}" value="${text}" />`);
   $('#'+id).text('').append(input);
   $('#'+id).parent().find('a').hide();
   
}

function cambiarValorCheckbox(clase, id){
    let fila = gTableAlarmasProceso.tablaAlarmasProceso.row(`#${id}`).data();
    fila[clase] = $("#"+clase+id).is("checked")?"S":"N";
    console.log(fila);
}

$("#btnGuardarAlarmas").click(function (){
    $("#tablaAlarmasProceso tbody tr").each(function () {
        if ($(this).find(".chck-anulado").is("checked")){
            let id = $(this).data('checked');
            console.log(id)
        }
    });
});
 */