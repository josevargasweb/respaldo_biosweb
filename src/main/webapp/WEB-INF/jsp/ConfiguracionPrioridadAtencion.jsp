<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BIOS-LIS | Configuración de Prioridad de Atenci&oacute;n</title>
        <jsp:include page="common/Styles_1.jsp"/>
        <!--begin::Page Vendors Styles(used by this page)-->
        <link href="<%=request.getContextPath()%>/resources/ColorPicker/css/farbtastic.css" rel="stylesheet" type="text/css" />
        <!--end::Page Vendors Styles-->
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
        <c:if test="${mensaje!=null}"><input type="hidden" id="messageSuccess" value="${mensaje}" /></c:if>
        <c:if test="${mensajeError!=null}"><input type="hidden" id="messageError" value="${mensajeError}" /></c:if>
        <div class="container container-main-content container-configuracion mt-5">
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0">
                    <div class="card border-15">
                        <div class="card-header border-15" id="accordionPrioridad">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    Búsqueda de Prioridad de Atención
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevaPrioridadAtencion" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarPrioridadAtencion" class='symbol-label bg-hover-blue hoverIcon' href="#" data-toggle="tooltip" title="Nueva Prioridad Atenci&oacute;n">
                                                <i id="iAgregarPrioridadAtencion" class='fas fa-plus text-blue'></i>
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
                        <div id="collapseHeader" class="collapse show container-content" aria-labelledby="accordionPrioridad" data-parent="#accordionPrioridad">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
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
                                        <table class='table table-hover table-striped' id="tableFiltro">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th>#</th>
                                                    <th>Prioridad Atención</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro"></tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron prioridades de atenci&oacute;n con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Prioridades de atenci&oacute;n encontradas: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                </div>
                <!-- FORMULARIO REGISTRO PRIORIDAD ATENCIÓN -->
                <div class="card mt-5 border-15">
                    <div class="card-header border-15">
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                Registro de Prioridad de Atención
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
                                            <span id="circuloEditarLab" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar prioridad atención">
                                                <i id="iEditPrioridad" class="far fa-edit text-dark-50"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                                <div class="float-right" >
                                    <a id="buscarPrioridadAtencion" class="navi-link" href="#" >
                                        <span class='symbol symbol-30 symbol-circle '>
                                            <span id="circuloBuscarLab" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar prioridad atención">
                                                <i id="iBuscarLab" class="fas fa-search text-blue"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <form method="POST" enctype="multipart/form-data">
                            <div id="divForm" class="row">
                                <div class="col-12 row">
                                    <input id="txtIdPrioridad" name="" type="text" class="form-control ocultar" autocomplete="off" placeholder=""/>
                                    <div class="col-4">
                                        <label>Nombre: </label>
                                        <input id="txtDescripcion" maxlength="50" name="cpaDescripcion"  type="text" class="form-control formPrioridad" placeholder=""/>
                                    </div>
                                    <div class="col-4">
                                        <label>Nivel de prioridad: </label>
                                        <input id="numPrioridad" name="cpaPrioridad" value="1" type="number" class="form-control formPrioridad" />
                                    </div>
                                    <!--<select id="NivelPrioIngreso" name="cpaPrioridad" class="form-control selectpicker SSM LimpearTexto1">
                                        <option value="" >Nada seleccionado</option>
                                    </select>-->
                                    <div class="col-4">
                                        <label>Orden: </label>
                                        <input id="numOrdenIngreso" name="cpaSort" value="1" type="number" class="form-control formPrioridad" placeholder=""/>
                                    </div>
                                    <div class="col-4">
                                        <label id="lblEstado" class="col-12 col-form-label">Activo</label>
                                        <div class="col-3">
                                            <label class="switch-content switch-comprobante switch-active">
                                                <input id='checkBoxActivo' name='cpaActivo' class="formPrioridad" type='checkbox' checked value='S' />
                                                <div></div>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-4">
                                        <label>Color</label>
                                        <input class="col-3 form-control formPrioridad" type="text" id="txtColor" name="cpaColorprioridad" value="#123456" style="height: 30px;" />
                                        <div class="col-6" id="colorpicker" class="formPrioridad"></div>
                                    </div>
                                    <div class="col-4">
                                        <label class="col-form-label col-12">Imagen:</label>
                                        <div class="image-input image-input-empty image-input-outline " id="kt_image_5" style="background-image: url(assets/media/users/blank.png);">
                                            <div class="image-input-wrapper" style="height: 200px;width: 200px;"></div>
                                            <label id="cambiarImagen" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="change" data-toggle="tooltip" title="" data-original-title="Cambiar imagen">
                                                <i class="fa fa-pen icon-sm text-muted"></i>
                                                <input src="" type="file" name="imagen" id="imagenn" accept=".png, .jpg, .jpeg" />
                                                <input type="hidden" name="profile_avatar_remove"/>

                                            </label>

                                            <span id="cancelImage" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="cancel" data-toggle="tooltip" title="Eliminar imagen">
                                                <i class="ki ki-bold-close icon-xs text-muted"></i>
                                            </span>

                                            <span id="removeImage" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="remove" data-toggle="tooltip" title="Eliminar imagen">
                                                <i class="ki ki-bold-close icon-xs text-muted"></i>
                                            </span>
                                        </div>
                                        <!--     <input id="output" type="image" src="" > --> 
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
		<script src="https://unpkg.com/imask"></script>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/prioridadatencion/ConfiguracionPrioridadAtencion.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/prioridadatencion/ConfiguracionPrioridadAtencionForm.js"></script>
        <script src="<%=request.getContextPath()%>/resources/ColorPicker/js/farbtastic.js"></script>
    </body>
</html>

