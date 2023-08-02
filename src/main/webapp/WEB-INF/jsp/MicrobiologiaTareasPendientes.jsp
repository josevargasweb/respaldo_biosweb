<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <jsp:include page="common/Styles_1.jsp" />
                    <title>BiosLIS | MB - Tareas Pendientes</title>
                </head>

                <body id="kt_body"
                    class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
                    <div class="row menu-container">
                        <div class="col-md-6">
                            <jsp:include page="common/Menu_dinamico.jsp" />
                        </div>
                        <div class="col-md-6">
                            <jsp:include page="common/Header_1.jsp" />
                        </div>
                    </div>
                    <!-- <div class="container mt-5">
                        <jsp:include page="microbiologia/PendingTasks.jsp" />
                    </div> -->

                    <div class="container container-main-content container-documentos container-impresion-etiquetas">
                        <c:if test="${mensaje!=null}"><div class="alert alert-success" role="alert">${mensaje}</div></c:if>
                        <div class="row d-flex justify-content-center">
                            <div class="accordion col-12 pl-0 pr-0" id="impresion-etiquetas-main">
                                <div id="panelSuperior" class="card">
                                    <div class="card-header border-15" id="impresion-etiquetas-title">
                                        <div class="card-title d-flex justify-content-between">
                                            <h3 class="mb-0 btn-block text-left pl-0 col-10"
                                            data-toggle="collapse"
                                            data-target="#divBuscadorOrdenes"  aria-expanded="true"
                                            aria-controls="divBuscadorOrdenes">B&uacute;squeda de &Oacute;rdenes</h3>
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
                                    <div id="divBuscadorOrdenes"
                                    aria-labelledby="impresion-etiquetas-title"
                                    data-parent="#impresion-etiquetas-main"
                                    class="collapse container-content show">
                                    <div class="card-body">
                                        <div class="col-12">
                                            <!-- <jsp:include page="desarrollo/Bios_OrdenesEdicion.jsp"></jsp:include> -->
                                            <jsp:include page="desarrollo/Bios_OrdenesEdicion.jsp"></jsp:include>
                                        </div>
                                    </div>
                                    </div>
                                </div>
         
                            </div>
                        </div>
                    </div>
                    <jsp:include page="common/Scripts.jsp"/>

                    <script
                        src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/js/common/biosvalidador.js"></script>
                    <script
                            src="<%=request.getContextPath()%>/resources/js/Dto/CfgPaneles.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboActualizado.js"></script>
                        <script
                        src="<%=request.getContextPath()%>/resources/js/common/ModuloMuestras.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/TareasPendientes.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/js/microbiologia/templates.js"></script>

                </body>

                </html>