$(document).ready(function() {
  $("#idTestCanvasNotificationsPhysicianEmail").dblclick(setMailDest);
  $("#testNotificationSaveButton").click(saveNotification);
})

function setMailDest(e) {
  console.table(e);
  let idNav = $("#myTabNotify").find('.nav-link.active').attr("data-target");
  let idOnly = idNav.match(/\d+/);
  if (idOnly == null) {
    idOnly = "";
  } else {
    idOnly = idOnly[0];
  }


  if (!$(`#idTestCanvasNotificationsSendTo${idOnly}`).is(':disabled') && $(`#idTestCanvasNotificationsSendTo${idOnly}`).val() == "" && $(`#idTestCanvasNotificationsPhysicianEmail`).text() != "") {
    $(`#idTestCanvasNotificationsSendTo${idOnly}`).val(e.currentTarget.innerText);
  }
  // $(idNav).find("")
  // $("#idTestCanvasNotificationsSendTo").val(e.currentTarget.innerText);
}



function getNotificationModalData() {

  let notificacion = new Object();
  notificacion.dfet_RESULTADO = $("#idTestCanvasNotificationsResult").text();
  notificacion.dfet_RCRITICO = $("#idTestCanvasNotificationsCriticalResult").text();
  notificacion.cm_NOMBRES = $("#idTestCanvasNotificationsPhysician").text();
  notificacion.dp_NOMBRES = $("#idTestCanvasNotificationsPatient").text();
  notificacion.cm_TELEFONO = $("idTestCanvasNotificationsPhysicianPhone").text();
  notificacion.dp_TELEFONO = $("#idTestCanvasNotificationsPatientPhone").text();
  notificacion.cm_EMAIL = $("#idTestCanvasNotificationsPhysicianEmail").text();
  notificacion.dp_EMAIL = $("#idTestCanvasNotificationsPatientEmail").text();
  notificacion.dftn_MENSAJENOTIFICACION = $("#idTestCanvasNotificationsMesage").val();
  notificacion.dftn_EMAILENVIO = $("#idTestCanvasNotificationsSendTo").val();
  notificacion.dftn_IDVIANOTIFICACION = $("#idTestCanvasNotificationsComunicationChannel").val();
  notificacion.dftn_NUMEROINTENTOS = $("#idTestCanvasNotificationsCommunicationAttempts").val();
  notificacion.dftn_NOTA = $("#idTestCanvasNotificationsNote").val();
  notificacion.nOrden = $("#nOrden").val();
  notificacion.examenId = $("#examenId").val();
  notificacion.testId = $("#testId").val();
  notificacion.df_NORDEN = $("#nOrden").val();
  notificacion.dfe_IDEXAMEN = $("#examenId").val();
  notificacion.dfet_IDTEST$("#testId").val();
  let checkbox = document.getElementById("chckTestCanvasNotificationsExamen");
  notificacion.tipo_NOTIFICACION = 0;
  if (checkbox.checked) {
    notificacion.tipo_NOTIFICACION = 2;
  }
  checkbox = document.getElementById("chckTestCanvasNotificationsOrden");
  if (checkbox.checked) {
    notificacion.tipo_NOTIFICACION = 1;
  }
  return notificacion;
}

function notifyResultTest(element) {

  let notification = getDataTestNotificationMultiples($(element).closest('[role="tabpanel"]').attr("id"));
  let url = gCte.getctx + '/api/orden/' + nroOrdenSeleccionada + '/examenes/resultados/notify';
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(notification),
    success: handlerResponseOk,
    error: handlerResponseError,
    contentType: 'application/json; charset=utf-8'
  });
}

function notifyTestResult(idOrden, idExamen, idTest) {
  let url = gCte.getctx + `/api/orden/${idOrden}/examen/${idExamen}/test/${idTest}`;
  $.ajax({
    type: "get",
    url: url,
    success: function(data) {
      if (data.status == 200) {
        // fillNotificacionResultadosModal(data[0], idOrden, idExamen, idTest); 
        if (data.dato != null) {
          let val = data.dato.dfet_RCRITICO;

          switch (val) {
            case 'Normal':
              rpta = 'N';
              break;
            case 'B':
              rpta = 'N';
              break;
            case 'CB':
              rpta = 'S';
              break;
            case 'A':
              rpta = 'N';
              break;
            case 'CA':
              rpta = 'S';
              break;
            default:
              rpta = 'N';
              break;
          }
          limpiarTabsNotificacion();
          if (rpta === "N") {
            handlerMessageWarning('No existe un resultado critico para notificar')
            return;
          }
        }
        $("#testNotificationSaveButton").prop('disabled',true)
        fillNotificacionResultadosModal(data.dato, idOrden, idExamen, idTest);
        $("#notificacionResultadosModal").modal('show');
      }
    },
    error: function(data) { alert('Errror'); },
    contentType: 'application/json; charset=utf-8'
  });


}

//agrega pestaña
function agregarTabs(counter) {
  //toma el div original y lo clona

  if (counter == 2 && $("#idTestCanvasNotificationsName").val() == "" || counter > 2 && $("#idTestCanvasNotificationsName" + (counter - 1)).val() == "") {
    return;
  }

  if (counter == 2 && $("#selectNotificacionTipoReceptor").val() == "" || counter > 2 && $("#selectNotificacionTipoReceptor" + (counter - 1)).val() == "") {
    return;
  }

  let valor;
  if (counter == 2) {
    valor = 1;
  } else {
    valor = counter - 1;
  }

  if ($("#nOrden").val() == "" || $("#examenId").val() == "" || $("#testId").val() == "") {
    return;
  }
  let url = `api/orden/${$("#nOrden").val()}/examen/${$("#examenId").val()}/test/${$("#testId").val()}/notificacion/${valor}/validador`;
  $.ajax({
    type: "POST",
    url: url,
    success: function(data) {
      if (data.status == 200 && !data.dato) {
        return;
      }
    },
    error: function(jqxhr, textStatus, error) {
      let err = 'Status:' + jqxhr.status + ' ' + textStatus + ", " + error;
      handlerMessageError(err);
      console.log("Request Failed: " + err);
    },
    contentType: 'application/json; charset=utf-8'
  });


  let originalDiv = $("div#condicion-sub");
  let cloneDiv = originalDiv.clone();

  //cambiar datos de los divs clonados
  cloneDiv.attr('id', 'condicion-sub' + counter);
  cloneDiv.removeClass("show active");

  cloneDiv.addClass('contenedor-copia');
  cloneDiv.removeAttr('style');

  $("#idTestCanvasNotificationsDate", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsDate' + counter });
  $("#idTestCanvasNotificationsDate" + counter, cloneDiv).val("");

  $("#idTestCanvasNotificationsUser", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsUser' + counter });
  $("#idTestCanvasNotificationsUser" + counter, cloneDiv).val("");

  $(".testCanvasNotificationsContainer", cloneDiv).each(function() {
    if (!$(this).hasClass("ocultar")) {
      $(this, cloneDiv).addClass("ocultar");
      $(this, cloneDiv).css("display", "none");
    }
  });


  $("#idTestCanvasNotificationsMesage", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsMesage' + counter });
  $("#idTestCanvasNotificationsMesage" + counter, cloneDiv).val("");

  $("#idTestCanvasNotificationsSendTo", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsSendTo' + counter });
  $("#idTestCanvasNotificationsSendTo" + counter, cloneDiv).val("");


  $("#selectNotificacionTipoReceptor", cloneDiv).attr({ 'id': 'selectNotificacionTipoReceptor' + counter });    //le cambia el nombre de la id
  $("#selectNotificacionTipoReceptor" + counter, cloneDiv).val("");

  $("#idTestCanvasNotificationsName", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsName' + counter });
  $("#idTestCanvasNotificationsName" + counter, cloneDiv).val("");

  $("#idTestCanvasNotificationsComunicationChannel", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsComunicationChannel' + counter });    //le cambia el nombre de la id
  $("#idTestCanvasNotificationsComunicationChannel" + counter, cloneDiv).val("");

  $("#idTestCanvasNotificationsNote", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsNote' + counter });
  $("#idTestCanvasNotificationsNote" + counter, cloneDiv).val("");

  $("#idTestCanvasNotificationsConfirmsReception", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsConfirmsReception' + counter });
  $("#idTestCanvasNotificationsConfirmsReception" + counter, cloneDiv).prop("checked", false);

  $(".chkErrorContainer", cloneDiv).each(function() {
    if (!$(this).hasClass("ocultar")) {
      $(this, cloneDiv).addClass("ocultar");
      $(this, cloneDiv).css("display", "none");
    }
  });

  $("#idTestCanvasNotificationsTypeError", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsTypeError' + counter });
  $("#idTestCanvasNotificationsTypeError" + counter, cloneDiv).val("");

  $("#idTestCanvasNotificationsErrorNote", cloneDiv).attr({ 'id': 'idTestCanvasNotificationsErrorNote' + counter });
  $("#idTestCanvasNotificationsErrorNote" + counter, cloneDiv).val("");
  $(`.chckTestCanvasNotificationsError`, cloneDiv).removeAttr('disabled');


  $(".typeErrorContainer", cloneDiv).each(function() {
    if (!$(this).hasClass("ocultar")) {
      $(this, cloneDiv).addClass("ocultar");
      $(this, cloneDiv).css("display", "none");
    }
  });

  $(".chckTestCanvasNotificationsError", cloneDiv).addClass('chckTestCanvasNotificationsErrorCopia')

  $('.chckTestCanvasNotificationsErrorCopia',cloneDiv).change(function() {
    console.log('adsda')
    if(this.checked) {
      $(this).closest('.tab-pane.fade.active.show').find(".typeErrorContainer").each(function() {
        if ($(this).hasClass("ocultar")) {
          $(this).removeClass("ocultar");
          $(this).removeAttr("style");
        }
      });  
      $("#idTestCanvasNotificationsTypeError"+counter,cloneDiv).val("");  
      $("#idTestCanvasNotificationsErrorNote"+counter,cloneDiv).val("");
      $(`#idTestCanvasNotificationsTypeError${counter}, #idTestCanvasNotificationsErrorNote${counter}`).removeAttr('disabled');
    } else {
      $(this).closest('.tab-pane.fade.active.show').find(".typeErrorContainer").each(function() {
        if (!$(this).hasClass("ocultar")) {
          $(this).addClass("ocultar");
          $(this).css("display", "none");
        }
      });  
      $("#idTestCanvasNotificationsTypeError"+counter,cloneDiv).val("");  
      $("#idTestCanvasNotificationsErrorNote"+counter,cloneDiv).val("");
      $(`#idTestCanvasNotificationsTypeError${counter}, #idTestCanvasNotificationsErrorNote${counter}`).prop('disabled', true);
    }
  }); 




  // //agrega en el div los datos ya formatiados
  $("#myTabNotifyContent").append(cloneDiv);

  $(".testSendMailButton").unbind("click");
  $(".testSendMailButton").click(function() {
    notifyResultTest(this);
  });


  // $('.tab-pane.show').find('.classTestCanvasNotificationsName').off('blur');

  //agregando al tab
  let originalTab = $("li#presentacion");
  let cloneTab = originalTab.clone();

  cloneTab.attr('id', 'presentacion' + counter);
  cloneTab.addClass('contenedor-copia');
  //  saveNotification
  $("#condicion", cloneTab).attr({ 'id': 'condicion' + counter, 'data-target': '#condicion-sub' + counter, 'aria-controls': "condicion-sub" + counter, 'aria-selected': "false" });    //cambia los atributos del tab
  $("#condicion" + counter, cloneTab).removeClass("active");
  $("#condicion" + counter, cloneTab).removeClass("green-color");
  $("#condicion" + counter, cloneTab).text("Notificación " + counter)
  $("#condicion" + counter, cloneTab).removeAttr("style");
  $("#nav-item-agregar").before(cloneTab);

  $(`#condicion-sub${counter} textarea, #condicion-sub${counter} input[type="checkbox"], #condicion-sub${counter} input[type="text"], #condicion-sub${counter} select`).removeAttr('disabled');

  //se marca la nueva pestaña
  $("#myTabNotifyContent").find("[aria-labelledby='condicion']").removeClass("active show");
  $(`#condicion-sub${counter}`).addClass("active show");

  $("#myTabNotify").find(".nav-link").removeClass("active");
  $(`#condicion${counter}`, cloneTab).addClass("active");

  $('.tab-pane.show').find('.classTestCanvasNotificationsName').off('blur');
  $('.tab-pane.show').find('.classNotificacionTipoReceptor').off('change');

  $('.tab-pane.show').find('.classTestCanvasNotificationsName').on('blur', function() {
    verificarDatos()
  });
  
  $('.tab-pane.show').find('.classNotificacionTipoReceptor').on('change', function() {
    verificarDatos()
  });

  $('.tab-pane.show').find('.testSendMailButton').prop('disabled',true)
  $('.tab-pane.show').find('.classTestCanvasNotificationsSendTo').off('blur')
  $('.tab-pane.show').find('.classTestCanvasNotificationsSendTo').on('blur', function() {
    verificarMail()
  });
  
  
  $('.tab-notificacion').off('shown.bs.tab');

  $('.tab-notificacion').on('shown.bs.tab', function () {
    verificarDatos()
    verificarMail()
  });
  

  //incrementa el contador
  counter++;
  $("#agregar-tab").attr("onclick", "agregarTabs(" + counter + ")");

}

$('.chckTestCanvasNotificationsError').change(function() {
  if(this.checked) {
    console.log('entra aca');
    $(this).closest('.tab-pane.fade.active.show').find(".typeErrorContainer").each(function() {
      if ($(this).hasClass("ocultar")) {
        $(this).removeClass("ocultar");
        $(this).removeAttr("style");
      }
    });  
    $("#idTestCanvasNotificationsTypeError").val("");  
    $("#idTestCanvasNotificationsErrorNote").val("");
    $(`#idTestCanvasNotificationsTypeError, #idTestCanvasNotificationsErrorNote`).removeAttr('disabled');
  } else {
    console.log('entra aca unchecked');
    $(this).closest('.tab-pane.fade.active.show').find(".typeErrorContainer").each(function() {
      if (!$(this).hasClass("ocultar")) {
        $(this).addClass("ocultar");
        $(this).css("display", "none");
      }
    });  
    $("#idTestCanvasNotificationsTypeError").val("");  
    $("#idTestCanvasNotificationsErrorNote").val("");
    $(`#idTestCanvasNotificationsTypeError, #idTestCanvasNotificationsErrorNote`).prop('disabled', true);
  }
}); 

function saveNotification(e) {


  const format1 = "DD/MM/YYYY HH:mm:ss"
  let date2 = new Date();
  let dateTime2 = moment(date2).format(format1);

  let notificacion = new Object();
  let conversiones = new Array();
  let count = 0;
  $("#myTabNotify li.condition").each(function() {
    count++;
    let conversion = {};
    if (count === 1) {
      conversion.notificacionid = count;
      conversion.notificaciondate = dateTime2;
      conversion.notificacionuser = $("#idUsuarioSession").val();
      conversion.notificacionmesage = $("#idTestCanvasNotificationsMesage").val();
      conversion.notificacionsendto = $("#idTestCanvasNotificationsSendTo").val();
      conversion.notificacionreceptor = $("#selectNotificacionTipoReceptor").val();
      conversion.notificacionname = $("#idTestCanvasNotificationsName").val();
      conversion.notificacionnote = $("#idTestCanvasNotificationsNote").val();
      conversion.notificacionchannel = $("#idTestCanvasNotificationsComunicationChannel").val();
      conversion.notificacionconfirm = $("#idTestCanvasNotificationsConfirmsReception").is(":checked") ? "S" : "N";
      conversion.notificaciontyperror = $("#idTestCanvasNotificationsTypeError").val();
      conversion.notificacionerrornote = $("#idTestCanvasNotificationsErrorNote").val();
    } else {
      conversion.notificacionid = count;
      conversion.notificaciondate = dateTime2;
      conversion.notificacionuser = $(`#idUsuarioSession`).val();
      conversion.notificacionmesage = $(`#idTestCanvasNotificationsMesage${count}`).val();
      conversion.notificacionsendto = $(`#idTestCanvasNotificationsSendTo${count}`).val();
      conversion.notificacionreceptor = $(`#selectNotificacionTipoReceptor${count}`).val();
      conversion.notificacionname = $(`#idTestCanvasNotificationsName${count}`).val();
      conversion.notificacionnote = $(`#idTestCanvasNotificationsNote${count}`).val();
      conversion.notificacionchannel = $(`#idTestCanvasNotificationsComunicationChannel${count}`).val();
      conversion.notificacionconfirm = $(`#idTestCanvasNotificationsConfirmsReception${count}`).is(":checked") ? "S" : "N";
      conversion.notificaciontyperror = $(`#idTestCanvasNotificationsTypeError${count}`).val();
      conversion.notificacionerrornote = $(`#idTestCanvasNotificationsErrorNote${count}`).val();
    }
    conversiones.push(conversion);
  });

  notificacion.dfet_IDFICHATESTNOTIFICACION = $("#idFichaTestNotificacion").val();
  notificacion.dfet_RESULTADO = $("#idTestCanvasNotificationsResult").text();
  notificacion.dfet_RCRITICO = $("#idTestCanvasNotificationsCriticalResult").text();
  notificacion.cm_NOMBRES = $("#idTestCanvasNotificationsPhysician").text();
  notificacion.dp_NOMBRES = $("#idTestCanvasNotificationsPatient").text();
  notificacion.cm_TELEFONO = $("idTestCanvasNotificationsPhysicianPhone").text();
  notificacion.dp_TELEFONO = $("#idTestCanvasNotificationsPatientPhone").text();
  notificacion.cm_EMAIL = $("#idTestCanvasNotificationsPhysicianEmail").text();
  notificacion.dp_EMAIL = $("#idTestCanvasNotificationsPatientEmail").text();
  notificacion.nOrden = $("#nOrden").val();
  notificacion.examenId = $("#examenId").val();
  notificacion.testId = $("#testId").val();
  notificacion.df_NORDEN = $("#nOrden").val();
  notificacion.dfe_IDEXAMEN = $("#examenId").val();
  notificacion.dfet_IDTEST = $("#testId").val();
  let checkbox = document.getElementById("chckTestCanvasNotificationsExamen");
  notificacion.tipo_NOTIFICACION = 0;
  if (checkbox.checked) {
    notificacion.tipo_NOTIFICACION = 2;
  }
  checkbox = document.getElementById("chckTestCanvasNotificationsOrden");
  if (checkbox.checked) {
    notificacion.tipo_NOTIFICACION = 1;
  }
  
  checkbox = document.getElementById("chckTestCanvasNotificationsExamen");
  notificacion.tipo_NOTIFICACION = 0;
  if (checkbox.checked) {
    notificacion.tipo_NOTIFICACION = 2;
  }
  checkbox = document.getElementById("chckTestCanvasNotificationsOrden");
  if (checkbox.checked) {
    notificacion.tipo_NOTIFICACION = 1;
  }
  notificacion.notificacion = conversiones;
  const datosVacios = notificacion.notificacion.some(obj => obj.notificacionname === '' && obj.notificacionreceptor === '');
  if (datosVacios) {
    handlerMessageWarning('Debe seleccionar el personal notificado y el nombre')
    return;
  }
  $("#confirmModal").modal();
  $("#confirmModal").find(".modal-title").html("Notificación");
  $("#confirmModal").find(".modal-body").html("<p>No se podrán alterar los datos de la notificación posteriormente</p>");
  $("#confirmModal").find(".modal-footer").html("");
  $("#confirmModal").find(".modal-footer").append("<button class='btn btn-secondary btn-no' data-dismiss='modal'>No</button>");
  $("#confirmModal").find(".modal-footer").append("<button class='btn btn-primary btn-si'>Si</button>");
  $("#confirmModal .btn-si").click(function() {
    let url = gCte.getctx.concat(`/api/orden/${notificacion.nOrden}/examen/${notificacion.examenId}/test/${notificacion.testId}/notificacion/add`);

    try {
      $.ajax({
        url: url,
        data: JSON.stringify(notificacion),
        headers: {
          'Content-Type': 'application/json'
        },
        method: 'post',
        dataType: 'json',
        success: function(data) {
          if (data.status !== 200) {
            handlerMessageError(data.dato);
          } else {
            handlerMessageOk(data.dato);
            let url = gCte.getctx + `/api/orden/${$("#nOrden").val()}/examen/${$("#examenId").val()}/test/${$("#testId").val()}`;
            // let url = `http://localhost:3000/notify?nOrden=${idOrden}&examenId=${idExamen}&testId=${idTest}`; 
            $.ajax({
              type: "get",
              url: url,
              success: function(data) {
                if (data.status == 200) {
                  fillNotificacionResultadosModal(data.dato, $("#nOrden").val(), $("#examenId").val(), $("#testId").val());
                }
              },
              error: function(data) { alert('Errror'); },
              contentType: 'application/json; charset=utf-8'
            });
          }
        },
        error: function(jqxhr, textStatus, error) {
          let err = 'Status:' + jqxhr.status + ' ' + textStatus + ", " + error;
          handlerMessageError(err);
          console.log("Request Failed: " + err);
        }
      });
    } catch (e) {
      handlerMessageError(e);
    }
    $("#confirmModal").modal('hide');
  });


  $("#confirmModal .btn-no").click(function() {
    $("#confirmModal").modal('hide');
  })

}


function fillNotificacionResultadosModal(datos, nOrden, examenId, testId) {


  limpiarTabsNotificacion();
  if (datos != null) {

    let val = datos.dfet_RCRITICO;
    switch (val) {
      case 'Normal':
        rpta = '';
        break;
      case 'B':
        rpta = '<i class="fas fa-arrow-down text-danger"></i>';
        break;
      case 'CB':
        rpta = '<i class="fas fa-arrow-down text-danger"></i>';
        rpta = rpta + '<i class="fas fa-exclamation-triangle text-danger"></i>';
        break;
      case 'A':
        rpta = '<i class="fas fa-arrow-up text-danger"></i>';
        break;
      case 'CA':
        rpta = '<i class="fas fa-arrow-up text-danger"></i>';
        rpta = rpta + '<i class="fas fa-exclamation-triangle text-danger"></i>';
        break;
      default:
        rpta = '';
        break;
    }


    $("#idTestCanvasNotificationsabr").text(datos.ct_ABREVIADO);
    $("#idTestCanvasNotificationsResult").text(datos.dfet_RESULTADO);
    if (datos.dfet_REFERENCIADESDE != null && datos.dfet_REFERENCIAHASTA != null) {
      $("#idTestCanvasNotificationsCriticalResult").html(`${datos.dfet_REFERENCIADESDE}-${datos.dfet_REFERENCIAHASTA}`);
    } else {
      $("#idTestCanvasNotificationsCriticalResult").html(``);
    }

    $("#idTestCanvasNotificationsPhysician").text(datos.cm_NOMBRES);
    $("#idTestCanvasNotificationsPatient").text(datos.dp_NOMBRES);
    $("idTestCanvasNotificationsPhysicianPhone").text(datos.cm_TELEFONO);
    $("#idTestCanvasNotificationsPatientPhone").text(datos.dp_TELEFONO);
    $("#idTestCanvasNotificationsPhysicianEmail").text(datos.cm_EMAIL);
    $("#idTestCanvasNotificationsPatientEmail").text(datos.dp_EMAIL);

    $("#nOrden").val(nOrden);
    $("#examenId").val(examenId);
    $("#testId").val(testId);
    $("#idFichaTestNotificacion").val(datos.dfet_IDFICHATESTNOTIFICACION);
    if (datos.notificacion != null && datos.notificacion.length > 0) {
      let efectivo = false;
      datos.notificacion.forEach(function(not) {
        let nCond = not.notificacionid;
        let listUsuario = new CfgUsuario();
        listUsuario.getUsuario();

        let iser = listUsuario.obtenerPorId(not.notificacionuser)

        if (nCond === "1") {
          $(`#condicion-sub textarea, #condicion-sub input[type="checkbox"],  #condicion-sub input[type="text"], #condicion-sub select`).prop('disabled', true);
          $("#condicion-sub").addClass("active show");
          $("#condicion").addClass("active");
          $("#idTestCanvasNotificationsDate").val(not.notificaciondate);
          $("#idTestCanvasNotificationsUser").val(`${iser[0].duNombres} ${iser[0].duPrimerapellido}`);

          $("#idTestCanvasNotificationsMesage").val(not.notificacionmesage);
          $("#idTestCanvasNotificationsSendTo").val(not.notificacionsendto);
          $("#selectNotificacionTipoReceptor").val(not.notificacionreceptor);
          $("#idTestCanvasNotificationsName").val(not.notificacionname);
          $("#idTestCanvasNotificationsNote").val(not.notificacionnote);
          $("#idTestCanvasNotificationsComunicationChannel").val(not.notificacionchannel);
          $("#idTestCanvasNotificationsConfirmsReception").prop("checked", not.notificacionconfirm === "S" ? true : false);
          if (not.notificacionconfirm === "S" && !efectivo) {
            efectivo = true;
            if (!$("#condicion").hasClass("green-color")) {
              $("#condicion").addClass("green-color");
            }
            // $("#condicion").css({
            //   "background-color": "rgb(112,173,71)",
            //   "color": "white"
            // });
          }
          $("#condicion-sub").find(".testCanvasNotificationsContainer").each(function() {
            if ($(this).hasClass("ocultar")) {
              $(this).removeClass("ocultar");
              $(this).removeAttr("style");
            }
          });
          $("#condicion-sub").find(".chkErrorContainer").each(function() {
            if (not.notificacionconfirm !== "S" && $(this).hasClass("ocultar")) {
              $(this).removeClass("ocultar");
              $(this).removeAttr("style");
              $(this).find("#chckTestCanvasNotificationsError").prop( "disabled", false );
              $("#chckTestCanvasNotificationsError").prop( "disabled", false );
            }
          });
          if (not.notificaciontyperror == null && not.notificacionerrornote == null) {
            $(`#condicion-sub`).find(`.chckTestCanvasNotificationsError`).removeAttr('disabled');
            $("#idTestCanvasNotificationsTypeError, #idTestCanvasNotificationsErrorNote").removeAttr('disabled');
            console.log('entra ca ');
          }


          if (not.notificacionconfirm !== "S" && not.notificaciontyperror != null && not.notificaciontyperror != "" && not.notificacionerrornote != null && not.notificacionerrornote != "") {
            $(".typeErrorContainer").each(function() {
              if ($(this).hasClass("ocultar")) {
                $(this).removeClass("ocultar");
                $(this).removeAttr("style");
               $(this).prop( "disabled", false );
             }
            });
            $(`#condicion-sub`).find(`.chckTestCanvasNotificationsError`).prop("checked", true);
            $("#idTestCanvasNotificationsTypeError").val(not.notificaciontyperror);
            $("#idTestCanvasNotificationsErrorNote").val(not.notificacionerrornote);
          }


        } else {
          agregarTabs(nCond);
          $(`#condicion-sub${nCond} textarea, #condicion-sub${nCond} input[type="checkbox"], #condicion-sub${nCond} input[type="text"], #condicion-sub${nCond} select`).prop('disabled', true);
          $(`#idTestCanvasNotificationsDate${nCond}`).val(not.notificaciondate);
          $(`#idTestCanvasNotificationsUser${nCond}`).val(`${iser[0].duNombres} ${iser[0].duPrimerapellido}`);

          $(`#idTestCanvasNotificationsMesage${nCond}`).val(not.notificacionmesage);
          $(`#idTestCanvasNotificationsSendTo${nCond}`).val(not.notificacionsendto);
          $(`#selectNotificacionTipoReceptor${nCond}`).val(not.notificacionreceptor);
          $(`#idTestCanvasNotificationsName${nCond}`).val(not.notificacionname);
          $(`#idTestCanvasNotificationsNote${nCond}`).val(not.notificacionnote);
          $(`#idTestCanvasNotificationsComunicationChannel${nCond}`).val(not.notificacionchannel);
          $(`#idTestCanvasNotificationsConfirmsReception${nCond}`).prop("checked", not.notificacionconfirm === "S" ? true : false);
          if (not.notificacionconfirm === "S" && !efectivo) {
            efectivo = true;
            if (!$(`#condicion${nCond}`).hasClass("green-color")) {
              $(`#condicion${nCond}`).addClass("green-color");
            }
            // $(`#condicion${nCond}`).css({
            //   "background-color": "rgb(112,173,71)",
            //   "color": "white"
            // });
          } else {
            if ($(`#condicion${nCond}`).hasClass("green-color")) {
              $(`#condicion${nCond}`).removeClass("green-color");
            }
            // $(`#condicion${nCond}`).removeAttr('style');
          }
          $(`#condicion-sub${nCond}`).find(".testCanvasNotificationsContainer").each(function() {
            if ($(this).hasClass("ocultar")) {
              $(this).removeClass("ocultar");
              $(this).removeAttr("style");
            }
          });
          $(`#condicion-sub${nCond}`).find(".chkErrorContainer").each(function() {
            if (not.notificacionconfirm !== "S" && $(this).hasClass("ocultar")) {
              $(this).removeClass("ocultar");
              $(this).removeAttr("style");
              $(this).find("#chckTestCanvasNotificationsError").prop( "disabled", false );
            }
          });

          if (not.notificacionconfirm !== "S" && not.notificaciontyperror == null && not.notificacionerrornote == null) {

            $(`#condicion-sub${nCond}`).find(`.chckTestCanvasNotificationsError`).removeAttr('disabled');
            $(`#condicion-sub${nCond}`).find(`.chckTestCanvasNotificationsError`).prop("checked", false);
            $(`#idTestCanvasNotificationsTypeError${nCond}, #idTestCanvasNotificationsErrorNote${nCond}`).removeAttr('disabled');
          }

          if (not.notificacionconfirm !== "S" && not.notificaciontyperror != null && not.notificacionerrornote != null) {
            $(`#condicion-sub${nCond}`).find(".typeErrorContainer").each(function() {
              if ($(this).hasClass("ocultar")) {
                $(this).removeClass("ocultar");
                $(this).removeAttr("style");
              }
            });
            $(`#condicion-sub${nCond}`).find(`.chckTestCanvasNotificationsError`).prop("checked", true);
            $(`#idTestCanvasNotificationsTypeError${nCond}`).val(not.notificaciontyperror);
            $(`#idTestCanvasNotificationsErrorNote${nCond}`).val(not.notificacionerrornote);
          }

        }
      })
    }


    // $("#idTestCanvasNotificationsMesage").val(datos.dftn_MENSAJENOTIFICACION);
    // $("#idTestCanvasNotificationsSendTo").val(datos.dftn_EMAILENVIO);
    // $("#idTestCanvasNotificationsComunicationChannel").val(datos.dftn_IDVIANOTIFICACION);
    // // $("#idTestCanvasNotificationsCommunicationAttempts").val(datos.dftn_NUMEROINTENTOS);
    // $("#idTestCanvasNotificationsNote").val(datos.dftn_NOTA);

    //  $("#selectNotificacion").val(datos.);
    //  $("#idTestCanvasNotificationsName").val(datos.);
    //  $("#idTestCanvasNotificationsConfirmsReception").val(datos.);
  }



}


function limpiarTabsNotificacion() {
  let count = 1;
  $("#myTabNotify").each(function() {
    count++;
    $("#presentacion" + count).remove();
    $("#condicion-sub" + count).remove();
  });
  $(".contenedor-copia").remove();
  $(`#condicion-sub textarea, #condicion-sub input[type="checkbox"], #condicion-sub input[type="text"], #condicion-sub select`).removeAttr('disabled');
  $(`#idTestCanvasNotificationsDate, #idTestCanvasNotificationsUser`).prop('disabled', true);
  $("#condicion-sub").addClass("active show");
  $("#condicion").addClass("active");
  $("#agregar-tab").attr("onclick", "agregarTabs(2)");

  $("#idTestCanvasNotificationsabr").val("");
  $("#idTestCanvasNotificationsDate").val("");
  $("#idTestCanvasNotificationsUser").val("");

  $("#idTestCanvasNotificationsMesage").val("");
  $("#idTestCanvasNotificationsSendTo").val("");
  $("#selectNotificacionTipoReceptor").val("");
  $("#idTestCanvasNotificationsName").val("");
  $("#idTestCanvasNotificationsNote").val("");
  $("#idTestCanvasNotificationsComunicationChannel").val("");
  $("#idTestCanvasNotificationsConfirmsReception").prop("checked", false);
  $("#idTestCanvasNotificationsTypeError").val("");
  $("#idTestCanvasNotificationsErrorNote").val("");
  $("#chckTestCanvasNotificationsError").prop("checked", false);
  $(".testCanvasNotificationsContainer").each(function() {
    if (!$(this).hasClass("ocultar")) {
      $(this).addClass("ocultar");
      $(this).css("display", "none");
    }
  });

  $(".chkErrorContainer").each(function() {
    if (!$(this).hasClass("ocultar")) {
      $(this).addClass("ocultar");
      $(this).css("display", "none");
    }
  });

  $(".typeErrorContainer").each(function() {
    if (!$(this).hasClass("ocultar")) {
      $(this).addClass("ocultar");
      $(this).css("display", "none");
    }
  });

  $(`#condicion`).removeAttr('style');
  $(`#condicion`).removeClass("green-color");

  $("#nOrden").val("");
  $("#examenId").val("");
  $("#testId").val("");
  $("#idFichaTestNotificacion").val("");

}


// function saveNotification(e) {

//   let notificacion = getNotificationModalData();
//   const nOrden = notificacion.nOrden;
//   const examenId = notificacion.examenId;
//   const testId = notificacion.testId;

//   let url = gCte.getctx.concat(`/api/orden/${nOrden}/examen/${examenId}/test/${testId}/notificacion/add`);

//   try {
//     $.ajax({
//       url: url,
//       data: JSON.stringify(notificacion),
//       headers: {
//         'Content-Type': 'application/json'
//       },
//       method: 'post',
//       dataType: 'json',
//       success: function(data) {
//         if (data.status !== 200) {
//           handlerMessageError(data.message);
//         } else {
//           handlerMessageOk('Se autorizaron examens. ');
//         }
//       },
//       error: function(jqxhr, textStatus, error) {
//         let err = 'Status:' + jqxhr.status + ' ' + textStatus + ", " + error;
//         handlerMessageError(err);
//         console.log("Request Failed: " + err);
//       }
//     });
//   } catch (e) {
//     handlerMessageError(e);
//   }


// }

function verificarDatos() {
  var activeTabPane = $('.tab-pane.show');
    var input = activeTabPane.find('.classTestCanvasNotificationsName');
    var select = activeTabPane.find('.classNotificacionTipoReceptor');
    var hasData = (input.val() !== '' && select.val() !== '' && !input.prop('disabled') && !select.prop('disabled'));
    var saveButton = $('#testNotificationSaveButton');
    saveButton.prop('disabled', !hasData);
}

function verificarMail() {
  var activeTabPane = $('.tab-pane.show');
    var input = activeTabPane.find('.classTestCanvasNotificationsSendTo');
    var hasData = (input.val() !== ''  && !input.prop('disabled'));
    var saveButton = activeTabPane.find('.testSendMailButton');
    saveButton.prop('disabled', !hasData);
}

$('.tab-pane.show').find('.classTestCanvasNotificationsName').on('blur', function() {
  verificarDatos()
});

$('.tab-pane.show').find('.classNotificacionTipoReceptor').on('change', function() {
  verificarDatos()
});


$('.tab-notificacion').on('shown.bs.tab', function () {
  verificarDatos()
});

$('.tab-pane.show').find('.testSendMailButton').prop('disabled',true)
$('.tab-pane.show').find('.classTestCanvasNotificationsSendTo').on('blur', function() {
  verificarMail()
});
