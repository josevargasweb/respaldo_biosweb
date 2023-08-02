var table = null;//tableOrdenes
// const sesionUsr = $('#sesionUsuario').val();
// const procedenciaUsr = $('#procedenciaUsuario').val();
// const isFlebotomista = $('#flebotomista').val();

//disabled y enabled inputs
$(document).ready(function () {
    $('#buscarCaja, #buscarMuestra').focus(function () {
      if ($(this).val() === '') {
        $('#buscarCaja, #buscarMuestra').not(this).prop('disabled', true);
      }
    });
  
    $('#buscarCaja, #buscarMuestra').blur(function () {
      if ($(this).val() === '') {
        $('#buscarCaja, #buscarMuestra').prop('disabled', false);
      }
    });

    const input1 = new ImaskInputCaracter('agregarEstafeta', 20);
  });

var initTable = function () {
    // let urlPrueba = "http://localhost:3000/transporte?numeroCaja=0"

    // $.ajax({
    //     url: urlPrueba,
    //     type: "GET",
    //     dataType: "json",
    //     success: function (response) {
    //         let data = imprimirDatos(response);
          
    //     },
    //     error: function (error) {
    //         console.error(error);
    //     }
    // });

    processData([]);
};
function processData(data) {
    // begin first table
    table = $('#tablaTransporte').DataTable({
        pageLength: 5,
        lengthChange: false,
        paging: false,
        searchDelay: 500,
        orderCellsTop: true,
        info: false,
        scrollX: true,
        scrollY: calcularHeightSolo("divTablaTransporte"),
        data: data,
        "columns": [
            { data: '', },
            { data: 'idmuestra'},
            { data: 'idtipomuestra'},
            { data: 'idenvase'},
            { data: 'nombreflebotomista'},
            { data: 'estadotm'},
        ],
        "columnDefs": [
            { targets: [0,3,4,5], orderable: false },
           {
                //Estado
                width: "21,62121977625406%",
                targets: 5,
                className: "notTMClick truncate",
                render: function (data, type, row, meta) {
                    return data;
                }

            }, {
                //Flebotomista
                width: "13,81948754962108%",
                targets: 4,
                className: "truncate",
                render: function (data, type, full, meta) {
                  return data;
                }
            }, {
                //Contenedor
                width: "20,86337062432335%",
                targets: 3,
                className: "truncate",
                render: function (data, type, full, meta) {
                  return data;
                }
            }, {
                //Tipo de muestras
                width: "21,46113316492241%",
                targets: 2,
                orderable:true,
                className: "truncate",
                render: function (data, type, full, meta) {
                    return data;
                }
            }, {
                //N ° muestras
                width: "12,0037531577048%",
                targets: 1,
                className: "dt-center truncate",
                orderable:true,
                render: function (data, type, row) {
                    return data;
                }
            }, {
                //Selección
                width: "10,2309635510646%",
                targets: 0,
                className: 'dt-center truncate',
                orderable:false,
                render: function (data, type, row) {
                    return `<input type="checkbox" value=""  data-colselector='x' class="checkable"/> `;
                }
            },

        ],
        initComplete: function () {

        
        },
        createdRow: function (row, data, index) {
         
        },
        //select: true,
        select: {
            style: 'multi',
            "style": 'multiple',
            "className": 'selected',
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
        registros = table.rows().count();
        let rows_filtro = table.rows({ search: 'applied' }).count();
        DeleteAddClassdataTable('#tablaTransporte_wrapper', rows_filtro)
        $("#dt_registros").html("Mostrando <b>" + rows_filtro + "</b> de <b>" + registros + "</b> órdenes en total");
    });
}

//imprimirDatos data
function imprimirDatos(response) {
    let data = [];
  
    if (response.length > 0) {
        limpiarDatosNuevaCaja()
        let fecha_creacion = response[0]['fecha_creacion'];
    
        var dia = fecha_creacion.substring(0, 2);
        var mes = fecha_creacion.substring(3, 5);
        var year = fecha_creacion.substring(6, 10);
    
        $("#fechaCreacion").text(`Fecha: ${dia + "-" + mes + "-" + year}`)
        $('#fechaCreacion').removeAttr('hidden');
    
        // Extract the data from the API response
        data = response[0]['data'];
    
        // Sort the data if needed
        data = orderByProperty(data, 'idmuestra');
    }
  
    return data;
  }

//imprimirDatos data



   

$('#agregarContenedor, #agregarEstafeta, #agregarTemp').on('input', function() {
    var hasValue = false;
    $('#agregarContenedor, #agregarEstafeta, #agregarTemp').each(function() {
      if ($(this).val().length > 0) {
        hasValue = true;
        return false; 
      }
    });
  
    if (hasValue) {
      $('#fechaCreacion').attr('hidden', true);
    } else {
      $('#fechaCreacion').removeAttr('hidden');
    }
  });


//modificar para que posiblemente funcione con cualquier
function agregarSaltoEntreCampos() {
    let campos = $('.container-nueva-caja input');
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


// busqueda de datos
let inputBuscarCaja = document.getElementById('buscarCaja');
inputBuscarCaja.addEventListener('keydown', function(event) {
    if (event.keyCode === 13) {
    var value = inputBuscarCaja.value;
    buscarNroCaja(value);
  }
});
function buscarNroCaja(value) {
  let urlPrueba = `http://localhost:3000/transporte?numeroCaja=${value}`;


  $.ajax({
      url: urlPrueba,
      type: "GET",
      dataType: "json",
      success: function (response) {
        let data = imprimirDatos(response);
        table.clear().rows.add(data).draw();
      },
      error: function (error) {
          console.error(error);
      }
  });

}

var inputBuscarMuestra = document.getElementById('buscarMuestra');
inputBuscarMuestra.addEventListener('keydown', function(event) {
  if (event.keyCode === 13) {
    var value = inputBuscarMuestra.value;
    buscarNroMuestra(value);
  }
});
function buscarNroMuestra(value) {
    let urlPrueba = `http://localhost:3000/transporte?numeroCaja=${value}`;
    $.ajax({
        url: urlPrueba,
        type: "GET",
        dataType: "json",
        success: function (response) {
            let data = imprimirDatos(response);
            table.clear().rows.add(data).draw();
        },
        error: function (error) {
            console.error(error);
        }
    });
}
// busqueda de datos



//ingresar nuevo valor a la tabla

function ingresoCodigoBarrasTr() {
    var key = window.event.keyCode;
    if (key === 13) {
      var agregarContenedor = $('#agregarContenedor').val().toUpperCase();
      validarData()
      $('#agregarContenedor, #agregarEstafeta, #agregarTemp').on('focus blur', function() {
        $(this).removeClass('is-invalid');
        UiMensajes.limpiarTooltip(this);
      });
  
      if (agregarContenedor) {
        // let urlPrueba = `http://localhost:3000/nuevoTransporte?idmuestra=${agregarContenedor}`;
        let urlPrueba = `${getctx}/api/transporteMuestras/${agregarContenedor}/search`;
        $.ajax({
            url: urlPrueba,
            type: "GET",
            dataType: "json",
            success: function (data) {
                validarData(data.status,data.message)
                if(data.status === 200 || data.status === 202){
                    $("#agregarContenedor").val("");
                    $("#agregarEstafeta").val("");
                    $("#agregarTemp").val("");
                }else if(data.status === 404){
                    return;
                }

                // const dato = data[0].data[0];
                if(data === null){
                  return;
                }
                const dato = data.dato;
                const idmuestra = dato.idmuestra;

                const existingRows = table.rows().data().toArray();
                
                //verifica si los datos ingresados en la tabla tienen numeroCaja, si es asi,nueva
                //los elimina pensando que los proximos en ingresar son datos nuevos para una caja 
                const tieneNumeroCaja = existingRows.some(dato => dato.numeroCaja !== "" && dato.numeroCaja !== null);
                if (tieneNumeroCaja) {
                    table.clear().draw();
                }
                
                //verifica si existe una muestra
                const isidmuestraAlreadyAdded = existingRows.some(row => row.idmuestra === idmuestra);
                if (isidmuestraAlreadyAdded) {
                    handlerMessageWarning('Esta muestra ya existe en la tabla');
                }else{
                  const newRowData = {
                    numeroCaja:dato.numeroCaja ?? "",
                    idmuestra: dato.idmuestra,
                    idtipomuestra: dato.idtipomuestra,
                    idenvase: dato.idenvase,
                    nombreflebotomista: dato.nombreflebotomista,
                    estadotm: dato.estadotm
                };
  
              
                table.row.add(newRowData).draw();
                   
                }

                
            },
            error: function () {
                handlerMessageError("Error al ingresar código de barras");
            }
        });
      } else {
        handlerMessageError("Complete todos los campos");
      }
  
      return false;
    } else {
      return true;
    }
  }
//ingresar nuevo valor a la tabla


//crear caja
$('#crearCaja').on('click', function() {
    let sinCaja = true;
    const dataTableData = table.rows().data().toArray();
    const uniqueNumeroCaja = new Set();
    const datos = {
        numeroCaja: '',
        estafeta: '',
        temperatura: '',
        data: []
      };

    const agregarEstafetaValue = $('#agregarEstafeta').val();
    const agregarTempValue = $('#agregarTemp').val();

    if(!ValidarDataAlcrear()){
        handlerMessageError('Faltan datos para guardar la caja');
    }
   
  
    dataTableData.forEach(row => {
      const { numeroCaja, idmuestra, idtipomuestra, idenvase, nombreflebotomista, estadotm } = row;
  
      if (numeroCaja !== null && numeroCaja.trim() !== '') {
        sinCaja = false;
        return;
      }
  
      uniqueNumeroCaja.add(numeroCaja);
  
      const dataRow = {
        idmuestra,
        idtipomuestra,
        idenvase,
        nombreflebotomista,
        estadotm
      };
  
      datos.data.push(dataRow);
    });
    
    if(!sinCaja) {
      handlerMessageWarning('Los elementos no deben pertenecer a ninguna caja');
      return;
    }
  
    if (uniqueNumeroCaja.size === 1) {
      datos.numeroCaja = Array.from(uniqueNumeroCaja)[0];
    }

    datos.estafeta = agregarEstafetaValue;
    datos.temperatura = agregarTempValue;
  
    console.log('datos a guardar en la caja',datos);
  });
//crear caja
function ValidarDataAlcrear(){
    var agregarEstafeta = $('#agregarEstafeta').val();
    var agregarTemp = $('#agregarTemp').val();
    let notieneError = true;

    if (!agregarEstafeta) {
        $('#agregarEstafeta').addClass('is-invalid');
        UiMensajes.imprimirTooltip("#agregarEstafeta", "Debe ingresar estafeta");
        notieneError = false;
      } else {
        $('#agregarEstafeta').removeClass('is-invalid');
        UiMensajes.limpiarTooltip("#agregarEstafeta");
      }
  
      if (!agregarTemp) {
        $('#agregarTemp').addClass('is-invalid');
        UiMensajes.imprimirTooltip("#agregarTemp", "Debe ingresar temperatura");
        notieneError = false;
      } else {
        $('#agregarTemp').removeClass('is-invalid');
        UiMensajes.limpiarTooltip("#agregarTemp");
      }

      return notieneError;
}

function validarData(status, message = '') {
    console.log(!status)
    if (!status) {
        var agregarContenedor = $('#agregarContenedor').val().toUpperCase();
      if (!agregarContenedor) {
        $('#agregarContenedor').addClass('is-invalid');
        UiMensajes.imprimirTooltip("#agregarContenedor", "Debe ingresar contenedor");
      } else {
        $('#agregarContenedor').removeClass('is-invalid');
        UiMensajes.limpiarTooltip("#agregarContenedor");
      }
    } else {
      switch (status) {
        case 200:
        case 202:
            $("#agregarContenedor").removeClass("is-invalid");
            $("#agregarEstafeta").removeClass("is-invalid");
            $("#agregarTemp").removeClass("is-invalid");
          if (message !== '') {
            handlerMessageOk(message);
          }
          break;
        case 409:
            $("#agregarContenedor").removeClass("is-invalid");
            $("#agregarEstafeta").removeClass("is-invalid");
            $("#agregarTemp").removeClass("is-invalid");
          if (message !== '') {
            handlerMessageWarning(message);
          }
          break;
        case 404:
          $("#agregarContenedor").addClass("is-invalid");
          $("#agregarEstafeta").addClass("is-invalid");
          $("#agregarTemp").addClass("is-invalid");
          if (message !== '') {
            handlerMessageError(message);
          }
          break;
      }
    }
}

function limpiarDatosNuevaCaja(){
    $("#agregarEstafeta").val("");
    $("#agregarTemp").val("");
    $("#agregarContenedor").val("");
    UiMensajes.limpiarTooltip("#agregarEstafeta");
    UiMensajes.limpiarTooltip("#agregarTemp");
    UiMensajes.limpiarTooltip("#agregarContenedor");
    $("#agregarContenedor").removeClass("is-invalid");
    $("#agregarEstafeta").removeClass("is-invalid");
    $("#agregarTemp").removeClass("is-invalid");
}

function limpiarDatosBusqueda(){
    $("#buscarCaja").val("");
    $("#buscarMuestra").val("");
    UiMensajes.limpiarTooltip("#buscarCaja");
    UiMensajes.limpiarTooltip("#buscarMuestra");
    $("#buscarCaja").removeClass("is-invalid");
    $("#buscarMuestra").removeClass("is-invalid");
}


function redibujarDatatable() {
    table.draw();
  }
  
  // se vuelven a dibujar las tablas al cambiar de dimension las tablas
  window.addEventListener('resize', redibujarDatatable);

