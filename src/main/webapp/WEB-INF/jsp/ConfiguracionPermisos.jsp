<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <jsp:include page="common/Styles_1.jsp"/>
        <title>BiosLIS | Permisos por cargo</title>
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
        <div class="container container-main-content  container-permisos mt-5">
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0" id="accordionExample8">
                    <div class="card">
                        <div class="card-header border-15" id="headingOne8">
                            <div class="card-title row justify-content-between pt-2 pb-2">
                                <h3 class="col-12 pl-0"  data-toggle="collapse" data-target="#collapseOne8">
                                    Configuración de Permisos
                                </h3>
                            </div>
                        </div>
                        <div id="collapseOne8" class="collapse show" data-parent="#accordionExample8">
                            <div class="card-body row">
                                <!-- TABLA CARGOS -->
                                <div class="col-6">
                                    <div class="col-md-12 pl-0 pr-0 container-table-conf">
                                        <table id="tableFiltro" class="table table-hover">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th id="thNombre" scope="col">Cargos</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyCargos">
        
                                            </tbody>
                                        </table>
                                    </div>
                                                                    
                                </div>
                            
                                <div class="col-6" >
                                    <h3>Cargo: <span id="cargoLbl">Seleccionar</span></h3>
                                    <input type="hidden" id="hIdCargo" />
                                    <div class="col-12 pl-0 pr-0 container-table-cargo">
                                        <ul id="listaModulosCargo" class="pl-0"></ul>
                                    </div>
                                </div>
                            <div class="col-12">
                                <hr>
                                <p>
                                    <b>Nuevo Cargo</b>
                                </p>
                                <div class="row border-top border-bottom">
                                    <div class="col-12 mt-2">
                                        <label>Nombre</label>
                                        <input id="txtNuevoCargo" name="nuevoCargo" type="text" autocomplete="off" />
                                        <div class="col-12 pl-0 d-flex justify-content-between mb-2">
                                            <button id="btnAgregarCargo" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " onclick="agregarCargo()"><i class="far fa-save"></i>Agregar Cargo</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
    
                                <!-- FIN TABLA FILTRO -->
                                <div class="col-12 d-flex justify-content-between">
                                    <button id="btnGuardar" onclick="guardarPermisos()" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  ocultar"><i class="far fa-save"></i>Guardar</button>
                                    <button id="btnCancelar" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 ocultar">Cerrar</button>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/permisos/ConfiguracionPermisos.js"></script>
    </body>
 
</html>
