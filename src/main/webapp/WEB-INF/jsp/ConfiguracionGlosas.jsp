<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Glosas</title>
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
                <c:if test="${mensaje!=null}"><div class="alert alert-success" role="alert">${mensaje}</div></c:if>
                <div class="accordion col-12 pl-0 pr-0" id="accordionBusquedaGlosa">
                    <div class="card border-15">
                        <div class="card-header border-15" id="headingGlosas">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true"
                                        aria-controls="collapseHeader">
                                    Búsqueda de Glosas
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevaGlosa" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarGlosa" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nueva glosa">
                                                <i id="iAgregarGlosa" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a>
                                    <a id="btnLimpiarFiltro" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle mr-3 ">
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue  hoverIcon"  href="#" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div>

                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content" data-parent="#accordionBusquedaGlosa">
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
                                            <label class="col-4 col-form-label">Glosa</label>
                                            <div class="col-8">
                                                <input id="txtGlosaFiltro" class="form-control" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Sección</label>
                                            <div class="col-8">
                                                <select id="selectSeccionFiltro" class="form-control selectpicker" data-live-search="true">
                                                    <option value="0" selected>TODOS</option>
                                                    <c:forEach var="seccion" items="${listaSecciones}">
                                                        <option value="${seccion.csecIdseccion}">${seccion.csecDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <label>
                                            <input id="chkMostrarActivos" type="checkbox" /> Mostrar solo activos
                                        </label>
                                    </div>
                                    <!--<a id="btnBuscarFiltro" class="btn btn-light-primary font-weight-bold mr-2 ml-4 pointer"  ><i class="la la-search"></i>Buscar</a>-->
                                </div>
                                <!-- FIN FORMULARIO FILTRO -->
                                <!-- TABLA FILTRO -->
                                <div class="col">
                                    <div class=" table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th id="thNombre" scope="col">Código</th>
                                                    <th>Glosas</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro"></tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron glosas con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Glosas encontradas: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                </div>
            <!-- FORMULARIO REGISTRO GLOSA -->
            <div class="card mt-5 border-15">
                <div class="card-header border-15"> 
                    <div class="card-title row col-12 d-flex justify-content-between">
                        <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                            Registro de Glosas
                        </h3>
                        <div class="col-2 justify-content-right iconos-registro">
                            <div class="float-right">
                                <a id="btnLimpiar" class="navi-link pointer" >
                                    <span class="symbol symbol-30 symbol-circle mr-3 ">
                                        <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                            <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>   
                            <div class="float-right" >
                                <a id="btnEditar" class="navi-link pointer">
                                    <span class='symbol symbol-30 symbol-circle' >
                                        <span id="circuloEditarGlosa" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar glosa">
                                            <i id="iEditGlosa" class="far fa-edit text-dark-50"></i>
                                        </span>
                                    </span>
                                </a>
                            </div>
                            <div class="float-right" >
                                <a id="buscarGlosa" class="navi-link" href="#" >
                                    <span class='symbol symbol-30 symbol-circle'>
                                        <span id="circuloBuscarGlosa" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar glosa">
                                            <i id="iBuscarGlosa" class="fas fa-search text-blue"></i>
                                        </span>
                                    </span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <form id="formRegistroGlosa" method="post">
                        <div id="divForm" class="row">
                            <div class="col-12 ">
                                <input id="txtIdGlosa" name="idGlosa" type="text" class="form-control ocultar" autocomplete="off" />
                                <div class="row">
                                    <div class="col-4">
                                        <label>Código:</label>
                                        <input id="txtCodigo" maxlength="20" name="cgCodigoglosa" type="text" class="form-control formGlosas"  autocomplete="off" />
                                        <div class="d-md-none mb-2"></div>
                                    </div>  
                                    <div class="col-4">
                                        <label>Sección:</label>
                                        <select class="form-control formGlosas selectpicker" id="selectSeccion" name="secciones" data-live-search="true">
                                            <c:forEach var="seccion" items="${listaSecciones}">
                                                <option value="${seccion.csecIdseccion}">${seccion.csecDescripcion}</option>
                                            </c:forEach>
                                        </select>
                                    </div> 
                                    <div class="col-4">
                                        <label>Glosa:</label>
                                        <input id="txtGlosa" maxlength="250" name="cgDescripcion" type="text" class="form-control formGlosas"  autocomplete="off" />
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-4">
                                        <label>Orden:</label>
                                        <input id="numOrden" value="1" name="orden" type="number" class="form-control formGlosas"  onkeypress="return isNumberKey(event)"  autocomplete="off" />
                                        <div class="d-md-none mb-2"></div>
                                    </div> 
                                    <div class="col-4">
                                        <label>Host</label>
                                        <input id="txtHost" maxlength="20" name="host" type="text" class="form-control formGlosas"   autocomplete="off" />
                                        <div class="d-md-none mb-2"></div>
                                    </div> 
                                    <div class="col-4">
                                        <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                        <div class="col-3">
                                            <label class="switch-content switch-comprobante switch-active">
                                                <input id='checkBoxActivo' name='estado' class="formGlosas" type='checkbox' checked value='S' />
                                                <div></div>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 form-group mt-6 row">
                                <div id="divBtnAgregar" class="col-6">
                                    <button id="btnAgregarGlosa" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Guardar</button>
                                </div>
                                <div id="divBtnUpdate" class="col-6 ocultar">
                                    <button id="btnUpdateGlosa" name="update" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Confirmar</button>
                                    <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            <!-- FIN FORMULARIO REGISTRO GLOSAS -->
            </div>
        </div>
        </div>
        <script src="https://unpkg.com/imask"></script>
    <jsp:include page="common/Scripts.jsp"/>
    <script src="<%=request.getContextPath()%>/resources/js/glosas/ConfiguracionGlosas.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/glosas/ConfiguracionGlosasForm.js"></script>
</body>
</html>
