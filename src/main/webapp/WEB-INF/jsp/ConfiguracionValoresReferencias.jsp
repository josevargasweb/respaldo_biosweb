<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración Valores de Referencias</title>
        <jsp:include page="common/Styles_1.jsp"/>
        <link rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
            integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
            crossorigin="anonymous">
            <link href="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.css" rel="stylesheet" type="text/css" />
            <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/tables.css"/>
        <link rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"
            integrity="sha512-mSYUmp1HYZDFaVKK//63EcZq4iFWFjxSL+Z3T/aCt4IO9Cejm03q3NKKYN6pFQzY0SBOr8h+eCIAZHPXcpZaNw=="
            crossorigin="anonymous" referrerpolicy="no-referrer" />
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
        <div class="container container-main-content">
            <div class="row d-flex justify-content-center">
                <div class="accordion col-12 pl-0 pr-0" id="registro-paciente-main">
                    <!-- buscador -->
                    <jsp:include page="BusquedaTest.jsp"/>
                    <!-- buscador -->
                    <!-- FORMULARIO REGISTRO TEST -->
                    <div id="divVR" class="card border-15" style="display:none">
                        <div class="card-header border-15">
                            <div class="card-title row justify-content-between">
                                <h3 id="tituloRegistro" class="card-label col-12 pl-0 ">Configuración de Valores de Referencia</h3>
                            </div>
                        </div>
                        <div class="card-body no-gutters">
                            <div id="divForm" class="row">
                                <div class="col-12 row">
                                    <div class="col-12 container-valores-list">
                                        <button id="addRow" type="button" class="btn btn-blue-primary mb-2">Nuevo</button>
                                        <table class="table table-bordered table-checkable mt-10" id="tablaValoresReferencia">
                                            <thead>
                                                <tr>
                                                    <th rowspan="2" style="text-align:center" valign="bot">Sexo</th>
                                                    <th colspan="3" style="text-align:center">Desde</th>
                                                    <th colspan="3" style="text-align:center">Hasta</th>
                                                    <th rowspan="2" style="text-align:center" valign="top">Método</th>
                                                    <th rowspan="2" style="text-align:center" valign="top">Bajo Crítico</th>
                                                    <th rowspan="2" style="text-align:center" valign="top">Bajo</th>
                                                    <th rowspan="2" style="text-align:center" valign="top">Alto</th>
                                                    <th rowspan="2" style="text-align:center" valign="top">Alto Crítico</th>
                                                    <th rowspan="2" style="text-align:center" valign="top">Texto</th>
                                                    <th rowspan="2" style="text-align:center" valign="top">Borrar</th>
                                                </tr>
                                                <tr>
                                                    <th align="center" valign="top">Años</th>
                                                    <th align="center" valign="top">Meses</th>
                                                    <th align="center" valign="top">Días</th>
                                                    <th align="center" valign="top">Años</th>
                                                    <th align="center" valign="top">Meses</th>
                                                    <th align="center" valign="top">Días</th>
                                                </tr>
                                            </thead>
                                        </table>
                                        
                                    </div>
                                    <div class="col-md-6 form-group mt-3">
                                        <button id="btnGuardar" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-4 mr-2" >
                                            <i class="far fa-save"></i>Guardar
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- FORMULARIO REGISTRO TEST -->
                </div>
            </div>
        </div>
        	<!-- Modal eliminar valor -->
        <div class="modal fade" id="usuarioElimina" tabindex="-1"
            role="dialog" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body"></div>
                </div>
            </div>
        </div>
        <!-- Fin Modal -->
            <jsp:include page="common/Scripts.jsp"/>
            <script src="https://unpkg.com/imask"></script>
            <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
            <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
            <script src="<%=request.getContextPath()%>/resources/js/dataTables.cellEdit.js"></script>
            <script src="https://cdn.datatables.net/datetime/1.1.1/js/dataTables.dateTime.min.js"></script>
            <script src="https://cdn.datatables.net/scroller/2.0.6/js/dataTables.scroller.min.js"></script>
            <script src="https://cdn.datatables.net/select/1.4.0/js/dataTables.select.min.js"></script>
            <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
            <script src="<%=request.getContextPath()%>/resources/js/common/BusquedaTests.js"></script>
            <script src="<%=request.getContextPath()%>/resources/js/valoresreferencia/ConfiguracionValoresReferencias.js"></script>
            <script>
                    $("#circuloAgregarTest").hide();
            </script>
    </body>
</html>
