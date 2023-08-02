<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BiosLIS | Registro de documentos</title>
        <jsp:include page="common/Styles_1.jsp"/>
        <jsp:include page="common/FileInputStyles.jsp"/>
        <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/biosbo.css"> -->
        <!-- <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
        <link href="https://cdn.datatables.net/select/1.4.0/css/select.dataTables.min.css" rel="stylesheet" type="text/css" />
        <link href="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
            .boDisabled {
              color:grey;
            }
            .boDisabled  input{
              color:grey;
            }
            .boDisabled  select{
              color:grey;
            }
        </style> -->
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
								aria-controls="divBuscadorOrdenes">B&uacute;squeda de Orden</h3>
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
                    <div id="divRegistroDocumentos" class="card border-15" style="display:none;">
                        <div class="card-header border-15" id="headingRegistroPaciente">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="card-label col-3 pl-0"> Registro de documentos</h3>
                            </div>
                        </div>
                        <div id="panelResultadoOrden ">
                            <div class="card-body no-gutters">
                                <div class="col-12">
                                    <div class="row justify-content-end">
                                        <div class="col-9 pl-0">
                                            <h5 class="text-center">Paciente: <label id="lblPaciente"></label></h5>
                                            <h5 class="text-center">Orden: <label id="lblOrden"></label></h5>
                                        </div>
                                        <div class="col-auto">
                                            <button id='btnVerOrden' class="btn btn-blue-primary btn-lg font-weight-bold mr-2"><i class="fas fa-user"></i>Datos de Paciente</button>
                                        </div>
                                    </div>
                                    <div class="row mt-5">
                                        <div class="col-4">
                                            <h5>Orden m&eacute;dica</h5>
                                            <form id="formOM" class="file-container" method="post" enctype="multipart/form-data">
                                                <input id="ordenMedicaFile" name="ordenMedicaFile" type="file" class="file doc-orden" 
                                                       
                                                       data-browse-on-zone-click="true">
                                            </form>
                                        </div>
                                        <div class="col-4">
                                            <h5>Consentimiento</h5>
                                            <form id="formCO" class="file-container" method="post" enctype="multipart/form-data">
                                                <input id="consentimientoFile" name="consentimientoFile" type="file" class="file doc-orden" 
                                                       
                                                       data-browse-on-zone-click="true">
                                            </form>
                                        </div>
                                        <div class="col-4">
                                            <h5>Otros documentos</h5>
                                            <form id="formOD" class="file-container" method="post" enctype="multipart/form-data">
                                                <input id="otrosDocsFile" name="otrosDocsFile" type="file" class="file doc-orden" 
                                                       
                                                       data-browse-on-zone-click="true">
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

         <!-- **************************** Modal Paciente ******************************************                    -->
         <div class="modal fade" id="datosPacienteModal" tabindex="-1"
         role="dialog" aria-labelledby="datosPacienteModal" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl"
                    role="document">
                <div class="modal-content">
                    <div class="modal-header">
                            <h1 class="modal-title" id="exampleModalLabel">Datos Paciente</h1>
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-label="Close">
                                    <i aria-hidden="true" class="ki ki-close"></i>
                            </button>
                    </div>
                    <div class="modal-body">
                            <jsp:include page="microbiologia/OrderCanvasData.jsp" />
                    </div>
                </div>
            </div>
        </div>
        
        <jsp:include page="common/Scripts.jsp"/>
        <!-- Decidí crear un archivo jsp donde incluya los estilos y scripts del plugin Krajee bootstrap-fileinput así es más fácil copiarlos donde se requiera -->
        <jsp:include page="common/FileInputScripts.jsp"/>
        <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
        <script src="https://unpkg.com/imask"></script>
        <script src="https://cdn.datatables.net/scroller/2.0.6/js/dataTables.scroller.min.js"></script>
        <script src="https://cdn.datatables.net/select/1.4.0/js/dataTables.select.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/biosvalidador.js"></script>
        <script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboActualizado.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/ModuloFecha.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/registrodocumentos/RegistroDocumentos.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/registrodocumentos/UploadDocumentos.js"></script>
    </body>
</html>
