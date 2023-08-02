
class UI {
  imprimirTooltip(elemento, mensaje,tipo = 'enable') {

    $(elemento).attr({
        "data-toggle": "tooltip",
        "data-placement": "top",
        "data-custom-class":"tooltip-warning",
        "title": mensaje,
        "data-original-title": mensaje,
      });
      $(elemento)
      .tooltip({
        template: '<div class="tooltip tooltip-danger" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',
      }).tooltip(tipo);
  }
  limpiarTooltip(elemento) {
    $(elemento).removeAttr("data-toggle data-placement data-custom-class title data-original-title");
    $(elemento).tooltip("disable");
    $(elemento).tooltip('update');
  }

  normalTooltip(elemento){
    $(elemento)
      .tooltip({
        template: '<div class="tooltip tooltip-normal" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',
      });
  }

  validarNombres(elemento){
    $(elemento).on("blur", function() {
      if($(this).val().trim() !== "" && $(this).val().trim().length >= 1 && $(this).val().trim().length < 2){
        $(this).addClass('is-invalid');
        ui.imprimirTooltip($(this),"Mínimo dos caracteres");
      }else{
        $(this).removeClass("is-invalid");
        ui.limpiarTooltip($(this));
      }

      if(validarParaSpan() ){
        $("#spanDatosBasicos").remove();
      }
    });
  }
  validarEmail(elemento){
    $(elemento).on("blur", function() {
    let emailRegex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
      if($(this).val().trim() !== "" && $(this).val().trim().length >= 1 && !emailRegex.test($(this).val().trim())){
        $(this).addClass('is-invalid');
        ui.imprimirTooltip($(this),"email invalido debe tener el siguiente formato mail@mail.com");
      }else{
        $(this).removeClass("is-invalid");
        ui.limpiarTooltip($(this));
      }

      if(validarParaSpan() ){
        $("#spanDatosBasicos").remove();
      }
    });
  }
  
}

const ui = new UI();

function filtrarByRut(rutPaciente, pacienteNuevo = false) {
  rutPaciente.replace(".", "");
  $("#tbodyFiltro").empty();
  $.ajax({
    type: "post",
    data: "rutFiltro=" + rutPaciente,
    datatype: "json",
    success: function(pacientes) {
      try {
        let infoPaciente = JSON.parse(pacientes);
        if(infoPaciente.dp.dpIdpaciente === 0){
          if(pacienteNuevo){
           // $('#idModalPacienteNuevo').data("set",rutPaciente.replace(/PASAPORTE/, ""));
            //$('#idModalPacienteNuevo').modal('show');
          }else{
            // handlerMessageError("Paciente no encontrado");
          }
        }else{
          let paciente = infoPaciente.dp;//.pacientes[0]; //PACIENTE QUE SE RETORNO DESDE JAVA
          $("#nDocumento").text('N° DOCUMENTO');
          if (paciente.dpIdpaciente === 0) {
            $('html, body').animate({
              scrollTop: $("div#divForm").offset().top
            }, 700);
            $("#confirmarDatos").hide();
            handlerMessageError('No se encontraron pacientes');
          } else {

            //RELLENAR FORMULARIO
  
            paciente.listaPatologias = infoPaciente.listaPatologias;
            rellenarFormulario(paciente, infoPaciente.dc, "");
  
            $("#divBtnEditar").show();
            $("divIdCheckBoxExitus").show();
            $("#divBtnLimpiar").hide();
            $('html, body').animate({
              scrollTop: $("div#divForm").offset().top
            }, 700);
  
            if (getWithExpiry('validador') === '1') {
              setLocalPaciente({
                rut: paciente.Rut,
                nombre: paciente.Nombres,
                apellido: paciente.PrimerApellido,
                apellido2: paciente.SegundoApellido,
                edad: fecha
              });
              $("#confirmarDatos").show();
            }
  
            activarBtnEdit();
            desactivarLimpiar();
            generarEdad();
            console.log(paciente);
            localStorage.setItem("editar", "no");
            if (paciente.ldvRegiones != undefined) {
              $("#selectRegion").val(paciente.ldvRegiones);
              let idRegion = $("#selectRegion :selected").val();
              let idComuna = paciente.ldvComunas;
              buscarComuna(idRegion,idComuna);
              wait();
              // $("#selectComuna").val(idComuna);
              $('.selectpicker').selectpicker('refresh');
            }
            $("#panelBusquedaPaciente").collapse('hide')
          }
          // handlerMessageOk("Paciente ya registrado");
          wait();
          if ($("#spanDatosMadre").length > 0) {
            $("#spanDatosMadre").remove();
          }
          if ($("#spanDatosBasicos").length > 0) {
            $("#spanDatosBasicos").remove();
          }
        }
      
      } catch (e) {
        console.log(e);
        handlerMessageError(e);
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError(msg);
    }
  });
}

function filtrarByNombre(nombrePaciente, apellidoPaciente, segundoApellidoPaciente, tipoDocumento) {
  $("#tbodyFiltro").empty();
  $.ajax({
    type: "post",
    data: "nombreFiltro=" + nombrePaciente + "&apellidoFiltro=" + apellidoPaciente + "&segundoApellidoFiltro=" + segundoApellidoPaciente + "&tipoDocumento=" + tipoDocumento,
    datatype: "json",
    success: function(pacientes) {
      try {
        let infoPaciente = JSON.parse(pacientes);
        console.log(infoPaciente);
        $("#thIdentificador").text("Identificador");
        $("#thNombre").text("Nombre");
        $("#tdFechaIngreso").remove();
        if (infoPaciente === "empty") {
          $("#lblErrorFiltro").show();
        } else {
          $("#lblErrorFiltro").hide();
          let cont = 0;
          let tipoDocFiltro = $("#selectTipoDocumentoFiltro, #selectTipoDocumentoFiltro2 :selected").val();
          infoPaciente.pacientes.forEach(function(paciente) {
            cont++;
            if (paciente.SegundoApellido === undefined) {
              paciente.SegundoApellido = "";
            }
            paciente.Rut = paciente.Rut.replace(",", "");
            switch (tipoDocFiltro) {
              case TipoDocRun:
                $("#tbodyFiltro").append("<tr class='pointer' onclick='buscarRut(this)'><td><b>" + cont + "</b></td><td class='rutFiltro'>" + cambiarDatoRut(paciente.Rut) + "</td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"":paciente.SegundoApellido) + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                break;
              case TipoDocPasaporte:
                $("#tbodyFiltro").append("<tr class='pointer' onclick='getPacientexId(this)'><td><b>" + cont + "</b></td><td class='rutFiltro'>" + paciente.Rut + "</td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"":paciente.SegundoApellido) + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                break;
              case TipoDocDni:
                $("#tbodyFiltro").append("<tr class='pointer' onclick='getPacientexId(this)'><td><b>" + cont + "</b></td><td class='rutFiltro'>" + paciente.Rut + "</td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"":paciente.SegundoApellido) + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                break;
              case TipoDocRecienNacido:
                $("#thIdentificador").text("Nombre");
                $("#thNombre").text("Nº de RN");
                $("#tdFechaIngreso").remove();
                if (paciente.Secuencia == 0) {
                  paciente.Secuencia = "HIJO ÚNICO";
                }
                $("#tbodyFiltro").append("<tr class='pointer' onclick='getDataRN(this)'><td><b>" + cont + "</b></td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"HIJO ÚNICO":paciente.SegundoApellido) + "</td><td class=''>" + (paciente.Secuencia===undefined?"":paciente.Secuencia) + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                if ($("#tableFiltro tr").length === 0) {
                  $("#lblErrorFiltro").show();
                }
                break;
              case TipoDocSinIdentificacion:
                let numeroColumns = $("#tableFiltro > thead > tr:first > th").length;
                $("#thIdentificador").text("Identificador");
                $("#thNombre").text("Nombre");
                if (numeroColumns <= 3) {
                  $("#trHeader").append("<th id='tdFechaIngreso'>Fecha Ingreso</th>");
                }
                $("#tbodyFiltro").append("<tr class='pointer' onclick='getPacientexId(this)'><td><b>" + cont + "</b></td><td class='rutFiltro'>" + paciente.Rut + "</td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"":paciente.SegundoApellido) + "</td><td>" + paciente.FechaIngreso + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                break;
              case TipoDocTodos:
                let tDoc = paciente.TipoDocumento.toString();
                switch (tDoc) {
                  case TipoDocRun:
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='buscarRut(this)'><td><b>" + cont + "</b></td><td class='rutFiltro'>" + cambiarDatoRut(paciente.Rut) + "</td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"":paciente.SegundoApellido) + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                    break;
                  case TipoDocPasaporte:
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='getPacientexId(this)'><td><b>" + cont + "</b></td><td class='rutFiltro'>" + paciente.Rut + "</td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"":paciente.SegundoApellido) + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                    break;
                  case TipoDocRecienNacido:
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='getDataRN(this)'><td><b>" + cont + "</b></td><td class='rutFiltro'>" + cambiarDatoRut(paciente.Rut) + "</td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"":paciente.SegundoApellido) + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                    break;
                  case TipoDocSinIdentificacion:
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='getPacientexId(this)'><td><b>" + cont + "</b></td><td class='rutFiltro'>" + paciente.Rut + "</td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"":paciente.SegundoApellido) + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                    break;
                  default:
                    $("#tbodyFiltro").append("<tr class='pointer' onclick='getPacientexId(this)'><td><b>" + cont + "</b></td><td class='rutFiltro'>" + paciente.Rut + "</td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + (paciente.SegundoApellido===undefined?"":paciente.SegundoApellido)+ "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
                    break;
                }
                break;
            }
          });
          $("#lblTotalFiltro").removeAttr("hidden");
          $("#totalFiltro").text(infoPaciente.pacientes.length);
        }
      } catch (e) {
        console.log(e);
        handlerMessageError(e);
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError(msg);
    }
  });
}

function filtrarByRutMadre(rutMadre) {
  $("#tbodyFiltro").empty();
  $.ajax({
    type: "post",
    data: "rutMadreFiltro=" + rutMadre,
    datatype: "json",
    success: function(pacientes) {
      try {
        let infoPaciente = JSON.parse(pacientes);
        console.log('filtro madre',pacientes);
        console.table(infoPaciente.pacientes);
        let cont = 0;
        $("#thIdentificador").text("Nombre");
        $("#thNombre").text("Nº de RN");
        $("#tdFechaIngreso").remove();
        infoPaciente.pacientes.forEach(function(paciente) {
          cont++;
          if (paciente.SegundoApellido == undefined) {
            paciente.SegundoApellido = "";
          }
          if (paciente.Secuencia == 0) {
            paciente.Secuencia = "HIJO ÚNICO";
          }
          $("#tbodyFiltro").append("<tr class='pointer' onclick='getDataRN(this)'><td><b>" + cont + "</b></td><td class='nombrefiltro'>" + paciente.Nombre + " " + paciente.Apellido + " " + paciente.SegundoApellido + "</td><td >" + (paciente.Secuencia !== undefined ? paciente.Secuencia : "") + "</td><td class='idPaciente' style='display:none'>" + paciente.Id + "</td></tr>");
          if ($("#tableFiltro tr").length === 0) {
            $("#lblErrorFiltro").show();
            $("#panelBusquedaPaciente").collapse('hide')
          } else {
            $("#lblErrorFiltro").hide();
          }
        });
        $("#lblTotalFiltro").removeAttr("hidden");
        $("#totalFiltro").text(infoPaciente.pacientes.length);
      } catch (e) {
        console.log(e);
        handlerMessageError('No se pudo recuperar datos del paciente.');
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError(msg);
    }
  });
}

function filtrarNN(fecha,tipoDoc) {
  $("#tbodyFiltro").empty();
  $.ajax({
    type: "post",
    data: "getNN='s'&fecha=" + fecha+"&tipoDoc=".concat(tipoDoc),
    datatype: "json",
    success: function(_paciente) {
      try {
        let cont = 0;
        let listaPaciente = JSON.parse(_paciente);
        let numeroColumns = $("#tableFiltro > thead > tr:first > th").length;
        $("#thIdentificador").text("Identificador");
        $("#thNombre").text("Nombre");
        listaPaciente.pacienteNN.forEach(function(paciente) {
          cont++;
          if (paciente.SegundoApellido == undefined) {
            paciente.SegundoApellido = "";
          }
          if (numeroColumns <= 3) {
            $("#trHeader").append("<th id='tdFechaIngreso'>Fecha Ingreso</th>")
          }
          $("#tbodyFiltro").append("<tr class='pointer' onclick='getPacientexId(this)'><th class='row mx-auto'><b>" + cont + "</b></th><td class='rutFiltro'>" + paciente.nroDocumento + "</td><td class='nombrefiltro'>" + paciente.nombres + " " + paciente.primerApellido + " " + paciente.SegundoApellido + "</td><td>" + paciente.fechaIngreso + "</td><td class='idPaciente' style='display:none'>" + paciente.idPaciente + "</td></tr>");

        });
        $("#lblTotalFiltro").removeAttr("hidden");
        $("#totalFiltro").text(listaPaciente.pacienteNN.length);
      } catch (e) {
        console.log(e);
        handlerMessageError(e);
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError(msg);
    }
  });
}


function getDataRN(a) {
  let $row = $(a).closest("tr"); // Find the row
  let idPaciente = $row.find(".idPaciente").text(); // Find the text
  $(".is-invalid").removeClass("is-invalid");
  $("#tbodyFiltro").empty();
  $.ajax({
    type: "post",
    url: gCte.getctx + '/api/paciente/rn/' + idPaciente,
    success: function(rpta) {
      try {
        console.table(rpta.dato);//

        if (rpta.status !== 200) {
          handlerMessageError(rpta.message);
          return;
        }
        let paciente = rpta.dato.dp;
        let contacto = rpta.dato.dc;
        $("#nDocumento").text('N° DOCUMENTO');
        if (paciente.dpRun === "0") {
          $('html, body').animate({
            scrollTop: $("div#divForm").offset().top
          }, 700);
          //          $('#txtRut').val(paciente.RutV);
          $("#confirmarDatos").hide();
          handlerMessageError('No se encontraron pacientes');
        } else {
          //RELLENAR FORMULARIO
          rellenarFormulario(paciente, contacto, rpta.dato.rutMadre);
          $("#divBtnEditar").show();
            $("divIdCheckBoxExitus").show();
          $("#divBtnLimpiar").hide();
          $('html, body').animate({
            scrollTop: $("div#divForm").offset().top
          }, 700);

          activarBtnEdit();
          desactivarLimpiar();
          generarEdad();
          localStorage.setItem("editar", "no");

          if (paciente.ldvRegiones != undefined) {
            $("#selectRegion").val(paciente.ldvRegiones);
            let idRegion = $("#selectRegion :selected").val();
            let idComuna = paciente.ldvComunas;
            buscarComuna(idRegion,idComuna);
            wait();
            // $("#selectComuna").val(idComuna);
            // $('.selectpicker').selectpicker('refresh');
          }
          $("#panelBusquedaPaciente").collapse('hide');
          $("#anchorRegistro").click();
        }
      } catch (e) {
        console.log(e);
        handlerMessageError(e);
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError(msg);
    }
  });
}


function updateCliente() {

  let datosPaciente = getDpFromForm();
   if(!datosPaciente){
    return false;
   }
  $.ajax({
    type: "post",
    url: gCte.getctx + '/api/paciente/actualiza',
    data: JSON.stringify(datosPaciente),
    dataType: 'json',
    contentType: "application/json",
    success: function(rpta) {
      try {
        if (rpta.status !== 200) {

          console.table(rpta.dato);//
          $("#nDocumento").text('N° DOCUMENTO');
          $('html, body').animate({
            scrollTop: $("div#divForm").offset().top
          }, 700);
          //          $('#txtRut').val(paciente.RutV);
          $("#confirmarDatos").hide();
          handlerMessageError('No se encontraron pacientes');
        } else {
          $("#panelBusquedaPaciente").collapse('hide')
          scrollToAnchor('tab_pane_datos_basicos');
          $("#anchorRegistro").click();
          limpiarFormulario();
          setModoRegistroCliente(ModoAgregacion);
          $("#spanDatosBasicos").remove();
          $("#spanDatosMadre").remove();
          handlerMessageOk("Se modificaron los datos satisfactoriamente");

        }
      } catch (e) {
        console.log(e);
        scrollToAnchor('tab_pane_datos_basicos');
        handlerMessageError(e);
      }
    },
    error: function(msg) {
      console.log(msg);
      scrollToAnchor('tab_pane_datos_basicos');
      handlerMessageError(msg);
    }
  });
}

function clickBotonBuscarFiltro() {
  let rut;
  let tipoDocumentoFiltro = $("#selectTipoDocumentoFiltro :selected").val();
  let se_busca_por_nombre = false;
  if ($("#txtFiltroNombre").val().trim().length >= 1 || $("#txtFiltroApellido").val().trim().length >= 1 || $("#txtFiltroSegundoApellido").val().trim().length >= 1) {
    se_busca_por_nombre = true;
  }
  if (se_busca_por_nombre && ($("#txtFiltroNombre").val().trim().length < 2 && $("#txtFiltroApellido").val().trim().length < 2 && $("#txtFiltroSegundoApellido").val().trim().length < 2)) {
    // handlerMessageError('Se deben ingresar dos o más caracteres');
      return false;
  }

  if (se_busca_por_nombre) {
    filtrarByNombre($("#txtFiltroNombre").val(), $("#txtFiltroApellido").val(), $("#txtFiltroSegundoApellido").val(), $("#selectTipoDocumentoFiltro :selected").val());
  }
  else {
    switch (tipoDocumentoFiltro) {
      case TipoDocSinIdentificacion:
        if (validaParametrosNN()) {
          filtrarNN($("#txtFiltroNN").val(),TipoDocSinIdentificacion);
        }
        break;
      case TipoDocRecienNacido:
        if (validaParametrosRN()) {
          filtrarByRutMadre($("#txtFiltroRut").val());

        }
        else {
          handlerMessageError('Se debe completar al menos un campo de búsqueda');
        }
        break;
      case TipoDocRun:
        rut = $("#txtFiltroRut").val().trim();
        if (rut.length > 0) {
          filtrarByRut(rut,true);
        }
        else {
          handlerMessageError('Se debe completar al menos un campo de búsqueda');
        }
        break;
      case TipoDocPasaporte:
        rut = $("#txtPasaporteFiltro").val().trim();
        if (rut.length > 0) {
          filtrarByPasaporte(rut);
        }
        else {
          handlerMessageError('Se debe completar al menos un campo de búsqueda');
        }
        break;
      case TipoDocDni:
        rut = $("#txtDniFiltro").val().trim();
        if (rut.length > 0) {
          filtrarByDni(rut);
        }
        else {
          handlerMessageError('Se debe completar al menos un campo de búsqueda');
        }
        break;
      case TipoDocTodos:
        if (validaParametrosTodos()) {
          filtrarByNombre($("#txtFiltroNombre").val(), $("#txtFiltroApellido").val(), $("#txtFiltroSegundoApellido").val(), $("#selectTipoDocumentoFiltro :selected").val());
        }
        break;
      default:
        handlerMessageError('Error inseperado, por favor comuniquese con el administrador');
        break;
    }
  }
}



function filtrarByPasaporte(pasaportePaciente) {
  $("#tbodyFiltro").empty();
  $.ajax({
    url: getctx + "/api/paciente/pasaporte/" + pasaportePaciente,
    type: "post",
    datatype: "json",
    success: function(response) {
      try {
        let status = response.status;
        if (status === 200) {
          pacientes = response.dato;
          infoPaciente = pacientes;
          let paciente = infoPaciente.dp
          $("#nDocumento").text('N° DOCUMENTO');
          if (paciente.dpIdpaciente === 0) {

            $("#confirmarDatos").hide();
           // $('#idModalPacienteNuevo').modal('show');
            //handlerMessageError('No se encontraron pacientes');
          } else {
            //RELLENAR FORMULARIO
            paciente.listaPatologias = new Object();
            paciente.listaPatologias = infoPaciente.listaPatologias;
            vaciarFormularioRegistroCliente();
            rellenarFormulario(paciente, infoPaciente.dc, "");
            $("#divBtnEditar").show();
            $("divIdCheckBoxExitus").show();
            $("#divBtnLimpiar").hide();
            $('html, body').animate({
              scrollTop: $("div#divForm").offset().top
            }, 700);

            if (getWithExpiry('validador') === '1') {
              setLocalPaciente({
                rut: paciente.Rut,
                nombre: paciente.Nombres,
                apellido: paciente.PrimerApellido,
                apellido2: paciente.SegundoApellido,
                edad: fecha
              });
              $("#confirmarDatos").show();
            }

            activarBtnEdit();
            desactivarLimpiar();
            generarEdad();
            localStorage.setItem("editar", "no");

            if (paciente.ldvRegiones != undefined) {
              $("#selectRegion").val(paciente.ldvRegiones);
              let idRegion = $("#selectRegion :selected").val();
              let idComuna = paciente.ldvComunas
              buscarComuna(idRegion,idComuna);
              wait();
              // $("#selectComuna").val(idComuna);
              // $('.selectpicker').selectpicker('refresh');
            }
            $("#panelBusquedaPaciente").collapse('hide')
          }

        }
      } catch (e) {
        console.log(e);
        handlerMessageError(e);
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError(msg);
    }
  });
}

//modificarlo cuando la api este lista
function filtrarByDni(dni) {
  $("#tbodyFiltro").empty();
  $.ajax({
    url: getctx + "/api/paciente/dni/" + dni,
    type: "post",
    datatype: "json",
    success: function(response) {
      try {
        let status = response.status;
        if (status === 200) {
          pacientes = response.dato;
          infoPaciente = pacientes;
          let paciente = infoPaciente.dp
          $("#nDocumento").text('N° DOCUMENTO');
          if (paciente.dpIdpaciente === 0) {

            $("#confirmarDatos").hide();
           // $('#idModalPacienteNuevo').modal('show');
            //handlerMessageError('No se encontraron pacientes');
          } else {
            //RELLENAR FORMULARIO
            paciente.listaPatologias = new Object();
            paciente.listaPatologias = infoPaciente.listaPatologias;
            vaciarFormularioRegistroCliente();
            rellenarFormulario(paciente, infoPaciente.dc, "");
            $("#divBtnEditar").show();
            $("divIdCheckBoxExitus").show();
            $("#divBtnLimpiar").hide();
            $('html, body').animate({
              scrollTop: $("div#divForm").offset().top
            }, 700);

            if (getWithExpiry('validador') === '1') {
              setLocalPaciente({
                rut: paciente.Rut,
                nombre: paciente.Nombres,
                apellido: paciente.PrimerApellido,
                apellido2: paciente.SegundoApellido,
                edad: fecha
              });
              $("#confirmarDatos").show();
            }

            activarBtnEdit();
            desactivarLimpiar();
            generarEdad();
            localStorage.setItem("editar", "no");

            if (paciente.ldvRegiones != undefined) {
              $("#selectRegion").val(paciente.ldvRegiones);
              let idRegion = $("#selectRegion :selected").val();
              let idComuna = paciente.ldvComunas
              buscarComuna(idRegion,idComuna);
              wait();
              // $("#selectComuna").val(idComuna);
              // $('.selectpicker').selectpicker('refresh');
            }
            $("#panelBusquedaPaciente").collapse('hide')
          }

        }
      } catch (e) {
        console.log(e);
        handlerMessageError(e);
      }
    },
    error: function(msg) {
      console.log(msg);
      handlerMessageError(msg);
    }
  });
}



function validaParametrosNN() {
  if ($("#txtFiltroNombre").val().trim() === "" && $("#txtFiltroApellido").val().trim() === "" && $("#txtFiltroSegundoApellido").val().trim() === "" &&
    $("#txtFiltroNN").val() === "") {
    handlerMessageError('Se debe completar al menos un campo de búsqueda');
    return false;
  }
  return true;
}

function validaParametrosRN() {
  if ($("#txtFiltroNombre").val().trim() === "" && $("#txtFiltroApellido").val().trim() === "" && $("#txtFiltroSegundoApellido").val().trim() === "" &&
    $("#txtFiltroRut").val() === "") {
    handlerMessageError('Debe ingresar al menos un criterio');
    return false;
  }
  return true;
}

function validaParametrosTodos() {
  if ($("#txtFiltroNombre").val().trim() === "" && $("#txtFiltroApellido").val().trim() === "" && $("#txtFiltroSegundoApellido").val().trim() === "") {
    handlerMessageError('Se debe completar al menos un campo de búsqueda');
    return false;
  }
  return true;
}


