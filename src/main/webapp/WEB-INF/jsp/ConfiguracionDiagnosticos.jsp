<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuraci&oacute;n de Diagn&oacute;sticos</title>
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
                <div class="accordion col-12 pl-0 pr-0">
                    <div id="panelDiagnosticos" class="card border-15">
                        <div class="card-header border-15" id="headingDiagnosticos">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    B&uacute;squeda de Diagn&oacute;sticos
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevoDiagnostico" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarDiagnostico" class='symbol-label bg-hover-blue hoverIcon' href="#" data-toggle="tooltip" title="Nuevo diagn&oacute;stico">
                                                <i id="iAgregarDiagnostico" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a>
                                    <a id="btnLimpiarFiltro" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue hoverIcon"  href="#" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content" aria-labelledby="headingDiagnosticos" data-parent="#headingDiagnosticos">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <!--
                                    <div class="col-12">
                                        <div class="form-group row mb-2">
                                            <label class="col-4 col-form-label">C&oacute;digo:</label>
                                            <div class="col-8">
                                                <input id="txtCodigoFiltro" type="text" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                    -->
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Nombre</label>
                                            <div class="col-8">
                                                <input id="txtDescripcionFiltro" class="form-control" />
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
                                                    <th id="thNombre" scope="col">Diagn&oacute;stico</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron diagn&oacute;sticos con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Diagn&oacute;sticos encontrados: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                       <!-- FORMULARIO REGISTRO DIAGNÓSTICOS -->
                <div class="card mt-5 border-15">
                    <div class="card-header pl-2 border-15">
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                Registro de Diagn&oacute;sticos
                            </h3>
                            <div class="col-2 justify-content-right iconos-registro">
                                <div class="float-right">
                                    <div id="divbtnLimpiar">
                                        <a id="btnLimpiar" class="navi-link pointer" >
                                            <span class="symbol symbol-30 symbol-circle mr-3 ">
                                                <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                                    <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                                </span>
                                            </span>
                                            <span class="navi-text"></span>
                                        </a>
                                    </div>
                                </div>   
                                <div class="float-right" >
                                    <div id="divbtnEditar">
                                        <a id="btnEditarDiagnostico" class="navi-link pointer">
                                            <span class='symbol symbol-30 symbol-circle'>
                                                <span id="circuloEditarDiagnostico" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar diagn&oacute;stico">
                                                    <i id="iEditDiagnostico" class="far fa-edit text-dark-50"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                                <div class="float-right" >
                                    <div id="divbuscarDiagnostico">
                                        <a id="buscarDiagnostico" class="navi-link" href="#" >
                                            <span class='symbol symbol-30 symbol-circle'>
                                                <span id="circuloBuscarDiagnostico" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar envase">
                                                    <i id="iBuscarDiagnostico" class="fas fa-search text-blue"></i></span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <form id="formRegistro" method="post">
                            <div id="divForm" class="row">
                                <div class="col-12">
                                    <div class="row">
                                        <input id="txtIdDiagnostico" type="text" class="form-control ocultar" autocomplete="off" placeholder=""/>
                                        <div class="col-4">
                                            <label>Nombre:</label>
                                            <input id="txtDescripcion" type="text" class="form-control formDiagnosticos" maxlength="150" autocomplete="off" placeholder=""/>
                                        </div>
                                        <div class="col-4">
                                            <label>C&oacute;digo CIE10: </label>
                                            <select class="form-control formDiagnosticos" id="selectCie10" name="cenvIdtipomuestra">
                                                <option value="0" selected>Seleccionar</option>
                                            </select>
                                        </div>
                                        <div class="col-4">
                                            <label>Host:</label>
                                            <input id="txtHost" type="text" class="form-control formDiagnosticos" maxlength="20" autocomplete="off" placeholder=""/>
                                        </div> 
                                        <div class="col-4">
                                            <label>Orden</label>
                                            <input id="numSort" type="number" value="1" class="form-control formDiagnosticos" autocomplete="off" placeholder=""/>
                                        </div> 
                                        <div class="col-4">
                                            <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                            <div class="col-3">
                                                <label class="switch-content switch-comprobante switch-active">
                                                    <input id="checkBoxActivo" name="clabActivo" class="formDiagnosticos" type='checkbox' checked />
                                                    <div></div>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 form-group mt-6 row">
                                    <div id="divAgregarBtn" class="col-6">
                                        <button id="btnAgregar" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Guardar</button>
                                    </div>
                                    <div id="divActualizarBtn" class="col-6 ocultar">
                                        <button id="btnUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Confirmar</button>
                                        <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                </div>
             
            </div>
        </div>
        <script src="https://unpkg.com/imask"></script>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/diagnosticos/ConfiguracionDiagnosticos.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/diagnosticos/ConfiguracionDiagnosticosForm.js"></script>
    </body>
</html>
