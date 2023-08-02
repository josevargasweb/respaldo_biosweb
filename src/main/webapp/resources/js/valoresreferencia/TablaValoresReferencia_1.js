class TablaValoresReferencia {
    jqId;
    idTest;
    tableValoresReferencia;
    counter;
    bTest;
    metodosTest;
    
    constructor(idTabla, idTest){
        this.jqId = idTabla;
        this.idTest = idTest;
        this.counter = 1;
        this.metodosTest = null;
        
        $.ajax({
            type: "GET",
            url: getctx + "/api/metodos/test/" + this.idTest,
            datatype: "json",
            async: false,
            success: function(data){
                this.metodosTest = data.dato;
            },
            error: function (msg) {
                handlerMessageError(msg);
            }
        });
        
        if (!$.fn.dataTable.isDataTable(this.jqId)) {
            this.initDatatable();
        } else {
            let newUrl = getctx + "/api/valoresReferencia/list/"+this.idTest;
            this.tableValoresReferencia.ajax.url(newUrl).load();
        }
        
    }
    
    initDatatable(){
        this.tableValoresReferencia = $(this.jqId).DataTable({
            responsive: false,
            orderCellsTop: true,
            searchDelay: 500,
            scrollY: 300,
            scrollX: true,
            paging: false,
            info: false,
            dom: 'lrt',
            "ordering": false,
            "rowId": "cvrIdvalorreferencia",
            "ajax": {
                url: getctx + "/api/valoresReferencia/list/"+this.idTest,
                type: "GET",
                contentType: "application/json",
                dataSrc: "dato",
                dataType: "json"
            },
            columns: [
                {data: 'cvrSexo'},
                {data: 'cvrAnosdesde'},
                {data: 'cvrMesesdesde'},
                {data: 'cvrDiasdesde'},
                {data: 'cvrAnoshasta'},
                {data: 'cvrMeseshasta'},
                {data: 'cvrDiashasta'},
                {data: 'cfgMetodos'},
                {data: 'cvrValorcriticobajo'},
                {data: 'cvrValorbajo'},
                {data: 'cvrValoralto'},
                {data: 'cvrValorcriticoalto'},
                {data: 'cvrValortexto'},
                {data: 'borrar'},
                {data: 'cvrIdtest'}
            ],
            columnDefs: [
                {
                    targets: 0,
                    title: "Sexo",
                    render: function ( data, type, row ) {
                        return cargarSexoVR(data, row['cvrIdvalorreferencia']);
                    }
                },
                {
                    targets: 1,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="yearsDesde${dataId}" class="cvrAnosdesde" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 2,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="mesesDesde${dataId}" class="cvrMesesdesde" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 3,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="diasDesde${dataId}" class="cvrDiasdesde" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 4,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="yearsHasta${dataId}" class="cvrAnoshasta" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 5,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="mesesHasta${dataId}" class="cvrMeseshasta" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 6,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="diasHasta${dataId}" class="cvrDiashasta" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 7,
                    title: "Métodos",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        return cargarMetodos(this.metodosTest, data, dataId);
                    }
                },
                {
                    targets: 8,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="bajoCritico${dataId}" class="cvrValorcriticobajo" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 9,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="bajo${dataId}" class="cvrValorbajo" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 10,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="alto${dataId}" class="cvrValoralto" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 11,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="altoCritico${dataId}" class="cvrValorcriticoalto" data-set="${dataId}">${data}</p>
                                        <a class="editNumber" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 12,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        let html = `<div class="row">
                                        <p id="texto${dataId}" class="cvrValortexto" data-set="${dataId}">${data}</p>
                                        <a class="editText" class="p-2 ml-2">
                                            <i class="fas fa-pen" aria-hidden="true"></i>
                                        </a>
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 13,
                    title: "Borrar",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+this.counter;
                        return `<div class="row d-flex flex-nowrap btn-toolbar">
                                    <a id="btnBorrar${dataId}" data-set="${row['cvrIdvalorreferencia']}" class="eliminar p-2" title="Eliminar valor referencia">
                                        <i class="far fa-trash-alt"></i><span class="nav-text"></span>
                                    </a>`;
                    }
                },
                {
                    targets: 14,
                    visible: false,
                    searchable: false
                }
            ],
            select: {
                style: "os",
                selector: "td"
            },
            language: {
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
        
        $('#tablaValoresReferencia tbody').on('blur', 'input', function (e) {
            let input = e.target;
            let value = $(input).val();
            let rowName = input.className;
            let dataId = $(input).parent().attr("data-set");
            let fila = tableValoresReferencia.row(`#${dataId}`).data();
            let editar = true;
            if (realizarComprobacion(rowName, fila)===true){
                switch (rowName){
                    case "cvrAnosdesde":
                        editar = validarDesdeHasta(value, fila.cvrAnoshasta, fila.cvrMesesdesde, fila.cvrMeseshasta, fila.cvrDiasdesde, fila.cvrDiashasta);
                        if (editar === true){
                            let fDesde = calcularDiasMesYear(fila.cvrDiasdesde, fila.cvrMesesdesde, value);
                            let fHasta = calcularDiasMesYear(fila.cvrDiashasta, fila.cvrMeseshasta, fila.cvrAnoshasta);
                            editar = validaTraslapeDatos(fDesde, fHasta, fila, rowName);
                        }
                        break;
                    case "cvrMesesdesde":
                        editar = validarDesdeHasta(fila.cvrAnosdesde, fila.cvrAnoshasta, value, fila.cvrMeseshasta, fila.cvrDiasdesde, fila.cvrDiashasta);
                        if (editar === true){
                            let fDesde = calcularDiasMesYear(fila.cvrDiasdesde, value, fila.cvrAnosdesde);
                            let fHasta = calcularDiasMesYear(fila.cvrDiashasta, fila.cvrMeseshasta, fila.cvrAnoshasta);
                            editar = validaTraslapeDatos(fDesde, fHasta, fila, rowName);
                        }
                        break;
                    case "cvrDiasdesde":
                        editar = validarDesdeHasta(fila.cvrAnosdesde, fila.cvrAnoshasta, fila.cvrMesesdesde, fila.cvrMeseshasta, value, fila.cvrDiashasta);
                        if (editar === true){
                            let fDesde = calcularDiasMesYear(value, fila.cvrMesesdesde, fila.cvrAnosdesde);
                            let fHasta = calcularDiasMesYear(fila.cvrDiashasta, fila.cvrMeseshasta, fila.cvrAnoshasta);
                            editar = validaTraslapeDatos(fDesde, fHasta, fila, rowName);
                        }
                        break;
                    case "cvrAnoshasta":
                        editar = validarDesdeHasta(fila.cvrAnosdesde, value, fila.cvrMesesdesde, fila.cvrMeseshasta, fila.cvrDiasdesde, fila.cvrDiashasta);
                        if (editar === true){
                            let fDesde = calcularDiasMesYear(fila.cvrDiasdesde, fila.cvrMesesdesde, fila.cvrAnosdesde);
                            let fHasta = calcularDiasMesYear(value, fila.cvrMeseshasta, fila.cvrAnoshasta);
                            editar = validaTraslapeDatos(fDesde, fHasta, fila, rowName);
                        }
                        break;
                    case "cvrMeseshasta":
                        editar = validarDesdeHasta(fila.cvrAnosdesde, fila.cvrAnoshasta, fila.cvrMesesdesde, value, fila.cvrDiasdesde, fila.cvrDiashasta);
                        if (editar === true){
                            let fDesde = calcularDiasMesYear(fila.cvrDiasdesde, fila.cvrMesesdesde, fila.cvrAnosdesde);
                            let fHasta = calcularDiasMesYear(fila.cvrDiashasta, value, fila.cvrAnoshasta);
                            editar = validaTraslapeDatos(fDesde, fHasta, fila, rowName);
                        }
                        break;
                    case "cvrDiashasta":
                        editar = validarDesdeHasta(fila.cvrAnosdesde, fila.cvrAnoshasta, fila.cvrMesesdesde, fila.cvrMeseshasta, fila.cvrDiasdesde, value);
                        if (editar === true){
                            let fDesde = calcularDiasMesYear(fila.cvrDiasdesde, fila.cvrMesesdesde, fila.cvrAnosdesde);
                            let fHasta = calcularDiasMesYear(value, fila.cvrMeseshasta, fila.cvrAnoshasta);
                            editar = validaTraslapeDatos(fDesde, fHasta, fila, rowName);
                        }
                        break;
                }
                
            }
            
            if (editar === true){
                $(input).prop('disabled', true);
                $(this).parent().parent().find('a').show();
                fila[rowName] = value;
            } else {
                $(input).prop('disabled', true);
                $(this).parent().parent().find('a').show();
            }
        });
        
        $('#tablaValoresReferencia').on('click','.editText', function (e) {
            //let dataset = "";
            let id = $(this).siblings("p").attr('id');
            let clase = $(this).siblings("p").attr('class');
            let text = $(this).siblings("p").text();
            cambiarEstadoParrafobutton(text,id,clase);
        });
        
        $('#tablaValoresReferencia').on('click','.editNumber', function (e) {
            //let dataset = "";
            let id = $(this).siblings("p").attr('id');
            let clase = $(this).siblings("p").attr('class');
            let text = $(this).siblings("p").text();
            cambiarEstadoParrafobuttonNumber(text,id,clase);
        });
        
        $('#tablaValoresReferencia').on('click','.eliminar', function (e) {
            let textConfirm = "¿Confirma que desea eliminar valor de referencia?";
            if (confirm(textConfirm) === true) {
                let input = e.currentTarget;
                let dataId = $(input).attr("data-set");
                if (dataId !== 'undefined' && dataId !== null){
                    $.ajax({
                        type: "delete",
                        url: getctx + "/api/valorReferencia/delete/" + dataId,
                        datatype: "json",
                        success: function (data, textStatus, jqXHR) {
                            if (data.status===200){
                                handlerMessageOk(data.message);
                            } else {
                                handlerMessageError(data.message);
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            handlerMessageError(textStatus);
                        }
                    });
                }
                this.tableValoresReferencia.row('.selected').remove().draw(false);
            }
        });
        
       
        
        
    }
    
}

$('#addRow').on('click', function () {
            let cfgmetodo = null;
            //dejar metodo seleccionado por defecto
            if (this.metodosTest!==null){
                this.metodosTest.forEach((met) => {
                    if (met.cmtEsvalorpordefecto==='S'){
                        cfgmetodo = {
                            "cmetIdmetodo": met.cmetIdmetodo
                        };;
                    }
                });
            }
            this.tableValoresReferencia.row.add({
                'cvrSexo': 'A',
                'cvrAnosdesde': 0,
                'cvrMesesdesde': 0,
                'cvrDiasdesde': 0,
                'cvrAnoshasta': 0,
                'cvrMeseshasta' : 0,
                'cvrDiashasta': 0,
                'cfgMetodos': cfgmetodo,
                'cvrValorcriticobajo': 0,
                'cvrValorbajo': 0,
                'cvrValoralto': 0,
                'cvrValorcriticoalto': 0,
                'cvrValortexto': "",
                'cvrIdtest': testId
            })
            .draw(false)
            .node().id = '_'+counter;

            counter++;
        });

        $('#addRow').click();

function cargarSexoVR(sexo, idVr){
    let select = `<select id="selectSexoVr${idVr}" onchange="cambiarSexo(this)">`;
    let sexos = [
        {'id': 'F', 'value': 'FEMENINO'},
        {'id': 'M', 'value': 'MASCULINO'},
        {'id': 'A', 'value': 'AMBOS'}
    ];
    sexos.forEach((gender) => {
        if (gender.id==sexo){
            select += "<option value="+gender.id+" selected>"+gender.value+"</option>";
        } else {
            select += "<option value=" +gender.id+">" +gender.value+ "</option>";
        }
    });
    select += '</select>';
    return select;
}

function cambiarSexo(e){
    let sexo = e.value;
    let fila = tableValoresReferencia.row('.selected').data();
    fila.cvrSexo = sexo;
    console.log(fila);
}

function cargarMetodos(metodosTest, metodo, idVr){
    let select = `<select id="selectMetodosVr${idVr}" onchange="cambiarMetodo(this,${idVr})">`;
    
    select += "<option value='N' selected>Ningún metodo seleccionado</option>";
    metodosTest.forEach((met) => {
        if (typeof metodo !== 'undefined' && metodo !== null){
            if(met.cmetIdmetodo == metodo.cmetIdmetodo){
                select += "<option value="+met.cmetIdmetodo+" selected>"+met.cmetDescripcion+"</option>";
            } else {
                select += "<option value="+met.cmetIdmetodo+">"+met.cmetDescripcion+"</option>";
            }
        } else {
            select += "<option value="+met.cmetIdmetodo+">"+met.cmetDescripcion+"</option>";
        }
    });
    
    select += '</select>';
    
    return select;
}

function cambiarMetodo(e, id){
    let idMetodo = e.value;
    let fila = tableValoresReferencia.row('.selected').data();
    let metodo = {
        "cmetIdmetodo": idMetodo
    };
    fila.cfgMetodos = metodo;
    $(`#selectMetodosVr${id} option[value='N']`).remove();
    console.log(fila);
}

//Edit rows
function cambiarEstadoParrafobutton(text,id,clase){
  let input = $(`<input id="${id}" type="text" class="${clase}" value="${text}" size="5" />`);
   $('#'+id).text('').append(input);
   $('#'+id).parent().find('a').hide();
   
}

function cambiarEstadoParrafobuttonNumber(text,id,clase){
  let max = 1000;
  let step = "";
  switch (clase){
      case "cvrAnosdesde":
      case "cvrAnoshasta":
          max=150;
          break;
      case "cvrMesesdesde":
      case "cvrMeseshasta":
          max=11;
          break;
      case "cvrDiasdesde":
      case "cvrDiashasta":
          max=30;
          break;
      case "cvrValorbajo":
      case "cvrValoralto":
      case "cvrValorcriticobajo":
      case "cvrValorcriticoalto":
          step="0.001";
  }
    
  let input = $(`<input id="${id}" type="number" class="${clase}" max="${max}" step="${step}" value="${text}" style="width:60px" />`);
   $('#'+id).text('').append(input);
   $('#'+id).parent().find('a').hide();
   
}

function validaNumberKey(evt, className) {
    if (className!=="cvrValortexto") {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        } else {
            return true;
        }
    }
    return true;
}

function realizarComprobacion(classRow, fila){
    switch (classRow){
        case "cvrAnosdesde":
        case "cvrMesesdesde":
        case "cvrDiasdesde":
            if(fila.cvrAnoshasta > 0 || fila.cvrMeseshasta > 0 || fila.cvrDiashasta > 0){
                return true;
            }
            break;
        case "cvrAnoshasta":
        case "cvrMeseshasta":
        case "cvrDiashasta":
            if(fila.cvrAnosdesde > 0 || fila.cvrMesesdesde > 0 || fila.cvrDiasdesde > 0){
                return true;
            }
            break;
    }
    return false;
}

function validarDesdeHasta(yearsDesde, yearsHasta, mesesDesde, mesesHasta, diasDesde, diasHasta){
    if (yearsDesde > yearsHasta){
        handlerMessageError("Año desde no puede ser mayor a año hasta");
        return false;
    } else if (yearsDesde === yearsHasta){
        if (mesesDesde > mesesHasta) {
            handlerMessageError("Mes desde no puede ser mayor a mes hasta");
            return false;
        } else if (mesesDesde === mesesHasta){
            if (diasDesde > diasHasta) {
                handlerMessageError("Día desde no puede ser mayor a día hasta");
                return false;
            }
        }
    }
    return true;
}
/*
 * 
 */
function validaTraslapeDatos(fDesde, fHasta, findx, clase){
    let filas = tableValoresReferencia.rows().data();
    let n = filas.data().length;
    let validar = true;
    for (let i=0; i<n; i++){
        if (filas[i] !== findx){
            if (filas[i].cvrSexo === 'A' || filas[i].cvrSexo === findx.cvrSexo){
                //calcula el total de dias de la fila desde y hasta de la columna 
                let iDesde = calcularDiasMesYear(filas[i].cvrDiasdesde, filas[i].cvrMesesdesde, filas[i].cvrAnosdesde);
                let iHasta = calcularDiasMesYear(filas[i].cvrDiashasta, filas[i].cvrMeseshasta, filas[i].cvrAnoshasta);
                if ((fDesde > iDesde || fHasta > iDesde) && (fDesde < iHasta || fHasta < iHasta)){
                    validar = false;
                    break;
                }
            }
        }
    }
    if (validar === false){
        handlerMessageError("Datos ingresados se traslapan con otras columnas");
        findx[clase] = 0;
    }
    return validar;
}

/**
 * Calcula el total de dias de acuerdo a dias, meses y años, y se suma el total.
 * @dias conserva su valor
 * @meses se multiplica por 30
 * @years se multiplica por 365
 */
function calcularDiasMesYear(dias, meses, years){
    let diasMeses = meses*30;
    let diasYears = years*365;
    return dias + diasMeses + diasYears;
    
}

$("#btnGuardar").click(function (){
        //e.preventDefault();
        let indx = this.tableValoresReferencia.rows().data();
        let items = new Array();
        let n = indx.data().length;
        let validar = true;
        for (let i = 0; i < n; i++) {
          if (indx[i].cvrAnosdesde===0 && indx[i].cvrMesesdesde===0 && indx[i].cvrDiasdesde===0 &&
                 indx[i].cvrAnoshasta===0 && indx[i].cvrMeseshasta===0 && indx[i].cvrDiashasta===0){
              validar = false;
              handlerMessageError("Debe definir al menos un valor Desde o Hasta");
              break;
          }
          if (indx[i].cvrValorbajo===0 && indx[i].cvrValoralto===0){
              validar = false;
              handlerMessageError("Debe definir al menos un valor bajo o valor alto");
              break;
          }
          if (indx[i].cfgMetodos===null){
              validar = false;
              handlerMessageError("Debe seleccionar método");
              break;
          }
          if (indx[i].cvrSexo===null){
              validar = false;
              handlerMessageError("Debe definir sexo");
              break;
          }
          items.push(indx.data()[i]);
        }
        if (validar===true){
            //console.log(items);
            let formData = new FormData();
            formData.append("data", JSON.stringify(items));
            
            $.ajax({
                type: "POST",
                data: formData,
                url: getctx + "/api/valoresReferencia/save",
                dataType: 'json',
                aysnc: false,
                processData: false,
                contentType: false,
                success: function(data){
                    handlerMessageOk("Valores de referencia guardados");
                    this.tableValoresReferencia.ajax.reload(null, false);
                    //console.log("guardar valores referencia");
                },
                error: function () {
                    handlerMessageError("Error al guardar valores de referencia");
                }
            });
            
        }
        
    });