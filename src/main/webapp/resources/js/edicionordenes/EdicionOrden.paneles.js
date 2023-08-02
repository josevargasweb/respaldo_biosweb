var dualDataBS;
var dualDataSource = new Array();
var dualDataTarget = new Array();
var vt;
var vs;
var visualSearcher;
var dataSearcher;
var demo;
var listaSexos;
// Obtener los examenes con test comunes.


    $(".switch-orden input").change(cambiarEstadoSwitch);
    $(".switch-comprobante input").change(cambiarEstadoComprobante);
    $('#idSearcher').attr('autocomplete', 'off');
    // Nuevo manejo examenes. hay que seguir modularizando en dual-data.js
    var dtVT = new DataTableVisualTarget("#idDataTarget");
    var dt = new DataSource([]);
    vt = new VisualTarget(dt, dtVT);
    vs = new VisualSource();
    test = genVSFiller("#idDualDataVisualSource", vs, "#idSearcher");
    testPaneles = genVSPanelFiller(
        "#idPanelDualDataVisualSource",
        vs,
        "#idSearcher"
    );

    var movedor = new Mover(dtVT, dt, vt, vs);

    vs.onDoubleClick(movedor);
    let svcPanel = new ItemPanelService();
    svcPanel.getPaneles(testPaneles);
    let svc = new CfgExamen();
    svc.getExamenes(test);
    function eventoAdd() {
        let itemId = vs.getSelectedItemId();
        let item = vs.getSelectedItemById(itemId);
        if(validarOnAdd(item,vs.visualElement.jqid)){
            if (vs.visualElement.jqid !== "#idPanelDualDataVisualSource"){
              vs.removeItemById(itemId);
            }
            vt.addItem(item);
            vs.removePanelItem(item);
        }
    }
    function eventoAddMultiple(examenes) {
        examenes.forEach(element =>{
            let dataItemAux =  new DataItem(element.idexamen,
                 element.codexamen,
                 element.descripcionmuestra,
                element.examenabreviado,
                 element.cedescripcion,
                element.idseccion,
                element.descripcionseccion,
                element.idlaboratorio !== null ? element.idlaboratorio : "",
                element.idcentrosalud !== null ? element.idcentrosalud : "",
                element.descripcionlaboratorio !== null ? element.descripcionlaboratorio : "",
                element.descripcioncentrosalud !== null ?element.descripcioncentrosalud : "",
                element.ceesurgente !== null ?element.ceesurgente : "N",
                element.ldvieidindicacionesexamen !== null ?element.ldvieidindicacionesexamen : "",
                element.ldviedescripcion !== null ?element.ldviedescripcion : "",
                element.examenanulado !== null ?element.examenanulado : "N",
                element.codestadoexamen,
                element.idusuarioanula !== null ? element.idusuarioanula : "",
                element.idderivador !== null ? element.idderivador : 0,
                );
                let itemAux = new Item(dataItemAux);
                vt.addItem(itemAux);

                if (vs.visualElement.jqid !== "#idPanelDualDataVisualSource"){
                vs.removeItemById(element.idexamen);
                }

              vs.removePanelItem(itemAux);
        });


    }

    function eventoDel() {
        let itemsDele = vt.getAllItems();
        if(itemsDele.length > 1){
            let items = vt.getSelectedItems();
            const yaAgregado =  Ordeneslst.idexamen.some(function(idExamens) {
                return idExamens == items[0].id;
              });
          if(!yaAgregado){
            let data = Ordeneslst.testsParaEnviar.reduce((acc,test,indice) =>{
            if(items[0].id == test.Idexamen){
                let d = {"Idexamen":test.Idexamen,"idtest":test.idtest,"idmuestra":test.idmuestra,"idenvase":test.idenvase};
                return d;
            }
                return acc;
            },{});
            if(!jQuery.isEmptyObject(data)){
                console.log(data);
                Ordeneslst.testsParaEnviar.splice(data, 1);
            }
            vt.removeItems(items);
            vs.addItems(items);
          }    
        }
    }

    function eventoLimpiar() {
        let items = vt.getItems();
        vt.removeItems(items);
        vs.addItems(items);
    }

    $("#clickAddBtn").click(eventoAdd);
    $("#clickDelBtn").click(eventoDel);
    $("#idPanelDualDataVisualSource").hide();
    $("#idBtnExamen").click(function (e) {
        $("#idDualDataVisualSource").show();
        $("#idPanelDualDataVisualSource").hide();
        vs.visualElement.jqid = "#idDualDataVisualSource";
        vs.swapDs2Examen();
    });
    $("#idBtnPanel").click(function (e) {
        $("#idPanelDualDataVisualSource").show();
        $("#idDualDataVisualSource").hide();
        vs.visualElement.jqid = "#idPanelDualDataVisualSource";
        vs.swapDs2Panel();
    });

    function cambiarEstadoSwitch() {
        var dad = $(this).parent();
        if ($(this).is(":checked")) {
            dad.addClass("switch-orden-checked");
            $("#idPanelDualDataVisualSource").show();
            $("#idDualDataVisualSource").hide();
            vs.visualElement.jqid = "#idPanelDualDataVisualSource";
            $('.label-panel').removeClass('text-black-50');
            $('.label-examen').addClass('text-black-50');
            vs.swapDs2Panel();
        } else {
            dad.removeClass("switch-orden-checked");
            $("#idDualDataVisualSource").show();
            $("#idPanelDualDataVisualSource").hide();
            vs.visualElement.jqid = "#idDualDataVisualSource";
            $('.label-panel').addClass('text-black-50');
            $('.label-examen').removeClass('text-black-50');
            vs.swapDs2Examen();
        }
    }
  
    function cambiarEstadoComprobante() {
        var dad = $(this).parent();
        if ($(this).is(":checked")) {
            $("#idOrderConfirmationModal").find("#checkBoxLeverCromp").val("S");
            $("#idOrderCreatedModal").find("#checkBoxLeverCromp").val("S");
            $("#idOrderConfirmationModal").find("#checkBoxLeverCromp").prop( "checked", true );
            $("#idOrderCreatedModal").find("#checkBoxLeverCromp").prop( "checked", true );
            
            $("#idOrderConfirmationModal").find("#txtEstado").val("S");

            $('.label-voucher').addClass('text-black-50');
            $('.label-comprobante').removeClass('text-black-50');
        } else {
            $("#idOrderConfirmationModal").find("#checkBoxLeverCromp").val("N");
            $("#idOrderCreatedModal").find("#checkBoxLeverCromp").val("N");
            $("#idOrderConfirmationModal").find("#checkBoxLeverCromp").prop( "checked", false );
            $("#idOrderCreatedModal").find("#checkBoxLeverCromp").prop( "checked", false );
            
            $("#idOrderConfirmationModal").find("#txtEstado").val("N");

            $('.label-voucher').removeClass('text-black-50');
            $('.label-comprobante').addClass('text-black-50');
        }
    }

    $(".selectpicker").selectpicker({
        noneSelectedText: "Seleccionar",
    });
    $(".SSM").selectpicker({
        noneSelectedText: "Seleccionar",
    });
    // Fin nuevo manejo examens
    
  


    $("#btnCancelEdit").click(limpiarOrden);
    function limpiarOrden(){
        $("#btnEditarOrden").addClass("d-none");
        $("#btnEditarOrden").removeClass("d-inline-block");
        $("#btnCancelEdit").addClass("d-none");
        $("#btnCancelEdit").removeClass("d-inline-block");
        $(".disabledForm").prop("disabled", true);

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
        $("#txtObservacionP").val("");

        RegistroOden.medicoComboBox= null,
        RegistroOden.tipoDeAtencion=-1;
        RegistroOden.ProcedenciaComboBoxM=-1;
        RegistroOden.ServicioComboBoxM=-1;
        RegistroOden.SalaComboBoxM=null;
        RegistroOden.cama=null;
        RegistroOden.selectPrioridadAtencionPac=null;
        RegistroOden.ConvencioComboBoxM=null;
        RegistroOden.DiagnosticoComboBoxM=1;
        /*
        $("#selectPrioridadAtencionPac").selectpicker('val', '');

        $("#medicoBox").selectpicker('val', '');
        */
         $("#selectPrioridadAtencionPac").val(null);
        $("#medicoBox").val(null);
        
        $("#txtRutMedico").text("");
        $("#txtNombreM").val("");
       // $("#ConvencioComboBoxM").selectpicker('val', '');
       $("#ConvencioComboBoxM").val(null)
        $("#idMedico").val("");
        $("#dfObservacion").val("");
     //  $("#DiagnosticoComboBoxM").selectpicker('val', '');
     $("#DiagnosticoComboBoxM").val(null);
      
        Ordeneslst.idexamen = null;

        $("#ipEquipo").val("");
        $("#txtIdPaciente").val("");
        $("#dpIdmadre").val("");
        $("#dfOrden").val("");
        $("#fechaOrden").val("");
         /*** METOODOS ***/
     
        eventoLimpiar();
        $("#bo_t_ordenes").DataTable().rows().deselect();
         $(".selectpicker").selectpicker("refresh");
      }


$('a[data-toggle="tab"]').on('shown.bs.tab', function(e){
    $("#idDataTarget").DataTable().columns.adjust().draw(false);
});  


// $('#idDataTarget').on( 'change', 'input.chck-urgente', function () {
//     console.log( $(this).data('urgente'));
// });




$('#idDataTarget').on( 'change', 'input.chck-anulado', function () {
    let items = vt.getAllItems();
    let item = '';
    let id = $(this).data('anulado');
    if($(`[data-anulado="${id}"]`).is(':checked')){
    items.forEach(element =>{
        if(element.id == id){
            console.log('element',element);
            let modalAnulado = `
            <h5 class="col-12 pl-0 pr-0 mt-3 mb-3 text-center">Anula examen</h5>
             Una vez anulado el examen no se podra volver a utilizar, Desea continuar?
            <div class="col-12 d-flex justify-content-end">
                <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  ${id}-no-examen">No</button>
                <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  ${id}-si-examen">Si</button>
            </div>
            `;
            $("#usuarioAnula").modal();
            $("#usuarioAnula").find(".modal-body").html(modalAnulado);
            $("#usuarioAnula").on('click',function(e) {
                if($(e.target).hasClass(`${id}-si-examen`) && element.dataValue.codestadoexamen != "A"){
                element.dataValue.ceActivo = 'S';
                element.dataValue.idusuarioanula = parseInt($("#sesionUsuario").val());
                $(`tr#${id}`).addClass('esInactivoRow');
                $(`[data-anulado="${id}"]`).prop('disabled', true);
                $(`[data-urgente="${id}"]`).prop('disabled', true);
                $("#usuarioAnula").modal('hide')
                $("#usuarioAnula").find(".modal-body").html("");
                }else if($(e.target).hasClass(`${id}-no-examen`) && element.dataValue.codestadoexamen != "A"){
                element.dataValue.ceActivo = 'N';
                element.dataValue.idusuarioanula = "";
                $(`tr#${id}`).removeClass('esInactivoRow');
                $(`[data-anulado="${id}"]`).removeAttr("disabled");
                $(`[data-urgente="${id}"]`).removeAttr("disabled");
                $(`[data-anulado="${id}"]`).prop('checked', false);
                $("#usuarioAnula").modal('hide')
                $("#usuarioAnula").find(".modal-body").html("");
                }else if(element.dataValue.codestadoexamen == "A"){
                element.dataValue.idusuarioanula = "";
                $("#usuarioAnula").modal('hide')
                $("#usuarioAnula").find(".modal-body").html("");
                let modalFirmado = `
                No se puede anular el examen ya que esta en estado firmado
                <div class="col-12 d-flex justify-content-end">
                    <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  ${id}-ok-firmado">Ok</button>
                </div>
                `;
                $("#ordenFirmada").modal();
                $("#ordenFirmada").find(".modal-body").html(modalFirmado);
                $("#ordenFirmada").on('click',function(e) { 
                    if($(e.target).hasClass(`${id}-ok-firmado`)){
                        $("#ordenFirmada").modal('hide')
                        $("#ordenFirmada").find(".modal-body").html("");
                    }
                });   
                // alert("No se puede anular el examen ya que esta en estado firmado");
                }
                });   
            // let isExecuted = confirm("Una vez anulado el examen no se podra volver a utilizar, Desea continuar?");
            // if(isExecuted && element.dataValue.codestadoexamen != "F"){
            //     element.dataValue.ceActivo = 'S'
            //     $(`tr#${id}`).addClass('esInactivoRow');
            //     $(`[data-anulado="${id}"]`).prop('disabled', true);
            //     $(`[data-urgente="${id}"]`).prop('disabled', true);
            // }else if(!isExecuted && element.dataValue.codestadoexamen != "F"){
            //     element.dataValue.ceActivo = 'N'
            //     $(`tr#${id}`).removeClass('esInactivoRow');
            //     $(`[data-anulado="${id}"]`).removeAttr("disabled");
            //     $(`[data-urgente="${id}"]`).removeAttr("disabled");
            //     $(`[data-anulado="${id}"]`).prop('checked', false);
            // }else if(element.dataValue.codestadoexamen == "F"){
            //     alert("No se puede anular el examen ya que esta en estado firmado");
            // }
        }
    })
    }

    if($(`[data-urgente="${id}"]`).is(':checked')){
        items.forEach(element =>{
            if(element.id == id){
                element.dataValue.ceEsurgente = 'S'
            }
        })
    }else if(!$(`[data-urgente="${id}"]`).is(':checked')){
        items.forEach(element =>{
            if(element.id == id){
                element.dataValue.ceEsurgente = 'N'
            }
        })
    }
});

$('#idDataTarget').on( 'change', 'input.chck-urgente', function () {
    let items = vt.getAllItems();
    let item = '';
    let id = $(this).data('urgente');

    if($(`[data-urgente="${id}"]`).is(':checked')){
        items.forEach(element =>{
            if(element.id == id){
                element.dataValue.ceEsurgente = 'S'
            }
        })
    }else if(!$(`[data-urgente="${id}"]`).is(':checked')){
        items.forEach(element =>{
            if(element.id == id){
                element.dataValue.ceEsurgente = 'N'
            }
        })
    }
});




function  validarOnAdd(examenes,jqId)
{
    if (jqId === "#idDualDataVisualSource"){
        let resultado = false;
        if(Ordeneslst.muestratomada == 'S'){
          try {
            let data = Ordeneslst.tests.reduce((acc,test,indice) =>{
              if(examenes.id == test.idexamen){
                let d = {"idexamen":test.idexamen,"idtest":test.idtest,"idmuestra":test.idmuestra,"idenvase":test.idenvase};
                return d;
              }
                return acc;
              },{});
              if(!jQuery.isEmptyObject(data)){
                Ordeneslst.testsParaEnviar = [...Ordeneslst.testsParaEnviar,data];
                Ordeneslst.testsParaEnviar = [...new Map(Ordeneslst.testsParaEnviar.map(item => [item["idexamen"], item])).values()];
                resultado = true;
              }else{
                handlerMessageError("No se puede agregar el examen ya que no contiene muestras tomadas");
                resultado = false;
              }
              // Ordeneslst.tests.splice(data, 1);
          } catch (error) {
            return false;
          }
       
          } else if(Ordeneslst.muestratomada == 'N'){
            return true;
          }else{
            handlerMessageError("No se puede agregar el examen ya que no contiene muestras tomadas");
          }
          return resultado;
      }
      else{
        let resultado = false;
        if(Ordeneslst.muestratomada == 'S'){
          try {
            let data = Ordeneslst.tests.reduce((acc,test,indice) =>{
              //verifica que exista
             let itemId = Object.keys(examenes.dataValue.lstExamenes).some(function(k) {
                return examenes.dataValue.lstExamenes[k]["ceIdexamen"] === test.idexamen ;
              })
      
              if(itemId){
                return acc[0] = [...acc, {"idexamen":test.idexamen,"idtest":test.idtest,"idmuestra":test.idmuestra,"idenvase":test.idenvase}];
              }
                return acc;
              },[]);
              if(!jQuery.isEmptyObject(data)){
                Ordeneslst.testsParaEnviar =  [...Ordeneslst.testsParaEnviar,...data];
                Ordeneslst.testsParaEnviar = [...new Map(Ordeneslst.testsParaEnviar.map(item => [item["idexamen"], item])).values()]    
                return true;
              }else{
                return false;
              }
          } catch (error) {
              return false;
          }
            
          } else if(Ordeneslst.muestratomada == 'N'){
            return true;
          }
          return resultado;
      }

 }