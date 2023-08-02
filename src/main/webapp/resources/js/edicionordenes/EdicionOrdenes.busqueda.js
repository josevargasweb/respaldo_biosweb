// const { Console } = require("console");
//IIFE 
$(function() {    
  function nTipoDocumento(documento){
    let tipoDocumento = 0;
    switch (documento) {
      case 1:
          tipoDocumento = "RUN";
        break;
        case 2:
          tipoDocumento = "PASAPORTE";
        break;
        case 3:
          tipoDocumento = "RECIEN NACIDO";
        break;
        case 4:
          tipoDocumento = "SIN IDENTIFICACION";
        break;
      default:
        break;
    }

    return tipoDocumento;
  }





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
      eventoLimpiar();

      Ordeneslst.idexamen = [];
      Ordeneslst.muestratomada = "N";
      Ordeneslst.tests = [];
      Ordeneslst.testsParaEnviar = [];
      //Ordeneslst.puedeEditarORden = "S";
      Ordeneslst.puedeEditarORden = $("#puedeEditarORden").val();
      Ordeneslst.df_IDPROCEDENCIA = 0;

      //if(data[0].df_IDFICHAESTADOTM == 1){
        //handlerMessageWarning("No se puede editar la orden ya que se encuentra atendida");
      //}else{
        $.ajax({
          type: "get",
          url: gCte.getctx +"/api/portal/editarorden/paciente/" + data[0].dp_IDPACIENTE + "/nOrden/" + data[0].df_NORDEN,
          datatype: "json",
          success: function (mensaje) {
              if(mensaje.status == 200){
                $("#panelBusquedaOrden").collapse('hide');
                $("#btnEditarOrden").removeClass("d-none");
                $("#btnEditarOrden").addClass("d-inline-block");
                $("#btnCancelEdit").removeClass("d-none");
                $("#btnCancelEdit").addClass("d-inline-block");
                $(".disabledForm").prop("disabled", false);
                $("#bo_t_ordenes").DataTable().rows().deselect();

                if(Ordeneslst.puedeEditarORden === 'S' && $("#procedenciaUsuario").length && data[0].df_IDPROCEDENCIA != $("#procedenciaUsuario").val()){
                  $("#btnEditarOrden").addClass("d-none");
                  $("#btnEditarOrden").removeClass("d-inline-block");
                  $("#btnCancelEdit").addClass("d-none");
                  $("#btnCancelEdit").removeClass("d-inline-block");
                  $(".disabledForm").prop("disabled", true);
                }
	
                let datos = mensaje.dato;
                $("#dfOrden").val(data[0].df_NORDEN);
				        $("#fechaOrden").val(datos.fechaorden);
                //datos paciente
                $("#idPaciente").val(data[0].dp_IDPACIENTE);

                $("#tipo_documento").text(nTipoDocumento(datos.idtipodocumento));
                $("#numero_doc").text(nTipoDocumento(datos.idtipodocumento) == "RUN" ? cambiarDatoRut(datos.nrdocumentopaciente) : datos.nrdocumentopaciente);
                $("#nombre_orden").text(datos.nombrepaciente);
                $("#sexo_orden").text(datos.sexodescripcion);
                $("#edad_orden").text(datos.edadpaciente);
                $("#email_orden").text(datos.emailpaciente);
                $("#txtObservacionP").text(datos.observacionpaciente);

                $("#txtObservacionR").text(datos.observacionorden);

                if(datos?.enviarresultadosemail == "S"){
                  $("#chbxRestutToMail").prop( "checked", true );
                  $("#txtEmailP").val("on");
                }else{
                  $("#chbxRestutToMail").prop( "checked", false );
                  $("#txtEmailP").val("off");
                }
                
                //rellena typeahead
                // $("#tipoDeAtencion").val(datos.ctaidtipoatencion);
                $('#tipoDeAtencion').selectpicker('val', datos.ctaidtipoatencion);
                $("#ProcedenciaComboBoxM").selectpicker('val', datos.idprocedencia);
                $("#ServicioComboBoxM").selectpicker('val', datos.idservicio);
                $("#SalaComboBoxM").selectpicker('val', datos.idsalaservicio);
                $("#CamaComboBoxM").val(datos.idcamasala);
                $("#selectPrioridadAtencionPac").selectpicker('val', datos.idprioridadatencion);
                $("#medicoBox").selectpicker('val', datos.idmedico);
                $("#ConvencioComboBoxM").selectpicker('val', datos.idconvenio);
                $("#DiagnosticoComboBoxM").selectpicker('val', datos.iddiagnostico);


                if(datos.ctaidtipoatencion != null){
                  RegistroOden.tipoDeAtencion = datos.ctaidtipoatencion;
                }else{
                  RegistroOden.tipoDeAtencion = -1;
                }
                
                if(datos.idprocedencia != null){
                  RegistroOden.ProcedenciaComboBoxM = datos.idprocedencia;
                }else{
                  RegistroOden.ProcedenciaComboBoxM = -1;
                }
                
                if(datos.idservicio != null){
                  RegistroOden.ServicioComboBoxM = datos.idservicio;
                }else{
                  RegistroOden.ServicioComboBoxM = -1;
                }
                
                if(datos.idsalaservicio != null){
                  RegistroOden.SalaComboBoxM = datos.idsalaservicio;
                }else{
                  RegistroOden.SalaComboBoxM = null;
                }
                
                if(datos.idcamasala != null){
                  RegistroOden.cama = datos.idcamasala;
                }else{
                  RegistroOden.cama = null;
                }
              
                if(datos.idprioridadatencion != null){
                  RegistroOden.selectPrioridadAtencionPac = datos.idprioridadatencion;
                }else{
                  RegistroOden.selectPrioridadAtencionPac = null;
                }
              
                if(datos.idmedico != null){
                  RegistroOden.medicoComboBox = datos.idmedico;
                }else{
                  RegistroOden.medicoComboBox = null;
                }
              
                if(datos.idconvenio != null){
                  RegistroOden.ConvencioComboBoxM = datos.idconvenio;
                }else{
                  RegistroOden.ConvencioComboBoxM = null;
                }
              
                if(datos.iddiagnostico != null){
                  RegistroOden.DiagnosticoComboBoxM = datos.iddiagnostico;
                }else{
                  RegistroOden.DiagnosticoComboBoxM = 1;
                }


                // autoSeleccionar("tipoDeAtencion",datos.ctaidtipoatencion);
                // autoSeleccionar("ProcedenciaComboBoxM",datos.idprocedencia);
                // autoSeleccionar("ServicioComboBoxM",datos.idservicio);
                // autoSeleccionar("SalaComboBoxM",datos.idsalaservicio);
                // autoSeleccionar("CamaComboBoxM",datos.idcamasala);
                // autoSeleccionar("selectPrioridadAtencionPac",datos.idprioridadatencion);
                // autoSeleccionar("medicoBox",datos.idmedico);
                // autoSeleccionar("ConvencioComboBoxM",datos.idconvenio);
                // autoSeleccionar("DiagnosticoComboBoxM",datos.iddiagnostico);
                //fin rellena typeahead
                
                //rellena el objeto si trae dato
                if(datos.idconvenio != null){
                  RegistroOden.ConvencioComboBoxM = datos.idconvenio;
                }

                //rellena el run del medico
                if(datos.idmedico != null){
                  filtrarByIdM(datos.idmedico);
                }

                // console.log(RegistroOden.ServicioComboBoxM,'servicio');
                // console.log(RegistroOden.tipoDeAtencion,'tipo atencion');
                // console.log(RegistroOden.ProcedenciaComboBoxM,'procedencia');
                // console.log(RegistroOden.selectPrioridadAtencionPac,'prioridad');
                // console.log(RegistroOden.medicoComboBox,'medico');
                // console.log(RegistroOden.ConvencioComboBoxM,'convenio');
                // console.log(RegistroOden.DiagnosticoComboBoxM,'diagnostico');
                //rellena los examanes
                eventoAddMultiple(datos.examenes);
                
                let arrayExamenes = [];
                datos.examenes.forEach(element => {
                    arrayExamenes.push(element.idexamen);
                });
                
                Ordeneslst.idexamen = arrayExamenes;
                
                Ordeneslst.muestratomada = datos.muestratomada;
                
                Ordeneslst.tests = [...datos.tests];

                Ordeneslst.puedeEditarORden = data[0].puedeEditarORden;

                Ordeneslst.df_IDPROCEDENCIA = data[0].df_IDPROCEDENCIA;

                let patologia = datos.patologias;
                
                let tr = "";
                patologia.forEach((element,index) => {
                  tr += `<tr>
                          <td>${index + 1}</td>
                          <td>${element.patologia}</td>
                          <td>${element.observacion}</td>
                        </tr>`;
                });

                $("#tableBodyDiagnosticoPaciente").html(tr);
                $(".selectpicker").selectpicker("refresh");
              }
          },
          error: function (msg) {
              console.log(msg);
          }
      });
      // }
    }

    function opcionesDisponibles(row){
      return '\<div class="row">\
                  <a href="#" class="btn btn-sm btn-clean btn-icon" title="Datos Paciente" data-toggle="modal" data-target="#ModalDatosPaciente" onclick=\'MostrarDatosPaciente(' + row['df_NORDEN'] + ',' + row['dp_IDPACIENTE'] +') \'>\
                    <i class="fas fa-user-md"></i><span class="nav-text"></span>\
                  </a>\
                 <a href="#" class="btn btn-sm btn-clean btn-icon" title="Orden/Exámenes" data-toggle="modal" data-target="#ModalDatosOrden" onclick=\'MostrarDatosOrden(' + row['df_NORDEN'] + ') \'>\
                      <i class="fa fa-search" aria-hidden="true"></i><span class="nav-text"></span>\
                  </a>\
            </div>'
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
});