<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Servicios</title>
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
        <div class="container container-main-content container-configuracion mt-5">
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0">
                    <div class="card border-15">
                        <div class="card-header border-15" id="accordionServicios">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    Búsqueda de Servicios
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevoServicio" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarServicio" class='symbol-label bg-hover-blue hoverIcon' href="#" data-toggle="tooltip" title="Nuevo servicio">
                                                <i id="iAgregarServicio" class='fas fa-plus text-blue'></i>
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
                        <div id="collapseHeader" class="collapse show container-content" data-parent="#accordionServicios">
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
                                            <label class="col-4 col-form-label">Servicio</label>
                                            <div class="col-8">
                                                <input id="txtDescripcionFiltro" class="form-control" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Centro</label>
                                            <div class="col-8">
                                                <select class="form-control" id="idCentroFiltro" name="cs">
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
                                <div class="col">
                                    <div class="table-container mb-3">
                                        <!--<label>Servicios: <span id="lblCantidadExamenes"></span></label>-->
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th scope="col">Codigo</th>
                                                    <th id="thNombre" scope="col">Servicios</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron servicios con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Servicios encontrados: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                </div>
                <!-- FORMULARIO REGISTRO SERVICIOS -->
                <div class="card mt-5 border-15">
                    <div class="card-header border-15">
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                Registro de Servicios
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
                                <div class="float-right" >
                                    <a id="btnEditarServicio" class="navi-link pointer">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloEditarServicio" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar servicio">
                                                <i id="iEditServicio" class="far fa-edit text-dark-50"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                                <div class="ml-1 float-right" >
                                    <a id="buscarServicio" class="navi-link" href="#" >
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloBuscarServicio" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar servicio">
                                                <i id="iBuscarServicio" class="fas fa-search text-blue"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div id="formRegistroServicio">
                            <div id="divForm" class="row">
                                <div class="col-12 row">
                                    <input id="txtIdServicio" name="idServicio" type="text" class="form-control ocultar" autocomplete="off" placeholder="" />
                                    <input id="ipEquipo" name="ip" type="text" class="form-control ocultar">
                                    <div class="col-4">
                                        <label>Código:</label>
                                        <input id="txtCodigo" name="csCodigo" type="text" class="form-control formServicio" maxlength="15" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-4">
                                        <label>Servicio:</label>
                                        <input id="txtDescripcion" name="csDescripcion" type="text" class="form-control formServicio" maxlength="50" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-4">
                                        <label for="" class="">Centro:</label>
                                        <select class="form-control formServicio" id="csCentro" name="centroMedico">
                                        </select>
                                    </div>
                                    <div class="col-4">
                                        <label>Orden:</label>
                                        <input id="numOrden" name="csSort" type="number" value="1" class="form-control formServicio" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-4">
                                        <label>E-mail:</label>
                                        <input id="txtEmail" name="csEmail" type="email" class="form-control formServicio" maxlength="50" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-6">
                                        <div id="divBtnInteroperabilidad">
                                            <!-- Button modal interoperabilidad (host) -->
                                            <button id="btnInoperabilidad" type="button" class="btn btn-lg btn-blue-primary mt-3" data-toggle="modal" data-target="#modalInteroperabilidad">
                                                Interoperabilidad (Host)
                                            </button>
                                            <!-- MODAL INTEROPERABILIDAD (HOST) -->    
                                            <div class="modal fade" id="modalInteroperabilidad" tabindex="-1" role="dialog" aria-labelledby="staticBackdrop" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-scrollable" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="exampleModalLabel">Interoperabilidad (Host)</h5>
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"> <i aria-hidden="true" class="ki ki-close"></i> </button>
                                                        </div>
                                                        <div class="modal-body" >
                                                            <div class="col">
                                                                <div class="col-12">
                                                                    <div class="form-group row">
                                                                        <label class="col-3 col-form-label">Host 1:</label>
                                                                        <div class="col-7">
                                                                            <input id="txtHost1" class="form-control formServicio" name="csHost" maxlength="20" type="text" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-12">
                                                                    <div class="form-group row">
                                                                        <label class="col-3 col-form-label">Host 2:</label>
                                                                        <div class="col-7">
                                                                            <input id="txtHost2" class="form-control formServicio" name="csHost2" maxlength="20" type="text" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-12">
                                                                    <div class="form-group row">
                                                                        <label class="col-3 col-form-label">Host 3:</label>
                                                                        <div class="col-7">
                                                                            <input id="txtHost3" class="form-control formServicio" name="csHost3" maxlength="20" type="text" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-12">
                                                                    <div class="form-group row">
                                                                        <label id="" class="col-3 col-form-label">LIS BAC:</label>
                                                                        <div class="col-7">
                                                                            <input id="txtHostMicro" class="form-control formServicio" name="csHostmicro" maxlength="20" type="text" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <!--
                                                        </div>
                                                        <div class="modal-footer">
                                                            <a id="btnLimpiarModal" class="btn btn-light-primary font-weight-bold"><i id="iLimpiarModal" class="la la-broom icon-xl  text-primary"></i>Limpiar</a>
                                                            -->
                                                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold" data-dismiss="modal">Ok</button>
                                                        </div>
                                                     </div>
                                                 </div>
                                             </div>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                        <div class="col-3">
                                            <label class="switch-content switch-comprobante switch-active"> <input
                                                id='checkBoxActivo' type="checkbox" tabindex="-1" name='csActivo' class="formServicio" checked value='S'>
                                                <div></div>
                                            </label> 
                                        </div>
                                    </div>

                                    <div class="col-12 mt-2">
                                        <label class="col-md-12 checkbox checkbox-primary">
                                            <input id="checkBoxUrgente" type="checkbox" name="csUrgente" class="formServicio" value="S" /><b>Urgente:</b> Órdenes con este servicio son marcadas como urgentes
                                            <span></span>
                                        </label>
                                    </div>
                                    <div class="col-12">
                                        <label class="col-md-12 checkbox checkbox-primary">
                                            <input id="checkBoxIndicador" type="checkbox" name="csIndicador" class="formServicio" value="S" /><b>Indicador:</b> Órdenes con este servicio se consideran como indicador estadístico
                                            <span></span>
                                        </label>
                                    </div>
                                    <!--
                                    <div class="col-12 mt-6">
                                        <label class="col-md-12 checkbox checkbox-primary">
                                            <input id="" type="checkbox" name="activo" value="N" /><b>Toma de Muestra Automática</b>
                                            <span></span>
                                        </label>
                                    </div>
                                    <div class="col-12">
                                        <label class="col-md-12 checkbox checkbox-primary">
                                            <input id="" type="checkbox" name="activo" value="N" /><b>Recepción de Muestra Automática</b>
                                            <span></span>
                                        </label>
                                    </div>
                                    -->
                                </div>
                                <div class="col-md-6 form-group mt-6 row">
                                    <div id="divAgregarBtn" class="col-6">
                                        <button id="btnAgregarServicio" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"><i class="far fa-save"></i>Guardar</button>
                                    </div>
                                    <div id="divActualizarBtn" class="col-6 ocultar">
                                        <button id="btnUpdateServicio" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" ><i class="far fa-save"></i>Confirmar</button>
                                        <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- FIN FORMULARIO SERVICIOS -->
            </div>
        </div>
        
        <jsp:include page="common/Scripts.jsp"/>
         <script src="https://unpkg.com/imask"></script>
        <script src="<%=request.getContextPath()%>/resources/js/ConfiguracionServicios.js"></script>
    </body>
</html>
