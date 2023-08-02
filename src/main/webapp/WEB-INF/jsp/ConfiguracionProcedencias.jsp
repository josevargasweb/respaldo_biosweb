<%-- 
    Document   : ConfiguracionProcedencias
    Created on : 07-12-2021, 10:46:54
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
        <title>BIOS-LIS | Configuración de Procedencias</title>
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
                    <div id="panelProcedencias" class="card border-15">
                        <div class="card-header border-15" id="headingProcedencias">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    B&uacute;squeda de procedencias
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end"> 
                                    <div class="float-right" >
                                        <a id="nuevaProcedencia" class="navi-link">
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circuloAgregar" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Nueva Procedencia">
                                                    <i id="iAgregar" class='fas fa-plus text-blue'></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                    <div class="float-right">
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
                    </div>
                    <div id="collapseHeader" class="collapse show" aria-labelledby="headingProcedencias" data-parent="#headingProcedencias">
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
                                                <th scope="col">Nombre</th>
                                            </tr>
                                        </thead>
                                        <tbody id="tbodyFiltro"></tbody>
                                    </table>
                                    <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron registros con los datos provistos.</label>
                                </div>
                                <label id="lblTotalFiltro">Procedencias encontradas: <span id="contRegistros"></span></label>
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
                            Registro de Procedencias
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
                            <div class="float-right">
                                <a id="btnEditarProcedencia" class="navi-link pointer">
                                    <span class='symbol symbol-30 symbol-circle mr-3' >
                                        <span id="circuloEditarProcedencia" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar Procedencia">
                                            <i id="iEditProcedencia" class="far fa-edit text-dark-50"></i>
                                        </span>
                                    </span>
                                </a>
                            </div>
                            <div class="float-right" >
                                <a id="buscarProcedencia" class="navi-link" href="#" >
                                    <span class='symbol symbol-30 symbol-circle mr-3'>
                                        <span id="circuloBuscarProcedencia" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar Procedencia">
                                            <i id="iBuscarProcedencia" class="fas fa-search text-blue"></i>
                                        </span>
                                    </span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div id="formRegistroProcedencia" >
                        <div id="divForm" class="row">
                            <div class="col-12 row">
                                <input id="txtIdProcedencia" name="idProcedencia" type="text" class="form-control ocultar" autocomplete="off" />
                                <div class="col-4">
                                    <label for="txtCodigoProcedencia">Código:</label>
                                    <input id="txtCodigoProcedencia" name="cpCodigo" type="text" class="form-control formProc" maxlength="15" autocomplete="off" /><!--name debe ser reemplazado por atributos de la entity-->
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-md-4">
                                    <label for="txtNombreProcedencia">Procedencia:</label>
                                    <input id="txtNombreProcedencia" maxlength="50" name="cpDescripcion" type="text" class="form-control formProc" autocomplete="off" />
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-md-4">
                                    <label>Orden:</label>
                                    <input id="numOrdenProcedencia" name="cpSort" type="number" value="1" class="form-control formProc" autocomplete="off" />
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-md-4">
                                    <label>E-mail</label>
                                    <input id="txtEmailProcedencia" maxlength="50" name="cpEmail" type="email" class="form-control formProc" autocomplete="off" />
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-md-4">
                                    <label for="selectCentro">Centro:</label>
                                    <select class="form-control selectpicker disabledForm formProc" id="selectCentro" name="cpCentro" data-live-search="true"></select>
                                </div>
                                <div class="col-md-4">
                                    <label for="selectConv">Convenio:</label>
                                    <select class="form-control selectpicker disabledForm formProc" id="selectConv" name="cpConvenio" data-live-search="true"></select>
                                </div>
                                <div class="col-md-4">
                                    <label>Texto Informe</label>
                                    <input id="txtInformeProcedencia" maxlength="400" name="cpTextoInforme" type="text" class="form-control formProc" autocomplete="off" />
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-md-4">
                                    <label>Grupo</label>
                                    <input id="txtGrupoProcedencia" maxlength="10" name="cpCodigoGrupo" type="text" class="form-control formProc " autocomplete="off" />
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-12 mt-2">
                                    <label class="col-md-12 checkbox checkbox-primary">
                                        <input id="checkBoxRestringida" type="checkbox" class="formProc" name="cpProcedenciarestringida" value="S" /><b>Restringida:</b> Usuarios asignados a esta procedencia no pueden imprimir informes
                                        <span></span>
                                    </label>
                                </div>
                                <div class="col-12">
                                    <label class="col-md-12 checkbox checkbox-primary">
                                        <input id="checkBoxTomaMuestraAuto" type="checkbox" class="formProc" name="cpTomamuestraautomatica" value="S" /><b>Toma de Muestra Automática</b>
                                        <span></span>
                                    </label>
                                </div>
                                <div class="col-12">
                                    <label class="col-md-12 checkbox checkbox-primary">
                                        <input id="checkBoxMembrete" type="checkbox" class="formProc" name="cpMembrete" value="S" /><b>Informe con encabezado pre-impreso</b>
                                        <span></span>
                                    </label>
                                </div>
                                <div class="col-6">
                                    <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                    <div class="col-3">
                                        <label class="switch-content switch-comprobante switch-active">
                                            <input id='checkBoxActivo' name='cpActivo' class="" type='checkbox' checked value='S' />
                                            <div></div>
                                        </label>
                                    </div>
                                </div>
                                <div class="col-6 mt-3">
                                    <div id="divBtnInoperabilidad">
                                        <!-- Button modal inoperabilidad (host) -->
                                        <button id="btnInoperabilidad" type="button" class="btn btn-lg btn-blue-primary" data-toggle="modal" data-target="#exampleModalScrollable">
                                            Interoperabilidad (Host)
                                        </button>
                                        <!-- MODAL INTEROPERABILIDAD (HOST) -->    
                                        <div class="modal fade" id="exampleModalScrollable" tabindex="-1" role="dialog" aria-labelledby="staticBackdrop" aria-hidden="true">
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
                                                                        <input id="txtHost1" maxlength="20" class="form-control formProc" name="cpHost" type="text" />
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-12">
                                                                <div class="form-group row">
                                                                    <label class="col-3 col-form-label">Host 2:</label>
                                                                    <div class="col-7">
                                                                        <input id="txtHost2" maxlength="20" class="form-control formProc" name="cpHost2" type="text" />
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-12">
                                                                <div class="form-group row">
                                                                    <label class="col-3 col-form-label">Host 3:</label>
                                                                    <div class="col-7">
                                                                        <input id="txtHost3" maxlength="20" class="form-control formProc" name="cpHost3" type="text" />
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    <!--</div>
                                                    <div class="modal-footer">
                                                        <a id="btnLimpiarModal" class="btn btn-light-primary btn-lg font-weight-bold"><i id="iLimpiarModal" class="la la-broom icon-xl text-primary"></i>Limpiar</a>-->
                                                        <button type="button" id="btnGuardarHosts" class="btn btn-blue-primary btn-lg font-weight-bold" data-dismiss="modal">Ok</button>
                                                    </div>
                                                 </div>
                                             </div>
                                         </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <div class="col-md-6 form-group mt-6 row">
                        <div id="divBtnAgregar" class="col-6">
                            <button id="btnAgregar"  class="btn btn-blue-primary btn-lg font-weight-bold mr-2 mt-2" ><i class="far fa-save"></i>Guardar</button>
                        </div>
                        <div id="divBtnUpdate" class="col-6 ocultar">
                            <button id="btnUpdate" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" name="update" type="submit"><i class="far fa-save"></i>Confirmar</button>
                            <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                        </div>
                    </div>
                    </div>
                </div>
            </div>

        </div>
        <script src="https://unpkg.com/imask"></script>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/ConfiguracionProcedencias.js"></script>
    </body>
</html>