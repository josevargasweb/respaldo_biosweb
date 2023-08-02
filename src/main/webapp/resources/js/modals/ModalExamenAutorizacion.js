$(document).ready(function() {
  $("#idTestCanvasNotificationsPhysicianEmail").dblclick(setMailDest);
  $("#examModalAuthOkButton").click(autorizar);
  $("#examModalAuthUndoButton").click(desautorizar);
  $("#examModalAuthExitButton").click(salir);
})

function fillModalExamenesAutorizacion(examenes,nombre) {

  $("#me_examenes").empty();
  let nOrden = -1;

  examenes.sort((a, b) => {
    if (quitarTildes(a.ce_ABREVIADO) < quitarTildes(b.ce_ABREVIADO)) return -1;
    if (quitarTildes(a.ce_ABREVIADO) > quitarTildes(b.ce_ABREVIADO)) return 1;
    return 0;
  });
  
  examenes.forEach((examen) => {
	
    let codigo = examen.ce_CODIGOEXAMEN;
    let descripcion = examen.ce_ABREVIADO;
//    let estado = examen.dfe_CODIGOESTADOEXAMEN;
    let estado = examen.cee_DESCRIPCIONESTADOEXAMEN
    let idExamen = examen.dfe_IDEXAMEN;
    nOrden = examen.df_NORDEN;
    
     const color = estadoExamenesColorMap[estado];
    let htmlRow = `<tr data-id-examen=${idExamen}>
                      <td style="width:${t1*3}px">${codigo}</td>
                      <td style="width:${t1*14}px">${largoTexto2(descripcion != null ? descripcion : "",20)}</td>
                      <td style="width:${t1*9}px"> <span class="label label-lg font-weight-bold label-inline estadoBadge-${color}">${estado}</span></td>
                      <td style="width:${t1*3}px"><input id='chk' type="checkbox" checked/></td>
                    </tr>`;
    $("#me_examenes").append(htmlRow);
  }); 
  $("#nOrden").val(nOrden);
  $("#autorPaciente").text("Paciente:"+nombre);
  $("#selectAllExam").prop('checked',true);
  $("#AutorizacionExamenesModal").removeClass("text-danger");
  $("#AutorizacionExamenesModal").modal("show");
  // Permiso de quitar autorización añadido por Marco Caracciolo 05/12/2022
  if ($("#quitaAutorizacion").length && $("#quitaAutorizacion").val() == "S"){
      $("#examModalAuthUndoButton").prop("disabled", false);
  } else {
      $("#examModalAuthUndoButton").prop("disabled", true);
  }
  //
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
      examen.dfe_IDEXAMEN = filas[i].dataset.idExamen;
      examen.dfe_NORDEN = nOrden;
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
  doActionExamenes('AUTORIZAR', examenes);
  if (examenes.length > 0) {
    loadInfoExamenesOrden(examenes[0].dfe_NORDEN);
  }
};

function desAutorizarLstExamenes(examenes) {

  $("#desautorizarResultadosModal").modal();
  let contador = 0;
  $("#desautorizarResultadosModal .btn-si").click(function(){
    $("#desautorizarResultadosModal").modal('hide');  
  if(contador == 0){
  	  doActionExamenes('DESAUTORIZAR', examenes)
  	  contador ++;
   }
    if (examenes.length > 0) {
      loadInfoExamenesOrden(examenes[0].dfe_NORDEN);
    }
});

$("#desautorizarResultadosModal .btn-no").click(function(){
  $("#desautorizarResultadosModal").modal('hide')  
  });
};

function doActionExamenes(actioncode, examenes) {

  try {
    $.ajax({
      url: gCte.getctx + "/api/examen/list/action/" + actioncode,
      data: JSON.stringify(examenes),
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'post',
      dataType: 'json',
      success: function(data) {
        if (data.status !== 200) {
          if(data.message === "No se pudo ejecutar acción AUTORIZAR"){
            handlerMessageError("Test aún pendientes de firmar");
            selectedModalData();
          }else{
            handlerMessageError(data.message);
          }
        } else {
          if (examenes.length > 0) {
            loadInfoExamenesOrden(examenes[0].dfe_NORDEN);
          }
          handlerMessageOk('Se autorizaron examens. ');
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
};

$("#selectAllExam").on( "click", function(e) {
  if ($(this).is(":checked")){
      $('#me_examenes').find(':checkbox').attr('checked', true);
  } else {
      $('#me_examenes').find(':checkbox').attr('checked', false);
  }
});
