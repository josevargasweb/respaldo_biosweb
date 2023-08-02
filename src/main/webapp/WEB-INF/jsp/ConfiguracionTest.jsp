<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Test</title>
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
        <div class="container container-main-content container-configuracion mt-5">
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0">
                        <div id="panelTest" class="card border-15">
                            <div class="card-header border-15" id="headingTest">
                                <div class="card-title d-flex justify-content-between">
                                    <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion"
                                        data-toggle="collapse"
                                        data-target="#collapseHeader" aria-expanded="true"
                                        aria-controls="collapseHeader">B&uacute;squeda de Test</h3>
                                    <div class="card-buttons col-2 d-flex justify-content-end">
                                         <a id="descargarExcelTest" class="navi-link"> 
                                            <span  class='symbol symbol-30 symbol-circle '> 
                                                <span id="circuloAgregarTest" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Descargar Excel"> 
                                                    <i id="idescargarExcelTest" class='fas fa-plus text-blue'></i>
                                                </span>
                                            </span>
                                        </a> 
                                        <a id="nuevoTest" class="navi-link"> 
                                            <span  class='symbol symbol-30 symbol-circle '> 
                                                <span id="circuloAgregarTest" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nuevo test"> 
                                                    <i id="iAgregarTest" class='fas fa-plus text-blue'></i>
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
                            aria-labelledby="headingTest"
                            data-parent="#headingTest">
                                <div class="card-body row">
                                    <!-- FORMULARIO FILTRO -->
                                    <div class="col">
                                        <div class="col-12">
                                            <div class="form-group row mb-0">
                                                <label id="" class="col-4 col-form-label">C&oacute;digo</label>
                                                <div class="col-8">
                                                    <input id="txtCodigoFiltro" class="form-control" name="" onkeypress="return alpha(event)" type="text" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="form-group row mb-0">
                                                <label id="" class="col-4 col-form-label">Nombre</label>
                                                <div class="col-8">
                                                    <input id="txtNombreTestFiltro" class="form-control" name="" type="text" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="form-group row mb-0">
                                                <label class="col-4 col-form-label">Secci&oacute;n</label>
                                                <div class="col-8">
                                                    <select id="selectSeccionFiltro" class="form-control selectpicker" data-live-search="true">
                                                        <option value="0" selected disabled>TODOS</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="form-group row mb-0">
                                                <label class="col-4 col-form-label">Examen</label>
                                                <div class="col-8">
                                                    <select id="selectExamenFiltro" class="form-control selectpicker" data-live-search="true">
                                                        <option value="0" selected disabled>TODOS</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <label>
                                                <input id="chkMostrarActivos" type="checkbox" /> Mostrar solo activos
                                            </label>
                                        </div>
                                        <a id="btnBuscarFiltro" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  pointer"><i class="la la-search"></i>Buscar</a>
                                    </div>
                                    <!-- FIN FORMULARIO FILTRO -->
                                    <!-- TABLA FILTRO -->
                                    <div class="col">
                                        <div class="table-container mb-3">
                                            <table id="tableFiltro" class="table table-hover table-striped">
                                                <thead>
                                                    <tr id="trHeader">
                                                        <th scope="col">#</th>
                                                        <th id="thIdentificador" scope="col">Código</th>
                                                        <th id="thNombre" scope="col">Nombre del Test</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="tbodyFiltro">

                                                </tbody>
                                            </table>
                                            <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron tests con los datos provistos.</label>
                                        </div>
                                        <label id="lblTotalFiltro" class="ocultar">Tests encontrados: <span id="totalFiltro"></span></label>
                                    <!-- FIN TABLA FILTRO -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    
                    <!-- FORMULARIO REGISTRO TEST -->
                    <div id="panelTestResultados" class="card mt-5 border-15">
                        <div class="card-header border-15"> 
                            <div class="card-title row col-12 d-flex justify-content-between">
                                <h3 id="" class="card-label col-4 pl-0">
                                    Registro de Test
                                </h3>
                                <div class="col-6">
                                    <ul class="nav nav-pills">
                                        <li class="nav-item"><a id="anchorRegistro"
                                                class="nav-link active" data-toggle="tab"
                                                href="#panelDatosBasicos"> <span
                                                    class="nav-icon"><i
                                                        class="flaticon2-paper"></i></span> <span
                                                    class="nav-text">Datos&nbsp;
                                                    B&aacute;sicos</span>
                                            </a></li>
                                        <li class="nav-item"><a   id="anchorResultado" class="nav-link" data-toggle="tab"
                                                href="#panelResultado"> <span class="nav-icon"><i
                                                        class="flaticon2-copy"></i></span> <span
                                                    class="nav-text">Resultado</span>
                                            </a></li>
                                        <li class="nav-item"><a id="anchorExtra" class="nav-link" data-toggle="tab"
                                                href="#panelDatosExtras"> <span class="nav-icon"><i
                                                        class="fas fa-book-medical"></i></span>
                                                <span class="nav-text">Datos Extra</span>
                                            </a></li>
                                    </ul>

                                </div>
                                <!-- Fin Tabuladores -->
                                <!-- ************************************** Botonera Registro Paciente **************************-->
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
                                                    <span id="circuloEditarPaciente"
                                                        class='symbol-label circuloIcon'
                                                        data-toggle="tooltip" title="Editar test">
                                                        <i id="iEditPaciente"
                                                            class="far fa-edit text-dark-50 "></i>
                                                    </span>
                                                </span>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="float-right">
                                        <div id="divbuscarPaciente">
                                            <a id="buscarPaciente" class="navi-link" href="#"> <span
                                                    class='symbol symbol-30 symbol-circle mr-3'> <span
                                                        id="circuloBuscarPaciente"
                                                        class='symbol-label bg-hover-blue hoverIcon'
                                                         data-toggle="tooltip" title="Buscar test"><i
                                                            id="iBuscarPaciente"
                                                            class="fas fa-search text-blue"></i></span>
                                                </span>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <!-- ************************************** Botonera Registro Paciente **************************-->
                             
                            </div>
                        </div>
                        <div class="card-body">
                            <form id="formRegistroTest" method="post" enctype="multipart/form-data">
                                <div class="col-12 pl-0">
                                    <h5 id="tituloRegistro" class="card-label text-center">
                                        Test
                                    </h5>
                                </div>
                                <div id="divForm" class="row">
                                    <div class="col-12">
                                        <div class="tab-content" id="myTabContent">
                                            <div class="tab-pane fade show active" id="panelDatosBasicos" role="tabpanel" aria-labelledby="panelResultado">
                                                <div class="row">
                                                    <input id="txtIdTest" name="idTest" type="text" class="form-control ocultar" />
        
                                                    <div class="col-4">
                                                        <label>Código *</label>
                                                        <input id="txtCodigo" maxlength="15" name="ctCodigo" type="text" class="form-control formTest" autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>                                                
                                                    <div class="col-4">
                                                        <label>Nombre *</label>
                                                        <input id="txtNombre" maxlength="80" name="ctAbreviado" type="text" class="form-control formTest"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>                                                
                                                    <div class="col-4">
                                                        <label>Nombre alternativo *</label>
                                                        <input id="txtDescripcion" maxlength="80" name="ctDescripcion" type="text" class="form-control  formTest"  autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4 mt-1">
                                                        <label class="">Tipo de muestra *</label>
                                                        <select class="form-control  formTest selectpicker" id="selectTipoMuestra" name="cmueIdtipomuestra" data-live-search="true"></select>
                                                    </div>                                    
                                                    <div class="col-4 mt-1">
                                                        <label class="">Laboratorio *</label>
                                                        <select class="form-control  formTest selectpicker" id="selectLab" name="idLab" data-live-search="true"></select>
                                                    </div>                                            
                                                    <div class="col-4 mt-1">
                                                        <label class="">Sección *</label>
                                                        <select class="form-control  formTest selectpicker" id="selectSeccion" name="csecIdseccion" data-live-search="true"></select>
                                                    </div>
                                                    <div class="col-4 mt-1">
                                                        <label class="">Contenedor *</label>
                                                        <select class="form-control  formTest selectpicker" id="selectEnvase" name="cenvIdenvase" data-live-search="true">
                                                        </select>
                                                    </div>
                                                    <div class="col-4 mt-1">
                                                        <label class="col-12 mt-2">
                                                            <input id="checkBoxPlanillaHistorica" class="formTest" type="checkbox" name="ctPlanillahistorica" />&nbsp;Planilla histórica
                                                            <span></span>
                                                        </label>
                                                        <label class="col-12">
                                                            <input id="checkBoxHojaTrabajo" class="formTest" type="checkbox" name="ctTesthojatrabajo" />&nbsp;Hoja de trabajo
                                                            <span></span>
                                                        </label>
                                                        <label class="col-12">
                                                            <input id="checkBoxRequiereCultivo" class="formTest" type="checkbox" name="ctEscultivo" />&nbsp;Requiere cultivo
                                                            <span></span>
                                                        </label>
                                                    </div>
                                                    <div class="col-4 mt-5">
                                                        <button id="btnMetodos" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-toggle="modal" data-target="#modalMetodos">
                                                            Agregar métodos
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="panelResultado" role="tabpanel" aria-labelledby="panelResultado">
                                                <div class='row'>
                                                    <div class='col-md-5 col-xl-6 pl-0'>
                                                        <div class="col-md-12 ">
                                                            <label class="">Tipo de resultado *</label>
                                                            <select id="selectTipoResultado" class="form-control formTest selectpicker" name="tipoResultado" data-live-search="true">
                                                                <c:forEach var="tipoResultado" items="${listaTiposResultado}">
                                                                    <option value="${tipoResultado.ctrIdtiporesultado}">${tipoResultado.ctrDescripcion}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <label class="">Unidad de medida </label>
                                                            <select class="form-control disabledForm selectpicker formTest" id="selectUnidadMedida" name="cumIdunidadmedida" data-live-search="true">
                                                                <c:forEach var="unidadMedida" items="${listaUnidadesMedida}">
                                                                    <option value="${unidadMedida.cumIdunidadmedida}">${unidadMedida.cumDescripcion}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>   
                                                        <div class="col-md-12">
                                                            <label class="">Número de decimales </label>
                                                            <input id="txtNroDecimales" name="ctDecimales" type="text" class="form-control disabledForm formTest" onkeypress="return isNumberKey(event)" autocomplete="off" />
                                                        </div>
                                                        <div id="" class="col-md-12">
                                                            <label>Resultado por omisión </label>
                                                            <input id="txtResultadoOmision" maxlength="150" name="ctResultadoomision" type="text" class="form-control formTest"  autocomplete="off" />
                                                        </div>
                                                    </div>
                                                    <div class='col-md-7 col-xl-6'>
                                                        <div class="col-5 pl-0 pr-0">
                                                            <!---<div class="col-12">
                                                                <button id="btnFormula" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 w-100 d-flex justify-content-center align-items-center  disabledForm" >Fórmula</button>
                                                            </div>-->
                                                            <div class="col-12">
                                                                <button id="btnGlosas" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 w-100 d-flex justify-content-center align-items-center disabledForm" data-toggle="modal" data-target="#exampleModalSizeSm" >Glosas</button>
                                                            </div>
                                                            <div class="col-12">
                                                                <button id="btnValoresReferencia" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 d-flex justify-content-center align-items-center  w-100 disabledForm">Valores de referencia</button>
                                                            </div>
                                                            <div class="col-12">
                                                                <button id="btnDeltaCheck" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 w-100 d-flex justify-content-center align-items-center  disabledForm" data-toggle="modal" data-target="#modalDeltaCheck">Delta Check</button>
                                                            </div>
                                                            <div class="col-12">
                                                                <button id="btnAntecedentes" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 d-flex justify-content-center align-items-center w-100" data-toggle="modal" data-target="#modalAntecedentes">Antecedentes Paciente</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
        
                                            </div>
                                            <div class="tab-pane fade" id="panelDatosExtras" role="tabpanel" aria-labelledby="panelDatosExtras">
                                                <div>
                                                    <div class="row form-group align-items-center col-12 pl-0">
                                                        <div class="col-3">
                                                            <label>Volumen requerido </label>
                                                            <div class="form-row align-items-center">
                                                                <div class="col-10">
                                                                  <input  id="txtVolumenMuestra" name="ctVolumenmuestra" type="text" class="form-control  formTest"  autocomplete="off">
                                                                </div>
                                                                <div class="col-auto">
                                                                    <label class="form-check-label" for="autoSizingCheck">
                                                                     mL
                                                                    </label>
                                                                </div>
                                                              </div>
                                                        </div> 
                                                        <div class="col-3">
                                                            <label>Código LOINC </label>
                                                            <input id="txtCodigoLoinc" name="ctCodigoloinc" type="text" class="form-control  formTest"  autocomplete="off" />
                                                        </div>
                                                        <div class="col-3">
                                                            <label class="">Grupo Microbiología </label>
                                                            <select class="form-control  formTest" id="selectGrupoMicrobiologia" name="grupoMicrobiologia"></select>
                                                        </div>
                                                        <div class="col-3 ">
                                                            <label class="">Grupo FONASA </label>
                                                            <select class="form-control formTest" id="selectGrupoFonasa" name="ctIdgrupoexamenesfonasa"></select>
                                                        </div>
                                                        <div class="col-2 mt-4 pr-0">
                                                            <label class="col-md-12 pl-0 pr-0z">
                                                                <input id="checkboxObligado" name="ctResultadoobligado" type="checkbox" class="formTest" /> ¿Obligatorio?
                                                            </label>
                                                        </div>
                                                        <div class="col-1 mt-4 pl-0">
                                                            <select id="selectObligado" class="form-control mt-2 formTest" name="ctCondicion">
                                                                <option value="N" selected> </option>
                                                                <option value="<"> < </option>
                                                                <option value=">"> > </option>
                                                                <option value="="> = </option>
                                                            </select>
                                                        </div>
                                                        <div class="col-3 mt-4">
                                                            <input id="txtObligado" name="ctCondicionTxt" type="text" class="form-control mt-2 formTest" autocomplete="off" onkeypress="return isNumberKey(event)"/>
                                                        </div>
                                                        <div class="col-3 ">
                                                            <label>Orden en Sistema </label>
                                                            <input id="txtOrdenSistema" name="ctSort" type="text" class="form-control formTest " autocomplete="off" onkeypress="return isNumberKey(event)"/>
                                                        </div>
                                                        <div class="col-3 ">
                                                            <label>Orden en Reporte </label>
                                                            <input id="txtOrdenReporte" name="ctTestreporte" type="text" class="form-control formTest " autocomplete="off" onkeypress="return isNumberKey(event)"/>
                                                        </div>
                                                        <div class="col-3 mt-4 mb-0 form-group">
                                                            <input id="chbxOpcional" name="ctOpcional" type="checkbox" class="formTest" /> ¿Opcional?
                                                        </div>
                                                        <div class="col-3 mt-2 pr-0">
                                                            <button id="btnInoperabilidad" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2" data-toggle="modal" data-target="#modalInteroperabilidad">
                                                                Interoperabilidad (Host)
                                                            </button>
                                                        </div>
                                                      
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- MODAL MÉTODOS -->
                                    <div class="modal fade" id="modalMetodos" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false"  aria-labelledby="staticBackdrop" aria-hidden="true">
                                        <div class="modal-dialog modal-xl" role="document">
                                            <div class="modal-content">
                                                <div class="modal-body" >
                                                    <h5 class="modal-title text-center">M&ecute;todos</h5>
                                                    <div class="card-body pt-0">
                                                        <div class="row" id="contenedorMetodos">
        
                                                            <div class="col-5">
                                                                <div class="row pt-3">
                                                                    <div class="col pr-0">
                                                                        <input type="text" id="idSearcher" class="formTest" inputmode="search"
                                                                                tabindex="5" style="width: 400px" />
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col pr-0">
                                                                        <select id="idDualDataVisualSource" class="formTest" size="5" tabindex="-1" style="max-width: 400px;">
                                                                                <option value="-1" disabled style="display: none;">Seleccionar</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
        
                                                            <div class="col-1 d-flex align-items-center">
                                                                <div id="dualDataButtonBar" class="mx-auto">
                                                                    <div class="col-12">
                                                                        <button id="clickAddBtn" type="button"
                                                                                class="btn btn-blue-primary formTest">
                                                                                <i class="fas fa-arrow-right" tabindex="-1"></i>
                                                                        </button>
                                                                    </div>
                                                                    <div class="col-12">
                                                                        <button id="clickDelBtn" type="button"
                                                                                class="btn btn-blue-primary formTest">
                                                                                <i class="fas fa-arrow-left" tabindex="-1"></i>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-6 ">
                                                                <table id="tableMetodos" tabindex="-1" class="display" style="width:100%">
                                                                        <thead>
                                                                                <tr>
                                                                                    <th scope="col">M&eacute;todo</th>
                                                                                    <th scope="col">Valor por defecto</th> 
                                                                                </tr>
                                                                        </thead>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-12 d-flex justify-content-between">
                                                        <button id="btnGuardarMetodos" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" data-dismiss="modal">Ok</button>
                                                    </div>
                                                </div>
                                             
                                             </div>
                                         </div>
                                    </div>
                                    
                                    <!-- MODAL DELTA CHECK -->
                                    <div class="modal fade" id="modalDeltaCheck" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false" aria-labelledby="staticBackdrop" aria-hidden="true">
                                        <div class="modal-dialog modal-lg" role="document">
                                            <div class="modal-content">
                                               
                                                <div class="modal-body" >
                                                    <h5 class="modal-title text-center">Delta Check</h5>
                                                        <div class="col-12">
                                                            <div class="form-group row">
                                                                <div class="col-2">
                                                                    <div class="col-11 ">
                                                                        <input id="txtValoresEvaluar" class="form-control formTest" name="ctDeltacantidad" type="text" onkeypress="return isNumberKey(event)" />
                                                                    </div>
                                                                </div>
                                                                <label class="col-10 col-form-label"><strong>Valores a evaluar:</strong> Número de resultados previos considerados</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row mb-0">
                                                                <div class="form-group col-2 d-flex">
                                                                    <div class="col-11 ">
                                                                        <input id="txtPorcentaje" class="form-control formTest" name="ctDeltaporcentaje" type="text" onkeypress="return isNumberKey(event)" />
                                                                    </div>
                                                                    <label>%</label>
                                                                </div>
                                                                <label id="" class="col-10 col-form-label"><strong>Porcentaje:</strong> Desviación del promedio de valores previos</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="form-group row">
                                                                <div class="col-2">
                                                                    <div class="col-11 ">
                                                                        <input id="txtDiasEvaluables" class="form-control formTest" name="ctDiasevaluables" type="text" onkeypress="return isNumberKey(event)" />
                                                                    </div>
                                                                </div>
                                                                <label id="" class="col-10 col-form-label"><strong>Días evaluables:</strong> Rango de tiempo para evaluación</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-12 pl-0 pr-0 d-flex justify-content-between">
                                                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2   cerrarModal" data-dismiss="modal">Ok</button>
                                                           
                                                        </div>
                                                </div>
                                             </div>
                                         </div>
                                    </div>
                                    
                                    <!-- MODAL VALORES REFERENCIA -->
                                    <div class="modal fade" id="modalValoresReferencia" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false"  aria-labelledby="staticBackdrop" aria-hidden="true">
                                        <div class="modal-dialog modal-xl" role="document">
                                            <div class="modal-content">
                                                <div class="modal-body">
                                                    <h5 class="modal-title text-center">Valores de Referencia</h5>
                                                    <div class="col-12">
                                                        <button id="addRow" type="button" class="btn btn-blue-primary formTest">Nuevo</button>
                                                        <table class="table table-bordered table-checkable mt-10" id="tablaValoresReferencia" style="width:1259.080px;">
                                                            <thead>
                                                                <tr>
                                                                    <th rowspan="2" style="text-align:center" valign="bot">Sexo</th>
                                                                    <th colspan="3" style="text-align:center">Desde</th>
                                                                    <th colspan="3" style="text-align:center">Hasta</th>
                                                                    <th rowspan="2" style="text-align:center" valign="top">Método</th>
                                                                    <th rowspan="2" style="text-align:center" valign="top">Bajo Crítico</th>
                                                                    <th rowspan="2" style="text-align:center" valign="top">Bajo</th>
                                                                    <th rowspan="2" style="text-align:center" valign="top">Alto</th>
                                                                    <th rowspan="2" style="text-align:center" valign="top">Alto Crítico</th>
                                                                    <th rowspan="2" style="text-align:center" valign="top">Texto</th>
                                                                    <th rowspan="2" style="text-align:center" valign="top">Borrar</th>
                                                                </tr>
                                                                <tr>
                                                                    <th align="center" valign="top">Años</th>
                                                                    <th align="center" valign="top">Meses</th>
                                                                    <th align="center" valign="top">Días</th>
                                                                    <th align="center" valign="top">Años</th>
                                                                    <th align="center" valign="top">Meses</th>
                                                                    <th align="center" valign="top">Días</th>
                                                                </tr>
                                                            </thead>
                                                        </table>
                                                    </div>
                                                    <div class="col-md-6 form-group mt-6 row">
                                                        <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  cerrarModal" data-dismiss="modal">Ok</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!-- MODAL ANTECEDENTES -->
                                    <div class="modal fade" id="modalAntecedentes" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false" aria-labelledby="staticBackdrop" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-body" >
                                                    <h5 class="modal-title text-center">Antecedentes Paciente</h5>
                                                    <ul id="listAntecedentes" class="row"></ul>
                                                    <div class="col-12 pl-0 pr-0 d-flex justify-content-between">
                                                        <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  cerrarModal" data-dismiss="modal">Ok</button>
                                                    </div>
                                                </div>
                                             </div>
                                         </div>
                                    </div>
                                    
                                    <!-- MODAL GLOSAS -->
                                    <div class="modal fade" id="exampleModalSizeSm" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false" aria-labelledby="exampleModalSizeSm" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                                            <div class="modal-content">
                                                <div class="modal-body">
                                                    <h5 class="modal-title text-center" id="exampleModalLabel">Asignación de Glosas</h5>
                                                    <label class="">Sección</label>
                                                    <select class="form-control col-4 formTest selectpicker" id="selectSeccionGlosa" name="seccionGlosa" data-live-search="true">
                                                        <option value="N" selected>TODOS</option>
                                                    </select>
                                                    
                                                    <div class="row" id="contenedorGlosas">
    
                                                        <div class="col-4">
                                                            <div class="row pt-3">
                                                                <div class="col pr-0">
                                                                    <input type="text" id="idGlosaSearcher" class="formTest" inputmode="search" class="col-12" />
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col pr-0">
                                                                    <select id="idDualDataVisualSourceGlosa" class="formTest" size="5" tabindex="-1" style="width: 100%;">
                                                                            <option value="-1" disabled style="display: none;">Seleccionar</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
    
                                                        <div class="col-1 d-flex align-items-center">
                                                            <div id="dualDataButtonBar" class="mx-auto">
                                                                <div class="col-12">
                                                                    <button id="clickAddBtnGlosa" type="button"
                                                                            class="btn btn-blue-primary formTest">
                                                                            <i class="fas fa-arrow-right" tabindex="-1"></i>
                                                                    </button>
                                                                </div>
                                                                <div class="col-12">
                                                                    <button id="clickDelBtnGlosa" type="button"
                                                                            class="btn btn-blue-primary formTest">
                                                                            <i class="fas fa-arrow-left" tabindex="-1"></i>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-7 ">
                                                            <table id="tableGlosas" tabindex="-1">
                                                                    <thead>
                                                                            <tr>
                                                                                <th scope="col">C&oacute;digo</th>
                                                                                <th scope="col">Glosa</th>
                                                                                <th scope="col">Valor crítico</th> 
                                                                                <th scope="col">Firma en lote</th> 
                                                                            </tr>
                                                                    </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                               
                                                <div class="form-group row mx-auto col-8">
                                                    
                                                </div>
                                                    <div class="col-12 pl-0 pr-0 d-flex justify-content-between">
                                                        <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 cerrarModal" data-dismiss="modal">Ok</button>
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
                                                        <div id='' class="col-12">
                                                            <div class="form-group row">
                                                                <label class="col-3 col-form-label">Host:</label>
                                                                <div class="col-7">
                                                                    <input id="txtHost" class="form-control formTest" name="ctHost" type="text" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div id='' class="col-12">
                                                            <div class="form-group row">
                                                                <label class="col-3 col-form-label">LIS BAC:</label>
                                                                <div class="col-7">
                                                                    <input id="txtHostMicro" class="form-control formTest" name="ctHostmicro" type="text" />
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
                                    
                                    <div class="col-md-6 form-group mt-6 row">
                                        <div class="col-12 row">
                                            <label id="lblEstado" class="col-2 col-form-label d-flex align-items-center">Activo</label>
                                            <div class="col-3">
                                                <label class="switch-content switch-normal switch-active"> 
                                                    <input id='checkBoxLever' name='ctActivo' class="formTest" type="checkbox" tabindex="-1" checked/>
                                                    <div></div>
                                                </label>
                                                <!-- <span id="spanCheck" class="switch switch-outline switch-icon switch-primary">
                                                    <label>
                                                        <input id='checkBoxLever' name='ctActivo' class="formTest mt-5 pointer" type='checkbox' checked />
                                                        <span></span>
                                                    </label>
                                                </span> -->
                                            </div>
                                        </div>
                                        <div id="divAgregarBtn" class="col-6"><button id="btnAgregarTest" type="submit" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Guardar</button></div>
                                        <div id="divActualizarBtn" class="col-6 ocultar">
                                            <button id="btnUpdateTest" name="update" type="submit" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Confirmar</button>
                                            <button id="btnCancelTest" name="" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                                        </div>
        
                                    </div>
                            </form>
                        </div>
                    </div>
                    <!-- FIN FORMULARIO REGISTRO TEST -->
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
					            <th scope="col">Tipo de muestra</th>
					            <th scope="col">Laboratorio</th>
					            <th scope="col">Sección</th>
  					            <th scope="col">Contenedor</th>
					            <th scope="col">Métodos</th>
					            <th scope="col">Tipo de resultado</th>
					            <th scope="col">U.M.</th>
					            <th scope="col">Decimales</th>
					            <th scope="col">Valores de referencia</th>
					            <th scope="col">Volumen de la muestra</th>
					            <th scope="col">Grupo FONASA</th>
					        </tr>
					    </thead>
					        <tbody id="examenesParaExcel">

						    </tbody>
					</table></div>
        	<!-- Modal eliminar valor -->
            <div class="modal fade" id="usuarioElimina" tabindex="-1"
            role="dialog" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body"></div>
                </div>
            </div>
        </div>
         <script src="https://unpkg.com/imask"></script>
        <jsp:include page="common/Scripts.jsp"/>
        
        <script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
   		<script src="<%=request.getContextPath()%>/resources/js/common/creadorExcell.js"></script>
		<script  src="<%=request.getContextPath()%>/resources/js/portalEstadistica/xlsx.mini.js"></script>
	    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script	src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/dataTables.cellEdit.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/typeahead/typeahead.bundle.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Dto/CfgMetodos.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Dto/CfgGlosas.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
<!--        <script src="<%=request.getContextPath()%>/resources/js/valoresreferencia/TablaValoresReferencia.js"></script>-->
        <script src="<%=request.getContextPath()%>/resources/js/valoresreferencia/ConfiguracionValoresReferencias.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/test/dual-data-metodos.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/test/asignacionGlosas.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/test/ConfiguracionTest.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/test/ConfiguracionTestForm.js"></script>
    </body>
</html>
