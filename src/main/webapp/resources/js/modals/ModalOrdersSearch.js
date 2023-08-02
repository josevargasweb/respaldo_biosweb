$(document).ready(function() {
  /*
    $("#OrderCanvas").hide();
    $("#ExamCanvas").hide();
    $("#TestCanvas").hide();
    $("#AntibiogramCanvas").hide();
    $.getJSON("Microbiologia/api/getSearchOptions")
      .done(function (searchOptions) {
        showSearchOptions(searchOptions);
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      });
  */
})

$("#searchModalOrderButton").click(function() {
  const url =  getctx + "/api/ordenes/examenes";
  
  tableInfoOrdenes.ajax.url(url).load();

/*
  let filtroOrden = new FiltroOrden();
  filtroOrden.fill("#nOrden", "#fIni", "#fFin", "#nombre", "#apellido",
    "#tipoDocumento", "#nroDocumento",  "#tipoAtencion",
    "#idServicio", "#idServicio");
 
  $.post({
    url: getctx + "/api/ordenes/examenes",
    data: JSON.stringify(filtroOrden),
    contentType: "application/json; charset=utf-8",
    success: (function (orders) {
      showOrderList(orders);
      centerViewTo("#ListsCanvas");
      $("#OrdersListToggle").click();
    }),
    fail: (function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
      handlerMessageError(err);
    }),
  });
*/
  });

  
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

/*
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
  $.getJSON("Microbiologia/api/getTestData", { orderId: orderId, examId: examId, testId: id })
    .done(function (test) {
      $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function (antibiogramOptions) {
          localStoreAntibiogramOptions(antibiogramOptions)
          showTest(test, $("#testMicrobiologyStatus-" + id).val() == "POSITIVO");
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
  $("#idExamCanvasObservationsPatientName").val(observation.patient.name);
  $("#idExamCanvasObservationsPatientObservation").val(observation.patient.observation);

  $("#idExamCanvasObservationsOrderId").val(observation.order.id);
  $("#idExamCanvasObservationsOrderObservation").val(observation.order.observation);
  $("#idExamCanvasObservationsOrderExam").val(observation.order.exam);
  $("#idExamCanvasObservationsOrderUser").val(observation.order.user);
  $("#idExamCanvasObservationsOrderDate").val(observation.order.date);

  $("#idExamCanvasObservationsSamplingObservation").val(observation.sampling.observation);
  $("#idExamCanvasObservationsSamplingStatus").val(observation.sampling.status);
  $("#idExamCanvasObservationsSamplingUser").val(observation.sampling.user);
  $("#idExamCanvasObservationsSamplingDate").val(observation.sampling.date);

  $("#idExamCanvasObservationsReceptionObservation").val(observation.reception.observation);
  $("#idExamCanvasObservationsReceptionStatus").val(observation.reception.status);
  $("#idExamCanvasObservationsReceptionUser").val(observation.reception.user);
  $("#idExamCanvasObservationsReceptionDate").val(observation.reception.date);
}

function centerViewTo(element) {
  $('html, body').animate({
    scrollTop: $(element).offset().top
  }, 700);
}

function showOrderList(orderList) {
  $("#ordersListData").empty();
  orderList.forEach(function (order) {
    $("#ordersListData").append(fillTemplate(orderHtmlTemplate, order));
  });
}

function showOrderEvents(events) {
  $("#idOrderCanvasEventsTable").empty();
  events.forEach(function (event) {
    $("#idOrderCanvasEventsTable").append(fillTemplate(eventsRowHtmlTemplate, event));
  });

}

function showOrderData(order) {
  $("#idOrderCanvasDataName").val(order.patient.name);
  $("#idOrderCanvasDataGender").val(order.patient.gender);
  $("#idOrderCanvasDataAge").val(calcularEdadTM(order.patient.age));
  $("#idOrderCanvasDataDateOfBirth").val(order.patient.dateOfBirth);
  $("#idOrderCanvasDataPhoneNumber").val(order.patient.phoneNumber);
  $("#idOrderCanvasDataAtentionType").val(order.patient.atentionType);
  $("#idOrderCanvasDataLocalization").val(order.patient.localization);
  $("#idOrderCanvasDataPatientObservation").val(order.patient.observation);
  $("#idOrderCanvasDataPathologies").val(order.patient.pathologies);

  $("#idOrderCanvasDataOrderId").val(order.order.id);
  $("#idOrderCanvasDataDate").val(order.order.date);
  $("#idOrderCanvasDataContract").val(order.order.contract);
  $("#idOrderCanvasDataPhysician").val(order.order.physician);
  $("#idOrderCanvasDataDiagnostic").val(order.order.diagnostic);
  $("#idOrderCanvasDataOrderObservation").val(order.order.observation);

  $("#idOrderCanvasDataPreviousOrders").val(order.previousOrders.join(", "));
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

function showExamNotes(notes) {
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
      $.getJSON("Microbiologia/api/getAntibiogramOptions")
        .done(function (antibiogramOptions) {
          localStoreAntibiogramOptions(antibiogramOptions)
          showTest(test);
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
}

function showTestData(data) {
  $("#idTestCanvasDataName").empty(data.name);
  $("#idTestCanvasDataName").append(data.name);

  $("#idTestCanvasDataExam").val(data.exam);
  $("#idTestCanvasDataSample").val(data.sample);
  $("#idTestCanvasDataResultType").val(data.resultType);
  $("#idTestCanvasDataCode").val(data.code);
  $("#idTestCanvasDataPrefix").val(data.prefix);
  $("#idTestCanvasDataResult").val(data.result);
  $("#idTestCanvasDataCriticalValue").val(data.criticalValue);
  $("#idTestCanvasDataBodyPart").val(data.bodyPart);
  $("#idTestCanvasDataStatus").val(data.status);
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

//Antibiogram

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
  $("#TestCanvasAntibiograms").data("antibiogram", antibiogram);
}

function localReadAntibiogram() {
  return $("#TestCanvasAntibiograms").data("antibiogram");
}

function localStoreAntibiogramOptions(antibiogramOptions) {
  $("#TestCanvasAntibiograms").data("antibiogramOptions", antibiogramOptions);
}

function localReadAntibiogramOptions() {
  return $("#TestCanvasAntibiograms").data("antibiogramOptions");
}

function showAntibiogramAntibioticList() {
  let antibioticList = localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics;
  $("#antibiogramsAntibioticList").empty();
  antibioticList.forEach(function (antibiotic) {
    $("#antibiogramsAntibioticList").append(fillTemplate(antibiogramHtmlTemplate, antibiotic));
    setAntibioticListOptions(antibiotic.antibiotic);
    $("#antibioticCim-" + antibiotic.antibiotic).val(antibiotic.cim.toString());
    $("#antibioticDiameter-" + antibiotic.antibiotic).val(antibiotic.diameter.toString());
    $("#antibioticInterpretation-" + antibiotic.antibiotic).val(antibiotic.interpretation);
    $('#antibioticIncludeInReport-' + antibiotic.antibiotic).prop('checked', antibiotic.includeInReport);
    $("#antibioticResistantMethod-" + antibiotic.antibiotic).val(antibiotic.resistantMethod);
    $("#antibioticResult-" + antibiotic.antibiotic).val(antibiotic.result);
    $("#antibioticDiameter-" + antibiotic.antibiotic).change(function () {
      $.getJSON("Microbiologia/api/getAntibiogramResistance", {
        antibioticId: antibiotic.antibiotic,
        microorganismId: $("#idTestCanvasAntibiogramMicroorganism").val(),
        method: "3",
        radio: $("#antibioticDiameter-" + antibiotic.antibiotic).val()
      })
        .done(function (response) {
          if (response.interpretation != "")
            $("#antibioticInterpretation-" + antibiotic.antibiotic).val(response.interpretation);
        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ", " + error;
          console.log("Request Failed: " + err);
        });
    });
    $("#antibioticCim-" + antibiotic.antibiotic).change(function () {
      $.getJSON("Microbiologia/api/getAntibiogramResistance", {
        antibioticId: antibiotic.antibiotic,
        microorganismId: $("#idTestCanvasAntibiogramMicroorganism").val(),
        method: "2",
        cim: $("#antibioticCim-" + antibiotic.antibiotic).val()
      })
        .done(function (response) {
          if (response.interpretation != "")
            $("#antibioticInterpretation-" + antibiotic.antibiotic).val(response.interpretation);
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

  $("#antibioticInterpretation-" + id).empty();
  options.forEach(function (interpretation) {
    $("#antibioticInterpretation-" + id).append(
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
}

function setAntibiogramColoniesCountOptions() {
  let options = getAntibiogramOptions().coloniesCount;
  $("#idTestCanvasAntibiogramColoniesCount").empty();
  options.forEach(function (coloniesCountName) {
    $("#idTestCanvasAntibiogramColoniesCount").append(
      `<option value="` + coloniesCountName + `">` + coloniesCountName + `</option>`
    );
  });
  $("#idTestCanvasAntibiogramColoniesCount").val(localReadAntibiogram().colonyCount);
}

function setAntibiogramInfectionTypeOptions() {
  let options = getAntibiogramOptions().infectionTypes;
  $("#idTestCanvasAntibiogramInfectionType").empty();
  options.forEach(function (infectionTypeName) {
    $("#idTestCanvasAntibiogramInfectionType").append(
      `<option value="` + infectionTypeName + `">` + infectionTypeName + `</option>`
    );
  });
  $("#idTestCanvasAntibiogramInfectionType").val(localReadAntibiogram().infectionType);
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
  localStoreCurrentAntibioticList();
  localSetCurrentAntibioticListIndex($(this).val());
  showAntibiogram();
});

$("#idTestCanvasAntibiogramMicroorganism").change(function () {
  let antibiogram = localReadAntibiogram();
  antibiogram.antibioticLists[localReadCurrentAntibioticListIndex()].microorganism = {
    name: $("#idTestCanvasAntibiogramMicroorganism").val(),
    id: $("#idTestCanvasAntibiogramMicroorganism").val()
  };
  localStoreAntibiogram(antibiogram);
});

$("#idTestCanvasAntibiogramType").change(function () {
  $.getJSON("Microbiologia/api/getAntibiogramAntibiotics", { antibiogramName: $("#idTestCanvasAntibiogramType").val() })
    .done(function (newAntibiotics) {
      console.log("cambia antibiograma");
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
      showAntibiogram();
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
});

$("#idTestCanvasAntibiogramColoniesCount").change(function () {
  let antibiogram = localReadAntibiogram();
  antibiogram.colonyCount = $("#idTestCanvasAntibiogramColoniesCount").val();
  localStoreAntibiogram(antibiogram);
});

$("#idTestCanvasAntibiogramInfectionType").change(function () {
  let antibiogram = localReadAntibiogram();
  antibiogram.infectionType = $("#idTestCanvasAntibiogramInfectionType").val();
  localStoreAntibiogram(antibiogram);
});

function localStoreCurrentAntibioticList() {
  let antibioticList = [];
  localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()].antibiotics
    .forEach(function (ab) {
      antibioticList.push({
        id: ab.id,
        antibiotic: ab.antibiotic,
        cim: parseInt($("#antibioticCim-" + ab.antibiotic).val()),
        diameter: parseInt($("#antibioticDiameter-" + ab.antibiotic).val()),
        interpretation: $("#antibioticInterpretation-" + ab.antibiotic).val(),
        includeInReport: $("#antibioticIncludeInReport-" + ab.antibiotic).prop("checked")
      })
    });
  let methodsList = []
  localReadAntibiogram().antibioticLists[localReadCurrentAntibioticListIndex()].methods
    .forEach(function (mt) {
      console.log(mt);
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
  $("#TestCanvasAntibiograms").data("currentAntibioticList", index);
}

function localReadCurrentAntibioticListIndex(index) {
  return $("#TestCanvasAntibiograms").data("currentAntibioticList");
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
  console.log("Lista de antibioticos");
}

function saveAddAntibiogram() {
  localStoreCurrentAntibioticList();
  let antibiogram = localReadAntibiogram()
  console.log(antibiogram)
  $.ajax({
    url: "Microbiologia/api/putAntibiogram",
    data: JSON.stringify({
      orderId: localReadCurrentOrderId(),
      examId: localReadCurrentExamId(),
      testId: localReadCurrentTestId(),
      coloniesCount: antibiogram.colonyCount,
      infectionType: antibiogram.infectionType,
      antibioticLists: antibiogram.antibioticLists
    }),
    headers: {
      'Content-Type': 'application/json'
    },
    method: 'put',
    dataType: 'json',
    success: function (test) {
      console.log(test);
    },
    error: function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    }
  });
}

function localSetCurrentExamId(examId) {
  $("#ExamCanvas").data("currentExamId", examId);
}

function localReadCurrentExamId() {
  return $("#ExamCanvas").data("currentExamId");
}

function localSetCurrentOrderId(examId) {
  $("#OrderCanvas").data("currentOrderId", examId);
}

function localReadCurrentOrderId() {
  return $("#OrderCanvas").data("currentOrderId");
}

function localSetCurrentTestId(testId) {
  $("#TestCanvas").data("currentTestId", testId);
}

function localReadCurrentTestId() {
  return $("#TestCanvas").data("currentTestId");
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
  clearCurrentAntibiogram();
  showAntibiogram();
})
*/