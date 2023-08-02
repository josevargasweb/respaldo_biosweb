const UrlBase = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));// 'http://localhost:9080/BiosLis30';
var tableInfoExamenesOrden = null;
var tableInfoResultados = null;
var nroOrdenSeleccionada = null;
var disablerQueue = new Queue("nroOrdenChange");
var enablerQueue = new Queue("nroOrdenChange");
var tableIterator = null;
var datos = { "nOrden": null, "fIni": null, "fFin": null, "nombre": "", "apellido": "" };

$(document).ready(function() {

  tableIterator = new TableIterator(tableInfoResultados);
  initTableInfoExamenesOrden();
  let filtroOrden = new FiltroOrden();
  filtroOrden.fIni = new Intl.DateTimeFormat('es-ES').format(new Date());
  filtroOrden.fFin = new Intl.DateTimeFormat('es-ES').format(new Date());
  getOrdenes(filtroOrden);
  setEnablerQueue(enablerQueue);
  setDisablerQueue(disablerQueue);

  //Permiso de enviar resultados por email añadido por Marco Caracciolo 05/12/2022
  if ($("#enviaResultadosEmail").length && $("#enviaResultadosEmail").val() == "N") {
    $("#btnSendMail").prop("disabled", true);
  } else {
    $("#btnSendMail").prop("disabled", false);
  }
  const opciones = [
    {
      //targets: [0, 4, 5, 9, 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 26, 27, 28, 29, 30], //targets no visibles
      targets: [0, 4, 5, 9, 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 26, 27, 28, 29],
      visible: false,
    }
    , {
      targets: 2, //fecha formateada
      visible: true,
      render: function(data, type, row) {
        let dia = data.substring(0, 2);
        let mes = data.substring(3, 5);
        let year = data.substring(8, 10);
        let hora = data.substring(11, 16);
        return data;
        //  return hora + " " + dia + "/" + nombresMeses(mes) + "/" + year;
      }
    }
    , {
      targets: 3, //Estado
      visible: true,
      render: function(data, type, row) {
        const color = estadoOrdenColor[row["ldvfet_DESCRIPCION"]];
        return `<span class="label label-lg font-weight-bold label-inline estadoOrden-${color}" data-id="${row["df_IDFICHAESTADOTM"]}"  >${row["ldvfet_DESCRIPCION"]}</span>`;
      }

    }
    , {
      targets: 7, //Rut formatiado
      visible: true,
      render: function(data, type, row) {
        if (row["ldvtd_DESCRIPCION"] == "RUN") {
          const nDocumento = cambiarDatoRut(row["dp_NRODOCUMENTO"]);
          return nDocumento;
        } else {
          return row["dp_NRODOCUMENTO"];
        }
      }
    }
    , {
      targets: 8, //Nombre completo
      visible: true,
      render: function(data, type, row) {
        return data + " " + row['dp_PRIMERAPELLIDO'] + " " + (row['dp_SEGUNDOAPELLIDO'] || "");
      }
    },
    {
      targets: 14, //Edad
      visible: true,
      render: function(data, type, full, meta) {
        return data !== null ? calcularEdadTM(data) : "";
      }

    },
  ];


  gBusquedaPacientes.boBuscador = new BiolisBuscadorOrdenes(["#bo_div_nOrdenIni",
    "#bo_div_fIni",
    "#bo_div_fFin",
    "#bo_div_nombre",
    "#bo_div_apellido",
    "#bo_div_tipoDocumento",
    "#bo_div_nroDocumento",
    "#bo_div_procedencia",
    "#bo_div_servicio"]);
  gBusquedaPacientes.bo_OrdenesTable = new BiolisDatatableOrdenes('#bo_t_ordenes', gBusquedaPacientes.boBuscador, clickOnOrden, opciones);
  $("#bo_btnBuscarOrden").click(gBusquedaPacientes.bo_OrdenesTable.doSearch);
  $("#btnLimpiarFiltro").click(gBusquedaPacientes.bo_OrdenesTable.doLimpiarForm);
});

$("#registroRecepcionModalRut").rut({ formatOn: 'keyup', validateOn: 'blur' }).on('rutInvalido', function() {
  var rut;
  rut = $("#registroRecepcionModalRut").val();
  $("#registroRecepcionModalRut").addClass('is-invalid');

  if (rut.length === 0) {
    $("#registroRecepcionModalRut").removeClass('is-invalid');
  }
}).on('rutValido', function() {
  var rut;
  rut = $("#registroRecepcionModalRut").val();
  $("#registroRecepcionModalRut").addClass('is-invalid');
  if (rut.length > 10) {
    $("#registroRecepcionModalRut").removeClass('is-invalid');
  }
});

$("#btnNext").click(function() {
  tableIterator.next();
});

$("#btnPrev").click(function() {
  tableIterator.prev();
});

$("#btnBuscarFiltro").click(function() {
  buscarOrdenes();
});

$("#btnRegistrarRecepcion").click(function() {
  let destinatarios = obtenerExamenesSeleccionados(tableInfoExamenesOrden);
  if (destinatarios === false) {
    return;
  }
  console.log('destinatarios', destinatarios, typeof destinatarios)

  let valor_buscado = "AUTORIZADO";
  var existe = $.grep(destinatarios, function(objeto) {
    console.log("objeto", objeto.ceeDescripcionEstadoExamen);
    return objeto.ceeDescripcionEstadoExamen !== valor_buscado;
  }).length > 0;

  if (existe) {
    handlerMessageWarning('Los examenes seleccionados no estan autorizados');
    return;
  }

  $("#registroRecepcionModal").modal('show');

  registrarRecepcionExamenes(tableInfoExamenesOrden);
});

$("#pdfId").click(function() {
  let destinatarios = obtenerExamenesSeleccionados(tableInfoExamenesOrden);
  if (destinatarios === false) {
    return;
  }
  preverPdf(tableInfoExamenesOrden);
}
);

$("#btnDatosPaciente").click(function() {
  let destinatarios = obtenerExamenesSeleccionados(tableInfoExamenesOrden);
  if (destinatarios === false) {
    return;
  }
  $("#datosPacienteModal").modal('show');
}
);


$("#btnVistaPrevia").click(function() {
  let destinatarios = obtenerExamenesSeleccionados(tableInfoExamenesOrden);
  if (destinatarios === false) {
    return;
  }
  console.log("vista previa *********************")
  console.log(tableInfoExamenesOrden)

  preverPdf(tableInfoExamenesOrden);
}
);



$("#btnSendMail").click(function() {
  $("#enviarMailModal").modal('show');
}
);

$("#mailId").click(function() {
  let destinatarios = obtenerDatosModalMail();
  if (destinatarios === []) {
    return;
  }
  let tipoDeCorreo;

  if ($('#mailPacienteCheckbox').is(':checked')) {
    tipoDeCorreo = 1;
  }
  if ($('#mailServicioCheckbox').is(':checked')) {
    tipoDeCorreo = 2;
  }
  //mailServicioCheckbox  mailPacienteCheckbox
  enviarMail(tableInfoExamenesOrden, destinatarios, nroOrdenSeleccionada, tipoDeCorreo);
}
);

$("#txtNOrden").change(function() {
  disablerQueue.broadcast("nroOrdenChange");
}
);

$("#txtFInicio").change(function() {
  //        enable("#txtNOrden");
}
);

$("#txtFFin").change(function() {
  //        disablerQueue.broadcast("nroOrdenChange");
}
);

$("#txtFiltroNombre").change(function() {
  disable("#selectTipoDocumentoFiltro");
  disable("#selectTipoAtencionFiltro");
  disable("#selectServicio");
  disable("#txtNOrden");

}
);

$("#txtFiltroApellido").change(function() {
  disableSel("#selectTipoDocumentoFiltro");
  disableSel("#selectTipoAtencionFiltro");
  disable("#selectServicio");
  disable("#txtNOrden");
}
);

$("#selectTipoAtencionFiltro").change(function() {
  disableSel("#selectTipoDocumentoFiltro");
  disableSel("#selectServicio");
  disable("#txtFiltroApellido");
  disable("#txtFiltroNombre");
  disable("#txtNOrden");
}
);

$("#selectServicio").focus(function() {
  disableSel("#selectTipoAtencionFiltro");
  disableSel("#selectTipoDocumentoFiltro");
  disable("#txtNDocumento");
  disable("#txtFiltroApellido");
  disable("#txtFiltroNombre");
  disable("#txtNOrden");
}
);

$("#txtNDocumento").change(function() {
  disableSel("#selectTipoAtencionFiltro");
  disableSel("#selectServicio");
  disableSel("#selectTipoDocumentoFiltro");
  disable("#txtFiltroApellido");
  disable("#txtFiltroNombre");
  disable("#txtNOrden");
}
);

$("#selectTipoDocumentoFiltro").focus(function() {
  disableSel("#selectTipoAtencionFiltro");
  disableSel("#selectServicio");
  disable("#txtFiltroApellido");
  disable("#txtFiltroNombre");
  disable("#txtNOrden");
}
);

$("#idModalSvcMailList").change(function() {
  const servicio = cfgServicios.getById($(this).val());
  const email = servicio[0].csEmail;
  $("#idModalSvcDescEmail").val(email);
})
  ;

$("#mailPacienteCheckbox").change(function() {
  const estado = $(this).prop("checked");
  if (estado === true) {
    $("#idModalPacienteEmail").prop("disabled", false);
  }
  else {
    $("#idModalPacienteEmail").prop("disabled", true);
  }
});

$("#mailServicioCheckbox").change(function() {
  const estado = $(this).prop("checked");
  if (estado === true) {
    $("#idModalSvcDescEmail").prop("disabled", false);
  }
  else {
    $("#idModalSvcDescEmail").prop("disabled", true);
  }
});



$("#recepcionRegistro").click(function() {
  let destinatarios = obtenerExamenesRecepcionados(tableInfoExamenesOrden);
  if (destinatarios === false) {
    return;
  }

  let dpDto = JSON.stringify(destinatarios);


  $.ajax({
    url: `${gCte.getctx}/api/informeResultado/update`,
    method: 'POST',
    headers: {
      "Content-Type": "application/json"
    },
    data: dpDto,
    success: function(datos) {
      try {
        if (datos.status == 200) {
          handlerMessageOk("Se registro la recepción con exito");
          loadInfoExamenesOrden($("#txtNroOrden").text());
          ReloadRecepcionExamenes(datos.dato);
        }
        return;
      } catch (e) {
        handlerMessageError("No se pudo realizar la recepcion");
      }
    }
  });
}
);


$("#btnGlosas").click(function() {

  $("#guardarPdfModal").modal('show');
});
