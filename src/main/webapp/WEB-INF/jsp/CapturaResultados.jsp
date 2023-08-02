<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>BIOS-LIS | Captura de Resultados</title>
    <!--begin::Page Vendors Styles(used by this page)-->

    <!--end::Page Vendors Styles-->
    <!--begin::Global Theme Styles(used by all pages)-->

    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.18/css/bootstrap-select.min.css"
      integrity="sha512-ARJR74swou2y0Q2V9k0GbzQ/5vJ2RBSoCWokg4zkfM29Fb3vZEQyv0iWBMW/yvKgyHSR/7D64pFMmU8nYmbRkg=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />

    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
      crossorigin="anonymous"
    />
    <link
      rel="stylesheet"
      href="<%=request.getContextPath()%>/resources/css/tables.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"
      integrity="sha512-mSYUmp1HYZDFaVKK//63EcZq4iFWFjxSL+Z3T/aCt4IO9Cejm03q3NKKYN6pFQzY0SBOr8h+eCIAZHPXcpZaNw=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <jsp:include page="common/Styles_1.jsp" />
    <jsp:include page="common/FileInputStyles.jsp" />
    <link href="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css" rel="stylesheet" type="text/css" />
  </head>

  <body>
    <!--end::Layout Themes-->
    <div class="row menu-container">
      <div class="col-md-6">
        <jsp:include page="common/Menu_dinamico.jsp" />
      </div>
      <div class="col-md-6">
        <jsp:include page="common/Header_1.jsp" />
      </div>
    </div>
    <!-- ********************************* Inicio contenedor -> *************************************************************************************-->

    <div class="container container-main-content container-captura">
      <input type="hidden" id="idSeccion" value="${idSeccion}" />
      <input
        type="hidden"
        id="idUsuarioConectado"
        value="${idUsuarioConectado} "
      />
      <!-- VARIABLES DE PERMISOS DE CAPTURA RESULTADOS -->
      <input
        type="hidden"
        id="firmaResultadosTest"
        name="firmaResultadosTest"
        value="${perfilUsuario.cpuFirmaexamenes}"
      />
      <input
        type="hidden"
        id="autorizaExamenes"
        name="autorizaExamenes"
        value="${perfilUsuario.cpuAutorizaexamenes}"
      />
      <input
        type="hidden"
        id="quitaAutorizacion"
        name="quitaAutorizacion"
        value="${perfilUsuario.cpuQuitarautorizacion}"
      />
      <input
        type="hidden"
        id="editaResultadosCriticos"
        name="editaResultadosCriticos"
        value="${perfilUsuario.cpuEditaresultadoscriticos}"
      />

      <!-- -->
      <div class="row d-flex justify-content-center">
        <div class="accordion col-12">
          <div class="row">
            <div class="col-12 pl-0">
              <!-- ********************************* ini contenido -> *************************************************************************************-->
              <div class="card card-rounded" id="panelSuperior">
                <div class="card-header mb-0">
                  <!-- ********************************* panel Seleccion de orden *************************************************************************************-->
                  <div
                    class="card-title d-flex justify-content-between pt-1 pb-1"
                  >
                    <h3
                      id="#accordionPanelSeleccionOrden1"
                      class="icon-accordion mb-0 btn-block text-left pl-0 col-3"
                      data-toggle="collapse"
                      data-target="#accordionPanelSeleccionOrden"
                    >
                      Selecci&oacute;n de Orden
                    </h3>
                    <div class="col-3 d-flex justify-content-end">
                      <label class="text-capt">Secci&oacute;n</label>
                      <div class="col-8 pr-0">
                        <select
                          id="seccionSelect"
                          class="form-control form-control-sm selectpicker"
                          data-live-search="true"
                        >
                          <option value="">TODOS</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-2">
                      <label class="text-capt">Pendientes</label>
                      <input type="checkbox" checked id="estadoPendiente" />
                    </div>
                    <div class="card-buttons col-1 d-flex justify-content-end">
                      <a id="btnBuscarOrden" class="navi-link mr-0">
                        <span class="symbol symbol-30 symbol-circle">
                          <span
                            id=""
                            class="symbol-label bg-hover-blue hoverIcon circuloBuscarPaciente"
                          >
                            <i
                              id=""
                              class="fas fa-search text-blue text-primary iBuscarPaciente"
                            ></i>
                          </span>
                        </span>
                      </a>
                    </div>
                    <div class="col-3 justify-content-right">
                      <div class="float-right">
                        <button
                          id="btnRefreshBuscarOrden"
                          type="button"
                          class="btn btn-sm btn-secondary mr-2"
                        >
                          <i class="fas fa-redo-alt" aria-hidden="true"></i>
                        </button>
                        <label class="checkbox checkbox-primary mb-0">
                          <input
                            id="idChkAutorefresh"
                            name="exitus"
                            type="checkbox"
                          />Automática
                          <span></span>
                        </label>
                      </div>
                    </div>
                  </div>
                </div>
                <div
                  id="accordionPanelSeleccionOrden"
                  class="collapse container-content show"
                  data-parent="#panelSuperior"
                >
                  <div class="containerTable pl-2 pr-2 no-th-first hidden">
                    <div class="col-12">
                      <span id="dt_registros_orden" class="p-3"></span>
                    </div>
                    <div class="card-body d-flex pb-0">
                      <!-- FORMULARIO FILTRO -->
                      <div class="col-12 pl-0 pr-0">
                        <div class="card card-2 mt-3">
                          <div class="card-body p-0">
                            <table
                              class="table table-hover table-striped nowrap compact-xs table-selected"
                              id="ordenesDatatable"
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
                                  <!-- <th>10</th> -->
                                  <!-- <th>11</th> -->
                                  <!-- <th>12</th> -->
                                  <!-- <th>13</th> -->
                                </tr>
                              </thead>
                            </table>
                          </div>
                        </div>
                      </div>
                      <!-- FIN TABLA FILTRO -->
                    </div>
                  </div>
                </div>
              </div>

              <!-- ********************************* panel Examenes *************************************************************************************-->
              <div class="row">
                <div class="col-6 pr-1">
                  <div class="card h-100" id="panelExamanes">
                    <div class="card-header mb-0">
                      <div
                        class="card-title d-flex justify-content-between pt-1 pb-1"
                      >
                        <h3 class="mb-0 btn-block text-left pl-0 col-3">
                          Ex&aacute;menes
                        </h3>
                        <!-- <div class="col-3 d-flex justify-content-end">
                        <a id="btnBuscarFiltro" href="#"
                          class="btn btn-blue-primary btn-normal font-weight-bold mr-2 pt-1 pb-1 w-100">Seleccionar</a>
                      </div> -->
                      </div>
                    </div>
                    <div class="card-body pt-1">
                      <div class="containerTableExamen col-12 pl-0 pr-0 hidden">
                        <p id="nroExamenes" class="ml-5"></p>
                        <div class="col-12 p-0 d-flex">
                          <div class="col-4 d-flex align-items-center">
                            <input
                              type="checkbox"
                              name=""
                              id="seleccionaTodasLosExamenes"
                              class="checkable mr-1"
                            />
                            Seleccionar Todo
                          </div>
                          <div class="col-6 d-flex align-items-center">
                            <span id="dt_registros_examenes"></span>
                          </div>
                        </div>
                        <div class="card card-2 mt-3">
                          <div class="card-body p-0">
                            <table
                              class="table table-hover table-striped nowrap compact-xs"
                              id="examenesOrdenesDatatable"
                              style="width: 100%; line-height: 1"
                            >
                              <thead>
                                <tr>
                                  <th>Sel</th>
                                  <th>C&oacute;digo</th>
                                  <th>Examen</th>
                                  <th>Contenedor</th>
                                  <th>Secci&oacute;n</th>
                                  <th>#Muestra</th>
                                  <th>Estado</th>
                                  <th>Urgente</th>
                                  <th>Acciones&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                  <th>Seccion</th>
                                  <th>Anulado</th>
                                  <th>Urgentes</th>
                                </tr>
                              </thead>
                              <tbody></tbody>
                            </table>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-6 pl-1">
                  <div class="card card-rounded h-100">
                    <div class="card-header" id="registro-paciente-title">
                      <div
                        class="card-title d-flex justify-content-between pt-1 pb-1"
                      >
                        <h3
                          class="mb-1 btn-block text-left pl-0 col-md-2 col-xl-5"
                        >
                          Datos
                        </h3>
                        <div
                          class="col-md-8 col-xl-7 d-flex justify-content-end"
                        >
                          <button
                            id="btnVerOrden"
                            class="btn btn-blue-primary btn-normal font-weight-bold mr-2 col-md-8 col-xl-5 w-100"
                          >
                            <i class="fas fa-user-md"></i>Datos Paciente
                          </button>
                          <button
                            id="btnRegDocOrden"
                            class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 col-md-8 col-xl-6 w-100"
                          >
                            <img
                              src="<%=request.getContextPath()%>/resources/img/add_files.png"
                              width="20"
                              style="filter: brightness(0) invert(1)"
                            />Orden Médica
                          </button>
                        </div>
                      </div>
                    </div>
                    <div class="card-body pb-0" id="panelPacienteOrden">
                      <div class="row">
                        <div class="col-7 pr-0">
                          <div class="row">
                            <div class="col-12 mb-2 form-row line-height-1">
                              <div class="col-lg-4 col-xl-4 form-group mb-0">
                                <label class="col-form-label pb-0 col-12 pl-0"
                                  >Nombre</label
                                >
                                <span id="txtNombrePaciente"></span>
                              </div>
                              <div class="col-lg-8 col-xl-3 form-group mb-0">
                                <label class="col-form-label pb-0 col-12 pl-0"
                                  >Sexo</label
                                >
                                <span id="txtSexoPaciente"></span>
                              </div>
                              <div class="col-lg-4 col-xl-5 form-group mb-0">
                                <label
                                  class="col-form-label pb-0 col-12 pl-0 pr-0"
                                  >Edad</label
                                >
                                <span id="txtEdadPaciente"></span>
                              </div>
                              <div
                                class="col-lg-8 col-xl-4 form-group mt-1 mb-0"
                              >
                                <label class="col-form-label pb-0 col-12 pl-0"
                                  >Tipo de Atención</label
                                >
                                <span id="txtTipoAtencion"></span>
                              </div>
                              <div
                                class="col-lg-4 col-xl-3 form-group mt-1 mb-0"
                              >
                                <label class="col-form-label pb-0 col-12 pl-0"
                                  >Servicio</label
                                >
                                <span id="txtLocalizacion"></span>
                              </div>
                              <div
                                class="col-lg-8 col-xl-5 form-group mt-1 mb-0"
                              >
                                <label class="col-form-label pb-0 col-12 pl-0"
                                  >Procedencia
                                </label>
                                <span id="txtProcedencia"></span>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="col-5 pl-0">
                          <div class="row">
                            <div class="col-12 mb-2 form-row line-height-1">
                              <div class="col-lg-12 col-xl-5 pl-0-pr-0">
                                <div class="col-12 form-group mb-0">
                                  <label class="col-form-label pb-0 col-12 pl-0"
                                    >#Orden</label
                                  >
                                  <span id="txtNroOrden"></span>
                                </div>
                                <div class="col-12 form-group mb-0">
                                  <label class="col-form-label pb-0 col-12 pl-0"
                                    >Fecha</label
                                  >
                                  <span id="txtFechaOrden"></span>
                                </div>
                              </div>
                              <div
                                class="col-lg-12 col-xl-6 form-group mb-0 mt-2 d-flex align-items-end"
                              >
                                <!-- ---------- ini botonera -> --------------------------------------------------------------->
                                <div class="row">
                                  <div class="col-lg-2 col-xl-3">
                                    <button
                                      id="btnFirstOrder"
                                      class="btn btn-light-primary font-weight-bold btn-capt"
                                    >
                                      <i class="fas fa-fast-backward"></i>
                                    </button>
                                  </div>
                                  <div class="col-lg-2 col-xl-3">
                                    <button
                                      id="btnPrevOrder"
                                      class="btn btn-light-primary font-weight-bold btn-capt"
                                    >
                                      <i class="fas fa-step-backward"></i>
                                    </button>
                                  </div>
                                  <div class="col-lg-2 col-xl-3">
                                    <button
                                      id="btnNextOrder"
                                      class="btn btn-light-primary font-weight-bold btn-capt"
                                    >
                                      <i class="fas fa-step-forward"></i>
                                    </button>
                                  </div>
                                  <div class="col-lg-2 col-xl-3">
                                    <button
                                      id="btnLastOrder"
                                      class="btn btn-light-primary font-weight-bold btn-capt"
                                    >
                                      <i class="fas fa-fast-forward"></i>
                                    </button>
                                  </div>
                                  <div class="col-xl-12 col-md-9 mt-2 pr-0">
                                    <div class="progress">
                                      <div
                                        id="order-progress2"
                                        class="progress-bar bg-success"
                                        role="progressbar"
                                        style="width: 0%"
                                        aria-valuenow="0"
                                        aria-valuemin="0"
                                        aria-valuemax="0"
                                      ></div>
                                    </div>
                                    <div class="col-12">
                                      <label
                                        class="col-form-label pb-0 col-12 pl-0 text-center"
                                        ><span id="count_orden_inicio">0</span>
                                        de
                                        <span id="count_orden_fin"
                                          >0</span
                                        ></label
                                      >
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
              <!-- ********************************* panel tests  y resultados *************************************************************************************-->
              <div class="card mt-3" id="panelTest">
                <div class="card-header mb-0">
                  <div
                    class="card-title d-flex justify-content-between pt-1 pb-1"
                  >
                    <h3 class="mb-0 btn-block text-left pl-0 col-3">
                      Tests y Resultados
                    </h3>
                    <div class="card-buttons col-8">
                      <div class="container">
                        <div class="row justify-content-end">
                          <div class="col-11">
                            <div class="row justify-content-end">
                              <div class="col-md-4 col-xl-3 d-flex cont-capt">
                                <div
                                  class="mb-1 d-flex align-items-end justify-content-center w-100"
                                >
                                  <button
                                    id="btnFirmaResultados"
                                    type="button"
                                    class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100"
                                  >
                                    <img
                                      src="<%=request.getContextPath()%>/resources/img/firmar.png"
                                      width="20"
                                      style="filter: brightness(0) invert(1)"
                                    />
                                    Firmar resultados
                                  </button>
                                </div>
                              </div>
                              <!-- 
                              <div class="col-md-4 col-xl-3 d-flex">
                                <div
                                  class="mb-1 d-flex align-items-end justify-content-center w-100"
                                >
                                  <button
                                    id="btnRetiraFirma"
                                    type="button"
                                    class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100"
                                  >
                                    <img
                                      src="<%=request.getContextPath()%>/resources/img/paper-eraser.jpg"
                                      width="20"
                                      style="filter: brightness(0) invert(1)"
                                    />
                                    Retirar firma
                                  </button>
                                </div>
                              </div>
                              -->
                              <div class="col-md-4 col-xl-3 d-flex">
                                <div
                                  class="mb-1 d-flex align-items-end justify-content-center w-100"
                                >
                                  <button
                                    id="btnAutorizaResultados"
                                    type="button"
                                    class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100"
                                  >
                                    <img
                                      src="<%=request.getContextPath()%>/resources/img/confirm-file.png"
                                      width="20"
                                      style="filter: brightness(0) invert(1)"
                                    />
                                    Autorizar resultados
                                  </button>
                                </div>
                              </div>
                            </div>
                          </div>
                          <!-- BOTON ABRE MODAL CONTADOR CELULAS -->
                          <!-- <div class="card-buttons col-1 d-flex justify-content-end">
													<a id="btnContadorCelulas" class="navi-link mr-0"> <span
														class='symbol symbol-30 symbol-circle'> <span id=""
															class='symbol-label bg-hover-blue hoverIcon circuloContador'>
																<i id=""
																class="fas fa-search text-blue text-primary iContador"></i>
														</span>
													</span>
													</a>
												</div> -->
                          <div class="col-md-1 d-flex">
                            <div class="dropdown">
                              <a
                                class="navi-link mr-0 d-flex align-items-center"
                                data-toggle="dropdown"
                                aria-expanded="false"
                              >
                                <i
                                  class="fas fa-bars text-blue text-primary iContador"
                                  style="font-size: 3rem"
                                ></i>
                              </a>
                              <div class="dropdown-menu dropdown-menu-right">
                                <a
                                  id="btnContadorCelulas"
                                  class="dropdown-item dropdown-none"
                                  href="#"
                                  ><i class="fas fa-stopwatch-20 text-blue"></i
                                  >Contador Hematológico</a>
                                <a
                                id="btnRetiraFirma"
                                class="dropdown-item dropdown-none"
                                href="#"><img src="<%=request.getContextPath()%>/resources/img/paper-eraser.jpg" width="20"/>&nbsp;Retirar firma</a>
                                 <a
                                id="btnImprimirCR"
                                class="dropdown-item dropdown-none"  data-toggle="modal" data-target="#exampleModal"
                                href="#" ><i class="la la-print"></i>Visualizar documento</a>
                             
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="card-body pt-1">
                  <div class="containerTableTest col-12 pl-0 pr-0 hidden">
                    <!-- FORMULARIO FILTRO -->
                    <div class="col-12 p-0 d-flex">
                      <div class="col-2 d-flex align-items-center">
                        <input
                          type="checkbox"
                          name=""
                          id="seleccionaTodasLosResultados"
                          class="checkable mr-1"
                        />
                        Seleccionar Todo
                      </div>
                      <div class="col-2 d-flex align-items-center">
                        <span id="dt_registros_resultado"></span>
                      </div>
                      <div class="col-8 d-flex justify-content-end">
                        <div class="float-right">
                          <button
                            id="btnRefreshBuscarResultado"
                            type="button"
                            class="btn btn-sm btn-secondary mr-2"
                          >
                            <i class="fas fa-redo-alt" aria-hidden="true"></i>
                          </button>
                          <label class="checkbox checkbox-primary mb-0">
                            <input
                              id="idChkAutorefreshres"
                              name="exitus"
                              type="checkbox"
                            />Automática
                            <span></span>
                          </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-12 pl-0 pr-0">
                      <p id="nroTest"></p>
                      <div class="card card-2 mt-3">
                        <div class="card-body p-0">
                          <table
                            class="table table-hover table-striped compact-xs nowrap"
                            id="resultadosExamenesOrdenesDatatable"
                            style="width: 100%"
                          >
                            <thead>
                              <tr>
                                <th>Sel</th>
                                <th class="res-examen">Examen</th>                          
                                <th class="res-test">Test</th>
                                <th>Alerta</th>
                                <th>Resultado</th>
                                <th>U.M.</th>
                                <th>Referencia</th>
                                <th>M&eacute;todo</th>                 
                                <th>Estado</th>                         
                                <th>Delta</th>  
                                 <th>Hist&oacute;rico</th>                                       
                                <th>Acciones</th>
                                <th>Limpiar</th>
                                <th>Sección</th>
                                <th>Anulado</th>
                                <th>Sort</th>
                                <th></th>
                                <th></th>
                              </tr>
                            </thead>
                          </table>
                        </div>
                      </div>
                    </div>
                    <!-- FIN TABLA FILTRO -->
                  </div>
                    
                </div>
              </div>
              <!-- ********************************* <-fin contenido *************************************************************************************-->
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- ********************************* <-fin contenedor *************************************************************************************-->

    <!-- **************************** Modal CONTADOR CELULAS ******************************************                    -->
    <div
      class="modal fade"
      id="ContadorCelulasModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="ContadorCelulasModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <div class="col-12">
              <div class="row">
                <div class="col-4 pr-0">
                  <a
                    id="btnConfiguradorContador"
                    class="navi-link mr-0 pointer"
                  >
                    <span class="">
                      <span
                        class="symbol-label"
                        data-toggle="tooltip"
                        title=""
                        data-original-title="Configuraci&oacute;n de Contador"
                      >
                        <i
                          class="fas fa-cog text-blue text-primary iBuscarPaciente"
                          style="font-size: 2rem"
                        ></i>
                      </span>
                    </span>
                  </a>
                </div>
                <h3 id="" class="col-8 pl-0" id="exampleModalLabel">
                  Contador Diferencial de C&eacute;lulas
                </h3>
              </div>
            </div>
            <jsp:include page="modals/ModalContadorCelulas.jsp" />
          </div>
        </div>
      </div>
    </div>

    <!-- **************************** Modal Configurador Contador Celulas  ******************************************                    -->
    <div
      class="modal fade"
      id="ConfiguradorCelulasModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="ConfiguradorCelulasModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 id="" class="text-center" id="exampleModalLabel">
              Configuración de Contador Hematológico
            </h3>
            <jsp:include page="modals/ModalConfiguradorContadorCelulas.jsp" />

            <button
              id="btnGuardarConfiguracion"
              type="button"
              class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
              onclick="saveConfiguradorContadorCelulas()"
            >
              OK
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- **************************** Modal Historico  ******************************************                    -->
    <div
      class="modal fade"
      id="historicoModal"
      role="dialog"
      aria-labelledby="historicoModal"
      aria-hidden="true"
    >
      <div
        class="modal-dialog modal-dialog-centered"
        style="max-width: 90% !important"
        role="document"
      >
        <div class="modal-content">
          <div class="modal-body">
            <h3 class="text-center pb-4" id="exampleModalLabel">
              Resultados Historicos
            </h3>
            <jsp:include page="modals/ModalHistorico.jsp" />
            <div
              class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse"
            >
              <button
                type="button"
                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                data-dismiss="modal"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- **************************** Modal Paciente ******************************************                    -->
    <div
      class="modal fade"
      id="datosPacienteModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="datosPacienteModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 id="" class="text-center" id="exampleModalLabel">
              Datos Paciente
            </h3>
            <jsp:include page="microbiologia/OrderCanvasData.jsp" />
            <div
              class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse"
            >
              <button
                type="button"
                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                data-dismiss="modal"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- **************************** Modal Eventos Orden ******************************************                    -->
    <jsp:include page="microbiologia/OrderCanvasEvents.jsp" />
         
    <!--
    <div
      class="modal fade" 
      id="eventosOrdenModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="eventosOrdenModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 id="tituloEvento" class="text-center" id="eventosOrdenModalLabel">

            </h3>
             <input type="hidden" id="idOrdenEvento" >
               <input type="hidden" id="idTestEvento" >
            <div class=" d-flex justify-content-end">	           

	            <input  type="text" id="textBuscarEvento" class="mr-2 form-control-sm"/>
	             <input  type="button" class="btn btn-blue-primary btn-normal font-weight-bold mr-4 " id="buscarEventoOrden" value="Buscar"/>

	              <input type="button" class="btn btn-blue-primary btn-normal font-weight-bold mr-4 " id="buscarEventoTest" value="Buscar"/>
	            <button id="btnExcelEvento" class="btn btn-blue-primary btn-normal font-weight-bold ml-8">
					<i class="fa fa-download" aria-hidden="true"></i>Descargar Excel</button>

            <div
              class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse"
            >
              <button
                type="button" id="btnCerrarModalEvento"
                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                data-dismiss="modal"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    -->

    <!-- **************************** Modal Eventos Examen ******************************************                    -->
    <div
      class="modal"
      id="eventosExamenModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="eventosExamenModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title" id="eventosExamenModalLabel">
              Eventos del Examen
            </h1>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <i aria-hidden="true" class="ki ki-close"></i>
            </button>
          </div>
          <div class="modal-body">
            <jsp:include page="modal/ModalExamCanvasEvents.jsp" />
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-dismiss="modal"
            >
              Cerrar
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- **************************** Modal Notas Examen ******************************************                    -->
    <div
      class="modal fade"
      id="notasExamenModal"
      tabindex="-1"
      role="dialog"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 id="" class="text-center" id="notasExamenModalLabel">
              Notas del Examen
            </h3>
            <jsp:include page="modal/ModalExamCanvasNotes.jsp" />
          </div>
        </div>
      </div>
    </div>

    <!-- **************************** Modal Observaciones Examen ******************************************                    -->
    <div
      class="modal fade"
      id="obsExamenModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="obsExamenModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 id="" class="text-center" id="obsExamenModalLabel">
              Observaciones del Examen
            </h3>
            <jsp:include page="microbiologia/ExamCanvasObservations.jsp" />
            <div
              class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse"
            >
              <button
                type="button"
                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                data-dismiss="modal"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- **************************** Modal Test Opcionales******************************************                    -->
    <div
      class="modal fade"
      id="opcTestExamenModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="opcTestExamenModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
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

    <!-- **************************** Modal Test Dcoumentos******************************************                    -->
    <jsp:include page="modal/ModalDocumentosExamen.jsp" />
    <!-- **************************** Modal Test Orden******************************************                    -->
    <jsp:include page="modal/ModalDocumentosSoloOrden.jsp" />

    <!-- **************************** Modal Test Eventos******************************************                    -->
    <div
      class="modal"
      id="eventosTestModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="eventosTestModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 id="" class="text-center" id="eventosTestLabel">
              Eventos Test
            </h3>
            <jsp:include page="microbiologia/TestCanvasEvents.jsp" />
            <div
              class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse"
            >
              <button
                type="button"
                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                data-dismiss="modal"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- **************************** Fin Modal Test Eventos******************************************                    -->

    <!-- **************************** Modal Test Detalles******************************************                    -->
    <div
      class="modal fade"
      id="detallesTestModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="detallesTestModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 id="" class="text-center" id="detallesTestLabel">
              Detalles Test
            </h3>
            <jsp:include page="microbiologia/TestCanvasData.jsp" />
            <div
              class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse"
            >
              <button
                type="button"
                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                data-dismiss="modal"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- **************************** Fin Modal Test Detalles******************************************                    -->
    <!-- **************************** Modal Buscar Ordenes ******************************************                    -->
    <div
      class="modal fade"
      id="buscarOrdenesModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="buscarOrdenesModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 id="" class="text-center" id="buscarOrdenesModalLabel">
              B&uacute;squeda Detallada
            </h3>
            <jsp:include page="desarrollo/Bios_ModalOrdenesBuscador.jsp" />
          </div>
        </div>
      </div>
    </div>
    <!-- **************************** Fin Modal Buscar Ordenes ******************************************                    -->

    <!-- **************************** Modal Antecedentes Pacientes ************************************************************************-->
    <jsp:include page="modal/ModalAntecedentesPac.jsp" />
    <!-- **************************** Fin Modal Antecedentes Pacinetes ************************************************************************-->
    <!-- **************************** Modal Notificaciones - >************************************************************************-->

  <div id="notificacionResultadosModal"
    class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
    aria-labelledby="" aria-hidden="true">
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
        <div class="modal-body">
          <h3 class="text-center">Notificaci&oacute;n de Resultados Cr&iacute;ticos <span id="idTestCanvasNotificationsabr"></span></h3>
          <jsp:include page="modal/ModalTestNotifications.jsp" />
          </div>
        </div>
      </div>
    </div>
    <!-- **************************** <-Fin Modal Notificaciones ************************************************************************-->

    <!-- **************************** Modal Autorizaciones - >************************************************************************-->

    <div
      id="AutorizacionExamenesModal"
      class="modal fade bd-example-modal-lg"
      tabindex="-1"
      role="dialog"
      aria-labelledby=""
      aria-hidden="true"
    >
      <jsp:include page="modal/ModalExamCanvasAuth.jsp" />
    </div>
    <!-- **************************** <-Fin Modal Autorizaciones ************************************************************************-->

    <!-- **************************** Modal Desautorizar - >************************************************************************-->

    <div
      id="desautorizarResultadosModal"
      class="modal fade bd-example-modal-lg"
      tabindex="-1"
      role="dialog"
      aria-labelledby=""
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <h3 class="text-center">Quitar autorización</h3>
            <p>
              ¿Se va a retirar la autorización de exámenes ya entregados, ¿Está
              seguro que desea continuar?
            </p>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary btn-no"
              data-dismiss="modal"
            >
              No
            </button>
            <button type="button" class="btn btn-primary btn-si">Si</button>
          </div>
        </div>
      </div>
    </div>
    <!-- **************************** <-Fin Modal Desautorizar ************************************************************************-->

    <!-- **************************** Modal Glosa Multiple************************************************************************-->
    <jsp:include page="modal/ModalGlosaMultiple.jsp" />
    <!-- **************************** Fin Modal Glosa Multiple************************************************************************-->

    <!-- *Realizado por cristian 11-10-2022                     -->
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
			<div class="modal fade" id="imprimirModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel">Exámenes sin autorizar reconocidos </h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
			        <table id="examenesNoImprimir">
			        	<thead>
			        	<tr>
			        	<td>Exámenes</td>
			        	</tr>
			        	</thead>
			        	<tbody>
			        	
			        	</tbody>
			        	
			        </table>
			      </div>
			      <div class="modal-footer">
			      <div class="form-check">
					  <input class="form-check-input" type="checkbox" value="" id="checkBoxNoMostrar">
					  <label class="form-check-label" for="flexCheckDefault">
					    No volver a mostrar mensaje?
					  </label>
					</div>
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
			        <button id="botonImprimirModal" type="button" class="btn btn-primary">OK</button>
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
                  <button id="btn_confirm_noti" type="button" class="btn btn-primary btn-si">Si</button>
                </div>
                </div>
              </div>
              </div>
    <!-- fin de modal Linea de tiempo            -->
    
      <!-- **************************** Modal Formula - >************************************************************************-->

    <div
      id="formulaTestModal"
      class="modal fade bd-example-modal-lg"
      tabindex="-1"
      role="dialog"
      aria-labelledby=""
      aria-hidden="true"
    >
      <jsp:include page="modal/ModalFormulaTest.jsp" />
    </div>
    <!-- **************************** <-Fin ModalForluma ************************************************************************-->

    <jsp:include page="common/AlertaSession.jsp" />
    <jsp:include page="comunes/js.jsp"></jsp:include>

    <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>

    <script src="<%=request.getContextPath()%>/resources/js/common/InicioFunciones.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/common/GlobalVars.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/MenuDinamico.js"></script>

    <!--begin::Global Theme Bundle(used by all pages)-->
    <!--end::Global Theme Bundle-->
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script src="https://code.highcharts.com/modules/export-data.js"></script>
    <script src="https://code.highcharts.com/modules/accessibility.js"></script>
    <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
    <!--end::Global Theme Bundle-->
    <script src="https://unpkg.com/imask"></script>
    <script src="https://cdn.datatables.net/scroller/2.0.6/js/dataTables.scroller.min.js"></script>
    <script src="https://cdn.datatables.net/select/1.4.0/js/dataTables.select.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery.masknumber.js"></script>
    <script src="<%=request.getContextPath()%>/resources/datetimepicker/moment.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgGlosas.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/common/queue.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboCaptura.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/common/ModuloFecha.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/Dto/FiltroOrden.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgMuestras.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgEnvases.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/Dto/DominiosClases.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/modals/ModalTestNotifications.js"></script> 
    <script src="<%=request.getContextPath()%>/resources/js/modals/ModalExamenAutorizacion.js"></script>

    <script src="<%=request.getContextPath()%>/resources/js/modals/ModalDocumentosExamen.js"></script>

    <script src="<%=request.getContextPath()%>/resources/js/modals/ModalGlosaMultipleTest.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/OptionalTestContent.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/common/FileInputBiosbo.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.upload.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.ordenes.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/informeresultados/InformeResultados.utiles.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.examenes.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.dtresultados.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.resultados.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.historico.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.contador.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/localStorage.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/microbiologia/templates.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/modals/ModalOrdersSearch.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/modals/ModalAntecedentesPaciente.js"></script>
    
    <script  src="<%=request.getContextPath()%>/resources/js/portalEstadistica/xlsx.mini.js"></script>
    <jsp:include page="common/FileInputScripts.jsp" />

    <!-- ** agregado por cristian 11-10-2022 -->
    <script src="<%=request.getContextPath()%>/resources/js/capturaresultados/CapturaResultados.lineaTiempo.js"></script>

    <script type="text/javascript">
      $.fn.dataTable.Api.register("row().show()", function () {
        var page_info = this.table().page.info();
        // Get row index
        var new_row_index = this.index();
        // Row position
        var row_position = this.table()
          .rows({ search: "applied" })[0]
          .indexOf(new_row_index);
        // Already on right page ?
        if (
          (row_position >= page_info.start && row_position < page_info.end) ||
          row_position < 0
        ) {
          // Return row object
          return this;
        }
        // Find page number
        var page_to_display = Math.floor(
          row_position / this.table().page.len()
        );
        // Go to that page
        this.table().page(page_to_display);
        // Return row object
        return this;
      });
    </script>
  </body>
</html>
