$(document).ready(function () {
  $("#tablePendingTasks").DataTable({
    "dom": "t",
    "paging": false
  });
  //validateForm();
  getPendingTasksSearchOptions();

})

function getPendingTasksSearchOptions() {
  $.getJSON("Microbiologia/api/getSearchOptions")
    .done(function (searchOptions) {
      showTasksSearchOptions(searchOptions);
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

function validateNameOrSunarme(){
  var nameLength = $("#idSearchName").val();
  var surnameLength = $("#idSearchSurname").val();
  if (nameLength.length > 0 || surnameLength.length > 0) {
    $("#idSearchOrderStart").val("");
    $("#idSearchOrderStart").prop("disabled", true);

    $('#idSearchDocumentNumber').prop('selectedIndex', -1);
    $("#idSearchDocumentNumber").prop("disabled", true);

    $('#idSearchAtentionType').prop('selectedIndex', -1);
    $("#idSearchAtentionType").prop("disabled", true);

    $('#idSearchService').prop('selectedIndex', -1);
    $("#idSearchService").prop("disabled", true);

  } 
  else {
    $("#idSearchOrderStart").prop("disabled", false);
    $("#idSearchDocumentNumber").prop("disabled", false);
    $("#idSearchAtentionType").prop("disabled", false);
    $("#idSearchService").prop("disabled", false);
} 

}

function showTasksSearchOptions(searchOptions) {
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


$("#searchButton").click(function () {
  var nameLength = $("#idSearchName").val();
  var surnameLength = $("#idSearchSurname").val();
  if (nameLength.length == 1){
    window.alert('Nombre debe tener 2 o más caracteres');
  }

  else if (surnameLength.length == 1){
    window.alert('Apellido debe tener 2 o más caracteres');
  }

  else {
    $("#tablePendingTasks").DataTable().clear();
    console.log(tasksSearchParameters());
    $.getJSON("Microbiologia/api/getPendingTasksList", tasksSearchParameters())
      .done(function (pendingTasksList) {
        if (pendingTasksList.length == 0){
          $("#tablePendingTasks").DataTable().clear().draw();
          window.alert('No se encontraron resultados');
        }
        else {
        pendingTasksList.forEach(function (pendingTask) {
            $("#tablePendingTasks").DataTable().row.add(
                [
                    pendingTask.examId,
                    pendingTask.date,
                    pendingTask.orderNumber,
                    pendingTask.name,
                    pendingTask.task
                ]
                ).draw();
              })
            }
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      });
  }
})


function tasksSearchParameters() {
  let result = {
    orderId: $("#idSearchOrderStart").val(),
    startDate: $("#idSearchStartDate").val(),
    endDate: $("#idSearchEndDate").val(),
    names: $("#idSearchName").val(),
    surname: $("#idSearchSurname").val(),
    documentType: $("#idSearchDocumentType").val(),
    documentId: $("#idSearchDocumentNumber").val(),
    atentionType: $("#idSearchAtentionType").val(),
    serviceId: $("#idSearchService").val()
  };
  return result;
}

$('#idSearchOrderStart').on('keyup', function () {
  var value = $('#idSearchOrderStart').val();
  if (value.length > 0) {
    $("#idSearchStartDate").val("");
    $("#idSearchStartDate").prop("disabled", true);

    $("#idSearchEndDate").val("");
    $("#idSearchEndDate").prop("disabled", true);

    $("#idSearchName").val("");
    $("#idSearchName").prop("disabled", true);
  
    $("#idSearchSurname").val("");
    $("#idSearchSurname").prop("disabled", true);

    $('#idSearchDocumentType').prop('selectedIndex', -1);
    $("#idSearchDocumentType").prop("disabled", true);

    $("#idSearchDocumentNumber").val("");
    $("#idSearchDocumentNumber").prop("disabled", true);

    $('#idSearchAtentionType').prop('selectedIndex', -1);
    $("#idSearchAtentionType").prop("disabled", true);

    $('#idSearchService').prop('selectedIndex', -1);
    $("#idSearchService").prop("disabled", true);

  } 
  else {
    $("#idSearchStartDate").prop("disabled", false);
    $("#idSearchEndDate").prop("disabled", false);
    $("#idSearchName").prop("disabled", false);
    $("#idSearchSurname").prop("disabled", false);
    $("#idSearchDocumentType").prop("disabled", false);
    $("#idSearchDocumentNumber").prop("disabled", false);
    $("#idSearchAtentionType").prop("disabled", false);
    $("#idSearchService").prop("disabled", false);
}})


$('#idSearchName').on('keyup', function () {
  validateNameOrSunarme();
})

$('#idSearchSurname').on('keyup', function () {
  validateNameOrSunarme();
})

$('#idSearchDocumentNumber').on('keyup', function () {
  var value = $('#idSearchDocumentNumber').val();
  if (value.length > 0) {
    $("#idSearchOrderStart").val("");
    $("#idSearchOrderStart").prop("disabled", true);

    $("#idSearchName").val("");
    $("#idSearchName").prop("disabled", true);
  
    $("#idSearchSurname").val("");
    $("#idSearchSurname").prop("disabled", true);

    $('#idSearchAtentionType').prop('selectedIndex', -1);
    $("#idSearchAtentionType").prop("disabled", true);

    $('#idSearchService').prop('selectedIndex', -1);
    $("#idSearchService").prop("disabled", true);

  } 
  else {
    $("#idSearchOrderStart").prop("disabled", false);
    $("#idSearchName").prop("disabled", false);
    $("#idSearchSurname").prop("disabled", false);
    $("#idSearchAtentionType").prop("disabled", false);
    $("#idSearchService").prop("disabled", false);
}})


$('#idSearchAtentionType').on('change', function () {
  var value = $('#idSearchAtentionType').val();
  if (value.length > 0) {
    $("#idSearchOrderStart").val("");
    $("#idSearchOrderStart").prop("disabled", true);

    $("#idSearchName").val("");
    $("#idSearchName").prop("disabled", true);
  
    $("#idSearchSurname").val("");
    $("#idSearchSurname").prop("disabled", true);

    $("#idSearchDocumentNumber").val("");
    $("#idSearchDocumentNumber").prop("disabled", true);

    $('#idSearchService').prop('selectedIndex', -1);
    $("#idSearchService").prop("disabled", true);

  } 
  else {
    $("#idSearchOrderStart").prop("disabled", false);
    $("#idSearchName").prop("disabled", false);
    $("#idSearchSurname").prop("disabled", false);
    $("#idSearchDocumentNumber").prop("disabled", false);
    $("#idSearchService").prop("disabled", false);
}})

$('#idSearchService').on('change', function () {
  var value = $('#idSearchService').val();
  if (value.length > 0) {
    $("#idSearchOrderStart").val("");
    $("#idSearchOrderStart").prop("disabled", false);

    $("#idSearchName").val("");
    $("#idSearchName").prop("disabled", true);
  
    $("#idSearchSurname").val("");
    $("#idSearchSurname").prop("disabled", true);

    $("#idSearchDocumentNumber").val("");
    $("#idSearchDocumentNumber").prop("disabled", true);

    $('#idSearchAtentionType').prop('selectedIndex', -1);
    $("#idSearchAtentionType").prop("disabled", true);

  } 
  else {
    $("#idSearchOrderStart").prop("disabled", false);
    $("#idSearchName").prop("disabled", false);
    $("#idSearchSurname").prop("disabled", false);
    $("#idSearchDocumentNumber").prop("disabled", false);
    $("#idSearchAtentionType").prop("disabled", false);
}})


class BusquedaPacientes{

  boBuscador;
  bo_OrdenesTable;
  tm_MuestrasTable;
  nro_Orden;
  
  constructor(){
    this.boBuscador = null;
    this.bo_OrdenesTable = null;
    this.tm_MuestrasTable = new MuestraList();
    this.nro_Orden = -1;  
    }
  }
  
  var gBusquedaPacientes = new BusquedaPacientes();

  function clickOnOrden(data){
      
    }


  $(document).ready(function() {


      const opciones = [
        {
          targets: [0,4,5,9,10,11,12,13,14,15,16,19,20,21,22,23,24,26,27,28,29], //targets no visibles
          visible:false,
        }
      ,{
        targets: 2, //fecha formatiada
        visible:true,
        render: function (data, type, row){
           let dia = data.substring(0, 2);
          let mes = data.substring(3, 5);
          let year = data.substring(8, 10);
          let hora = data.substring(11, 16);
         return data;
        //  return hora + " " + dia + "/" + nombresMeses(mes) + "/" + year;
        }
      }
      ,{
        targets: 3, //Estado
        visible:true,
        render: function (data, type, row){
          const color = estadoOrdenColor[row["ldvfet_DESCRIPCION"]];
          return `<span class="label label-lg font-weight-bold label-inline estadoOrden-${color}" data-id="${row["df_IDFICHAESTADOTM"]}"  >${row["ldvfet_DESCRIPCION"]}</span>`;
        }
        
      }
      ,{
        targets: 7, //Rut formatiado
        visible:true,
        render: function (data, type, row){
          if(row["ldvtd_DESCRIPCION"] == "RUN"){
            const nDocumento = cambiarDatoRut(row["dp_NRODOCUMENTO"]);
            return nDocumento;
          }else{
            return row["dp_NRODOCUMENTO"];
          }
        }
      }
      ,{
        targets: 8, //Nombre completo
        visible:true,
        render: function (data, type, row){
            return data+" "+row['dp_PRIMERAPELLIDO']+" "+(row['dp_SEGUNDOAPELLIDO'] || "");
        }
      },
      {
        targets: 14, //Edad
        visible:true,
        render: function(data, type, full, meta) {
            return data!==null ? calcularEdadTM(data) : "";
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
        gBusquedaPacientes.bo_OrdenesTable = new BiolisDatatableOrdenes('#bo_t_ordenes', gBusquedaPacientes.boBuscador,clickOnOrden,opciones);
      $("#bo_btnBuscarOrden").click(gBusquedaPacientes.bo_OrdenesTable.doSearch);
      $("#btnLimpiarFiltro").click(gBusquedaPacientes.bo_OrdenesTable.doLimpiarForm);
    // let columnas =  [0,4,5,9,10,11,12,13,14,15,16,19,20,21,22,23,24,26];
    // const n = gBusquedaPacientes.bo_OrdenesTable.bo_OrdenesDatable.columns().nodes().length;
    // for (let i=0; i<n; i++){
    //     for (let j=0; j<columnas.length; j++){
    //         if (i===columnas[j]){
    //           gBusquedaPacientes.bo_OrdenesTable.bo_OrdenesDatable.column(i).visible(false)
    //         }
    //     }
    // }
    // gBusquedaPacientes.bo_OrdenesTable.bo_OrdenesDatable.column(7).visible(true);
    });