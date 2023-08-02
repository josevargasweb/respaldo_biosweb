<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Secciones</title>
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
                <div class="accordion col-12 pl-0 pr-0" id="accordionSecciones">
                    <div class="card border-15">
                        <div class="card-header border-15">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader">
                                    Búsqueda de Secciones
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevaSeccion" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloAgregarSeccion" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Nueva sección">
                                                <i id="iAgregarSeccion" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a>
                                    <a id="btnLimpiarFiltro" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content" data-parent="#accordionSecciones">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Código</label>
                                            <div class="col-8">
                                                <input id="txtCodigoFiltro" class="form-control" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Nombre</label>
                                            <div class="col-8">
                                                <input id="txtDescripcionFiltro" class="form-control" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Laboratorio</label>
                                            <div class="col-8">
                                                <select class="form-control selectpicker" id="selectLabFiltro" data-live-search="true">
                                                </select>
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
                                <div class="col" >
                                    <div class="table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th scope="col">C&oacute;digo</th>
                                                    <th id="thNombre" scope="col">Secciones</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron secciones con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Secciones encontradas: <span id="contRegistros"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                </div>
                <!-- FORMULARIO REGISTRO SECCIONES -->
                <div class="card mt-5 border-15 w-100">
                    <div class="card-header border-15"> 
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                Registro de Secciones
                            </h3>
                            <div class="col-2 justify-content-right iconos-registro">
                                <div class="float-right">
                                    <a id="btnLimpiar" class="navi-link pointer">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div>   
                                <div class="float-right">
                                    <a id="btnEditar" class="navi-link pointer">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloEditarSeccion" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar sección">
                                                <i id="iEditSeccion" class="far fa-edit text-dark-50"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                                <div class="float-right" >
                                    <a id="buscarSeccion" class="navi-link" href="#" >
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloBuscarSeccion" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar sección">
                                                <i id="iBuscarSeccion" class="fas fa-search text-blue"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <form id="formRegistroTest" method="post">
                            <div id="divForm" class="row">
                                <div class="col-12 row">
                                    <input id="txtIdSeccion" name="idSeccion" type="text" class="form-control ocultar" autocomplete="off" placeholder=""/>
                                    <div class="col-4">
                                        <label>Código:</label>
                                        <input id="txtCodigo" name="csecCodigo" type="text" class="form-control formSecciones" maxlength="12" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-4">
                                        <label>Nombre:</label>
                                        <input id="txtDescripcion" name="csecDescripcion" type="text" class="form-control formSecciones" maxlength="50" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-4">
                                        <label>Orden:</label>
                                        <input id="numSort" name="csecSort" type="number" value="1" max="9999" class="form-control formSecciones" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <!--                                <div id="" class="col-md-4 mt-5">
                                                                        <label>Orden:</label>
                                                                        <input id="txtCodigo" name="csecCodigo" type="text" class="form-control mt-5 " autocomplete="off" placeholder=""/>
                                                                        <div class="d-md-none mb-2"></div>
                                                                    </div>
                                    -->
                                    <div class="col-4">
                                        <label>Laboratorio:</label>
                                        <select id="selectLab" class="form-control disabledForm formSecciones selectpicker" name="clabIdlaboratorio" data-live-search="true" data-none-selected-text>
                                        </select>
                                    </div>
                                    <div class="col-12">
                                        <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                        <div class="col-3">
                                            <label class="switch-content switch-comprobante switch-active"> <input
                                                id='checkBoxActivo' type="checkbox" tabindex="-1" name='' class="formSecciones" checked value='S'>
                                                <div></div>
                                            </label> 
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 form-group mt-6 row">
                                    <div id="divBtnAgregar" class="col-6">
                                        <button id="btnAgregarSeccion" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" ><i class="far fa-save"></i>Guardar</button>
                                    </div>
                                    <div id="divBtnUpdate" class="col-6 ocultar">
                                        <button id="btnUpdateSeccion" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" type="button"><i class="far fa-save"></i>Confirmar</button>
                                        <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2">Cancelar</button>
                                    </div>

                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- FIN FORMULARIO REGISTRO PACIENTE -->
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="https://unpkg.com/imask"></script>
        <script src="<%=request.getContextPath()%>/resources/js/secciones/ConfiguracionSecciones.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/secciones/ConfiguracionSeccionesForm.js"></script>
    </body>
</html>
