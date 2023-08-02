<%-- 
    Document   : LocalizacionSalasCamas
    Created on : 02-06-2021, 10:57:07
    Author     : marco.caracciolo
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
        <title>BIOS-LIS | Localización de Salas y Camas</title>
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
        
        <c:if test="${mensaje!=null}"><div class="alert alert-success" role="alert">${mensaje}</div></c:if>
        <div class="container container-main-content container-configuracion">
            <div class="row d-flex justify-content-center">
                <div class="accordion col-12 pl-0 pr-0">
                    <div class="card border-15">
                        <div class="card-header border-15">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    Registro y Edición de Localizaciones (Salas y Camas)
                                </h3>
                            </div>
                        </div>


                        <div class="card-body">
                            <div class="col-12 row">
                                <input type="hidden" id="idCentroSalud" name="idCentroSalud" value="${idCentro}" />
                                <label id="" class="col-3 col-form-label mt-3">Centro de Salud:</label>
                                <select class="form-control col-3 mt-3 disabledForm" id="idCentroFiltro" name="cs"></select>
                            </div>
                            <div class="col-12">
                                <label class="col-md-12 checkbox checkbox-primary">
                                    <input id="checkBoxInactivos" type="checkbox" name="" value="S" />&nbsp;Mostrar Servicios inactivos
                                    <span></span>
                                </label>
                            </div>
                            <div class="col-12">
                                <input id="ipEquipo" name="c" type="text" class="form-control ocultar">
                                <div class='accordion' id="listaServicios"></div>
                                <div id="lblError" class="alert alert-danger ocultar" role="alert">No se encontraron servicios en el centro de salud señalado.</div>
                                <a id='newServicio' href='./ConfiguracionServicios' class='btn btn-lg btn-blue-primary font-weight-bold mt-3 pointer'><i class='fas fa-plus'></i>Nuevo Servicio</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!-- MODAL SALAS (AGREGAR Y EDITAR) -->         
        <div class="modal fade" id="modalSalas" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false" aria-labelledby="staticBackdrop" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="col">
                            <h5 id="tituloCrudSala" class="modal-title text-center">Nueva Sala</h5>
                            <input id="idSala" type="hidden" value="0" />
                            <input id="idServicio" type="hidden" value="0" />
                            <div class="col-12 mt-2">
                                <div class="form-group row">
                                    <label class="col-4 col-form-label">C&oacute;digo Sala: *</label>
                                    <div class="col-8">
                                        <input id="txtCodigoSala" class="form-control" name="" type="text" maxlength="15" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-group row">
                                    <label class="col-4 col-form-label">Nombre Sala: *</label>
                                    <div class="col-8">
                                        <input id="txtNombreSala" class="form-control" name="" type="text" maxlength="50" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 pl-0 pr-0 d-flex justify-content-between">
                            <button id="btnGuardarSala" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"><i class="far fa-save"></i>Guardar</button>
                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" data-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                
        <!-- MODAL CAMAS (AGREGAR Y EDITAR) -->         
        <div class="modal fade" id="modalCamas" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false" aria-labelledby="staticBackdrop" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="col">
                            <h5 id="tituloCrudCama" class="modal-title text-center">Nueva Sala</h5>
                            <input id="idCama" type="hidden" value="0" />
                            <input id="idSalaCama" type="hidden" value="0" />
                            <div class="col-12 mt-2">
                                <div class="form-group row">
                                    <label class="col-4 col-form-label">C&oacute;digo Cama: *</label>
                                    <div class="col-8">
                                        <input id="txtCodigoCama" class="form-control" name="" type="text" maxlength="15" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-group row">
                                    <label class="col-4 col-form-label">Nombre Cama: *</label>
                                    <div class="col-8">
                                        <input id="txtNombreCama" class="form-control" name="" type="text" maxlength="50" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 pl-0 pr-0 d-flex justify-content-between">
                            <button id="btnGuardarCama" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"><i class="far fa-save"></i>Guardar</button>
                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" data-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/localizaciones/SalasCamas.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/localizaciones/SalasCamasForm.js"></script>
    </body>
</html>
