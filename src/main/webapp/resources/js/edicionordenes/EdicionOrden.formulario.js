

//IIFE 
$(function() {    
  
    // $(".ocultar").hide();
    $(".disabledForm").prop("disabled", true);
      $(document).ready(function(){
        // $("#bo_btnBuscarOrden").click(obtPac);

console.log("autorizacion")
console.log($("#pideautorizeditorden").val());
console.log($("#eliminarexamenes").val());
console.log("********************")


        
            //al apretar el boton de guardar
    //   let ordenDoc = null;
    //   $("#btnModalSave").click(function (e) {
    //       let datos = $("#jsonFormData").val();
    //       datos = JSON.parse(datos);
    //       datos.dfObservacion = $("#txtObservacionRC").val();
    //       datos = JSON.stringify(datos);
    //       console.log(datos);
    //       $.ajax({
    //           type: "post",
    //           url: gCte.getctx + "/api/ordenes/insert",
    //           data: datos,
    //           async: false,
    //           contentType: "application/json",
    //           success: function (data) {
    //               try {
    //                   if (data.status !== 200) {
    //                       handlerMessageError(data.message);
    //                       return;
    //                   }
    //                   let orden = data.dato;
    //                   ordenDoc = orden;
    //                   //        $("#msjeRegistro").html(data.message);
    //                   $("#numeroOrdenTitulo").text("Nº" + orden.dfNorden);
    //                   $("#idOrderCreatedModal").modal("show");
    //                   limpiarTodo(vs, vt);
    //               } catch (e) {
    //                   handlerMessageError("No se pudo agregar la orden." + e.message);
    //               }
    //           },
    //           error: function (msg) {
    //               console.log(msg);
    //               handlerMessageError("No se pudo agregar la orden. " + msg);
    //           },
    //       });
  
    //   });
    

    let ordenDoc = null;
        $("#btnEditarOrden").click(insertarOrden);
        
      });


      function insertarOrden(e) {//rut
        e.preventDefault();
        
        let dpDto = getFormData();
        console.log(dpDto);
        if (!validaOrdenSubmit(dpDto)) {
          handlerMessageError('Faltan campos obligatorios');
          if ($("#spanDatosBasicos").length === 0) {
            let spanDatosBasicos = `<span id="spanDatosBasicos"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanDatosBasicos").css('display', 'inline-block');
            $("#edicionOrdenBasico").append(spanDatosBasicos);
          }
          if ($("#spanDatosExamanes").length === 0) {
            let spanDatosExamanes = `<span id="spanDatosExamanes"  class="label label-danger font-weight-bold label-inline ocultar">
            <i class="fa la-exclamation-circle text-white"></i></span>`;
            $("#spanDatosExamanes").css('display', 'inline-block');
            $("#edicionOrdenExamanes").append(spanDatosExamanes);
          }
          return;
        }
        if ($("#spanDatosBasicos").length > 0) {
          $("#spanDatosBasicos").remove();
        }
        if ($("#spanDatosExamanes").length > 0) {
          $("#spanDatosExamanes").remove();
        }

        if(Ordeneslst.puedeEditarORden == "N" && $("#procedenciaUsuario").length && Ordeneslst.df_IDPROCEDENCIA != $("#procedenciaUsuario").val()){
          return 
        }

        datos = JSON.stringify(dpDto);
        
        $.ajax({
          type: "post",
         // url: gCte.getctx + "/api/edicionOrdenes/update",
         url: gCte.getctx + "/api/ordenes/update",
          data: datos,
          async: false,
          contentType: "application/json",
          success: function (data) {
              try {
                  if (data.status !== 200) {
                      handlerMessageError(data.message);
                      return;
                  }
                  $("#numeroOrdenTitulo").text("Nº" + dpDto.dfNorden);
                  $("#idOrderCreatedModal").modal("show");
                  handlerMessageOk("Se actualizo la orden con exito");
                 limpiarTodo(vs, vt);
              } catch (e) {
                  handlerMessageError("No se pudo modificar la orden." + e.message);
              }
             
          },
          error: function (msg) {
              console.log(msg);
              handlerMessageError(" No se pudo modificar la orden. " + msg);
          },           
         
      });
      
      }


    function getFormData() {
        let datosFichasDTO = new Object();
        datosFichasDTO.datosPacientes = $("#idPaciente").val();
        datosFichasDTO.dfNorden = $("#dfOrden").val();
        console.log("fechaorden")
        console.log($("#fechaOrden").val())
         datosFichasDTO.dfFechaorden = $("#fechaOrden").val();
        datosFichasDTO.cfgServicios = RegistroOden.ServicioComboBoxM;
        datosFichasDTO.cfgDiagnosticos = RegistroOden.DiagnosticoComboBoxM;
        datosFichasDTO.cfgConvenios = RegistroOden.ConvencioComboBoxM;
        datosFichasDTO.cfgProcedencias = RegistroOden.ProcedenciaComboBoxM;
        datosFichasDTO.cfgPrioridadatencion = RegistroOden.selectPrioridadAtencionPac;
        datosFichasDTO.cfgTipoatencion = RegistroOden.tipoDeAtencion;
        datosFichasDTO.dfIdmedico = RegistroOden.medicoComboBox;
        datosFichasDTO.dfEnviarresultadosemail = $("#txtEmailP").val();
        datosFichasDTO.dfIdprevision = RegistroOden.ConvencioComboBoxM;
        datosFichasDTO.salas = RegistroOden.SalaComboBoxM;
        datosFichasDTO.camas = RegistroOden.cama;
        datosFichasDTO.lstExamenesDto = new Array();
        datosFichasDTO.lstExamenes = new Array();
        
      
        datosFichasDTO.dfObservacion = $("#txtObservacionR").val();
        datosFichasDTO.dfCodigolocalizacion = $("#ServicioComboBoxM").val();
        
        let items = vt.getAllItems();
      
        items.forEach((item) => { datosFichasDTO.lstExamenesDto.push(item.dataValue); });
        items.forEach((item) => { datosFichasDTO.lstExamenes.push(item.id); });
        setExamenes(items);


        let difference = Ordeneslst.idexamen.filter(x => !datosFichasDTO.lstExamenes.includes(x));
        datosFichasDTO.lstExamenesEliminados = difference;
        
        
        datosFichasDTO.tests = Ordeneslst.testsParaEnviar;


      
        return datosFichasDTO;
      
      }
      
function setExamenes(items) {
        let i = 0;
        $("#txtExamenesR tbody").empty();
        items.forEach((item) => {
          let descripcion = (typeof item.dataValue.ldvieDescripcion !== "undefined") ? item.dataValue.ldvieDescripcion : "-";
          $("#txtExamenesR tbody").append('<tr>' + '<td><input type="hidden" value="' + item.dataValue.ceAbreviado + '" name="ExamenesResumenes">' + item.dataValue.ceAbreviado + '</td><td>' + (descripcion === "" ? "<div class='w-100 text-center'>-</div>" : descripcion) +
            '</td></tr>');
      
        });
      }


     
  



        $("#CamaComboBoxM").on("blur change", function () {
            if($(this).val() == ""){
            RegistroOden.cama = null;
            }
            $(this).removeClass("is-invalid");
        });
        
        $("#txtObservacion").keydown(function () {
            $("#txtObservacionR").val(this.value);
        });
        
        
        $("#selectTipoDocumento").change(function () {
            let tDoc = "Pasaporte";
            if ($(this).val() == 1) {
                tDoc = "RUN";
                $("#divTxtRutFiltro").show();
                $("#divTxtPasaporteFiltro").hide();
            } else {
                $("#divTxtRutFiltro").hide();
                $("#divTxtPasaporteFiltro").show();
            }
            $("#tipoDocLabel").text(tDoc);
        
            limpiarTabPaciente();
        });
        $("#chbxRestutToMail").change(function(){
          if ($(this).is(":checked")) {
            $("#txtEmailP").val("on");
            console.log($("#txtEmailP").val())
          } else {
            $("#txtEmailP").val("off");
            console.log($("#txtEmailP").val())
          }
        })

    
});



function limpiarTodo(vs, vt) {

  limpiarDatosPaciente();
  limpiarDatosOrdenes();
  limpiarDatosExamenes(vs, vt);
  limpiarBotones();
  $("#bo_t_ordenes").DataTable().rows().deselect();
  $(".selectpicker").selectpicker("refresh");
}






function limpiarBotones(){
  $("#btnEditarOrden").addClass("d-none");
  $("#btnEditarOrden").removeClass("d-inline-block");
  $("#btnCancelEdit").addClass("d-none");
  $("#btnCancelEdit").removeClass("d-inline-block");
  $(".disabledForm").prop("disabled", true);
}




function limpiarDatosPaciente() {
  limpiarDatosBasicosPaciente();
}

function limpiarDatosOrdenes() {
  limpiarDatosBasicosOrden();
}

function limpiarDatosExamenes() {
  eventoLimpiar();
}

function limpiarDatosBasicosPaciente() {

  $("#tipo_documento").text("");
  $("#numero_doc").text("");
  $("#nombre_orden").text("");
  $("#sexo_orden").text("");
  $("#edad_orden").text("");
  $("#email_orden").text("");
  $("#chbxRestutToMail").prop("checked", false);

  $("#txtNombreP").val("");
  /*
  $("#tipoDeAtencion").selectpicker('val', '');
  $("#ProcedenciaComboBoxM").selectpicker('val', '');
  $("#ServicioComboBoxM").selectpicker('val', '');
  $("#SalaComboBoxM").selectpicker('val', '');
  $("#CamaComboBoxM").selectpicker('val', '');
  */
  $("#tipoDeAtencion").val(null);
  $("#ProcedenciaComboBoxM").val(null);
  $("#ServicioComboBoxM").val(null);
  $("#SalaComboBoxM").val(null);
  $("#CamaComboBoxM").val(null);
  
  $("#idPaciente").val("");
  $("#txtEdad").val("");
  
  $("#dfEnviarresultadosemail").val("");
  $("#sexoPacienteOrden").val("");
  $("#txtPrimerApellidoP").val("");
  $("#txtEmailP").val("");


  $("#tableBodyDiagnosticoPaciente").empty();
  $("#txtObservacionP").text("");

  RegistroOden.medicoComboBox= null,
  RegistroOden.tipoDeAtencion=-1;
  RegistroOden.ProcedenciaComboBoxM=-1;
  RegistroOden.ServicioComboBoxM=-1;
  RegistroOden.SalaComboBoxM=null;
  RegistroOden.cama=null;
  RegistroOden.selectPrioridadAtencionPac=null;
  RegistroOden.ConvencioComboBoxM=null;
  RegistroOden.DiagnosticoComboBoxM=1;
  //$("#selectPrioridadAtencionPac").selectpicker('val', '');
  $("#selectPrioridadAtencionPac").val(null);

  $("#ipEquipo").val("");
  $("#txtIdPaciente").val("");
  $("#dpIdmadre").val("");
  $("#dfOrden").val("");
  $("#fechaOrden").val("");
}



function limpiarDatosBasicosOrden() {

 // $("#medicoBox").selectpicker('val', '');
 $("#medicoBox").val(null);
  $("#txtRutMedico").text("");
  $("#txtNombreM").val("");
 // $("#ConvencioComboBoxM").selectpicker('val', '');
 $("#ConvencioComboBoxM").val(null);
  $("#idMedico").val("");
  $("#dfObservacion").val("");
 // $("#DiagnosticoComboBoxM").selectpicker('val', '');
  $("#DiagnosticoComboBoxM").val(null);
  Ordeneslst.idexamen = null;
}


