<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Procesos</title>
        <jsp:include page="common/Styles_1.jsp"/>
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
        <div class="container container-main-content container-configuracion mt-5">
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0">
                    <div id="panelProceso" class="card border-15">
                        <div class="card-header border-15" id="headingProcesos">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion"
                                        data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true"
                                        aria-controls="collapseHeader">B&uacute;squeda de Procesos</h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevoProceso" class="navi-link"> 
                                        <span  class='symbol symbol-30 symbol-circle '> 
                                            <span id="circuloAgregarProceso" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nuevo Proceso"> 
                                                <i id="iAgregarProceso" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a> 
                                    <a id="btnLimpiarFiltro" class="navi-link"> 
                                        <span class="symbol symbol-30 symbol-circle "> 
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue  hoverIcon" href="#" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span> 
                                        <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content"
                            aria-labelledby="headingProcesos" data-parent="#headingProcesos">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label id="" class="col-4 col-form-label">C&oacute;digo</label>
                                            <div class="col-8">
                                                <input id="txtCodigoFiltro" class="form-control" name="" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label id="" class="col-4 col-form-label">Nombre</label>
                                            <div class="col-8">
                                                <input id="txtNombreFiltro" class="form-control" name="" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Secci&oacute;n</label>
                                            <div class="col-8">
                                                <select id="selectSeccionFiltro" class="form-control selectpicker" data-live-search="true"></select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Estaci&oacute;n</label>
                                            <div class="col-8">
                                                <select id="selectEstacionFiltro" class="form-control selectpicker" data-live-search="true"></select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <label>
                                            <input id="chkMostrarActivos" type="checkbox" /> Mostrar solo activos
                                        </label>
                                    </div>
                                </div>
                                <!-- FIN FORMULARIO FILTRO -->
                                <!-- TABLA FILTRO -->
                                <div class="col">
                                    <div class="col table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th id="thNombre" scope="col">Proceso</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron procesos con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro" class="ocultar">Procesos encontrados: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                    <!-- FORMULARIO REGISTRO PROCESOS -->
                    <div id="panelProcesoResultados" class="card mt-5 border-15">
                        <div class="card-header pl-2 border-15">
                            <div class="card-title row col-12 d-flex justify-content-between">
                                <h3 id="" class="card-label col-4 pl-0">
                                    Registro de Procesos
                                </h3>
                                <!--<div class="col-6">
                                    <ul class="nav nav-pills">
                                        <li class="nav-item">
                                            <a id="anchorRegistro" class="nav-link active" data-toggle="tab" href="#panelDatosBasicos">
                                                <span class="nav-icon"><i class="flaticon2-paper"></i></span>
                                                <span class="nav-text">Datos&nbsp; B&aacute;sicos</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>-->
                                <div class="col-2 justify-content-right iconos-registro ">
                                    <div class="float-right">
                                        <div id="divbtnLimpiar">
                                            <a id="btnLimpiar" class="navi-link pointer">
                                                <span class="symbol symbol-30 symbol-circle">
                                                    <span id="circuloLimpiar" data-toggle="tooltip"
                                                        title="Limpiar"
                                                        class="symbol-label bg-hover-blue ">
                                                        <i id="iLimpiar"
                                                            class="la la-broom icon-xl  text-blue"></i></span>
                                                </span> <span class="navi-text"></span>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="float-right">
                                        <div id="divbtnEditar">
                                            <a id="btnEditar" class="navi-link pointer"> 
                                                <span class='symbol symbol-30 symbol-circle'>
                                                    <span id="circuloEditarProceso"
                                                        class='symbol-label circuloIcon'
                                                        data-toggle="tooltip" title="Editar Proceso">
                                                        <i id="iEditProceso"
                                                            class="far fa-edit text-dark-50 "></i>
                                                    </span>
                                                </span>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="float-right">
                                        <div id="divbuscarProceso">
                                            <a id="buscarProceso" class="navi-link" href="#"> 
                                                <span class='symbol symbol-30 symbol-circle mr-3'> 
                                                    <span id="circuloBuscarProceso" class='symbol-label bg-hover-blue hoverIcon'
                                                         data-toggle="tooltip" title="Buscar Proceso">
                                                        <i id="iBuscarProceso" class="fas fa-search text-blue"></i>
                                                    </span>
                                                </span>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <form id="formRegistroProceso" method="post" enctype="multipart/form-data">
                                <div class="col-12 pl-0">
                                    <h5 id="tituloRegistro" class="card-label text-center">
                                        Proceso:
                                    </h5>
                                </div>
                                <div id="divForm" class="row">
                                    <div class="col-12">
                                        <!--<div class="tab-content" id="myTabContent">-->
                                            <div class="tab-pane fade show active" id="panelDatosBasicos" role="tabpanel" aria-labelledby="panelResultado">
                                                <div class="row">
                                                    <input id="txtIdProceso" type="text" class="form-control ocultar" />
                                                    <div class="col-4">
                                                        <label>C&oacute;digo *</label>
                                                        <input id="txtCodigo" maxlength="12" name="" type="text" class="form-control formProceso" autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Nombre *</label>
                                                        <input id="txtNombre" maxlength="50" type="text" class="form-control formProceso"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Par&aacute;metros</label>
                                                        <input id="txtParametros" maxlength="25" type="text" class="form-control formProceso"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Tipo Comunicaci&oacute;n</label>
                                                        <select class="form-control formProceso" id="selectTipoComunicacion">
                                                            
                                                        </select>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Secci&oacute;n</label>
                                                        <select class="form-control formProceso selectpicker" id="selectSeccion" data-live-search="true"></select>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Centro de Salud</label>
                                                        <select class="form-control formProceso" id="selectCentroSalud"></select>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>C&oacute;digo de barras *</label>
                                                        <!--<input id="txtCodigoBarras" type="text" class="form-control formProceso"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>-->
                                                        <select class="form-control formProceso" id="selectCodigoBarras">
                                                            <option value="">SIN ESPECIFICAR</option>
                                                            <option value="CODE128">CODE128</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>C&oacute;digo Host</label>
                                                        <input id="txtCodigoHost" maxlength="20" name="" type="text" class="form-control formProceso"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>C&oacute;digo QC</label>
                                                        <input id="txtCodigoQC" name="" type="text" class="form-control formProceso"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Estaci&oacute;n</label>
                                                        <select class="form-control formProceso" id="selectEstacion"></select>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Orden</label>
                                                        <input id="txtOrden" type="number" class="form-control formProceso" min="1" value="1"  autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Sender ID</label>
                                                        <input id="txtSender" maxlength="50" type="text" class="form-control formProceso"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Receiver ID</label>
                                                        <input id="txtReceiver" maxlength="50" type="text" class="form-control formProceso"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4 mt-1">
                                                        <label class="col-12 mt-2">
                                                            <input id="chbxControlAlarmas" type="checkbox" class="formProceso" />&nbsp;Control de alarmas de test
                                                        </label>
                                                        <label class="col-12 mt-2">
                                                            <input id="chbxResultadosTemporales" type="checkbox" class="formProceso" />&nbsp;Resultado en tabla temporal
                                                        </label>
                                                    </div>
                                                    <div class="col-4 mt-1">
                                                        <label class="col-12 mt-2">
                                                            <input id="chbxNroMuestraCorrelativo" type="checkbox" class="formProceso" />&nbsp;N&ordm; de Muestra correlativo
                                                        </label>
                                                        <label class="col-12 mt-2">
                                                            <input id="chbxQC" type="checkbox" class="formProceso" />&nbsp;QC
                                                        </label>
                                                    </div>
                                                    <h5 class="col-12 mt-2">Bidireccional</h5>
                                                    <div class="col-4">
                                                        <div class="form-group row">
                                                            <div class="col-5">
                                                                <input id="chbxCargaTemporal" type="checkbox" class="formProceso" />&nbsp;Carga temporal&nbsp;
                                                            </div>
                                                            <div class="col-7 row">
                                                                <input id="txtSegundosCargaTemporal" type="number" class="form-control formProceso col-5" autocomplete="off" />&nbsp;&nbsp;Segundos
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <div class="col-5">
                                                                <input id="chbxCargaProceso" type="checkbox" class="formProceso" />&nbsp;Carga procesos&nbsp;
                                                            </div>
                                                            <div class="col-7 row">
                                                                <input id="txtSegundosCargaProceso" type="number" class="form-control formProceso col-5" autocomplete="off" />&nbsp;&nbsp;Segundos
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <div class="col-12">
                                                                <input id="chbxMuestraRecepcionada" type="checkbox" class="formProceso" />&nbsp;Carga solo muestra recepcionada
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-4 mt-2">
                                                        <button id="btnAddTests" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-toggle="modal" data-target="#modalTests">
                                                            A&ntilde;adir Tests
                                                        </button>
                                                    </div>
                                                    <div class="col-4 mt-2">
                                                        <button id="btnModalAlarmas" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-toggle="modal" data-target="#modalAlarmas">
                                                            A&ntilde;adir Alarmas
                                                        </button>
                                                    </div>
                                                    <div class="col-12 mt-2">
                                                        <table id="tablaProcesosTests" class="table">
                                                            <thead>
                                                                <tr>
                                                                    <!--<th scope="col">#</th>-->
                                                                    <th>Test</th>
                                                                    <th>C&oacute;digo</th>
                                                                    <th>Ex&aacute;menes</th>
                                                                    <th>Secci&oacute;n</th>
                                                                    <th>Activo</th>
                                                                    <th>Editar</th>
                                                                </tr>
                                                            </thead>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        <!--</div>-->
                                    </div>
                                    <div class="col-md-6 form-group mt-6 row">
                                        <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                        <div class="col-3">
                                            <label class="switch-content switch-comprobante switch-active">
                                                <input id='checkBoxActivo' name='mcActivo' class="formProceso" type='checkbox' checked />
                                                <div></div>
                                            </label>
                                        </div>
                                        <div id="divAgregarBtn" class="col-6">
                                            <button id="btnAgregarProceso" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Guardar</button>
                                        </div>
                                        <div id="divActualizarBtn" class="col-6 ocultar">
                                            <button id="btnUpdateProceso" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Confirmar</button>
                                            <button id="btnCancelProceso" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                                        </div>
                                    </div>
                                    
                                    <!-- MODAL TEST-->
                                    <div class="modal fade" id="modalTests" tabindex="-1" role="dialog" aria-labelledby="exampleModalSizeSm" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Selección de Tests</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <i aria-hidden="true" class="ki ki-close"></i>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row" id="contenedorTests">
                                                            <div class="col-4">
                                                                <label class="">Filtro por Secci&oacute;n</label>
                                                                <select class="form-control formProceso selectpicker" id="selectSeccionTest" data-live-search="true"></select>
                                                                <label class="">Filtro por Examen</label>
                                                                <select class="form-control formProceso selectpicker" id="selectExamenTest" data-live-search="true"></select>
                                                                <div class="row pt-3">
                                                                    <div class="col pr-0">
                                                                        <input type="text" id="idSearcherTests" class="formProceso" inputmode="search" class="col-12" style="width:100%" />
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col pr-0">
                                                                        <select id="idDualDataVisualSourceTest" class="formProceso" size="5" tabindex="-1" style="width: 100%;">
                                                                                <option value="-1" disabled style="display: none;">Seleccionar</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="col-1 d-flex align-items-center">
                                                                <div id="dualDataButtonBar" class="mx-auto">
                                                                    <div class="col-12">
                                                                        <button id="clickAddBtnTest" type="button"
                                                                                class="btn btn-blue-primary formProceso">
                                                                                <i class="fas fa-arrow-right" tabindex="-1"></i>
                                                                        </button>
                                                                    </div>
                                                                    <div class="col-12">
                                                                        <button id="clickDelBtnTest" type="button"
                                                                                class="btn btn-blue-primary formProceso">
                                                                                <i class="fas fa-arrow-left" tabindex="-1"></i>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-7 ">
                                                                <table id="tablaTests" class="table">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>C&oacute;digo</th>
                                                                            <th>Test</th>
                                                                            <th>Activo</th>
                                                                        </tr>
                                                                    </thead>
                                                                </table>
                                                            </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button id="btnCerrarModalTests" type="button" class="btn btn-lg btn-blue-primary font-weight-bold" data-dismiss="modal">Guardar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!-- MODAL TESTS POR PROCESO-->
                                    <div class="modal fade" id="modalTestsProceso" tabindex="-1" role="dialog" aria-labelledby="exampleModalSizeSm" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Tests por Proceso</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <i aria-hidden="true" class="ki ki-close"></i>
                                                    </button>
                                                </div>
                                                <div class="modal-body">

                                                    <h5 id="tituloProceso" class="card-label text-center"></h5>
                                                    <h5 id="tituloTest" class="card-label text-center"></h5>
                                                    <div class="row mt-5">
                                                        <input id="txtIdTest" type="text" class="form-control ocultar" />
                                                        <div class="col-6">
                                                            <div class="form-group row pl-3">
                                                                <label class="col-4">C&oacute;digo Recepci&oacute;n</label>
                                                                <input id="txtCodigoRecepcion" type="text" class="col-7 form-control formProceso"   autocomplete="off" />
                                                            </div>
                                                            <div class="form-group row pl-3">
                                                                <label class="col-4">C&oacute;digo Env&iacute;o</label>
                                                                <input id="txtCodigoEnvio" type="text" class="col-7 form-control formProceso"   autocomplete="off" />
                                                            </div>
                                                            <div class="form-group row pl-3">
                                                                <label class="col-4">Tipo de Muestra</label>
                                                                <select class="form-control formProceso col-7" id="selectTipoMuestra"></select>
                                                            </div>
                                                            <div class="form-group row pl-3">
                                                                <label class="col-4">Tipo de Resultado</label>
                                                                <select class="form-control formProceso col-7 " id="selectTipoResultado"></select>
                                                            </div>
                                                            <div class="border">
                                                                <h5 class="pl-3 mt-3 mb-3">Datos del test</h5>
                                                                <div class="form-group row pl-3">
                                                                    <label class="col-4">C&oacute;digo de test</label>
                                                                    <input id="txtCodigoTest" type="text" class="col-7 form-control" autocomplete="off" disabled />
                                                                    <div class="d-md-none mb-2"></div>
                                                                </div>
                                                                <div class="form-group row pl-3">
                                                                    <label class="col-4">Descripci&oacute;n de test</label>
                                                                    <input id="txtDescripcionTest" type="text" class="col-7 form-control" autocomplete="off" disabled />
                                                                    <div class="d-md-none mb-2"></div>
                                                                </div>
                                                                <div class="form-group row pl-3">
                                                                    <label class="col-4">Ex&aacute;menes</label>
                                                                    <input id="txtExamenes" type="text" class="col-7 form-control" autocomplete="off" disabled />
                                                                    <div class="d-md-none mb-2"></div>
                                                                </div>
                                                                <div class="form-group row pl-3">
                                                                    <label class="col-4">Prefijo Muestra</label>
                                                                    <input id="txtPrefijoMuestra" type="text" class="col-7 form-control formProceso" autocomplete="off" />
                                                                    <div class="d-md-none mb-2"></div>
                                                                </div>
                                                                <div class="form-group row pl-3">
                                                                    <label class="col-4">Decimales</label>
                                                                    <input id="txtDecimales" type="text" class="col-7 form-control"  disabled autocomplete="off" />
                                                                    <div class="d-md-none mb-2"></div>
                                                                </div>
                                                                <input type="hidden" id="hidIdTipoMuestra" class="ocultar" />
                                                            </div>
                                                        </div>
                                                        <div class="col-6">
                                                            <div class="border p-2">
                                                                <h5 class="pl-3 mt-3 mb-3" id="tituloConversion">Conversiones</h5>
                                                                <div class="form-group row pl-3">
                                                                    <label class="col-11">
                                                                        <input id="chbxCondicional" type="checkbox" class="formProceso" />&nbsp;
                                                                        &nbsp;Condicional
                                                                    </label>
                                                                </div>
                                                                <ul class="nav nav-tabs" id="myTab" role="tablist">
                                                                    <li class="nav-item condition" role="presentation" id="presentacion">
                                                                      <button class="nav-link active" id="condicion" data-toggle="tab" data-target="#condicion-sub" type="button" role="tab" aria-controls="condicion-sub" aria-selected="true">Condicion 1</button>
                                                                    </li>
                                                                    <li class="nav-item" id="nav-item-agregar">
                                                                      <!--<a class="nav-link formConditional" id="agregar-tab" onclick="agregarTabs(2)" disabled>+</a>-->
                                                                      <button type="button" class="nav-link formConditional" id="agregar-tab" onclick="agregarTabs(2)" disabled>+</button>
                                                                    </li>
                                                                </ul>
                                                                <div class="tab-content border" id="myTabContent">
                                                                    <div class="tab-pane fade show active" id="condicion-sub" role="tabpanel" aria-labelledby="condicion">
                                                                        <div class="form-group row ml-3">
                                                                            <label class="col-11">Si resultado</label>
                                                                            <select class="form-control formConditional col-4" id="selectOperando" disabled></select>&nbsp;
                                                                            <input id="textValorEntrada" type="text" class="col-7 form-control formConditional" autocomplete="off" disabled />
                                                                        </div>
                                                                        <input id="chbxCritico" type="checkbox" class="formConditional" disabled />&nbsp;Marcar como crítico&nbsp;&nbsp;
                                                                        <div class="form-group row ml-3">
                                                                            <label class="col-11">Entonces</label>
                                                                            <input id="textValorSalida" type="text" class="col-11 form-control formConditional" autocomplete="off" disabled />
                                                                        </div>
                                                                        <div class="form-group row ml-3">
                                                                            <input id="chbxReprocesar" type="checkbox" class="formConditional" disabled />&nbsp;Reprocesar&nbsp;&nbsp;
                                                                            <input id="chbxBloquear" type="checkbox" class="formConditional" disabled />&nbsp;Bloquear&nbsp;&nbsp;
                                                                            <input id="chbxFirmar" type="checkbox" class="formConditional" disabled />&nbsp;Firmar&nbsp;&nbsp;
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <label class="col-11">
                                                                    <input id="chbkCalculo" type="checkbox" class="formProceso" />&nbsp;
                                                                    &nbsp;C&aacute;lculo
                                                                </label>
                                                                <div class="form-group row pl-3">
                                                                    <label class="col-3">Resultado</label>
                                                                    <select class="form-control formCalculo col-4" id="selectOperador" disabled></select>
                                                                    &nbsp;
                                                                    <input id="numValorOperando" type="number" class="col-11 form-control formCalculo" autocomplete="off" disabled />
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <label id="lblEstadoTest" class="col-4 col-form-label">Activo</label>
                                                            <div class="col-3">
                                                                <label class="switch-content switch-comprobante switch-active"> <input id="checkBoxTestActivo" type="checkbox" tabindex="-1"  class="formProceso" checked="" value="S">
                                                                    <div></div>
                                                                </label> 
                                                                <!-- <span id="spanCheckTest" class="switch switch-outline switch-icon switch-primary">
                                                                    <label>
                                                                        <input id='checkBoxTestActivo' class="formProceso" type='checkbox' />
                                                                        <span></span>
                                                                    </label>
                                                                </span> -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button id="btnGuardarTestProceso" type="button" class="btn btn-lg btn-blue-primary font-weight-bold">Guardar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!-- MODAL ALARMAS -->
                                    <div class="modal fade" id="modalAlarmas" tabindex="-1" role="dialog" aria-labelledby="modalAlarmasProceso" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Alarmas para Proceso</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <i aria-hidden="true" class="ki ki-close"></i>
                                                    </button>
                                                </div>
                                                <div class="modal-body ">
                                                    <button id="btnAddAlarma" type="button" class="btn btn-blue-primary">Agregar</button>
                                                    <table id="tablaAlarmasProceso" class="table">
                                                        <thead>
                                                            <tr>
                                                                <th>Id</th>
                                                                <th>C&oacute;digo</th>
                                                                <th>Descripci&oacute;n</th>
                                                                <th>Bloquear</th>
                                                                <th>Activo</th>
                                                                <th>Editar</th>
                                                            </tr>
                                                        </thead>
                                                    </table>   
                                                    
                                                </div>
                                                <div class="modal-footer">
                                                    <button id="btnCerrarModalAlarma" type="button" class="btn btn-blue-primary btn-lg font-weight-bold" data-dismiss="modal">OK</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- END MODAL ALARMAS-->
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://unpkg.com/imask"></script>
        <jsp:include page="common/Scripts.jsp"/>
        <script	src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/dataTables.cellEdit.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgTests.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/procesos/ConfiguracionProcesos.tests.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/procesos/ConfiguracionProcesos.binding.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/procesos/ConfiguracionProcesos.tablatests.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/procesos/ConfiguracionProcesos.alarmas.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/procesos/ConfiguracionProcesos.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/procesos/ConfiguracionProcesosForm.js"></script>
    </body>
</html>

