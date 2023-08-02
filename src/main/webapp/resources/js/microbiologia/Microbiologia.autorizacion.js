$(document).ready(function() {
  // $("#idTestCanvasNotificationsPhysicianEmail").dblclick(setMailDest);
  $("#examModalAuthOkButton").click(autorizar);
  $("#examModalAuthUndoButton").click(desautorizar);
  $("#examModalAuthExitButton").click(salir);
})

function fillModalExamenesAutorizacion(examenes,nombre) {

  $("#me_examenes").empty();
  let nOrden = -1;
  examenes.forEach((examen) => {
    let codigo = examen.id;
    let descripcion = examen.exam;
    let estado = examen.processstatus
    let idExamen = examen.id;
    nOrden = examen.orderid;
    let htmlRow = `<tr data-id-examen=${idExamen}>
                    <td>${codigo}</td>
                    <td>${descripcion}</td>
                    <td>${estado}</td>
                    <td><input id='chk' type="checkbox"/> </td>
                  </tr>`;
    $("#me_examenes").append(htmlRow);
  });
  $("#nOrden").val(nOrden);
  $("#autorPaciente").text("Paciente:"+nombre);
  $("#selectAllExam").prop('checked', false);
  $("#AutorizacionExamenesModal").removeClass("text-danger");
  $("#AutorizacionExamenesModal").modal("show");
}
function autorizar() {

  let examenes = getModalData();
  if (examenes.length === 0) {
    handlerMessageWarning('Seleccionar al menos un examen');
    return;
  }

  autorizarLstExamenes(examenes);
    $("#AutorizacionExamenesModal").modal("hide");
}

function desautorizar() {
  let examenes = getModalData();
  if (examenes.length === 0) {
    handlerMessageWarning('Seleccionar al menos un examen');
    return;
  }
  desAutorizarLstExamenes(examenes);
  $("#AutorizacionExamenesModal").modal("hide");
}

function salir(){
    $("#AutorizacionExamenesModal").modal("hide");
}

function getModalData() {

  let filas = document.querySelectorAll('tr[data-id-examen]');
  console.log(filas);
  let n = filas.length;
  let examenesId = new Array();
  let nOrden = $("#nOrden").val();
  for (let i = 0; i < n; i++) {
    if (filas[i].getElementsByTagName('input')[0].checked === true) {
      let examen = new Object();
      examen.examid = filas[i].dataset.idExamen;
      examen.orderid = nOrden;
      examenesId.push(examen);
    }
  }
  return examenesId;
}

function selectedModalData(){
  let filas = document.querySelectorAll('tr[data-id-examen]');
  let n = filas.length;
  for (let i = 0; i < n; i++) {
    if (filas[i].getElementsByTagName('input')[0].checked === true) {
      if(filas[i].getElementsByTagName('td')[2].innerHTML !== "F"){
        filas[i].classList.add("text-danger");
      }
    }
  }
}

function autorizarLstExamenes(examenes) {
  if (examenes.length > 0) {
    doActionExamenes('AUTORIZAR', examenes);
    // loadInfoExamenesOrden(examenes[0].orderId);
  }
};

function desAutorizarLstExamenes(examenes) {
  if (examenes.length > 0) {
    doActionExamenes('DESAUTORIZAR', examenes)
    // loadInfoExamenesOrden(examenes[0].orderId);
  }
//   $("#desautorizarResultadosModal").modal();
//   $("#desautorizarResultadosModal .btn-si").click(function(){
//     $("#desautorizarResultadosModal").modal('hide');
 
// });

// $("#desautorizarResultadosModal .btn-no").click(function(){
//   $("#desautorizarResultadosModal").modal('hide')
//   });
};

function doActionExamenes(actioncode, examenes) {

  try {
    console.log('llega aca?',examenes);
   

    $.ajax({
      url: `${gCte.getctx}/api/microbiologia/examen/action/list/${actioncode}`,
      method: 'PUT',
      headers: {
        "Content-Type": "application/json"
      },
      data: JSON.stringify(examenes),
      success: function(datos) { 
        if(datos.status !== undefined && datos.status == 200){
          datos.dato.forEach(element => {
              element.dfeCodigoestadoexamen; //dato a modificar
              element.iddatosFichasExamenes.dfeIdexamen; //tr de donde tomar los datos

              let table = $("#examenesOrdenesDatatable").DataTable();
              let row = $(`#examenesOrdenesDatatable`).find(`tr#${element.iddatosFichasExamenes.dfeIdexamen}`);
              let tabladata = table.row(row).data();
              let tipoEstado;
              if (element.dfeCodigoestadoexamen == "AUTORIZADO"){
                tipoEstado = "A";
              }else if(element.dfeCodigoestadoexamen == "INGRESADO") {
                tipoEstado = "I";
              }else if(element.dfeCodigoestadoexamen == "PENDIENTE") {
                tipoEstado = "P";
              }else if(element.dfeCodigoestadoexamen == "BLOQUEADO") {
                tipoEstado = "B";
              }else if(element.dfeCodigoestadoexamen == "EN PROCESO") {
                tipoEstado = "E";
              }else if(element.dfeCodigoestadoexamen == "FIRMADO") {
                tipoEstado = "F";
              }
              tabladata.processstatus =  element.dfeCodigoestadoexamen;
              tabladata.estado =  tipoEstado;
              table.row(row).data(tabladata).draw();
          });

          if(actioncode == "AUTORIZAR"){
            handlerMessageOk('Se autorizaron examens. ');
          }else{
            handlerMessageOk('Se desautorizaron examens. ');
          }
        }
      }
    });


    // examenes.forEach(element => {
    //   let table = $("#examenesOrdenesDatatable").DataTable();
    //   let row = $(`#examenesOrdenesDatatable`).find(`tr#${element.id}`);
    //   let tabladata = table.row(row).data();
    //   $.ajax({
    //     method: 'PUT',
    //     url: `http://localhost:3002/exams/${tabladata.id}`,
    //     data: {
    //       cultivo:tabladata.cultivo,
    //       exam : tabladata.exam,
    //       estado : tabladata.estado,
    //       processStatus : texto,
    //       orderId : tabladata.orderId,
    //       seedingDate : tabladata.seedingDate,
    //       reseedingDate : tabladata.reseedingDate,
    //       urgency : tabladata.urgency,
    //       id : tabladata.id,
    //       sample : tabladata.sample,
    //       bodyPart : tabladata.bodyPart,
    //       status : tabladata.status,
    //     },
    //     success: function(datos) {

    //       let ordenData = {
    //         cultivo:datos.cultivo,
    //         exam : datos.exam,
    //         estado : datos.estado,
    //         processStatus : datos.processStatus,
    //         orderId : datos.orderId,
    //         seedingDate : datos.seedingDate,
    //         reseedingDate : datos.reseedingDate,
    //         urgency : datos.urgency,
    //         id : datos.id,
    //         sample : datos.sample,
    //         bodyPart : datos.bodyPart,
    //         status : datos.status,
    //       }

    //         table.row(row).data(ordenData).draw();
    //     }
    //   });
      
    // });
  
 
  } catch (e) {
    handlerMessageError(e);
  }
};

$("#selectAllExam").on( "click", function(e) {
  if ($(this).is(":checked")){
      $('#me_examenes').find(':checkbox').attr('checked', true);
  } else {
      $('#me_examenes').find(':checkbox').attr('checked', false);
  }
});
