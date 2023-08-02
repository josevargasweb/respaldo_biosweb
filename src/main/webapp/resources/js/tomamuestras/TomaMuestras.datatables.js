
var initTableMuestras = function() {

  tableMuestras = $('#tablaMuestras').DataTable({
    responsive: false,
    paging: false,
    //searchDelay: 500,
    orderCellsTop: true,
    scrollX: true,
    info: false,
    dom: '<"top"i>rt<"bottom"flp><"clear">',
    // responsive: false,
    autoWidth: true,
    scrollY: calcularHeightSolo("divTablaMuestras"),
    "rowId": function(a) {
      if (a.idmuestra == null) {
        return nOrden.toString().concat('_').concat(a.idtipomuestra).concat('_').concat(a.idenvase).concat('_').concat(a.idderivador).concat('_').concat(a.compartemuestra)
      }
      return a.idmuestra;
    },
    ajax: {
      url: getctx + "/api/tomaMuestras/muestras/" + nOrden,
      type: "POST",
      contentType: "application/json",
      "dataSrc": function(response) {
        let resultado = response.dato;

        if (resultado != null) {
          resultado.sort(function(a, b) {
            if (a.codigobarras < b.codigobarras) return -1;
            if (a.codigobarras > b.codigobarras) return 1;
            return 0;
          });
        }
        return resultado;
      },
      dataType: "json"
    },
    serverSide: true,
    columns: [
      { data: 'Seleccion', responsivePriority: -2, "width": "5%" },
      { data: 'muestraabrev', "width": "14%" },
      { data: 'envasedesc', "width": "14%" },
      { data: 'estadotm', type: "select", "width": "10%" },
      { data: 'usrflebotomista', type: "select", "width": "10%" },
      { data: 'codigobarras', "width": "10%" },
      { data: 'observaciontm', "width": "15%" },
      { data: 'Botones', "width": "22%" }
    ],
    //aquí defino el formato de las columnas
    columnDefs: [
      { orderable: false, targets: '_all' },
      {
        targets: 0,
        width: '30px',
        className: 'dt-center',
        //  orderable: false,
        render: function(data, type, row, meta) {
          return `<input type="checkbox" data-colselector-test="x" data-colselector-muestras="x" value="" class="checkable" checked/> `;
        },
      },
      {
        //Muestra
        targets: 1,
        render: function(data, type, row) {
          return `<div style="width:${t1 * 10}px">${largoTexto2(data != null ? data : '', 20)}</div>`

        }
      },
      {
        //Contenedor
        targets: 2,
        render: function(data, type, row) {
          let imageUrl = jpegBase64(row.imgenvase);
          return `<div class="label-popover pointer"  data-toggle="popover" data-img="${imageUrl}" style="width:${t1 * 11}px">${cutText(data, 16)}</div>`;

        }
      }
      , {
        //Estado
        targets: 3,
        render: function(data, type, row) {
          let bgColor = "";
          let estado = "";
          switch (data.trim()) {
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
          if (estado === "") {
            return `<span style="background-color:${bgColor};width:${t1 * 10}px" title="${data}" class="label label-lg font-weight-bold label-inline">PENDIENTE</span>`;

            //Cuando la muestra está rechazada o está tomada y recepcionada
          } else if (estado === "RECHAZADA" || (estado === "TOMADA" && row['estadorm'] === "R")) {
            return `<span style="background-color:${bgColor};width:${t1 * 10}px" title="${data}" class="label label-lg font-weight-bold label-inline" >${estado}</span>`;
          } else {
            if (isFlebotomista === 'S') {
              return cargarEstadosMuestras(estado, row['idmuestra']);

            } else {
              return `<span id="lblEstadoMuestra${row['idmuestra']}" title="${data}" style="background-color:${bgColor};width:${t1 * 10}px" class="label label-lg font-weight-bold label-inline">${estado}</span>`;
            }
          }
        }
      }, {
        //Flebotomista
        targets: 4,
        className: 'notChildRow',
        render: function(data, type, row) {
          if (row['idmuestra'] !== null) {
            if (isFlebotomista === 'S') {
              return cargarFlebotomistaData(flebotomistaData(), data, nOrden, row['idmuestra']);
            } else {
              return `<div id="lblUsuario${row['idmuestra']}" class="label label-lg font-weight-bold label-inline" style="width:${t1 * 13}px">${data}</div>`;
            }
          } else {
            return "";
          }
        }
      }, {
        //Contenedor
        targets: 5,
        render: function(data, type, row) {
          return `<div style="width:${t1 * 6}px" title="${data}">${data}</div>`;

        }
      }, {
        //Observación
        targets: 6,
        className: 'notChildRow',
        render: function(data, type, row) {
          if (row['idmuestra'] !== null) {
            if (isFlebotomista === 'S') {
              return `<div class="row" style="width:${t1 * 16}px">
                                <p id="txtObsMuestra${row['idmuestra']}" data-set="${row['idmuestra']}" title="${data != null ? data.trim() : ''}">${largoTexto2(data != null ? data.trim() : '', 21)}</p>
                                <a class="btnObsMuestra" class="p-2 ml-2">
                                    <i class="fas fa-pen" aria-hidden="true"></i>
                                </a>
                            </div > `
              // return `< p id = "txtObsMuestra${row['idmuestra']}" data - set="${row['idmuestra']}" > ${ data.trim() }</p > `
              // return '<input type="text" id="txtObsMuestra'+row['idmuestra']+'" value="'+data.trim()+'" onchange="guardarObservacionMuestra(' + row['idmuestra'] + ')">';
            } else {
              return `< div style = "width:${t1 * 16}px>${largoTexto2(data.trim(), 21)}</div>`;
            }
          }
          return `<div style="width:${t1 * 16}px>${largoTexto2(data, 21)}</div>`;
        }
      }, {
        //Acciones
        targets: 7,
        title: 'Acciones',
        orderable: false,
        className: 'notChildRow',
        render: function(data, type, row, meta) {
          let datos = JSON.stringify(row);
          const muestraNull = row.idmuestra === null ? true : false;
          const rechazoDisabled = row.idmuestra === null && row.estadotm !== "TOMADA" ? true : false;
          let htmlCurva = "";
          if (row['idmuestra'] !== null) {
            console.log(row['idmuestra'])
            console.log(data)
            if (row['escurvatolerancia'] === "S") {
              htmlCurva = '<a href="#" id="curvaTolerancia" class="p-2" title="Curva Tolerancia" data-toggle="modal" data-target="#modalCurvaTolerancia" onclick=\'cargarCurvasTolerancia(' + nOrden + ',' + row['idmuestra'] + ')\' >\
                                            <i class="far fa-clock"></i><span class="nav-text"></span>\
                                        </a>';
            }
          }

          return '\<div class="row">\
                                    <a href="#" class="p-2 verExamenes" title="Ver Exámenes">\
                                        <i class="far fa-file-alt" aria-hidden="true"></i>\
                                    </a>\
                                    '+ htmlCurva +
            '<a href="#" id="dt_tipomuestra" class="p-2 ' + (muestraNull ? 'disabled' : '') + '" title="Tipo de Muestra: ' + row['muestradesc'] + '" data-toggle="modal" data-target="#modalDetalleMuestra" onclick=\'modalTipoMuestra(' + datos + '); mostrarDatosPacienteOrden(' + nOrden + ') \'>\
                                        <i class="fas fa-lightbulb" '+ (muestraNull ? 'style="color:#858383 !important;"' : '') + '></i><span class="nav-text"></span>\
                                    </a>\
                                    <a href="#" id="" class="p-2 '+ (muestraNull ? 'disabled' : '') + '" title="' + (row['zonacuerpo'] !== null ? "Sitio anatómico: " + row['zonacuerpo'] : "Sin sitio anatómico") + '" data-toggle="modal" data-target="#modalZonaCuerpo" onclick=\'modalZonaCuerpo(' + row['idmuestra'] + ',' + row['idzonacuerpo'] + '); mostrarDatosPacienteOrden(' + nOrden + ') \'>\
                                        <i class="fas fa-child" '+ (muestraNull ? 'style="color:#858383 !important;"' : '') + '></i><span class="nav-text"></span>\
                                    </a>\
                                    <a href="#" id="" class="p-2" title="Indicaciones TM" data-toggle="modal" data-target="#exampleModal4" onclick=\'MostrarIndicaciones(' + datos + ') \'>\
                                        <i class="fas fa-user-md"></i><span class="nav-text"></span>\
                                    </a>'
            + (row['estadorm'] !== "R" && (isFlebotomista === 'S') ?
              '<a href="#" id="" class="p-2 ' + (rechazoDisabled ? 'disabled' : '') + '" title="Rechazo de Muestra" data-toggle="modal" data-target="#exampleModal5" onclick=\'rechazarMuestra(' + nOrden + ', ' + row['idmuestra'] + ') \'>\
                                        <i class="fas fa-hand-paper" '+ (rechazoDisabled ? 'style="color:#858383 !important;"' : '') + '></i><span class="nav-text"></span>\
                                    </a>\
                                </div>' : `<div style="width:'+t1*7+'px"></div>`);
        }
      }
    ],
    "drawCallback": function() {
      //esto es para habilitar o deshabilitar botón de impresión de etiquetas
      const filas = tableMuestras.rows().data();
      const n = filas.count();
    },

    "select": {
      "style": 'single',
      "className": 'row-selected-none',
    },
    createdRow: function() {

      this.api().columns().every(function() {
        let columna = this;
        genSetColFilterM(columna.index(), columna);
      });

      const registros = tableMuestras.rows().count();
      $("#dt_registros_m").html("Muestras: <b>" + registros + "</b>");
      //función para filtrar tablas de muestra y envase
    },
    initComplete: function() {
      //se agrega filtros
      let rowFilter = $('<tr class="filterMuestras"></tr>').appendTo($(tableMuestras.table().header()));
      this.api().columns().every(function() {
        let columna = this;
        genColFilterM(columna.index(), rowFilter, columna);
      });


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


  $('#tablaMuestras tbody').on('click', '.verExamenes', function() {
    let colSelectorTest = $(this).children().attr('data-colselector-muestras');
    var tr = $(this).closest('tr');
    var row = tableMuestras.row(tr);
    let data = row.data();
    if (colSelectorTest !== 'x') {
      //Verificar si el elemento tiene el atributo data-colselector-test y su valor es "x"
      if (row.child.isShown()) {
        // This row is already open - close it
        row.child.hide();
        tr.removeClass('shown');
      }
      else {
        let url = "";
        // Open this row
        if (data.idmuestra != null) {
          url = getctx + `/api/tomaMuestras/examenes/muestra/${data.idmuestra}`;
        } else {
          url = getctx + "/api/tomaMuestras/examenes/" + data.norden + "/" + data.idtipomuestra + "/" + data.idenvase + "/" + data.idderivador + "/" + data.compartemuestra + "/" + data.idexamen;
          console.log(data)
        }
        $.ajax({
          type: "GET",
          url: url,
          datatype: "json",
          async: false,
          success: function(data) {
            let table = `<div class="col-12 form-group pl-0 pr-0 mb-0 examenes-recepcion">
                        <label for="exampleTextarea" class=" col-form-label pb-0 col-12 pl-0">Exámenes</label>
                        <div class="col-12 form-group pl-0 pr-0 mb-0 examenes-contenedor-recepcion">
                        <ul id="ulExamenes-muestras" class="mb-0 d-flex flex-column flex-wrap pl-3">`
            for (let examen of data) {
              if (examen.dfe_EXAMENANULADO === 'S') {
                table += '<li class="col-3 pl-0 pr-0"><del>' + examen.ce_CODIGOEXAMEN + ' - ' + examen.ce_ABREVIADO + '</del></li>';
              } else {
                table += '<li class="col-3 pl-0 pr-0">' + examen.ce_CODIGOEXAMEN + ' - ' + examen.ce_ABREVIADO + '</li>';
              }
            }
            table += '</ul></div></div>';
            row.child(table).show();
          }
        });
        tr.addClass('shown');
      }
    }
  });

  tableMuestras.on('draw', function() {
    if (tableMuestras.settings()[0].oFeatures.bServerSide){
      tableMuestras.settings()[0].oFeatures.bServerSide = false;
      tableMuestras.draw();
    }
    $('.containerTableMuestras').removeClass('hidden');
    $(".spinnerContainer2").remove();
    registros_muestras = tableMuestras.rows().count();
    //console.log('total rows',registros_muestras);
    let rows_filtroMue = tableMuestras.rows({ search: 'applied' }).count();
    //console.log('rows count:', rows_filtroMue);

    $("#dt_registros_m").html("Mostrando <b>" + rows_filtroMue + "</b> de <b>" + registros_muestras + "</b> muestras en total");

    const feblotomistaInput = $('input[data-col-m-index="4"]');
    const nMuestraInput = $('input[data-col-m-index="5"]');
    let result = obtenerMuestrasCheck(tableMuestras);
    if (result) {
      let muestrasChecked = result.some(objeto => objeto.idmuestra !== null);

      if (muestrasChecked) {
        $("#txtIngresoCodigoBarras").prop("disabled", false);
        feblotomistaInput.prop('disabled', false);
        nMuestraInput.prop('disabled', false);
        //    inputs.prop('disabled', false);
      } else {
        $("#txtIngresoCodigoBarras").prop("disabled", true);
        feblotomistaInput.prop('disabled', true);
        nMuestraInput.prop('disabled', true);
        //    inputs.prop('disabled', true);
      }
    } else {
      $("#txtIngresoCodigoBarras").prop("disabled", true);
      feblotomistaInput.prop('disabled', true);
      nMuestraInput.prop('disabled', true);
      // inputs.prop('disabled', true);
    }

    verifica_estado_tm();

    $('#tablaMuestras tbody').on('change', 'tr :checkbox', function() {
      var anyChecked = false;
      $('#tablaMuestras tbody tr :checkbox').each(function() {
        if ($(this).prop('checked')) {
          anyChecked = true;
          return false; // Exit the loop early if a checkbox is checked
        }
      });
      $('#btnImpresionEtiquetas').prop('disabled', !anyChecked);
    });

  });
  
  tableMuestras.on("change", 'input[data-colselector-muestras="x"]', function(e) {
  e.preventDefault();
  let result = obtenerMuestrasCheck(tableMuestras)
  if (result) {
    let muestrasChecked = result.some(objeto => objeto.idmuestra !== null);

    if (muestrasChecked) {
      $("#txtIngresoCodigoBarras").prop("disabled", false);
    } else {
      $("#txtIngresoCodigoBarras").prop("disabled", true);
    }
  } else {
    $("#txtIngresoCodigoBarras").prop("disabled", true);
  }

})

/*  if ($.fn.DataTable.isDataTable('#tablaMuestras')) {
  
    $('#tablaMuestras').DataTable().clear().destroy();
  }
*/

}
