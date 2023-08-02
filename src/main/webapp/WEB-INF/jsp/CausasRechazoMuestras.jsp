<%-- 
    Document   : CausasRechazoMuestras
    Created on : 09-06-2021, 10:58:34
    Author     : marco.c
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
        <title>BIOS-LIS | Causas de Rechazo de Muestras</title>
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
                <div class="accordion col-12 pl-0 pr-0">
                    <div id="panelCausasRechazo" class="card border-15">
                        <div class="card-header border-15" id="accordionCausaRechazo">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    B&uacute;squeda de Causas de Rechazo
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevaCausa" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarCausa" class='symbol-label bg-hover-blue hoverIcon' href="#" data-toggle="tooltip" title="Nueva Causa">
                                                <i id="iAgregarServicio" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a>
                                    <a id="btnLimpiarFiltro" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue hoverIcon" href="#" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content" aria-labelledby="accordionCausaRechazo" data-parent="#accordionCausaRechazo">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label id="" class="col-4 col-form-label">Código</label>
                                            <div class="col-8">
                                                <input id="txtCodigoFiltro" class="form-control" name="" placeholder="" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label id="" class="col-4 col-form-label">Causa de Rechazo</label>
                                            <div class="col-8">
                                                <input id="txtCausaFiltro" class="form-control" name="" placeholder="" type="text" />
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
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th scope="col">C&oacute;digo</th>
                                                    <th id="thCausa" scope="col">Causa de Rechazo</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">
                                                <tr></tr>
                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron causas de rechazo con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Causas rechazo encontradas: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                    <!-- FORMULARIO REGISTRO CAUSA RECHAZO MUESTRAS -->
                    <div id="panelRegistroCausasRechazo" class="card mt-5 border-15">
                        <div class="card-header border-15">
                            <div class="card-title row col-12 d-flex justify-content-between">
                                <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                    Registro de Causas de Rechazo
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
                                    <div class="float-right" >
                                        <a id="btnEditarCausa" class="navi-link pointer">
                                            <span class='symbol symbol-30 symbol-circle mr-3' >
                                                <span id="circuloEditarCausa" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar Causa">
                                                    <i id="iEditCausa" class="far fa-edit text-dark-50"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                    <div class="float-right" >
                                        <a id="buscarCausa" class="navi-link" href="#" >
                                            <span class='symbol symbol-30 symbol-circle mr-3'>
                                                <span id="circuloBuscarCausa" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar Causa">
                                                    <i id="iBuscarCausa" class="fas fa-search text-blue"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div id="formRegistroTest" >
                                <div id="divForm" class="row">
                                    <div class="col-12 row">
                                        <input id="txtIdCausaRechazo" name="idCausaRechazo" type="text" class="form-control ocultar" autocomplete="off" placeholder="" />
                                        <input id="ipEquipo" name="c" type="text" class="form-control ocultar">
                                        <!--<input type="hidden" name="idServicio" />-->
                                        <div class="col-4">
                                            <label>Código:</label>
                                            <input id="txtCodigo" maxlength="15" name="ccrmCodigo" type="text" class="form-control " autocomplete="off" placeholder=""/>
                                        </div>
                                        <div  class="col-4">
                                            <label>Causa de Rechazo</label>
                                            <input id="txtDescripcionCausaRechazo" maxlength="50" name="ccrmDescripcion" type="text" class="form-control " autocomplete="off" placeholder=""/>
                                        </div>
                                        <div class="col-4">
                                            <label>Orden:</label>
                                            <input id="txtOrden" name="ccrmSort" type="number" class="form-control " autocomplete="off" placeholder=""/>
                                        </div>
                                        <div class="col-12">
                                            <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                            <div class="col-3">
                                                    <label class="switch-content switch-comprobante switch-active">
                                                        <input id='checkBoxActivo' name='ccrmActivo' class="" type='checkbox' checked value='S' />
                                                        <div></div>
                                                    </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 form-group mt-6 row">
                                        <div id="divAgregarBtn" class="col-6">
                                            <button id="btnAgregarCausa" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"><i class="far fa-save"></i>Guardar</button>
                                        </div>
                                        <div id="divActualizarBtn" class="col-6 ocultar">
                                            <button id="btnGuardarUpdate" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" name="update"  type="submit"><i class="far fa-save"></i>Confirmar</button>
                                            <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2">Cancelar</button>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    <!-- FIN FORMULARIO REGISTRO PACIENTE -->
                    </div>
                </div>
            </div>
        </div>
        <script src="https://unpkg.com/imask"></script>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/CausasRechazoMuestras.js"></script>
    </body>
</html>
