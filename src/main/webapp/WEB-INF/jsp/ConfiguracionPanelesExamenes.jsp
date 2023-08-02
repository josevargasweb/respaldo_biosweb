<%-- 
    Document   : ConfiguracionPanelesExamenes
    Created on : 29-11-2021, 10:38:50
    Author     : Marco Caracciolo
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración Paneles Exámenes</title>
        <jsp:include page="common/Styles_1.jsp"/>
        <link rel="icon" type="image/jpg" href="<%=request.getContextPath()%>/resources/img/gbios.png">
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
           	<c:if test="${mensaje!=null}"><input type="hidden" id="messageSuccess" value="${mensaje}" /></c:if>
        	<c:if test="${mensajeError!=null}"><input type="hidden" id="messageError" value="${mensajeError}" /></c:if>
                <div class="accordion col-12 pl-0 pr-0" id="accordionHeader">
                    <div class="card border-15">
                        <div class="card-header border-15" id="headingOne8">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    B&uacute;squeda de Paneles de Ex&aacute;menes
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <div class="float-right">
                                        <a id="btnLimpiarFiltro" class="navi-link">
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue hoverIcon" href="#" data-toggle="tooltip" title="Limpiar">
                                                    <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                                </span>
                                            </span>
                                            <span class="navi-text"></span>
                                        </a>
                                    </div> 
                                    <div class="float-right" >
                                        <a id="nuevoPanel" class="navi-link">
                                            <span class='symbol symbol-30 symbol-circle'>
                                                <span id="circuloAgregarTest" class='symbol-label bg-hover-blue hoverIcon' href="#" data-toggle="tooltip" title="Nuevo Panel">
                                                    <i id="iAgregarPanel" class='fas fa-plus text-blue'></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content" data-parent="#accordionHeader">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Panel</label>
                                            <div class="col-8">
                                                <input id="txtNombreFiltro" class="form-control" type="text" />
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
                                    <div class=" table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th scope="col">Paneles</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro"></tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron paneles con los datos provistos.</label>
                                    </div>
                                    <label id="lblCantidadExamenes">Paneles encontrados: <span id="spanCantExamenes"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                </div>
                <!-- FORMULARIO REGISTRO PANELES EXAMENES -->
                <div class="card mt-5 border-15">
                    <div class="card-header border-15"> 
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                Registro de Paneles de Ex&aacute;menes
                            </h3>
                            <div class="col-2 justify-content-right iconos-registro">
                                <div class="float-right">
                                    <a id="btnLimpiar" class="navi-link pointer" >
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i></span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div>
                                <div class="float-right">
                                    <a id="btnEditarPanel" class="navi-link pointer">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloEditarPanel" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar panel">
                                                <i id="iEditPanel" class="far fa-edit text-dark-50"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                                <div class="float-right">
                                    <a id="buscarPanel" class="navi-link" href="#">
                                        <span class='symbol symbol-30 symbol-circle mr-3'>
                                            <span id="circuloBuscarPanel" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar panel">
                                                <i id="iBuscarPanel" class="fas fa-search text-blue"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <form id="formRegistroPanel" method="post">
                            <div id="divForm" class="row">
                                <div class="col-12">
                                    <div class="row">
                                        <input id="txtIdPanel" name="idPanel" type="text" class="form-control ocultar " autocomplete="off" placeholder="" />
                                        <div class="col-4">
                                            <label>Nombre:</label>
                                            <input id="txtNombrePanel" maxlength="50" name="cpeNombrepanelexamen" type="text" class="formPanelEx form-control" autocomplete="off" placeholder=""/>
                                            <div class="d-md-none mb-2"></div>
                                        </div>
                                        <div class="col-4">
                                            <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                            <div class="col-3">
                                                <label class="switch-content switch-comprobante switch-active"> <input
                                                    id='checkBoxActivo' type="checkbox" tabindex="-1" name='cpeActivo' class="formExamen" checked value='S'>
                                                    <div></div>
                                                </label> 
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <label>Seleccionar ex&aacute;menes</label>
                                            <div class="row">
                                                <div class="col-5">
                                                    <div class="row pt-3">
                                                        <div class="col pr-0">
                                                            <input type="text" id="idSearcher" class="formPanelEx" inputmode="search"
                                                                    tabindex="5" style="width: 400px" />
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col pr-0">
                                                            <select id="idDualDataVisualSource" class="formPanelEx" size="5" tabindex="-1" style="width: 400px;">
                                                                    <option value="-1" disabled style="display: none;">Seleccionar</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
        
                                                <div class="col-1 d-flex align-items-center">
                                                    <div id="dualDataButtonBar" class="mx-auto">
                                                        <div class="col-12">
                                                            <button id="clickAddBtn" type="button"
                                                                    class="btn btn-blue-primary formPanelEx">
                                                                    <i class="fas fa-arrow-right" tabindex="-1"></i>
                                                            </button>
                                                        </div>
                                                        <div class="col-12">
                                                            <button id="clickDelBtn" type="button"
                                                                    class="btn btn-blue-primary formPanelEx">
                                                                    <i class="fas fa-arrow-left" tabindex="-1"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-6 ">
                                                    <table id="listExamenes" tabindex="-1" class="display" style="width:100%">
                                                            <thead>
                                                                    <tr>
                                                                        <th scope="col">C&oacute;digo examen</th>
                                                                        <th scope="col">Examen</th> 
                                                                    </tr>
                                                            </thead>
                                                    </table>
                                                </div>
                                            </div>
                                            
                                        </div>
                                            <!--
                                            <div id="listbox" class="form-group ml-5 ">
                                                <select id="listbox_examenes" class="dual-listbox w-75">
                                                </select>
                                            </div> 
                                            -->
                                            <ul id="examenesTest" class="ocultar"></ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 form-group mt-6 row">
                                    <div id="divAgregarBtn" class="col-6">
                                        <button id="btnAgregarPanel" type="submit" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"><i class="far fa-save"></i>Guardar</button>
                                    </div>
                                    <div id="divActualizarBtn" class="col-6 ocultar">
                                        <button id="btnUpdatePanel" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" name="update" type="submit"><i class="far fa-save"></i>Confirmar</button>
                                        <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/panelesexamenes/ConfiguracionPanelesExamenes.dual-data.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/panelesexamenes/ConfiguracionPanelesExamenes.js"></script>
    </body>
</html>

