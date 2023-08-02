<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración Centros de Salud</title>
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
        <c:if test="${mensaje!=null}"><input type="hidden" id="messageSuccess" value="${mensaje}" /></c:if>
        <c:if test="${mensajeError!=null}"><input type="hidden" id="messageError" value="${mensajeError}" /></c:if>
        <c:if test="${errorPageLoad!=null}"><input type="hidden" id="errorPageLoad" value="${errorPageLoad}" /></c:if>
        <div class="container container-main-content container-configuracion mt-5">
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0" id="accordionHeader">
                    <div class="card border-15">
                        <div class="card-header border-15">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true">
                                    Configuraci&oacute;n Centros de Salud
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevoCentro" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloAgregar" class="symbol-label bg-hover-blue hoverIcon" href="#" data-toggle="tooltip" title="Nuevo Centro">
                                                <i id="iAgregar" class='fas fa-plus text-blue'></i>
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
                        <div id="collapseHeader" class="collapse show" data-parent="#accordionHeader">
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
                                    <div class="table-container mb-3">
                                        <!--<label id="lblCantidad">Cantidad: <span id="spanCantidad"></span></label><!--Cantidad de registros en la tabla-->
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th scope="col">Código</th>
                                                    <th scope="col">Nombre</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro"></tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron registros con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Centros de salud encontrados: <span id="contRegistros"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                        <!-- FORMULARIO REGISTRO CENTROS DE SALUD -->
                    <div class="card mt-5 border-15">
                        <div class="card-header border-15"> 
                            <div class="card-title row col-12 d-flex justify-content-between">
                                <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                    Registro de Centros de Salud
                                </h3>
                                <div class="col-2 justify-content-right iconos-registro">
                                    <div class="float-right">
                                        <a id="btnLimpiar" class="navi-link pointer" >
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                                    <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                                </span>
                                            </span>
                                            <span class="navi-text"></span>
                                        </a>
                                    </div>   
                                    <div class="float-right">
                                        <a id="btnEditarCentro" class="navi-link pointer">
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circuloEditarCentro" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar Centro">
                                                    <i id="iEditCentro" class="far fa-edit text-dark-50"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                    <div class="float-right">
                                        <a id="buscarCentro" class="navi-link" href="#" >
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circuloBuscarCentro" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar Centro">
                                                    <i id="iBuscarCentro" class="fas fa-search text-blue"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div id="formRegistroCentro" >
                                <div id="divForm" class="row">
                                    <div class="col-12 row">
                                        <input id="txtIdCentro" name="idCentro" value ="0" type="text" class="form-control ocultar" autocomplete="off" />
                                        
                                            <div class="col-4">
                                                <label>Código: *</label>
                                                <input id="txtCodigoCentro" maxlength="15" name="ccdsCodigo" type="text" class="form-control" autocomplete="off"/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="col-4">
                                                <label>Nombre: *</label>
                                                <input id="txtNombreCentro" maxlength="50" name="ccdsDescripcion" type="text" class="form-control" autocomplete="off"/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <!--  
                                            <div class="col-4">
                                                <label>Institución:</label>
                                                <select class="form-control" id="selectInstituciones" disabled></select>
                                                <div class="d-md-none mb-2"></div>
                                            </div>-->
                                            <div class="col-4">
                                                <label>Dirección:</label>
                                                <input id="txtDireccionCentro" maxlength="50" name="ccdsDireccion" type="text" class="form-control" autocomplete="off" />
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="col-4">
                                                <label>Teléfono:</label>
                                                <input id="txtTelefonoCentro" maxlength="20" name="ccdsTelefono" type="text" class="form-control" autocomplete="off" />
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="col-4">
                                                <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                                <div class="col-3">
                                                    <label class="switch-content switch-comprobante switch-active">
                                                        <input id='checkBoxActivo' name='ccdsActivo' type='checkbox' checked value='S' />
                                                        <div></div>
                                                    </label>
                                                </div>
                                            </div>
                                    </div>
                                </div>
                                <div class="col-md-6 form-group mt-6 row">
                                    <div id="divAgregarBtn" class="col-6">
                                        <button id="btnAgregar" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" ><i class="far fa-save"></i>Guardar</button>
                                    </div>
                                    <div id="divActualizarBtn" class="col-6 ocultar">
                                        <button id="btnUpdate" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" name="update"  type="submit"><i class="far fa-save"></i>Confirmar</button>
                                        <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2">Cancelar</button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/ConfiguracionCentrosDeSalud.js"></script>
    </body>
</html>

