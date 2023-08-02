<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Contenedores</title>
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
                    <div id="panelEnvases" class="card border-15">
                        <div class="card-header border-15" id="headingEnvases">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    B&uacute;squeda de Tipos de Contenedores
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevoEnvase" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarEnvase" class='symbol-label bg-hover-blue hoverIcon' href="#" data-toggle="tooltip" title="Nuevo contenedor">
                                                <i id="iAgregarEnvase" class='fas fa-plus text-blue'></i>
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
                        <div id="collapseHeader" class="collapse show container-content" aria-labelledby="headingEnvases" data-parent="#headingEnvases">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">C&oacute;digo</label>
                                            <div class="col-8">
                                                <input id="txtCodigoFiltro" type="text" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Nombre</label>
                                            <div class="col-8">
                                                <input id="txtDescripcionFiltro" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Tipo de Muestra</label>
                                            <div class="col-8">
                                                <select class="form-control selectpicker" id="selectTipoMuestraFiltro" data-live-search="true">
                                                    <option value="0" selected>TODOS</option>
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
                                    <div class=" table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th id="thNombre" scope="col">C&oacute;digo</th>
                                                    <th>Tipos de contenedor</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron Contenedores con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Contenedores encontrados: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                </div>
                <!-- FORMULARIO REGISTRO ENVASES -->
                <div class="card mt-5 border-15">
                    <div class="card-header border-15"> 
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                Registro de Tipos de Contenedores
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
                                        <a id="btnEditarEnvase" class="navi-link pointer">
                                            <span class='symbol symbol-30 symbol-circle'>
                                                <span id="circuloEditarEnvase" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar contenedor">
                                                    <i id="iEditEnvase" class="far fa-edit text-dark-50"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                                <div class="float-right" >
                                    <div id="divbuscarEnvase">
                                        <a id="buscarEnvase" class="navi-link" href="#" >
                                            <span class='symbol symbol-30 symbol-circle'>
                                                <span id="circuloBuscarEnvase" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar contenedor">
                                                    <i id="iBuscarEnvase" class="fas fa-search text-blue"></i></span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <form id="formRegistroTest" method="post" enctype="multipart/form-data">
                            <div id="divForm" class="row">
                                <div class="col-12">
                                    <input id="txtIdEnvase" name="idEnvase" type="text" class="form-control ocultar" autocomplete="off" placeholder=""/>
                                    <div class="row">
                                        <div class="col-4">
                                            <label>Código:</label>
                                            <input id="txtCodigo" name="cenvCodigo" type="text" class="form-control formEnvases isRequired " maxlength="15" autocomplete="off" placeholder=""/>
                                            <div class="d-md-none mb-2"></div>
                                        </div>
                                        <div class="col-4">
                                            <label>Descripción:</label>
                                            <input id="txtDescripcion" name="cenvDescripcion" type="text" class="form-control formEnvases isRequired " maxlength="50"  autocomplete="off" placeholder=""/>
                                            <div class="d-md-none mb-2"></div>
                                        </div> 
                                        <div class="col-4">
                                            <label for="" class="">Tipo de muestra: </label>
                                            <select class="form-control formEnvases" id="selectTipoMuestra" name="cenvIdtipomuestra" class="formEnvases isRequired ">
                                                <option value="0" selected>SIN ESPECIFICAR</option>
                                            </select>
                                        </div>
                                        <div class="col-4">
                                            <label>Orden:</label>
                                            <input id="txtOrden" name="orden" type="number" class="form-control formEnvases"   autocomplete="off" placeholder=""/>
                                            <div class="d-md-none mb-2"></div>
                                        </div>

                                        <div class="col-4">
                                            <label class="col-form-label text-right ">Volumen Total:</label>
                                            <div class="form-group d-flex">
                                                <input id="numVolTotal" type="number" class="form-control formEnvases" value="0" name="cenvVolumenml" />
                                                <label>&nbsp;ml</label>
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <label class="col-form-label text-right">Volumen &Uacute;til:</label>
                                            <div class="form-group d-flex">
                                                <input id="numVolUtil" type="number" class="form-control formEnvases" value="0" name="cenvVolumenutilmicrolt" />
                                                <label>&nbsp;ml</label>
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <label>Host</label>
                                            <input id="txtHost"  maxlength="20" name="cenvDescripcion" type="text" class="form-control formEnvases"  autocomplete="off" placeholder=""/>
                                            <div class="d-md-none mb-2"></div>
                                        </div> 
                                        <div class="col-4">
                                            <label class="col-form-label col-12">Imagen:</label>
                                            <div class="image-input  image-input-outline" id="kt_image_5" ">
                                                <div   id="fotoEnvaseWrapper" class="image-input-wrapper" style="height: 300px;width: 300px;"></div>
                                                <label id="cambiarImagenEnvase" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="change" data-toggle="tooltip" title="" data-original-title="Cambiar imagen">
                                                    <i class="fa fa-pen icon-sm"></i>
                                                    <input type="file" id="imagenEnvase" name="imagenEnvase" accept=".png, .jpg, .jpeg"/>
                                                    <input type="hidden" name="profile_avatar_remove"/>
                                                </label>

                                                <span id="cancelImage" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="cancel" data-toggle="tooltip" title="Cancel avatar">
                                                    <i class="ki ki-bold-close icon-xs"></i>
                                                </span>

                                                <span id="removeImage" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="remove" data-toggle="tooltip" title="Remove avatar">
                                                    <i class="ki ki-bold-close icon-xs"></i>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <label id="lblEstado" class=" col-4 col-form-label">Activo</label>
                                            <div class="col-3">
                                                <label class="switch-content switch-comprobante switch-active">
                                                    <input id='checkBoxActivo' name='cenvActivo' class="formEnvases" type='checkbox' checked />
                                                    <div></div>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <!--<input id="txtEstado" name="estado" type="text" class="form-control mt-5 ocultar" value="S" autocomplete="off" placeholder=""/>-->
                                </div>
                                <div class="col-md-6 form-group mt-6 row">

                                    <div id="divAgregarBtn" class="col-6">
                                        <button id="btnAgregarEnvase" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Guardar</button>
                                    </div>
                                    <div id="divActualizarBtn" class="col-6 ocultar">
                                        <button id="btnUpdateEnvase" name="update" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Confirmar</button>
                                        <button id="btnCancelEnvase" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                                    </div>
                                </div>
                        
                        </div>
                    </form>
                </div>
            </div>
            <!-- FIN FORMULARIO -->
        </div>
        </div>
        <script src="https://unpkg.com/imask"></script>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgMuestras.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/envases/ConfiguracionEnvases.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/envases/ConfiguracionEnvasesForm.js"></script>
    </body>
</html>
