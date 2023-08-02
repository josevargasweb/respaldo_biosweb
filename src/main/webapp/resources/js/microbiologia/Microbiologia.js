//use 'strict'
// import {YYYY_MM_DD2DD_MM_YY} from '../common/ModuloFecha.js';

var GlobalAGOptions;
var GlobalDBValues;
var GlobalAntibiogram;
var GlobalAntibiogramOptions;
var GlobalCurrentTestId;  
var GlobalCurrentExamId;
var GlobalCurrentOrderId;
var GlobalCurrentAntibioticList;
var GlobalTestMOList; 
var GlobalTestParameters
var GlobalTest;
var GlobalId;
var GlobalFlag;

$(document).ready(function () {
  $("#OrderCanvas").hide();
  $("#ExamCanvas").hide();
  $("#TestCanvas").hide();
  $("#AntibiogramCanvas").hide();
  centerViewTo("#ListsCanvas");
  $.getJSON("Microbiologia/api/getSearchOptions")
    .done(function (searchOptions) {
      showSearchOptions(searchOptions);
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
})

$("#searchButton").click(function () {
  $.getJSON("Microbiologia/api/getOrderList", orderSearchParameters())
    .done(function (orders) {
      showOrderList(orders);
      centerViewTo("#ListsCanvas");
      $("#OrdersListToggle").click();
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
})

function showSearchOptions(searchOptions) {
  for (let o in searchOptions.documentType) {
    let htmlOption = '<option value="' + o + '" >' + searchOptions.documentType[o] + '</option>';
    $("#idSearchDocumentType").append(htmlOption);
  }
  for (let o in searchOptions.section) {
    let htmlOption = '<option value="' + o + '" >' + searchOptions.section[o] + '</option>';
    $("#idSearchSection").append(htmlOption);
  }
  for (let o in searchOptions.exam) {
    let htmlOption = '<option value="' + o + '" >' + searchOptions.exam[o] + '</option>';
    $("#idSearchExam").append(htmlOption);
  }
  for (let o in searchOptions.service) {
    let htmlOption = '<option value="' + o + '" >' + searchOptions.service[o] + '</option>';
    $("#idSearchService").append(htmlOption);
  }
  for (let o in searchOptions.attentionType) {
    let htmlOption = '<option value="' + o + '" >' + searchOptions.attentionType[o] + '</option>';
    $("#idSearchAtentionType").append(htmlOption);
  }
}

function orderSearchParameters() {
  let result = {
    startId: $("#idSearchOrderStart").val(),
    endId: $("#idSearchOrderEnd").val(),
    startDate: $("#idSearchStartDate").val(),
    endDate: $("#idSearchEndDate").val(),
    names: $("#idSearchName").val(),
    surname: $("#idSearchSurname").val(),
    section: $("#idSearchSection").val(),
    exam: $("#idSearchExam").val(),
    documentType: $("#idSearchDocumentType").val(),
    documentId: $("#idSearchDocumentNumber").val(),
    serviceId: $("#idSearchService").val(),
    atentionType: $("#idSearchAtentionType").val(),
    statusPending: $("#idSearchPending").prop("checked").toString(),
    statusForSignature: $("#idSearchForSignature").prop("checked").toString(),
  }
  return result;
}

function orderDetail(id) {
  localSetCurrentOrderId(id);
  $.getJSON("Microbiologia/api/getOrderData", { orderId: id })
    .done(function (order) {
      showOrderData(order);
      showOrderEvents(order.events);
      $("#OrderCanvas").show();
      $("#ExamCanvas").hide();
      $("#TestCanvas").hide();
      $("#AntibiogramCanvas").hide();
      showExamList(order.exams);
      $("#btnTasksOpen").attr("href", "javascript:void(0)");
      $("#ExamsListToggle").click();
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function examDetail(id, orderId) {
  localSetCurrentExamId(id);
  $.getJSON("Microbiologia/api/getExamData", { orderId: orderId, examId: id })
    .done(function (exam) {
      showExam(exam);
      $("#btnTasksOpen").attr("href", "Microbiologia/Tareas?orderId=" + orderId + "&examId=" + id);
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function showExam(exam) {
  showExamObservations(exam.observations);
  showExamEvents(exam.events);
  showExamNotes(exam.notes);
  showExamOptionalTests(exam.optionalTests);
  $("#OrderCanvas").hide();
  $("#ExamCanvas").show();
  $("#TestCanvas").hide();
  $("#AntibiogramCanvas").hide();
  showTestsList(exam.testsList);
  $("#TestsListToggle").click();
}

function testDetail(orderId, examId, id) {
  localSetCurrentTestId(id);
  $("#btnTasksOpenTest").attr("href", "Microbiologia/Tareas?orderId=" + orderId + "&examId=" + examId);
  $.getJSON("Microbiologia/api/getTestData", { orderId: orderId, examId: examId, testId: id })
    .done(function (test) {
      localStoreGlobalTest(test);
      localStoreGlobalId(id);
      $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function (antibiogramOptions) {
          $.getJSON("Microbiologia/api/getTestMOList", { orderId: orderId, examId: examId, testId: id })
          .done(function (testMOList) {
            $("#btnTestCanvasAntibiogramClearIsolatedNumber").hide();
            localStoreTestParameters(orderId, examId, id, test.data.patientId);
            localStoreTestMOList(testMOList);
            localStoreAntibiogramOptions(antibiogramOptions)
            localStoreAGOptions({});
            var AGValuesForDB = JSON.parse(JSON.stringify(test.antibiogram));
            localStoreDBAntibiogram(AGValuesForDB);
            localStoreAntibiogram(test.antibiogram);
            localStoreGlobalFlag(false);
            addAGOptionsToData();
          })

          .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
          });

        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function showTest(test, enableABG) {
  showTestData(test.data);
  showTestEvents(test.events);
  showTestNotification(test.notification);
  showTestActions(test.actions);
  showTestLabels(test.labels);
  var testAG = test.antibiogram;
  localStoreAntibiogram(test.antibiogram);
  localSetCurrentAntibioticListIndex(1);
  showAntibiogram();

  $("#OrderCanvas").hide();
  $("#ExamCanvas").hide();
  $("#TestCanvas").show();
  $("#AntibiogramCanvas").hide();

  centerViewTo("#TestCanvas");

  $("#TestCanvasAntibiogramsToggle").attr("href", enableABG ? "#TestCanvasAntibiograms" : "javascript:void(0)");
  $("#TestCanvasAntibiogramsToggle").attr("data-toggle", enableABG ? "tab" : "");
  $("#TestCanvasDataToggle").click();
}

function fillTemplate(template, data) {
  const pattern = /{\s*(\w+?)\s*}/g; 
  return template.replace(pattern, (_, token) => data[token] || '');
}

function showExamObservations(observation) {
	//cambios en formato fecha, captura resultado datos examenes por cristian 13-01-2022
  $("#idExamCanvasObservationsPatientName").text(observation.patient.name);
  $("#idExamCanvasObservationsPatientObservation").text(observation.patient.observation);

  $("#idExamCanvasObservationsOrderId").text(observation.order.id);
  $("#idExamCanvasObservationsOrderObservation").text(observation.order.observation);
  $("#idExamCanvasObservationsOrderExam").text(observation.order.exam);
  $("#idExamCanvasObservationsOrderUser").text(observation.order.user);
  //$("#idExamCanvasObservationsOrderDate").text(YYYY_MM_DD2DD_MM_YY(observation.order.date,'-','/'));
$("#idExamCanvasObservationsOrderDate").text( observation.order.date == "" ? "" : convertirDateDD_MM_YYYY_HH_mm_SS(new Date(observation.order.date), '-', ':'));
  $("#idExamCanvasObservationsSamplingObservation").text(observation.sampling.observation);
  $("#idExamCanvasObservationsSamplingStatus").text(observation.sampling.status);
  $("#idExamCanvasObservationsSamplingUser").text(observation.sampling.user);

 // $("#idExamCanvasObservationsSamplingDate").text(YYYY_MM_DD2DD_MM_YY(observation.sampling.date,'-','/'));
  $("#idExamCanvasObservationsSamplingDate").text(observation.sampling.date == "" ? "" : convertirDateDD_MM_YYYY_HH_mm_SS(new Date(observation.sampling.date), '-', ':'));
  $("#idExamCanvasObservationsReceptionObservation").text(observation.reception.observation);
  $("#idExamCanvasObservationsReceptionStatus").text(observation.reception.status);
  $("#idExamCanvasObservationsReceptionUser").text(observation.reception.user);
  //$("#idExamCanvasObservationsReceptionDate").text(YYYY_MM_DD2DD_MM_YY(observation.reception.date,'-','/'));
  $("#idExamCanvasObservationsReceptionDate").text( observation.reception.date == "" ? "" :convertirDateDD_MM_YYYY_HH_mm_SS(new Date(observation.reception.date), '-', ':'));
}

function centerViewTo(element) {
  $('html, body').animate({
    // scrollTop: $(element).offset().top
  }, 700);
}

function showOrderList(orderList) {
  $("#ordersListData").empty();
  orderList.forEach(function (order) {
    $("#ordersListData").append(fillTemplate(orderHtmlTemplate, order));
  });
}

//cambiado por cristian 09-02 ***   revisar que no modifique otrp modulo
function showOrderEvents(events) {
  $("#idOrderCanvasEventsTable").empty();
  let html = "";
  
  $("#idOrdenEvento").val(events[0].lef_NORDEN)
  $("#idTestEvento").val(events[0].ct_IDTEST)
 events.forEach(function (dato) {	
	//*****************************  agregado desde aqui */
	if(dato.lef_IDEVENTO == 1){
		let mensaje = "";
		if(dato.ce_ABREVIADO == null && dato.lef_CODIGOBARRA == null){
			console.log(dato.lef_NOMBRETABLA)
			if(dato.lef_NOMBRETABLA == "DATOS_FICHASANTECEDENTES"){
				mensaje = "  Crea antecedente <b>"+ dato.lef_VALORNUEVO + "</b> en Orden  N° <b>" + dato.lef_NORDEN +"</b>"
			}else{
				mensaje = "  Crea Orden N° <b>" + dato.lef_NORDEN +"</b>"
			}
			
		}else{
			if(dato.ce_ABREVIADO == null){
				mensaje = " Crea Muestra : <b>" + dato.lef_CODIGOBARRA +"</b>";
			}else{
				mensaje = " Crea examen : <b>" + dato.ce_ABREVIADO +"</b>";
			}
		}
		
		
		html += "<tr><td>" + (new Date(dato.lef_FECHAREGISTRO).toLocaleString())
		 + "</td><td>" + (dato.du_NOMBRES == null ? "" : dato.du_NOMBRES) + " " + (dato.du_PRIMERAPELLIDO == null ? "" : dato.du_PRIMERAPELLIDO ) +  " "  +	(dato.du_SEGUNDOAPELLIDO == null ? "" : dato.du_SEGUNDOAPELLIDO )
		  + "</td><td>" + (dato.lef_IPESTACION == null ? "" : dato.lef_IPESTACION  ) +" </td><td>"+  dato.nombrepaciente +" </td><td>"+ dato.lef_NORDEN  +" </td><td>"+ 
		   (dato.ce_ABREVIADO == null ? "" : dato.ce_ABREVIADO ) + " </td><td>"+ ( dato.ct_ABREVIADO == null ? "" : dato.ct_ABREVIADO  ) + "  </td><td>" + 
		    (dato.lef_CODIGOBARRA == null ? "" : dato.lef_CODIGOBARRA ) +" </td><td>"+ 
		    mensaje + "</td></tr>"
			
	}else{	
		let mensaje2 = 	"   </b>  de  <b> " + (dato.lef_VALORANTERIOR == null ? " - " : dato.lef_VALORANTERIOR  ) 
		    + " </b> a valor nuevo <b> "+ (dato.lef_VALORNUEVO == null ? " - " : dato.lef_VALORNUEVO )+ " </b>"
		let mensaje =" Se modificó el campo  <b>  "+dato.lef_NOMBRECAMPO
		if(dato.lef_NOMBRECAMPO === "DFD_IDTIPODOCUMENTOANEXO"){
			mensaje =" Se adjunta documento:  <b> "+(dato.lef_VALORNUEVO == null ? " - " : dato.lef_VALORNUEVO )
			mensaje2 =""
		}
		
		
		html += "<tr><td>" + (new Date(dato.lef_FECHAREGISTRO).toLocaleString())
		 + "</td><td>" + (dato.du_NOMBRES == null ? "" : dato.du_NOMBRES) + " " + (dato.du_PRIMERAPELLIDO == null ? "" : dato.du_PRIMERAPELLIDO ) +  " "  +	(dato.du_SEGUNDOAPELLIDO == null ? "" : dato.du_SEGUNDOAPELLIDO )
		  + "</td><td>"+   (dato.lef_IPESTACION == null ? "" : dato.lef_IPESTACION  ) +" </td><td>"+  dato.nombrepaciente + " </td><td>" +  dato.lef_NORDEN  + "  </td><td>" +
		   (dato.ce_ABREVIADO == null ? "" : dato.ce_ABREVIADO ) + " </td><td> "+ ( dato.ct_ABREVIADO == null ? "" : dato.ct_ABREVIADO  ) + "  </td><td>" + 
		    (dato.lef_CODIGOBARRA == null ? "" : dato.lef_CODIGOBARRA ) +" </td><td> "+ 
		   mensaje +  mensaje2 + "</td></tr>"			
	}
			
//***********************  hasta aca *********************** */	
	
   // $("#idOrderCanvasEventsTable").append(fillTemplate(eventsRowHtmlTemplate, event));
  });
		$("#idOrderCanvasEventsTable").html(html) 
		
}

function showOrderData(order) {
	console.log("entre a showOrderData")
	
  $("#idOrderCanvasDataName").text(order.patient.name);
  // agregado por cristian 16-01-2023
  if(order.patient.tipoDocumento == "1"){
	$("#idOrderCanvasDataDocumento").text(cambiarDatoRut(order.patient.nroDocumento)); 
	}else{
		 $("#idOrderCanvasDataDocumento").text(order.patient.nroDocumento);
	}
  $("#idOrderCanvasDataHost").text(order.order.host);
  $("#idOrderCanvasDataGender").text(order.patient.gender);
  $("#idOrderCanvasDataAge").text(normalizarEdad(order.patient.age));
  $("#idOrderCanvasDataDateOfBirth").text(YYYY_MM_DD2DD_MM_YY(order.patient.dateOfBirth,'-','/')); 
  $("#idOrderCanvasDataPhoneNumber").text(order.patient.phoneNumber);
  $("#idOrderCanvasDataAtentionType").text(order.patient.atentionType);
  $("#idOrderCanvasDataLocalization").val(order.patient.localization);
  $("#idOrderCanvasDataPatientObservation").text(order.patient.observation);
  $("#idOrderCanvasDataPathologies").text(order.patient.pathologies);

  $("#idOrderCanvasDataOrderId").text(order.order.id);
 // $("#idOrderCanvasDataDate").text(YYYY_MM_DD2DD_MM_YY(order.order.date,'-','/'));
 $("#idOrderCanvasDataDate").text( order.order.date == "" ? "" : convertirDateDD_MM_YYYY_HH_mm_SS(new Date(order.order.date), '-', ':'));
  $("#idOrderCanvasDataContract").text(order.order.contract);
  $("#idOrderCanvasDataPhysician").text(order.order.physician);
  $("#idOrderCanvasDataDiagnostic").text(order.order.diagnostic);
  $("#idOrderCanvasDataOrderObservation").text(order.order.observation);
  $("#idOrderCanvasDataService").text(order.order.service);
  $("#idOrderCanvasDataOrigin").text(order.order.procedencia);

  let orders = order.previousOrders;
  let ordersList = " "
  if(order.previousOrders != null){
	  for (let i=0;i<gCte.nroMaxOrdenesHistoricas && i < 4 ;i++){
		
		if(orders[i] != undefined){
	   	 ordersList =  ordersList.concat(orders[i]).concat(' , ');
	    }
	  }
	  if(orders[4] != undefined){
	 	  ordersList =  ordersList.concat(orders[4]);
	   }
 } 
  $("#idOrderCanvasDataPreviousOrders").text(ordersList);
}

function showExamList(examList) {
  $("#examsListData").empty();
  examList.forEach(function (exam) {
    $("#examsListData").append(fillTemplate(examHtmlTemplate, exam));
  });
  $('label[data-toggle="tooltip"]').each(function () {
    let date_str = $(this).text();
    if (date_str != "-") {
      let date_diff = Date.parse(Date()) - Date.parse(date_str);
      $(this).prop("title", "" + parseInt((date_diff / (1000 * 3600 * 24))) + " dias y " +
        parseInt((date_diff % (1000 * 3600 * 24)) / (3600 * 1000)) + " horas");
      $(this).tooltip();
    }
  })
}

function showExamEvents(events) {
  $("#idExamCanvasEventsTable").empty();
  events.forEach(function (event) {
    $("#idExamCanvasEventsTable").append(fillTemplate(eventsRowHtmlTemplate, event));
  })
}

const miObjeto = {};

function showExamNotes(notes) {
  $("#examModalNotesButton").prop('disabled',true)
  //rellena el objeto
  textoAnterior(miObjeto, notes.internal, notes.exam, notes.footer);
  $("#idExamCanvasNotesInternalNote").val(notes.internal);
  $("#idExamCanvasNotesExamNote").val(notes.exam);
  $("#idExamCanvasNotesFooterNote").val(notes.footer);
}


function showExamOptionalTests(tests) {
  $("#optionalTestsContainer").empty();
  tests.forEach(function (test) {
    $("#optionalTestsContainer").append(fillTemplate(optionalTestHtmlTemplate, test));
    $("#optionalTest-" + test.id).prop("checked", test.selected);
    optionalTestDisplay(test.id);
  })

}

function showTestsList(testsList) {
  $("#testsListData").empty();
  testsList.forEach(function (test) {
    $("#testsListData").append(fillTemplate(testHtmlTemplate, test));
    showTestsSetResult(test);
    showTestsSetMicrobiologyStatus(test);
  });
}

function showTestsSetResult(test) {
  if (test.resultType == "select") {
    $("#testResultDiv-" + test.id).append(`<select onchange="changeTestData(` + test.id + `)" id="testResult-` + test.id + `" class="form-control"></select>`);
    test.resultOptions.forEach(function (option) {
      $("#testResult-" + test.id).append(`<option value="` + option + `" >` + option + `</option>`)
    });
  } else {
    $("#testResultDiv-" + test.id).append(`<input class="form-control" id="testResult-` + test.id + `" type="text"/>`);
  }
  $("#testResult-" + test.id).val(test.result);
}

function showTestsSetMicrobiologyStatus(test) {
  $("#testMicrobiologyStatusDiv-" + test.id).append(`<select onchange="changeTestData(` + test.id + `)" id="testMicrobiologyStatus-` + test.id + `" class="form-control"></select>`);
  test.microbiologyStatusOptions.forEach(function (option) {
    $("#testMicrobiologyStatus-" + test.id).append(`<option value="` + option + `" >` + option + `</option>`)
  });
  $("#testMicrobiologyStatus-" + test.id).val(test.microbiologyStatus);
}

function changeTestData(id) {
  localSetCurrentTestId(id);
  $.getJSON("Microbiologia/api/getTestData", { orderId: localReadCurrentOrderId(), examId: localReadCurrentExamId(), testId: id })
    .done(function (test) {
      localStoreGlobalTest(test);
      localStoreGlobalId(id);
      $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function (antibiogramOptions) {
          $.getJSON("Microbiologia/api/getTestMOList", { orderId: localReadCurrentOrderId(), examId: localReadCurrentExamId(), testId: id })
          .done(function (testMOList) {
            localStoreTestParameters(localReadCurrentOrderId(), localReadCurrentExamId(), id, test.data.patientId);
            localStoreTestMOList(testMOList);
            localStoreAntibiogramOptions(antibiogramOptions)
            localStoreAGOptions({});
            var AGValuesForDB = JSON.parse(JSON.stringify(test.antibiogram));
            localStoreAntibiogram(test.antibiogram);
            localStoreDBAntibiogram(AGValuesForDB);
            localStoreGlobalFlag(true);
            addAGOptionsToData();
            saveTest();
          })

          .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
          });

        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function showTestData(data) {
  $("#idTestCanvasDataName").empty(data.name);
  $("#idTestCanvasDataName").append(data.name);

  $("#idTestCanvasDataExam").text(data.exam);
  $("#idTestCanvasDataSample").text(data.sample);
  $("#idTestCanvasDataResultType").text(data.resultType);
  $("#idTestCanvasDataCode").text(data.code);
  $("#idTestCanvasDataPrefix").text(data.prefix);
  $("#idTestCanvasDataResult").text(data.result);
  $("#idTestCanvasDataCriticalValue").text(data.criticalValue);
  $("#idTestCanvasDataBodyPart").text(data.bodyPart);
  $("#idTestCanvasDataStatus").text(data.status);
}

function showTestEvents(events) {
  $("#idTestCanvasEventsTable").empty();
  events.forEach(function (event) {
    $("#idTestCanvasEventsTable").append(fillTemplate(eventsRowHtmlTemplate, event));
  })
}

function getTestNotification(testId) {
  let notification = {
    result: "Positivo", criticalResult: "Hongos presentes", physician: "Rodrigo Donoso", patient: "Ignacio Abarca",
    patientPhone: "+56 995083061", physicianPhone: "+56 987654321", patientEmail: "iabarca@gmail.com",
    physicianEmail: "drdonoso@teledx.com"
  }
  return notification;
}

function showTestNotification(notification) {
  $("#idTestCanvasNotificationsResult").val(notification.result);
  $("#idTestCanvasNotificationsCriticalResult").val(notification.criticalResult);
  $("#idTestCanvasNotificationsPhysician").val(notification.physician);
  $("#idTestCanvasNotificationsPatient").val(notification.patient);
  $("#idTestCanvasNotificationsPatientPhone").val(notification.patientPhone);
  $("#idTestCanvasNotificationsPhysicianPhone").val(notification.physicianPhone);
  $("#idTestCanvasNotificationsPatientEmail").val(notification.patientEmail);
  $("#idTestCanvasNotificationsPhysicianEmail").val(notification.physicianEmail);
}

function showTestActions(actions) {

  $.getJSON("Microbiologia/api/getSampleOptions")
    .done(function (samples) {
      $("#idTestCanvasActionsSample").empty();
      samples.forEach((o, i) => {
        let htmlOption = '<option value="' + o + '" >' + o + '</option>';
        $("#idTestCanvasActionsSample").append(htmlOption);
      });
      $("#idTestCanvasActionsSample").val(actions.sampleType);
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });

  $.getJSON("Microbiologia/api/getBodyPartOptions")
    .done(function (bodyParts) {
      $("#idTestCanvasActionsBodyPart").empty();
      bodyParts.forEach((o, i) => {
        let htmlOption = '<option value="' + o + '" >' + o + '</option>';
        $("#idTestCanvasActionsBodyPart").append(htmlOption);
      });
      $("#idTestCanvasActionsBodyPart").val(actions.bodyPart);
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });

  $("#idTestCanvasActionsSample").val(actions.sampleType);
  $("#idTestCanvasActionsBodyPart").val(actions.bodyPart);
  $("#idTestCanvasActionsSeedingDate").val(actions.seedingDate);
  $("#idTestCanvasActionsSeedingTime").val(actions.seedingTime);
  $("#idTestCanvasActionsReseedingDate").val(actions.reseedingDate);
  $("#idTestCanvasActionsReseedingTime").val(actions.reseedingTime);
  $("#idTestCanvasActionsResultDate").val(actions.resultDate);
  $("#idTestCanvasActionsResultTime").val(actions.resultTime);
  $("#idTestCanvasActionsAlertTime").val(actions.alertTime);
}

$("#idTestCanvasActionsSave").click(function () {
  saveTest();
});

function saveTest() {
  let data = {
    orderId: localReadCurrentOrderId(),
    examId: localReadCurrentExamId(),
    testId: localReadCurrentTestId(),
    result: $("#testResult-" + localReadCurrentTestId()).val(),
    microbiologyStatus: $("#testMicrobiologyStatus-" + localReadCurrentTestId()).val(),
    sampleType: $("#idTestCanvasActionsSample").val(),
    bodyPart: $("#idTestCanvasActionsBodyPart").val(),
    seedingDate: $("#idTestCanvasActionsSeedingDate").val() + " " + $("#idTestCanvasActionsSeedingTime").val(),
    reseedingDate: $("#idTestCanvasActionsReseedingDate").val() + " " + $("#idTestCanvasActionsReseedingTime").val(),
    resultDate: $("#idTestCanvasActionsResultDate").val() + " " + $("#idTestCanvasActionsResultTime").val(),
    alertTime: $("#idTestCanvasActionsAlertTime").val()
  }

  $.ajax({
    url: "Microbiologia/api/putTestData",
    data: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json'
    },
    method: 'put',
    dataType: 'json',
    success: function (test) {
      $.getJSON("Microbiologia/api/getExamData", { orderId: localReadCurrentOrderId(), examId: localReadCurrentExamId() })
        .done(function (exam) {
          showExam(exam);
          showTest(test);
        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    },
    error: function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });
}

$("#idTestCanvasActionsSign").click(function () {
  signTest();
});

function signTest() {
  let data = {
    orderId: localReadCurrentOrderId(),
    examId: localReadCurrentExamId(),
    testId: localReadCurrentTestId(),
  }

  $.ajax({
    url: "Microbiologia/api/putSignTest",
    data: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json'
    },
    method: 'put',
    dataType: 'json',
    success: function (test) {
      $.getJSON("Microbiologia/api/getExamData", { orderId: localReadCurrentOrderId(), examId: localReadCurrentExamId() })
        .done(function (exam) {
          showExam(exam);
          showTest(test);
        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    },
    error: function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });
}

function getTestLabels(testId) {
  let labels = {
    "2101": [{ id: "label_1", name: "Eqtiqueta 1", selected: true }, { id: "label_2", name: "Eqtiqueta 2", selected: false }, { id: "label_3", name: "Eqtiqueta 3", selected: false }],
    "2102": [{ id: "label_4", name: "Eqtiqueta 4", selected: false }, { id: "label_4", name: "Eqtiqueta 4", selected: false }]
  }
  return labels["2101"];
}

function showTestLabels(labels) {
  let select = `<select id="TestCanvasLabelsListBox" class="dual-listbox" data-search="false" multiple
                    data-available-title="Glosas disponibles"
                    data-selected-title="Glosas seleccionadas"
                    data-add="Agregar<i class='flaticon2-next'></i>"
                    data-remove="<i class='flaticon2-back'></i>Quitar">
                  </select>`;
  $("#TestCanvasLabelsListBoxDiv").empty();
  $("#TestCanvasLabelsListBoxDiv").append(select);
  dualListBosxInit("TestCanvasLabelsListBox", labels);
}

function dualListBosxInit(id, options) {
  // Dual Listbox
  var listBoxes = $("#" + id);

  listBoxes.each(function () {
    var $this = $(this);
    // get titles
    var availableTitle = ($this.attr("data-available-title") != null) ? $this.attr("data-available-title") : "Available options";
    var selectedTitle = ($this.attr("data-selected-title") != null) ? $this.attr("data-selected-title") : "Selected options";

    // get button labels
    var addLabel = ($this.attr("data-add") != null) ? $this.attr("data-add") : "Add";
    var removeLabel = ($this.attr("data-remove") != null) ? $this.attr("data-remove") : "Remove";
    var addAllLabel = ($this.attr("data-add-all") != null) ? $this.attr("data-add-all") : "Add All";
    var removeAllLabel = ($this.attr("data-remove-all") != null) ? $this.attr("data-remove-all") : "Remove All";

    options.forEach(function (option) {
      if (option.selected) {
        $("#" + id).append(`<option value="` + option.id + `" selected>` + option.name + `</option>`);
      } else {
        $("#" + id).append(`<option value="` + option.id + `">` + option.name + `</option>`);
      }
    });

    // get search option
    var search = ($this.attr("data-search") != null) ? $this.attr("data-search") : "";

    // init dual listbox
    var dualListBox = new DualListbox($this.get(0), {

      addEvent: function (value) { },
      removeEvent: function (value) { },
      availableTitle: availableTitle,
      selectedTitle: selectedTitle,
      addButtonText: addLabel,
      removeButtonText: removeLabel,
      addAllButtonText: addAllLabel,
      removeAllButtonText: removeAllLabel,
    });

    if (search == "false") {
      dualListBox.search.classList.add("dual-listbox__search--hidden");
    }
  });
};

function printLabels() {
  console.log($("#TestCanvasLabelsListBox").val());
}



function getAntibiogram() {
  let antibiogram = {
    colonyCount: "10.000 UFC",
    infectionType: "infeccion 1",
    antibioticLists: {
      1: {
        microorganism: { name: "", id: "" },
        antibiogram: { name: "", id: "" },
        antibiotics: [],
      },
      2: {
        microorganism: { name: "", id: "" },
        antibiogram: { name: "", id: "" },
        antibiotics: []
      },
      3: {
        microorganism: { name: "", id: "" },
        antibiogram: { name: "", id: "" },
        antibiotics: []
      },
      4: {
        microorganism: { name: "", id: "" },
        antibiogram: { name: "", id: "" },
        antibiotics: []
      },
    }
  }
  localStoreAntibiogram(antibiogram);
}

function deleteAntibiotic(antibioticId) {
  localStoreCurrentAntibioticList();
  let antibiogram = localReadAntibiogram();

  let index = antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics
    .map(function (antibiotic) { return antibiotic.antibiotic })
    .indexOf(antibioticId)

  if (index > -1) {
    antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics.splice(index, 1);
  }

  localStoreAntibiogram(antibiogram);
  showAntibiogramAntibioticList();
  showAddAntibioticList();
}

function deleteMethod(methodId) {
  localStoreCurrentAntibioticList();
  let antibiogram = localReadAntibiogram();

  let index = antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].methods
    .map(function (method) { return method.method.replace(/ /g, "_") })
    .indexOf(methodId)

  if (index > -1) {
    antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].methods.splice(index, 1);
  }

  localStoreAntibiogram(antibiogram);
  showAntibiogramAntibioticList();
  showAddAntibioticList();
  showAntibiogramResistanceMethodList()
  showAddResistanceMethodList();
}

function localStoreAntibiogram(antibiogram) {
  var AG = antibiogram;
  GlobalAntibiogram = AG;
}

function localReadAntibiogram() {
  return GlobalAntibiogram;
}

function localStoreDBAntibiogram(optionSelects) {
  GlobalDBValues = {};
  var newVar = optionSelects.antibioticLists;
  GlobalDBValues.antibioticLists = newVar;
}

function localReadDBAntibiogram() {
  return GlobalDBValues;
}

function localStoreAntibiogramOptions(antibiogramOptions) {
  GlobalAntibiogramOptions = antibiogramOptions;
}

function localReadAntibiogramOptions() {
  return GlobalAntibiogramOptions;
}

function localStoreAGOptions(optionSelects) {
  var newVar = JSON.parse(JSON.stringify(optionSelects));
  GlobalAGOptions = newVar;
}

function localReadAGOptions() {
  return GlobalAGOptions;
}

function localStoreTestMOList(MOList){
  GlobalTestMOList = MOList;
}

function localReadTestMOList(){
  return GlobalTestMOList;
}

function localStoreTestParameters(orderId, examId, testId, patientId){
  GlobalTestParameters = {
    orderId: orderId, 
    examId: examId, 
    testId: testId,
    patientId: patientId
  };
}

function localReadTestParameters(){
  return GlobalTestParameters;
}


function showAntibiogramAntibioticList() {
  let cimValue = '';
  let diameterValue = '';
  let antibioticList = localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics;
  let cleanAntibioticName = "";
  let abName = "";
  $("#antibiogramsAntibioticList").empty();
  antibioticList.forEach(function (antibiotic) {
    
    abName = antibiotic.antibiotic;
    
    cleanAntibioticName = abName.replace("/","-");
    antibiotic.abName = cleanAntibioticName;
    $("#antibiogramsAntibioticList").append(fillTemplate(antibiogramHtmlTemplate, antibiotic));
    setAntibioticListOptions(cleanAntibioticName);
    antibiotic.cim == null ? cimValue = '' : cimValue = antibiotic.cim.toString();
    antibiotic.diameter == null ? diameterValue = '' : diameterValue = antibiotic.diameter.toString(); 
    $("#antibioticCim-" + cleanAntibioticName).val(cimValue);
    $("#antibioticDiameter-" + cleanAntibioticName).val(diameterValue);
    $("#antibioticInterpretation-" + cleanAntibioticName).val(antibiotic.interpretation);
    $('#antibioticIncludeInReport-' + cleanAntibioticName).prop('checked', antibiotic.includeInReport);
    $("#antibioticResistantMethod-" + cleanAntibioticName).val(antibiotic.resistantMethod);
    $("#antibioticResult-" + cleanAntibioticName).val(antibiotic.result);
    $("#antibioticDiameter-" + cleanAntibioticName).change(function () {
      $.getJSON("Microbiologia/api/getAntibiogramResistance", {
        antibioticId: antibiotic.antibiotic,
        microorganismId: $("#idTestCanvasAntibiogramMicroorganism").val(),
        method: "3",
        radio: $("#antibioticDiameter-" + cleanAntibioticName).val()
      })
        .done(function (response) {
          if (response.interpretation != "")
            $("#antibioticInterpretation-" + cleanAntibioticName).val(response.interpretation);
        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    });
    $("#antibioticCim-" + cleanAntibioticName).change(function () {
      $.getJSON("Microbiologia/api/getAntibiogramResistance", {
        antibioticId: antibiotic.antibiotic,
        microorganismId: $("#idTestCanvasAntibiogramMicroorganism").val(),
        method: "2",
        cim: $("#antibioticCim-" + cleanAntibioticName).val()
      })
        .done(function (response) {
          if (response.interpretation != "")
            $("#antibioticInterpretation-" + cleanAntibioticName).val(response.interpretation);
        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    });


  });
}

function showAntibiogramResistanceMethodList() {
  let methodList = localReadAntibiogram()
    .antibioticLists[localReadCurrentAntibioticListIndex()]
    .methods;
  $("#antibiogramsResistanceMethodList").empty();
  methodList.forEach(function (method) {
    method.id = method.method.replace(/ /g, "_");
    $("#antibiogramsResistanceMethodList").append(fillTemplate(resistanceMethodHtmlTemplate, method));
    setResistanceMethodListOptions(method.id);
    $("#methodResult-" + method.id).val(method.result);
  });
}

function getAntibiogramOptions() {
  return localReadAntibiogramOptions();
}

function setResistanceMethodListOptions(id) {

  let options = getAntibiogramOptions().methodResults;

  $("#methodResult-" + id).empty();
  options.forEach(function (result) {
    $("#methodResult-" + id).append(
      `<option value="` + result + `">` + result + `</option>`
    );
  });

}

function setAntibioticListOptions(id) {

  let options = getAntibiogramOptions().interpretations;
  let abName = id.replace("/","-");
  $("#antibioticInterpretation-" + abName).empty();
  options.forEach(function (interpretation) {
    $("#antibioticInterpretation-" + abName).append(
      `<option value="` + interpretation + `">` + interpretation + `</option>`
    );
  });

}

function showAntibiogram() {
  setMicroorganismOptions();
  setAntibiogramTypesOptions();
  setAntibiogramColoniesCountOptions();
  setAntibiogramInfectionTypeOptions();
  showAntibiogramAntibioticList();
  showAntibiogramResistanceMethodList()
  showAddAntibioticList();
  showAddResistanceMethodList();
  showOptionalAntibioticList();  
  enableOrDisableIfMO();
  enableOrDisableRM();
  enableOrDisableAB();
}

function setAntibiogramColoniesCountOptions() {
  let options = getAntibiogramOptions().coloniesCount;
  let antibiogram = localReadAntibiogram();
  $("#idTestCanvasAntibiogramColoniesCount").empty();
  options.forEach(function (coloniesCountName) {
    $("#idTestCanvasAntibiogramColoniesCount").append(
      `<option value="` + coloniesCountName + `">` + coloniesCountName + `</option>`
    );
  });
  $("#idTestCanvasAntibiogramColoniesCount").val(antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].microorganism.ccname);
}

function setAntibiogramInfectionTypeOptions() {
  let options = getAntibiogramOptions().infectionTypes;
  let antibiogram = localReadAntibiogram();
  $("#b").empty();
  options.forEach(function (infectionTypeName) {
    $("#idTestCanvasAntibiogramInfectionType").append(
      `<option value="` + infectionTypeName + `">` + infectionTypeName + `</option>`
    );
  });
  $("#idTestCanvasAntibiogramInfectionType").val(antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].microorganism.itname);
}

function setAntibiogramTypesOptions() {
  let options = getAntibiogramOptions().antibiogramTypes;
  $("#idTestCanvasAntibiogramType").empty();
  options.forEach(function (antibiogramTypeName) {
    $("#idTestCanvasAntibiogramType").append(
      `<option value="` + antibiogramTypeName + `">` + antibiogramTypeName + `</option>`
    );
  });
  $("#idTestCanvasAntibiogramType").val(
    localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()].antibiogram.name
  );
}

function setMicroorganismOptions() {
  let options = getAntibiogramOptions().microorganism;
  $("#idTestCanvasAntibiogramMicroorganism").empty();
  options.forEach(function (microorganismName) {
    $("#idTestCanvasAntibiogramMicroorganism").append(
      `<option value="` + microorganismName + `">` + microorganismName + `</option>`
    );
  });
  $("#idTestCanvasAntibiogramMicroorganism").val(
    localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()].microorganism.name
  );
}

$("#idTestCanvasAntibiogramIsolatedNumber").change(function () {
  // localStoreCurrentAntibioticList();
  // localSetCurrentAntibioticListIndex($(this).val());
  // showAntibiogram();
});

$("#idTestCanvasAntibiogramMicroorganism").change(function () {
  let antibiogram = localReadAntibiogram();
  antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].microorganism = {
    name: $("#idTestCanvasAntibiogramMicroorganism").val(),
    id: $("#idTestCanvasAntibiogramMicroorganism").val()
  };
  localStoreAntibiogram(antibiogram);
  enableOrDisableIfMO();
  enableOrDisableRM();
  enableOrDisableAB();
});

$("#idTestCanvasAntibiogramType").change(function () {
  $.getJSON("Microbiologia/api/getAntibiogramAntibiotics", { antibiogramName: $("#idTestCanvasAntibiogramType").val() })
    .done(function (newAntibiotics) {
      let antibiogram = localReadAntibiogram();
      let newAntibioticList = [];
      newAntibiotics.forEach(function (newAntibiotic) {
        newAntibioticList.push({
          id: newAntibiotic,
          antibiotic: newAntibiotic,
          cim: 0, diameter: 0,
          interpretation: null,
          includeInReport: false
        })
      })
      antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].antibiogram = { name: $("#idTestCanvasAntibiogramType").val(), id: $("#idTestCanvasAntibiogramType").val() };
      antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics = newAntibioticList;
      localStoreAntibiogram(antibiogram);
      enableOrDisableAB();
      showAntibiogram();
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
});

$("#idTestCanvasAntibiogramColoniesCount").change(function () {
  let antibiogram = localReadAntibiogram();
  antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].microorganism.ccname = $("#idTestCanvasAntibiogramColoniesCount").val();
  localStoreAntibiogram(antibiogram);
});

$("#idTestCanvasAntibiogramInfectionType").change(function () {
  let antibiogram = localReadAntibiogram();
  antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].microorganism.itname =  $("#idTestCanvasAntibiogramInfectionType").val();
  localStoreAntibiogram(antibiogram);
});

function localStoreCurrentAntibioticList() {
  let antibioticList = [];
  let abName = "";
  let cleanAntibioticName = "";
  localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics
    .forEach(function (ab) {
      abName = ab.antibiotic;
      cleanAntibioticName = abName.replace("/","-")
      antibioticList.push({
        id: ab.id,
        antibiotic: ab.antibiotic,
        cim: parseInt($("#antibioticCim-" + cleanAntibioticName).val()),
        diameter: parseInt($("#antibioticDiameter-" + cleanAntibioticName).val()),
        interpretation: $("#antibioticInterpretation-" + cleanAntibioticName).val(),
        includeInReport: $("#antibioticIncludeInReport-" + cleanAntibioticName).prop("checked")
      })
    });
  let methodsList = []
  localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()].methods
    .forEach(function (mt) {
      methodsList.push({
        method: mt.method,
        result: $("#methodResult-" + mt.method.replace(/ /g, "_")).val()
      })
    });
  let antibiogram = localReadAntibiogram();
  antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics = antibioticList;
  antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].methods = methodsList;
  localStoreAntibiogram(antibiogram);
}

function localSetCurrentAntibioticListIndex(index) {
  GlobalCurrentAntibioticList = index;
}

function localReadCurrentAntibioticListIndex() {
  return GlobalCurrentAntibioticList;
}

function showAddAntibioticList() {
  let antibioticOptions = getAntibiogramOptions().antibioticList;
  let currentAntibioticList = localReadAntibiogram()
    .antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics
    .map(function (antibiotic) {
      return antibiotic.antibiotic;
    });
  let options = [];

  antibioticOptions.forEach(function (antibiotic) {
    options.push({
      id: antibiotic,
      name: antibiotic,
      selected: currentAntibioticList.includes(antibiotic)
    })
  })

  let select = `<select id="TestCanvasAntibiogramsAddAntibioticListBox" class="dual-listbox" data-search="false" multiple
                    data-available-title="Glosas disponibles"
                    data-selected-title="Glosas seleccionadas"
                    data-add="Agregar<i class='flaticon2-next'></i>"
                    data-remove="<i class='flaticon2-back'></i>Quitar">
                  </select>`;
  $("#TestCanvasAddAntibioticListBoxDiv").empty();
  $("#TestCanvasAddAntibioticListBoxDiv").append(select);
  dualListBosxInit("TestCanvasAntibiogramsAddAntibioticListBox", options);
}

function showAddResistanceMethodList() {
  let antibioticOptions = getAntibiogramOptions().methodList;
  let currentAntibioticList = localReadAntibiogram()
    .antibioticLists[localReadCurrentAntibioticListIndex()].methods
    .map(function (method) {
      return method.method;
    });
  let options = [];

  antibioticOptions.forEach(function (antibiotic) {
    options.push({
      id: antibiotic,
      name: antibiotic,
      selected: currentAntibioticList.includes(antibiotic)
    })
  })

  let select = `<select id="TestCanvasAntibiogramsResistanceMethodListBox" class="dual-listbox" data-search="false" multiple
                    data-available-title="Métodos disponibles"
                    data-selected-title="Métodos seleccionadas"
                    data-add="Agregar<i class='flaticon2-next'></i>"
                    data-remove="<i class='flaticon2-back'></i>Quitar">
                  </select>`;
  $("#TestCanvasAntibiogramsResistanceMethodListBoxDiv").empty();
  $("#TestCanvasAntibiogramsResistanceMethodListBoxDiv").append(select);
  dualListBosxInit("TestCanvasAntibiogramsResistanceMethodListBox", options);
}

function saveAddAntibiotics() {

  let antibiogram = localReadAntibiogram();

  let newAntibioticList = $("#TestCanvasAntibiogramsAddAntibioticListBox").val();
  let currentAntibioticList = antibiogram
    .antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics
    .map(function (antibiotic) {
      return antibiotic.antibiotic;
    });

  currentAntibioticList.forEach(function (currentAntibiotic) {
    if (newAntibioticList.includes(currentAntibiotic)) {
      newAntibioticList.splice(newAntibioticList.indexOf(currentAntibiotic), 1);
    } else {
      let currentAntibioticIndex = antibiogram
        .antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics
        .map(function (antibiotic) {
          return antibiotic.antibiotic;
        }).indexOf(currentAntibiotic);

      antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics.splice(currentAntibioticIndex, 1);
    }
  });

  newAntibioticList.forEach(function (newAntibiotic) {
    let antibiotcData = {
      id: newAntibiotic, antibiotic: newAntibiotic, cim: 0, diameter: 0, interpretation: null,
      includeInReport: false
    };
    antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics.push(antibiotcData);
  });

  localStoreAntibiogram(antibiogram);
  showAntibiogram();
}

function saveAddResistanceMethod() {
  let antibiogram = localReadAntibiogram();

  let newMethodList = $("#TestCanvasAntibiogramsResistanceMethodListBox").val();
  let currentMethodList = antibiogram
    .antibioticLists[localReadCurrentAntibioticListIndex()].methods
    .map(function (method) {
      return method.method;
    });

  currentMethodList.forEach(function (currentMethod) {
    if (newMethodList.includes(currentMethod)) {
      newMethodList.splice(newMethodList.indexOf(currentMethod), 1);
    } else {
      let currentAntibioticIndex = antibiogram
        .currentMethodList[localReadCurrentAntibioticListIndex()].methods
        .map(function (method) {
          return method.method;
        }).indexOf(currentMethod);

      antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].methods.splice(currentAntibioticIndex, 1);
    }
  });

  newMethodList.forEach(function (newMethod) {
    let methodData = { method: newMethod, result: null };
    antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].methods.push(methodData);
  });

  localStoreAntibiogram(antibiogram);
  showAntibiogram();
}

function showOptionalAntibioticList() {

}

function saveAddAntibiogram() {
  localStoreCurrentAntibioticList();
  let antibiogram = localReadAntibiogram();
  updateAntibiogram();
}

function localSetCurrentExamId(examId) {
  GlobalCurrentExamId = examId;
}

function localReadCurrentExamId() {
  return GlobalCurrentExamId;
}

function localSetCurrentOrderId(examId) {
  GlobalCurrentOrderId = examId;
}

function localReadCurrentOrderId() {
  return GlobalCurrentOrderId;
}

function localSetCurrentTestId(testId) {
  GlobalCurrentTestId = testId;
}

function localReadCurrentTestId() {
  return GlobalCurrentTestId;
}

function localStoreGlobalTest(test){
  GlobalTest = test;
}

function localReadGlobalTest(){
  return GlobalTest;
}

function localStoreGlobalId(id){
  GlobalId = id;
}

function localReadGlobalId(){
  return GlobalId;
}

function localStoreGlobalFlag(flag){
  GlobalFlag = flag;
}

function localReadGlobalFlag(){
  return GlobalFlag;
}

function clearCurrentAntibiogram() {
  let currentAntibiogram = localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()];
  currentAntibiogram.antibiogram = {};
  currentAntibiogram.antibiotics = [];
  currentAntibiogram.methods = [];
  currentAntibiogram.microorganism = {}
  localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()] = currentAntibiogram;
}

function optionalTestDisplay(id) {
  if ($("#optionalTest-" + id).is(':checked')) {
    $("#testsListDataRow-" + id).attr('style', '');
  } else {
    $("#testsListDataRow-" + id).attr('style', 'display: none');
  }
}

$("#examNotesButton").click(function () {
  $.ajax({
    url: "Microbiologia/api/putExamNotes",
    data: JSON.stringify({
      orderId: localReadCurrentOrderId(),
      examId: localReadCurrentExamId(),
      internalNote: $("#idExamCanvasNotesInternalNote").val(),
      examNote: $("#idExamCanvasNotesExamNote").val(),
      footerNote: $("#idExamCanvasNotesFooterNote").val()
    }),
    headers: {
      'Content-Type': 'application/json'
    },
    method: 'put',
    dataType: 'json',
    success: function (exam) {
      showExam(exam);
    },
    error: function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });
})

$("#btnTestCanvasAntibiogramClearIsolatedNumber").click(function () {
  alert("No se puede borrar el número aislado")
})


function addAGOptionsToData(){
  $.getJSON("Microbiologia/Mantenedores/api/getAGList", {code: "", name: "", activeOnly: true})
  .done(function (AGOptions) {
    var antibiogramOptionsNew = localReadAGOptions();
    antibiogramOptionsNew.antibiogramTypes = AGOptions;
    localStoreAGOptions(antibiogramOptionsNew);
    addRMOptionsToData();

  })
  .fail(function (jqxhr, textStatus, error) {
    var err = textStatus + ", " + error;
    console.log("Request Failed: " + err);
  });
}

function addRMOptionsToData(){
  $.getJSON("Microbiologia/Mantenedores/api/getRMList", { code: "", name: "", activeOnly: true })
    .done(function (RMList) {
      var antibiogramOptionsNew = localReadAGOptions();
      antibiogramOptionsNew.methodList = RMList;
      localStoreAGOptions(antibiogramOptionsNew);
      addRMResultsOptionsToData();
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function addRMResultsOptionsToData(){
  $.getJSON("Microbiologia/api/getResistanceMethodResultList")
    .done(function (resultList) {
      var antibiogramOptionsNew = localReadAGOptions();
      antibiogramOptionsNew.methodResults = resultList;
      localStoreAGOptions(antibiogramOptionsNew);
      addABOptionsToData();
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function addABOptionsToData(){
  $.getJSON("Microbiologia/Mantenedores/api/getABList")
    .done(function (antibioticOptions) {
      var antibiogramOptionsNew = localReadAGOptions();
      antibiogramOptionsNew.antibioticList = antibioticOptions;
      localStoreAGOptions(antibiogramOptionsNew);
      addMOOptionsToData()
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function addMOOptionsToData(){
  $.getJSON("Microbiologia/Mantenedores/api/getMOList", {code: "", name: "", activeOnly: true})
  .done(function (searchOptionsMO) {
    var antibiogramOptionsNew = localReadAGOptions();
    antibiogramOptionsNew.microorganism = searchOptionsMO;
    localStoreAGOptions(antibiogramOptionsNew);
    addCCOptionsToData()
  })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function addCCOptionsToData(){
  $.getJSON("Microbiologia/Mantenedores/api/getCCList", { code: "", name: "", activeOnly: true })
    .done(function (searchOptionsCC) {
      var antibiogramOptionsNew = localReadAGOptions();
      antibiogramOptionsNew.coloniesCount = searchOptionsCC;
      localStoreAGOptions(antibiogramOptionsNew);
      addInterpretationOptionsToData()
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function addInterpretationOptionsToData(){
  $.getJSON("Microbiologia/api/getAntibioticResultList")
  .done(function (interpretationList) {
      var antibiogramOptionsNew = localReadAGOptions();
      antibiogramOptionsNew.interpretations = interpretationList;
      localStoreAGOptions(antibiogramOptionsNew);
      var localAG = localReadAntibiogram();
      var testMOList = localReadTestMOList(); 
      mergeMOListAndTestData(localAG, testMOList);
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function addITOptionsToData(){
  $.getJSON("Microbiologia/api/getInfectionTypeList")
    .done(function (searchOptionsIT) {
      var antibiogramOptionsNew = localReadAGOptions();
      antibiogramOptionsNew.infectionTypes = searchOptionsIT;
      localStoreAGOptions(antibiogramOptionsNew);
    })

    .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    });
}

function enableOrDisableIfMO(){
  var MOId = $("#idTestCanvasAntibiogramIsolatedNumber").val();
  var DBValues = localReadDBAntibiogram();
  var idCheck = parseInt(MOId);
  if (!isNaN(idCheck)){  
    if (DBValues.antibioticLists[idCheck].microorganism.id != undefined){
      $("#idTestCanvasAntibiogramMicroorganism").attr("disabled", true);
      $('.dual-listbox__button:nth-child(2)').hide()
    }
    else{
      $("#idTestCanvasAntibiogramMicroorganism").attr("disabled", false);
      $('.dual-listbox__button:nth-child(2)').show()
    }
  }
  else{
    $("#idTestCanvasAntibiogramMicroorganism").attr("disabled", false);
    $("#removeButtonRM").attr("disabled", false);
  }
}

function enableOrDisableRM(){
  var selectedMO = $("#idTestCanvasAntibiogramMicroorganism").val();
  if (selectedMO == "" || selectedMO == null){
    $("#idTestCanvasAntibiogramsAddResistanceMethod").prop("disabled", true);
    $("#idTestCanvasAntibiogramColoniesCount").attr("disabled", true);
    $("#idTestCanvasAntibiogramInfectionType").attr("disabled", true);
    $("#idTestCanvasAntibiogramType").attr("disabled", true);
  }
  else{
    $("#idTestCanvasAntibiogramsAddResistanceMethod").prop("disabled", false);
    $("#idTestCanvasAntibiogramColoniesCount").attr("disabled", false);
    $("#idTestCanvasAntibiogramInfectionType").attr("disabled", false);
    $("#idTestCanvasAntibiogramType").attr("disabled", false);
  }
}

function enableOrDisableAB(){
  var selectedAG = $("#idTestCanvasAntibiogramType").val();
  if (selectedAG == "" || selectedAG == null){
    $("#idTestCanvasAntibiogramsAddAntibiotic").prop("disabled", true);
  }
  else{
    $("#idTestCanvasAntibiogramsAddAntibiotic").prop("disabled", false);
  }
}

function mergeMOListAndTestData(antibiogram, moList){
  var ITOptions = localReadAntibiogramOptions();
  var selectionOptions = localReadAGOptions();
  for (let i=1; i<5; i++){
    antibiogram.antibioticLists[i].microorganism.ccname = "";
    antibiogram.antibioticLists[i].microorganism.ccid = "";
    antibiogram.antibioticLists[i].microorganism.itname = "";
    antibiogram.antibioticLists[i].microorganism.itid = "";
    if (antibiogram.antibioticLists[i].microorganism.name != undefined){
      var mo = {};
      for (let j=0; j<moList.length; j++){
        mo = moList[j];
        let MOName = getNameFromId(mo.microorganismId, 'microorganism');
        if (antibiogram.antibioticLists[i].microorganism.name == MOName){
          if (mo.colonyCountId != ""){
            antibiogram.antibioticLists[i].microorganism.ccname = getNameFromId(mo.colonyCountId, 'coloniesCount');
            antibiogram.antibioticLists[i].microorganism.ccid = mo.colonyCountId;
          }
          if (mo.infectionTypeId != ""){
            antibiogram.antibioticLists[i].microorganism.itname = getInfectionTypeNameById(ITOptions.infectionTypes, mo.infectionTypeId);
            antibiogram.antibioticLists[i].microorganism.itid = mo.infectionTypeId;
          }
      }
     }
    }
  }
  localStoreAntibiogram(antibiogram);
  showTest(localReadGlobalTest(), $("#testMicrobiologyStatus-" + localReadGlobalId()).val() == "POSITIVO");
  if (localReadGlobalFlag()){
    saveTest();
  }
}

function getNameFromId(id, selectIdentifier){
  var selectOptions = localReadAGOptions();
  var name = "";
  var options = selectOptions[selectIdentifier];
  var element = {};
  for (i=0; i<options.length; i++){
    var element = options[i];
    if (parseInt(element.id) == parseInt(id)){
      name = element.name;
    }
  }
  return name; 
}

function getIdFromName(name, selectIdentifier){
  if (name != "" && name != undefined){
  var selectOptions = localReadAGOptions();
  var id = "";
  let elementName = "";
  var options = selectOptions[selectIdentifier];
  var element = {};
  for (i=0; i<options.length; i++){
    var element = options[i];
    elementName = element.name;
    if (elementName.indexOf(name) != -1){
      id = element.id;
    }
  }
  return id; 
}
else{
  return "";
}
}

function getInfectionTypeNameById(selectOptions, id){
  let ITName = "";
  ITName = selectOptions[parseInt(id)];
  return ITName; 
}

function getInfectionTypeIdByName(selectOptions, name){
  if (name == "" || name ==undefined){
    return "";
  }
  else{
  let ITId = "";
  for (let i=0; i<selectOptions.length; i++){
    if (selectOptions[i] == name){
      ITId = i;
    }
  
  }
  return ITId; 
}
}

function updateAntibiogram(){
  var ITOptions = localReadAntibiogramOptions();
  var testParams = localReadTestParameters();
  
  let examId = testParams.examId;
  let testId = testParams.testId;
  let orderId = testParams.orderId;
  
  let rowElement = {};

  let antibiogram = localReadAntibiogram();
  var moId = "";
  var agId = "";
  var ccId = "";
  var itId = "";
  for (let i=1; i<5; i++){
    if (antibiogram.antibioticLists[i].microorganism.name != undefined){
        moId = "";
        agid = "";
        ccId = "";
        itId = "";
        rowElement ={ 
          orderId: orderId,
          examId: examId,
          testId: testId,
          microorganismName: antibiogram.antibioticLists[i].microorganism.name,
        }
        if (antibiogram.antibioticLists[i].antibiogram.name != "" & antibiogram.antibioticLists[i].antibiogram.name != undefined){
          agId =  getIdFromName(antibiogram.antibioticLists[i].antibiogram.name, 'antibiogramTypes'); 
          if (agId !=""){
            rowElement.antibiogramId = agId;
          }
        }

        if (antibiogram.antibioticLists[i].microorganism.name != "" & antibiogram.antibioticLists[i].microorganism.name != undefined){
          moId = getIdFromName(antibiogram.antibioticLists[i].microorganism.name, 'microorganism');
          if (moId !=""){
            rowElement.microorganismId = moId;
          }
        }

        if (antibiogram.antibioticLists[i].microorganism.ccname != "" & antibiogram.antibioticLists[i].microorganism.ccname != undefined){
          ccId = getIdFromName(antibiogram.antibioticLists[i].microorganism.ccname, 'coloniesCount');
          if (ccId !=""){
            rowElement.colonyCountId = ccId;
          }
        }

        if (antibiogram.antibioticLists[i].microorganism.itname != "" & antibiogram.antibioticLists[i].microorganism.itname != undefined){
          itId = getInfectionTypeIdByName(ITOptions.infectionTypes, antibiogram.antibioticLists[i].microorganism.itname);
          if (itId !=""){
            rowElement.infectionTypeId = itId;
          }
        }
        $.ajax({
          url: "Microbiologia/api/putTestMO",
          data: JSON.stringify(rowElement),
          headers: { 'Content-Type': 'application/json' },
          method: 'put',
          async: false,
          dataType: 'json',
          success: function (test) {
            putMOResistance(moId, i, rowElement);
          },
          error: function (jqxhr, textStatus, error) {
              var err = textStatus + ", " + error;
              console.log("Request Failed: " + err);
          }
        });
        

    }
  }
}


function updateMOAntibiotic(i){
  
  var testParams = localReadTestParameters();
  
  let examId = testParams.examId;
  let testId = testParams.testId;
  let orderId = testParams.orderId;
  let patientId = testParams.patientId;

  let antibiogram = localReadAntibiogram();

  rowElement ={ 
    orderId: orderId,
    examId: examId,
    testId: testId,
    patientId: patientId,
  }


  if (antibiogram.antibioticLists[i].antibiogram.name != "" & antibiogram.antibioticLists[i].antibiogram.name != undefined){
    let agId =  getIdFromName(antibiogram.antibioticLists[i].antibiogram.name, 'antibiogramTypes'); 
    if (agId !=""){
    rowElement.antibiogramId = agId;
    }
  }

  if (antibiogram.antibioticLists[i].microorganism.name != "" & antibiogram.antibioticLists[i].microorganism.name != undefined){
    let moId = getIdFromName(antibiogram.antibioticLists[i].microorganism.name, 'microorganism');
    if (moId !=""){
      rowElement.microorganismId = moId;
    }
  }

  for (let j=0; j<antibiogram.antibioticLists[i].antibiotics.length; j++){

    if (antibiogram.antibioticLists[i].antibiotics[j].antibiotic != "" & antibiogram.antibioticLists[i].antibiotics[j].antibiotic != undefined){
      let abId = getIdFromName(antibiogram.antibioticLists[i].antibiotics[j].antibiotic, 'antibioticList');
      if (abId !=""){
        rowElement.antibioticId = abId;
      }
    }

    rowElement.includeInReport = "false";
    if (antibiogram.antibioticLists[i].antibiotics[j].includeInReport != null){
      rowElement.includeInReport = antibiogram.antibioticLists[i].antibiotics[j].includeInReport;
    }

    rowElement.diameter = "";
    if (antibiogram.antibioticLists[i].antibiotics[j].diameter != null & !isNaN(antibiogram.antibioticLists[i].antibiotics[j].diameter)){
      rowElement.diameter = antibiogram.antibioticLists[i].antibiotics[j].diameter;
    }

    rowElement.cim = "";
    if (antibiogram.antibioticLists[i].antibiotics[j].cim != null & !isNaN(antibiogram.antibioticLists[i].antibiotics[j].cim)){
      rowElement.cim = antibiogram.antibioticLists[i].antibiotics[j].cim;
    }

    rowElement.interpretation = "";
    if (antibiogram.antibioticLists[i].antibiotics[j].interpretation != null & !isNaN(antibiogram.antibioticLists[i].antibiotics[j].interpretation)){
      rowElement.interpretation = antibiogram.antibioticLists[i].antibiotics[j].interpretation;
    }
    $.ajax({
        url: "Microbiologia/api/putMOAntibiotic",
        data: JSON.stringify(rowElement),
        headers: { 'Content-Type': 'application/json' },
        method: 'put',
        async: false,
        dataType: 'json',
        success: function (test) {
        },
        error: function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        }
    });
    
  }
}

function deleteMOAntibiotic(idAB, microorganismId){
  var testParams = localReadTestParameters();
  let examId = testParams.examId;
  let testId = testParams.testId;
  let orderId = testParams.orderId;
  let patientId = testParams.patientId;


  let rowElement ={ 
      orderId: orderId,
      examId: examId,
      testId: testId,
      patientId: patientId,
      microorganismId: microorganismId,
      antibioticId: idAB
  }

  $.ajax({
      url: "Microbiologia/api/delMOAntibiotics",
      data: JSON.stringify(rowElement),
      headers: { 'Content-Type': 'application/json' },
      method: 'put',
      async: false,
      dataType: 'json',
      success: function (test) {
      },
      error: function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
      }
  });

}

function deleteErasedABFromMO(i){
  let antibiogram = localReadAntibiogram();
  let dbValues = localReadDBAntibiogram();

  let deleteSign = true;
  let ABName = "";

  let moId = getIdFromName(dbValues.antibioticLists[i].microorganism.name, 'microorganism');
  let idAB = "";
  for (let j=0; j<dbValues.antibioticLists[i].antibiotics.length; j++){
    deleteSign = true;  
    for (let k=0; k<antibiogram.antibioticLists[i].antibiotics.length; k++){
       if (dbValues.antibioticLists[i].antibiotics[j].antibiotic == antibiogram.antibioticLists[i].antibiotics[k].antibiotic){
         deleteSign = false;
       }
    }
    if (deleteSign){
      idAB = getIdFromName(dbValues.antibioticLists[i].antibiotics[j].antibiotic, 'antibioticList');
      deleteMOAntibiotic(idAB, moId)
    }
  }
}

function putMOResistance(moId, i, rowElement){
  var testParams = localReadTestParameters();
  let examId = testParams.examId;
  let testId = testParams.testId;
  let orderId = testParams.orderId;

  let antibiogram = localReadAntibiogram();

  let MOResistance = {
    orderId: orderId,
    examId: examId,
    testId: testId,
    microorganismId: moId
    };

    var mrId = "";
    var mrResultId = "";
  for (let j=0; j<antibiogram.antibioticLists[i].methods.length; j++){
    mrId = "";
    mrResultId = "";
    if (antibiogram.antibioticLists[i].methods[j].method != "" & antibiogram.antibioticLists[i].methods[j].method != undefined){
      mrId = getIdFromName(antibiogram.antibioticLists[i].methods[j].method, 'methodList');
      if (mrId !=""){
        MOResistance.marcadorId = mrId;
      }
    }

    if (antibiogram.antibioticLists[i].methods[j].result != "" & antibiogram.antibioticLists[i].methods[j].result != undefined){
      mrResultId = getIdFromName(antibiogram.antibioticLists[i].methods[j].result, 'methodResults');
      if (mrResultId !=""){
        MOResistance.resultId = mrResultId;
      }
    }
    
    $.ajax({
    url: "Microbiologia/api/putMOResistance",
    data: JSON.stringify(MOResistance),
    headers: { 'Content-Type': 'application/json' },
    method: 'put',
    async: false,
    dataType: 'json',
    success: function (testMO) {
      testMO.colonyCountId = rowElement.colonyCountId;
      testMO.infectionTypeId = rowElement.infectionTypeId;
      testMO.antibiogramId = rowElement.antibiogramId;
      $.ajax({
          url: "Microbiologia/api/putTestMO",
          data: JSON.stringify(testMO),
          headers: { 'Content-Type': 'application/json' },
          method: 'put',
          async: false,
          dataType: 'json',
          success: function (test) {
            console.group(test);
            deleteErasedABFromMO(i);
            updateMOAntibiotic(i);
          },
          error: function (jqxhr, textStatus, error) {
              var err = textStatus + ", " + error;
              console.log("Request Failed: " + err);
          }
      });
    },
    error: function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
    }
    });
    
  }

}



$("#idExamCanvasNotesInternalNote").on('blur', function() {
  modificacionTexto($(this));
});
$("#idExamCanvasNotesExamNote").on('blur', function() {
  modificacionTexto($(this));
});
$("#idExamCanvasNotesFooterNote").on('blur', function() {
  modificacionTexto($(this));
});


function textoAnterior(objeto, idExamCanvasNotesInternalNote, idExamCanvasNotesExamNote, idExamCanvasNotesFooterNote) {
  objeto.idExamCanvasNotesExamNote = "";
  objeto.idExamCanvasNotesFooterNote = "";
  objeto.idExamCanvasNotesInternalNote = "";
  objeto.idExamCanvasNotesInternalNote = idExamCanvasNotesInternalNote;
  objeto.idExamCanvasNotesExamNote = idExamCanvasNotesExamNote;
  objeto.idExamCanvasNotesFooterNote = idExamCanvasNotesFooterNote;
  Object.setPrototypeOf(objeto, Object.prototype);
}

function modificacionTexto(input){
  let validarVacio = $("#idExamCanvasNotesExamNote").val().trim() === "" 
                    && $("#idExamCanvasNotesFooterNote").val().trim() === "" 
                    && $("#idExamCanvasNotesInternalNote").val().trim() === "";

  if(validarVacio){
    $("#examModalNotesButton").prop('disabled',true)
  }
  if($(input).val().trim() !== "" && miObjeto[input.attr('id')] !== $(input).val()){
    $("#examModalNotesButton").prop('disabled',false)
  }
}