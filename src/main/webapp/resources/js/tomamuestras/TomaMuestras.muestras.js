/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$("#guardarZonaCuerpo").prop('disabled', true)
$("#txtIngresoCodigoBarras").prop("disabled", true);
$('input[data-col-m-index="4"]').prop('disabled', true);
$('input[data-col-m-index="5"]').prop('disabled', true);



//CONFIRMAR MUESTRA
$("#btnConfirmarTomaMuestra").click(function() {
  $(this).prop('disabled', true);

  let nOrden = document.getElementById("lblNroOrden").innerHTML;



  let inputs = $("input[data-colselector-muestras]");

  console.log('inputs', inputs);


  if (inputs.filter(":checked").length === 0) {
    $.notify({ message: "Debe seleccionar una muestra" }, { type: "warning" });
  }

  const nInputs = inputs.length;

  for (let i = 0; i < nInputs; i++) {
    if (inputs[i].checked) {
      const filas = tableMuestras.row(inputs[i].closest("tr")).data();
      let idmuestra = filas.idmuestra;


      if (filas.estadotm === "TOMADA") {
        handlerMessageWarning("Muestra nro. " + filas.codigobarras + " ya está tomada")
        $('#btnConfirmarTomaMuestra').prop('disabled', false);
      } else {
        $.ajax({
          type: "PATCH",
          //data: "confirmarTomaMuestra=" + nOrden + "&idMuestra=" + idmuestra + "&usuario=" + sesionUsr,
          //url: getctx + "/api/tomaMuestras/tomarMuestra/"+idmuestra+"/"+sesionUsr,
          url: getctx + "/api/tomaMuestras/tomarMuestra/" + idmuestra,
          async: false,
          datatype: "json",
          success: function(data) {
            switch (data.status) {
              case 200:
                $('#divTablaMuestras').css('display', 'none');
                $("#tablaOrdenesCollapse").collapse('show');
                $("#tablaOrdenesCollapse").collapse('hide');
                $("#dt_info_registro").html("Última atención: Orden: " + data.dato.norden + " ; Paciente: " + data.dato.nombres);
                table.ajax.reload(null, false);
                tableMuestras.ajax.reload(null, false);
                handlerMessageOk(data.message);
                break;
              case 202:
                tableMuestras.ajax.reload(null, false);
                handlerMessageOk(data.message);
                break;
              case 404:
                handlerMessageError(data.message)
            }

          },
          error: function(msg) {
            handlerMessageError("Error al tomar muestra");
          },
          complete: function(jqXHR, textStatus) {
            $('#btnConfirmarTomaMuestra').prop('disabled', false);
          }
        });
      }
    }
  }


  //mostrarRegistroAcciones(nOrden);

});

//IMPRESION ETIQUETAS
$("#btnImpresionEtiquetas").click(function() {

  $(this).prop('disabled', true);
  let nOrden = document.getElementById("lblNroOrden").innerHTML;
  let idPaciente = document.getElementById("lblPaciente").innerHTML;
  //let muestrasAImprimir = null;
  //let muestrasAImprimir = tableMuestras.rows('.selected').data();

  $.ajax({
    type: "post",
    url: getctx + "/api/antecedentes/" + nOrden,
    async: false,
    datatype: "json",
    success: function(response) {
      let validAntecedentes = true;
      for (let dato of response.dato) {
        //Se añade condicion si el antecedente es obligatorio. Marco Caracciolo 18/01/2023
        if (dato.dfa_VALORANTECEDENTE == null && dato.ca_OBLIGATORIO === 'S')
          validAntecedentes = false;
      }
      if (validAntecedentes === true) {
        const table = $("#tablaMuestras").DataTable(); // Replace "tablaMuestras" with the actual ID of your table
        //const selectedRows = table.rows(".selected").data();
        const filas = table.rows().data();
        const n = filas.count();
        let muestras = [];
        let cont = 0;
        for (i = 0; i < n; i++) {
          if (filas[i].idmuestra == null) {
            let usuario = $('select[name=dUsuario] option').filter(':selected').val();
            let muestrasDTO = {
              "norden": nOrden,
              "idpaciente": idPaciente,
              "idtipomuestra": filas[i].idtipomuestra,
              "idenvase": filas[i].idenvase,
              "idderivador": filas[i].idderivador,
              "compartemuestra": filas[i].compartemuestra,
              "prefijo": filas[i].prefijo,
              "idusrflebotomista": usuario,
              "escurvatolerancia": filas[i].escurvatolerancia,
              "idexamen": filas[i].idexamen
            };
            muestras.push(muestrasDTO);
            cont++;
          }
        }

        let listaMuestras = [];
        let muestrasAImprimir = [];
        let inputs = $("input[data-colselector-muestras]");
        let cantMuestras = inputs.length;
        for (let i = 0; i < cantMuestras; i++) {
          if (inputs[i].checked) {
            const filas = tableMuestras.row(inputs[i].closest("tr")).data();
            // Crear un objeto con todos los valores de la fila actual
            let resultado = Object.assign({}, filas);

            // Agregar el objeto al array de resultados
            muestrasAImprimir.push(resultado);
          }
        }

        if (cont > 0) {
          let data = JSON.stringify(muestras);

          let strData = new Object();
          strData.data = muestras;

          //                    formData.append("data", JSON.stringify(muestras));
          filaSel = 2;
          $.ajax({
            type: "post",
            data: JSON.stringify(strData),
            url: getctx + "/api/tomaMuestras/generarMuestras",
            async: false,
            datatype: "json",
            //                        processData: false,
            //                        contentType: false,
            success: function(json) {
              if (json.status === 200) {

                handlerMessageOk(json.message);
                tableMuestras.ajax.reload(function() {
                  // tableMuestras.rows().select();
                  let checkboxes = $("input[type='checkbox'][data-colselector-muestras]");
                  checkboxes.prop("checked", true);
                });
                muestrasAImprimir.forEach((muestrasAImprimir) => {
                  json.dato.forEach((mue) => {
                    if (
                      muestrasAImprimir.idtipomuestra === mue.idtipomuestra &&
                      muestrasAImprimir.idenvase === mue.idenvase &&
                      muestrasAImprimir.idderivador === mue.idderivador &&
                      muestrasAImprimir.compartemuestra === mue.compartemuestra
                    ) {
                      listaMuestras.push(mue.codigobarras);
                    }
                  });
                });
              } else {

                handlerMessageError(json.message);
              }

            },
            error: function(e) {
              $.notify({ message: "Error al ingresar muestra: " + e.message }, { type: "danger" });
            }
          });
        } else {
          //si no se genera muestras (porque ya existen) se genera la lista de muestras mediante las filas seleccionadas
          for (let i = 0; i < cantMuestras; i++) {
            listaMuestras.push(muestrasAImprimir[i].codigobarras);
            console.log(muestrasAImprimir[i].codigobarras);
          }
        }

        // tableMuestras.rows().select();
        let checkboxes = $("input[type='checkbox'][data-colselector-muestras]");
        checkboxes.prop("checked", true);

        if (listaMuestras.length > 0) {
          ajaxImpresion(nOrden, listaMuestras);
        } else {
          handlerMessageError("No se ha seleccionado ninguna muestra para imprimir")
        }

      } else {
        $('#btnImpresionEtiquetas').prop('disabled', false);
        handlerMessageError("Debe ingresar antecedentes requeridos");
        $("#tablaOrdenesCollapse").collapse('show');
        $('html, body').animate({
          scrollTop: $("div#divTablaOrdenes").offset().top
        }, 700);
        $("#btnModalAntecedentes" + nOrden).find('i').addClass('text-danger-obligatorio');
        //     setTimeout(function () {
        //         $("#btnModalAntecedentes"+nOrden).removeClass('btn-danger');
        //    },5000);
      }
    }
  });


});

function imprimirEtiquetasBoton() {

}

function ajaxImpresion(nOrden, listaMuestras) {
  let datosImpresion = {
    "nroOrden": nOrden,
    "listaMuestras": listaMuestras
  };
  console.log("datos ajax impresion: " + JSON.stringify(datosImpresion));
  $.ajax({
    type: "get",
    url: "http://localhost:8080/rest/muestrasPrintRest",
    datatype: "json",
    data: JSON.stringify(datosImpresion),
    success: function() {
      handlerMessageOk("Ok");
      console.log("ok de ajaximpresion")
    },
    error: function(e) {
      handlerMessageError("Error. Compruebe que impresora esté conectada");
      console.log("error de ajaximpresion");
    },
    complete: function(jqXHR, textStatus) {
      $('#btnImpresionEtiquetas').prop('disabled', false);
    }
  });
}


function ingresoCodigoBarras() {
  let usuario = $('select[name=dUsuario] option').filter(':selected').val();
  var key = window.event.keyCode;
  //si se presiona la tecla Enter
  if (key === 13) {
    var txtIngresoCodigoBarras = $('#txtIngresoCodigoBarras').val().toUpperCase();
    $.ajax({
      type: "PATCH",
      url: getctx + "/api/tomaMuestras/tomarMuestra/codigoBarras/" + usuario + "/" + txtIngresoCodigoBarras,
      datatype: "json",
      success: function(data) {
        console.log(data);
        switch (data.status) {
          case 200:
            $('#divTablaMuestras').css('display', 'none');
            $("#tablaOrdenesCollapse").collapse('show');
            $("#dt_info_registro").html("Última atención: Orden: " + data.dato.norden + " ; Paciente: " + data.dato.nombres);
            $("#txtIngresoCodigoBarras").removeClass("is-invalid");
            table.ajax.reload(null, false);
            tableMuestras.ajax.reload(null, false);
            handlerMessageOk(data.message);
            break;
          case 202:
            tableMuestras.ajax.reload(null, false);
            handlerMessageOk(data.message);
            $("#txtIngresoCodigoBarras").removeClass("is-invalid");
            break;
          case 409:
            handlerMessageWarning(data.message);
            $("#txtIngresoCodigoBarras").removeClass("is-invalid");
            break;
          case 404:
            handlerMessageError(data.message)
            $("#txtIngresoCodigoBarras").addClass("is-invalid");
            break;
        }
        $("#txtIngresoCodigoBarras").val("");
      },
      error: function() {
        handlerMessageError("Error al ingresar código de barras");
      }
    });
    return false;
  } else {
    return true;
  }
}

function selectCambioEstadoMuestra(estado, idMuestra, nOrden, prefijo) {
  //console.log("cambio muestra manual")
  $("#spnEstadoMuestra" + idMuestra).empty();
  let idSelect = "#selectEstadoMuestra" + idMuestra;
  $("#spnEstadoMuestra" + idMuestra).html('<select id="selectEstadoMuestra' + idMuestra + '" class="form-control form-control-sm p-1" onchange=\'cambiarEstadoMuestraPaciente(this,' + idMuestra + ',' + nOrden + ',"' + prefijo + '")\'></select>');
  let estados = ["TOMADA", "PENDIENTE", "BLOQUEADA"];
  for (let i in estados) {
    let sel = "";
    if (estados[i] === estado) {
      sel = "selected";
    }
    $(idSelect).append('<option value="' + estados[i] + '" ' + sel + '>' + estados[i] + '</option>');
  }
  $("#spnEstadoMuestra" + idMuestra).append('<button class="btn" onclick=\'cancelarCambioEstadoMuestra("' + estado + '",' + idMuestra + ',' + nOrden + ',"' + prefijo + '")\'><i class="far fa-times-circle"></i></button>');
}

function cancelarCambioEstadoMuestra(estado, idMuestra, nOrden, prefijo) {
  let bgColor = "";
  switch (estado) {
    case 'TOMADA':
      bgColor = "#ACECD6";
      break;
    case 'PENDIENTE':
      bgColor = "#FF9AA2";
      break;
    case 'BLOQUEADA':
      bgColor = "#DFC7C1";
      break;
    default:
      bgColor = "#808080";
      break;
  }
  $("#spnEstadoMuestra" + idMuestra).html('<span id="lblEstadoMuestra' + idMuestra + '" onclick=\'selectCambioEstadoMuestra("' + estado + '",' + idMuestra + ',' + nOrden + ',"' + prefijo + '")\' style="background-color:' + bgColor + '" class="label label-lg font-weight-bold label-inline">' + estado + '\n\
                                    <button class="btn"><i class="fas fa-cog"></i></span></button>');
}

function cambiarEstadoMuestraPaciente(e, idMuestra) {
  let estado = e.value;
  let usuario = $('select[name=dUsuario] option').filter(':selected').val();

  let bgColor = "";
  switch (estado) {
    case 'TOMADA':
      bgColor = "#ACECD6";
      break;
    case 'PENDIENTE':
      bgColor = "#FF9AA2";
      break;
    case 'BLOQUEADA':
      bgColor = "#DFC7C1";
      break;
    default:
      break;
  }

  let confirmado = true;
  if (estado === 'PENDIENTE') {
    confirmado = false;
    let text = "¿Desea cambiar a estado Pendiente?";
    if (confirm(text) == true) {
      confirmado = true;
    } else {
      confirmado = false;
    }
  }

  if (confirmado === true) {
    $.ajax({
      type: "post",
      data: "cambiarEstadoMuestra=1&idMuestra=" + idMuestra + "&estado=" + estado.substring(0, 1) + "&usuario=" + usuario,
      datatype: "json",
      success: function() {
        tableMuestras.ajax.reload(null, false);
        $.notify({ message: "Se ha cambiado estado de muestra" }, { type: "success" });
      },
      error: function(msg) {
        $.notify({ message: "Error al cambiar estado muestra" }, { type: "danger" });
        console.log(msg);
      }
    });
    $("#spnEstadoMuestra" + idMuestra).html('<span id="lblEstadoMuestra' + idMuestra + '" onclick=\'selectCambioEstadoMuestra("' + estado + '",' + idMuestra + ')\' style="background-color:' + bgColor + '" class="label label-lg font-weight-bold label-inline">' + estado + '\n\
                                    <button class="btn"><i class="fas fa-cog"></i></span></button>');
  }
}

function selectCambioFlebotomista(idUsuario, idMuestra) {
  $.ajax({
    type: "GET",
    url: getctx + "/api/tomaMuestras/flebotomistas/list",
    datatype: "json",
    success: function(json) {
      let idSelect = "#selectUsuario" + idMuestra;
      $(idSelect).show();
      $(idSelect).empty();
      $("#lblUsuario" + idMuestra).hide();
      $("#btnCancelar" + idMuestra).show();
      for (let usuario of json) {
        $(idSelect).append('<option value="' + usuario.duIdusuario + '" ' + (usuario.duIdusuario == idUsuario ? 'selected' : '') + '>' + usuario.duNombres + ' ' + usuario.duPrimerapellido + '</option>');
      }
    },
    error: function(msg) {
      console.log(msg);
    }
  });
}

function cancelarCambioFlebotomista(idMuestra) {
  $("#lblUsuario" + idMuestra).show();
  $("#selectUsuario" + idMuestra).hide();
  $("#btnCancelar" + idMuestra).hide();
}

function cambiarFlebotomista(f, nOrden, idMuestra) {
  let flebotomista = f.value;

  $("#confirmModal").modal();
  $("#confirmModal").find(".modal-title").html("Cambio de Flebotomista");
  $("#confirmModal").find(".modal-text").html("<p>¿Está seguro de cambiar el usuario receptor de la muestra?</p>");
  //console.log("orden=" + nOrden + "&flebotomista=" + flebotomista + "&idMuestra=" + idMuestra)

  $("#confirmModal .btn-si").click(function() {
    $('#confirmModal').modal('toggle');
    $.ajax({
      type: "post",
      data: "cambiarFlebotomista=" + nOrden + "&flebotomista=" + flebotomista + "&idMuestra=" + idMuestra,
      datatype: "json",
      success: function() {
        tableMuestras.ajax.reload(null, false);
        // $.notify({ message: "Flebotomista cambiado exitosamente" }, { type: "success" });
      },
      error: function(msg) {
        $.notify({ message: "Error al cambiar Flebotomista" }, { type: "danger" });
        console.log(msg);
      }
    });
  });

  $("#confirmModal .btn-no").click(function() {
    tableMuestras.ajax.reload(null, false);
  });

  $("#confirmModal ").on('hide.bs.modal', function() {
    tableMuestras.ajax.reload(null, false);
  });
}




function rechazarMuestra(nOrden, idMuestra) {
  $("#confirmModal").modal();
  $("#confirmModal").find(".modal-title").html("Rechazo de muestra");
  $("#confirmModal").find(".modal-text").html("<p>La muestra seleccionada será rechazada, ¿desea continuar?</p>");


  $("#confirmModal .btn-si").click(function() {
    $('#confirmModal').modal('toggle');
    setWithExpiry("nOrden", nOrden, 50000);
    setWithExpiry("idMuestra", idMuestra, 50000);
    window.location.href = getctx + "/RechazoMuestras";
  });

}

//guardar observacion
function guardarObservacionMuestra(fila, obs) {

  $.ajax({
    type: "PATCH",
    url: "api/tomaMuestras/guardarObservacion/" + fila.idmuestra + "/" + obs,
    //data: JSON.stringify(muestra),
    async: false,
    contentType: "application/json",
    success: function(data) {
      let status = data.status;
      if (status === 200) {
        handlerMessageOk(data.message);
        tableMuestras.ajax.reload(null, false);
      }
    },
    error: function(msg) {
      handlerMessageError("Error");
    }
  });

}

function modalTipoMuestra(data) {
  idMuestra = data.idmuestra;

  if (datosMuestra === null) {
    datosMuestra = {
      "nOrden": nOrden,
      "idTipoMuestra": data.idtipomuestra,
      "idEnvase": data.idenvase,
      "idDerivador": data.idderivador,
      "comparteMuestra": data.compartemuestra
    };
    initTableTipoMuestra();
  } else {
    let urlTipoMuestra = getctx + "/api/tomaMuestras/tipoMuestra";
    datosMuestra = {
      "nOrden": nOrden,
      "idTipoMuestra": data.idtipomuestra,
      "idEnvase": data.idenvase,
      "idDerivador": data.idderivador,
      "comparteMuestra": data.compartemuestra
    };
    tableTipoMuestras.ajax.url(urlTipoMuestra).load();
  }

}

function modalZonaCuerpo(idMuestra, zonaCuerpo) {
  if (idMuestra !== null) {
    $("#rowZonaCuerpo").show();
    $("#rowErrorZC").hide();
    $("#rowSinPermiso").hide();
    $('#selZonaCuerpo').empty();
    $.ajax({
      type: "GET",
      url: getctx + "/Microbiologia/Mantenedores/api/getBAList",
      datatype: "json",
      success: function(data) {
        $('#selZonaCuerpo').append(`<option value="" selected>SIN ESPECIFICAR</option>`);
        for (let zona of data) {
          if (zona.id == zonaCuerpo) {
            $('#selZonaCuerpo').append('<option value=' + zona.id + ' selected>' + zona.name + '</option>');
          } else {
            $('#selZonaCuerpo').append('<option value=' + zona.id + '>' + zona.name + '</option>');
          }
        }
        $('.selectpicker').selectpicker('refresh');
      },
      error: function(msg) {
        console.log(msg);
      }
    });
    if (isFlebotomista === 'S') {
      $("#guardarZonaCuerpo").show();
      $('#selZonaCuerpo').prop("disabled", false);
      $("#guardarZonaCuerpo").attr('onClick', 'guardarZonaCuerpo(' + idMuestra + ');');
    } else {
      $('#selZonaCuerpo').prop("disabled", true);
      $("#guardarZonaCuerpo").hide();
      $("#rowSinPermiso").show();
    }

  } else {
    $("#rowZonaCuerpo").hide();
    $("#rowErrorZC").show();
  }
  $("#guardarZonaCuerpo").prop('disabled', true)
  $('.selectpicker').selectpicker('refresh');
}

function guardarZonaCuerpo(idMuestra) {
  let zonaCuerpo = document.getElementById("selZonaCuerpo").value;
  console.log(zonaCuerpo)
  $.ajax({
    type: "GET",
    url: getctx + `/api/tomaMuestras/muestra/zonacuerpo/update/idmuestra/${idMuestra}/idCuerpo/${zonaCuerpo}`,
    // data: "",
    datatype: "json",
    success: function() {
      tableMuestras.ajax.reload(null, false);
      handlerMessageOk("Se ha modificado sitio anatómico");
    },
    error: function(msg) {
      console.log(msg);
    }
  });
}

function mostrarDatosPacienteOrden(nOrden) {
  $.ajax({
    type: "GET",
    url: getctx + "/api/paciente/orden/" + nOrden,
    datatype: "json",
    async: false,
    success: function(message) {
      let paciente = message.dato;
      let nombres = paciente.dp.dpNombres + " " + paciente.dp.dpPrimerapellido + " " + (paciente.dp.dpSegundoapellido = null || '');

      $(".datosPacienteOrden").html(`
            <div class="row flex-wrap justify-content-center align-items-center">
                <div class="col-4">
                    <div class="col-12">
                        <label class="text-label">Paciente</label>
                    </div>
                    <span class="col-12" >${nombres}</span>
                </div>
                <div class="col-4">
                    <div class="col-12">
                        <label class="text-label">Orden</label>
                    </div>
                    <span class="col-12">${nOrden}</span>
                </div>
            </div>
                `)

    }
  });
}

/* desarrollo nuevo */
function cargarFlebotomistaData(estado, usuario, nOrden, idMuestra) {
  let feblotomista = estado;
  let count = 0;
  let option = "";
  let select = `<select id="selectUsuario${idMuestra}" class="form-control form-control-sm p-1" onchange="cambiarFlebotomista(this,${nOrden},${idMuestra})" style="width:${t1 * 13}px">`;
  feblotomista.forEach(element => {
    if (element.duNombres + " " + element.duPrimerapellido == usuario) {
      option += `<option value="${element.duIdusuario}" selected>${element.duNombres + " " + element.duPrimerapellido}</option>`;
      count++;
    } else {
      option += `<option value="${element.duIdusuario}">${element.duNombres + " " + element.duPrimerapellido}</option>`;
    }
  });
  if (count == 0) {
    select += `<option value="" disabled selected></option>`;
  }
  select += option;
  select += '</select>';

  return select;
}



let colorMuestras = function(estado) {
  let bgColor = "";
  if (estado == "TOMADA") {
    bgColor = "greenlight-1";
  } else if (estado == "PENDIENTE") {
    bgColor = "redlight-1";
  } else if (estado == "BLOQUEADA") {
    bgColor = "pinklight-1";
  } else {
    bgColor = "leadlight-1";
  }

  return bgColor;
};


function cargarEstadosMuestras(estado, idMuestra) {
  let select = `<select id="selectEstadoMuestra${idMuestra}" class="form-control select-color ${colorMuestras(estado)}" onchange="cambiarEstadoMuestraPacienteMuestra(this,'${idMuestra}')" style="width:${t1 * 10}px">`;
  let estados = ["TOMADA", "PENDIENTE", "BLOQUEADA", "RECHAZO PARCIAL"];

  estados.forEach((element) => {
    if (element == estado) {
      select += "<option value=" + element + " selected>" + element + "</option>";
    } else {
      select += "<option value=" + element + ">" + element + "</option>";
    }
  });

  select += '</select>';
  return select;
}

function cambiarEstadoMuestraPacienteMuestra(e, idMuestra) {
  let estado = e.value;
  let contador = 0;
  if (estado === 'PENDIENTE') {
    $("#confirmModal").modal();
    $("#confirmModal").find(".modal-title").html("Cambio de estado");
    $("#confirmModal").find(".modal-text").html("<p>¿Está seguro que desea retornar el estado de la muestra a Pendiente?</p>");

    $("#confirmModal .btn-si").click(function() {
      console.log("boton si ")

      $("#confirmModal").modal("toggle");
      if (contador == 0) {
        contador++;
        cambiarEstadoMuestraPacienteMuestraConfirmar(e, idMuestra);
      }
    });

    $("#confirmModal .btn-no").click(function() {
      tableMuestras.ajax.reload(null, false);
    });
    $("#confirmModal ").on('hide.bs.modal', function() {
      tableMuestras.ajax.reload(null, false);
    });
  } else {
    cambiarEstadoMuestraPacienteMuestraConfirmar(e, idMuestra);
  }
}


function cambiarEstadoMuestraPacienteMuestraConfirmar(e, idMuestra) {
  let estado = e.value;
  let usuario = $('select[name=dUsuario] option').filter(':selected').val();
  $.ajax({
    type: "patch",
    url: getctx + "/api/tomaMuestras/cambiarEstado/" + idMuestra + "/" + usuario + "/" + estado.substring(0, 1),
    datatype: "json",
    success: function(data) {
      tableMuestras.ajax.reload(null, false);
      if (estado === "BLOQUEADA") {
        handlerMessageWarning("Muestra bloqueada");
      } else if (estado !== 'PENDIENTE' && estado !== 'TOMADA') {
        handlerMessageOk(data.message);
      } else {
        handlerMessageOk("Se ha cambiado la muestra a tomada");
      }
      if (data.status === 200) {
        table.ajax.reload(null, false); //tabla de ordenes
        $('#divTablaMuestras').css('display', 'none');
        $("#tablaOrdenesCollapse").collapse('show');
        $("#dt_info_registro").html("Última atención: Orden: " + data.dato.norden + " ; Paciente: " + data.dato.nombres);
      }
    },
    error: function(msg) {
      handlerMessageError(msg)
      console.log(msg);
    }
  });
}


$('#tablaMuestras').on('click', '.btnObsMuestra', function(e) {
  let dataset = "";
  let id = $(this).siblings("p").attr('id');
  let text = $(this).siblings("p").text();
  cambiarEstadoParrafobutton(text, id);
});

$('#tablaMuestras').on('change', 'input', function(e) {
  // if(e.keyCode === 13){
  let input = e.target;
  let value = $(input).val();
  let colSelectorTest = $(this).attr('data-colselector-muestras');

  //Verificar si el elemento tiene el atributo data-colselector-test y su valor es "x"
  if (colSelectorTest !== 'x') {
    //No deshabilitar el elemento
    $(input).prop('disabled', true);
    let dataId = $(input).parent().attr("data-set");
    let fila = tableMuestras.row(`#${dataId}`).data();

    guardarObservacionMuestra(fila, value);
  }
  // }
});

$('#selZonaCuerpo').on('changed.bs.select', function(e) {
  $("#guardarZonaCuerpo").prop('disabled', false)
})


function obtenerMuestrasCheck(pTableInfoExamenesOrden) {
  function tuplaExamen(norden, estadotm, idexamen, idmuestra, estadotm, idtipomuestra, codigobarras) {
    this.norden = norden || null;
    this.estadotm = estadotm || "";
    this.idmuestra = idmuestra || null;
    this.idexamen = idexamen || null;
    this.estadotm = estadotm || "";
    this.idtipomuestra = idtipomuestra || null;
    this.codigobarras = codigobarras || null;
  }

  const inputs = $("input[data-colselector-muestras]");
  const examenesSeleccionados = inputs.map((index, input) => {
    const data = pTableInfoExamenesOrden.row($(input).closest("tr")).data();
    return new tuplaExamen(
      data.norden,
      data.estadotm,
      data.idexamen,
      data.idmuestra,
      data.estadotm,
      data.idtipomuestra,
      data.codigobarras
    );
  }).get();


  return examenesSeleccionados;
}


function obtenerMuestrasCheckeado(pTableInfoExamenesOrden) {
  function tuplaExamen(norden, estadotm, idexamen, idmuestra, estadotm, idtipomuestra, codigobarras) {
    this.norden = norden || null;
    this.estadotm = estadotm || "";
    this.idmuestra = idmuestra || null;
    this.idexamen = idexamen || null;
    this.estadotm = estadotm || "";
    this.idtipomuestra = idtipomuestra || null;
    this.codigobarras = codigobarras || null;
  }

  const inputs = $("input[data-colselector-muestras]");
  const examenesSeleccionados = inputs.map((index, input) => {
    if ($(input).is(":checked")) {
      const data = pTableInfoExamenesOrden.row($(input).closest("tr")).data();
      return new tuplaExamen(
        data.norden,
        data.estadotm,
        data.idexamen,
        data.idmuestra,
        data.estadotm,
        data.idtipomuestra,
        data.codigobarras
      );
    }
  }).get();


  return examenesSeleccionados;
}


function genColFilterM(colIndex, rowFilter, columna) {

  switch (colIndex) {
    case 0:
      getCheckAll(colIndex, rowFilter, 2.911076923076923);
      break;
    case 1:
      getColSelectVisibleFilterM(colIndex, rowFilter, columna, 12.55892307692308);
      break;
    case 2:
      getColSelectVisibleFilterM(colIndex, rowFilter, columna, 13.76084615384615);
      break;
    case 3:
      genColSelectFilterM(colIndex, rowFilter, 12.55892307692308);
      break;
    case 4:
      genColInputFilterM(colIndex, rowFilter, 16.16830769230769);
      break;
    case 5:
      genColInputFilterM(colIndex, rowFilter, 7.747615384615385);
      break;
    case 6:
      genNoFilterm(colIndex, rowFilter, 19.77646153846154);
      break;
    case 7:
      genNoFilterm(colIndex, rowFilter, 12.56607692307692);
      break;
    default:
      genNoFilterm(colIndex, rowFilter);
      break;
  }
}


//checkbox
function getCheckAll(colIndex, rowFilter, size = 6) {
  let inputUrgente = $(`<input id="seleccionaTodasLasMuestras" type="checkbox" class="" data-col-m-index="${colIndex}" style="width:${t1 * size}" checked>`);
  $(inputUrgente).appendTo($('<th class="dt-center">').appendTo(rowFilter));
  inputUrgente.on('change', function() {

    $('input[data-colselector-muestras]').prop('checked', $(this).prop('checked'));

    verifica_estado_tm()
  });




}

//checkbox


// select con datos visibles solamente

function genInputSelectVisibleM(colIndex, col, size = 6) {
  function fillSelect(data) {
    let options = [];
    console.log('data', data[0], data.data().unique().sort().toArray())
    data.data().unique().sort().each(servicio => {
      let opt = new Option(servicio, servicio);
      options.push(opt);
    });
    let opt = new Option('TODOS', '', true, true);
    options.unshift(opt);
    return options;
  }


  let selectElement = $('<select>', { class: 'form-control form-control-sm form-filter datatable-input', 'data-col-m-index': colIndex, style: `width:${calcularTamanoPx(size, t1)}px` });
  let options = fillSelect(col);
  options.forEach(option => selectElement.append(option));
  return selectElement;
}



function searchValueVisibleM(e) {
  let columna = $('#tablaMuestras').DataTable().column(this.dataset["colMIndex"]);
  console.log('searchValueVisible ' + this.dataset["colMIndex"]);
  columna.search(this.value, true, false, false).draw();
}

function getColSelectVisibleFilterM(colIndex, rowFilter, col, size = 6) {
  let inputSelect = genInputSelectVisibleM(colIndex, col, size);
  $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
  $(`select[data-col-m-index="` + colIndex + `"]`).change(searchValueVisibleM);
}

//

//select

function setOptionEstadoM(colIndex) {
  function fillSelect(data) {
    let options = [];
    data.forEach(servicio => {
      let opt = new Option(servicio, servicio);
      options.push(opt);
    });
    let opt = new Option('TODOS', '', true, true);
    options.unshift(opt);
    return options;
  }
  let estados = ['TOMADA', 'PENDIENTE', 'BLOQUEADA']

  return fillSelect(estados)

}

function setOptionSelColM(colIndex) {

  switch (colIndex) {
    case 3:
      return setOptionEstadoM(colIndex);
    default:
      break;
  }
}


function genInputSelectM(colIndex, size = 6) {
  let selectElement = $('<select>', { class: 'form-control form-control-sm form-filter datatable-input', 'data-col-m-index': colIndex, style: `width:${calcularTamanoPx(size, t1)}px` });
  let options = setOptionSelColM(colIndex);
  options.forEach(option => selectElement.append(option));
  return selectElement;
}



function searchValueM(e) {
  let colIndexvalue = this.dataset["colMIndex"];
  if (colIndexvalue == 3) {
    $.fn.dataTable.ext.search.push(
      function(settings, data, dataIndex) {
        if (settings.nTable.id !== 'tablaMuestras') {
          return true;
        }
        var selected_rarities = [];
        let text = (($('select[data-col-m-index="' + colIndexvalue + '"]').is('select') && $('select[data-col-m-index="' + colIndexvalue + '"]').val() == $(data[colIndexvalue]).find(":selected").text()) || ($(data[colIndexvalue]).is('span') && $('select[data-col-m-index="' + colIndexvalue + '"]').val() == $(data[colIndexvalue]).text().trim()));

        if ($('select[data-col-m-index="' + colIndexvalue + '"]').val() != "" && text) {
          selected_rarities.push(1);
        } else if ($('select[data-col-m-index="' + colIndexvalue + '"]').val() == "") {
          selected_rarities.push(1);
        }
        if (selected_rarities.length !== 0) {
          return true;
        } else {
          return false;
        }
      }
    );

    tableMuestras.draw();
  } else {
    let columna = $('#tablaMuestras').DataTable().column(this.dataset["colMIndex"]);
    console.log('searchValue ' + this.dataset["colMIndex"]);
    columna.search(this.value, true, false, false).draw();
  }
}

function genColSelectFilterM(colIndex, rowFilter, size = 6) {
  let inputSelect = genInputSelectM(colIndex, size);
  $(inputSelect).appendTo($('<th>').appendTo(rowFilter));
  $(`select[data-col-m-index="` + colIndex + `"]`).change(searchValueM);
}
//select


//input
function searchKeyM(e) {
  let colIndex = this.dataset["colMIndex"];
  if (colIndex == 4) {
    $.fn.dataTable.ext.search.push(
      function(settings, data, dataIndex) {
        let selected_rarities = [];
        let textoption = (($(data[colIndex]).is('select') && $(data[colIndex]).find(":selected").text().trim().toLowerCase()) || ($(data[colIndex]).is('span') && $(data[colIndex]).text().trim().toLowerCase()));

        if (settings.nTable.id !== 'tablaMuestras') {
          return true;
        }
        if ($("#searchFlebo").val() == "") {
          selected_rarities.push(1);
        } else if ($("#searchFlebo").val() != "") {
          substrRegex = new RegExp($("#searchFlebo").val(), 'i');
          if (substrRegex.test(textoption)) {
            selected_rarities.push(1);
          }
        }
        if (selected_rarities.length !== 0) {
          return true;
        } else {
          return false;
        }
      }
    );
    tableMuestras.draw();
  } else if (colIndex == 5) {
    if (tableMuestras.column(i).search() !== this.value) {
      tableMuestras
        .column(colIndex)
        .search(this.value)
        .draw();
    }
  }
}

function genColInputFilterM(colIndex, rowFilter, size = 6) {
  let inputText = $(`<input id="searchFlebo" type="text" style="width:${calcularTamanoPx(size, t1)}px" class="form-control form-control-sm form-filter datatable-input" data-col-m-index="` + colIndex + `"/>`);
  $(inputText).appendTo($('<th>').appendTo(rowFilter));
  $(`input[data-col-m-index="` + colIndex + `"]`).on('keyup change clear', searchKeyM);
}

//input


//no filter

function genNoFilterm(colIndex, rowFilter, size = 6) {
  let inputText = $(`<div style="width:${calcularTamanoPx(size, t1)}px"></div>`);
  $(inputText).appendTo($('<th>').appendTo(rowFilter));
}

//no filter


// modifica filtros
function setColSelectVisibleFilterM(colIndex, columna) {

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

  let select = $('select[data-col-m-index="' + colIndex + '"]');
  select.empty();
  let options = fillSelect(columna);
  options.forEach(option => select.append(option));
}


function genSetColFilterM(colIndex, columna) {

  switch (colIndex) {
    case 1:
    case 2:
      setColSelectVisibleFilterM(colIndex, columna);
      break;
    default:
      break;
  }
}


// modifica filtros


$('#tablaMuestras tbody').on('click', 'td:not(:first-child)', function(event) {
  console.log('child click event');
  var row = $(this).closest('tr');
  row.toggleClass('selected');
  event.stopPropagation();
});


// modifica boton de tomar muestras
function verifica_estado_tm() {
  let result = obtenerMuestrasCheckeado(tableMuestras)
  if (result) {
    console.log('result', result)
    let muestrasChecked = result.some(objeto => objeto.estadotm === "RECHAZADA" || objeto.estadotm === "" || objeto.estadotm === "TOMADA");
    console.log(muestrasChecked)
    if (muestrasChecked) {
      $("#btnConfirmarTomaMuestra").prop('disabled', true)
    } else {
      $("#btnConfirmarTomaMuestra").prop('disabled', false)
    }
  } else {
    $("#btnConfirmarTomaMuestra").prop('disabled', true)
  }
  var anyChecked = false;
  $('#tablaMuestras tbody tr :checkbox').each(function() {
    if ($(this).prop('checked')) {
      anyChecked = true;
      return false; // Exit the loop early if a checkbox is checked
    }
  });
  console.log(anyChecked);
  $('#btnImpresionEtiquetas').prop('disabled', !anyChecked);
}

$("#tablaMuestras").on("change", "input[data-colselector-muestras]", function() {
  console.log('entra aca')
  verifica_estado_tm()
});


function MostrarIndicaciones(data) {
  let nOrden = data.norden;
  let idEnvase = data.idenvase;
  let idTipoMuestra = data.idtipomuestra;
  let idDerivador = data.idderivador;
  let comparteMuestra = data.compartemuestra;
  let esCurva = data.escurvatolerancia;

  $("#indicacionesPaciente").empty();
  $("#indicacionesTM").empty();
  $("#tablaTomaMuestra").empty();
  $.ajax({
    type: "post",
    //data: "MostrarIndicaciones=" + nOrden,
    data: "MostrarIndicaciones=1&nOrden=" + nOrden +
      "&idEnvase=" + idEnvase +
      "&idTipoMuestra=" + idTipoMuestra +
      "&idDerivador=" + idDerivador +
      "&comparteMuestra=" + comparteMuestra + "&esCurva=" + esCurva,
    datatype: "json",
    success: function(pacientes) {
      var infoPaciente = JSON.parse(pacientes);
      console.log(infoPaciente);
      let validadorDescExamen = 0;
      let validadorDescExamenTM = 0;

      for (let variable of infoPaciente.indicaciones) {
        console.log(variable.minAdulto)
        console.log(variable.minPediatra)
        if (variable.minAdulto != 0) {
          $("#adultoVolumen").text(variable.minAdulto);
          //   $("#adultoVolumen").val(variable.minAdulto);
        } else {
          $("#adultoVolumen").text("");
          // $("#adultoVolumen").val("");
        }
        if (variable.minPediatra != 0) {
          $("#pediVolumen").text(variable.minPediatra);
          // $("#pediVolumen").val(variable.minPediatra);
        } else {
          $("#adultoVolumen").text("");
          // $("#adultoVolumen").val("");
        }

        for (let indicaciones of variable.indicacionesExamenes) {
          console.log('indicaciones', indicaciones)
          if (!(indicaciones.descpIndicacionExamen === '')) {
            $("#indicacionesPaciente").append('<li>' + indicaciones.descpIndicacionExamen + '</li>');
            validadorDescExamen++;
          }
          if (!(indicaciones.descpIndicacionExamenTM === '')) {
            $("#indicacionesTM").append('<li>' + indicaciones.descpIndicacionExamenTM + '</li>');
            validadorDescExamenTM++;
          }
          $("#tablaTomaMuestra").append("<tr class=''>\
                                                        <td class=''>" + indicaciones.Examen + "</td>\
                                                        <td class=''>" + indicaciones.TAmbiente + "</td>\
                                                        <td class=''>" + indicaciones.Refrigerado + "</td>\
                                                        <td class=''>" + indicaciones.Congelado + "</td>\
                                                    </tr>");
        }

        if (validadorDescExamen === 0)
          $("#indicacionesPaciente").append('<li>No se encuentran indicaciones para los exámenes seleccionados</li>');
        if (validadorDescExamenTM === 0)
          $("#indicacionesTM").append('<li>No se encuentran indicaciones para los exámenes seleccionados</li>');


      }

    },
    error: function(msg) {
      console.log(msg);
    }
  });

}

$('#limpiarFiltroTablaMuestras').click(function() {
  $('.selectFiltroMue').val("");
  $('.txtFiltroMue').val("");
  for (let i = 0; i < 9; i++) {
    tableMuestras
      .column(i)
      .search("", false, false)
      .draw();

  }
});

