<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <jsp:include page="common/Styles_1.jsp"/>
        <jsp:include page="common/FileInputStyles.jsp" />
        
        <link href="<%=request.getContextPath()%>/resources/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
        <title>BiosLIS | MB - Captura de resultados</title>
    </head>
    <body>
        <div class="row menu-container">
            <div class="col-md-6">
                <jsp:include page="common/Menu_dinamico.jsp" />
            </div>
            <div class="col-md-6">
                <jsp:include page="common/Header_1.jsp" />
            </div>
        </div>
        <jsp:include page="common/AlertaSession.jsp"/>
        

        <div class="container container-main-content container-captura">
            <input type="hidden" id="idSeccion" value="${idSeccion}" /> <input
              type="hidden" id="idUsuarioConectado" value="${idUsuarioConectado} " />
              <input type="hidden" id="editaResultadosCriticos" name="editaResultadosCriticos" value="${perfilUsuario.cpuEditaresultadoscriticos}" />
        
            <div class="row d-flex justify-content-center">
              <div class="accordion col-12">
                <div class="row">
                  <div class="col-12 pl-0">
                    <!-- ********************************* ini contenido -> *************************************************************************************-->
                    <div class="card card-rounded" id="panelSuperior">
                      <div class="card-header mb-0">
                        <!-- ********************************* panel Seleccion de orden *************************************************************************************-->
                        <div class="card-title d-flex justify-content-between pt-1 pb-1">
                          <h3 class="icon-accordion mb-0 btn-block text-left pl-0 col-2"
                            data-toggle="collapse"
                            data-target="#accordionPanelSeleccionOrden">
                            Selecci&oacute;n de Orden</h3>
                            <!--
                            <div class="d-flex justify-content-end">
                                <label class="text-capt">Secci&oacute;n</label>&nbsp;
                                <div class="col-10 pr-0">
                                  <select
                                    id="seccionSelect"
                                    class="form-control form-control-sm selectpicker"
                                  >
                                    <option value="">TODOS</option>
                                  </select>
                                </div>
                            </div>
                            -->
                          <div class="col-auto">
                            <label class="text-capt">Cultivo</label> <input
                              type="checkbox" id="estadoCultivo" />
                          </div>
                          <div class="col-auto">
                            <label class="text-capt">En proceso</label> <input
                              type="checkbox" id="estadoEnProceso" />
                          </div>
                          <div class="col-auto">
                            <label class="text-capt">Pendientes</label> <input
                              type="checkbox" checked id="estadoPendiente" />
                          </div>
                          <div class="card-buttons col-1 d-flex justify-content-end">
                            <a id="btnBuscarOrden" class="navi-link mr-0"> <span
                              class='symbol symbol-30 symbol-circle'> <span id=""
                                class='symbol-label bg-hover-blue hoverIcon circuloBuscarPaciente'>
                                  <i id=""
                                  class="fas fa-search text-blue text-primary iBuscarPaciente"></i>
                              </span>
                            </span>
                            </a>
                          </div>
                          <div class="col-2 justify-content-right">
                            <div class="float-right">
                              <button id="btnRefreshBuscarOrden" type="button"
                                class="btn btn-sm btn-secondary mr-2">
                                <i class="fas fa-redo-alt" aria-hidden="true"></i>
                              </button>
                              <label class="checkbox checkbox-primary mb-0"> <input
                                id="idChkAutorefresh" name="exitus" type="checkbox">Automática
                                <span></span>
                              </label>
        
                            </div>
                          </div>
        
                        </div>
                      </div>
                      <div id="accordionPanelSeleccionOrden"
                        class="collapse container-content show"
                        data-parent="#panelSuperior" >
                        <div class="containerTable pl-2 pr-2 no-th-first hidden">
                            <span id="dt_registros_orden" class="p-3"></span>
                            <div class="card card-2 mt-3 mb-3">
                                <div class="card-body p-0">
                                  <!-- FORMULARIO FILTRO -->
                                    <div class="col-12 pl-0 pr-0 overflow-hidden">
                                            <table
                                            class="table table-hover table-striped nowrap display compact-xs"
                                            id="bo_t_ordenes"
                                            style="width: 100%"
                                                >
                                            <thead>
                                            <tr>
                                                <th>Orden</th>
                                                <th>Fecha</th>
                                                <th>Paciente</th>
                                                <th>Sexo</th>
                                                <th>Edad</th>
                                                <th>Tipo de Atenci&oacute;n</th>
                                                <th>Procedencia</th>
                                                <th>Servicio</th>
                                                <th>Urgente</th>
                                                <th>Acciones</th>
                                                <th></th>
                                                <th>11</th>
                                                <th>12</th>
                                                <th>13</th>
                                                <th>14</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                  <!-- FIN TABLA FILTRO -->
                                </div>
                            </div>
                        </div>
                      </div>
                    </div>
        
                    <!-- ********************************* panel Ex&aacute;menes / Datos pacientes ***************************************************-->
                    <div class="row">
                        <div class="col-6 pr-1">
                            <div class="card h-100" id="panelExamanes">
                                <div class="card-header mb-0">
                                    <div class="card-title d-flex justify-content-between pt-1 pb-1">
                                        <h3 class="mb-0 btn-block text-left pl-0 col-3">Ex&aacute;menes</h3>
                                    </div>
                                </div>
                                <div class="card-body pt-1">
                                    <div class="containerTableExamen col-12 pl-0 pr-0 hidden" >
                                        <p id="nroExamenes" class="ml-5"></p>
                                        <div class="col-12 p-0 d-flex">
                                            <div class="col-4 d-flex align-items-center">
                                              <input type="checkbox" name="" id="seleccionaTodasLosExamenes" class="checkable mr-1" /> Seleccionar Todo
                                            </div>
                                            <div class="col-6 d-flex align-items-center">
                                              <span id="dt_registros_examenes"></span>
                                            </div>
                                        </div>
                                        <div class="card card-2 mt-3">
                                            <div class="card-body p-0">
                                                <table class="table table-hover table-striped nowrap compact-xs"
                                                    id="examenesOrdenesDatatable" style="width: 100%; line-height: 1;">
                                                    <thead>
                                                        <tr>
                                                            <th>Sel</th>
                                                            <th>C&oacute;digo</th>
                                                            <th>Examen</th>
                                                            <th>Muestra</th>
                                                            <th>Sitio anat&oacute;mico</th>
                                                            <th>Estado</th>
                                                            <th>Fecha Siembra</th>
                                                            <th>Fecha Resiembra</th>
                                                            <th>Urgente</th>
                                                            <th>Acciones</th>
                                                            <th>10</th>
                                                            <th>11</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                                <!-- fin tabla-->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-6 pl-1">
                            <div class="card card-rounded h-100">
                                <div class="card-header" id="">
                                    <div class="card-title d-flex justify-content-between pt-1 pb-1">
                                        <h3 class="mb-1 btn-block text-left pl-0 col-md-2 col-xl-5">Datos</h3>
                                        <div class="col-md-8 col-xl-7 d-flex justify-content-end">
                                            <!-- Botones agregados x Marco Caracciolo -->
                                            <button id="btnVerOrden"  class="btn btn-blue-primary btn-normal font-weight-bold mr-2 col-md-8 col-xl-5 w-100">
                                                <i class="fas fa-user-md"></i>Datos Paciente
                                            </button>
                                            <button id="btnRegDocOrden" class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 col-md-8 col-xl-6 w-100">
                                                  <img src="<%=request.getContextPath()%>/resources/img/add_files.png" width="20" style="filter: brightness(0) invert(1)"/>Visualizar Documento
                                            </button>
                                            <!-- end -->
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body" id="panelPacienteOrden">
                                    <div class="row">
                                        <div class="col-7">
                                            <div class="row ">
                                            <div class='col-12 mb-2 form-row line-height-1'>
                                                <div class="col-lg-6 col-xl-5 form-group mb-0">
                                                <label class="col-form-label pb-0 col-12 pl-0">Nombre</label>
                                                <span id="txtNombrePaciente"></span>
                                                </div>
                                                <div class="col-lg-6 col-xl-3 form-group  mb-0">
                                                <label class="col-form-label pb-0 col-12 pl-0">Sexo</label>
                                                <span id="txtSexoPaciente"></span>
                                                </div>
                                                <div class="col-lg-5 col-xl-3 form-group mb-0">
                                                <label class="col-form-label pb-0 col-12 pl-0 pr-0">Edad</label>
                                                <span id="txtEdadPaciente"></span>
                                                </div>
                                                <div class="col-lg-7 col-xl-4 form-group mt-1 mb-0">
                                                <label class="col-form-label pb-0 col-12 pl-0">Tipo
                                                    de Atención</label> <span id="txtTipoAtencion"></span>
                                                </div>
                                                <div class="col-lg-6 col-xl-4 form-group mt-1 mb-0">
                                                <label class=" col-form-label pb-0 col-12 pl-0">Servicio</label>
                                                <span id="txtLocalizacion"></span>
                                                </div>
                                                <div class="col-lg-6 col-xl-4 form-group mt-1 mb-0">
                                                <label class=" col-form-label pb-0 col-12 pl-0">Procedencia </label>
                                                <span id="txtProcedencia"></span>
                                                </div>

                                            </div>
                                            </div>
                                        </div>
                                        <div class="col-5">
                                            <div class="row">
                                            <div class='col-12 mb-2 form-row line-height-1'>
                                                <div class="col-lg-12 col-xl-5 pl-0-pr-0">
                                                <div class="col-12 form-group mb-0">
                                                    <label class="col-form-label pb-0 col-12 pl-0">#Orden</label>
                                                    <span id="txtNroOrden"></span>
                                                </div>
                                                <div class="col-12 form-group mb-0">
                                                    <label class="col-form-label pb-0 col-12 pl-0">Fecha</label>
                                                    <span id="txtFechaOrden"></span>
                                                </div>
                                                </div>
                                                <div
                                                class="col-lg-12 col-xl-6 form-group  mb-0 mt-2 d-flex align-items-end">
                                                <!-- ---------- ini botonera -> --------------------------------------------------------------->
                                                <div class="row">
                                                    <div class="col-lg-2 col-xl-3 ">
                                                    <button id="btnFirstOrder"
                                                        class="btn btn-light-primary font-weight-bold btn-capt">
                                                        <i class="fas fa-fast-backward"></i>
                                                    </button>
                                                    </div>
                                                    <div class=" col-lg-2 col-xl-3 ">
                                                    <button id="btnPrevOrder"
                                                        class="btn btn-light-primary font-weight-bold btn-capt">
                                                        <i class="fas fa-step-backward"></i>
                                                    </button>
                                                    </div>
                                                    <div class="col-lg-2 col-xl-3 ">
                                                    <button id="btnNextOrder"
                                                        class="btn btn-light-primary font-weight-bold btn-capt">
                                                        <i class="fas fa-step-forward"></i>
                                                    </button>
                                                    </div>
                                                    <div class=" col-lg-2 col-xl-3 ">
                                                    <button id="btnLastOrder"
                                                        class="btn btn-light-primary font-weight-bold btn-capt">
                                                        <i class="fas fa-fast-forward"></i>
                                                    </button>
                                                    </div>
                                                    <div class="col-xl-12 col-md-9 mt-2 pr-0">
                                                    <div class="progress">
                                                        <div id="order-progress" class="progress-bar bg-success" role="progressbar" style="width:0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="0"></div>
                                                    </div>
                                                    <div class="col-12">
                                                        <label class=" col-form-label pb-0 col-12 pl-0 text-center"><span id="count_orden_inicio">0</span> de <span id="count_orden_fin">0</span></label>
                                                    </div>
                                                    </div>
                                                </div>
                                                <!-- ---------- <- Fin botonera ---------------------------------------------------------------->
                                                </div>
                                            </div>
                                            </div>
                                        </div>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- ********************************* panel tests y resultados ***************************************************-->                    
                    <div id="panelInferior" class="card mt-3 mb-4">
                        <div class="card-header mb-0" id="headingRegistroPaciente">
                            <div class="card-title d-flex justify-content-between pt-1 pb-1">
                                <h3 class="card-label col-3 pl-0">Tests y Resultados</h3>
                                <div class="card-buttons col-8">
                                    <div class="container">
                                        <div class="row justify-content-end">
                                            <div class="col-11">
                                                <div class="row justify-content-end">
                                                    <div class="col-md-4 col-xl-3 d-flex cont-capt">
                                                        <div class="mb-1 d-flex align-items-end justify-content-center w-100">
                                                            <button id="btnFirmaResultados" type="button" class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100">
                                                            <img src="<%=request.getContextPath()%>/resources/img/firmar.png" style="filter: brightness(0) invert(1);" width="20">
                                                            Firmar resultados
                                                            </button>
                                                        </div>
                                                    </div>
                                                            <!--
                                                    <div class="col-md-4 col-xl-4 d-flex">
                                                        <div class="mb-1 d-flex align-items-end justify-content-center w-100">
                                                            <button id="btnRetiraFirma" type="button" class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100">
                                                            <img src="<%=request.getContextPath()%>/resources/img/paper-eraser.jpg" style="filter: brightness(0) invert(1);" width="20">
                                                            Retirar firma
                                                            </button>
                                                        </div>
                                                    </div>
                                                            -->
                                                    <div class="col-md-4 col-xl-4 d-flex">
                                                        <div class="mb-1 d-flex align-items-end justify-content-center w-100">
                                                            <button id="btnAutorizaResultados" type="button" class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100">
                                                            <img src="<%=request.getContextPath()%>/resources/img/confirm-file.png" style="filter: brightness(0) invert(1);" width="20">
                                                            Autorizar resultados
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-1 d-flex">
                                                <div class="dropdown">
                                                    <a class="navi-link mr-0 d-flex align-items-center"
                                                      data-toggle="dropdown" aria-expanded="false">
                                                        <i class="fas fa-bars text-blue text-primary iContador" style="font-size: 3rem"></i>
                                                    </a>
                                                    <div class="dropdown-menu dropdown-menu-right">
                                                        <a id="btnRetiraFirma" class="dropdown-item dropdown-none" href="#">
                                                            <img src="<%=request.getContextPath()%>/resources/img/paper-eraser.jpg" width="20"/>&nbsp;Retirar firma</a>
                                                        <a id="btnImprimirCR" class="dropdown-item dropdown-none"  data-toggle="modal" data-target="#exampleModal" href="#">
                                                            <i class="la la-print"></i>Pre- Informe</a>

                                                    </div>
                                                </div>
                                              </div>           
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body no-gutters">
                            <div class="col-12">
                                <div class="row">
                                    <div class="col-6">
                                        <div class="containerTableTest col-12 pl-0 pr-0 hidden">
                                            <div class="col-12 p-0 d-flex">
                                                <div class="col-4 d-flex align-items-center">
                                                  <input type="checkbox" name="" id="seleccionaTodasLosResultados" class="checkable mr-1" /> Seleccionar Todo
                                                </div>
                                                <div class="col-6 d-flex align-items-center">
                                                  <span id="dt_registros_tests"></span>
                                                </div>
                                            </div>
                                            <div class="card card-2 mt-3">
                                                <!-- tabla-->
                                                <table
                                                class="table table-hover table-striped nowrap compact-xs"
                                                id="resultadosExamenesOrdenesDatatable"
                                                style="width: 100%; line-height: 1;">
                                                    <thead>
                                                        <tr>
                                                            <th>Sel</th>
                                                            <th>Test</th>
                                                            <th>Resultado</th>
                                                            <th>Estado</th>
                                                            <th>Microbiolog&iacute;a</th>
                                                            <th>Acciones</th>
                                                            <th>6</th>
                                                            <th>7</th>
                                                            <th>8</th>
                                                            <th>Limpiar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="row col-12 justify-content-end">
                                            <div class="float-right">
                                                <button id="btnRefreshBuscarResultado" type="button" class="btn btn-sm btn-secondary mr-2">
                                                    <i class="fas fa-redo-alt" aria-hidden="true"></i>
                                                </button>
                                                <label class="checkbox checkbox-primary mb-0">
                                                    <input id="idChkAutorefreshres" name="exitus" type="checkbox" />Automática
                                                    <span></span>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-12  d-flex justify-content-end pt-1 pb-1 mt-3">
                                            <div class="btn-group btn-group-toggle d-flex w-100 justify-content-between" data-toggle="buttons">
                                                <button type="button" class="btn btn-outline-blue col-2 active"> 
                                                    <input type="radio" name="cultivoChck" id="option1" value="CULTIVO" checked> Cultivos
                                                </button>
                                                <button type="button" class="btn btn-outline-blue col-2"> 
                                                    <input type="radio" name="cultivoChck" id="option2" value="HONGOS"> Hongos
                                                </button>
                                                <button type="button" class="btn btn-outline-blue col-2"> 
                                                    <input type="radio" name="cultivoChck" id="option3" value="TINCIÓN"> Tinción
                                                </button>
                                                <button type="button" class="btn btn-outline-blue col-2"> 
                                                    <input type="radio" name="cultivoChck" id="option4" value="OTROS"> Otros
                                                </button>
                                            </div>
                                        </div>
                                        <div class="row col-12">
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-12"><strong>Test <span id="testSelectedText"></span></strong></label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-3">Tipo de muestra</label>
                                                    <div class="col-lg pl-0">
                                                        <select id="idTestCanvasActionsSample" class="form-control" disabled>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-3">Sitio anat&oacute;mico</label>
                                                    <div class="col-lg pl-0">
                                                        <select id="idTestCanvasActionsBodyPart" class="form-control" disabled>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group row justify-content-between">
                                                    <div class="col-5">
                                                        <div class="d-flex align-items-end justify-content-center">
                                                            <button id="btnAntibiograma" type="button" class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100">
                                                                <i class="fas fa-bacterium"></i>
                                                                Antibiogramas
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div class="col-5">
                                                        <!--
                                                        <div class="d-flex align-items-end justify-content-center">
                                                            <button id="idTestCanvasActionsLabels" type="button" class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100">
                                                                <img src="<%=request.getContextPath()%>/resources/img/barcode.png" width="17">
                                                                Imprimir etiquetas
                                                            </button>
                                                        </div>-->
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <div class="col-4">
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="checkbox" value="" id="fechaSiembra" disabled>
                                                            <label class="form-check-label" for="defaultCheck1">
                                                                &nbsp;Fecha de Siembra
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder="" id="idTestCanvasActionsSeedingDate" disabled/>
                                                    </div>
                                                    <div class="col-lg-3 pl-0">
                                                        <input class="form-control" placeholder="" id="idTestCanvasActionsSeedingTime"  disabled/>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <div class="col-4">
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="checkbox" value="" id="fechaResiembra" disabled>
                                                            <label class="form-check-label" for="defaultCheck1">
                                                                &nbsp;Fecha de Resiembra
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder="" id="idTestCanvasActionsReseedingDate" type="" disabled/>
                                                    </div>
                                                    <div class="col-lg-3 pl-0">
                                                        <input class="form-control" placeholder="" id="idTestCanvasActionsReseedingTime" type="" disabled/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- ********************************* biograma -> *************************************************************************************-->
                    <div class="card card-rounded d-none" id="panelBiograma">
                        <div class="card-header mb-0">
                            <div class="card-title d-flex justify-content-between pt-1 pb-1">
                                <h3 class="icon-accordion mb-0 btn-block text-left pl-0 col-3"
                                data-toggle="collapse"
                                data-target="#accordionPanelAntibiograma">
                                Organismo y Antibiograma</h3>
                            </div>
                        </div>
                        <div id="accordionPanelAntibiograma"
                            class="collapse container-content show"
                            data-parent="#panelBiograma">
                            <div class="card-body d-flex pb-0">
                                <div class="col-12 pl-0">
                                    <div class="form-group row">
                                        <div class="col-4">
                                            <h3>Origen: <span id="origenAntibioText"></span></h3>
                                            <button id="idAntibiogramsAddResistanceMethodOptional" type="button"
                                            class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2  disabledForm" data-toggle="modal"
                                            data-target="#AntibiogramaAddOpcional" disabled>Añadir Antibiotico
                                            opcional</button>
                                            <button id="idTestCanvasAntibiogramsAddAntibiotic" type="button"
                                            class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 disabledForm" disabled>Añadir antibiotico</button>
                                        </div>
                                        <div class="col-4 pl-0">
                                            <div class="col-10">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-4">Aislamiento</label>
                                                    <div class="col-4 pl-0">
                                                        <select id="idTestCanvasAntibiogramIsolatedNumber" class="form-control">
                                                            <option value="1">1</option>
                                                            <option value="2">2</option>
                                                            <option value="3">3</option>
                                                            <option value="4">4</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-8">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-5">
                                                        Microorganismo
                                                    </label>
                                                    <div class="col pl-0">
                                                        <select id="idTestCanvasAntibiogramMicroorganism" class="form-control">
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-8">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-5">
                                                        Antibiograma
                                                    </label>
                                                    <div class="col pl-0">
                                                        <select id="idTestCanvasAntibiogramType" class="form-control" disabled>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-4 pl-0">
                                            <div class="col-12">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-5">
                                                        Recuento de colonias
                                                    </label>
                                                    <div class="col-lg pl-0">
                                                        <select id="idTestCanvasAntibiogramColoniesCount" class="form-control" disabled>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-5">
                                                        Tipo de infección
                                                    </label>
                                                    <div class="col-lg pl-0">
                                                        <select id="idTestCanvasAntibiogramInfectionType" class="form-control" disabled>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-6">
                                            <table
                                            class="table table-hover table-striped nowrap compact-xs"
                                            id="idDataTargetFull"
                                            style="width: 100%; line-height: 1;">
                                                <thead>
                                                    <tr>
                                                        <th>Antibiotico</th>
                                                        <th>CIM</th>
                                                        <th>Diámetro (KB)</th>
                                                        <th>E-test</th>
                                                        <th>Interpretación</th>
                                                        <th>Incluir en Informe</th>
                                                        <th>Eliminar</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="col-6">
                                            <table
                                            class="table table-hover table-striped nowrap compact-xs"
                                            id="antibiogramaMetodoResistencia"
                                            style="width: 100%; line-height: 1;">
                                                <thead>
                                                    <tr>
                                                        <th>Marcador de Resistencia</th>
                                                        <th>Resultado</th>
                                                        <th>Eliminar</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                        </div>
                                       <div class="col-12 container-button-anti">
                                            <button type="button" id="btnEditarAntibiograma" class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2">Guardar</button>
                                       </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- ********************************* <-fin contenido *************************************************************************************-->
                  </div>
                </div>
              </div>
        
            </div>
        </div>

        <!-- **************************** Modal Buscar Ordenes ******************************************-->
            <div class="modal fade" id="buscarOrdenesModal" tabindex="-1"
            role="dialog" aria-labelledby="buscarOrdenesModal" aria-hidden="true" >
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <!--<h3 id="" class="text-center" id="buscarOrdenesModalLabel">
                              B&uacute;squeda Detallada
                            </h3>-->
                            <jsp:include page="desarrollo/Bios_OrdenesMicrobiologiaBuscador.jsp" />
                        </div>
                    </div>
                </div>
            </div>
        <!-- **************************** Fin Modal Buscar Ordenes ****************************************** -->
        <!-- **************************** Modal Eventos Orden ******************************************                    -->
        <jsp:include page="microbiologia/OrderCanvasEvents.jsp" />    
        <!--<div class="modal fade" id="eventosOrdenModal" tabindex="-1"
            role="dialog" aria-labelledby="eventosOrdenModal" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-xl"
                role="document">
                    <div class="modal-content">
                        
                        <div class="modal-body">
                        <h3 id="" class="text-center" id="eventosOrdenModalLabel">Eventos
                            de la Orden</h3>
                        
                        <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
                        -->
        <!-- **************************** Fin Modal Eventos Orden ****************************************** -->
        	<!-- **************************** Modal Test Opcionales******************************************                    -->
                <div class="modal fade" id="opcTestExamenModal" tabindex="-1"
                role="dialog" aria-labelledby="opcTestExamenModal" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-xl"
                    role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <h3 id="" class="text-center" id="obsExamenModalLabel">
                                Tests Opcionales
                            </h3>
                            <jsp:include page="microbiologia/ExamCanvasOptionalTests.jsp" />
                        </div>
                    </div>
                </div>
            </div>
        <!-- **************************** Modal Test Opcionales******************************************                    -->
          <!-- **************************** Modal Paciente ******************************************                    -->
            <div class="modal fade" id="datosPacienteModal" tabindex="-1"
            role="dialog" aria-labelledby="datosPacienteModal" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-lg"
                    role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <h3 id="" class="text-center" id="exampleModalLabel">Datos
                            Paciente</h3>
                            <jsp:include page="microbiologia/OrderCanvasData.jsp" />
                            <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <!-- **************************** Modal Paciente******************************************                    -->
        <!-- **************************** Modal Observaciones Examen ******************************************                    -->
            <div class="modal fade" id="obsExamenModal" tabindex="-1" role="dialog"
            aria-labelledby="obsExamenModal" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-lg"
                    role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <h3 id="" class="text-center" id="obsExamenModalLabel">Observaciones
                            del Examen</h3>
                            <jsp:include page="microbiologia/ExamCanvasObservations.jsp" />
                            <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <!-- **************************** Modal Observaciones Examen  ******************************************                    -->
        <!-- **************************** Modal Notas Examen ******************************************                    -->
            <div class="modal fade" id="notasExamenModal" tabindex="-1"
            role="dialog"  aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-xl"
                    role="document">
                    <div class="modal-content">
                    <div class="modal-body">
                        <h3 id="" class="text-center" id="notasExamenModalLabel">Notas
                        del Examen</h3>
                        <jsp:include page="modal/ModalExamCanvasNotes.jsp" />
                    </div>
                    </div>
                </div>
            </div>

        <!-- **************************** Modal Notas Examen  ******************************************                    -->
          <!-- **************************** Modal Test Detalles******************************************                    -->
            <div class="modal fade" id="detallesTestModal" tabindex="-1"
            role="dialog" aria-labelledby="detallesTestModal" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-lg"
                    role="document">
                    <div class="modal-content">
                    <div class="modal-body">
                        <h3 id="" class="text-center" id="detallesTestLabel">Detalles
                        Test</h3>
                        <jsp:include page="microbiologia/TestCanvasData.jsp" />
                        <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                        <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
<!-- **************************** Fin Modal Test Detalles******************************************                    -->
<!-- **************************** Modal Notificaciones - >************************************************************************-->
    <div id="notificacionResultadosModal"
        class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
        aria-labelledby="" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-body">
                    <h3 class="text-center">Notificación de Resultados Críticos <span id="idTestCanvasNotificationsabr"></span></h3>
                    <jsp:include page="modal/ModalTestNotifications.jsp" />
                </div>
            </div>
        </div>
    </div>
<!-- **************************** <-Fin Modal Notificaciones ************************************************************************-->
      <!-- **************************** Modal Test Eventos******************************************                    -->
  <div class="modal" id="eventosTestModal" tabindex="-1" role="dialog"
  aria-labelledby="eventosTestModal" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-xl"
    role="document">
    <div class="modal-content">
      <div class="modal-body">
        <h3 id="" class="text-center" id="eventosTestLabel">Eventos
          Test</h3>
        <jsp:include page="microbiologia/TestCanvasEvents.jsp" />
        <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
          <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
        </div>
      </div>
    </div>
  </div>
</div>

  <!-- **************************** Modal Autorizaciones - >************************************************************************-->

  <div id="AutorizacionExamenesModal"
    class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
    aria-labelledby="" aria-hidden="true">
    <jsp:include page="modal/ModalExamCanvasAuth.jsp" />
  </div>
  <!-- **************************** <-Fin Modal Autorizaciones ************************************************************************-->


      <!-- **************************** Modal Desautorizar - >************************************************************************-->

  <div id="desautorizarResultadosModal"
  class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
  aria-labelledby="" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        <h3 class="text-center">Quitar autorización</h3>
        <p>¿Se va a retirar la autorización de exámenes ya entregados, ¿Está seguro que desea continuar?</p>
      </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-secondary btn-no" data-dismiss="modal">No</button>
        <button type="button" class="btn btn-primary btn-si">Si</button>
      </div>
    </div>
  </div>
</div>
<!-- **************************** <-Fin Modal Desautorizar ************************************************************************-->

<!-- **************************** Modal Antecedentes Pacientes ************************************************************************-->
    <jsp:include page="modal/ModalAntecedentesPac.jsp" />
    <!-- **************************** Fin Modal Antecedentes Pacinetes ************************************************************************-->
   <!-- **************************** Modal Test Dcoumentos******************************************                    -->
    <jsp:include page="modal/ModalDocumentosExamen.jsp" />
    <!-- **************************** Modal Test Orden******************************************                    -->
    <jsp:include page="modal/ModalDocumentosSoloOrden.jsp" />

<div class="modal fade" id="TestCanvasLabels" tabindex="-1" role="dialog" aria-labelledby="TestCanvasLabels" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Asignación de Glosas</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <i aria-hidden="true" class="ki ki-close"></i>
                </button>
            </div>
            <div class="modal-body">
                <div id="TestCanvasLabelsListBoxDiv" class="form-group row mt-5 mx-auto col-8">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light-primary font-weight-bold" onclick="printLabels()">Imprimir</button>
            </div>
        </div>
    </div>
</div>




<div class="modal fade" id="TestCanvasAntibiogramsAddAntibiotic" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="TestCanvasAntibiogramsAddAntibiotic" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <h5 class="modal-title" id="exampleModalLabel">Añadir Antibióticos</h5>
                <jsp:include page="microbiologia/AntibioticoMicrobiologiaTable.jsp" />
                <button type="button" class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2" data-dismiss="modal">Guardar</button>
            </div>
            <!-- <div class="modal-footer">
                <button type="button" class="btn btn-light-primary font-weight-bold" data-dismiss="modal" onclick="saveAddAntibiotics()">Guardar</button>
            </div> -->
        </div>
    </div>
</div>

<div class="modal fade" id="AntibiogramaAddOpcional" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="TestCanvasAntibiogramsAddAntibiotic" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <h5 class="modal-title" id="exampleModalLabel">Antibióticos Opcionales</h5>
                <div class="col-6">
                <table
                class="table table-hover table-striped nowrap"
                id="idDataTargetOpcional"
                style="width: 100%; line-height: 1;">
                    <thead>
                        <tr>
                            <th>Antibiotico</th>
                            <th>Incluir</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
                <button type="button" class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2" data-dismiss="modal">Guardar</button>
            </div>
            <!-- <div class="modal-footer">
                <button type="button" class="btn btn-light-primary font-weight-bold" data-dismiss="modal" onclick="saveAddAntibiotics()">Guardar</button>
            </div> -->
        </div>
    </div>
</div>

  <!-- **************************** Modal Glosa Multiple************************************************************************-->
  <jsp:include page="modal/ModalGlosaMultiple.jsp" />
  <!-- **************************** Fin Modal Glosa Multiple************************************************************************-->
<!-- **************************** Modal Linea Tiempo  ******************************************                    -->
    <div
      class="modal fade"
      id="lineaTiempoModal"
      role="dialog"
      aria-labelledby="lineaTiempoModal"
      aria-hidden="true"
    >
      <div
        class="modal-dialog modal-dialog-centered"
        style="max-width: 90% !important"
        role="document"
      >
        <div class="modal-content">
          <div class="modal-body">
            <h3 class="text-center" id="exampleModalLabel">Linea de Tiempo</h3>
            <jsp:include page="modals/ModalCapturaResultadosLineaTiempo.jsp" />
            <div
              class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse"
            >
              <button
                type="button"
                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                data-dismiss="modal"
              >
                Salir
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
            
    <!-- Modal aceptacion -->
    <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog">
     <div class="modal-dialog" role="document">
       <div class="modal-content">
       <div class="modal-header">
         <h5 class="modal-title titulo-color"></h5>
         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
         <span aria-hidden="true">&times;</span>
         </button>
       </div>
       <div class="modal-body">

       </div>
       <div class="modal-footer">
         <button type="button" class="btn btn-secondary btn-no" data-dismiss="modal">No</button>
         <button type="button" class="btn btn-primary btn-si">Si</button>
       </div>
       </div>
     </div>
     </div>
            
<!-- **************************** Fin Modal Test Eventos******************************************                    -->
        <div class="container mt-5">
            <!-- <jsp:include page="microbiologia/OrdersSearch.jsp"/> -->
            <!-- <jsp:include page="microbiologia/ListsCanvas.jsp"/> -->
            <div>
                <!-- <jsp:include page="microbiologia/ExamCanvas.jsp"/> -->
                <!-- <jsp:include page="microbiologia/TestCanvas.jsp"/> -->
                <!-- <jsp:include page="microbiologia/AntibiogramCanvas.jsp"/> -->
            </div>
        </div>
        
        <jsp:include page="common/Scripts.jsp"/>
        <jsp:include page="common/FileInputScripts.jsp" />

        <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/InicioFunciones.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/ModuloFecha.js"></script>
        <script
        src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.min.js"></script>
      <!--end::Global Theme Bundle-->
      <script src="https://unpkg.com/imask"></script>
      <script
        src="https://cdn.datatables.net/scroller/2.0.6/js/dataTables.scroller.min.js"></script>
      <script
        src="https://cdn.datatables.net/select/1.4.0/js/dataTables.select.min.js"></script>
        <script
        src="<%=request.getContextPath()%>/resources/js/jquery.masknumber.js"></script>
    
        <script
        src="<%=request.getContextPath()%>/resources/datetimepicker/moment.min.js"></script>
        <script
        src="<%=request.getContextPath()%>/resources/datetimepicker/bootstrap-datetimepicker.min.js"></script>
        <script
	src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
        
        <!--<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboActualizado.js"></script>
        -->
        <script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboMicrobiologia.js"></script>

      

    <script
    src="<%=request.getContextPath()%>/resources/js/microbiologia/trio-data.js"></script>
    <script
    src="<%=request.getContextPath()%>/resources/js/Dto/CfgPaneles.js"></script>
    <script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloMuestras.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/common/FileInputBiosbo.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/OptionalTestContent.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.upload.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/EventosCapturaResultados.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.init.js"></script>
        <script
        src="<%=request.getContextPath()%>/resources/js/Dto/CfgGlosas_microbiologia.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Dto/DominiosClases.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.ordenes.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.examenes.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.testResultados.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modals/ModalGlosaMultipleTest_microbiologia.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.antibiograma.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.formulario.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.autorizacion.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/templates.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modals/ModalAntecedentesPaciente.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.lineaTiempo.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/modals/ModalTestNotifications.js"></script>
    <script  src="<%=request.getContextPath()%>/resources/js/portalEstadistica/xlsx.mini.js"></script>

        

    </body>
</html>
