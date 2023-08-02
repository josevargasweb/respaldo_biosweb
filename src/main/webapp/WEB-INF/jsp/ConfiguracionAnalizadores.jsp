<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Analizadores</title>
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
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0">
                    <div id="panelAnalizador" class="card border-15">
                        <div class="card-header border-15" id="headingAnalizador">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion"
                                        data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true"
                                        aria-controls="collapseHeader">B&uacute;squeda de Analizadores</h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevoAnalizador" class="navi-link"> 
                                        <span  class='symbol symbol-30 symbol-circle '> 
                                            <span id="circuloAgregarAnalizador" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nuevo Analizador"> 
                                                <i id="iAgregarAnalizador" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a> 
                                    <a id="btnLimpiarFiltro" class="navi-link"> 
                                        <span class="symbol symbol-30 symbol-circle "> 
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue  hoverIcon" href="#" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span> 
                                        <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content"
                            aria-labelledby="headingAnalizador" data-parent="#headingAnalizador">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label id="" class="col-4 col-form-label">C&oacute;digo</label>
                                            <div class="col-8">
                                                <input id="txtCodigoFiltro" class="form-control" name="" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label id="" class="col-4 col-form-label">Nombre</label>
                                            <div class="col-8">
                                                <input id="txtNombreFiltro" class="form-control" name="" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <!--
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Secci&oacute;n</label>
                                            <div class="col-8">
                                                <select id="selectSeccionFiltro" class="form-control"></select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <label>
                                            <input id="chkMostrarActivos" type="checkbox" /> Mostrar solo activos
                                        </label>
                                    </div>
                                    -->
                                </div>
                                <!-- FIN FORMULARIO FILTRO -->
                                <!-- TABLA FILTRO -->
                                <div class="col">
                                    <div class="table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th id="thIdentificador" scope="col">Código</th>
                                                    <th id="thNombre" scope="col">Analizador</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron analizadores con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro" class="ocultar">Analizadores encontrados: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                    <!-- FORMULARIO REGISTRO ANALIZADOR -->
                    <div id="panelAnalizadorResultados" class="card mt-5 border-15">
                        <div class="card-header pl-2 border-15">
                            <div class="card-title row col-12 d-flex justify-content-between">
                                <h3 id="" class="card-label col-4 pl-0">
                                    Registro de Analizadores
                                </h3>
                                <!--<div class="col-6">
                                    <ul class="nav nav-pills">
                                        <li class="nav-item">
                                            <a id="anchorRegistro" class="nav-link active" data-toggle="tab" href="#panelDatosBasicos">
                                                <span class="nav-icon"><i class="flaticon2-paper"></i></span>
                                                <span class="nav-text">Datos&nbsp; B&aacute;sicos</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>-->
                                <div class="col-2 justify-content-right iconos-registro ">
                                    <div class="float-right">
                                        <div id="divbtnLimpiar">
                                            <a id="btnLimpiar" class="navi-link pointer">
                                                <span class="symbol symbol-30 symbol-circle">
                                                    <span id="circuloLimpiar" data-toggle="tooltip"
                                                        title="Limpiar"
                                                        class="symbol-label bg-hover-blue ">
                                                        <i id="iLimpiar"
                                                            class="la la-broom icon-xl  text-blue"></i></span>
                                                </span> <span class="navi-text"></span>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="float-right">
                                        <div id="divbtnEditar">
                                            <a id="btnEditar" class="navi-link pointer"> 
                                                <span class='symbol symbol-30 symbol-circle'>
                                                    <span id="circuloEditarAnalizador"
                                                        class='symbol-label circuloIcon'
                                                        data-toggle="tooltip" title="Editar Analizador">
                                                        <i id="iEditAnalizador"
                                                            class="far fa-edit text-dark-50 "></i>
                                                    </span>
                                                </span>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="float-right">
                                        <div id="divbuscarAnalizador">
                                            <a id="buscarAnalizador" class="navi-link" href="#"> 
                                                <span class='symbol symbol-30 symbol-circle mr-3'> 
                                                    <span id="circuloBuscarAnalizador" class='symbol-label bg-hover-blue hoverIcon'
                                                         data-toggle="tooltip" title="Buscar Analizador">
                                                        <i id="iBuscarAnalizador" class="fas fa-search text-blue"></i>
                                                    </span>
                                                </span>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <form id="formRegistroAnalizador" method="post" enctype="multipart/form-data">
                                <div class="col-12 pl-0">
                                    <h5 id="tituloRegistro" class="card-label text-center">
                                        Analizador
                                    </h5>
                                </div>
                                <div id="divForm" class="row">
                                    <div class="col-12">
                                        <!--<div class="tab-content" id="myTabContent">-->
                                            <div class="tab-pane fade show active" id="panelDatosBasicos" role="tabpanel" aria-labelledby="panelResultado">
                                                <div class="row">
                                                    <input id="txtIdAnalizador" type="text" class="form-control ocultar" />
                                                    <div class="col-4">
                                                        <label>Código *</label>
                                                        <input id="txtCodigo" maxlength="15" name="" type="text" class="form-control formAnalizador" autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Nombre *</label>
                                                        <input id="txtNombre" maxlength="50" name="" type="text" class="form-control formAnalizador"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Modelo</label>
                                                        <input id="txtModelo" maxlength="30" name="" type="text" class="form-control formAnalizador"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Empresa</label>
                                                        <input id="txtEmpresa" maxlength="30" name="" type="text" class="form-control formAnalizador"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Código Proceso 1</label>
                                                        <input id="txtCodigoProceso1" maxlength="12" name="" type="text" class="form-control formAnalizador"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Código Proceso 2</label>
                                                        <input id="txtCodigoProceso2" maxlength="12" name="" type="text" class="form-control formAnalizador"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Código Proceso 3</label>
                                                        <input id="txtCodigoProceso3" maxlength="12" name="" type="text" class="form-control formAnalizador"   autocomplete="off" />
                                                        <div class="d-md-none mb-2"></div>
                                                    </div>
                                                    <div class="col-4">
                                                        <label>Imagen</label>
                                                        <div>
                                                            <div class="image-input image-input-outline" id="inputImageAnalizador" >
                                                                <div id="imagenWrapper" class="image-input-wrapper"></div>
                                                                <label id="cambiarImagenAnalizador" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="change" data-toggle="tooltip" title="" data-original-title="Cambiar imagen">
                                                                    <i class="fa fa-pen icon-sm text-muted"></i>
                                                                    <input type="file" id="imageAnalizador" name="imageAnalizador" accept=".png, .jpg, .jpeg"/>
                                                                    <input type="hidden" name="profile_avatar_remove"/>
                                                                </label>
                                                                <span id="eliminarImageAnalizador" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="cancel" data-toggle="tooltip" title="Eliminar imagen">
                                                                    <i class="ki ki-bold-close icon-xs text-muted"></i>
                                                                </span>
                                                                <span id="removeImageAnalizador" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="remove" data-toggle="tooltip" title="Eliminar imagen">
                                                                    <i class="ki ki-bold-close icon-xs text-muted"></i>
                                                                </span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        <!--</div>-->
                                    </div>
                                    <div class="col-md-6 form-group mt-6 row">
                                        <div id="divAgregarBtn" class="col-6">
                                            <button id="btnAgregarAnalizador" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Guardar</button>
                                        </div>
                                        <div id="divActualizarBtn" class="col-6 ocultar">
                                            <button id="btnUpdateAnalizador" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Confirmar</button>
                                            <button id="btnCancelAnalizador" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/analizadores/ConfiguracionAnalizadores.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/analizadores/ConfiguracionAnalizadoresForm.js"></script>
    </body>
</html>

