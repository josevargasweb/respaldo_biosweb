var idTestVR = null;
var counter = 1;
//var valReferencia = new ConfiguracionValoresReferencia();
var tableValoresReferencia = null;
var metodosTest;

function selectCallback(data){
    initTablaValoresReferencia(data.cfgTest.ctIdtest);
}

function initTablaValoresReferencia(testId){
    console.log(testId);
    idTestVR = testId;
    metodosTest = new Array();
    $("#divVR").show();
    if (!$.fn.dataTable.isDataTable( '#tablaValoresReferencia')) {
        initDatatable(idTestVR);
    } else {
        let newUrl = getctx + "/api/valoresReferencia/list/"+idTestVR;
        tableValoresReferencia.ajax.url(newUrl).load();
    }
    if (idTestVR>0){
        $.ajax({
            type: "GET",
            url: getctx + "/api/metodos/test/" + idTestVR,
            datatype: "json",
            async: false,
            success: function(data){
                metodosTest = data.dato;
            },
            error: function (msg) {
                handlerMessageError(msg);
            }
        });
    } else {
        vt.metodosTemp.forEach((metodo)=>{
            metodosTest.push(metodo.dataValue);
        });
    }
    
}
    
function initDatatable(idTest){
    tableValoresReferencia = $('#tablaValoresReferencia').DataTable({
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
                url: getctx + "/api/valoresReferencia/list/"+idTest,
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
                    className: "bg-light-success",
                    render: function ( data, type, row ) {
                        
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                        ${estadoParrafobuttonNumber(data,"yearsDesde"+dataId,dataId,"cvrAnosdesde")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 2,
                    className: "bg-light-success",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                        ${estadoParrafobuttonNumber(data,"mesesDesde"+dataId,dataId,"cvrMesesdesde")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 3,
                    className: "bg-light-success",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                        ${estadoParrafobuttonNumber(data,"diasDesde"+dataId,dataId,"cvrDiasdesde")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 4,
                    className: "bg-light-primary",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                        ${estadoParrafobuttonNumber(data,"yearsHasta"+dataId,dataId,"cvrAnoshasta")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 5,
                    className: "bg-light-primary",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                        ${estadoParrafobuttonNumber(data,"mesesHasta"+dataId,dataId,"cvrMeseshasta")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 6,
                    className: "bg-light-primary",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                        ${estadoParrafobuttonNumber(data,"diasHasta"+dataId,dataId,"cvrDiashasta")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 7,
                    title: "Métodos",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        return cargarMetodos(metodosTest, data, dataId);
                    }
                },
                {
                    targets: 8,
                    className: "text-danger",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                            ${estadoParrafobuttonNumber(data,"bajoCritico"+dataId,dataId,"cvrValorcriticobajo","text-danger")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 9,
                    className: "text-primary",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                        ${estadoParrafobuttonNumber(data,"bajo"+dataId,dataId,"cvrValorbajo","text-primary")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 10,
                    className: "text-primary",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                        ${estadoParrafobuttonNumber(data,"alto"+dataId,dataId,"cvrValoralto","text-primary")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 11,
                    className: "text-danger",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                        ${estadoParrafobuttonNumber(data,"altoCritico"+dataId,dataId,"cvrValorcriticoalto","text-danger")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 12,
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
                        let html = `<div class="row justify-content-center">
                                            ${estadoParrafobutton(data,"texto"+dataId,dataId,"cvrValortexto","text-danger")}
                                    </div>`;
                        return html;
                    }
                },
                {
                    targets: 13,
                    title: "Borrar",
                    render: function ( data, type, row ) {
                        let dataId = row['cvrIdvalorreferencia'] || '_'+counter;
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
            createdRow: function(row, data, dataIndex, cells) {
                
            },
            select: {
                style: "os",
                selector: "td",
            },
            language: {
                url: "//cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json",
                select: {
                    rows: {
                        _: "%d filas seleccionadas",
                        0: "",
                        1: "1 fila seleccionada",
                    },
                },
            },
        });
        
        $('#tablaValoresReferencia tbody').on('blur', 'input', function (e) {
            let input = e.target;
            let value = $(input).val();
            //let classname = input.className.trim();
            //let rowName = classname.replace("form-control form-control-sm ", "").trim();
            let className = $(input).attr("data-class").trim();
            // let dataId = $(input).parent().attr("data-set");
            let dataId = $(input).attr("data-set");
            let fila = tableValoresReferencia.row(`#${dataId}`).data();
            fila[className] = value;
            $(this).parent().parent().find('a').show();
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
            let input = e.currentTarget;
            let dataId = $(input).attr("data-set");

            let modalEliminar = `
            <h5 class="col-12 pl-0 pr-0 mt-3 mb-3 text-center">Eliminar valor de referencia</h5>
                ¿Confirma que desea eliminar valor de referencia?
            <div class="col-12 d-flex justify-content-end">
                <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 ${$(input).attr("data-set")}no-examen">No</button>
                <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  ${$(input).attr("data-set")}-si-examen">Si</button>
            </div>
            `;
            $("#usuarioElimina").modal();
            $("#usuarioElimina").find(".modal-body").html(modalEliminar);

            $("#usuarioElimina").on('click',function(e) {
                if($(e.target).hasClass(`${$(input).attr("data-set")}-si-examen`)){
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
                    tableValoresReferencia.row('.selected').remove().draw(false);
                    $("#usuarioElimina").modal('hide');
                }else{
                    $("#usuarioElimina").modal('hide');
                }
            });   

            // let textConfirm = "¿Confirma que desea eliminar valor de referencia?";
            // if (confirm(textConfirm) === true) {
          
            // }
        });
}
    
//}

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

$('#addRow').on('click', function () {
    let cfgmetodo = null;
    //dejar metodo seleccionado por defecto
    if (metodosTest!==null){
        metodosTest.forEach((met) => {
            if (met.cmtEsvalorpordefecto==='S'){
                cfgmetodo = {
                    "cmetIdmetodo": met.cmetIdmetodo
                };;
            }
        });
    }
    tableValoresReferencia.row.add({
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
        'cvrIdtest': idTestVR
    })
    .draw(false)
    .node().id = '_'+counter;

    counter++;
});

$('#addRow').click();

//Edit rows
function cambiarEstadoParrafobutton(text,id,clase){
  let input = $(`<input id="${id}" type="text" class="form-control form-control-sm ${clase}" value="${text}" size="5" />`);
   $('#'+id).text('').append(input);
   $('#'+id).parent().find('a').hide();
   
}

function estadoParrafobutton(text,id,dataId,clase,clase2 = ""){
    return `<input data-set="${dataId}" data-class="${clase}" id="${id}" type="text" class="form-control form-control-sm ${clase} ${clase2}" value="${text}" size="5" />`;
  
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
    
  let input = $(`<input id="${id}" type="number" class="form-control form-control-sm ${clase}" max="${max}" step="${step}" value="${text}" style="width:60px" />`);
   $('#'+id).text('').append(input);
   $('#'+id).parent().find('a').hide();
   
}
function estadoParrafobuttonNumber(text,id,dataId,clase,clase2 = ""){
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
    
  return `<input data-set="${dataId}" data-class="${clase}" id="${id}" type="text" class="form-control form-control-sm ${clase2}" max="${max}" step="${step}" value="${text}" style="width:60px" />`;
   
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

function realizarComprobacion(fila){
    if ((fila.cvrAnosdesde == 0 && fila.cvrMesesdesde == 0 && fila.cvrDiasdesde == 0)
        || (fila.cvrAnoshasta == 0 && fila.cvrMeseshasta == 0 && fila.cvrDiashasta == 0)){
        return false;
    } 
    return true;
}

function validarFechasDesdeHasta(fila){
    if (parseInt(fila.cvrAnosdesde) > parseInt(fila.cvrAnoshasta)){
        handlerMessageError("Año desde no puede ser mayor a año hasta");
        return false;
    } else if (parseInt(fila.cvrAnosdesde) === parseInt(fila.cvrAnoshasta)){
        if (parseInt(fila.cvrMesesdesde) > parseInt(fila.cvrMeseshasta)) {
            handlerMessageError("Mes desde no puede ser mayor a mes hasta");
            return false;
        } else if (parseInt(fila.cvrMesesdesdeesDesde) === parseInt(fila.cvrMeseshasta)){
            if (parseInt(fila.cvrDiasdesde) > parseInt(fila.cvrDiashasta)) {
                handlerMessageError("Día desde no puede ser mayor a día hasta");
                return false;
            }
        }
    }
    return true;
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
    let resultado = dias + diasMeses + diasYears;
    return parseInt(resultado);
}

function validarDatosVR(filas){
	console.log('entro a validar datos bvr')
    let n = filas.data().length;
    for (let i=0; i<n; i++){
        if (filas[i].cvrAnosdesde==0 && filas[i].cvrMesesdesde==0 && filas[i].cvrDiasdesde==0 &&
               filas[i].cvrAnoshasta==0 && filas[i].cvrMeseshasta==0 && filas[i].cvrDiashasta==0){
            handlerMessageError("Debe definir al menos un valor Desde o Hasta");
            return false;
        }
        if (filas[i].cvrValorbajo==0 && filas[i].cvrValoralto==0){
            handlerMessageError("Debe definir al menos un valor bajo o valor alto");
            return false;
        }
        if (filas[i].cfgMetodos===null){
            handlerMessageError("Debe seleccionar método");
            return false;
        }
        if (filas[i].cvrSexo===null){
            handlerMessageError("Debe definir sexo");
            return false;
        }
        let comprobar = realizarComprobacion(filas[i]);
        if (comprobar === true){
            if (validarFechasDesdeHasta(filas[i])==true){
                let iDesde = calcularDiasMesYear(filas[i].cvrDiasdesde, filas[i].cvrMesesdesde, filas[i].cvrAnosdesde);
                let iHasta = calcularDiasMesYear(filas[i].cvrDiashasta, filas[i].cvrMeseshasta, filas[i].cvrAnoshasta);
                for (let j=0; j<n; j++){
                    if (filas[i]!=filas[j]){
                        if (filas[i].cfgMetodos.cmetIdmetodo == filas[j].cfgMetodos.cmetIdmetodo){
                            if (filas[j].cvrSexo == 'A' || filas[i].cvrSexo == filas[j].cvrSexo){
                                let jDesde = calcularDiasMesYear(filas[j].cvrDiasdesde, filas[j].cvrMesesdesde, filas[j].cvrAnosdesde);
                                let jHasta = calcularDiasMesYear(filas[j].cvrDiashasta, filas[j].cvrMeseshasta, filas[j].cvrAnoshasta);
                                console.log(jDesde)
                                console.log(jHasta)
                                if ((iDesde > jDesde || iHasta > jDesde) && (iDesde < jHasta || iHasta < jHasta)){
                                    handlerMessageError("Datos ingresados se traslapan con otras columnas");
                                    return false;
                                }
                            }
                        }
                    }
                }
            } else {
                return false;
            }
        }
    }
    return true;
}
