<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuraci&oacute;n de Examen</title>
        <jsp:include page="common/Styles_1.jsp"/>
    </head>
    <body>
        <div class="row menu-container">
            <div class="col-md-6">
                    <jsp:include page="common/Menu_dinamico.jsp"/>
            </div>
            <div class="col-md-6">
                    <jsp:include page="common/Header_1.jsp" />
            </div>
	</div>
        <jsp:include page="common/AlertaSession.jsp"/>
        <div class="container container-main-content container-examen">
            <div class="row d-flex justify-content-center">
                <div class="accordion col-12 pl-0 pr-0 mb-4" id="examen-main">
                    <div id="panelSuperior" class="card">
                        <div class="card-header" id="registro-paciente-title">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true">B&uacute;squeda de Examen </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="descargaExcelExamen" class="navi-link"> <span
                                        class='symbol symbol-30 symbol-circle '> <span
                                            id="circuloAgregarExamen"
                                            class='symbol-label bg-hover-blue  hoverIcon' href="#"
                                            data-toggle="tooltip" title="Descarga Excel"> <i
                                                id="iDescargarExamen" class='fas fa-plus  text-blue'></i>
                                        </span>
                                    </span>
                                    </a> 
                                    <a id="nuevoExamen" class="navi-link"> <span
                                        class='symbol symbol-30 symbol-circle '> <span
                                            id="circuloAgregarExamen"
                                            class='symbol-label bg-hover-blue  hoverIcon' href="#"
                                            data-toggle="tooltip" title="Nuevo examen"> <i
                                                id="iAgregarTest" class='fas fa-plus  text-blue'></i>
                                        </span>
                                    </span>
                                    </a> <a id="btnLimpiarFiltro" class="navi-link"> <span
                                        class="symbol symbol-30 symbol-circle "> <span
                                            id="circuloLimpiarFiltro"
                                            class="symbol-label bg-hover-blue  hoverIcon" href="#"
                                            data-toggle="tooltip" title="Limpiar"> <i
                                                id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span> <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content" data-parent="#examen-main">
                            <div class="card-body row d-flex">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col-6">
                                    <div class=" row">
                                        <label class="col-form-label text-left col-4 ">C&oacute;digo</label>
                                        <div class="col-7">
                                            <input id="txtCodigoFiltro" class="form-control" type="text" />
                                        </div>
                                    </div>
                                    <div class=" row">
                                        <label class="col-form-label text-left col-4 ">Nombre</label>
                                        <div class="col-7">
                                            <input id="txtNombreFiltro" class="form-control" type="text" />
                                        </div>
                                    </div>
                                    <div class=" row">
                                        <label class="col-form-label text-left col-4 ">Secci&oacute;n</label>
                                        <div class="col-7">
                                            <select id="selectSeccionFiltro" class="form-control selectpicker" data-live-search="true">
                                                     
                                            </select>
                                        </div>
                                    </div>
                                    <div class=" row">
                                        <div class="col-4">
                                            <label class="checkbox checkbox-primary">
                                                <input id="chkMostrarActivos" type="checkbox" value="N" /> Mostrar solo activos
                                                <span></span>
                                            </label>
                                        </div>
                                    </div>
                                    <a id="btnBuscarFiltro" class="btn btn-blue-primary btn-lg font-weight-bold mt-4 mr-2"><i class="la la-search"></i>Buscar</a>
                                </div>

                                <!-- FIN FORMULARIO FILTRO -->
                                <!-- TABLA FILTRO -->
                                <div class="col-6">
                                    <div class="table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th id="thIdentificador" scope="col">C&oacute;digo</th>
                                                    <th id="thNombre" scope="col">Nombre del Examen</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron ex&aacute;menes con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro" class="ocultar">Ex&aacute;menes encontrados: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>

                    <div id="panelInferior" class="card panelInferior border-15">
                        <div class="card-header border-15">
                            <div class="card-title row justify-content-between  ">
                                <h3 class="card-label col-2 pl-0">Registro de Examen</h3>
    
                            <!-- Inicio Tabuladores -->
                            <div class="col-8">
                                <ul class="nav nav-pills pills-examen">
                                    <li class="nav-item"><a id="anchorRegistro"
                                            class="nav-link active" data-toggle="tab"
                                            href="#kt_tab_pane_1"> <span
                                                class="nav-icon"><i
                                                    class="flaticon2-paper"></i></span> <span
                                                class="nav-text">Datos&nbsp;
                                                B&aacute;sicos</span>
                                        </a></li>
                                    <li id="liDatosMadres" class="nav-item">
                                        <a class="nav-link" data-toggle="tab"
                                            href="#kt_tab_pane_2">
                                            <span class="nav-icon"><i
                                                    class="flaticon2-copy"></i></span>
                                            <span class="nav-text">Test</span>
                                        </a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab"
                                            href="#kt_tab_pane_3"> <span class="nav-icon"><i
                                                    class="la la-phone"></i></span> <span
                                                class="nav-text">Muestras</span>
                                        </a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab"
                                            href="#kt_tab_pane_4"> <span class="nav-icon"><i
                                                    class="la la-phone"></i></span>
                                            <span class="nav-text">Procesamiento</span>
                                        </a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab"
                                            href="#kt_tab_pane_5"> <span class="nav-icon"><i
                                                    class="la la-phone"></i></span>
                                            <span class="nav-text">Estad&iacute;sticas</span>
                                        </a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab"
                                            href="#kt_tab_pane_6"> <span class="nav-icon"><i
                                                    class="la la-phone"></i></span>
                                            <span class="nav-text">Datos Extras</span>
                                        </a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab"
                                            href="#kt_tab_pane_7"> <span class="nav-icon"><i
                                                    class="la la-phone"></i></span>
                                            <span class="nav-text">Impresi&oacute;n</span>
                                        </a></li>
                                </ul>

                            </div>
                            <!-- Fin Tabuladores -->
                            <!-- ************************************** Botonera  **************************-->
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
                                        <a id="btnEditar" class="navi-link pointer"> <span
                                                class='symbol symbol-30 symbol-circle'>
                                                <span id="circuloEditarExamen"
                                                    class='symbol-label circuloIcon'
                                                    data-toggle="tooltip" title="Editar examen">
                                                    <i id="iEditExamen"
                                                        class="far fa-edit text-dark-50 "></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                                <div class="float-right">
                                    <div id="divbuscarPaciente">
                                        <a id="buscarExamen" class="navi-link" href="#"> <span
                                                class='symbol symbol-30 symbol-circle mr-3'> <span
                                                    id="circuloBuscarExamen"
                                                    class='symbol-label bg-hover-blue hoverIcon'
                                                        data-toggle="tooltip" title="Buscar examen"><i
                                                        id="iBuscarExamen"
                                                        class="fas fa-search text-blue"></i></span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <!-- ************************************** Botonera  **************************-->
                            </div>
                        </div>
                        <div class="card-body no-gutters">
                            <form id="formRegistroTest" class="h-100" method="post">
                                <div id="divForm" class="row h-100 align-content-between">
                                    <div class="col-12">
                                        <h5 id="tituloRegistro" class="card-label text-center" style="color: black;">Examen</h5>
                                        <div class="tab-content" id="myTabContent">
                                            <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_2">
                                                <div class="row form-group align-items-center col-12">
                                                    <input id="txtIdExamen" name="idExamen" type="text" class="form-control ocultar" />
                                                    <div class="col-md-4">
                                                        <label>C&oacute;digo *</label>
                                                        <input id="txtCodigo" name="ceCodigoexamen" type="text" class="form-control formExamen" maxlength="15" autocomplete="off" placeholder=""/>
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>                                                
                                                    <div class="col-md-4 ">
                                                        <label>Nombre *</label>
                                                        <input id="txtNombre" name="ceAbreviado" type="text" class="form-control formExamen" maxlength="50" autocomplete="off" placeholder=""/>
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>                                                
                                                    <div class="col-md-4 ">
                                                        <label>Nombres alternativos</label>
                                                        <input id="txtDescripcion" name="ceDescripcion" type="text" class="form-control formExamen" maxlength="80" autocomplete="off" placeholder=""/>
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-md-4 mt-1">
                                                        <label>Nombre de etiqueta</label>
                                                        <input id="txtEtiqueta" name="ceAbreviado2" type="text" class="form-control formExamen" maxlength="10" autocomplete="off" placeholder=""/>
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-md-4 mt-1">
                                                        <label>C&oacute;digo FONASA</label>
                                                        <input id="txtCodigoFonasa" name="ceCodigofonasa" type="text" class="form-control formExamen" maxlength="20" autocomplete="off" placeholder=""/>
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-md-4 mt-1">
                                                        <label>Derivador</label>
                                                        <select id="selectDerivador" name="cderivIdderivador" class="form-control selectpicker formExamen" data-live-search="true"></select>
                                                    </div>
                                                    <div class="col-12 mt-4">
                                                        <label class="checkbox checkbox-primary">
                                                            <input id="chkSoloPrestacion" type="checkbox" name="ceEsexamen" value="S" class="formExamen" /><b>Solo Prestaci&oacute;n:</b> Es prestaci&oacute;n y no se gestiona como examen de laboratorio (no contiene tests)
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                    <div class="col-12">
                                                        <label class="checkbox checkbox-primary">
                                                            <input id="chkMicrobiologia" type="checkbox" value="N" class="formExamen" /><b>Microbiolog&iacute;a</b>
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                    <div class="col-12">
                                                        <label class="checkbox checkbox-primary">
                                                            <input id="chkEsCultivo" type="checkbox" name="ceEscultivo" value="N" class="formExamen" /><b>Requiere cultivo</b>
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
                                                <div class="col-12">
                                                   
                                                    <!--<div class="col-12"><a class="btn btn-light-primary font-weight-bold mr-2 pointer mt-5" data-toggle="modal" data-target="#exampleModalSizeSm">A&ntilde;adir tests</a></div>-->
                                                    <div class="mt-4">
                                                        <!--
                                                        <div class="row">
                                                            <label class="col-4">
                                                                <input id="checkBoxCambioMuestra" type="checkbox" class="formExamen" /> Puede cambiar muestra
                                                            </label>
                                                            <button id="btnModalCambioMuestras" type="button" data-toggle="modal" data-target="#modalDatosMuestras"
                                                                    class="btn btn-blue-primary" disabled>
                                                                    Seleccionar muestras
                                                            </button>
                                                        </div>
                                                        -->
                                                        <div class="row" id="contenedorTestsExamen">
                                                            <div class="col-3">
                                                                <label class="">Filtro por Secci&oacute;n</label>
                                                                <select class="form-control selectpicker formExamen" id="selectSeccionTest" data-live-search="true"></select>
                                                                <div class="row pt-4">
                                                                    <div class="col pr-0">
                                                                        <input type="text" id="idSearcherTest" class="formExamen" inputmode="search" class="col-12" style="width:100%" />
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col pr-0">
                                                                        <select id="idDualDataVisualSourceTest" class="formExamen" size="5" tabindex="-1" style="width: 100%;">
                                                                                <option value="-1" disabled style="display: none;">Seleccionar</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-1 d-flex align-items-center">
                                                                <div id="dualDataButtonBar" class="mx-auto">
                                                                    <div class="col-12">
                                                                        <button id="clickAddBtnTest" type="button"
                                                                                class="btn btn-blue-primary formExamen">
                                                                                <i class="fas fa-arrow-right" tabindex="-1"></i>
                                                                        </button>
                                                                    </div>
                                                                    <div class="col-12">
                                                                        <button id="clickDelBtnTest" type="button"
                                                                                class="btn btn-blue-primary formExamen">
                                                                                <i class="fas fa-arrow-left" tabindex="-1"></i>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-8 pr-5">
                                                                <div class="card card-2 h-100">
                                                                    <div class="card-body p-0">
                                                                        <table id="tablaTestExamenes" class="table display nowrap compact-xs table-selected">
                                                                            <thead>
                                                                                <tr>
                                                                                    <!--<th scope="col">#</th>-->
                                                                                    <th>Codigo</th>
                                                                                    <th>Test</th>
                                                                                    <th>Muestra</th>
                                                                                    <th>Secci&oacute;n</th>
                                                                                    <!--<th>Analizador</th>-->
                                                                                    <th>M&eacute;todo</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody id="tbodyTestSeleccionados">
                
                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-12 mt-4 pl-0">
                                                        <div class="row">
                                                            <div class="col-12 p-4">
                                                                <table class="table table-bordered">
                                                                    <tbody>
                                                                      <tr>
                                                                        <td class="col-6">
                                                                            <div class="form-group row mb-0">
                                                                                <label class="col-2 col-form-label"><b>Muestra</b></label>
                                                                                <div class="col-10">
                                                                                    <div id="divMuestras" class="containerDivHeight"></div>
                                                                                </div>
                                                                            </div> 
                                                                        </td>
                                                                        <td class="col-6">
                                                                            <div class="form-group row mb-0">
                                                                                <label class="col-2 col-form-label"><b>Secci&oacute;n</b></label>
                                                                                <div class="col-10">
                                                                                    <div id="divSeccion" class="containerDivHeight"></div>
                                                                                </div>
                                                                            </div> 
                                                                        </td>
                                                                      </tr>
                                                                      <tr>
                                                                        <td class="col-6">
                                                                            <div class="form-group row mb-0">
                                                                                <label class="col-2 col-form-label"><b>M&eacute;todo</b></label>
                                                                                <div class="col-10">
                                                                                    <div id="divMetodo" class="containerDivHeight"></div>
                                                                                </div>
                                                                            </div> 
                                                                        </td>
                                                                        <td class="col-6"></td>
                                                                      </tr>
                                                                
                                                                    </tbody>
                                                                  </table>
                                                            </div>
                                                                                       
                                                                <!--
                                                                <div class="col-6 ">
                                                                    <label>Analizador: </label>
                                                                    <div id="divAnalizador"></div>
                                                                    <div class="d-md-none mb-2"></div>
                                                                </div>
                                                                -->
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">
                                                <div class="col-12">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="row">
                                                                <span class="col-4">
                                                                    <div class="form-group d-flex justify-content-center mb-0">
                                                                        <label class="col-5 col-form-label"> <b>Muestra del examen</b></label>
                                                                        <div class="col-7 row">
                                                                            <input id="muestraExamen" type="text" class="form-control formExamen" readonly />
                                                                        </div>
                                                                    </div>
                                                                </span>
                                                                <label class="col-3">
                                                                    <input id="checkBoxCambioMuestra" type="checkbox" class="formExamen" /> Puede cambiar muestra
                                                                </label>
                                                                <button id="btnModalCambioMuestras" type="button" data-toggle="modal" data-target="#modalDatosMuestras"
                                                                        class="btn btn-blue-primary btn-lg font-weight-bold mr-2" disabled>
                                                                        Seleccionar muestras
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <div class="col-8 mt-3">
                                                            <div class="card card-2 h-100">
                                                                <div class="card-body">
                                                                    <h5 class="text-center">Tiempo de estabilidad</h5>
                                                                    <div class="row mt-4">
                                                                        <div class="col-4">
                                                                            <label class="">T° Ambiente</label>
                                                                            <div class="col-12 row ml-0 p-0">
                                                                                <input id="txtEstabilidadAmbiental" class="form-control formExamen col-xl-5 col-md-5" maxlength="20" type="number" />
                                                                                <div class="col-xl-7 col-md-7 pr-0">
                                                                                    <select id="selectEstabilidadAmbiental" class="form-control formExamen">
                                                                                        <option value="días">D&iacute;a(s)</option>
                                                                                        <option value="horas">Hora(s)</option>
                                                                                        <option value="minutos">Minuto(s)</option>
                                                                                        <option value="No aplica">No aplica</option>
                                                                                    </select>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-4">
                                                                            <label class="">Refrigerado (4 - 8°C)</label>
                                                                            <div class="col-12 row ml-0 p-0">
                                                                                <input id="txtEstabilidadRefrigerado" class="form-control formExamen col-xl-5 col-md-5"  maxlength="20" type="number" />
                                                                                <div class="col-xl-7 col-md-7 pr-md-0">
                                                                                    <select id="selectEstabilidadRefrigerado" class="form-control formExamen">
                                                                                        <option value="días">D&iacute;a(s)</option>
                                                                                        <option value="horas">Hora(s)</option>
                                                                                        <option value="minutos">Minuto(s)</option>
                                                                                        <option value="No aplica">No aplica</option>
                                                                                    </select>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-4">
                                                                            <label class="">Congelado (-20°C)</label>
                                                                            <div class="col-12 row ml-0 p-0">
                                                                                <input id="txtEstabilidadCongelado" class="form-control formExamen col-xl-5 col-md-5"  maxlength="20" type="number" />
                                                                                <div class="col-xl-7 col-md-7 pr-md-0">
                                                                                    <select id="selectEstabilidadCongelado" class="form-control formExamen">
                                                                                        <option value="días">D&iacute;a(s)</option>
                                                                                        <option value="horas">Hora(s)</option>
                                                                                        <option value="minutos">Minuto(s)</option>
                                                                                        <option value="No aplica">No aplica</option>
                                                                                    </select>
                                                                                </div>
                                                                            </div>
                                                                            
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-4 mt-3 pr-5">
                                                            
                                                            <div class="card card-2 h-100">
                                                                <div class="card-body">
                                                                    <h5 class="text-center">Volumen m&iacute;nimo</h5>
                                                                    <div class="row mt-4">
                                                                        <div class="col-6">
                                                                            <label class="">Adulto</label>
                                                                            <div class="col-12 row m-0 p-0">
                                                                                <input id="txtVolumenAdulto" class="form-control formExamen col-6"  type="text" />
                                                                                <label class="col-6">mL</label>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-6">
                                                                            <label class="">Ni&ntilde;os</label>
                                                                            <div class="col-12 row m-0 p-0">
                                                                                <input id="txtVolumenPediatrica" class="form-control formExamen col-6"  type="text" />
                                                                                <label class="col-6">&nbsp;mL</label>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                                
                                                                <!--
                                                                <div class="col-12 mt-4">
                                                                    <div class="card card-2">
                                                                        <div class="card-body">
                                                                            <div class="row col-12">
                                                                                <div class="form-group col-12">
                                                                                    <h5 class="text-center">Etiquetas</h5>
                                                                                    <div class="col-12">
                                                                                        <div class="form-group d-flex justify-content-center">
                                                                                            <label class="col-5 col-form-label">N° de etiquetas a imprimir</label>
                                                                                            <div class="col-8 row">
                                                                                                <input id="txtNroEtiquetas" class="form-control formExamen col-8"  type="number"  autocomplete="off" />
                                                                                                <label class="col-4">&nbsp;mL</label>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                -->
                                                        </div>
                                                        <div class="col-12 mt-4 pr-5">
                                                            <div class="card card-2 h-100">
                                                                <div class="card-body">
                                                                    <h5 class="ml-3 mb-0 line-height-2 mr-1 text-center">Indicaciones</h5>
                                                                    <div class="row">
                                                                        <div class="col-6 mt-4 containerIndicacionesTM">
                                                                            <div class="form-group d-flex">
                                                                                <h5 class="ml-3 mb-0 line-height-2 mr-1 col-10">Indicaciones para la toma de muestra</h5>
                                                                                <button id="addIndicacion" type="button" class="btn btn-blue-primary formExamen">
                                                                                        <i class="fa fa-plus pr-0" aria-hidden="true"></i>
                                                                                </button>
                                                                            </div>
                                                                            <table id="tablaIndicacionesTM" class="display col-12">
                                                                                <tbody id="tbodyIndicaciones"></tbody>
                                                                            </table>
                                                                        </div>

                                                                        <div class="col-6 mt-4">
                                                                            <h5 class="text-center">Indicaciones para el paciente</h5>
                                                                            <select id="selectIndicacionesPaciente" class="form-control selectpicker formExamen" data-live-search="true"></select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="kt_tab_pane_4" role="tabpanel" aria-labelledby="kt_tab_pane_4">
                                                <div class="row justify-content-center">
                                                    <div class="col-3">
                                                        <div class="card card-2 h-100">
                                                            <div class="card-body">
                                                                <h5 class="text-center">D&iacute;as de procesamiento</h5>
                                                                <div class="col-12">
                                                                    <label class="checkbox checkbox-primary">
                                                                        <input id="chkDiaProcesoLunes" type="checkbox" class="formExamen" /> Lunes
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-12">
                                                                    <label class="checkbox checkbox-primary">
                                                                        <input id="chkDiaProcesoMartes" type="checkbox" class="formExamen" /> Martes
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-12">
                                                                    <label class="checkbox checkbox-primary">
                                                                        <input id="chkDiaProcesoMiercoles" type="checkbox" class="formExamen" /> Mi&eacute;rcoles
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-12">
                                                                    <label class="checkbox checkbox-primary">
                                                                        <input id="chkDiaProcesoJueves" type="checkbox" class="formExamen" /> Jueves
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-12">
                                                                    <label class="checkbox checkbox-primary">
                                                                        <input id="chkDiaProcesoViernes" type="checkbox" class="formExamen" /> Viernes
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-12">
                                                                    <label class="checkbox checkbox-primary">
                                                                        <input id="chkDiaProcesoSabado" type="checkbox" class="formExamen" /> S&aacute;bado
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-12">
                                                                    <label class="checkbox checkbox-primary">
                                                                        <input id="chkDiaProcesoDomingo" type="checkbox" class="formExamen" /> Domingo
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-8">
                                                        <div class="row">
                                                            <div class="col-12">
                                                                <div class="row">
                                                                    <div class="col-6">
                                                                        <div class="card card-2 h-100">
                                                                            <div class="card-body">
                                                                                <h5 class="text-center">Plazo de entrega</h5>
                                                                                <div class="form-group d-flex justify-content-center">
                                                                                    <label class="col-xl-4 col-md-5 col-form-label">Ambulatorio</label>
                                                                                    <div class="col-xl-4 col-md-3">
                                                                                        <input id="txtTiempoEntregaNormal" class="form-control formExamen"  type="text" />
                                                                                    </div>
                                                                                    <div class="col-4">
                                                                                        <select id="selectTiempoEntregaNormal" class="form-control formExamen">
                                                                                            <option value="d">D&iacute;a(s)</option>
                                                                                            <option value="h">Hora(s)</option>
                                                                                            <option value="m">Minuto(s)</option>
                                                                                        </select>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group d-flex justify-content-center">
                                                                                    <label class="col-xl-4 col-md-5 col-form-label">Hospitalizado</label>
                                                                                    <div class="col-xl-4 col-md-3">
                                                                                        <input id="txtTiempoEntregaHospitalizado" class="form-control formExamen"  type="text" />
                                                                                    </div>
                                                                                    <div class="col-4">
                                                                                        <select id="selectTiempoEntregaHospitalizado" class="form-control formExamen">
                                                                                            <option value="d">D&iacute;a(s)</option>
                                                                                            <option value="h">Hora(s)</option>
                                                                                            <option value="m">Minuto(s)</option>
                                                                                        </select>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group d-flex justify-content-center">
                                                                                    <label class="col-xl-4 col-md-5 col-form-label">Urgencia</label>
                                                                                    <div class="col-xl-4 col-md-3">
                                                                                        <input id="txtTiempoEntregaUrgencia" class="form-control formExamen"  type="text" />
                                                                                    </div>
                                                                                    <div class="col-4">
                                                                                        <select id="selectTiempoEntregaUrgencia" class="form-control formExamen">
                                                                                            <option value="d">D&iacute;a(s)</option>
                                                                                            <option value="h">Hora(s)</option>
                                                                                            <option value="m">Minuto(s)</option>
                                                                                        </select>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    
                                                                    <div class="col-6">
                                                                        <div class="card card-2 h-100">
                                                                            <div class="card-body d-flex justify-content-center flex-wrap">
                                                                                <h5 class="text-center col-12">Etiquetas</h5>
                                                                                <div class="col-12 mt-3">
                                                                                    <div class="form-group d-flex justify-content-center">
                                                                                        <label class="col-xl-6 col-md-8 col-form-label">N° de etiquetas a imprimir</label>
                                                                                        <div class="col-4 row">
                                                                                            <input id="txtEtiquetasCantidad" class="form-control formExamen" type="number" value="1" min="1" />
                                                                                        </div>
                                                                                    </div>
                                                                                </div> 
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-12 mt-4">
                                                                <div class="row">
                                                                    <div class="col-6">
                                                                        <div class="card card-2 h-100">
                                                                            <div class="card-body">
                                                                                <h5 class="text-center">D&iacute;as de Validez</h5>
                                                                                <div class="form-group d-flex justify-content-center">
                                                                                    <label class="col-xl-4 col-md-5 col-form-label">Ambulatorio</label>
                                                                                    <div class="col-xl-4 col-md-3">
                                                                                        <input id="txtDiasValidezAmbulatorio" class="form-control formExamen" type="number" value="0" />
                                                                                    </div>
                                                                                    <div class="col-4">
                                                                                        <label class="checkbox checkbox-primary">
                                                                                            <input id="chkObligadoAmbulatorio" type="checkbox" class="formExamen" /> Obligatorio
                                                                                            <span></span>
                                                                                        </label>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group d-flex justify-content-center">
                                                                                    <label class="col-xl-4 col-md-5 col-form-label">Hospitalizado</label>
                                                                                    <div class="col-xl-4 col-md-3">
                                                                                        <input id="txtDiasValidezHospitalizado" class="form-control formExamen" type="number" value="0" />
                                                                                    </div>
                                                                                    <div class="col-4">
                                                                                        <label class="checkbox checkbox-primary">
                                                                                            <input id="chkObligadoHospitalizado" type="checkbox" class="formExamen" /> Obligatorio
                                                                                            <span></span>
                                                                                        </label>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-12 mt-3">
                                                                                </div> 
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-6">
                                                                        <div class="card card-2 h-100">
                                                                            <div class="card-body d-flex justify-content-center flex-wrap">
                                                                                <h5 class="text-center col-12">Contabilidad</h5>
                                                                                <div class="col-12 mt-3">
                                                                                    <div class="form-group d-flex justify-content-center">
                                                                                        <label class="col-xl-6 col-md-8 col-form-label"> Cantidad de ex&aacute;menes</label>
                                                                                        <div class="col-4 row">
                                                                                            <input id="txtContableCantidad" class="form-control formExamen" type="number" value="1" min="1" />
                                                                                        </div>
                                                                                    </div>
                                                                                </div> 
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                               
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-12 mt-4">
                                                        <div class="row justify-content-center">
                                                            <!--
                                                            <div class="col-5">
                                                                <div class="card card-2 h-100">
                                                                    <div class="card-body">
                                                                        <h5 class="text-center">Indicaciones para el paciente</h5>
                                                                        <select id="selectIndicacionesPaciente" class="form-control formExamen"></select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            -->
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="kt_tab_pane_5" role="tabpanel" aria-labelledby="kt_tab_pane_5">
                                                <div class="col-12">
                                                    <div class="form-group row">
                                                        <div class="col-12">
                                                            <label class="checkbox checkbox-primary">
                                                                <input id="chkEstadistica" type="checkbox" class="formExamen" /> <b>Estad&iacute;stica:</b> El examen debe ser considerado para estad&iacute;sticas
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                        <div class="col-12">
                                                            <label class="checkbox checkbox-primary mt-3">
                                                                <input id="chkIndicador" type="checkbox" class="formExamen" /> <b>Indicador:</b> El examen se considera en los indicadores de calidad
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                        <div class="col-12">
                                                            <label class="checkbox checkbox-primary mt-3">
                                                                <input id="chkHojaTrabajo" type="checkbox" class="formExamen" /> <b>Hoja de Trabajo:</b> Se incluye en la hoja de trabajo
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                        <div class="col-12">
                                                            <label class="checkbox checkbox-primary mt-3">
                                                                <input id="chkPlanillaHistorica" type="checkbox" class="formExamen" /> <b>Planilla Hist&oacute;rica:</b> Se incluye en la planilla hist&oacute;rica
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                        <div class="col-4">
                                                            <label class="checkbox checkbox-primary mt-3">
                                                                <input id="chkPesquisaObligatoria" type="checkbox" class="formExamen" disabled /> <b>Pesquisa Obligatoria</b>
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                     
                                                        <div class="col-8">
                                                            <div class="form-group d-flex mb-0 mt-3">
                                                                <label class="col-3 col-form-label">Seleccionar Pesquisa</label>
                                                                <div class="col-6 row">
                                                                    <select id="selectPesquisa" class="form-control formExamen selectpicker" data-live-search="true"></select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row form-group align-items-center col-12">
                                                    <div class="col-4">
                                                        <label>Grupo FONASA</label>
                                                        <select id="selectGrupoFonasa" class="form-control formExamen selectpicker" data-live-search="true"></select>
                                                    </div>   
                                                   
                                                    <div class="col-4">
                                                        <label>LOINC</label>
                                                        <input id="selectLoinc" class="form-control formExamen"  type="text" />
                                                        <!-- <select id="selectLoinc" class="form-control formExamen"></select> -->
                                                    </div>  
                                                    <div class="col-12 mt-4 pr-0">
                                                        <button id="btnInoperabilidad" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2" data-toggle="modal" data-target="#modalInteroperabilidad">
                                                            Interoperabilidad (Host)
                                                        </button>
                                                    </div>
        
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="kt_tab_pane_6" role="tabpanel" aria-labelledby="kt_tab_pane_6">
                                                <div class="col-12">
                                                    <div class="form-group row">
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="col-8">
                                                                    <label class="checkbox checkbox-primary mt-3">
                                                                        <input id="chkExamenUrgente" type="checkbox" class="formExamen" /> <b>Examen Urgente:</b> Examen con tiempo de respuesta Urgente, siempre
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-4 d-flex justify-content-center">
                                                                    <a class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-toggle="modal" data-target="#modalExamenConjunto">Ex&aacute;menes Conjuntos</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="col-8">
                                                                    <label class="checkbox checkbox-primary mt-2">
                                                                        <input id="chkExamenConfidencial" type="checkbox" class="formExamen" /> <b>Examen Confidencial:</b> Examen restringido para su visualizaci&oacute;n o impresi&oacute;n
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-4"></div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="col-8">
                                                                    <label class="checkbox checkbox-primary mt-4">
                                                                        <input id="chkAutovalidacion" type="checkbox" class="formExamen" /> <b>Autovalidaci&oacute;n:</b> Permite la firma autom&aacute;tica del examen
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-4 d-flex justify-content-center">
                                                                    <a class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-toggle="modal" data-target="#modalTablaReferencias">Tabla de Referencias</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="col-8">
                                                                    <label class="checkbox checkbox-primary mt-3">
                                                                        <input id="chkDiagnosticoObligatorio" type="checkbox" class="formExamen" /> <b>Diagn&oacute;stico Obligatorio:</b> Es necesario ingresar el Diagn&oacute;stico en la Orden
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-4"></div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="col-8">
                                                                    <label class="checkbox checkbox-primary mt-3">
                                                                        <input id="chkCompartirMuestra" type="checkbox" class="formExamen" /> <b>Compartir Muestra: </b>Puede compartir muestra con otros ex&aacute;menes
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-4"></div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="col-8">
                                                                    <label class="checkbox checkbox-primary mt-3">
                                                                        <input id="chkExamenTipoCurva" type="checkbox" class="formExamen" /> <b>Examen Tipo Curva:</b> Examen tipo curva de tolerancia
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-4"></div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="col-8">
                                                                    <label class="checkbox checkbox-primary mt-3">
                                                                        <input id="chkTieneTestOpcionales" type="checkbox" class="formExamen" /> <b>Solicitar Test Opcionales:</b> Para ex&aacute;menes con diferentes puntos
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-4"></div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="col-8">
                                                                    <label class="checkbox checkbox-primary mt-3">
                                                                        <input id="chkCellCounter" type="checkbox" class="formExamen" /> <b>Contador celular:</b> Compatible con el uso del contador hematol&oacute;gico
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                                <div class="col-4">
                                                                    <div class="form-group d-flex mb-0 mt-3">
                                                                        <label class="col-2">Orden</label>
                                                                        <div class="col-10 row">
                                                                            <input id="txtSortSeccion" type="number" value="1" min="1" class="form-control formExamen"  autocomplete="off" placeholder=""/>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="col-8">
                                                                    <div class="row">
                                                                        <div class="col-3">
                                                                            <label class="checkbox checkbox-primary mt-3">
                                                                                <input id="chkDisponibleWeb" type="checkbox" class="formExamen" /> <b>Disponible en web</b>
                                                                                <span></span>
                                                                            </label>
                                                                        </div>
                                                                     
                                                                        <div class="col-9">
                                                                            <div class="form-group d-flex mb-0 mt-3">
                                                                                <label class="col-auto">Nombre Web</label>
                                                                                <div class="col-6 row">
                                                                                    <input id="txtNombreWeb" type="text" class="form-control formExamen" maxlength="50" autocomplete="off" placeholder=""/>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-4">
                                                                    <div class="form-group d-flex mb-0 mt-3">
                                                                        <label class="col-2">Nota</label>
                                                                        <div class="col-10 row">
                                                                            <input id="txtNota" maxlength="500" type="text" class="form-control formExamen col-12" autocomplete="off" placeholder=""/>   
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                         
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="kt_tab_pane_7" role="tabpanel" aria-labelledby="kt_tab_pane_7">
                                                <div class="col-12">
                                                    <div class="form-group row">
                                                        <div class="col-12">
                                                            <label class="checkbox checkbox-primary mt-3">
                                                                <input id="chkImprimirSeparado" type="checkbox" class="formExamen" /> <b>Imprimir Separado</b>
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                        <div class="col-12">
                                                            <label class="checkbox checkbox-primary mt-3">
                                                                <input id="chkImprimirDescripcion" type="checkbox" class="formExamen" /> <b>Imprimir Descripci&oacute;n</b>
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                        <div class="col-12">
                                                            <label class="checkbox checkbox-primary mt-3">
                                                                <input id="chkImprimirPorSeccion" type="checkbox" class="formExamen" /> <b>Imprimir por Secci&oacute;n</b>
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                        <div class="col-12">
                                                            <label class="checkbox checkbox-primary mt-3">
                                                                <input id="chkImprimirTabla" type="checkbox" class="formExamen" /> <b>Imprimir Tabla</b>
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="row">
                                                                <div class="col-2">
                                                                    <label class="checkbox checkbox-primary mt-3">
                                                                        <input id="chkMultiformato" type="checkbox" class="formExamen" /> <b>Multiformato</b>
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                             
                                                                <div class="col-8">
                                                                    <div class="form-group d-flex mb-0 mt-3">
                                                                        <label class="col-auto">Formato de impresi&oacute;n</label>
                                                                        <div class="col-6 row">
                                                                            <select id="selectFormatoImpresion" class="form-control formExamen selectpicker" data-live-search="true"></select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-6 pl-0">
                                                            <div class="form-group d-flex mb-0 mt-3">
                                                                <label class="col-auto">Grupo de ex&aacute;menes para informes</label>
                                                                <div class="col-12 row">
                                                                    <select id="selectGrupoExamenes" class="form-control formExamen selectpicker" data-live-search="true"></select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="col-md-6 form-group mt-6 row">
                                            <div class="col-12">
                                                <div class="row">
                                                    <label id="lblEstado" class="col-3 col-form-label">Activo</label>
                                                    <div class="col-3">
                                                        <label class="switch-content switch-comprobante switch-active"> <input
                                                            id='checkBoxLever' type="checkbox" tabindex="-1" name='mcActivo' class="formExamen" checked value='S'>
                                                            <div></div>
                                                        </label> 
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="divAgregarBtn" class="col-6">
                                                <button id="btnAgregarExamen" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Guardar</button>
                                            </div>
                                            <div id="divActualizarBtn" class="col-6 ocultar">
                                                <button id="btnUpdateExamen" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Confirmar</button>
                                                <button id="btnCancelExamen" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!--MODAL MUESTRAS-->
                                    <div class="modal fade" id="modalDatosMuestras" tabindex="-1" role="dialog" aria-labelledby="exampleModalSizeSm" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                                            <div class="modal-content">
                                                <div class="modal-body">
                                                    <h5 class="modal-title" id="exampleModalLabel">Selecci&oacute;n de Muestras</h5>
                                                    <div class="row" id="contenedorTestsMuestras">
                                                            <div class="col-4">
                                                                <div class="row pt-3">
                                                                    <div class="col pr-0">
                                                                        <input type="text" id="idSearcherMuestra" class="formExamen" inputmode="search" class="col-12" style="width:100%" />
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col pr-0">
                                                                        <select id="idDualDataVisualSourceMuestra" class="formExamen" size="5" tabindex="-1" style="width: 100%;">
                                                                                <option value="-1" disabled style="display: none;">Seleccionar</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                
                                                            </div>
                                                        
                                                            <div class="col-1 d-flex align-items-center">
                                                                <div id="dualDataButtonBar" class="mx-auto">
                                                                    <div class="col-12">
                                                                        <button id="clickAddBtnMuestra" type="button"
                                                                                class="btn btn-blue-primary formExamen">
                                                                                <i class="fas fa-arrow-right" tabindex="-1"></i>
                                                                        </button>
                                                                    </div>
                                                                    <div class="col-12">
                                                                        <button id="clickDelBtnMuestra" type="button"
                                                                                class="btn btn-blue-primary formExamen">
                                                                                <i class="fas fa-arrow-left" tabindex="-1"></i>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-7 containerTablaTestMuestras">
                                                                <div class="card card-2 h-100">
                                                                    <div class="card-body p-0">
                                                                        <table id="tablaTestMuestras" class="table display compact-xs table-selected">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>Muestra</th>
                                                                                    <th>Valor por defecto</th>
                                                                                </tr>
                                                                            </thead>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                    </div>
                                                    <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                                                        <button id="btnCerrarModalMuestras" type="button" class="btn btn-blue-primary btn-lg font-weight-bold cerrarModal" data-dismiss="modal">Ok</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!-- MODAL INOPERABILIDAD (HOST) -->
                                    <div class="modal fade" id="modalInteroperabilidad" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false" aria-labelledby="staticBackdrop" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-scrollable" role="document">
                                            <div class="modal-content">
        
                                                <div class="modal-body" >
                                                    <div class="col">
                                                        <h5 class="modal-title text-center" id="exampleModalLabel">Interoperabilidad (Host)</h5>
                                                        <div class="col-12">
                                                            <div class="form-group row">
                                                                <label class="col-3 col-form-label">Host 1:</label>
                                                                <div class="col-7">
                                                                    <input id="txtHost1" class="form-control formExamen" name="ctHost" type="text" maxlength="20" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row">
                                                                <label class="col-3 col-form-label">Host 2:</label>
                                                                <div class="col-7">
                                                                    <input id="txtHost2" class="form-control formExamen" name="ctHost" type="text" maxlength="20" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row">
                                                                <label class="col-3 col-form-label">Host 3:</label>
                                                                <div class="col-7">
                                                                    <input id="txtHost3" class="form-control formExamen" name="ctHost" type="text" maxlength="20" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row">
                                                                <label class="col-3 col-form-label">LIS BAC:</label>
                                                                <div class="col-7">
                                                                    <input id="txtHostMicro" class="form-control formExamen" name="ctHostmicro" type="text" maxlength="20" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-12 pl-0 pr-0 d-flex justify-content-between">
                                                        <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 cerrarModal" data-dismiss="modal">Ok</button>
        
                                                    </div>
                                                </div>
        
                                             </div>
                                         </div>
                                    </div>
                                    
                                    <!-- MODAL TABLA EXÁMENES CONJUNTOS -->
                                    <div class="modal fade" id="modalExamenConjunto" tabindex="-1" role="dialog" aria-labelledby="modalExamenConjunto" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                                            <div class="modal-content">
                                                <div class="modal-body">
                                                    <h5 class="modal-title" id="exampleModalLabel">Ex&aacute;menes Conjunto</h5>
                                                    <div class="row" id="contenedorExamenesConjuntos">
                                                    <input id="txtIdExamenesConjuntos" type="text" class="form-control ocultar" />
                                                        <div class="col-3">
                                                            <label class="">Filtro por Secci&oacute;n</label>
                                                            <select class="form-control selectpicker formExamen" id="selectSeccionExamenesConjuntos" data-live-search="true"></select>
                                                            <div class="row pt-3">
                                                                <div class="col pr-0">
                                                                    <input type="text" id="idSearcherExamen" class="formExamen" inputmode="search" class="col-12" style="width:100%" />
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col pr-0">
                                                                    <select id="idDualDataVisualSourceExamen" class="formExamen" size="5" tabindex="-1" style="width: 100%;">
                                                                            <option value="-1" disabled style="display: none;">Seleccionar</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                        <div class="col-1 d-flex align-items-center">
                                                            <div id="dualDataButtonBar" class="mx-auto">
                                                                <div class="col-12">
                                                                    <button id="clickAddBtnExamen" type="button"
                                                                            class="btn btn-blue-primary formExamen">
                                                                            <i class="fas fa-arrow-right" tabindex="-1"></i>
                                                                    </button>
                                                                </div>
                                                                <div class="col-12">
                                                                    <button id="clickDelBtnExamen" type="button"
                                                                            class="btn btn-blue-primary formExamen">
                                                                            <i class="fas fa-arrow-left" tabindex="-1"></i>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-7 ">
                                                            <div class="card card-2 h-100">
                                                                <div class="card-body p-0">
                                                                    <table id="tablaExamenesConjuntos" class="table">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>Ex&aacute;menes seleccionados</th>
                                                                            </tr>
                                                                        </thead>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                                                        <button type="button" class="btn btn-lg btn-blue-primary font-weight-bold cerrarModal" data-dismiss="modal">Ok</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!-- MODAL TABLA REFERENCIAS -->
                                    <div class="modal fade" id="modalTablaReferencias" tabindex="-1" role="dialog" aria-labelledby="modalTablaReferencias" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                                            <div class="modal-content">
                                               
                                                <div class="modal-body">
                                                    <h5 class="modal-title" id="titleModalTablaReferencias">Tabla de referencias </h5>
                                                    <div class="col-12 row">
                                                        <input id="txtIdTablaReferencia" type="text" class="form-control ocultar" />
                                                        <label>T&iacute;tulo de la tabla:</label>
                                                        <input id="tituloTablaAnexa" name="ceCodigoexamen" type="text" class="form-control formExamen" autocomplete="off" placeholder=""/>
                                                        
                                                        <label>Subt&iacute;tulo de la tabla:</label>
                                                        <input id="subtituloTablaAnexa" name="ceCodigoexamen" type="text" class="form-control formExamen" autocomplete="off" placeholder=""/>
                                                        
                                                        <div class="col-10 mx-auto">
                                                            <table id="tablaReferencias" class="table table-bordered mt-5">
                                                                <thead id="theadTablaRef">
                                                                    <tr>
                                                                        <td style="background-color: #ddd"></td>
                                                                        <th id="A" style="background-color: #ddd">A <!--<button type="button" class="btn btn-sm btn-danger btnDelete ocultar" onclick="//eliminarColumna('A')"><i class="far fa-trash-alt"></i></button>--></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="tbodyTablaRef">
                                                                    <tr id="1">
                                                                        <th onmouseover='//showBtnDel(this)' onmouseout='//hideBtnDel(this)' style="background-color: #ddd">1 <!--<button type="button" class="btn btn-sm btn-danger btnDelete ocultar" onclick="//eliminarFila(1)"><i class="far fa-trash-alt"></i></button>--></th>
                                                                        <td id="A1"><input id="A1" type='text' name='valorReferencia' style='visibility: hidden' value='' /></td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                            <a id="btnAddRow" class="btn btn-blue-primary formExamen"><i class="fas fa-angle-double-down"></i>Agregar Fila</a>
                                                        </div>
                                                        <div class="col-2 mx-auto">
                                                            <a  id="btnAddColumn" class="btn btn-blue-primary formExamen mt-5" ><i class="fas fa-angle-double-right"></i>Agregar Columna</a>
                                                        </div>
                                                        <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                                                            <button id="btnCerrarModalTest" type="button" class="btn btn-lg btn-blue-primary font-weight-bold cerrarModal" data-dismiss="modal">Ok</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        
                                    </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        
       <div>        <table id="tb" style="display:none;">
					    <thead>
					        <tr>
					            <th scope="col">Activo</th>
					            <th scope="col">Código</th>
					            <th scope="col">Nombre</th>
					            <th scope="col">Descripción</th>
					            <th scope="col">Etiqueta</th>
					            <th scope="col">Código FONASA</th>
					            <th scope="col">Derivador</th>
					        </tr>
					    </thead>
					        <tbody id="examenesParaExcel">

						    </tbody>
					</table></div>

        		<script src="<%=request.getContextPath()%>/resources/js/common/creadorExcell.js"></script>
  				<script  src="<%=request.getContextPath()%>/resources/js/portalEstadistica/xlsx.mini.js"></script>
               	<script src="https://unpkg.com/imask"></script>
                <jsp:include page="common/Scripts.jsp"/>
                <script	src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/dataTables.cellEdit.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/typeahead/typeahead.bundle.js"></script>
                
                <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgMuestras.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgTests.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/common/ArrayFunctions.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/examenes/ConfiguracionExamen.tests.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/examenes/ConfiguracionExamen.muestras.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/examenes/ConfiguracionExamen.examenesconjuntos.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/examenes/ConfiguracionExamen.tablareferencias.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/examenes/ConfiguracionExamen.binding.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/examenes/ConfiguracionExamen.js"></script>
                <script src="<%=request.getContextPath()%>/resources/js/examenes/ConfiguracionExamenForm.js"></script>
    </body>
</html>
