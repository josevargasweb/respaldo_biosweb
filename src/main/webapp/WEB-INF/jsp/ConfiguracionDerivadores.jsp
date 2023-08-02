<%-- 
    Document   : ConfiguracionDerivadores
    Created on : 06-12-2021, 12:35:02
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
        <title>BIOS-LIS | Configuración de derivadores</title>
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
           	<c:if test="${mensaje!=null}"><input type="hidden" id="messageSuccess" value="${mensaje}" /></c:if>
        	<c:if test="${mensajeError!=null}"><input type="hidden" id="messageError" value="${mensajeError}" /></c:if>
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0">
                    <div class="card border-15">
                        <div class="card-header border-15" id="accordionHeader">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    B&uacute;squeda Derivadores
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevoDerivador" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloAgregarDerivador" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Nuevo Derivador">
                                                <i id="iAgregarDerivador" class='fas fa-plus text-blue'></i>
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
                        <div id="collapseHeader" class="collapse show container-content" aria-labelledby="accordionHeader" data-parent="#accordionHeader">
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
                                    <div class=" table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th scope="col">Código</th>
                                                    <th scope="col">Derivador</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro"></tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron derivadores con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Derivadores encontrados: <span id="contRegistros"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                </div>
            </div>
            <!-- FORMULARIO REGISTRO DERIVADORES -->
            <div class="card mt-5 border-15">
                <div class="card-header border-15"> 
                    <div class="card-title row col-12 d-flex justify-content-between">
                        <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                            Registro de Derivadores
                        </h3>
                        <div class="col-2 justify-content-right iconos-registro">
                            <div class="float-right">
                                <a id="btnLimpiar" class="navi-link pointer">
                                    <span class="symbol symbol-30 symbol-circle mr-3">
                                        <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                            <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>   
                            <div class="float-right" >
                                <a id="btnEditarDerivador" class="navi-link pointer">
                                    <span class='symbol symbol-30 symbol-circle mr-3' >
                                        <span id="circuloEditarDerivador" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar Derivador">
                                            <i id="iEditDerivador" class="far fa-edit text-dark-50"></i>
                                        </span>
                                    </span>
                                </a>
                            </div>
                            <div class="float-right" >
                                <a id="buscarDerivador" class="navi-link" href="#" >
                                    <span class='symbol symbol-30 symbol-circle mr-3'>
                                        <span id="circuloBuscarDerivador" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar Derivador">
                                            <i id="iBuscarDerivador" class="fas fa-search text-blue"></i>
                                        </span>
                                    </span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div id="formRegistro" >
                        <div id="divForm" class="row">
                            <div class="col-12 row">
                                <input id="txtIdDerivador" name="idDerivador" type="text" class="form-control ocultar" autocomplete="off" placeholder="" />
                                <div class="col-4">
                                    <label>Código:</label>
                                    <input id="txtCodigoDerivador" maxlength="10" name="cderivCodigo" type="text" class="form-control " autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>Nombre:</label>
                                    <input id="txtNombreDerivador" maxlength="50" name="cderivDescripcion" type="text" class="form-control " autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>Orden</label>
                                    <input id="numOrdenDerivador" value="1" name="cderivSort" type="number" class="form-control " autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>Host</label>
                                    <input id="txtHostDerivador" maxlength="20" name="cderivHost" type="text" class="form-control " autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>Horario Atención</label>
                                    <input id="txtHoraDerivador" maxlength="100" name="cderivHorarioatencion" type="text" class="form-control " autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>Teléfono</label>
                                    <input id="txtFonoDerivador" maxlength="20" name="cderivTelefono" type="text" class="form-control " autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>Dirección</label>
                                    <input id="txtDireccionDerivador" maxlength="80" name="cderivDireccion" type="text" class="form-control " autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>E-mail</label>
                                    <input id="txtEmailDerivador" maxlength="30" name="cderivEmail" type="email" class="form-control " autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>Persona Encargada</label>
                                    <input id="txtEncargadoDerivador" maxlength="60" name="cderivNombreencargado" type="text" class="form-control " autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                    <div class="col-3">
                                        <label class="switch-content switch-comprobante switch-active">
                                            <input id='checkBoxActivo' name='cderivActivo' class=" mt-5" type='checkbox' checked value='S' />
                                            <div></div>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <div class="col-md-6 form-group mt-6 row">
                        <div id="divBtnAgregar" class="col-6">
                            <button id="btnAgregar" type="submit" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" ><i class="far fa-save"></i>Guardar</button>
                        </div>
                        <div id="divBtnUpdate" class="col-6 ocultar">
                            <button id="btUpdate" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" name="update" type="submit"><i class="far fa-save"></i>Confirmar</button>
                            <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                        </div>

                    </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <script src="https://unpkg.com/imask"></script>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/ConfiguracionDerivadores.js"></script>
    </body>
</html>
