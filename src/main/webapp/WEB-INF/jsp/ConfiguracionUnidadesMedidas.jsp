<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración Unidades de Medidas</title>
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
                <div class="accordion col-12 pl-0 pr-0" >
                    <div class="card border-15">
                        <div class="card-header border-15" id="headingUnidadesMedida">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    Búsqueda de Unidades de Medida
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevaUnidad" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarUnidadMedida" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Nueva unidad de medida">
                                                <i id="iAgregarUnidadMedida" class='fas fa-plus text-blue'></i>
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
                        <div id="collapseHeader" class="collapse show" data-parent="#headingUnidadesMedida">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Código</label>
                                            <div class="col-8">
                                                <input id="txtCodigoFiltro"  class="form-control" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label id="" class="col-4 col-form-label">Unidad</label>
                                            <div class="col-8">
                                                <input id="txtUnidadFiltro" class="form-control" type="text" />
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
                                                    <th id="thNombre" scope="col">Código</th>
                                                    <th>Unidades de Medida</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron unidades de medida con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Unidades de medida encontradas: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                    <!-- FORMULARIO REGISTRO UNIDADES DE MEDIDA -->
                    <div class="card mt-5 border-15" id="panelInferior">
                        <div class="card-header border-15"> 
                            <div class="card-title row col-12 d-flex justify-content-between">
                                <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                    Registro Unidades de Medida
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
                                                <span id="circuloEditarUnidadMedida" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar unidad de medida">
                                                    <i id="iEditUnidadMedida" class="far fa-edit text-dark-50"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                    <div class="float-right">
                                        <a id="buscarUnidad" class="navi-link" href="#" >
                                            <span class='symbol symbol-30 symbol-circle mr-3'>
                                                <span id="circuloBuscarUnidadMedida" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar unidad de medida">
                                                    <i id="iBuscarUnidadMedida" class="fas fa-search text-blue"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            
                                <div id="divForm" class="row">
                                    <div class="col-12 row">
                                        <input id="txtIdUnidadMedida" name="idUnidad" type="text" class="form-control ocultar" autocomplete="off" placeholder=""/>
                                        <div class="col-4">
                                            <label>Código:</label>
                                            <input id="txtCodigo" maxlength="25" name="cumCodigo" type="text" class="form-control"  autocomplete="off" placeholder=""/>
                                            <div class="d-md-none mb-2"></div>
                                        </div>
                                        <div class="col-4">
                                            <label>Unidad:</label>
                                            <input id="txtDescripcion" maxlength="50" name="cumDescripcion" type="text" class="form-control "  autocomplete="off" placeholder=""/>
                                            <div class="d-md-none mb-2"></div>
    
                                            <button id="potenciaUno" type="button" class="btn btn-outline-secondary">x¹</button>
                                            <button id="potenciaDos" type="button" class="btn btn-outline-secondary">x²</button>
                                            <button id="potenciaTres" type="button" class="btn btn-outline-secondary">x³</button>
                                            <button id="potenciaCuatro" type="button" class="btn btn-outline-secondary">x⁴</button>
                                            <button id="potenciaCinco" type="button" class="btn btn-outline-secondary">x⁵</button>
                                            <button id="potenciaSeis" type="button" class="btn btn-outline-secondary">x⁶</button>
                                            <button id="potenciaSiete" type="button" class="btn btn-outline-secondary">x⁷</button>
                                            <button id="potenciaOcho" type="button" class="btn btn-outline-secondary">x⁸</button>
                                            <button id="potenciaNueve" type="button" class="btn btn-outline-secondary">x⁹</button>
                                            <button id="potenciaCero" type="button" class="btn btn-outline-secondary">x⁰</button>
    
                                            <button id="superindexruno" type="button" class="btn btn-outline-secondary" >x⁻</button>
                                            <button id="superindexrdos" type="button" class="btn btn-outline-secondary" >x°</button>
                                            <button id="superindexrtres" type="button" class="btn btn-outline-secondary" >xª</button>
                                            <button id="superindexcuatro" type="button" class="btn btn-outline-secondary" >µ</button>
                                        </div>
    
                                        <div class="col-4">
                                            <label>Orden:</label>
                                            <input id="numOrden" name="cumSort" type="number" value="1" class="form-control" onkeypress="return isNumberKey(event)" maxlength="4"  autocomplete="off" placeholder=""/>
                                            <div class="d-md-none mb-2"></div>
                                        </div>
                                        <div class="col-4">
                                            <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                            <div class="col-3">
                                                <label class="switch-content switch-comprobante switch-active">
                                                    <input id='checkBoxLever' class="" type='checkbox' checked value='S' />
                                                    <div></div>
                                                </label>
                                            </div>
                                        </div>
                                    <!--input id="txtEstado" name="estado" type="text" class="form-control mt-5 ocultar" value="S" autocomplete="off" placeholder=""/>-->
                                    </div>
                                    <div class="col-12 form-group mt-6 row">
                                        <div id="divBtnAgregar" class="col-6">
                                            <button id="btnAgregarUnidadMedida" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"><i class="far fa-save"></i>Guardar</button>
                                        </div>
                                        <div id="divBtnUpdate" class="col-6 ocultar">
                                            <button id="btnUpdateUnidadMedida" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" name="update" type="submit"><i class="far fa-save"></i>Confirmar</button>
                                            <button id="btnCancelUnidadMedida" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2">Cancelar</button>
                                        </div>
    
                                    </div>
                                </div>
                            
                        </div>
                    </div>
                </div>
            </div>
            <!-- FIN FORMULARIO REGISTRO UNIDAD MEDIDA -->
        </div>
        <script src="https://unpkg.com/imask"></script>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/ConfiguracionUnidadesMedidas.js"></script>
    </body>
</html>
