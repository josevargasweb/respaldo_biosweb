<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Rechazo de Muestras</title>
        <jsp:include page="common/Styles_1.jsp"/>
        <!--begin::Layout Themes(used by all pages)-->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/tables.css"/>
        <!--end::Layout Themes-->
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
        <jsp:include page="modal/ModalDatosPaciente.jsp"/>
        <jsp:include page="modal/ModalDatosOrden.jsp"/>
        <c:if test="${errorPageLoad!=null}"><input type="hidden" id="errorPageLoad" value="${errorPageLoad}" /></c:if>

        <div class="container container-main-content container-edicion-ordenes">
            <div class="row d-flex justify-content-center">
                <div class="accordion col-12 pl-0 pr-0" id="edicion-orden-main">
                      <div id="panelSuperior" class="card">
                        <div class="card-header mb-0 border-15">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10"
                                    data-toggle="collapse"
                                    data-target="#panelBusquedaOrden" aria-expanded="true"
                                    aria-controls="panelBusquedaOrden">B&uacute;squeda de Orden</h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="btnLimpiarFiltro" class="navi-link"> <span
                                        class="symbol symbol-30 symbol-circle "> <span
                                            id="circuloLimpiarFiltro"
                                            class="symbol-label bg-hover-blue  hoverIcon" href="#"
                                            data-toggle="tooltip" title="Limpiar"> <i
                                                id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span> <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="panelBusquedaOrden"
                        class="collapse container-content show"
                        aria-labelledby="edicion-orden-title"
                        data-parent="#edicion-orden-main">
                            <div class="card-body">
                                <div class="col-12">
                                    <input class="col-12 ocultar" id="volverNomalidad" type="button" value="TMclick activo" style="visibility: hidden" />
                                    <input type="hidden" id="procedenciaUsuario" name="procedenciaUsuario" value="${perfilUsuario.cpuIdprocedencia}" />
                                    <input type="hidden" id="idSeccion" value="${idSeccion}" /> 
                                    <input type="hidden" id="sesionUsuario" name="sesionUsuario" value="${usuario.duIdusuario}" />
                                    <input type="hidden" id="estadosPacienteLoad" name="estadosPacienteLoad" value="${listaEstadosStr}" />
                                      <jsp:include page="desarrollo/Bios_OrdenesEdicion.jsp"></jsp:include>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="divTablaMuestras" class="card border-15 " style="display: none">
                        <div class="card-header mb-0 border-15">
                            <div class="card-title d-flex justify-content-between pt-2 pb-2">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10">  Rechazo de muestra - Muestras (Nro. Orden: <label id="lblNroOrden"></label>)</h3>
                            </div>
                        </div>
                        <div id="" class="container-content">
                            <div class="card-body">
                                <div class="col-12 pl-0 pr-0">
                                    <div class="row">
                                        <div class="col-9">
                                            <div class="card card-2 h-100">
                                                <div class="card-body container-table-list containerTablaTestMuestras">
                                                    <table class='table table-hover table-striped nowrap compact-xs table-selected' id="tablaMuestras">
                                                        <thead>
                                                            <tr>
                                                                <th>N&deg; Muestra</th>
                                                                <th>Estado</th>
                                                                <th>Muestra</th>
                                                                <th>Contenedor</th>
                                                                <th>Extracci&oacute;n</th>
                                                                <th>Flebotomista</th>
                                                                <th>Observación</th>
                                                            </tr>
                                                            <tr>
                                                                <th></th>
                                                                <th></th>
                                                                <th></th>
                                                                <th></th>
                                                                <th></th>
                                                                <th></th>
                                                                <th></th>
                                                            </tr>
                                                        </thead>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-3">
                                            <div class="card card-2"  id="divRechazo">
                                                <div class="card-body containerTablaTestMuestras">
                                                    <span id="lblPrimerRechazo" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " style="display:none;">Primer Rechazo</span>
                                                    <span id="lblSegundoRechazo" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " style="display:none;">Segundo Rechazo</span>
                                                    <span id="lblNoMasRechazos" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " style="display:none;">No se permiten más rechazos</span>
                                                    </br></br>
                                                    <label for="causaRechazoMuestras">Causa de rechazo:</label>
                                                    <select id="causaRechazoMuestras" name="causaRechazoMuestras" class="form-control selectpicker disabledForm" data-live-search="true">
                                                        <c:forEach var="causa" items="${listCausaRechazoMuestras}">
                                                            <option value="${causa.ccrmIdcausarechazo}">${causa.ccrmDescripcion}</option>
                                                        </c:forEach>
                                                    </select>
                                                    </br></br>
                                                    <label for="observacion">Observación:</label>
                                                    <textarea id="observacion" class="form-control pr-5" name="observacion" rows="3" cols="50"></textarea>
                                                    </br>
                                                    <span id="lblNuevaMuestra" class="label label-lg label-inline">Nueva Muestra: <label id="lblIdNuevaMuestra" class="label label-lg label-inline"></label></span>
                                                    <span id="lblNuevaMuestraPendiente" class="label label-lg label-inline label-danger" style="display:none;">Nueva Muestra Pendiente</span>
                                                    <span id="lblNuevaMuestraLista" class="label label-lg label-inline label-success" style="display:none;">Nueva Muestra Lista</span>
                                                    </br></br>
                                                    <button class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2" id="btnRechazoParcial" data-toggle="modal" title="Rechazo parcial">
                                                        <img src="<%=request.getContextPath()%>/resources/img/rechazoparcial.png" width="20" height="20" />
                                                        Rechazo parcial
                                                    </button>
                                                    <button type="button"  id="btnRechazarMuestra" class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">Rechazar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-none">
                                        <label id="idPacienteTabla" class=""></label>
                                        <label id="idOrdenTabla" class=""></label>
                                        <label id="idUsuario" class=""></label>
                                    </div>
            
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        
        <div id="modalRechazoParcial" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <h3 id="tituloPacienteModal" class="text-center">Rechazo Parcial</h3>
                        <div class="col-12 row line-height-1-5 mt-3">
                            <div class="col-8 strong-data">
                                <div class="col-12 pl-0 pr-0">
                                    <label class="text-label">Paciente</label>
                                </div>
                                <span class="" id="pacienteModal"></span>
                            </div>
                            <div class="col-4 strong-data">
                                <div class="col-12 pl-0 pr-0">
                                    <label class="text-label"># Muestra</label>
                                </div>
                                <span class="" id="nroMuestraModal"></span>
                            </div>
                            
                            <div class="col-12">
                                <div id="" class="col-12 row mt-5 ">
                                    <label class="text-label">Ex&aacute;menes</label>
                                    <table id="examenesRechazo" class="table">
                                        <thead>
                                            <tr>
                                                <th scope="col">#</th>
                                                <th scope="col">Examen</th>
                                                <th scope="col" style="text-align:center;">¿Rechazar?</th>
                                            </tr>
                                        </thead>
                                        <tbody id="examenesRechazoBody">

                                        </tbody>
                                    </table>

                                </div>
                            </div>
                        </div>
                        <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                            <button type="button" id="confirmRechazoParcial" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 ">Confirmar</button>
                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                        </div>
                        
                        <!--
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" id="confirmRechazoParcial">Confirmar</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        </div>-->
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/common/InicioFunciones.js"></script>
        <!--begin::Page Scripts(used by this page)-->
        <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
        <!--end::Page Vendors-->
        <script src="<%=request.getContextPath()%>/resources/js/dataTables.cellEdit.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/queue.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Dto/FiltroOrden.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgMuestras.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgEnvases.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modals/ModalDatosPaciente.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modals/ModalDatosOrden.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/biosvalidador.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboActualizado.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/ModuloFecha.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/rechazomuestras/RechazoMuestras.init.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/rechazomuestras/RechazoMuestras.muestras.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/rechazomuestras/RechazoMuestras.ordenes.js"></script>
    </body>
</html>