// agregarIconoFunciones('.icon-buenos',[
//     anadir_circulo_icon,
//     anadir_icon,
//     adelante_icon,
//     adjuntar_icon,
//     agregar_icon,
//     alerta_icon,
//     alto_icon,
//     antecedentes_icon,
//     antibiograma_icon,
//     aplicar_filtro_icon,
//     archivo_icon,
//     atras_icon,
//     bajo_icon,
//     borrar_limpiar_icon,
//     buscar_icon,
//     check_icon,
//     codigo_barras_icon,
//     configuracion_icon,
//     confirmar_icon,
//     contador_icon,
//     curva_tolerancia_icon,
//     datos_basicos_icon,
//     datos_orden_icon,
//     datos_test_icon,
//     datos_paciente_icon,
//     descargar_icon,
//     enviar_email_icon,
//     examenes_icon,
//     firmar_resultados_icon,
//     flebotomista_icon,
//     formula_icon,
//     guardar_icon,
//     incluir_icon,
//     informacion_icon,
//     codigo_barras_icon,
//     limpiar_icon,
//     linea_tiempo_icon,
//     muestra_icon,
//     notas_icon,
//     notificacion_icon,
//     patologias_icon,
//     editar_icon,
//     datos_extras_icon,
//     eliminar_icon,
//     estadisticas_icon,
//     hamburguesa_icon,
//     imprimir_icon,
//     indicaciones_icon,
//     mostrar_icon,
//     ocultar_icon    
// ])

// agregarIconoFunciones('.icon-2',[
//     anadir_circulo_icon_2,
//     anadir_icon_2,
//     adelante_icon_2,
//     adjuntar_icon_2,
//     agregar_icon_2,
//     alerta_icon_2,
//     antecedentes_icon_2,
//     antibiograma_icon_2,
//     aplicar_filtro_icon_2,
//     archivo_icon_2,
//     atras_icon_2,
//     borrar_limpiar_icon_2,
//     buscar_icon_2,
//     check_icon_2,
//     codigo_barras_icon_2,
//     configuracion_icon_2,
//     confirmar_icon_2,
//     contador_icon_2,
//     curva_tolerancia_icon_2,
//     datos_basicos_icon_2,
//     datos_orden_icon_2,
//     datos_test_icon_2,
//     datos_paciente_icon_2,
//     descargar_icon_2,
//     enviar_email_icon_2,
//     examenes_icon_2,
//     firmar_resultados_icon_2,
//     flebotomista_icon_2,
//     formula_icon_2,
//     guardar_icon_2,
//     incluir_icon_2,
//     codigo_barras_icon_2,
//     limpiar_icon_2,
//     linea_tiempo_icon_2,
//     muestra_icon_2,
//     notas_icon_2,
//     notificacion_icon_2,
//     patologias_icon_2,
//     editar_icon_2,
//     datos_extras_icon_2,
//     eliminar_icon_2,
//     estadisticas_icon_2,
//     hamburguesa_icon_2,
//     imprimir_icon_2,
//     indicaciones_icon_2,
//     mostrar_icon_2,
//     ocultar_icon_2,
//     informacion_icon_2,
// ])


// agregarIconoFunciones('.icon-3',[
//     zoom_icon,
//     ver_icon,
//     documento_adjunto_icon,
//     validar_examen_icon,
//     ultima_icon,
//     trazabilidad_icon,
//     toma_muestras_icon,
//     test_opcionales_icon,
//     sitio_anatomico_icon,
//     separador_informacion_icon,
//     seleccionar_archivo_icon,
//     seleccionado_icon,
//     seleccion_examen_icon,
//     salir_icon,
//     retirar_firma_icon,
//     remover_icon,
//     refrezcar_icon,
//     rechazo_parcial_icon,
//     rechazar_icon,
//     receptor_icon,
//     recepcion_documentos_icon,
//     quitar_icon,
//     query_icon,
//     proteger_icon,
//     nueva_icon,
//     procesamiento_icon
// ])


// agregarIconoFunciones('.icon-4',[
//     test_opcionales_icon_2,
//     contador_icon_3,
//     datos_extras_icon_1,
//     contador_icon_1,
//     muestra_icon_1,
//     codigo_barras_icon_1,
//     eliminar_icon_1 
// ])

// agregarIconoFunciones('.icon-5',[
//     resultados_test_icon,
//     mostrar_icon_2,

// ])


var KTDatatableAutoColumnHideDemo = function () {
    // Private functions

    // basic demo
    var demo = function () {

        var datatable = $('.kt_datatable').KTDatatable({
            // datasource definition
            data: {

                pageSize: 10,
                saveState: false,
                serverPaging: true,
                serverFiltering: true,
                serverSorting: true,
            },

            layout: {
                scroll: false
            },

            // column sorting
            sortable: true,

            pagination: true,

            search: {
                input: $('#kt_datatable_search_query'),
                key: 'generalSearch'
            },
            scrollY: "50vh", scrollX: !0, scrollCollapse: !0,
            columnDefs: [{
                width: '75px',
                targets: 2,
                render: function (data, type, full, meta) {
                    var status = {
                        IFI: { 'title': 'IFI', 'class': ' label-light-success' },
                        ELISA: { 'title': 'Delivered', 'class': ' label-light-danger' },
                    };
                    if (typeof status[data] === 'undefined') {
                        return data;
                    }
                    return '<span class="label label-xl font-weight-bold ' + status[data].class + ' label-inline">' + status[data].title + '</span>';
                },
            },],

            // columns definition
            columns: [
                {
                    field: 'OrderID',
                    title: 'Order ID',
                }, {
                    field: 'Country',
                    title: 'Country',
                    template: function (row) {
                        return row.Country + ' ' + row.ShipCountry;
                    },
                }, {
                    field: 'CompanyEmail',
                    title: 'Email',
                    width: 'auto',
                }, {
                    field: 'ShipDate',
                    title: 'Ship Date',
                    type: 'date',
                    format: 'MM/DD/YYYY',
                }, {
                    field: 'CompanyName',
                    title: 'Company Name',
                    width: 'auto',
                }, {
                    field: 'ShipAddress',
                    title: 'Ship Address',
                }, {
                    field: 'Website',
                    title: 'Website',
                }, {
                    field: 'TotalPayment',
                    title: 'Payment',
                }, {
                    field: 'Notes',
                    title: 'Notes',
                    width: 300,
                }, {
                    field: 'Status',
                    title: 'Status',
                    // callback function support for column rendering
                    template: function (row) {
                        var status = {
                            1: { 'title': 'Pending', 'class': 'label-light-primary' },
                            2: { 'title': 'Delivered', 'class': ' label-light-danger' },
                            3: { 'title': 'Canceled', 'class': ' label-light-primary' },
                            4: { 'title': 'Success', 'class': ' label-light-success' },
                            5: { 'title': 'Info', 'class': ' label-light-info' },
                            6: { 'title': 'Danger', 'class': ' label-light-danger' },
                            7: { 'title': 'Warning', 'class': ' label-light-warning' },
                        };
                        return '<span class="label label-lg font-weight-bold' + status[row.Status].class + ' label-inline">' + status[row.Status].title + '</span>';
                    },
                }, {
                    field: 'Type',
                    title: 'Type',
                    // callback function support for column rendering
                    template: function (row) {
                        var status = {
                            1: { 'title': 'Online', 'state': 'danger' },
                            2: { 'title': 'Retail', 'state': 'primary' },
                            3: { 'title': 'Direct', 'state': 'success' },
                        };
                        return '<span class="label label-' + status[row.Type].state + ' label-dot mr-2"></span><span class="font-weight-bold text-' + status[row.Type].state + '">' +
                            status[row.Type].title + '</span>';
                    },
                }, {
                    field: 'Actions',
                    title: 'Actions',
                    sortable: false,
                    width: 125,
                    overflow: 'visible',
                    autoHide: false,
                    template: function () {
                        return '\
	                        <div class="dropdown dropdown-inline">\
	                            <a href="javascript:;" class="btn btn-sm btn-clean btn-icon mr-2" data-toggle="dropdown">\
	                                <span class="svg-icon svg-icon-md">\
	                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
	                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
	                                            <rect x="0" y="0" width="24" height="24"/>\
	                                            <path d="M5,8.6862915 L5,5 L8.6862915,5 L11.5857864,2.10050506 L14.4852814,5 L19,5 L19,9.51471863 L21.4852814,12 L19,14.4852814 L19,19 L14.4852814,19 L11.5857864,21.8994949 L8.6862915,19 L5,19 L5,15.3137085 L1.6862915,12 L5,8.6862915 Z M12,15 C13.6568542,15 15,13.6568542 15,12 C15,10.3431458 13.6568542,9 12,9 C10.3431458,9 9,10.3431458 9,12 C9,13.6568542 10.3431458,15 12,15 Z" fill="#000000"/>\
	                                        </g>\
	                                    </svg>\
	                                </span>\
	                            </a>\
	                            <div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
	                                <ul class="navi flex-column navi-hover py-2">\
	                                    <li class="navi-header font-weight-bolder text-uppercase font-size-xs text-primary pb-2">\
	                                        Choose an action:\
	                                    </li>\
	                                    <li class="navi-item">\
	                                        <a href="#" class="navi-link">\
	                                            <span class="navi-icon"><i class="la la-print"></i></span>\
	                                            <span class="navi-text">Print</span>\
	                                        </a>\
	                                    </li>\
	                                    <li class="navi-item">\
	                                        <a href="#" class="navi-link">\
	                                            <span class="navi-icon"><i class="la la-copy"></i></span>\
	                                            <span class="navi-text">Copy</span>\
	                                        </a>\
	                                    </li>\
	                                    <li class="navi-item">\
	                                        <a href="#" class="navi-link">\
	                                            <span class="navi-icon"><i class="la la-file-excel-o"></i></span>\
	                                            <span class="navi-text">Excel</span>\
	                                        </a>\
	                                    </li>\
	                                    <li class="navi-item">\
	                                        <a href="#" class="navi-link">\
	                                            <span class="navi-icon"><i class="la la-file-text-o"></i></span>\
	                                            <span class="navi-text">CSV</span>\
	                                        </a>\
	                                    </li>\
	                                    <li class="navi-item">\
	                                        <a href="#" class="navi-link">\
	                                            <span class="navi-icon"><i class="la la-file-pdf-o"></i></span>\
	                                            <span class="navi-text">PDF</span>\
	                                        </a>\
	                                    </li>\
	                                </ul>\
	                            </div>\
	                        </div>\
	                        <a href="javascript:;" class="btn btn-sm btn-clean btn-icon mr-2" title="Edit details">\
	                            <span class="svg-icon svg-icon-md">\
	                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
	                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
	                                        <rect x="0" y="0" width="24" height="24"/>\
	                                        <path d="M8,17.9148182 L8,5.96685884 C8,5.56391781 8.16211443,5.17792052 8.44982609,4.89581508 L10.965708,2.42895648 C11.5426798,1.86322723 12.4640974,1.85620921 13.0496196,2.41308426 L15.5337377,4.77566479 C15.8314604,5.0588212 16,5.45170806 16,5.86258077 L16,17.9148182 C16,18.7432453 15.3284271,19.4148182 14.5,19.4148182 L9.5,19.4148182 C8.67157288,19.4148182 8,18.7432453 8,17.9148182 Z" fill="#000000" fill-rule="nonzero"\ transform="translate(12.000000, 10.707409) rotate(-135.000000) translate(-12.000000, -10.707409) "/>\
	                                        <rect fill="#000000" opacity="0.3" x="5" y="20" width="15" height="2" rx="1"/>\
	                                    </g>\
	                                </svg>\
	                            </span>\
	                        </a>\
	                        <a href="javascript:;" class="btn btn-sm btn-clean btn-icon" title="Delete">\
	                            <span class="svg-icon svg-icon-md">\
	                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
	                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
	                                        <rect x="0" y="0" width="24" height="24"/>\
	                                        <path d="M6,8 L6,20.5 C6,21.3284271 6.67157288,22 7.5,22 L16.5,22 C17.3284271,22 18,21.3284271 18,20.5 L18,8 L6,8 Z" fill="#000000" fill-rule="nonzero"/>\
	                                        <path d="M14,4.5 L14,4 C14,3.44771525 13.5522847,3 13,3 L11,3 C10.4477153,3 10,3.44771525 10,4 L10,4.5 L5.5,4.5 C5.22385763,4.5 5,4.72385763 5,5 L5,5.5 C5,5.77614237 5.22385763,6 5.5,6 L18.5,6 C18.7761424,6 19,5.77614237 19,5.5 L19,5 C19,4.72385763 18.7761424,4.5 18.5,4.5 L14,4.5 Z" fill="#000000" opacity="0.3"/>\
	                                    </g>\
	                                </svg>\
	                            </span>\
	                        </a>\
	                    ';
                    },
                }],

        });

        $('#kt_datatable_search_status').on('change', function () {
            datatable.search($(this).val().toLowerCase(), 'Status');
        });

        $('#kt_datatable_search_type').on('change', function () {
            datatable.search($(this).val().toLowerCase(), 'Type');
        });

        $('#kt_datatable_search_status, #kt_datatable_search_type').selectpicker();
    };

    return {
        // public functions
        init: function () {
            demo();
        },
    };
}();

jQuery(document).ready(function () {

    KTDatatableAutoColumnHideDemo.init();


});

$(function () {
    // $("table").colResizable();
    rellenarTabla('');
    //rellenarTablaTM('');
});
$('#buscarIndicacionesExa').keyup(function (e) {
    e.preventDefault();
    if (e.keyCode == 13) {
        $(this).trigger("enterKey");
        if ($('#buscarIndicacionesExa').val().length < 4) {
            console.log('Insuficientes caracteres')
        } else {
            ingresarIndicacionExa($('#buscarIndicacionesExa').val().toUpperCase());
        }
        /*
        if ($('#buscarNuevoIndiExa').text() === 'Nuevo:') {
            ingresarIndicacionExa($(this).val());
        } else {
           // rellenarTabla($(this).val());
        }*/

    }
});

function rellenarTabla(buscarIndicacionesExa) {//rut
    $('#tablaIndicacionPaciente').empty();
    $.ajax({
        type: "post",
        data: "getIndicaciones=" + buscarIndicacionesExa,
        datatype: "json",
        success: function (pacientes) {
            var paciente = JSON.parse(pacientes);
            // var paciente = infoPaciente.pacientes[0]; //PACIENTE QUE SE RETORNO DESDE JAVA
            //$("#nDocumento").text('N° DOCUMENTO');
            console.table(paciente);
            //formato fecha
            var cont = 1;
            console.log(paciente)
            // variable.detalleIndicaciones

            for (variable of paciente.Indicaciones) {
                console.log('variable', variable);
                $("#tablaIndicacionPaciente").
                    append(
                        `<tr data-id-indicaciones="${variable.idIndicaciones}">
                    <td class='a' style='width: 45px;'>${cont}</td>
                    <td onclick='guardarValores(this)'>
                        <div class="input-group is-invalid container-text">
                            <div class="custom-file">
                                <p class='detalleIndicaciones col-12 pl-0 mb-0 '>${variable.detalleIndicaciones}</p>
                            </div>
                            <div class="input-group-append">
                                <a class="editProce">
                                    <i class="fas fa-pencil-alt" style="line-height: 1.5;"></i>
                                </a>
                            </div>
                        </div>
                    </td>
                </tr>`
                    );
                cont++;
            }
            $('.editProce').on("click", function () {
                var $this = $(this).closest('.container-text').find('.detalleIndicaciones');
                var numeroInicio = $this.text();
                if ($this.text() == "") {
                    return;
                }
                $('#guardadoDeCupos').text(numeroInicio);
                $(this).remove();

                var $input = $('<input>', {
                    value: $this.text(),
                    type: 'text',
                    class: 'form-control',
                    keypress: function () {
                        //validarNumero($this);
                        //alert(this.value);
                    },
                    blur: function () {
                        //$this.text(this.value);
                        //mandarCupos($this, numeroInicio);

                    },
                    keyup: function (e) {

                        if (e.which === 13)
                            updateIndicacionExa(this.value);

                    }
                }).appendTo($this.empty()).focus();
            });

        },
        error: function (msg) {
            console.log(msg);
        }
    });
}
$("#agregarIndicacionExamen").click(function () {
    if ($('#buscarIndicacionesExa').val().length < 4) {
        console.log('Insuficientes caracteres')
    } else {
        ingresarIndicacionExa($('#buscarIndicacionesExa').val() != "" ? $('#buscarIndicacionesExa').val().toUpperCase() : $('#buscarIndicacionesExa').val());
    }
});
//const getctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
function ingresarIndicacionExa(insertar) {//rut
    console.log(insertar);
    //getctx
    $.ajax({
        url: getctx + "/api/ldvIndicaciones/insert/" + insertar,
        method: 'post',
        success: function (exam) {
            $('#buscarIndicacionesExa').val('');
            rellenarTabla('');
            handlerMessageOk(exam.dato);
            // console.log(exam)
        },
        error: function (jqxhr, textStatus, error) {
            handlerMessageError("Ha ocurrido un error");
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });
}
function updateIndicacionExa(descIndicacion) {//rut

    if ($("#guardarDescripcion").val() == descIndicacion) {
        alert('ningun valor cambiado');
        return;
    }
    $.ajax({
        url: getctx + "/api/ldvIndicaciones/update",
        data: JSON.stringify({
            ldvieIdindicacionesexamen: $("#guardarId").val(),
            ldvieDescripcion: descIndicacion.toUpperCase(),
        }),
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'post',
        dataType: 'json',
        success: function (exam) {
            console.log(exam)
            if (exam.status == 200) {
                handlerMessageOk(exam.dato);
                rellenarTabla('');
            } else {
                handlerMessageWarning(exam.dato);
            }

        },
        error: function (jqxhr, textStatus, error) {
            handlerMessageError("Ha ocurrido un error");
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });
}

function guardarValores(a) {
    var $row = $(a).closest("tr"); // Find the row
    //Paciente
    var detalleIndicaciones = $row.find(".detalleIndicaciones").text(); // Find the text
    // var idIndicaciones = $row.find(".idIndicaciones").text();
    let idIndicaciones = $row.attr("data-id-indicaciones");

    $("#guardarDescripcion").val(detalleIndicaciones);
    $("#guardarId").val(idIndicaciones);

}
///////////////////////////////////////////////////////////
function FiltradoTablaKeyUp() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("buscarIndicacionesExa");
    filter = input.value.toUpperCase();
    table = document.getElementById("tablaPrioridadAtencion");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

$("#examen-main").height(calcularTamanoContenedor('examen-main', 0.9));
$("#contenedor-atencion").height(calcularTamanoContenedor('contenedor-atencion', 0.85));

