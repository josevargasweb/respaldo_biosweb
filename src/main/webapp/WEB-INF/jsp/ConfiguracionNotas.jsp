<%-- 
    Document   : ConfiguracionMetodos
    Created on : Sep 21, 2020, 4:01:40 PM
    Author     : Nacho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Notas</title>
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
        <div class="container mt-5">
            <!-- FORMULARIO REGISTRO TEST -->
            <div class="card card-custom gutter-b  mt-5">
                <div class="card-header pl-2"> 
                    <div class="card-title row col-12 d-flex justify-content-between">
                        <h3 id="tituloRegistro" class="card-label col-12 pl-0 text-center">
                            Notas Automáticas
                        </h3>
                    </div>
                </div>
                <div class="card-body">
                    <form id="formRegistroTest" method="post">
                        <div id="divForm" class="row">
                            <div class="col-12 row">
                                
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Nota</th>
                                            <th>Registro de Paciente</th>
                                            <th>Toma o Recepcion de Muestra</th>
                                            <th>Captura de Resultado</th>
                                            <th>Nota Interna</th>
                                            <th>Notificacion Valores Criticos</th>
                                            <th>Pie de Firma de Informe</th>
                                            <th>Activo?</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tbodyNotas">
                                        
                                    </tbody>
                                </table>
                                <button id="btnAddRow" class="btn btn-blue-primary btn-lg font-weight-bold" type="button" >AGREGAR FILA</button>
                            </div>
                    </form>
                </div>
            </div>
            <!-- FIN FORMULARIO REGISTRO PACIENTE -->
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/ConfiguracionNotas.js"></script>
    </body>
</html>
