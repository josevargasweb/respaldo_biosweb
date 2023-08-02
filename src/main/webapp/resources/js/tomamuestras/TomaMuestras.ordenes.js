/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var table = null;//tableOrdenes
var tableMuestras = null;//tableMuestras
var tableTipoMuestras = null;
const sesionUsr = $('#sesionUsuario').val();
const procedenciaUsr = $('#procedenciaUsuario').val();
const isFlebotomista = $('#flebotomista').val();
var procedenciaDesc = "";
var servicioDesc = "";
var targetIdServicio = 13;
var timeoutTC = null;
var dia = 0;
var mes = 0;
var year = 0;
var registros_ordenes = 0;
var nOrden = 0;
let arrayTable = [];
let estadosOrdenArray = new Array();
//rellenar cabecera filtros
var estadosOrdenTM = new CfgEstadosOrden();
var servicioProcedenciasTM = new CfgProcedencia();
var servicioServiciosTM = new CfgServicios();
var flebotomistaTM = new Cfgflebotomista();

//rellenar td


class addValoresIntoDatatable extends ManejadorDatos {
    constructor() {
        super();
        this.datosOriginales = [];
    }

    agregarLimpiarDatosOriginales(elementos) {
        this.datosOriginales = [];
        this.datosOriginales = [...elementos];
    }

    agregarYLimpiarElementos(elementos) {
        this.limpiarArray();
        this.array = [...elementos];
    }

    addToDataTable(idTabla, datosEnviados, valorBuscado, propiedad) {
        this.agregarYLimpiarElementos(datosEnviados)
        let datosFiltrados = this.filtrarPorValorProiedad(propiedad, valorBuscado);
        console.log(datosFiltrados,'datosFiltrados')
        let tabla = $(`#${idTabla}`).DataTable();
        tabla.clear().rows.add(datosFiltrados).draw();
    }
}

let manejadorDatos = new addValoresIntoDatatable();

let estadosOrden = function () {
    var jqXHR = $.ajax({
        type: "GET",
        url: getctx + "/api/tomaMuestras/estadosOrden",
        datatype: "json",
        async: false,
    });
    return JSON.parse(jqXHR.responseText);
};

function cargarEstadosOrdenArray(estadosOrden) {
    estadosOrden.dato.forEach(element => {

        let estado = {
            "id": element.ldvfetIdfichaestadotm,
            "descripcion": element.ldvfetDescripcion
        }
        estadosOrdenArray.push(estado);

    });
}


function cargarEstadosOrden(estados, select) {
    let option = "";
    estados.forEach(element => {
        // A1QUI SE AGREGO IF ELEMENT.ID === 1  POR CRISTIAN 14-02 *****************************
        if (element.id === 2) {
            option += `<option  value="${element.descripcion}" selected>${element.descripcion}</option>`;

        } else {
            if (element.id === 1) {
                option += `<option disable value="${element.descripcion}">${element.descripcion}</option>`;
            } else {
                option += `<option value="${element.descripcion}">${element.descripcion}</option>`;
            }

        }



    });
    select.append(option);
}


$(document).ready(function() {
    $('#filtroFecha').datepicker({
        language: 'es',
        endDate: "today",
        format: "dd-mm-yyyy",
        todayHighlight: true,
        autoclose: true
    })
    console.log(today)
    $('#filtroFecha').val(today);
    $('#filtroFecha').datepicker()
    .change(function () {
        let url = "";
        if ($('#filtroFecha').val() !== "") {
            let fechaTM = $('#filtroFecha').val();
            let fechaMoment = moment(fechaTM, 'DD-MM-YYYY');
            let dia = fechaMoment.format('DD');
            let mes = fechaMoment.format('MM');
            let year = fechaMoment.format('YYYY');

            for (let i = 0; i < 13; i++) {
                if (i !== 5)
                    table
                        .column(i)
                        .search("", false, false)
                        .draw();

            }
            $('#divTablaMuestras').css('display', 'none');
            url = getctx + "/api/tomaMuestras/ordenes/" + dia + "/" + mes + "/" + year;
        } else {
            url = getctx + "/api/tomaMuestras/ordenes/0/0/0";
            $('#filtroFecha').val(today);
        }
        table.ajax.url(url).load();
        $('input[data-col-index="2"]').val('');
        table.draw();
        tableMuestras.draw();
    });
  });


var initTableOrdenes = function () {
    cfgGetEstadoOrden(estadosOrdenTM)
    getProcedencias(servicioProcedenciasTM)
    getServicios(servicioServiciosTM)
    getflebotomista(flebotomistaTM)

    // begin first table
    table = $('#tablaPrioridadAtencion').DataTable({
        pageLength: 5,
        lengthChange: false,
        paging: false,
        searchDelay: 500,
        orderCellsTop: true,
        info: false,
        scrollX: true,
        scrollY: calcularHeightSolo("divTablaOrdenes"),
        "ajax": {
            url: getctx + "/api/tomaMuestras/ordenes/" + dia + "/" + mes + "/" + year,
            type: "POST",
            dataType: "json",
            "dataSrc": function (response) {
                let resultado = response.dato;

                if (resultado != null) {
                    resultado.sort(function (a, b) {
                        if (a.codigobarras != null && b.codigobarras != null && a.codigobarras != "" && b.codigobarras != "") {
                            return a.codigobarras - b.codigobarras;
                        }
                    });
                }

                manejadorDatos.agregarLimpiarDatosOriginales(resultado)
                return resultado;
            },
            processing: true,
        },
        serverSide: true,
        "columns": [
            { data: 'Seleccion', },
            { data: 'fechaorden'},
            { data: 'norden'},
            { data: 'nombres'},
            { data: 'fechanac'},
            { data: 'estado', type: 'select'},
            { data: 'prioridadatencion'},
            { data: 'observacion'},
            { data: 'tipoatencion'},
            { data: 'procedencia', type: 'div'},
            { data: 'servicio', type: 'div'},
            // { data: 'prioridadatencion'},
            { data: 'Acciones'},
        ],
        "columnDefs": [
            { targets: [0,3,4,5,6,7,8,9,10,11], orderable: false },
            {
                width: "6.839665340831162%",
                targets: 11,
                title: 'Acciones',
                className: "notTMClick truncate",
                render: function (data, type, row) {
                    const estadoAntecedente = row.estadoantecedentetm === 'N' ? true : false;
                    let datos = JSON.stringify(row)
                    return '\<div class="row btn-toolbar">\
                                            <a href="#" id="btnModalPacientes'+ row['norden'] + '" class="p-2" title="Datos Paciente" data-toggle="modal" data-target="#ModalDatosPaciente" onclick=\'MostrarDatosPaciente(' + row['norden'] + ') \'>\
                                                <i class="fas fa-user-md"></i><span class="nav-text"></span>\
                                            </a>\
                                            <a href="#" id="btnModalAntecedentes'+ row['norden'] + '" class="p-2 ' + (estadoAntecedente ? 'disabled' : '') + '" title="Antecedentes" data-toggle="modal" data-target="#modalAntecedentesPac" onclick=\'mostrarModalAntecentesPac(' + row['norden'] + ',1) \'>\
                                                <i class="fas fa-notes-medical" '+ (estadoAntecedente ? 'style="color:#858383 !important;"' : '') + '></i><span class="nav-text"></span>\
                                            </a>\
                                            <a href="#" id="btnModalOrden'+ row['norden'] + '" class="p-2" title="Orden/Exámenes" data-toggle="modal" data-target="#ModalDatosOrden" onclick=\'MostrarDatosOrden(' + row['norden'] + ') \'>\
						<i class="fa fa-search" aria-hidden="true"></i><span class="nav-text"></span>\
                                            </a>\
                                        </div>';
                },

            }, 
            // {
            //     //Urgente
            //     width: "6.266904402688246%",
            //     targets: 11,
            //     className: "dt-center notTMClick truncate",
            //     render: function (data, type, row) {
            //         let eestado = row.estado
            //         let estadoCheck = "";
            //         let disabled = "";
            //         if (isFlebotomista === 'N' || eestado === 'ATENDIDO' || (isFlebotomista === 'S' && data === 'URGENTE')) {
            //             disabled = "disabled";
            //         }
            //         if (data === 'URGENTE') {
            //             estadoCheck = "checked";
            //         }

                    
            //         return `
            //                 <div>
            //                     <input onclick="cambiarTipoAtencionUrgente(${row['norden']})" value="${data}" type="checkbox" class="checkboxTablaOrdenes" ${disabled} ${estadoCheck}>
            //                 </div>
            //     `;
            //     }
            // },
            {
                //Servicio
                width: "11.6071869428062%",
                targets: 10,
                className: "truncate",
                render: function (data, type, row) {
                    let texto = row.idservicio > 0 ? data : "";
                    // return `${largoTexto2(data != null ? texto : '', 15)}`
                    return `<div>${cutText(data != null ? texto : '', 15)}</div>`;
                }
            },
            {
                //Procedencia
                width: "9.828007132080647%",
                targets: 9,
                className: "truncate",
                render: function (data, type, row) {
                    return `<div title="${data != null ? data : ''}">${largoTexto2(data != null ? data : '', 15)}</div>`
                }
            }
            , {
                //Tipo de Atención
                width: "10.46769990399122%",
                targets: 8,
                className: "truncate dt-center",
                render: function (data, type, full, meta) {

                    let bgColor = "";
                    switch (data.trim()) {
                        case 'URGENCIA':
                            bgColor = "#FF9AA2";
                            break;
                        case 'AMBULATORIO':
                            bgColor = "#C7CEEA";
                            break;
                        case 'HOSPITALIZADO':
                            bgColor = "#FFF9AA";
                            break;
                        default:
                            bgColor = "#808080";
                            break;
                    }
                    // return data;
                    return `<span style="background-color:${bgColor};" class="label label-lg font-weight-bold label-estado">${data}</span>`;

                },
            },
            {
                //Observacion
                width: "8.25587710876423%",
                targets: 7,
                className: "truncate",
                render: function (data, type, row) {
                    return `<div title="${data != null ? data : ''}">${largoTexto2(data != null ? data : '', 10)}</div>`
                }
            },
            {
                //Prioridad
                width: "10.23061308462488%",
                targets: 6,
                className: "notTMClick truncate",
                render: function (data, type, row) {
                    if (isFlebotomista === 'S' && row.estado !== 'ATENDIDO' && data.trim() !== 'URGENTE') {

                        let select = cargarEstadosPrioridad(
                            ['URGENTE'],
                            data.trim(),
                            row["norden"],
                            row["colorprio"],
                            11
                        )

                        return select.prop('outerHTML')
                                                
                        return `<span style="color:#000; background-color:${row["colorprio"]};" class="label label-lg font-weight-bold label-inline" title="${data != null ? data : ''}">${largoTexto2(data, 11)}</span>`
                    }else{
                        // return data;
                        return `<span style="color:#000; background-color:${row["colorprio"]};" class="label label-lg font-weight-bold label-estado select-label" title="${data != null ? data : ''}">${largoTexto2(data, 11)}</span>`
                    }

                }
            }, {
                //Estado
                width: "10.89672198601015%",
                targets: 5,
                className: "notTMClick truncate",
                render: function (data, type, row, meta) {
                    let bgColor = "";
                    switch (data.trim()) {
                        case 'ATENDIDO':
                            bgColor = "#ACECD6";
                            break;
                        case 'ESPERA':
                            bgColor = "#FF9AA2";
                            break;
                        case 'BLOQUEADA':
                            bgColor = "#DFC7C1";
                            break;
                        default:
                            bgColor = "#808080";
                            break;
                    }
                    if (isFlebotomista === 'S') {
                        // console.log(estadosOrdenTM.misEstados,'estadosOrdenTM')
                        // return data;
                        let select = cargarEstados(
                            estadosOrdenTM.misEstados,
                            data.trim(),
                            row["norden"],
                        )

                        return select.prop('outerHTML')

                    } else {
                        // return data;
                        return `<div id="lblEstadoPac${row['norden']}" >
                                    <span style="background-color:${bgColor}" class="label label-lg font-weight-bold label-estado">
                                    ${data.trim()}
                                    </span>
                                </div>`;

                    }
                }

            }, {
                //Edad
                width: "6.222466054039226%",
                targets: 4,
                className: "truncate",
                render: function (data, type, full, meta) {
                    if (data !== null) {
                        return `<div>${calcularEdadTM(data)}</div>`;
                    }
                    return `<div></div>`;
                }
            }, {
                //Nombres
                width: "10.516938691537512%",
                targets: 3,
                className: "truncate",
                render: function (data, type, full, meta) {
                    const lower = data.toLowerCase();
                    const arr = lower.split(" ");
                    for (var i = 0; i < arr.length; i++) {
                        arr[i] = arr[i].charAt(0).toUpperCase() + arr[i].slice(1);
                    }
                    const nombre = arr.join(" ");
                    return `<div>${cutText(data, 20)}</div>`;
                }
            },{
                //Orden
                width: "5.4209299135921%",
                targets: 2,
                className: "dt-center truncate",
                orderable:true,
                render: function (data, type, row) {
                    return `<div>${data}</div>`
                }
            },{
                //Fecha
                width: "5.4209299135921%",
                targets: 1,
                orderable:true,
                className: "truncate",
                render: function (data, type, full, meta) {
                    var dia = data.substring(0, 2);
                    var mes = data.substring(3, 5);
                    var year = data.substring(6, 10);
                    var hora = data.substring(11, 16);

                    return `<div>${hora}</div>`;
                }
            },{
                //Selección
                width: "4.292963928130572%",
                targets: 0,
                className: 'dt-center truncate',
                orderable:false,
                render: function (data, type, row) {
                    let tmclick = row['tmclick'];
                    let arr = tmclick.split("/");
                    let display = (tmclick === 'N') ? "none" : "inline-block";
                    let color = arr[0] == $("#sesionUsuario").val() ? 'miUserClass' : '';
                    return "<div><span id='select" + row['norden'] + "' class='seleccion'  style='padding-top: 3px; display:" + display + ";'>\
                                    <img class='' src='" + getctx + "/resources/img/select.png' width='25' height='25' title='" + arr[1] + "'></img></span></div>";
                }
            },

        ],
        initComplete: function () {

            //se agrega filtros
            let rowFilter = $('<tr class="filterOrden"></tr>').appendTo($(table.table().header()));
            this.api().columns().every(function () {
                let columna = this;
                genColFilter(columna.index(), rowFilter,columna);
            });
        },
        createdRow: function (row, data, index) {
            let tmclick = data['tmclick'];
            //console.log(data,'data toma')
            let arr = tmclick.split("/");
            if (tmclick !== 'N' && arr[0] == $("#sesionUsuario").val()) {
                $(row).addClass('muestra-blue');
            }
            
            this.api().columns().every(function () {
                let columna = this;
                genSetColFilter(columna.index(),columna);
            });
        },
        //select: true,
        select: {
            style: 'single',
            className: 'selected',
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
        },
        autoWidth: false,

    });

    table.on('draw', function () {
        if (table.settings()[0].oFeatures.bServerSide){
            table.settings()[0].oFeatures.bServerSide = false;
            table.draw();
        }
    
        registros = table.rows().count();
        let rows_filtro = table.rows({ search: 'applied' }).count();
    
        // DeleteAddClassdataTable('#tablaPrioridadAtencion_wrapper', rows_filtro);
    
        $("#dt_registros").html("Mostrando <b>" + rows_filtro + "</b> de <b>" + manejadorDatos.datosOriginales.length + "</b> órdenes en total");
    
        $('#tablaOrdenesCollapse').removeClass('hidden');
        $(".spinnerContainer1").remove();
    
        
    });

    var intervalo = setInterval(function () {
        if (estado === 0) {
            table.ajax.reload(function (datos) {
                let term2 = $('select[data-col-index="10"]').val();
                filtrarTablaTest('tablaPrioridadAtencion', datos.dato, term2, 'idservicio')
            }, true); // user paging is not reset on reload
            console.log("actualizando...")
        }
    }, 50000);
    var estado = 0;

    //Actualización automática
    $('#checkRealTime').change(function () {
        console.log('estado', estado)
        if (estado === 0) {
            estado = 1;
            intervalo = setInterval(function () {
                if ($('#tablaPrioridadAtencion').is(":visible")) {
                    table.ajax.reload(function (datos) {
                        let term2 = $('select[data-col-index="10"]').val();
                        filtrarTablaTest('tablaPrioridadAtencion', datos.dato, term2, 'idservicio')
                    }, true); // user paging is not reset on reload
                    console.log("actualizando...")
                }
            }, 50000);
        }
        if ($(this).prop("checked") === false) {
            estado = 1;
            console.log("desactivando actualizacion")
            clearInterval(intervalo);
        }
        //console.log("estado: " +estado);
    });

    $('#actualizarTabla').click(function () {
        console.log("actualizando con click...")
        table.ajax.reload(function (datos) {
            let term2 = $('select[data-col-index="10"]').val();
            manejadorDatos.agregarLimpiarDatosOriginales(datos.dato)
            filtrarTablaTest('tablaPrioridadAtencion', manejadorDatos.datosOriginales, term2, 'idservicio')

        }, true); // user paging is not reset on reload
    });

    $('#tablaPrioridadAtencion tbody').on('click', 'td:not(.notTMClick)', function () {
        console.log(this);
        //let idUsuario = $('#selectUsuario').val();
        //let ordenAnt=$("#lblNroOrden").html();
        var data = table.row(this).data();
        nOrden = data['norden'];
        let idPaciente = data['idpaciente'];
        $("#lblNroOrden").html(nOrden);
        $("#lblPaciente").html(idPaciente);
        $('#btnConfirmarTomaMuestra').prop('disabled', true);
        $('#btnImpresionEtiquetas').prop('disabled', false);

        $.ajax({
            type: "POST",
            datatype: "json",
            async: false,
            url: getctx + "/api/tomaMuestras/tmClick/" + nOrden + "/" + sesionUsr,
            success: function (tc) {
                event.preventDefault();
                //console.log("Ocupado: "+tc.ocupado);
                //si es que el usuario tenía otra orden tomada anteriormente, volver a normalidad
                $.ajax({
                    type: "post",
                    url: getctx + "/api/tomaMuestras/tmClick/volverNormalidadOrdenAnt/" + nOrden + "/" + sesionUsr,
                    async: false,
                    datatype: "json",
                    success: function (data) {
                        for (let tcl of data) {
                            $('#select' + tcl.norden).css('display', 'none');
                            $('#select' + tcl.norden).attr('title', '');
                        }
                    },
                    error: function (msg) {
                        console.log(msg)
                    }
                });

                if (tc.ocupado === "N" || (tc.idusuario == sesionUsr && tc.ocupado !== "N")) {
                    table.ajax.reload(null, false);
                    //activa el icono de la mano en la columna seleccionada en la tabla de ordenes
                    $('#select' + nOrden).css('display', 'inline-block');
                    //al hacer hover sobre el icono, se muestra el usuario que está atendiendo la orden
                    //$('#select'+nOrden).attr('title', tc.username);
                    //rellenarTablaMuestras(nOrden,idPaciente);
                    if (nOrden !== 0) {
                        $('#divTablaMuestras').css('display', 'block');
                        $('#btnConfirmarTomaMuestra').removeAttr('disabled');
                        $("#tablaOrdenesCollapse").collapse('hide');
                    }
                    let newUrl = getctx + "/api/tomaMuestras/muestras/" + nOrden;

                    tableMuestras.ajax.url(newUrl).load(function () {
                        tableMuestras.rows().select();
                        $("#seleccionaTodasLasMuestras").prop("checked", true);
                    });
                    //esto hace que a los 5 min (300000 msegs) se actualice el tmClick a "N" (volver a normalidad)
                    if (timeoutTC !== null) {
                        clearInterval(timeoutTC);
                    }
                    timeoutTC = setTimeout(function () {
                        console.log("La orden " + nOrden + " ha vuelto a la normalidad. TMClick se ha inactivado para esta orden.");
                        //$.notify({ message: "La orden " + nOrden + " ha vuelto a la normalidad." }, { type: "info" });
                        $.ajax({
                            type: "post",
                            url: getctx + "/api/tomaMuestras/tmClick/volverNormalidadOrden/" + nOrden,
                            async: false,
                            datatype: "json",
                            success: function (msg) {
                                $('#select' + nOrden).css('display', 'none');
                                $('#select' + nOrden).attr('title', '');
                            },
                            error: function (msg) {
                                console.log(msg)
                            }
                        });
                    }, 300000);
                } else {
                    handlerMessageError("Orden ya está siendo atendida");
                }
            },
            error: function (msg) {
                console.log(msg);
            }
        });


    });

}

function cambiarTipoAtencionUrgente(nOrden) {
    console.log('entra aca o no?')
    $.ajax({
        type: "patch",
        url: getctx + "/api/tomaMuestras/tipoAtencionUrgente/" + nOrden,
        datatype: "json",
        success: function (data) {
            table.ajax.reload(null, false);
            handlerMessageOk(data.message);
        },
        error: function () {
            handlerMessageError("Error");
        }
    });
}

function selectCambioEstado(estado, nOrden, fecha) {
    $.ajax({
        type: "POST",
        data: "selectEstadoPaciente=1",
        datatype: "json",
        success: function (data) {
            $("#lblEstadoPac" + nOrden).empty();
            let idSelect = "#selectEstado" + nOrden;
            $("#lblEstadoPac" + nOrden).html(`<select id="selectEstado${nOrden}" class="form-control form-control-sm p-1" onchange="${cambiarEstadoPaciente(this, nOrden)}"></select>`);
            var datos = JSON.parse(data);
            for (variable of datos.estados) {
                if (variable.descripcion == estado) {
                    $(idSelect).append('<option value=' + variable.id + ' selected>' + variable.descripcion + '</option>');
                } else {
                    $(idSelect).append('<option value=' + variable.id + '>' + variable.descripcion + '</option>');
                }
            }
            $("#lblEstadoPac" + nOrden).append('<button class="btn" onclick=\'cancelarCambioEstado("' + estado + '",' + nOrden + ')\'><i class="far fa-times-circle"></i></button>');
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

function cancelarCambioEstado(estado, nOrden) {
    let bgColor = "";
    switch (estado) {
        case 'ATENDIDO':
            bgColor = "#ACECD6";
            break;
        case 'ESPERA':
            bgColor = "#FF9AA2";
            break;
        case 'BLOQUEADA':
            bgColor = "#DFC7C1";
            break;
        default:
            bgColor = "#808080";
            break;
    }
    $("#lblEstadoPac" + nOrden).html('<span onclick=\'selectCambioEstado("' + estado + '",' + nOrden + ')\' style="background-color:' + bgColor + '" class="label label-lg font-weight-bold label-estado">' + estado + '\n\
                                    <button class="btn"><i class="fas fa-cog"></i></span></button>');
}

function cambiarEstadoPaciente(e, nOrden) {
    let estado = e.value;

    $.ajax({
        type: "post",
        data: "cambiarEstadoPaciente=" + nOrden + "&estado=" + estado,
        datatype: "json",
        success: function () {
            //rellenarTablaOrdenes(fechaRegs);
            table.ajax.reload(null, false);
            if (estado == 1 && estado != 2) {
                $.notify({ message: "Paciente atendido" }, { type: "success" });
            } else if (estado != 2) {
                $.notify({ message: "Se ha cambiado estado de orden" }, { type: "success" });
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}




let color = function (estado) {
    let bgColor = "";
    if (estado == "ATENDIDO") {
        bgColor = "greenlight-1";
    } else if (estado == "ESPERA") {
        bgColor = "redlight-1";
    } else if (estado == "BLOQUEADA") {
        bgColor = "pinklight-1";
    } else {
        bgColor = "leadlight-1";
    }

    return bgColor;
};




function cargarEstados(estados, estado, nOrden) {
    const select = $(`<select id="selectEstado2${nOrden}" class="form-control select-color ${color(estado)}" onchange="selectCambioEstadoOrdenes(this,'${nOrden}')"></select>`);
    estados.forEach((element) => {
        const opt = new Option(element.ldvfetDescripcion, element.ldvfetIdfichaestadotm, element.ldvfetDescripcion === estado);
        if (element.ldvfetIdfichaestadotm === 1) {
            opt.disabled = true;
        }
        select.append(opt);
    });
    return $('<div></div>').append(select);
}

function cargarEstadosPrioridad(estados, estado, nOrden, color, largoTexto) {
    const select = $(`<select class="form-control select-color" style="color:#000; background-color:${color};" onchange="cambiarTipoAtencionUrgente(${nOrden})"></select>`);
    estados.forEach((element) => {
        const truncatedValue = largoTexto && largoTexto < element.length ? element.slice(0, largoTexto) : element;
        const opt = new Option(truncatedValue, element, element === estado);
        select.append(opt);
    });

    if (estado !== "URGENTE") {
        const opt = new Option(estado, estado, true);
        select.append(opt);
    }

   

    return $('<div></div>').append(select);
}



function selectCambioEstadoOrdenes(e, nOrden) {
    console.log(e.value)
    console.log($("#selectEstado2" + nOrden + "  option:selected").val())
    $("#selectEstado2" + nOrden).removeAttr("class");
    $("#selectEstado2" + nOrden).addClass(
        "form-control select-color " +
        color($("#selectEstado2" + nOrden + "  option:selected").text())
    );
    if (e.value == 2) {
        $("#confirmModal").modal();
        $("#confirmModal").find(".modal-title").html("Cambio de estado");
        $("#confirmModal").find(".modal-text").html("<p>¿Está seguro que desea retornar la Orden al estado de Espera?</p>");

        $("#confirmModal .btn-si").click(function () {
            cambiarEstadoPaciente(e, nOrden);
            $('#confirmModal').modal('toggle')
        });

        $("#confirmModal .btn-no").click(function () {
            table.ajax.reload(null, false);
        });

        $("#confirmModal ").on('hide.bs.modal', function () {
            table.ajax.reload(null, false);
        });
    } else {
        cambiarEstadoPaciente(e, nOrden);
    }
}



function tablaOrdenesData(data) {
    nOrden = data['norden'];
    let idPaciente = data['idpaciente'];
    $("#lblNroOrden").html(nOrden);
    $("#lblPaciente").html(idPaciente);
    $('#btnConfirmarTomaMuestra').prop('disabled', true);
    //$('#btnImpresionEtiquetas').prop('disabled', true);

    $.ajax({
        type: "POST",
        datatype: "json",
        async: false,
        url: getctx + "/api/tomaMuestras/tmClick/" + nOrden + "/" + sesionUsr,
        success: function (tc) {
            event.preventDefault();
            //console.log("Ocupado: "+tc.ocupado);
            //si es que el usuario tenía otra orden tomada anteriormente, volver a normalidad
            $.ajax({
                type: "post",
                url: getctx + "/api/tomaMuestras/tmClick/volverNormalidadOrdenAnt/" + nOrden + "/" + sesionUsr,
                async: false,
                datatype: "json",
                success: function (data) {
                    for (let tcl of data) {
                        $('#select' + tcl.norden).css('display', 'none');
                        $('#select' + tcl.norden).attr('title', '');
                    }
                },
                error: function (msg) {
                    console.log(msg)
                }
            });
            if (tc.ocupado === "N" || (tc.idusuario == sesionUsr && tc.ocupado !== "N")) {
                table.ajax.reload(null, false);
                //activa el icono de la mano en la columna seleccionada en la tabla de ordenes
                $('#select' + nOrden).css('display', 'inline-block');
                //al hacer hover sobre el icono, se muestra el usuario que está atendiendo la orden
                //$('#select'+nOrden).attr('title', tc.username);
                //rellenarTablaMuestras(nOrden,idPaciente);
                if (nOrden !== 0) {
                    $('#divTablaMuestras').css('display', 'block');
                    $('#btnConfirmarTomaMuestra').removeAttr('disabled');
                    $("#tablaOrdenesCollapse").collapse('hide');
                }
                let newUrl = getctx + "/api/tomaMuestras/muestras/" + nOrden;
                tableMuestras.ajax.url(newUrl).load();
                //esto hace que a los 5 min (300000 msegs) se actualice el tmClick a "N" (volver a normalidad)
                if (timeoutTC !== null) {
                    clearInterval(timeoutTC);
                }
                timeoutTC = setTimeout(function () {
                    console.log("La orden " + nOrden + " ha vuelto a la normalidad. TMClick se ha inactivado para esta orden.");
                    //$.notify({ message: "La orden " + nOrden + " ha vuelto a la normalidad." }, { type: "info" });
                    $.ajax({
                        type: "post",
                        url: getctx + "/api/tomaMuestras/tmClick/volverNormalidadOrden/" + nOrden,
                        async: false,
                        datatype: "json",
                        success: function (msg) {
                            $('#select' + nOrden).css('display', 'none');
                            $('#select' + nOrden).attr('title', '');
                        },
                        error: function (msg) {
                            console.log(msg)
                        }
                    });
                }, 300000);
            } else {
                handlerMessageError("Orden ya está siendo atendida");
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

$('#btnReImpresionEtiquetas').click(function (e) {
    e.preventDefault();
    window.location.href = getctx + "/ImpresionEtiquetas";
});



$('[aria-expanded="true"]').on('shown.bs.collapse', function (e) {
    $("#tablaPrioridadAtencion").DataTable().columns.adjust().draw(false);
});




function filtrarTablaTest(idTabla, datosEnviados, valorBuscado, propiedad) {
    manejadorDatos.addToDataTable(idTabla, datosEnviados, valorBuscado, propiedad)
}


//modificar para que posiblemente funcione con cualquier
function agregarSaltoEntreCampos() {
    let campos = $('#formAntecedentes input, #formAntecedentes select');
    campos.off('keydown');
    campos.on('keydown', function (event) {
        if (event.key === "Tab" || event.key === "Enter") {
            event.preventDefault();
            let siguienteCampo = campos.eq(campos.index(this) + 1);
            if (siguienteCampo.length) {
                siguienteCampo.focus();
            } else {
                campos.eq(0).focus();
            }
        }
    });
}

//fecha 
function modfecha(dataNuevo) {
    let smallestDate = dataNuevo.reduce(function(minDate, obj) {
        var currentDate = new Date(obj.fechaorden.replace(/(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3"));
        if (!minDate || currentDate < minDate) {
          minDate = currentDate;
        }
        return minDate;
      }, null);
      
      var formattedDate = "";
      if (smallestDate) {
        var day = smallestDate.getDate();
        var month = smallestDate.getMonth() + 1; // Los meses en JavaScript son indexados desde 0
        var year = smallestDate.getFullYear().toString().slice(-2); // Obtener los últimos dos dígitos del año
      
        // Asegurarse de que el día y el mes tengan dos dígitos
        day = day < 10 ? "0" + day : day;
        month = month < 10 ? "0" + month : month;
      
        formattedDate = day + "-" + month + "-" + year;
      }

      return formattedDate;
}

//buscar segun dato
function buscarEstadoFilter(colIndexvalue){
    $.fn.dataTable.ext.search.push(
        function( settings, data, dataIndex ) {
            if ( settings.nTable.id !== 'tablaPrioridadAtencion' ) {
                return true;
                }
            var selected_rarities = [];
            if($('select[data-col-index="' + colIndexvalue + '"]').val() != "" && $('select[data-col-index="' + colIndexvalue + '"]').val() == $(data[5]).find(":selected").text()){
                selected_rarities.push(1);
            }else if($('select[data-col-index="' + colIndexvalue + '"]').val() == ""){
                selected_rarities.push(1);
            }
            if(selected_rarities.length !== 0) {
                return true;
            }else{
                return false;
            }
        }
        );
        table.draw();
}

// se agrega filtros
function genColFilter(colIndex, rowFilter,columna) {

    switch (colIndex) {
        case 0:
            genNoFilter(colIndex, rowFilter,4.570769230769232);
            break;
        case 2:
            genColInputFilter(colIndex, rowFilter,7.348901098901099);
            break;
        // case 1:
        //     genColDateFilter(colIndex, rowFilter,14.625714285714288);
        //     break;
        case 3:
            genColInputFilter(colIndex, rowFilter,20.449890109890116 );
            break;
        case 4:
            genNoFilter(colIndex, rowFilter,5.89285714285714);
            break;
        case 5:
            genColSelectFilter(colIndex, rowFilter,12.589230769230769);
            break;
        case 6:
            getColSelectVisibleFilter(colIndex, rowFilter,columna,13.169670329670332);
            break;
        case 7:
            genNoFilter(colIndex, rowFilter,16.08516483516483);
            break;
        case 8:
            getColSelectVisibleFilter(colIndex, rowFilter,columna,13.169670329670332);
            break;
        case 9:
            genColSelectFilter(colIndex, rowFilter,14.625714285714286);
            break;
        case 10:
            genColSelectFilter(colIndex, rowFilter,14.625714285714286);
            break;
        // case 11:
        //     getUrgente(colIndex, rowFilter,4.133384615384615);
        //     break;
        case 11:
            genLimpiar(colIndex, rowFilter,4.760846153846154);
            break;
        default:
            genNoFilter(colIndex, rowFilter);
            break;
    }
}

//input
function searchKey(e) {
    let colIndexvalue =  this.dataset["colIndex"];
   
    if(colIndexvalue == 2){
        if (e.which == 13) {
            let busquedaOrden = e.target.value.toUpperCase();
            let url = "";
            let actual = false;
            if (busquedaOrden !== "") {
                $('select[data-col-index="5"]').val('')
                url = getctx + "/api/tomaMuestras/ordenes/" + busquedaOrden;
            } else {
                actual = true;
                $('#filtroFecha').val(today);
                url = getctx + "/api/tomaMuestras/ordenes/0/0/0";
            }
            table.ajax.url(url).load(function (datos) {
                        if(datos.status == '200'){
                            if(!actual){
                                let fecha = modfecha(datos.dato);
                                if(fecha != ""){
                                  $('#filtroFecha').val(fecha);
                                }
                            }
                        }
                    })
        }else{
            let columna = $('#tablaPrioridadAtencion').DataTable().column(colIndexvalue);
            columna.search(e.target.value.toUpperCase(), true, false, false).draw();
        }

    }else{
        let columna = $('#tablaPrioridadAtencion').DataTable().column(colIndexvalue);
        columna.search(e.target.value.toUpperCase(), true, false, false).draw();
    }
}



function genColInputFilter(colIndex, rowFilter, size = 6) {
    let inputText = $(`<input type="text" class="form-control form-control-sm form-filter datatable-input" data-col-index="` + colIndex + `"/>`);
    $(inputText).appendTo($('<th>').appendTo(rowFilter));
    $(`input[data-col-index="` + colIndex + `"]`).on('keyup change clear', searchKey);
}

//input

//date

function genColDateFilter(colIndex, rowFilter,size = 6) {
    let inputText = $(`
                    <div class="input date">
                    <input type="text" id="filtroFecha" class="form-control form-control-sm datatable-input kt_datepicker_1_modal  " readonly placeholder="" id="kt_datepicker_1_modal"
                       data-col-index="` + colIndex + `"/>
                    </div>
                    `);
    $(inputText).appendTo($('<th>').appendTo(rowFilter));

    $('#filtroFecha').datepicker({
        language: 'es',
        endDate: "today",
        format: "dd-mm-yyyy",
        todayHighlight: true,
        autoclose: true
    })
    console.log(today)
    $('#filtroFecha').val(today);
    $('#filtroFecha').datepicker()
    .change(function () {
        let url = "";
        if ($('#filtroFecha').val() !== "") {
            let fechaTM = $('#filtroFecha').val();
            let fechaMoment = moment(fechaTM, 'DD-MM-YYYY');
            let dia = fechaMoment.format('DD');
            let mes = fechaMoment.format('MM');
            let year = fechaMoment.format('YYYY');

            for (let i = 0; i < 13; i++) {
                if (i !== 5)
                    table
                        .column(i)
                        .search("", false, false)
                        .draw();

            }
            $('#divTablaMuestras').css('display', 'none');
            url = getctx + "/api/tomaMuestras/ordenes/" + dia + "/" + mes + "/" + year;
        } else {
            url = getctx + "/api/tomaMuestras/ordenes/0/0/0";
            $('#filtroFecha').val(today);
        }
        table.ajax.url(url).load();
        $('input[data-col-index="1"]').val('');
    });

}

//date

//select

//rellenar los select
function setOptionEstado(colIndex) {
      function fillSelect(data) {
        let options = [];
        data.forEach(servicio => {
            let opt = new Option(servicio.ldvfetDescripcion, servicio.ldvfetDescripcion);
            options.push(opt);
        });
        let opt = new Option('TODOS', '', true, true);
        options.unshift(opt);
        return options;
    }
    return fillSelect(estadosOrdenTM.misEstados)
    
}

function setOptionProcedencia(colIndex) {

    function fillSelect(data) {
        let options = [];
        data.forEach(servicio => {
            let opt = new Option(servicio.cpDescripcion, servicio.cpDescripcion);
            options.push(opt);
        });
        let opt = new Option('TODOS', '', true, true);
        options.unshift(opt);
        return options;
    }
    return fillSelect(servicioProcedenciasTM.misProcedencias)
  }


  function setOptionServicios(colIndex) {
    function fillSelect(data) {
        let options = [];
        data.forEach(servicio => {
            let opt = new Option(servicio.csDescripcion, servicio.csIdservicio);
            options.push(opt);
        });
        let opt = new Option('TODOS', '', true, true);
        options.unshift(opt);
        return options;
    }
    return fillSelect(servicioServiciosTM.misServicios)
  
  }
  
  


function setOptionSelCol(colIndex) {

    switch (colIndex) {
        case 5:
            return setOptionEstado(colIndex);
        case 9:
            return setOptionProcedencia(colIndex);
        case 10 :
            return setOptionServicios(colIndex);
        default:
            break;
    }
}

function genInputSelect(colIndex, size = 6) {
    let selectElement = $('<select>', {class: 'form-control form-control-sm form-filter datatable-input', 'data-col-index': colIndex,});
    let options = setOptionSelCol(colIndex);
    options.forEach(option => selectElement.append(option));
    return selectElement;
}



function searchValue(e) {
    let colIndexvalue =  this.dataset["colIndex"];
    if (colIndexvalue==5){

        buscarEstadoFilter(colIndexvalue)
    }
    else if(colIndexvalue == 10){
        let term2 = $('select[data-col-index="10"]').val();
        filtrarTablaTest('tablaPrioridadAtencion',manejadorDatos.datosOriginales,term2, 'idservicio')
    }
    else{
        let columna = $('#tablaPrioridadAtencion').DataTable().column(this.dataset["colIndex"]);
        console.log('searchValue ' + this.dataset["colIndex"]);
        columna.search(this.value, true, false, false).draw();
    }
}

function genColSelectFilter(colIndex, rowFilter, size = 6) {
    let inputSelect = genInputSelect(colIndex, size);
    $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
    $(`select[data-col-index="` + colIndex + `"]`).change(searchValue);
    if(colIndex === 5){
        $(`select[data-col-index="` + colIndex + `"]`).val("ESPERA");
        buscarEstadoFilter(colIndex)
    }
}
//select

// select con datos visibles solamente

function genInputSelectVisible(colIndex, col, size = 6) {
    function fillSelect(data) {
        let options = [];
        data.data().unique().sort().each(servicio => {
            let opt = new Option(servicio, servicio);
            options.push(opt);
        });
        let opt = new Option('TODOS', '', true, true);
        options.unshift(opt);
        return options;
    };

    let selectElement = $('<select>', {class: 'form-control form-control-sm form-filter datatable-input', 'data-col-index': colIndex,});
    let options = fillSelect(col);
    options.forEach(option => selectElement.append(option));
    return selectElement;
}



function searchValueVisible(e) {
    let columna = $('#tablaPrioridadAtencion').DataTable().column(this.dataset["colIndex"]);
    console.log('searchValueVisible ' + this.dataset["colIndex"]);
    columna.search(this.value, true, false, false).draw();
}

function getColSelectVisibleFilter(colIndex, rowFilter,col,size = 6) {
    let inputSelect = genInputSelectVisible(colIndex,col, size);
    $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
    $(`select[data-col-index="` + colIndex + `"]`).change(searchValueVisible);
}

//


//checkbox
function getUrgente(colIndex, rowFilter) {
    let inputUrgente = $('<input type="checkbox" class="" data-col-index="'+ colIndex +'">');
    $(inputUrgente).appendTo($('<th class="dt-center">').appendTo(rowFilter));

    let busqueda = "";
    inputUrgente.on('change', function () {
        console.log('entra aca')
        $('#divTablaMuestras').css('display', 'none');
        if($(this).prop("checked") === true){
            busqueda = "URGENTE";
        } else {
            busqueda = "";
        }
        table
            .column(6)
            .search(busqueda, false, false)
            .draw();
    });

}

//checkbox


// limpiar
function genLimpiar(colIndex, rowFilter,size = 6) {
    let inputLimpiar = $(`<div >
                            <span class="symbol symbol-30 symbol-circle">
                                <span id="limpiarFiltroTablaOrdenes" data-toggle="tooltip" title="Limpiar" class="symbol-label bg-hover-blue ">
                                    <i id="" class="la la-broom icon-xl  text-blue"></i>
                                </span>
                            </span>
                        </div>
                         `);
    $(inputLimpiar).appendTo($('<th>').appendTo(rowFilter));
    $("#limpiarFiltroTablaOrdenes").click(limpiarFiltroOrdenes);
}

function limpiarFiltroOrdenes() {
    $('#divTablaMuestras').css('display', 'none');
    $('.selectFiltro').val("");
    $('.txtFiltro').val("");
    for (let i = 0; i < 14; i++) {
        if (i === 5) {
            $('select[data-col-index="' + i + '"]').val("ESPERA")
            // $("#statusPaciente option[value='ESPERA']").prop('selected', true);
            // table.column(5).search("ESPERA").draw();
            $.fn.dataTable.ext.search.push(
                function( settings, data, dataIndex ) {
                    if ( settings.nTable.id !== 'tablaPrioridadAtencion' ) {
                        return true;
                        }
                    var selected_rarities = [];
                    if($('select[data-col-index="' + i + '"]').val() != "" && $('select[data-col-index="' + i + '"]').val() == $(data[5]).find(":selected").text()){
                        selected_rarities.push(1);
                    }else if($('select[data-col-index="' + i + '"]').val() == ""){
                        selected_rarities.push(1);
                    }
                    if(selected_rarities.length !== 0) {
                        return true;
                    }else{
                        return false;
                    }
                }
                );
            table.draw();
        } else if (i === 9) {
            // if (procedenciaUsr > 0) {
            //     $('select[data-col-index="9"]').val(procedenciaUsr);
            //     table.column(9).search(procedenciaDesc).draw();
            // }

            $('select[data-col-index="9"]').val('');
                table.column(9).search('').draw();
        } else if (i === 13) {
            $('select[data-col-index="10"]').val(servicioDesc);
            // $("#servicioList option[value='" + servicioDesc + "']").prop("selected", true);
            // table.column(13).search(servicioDesc).draw();

            let term2 = servicioDesc;
            filtrarTablaTest('tablaPrioridadAtencion', manejadorDatos.datosOriginales, term2, 'idservicio')
        }
        else {
            table
                .column(i)
                .search("", false, false)
                .draw();
        }
    }
    $('#filtroFecha').val(today);
    let newUrl = getctx + "/api/tomaMuestras/ordenes/0/0/0";
    table.ajax.url(newUrl).load();
    // table.column(targetIdServicio).search(term, false, false).draw();
}


//no filter

function genNoFilter(colIndex, rowFilter, size = 6) {
    console.log(t1*size,' tamaño en pixeles');
    let inputText = $(`<div></div>`);
    $(inputText).appendTo($('<th>').appendTo(rowFilter));
  
}

//no filter


// modifica filtros
function setColSelectVisibleFilter(colIndex,columna) {
  
    function fillSelect(data) {
        let options = [];
        data.data().unique().sort().each(servicio => {
            let opt = new Option(servicio, servicio);
            options.push(opt);
        });
        let opt = new Option('TODOS', '', true, true);
        options.unshift(opt);
        return options;
    }

    let select = $('select[data-col-index="'+colIndex+'"]');
    select.empty();
    let options = fillSelect(columna);
    options.forEach(option => select.append(option));
}


function genSetColFilter(colIndex,columna) {

    switch (colIndex) {
        case 6:
        case 8:
            setColSelectVisibleFilter(colIndex,columna);
            break;
        default:
            break;
    }
}


// modifica filtros


  // cfg servicios
  function CfgEstadosOrden() {
    this.misEstados = [];
}

function cfgGetEstadoOrden(svc, callback) {
    let url = getctx + "/api/tomaMuestras/estadosOrden";
    $.get(url, function (data) {
        console.log(data);
        if(data.status == 200){
            svc.misEstados = data.dato;
        }
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}



function CfgProcedencia() {
    this.misProcedencias = [];
}

function getProcedencias(svc, callback) {
    let url = getctx + "/api/dominio/procedencia/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misProcedencias = data;
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}


function CfgServicios() {
    this.misServicios = [];
}


function getServicios(svc, callback) {
    let url = getctx + "/api/dominio/servicios/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misServicios = data;
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}




function Cfgflebotomista() {
    this.misFlebotomistas = [];
}


function getflebotomista(svc) {
    let url = getctx + "/api/recepcionMuestras/recepcionistas/list";
    $.get(url, function (data) {
        console.log(data);
        svc.misServicios = data;
    })
        .done(function () {
            console.log("done");
        })
        .fail(function (err) {
            console.log(err);
        });
}


//testing
  function calcularEquivalencia(pixeles,tamanoFuentePx) {
    tamanoFuentePx = tamanoFuentePx * 0.70
    return pixeles / tamanoFuentePx;
  }


  function calcularTamanoPx(cantidadCaracteres,tamanoFuentePx){
    tamanoFuentePx = tamanoFuentePx * 0.70
    return cantidadCaracteres * tamanoFuentePx;

  }



  function redibujarDatatable() {
    table.draw();
    tableMuestras.draw();
  }
  
  // se vuelven a dibujar las tablas al cambiar de dimension las tablas
  window.addEventListener('resize', redibujarDatatable);